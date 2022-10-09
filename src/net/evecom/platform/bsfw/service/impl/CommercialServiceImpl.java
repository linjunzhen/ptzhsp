/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.bsfw.service.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

import javax.annotation.Resource;

import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.service.KtStampService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.axis.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bsfw.dao.CommercialDao;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.entinfo.model.EcipEntInfoManagementServiceSoapBindingStub;
import net.evecom.platform.entinfo.model.EcipResponse;
import net.evecom.platform.entinfo.model.EcipResponseReCodeEntry;
import net.evecom.platform.entinfo.model.EntCompositeInfo;
import net.evecom.platform.entinfo.model.EntManager;
import net.evecom.platform.entinfo.model.EntStockHolder;
import net.evecom.platform.entinfo.model.EntSupervisor;
import net.evecom.platform.entinfo.model.EntTrustee;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.dao.SysUserDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-11-17 上午9:59:46
 */
@Service("commercialService")
public class CommercialServiceImpl extends BaseServiceImpl implements CommercialService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CommercialServiceImpl.class);
    /**
     * 引入dao
     */
    @Resource
    private CommercialDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private SysUserDao sysUserDao;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * flowTaskService
     */
    @Resource
    private KtStampService ktStampService;
    /**
     * flowTaskService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * flowTaskService
     */
    @Resource
    private ExeDataService exeDataService;

    /**
     * 覆盖获取实体dao方法
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     * 描述   获取需要自动跳转记录
     * @author Danto Huang
     * @created 2016-11-17 上午10:16:16
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" AND D.DEF_KEY IN ( ");
        sql.append(" select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='ssdjlc' ");
        sql.append(" ) and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "预审意见汇总", "预审意见汇总" }, null);
        
        return datas;
    }

    /**
     * 
     * 描述   获取需要自动跳转记录
     * @author Danto Huang
     * @created 2016-11-17 上午10:16:16
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findWXNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID,MA.IS_SIGN");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" LEFT JOIN T_BDCQLC_MATERSIGNINFO MA ON MA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "受理自动跳转", "受理自动跳转" }, null);
        
        return datas;
    }
    

    /**
     * 
     * 描述    获取需要自动跳转记录
     * @author Kester Chen
     * @created 2020年6月12日 上午10:36:43
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBJNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "办结自动跳转", "办结自动跳转" }, null);
        
        return datas;
    }
    /**
     * 
     * 描述    获取需要自动跳转记录
     * @author Kester Chen
     * @created 2020年6月12日 上午10:36:43
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findSCYJDNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "审查与决定自动跳转", "审查与决定自动跳转" }, null);
        
        return datas;
    }
    
    /**
     * 
     * 描述   获取需要自动跳转记录
     * @author Danto Huang
     * @created 2016-11-17 上午10:16:16
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMQNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" and TA.TASK_STATUS=1 ");
        sql.append(" and (T.ISFACESIGN=1 or T.ISNEEDSIGN=0) ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] {ExeDataService.ID_IDENTIFY_TASKNAME, ExeDataService.ID_IDENTIFY_TASKNAME }, null);
        
        return datas;
    }
    /**
     *
     * 描述   获取需要商事身份认证自动跳转记录
     * @author Danto Huang
     * @created 2016-11-17 上午10:16:16
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMpIdIdentifyNeedAutoJump(String curStepName,String taskNodeName) {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" and TA.TASK_STATUS=1 ");
        sql.append(" and T.ISFACESIGN=1  ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] {curStepName, taskNodeName }, null);

        return datas;
    }
    /**
     * 
     * 描述   获取并审需要自动跳转记录
     * @author Danto Huang
     * @created 2016-12-13 上午10:59:28
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBsNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" AND D.DEF_KEY IN ( ");
        sql.append(" select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='ssdjlc' ");
        sql.append(" ) and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "并审汇总", "并审汇总" }, null);
        
        return datas;
    }
    

    /**
     * 
     * 描述   预审意见汇总自动跳转
     * @author Danto Huang
     * @created 2016-11-17 上午10:22:56
     * @param data
     */
    public void jumpTaskForYjhz(Map<String, Object> data) throws Exception {
        Map<String, Object> flowVars = new HashMap<String, Object>();
        String defId = (String) data.get("DEF_ID");
        int flowVersion = Integer.parseInt(data.get("DEF_VERSION").toString());
        flowVars.put("EFLOW_EXEID", data.get("EXE_ID"));
        flowVars.put("EFLOW_DEFKEY", data.get("DEF_KEY"));
        flowVars.put("EFLOW_DEFID", data.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION", data.get("DEF_VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", data.get("CUR_STEPNAMES"));
        flowVars.put("EFLOW_CURUSEROPERNODENAME", data.get("CUR_STEPNAMES"));
        flowVars.put("ITEM_CODE", data.get("ITEM_CODE"));
        flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
        flowVars.put("EFLOW_CURRENTTASK_ID", data.get("TASK_ID"));
        String nextStepJson = this.jbpmService.getNextStepsJson(defId, flowVersion, data.get("CUR_STEPNAMES")
                .toString(), flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
            jbpmService.doFlowJob(flowVars);
        }else if("办结自动跳转".equals(data.get("CUR_STEPNAMES").toString())||
                "审查与决定自动跳转".equals(data.get("CUR_STEPNAMES").toString())){
            jbpmService.doFlowJob(flowVars);
        }
    }
    /**
     * 
     * 描述   自动退件
     * @author Kester Chen
     * @created 2020年8月25日 下午3:21:28
     * @param data
     * @throws Exception
     */
    public void jumpTaskForZdtj(Map<String, Object> data) throws Exception {
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            String exeId = (String) data.get("EXE_ID");
            variables.put("EXE_ID", exeId);
            if(StringUtils.isNotEmpty(StringUtil.getString(data.get("TASK_ID")))){
                variables.put("EFLOW_CURRENTTASK_ID", StringUtil.getString(data.get("TASK_ID")));
            }   
            if(StringUtils.isNotEmpty(StringUtil.getString(data.get("ASSIGNER_NAME"))) 
                    && StringUtils.isNotEmpty(StringUtil.getString(data.get("ASSIGNER_CODE")))){
                variables.put("ASSIGNER_NAME", StringUtil.getString(data.get("ASSIGNER_NAME")));
                variables.put("ASSIGNER_CODE", StringUtil.getString(data.get("ASSIGNER_CODE")));
            }
//            Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
//                    new Object[] { exeId });
//            String sqfs = (String) flowExe.get("SQFS");
            // 获取意见内容
            String EFLOW_HANDLE_OPINION = "签章超期未通过自动退件";
            //executionService.updateExeToNotAccept(exeId, EFLOW_HANDLE_OPINION, variables);
            executionService.updateExeToNotAcceptForAuto(exeId, EFLOW_HANDLE_OPINION, variables);
        } catch (Exception e) {
            log.info(e.getMessage());
//            e.printStackTrace();
        }
    }

    /**
     * 描述:住房公积金申请表格数据
     *
     * @author Madison You
     * @created 2020/11/9 15:33:00
     * @param
     * @return
     */
    @Override
    public Map<String,Object> zfgjjsqTableData(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String, Object> exeMap = args.get("exeMap");
        String exeId = StringUtil.getValue(exeMap, "EXE_ID");
        Map<String, Object> busMap = exeDataService.getBuscordMap(exeId);
        busMap.putAll(exeDataService.getHhLegal(exeId));
        returnMap.put("dwqc", StringUtil.getValue(busMap, "COMPANY_NAME"));
        returnMap.put("gjjlx", ktStampService.getExcelSelectField("GJJLX", StringUtil.getValue(busMap, "FUNDS_GJJLX")));
        returnMap.put("zzjgdm", StringUtil.getValue(busMap,"SOCIAL_CREDITUNICODE"));
        returnMap.put("dwdz", StringUtil.getValue(busMap, "REGISTER_ADDR"));
        returnMap.put("dwclrq", "");
        returnMap.put("dwfxr", StringUtil.getValue(busMap, "FUNDS_GJJDWFXR"));
        returnMap.put("lsgx", ktStampService.getExcelSelectField("GJJLSGX", StringUtil.getValue(busMap, "FUNDS_GJJLSGX")));
        returnMap.put("dwxz", ktStampService.getExcelSelectField("GJJDWXZ", StringUtil.getValue(busMap, "FUNDS_GJJDWXZ")));
        returnMap.put("jjlx", ktStampService.getExcelSelectField("GJJJJLX", StringUtil.getValue(busMap, "FUNDS_GJJJJLX")));
        returnMap.put("hyfl", ktStampService.getExcelSelectField("GJJHYFL", StringUtil.getValue(busMap, "FUNDS_GJJHYFL")));
        String gjjlx = StringUtil.getValue(busMap, "FUNDS_GJJLX");
        if (StringUtils.isNotEmpty(gjjlx) && gjjlx.equals("ZFGJJ")) {
            returnMap.put("gjjdwjcbl", StringUtil.getValue(busMap, "FUNDS_GJJDWJCBL") + "%");
            returnMap.put("gjjgrjcbl", StringUtil.getValue(busMap, "FUNDS_GJJGRJCBL") + "%");
            returnMap.put("gjjjcrs", StringUtil.getValue(busMap, "FUNDS_GJJJCRS") + "个");
            returnMap.put("gjjgzze", StringUtil.getValue(busMap, "FUNDS_GJJGJZE") + "元");
            returnMap.put("gjjyjcze", StringUtil.getValue(busMap, "FUNDS_GJJYJCZE") + "元");
            returnMap.put("btjcrs", "");
            returnMap.put("btyjcze", "");
            returnMap.put("btycxffrs", "");
            returnMap.put("btycxffje", "");
        } else {
            returnMap.put("gjjdwjcbl", "");
            returnMap.put("gjjgrjcbl", "");
            returnMap.put("gjjjcrs", "");
            returnMap.put("gjjgzze", "");
            returnMap.put("gjjyjcze", "");
            returnMap.put("btjcrs", StringUtil.getValue(busMap, "FUNDS_GJJBTJCRS") + "个");
            returnMap.put("btyjcze", StringUtil.getValue(busMap, "FUNDS_GJJBTYJCZE") + "元");
            returnMap.put("btycxffrs", StringUtil.getValue(busMap, "FUNDS_GJJBTYCXFFRS") + "个");
            returnMap.put("btycxffje", StringUtil.getValue(busMap, "FUNDS_GJJBTYCXFFJE") + "元");
        }
        returnMap.put("fddbrxm", StringUtil.getValue(busMap, "LEGAL_NAME"));
        returnMap.put("fddbrzjhm", StringUtil.getValue(busMap, "LEGAL_IDNO"));
        returnMap.put("fddbrzjlx", ktStampService.getExcelSelectField("GJJZJLX",
                StringUtil.getValue(busMap, "LEGAL_IDTYPE"),"QT"));
        returnMap.put("jbrxm", StringUtil.getValue(busMap, "OPERATOR_NAME"));
        returnMap.put("jbrzjhm", StringUtil.getValue(busMap, "OPERATOR_IDNO"));
        returnMap.put("jbrzjlx", ktStampService.getExcelSelectField("GJJZJLX",
                StringUtil.getValue(busMap, "OPERATOR_IDTYPE"),"QT"));
        returnMap.put("frsjhm", StringUtil.getValue(busMap, "LEGAL_MOBILE"));
        returnMap.put("jbrsjhm", StringUtil.getValue(busMap, "OPERATOR_MOBILE"));
        returnMap.put("zczj", StringUtil.getValue(busMap, "INVESTMENT") + "万元");
        returnMap.put("styh", dictionaryService.getDicNames("GJJSTYH", StringUtil.getValue(busMap, "FUNDS_GJJSTYH")));
        returnMap.put("qjny", StringUtil.getValue(busMap, "FUNDS_GJJQJNY"));
        returnMap.put("dwdzyx", StringUtil.getValue(busMap, "FUNDS_GJJDWDZYX"));
        returnMap.put("tbrq", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return returnMap;
    }


    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:42:06
     * @param parentCode
     * @return
     */
    public List<Map<String,Object>> getIndustryByParentCode(String parentCode){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.indu_id,t.indu_name,t.indu_code,t.remark from T_WSBS_INDUSTRY t ");
        sql.append("where t.parent_id=(select s.indu_id from T_WSBS_INDUSTRY s where s.indu_code=? ) ");
        return dao.findBySql(sql.toString(), new Object[]{parentCode}, null);
    }
    

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:42:06
     * @param parentCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findTZIndustryBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.indu_id,t.indu_name,t.indu_code,t.remark from T_WSBS_INDUSTRY t where 1=1 ");

        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述   商事预审自动审核通过
     * @author Danto Huang
     * @created 2016-12-8 下午3:34:57
     * @param flowVars
     * @return
     */
    public Map<String, Object> preAuditAutoPass(Map<String,Object> flowVars) throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        Map<String,Object> exeMap = this.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{exeId});
        String defId = (String) exeMap.get("DEF_ID");
        int flowVersion = Integer.parseInt(exeMap.get("DEF_VERSION").toString());
        String EFLOW_JOINPRENODENAMES = "";
        if(flowVars.get("EFLOW_DEFKEY").equals("ss002")){
            EFLOW_JOINPRENODENAMES = "工商预审,质监预审,国税预审,地税预审,社保预审,统计预审,商务处预审,交建预审";
        }else{
            EFLOW_JOINPRENODENAMES = "工商预审,质监预审,国税预审,地税预审,社保预审,统计预审,交建预审";
        }
        if(this.isGsGsAudited(exeId)){
            List<Map<String, Object>> sameFromTasks = this
                    .findSameFromTasks(flowVars.get("EFLOW_CURRENTTASK_ID").toString());
            if(sameFromTasks!=null&&sameFromTasks.size()>0){
                for(int i=0;i<sameFromTasks.size();i++){
                    Map<String,Object> task = sameFromTasks.get(i);
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("EFLOW_EXEID", task.get("EXE_ID"));
                    data.put("EFLOW_DEFKEY", flowVars.get("EFLOW_DEFKEY"));
                    data.put("EFLOW_DEFID", defId);
                    data.put("EFLOW_DEFVERSION", flowVersion);
                    data.put("EFLOW_ISQUERYDETAIL", "false");
                    data.put("EFLOW_ISTEMPSAVE", "-1");
                    data.put("EFLOW_CUREXERUNNINGNODENAMES", task.get("TASK_NODENAME"));
                    data.put("EFLOW_CURUSEROPERNODENAME", task.get("TASK_NODENAME"));
                    data.put("ITEM_CODE", flowVars.get("ITEM_CODE"));
                    data.put("EFLOW_INVOKEBUSSAVE", "-1");
                    data.put("EFLOW_CURRENTTASK_ID", task.get("TASK_ID"));
                    data.put("IS_PASS", "1");
                    data.put("EFLOW_JOINPRENODENAMES", EFLOW_JOINPRENODENAMES);
                    String nextStepJson = this.jbpmService.getNextStepsJson(
                            defId, flowVersion, task.get("TASK_NODENAME").toString(), data);
                    if (StringUtils.isNotEmpty(nextStepJson)) {
                        data.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                        jbpmService.doFlowJob(data);
                    }
                }
            }
        }
        
        return flowVars;
    }
    
    /**
     * 
     * 描述   判断商事工商预审和国税预审已完成
     * @author Danto Huang
     * @created 2016-12-8 下午4:00:32
     * @param exeId
     * @return
     */
    public boolean isGsGsAudited(String exeId){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from JBPM6_TASK t where t.exe_id=? ");
        sql.append("and t.task_nodename in('工商预审','国税预审','商务处预审') and t.task_status=1 ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{exeId}, null);
        if(list!=null&&list.size()>0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 
     * 描述   获取同源在办任务
     * @author Danto Huang
     * @created 2016-12-8 下午4:34:48
     * @param taskId
     * @return
     */
    public List<Map<String,Object>> findSameFromTasks(String taskId){
        StringBuffer sql = new StringBuffer("select T.* from JBPM6_TASK t WHERE T.FROMTASK_IDS=");
        sql.append("(SELECT S.FROMTASK_IDS FROM JBPM6_TASK S WHERE S.TASK_ID=?) AND T.TASK_STATUS=1 ");
        sql.append("and t.is_audited='1' ");
        return dao.findBySql(sql.toString(), new Object[]{taskId}, null);
    }

    /**
     * 
     * 描述   核准日期
     * @author Danto Huang
     * @created 2017-1-4 下午2:40:54
     * @param flowVars
     * @return
     */
    public Map<String, Object> approvalDate(Map<String,Object> flowVars){
        flowVars.put("APPROVAL_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return flowVars;
    }
    
    @Override
    public Map<String, Object> getEntInfo(Map<String, Object> flowVars) throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        getEicpEntInfo(exeId);
        return flowVars;
    }

    public void getEicpEntInfo(String exeId) {
        Map<String,Object> returnInfo = null;
        returnInfo = dao.getByJdbc("T_COMMERCIAL_ECIPRESPONSE",new String[]{"EXE_ID"},
                new Object[]{exeId});
        if (null == returnInfo) {
            //获取事项信息
            Map<String,Object> flowExe = null;
            flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"}, new Object[]{exeId});
            //获取企业信息
            //BUS_RECORDID 业务记录ID  BUS_TABLENAME 业务记录表名
            String busRecordid = flowExe.get("BUS_RECORDID").toString();
            String busTablename = flowExe.get("BUS_TABLENAME").toString();
            Map<String,Object> entInfo = null;

            if ("T_COMMERCIAL_FOREIGN".equals(busTablename)) {
                busTablename = "T_COMMERCIAL_COMPANY";
                entInfo = dao.getByJdbc(busTablename,new String[]{"COMPANY_ID"}, new Object[]{busRecordid});
            }
            if ("T_COMMERCIAL_DOMESTIC".equals(busTablename)) {
                busTablename = "T_COMMERCIAL_COMPANY";
                entInfo = dao.getByJdbc(busTablename,new String[]{"COMPANY_ID"}, new Object[]{busRecordid});
            }
            if ("T_COMMERCIAL_SOLELYINVEST".equals(busTablename)) {
                entInfo = dao.getByJdbc(busTablename,new String[]{"SOLELY_ID"}, new Object[]{busRecordid});
                entInfo.put("SOCIAL_CREDITUNICODE", entInfo.get("CREDIT_CODE")==null?
                        "":entInfo.get("CREDIT_CODE").toString());
            }
            if ("T_COMMERCIAL_INDIVIDUAL".equals(busTablename)) {
                entInfo = dao.getByJdbc(busTablename,new String[]{"INDIVIDUAL_ID"}, new Object[]{busRecordid});
                entInfo.put("SOCIAL_CREDITUNICODE", entInfo.get("CREDIT_CODE")==null?
                        "":entInfo.get("CREDIT_CODE").toString());
            }
            //生成同步数据
            EcipEntInfoManagementServiceSoapBindingStub stub;
            try {
                stub = new EcipEntInfoManagementServiceSoapBindingStub();
                String username = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSJK_USERNAME");
                String password = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSJK_PASSWORD");
                stub.setUsername(username);
                stub.setPassword(password);

                EntCompositeInfo item = hangInfo(flowExe, entInfo);
                try {
                    EntCompositeInfo[]  items =  new EntCompositeInfo[1];
                    items[0] = item;
//                    List list = new ArrayList(Arrays.asList(items));
                    EcipResponse ecr = stub.updateEntInfo(items);
//                    log.info("getDataSet:"+ecr.getDataSet()[0].getValue());
//                    log.info("getResultCode:"+ecr.getResultCode());
//                    log.info("getErrorMsg:"+ecr.getErrorMsg());
                    //更新 T_COMMERCIAL_ECIPRESPONSE 信息
                    Map<String,Object> map = new HashMap<String, Object>();
//                    log.info(exeId);
                    map.put("EXE_ID", exeId);
                    map.put("RESULT_CODE", ecr.getResultCode()==null?
                            "":ecr.getResultCode().toString());
                    map.put("ERROR_MSG", ecr.getErrorMsg()==null?
                            "":ecr.getErrorMsg().toString());
                    String pUrl="";
                    if(ecr.getReCode()!=null){
                        EcipResponseReCodeEntry[] entCodes = ecr.getReCode();
                        pUrl = entCodes[0].getKey();
                        pUrl = entCodes[0].getValue();
                    }
                    String photoUrl = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSJK_PHOTOURL");
                    String photoReplace = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSJK_PHOTOREPLACE");
                    String photoString = pUrl.replace(photoUrl, photoReplace);
                    map.put("QRCODE_URL", photoString);
                    dao.saveOrUpdate(map, "T_COMMERCIAL_ECIPRESPONSE", null);
                } catch (RemoteException e) {
                    log.error(e.getMessage());
                }
            
            } catch (AxisFault e) {
            }
        }
    }
    /**
     * 
     * 描述：定时器发送商事信息到信用平台
     * @author Water Guo
     * @created 2017-12-26 下午2:40:56
     * @see net.evecom.platform.bsfw.service.CommercialService#sendToCreditByTimer()
     */
    public void sendToCreditByTimer(){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT E.EXE_ID FROM JBPM6_EXECUTION E LEFT JOIN JBPM6_FLOWDEF F ON  ");
        sql.append(" E.DEF_ID=F.DEF_ID WHERE E.RUN_STATUS='2' AND");
        sql.append(" (F.DEF_KEY='ss001' OR F.DEF_KEY='ss002') AND  NOT EXISTS");
        sql.append(" (SELECT C.EXE_ID FROM T_COMMERCIAL_ECIPRESPONSE C ");
        sql.append(" WHERE C.EXE_ID=E.EXE_ID) AND ROWNUM<=100");
        sql.append(" ORDER BY E.CREATE_TIME ASC");
        List<Map<String,Object>> list=dao.findBySql(sql.toString(), null, null);
        for(Map<String,Object> map:list){
            String exeId=(String)map.get("EXE_ID");
            getEicpEntInfo(exeId);
        }
    }
    private EntCompositeInfo hangInfo(Map<String, Object> flowExe, Map<String, Object> entInfo) {
        EntCompositeInfo item = new EntCompositeInfo();
        EntManager managerInfo = new EntManager();
        EntSupervisor entSupervisorInfo = new EntSupervisor();
        EntTrustee entTrusteeInfo = new EntTrustee();
        EntStockHolder entStockHolderInfo = new EntStockHolder();

           //matterId  事项ID socialCreditUnicode 统一社会信用代码 entName   企业名称
        item.setMatterId(flowExe.get("EXE_ID")==null?
                "":flowExe.get("EXE_ID").toString());
        item.setSocialCreditUnicode(entInfo.get("SOCIAL_CREDITUNICODE")==null?
                "":entInfo.get("SOCIAL_CREDITUNICODE").toString());//??
        item.setEntName(entInfo.get("COMPANY_NAME")==null?
                "":entInfo.get("COMPANY_NAME").toString());
        //approvalDate  核准日期;  entType   企业类型;  dom   住所;  address 地址
        item.setApprovalDate(entInfo.get("APPROVAL_DATE")==null?
                "":entInfo.get("APPROVAL_DATE").toString());//??
        item.setEntType(entInfo.get("COMPANY_TYPE")==null?
                "":entInfo.get("COMPANY_TYPE").toString()); 
        item.setDom(entInfo.get("BUSSINESS_ADDR")==null?
                "":entInfo.get("BUSSINESS_ADDR").toString());
        //regCap    注册资本;  opExpires    经营期限;  opScope  经营范围
        BigDecimal volumn = new BigDecimal(entInfo.get("REGISTERED_CAPITAL")==null?
                "0":entInfo.get("REGISTERED_CAPITAL").toString());
        item.setRegCap(volumn);
        item.setOpExpires(entInfo.get("BUSSINESS_YEARS")==null?
                "":entInfo.get("BUSSINESS_YEARS").toString());
        item.setOpScope(entInfo.get("BUSSINESS_SCOPE")==null?
                "":entInfo.get("BUSSINESS_SCOPE").toString());
        //lerep 法定代表人;  lerepMobile 法定代表人手机;  operator  经办人 ;operatorMobile    经办人手机;
        item.setLerep(entInfo.get("LEGAL_NAME")==null?
                "":entInfo.get("LEGAL_NAME").toString());
        if (entInfo.get("LEGAL_MOBILE")!=null) {
            item.setLerepMobile(entInfo.get("LEGAL_MOBILE")==null?
                    "":entInfo.get("LEGAL_MOBILE").toString());
        }
        item.setOperator(entInfo.get("OPERATOR_NAME")==null?
                "":entInfo.get("OPERATOR_NAME").toString());
        if (entInfo.get("OPERATOR_MOBILE")!=null) {
            item.setOperatorMobile(entInfo.get("OPERATOR_MOBILE")==null?
                    "":entInfo.get("OPERATOR_MOBILE").toString());
        }
        //经理信息（EntManager）：
        //personName    姓名;  cerType    证件类型;  cerNo    证件号码
        String managerString = entInfo.get("MANAGER_JSON")==null?
                "":entInfo.get("MANAGER_JSON").toString();
        if (!StringUtils.isEmpty(managerString)) {
            List<Map<String, Object>> managerList = JSON.parseObject(managerString, List.class);
            EntManager[] managerInfos = new EntManager[managerList.size()];
            for(int i=0;i<managerList.size();i++){
                Map<String, Object> managerMap = managerList.get(i);
                managerInfo.setCerNo(managerMap.get("MANAGER_IDNO")==null?
                        "":managerMap.get("MANAGER_IDNO").toString());
                managerInfo.setPersonName(managerMap.get("MANAGER_NAME")==null?
                        "":managerMap.get("MANAGER_NAME").toString());
                managerInfo.setCerType(managerMap.get("MANAGER_IDTYPE")==null?
                        "":managerMap.get("MANAGER_IDTYPE").toString());
                managerInfos[i] = managerInfo;
            }
            item.setManagers(managerInfos);
        }
        //监事信息（EntSupervisor）：
        //personName    姓名;  cerType    证件类型;  cerNo    证件号码
        String entSupervisorString = entInfo.get("SUPERVISOR_JSON")==null?
                "":entInfo.get("SUPERVISOR_JSON").toString();
        if (!StringUtils.isEmpty(entSupervisorString)) {
            List<Map<String, Object>> entSupervisorList = JSON.parseObject(entSupervisorString, List.class);
            EntSupervisor[] entSupervisorInfos = new EntSupervisor[entSupervisorList.size()];
            for(int i=0;i<entSupervisorList.size();i++){
                Map<String, Object> entSupervisorMap = entSupervisorList.get(i);
                entSupervisorInfo.setPersonName(entSupervisorMap.get("SUPERVISOR_NAME")==null?
                        "":entSupervisorMap.get("SUPERVISOR_NAME").toString());
                entSupervisorInfo.setCerType(entSupervisorMap.get("SUPERVISOR_IDTYPE")==null?
                        "":entSupervisorMap.get("SUPERVISOR_IDTYPE").toString());
                entSupervisorInfo.setCerNo(entSupervisorMap.get("SUPERVISOR_IDNO")==null?
                        "":entSupervisorMap.get("SUPERVISOR_IDNO").toString());
                entSupervisorInfos[i] = entSupervisorInfo;
            }
            item.setSupervisors(entSupervisorInfos);
        }
        //董事信息（EntTrustee）：
        //personName    姓名;  cerType    证件类型;  cerNo    证件号码
//                Map<String, Object> m = JsonUtil.parseJSON2Map(name);
//                boolean result = (Boolean) m.get("result");
        String entTrusteesString = entInfo.get("DIRECTOR_JSON")==null?
                "":entInfo.get("DIRECTOR_JSON").toString();
        if (!StringUtils.isEmpty(entTrusteesString)) {
            List<Map<String, Object>> entTrusteesList = JSON.parseObject(entTrusteesString, List.class);
            EntTrustee[] entTrusteeInfos = new EntTrustee[entTrusteesList.size()];
            for(int i=0;i<entTrusteesList.size();i++){
                Map<String, Object> entTrusteemMap = entTrusteesList.get(i);
                entTrusteeInfo.setPersonName(entTrusteemMap.get("DIRECTOR_NAME")==null?
                        "":entTrusteemMap.get("DIRECTOR_NAME").toString());
                entTrusteeInfo.setCerType(entTrusteemMap.get("DIRECTOR_IDTYPE")==null?
                        "":entTrusteemMap.get("DIRECTOR_IDTYPE").toString());
                entTrusteeInfo.setCerNo(entTrusteemMap.get("DIRECTOR_IDNO")==null?
                        "":entTrusteemMap.get("DIRECTOR_IDNO").toString());
                entTrusteeInfos[i] = entTrusteeInfo;
            }
            item.setTrustees(entTrusteeInfos);
        }
        hangESInfo(entInfo, item, entStockHolderInfo);
        return item;
    }
    private void hangESInfo(Map<String, Object> entInfo, EntCompositeInfo item, EntStockHolder entStockHolderInfo) {
        //股东信息（EntStockHolder）：
        //personName    姓名;  cerType    证件类型;  cerNo    证件号码
        //linkTel   联系电话;  impAm    出资总额
        String entStockHolderString = entInfo.get("HOLDER_JSON")==null?
                "":entInfo.get("HOLDER_JSON").toString();
        if (!StringUtils.isEmpty(entStockHolderString)) {
            List<Map<String, Object>> entStockHolderList = JSON.parseObject(entStockHolderString, List.class);
            EntStockHolder[] entStockHolderInfos = new EntStockHolder[entStockHolderList.size()];
            for(int i=0;i<entStockHolderList.size();i++){
                Map<String, Object> entStockHolderMap = entStockHolderList.get(i);
                entStockHolderInfo.setPersonName(entStockHolderMap.get("SHAREHOLDER_NAME")==null?
                        "":entStockHolderMap.get("SHAREHOLDER_NAME").toString());
                entStockHolderInfo.setCerType(entStockHolderMap.get("LICENCE_TYPE")==null?
                        "":entStockHolderMap.get("LICENCE_TYPE").toString());
                entStockHolderInfo.setCerNo(entStockHolderMap.get("LICENCE_NO")==null?
                        "":entStockHolderMap.get("LICENCE_NO").toString());
                if (entStockHolderMap.get("CONTACT_WAY")!=null) {
                    entStockHolderInfo.setLinkTel(entStockHolderMap.get("CONTACT_WAY")==null?
                            "":entStockHolderMap.get("CONTACT_WAY").toString());
                }
                BigDecimal impAm = new BigDecimal(entStockHolderMap.get("CONTRIBUTIONS")==null?
                        "":entStockHolderMap.get("CONTRIBUTIONS").toString());
                entStockHolderInfo.setImpAm(impAm);
                entStockHolderInfos[i] = entStockHolderInfo;
            }
            item.setStockHolders(entStockHolderInfos);
        }
    }

    /**
     * 
     * 描述   获取字段审核记录
     * @author Danto Huang
     * @created 2017-1-9 下午2:25:28
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findFieldOpinion(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_FIELDAUDIT t ");
        sql.append("WHERE 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述   更新字段审核记录状态
     * @author Danto Huang
     * @created 2017-1-9 下午3:23:00
     * @param flowVars
     * @return
     */
    public Map<String,Object> updateFieldAutitStatus(Map<String,Object> flowVars){
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        String sql = "update T_COMMERCIAL_FIELDAUDIT t set t.CONFIRM_STATUS='1' "
                + "where t.CONFIRM_STATUS='0' and t.EXE_ID=?";
        this.dao.executeSql(sql, new Object[] { exeId });   
        
        String lineId = (String)flowVars.get("lineId");
        SysUser curLoginUser = null;
        try {
            curLoginUser =  AppUtil.getLoginUser();
        } catch (Exception e) {
            // 省下发事项无当前登录人
            return flowVars;
        }
        if(curLoginUser!=null){
            Map<String,Object> variable = new HashMap<String, Object>();
            variable.put("CALL_STATUS", "1");
            variable.put("OPERATOR", AppUtil.getLoginUser().getFullname());
            variable.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
            variable.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variable.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
            if(StringUtils.isNotEmpty(lineId)){
                Map<String, Object> queue = this.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                        new Object[] { lineId });
                if(queue!=null){
                    variable.put("OPERATOR_NAME", AppUtil.getLoginUser().getFullname());
                    this.dao.saveOrUpdate(variable, "T_CKBS_QUEUERECORD", lineId);
                }else{
                    this.dao.saveOrUpdate(variable, "T_CKBS_NUMRECORD", lineId);
                }
            }
            
            try {
                updateOperatorId(flowVars);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage(),e);
            }
        }
        return flowVars;
    }
    /**
     * 
     * 描述   是否是窗口办理，如果是窗口办理则更新办理人员信息，用户个人绩效统计
     * @author Danto Huang
     * @created 2016-12-8 下午4:00:32
     * @param exeId
     * @return
     */
    @Override
    public Map<String, Object> updateOperatorId(Map<String, Object> flowVars) throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        String hjmc = "";
        if(flowVars.get("HJMC")!=null){
            hjmc=flowVars.get("HJMC").toString();
        }
        if("窗口办理".equals(hjmc)){
        //获取当前登录用户信息
            SysUser sysUser = AppUtil.getLoginUser();
            String eflowCreatorid = sysUser.getUserId();
            int isExist = sysUserDao.isExistsUserById(eflowCreatorid);
            if (1==isExist) {
                Map<String,Object> flowExe = null;
                if(StringUtils.isNotEmpty(exeId)){
                    flowExe = dao.getByJdbc("JBPM6_EXECUTION",new String[]{"EXE_ID"}, new Object[]{exeId});
                    flowExe.put("OPERATOR_ID",eflowCreatorid);
                    dao.saveOrUpdate(flowExe, "JBPM6_EXECUTION", exeId);
                }
            }
        }
        return flowVars;
    }
    /**
     * 
     * 描述   判断审批是否已完成
     * @author Danto Huang
     * @created 2016-12-8 下午4:00:32
     * @param exeId
     * @return
     */
    public boolean isAudited(String exeId){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from JBPM6_TASK t where t.exe_id=? ");
        sql.append("and t.task_nodename in('公安审批') and t.task_status=1 ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{exeId}, null);
        if(list!=null&&list.size()>0){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 
     * 描述   商事审批自动审核通过
     * @author Danto Huang
     * @created 2016-12-8 下午3:34:57
     * @param flowVars
     * @return
     */
    public Map<String, Object> auditAutoPass(Map<String,Object> flowVars) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        Map<String,Object> exeMap = this.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{exeId});
        String defId = (String) exeMap.get("DEF_ID");
        int flowVersion = Integer.parseInt(exeMap.get("DEF_VERSION").toString());
        String EFLOW_JOINPRENODENAMES = "";
        if(flowVars.get("EFLOW_DEFKEY").equals("ss002")){
            EFLOW_JOINPRENODENAMES = "质监审批,国税审批,地税审批,社保审批,统计审批,公安审批,商务审批";
        }else{
            EFLOW_JOINPRENODENAMES = "质监审批,国税审批,地税审批,社保审批,统计审批,公安审批";
        }
        if(this.isAudited(exeId)){
            List<Map<String, Object>> sameFromTasks = this
                    .findSameFromTasks(flowVars.get("EFLOW_CURRENTTASK_ID").toString());
            if(sameFromTasks!=null&&sameFromTasks.size()>0){
                for(int i=0;i<sameFromTasks.size();i++){
                    Map<String,Object> task = sameFromTasks.get(i);
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("EFLOW_EXEID", task.get("EXE_ID"));
                    data.put("EFLOW_DEFKEY", flowVars.get("EFLOW_DEFKEY"));
                    data.put("EFLOW_DEFID", defId);
                    data.put("EFLOW_DEFVERSION", flowVersion);
                    data.put("EFLOW_ISQUERYDETAIL", "false");
                    data.put("EFLOW_ISTEMPSAVE", "-1");
                    data.put("EFLOW_CUREXERUNNINGNODENAMES", task.get("TASK_NODENAME"));
                    data.put("EFLOW_CURUSEROPERNODENAME", task.get("TASK_NODENAME"));
                    data.put("ITEM_CODE", flowVars.get("ITEM_CODE"));
                    data.put("EFLOW_INVOKEBUSSAVE", "-1");
                    data.put("EFLOW_CURRENTTASK_ID", task.get("TASK_ID"));
                    data.put("IS_PASS", "1");
                    data.put("EFLOW_JOINPRENODENAMES", EFLOW_JOINPRENODENAMES);
                    String nextStepJson = this.jbpmService.getNextStepsJson(
                            defId, flowVersion, task.get("TASK_NODENAME").toString(), data);
                    if (StringUtils.isNotEmpty(nextStepJson)) {
                        data.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                        jbpmService.doFlowJob(data);
                    }
                }
            }
        }
        
        return flowVars;
    }
    @Override
    public void updateField(String exeId, String fieldName, String curNode) {
        // TODO Auto-generated method stub
        this.dao.executeSql("update T_COMMERCIAL_FIELDAUDIT t set t.isdelete = 1"
                + " where t.exe_id = ?  and t.field_name = ? "
                , new Object[] { exeId, fieldName});
    }

    @Override
    public Map<String, Object> zfgjjsqTableDataNew(Map<String, Map<String, Object>> args) {
        // TODO Auto-generated method stub
        Map<String, Object> exeMap = args.get("exeMap");
        String exeId = StringUtil.getValue(exeMap, "EXE_ID");
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        busRecord.putAll(exeMap);
        String FUNDS_NAME = StringUtil.getString(busRecord.get("FUNDS_NAME"));// 经办人名称
        String FUNDS_MOBILE = StringUtil.getString(busRecord.get("FUNDS_MOBILE"));// 经办人手机号码
        String FUNDS_FIXEDLINE = StringUtil.getString(busRecord.get("FUNDS_FIXEDLINE"));// 经办人固定电话
        String FUNDS_IDTYPE = StringUtil.getString(busRecord.get("FUNDS_IDTYPE"));// 经办人证件类型
        String FUNDS_IDNO = StringUtil.getString(busRecord.get("FUNDS_IDNO"));// 经办人证件号码
        busRecord.put("FUNDS_NAME",
                StringUtils.isEmpty(FUNDS_NAME) ? StringUtil.getString(busRecord.get("JBR_NAME")) : FUNDS_NAME);
        busRecord.put("FUNDS_MOBILE",
                StringUtils.isEmpty(FUNDS_MOBILE) ? StringUtil.getString(busRecord.get("JBR_MOBILE")) : FUNDS_MOBILE);
        busRecord.put("FUNDS_FIXEDLINE", StringUtils.isEmpty(FUNDS_FIXEDLINE)
                ? StringUtil.getString(busRecord.get("JBR_LXDH")) : FUNDS_FIXEDLINE);
        busRecord.put("FUNDS_IDTYPE",
                StringUtils.isEmpty(FUNDS_IDTYPE) ? StringUtil.getString(busRecord.get("JBR_ZJLX")) : FUNDS_IDTYPE);
        busRecord.put("FUNDS_IDNO",
                StringUtils.isEmpty(FUNDS_IDNO) ? StringUtil.getString(busRecord.get("JBR_ZJHM")) : FUNDS_IDNO);
        setGjjxx(busRecord);

        String busTablename = busRecord.get("BUS_TABLENAME").toString();
        if(busTablename.equals("T_COMMERCIAL_SOLELYINVEST")){
            busRecord.put("REGISTER_ADDR", busRecord.get("COMPANY_ADDR"));
            busRecord.put("LEGAL_NAME", "");
            busRecord.put("LEGAL_MOBILE", "");
            busRecord.put("LEGAL_IDNO", "");
        }else if(busTablename.equals("T_COMMERCIAL_BRANCH")){
            busRecord.put("COMPANY_NAME", busRecord.get("BRANCH_NAME"));
            busRecord.put("POST_CODE", busRecord.get("POSTCODE"));
        }else if(busTablename.equals("T_COMMERCIAL_INTERNAL_STOCK")){
            busRecord.put("POST_CODE", busRecord.get("POSTAL_CODE"));
        }
        
        return busRecord;
    }

    /**
     * 
     * 描述 设置公积金信息
     * 
     * @author Rider Chen
     * @created 2021年6月29日 上午10:55:58
     * @param busRecord
     */
    private void setGjjxx(Map<String, Object> busRecord) {
        try {

            String ISFUNDSREGISTER = StringUtil.getString(busRecord.get("ISFUNDSREGISTER"));// 是否办理公积金登记
            String FUNDS_GJJLX = StringUtil.getString(busRecord.get("FUNDS_GJJLX"));// 类型
            String FUNDS_GJJSTYH = StringUtil.getString(busRecord.get("FUNDS_GJJSTYH"));// 受托银行
            String FUNDS_GJJLSGX = StringUtil.getString(busRecord.get("FUNDS_GJJLSGX"));// 隶属关系
            String FUNDS_GJJDWXZ = StringUtil.getString(busRecord.get("FUNDS_GJJDWXZ"));// 单位性质
            String FUNDS_GJJJJLX = StringUtil.getString(busRecord.get("FUNDS_GJJJJLX"));// 经济类型
            String FUNDS_GJJHYFL = StringUtil.getString(busRecord.get("FUNDS_GJJHYFL"));// 行业分类
            String FUNDS_GJJDWLX = StringUtil.getString(busRecord.get("FUNDS_GJJDWLX"));// 单位类型
            String FUNDS_DWSCHJSJ = StringUtil.getString(busRecord.get("FUNDS_DWSCHJSJ"));// 单位首次汇缴时间
            String LEGAL_IDTYPE = StringUtil.getString(busRecord.get("LEGAL_IDTYPE"));// 法人证件类型
            String LEGAL_MOBILE = StringUtil.getString(busRecord.get("LEGAL_MOBILE"));// 法人证件电话号码

            String FUNDS_IDTYPE = StringUtil.getString(busRecord.get("FUNDS_IDTYPE"));// 经办人证件类型

            busRecord.put("LEGAL_MOBILE", LEGAL_MOBILE);
            if (StringUtils.isNotEmpty(FUNDS_GJJLX) && FUNDS_GJJLX.equals("ZFGJJ")) {
                busRecord.put("GJJYHJZE", StringUtil.getValue(busRecord, "FUNDS_GJJYJCZE"));
            } else {
                busRecord.put("GJJYHJZE", StringUtil.getValue(busRecord, "FUNDS_GJJBTYJCZE"));
            }
            // 判断类型是否办理公积金
            if (StringUtils.isNotEmpty(ISFUNDSREGISTER) && ISFUNDSREGISTER.equals("1")
                    && StringUtils.isNotEmpty(FUNDS_GJJLX)) {
                initDicCTSym(busRecord, FUNDS_GJJSTYH, "FUNDSGJJSTYH", "GJJSTYH", 2);// 受托银行
                initDicCTSym(busRecord, FUNDS_GJJLSGX, "FUNDSGJJLSGX", "GJJLSGX", 2);// 隶属关系
                initDicCTSym(busRecord, FUNDS_GJJDWXZ, "FUNDSGJJDWXZ", "GJJDWXZ", 2);// 单位性质
                initDicCTSym(busRecord, FUNDS_GJJJJLX, "FUNDSGJJJJLX", "GJJJJLX", 2);// 经济类型
                initDicCTSym(busRecord, FUNDS_GJJHYFL, "FUNDSGJJHYFL", "GJJHYFL", 2);// 行业分类
                initDicCTSym(busRecord, FUNDS_GJJDWLX, "FUNDSGJJDWLX", "GJJDWLX", 2);// 单位类型
                initDicCTSym(busRecord, FUNDS_IDTYPE, "FUNDSIDTYPE", "GJJZJLX", 2);// 经办人证件类型
                busRecord.put("FUNDSDWSCHJSJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("FUNDSDWSCHJSJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                if (StringUtils.isNotEmpty(FUNDS_DWSCHJSJ)) {
                    busRecord.put("FUNDSDWSCHJSJ" + FUNDS_DWSCHJSJ, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                }

                busRecord.put("LEGALIDTYPE01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("LEGALIDTYPE02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("LEGALIDTYPE03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("LEGALIDTYPE04", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("LEGALIDTYPE05", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                if (StringUtils.isNotEmpty(LEGAL_IDTYPE) && LEGAL_IDTYPE.equals("SF")) {
                    busRecord.put("LEGALIDTYPE01", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                } else if (StringUtils.isNotEmpty(LEGAL_IDTYPE) && LEGAL_IDTYPE.equals("JG")) {
                    busRecord.put("LEGALIDTYPE02", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                } else if (StringUtils.isNotEmpty(LEGAL_IDTYPE) && LEGAL_IDTYPE.equals("HZ")) {
                    busRecord.put("LEGALIDTYPE03", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                } else if (StringUtils.isNotEmpty(LEGAL_IDTYPE)) {
                    busRecord.put("LEGALIDTYPE05", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                }
                busRecord.put("TBRQ", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
    }

    /**
     * 
     * 描述 根据字典值设置选中
     * 
     * @author Rider Chen
     * @created 2021年6月29日 上午11:12:26
     * @param busRecord
     * @param FUNDS_GJJJJLX
     * @param cname
     * @param typeCode
     * @param formatLength
     * @throws Exception
     */
    private void initDicCTSym(Map<String, Object> busRecord, String value, String cname, String typeCode,
            int formatLength) throws Exception {
        List<Map<String, Object>> diclist = dictionaryService.findByTypeCode(typeCode);
        for (Map<String, Object> map : diclist) {
            String dicCode = StringUtil.getString(map.get("DIC_CODE"));
            busRecord.put(cname + StringUtil.getFormatNumber(formatLength, dicCode),
                    WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        }
        if (StringUtils.isNotEmpty(value)) {
            busRecord.put(cname + StringUtil.getFormatNumber(formatLength, value),
                    WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
    }
}
