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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.MonitorNodeDao;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.service.MonitorNodeService;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Service("monitorNodeService")
public class MonitorNodeServiceImpl extends BaseServiceImpl implements MonitorNodeService {
    /**
     * table name
     */
    private static final String TABLE_NAME = "T_LCJC_BUS_MONITOR_NODE";
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MonitorNodeServiceImpl.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 流程service
     */
    @Resource
    private TacheFlowService flowChartService;
    /**
     * 数据访问dao
     */
    @Resource
    private MonitorNodeDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        //StringBuffer sql = new StringBuffer("SELECT U.* FROM view_bus_special_factor_new U");
        StringBuffer sql = new StringBuffer("SELECT U.* FROM view_bus_special_elements U");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 根据过程编码查询要素
     * 
     * @param processCode
     * @return
     */
    @Override
    public List<Map<String, Object>> findByProcessCode(String processCode) {
        // String sql = "select * from t_lcjc_bus_ruleconfig r WHERE";
        String sql = "select r.*,a.dic_code,a.dic_name ";
        sql += " from t_lcjc_bus_ruleconfig r left join ";
        sql += "(select dic_code,dic_name from T_MSJW_SYSTEM_DICTIONARY t where t.type_id in(";
        sql += "select ty.type_id from T_MSJW_SYSTEM_DICTYPE ty where type_code='EsuperType')) a ";
        sql += "on r.analysis_type=a.dic_code  where r.PROCESS_CODE=?  ORDER BY update_TIME ASC";
        return dao.findBySql(sql, new Object[] { processCode }, null);
    }

    @Override
    public void factorSubmit(String busCode, String userId) {
        dao.factorSubmit(busCode, userId);
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update T_LCJC_BUS_SPECIAL set change_flag=?"
                + ",update_user=?,update_time=?");
        String params = null;
        if (StringUtils.isNotEmpty(busCode)) {
            updateSql.append("   where bus_code=?");
            params = busCode;
        }
        dao.executeSql(updateSql.toString(), new Object[] { "-1", userId, date, params });

//        Object audit = dao.getObjectBySql("select * from t_lcjc_bus_audit where bus_code=? and config_code=?",
//                new Object[] { busCode, "JCJDYYS" });
        String query="select * from t_lcjc_bus_audit where bus_code=? and config_code=?";
        List<Map<String, Object>> objs=dao.findBySql(query, new Object[] {busCode,"JCJDYYS"}, null);
        if (objs.size()>0) {
            StringBuffer sql = new StringBuffer("update t_lcjc_bus_audit set status=?"
                    + ",update_user=?,update_time=? where bus_code=? and config_code=?");
            dao.executeSql(sql.toString(), new Object[] { "1", userId, date, busCode,"JCJDYYS"});
        } else {
            // 往审核表插入记录
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("config_name", "监察节点与要素");
            reqData.put("tree_sn", "1");
            reqData.put("status", "1");
            reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("CREATE_USER", userId);
            reqData.put("BUS_CODE", busCode);
            reqData.put("config_code", "JCJDYYS");
            String recordId = dao.saveOrUpdate(reqData, "t_lcjc_bus_audit", null);
        }
    }

    @Override
    public List<Map<String, Object>> findByTacheCode(String tacheCode) {
        String sql = "select distinct(node_code) from t_lcjc_bus_monitor_node t ";
        sql += " where TACHE_CODE=? ";
        return dao.findBySql(sql, new Object[] { tacheCode }, null);
    }

    @Override
    public List<Map<String, Object>> findMonitorNodeByTCode(String tacheCode) {
        String sql = "select distinct(process_code) node_code from t_lcjc_bus_process t ";
        sql += " where TACHE_CODE=? and IS_MONITORNODE='1' ";
        return dao.findBySql(sql, new Object[] { tacheCode }, null);
    }

    @Override
    public void changeFactorSubmit(String busCode, String userId) {
        dao.changeFactorSubmit(busCode, userId);
    }

    /***
     * 描述 : 针对可编剧表格的更新与保存
     * 
     * @author Water Guo
     * @created 2015-8-18 下午3:10:54
     * @param variables
     */
    @Override
    public void saveOrUpdateBatch(Map<String, Object> variables) {
        // 字段基本信息
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String, Object> formMap = new HashMap<String, Object>();
        for (String str : formDatas.split("&")) {
            String[] strs = str.split("=");
            if (strs.length > 1) {
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String processCode = String.valueOf(formMap.get("PROCESS_CODE"));
        String[] substr = processCode.split("\\.");
        BusSpecialInfo bus = flowChartService.getBusByBusCode(substr[0]);
        if (StringUtils.isEmpty(processCode) || "null".equals(processCode)) {
            log.error("要素信息新增修改：过程编码为空！ERROR！");
        }
        // 可编剧表格的信息
        String jsonDatas = String.valueOf(variables.get("jsonDatas"));
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);
        String insertedJson = String.valueOf(jsonMap.get("inserted"));
        String updatedJson = String.valueOf(jsonMap.get("updated"));
        String deletedJson = String.valueOf(jsonMap.get("deleted"));
        // 当前用户信息
        SysUser user = AppUtil.getLoginUser();
        // 新增
        if (!"[]".equals(insertedJson)) {
            List<Map<String, Object>> insertedList = JSON.parseObject(insertedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> insertMap : insertedList) {
                insertMap.put("BUSSYS_UNITCODE", bus.getUnitCode());
                insertMap.put("PROCESS_CODE", processCode);
                insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("CREATE_USER", user.getUserId());
                insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_USER", user.getUserId());
                insertMap.put("STATUS", 0);
                String recordId = this.saveOrUpdate(insertMap, "T_LCJC_BUS_RULECONFIG", null);
                sysLogService.saveLog("新增了ID为[" + recordId + "]的要素信息记录", SysLogService.OPERATE_TYPE_ADD);
            }
        }
        // 修改
        if (!"[]".equals(updatedJson)) {
            List<Map<String, Object>> updatedList = JSON.parseObject(updatedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> updateMap : updatedList) {
                updateMap.put("PROCESS_CODE", processCode);
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", user.getUserId());
                String serialId = String.valueOf(updateMap.get("RULE_ID"));
                String recordId = this.saveOrUpdate(updateMap, "T_LCJC_BUS_RULECONFIG", serialId);
                sysLogService.saveLog("更新了ID为[" + recordId + "]的要素信息记录", SysLogService.OPERATE_TYPE_EDIT);
            }
        }
        // 删除
        if (!"[]".equals(deletedJson)) {
            List<Map<String, Object>> deletedList = JSON.parseObject(deletedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("RULE_ID"));
                this.remove("T_LCJC_BUS_RULECONFIG", "RULE_ID", new String[] { serialId });
                sysLogService.saveLog("删除了ID为[" + serialId + "]的要素信息记录", SysLogService.OPERATE_TYPE_DEL);
            }
        }
    }

}
