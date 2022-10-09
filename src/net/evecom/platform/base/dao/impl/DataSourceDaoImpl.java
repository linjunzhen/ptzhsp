/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.base.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.command.HqlFilter;
import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.model.TableInfo;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.dao.DataSourceDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月17日 下午3:44:28
 */
@Repository("dataSourceDao")
public class DataSourceDaoImpl extends BaseDaoImpl implements DataSourceDao {
    /**
     * 判断一个记录是否存在
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsRecord(String tableName,String fieldName,Object fieldValue,String recordId){
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(tableName).append(" WHERE ")
        .append(fieldName).append("=? ");
        List params = new ArrayList();
        params.add(fieldValue);
        if(StringUtils.isNotEmpty(recordId)&&!recordId.equals("undefined")){
            //获取主键名称
            String pkName =  (String) this.getPrimaryKeyName(tableName).get(0);
            sql.append(" AND ").append(pkName).append("!=? ");
            params.add(recordId);
        }
        int count = this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
        if(count==0){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 判断一个流程记录状态在[1:正在办理中,2:已办结(正常结束),3:已办结(审核通过)。]是否存在相同的公司名称记录
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsFlowRecord(String tableName,String fieldName,Object fieldValue,String recordId){
        String pkName =  (String) this.getPrimaryKeyName(tableName).get(0);
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(tableName+" ts left join JBPM6_EXECUTION te on ts."+pkName+" = te.bus_recordid")
        .append(" WHERE te.RUN_STATUS in (1, 2, 3) and ")
        .append("ts."+fieldName).append("=? ");
        List params = new ArrayList();
        params.add(fieldValue);
        if(StringUtils.isNotEmpty(recordId)&&!recordId.equals("undefined")){
            //获取主键名称
            sql.append(" AND ts.").append(pkName).append("!=? ");
            params.add(recordId);
        }
        int count = this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
        if(count==0){
            return false;
        }else{
            return true;
        }
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean isExistsFlowToBdcdyhRecord(String tableName, String fieldName, Object fieldValue, String recordId) {
        // TODO Auto-generated method stub
        String pkName =  (String) this.getPrimaryKeyName(tableName).get(0);
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(tableName+" ts join JBPM6_EXECUTION te on ts."+pkName+" = te.bus_recordid")
        .append(" WHERE te.RUN_STATUS in (1, 2, 3) and ")
        .append("ts."+fieldName).append("=? ");
        StringBuffer sql1 = new StringBuffer("select count(*) from ");
        sql1.append(tableName+" ts join JBPM6_EXECUTION_EVEHIS te on ts."+pkName+" = te.bus_recordid")
        .append(" WHERE te.RUN_STATUS in (1, 2, 3) and ")
        .append("ts."+fieldName).append("=? ");
        List params = new ArrayList();
        params.add(fieldValue);
        if(StringUtils.isNotEmpty(recordId)&&!recordId.equals("undefined")){
            //获取主键名称
            sql.append(" AND ts.").append(pkName).append("!=? ");
            //获取主键名称
            sql1.append(" AND ts.").append(pkName).append("!=? ");
            params.add(recordId);
        }
        int count = this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
        int count1 = this.jdbcTemplate.queryForInt(sql1.toString(),params.toArray());
        if(count==0&&count1==0){
            return false;
        }else{
            return true;
        }
    } 
}
