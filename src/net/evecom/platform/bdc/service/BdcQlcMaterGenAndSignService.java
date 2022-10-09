/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述  不动产全流程全程网办材料生成及签章Service
 * @author Allin Lin
 * @created 2020年8月15日 上午11:07:27
 */
public interface BdcQlcMaterGenAndSignService extends BaseService{
    
    
     /**
     * 国有转移业务-材料模板文件夹key
     */
    public static String GYZY_KEY="gyjsjfwzydj";
    
    /**
     * 不动产通用业务-材料模板文件夹key
     */
    public static String GENERAL_KEY="general";
    
    /**
     * 预购商品房预告登记业务-材料模板文件夹key
     */
    public static String YG_KEY="ygspfygdj";

    /**
     * 抵押权首次登记（转本）业务-材料模板文件夹key
     */
    public static String DYQ_KEY="dyqscdj";
    
    /**
     * 申请表模板key
     */
    public static String SQS_KEY="BDCQLCSQB.docx";
    
    /**
     * 申请表模板key（平潭通办件）
     */
    public static String SQSPTT_KEY="BDCQLCSQBPTT.docx";
    
    /**
     * 询问笔录模板key
     */
    public static String XWBL_KEY="XWBL.docx";
    
    /**
     * 银行申请表模板key
     */
    public static String BANK_KEY="YHSQS.docx";
    
    /**
     * 抵押期限模板key
     */
    public static String DYQX_KEY="DYQXSM.docx";
    
    /**
     * 关于无法提供“预购商品房贷款抵押登记证明书”的说明签章模板key
     */
    public static String WFTGZM_KEY="YGDKDYDJSMS.docx";
    
    
    /**
     * 预告-申请表模板key
     */
    public static String YGSQS_KEY="bdcsqb.docx";
    
    
    /**
     * 预告-商品房约定书模板key
     */
    public static String YGSPF_KEY="spfygyds.docx";
    
    
    /**
     * 预告-安置房约定书模板key
     */
    public static String YGAZF_KEY="azfygyds.docx";
    
    
    /**
     * 预抵-申请表模板key
     */
    public static String YDSQS_KEY="bdcsqb-yd.docx";
    
    
    /**
     * 预抵-商品房约定书模板key
     */
    public static String YDSPF_KEY="spfydyds.docx";
    
    
    /**
     * 预抵-安置房约定书模板key
     */
    public static String YDAZF_KEY="azfydyds.docx";
    /**
     * 不动产通用模板key
     */
    public static String GENERALSQB_KEY="BDCGENERALSQB.docx";
    
    
    /**
     * 描述      国有建设用地使用权及房屋所有权转移登记前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> gyjsjfwzydjGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    
    /**
     * 描述      不动产全程网办通用模板前置事件（材料生成及签章）
     * @author Yanisin Shi
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> bdcGeneralGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    /**
     * 描述      不动产全程网办业务后置事件（退件，更改签章状态）
     * @author Allin Lin
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> bdcChangeSignStatus(Map<String,Object> flowVars)throws Exception;

    /**
     * 描述:不动产全程网办业务后置事件（退件，更改签章状态）
     *
     * @author Madison You
     * @created 2020/9/15 15:19:00
     * @param
     * @return
     */
    public Map<String,Object> bdcChangeSignStatusGyzy(Map<String,Object> flowVars)throws Exception;

