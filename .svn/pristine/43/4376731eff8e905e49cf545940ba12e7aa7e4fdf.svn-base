/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.bdc.dao.BdcQlcMaterGenAndSignDao;
import net.evecom.platform.bdc.service.BdcQslyshbsyService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.bsfw.model.PtspFile;
import net.evecom.platform.bsfw.service.CreditService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import javax.annotation.Resource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述  不动产权属来源审核业务Service
 *
 * @author Allin Lin
 * @created 2020年8月15日 上午11:09:52
 */
@Service("bdcQslyshbsyService")
public class BdcQslyshbsyServiceImpl extends BaseServiceImpl implements BdcQslyshbsyService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcQslyshbsyServiceImpl.class);

    /**
     * 引入dao
     */
    @Resource
    private BdcQlcMaterGenAndSignDao dao;
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * creditService
     */
    @Resource
    private CreditService creditService;
    
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    
    /**
     * SysUserService
     */
    @Resource
    private SysUserService sysUserService;


    /**
     * 描述   覆盖获取实体dao方法
     *
     * @return
     * @author Allin Lin
     * @created 2020年8月15日 上午11:20:20
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    
    /**
     * 描述    判断当前环节是否已签署完成
     * @author Allin Lin
     * @created 2020年10月30日 上午9:45:19
     * @param exeId
     * @param curNodeName
     * @return
     */
    public AjaxJson isFinishSign(String exeId,String curNodeName){
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        String ywId = StringUtil.getString(busRecord.get("YW_ID"));
        Map<String, Object> signInfo = this.getByJdbc("T_BDC_QSLYSHBSY", 
                new String[]{"YW_ID","SIGN_CURNODENAME"}, new Object[]{ywId,curNodeName});
        if(signInfo!=null && "1".equals(StringUtil.getString(signInfo.get("SIGN_SIGNFLAG"))))
            ajaxJson.setSuccess(true);//已签署
        else {
            ajaxJson.setSuccess(false);//未签署
        }        
        return ajaxJson;
    }
    
    /**
     * 是否允许进行签章,并把状态置为其他人不可签章状态
     * @param exeId
     * @return
     */
    public AjaxJson isPermitSign(String exeId){
        AjaxJson ajaxJson = new AjaxJson();
        StringBuilder sf = new StringBuilder();
        sf.append(BdcQslyshbsyService.PRE_SIGN_STATUS).append(exeId);
        String synKey=sf.toString().intern();
        SysUser user = AppUtil.getLoginUser();
        String loginAccount="";
        if(user!=null){
            loginAccount = user.getUsername();//当前登录用户账号
        }
        synchronized (synKey) {
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String signStatus = StringUtil.getString(busRecord.get("SIGN_STATUS"));
            String ywId = StringUtil.getString(busRecord.get("YW_ID"));
            if ("1".equals(signStatus)) {//已占用
                String signAccount = StringUtil.getString(busRecord.get("SIGN_ACCOUNT"));
                if(!Objects.equals(loginAccount, signAccount)){
                    Map<String, Object> userMap = sysUserService.getUserInfo(signAccount);
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg(StringUtil.getString(userMap.get("FULLNAME"))
                            +BdcQslyshbsyService.IS_NOT_PERMIT_SIGN_MSG); 
                }                
            } else {//无占用
                Map<String, Object> bdcSign = new HashMap<>();
                bdcSign.put("SIGN_STATUS", 1);
                bdcSign.put("SIGN_ACCOUNT", loginAccount);
                dao.saveOrUpdate(bdcSign, "T_BDC_QSLYSHBSY", ywId);
            }
        }
        return ajaxJson;
    }
    
    /**
     * 描述    签署版本号是否一致
     * @author Allin Lin
     * @created 2020年10月27日 下午7:24:42
     * @param exeId
     * @param signVersion
     * @return
     */
    public AjaxJson isSameSignVersion(String exeId,String signVersion){
        AjaxJson ajaxJson = new AjaxJson();
        StringBuilder sf = new StringBuilder();
        sf.append(BdcQslyshbsyService.PRE_SIGN_VERSION).append(exeId);
        String synKey=sf.toString().intern();
        synchronized (synKey) {            
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String currentVersion = StringUtil.getString(busRecord.get("SIGN_VERSION"));          
            if (Objects.equals(signVersion, currentVersion)) {
                ajaxJson.setSuccess(true);                
            } else {
                ajaxJson.setSuccess(false); 
            }
        }
        return ajaxJson;
    }
    
    /**
     * 
     * 描述   当前签章文件版本号是否一致（前置事件） 
     * @author Allin Lin
     * @created 2020年10月27日 下午9:34:19
     * @param flowVars
     * @return
     */
     public Map<String, Object> validateSignVersion(Map<String, Object> flowVars) {
         String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));// 申报号
         String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));// 退回不执行
         String eflowIstempsave = StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作(1:暂存)
         String signVersion= StringUtil.getString(flowVars.get("SIGN_VERSION"));
         AjaxJson ajaxJson=new AjaxJson();         
         if ( StringUtils.isEmpty(eflowIsback) || "1".equals(eflowIstempsave)) {           
             ajaxJson=isSameSignVersion(exeId,signVersion);   
         }
         if(!ajaxJson.isSuccess()){
             flowVars.put("SIGN_FLAG", false);
             flowVars.put("SIGN_MSG", "签署文件已更新，请重新刷新页面！");
         }else{
             int newSignVersion =  Integer.valueOf(signVersion)+1;
             //更新版本号
             flowVars.put("SIGN_VERSION",newSignVersion);
             Map<String, Object> signVersionMap = new HashMap<String, Object>();
             signVersionMap.put("SIGN_VERSION", newSignVersion);
             Map<String, Object> busRecordMap=exeDataService.getBuscordMap(exeId);
             String ywId=StringUtil.getString(busRecordMap.get("YW_ID"));
             exeDataService.saveOrUpdate(signVersionMap, "T_BDC_QSLYSHBSY", ywId);
         }
         return  flowVars;                
     }
    
    /**
     * 根据办件id进行签章状态修改
     * @param exeId
     */
    public AjaxJson changeSignStatus(String exeId){
        AjaxJson ajaxJson=new AjaxJson();
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        String ywId = StringUtil.getString(busRecord.get("YW_ID"));
        Map<String, Object> bdcSign = new HashMap<>();
        bdcSign.put("SIGN_STATUS", 0);
        dao.saveOrUpdate(bdcSign, "T_BDC_QSLYSHBSY", ywId);
        return ajaxJson;
    }
    
    /**
     * 描述   后置事件（生成签章模板文件）
     * @author Allin Lin
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> genSignFile(Map<String,Object> flowVars){
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作(1:是)
        if(StringUtils.isNotEmpty(exeId)&&!"1".equals(eflowIstempsave)){//暂存不执行
            Map<String, Object> exeInfo  = this.getByJdbc("JBPM6_EXECUTION", 
                    new String[]{"EXE_ID"}, new Object[]{exeId}); 
            //1、初始化模板文档业务数据
            Map<String,Object> returnMap = new HashMap<String, Object>();
            returnMap.put("ZDMJ", StringUtil.getString(flowVars.get("ZDMJ")));//宗地面积
            returnMap.put("JZMJ", StringUtil.getString(flowVars.get("JZMJ")));//建筑面积
            returnMap.put("FWCS", StringUtil.getString(flowVars.get("FWCS")));//房屋层数        
            //2、模板文件字符串替换&生成word文件
            Properties properties = FileUtil.readProperties("project.properties");
            String AttachFilePath = properties.getProperty("AttachFilePath");
            String path = "attachFiles//signFileTemplate//qslyshbsy//"+BdcQslyshbsyService.SHB_MATERNAME;       
            WordRedrawUtil.generateWord(returnMap,AttachFilePath+path);
            //3、上传文件服务器
            String newWordFilePath = StringUtil.getString(returnMap.get("bdcPath"));//模板替换生成word路径 
            Map<String,Object> map=new HashMap<>();
            map.put("busTableName", StringUtil.getString(exeInfo.get("BUS_TABLENAME")));
            map.put("busRecordId", StringUtil.getString(exeInfo.get("BUS_RECORDID")));
            File file=new File(newWordFilePath);
            PtspFile ptspFile = creditService.uploadAndGetPtspfile(map,"不动产权属来源审核表.docx",file,"0");      
            //4、存储文件ID至业务表
            Map<String, Object> busData  = this.getByJdbc("T_BDC_QSLYSHBSY", 
                    new String[]{"YW_ID"}, new Object[]{StringUtil.getString(exeInfo.get("BUS_RECORDID"))}); 
            Map<String, Object> record = new HashMap<String, Object>();     
            if(ptspFile!=null){
                record.put("SIGN_FILEID", ptspFile.getFileId());
                record.put("SIGN_FILE_TYPE", "docx");                
            }
            this.saveOrUpdate(record, "T_BDC_QSLYSHBSY", StringUtil.getString(busData.get("YW_ID")));      
        }     
        return flowVars;
    }
    
    /**
     * 描述    获取并审汇总环节需要自动跳转的任务
     * @author Allin Lin
     * @created 2020年10月27日 上午9:45:44
     * @return
     */
    public List<Map<String, Object>> findNeedAutoJump(){
        //1、获取自动跳转账号（ssyshz）并审汇总环节需自动跳转的记录。
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES,T.SQFS");
        sql.append(",TA.ASSIGNER_NAME,TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE ");
        sql.append(",TA.TASK_ID,T.BUS_RECORDID,T.BUS_TABLENAME");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" AND TA.ASSIGNER_CODE = 'ssyshz' AND TA.ASSIGNER_NAME='自动跳转' AND T.SQFS = '2' ");
        sql.append(" AND D.DEF_KEY IN ('");
        sql.append(BdcQslyshbsyService.DEF_KEY);
        sql.append("','");
        sql.append(BdcQslyshbsyService.DEF_MS_KEY);
        sql.append("','");
        sql.append(BdcQslyshbsyService.DEF_NMS_KEY);
        sql.append("') and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "并审汇总", "并审汇总" }, null);     
        return datas;
    }
    
    /**
     * 描述    并审汇总环节自动跳转
     * @author Allin Lin
     * @created 2020年9月10日 上午10:25:20
     * @param data
     */
    public void jumpTaskToAuto(Map<String, Object> data)throws Exception{
       //1、组装流程自动跳转所需的参数
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
        //flowVars.put("EFLOW_INVOKEBUSSAVE", "1");
        flowVars.put("EFLOW_CURRENTTASK_ID", data.get("TASK_ID"));
        //String ASSIGNER_NAME = "自动跳转";
        flowVars.put("EFLOW_CREATORNAME", data.get("ASSIGNER_NAME"));
        flowVars.put("EFLOW_HANDLE_OPINION", "并审汇总自动流转");
        flowVars.put("SQFS", data.get("SQFS"));    
        flowVars.put("EFLOW_BUSRECORDID", data.get("BUS_RECORDID")); 
        flowVars.put("EFLOW_BUSTABLENAME", data.get("BUS_TABLENAME"));
        //获取业务数据
        Map<String, Object> busRecordMap=exeDataService.getBuscordMap(StringUtil.getString(data.get("EXE_ID")));
        flowVars.put("ZDMJ", StringUtil.getString(busRecordMap.get("ZDMJ")));//宗地面积
        flowVars.put("JZMJ", StringUtil.getString(busRecordMap.get("JZMJ")));//建筑面积
        flowVars.put("FWCS", StringUtil.getString(busRecordMap.get("FWCS")));//房屋层数
        log.info("流程申报号:" + data.get("EXE_ID") + 
                "-并审汇总环节执行自动跳转的时间为："+DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
        //2.获取下一环节审核信息
        String nextStepJson = this.jbpmService.getNextStepsJson(defId, flowVersion,
                data.get("CUR_STEPNAMES").toString(), flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        }
        //3、执行流程提交
        if ("并审汇总".equals(data.get("CUR_STEPNAMES").toString())) {
            jbpmService.doFlowJob(flowVars);
        }                   
    }

    /**
     * 描述  保存或更新签署文件
     * @author Allin Lin
     * @created 2020年10月29日 下午6:43:59
     * @param exeId
     * @param fileId
     * @return
     */
    @Override
    public AjaxJson saveOrUpdateSignFile(String exeId, String fileId,String fileType) {
        AjaxJson ajaxJson=new AjaxJson();
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        String ywId = StringUtil.getString(busRecord.get("YW_ID"));
        Map<String, Object> bdcSign = new HashMap<>();
        bdcSign.put("SIGN_FILEID", fileId);
        bdcSign.put("SIGN_FILE_TYPE", fileType);
        dao.saveOrUpdate(bdcSign, "T_BDC_QSLYSHBSY", ywId);
        return ajaxJson;
    }
}
