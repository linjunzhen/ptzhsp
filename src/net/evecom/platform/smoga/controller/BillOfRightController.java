/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.smoga.controller;

import java.io.IOException;
import java.util.*;

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
import net.evecom.platform.smoga.service.BillOfRightService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月1日 上午10:11:04
 */
@Controller
@RequestMapping("/billOfRightController")
public class BillOfRightController extends BaseController {

    /**
     * 引入service
     */
    @Resource
    private BillOfRightService billOfRightService;
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
    @RequestMapping(params="billOfRightTab")
    public ModelAndView billOfRightTab(HttpServletRequest request){
        return new ModelAndView("smoga/billofright/billOfRightTab");
    }
    
    /**
     * 
     * 描述    草稿库
     * @author Danto Huang
     * @created 2018年8月1日 下午3:11:32
     * @param request
     * @return
     */
    @RequestMapping(params="billOfRightDraft")
    public ModelAndView billOfRightDraft(HttpServletRequest request){
        return new ModelAndView("smoga/billofright/billOfRightDraft");
    }
    
    /**
     * 
     * 描述    草稿信息
     * @author Danto Huang
     * @created 2018年8月2日 下午4:23:19
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="draftInfo")
    public ModelAndView draftInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)){
            Map<String, Object> draftInfo = billOfRightService.getByJdbc("T_SMOGA_BILLOFRIGHTS",
                    new String[] { "RIGHT_ID" }, new Object[] { entityId });
            if(billOfRightService.isHavingHis(entityId)){
                request.setAttribute("isHaving", "1");
            }else{
                request.setAttribute("isHaving", "0");
            }
            request.setAttribute("draftInfo", draftInfo);
            
            List<Map<String,Object>> logList = billOfRightService.findTransLog("T_SMOGA_BILLOFRIGHTS", entityId);
            request.setAttribute("logList", logList);
        }else{
            request.setAttribute("isHaving", "0");
        }
        return new ModelAndView("smoga/billofright/draftInfo");
    }
    
    /**
     * 
     * 描述    保存
     * @author Danto Huang
     * @created 2018年8月2日 下午5:41:14
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RIGHT_ID");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            String rightCode = "QZQD" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd");
            rightCode += variables.get("RIGHT_TYPE").toString();
            int maxSn = billOfRightService.getMaxSn();
            variables.put("RIGHT_CODE", rightCode+String.format("%04d", maxSn+1));
            variables.put("C_SN", maxSn+1);
            variables.put("C_VERSION", "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
            variables.put("NEW_CATEGORY", 1);
        }
        if(!variables.get("RIGHT_SOURCE").equals("qt")){
            variables.put("RIGHT_SOURCE_OTHER", "");
        }
        String recordId = billOfRightService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 权责清单记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            saveItemLog(recordId,"-1", request);
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 权责清单记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述    删除
     * @author Danto Huang
     * @created 2018年8月3日 下午2:58:31
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        billOfRightService.remove("T_SMOGA_BILLOFRIGHTS", "RIGHT_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    审核库
     * @author Danto Huang
     * @created 2018年8月1日 下午3:11:32
     * @param request
     * @return
     */
    @RequestMapping(params="billOfRightAudit")
    public ModelAndView billOfRightAudit(HttpServletRequest request){
        return new ModelAndView("smoga/billofright/billOfRightAudit");
    }
    
    /**
     * 
     * 描述    审核信息页面
     * @author Danto Huang
     * @created 2018年8月6日 下午3:39:14
     * @param request
     * @return
     */
    @RequestMapping(params="opinionInfo")
    public ModelAndView opinionInfo(HttpServletRequest request){
        String rightIds = request.getParameter("rightIds");
        String status = request.getParameter("status");
        request.setAttribute("rightIds", rightIds);
        request.setAttribute("status",status);
        return new ModelAndView("smoga/billofright/opinionInfo");
    }
    
