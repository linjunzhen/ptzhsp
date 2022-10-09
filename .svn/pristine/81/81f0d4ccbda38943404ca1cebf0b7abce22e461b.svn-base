/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.project.service.ProjectWebsiteService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 投资项目前端页面申报Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/projectWebsiteController")
public class ProjectWebsiteController extends BaseController {
    /**
     * projectWebsiteService
     */
    @Resource
    public ProjectWebsiteService projectWebsiteService;
    /**
     * ApplyMaterService
     */
    @Resource
    public ApplyMaterService applyMaterService;

    /**
     * ApplyMaterService
     */
    @Resource
    public ServiceItemService serviceItemService;
    /**
     *projectWebsiteApplyService
     */
    @Resource
    public ProjectWebsiteApplyService projectWebsiteApplyService;
    /**
     * flowDefService
     */
    @Resource
    public FlowDefService flowDefService;
    /**
     * flowNodeService
     */
    @Resource
    public FlowNodeService flowNodeService;
    /**
     * fileAttachService
     */
    @Resource
    public FileAttachService fileAttachService;
    /**
     * exeDataService
     */
    @Resource
    public ExeDataService exeDataService;

    /**
     * 项目工程首页
     * @param request
     * @return
     */
    @RequestMapping(params = "index")
    public ModelAndView index(HttpServletRequest request) {
        request.setAttribute("nav","1");
        return new ModelAndView("website/project/index");
    }

    /**
     * 项目工程申报页
     * @param request
     * @return
     */
    @RequestMapping(params = "declareInfo")
    public ModelAndView declareInfo(HttpServletRequest request) {
        request.setAttribute("nav","2");
        return new ModelAndView("website/project/declareInfo");
    }
    /**
     * 跳转到项目登记详情页
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/registerProject")
    public ModelAndView registerProject(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(id)&&!id.equals("undefined")){
            Map<String,Object>  projectApply = projectWebsiteApplyService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{id});
            request.setAttribute("projectApply", projectApply);
        }
        return new ModelAndView("website/project/registerProject");
    }
    /**
     * 办事指南
     * @param request
     * @return
     */
    @RequestMapping(params = "bsznView")
    public ModelAndView bsznView(HttpServletRequest request) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        String typeId= StringUtil.getString(variable.get("typeId"));
        if(StringUtils.isEmpty(typeId)){
            StringBuffer sql=new StringBuffer("SELECT * FROM ");
            sql.append(" T_FLOW_CONFIG_TYPE ORDER BY SPLCLX ");
            Map<String,Object> type=exeDataService.getFirstByJdbc(sql.toString(),null);
            typeId=StringUtil.getString(type.get("TYPE_ID"));
        }
        String stageId= StringUtil.getString(variable.get("stageId"));
        if(StringUtils.isEmpty(StringUtil.getString(stageId))){
            stageId=projectWebsiteService.getFirstStageIdByTypeId(typeId);
        }
        Map<String,Object> serviceItem=projectWebsiteService.getServiceItemByStageId(stageId);
        request.setAttribute("typeId",typeId);
        request.setAttribute("stageId",stageId);
        request.setAttribute("serviceItem",serviceItem);

