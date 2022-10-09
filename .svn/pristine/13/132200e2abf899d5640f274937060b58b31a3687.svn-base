/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SendMsgUtil;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.dao.NodeConfigDao;
import net.evecom.platform.hflow.dao.SendTaskNoticeDao;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.SendTaskNoticeService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.WorkdayService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月14日 上午11:24:57
 */
@Service("sendTaskNoticeService")
public class SendTaskNoticeServiceImpl extends BaseServiceImpl implements SendTaskNoticeService {

    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(SendTaskNoticeServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SendTaskNoticeDao dao;
    /**
     * 
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * userInfoService
     */
    @Resource
    private UserInfoService userInfoService;
    /**
     * 引入Service
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 发送流程短信信息
     * @author Flex Hu
     * @created 2015年12月14日 下午2:25:52
     * @param flowVars
     * @return
     */
    public Map<String,Object> sendMobileMsg(Map<String,Object> flowVars){
        //获取是否发送短信
        String EFLOW_IS_SENDMSG = (String) flowVars.get("EFLOW_IS_SENDMSG");
        //获取流程实例ID
        String EFLOW_EXEID = (String) flowVars.get("EFLOW_EXEID");
        if(!(StringUtils.isNotEmpty(EFLOW_IS_SENDMSG)&&!EFLOW_IS_SENDMSG.equals("false"))){
            //获取最新的流程任务ID
            String EFLOW_NEWTASK_PARENTIDS = (String) flowVars.get("EFLOW_NEWTASK_PARENTIDS");
            String EFLOW_EXERUNSTATUS = "";
            if(flowVars.get("EFLOW_EXERUNSTATUS")!=null){
                EFLOW_EXERUNSTATUS = flowVars.get("EFLOW_EXERUNSTATUS").toString();
            }
           
            //获取当前事项
            if(StringUtils.isNotEmpty(EFLOW_NEWTASK_PARENTIDS)){
                String[] parentTaskIds = EFLOW_NEWTASK_PARENTIDS.split(",");
                for(String parentTaskId:parentTaskIds){
                    Map<String,Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                            new String[]{"TASK_ID"},new Object[]{parentTaskId});
                    //获取审核人类型
                    String ASSIGNER_TYPE= (String) flowTask.get("ASSIGNER_TYPE");
                    //获取团队编码
                    String TEAM_CODES = (String) flowTask.get("TEAM_CODES");
                    if(ASSIGNER_TYPE.equals(AllConstant.ASSIGNER_TYPE_SYSTEMUSER)){
                        this.sendMobileMsgToSystemUser(EFLOW_EXEID,TEAM_CODES, flowVars, flowTask);
                    }else if(ASSIGNER_TYPE.equals(AllConstant.ASSIGNER_TYPE_WEBSITEUSER)){
                        this.sendMobileMsgToWebSiteUser(EFLOW_EXEID, TEAM_CODES, flowVars, flowTask);
                    }
                }
            }else if(StringUtils.isNotEmpty(EFLOW_EXERUNSTATUS)
                    &&!EFLOW_EXERUNSTATUS.equals("0")&&!EFLOW_EXERUNSTATUS.equals("1")){
                boolean isGCJSXM = checkisGyjsxm((String)flowVars.get("ITEM_CODE"));
                if(isGCJSXM){
                    this.sendEndMobileToStartUser(EFLOW_EXEID, EFLOW_EXERUNSTATUS);
                }
            }
        }
        return flowVars;
    }
    
