/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年7月11日 上午9:11:18
 */
public interface CommercialSetService extends BaseService {

    /**
     * 
     * 获取1+N关联事项
     * @author Danto Huang
     * @created 2017年7月11日 下午2:53:40
     * @param filter
     * @return
     */
    public List<Map<String ,Object>> getRelatedItems(SqlFilter filter);
    /**
     * 
     * 关联事项材料
     * @author Danto Huang
     * @created 2017年7月12日 下午5:40:04
     * @param itemCodes
     * @return
     */
    public List<Map<String ,Object>> getRelatedItemMater(String itemCodes);
    /**
     * 
     * 获取关联事项材料列表
     * @author Danto Huang
     * @created 2017年8月23日 上午9:32:32
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findRelatedItemMater(SqlFilter filter);
    /**
     * 
     *  根据事项Codes获取材料信息列表
     * @author Danto Huang
     * @created 2017年8月24日 上午8:40:26
     * @param itemCodes
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findMaterByItemCodes(String itemCodes,String exeId);
    /**
     * 
     * 描述 验证1+N关联事项是否归属当前登录审核人
     * @author Danto Huang
     * @created 2017年9月28日 下午5:16:14
     * @param username
     * @param itemCode
     * @param currNodeName
     * @return
     */
    public Map<String,Object> checkAuditerRelatedItem(String username,String itemCode,String currNodeName);
    /**
     * 
     * 描述 将环节审核转换成多人审核(基于关联事项)
     * @author Danto Huang
     * @created 2017年9月29日 下午2:41:32
     * @param nodeName
     * @param handlers
     * @param flowVars
     * @return
     */
    public List<FlowNextStep> conventTaskToMulitItem(String nodeName, List<FlowNextHandler> handlers,
            Map<String, Object> flowVars);
    /**
     *     
     * 描述  商事银行预审审核人获取
     * @author Danto Huang
     * @created 2017年11月21日 上午9:47:22
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getBankUsers(Map<String,Object> flowVars,
            String varName,String handlerRule);
    
    
    /**
     * 
     * 描述  获取支撑平台字典数据
     * @author Danto Huang
     * @created 2018年6月7日 上午11:26:36
     * @param param
     * @return
     */
    public List<Map<String,Object>> findDatasForSelectFromDevbase(String param);

    /**
     * 描述:公章刻制材料下载数据
     *
     * @author Madison You
     * @created 2019/9/4 10:59:00
     * @param
     * @return
     */
    List<Map<String, Object>> findSealMaterData(SqlFilter filter);

    /**
     * 描述:公章刻制材料详细信息
     *
     * @author Madison You
     * @created 2019/9/4 17:26:00
     * @param
     * @return
     */
    Map<String, Object> findSealMaterDetailData(SqlFilter filter);
    
    
    /**
     * 
     * 描述    商事登记-企业医保开户
     * @author Allin Lin
     * @created 2021年4月21日 上午9:31:31
     * @param flowDatas
     * @return
     */
    public Map<String, Object> openYbAccount(Map<String, Object> flowDatas);
    
    /**
     * 
     * 描述    商事登记-推送企业信息开办医保账户（推送失败重复推送）
     * @author Allin Lin
     * @created 2021年4月21日 下午4:43:08
     */
    public void pushYbAccount();
    
    /**
     * 
     * 描述    内资变更登记业务数据回填
     * @author Allin Lin
     * @created 2021年4月25日 上午11:57:07
     * @param busRecord
     */
    public void setCommercialChangeDomestic(Map<String, Object> busRecord,String fileName,String stockFrom);
    
    /**
     * 
     * 描述    内资变更股权转让协议集合
     * @author Allin Lin
     * @created 2021年6月20日 下午8:43:42
     * @param exeId
     * @param fileId
     * @return
     */
    public List<Map<String, Object>> getStockFromList(String exeId,String fileId);
    
    /**
     * 
     * 描述  判断是否生成新股东决定/会议  (依据材料列表生成规则)--和前台保持一致
     * @author Allin Lin
     * @created 2021年7月6日 上午9:22:12
     * @param busRecord
     * @return
     */
    public boolean isNewGdMater(Map<String, Object> busRecord);
    
    /**
     * 
     * 描述  判断是否生成原股东决定/决议  (依据材料列表生成规则)--和前台保持一致
     * @author Allin Lin
     * @created 2021年7月6日 上午9:22:12
     * @param busRecord
     * @return
     */
    public boolean isOldGdMater(Map<String, Object> busRecord);
    
    /**
     * 
     * 描述  判断是否生成董事会决议  (依据材料列表生成规则)--和前台保持一致
     * @author Allin Lin
     * @created 2021年7月6日 上午9:22:12
     * @param busRecord
     * @return
     */
    public boolean isDshMater(Map<String, Object> busRecord);
    
    /**
     * 
     * 描述    设置股东（发起人）、外国投资者出资情况
     * @author Allin Lin
     * @created 2021年4月27日 上午10:59:02
     * @param busRecord
     */
    public void setHolderCzList(Map<String, Object> busRecord);
}
