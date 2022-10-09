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
import net.evecom.platform.smoga.service.PreApprovalService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月7日 下午4:45:13
 */
@Controller
@RequestMapping("/preApprovalController")
public class PreApprovalController extends BaseController {

    /**
     * 引入preApprovalService
     */
    @Resource
    private PreApprovalService preApprovalService;
    
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
     * @created 2018年8月7日 下午4:50:19
     * @param request
     * @return
     */
    @RequestMapping(params="preApprovalTab")
    public ModelAndView preApprovalTab(HttpServletRequest request){
        return new ModelAndView("smoga/preApproval/preApprovalTab");
    }
    
    /**
     * 
     * 描述    草稿库
     * @author Danto Huang
     * @created 2018年8月7日 下午4:58:00
     * @param request
     * @return
     */
    @RequestMapping(params="preApprovalDraft")
    public ModelAndView preApprovalDraft(HttpServletRequest request){
        return new ModelAndView("smoga/preApproval/preApprovalDraft");
    }
    
    /**
     * 
     * 描述    保存
     * @author Danto Huang
     * @created 2018年8月8日 下午4:47:19
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("PRE_ID");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            int maxSn = preApprovalService.getMaxSn();
            variables.put("C_SN", maxSn+1);
            variables.put("C_VERSION", "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
        }
        String recordId = preApprovalService.saveOrUpdate(variables, "T_SMOGA_PREAPPROVAL", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 前置审批记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            saveItemLog(recordId,"-1", request);
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 前置审批记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述      草稿信息
     * @author Danto Huang
     * @created 2018年8月8日 上午9:41:52
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="draftInfo")
    public ModelAndView draftInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)){
            Map<String, Object> draftInfo = preApprovalService.getByJdbc("T_SMOGA_PREAPPROVAL",
                    new String[] { "PRE_ID" }, new Object[] { entityId });
            request.setAttribute("draftInfo", draftInfo);
            
            List<Map<String,Object>> logList = preApprovalService.findTransLog("T_SMOGA_PREAPPROVAL", entityId);
            request.setAttribute("logList", logList);
            if(preApprovalService.isHavingHis(entityId)){
                request.setAttribute("isHaving", "1");
            }else{
                request.setAttribute("isHaving", "0");
            }
        }else{
            request.setAttribute("isHaving", "0");
        }
        return new ModelAndView("smoga/preApproval/draftInfo");
    }
    
    /**
     * 
     * 描述    审核库
     * @author Danto Huang
     * @created 2018年8月7日 下午4:58:06
     * @param request
     * @return
     */
    @RequestMapping(params="preApprovalAudit")
    public ModelAndView preApprovalAudit(HttpServletRequest request){
        return new ModelAndView("smoga/preApproval/preApprovalAudit");
    }
    
    /**
     * 
     * 描述    审核信息页面
     * @author Danto Huang
     * @created 2018年8月9日 上午9:08:44
     * @param request
     * @return
     */
    @RequestMapping(params="opinionInfo")
    public ModelAndView opinionInfo(HttpServletRequest request){
        String preIds = request.getParameter("preIds");
        String status = request.getParameter("status");
        request.setAttribute("preIds", preIds);
        request.setAttribute("status",status);
        return new ModelAndView("smoga/preApproval/opinionInfo");
    }

