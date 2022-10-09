/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 下午2:00:58
 */
@Controller
@RequestMapping("/sysLogController")
public class SysLogController extends BaseController {
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
        sysLogService.remove("T_MSJW_SYSTEM_SYSLOG","LOG_ID",selectColNames.split(","));
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
        return new ModelAndView("system/syslog/info");
    }
    /**
     * 
     * 跳转到日志列表界面
     * @param request
     * @return
     */
    @RequestMapping(params = "SysLogList")
    public ModelAndView sysLogList(HttpServletRequest request) {
       /* String content = FileUtil.getContentOfFile("d:/html.txt");
        request.setAttribute("content1", StringUtil.htmlEncode(content));*/
        return new ModelAndView("system/syslog/SysLogList");
    }
    
    /**
     * 获取类别数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("L.OPERATE_TIME","desc");
        List<Map<String, Object>> list = sysLogService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}
