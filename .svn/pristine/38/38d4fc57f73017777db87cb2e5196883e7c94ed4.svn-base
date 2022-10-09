/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchange.dao.BusRuleChangeDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
/**
 * 描述 监察规则
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busRuleChangeDao")
public class BusRuleChangeDaoImpl extends BaseDaoImpl implements BusRuleChangeDao{
    /**
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByRuleId(String ruleId){
        Map<String, Object> processMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("select T.*,A.PROCESS_NAME,B.TACHE_CODE,B.TACHE_NAME,C.BUS_CODE,");
        sql.append(" C.BUS_NAME,D.DEPART_NAME UNIT_NAME from T_LCJC_BUS_RULECONFIG_CHANGE T" );
        sql.append(" LEFT JOIN T_LCJC_BUS_PROCESS_CHANGE A ");
        sql.append(" ON T.PROCESS_CODE=A.PROCESS_CODE AND T.APPLY_ID=A.APPLY_ID ");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE_CHANGE B ON A.TACHE_CODE=B.TACHE_CODE AND B.APPLY_ID=A.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL_CHANGE C ON B.BUS_CODE=C.BUS_CODE AND B.APPLY_ID=C.APPLY_ID");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.BUSSYS_UNITCODE");
        sql.append(" WHERE T.CHANGE_ID=?");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{ruleId}, null);
        if(list.size()>0){
            processMap = list.get(0);
        }
        return processMap;
    }
    /**
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessCode(String processCode){
        Map<String, Object> processMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("select T.*,A.PROCESS_NAME,B.TACHE_NAME,C.BUS_NAME,D.UNIT_NAME");
        sql.append(" from T_LCJC_BUS_RULECONFIG_CHANGE T" );
        sql.append(" LEFT JOIN T_LCJC_BUS_PROCESS_CHANGE A ON T.PROCESS_CODE=A.PROCESS_CODE AND T.APPLY_ID=A.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE_CHANGE B ON A.TACHE_CODE=B.TACHE_CODE AND B.APPLY_ID=A.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL_CHANGE C ON B.BUS_CODE=C.BUS_CODE AND B.APPLY_ID=C.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_SYSTEM_BUSUNIT D ON D.UNIT_CODE=T.BUSSYS_UNITCODE");
        sql.append(" WHERE T.PROCESS_CODE=?");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{processCode}, null);
        if(list.size()>0){
            processMap = list.get(0);
        }
        return processMap;
    }
    /**
     * 描述描述通过变更id及申报号删除隐藏开始过程编码记录
     * @author Toddle Chen
     * @created Oct 10, 2015 5:01:58 PM
     * @param changeId
     * @param applyId
     */
    public void delStartProcessByRuleId(String changeId,String applyId){
        StringBuffer sql = new StringBuffer("DELETE FROM T_LCJC_BUS_RULECONFIG_CHANGE T");
        sql.append(" WHERE T.IS_SHOW=? AND T.APPLY_ID=? AND T.JUDGE_CONDITIONS IN (");
        sql.append(" SELECT JUDGE_CONDITIONS FROM T_LCJC_BUS_RULECONFIG_CHANGE ");
        sql.append(" WHERE CHANGE_ID=?");
        sql.append(" )");
        this.executeSql(sql.toString(), new Object[]{0,applyId,changeId});
    }
    /**
     * 描述 通过业务专项编码获取所属监察字段是否完整
     * @author Toddle Chen
     * @created Sep 14, 2015 6:11:05 PM
     * @param tacheCode
     * @return
     */    
    public boolean checkColumnByBusCode(String busCode,String applyId){
        StringBuffer sql = new StringBuffer("select count(*) from T_LCJC_BUS_RULECONFIG_CHANGE T " );
        sql.append(" WHERE T.JUDGE_CONDITIONS IS NULL AND T.APPLY_ID=? AND T.PROCESS_CODE IN (");
        sql.append(" SELECT PROCESS_CODE FROM T_LCJC_BUS_PROCESS_CHANGE WHERE IS_MONITORNODE=1 ");
        sql.append(" AND IS_VALID=1 AND APPLY_ID=? AND TACHE_CODE IN( ");
        sql.append(" SELECT TACHE_CODE FROM T_LCJC_BUS_TACHE_CHANGE WHERE IS_VALID=1 AND BUS_CODE=? ");
        sql.append(" AND APPLY_ID=? ))");
        int n_count = jdbcTemplate.queryForInt(sql.toString(), new Object[]{applyId,applyId,busCode,applyId}); 
        if(n_count > 0){
            return false;
        }
        return true;
    }
    /**
     * 描述通过业务专项获取业务环节
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBus(String busCode,String applyId){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        StringBuffer strBuffer = new StringBuffer("SELECT T.* FROM T_LCJC_BUS_TACHE_CHANGE T");
        strBuffer.append(" WHERE T.BUS_CODE=? AND T.APPLY_ID=?");
        strBuffer.append(" ORDER BY T.TACHE_CODE");
        if(StringUtils.isNotEmpty(busCode)){
            list = this.findBySql(strBuffer.toString(), new Object[]{busCode,applyId}, null);
        }       
        return list;
    }
    /**
     * 描述通过业务环节获取过程编码
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param tacheCode
     * @return
     */
    public List<Map<String, Object>> findProcessByTache(String tacheCode,String applyId){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        StringBuffer strBuffer = new StringBuffer("SELECT T.* FROM T_LCJC_BUS_PROCESS_CHANGE T");
        strBuffer.append(" WHERE T.TACHE_CODE=? AND T.APPLY_ID=? ");
        strBuffer.append(" ORDER BY T.PROCESS_CODE");
        if(StringUtils.isNotEmpty(tacheCode)){
            list = this.findBySql(strBuffer.toString(), new Object[]{tacheCode,applyId}, null);
        }       
        return list;
    }
}
