/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.smoga.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.smoga.service.AgencyServiceService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月9日 下午3:56:05
 */
@Controller
@RequestMapping("/agencyServiceController")
public class AgencyServiceController extends BaseController {

    /**
     * 引入agencyServiceService
     */
    @Resource
    private AgencyServiceService agencyServiceService;
    
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月1日 下午3:11:32
     * @param request
     * @return
     */
    @RequestMapping(params="agencyServiceTab")
    public ModelAndView agencyServiceTab(HttpServletRequest request){
        return new ModelAndView("smoga/agencyService/agencyServiceTab");
    }
    
    /**
     * 
     * 描述    草稿库
     * @author Danto Huang
     * @created 2018年8月9日 下午4:39:55
     * @param request
     * @return
     */
    @RequestMapping(params="agencyServiceDraft")
    public ModelAndView agencyServiceDraft(HttpServletRequest request){
        return new ModelAndView("smoga/agencyService/agencyServiceDraft");
    }
    
    /**
     * 
     * 描述    草稿信息
     * @author Danto Huang
     * @created 2018年8月9日 下午4:46:03
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="draftInfo")
    public ModelAndView draftInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)){
            Map<String, Object> draftInfo = agencyServiceService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                    new String[] { "SERVICE_ID" }, new Object[] { entityId });
            if(draftInfo.get("AGENCY_ORG_TYPE")!=null){
                String agencyTypeName = dictionaryService
                        .findByDicCodeAndTypeCode((String) draftInfo.get("AGENCY_ORG_TYPE"), "agencyType");
                draftInfo.put("agencyTypeName", agencyTypeName);
            }
            request.setAttribute("draftInfo", draftInfo);
            
            List<Map<String,Object>> logList = agencyServiceService.findTransLog("T_SMOGA_AGENCYSERVICE", entityId);
            request.setAttribute("logList", logList);
            if(agencyServiceService.isHavingHis(entityId)){
                request.setAttribute("isHaving", "1");
            }else{
                request.setAttribute("isHaving", "0");
            }
        }else{
            request.setAttribute("isHaving", "0");
        }
        return new ModelAndView("smoga/agencyService/draftInfo");
    }
    
    /**
     * 
     * 描述    保存
     * @author Danto Huang
     * @created 2018年8月9日 下午5:04:44
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SERVICE_ID");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            int maxSn = agencyServiceService.getMaxSn();
            variables.put("C_SN", maxSn+1);
            variables.put("C_VERSION", "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
        }
        String recordId = agencyServiceService.saveOrUpdate(variables, "T_SMOGA_AGENCYSERVICE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 中介服务记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            saveItemLog(recordId,"-1", request);
            sysLogService.saveLog("新增了ID为[" + recordId + "]的中介服务记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述    审核库
     * @author Danto Huang
     * @created 2018年8月9日 下午4:41:35
     * @param request
     * @return
     */
    @RequestMapping(params="agencyServiceAudit")
    public ModelAndView agencyServiceAudit(HttpServletRequest request){
        return new ModelAndView("smoga/agencyService/agencyServiceAudit");
    }
    
