/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.model.FlowNextHandler;

import java.util.List;
import java.util.Map;

/**
 * 描述 服务事项操作service
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ServiceItemService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 获取到log数据列表
     * 
     * @param itemId
     * @return
     */
    public List<Map<String, Object>> findLogByItemId(String itemId,String type);

    /**
     * 
     * 描述 根据项目编码判断是否存在记录
     * 
     * @author Flex Hu
     * @created 2015年9月22日 上午11:28:45
     * @param itemCode
     * @return
     */
    public boolean isExists(String itemCode);

    /**
     * 
     * 描述 更新或者保存服务项目
     * 
     * @author Flex Hu
     * @created 2015年9月22日 下午3:02:14
     * @param serviceItem
     * @return
     */
    public String saveOrUpdateCascade(Map<String, Object> serviceItem);

    /**
     * 
     * 描述 保存办事项目用户中间表
     * 
     * @author Flex Hu
     * @created 2015年9月24日 下午5:48:57
     * @param itemId
     * @param userIds
     */
    public void saveItemUsers(String itemId, String userIds);

    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * 
     * @author Flex Hu
     * @created 2015年9月24日 下午5:57:47
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindUserIdANdNames(String itemId);

    /**
     * 
     * 描述 从省网办同步办事项目数据
     * 
     * @author Flex Hu
     * @created 2015年10月12日 下午5:02:07
     * @param params
     */
    public void saveDatasFromProvince(Map<String, Object> dataAbutment);

    /**
     * 
     * 描述 根据省网办XML新增或者更新数据
     * 
     * @author Flex Hu
     * @created 2015年10月16日 上午10:42:03
     * @param xmlInfo
     */
    public String saveOrUpdateFromProvinceXml(String xmlInfo);

    /**
     * 
     * 描述 根据事项编码获取事项ID
     * 
     * @author Flex Hu
     * @created 2015年10月19日 下午4:06:32
     * @param itemCode
     * @return
     */
    public String getItemId(String itemCode);

    /**
     * 
     * 描述 保存事项票单模板配置
     * 
     * @author Faker Li
     * @created 2015年10月16日 下午5:27:23
     * @param itemId
     * @param ticketsIds
     */
    public void saveItemTickets(String itemId, String ticketsIds);

    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * 
     * @author Faker Li
     * @created 2015年10月19日 上午9:09:46
     * @param entityId
     * @return
     */
    public Map<String, Object> getBindTicketsIdANdNames(String itemId);

    /**
     * 
     * 描述 根据项目ID获取绑定公文的IDSNAMES
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午3:01:03
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindDocumentIdANdNames(String itemId);

    /**
     * 
     * 描述 保存事物和公文的中间表
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午3:08:36
     * @param itemId
     * @param documentIds
     */
    public void saveItemDocument(String itemId, String documentIds);

    /**
     * 
     * 描述 根据项目ID获取绑定阅办的IDSNAMES
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午3:01:03
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindReadIdANdNames(String itemId);

    /**
     * 
     * 描述 保存阅办的中间表
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午3:08:36
     * @param itemId
     * @param readIds
     */
    public void saveItemRead(String itemId, String readIds);

    /**
     * 
     * 描述 根据组织机构取出草稿办事事项
     * 
     * @author Faker Li
     * @created 2015年10月20日 下午4:59:26
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByDraftSqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 获得审核列表
     * 
     * @author Faker Li
     * @created 2015年10月21日 上午10:34:59
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByAuditingSqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 获得发布库列表
     * 
     * @author Faker Li
     * @created 2015年10月21日 下午4:38:34
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter);
    /**
     *
     * 描述 获取所有事项列表
     *
     * @author Faker Li
     * @created 2015年10月21日 下午4:39:46
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#findByPublishSqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findByAllServiceItemSqlFilter(SqlFilter sqlFilter) ;

    /**
     * 
     * 描述 获得一窗通办列表
     * 
     * @author Faker Li
     * @created 2015年10月21日 下午4:38:34
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByPublishYctbSqlFilter(SqlFilter sqlFilter);
    /**
     * 根据事项编码获取事项具体信息
     * @param itemCode
     * @return
     */
    public Map<String,Object> getItemInfoByItemCode(String itemCode);

    /**
     * 
     * 描述 更新办事状态
     * 
     * @author Faker Li
     * @created 2015年10月22日 上午10:15:26
     * @param selectColNames
     * @param state
     */
    public void updateFwsxzt(String selectColNames, String state);

    /**
     * 
     * 描述 获取服务事项和流程表单信息
     * 
     * @author Flex Hu
     * @created 2015年10月27日 上午11:12:11
     * @param itemCode
     * @return
     */
    public Map<String, Object> getItemAndDefInfo(String itemCode);

    /**
     * 
     * 描述 获取服务事项和流程表单信息new
     * 
     * @author Sundy Sun
     * @param itemCode
     * @return
     */
    public Map<String, Object> getItemAndDefInfoNew(String itemCode);
    /**
     * 
     * 描述 根据目录编号获取项目编码
     * 
     * @author Faker Li
     * @created 2015年10月28日 下午5:07:19
     * @param catalogCode
     * @return
     */
    public String getMaxNumCode(String catalogCode);

    /**
     * 
     * 描述 获取预审人员数据
     * 
     * @author Flex Hu
     * @created 2015年8月20日 下午1:13:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getPreAuditers(Map<String, Object> flowVars, String varName, String handlerRule);
    /**
     * 根据事项名查找事项
     * @param itemName
     * @param page
     * @param rows
     * @return
     */
    public List<Map<String,Object>> findItemForRobot(String itemName,String page,String rows);
    /**
     * 
     * 描述 获取服务事项绑定的表单列表
     * @author Flex Hu
     * @created 2015年11月16日 下午4:30:11
     * @param exeId
     * @param itemCode
     * @return
     */
    public List<Map<String,Object>> findBindForms(String exeId,String itemCode);
    /**
     * 
     * 描述 绑定表单
     * @author Faker Li
     * @created 2015年11月16日 下午5:30:46
     * @param itemId
     * @param bindFormIds
     */
    public void saveItemFormIds(String itemId, String bindFormIds);
    /**
     * 
     * 描述 获取绑定表单
     * @author Faker Li
     * @created 2015年11月17日 上午9:08:55
     * @param entityId
     * @return
     */
    public Map<String, Object> getBindFormIdAndName(String itemId);
    /**
     * 
     * 描述 获取前台列表
     * @author Faker Li
     * @created 2015年11月23日 下午3:16:53
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findfrontList(String page, String rows,String busType,String sfzxbl);
    
    /**
     * 
     * 描述 获取前台列表
     * @author Faker Li
     * @created 2015年11月23日 下午3:16:53
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findfrontListNew(String page, String rows,String busType,
            String busTypeIds,String itemName,String sfzxbl);
    /**
     * 
     * 描述 保存排序
     * @author Faker Li
     * @created 2015年11月24日 下午3:40:35
     * @param itemIds
     */
    public void updateSn(String[] itemIds);
    /**
     * 
     * 描述 获取排序最大值
     * @author Faker Li
     * @created 2015年11月24日 下午3:47:21
     * @return
     */
    public int getMaxSn();
    /**
     * 
     * 描述 前台自动补全获取全部发布服务事项
     * @author Faker Li
     * @created 2015年11月26日 下午3:01:45
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findALLPublishItemsFront(String busType);
    /**
     * 
     * 描述 前台根据事项名称或者事项类型进行查找
     * @author Faker Li
     * @created 2015年11月26日 下午6:37:44
     * @param page
     * @param rows
     * @param busType
     * @param itemName
     * @param busTypeIds
     */
    public Map<String, Object> findfrontListByNameAndbusTypeIds(String page, String rows,
            String busType, String itemName, String busTypeIds,String sfzxbl);
    
    /**
     * 
     * 描述 前台根据事项名称或者事项类型进行查找
     * @author Sundy Sun
     * @param busType
     * @param itemName
     * @param busTypeIds
     */
    public List<Map<String, Object>> findWXListByName(String busType, String itemName, String busTypeIds,String sfzxbl);
    
    /**
     * 
     * 描述 根据事项名称进行查找
     * @author Sundy Sun
     * @param itemName
     */
    public List<Map<String, Object>> findZNListByName(String itemName,String sfzxbl);
    /**
     * 
     * 描述 根据类别ID获取办事项
     * @author Rider Chen
     * @created 2016-1-5 下午04:23:34
     * @param busTypeIds
     * @return
     */
    public List<Map<String, Object>>  findfrontList(String busTypeId);
    /**
     * 
     * 描述 根据栏目编码获取发布的事项
     * @author Faker Li
     * @created 2015年11月26日 下午3:01:45
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByCatalogCodeForWebSite(String catalogCode);
    
    /**
     * 
     * 描述 获取下一个流程对应的项目信息
     * @author Flex Hu
     * @created 2015年12月2日 下午4:17:35
     * @param currentItemCode:当前项目编码
     * @param tzlx :投资项目类型(1:社会投资 2:政府投资)
     * @return
     */
    public Map<String,Object> getNextTzItemCode(String currentItemCode,String tzlx);
    /**
     * 
     * 描述 根据catalogCode更新事项所属部门编码
     * @author Faker Li
     * @created 2015年12月16日 下午5:03:36
     * @param catalogCode
     * @param object
     */
    public void updateSSBMBM(String catalogCode, String departCode);
    /**
     * 
     * 描述 获取前台权利清单列表
     * @author Faker Li
     * @created 2015年12月28日 下午3:42:49
     * @param page
     * @param rows
     * @param sxxz
     * @param busTypeIds
     * @return
     */
    public Map<String, Object> findfrontQlqdList(String page, String rows, String sxxz, String busTypeIds);
    /**
     * 
     * 描述 根据部门编码获取该部门下的事项总数、行政许可事项数、公共服务事项数、事项服务星级、网上办事开通比例（%）     
     * @author Faker Li
     * @created 2016年1月26日 上午9:51:42
     * @param ssbmbm
     * @return
     */
    public Map<String, Object> getXnjcTaotal(String ssbmbm);
    /**
     * 
     * 描述 是否支持挂起
     * @author Flex Hu
     * @created 2016年3月19日 上午8:55:45
     * @param itemCode
     * @return
     */
    public boolean isForHandUp(String itemCode);
    
    /**
     * 描述 获取需要特殊处理的环节
     * @author Joseph Huang
     * @created 2016年5月5日 上午11:15:00
     * @param itemCode
     * @return
     */
    public Map<String,Object> getParticularDealNode(String itemCode);

    /**
     * 描述 获取省网办对应事项id
     * @author Water Guo
     * @created 2016年8月28日 上午11:15:00
     * @param itemCode
     * @return
     */
    public String getSwbItemCode(String itemCode);
    /**
     * 根据itemIds获取到数据列表
     * 
     * @param itemIds
     * @return
     */
    public List<Map<String, Object>> findByItemIds(String itemIds);
    
    /**
     * 描述 根据事项名称和部门编码，获取事项
     * @author Bryce Zhang
     * @created 2017-5-22 上午10:43:21
     * @param name
     * @param deptCode
     * @return
     */
    public Map<String, Object> getByNameAndDeptcode(String name, String deptCode);
    /**
     * 
     * 描述 获取事项的流程审核节点设置信息
     * @author Danto Huang
     * @created 2017年10月25日 上午9:47:56
     * @param itemCode
     * @param nodeName
     * @return
     */
    public boolean checkItemFlowNodes(String itemCode,String nodeName,String userAccount);
    /**
     * 
     * 描述  获取未同步事项
     * @author Kester Chen
     * @created 2018-4-20 上午11:42:36
     * @param filter
     * @return
     */
    public List<Map<String, Object>> swbRecDatas(SqlFilter filter);

    /**
     * 描述:根据申报号exeid获取事项
     *
     * @author Madison You
     * @created 2019/11/8 14:17:00
     * @param
     * @return
     */
    Map<String, Object> getItemInfoByExeId(String exeId);

    /**
     * 描述:是否存在此事项的目录
     *
     * @author Madison You
     * @created 2020/8/31 17:42:00
     * @param
     * @return
     */
    boolean findExitCatalog(String unid);

    
    /**
     * 描述:根据申报号exeid查询流程定义节点
     *
     * @author Madison You
     * @created 2020/8/31 17:42:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getFlowConfigInfo(String exeid);
    
    /**
     * 描述:根据申报号exeid查询流程定义
     *
     * @author Madison You
     * @created 2020/8/31 17:42:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getNodeConfigInfo(String exeid);


    /**
     * 初始化事项数据
     * @param type
     */
    void initServiceItem(String type);
}
