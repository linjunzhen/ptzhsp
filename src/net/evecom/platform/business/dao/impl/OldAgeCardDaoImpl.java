/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.business.dao.OldAgeCardDao;
import net.evecom.platform.system.model.SysUser;

import org.springframework.stereotype.Repository;

/**
 * 描述 老年人优待证业务管理Dao实现类
 * @author Bryce Zhang
 * @created 2017-5-15 上午9:06:21
 */
@Repository("oldAgeCardDao")
public class OldAgeCardDaoImpl extends BaseDaoImpl implements OldAgeCardDao{
    
    /**
     * 描述 根据sqlfilter，获取某用户办理的流程
     * @author Bryce Zhang
     * @created 2017-5-16 上午9:53:35
     * @param filter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findHandledByUser(SqlFilter filter, String userAccount){
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM ");
        sql.append("JBPM6_TASK TA WHERE TA.ASSIGNER_CODE = ? AND TA.IS_AUDITED = 1 ");
        sql.append("AND TA.TASK_STATUS != '-1' AND TA.IS_REAL_HANDLED = 1 ) ");
        sql.append("AND T.BUS_TABLENAME = 'T_BSFW_OLDAGECARD' ");
        List<Object> params = new ArrayList<Object>();
        params.add(userAccount);
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        return this.findBySql(exeSql, params.toArray(), filter.getPagingBean());
    }
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth(SqlFilter filter, SysUser loginUser){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select e.*,t.business_id,t.card_type,t.business_source,t.operator_name, ");
        sql.append("t.acceptdept_name,t.card_status from JBPM6_EXECUTION e left join T_BSFW_OLDAGECARD t ");
        sql.append("on e.bus_recordid = t.business_id where e.bus_tablename = 'T_BSFW_OLDAGECARD' ");
        //非超级管理员
        if(!loginUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            sql.append("and e.ssbmbm in (select DEP_CODE from T_MSJW_SYSTEM_SYSROLE_DATA ");
            sql.append("where ROLE_ID in (select role_id from t_msjw_system_sysuser_sysrole ");
            sql.append("where USER_ID = ?)) ");
            params.add(loginUser.getUserId());
        }
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        return this.findBySql(exeSql, params.toArray(), filter.getPagingBean());
    }
    
    /**
     * 描述 注销优待证号非cardNum的申请人证件
     * @author Bryce Zhang
     * @created 2017-5-26 下午12:42:48
     * @param idNum
     * @param cardNum
     */
    public void updateDeRegister(String idNum, String cardNum){
        StringBuffer sql = new StringBuffer("update T_BSFW_OLDAGECARD set CARD_STATUS = 1 ");
        sql.append("where CARD_NUM != ? and BUSINESS_ID in(select BUS_RECORDID from JBPM6_EXECUTION ");
        sql.append("where BUS_TABLENAME = 'T_BSFW_OLDAGECARD' and SQRSFZ = ?) and CARD_STATUS = 0 ");
        jdbcTemplate.update(sql.toString(), new Object[]{cardNum, idNum});
    }
    
    /**
     * 描述 得到绿证下一编号
     * @author Bryce Zhang
     * @created 2017-5-26 下午12:56:27
     * @return
     */
    public String getGreenCardNextNum(){
        String sql = "select S_NUM_GREENOLDAGECARD.nextval from dual ";
        int num = jdbcTemplate.queryForInt(sql);
        return String.valueOf(num);
    }
    
    /**
     * 描述 得到红证下一编号
     * @author Bryce Zhang
     * @created 2017-5-26 下午12:56:27
     * @return
     */
    public String getRedCardNextNum(){
        String sql = "select S_NUM_REDOLDAGECARD.nextval from dual ";
        int num = jdbcTemplate.queryForInt(sql);
        return String.valueOf(num);
    }
    
    /**
     * 描述 更新证件为注销
     * @author Bryce Zhang
     * @created 2017-5-27 下午5:19:51
     * @param busId
     * @param lostStateId
     * @param lostStateName
     * @param lostStatePath
     */
    public void updateUnregister(String busId, String lostStateId, String lostStateName, String lostStatePath){
        StringBuffer sql = new StringBuffer("update T_BSFW_OLDAGECARD set CARD_STATUS = 1, ");
        sql.append("LOSTSTATE_FILEID = ?, LOSTSTATE_FILENAME = ?, LOSTSTATE_PATH = ? where BUSINESS_ID = ? ");
        jdbcTemplate.update(sql.toString(), new Object[]{lostStateId, lostStateName, lostStatePath, busId});
    }
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth4Exp(SqlFilter filter, SysUser loginUser){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select e.exe_id, ");
        sql.append("decode(t.business_source, 0, '现场办理', 1, '五彩麒麟app', 2, '岚岛网格采集', '') as business_source, ");
        sql.append("e.sqrmc, e.sqrsfz, e.create_time, t.card_num, e.sqrlxdz, e.sqrsjh, ");
        sql.append("decode(t.card_type, 1, '绿证', 2, '红证', '') as card_type, t.operator_name, ");
        sql.append("t.acceptdept_name, t.gridman_name, t.business_remark, t.applicant_addr from JBPM6_EXECUTION e ");
        sql.append("left join T_BSFW_OLDAGECARD t on e.bus_recordid = t.business_id ");
        sql.append("where e.bus_tablename = 'T_BSFW_OLDAGECARD' ");
        //非超级管理员
        if(!loginUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            sql.append("and e.ssbmbm in (select DEP_CODE from T_MSJW_SYSTEM_SYSROLE_DATA ");
            sql.append("where ROLE_ID in (select role_id from t_msjw_system_sysuser_sysrole ");
            sql.append("where USER_ID = ?)) ");
            params.add(loginUser.getUserId());
        }
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        return this.findBySql(exeSql, params.toArray(), null);
    }

}
