/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.CommonOpinionService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 描述  预审业务处理Controller
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月14日 下午2:25:37
 */
@Controller
@RequestMapping("/preAuditController")
public class PreAuditController extends BaseController {
    /**
     * log4J声明
     */
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(PreAuditController.class);
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    

    /**
     * 引入Service
     */
    @Resource
    private CommonOpinionService commonOpinionService;
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "proAudit")
    public ModelAndView info(HttpServletRequest request) {
        String flag = request.getParameter("flag");
        String title="";
        if (flag.equals("1")) {
            title = "预审通过";
        } else if (flag.equals("2")) {
            title = "预审不通过";
        } else if (flag.equals("3")) {
            title = "预审补件";
        } else if (flag.equals("4")) {
            title = "预审转办";
        }
        String entityId = request.getParameter("entityId");
        request.setAttribute("serviceItem", entityId);
        request.setAttribute("flag", flag);
        request.setAttribute("title", title);
        return new ModelAndView("wsbs/preaudit/preAuditInfo");
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
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = serviceItemService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/preaudit/preAuditView");
    }
}