    /**
     * 
     * 描述    详细信息
     * @author Danto Huang
     * @created 2018年8月10日 上午9:47:30
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="draftDetail")
    public ModelAndView draftDetail(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String operType = request.getParameter("operType");
        Map<String, Object> draftInfo = agencyServiceService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                new String[] { "SERVICE_ID" }, new Object[] { entityId });
        if(draftInfo.get("MODIFY_TYPE")!=null){
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("MODIFY_TYPE"),
                    "modifyType");
            draftInfo.put("modifyTypeName", modifyTypeName);
        }
        if(draftInfo.get("AGENCY_ORG_TYPE")!=null){
            String agencyTypeName = dictionaryService
                    .findByDicCodeAndTypeCode((String) draftInfo.get("AGENCY_ORG_TYPE"), "agencyType");
            draftInfo.put("agencyTypeName", agencyTypeName);
        }
        request.setAttribute("draftInfo", draftInfo);
        request.setAttribute("operType", operType);
        
        List<Map<String,Object>> logList = agencyServiceService.findTransLog("T_SMOGA_AGENCYSERVICE", entityId);
        request.setAttribute("logList", logList);
        if(agencyServiceService.isHavingHis(entityId)){
            request.setAttribute("isHaving", "1");
        }else{
            request.setAttribute("isHaving", "0");
        }
        if(draftInfo.get("STATUS").equals("4")||draftInfo.get("STATUS").equals("6")){
            return new ModelAndView("smoga/agencyService/modifyInfo");
        }else{
            return new ModelAndView("smoga/agencyService/draftDetail");
        }
    }
    
    /**
     * 
     * 描述    审核信息页面
     * @author Danto Huang
     * @created 2018年8月10日 上午9:52:34
     * @param request
     * @return
     */
    @RequestMapping(params="opinionInfo")
    public ModelAndView opinionInfo(HttpServletRequest request){
        String serviceIds = request.getParameter("serviceIds");
        String status = request.getParameter("status");
        request.setAttribute("serviceIds", serviceIds);
        request.setAttribute("status",status);
        return new ModelAndView("smoga/agencyService/opinionInfo");
    }

    /**
     * 
     * 描述    审核结果
     * @author Danto Huang
     * @created 2018年8月10日 上午9:55:06
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="updateSHYJ")
    @ResponseBody
    public AjaxJson updateSHYJ(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("serviceIds");
        String sfty = request.getParameter("SFTY");
        String shyj = request.getParameter("SHYJ");
        SysUser curUser = AppUtil.getLoginUser();
        Map<String ,Object> variables = new HashMap<String, Object>();
        variables.put("AUDITOR_ID", curUser.getUserId());
        variables.put("AUDITOR_NAME", curUser.getFullname());
        variables.put("AUDITOR_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("AUDITOR_OPINION", shyj);
        String[] serviceIds = entityIds.split(",");
        String type = null;
        for(int i=0;i<serviceIds.length;i++){
            Map<String, Object> serviceInfo = agencyServiceService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                    new String[] { "SERVICE_ID" }, new Object[] { serviceIds[i] });
            if (serviceInfo.get("STATUS").equals("2") && sfty.equals("1")) {
                variables.put("STATUS", 1);
                type = "1";
            }else if (serviceInfo.get("STATUS").equals("2") && sfty.equals("2")) {
                variables.put("STATUS", 3);
                type = "3";
            }else if (serviceInfo.get("STATUS").equals("4") && sfty.equals("1")) {
                variables.put("STATUS", 5);
                type = "5";
            }else if (serviceInfo.get("STATUS").equals("4") && sfty.equals("2")) {
                variables.put("STATUS", 1);
                type = "42";
            }else if (serviceInfo.get("STATUS").equals("6") && sfty.equals("1")) {
                variables.put("STATUS", 1);
                variables.put("C_VERSION", "V"+DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
                type = "61";
                
                Map<String,Object> pre = new HashMap<String, Object>();
                pre.put("EFFECT_STATUS", 1);
                pre.put("C_VERSION", serviceInfo.get("C_VERSION"));
                pre.put("SERVICE_ID", serviceIds[i]);
                pre.put("MODIFY_TYPE", serviceInfo.get("MODIFY_TYPE"));
                pre.put("MODIFY_DESC", serviceInfo.get("MODIFY_DESC"));
                pre.put("MODIFY_TIME", serviceInfo.get("MODIFY_TIME"));
                agencyServiceService.saveOrUpdateRecord(pre);

                variables.put("MODIFY_TYPE", "");
                variables.put("MODIFY_DESC", "");
            }else if (serviceInfo.get("STATUS").equals("6") && sfty.equals("2")) {
                variables.put("STATUS", 7);
                type = "7";
            }
            agencyServiceService.saveOrUpdate(variables, "T_SMOGA_AGENCYSERVICE", serviceIds[i]);
            variables.put("BUSRECORD_ID", serviceIds[i]);
            variables.put("BUSTABLE_NAME", "T_SMOGA_AGENCYSERVICE");
            variables.put("OLD_STATUS", serviceInfo.get("STATUS"));
            agencyServiceService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS_OPINION", null);
            
            saveItemLog(serviceIds[i], type, request);
        }
        return j;
    }
    
    /**
     * 
     * 描述    发布库
     * @author Danto Huang
     * @created 2018年8月9日 下午4:41:44
     * @param request
     * @return
     */
    @RequestMapping(params="agencyServicePublish")
    public ModelAndView agencyServicePublish(HttpServletRequest request){
        return new ModelAndView("smoga/agencyService/agencyServicePublish");
    }
    
