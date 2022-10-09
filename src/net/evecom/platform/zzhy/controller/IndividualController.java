/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

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
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.zzhy.service.IndividualService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 个体商户Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/individualController")
public class IndividualController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(IndividualController.class);
    /**
     * 引入Service
     */
    @Resource
    private IndividualService individualService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
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
        individualService.remove("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 个体商户记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> individual = individualService.getByJdbc("T_COMMERCIAL_INDIVIDUAL",
                    new String[] { "INDIVIDUAL_ID" }, new Object[] { entityId });
            request.setAttribute("individual", individual);
        }
        return new ModelAndView("zzhy/individual/info");
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
        String entityId = request.getParameter("INDIVIDUAL_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = individualService.saveOrUpdate(variables, "T_COMMERCIAL_INDIVIDUAL", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 个体商户记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 个体商户记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到家庭成员信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJtcyxxDiv")
    public ModelAndView refreshJtcyxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/individual/jtcyxxDiv");
    }

    @RequestMapping(params = "gtmpView")
    public ModelAndView gtmpView(HttpServletRequest request) {
        return new ModelAndView("zzhy/gtmp/list");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.EXE_ID", "DESC");
        List<Map<String, Object>> list = individualService.findBySqlFilter(filter, null);
        encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 方法multiPublish
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "gtmpTestStatus")
    @ResponseBody
    public AjaxJson gtmpTestStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        int status = Integer.parseInt(request.getParameter("status"));
        if (StringUtils.isNotEmpty(selectColNames)) {
            String[] ids = selectColNames.split(",");
            for (String id : ids) {
                Map<String, Object> exeInfo = individualService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { id });
                Map<String, Object> gtxx = new HashMap<String, Object>();
                String individualId = StringUtil.getString(exeInfo.get("BUS_RECORDID"));
                gtxx.put("IS_TEST", status);
                individualService.saveOrUpdate(gtxx, "T_COMMERCIAL_INDIVIDUAL", individualId);

            }
        }
        sysLogService.saveLog("修改ID为[" + selectColNames + "]的 个体记录中的IS_TEST为[" + status + "]",
                SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("操作成功");
        return j;
    }
}
