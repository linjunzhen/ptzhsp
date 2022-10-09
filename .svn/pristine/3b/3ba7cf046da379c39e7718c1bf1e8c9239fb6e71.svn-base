/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.files.service.TranscodeService;
import net.evecom.platform.files.service.VideoFileService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 视频转码管理Controller
 * @author Bryce Zhang
 * @created 2016-10-27 下午6:25:03
 */
@Controller
@RequestMapping("/transcodeController")
public class TranscodeController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(TranscodeController.class);
    
    /**
     * 视频转码管理Service
     */
    @Resource
    private TranscodeService transcodeService;
    
    /**
     * 多媒体中心视频类管理Service
     */
    @Resource
    private VideoFileService videoFileService;
    
    /**
     * 日志管理Service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 描述 跳转至转码配置页
     * @author Bryce Zhang
     * @created 2016-10-27 下午6:32:33
     * @return
     */
    @RequestMapping(params = "confInfo")
    public ModelAndView confInfo(HttpServletRequest request, String mediaType){
        Map<String, Object> transcodeConf = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(mediaType)){
            transcodeConf = transcodeService.getConfByMedia(mediaType);
        }
        request.setAttribute("transcodeConf", transcodeConf);
        return new ModelAndView("files/transcode/"+mediaType+"ConfInfo");
    }
    
    /**
     * 描述 保存或更新转码配置
     * @author Bryce Zhang
     * @created 2016-10-28 上午9:30:58
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateConf")
    @ResponseBody
    public AjaxJson saveOrUpdateConf(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser loginUser = AppUtil.getLoginUser();
        String currentTime = DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        variables.put("UPDATE_USER", loginUser.getUserId());
        variables.put("UPDATE_TIME", currentTime);
        transcodeService.saveOrUpdateConf(variables);
        sysLogService.saveLog("修改了类型为["+variables.get("MEDIA_TYPE")+"]的视频转码配置", SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("视频转码配置修改成功~");
        return j;
    }
    
    /**
     * 描述 跳转至转码页面
     * @author Bryce Zhang
     * @created 2016-10-28 下午4:06:33
     * @param request
     * @param mediaType
     * @param videoId
     * @return
     */
    @RequestMapping(params = "transcodeInfo")
    public ModelAndView transcodeInfo(HttpServletRequest request, String mediaType, String videoId){
        Map<String, Object> transcodeConf = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(mediaType)){
            transcodeConf = transcodeService.getConfByMedia(mediaType);
        }
        request.setAttribute("transcodeConf", transcodeConf);
        Map<String, Object> videoFile = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(videoId)){
            videoFile = videoFileService.getByJdbc("T_FILES_VIDEO", 
                             new String[]{"VIDEO_ID"}, new Object[]{videoId});
        }
        request.setAttribute("videoFile", videoFile);
        return new ModelAndView("files/transcode/"+mediaType+"TranscodeInfo");
    }

}
