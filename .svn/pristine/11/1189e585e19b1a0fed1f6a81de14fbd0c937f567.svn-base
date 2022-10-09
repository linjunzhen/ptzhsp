/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.WorkdayService;

/**
 * 描述 投资项目详情Controller
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年11月26日 上午8:33:15
 */
@Controller
@RequestMapping("/projectDetailController")
public class ProjectDetailController extends BaseController {
    
    /**
     * 引入projectApplyService
     */
    @Resource
    private ProjectApplyService projectApplyService;
    
    /**
     * 引入Controller
     */
    @Resource
    private ProjectApplyController projectApplyController;
    
    /**
     * 引入workdayService
     */
    @Resource
    private WorkdayService workdayService;
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 项目列表视图
     * @param request
     * @return
     */
    @RequestMapping(params = "projectList")
    public ModelAndView projectList(HttpServletRequest request) {
        return new ModelAndView("project/projectDetail/projectList");
    }
    
    /**
     * 项目列表数据获取
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "projectListData")
    public void projectListData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.ID", "DESC");
        List<Map<String, Object>> list = projectApplyService.findBySqlFilter(filter, null);
        Map<String, Object> stageFilterMap = new HashMap<String, Object>();
        stageFilterMap.put("Q_T.FWSXMXLB_NEQ", "2");
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if(list!=null && list.size()>0) {
            for(Map<String, Object> map : list) {
                String projectCode = "";
                if(map.get("PROJECT_CODE") != null) {
                    projectCode = map.get("PROJECT_CODE").toString();
                }
                String stageName = projectApplyService.findStageProgress(projectCode);
                if(stageName==null || "".equals(stageName)) {
                    stageName = "立项用地规划许可阶段";//默认第一个阶段
                }
                map.put("stageName", stageName);
                resultList.add(map);
            }
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), resultList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> projectApply = projectApplyService.getByJdbc("SPGL_XMJBXXB", new String[] { "ID" },
                    new Object[] { entityId });
            if(projectApply.get("PROJECT_CODE") != null) {
                String projectCode = projectApply.get("PROJECT_CODE").toString();
                //前台共性材料
                projectApplyController.receptionMaterInfo(request, projectCode);
                //后台批复材料
                projectApplyController.backstageMaterInfo(request, projectCode);
            }
            String CREATOR_ID = projectApply.get("CREATOR_ID") == null ? "" : projectApply.get("CREATOR_ID").toString();
            if (StringUtils.isNotEmpty(CREATOR_ID)) {
                Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                        new Object[] { CREATOR_ID });
                if (null != userInfo && userInfo.size() > 0) {
                    request.setAttribute("projectUserInfo", userInfo);
                }
            }
            request.setAttribute("projectApply", projectApply);
        }
        request.setAttribute("projectDetail", true);
        return new ModelAndView("project/projectApply/info");
    }
    
    /**
     * 
     * 描述：
     * 
     * @author Scolder Lin
     * @created 2019年11月27日 上午11:19:40
     * @param request
     * @return
     */
    @RequestMapping("/ItemDetailSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        String stageId = request.getParameter("stageId");
        String FLOW_CATE_ID = request.getParameter("FLOW_CATE_ID");
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");
        request.setAttribute("FLOW_CATE_ID", FLOW_CATE_ID);
        request.setAttribute("PROJECT_CODE", PROJECT_CODE);
        List<Map<String, Object>> stageList = new ArrayList<Map<String, Object>>();
        if(FLOW_CATE_ID != null && !"".equals(FLOW_CATE_ID)) {
            stageList = projectApplyService.findStageList(FLOW_CATE_ID);
            if(stageId==null || "".equals(stageId)) {
                //如果为空，默认取第一阶段
                stageId = stageList.get(0).get("ID").toString();
            }
        }
        request.setAttribute("stageId", stageId);
        request.setAttribute("stageList", stageList);
        return new ModelAndView("project/projectDetail/ItemDetailSelector");
    }
    
    /**
     * 项目事项列表
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "projectItemList")
    public void projectItemList(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("S.TREE_SN", "ASC");
        filter.addSorted("S.CREATE_TIME", "ASC");
        filter.addSorted("L.SORT", "ASC");
        List<Map<String, Object>> resultlist = projectApplyService.findProjectDetailList(filter);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(resultlist!=null && resultlist.size()>0) {
            for(Map<String, Object> map : resultlist) {
                if(map.get("CREATE_TIME")!=null) {
                    String createTime = map.get("CREATE_TIME").toString();
                    String workDayCount = map.get("CNQXGZR").toString();//承诺时限（工作日）
                    String endTime = workdayService.getDeadLineDate(createTime,Integer.valueOf(workDayCount));
                    map.put("END_DATE", endTime+createTime.substring(10, 19));
                }
                list.add(map);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 项目列表视图
     * @param request
     * @return
     */
    @RequestMapping(params = "recallList")
    public ModelAndView recallList(HttpServletRequest request) {
        return new ModelAndView("project/projectDetail/recallList");
    }
    
