/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 申请材料操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ApplyMaterDao extends BaseDao {
    /**
     * 
     * 描述 保存申请材料主题服务类别中间表
     * @author Flex Hu
     * @created 2015年9月17日 下午4:49:24
     * @param materId
     * @param typeIds
     */
    public void saveMaterBusTypes(String materId,String[] typeIds);
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterItem(String materIds,String itemId);
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterByItem(String itemId);
    /**
     * 
     * 描述 保存材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:52:05
     * @param materIds
     * @param itemId
     */
    public void saveMaterItem(String materIds,String itemId);
    /**
     * 
     * 描述 判断是否存在材料和办事项目
     * @author Flex Hu
     * @created 2015年9月23日 上午9:54:57
     * @param materId
     * @param itemId
     * @return
     */
    public boolean isExistMaterItem(String materId,String itemId);
    /**
     * 
     * 描述 获取中间表最大排序值
     * @author Flex Hu
     * @created 2015年9月23日 上午9:59:20
     * @param materId
     * @param itemId
     * @return
     */
    public int getMaxSn(String itemId);
    /**
     * 
     * 描述 更新排序值
     * @author Flex Hu
     * @created 2015年9月23日 上午11:27:27
     * @param itemId
     * @param materIds
     */
    public void updateSn(String itemId,String[] materIds);
    /**
     * 
     * 描述 修改是否材料是否为必须提供
     * @author Faker Li
     * @created 2015年11月4日 下午3:25:45
     * @param isneed
     * @param itemId
     * @param materIds
     */
    public void updateIsneed(String isneed, String itemId, String materIds);
    /**
     * 
     * 描述 更新材料的过滤参数
     * @author Faker Li
     * @created 2016年3月7日 下午1:14:29
     * @param fpara
     * @param materIds
     * @param itemId
     */
    public void updateFilterPara(String fpara, String materIds, String itemId);
    
    /**
     * 
     * 描述 更新状态
     * @author Rider Chen
     * @created 2016-4-19 下午05:00:38
     * @param ids
     */
    public void updateFlowUserMaterStatus(String ids,String nextTaskName);
}
