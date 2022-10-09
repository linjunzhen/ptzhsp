/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowDataUtil;
import net.evecom.core.util.FlowNodeBean;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.TacheFlowDao;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.FlowChartInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 监察流程service impl
 * 
 * @author Sundy Sun
 * @version 2.0
 */
@SuppressWarnings("unchecked")
@Service("flowChartService")
public class TacheFlowServiceImpl extends BaseServiceImpl implements
        TacheFlowService {
    /**
     * 数据访问dao
     */
    @Resource
    private TacheFlowDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public TacheFlow getFlowByTacheId(String tacheId) {
        // TODO Auto-generated method stub
        return dao.getFlowByTacheId(tacheId);
    }

    @Override
    public List<TacheFlow> getFlowByBusiCode(String busiCode) {
        // TODO Auto-generated method stub
        return dao.getFlowByBusiCode(busiCode);
    }

    @Override
    public TacheFlow getFlowByTacheCode(String tacheCode) {
        // TODO Auto-generated method stub
        return dao.getFlowByTacheCode(tacheCode);
    }

    @Override
    public void saveFlow(String tacheId, String tacheCode, String busiCode,
            String flowInfo, String height) {
        Map<String, Object> map = new HashMap<String, Object>();
        // map.put("FLOW_ID", flowId);
        map.put("tache_code", tacheCode);
        map.put("BUS_code", busiCode);
        map.put("flow_info", flowInfo);
        if (tacheCode != null && !"".equals(tacheCode)) {
            dao.updateFlowInfo(tacheCode, flowInfo, height);
        } else {
            dao.saveOrUpdate(map, "T_LCJC_BUS_TACHE", null);
        }
    }

    @Override
    public void updateFlow(String tacheCode, String flowInfo, String height) {
        dao.updateFlowInfo(tacheCode, flowInfo, height);
    }

    @Override
    public void submitAudit(String tacheCode, String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer(
                "update T_LCJC_BUS_SPECIAL set change_flag=?"
                        + ",update_user=?,update_time=?");
        String params = null;
        if (StringUtils.isNotEmpty(busCode)) {
            updateSql.append("   where bus_code=?");
            params = busCode;
        }
        dao.executeSql(updateSql.toString(), new Object[] {"2", userId,date, params });
        //更新环节状态
        StringBuffer tacheSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE set status=1,update_time=?  where bus_code=?");
        dao.executeSql(tacheSql.toString(), new Object[] { date,busCode });
        //dao.submitAudit(tacheCode, busCode);
        
        //更新审核记录
        String query="select * from t_lcjc_bus_audit where bus_code=? and config_code=?";
        List<Map<String, Object>> objs=dao.findBySql(query, new Object[] {busCode,"YWLCT"}, null);
        if(objs.size()>0){
            StringBuffer sql = new StringBuffer(
                    "update t_lcjc_bus_audit set status=?"
                            + ",update_user=?,update_time=? where bus_code=? and config_code=?");
            dao.executeSql(sql.toString(), new Object[] {"1", userId,date,busCode,"YWLCT"});
//        }
//        if(audit!=null&&!"".equals(audit)){
//            StringBuffer sql = new StringBuffer(
//                    "update t_lcjc_bus_audit set status=?"
//                            + ",update_user=?,update_time=? where bus_code=? and config_code=?");
//            dao.executeSql(sql.toString(), new Object[] {"1", userId,date,busCode,"YWLCT"});
        }else{
          //往审核表插入记录
            Map<String, Object> reqData =new HashMap<String, Object>();
            reqData.put("config_name","业务流程图");
            reqData.put("tree_sn", "1");
            reqData.put("status", "1");
            reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("CREATE_USER",userId);
            reqData.put("BUS_CODE",busCode);
            reqData.put("config_code","YWLCT");
            dao.saveOrUpdate(reqData,"t_lcjc_bus_audit",null);
        }
    }

    @Override
    public BusSpecialInfo getBusByBusCode(String buscode) {
        return dao.getBusByBusCode(buscode);
    }

    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT distinct U.*,t.status tstatus");
        sql.append(" FROM t_lcjc_bus_special U left join t_lcjc_bus_tache T on u.bus_code=t.bus_code where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<TacheFlow> getFlowByBusId(String busid) {
        return dao.getFlowByBusId(busid);
    }

    @Override
    public void updateBusiState(String state, String busCode, String busId,
            String userId) {
        dao.updateBusiState(state, busCode, busId, userId);
    }

    @Override
    public void flowPassAudit(String state, String busCode, String userId) {
        dao.flowPassAudit(state, busCode, userId);
    }

    @Override
    public void updateAuditInfo(String status,String userId, String buscode, String configName) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        //更新流程图审核状态
        StringBuffer updateSql = new StringBuffer(
                "update t_lcjc_bus_audit set status=?"
                        + ",update_user=?,update_time=? where bus_code=? and config_code=?");
        dao.executeSql(updateSql.toString(), new Object[] {status, userId,date,buscode,"YWLCT"});
        //更新监察点审核状态
        StringBuffer updateSql2 = new StringBuffer(
                "update t_lcjc_bus_audit set status=?"
                        + ",update_user=?,update_time=? where bus_code=? and config_code=?");
        dao.executeSql(updateSql2.toString(), new Object[] {status, userId,date,buscode,"JCJDYYS"});
    }

    @Override
    public List<Map<String, Object>> findTemplate(String templatecode) {
        String sql = "select * from T_LCJC_BUS_FLOW_TEMPLATE r WHERE";
        sql+=" template_CODE=? ";
        return dao.findBySql(sql, new Object[]{templatecode}, null);
    }

    @Override
    public List<Map<String, Object>> findTemplateBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * from T_LCJC_BUS_FLOW_TEMPLATE U ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

}
