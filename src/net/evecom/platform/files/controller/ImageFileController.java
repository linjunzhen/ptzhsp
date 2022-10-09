/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.files.service.ImageFileService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 多媒体中心图片类管理Controller
 * @author Bryce Zhang
 * @created 2016-10-19 下午5:14:05
 */
@Controller
@RequestMapping("/imageFileController")
public class ImageFileController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ImageFileController.class);
    
    /**
     * 多媒体中心图片类管理Service
     */
    @Resource
    private ImageFileService imageFileService;
    
    /**
     * 日志管理Service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 描述 跳转至列表页
     * @author Bryce Zhang
     * @created 2016-10-20 上午9:32:35
     * @return
     */
    @RequestMapping(params = "ImageFileView")
    public ModelAndView imageFileView(HttpServletRequest request){
        return new ModelAndView("files/imageFile/ImageFileView");
    }
    
    /**
     * 描述 datagrid数据获取
     * @author Bryce Zhang
     * @created 2016-10-20 上午10:19:29
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = imageFileService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 跳转至配置框
     * @author Bryce Zhang
     * @created 2016-10-20 上午11:00:39
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String typeId = request.getParameter("TYPE_ID");
        String typeName = request.getParameter("TYPE_NAME");
        Map<String, Object> imageFile = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            imageFile = imageFileService.getByJdbc("T_FILES_IMAGE", new String[]{"IMAGE_ID"}, new Object[]{entityId});
            typeId = (String)imageFile.get("TYPE_ID");
            Map<String, Object> fileType = imageFileService.getByJdbc("T_FILES_TYPE", 
                                    new String[]{"TYPE_ID"}, new Object[]{typeId});
            imageFile.put("TYPE_ID", typeId);
            imageFile.put("TYPE_NAME", (String)fileType.get("TYPE_NAME"));
        }else{
            imageFile.put("TYPE_ID", typeId);
            imageFile.put("TYPE_NAME", typeName);
        }
        request.setAttribute("imageFile", imageFile);
        //文件上传参数
        request.setAttribute("acceptFileType", ImageFileService.ACCEPTFILETYPES);
        request.setAttribute("fileSizeLimit", ImageFileService.FILESIZELIMIT);
        request.setAttribute("formatFileSizeLimit", ImageFileService.FORMAT_FILESIZELIMIT);
        return new ModelAndView("files/imageFile/ImageFileInfo");
    }
    
    /**
     * 描述 保存或更新
     * @author Bryce Zhang
     * @created 2016-10-20 上午11:05:12
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("IMAGE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            SysUser loginUser = AppUtil.getLoginUser();
            variables.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("UPLOADER_ID", loginUser.getUserId());
            variables.put("UPLOADER_NAME", loginUser.getFullname());
        }
        String recordId = imageFileService.saveOrUpdate(variables, "T_FILES_IMAGE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为[" + entityId + "]的媒体中心图片类记录", SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("修改成功~");
        }else{
            sysLogService.saveLog("新增了ID为[" + recordId + "]的媒体中心图片类记录", SysLogService.OPERATE_TYPE_ADD);
            j.setMsg("保存成功~");
        }
        return j;
    }
    
    /**
     * 描述 删除入口
     * @author Bryce Zhang
     * @created 2016-10-20 下午1:08:17
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        imageFileService.remove("T_FILES_IMAGE", "IMAGE_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的媒体中心图片类记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功~");
        return j;
    }
    
    /**
     * 描述 跳转至移动页面
     * @author Bryce Zhang
     * @created 2016-11-28 下午7:10:31
     * @param request
     * @return
     */
    @RequestMapping(params = "move")
    public ModelAndView move(HttpServletRequest request){
        String selectColNames = request.getParameter("selectColNames");
        request.setAttribute("selectColNames", selectColNames);
        return new ModelAndView("files/imageFile/ImageFileMove");
    }
    
    /**
     * 描述 保存移动结果
     * @author Bryce Zhang
     * @created 2016-11-28 下午8:31:23
     * @param request
     * @return
     */
    @RequestMapping(params = "saveMove")
    @ResponseBody
    public AjaxJson saveMove(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        imageFileService.saveMove(variables);
        sysLogService.saveLog("修改了图片类文件(ID为["+variables.get("sourceFileIds")+"])的多媒体类型，目标多媒体资源类型["+
                               variables.get("targetTypeId")+"]", SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("操作成功~");
        return j;
    }
    
    /**
     * 描述 根据主键id集合，查询列表
     * @author Bryce Zhang
     * @created 2016-12-1 上午10:20:52
     * @param request
     * @param response
     */
    @RequestMapping(params = "selectedDatagrid")
    public void selectedDatagrid(HttpServletRequest request, HttpServletResponse response){
        String needSelectRowIds = request.getParameter("needSelectRowIds");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(needSelectRowIds)){
            list = imageFileService.findByIds(needSelectRowIds);
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }


    /**
     * 描述 图片窗口显示
     *
     * @param request the request
     * @return the model and view
     * @author Harley Hong
     * @created 2017 /03/07 16:54:40 Image win show model and view.
     */
    @RequestMapping(params = "imageWinShow")
    public ModelAndView imageWinShow(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("src",request.getParameter("src"));
        map.put("width",request.getParameter("width"));
        map.put("height",request.getParameter("height"));
        request.setAttribute("map",map);
        return new ModelAndView("files/imageFile/imageWinShow");
    }

}
