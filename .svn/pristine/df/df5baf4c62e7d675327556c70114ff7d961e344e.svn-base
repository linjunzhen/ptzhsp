/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.wsbs.dao.BusTypeDao;

/**
 * 描述  业务类别操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("busTypeDao")
public class BusTypeDaoImpl extends BaseDaoImpl implements BusTypeDao {

    /**
     * 
     * 描述 根据材料ID获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月17日 下午5:41:55
     * @param materId
     * @return
     */
    public Map<String,Object> getIdAndNamesByMaterId(String materId){
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(BT.TYPE_ID) TYPE_IDS");
        sql.append(",WMSYS.WM_CONCAT(BT.TYPE_NAME) TYPE_NAMES FROM T_WSBS_BUSTYPE BT ");
        sql.append("WHERE BT.TYPE_ID IN (SELECT BM.TYPE_ID FROM T_WSBS_BUSTYPE_MATER BM");
        sql.append(" WHERE BM.MATER_ID=? )");
        return this.jdbcTemplate.queryForMap(sql.toString(),new Object[]{materId});
    }
    
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getIdAndNamesByItemId(String itemId){
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(BT.TYPE_ID) TYPE_IDS");
        sql.append(",WMSYS.WM_CONCAT(BT.TYPE_NAME) TYPE_NAMES FROM T_WSBS_BUSTYPE BT ");
        sql.append("WHERE BT.TYPE_ID IN (SELECT BM.TYPE_ID FROM T_WSBS_SERVICEITEM_TYPE BM");
        sql.append(" WHERE BM.ITEM_ID=? )");
        return this.jdbcTemplate.queryForMap(sql.toString(),new Object[]{itemId});
    }
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public List<Map<String, Object>> getParentNamesAndNamesByItemId(String itemId){
        StringBuffer sql = new StringBuffer("SELECT T2.TYPE_NAME AS PNAME,T.TYPE_NAME FROM T_WSBS_BUSTYPE T ");
        sql.append("LEFT JOIN T_WSBS_BUSTYPE T2 ON T.PARENT_ID = T2.TYPE_ID ");
        sql.append("WHERE T.TREE_LEVEL = 3 AND T.TYPE_ID  ");
        sql.append("IN (SELECT BM.TYPE_ID FROM T_WSBS_SERVICEITEM_TYPE BM WHERE BM.ITEM_ID=? ) ");
        sql.append("ORDER BY T2.TYPE_NAME ");
        return this.jdbcTemplate.queryForList(sql.toString(),new Object[]{itemId});
    }
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getLsIdAndNamesByItemId(String itemId){
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(BT.TYPE_ID) TYPE_IDS");
        sql.append(",WMSYS.WM_CONCAT(BT.TYPE_NAME) TYPE_NAMES FROM T_WSBS_BUSTYPE BT ");
        sql.append("WHERE BT.TYPE_ID IN (SELECT BM.TYPE_ID FROM T_WSBS_SERVICEITEM_LSTYPE BM");
        sql.append(" WHERE BM.ITEM_ID=? )");
        return this.jdbcTemplate.queryForMap(sql.toString(),new Object[]{itemId});
    }
    
    /**
     * 
     * 描述 保存类别项目中间表
     * @author Flex Hu
     * @created 2015年9月22日 下午3:17:40
     * @param typeIds
     * @param itemId
     */
    public void saveBusTypeItem(String[] typeIds,String itemId){
        StringBuffer sql = new StringBuffer("insert into ");
        sql.append("T_WSBS_SERVICEITEM_TYPE(ITEM_ID,TYPE_ID) ");
        sql.append(" values(?,?)");
        for(String typeId:typeIds){
            this.jdbcTemplate.update(sql.toString(),new Object[]{itemId,typeId});
        }
    }
}
