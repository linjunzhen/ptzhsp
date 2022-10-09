/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.AllMaterialsDao;
import net.evecom.platform.flowchart.service.AllMaterialsService;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 负责业务梳理材料审核过程各个业务材料的加载业务层
 * @author Water Guo
 * @created 2015-8-31 下午5:17:06
 */
@Service("allMaterialsService")
public class AllMaterialsServiceImpl extends BaseServiceImpl 
     implements AllMaterialsService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(AllMaterialsServiceImpl.class);
    /**
     * @Resource dao
     */
    @Resource
    private AllMaterialsDao dao;
    
    /**
     * 描述 getEntityDao
     * @author Water Guo
     * @created 2015-8-31 下午5:28:41
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息（从历史表中获取相应的环节信息）
     * @author Water Guo
     * @created 2015-8-31 下午5:36:55
     * @param applyId
     * @param buscode
     * @return
     */
    @Override
    public List<TacheFlow> getFlowByApplyId(String applyId, String buscode) {
        return dao.getFlowByApplyId(applyId,buscode);
    }
    
    /**
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息（从变更表中获取相应的环节信息）
     * @author Water Guo
     * @created 2015-8-31 下午5:36:55
     * @param applyId
     * @param buscode
     * @return
     */
    @Override
    public List<TacheFlow> getChangeFlowByApplyId(String applyId, String buscode) {
        return dao.getChangeFlowByApplyId(applyId,buscode);
    }

    /**
     * 描述 从历史表中和操作申报号找到该对接专项的基本信息（业务梳理）
     * @author Water Guo
     * @created 2015-10-10 下午11:26:29
     * @param buscode
     * @param applyId
     * @return
     */
    public BusSpecialInfo getBusByBusCode(String buscode, String applyId) {
        Map<String,Object> map = this.getByJdbc("T_LCJC_BUS_SPECIAL_HISTORY", new String[]{
            "APPLY_ID","BUS_CODE"}, new String[]{applyId,buscode});
        BusSpecialInfo bus = null;
        if (map != null) {
            bus = new BusSpecialInfo();
            bus.setBusCode((String) map.get("bus_code"));
            bus.setBusName((String) map.get("bus_name"));
            bus.setStatus(Integer.valueOf(String.valueOf(map.get("version"))));
            bus.setApplyId((String) map.get("apply_id"));
            bus.setUnitCode(String.valueOf(map.get("UNIT_CODE")));
        }
        return bus;
    }
    
    /**
     * 描述 从变更表中和操作申报号找到该对接专项的基本信息（业务变更）
     * @author Water Guo
     * @created 2015-10-10 下午11:25:51
     * @param buscode
     * @param applyId
     * @return
     */
    @Override
    public BusSpecialInfo getChangeBusByBusCode(String buscode, String applyId) {
        Map<String,Object> map = this.getByJdbc("T_LCJC_BUS_SPECIAL_CHANGE", new String[]{
            "APPLY_ID","BUS_CODE"}, new String[]{applyId,buscode});
        BusSpecialInfo bus = null;
        if (map != null) {
            bus = new BusSpecialInfo();
            bus.setBusCode((String) map.get("bus_code"));
            bus.setBusName((String) map.get("bus_name"));
            bus.setStatus(Integer.valueOf(String.valueOf(map.get("version"))));
            bus.setApplyId((String) map.get("apply_id"));
            bus.setUnitCode(String.valueOf(map.get("UNIT_CODE")));
        }
        return bus;
    }
    

    /**
     * 
     * 描述 从历史表中查询操作申报号相关的监察规则数据（历史表）
     * @author Water Guo
     * @created 2015-10-10 下午11:27:03
     * @param filter
     * @param appliyId
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> listMaterialsRuleDatagrid(SqlFilter filter,
            String appliyId, String processCode) {
        filter.addFilter("Q_T1.APPLY_ID_EQ", appliyId);
        filter.addFilter("Q_T1.PROCESS_CODE_EQ", processCode);
//        String[] substr = processCode.split("\\.");
//        String tacheCode=substr[0]+"."+substr[1];
//        filter.addFilter("Q_T1.TACHE_CODE_EQ", tacheCode);
        filter.addSorted("T2.CREATE_TIME", "ASC");
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_PROCESS_HISTORY T1 ");
        sql.append(" INNER JOIN T_LCJC_BUS_RULECONFIG_HISTORY T2 ON T1.PROCESS_CODE = T2.PROCESS_CODE ");
        sql.append(" WHERE T1.APPLY_ID = T2.APPLY_ID ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 描述（数据源变更表）根据操作申报号和过程编号查询该过程节点的监察点相关信息（要素和过程）
     * @author Water Guo
     * @created 2015-10-11 下午12:56:40
     * @param filter
     * @param appliyId
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> listMaterialsChangeRuleDatagrid(SqlFilter filter,
            String appliyId, String processCode) {
        filter.addFilter("Q_T1.APPLY_ID_EQ", appliyId);
        filter.addFilter("Q_T1.PROCESS_CODE_EQ", processCode);
        filter.addSorted("T2.CREATE_TIME", "ASC");
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_PROCESS_CHANGE T1 ");
        sql.append(" INNER JOIN T_LCJC_BUS_RULECONFIG_CHANGE T2 ON T1.PROCESS_CODE = T2.PROCESS_CODE ");
        sql.append(" WHERE T1.APPLY_ID = T2.APPLY_ID ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述获得该专项第一个环节的第一个监察节点的过程编码
     * @author Water Guo
     * @created 2015-9-11 下午4:26:14
     * @param tacheCode
     * @param applyId
     * @return
     */
    public Map<String, Object> getFirstProccess(String tacheCode, String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_PROCESS_HISTORY T ");
        sql.append(" WHERE T.APPLY_ID = ? AND T.TACHE_CODE = ? ORDER BY T.TREE_SN ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{applyId,tacheCode}, null);
        return list.isEmpty() ? null : list.get(0);
    }
    /**
     * 描述 从变更表中获得该专项第一个环节的第一个监察节点的过程编码
     * @author Water Guo
     * @created 2015-10-11 下午4:55:06
     * @param tacheCode
     * @param applyId
     * @return
     */
    public Map<String, Object> getFirstChangeProccess(String tacheCode, String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_PROCESS_CHANGE T ");
        sql.append(" WHERE T.APPLY_ID = ? AND T.TACHE_CODE = ? ORDER BY T.TREE_SN ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{applyId,tacheCode}, null);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 描述从变更表中获得当前申报号当前环节的流程图信息
     * @author Water Guo
     * @created 2015-10-20 下午10:37:21
     * @param tacheCode
     * @param applyId
     * @return
     */
    @Override
    public TacheFlow getFlowByTacheCodeFromChange(String tacheCode, String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_TACHE_CHANGE U");
        sql.append(" WHERE U.TACHE_CODE= ? AND U.APPLY_ID = ? ORDER BY U.TREE_SN ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{tacheCode,applyId}, null);
        TacheFlow flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new TacheFlow();
            flow.setTacheId((String) map.get("tache_id"));
            flow.setFlowInfo((String) map.get("flow_info"));
            flow.setFlowSvg((String) map.get("flow_svg"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setBusCode((String) map.get("BUS_CODE"));
            flow.setTreeSn(String.valueOf(map.get("tree_sn")));
            flow.setTacheName((String) map.get("tache_name"));
            flow.setFlowHeight((String) map.get("flow_height"));
        }else{
            log.error("从变更表中获得当前申报号当前环节的流程图信息  list is empty!! ");
        }
        return flow;
    }

    /**
     * 描述 从历史表中获得当前申报号当前环节的流程图信息
     * @author Water Guo
     * @created 2015-10-20 下午10:38:10
     * @param tacheCode
     * @param lastApplyId
     * @return
     */
    @Override
    public TacheFlow getFlowByTacheCodeFromHistory(String tacheCode, String lastApplyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_TACHE_HISTORY U");
        sql.append(" WHERE U.TACHE_CODE= ? AND U.APPLY_ID = ? ORDER BY U.TREE_SN ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{tacheCode,lastApplyId}, null);
        TacheFlow flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new TacheFlow();
            flow.setTacheId((String) map.get("tache_id"));
            flow.setFlowInfo((String) map.get("flow_info"));
            flow.setFlowSvg((String) map.get("flow_svg"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setBusCode((String) map.get("BUS_CODE"));
            flow.setTreeSn(String.valueOf(map.get("tree_sn")));
            flow.setTacheName((String) map.get("tache_name"));
            flow.setFlowHeight((String) map.get("flow_height"));
        }else{
            log.error("从历史表中获得当前申报号当前环节的流程图信息  list is empty!! ");
        }
        return flow;
    }

    /**
     * 描述根据环节编码查询过程 (变更表) 
     * @author Water Guo
     * @created 2015-10-20 下午10:55:58
     * @param tacheCode
     * @param applyId
     * @return
     */
    @Override
    public List<Map<String, Object>> findMonitorNodeByTCodeFromChange(String tacheCode, String applyId) {
        String sql = "SELECT DISTINCT(PROCESS_CODE) NODE_CODE FROM T_LCJC_BUS_PROCESS_CHANGE T ";
        sql += " WHERE TACHE_CODE=? AND APPLY_ID = ? AND IS_MONITORNODE='1' ";
        return dao.findBySql(sql, new Object[] { tacheCode, applyId }, null);
    }

    /**
     * 描述 根据环节编码查询过程 (历史表) 
     * @author Water Guo
     * @created 2015-10-20 下午10:56:04
     * @param tacheCode
     * @param lastApplyId
     * @return
     */
    @Override
    public List<Map<String, Object>> findMonitorNodeByTCodeFromHistory(String tacheCode, String lastApplyId) {
        String sql = "SELECT DISTINCT(PROCESS_CODE) NODE_CODE FROM T_LCJC_BUS_PROCESS_HISTORY T ";
        sql += " WHERE TACHE_CODE=? AND APPLY_ID = ? AND IS_MONITORNODE='1' ";
        return dao.findBySql(sql, new Object[] { tacheCode,lastApplyId }, null);
    }

    @Override
    public Map<String, Object> queryFirstProccess(String tacheCode,
            String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_PROCESS_HISTORY T ");
        sql.append(" WHERE T.APPLY_ID = ? AND T.TACHE_CODE = ? ORDER BY T.process_code ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{applyId,tacheCode}, null);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Map<String, Object> queryFirstChangeProccess(String tacheCode,
            String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_PROCESS_CHANGE T ");
        sql.append(" WHERE T.APPLY_ID = ? AND T.TACHE_CODE = ? ORDER BY T.PROCESS_CODE ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{applyId,tacheCode}, null);
        return list.isEmpty() ? null : list.get(0);
    }

}