    /**
     * 
     * 描述    审核结果
     * @author Danto Huang
     * @created 2018年8月6日 下午4:01:50
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="updateSHYJ")
    @ResponseBody
    public AjaxJson updateSHYJ(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("rightIds");
        String sfty = request.getParameter("SFTY");
        String shyj = request.getParameter("SHYJ");
        SysUser curUser = AppUtil.getLoginUser();
        Map<String ,Object> variables = new HashMap<String, Object>();
        variables.put("AUDITOR_ID", curUser.getUserId());
        variables.put("AUDITOR_NAME", curUser.getFullname());
        variables.put("AUDITOR_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("AUDITOR_OPINION", shyj);
        String[] rightIds = entityIds.split(",");
        String type = null;
        for(int i=0;i<rightIds.length;i++){
            Map<String, Object> rightInfo = billOfRightService.getByJdbc("T_SMOGA_BILLOFRIGHTS",
                    new String[] { "RIGHT_ID" }, new Object[] { rightIds[i] });
            if (rightInfo.get("STATUS").equals("2") && sfty.equals("1")) {
                variables.put("STATUS", 1);
                type = "1";
            }else if (rightInfo.get("STATUS").equals("2") && sfty.equals("2")) {
                variables.put("STATUS", 3);
                type = "3";
            }else if (rightInfo.get("STATUS").equals("4") && sfty.equals("1")) {
                variables.put("STATUS", 5);
                type = "5";
            }else if (rightInfo.get("STATUS").equals("4") && sfty.equals("2")) {
                variables.put("STATUS", 1);
                type = "42";
            }else if (rightInfo.get("STATUS").equals("6") && sfty.equals("1")) {
                variables.put("STATUS", 1);
                variables.put("C_VERSION", "V"+DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
                type = "61";
                
                Map<String,Object> pre = new HashMap<String, Object>();
                pre.put("EFFECT_STATUS", 1);
                pre.put("C_VERSION", rightInfo.get("C_VERSION"));
                pre.put("RIGHT_ID", rightIds[i]);
                pre.put("MODIFY_TYPE", rightInfo.get("MODIFY_TYPE"));
                pre.put("MODIFY_DESC", rightInfo.get("MODIFY_DESC"));
                pre.put("MODIFY_TIME", rightInfo.get("MODIFY_TIME"));
                billOfRightService.saveOrUpdateRecord(pre);

                variables.put("MODIFY_TYPE", "");
                variables.put("MODIFY_DESC", "");
            }else if (rightInfo.get("STATUS").equals("6") && sfty.equals("2")) {
                variables.put("STATUS", 7);
                type = "7";
            }
            billOfRightService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS", rightIds[i]);
            variables.put("BUSRECORD_ID", rightIds[i]);
            variables.put("BUSTABLE_NAME", "T_SMOGA_BILLOFRIGHTS");
            variables.put("OLD_STATUS", rightInfo.get("STATUS"));
            billOfRightService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS_OPINION", null);
            
            saveItemLog(rightIds[i], type, request);
        }
        return j;
    }
    
    /**
     * 
     * 描述    发布库
     * @author Danto Huang
     * @created 2018年8月1日 下午3:11:32
     * @param request
     * @return
     */
    @RequestMapping(params="billOfRightPublish")
    public ModelAndView billOfRightPublish(HttpServletRequest request){
        return new ModelAndView("smoga/billofright/billOfRightPublish");
    }
    
