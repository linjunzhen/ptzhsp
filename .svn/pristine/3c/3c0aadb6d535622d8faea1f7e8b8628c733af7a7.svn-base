/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import icepdf.i;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WordToPdfUtil;
import net.evecom.platform.bdc.dao.BdcGyjsydsyqscdjDao;
import net.evecom.platform.bdc.service.BdcDyqzxdjService;
import net.evecom.platform.bdc.service.BdcGyjsydsyqscdjService;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述  国有建设用地使用权首次登记service
 * @author Allin Lin
 * @created 2020年11月11日 上午11:20:35
 */
@Service("bdcGyjsydsyqscdjService")
public class BdcGyjsydsyqscdjServiceImpl extends BaseServiceImpl implements BdcGyjsydsyqscdjService{
    /**
     * log
     */
    private static Log log=LogFactory.getLog(BdcGyjsydsyqscdjServiceImpl.class);
    
    /**
     * 所引入Dao
     */
    @Resource
    private BdcGyjsydsyqscdjDao dao;
    
    /**
     * 所引入的service
     */
    @Resource
    private BdcGyjsydsyqscdjService bdcGyjsydsyqscdjService;
    
    /**
     * 所引入的service
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
       
    /**
     * 所引入的service
     */
    @Resource
    private BdcQlcCreateSignFlowService bdcQlcCreateSignFlowService;
       
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入service
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;
    
    
    /**
     * 描述 覆盖实体dao方法
     * @author Allin Lin
     * @created 2019年3月5日 上午11:42:08
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述     国有建设用地使用权首次登记前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年10月14日 下午2:44:26
     * @param flowVars
     * @return
     * @throws Exception
     */
    public Map<String, Object> gyjsscdjGenAndSign(Map<String,Object> flowVars)throws Exception{
        Map<String, Object> returnMap ;
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 ）
        String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//前台申报方式 （1：智能审批 0：全程网办）       
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行(退回为:不空)
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作(暂存为：1)
        String curStepName=StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));//当前环节
        if(StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                &&StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)
                && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审")){ 
            if("0".equals(isqcwb)){
              //1.材料业务数据回填
                returnMap = initGenValue(flowVars);
                //2.权利人：个人办理无需签委托书；企业办理申请书、委托书都签
                boolean isGr = false;
                List<Map<String, Object>> qlrInfoList =(List<Map<String, Object>>) returnMap.get("qlrInfoList");
                for (Map<String, Object> qlr : qlrInfoList) {
                    if("1".equals(StringUtil.getString(qlr.get("qlxz")))){//权利性质-个人
                        isGr = true;
                        break;
                    }
                }
                returnMap.put("isGrFlag", isGr);//权利人性质标志位
                //System.out.println("权利性质是否为个人："+isGr);
                if(!isGr){//权利人为企业
                    commonGenPdf(BdcGyjsydsyqscdjService.DJSQS_KEY,
                            BdcGyjsydsyqscdjService.DJSQS_MATERNAME,exeId,returnMap); //不动产登记申请书-生成PDF 
                    commonGenPdf(BdcGyjsydsyqscdjService.WTS_KEY,
                            BdcGyjsydsyqscdjService.WTS_MATERNAME,exeId,returnMap); //授权委托书-生成PDF 
                    djSqsSign(flowVars,returnMap);//不动产登记申请书-签章 
                    if((boolean)flowVars.get("SIGN_FLAG")){
                        wtsSign(flowVars,returnMap);//授权委托书-签章
                    }
                }else{//权利人为个人
                    commonGenPdf(BdcGyjsydsyqscdjService.DJSQS_KEY,
                            BdcGyjsydsyqscdjService.DJSQS_MATERNAME,exeId,returnMap); //不动产登记申请书-生成PDF 
                    djSqsSign(flowVars,returnMap);//不动产登记申请书-签章 
                } 
            }
        }
        return flowVars;
    }
    
    /**
     * 描述  初始化签章材料业务数据
     * @author Allin Lin
     * @created 2020年11月11日 上午11:51:50
     * @param flowVars
     * @return
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars){
        Map<String,Object> returnMap = new HashMap<String, Object>();
        //申请书
        returnMap.put("EXEID", StringUtil.getString(flowVars.get("EFLOW_EXEID")));
        Map flowExe = (Map)this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { flowVars.get("EFLOW_EXEID") });
        if(flowExe == null) {
            flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                    new Object[] { flowVars.get("EFLOW_EXEID")});
        }
        returnMap.put("SQRQ", DateTimeUtil.getChinaDate(StringUtil.getValue(flowExe, "CREATE_TIME")));
        //收件人默认-平潭综合实验区行政服务中心
        //returnMap.put("SJR", StringUtil.getString(flowExe.get("CREATOR_NAME")));
        returnMap.put("SJR", "平潭综合实验区行政服务中心");
        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(flowVars, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            StringBuffer qlrmc = new StringBuffer();
            StringBuffer qlrzjzl = new StringBuffer();
            StringBuffer qlrzjhm = new StringBuffer();
            StringBuffer qlrdz = new StringBuffer();
            StringBuffer frmc = new StringBuffer();
            StringBuffer frzjhm = new StringBuffer();
            StringBuffer frdh = new StringBuffer();
            StringBuffer dlrmc = new StringBuffer();
            StringBuffer dlrzjzl = new StringBuffer();
            StringBuffer dlrzjhm = new StringBuffer();
            StringBuffer dlrdh = new StringBuffer();
            List<Map<String,Object>> qlrInfoList = new ArrayList<>();
            
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrmc.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /*证件类型代码转换*/
                String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                qlrzjzl.append(documentType).append(",");
                qlrzjhm.append(StringUtil.getValue(piMap, "POWERPEOPLEIDNUM")).append(",");
                qlrdz.append(StringUtil.getValue(piMap, "POWERPEOPLEADDR")).append(",");
                frmc.append(StringUtil.getValue(piMap, "POWLEGALNAME")).append(",");
                frzjhm.append(StringUtil.getValue(piMap, "POWLEGALIDNUM")).append(",");
                frdh.append(StringUtil.getValue(piMap, "POWLEGALTEL")).append(",");
                dlrmc.append(StringUtil.getValue(piMap, "POWAGENTNAME")).append(",");
                String poweragentidtype = StringUtil.getValue(piMap, "POWAGENTIDTYPE");
                String documentType1 = dictionaryService.getDicNames("DocumentType", poweragentidtype);
                dlrzjzl.append(documentType1).append(",");
                dlrzjhm.append(StringUtil.getValue(piMap, "POWAGENTIDNUM")).append(",");
                dlrdh.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");
                //权利人
                Map<String,Object> qlrInfoMap = new HashMap<>();
                qlrInfoMap.put("sqrxm", StringUtil.getValue(piMap,"POWERPEOPLENAME"));
                if (powerpeopleidtype.equals("SF")) {
                    qlrInfoMap.put("sqrzjlx", "IDCard");
                } else if (powerpeopleidtype.equals("HZ")) {
                    qlrInfoMap.put("sqrzjlx", "Passport");
                } else if (powerpeopleidtype.equals("GATX")) {
                    qlrInfoMap.put("sqrzjlx", "HMPass");
                } else if(powerpeopleidtype.equals("TWTX")){//台湾来往大陆通行证即台胞证
                    qlrInfoMap.put("sqrzjlx", "MTP");
                } else if(powerpeopleidtype.equals("YYZZ")){
                    qlrInfoMap.put("sqrzjlx", "SOCNO");//营业执照（社会信用代码）
                } else if(powerpeopleidtype.equals("JGDM")){
                    qlrInfoMap.put("sqrzjlx", "ORANO");//机构代码证
                }else {
                    qlrInfoMap.put("sqrzjlx", "Other");
                }
                qlrInfoMap.put("qlxz", StringUtil.getValue(piMap,"POWERPEOPLENATURE"));
                qlrInfoMap.put("sqrzjhm", StringUtil.getValue(piMap,"POWERPEOPLEIDNUM"));
                qlrInfoMap.put("sqrsjhm", StringUtil.getValue(piMap,"POWERPEOPLEMOBILE"));
                //权利人-代理人
                qlrInfoMap.put("dlrxm", StringUtil.getValue(piMap,"POWAGENTNAME"));
                String powagentidtype = StringUtil.getValue(piMap, "POWAGENTIDTYPE");
                if (powagentidtype.equals("SF")) {
                    qlrInfoMap.put("dlrzjlx", "IDCard");
                } else if (powagentidtype.equals("HZ")) {
                    qlrInfoMap.put("dlrzjlx", "Passport");
                } else if (powagentidtype.equals("GATX")) {
                    qlrInfoMap.put("dlrzjlx", "HMPass");
                } else if(powagentidtype.equals("TWTX")){//台湾来往大陆通行证即台胞证
                    qlrInfoMap.put("dlrzjlx", "MTP");
                } else if(powagentidtype.equals("YYZZ")){
                    qlrInfoMap.put("dlrzjlx", "SOCNO");//营业执照（社会信用代码）
                } else if(powagentidtype.equals("JGDM")){
                    qlrInfoMap.put("dlrzjlx", "ORANO");//机构代码证
                }else {
                    qlrInfoMap.put("dlrzjlx", "Other");
                }
                qlrInfoMap.put("dlrzjhm", StringUtil.getValue(piMap,"POWAGENTIDNUM"));
                qlrInfoMap.put("dlrsjhm", StringUtil.getValue(piMap,"POWAGENTTEL"));
                qlrInfoList.add(qlrInfoMap);
            }
            returnMap.put("qlrInfoList", qlrInfoList);//权利人信息（用于签章）
            returnMap.put("QLRMC", bdcSpbConfig.bdcStringOutFormate(qlrmc));
            returnMap.put("QLRZJLB", bdcSpbConfig.bdcStringOutFormate(qlrzjzl));
            returnMap.put("QLRZJHM", bdcSpbConfig.bdcStringOutFormate(qlrzjhm));
            returnMap.put("QLRDZ", bdcSpbConfig.bdcStringOutFormate(qlrdz));
            returnMap.put("FRMC", bdcSpbConfig.bdcStringOutFormate(frmc));
            returnMap.put("FRZJHM", bdcSpbConfig.bdcStringOutFormate(frzjhm));
            returnMap.put("FRDH", bdcSpbConfig.bdcStringOutFormate(frdh));
            returnMap.put("DLRMC", bdcSpbConfig.bdcStringOutFormate(dlrmc));
            returnMap.put("DLRZJLB", bdcSpbConfig.bdcStringOutFormate(dlrzjzl));
            returnMap.put("DLRZJHM", bdcSpbConfig.bdcStringOutFormate(dlrzjhm));
            returnMap.put("DLRDH", bdcSpbConfig.bdcStringOutFormate(dlrdh));
            //权利人为企业回填申请书-申请人企业名称；为个人则不用回填
            boolean isGrFlag = false;
            for (Map<String, Object> qlr : qlrInfoList) {
                if("1".equals(StringUtil.getString(qlr.get("qlxz")))){//权利性质-个人
                    isGrFlag = true;
                    break;
                }
            }
            if(isGrFlag){
                returnMap.put("QLRQYMC","");
            }else{
                returnMap.put("QLRQYMC",bdcSpbConfig.bdcStringOutFormate(qlrmc));
            }
        }
        returnMap.put("ZL", StringUtil.getString(flowVars.get("LOCATED")));
        returnMap.put("BDCDYH", StringUtil.getString(flowVars.get("ESTATE_NUM")));
        returnMap.put("MJ", StringUtil.getString(flowVars.get("ZD_MJ")));
        returnMap.put("TDYT", StringUtil.getString(flowVars.get("ZD_YTSM")));
        returnMap.put("DJYY", StringUtil.getString(flowVars.get("GYTD_DJYY")));
        StringBuffer wtqx = new StringBuffer();
        wtqx.append(StringUtil.getString(flowVars.get("WTQX_BEGIN")));
        wtqx.append("至");
        wtqx.append(StringUtil.getString(flowVars.get("WTQX_END")));
        returnMap.put("WTQX",wtqx);
        returnMap.put("SQSJ_1", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));  
        returnMap.put("SQSJ_2", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日")); 
        /*登记原因证明文件*/
        //bdcSpbConfig.getMaterNameList(returnMap);
        //询问记录-权利人（依据共有方式回填）
        selectYesOrNo("GYTD_GYFS","DDSY", "0", flowVars, returnMap);
        selectYesOrNo("GYTD_GYFS","GTGY", "1", flowVars, returnMap);
        selectYesOrNo("GYTD_GYFS","AFGY", "2", flowVars, returnMap);
        return returnMap;
    }
    
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
            Map<String,Object> busInfo , Map<String,Object> returnMap) {
        String value = StringUtil.getValue(busInfo, column);
        if (StringUtils.isNotEmpty(value)) {
            if (value.equals(columnValue)) {
                returnMap.put(columnName + "_1", "■");
                returnMap.put(columnName + "_2", "□");
            } else {
                returnMap.put(columnName + "_1", "□");
                returnMap.put(columnName + "_2", "■");
            }
        } else {
            returnMap.put(columnName + "_1", "□");
            returnMap.put(columnName + "_2", "□");
        }
    }
  
    
    /** 
     * 描述 word非动态表格模板数据替换&生成PDF文件（通用）
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void commonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){ 
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//gyjsscdj//"+templatePath;       
        WordRedrawUtil.generateWord(returnMap,AttachFilePath+path);
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
        String pdfFilePath = BdcQlcMaterGenAndSignServiceImpl.getSignPdfPath();//生成PDF文件路径      
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
     * 描述    国有建设用地使用权首次登记-不动产登记申请书签章
     * @author Allin Lin
     * @created 2020年10月16日 上午9:25:11
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void djSqsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsydsyqscdjService.DJSQS_MATERNAME});
        //2.创建用户&机构
        signUserAndExinstitution(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = bdcQlcMaterGenAndSignService.
                    getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    StringUtil.getString(flowVars.get("EFLOW_EXEID")),BdcGyjsydsyqscdjService.DJSQS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfGyjsscdjsqs(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                } 
            }        
        }
    }
    
    /**
     * 描述    国有建设用地使用权首次登记-授权委托书签章
     * @author Allin Lin
     * @created 2020年10月16日 上午9:25:11
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void wtsSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcGyjsydsyqscdjService.WTS_MATERNAME});
        //2.创建用户&机构
        signUserAndExinstitution(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = bdcQlcMaterGenAndSignService.
                    getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    StringUtil.getString(flowVars.get("EFLOW_EXEID")),BdcGyjsydsyqscdjService.WTS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfGyjsscdjwts(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                } 
            }        
        }
    }
    
    /**
     * 描述   国有建设用地使用权首次登记-签章所有用户与机构创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void signUserAndExinstitution(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        boolean isGrFlag = (boolean)returnMap.get("isGrFlag");//权利人性质（true 个人  false 企业 ）
        List<Map<String, Object>> qlrList = (List<Map<String, Object>>) returnMap.get("qlrInfoList");
        if(isGrFlag){//个人           
            for (Map<String, Object> qlr : qlrList) {
                Map<String, Object> exUser = bdcQlcMaterGenAndSignService.creExUser(qlr);
                if (!(boolean) exUser.get("USER_CREATEFLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    break;
                }
            }
        }else{//企业
            for (Map<String, Object> qlr : qlrList) {
                Map<String, Object> enterprise = new HashMap<String, Object>();
                //权利人（企业）信息
                enterprise.put("licenseNumber", qlr.get("sqrzjhm"));//证照号码
                enterprise.put("organizeName", qlr.get("sqrxm"));//名称
                enterprise.put("licenseType", qlr.get("sqrzjlx"));//证照类型
                //权利人（代理人）经办信息
                enterprise.put("sqrsjhm", qlr.get("dlrsjhm"));//手机号码
                enterprise.put("sqrzjhm", qlr.get("dlrzjhm"));//证件号码
                enterprise.put("sqrzjlx", qlr.get("dlrzjlx"));//证件类型
                enterprise.put("sqrxm", qlr.get("dlrxm"));//姓名               
                enterprise = bdcQlcMaterGenAndSignService.creExInstitutions(enterprise);
                if(!(boolean)enterprise.get("INS_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", enterprise.get("SIGN_MSG"));  
                    break;
                }
            }
        }
    }
}
