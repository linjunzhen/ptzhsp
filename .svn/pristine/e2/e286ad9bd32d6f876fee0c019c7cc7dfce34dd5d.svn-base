/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.system.model.SysUser;
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
import com.zhuozhengsoft.pageoffice.FileSaver;

/**
 * 描述  申请材料Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/applyMaterController")
public class ApplyMaterController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ApplyMaterController.class);
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * busTypeService
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;
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
        applyMaterService.removeCascadeMiddle(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 申请材料记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String itemId = request.getParameter("itemId");
        Map<String,Object>  applyMater = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            applyMater = applyMaterService.getByJdbc("T_WSBS_APPLYMATER",
                    new String[]{"MATER_ID"},new Object[]{entityId});
            Map<String,Object> idAndNames = busTypeService.getIdAndNamesByMaterId(entityId);
            applyMater.put("BUS_TYPEIDS", idAndNames.get("TYPE_IDS"));
            applyMater.put("BUS_TYPENAMES", idAndNames.get("TYPE_NAMES"));
        }else{
            applyMater = new HashMap<String,Object>();
            applyMater.put("MATER_TYPE",AllConstant.DEFAULT_FILE_TYPES);
            applyMater.put("MATER_SIZE",AllConstant.DEFAULT_FILE_SIZE);
            applyMater.put("MATER_CLSM",1);
        }
        request.setAttribute("applyMater", applyMater);
        if(StringUtils.isNotEmpty(itemId)&&!itemId.equals("undefined")){
            request.setAttribute("itemId", itemId);
        }
        return new ModelAndView("wsbs/applymater/applymaterinfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("MATER_ID");
        String itemId = request.getParameter("itemId");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = applyMaterService.saveOrUpdateCascadeMiddle(variables);
        String content="";
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 申请材料记录",SysLogService.OPERATE_TYPE_EDIT);
            content="修改了服务事项记录的申请材料记录：【修改材料】材料名称为："+variables.get("MATER_NAME");
        }else{
            if(StringUtils.isNotEmpty(itemId)){
                applyMaterService.saveMaterItem(recordId, itemId);
            }
            sysLogService.saveLog("新增了ID为["+recordId+"]的 申请材料记录",SysLogService.OPERATE_TYPE_ADD);
            content="修改了服务事项记录的申请材料记录：【新增材料】材料名称为："+variables.get("MATER_NAME");
        }
        //维护信息
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE","1");
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        applyMaterService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
        
        j.setMsg("保存成功");
        return j;
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
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = applyMaterService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String materIds = request.getParameter("materIds");
        List<Map<String, Object>> list = applyMaterService.findByMaterIds(materIds);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/applymater/applymaterview");
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String materIds = request.getParameter("materIds");
        request.setAttribute("materIds", materIds);
        return new ModelAndView("wsbs/applymater/applymaterselector");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "itemSelected")
    public void itemSelected(HttpServletRequest request,
            HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String exeId = request.getParameter("exeId");
        String taskId = request.getParameter("taskId");
        List<Map<String, Object>> list = applyMaterService.findByMaterIds(userName,exeId,taskId);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "itemSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String taskId = request.getParameter("taskId");
        String exeId = request.getParameter("exeId");
        request.setAttribute("userName", userName);
        request.setAttribute("taskId", taskId);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("wsbs/applymater/applyMaterItemSelector");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "addMissItemSelector")
    public ModelAndView addMissItemSelector(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String taskId = request.getParameter("taskId");
        String exeId = request.getParameter("exeId");
        request.setAttribute("userName", userName);
        request.setAttribute("taskId", taskId);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("hflow/execution/applyMaterItemSelector");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "itemDatagrid")
    public void itemDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        String taskId = request.getParameter("taskId");
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> list = applyMaterService.findByTaskId(taskId, exeId,request);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "flowUserMaterSaveOrUpdate")
    @ResponseBody
    public void flowUserMaterSaveOrUpdate(HttpServletRequest request,HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        String taskId = request.getParameter("taskId");
        String exeId = request.getParameter("exeId");
        String nodeName = request.getParameter("nodeName");
        String userName = request.getParameter("userName");
        String materIds = request.getParameter("materIds");    
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("USERNAME", userName);
        variables.put("EXEID", exeId);
        variables.put("TASKID", taskId);
        variables.put("FROMTASK_NODENAMES", nodeName);
        variables.put("STATUS", 0);
        StringBuffer ids=new StringBuffer();
        applyMaterService.remove("JBPM6_FLOW_USER_MATER", new String[]{"USERNAME","EXEID","TASKID"},
                new Object[]{userName,exeId,taskId});
        if(StringUtils.isNotEmpty(materIds)){
            String [] materId = materIds.split(",");
            for (String id : materId) {
                variables.put("MATER_ID", id);
                Map<String, Object> map = applyMaterService.getByJdbc("JBPM6_FLOW_USER_MATER"
                        , new String[]{"MATER_ID","USERNAME","EXEID","TASKID"}, new Object[]{id,userName,exeId,taskId});
                if(null!=map){
                    ids.append(",").append(map.get("FLOW_USER_MATER_ID"));
                }else{
                    ids.append(",").append(applyMaterService.
                            saveOrUpdate(variables, "JBPM6_FLOW_USER_MATER", entityId));
                }
            }
        }
        if(StringUtils.isNotEmpty(ids)){
            ids = new StringBuffer(ids.substring(1, ids.length()));
            sysLogService.saveLog("新增了ID为["+ids+"]的 绑定审核材料记录",SysLogService.OPERATE_TYPE_ADD);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("ids", ids);
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addMissMater")
    @ResponseBody
    public void addMissMater(HttpServletRequest request,HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        String taskId = request.getParameter("taskId");
        String exeId = request.getParameter("exeId");
        String nodeName = request.getParameter("nodeName");
        String userName = request.getParameter("userName");
        String materIds = request.getParameter("materIds");    
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("USERNAME", userName);
        variables.put("EXEID", exeId);
        variables.put("TASKID", taskId);
        variables.put("FROMTASK_NODENAMES", nodeName);
        variables.put("STATUS", 1);
        StringBuffer ids=new StringBuffer();
        applyMaterService.remove("JBPM6_FLOW_USER_MATER", new String[]{"USERNAME","EXEID","TASKID"},
                new Object[]{userName,exeId,taskId});
        if(StringUtils.isNotEmpty(materIds)){
            String [] materId = materIds.split(",");
            for (String id : materId) {
                variables.put("MATER_ID", id);
                Map<String, Object> map = applyMaterService.getByJdbc("JBPM6_FLOW_USER_MATER"
                        , new String[]{"MATER_ID","USERNAME","EXEID","TASKID"}, new Object[]{id,userName,exeId,taskId});
                if(null!=map){
                    ids.append(",").append(map.get("FLOW_USER_MATER_ID"));
                }else{
                    ids.append(",").append(applyMaterService.
                            saveOrUpdate(variables, "JBPM6_FLOW_USER_MATER", entityId));
                }
            }
        }
        if(StringUtils.isNotEmpty(ids)){
            ids = new StringBuffer(ids.substring(1, ids.length()));
            sysLogService.saveLog("新增了ID为["+ids+"]的 绑定审核材料记录",SysLogService.OPERATE_TYPE_ADD);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("ids", ids);
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "forItemDatas")
    public void forItemDatas(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("SM.MATER_SN","asc");
        //获取服务事项ID
        String itemId = request.getParameter("itemId");
        if(StringUtils.isEmpty(itemId)){
            itemId = request.getParameter("Q_SM.ITEM_ID_EQ");
        }
        if(StringUtils.isNotEmpty(itemId)){
            filter.addFilter("Q_SM.ITEM_ID_=",itemId);
            List<Map<String, Object>> list = applyMaterService.findForItem(filter);
            //判断材料来源(出具单位)是否为数字，如果是则进行重新解释
            for (Map<String, Object> mapData : list) {
                if(mapData.get("MATER_SOURCE") != null){
                    switch (mapData.get("MATER_SOURCE").toString()) {
                        case "10":
                            mapData.put("MATER_SOURCE","申请人自备");
                            break;
                        case "20":
                            mapData.put("MATER_SOURCE","政府部门核发");
                            break;
                        case "99":
                            mapData.put("MATER_SOURCE","其他");
                            break;
                        default:
                            break;
                    }
                }
            }
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * 方法updateSn
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] materIds = request.getParameterValues("materIds[]");
        String itemId = request.getParameter("itemId");
        this.applyMaterService.updateSn(itemId, materIds);
        j.setMsg("排序成功");
        return j;
    }
    /**
     * 方法更新中间表绑定材料是否为必须提供
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateIsneed")
    @ResponseBody
    public AjaxJson updateIsneed(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String isneed = request.getParameter("isneed");
        String itemId = request.getParameter("itemId");
        applyMaterService.updateIsneed(isneed,itemId,selectColNames);
        j.setMsg("操作成功");
        return j;
    }
    /**
     * easyui AJAX请求数据 所有常见问题分页
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/bgxzPagelist")
    public void bgxzPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String materName = request.getParameter("MATER_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        Map<String, Object> mapList = applyMaterService.findbgxzList(page, rows,materName,busTypeIds);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "refreshPublicDoc")
    public ModelAndView refreshPublicDoc(HttpServletRequest request) {
        String handlerJson = request.getParameter("handlerJson");
        String PUBLIC_DOC_JSON = request.getParameter("PUBLIC_DOC_JSON");
        String publicDocRule = request.getParameter("publicDocRule");
        String IS_MARTER = request.getParameter("IS_MARTER");
        request.setAttribute("IS_MARTER", IS_MARTER);
        List<Map> userList = JSON.parseArray(handlerJson,Map.class);
        List<Map> docList = JSON.parseArray(PUBLIC_DOC_JSON,Map.class);
        if(publicDocRule.equals("-1")){
            for(Map<String,Object> user:userList){
                user.put("ASSIGNER_NAME", user.get("nextStepAssignerName"));
                user.put("ASSIGNER_DEPNAME", user.get("nextStepAssignerDepName"));
                user.put("ASSIGNER_CODE", user.get("nextStepAssignerCode"));
                user.put("materList", docList);
            }
            request.setAttribute("publicDocList", userList);
        }else if(publicDocRule.equals("1")){
            StringBuffer authUserCodes = new StringBuffer("");
            for(int i =0;i<userList.size();i++){
                if(i>0){
                    authUserCodes.append(",");
                }
                Map<String,Object> user = userList.get(i);
                authUserCodes.append(user.get("nextStepAssignerCode"));
            }
            for(Map<String,Object> mater:docList){
                mater.put("AUTH_USER_CODES", authUserCodes.toString());
            }
            request.setAttribute("publicDocList", docList);
        }
        request.setAttribute("publicDocRule", publicDocRule);
        return new ModelAndView("hflow/execution/refreshPublicDoc");
    }
    /**
     * 跳转到材料设置过滤参数
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "filterParameter")
    public ModelAndView filterParameter(HttpServletRequest request) {
        return new ModelAndView("wsbs/applymater/filtermaterinfo");
    }
    /**
     * 方法updateFilterPara
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateFilterPara")
    @ResponseBody
    public AjaxJson updateFilterPara(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String materIds = request.getParameter("materIds");
        String fpara = request.getParameter("fpara");
        String itemId = request.getParameter("itemId");
        applyMaterService.updateFilterPara(fpara, materIds,itemId);
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述 跳转材料申请材料在线编辑界面
     * @author Faker Li
     * @created 2016年4月19日 下午1:55:04
     * @param request
     * @return
     */
    @RequestMapping(params = "onlineWord")
    public ModelAndView onlineWord(HttpServletRequest request) {
        String fileId = request.getParameter("fileId");
        String uploadUserId = request.getParameter("uploadUserId");
        String uploadUserName = request.getParameter("uploadUserName");
        String materName = request.getParameter("materName");
        String materCode = request.getParameter("materCode");
        String onlineBusTableName = request.getParameter("onlineBusTableName");
        String isfrist = request.getParameter("isfrist");
        Map<String, Object> fileMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(fileId)&&!fileId.equals("undefined")){
            fileMap = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{fileId});
        }else{
            fileMap.put("FILE_PATH", "");
        }
        fileMap.put("uploadUserId", uploadUserId);
        fileMap.put("uploadUserName", uploadUserName);
        fileMap.put("materName", materName);
        fileMap.put("materCode", materCode);
        fileMap.put("onlineBusTableName", onlineBusTableName);
        fileMap.put("isfrist", isfrist);
        request.setAttribute("fileMap", fileMap);
        return new ModelAndView("wsbs/applymater/onlineWord");
    }
    /**
     * 
     * 描述 保存在线编辑文化
     * @author Faker Li
     * @created 2016年1月12日 上午9:29:13
     * @param request
     * @param response
     */
    @RequestMapping("/saveOnlineFile")
    public void saveOnlineFile(HttpServletRequest request, HttpServletResponse response) {
        String oldfilePath = request.getParameter("filePath");
        String filePath = "";
        String filename = "";
        Properties properties = FileUtil.readProperties("project.properties");
        if(StringUtils.isEmpty(oldfilePath)||oldfilePath.equals("undefined")){
            String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
            String uploadPath =  "applyform/";
         // 定义上传文件的保存的相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
         // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            String str = "";
            String uuId = UUIDGenerator.getUUID();
            str = uuId+".doc";
            filePath = "attachFiles/" + uploadPath+currentDate+"/"+ uuId + ".doc";
            filename = uploadFullPath + str;
        }else{
            filename = properties.getProperty("AttachFilePath") + oldfilePath;
            filePath = oldfilePath;
        }
        FileSaver fs = new FileSaver(request, response);
        fs.saveToFile(filename);
        fs.setCustomSaveResult(filePath);
        fs.close();
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveFileAttach")
    @ResponseBody
    public void saveFileAttach(HttpServletRequest request,HttpServletResponse response) {
        String entityId = request.getParameter("FILE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = fileAttachService.saveOrUpdate(variables, "T_MSJW_SYSTEM_FILEATTACH", entityId);
        /*if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 系统附件记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 系统附件记录",SysLogService.OPERATE_TYPE_ADD);
        }*/
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("msg", "保存成功！");
        resultMap.put("fileId", recordId);
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到信息页面(部门服务事项管理)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "infoDepart")
    public ModelAndView infoDepart(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String itemId = request.getParameter("itemId");
        String onlyCode = request.getParameter("onlyCode");
        Map<String,Object>  applyMater = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            applyMater = applyMaterService.getByJdbc("T_WSBS_APPLYMATER",
                    new String[]{"MATER_ID"},new Object[]{entityId});
        }else{
            applyMater = new HashMap<String,Object>();
            applyMater.put("MATER_TYPE",AllConstant.DEFAULT_FILE_TYPES);
            applyMater.put("MATER_SIZE",AllConstant.DEFAULT_FILE_SIZE);
            applyMater.put("MATER_CLSM",1);
        }
        if(StringUtils.isNotEmpty(itemId)&&!itemId.equals("undefined")){
            request.setAttribute("itemId", itemId);
            String itemCode = applyMaterService
                    .getByJdbc("T_WSBS_SERVICEITEM",
                            new String[] { "ITEM_ID" }, new Object[] { itemId })
                    .get("ITEM_CODE").toString();
            String maxCode = applyMaterService.getMaxNumCode(itemId);
            if(StringUtils.isEmpty("entityId")||entityId.equals("undefined")){
                String maxMaterCode = applyMaterService.getMaxMaterCodeByItemCode(itemCode,maxCode);
                applyMater.put("MATER_CODE", maxMaterCode);
            }
        }
        request.setAttribute("applyMater", applyMater);
        request.setAttribute("onlyCode", onlyCode);
        return new ModelAndView("wsbs/applymater/departapplymaterinfo");
    }

    /**
     * 修改或者修改操作(部门服务事项管理)
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateDepart")
    @ResponseBody
    public AjaxJson saveOrUpdateDepart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("MATER_ID");
        String itemId = request.getParameter("itemId");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);

        if (StringUtils.isNotEmpty(itemId)) {
            String status = "";
            status = departServiceItemService
                    .getByJdbc("T_WSBS_SERVICEITEM",
                            new String[] { "ITEM_ID" },
                            new Object[] { itemId }).get("FWSXZT").toString();
            if(status.equals("1")){
                serviceItemService.updateFwsxzt(itemId, "-1");
                status = "-1";
                saveItemLog("已发布事项撤销回草稿库",itemId,"-1",request);
            }
        }
      //业务表保存操作日志
        sysLogService.saveLogByMap("T_WSBS_APPLYMATER", "MATER_ID"
                , entityId, variables,entityId);
        String recordId = applyMaterService.saveOrUpdate(variables, "T_WSBS_APPLYMATER", entityId);
        String content="";
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 申请材料记录",SysLogService.OPERATE_TYPE_EDIT);
            content="修改了服务事项记录的申请材料记录：'修改材料'编码为："+variables.get("MATER_CODE");
        }else{
            if(StringUtils.isNotEmpty(itemId)){
                applyMaterService.saveMaterItem(recordId, itemId);
            }
            sysLogService.saveLog("新增了ID为["+recordId+"]的 申请材料记录",SysLogService.OPERATE_TYPE_ADD);
            content="修改了服务事项记录的申请材料记录：'新增材料'编码为："+variables.get("MATER_CODE");
        }
        
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE","1");
        data.put("BUS_TABLENAME", "T_WSBS_APPLYMATER");
        data.put("BUS_INDEX", entityId);
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        applyMaterService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
        
        j.setMsg("保存成功");
        return j;
    }

    /**
     * save item log
     * @param content
     * @param itemId
     * @param type
     * @param request
     */
    public void saveItemLog(String content,String itemId,String type,HttpServletRequest request){
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE",type);
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        serviceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
    }
    /**
     * 跳转到信息页面(新建材料所属业务分类)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "newBusunessClass")
    public ModelAndView newBusunessClass(HttpServletRequest request) {
        String itemId = request.getParameter("itemId");
        String selectId = request.getParameter("selectId");
        if (StringUtils.isNotEmpty(selectId)) {
            Map<String, Object> busMap = serviceItemService.getByJdbc("t_wsbs_serviceitem_busclass", new String[]{"RECORD_ID"}, new Object[]{selectId});
            request.setAttribute("busRecord", busMap);
        }
        request.setAttribute("itemId", itemId);
        return new ModelAndView("wsbs/applymater/newBusunessClass");
    }

    /**
     * 修改或者修改操作(材料所属业务类别)
     * @param request
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateBusclass")
    @ResponseBody
    public void saveOrUpdateBusclass(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String itemId = request.getParameter("ITEM_ID");
        String recordId = request.getParameter("RECORD_ID");
        String busclassName = request.getParameter("BUSCLASS_NAME");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> busMap = applyMaterService.getByJdbc("T_WSBS_SERVICEITEM_BUSCLASS", new String[]{"RECORD_ID"}, new Object[]{recordId});
        if (busMap != null) {
            applyMaterService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_BUSCLASS", recordId);
            resultMap.put("success", true);
            resultMap.put("msg", "修改成功！");
        } else {
            Map<String, Object> exist = applyMaterService.getByJdbc("T_WSBS_SERVICEITEM_BUSCLASS",
                    new String[]{"ITEM_ID", "BUSCLASS_NAME"}, new Object[]{itemId, busclassName});
            if (exist != null) {
                resultMap.put("success", false);
                resultMap.put("msg", "事项子项已存在！");
            } else {
                applyMaterService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_BUSCLASS", null);
                resultMap.put("success", true);
                resultMap.put("msg", "保存成功！");
            }
        }
        resultMap.put("recordId", recordId);
        resultMap.put("busclassName", busclassName);
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }

    /**
     * 修改或者修改操作(材料所属业务类别)
     * @param request
     */
    @RequestMapping(params = "removeBusunessClass")
    @ResponseBody
    public void removeBusunessClass(HttpServletRequest request,HttpServletResponse response) {
        String selectId = request.getParameter("selectId");
        String[] selectIds = selectId.split(",");
        for(int i=0;i<selectIds.length;i++){
            Map<String, Object> busClass = applyMaterService.getByJdbc("T_WSBS_SERVICEITEM_BUSCLASS",
                    new String[] { "RECORD_ID" }, new Object[] { selectIds[i] });
            List<Map<String, Object>> list = applyMaterService.findMatersByItemId((String) busClass.get("ITEM_ID"));
            if(list!=null&&list.size()>0){
                for(Map<String,Object> mater : list){
                    if(mater.get("MATER_SSYW")!=null){
                        String[] ssyw = mater.get("MATER_SSYW").toString().split(",");
                        String newssyw = "";
                        for(int j=0;j<ssyw.length;j++){
                            if(!ssyw[j].equals(selectIds[i])){
                                if(StringUtils.isNotEmpty(newssyw))
                                    newssyw = newssyw.concat(",").concat(ssyw[j]);
                                else
                                    newssyw = ssyw[j];
                            }
                        }
                        Map<String,Object> variables = new HashMap<String, Object>();
                        variables.put("MATER_SSYW", newssyw);
                        applyMaterService.saveOrUpdate(variables, "T_WSBS_APPLYMATER", (String) mater.get("MATER_ID"));
                    }
                }
            }
            applyMaterService.remove("T_WSBS_SERVICEITEM_BUSCLASS", new String[] { "RECORD_ID" },
                    new Object[] { selectIds[i] });
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("msg", "删除成功！");
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述  业务办理分类树
     * @author Danto Huang
     * @created 2018年9月3日 下午3:15:49
     * @param request
     * @param response
     * 
     */
    @RequestMapping(params = "handleTypeTree")
    public void handleTypeTree(HttpServletRequest request, HttpServletResponse response) {
        String itemId = request.getParameter("itemId");
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "业务办理分类");
        root.put("open", true);
        // 获取topType
        List<Map<String, Object>> toplist = applyMaterService.findHandleTypeList(itemId);
        List<Map<String, Object>> typelist = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> top : toplist) {
            if(top.get("BUS_HANDLE_TYPE")!=null){
                top.put("id", top.get("BUS_HANDLE_TYPE"));
                top.put("name", top.get("BUS_HANDLE_TYPE"));
                typelist.add(top);
            }
        }
        root.put("children", typelist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
}

