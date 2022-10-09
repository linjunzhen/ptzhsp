/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.indupark.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.indupark.dao.InduParkDao;
import net.evecom.platform.indupark.service.InduParkWebService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年3月29日 上午9:11:36
 */
@Service("induParkWebService")
public class InduParkWebServiceImpl extends BaseServiceImpl implements InduParkWebService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(InduParkWebServiceImpl.class);
    /**
     * 引入jbpmService
     */
    private JbpmService jbpmService;
    /**
     * 引入serviceItemService
     */
    private ServiceItemService serviceItemService;
    /**
     * 引入applyMaterService
     */
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    
    /**
     * 引入的dao
     */
    @Resource
    private InduParkDao dao;

    /**
     * 
     * 描述 覆盖获取实体dao方法
     * @author Danto Huang
     * @created 2018年4月2日 下午2:58:56
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     *
     * 描述
     * @author Danto Huang
     * @created 2018年3月29日 上午9:05:27
     * @param flowInfoJson
     * @return
     */
    public String dataChange(String flowInfoJson) {
        log.info("++++++++++++数据交换发起请求++++++++++++");
        Map<String, Object> result = new HashMap<String, Object>();
        jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            try{
                //TODO  在这里根据实际情况进行编码，以下方法时默认提供所有发起流程需要的数据
                Map<String, Object> variables = JSON.parseObject(flowInfoJson, Map.class);
                String ITEM_CODE = variables.get("ITEM_CODE")==null?
                        "":variables.get("ITEM_CODE").toString();
                if("201605170002XK00107".equals(ITEM_CODE)){
                    result=financeFlow(flowInfoJson);
                }
            }catch(Exception e){
                result.put("resultCode", "000");
                result.put("resultMsg", "提交失败,请联系网站管理员!");
                log.error(e.getMessage(),e);
            }
        }else{
            result.put("resultCode", "002");
            result.put("resultMsg", "提交失败,没有提供数据!");
        }
        log.info("++++++++++++数据交换结束++++++++++++");
        return JSON.toJSONString(result);
    }

    /**
     * 金融审核流程具体数据提交
     * @param flowInfoJson
     * @return
     */
    private Map<String, Object> financeFlow(String flowInfoJson) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = JSON.parseObject(flowInfoJson, Map.class);
        if(!isValid(variables,result)){
            return result;
        }
        String fileId = getFileId(variables);
        //保存金融办审核办件表
        String cyyCreateTime = String.valueOf(variables.get("CREATE_TIME"));
        variables.put("CYY_CREATE_TIME", cyyCreateTime);
        String createTime = DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm");
        variables.put("CREATE_TIME", createTime);
        variables.put("FILE_ID", fileId);
        dao.saveOrUpdate(variables,"JBPM6_CYY_FINANCE","");
        result.put("resultCode", "001");
        result.put("resultMsg", "数据提交成功");
        return result;
    }

    /**
     * 获取附件的id
     * @param variables
     * @return
     */
    private String getFileId(Map<String, Object> variables) {
        //文件参数
        Map<String, Object> fileMap = new HashMap<String, Object>();
        String filePath = String.valueOf(variables.get("FILE_PATH"));
        String fileName = String.valueOf(variables.get("FILE_NAME"));
        String fileType = String.valueOf(variables.get("FILE_TYPE"));
        fileMap.put("FILE_PATH",filePath);
        fileMap.put("FILE_NAME", fileName);
        fileMap.put("FILE_TYPE", fileType);
        String filePathField = variables.get("FILE_PATH_FIELD") == null ? ""
                : variables.get("FILE_PATH_FIELD").toString();
        //下载附件
        downloadFlowFile(variables, filePathField);
        return jbpmService.saveOrUpdate(fileMap, "T_MSJW_SYSTEM_FILEATTACH", "");
    }

    /**
     * 校验参数是否有效
     * @param variables
     * @param result
     * @return
     */
    private boolean isValid(Map<String,Object> variables,Map<String,Object> result){
        String companyName = variables.get("COMPANY_NAME") == null ? "" :
                variables.get("COMPANY_NAME").toString();
        String filePath = variables.get("FILE_PATH") == null ? "" :
                variables.get("FILE_PATH").toString();
        if (StringUtils.isEmpty(companyName)) {
            result.put("resultCode", "002");
            result.put("resultMsg", "公司名称不得为空");
            return false;
        }
        if (StringUtils.isEmpty(filePath)) {
            result.put("resultCode", "002");
            result.put("resultMsg", "附件地址不得为空");
            return false;
        }
        return true;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年3月29日 上午9:05:27
     * @param flowInfoJson
     * @return
     */
    public String submitApply(String flowInfoJson) {
        log.info("++++++++++++接收到产业园流程发起请求++++++++++++");
        Map<String, Object> result = new HashMap<String, Object>();
        jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            try{
                //TODO  在这里根据实际情况进行编码，以下方法时默认提供所有发起流程需要的数据
                Map<String, Object> variables = JSON.parseObject(flowInfoJson, Map.class);
//                cs(variables);
                //获取申报用户
                Map<String,Object> curMemeber = 
                        jbpmService.getByJdbc("T_BSFW_USERINFO",
                                new String[] { "YHZH" }, new Object[] { "cyy" });
                String ITEM_CODE = variables.get("ITEM_CODE")==null?
                        "":variables.get("ITEM_CODE").toString();
                Map<String,Object> itemInfo = 
                        jbpmService.getByJdbc("T_WSBS_SERVICEITEM",
                                new String[] { "ITEM_CODE" }, new Object[] { ITEM_CODE });
                Map<String,Object> flowInfo = 
                        jbpmService.getByJdbc("JBPM6_FLOWDEF",
                                new String[] { "DEF_ID" }, new Object[] { itemInfo.get("BDLCDYID") });
                variables.put("EFLOW_DEFKEY", flowInfo.get("DEF_KEY"));
                String EFLOW_CURRENTTASK_ID = variables.get("EFLOW_CURRENTTASK_ID")==null?
                        "":variables.get("EFLOW_CURRENTTASK_ID").toString();
                variables.put("JBR_NAME", curMemeber.get("YHMC"));
                variables.put("JBR_SEX", curMemeber.get("YHXB"));
                variables.put("JBR_ZJLX", curMemeber.get("ZJLX"));
                variables.put("JBR_ZJHM", curMemeber.get("ZJHM"));
                variables.put("JBR_MOBILE", curMemeber.get("SJHM"));
                variables.put("JBR_LXDH", curMemeber.get("DHHM"));
                variables.put("JBR_POSTCODE", curMemeber.get("YZBM"));
                variables.put("JBR_EMAIL", curMemeber.get("DZYX"));
//                variables.put("JBR_ADDRESS", curMemeber.get("JTZZ"));
//                variables.put("JBR_REMARK", curMemeber.get("YHMC"));
                
                if(StringUtils.isEmpty(EFLOW_CURRENTTASK_ID)){
                    variables.put("EFLOW_CREATORID", curMemeber.get("USER_ID"));
                    variables.put("EFLOW_CREATORACCOUNT",  curMemeber.get("YHZH"));
                    variables.put("EFLOW_CREATORNAME", curMemeber.get("YHMC"));
                    variables.put("EFLOW_CREATORPHONE", curMemeber.get("SJHM"));
                    variables.put("EFLOW_ASSIGNER_TYPE",AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
                }
                //获取当前节点 默认当前节点为开始节点
                String EFLOW_CUREXERUNNINGNODENAMES = "开始";
                if(StringUtils.isNotEmpty(EFLOW_CUREXERUNNINGNODENAMES) 
                        && EFLOW_CUREXERUNNINGNODENAMES.equals("开始")){
                    variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                }
                //定义流程标题值
                String eflowSubject = null;
                String itemName = (String) variables.get("ITEM_NAME");
                //企业名称
                String COMPANY_NAME = (String) variables.get("COMPANY_NAME");
                //获取申报名称
                String sbmc = (String) variables.get("SBMC");
                StringBuffer subject = new StringBuffer("");
                if(StringUtils.isNotEmpty(sbmc)){
                    if(StringUtils.isNotEmpty(COMPANY_NAME)){
                        subject.append(sbmc).append("（").append(COMPANY_NAME).append("）");
                    }else{
                        subject.append(sbmc).append("（").append(itemName).append("）");                    
                    }
                }else{
                    String projectName = (String)variables.get("PROJECT_NAME");
                    subject.append(projectName).append("（").append(itemName).append("）");
                }
                eflowSubject = subject.toString();
                variables.put("EFLOW_SUBJECT",eflowSubject);
                setFlowData(variables, ITEM_CODE);
                downloadFlowFile(variables,"DEALER_PHOTO");
                //提交流程
                variables = jbpmService.exeFrontFlow(variables);
                result.put("resultCode", "001");
                result.put("EXE_ID", (String)variables.get("EFLOW_EXEID"));
                Map<String,Object> execution = jbpmService.getByJdbc("JBPM6_EXECUTION",
                        new String[]{"EXE_ID"},new Object[]{(String)variables.get("EFLOW_EXEID")});
                String SPBUSRECORD_ID=String.valueOf(execution.get("BUS_RECORDID"));
                result.put("SPBUSRECORD_ID",SPBUSRECORD_ID);
                result.put("resultMsg", "申报成功,申报号是:"+(String)variables.get("EFLOW_EXEID"));
            }catch(Exception e){
                result.put("resultCode", "000");
                result.put("resultMsg", "提交失败,请联系网站管理员!");
                log.error(e.getMessage(),e);
            }
        }else{
            result.put("resultCode", "002");
            result.put("resultMsg", "未提供流程相关数据");
        }

        log.info("产业园流程发起请求处理结果：" + result.get("resultMsg"));
        log.info("++++++++++++结束产业园流程发起请求处理++++++++++++");
        return JSON.toJSONString(result);
    }

    /**
     * 产业园下载文件
     * @param variables
     * @param field  字段名
     */
    private void downloadFlowFile(Map<String, Object> variables, String field) {
        String filePath = variables.get(field) == null ? "" : variables.get(field).toString();
        URL url = null;
        FileOutputStream fileOutputStream = null;
        ByteArrayOutputStream output = null;
        DataInputStream dataInputStream = null;
        if (StringUtils.isNotEmpty(filePath)) {
            String sufixUrl=variables.get("SUFIX_URL")==null?
                    "":variables.get("SUFIX_URL").toString();
            if(StringUtils.isEmpty(sufixUrl)) {
                sufixUrl = "http://www.ptcsip.com/ptcyyPlat/";
            }
            String fullUrl = sufixUrl + filePath;
            try {
                url = new URL(fullUrl);
                dataInputStream = new DataInputStream(url.openStream());
                Properties properties = FileUtil.readProperties("project.properties");
                String attachFileFolderPath = properties.getProperty("AttachFilePath");
                String imageName = attachFileFolderPath+filePath;
                String fileFloder=imageName.substring(0, imageName.lastIndexOf("/"));
                File floder=new File(fileFloder);
                if(!floder.exists()){
                    floder.mkdirs();
                }
                File f=new File(imageName);
                fileOutputStream = new FileOutputStream(f);
                output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                fileOutputStream.write(output.toByteArray());
                dataInputStream.close();
                fileOutputStream.close();
                output.close();
            } catch (Exception e) {
                log.info(e.getMessage());
            } finally {
                try {
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }
    /**
     * 产业园重新提交设置必要的参数
     * @param variables
     * @param ITEM_CODE
     */
    private void setFlowData(Map<String, Object> variables, String ITEM_CODE) {
        dao = (InduParkDao) AppUtil.getBean("induParkDao");
        String exeId = variables.get("EFLOW_EXEID") == null ? "" : variables.get("EFLOW_EXEID").toString();
        if (StringUtils.isNotEmpty(exeId)) {
            StringBuffer sqlTask = new StringBuffer("select * from ");
            sqlTask.append(" (select * from jbpm6_task t where t.exe_id=? AND T.IS_AUDITED=1  ");
            sqlTask.append("  order by t.create_time desc)  ");
            sqlTask.append("  where rownum=1 ");
            Map<String, Object> task = dao.getByJdbc(sqlTask.toString(), new String[]{exeId});
            String taskId = String.valueOf(task.get("TASK_ID"));

            StringBuffer sqlDef = new StringBuffer(" select * from ");
            sqlDef.append("  t_wsbs_serviceitem s left join JBPM6_FLOWDEF f on s.bdlcdyid=f.def_id ");
            sqlDef.append(" where s.item_code=? ");
            Map<String, Object> serviceItemDef = dao.getByJdbc(sqlDef.toString(), new String[]{ITEM_CODE});
            String defVersion = String.valueOf(serviceItemDef.get("VERSION"));
            String defId = String.valueOf(serviceItemDef.get("DEF_ID"));


            variables.put("EFLOW_CURRENTTASK_ID", taskId);
            variables.put("EFLOW_DEFVERSION", defVersion);
            variables.put("CREATOR_ACCOUNT","cyy");
            variables.put("EFLOW_ISTEMPSAVE", "-1");
            variables.put("EFLOW_CURUSEROPERNODENAME", task.get("TASK_NODENAME"));
            variables.put("EFLOW_DEFID", defId);
            //variables.put("EFLOW_BUSRECORDID", "1");
        }
    }

    /**
     * 
     * 描述 撤回申请
     * @author Kester Chen
     * @created 2018-6-10 下午2:59:57
     * @param exeId
     * @return
     */
    public String getBackMyApply(String exeId) {
        // TODO Auto-generated method stub
        log.info("++++++++++++接收到产业园撤回申请请求++++++++++++");
        executionService = (ExecutionService) AppUtil.getBean("executionService");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String,Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION", 
                new String[]{"EXE_ID"}, new Object[]{exeId});
        String[] nodeNames = {"质监审批","国税审批","地税审批","社保审批","统计审批","公安审批","商务审批","办结","并审汇总"};
        boolean isAllow = false;
        for(int i=0;i<nodeNames.length;i++){
            int have = exeMap.get("CUR_STEPNAMES").toString().indexOf(nodeNames[i]);
            if(have>=0){
                isAllow = true;
                break;
            }
        }
        if(isAllow){
            result.put("resultCode", "002");
            result.put("resultMsg", "已完成工商审批，不允许追回！");
        }else{
            executionService.getBackMyApply(exeId);
            result.put("resultCode", "001");
            result.put("resultMsg", "撤回成功");
        }
        log.info("++++++++++++结束产业园撤回申请请求处理++++++++++++");
        return JSON.toJSONString(result);
    }
    /**
     * 
     * 描述    办件状态查询
     * @author Danto Huang
     * @created 2018年4月2日 下午2:45:35
     * @param exeId
     * @return
     */
    public String queryApplyStatus(String exeId){
        jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String,Object> exe = jbpmService.getByJdbc("JBPM6_EXECUTION", 
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            if(exe!=null){
                //0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回)
                String runStatus = exe.get("RUN_STATUS")==null?
                        "":exe.get("RUN_STATUS").toString();
                String curStepnames = exe.get("CUR_STEPNAMES")==null?
                        "":exe.get("CUR_STEPNAMES").toString();
                if (StringUtils.isEmpty(curStepnames)) {
//                    curStepnames = runStatus;
//                    if ("2".equals(runStatus)) {
//                        curStepnames = "已办结(正常结束)";
//                    }else if ("3".equals(runStatus)) {
//                        curStepnames = "已办结(审核通过)";
//                    }else if ("4".equals(runStatus)) {
//                        curStepnames = "已办结(审核不通过)";
//                    }else if ("5".equals(runStatus)) {
//                        curStepnames = "已办结(退回)";
//                    }else if ("6".equals(runStatus)) {
//                        curStepnames = "强制结束";
//                    }else if ("7".equals(runStatus)) {
//                        curStepnames = "预审不通过(退回)";
//                    }else if ("1".equals(runStatus)) {
//                        curStepnames = "正在办理中";
//                    }else if ("0".equals(runStatus)) {
//                        curStepnames = "草稿";
//                    }
                    if ("2".equals(runStatus)||"3".equals(runStatus)) {
                        curStepnames = "已办结(审核通过)";
                    }else if ("4".equals(runStatus)||"5".equals(runStatus)||"6".equals(runStatus)) {
                        curStepnames = "已办结(审核不通过)";
                    }
                }else if (curStepnames.contains("预审")) {
                    curStepnames = "预审";
                }else if (curStepnames.contains("审批")) {
                    curStepnames = "审批";
                }else {
//                    curStepnames = curStepnames;
                }
                
                result.put("resultCode", "001");
                result.put("CUR_STEPNAMES", curStepnames);
                result.put("resultMsg", "办件申报号:"+exeId+",当前环节名称为"+curStepnames);
            }else{
                result.put("resultCode", "002");
                result.put("resultMsg", "提供的办件流水号有误,在审批平台查询不到对应办件记录");
            }
        }else{
            result.put("resultCode", "002");
            result.put("resultMsg", "未提供办件流水号");
        }

        return JSON.toJSONString(result);
    }
    /**
     *
     * 描述    社会信用代码同步
     * @created 2018年4月2日 下午2:45:35
     * @param exeId
     * @return
     */
    public String querySocilCode(String exeId) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> buscordMap = getBuscordMap(exeId);
            String creditUnicode = StringUtil.getString(buscordMap.get("SOCIAL_CREDITUNICODE"));
            if (StringUtils.isEmpty(creditUnicode)) {
                result.put("SOCIAL_CREDITUNICODE", "-1");
                result.put("resultCode", "003");
                result.put("resultMsg", "无社会信用统一代码");
            } else {
                result.put("SOCIAL_CREDITUNICODE", creditUnicode);
                result.put("resultCode", "001");
                result.put("resultMsg", "成功");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "提供的办件流水号有误,在审批平台查询不到对应办件记录");
        }
        return JSON.toJSONString(result);
    }
    /**
     * 
     * 描述 获取审核意见
     * @author Kester Chen
     * @created 2018-6-10 下午3:39:41
     * @param exeId
     * @return
     */
    public String getAuditOpinion(String exeId) {
        // TODO Auto-generated method stub
        dao = (InduParkDao) AppUtil.getBean("induParkDao");
        Map<String, Object> result = new HashMap<String, Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_FIELDAUDIT T WHERE 1=1 ");
        sql.append(" and T.EXE_ID = ");
        sql.append("'");
        sql.append(exeId);
        sql.append("'");
        List<Map<String, Object>> auditOpinionlist = dao.findBySql(sql.toString(), params.toArray(), null);
        result.put("auditOpinionlist", auditOpinionlist);
        return JSON.toJSONString(auditOpinionlist);
    }
    
    /**
     * 
     * 描述 附件下载
     * @author Kester Chen
     * @created 2018-4-10 下午7:19:47
     * @param exeId
     * @return
     */
    public String downLoadFile(String itemCode, String exeId){
        jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
        serviceItemService = (ServiceItemService) AppUtil.getBean("serviceItemService");
        applyMaterService = (ApplyMaterService) AppUtil.getBean("applyMaterService");
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(exeId)&&StringUtils.isNotEmpty(itemCode)) {
            Map<String,Object> exe = jbpmService.getByJdbc("JBPM6_EXECUTION", 
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            if(exe!=null){
                //0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回)

                Map<String,Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
                if(serviceItem!=null){
                    //获取流程KEY
//                    String defKey = (String) serviceItem.get("DEF_KEY");
                    //获取项目ID
                    String itemId = (String) serviceItem.get("ITEM_ID");
                    //获取材料信息列表
                    List<Map<String,Object>> applyMaters = applyMaterService.findByItemId(itemId,exeId);
                    applyMaters=applyMatersFilter(applyMaters,exeId);
                    result.put("applyMaters", applyMaters);
                }
            }else{
                result.put("resultCode", "002");
                result.put("resultMsg", "提供的办件流水号有误,在审批平台查询不到对应办件记录");
            }
        }else{
            result.put("resultCode", "002");
            result.put("resultMsg", "未提供事项编码或办件流水号");
        }

        return JSON.toJSONString(result);
    }

    /**
     * 过滤掉不必要的附件
     *
     * @param applyMaters
     * @param exeId
     * @return
     */
    public List<Map<String, Object>> applyMatersFilter(List<Map<String, Object>> applyMaters,
                                                       String exeId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> buscordMap = getBuscordMap(exeId);
        String COMPANY_SETNATURE = String.valueOf(buscordMap.get("COMPANY_SETNATURE"));
        String COMPANY_SETTYPE = String.valueOf(buscordMap.get("COMPANY_SETTYPE"));
        for (Map<String, Object> applyMater : applyMaters) {
            String MATER_FILTER = applyMater.get("MATER_FILTER") == null ? "" :
                    applyMater.get("MATER_FILTER").toString();
            if (StringUtils.isEmpty(MATER_FILTER)) {
                list.add(applyMater);
            } else {
                if (MATER_FILTER.equals(COMPANY_SETNATURE) ||
                        MATER_FILTER.equals(COMPANY_SETTYPE)) {
                    list.add(applyMater);
                }
            }
        }
        return list;
    }
    /**
     * 根据exeId获取业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getBuscordMap(String exeId) {
        dao = (InduParkDao) AppUtil.getBean("induParkDao");
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName=String.valueOf(execution.get("BUS_TABLENAME"));
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_DOMESTIC")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_FOREIGN")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        String busRecordId = (String) execution.get("BUS_RECORDID");//获取业务ID
        String busPkName = (String) dao.getPrimaryKeyName(busTableName).get(0);//获取业务表主键名称
        Map<String,Object> busMap=dao.getByJdbc(busTableName,
                new String[]{busPkName}, new Object[]{busRecordId});
        return busMap;
    }

}
