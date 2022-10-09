/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQslyshbsyService;
import net.evecom.platform.hflow.controller.ExeDataController;
import net.evecom.platform.hflow.service.ExeDataService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 描述 不动产权属来源审核业务Controller
 * @author Allin Lin
 * @created 2019年3月5日 上午11:32:19
 */
@Controller
@RequestMapping("/bdcQslyshbsyController")
public class BdcQslyshbsyController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcDyqscdjController.class);
    
    /**
     * 凯特远程文件签章页面
     */
    private static String ktSignRemoteFileView="bsdt/applyform/bdcqlc/qslyshbsy/ktSignRemoteFileView";
    
    /**
     * bdcQslyshbsyService
     */
    @Resource
    private BdcQslyshbsyService bdcQslyshbsyService;
    
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 描述    手动盖章上传页面
     * @author Allin Lin
     * @created 2020年10月29日 下午6:28:15
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/ktStampUploadView")
    public ModelAndView ktStampUploadView(HttpServletRequest request, HttpServletResponse response) {
        String busTableName = request.getParameter("busTableName");
        String uploadUserId = request.getParameter("uploadUserId");
        String uploadUserName = request.getParameter("uploadUserName");
        String attachKey = request.getParameter("attachKey");
        String signFileType=request.getParameter("signFileType");
        String exeId=request.getParameter("exeId");
        request.setAttribute("attachKey",attachKey);
        request.setAttribute("busTableName",busTableName);
        request.setAttribute("uploadUserId",uploadUserId);
        request.setAttribute("uploadUserName",uploadUserName);
        String fileId = "";
        request.setAttribute("signFileType",signFileType);       
        String modelView=ktSignRemoteFileView; //凯特签章页面   
        if(StringUtils.isNotEmpty(exeId)){
            //获取指定申报号对应的签署文件ID
            fileId = StringUtil.getString(exeDataService.getBuscordMap(exeId).get("SIGN_FILEID"));
        }
        request.setAttribute("fileId",fileId);
        return new ModelAndView(modelView);
    }

    /**
     * 描述    当前环节是否已签章
     * @author Allin Lin
     * @created 2020年10月30日 上午9:41:10
     * @param request
     * @return
     */
    @RequestMapping("/isFinishSign")
    @ResponseBody
    public AjaxJson isFinishSign(HttpServletRequest request ){
        String exeId=request.getParameter("exeId");
        String curNodeName=request.getParameter("curNodeName");
        AjaxJson ajaxJson=bdcQslyshbsyService.isFinishSign(exeId,curNodeName);      
        return ajaxJson;
    }
    
    
    
    
    /**
     * 描述    是否允许签章
     * @author Allin Lin
     * @created 2020年10月30日 上午9:41:10
     * @param request
     * @return
     */
    @RequestMapping("/isPermitSign")
    @ResponseBody
    public AjaxJson isPermitSign(HttpServletRequest request ){
        String exeId=request.getParameter("exeId");
        AjaxJson ajaxJson=bdcQslyshbsyService.isPermitSign(exeId);      
        return ajaxJson;
    }
    
    
    
    
    /**
     * 签章是否为同一版本号
     * @return
     */
    @RequestMapping("/isSameSignVersion")
    @ResponseBody
    public AjaxJson isSameSignVersion(HttpServletRequest request ){
        String exeId=request.getParameter("exeId");
        String signVersion=request.getParameter("signVersion");
        AjaxJson ajaxJson=bdcQslyshbsyService.isSameSignVersion(exeId,signVersion);      
        return ajaxJson;
    }
    
    /**
     * 更改是否允许其他人进行签章状态
     * @return
     */
    @RequestMapping("/changeSignStatus")
    @ResponseBody
    public AjaxJson changeSignStatus(HttpServletRequest request ){
        String exeId=request.getParameter("exeId");
        AjaxJson ajaxJson=bdcQslyshbsyService.changeSignStatus(exeId);
        return ajaxJson;
    }
    
    
    /**
     * 描述    保存或更新签署文件
     * @author Allin Lin
     * @created 2020年10月29日 下午5:36:15
     * @param request
     * @return
     */
    @RequestMapping("/saveOrUpdateSignFile")
    @ResponseBody
    public AjaxJson saveOrUpdateSignFile(HttpServletRequest request ){
        String exeId=request.getParameter("exeId");
        String fileId=request.getParameter("fileId");
        String fileType = request.getParameter("fileType");
        AjaxJson ajaxJson=bdcQslyshbsyService.saveOrUpdateSignFile(exeId,fileId,fileType);
        return ajaxJson;
    }
}