        request.setAttribute("nav","3");
        return new ModelAndView("website/project/bsznView");
    }
    /**
     * 办事指南
     * @param request
     * @return
     */
    @RequestMapping(params = "userCenter")
    public ModelAndView userCenter(HttpServletRequest request) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        request.setAttribute("nav","7");
        return new ModelAndView("website/project/userCenter");
    }
    /**
     * 办事指南
     * @param request
     * @return
     */
    @RequestMapping(params = "projectInfoDetail")
    public ModelAndView projectInfoDetail(HttpServletRequest request) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        String id=StringUtil.getString(variable.get("id"));
        Map<String,Object> project=projectWebsiteService.getProjectInfosById(id);
        request.setAttribute("project",project);
        request.setAttribute("nav","4");
        return new ModelAndView("website/project/projectInfoDetail");
    }
    /**
     * 办事指南
     * @param request
     * @return
     */
    @RequestMapping("/getProjectInfoById")
    public void getProjectInfoById(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        String id=StringUtil.getString(variable.get("id"));
        Map<String,Object> project=projectWebsiteService.getProjectInfosById(id);
        this.setJsonString(JSONObject.toJSONString(project),response);
    }
    /**
     * 根据阶段id和主题编号获取需要办理的事项
     * @param request
     * @return
     */
    @RequestMapping("/findServiceItemByStageIdAndTopicCode")
    public void findServiceItemByStageIdAndTopicCode(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> serviceItems=projectWebsiteService.findServiceItemByStageIdAndTopicCode(variable);
        this.setJsonString(JSONObject.toJSONString(serviceItems),response);
    }
    /**
     *项目申报页面
     * @param request
     * @return
     */
    @RequestMapping(params = "applyProjectView")
    public ModelAndView applyProjectView(HttpServletRequest request) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        String typeId= StringUtil.getString(variable.get("typeId"));
        String stageId= StringUtil.getString(variable.get("stageId"));
        if(StringUtils.isEmpty(StringUtil.getString(stageId))){
            stageId=projectWebsiteService.getFirstStageIdByTypeId(typeId);
        }
        Map<String,Object> serviceItem=projectWebsiteService.getServiceItemByStageId(stageId);
        request.setAttribute("typeId",typeId);
        request.setAttribute("stageId",stageId);
        request.setAttribute("serviceItem",serviceItem);
        request.setAttribute("nav","3");
        return new ModelAndView("website/applyforms/T_GCXM_XMJBXX");
    }
    /**
     *项目申报页面
     * @param request
     * @return
     */
    @RequestMapping(params = "myProjectView")
    public ModelAndView myProjectView(HttpServletRequest request) {
        Map<String,Object> variable= BeanUtil.getMapFromRequest(request);
        request.setAttribute("nav","7");
        return new ModelAndView("website/project/myProjectView");
    }
    /**
     *
     * 描述 加载项目基本信息
     *
     * @author Rider Chen
     * @created 2015-12-22 下午05:26:43
     * @param request
     * @param response
     */
    @RequestMapping("/loadLocalXMJBXXB")
    public void loadLocalXMJBXXB(HttpServletRequest request, HttpServletResponse response) {
        String projectCode = request.getParameter("projectCode");
        Map<String,Object>  tzprojectInfo = projectWebsiteService.loadLocalXMJBXXBByProjectCode(projectCode);
        String json = JSON.toJSONString(tzprojectInfo);
        this.setJsonString(json, response);
    }
    /**
     * 跳转到表单界面
     * @param request
     * @return
     */
    @RequestMapping(params = "findContentForPage")
    @ResponseBody
    public AjaxJsonCode findContentForPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> contents=projectWebsiteService.findContentForPage(variable);
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",contents);
        jsonObject.put("total",variable.get("total"));
        ajaxJsonCode.setJsonString(jsonObject.toJSONString());
        return  ajaxJsonCode;
    }
    /**
     * 跳转到表单界面
     * @param request
     * @return
     */
    @RequestMapping("/findMyProjectList")
    @ResponseBody
    public AjaxJsonCode findMyProjectList(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> myProjectList=projectWebsiteService.findMyProjectList(variable);
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",myProjectList);
        jsonObject.put("total",variable.get("total"));
        ajaxJsonCode.setJsonString(jsonObject.toJSONString());
        return  ajaxJsonCode;
    }
    /**
     * 跳转到表单界面
     * @param request
     * @return
     */
    @RequestMapping("/findMyProjectInfo")
    @ResponseBody
    public AjaxJsonCode findMyProjectInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> myProjectInfos=projectWebsiteService.findMyProjectInfo(variable);
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",myProjectInfos);
        jsonObject.put("total",variable.get("total"));
        ajaxJsonCode.setJsonString(jsonObject.toJSONString());
        return  ajaxJsonCode;
    }
    /**
     * 跳转到事项材料列表界面
     *
     * @param request
     * @return
     */
    @RequestMapping("/applyItemMaterList")
    public ModelAndView applyItemMaterList(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String exeId = request.getParameter("exeId");// 获取流程申报号
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");// 投资项目编号（工程建设项目申报）
        String itemCode = request.getParameter("itemCode");// 获取服务事项编码
        String STAGE_ID = request.getParameter("STAGE_ID");// 阶段ID
        String isWebsite = request.getParameter("isWebsite");// 引入标识
        String materModelView=request.getParameter("materModelView");//视图模型
        String toMaterModelVies="itemMaterView";
        if(StringUtils.isNotBlank(materModelView)){
            toMaterModelVies=materModelView;
        }
        Map<String, Object> execution = null;
        int flowVersion = 0;
        // 是否归档流程查看
        String isFiled = request.getParameter("isFiled");
        String currentNodeName = null;
        String busRecordId = "", busTableName = "";
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        if (null != serviceItem) {
            setMatersAttribute(request, exeId, PROJECT_CODE, STAGE_ID, execution, flowVersion, isFiled, currentNodeName,
                    busRecordId, busTableName, serviceItem);
        }
        request.setAttribute("isWebsite", isWebsite);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("website/project/"+toMaterModelVies);
    }
    /**
     *
     * 描述： 设置办件材料参数
     *
     * @author Rider Chen
     * @created 2020年9月9日 下午2:48:55
     * @param request
     * @param exeId
     * @param PROJECT_CODE
     * @param STAGE_ID
     * @param execution
     * @param flowVersion
     * @param isFiled
     * @param currentNodeName
     * @param busRecordId
     * @param busTableName
     * @param serviceItem
     */
    @SuppressWarnings("unchecked")
    private void setMatersAttribute(HttpServletRequest request, String exeId, String PROJECT_CODE, String STAGE_ID,
                                    Map<String, Object> execution, int flowVersion, String isFiled, String currentNodeName, String busRecordId,
                                    String busTableName, Map<String, Object> serviceItem) {
        String formKey = (String) serviceItem.get("FORM_KEY");
        String itemId = (String) serviceItem.get("ITEM_ID");// 获取项目ID
        // 获取材料信息列表 仅获取事项材料列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        // 获取材料业务办理子项分类列表
        List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeList(itemId);
        List<Map<String, Object>> filterApplyMaters = new ArrayList<Map<String, Object>>();
        if (applyMaters != null && applyMaters.size() > 0) {
            filterApplyMaters.addAll(applyMaters);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(exeId)) {
            String tableName = "JBPM6_EXECUTION";
            if ("1".equals(isFiled)) {
                tableName = "JBPM6_EXECUTION_EVEHIS";
            }
            execution = applyMaterService.getByJdbc(tableName, new String[] { "EXE_ID" }, new Object[] { exeId });
            if (null != execution) {
                if (execution.get("PROJECT_CODE") != null) {
                    /* 工程建设项目查看是否有国标行业与产业指导目录字段 */
//                    execution = projectWebsiteApplyService.checkProjectIndustry(exeId, execution);
                    PROJECT_CODE = execution.get("PROJECT_CODE").toString();
                    if (STAGE_ID == null || "".equals(STAGE_ID)) {
                        Map<String, Object> stageInfo = projectWebsiteApplyService.findStageInfo(exeId, PROJECT_CODE);
                        if (stageInfo != null && stageInfo.get("STAGE_ID") != null) {
                            STAGE_ID = stageInfo.get("STAGE_ID").toString();
                        }
                    }
                    List<Map<String, Object>> materList = projectWebsiteApplyService.findMaterList(STAGE_ID,
                            PROJECT_CODE);
                    if (materList != null && materList.size() > 0) {
                        request.setAttribute("materListValue", true);
                        request.setAttribute("materList", materList);
                    }
                } // 获取流程状态
                String runStatus = execution.get("RUN_STATUS").toString();
                if (!runStatus.equals("0")) {
                    // 获取当前流程流转节点
                    currentNodeName = (String) execution.get("CUR_STEPNAMES");
                }
                if (flowVersion == 0) {
                    String defKey = (String) serviceItem.get("DEF_KEY");// 获取流程定义KEY
                    Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_KEY" },
                            new Object[] { defKey });
                    flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
                    String defId = (String) flowDef.get("DEF_ID");
                    currentNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                }
                String defId = (String) execution.get("DEF_ID");
                Map<String, Object> nodeConfig = flowNodeService.getByJdbc("JBPM6_NODECONFIG",
                        new String[] { "NODE_NAME", "DEF_ID", "DEF_VERSION" },
                        new Object[] { currentNodeName, defId, flowVersion });
                if (nodeConfig != null) {
                    request.setAttribute("nodeConfig", nodeConfig);
                }

//                busRecordId = execution.get("BUS_RECORDID") == null ? "" : execution.get("BUS_RECORDID").toString();// 获取业务ID
//                busTableName = execution.get("BUS_TABLENAME") == null ? "" : execution.get("BUS_TABLENAME").toString();// 获取业务表名称
            }
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(currentNodeName) && org.apache.commons.lang3.StringUtils.isNotEmpty(exeId) && applyMaters != null
                && applyMaters.size() > 0) {
            String sysUserName = AppUtil.getLoginUser() == null ? "" : AppUtil.getLoginUser().getUsername();
            List<Map<String, Object>> filterMater = applyMaterService.findFilterMater(sysUserName, currentNodeName,
                    exeId);
            if (filterMater != null && filterMater.size() > 0) {
                applyMaters.clear();
                for (int i = 0; i < filterMater.size(); i++) {
                    Map<String, Object> m = filterMater.get(i);
                    for (int j = 0; j < filterApplyMaters.size(); j++) {
                        Map<String, Object> fmap = filterApplyMaters.get(j);
                        if (m.get("MATER_ID").toString().equals(fmap.get("MATER_ID").toString())) {
                            applyMaters.add(fmap);
                        }
                    }
                }
            }
        }
