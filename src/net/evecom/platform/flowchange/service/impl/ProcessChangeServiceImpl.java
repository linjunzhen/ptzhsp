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

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.flowchange.dao.ProcessChangeDao;
import net.evecom.platform.flowchange.service.ProcessChangeService;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.flowchart.service.impl.MonitorNodeServiceImpl;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Service("processChangeService")
public class ProcessChangeServiceImpl extends BaseServiceImpl implements ProcessChangeService {
    /**
     * 过程处理dao
     */
    @Resource
    private ProcessChangeDao dao;
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

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public void saveBatch(List plist, String unitcode, String user, String tacheCode,String applyId) {
        dao.saveBatch(plist, unitcode, user, tacheCode,applyId);
    }

    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        //StringBuffer sql = new StringBuffer("SELECT U.* FROM view_bus_special_factor_change U");
        StringBuffer sql = new StringBuffer("SELECT U.* FROM view_bus_special_elements_ch U");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public void copyMonitorNode(String busCode, String userId, String applyId) {
        dao.copyMonitorNode(busCode, userId, applyId);
    }

    @Override
    public BusProcessInfo getProcessByCode(String processCode, String applyId) {
        return dao.getProcessByCode(processCode, applyId);
    }

    @Override
    public List<Map<String, Object>> findByProcessCode(String processCode, String applyId) {
        String sql="select r.*,a.dic_code,a.dic_name ";
        sql+=" from t_lcjc_bus_ruleconfig_change r left join ";
        sql+="(select dic_code,dic_name from t_msjw_system_dictionary t where t.type_id in(";
        sql+="select ty.type_id from t_msjw_system_dictype ty where type_code='EsuperType')) a ";
        sql+="on r.analysis_type=a.dic_code  where r.PROCESS_CODE=? and apply_id=? ORDER BY update_TIME ASC";
        
        //String sql = "select * from t_lcjc_bus_ruleconfig_change r WHERE";
        //sql += " PROCESS_CODE=? and apply_id=?  ORDER BY CREATE_TIME ASC";
        return dao.findBySql(sql, new Object[] { processCode, applyId }, null);
    }

    @Override
    public void changeFactorSubmit(String busCode, String userId, String applyId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");

        StringBuffer updateSql2 = new StringBuffer("update ")
                .append(" t_lcjc_bus_process_change set status=1,update_user=?,update_time=?" + "where apply_id=?");
        dao.executeSql(updateSql2.toString(), new Object[] { userId, date, applyId });
        // 更新审核表
        Map<String, Object> audit = this.getByJdbc("T_LCJC_BUS_AUDIT_CHANGE", new String[] { "APPLY_ID", 
            "BUS_CODE","CONFIG_CODE" }, new Object[] { applyId, busCode, "JCJDYYS" });
        if (audit == null) {
          //往审核表插入记录
            Map<String, Object> reqData =new HashMap<String, Object>();
            reqData.put("config_name","监察节点及要素");
            reqData.put("tree_sn", "1");
            reqData.put("status", "1");
            reqData.put("CREATE_TIME",date);
            reqData.put("CREATE_USER",userId);
            reqData.put("UPDATE_TIME", date);
            reqData.put("UPDATE_USER", userId);
            reqData.put("BUS_CODE",busCode);
            reqData.put("CONFIG_CODE","JCJDYYS");
            reqData.put("APPLY_ID", applyId);
            dao.saveOrUpdate(reqData,"T_LCJC_BUS_AUDIT_CHANGE",null);
        } else {
//            audit.put("STATUS", "1");
//            audit.put("UPDATE_TIME", date);
//            audit.put("UPDATE_USER", userId);
//            this.saveOrUpdate(audit, "T_LCJC_BUS_AUDIT_CHANGE", audit.get("AUDIT_ID").toString());
            StringBuffer sql = new StringBuffer("update T_LCJC_BUS_AUDIT_CHANGE set status=?"
                    + ",update_user=?,update_time=? where bus_code=? and config_code like '%JCJDYYS' and apply_id=?");
            dao.executeSql(sql.toString(), new Object[] { "1", userId, date, busCode,applyId});
        }
//        StringBuffer sql = new StringBuffer("update t_lcjc_bus_audit set status=?"
//                + ",update_user=?,update_time=? where bus_code=? and config_code=?");
//        dao.executeSql(sql.toString(), new Object[] { "1", userId, date, busCode, "JCJDYYS" });
    }

