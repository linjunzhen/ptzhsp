/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;    

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.web.paging.PagingBean;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 使用dbutils来操纵数据库工具类
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年10月28日 上午9:14:57
 */
public class DbUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DbUtil.class);
    /**
     * 根据数据库表名称获取列信息
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public static Set<String> getColumnNameByTableName(Connection conn,String tableName,boolean isCloseDb){
        Set<String> set = new HashSet<String>();
        StringBuffer sql = new StringBuffer("select t.COLUMN_NAME from ")
                .append("user_tab_columns t where  t.table_name=? ");
        List<Map<String,Object>> list = DbUtil.findBySql(conn, sql.toString(),new Object[]{tableName},isCloseDb);
        for(Map<String,Object> map:list){
            set.add(map.get("COLUMN_NAME").toString());
        }
        return set;
    }
    
    /**
     * 插入数据库
     * @param conn
     * @param tableName
     * @param busRecord
     * @param seqName
     * @param assignId
     * @param isClose
     * @return
     */
    public static String insert(Connection conn,String tableName,Map<String,Object> busRecord,
            String seqName,String assignId,boolean isClose){
        Set<String> columns =  DbUtil.getColumnNameByTableName(conn, tableName, false);
        // 获取主键名称
        String primaryKeyName = DbUtil.getPrimaryKeyName(conn, tableName,false).get(0);
        String nextSeq= "";
        if(StringUtils.isNotEmpty(seqName)){
            String getNextSeq = "select "+seqName+".nextval FROM DUAL";
            Object nextObj = DbUtil.getObjectBySql(conn, getNextSeq,null, false);
            nextSeq = nextObj.toString();
        }else if(StringUtils.isNotEmpty(assignId)){
            nextSeq = assignId;
        }else{
            nextSeq = UUIDGenerator.getUUID();
        }
        Map<String,Object> insertMap = new HashMap<String,Object>();
        insertMap.put(primaryKeyName, nextSeq);
        Iterator it = busRecord.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String fieldName = (String) entry.getKey();
            Object val = entry.getValue();
            if (columns.contains(fieldName.toUpperCase()) && val != null) {
                if (!insertMap.containsKey(fieldName.toUpperCase())) {
                    insertMap.put(fieldName.toUpperCase(),val);
                }
            }
        }
        StringBuffer sql = new StringBuffer("insert into ")
                .append(tableName.toUpperCase());
        sql.append("(");
        // 定义目标columns
        List<String> targetCols = new ArrayList<String>();
        // 定义目标值
        List<Object> targetVal = new ArrayList<Object>();
        Iterator insertIter = insertMap.entrySet().iterator();
        while (insertIter.hasNext()) {
            Map.Entry<String,Object> entry = (Map.Entry<String,Object>) insertIter.next();
            String fieldName =  entry.getKey();
            Object val = entry.getValue();
            if(val!=null){
                targetCols.add(fieldName);
                targetVal.add(val);
            }
        }
        for (String targetCol : targetCols) {
            sql.append(targetCol).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") values (");
        for (String targetCol : targetCols) {
            sql.append("?").append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        int result = DbUtil.update(conn, sql.toString(), targetVal.toArray(), isClose);
        return nextSeq;
    }
    
    /**
     * 保存或者更新表记录
     * @param tableName
     * @param busRecord
     * @param entityId
     * @return
     */
    public static String saveOrUpdate(Connection conn, String tableName,
            Map<String, Object> busRecord, String recordId, boolean isClose) {
        if(StringUtils.isNotEmpty(recordId)){
            boolean isExists = DbUtil.isExists(conn, recordId, tableName,false);
            if(isExists){
                DbUtil.updateRecord(conn, tableName, recordId, busRecord, isClose);
                return recordId;
            }else{
                recordId = DbUtil.insert(conn, tableName, busRecord, null, null, isClose);
                return recordId;
            }
        }else{
            recordId = DbUtil.insert(conn, tableName, busRecord, null, null, isClose);
        }
        return recordId;
    }
    
    /**
     * 更新数据库记录
     * @param conn
     * @param tableName
     * @param recordId
     * @param busRecord
     * @param closeDb
     */
    public static void updateRecord(Connection conn,String tableName,
            String recordId,Map<String,Object> busRecord,boolean closeDb){
        if (StringUtils.isNotEmpty(recordId)) {
            // 获取私有主键名称
            String primaryKeyName = DbUtil.getPrimaryKeyName(conn, tableName, false).get(0);
            StringBuffer sql = new StringBuffer("update ");
            sql.append(tableName).append(" set ");
            // 获取业务表的列名称
            Set<String> columns = DbUtil.getColumnNameByTableName(conn, tableName,false);
            // 设置更新目标列
            List<String> targetCols = new ArrayList<String>();
            // 定义更新的目标值
            List<Object> targetVals = new ArrayList<Object>();
            Iterator it = busRecord.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                Object val = entry.getValue();
                if (columns.contains(fieldName.toUpperCase())
                        && !fieldName.toUpperCase().equals(primaryKeyName)) {
                    if(!targetCols.contains(fieldName.toUpperCase())){
                        targetCols.add(fieldName.toUpperCase());
                        targetVals.add(val);
                    }
                   
                }
            }
            for (String targetCol : targetCols) {
                sql.append(targetCol).append("=?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where ").append(primaryKeyName).append("=?");
            targetVals.add(recordId);
            if(targetCols.size()>0){
                DbUtil.update(conn, sql.toString(), targetVals.toArray());
            }
        }
    }
    
    /**
     * 判断是否存在记录
     * @param conn
     * @param tableName
     * @param colNames
     * @param params
     * @return
     */
    public static boolean isExists(Connection conn,String tableName,String[] colNames,Object[] params){
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(tableName).append(" where ");
        for(int i=0;i<colNames.length;i++){
            if(i>0){
                sql.append(" and ");
            }
            sql.append(colNames[i]).append("=?");
        }
        
        Object count = DbUtil.getObjectBySql(conn, sql.toString(),params,false);
        if(count!=null){
            if(Integer.parseInt(count.toString())>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    /**
     * 判断是否存在记录
     * @param recordId
     * @param busTableName
     * @return
     */
    public static boolean isExists(Connection conn, String recordId,String tableName,boolean isClose){
        String primaryKeyName = DbUtil.getPrimaryKeyName(conn, tableName, false).get(0);
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(tableName).append(" where ").append(primaryKeyName)
                .append("=? ");
        if(StringUtils.isEmpty(recordId)){
            recordId = "-1";
        }
        Object count = DbUtil.getObjectBySql(conn, sql.toString(),new Object[]{recordId},isClose);
        if(count!=null){
            if(Integer.parseInt(count.toString())>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 
     * 描述 执行更新数据语句
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午11:01:39
     * @param sql
     * @param params
     * @return
     */
    public static int update(Connection conn, String sql, Object[] params) {
        return update(conn, sql, params, true);
    }
    /**
     * 
     * 描述    执行更新数据语句 不关闭数据库连接
     * @author Yanisin Shi
     * @param conn
     * @param sql
     * @param params
     * @return
     */
    public static int updateNew(Connection conn, String sql, Object[] params) {
        return updateNew(conn, sql, params, false);
    }

    /**
     * 
     * 描述 执行更新数据语句
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午11:01:39
     * @param sql
     * @param params
     * @return
     */
    public static int update(Connection conn, String sql, Object[] params, boolean isClose) {
        try {
            QueryRunner qRunner = new QueryRunner();
            int n = 0;
            if (params != null && params.length > 0) {
                n = qRunner.update(conn, sql, params);
            } else {
                n = qRunner.update(conn, sql);
            }
            return n;
        } catch (SQLException e) {
            try {
                DbUtils.rollback(conn);
            } catch (SQLException e1) {
                log.error(e1.getMessage());
            }
            log.error(e.getMessage());
        } finally {
            if (isClose)
                DbUtils.closeQuietly(conn);
        }
        return 0;
    }
    /**
     * 
     * 描述     返回0代表更新失败
     * @author Yanisin Shi
     * @param conn
     * @param sql
     * @param params
     * @param isClose
     * @return
     */
    public static int updateNew(Connection conn, String sql, Object[] params, boolean isClose) {
        try {
            QueryRunner qRunner = new QueryRunner();
            int n = 0;
            if (params != null && params.length > 0) {
                n= qRunner.update(conn, sql, params);
            } else {
                n=qRunner.update(conn, sql);
            }
            if(n!=0){
            return 1;
            }else{
                return 0;
            }
        } catch (SQLException e) {
             log.error(e.getMessage());
            try {
                DbUtils.rollback(conn);
                return 0;
            } catch (SQLException e1) {
                log.error(e1.getMessage());
                return 0;
            }
           
        } finally {
            if (isClose)
                DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 
     * 描述 根据sql获取唯一对象
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午10:41:38
     * @param sql
     * @param params
     * @return
     */
    public static Object getObjectBySql(Connection conn, String sql, Object[] params) {
        return getObjectBySql(conn, sql, params, true);
    }

    /**
     * 
     * 描述 根据sql获取唯一对象
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午10:41:38
     * @param sql
     * @param params
     * @param closeConn
     *            true查询后关闭连接
     * @return
     */
    public static Object getObjectBySql(Connection conn, String sql, Object[] params, boolean closeConn) {
        ResultSetHandler rsh = new ScalarHandler();
        try {
            QueryRunner qRunner = new QueryRunner();
            Object result = null;
            if (params != null && params.length > 0) {
                result = qRunner.query(conn, sql, params, rsh);
            } else {
                result = qRunner.query(conn, sql, rsh);
            }
            if(closeConn){
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
            DbUtils.closeQuietly(conn);
        } 
        return null;

    }

    /**
     * 
     * 描述 根据sql语句获取MAP
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午10:40:01
     * @param sql
     * @param params
     * @return
     */
    public static Map<String, Object> getMapBySql(Connection conn, String sql, Object[] params) {
        return getMapBySql(conn, sql, params, true);
    }
    
    /**
     * 根据数据库表名称获取主键名称
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public static List<String> getPrimaryKeyName(Connection conn,String tableName,boolean isCloseDb){
        StringBuffer sql = new StringBuffer(
                "select cu.column_name from user_cons_columns cu")
                .append(", user_constraints au where cu.constraint_name = au.constraint_name ")
                .append("and au.constraint_type = 'P' and au.table_name=? ");
        List<Map<String,Object>> list = DbUtil.findBySql(conn, sql.toString(), 
                new Object[]{tableName.toUpperCase()},false);
        List<String> pkNames = new ArrayList<String>();
        if (isCloseDb){
            DbUtils.closeQuietly(conn);
        }
        for(Map<String,Object> map:list){
            String column_name = (String) map.get("COLUMN_NAME");
            pkNames.add(column_name);
        }
        return pkNames;
    }

    /**
     * 
     * 描述 根据sql语句获取MAP
     * 
     * @author Derek Zhang
     * @created 2015年12月31日 下午2:30:13
     * @param sql
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapBySql(Connection conn, String sql, Object[] params, boolean isCloseDb) {
        ResultSetHandler rsh = new MapHandler();
        try {
            QueryRunner qRunner = new QueryRunner();
            Map<String, Object> map = null;
            if (params != null && params.length > 0) {
                map = (Map<String, Object>) qRunner.query(conn, sql, params, rsh);
            } else {
                map = (Map<String, Object>) qRunner.query(conn, sql, rsh);
            }
            if (map == null || map.isEmpty())
                return null;
            Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Object> entry = entries.next();
                if (entry.getValue() instanceof Clob) {
                    map.put(entry.getKey(), StringUtil.clobToString((Clob) entry.getValue()));
                } else if (entry.getValue() instanceof Blob) {
                    map.put(entry.getKey(), StringUtil.blobToString((Blob) entry.getValue(), "GBK"));
                }
            }
            return map;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (isCloseDb)
                DbUtils.closeQuietly(conn);
        }
        return null;
    }

    /**
     * 
     * 描述 根据sql语句获取MAP
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午10:40:01
     * @param sql
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapBySqlBlob(Connection conn, String sql, Object[] params) {
        ResultSetHandler rsh = new MapHandler();
        try {
            QueryRunner qRunner = new QueryRunner();
            Map<String, Object> map = null;
            if (params != null && params.length > 0) {
                map = (Map<String, Object>) qRunner.query(conn, sql, params, rsh);
            } else {
                map = (Map<String, Object>) qRunner.query(conn, sql, rsh);
            }
            Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Object> entry = entries.next();
                if (entry.getValue() instanceof Clob) {
                    map.put(entry.getKey(), (Clob) entry.getValue());
                } else if (entry.getValue() instanceof Blob) {
                    map.put(entry.getKey(), (Blob) entry.getValue());
                }
            }
            return map;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
        }
        return null;
    }

    /**
     * 
     * 描述 根据SQL语句获取数量值
     * 
     * @author Flex Hu
     * @created 2014年10月28日 下午3:46:08
     * @param sql
     * @return
     */
    public static int getCount(Connection conn, String sql, Object[] params) {
        StringBuffer newSql = new StringBuffer("select count(*) from (").append(sql).append(") tmp_count_t ");
        return (Integer) DbUtil.getObjectBySql(conn, newSql.toString(), params);
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2014年10月28日 下午4:08:48
     * @param sql
     * @param params
     * @param pb
     * @param orderSql
     * @return
     */
    public static List<Map<String, Object>> findBySql(Connection conn, String sql, Object[] params, PagingBean pb,
            String orderSql) {
        int totalCount = DbUtil.getCount(conn, sql, params);
        pb.setTotalItems(totalCount);
        ResultSetHandler rsh = new MapListHandler();
        int pageSize = pb.getPageSize();
        StringBuffer newSql = new StringBuffer("select top ").append(pageSize).append(" * from (")
                .append("select row_number() over(").append(orderSql).append(" ) as rownumber,* from (").append(sql)
                .append(") BUSSQL ) _A WHERE rownumber ").append(" > ").append(pb.getStart());
        try {
            QueryRunner qRunner = new QueryRunner();
            List<Map<String, Object>> list = null;
            if (params != null && params.length > 0) {
                list = (List<Map<String, Object>>) qRunner.query(conn, newSql.toString(), params, rsh);
            } else {
                list = (List<Map<String, Object>>) qRunner.query(conn, newSql.toString(), rsh);
            }
            return list;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    /**
     * 
     * 描述 转换大字段
     * 
     * @author Flex Hu
     * @created 2015年10月16日 上午10:11:34
     * @param list
     * @return
     */
    private static List<Map<String, Object>> conventClobAndBlob(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Object> entry = entries.next();
                if (entry.getValue() instanceof Clob) {
                    map.put(entry.getKey(), StringUtil.clobToString((Clob) entry.getValue()));
                } else if (entry.getValue() instanceof Blob) {
                    map.put(entry.getKey(), StringUtil.blobToString((Blob) entry.getValue(), "GBK"));
                }
            }
        }
        return list;
    }

    /**
     * 
     * 描述 根据SQL语句获取列表数据
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午9:32:46
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> findBySql(Connection conn, String sql, Object[] params) {
        return findBySql(conn, sql, params, true);
    }

    /**
     * 
     * 描述 根据SQL语句获取列表数据
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午9:32:46
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> findBySql(Connection conn, String sql, Object[] params, boolean closeConn) {
        ResultSetHandler rsh = new MapListHandler();
        try {
            QueryRunner qRunner = new QueryRunner();
            List<Map<String, Object>> list = null;
            if (params != null && params.length > 0) {
                list = (List<Map<String, Object>>) qRunner.query(conn, sql, params, rsh);
            } else {
                list = (List<Map<String, Object>>) qRunner.query(conn, sql, rsh);
            }
            return DbUtil.conventClobAndBlob(list);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (closeConn)
                DbUtils.closeQuietly(conn);
        }
        return null;
    }

    /**
     * 
     * 描述 根据SQL语句获取列表数据
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午9:32:46
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> findBySqlClob(Connection conn, String sql, Object[] params) {
        ResultSetHandler rsh = new MapListHandler();
        try {
            QueryRunner qRunner = new QueryRunner();
            List<Map<String, Object>> list = null;
            if (params != null && params.length > 0) {
                list = (List<Map<String, Object>>) qRunner.query(conn, sql, params, rsh);
            } else {
                list = (List<Map<String, Object>>) qRunner.query(conn, sql, rsh);
            }
            return list;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 
     * 描述 获取连接对象
     * 
     * @author Flex Hu
     * @created 2014年10月28日 上午9:33:17
     * @return
     * @throws SQLException
     */
    public static Connection getConnect(String dbUrl, String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(dbUrl, username, password);
        return conn;
    }
}
    