/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcLsylwtcxDao;
import net.evecom.platform.bdc.service.BdcLsylwtcxService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述  不动产历史遗留问题查询service
 * @author Allin Lin
 * @created 2021年1月14日 下午4:27:21
 */
@Service("bdcLsylwtcxService")
public class BdcLsylwtcxServiceImpl extends BaseServiceImpl implements BdcLsylwtcxService{
    
    
    /**
     * log4j声明
     */
    private static Log log = LogFactory.getLog(BdcLsylwtcxServiceImpl.class);
    
    
    /**
     * 引入dao
     */
    @Resource
    private BdcLsylwtcxDao dao;
    
    
    /**
     *  引入service
     */
    @Resource
    private BdcSpbPrintConfigService bdcSqbConfig;
    
    
    /**
     * 所引入的serivice
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 所引入的service
     */
    @Resource
    private JbpmService jbpmService;
    
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    
    /**
     * 
     * 描述  覆盖实体dao
     * @author Allin Lin
     * @created 2021年1月14日 下午4:32:27
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述    不动产历史遗留问题数据回填
     * @author Allin Lin
     * @created 2021年1月14日 下午4:38:18
     * @param args
     */
    public void setLsylwtcxData(Map<String, Map<String, Object>> args){
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置不动产历史遗留问题业务数据*/
        this.setLsylwtcxBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }
    
    
    /**
     * 
     * 描述    设置不动产历史遗留问题业务数据
     * @author Allin Lin
     * @created 2021年1月14日 下午4:44:43
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setLsylwtcxBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("p_ewm", "");
        returnMap.put("qllx", StringUtil.getValue(busInfo,"ITEM_NAME"));
        returnMap.put("djlx", "建设用地使用权及房屋所有权登记");
        returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        if (StringUtil.getValue(execution, "SQFS").equals("1")) {
            returnMap.put("sjr", "");
        } else {
            returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        }
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isEmpty(sbh)) {
            returnMap.put("xmsqbh", StringUtil.getValue(busInfo, "EFLOW_EXEID"));
        }
        /* 权利人信息JSON */
        String piJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {

            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();

            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /* 证件类型代码转换 */
                String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                sfzjzl.append(documentType).append(",");
                zjhm.append(StringUtil.getValue(piMap, "POWERPEOPLEIDNUM")).append(",");
                txdz.append(StringUtil.getValue(piMap, "POWERPEOPLEADDR")).append(",");
                yb.append(StringUtil.getValue(piMap, "POWERPEOPLEPOSTCODE")).append(",");
                fddbr.append(StringUtil.getValue(piMap, "POWLEGALNAME")).append(",");
                dlrxm.append(StringUtil.getValue(piMap, "POWAGENTNAME")).append(",");
                lxdh1.append(StringUtil.getValue(piMap, "POWERPEOPLEMOBILE")).append(",");
                lxdh2.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");

                returnMap.put("qlrxm", bdcSqbConfig.bdcStringOutFormate(qlrxm));
                returnMap.put("sfzjzl", bdcSqbConfig.bdcStringOutFormate(sfzjzl));
                returnMap.put("zjhm", bdcSqbConfig.bdcStringOutFormate(zjhm));
                returnMap.put("txdz", bdcSqbConfig.bdcStringOutFormate(txdz));
                returnMap.put("yb", bdcSqbConfig.bdcStringOutFormate(yb));
                returnMap.put("fddbr", bdcSqbConfig.bdcStringOutFormate(fddbr));
                returnMap.put("dlrxm", bdcSqbConfig.bdcStringOutFormate(dlrxm));
                returnMap.put("lxdh1", bdcSqbConfig.bdcStringOutFormate(lxdh1));
                returnMap.put("lxdh2", bdcSqbConfig.bdcStringOutFormate(lxdh2));
            }
        }

        /* 义务人信息JSON */
        String qsJson = StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON");
        if (StringUtils.isNotEmpty(qsJson)) {

            StringBuffer ywrxm = new StringBuffer();
            StringBuffer sfzjzl1 = new StringBuffer();
            StringBuffer zjhm1 = new StringBuffer();
            StringBuffer fddbr1 = new StringBuffer();
            StringBuffer lxdh3 = new StringBuffer();
            StringBuffer dlrxm1 = new StringBuffer();
            StringBuffer lxdh4 = new StringBuffer();
            /* 原不动产权证书号 */
            StringBuffer ybdcqzsh = new StringBuffer();
            StringBuffer dljgmc1 = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "QLR")).append(",");
                /* 证件类型代码转换 */
                String powsourceidtype = StringUtil.getValue(qsMap, "BDC_QLRZJLX");
                String documentType = "";
                if (StringUtils.isNotEmpty(powsourceidtype)) {
                    documentType = dictionaryService.getDicNames("DocumentType", powsourceidtype);
                }
                sfzjzl1.append(documentType).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "BDC_QLRZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "BDC_FDDBRXM")).append(",");
                lxdh3.append(StringUtil.getValue(qsMap, "BDC_FDDBRDH")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "BDC_DLRXM")).append(",");
                lxdh4.append(StringUtil.getValue(qsMap, "BDC_DLRDH")).append(",");
                ybdcqzsh.append(StringUtil.getValue(qsMap, "POWERSOURCEIDNUM")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "BDC_DLJGMC")).append(",");
            }

            returnMap.put("ywrxm", bdcSqbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSqbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSqbConfig.bdcStringOutFormate(zjhm1));
            returnMap.put("fddbr1", bdcSqbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("lxdh3", bdcSqbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("dlrxm1", bdcSqbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("lxdh4", bdcSqbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("ybdcqzsh", bdcSqbConfig.bdcStringOutFormate(ybdcqzsh));
            returnMap.put("dljgmc1", bdcSqbConfig.bdcStringOutFormate(dljgmc1));
        }
        
        /* 不动产情况 */
        returnMap.put("zl", StringUtil.getValue(busInfo, "FW_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));

        /*权利开始时间和结束时间*/
        String gytdBeginTime = StringUtil.getValue(busInfo, "GYTD_BEGIN_TIME");
        String gytdEndTime1 = StringUtil.getValue(busInfo, "GYTD_END_TIME1");
        if (StringUtils.isNotEmpty(gytdBeginTime)) {
            returnMap.put("qlkssj", bdcSqbConfig.bdcDateFormat(gytdBeginTime.substring(0, 10),
                    "yyyy-MM-dd", "yyyy月MM月dd日"));
        } else {
            returnMap.put("qlkssj", "");
        }
        if (StringUtils.isNotEmpty(gytdEndTime1)) {
            returnMap.put("qljssj", bdcSqbConfig.bdcDateFormat(gytdEndTime1.substring(0, 10),
                    "yyyy-MM-dd", "yyyy月MM月dd日"));
        } else {
            returnMap.put("qljssj", "");
        }
        /*宗地信息*/
        returnMap.put("zondmj", StringUtil.getValue(busInfo,"ZD_MJ"));
        /*房屋信息*/
        returnMap.put("fwjzmj", StringUtil.getValue(busInfo,"FW_JZMJ"));
        returnMap.put("fwfttdmj", StringUtil.getValue(busInfo,"FW_FTTDMJ"));
        // 字典qlxz
        returnMap.put("qlxz",
                dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
        
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        
        /*用途&面积 */
        StringBuffer mj = new StringBuffer();
        mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²").append("/");
        mj.append("房屋建筑面积：").append(StringUtil.getValue(busInfo, "FW_JZMJ")).append("m²");
        returnMap.put("mj", mj);
        returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM") + "/" + StringUtil.getValue(busInfo, "FW_YTSM"));
        
        /* 被担保债券数额 */
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "YDCRJ"));
        
        /*在建建筑物抵押范围*/
        returnMap.put("zjjzwdyfw", "");
        
        /* 债务起止时间 */
        /* 债务履行期限 */
        returnMap.put("zwlxqx", StringUtil.getValue(busInfo, "WJMJBJ"));
//        String DY_QLQSSJ = StringUtil.getValue(busInfo, "DY_QLQSSJ");
//        String DY_QLJSSJ = StringUtil.getValue(busInfo, "DY_QLJSSJ");
//        if (!DY_QLQSSJ.equals("") && !DY_QLJSSJ.equals("")) {
//            String zwBegin = bdcSqbConfig.bdcDateFormat(DY_QLQSSJ, "yyyy-MM-dd", "yyyy年MM月dd日");
//            String zwEnd = bdcSqbConfig.bdcDateFormat(DY_QLJSSJ, "yyyy-MM-dd", "yyyy年MM月dd日");
//            returnMap.put("zwqssj", zwBegin);
//            returnMap.put("zwjssj", zwEnd);
//            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
//        }
      
        /* 登记原因 */
        returnMap.put("djyy", StringUtil.getValue(busInfo, "FW_DJYY"));
        /* 登记原因证明文件 */
        bdcSqbConfig.getMaterNameList(returnMap);
        /* 填写初审核定意见' */
        bdcSqbConfig.getbdcDjshOpinion(busInfo, returnMap);
        /*回填审批日期*/
        fillInSpDate(returnMap);
        /*填写不动产地价计算信息*/
        fillInBdcPriceInfo(busInfo, returnMap);
        returnMap.put("hd", "待缴清税费后予以登记。");
        String hzOpinionContent = StringUtil.getValue(busInfo, "HZ_OPINION_CONTENT");
        if (StringUtils.isNotEmpty(hzOpinionContent)) {
            returnMap.put("hd", hzOpinionContent);
        }
    }

    /**
     * 描述:回填审批日期
     *
     * @author Madison You
     * @created 2021/4/15 15:10:00
     * @param
     * @return
     */
    private void fillInSpDate(Map<String, Object> returnMap) {
        /*获取收费环节提交日期*/
        Map<String, Object> taskMap = dao.getMapBySql("select end_time from jbpm6_task where exe_id = ? " +
                        "AND IS_AUDITED = '-1' AND TASK_NODENAME = '收费' and task_status = 2 ",
                new Object[]{StringUtil.getValue(returnMap, "xmsqbh")});
        if (Objects.nonNull(taskMap)) {
            String endTime = StringUtil.getValue(taskMap, "END_TIME");
            if (StringUtils.isNotEmpty(endTime)) {
                returnMap.put("sprq", bdcSqbConfig.bdcDateFormat(endTime,
                        "yyyy-MM-dd hh:mm:ss", "yyyy年MM月dd日"));
            }
        }
    }

    /**
     * 
     * 描述     批量导入人员绑定流程实例
     * @author Allin Lin
     * @created 2021年2月1日 下午4:24:35
     * @param flowVars
     * @return
     */
    public Map<String,Object> bindPersonExeId(Map<String, Object> flowVars){
        String personIds = flowVars.get("personIds").toString();//人员ID
        if(StringUtils.isNotEmpty(personIds)){
            String[] personArr = personIds.split(",");
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
            variables.put("GEN_STATUS", "0");
            for(String personId : personArr){
                dao.saveOrUpdate(variables, "T_BDCQLC_LSYLPLBJRY", personId);
            }
        }
        return flowVars;
    }
    
    /**
     * 描述   批量收件人员独立生成办件并受理 
     * @author Allin Lin
     * @created 2021年2月1日 下午4:58:13
     * @param recordId
     * @return
     */
    public Map<String,Object> acceptPersonExe(String recordId){
        Map<String,Object> flowVars = new HashMap<String, Object>();
        try{
            Map<String,Object> personInfo = dao.getByJdbc("T_BDCQLC_LSYLPLBJRY", new String[]{"RECORD_ID"}, new Object[]{recordId});
            Map<String,Object> exe = dao.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{personInfo.get("EXE_ID")});
            Map<String,Object> ywInfo = dao.getByJdbc("T_BDCQLC_LSYLPLJINFO", new String[]{"YW_ID"}, new Object[]{exe.get("BUS_RECORDID")});
            
            Map<String,Object> dic = dictionaryService.get("plys", exe.get("ITEM_CODE").toString());
            Map<String,Object> item = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"}, new Object[]{dic.get("DIC_DESC")});
            Map<String,Object> def = dao.getByJdbc("JBPM6_FLOWDEF", new String[]{"DEF_ID"}, new Object[]{item.get("BDLCDYID")});
            Map<String,Object> form = dao.getByJdbc("JBPM6_FLOWFORM", new String[]{"FORM_ID"}, new Object[]{def.get("BIND_FORMID")});
            //办件基本信息
            flowVars.put("ITEM_CODE", dic.get("DIC_DESC"));
            flowVars.put("ITEM_NAME", item.get("ITEM_NAME"));
            flowVars.put("SQFS", exe.get("SQFS"));
            flowVars.put("SSBMBM", exe.get("SSBMBM"));
            flowVars.put("BELONG_DEPT", exe.get("SSBMBM"));
            flowVars.put("EFLOW_SUBJECT", personInfo.get("SQRMC")+"-"+item.get("ITEM_NAME")+"（"+item.get("ITEM_NAME")+"）");
            flowVars.put("SBMC", personInfo.get("SQRMC")+"-"+item.get("ITEM_NAME"));
            flowVars.put("SXLX", item.get("SXLX"));
            flowVars.put("SXXZ", item.get("SXXZ"));
            flowVars.put("PROMISE_DATE", item.get("CNQXGZR"));
            flowVars.put("EFLOW_DEFID", def.get("DEF_ID"));
            flowVars.put("EFLOW_DEFKEY", def.get("DEF_KEY"));
            flowVars.put("EFLOW_DEFVERSION", def.get("VERSION"));
            flowVars.put("EFLOW_ISTEMPSAVE", "-1");     
            flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", "开始");
            flowVars.put("EFLOW_CURUSEROPERNODENAME", "开始");
            String nextStepJson = this.jbpmService.getNextStepsJson(def.get("DEF_ID").toString(),
                    Integer.parseInt(def.get("VERSION").toString()), "开始",
                    flowVars);
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);   
            flowVars.put("EFLOW_BUSTABLENAME", form.get("FORM_KEY"));
    
            flowVars.put("EFLOW_CREATORID", exe.get("CREATOR_ID"));
            flowVars.put("EFLOW_CREATORACCOUNT", exe.get("CREATOR_ACCOUNT"));
            flowVars.put("EFLOW_CREATORNAME", exe.get("CREATOR_NAME"));
            flowVars.put("EFLOW_CREATORPHONE", exe.get("CREATOR_PHONE"));
            String randomNum = StringUtil.getFormatNumber(6, StringUtil.getRandomIntNumber(1000000) + "");
            flowVars.put("BJCXMM", randomNum);
            flowVars.put("OPERATOR_ID", exe.get("OPERATOR_ID"));
            //申报对象信息
            flowVars.put("SFCJ", exe.get("SFCJ"));
            flowVars.put("BSYHLX", 1);
            flowVars.put("SQRMC", personInfo.get("SQRMC"));
            flowVars.put("SQRXB", personInfo.get("SQRXB"));
            flowVars.put("SQRZJLX", "SF");
            flowVars.put("SQRSFZ", personInfo.get("SQRSFZ"));
            flowVars.put("SQRSJH", personInfo.get("SQRSJH"));
            flowVars.put("SQRLXDZ", personInfo.get("SQRLXDZ"));
            flowVars.put("SFXSJBRXX", 1);
            flowVars.put("JBR_NAME", exe.get("JBR_NAME"));
            flowVars.put("JBR_SEX", exe.get("JBR_SEX"));
            flowVars.put("JBR_MOBILE", exe.get("JBR_MOBILE"));
            flowVars.put("JBR_ZJLX", exe.get("JBR_ZJLX"));
            flowVars.put("JBR_ZJHM", exe.get("JBR_ZJHM"));
            flowVars.put("JBR_LXDH", exe.get("JBR_LXDH"));
            flowVars.put("JBR_POSTCODE", exe.get("JBR_POSTCODE"));
            flowVars.put("JBR_EMAIL", exe.get("JBR_EMAIL"));
            flowVars.put("JBR_ADDRESS", exe.get("JBR_ADDRESS"));
            flowVars.put("JBR_REMARK", exe.get("JBR_REMARK"));
            
            //受理信息
            flowVars.put("ESTATE_NUM", ywInfo.get("ESTATE_NUM"));
            flowVars.put("TAKECARD_TYPE", ywInfo.get("TAKECARD_TYPE"));
            flowVars.put("APPLICANT_UNIT", personInfo.get("SQRMC"));//取权利人姓名
            flowVars.put("LOCATED", personInfo.get("SQRLXDZ"));//取联系地址
            flowVars.put("DZWQLLX", ywInfo.get("DZWQLLX"));
            flowVars.put("NOTIFY_NAME", ywInfo.get("NOTIFY_NAME"));
            flowVars.put("NOTIFY_TEL", ywInfo.get("NOTIFY_TEL"));
            flowVars.put("CHGS_ID", ywInfo.get("CHGS_ID"));
            flowVars.put("SFSCCH", ywInfo.get("SFSCCH"));
            flowVars.put("NOTIFY_NAME", ywInfo.get("NOTIFY_NAME"));
            flowVars.put("NOTIFY_TEL", ywInfo.get("NOTIFY_TEL"));
            
            
            //业务数据设置（不动产历史遗留问题业务-权利人信息取申请人信息）
            if("T_BDCQLC_LSYLWTCZ".equals(StringUtil.getString(form.get("FORM_KEY")))){
                Map<String, Object> qlrInfo = new HashMap<String, Object>();
                List<Map<String, Object>> qlrList = new ArrayList<Map<String,Object>>();
                qlrInfo.put("POWERPEOPLENAME", personInfo.get("SQRMC"));//姓名
                qlrInfo.put("POWERPEOPLEIDNUM", personInfo.get("SQRSFZ"));//身份证号码
                qlrInfo.put("POWERPEOPLEIDTYPE", "SF");//身份证类型
                qlrInfo.put("POWERPEOPLENATURE", "1");//权利性质-默认个人
                qlrInfo.put("POWERPEOPLESEX", personInfo.get("SQRXB"));//性别
                qlrInfo.put("POWERPEOPLEMOBILE", personInfo.get("SQRSJH"));//手机号码
                qlrInfo.put("POWERPEOPLEADDR", personInfo.get("SQRLXDZ"));//地址
                //权利人-代理人信息
                qlrInfo.put("POWAGENTNAME", exe.get("JBR_NAME"));
                qlrInfo.put("POWAGENTIDTYPE", exe.get("JBR_ZJLX"));
                qlrInfo.put("POWAGENTTEL", exe.get("JBR_MOBILE"));
                qlrInfo.put("POWAGENTIDNUM", exe.get("JBR_ZJHM"));
                qlrList.add(qlrInfo);
                flowVars.put("POWERPEOPLEINFO_JSON", JSON.toJSONString(qlrList)); 
            }
            
            
            //办理结果领取方式
            flowVars.put("FINISH_GETTYPE", ywInfo.get("FINISH_GETTYPE"));
            flowVars.put("RESULT_SEND_ADDRESSEE", ywInfo.get("RESULT_SEND_ADDRESSEE"));
            flowVars.put("RESULT_SEND_ADDR", ywInfo.get("RESULT_SEND_ADDR"));
            flowVars.put("RESULT_SEND_MOBILE", ywInfo.get("RESULT_SEND_MOBILE"));
            flowVars.put("RESULT_SEND_POSTCODE", ywInfo.get("RESULT_SEND_POSTCODE"));
            flowVars.put("RESULT_SEND_REMARKS", ywInfo.get("RESULT_SEND_REMARKS"));
            flowVars.put("RESULT_SEND_PROV", ywInfo.get("RESULT_SEND_PROV"));
            flowVars.put("RESULT_SEND_CITY", ywInfo.get("RESULT_SEND_CITY"));
            
            flowVars.put("REMARK", "归属（"+exe.get("EXE_ID")+"-"+exe.get("SUBJECT")+"）");
            
            //其他信息-备注
            flowVars.put("BDC_REMARK", ywInfo.get("BDC_REMARK"));
            
            //材料-业务办理子项
            flowVars.put("EXE_SUBBUSCLASS", exe.get("EXE_SUBBUSCLASS"));
            
            flowVars = jbpmService.doFlowJob(flowVars);

            // 获取业务表名称
            String busTableName = (String) flowVars.get("EFLOW_BUSTABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) flowVars.get("EFLOW_BUSRECORDID");
            
            //保存材料附件
            List<Map<String, Object>> filelist = fileAttachService.findByList(exe.get("BUS_TABLENAME").toString(),
                    exe.get("BUS_RECORDID").toString());
            //List<Map<String,Object>> newfilelist = new ArrayList<Map<String,Object>>();
            //String fileIds = "";
            for(Map<String,Object> file : filelist){
                Map<String,Object> newfile = new HashMap<String, Object>();
                Map<String,Object> mater = dao.getByJdbc("T_WSBS_APPLYMATER", new String[]{"MATER_CODE"}, new Object[]{file.get("ATTACH_KEY")});

                newfile.put("BUS_TABLENAME", busTableName);
                newfile.put("BUS_TABLERECORDID", busRecordId);
                newfile.put("ATTACH_KEY", mater.get("MATER_PARENTCODE"));
                newfile.put("FILE_NAME", file.get("FILE_NAME"));
                newfile.put("FILE_PATH", file.get("FILE_PATH"));
                newfile.put("FILE_TYPE", file.get("FILE_TYPE"));
                newfile.put("UPLOADER_ID", file.get("UPLOADER_ID"));
                newfile.put("UPLOADER_NAME", file.get("UPLOADER_NAME"));
                newfile.put("FILE_LENGTH", file.get("FILE_LENGTH"));
                newfile.put("FILE_SIZE", file.get("FILE_SIZE"));
                newfile.put("IS_IMG", file.get("IS_IMG"));
                newfile.put("SQFS", file.get("SQFS"));
                newfile.put("SFSQ", file.get("SFSQ"));
                newfile.put("PLAT_TYPE", file.get("PLAT_TYPE"));
                newfile.put("CREADIT_FILEID", file.get("CREADIT_FILEID"));
                
                String fileId = dao.saveOrUpdate(newfile, "T_MSJW_SYSTEM_FILEATTACH", null);
                //fileIds = fileIds.concat(",").concat(fileId);
                //newfile.put("FILE_ID", fileId);
                //newfilelist.add(newfile);
            }
            //flowVars.put("EFLOW_SUBMITMATERFILEJSON", JSON.toJSONString(newfilelist));
            //flowVars.put("EFLOW_FILEATTACHIDS", fileIds);
            
            
            //TODO 自动流转到审核环节
            
            //更新人员办件生成状态
            Map<String,Object> udp = new HashMap<String, Object>();
            udp.put("GEN_EXE_ID", flowVars.get("EFLOW_EXEID"));
            udp.put("GEN_STATUS", 1);
            dao.saveOrUpdate(udp, "T_BDCQLC_LSYLPLBJRY", recordId);
            
            flowVars.put("OPER_SUCCESS", true);            
            flowVars.put("OPER_MSG", "操作成功");
        }catch (Exception e) {
            flowVars.put("OPER_SUCCESS", false);            
            flowVars.put("OPER_MSG", "操作失败,请联系系统管理员!");
            log.error("", e);
        }
        
        return flowVars;
    }
    
    /**
     * 
     * 描述    加载不动产历史遗留问题批量件人员信息
     * @author Allin Lin
     * @created 2021年2月2日 上午10:58:13
     * @param filter
     * @return
     */
    public List<Map<String,Object>> loadPLperson(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_BDCQLC_LSYLPLBJRY t ");
        sql.append("where 1=1 ");
        
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }

//    /**
//     * 描述:是否在资源局之后审批
//     *
//     * @author Madison You
//     * @created 2021/4/8 15:19:00
//     * @param
//     * @return
//     */
//    @Override
//    public Boolean isAfterZfjApprove(String exeId) {
//        StringBuffer sql = new StringBuffer();
//        sql.append(" SELECT TASK_STATUS FROM JBPM6_TASK WHERE EXE_ID = ? AND IS_AUDITED = '-1' AND TASK_NODENAME = '资源局' ");
//        Map<String, Object> map = dao.getMapBySql(sql.toString(), new Object[]{exeId});
//        return map != null && Objects.equals(StringUtil.getValue(map, "TASK_STATUS"), "2");
//    }

    /**
     * 描述:填写不动产地价计算信息
     *
     * @author Madison You
     * @created 2021/4/13 9:54:00
     * @param
     * @return
     */
    private void fillInBdcPriceInfo(Map<String,Object> busInfo , Map<String,Object> returnMap) {
        returnMap.put("tdzrqx", "/"); // 土地转让情形
        returnMap.put("tdxz", "/"); // 土地性质
        returnMap.put("tdyt", "/"); // 土地用途
        returnMap.put("zjzmj", "/"); // 总建筑面积
        returnMap.put("ydmj", "/"); // 用地面积
        returnMap.put("jzmj", "/"); // 建筑面积
        returnMap.put("zdmj", "/"); // 占地面积
        returnMap.put("dyjzmj", "/"); // 单元建筑面积
        returnMap.put("rjl", "/"); // 容积率
        returnMap.put("tdsyqmj", "/"); // 土地使用权面积
        returnMap.put("wjmj", "/"); // 违建面积
        returnMap.put("wjmjdj", "/"); // 违建面积单价
        returnMap.put("ydcrj", "/"); // 用地出让金
        returnMap.put("wjmjbj", "/"); // 违建面积补价

        String exeSubbusclass = StringUtil.getValue(busInfo, "EXE_SUBBUSCLASS");
        if (Objects.equals(exeSubbusclass, "单位集资房")) {
            fillInBdcDwjzfPriceInfo(busInfo,returnMap);
        } else if (Objects.equals(exeSubbusclass, "国有土地上自建房屋分割销售")) {
            fillInBdcGytdszjfwfgxsPriceInfo(busInfo,returnMap);
        } else if (Objects.equals(exeSubbusclass, "旧城改造")) {
            fillInBdcJcgzPriceInfo(busInfo,returnMap);
        }
    }

    /**
     * 描述:旧城改造
     *
     * @author Madison You
     * @created 2021/4/13 10:03:00
     * @param
     * @return
     */
    private void fillInBdcJcgzPriceInfo(Map<String, Object> busInfo, Map<String, Object> returnMap) {
        returnMap.put("tdzrqx", dictionaryService.getDicNames("JSQTDZRLX",
                StringUtil.getValue(busInfo, "TDZRQX2")));
        returnMap.put("zjzmj", StringUtil.getValue(busInfo,"JJZMJ2"));
        returnMap.put("zdmj", StringUtil.getValue(busInfo,"ZDMJ2"));
        returnMap.put("rjl", StringUtil.getValue(busInfo,"RJL2"));
        returnMap.put("dyjzmj", StringUtil.getValue(busInfo,"DYJZMJ"));
        returnMap.put("ydcrj", StringUtil.getValue(busInfo,"YDCRJ"));
    }

    /**
     * 描述:国有土地上自建房屋分割销售
     *
     * @author Madison You
     * @created 2021/4/13 10:03:00
     * @param
     * @return
     */
    private void fillInBdcGytdszjfwfgxsPriceInfo(Map<String, Object> busInfo, Map<String, Object> returnMap) {
        returnMap.put("tdzrqx", dictionaryService.getDicNames("JSQTDZRLX",
                StringUtil.getValue(busInfo, "TDZRQX1")));
        returnMap.put("zjzmj", StringUtil.getValue(busInfo,"JJZMJ1"));
        returnMap.put("zdmj", StringUtil.getValue(busInfo,"ZDMJ1"));
        returnMap.put("ydmj", StringUtil.getValue(busInfo,"YDMJ"));
        returnMap.put("rjl", StringUtil.getValue(busInfo,"RJL1"));
        returnMap.put("wjmj", StringUtil.getValue(busInfo,"WJMJ"));
        returnMap.put("ydcrj", StringUtil.getValue(busInfo,"YDCRJ"));
        returnMap.put("wjmjdj", "2820");
        returnMap.put("wjmjbj", StringUtil.getValue(busInfo,"WJMJBJ"));
    }

    /**
     * 描述:单位集资房
     *
     * @author Madison You
     * @created 2021/4/13 10:03:00
     * @param
     * @return
     */
    private void fillInBdcDwjzfPriceInfo(Map<String, Object> busInfo, Map<String, Object> returnMap) {
        returnMap.put("tdxz", dictionaryService.getDicNames("JSQTDXZ",
                StringUtil.getValue(busInfo, "TDXZ")));
        returnMap.put("tdsyqmj", StringUtil.getValue(busInfo,"TDSYQMJ"));
        returnMap.put("jzmj", StringUtil.getValue(busInfo,"JZMJ"));
        returnMap.put("tdyt", dictionaryService.getDicNames("JSQTDYT",
                StringUtil.getValue(busInfo, "TDYT")));
        returnMap.put("ydcrj", StringUtil.getValue(busInfo, "YDCRJ"));
    }

}
