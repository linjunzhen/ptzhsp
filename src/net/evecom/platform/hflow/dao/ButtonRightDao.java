/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 按钮权限操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ButtonRightDao extends BaseDao {
    /**
     * 
     * 描述 判断记录是否存在
     * @author Flex Hu
     * @created 2015年8月13日 下午3:27:00
     * @param defId
     * @param nodeName
     * @param buttonKey
     * @return
     */
    public boolean isExist(String defId,String nodeName,String buttonKey,int flowVersion);
    /**
     * 
     * 描述 根据按钮KEY删除掉权限配置记录
     * @author Flex Hu
     * @created 2015年8月13日 下午3:52:05
     * @param buttonKey
     */
    public void deleteByButtonKey(String buttonKey);
    /**
     * 
     * 描述 根据流程定义ID和节点名称删除掉数据
     * @author Flex Hu
     * @created 2015年8月13日 下午4:51:50
     * @param defId
     * @param nodeName
     */
    public void delete(String defId,String nodeName,int flowVersion);
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId);
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
     * 描述 根据流程定义ID和节点名称获取权限配置
     * @author Flex Hu
     * @created 2015年8月13日 下午4:10:32
     * @param nodeName
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findList(String nodeName,String defId,boolean filterAuth,int flowVersion);
}
