/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述  不动产抵押权注销操作service
 * @author Allin Lin
 * @created 2019年3月5日 上午11:40:06
 */
public interface BdcDyqzxdjService extends BaseService{
    
    
    /**
     * 抵押权注销登记-注销申请书模板key
     */
    public static String ZXSQS_KEY="ZXSQS.docx";
    
    
    /**
     * 抵押权注销登记-结清证明模板key
     */
    public static String JQZM_KEY="JQZM.docx";
    
    /**
     *抵押权注销登记-注销申请书材料名称
     */
    public static String ZXSQS_MATERNAME="注销申请书";
    
    /**
     *抵押权注销登记-结清证明材料名称
     */
    public static String JQZM_MATERNAME="结清证明";
    
    /**
     * 描述    根据sqlFilter获取数据列表
     * @author Allin Lin
     * @created 2019年3月5日 下午2:41:18
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述：抵押权注销登记审批表数据回填
     * @author Rider Chen
     * @created 2020年3月26日 下午5:16:49
     * @param args
     */
    public void setDyqzxdjspbData(Map<String, Map<String, Object>> args);
    
    /**
     * 描述     抵押权注销登记前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年10月14日 下午2:44:26
     * @param flowVars
     * @return
     * @throws Exception
     */
    public Map<String, Object> dyqzxdjGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    /**
     * 描述    初始化签章材料业务数据
     * @author Allin Lin
     * @created 2020年10月14日 下午2:52:34
     * @param flowVars
     * @return
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars);
    
    /**
     * 描述    生成注销申请表
     * @author Allin Lin
     * @created 2020年10月14日 下午5:50:33
     * @param returnMap
     * @param path
     */
    public void generateSQB(Map<String, Object> returnMap,String path);
    
    /**
     * 描述    生成注销申请表PDF文件
     * @author Allin Lin
     * @created 2020年10月14日 下午9:44:30
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void zxSqbGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap);
    
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
     * 描述    抵押权注销登记-注销申请书签章
     * @author Allin Lin
     * @created 2020年10月16日 上午9:25:11
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void zxSqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    
    /** 
     * 描述  抵押权注销登记-结清证明签章  
     * @author Allin Lin
     * @created 2020年10月16日 下午2:47:02
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void zxJqzmSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    
    /**
     * 描述   抵押权注销登记-签章所有用户与机构创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void signUserAndExinstitution(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /** 
     * 描述      抵押权注销登记-创建抵押权人信息（企业或银行，含经办人）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creDyqrInfo(Map<String, Object> returnMap)throws Exception;
}
