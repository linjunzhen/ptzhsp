/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.encryption.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.encryption.dao.EncryptionDao;
import net.evecom.platform.encryption.service.EncryptionService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年5月12日 上午9:41:04
 */
@Service("encryptionService")
public class EncryptionServiceImpl extends BaseServiceImpl implements EncryptionService {

    /**
     * 
     */
    @Resource
    private EncryptionDao dao;
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年5月12日 上午9:43:23
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao(){
        return dao;
    }
    
    /**
     * 
     * 描述   加密表配置树查询
     * @author Danto Huang
     * @created 2020年5月12日 上午10:32:48
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findConfigTableList(String parentId){
        String sql = "select t.* from ENCRYPTION_CONFIG_TABLE t where t.parent_id=? order by t.tree_sn asc";
        return dao.findBySql(sql, new Object[]{parentId}, null);
    }
    
    /**
     * 
     * 描述    根据加密表配置id删除
     * @author Danto Huang
     * @created 2020年5月12日 上午10:53:59
     * @param tableId
     */
    public void removeByTableId(String tableId){
        StringBuffer deleteColumn = new StringBuffer();
        deleteColumn.append("delete from ENCRYPTION_CONFIG_COLUMN D WHERE D.CONFIG_ID=? ");
        dao.executeSql(deleteColumn.toString(), new Object[] { tableId });
        dao.remove("ENCRYPTION_CONFIG_TABLE", "CONFIG_ID", new Object[] { tableId });
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月12日 下午3:15:42
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findConfigListBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select D.*,t.CONFIG_NAME from ENCRYPTION_CONFIG_COLUMN D ");
        sql.append("left join ENCRYPTION_CONFIG_TABLE t on t.config_id = D.config_id ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    查询表待加密记录
     * @author Danto Huang
     * @created 2020年5月21日 上午10:25:13
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findNeedEncryptionDataByTable(SqlFilter filter){
        String configId = filter.getRequest().getParameter("configId");
        Map<String, Object> config = dao.getByJdbc("ENCRYPTION_CONFIG_TABLE",
                new String[] { "CONFIG_ID" }, new Object[] { configId });
        if(config!=null){
            String orderColumn = config.get("ORDER_COLUMN").toString();
            String csql = "select wmsys.wm_concat(t.column_name) columnname from ENCRYPTION_CONFIG_COLUMN t "
                    + "where t.config_id=?";
            String column = (String) dao.getByJdbc(csql, new Object[]{configId}).get("COLUMNNAME");
            
            StringBuffer sql = new StringBuffer("select ");
            sql.append(config.get("BUSTABLE_PRIMARYKEY")).append(" record_id,").append(column);
            sql.append(" from ").append(config.get("BUSTABLE_NAME")).append(" where ");
            sql.append(config.get("BUSTABLE_PRIMARYKEY")).append(" not in(");
            sql.append("select TABLE_RECORD_ID from ENCRYPTION_DATA_MIDDLE where BUSTABLE_NAME='");
            sql.append(config.get("BUSTABLE_NAME")).append("')");
            sql.append("and ").append(orderColumn).append("<='").append(config.get("MAX_ORDER_VAL")).append("' ");
            
            filter.addSorted(orderColumn, "desc");

            List<Object> params = new ArrayList<Object>();
            String exeSql = dao.getQuerySql(filter, sql.toString(), params);
            List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
            return list;
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述    表加密
     * @author Danto Huang
     * @created 2020年5月13日 下午2:12:19
     * @param selectColNames
     * @param configId
     */
    public void tableEncryption(String selectColNames,String configId){
        if(StringUtils.isNotEmpty(selectColNames)){
            Map<String, Object> config = dao.getByJdbc("ENCRYPTION_CONFIG_TABLE", new String[] { "CONFIG_ID" },
                    new Object[] { configId });
            String busTableName = (String) config.get("BUSTABLE_NAME");
            String primaryKey = (String) config.get("BUSTABLE_PRIMARYKEY");
            String sql = "select wmsys.wm_concat(t.column_name) columnname from ENCRYPTION_CONFIG_COLUMN t "
                    + "where t.config_id=?";
            String column = (String) dao.getByJdbc(sql, new Object[]{configId}).get("COLUMNNAME");
            String[] columns = column.split(",");
            
            String[] recordIds = selectColNames.split(",");
            for(String recordId : recordIds){
                StringBuffer rsql = new StringBuffer();
                rsql.append("select * from ").append(busTableName).append(" where ").append(primaryKey);
                rsql.append("='").append(recordId).append("'");
                Map<String, Object> record = dao.getByJdbc(rsql.toString(), null);
                Map<String,Object> variables = new HashMap<String, Object>();
                for(String columnName : columns){
                    if(record.get(columnName)!=null){
                        variables.put(columnName, record.get(columnName));
                    }
                }
                dao.saveOrUpdate(variables, busTableName, (String) record.get(primaryKey));
                
                Map<String,Object> middle = new HashMap<String, Object>();
                middle.put("BUSTABLE_NAME", busTableName);
                middle.put("TABLE_RECORD_ID", recordId);
                middle.put("ENCRYPTED_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                dao.saveOrUpdate(middle, "ENCRYPTION_DATA_MIDDLE", null);
            }
        }
    }
    
    /**
     * 
     * 描述    List数据解密
     * @author Danto Huang
     * @created 2020年5月19日 下午3:23:40
     * @param list
     * @param tableName
     * @return
     */
    public List<Map<String,Object>> listDecrypt(List<Map<String,Object>> list,String tableName){
        Map<String, Object> config = dao.getByJdbc("ENCRYPTION_CONFIG_TABLE", new String[] { "BUSTABLE_NAME" },
                new Object[] { tableName.toUpperCase() });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            String csql = "select wmsys.wm_concat(t.column_name) columnname from ENCRYPTION_CONFIG_COLUMN t "
                    + "where t.config_id=?";
            String column = (String) dao.getByJdbc(csql, new Object[]{config.get("CONFIG_ID")}).get("COLUMNNAME");
            String[] columns = column.split(",");
            if(list!=null&&list.size()>0){
                for(Map<String,Object> map : list){
                    for(String columnName : columns){
                        if(map.get(columnName)!=null){
                            String val = (String) map.get(columnName);
                            String newVal = sm4.decryptDataCBC(val);
                            if(StringUtils.isNotEmpty(newVal)){
                                map.put(columnName, newVal);
                            }else{
                                map.put(columnName, val);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
    /**
     * 
     * 描述    Map数据解密
     * @author Danto Huang
     * @created 2020年5月19日 下午3:23:54
     * @param map
     * @param tableName
     * @return
     */
    public Map<String,Object> mapDecrypt(Map<String,Object> map,String tableName){
        Map<String, Object> config = dao.getByJdbc("ENCRYPTION_CONFIG_TABLE", new String[] { "BUSTABLE_NAME" },
                new Object[] { tableName.toUpperCase() });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            String csql = "select wmsys.wm_concat(t.column_name) columnname from ENCRYPTION_CONFIG_COLUMN t "
                    + "where t.config_id=?";
            String column = (String) dao.getByJdbc(csql, new Object[]{config.get("CONFIG_ID")}).get("COLUMNNAME");
            String[] columns = column.split(",");
            for(String columnName : columns){
                if(map.get(columnName)!=null){
                    String val = (String) map.get(columnName);
                    String newVal = sm4.decryptDataCBC(val);
                    if(StringUtils.isNotEmpty(newVal)){
                        map.put(columnName, newVal);
                    }else{
                        map.put(columnName, val);
                    }
                }
            }
        }
        return map;
    }
}
