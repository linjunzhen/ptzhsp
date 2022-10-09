/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WordToPdfUtil;
import net.evecom.platform.bdc.dao.BdcQlcMaterGenAndSignDao;
import net.evecom.platform.bdc.service.BdcDyqscdjService;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcYgspfService;
import net.evecom.platform.bdc.service.BdcYgspfygdjService;
import net.evecom.platform.bdc.util.BdcQlcSignUtil;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述  不动产全流程全程网办业务-材料生成及签章Service
 * @author Allin Lin
 * @created 2020年8月15日 上午11:09:52
 */
@Service("bdcQlcMaterGenAndSignService")
public class BdcQlcMaterGenAndSignServiceImpl extends BaseServiceImpl implements BdcQlcMaterGenAndSignService{
    
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcQlcMaterGenAndSignServiceImpl.class);
    
    
    /**
     * 引入dao
     */
    @Resource
    private BdcQlcMaterGenAndSignDao dao;
    
    
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    
    
    /**
     * 引入service
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;
    
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    
    /**
     * 引入Service
     */
    @Resource
    private BdcGyjsjfwzydjService bdcGyjsjfwzydjService;
    
    /**
     * 签章启动流程 bdcQlcCreateSignFlowService
     */
    @Resource
    private BdcQlcCreateSignFlowService bdcQlcCreateSignFlowService;
    
    /**
     * 
     */
    @Resource
    private BdcYgspfygdjService bdcYgspfygdjService;
    
    /**
     * 
     */
    @Resource
    private BdcDyqscdjService bdcDyqscdjService;    
    
    
    /**
     * 
     */
    @Resource
    private BdcYgspfService bdcYgspfService;
    
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 描述   覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年8月15日 上午11:20:20
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    
    /**
     * 描述    预购商品房抵押预告登记业务前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public Map<String, Object> ygspfdyygdjGenAndSign(Map<String,Object> flowVars)throws Exception{
        Map<String, Object> returnMap ;
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        //String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//前台申报方式（ 1智能审批  0全程网办  ）       
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作    
        String curStepName=StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));//当前环节名称
        String pttSqfs = StringUtil.getString(flowVars.get("PTT_SQFS"));//平潭通申请方式（ 1智能审批 2全程网办）
        if(StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                && StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)){
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            //全程网办触发签章（预抵流程个性化）-审批门户申请 / 平潭通全程网办
            if( ("2".equals(StringUtil.getString(busRecord.get("ISQCWB"))) && 
                    StringUtils.isNotEmpty(curStepName) && curStepName.equals("开始")) ||  
                    ("2".equals(pttSqfs) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审"))){
                /*合同类型（HTLX）*/
                String htlx =StringUtil.getString( flowVars.get("HT_LX"));
                //1、材料生成PDF
                /*材料业务数据初始化*/
                returnMap = bdcYgspfService.initGenValue(flowVars);
                /*材料替换字符串&生成PDF文件&签章流程*/
                if("现售合同".equals(htlx) || "预售合同".equals(htlx)){//商品房              
                    ydSqbGenPdf(BdcQlcMaterGenAndSignService.YDSQS_KEY,
                            BdcQlcCreateSignFlowService.YDSQS_MATERNAME,exeId,returnMap); //抵押预告申请表-生成PDF 
                    ydCommonGenPdf(BdcQlcMaterGenAndSignService.YDSPF_KEY,
                            BdcQlcCreateSignFlowService.YDSPF_MATERNAME,exeId,returnMap);//商品房预抵约定书-生成PDF 
                    ydSqbSign(flowVars,returnMap);//抵押预告申请表-签章 
                    if((boolean)flowVars.get("SIGN_FLAG")){
                        ydYdsSign(flowVars,returnMap);//商品房预抵约定书-签章
                    }               
                }
                if("拆迁安置协议".equals(htlx)){//安置房
                    ydSqbGenPdf(BdcQlcMaterGenAndSignService.YDSQS_KEY,
                            BdcQlcCreateSignFlowService.YDSQS_MATERNAME,exeId,returnMap); //抵押预告申请表-生成PDF 
                    ydCommonGenPdf(BdcQlcMaterGenAndSignService.YDAZF_KEY,
                            BdcQlcCreateSignFlowService.YDAZF_MATERNAME,exeId,returnMap);//安置房预抵约定书-生成PDF              
                    ydSqbSign(flowVars,returnMap);//抵押预告申请表-生成PDF 
                    if((boolean)flowVars.get("SIGN_FLAG")){
                        ydYdsSign(flowVars,returnMap);//安置房预抵约定书-生成PDF
                    }
                }  
            }
        }
        
        return flowVars;
    }
    
    /**
     * 描述    预购商品房预告登记业务前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public Map<String, Object> ygspfygdjGenAndSign(Map<String,Object> flowVars)throws Exception{
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//是否智能审批方式（1：是）       
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作（1是）
        if(StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                &&StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)
                &&"1".equals(isqcwb)){
           bdcYgspfygdjService.genAndSign(flowVars);
        }
        return flowVars;
    }
    
    
    /**
     * 描述    国有建设用地使用权及房屋所有权转移登记前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> gyjsjfwzydjGenAndSign(Map<String,Object> flowVars)throws Exception{
        Map<String, Object> returnMap = new HashMap<String, Object>();      
        String exeId = (String) flowVars.get("EFLOW_EXEID");//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作
        String curStepName=StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));//是否暂存操作
        if(StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                &&StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)){
            //获取流程实例数据
            Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if (flowExe == null) {
                flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] {exeId});
            }
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//前台申请方式（1：智能审批 0：全程网办）
            String pttSqfs = StringUtil.getValue(flowExe, "PTT_SQFS");//平潭通申报方式（1智能审批 2全程网办）
            if(StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs)){
                /*平潭通接入智能审批业务触发签章为网上预审环节定时器触发执行，因此需添加业务和流程实例数据*/
                flowVars.putAll(busRecord);
                flowVars.putAll(flowExe);
            }
            /*转移登记业务子项类型（1：分户  5：分户-抵押联办）*/
            String zyType = flowVars.get("ZYDJ_TYPE").toString();
            //1、材料生成PDF
            /*初始化材料字段*/
            bdcSpbConfig.bdcInitSpbVariables(returnMap);
            /*设置材料业务表数据*/
            bdcGyjsjfwzydjService.initBdcFieldValue(returnMap, flowVars); 
            /*材料替换字符串&生成PDF文件&签章流程*/
            if(("1".equals(zyType) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("开始") &&"1".equals(isqcwb))
                || ("1".equals(zyType) && StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs) 
                        && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审"))){//分户子项
                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.SQS_KEY,
                        BdcQlcCreateSignFlowService.SQS_MATERNAME,exeId,returnMap); //申请表生成PDF
//                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.XWBL_KEY,
//                        BdcQlcCreateSignFlowService.XWBL_MATERNAME,exeId,returnMap);//询问笔录生成PDF
                sqbSign(flowVars,returnMap);//申请表签章 
//                if((boolean)flowVars.get("SIGN_FLAG")){
//                    xwblSign(flowVars,returnMap);//询问笔录签章
//                }
                //throw new Exception("签章失败！");               
            }
