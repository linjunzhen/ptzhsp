/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.files.service.AttachFileService;
import net.evecom.platform.files.service.AudioFileService;
import net.evecom.platform.files.service.FileTypeService;
import net.evecom.platform.files.service.ImageFileService;
import net.evecom.platform.files.service.SwfFileService;
import net.evecom.platform.files.service.VideoFileService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 多媒体类别管理Controller
 * @author Bryce Zhang
 * @created 2016-11-28 下午3:39:36
 */
@Controller
@RequestMapping("/fileTypeController")
public class FileTypeController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FileTypeController.class);
    
    /**
     * 多媒体类别管理Service
     */
    @Resource
    private FileTypeService fileTypeService;
    
    /**
     * 日志管理Service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 描述 多媒体类别树数据获取
     * @author Bryce Zhang
     * @created 2016-11-28 下午3:53:25
     * @param request
     * @param response
     */
    @RequestMapping("/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "多媒体类别树");
        root.put("open", true);
        root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        List<Map<String, Object>> toplist = fileTypeService.findByParentId("0");
        for(Map<String, Object> top: toplist){
            top.put("id", top.get("TYPE_ID"));
            top.put("name", top.get("TYPE_NAME"));
            top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildren(top, (String)top.get("TYPE_ID"));
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述 获取孩子节点
     * @author Bryce Zhang
     * @created 2016-11-28 下午4:00:20
     * @param parentType
     * @param parentId
     */
    private void getChildren(Map<String, Object> parentType, String parentId){
        List<Map<String, Object>> children = fileTypeService.findByParentId(parentId);
        if(children != null && children.size() > 0){
            parentType.put("children", children);
            for(Map<String, Object> child: children){
                child.put("id", child.get("TYPE_ID"));
                child.put("name", child.get("TYPE_NAME"));
                child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, (String)child.get("TYPE_ID"));
            }
        }
    }
    
    /**  
     * 描述 跳转至多媒体类别信息框
     * @author Bryce Zhang
     * @created 2016-11-28 下午4:48:39
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String, Object> fileType = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined") && !entityId.equals("0")){
            fileType = fileTypeService.getByJdbc("T_FILES_TYPE", new String[]{"TYPE_ID"}, new Object[]{entityId});
        }
        fileType.put("PARENT_ID", parentId);
        fileType.put("PARENT_NAME", parentName);
        request.setAttribute("fileType", fileType);
        return new ModelAndView("files/fileType/FileTypeInfo");
    }
    
    /**
     * 描述 保存或更新
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:16:18
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateTree")
    @ResponseBody
    public AjaxJson saveOrUpdateTree(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("TYPE_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        SysUser loginUser = AppUtil.getLoginUser();
        String currentTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_USER", loginUser.getUserId());
            treeData.put("CREATE_TIME", currentTime);
        }else{
            treeData.put("UPDATE_USER", loginUser.getUserId());
            treeData.put("UPDATE_TIME", currentTime);
        }
        String recordId = fileTypeService.saveOrUpdateTreeData(parentId, treeData, "T_FILES_TYPE", null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的多媒体类别记录", SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的多媒体类别记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("操作成功~");
        return j;
    }
    
    /**
     * 描述 删除
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:45:28
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        if(StringUtils.isNotEmpty(selectColNames)){
            int fileNum = fileTypeService.countFileNumByType(selectColNames);
            if(fileNum == 0){
                fileTypeService.removeByTypeId(selectColNames);
                sysLogService.saveLog("删除了ID为["+selectColNames+"]的多媒体类别记录", SysLogService.OPERATE_TYPE_DEL);
            }else{
                j.setSuccess(false);
                j.setMsg("操作失败，请先删除该类别下的多媒体资源，再进行类别删除操作~");
                return j;
            }
        }
        j.setMsg("操作成功~");
        return j;
    }
    
    /**
     * 描述 跳转至多媒体类别选择器
     * @author Bryce Zhang
     * @created 2016-11-28 下午7:25:24
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request){
        String needCheckIds = request.getParameter("needCheckIds");
        request.setAttribute("needCheckIds", needCheckIds);
        return new ModelAndView("files/fileType/FileTypeSelector");
    }
    
    /**
     * 描述 跳转至文件上传对话框
     * @author Bryce Zhang
     * @created 2016-11-30 下午4:37:44
     * @param request
     * @return
     */
    @RequestMapping(params = "openUploadDialog")
    public ModelAndView openUploadDialog(HttpServletRequest request){
        String attachType = request.getParameter("attachType");
        String materType = request.getParameter("materType");
        String busTableName = request.getParameter("busTableName");
        String attachKey = request.getParameter("attachKey");
        String SQFS = request.getParameter("SQFS");
        String SFSQ = request.getParameter("SFSQ");
        String SFHZD = request.getParameter("SFHZD");
        if(StringUtils.isNotEmpty(attachType)){
            String acceptFileType = null;
            long fileSizeLimit;
            String formatFileSizeLimit = null;
            //附件
            if(FileTypeService.ATTACHTYPE_ATTACH.equals(attachType)){
                acceptFileType = AttachFileService.ACCEPTFILETYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
            //音频
            }else if(FileTypeService.ATTACHTYPE_AUDIO.equals(attachType)){
                acceptFileType = AudioFileService.ACCEPTFILETYPES;
                fileSizeLimit = AudioFileService.FILESIZELIMIT;
                formatFileSizeLimit = AudioFileService.FORMAT_FILESIZELIMIT;
            //图片
            }else if(FileTypeService.ATTACHTYPE_IMAGE.equals(attachType)){
                acceptFileType = ImageFileService.ACCEPTFILETYPES;
                fileSizeLimit = ImageFileService.FILESIZELIMIT;
                formatFileSizeLimit = ImageFileService.FORMAT_FILESIZELIMIT;
            //flash
            }else if(FileTypeService.ATTACHTYPE_FLASH.equals(attachType)){
                acceptFileType = SwfFileService.ACCEPTFILETYPES;
                fileSizeLimit = SwfFileService.FILESIZELIMIT;
                formatFileSizeLimit = SwfFileService.FORMAT_FILESIZELIMIT;
            //视频
            }else if(FileTypeService.ATTACHTYPE_VIDEO.equals(attachType)){
                acceptFileType = VideoFileService.ACCEPTFILETYPES;
                fileSizeLimit = VideoFileService.FILESIZELIMIT;
                formatFileSizeLimit = VideoFileService.FORMAT_FILESIZELIMIT;
            }else{
                acceptFileType = AttachFileService.ACCEPTFILETYPES+","+ImageFileService.ACCEPTFILETYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
                if(StringUtils.isNotEmpty(materType)){
                    attachType = materType;                    
                } else {
                    attachType = "attach";
                }
            }
            request.setAttribute("attachType", attachType);
            request.setAttribute("attachKey", attachKey);
            request.setAttribute("busTableName", busTableName);
            request.setAttribute("acceptFileType", acceptFileType);
            request.setAttribute("fileSizeLimit", fileSizeLimit);
            request.setAttribute("formatFileSizeLimit", formatFileSizeLimit);
            request.setAttribute("SQFS", SQFS);
            request.setAttribute("SFSQ", SFSQ);
            request.setAttribute("SFHZD", SFHZD);
            return new ModelAndView("files/fileType/UploadDialog");
        }else{
            return null;
        }
    }
    
    /**
     * 描述 跳转至多媒体资源选择器页面
     * @author Bryce Zhang
     * @created 2016-11-30 下午5:31:45
     * @param request
     * @return
     */
    @RequestMapping(params = "fileSelector")
    public ModelAndView fileSelector(HttpServletRequest request){
        String allowCount= request.getParameter("allowCount");
        String needSelectRowIds = request.getParameter("needSelectRowIds");
        String attachType = request.getParameter("attachType");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("needSelectRowIds", needSelectRowIds);
        request.setAttribute("attachType", attachType);
        return new ModelAndView("files/fileType/FileSelector");
    }
    /**
     * 描述 跳转至文件上传对话框
     * @author Bryce Zhang
     * @created 2016-11-30 下午4:37:44
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(params = "openWebStieUploadDialog")
    public ModelAndView openWebStieUploadDialog(HttpServletRequest request) throws UnsupportedEncodingException{
        String attachType = request.getParameter("attachType");
        //String materType = request.getParameter("materType");
        String uploadUserId = request.getParameter("uploadUserId");
        String uploadUserName = request.getParameter("uploadUserName");
        if(uploadUserName!=null&&!"".equals(uploadUserName)) {
            uploadUserName = URLDecoder.decode(uploadUserName,"UTF-8"); 
        }
        String isAdmin = request.getParameter("isAdmin");
        String busTableName = request.getParameter("busTableName");
        String attachKey = request.getParameter("attachKey");
        String SQFS = request.getParameter("SQFS");
        String SFSQ = request.getParameter("SFSQ");
        String SFHZD = request.getParameter("SFHZD");
        if(StringUtils.isNotEmpty(attachType)){
            String acceptFileType = null;
            long fileSizeLimit;
            String formatFileSizeLimit = null;
            //附件
            if(FileTypeService.ATTACHTYPE_ATTACH.equals(attachType)){
                acceptFileType = AttachFileService.ACCEPTFILETYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
            //音频
            }else if(FileTypeService.ATTACHTYPE_AUDIO.equals(attachType)){
                acceptFileType = AudioFileService.ACCEPTFILETYPES;
                fileSizeLimit = AudioFileService.FILESIZELIMIT;
                formatFileSizeLimit = AudioFileService.FORMAT_FILESIZELIMIT;
            //图片
            }else if(FileTypeService.ATTACHTYPE_IMAGE.equals(attachType)){
                acceptFileType = ImageFileService.ACCEPTFILETYPES;
                fileSizeLimit = ImageFileService.FILESIZELIMIT;
                formatFileSizeLimit = ImageFileService.FORMAT_FILESIZELIMIT;
            //flash
            }else if(FileTypeService.ATTACHTYPE_FLASH.equals(attachType)){
                acceptFileType = SwfFileService.ACCEPTFILETYPES;
                fileSizeLimit = SwfFileService.FILESIZELIMIT;
                formatFileSizeLimit = SwfFileService.FORMAT_FILESIZELIMIT;
            //视频
            }else if(FileTypeService.ATTACHTYPE_VIDEO.equals(attachType)){
                acceptFileType = VideoFileService.ACCEPTFILETYPES;
                fileSizeLimit = VideoFileService.FILESIZELIMIT;
                formatFileSizeLimit = VideoFileService.FORMAT_FILESIZELIMIT;
            //excel附件
            }else if(FileTypeService.ATTACHTYPE_EXCEL.equals(attachType)){
                acceptFileType = AttachFileService.ACCEPEXCELTYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
            }else{
                acceptFileType = AttachFileService.ACCEPTFILETYPES+","+ImageFileService.ACCEPTFILETYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
                attachType = "attach";
            }
            request.setAttribute("attachType", attachType);
            request.setAttribute("attachKey", attachKey);
            request.setAttribute("busTableName", busTableName);
            request.setAttribute("acceptFileType", acceptFileType);
            request.setAttribute("fileSizeLimit", fileSizeLimit);
            request.setAttribute("formatFileSizeLimit", formatFileSizeLimit);
            request.setAttribute("uploadUserId", uploadUserId);
            request.setAttribute("uploadUserName", uploadUserName);
            request.setAttribute("isAdmin", isAdmin);
            request.setAttribute("SQFS", SQFS);
            request.setAttribute("SFSQ", SFSQ);
            request.setAttribute("SFHZD", SFHZD);
            return new ModelAndView("files/fileType/WebSiteUploadDialog");
        }else{
            return null;
        }
    }

    /**
     * 
     * 描述：跳转至多文件上传对话框
     * 
     * @author Rider Chen
     * @created 2019年9月18日 下午4:07:57
     * @param request
     * @return
     */

    @RequestMapping(params = "openMultifileUploadDialog")
    public ModelAndView openMultifileUploadDialog(HttpServletRequest request) {
        String attachType = request.getParameter("attachType");
        String materType = request.getParameter("materType");
        String busTableName = request.getParameter("busTableName");
        String attachKey = request.getParameter("attachKey");
        String SQFS = request.getParameter("SQFS");
        String SFSQ = request.getParameter("SFSQ");
        String SFHZD = request.getParameter("SFHZD");
        if (StringUtils.isNotEmpty(attachType)) {
            String acceptFileType = null;
            long fileSizeLimit;
            String formatFileSizeLimit = null;
            // 附件
            if (FileTypeService.ATTACHTYPE_ATTACH.equals(attachType)) {
                acceptFileType = AttachFileService.ACCEPTFILETYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
                // 音频
            } else if (FileTypeService.ATTACHTYPE_AUDIO.equals(attachType)) {
                acceptFileType = AudioFileService.ACCEPTFILETYPES;
                fileSizeLimit = AudioFileService.FILESIZELIMIT;
                formatFileSizeLimit = AudioFileService.FORMAT_FILESIZELIMIT;
                // 图片
            } else if (FileTypeService.ATTACHTYPE_IMAGE.equals(attachType)) {
                acceptFileType = ImageFileService.ACCEPTFILETYPES;
                fileSizeLimit = ImageFileService.FILESIZELIMIT;
                formatFileSizeLimit = ImageFileService.FORMAT_FILESIZELIMIT;
                // flash
            } else if (FileTypeService.ATTACHTYPE_FLASH.equals(attachType)) {
                acceptFileType = SwfFileService.ACCEPTFILETYPES;
                fileSizeLimit = SwfFileService.FILESIZELIMIT;
                formatFileSizeLimit = SwfFileService.FORMAT_FILESIZELIMIT;
                // 视频
            } else if (FileTypeService.ATTACHTYPE_VIDEO.equals(attachType)) {
                acceptFileType = VideoFileService.ACCEPTFILETYPES;
                fileSizeLimit = VideoFileService.FILESIZELIMIT;
                formatFileSizeLimit = VideoFileService.FORMAT_FILESIZELIMIT;
            } else {
                acceptFileType = AttachFileService.ACCEPTFILETYPES + "," + ImageFileService.ACCEPTFILETYPES;
                fileSizeLimit = AttachFileService.FILESIZELIMIT;
                formatFileSizeLimit = AttachFileService.FORMAT_FILESIZELIMIT;
                if (StringUtils.isNotEmpty(materType)) {
                    attachType = materType;
                } else {
                    attachType = "attach";
                }
            }
            request.setAttribute("attachType", attachType);
            request.setAttribute("attachKey", attachKey);
            request.setAttribute("busTableName", busTableName);
            request.setAttribute("acceptFileType", acceptFileType);
            request.setAttribute("fileSizeLimit", fileSizeLimit);
            request.setAttribute("formatFileSizeLimit", formatFileSizeLimit);
            request.setAttribute("SQFS", SQFS);
            request.setAttribute("SFSQ", SFSQ);
            request.setAttribute("SFHZD", SFHZD);
            return new ModelAndView("files/fileType/MultifileUploadDialog");
        } else {
            return null;
        }
    }
}