    @Override
    public void monitorNodePassAudit(String state, String busCode, String userId, String applyId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 过程点状态
        StringBuffer updateSql2 = new StringBuffer("update  ").append(" t_lcjc_bus_process set status=? "
                + "where apply_id=?");
        dao.executeSql(updateSql2.toString(), new Object[] { state, applyId });
        // 业务专项状态提交
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL set change_flag=?,update_user=?,update_time=?" + "where bus_code=?");
        dao.executeSql(bsql.toString(), new Object[] { "-1", userId, date, busCode });
    }

    @Override
    public void saveOrUpdateBatch(Map<String, Object> variables) {
      //字段基本信息
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String,Object> formMap = new HashMap<String, Object>();
        for(String str : formDatas.split("&")){
            String[] strs = str.split("=");
            if(strs.length > 1){
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String processCode = String.valueOf(formMap.get("PROCESS_CODE"));
        String applyId = String.valueOf(formMap.get("APPLY_ID"));
        String[] substr=processCode.split("\\.");
        BusSpecialInfo bus=flowChartService.getBusByBusCode(substr[0]);
        if(StringUtils.isEmpty(processCode) || "null".equals(processCode)){
            log.error("要素信息新增修改：过程编码为空！ERROR！");
        }
        //可编剧表格的信息
        String jsonDatas = String.valueOf(variables.get("jsonDatas"));        
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);
        String insertedJson = String.valueOf(jsonMap.get("inserted"));
        String updatedJson = String.valueOf(jsonMap.get("updated"));
        String deletedJson = String.valueOf(jsonMap.get("deleted"));
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        //新增
        if(!"[]".equals(insertedJson)){
            List<Map<String,Object>> insertedList = JSON.parseObject(insertedJson,
                    new TypeReference<List<Map<String,Object>>>(){});
            for (Map<String,Object> insertMap : insertedList) {
                insertMap.put("BUSSYS_UNITCODE", bus.getUnitCode());
                insertMap.put("PROCESS_CODE", processCode);
                insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("CREATE_USER", user.getUserId());
                insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_USER", user.getUserId());
                insertMap.put("STATUS", 0);
                insertMap.put("APPLY_ID", applyId);
                String nextRuleId=UUIDGenerator.getUUID();
                insertMap.put("RULE_ID",nextRuleId);
                String recordId =  this.saveOrUpdate(insertMap, "T_LCJC_BUS_RULECONFIG_CHANGE", null);
                sysLogService.saveLog("新增了ID为[" + recordId + "]的要素信息记录", SysLogService.OPERATE_TYPE_ADD);
            }
        }
        //修改
        if(!"[]".equals(updatedJson)){
            List<Map<String,Object>> updatedList = JSON.parseObject(updatedJson,
                    new TypeReference<List<Map<String,Object>>>(){});
            for (Map<String,Object> updateMap : updatedList) {
                updateMap.put("PROCESS_CODE", processCode);
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", user.getUserId());
                String serialId = String.valueOf(updateMap.get("CHANGE_ID"));
                String recordId =  this.saveOrUpdate(updateMap, "T_LCJC_BUS_RULECONFIG_CHANGE", serialId);
                sysLogService.saveLog("更新了ID为[" + recordId + "]的要素信息记录", SysLogService.OPERATE_TYPE_EDIT);
            }
        }
        //删除
        if(!"[]".equals(deletedJson)){
            List<Map<String,Object>> deletedList = JSON.parseObject(deletedJson,
                    new TypeReference<List<Map<String,Object>>>(){});  
            for (Map<String,Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("CHANGE_ID"));
                this.remove("T_LCJC_BUS_RULECONFIG_CHANGE", "CHANGE_ID", new String[]{serialId});
                sysLogService.saveLog("删除了ID为[" + serialId + "]的要素信息记录", SysLogService.OPERATE_TYPE_DEL);
            }
        }
    }

    @Override
    public void updateByProcessCode(String processCode,
            String userId, String state,String applyId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update T_LCJC_BUS_RULECONFIG_change set is_valid=?"
                + ",update_user=?,update_time=? where process_code=? and apply_id=?");
        dao.executeSql(updateSql.toString(), new Object[] {state, userId, date,processCode,applyId});
    }

    @Override
    public List<Map<String, Object>> findMonitorNodeByTCode(String tacheCode,
            String applyId) {
        String sql = "select distinct(process_code) node_code from t_lcjc_bus_process_change t ";
        sql += " where TACHE_CODE=? and apply_id=? and IS_MONITORNODE='1' ";
        return dao.findBySql(sql, new Object[] { tacheCode,applyId}, null);
    }

}
