/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowNodeBean;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchange.dao.FlowChangeDao;
import net.evecom.platform.flowchange.model.FlowChangeView;
import net.evecom.platform.flowchange.service.FlowChangeService;
import net.evecom.platform.flowchart.dao.TacheFlowDao;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Service("flowChangeService")
public class FlowChangeServiceImpl extends BaseServiceImpl implements FlowChangeService {
    /**
     * 数据访问dao
     */
    @Resource
    private FlowChangeDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }



    /*
     * (non-Javadoc)
     * 
     * @see net.evecom.platform.flowchange.service.FlowChangeService#updateFlow
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void updateFlow(String tacheId, String flowInfo, String height,String applyId,String userId,String flowSvg) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update T_LCJC_BUS_TACHE_CHANGE ")
                .append(" set flow_info=? ,flow_height=?,update_date=?,update_user=?," +
                "flow_svg=? where tache_id=? and apply_id=?");
        dao.executeSql(updateSql.toString(), new Object[] { flowInfo, height, date,userId,flowSvg,tacheId,applyId});
    }

    @Override
    public List<TacheFlow> getFlowByBusiCode(String busiCode, String applyId) {
        return dao.getFlowByBusiCode(busiCode, applyId);
    }

    @Override
    public TacheFlow getFlowByTacheCode(String tacheCode, String applyId) {
        return dao.getFlowByTacheCode(tacheCode, applyId);
    }

    @Override
    public void copyTaches(String busCode, String date) {
        dao.copyTaches(busCode, date);
    }

    @Override
    public List<TacheFlow> getChangeFlowByBusId(String busId, String applyId) {
        return dao.getChangeFlowByBusId(busId, applyId);
    }

    @Override
    public void submitAudit(String tacheCode, String busCode, String userId, String applyId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 环节状态提交
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE_CHANGE set status=1,update_date=?,update_user=?  where apply_id=?");
        dao.executeSql(updateSql.toString(), new Object[] { date, userId, applyId });
        // 更新审核状态
        Map<String, Object> audit = this.getByJdbc("T_LCJC_BUS_AUDIT_CHANGE", new String[] { "APPLY_ID", 
            "BUS_CODE","CONFIG_CODE" }, new Object[] { applyId, busCode, "YWLCT" });
        if (audit == null) {
          //往审核表插入记录
            Map<String, Object> reqData =new HashMap<String, Object>();
            reqData.put("config_name","业务流程图");
            reqData.put("tree_sn", "1");
            reqData.put("status", "1");
            reqData.put("CREATE_TIME",date);
            reqData.put("CREATE_USER",userId);
            reqData.put("UPDATE_TIME", date);
            reqData.put("UPDATE_USER", userId);
            reqData.put("BUS_CODE",busCode);
            reqData.put("CONFIG_CODE","YWLCT");
            reqData.put("APPLY_ID", applyId);
            dao.saveOrUpdate(reqData,"T_LCJC_BUS_AUDIT_CHANGE",null);
        } else {
            audit.put("STATUS", "1");
            audit.put("UPDATE_TIME", date);
            audit.put("UPDATE_USER", userId);
            this.saveOrUpdate(audit, "T_LCJC_BUS_AUDIT_CHANGE", audit.get("AUDIT_ID").toString());
        }
//        StringBuffer sql = new StringBuffer("update t_lcjc_bus_audit set status=?"
//                + ",update_user=?,update_time=? where bus_code=? and config_code=?");
//        dao.executeSql(sql.toString(), new Object[] { "1", userId, date, busCode, "YWLCT" });
        // 业务专项状态提交
        // StringBuffer bsql = new StringBuffer("update ")
        // .append(" T_LCJC_BUS_SPECIAL_change set status=2,update_user=?,update_time=? "
        // + " where bus_code=?");
        // dao.executeSql(bsql.toString(), new Object[] { userId,
        // date, busCode});

    }

    @Override
    public void endAudit(String state, String applyId, String userId) {
        Map<String, Object> map = this.getByJdbc("T_LCJC_BUS_SPECIAL_change", new String[] { "apply_id" },
                new Object[] { applyId });
        // 更新业务专项副本状态、环节副本状态、更新专项正式表信息、覆盖新的环节到正式表
        String busCode = (String) map.get("BUS_CODE");
        String busName = (String) map.get("BUS_NAME");
        String unitCode = (String) map.get("unit_code");
        dao.endAudit(state, applyId, userId, busCode, busName);
        // 新的过程数据覆盖到正式的过程数据表
        handleCoverAndProcess(applyId, userId, busCode, unitCode);
    }

    public void handleCoverAndProcess(String applyId, String userId, String busCode, String unitCode) {
        // 1.生成节点对应数据
        List<TacheFlow> flist = dao.getFlowByBusiCode(busCode, applyId);
        for (int i = 0; i < flist.size(); i++) {
            TacheFlow flow = flist.get(i);
            String jsonStr = flow.getFlowInfo();
            if (StringUtils.isNotEmpty(jsonStr)) {
                JSONObject jsonObj = JSON.parseObject(jsonStr);
                JSONArray data = jsonObj.getJSONArray("nodeDataArray");
                List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
                for (int j = 0; j < data.size(); j++) {
                    String nodestr = data.getString(j);
                    FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
                    list.add(bean);
                }
                dao.saveProcessBatch(list, unitCode, userId, flow.getTacheCode());
            }
        }
    }

    public void handleUpdateProcess(List plist, String unitcode, String user, String tacheCode) {
        for (int i = 0; i < plist.size(); i++) {
            FlowNodeBean pro = (FlowNodeBean) plist.get(i);
            BusProcessInfo bus = getProcessByCode(pro.getId());
            if (bus != null) {
                Map<String, Object> reqData = new HashMap<String, Object>();
                reqData.put("PROCESS_ID", bus.getProcessId());
                reqData.put("PROCESS_CODE", pro.getId());
                reqData.put("PROCESS_NAME", pro.getText());
                reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("UPDATE_USER", user);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process", bus.getProcessId());
            } else {
                Map<String, Object> reqData = new HashMap<String, Object>();
                reqData.put("PROCESS_CODE", pro.getId());
                reqData.put("PROCESS_NAME", pro.getText());
                reqData.put("TACHE_CODE", tacheCode);
                reqData.put("UNIT_CODE", unitcode);
                reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("CREATE_USER", user);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process", null);
            }
        }
    }

    public BusProcessInfo getProcessByCode(String processCode) {
        StringBuffer sql = new StringBuffer("select * from t_lcjc_bus_process U");
        sql.append(" WHERE U.process_code=? ");

        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { processCode }, null);
        BusProcessInfo flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new BusProcessInfo();
            flow.setProcessCode((String) map.get("PROCESS_CODE"));
            flow.setProcessId((String) map.get("PROCESS_ID"));
            flow.setProcessName((String) map.get("PROCESS_NAME"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setStatus(String.valueOf(map.get("STATUS")));
            flow.setIsMonitorNode((String) map.get("IS_MONITORNODE"));
        }
        return flow;
    }

    @Override
    public void monitorNodePassAudit(String state, String busCode, String userId) {
        dao.monitorNodePassAudit(state, busCode, userId);
    }

    @Override
    public void coverOldTaches(String applyId) {
        dao.remove("t_lcjc_bus_tache", "apply_id", new Object[] { applyId });
        dao.coverOldTaches(applyId);
        // dao.remove("t_lcjc_bus_tache_change", "BUS_CODE", new
        // Object[]{busCode});
    }

    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        // StringBuffer sql = new
        // StringBuffer("SELECT U.* FROM t_lcjc_bus_special_change U");

        StringBuffer sql = new StringBuffer("SELECT distinct U.*,t.status tstatus");
        sql.append(" FROM t_lcjc_bus_special_change U,t_lcjc_bus_tache_change T where u.bus_code=t.bus_code");
        sql.append(" and u.apply_id=t.apply_id");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public void copyBusItem(String busCode, String date) {
        dao.copyBusItem(busCode, date);
    }

    @Override
    public void applyAuditPass(String applyId, String userId) {
        dao.applyAuditPass(applyId, userId);
    }

    @Override
    public List<Map<String, Object>> getBusByBusCode(String buscode, String applyId) {
        String sql = "select * from t_lcjc_bus_special_change r WHERE";
        sql += " bus_CODE=? and apply_id=?  ORDER BY CREATE_TIME ASC";
        return dao.findBySql(sql, new Object[] { buscode, applyId }, null);
    }

    @Override
    public List<Map<String, Object>> findNewestBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT U.* FROM t_lcjc_bus_special_change U ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<Map<String, Object>> getBusByApplyId(String applyId) {
        String sql = "select * from t_lcjc_bus_special_change r WHERE";
        sql += " apply_id=?  ORDER BY CREATE_TIME ASC";
        return dao.findBySql(sql, new Object[] { applyId }, null);
    }

}
