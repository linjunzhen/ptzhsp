/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.projectflow.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.projectflow.service.ProjectFlowService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述  工程建设项目流程管理Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/projectFlowController")
public class ProjectFlowController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectFlowController.class);
    /**
     * 引入Service
     */
    @Resource
    private ProjectFlowService projectFlowService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * projectWebsiteApplyService
     */
    @Resource
    private ProjectWebsiteApplyService projectWebsiteApplyService;
    
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
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
        projectFlowService.remove("SPGL_XMJBXXB","ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 工程建设项目流程管理记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述   跳转到工程建设信息页面
     * @author Scolder Lin
     * @created 2020-10-15 上午11:47:41
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="projectInfoTab")
    public ModelAndView projectInfoTab(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String,Object> projectFlow = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            projectFlow = projectFlowService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
            if(projectFlow!=null) {
                projectFlow = formatProjectFlowMap(projectFlow);
                if(projectFlow.get("LEREP_INFO")!=null) {
                    String lerepJson = (String)projectFlow.get("LEREP_INFO");
                    List<Map> lerepList = JSON.parseArray(lerepJson, Map.class);
                    if(lerepList!=null && lerepList.size()>0) {
                        Map<String, Object> lerepInfo = lerepList.get(0);
                        lerepInfo = formatLerepMap(lerepInfo);
                        request.setAttribute("lerepInfo", lerepInfo);
                    }
                }
            }
        }
        //共性材料
        List<Map<String, Object>> generalityMaterList = projectFlowService.findGeneralityMaterList(entityId);
        request.setAttribute("generalityMaterList", generalityMaterList);
        
        //申报材料
        List<Map<String, Object>> applyMaterList = projectFlowService.findApplyMaterList(entityId);
        Map<String,Object> stageMap = projectFlowService.getByJdbc("T_FLOW_CONFIG_STAGE",
                new String[]{"STAGE_ID"},new Object[]{projectFlow.get("STAGE_ID")});
        request.setAttribute("stageMap", stageMap);
        request.setAttribute("applyMaterList", applyMaterList);
        
        //流转公文材料
        List<Map<String, Object>> flowMaterList = projectFlowService.findFlowMaterList(entityId);
        request.setAttribute("flowMaterList", flowMaterList);
        
        //许可证材料
        String projectCode = (String)projectFlow.get("PROJECT_CODE");
        List<Map<String, Object>> licenceMaterList = 
                projectWebsiteApplyService.findSgxkMaterListByProjectCode(projectCode);
        request.setAttribute("licenceMaterList", licenceMaterList);
        
        //电子证照材料
        List<Map<String, Object>> electronicMaterList = projectFlowService.findElectronicMaterList(entityId);
        request.setAttribute("electronicMaterList", electronicMaterList);
        
        request.setAttribute("info", projectFlow);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("projectflow/project/projectInfoTab1");
    }
    
    /**
     * MAP中相关字典值的转换（项目基本信息）
     * @param projectFlow
     * @return
     */
    public Map<String, Object> formatProjectFlowMap(Map<String,Object> projectFlow){
        if(projectFlow.get("PROJECT_TYPE")!=null) {
            projectFlow.put("PROJECT_TYPE", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("PROJECT_TYPE"),"PROJECTTYPE"));
        }
        if(projectFlow.get("PROJECT_NATURE")!=null) {
            projectFlow.put("PROJECT_NATURE", dictionaryService.findByDicCodeAndTypeCode(
                  new BigDecimal(projectFlow.get("PROJECT_NATURE").toString()).toString(),"PROJECTNATURE"));
        }
        if(projectFlow.get("INDUSTRY")!=null) {
            String industry = projectFlow.get("INDUSTRY").toString();
            projectFlow.put("INDUSTRY", dictionaryService.findIndustryByTypeCode(industry));
        }
        if(projectFlow.get("PROJECT_ATTRIBUTES")!=null) {
            projectFlow.put("PROJECT_ATTRIBUTES", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("PROJECT_ATTRIBUTES"),"PROJECTATTRIBUTES"));
        }
        if(projectFlow.get("INDUSTRY_STRUCTURE")!=null) {
            String industryStructure = projectFlow.get("INDUSTRY_STRUCTURE").toString();
            projectFlow.put("INDUSTRY_STRUCTURE", dictionaryService.findIndustryByTypeCode(industryStructure));
        }
        if(projectFlow.get("GET_LAND_MODE")!=null) {
            projectFlow.put("GET_LAND_MODE", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("GET_LAND_MODE"),"getLandMode"));
        }
        if(projectFlow.get("XMTZLY")!=null) {
            projectFlow.put("XMTZLY", dictionaryService.findByDicCodeAndTypeCode(
                  new BigDecimal(projectFlow.get("XMTZLY").toString()).toString(),"XMTZLY"));
        }
        if(projectFlow.get("TDSFDSJFA")!=null) {
            projectFlow.put("TDSFDSJFA", dictionaryService.findByDicCodeAndTypeCode(
                  new BigDecimal(projectFlow.get("TDSFDSJFA").toString()).toString(),"TDSFDSJFA"));
        }
        if(projectFlow.get("SFWCQYPG")!=null) {
            projectFlow.put("SFWCQYPG", dictionaryService.findByDicCodeAndTypeCode(
                  new BigDecimal(projectFlow.get("SFWCQYPG").toString()).toString(),"SFWCQYPG"));
        }
        if(projectFlow.get("GCFL")!=null) {
            projectFlow.put("GCFL", dictionaryService.findByDicCodeAndTypeCode(
                  new BigDecimal(projectFlow.get("GCFL").toString()).toString(),"GCFL"));
        }
        if(projectFlow.get("IS_COUNTRY_SECURITY")!=null) {
            projectFlow.put("IS_COUNTRY_SECURITY", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("IS_COUNTRY_SECURITY"),"YesOrNo"));
        }
        if(projectFlow.get("INVESTMENT_MODE")!=null) {
            projectFlow.put("INVESTMENT_MODE", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("INVESTMENT_MODE"),"investmentMode"));
        }
        if(projectFlow.get("INDUSTRIAL_POLICY_TYPE")!=null) {
            projectFlow.put("INDUSTRIAL_POLICY_TYPE", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("INDUSTRIAL_POLICY_TYPE"),"industrialPolicyType"));
        }
        if(projectFlow.get("INDUSTRIAL_POLICY")!=null) {
            projectFlow.put("INDUSTRIAL_POLICY", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("INDUSTRIAL_POLICY"),"industrialPolicy"));
        }
        if(projectFlow.get("IS_ADD_DEVICE")!=null) {
            projectFlow.put("IS_ADD_DEVICE", dictionaryService.findByDicCodeAndTypeCode(
                    (String)projectFlow.get("IS_ADD_DEVICE"),"YesOrNo"));
        }
        return projectFlow;
    }
    /**
     * MAP中相关字典值的转换（项目单位信息）
     * @param lerepInfo
     * @return
     */
    public Map<String, Object> formatLerepMap(Map<String,Object> lerepInfo){
        if(lerepInfo.get("dwlx")!=null) {
            lerepInfo.put("dwlx", dictionaryService.findByDicCodeAndTypeCode(
                    (String)lerepInfo.get("dwlx"),"DWLX"));
        }
        if(lerepInfo.get("lerep_certtype")!=null) {
            lerepInfo.put("lerep_certtype", dictionaryService.findByDicCodeAndTypeCode(
                    (String)lerepInfo.get("lerep_certtype"),"LEREPCERTTYPE"));
        }
        if(lerepInfo.get("enterprise_nature")!=null) {
            lerepInfo.put("enterprise_nature", dictionaryService.findByDicCodeAndTypeCode(
                    (String)lerepInfo.get("enterprise_nature"),"enterpriseNature"));
        }
        if(lerepInfo.get("china_foreign_share_ratio")!=null) {
            lerepInfo.put("china_foreign_share_ratio", dictionaryService.findByDicCodeAndTypeCode(
                    (String)lerepInfo.get("china_foreign_share_ratio"),"chinaForeignShareRatio"));
        }
        return lerepInfo;
    }
    /**
     * 跳转到项目基本信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "projectInfo")
    public ModelAndView projectInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  projectFlow = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            projectFlow = projectFlowService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
        }
        request.setAttribute("info", projectFlow);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("projectflow/project/projectInfo");
    }
    
    /**
     * 跳转到审批结果信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "approvalResultInfo")
    public ModelAndView approvalResultInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  projectFlow = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            projectFlow = projectFlowService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
        }
        request.setAttribute("info", projectFlow);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("projectflow/project/approvalResultInfo");
    }
    
    /**
     * 跳转到项目材料信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "projectMaterInfoTab")
    public ModelAndView projectMaterInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  projectFlow = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            projectFlow = projectFlowService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
        }
        request.setAttribute("info", projectFlow);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("projectflow/project/projectMaterInfoTab");
    }
    
    /**
     * 跳转到共性材料信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "generalityMater")
    public ModelAndView generalityMater(HttpServletRequest request) {
        String materType = request.getParameter("materType");
        String entityId = request.getParameter("entityId");
        Map<String,Object>  projectFlow = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            projectFlow = projectFlowService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
            
            //许可证材料信息
            String projectCode = (String)projectFlow.get("PROJECT_CODE");
            List<Map<String, Object>> licenceMaterList = 
                    projectWebsiteApplyService.findSgxkMaterListByProjectCode(projectCode);
            request.setAttribute("licenceMaterList", licenceMaterList);
        }
        request.setAttribute("info", projectFlow);
        request.setAttribute("entityId", entityId);
        request.setAttribute("materType", materType);
        /*if(materType!=null && !"".equals(materType)) {
            request.setAttribute("materType", materType);
            if("1".equals(materType)) {
                return new ModelAndView("projectflow/project/generalityMater");
            }else if("2".equals(materType)) {
                return new ModelAndView("projectflow/project/declareMater");
            }
        }*/
        return new ModelAndView("projectflow/project/generalityMater");
    }
    
    /**
     * 跳转到申报材料信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "declareMater")
    public ModelAndView declareMater(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  projectFlow = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            projectFlow = projectFlowService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
        }
        request.setAttribute("info", projectFlow);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("projectflow/project/declareMater");
    }
    
    /**
     * 
     * 描述：
     * 
     * @author Scolder Lin
     * @created 2020年11月13日 上午10:19:40
     * @param request
     * @return
     */
    @RequestMapping(params = "projectNodeInfo")
    public ModelAndView projectNodeInfo(HttpServletRequest request) {
        String projectCode = request.getParameter("projectCode");
        
        List<Map<String, Object>> projectCodeList = new ArrayList<Map<String, Object>>();
        
        request.setAttribute("projectCode", projectCode);
        request.setAttribute("projectCodeList", projectCodeList);
        return new ModelAndView("projectflow/project/projectNodeInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = projectFlowService.saveOrUpdate(variables, "SPGL_XMJBXXB", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 工程建设项目流程管理记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 工程建设项目流程管理记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述：项目管理列表页面
     * @author Rider Chen
     * @created 2020年9月21日 上午11:46:30
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("projectflow/project/list");
    }
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.ID", "DESC");
        List<Map<String, Object>> list = projectFlowService.findBySqlFilter(filter, null);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 加载审批流程类型管理下拉框数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadConfigType")
    public void loadConfigType(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String,Object>> list = projectFlowService.findConfigType();
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("TYPE_ID","");
        map.put("TYPE_NAME", "请选择项目类型");
        list.add(0, map);
        
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
}