//        List<Map<String, Object>> ysqMaters = null;
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(busRecordId) && org.apache.commons.lang3.StringUtils.isNotEmpty(busTableName)) {
//            // 根据业务表名和id获取该办件已收取的电子档材料附件
//            ysqMaters = fileAttachService.findYsqByList(busTableName, busRecordId);
//        }
        // 获取流程实例信息
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            applyMaters = getFlowCaseInfo(execution, request, applyMaters);
//            ysqMaters = getFlowCaseInfo(execution, request, ysqMaters);
        }
        // 定义材料列表JSON
//        String ysqMatersJson = "";
//        if (ysqMaters != null && ysqMaters.size() > 0) {
//            ysqMatersJson = JsonUtil.jsonStringFilter(ysqMaters, new String[] { "uploadFiles" }, true);
//        }
//        if (ysqMaters != null && ysqMaters.size() > 0) {
//            request.setAttribute("ysqMatersValue", true);
//        }
//        request.setAttribute("ysqMaters", ysqMaters);
//        request.setAttribute("ysqMatersJson", StringEscapeUtils.escapeHtml3(ysqMatersJson));
        // 开始设定工程建设项目相同附件描述智能回填
        if (null == execution && org.apache.commons.lang3.StringUtils.isNotEmpty(PROJECT_CODE)) {
            applyMaterService.setSameUploadFiles(applyMaters, formKey, PROJECT_CODE);
            applyMaterService.setSameKeyUploadFiles(applyMaters, formKey, PROJECT_CODE);
        }
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("handleTypes", handleTypes);
        request.setAttribute("serviceItem", serviceItem);
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
    }
    /**
     * 设置值
     *
     * @param execution
     * @param request
     * @param applyMaters
     * @return
     */
    public List<Map<String, Object>> getFlowCaseInfo(Map<String, Object> execution, HttpServletRequest request,
                                                     List<Map<String, Object>> applyMaters) {
        String busRecordId = (String) execution.get("BUS_RECORDID");// 获取业务ID
        String busTableName = (String) execution.get("BUS_TABLENAME");// 获取业务表名称
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);

        return applyMaters;
    }

}
