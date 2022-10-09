/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月16日 下午2:48:12
 */
@Controller
@RequestMapping("/promiseInfoController")
public class PromiseInfoController extends BaseController {
    
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/info")
    public ModelAndView info(HttpServletRequest request) {
        String itemCode = request.getParameter("itemCode");
        String exeId = request.getParameter("exeId");
        Map<String,Object> promiseInfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(exeId)&&!exeId.equals("null")){
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            promiseInfo.put("SXMC", flowExe.get("ITEM_NAME"));
            String createTime = (String) flowExe.get("CREATE_TIME");
            Date date = DateTimeUtil.getDateOfStr(createTime,"yyyy-MM-dd");
            promiseInfo.put("CNSJ",DateTimeUtil.getStrOfDate(date, "yyyy-MM-dd"));
        }else{
            Map<String,Object> item = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"},new Object[]{itemCode});
            promiseInfo.put("SXMC", item.get("ITEM_NAME"));
            promiseInfo.put("CNSJ", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        request.setAttribute("promiseInfo", promiseInfo);
        return new ModelAndView("bsdt/maters/promiseinfo");
    }
}