    /**
     * 
     * 描述    变更
     * @author Danto Huang
     * @created 2018年8月7日 上午10:31:49
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="modifyInfo")
    public ModelAndView modifyInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String operType = request.getParameter("operType");
        Map<String, Object> draftInfo = billOfRightService.getByJdbc("T_SMOGA_BILLOFRIGHTS",
                new String[] { "RIGHT_ID" }, new Object[] { entityId });

        if(draftInfo.get("MODIFY_TYPE")!=null){
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("MODIFY_TYPE"),
                    "modifyType");
            draftInfo.put("modifyTypeName", modifyTypeName);
        }
        String rightSourceName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("RIGHT_SOURCE"),
                "qlly");
        if(draftInfo.get("RIGHT_SOURCE").equals("qt")){
            rightSourceName = rightSourceName + (String) draftInfo.get("RIGHT_SOURCE_OTHER");
        }
        draftInfo.put("rightSourceName", rightSourceName);
        if(draftInfo.get("RIGHT_TYPE")!=null){
            String rightTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("RIGHT_TYPE"),
                    "qzlb");
            draftInfo.put("rightTypeName", rightTypeName);
        }
        if(draftInfo.get("EXERCISE_LEVEL")!=null){
            String levelName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("EXERCISE_LEVEL"),
                    "xzcj");
            draftInfo.put("levelName", levelName);
        }
        
        request.setAttribute("draftInfo", draftInfo);
        request.setAttribute("operType", operType);
        
        List<Map<String,Object>> logList = billOfRightService.findTransLog("T_SMOGA_BILLOFRIGHTS", entityId);
        request.setAttribute("logList", logList);
        if(billOfRightService.isHavingHis(entityId)){
            request.setAttribute("isHaving", "1");
        }else{
            request.setAttribute("isHaving", "0");
        }
        return new ModelAndView("smoga/billofright/modifyInfo");
    }
    
    /**
     * 
     * 描述    变更保存
     * @author Danto Huang
     * @created 2018年8月7日 上午10:49:40
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="modify")
    @ResponseBody
    public AjaxJson modify(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RIGHT_ID");
        String modifyType = request.getParameter("MODIFY_TYPE");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        if(modifyType.equals("06")){
            variables.put("STATUS", 4);
        }else{
            variables.put("STATUS", 6);
            billOfRightService.copyToHis(entityId);
        }
        variables.put("MODIFY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        if(!variables.get("RIGHT_SOURCE").equals("qt")){
            variables.put("RIGHT_SOURCE_OTHER", "");
        }
        billOfRightService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS", entityId);
        sysLogService.saveLog("变更了ID为[" + entityId + "]的 权责清单记录", SysLogService.OPERATE_TYPE_EDIT);
        if(modifyType.equals("06")){
            saveItemLog(entityId, "4", request);
        }else{
            saveItemLog(entityId, "6", request);
        }
        j.setMsg("变更保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    取消库
     * @author Danto Huang
     * @created 2018年8月1日 下午3:11:32
     * @param request
     * @return
     */
    @RequestMapping(params="billOfRightCancel")
    public ModelAndView billOfRightCancel(HttpServletRequest request){
        return new ModelAndView("smoga/billofright/billOfRightCancel");
    }
    
    /**
     * 
     * 描述    已承接
     * @author Danto Huang
     * @created 2018年8月1日 下午3:11:32
     * @param request
     * @return
     */
    @RequestMapping(params="billOfRightUndertake")
    public ModelAndView billOfRightUndertake(HttpServletRequest request){
        return new ModelAndView("smoga/billofright/billOfRightUndertake");
    }
    