    /**
     * 
     * 描述    变更
     * @author Danto Huang
     * @created 2018年8月10日 上午10:10:07
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="modifyInfo")
    public ModelAndView modifyInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String operType = request.getParameter("operType");
        Map<String, Object> draftInfo = agencyServiceService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                new String[] { "SERVICE_ID" }, new Object[] { entityId });
        if(draftInfo.get("MODIFY_TYPE")!=null){
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("MODIFY_TYPE"),
                    "modifyType");
            draftInfo.put("modifyTypeName", modifyTypeName);
        }
        if(draftInfo.get("AGENCY_ORG_TYPE")!=null){
            String agencyTypeName = dictionaryService
                    .findByDicCodeAndTypeCode((String) draftInfo.get("AGENCY_ORG_TYPE"), "agencyType");
            draftInfo.put("agencyTypeName", agencyTypeName);
        }
        request.setAttribute("draftInfo", draftInfo);
        request.setAttribute("operType", operType);
        
        List<Map<String,Object>> logList = agencyServiceService.findTransLog("T_SMOGA_AGENCYSERVICE", entityId);
        request.setAttribute("logList", logList);
        if(agencyServiceService.isHavingHis(entityId)){
            request.setAttribute("isHaving", "1");
        }else{
            request.setAttribute("isHaving", "0");
        }
        return new ModelAndView("smoga/agencyService/modifyInfo");
    }
    
    /**
     * 
     * 描述    变更保存
     * @author Danto Huang
     * @created 2018年8月10日 上午10:16:08
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="modify")
    @ResponseBody
    public AjaxJson modify(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SERVICE_ID");
        String modifyType = request.getParameter("MODIFY_TYPE");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        if(modifyType.equals("06")){
            variables.put("STATUS", 4);
        }else{
            variables.put("STATUS", 6);
            agencyServiceService.copyToHis(entityId);
        }
        variables.put("MODIFY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        agencyServiceService.saveOrUpdate(variables, "T_SMOGA_AGENCYSERVICE", entityId);
        sysLogService.saveLog("变更了ID为[" + entityId + "]的 中介服务记录", SysLogService.OPERATE_TYPE_EDIT);
        if(modifyType.equals("06")){
            saveItemLog(entityId, "4", request);
        }else{
            saveItemLog(entityId, "6", request);
        }
        
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    取消库
     * @author Danto Huang
     * @created 2018年8月9日 下午4:41:51
     * @param request
     * @return
     */
    @RequestMapping(params="agencyServiceCancel")
    public ModelAndView agencyServiceCancel(HttpServletRequest request){
        return new ModelAndView("smoga/agencyService/agencyServiceCancel");
    }
    /**
     * 
     * 描述    数据列表查询
     * @author Danto Huang
     * @created 2018年8月9日 下午4:36:33
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.C_SN", "DESC");
        List<Map<String,Object>> list = agencyServiceService.findAgencyServiceBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    更新状态
     * @author Danto Huang
     * @created 2018年8月9日 下午5:17:08
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="updateStatus")
    @ResponseBody
    public AjaxJson updateStatus(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String[] serviceIds = request.getParameter("serviceIds").split(",");
        String status = request.getParameter("status");
        for(int i=0;i<serviceIds.length;i++){
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("STATUS", status);
            agencyServiceService.saveOrUpdate(variables, "T_SMOGA_AGENCYSERVICE", serviceIds[i]);
            saveItemLog(serviceIds[i], status,request);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月10日 下午3:59:58
     * @param type
     * @param request
     */
    @SuppressWarnings("unchecked")
    private void saveItemLog(String entityId, String type, HttpServletRequest request){
        Map<String,Object> data=new HashMap<String, Object>();
        String content = null;
        if(type.equals("-1")){
            content = "新建";
        }else if(type.equals("-2")){
            content = "撤回至草稿库";
        }else if(type.equals("1")){
            content = "申请发布审核通过，中介服务发布";
        }else if(type.equals("2")){
            content = "申请发布";
        }else if(type.equals("3")){
            content = "申请发布审核不通过，退回至草稿库";
        }else if(type.equals("4")){
            content = "申请取消";
        }else if(type.equals("5")){
            content = "申请取消审核通过，中介服务取消";
        }else if(type.equals("6")){
            content = "变更，并申请发布";
        }else if(type.equals("7")){
            content = "变更申请发布审核不通过，退回至草稿库";
        }else if(type.equals("42")){
            content = "申请取消审核不通过，保持发布";
        }else if(type.equals("61")){
            content = "变更申请发布审核通过，版本升级并发布";
        }
        data.put("OPERATE_CONTENT",content);
        data.put("OPERATE_TYPE",type);
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        data.put("BUSRECORD_ID",entityId);
        data.put("BUSTABLE_NAME","T_SMOGA_AGENCYSERVICE");
        agencyServiceService.saveOrUpdate(data, "T_SMOGA_BILLOFRIGHTS_LOG", null);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月13日 下午3:42:53
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/loadData")
    public void loadData(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String entityId = request.getParameter("entityId");
        List<Map<String,Object>> list = agencyServiceService.findHisVersionForSelect(entityId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("DIC_CODE","");
        map.put("DIC_NAME", "请选择历史版本");
        list.add(0, map);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    历史版本详细信息
     * @author Danto Huang
     * @created 2018年8月13日 下午4:10:14
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="hisVersionInfo")
    @ResponseBody
    public AjaxJson hisVersionInfo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        String version = request.getParameter("version");
        if(StringUtils.isNotEmpty(entityId)&&StringUtils.isNotEmpty(version)){
            Map<String, Object> hisInfo = agencyServiceService.getByJdbc("T_SMOGA_AGENCYSERVICE_HIS",
                    new String[] { "SERVICE_ID", "C_VERSION" }, new Object[] { entityId, version });

            if(hisInfo.get("MODIFY_TYPE")!=null){
                String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) hisInfo.get("MODIFY_TYPE"),
                        "modifyType");
                hisInfo.put("modifyTypeName", modifyTypeName);
            }
            if(hisInfo.get("AGENCY_ORG_TYPE")!=null){
                String agencyTypeName = dictionaryService
                        .findByDicCodeAndTypeCode((String) hisInfo.get("AGENCY_ORG_TYPE"), "agencyType");
                hisInfo.put("agencyTypeName", agencyTypeName);
            }
            
            j.setJsonString(JSON.toJSONString(hisInfo));
            j.setSuccess(true);
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 
     * 描述    复制
     * @author Danto Huang
     * @created 2018年8月17日 上午9:40:23
     * @param request
     * @return
     */
    @RequestMapping(params="copyToOtherDepart")
    @ResponseBody
    public AjaxJson copyToOtherDepart(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String serviceIds = request.getParameter("serviceIds");
        String departIds = request.getParameter("departIds");
        String departNames = request.getParameter("departNames");
        String idAddress = BrowserUtils.getIpAddr(request);
        agencyServiceService.copyToOtherDepart(serviceIds, departIds, departNames, idAddress);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月21日 下午3:43:50
     * @param request
     * @return
     */
    @RequestMapping("/agencySelector")
    public ModelAndView agencySelector(HttpServletRequest request) {
        return new ModelAndView("smoga/agencyService/agencySelector");
    }
}