    /**
     * 
     * 描述  判断事项是否为国有建设项目
     * @author Flex Hu
     * @created 2021年8月31日 下午17:13:35
     * @param itemCode 事项编码
     */
    public boolean checkisGyjsxm(String itemCode){
        boolean isGCJSXM = false;
        try {
            if(StringUtils.isNotEmpty(itemCode)){
                StringBuffer sql = new StringBuffer();
                sql.append(" select a.SFGCJSXM from T_WSBS_SERVICEITEM a ");
                sql.append(" where 1=1 ");
                sql.append(" and a.item_code = ? ");
                List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{itemCode}, null);
                if(list != null && list.size() >= 1){
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).get("SFGCJSXM") != null && "1".equals(list.get(i).get("SFGCJSXM").toString())){
                            isGCJSXM = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("checkisGyjsxm异常Exception" + e);
        }
        return isGCJSXM;
    }
    
    
    /**
     * 
     * 描述 发送流程办结通知给发起人
     * @author Flex Hu
     * @created 2015年12月14日 下午5:09:35
     * @param exeId
     */
    public void sendEndMobileToStartUser(String exeId,String runStatus){
        //获取流程实例
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        //获取申请方式
        String SQFS = (String) flowExe.get("SQFS");
        if(SQFS!=null&&"1".equals(SQFS)){
            String endStatus = "已办结";
            if(runStatus.equals("3")){
                endStatus = "审核通过";
            }else if(runStatus.equals("4")){
                endStatus = "审核不通过";
            }
            //获取发起人帐号
            String CREATOR_ACCOUNT = (String) flowExe.get("CREATOR_ACCOUNT");
            //获取发起人
            Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{CREATOR_ACCOUNT});
            //获取手机号
            String sjhm = (String) userInfo.get("SJHM");
            StringBuffer mobileMsg = new StringBuffer("您好,您于");
            mobileMsg.append(flowExe.get("CREATE_TIME")).append("申报了“");
            mobileMsg.append(flowExe.get("ITEM_NAME")).append("”项目,");
            mobileMsg.append("申报号为:").append(exeId).append(",");
            mobileMsg.append("目前办理状态为【").append(endStatus).append("】");
            mobileMsg.append(",具体细节请登录平潭综合实验区行政服务中心系统进行查看。");
            if(StringUtils.isNotEmpty(sjhm)){
                log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
//                SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
                SendMsgUtil.saveSendMsgByType(mobileMsg.toString(), sjhm,"1");
            }
        }
    }
    
    /**
     * 
     * 描述 发送待办任务提醒给公众用户
     * @author Flex Hu
     * @created 2015年12月14日 下午4:05:05
     * @param exeId
     * @param teamCodes
     * @param flowVars
     * @param flowTask
     */
    public void sendMobileMsgToWebSiteUser(String exeId, String teamCodes, Map<String, Object> flowVars,
            Map<String, Object> flowTask) {
        boolean isGCJSXM = checkisGyjsxm((String)flowVars.get("ITEM_CODE"));
        if(isGCJSXM){
          //获取流程实例
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            StringBuffer mobileMsg = new StringBuffer("您好,您于");
            mobileMsg.append(flowExe.get("CREATE_TIME")).append("申报了“");
            String subject = (String)flowExe.get("SUBJECT");
            int len = subject.indexOf("【");
            if(len!=-1){
                subject = subject.substring(0, len);
            }
            mobileMsg.append(subject).append("”项目,");
            mobileMsg.append("申报号为:").append(exeId).append(",目前环节流转到");
            mobileMsg.append("[").append(flowTask.get("TASK_NODENAME")).append("]");
            mobileMsg.append(",具体细节请登录平潭综合实验区行政服务中心系统进行查看。");
            /*List<Map<String,Object>> userList = userInfoService.findByAccounts(teamCodes);
            for(Map<String,Object> user:userList){
                String sjhm = (String) user.get("SJHM");
                if(StringUtils.isNotEmpty(sjhm)){
                    log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
                    SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
                }
            }*/
            String sjhm = flowExe.get("JBR_MOBILE").toString();
            if(StringUtils.isNotEmpty(sjhm)){
                log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
                SendMsgUtil.saveSendMsgByType(mobileMsg.toString(), sjhm,"1");
            }
        }
    }
    
    /**
     * 
     * 描述 发送待办任务提醒短信给系统用户
     * @author Flex Hu
     * @created 2015年12月14日 下午3:48:17
     * @param exeId
     * @param teamCodes
     * @param flowVars
     */
    public void sendMobileMsgToSystemUser(String exeId, String teamCodes, Map<String, Object> flowVars,
            Map<String, Object> flowTask) {
        boolean isGCJSXM = checkisGyjsxm((String)flowVars.get("ITEM_CODE"));
        if(isGCJSXM){
            //获取流程实例
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            StringBuffer mobileMsg = new StringBuffer("您好,您有一份");
            String taskname=(String)flowTask.get("TASK_NODENAME");
            String curstep=(String)flowTask.get("FROMTASK_NODENAMES");
                //(String) flowExe.get("CUR_STEPNAMES");//当前环节名称
            String sqfs=(String)flowExe.get("SQFS");
            if("网上预审".equals(taskname)){
                mobileMsg.append("[网上预审]");
            }else{
                mobileMsg.append("[待办]");
            }
            mobileMsg.append("件，申报号为:").append(exeId);
            mobileMsg.append("，标题为“");
            String subject = (String)flowExe.get("SUBJECT");
            int len = subject.indexOf("【");
            if(len!=-1){
                subject = subject.substring(0, len);
            }
            mobileMsg.append(subject).append("”,请您及时办理！");
            
    //        if (!flowExe.get("BUS_TABLENAME").equals("T_BSFW_PTJINFO")
    //                && !flowExe.get("BUS_TABLENAME").equals("T_BSFW_JBJINFO")) {
            /****************提交办件给公众下发短信***************/
                String defId=(String) flowExe.get("DEF_ID");
                String defVersion=(String) flowExe.get("DEF_VERSION").toString();
                Map<String,Object> flownode = flowNodeService.getFlowNode(defId,Integer.parseInt(defVersion),curstep);
                if(flownode!=null&&Jbpm6Constants.START_NODE.equals((String)flownode.get("NODE_TYPE"))){
                    if("1".equals(sqfs)){//网上申请
                        this.sendMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"online");
                     }else if("2".equals(sqfs)){//窗口提交
                         this.sendMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"windows");
                     }
                }
            /****************提交办件给公众下发短信end***************/
            /*****************下发短信给后台用户****************/
            if("网上预审".equals(taskname)){//不管个人配置都进行下发
                String content=setMsgContent("网上预审申请",subject,exeId);
                List<Map<String, Object>> userList = sysUserService.findByUserAccounts(teamCodes);
                for (Map<String, Object> user : userList) {
                    String mobile = (String) user.get("MOBILE");
                    if (StringUtils.isNotEmpty(mobile)) {
                        log.info("发送号码:" + mobile + ",的内容是:" +content);
                          SendMsgUtil.saveSendMsgByType(content.toString(), mobile,"1");
//                        SendMsgUtil.saveSendMsg(content.toString(), mobile);
//                        log.info("发送号码:" + mobile + ",的内容是:" + mobileMsg.toString());
//                        SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobile);
                    }
                }
            }else if("受理".equals(taskname)){
                if(!"2".equals(sqfs)){//非窗口提交
                    if("网上预审".equals(curstep)){
                        String content=setMsgContent("网上预审通过后流转至受理环节",subject,exeId);
                        List<Map<String, Object>> userList = sysUserService.findByUserAccounts(teamCodes);
                        for (Map<String, Object> user : userList) {
                            String mobile = (String) user.get("MOBILE");
                            String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
                            if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                                log.info("发送号码:" + mobile + ",的内容是:" + content.toString());
                                SendMsgUtil.saveSendMsgByType(content.toString(), mobile,"1");
                                //SendMsgUtil.saveSendMsg(content.toString(), mobile);
                            }
                        }
                    }else{
                        String content=setMsgContent("窗口收件受理后各个下一环节",subject,exeId);
                        List<Map<String, Object>> userList = sysUserService.findByUserAccounts(teamCodes);
                        for (Map<String, Object> user : userList) {
                            String mobile = (String) user.get("MOBILE");
                            String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
                            if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                                log.info("发送号码:" + mobile + ",的内容是:" + content.toString());
                                SendMsgUtil.saveSendMsgByType(content.toString(), mobile,"1");
                                //SendMsgUtil.saveSendMsg(content.toString(), mobile);
                            }
                        }
                    }
                }
            }else if("网上预审".equals(curstep)||"预审".equals(curstep)){//预审操作完成
                String runstate=(String)flowExe.get("RUN_STATUS").toString();
                String applystate=(String)flowExe.get("APPLY_STATUS").toString();
                if("7".equals(runstate)&&"3".equals(applystate)){//预审不通过
                    this.sendPreAuditMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"nopass");
                }else if("1".equals(runstate)&&"2".equals(applystate)){//预审通过
                    this.sendPreAuditMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"pass");
                    String content=setMsgContent("窗口收件受理后各个下一环节",subject,exeId);
                    sendMsgToSystemUser(content,teamCodes);
                }
            }else{//根据个人配置进行下发
                String content=setMsgContent("窗口收件受理后各个下一环节",subject,exeId);
                List<Map<String, Object>> userList = sysUserService.findByUserAccounts(teamCodes);
                for (Map<String, Object> user : userList) {
                    String mobile = (String) user.get("MOBILE");
                    String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
                    if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                        log.info("发送号码:" + mobile + ",的内容是:" + content.toString());
                        SendMsgUtil.saveSendMsgByType(content.toString(), mobile,"1");
    //                    SendMsgUtil.saveSendMsg(content.toString(), mobile);
                    }
                }
            }
            if("受理".equals(taskname)&&("网上预审".equals(curstep)||"预审".equals(curstep))){//预审操作完成
                String runstate=(String)flowExe.get("RUN_STATUS").toString();
                String applystate=(String)flowExe.get("APPLY_STATUS").toString();
                if("7".equals(runstate)&&"3".equals(applystate)){//预审不通过
                    this.sendPreAuditMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"nopass");
                }else if("1".equals(runstate)&&"2".equals(applystate)){//预审通过
                    this.sendPreAuditMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"pass");
                }
            }
        }
        /*****************下发短信给后台用户end****************/