    /**
     * 
     * 描述    审核结果
     * @author Danto Huang
     * @created 2018年8月9日 上午9:13:19
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="updateSHYJ")
    @ResponseBody
    public AjaxJson updateSHYJ(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("preIds");
        String sfty = request.getParameter("SFTY");
        String shyj = request.getParameter("SHYJ");
        SysUser curUser = AppUtil.getLoginUser();
        Map<String ,Object> variables = new HashMap<String, Object>();
        variables.put("AUDITOR_ID", curUser.getUserId());
        variables.put("AUDITOR_NAME", curUser.getFullname());
        variables.put("AUDITOR_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("AUDITOR_OPINION", shyj);
        String[] preIds = entityIds.split(",");
        String type = null;
        for(int i=0;i<preIds.length;i++){
            Map<String, Object> preInfo = preApprovalService.getByJdbc("T_SMOGA_PREAPPROVAL",
                    new String[] { "PRE_ID" }, new Object[] { preIds[i] });
            if (preInfo.get("STATUS").equals("2") && sfty.equals("1")) {
                variables.put("STATUS", 1);
                type = "1";
            }else if (preInfo.get("STATUS").equals("2") && sfty.equals("2")) {
                variables.put("STATUS", 3);
                type = "3";
            }else if (preInfo.get("STATUS").equals("4") && sfty.equals("1")) {
                variables.put("STATUS", 5);
                type = "5";
            }else if (preInfo.get("STATUS").equals("4") && sfty.equals("2")) {
                variables.put("STATUS", 1);
                type = "42";
            }else if (preInfo.get("STATUS").equals("6") && sfty.equals("1")) {
                variables.put("STATUS", 1);
                variables.put("C_VERSION", "V"+DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
                type = "61";
                
                Map<String,Object> pre = new HashMap<String, Object>();
                pre.put("EFFECT_STATUS", 1);
                pre.put("C_VERSION", preInfo.get("C_VERSION"));
                pre.put("PRE_ID", preIds[i]);
                pre.put("MODIFY_TYPE", preInfo.get("MODIFY_TYPE"));
                pre.put("MODIFY_DESC", preInfo.get("MODIFY_DESC"));
                pre.put("MODIFY_TIME", preInfo.get("MODIFY_TIME"));
                preApprovalService.saveOrUpdateRecord(pre);
                
                variables.put("MODIFY_TYPE", "");
                variables.put("MODIFY_DESC", "");
                
            }else if (preInfo.get("STATUS").equals("6") && sfty.equals("2")) {
                variables.put("STATUS", 7);
                type = "7";
            }
            preApprovalService.saveOrUpdate(variables, "T_SMOGA_PREAPPROVAL", preIds[i]);
            variables.put("BUSRECORD_ID", preIds[i]);
            variables.put("BUSTABLE_NAME", "T_SMOGA_PREAPPROVAL");
            variables.put("OLD_STATUS", preInfo.get("STATUS"));
            preApprovalService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS_OPINION", null);
            
            saveItemLog(preIds[i], type, request);
        }
        return j;
    }
    /**
     * 
     * 描述    发布库
     * @author Danto Huang
     * @created 2018年8月7日 下午4:58:10
     * @param request
     * @return
     */
    @RequestMapping(params="preApprovalPublish")
    public ModelAndView preApprovalPublish(HttpServletRequest request){
        return new ModelAndView("smoga/preApproval/preApprovalPublish");
    }
    
    /**
     * 
     * 描述    变更
     * @author Danto Huang
     * @created 2018年8月9日 上午9:58:34
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="modifyInfo")
    public ModelAndView modifyInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String operType = request.getParameter("operType");
        Map<String, Object> draftInfo = preApprovalService.getByJdbc("T_SMOGA_PREAPPROVAL",
                new String[] { "PRE_ID" }, new Object[] { entityId });
        if(draftInfo.get("MODIFY_TYPE")!=null){
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("MODIFY_TYPE"),
                    "modifyType");
            draftInfo.put("modifyTypeName", modifyTypeName);
        }
        request.setAttribute("draftInfo", draftInfo);
        request.setAttribute("operType", operType);
        
        List<Map<String,Object>> logList = preApprovalService.findTransLog("T_SMOGA_PREAPPROVAL", entityId);
        request.setAttribute("logList", logList);
        if(preApprovalService.isHavingHis(entityId)){
            request.setAttribute("isHaving", "1");
        }else{
            request.setAttribute("isHaving", "0");
        }
        return new ModelAndView("smoga/preApproval/modifyInfo");
    }
    
    /**
     * 
     * 描述    变更保存
     * @author Danto Huang
     * @created 2018年8月9日 上午10:02:16
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="modify")
    @ResponseBody
    public AjaxJson modify(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("PRE_ID");
        String modifyType = request.getParameter("MODIFY_TYPE");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        if(modifyType.equals("06")){
            variables.put("STATUS", 4);
        }else{
            variables.put("STATUS", 6);
            preApprovalService.copyToHis(entityId);
        }
        variables.put("MODIFY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        preApprovalService.saveOrUpdate(variables, "T_SMOGA_PREAPPROVAL", entityId);
        sysLogService.saveLog("变更了ID为[" + entityId + "]的 前置审批记录", SysLogService.OPERATE_TYPE_EDIT);
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
     * @created 2018年8月7日 下午4:58:14
     * @param request
     * @return
     */
    @RequestMapping(params="preApprovalCancel")
    public ModelAndView preApprovalCancel(HttpServletRequest request){
        return new ModelAndView("smoga/preApproval/preApprovalCancel");
    }
    
