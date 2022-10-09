/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.declare.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.declare.util.DeclareUtil;

/**
 * 描述:在线申报
 * 
 * @author Michael Lin
 * @created 2018年10月9日 下午2:51:04
 */
@Controller
@RequestMapping("/declare")
public class DeclareController extends BaseController {
    
    /**
     * 接口地址
     */
    private static String url = "http://xzfwzx.pingtan.gov.cn:8888";
    
    /**
     * 
     * 描述 获取申报基本信息
     * 
     * @author Michael Lin
     * @throws Exception 
     * @created 2018年10月9日 下午2:51:04
     */
    @RequestMapping(params = "getDeclareInfo")
    public void getDeclareInfo(HttpServletRequest request , HttpServletResponse response){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("itemCode",request.getParameter("itemCode"));
        paramsMap.put("userCode",request.getParameter("userCode"));
        paramsMap.put("userName",request.getParameter("userName"));
        paramsMap.put("userCard",request.getParameter("userCard"));
        paramsMap.put("userMobile",request.getParameter("userMobile"));
        String result = DeclareUtil.sendPostParams(url+"/onlineApplyController/applyItem.do", paramsMap);
        this.setJsonString(result,response);
    }
    
    /**
     * 
     * 描述 上传图片
     * 
     * @author Michael Lin
     * @throws Exception 
     * @created 2018年10月9日 下午2:51:04
     */
    @RequestMapping(params = "uploadImages")
    public void uploadImages(HttpServletRequest request , HttpServletResponse response){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("busTableName",request.getParameter("busTableName"));
        paramsMap.put("uploadUserName",request.getParameter("uploadUserName"));
        paramsMap.put("attachKey",request.getParameter("attachKey"));
        paramsMap.put("uploadUserId",request.getParameter("uploadUserId"));
        paramsMap.put("URL",request.getParameter("URL"));
        paramsMap.put("localFilePath",request.getParameter("localFilePath"));
        paramsMap.put("uploadPath",request.getParameter("uploadPath"));
        String result = DeclareUtil.sendPostParams(url+"/PtwgUploadServlet", paramsMap);
        this.setJsonString(result,response);
    }
    