    /**
     * 
     * 描述：工程建设项目撤回事项列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/projectRecallSelector")
    public ModelAndView projectRecallSelector(HttpServletRequest request) {
        String stageId = request.getParameter("stageId");
        String FLOW_CATE_ID = request.getParameter("FLOW_CATE_ID");
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");
        request.setAttribute("FLOW_CATE_ID", FLOW_CATE_ID);
        request.setAttribute("PROJECT_CODE", PROJECT_CODE);
        List<Map<String, Object>> stageList = new ArrayList<Map<String, Object>>();
        if(FLOW_CATE_ID != null && !"".equals(FLOW_CATE_ID)) {
            stageList = projectApplyService.findStageList(FLOW_CATE_ID);
            if(stageId==null || "".equals(stageId)) {
                //如果为空，默认取第一阶段
                stageId = stageList.get(0).get("ID").toString();
            }
        }
        request.setAttribute("stageId", stageId);
        request.setAttribute("stageList", stageList);
        return new ModelAndView("project/projectDetail/projectRecallSelector");
    }
    
    /**
     * 工程建设项目事项列表（撤回）
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "projectRecallList")
    public void projectRecallList(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("S.TREE_SN", "ASC");
        filter.addSorted("S.CREATE_TIME", "ASC");
        filter.addSorted("L.SORT", "ASC");
        String PROJECT_CODE = filter.getRequest().getParameter("PROJECT_CODE");
        String stageId = filter.getRequest().getParameter("Q_S.STAGE_ID_EQ");
        List<Map<String, Object>> resultList = projectApplyService.findProjectDetailList(filter);
        List<Map<String, Object>> recallList = projectApplyService.findProjectRecallList();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(resultList!=null && resultList.size()>0) {
            for(int i=0; i<resultList.size(); i++) {
                Map<String, Object> map = resultList.get(i);
                if(recallList!=null && recallList.size()>0) {
                    String itemCode = "";
                    if(map.get("ITEM_CODE")!=null) {
                        itemCode = map.get("ITEM_CODE").toString();
                    }
                    for(int j=0; j<recallList.size(); j++) {
                        Map<String, Object> recallMap = recallList.get(j);
                        if(recallMap.get("ITEM_CODE")!=null && recallMap.get("PROJECT_CODE")!=null) {
                            String recallItemCode = recallMap.get("ITEM_CODE").toString();
                            String projectCode = recallMap.get("PROJECT_CODE").toString();
                            if(projectCode.equals(PROJECT_CODE) && recallItemCode.equals(itemCode)) {
                                map.put("RECALL", true);
                                map.put("recallId", recallMap.get("YW_ID").toString());
                            }
                        }
                    }
                }
                if(map.get("CREATE_TIME")!=null) {
                    String createTime = map.get("CREATE_TIME").toString();
                    String workDayCount = map.get("CNQXGZR").toString();//承诺时限（工作日）
                    String endTime = workdayService.getDeadLineDate(createTime,Integer.valueOf(workDayCount));
                    map.put("END_DATE", endTime+createTime.substring(10, 19));
                }
                list.add(map);
            }
        }
        request.setAttribute("stageId", stageId);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 获取项目撤回列表数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "projectRecallData")
    public void projectRecallData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String, Object>> list = projectApplyService.findProjectRecallData(filter, null);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述：工程建设项目撤回事项列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "checkRecall")
    public ModelAndView checkRecall(HttpServletRequest request) {
        String stageId = request.getParameter("stageId");
        String flowCateId = request.getParameter("flowCateId");
        String projectCode = request.getParameter("projectCode");
        String recallId = request.getParameter("recallId");
        String exeId = request.getParameter("exeId");
        String type = request.getParameter("type");
        if(type!=null && !"".equals(type)) {
            //审核通过
            if("1".equals(type)) {
                projectApplyService.updateExeProCode(exeId);
                projectApplyService.updateRecallInfo(recallId, type);
            }else{//审核不通过
                projectApplyService.updateRecallInfo(recallId, type);
            }
        }
        request.setAttribute("FLOW_CATE_ID", flowCateId);
        request.setAttribute("PROJECT_CODE", projectCode);
        List<Map<String, Object>> stageList = new ArrayList<Map<String, Object>>();
        if(flowCateId != null && !"".equals(flowCateId)) {
            stageList = projectApplyService.findStageList(flowCateId);
            if(stageId==null || "".equals(stageId)) {
                //如果为空，默认取第一阶段
                stageId = stageList.get(0).get("ID").toString();
            }
        }
        request.setAttribute("stageId", stageId);
        request.setAttribute("stageList", stageList);
        return new ModelAndView("project/projectDetail/projectRecallSelector");
    }
    /**
     * 撤件审核弹窗界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "recallCheckSelector")
    public ModelAndView recallCheckSelector(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        request.setAttribute("fileServer", fileServer);
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> recallInfo = projectApplyService.getByJdbc("T_WSBS_PROJECT_RECALL",
                    new String[] { "YW_ID" }, new Object[] { entityId });
            request.setAttribute("recallInfo", recallInfo);
        }
        return new ModelAndView("project/projectDetail/recallCheckSelector");
    }
    /**
     * 撤件审核
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "recallCheck")
    @ResponseBody
    public AjaxJson recallCheck(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("YW_ID");
        String exeId = request.getParameter("EXE_ID");
        if(StringUtils.isNotEmpty(entityId) && StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if(variables.get("CHECK_STATUS")!=null) {
                String checkStatus = variables.get("CHECK_STATUS").toString();
                if(checkStatus!=null && "1".equals(checkStatus)) {
                    projectApplyService.updateExeProCode(exeId);
                }
                SysUser sysUser = AppUtil.getLoginUser();
                variables.put("CHECK_OPERATOR", sysUser.getFullname());
                variables.put("CHECK_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                projectApplyService.saveOrUpdate(variables, "T_WSBS_PROJECT_RECALL", entityId);
                sysLogService.saveLog("修改了ID为[" + entityId + "]的工程建设项目流程撤回记录", SysLogService.OPERATE_TYPE_EDIT);
                j.setSuccess(true);
                j.setMsg("审核成功");
            }else {
                j.setSuccess(false);
                j.setMsg("审核状态不能为空");
            }
        }else {
            j.setSuccess(false);
            j.setMsg("业务编号或流程编号不能为空");
        }
        return j;
    }
}