    /**
     * 
     * 描述    数据查询列表
     * @author Danto Huang
     * @created 2018年8月2日 下午3:21:02
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.C_SN", "DESC");
        /*filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.RIGHT_CODE", "asc");*/
        List<Map<String,Object>> list = billOfRightService.findBillOfRightsByFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    详细信息
     * @author Danto Huang
     * @created 2018年8月2日 下午4:23:19
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="draftDetail")
    public ModelAndView draftDetail(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String operType = request.getParameter("operType");
        Map<String, Object> draftInfo = billOfRightService.getByJdbc("T_SMOGA_BILLOFRIGHTS",
                new String[] { "RIGHT_ID" }, new Object[] { entityId });

        if(draftInfo.get("MODIFY_TYPE")!=null){
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("MODIFY_TYPE"),
                    "modifyType");
            draftInfo.put("modifyTypeName", modifyTypeName);
        }
        String rightSourceName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("RIGHT_SOURCE"),
                "qlly");
        if(Objects.equals(draftInfo.get("RIGHT_SOURCE"),"qt")){
            rightSourceName = rightSourceName + (String) draftInfo.get("RIGHT_SOURCE_OTHER");
        }
        draftInfo.put("rightSourceName", rightSourceName);
        if(draftInfo.get("RIGHT_TYPE")!=null){
            String rightTypeName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("RIGHT_TYPE"),
                    "qzlb");
            draftInfo.put("rightTypeName", rightTypeName);
        }
        if(draftInfo.get("EXERCISE_LEVEL")!=null){
            String levelName = dictionaryService.findByDicCodeAndTypeCode((String) draftInfo.get("EXERCISE_LEVEL"),
                    "xzcj");
            draftInfo.put("levelName", levelName);
        }
        
        request.setAttribute("draftInfo", draftInfo);
        request.setAttribute("operType", operType);
        List<Map<String,Object>> logList = billOfRightService.findTransLog("T_SMOGA_BILLOFRIGHTS", entityId);
        request.setAttribute("logList", logList);
        if(billOfRightService.isHavingHis(entityId)){
            request.setAttribute("isHaving", "1");
        }else{
            request.setAttribute("isHaving", "0");
        }
        if(draftInfo.get("STATUS").equals("4")||draftInfo.get("STATUS").equals("6")){
            return new ModelAndView("smoga/billofright/modifyInfo");
        }else{
            return new ModelAndView("smoga/billofright/draftDetail");
        }
    }
    
    /**
     * 
     * 描述    更新状态
     * @author Danto Huang
     * @created 2018年8月3日 下午4:33:03
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="updateStatus")
    @ResponseBody
    public AjaxJson updateStatus(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String[] rightIds = request.getParameter("rightIds").split(",");
        String status = request.getParameter("status");
        for(int i=0;i<rightIds.length;i++){
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("STATUS", status);
            billOfRightService.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS", rightIds[i]);
            saveItemLog(rightIds[i], status,request);
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
            content = "申请发布审核通过，权责清单事项发布";
        }else if(type.equals("2")){
            content = "申请发布";
        }else if(type.equals("3")){
            content = "申请发布审核不通过，退回至草稿库";
        }else if(type.equals("4")){
            content = "申请取消";
        }else if(type.equals("5")){
            content = "申请取消审核通过，权责清单事项取消";
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
        data.put("BUSTABLE_NAME","T_SMOGA_BILLOFRIGHTS");
        billOfRightService.saveOrUpdate(data, "T_SMOGA_BILLOFRIGHTS_LOG", null);
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
        List<Map<String,Object>> list = billOfRightService.findHisVersionForSelect(entityId);
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
            Map<String, Object> hisInfo = billOfRightService.getByJdbc("T_SMOGA_BILLOFRIGHTS_HIS",
                    new String[] { "RIGHT_ID", "C_VERSION" }, new Object[] { entityId, version });
            
            String modifyTypeName = dictionaryService.findByDicCodeAndTypeCode((String) hisInfo.get("MODIFY_TYPE"),
                    "modifyType");
            hisInfo.put("modifyTypeName", modifyTypeName);
            String rightSourceName = dictionaryService.findByDicCodeAndTypeCode((String) hisInfo.get("RIGHT_SOURCE"),
                    "qlly");
            if(hisInfo.get("RIGHT_SOURCE").equals("qt")){
                rightSourceName = rightSourceName + (String) hisInfo.get("RIGHT_SOURCE_OTHER");
            }
            hisInfo.put("rightSourceName", rightSourceName);
            String rightTypeName = dictionaryService.findByDicCodeAndTypeCode((String) hisInfo.get("RIGHT_TYPE"),
                    "qzlb");
            hisInfo.put("rightTypeName", rightTypeName);
            String levelName = dictionaryService.findByDicCodeAndTypeCode((String) hisInfo.get("EXERCISE_LEVEL"),
                    "xzcj");
            hisInfo.put("levelName", levelName);
            
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
        String rightIds = request.getParameter("rightIds");
        String departIds = request.getParameter("departIds");
        String departNames = request.getParameter("departNames");
        String idAddress = BrowserUtils.getIpAddr(request);
        billOfRightService.copyToOtherDepart(rightIds, departIds, departNames, idAddress);
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
    @RequestMapping("/itemSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        return new ModelAndView("smoga/billofright/itemSelector");
    }
}
