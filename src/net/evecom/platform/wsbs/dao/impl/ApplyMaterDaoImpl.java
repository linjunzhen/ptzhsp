/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.ApplyMaterDao;

/**
 * 描述  申请材料操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("applyMaterDao")
public class ApplyMaterDaoImpl extends BaseDaoImpl implements ApplyMaterDao {

    /**
     * 
     * 描述 保存申请材料主题服务类别中间表
     * @author Flex Hu
     * @created 2015年9月17日 下午4:49:24
     * @param materId
     * @param typeIds
     */
    public void saveMaterBusTypes(String materId,String[] typeIds){
        StringBuffer sql = new StringBuffer("insert into ");
        sql.append("T_WSBS_BUSTYPE_MATER(TYPE_ID,MATER_ID) values(?,?)");
        for(String typeId:typeIds){
            this.jdbcTemplate.update(sql.toString(),new Object[]{typeId,materId});
        }
    }
    
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterByItem(String itemId){
        StringBuffer sql = new StringBuffer("delete from T_WSBS_SERVICEITEM_MATER");
        sql.append(" M WHERE M.ITEM_ID=? ");
        this.jdbcTemplate.update(sql.toString(),new Object[]{itemId});
    }
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterItem(String materIds,String itemId){
        StringBuffer sql = new StringBuffer("delete from T_WSBS_SERVICEITEM_MATER");
        sql.append(" M WHERE M.ITEM_ID=? AND M.MATER_ID IN ");
        sql.append(StringUtil.getValueArray(materIds));
        this.jdbcTemplate.update(sql.toString(),new Object[]{itemId});
    }
    
    /**
     * 
     * 描述 保存材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:52:05
     * @param materIds
     * @param itemId
     */
    public void saveMaterItem(String materIds,String itemId){
        String[] materIdArray = materIds.split(",");
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SERVICEITEM_MATER");
        sql.append("(ITEM_ID,MATER_ID,MATER_SN) values(?,?,?) ");
        for(String materId:materIdArray){
            //先判断是否存在记录
            boolean exists = this.isExistMaterItem(materId, itemId);
            if(!exists){
                int maxSn = this.getMaxSn(itemId);
                int orderSn = maxSn+1;
                this.jdbcTemplate.update(sql.toString(),new Object[]{itemId,materId,orderSn});
            }
        }
    }
    
    /**
     * 
     * 描述 判断是否存在材料和办事项目
     * @author Flex Hu
     * @created 2015年9月23日 上午9:54:57
     * @param materId
     * @param itemId
     * @return
     */
    public boolean isExistMaterItem(String materId,String itemId){
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append("T_WSBS_SERVICEITEM_MATER SM WHERE SM.ITEM_ID=? ");
        sql.append(" AND SM.MATER_ID=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{itemId,materId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 获取中间表最大排序值
     * @author Flex Hu
     * @created 2015年9月23日 上午9:59:20
     * @param materId
     * @param itemId
     * @return
     */
    public int getMaxSn(String itemId){
        StringBuffer sql = new StringBuffer("select max(SM.MATER_SN) from T_WSBS_SERVICEITEM_MATER");
        sql.append(" SM WHERE SM.ITEM_ID=? ");
        int maxSn = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{itemId});
        return maxSn;
    }
    
    /**
     * 
     * 描述 更新排序值
     * @author Flex Hu
     * @created 2015年9月23日 上午11:27:27
     * @param itemId
     * @param materIds
     */
    public void updateSn(String itemId,String[] materIds){
        int[] oldSns = new int[materIds.length];
        StringBuffer sql1 = new StringBuffer("select M.MATER_SN FROM ");
        sql1.append("T_WSBS_SERVICEITEM_MATER M WHERE M.ITEM_ID=? AND M.MATER_ID=? ");
        for (int i = 0; i < oldSns.length; i++) {
            int dicSn = jdbcTemplate.queryForInt(sql1.toString(), new Object[] {itemId,materIds[i]});
            oldSns[i] = dicSn;
        }
        int[] newSns = ArrayUtil.sortByAsc(oldSns);
        StringBuffer updateSql = new StringBuffer("update T_WSBS_SERVICEITEM_MATER T ")
                .append(" SET T.MATER_SN=? WHERE T.MATER_ID=? AND T.ITEM_ID=? ");
        for (int i = 0; i < materIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] {newSns[i],materIds[i],itemId});
        }
    }

    /**
     * 
     * 描述 修改绑定材料是否为必须提供
     * @author Faker Li
     * @created 2015年11月4日 下午3:26:26
     * @param isneed
     * @param itemId
     * @param materIds
     */
    public void updateIsneed(String isneed, String itemId, String materIds) {
        StringBuffer sql = new StringBuffer("update T_WSBS_SERVICEITEM_MATER");
        sql.append(" M SET M.MATER_ISNEED = ? WHERE M.ITEM_ID=? AND M.MATER_ID IN ");
        sql.append(StringUtil.getValueArray(materIds));
        this.jdbcTemplate.update(sql.toString(),new Object[]{isneed,itemId});
    }

    /**
     * 
     * 描述 更新材料过滤参数
     * @author Faker Li
     * @created 2016年3月7日 下午1:15:17
     * @param fpara
     * @param materIds
     * @param itemId
     */
    public void updateFilterPara(String fpara, String materIds, String itemId) {
        StringBuffer sql = new StringBuffer("update T_WSBS_SERVICEITEM_MATER");
        sql.append(" M SET M.MATER_FPARA = ? WHERE M.ITEM_ID=? AND M.MATER_ID IN ");
        sql.append(StringUtil.getValueArray(materIds));
        this.jdbcTemplate.update(sql.toString(),new Object[]{fpara,itemId});
        
    }

    @Override
    public void updateFlowUserMaterStatus(String ids,String nextTaskName) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("update JBPM6_FLOW_USER_MATER");
        sql.append(" M SET M.STATUS = 1,M.NEXT_TASK_NAME=? WHERE M.FLOW_USER_MATER_ID IN ");
        sql.append(StringUtil.getValueArray(ids));
        this.jdbcTemplate.update(sql.toString(),new Object[]{nextTaskName});
    }
}