    /**
     * 
     * 描述    数据列表查询
     * @author Danto Huang
     * @created 2018年8月8日 上午8:58:27
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.C_SN", "DESC");
        List<Map<String,Object>> list = preApprovalService.findPreApprovalsBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月9日 上午9:00:25
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="draftDetail")
    public ModelAndView draftDetail(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String operType = request.getParameter("operType");
        Map<String, Object> draftInfo = preApprovalService.getByJdbc("T_SMOGA_PREAPPROVAL",
                new String[] { "PRE_ID" }, new Object[] { entityId });
        if(draftInfo.get("MODIFY_TYPE")!=null){
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("MODIFY_TYPE"),
                    "modifyType");
            draftInfo.put("modifyTypeName", modifyTypeName);
        }
        request.setAttribute("draftInfo", draftInfo);
        request.setAttribute("operType", operType);
        
        List<Map<String,Object>> logList = preApprovalService.findTransLog("T_SMOGA_PREAPPROVAL", entityId);
        request.setAttribute("logList", logList);
        if(preApprovalService.isHavingHis(entityId)){
            request.setAttribute("isHaving", "1");
        }else{
            request.setAttribute("isHaving", "0");
        }
        if(draftInfo.get("STATUS").equals("4")||draftInfo.get("STATUS").equals("6")){
            return new ModelAndView("smoga/preApproval/modifyInfo");
        }else{
            return new ModelAndView("smoga/preApproval/draftDetail");
        }
    }

    /**
     * 
     * 描述    状态更新
     * @author Danto Huang
     * @created 2018年8月8日 下午5:22:32
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="updateStatus")
    @ResponseBody
    public AjaxJson updateStatus(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String[] preIds = request.getParameter("preIds").split(",");
        String status = request.getParameter("status");
        for(int i=0;i<preIds.length;i++){
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("STATUS", status);
            preApprovalService.saveOrUpdate(variables, "T_SMOGA_PREAPPROVAL", preIds[i]);
            saveItemLog(preIds[i], status,request);
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
            content = "申请发布审核通过，前置审批事项发布";
        }else if(type.equals("2")){
            content = "申请发布";
        }else if(type.equals("3")){
            content = "申请发布审核不通过，退回至草稿库";
        }else if(type.equals("4")){
            content = "申请取消";
        }else if(type.equals("5")){
            content = "申请取消审核通过，前置审批事项取消";
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
        data.put("BUSTABLE_NAME","T_SMOGA_PREAPPROVAL");
        preApprovalService.saveOrUpdate(data, "T_SMOGA_BILLOFRIGHTS_LOG", null);
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
        List<Map<String,Object>> list = preApprovalService.findHisVersionForSelect(entityId);
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
            Map<String, Object> hisInfo = preApprovalService.getByJdbc("T_SMOGA_PREAPPROVAL_HIS",
                    new String[] { "PRE_ID", "C_VERSION" }, new Object[] { entityId, version });
            
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) hisInfo.get("MODIFY_TYPE"),
                    "modifyType");
            hisInfo.put("modifyTypeName", modifyTypeName);
            
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
        String preIds = request.getParameter("preIds");
        String departIds = request.getParameter("departIds");
        String departNames = request.getParameter("departNames");
        String idAddress = BrowserUtils.getIpAddr(request);
        preApprovalService.copyToOtherDepart(preIds, departIds, departNames, idAddress);
        j.setMsg("操作成功");
        return j;
    }
}
