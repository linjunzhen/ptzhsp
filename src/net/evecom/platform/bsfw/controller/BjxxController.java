/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.service.FileAttachService;
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
 * 描述 补件信息Controller
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/bjxxController")
public class BjxxController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BjxxController.class);
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bjxxFiles")
    public ModelAndView flowFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        String isFied = request.getParameter("isFiled");
        Map<String,Object> bjxx = bjxxService.getByJdbc("T_WSBS_BJXX",
                new String[]{"EXE_ID"},new Object[]{exeId});
        List bjclList = null ;
        if(bjxx!=null){
            Map<String, Object> flowExe = null;
            String exeTable = "JBPM6_EXECUTION";
            if("1".equals(isFied)) {
                exeTable = "JBPM6_EXECUTION_EVEHIS";
            }
            flowExe = executionService.getByJdbc(exeTable, new String[] { "EXE_ID"}, new Object[] {exeId});
            bjclList = JSON.parseArray((String)bjxx.get("BJCLLB"), Map.class);
            for (int i = 0; i < bjclList.size(); i++) {
                Map<String, Object> e = (Map<String, Object>) bjclList.get(i);
                List<Map<String, Object>> filesMap = fileAttachService.findByList((String)flowExe.get("BUS_TABLENAME"),
                                (String)flowExe.get("BUS_RECORDID"),(String) e.get("MATER_CODE"));
                e.put("filesMap", filesMap);
            }
        }
        if(bjclList!=null){
            request.setAttribute("bjclList",bjclList);
        }
        return new ModelAndView("hflow/bjxx/bjxxUploadFileAppMaters");
    }
}
