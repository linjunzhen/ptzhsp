/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.service.BdcRegisterInterfaceService;
import net.evecom.platform.bdc.service.BdcYgspfService;
import net.evecom.platform.bsfw.dao.SwbDataResDao;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.ems.service.EmsService;
import net.evecom.platform.ems.util.EmsSend;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.*;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述  流程实例Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/executionController")
public class ExecutionController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExecutionController.class);
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * flowFormService
     */
    @Resource
    private FlowFormService flowFormService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * buttonRightService
     */
    @Resource
    private ButtonRightService buttonRightService;
    /**
     * fieldRightService
     */
    @Resource
    private FieldRightService fieldRightService;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * nodeAuditerService
     */
    @Resource
    private NodeAuditerService nodeAuditerService;
    /**
     * nodeConfigService
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;

    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * againMaterService
     */
    @Resource
    private AgainMaterService againMaterService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 
     */
    @Resource
    private BdcYgspfService bdcYgspfService;
    /**
     * 
     */
    @Resource
    private EmsService emsService;

    /**
     * 所引入的dao
     */
    @Resource
    private SwbDataResDao swbDataResDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/1 19:25:00
     * @param
     * @return
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterService;
    /**
     * 引入Service
     */
    @Resource
    private BdcRegisterInterfaceService bdcRegisterInterfaceService;
    
    /**
     * 
     * 描述 跳转到流程办理界面
     * @author Flex Hu
     * @created 2015年8月21日 上午10:42:27
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goHandle")
    public ModelAndView goHandle(HttpServletRequest request) {

        String exeId = request.getParameter("exeId");
        String taskId = request.getParameter("taskId");
        Map<String, Object> revoke = executionService.getByJdbc("JBPM6_REVOKE",
                new String[] { "EXE_ID", "REVOKE_STATUS" }, new Object[] { exeId, 0 });
        if (null != revoke && revoke.size() > 0) {
            request.setAttribute("revoke", revoke);
            request.setAttribute("taskId", taskId);
            return new ModelAndView("hflow/execution/sqcb");
        } else {
            this.setFlowValues(request, "false");
            return new ModelAndView("hflow/execution/flowHandle");
        }
    }

    /**
     * 
     * 描述 调整到流程明细界面
     * @author Flex Hu
     * @created 2015年8月21日 上午10:42:27
     * @param request
     * @return
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(HttpServletRequest request) {
        this.setFlowValues(request, "true");
        // 是否归档流程查看
        request.setAttribute("isFiled", request.getParameter("isFiled"));
        return new ModelAndView("hflow/execution/flowDetail");
    }

    /**
     * 
     * 描述 调整到流程明细界面-预审--》并联审查
     * @author Flex Hu
     * @created 2015年8月21日 上午10:42:27
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goDetailGover")
    public ModelAndView goDetailGover(HttpServletRequest request) {
        this.setFlowValues(request, "true");
        return new ModelAndView("hflow/execution/flowDetailGover");
    }
    
    /**
     * 描述 不动产缴费发证明细页面
     * @author Keravon Feng
     * @created 2019年12月6日 下午4:26:03
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goBdcFzjfmx")
    public ModelAndView goBdcFzjfmx(HttpServletRequest request) {
        this.setFlowValues(request, "true");
        request.setAttribute("bdc_optype", request.getParameter("bdc_optype"));
        return new ModelAndView("hflow/execution/bdcfzjfmx");
    }

    /**
     * 
     * 描述 设置流程的值
     * @author Flex Hu
     * @created 2015年8月21日 下午12:08:42
     * @param request
     * @param isQueryDetail
     */
    @SuppressWarnings("unchecked")
    private void setFlowValues(HttpServletRequest request, String isQueryDetail) {
        String defKey = request.getParameter("defKey");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String acceptway = request.getParameter("acceptway");
        String takenoway = request.getParameter("takenoway");
        String lineId = request.getParameter("lineId");
        String zjlx = request.getParameter("zjlx");
        String itemdetail_id = request.getParameter("itemdetail_id");
        String subBusClass = request.getParameter("subBusClass");
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");//投资项目编号（工程建设项目申报）
        String bdc_optype = request.getParameter("bdc_optype");//不动产全流程业务证书收费发证状态
        String isKfsywsl = request.getParameter("isKfsywsl");//不动产全流程业务证书收费发证状态
        String STAGE_ID = request.getParameter("STAGE_ID");//所属阶段ID（工程建设项目申报）
        Map<String, Object> flowForm = null;
        Map<String, Object> flowDef = null;
        Map<String, Object> eflowObj = null;
        Map<String, Object> flowExe = null;
        // 开始定义流程信息对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, isQueryDetail, request);
        if (flowAllObj.get("busRecord") != null) {
            Map<String, Object> busRecord = (Map<String, Object>) flowAllObj.get("busRecord");
            // 获取多证合一数据
            Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                    new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("BUS_RECORDID") });
            if (null != multiple) {
                busRecord.putAll(multiple);
            }
            // 查询是否已完成省网数据回流对接
            Map<String, Object> provinceData = executionService.getByJdbc("MZT_ACCEPT_APASINFOBASE",
                    new String[] { "SN" }, new Object[] { exeId });
            if (provinceData != null) {
                busRecord.put("IS_PROVINCE_ACCEPTED", "1");
            }
            request.setAttribute("busRecord", busRecord);
        }
        if (flowAllObj.get("EFLOW_FLOWEXE") != null) {
            flowExe = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWEXE");
            request.setAttribute("EFLOW_FLOWEXE", flowExe);
        }
        if (flowAllObj.get("EFLOWOBJ") != null) {
            eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            request.setAttribute("EFLOWOBJ", eflowObj);
        }
        if (flowAllObj.get("EFLOW_FLOWOBJ") != null) {
            request.setAttribute("EFLOW_FLOWOBJ", flowAllObj.get("EFLOW_FLOWOBJ"));
        }
        if (flowAllObj.get("EFLOW_BUTTONRIGHTS") != null) {
            request.setAttribute("EFLOW_BUTTONRIGHTS", flowAllObj.get("EFLOW_BUTTONRIGHTS"));
        }
        if (flowAllObj.get("EFLOW_FLOWDEF") != null) {
            flowDef = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF");
            request.setAttribute("EFLOW_FLOWDEF", flowDef);
        }
        if (flowAllObj.get("EFLOW_FLOWFORM") != null) {
            flowForm = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWFORM");
            request.setAttribute("EFLOW_FLOWFORM", flowForm);
        }
        if (flowAllObj.get("EFLOW_QUERYBTNRIGHTS") != null) {
            request.setAttribute("EFLOW_QUERYBTNRIGHTS", flowAllObj.get("EFLOW_QUERYBTNRIGHTS"));
        }
        // 获取绑定的表单
        String itemCode = request.getParameter("itemCode");
        if (StringUtils.isEmpty(itemCode) && StringUtils.isNotEmpty(exeId)) {
            itemCode = (String) flowExe.get("ITEM_CODE");
        }
        Map<String, Object> existParams = new HashMap<String, Object>();
        existParams.put("itemCode", itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("lineId", lineId);
        // 开始构建IFRAME的URL
        // 获取表单的URL
        String formUrl = this.flowFormService.getUrlByFlowForm(flowForm, existParams);
        StringBuffer iframeUrl = new StringBuffer(formUrl);
        if (formUrl.indexOf("?") != -1) {
            iframeUrl.append("&");
        } else {
            iframeUrl.append("?");
        }
        iframeUrl.append("defId=").append((String) flowDef.get("DEF_ID"));
        iframeUrl.append("&exeId=").append((String) eflowObj.get("EFLOW_EXEID"));
        if (eflowObj.get("EFLOW_FLOWSTAGE") != null) {
            iframeUrl.append("&flowstage=").append((String) eflowObj.get("EFLOW_FLOWSTAGE"));
        }
        if (StringUtils.isNotEmpty(acceptway)) {
            iframeUrl.append("&acceptway=").append(acceptway);
        }
        if (StringUtils.isNotEmpty(takenoway)) {
            iframeUrl.append("&takenoway=").append(takenoway);
        }
        if (StringUtils.isNotEmpty(lineId)) {
            iframeUrl.append("&lineId=").append(lineId);
        }
        if (StringUtils.isNotEmpty(zjlx)) {
            iframeUrl.append("&zjlx=").append(zjlx);
        }
        if (StringUtils.isNotEmpty(itemdetail_id)) {
            iframeUrl.append("&itemdetail_id=").append(itemdetail_id);
        }
        if (StringUtils.isNotEmpty(subBusClass)) {
            iframeUrl.append("&subBusClass=").append(subBusClass);
        }
        if (isQueryDetail.equals("true")) {
            iframeUrl.append("&EFLOW_ISQUERYDETAIL=" + isQueryDetail);
        }
        if("1".equals(request.getParameter("isFiled"))) {
            iframeUrl.append("&isFiled=1");
        }
        if (StringUtils.isNotEmpty(PROJECT_CODE)) {
            iframeUrl.append("&PROJECT_CODE=").append(PROJECT_CODE);
        }
        if (StringUtils.isNotEmpty(STAGE_ID)) {
            iframeUrl.append("&STAGE_ID=").append(STAGE_ID);
        }
        if (StringUtils.isNotEmpty(bdc_optype)) {
            iframeUrl.append("&bdc_optype=").append(bdc_optype);
        }
        if (StringUtils.isNotEmpty(isKfsywsl)) {
            iframeUrl.append("&isKfsywsl=").append(isKfsywsl);
        }
        String categoryId = request.getParameter("categoryId");
        if(categoryId!=null && !"".equals(categoryId)) {
            iframeUrl.append("&categoryId=").append(categoryId);
        }
        if (StringUtils.isNotEmpty(itemCode)) {
            List<Map<String, Object>> bindForms = this.serviceItemService.findBindForms(exeId, itemCode);
            if (bindForms != null && bindForms.size() > 0) {
                request.setAttribute("bindForms", bindForms);
            }
        }
        request.setAttribute("IFRAME_URL", iframeUrl.toString());

    }
    
    /**
     * 跳转启动流程实例信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goStart")
    public ModelAndView goStart(HttpServletRequest request) {
        this.setFlowValues(request, "false");
        return new ModelAndView("hflow/execution/flowStart");
    }

    /**
     * 跳转信息界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goMaterUpload")
    public ModelAndView goMaterUpload(HttpServletRequest request) {
        this.setFlowValues(request, "false");
        return new ModelAndView("hflow/execution/flowStart");
    }

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
        this.executionService.deleteByExeId(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 流程实例记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 方法 出件
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiUpdateCJZT")
    @ResponseBody
    public AjaxJson multiUpdateCJZT(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] exeIds = selectColNames.split(",");
        for (String exeId : exeIds) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("CJZT", "1");
            String recordId = jbpmService.saveOrUpdate(data, "JBPM6_EXECUTION", exeId);
        }
        j.setMsg("修改取件状态成功");
        return j;
    }

    /**
     * 方法del 删除流程草稿
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelDraft")
    @ResponseBody
    public AjaxJson multiDelDraft(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String creatorId = "";
        if (selectColNames != null) {
            Map<String, Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"}, new Object[]{selectColNames});
            creatorId = (String) exeMap.get("CREATOR_ID");
        }
        Map<String, Object> loginMember = AppUtil.getLoginMember();
        String userId = (String) loginMember.get("USER_ID");
        if (creatorId.equals(userId)) {
            this.executionService.deleteByExeId(selectColNames.split(","));
        } else {
            j.setMsg("删除失败，用户错误");
            return j;
        }
        sysLogService.saveLogForMember("删除了ID为[" + selectColNames + "]的 流程实例记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 方法multiEnd
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiEnd")
    @ResponseBody
    public AjaxJson multiEnd(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        this.executionService.endByExeId(selectColNames.split(","), "");
        sysLogService.saveLog("终结了ID为[" + selectColNames + "]的 流程实例记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("终结成功");
        return j;
    }

    /**
     * 执行预审不通过方法
     * @param request
     * @return
     */
    @RequestMapping(params = "preNoPass")
    @ResponseBody
    public void preNoPass(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            String exeId = (String) variables.get("EFLOW_EXEID");
            // 获取意见内容
            String EFLOW_HANDLE_OPINION = (String) variables.get("EFLOW_HANDLE_OPINION");
            this.executionService.updateExeToNoPass(exeId, EFLOW_HANDLE_OPINION, variables);
            variables.put("EFLOW_APPLY_STATUS", "7");
            bdcYgspfService.saveShyjData(variables);
            variables.put("OPER_SUCCESS", true);
            variables.put("OPER_MSG", "提交成功");
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
            log.error(e.getMessage());
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }

    /**
     * 执行流程方法
     * @param request
     * @return
     */
    @RequestMapping(params = "exeFlow")
    @ResponseBody
    public void exeFlow(HttpServletRequest request, HttpServletResponse response) {
        exeSubmitFlow(request, response);
    }

    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年4月17日 上午9:05:02
     * @param request
     * @param response
     */
    @RequestMapping("/mobileExeFlow")
    @ResponseBody
    public void mobileExeFlow(HttpServletRequest request, HttpServletResponse response) {
        exeSubmitFlow(request, response);
    }

    /**
     * 描述
     * @author Flex Hu
     * @created 2016年4月18日 下午4:11:05
     * @param request
     * @param response
     */
    private void exeSubmitFlow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            // 获取申请方式
            String sqfs = (String) variables.get("SQFS");
            // 如果是窗口收件方式
            if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
                // 获取当前登录用户信息
                SysUser sysUser = AppUtil.getLoginUser();
                variables.put("EFLOW_CREATORID", sysUser.getUserId());
                variables.put("EFLOW_CREATORACCOUNT", sysUser.getUsername());
                variables.put("EFLOW_CREATORNAME", sysUser.getFullname());
                variables.put("EFLOW_CREATORPHONE", sysUser.getMobile());
            }
            String exeId = (String) variables.get("EFLOW_EXEID");
            if (StringUtils.isEmpty(exeId)) {
                String randomNum = StringUtil.getFormatNumber(6, StringUtil.getRandomIntNumber(1000000) + "");
                variables.put("BJCXMM", randomNum);
            }
            String itemName = (String) variables.get("ITEM_NAME");
            // 企业名称
            String COMPANY_NAME = (String) variables.get("COMPANY_NAME");
            // 获取申报名称
            String sbmc = (String) variables.get("SBMC");
            StringBuffer subject = new StringBuffer("");
            if (StringUtils.isNotEmpty(sbmc)) {
                if (StringUtils.isNotEmpty(COMPANY_NAME)) {
                    subject.append(sbmc).append("（").append(COMPANY_NAME).append("）");
                } else {
                    subject.append(sbmc).append("（").append(itemName).append("）");
                }
            } else {
                String projectName = (String) variables.get("PROJECT_NAME");
                if (StringUtils.isNotEmpty(projectName)) {
                    subject.append(projectName).append("（").append(itemName).append("）");
                } else {
                    subject.append(COMPANY_NAME).append("（").append(itemName).append("）");
                }
            }
            variables.put("EFLOW_SUBJECT", subject.toString());
            // 获取当前操作人的任务ID
            String EFLOW_CURRENTTASK_ID = (String) variables.get("EFLOW_CURRENTTASK_ID");
            boolean isPassTime = false;
            if (StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)) {
                Map<String, Object> jbpmTask = flowTaskService.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { EFLOW_CURRENTTASK_ID });
                if (jbpmTask != null) {
                    // 获取状态
                    String TASK_STATUS = jbpmTask.get("TASK_STATUS").toString();
                    if (!TASK_STATUS.equals("1")) {
                        isPassTime = true;
                    }
                }
            }
            if (isPassTime) {
                // variables = jbpmService.doFlowJob(variables);
                variables.put("OPER_SUCCESS", true);
                variables.put("OPER_MSG", "该流程任务已经被其他经办人处理,您的流程任务被取消!");
            } else {
                variables = jbpmService.doFlowJob(variables);
                variables.put("OPER_SUCCESS", true);
                String isTempSave = (String) variables.get("EFLOW_ISTEMPSAVE");
                if (isTempSave.equals("1")) {
                    variables.put("OPER_MSG", "操作成功");
                } else {
                    String msg = "提交成功";
                    if ((boolean) variables.get("isStartFlow")) {
                        try {
                            msg = executionService.sendEms(variables, msg);
                        } catch (Exception e) {
                            log.error("提交流程Ems生成失败" + e.getMessage());
                        }
                    }

                    variables.put("OPER_MSG", msg);
                }

                // 绑定审核材料
                String FLOW_USER_MATER_ID = variables.get("FLOW_USER_MATER_ID") == null ? ""
                        : variables.get("FLOW_USER_MATER_ID").toString();
                if (StringUtils.isNotEmpty(FLOW_USER_MATER_ID)) {
                    // 获取下一环节经办人的JSON
                    String EFLOW_NEXTSTEPSJSON = (String) variables.get("EFLOW_NEXTSTEPSJSON");
                    if (StringUtils.isNotEmpty(EFLOW_NEXTSTEPSJSON)) {
                        Map<String, Object> map = JSON.parseObject(EFLOW_NEXTSTEPSJSON, Map.class);
                        String nextTaskName = map.keySet().toArray(new String[map.keySet().size()])[0];
                        applyMaterService.updateFlowUserMaterStatus(FLOW_USER_MATER_ID, nextTaskName);
                    }
                }
                // 获取二次审核材料的JSON
                String EFLOW_AGAINMATERFILEJSON = (String) variables.get("EFLOW_AGAINMATERFILEJSON");
                if (StringUtils.isNotEmpty(EFLOW_AGAINMATERFILEJSON)) {
                    againMaterService.saveDatas(EFLOW_AGAINMATERFILEJSON, variables);
                }
                // 获取办件办结时录入办件结果信息
                String EFLOW_DESTTOEND = (String) variables.get("EFLOW_DESTTOEND");
                String filenum = (String) variables.get("xkfile_num");
                lastStep(request, variables, EFLOW_DESTTOEND, filenum);
                createSocialForm(variables);// 创建社会事业局附件
                // 保存平潭网格政务服务待办事项
                setItemTask(variables);

                if ("2".equals(variables.get("EFLOW_EXERUNSTATUS")) || "3".
                        equals(variables.get("EFLOW_EXERUNSTATUS"))) {
                    this.sendUploadMaterToLicense(variables);
                    this.sendJbpmRstToLicense(variables);
                    
                    //已办结流程，非实际办理人任务记录删除
                    flowTaskService.deleteUndoTaskInfosByExeId(exeId);
                }
                if("-1".equals(isTempSave)) {
                    //不动产省网对接-推送受理信息
                      String curexeRunningNodeNames = "";
                      if(variables.get("EFLOW_CUREXERUNNINGNODENAMES")!=null) {
                          curexeRunningNodeNames = (String)variables.get("EFLOW_CUREXERUNNINGNODENAMES");
                      }

                      String ITEM_CODE = (String) variables.get("ITEM_CODE");
                      String  EFLOW_BUSTABLENAME  = StringUtil.getString(variables.get("EFLOW_BUSTABLENAME"));
                      //国有建设用地使用权及房屋所有权转移登记+转移拆分后的6个事项均需要在收费或登簿环节传受理信息
                      if("569262478QR00310".equals(ITEM_CODE)
                              || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0) {
                          if("收费".equals(curexeRunningNodeNames) || "登簿".equals(curexeRunningNodeNames)) {
                              bdcRegisterInterfaceService.appayDataStorage(variables);
                          }
                      }else {
                          if("受理".equals(curexeRunningNodeNames)) {
                              bdcRegisterInterfaceService.appayDataStorage(variables);
                          }
                      }
                  }
            }
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            String signMsg=StringUtil.getString(variables.get("SIGN_MSG"));
            if(StringUtils.isNotEmpty(signMsg)){
                variables.put("OPER_MSG",signMsg);
            }else{
                variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
            }
            log.error("", e);
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    上传材料推送证照库
     * @author Danto Huang
     * @created 2020年3月9日 下午3:24:39
     * @param variables
     */
    private void sendUploadMaterToLicense(Map<String,Object> variables){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        Properties projectproperties = FileUtil.readProperties("project.properties");
        String devbase_url = properties.getProperty("devbase_url");
        String grantcode = properties.getProperty("grantcode");
        String uploadFileUrl = projectproperties.getProperty("uploadFileUrl");
        String exeId = variables.get("EFLOW_EXEID").toString();
        Map<String,Object> exe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[] { "EXE_ID" }, new Object[] { exeId });
        String busTableName = (String) exe.get("BUS_TABLENAME");
        String busRecordId = (String) exe.get("BUS_RECORDID");
        
        Map<String,Object> holder = new HashMap<String, Object>();
        String bsyhlx = exe.get("BSYHLX").toString();
        if(bsyhlx.equals("1")){
            holder.put("HOLDER_TYPE", "2");
            holder.put("HOLDER_NAME", exe.get("SQRMC"));
            holder.put("HOLDER_CODE", exe.get("SQRSFZ"));
            holder.put("HOLDER_CODE_TYPE",this.formateZJLX((String) exe.get("SQRZJLX"), bsyhlx) );
        }else if(bsyhlx.equals("2")){
            holder.put("HOLDER_TYPE", "1");
            holder.put("HOLDER_NAME", exe.get("SQJG_NAME"));
            /*if(exe.get("SQJG_CREDIT_CODE")!=null){
                holder.put("HOLDER_CODE", exe.get("SQJG_CREDIT_CODE"));
                holder.put("HOLDER_CODE_TYPE", "C2");
            }else{*/
            holder.put("HOLDER_CODE", exe.get("SQJG_CODE"));
            holder.put("HOLDER_CODE_TYPE",this.formateZJLX((String) exe.get("SQRZJLX"), bsyhlx) );
            //}
        }
        List<Map<String,Object>> holderList = new ArrayList<Map<String,Object>>();
        holderList.add(holder);
        List<Map<String,Object>> itemMaters = applyMaterService.findByItemCodes((String) exe.get("ITEM_CODE"), exeId);
        List<Map<String,Object>> attachs = fileAttachService.findList(busTableName, busRecordId);
        if(attachs!=null&&attachs.size()>0){
            for(Map<String,Object> mater : itemMaters){
                if(mater!=null&&mater.get("BINDCATALOG_CODE")!=null){
                    Map<String,Object> postParam = new HashMap<String,Object>();
                    postParam.put("grantcode", grantcode);
                    postParam.put("servicecode", "saveLicense");
                    postParam.put("TYPE_CODE", mater.get("BINDCATALOG_CODE"));
                    postParam.put("SERIALNUMBER",
                            exe.get("EXE_ID").toString().concat((String) mater.get("MATER_CODE")));
                    postParam.put("INPUTPATH", "3");
                    postParam.put("VALID_DATE_TYPE", "0");
                    postParam.put("HOLDER_JSON", JSON.toJSONString(holderList));
                    List<Map<String,Object>> fileList = new ArrayList<Map<String,Object>>();
                    for(Map<String,Object> attach : attachs){
                        String attachKey = (String) attach.get("ATTACH_KEY");
                        if(!attachKey.equals(mater.get("MATER_CODE"))){
                            continue;
                        }else if("1".equals(attach.get("SQFS"))&&"1".equals(attach.get("SFSQ"))){
                            Map<String,Object> file = new HashMap<String, Object>();
                            file.put("FILE_NAME", attach.get("FILE_NAME"));
                            if("jpg,jpeg,bpm,gif,png,tif".contains((String) attach.get("FILE_TYPE"))){
                                file.put("ATTACH_TYPE", "image");
                            }else{
                                file.put("ATTACH_TYPE", "attach");
                            }
                            file.put("FILE_PATH", uploadFileUrl.concat((String) attach.get("FILE_PATH")));
                            file.put("FILE_DOWNLOAD_PATH", uploadFileUrl.concat((String) attach.get("FILE_PATH")));
                            fileList.add(file);
                        }
                    }
                    postParam.put("FILE_JSON", JSON.toJSONString(fileList));
                    String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
                    log.info(respContent);
                }
            }
        }
        
        
    }
    /**
     * 
     * 描述    上传办理结果到证照库
     * @author Danto Huang
     * @created 2020年3月13日 上午10:59:13
     * @param variables
     */
    private void sendJbpmRstToLicense(Map<String,Object> variables){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        Properties projectproperties = FileUtil.readProperties("project.properties");
        String devbase_url = properties.getProperty("devbase_url");
        String grantcode = properties.getProperty("grantcode");
        String uploadFileUrl = projectproperties.getProperty("uploadFileUrl");
        String exeId = variables.get("EFLOW_EXEID").toString();
        Map<String,Object> exe = executionService.getByJdbc("JBPM6_EXECUTION",
                new String[] { "EXE_ID" }, new Object[] { exeId });
        Map<String, Object> item = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new Object[] { exe.get("ITEM_CODE") });
        if(item.get("RESULT_BINDCATALOG_CODE")!=null&&variables.get("xkfile_num")!=null){            
            Map<String,Object> postParam = new HashMap<String,Object>();
            postParam.put("grantcode", grantcode);
            postParam.put("servicecode", "saveLicense");
            postParam.put("TYPE_CODE", item.get("RESULT_BINDCATALOG_CODE"));
            postParam.put("SERIALNUMBER",variables.get("xkfile_num"));
            postParam.put("INPUTPATH", "3");
            postParam.put("PUBLISHDATE", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
            if(variables.get("islong_term")!=null&&variables.get("islong_term")==null){
                postParam.put("VALID_DATE_TYPE", "1");
            }else{
                postParam.put("VALID_DATE_TYPE", "0");
            }
            postParam.put("VALIDFROMDATE", variables.get("effect_time"));
            postParam.put("VALIDUNTILDATE", variables.get("close_time"));
            
            Map<String,Object> holder = new HashMap<String, Object>();
            String bsyhlx = exe.get("BSYHLX").toString();
            if(bsyhlx.equals("1")){
                holder.put("HOLDER_TYPE", "2");
                holder.put("HOLDER_NAME", exe.get("SQRMC"));
                holder.put("HOLDER_CODE", exe.get("SQRSFZ"));
                holder.put("HOLDER_CODE_TYPE",this.formateZJLX((String) exe.get("SQRZJLX"), bsyhlx) );
            }else if(bsyhlx.equals("2")){
                holder.put("HOLDER_TYPE", "1");
                holder.put("HOLDER_NAME", exe.get("SQJG_NAME"));
                /*if(exe.get("SQJG_CREDIT_CODE")!=null){
                    holder.put("HOLDER_CODE", exe.get("SQJG_CREDIT_CODE"));
                    holder.put("HOLDER_CODE_TYPE", "C2");
                }else{*/
                holder.put("HOLDER_CODE", exe.get("SQJG_CODE"));
                holder.put("HOLDER_CODE_TYPE",this.formateZJLX((String) exe.get("SQRZJLX"), bsyhlx) );
                //}
            }
            List<Map<String,Object>> holderList = new ArrayList<Map<String,Object>>();
            holderList.add(holder);
            postParam.put("HOLDER_JSON", JSON.toJSONString(holderList));
            
            List<Map<String,Object>> fileList = new ArrayList<Map<String,Object>>();
            if (variables.get("RESULT_FILE_ID")!=null) {
                String[] ids = ((String)variables.get("RESULT_FILE_ID")).split(";");
                for(String id : ids){
                    Map<String, Object> attach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                            new String[] { "FILE_ID" }, new Object[] { id });
                    Map<String,Object> file = new HashMap<String, Object>();
                    file.put("FILE_NAME", attach.get("FILE_NAME"));
                    if("jpg,jpeg,bpm,gif,png,tif".contains((String) attach.get("FILE_TYPE"))){
                        file.put("ATTACH_TYPE", "image");
                    }else{
                        file.put("ATTACH_TYPE", "attach");
                    }
                    file.put("FILE_PATH", uploadFileUrl.concat((String) attach.get("FILE_PATH")));
                    file.put("FILE_DOWNLOAD_PATH", uploadFileUrl.concat((String) attach.get("FILE_PATH")));
                    fileList.add(file);
                }
                postParam.put("FILE_JSON", JSON.toJSONString(fileList));
            }
            String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
            log.info(respContent);
        }
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月12日 下午8:43:44
     * @param zjlx
     * @param bsyhlx
     * @return
     */
    private String formateZJLX(String zjlx,String bsyhlx){
        if (zjlx != null) {
            if (zjlx.equals("SF")) {
                return "P1";
            } else if (zjlx.equals("TWTX")) {
                return "P2";
            } else if (zjlx.equals("GATX")) {
                return "P3";
            } else if (zjlx.equals("HZ")) {
                return "P4";
            } else if (zjlx.equals("HZ")) {
                return "P4";
            } else if (zjlx.equals("JGDM")) {
                return "C1";
            } else if (zjlx.equals("YYZZ")) {
                return "C3";
            } else if (bsyhlx.equals("1")) {
                return "P9";
            } else if (bsyhlx.equals("2")) {
                return "C9";
            } else {
                return null;
            }
        } else {
            return "C9";
        }

    }
    
    /**
     * 创建社会事业局附件
     * @param variables
     */
    private void createSocialForm(Map<String, Object> variables) {
        String exeId = (String) variables.get("EFLOW_EXEID");
        Map<String, Object> execution = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        String itemCode = String.valueOf(execution.get("ITEM_CODE"));
        String dicItemCode1 = dictionaryService.getDicCode("wxConfig", "socialItemCode1");
        String dicItemCode2 = dictionaryService.getDicCode("wxConfig", "socialItemCode2");
        if (StringUtils.isNotEmpty(dicItemCode1) && StringUtils.isNotEmpty(dicItemCode2)) {
            if (dicItemCode1.indexOf(itemCode) > -1 || dicItemCode2.indexOf(itemCode) > -1) {
                flowEventService.createSocialForm(exeId);// 创建社会事业局附件
            }
        }
    }

    /**
     *
     * @param request
     * @param variables
     * @param EFLOW_DESTTOEND
     * @param filenum
     */
    private void lastStep(HttpServletRequest request, Map<String, Object> variables, String EFLOW_DESTTOEND,
            String filenum) {
        if ("true".equals(EFLOW_DESTTOEND) && StringUtils.isNotEmpty(filenum)) {// 最后环节
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("XKFILE_NUM", variables.get("xkfile_num"));
            data.put("XKFILE_NAME", variables.get("xkfile_name"));
            data.put("XKDEPT_NAME", variables.get("xkdept_name"));
            data.put("XKDEPT_ID", variables.get("xkdept_id"));
            data.put("EFFECT_TIME", variables.get("effect_time"));
            data.put("CLOSE_TIME", variables.get("close_time"));
            data.put("ISLONG_TERM", variables.get("islong_term"));
            data.put("XKCONTENT", variables.get("xkcontent"));
            data.put("IS_OPEN", variables.get("is_open"));
            data.put("XKDOCUMENT_NUM", variables.get("xkdocument_num"));
            data.put("XKDOCUMENT_NAME", variables.get("xkdocument_name"));
            data.put("XK_TYPE", variables.get("xk_type"));
            data.put("XKDECIDE_TIME", variables.get("xkdecide_time"));
            data.put("XK_USC", variables.get("xk_usc"));
            data.put("XK_HOLDER", variables.get("xk_holder"));
            data.put("SDCONTENT", variables.get("sdcontent"));
            data.put("RESULT_FILE_ID", variables.get("RESULT_FILE_ID"));
            data.put("RESULT_FILE_URL", variables.get("RESULT_FILE_URL"));
            data.put("RUN_MODE", variables.get("run_mode"));
            data.put("EXE_ID", variables.get("EFLOW_EXEID"));
            data.put("CUR_NODE", variables.get("EFLOW_CURUSEROPERNODENAME"));
            data.put("XKDEPT_SWDJH", variables.get("xkdept_swdjh"));
            data.put("XKDEPT_SYDWZSH", variables.get("xkdept_sydwzsh"));
            data.put("XKDEPT_SJLYDW", variables.get("xkdept_sjlydw"));
            data.put("XKDEPT_SJLYDW_USC", variables.get("xkdept_sjlydw_usc"));
            String recordId = jbpmService.saveOrUpdate(data, "JBPM6_FLOW_RESULT", null);
            // 判断是否含有附件ID的值
            String attachFileIds = request.getParameter("attachFileIds");
            if (StringUtils.isNotBlank(attachFileIds)) {
                fileAttachService.updateBusTableRecordId(attachFileIds.split(","), recordId);
            }
        }
    }

    /**
     * 跳转申请流程信息界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "flowResultDetail")
    public ModelAndView flowResultDetail(HttpServletRequest request) {
        // 获取流程申报号
        String exeId = request.getParameter("exeId");
        // 是否归档流程明细查看
        String isFiled = request.getParameter("isFiled");
        Map<String, Object> flowResult = null;
        String tableName = "JBPM6_FLOW_RESULT";
        if ("1".equals(isFiled)) {
            tableName = "JBPM6_FLOW_RESULT_EVEHIS";
        }
        flowResult = executionService.getByJdbc(tableName, new String[] { "EXE_ID" }, new Object[] { exeId });
        // 新增不动产高级电子证照查询
        Map<String, Object> isBdcScdydjFlag = null;
        Map<String, Object> serviceItem = null;
        Map<String, Object> bsyhlx = null;
        List<Map<String, Object>> obj = null;
        String exetablename = "JBPM6_EXECUTION";
        if ("1".equals(isFiled)) {
            exetablename = "JBPM6_EXECUTION_EVEHIS";
        }
        
        isBdcScdydjFlag = executionService.getByJdbc(exetablename, new String[] { "EXE_ID" }, new Object[] { exeId });
             
        if (isBdcScdydjFlag != null&& StringUtils.isNotEmpty(isBdcScdydjFlag.get("ITEM_CODE").toString()) ) {
            // 获取事项编码
            String itemCode = isBdcScdydjFlag.get("ITEM_CODE").toString() ;
            
            String dicCode = dictionaryService.getDicCode("BDCDZZZCX",itemCode);
            if(StringUtils.isNotEmpty(dicCode)){
                // 用于判断是否是抵押权首次登记
                isBdcScdydjFlag.put("isBdcScdydjFlag", "1");
             }else{
                isBdcScdydjFlag.put("isBdcScdydjFlag", "0");
             }
            serviceItem = executionService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });

            String itemName = serviceItem.get("ITEM_NAME").toString();
            SysUser User = AppUtil.getLoginUser();
            String transactor = User.getUsername();
            request.setAttribute("itemName", itemName);
            request.setAttribute("transactor", transactor);
            request.setAttribute("itemId", itemCode);
            //获取业务表名字
            String  busTableName="";
           if(StringUtils.isNotEmpty(isBdcScdydjFlag.get("BUS_TABLENAME").toString()) && 
                   "T_BDC".contentEquals(isBdcScdydjFlag.get("BUS_TABLENAME").toString().substring(0, 5))){
               busTableName=isBdcScdydjFlag.get("BUS_TABLENAME").toString();
               obj= executionService.findBdcScdjInfoList(exeId,busTableName);
           }
           // 获取抵押权人名字
            if (obj != null) {
                String str = JSON.toJSONString(obj);

                request.setAttribute("BdcScdjInfoList", str);

            } else {

                request.setAttribute("BdcScdjInfoList", "");

            }

        } else {
            isBdcScdydjFlag.put("isBdcScdydjFlag", "0");

            request.setAttribute("itemName", "");
            request.setAttribute("transactor", "");
            request.setAttribute("itemId", "");
        }

        if (flowResult != null) {
            FlowData flowData = new FlowData(exeId, FlowData.ITEM_MAP);
            Map<String, Object> itemMap = flowData.getItemMap();
            request.setAttribute("itemXz", StringUtil.getValue(itemMap, "SXXZ"));
            String json = JSON.toJSONString(flowResult);
            request.setAttribute("resultJson", json);
            List<Map<String, Object>> uploglist = sysLogService.getBusTableLogs("result_id", "JBPM6_FLOW_RESULT",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            flowResult.put("uplogList", uploglist);
            request.setAttribute("flowResult", flowResult);
            request.setAttribute("isBdcScdydjFlag", isBdcScdydjFlag);

        }
        return new ModelAndView("hflow/execution/flowHandleOverDetail");
    }
    

    
    /**
     * 跳转修改办件结果信息界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "updateFlowResult")
    public ModelAndView updateFlowResult(HttpServletRequest request) {
        // 获取流程申报号flowUpdateFlowResult
        String exeId = request.getParameter("exeId");
        Map<String, Object> flowResult = null;
        flowResult = executionService.getByJdbc("JBPM6_FLOW_RESULT", new String[] { "EXE_ID" }, new Object[] { exeId });
        String json = JSON.toJSONString(flowResult);
        if (exeId != null) {
            Map<String,Object> itemMap = serviceItemService.getItemInfoByExeId(exeId);
            if (itemMap != null) {
                request.setAttribute("itemXz",itemMap.get("SXXZ"));
            }
        }
        request.setAttribute("exeId", exeId);
        request.setAttribute("resultJson", json);
        request.setAttribute("flowResult", flowResult);
        return new ModelAndView("hflow/execution/flowUpdateFlowResult");
    }

    /**
     * 更新办件结果
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveFlowResult")
    @ResponseBody
    public void saveFlowResult(HttpServletRequest request, HttpServletResponse response) {
        // 获取流程申报号
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String exeId = request.getParameter("EXE_ID");
        String resultId = variables.get("RESULT_ID") == null ? "" : variables.get("RESULT_ID").toString();
        try {
            // 保存操作日志
            sysLogService.saveLogByMap("JBPM6_FLOW_RESULT", "RESULT_ID", resultId, variables, resultId);
            List<Map<String, Object>> resultList = serviceItemService.getAllByJdbc("JBPM6_FLOW_RESULT",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            if (null != resultList && resultList.size() <= 1) {
                executionService.saveOrUpdate(variables, "JBPM6_FLOW_RESULT", resultId);
                executionService.updateRes(exeId);
                variables.put("success", true);
                variables.put("msg", "操作成功");
                SysUser sysUser = AppUtil.getLoginUser();
                StringBuffer logContent = new StringBuffer();
                logContent.append(sysUser.getFullname()).append("修改了exeId=");
                logContent.append(exeId).append("的办件结果");
                sysLogService.saveLog(logContent.toString(), 7);
            } else {
                variables.put("success", false);
                variables.put("msg", "提交失败,请联系系统管理员!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            variables.put("success", false);
            variables.put("msg", "提交失败,请联系系统管理员!");
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }

    /**
     * easyui AJAX请求数据 列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "getResultFiles")
    public void getResultFiles(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("fileIds");
        List<Map<String, Object>> list = fileAttachService.findListForResult(ids);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 查看
     * @author Rider Chen
     * @created 2017年6月23日 上午10:21:45
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/lookResult")
    public void lookResult(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        String vcode = request.getParameter("vcode");
        String kaRandCode = (String) request.getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);// 验证码
        if (StringUtils.isNotEmpty(vcode) && StringUtils.isNotEmpty(kaRandCode) && vcode.equals(kaRandCode)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            SysUser sysUser = AppUtil.getLoginUser();
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("USER_ID", sysUser.getUserId());
            variables.put("USER_NAME", sysUser.getUsername());
            variables.put("FULL_NAME", sysUser.getFullname());
            executionService.saveOrUpdate(variables, "JBPM6_FLOW_RESULT_LOG", null);
            result.put("msg", "验证成功");
            result.put("success", true);

        } else {
            result.put("msg", "验证码填写错误!");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 跳转我的申请界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goMyapply")
    public ModelAndView goMyapply(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/myapply");
    }

    /**
     * 跳转到待我审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goNeedMeHandle")
    public ModelAndView goNeedMeHandle(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/needmeHandlerTask");
    }
    
    /**
     * 跳转到投资项目待审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goTzxmNeedMeHandle")
    public ModelAndView goTzxmNeedMeHandle(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/tzxmNeedmeHandlerTask");
    }

    @RequestMapping(params = "goSystemAnnouncement")
    public ModelAndView goSystemAnnouncement(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/systemAnnouncement");
    }

    /**
     * 跳转到一窗通办待我审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goYctbNeedMeHandleWarning")
    public ModelAndView goYctbNeedMeHandleWarning(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/yctbNeedMeHandleWarningTask");
    }

    /**
     * 跳转到一窗通办出件界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goYctbIssueResView")
    public ModelAndView goYctbIssueResView(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/yctbIssueResView");
    }

    /**
     * 跳转到商事待预审界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goZzhyDwys")
    public ModelAndView goZzhyDwys(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/zzhyDwysTask");
    }

    /**
     * 跳转到金融办办件页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "financeView")
    public ModelAndView financeForm(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/financeView");
    }

    /**
     * 跳转到社會件页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "socialView")
    public ModelAndView socialForm(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/socialView");
    }

    /**
     * 跳转到商事待受理界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goZzhyNeedMeHandle")
    public ModelAndView goZzhyNeedMeHandle(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/zzhyNeedmeHandlerTask");
    }

    /**
     * 跳转到商事待审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goZzhyDwsp")
    public ModelAndView goZzhyDwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/zzhyDwspTask");
    }

    /**
     * 跳转到商事已审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goZzhyJwsp")
    public ModelAndView goZzhyJwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/zzhyJwspTask");
    }

    /**
     * 跳转到流程预警界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goProcessWarning")
    public ModelAndView goProcessWarning(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/processWarning");
    }

    /**
     * 跳转到经我审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goHandledByMe")
    public ModelAndView goHandledByMe(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/handledByMe");
    }

    /**
     * 跳转流程实例监控界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goFlowMonitor")
    public ModelAndView goFlowMonitor(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/flowMonitor");
    }

    /**
     * 跳转流程实例监控界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goHangFlow")
    public ModelAndView goHangFlow(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/flowHang");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "hangDatagrid")
    public void hangDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        // String userAccount = request.getParameter("userAccount");
        List<Map<String, Object>> list = executionService.findHandledByHangup(filter);
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> dic = this.executionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("DEF_KEY"), "ZFJointDefKey" });
            if (dic != null) {
                map.put("DIC_STATE", 1);
            }
            newlist.add(map);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), newlist, null, JsonUtil.EXCLUDE, response);
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
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.CREATOR_ACCOUNT_EQ",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = executionService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridNew")
    public void datagridNew(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = executionService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "YctbDatagrid")
    public void yctbDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CJZT", "asc");
        filter.addSorted("T.RUN_STATUS", "desc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = executionService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "handledByMe")
    public void handledByMe(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("userAccount",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        String userAccount = sysUser.getUsername();
        String APPLY_STATUS_IN = request.getParameter("Q_T.APPLY_STATUS_IN");
        String APPLY_STATUS_EQ = request.getParameter("Q_T.APPLY_STATUS_EQ");
        List<Map<String, Object>> list;
        // 预审查询所有信息
        if ("2,8".equals(APPLY_STATUS_IN) || "3".equals(APPLY_STATUS_EQ)) {
            list = executionService.findHandledAllUser(filter, userAccount);
        } else {
            list = executionService.findHandledByUser(filter, userAccount);
        }
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> dic = this.executionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("DEF_KEY"), "ZFJointDefKey" });
            Map<String, Object> tzdic = this.executionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("ITEM_CODE"), "tzxmsx" });
            if (dic != null) {
                map.put("DIC_STATE", 1);
            }
            if (tzdic != null) {
                map.put("tzxm", 1);
            }
            newlist.add(map);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), newlist, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "zzhyHandledByMe")
    public void zzhyHandledByMe(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("userAccount",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        String userAccount = sysUser.getUsername();
        List<Map<String, Object>> list = executionService.findZzhyHandledByUser(filter, userAccount);
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> dic = this.executionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("DEF_KEY"), "ZFJointDefKey" });
            if (dic != null) {
                map.put("DIC_STATE", 1);
            }
            newlist.add(map);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), newlist, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 方法
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "checkDefkey")
    @ResponseBody
    public AjaxJson checkDefkey(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        // SysUser sysUser = AppUtil.getLoginUser();
        // String userAccount = sysUser.getUsername();
        String defKey = request.getParameter("defKey");
        Map<String, Object> map = this.executionService.getByJdbc("t_msjw_system_dictionary",
                new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { defKey, "ZFJointDefKey" });
        if (map != null) {
            j.setMsg("该流程是政府投资项目类型");
            j.setSuccess(true);
        } else {
            j.setMsg("该流程不是政府投资项目类型");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 跳转流程提交界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goBackFlow")
    public ModelAndView goBackFlow(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        Map<String, Object> backFlowBbj = executionService.getBackFlowObj(flowSubmitInfoJson);
        request.setAttribute("backNodeNames", backFlowBbj.get("backNodeNames"));
        request.setAttribute("nextTrans", backFlowBbj.get("nextTrans"));
        request.setAttribute("flowSubmitInfoJson", backFlowBbj.get("flowSubmitInfoJson"));
        return new ModelAndView("hflow/execution/backFlow");
    }

    /**
     * 跳转流程提交界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goSubmitFlow")
    public ModelAndView goSubmitFlow(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        Map<String, Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson, Map.class);
        // 获取申报号
        String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
        String defId = (String) flowSubmitInfo.get("EFLOW_DEFID");
        String nodeName = (String) flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
        int flowVersion = 0;
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
            Map<String, Object> itemInfo = this.executionService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_CODE" }, new Object[] { flowExe.get("ITEM_CODE") });
            if (itemInfo != null) {
                request.setAttribute("isYctb", itemInfo.get("IS_YCTB"));
                request.setAttribute("itemType", itemInfo.get("SXLX"));
                request.setAttribute("item_code", itemInfo.get("item_code"));
                request.setAttribute("item_id", itemInfo.get("item_id"));
                request.setAttribute("itemXz",itemInfo.get("SXXZ"));
                request.setAttribute("exeId", exeId);
                Map<String, Object> ssbm = this.executionService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[] { "DEPART_CODE" }, new Object[] { itemInfo.get("SSBMBM") });
                request.setAttribute("XKJG", ssbm.get("DEPART_NAME"));
                request.setAttribute("XKJGID", ssbm.get("DEPART_ID"));
                request.setAttribute("USC", ssbm.get("USC"));
            }
        } else {
            flowVersion = this.flowDefService.getFlowVersion(defId);
        }
        flowSubmitInfo.put("EFLOW_DEFVERSION", String.valueOf(flowVersion));
        // 获取开始节点名称
        String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
        if (nodeName.equals(startNodeName)) {
            request.setAttribute("IS_STARTFLOW", "true");
        } else {
            request.setAttribute("IS_STARTFLOW", "false");
        }
        List<Map<String, Object>> nextNodes = flowNodeService.findNextFlowNodes(defId, nodeName, flowVersion);
        List<FlowNextStep> nextTrans = jbpmService.findNextSteps(defId, nodeName, flowVersion, flowSubmitInfo, null,
                nextNodes);
        // 定义是否目标到结束
        boolean EFLOW_DESTTOEND = false;
        // 开始将接口类型的审核人加入变量里面
        Map<String, Object> eflowNextSteps = new HashMap<String, Object>();
        for (FlowNextStep nextTran : nextTrans) {
            if ("true".equals(nextTran.getIsEndNode())) {
                EFLOW_DESTTOEND = true;
                List<Map<String, Object>> contr = dictionaryService.findByTypeCode("RESULTCONTROLL");
                if (contr.size() > 0) {
                    request.setAttribute("resultControll", contr.get(0).get("DIC_CODE"));
                }
            }
            if (nextTran.getHandlers() != null && nextTran.getHandlers().size() > 0) {
                eflowNextSteps.put(nextTran.getFlowNodeName(), nextTran.getHandlers());
            }
        }
        if (eflowNextSteps.size() > 0) {
            flowSubmitInfo.put("EFLOW_NEXTSTEPS", eflowNextSteps);
        }
        // 获取是不是并行流程
        String isParallel = (String) flowSubmitInfo.get("ISPARALLEL");
        if (StringUtils.isEmpty(isParallel)) {
            isParallel = "false";
        }
        if (isParallel.equals("false") && nextTrans.size() > 1) {
            request.setAttribute("isMultiTransFlow", "true");
        } else {
            request.setAttribute("isMultiTransFlow", "false");
        }
        // 获取流程节点配置信息
        Map<String, Object> nodeConfig = this.nodeConfigService.getByJdbc("JBPM6_NODECONFIG",
                new String[] { "NODE_NAME", "DEF_ID", "DEF_VERSION" }, new Object[] { nodeName, defId, flowVersion });
        flowSubmitInfoJson = JSON.toJSONString(flowSubmitInfo);
        // 获取流程定义
        Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { defId });
        //判断如果所属部门是否为区党工委宣传与影视发展部，如果是的话，办结阶段的办结结果无需必填
        if(flowSubmitInfo.get("BELONG_DEPTNAME")!=null) {
            String belongDeptName = (String) flowSubmitInfo.get("BELONG_DEPTNAME");
            if("区党工委宣传与影视发展部".equals(belongDeptName)) {
                request.setAttribute("IS_QDGWXCYYSFZB", "1");
            }else {
                request.setAttribute("IS_QDGWXCYYSFZB", "0");
            }
        }
        request.setAttribute("flowDef", flowDef);
        request.setAttribute("nodeConfig", nodeConfig);
        request.setAttribute("EFLOW_DESTTOEND", EFLOW_DESTTOEND);
        request.setAttribute("nextTrans", nextTrans);
        request.setAttribute("ISPARALLEL", isParallel);
        request.setAttribute("flowSubmitInfoJson", StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        return new ModelAndView("hflow/execution/submitFlow");
    }

    /**
     * 跳转流程提交界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goFlowAddSPMatter")
    public ModelAndView goFlowAddSPMatter(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        Map<String, Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson, Map.class);
        // 获取申报号
        String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
        String defId = (String) flowSubmitInfo.get("EFLOW_DEFID");
        String nodeName = "并联审查";// (String) flowSubmitInfo.get("CUR_STEPNAMES");
        int flowVersion = 0;
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
        } else {
            flowVersion = this.flowDefService.getFlowVersion(defId);
        }
        flowSubmitInfo.put("EFLOW_DEFVERSION", String.valueOf(flowVersion));
        // 获取开始节点名称
        String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
        if (StringUtils.isNotEmpty(nodeName) && nodeName.equals(startNodeName)) {
            request.setAttribute("IS_STARTFLOW", "true");
        } else {
            request.setAttribute("IS_STARTFLOW", "false");
        }
        List<Map<String, Object>> nextNodes = flowNodeService.findNextFlowNodes(defId, "预审", flowVersion);
        // List<Map<String,Object>> preNodes = flowNodeService.findPreFlowNodes(defId, nodeName, flowVersion);
        // Map<String,Object> curNodes = flowNodeService.getFlowNode(defId, flowVersion, nodeName);
        List<FlowNextStep> nextTrans = jbpmService.findNextSteps(defId, "预审", flowVersion, flowSubmitInfo, null,
                nextNodes);
        // 定义是否目标到结束
        boolean EFLOW_DESTTOEND = false;
        // 开始将接口类型的审核人加入变量里面
        Map<String, Object> eflowNextSteps = new HashMap<String, Object>();
        for (FlowNextStep nextTran : nextTrans) {
            if ("true".equals(nextTran.getIsEndNode())) {
                EFLOW_DESTTOEND = true;
            }
            if (nextTran.getHandlers() != null && nextTran.getHandlers().size() > 0) {
                eflowNextSteps.put(nextTran.getFlowNodeName(), nextTran.getHandlers());
            }
        }
        if (eflowNextSteps.size() > 0) {
            flowSubmitInfo.put("EFLOW_NEXTSTEPS", eflowNextSteps);
        }
        // 获取是不是并行流程
        String isParallel = (String) flowSubmitInfo.get("ISPARALLEL");
        if (StringUtils.isEmpty(isParallel)) {
            isParallel = "false";
        }
        if (isParallel.equals("false") && nextTrans.size() > 1) {
            request.setAttribute("isMultiTransFlow", "true");
        } else {
            request.setAttribute("isMultiTransFlow", "false");
        }
        // 获取流程节点配置信息
        Map<String, Object> nodeConfig = this.nodeConfigService.getByJdbc("JBPM6_NODECONFIG",
                new String[] { "NODE_NAME", "DEF_ID", "DEF_VERSION" }, new Object[] { "预审", defId, flowVersion });
        flowSubmitInfoJson = JSON.toJSONString(flowSubmitInfo);
        request.setAttribute("nodeConfig", nodeConfig);
        request.setAttribute("EFLOW_DESTTOEND", EFLOW_DESTTOEND);
        request.setAttribute("nextTrans", nextTrans);
        request.setAttribute("ISPARALLEL", isParallel);
        request.setAttribute("flowSubmitInfoJson", StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        // add bind
        request.setAttribute("nodeName", "预审");
        request.setAttribute("EFLOW_DEFID", defId);
        request.setAttribute("EFLOW_EXEID", exeId);
        SysUser user = AppUtil.getLoginUser();
        Map<String, Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                new String[] { "DEF_ID", "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE" },
                new Object[] { defId, exeId, "预审", user.getUsername() });
        request.setAttribute("curTaskId", flowTask.get("TASK_ID"));

        String taskId = (String) flowSubmitInfo.get("EFLOW_CURRENTTASK_ID");
        request.setAttribute("EFLOW_CURRENTTASK_ID", taskId);
        String str = (String) flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
        request.setAttribute("EFLOW_CURUSEROPERNODENAME", str);
        return new ModelAndView("hflow/execution/submitAddSPMatter");
    }

    /**
     * 跳转预审不通过界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goPreAuditNoPass")
    public ModelAndView goPreAuditNoPass(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        Map<String, Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson, Map.class);
        // 获取实例ID
        String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null && StringUtils.isNotEmpty((String) flowExe.get("ITEM_CODE"))) {
            String itemCode = (String) flowExe.get("ITEM_CODE");
            Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // List<Map<String, Object>> materList = applyMaterService.findMaterByItemCode(itemCode);
            List<Map<String, Object>> materList = applyMaterService.findByItemId(itemId, exeId);
            String curnodename = (String) flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
            List<Map<String, Object>> againMater = againMaterService.findAgainMaterByExeId(exeId, curnodename);
            if (againMater != null && againMater.size() > 0) {
                for (int i = 0; i < materList.size(); i++) {
                    Map<String, Object> materMap = materList.get(i);
                    for (int j = 0; j < againMater.size(); j++) {
                        Map<String, Object> againMaterMap = againMater.get(j);
                        if (materMap.get("MATER_ID").equals(againMaterMap.get("MATER_ID"))) {
                            materMap.put("NOPASS", "1");
                        }
                    }
                    String materYj = againMaterService.getYjByExeIdAnd(exeId, curnodename,
                            (String) materMap.get("MATER_ID"));
                    materMap.put("materYj", materYj);// 设置回填审核意见
                }
            }
            // 定义材料列表JSON
            String applyMatersJson = JsonUtil.jsonStringFilter(materList, new String[] { "MATER_ID", "MATER_NAME",
                    "MATER_TYPE", "MATER_SIZE", "MATER_PATH", "MATER_ISNEED", "NOPASS" }, true);
            request.setAttribute("materList", materList);
            request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        }
        request.setAttribute("flowSubmitInfoJson", StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        return new ModelAndView("hflow/execution/preAuditNoPass");
    }

    /**
     * 跳转到办结流程界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goHandleOver")
    public ModelAndView goHandleOver(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        request.setAttribute("flowSubmitInfoJson", StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        return new ModelAndView("hflow/execution/handleOver");
    }

    /**
     * 执行办结流程方法
     * @param request
     * @return
     */
    @RequestMapping(params = "handleOver")
    @ResponseBody
    public void handleOver(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            String exeId = (String) variables.get("EFLOW_EXEID");
            // 获取意见内容
            String EFLOW_HANDLE_OPINION = (String) variables.get("EFLOW_HANDLE_OPINION");
            this.executionService.endByExeId(exeId, Jbpm6Constants.RUNSTATUS_OVERZCJS, EFLOW_HANDLE_OPINION);
            variables.put("OPER_SUCCESS", true);
            variables.put("OPER_MSG", "提交成功");
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
            log.error(e.getMessage());
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "revokeFlow")
    @ResponseBody
    public AjaxJson revokeFlow(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        String userAccount = sysUser.getUsername();
        String exeId = request.getParameter("exeId");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        int runStatus = Integer.parseInt(flowExe.get("RUN_STATUS").toString());
        if (runStatus != Jbpm6Constants.RUNSTATUS_RUNING) {
            j.setMsg("流程已经办结,撤回失败!");
            j.setSuccess(false);
        } else {
            String latestTaskId = this.flowTaskService.getLatestHandlerOverTask(exeId, userAccount);
            boolean isHaveHanded = flowTaskService.isNextTasksHaveHandled(latestTaskId);
            if (isHaveHanded) {
                j.setSuccess(false);
                j.setMsg("下一环节流程任务已被审批,撤回失败!");
            } else {
                flowTaskService.revokeTask(latestTaskId, exeId);
                j.setMsg("撤回流程成功,请查看待办审批.");
                j.setSuccess(true);
            }
        }
        return j;
    }

    /**
     * 前台撤办
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "frontRevokeFlow")
    @ResponseBody
    public AjaxJson frontRevokeFlow(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        String userAccount = userInfo.get("YHZH").toString();
        String exeId = request.getParameter("exeId");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        int runStatus = Integer.parseInt(flowExe.get("RUN_STATUS").toString());
        if (runStatus != Jbpm6Constants.RUNSTATUS_RUNING) {
            j.setMsg("流程已经办结,撤回失败!");
            j.setSuccess(false);
        } else {
            String latestTaskId = this.flowTaskService.getFrontLatestOverTask(exeId, userAccount);
            boolean isHaveHanded = flowTaskService.isNextTasksHaveHandled(latestTaskId);
            if (isHaveHanded) {
                j.setSuccess(false);
                j.setMsg("下一环节流程任务已被审批,撤回失败!");
            } else {
                flowTaskService.revokeTask(latestTaskId, exeId);
                j.setMsg("撤回流程成功,请查看待办审批.");
                j.setSuccess(true);
            }
        }
        return j;
    }

    /**
     * 跳转流程退回界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goBackBjFlow")
    public ModelAndView goBackBjFlow(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        // 获取退回信息对象
        Map<String, Object> backFlowBbj = executionService.getBackFlowObj(flowSubmitInfoJson);
        request.setAttribute("backNodeNames", backFlowBbj.get("backNodeNames"));
        request.setAttribute("nextTrans", backFlowBbj.get("nextTrans"));
        request.setAttribute("flowSubmitInfoJson", backFlowBbj.get("flowSubmitInfoJson"));
        Map<String, Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson, Map.class);
        // 获取实例ID
        String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null && StringUtils.isNotEmpty((String) flowExe.get("ITEM_CODE"))) {
            String itemCode = (String) flowExe.get("ITEM_CODE");
            Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // List<Map<String, Object>> materList = applyMaterService.findMaterByItemCode(itemCode);
            List<Map<String, Object>> materList = applyMaterService.findByItemId(itemId, exeId);
            String curnodename = (String) flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
            List<Map<String, Object>> againMater = againMaterService.findAgainMaterByExeId(exeId, curnodename);
            if (againMater != null && againMater.size() > 0) {
                for (int i = 0; i < materList.size(); i++) {
                    Map<String, Object> materMap = materList.get(i);
                    for (int j = 0; j < againMater.size(); j++) {
                        Map<String, Object> againMaterMap = againMater.get(j);
                        if (materMap.get("MATER_ID").equals(againMaterMap.get("MATER_ID"))) {
                            materMap.put("NOPASS", "1");
                        }
                    }
                    String materYj = againMaterService.getYjByExeIdAnd(exeId, curnodename,
                            (String) materMap.get("MATER_ID"));
                    materMap.put("materYj", materYj);// 设置回填审核意见
                }
            }
            // 定义材料列表JSON
            String applyMatersJson = JsonUtil.jsonStringFilter(materList, new String[] { "MATER_ID", "MATER_NAME",
                    "MATER_TYPE", "MATER_SIZE", "MATER_PATH", "MATER_ISNEED", "NOPASS" }, true);
            request.setAttribute("materList", materList);
            request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        }
        return new ModelAndView("hflow/execution/backBjFlow");
    }
    
    /**
     * 
     * 描述   打印 窗口一次性告知单界面
     * @author Allin Lin
     * @created 2021年2月22日 下午2:53:02
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goPrintYcxgzd")
    public ModelAndView goPrintYcxgzd(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        request.setAttribute("flowSubmitInfoJson",StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        Map<String, Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson, Map.class);
        // 获取实例ID
        String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
        /*Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });*/
        String itemCode = request.getParameter("itemCode");//事项编码
        String lineId = request.getParameter("lineId");//叫号记录ID
        String sqrxm = request.getParameter("SQRMC");//申请人
        if (StringUtils.isNotEmpty(itemCode)) {
            Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // List<Map<String, Object>> materList = applyMaterService.findMaterByItemCode(itemCode);
            List<Map<String, Object>> materList = applyMaterService.findByItemId(itemId, exeId);
            String curnodename = (String) flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
            List<Map<String, Object>> againMater = againMaterService.findAgainMaterByExeId(exeId, curnodename);
            if (againMater != null && againMater.size() > 0) {
                for (int i = 0; i < materList.size(); i++) {
                    Map<String, Object> materMap = materList.get(i);
                    for (int j = 0; j < againMater.size(); j++) {
                        Map<String, Object> againMaterMap = againMater.get(j);
                        if (materMap.get("MATER_ID").equals(againMaterMap.get("MATER_ID"))) {
                            materMap.put("NOPASS", "1");
                        }
                    }
                    String materYj = againMaterService.getYjByExeIdAnd(exeId, curnodename,
                            (String) materMap.get("MATER_ID"));
                    materMap.put("materYj", materYj);// 设置回填审核意见
                }
            }
            // 定义材料列表JSON
            String applyMatersJson = JsonUtil.jsonStringFilter(materList, new String[] { "MATER_ID", "MATER_NAME",
                    "MATER_TYPE", "MATER_SIZE", "MATER_PATH", "MATER_ISNEED", "NOPASS" }, true);
            request.setAttribute("materList", materList);
            request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
            request.setAttribute("itemCode", itemCode);
            request.setAttribute("lineId", lineId);
            request.setAttribute("sqrxm", sqrxm);
        }
        return new ModelAndView("hflow/execution/ycxgzdPrint");
    }

    /**
     * 执行补件流程方法
     * @param request
     * @return
     */
    @RequestMapping(params = "exeBjFlow")
    @ResponseBody
    public void exeBjFlow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            // variables = jbpmService.doFlowJob(variables);
            String exeId = (String) variables.get("EFLOW_EXEID");
            Map<String, Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String startNode = flowNodeService.getNodeName(exeMap.get("DEF_ID").toString(),
                    Integer.parseInt(exeMap.get("DEF_VERSION").toString()), Jbpm6Constants.START_NODE);
            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            Map<String, Object> e = new HashMap<String, Object>();
            e.put("nextStepAssignerCode", exeMap.get("CREATOR_ACCOUNT"));
            e.put("nextStepAssignerName", exeMap.get("CREATOR_NAME"));
            String sqfs = (String) exeMap.get("SQFS");
            if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("1")) {
                e.put("nextStepAssignerType", "2");
            } else {
                e.put("nextStepAssignerType", "1");
            }
            e.put("taskOrder", 0);
            listMap.add(e);
            Map<String, Object> backJson = new HashMap<String, Object>();
            backJson.put(startNode, listMap);

            String EFLOW_CURRENTTASK_ID = (String) variables.get("EFLOW_CURRENTTASK_ID");
            boolean isPassTime = false;
            if (StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)) {
                Map<String, Object> jbpmTask = flowTaskService.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { EFLOW_CURRENTTASK_ID });
                if (jbpmTask != null) {
                    // 获取状态
                    String TASK_STATUS = jbpmTask.get("TASK_STATUS").toString();
                    if (!TASK_STATUS.equals("1")) {
                        isPassTime = true;
                    }
                }
            }
            if (isPassTime) {
                variables.put("OPER_SUCCESS", true);
                variables.put("OPER_MSG", "该流程任务已经被其他经办人处理,您的流程任务被取消!");
            } else {
                variables.put("EFLOW_NEXTSTEPSJSON", JSON.toJSONString(backJson));
                variables = jbpmService.doBjFlowJob(variables);

                // 保存到平潭网格政务门户待办事项
                setItemTask(variables);

                variables.put("OPER_SUCCESS", true);
                variables.put("OPER_MSG", "提交成功");
            }
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
            log.error(e.getMessage(),e);
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 查看汇总意见
     * @author Danto Huang
     * @created 2015-12-3 上午9:36:36
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "viewSumOpinion")
    public ModelAndView viewSumOpinion(HttpServletRequest request) {
        /*
         * String flowSubmitInfoJson = request.getParameter("flowSubmitInfoJson"); Map<String,Object> flowSubmitInfo =
         * JSON.parseObject(flowSubmitInfoJson,Map.class); //获取实例ID String exeId = (String)
         * flowSubmitInfo.get("EFLOW_EXEID"); //获取当前审核人所处的任务ID String currentTaskId = (String)
         * flowSubmitInfo.get("EFLOW_CURRENTTASK_ID");
         */
        String exeId = request.getParameter("exeId");
        String currentTaskId = request.getParameter("currentTaskId");
        request.setAttribute("exeId", exeId);
        request.setAttribute("currentTaskId", currentTaskId);
        return new ModelAndView("hflow/execution/ViewSumOpinion");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "opinionDatagrid")
    public void opinionDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String currentTaskId = request.getParameter("currentTaskId");// 获取当前任务信息
        Map<String, Object> currentTask = this.flowTaskService.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                new Object[] { currentTaskId });
        String fromTask = currentTask.get("FROMTASK_NODENAMES").toString();
        filter.addFilter("Q_T.TASK_NODENAME_EQ", fromTask);
        filter.addSorted("T.STEP_SEQ", "asc");
        List<Map<String, Object>> list = flowTaskService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/rdbsPagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = null;
        mapList = executionService.findByRdbs(page, rows);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述跳转到打印页面
     * @author Faker Li
     * @created 2016年1月12日 下午2:59:16
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goPrintTemplateFlow")
    public ModelAndView goPrintTemplateFlow(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/printTemplateFlow");
    }

    /**
     * 方法multiEnd
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "storeFlowSubmitInfoJson")
    @ResponseBody
    public AjaxJson storeFlowSubmitInfoJson(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String flowSubmitInfoJson = request.getParameter("flowSubmitInfoJson");
        AppUtil.getSession().setAttribute("flowSubmitInfoJson", flowSubmitInfoJson);
        return j;
    }

    /**
     * 跳转平潭网格政务门户启动流程实例信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/goPtwgStart")
    public ModelAndView goPtwgStart(HttpServletRequest request) {
        setUserToSession(request);
        this.setPtwgFlowValues(request, "false");
        return new ModelAndView("hflow/execution/ptwgFlowStart");
    }

    /**
    * 
    * 描述  跳转到平潭网格政务门户流程办理界面
    * @author Rider Chen
    * @created 2016年8月11日 上午10:13:38
    * @param request
    * @return
    */
    @RequestMapping("/goPtwgHandle")
    public ModelAndView goPtwgHandle(HttpServletRequest request) {
        setUserToSession(request);
        this.setPtwgFlowValues(request, "false");
        return new ModelAndView("hflow/execution/ptwgflowHandle");
    }

    /**
     * 
     * 描述 设置平潭网格流程的值
     * @author Rider Chen
     * @created 2016年7月28日 下午5:57:48
     * @param request
     * @param isQueryDetail
     */
    @SuppressWarnings("unchecked")
    private void setPtwgFlowValues(HttpServletRequest request, String isQueryDetail) {
        String defKey = request.getParameter("defKey");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String lineName = request.getParameter("lineName");
        String lineCard = request.getParameter("lineCard");
        String lineId = request.getParameter("lineId");
        String zjlx = request.getParameter("zjlx");
        Map<String, Object> flowForm = null;
        Map<String, Object> flowDef = null;
        Map<String, Object> eflowObj = null;
        Map<String, Object> flowExe = null;
        // 开始定义流程信息对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, isQueryDetail, request);
        if (flowAllObj.get("busRecord") != null) {
            request.setAttribute("busRecord", flowAllObj.get("busRecord"));
        }
        if (flowAllObj.get("EFLOW_FLOWEXE") != null) {
            flowExe = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWEXE");
            request.setAttribute("EFLOW_FLOWEXE", flowExe);
        }
        if (flowAllObj.get("EFLOWOBJ") != null) {
            eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            request.setAttribute("EFLOWOBJ", eflowObj);
        }
        if (flowAllObj.get("EFLOW_FLOWOBJ") != null) {
            request.setAttribute("EFLOW_FLOWOBJ", flowAllObj.get("EFLOW_FLOWOBJ"));
        }
        if (flowAllObj.get("EFLOW_BUTTONRIGHTS") != null) {
            request.setAttribute("EFLOW_BUTTONRIGHTS", flowAllObj.get("EFLOW_BUTTONRIGHTS"));
        }
        if (flowAllObj.get("EFLOW_FLOWDEF") != null) {
            flowDef = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF");
            request.setAttribute("EFLOW_FLOWDEF", flowDef);
        }
        if (flowAllObj.get("EFLOW_FLOWFORM") != null) {
            flowForm = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWFORM");
            request.setAttribute("EFLOW_FLOWFORM", flowForm);
        }
        if (flowAllObj.get("EFLOW_QUERYBTNRIGHTS") != null) {
            request.setAttribute("EFLOW_QUERYBTNRIGHTS", flowAllObj.get("EFLOW_QUERYBTNRIGHTS"));
        }
        // 获取绑定的表单
        String itemCode = request.getParameter("itemCode");
        if (StringUtils.isEmpty(itemCode) && StringUtils.isNotEmpty(exeId)) {
            itemCode = (String) flowExe.get("ITEM_CODE");
        }
        Map<String, Object> existParams = new HashMap<String, Object>();
        existParams.put("itemCode", itemCode);
        // 开始构建IFRAME的URL
        // 获取表单的URL
        String formUrl = this.flowFormService.getUrlByFlowForm(flowForm, existParams);
        StringBuffer iframeUrl = new StringBuffer(formUrl);
        if (formUrl.indexOf("?") != -1) {
            iframeUrl.append("&");
        } else {
            iframeUrl.append("?");
        }
        iframeUrl.append("defId=").append((String) flowDef.get("DEF_ID"));
        iframeUrl.append("&exeId=").append((String) eflowObj.get("EFLOW_EXEID"));
        if (eflowObj.get("EFLOW_FLOWSTAGE") != null) {
            iframeUrl.append("&flowstage=").append((String) eflowObj.get("EFLOW_FLOWSTAGE"));
        }
        if (StringUtils.isNotEmpty(lineName)) {
            iframeUrl.append("&lineName=").append(lineName);
        }
        if (StringUtils.isNotEmpty(lineCard)) {
            iframeUrl.append("&lineCard=").append(lineCard);
        }
        if (StringUtils.isNotEmpty(lineId)) {
            iframeUrl.append("&lineId=").append(lineId);
        }
        if (StringUtils.isNotEmpty(zjlx)) {
            iframeUrl.append("&zjlx=").append(zjlx);
        }
        if (isQueryDetail.equals("true")) {
            iframeUrl.append("&EFLOW_ISQUERYDETAIL=" + isQueryDetail);
        }
        if (StringUtils.isNotEmpty(itemCode)) {
            List<Map<String, Object>> bindForms = this.serviceItemService.findBindForms(exeId, itemCode);
            if (bindForms != null && bindForms.size() > 0) {
                request.setAttribute("bindForms", bindForms);
            }
        }
        iframeUrl.append("&ptwgType=1");
        // log.info("iframe_URL:"+iframeUrl.toString());
        request.setAttribute("IFRAME_URL", iframeUrl.toString());

    }

    /**
     * 
     * 描述 设置平潭网格用户到session
     * @author Rider Chen
     * @created 2016年8月1日 上午10:58:41
     * @param request
     */
    public void setUserToSession(HttpServletRequest request) {
        // 用户ID与账号
        String uid = request.getParameter("uid");
        // 用户姓名
        String cn = request.getParameter("cn");
        // 用户手机
        String phone = request.getParameter("phone");
        // 用户身份证号码
        // String idcard = request.getParameter("idcard");
        SysUser sysUser = new SysUser();
        sysUser.setUserId(uid);
        sysUser.setUsername(uid);
        sysUser.setFullname(cn);
        sysUser.setMobile(phone);
        HttpSession session = AppUtil.getSession();
        AppUtil.addUserToSession(session, sysUser);
    }

    /**
     * 
     * 描述 远程保存到平潭网格政务门户待办事项表
     * @author Rider Chen
     * @created 2016年8月11日 上午8:51:32
     * @param variables
     */
    public void setItemTask(Map<String, Object> variables) {
        try {
            // Properties properties = FileUtil.readProperties("project.properties");
            // String ptwgzwmhPath = properties.getProperty("ptwgzwmhPath") ;
            // String param ="";
            String exeId = (String) variables.get("EFLOW_EXEID");
            // String title = variables.get("EFLOW_SUBJECT")==null?
            // (String)variables.get("busRecord[SUBJECT]"):(String)variables.get("EFLOW_SUBJECT");
            String userId = (String) variables.get("EFLOW_NEWTASK_HANDLERCODES");
            String nodeNames = (String) variables.get("EFLOW_NEWTASK_NODENAMES");
            String ITEM_STATE = "1";
            Map<String, Object> user = flowTaskService.getByJdbc("T_MSJW_SYSTEM_SYSUSER", new String[] { "USER_ID" },
                    new Object[] { userId });
            // 盘点下一环节是否是结束，是结束则更改状态
            if (StringUtils.isNotEmpty(nodeNames) && nodeNames.equals("结束")) {
                ITEM_STATE = "2";
            }
            String taskId = "";
            // 判断下一环节人是否是平潭网格用户。
            if (StringUtils.isNotEmpty(exeId) && null == user && StringUtils.isNotEmpty(userId)
                    && userId.contains("U")) {
                taskId = flowTaskService.findTaskId(exeId);
                if (StringUtils.isNotEmpty(taskId)) {
                    flowTaskService.startUpFlowTask(taskId);
                }
            }
            /*
             * param ="EXEID="+exeId+"&ITEM_TITLE="+title+"&USER_ID="+userId+"&ITEM_STATE="+ITEM_STATE
             * +"&ITEM_SOURCE=ptzhsp&TASKID="+taskId;
             */
            // String EFLOW_CREATORID = (String)variables.get("EFLOW_CREATORID");
            /*
             * if(StringUtils.isNotEmpty(EFLOW_CREATORID)){ param += "&CREATE_USER_ID="+EFLOW_CREATORID; }
             */
            // 推送办事数据到平潭网格政务门户
            // HttpRequestUtil.sendPost(ptwgzwmhPath+"webSiteController/setTask.do", param, "UTF-8");

            // 判断登录用户是否本系统用户，不是则销毁登录session
            Map<String, Object> loginUser = flowTaskService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[] { "USER_ID" }, new Object[] { AppUtil.getLoginUser().getUserId() });
            if (null == loginUser) {
                HttpSession session = AppUtil.getSession();
                session.removeAttribute("curLoginUser");
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
            return;
        }

    }

    /**
     * 跳转退件界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goNotAccept")
    public ModelAndView goNotAccept(HttpServletRequest request) {
        String flowSubmitInfoJson = (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        request.setAttribute("flowSubmitInfoJson", StringEscapeUtils.escapeHtml3(flowSubmitInfoJson));
        return new ModelAndView("hflow/execution/notAccept");
    }

    /**
     * 执行退件方法
     * @param request
     * @return
     */
    @RequestMapping(params = "notAccept")
    @ResponseBody
    public void notAccept(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            String exeId = (String) variables.get("EFLOW_EXEID");
            Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String sqfs = (String) flowExe.get("SQFS");
            // 获取意见内容
            String EFLOW_HANDLE_OPINION = (String) variables.get("EFLOW_HANDLE_OPINION");
            String EFLOW_CURRENTTASK_ID = (String) variables.get("EFLOW_CURRENTTASK_ID");
            // 退件判断是否已保存过同步省网信息 是否为省网下发办件
            Boolean isSaveBoolean = swbDataResDao.isHaveSaveBjxxInfo(exeId);
            if (isSaveBoolean || sqfs.equals("3")) {
                /*判断在是否存在20信息，没有的话，添加一条20信息*/
                executionService.addProcessDataRes(exeId, EFLOW_CURRENTTASK_ID);
                this.saveTjxxDataRes(exeId, EFLOW_CURRENTTASK_ID);
            }
            this.executionService.updateExeToNotAccept(exeId, EFLOW_HANDLE_OPINION, variables);
            variables.put("OPER_SUCCESS", true);
            variables.put("OPER_MSG", "提交成功");
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
            log.error(e.getMessage());
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }


    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2017-6-27 下午4:25:49
     * @param flowVars
     * @param flowExe
     * @param currentTaskId
     */
    private void saveTjxxDataRes(String exeId, String currentTaskId) {
        // 开始保存办件信息
        Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
        bjxxDataRes.put("EXE_ID", exeId);
        bjxxDataRes.put("DATA_TYPE", "30");
        bjxxDataRes.put("OPER_TYPE", "I");
        // 定义是否有附件
        int HAS_ATTR = 0;
        bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
        bjxxDataRes.put("INTER_TYPE", "10");
        bjxxDataRes.put("TASK_ID", currentTaskId);
        swbDataResDao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
    }

    /**
     * 
     * 描述： base64图片上传到文件服务器
     * 
     * @author Rider Chen
     * @created 2019年6月3日 上午11:36:43
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "uploadBase64")
    @ResponseBody
    public void uploadBase64(HttpServletRequest request, HttpServletResponse response) {
        String uploadUserId = request.getParameter("uploadUserId");// 上传人ID
        String uploadUserName = request.getParameter("uploadUserName");// 上传人姓名
        String busTableName = request.getParameter("busTableName");// 获取业务表名称
        String busRecordId = request.getParameter("busRecordId");// 获取业务表记录ID
        String attachKey = request.getParameter("attachKey");// 材料编码
        String FLOW_EXEID = request.getParameter("FLOW_EXEID");// 流程实例ID
        String FLOW_TASKID = request.getParameter("FLOW_TASKID");// 流程任务ID
        String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");// 流程任务名称
        String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");// 上传人员部门ID
        String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");// 上传人员部门名称
        String SFHZD = "-1";// 是否回执单

        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");

        String url = uploadFileUrl + "upload/file";// 上传路径

        String imgStrAll = request.getParameter("base64Code");// 文件base64
        String[] jsonArr = imgStrAll.split(";");
        String base64Code = "";
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        // formData参数
        Map<String, Object> param;
        for (int i = 0; i < jsonArr.length; i++) {
            base64Code = jsonArr[i];
            if (StringUtils.isEmpty(base64Code)) {
                continue;
            }
            String result = "";
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                String uuId = UUIDGenerator.getUUID();
                String fileName = uuId + ".jpg";//文件名称
                param = new HashMap<String, Object>();
                param.put("uploaderId", uploadUserId);// 上传人ID
                param.put("uploaderName", uploadUserName); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName);// 上传附件名

                param.put("attachKey", attachKey);// 材料编码
                param.put("busTableName", busTableName);// 业务表名
                param.put("busRecordId", busRecordId);// 业务表ID
                param.put("flowExeId", FLOW_EXEID);// 流程实例ID
                param.put("flowTaskId", FLOW_TASKID);// 流程任务ID
                param.put("flowTaskName", FLOW_TASKNAME);// 流程任务名称
                param.put("uploaderDepId", UPLOADER_DEPID);// 上传人部门ID
                param.put("uploaderDepName", UPLOADER_DEPNAME);// 上传人部门名称
                param.put("SFHZD", SFHZD);// 是否回执单

                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
                if (StringUtils.isNotEmpty(result)) {
                    Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                    resultList.add(resultMap);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("", e);
            }
        }
        String json = JSONArray.toJSONString(resultList);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述   追回个人申请
     * @author Danto Huang
     * @created 2016-11-17 上午11:31:23
     * @param request
     * @param response
     */
    @RequestMapping(params = "getBackMyApply")
    @ResponseBody
    public AjaxJson getBackMyApply(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String exeId = request.getParameter("exeId");
        Map<String, Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        String[] nodeNames = { "质监审批", "国税审批", "地税审批", "社保审批", "统计审批", "公安审批", "商务审批", "交建审批", "食药监审批", "并审汇总", "办结" };
        boolean isAllow = false;
        for (int i = 0; i < nodeNames.length; i++) {
            int have = exeMap.get("CUR_STEPNAMES").toString().indexOf(nodeNames[i]);
            if (have >= 0) {
                isAllow = true;
                break;
            }
        }
        if (isAllow) {
            j.setMsg("已完成工商审批，不允许追回！");
            j.setSuccess(false);
        } else {
            executionService.getBackMyApply(exeId);
            j.setMsg("撤回成功");
            j.setSuccess(true);
        }
        return j;
    }

    /**
     * 描述:不动产退回流程
     *
     * @author Madison You
     * @created 2020/10/30 15:27:00
     * @param
     * @return
     */
    @RequestMapping("/getBdcBackApply")
    @ResponseBody
    public AjaxJson getBdcBackApply(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        j.setMsg("撤回失败");
        try{
            String exeId = request.getParameter("exeId");
            Map<String, Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String curStepnames = StringUtil.getValue(exeMap, "CUR_STEPNAMES");
            String runStatus = StringUtil.getValue(exeMap, "RUN_STATUS");
            if (StringUtils.isNotEmpty(curStepnames) && !"开始".equals(curStepnames)
                    && StringUtils.isNotEmpty(runStatus) && "1".equals(runStatus)) {
                List signList = serviceItemService.getAllByJdbc("T_BDCQLC_MATERSIGNINFO", new String[]{"EXE_ID"},
                        new Object[]{exeId});
                if (signList != null && !signList.isEmpty()) {
                    Boolean isCancel = bdcQlcMaterService.cancelSignFlow(exeId);
                    if (isCancel) {
                        executionService.getBackMyApply(exeId);
                        j.setMsg("撤回成功");
                        j.setSuccess(true);
                    } else {
                        j.setMsg("撤回失败，签章作废失败");
                    }
                } else {
                    executionService.getBackMyApply(exeId);
                    j.setMsg("撤回成功");
                    j.setSuccess(true);
                }
            }
        } catch (Exception e) {
            log.error("不动产全流程撤回失败");
            log.error(e.getMessage(),e);
        }
        return j;
    }

    /**
     * 
     * 描述跳转到打印页面
     * @author Faker Li
     * @created 2016年1月12日 下午2:59:16
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "goPrintQRcodeFlow")
    public ModelAndView goPrintQRcodeFlow(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/printQRcodeFlow");
    }

    /**
     * 
     * 描述    跳转食药登记待受理
     * @author Danto Huang
     * @created 2019年2月22日 下午3:52:04
     * @param request
     * @return
     */
    @RequestMapping(params = "goFdaNeedMeHandle")
    public ModelAndView goFdaDwsl(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/fdaNeedMeHandle");
    }

    /**
     * 
     * 描述    跳转食药登记待我审批
     * @author Danto Huang
     * @created 2019年2月25日 上午9:15:02
     * @param request
     * @return
     */
    @RequestMapping(params = "goFdaDwsp")
    public ModelAndView goFdaDwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/fdadwspTask");
    }

    /**
     * 
     * 描述    跳转食药登记经我审批
     * @author Danto Huang
     * @created 2019年2月25日 上午9:26:59
     * @param request
     * @return
     */
    @RequestMapping(params = "goFdaJwsp")
    public ModelAndView goFdaJwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/fdaJwspTask");
    }

    /**
     * 
     * 描述    食药登记经我审批
     * @author Danto Huang
     * @created 2019年2月25日 上午9:31:36
     * @param request
     * @param response
     */
    @RequestMapping(params = "fdaHandledByMe")
    public void fdaHandledByMe(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("userAccount",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        String userAccount = sysUser.getUsername();
        List<Map<String, Object>> list = executionService.findFdaHandledByUser(filter, userAccount);
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> dic = this.executionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("DEF_KEY"), "ZFJointDefKey" });
            if (dic != null) {
                map.put("DIC_STATE", 1);
            }
            newlist.add(map);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), newlist, null, JsonUtil.EXCLUDE, response);
    }

    /*获取环土局传过来的材料信息 */
    @RequestMapping("/fetchHPFile")
    @ResponseBody
    public Map<String,Object> fetchHPFile(HttpServletRequest request){
        return executionService.fetchHPFile(request);
    }
   
    /**
     * 描述 省网数据回流信息
     * 
     * @author Reuben Bao
     * @created 2019年9月29日 下午2:39:51
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "provinceDataDetail")
    public ModelAndView provinceDataDetail(HttpServletRequest request) {
        // 获取流程申报号
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        if(StringUtils.isNotEmpty(exeId)) {
            // 是否归档流程明细查看
            // String isFiled = request.getParameter("isFiled");
            // 查询省网回流数据基础信息表
            request.setAttribute("apasinfobase", executionService.getByJdbc("MZT_ACCEPT_APASINFOBASE",
                    new String[] { "SN" }, new Object[] { exeId }));
            // 查询省网回流数据办结流程表
            List<Map<String, Object>> opionsList = new ArrayList<Map<String,Object>>();
            opionsList.addAll(
                    executionService.getAllByJdbc("MZT_ACCEPT_NEXTS", new String[] { "SN" }, new Object[] { exeId }));
            // 获取统建系统数据
            opionsList.addAll(
                    executionService.getAllByJdbc("MZT_ACCEPT_PROCESSS", 
                            new String[] { "SN" }, new Object[] { exeId }));
            request.setAttribute("nexts", opionsList);
            // 查询省网回流数据办结流程表
            Map<String, Object> resultsMap = new HashMap<String, Object>();
            Map<String, Object> finishMap = executionService.getByJdbc("MZT_ACCEPT_FINISHS", 
                    new String[] { "SN" }, new Object[] { exeId });
            if(finishMap != null && finishMap.size() > 0) {
                resultsMap.putAll(finishMap);
            }
            // 获取统建系统办结信息
            Map<String, Object> resultMap = executionService.getByJdbc("MZT_ACCEPT_RESULTINFOS", new String[] { "SN" },
                    new Object[] { exeId });
            if(resultMap != null && resultMap.size() > 0) {
                resultsMap.putAll(resultMap);
            }
            request.setAttribute("finishs", resultMap);
            // 获取统建特殊办理数据
            request.setAttribute("specials", executionService.getAllByJdbc("MZT_ACCEPT_SPECIALINFOS",
                    new String[] { "SN" }, new Object[] { exeId }));
            // 查询省网回流数据计时恢复表
            request.setAttribute("resumes",
                    executionService.getAllByJdbc("MZT_ACCEPT_RESUMES", new String[] { "SN" }, new Object[] { exeId }));
        }
        return new ModelAndView("hflow/execution/provinceData");
    }
    
    /**
     * 描述 获取省网回流数据附件
     * 
     * @author Reuben Bao
     * @created 2019年9月29日 下午5:06:24
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "provinceAttrList")
    public void provinceAttrList(HttpServletRequest request, HttpServletResponse response) {
        // 查询省网回流附件数据
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> list = executionService.getAllByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[] { "FLOW_EXEID" }, new Object[] { exeId });
        // executionService.getProvinceAttrs(request);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 流程回撤提交界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "recall")
    public ModelAndView recall(HttpServletRequest request) {
        String projectCode = request.getParameter("projectCode");
        String exeid = request.getParameter("exeid");
        String itemCode = request.getParameter("itemCode");
        request.setAttribute("projectCode", projectCode);
        request.setAttribute("exeid", exeid);
        request.setAttribute("itemCode", itemCode);
        return new ModelAndView("project/projectDetail/recallFlow");
    }
    /**
     * 保存流程回撤信息
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveRecallInfo")
    @ResponseBody
    public AjaxJson saveRecallInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        boolean isExist = executionService.projectRecallIsExist(variables);
        if(!isExist) {
            String userName = AppUtil.getLoginUser().getUsername();
            variables.put("OPERATOR_NAME", userName);
            executionService.saveOrUpdate(variables, "T_WSBS_PROJECT_RECALL", null);
            j.setMsg("撤回申请成功");
            j.setSuccess(true);
        }else {
            j.setMsg("该事项已经是撤回状态");
            j.setSuccess(false);
        }
        return j;
    }


    /**
     * 描述:缴费明细页面
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/26 10:52:00
     */
    @RequestMapping(params = "payListView")
    public ModelAndView payListView(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> payList = executionService.getPayListData(exeId);
        request.setAttribute("payList", payList);
        return new ModelAndView("hflow/execution/payListView");
    }

    /**
     * 描述:医疗保险材料下载页面
     *
     * @author Madison You
     * @created 2020/10/19 16:40:00
     * @param
     * @return
     */
    @RequestMapping(params = "medicialMaterView")
    public ModelAndView medicialMaterView(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/medicialMaterView");
    }

    /**
     * 描述:公积金材料下载页面
     *
     * @author Madison You
     * @created 2020/11/10 11:56:00
     * @param
     * @return
     */
    @RequestMapping(params = "fundsMaterView")
    public ModelAndView fundsMaterView(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/fundsMaterView");
    }

    /**
     * 描述:税务登记材料下载页面
     *
     * @author Madison You
     * @created 2020/11/10 11:56:00
     * @param
     * @return
     */
    @RequestMapping(params = "taxMaterView")
    public ModelAndView taxMaterView(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/taxMaterView");
    }

}