//        }
    }
    /**
     * 
     * 描述：发送短信给系统后台用户
     * @author Water Guo
     * @created 2017-11-15 下午2:28:51
     */
    public void sendMsgToSystemUser(String content,String teamCodes){
        List<Map<String, Object>> userList = sysUserService.findByUserAccounts(teamCodes);
        for (Map<String, Object> user : userList) {
            String mobile = (String) user.get("MOBILE");
            String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
            if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                log.info("发送号码:" + mobile + ",的内容是:" + content.toString());
                SendMsgUtil.saveSendMsg(content.toString(), mobile);
            }
        }
    }
    /**
     * 过滤短信内容
     * @param templateName
     * @param subject
     * @param exeId
     * @return
     */
    public String setMsgContent(String templateName,String subject,String exeId){
        Map<String,Object> template = executionService.getByJdbc("JBPM6_SENDMSG_TEMPLATE",
                new String[]{"TEMPLATE_NAME"},new Object[]{templateName});
        String content ="";
        if(null!=template){
            content=(String)template.get("TEMPLATE_CONTENT");
            int slen=content.indexOf("【subject】");
            if(slen!=-1){
                content =content.replace("【subject】", subject);
            }
            int elen=content.indexOf("【exeId】");
            if(elen!=-1){
                content =content.replace("【exeId】", exeId);
            }
        }
        return content;
    }
    /**
     * 短信下发需求
     * @param exeId
     * @param teamCodes
     * @param flowVars
     * @param flowTask
     * @param type
     */
    public void sendMobileMsgToSystemUserOld(String exeId, String teamCodes, Map<String, Object> flowVars,
            Map<String, Object> flowTask,String type) {
      //获取流程实例
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        StringBuffer mobileMsg = new StringBuffer("您好,您有一份");
        String taskname=(String)flowTask.get("TASK_NODENAME");
        String curstep=(String) flowExe.get("CUR_STEPNAMES");//当前环节名称
        String sqfs=(String)flowExe.get("SQFS");
        if("预审".equals(curstep)){
            if("1".equals(sqfs)){
                this.sendMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"online");
            }
        }else{
            String defId=(String) flowExe.get("DEF_ID");
            String defVersion=(String) flowExe.get("DEF_VERSION");
            Map<String,Object> flownode = executionService.getByJdbc("JBPM6_FLOWNODE",
                    new String[]{"DEF_ID","FLOW_VERSION","NODE_NAME"},
                    new Object[]{defId,defVersion,curstep});
            if(Jbpm6Constants.START_NODE.equals((String)flownode.get("NODE_TYPE"))){
                this.sendMsgToWebSiteUser(exeId, teamCodes, flowVars, flowTask,"windows");
            }
        }
        if("预审".equals(taskname)){
            mobileMsg.append("[预审]");
        }else{
            mobileMsg.append("[待办]");
        }
        mobileMsg.append("件，标题为“");
        String subject = (String)flowExe.get("SUBJECT");
        int len = subject.indexOf("【");
        if(len!=-1){
            subject = subject.substring(0, len);
        }
        mobileMsg.append(subject).append("”,");
        mobileMsg.append("申报号为:").append(exeId).append(",请您及时办理！");
        if (!flowExe.get("BUS_TABLENAME").equals("T_BSFW_PTJINFO")
                && !flowExe.get("BUS_TABLENAME").equals("T_BSFW_JBJINFO")) {
            List<Map<String, Object>> userList = sysUserService.findByUserAccounts(teamCodes);
            
            Map<String, Object> msgControllerMap = this.dao.getByJdbc("t_msjw_system_dictionary", 
                    new String[] {"type_code", "dic_code" }, 
                    new Object[] { "msgInterfaceConfig", "isFlowSend" });
            if (msgControllerMap != null && msgControllerMap.get("DIC_DESC") != null) {
                String isFlow= (String) msgControllerMap.get("DIC_DESC");
                if("1".equals(isFlow)){//全流程下发
                    for (Map<String, Object> user : userList) {
                        String mobile = (String) user.get("MOBILE");
                        String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
                        if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                            log.info("发送号码:" + mobile + ",的内容是:" + mobileMsg.toString());
                            SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobile);
                        }
                    }
                }else{//节点下发
                    Map<String, Object> dicnodes = this.dao.getByJdbc("t_msjw_system_dictionary", 
                            new String[] {"type_code", "dic_code" }, 
                            new Object[] { "msgInterfaceConfig", "nodeNames" });
                    String nodenames= (String) dicnodes.get("DIC_DESC");
                    List<String> nodes=Arrays.asList(nodenames.split(";"));
                    String curnode=(String)flowTask.get("TASK_NODENAME");
                    if(nodes.contains(curnode)){
                        for (Map<String, Object> user : userList) {
                            String mobile = (String) user.get("MOBILE");
                            String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
                            if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                                log.info("发送号码:" + mobile + ",的内容是:" + mobileMsg.toString());
                                SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobile);
                            }
                        }
                    }
                }
            }else{//没配置字典时按照旧的不进行流程过滤；
                for (Map<String, Object> user : userList) {
                    String mobile = (String) user.get("MOBILE");
                    String isAcceptMsg=(String)user.get("IS_ACCEPTMSG");
                    if (StringUtils.isNotEmpty(mobile)&&"1".equals(isAcceptMsg)) {
                        log.info("发送号码:" + mobile + ",的内容是:" + mobileMsg.toString());
                        SendMsgUtil.saveSendMsg(mobileMsg.toString(), mobile);
                    }
                }
            }
        }
    }
    /**
     * 
     * 描述 发送提醒给公众用户
     * @author Sundy Sun
     * @param exeId
     * @param teamCodes
     * @param flowVars
     * @param flowTask
     */
    public void sendMsgToWebSiteUser(String exeId, String teamCodes, Map<String, Object> flowVars,
            Map<String, Object> flowTask,String type) {
        boolean isGCJSXM = checkisGyjsxm((String)flowVars.get("ITEM_CODE"));
        if(isGCJSXM){
          //获取流程实例
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            StringBuffer mobileMsg = new StringBuffer("您好,您于").append(flowExe.get("CREATE_TIME"));
            if("online".equals(type)){
                mobileMsg.append("在区行政服务中心网上办事大厅申请的办件");
            }else{
                mobileMsg.append("在区行政服务中心申请的办件");
            }
            mobileMsg.append("，申报号是:").append(exeId).append("，查询密码是");
            mobileMsg.append((String) flowExe.get("BJCXMM")+",");
            mobileMsg.append("关注平潭综合实验区行政服务中心官方微信公众号查询办件进度。");
            
            // 获取申请人类型
            String BSYHLX = flowExe.get("BSYHLX")==null?""
                    :flowExe.get("BSYHLX").toString();
            String sjhm = "";
            if ("1".equals(BSYHLX)) {
                sjhm = (String) flowExe.get("SQRSJH");
            } else {
                sjhm = (String) flowExe.get("JBR_MOBILE");
            }
            if(StringUtils.isNotEmpty(sjhm)){
                log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
                SendMsgUtil.saveSendMsgByType(mobileMsg.toString(), sjhm,"1");
                //SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
            }
        }
    }

    /**
     * 
     * 描述：事项审核，短信提醒
     * 
     * @author Water Guo
     * @created 2017-4-7 下午3:57:22
     * @param serviceItem
     * @see net.evecom.platform.hflow.service.SendTaskNoticeService#sendServiceitemMsg(java.util.Map)
     */
    public void sendServiceitemMsg(Map<String, Object> serviceItem, String thyj, int sfty) {
        String wei = "";
        if (sfty != 1) {
            wei = "未";
        }
        if (StringUtils.isEmpty(thyj)) {
            thyj = "无";
        }
        String itemId = serviceItem.get("ITEM_ID").toString();
        String sql = "select * from (select * from T_WSBS_SERVICEITEM_LOG L "
                + " where L.item_id=? and operate_type='1' " + "  order by L.OPERATE_TIME DESC ) where rownum=1";
        Map<String, Object> itemlog = dao.getByJdbc(sql, new Object[] { itemId });
        String userId = serviceItem.get("AUDITING_IDS") == null ? "" : serviceItem.get("AUDITING_IDS").toString();
        String sqlUser = "select s.mobile from T_MSJW_SYSTEM_SYSUSER s where s.user_id=?";
        Map<String, Object> user = dao.getByJdbc(sqlUser, new Object[] { userId });
        encryptionService.mapDecrypt(user, "T_MSJW_SYSTEM_SYSUSER");
        if (null != user) {
            String sjhm = user.get("mobile") == null ? "" : user.get("mobile").toString();
            String content = "您好！您于" + itemlog.get("operate_time") + "提交审核库的事项(事项名：" + serviceItem.get("ITEM_NAME")
                    + "  事项" + "编码：" + serviceItem.get("ITEM_CODE") + "）审核" + wei + "通过，审核意见：" + thyj + "。请及时查阅!"
                    + "区综合审批服务平台";
            if (StringUtils.isNotEmpty(sjhm)) {
                log.info("发送号码:" + sjhm + ",的内容是：" + content);
                SendMsgUtil.saveSendMsg(content, sjhm);
            }
        }

    }
    /**
     * 
     * 描述 发送提醒给公众用户
     * @author Sundy Sun
     * @param exeId
     * @param teamCodes
     * @param flowVars
     * @param flowTask
     */
    private void sendPreAuditMsgToWebSiteUser(String exeId, String teamCodes, Map<String, Object> flowVars,
            Map<String, Object> flowTask,String type) {
      //获取流程实例
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String subject = (String)flowExe.get("SUBJECT");
        int len = subject.indexOf("【");
        if(len!=-1){
            subject = subject.substring(0, len);
        }
        String curdate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        StringBuffer content = new StringBuffer("您好,您于").append(flowExe.get("CREATE_TIME"));
        content.append("在区行政服务中心网上办事大厅申请的办件申报号是:").append(exeId).append("，标题为“");
        content.append(subject).append("”,已于").append(curdate);
        if("pass".equals(type)){
            content.append("预审通过");
          //获取主键名称
            String primaryKeyName = (String) this.getPrimaryKeyName((String)flowExe.get("BUS_TABLENAME")).get(0);
            Map<String,Object> item = executionService.getByJdbc((String)flowExe.get("BUS_TABLENAME"),
                    new String[]{primaryKeyName},new Object[]{flowExe.get("BUS_RECORDID")});
            String tel=item.get("CONTACT_TEL")==null?
                    "":item.get("CONTACT_TEL").toString();
//            String endDay = DateTimeUtil.getStrOfDate(DateTimeUtil
//                    .getDateOfStr(new, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
            String endDay=workdayService.getDeadLineDate(curdate, 1);
//            content.append(",请于"+endDay+"内到相应窗口办理，预约详询："+tel+".");
//            content.append(",请于30内到相应窗口办理，预约详询："+tel+".");
            
            String itemCode = flowVars.get("ITEM_CODE").toString();
            Map<String, Object> currentTask = this.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_CODE" }, new Object[] { itemCode });
            String ckcs3 = currentTask.get("CKCS_3")==null?
                    "0":currentTask.get("CKCS_3").toString();
            String ckcs4 = currentTask.get("CKCS_4")==null?
                    "0":currentTask.get("CKCS_4").toString();
            if(ckcs3.equals("1")||ckcs4.equals("1")){
                content.append(",请于"+endDay+"内到相应窗口办理。");
                if (!tel.isEmpty()) {
                    content.append("预约详询："+tel+"。");
                }
            } else {
                content.append(",请于30内到相应窗口办理。");
                if (!tel.isEmpty()) {
                    content.append("预约详询："+tel+"。");
                }
            }
            
        }else{
            content.append("预审不通过").append(",请及时查阅。");
        }
        
        // 获取申请人类型
        String BSYHLX = (String) flowExe.get("BSYHLX");
        String sjhm = "";
        if (BSYHLX.equals("1")) {
            sjhm = (String) flowExe.get("SQRSJH");
        } else {
            sjhm = (String) flowExe.get("JBR_MOBILE");
        }
        if(StringUtils.isNotEmpty(sjhm)){
            log.info("发送号码:"+sjhm+",的内容是:"+content.toString());
            SendMsgUtil.saveSendMsg(content.toString(), sjhm);
        }
    }
    
    
    /**
     * 
     * @Description 发送流程成功办结通知给发起人
     * @author Luffy Cai
     * @date 2021年5月28日
     * @param exeId
     */
    public void sendSuccessMsgToStartUser(String exeId) {
        // 获取流程实例
        Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        // 获取申请人类型
        String BSYHLX = (String) flowExe.get("BSYHLX");
        String sjhm = "";
        if (BSYHLX.equals("1")) {
            sjhm = (String) flowExe.get("SQRSJH");
        } else {
            sjhm = (String) flowExe.get("JBR_MOBILE");
        }
        String content = "【平潭综合实验区行政服务中心】您的办件【" + exeId
                + "】已办结，请点击http://xzfwzx.pingtan.gov.cn/cms-h5/#/approvalEvaluate?exeId=" + exeId + "&evalChannel\r\n"
                + "=2，对我们的服务进行评价！";
        content = "http://xzfwzx.pingtan.gov.cn/cms-h5/#/approvalEvaluate?exeId=" + exeId + "&evalChannel\r\n"
                + "=2，对我们的服务进行评价！";
        if (StringUtils.isNotEmpty(sjhm)) {
            log.info("发送号码:" + sjhm + ",的内容是:" + content);
            try {
                SmsSendUtil.sendSms(content, "18850106994","180679");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
        }
    }   
    
    
    
}