//            if("5".equals(zyType) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("受理")){//分户抵押联办
//                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.SQS_KEY,
//                        BdcQlcCreateSignFlowService.SQS_MATERNAME,exeId,returnMap); //申请表生成PDF
//                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.XWBL_KEY,
//                        BdcQlcCreateSignFlowService.XWBL_MATERNAME,exeId,returnMap);//询问笔录生成PDF
//                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.BANK_KEY,
//                        BdcQlcCreateSignFlowService.BANKSQS_MATERNAME,exeId,returnMap);//银行申请表生成PDF
//                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.DYQX_KEY,
//                        BdcQlcCreateSignFlowService.QYQXSQS_MATERNAME,exeId,returnMap);//抵押期限声明生成PDF
//                wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.WFTGZM_KEY,
//                        BdcQlcCreateSignFlowService.WFTGSM_MATERNAME,exeId,returnMap);//关于无法提供“预购商品房贷款抵押登记证明书”的说明生成PDF
//                sqbSign(flowVars,returnMap);//申请表签章
//                if((boolean)flowVars.get("SIGN_FLAG")){
//                    xwblSign(flowVars,returnMap);//询问笔录签章
//                    if((boolean)flowVars.get("SIGN_FLAG")){
//                        bankSign(flowVars,returnMap);//银行申请表签章
//                        if((boolean)flowVars.get("SIGN_FLAG")){
//                            dyqxSign(flowVars,returnMap);//抵押期限声明签章
//                            if((boolean)flowVars.get("SIGN_FLAG")){
//                                wftgsmSign(flowVars,returnMap);//关于无法提供“预购商品房贷款抵押登记证明书”的说明签章
//                            }
//                        }
//                    }
//                }
//            }
            if (("4".equals(zyType) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审"))
                    || ("4".equals(zyType) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审")
                            && StringUtils.isNotEmpty(pttSqfs) && "2".equals(pttSqfs) )) {
               if(StringUtils.isNotEmpty(pttSqfs) && "2".equals(pttSqfs)){//平潭通办件申请表
                    wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.SQSPTT_KEY,
                            BdcQlcCreateSignFlowService.SQS_MATERNAME,exeId,returnMap); //申请表生成PDF
                }else{
                    wordGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcQlcMaterGenAndSignService.SQS_KEY,
                            BdcQlcCreateSignFlowService.SQS_MATERNAME,exeId,returnMap); //申请表生成PDF
                }
                sqbSign1(flowVars,returnMap);//申请表签章
                
                //存量房在线税费联办增加需签章文件
                String IS_AGREE = StringUtil.getString(flowVars.get("IS_AGREE"));
                String  GRSDS_MZ = StringUtil.getString(flowVars.get("GRSDS_MZ"));
                //String ESF_JY = StringUtil.getString(flowVars.get("ESF_JY"));
                //String GF_ZM = StringUtil.getString(flowVars.get("GF_ZM"));
                //String  WCZY_GR = StringUtil.getString(flowVars.get("WCZY_GR"));
                //String  IS_SWDJ = StringUtil.getString(flowVars.get("IS_SWDJ"));
               
                /*if(StringUtils.isNotEmpty(GF_ZM) && "0".equals(GF_ZM)){
                    //税务证明事项告知承诺书
                    commonGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcGyjsjfwzydjService.SWZMCNS_KEY,
                            BdcGyjsjfwzydjService.SWZMCNS_MATERNAME,exeId,returnMap,null);
                    swzmCnsSign(flowVars,returnMap);
                }*/
                
                //不动产权属转移涉税补充信息确认单
                if(StringUtils.isNotEmpty(IS_AGREE) && "0".equals(IS_AGREE)){
                    if((boolean)flowVars.get("SIGN_FLAG")){
                        commonGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcGyjsjfwzydjService.SSBCXXQRD_KEY,
                                BdcGyjsjfwzydjService.SSBCXXQRD_MATERNAME,exeId,returnMap,null);
                        ssbcxxqrdSign(flowVars,returnMap);
                    }
                    
                }
                //家庭唯一生活用房告知承诺书(多个房屋权利人需要下发多份文件签署)
                if(StringUtils.isNotEmpty(GRSDS_MZ) && "0".equals(GRSDS_MZ)){
                    List<Map<String, Object>> fwqlrList = (List<Map<String, Object>> )returnMap.get("fwqlrInfoList");
                    if(fwqlrList.size()>0){
                        for (int i = 0; i < fwqlrList.size(); i++) {
                            if((boolean)flowVars.get("SIGN_FLAG")){
                                Map<String, Object> fwQlrInfo = fwqlrList.get(i);
                                returnMap.put("wyyffwdz", StringUtil.getValue(fwQlrInfo, "WYYF_FWZL"));
                                returnMap.put("wyyfqlrxm", StringUtil.getValue(fwQlrInfo, "WYYF_QLRXM"));
                                returnMap.put("wyyfhyzk", dictionaryService.getDicNames("hyzk", 
                                        StringUtil.getValue(fwQlrInfo, "WYYF_HYZK")));
                                returnMap.put("wyyfqlrsfh", StringUtil.getValue(fwQlrInfo, "WYYF_QLRZJHM"));
                                returnMap.put("wyyfpoxm", StringUtil.getValue(fwQlrInfo, "WYYF_POXM"));
                                returnMap.put("wyyfposfh", StringUtil.getValue(fwQlrInfo, "WYYF_POZJHM"));
                                returnMap.put("wyyfzzcn", StringUtil.getValue(fwQlrInfo, "WYYF_ZZCN"));
                                returnMap.put("wyyflxdh", StringUtil.getValue(fwQlrInfo, "WYYF_LXDH"));
                                commonGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcGyjsjfwzydjService.WYSFYFCNS_KEY,
                                        (i+1)+"-"+BdcGyjsjfwzydjService.WYSFYFCNS_MATERNAME,exeId,returnMap,null);
                                wyShYfCnsSign(flowVars,returnMap,fwqlrList.get(i),(i+1)); 
                            }
                        }
                    }
                }
                
                
                //个人无偿赠予不动产登记表
                /*if(StringUtils.isNotEmpty(WCZY_GR) && "0".equals(WCZY_GR)){
                    commonGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcGyjsjfwzydjService.BDCZY_KEY,
                            BdcGyjsjfwzydjService.BDCZY_MATERNAME,exeId,returnMap,null);
                    bdcZyDjbSign(flowVars,returnMap);
                }*/
                //实名办税授权委托书
               /* if(StringUtils.isNotEmpty(IS_SWDJ) && "0".equals(IS_SWDJ)){
                    //设置表格的key和表头关键字key
                    returnMap.put("bsWtsKey", "wtrList");
                    returnMap.put("bsWtsTableKey", "swdjindex");
                    commonGenPdf(BdcQlcMaterGenAndSignService.GYZY_KEY,BdcGyjsjfwzydjService.SMBSSQWTS_KEY,
                            BdcGyjsjfwzydjService.SMBSSQWTS_MATERNAME,exeId,returnMap,"dynamic");
                    smBsSqWtsSign(flowVars,returnMap);
                }*/
            }
        }
        return flowVars;
    }
    /**
     * 描述    不动产全程网办通用模板事件（材料生成及签章）
     * @author Yanisin Shi
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> bdcGeneralGenAndSign(Map<String,Object> flowVars)throws Exception{
        Map<String, Object> returnMap = new HashMap<String, Object>();      
        String exeId = (String) flowVars.get("EFLOW_EXEID");//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作
        String curStepName=StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));//当前环节名称
        if(StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                &&StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)){
            //获取流程实例数据
            Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if (flowExe == null) {
                flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] {exeId});
            }
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//前台申请方式（1：智能审批 0：全程网办）
            String pttSqfs = StringUtil.getValue(flowExe, "PTT_SQFS");//平潭通申报方式（1智能审批 2全程网办）
            if(StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs)){
                /*平潭通接入智能审批业务触发签章为网上预审环节定时器触发执行，因此需添加业务和流程实例数据*/
                flowVars.putAll(busRecord);
                flowVars.putAll(flowExe);
            }
         
            //1、材料生成PDF
            /*初始化材料字段*/
            bdcSpbConfig.bdcInitSpbVariables(returnMap);
            /*设置材料业务表数据*/
            bdcGyjsjfwzydjService.initBdcGeneralFieldValue(returnMap, flowVars); 
          
            /*材料替换字符串&生成PDF文件&签章流程*/
            if((StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审") &&"0".equals(isqcwb))){
                wordGenPdf(BdcQlcMaterGenAndSignService.GENERAL_KEY,BdcQlcMaterGenAndSignService.GENERALSQB_KEY,
                           BdcQlcCreateSignFlowService.SQS_MATERNAME,exeId,returnMap); //申请表生成PDF
                bdcGeneralSqbSign(flowVars,returnMap);//申请表签章                        
            }
    }
        return flowVars;
    }
    
    
    /**
     * 
     * 描述     不动产通用事项申请表签章(同我要买房子业务申请表签章规则一致)
     * @author Allin Lin
     * @created 2021年12月27日 下午2:52:08
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bdcGeneralSqbSign(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID", "MATER_NAME"},new Object[]{flowVars.get("EFLOW_EXEID"), 
                        BdcQlcCreateSignFlowService.SQS_MATERNAME});
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        String ywrJson = StringUtil.getValue(flowVars, "YWPEOPLEINFO_JSON");
        List<Map<String,Object>> creQslyList = new ArrayList<>();
        returnMap.put("creQslyList", creQslyList);
        if (StringUtils.isNotEmpty(ywrJson)) {//义务人信息
            List<Map> ywrList = JSONArray.parseArray(ywrJson, Map.class);
            if(ywrList.size()>0){
                for (Map<String, Object> ywrMap : ywrList) {
                    Map<String, Object> user = new HashMap<>();
                    Map<String,Object> creQslyMap = new HashMap<>();
                    String YWPEOPLENAME = StringUtil.getValue(ywrMap, "YWPEOPLENAME");
                    String YWPEOPLEIDTYPE = StringUtil.getValue(ywrMap, "YWPEOPLEIDTYPE");
                    String YWPEOPLEIDNUM = StringUtil.getValue(ywrMap, "YWPEOPLEIDNUM");
                    String YWPEOPLEISWCNR = StringUtil.getValue(ywrMap, "YWPEOPLEISWCNR");
                    String YWPEOPLEMOBILE = StringUtil.getValue(ywrMap, "YWPEOPLEMOBILE");
                    String YWDLAGENTNAME = StringUtil.getValue(ywrMap, "YWDLAGENTNAME");
                    String YWDLAGENTTEL = StringUtil.getValue(ywrMap, "YWDLAGENTTEL");
                    String YWDLAGENTIDNUM = StringUtil.getValue(ywrMap, "YWDLAGENTIDNUM");
                    String YWDLAGENTIDTYPE = StringUtil.getValue(ywrMap, "YWDLAGENTIDTYPE");

                    if (YWPEOPLEIDTYPE.equals("JGDM") || YWPEOPLEIDTYPE.equals("YYZZ")||
                            YWPEOPLEIDTYPE.equals("XYDM")) { //判断是否企业
                        user.put("licenseNumber", YWPEOPLEIDNUM);//企业证照号码
                        user.put("organizeName", YWPEOPLENAME);//企业名称
                        user.put("licenseType", zjlxFormmat(YWPEOPLEIDTYPE));//企业证照类型
                        //企业经办信息
                        user.put("sqrsjhm", YWDLAGENTTEL);//手机号码
                        user.put("sqrzjhm", YWDLAGENTIDNUM);//证件号码
                        user.put("sqrzjlx", zjlxFormmat(YWDLAGENTIDTYPE));//证件类型
                        user.put("sqrxm", YWDLAGENTNAME);//姓名
                        Map<String, Object> exUser = creExInstitutions(user);
                        if (!(boolean) exUser.get(("INS_CREATEFLAG"))) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", flowVars.get("SIGN_MSG"));
                        } else {
                            creQslyMap.put("type", "QY");
                            creQslyMap.put("sqrzjhm", YWDLAGENTIDNUM);
                            creQslyMap.put("licenseNumber", YWPEOPLEIDNUM);
                        }
                    } else {
                        creQslyMap.put("isWcnr", YWPEOPLEISWCNR);
                        if (YWPEOPLEISWCNR.equals("0")) {   //判断是否未成年人 0 否 1 是
                            user.put("sqrxm", YWPEOPLENAME);
                            user.put("sqrzjhm",YWPEOPLEIDNUM);
                            user.put("sqrzjlx",zjlxFormmat(YWPEOPLEIDTYPE));
                            user.put("contactsEmail","");
                            user.put("sqrsjhm",YWPEOPLEMOBILE);
                            Map<String, Object> exUser = creExUser(user);
                            if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                                flowVars.put("SIGN_FLAG", false);
                                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                            } else {
                                creQslyMap.put("type", "GR");
                                creQslyMap.put("sqrzjhm", YWPEOPLEIDNUM);
                            }
                        } else {
                            user.put("sqrxm", YWDLAGENTNAME);
                            user.put("sqrzjhm", YWDLAGENTIDNUM);
                            user.put("sqrzjlx", zjlxFormmat(YWDLAGENTIDTYPE));
                            user.put("contactsEmail", "");
                            user.put("sqrsjhm", YWDLAGENTTEL);
                            Map<String, Object> exUser = creExUser(user);
                            if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                                flowVars.put("SIGN_FLAG", false);
                                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                            } else {
                                creQslyMap.put("type", "GR");
                                creQslyMap.put("sqrzjhm", YWDLAGENTIDNUM);
                            }
                        }
                    }
                
                    if (creQslyMap.size() > 0) {
                        creQslyList.add(creQslyMap);
                    }
                }
            }
          
        }
        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(flowVars, "POWERPEOPLEINFO_JSON");
        List<Map<String,Object>> creQlrList = new ArrayList<>();
        returnMap.put("creQlrList", creQlrList);
        if (StringUtils.isNotEmpty(piJson)) {
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                Map<String, Object> user = new HashMap<>();
                Map<String,Object> creQlrMap = new HashMap<>();
                String POWERPEOPLENAME = StringUtil.getValue(piMap, "POWERPEOPLENAME");
                //String POWERPEOPLENATURE = StringUtil.getValue(piMap, "POWERPEOPLENATURE");
                String POWERPEOPLEIDTYPE = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String POWERPEOPLEIDNUM = StringUtil.getValue(piMap, "POWERPEOPLEIDNUM");
                String POWERPEOPLEMOBILE = StringUtil.getValue(piMap, "POWERPEOPLEMOBILE");
                String POWAGENTNAME = StringUtil.getValue(piMap, "POWAGENTNAME");
                String POWAGENTIDTYPE = StringUtil.getValue(piMap, "POWAGENTIDTYPE");
                String POWAGENTTEL = StringUtil.getValue(piMap, "POWAGENTTEL");
                String POWAGENTIDNUM = StringUtil.getValue(piMap, "POWAGENTIDNUM");
                String POWERPEOPLEISWCNR = StringUtil.getValue(piMap, "POWERPEOPLEISWCNR");
                if (POWERPEOPLEIDTYPE.equals("JGDM") || POWERPEOPLEIDTYPE.equals("YYZZ")||
                        POWERPEOPLEIDTYPE.equals("XYDM")) {  //判断是否为企业
                    user.put("licenseNumber", POWERPEOPLEIDNUM);//企业证照号码
                    user.put("organizeName", POWERPEOPLENAME);//企业名称
                    user.put("licenseType", zjlxFormmat(POWERPEOPLEIDTYPE));//企业证照类型
                    //企业经办信息
                    user.put("sqrsjhm", POWAGENTTEL);//手机号码
                    user.put("sqrzjhm", POWAGENTIDNUM);//证件号码
                    user.put("sqrzjlx", zjlxFormmat(POWAGENTIDTYPE));//证件类型
                    user.put("sqrxm", POWAGENTNAME);//姓名
                    Map<String, Object> exUser = creExInstitutions(user);
                    if (!(boolean) exUser.get(("INS_CREATEFLAG"))) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", flowVars.get("SIGN_MSG"));
                    } else {
                        creQlrMap.put("type", "QY");
                        creQlrMap.put("sqrzjhm", POWAGENTIDNUM);
                        creQlrMap.put("licenseNumber", POWERPEOPLEIDNUM);
                    }
                } else {
                    creQlrMap.put("isWcnr", POWERPEOPLEISWCNR);
                    if (POWERPEOPLEISWCNR.equals("0")) {//判断是否未成年人
                        user.put("sqrxm", POWERPEOPLENAME);
                        user.put("sqrzjhm",POWERPEOPLEIDNUM);
                        user.put("sqrzjlx",zjlxFormmat(POWERPEOPLEIDTYPE));
                        user.put("contactsEmail","");
                        user.put("sqrsjhm",POWERPEOPLEMOBILE);
                        Map<String, Object> exUser = creExUser(user);
                        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                        } else {
                            creQlrMap.put("type", "GR");
                            creQlrMap.put("sqrzjhm", POWERPEOPLEIDNUM);
                        }
                    } else {
                        user.put("sqrxm", POWAGENTNAME);
                        user.put("sqrzjhm", POWAGENTIDNUM);
                        user.put("sqrzjlx", zjlxFormmat(POWAGENTIDTYPE));
                        user.put("contactsEmail", "");
                        user.put("sqrsjhm", POWAGENTTEL);
                        Map<String, Object> exUser = creExUser(user);
                        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                        } else {
                            creQlrMap.put("type", "GR");
                            creQlrMap.put("sqrzjhm", POWAGENTIDNUM);
                        }
                    }
                }
                if (creQlrMap.size() > 0) {
                    creQlrList.add(creQlrMap);
                }
            }
        }
        if ((boolean) flowVars.get("SIGN_FLAG")) {
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.SQS_MATERNAME);
            if (!(boolean) fileResult.get("SIGN_FLAG")) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            } else {
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfSqb1(flowVars, returnMap);
                if (!(boolean) result.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }
    }
    
    
    private String zjlxFormmat(String zjlx){
        String fmtzjlx = "";
        if(zjlx.equals("身份证") || zjlx.equals("SF")){
            fmtzjlx = "IDCard";
        }else if(zjlx.equals("港澳居民来往内地通行证") || zjlx.equals("GATX")){
            fmtzjlx = "HMPass";
        }else if(zjlx.equals("护照") || zjlx.equals("HZ")){
            fmtzjlx = "Passport";
        }else if(zjlx.equals("台湾居民来往内地通行证") || zjlx.equals("TWTX")){
            fmtzjlx = "MTP";
        }else if(zjlx.equals("营业执照")||zjlx.equals("统一社会信用代码") ||
                zjlx.equals("YYZZ")||zjlx.equals("XYDM")){
            fmtzjlx = "SOCNO";
        }else if(zjlx.equals("组织机构代码") || zjlx.equals("JGDM")){
            fmtzjlx = "ORANO";
        }else{
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-不动产权属转移涉税补充信息确认单签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ssbcxxqrdSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsjfwzydjService.SSBCXXQRD_MATERNAME});
        //2.创建用户&机构
        bdcGyjsjfwzydjService.clfsflbSssbSignUser(flowVars, returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcGyjsjfwzydjService.SSBCXXQRD_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfClfSsbcxxQrd(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }        
        }   
    }
    
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-实名办税授权委托书签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void smBsSqWtsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsjfwzydjService.SMBSSQWTS_MATERNAME});
        //2.创建用户&机构
        bdcGyjsjfwzydjService.clfsflbSssbSignUser(flowVars, returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcGyjsjfwzydjService.SMBSSQWTS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfClfSmBsSqWts(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }        
        }   
    }
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-个人无尘赠与不动产登记表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void bdcZyDjbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsjfwzydjService.BDCZY_MATERNAME});
        //2.创建用户&机构
        bdcGyjsjfwzydjService.clfsflbSssbSignUser(flowVars, returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcGyjsjfwzydjService.BDCZY_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfClfBdcZyDjb(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }        
        }   
    }
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-家庭唯一生活用房承诺书签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     * @param fwqlrMap
     * @param index
     */
    public void wyShYfCnsSign(Map<String, Object> flowVars,Map<String, Object> returnMap,
            Map<String, Object> fwqlrMap,int index)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),index+"-"+BdcGyjsjfwzydjService.WYSFYFCNS_MATERNAME});
        //2.创建用户&机构
        bdcGyjsjfwzydjService.clfsflbSssbSignUser(flowVars, returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),index+"-"+BdcGyjsjfwzydjService.WYSFYFCNS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfClfJtWyYfCns(flowVars,returnMap,fwqlrMap,index); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }        
        }   
    }
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-存量房评估补充信息表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void clfPgBcXxSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsjfwzydjService.CLFPGBCXX_MATERNAME});
        //2.创建用户&机构
        bdcGyjsjfwzydjService.clfsflbSssbSignUser(flowVars, returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcGyjsjfwzydjService.CLFPGBCXX_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfClfPgBcXx(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }        
        }   
    }
    
    /**
     * 
     * 描述    国有转移-存量房税费联办业务-税务事项告知承诺书签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void swzmCnsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsjfwzydjService.SWZMCNS_MATERNAME});
        //2.创建用户&机构
        bdcGyjsjfwzydjService.clfsflbSssbSignUser(flowVars, returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcGyjsjfwzydjService.SWZMCNS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfClfSwzmCns(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }        
        }   
    }
 
    /**
     * 描述   不动产全程网办业务后置事件（退回，更改签章状态）
     * @author Allin Lin
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> bdcChangeSignStatus(Map<String,Object> flowVars)throws Exception{
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        //String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回更新签章状态
        if( "true".equals(eflowIsback)){
            StringBuffer sql=new StringBuffer();
            sql.append(" update T_BDCQLC_MATERSIGNINFO set is_sign='0' , DOWNLOAD_DOCURL = '' ");
            sql.append(" where exe_id=? ");
            dao.executeSql(sql.toString(),new Object[]{exeId});
        }
        return flowVars;
    }

    /**
     * 描述:不动产全程网办业务后置事件（退件，更改签章状态）
     *
     * @author Madison You
     * @created 2020/9/15 14:55:00
     * @param
     * @return
     */
    public Map<String,Object> bdcChangeSignStatusGyzy(Map<String,Object> flowVars)throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        String curStepName = (String) flowVars.get("EFLOW_CUREXERUNNINGNODENAMES");//申报号
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回更新签章状态
        Map<String, Object> exeMap = dao.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{exeId});
        String busTableName = exeMap.get("BUS_TABLENAME").toString();
        //国有转移6个事项虚拟主表替换真实表名称
        if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String busRecordId = exeMap.get("BUS_RECORDID").toString();
        String sqfs = exeMap.get("SQFS").toString();
        String primaryKeyName = dao.getPrimaryKeyName(busTableName).get(0).toString();
        Map<String, Object> busMap = dao.getByJdbc(busTableName, new String[]{primaryKeyName}, new Object[]{busRecordId});
        String zydjType = StringUtil.getValue(busMap, "ZYDJ_TYPE");
        if( "true".equals(eflowIsback) && StringUtils.isNotEmpty(sqfs) && sqfs.equals("1")){
            if (zydjType.equals("1") || zydjType.equals("5")) {
                if (StringUtils.isNotEmpty(curStepName) && (curStepName.equals("受理"))) {
                    StringBuffer sql=new StringBuffer();
                    sql.append(" update T_BDCQLC_MATERSIGNINFO set is_sign='0' , DOWNLOAD_DOCURL = '' ");
                    sql.append(" where exe_id=? ");
                    dao.executeSql(sql.toString(),new Object[]{exeId});
                }
            } else if (zydjType.equals("4")) {
                if (StringUtils.isNotEmpty(curStepName) && curStepName.equals("待受理")) {
                    StringBuffer sql=new StringBuffer();
                    sql.append(" update T_BDCQLC_MATERSIGNINFO set is_sign='0' , DOWNLOAD_DOCURL = '' ");
                    sql.append(" where exe_id=? ");
                    dao.executeSql(sql.toString(),new Object[]{exeId});
                }
            }
        }
        return flowVars;
    }

    
    /**
     * 
     * 描述    （预购商品房预告登记）-申请表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ygSqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcQlcCreateSignFlowService.YGSQS_MATERNAME});
        //2.创建用户&机构
        ygspfygdjSignUser(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.YGSQS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfYgbdcsqs(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                } 
            }        
        }   
    } 
    
    
    /**
     * 
     * 描述    （预购商品房预告登记）-预告约定书签章(商品房/安置房)
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ygYdsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        /*合同类型（HTLX）*/
        String htlx =StringUtil.getString( flowVars.get("HTLX"));
        String materName = "";
        if("现售合同".equals(htlx) || "预售合同".equals(htlx)){
             materName = BdcQlcCreateSignFlowService.YGSPF_MATERNAME;//预告商品房 约定书
        }
        if("拆迁安置协议".equals(htlx)){//安置房
            materName = BdcQlcCreateSignFlowService.YGAZF_MATERNAME;//预告安置房约定书
        }
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),materName});
        //2.创建用户&机构
        ygspfygdjSignUser(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),materName);
            if(!(boolean)fileResult.get("SIGN_FLAG")) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else {
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfYgyds(flowVars, returnMap);
                if (!(boolean) result.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }        
    }   
    
    
    /**
     * 
     * 描述    （预购商品房抵押预告登记）-预抵约定书签章(商品房/安置房)
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ydYdsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        /*合同类型（HTLX）*/
        String htlx =StringUtil.getString( flowVars.get("HT_LX"));
        String materName = "";
        if("现售合同".equals(htlx) || "预售合同".equals(htlx)){
             materName = BdcQlcCreateSignFlowService.YDSPF_MATERNAME;//预抵商品房约定书
        }
        if("拆迁安置协议".equals(htlx)){//安置房
            materName = BdcQlcCreateSignFlowService.YDAZF_MATERNAME;//预抵安置房约定书
        }
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),materName});
        //2.创建(预抵)用户&机构
        ygspfdyygdjSignUser(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),materName);
            if(!(boolean)fileResult.get("SIGN_FLAG")) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else {
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfYdyds(flowVars, returnMap);
                if (!(boolean) result.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }        
    }
    
    /**
     * 
     * 描述    （预购商品房抵押预告登记）-申请表签章
     * @author Allin Lin
     * @created 2020年8月24日 下午3:12:00
     * @param flowVars
     * @param returnMap
     */
    public void ydSqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
      //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcQlcCreateSignFlowService.YDSQS_MATERNAME});
        //2.创建(预抵)用户&机构
        ygspfdyygdjSignUser(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.YDSQS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfYdbdcsqs(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                } 
            }        
        }   
    }

    
    /**
     * 描述   预购商品房预告登记业务-签章所有用户创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void ygspfygdjSignUser(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //2.创建外部用户（权利人）
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        List<Map<String, Object>> qlrList = (List<Map<String, Object>> )returnMap.get("QLR_LIST");
        for (Map<String, Object> qlr : qlrList) {
            Map<String, Object> user = new HashMap<String, Object>();
            user.put("sqrsjhm", qlr.get("MSFSJHM"));
            user.put("sqrzjhm", qlr.get("MSFZJHM"));
            user.put("sqrzjlx", qlr.get("MSFZJLB_F"));
            user.put("sqrxm", qlr.get("MSFXM"));
            Map<String, Object> exUser = creExUser(user); 
            if(!(boolean)exUser.get("USER_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                break;
            }
        }       
        if((boolean)flowVars.get(("SIGN_FLAG"))){ 
            //3.创建外部用户（权利人-代理人（领证人））
            String sqrsjhm=StringUtil.getString(returnMap.get("LZRSJHM"));
            String sqrzjhm=StringUtil.getString(returnMap.get("LZRZJHM"));
            String sqrzjlx=StringUtil.getString(returnMap.get("LZRZJLB"));
            String sqrxm=StringUtil.getString(returnMap.get("LZRXM"));
            if(StringUtils.isNotEmpty(sqrsjhm) && StringUtils.isNotEmpty(sqrzjhm) 
                    && StringUtils.isNotEmpty(sqrzjlx) && StringUtils.isNotEmpty(sqrxm)){
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("sqrsjhm", returnMap.get("LZRSJHM"));
                user.put("sqrzjhm", returnMap.get("LZRZJHM"));
                user.put("sqrzjlx", returnMap.get("LZRZJLB"));
                user.put("sqrxm", returnMap.get("LZRXM"));
                Map<String, Object> exUser = creExUser(user);
                if(!(boolean)exUser.get("USER_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));  
                }
            }
            if((boolean)flowVars.get(("SIGN_FLAG"))){
                //4.创建外部机构（义务人-开发商（含经办人））
                Map<String, Object> developer = creDeveloper(returnMap);
                if(!(boolean)developer.get("INS_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", developer.get("SIGN_MSG"));  
            }  
          }
        }       
    }
    
    
    /**
     * 描述   预购商品房抵押预告登记-签章所有用户创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void ygspfdyygdjSignUser(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //2.创建外部用户:（义务人-代理人存在则无需创建义务人，不存在则需创建义务人）
        flowVars.put("SIGN_FLAG", true);//设置签章标志位   
        String sqrsjhm=StringUtil.getString(returnMap.get("DLR2_SJHM"));
        String sqrzjhm=StringUtil.getString(returnMap.get("DLR2_ZJNO"));
        String sqrzjlx=StringUtil.getString(returnMap.get("DLR2_ZJLX_C"));
        String sqrxm=StringUtil.getString(returnMap.get("DLR2_MC"));
        if(StringUtils.isNotEmpty(sqrsjhm) && StringUtils.isNotEmpty(sqrzjhm) 
                && StringUtils.isNotEmpty(sqrzjlx) && StringUtils.isNotEmpty(sqrxm)){
            Map<String, Object> user = new HashMap<String, Object>();
            user.put("sqrsjhm", sqrsjhm);
            user.put("sqrzjhm", sqrzjhm);
            user.put("sqrzjlx", sqrzjlx);
            user.put("sqrxm", sqrxm);
            Map<String, Object> exUser = creExUser(user);
            if(!(boolean)exUser.get("USER_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));  
            }
        }else{
            //3.创建外部用户:义务人（多个）
            List<Map<String, Object>> ywrList = (List<Map<String, Object>> )returnMap.get("YWR_LIST");
            for (Map<String, Object> ywr : ywrList) {
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("sqrsjhm", ywr.get("YWR_SJHM"));
                user.put("sqrzjhm", ywr.get("YWR_ZJNO"));
                user.put("sqrzjlx", ywr.get("YWR_ZJLX_C"));
                user.put("sqrxm", ywr.get("YWR_MC"));
                Map<String, Object> exUser = creExUser(user); 
                if(!(boolean)exUser.get("USER_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                    break;
                }
            }
        } 
        if((boolean)flowVars.get(("SIGN_FLAG"))){ 
          //4.创建外部机构（权利人-开发商（含经办人））
            Map<String, Object> developer = creYdDeveloper(returnMap);
            if(!(boolean)developer.get("INS_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", developer.get("SIGN_MSG"));  
            }
        }     
    }
    
    
    /**
     * 描述  （国有转移）申请表签章
     * @author Allin Lin
     * @created 2020年8月18日 下午9:23:52
     * @param flowVars  流程提交所有信息 returnMap
     * @param returnMap 所有材料模板中业务数据
     * @throws Exception
     * @see net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService#sqbSign(java.util.Map, java.util.Map)
     */
    public void sqbSign(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception {
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", new String[]{"EXE_ID", "MATER_NAME"},
                new Object[]{flowVars.get("EFLOW_EXEID"), BdcQlcCreateSignFlowService.SQS_MATERNAME});
       /* //2.创建外部用户（申请人）
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        List<Map<String, Object>> sqlList = (List<Map<String, Object>>) returnMap.get("sqrInfoList");
        for (Map<String, Object> sql : sqlList) {
            Map<String, Object> exUser = creExUser(sql);
            if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                flowVars.put("SIGN_FLAG", false);
                break;
            }
        }
        if ((boolean) flowVars.get(("SIGN_FLAG"))) {
            // 3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.SQS_MATERNAME);
            if (!(boolean) fileResult.get("SIGN_FLAG")) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            } else {
                // 4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfSqb(flowVars, returnMap);
                if (!(boolean) result.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }*/
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //2.创建外部用户（经办人）
        String  jbrName=StringUtil.getString(flowVars.get("JBR_NAME"));
        String  jbrZjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String  jbrZjlx="IDCard";
        String  jbrMobile=StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String  jbrEmail=StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String,Object> user=new HashMap<>();
        user.put("sqrxm",jbrName);
        user.put("sqrzjhm",jbrZjhm);
        user.put("sqrzjlx",jbrZjlx);
        user.put("contactsEmail",jbrEmail);
        user.put("sqrsjhm",jbrMobile);
        Map<String, Object> exUser = creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        }else{
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.SQS_MATERNAME);
            if (!(boolean) fileResult.get("SIGN_FLAG")) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            } else {
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfSqb(flowVars, returnMap);
                if (!(boolean) result.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }
    
    }

    /**
     * 描述:存量房申请表签章
     *
     * @author Madison You
     * @created 2020/9/11 10:06:00
     * @param
     * @return
     */
    @Override
    public void sqbSign1(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception {
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", new String[]{"EXE_ID", "MATER_NAME"},
                new Object[]{flowVars.get("EFLOW_EXEID"), BdcQlcCreateSignFlowService.SQS_MATERNAME});
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        String qsJson = StringUtil.getValue(flowVars, "POWERSOURCEINFO_JSON");
        String EXE_SUBBUSCLASS = StringUtil.getValue(flowVars, "EXE_SUBBUSCLASS");
        List<Map<String,Object>> creQslyList = new ArrayList<>();
        returnMap.put("creQslyList", creQslyList);
        if (StringUtils.isNotEmpty(qsJson)) {
            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                Map<String, Object> user = new HashMap<>();
                Map<String,Object> creQslyMap = new HashMap<>();
                String POWERSOURCE_QLRMC = StringUtil.getValue(qsMap, "POWERSOURCE_QLRMC");
                String POWERSOURCE_ZJLX = StringUtil.getValue(qsMap, "POWERSOURCE_ZJLX");
                String POWERSOURCE_ZJHM = StringUtil.getValue(qsMap, "POWERSOURCE_ZJHM");
                String POWERSOURCE_ISWCNR = StringUtil.getValue(qsMap, "POWERSOURCE_ISWCNR");
                String POWERSOURCE_QLR_TEL = StringUtil.getValue(qsMap, "POWERSOURCE_QLR_TEL");
                String POWERSOURCE_DLR = StringUtil.getValue(qsMap, "POWERSOURCE_DLR");
                String POWERSOURCE_DLR_TEL = StringUtil.getValue(qsMap, "POWERSOURCE_DLR_TEL");
                String POWERSOURCE_DLR_CARD = StringUtil.getValue(qsMap, "POWERSOURCE_DLR_CARD");

                if (!(EXE_SUBBUSCLASS.equals("判决或法拍（税费联办）") || EXE_SUBBUSCLASS.equals("继承（税费联办）"))) { //判断是否是 判决或法拍（税费联办）
                    if (POWERSOURCE_ZJLX.equals("组织机构代码") || POWERSOURCE_ZJLX.equals("营业执照") ||
                            POWERSOURCE_ZJLX.equals("统一社会信用代码")) { //判断是否企业
                        user.put("licenseNumber", POWERSOURCE_ZJHM);//银行证照号码
                        user.put("organizeName", POWERSOURCE_QLRMC);//银行名称
                        user.put("licenseType", "SOCNO");//银行证照类型
                        //银行经办信息
                        user.put("sqrsjhm", POWERSOURCE_DLR_TEL);//手机号码
                        user.put("sqrzjhm", POWERSOURCE_DLR_CARD);//证件号码
                        user.put("sqrzjlx", "IDCard");//证件类型
                        user.put("sqrxm", POWERSOURCE_DLR);//姓名
                        Map<String, Object> exUser = creExInstitutions(user);
                        if (!(boolean) exUser.get(("INS_CREATEFLAG"))) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", flowVars.get("SIGN_MSG"));
                        } else {
                            creQslyMap.put("type", "QY");
                            creQslyMap.put("sqrzjhm", POWERSOURCE_DLR_CARD);
                            creQslyMap.put("licenseNumber", POWERSOURCE_ZJHM);
                        }
                    } else {
                        creQslyMap.put("isWcnr", POWERSOURCE_ISWCNR);
                        if (POWERSOURCE_ISWCNR.equals("0")) {   //判断是否未成年人
                            user.put("sqrxm", POWERSOURCE_QLRMC);
                            user.put("sqrzjhm",POWERSOURCE_ZJHM);
                            user.put("sqrzjlx","IDCard");
                            user.put("contactsEmail","");
                            user.put("sqrsjhm",POWERSOURCE_QLR_TEL);
                            Map<String, Object> exUser = creExUser(user);
                            if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                                flowVars.put("SIGN_FLAG", false);
                                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                            } else {
                                creQslyMap.put("type", "GR");
                                creQslyMap.put("sqrzjhm", POWERSOURCE_ZJHM);
                            }
                        } else {
                            user.put("sqrxm", POWERSOURCE_DLR);
                            user.put("sqrzjhm", POWERSOURCE_DLR_CARD);
                            user.put("sqrzjlx", "IDCard");
                            user.put("contactsEmail", "");
                            user.put("sqrsjhm", POWERSOURCE_DLR_TEL);
                            Map<String, Object> exUser = creExUser(user);
                            if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                                flowVars.put("SIGN_FLAG", false);
                                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                            } else {
                                creQslyMap.put("type", "GR");
                                creQslyMap.put("sqrzjhm", POWERSOURCE_DLR_CARD);
                            }
                        }
                    }
                }
                if (creQslyMap.size() > 0) {
                    creQslyList.add(creQslyMap);
                }
            }
        }
        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(flowVars, "POWERPEOPLEINFO_JSON");
        List<Map<String,Object>> creQlrList = new ArrayList<>();
        returnMap.put("creQlrList", creQlrList);
        if (StringUtils.isNotEmpty(piJson)) {
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                Map<String, Object> user = new HashMap<>();
                Map<String,Object> creQlrMap = new HashMap<>();
                String POWERPEOPLENAME = StringUtil.getValue(piMap, "POWERPEOPLENAME");
                String POWERPEOPLENATURE = StringUtil.getValue(piMap, "POWERPEOPLENATURE");
//                String POWERPEOPLEIDTYPE = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String POWERPEOPLEIDNUM = StringUtil.getValue(piMap, "POWERPEOPLEIDNUM");
                String POWERPEOPLEMOBILE = StringUtil.getValue(piMap, "POWERPEOPLEMOBILE");
                String POWAGENTNAME = StringUtil.getValue(piMap, "POWAGENTNAME");
//                String POWAGENTIDTYPE = StringUtil.getValue(piMap, "POWAGENTIDTYPE");
                String POWAGENTTEL = StringUtil.getValue(piMap, "POWAGENTTEL");
                String POWAGENTIDNUM = StringUtil.getValue(piMap, "POWAGENTIDNUM");
                String POWERPEOPLEISWCNR = StringUtil.getValue(piMap, "POWERPEOPLEISWCNR");
                if (POWERPEOPLENATURE.equals("2")) {  //判断是否为企业
                    user.put("licenseNumber", POWERPEOPLEIDNUM);//银行证照号码
                    user.put("organizeName", POWERPEOPLENAME);//银行名称
                    user.put("licenseType", "SOCNO");//银行证照类型
                    //银行经办信息
                    user.put("sqrsjhm", POWAGENTTEL);//手机号码
                    user.put("sqrzjhm", POWAGENTIDNUM);//证件号码
                    user.put("sqrzjlx", "IDCard");//证件类型
                    user.put("sqrxm", POWAGENTNAME);//姓名
                    Map<String, Object> exUser = creExInstitutions(user);
                    if (!(boolean) exUser.get(("INS_CREATEFLAG"))) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", flowVars.get("SIGN_MSG"));
                    } else {
                        creQlrMap.put("type", "QY");
                        creQlrMap.put("sqrzjhm", POWAGENTIDNUM);
                        creQlrMap.put("licenseNumber", POWERPEOPLEIDNUM);
                    }
                } else {
                    creQlrMap.put("isWcnr", POWERPEOPLEISWCNR);
                    if (POWERPEOPLEISWCNR.equals("0")) {//判断是否未成年人
                        user.put("sqrxm", POWERPEOPLENAME);
                        user.put("sqrzjhm",POWERPEOPLEIDNUM);
                        user.put("sqrzjlx","IDCard");
                        user.put("contactsEmail","");
                        user.put("sqrsjhm",POWERPEOPLEMOBILE);
                        Map<String, Object> exUser = creExUser(user);
                        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                        } else {
                            creQlrMap.put("type", "GR");
                            creQlrMap.put("sqrzjhm", POWERPEOPLEIDNUM);
                        }
                    } else {
                        user.put("sqrxm", POWAGENTNAME);
                        user.put("sqrzjhm", POWAGENTIDNUM);
                        user.put("sqrzjlx", "IDCard");
                        user.put("contactsEmail", "");
                        user.put("sqrsjhm", POWAGENTTEL);
                        Map<String, Object> exUser = creExUser(user);
                        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                        } else {
                            creQlrMap.put("type", "GR");
                            creQlrMap.put("sqrzjhm", POWAGENTIDNUM);
                        }
                    }
                }
                if (creQlrMap.size() > 0) {
                    creQlrList.add(creQlrMap);
                }
            }
        }
        if ((boolean) flowVars.get("SIGN_FLAG")) {
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.SQS_MATERNAME);
            if (!(boolean) fileResult.get("SIGN_FLAG")) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            } else {
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfSqb1(flowVars, returnMap);
                if (!(boolean) result.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }
    }
    /**
     * 描述   （国有转移）  询问笔录表签章
     * @author Allin Lin
     * @created 2020年8月17日 下午5:02:43
     * @param variables
     */
    public void xwblSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcQlcCreateSignFlowService.XWBL_MATERNAME});
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //2.创建外部用户（经办人）
        String  jbrName=StringUtil.getString(flowVars.get("JBR_NAME"));
        String  jbrZjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String  jbrZjlx="IDCard";
        String  jbrMobile=StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String  jbrEmail=StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String,Object> user=new HashMap<>();
        user.put("sqrxm",jbrName);
        user.put("sqrzjhm",jbrZjhm);
        user.put("sqrzjlx",jbrZjlx);
        user.put("contactsEmail",jbrEmail);
        user.put("sqrsjhm",jbrMobile);
        Map<String, Object> exUser = creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        }else{
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.XWBL_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfXwbl(flowVars,returnMap);
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }
        }
        /*//2.创建外部用户（申请人）
        List<Map<String, Object>> sqlList = (List<Map<String, Object>> )returnMap.get("sqrInfoList");
        for (Map<String, Object> sql : sqlList) {
            Map<String, Object> exUser = creExUser(sql); 
            if(!(boolean)exUser.get("USER_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                break;
            }
        }
        if((boolean)flowVars.get(("SIGN_FLAG"))){
            //3.文件直传
            Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.XWBL_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfXwbl(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                }
            }   
        }*/
        
    }
    
    /**
     * 
     * 描述    （国有转移）银行申请表签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:43
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bankSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[]{flowVars.get("EFLOW_EXEID"),BdcQlcCreateSignFlowService.BANKSQS_MATERNAME});
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //2.创建外部用户（经办人）
        String  jbrName=StringUtil.getString(flowVars.get("JBR_NAME"));
        String  jbrZjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String  jbrZjlx="IDCard";
        String  jbrMobile=StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String  jbrEmail=StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String,Object> user=new HashMap<>();
        user.put("sqrxm",jbrName);
        user.put("sqrzjhm",jbrZjhm);
        user.put("sqrzjlx",jbrZjlx);
        user.put("contactsEmail",jbrEmail);
        user.put("sqrsjhm",jbrMobile);
        Map<String, Object> exUser = creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        }else{
            //3.创建机构（银行&经办人信息）
            Map<String, Object> bank = creBank(returnMap);
            if (!(boolean) bank.get(("INS_CREATEFLAG"))) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
            } else {
                // 5.文件直传
                Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                        flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.BANKSQS_MATERNAME);
                if (!(boolean) fileResult.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                } else {
                    // 6.签署流程
                    Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfBankSqb(flowVars,
                            returnMap);
                    if (!(boolean) result.get("SIGN_FLAG")) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                    }
                }
            }
        }
        /*//2.创建外部用户（申请人）
        List<Map<String, Object>> sqlList = (List<Map<String, Object>> )returnMap.get("sqrInfoList");
        for (Map<String, Object> sql : sqlList) {
            Map<String, Object> exUser = creExUser(sql); 
            if(!(boolean)exUser.get("USER_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                break;
            }
        }       
        // 3.创建机构信息（银行）
        if ((boolean) flowVars.get(("SIGN_FLAG"))) {
            Map<String, Object> bank = creBank(returnMap);
            if (!(boolean) bank.get(("INS_CREATEFLAG"))) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
            } else {
                // 4.创建外部用户（银行法人）
                Map<String, Object> bankFr = creBankFr(returnMap);
                if (!(boolean) bankFr.get("USER_CREATEFLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", bankFr.get("SIGN_MSG"));
                } else {
                    // 5.文件直传
                    Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                            flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.BANKSQS_MATERNAME);
                    if (!(boolean) fileResult.get("SIGN_FLAG")) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                    } else {
                        // 6.签署流程
                        Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfBankSqb(flowVars,
                                returnMap);
                        if (!(boolean) result.get("SIGN_FLAG")) {
                            flowVars.put("SIGN_FLAG", false);
                            flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                        }
                    }
                }
            }
        }*/
    }
    
    /**
     * 
     * 描述   （国有转移） 抵押期限声明签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:46
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void dyqxSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[]{flowVars.get("EFLOW_EXEID"),BdcQlcCreateSignFlowService.QYQXSQS_MATERNAME});
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //2.创建外部用户（经办人）
        String  jbrName=StringUtil.getString(flowVars.get("JBR_NAME"));
        String  jbrZjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String  jbrZjlx="IDCard";
        String  jbrMobile=StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String  jbrEmail=StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String,Object> user=new HashMap<>();
        user.put("sqrxm",jbrName);
        user.put("sqrzjhm",jbrZjhm);
        user.put("sqrzjlx",jbrZjlx);
        user.put("contactsEmail",jbrEmail);
        user.put("sqrsjhm",jbrMobile);
        Map<String, Object> exUser = creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        }else{
            //3.创建机构（银行&经办人信息）
            Map<String, Object> bank = creBank(returnMap);
            if(!(boolean)bank.get(("INS_CREATEFLAG"))){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
            }else{
                //4.文件直传
                Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                        flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.QYQXSQS_MATERNAME);
                if(!(boolean)fileResult.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                }else{
                    //5.签署流程
                    Map<String, Object> result = bdcQlcCreateSignFlowService.
                            createSignFlowOfDyqxSqb(flowVars,returnMap); 
                    if(!(boolean)result.get("SIGN_FLAG")){
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                    }
                }  
            }
        }
       /* //2.创建外部用户（申请人）
        List<Map<String, Object>> sqlList = (List<Map<String, Object>> )returnMap.get("sqrInfoList");
        for (Map<String, Object> sql : sqlList) {
            Map<String, Object> exUser = creExUser(sql); 
            if(!(boolean)exUser.get("USER_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                break;
            }
        }       
        //3.创建机构信息（银行）
        if((boolean)flowVars.get(("SIGN_FLAG"))){          
          Map<String, Object> bank = creBank(returnMap);
          if(!(boolean)bank.get(("INS_CREATEFLAG"))){
              flowVars.put("SIGN_FLAG", false);
              flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
          }else{
              //4.创建外部用户（银行法人）
              Map<String, Object> bankFr = creBankFr(returnMap);
              if(!(boolean)bankFr.get("USER_CREATEFLAG")){
                  flowVars.put("SIGN_FLAG", false);
                  flowVars.put("SIGN_MSG", bankFr.get("SIGN_MSG"));
              }else{
                  //5.文件直传
                  Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                          flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.QYQXSQS_MATERNAME);
                  if(!(boolean)fileResult.get("SIGN_FLAG")){
                      flowVars.put("SIGN_FLAG", false);
                      flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                  }else{
                      //6.签署流程
                      Map<String, Object> result = bdcQlcCreateSignFlowService.
                              createSignFlowOfDyqxSqb(flowVars,returnMap); 
                      if(!(boolean)result.get("SIGN_FLAG")){
                          flowVars.put("SIGN_FLAG", false);
                          flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                      }
                  }  
              }
          }         
        } */
    }
    
    /**
     * 
     * 描述    （国有转移）关于无法提供“预购商品房贷款抵押登记证明书”的说明签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:50
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void wftgsmSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception{
      //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[]{flowVars.get("EFLOW_EXEID"),BdcQlcCreateSignFlowService.WFTGSM_MATERNAME});
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //2.创建外部用户（经办人）
        String  jbrName=StringUtil.getString(flowVars.get("JBR_NAME"));
        String  jbrZjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String  jbrZjlx="IDCard";
        String  jbrMobile=StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String  jbrEmail=StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String,Object> user=new HashMap<>();
        user.put("sqrxm",jbrName);
        user.put("sqrzjhm",jbrZjhm);
        user.put("sqrzjlx",jbrZjlx);
        user.put("contactsEmail",jbrEmail);
        user.put("sqrsjhm",jbrMobile);
        Map<String, Object> exUser = creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        }else{
            //3.创建机构信息（银行）
            Map<String, Object> bank = creBank(returnMap);
            if(!(boolean)bank.get(("INS_CREATEFLAG"))){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
            }else{
                // 4.文件直传
                Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                        flowVars.get("EFLOW_EXEID").toString(), BdcQlcCreateSignFlowService.WFTGSM_MATERNAME);
                if (!(boolean) fileResult.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                } else {
                    // 6.签署流程
                    Map<String, Object> result = bdcQlcCreateSignFlowService.createSignFlowOfWftgsmSqb(flowVars,
                            returnMap);
                    if (!(boolean) result.get("SIGN_FLAG")) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                    }
                } 
            }
        }
       /* //2.创建外部用户（申请人）
        List<Map<String, Object>> sqlList = (List<Map<String, Object>> )returnMap.get("sqrInfoList");
        for (Map<String, Object> sql : sqlList) {
            Map<String, Object> exUser = creExUser(sql); 
            if(!(boolean)exUser.get("USER_CREATEFLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                break;
            }
        }       
        //3.创建机构信息（银行）
        if((boolean)flowVars.get(("SIGN_FLAG"))){          
          Map<String, Object> bank = creBank(returnMap);
          if(!(boolean)bank.get(("INS_CREATEFLAG"))){
              flowVars.put("SIGN_FLAG", false);
              flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
          }else{
              //4.创建外部用户（银行法人）
              Map<String, Object> bankFr = creBankFr(returnMap);
              if(!(boolean)bankFr.get("USER_CREATEFLAG")){
                  flowVars.put("SIGN_FLAG", false);
                  flowVars.put("SIGN_MSG", bankFr.get("SIGN_MSG"));
              }else{
                  //5.文件直传
                  Map<String, Object> fileResult = getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                          flowVars.get("EFLOW_EXEID").toString(),BdcQlcCreateSignFlowService.WFTGSM_MATERNAME);
                  if(!(boolean)fileResult.get("SIGN_FLAG")){
                      flowVars.put("SIGN_FLAG", false);
                      flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                  }else{
                      //6.签署流程
                      Map<String, Object> result = bdcQlcCreateSignFlowService.
                              createSignFlowOfWftgsmSqb(flowVars,returnMap);
                      if(!(boolean)result.get("SIGN_FLAG")){
                          flowVars.put("SIGN_FLAG", false);
                          flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                      }
                  }  
              }
          }         
        }        */
    }
    
    /** 
     * 描述    创建外部用户
     * @author Allin Lin
     * @created 2020年8月17日 下午5:09:14
     * @param variables
     * @return
     * @throws Exception 
     */
    public Map<String, Object> creExUser(Map<String, Object> variables) throws Exception{
        Map<String, Object> account = new HashMap<String, Object>();//返回用户信息
        if(variables.get("sqrzjhm")!=null && !"".equals(variables.get("sqrzjhm").toString())){
            String licenseNumber = variables.get("sqrzjhm").toString();//用户证件号码
            //证件号码具有唯一性
            Map<String, Object> exUser = this.getByJdbc("T_BDCQLC_SIGNEXUSER", 
                    new String[]{"USRE_NUMBER"}, new Object[]{licenseNumber});
            if(exUser!=null){
                account.put("USRE_NUMBER", exUser.get("USRE_NUMBER"));//证件号码
                account.put("ACCOUNTID", exUser.get("ACCOUNTID"));//用户在天印签章系统的ID
                account.put("UNIQUEID", exUser.get("UNIQUEID"));//用户在天印系统的唯一标识
                account.put("USER_CREATEFLAG", true);
            }else{
                Map<String, Object> user = new LinkedHashMap<String, Object>();      
                user.put("contactsMobile",variables.get("sqrsjhm") );//联系人手机号
                user.put("licenseNumber",variables.get("sqrzjhm"));//证件号码
                user.put("licenseType",variables.get("sqrzjlx") );//证件类型
                user.put("name",variables.get("sqrxm") );//用户名称
                user.put("contactsEmail",variables.get("contactsEmail"));//联系人邮箱
                user.put("cardNo",variables.get("cardNo"));//银行卡号
                user.put("loginEmail", variables.get("loginEmail"));//登录邮箱
                user.put("loginMobile",variables.get("sqrsjhm"));//登录手机号              
                user.put("uniqueId",variables.get("uniqueId")); //用户唯一标识
                Map<String, Object> result = BdcQlcSignUtil.creExUserOrInstitutions(user, "exUserUrl");            
                if(result.get("data")!=null){//创建成功
                    Map<String, Object> data = (Map)JSON.parse(result.get("data").toString());
                    //保存至外部用户表中
                    Map<String, Object> exAccount = new HashMap<String, Object>();
                    exAccount.put("USRE_NUMBER", variables.get("sqrzjhm"));
                    exAccount.put("USER_IDTYPE", variables.get("sqrzjlx"));
                    exAccount.put("USER_NAME", variables.get("sqrxm"));
                    exAccount.put("USRE_MOBILE", variables.get("sqrsjhm"));
                    exAccount.put("USER_EMAIL", variables.get("contactsEmail"));
                    exAccount.put("USER_LOGINEMAIL", variables.get("loginEmail"));
                    exAccount.put("USER_LOGINMOBILE", variables.get("sqrsjhm"));
                    exAccount.put("USER_CARDNO", variables.get("cardNo"));                   
                    exAccount.put("ACCOUNTID", data.get("accountId"));
                    exAccount.put("UNIQUEID",data.get("uniqueId"));
                    this.saveOrUpdate(exAccount, "T_BDCQLC_SIGNEXUSER", UUIDGenerator.getUUID());
                    log.info("证件号码："+licenseNumber+":创建外部用户成功！");                                      
                    account = exAccount;
                    account.put("USER_CREATEFLAG", true);
                }else{//创建失败
                    account.put("USER_CREATEFLAG", false);  
                    account.put("SIGN_MSG", "证件号码:"+licenseNumber+"-签章错误原因："+result.get("msg"));
                    log.error("证件号码:"+licenseNumber+"调用天印创建外部用户接口失败：错误原因："+result.toString());
                }
            } 
        }else{
            account.put("USER_CREATEFLAG", false);  
            account.put("SIGN_MSG", "申请人证件号码缺失,请确认！"); 
            log.error("创建天印签章-外部用户：未传入用户证件号码！");
        }      
        return account;
    }
    
    /** 
     * 描述    创建外部机构
     * @author Allin Lin
     * @created 2020年8月17日 下午5:09:14
     * @param variables
     * @return
     */
    public Map<String, Object> creExInstitutions(Map<String, Object> variables)throws Exception{
        Map<String, Object> institution = new HashMap<String, Object>();//返回机构信息
        //1.创建机构默认经办人信息
        Map<String, Object> agent = creExUser(variables);
        Map<String, Object> organize = new LinkedHashMap<String, Object>();
        Map<String, Object> exOrganize = new HashMap<String, Object>();
        if((boolean)agent.get(("USER_CREATEFLAG"))){
            organize.put("agentAccountId",agent.get("ACCOUNTID") );//默认经办人id
            organize.put("agentUniqueId",agent.get("UNIQUEID"));//默认经办人唯一标识 
            exOrganize.put("AGENT_ACCOUNTID",agent.get("ACCOUNTID"));
            exOrganize.put("AGENT_UNIQUEID",agent.get("UNIQUEID"));
            //2.创建机构信息,判断机构是否已创建（证照号码唯一性）
            if(variables.get("licenseNumber")!=null && !"".equals(variables.get("licenseNumber").toString())){
                String licenseNumber = variables.get("licenseNumber").toString();//机构证照号码
                //证照号码具有唯一性
                Map<String, Object> exInstitution = this.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION", 
                        new String[]{"LICENSE_NUMBER"}, new Object[]{licenseNumber});
                if(exInstitution!=null){
                    institution.put("AGENT_ACCOUNTID", exInstitution.get("AGENT_ACCOUNTID"));//默认经办人ID
                    institution.put("LICENSE_NUMBER", exInstitution.get("LICENSE_NUMBER"));//证照号码
                    institution.put("ORGANIZEID", exInstitution.get("ORGANIZEID"));//机构ID（天印签章系统）
                    institution.put("ORGANIZENO", exInstitution.get("ORGANIZENO"));//机构唯一标识（天印签章系统）
                    institution.put("INS_CREATEFLAG", true);
                }else{
                    organize.put("licenseNumber",variables.get("licenseNumber")); //证照号码       
                    organize.put("licenseType",variables.get("licenseType"));//证照类型
                    organize.put("organizeName",variables.get("organizeName") );//机构名称
                    organize.put("cardNo",variables.get("cardNo"));//银行卡号
                    organize.put("email",variables.get("email"));//当前机构联系邮箱
                    organize.put("legalLicenseNumber",variables.get("legalLicenseNumber"));//法定代表人证件号码
                    organize.put("legalLicenseType",variables.get("legalLicenseType"));//法定代表人证件类型
                    organize.put("legalMobile",variables.get("legalMobile"));//法定代表人手机号
                    organize.put("legalName",variables.get("legalName") );//法定代表人姓名               
                    organize.put("organizeNo",variables.get("organizeNo"));//机构唯一标识 
                    Map<String, Object> result = BdcQlcSignUtil.creExUserOrInstitutions(organize,
                            "exInstitutionUrl");            
                    if(result.get("data")!=null){//创建成功
                        Map<String, Object> data = (Map)JSON.parse(result.get("data").toString());
                        //保存至外部机构表中
                        exOrganize.put("LICENSE_NUMBER",variables.get("licenseNumber")); //证照号码       
                        exOrganize.put("LICENSE_TYPE",variables.get("licenseType"));//证照类型
                        exOrganize.put("ORGANIZE_NAME",variables.get("organizeName") );//机构名称
                        exOrganize.put("CARDNO",variables.get("cardNo"));//银行卡号
                        exOrganize.put("EMAIL",variables.get("email"));//当前机构联系邮箱
                        exOrganize.put("LEGAL_LICENSENUMBER",variables.get("legalLicenseNumber"));//法定代表人证件号码
                        exOrganize.put("LEGAL_LICENSETYPE",variables.get("legalLicenseType"));//法定代表人证件类型
                        exOrganize.put("LEGAL_MOBILE",variables.get("legalMobile"));//法定代表人手机号
                        exOrganize.put("LICENSE_TYPE",variables.get("legalName") );//法定代表人姓名                   
                        exOrganize.put("ORGANIZENO",data.get("organizeNo"));//机构唯一标识 
                        exOrganize.put("ORGANIZEID",data.get("organizeId"));//机构ID
                        this.saveOrUpdate(exOrganize, "T_BDCQLC_SIGNEXINSTITUTION", UUIDGenerator.getUUID());
                        log.info("证照号码："+licenseNumber+":创建外部机构成功！");
                        institution = exOrganize;
                        institution.put("INS_CREATEFLAG", true);
                    }else{//创建失败
                        institution.put("INS_CREATEFLAG", false);
                        institution.put("SIGN_MSG", "证照号码:"+licenseNumber+"-签章失败：错误原因："+result.get("msg"));
                        log.error("证照号码:"+licenseNumber+"调用天印创建外部机构接口失败：错误原因："+result.toString());
                    }
                } 
            }else{
                institution.put("INS_CREATEFLAG", false);  
                institution.put("SIGN_MSG", "机构证照号码缺失！"); 
                log.error("创建天印签章-外部机构：未传入用户证照号码！");
            }      
        }else{
            //创建默认经办人失败
            institution.put("INS_CREATEFLAG", false);  
            institution.put("SIGN_MSG", agent.get("SIGN_MSG")); 
            log.error("创建天印签章-外部机构：创建该机构对应的经办人失败！");
        }
        
        return institution;
    }
    
    
    
    /** 
     * 描述    预购商品房预告登记-创建开发商信息（义务人）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creDeveloper(Map<String, Object> returnMap)throws Exception{
        Map<String, Object> developer = new HashMap<String, Object>();
        //开发商信息
        developer.put("licenseNumber", returnMap.get("ZRFZJHM"));//开发商证照号码
        developer.put("organizeName", returnMap.get("ZRFXM"));//开发商名称
        developer.put("licenseType", returnMap.get("ZRFZJLB_C"));//开发商证照类型
        //开发商经办信息
        developer.put("sqrsjhm", returnMap.get("DLRSJHM"));//手机号码
        developer.put("sqrzjhm", returnMap.get("DLRZJHM"));//证件号码
        developer.put("sqrzjlx", returnMap.get("DLRZJLB_C"));//证件类型
        developer.put("sqrxm", returnMap.get("DLRXM"));//姓名               
        developer = creExInstitutions(developer);
        return developer;   
    }
    
    
    /** 
     * 描述    预购商品房抵押预告登记-创建开发商信息（权利人）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creYdDeveloper(Map<String, Object> returnMap)throws Exception{
        Map<String, Object> developer = new HashMap<String, Object>();
        //开发商信息
        developer.put("licenseNumber", returnMap.get("QLR_ZJNO"));//开发商证照号码
        developer.put("organizeName", returnMap.get("QLR_MC"));//开发商名称
        developer.put("licenseType", returnMap.get("QLR_ZJLX_C"));//开发商证照类型
        //开发商经办信息
        developer.put("sqrsjhm", returnMap.get("DLR_SJHM"));//手机号码
        developer.put("sqrzjhm", returnMap.get("DLR_ZJNO"));//证件号码
        developer.put("sqrzjlx", returnMap.get("DLR_ZJLX_C"));//证件类型
        developer.put("sqrxm", returnMap.get("DLR_MC"));//姓名               
        developer = creExInstitutions(developer);
        return developer;   
    }
    
    
    
    
    
    
    /** 
     * 描述    创建银行信息（外部机构）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creBank(Map<String, Object> returnMap)throws Exception{
        Map<String, Object> bank = new HashMap<String, Object>();
        //银行信息
        bank.put("licenseNumber", returnMap.get("yhdm"));//银行证照号码
        bank.put("organizeName", returnMap.get("yhmc"));//银行名称
        bank.put("licenseType", returnMap.get("yhzzlx"));//银行证照类型
        //银行经办信息
        bank.put("sqrsjhm", returnMap.get("yhlxdh"));//手机号码
        bank.put("sqrzjhm", returnMap.get("yhlxzjhm"));//证件号码
        bank.put("sqrzjlx", returnMap.get("yhlxzjlx"));//证件类型
        bank.put("sqrxm", returnMap.get("yhlxr"));//姓名               
        bank = creExInstitutions(bank);
        return bank;   
    }
    
    /** 
     * 描述    创建银行法人信息（外部用户）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creBankFr(Map<String, Object> returnMap)throws Exception{
        Map<String, Object> bankFr = new HashMap<String, Object>();
        bankFr.put("sqrsjhm", returnMap.get("yhfrsjh"));//法人联系手机号
        bankFr.put("sqrzjhm", returnMap.get("yhfrzjhm"));//法人证件号码
        bankFr.put("sqrzjlx", returnMap.get("yhfrzjlx"));//法人证件类型
        bankFr.put("sqrxm", returnMap.get("yhfr"));//法人名称
        bankFr = creExUser(bankFr);
        return bankFr;          
    }
    
    /** 
     * 描述    银行经办人信息（外部用户）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creBankJb(Map<String, Object> returnMap)throws Exception{
        Map<String, Object> bankJb = new HashMap<String, Object>();
        bankJb.put("sqrsjhm", returnMap.get("yhlxdh"));//银行经办/联系人手机号
        bankJb.put("sqrzjhm", returnMap.get("yhlxzjhm"));//银行经办/联系人证件号码
        bankJb.put("sqrzjlx", returnMap.get("yhlxzjlx"));//银行经办/联系人证件类型
        bankJb.put("sqrxm", returnMap.get("yhlxr"));//银行经办/联系人名称
        bankJb = creExUser(bankJb);
        return bankJb;
    }
    
    /**
     * 描述     材料替换字符串&生成PDF（模板需表格+【】）
     * @author Allin Lin
     * @created 2020年8月20日 上午12:34:10
     * @param templateFolder  材料模板文件夹
     * @param templatePath  材料模板路径
     * @param materName  材料模板名称（唯一）
     * @param exeId  申报号
     * @param returnMap  业务信息
     */
    public void wordGenPdf(String templateFolder,String templatePath,String materName,
            String exeId,Map<String, Object> returnMap){
        WordRedrawUtil.processNormalTable07(
                "attachFiles//signFileTemplate//"+templateFolder+"//"+templatePath, returnMap);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空   
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
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
            String exeId,Map<String, Object> returnMap,String type){
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//"+templateFolder+"//"+templatePath;
        if(StringUtils.isNotEmpty(type) && "dynamic".equals(type)){
            //含动态表格
            WordRedrawUtil.generateWordForDnamicTable(returnMap, AttachFilePath+path,
                    StringUtil.getString(returnMap.get("bsWtsKey")), 
                    StringUtil.getString(returnMap.get("bsWtsTableKey")));
        }else{
            WordRedrawUtil.generateWord(returnMap,AttachFilePath+path);//不含动态表格
        }
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空   
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
    
    /** 
     * 描述    预购商品房预告-申请表生成PDF(模板动态生成表格)
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ygSqbGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){ 
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//ygspfygdj//"+templatePath;       
        bdcYgspfygdjService.generateSQB(returnMap,AttachFilePath+path);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空       
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
    
    /** 
     * 描述    预购商品房预告-非动态表格通用生成PDF
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ygCommonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){ 
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//ygspfygdj//"+templatePath;       
        bdcYgspfygdjService.generateWord(returnMap,AttachFilePath+path);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空     
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);      
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
    /** 
     * 描述    预购商品房抵押预告登记-申请表生成PDF(模板动态生成表格)
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ydSqbGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//ygspfdyqdj//"+templatePath;       
        bdcYgspfService.generateSQB(returnMap,AttachFilePath+path);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空   
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    } 
 
    
    /** 
     * 描述     预购商品房抵押预告登记-非动态表格通用生成PDF
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void ydCommonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//ygspfdyqdj//"+templatePath;       
        bdcYgspfygdjService.generateWord(returnMap,AttachFilePath+path);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空   
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
    /**
     * 
     * 描述 生成PDF文件的保存路径
     * 
     * @author Roger Li
     * @created 2019年12月23日 下午4:47:18
     * @param fileExt
     * @return
     */
    public static String getSignPdfPath() {
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        String uploadPath = "SignAttach/";
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        String str = "";
        String uuId = UUIDGenerator.getUUID();
        str = uuId + ".pdf";;
        String filename = uploadFullPath + str;
        return filename;
    }
    
    /**
     * 描述 保存签署文档信息
     * 
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param docInfo
     * @param flowId
     */
    public void savaMaterSignInfo(Map<String, Object> docInfo, String flowId ,String status) {
        String docFileKey = (String) docInfo.get("docFileKey");
        String finishFileKey = (String) docInfo.get("finishFileKey");
        String downloadDocUrl = (String) docInfo.get("downloadDocUrl");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("FINISH_FILEKEY", finishFileKey);
        variables.put("DOWNLOAD_DOCURL", downloadDocUrl);
        variables.put("IS_SIGN", status);
        Map<String, Object> signInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",
                new String[] { "SIGN_FLOWID", "FILE_KEY" }, new Object[] { flowId, docFileKey });
        if (signInfo != null) {
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else {
            variables.put("SIGN_FLOWID", flowId);
            variables.put("FILE_KEY", docFileKey);            
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }

    }

    /**
     * 描述 保存签署流程回调信息
     * 
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:15:02
     * @param accountInfo
     * @param flowId
     */
    public void savaSignFlowsInfo(Map<String, Object> accountInfo, String flowId) {
        String accountId = (String) accountInfo.get("ACCOUNTID");
        Map<String, Object> signInfo = this.getByJdbc("T_BDCQLC_SIGNFLOW", new String[] { "SIGN_FLOWID", "ACCOUNTID" },
                new Object[] { flowId, accountId });
        if (signInfo != null) {
            this.saveOrUpdate(accountInfo, "T_BDCQLC_SIGNFLOW", signInfo.get("YW_ID").toString());
        } else {
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            accountInfo.put("CREATE_TIME", createTime);
            this.saveOrUpdate(accountInfo, "T_BDCQLC_SIGNFLOW", UUIDGenerator.getUUID());
        }

    }    
    
    /**
     * 描述    获取文件fileKey信息
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param filePath
     * @param exe_id
     * @param materName
     */
    public Map<String, Object> getFileKey(String filePath, String exe_id ,String materName) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            result = BdcQlcSignUtil.uploadFiles(filePath, "uploadUrl");
        } catch (Exception e) {           
            log.error("未正确请求到天印签章业务文件直传接口,服务异常如下：" + e.getMessage());
        }
        int errCode = Integer.parseInt(result.get("errCode").toString());
        if(errCode==0) {//为0表示成功
            String data = result.get("data") == null ? "": result.get("data").toString();
            Map<String, Object> dataInfo = (Map<String, Object>) JSON.parse(data);
            String fileKey = dataInfo.get("fileKey").toString();
                //保存fileKey信息
                Map<String, Object> variables = new HashMap<String, Object>();
                Map<String, Object> signInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", new String[] { "EXE_ID", "MATER_NAME" },
                        new Object[] { exe_id, materName });
                variables.put("FILE_KEY", fileKey);
                if (signInfo != null) {
                    this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
                } 
                response.put("fileKey", fileKey);
                response.put("SIGN_FLAG", true);                       
        }else {
            response.put("SIGN_FLAG", false); 
            response.put("SIGN_MSG", "未正确请求到天印签章业务文件直传接口,服务异常如下："+result.get("msg"));
            log.error("未正确请求到天印签章业务文件直传接口,服务异常如下：" + result.toString());
        } 
        return response;
    }     
 
    /**
     * 描述 获取完结状态回调数据
     * 
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param resultJson
     */
    public Map<String, Object> getSignFlowFinishMap(JSONObject resultJson) {
        String action = resultJson.getString("action") == null ? "" : resultJson.getString("action");
        String status = resultJson.getString("status") == null ? "" : resultJson.getString("status");
        String flowId = resultJson.getString("flowId") == null ? "" : resultJson.getString("flowId");
        String createTime = resultJson.getString("createTime") == null ? "" : resultJson.getString("createTime");
        String flowType = resultJson.getString("flowType") == null ? "" : resultJson.getString("flowType");
        String resultDescription = resultJson.getString("resultDescription") == null ? ""
                : resultJson.getString("resultDescription");
        String endTime = resultJson.getString("endTime") == null ? "" : resultJson.getString("endTime");
        String signTime = resultJson.getString("signTime") == null ? "" : resultJson.getString("signTime");
        String account = resultJson.getString("accountInfo") == null ? "" : resultJson.getString("accountInfo");
        //获取操作人个人详情信息
        Map<String, Object> accountInfo = (Map<String, Object>) JSON.parse(account);
        Map<String, Object> variables = new HashMap<String, Object>();
        if(accountInfo!=null && accountInfo.size()>0) {
            String accountId = accountInfo.get("accountId") == null ? "" : accountInfo.get("accountId").toString();
            String accountUid = accountInfo.get("accountUid") == null ? "" : accountInfo.get("accountUid").toString();
            String organizeNo = accountInfo.get("organizeNo") == null ? "" : accountInfo.get("organizeNo").toString();
            String authOrgId = accountInfo.get("authOrgId") == null ? "" : accountInfo.get("authOrgId").toString();
            String authOrgName = accountInfo.get("authOrgName") == null ? "" : accountInfo.get("authOrgName").toString();
            String type = accountInfo.get("type") == null ? "" : accountInfo.get("type").toString();
            String name = accountInfo.get("name") == null ? "" : accountInfo.get("name").toString();
            String realnameFlowId = accountInfo.get("realnameFlowId") == null ? "" : accountInfo.get("realnameFlowId").toString();
            String signUrl = accountInfo.get("signUrl") == null ? "" : accountInfo.get("signUrl").toString();   
            variables.put("ACCOUNTID", accountId);
            variables.put("ACCOUNTUID",accountUid);
            variables.put("ORGANIZE_NO", organizeNo);
            variables.put("AUTHORG_ID", authOrgId);
            variables.put("AUTHORG_NAME", authOrgName);
            variables.put("TYPE", type);
            variables.put("SIGN_NAME", name);
            variables.put("REALNAME_FLOWID",realnameFlowId);
            variables.put("SIGN_URL",signUrl);
        }
        variables.put("ACTION", action);
        variables.put("SIGN_FLOWID", flowId);
        variables.put("STATUS", status);
        variables.put("FLOW_TYPE", flowType);
        variables.put("RESDESCRIPTION", resultDescription);
        variables.put("SIGN_TIME", signTime);
        variables.put("BEGINTIME", createTime);
        variables.put("ENDTIME", endTime);

        return variables;
    }     

    /**
     * 描述 获取过程更新状态回调数据
     * 
     * @author Luffy Cai
     * @created 2020年8月18日 下午2:00:02
     * @param resultJson
     */
    public Map<String, Object> getSignFlowUpdateMap(JSONObject resultJson) {
        String action = resultJson.getString("action") == null ? "" : resultJson.getString("action");
        String createTime = resultJson.getString("createTime") == null ? "" : resultJson.getString("createTime");
        String flowId = resultJson.getString("flowId") == null ? "" : resultJson.getString("flowId");
        String flowType = resultJson.getString("flowType") == null ? "" : resultJson.getString("flowType");
        String resultDescription = resultJson.getString("resultDescription") == null ? ""
                : resultJson.getString("resultDescription");
        String signTime = resultJson.getString("signTime") == null ? "" : resultJson.getString("signTime");
        String status = resultJson.getString("status") == null ? "" : resultJson.getString("status");
        //获取操作人个人详情信息
        String account = resultJson.getString("accountInfo") == null ? "" : resultJson.getString("accountInfo");
        Map<String, Object> accountInfo = (Map<String, Object>) JSON.parse(account);
        String accountId = accountInfo.get("accountId") == null ? "" : accountInfo.get("accountId").toString();
        String accountUid = accountInfo.get("accountUid") == null ? "" : accountInfo.get("accountUid").toString();
        String type = accountInfo.get("type") == null ? "" : accountInfo.get("type").toString();
        String name = accountInfo.get("name") == null ? "" : accountInfo.get("name").toString();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("ACTION", action);
        variables.put("SIGN_FLOWID", flowId);
        variables.put("STATUS", status);
        variables.put("FLOW_TYPE", flowType);
        variables.put("RESDESCRIPTION", resultDescription);
        variables.put("SIGN_TIME", signTime);
        variables.put("BEGINTIME", createTime);
        variables.put("ACCOUNTID", accountId);
        variables.put("ACCOUNTUID",accountUid);
        variables.put("TYPE", type);
        variables.put("SIGN_NAME", name);
        return variables;
    }    
    
    /**
     * 
     * @Description 根据申报号获取签署状态
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param exeId
     * @return String
     */
    public String getSignStatusByExeId(String exeId) {
        String signStatus = "";
        if (StringUtils.isNotEmpty(exeId)) {
            List<Object> params = new ArrayList<Object>();
            params.add(exeId);
            StringBuffer sql = new StringBuffer();
            sql.append(" select T.* from T_BDCQLC_MATERSIGNINFO T");
            sql.append(" where 1=1  and T.EXE_ID=? ");
            List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
            StringBuffer signStringBuffer = new StringBuffer();
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    if (StringUtils.isNotEmpty(signStringBuffer.toString())) {
                        signStringBuffer.append("," ).append(map.get("IS_SIGN"));
                    } else {
                        signStringBuffer.append(map.get("IS_SIGN"));
                    }
                }
            }
            if (StringUtils.isNotEmpty(signStringBuffer.toString())) {
                String array1[];// 获取到的sign数组
                array1 = signStringBuffer.toString().split(",");
                int array[] = new int[array1.length];
                for (int i = 0; i < array1.length; i++) {
                    array[i] = Integer.parseInt(array1[i]);
                }
                int array2[] = { 0, 1, 2 };//0：未通知 ,1：已通知 , 2：已签署
                //获取0,1,2以外的签署状态
                String notinstr = compareArray(array, array2);
                if (StringUtils.isNotEmpty(notinstr)) {// 签署状态异常
                    String array3[];// 获取到的sign数组
                    array3 = notinstr.split(",");
                    for (int i = 0; i < array3.length; i++) {
                        signStatus = array3[i];
                    }
                } else {
                    boolean sameFlag = true;
                    for (int j = 0; j < array1.length; j++) {
                        if (!array1[j].equals("2")) {
                            sameFlag = false;
                            signStatus = array1[j];
                            break;
                        }
                    }
                    if (sameFlag) {// 已签署
                        signStatus = "2";
                    }
                }
            }
        }
        return signStatus;
    }   
 
    
    /**
     * 
     * @Description 比较数组
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param array1
     * @param array2
     * @return boolean
     */
    public static String compareArray(int array1[], int array2[]) {
        StringBuffer inStringBuffer=new StringBuffer();// 用于记录符合条件数组元素
        StringBuffer notinStringBuffer=new StringBuffer();// 用于记录不符合条件数组元素
        for (int i = 0; i < array1.length; i++) {
            int array1elem = array1[i];
            boolean flag = false;
            for (int j = 0; j < array2.length; j++) {
                if (array1elem == array2[j]) {
                    flag = true;
                    break;
                }
            }
            if (true == flag) {
                inStringBuffer.append(" ").append(array1elem);
            } else {
                if (StringUtils.isNotEmpty(notinStringBuffer.toString())) {
                    notinStringBuffer.append(",").append(array1elem);
                } else {
                    notinStringBuffer.append(array1elem);
                }
            }
        }
        if (StringUtils.isNotEmpty(notinStringBuffer.toString())) {
            return notinStringBuffer.toString();
        } else {
            return null;
        }
    }
    
    /**
     * 
     * @Description 根据申报号获取签章材料列表
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param exeId
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findSignMaterListByExeId(String exeId) {
        if(StringUtils.isNotEmpty(exeId)) {
            List<Object> params = new ArrayList<Object>();
            params.add(exeId);
            StringBuffer sql = new StringBuffer();
            sql.append(" select T.* from T_BDCQLC_MATERSIGNINFO T");
            sql.append(" where 1=1  and T.EXE_ID=? ");
            List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
            if(list!=null && list.size()>0) {
                for (Map<String, Object> map : list) {
                    String SIGN_FLOWID = map.get("SIGN_FLOWID")==null?"":map.get("SIGN_FLOWID").toString();
                    map.put("SIGN_FLOWID", SIGN_FLOWID);
                    String IS_SIGN = map.get("IS_SIGN")==null?"":map.get("IS_SIGN").toString();
                    if(IS_SIGN.equals("0")) {
                        IS_SIGN="未通知";
                    }else if(IS_SIGN.equals("1")) {
                        IS_SIGN="已通知";
                    }else if(IS_SIGN.equals("2")) {
                        IS_SIGN="已签署";
                    }else if(IS_SIGN.equals("5")) {
                        IS_SIGN="过期作废";
                    }else if(IS_SIGN.equals("7")) {
                        IS_SIGN="拒签";
                    }else {
                        IS_SIGN="签署失败";
                    }
                    map.put("IS_SIGN", IS_SIGN);
                }
            }
            return list;
        }else {
            return null;
        }
    }

    /**
     * 
     * @Description 根据签署流程id获取签章明细列表
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param flowId
     * @return List<Map<String,Object>>
     */
    public List<Map<String,Object>> findSignFlowListByflowId(String flowId){
        List<Object> params = new ArrayList<Object>();
        params.add(flowId);
        StringBuffer sql = new StringBuffer();
        sql.append(" select T.* from T_BDCQLC_SIGNFLOW T");
        sql.append(" where 1=1  and T.SIGN_FLOWID=? ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    
    /**
     * 
     * @Description 抵押权首次登记（转本）前置事件（材料生成及签章）
     * @author Luffy Cai
     * @date 2020年8月25日
     * @param flowVars
     * @return
     * @throws Exception Map<String,Object>
     */
    public Map<String, Object> dyqscdjGenAndSign(Map<String, Object> flowVars) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));// 申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));// 受理方式（1：网上申请 触发签章）
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));// 退回不执行
        String eflowIstempsave = StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        String curStepName=StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));//当前操作环节
        String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//是否全程网办方式（0：全程网办 1：智能审批 2：首登全程网办个性化流程）
        String pttSqfs =  StringUtil.getString(flowVars.get("PTT_SQFS"));//平潭通申请方式 1智能审批 2全程网办
        if (StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                && StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
            String takecardType = flowVars.get("TAKECARD_TYPE").toString();//业务类型

            if (("2".equals(takecardType) && StringUtils.isNotEmpty(curStepName)
                    && curStepName.equals("开始") && "1".equals(isqcwb))
                    || ("2".equals(takecardType) && StringUtils.isNotEmpty(curStepName)
                            && curStepName.equals("网上预审") && "1".equals(pttSqfs)) ) {//抵押权首次登记转本（智能审批）门户申请&平潭通申请
                /* 初始化材料字段 */
                bdcSpbConfig.bdcInitSpbVariables(returnMap);
                /* 设置材料业务表数据 */
                bdcDyqscdjService.initGenValue(returnMap, flowVars);
                /* 材料替换字符串&生成PDF文件&签章流程 */
                wordGenPdf(BdcQlcMaterGenAndSignService.DYQ_KEY, BdcQlcMaterGenAndSignService.BANK_KEY,
                        BdcQlcCreateSignFlowService.BANKSQS_MATERNAME, exeId, returnMap);// 银行申请表生成PDF
                bdcDyqscdjService.bankSign(flowVars, returnMap);// 银行申请表签章
            } else if ("1".equals(takecardType)) {//首登（全程网办个性化定制业务）
                if( ("2".equals(isqcwb) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("开始"))
                        || ("2".equals(pttSqfs) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审"))){
                    /* 初始化材料字段 */
                    bdcSpbConfig.bdcInitSpbVariables(returnMap);
                    /* 设置材料业务表数据 */
                    bdcDyqscdjService.initGenValue(returnMap, flowVars);
                    /* 材料替换字符串&生成PDF文件&签章流程 */
                    wordGenPdf(BdcQlcMaterGenAndSignService.DYQ_KEY, BdcQlcMaterGenAndSignService.BANK_KEY,
                            BdcQlcCreateSignFlowService.BANKSQS_MATERNAME, exeId, returnMap);// 银行申请表生成PDF
                    bdcDyqscdjService.bankSign(flowVars, returnMap);// 银行申请表签章 
                }
                
            }

        }
        return flowVars;
    }  
    
    /**
     * 描述   签署流程作废（指定申报号）
     * @author Allin Lin
     * @created 2020年10月30日 下午10:17:17
     * @param exeId
     * @return
     */
    public Boolean cancelSignFlow(String exeId){
        Boolean flag = false;
        List<Map<String, Object>> cancelList = new ArrayList<Map<String,Object>>();//签署作废流程结果集合
        if(StringUtils.isNotEmpty(exeId)){
            //根据申报号获取签署流程信息集合
            List<Map<String, Object>> signFlowList = new ArrayList<Map<String,Object>>();
            signFlowList = this.getAllByJdbc("T_BDCQLC_MATERSIGNINFO", new String[]{"EXE_ID"}, 
                    new Object[]{exeId});
            if(signFlowList!=null&&signFlowList.size() > 0){
                for (Map<String, Object> signFlow : signFlowList) {
                    Map<String, Object> cancelFlow = new HashMap<String, Object>();
                    //作废签署流程id
                    cancelFlow.put("signFlowId", Integer.valueOf(StringUtil.getString(signFlow.get("SIGN_FLOWID"))));
                    try {
                        Map<String, Object> result = BdcQlcSignUtil.creExUserOrInstitutions(cancelFlow,"cancelFlowUrl");
                        Map<String, Object> flow = new HashMap<String, Object>();
                        if(Integer.valueOf(StringUtil.getString(result.get("errCode")))==0){                         
                            //作废成功
                            flow.put("CANCEL_FLAG", true);
                            flow.put("CANCEL_STATUS", "1");
                            this.saveOrUpdate(flow, "T_BDCQLC_MATERSIGNINFO",
                                    StringUtil.getString(signFlow.get("YW_ID")));
                            cancelList.add(flow);
                            log.info("不动产业务申报号："+exeId+":签署流程ID-"+
                                    StringUtil.getString(signFlow.get("SIGN_FLOWID"))+"作废成功");
                        }else{//作废失败
                            flow.put("CANCEL_FLAG", false);
                            flow.put("CANCEL_STATUS", "2");  
                            flow.put("CANCEL_FAILREASON", StringUtil.getString(result.get("msg"))); 
                            cancelList.add(flow);
                            this.saveOrUpdate(flow, "T_BDCQLC_MATERSIGNINFO", 
                                    StringUtil.getString(signFlow.get("YW_ID")));
                            log.info("不动产业务申报号："+exeId+":签署流程ID-"+
                                    StringUtil.getString(signFlow.get("SIGN_FLOWID"))+"作废失败，原因："+result.get("msg"));
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }  
                }
            }
        }else{
            flag = false;
            log.error("不动产智能审批业务签署流程作废失败，原因：传入申报号为空");
        }
        //判断是否存在作废成功的文件
        if(cancelList!=null&&cancelList.size()>0){
            for (Map<String, Object> cancel : cancelList) {
                if(true==(Boolean)cancel.get("CANCEL_FLAG")){
                    flag = true;
                    break;
                }
            }
        }
        //申报号存在文件作废成功，则更新签署状态以及下载文档清空
        if(flag){
            StringBuffer sql=new StringBuffer();
            sql.append(" update T_BDCQLC_MATERSIGNINFO set is_sign='0' , DOWNLOAD_DOCURL = '' ");
            sql.append(" where exe_id=? ");
            dao.executeSql(sql.toString(),new Object[]{exeId});
        }
        return flag;      
    }    
}
