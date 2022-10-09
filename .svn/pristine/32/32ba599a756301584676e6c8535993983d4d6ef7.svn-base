/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.LogCongfigDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.LogConfigService;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年9月5日 下午4:53:15
 */
@Service("logConfigService")
public class LogConfigServiceImpl extends BaseServiceImpl implements LogConfigService {

    /**
     * 
     */
    @Resource
    private LogCongfigDao dao;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     * 描述 获取dao
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:52:27
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午5:19:35
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findConfigTableList(String parentId){
        String sql = "select t.* from T_SYSTEM_LOGCONFIG_TABLES t where t.parent_id=? order by t.tree_sn asc";
        return dao.findBySql(sql, new Object[]{parentId}, null);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午6:00:43
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findConfigListBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select D.*,t.CONFIG_NAME from T_SYSTEM_LOGCONFIG_COLUMN D ");
        sql.append("left join T_SYSTEM_LOGCONFIG_TABLES t on t.config_id = D.config_id ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    保存业务表维护日志
     * @author Danto Huang
     * @created 2018年9月6日 下午2:41:41
     * @param busTable
     * @param colNames
     * @param colValues
     * @param variables
     * @param entityId
     */
    public void saveLogForBusTable(String busTable, String colNames, String colValues, Map<String, Object> variables,
            String entityId) {
        if (StringUtils.isNotEmpty(entityId)&&StringUtils.isNotEmpty(colValues)) {
            // 获得数据库原始数据
            Map<String, Object> oldDate = dao.getByJdbc
                    (busTable, new String[] { colNames }, new Object[] { colValues });
            List<Map<String,Object>> columns = this.findLogColumns(busTable);
            if(columns!=null&&columns.size()>0){
                for(Map<String,Object> column : columns){
                    String key = column.get("COLUMN_NAME").toString();
                    String comment = column.get("COLUMN_COMMENT").toString();
                    String isDic = column.get("IS_DIC").toString();
                    String isKey = column.get("IS_PRIMARYKEY").toString();
                    if (variables.containsKey(key)) {
                        String oldValue = oldDate.get(key)==null?"":oldDate.get(key).toString().trim();
                        String newValue = variables.get(key)==null?"":variables.get(key).toString().trim();
                        if (StringUtils.isNotEmpty(newValue)&&!newValue.equals(oldValue)) {
                            StringBuffer content = new StringBuffer();
                            if(isDic.equals("1")){
                                String typeCode = column.get("TYPE_CODE").toString();
                                if(StringUtils.isNotEmpty(newValue)){
                                    newValue = dictionaryService.getDicNames(typeCode, newValue);
                                }
                                if(StringUtils.isEmpty(oldValue)){
                                    oldValue = "<font color='red'>空值</font>";
                                }else{
                                    oldValue = dictionaryService.getDicNames(typeCode, oldValue);
                                }
                            }else if(isKey.equals("1")){
                                String table = column.get("RELATE_TABLE").toString();
                                String relateColumn = column.get("RELATE_COLUMN").toString();
                                String priamryKey = (String) dao.getPrimaryKeyName(table).get(0);
                                StringBuffer sql = new StringBuffer();
                                if(StringUtils.isNotEmpty(newValue)){
                                    sql.append("select wm_concat(").append(relateColumn).append(") result from ")
                                        .append(table).append(" where ").append(priamryKey).append(" in ")
                                        .append(StringUtil.getValueArray(newValue));
                                    newValue = (String) dao.getByJdbc(sql.toString(), null).get("RESULT");
                                }
                                if(StringUtils.isEmpty(oldValue)){
                                    oldValue = "<font color='red'>空值</font>";
                                }else{
                                    sql = new StringBuffer();
                                    sql.append("select wm_concat(").append(relateColumn).append(") result from ")
                                            .append(table).append(" where ").append(priamryKey).append(" in ")
                                            .append(StringUtil.getValueArray(oldValue));
                                    oldValue = (String) dao.getByJdbc(sql.toString(), null).get("RESULT");
                                }
                            }else{
                                if(StringUtils.isEmpty(oldValue)){
                                    oldValue="<font color='red'>空值</font>";
                                }
                            }
                            content.append("【").append(comment).append("】:'");
                            content.append(oldValue).append("'").append(" 改为  '");
                            content.append(newValue).append("'");
                            saveLogForTable(busTable.toUpperCase(),content.toString(), 2, entityId);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     */
    private List<Map<String,Object>> findLogColumns(String busTable){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_SYSTEM_LOGCONFIG_COLUMN t where t.config_id = ( ");
        sql.append("select s.config_id from t_system_logconfig_tables s where s.bustable_name = ?) ");
        return dao.findBySql(sql.toString(), new Object[]{busTable}, null);
    }

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月6日 下午3:30:41
     * @param busTableName
     * @param logContent
     * @param operateType
     * @param busIndex
     */
     private void saveLogForTable(String busTableName,String logContent, 
             int operateType,String busIndex) {
         HttpServletRequest request = AppUtil.getRequest();
         String browser = BrowserUtils.checkBrowse(request);
         String operateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
         SysUser sysUser = AppUtil.getLoginUser();
         String idAddress = BrowserUtils.getIpAddr(request);
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("BROWSER", browser);
         map.put("OPERATE_CONTENT", logContent);
         map.put("OPERATE_TYPE", operateType);
         map.put("OPERATE_TIME", operateTime);
         map.put("FULLNAME", sysUser.getFullname());
         map.put("USERNAME", sysUser.getUsername());
         map.put("USERID", sysUser.getUserId());
         map.put("IP_ADDRESS", idAddress);
         map.put("BUS_INDEX", busIndex);
         map.put("BUS_TABLENAME", busTableName);
         dao.saveOrUpdate(map, "T_MSJW_SYSTEM_BUSLOG", null);
     }
}
