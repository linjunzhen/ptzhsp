/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-14 上午9:29:13
 */
public interface DepartServiceItemService extends BaseService {
    /**
     * 工程建设项目定义名称
     */
    public static String GCJS_DEF_TYPENAME="工程建设项目";
    /**
     * 
     * 描述   获取事项列表
     * @author Danto Huang
     * @created 2016-9-18 下午3:27:36
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    更新或者保存服务项目
     * @author Danto Huang
     * @created 2016-9-20 下午2:01:40
     * @param serviceItem
     * @return
     */
    public String saveOrUpdateCascade(Map<String, Object> serviceItem);
    /**
     * 
     * 描述   删除服务事项
     * @author Danto Huang
     * @created 2016-9-20 下午3:40:43
     * @param selectColNames
     */
    public void removeCascade(String selectColNames);
    /**
     * 
     * 描述   特殊环节列表
     * @author Danto Huang
     * @created 2016-9-21 下午4:40:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSpecialLink(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述 根据事项ID或者特殊环节列表
     * @author Rider Chen
     * @created 2017年5月2日 下午2:17:35
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> getSpecialLink(String itemId);
    /**
     * 
     * 描述   流程环节审核人设置数据
     * @author Danto Huang
     * @created 2016-9-28 下午3:02:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getDefNode(SqlFilter sqlFilter);
    /**
     * 
     * 描述   保存流程审核节点
     * @author Danto Huang
     * @created 2016-9-28 下午4:03:05
     * @param itemId
     * @param defId
     */
    public void saveItemDefNode(String itemId,String defId);

    /**
     * 
     * 描述    获取审核列表页
     * @author Danto Huang
     * @created 2016-10-10 下午1:31:01
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByAuditingSqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：获取拟发布库列表页
     * @author Water Guo
     * @created 2017-3-22 上午9:55:36
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPrePublicSqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    获取发布库列表
     * @author Danto Huang
     * @created 2016-10-10 下午2:14:24
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：事项汇总
     * @author Water Guo
     * @created 2017-3-5 下午7:37:13
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findAllBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：获得流程点
     * @author Water Guo
     * @created 2017-4-9 下午8:53:31
     * @param defId
     * @return
     */
    public List<Map<String, Object>> getNodeNamesByDefid(String defId);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月8日 下午4:21:56
     * @param itemCodes
     * @return
     */
    public List<Map<String,Object>> findByItemCodes(String itemCodes);
    /**
     * 
     * 描述    收费明细
     * @author Danto Huang
     * @created 2018年8月27日 下午5:46:18
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getChargeData(SqlFilter sqlFilter);
    /**
     * 
     * 描述    自查条件
     * @author Danto Huang
     * @created 2018年9月3日 下午5:41:50
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSelfExamData(SqlFilter sqlFilter);
    /**
     * 
     * 描述    获取自查条件最大排序值
     * @author Danto Huang
     * @created 2018年9月4日 上午10:45:38
     * @return
     */
    public int getMaxExamSn();/**
     * 
     * 描述    商户管理数据
     * @author Danto Huang
     * @created 2018年9月20日 上午10:59:40
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getMerchantData(SqlFilter filter);
    /**
     * 
     * 描述    判断事项当前绑定流程审核人员已设置
     * @author Danto Huang
     * @created 2018年9月25日 上午10:09:22
     * @param itemId
     * @param defId
     * @return
     */
    public boolean defNodeSetDone(String itemId,String defId);
    /**
     * 
     * 描述    保存到历史版本库
     * @author Danto Huang
     * @created 2018年10月9日 上午10:44:16
     * @param entityId
     * @param version
     */
    public void copyToHis(String entityId,String version);
    /**
     * 
     * 描述    历史版本记录生效状态更新
     * @author Danto Huang
     * @created 2018年10月17日 上午10:58:58
     * @param itemId
     */
    public void updateHisStatus(String itemId);
    /**
     * 
     * 描述    更新关联表版本号
     * @author Danto Huang
     * @created 2018年10月30日 上午8:52:49
     * @param itemId
     * @param version
     */
    public void updateRelateVersion(String itemId,String version);
    /**
     * 
     * 描述    历史版本查询
     * @author Danto Huang
     * @created 2018年11月9日 下午3:59:53
     * @param fitler
     * @return
     */
    public List<Map<String,Object>> findItemHisBySqlfilter(SqlFilter fitler);

    /**
     * 判断是否是工程建设项目节点
     */
    public boolean isGcjsDefTypeNode(String defId);
    /**
     * 
     * 描述    根据项目ID和版本号获取绑定的用户IDSNAMES
     * @author Danto Huang
     * @created 2018年11月12日 上午10:23:55
     * @param itemId
     * @param version
     * @return
     */
    public  Map<String, Object> getHisBindUserIdANdNames(String itemId,String version) ;
    /**
     * 
     * 描述    特殊环节列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月12日 下午4:41:09
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSpecialLinkHis(SqlFilter sqlFilter);
    /**
     * 
     * 描述    材料列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月12日 下午4:41:09
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getMatterHis(SqlFilter sqlFilter);
    /**
     * 
     * 描述    收费明细列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月13日 上午10:01:11
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getChargeDataHis(SqlFilter sqlFilter);
    /**
     * 
     * 描述     自查条件列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月13日 上午10:01:23
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSelfExamDataHis(SqlFilter sqlFilter);
    /**
     * 
     * 描述   流程环节审核人设置数据(历史版本)
     * @author Danto Huang
     * @created 2016-9-28 下午3:02:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getDefNodeHis(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述： 根据业务编码获取事项
     * @author Rider Chen
     * @created 2019年3月12日 上午11:10:15
     * @param itemCodes
     * @return
     */
    public List<Map<String,Object>> findByBusinessCode(String businessCode);
    /**
     * 
     * 描述：修改事项业务编码
     * @author Rider Chen
     * @created 2019年3月12日 上午11:41:03
     * @param itemCodes
     * @param businessCode
     */
    public void updateItemCodes(String itemCodes , String businessCode);

    /**
     * 描述:判断材料是否完整
     *
     * @author Madison You
     * @created 2019/11/12 17:39:00
     * @param
     * @return
     */
    Map<String,Object> checkServiceItemApplyMater(Map<String, Object> item);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/6 10:36:00
     * @param
     * @return
     */
    List<Map<String, Object>> findByStampId(String stampId);

    /**
     * 描述:判断部门是否正确
     *
     * @author Madison You
     * @created 2021/2/24 9:19:00
     * @param
     * @return
     */
    Map<String, Object> checkServiceItemDepart(Map<String, Object> item);

    /**
     * 描述:获取本地新建事项数量
     *
     * @author Madison You
     * @created 2021/3/4 14:51:00
     * @param
     * @return
     */
    int getLocalItemCount();
    
    /**
     * 
     * 描述    待划转数据
     * @author Danto Huang
     * @created 2021年5月18日 下午4:02:20
     * @param itemIds
     * @return
     */
    public List<Map<String,Object>> selectedForTransferData(String itemIds);
    
    /**
     * 
     * 描述    事项划转
     * @author Danto Huang
     * @created 2021年5月19日 上午10:53:58
     * @param request
     * @return
     */
    public void doTransfer(HttpServletRequest request);
}
