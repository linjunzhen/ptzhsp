/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

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
import net.evecom.platform.project.service.ProjectSgxkbgService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  施工许可变更Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2020年5月26日 15:06:14
 */
@Controller
@RequestMapping("/projectSgxkbgController")
public class ProjectSgxkbgController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectSgxkbgController.class);
    /**
     * 引入Service
     */
    @Resource
    private ProjectSgxkbgService projectSgxkbgService;
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
        projectSgxkbgService.remove("T_BSFW_GCJSSGXKBG","YW_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 施工许可变更记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  projectSgxkbg = projectSgxkbgService.getByJdbc("T_BSFW_GCJSSGXKBG",
                    new String[]{"YW_ID"},new Object[]{entityId});
            request.setAttribute("projectSgxkbg", projectSgxkbg);
        }
        return new ModelAndView("project/projectSgxkbg/info");
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
        String recordId = projectSgxkbgService.saveOrUpdate(variables, "T_BSFW_GCJSSGXKBG", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 施工许可变更记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 施工许可变更记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述：根据施工许可证编号获取施工许可信息
     * @author Rider Chen
     * @created 2020年5月26日 下午3:21:29
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSgxkxx")
    public  Map<String, Object> getPrjCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String constrNum = request.getParameter("constrNum");
        if(StringUtils.isEmpty(constrNum)){
            resultMap.put("status", false);
            resultMap.put("msg", "施工许可证编号为空"); 
        } else{
            Map<String, Object> sgxk = projectSgxkbgService.findSgxkToConstrNum(constrNum);
            if(null!=sgxk && sgxk.size()>0) {
                resultMap.put("status", true);
                resultMap.put("data", sgxk);
            }else {
                resultMap.put("status", false);
                resultMap.put("msg", "未查询到施工许可信息");
            }
        }
        return resultMap;
    }
}

