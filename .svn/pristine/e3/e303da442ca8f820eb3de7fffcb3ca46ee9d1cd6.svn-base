/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tyjk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.sync.controller.SwbDataController;
import net.evecom.platform.tyjk.service.FlowWebService;
/**
 * 
 * 描述：
 * @author Rider Chen
 * @created 2018年10月16日 上午10:01:19
 */
@Controller
@RequestMapping("/flowWebController")
public class FlowWebController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SwbDataController.class);

    /**
     * flowWebService
     */
    @Resource
    private FlowWebService flowWebService;
    /**
     * 
     * 描述： 启动流程
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/flowStart")
    public void flowStart(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.flowStart(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述： 查询流程定义节点
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/queryFlowConfig")
    public void queryFlowConfig(HttpServletRequest request, HttpServletResponse response) { 
        String exeId = request.getParameter("exeId");
        String json  =  flowWebService.queryFlowConfig(exeId);
        this.setJsonString(json, response);
    }
    
    /**
     *
     * 描述： 网上用户启动流程
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/onlineFlowStart")
    public void onLineFlowStart(HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.onlineFlowStart(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    /**
     *
     * 描述： 根据闽政通账号获取用户账号名
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/getAccountByMztInfo")
    public void getAccountByMztInfo(HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("mztInfoJson");
        String json  =  flowWebService.getAccountByMztInfo(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述： 流程环节执行
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/flowExecute")
    public void flowExecute(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.flowExecute(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述： 获取事项材料编码
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/getItemApplyMaters")
    public void getItemApplyMaters(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String itemCode = request.getParameter("itemCode");
        String json  =  flowWebService.getItemApplyMaters(uuid, itemCode);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述：退件
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/flowNotAccept")
    public void flowNotAccept(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.notAccept(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述： 挂起
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/flowHandUp")
    public void flowHandUp(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.handUp(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述： 重启
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/flowReStart")
    public void flowReStart(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.reStart(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述： 重启
     * @author Rider Chen
     * @created 2018年10月16日 上午10:11:17
     * @param request
     * @param response
     */
    @RequestMapping("/saveFile")
    public void saveFile(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.saveFile(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述：获取平台综合审批系统叫号信息
     * @author Rider Chen
     * @created 2019年1月21日 上午9:44:48
     * @param request
     * @param response
     */
    @RequestMapping("/getCallNumber")
    public void getCallNumber(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.getCallNumber(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述：获取平台综合审批系统取号排队信息
     * @author Rider Chen
     * @created 2019年1月21日 上午9:44:48
     * @param request
     * @param response
     */
    @RequestMapping("/getQueryLine")
    public void getQueryLine(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.getQueryLine(uuid, flowInfoJson);
        this.setJsonString(json, response);
    } 
    /**
     * 
     * 描述：获取平台综合审批系统办件结果信息
     * @author Rider Chen
     * @created 2019年1月21日 上午9:44:48
     * @param request
     * @param response
     */
    @RequestMapping("/getQueryResult")
    public void getQueryResult(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.getQueryResult(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述： 平潭流程退回补件接口
     * @author Rider Chen
     * @created 2020年12月7日 下午4:59:11
     * @param request
     * @param response
     */
    @RequestMapping("/exeBjFlow")
    public void exeBjFlow(HttpServletRequest request, HttpServletResponse response) { 
        String uuid = request.getParameter("uuid");
        String flowInfoJson = request.getParameter("flowInfoJson");
        String json  =  flowWebService.exeBjFlow(uuid, flowInfoJson);
        this.setJsonString(json, response);
    }
}
