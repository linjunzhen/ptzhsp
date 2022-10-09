/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

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
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.zzhy.service.CancelService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 商事企业注销Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/cancelController")
public class CancelController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CancelController.class);
    /**
     * 引入Service
     */
    @Resource
    private CancelService cancelService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

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
        cancelService.remove("T_COMMERCIAL_CANCEL", "COMPANY_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 商事企业注销记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> cancel = cancelService.getByJdbc("T_COMMERCIAL_CANCEL", new String[] { "COMPANY_ID" },
                    new Object[] { entityId });
            request.setAttribute("cancel", cancel);
        }
        return new ModelAndView("zzhy/cancel/info");
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
        String entityId = request.getParameter("COMPANY_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = cancelService.saveOrUpdate(variables, "T_COMMERCIAL_CANCEL", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 商事企业注销记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 商事企业注销记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDiv")
    public ModelAndView refreshGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        String isClose = request.getParameter("isClose");
        request.setAttribute("currentTime", currentTime);
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/cancel/gdxxDiv");
    }

    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/initGdxxDiv")
    public ModelAndView initGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        String isClose = request.getParameter("isClose");
        String holder = request.getParameter("holder");
        List<Map<String, Object>> holderList = null;
        if (StringUtils.isNotEmpty(holder)) {
            holderList = (List<Map<String, Object>>) JSON.parse(holder);
        }
        request.setAttribute("holderList", holderList);
        request.setAttribute("currentTime", currentTime);
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/cancel/initGdxxDiv");
    }

    /**
     * 跳转到清算组成员信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshQszcyxxDiv")
    public ModelAndView refreshQszcyxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/ssqcwb/cancel/qszcyxxDiv");
    }

    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "enterpriseInfo")
    public ModelAndView enterpriseSelector(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/ssqcwb/cancel/enterpriseInfo");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getEnterpriseInfo")
    public void getEnterpriseInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> result = new HashMap<String, Object>();
        String qyname = StringUtil.getString(variables.get("qyname"));
        String qycode = StringUtil.getString(variables.get("qycode"));
        Map<String, Object> params = new HashMap<>();
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String devbaseUrl = properties.getProperty("genplatrequrlIn");
        String grantcode = properties.getProperty("grantcode");
        params.put("grantcode", grantcode);
        params.put("servicecode", "getCompanyInfo");
        if (StringUtils.isNotEmpty(qyname)) {
            params.put("ENTNAME", qyname);
        }
        if (StringUtils.isNotEmpty(qycode)) {
            params.put("UNISCID", qycode);
        }
        Map<String, Object> resultMsg = TokenUtil.doPost(devbaseUrl, params);
        boolean success = (boolean) resultMsg.get("success");
        if (success) {
            Map<String, Object> data = (Map<String, Object>) resultMsg.get("data");
            if (null != data && data.size() > 0) {
                Map<String, Object> baseInfo = (Map<String, Object>) data.get("baseInfo");
                String entType = StringUtil.getString(baseInfo.get("ENTTYPE"));
                boolean flag = isEntTypeToNz(entType);
                if (flag) {
                    result.put("success", true);
                    result.put("msg", "查询到企业数据!");
                    result.put("data", data);
                } else {
                    result.put("success", false);
                    result.put("msg", "请输入内资有限责任公司“自然人独资”，“法人独资”，“自然人投资或控股”<br/>，“其他有限责任公司”的企业信息!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "查询不到数据!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "查询接口错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 判断公司类型
     * 
     * @author Rider Chen
     * @created 2021年4月6日 上午11:52:54
     * @param entType
     * @return
     */
    public boolean isEntTypeToNz(String entType) {
        boolean flag = false;
        switch (entType) {
            case "1110":// 有限责任公司(国有独资)
                flag = false;
                break;
            case "1130":// 有限责任公司(自然人投资或控股)
                flag = true;
                break;
            case "1140":// 有限责任公司(国有控股)
                flag = false;
                break;
            case "1150":// 一人有限责任公司
                flag = true;
                break;
            case "1151":// 有限责任公司(自然人独资)
                flag = true;
                break;
            case "1152":// 有限责任公司(自然人投资或控股的法人独资)
                flag = true;
                break;
            case "1153":// 有限责任公司（非自然人投资或控股的法人独资）
                flag = true;
                break;
            case "1190":// 其他有限责任公司
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getCommercialDicType")
    public void getCommercialDicType(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> result = new HashMap<String, Object>();
        String typecode = StringUtil.getString(variables.get("typecode"));
        Map<String, Object> dictype = cancelService.getByJdbc("T_COMMERCIAL_DICTYPE", new String[] { "TYPE_CODE" },
                new Object[] { typecode });
        if (null != dictype && dictype.size() > 0) {
            result.put("success", true);
            result.put("msg", "查询数据成功!");
            result.put("data", dictype);
        } else {
            result.put("success", false);
            result.put("msg", "查询不到数据!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getCommercialDic")
    public void getCommercialDic(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> result = new HashMap<String, Object>();
        String typecode = StringUtil.getString(variables.get("typecode"));
        String diccode = StringUtil.getString(variables.get("diccode"));
        Map<String, Object> dic = cancelService.getByJdbc("T_COMMERCIAL_DIC", new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { typecode, diccode });
        if (null != dic && dic.size() > 0) {
            result.put("success", true);
            result.put("msg", "查询数据成功!");
            result.put("data", dic);
        } else {
            result.put("success", false);
            result.put("msg", "查询不到数据!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
}
