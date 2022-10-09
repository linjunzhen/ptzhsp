/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.ApasInfoDao;
import net.evecom.platform.wsbs.service.ApasInfoService;

/**
 * 描述  旧系统历史数据
 * @author Sundy Sun
 * @version v1.0
 */
@Service("apasInfoService")
public class ApasInfoServiceImpl extends BaseServiceImpl implements ApasInfoService{

    /**
     * dao
     */
    @Resource
    private ApasInfoDao dao;
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select T.UNID,T.SERVICENAME,T.SERVICETYPE,T.PROJECTNAME,T.APPLYNAME,")
        .append(" T.MOBILEPHONE,T.CONTACTMAN,T.ADDRESS,T.RECEIVEUSER,T.CREATETIME,T.PROMISEENDDAY,")
        .append(" T.TRANSACTTIME,T.HANDLESTATE,T.RECEIVEDEPT")
        .append(" from T_WSBS_APASINFO T where 1=1 ");
        //sql.append(" ORDER BY TRANSACTTIME,CREATETIME desc ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        
        return list;
    }
    @Override
    public List<Map<String, Object>> findExBySqlFilter(SqlFilter sqlFilter) {
        //List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select rownum CURROW,T.SERVICENAME,T.PROJECTNAME,T.APPLYNAME,T.CONTACTMAN,T.MOBILEPHONE,")
        .append(" T.ADDRESS,T.RECEIVEUSER,T.CREATETIME,T.PROMISEENDDAY,")
        .append(" T.TRANSACTTIME,T.HANDLESTATE,T.RECEIVEDEPT")
        .append(" from T_WSBS_APASINFO T where 1=1 ");
        Map<String, Object> pm=sqlFilter.getQueryParams();
        StringBuffer where = new StringBuffer(" ");
        String type=(String) pm.get("Q_T.SERVICETYPE_EQ");
        String dept=(String) pm.get("Q_T.RECEIVEDEPT_LIKE");
        String begin=(String) pm.get("Q_T.CREATETIME_BEGIN");
        String end=(String) pm.get("Q_T.CREATETIME_END");
        if(StringUtils.isNotEmpty(type)){
            sql.append(" and T.SERVICETYPE='"+type+"' ");
            where.append(" and T.SERVICETYPE='"+type+"' ");
        }
        if(StringUtils.isNotEmpty(dept)){
            sql.append(" and T.RECEIVEDEPT like '%"+dept+"%' ");
            where.append(" and T.RECEIVEDEPT like '%"+dept+"%' ");
        }
        if(StringUtils.isNotEmpty(begin)){
            sql.append(" and T.CREATETIME>='"+begin+" 00:00:00' ");
            where.append(" and T.CREATETIME>='"+begin+" 00:00:00' ");
        }
        if(StringUtils.isNotEmpty(end)){
            sql.append(" and T.CREATETIME<='"+end+" 23:59:59' ");
            where.append(" and T.CREATETIME<='"+end+" 23:59:59' ");
        }
        
        //String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        
        String countsql=" select count(*) from T_WSBS_APASINFO T where 1=1 "+where.toString();
        int count = dao.getCount(countsql);
        if(count>10000){
            //List<Map<String, Object>> countlist=new ArrayList<Map<String,Object>>(count);
            StringBuffer rowsql=new StringBuffer(" ");
            rowsql.append(" and rownum<=10000 ");
            rowsql.append(" order by T.CREATETIME DESC ");
            List<Map<String, Object>> list;
//            List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(20000);
            list =dao.findBySql(sql.toString()+rowsql,null, null);
            //countlist.addAll(list);
            
            return list;
        }else{
            sql.append(" order by T.CREATETIME DESC ");
            List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
            return list;
        }
    }
    @Override
    public List<Map<String, Object>> findExByBatch(SqlFilter sqlFilter,
            int start, int end) {
        //List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select rownum CURROW,T.SERVICENAME,T.PROJECTNAME,T.APPLYNAME,T.CONTACTMAN,T.MOBILEPHONE,")
        .append(" T.ADDRESS,T.RECEIVEUSER,T.CREATETIME,T.PROMISEENDDAY,")
        .append(" T.TRANSACTTIME,T.HANDLESTATE,T.RECEIVEDEPT")
        .append(" from T_WSBS_APASINFO T where 1=1 ");
        Map<String, Object> pm=sqlFilter.getQueryParams();
        StringBuffer where = new StringBuffer(" ");
        String type=(String) pm.get("Q_T.SERVICETYPE_EQ");
        String dept=(String) pm.get("Q_T.RECEIVEDEPT_LIKE");
        if(StringUtils.isNotEmpty(type)){
            sql.append(" and T.SERVICETYPE='"+type+"' ");
            where.append(" and T.SERVICETYPE='"+type+"' ");
        }
        if(StringUtils.isNotEmpty(dept)){
            sql.append(" and T.RECEIVEDEPT like '%"+dept+"%' ");
            where.append(" and T.RECEIVEDEPT like '%"+dept+"%' ");
        }

        StringBuffer rowsql2=new StringBuffer(" ");
        sql.append(" order by T.CREATETIME DESC ");
        rowsql2.append(" select * from (").append(sql).append(") a where ");
        if(start==0){
            rowsql2.append(" a.CURROW<="+end);
        }else{
            rowsql2.append(" a.CURROW>"+start+"  and a.CURROW<="+end);
        }
        List<Map<String, Object>> list;
        list=dao.findBySql(rowsql2.toString(),null, null);
        return list;
    }
    @Override
    public int getCount(SqlFilter sqlFilter) {
        //List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select rownum CURROW,T.SERVICENAME,T.PROJECTNAME,T.APPLYNAME,T.CONTACTMAN,T.MOBILEPHONE,")
        .append(" T.ADDRESS,T.RECEIVEUSER,T.CREATETIME,T.PROMISEENDDAY,")
        .append(" T.TRANSACTTIME,T.HANDLESTATE,T.RECEIVEDEPT")
        .append(" from T_WSBS_APASINFO T where 1=1 ");
        Map<String, Object> pm=sqlFilter.getQueryParams();
        StringBuffer where = new StringBuffer(" ");
        String type=(String) pm.get("Q_T.SERVICETYPE_EQ");
        String dept=(String) pm.get("Q_T.RECEIVEDEPT_LIKE");
        if(StringUtils.isNotEmpty(type)){
            sql.append(" and T.SERVICETYPE='"+type+"' ");
            where.append(" and T.SERVICETYPE='"+type+"' ");
        }
        if(StringUtils.isNotEmpty(dept)){
            sql.append(" and T.RECEIVEDEPT like '%"+dept+"%' ");
            where.append(" and T.RECEIVEDEPT like '%"+dept+"%' ");
        }
        
        String countsql=" select count(*) from T_WSBS_APASINFO T where 1=1 "+where.toString();
        int count = dao.getCount(countsql);
        return count;
    }

}
