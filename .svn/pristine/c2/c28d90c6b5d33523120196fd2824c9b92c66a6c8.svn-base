/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.ApasInfoDao;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
@Repository("apasInfoDao")
public class ApasInfoDaoImpl extends BaseDaoImpl implements ApasInfoDao{
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ApasInfoDaoImpl.class);
    
    @Override
    public int getCount(String sql){
        int count =this.jdbcTemplate.queryForInt(sql,null);  
        return count;
    }

  /*  @Override
    public List<Map<String, Object>> findExports(SqlFilter sqlFilter){
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
            sql.append(" and T.SERVICETYPE='");
            sql.append(type);
            sql.append("' ");
            where.append(" and T.SERVICETYPE='");
            where.append(type);
            where.append("' ");
        }
        if(StringUtils.isNotEmpty(dept)){
            sql.append(" and T.RECEIVEDEPT like '%");
            sql.append(dept);
            sql.append("%' ");
            where.append(" and T.RECEIVEDEPT like '%");
            where.append(dept);
            where.append("%' ");
        }

        ResultSet rs = null; 
        Statement stat = null;
        
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        try {
            stat=getSessionFactory().getCurrentSession().connection()
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                stat.setMaxRows(10000); 
                stat.setFetchSize(1000); 
                rs = stat.executeQuery(sql.toString());  
            while (rs.next()) {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("SERVICENAME", rs.getObject(1));
                map.put("PROJECTNAME", rs.getObject(2));
                map.put("APPLYNAME", rs.getObject(3));
                map.put("CONTACTMAN", rs.getObject(4));
                map.put("MOBILEPHONE", rs.getObject(5));
                map.put("ADDRESS", rs.getObject(6));
                map.put("RECEIVEUSER", rs.getObject(7));
                map.put("CREATETIME", rs.getObject(8));
                map.put("PROMISEENDDAY", rs.getObject(9));
                map.put("TRANSACTTIME", rs.getObject(10));
                map.put("HANDLESTATE", rs.getObject(11));
                map.put("RECEIVEDEPT", rs.getObject(12));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            try {
                if (stat!=null) {
                    stat.close();
                    rs.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
        return list;
    }
    */
    

}
