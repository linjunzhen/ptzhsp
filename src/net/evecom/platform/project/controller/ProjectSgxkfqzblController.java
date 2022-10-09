/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import java.util.Date;
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
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.ProjectSgxkfqzblService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  施工许可废弃再办理Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/projectSgxkfqzblController")
public class ProjectSgxkfqzblController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectSgxkfqzblController.class);
    /**
     * 引入Service
     */
    @Resource
    private ProjectSgxkfqzblService projectSgxkfqzblService;
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
        projectSgxkfqzblService.remove("T_BSFW_GCJSSGXKFQZBL","YW_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 施工许可废弃再办理记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  projectSgxkfqzbl = projectSgxkfqzblService.getByJdbc("T_BSFW_GCJSSGXKFQZBL",
                    new String[]{"YW_ID"},new Object[]{entityId});
            request.setAttribute("projectSgxkfqzbl", projectSgxkfqzbl);
        }
        return new ModelAndView("project/projectSgxkfqzbl/info");
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
        String entityId = request.getParameter("YW_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = projectSgxkfqzblService.saveOrUpdate(variables, "T_BSFW_GCJSSGXKFQZBL", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 施工许可废弃再办理记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 施工许可废弃再办理记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 跳转到建设单位界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/refreshJsdwDiv")
    public ModelAndView refreshJsdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        String isDel = request.getParameter("isDel");
        request.setAttribute("isDel", isDel);
        String divId = request.getParameter("divId");
        request.setAttribute("divId", divId);
        request.setAttribute("currentTime", currentTime);
        String info = request.getParameter("info");
        if(StringUtils.isNotEmpty(info)){
            Map<String, Object> data = JSON.parseObject(info, Map.class);
            request.setAttribute("data", data);
        }
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgskfqzbl/children/jsdwDiv");
    }
    /**
     * 跳转到施工单位界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/refreshSgdwDiv")
    public ModelAndView refreshSgdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        String isDel = request.getParameter("isDel");
        request.setAttribute("isDel", isDel);
        String divId = request.getParameter("divId");
        request.setAttribute("divId", divId);
        request.setAttribute("currentTime", currentTime);
        String info = request.getParameter("info");
        if(StringUtils.isNotEmpty(info)){
            Map<String, Object> data = JSON.parseObject(info, Map.class);
            request.setAttribute("data", data);
        }
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgskfqzbl/children/sgdwDiv");
    }

    /**
     * 跳转到监理单位界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/refreshJldwDiv")
    public ModelAndView refreshJldwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        String isDel = request.getParameter("isDel");
        request.setAttribute("isDel", isDel);
        String divId = request.getParameter("divId");
        request.setAttribute("divId", divId);
        request.setAttribute("currentTime", currentTime);
        String info = request.getParameter("info");
        if(StringUtils.isNotEmpty(info)){
            Map<String, Object> data = JSON.parseObject(info, Map.class);
            request.setAttribute("data", data);
        }
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgskfqzbl/children/jldwDiv");
    }
    
}

