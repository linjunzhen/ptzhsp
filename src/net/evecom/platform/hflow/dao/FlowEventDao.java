/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 流程事件操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FlowEventDao extends BaseDao {
    /**
     * 
     * 描述 判断是否存在记录
     * @author Flex Hu
     * @created 2015年8月12日 下午5:08:55
     * @param defId
     * @param eventType
     * @param nodeName
     * @return
     */
    public boolean isExists(String defId,String eventType,String nodeName,int flowVersion);
    /**
     * 
     * 描述 根据流程定义ID和节点名称删除事件
     * @author Flex Hu
     * @created 2015年8月13日 上午10:01:24
     * @param defId
     * @param nodeName
     */
    public void deleteEvent(String defId,String nodeName,int flowVersion);
//    /**
//     *
//     * 描述 获取事件配置代码
//     * @author Flex Hu
//     * @created 2015年8月18日 上午11:21:25
//     * @param defId
//     * @param nodeName
//     * @param eventType
//     * @return
//     */
//    2020年4月27日Adrian Bian 注掉
//    public String getEventCode(String defId,String nodeName,String eventType,int flowVersion);
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion);

    /**
     *
     * 描述 获取事件配置代码
     * @author Adrian Bian
     * @created 2020年03月19日 上午11:21:25
     * @param defId
     * @param nodeName
     * @param eventType
     * @param flowVersion
     * @return
     */
    public List<String> findEventCodeList(String defId, String nodeName, String eventType, int flowVersion);
}
