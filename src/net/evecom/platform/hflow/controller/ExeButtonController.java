/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.FlowEventService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 描述  流程实例Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/exeButtonController")
public class ExeButtonController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExecutionController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * 引入Service
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 重新生成附件
     *
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSocialFile")
    @ResponseBody
    public AjaxJson updateSocialFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] exeIdArr=selectColNames.split(",");
        for(String exeId:exeIdArr){
            if(StringUtils.isNotEmpty(exeId)){
                flowEventService.createSocialForm(exeId);
            }
        }
        j.setMsg("生成附件成功");
        return j;
    }

    /**
     * 获取公司名
     * @param request
     * @return
     */
    @RequestMapping(params = "findCompanyName")
    @ResponseBody
    public List<Map<String,Object>>  findCompanyName(HttpServletRequest request){
        List<Map<String,Object>> companyList=null;
        SqlFilter filter = new SqlFilter(request);
        String companyName=request.getParameter("name");
        if(StringUtils.isNotEmpty(companyName)){
            filter.addFilter("Q_E.SQJG_NAME_LIKE", companyName);
            companyList=exeDataService.findCompanyName(filter);
        }
        return companyList;
    }
}