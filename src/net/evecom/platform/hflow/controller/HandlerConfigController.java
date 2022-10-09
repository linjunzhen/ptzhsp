/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.model.FlowNextHandler;

/**
 * 描述 审核人控制器
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月19日 下午12:59:12
 */
@Controller
@RequestMapping("/handlerConfigController")
public class HandlerConfigController extends BaseController {

    /**
     * 跳转到人员选择界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selectUser")
    public ModelAndView selectUser(HttpServletRequest request) {
        String flowSubmitInfoJson =  (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        String noAuth = "false";
        if(StringUtils.isNotEmpty(request.getParameter("noAuth"))){
            noAuth = request.getParameter("noAuth");
        }
        Map<String,Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson,Map.class);
        //获取传递过来的环节名称
        String flowNodeName = (String) flowSubmitInfo.get("EFLOW_FLOWNODENAME");
        //获取已选择的审核人
        Map<String,Object> nextSteps = (Map<String, Object>) flowSubmitInfo.get("EFLOW_NEXTSTEPS");
        if(nextSteps!=null){
            //获取目标节点的审核人配置
            if(nextSteps.get(flowNodeName)!=null){
                List<FlowNextHandler> handlers = JSON.parseArray(nextSteps.
                        get(flowNodeName).toString(), FlowNextHandler.class);
                if(handlers!=null&&handlers.size()>0){
                    StringBuffer userNames = new StringBuffer("");
                    StringBuffer userAccounts = new StringBuffer("");
                    for(FlowNextHandler handler:handlers){
                        userAccounts.append(handler.getNextStepAssignerCode()).append(",");
                        userNames.append(handler.getNextStepAssignerName()).append(",");
                    }
                    userAccounts.deleteCharAt(userAccounts.length()-1);
                    userNames.deleteCharAt(userNames.length()-1);
                    request.setAttribute("userAccounts",userAccounts.toString());
                    request.setAttribute("userNames",userNames.toString());
                }
            }
            
        }
        //request.setAttribute("allowCount", flowSubmitInfo.get("EFLOW_SELECTORVARS"));
        request.setAttribute("noAuth",noAuth);
        request.setAttribute("allowCount","15");
        return new ModelAndView("hflow/handlerconfig/SysUserSelector");
    }
}
