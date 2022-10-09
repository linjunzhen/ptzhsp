/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 按钮权限操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ButtonRightService extends BaseService {
    /**
     * 
     * 描述 保存或者更新节点的按钮权限配置
     * @author Flex Hu
     * @created 2015年8月12日 下午4:40:30
     * @param nodeDataArray
     * @param defId
     */
    public void saveOrUpdate(JSONArray nodeDataArray,String defId,int flowVersion,int oldFlowVersion);
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
     * 描述 根据流程定义ID和节点名称获取权限配置
     * @author Flex Hu
     * @created 2015年8月13日 下午4:10:32
     * @param nodeName
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findList(String nodeName,String defId,boolean filterAuth,int flowVersion);
    /**
     * 
     * 描述 保存按钮权限配置
     * @author Flex Hu
     * @created 2015年8月13日 下午4:45:53
     * @param defId
     * @param nodeName
     * @param rightJson
     */
    public void saveButtonRight(String defId,String nodeName,String rightJson,int flowVersion);
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
     * 描述 保存新配置的按钮权限
     * @author Flex Hu
     * @created 2015年12月3日 上午9:50:49
     * @param defId
     * @param nodeName
     * @param flowVersion
     */
    public void saveNewButtonRight(String defId,String nodeName,int flowVersion);
    /**
     * 
     * 描述 面向省网办发起流程个性化判断权限
     * @author Flex Hu
     * @created 2016年1月25日 下午4:04:17
     * @param flowVars
     * @return
     */
    public boolean isAuthForProvinceSite(Map<String,Object> flowVars);
    /**
     * 
     * 描述 判断是否有挂起按钮权限
     * @author Flex Hu
     * @created 2016年3月18日 下午4:37:25
     * @param flowVars
     * @return
     */
    public boolean isAuthForHandUp(Map<String,Object> flowVars);
    /**
     * 
     * 描述 获取个性化权限之后的按钮列表
     * @author Flex Hu
     * @created 2016年1月25日 下午4:18:50
     * @param oldButtonRightList
     * @return
     */
    public List<Map<String,Object>> getFilterAuthList(List<Map<String,Object>> oldButtonRightList
            ,Map<String,Object> flowVars);
    /**
     * 
     * 描述 拷贝按钮权限数据
     * @author Flex Hu
     * @created 2016年3月31日 上午10:58:16
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyButtonRights(String targetDefId,int targetFlowVersion,String newDefId); 
}