    /**
     * 描述    预购商品房预告登记业务前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public Map<String, Object> ygspfygdjGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    
    /**
     * 描述    预购商品房抵押预告登记业务前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public Map<String, Object> ygspfdyygdjGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    /**
     * 
     * 描述    （预购商品房预告登记）-申请表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ygSqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 
     * 描述    （预购商品房预告登记）-预告约定书签章(商品房/安置房)签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ygYdsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 
     * 描述    （预购商品房抵押预告登记）-申请表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ydSqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 
     * 描述    （预购商品房抵押预告登记）-预抵约定书签章(商品房/安置房)
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ydYdsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
 
    
    /**
     * 描述    申请表签章
     * @author Allin Lin
     * @created 2020年8月19日 上午11:47:26
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void sqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;

    /**
     * 描述:存量房申请表签章
     *
     * @author Madison You
     * @created 2020/9/11 10:06:00
     * @param
     * @return
     */
    public void sqbSign1(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception;
    
    /**
     * 
     * 描述     不动产通用事项申请表签章
     * @author Allin Lin
     * @created 2021年12月27日 下午2:52:08
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bdcGeneralSqbSign(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 描述     询问笔录表签章
     * @author Allin Lin
     * @created 2020年8月19日 上午11:47:13
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void xwblSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 
     * 描述    银行申请表签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:43
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bankSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 
     * 描述    抵押期限声明签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:46
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void dyqxSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 
     * 描述    关于无法提供“预购商品房贷款抵押登记证明书”的说明签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:50
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void wftgsmSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;
    
    /** 
     * 描述    创建外部用户
     * @author Allin Lin
     * @created 2020年8月17日 下午5:09:14
     * @param variables
     * @return
     */
    public Map<String, Object> creExUser(Map<String, Object> variables)throws Exception;
    
    /** 
     * 描述    创建外部机构
     * @author Allin Lin
     * @created 2020年8月17日 下午5:09:14
     * @param variables
     * @return
     */
    public Map<String, Object> creExInstitutions(Map<String, Object> variables)throws Exception;
    
    
    /** 
     * 描述    创建银行信息（外部机构）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creBank(Map<String, Object> returnMap)throws Exception;
    
    
    /** 
     * 描述      预购商品房预告登记-创建开发商信息（义务人）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creDeveloper(Map<String, Object> returnMap)throws Exception;
    
    
    
    /** 
     * 描述    预购商品房抵押预告登记-创建开发商信息（权利人）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creYdDeveloper(Map<String, Object> returnMap)throws Exception;
    
    
    /**
     * 描述   预购商品房预告登记业务-签章所有用户创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void ygspfygdjSignUser(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    
    /**
     * 描述   预购商品房抵押预告登记-签章所有用户创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void ygspfdyygdjSignUser(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /** 
     * 描述    创建银行法人信息（外部用户）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creBankFr(Map<String, Object> returnMap)throws Exception;
    
    /** 
     * 描述    银行经办人信息（外部用户）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creBankJb(Map<String, Object> returnMap)throws Exception;
    
    /**
     * 描述     材料替换字符串&生成PDF
     * @author Allin Lin
     * @created 2020年8月20日 上午12:34:10
     * @param templateFolder  材料模板文件夹
     * @param templatePath  材料模板路径
     * @param materName  材料模板名称（唯一）
     * @param exeId  申报号
     * @param returnMap  业务信息
     */
    public void wordGenPdf(String templateFolder,String templatePath,String materName,
            String exeId,Map<String, Object> returnMap);
    
    /** 
     * 描述    预购商品房预告-申请表生成PDF
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ygSqbGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap);
    
    /** 
     * 描述    预购商品房预告-非动态表格通用生成PDF
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ygCommonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap);
  
    /** 
     * 描述    预购商品房抵押预告登记-申请表生成PDF(模板动态生成表格)
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ydSqbGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap);
    
    /** 
     * 描述     预购商品房抵押预告登记-非动态表格通用生成PDF
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ydCommonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap);
    
    /**
     * 描述    保存签署文档信息
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param docInfo
     * @param flowId
     */
    public void savaMaterSignInfo(Map<String, Object> docInfo,String flowId,String status) ;
    
    /**
     * 描述    保存签署流程回调信息
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param docInfo
     * @param flowId
     */
    public void savaSignFlowsInfo(Map<String, Object> signInfo,String flowId) ;   
    
    /**
     * 描述    获取文件fileKey信息
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param filePath
     * @param exe_id
     * @param flowId
     */
    public Map<String, Object> getFileKey(String filePath, String exe_id ,String materName); 
    
    /**
     * 描述    获取完结状态回调数据
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param resultJson
     */
    public Map<String, Object> getSignFlowFinishMap(JSONObject resultJson); 
    
    /**
     * 描述    获取过程更新状态回调数据
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param resultJson
     */
    public Map<String, Object> getSignFlowUpdateMap(JSONObject resultJson); 
    
    /**
     * 
     * @Description 根据申报号获取签署状态
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param exeId
     * @return String
     */
    public String getSignStatusByExeId(String exeId);
    
    /**
     * 
     * @Description 根据申报号获取签章材料列表
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param exeId
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findSignMaterListByExeId(String exeId);    
    
    /**
     * 
     * @Description 根据签署流程id获取签章明细列表
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param flowId
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findSignFlowListByflowId(String flowId);
    
    /**
     * 
     * @Description 抵押权首次登记（转本）前置事件（材料生成及签章）
     * @author Luffy Cai
     * @date 2020年8月25日
     * @param flowVars
     * @return
     * @throws Exception Map<String,Object>
     */
    public Map<String,Object> dyqscdjGenAndSign(Map<String,Object> flowVars)throws Exception;
    
    /**
     * 描述    签署流程作废（指定申报号）
     * @author Allin Lin
     * @created 2020年10月30日 下午10:17:17
     * @param exeId
     * @return
     */
    public Boolean cancelSignFlow(String exeId);
    
    /**
     * 
     * 描述    材料替换字符串&生成PDF(通用，书签不含【】)
     * @author Allin Lin
     * @created 2021年10月25日 上午10:10:15
     * @param templateFolder 模板路径
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     * @param type 类型 "dynamic表示为含动态表格"
     */
    public void commonGenPdf(String templateFolder,String templatePath,String materName,
            String exeId,Map<String, Object> returnMap,String type);
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-税务事项告知承诺书签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void swzmCnsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-存量房评估补充信息表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void clfPgBcXxSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-家庭唯一生活用房承诺书签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     * @param fwqlrMap
     *  @param index
     */
    public void wyShYfCnsSign(Map<String, Object> flowVars,Map<String, Object> returnMap,
            Map<String, Object> fwqlrMap,int index)throws Exception;
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-个人无尘赠与不动产登记表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void bdcZyDjbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-实名办税授权委托书签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void smBsSqWtsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
    
    
}
