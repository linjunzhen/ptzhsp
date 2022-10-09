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
 * 描述 字段权限操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FieldRightService extends BaseService {
    /**
     * 
     * 描述 更新或者保存字段权限
     * @author Flex Hu
     * @created 2015年8月12日 上午9:43:40
     * @param formFields
     * @param nodeDataArray
     * @param defId
     */
    public void saveOrUpdate(List<Map<String,Object>> formFields,JSONArray nodeDataArray
            ,String defId,int flowDefVersion,int oldVersion);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 更新字段的权限
     * @author Flex Hu
     * @created 2015年8月12日 下午3:57:10
     * @param rightIds
     * @param rightFlag
     */
    public void updateRightFlag(String[] rightIds,int rightFlag);
    /**
     * 
     * 描述 根据流程定义ID和节点名称获取字段权限控制值
     * @author Flex Hu
     * @created 2015年8月17日 下午3:14:01
     * @param defId
     * @param nodeName
     * @return
     */
    public List<Map<String,Object>> findList(String defId,String nodeName,int flowVersion);
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
     * 描述 拷贝字段权限数据
     * @author Flex Hu
     * @created 2016年3月31日 下午3:37:29
     * @param targetDefId
     * @param targetVersion
     * @param newDefId
     */
    public void copyFieldRights(String targetDefId,int targetVersion,String newDefId);
}
