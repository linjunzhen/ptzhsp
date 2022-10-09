/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.SwbProvDataDao;
import net.evecom.platform.bsfw.service.SwbProvAttrService;
import net.evecom.platform.bsfw.service.SwbProvDataService;
import net.evecom.platform.ems.util.EmsSend;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.FileAttachService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述
 * @author Water Guo
 * @version 1.0
 * @created 2016年1月26日 下午3:21:23
 */
@Service("swbProvDataService")
public class SwbProvDataServiceImpl extends BaseServiceImpl implements SwbProvDataService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbProvDataServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SwbProvDataDao dao;
    /**
     * 
     */
    @Resource
    private SwbProvAttrService swbProvAttrService;
    /**
     * 
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Water Guo
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 获取省网办数据中需要被启动流程的数据列表
     * @author Water Guo
     * @created 2016年1月26日 下午3:40:21
     * @return
     */
    public List<Map<String,Object>> findNeedToStart(){
        StringBuffer sql = new StringBuffer("select S.*,I.ITEM_CODE,I.ITEM_NAME,I.BDLCDYID");
        sql.append(",I.SSBMBM,F.DEF_KEY,F.VERSION,R.FORM_KEY ");
        sql.append(" from T_BSFW_SWBPROVDATA S LEFT JOIN T_WSBS_SERVICEITEM I");
        sql.append(" ON S.SWB_ITEM_CODE=I.SWB_ITEM_CODE ");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF F ON F.DEF_ID=I.BDLCDYID ");
        sql.append(" LEFT JOIN JBPM6_FLOWFORM R ON F.BIND_FORMID=R.FORM_ID ");
        sql.append(" WHERE I.ITEM_CODE IS NOT NULL ");
        sql.append(" AND I.BDLCDYID IS NOT NULL AND S.APASINFO IS NOT NULL ");
        sql.append(" AND S.PROPOSER IS NOT NULL ");
        sql.append(" AND S.STATUS=0 ORDER BY S.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), null,null);
    }
    
    /**
     * 
     * 描述 根据省网办的办件基本JSON构建基本信息的MAP
     * @author Water Guo
     * @created 2016年1月26日 下午4:04:45
     * @param baseInfoJson
     * @return
     */
    public Map<String,Object> getBjBaseInfo(String baseInfoJson,Map<String,Object> swbData){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        Map<String,Object> apasInfo = JSON.parseObject(baseInfoJson, Map.class);
        //申请时间 设为接收时间
        String createtime  = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"); 
        flowVars.put("EFLOW_CREATETIME", createtime);
        flowVars.put("BJCXMM", apasInfo.get("PWD"));
        flowVars.put("EFLOW_ASSIGNED_EXEID", apasInfo.get("SN"));
        String sbmc = "";
        String apasInfosString = apasInfo.get("ApasInfo").toString();
        apasInfosString = apasInfosString.replace("[", "").replace("]", "");
        JSONObject  apasInfosObject = JSONObject.fromObject(apasInfosString);
//        Map<String,Object> apasInfoMap = JSON.parseObject(apasInfosObject, Map.class);
        Map apasInfoMap = (Map)apasInfosObject;//["350128"]
        /*String qweString = apasInfoMap.get("ProjectName").toString();*/
//        flowVars.put("EFLOW_CREATETIME", apasInfoMap.get("DeclareTime"));
        flowVars.put("EFLOW_CREATETIME", createtime);
        flowVars.put("BJCXMM", apasInfoMap.get("PWD"));
        flowVars.put("EFLOW_ASSIGNED_EXEID", apasInfoMap.get("SN"));
        //设置申报名称
//        if(!apasInfo.get("ProjectName").toString().equals("[]")){
//            flowVars.put("SBMC", apasInfo.get("ProjectName"));
//            sbmc = apasInfo.get("ProjectName").toString();
//        }
        if(apasInfoMap.get("ProjectName")!=null&&StringUtils.isNotEmpty(apasInfoMap.get("ProjectName").toString())){
            flowVars.put("SBMC", apasInfoMap.get("ProjectName"));
            sbmc = apasInfoMap.get("ProjectName").toString();
        }
        String itemName = (String) swbData.get("ITEM_NAME");
        StringBuffer subject = new StringBuffer("");
        if(StringUtils.isNotEmpty(sbmc)){
            subject.append(sbmc).append("(").append(itemName).append(")");
        }else{
            subject.append(itemName);
        }
        //定义标题
        flowVars.put("EFLOW_SUBJECT",subject.toString());
        return flowVars;
    }
    
    /**
     * 
     * 描述 根据省网办的申报基本JSON构建基本信息的MAP
     * @author Water Guo
     * @created 2016年1月26日 下午5:28:23
     * @param applyBaseJson
     * @return
     */
    public Map<String,Object> getApplyBaseInfo(String applyBaseJson){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        Map<String,Object> baseInfo = JSON.parseObject(applyBaseJson, Map.class);
       
        String baseInfoString = baseInfo.get("Proposer").toString();
        baseInfoString = baseInfoString.replace("[", "").replace("]", "");
        JSONObject  baseInfoObject = JSONObject.fromObject(baseInfoString);
        Map baseInfoMap = (Map)baseInfoObject;
        /*String qweString = baseInfoMap.get("ApplyType").toString();*/
        
        //获取申请人的类型
//        String applyType = (String) baseInfo.get("ApplyType");
        String applyType = baseInfoMap.get("ApplyType").toString();
        //如果是法人用户
        if(applyType.equals("1")){
            flowVars.put("BSYHLX","2");
            //获取法人信息
//            Map<String,Object> unit = (Map<String, Object>) baseInfo.get("Unit");
            Map<String,Object> unit = (Map<String, Object>) baseInfoMap.get("Unit");
            flowVars.put("SQJG_NAME", unit.get("UnitName"));
            if(unit.get("UnitCode")!=null&&StringUtils.isNotEmpty(unit.get("UnitCode").toString())){
                flowVars.put("SQJG_CODE", unit.get("UnitCode"));
            }
            if(unit.get("UnitType")!=null&&StringUtils.isNotEmpty(unit.get("UnitType").toString())){
                flowVars.put("SQJG_TYPE", unit.get("UnitType"));
            }
            if(unit.get("LealPerson")!=null&&StringUtils.isNotEmpty(unit.get("LealPerson").toString())){
                flowVars.put("SQJG_LEALPERSON", unit.get("LealPerson"));
            }
            if(unit.get("Address")!=null&&StringUtils.isNotEmpty(unit.get("Address").toString())){
                flowVars.put("SQJG_ADDR", unit.get("Address"));
            }
            if(unit.get("Tel")!=null&&StringUtils.isNotEmpty(unit.get("Tel").toString())){
                flowVars.put("SQJG_TEL", unit.get("Tel"));
            }
        }else{
            flowVars.put("BSYHLX","1");
            //获取个人用户信息
//            Map<String,Object> person = (Map<String, Object>) baseInfo.get("Person");
            Map<String,Object> person = (Map<String, Object>) baseInfoMap.get("Person");
            if(person.get("ApplyName")!=null&&StringUtils.isNotEmpty(person.get("ApplyName").toString())){
                flowVars.put("SQRMC", person.get("ApplyName"));
            }
            if(person.get("Sex")!=null&&StringUtils.isNotEmpty(person.get("Sex").toString())){
                if(person.get("Sex").toString().equals("男")){
                    flowVars.put("SQRXB", "1");
                }else{
                    flowVars.put("SQRXB", "2");
                }
            }
            if(person.get("CertificateType")!=null&&StringUtils.isNotEmpty(person.get("CertificateType").toString())){
                flowVars.put("SQRZJLX", person.get("CertificateType"));
            }
            if (person.get("CertificateNumber") != null
                    && StringUtils.isNotEmpty(person.get("CertificateNumber")
                            .toString())) {
                flowVars.put("SQRSFZ", person.get("CertificateNumber"));
            }
            if(person.get("Address")!=null&&StringUtils.isNotEmpty(person.get("Address").toString())){
                flowVars.put("SQRLXDZ", person.get("Address"));
            }
            if(person.get("MobilePhone")!=null&&StringUtils.isNotEmpty(person.get("MobilePhone").toString())){
                flowVars.put("SQRSJH", person.get("MobilePhone"));
            }
            if(person.get("Mail")!=null&&StringUtils.isNotEmpty(person.get("Mail").toString())){
                flowVars.put("SQRYJ", person.get("Mail"));
            }
            if(person.get("Tel")!=null&&StringUtils.isNotEmpty(person.get("Tel").toString())){
                flowVars.put("SQRDHH", person.get("Tel"));
            }
        }
        return flowVars;
    }
    
    /**
     * 
     * 描述 根据省网办的经办人JSON构建基本信息的MAP
     * @author Water Guo
     * @created 2016年1月27日 上午10:01:56
     * @param jbrBaseJson
     * @return
     */
    public Map<String,Object> getJbrBaseInfo(String jbrBaseJson){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        Map<String,Object> baseInfo = JSON.parseObject(jbrBaseJson, Map.class);
       
        String baseInfoString = baseInfo.get("Operator").toString();
        baseInfoString = baseInfoString.replace("[", "").replace("]", "");
        JSONObject  baseInfoObject = JSONObject.fromObject(baseInfoString);
        Map baseInfoMap = (Map)baseInfoObject;
        /*String qweString = baseInfoMap.get("Name").toString();*/
        
        if(baseInfoMap.get("Name")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("Name").toString())){
            flowVars.put("JBR_NAME", baseInfoMap.get("Name"));
        }
        if(baseInfoMap.get("Sex")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("Sex").toString())){
            if(baseInfoMap.get("Sex").equals("男")){
                flowVars.put("JBR_SEX", "1");
            }else{
                flowVars.put("JBR_SEX", "2");
            }
        }
        if (baseInfoMap.get("CertificateType") != null
                && StringUtils.isNotEmpty(baseInfoMap.get("CertificateType")
                        .toString())) {
            flowVars.put("JBR_ZJLX", baseInfoMap.get("CertificateType"));
        }
        if (baseInfoMap.get("CertificateNumber") != null
                && StringUtils.isNotEmpty(baseInfoMap.get("CertificateNumber")
                        .toString())) {
            flowVars.put("JBR_ZJHM", baseInfoMap.get("CertificateNumber"));
        }
        if(baseInfoMap.get("Address")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("Address").toString())){
            flowVars.put("JBR_ADDRESS", baseInfoMap.get("Address"));
        }
        if(baseInfoMap.get("Postcode")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("Postcode").toString())){
            flowVars.put("JBR_POSTCODE", baseInfoMap.get("Postcode"));
        }
        if(baseInfoMap.get("MobilePhone")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("MobilePhone").toString())){
            flowVars.put("JBR_MOBILE", baseInfoMap.get("MobilePhone"));
        }
        if(baseInfoMap.get("Mail")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("Mail").toString())){
            flowVars.put("JBR_EMAIL", baseInfoMap.get("Mail"));
        }
        if(baseInfoMap.get("Tel")!=null&&StringUtils.isNotEmpty(baseInfoMap.get("Tel").toString())){
            flowVars.put("JBR_LXDH", baseInfoMap.get("Tel"));
        }
        return flowVars;
    }
    
    /**
     * 
     * 描述 获取省网办的附件JSON
     * @author Water Guo
     * @created 2016年1月27日 上午11:29:26
     * @param attJson
     * @param flowVars
     * @return
     */
    public String getSwbAttachFileJson(String attJson,Map<String,Object> flowVars){
        String busRecordId = "";
        Map<String,Object> attachMap = JSON.parseObject(attJson, Map.class);
        String attachsString = attachMap.get("Attrs").toString();
        Map<String, Object> AttrMap = JSON.parseObject(attachsString,Map.class);
        String attrJson = AttrMap.get("Attr").toString();
        List<Map<String,Object>> attachList  =  JSON.parseObject(attrJson,List.class);
       // List<Map> attachList = JSON.parseArray(attJson, Map.class);
        List<Map<String,Object>> flowAttachList = new ArrayList<Map<String,Object>>();
        for(Map attach:attachList){
            Map<String,Object> flowAttach = new HashMap<String,Object>();
            //获取文件的模式
            String mode = attach.get("Mode") == null ? "" : attach.get("Mode")
                    .toString().replace("[\"", "").replace("\"]", "");
            if (mode.equals("upload") || mode.equals("paper") || mode.equals("electronicmaterial") || mode.equals("license")) {
                String UNID = attach.get("UNID") == null ? "" : attach
                        .get("UNID").toString().replace("[\"", "")
                        .replace("\"]", "");
                String code = attach.get("Code") == null ? "" : attach
                        .get("Code").toString().replace("[\"", "")
                        .replace("\"]", "");
                String fileId = "";
                String fileName = "";
                String filePath = "";
                String fileType = "";
                String sqfs = "1";
                String busTableName = (String)flowVars.get("EFLOW_BUSTABLENAME");
                if (StringUtils.isNotEmpty(UNID)) {
                    fileName = attach.get("FileName") == null ? "" : attach
                            .get("FileName").toString().replace("[\"", "")
                            .replace("\"]", "");
                    // 获取文件的后缀名
                    fileType = FileUtil.getFileExtension(fileName);
                    // 保存文件到磁盘
                } else {
                    // fileName = (String) attach.get("Name");
                    fileName = attach.get("Name") == null ? "" : attach
                            .get("Name").toString().replace("[\"", "")
                            .replace("\"]", "");
                    sqfs = "2";
                }
                fileId = swbProvAttrService.genFileToDiskAndSaveL(UNID, fileType, fileName, filePath, busTableName,
                        code, sqfs, busRecordId);
                flowAttach.put("ATTACH_KEY",code);
                flowAttach.put("SQFS",sqfs);
                flowAttach.put("SFSQ","1");
                flowAttach.put("FILE_ID",fileId);
                flowAttachList.add(flowAttach);
            }
        }
        String json = JSON.toJSONString(flowAttachList);
        log.info("json:"+json);
        return json;
    }
    
    /**
     * 
     * 描述 启动省网办传输过来的流程
     * @author Water Guo
     * @created 2016年1月27日 下午3:56:21
     * @param swbData
     * @return
     * @throws Exception 
     */
    public Map<String,Object> startSwbFlow(Map<String,Object> swbData) throws Exception{
        Map<String,Object> flowVars = new HashMap<String,Object>();
        flowVars.put("EFLOW_CREATORACCOUNT","admin");
        flowVars.put("EFLOW_CREATORNAME", "福建省网上办事大厅下发");
        flowVars.put("EFLOW_DEFKEY", swbData.get("DEF_KEY"));
        flowVars.put("EFLOW_BUSTABLENAME", swbData.get("FORM_KEY"));
        flowVars.put("EFLOW_DEFID", swbData.get("BDLCDYID"));
        flowVars.put("EFLOW_DEFVERSION",swbData.get("VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL","false");
        flowVars.put("EFLOW_ISTEMPSAVE","-1");
        flowVars.put("ITEM_NAME", swbData.get("ITEM_NAME"));
        flowVars.put("ITEM_CODE", swbData.get("ITEM_CODE"));
        flowVars.put("SSBMBM", swbData.get("SSBMBM"));
        flowVars.put("SQFS","3");
        int flowVersion = Integer.parseInt(swbData.get("VERSION").toString());
        String defId = swbData.get("BDLCDYID").toString();
        //获取当前运行节点
        String currentOperNodeName = flowNodeService.getNodeName(defId, 
                flowVersion, Jbpm6Constants.START_NODE);
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES",currentOperNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME",currentOperNodeName);
        //获取办件基本信息
        String APASINFO = (String) swbData.get("APASINFO");
        if(StringUtils.isNotEmpty(APASINFO)){
            Map<String,Object> bjInfo = this.getBjBaseInfo(APASINFO,swbData);
            flowVars.putAll(bjInfo);
        }
        //获取申报基本信息
        String PROPOSER= (String) swbData.get("PROPOSER");
        if(StringUtils.isNotEmpty(PROPOSER)){
            Map<String,Object> applyInfo = this.getApplyBaseInfo(PROPOSER);
            flowVars.putAll(applyInfo);
        }
        //获取经办人基本信息
        String OPERATOR= (String) swbData.get("OPERATOR");
        if(StringUtils.isNotEmpty(OPERATOR)){
            Map<String,Object> jbrInfo = this.getJbrBaseInfo(OPERATOR);
            flowVars.putAll(jbrInfo);
        }
        //获取附件基本信息
        String ATTRS = swbData.get("ATTRS")==null?
                "":swbData.get("ATTRS").toString();
        if(StringUtils.isNotEmpty(ATTRS)){
            String fileAttachJson = this.getSwbAttachFileJson(ATTRS, flowVars);
            flowVars.put("EFLOW_SUBMITMATERFILEJSON", fileAttachJson);
        }
        //Maodison You 2020/4/29 获取快递信息
        String EXPRESS = swbData.get("EXPRESS") == null ?
                "" : swbData.get("EXPRESS").toString();
        if (StringUtils.isNotEmpty(EXPRESS)) {
            this.getExpressInfo(EXPRESS, flowVars);
        }
        //获取省网办发起节点名称
        String swbStartNodeName = nodeConfigService.getNodeNameForProvinceStart(defId, flowVersion);
        if(StringUtils.isNotEmpty(swbStartNodeName)){
            String nextStepJson = this.jbpmService.getNextStepsJson(defId, 
                    flowVersion,swbStartNodeName, flowVars);
            if(StringUtils.isNotEmpty(nextStepJson)){
                flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
            }
        }
        Map<String,Object> resultMap = jbpmService.doFlowJob(flowVars);
        //更新状态为完成
        this.updateDataStatus(swbData.get("ID").toString(), 1);
        return resultMap;
    }
    
    /**
     * 
     * 描述 更新数据的状态
     * @author Water Guo
     * @created 2016年1月27日 下午4:08:51
     * @param dataId
     * @param status
     */
    public void updateDataStatus(String dataId,int status){
        StringBuffer sql = new StringBuffer("update T_BSFW_SWBPROVDATA S ");
        sql.append(" SET S.STATUS=? WHERE S.ID=? ");
        dao.executeSql(sql.toString(), new Object[]{status,dataId});
    }

    /**
     * 描述:存储省网下发得EMS信息
     *
     * @author Madison You
     * @created 2020/4/29 11:38:00
     * @param
     * @return
     */
    private void getExpressInfo(String expressJson , Map<String,Object> flowVars) {
        try {
            Map<String, Object> expressInfo = JSON.parseObject(expressJson, Map.class);
            if (expressInfo!=null && expressInfo.get("Expresses") != null) {
                String expressInfoString = expressInfo.get("Expresses").toString();
                expressInfoString = expressInfoString.replace("[", "").replace("]", "");
                if(expressInfoString!=null && !expressInfoString.isEmpty()){
                JSONObject expressInfoObject = JSONObject.fromObject(expressInfoString);
                String express = expressInfoObject.getString("Express") == null ? ""
                        : expressInfoObject.getString("Express").toString();
                if (StringUtils.isNotEmpty(express)) {
                    Map<String, Object> expressMap = JSON.parseObject(express, Map.class);
                    String recipients = StringUtil.getValue(expressMap, "Recipients");
                    if (recipients.equals("")) {
                        flowVars.put("FINISH_GETTYPE", "01");
                    } else {
                        flowVars.put("FINISH_GETTYPE", "02");
                        flowVars.put("RESULT_SEND_ADDR", StringUtil.getValue(expressMap, "RAddress"));
                        flowVars.put("RESULT_SEND_ADDRESSEE", recipients);
                        flowVars.put("RESULT_SEND_MOBILE", StringUtil.getValue(expressMap, "RMobile"));
                        flowVars.put("RESULT_SEND_POSTCODE", StringUtil.getValue(expressMap, "RAreaCode"));
                        /*揽件*/
                        sendEmsByProvince(flowVars);
                    }
                }
            } 
                }
        } catch (Exception e) {
            flowVars.put("FINISH_GETTYPE", "");
            flowVars.put("RESULT_SEND_ADDR", "");
            flowVars.put("RESULT_SEND_ADDRESSEE", "");
            flowVars.put("RESULT_SEND_MOBILE", "");
            flowVars.put("RESULT_SEND_POSTCODE", "");
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 描述:揽件
     *
     * @author Madison You
     * @created 2020/5/12 10:09:00
     * @param
     * @return
     */
    private void sendEmsByProvince(Map<String, Object> flowVars) {
        String itemCode = StringUtil.getValue(flowVars, "ITEM_CODE");
        Map<String, Object> item = swbProvAttrService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_CODE" }, new Object[] { itemCode });
        if (flowVars.get("FINISH_GETTYPE") != null && "02".equals(flowVars.get("FINISH_GETTYPE"))
                && item.get("PAPERSUB") != null && item.get("PAPERSUB").toString().contains("2")) {
            List<Map<String,Object>> OrderNormals = new ArrayList<Map<String,Object>>();
            Map<String,Object> OrderNormal = new HashMap<String, Object>();
            OrderNormal.put("txLogisticID", flowVars.get("EFLOW_ASSIGNED_EXEID"));
            OrderNormal.put("serviceType", "1");
            Map<String,Object> sender = new HashMap<String, Object>();
            sender.put("name", item.get("ITEM_SEND_ADDRESSEE"));
            sender.put("postCode", item.get("ITEM_SEND_POSTCODE"));
            sender.put("prov", item.get("ITEM_SEND_PROV"));
            sender.put("city", item.get("ITEM_SEND_CITY"));
            sender.put("address", item.get("ITEM_SEND_ADDR"));
            sender.put("mobile", item.get("ITEM_SEND_MOBILE"));
            OrderNormal.put("sender", sender);
            Map<String,Object> receiver = new HashMap<String, Object>();
            receiver.put("name", flowVars.get("RESULT_SEND_ADDRESSEE"));
            receiver.put("mobile", flowVars.get("RESULT_SEND_MOBILE"));
            receiver.put("postCode", flowVars.get("RESULT_SEND_POSTCODE"));
            String resultSendAddr = StringUtil.getValue(flowVars, "RESULT_SEND_ADDR");
            if (resultSendAddr.contains(",")) {
                String[] addr = resultSendAddr.split(",");
                if (addr.length == 3) {
                    receiver.put("prov", addr[0]);
                    receiver.put("city", addr[1]);
                    receiver.put("address", addr[2]);
                }
            }
            OrderNormal.put("receiver", receiver);
            OrderNormals.add(OrderNormal);
            Map<String,Object> OrderNormalsMap = new HashMap<String, Object>();
            OrderNormalsMap.put("orderNormals", OrderNormals);
            Map<String,String> busParams = new HashMap<String, String>();
            busParams.put("orderNormal", JSON.toJSONString(OrderNormalsMap));
            busParams.put("size", "1");
            Map<String, Object> result = EmsSend.emsSend(busParams);
            Map<String,Object> emsInfo = new HashMap<String, Object>();
            emsInfo.put("EXE_ID", flowVars.get("EFLOW_ASSIGNED_EXEID"));
            emsInfo.put("ADDRESSEE_NAME", flowVars.get("RESULT_SEND_ADDRESSEE"));
            emsInfo.put("ADDRESSEE_PHONE", flowVars.get("RESULT_SEND_MOBILE"));
            emsInfo.put("ADDRESSEE_ADDR", flowVars.get("RESULT_SEND_ADDR"));
            emsInfo.put("ADDRESSEE_ZIPCODE", flowVars.get("RESULT_SEND_POSTCODE"));
            if((boolean) result.get("success")){
                emsInfo.put("MAIL_NO", result.get("mailNo"));
                emsInfo.put("LOGISTICS_ID", result.get("txLogisticID"));
                swbProvAttrService.saveOrUpdate(emsInfo, "JBPM6_FLOW_EMSINFO", null);
            }
        }
    }
}

