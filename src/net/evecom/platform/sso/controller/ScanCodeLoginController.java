/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sso.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.util.ExcelRedrawUtil;
import net.evecom.platform.bsfw.util.AESOperator;
import net.evecom.platform.sso.service.ScanCodeLoginService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 扫码登录Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/scanCodeLoginController")
public class ScanCodeLoginController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ScanCodeLoginController.class);
    /**
     * 引入Service
     */
    @Resource
    private ScanCodeLoginService scanCodeLoginService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;    
    /**
     * 引入service
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        scanCodeLoginService.remove("T_SYSTEM_SCANCODE_LOGIN", "LOGIN_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 扫码登录记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
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
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> scanCodeLogin = scanCodeLoginService.getByJdbc("T_SYSTEM_SCANCODE_LOGIN",
                    new String[] { "LOGIN_ID" }, new Object[] { entityId });
            request.setAttribute("scanCodeLogin", scanCodeLogin);
        }
        return new ModelAndView("sso/scanCodeLogin/info");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("LOGIN_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = scanCodeLoginService.saveOrUpdate(variables, "T_SYSTEM_SCANCODE_LOGIN", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 扫码登录记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 扫码登录记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * easyui AJAX请求数据 手机详情
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getLoginQRCode")
    public void getLoginQRCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = UUIDGenerator.getUUID();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("token", token);
        scanCodeLoginService.saveOrUpdate(info, "T_SYSTEM_SCANCODE_LOGIN", null);
        String encryptKey = "qwe!@#45TF12MNBF";
        String scanStr = "";
        try {
            String codeNum = "login_qrcode_" + new Date().getTime();
            String params = "{\"tokenID\":\"ptzhsp_" + token + "\",\"qrcode\":\"" + codeNum + "\"}";
            scanStr = "{\"code_type\":\"login\",\"param\":\"" + AESOperator.aesEncrypt(params,encryptKey) + "\"}";
            //scanStr = AESOperator.aesEncrypt(pJson, encryptKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("加密失败", e);
        }
        result.put("token", token);
        result.put("scanStr", scanStr);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求数据 手机详情
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/isSacnCode")
    public void isSacnCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String scanToken = request.getParameter("scanToken");
        if (StringUtils.isNotEmpty(scanToken)) {
            Map<String, Object> info = scanCodeLoginService.getByJdbc("T_SYSTEM_SCANCODE_LOGIN",
                    new String[] { "TOKEN" }, new Object[] { scanToken });
            if (null != info) {
                String code = StringUtil.getString(info.get("CODE"));
                String tokenTime = StringUtil.getString(info.get("CREATE_TIME"));
                if (StringUtils.isNotEmpty(code)) {
                    if (StringUtils.isNotEmpty(tokenTime)) {
                        long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                                DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        if (timeMinute >1) {
                            result.put("success", true);
                            result.put("msg", "二维码过期");
                            result.put("type", -1);
                        } else{
                            List<Map<String, Object>>  list = sysUserService.findByMoblie(code);
                            result.put("success", true);
                            result.put("list", list);
                            result.put("type", 1);
                        }
                    }
                } else {
                    result.put("success", false);
                }
            } else {
                result.put("success", false);
            }
        } else {
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
}
