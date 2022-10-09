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

import com.jacob.com.STA;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 投资项目申报Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/projectApplyController")
public class ProjectApplyController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectApplyController.class);
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
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
     * projectWebsiteApplyService
     */
    @Resource
    private ProjectWebsiteApplyService projectWebsiteApplyService;

    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("project/projectApply/list");
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        projectApplyService.remove("SPGL_XMJBXXB", "ID", selectColNames.split(","));
        projectApplyService.remove("SPGL_XMDWXXB", "JBXX_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 投资项目申报记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
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
                receptionMaterInfo(request, projectCode);
                //后台批复材料
                backstageMaterInfo(request, projectCode);
                //会商反馈意见汇总材料
                consultationMaterInfo(request, projectCode);
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
        return new ModelAndView("project/projectApply/info");
    }
    /**
     * 前台共性材料信息
     * @param request
     * @param projectCode
     */
    public void receptionMaterInfo(HttpServletRequest request, String projectCode) {
        List<Map<String,Object>> resultList = projectWebsiteApplyService.findMaterListByProjectCode(projectCode,"1");
        if(resultList!=null && resultList.size()>0) {
            String categoryId = "";
            List<Map<String,Object>> receptionCategoryList = new ArrayList<Map<String,Object>>();
            List<Map<String,Object>> receptionMaterList = new ArrayList<Map<String,Object>>();
            for(int i=0; i<resultList.size();i++) {
                Map<String,Object> categoryMap = new HashMap<String,Object>();
                Map<String,Object> materMap = resultList.get(i);
                if(materMap.get("CATEGORY_ID") !=null 
                        && !categoryId.equals(materMap.get("CATEGORY_ID").toString())) {
                    categoryMap.put("CATEGORY_ID", materMap.get("CATEGORY_ID").toString());
                    categoryMap.put("CATEGORY_NAME", materMap.get("CATEGORY_NAME").toString());
                    receptionCategoryList.add(categoryMap);
                    categoryId = materMap.get("CATEGORY_ID").toString();
                }
            }
            request.setAttribute("receptionMaterInfo", true);
            request.setAttribute("receptionCategoryList", receptionCategoryList);
            if(receptionCategoryList != null && receptionCategoryList.size()>0) {
                for(Map<String, Object> categoryMap : receptionCategoryList) {
                    categoryId = categoryMap.get("CATEGORY_ID").toString();
                    int i = 1;
                    for(Map<String, Object> materMap : resultList) {
                        if (materMap.get("CATEGORY_ID") != null
                                && categoryId.equals(materMap.get("CATEGORY_ID").toString())) {
                            materMap.put("orderNum", i);
                            i++;
                            if(materMap.get("FILE_ID") != null) {
                                String fileId = materMap.get("FILE_ID").toString();
                                if(fileId.contains("_") || fileId.length()!=32) {
                                    materMap.put("newUpload", true);
                                }
                            }
                            receptionMaterList.add(materMap);
                        }
                    }
                }
            }
            request.setAttribute("receptionMaterList", receptionMaterList);
        }
    }
    /**
     * 施工许可电子证照材料信息
     * @param request
     * @param projectCode
     */
    public void sgkxMaterInfo(HttpServletRequest request, String projectCode) {
        List<Map<String,Object>> resultList = projectWebsiteApplyService.findSgxkMaterListByProjectCode(projectCode);
        if(resultList!=null && resultList.size()>0) {
            List<Map<String,Object>> sgxkMaterList = new ArrayList<Map<String,Object>>();
            for(int i=0; i<resultList.size();i++) {
                Map<String,Object> materMap = resultList.get(i);
                materMap.put("orderNum", i+1);
                sgxkMaterList.add(materMap);
            }
            request.setAttribute("sgxkMaterInfo", true);
            request.setAttribute("sgxkMaterList", sgxkMaterList);
        }
    }
    
    /**
     * 施工许可电子证照材料信息
     * @param request
     * @param projectCode
     */
    public void backstageMaterInfo(HttpServletRequest request, String projectCode) {
        List<Map<String,Object>> resultList = projectWebsiteApplyService.findMaterListByProjectCode(projectCode,"2");
        if(resultList!=null && resultList.size()>0) {
            String categoryId = "";
            List<Map<String,Object>> backstageCategoryList = new ArrayList<Map<String,Object>>();
            List<Map<String,Object>> backstageMaterList = new ArrayList<Map<String,Object>>();
            for(int i=0; i<resultList.size();i++) {
                Map<String,Object> categoryMap = new HashMap<String,Object>();
                Map<String,Object> materMap = resultList.get(i);
                if(materMap.get("CATEGORY_ID") !=null 
                        && !categoryId.equals(materMap.get("CATEGORY_ID").toString())) {
                    categoryMap.put("CATEGORY_ID", materMap.get("CATEGORY_ID").toString());
                    categoryMap.put("CATEGORY_NAME", materMap.get("CATEGORY_NAME").toString());
                    backstageCategoryList.add(categoryMap);
                    categoryId = materMap.get("CATEGORY_ID").toString();
                }
            }
            request.setAttribute("backstageMaterInfo", true);
            request.setAttribute("backstageCategoryList", backstageCategoryList);
            if(backstageCategoryList != null && backstageCategoryList.size()>0) {
                for(Map<String, Object> categoryMap : backstageCategoryList) {
                    categoryId = categoryMap.get("CATEGORY_ID").toString();
                    int i = 1;
                    for(Map<String, Object> materMap : resultList) {
                        if (materMap.get("CATEGORY_ID") != null
                                && categoryId.equals(materMap.get("CATEGORY_ID").toString())) {
                            materMap.put("orderNum", i);
                            i++;
                            backstageMaterList.add(materMap);
                        }
                    }
                }
            }
            request.setAttribute("backstageMaterList", backstageMaterList);
        }
    }
    /**
     * 会商材料信息
     * @param request
     * @param projectCode
     */
    public void consultationMaterInfo(HttpServletRequest request, String projectCode) {
        List<Map<String,Object>> resultList = projectApplyService.getAllByJdbc("T_MSJW_PROJECT_HS_FILE",
                new String[]{"PROJECT_CODE"},new Object[]{projectCode});
        if(resultList!=null && resultList.size()>0) {
            List<Map<String,Object>> consultationMaterList = new ArrayList<Map<String,Object>>();
            request.setAttribute("consultationMaterInfo", true);
            int i = 1;
            for(Map<String, Object> materMap : resultList) {
                materMap.put("orderNum", i);
                i++;
                if(materMap.get("FILE_ID") != null) {
                    String fileId = materMap.get("FILE_ID").toString();
                    if(fileId.contains("_") || fileId.length()!=32) {
                        materMap.put("newUpload", true);
                    }
                }
                consultationMaterList.add(materMap);
            }
            request.setAttribute("consultationMaterList", consultationMaterList);
        }
    }
    /**
     * 跳转到项目分类选择器
     * 
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        String induIds = request.getParameter("induIds");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", induIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("project/projectApply/xmflxzqSelector");
    }

    /**
     * 
     * 描述：
     * 
     * @author Rider Chen
     * @created 2019年6月11日 上午11:19:40
     * @param request
     * @return
     */
    @RequestMapping("/itemSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        String FLOW_CATE_ID = request.getParameter("FLOW_CATE_ID");
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");
        request.setAttribute("FLOW_CATE_ID", FLOW_CATE_ID);
        request.setAttribute("PROJECT_CODE", PROJECT_CODE);
        return new ModelAndView("project/projectApply/ItemSelector");
    }

    /**
     * easyui AJAX请求数据 发布库列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "publishdatagrid")
    public void publishdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("S.TREE_SN", "ASC");
        filter.addSorted("S.CREATE_TIME", "ASC");
        filter.addSorted("L.SORT", "ASC");        
        List<Map<String, Object>> resultlist = projectApplyService.findByPublishSqlFilter(filter);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(resultlist!=null && resultlist.size()>0) {
            for(Map<String, Object> map : resultlist) {
                if(map.get("CATEGORY_ID")!=null) {
                    map.put("uploadName", map.get("CATEGORY_ID").toString());
                }
                list.add(map);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATOR_NAME", sysUser.getFullname());
            variables.put("CREATOR_ID", sysUser.getUserId());
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            variables.put("PUSH_STATUS", 0);
        }
        String recordId = projectApplyService.saveOrUpdate(variables, "SPGL_XMJBXXB", entityId);
        String lerepInfo = variables.get("LEREP_INFO").toString();
        List<Map<String, Object>> lerepInfoList = JSON.parseObject(lerepInfo, List.class);
        // 删除项目单位信息
        projectApplyService.remove("SPGL_XMDWXXB", "JBXX_ID", new Object[] { recordId });
        for (Map<String, Object> map : lerepInfoList) {
            map.put("JBXX_ID", recordId);
            // 保存项目单位信息
            projectApplyService.saveOrUpdate(map, "SPGL_XMDWXXB", null);
        }
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 投资项目申报记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 投资项目申报记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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
        List<Map<String, Object>> list = projectApplyService.findBySqlFilter(filter, null);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "loadTzxmxxData")
    public void loadTzxmxxData(HttpServletRequest request, HttpServletResponse response) {
        String projectCode = request.getParameter("projectCode");
        
        Properties projectProperties = FileUtil.readProperties("conf/config.properties");
        String isNetwork = projectProperties.getProperty("isNetwork");
        String internetAddress = projectProperties.getProperty("internetAddress");
        //公用网络区
        if(StringUtils.isNotEmpty(isNetwork) && isNetwork.equals("1")){
            String json = HttpRequestUtil.sendPost(internetAddress+"projectApplyController.do?loadTzxmxxData",
                    "projectCode="+projectCode, "UTF-8");
            this.setJsonString(json, response);
        }else{
            Map<String, Object> data = projectApplyService.loadTzxmxxData(projectCode);
            String json = JSON.toJSONString(data);
            this.setJsonString(json, response);
        }
    }

    /**
     * 
     * 描述： 获取要进行推送的项目分类列表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toDfxmsplcxxb")
    public void toDfxmsplcxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveDfxmsplcxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 获取要进行推送的项目分类阶段列表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toDfxmsplcjdxxb")
    public void toDfxmsplcjdxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveDfxmsplcjdxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 获取要进行推送的项目分类阶段事项列表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toDfxmsplcjdsxxxb")
    public void toDfxmsplcjdsxxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveDfxmsplcjdsxxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 获取要进行推送的项目基本信息表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toXmjbxxb")
    public void toXmjbxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveXmjbxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 获取要进行推送的项目单位信息表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toXmdwxxb")
    public void toXmdwxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveXmdwxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 获取要进行推送的项目审批事项办理信息表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toXmspsxblxxb")
    public void toXmspsxblxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveXmspsxblxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 获取要进行推送的项目审批事项办理详细信息表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:03:46
     * @param request
     * @param response
     */
    @RequestMapping("/toXmspsxblxxxxb")
    public void toXmspsxblxxxxb(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> data = projectApplyService.saveXmspsxblxxxxb(id);
        String json = JSON.toJSONString(data);
        this.setJsonString(json, response);
    }

    /**
     * 描述:根据事项编码获取工程建设项目所对应的阶段
     *
     * @author Madison You
     * @created 2019/10/14 10:20:00
     * @param
     * @return
     */
    @RequestMapping("/getProjectJDByItemCode")
    @ResponseBody
    public Map<String,Object> getProjectJDByItemCode(HttpServletRequest request , HttpServletResponse response) {
        String itemCode = request.getParameter("ITEM_CODE");
        return projectApplyService.findProjectJD(itemCode);
    }
    
    
