/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 查询按钮权限操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface QueryButtonAuthService extends BaseService {
    /**
     * 
     * 描述 保存或者获取列表
     * @author Flex Hu
     * @created 2016年1月12日 上午9:00:51
     * @param buttonList
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>>  saveOrGetList(List<Map<String,Object>> buttonList
            ,String defId,int flowVersion);
    /**
     * 
     * 描述 保存按钮权限
     * @author Flex Hu
     * @created 2016年1月12日 上午10:51:38
     * @param variables
     */
    public void saveButtonAuths(Map<String,Object> variables);
    /**
     * 
     * 描述 根据流程定义ID和版本号获取查看按钮列表
     * @author Flex Hu
     * @created 2016年1月12日 上午11:26:30
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findList(String defId,int flowVersion);
    /**
     * 
     * 描述 拷贝查询按钮权限
     * @author Flex Hu
     * @created 2016年3月31日 下午4:17:40
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyQueryButtons(String targetDefId,int targetFlowVersion,String newDefId);
}
