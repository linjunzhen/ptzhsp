/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.demo.dao.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.demo.dao.ProductDao;

/**
 * 描述  产品操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年1月12日 下午5:33:02
     * @param parentId
     * @param treeData
     * @param tableName
     * @param seqName
     * @return
     */
    public String saveOrUpdateTreeData(String parentId,Map<String,Object> treeData
            ,String tableName,String seqName){
        String path = "";
        int level = 0;
        //获取私有主键名称
        String primaryKeyName = (String) this.getPrimaryKeyName(tableName).get(0);
        if (StringUtils.isNotEmpty(parentId) && !parentId.equals("0")) {
            StringBuffer sql = new StringBuffer("select * from ").append(tableName).append(" WHERE ")
                    .append(primaryKeyName).append("=? ");
            Map<String,Object> parentData = this.getByJdbc(sql.toString(), new Object[]{parentId});
            path = (String) parentData.get("PATH");
            level = Integer.parseInt(parentData.get("DEPT_LEVEL").toString());
        } else {
            parentId = "0";
            path = "0.";
        }
        if (level < 1) {
            level = 1;
        }
        treeData.put("DEPT_LEVEL", level + 1);
        treeData.put("PARENT_ID", parentId);
        int maxSn = jdbcTemplate
                .queryForInt("select max(DEPT_SN) from " + tableName);
        if (maxSn == 0) {
            maxSn = 1;
        }
        // 获取主键值
        String primaryKeyValue = (String) treeData.get(primaryKeyName); 
        if (StringUtils.isEmpty(primaryKeyValue)) {
            // 说明是新增操作
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            treeData.put("CREATE_DATE", createTime);
            treeData.put("DEPT_SN", maxSn + 1);
            treeData.put("PATH",path);
        }
        String entityId = null;
        if (StringUtils.isNotEmpty(primaryKeyValue)) {
            if(StringUtils.isNotEmpty(seqName)){
                entityId = this.saveOrUpdate(treeData, tableName, primaryKeyValue,seqName);
            }else{
                entityId = this.saveOrUpdate(treeData, tableName, primaryKeyValue);
            }
        } else {
            if(StringUtils.isNotEmpty(seqName)){
                entityId = this.saveOrUpdate(treeData, tableName, null,seqName);
            }else{
                entityId = this.saveOrUpdate(treeData, tableName, null);
            }
        }
        if (!entityId.equals("-1")) {
            path = path + entityId + ".";
            treeData.put("PATH", path);
            this.saveOrUpdate(treeData, tableName, entityId);
        }
        return entityId;
    }
}
