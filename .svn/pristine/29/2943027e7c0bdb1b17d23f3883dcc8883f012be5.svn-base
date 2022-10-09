/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.WorkdayService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 描述  流程任务Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowTaskController")
public class FlowTaskController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowTaskController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String exeId = request.getParameter("exeId");
        this.flowTaskService.deleteTask(selectColNames.split(","), exeId);
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 流程任务记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 方法changeHandler
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "changeHandler")
    @ResponseBody
    public AjaxJson changeHandler(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userId = request.getParameter("userId");
        String exeId = request.getParameter("exeId");
        String taskId = request.getParameter("taskId");
        Map<String,Object> user = sysUserService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                new String[]{"USER_ID"},new Object[]{userId});
        String userAccount = (String) user.get("USERNAME");
        String userName = (String) user.get("FULLNAME");
        this.flowTaskService.changeTaskHandler(taskId, exeId, userAccount, userName);
        sysLogService.saveLog("转发了ID为["+taskId+"]的 流程任务记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("转发成功");
        return j;
    }
    
    /**
     * 方法changeTask
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "changeTask")
    @ResponseBody
    public AjaxJson changeTask(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String exeId= request.getParameter("exeId");
        String changeListJson = request.getParameter("changeListJson");
        List<Map> changeList = JSON.parseArray(changeListJson, Map.class);
        this.flowTaskService.changeTaskNode(exeId, changeList);
        j.setMsg("更改路径成功");
        return j;
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        request.setAttribute("exeId", request.getParameter("exeId"));
        // 是否流程归档查看
        request.setAttribute("isFiled", request.getParameter("isFiled"));
        return new ModelAndView("hflow/flowtask/flowtaskview");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tzxmView")
    public ModelAndView tzxmView(HttpServletRequest request) {
        request.setAttribute("exeId", request.getParameter("exeId"));
        // 是否流程归档查看
        request.setAttribute("isFiled", request.getParameter("isFiled"));
        return new ModelAndView("hflow/flowtask/flowTaskTzxmView");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "zzhyView")
    public ModelAndView zzhyView(HttpServletRequest request) {
        request.setAttribute("exeId", request.getParameter("exeId"));
        // 是否流程归档查看
        request.setAttribute("isFiled", request.getParameter("isFiled"));
        return new ModelAndView("hflow/flowtask/flowtaskzzhyview");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "gcjsItemView")
    public ModelAndView gcjsItemView(HttpServletRequest request) {
        request.setAttribute("exeId", request.getParameter("exeId"));
        // 是否流程归档查看
        request.setAttribute("isFiled", request.getParameter("isFiled"));
        return new ModelAndView("hflow/flowtask/flowtaskgcjsItemView");
    }       
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.STEP_SEQ","asc");
        List<Map<String, Object>> list = flowTaskService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:办件详情信息（提供给devbase接口）
     *
     * @author Madison You
     * @created 2021/3/18 10:07:00
     * @param
     * @return
     */
    @RequestMapping("/exeTaskDetailForDevbase")
    @ResponseBody
    public List<Map<String, Object>> exeDetailForDevbase(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.STEP_SEQ","asc");
        filter.addFilter("Q_T.STEP_SEQ_NEQ","0");
        filter.addFilter("Q_T.EXE_ID_EQ", exeId);
        List<Map<String, Object>> list = flowTaskService.findBySqlFilter(filter);
        return list;
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandle")
    public void needMeHandle(HttpServletRequest request,
            HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("S.SXLX","asc");
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandle(filter,haveHandUp);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
        null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 投资项目待办列表信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "tzxmNeedMeHandle")
    public void tzxmNeedMeHandle(HttpServletRequest request,
            HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("S.SXLX","asc");
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = flowTaskService.findTzxmNeedMeHandle(filter,haveHandUp);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2018年12月20日 下午5:23:06
     * @param request
     * @return
     */
    @RequestMapping(params = "YctbWarningNum")
    @ResponseBody
    public AjaxJson yctbWarningNum(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        boolean haveHandUp = false;
        SysUser sysUser = AppUtil.getLoginUser();
        String isHaveHandup = "true";
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_E.ACCEPTWAY_=", "1");
        filter.addFilter("Q_S.SXLX_=", "1");
        filter.addFilter("Q_T.ASSIGNER_CODE_=", sysUser.getUsername());
        filter.addSorted("S.SXLX","asc");
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = flowTaskService.findNeedMeHandle(filter,haveHandUp);
        int ycqNum = 0,sy3Num = 0,sy5Num = 0,sy10Num = 0,sy15Num = 0;
        StringBuffer msg = new StringBuffer("");
        for (Map<String, Object> map : list) {
            int leftMinute = Integer.parseInt(map.get("LEFTMINUTE")==null?
                    "0":map.get("LEFTMINUTE").toString());
            if (leftMinute<0) {
                ycqNum++;
            }else if (leftMinute<3) {
                sy3Num++;
            }else if (leftMinute<5) {
                sy5Num++;
            }else if (leftMinute<10) {
                sy10Num++;
            }else if (leftMinute<15) {
                sy15Num++;
            }
            int total = ycqNum+sy3Num+sy5Num;
//                    +sy10Num+sy15Num;
            if (total<1) {
                j.setMsg(msg.toString());
            }else {
                msg.append("待您处理的一窗通办即办件");
                if (ycqNum>0) {
                    msg.append(ycqNum+"个已超期，");
                }
                if (sy3Num>0) {
                    msg.append(sy3Num+"个剩余时间不足三分钟，");
                }
                if (sy5Num>0) {
                    msg.append(sy5Num+"个剩余时间不足五分钟，");
                }
//                if (sy10Num>0) {
//                    msg.append(sy10Num+"个一窗通办即办件剩余时间不足十分钟，");
//                }
//                if (sy15Num>0) {
//                    msg.append(sy15Num+"个一窗通办即办件剩余时间不足十五分钟，");
//                }
                String tsString = "<a  style=\"margin-top: 2px;\" "
                        + "title=\"点击此处查看\" href=\"javascript:void(0);\" "
                        + "onclick=\"AppUtil.addMenuToCenterTab('YctbNeedMeHandleWarningView','一窗通办即办预警',"
                        + "'executionController.do?goYctbNeedMeHandleWarning');\" >点击此处查看</a>";
//                msg.append("请及时处理。");
                msg.append(tsString);
                j.setMsg(msg.toString());
            }
        }
        return j;
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bzhjsMyPortal")
    public void bzhjsMyPortal(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ISTOP","DESC");
        filter.addSorted("T.RELEASE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.findBzhjsMyPortal(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "zzhyNeedMeHandle")
    public void zzhyNeedMeHandle(HttpServletRequest request,
            HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME","ASC");
        List<Map<String, Object>> list = flowTaskService.findZzhyNeedMeHandle(filter,haveHandUp);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "financeNeedMeHandle")
    public void financeNeedMeHandle(HttpServletRequest request,
                                 HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.financeNeedMeHandle(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                    null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "socialNeedMeHandle")
    public void socialNeedMeHandle(HttpServletRequest request,
                                    HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","DESC");
//        filter.addSorted("A.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.findSocialFormList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "medicialNeedMeHandle")
    public void medicialNeedMeHandle(HttpServletRequest request,
                                   HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("A.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.findMedicialFormList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "fundsNeedMeHandle")
    public void fundsNeedMeHandle(HttpServletRequest request,
                                     HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("A.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.findFundsFormList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "taxNeedMeHandle")
    public void taxNeedMeHandle(HttpServletRequest request,
                                     HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("A.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowTaskService.findTaxFormList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "processWarningdata")
    public void processWarningdata(HttpServletRequest request,
            HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = flowTaskService.findProcessWarningData(filter,haveHandUp);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述：特殊环节重复环节挂起展示
     * @author Water Guo
     * @created 2017-9-30 下午5:39:35
     * @param request
     * @param response
     */
    @RequestMapping(params="speShow")
    public ModelAndView speShow(HttpServletRequest request){
        return new ModelAndView("statis/speShow");
    }
    /**
     * 
     * 描述：特殊环节重复环节speDatagrid
     * @author Water Guo
     * @created 2017-10-9 上午11:35:06
     * @param request
     * @param response
     */
    @RequestMapping(params="speDatagrid")
    public void speDatagrid(HttpServletRequest request
            ,HttpServletResponse response){
        SqlFilter filter=new SqlFilter(request);
        //filter.addSorted("DECODE(E.EXE_ID,NULL,0,1)", "DESC");
        filter.addSorted("H.BEGIN_TIME","DESC");
        filter.addSorted("E.EXE_ID", "DESC");
        filter.addSorted("H.TASK_ID","DESC");
        List<Map<String, Object>> list = flowTaskService.findHangSpe(filter);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到流程任务管理界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goManager")
    public ModelAndView goManager(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        return new ModelAndView("hflow/flowtask/flowtaskManager");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "managerData")
    public void managerData(HttpServletRequest request,
            HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> list = flowTaskService.findIsAuditTask(exeId, 
                Jbpm6Constants.TASKSTATUS_ZZSH);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping(params = "goChange")
    public ModelAndView goChange(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        //获取流程实例信息
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        int flowVersion= Integer.parseInt(flowExe.get("DEF_VERSION").toString());
        //获取环节名称
        List<String> taskList = flowNodeService.findTaskNodeNames((String)flowExe.get("DEF_ID"),flowVersion);
        //获取当前环节
        String curNodeNames = (String) flowExe.get("CUR_STEPNAMES");
        List<Map<String,Object>> trans = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(curNodeNames)){
            for(String nodeName:curNodeNames.split(",")){
                Map<String,Object> tran =new HashMap<String,Object>();
                tran.put("OLD_NODENAME",nodeName);
                tran.put("NODENAME_LIST", taskList);
                trans.add(tran);
            }
        }
        request.setAttribute("exeId", exeId);
        request.setAttribute("trans", trans);
        request.setAttribute("curNodeNames", curNodeNames);
        return new ModelAndView("hflow/flowtask/changeFlowTask");
    }
    
    /**
     * 执行挂起功能
     * @param request
     * @return
     */
    @RequestMapping(params = "handUp")
    @ResponseBody
    public void handUp(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        //获取实例ID
        String exeId = (String) variables.get("EFLOW_EXEID");
        String LinkId=request.getParameter("LinkId");
        String link_man_tel=request.getParameter("link_man_tel");
        String link_man=request.getParameter("link_man");
        String currentTaskId = (String) variables.get("EFLOW_CURRENTTASK_ID");
        try{
            if(StringUtils.isEmpty(LinkId)){
                throw new NullPointerException();
            }
            flowTaskService.handUpFlowTask(exeId, currentTaskId);
            //获取流程实例信息
            Map<String,Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{currentTaskId});
            Map<String, Object> flowHangInfo = new HashMap<String, Object>();
            flowHangInfo.put("TASK_ID",(String)flowTask.get("PARENT_TASKID"));
            String beginTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String gqsj = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
            SysUser sysUser = AppUtil.getLoginUser();
            //特殊环节挂起
            if (LinkId != null) {
                String linkTime = flowHangInfoService
                        .getByJdbc("T_WSBS_SERVICEITEM_LINK", new String[] { "record_id" }, new Object[] { LinkId })
                        .get("LINK_LIMITTIME").toString();
                String linkEndTime = workdayService.getDeadLineDate(gqsj, Integer.parseInt(linkTime));
                flowHangInfo.put("link_End_Time", linkEndTime+" 23:59:59");
                flowHangInfo.put("link_man_tel", link_man_tel);
                flowHangInfo.put("link_man", link_man);
                flowHangInfo.put("link_id", LinkId);
                flowHangInfo.put("link_status", "1");
                flowHangInfo.put("userid", sysUser.getUserId());
            }
            flowHangInfo.put("BEGIN_TIME", beginTime);
            /*判断当前是否已经挂起*/
            if (StringUtils.isNotEmpty((String)flowTask.get("PARENT_TASKID"))) {
                List hangList = flowHangInfoService.getAllByJdbc("JBPM6_HANGINFO", new String[]{"TASK_ID", "LINK_STATUS"},
                        new Object[]{flowTask.get("PARENT_TASKID"), "1"});
                if (hangList != null && !hangList.isEmpty()) {
                    variables.put("OPER_SUCCESS", false);
                    variables.put("OPER_MSG", "办件已挂起，请勿重复操作！");
                } else {
                    String hangInfoId = flowHangInfoService.saveOrUpdate(flowHangInfo, "JBPM6_HANGINFO", "");
                    Map<String, Object> exeHandledescInfo = new HashMap<String, Object>();
                    String exeHandledesc = "";
                    if (StringUtils.isNotEmpty((String) flowTask.get("EXE_HANDLEDESC"))) {
                        exeHandledesc=flowTask.get("EXE_HANDLEDESC").toString();
                        exeHandledesc = exeHandledesc+"\r\n";
                    }
                    exeHandledesc = exeHandledesc+"["+flowTask.get("ASSIGNER_NAME").toString()+"]"
                            +"于"+gqsj+"挂起";
                    exeHandledescInfo.put("EXE_HANDLEDESC", exeHandledesc);
                    flowTaskService.saveOrUpdate(exeHandledescInfo, "JBPM6_TASK", currentTaskId);
                    flowTaskService.updateDescInfo(flowTask.get("PARENT_TASKID").toString(),exeHandledesc);
                    variables.put("OPER_SUCCESS", true);
                    variables.put("OPER_MSG", "提交成功");
                    //保存计时暂停指令数据
                    this.saveHandUpDataRes(hangInfoId, exeId);
                    //挂起成功通知公众用户
                    handUpSendMsg(exeId,gqsj,LinkId,link_man_tel);

                    // 开始保存工程建设项目审批事项办理详细信息
                    projectApplyService.saveAfterToXmspsxblxxxxb(9,exeId, sysUser.getFullname(), exeHandledesc);
                    // 结束保存工程建设项目审批事项办理详细信息
                }
            }
        }catch(Exception e){
            log.info(e.getMessage());
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 保存计时暂停指令数据
     * @author Danto Huang
     * @created 2019年5月10日 上午11:19:49
     * @param hangInfoId
     * @param exeId
     */
    private void saveHandUpDataRes(String hangInfoId,String exeId){
        Map<String, Object> handUpDataRes = new HashMap<String, Object>();
        handUpDataRes.put("EXE_ID", exeId);
        handUpDataRes.put("DATA_TYPE", "21");
        handUpDataRes.put("OPER_TYPE", "I");
        handUpDataRes.put("HAS_ATTR", 0);
        handUpDataRes.put("INTER_TYPE", "10");
        handUpDataRes.put("TASK_ID", hangInfoId);
        handUpDataRes.put("OTHER_STATUS", 3);
        flowTaskService.saveOrUpdate(handUpDataRes, "T_BSFW_SWBDATA_RES", null);
    }
    
    /**
     * 
     * 描述 挂起流程发送提醒给公众用户和后台人员
     * @author Rider Chen
     * @created 2017年6月22日 下午3:58:05
     * @param exeId
     * @param type
     */
    public void handUpSendMsg(String exeId,String gqsj,String linkId,String link_man_tel) {
        //获取流程实例
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String itemCode=flowExe.get("ITEM_CODE").toString();
        String lxdh =(String)executionService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[]{"ITEM_CODE"},new Object[]{itemCode}).get("LXDH");
        Map<String,Object> link=flowHangInfoService.getByJdbc
                ("T_WSBS_SERVICEITEM_LINK", new String[] { "record_id" }, new Object[] { linkId });
        StringBuffer mobileMsg=new StringBuffer();
        mobileMsg.append("您好！您在区综合审批服务平台上提交的申请件，遇特殊环节'");
        mobileMsg.append(link.get("LINK_NAME")).append("'需挂起操作，");
        mobileMsg.append("咨询电话：").append(link_man_tel).append(",");
        mobileMsg.append(lxdh).append(",");
        mobileMsg.append("申报号:").append(exeId).append("，标题为“");
        mobileMsg.append(flowExe.get("SUBJECT")).append("”");
        // 获取申请人类型
        String BSYHLX = (String) flowExe.get("BSYHLX");
        String sjhm = "";
        if (BSYHLX.equals("1")) {
            sjhm = (String) flowExe.get("SQRSJH");
        } else {
            sjhm = (String) flowExe.get("JBR_MOBILE");
        }
        if(StringUtils.isNotEmpty(sjhm)){
            log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
            SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
        }
        SysUser sysUser = AppUtil.getLoginUser();
        StringBuffer msg=new StringBuffer();
        msg.append("挂起通知，您在区综合审批服务平台上有办件被执行挂起操作，请注意挂起时限。申报号");
        msg.append(exeId).append("，标题为“").append(flowExe.get("SUBJECT")).append("”");
        sjhm=sysUser.getMobile();
        SendMsgUtil.saveSendMsg(msg.toString(), sjhm);
        SendMsgUtil.saveSendMsg(msg.toString(), link_man_tel);
    }
    /**
     * 
     * 描述 挂起流程发送提醒给公众用户
     * @author Rider Chen
     * @created 2017年6月22日 下午3:58:05
     * @param exeId
     * @param type
     */
    public void handUpSendMsgToWebSiteUser(String exeId,String gqsj) {
      //获取流程实例
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
               
        StringBuffer mobileMsg = new StringBuffer("您好,您于").append(flowExe.get("CREATE_TIME"));
        mobileMsg.append("在区行政服务中心申请的办件");
        mobileMsg.append("，申报号:").append(exeId);
        mobileMsg.append("，已于").append(gqsj).append("挂起，");
        mobileMsg.append("请及时查阅。");
        // 获取申请人类型
        String BSYHLX = (String) flowExe.get("BSYHLX");
        String sjhm = "";
        if (BSYHLX.equals("1")) {
            sjhm = (String) flowExe.get("SQRSJH");
        } else {
            sjhm = (String) flowExe.get("JBR_MOBILE");
        }
        if(StringUtils.isNotEmpty(sjhm)){
            log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
            SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
        }
    }

    /**
     * 
     * 描述
     * @author Water Guo
     * @created 2016-11-08 下午4:42:48
     * @param request
     * @return
     */
    @RequestMapping(params="restartFlowTaskExplain")
    public ModelAndView restartFlowTaskExplain(HttpServletRequest request){
        String selectTaskIds = request.getParameter("selectTaskIds");
        request.setAttribute("selectTaskIds", selectTaskIds);
        return new ModelAndView("hflow/execution/restartFlowTaskExplain");
    }
    /**
     * 
     * 描述
     * @author Water Guo
     * @created 2016-11-08 下午4:42:48
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="flowTaskExplain")
    public ModelAndView flowTaskExplain(HttpServletRequest request){
        String selectTaskIds = request.getParameter("selectTaskIds");
        
//        Map<String,Object> flowResult = null;
//        flowResult = executionService.getByJdbc("JBPM6_HANGINFO",
//                new String[]{"TASK_ID"},new Object[]{selectTaskIds});
        List<Map<String,Object>> flowResult = null;
        flowResult = executionService.getAllByJdbc("JBPM6_HANGINFO",
                new String[]{"TASK_ID"},new Object[]{selectTaskIds});
        for (Map<String, Object> map : flowResult) {
            String linkId=map.get("LINK_ID")==null?"":map.get("LINK_ID")
                    .toString();
            if(linkId!=null&&linkId!=""){
                Map<String,Object> linkResult = executionService.getByJdbc("T_WSBS_SERVICEITEM_LINK",
                        new String[]{"RECORD_ID"},new Object[]{linkId});
                map.put("LINK_NAME",linkResult.get("LINK_NAME")==null?"":
                        linkResult.get("LINK_NAME").toString());
            }
            String linkEndTime=map.get("LINK_END_TIME")==null?"":
                map.get("LINK_END_TIME").toString();
            map.put("OVERTIME", "否");
            //判断挂起的特殊环节有没有过期
            if(linkEndTime!=""){
                String endTime=map.get("END_TIME")==null?"":
                    map.get("END_TIME").toString();
                Date linkEndDate=DateTimeUtil.getDateOfStr(linkEndTime, "yyyy-MM-dd HH:mm:ss");
                //判断是否已结束的挂起任务，未结束则以当前时间作为比较对象
                if(endTime==""){
                    Date curDate=new Date();
                    if(curDate.after(linkEndDate)){
                        map.put("OVERTIME", "是");
                    }
                }else{
                    Date endDate=DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss");
                    if(endDate.after(linkEndDate)){
                        map.put("OVERTIME", "是");
                    }
                }
            }
            StringBuffer files=new StringBuffer();
            String ids = map.get("HANG_FILE_ID")==null?"":map.get("HANG_FILE_ID").toString();
            List<Map<String,Object>> fileList=fileAttachService.findListForResult(ids);
            for(Map<String, Object> filemap : fileList){
                files.append("<p style=\"margin-left: 5px; margin-right: 5px;line-height: 20px;\">");
                files.append("<a style=\"color: blue;\" href=\"javascript:AppUtil.downLoadFile(\'");
                files.append(filemap.get("FILE_ID")).append("\');\">");
                files.append(filemap.get("FILE_NAME")).append("</a>");
                files.append("</p>");
//                log.info(files);
            }
            map.put("FILES", files.toString());
        }
        String json = JSON.toJSONString(flowResult);
        request.setAttribute("resultJson", json);
        request.setAttribute("flowResult", flowResult);
        
        return new ModelAndView("hflow/execution/flowTaskExplain");
    }
    /**
     * 
     * 描述
     * @author Water Guo
     * @created 2016-11-08 下午4:42:48
     * @param request
     * @return
     */
    @RequestMapping(params="isHang")
    public void isHang(HttpServletRequest request, HttpServletResponse response){
        String taskId = request.getParameter("TASK_ID");
        List<Map<String, Object>> hangList = flowTaskService.getListByJdbc("JBPM6_HANGINFO",  taskId );
        String json = JSON.toJSONString(hangList);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述：挂起环节弹窗
     * @author Water Guo
     * @created 2017-9-7 上午10:11:41
     * @param request
     * @return
     */
    @RequestMapping(params="specialLinkExplain")
    public ModelAndView specialLinkExplain(HttpServletRequest request){
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        return new ModelAndView("hflow/execution/specialLinkExplain");
    }
    /**
     * 
     * 描述：获得可挂起天数
     * @author Water Guo
     * @created 2017-9-14 上午10:56:04
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getLinkDay")
    @ResponseBody
    public AjaxJson getLinkDay(HttpServletRequest request){
        AjaxJson json=new AjaxJson();
        String linkId=request.getParameter("linkId");
        Map<String,Object> map=flowTaskService.getByJdbc("T_WSBS_SERVICEITEM_LINK"
                , new String[]{"RECORD_ID"},new Object[]{ linkId});
        String day=map.get("LINK_LIMITTIME").toString();
        //返回挂起天数
        boolean isNum = day.matches("[0-9]+"); 
        if(!isNum||"0".equals(day)){
            day="error";
        }
        json.setJsonString(day);
        return json;
    }
    /**
     * 执行重启功能
     * @param request
     * @return
     */
    @RequestMapping(params = "reStart")
    @ResponseBody
    public void reStart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String selectTaskIds= request.getParameter("selectTaskIds");
        String Explain= request.getParameter("EXPLAIN");
        String fileid= request.getParameter("EXPLAIN_FILE_ID");
        try{
            flowTaskService.startUpFlowTask(selectTaskIds);
            flowHangInfoService.hangExplain(selectTaskIds,Explain,fileid);
            flowHangInfoService.endHangTime(selectTaskIds);
            
          //获取流程实例信息
            Map<String,Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{selectTaskIds});
            //String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String qdsj = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
            Map<String, Object> exeHandledescInfo = new HashMap<String, Object>();
            String exeHandledesc = "";
            if (StringUtils.isNotEmpty((String) flowTask.get("EXE_HANDLEDESC"))) {
                exeHandledesc=flowTask.get("EXE_HANDLEDESC").toString();
                exeHandledesc = exeHandledesc+"\r\n";
            }
            exeHandledesc = exeHandledesc+"["+flowTask.get("ASSIGNER_NAME").toString()+"]"
                    +"于"+qdsj+"启动";
            exeHandledescInfo.put("EXE_HANDLEDESC", exeHandledesc);
            flowTaskService.saveOrUpdate(exeHandledescInfo, "JBPM6_TASK", selectTaskIds);
            flowTaskService.updateDescInfo(flowTask.get("PARENT_TASKID").toString(), exeHandledesc);
            
            this.saveRestartDataRes((String) flowTask.get("PARENT_TASKID"), (String) flowTask.get("EXE_ID"));
            handUpFinishSendMsg(selectTaskIds);
            variables.put("success", true);
            variables.put("msg", "操作成功");
            
            SysUser sysUser = AppUtil.getLoginUser();
            // 开始保存工程建设项目审批事项办理详细信息
            projectApplyService.saveAfterToXmspsxblxxxxb(10,(String) flowTask.get("EXE_ID"), sysUser.getFullname(),
                    exeHandledesc);
            // 结束保存工程建设项目审批事项办理详细信息
        }catch(Exception e){
            log.error(e.getMessage(),e);
            variables.put("success", false);
            variables.put("msg", "提交失败,请联系系统管理员!");
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
        
    /**
     * 
     * 保存计时恢复指令数据
     * @author Danto Huang
     * @created 2019年5月10日 上午11:19:49
     * @param hangInfoId
     * @param exeId
     */
    private void saveRestartDataRes(String selectTaskIds,String exeId){
        Map<String, Object> handUpDataRes = new HashMap<String, Object>();
        Map<String,Object> hangInfo = flowTaskService.getHangInfo(selectTaskIds);
        handUpDataRes.put("EXE_ID", exeId);
        handUpDataRes.put("DATA_TYPE", "23");
        handUpDataRes.put("OPER_TYPE", "I");
        handUpDataRes.put("HAS_ATTR", 0);
        handUpDataRes.put("INTER_TYPE", "10");
        handUpDataRes.put("TASK_ID", hangInfo.get("HANG_ID"));
        String linkId=hangInfo.get("LINK_ID")==null?"":hangInfo.get("LINK_ID").toString();
        if(StringUtils.isEmpty(linkId)){
            //投资项目退回补件对接省网，补充数据
            handUpDataRes.put("OTHER_STATUS", 2);
            SysUser sysUser=AppUtil.getLoginUser();
            if(sysUser!=null){
                String userId=sysUser.getUserId();
                Map<String,Object> handInfo=new HashMap<String,Object>();
                handInfo.put("USERID",userId);
                flowTaskService.saveOrUpdate(handInfo,"jbpm6_hanginfo",
                        StringUtil.getString( hangInfo.get("HANG_ID")));
            }
        }else{
            handUpDataRes.put("OTHER_STATUS", 4);
            flowTaskService.saveOrUpdate(handUpDataRes, "T_BSFW_SWBDATA_RES", null);
        }
    }
    
    /**
     * 
     * 描述 特殊环节挂起流程结束发送提醒给公众用户和后台人员
     * @author Rider Chen
     * @created 2017年6月22日 下午3:58:05
     * @param taskId
     */
    public void handUpFinishSendMsg(String taskId) {
        //获取exeId
        String exeId = executionService.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{taskId}).get("EXE_ID").toString();
       //获取taksParentId
        String taksParentId = executionService.getByJdbc("JBPM6_TASK",
                new String[]{"TASK_ID"},new Object[]{taskId}).get("PARENT_TASKID").toString();
        //获取挂起信息
        Map<String,Object> hangInfo=flowTaskService.getHangInfo(taksParentId);
        //挂起userID
        String userId=hangInfo.get("USERID")==null?"":
            hangInfo.get("USERID").toString();
        String linkId=StringUtil.getString(hangInfo.get("LINK_ID"));
        //新挂起件有userId,短信通知，旧挂起件不需要短信通知
        if(StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(linkId)){
            String link_man_tel=hangInfo.get("LINK_MAN_TEL").toString();
            //获取流程实例
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            String itemCode=flowExe.get("ITEM_CODE").toString();
            Map<String,Object> maplxdh = executionService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"},new Object[]{itemCode});
            String lxdh=maplxdh.get("LXDH")==null?"":maplxdh.get("LXDH").toString();
            Map<String,Object> link=flowHangInfoService.getByJdbc
                    ("T_WSBS_SERVICEITEM_LINK", new String[] { "record_id" }, new Object[] { linkId });
            StringBuffer mobileMsg=new StringBuffer();
            mobileMsg.append("您好！您在区综合审批服务平台上提交的申请件，特殊环节'");
            mobileMsg.append(link.get("LINK_NAME")).append("'结束并进入下一环节，");
            mobileMsg.append("咨询电话：").append(link_man_tel).append(",");
            mobileMsg.append(lxdh).append(",");
            mobileMsg.append("申报号:").append(exeId).append("，标题为“");
            mobileMsg.append(flowExe.get("SUBJECT")).append("”");
            // 获取申请人类型
            String BSYHLX = (String) flowExe.get("BSYHLX");
            String sjhm = "";
            if (BSYHLX.equals("1")) {
                sjhm = (String) flowExe.get("SQRSJH");
            } else {
                sjhm = (String) flowExe.get("JBR_MOBILE");
            }
            if(StringUtils.isNotEmpty(sjhm)){
                log.info("发送号码:"+sjhm+",的内容是:"+mobileMsg.toString());
                SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
            }
            StringBuffer msg=new StringBuffer();
            msg.append("挂起结束通知,您在区综合审批服务平台上有办件被结束挂起操作，请注意及时办理。申报号");
            msg.append(exeId).append("，标题为“").append(flowExe.get("SUBJECT")).append("”");
            //获取挂起人电话
            sjhm=executionService.getByJdbc("T_MSJW_SYSTEM_SYSUSER", new String[]{"USER_ID"}, 
                    new Object[]{userId}).get("MOBILE").toString();
            SendMsgUtil.saveSendMsg(msg.toString(), sjhm);
            SendMsgUtil.saveSendMsg(msg.toString(), link_man_tel);
        }
    }
    
    /**
     * 
     * 描述    食药登记待处理列表
     * @author Danto Huang
     * @created 2019年2月22日 下午3:56:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "fdaNeedMeHandle")
    public void fdaNeedMeHandle(HttpServletRequest request,
            HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME","ASC");
        List<Map<String, Object>> list = flowTaskService.findFdaNeedMeHandle(filter,haveHandUp);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

