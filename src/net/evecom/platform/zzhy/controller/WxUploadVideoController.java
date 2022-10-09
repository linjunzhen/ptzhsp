/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import net.evecom.core.util.AjaxJsonCode;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExeDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;

/**
 * 描述 微信上传视频Controller
 *
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/wxUploadVideoController")
public class WxUploadVideoController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WxUploadVideoController.class);
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;

    /**
     *视频上传
     * @param file
     * @param token
     * @return
     */
    @RequestMapping(params = "fileUpload")
    @ResponseBody
    public AjaxJsonCode fileUpload (@RequestParam("file") CommonsMultipartFile file,
                                    @RequestParam("TOKEN") String token)  {
        log.info("面签视频的token="+token);
        return exeDataService.handleSignVideo(file,token);
    }
}