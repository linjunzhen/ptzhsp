/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述  国有建设用地使用权首次登记service
 * @author Allin Lin
 * @created 2020年11月11日 上午11:20:25
 */
public interface BdcGyjsydsyqscdjService extends BaseService{
    
    /**
     * 国有建设用地使用权首次登记-不动产登记申请书模板key
     */
    public static String DJSQS_KEY="SQS.docx";
    
    
    /**
     * 国有建设用地使用权首次登记-授权委托书模板key
     */
    public static String WTS_KEY="WTS.docx";
    
    /**
     *国有建设用地使用权首次登记-不动产登记申请书材料名称
     */
    public static String DJSQS_MATERNAME="不动产登记申请书";
    
    /**
     *国有建设用地使用权首次登记-授权委托书材料名称
     */
    public static String WTS_MATERNAME="授权委托书";
    
    /**
     * 描述     国有建设用地使用权首次登记前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年10月14日 下午2:44:26
     * @param flowVars
     * @return
     * @throws Exception
     */
    public Map<String, Object> gyjsscdjGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    /**
     * 描述    初始化签章材料业务数据
     * @author Allin Lin
     * @created 2020年10月14日 下午2:52:34
     * @param flowVars
     * @return
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars);
    
    /**
     * 描述    询问记录是否选择框
     * @author Allin Lin
     * @created 2020年11月11日 下午2:51:07
     * @param column
     * @param columnValue
     * @param busInfo
     * @param returnMap
     */
    public void selectYesOrNo(String column, String columnName,String columnValue ,
            Map<String,Object> busInfo , Map<String,Object> returnMap);
    
    /** 
     * 描述    非动态表格通用生成PDF文件
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void commonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap);
    
    /**
     * 描述    国有建设用地使用权首次登记-不动产登记申请书签章
     * @author Allin Lin
     * @created 2020年10月16日 上午9:25:11
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void djSqsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    
    /**
     * 描述    国有建设用地使用权首次登记-授权委托书签章
     * @author Allin Lin
     * @created 2020年10月16日 上午9:25:11
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void wtsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 描述   国有建设用地使用权首次登记-签章所有用户与机构创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void signUserAndExinstitution(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
}
