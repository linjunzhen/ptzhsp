/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import com.alibaba.fastjson.JSONArray;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述 流程事件操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowEventService extends BaseService {
    /**
     * 
     * 描述 保存或者更新节点的事件配置
     * @author Flex Hu
     * @created 2015年8月12日 下午4:40:30
     * @param nodeDataArray
     * @param defId
     */
    public void saveOrUpdate(JSONArray nodeDataArray,String defId,int flowVersion,int oldVersion);
    /**
     * 
     * 描述 存储业务数据
     * @author Flex Hu
     * @created 2015年8月12日 下午4:52:20
     * @param flowDatas
     * @return
     */
    public Map<String,Object> saveBusData(Map<String,Object> flowDatas);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存事件配置信息
     * @author Flex Hu
     * @created 2015年8月13日 上午9:59:37
     * @param defId
     * @param nodeName
     * @param eventJson
     */
    public void saveEvent(String defId,String nodeName,String eventJson,int flowVersion);
    /**
     *
     * 描述 生成社会保险登记表
     * @author Water Guo
     * @created 2019年3月4日 上午9:59:37
     */
    public void createSocialForm(String exeId);

    /**
     * 描述:社会保险登记表数据回填
     *
     * @author Madison You
     * @created 2020/11/11 15:25:00
     * @param
     * @return
     */
    public Map<String, Object> shbxdjbTableData(Map<String, Map<String, Object>> args);

    /**
     * 描述:养老工伤保险申请表数据回填
     *
     * @author Madison You
     * @created 2020/11/11 10:58:00
     * @param
     * @return
     */
    public Map<String, Object> ylgsbxsqbTableData(Map<String,Map<String,Object>> args);

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
//     2020年4月27日Adrian Bian 注掉以避免前端配置多事件，此处查询报错
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
     * 描述 拷贝事件数据
     * @author Flex Hu
     * @created 2016年3月31日 上午11:10:49
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyEvents(String targetDefId,int targetFlowVersion,String newDefId);
    
    /**
     * 
     * 描述 存储业务数据
     * @author Flex Hu
     * @created 2015年8月12日 下午4:52:20
     * @param flowDatas
     * @return
     */
    public Map<String,Object> saveTzxmZTBBusData(Map<String,Object> flowDatas);

    /**
     * 
     * 描述   存储商事业务数据
     * @author Danto Huang
     * @created 2016-11-15 下午3:29:44
     * @param flowDatas
     * @return
     */
    public Map<String,Object> saveCommercialBusData(Map<String,Object> flowDatas);
    
    /**
     * 
     * 描述 发送办件结果邮递信息
     * @author Kester Chen
     * @created 2018-7-10 下午2:43:54
     * @param flowDatas
     * @return
     */
    public Map<String,Object> sendResultEMSInfo(Map<String,Object> flowDatas);
    
    /**
     * 
     * 描述 发送材料邮递信息
     * @author Kester Chen
     * @created 2018-7-10 下午2:43:54
     * @param flowDatas
     * @return
     */
    public Map<String,Object> sendMatEMSInfo(Map<String,Object> flowDatas);

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
    public List<String> findEventCodeList(String defId,String nodeName,String eventType,int flowVersion);

    /**
     * 
     * 描述： 工程建设项目分发流程存储业务数据
     * @author Rider Chen
     * @created 2020年9月8日 下午5:56:26
     * @param flowDatas
     * @return
     * @see net.evecom.platform.hflow.service.FlowEventService#saveBusData(java.util.Map)
     */
    public Map<String, Object> saveDistributeBusData(Map<String, Object> flowDatas);
}
