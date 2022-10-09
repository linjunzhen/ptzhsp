/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
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


/**
 * 描述  不动产全流程-国有建设用地使用权首次登记控制类
 * @author Allin Lin
 * @created 2020年11月6日 下午3:02:53
 */
@Controller
@RequestMapping("/bdcGyjsscdjController")
public class BdcGyjsscdjController extends BaseController{
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcGyjsjfwzydjController.class);
    
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;

    /**
     * 跳转到不动产档案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "zdxxcxSelector")
    public ModelAndView zdxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));    
        return new ModelAndView("bsdt/applyform/bdcqlc/gyjsscdj/zdxxcxSelector");
    }
    
    /** 
     * 描述    宗地基本信息查询
     * @author Allin Lin
     * @created 2020年11月6日 下午4:38:46
     * @param request
     * @param response
     */
    @RequestMapping(params = "zdxxcxDatagrid")
    public void zdxxcxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String zddm = variables.get("zddm") == null ? "" : variables.get("zddm").toString();
        String zl = variables.get("zl") == null ? "" : variables.get("zl").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(zddm)
                ||StringUtils.isNotEmpty(zl)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "zxxxcxUrl");
            String jsonString = ajaxJson.getJsonString();        
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
}
