/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysMsgService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述：
 * @author Water Guo
 * @created 2017-3-16 上午10:57:29
 */
@Controller
@RequestMapping("/sysMsgController")
public class SysMsgController extends BaseController{
    /**
     * sysMsgService
     */
    @Resource
    private SysMsgService sysMsgService;
    
    /**
     * 
     * 描述：前台列表显示
     * @author Water Guo
     * @created 2017-3-16 上午10:59:44
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        return new ModelAndView("system/sysMsg/fontMsgView");
    }
    /**
     * 
     * 描述：短信列表展示
     * @author Water Guo
     * @created 2017-3-16 下午1:43:01
     * @param request
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("SEND_TIME","DESC");
        filter.addSorted("CREATE_TIME","DESC");
        List<Map<String, Object>> list = sysMsgService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}
