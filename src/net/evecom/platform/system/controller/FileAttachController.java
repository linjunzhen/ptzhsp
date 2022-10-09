/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.projectflow.service.ProjectFlowService;
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
import com.alibaba.fastjson.JSONArray;

/**
 * 描述  系统附件Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/fileAttachController")
public class FileAttachController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FileAttachController.class);
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectFlowService projectFlowService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        if(StringUtils.isNotEmpty(selectColNames)&&!"".equals(selectColNames)){
            fileAttachService.removeFile(selectColNames.split(","));
            j.setMsg("删除成功");
        }
        return j;
    }
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "cmsMultiDel")
    @ResponseBody
    public AjaxJson cmsMultiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] selectColNames = request.getParameterValues("selectColNames");
        if(null!=selectColNames){
            fileAttachService.removeFile(selectColNames);
            j.setMsg("删除成功");
        }
        return j;
    }
    @RequestMapping("/delete")
    public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String ids = request.getParameter("ids");
        String attachKey = request.getParameter("attachKey");
        fileAttachService.removeFile(ids.split(","));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", true);
        map.put("attachKey", attachKey);
        map.put("fileId", ids);
        String json = JSON.toJSONString(map);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"},new Object[]{entityId});
            request.setAttribute("fileAttach", fileAttach);
        }
        return new ModelAndView("system/fileAttach/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("FILE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = fileAttachService.saveOrUpdate(variables, "T_MSJW_SYSTEM_FILEATTACH", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 系统附件记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 系统附件记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述 获取附件信息
     * @author Flex Hu
     * @created 2014年9月27日 下午4:01:43
     * @param request
     * @param response
     */
    @RequestMapping(params = "get")
    @ResponseBody
    public void get(HttpServletRequest request, HttpServletResponse response){
        String fileId = request.getParameter("fileId");
        if(StringUtils.isNotEmpty(fileId)){
            Map<String,Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"},new Object[]{fileId});
            String json = JSON.toJSONString(fileAttach);
            this.setJsonString(json, response);
        }
    }

    /**
     * 跳转到信息页面()
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "getHisMatInfo")
    @ResponseBody
    public AjaxJson getHisMatInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String sqrsfz = request.getParameter("sqrsfz");
        String sqjgCode = request.getParameter("sqjgCode");
//        request.setAttribute("sqrsfz", sqrsfz);
//        request.setAttribute("sqjgCode", sqjgCode);
        if (StringUtils.isEmpty(sqrsfz)&&StringUtils.isEmpty(sqjgCode)) {
            //无查询信息
            j.setMsg("请添加身份证号或组织机构代码！");
        }else {
            Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
            List<Map<String,Object>> returnList=fileAttachService.findHisDatagrid(variables);
            String str = JSON.toJSONString(returnList);
            j.setJsonString(str);
            j.setMsg("成功");
        }
        return j;
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "hisMatInfo")
    public ModelAndView hisMatInfo(HttpServletRequest request) {
        String sqrsfz = request.getParameter("sqrsfz");
        String sqjgCode = request.getParameter("sqjgCode");
        String attachKey=request.getParameter("attachKey");
        String busTableName=request.getParameter("busTableName");
        request.setAttribute("sqrsfz", sqrsfz);
        request.setAttribute("sqjgCode", sqjgCode);
        request.setAttribute("attachKey",attachKey);
        request.setAttribute("busTableName",busTableName);
        return new ModelAndView("bsdt/applyform/hisMatInfoList");
    }
    /**
     * easyui AJAX请求数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "hisMatDatagrid")
    public void hisMatDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> returnList=fileAttachService.findDatagrid(variables);
        this.setListToJsonString(returnList.size(), returnList,
                null, JsonUtil.EXCLUDE, response);
    }
//    /**
//     * 
//     * 描述 接收
//     * @author Kester Chen
//     * @created 2018-1-15 上午10:47:44
//     */
//    @RequestMapping(params = "selectHisMatByIds")
//    @ResponseBody
//    public AjaxJson selectHisMatByIds(HttpServletRequest request, HttpServletResponse response){
//        AjaxJson j = new AjaxJson();
//        String FILE_ID = request.getParameter("FILE_ID");
//        fileAttachService.useHisMatAgainByIds(FILE_ID);
//        j.setMsg("接收成功");
//        return j;
//    }
    /**
     * 上传电子证照
     * @param request
     * @return
     */
    @RequestMapping(params = "selectHisMatByIds")
    @ResponseBody
    public void selectHisMatByIds(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> list=fileAttachService.useHisMatAgainByIds(variables);
        String json = JSONArray.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 手机APP获取附件信息
     * @author Rider Chen
     * @created 2016-2-18 上午10:31:08
     * @param request
     * @param response
     */
    @RequestMapping("/getFile")
    @ResponseBody
    public void getFile(HttpServletRequest request, HttpServletResponse response){
        String fileId = request.getParameter("fileId");
        if(StringUtils.isNotEmpty(fileId)){
            Map<String,Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"},new Object[]{fileId});
            String json = JSON.toJSONString(fileAttach);
            this.setJsonString(json, response);
        }
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        String[] fileIds = request.getParameterValues("fileIds[]");
        String busRecordId = request.getParameter("BUS_TABLERECORDID");
        String busTableName = request.getParameter("BUS_TABLENAME");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String attachFileIds = request.getParameter("attachFileIds");
        if(fileIds!=null&&fileIds.length>0){
            for(String fileId:fileIds){
                Map<String,Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"},new Object[]{fileId});
                if(null!=fileAttach){
                    fileAttach.put("SWF_FILEID", fileId);
                    list.add(fileAttach);
                }
            }
        }else if(StringUtils.isNotEmpty(attachFileIds)){
            fileIds = attachFileIds.split(",");
            for(String fileId:fileIds){
                Map<String,Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"},new Object[]{fileId});
                if(null!=fileAttach){
                    fileAttach.put("SWF_FILEID", fileId);
                    list.add(fileAttach);
                }
            }
        }else if(StringUtils.isNotEmpty(busRecordId)){
            list = fileAttachService.findList(busTableName, busRecordId);
            for(Map<String,Object> fileAttach :list){
                String fileId = (String) fileAttach.get("FILE_ID");
                fileAttach.put("SWF_FILEID", fileId);
            }
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowFiles")
    public ModelAndView flowFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        String projectId = request.getParameter("projectId");
        request.setAttribute("exeId", exeId);
        request.setAttribute("projectId", projectId);
        return new ModelAndView("hflow/flowtask/uploadFileAppMaters");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "flowList")
    public void flowList(HttpServletRequest request,
            HttpServletResponse response) {
        List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
        String projectId = request.getParameter("projectId");
        if(projectId!=null && !"".equals(projectId)) {
            List<Map<String, Object>> exeIdList = projectFlowService.findExeIdListByProjectId(projectId);
            if(exeIdList!=null && exeIdList.size()>0) {
                for(Map<String, Object> exeMap : exeIdList) {
                    List<Map<String, Object>> list = fileAttachService.findByExeId((String)exeMap.get("EXE_ID"),"-1");
                    if(list!=null && list.size()>0) {
                        for(Map<String, Object> map : list) {
                            resultList.add(map);
                        }
                    }
                }
            }
        }else {
            String exeId = request.getParameter("exeId");
            resultList = fileAttachService.findByExeId(exeId,"-1");
        }
        this.setListToJsonString(resultList.size(), resultList, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "mailInfo")
    public ModelAndView mailFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        return new ModelAndView("hflow/flowtask/mailInfo");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "mailInfoList")
    public void mailInfoList(HttpServletRequest request,
            HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> list = fileAttachService.findMailInfoByExeId(exeId);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:查看已上传过的所有材料
     *
     * @author Madison You
     * @created 2021/4/7 14:22:00
     * @param
     * @return
     */
    @RequestMapping(params = "queryAllUploadFilesView")
    public ModelAndView queryAllUploadFilesView(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("exeId", request.getParameter("exeId"));
        return new ModelAndView("hflow/flowtask/queryAllUploadFilesView");
    }

    /**
     * 描述:查询此办件的所有上传过的材料
     *
     * @author Madison You
     * @created 2021/4/7 14:29:00
     * @param
     * @return
     */
    @RequestMapping(params = "queryAllUploadFilesData")
    @ResponseBody
    public List<Map<String,Object>> queryAllUploadFilesData(HttpServletRequest request) {
        return fileAttachService.queryAllUploadFiles(request);
    }

}

