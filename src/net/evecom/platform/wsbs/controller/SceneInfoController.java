/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.SceneInfoService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  场景导航Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/sceneInfoController")
public class SceneInfoController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SceneInfoController.class);
    /**
     * 引入Service
     */
    @Resource
    private SceneInfoService sceneInfoService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
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
        sceneInfoService.remove("T_WSBS_SCENEINFO","SCENE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 场景导航记录",SysLogService.OPERATE_TYPE_DEL);
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
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  sceneInfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            sceneInfo = sceneInfoService.getByJdbc("T_WSBS_SCENEINFO",
                    new String[]{"SCENE_ID"},new Object[]{entityId});
            
        }
        sceneInfo.put("PARENT_ID", parentId);
        sceneInfo.put("PARENT_NAME", parentName);
        request.setAttribute("sceneInfo", sceneInfo);
        return new ModelAndView("wsbs/sceneinfo/SceneInfo");
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
        String entityId = request.getParameter("SCENE_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        String recordId = sceneInfoService.saveOrUpdateTreeData(parentId, treeData,"T_WSBS_SCENEINFO",null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的场景导航记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的场景导航记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到管理界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/sceneinfo/sceneView");
    }
    
    /**
     * 分配办事项目
     * @param request
     * @return
     */
    @RequestMapping(params = "grantguides")
    @ResponseBody
    public AjaxJson grantguides(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String sceneId = request.getParameter("sceneId");
        String guideIds = request.getParameter("guideIds");
        sceneInfoService.saveGuides(sceneId, guideIds);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 移除办事项目
     * @param request
     * @return
     */
    @RequestMapping(params = "removeGuides")
    @ResponseBody
    public AjaxJson removeGuides(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String guideIds = request.getParameter("selectColNames");
        String sceneId = request.getParameter("sceneId");
        sceneInfoService.removeGuides(sceneId, guideIds);
        j.setMsg("操作成功");
        return j;
    }
}

