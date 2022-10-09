/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.SerialNumberService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 描述 编号配置Controller
 * 
 * @author Derek Zhang
 * @created 2015年9月22日 上午11:21:39
 */
@Controller
@RequestMapping("/serialNumberController")
public class SerialNumberController extends BaseController {
    /**
     * log4J声明
     */
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(SerialNumberController.class);
    /**
     * 引入Service
     */
    @Resource
    private SerialNumberService serialNumberService;
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
        serialNumberService.remove("t_wsbs_serialnumber", "serial_id", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的编号配置记录", SysLogService.OPERATE_TYPE_DEL);
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
            @SuppressWarnings("unchecked")
            Map<String, Object> serviceItem = serialNumberService.getByJdbc("T_WSBS_SERIALNUMBER",
                    new String[] { "SERIAL_ID" }, new Object[] { entityId });
            request.setAttribute("serialNumber", serviceItem);
        }
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        List<Map<String, Object>> list = dictionaryService.findByTypeCode("SerialParameter");
        request.setAttribute("serialParam", list);
        return new ModelAndView("wsbs/serialnumber/serialNumberInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SERIAL_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            SysUser sysUser = AppUtil.getLoginUser();
            variables.put("SSBMBM", sysUser.getDepCode());
        }
        String initSeq = request.getParameter("INITSEQ");
        if (StringUtils.isEmpty(initSeq)) {
            variables.put("INITSEQ", 1);
        }
        String recordId = serialNumberService.saveOrUpdate(variables, "T_WSBS_SERIALNUMBER", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的编号配置记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的编号配置记录", SysLogService.OPERATE_TYPE_ADD);
        }
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
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serialNumberService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/serialnumber/serialNumberView");
    }

    /**
     * 
     * 
     * 加载编号选择器信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String, Object>> list = serialNumberService.findSerialNumbers(filter);
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getPingYin((String) map.get("SERIAL_NAME")));
        }
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("SERIAL_ID", "");
            map.put("SERIAL_NAME", "请选择编号配置");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 序号选择器
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String serialNumberIds = request.getParameter("serialNumberIds");
        String serialNumberNames = request.getParameter("serialNumberNames");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("serialNumberIds", serialNumberIds);
        request.setAttribute("serialNumberNames", serialNumberNames);
        return new ModelAndView("wsbs/serialnumber/serialNumberSelector");
    }

}