/**
 *     
 * @Description 能否注销工程建设项目
 * @author Luffy Cai
 * @date 2020年11月6日
 * @param request
 * @return
 * AjaxJson
 */
    @RequestMapping(params = "isCancelOk")
    @ResponseBody
    public AjaxJson isCancelOk(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String projectCode = request.getParameter("projectCode");
        List<Map<String, Object>> list = projectApplyService.findListByProjectCode(projectCode);
        if(list!=null&&list.size()>0){
            j.setSuccess(false);
            j.setMsg("该事项存在未撤回的办件，无法注销！"); 
        }else {
            j.setSuccess(true);
        }
        return j;
    }    
    
    
    /**
     * 
     * @Description 注销工程建设项目
     * @author Luffy Cai
     * @date 2020年11月6日
     * @param request
     * @return AjaxJson
     */
    @RequestMapping(params = "cancelProject")
    @ResponseBody
    public AjaxJson cancelProject(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("PRO_STATUS", 0);
        String recordId = projectApplyService.saveOrUpdate(variables, "SPGL_XMJBXXB", entityId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("IS_DATA_VALID", 0);
        map.put("JBXX_ID", recordId);
        projectApplyService.saveOrUpdate(map, "SPGL_XMDWXXB", null);
        sysLogService.saveLog("修改了ID为[" + entityId + "]的 投资项目申报记录", SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("注销成功");
        return j;
    }

}
