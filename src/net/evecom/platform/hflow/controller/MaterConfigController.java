/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

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
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.MaterConfigService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  审批材料Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/materConfigController")
public class MaterConfigController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);
    /**
     * 引入Service
     */
    @Resource
    private MaterConfigService materConfigService;
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
        materConfigService.remove("JBPM6_MATERCONFIG","CONFIG_ID",selectColNames.split(","));
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
            Map<String,Object>  materConfig = materConfigService.getByJdbc("JBPM6_MATERCONFIG",
                    new String[]{"CONFIG_ID"},new Object[]{entityId});
            request.setAttribute("materConfig", materConfig);
        }
        return new ModelAndView("hflow/materConfig/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "bindTemplate")
    @ResponseBody
    public AjaxJson bindTemplate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String defId = request.getParameter("defId");
        String nodeName = request.getParameter("nodeName");
        String tplIds = request.getParameter("tplIds");
        materConfigService.saveOrUpdate(defId, nodeName, tplIds);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到权限配置界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "materconf")
    public ModelAndView materconf(HttpServletRequest request) {
        Map<String,Object> nodeData = BeanUtil.getMapFromRequest(request);
        request.setAttribute("nodeData", nodeData);
        return new ModelAndView("hflow/materconfig/materconfigview");
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
        filter.addSorted("T.CONFIG_SN","asc");
        List<Map<String, Object>> list = materConfigService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 方法updateSn
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] configIds = request.getParameterValues("configIds[]");
        this.materConfigService.updateSn(configIds);
        j.setMsg("排序成功");
        return j;
    }
    /**
     * 
     * 描述 设置模版是否回填
     * @author Faker Li
     * @created 2016年4月1日 下午3:30:44
     * @param request
     * @return
     */
    @RequestMapping(params = "updateIsBackfill")
    @ResponseBody
    public AjaxJson updateIsneed(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String isBackfill = request.getParameter("isBackfill");
        materConfigService.updateIsBackfill(isBackfill,selectColNames);
        j.setMsg("操作成功");
        return j;
    }
}

