/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 食品经营操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FoodManagementService extends BaseService {
    /**
     * 
     * 描述 流程保存数据
     * @author Faker Li
     * @created 2016年5月31日 下午4:17:58
     * @param flowDatas
     * @return
     */
    public Map<String,Object> saveBusData(Map<String,Object> flowDatas);

    /**
     * 描述 获取人员信息数据
     * @author Faker Li
     * @created 2016年5月31日 下午4:37:58
     * @param string
     * @param string2
     * @return
     */
    public List<Map<String, Object>> findPersonel(String jbxxId, String rysx);

    /**
     * 描述 获取法人代表
     * @author Faker Li
     * @created 2016年5月31日 下午4:48:24
     * @param string
     * @return
     */
    public Map<String, Object> getLegalRepresentative(String jbxxId);

    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月31日 下午4:53:57
     * @param string
     * @return
     */
    public List<Map<String, Object>> findFacilityEquipment(String jbxxId);

    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月31日 下午6:31:32
     * @param string
     * @return
     */
    public Map<String, Object> getYxm(String jbxxId,String key);

    /**
     * 描述
     * @author Faker Li
     * @created 2016年6月1日 上午9:10:36
     * @param bus_recordid
     * @param string
     * @return
     */
    public Map<String, Object> getAddressMap(String jbxxId, String dzlb);

    /**
     * 描述 获取代理人信息
     * @author Faker Li
     * @created 2016年6月1日 下午4:48:07
     * @param bus_recordid
     * @return
     */
    public Map<String, Object> getClinetMap(String jbxxId);
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年6月2日 上午9:59:20
     * @param flowVars
     * @return
     */
    public Set<String> getApplicationFormResult(Map<String, Object> flowVars);
    /**
     * 
     * 描述 流程保存数据
     * @author Faker Li
     * @created 2016年5月31日 下午4:17:58
     * @param flowDatas
     * @return
     */
    public Map<String,Object> saveChangeBusData(Map<String,Object> flowDatas);
    /**
     * 
     * 描述 许可延续草稿复制接口
     * @author Flex Hu
     * @created 2016年6月21日 下午4:30:58
     * @param flowVars
     * @return
     */
    public Map<String,Object> saveDraftForContinue(Map<String,Object> flowVars);

    /**
     * 描述 根据经营场所获取条数
     * @author Faker Li
     * @created 2016年7月15日 下午1:17:09
     * @param jYCS
     * @return
     */
    public Map<String,Object>  getCountNumByJycs(String itemCode,String jycsqs,
            String jycsxs,String jycsxz,String jyxxdz,String exe_id);
    
    
    /**
     * 
     * 描述 获取经营许可信息
     * @author Usher Song
     * @created 2016年7月15日 上午10:58:15
     * @param  jbxxId
     * @return
     */
    public Map<String, Object> getFoodManagementInfo(String jbxxId);
    
    /**
     * 
     * 描述 获取经营许可信息
     * @author Usher Song
     * @created 2016年7月15日 上午10:58:15
     * @param  flowVars
     * @return
     */
    public Map<String, Object> getFoodManagementInfo(Map<String, Object> flowVars);

    /**
     * 描述 根据原许可编码获取Map
     * @author Faker Li
     * @created 2016年7月16日 上午9:55:53
     * @param xKZBH_OLD
     * @return
     */
    public Map<String, Object> getSpjyxkxxMap(String xKZBH_OLD,String xkzzt);
    /**
     * 
     * 描述 预审后置事件
     * @author Faker Li
     * @created 2016年7月18日 下午8:44:54
     * @param flowVars
     * @return
     */
    public Map<String, Object> afterYs(Map<String, Object> flowVars);
    /**
     * 
     * 描述 获取地址信息
     * @author Usher Song
     * @created 2016年6月24日 上午11:56:49
     * @param jbxxId 基本信息ID
     * @param 地址类型（1:住所;2:经营场所;3:仓库地址;)
     * @return
     */
    public String getAddressStr(String jbxxId, int dzlb);
    
    
    /**
     * 
     * 描述 获取主体业态
     * @author Usher Song
     * @created 2016年6月29日 上午9:13:35
     * @param licenseData
     * @return
     */
    public String getZtytStr(Map<String, Object> licenseData);
    /**
     *  描述 根据退回业务受理号获取Map
     * @author Lina Lin
     * @created 2016年7月23日 上午10:45:44
     * @param oldexe_id
     * @return
     */
    public Map<String, Object> getExecutionMap(String oldexe_id);
    /**
     * 
     * 描述 业务退回后置事件
     * @author Faker Li
     * @created 2016年7月23日 上午11:24:05
     * @param flowVars
     * @return
     */
    public Map<String, Object> afterYwthHandle(Map<String, Object> flowVars);
    
    
    /**
     * 
     * 描述 受理后置事件
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterZXHandle(Map<String, Object> flowDatas);
    
    /**
     * 
     * 描述 撤销审核通过后置事件
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterCXHandle(Map<String, Object> flowVars);
    
    /**
     * 
     * 描述 换证决定后置事件
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterHZHandle(Map<String, Object> flowVars);
    
    
    /**
     * 
     * 描述 产生经营许可证号
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    public Map<String, Object> generateJYLicenseCode(Map<String, Object> flowDatas);
    
    
    /**
     * 
     * 描述 更新许可证状态（10有效    20过期    30被撤销    40被吊销    50被撤回   60注销）
     * @author Usher Song
     * @created 2016年7月5日 下午7:27:49
     * @param punishId
     * @param punishStatue
     */
    public void updateLicenseState(String xkzbh,String state);

    
    

    /**
     * 描述 获取社会信用代码回填数据
     * @author Faker Li
     * @created 2016年7月27日 上午9:35:13
     * @param sHXYDMSFZHM
     * @return
     */
    public Map<String, Object> getCreditCodeData(String shxydmsfzhm);

    /**
     * 描述  根据社会信用代码和事项编号获取流水号
     * @author Faker Li
     * @created 2016年7月28日 上午10:21:14
     * @param sHXYDMSFZHM
     * @param itemCode
     * @return
     */
    public List<Map<String, Object>> isExistShxydm(String shxydmsfzhm,
            String itemCode);

    /**
     * 描述 获取旧换证补数据
     * @author Faker Li
     * @created 2016年8月12日 下午4:57:25
     * @param string
     * @param string2
     * @return
     */
    public Map<String, Object> getPatchSpjyxx(String xkzbm);

    /**
     * 描述 验证许可信息
     * @author Faker Li
     * @created 2016年8月12日 下午5:35:00
     * @param xKZBH_OLD
     * @param string
     * @return
     */
    public Map<String, Object> getSpjyxkxxMapForValidateXkzbh(String xKZBH_OLD,
            String string);
    
    /**
     * 
     * 描述 保存证照图片路径
     * @author Usher Song
     * @created 2016年7月11日 上午9:16:53
     * @param jbxxId
     * @param filePath
     */
    public void saveImagePath(String jbxxId, String filePath);
    
    
    /**
     * 
     * 描述 获取经营项目
     * @author Usher Song
     * @created 2016年8月23日 上午9:34:56
     * @param jbxxId
     * @param jbxxMap
     */
    public String getJyxmValue(String jbxxId, Map<String, Object> jbxxMap);
    
    /**
     * 
     * 描述 生产和保存许可证编号
     * @author Usher Song
     * @created 2016年8月1日 上午10:27:45
     * @param jbxxId
     * @param ztytdm
     * @return 
     */
    public String generateAndSaveLicenseCode(String jbxxId, String ztytdm);
    

    /**
     * 
     * 描述 生成许可证编号
     * @author Usher Song
     * @created 2016年6月21日 下午3:10:01
     * @param ztyt 主体业态
     * @param xzqhbm 行政区划编码
     * @param orderCode  顺序码(6位)
     * @return
     */
    public String generateLicenseCode(String ztyt, String xzqhbm,String orderCode);

    /**
     * 
     * 描述
     * @author Usher Song
     * @created 2016年9月13日 上午9:05:36
     * @param string
     * @param string2
     * @return
     */
    public Map<String, Object> getSpjyOldMap(String string, String string2);

    
    /**
     * 
     * 描述
     * @author Usher Song
     * @created 2016年10月25日 下午5:56:26
     * @param licenseData
     * @param printConfigMap
     */
    public String saveOrUpdateToResult(Map<String, Object> licenseData, Map<String, Object> printConfigMap);

    
    /**
     * 
     * 描述 修改许可证编号
     * @author Usher Song
     * @created 2016年10月26日 上午12:10:02
     * @param licenseData
     */
    public String generateNewXkzbh(Map<String, Object> licenseData);

    /**
     * 
     * 描述 回填食品经营新开办数据
     * @author Faker Li
     * @created 2016年11月14日 上午11:30:04
     * @param vars
     * @return
     */
    public Map<String,Object> setWordValuesForXKB(Map<String,Object> vars);
    /**
     * 
     * 描述 回填业务退回申请表数据
     * @author Faker Li
     * @created 2016年11月16日 下午5:06:02
     * @param vars
     * @return
     */
    public Map<String, Object> setWordValuesForYWTH(Map<String, Object> vars);
    /**
     * 
     * 描述 回填注销申请表数据
     * @author Faker Li
     * @created 2016年11月16日 下午5:06:02
     * @param vars
     * @return
     */
    public Map<String, Object> setWordValuesForZX(Map<String, Object> vars);
    /**
     * 
     * 描述 回填撤销申请表数据
     * @author Faker Li
     * @created 2016年11月16日 下午5:06:02
     * @param vars
     * @return
     */
    public Map<String, Object> setWordValuesForCX(Map<String, Object> vars);
    /**
     * 
     * 描述 回填补正申请表数据
     * @author Faker Li
     * @created 2016年11月16日 下午5:06:02
     * @param vars
     * @return
     */
    public Map<String, Object> setWordValuesForBZ(Map<String, Object> vars);

    
    /**
     * 
     * 描述
     * @author Usher Song
     * @created 2016年12月9日 上午10:35:11
     * @param xkzbh
     * @param jycs
     * @param jyzmc
     */
    public List<Map<String, Object>> getLicenseByJycsAndXkzbh(String jycs,String xkzbh,String jyzmc);
    /**
     * 
     * 描述 预审通过后置事件
     * @author Faker Li
     * @created 2016年12月14日 下午3:20:05
     * @param flowVars
     * @return
     */
    public Map<String, Object> afterYSTGHandle(Map<String, Object> flowVars);

    /**
     * 描述
     * @author Faker Li
     * @created 2017年11月17日 上午11:12:28
     */
    public void updateExeAddr();

    /**
     * 描述 发送预计短信
     * @author Faker Li
     * @created 2018年3月27日 下午2:24:57
     */
    public void sendFoodWarnMessage();
    
    /**
     * 描述 后置事件构造传输报文
     * @author Keravon Feng
     * @created 2019年2月21日 上午11:12:04
     * @param flowDatas
     * @return
     */
    public Map<String,Object> afterCreateTransData(Map<String,Object> flowDatas);

    /**
     * 描述 获取待上传的材料附件列表数据
     * @author Keravon Feng
     * @created 2019年2月22日 下午4:49:01
     * @return
     */
    public List<Map<String, Object>> listTransMaters(int pageSize);

    /**
     * 描述 获取待上传的业务数据
     * @author Keravon Feng
     * @created 2019年2月25日 下午2:20:10
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> listTrans(int pageSize);

}

