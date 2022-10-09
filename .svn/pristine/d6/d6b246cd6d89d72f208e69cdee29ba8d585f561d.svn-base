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
import net.evecom.platform.hflow.model.FlowNextStep;

/**
 * 描述 审批材料操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface MaterConfigService extends BaseService {
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存模版配置信息
     * @author Flex Hu
     * @created 2016年1月29日 上午9:53:15
     * @param defId
     * @param nodeName
     * @param tplIds
     */
    public void saveOrUpdate(String defId,String nodeName,String tplIds);
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] configIds);
    /***
     * 
     * 描述 根据流程定义ID和节点名称和模版类型获取数据列表
     * @author Flex Hu
     * @created 2016年1月30日 下午3:43:57
     * @param defId
     * @param nodeName
     * @param documentType
     * @return
     */
    public List<Map<String,Object>> findList(String defId,String nodeName,String documentType,String exeId);
    /**
     * 
     * 描述 获取下一环节具备查看审核材料权限的部门列表
     * @author Flex Hu
     * @created 2016年1月30日 下午5:05:14
     * @param steps
     * @return
     */
    public List<Map<String,Object>> findMaterAuiderDeps(List<FlowNextStep> steps);
    
    /**
     * 
     * 描述 
     * @author Flex Hu
     * @created 2016年3月22日 上午11:07:01
     * @param defId
     * @param nodeName
     * @param steps
     * @param publicDocRule 公文流转方式(-1:按个人 1:按组织)
     * @return
     */
    public List<Map<String,Object>> findNextStepPublicDocs(String defId,String nodeName,
            List<FlowNextStep> steps,String publicDocRule,String exeId);
    /**
     * 
     * 描述 根据定义ID和节点名称获取公文JSON
     * @author Flex Hu
     * @created 2016年3月3日 下午8:51:14
     * @param defId
     * @param nodeName
     * @return
     */
    public String getPublicDocJson(String defId,String nodeName,String exeId);
    /**
     * 
     * 描述 拷贝材料配置信息
     * @author Flex Hu
     * @created 2016年3月31日 下午3:53:13
     * @param targetDefId
     * @param newDefId
     */
    public void copyMaterConfigs(String targetDefId,String newDefId);
    /**
     * 
     * 描述 设置模版是否回填
     * @author Faker Li
     * @created 2016年4月1日 下午3:37:16
     * @param isBackfill
     * @param configIds
     */
    public void updateIsBackfill(String isBackfill, String configIds);
}
