/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 申请材料操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ApplyMaterService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据sqlfilter获取数据列表
     * @author Flex Hu
     * @created 2015年9月22日 下午5:26:25
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findForItem(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存或者更新,级联更新中间表
     * @author Flex Hu
     * @created 2015年9月17日 下午4:45:06
     * @param applyMater
     * @return
     */
    public String saveOrUpdateCascadeMiddle(Map<String, Object> applyMater);
    /**
     * 
     * 描述 级联删除掉数据
     * @author Flex Hu
     * @created 2015年9月17日 下午5:26:59
     * @param materIds
     */
    public void removeCascadeMiddle(String[] materIds);
    /**
     * 
     * 描述 获取列表数据
     * @author Flex Hu
     * @created 2015年9月22日 下午5:41:10
     * @param materIds
     * @return
     */
    public List<Map<String,Object>> findByMaterIds(String materIds);
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterItem(String materIds,String itemId);
    /**
     * 
     * 描述 保存材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:52:05
     * @param materIds
     * @param itemId
     */
    public void deleteMaterByItem(String itemId);
    /**
     * 
     * 描述 保存材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:52:05
     * @param materIds
     * @param itemId
     */
    public void saveMaterItem(String materIds,String itemId);
    /**
     * 
     * 描述 更新排序值
     * @author Flex Hu
     * @created 2015年9月23日 上午11:27:27
     * @param itemId
     * @param materIds
     */
    public void updateSn(String itemId,String[] materIds);
    /**
     * 
     * 描述 根据项目ID获取材料信息列表
     * @author Flex Hu
     * @created 2015年10月27日 下午5:03:23
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> findByItemId(String itemId,String exeId);
    /**
     * 
     * 描述 根据项目Codes获取材料信息列表
     * @author Rider Chen
     * @created 2017年7月11日 下午4:12:38
     * @param itemCodes
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findByItemCodes(String itemCodes,String exeId);
    /**
     * 
     * 描述 更新绑定材料是否为必须提供
     * @author Faker Li
     * @created 2015年11月4日 下午3:24:03
     * @param isneed
     * @param itemId
     * @param materIds
     */
    public void updateIsneed(String isneed, String itemId, String materIds);
    /**
     * 
     * 描述 根ITEMCODE获取绑定材料列表
     * @author Faker Li
     * @created 2015年11月6日 上午9:22:11
     * @param itemCode
     * @return
     */
    public List<Map<String,Object>> findMaterByItemCode(String itemCode);
    /**
     * 
     * 描述 设置已经上传的材料列表
     * @author Flex Hu
     * @created 2015年12月1日 上午11:31:18
     * @param busRecordId
     * @param busTableName
     * @param applyMaters
     * @return
     */
    public List<Map<String,Object>> setUploadFiles(String busRecordId,String busTableName,
            List<Map<String,Object>> applyMaters);
    /**
     * 
     * 根据标签设置自动代填上传材料 
     * @author Danto Huang
     * @created 2017-5-4 下午2:01:36
     * @param applyMaters
     * @param busTableName
     * @param resultMap
     * @return
     */
    public List<Map<String, Object>> setHisUploadFiles(
            List<Map<String, Object>> applyMaters, String busTableName,
            Map<String, Object> resultMap);
   /**
    * 
    * 描述：根据附件名称，项目代码自代填上传材料
    * @author Rider Chen
    * @created 2019年6月14日 下午3:08:11
    * @param applyMaters
    * @param busTableName
    * @param resultMap
    * @return
    */
    public List<Map<String, Object>> setSameUploadFiles(
            List<Map<String, Object>> applyMaters, String busTableName,
            String projectCode);
    /**
     * 
     * 描述：根据附件编码，项目代码自代填上传材料(多规合一)
     * @author Rider Chen
     * @created 2019年8月21日 下午2:50:25
     * @param applyMaters
     * @param projectCode
     * @return
     */
     public List<Map<String, Object>> setSameKeyUploadFiles(
             List<Map<String, Object>> applyMaters,String busTableName,
             String projectCode);
    /**
     * 
     * 描述 获取前台表格下载分页
     * @author Faker Li
     * @created 2015年12月10日 下午3:38:20
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> findbgxzList(String page, String rows,String materName,String busTypeIds);
    /**
     * 
     * 描述 获取所有表格下载列表
     * @author Faker Li
     * @created 2015年12月17日 上午9:25:53
     * @return
     */
    public List<Map<String, Object>> findAllBgxzList();
    /**
     * 
     * 描述 更新过滤参数
     * @author Faker Li
     * @created 2016年3月7日 下午1:13:07
     * @param fpara
     * @param materIds
     * @param itemId
     */
    public void updateFilterPara(String fpara, String materIds,String itemId);
    

   /**
    * 
    * 描述 根据任务ID获取材料信息列表
    * @author Rider Chen
    * @created 2016-4-19 上午11:24:55
    * @param taskId
    * @param exeId
    * @return
    */
    public List<Map<String,Object>> findByTaskId(String taskId,String exeId,HttpServletRequest request);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public String findByUserMater(String userName);
    
    /**
     * 
     * 描述 更新状态
     * @author Rider Chen
     * @created 2016-4-19 下午05:00:38
     * @param ids
     */
    public void updateFlowUserMaterStatus(String ids,String nextNodeName);
    /**
     * 
     * 描述  获取列表数据
     * @author Rider Chen
     * @created 2016-4-20 下午12:35:58
     * @param userName
     * @param exeId
     * @param taskId
     * @return
     */
    public List<Map<String,Object>> findByMaterIds(String userName,String exeId,String taskId);
    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2016年4月21日 下午5:51:32
     * @param sysUserName
     * @param currentNodeName
     * @param exeId
     * @return
     */
    public List<Map<String, Object>> findFilterMater(String sysUserName,
            String currentNodeName, String exeId);
    /**
     * 
     * 描述 获取再次审核材料信息
     * 
     * @author Sundy Sun
     * @param sysUserName
     * @param currentNodeName
     * @param exeId
     * @return
     */
    public List<Map<String, Object>> findAgainMater(String sysUserName,
            String currentNodeName, String exeId);
    
    /**
     * 
     * 描述   获取服务事项材料编码
     * @author Danto Huang
     * @created 2016-9-23 上午8:39:12
     * @param itemId
     * @return
     */
    public String getMaxNumCode(String itemId);
    /**
     * 
     * 描述   根据事项编码获取最大材料编码
     * @author Danto Huang
     * @created 2016-10-20 上午9:47:54
     * @param itemCode
     * @param maxCode
     * @return
     */
    public String getMaxMaterCodeByItemCode(String itemCode,String maxCode);

    
    /**
     * 
     * 描述    业务办理分类列表
     * @author Danto Huang
     * @created 2018年9月3日 下午3:22:16
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> findHandleTypeList(String itemId);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/31 15:06:00
     * @param 
     * @return 
     */
    public List<Map<String, Object>> findHandleTypeListE(String itemId);
    /**
     * 
     * 描述    获取材料信息列表
     * @author Danto Huang
     * @created 2019年3月7日 下午4:41:50
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> findMatersByItemId(String itemId);
    /**
     * 描述:根据事项ID获取材料列表
     *
     * @author Madison You
     * @created 2020/11/3 17:36:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findMaterListByItemId(String itemId);
    public List<Map<String, Object>> findForItem2(SqlFilter filter);
}
