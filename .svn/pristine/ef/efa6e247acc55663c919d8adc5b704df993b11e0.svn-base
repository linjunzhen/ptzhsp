/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchart.dao.BusRuleDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
/**
 * 描述 监察规则
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busRuleDao")
public class BusRuleDaoImpl extends BaseDaoImpl implements BusRuleDao{
    /**
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByRuleId(String ruleId){
        Map<String, Object> processMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("select T.*,A.PROCESS_NAME,B.TACHE_CODE,B.TACHE_NAME,");
        sql.append(" C.BUS_CODE,C.BUS_NAME,D.DEPART_CODE UNIT_CODE,D.DEPART_NAME UNIT_NAME"
                + " from T_LCJC_BUS_RULECONFIG T");
        sql.append(" LEFT JOIN T_LCJC_BUS_PROCESS A ON T.PROCESS_CODE=A.PROCESS_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE B ON A.TACHE_CODE=B.TACHE_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL C ON B.BUS_CODE=C.BUS_CODE");
        sql.append(" LEFT JOIN t_msjw_system_department D ON D.DEPART_CODE=T.BUSSYS_UNITCODE");
        sql.append(" WHERE T.RULE_ID=?");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{ruleId}, null);
        if(list.size()>0){
            processMap = list.get(0);
        }
        return processMap;
    }
    /**
     * 描述通过业务专项获取业务环节
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBus(String busCode){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        StringBuffer strBuffer = new StringBuffer("SELECT T.* FROM T_LCJC_BUS_TACHE T");
        strBuffer.append(" WHERE T.BUS_CODE=?");
        strBuffer.append(" ORDER BY T.TACHE_CODE");
        if(StringUtils.isNotEmpty(busCode)){
            list = this.findBySql(strBuffer.toString(), new Object[]{busCode}, null);
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
    public List<Map<String, Object>> findProcessByTache(String tacheCode){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        StringBuffer strBuffer = new StringBuffer("SELECT T.* FROM T_LCJC_BUS_PROCESS T");
        strBuffer.append(" WHERE T.TACHE_CODE=?");
        strBuffer.append(" ORDER BY T.PROCESS_CODE");
        if(StringUtils.isNotEmpty(tacheCode)){
            list = this.findBySql(strBuffer.toString(), new Object[]{tacheCode}, null);
        }       
        return list;
    }
    /**
     * 描述 通过业务专项获取所属规则是否完整
     * @author Toddle Chen
     * @created Sep 14, 2015 6:11:05 PM
     * @param tacheCode
     * @return
     */    
    public boolean checkRuleByBusCode(String busCode){
        StringBuffer sql = new StringBuffer("select count(*) from T_LCJC_BUS_RULECONFIG T" );
        sql.append(" WHERE T.JUDGE_CONDITIONS IS NULL AND T.PROCESS_CODE IN (");
        sql.append(" SELECT PROCESS_CODE FROM T_LCJC_BUS_PROCESS WHERE IS_MONITORNODE=1 ");
        sql.append(" AND IS_VALID=1 AND TACHE_CODE IN( ");
        sql.append(" SELECT TACHE_CODE FROM T_LCJC_BUS_TACHE WHERE IS_VALID=1 AND BUS_CODE=? ");
        sql.append(" ))");
        int n_count = jdbcTemplate.queryForInt(sql.toString(), new Object[]{busCode}); 
        if(n_count > 0){
            return false;
        }
        return true;
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
        sql.append(" from T_LCJC_BUS_RULECONFIG T" );
        sql.append(" LEFT JOIN T_LCJC_BUS_PROCESS A ON T.PROCESS_CODE=A.PROCESS_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE B ON A.TACHE_CODE=B.TACHE_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL C ON B.BUS_CODE=C.BUS_CODE");
        sql.append(" LEFT JOIN T_LCJC_SYSTEM_BUSUNIT D ON D.UNIT_CODE=T.BUSSYS_UNITCODE");
        sql.append(" WHERE T.PROCESS_CODE=?");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{processCode}, null);
        if(list.size()>0){
            processMap = list.get(0);
        }
        return processMap;
    }
    /**
     * 描述通过规则表达式删除隐藏开始过程编码记录
     * @author Toddle Chen
     * @created Oct 10, 2015 3:55:09 PM
     * @param ruleId
     */
    public void delStartProcessByRuleId(String ruleId){
        StringBuffer sql = new StringBuffer("DELETE FROM T_LCJC_BUS_RULECONFIG T");
        sql.append(" WHERE T.IS_SHOW=? AND T.JUDGE_CONDITIONS IN (");
        sql.append(" SELECT JUDGE_CONDITIONS FROM T_LCJC_BUS_RULECONFIG ");
        sql.append(" WHERE RULE_ID=?");
        sql.append(" )");
        this.executeSql(sql.toString(), new Object[]{0,ruleId});
    }
}
