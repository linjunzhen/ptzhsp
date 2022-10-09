/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 食品生产操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FoodProductionService extends BaseService {
    /**
     * 
     * 描述 获取产品信息
     * 
     * @author Rider Chen
     * @created 2016年7月5日 上午11:17:12
     * @param jbxxId
     * @return
     */
    public List<Map<String, Object>> findCpxx(String jbxxId,String isQueryDetail,String EFLOW_ASSIGNER_TYPE);

    /**
     * 
     * 描述 获取技术人员信息
     * 
     * @author Rider Chen
     * @created 2016年7月5日 上午11:16:14
     * @param jbxxId
     * @return
     */
    public List<Map<String, Object>> findJsry(String jbxxId);

    /**
     * 描述 获取加工场所信息
     * @author John Zhang
     * @created 2016年8月16日 上午10:48:57
     * @param jbxxId
     * @return
     */
    public List<Map<String, Object>> findJgcs(String jbxxId);
    
    /**
     * 
     * 描述 获取委托代理人信息
     * 
     * @author Rider Chen
     * @created 2016年7月5日 上午11:16:44
     * @param jbxxId
     * @return
     */
    public Map<String, Object> getWtdlr(String jbxxId);

    /**
     * 
     * 描述 根据申请方式不同跳转不同的环节
     * 
     * @author Rider Chen
     * @created 2016年7月5日 下午2:49:18
     * @param flowVars
     * @return
     */
    public Set<String> getApplicationFormResult(Map<String, Object> flowVars);

    /**
     * 
     * 描述 流程保存数据
     * 
     * @author Rider Chen
     * @created 2016年7月5日 下午6:06:00
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas);
    
   /**
    * 
    * 描述 获取保存地址信息
    * @author Rider Chen
    * @created 2016年7月5日 下午8:04:42
    * @param jbxxId
    * @param dzlb
    * @return
    */
    public Map<String, Object> getAddressMap(String jbxxId, String dzlb);
    /**
     * 
     * 描述 根据许可证编号获取基本信息
     * @author Rider Chen
     * @created 2016年7月11日 下午5:08:41
     * @param xkzbh
     * @return
     */
     public Map<String, Object> getSpscxkxxMap(String xkzbh, String isProvince,String sqlb);
     /**
      * 
      * 描述 根据基本信息ID与单位ID获取证照信息
      * @author Rider Chen
      * @created 2016年7月12日 上午9:05:54
      * @param flowVars
      * @param deptId
      * @return
      */
     public Map<String, Object> getProductionInfo(Map<String,Object> flowVars,String deptId);
     
     /**
      * 
      * 描述 保存证照信息
      * @author Rider Chen
      * @created 2016年7月12日 下午2:52:53
      * @param flowDatas
      * @return
      */
     public Map<String, Object> saveZzxxData(Map<String, Object> flowDatas);
     
     
     /**
      * 获取生产地址列表
      * @author John Zhang
      * @created 2016年8月23日 上午9:50:07
      * @param jbxxId
      * @return
      */
     public List<Map<String, Object>> getDzList(String jbxxId, String dzlb);
     
     /**
      * 描述 根据社会信用代码 查询已通过审核的食品生产基本信息
      * @author John Zhang
      * @created 2016年8月23日 下午5:02:02
      * @return
      */
     public Map<String,Object> findByShxydm(String shxydm, String jbxxId);
     
     /**
      * 描述 注销流程 后置事件  审核通过后
      * @author John Zhang
      * @created 2016年8月23日 下午5:16:02
      */
     public Map<String, Object> updateZzxxState(Map<String, Object> flowvars);
     
     /**
      * 更新临时表许可证编号
      * @author John Zhang
      * @created 2016年8月23日 下午7:43:05
      * @param flowvars
      */
     public void updateXkzbh(Map<String, Object> flowvars);
     
     /**
      * 描述  办结审核通过后 生成临时表数据
      * @author John Zhang
      * @created 2016年8月23日 下午7:54:51
      * @param flowvars
      */
     public Map<String, Object> addTempXkxx(Map<String, Object> flowvars);
     
     /**
      * 描述 生成食品生产许可证号
      * @author Usher Song
      * @created 2016年7月13日 下午12:02:07
      * @param sqlx
      * @param typeCode
      * @param orderCode
      * @return
      */
     public String generateSCLicenseCode(String sqlx, String typeCode, String orderCode,String xzqhbm);
     

     /**
      * 
      * 描述 生成并保存食品生产许可证号
      * @author Usher Song
      * @created 2016年8月24日 上午9:43:38
      * @param jbxxId 业务表主键id
      * @param sqlx 申请类型
      * @param spbh 食品类别编码
      * @param xzqhbm 行政区划编码
      * @return
      */
       public String generateAndSaveLicenseCode(String jbxxId, String sqlx,String spbh,String xzqhbm);

      /**
       *  
       * 描述 
       * @author Usher Song
       * @created 2016年8月24日 上午11:28:45
       * @param liceseData
       * @param printConfigMap
       */
      public String saveOrUpdateToResult(Map<String, Object> liceseData, Map<String, Object> printConfigMap);

     /**
      * 描述  获取保健食品产品信息
      * @author John Zhang
      * @created 2016年8月24日 下午4:11:48
      * @param jbxxId
      * @return
      */
     public List<Map<String,Object>> getHealthCpxx(String jbxxId);
     
     /**
      * 描述 根据社会信用代码统计许可证条数
      * @author John Zhang
      * @created 2016年8月30日 下午3:22:36
      * @param xhxydm
      * @param itemCode
      * @param exeId
      * @return
      */
     public Map<String, Object> getCountByShxydm(String shxydm, String itemCode, String exeId, String hasHealth);
     
     /**
      * 描述 根据社会信用代码查询历史证照数据
      * @author John Zhang
      * @created 2016年9月1日 上午10:27:03
      * @param shxydm
      * @return
      */
     public Map<String, Object> getByShxydm(String shxydm,String isHealth);
     
     /**
      * 描述 根据基本信息id查询 设备设施
      * @author John Zhang
      * @created 2016年9月1日 上午11:47:10
      * @param jbxxId
      * @return
      */
     public List<Map<String, Object>> findSbss(String jbxxId);

     /**
      * 描述  获取检验仪器
      * @author John Zhang
      * @created 2016年10月9日 下午3:58:50
      * @param jbxxId
      * @return
      */
     public List<Map<String, Object>> findJyyq(String jbxxId);
     
     /**
      * 描述 获取安全制度清单
      * @author John Zhang
      * @created 2016年10月9日 下午3:58:53
      * @param jbxxId
      * @return
      */
     public List<Map<String, Object>> findAqzd(String jbxxId);
     /**
      * 
      * 描述 获取证照信息确认页面数据
      * @author Usher Song
      * @created 2016年9月6日 下午4:52:30
      * @param request
      * @return
      */
     public Map<String, Object> getPrintJumpPageData(HttpServletRequest request);
     
     /**
      * 描述 打印证照后合并数据
      * @author John Zhang
      * @created 2016年9月6日 上午9:15:27
      * @param map
      * @return
      */
     public String afterPrintZz(Map<String, Object> map);
     
     /**
      * 
      * 描述 根据基本信息ID与部门ID获取产品信息
      * @author Rider Chen
      * @created 2016年7月12日 上午9:31:01
      * @param jbxxId
      * @param deptId
      * @return
      */
     public List<Map<String, Object>> getCpxxInfo(String jbxxId,String deptId);
     
     /**
      * 描述 获取保健食品 产品信息
      * @author John Zhang
      * @created 2016年9月6日 下午5:22:48
      */
     public List<Map<String, Object>> getBjCpxxInfo(String jbxxId);
     
     /**
      * 描述 获得合并后的数据
      * @author John Zhang
      * @created 2016年9月13日 下午3:58:16
      * @param curJbxxId
      * @param oldJbxxId
      * @return
      */
     public List<Map<String, Object>> getMergeCpxx(String curJbxxId, String zzxxId);

     /**
      * 描述 保存合并数据
      * @author John Zhang
      * @created 2016年10月9日 下午3:57:59
      * @param licenseData
      */
     public void saveMergeInfo(Map<String, Object> licenseData);
     
     /**
      * 获取办件数据
      * @param sqlFilter
      * @return
      */
     public List<Map<String,Object>> findHandleDatas(SqlFilter sqlFilter);
     
     /**
      * 回填申请书数据
      * @param vars
      * @return
      */
     @SuppressWarnings("unchecked")
     public Map<String,Object> setWordValuesForSqs(Map<String,Object> vars);
     
     /**
      * 回填申请书数据
      * @param vars
      * @return
      */
     @SuppressWarnings("unchecked")
     public Map<String,Object> setWordValuesForSqsBZ(Map<String,Object> vars);
     
     /**
      * 回填申请书数据
      * @param vars
      * @return
      */
     @SuppressWarnings("unchecked")
     public Map<String,Object> setWordValuesForSqsBJ(Map<String,Object> vars);
     /**
      * 描述 回填许可文书数据
      * @author John Zhang
      * @created 2016年11月9日 下午3:50:13
      * @param docDatas
      * @param flowVars
      */
     public void setWordTemplateData(Map<String, Object> docDatas,Map<String,Object> flowVars);
     /**
      */
     public Map<String, Object> zxHandle(Map<String, Object> flowVars);
     
     /**
      * 描述  获取证照记录的
      * @author John Zhang
      * @created 2017年2月26日 下午2:17:12
      * @param zzxxId
      * @param isHealth
      * @return
      */
     public List<Map<String,Object>> getZzCp(String zzxxId, String isHealth);

     /**
      * 
      * 描述 地址信息处理
      * @author Usher Song
      * @created 2017年2月28日 下午6:19:22
      * @param productionInfo
      */
     public void handleAdress(Map<String, Object> productionInfo);

     /**
      * 
      * 描述 许可证编号处理
      * @author Usher Song
      * @created 2017年2月28日 下午6:29:25
      * @param productionInfo
      */
     public void handleXkzbh(Map<String, Object> productionInfo);

     /**
      * 描述 审批办结处理  准予许可 不予许可
      * @author John Zhang
      * @created 2017年4月6日 上午10:49:01
      * @param flowVars
      * @return
      */
     public Map<String, Object> afterSpbj(Map<String, Object> flowVars);
     
     /**
      * 描述 后置事件构造传输报文
      * @author Keravon Feng
      * @created 2019年2月21日 上午11:12:04
      * @param flowDatas
      * @return
      */
     public Map<String,Object> afterCreateTransData(Map<String,Object> flowDatas);
     
}