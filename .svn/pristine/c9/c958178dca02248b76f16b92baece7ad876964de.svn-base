/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.evecom.core.command.HqlFilter;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.hibernate.HibernateModel;
import net.evecom.core.model.TableColumn;
import net.evecom.core.model.TableInfo;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DAOUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月5日 下午12:01:35
 */
@SuppressWarnings("unchecked")
abstract public class GenericDaoImpl<T, PK extends Serializable> extends
        HibernateDaoSupport implements GenericDao<T, PK> {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(GenericDaoImpl.class);
    /**
     * 属性jdbcTemplate
     */
    protected JdbcTemplate jdbcTemplate;
    /**
     * 方法=
     * 
     * @param Class
     *            <T> 传入参数
     * @return 返回值persistType
     */
    protected Class<T> persistType = (Class<T>) DAOUtil.getTypeArguments(
            GenericDaoImpl.class, this.getClass()).get(0);
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:11:14
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:11:08
     * @param persistType
     */
    public void setPersistType(Class persistType) {
        this.persistType = persistType;
    }
    
    /**
     * 从请求对象获取查询参数,并进行构造
     * <p>
     * 参数名格式必须为: Q_firstName_S_EQ 其中Q_表示该参数为查询的参数，firstName查询的字段名称，
     * S代表该参数的类型为字符串类型,该位置的其他值有：
     * D=日期，BD=BigDecimal，FT=float,N=Integer,SN=Short,S=字符串 EQ代表等于。
     * 该位置的其他值有：<br/>
     * LT，GT，EQ，LE，GE,LK,NEQ<br/>
     * 要别代表<,>,=,<=,>=,like,!=的条件查询
     * <p>
     * 
     * @param request
     */
    private String convertOperate(String operate) {
        if (operate.equals("LT")) {
            return "<";
        } else if (operate.equals("GT")) {
            return ">";
        } else if (operate.equals("EQ")) {
            return "=";
        } else if (operate.equals("LE")) {
            return "<=";
        } else if (operate.equals("GE")) {
            return ">=";
        } else if (operate.equals("LK")) {
            return "like";
        } else if (operate.equals("NEQ")) {
            return "!=";
        } else {
            return null;
        }
    }
    
    /**
     * 通过sql查找某个唯一的对象
     * 
     * @author Flex Hu
     * @param queryString
     * @param values
     * @return
     */
    public Object getUniqueBySql(final String sql, final Object[] values){
        return (Object) getHibernateTemplate().execute(new HibernateCallback() {
            /**
             * 方法doInHibernate
             * 
             * @param session
             *            传入参数
             * @return 返回值Object
             */
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sql);

                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.uniqueResult();
            }
        });
    }
    
    /**
     * 方法getTotalCount
     * 
     * @param queryString
     *            传入参数
     * @param values
     *            传入参数
     * @return 返回值Long
     */
    private Long getTotalCount(final String queryString, final Object[] values,
            final Map<String, Class> entityClassMap) {
        Object reVal = getHibernateTemplate().execute(new HibernateCallback() {
            /**
             * 方法doInHibernate
             * 
             * @param session
             *            传入参数
             * @return 返回值Object
             */
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = null;
                if (entityClassMap != null) {
                    query = session.createSQLQuery(queryString);
                } else {
                    query = session.createSQLQuery("select count(*) from ( "
                            + queryString + ") ");
                }
                if (entityClassMap != null) {
                    Iterator it = entityClassMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Class> e = (Map.Entry) it.next();
                        query.addEntity(e.getKey().toString(), e.getValue());
                    }
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                if (entityClassMap != null) {
                    return query.list().size();
                } else {
                    return query.uniqueResult();
                }

            }
        });
        return Long.parseLong(reVal.toString());
    }
    
    /**
     * 根据sql语句查询出结果集合,如果你要查询的是实体结果,那么要往 entityClassMap
     * 存放class对象,如果查询的列结果,那么无需传递该参数
     * 
     * @author Flex Hu
     * @param sql
     *            :查询sql语句
     * @param queryParams
     *            :查询参数,可选
     * @param entityClassMap
     *            :当查询的是实体对象的时候,要用到该参数,也是可选的
     * @param pb
     *            :分页对象,可选参数,不传代表不进行分页
     * @return
     * @Example1: String sql = "select {t.*} from t_sys_menu t";
     *            Map<String,Class> map = new TreeMap<String,Class>();
     *            map.put("t", SysMenu.class); return findBySql(sql, null, map,
     *            null);
     * 
     * @Example2: String sql =
     *            "select username,account from t_system_user where age>? ";
     *            return findBySql(sql,new Object[]{20},null,new PagingBean());
     * 
     */
    public List findBySql(String sql, Object[] queryParams,
            Map<String, Class> entityClassMap, PagingBean pb) {
        if (pb != null) {
            int totalItems = this.getTotalCount(sql, queryParams,
                    entityClassMap).intValue();
            pb.setTotalItems(totalItems);
        }
        SQLQuery sqlquery = getSession().createSQLQuery(sql);
        if (entityClassMap != null) {
            Iterator it = entityClassMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Class> e = (Map.Entry) it.next();
                sqlquery.addEntity(e.getKey().toString(), e.getValue());
            }
        }
        if (pb != null) {
            sqlquery.setFirstResult(pb.getFirstResult()).setMaxResults(
                    pb.getPageSize());
        }
        if (queryParams != null) {
            for (int i = 0; i < queryParams.length; i++) {
                sqlquery.setParameter(i, queryParams[i]);
            }
        }
        return sqlquery.list();
    }
    
    /**
     * 根据sql语句和查询参数获取到结果集合
     * 
     * @author Flex Hu
     * @param sql
     *            :sql语句
     * @param queryParams
     *            :查询参数
     * @param pb
     *            :分页对象
     * @return
     */
    public List<Map<String, Object>> findBySql(String sql,
            Object[] queryParams, PagingBean pb) {
        if (pb != null) {
            int totalItems = this.getTotalCount(sql, queryParams, null)
                    .intValue();
            pb.setTotalItems(totalItems);
            int startIndex = pb.getStart() + 1;
            int endIndex = startIndex + pb.getPageSize() - 1;
            String newSql = "select * from (select rOraclePageSQL.*,ROWNUM as currentRow from ("
                    + sql
                    + ") rOraclePageSQL where rownum <="
                    + endIndex
                    + ") where currentRow>=" + startIndex;
            return this.jdbcTemplate.queryForList(newSql, queryParams);
        } else {
            return this.jdbcTemplate.queryForList(sql, queryParams);
        }

    }
    
    /**
     * 返回queryString查询返回的记录数
     * 
     * @param queryString
     * @param values
     * @return Long
     */
    public Long getTotalItems(final String queryString, final Object[] values) {
        QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(
                queryString,
                queryString,
                java.util.Collections.EMPTY_MAP,
                (org.hibernate.engine.SessionFactoryImplementor) getSessionFactory());
        queryTranslator.compile(java.util.Collections.EMPTY_MAP, false);
        // 不包含ORDER BY字句的HQL语句 add by chensheng at 2011.11.10
        String sqlStringWithoutOrderBy = "";
        // 去掉最后的ORDER BY排序条件,否则使用SQL Server 2008会出现错误 add by chensheng at
        // 2011.11.10
        if (StringUtils.isNotEmpty(queryTranslator.getSQLString())
                && StringUtils.isNotBlank(queryTranslator.getSQLString())) {
            if (queryTranslator.getSQLString().toUpperCase()
                    .lastIndexOf(" ORDER BY") >= 0) {
                // ORDER BY字句在HQL语句字符串中的索引
                int position = queryTranslator.getSQLString().toUpperCase()
                        .lastIndexOf(" ORDER BY");
                // 去掉最后的ORDER BY排序条件
                sqlStringWithoutOrderBy = queryTranslator.getSQLString()
                        .substring(0, position);
            } else {
                sqlStringWithoutOrderBy = queryTranslator.getSQLString();
            }
        }

        final String sql = "select count(*) from ( " + sqlStringWithoutOrderBy
                + ") tmp_count_t";

        Object reVal = getHibernateTemplate().execute(new HibernateCallback() {
            /**
             * 方法doInHibernate
             * 
             * @param session
             *            传入参数
             * @return 返回值Object
             */
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sql);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.uniqueResult();
            }
        });

        return Long.parseLong(reVal.toString());
    }
    
    
    /**
     * 根据数据库表名称获取主键名称
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public List<String> getPrimaryKeyName(String tableName){
        StringBuffer sql = new StringBuffer(
                "select cu.column_name from user_cons_columns cu")
                .append(", user_constraints au where cu.constraint_name = au.constraint_name ")
                .append("and au.constraint_type = 'P' and au.table_name=? ");
        List<String> primaryKeyNames = this.jdbcTemplate.queryForList(
                sql.toString(), String.class,
                new Object[] { tableName.toUpperCase() });
        return primaryKeyNames;
    }
    
    /**
     * 
     * 描述 获取sqlserver的主键名称
     * @author Flex Hu
     * @created 2014年10月13日 下午4:46:13
     * @param tableName
     * @return
     */
    public List<String> getSqlServerPrimaryKeys(String tableName){
        StringBuffer sql = new StringBuffer("SELECT COLUMN_NAME FROM")
                .append(" INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME=? ");
        return this.jdbcTemplate.queryForList(sql.toString(), String.class, new Object[]{tableName});
    }
    
    /**
     * 根据表名称获取ORACLE的表注释
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public String getOracleTableComment(String tableName) {
        String sql = "SELECT COMMENTS FROM  USER_TAB_COMMENTS WHERE TABLE_NAME=? ";
        String comments = (String) this.getUniqueBySql(sql,
                new Object[] { tableName.toUpperCase() });
        return comments;
    }
    /**
     * 
     * 描述：获取表中大字段列
     * @author Water Guo
     * @created 2017-10-16 上午9:17:15
     * @param tableName
     * @return
     */
    public List<String> getBigFiled(String tableName){
        StringBuffer sql=new StringBuffer("SELECT COL.COLUMN_NAME FROM");
        sql.append(" USER_TAB_COLUMNS COL WHERE COL.TABLE_NAME=?  AND ");
        sql.append(" COL.DATA_TYPE  IN ('CLOB','BLOB') ");
        List<String> list = this.jdbcTemplate.queryForList(sql.toString(),
                new Object[]{tableName.toUpperCase()},String.class);
        return list;
    }
    /**
     * 根据数据库表名称获取到数据库列信息列表
     * 
     * @param tableName
     * @return
     */
    public List<TableColumn> findColumns(String tableName) {
        StringBuffer sql = new StringBuffer(
                "select col.COLUMN_NAME,colcomment.comments,");
        sql.append("col.DATA_TYPE,col.DATA_LENGTH,col.DATA_SCALE,col.NULLABLE ")
                .append("from user_tab_columns col,user_col_comments colcomment")
                .append(" where col.Table_Name=? and col.TABLE_NAME=colcomment.table_name")
                .append("  and col.COLUMN_NAME=colcomment.column_name")
                .append(" order by col.COLUMN_ID asc");
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(
                sql.toString(), new Object[] { tableName.toUpperCase() });
        List<TableColumn> columns = new ArrayList<TableColumn>();
        for(Map<String,Object> map:list){
            Iterator ite = map.entrySet().iterator();
            TableColumn tableColumn = new TableColumn();
            while (ite.hasNext()) {
                Map.Entry<String, Object> entry = (Entry<String, Object>) ite
                        .next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equals("COLUMN_NAME")){
                    tableColumn.setColumnName(value.toString());
                }else if(key.equals("COMMENTS")){
                    if(value!=null){
                        tableColumn.setComments(value.toString());
                    }else{
                        tableColumn.setComments("");
                    }
                }else if(key.equals("DATA_TYPE")){
                    tableColumn.setDataType(value.toString());
                }else if(key.equals("DATA_LENGTH")){
                    tableColumn.setDataLength(Integer.parseInt(value.toString()));
                }else if(key.equals("DATA_SCALE")){
                    if(value!=null){
                        tableColumn.setDataScale(Integer.parseInt(value.toString()));
                    }else{
                        tableColumn.setDataScale(0);
                    }
                }else if(key.equals("NULLABLE")){
                    if(value.toString().equals("Y")){
                        tableColumn.setNullAble(true);
                    }else{
                        tableColumn.setNullAble(false);
                    }
                }
            }
            columns.add(tableColumn);
        }
        return columns;
    }
    /**
     * 根据表名称获取到表详细实体类
     * 
     * @param tableName
     * @return
     */
    public TableInfo getTableInfoByName(String tableName){
        String comments = this.getOracleTableComment(tableName);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName.toUpperCase());
        tableInfo.setTableComments(comments);
        List<TableColumn> columns = this.findColumns(tableName);
        tableInfo.setColumns(columns);
        return tableInfo;
    }
    /**
     * 根据表名称获取所有数据
     * @param tableName
     * @return
     */
    public List<Map<String,Object>> findAllByTableName(String tableName){
        StringBuffer sql = new StringBuffer("select *from ").append(tableName.toUpperCase());
        return this.jdbcTemplate.queryForList(sql.toString());
    }
    
    /**
     * 判断某条记录是否存在
     * 
     * @author Flex Hu
     * @param entityId
     * @param tableName
     * @return
     */
    public boolean isExists(String entityId, String tableName) {
        String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(tableName).append(" where ").append(primaryKeyName)
                .append("=? ");
        if (entityId == null) {
            entityId = "-1";
        }
        long count = this.jdbcTemplate.queryForLong(sql.toString(),
                new Object[] { entityId });
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 根据数据库表名称获取列信息
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public Set<String> getColumnNameByTableName(String tableName) {
        Set<String> set = new HashSet<String>();
        StringBuffer sql = new StringBuffer("select t.COLUMN_NAME from ")
                .append("user_tab_columns t where  t.table_name=? ");
        List<String> list = this.jdbcTemplate.queryForList(sql.toString(),
                new Object[]{tableName.toUpperCase()},String.class);
        set.addAll(list);
        return set;
    }
    /**
     * 更新业务表数据
     * 
     * @author Flex Hu
     * @param variables
     *            :获取到流程里面的变量值
     */
    public void updateDatas(Map<String, Object> variables, String entityId,
            String entityName) {
        if (StringUtils.isNotEmpty(entityId)) {
            // 获取业务表名称
            String tableName = entityName.toUpperCase();
            // 获取私有主键名称
            String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
            StringBuffer sql = new StringBuffer("update ");
            sql.append(tableName).append(" set ");
            // 获取业务表的列名称
            Set<String> columns = this.getColumnNameByTableName(tableName);
            //获取业务表大字段的列名称
            List<String> bigColumns=this.getBigFiled(tableName);
            // 设置更新目标列
            List<String> targetCols = new ArrayList<String>();
            // 定义更新的目标值
            List<Object> targetVals = new ArrayList<Object>();
            // 设置更新Clob目标列
            List<String> targetColsClob = new ArrayList<String>();
            // 定义更新的Clob目标值
            List<Object> targetValsClob = new ArrayList<Object>();
            Iterator it = variables.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                Object val = entry.getValue();
                if (columns.contains(fieldName.toUpperCase())
                        && !fieldName.toUpperCase().equals(primaryKeyName)) {
                    if(!targetCols.contains(fieldName.toUpperCase())){
                        if(!bigColumns.contains(fieldName.toUpperCase())){
                            targetCols.add(fieldName.toUpperCase());
                            targetVals.add(val);
                        }else{
                            targetColsClob.add(fieldName.toUpperCase());
                            targetValsClob.add(val);
                        }
                    }
                   
                }
            }
            targetCols.addAll(targetColsClob);
            targetVals.addAll(targetValsClob);
            for (String targetCol : targetCols) {
                sql.append(targetCol).append("=?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where ").append(primaryKeyName).append("=?");
            targetVals.add(entityId);
            if(targetCols.size()>0){
                jdbcTemplate.update(sql.toString(), targetVals.toArray());
            }
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年10月11日 上午10:30:57
     * @param colValues
     * @param tableName
     * @param entityId
     * @param seqName
     * @return
     */
    private String saveOrUpdateCode(Map<String,Object> colValues,String tableName,String entityId,
            String seqName,String assignId){
        if (this.isExists(entityId, tableName)) {
            this.updateDatas(colValues, entityId.toString(), tableName);
            return entityId;
        } else {
            Set<String> columns = this.getColumnNameByTableName(tableName);
            // 获取主键名称
            String primaryKeyName = getPrimaryKeyName(tableName).get(0);
            String nextSeq= "";
            if(StringUtils.isNotEmpty(seqName)){
                String getNextSeq = "select "+seqName+".nextval FROM DUAL";
                nextSeq = String.valueOf(jdbcTemplate.queryForInt(getNextSeq));
            }else if(StringUtils.isNotEmpty(assignId)){
                nextSeq = assignId;
            }else{
                nextSeq = UUIDGenerator.getUUID();
            }
            Map<String,Object> insertMap = new HashMap<String,Object>();
            insertMap.put(primaryKeyName, nextSeq);
            Iterator it = colValues.entrySet().iterator();
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
            /*for(int k = 0;k<targetCols.size();k++){
                log.info(targetCols.get(k)+",value:"+targetVal.get(k));
            }*/
            int result = this.jdbcTemplate.update(sql.toString(),
                    targetVal.toArray());
            if (result == 1) {
                // 开始获取插入的entityId
                if (StringUtils.isNotEmpty(entityId)) {
                    return entityId;
                } else {
                    return nextSeq;
                }
            } else {
                return "-1";
            }
        }
    }
    
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @param seqName:序列名称
     * @return
     */
    public String saveOrUpdate(Map<String,Object> colValues,String tableName,String entityId,String seqName){
        return this.saveOrUpdateCode(colValues, tableName, entityId, seqName,null);
    }
    /**
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @return
     */
    public String saveOrUpdate(Map<String,Object> colValues,String tableName,String entityId){
        Map<String, Object> config = this.getByJdbc("ENCRYPTION_CONFIG_TABLE", new String[] { "BUSTABLE_NAME" },
                new Object[] { tableName.toUpperCase() });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            List<Map<String, Object>> columnList = this.getAllByJdbc("ENCRYPTION_CONFIG_COLUMN",
                    new String[] { "CONFIG_ID" }, new Object[] { config.get("CONFIG_ID") });
            if(columnList!=null&&columnList.size()>0){
                for(Map<String,Object> column : columnList){
                    String columnName = (String) column.get("COLUMN_NAME");
                    if(colValues.get(columnName)!=null&&StringUtils.isNotEmpty((String) colValues.get(columnName))){
                        String val = (String) colValues.get(columnName);
                        colValues.put(columnName, sm4.encryptDataCBC(val));
                        
                        if("0".equals(column.get("IS_ENCRYPTED"))){
                            column.put("IS_ENCRYPTED", 1);
                            this.saveOrUpdateCode(column, "ENCRYPTION_CONFIG_COLUMN", (String) column.get("COLUMN_ID"),
                                    null, null);
                        }
                    }
                }
            }
        }
        return this.saveOrUpdateCode(colValues, tableName, entityId,null,null);
    }
    /**
     * 
     * 描述 保存或者更新记录,基于分配的ID
     * @author Flex Hu
     * @created 2014年10月16日 上午11:14:08
     * @param colValues
     * @param tableName
     * @param assignId
     * @return
     */
    public String saveOrUpdateForAssignId(Map<String,Object> colValues,String tableName,String assignId){
        Map<String, Object> config = this.getByJdbc("ENCRYPTION_CONFIG_TABLE", new String[] { "BUSTABLE_NAME" },
                new Object[] { tableName.toUpperCase() });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            List<Map<String, Object>> columnList = this.getAllByJdbc("ENCRYPTION_CONFIG_COLUMN",
                    new String[] { "CONFIG_ID" }, new Object[] { config.get("CONFIG_ID") });
            if(columnList!=null&&columnList.size()>0){
                for(Map<String,Object> column : columnList){
                    String columnName = (String) column.get("COLUMN_NAME");
                    if(colValues.get(columnName)!=null&&StringUtils.isNotEmpty((String) colValues.get(columnName))){
                        String val = (String) colValues.get(columnName);
                        colValues.put(columnName, sm4.encryptDataCBC(val));
                        
                        if("0".equals(column.get("IS_ENCRYPTED"))){
                            column.put("IS_ENCRYPTED", 1);
                            this.saveOrUpdateCode(column, "ENCRYPTION_CONFIG_COLUMN", (String) column.get("COLUMN_ID"),
                                    null, null);
                        }
                    }
                }
            }
        }
        return this.saveOrUpdateCode(colValues, tableName, assignId, null, assignId);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年8月29日 下午3:08:45
     * @param sql
     * @return
     */
    public Map<String,String> getTableNamesBySql(String sql){
        Map<String,String> tables = new HashMap<String,String>();
        String upperSql = sql.toUpperCase();
        String tableSql = "";
        if(upperSql.indexOf("WHERE")!=-1){
            tableSql = upperSql.substring(upperSql.indexOf("FROM")+4,upperSql.indexOf("WHERE"));
            
        }else{
            tableSql = upperSql.substring(upperSql.indexOf("FROM")+4,upperSql.length());
        }
        if(tableSql.contains("JOIN")){
            String[] splitSqls = tableSql.split("JOIN");
            for(String splitSql:splitSqls){
                String tableName=splitSql.trim().split(" ")[0];
                String aliasName=splitSql.trim().split(" ")[1];
                tables.put(tableName,aliasName);
            }
        }else{
            String[] targetSqls = tableSql.trim().split(",");
            for(String targetSql:targetSqls){
                tables.put(targetSql.split(" ")[0],targetSql.split(" ")[1]);
            }
        }
        return tables;
    }
    
    /**
     * 
     * 描述 根据sql语句获取查询结果keys
     * @author Flex Hu
     * @created 2014年8月29日 下午3:21:58
     * @param sql
     * @return
     */
    public List<String> findQueryResultKeysBySql(String sql){
        final List<String> keys = new ArrayList<String>();
        StringBuffer targetSql = new StringBuffer("select * FROM (").append(sql.toUpperCase()).append(
                ")  WHERE ROWNUM<=1 ");
        this.jdbcTemplate.query(targetSql.toString(), new ResultSetExtractor(){
            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                ResultSetMetaData rsmd=rs.getMetaData();
                for(int i =1;i<=rsmd.getColumnCount();i++){
                    keys.add(rsmd.getColumnName(i));
                }
                return null;
            }
            
        });
        return keys;
    }
    
    /**
     * 
     * 描述 根据表名称获取到列、注释键值对
     * @author Flex Hu
     * @created 2014年9月2日 上午9:20:32
     * @param tableNames
     * @return
     */
    public Map<String,String> getColumnCommentByTableNames(Set<String> tableNames){
        Map<String,String> map = new HashMap<String,String>();
        StringBuffer sql = new StringBuffer("SELECT column_name,comments FROM ");
        sql.append("user_col_comments WHERE ");
        List params = new ArrayList();
        for(String tableName:tableNames){
            sql.append(" table_name=? ").append("or");
            params.add(tableName);
        }
        String targetSql = sql.substring(0, sql.length()-2);
        targetSql+=" ORDER BY column_name asc ";
        List<Map<String,Object>> list = this.jdbcTemplate.queryForList(targetSql, params.toArray());
        for(Map<String,Object> data:list){
            String columnName = (String) data.get("COLUMN_NAME");
            String comments = (String) data.get("COMMENTS");
            map.put(columnName, comments);
        }
        return map;
    }
    /**
     * 
     * 描述 根据SQL语句获取查询结果集合中的列名称集合
     * @author Flex Hu
     * @created 2014年8月29日 下午4:59:45
     * @param sql
     * @return
     */
    public List<TableColumn> findQueryResultColsBySql(String sql){
        List<String> resultKeys = this.findQueryResultKeysBySql(sql);
        Map<String,String> tables = this.getTableNamesBySql(sql);
        List<TableColumn> columns = new ArrayList<TableColumn>();
        Map<String,String> columnComments = this.getColumnCommentByTableNames(tables.keySet());
        
        for(String resultKey:resultKeys){
            TableColumn column = new TableColumn();
            column.setColumnName(resultKey);
            String tableAliasName = "";
            for(String tableName:tables.keySet()){
                Set<String> columnNames = this.getColumnNameByTableName(tableName);
                if(columnNames.contains(resultKey)){
                    tableAliasName = tables.get(tableName);
                    break;
                }
            }
            column.setAliasName(tableAliasName+"."+resultKey);
            String comments = columnComments.get(resultKey);
            if(StringUtils.isNotEmpty(comments)){
                column.setComments(comments);
            }else{
                column.setComments(resultKey);
            }
            columns.add(column);
        }
       
        return columns;
    }
    
    /**
     * 把传递进来的SQL语句进行重新构建 构建成查询的SQL语句
     * 
     * @author Flex Hu
     * @param sqlFilter
     *            :sql过滤器
     * @param oldSql
     *            :原本的SQL语句
     * @param params
     *            :查询参数
     * @return
     */
    public String getQuerySql(SqlFilter sqlFilter, String oldSql,
            List<Object> params) {
        StringBuffer sql = new StringBuffer(oldSql);
        Map<String, Object> queryParams = sqlFilter.getQueryParams();
        Map<String, String> orderParams = sqlFilter.getOrderParams();
        Map<String, String> groupParams = sqlFilter.getGroupParams();
        if (sql.indexOf("where") == -1 && sql.indexOf("WHERE") == -1) {
            sql.append(" where 1=1 ");
        }
        Iterator iter = queryParams.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            SqlFilter.addRequestQueryParam(key, val, sql, params);
        }
        if (groupParams.size() > 0) {
            sql.append(" group by ");
            Iterator orderIter = groupParams.entrySet().iterator();
            while (orderIter.hasNext()) {
                Map.Entry entry = (Map.Entry) orderIter.next();
                String key = entry.getKey().toString();
            //    String val = entry.getValue().toString();
                sql.append(key).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
        }
        if (orderParams.size() > 0) {
            sql.append(" order by ");
            Iterator orderIter = orderParams.entrySet().iterator();
            while (orderIter.hasNext()) {
                Map.Entry entry = (Map.Entry) orderIter.next();
                String key = entry.getKey().toString();
                String val = entry.getValue().toString();
                sql.append(key).append(" ").append(val).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
        }
        return sql.toString();
    }
    
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public Map<String,Object> getByJdbc(String tableName,String[] colNames,Object[] colValues){
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(tableName).append(" BUS WHERE ");
        for(int i = 0;i<colNames.length;i++){
            if(i>0){
                sql.append(" AND ");
            }
            sql.append("BUS.").append(colNames[i]).append("=? ");
        }
        try{
            Map<String,Object> result = this.jdbcTemplate.queryForMap(sql.toString(), colValues);
            Map<String, Object> config = this.getByJdbc("ENCRYPTION_CONFIG_TABLE", new String[] { "BUSTABLE_NAME" },
                    new Object[] { tableName.toUpperCase() });
            if(config!=null){
                Sm4Utils sm4 = new Sm4Utils();
                String csql = "select wmsys.wm_concat(t.column_name) columnname from ENCRYPTION_CONFIG_COLUMN t "
                        + "where t.config_id=?";
                String column = (String) this.getByJdbc(csql, new Object[]{config.get("CONFIG_ID")}).get("COLUMNNAME");
                String[] columns = column.split(",");
                for(String columnName : columns){
                    if(result.get(columnName)!=null){
                        String val = (String) result.get(columnName);
                        String newVal = sm4.decryptDataCBC(val);
                        if(StringUtils.isNotEmpty(newVal)){
                            result.put(columnName, newVal);
                        }else{
                            result.put(columnName, val);
                        }
                    }
                }
            }
            
            return result;
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public List<Map<String,Object>> getAllByJdbc(String tableName,String[] colNames,Object[] colValues){
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(tableName).append(" BUS WHERE ");
        for(int i = 0;i<colNames.length;i++){
            if(i>0){
                sql.append(" AND ");
            }
            sql.append("BUS.").append(colNames[i]).append("=? ");
        }
        try{
            return this.jdbcTemplate.queryForList(sql.toString(), colValues);
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * 
     * 描述 根据表名称,列名称,列对应值移除掉记录
     * @author Flex Hu
     * @created 2014年9月7日 上午11:40:43
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void remove(String tableName,String colName,Object[] colValues){
        StringBuffer sql = new StringBuffer("delete from ");
        sql.append(tableName.toUpperCase());
        sql.append(" WHERE ").append(colName).append("=? ");
        for(Object colValue:colValues){
            this.jdbcTemplate.update(sql.toString(), new Object[]{colValue});
        }
    }
    
    /**
     * 
     * 描述 删除掉数据
     * @author Flex Hu
     * @created 2015年8月27日 上午11:00:47
     * @param tableName
     * @param colNames
     * @param colValues
     */
    public void remove(String tableName,String[] colNames,Object[]colValues){
        StringBuffer sql = new StringBuffer("delete from ");
        sql.append(tableName.toUpperCase());
        sql.append(" WHERE ");
        for(int i=0;i<colNames.length;i++){
            if(i>0){
                sql.append(" AND ");
            }
            sql.append(colNames[i]).append("=? ");
        }
        this.jdbcTemplate.update(sql.toString(), colValues);
    }
    
    /**
     * 
     * 描述 根据表名称,列名称,列对应值伪删除记录
     * @author Roy Li
     * @created 2014-11-13 下午4:06:19
     * @param tableName
     * @param colName
     * @param colValues
     * @see net.evecom.core.dao.GenericDao#pseudoDel(java.lang.String, java.lang.String, java.lang.Object[])
     */
    public void pseudoDel(String tableName,String colName,Object[] colValues){
        StringBuffer sql =new StringBuffer("UPDATE ");
        sql.append(tableName).append(" SET IS_DELETE=1 ");
        sql.append(" WHERE ").append(colName).append("=? ");
        for(Object colValue:colValues){
            this.jdbcTemplate.update(sql.toString(), new Object[]{colValue});
        }
    }
    /**
     * 根据SQL获取到唯一结果MAP
     * @param sql
     * @param objs
     * @return
     */
    public Map<String,Object> getByJdbc(String sql,Object[] objs){
        try{
            if(objs!=null&&objs.length>0){
                return jdbcTemplate.queryForMap(sql, objs);
            }else{
                return jdbcTemplate.queryForMap(sql);
            }
        }catch(Exception e){
//            log.error("",e);
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 获取树形结构的最大排序值
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public int getMaxTreeSortSn(String tableName) {
        return jdbcTemplate
                .queryForInt("select max(TREE_SN) from " + tableName);
    }
    
    /**
     * 更新树形排序字段
     * @param tableName
     * @param dragTreeNodeId
     * @param dragTreeNodeNewLevel
     * @param targetNodeId
     * @param targetNodeLevel
     */
    public void updateTreeSn(String tableName,String dragTreeNodeId,
            int dragTreeNodeNewLevel,String targetNodeId, int targetNodeLevel,String moveType){
        String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
        Map<String,Object> dragTreeData = this.getByJdbc(tableName, 
                new String[]{primaryKeyName},new Object[]{dragTreeNodeId});
        Map<String,Object> targetTreeData = this.getByJdbc(tableName,
                new String[]{primaryKeyName},new Object[]{targetNodeId});
        //计算最终的level
        int level = dragTreeNodeNewLevel+1;
        //获取原先的level
        int oldLevel = Integer.parseInt(dragTreeData.get("TREE_LEVEL").toString());
      //获取差距的level
        int minusLevel = oldLevel - level;
        //获取旧的路径
        String oldPath = (String) dragTreeData.get("PATH");
        if(dragTreeNodeNewLevel==targetNodeLevel){
            //说明将其拖动到目标节点前面
            //计算最终的排序值
            int treeSn = Integer.parseInt(targetTreeData.get("TREE_SN").toString());
            if(moveType.equals("next")){
                treeSn +=1;
            }
            //计算最终的父亲ID
            String parentId =targetTreeData.get("PARENT_ID").toString();
            //计算最新的path
            String path1 = (String) targetTreeData.get("PATH");
            String path2 = path1.substring(0,path1.indexOf(targetNodeId+"."));
            String path = path2+dragTreeNodeId+".";
            //将该节点更新
            dragTreeData.put("TREE_LEVEL", level);
            dragTreeData.put("PARENT_ID", parentId);
            dragTreeData.put("PATH", path);
            dragTreeData.put("TREE_SN", treeSn);
            //将同级的其它节点排序全部加+1
            if(moveType.equals("prev")){
                StringBuffer updateSnSql =new StringBuffer("UPDATE ");
                updateSnSql.append(tableName).append(" BUS SET BUS.TREE_SN=(BUS.TREE_SN+1) ")
                .append(" WHERE BUS.TREE_LEVEL=? AND BUS.TREE_SN>= ? AND BUS.")
                .append(primaryKeyName).append("!=? ");
                this.jdbcTemplate.update(updateSnSql.toString(),new Object[]{level,treeSn,dragTreeNodeId});
            }
            //更新被拖动节点子孙节点的层级信息和PATH信息
            StringBuffer updateChildInfo = new StringBuffer("UPDATE ").append(tableName).append(
                    " BUS SET BUS.TREE_LEVEL=(BUS.TREE_LEVEL");
            if(minusLevel>=0){
                updateChildInfo.append("-");
            }else{
                minusLevel = -minusLevel;
                updateChildInfo.append("+");
            }
            updateChildInfo.append(minusLevel).append("),")
            .append("BUS.PATH=REPLACE(BUS.PATH,'").append(oldPath).append("','")
            .append(path).append("') WHERE BUS.").append(primaryKeyName).append("!=? AND ")
            .append(" BUS.PARENT_ID=? ");
            this.jdbcTemplate.update(updateChildInfo.toString(),new Object[]{dragTreeNodeId,dragTreeNodeId});
            //更新被拖动节点的信息
            this.saveOrUpdate(dragTreeData, tableName, dragTreeNodeId);
            
        }else if(dragTreeNodeNewLevel>targetNodeLevel){
            //说明将其拖动到目标节点里面
            String parentId = targetNodeId;
            //获取该节点下最大排序值的节点
            String maxSql = "select max(TREE_SN) from "+tableName+" WHERE PARENT_ID=? ";
            int treeSn =this.jdbcTemplate.queryForInt(maxSql,new Object[]{parentId})+1;
            //计算最新的path
            String path1 = (String) targetTreeData.get("PATH");
            String path = path1+dragTreeNodeId+".";
          //更新被拖动节点子孙节点的层级信息和PATH信息
            StringBuffer updateChildInfo = new StringBuffer("UPDATE ").append(tableName).append(
                    " BUS SET BUS.TREE_LEVEL=(BUS.TREE_LEVEL");
            if(minusLevel>=0){
                updateChildInfo.append("-");
            }else{
                minusLevel = -minusLevel;
                updateChildInfo.append("+");
            }
            updateChildInfo.append(minusLevel).append("),")
            .append("BUS.PATH=REPLACE(BUS.PATH,'").append(oldPath).append("','")
            .append(path).append("') WHERE BUS.").append(primaryKeyName).append("!=? AND ")
            .append(" BUS.PARENT_ID=? ");
            this.jdbcTemplate.update(updateChildInfo.toString(),new Object[]{dragTreeNodeId,dragTreeNodeId});
            //更新被拖动节点的信息
            //将该节点更新
            dragTreeData.put("TREE_LEVEL", level);
            dragTreeData.put("PARENT_ID", parentId);
            dragTreeData.put("PATH", path);
            dragTreeData.put("TREE_SN", treeSn);
            this.saveOrUpdate(dragTreeData, tableName, dragTreeNodeId);
        }
    }
    
    /**
     * 
     * 描述 删除树形数据,包括子孙数据
     * @author Flex Hu
     * @created 2014年9月24日 下午2:43:20
     * @param tableName
     * @param recordId
     */
    public void removeTreeDataCascadeChild(String tableName,String recordId){
        String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
        StringBuffer getPathSql = new StringBuffer("select PATH FROM ").append(tableName).append(" WHERE ")
                .append(primaryKeyName).append("=? ");
        String path = this.jdbcTemplate.queryForObject(getPathSql.toString(), String.class, new Object[] { recordId });
        StringBuffer delChildSql = new StringBuffer("delete from ").append(tableName).append(" WHERE ")
                .append(" PATH LIKE ? ");
        this.jdbcTemplate.update(delChildSql.toString(),new Object[]{path+"%"});
    }
    
    /**
     * 
     * 描述 伪删除树形数据,包括子孙数据，伪删除字段需为IS_DELETE，0未删除，1为删除
     * @author Roy Li
     * @created 2014-10-28 下午3:11:35
     * @param tableName
     * @param recordId
     * @see net.evecom.core.dao.GenericDao#pseudoDelTreeDataCascadeChild(java.lang.String, java.lang.String)
     */
    public void pseudoDelTreeDataCascadeChild(String tableName,String recordId){
        String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
        StringBuffer getPathSql = new StringBuffer("select PATH FROM ").append(tableName).append(" WHERE ")
                .append(primaryKeyName).append("=? ");
        String path = this.jdbcTemplate.queryForObject(getPathSql.toString(), String.class, new Object[] { recordId });
        StringBuffer delChildSql = new StringBuffer("update ").append(tableName).append(" set IS_DELETE=1 ")
                .append(" WHERE ").append(" PATH LIKE ? ");
        this.jdbcTemplate.update(delChildSql.toString(),new Object[]{path+"%"});
    }
    
    /**
     * 
     * 描述 执行SQL语句
     * @author Flex Hu
     * @created 2014年10月3日 下午4:47:56
     * @param sql
     * @param params
     */
    public void executeSql(String sql,Object[] params){
        if(params!=null&&params.length>0){
            this.jdbcTemplate.update(sql, params);
        }else{
            this.jdbcTemplate.update(sql);
        }
    }
    
    /**
     * 
     * 描述 获取SqlServer数据库下的所有数据库表名称
     * @author Flex Hu
     * @created 2014年10月13日 下午2:45:57
     * @return
     */
    public List<String> findSqlServerTables(){
        StringBuffer sql = new StringBuffer("select name FROM ").append(TableInfo.DB_NAME).append("..Sysobjects ")
                .append("WHERE XType = 'U' ORDER BY Name ");
        return this.jdbcTemplate.queryForList(sql.toString(), String.class);
    }
    /**
     * 
     * 描述 获取SqlServer表的注释
     * @author Flex Hu
     * @created 2014年10月15日 下午3:31:47
     * @param tableName
     * @return
     */
    public String getSqlServerTableComment(String tableName){
        StringBuffer sql = new StringBuffer("select cast(isnull(f.[value],'') as nvarchar(100)) ").append(
                "from Sysobjects d  left join sys.extended_properties f ").append(
                "on d.id=f.major_id and f.minor_id=0 where d.name=? ");
        return this.jdbcTemplate.queryForObject(sql.toString(), String.class,new Object[]{tableName});
    }
    /**
     * 
     * 描述 根据表名称获取列集合
     * @author Flex Hu
     * @created 2014年10月15日 下午3:58:49
     * @param tableName
     * @return
     */
    public List<TableColumn> findSqlServerTableColumns(String tableName){
        List<TableColumn> columns =new ArrayList<TableColumn>();
        StringBuffer sql = new StringBuffer("SELECT a.name FIELDNAME,")
                .append("b.name FIELDTYPE,COLUMNPROPERTY(a.id,a.name,'PRECISION') DATALENGTH,")
                .append(" isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0) DATASCALE,").append("a.isnullable NULLABLE,")
                .append(" cast(isnull(g.[value],'') as nvarchar(1024)) COLCOMMENT FROM syscolumns a")
                .append(" left join systypes b ").append("on a.xusertype=b.xusertype ")
                .append("inner join sysobjects d on ")
                .append("a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties' ")
                .append("left join syscomments e on a.cdefault=e.id ").append("left join sys.extended_properties g ")
                .append("on a.id=G.major_id and a.colid=g.minor_id ").append("left join sys.extended_properties f ")
                .append("on d.id=f.major_id and f.minor_id=0 ").append("where d.name=? order by a.id,a.colorder");
        List<Map<String,Object>> list = this.findBySql(sql.toString(), new Object[]{tableName}, null);
        for(Map<String,Object> map:list){
            TableColumn tableColumn = new TableColumn();
            String columnName = (String) map.get("FIELDNAME");
            String comments = "";
            if(map.get("COLCOMMENT")!=null){
                comments=(String) map.get("COLCOMMENT");
            }
            String dataType= (String) map.get("FIELDTYPE");
            int dataLength = Integer.parseInt(map.get("DATALENGTH").toString());
            int dataScale = Integer.parseInt(map.get("DATASCALE").toString());
            int nullAble = Integer.parseInt(map.get("NULLABLE").toString());
            tableColumn.setColumnName(columnName);
            tableColumn.setComments(comments);
            tableColumn.setDataType(dataType);
            tableColumn.setDataLength(dataLength);
            tableColumn.setDataScale(dataScale);
            if(nullAble==1){
                tableColumn.setNullAble(true);
            }else{
                tableColumn.setNullAble(false);
            }
            columns.add(tableColumn);
        }
        return columns;
    }
    /**
     * 
     * 描述 根据表名称获取表格信息
     * @author Flex Hu
     * @created 2014年10月13日 下午3:55:54
     * @param tableName
     * @return
     */
    public TableInfo getSqlServerTableInfo(String tableName){
        TableInfo info = new TableInfo();
        info.setTableName(tableName);
        info.setTableComments(this.getSqlServerTableComment(tableName));
        List<String> primaryKeys = this.getSqlServerPrimaryKeys(tableName);
        info.setPrimaryKeys(primaryKeys);
        List<TableColumn> columns = this.findSqlServerTableColumns(tableName);
        info.setColumns(columns);
        return info;
    }
    /**
     * 
     * 描述 拼接获取查询带有数据权限的SQL语句
     * @author Flex Hu
     * @created 2014年10月23日 上午9:49:10
     * @param oldSql
     * @param tableAliasName
     * @param queryParams
     * @return
     */
    public String getDataPermissionSql(StringBuffer oldSql,String tableAliasName,List<Object> queryParams){
        SysUser curLoginUser = AppUtil.getLoginUser();
        if(curLoginUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            return oldSql.toString();
        }else{
            StringBuffer newSql = new StringBuffer(oldSql.toString().toUpperCase());
            if(newSql.indexOf("WHERE")!=-1){
                newSql.append(" AND ").append(tableAliasName).append(".PROVINCE_CODE=? ");
            }else{
                newSql.append(" WHERE ").append(tableAliasName).append(".PROVINCE_CODE=? ");
            }
            return newSql.toString();
        }
    }
    
    /**
     * 
     * 描述 根据表名称和列名称获取列的注释
     * @author Flex Hu
     * @created 2015年4月24日 下午4:40:26
     * @param tableName
     * @param colName
     * @return
     */
    public String getColComment(String tableName, String colName) {
        StringBuffer sql = new StringBuffer("select colcomment.comments from user_tab_columns col");
        sql.append(",user_col_comments colcomment where col.TABLE_NAME=? ")
                .append(" and col.TABLE_NAME=colcomment.table_name ")
                .append(" and col.COLUMN_NAME=colcomment.column_name ").append(" and col.COLUMN_NAME=? ");
        String comments = "";
        try{
            comments= this.jdbcTemplate.queryForObject(sql.toString(), new Object[] { tableName, colName },
                    String.class);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return comments;
    }
    
    /**
     * 
     * 描述 根据sql语句获取对象
     * @author Flex Hu
     * @created 2015年10月19日 下午2:47:15
     * @param sql
     * @param values
     * @return
     */
    public Object getObjectBySql(final String sql,final Object[] values){
        try{
            if(values!=null&&values.length>=1){
                return this.jdbcTemplate.queryForObject(sql.toString(),values,Object.class);
            }else{
                return this.jdbcTemplate.queryForObject(sql.toString(),Object.class);
            }
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * 
     * 描述 根据SQL和参数获取MAP数据
     * @author Flex Hu
     * @created 2015年11月25日 上午11:03:23
     * @param sql
     * @param params
     * @return
     */
    public Map<String,Object> getMapBySql(String sql,Object[] params){
        try{
            if(params!=null&&params.length>=1){
                return this.jdbcTemplate.queryForMap(sql.toString(),params);
            }else{
                return this.jdbcTemplate.queryForMap(sql.toString());
            }
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
