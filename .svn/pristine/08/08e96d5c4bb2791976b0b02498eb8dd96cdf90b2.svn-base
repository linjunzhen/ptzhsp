/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.AjaxJsonCode;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.wsbs.service.ProvBusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 描述  产业奖补Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/busController")
public class BusController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusController.class);
    /**
     * provBusService
     */
    @Resource
    private ProvBusService provBusService;

    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "workFlowReceive")
    @ResponseBody
    public AjaxJsonCode workFlowReceive(HttpServletRequest request) {
        log.info("接收省网总线数据开始");
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        AjaxJsonCode j=provBusService.parseBusService(variable);
        return j;
    }
    /**
     *
     * @param request 传入参数
     */
    @RequestMapping("/busFlowReceive")
    public void busFlowReceive(HttpServletRequest request, HttpServletResponse response) {
        log.info("接收省网总线数据开始");
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        AjaxJsonCode j=provBusService.parseBusService(variable);
        this.setJsonString(JSON.toJSONString(j), response);
    }
}

