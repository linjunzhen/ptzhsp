/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcqZxdjService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.CommonOpinionService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.ReadTemplateService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 
 * 描述 不动产权注销登记Controller
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月5日 上午9:45:53
 */
@Controller
@RequestMapping("/bdcqZxdjController")
public class BdcqZxdjController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcqZxdjController.class);
    /**
     * 引入Service
     */
    @Resource
    private CommonOpinionService commonOpinionService;

    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;

    /**
     * 引入Service
     */
    @Resource
    private BdcqZxdjService bdcqZxdjService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;

    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;

    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 引入Service
     */
    @Resource
    private PrintAttachService printAttachService;
    /**
     * 引入Service
     */
    @Resource
    private ReadTemplateService readTemplateService;

    /**
     * 
     * 描述 跳转到不动产档案信息查询选择器
     * 
     * @author Roger Li
     * @created 2019年12月5日 上午9:46:45
     * @param request
     * @return MAV
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String negativeListCodes = request.getParameter("negativeListCodes");
        String negativeListNames = request.getParameter("negativeListNames");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if (StringUtils.isNotEmpty(negativeListCodes) && !negativeListCodes.equals("undefined")) {
            request.setAttribute("negativeListCodes", negativeListCodes);
            request.setAttribute("negativeListNames", negativeListNames);
        }
        return new ModelAndView("bsdt/applyform/bdcqlc/cqzxdj/bdcdaxxcxSelector");
    }

    /**
     * 
     * 描述 加载不动产档案信息
     * 
     * @author Roger Li
     * @created 2019年12月5日 上午9:47:26
     * @param request,response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcqzh = variables.get("bdcqzh") == null ? "" : variables.get("bdcqzh").toString();
        String zjhm = variables.get("zjhm") == null ? "" : variables.get("zjhm").toString();
        String fwbm = variables.get("fwbm") == null ? "" : variables.get("fwbm").toString();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(bdcdyh) || StringUtils.isNotEmpty(bdcqzh) || StringUtils.isNotEmpty(zjhm)
                || StringUtils.isNotEmpty(fwbm)) {
            AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdaxxcxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
}
