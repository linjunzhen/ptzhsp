/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程定义操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowDefService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据流程定义KEY判断是否存在
     * @author Flex Hu
     * @created 2015年8月7日 上午9:01:13
     * @param defKey
     * @return
     */
    public boolean isExists(String defKey);
    /**
     * 
     * 描述 将绑定表单的ID更新为空
     * @author Flex Hu
     * @created 2015年8月10日 上午10:49:42
     * @param formId
     */
    public void updateBindFormIdToNull(String formId);
    /**
     * 
     * 描述 部署流程定义
     * @author Flex Hu
     * @created 2015年8月12日 上午9:20:26
     * @param flowDef
     * @param defId
     */
    public void deployFlow(Map<String,Object> flowDef,String defId);
    /**
     * 
     * 描述 根据流程定义获取流程版本号
     * @author Flex Hu
     * @created 2015年8月15日 下午1:12:57
     * @param defId
     * @return
     */
    public int getFlowVersion(String defId);
    /**
     * 
     * 描述 生成流程图片到磁盘
     * @author Flex Hu
     * @created 2015年8月17日 下午12:47:41
     * @param defXml
     * @param currentNodeNames
     * @param targetPath
     */
    //public void genFlowImgToDisk(String defXml,Set<String> currentNodeNames,String targetPath);
    /**
     * 
     * 描述 生成流程图片到磁盘
     * @author Flex Hu
     * @created 2015年12月21日 下午1:35:24
     * @param defXml :定义XML
     * @param targetPath:目标磁盘路径
     * @param changeColorNodeNames 
     */
    public void genFlowImgToDisk(String defXml,String targetPath,Map<String,Set<String>> changeColorNodeNames);
    /**
     * 
     * 描述 删除掉流程定义数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:46:57
     * @param defIds
     */
    public void deleteFlowDef(String[] defIds);
    /**
     * 
     * 描述 获取ID和名称列表
     * @author Flex Hu
     * @created 2015年10月26日 下午4:57:48
     * @return
     */
    public List<Map<String,Object>> findIdAndName();
    /**
     * 
     * 描述 根据流程定义ID获取流程定义名称S
     * @author Flex Hu
     * @created 2015年10月26日 下午5:20:54
     * @param defIds
     * @return
     */
    public String getDefNames(String defIds);
    /**
     * 
     * 描述 拷贝流程定义和配置相关信息
     * @author Flex Hu
     * @created 2016年3月31日 上午10:19:58
     * @param targetDefKey
     * @param newDefName
     * @param newDefKey
     * @return
     */
    public String copyFlowDefAndConfig(String targetDefKey,String newDefName,String newDefKey);
    /**
     * 
     * 描述 获取ID和名称列表(部门服务流程)
     * @author Flex Hu
     * @created 2015年10月26日 下午4:57:48
     * @return
     */
    public List<Map<String,Object>> findIdAndNameForDept();
}