    /**
     * 
     * 描述 在线申报提交
     * 
     * @author Michael Lin
     * @throws Exception 
     * @created 2018年10月9日 下午2:51:04
     */
    @RequestMapping(params = "submitApply")
    public void submitApply(HttpServletRequest request , HttpServletResponse response){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("EFLOW_CREATORID",request.getParameter("EFLOW_CREATORID"));
        paramsMap.put("BELONG_DEPT",request.getParameter("BELONG_DEPT"));
        paramsMap.put("BELONG_DEPTNAME",request.getParameter("BELONG_DEPTNAME"));
        paramsMap.put("SXLX",request.getParameter("SXLX"));
        paramsMap.put("PROMISE_DATE",request.getParameter("PROMISE_DATE"));
        paramsMap.put("uploadUserId",request.getParameter("uploadUserId"));
        paramsMap.put("uploadUserName",request.getParameter("uploadUserName"));
        paramsMap.put("ITEM_NAME",request.getParameter("ITEM_NAME"));
        paramsMap.put("ITEM_CODE",request.getParameter("ITEM_CODE"));
        paramsMap.put("SSBMBM",request.getParameter("SSBMBM"));
        paramsMap.put("SQFS",request.getParameter("SQFS"));
        //paramsMap.put("EFLOW_FLOWOBJ",getPara("EFLOW_FLOWOBJ"));
        paramsMap.put("EFLOW_DEFKEY",request.getParameter("EFLOW_DEFKEY"));
        paramsMap.put("EFLOW_BUSTABLENAME",request.getParameter("EFLOW_BUSTABLENAME"));
        paramsMap.put("EFLOW_CUREXERUNNINGNODENAMES",request.getParameter("EFLOW_CUREXERUNNINGNODENAMES"));
        paramsMap.put("EFLOW_CURUSEROPERNODENAME",request.getParameter("EFLOW_CURUSEROPERNODENAME"));
        paramsMap.put("EFLOW_DEFID",request.getParameter("EFLOW_DEFID"));
        paramsMap.put("EFLOW_DEFVERSION",request.getParameter("EFLOW_DEFVERSION"));
        paramsMap.put("itemName",request.getParameter("itemName"));
        paramsMap.put("departName",request.getParameter("departName"));
        paramsMap.put("itemType",request.getParameter("itemType"));
        paramsMap.put("promiseTime",request.getParameter("promiseTime"));
        paramsMap.put("SBMC",request.getParameter("SBMC"));
        paramsMap.put("EFLOW_CREATORACCOUNT",request.getParameter("EFLOW_CREATORACCOUNT"));
        paramsMap.put("EFLOW_CREATORPHONE",request.getParameter("EFLOW_CREATORPHONE"));
        paramsMap.put("EFLOW_CREATORNAME",request.getParameter("EFLOW_CREATORNAME"));
        paramsMap.put("BSYHLX",request.getParameter("BSYHLX"));
        paramsMap.put("FINISH_GETTYPE_NAME",request.getParameter("FINISH_GETTYPE_NAME"));
        paramsMap.put("FINISH_GETTYPE",request.getParameter("FINISH_GETTYPE"));
        paramsMap.put("EFLOW_SUBMITMATERFILEJSON",request.getParameter("EFLOW_SUBMITMATERFILEJSON"));
        paramsMap.put("SQRXB",request.getParameter("SQRXB"));
        paramsMap.put("SQRXB_NAME",request.getParameter("SQRXB_NAME"));;
        paramsMap.put("RESULT_SEND_PROV",request.getParameter("RESULT_SEND_PROV"));
        paramsMap.put("RESULT_SEND_PROV_NAME",request.getParameter("RESULT_SEND_PROV_NAME"));
        paramsMap.put("RESULT_SEND_CITY",request.getParameter("RESULT_SEND_CITY"));
        paramsMap.put("RESULT_SEND_CITY_NAME",request.getParameter("RESULT_SEND_CITY_NAME"));
        paramsMap.put("BJCXMM",request.getParameter("BJCXMM"));
        paramsMap.put("SQRSJH",request.getParameter("SQRSJH"));
        paramsMap.put("JBR_NAME",request.getParameter("JBR_NAME"));
        paramsMap.put("JBR_ZJHM",request.getParameter("JBR_ZJHM"));
        paramsMap.put("JBR_MOBILE",request.getParameter("JBR_MOBILE"));
        paramsMap.put("RESULT_SEND_ADDRESSEE",request.getParameter("RESULT_SEND_ADDRESSEE"));
        paramsMap.put("RESULT_SEND_MOBILE",request.getParameter("RESULT_SEND_MOBILE"));
        paramsMap.put("RESULT_SEND_POSTCODE",request.getParameter("RESULT_SEND_POSTCODE"));
        paramsMap.put("RESULT_SEND_ADDR",request.getParameter("RESULT_SEND_ADDR"));
        paramsMap.put("RESULT_SEND_REMARKS",request.getParameter("RESULT_SEND_REMARKS"));
        String result = DeclareUtil.sendPostParams(url+"/mobileCommonController/submitApply.do", paramsMap);
        this.setJsonString(result,response);
    }

    /**
     *
     * 描述 获取事项
     *
     * @author Michael Lin
     * @throws Exception
     * @created 2018年10月9日 下午2:51:04
     */
    @RequestMapping(params = "findItemForRobot")
    public void findItemForRobot(HttpServletRequest request , HttpServletResponse response){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("ITEM_NAME",request.getParameter("ITEM_NAME"));
        String result = DeclareUtil.sendPostParams(url+"/serviceItemController/findItemForRobot.do", paramsMap);
        this.setJsonString(result,response);
    }

    /**
     *
     * 描述 获取事项详细信息
     *
     * @author Michael Lin
     * @throws Exception
     * @created 2018年10月9日 下午2:51:04
     */
    @RequestMapping(params = "itemDetail")
    public void itemDetail(HttpServletRequest request , HttpServletResponse response){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("ITEM_CODE",request.getParameter("ITEM_CODE"));
        String result = DeclareUtil.sendPostParams(url+"/serviceItemController/itemDetail.do", paramsMap);
        this.setJsonString(result,response);
    }
}
