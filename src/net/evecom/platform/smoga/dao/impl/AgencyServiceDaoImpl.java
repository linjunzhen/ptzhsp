/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.smoga.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.smoga.dao.AgencyServiceDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月9日 下午3:50:56
 */
@Repository("agencyServiceDao")
public class AgencyServiceDaoImpl extends BaseDaoImpl implements AgencyServiceDao {
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月2日 下午6:11:18
     * @return
     */
    public int getMaxSn() {
        StringBuffer sql = new StringBuffer("select decode(max(C_SN),null,0,max(C_SN)) FROM T_SMOGA_AGENCYSERVICE ");
        return this.jdbcTemplate.queryForInt(sql.toString());
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月2日 下午6:11:18
     * @return
     */
    public int getCurVersion() {
        StringBuffer sql = new StringBuffer();
        sql.append("select decode(max(C_VERSION),null,1,max(C_VERSION)) FROM T_SMOGA_AGENCYSERVICE ");
        return this.jdbcTemplate.queryForInt(sql.toString());
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2018年8月14日 下午2:47:25
     * @param variables
     * @return
     * @see net.evecom.platform.smoga.dao.AgencyServiceDao#saveOrUpdateRecord(java.util.Map)
     */
    public String saveOrUpdateRecord(Map<String, Object> variables){
        String rightId = variables.get("SERVICE_ID").toString();
        String version = variables.get("C_VERSION").toString();
        if(StringUtils.isNotEmpty(rightId)&&isExist(rightId,version)){
            this.updateRecord(variables);
            return rightId;
        }else{
            Set<String> columns = this.getColumnNameByTableName("T_SMOGA_AGENCYSERVICE_HIS");
            Map<String,Object> insertMap = new HashMap<String,Object>();
            rightId = UUIDGenerator.getUUID();
            insertMap.put("SERVICE_ID", rightId);
            StringBuffer sql = new StringBuffer("insert into T_SMOGA_AGENCYSERVICE_HIS (");
            Iterator it = variables.entrySet().iterator();
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
            int result = this.jdbcTemplate.update(sql.toString(),
                    targetVal.toArray());
            if (result == 1) {
                return rightId;
            } else {
                return "-1";
            }
        }
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月3日 上午10:57:22
     * @param rightId
     * @param version
     * @return
     */
    private boolean isExist(String rightId,String version){
        StringBuffer sql = new StringBuffer("select count(*) from T_SMOGA_AGENCYSERVICE_HIS ");
        sql.append(" where SERVICE_ID=? and C_VERSION=? ");
        long count = this.jdbcTemplate.queryForLong(sql.toString(),
                new Object[] { rightId, version});
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月3日 上午11:09:23
     */
    private void updateRecord(Map<String,Object> variables){
        StringBuffer sql = new StringBuffer("update T_SMOGA_AGENCYSERVICE_HIS set ");
        // 获取业务表的列名称
        Set<String> columns = this.getColumnNameByTableName("T_SMOGA_AGENCYSERVICE_HIS");
        //获取业务表大字段的列名称
        List<String> bigColumns=this.getBigFiled("T_SMOGA_AGENCYSERVICE_HIS");
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
                    && !fieldName.toUpperCase().equals("SERVICE_ID")&& !fieldName.toUpperCase().equals("C_VERSION")) {
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
        sql.append(" where SERVICE_ID=? and C_VERSION=? ");
        targetVals.add(variables.get("SERVICE_ID"));
        targetVals.add(variables.get("C_VERSION"));
        if(targetCols.size()>0){
            jdbcTemplate.update(sql.toString(), targetVals.toArray());
        }
    }
}
