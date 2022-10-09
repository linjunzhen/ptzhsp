/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.evecom.platform.hd.service.LetterService;
import net.evecom.platform.system.model.SysUser;
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
 * 描述 写信求诉Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/letterController")
public class LetterController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(LetterController.class);
    /**
     * 引入Service
     */
    @Resource
    private LetterService letterService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hd/letter/list");
    }

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
        Map<String, Object> variables=new HashMap<String, Object>();
        String[] colnames=selectColNames.split(",");
        for (int i = 0; i < colnames.length; i++) {
            variables.put("LETTER_ID", colnames[i]);
            variables.put("STATUS", "-1");
            letterService.saveOrUpdate(variables, "T_HD_LETTER", colnames[i]);
        }
        //letterService.remove("T_HD_LETTER", "LETTER_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 写信求诉记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
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
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list;
        list = letterService.findBySqlFilter(filter, " where (T.STATUS IS NULL OR T.STATUS=1)");
        //if ("__ALL".equals(resKey)||roleCodes.contains("XSQXGLY")) {// 判断是否超级管理员
        //    list = letterService.findBySqlFilter(filter, " where (T.STATUS IS NULL OR T.STATUS=1)");
        //} else {
        //    filter.addFilter("Q_T.LETTER_DEPTID_=", sysUser.getDepId());
        //    list = letterService.findBySqlFilter(filter, "where (T.STATUS IS NULL OR T.STATUS=1)");
        //}

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 办事列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/xsqxPagelist")
    public void xsqxPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String code = request.getParameter("LETTER_CODE");
        String title= request.getParameter("LETTER_TITLE");
        Map<String, Object> mapList = letterService.findfrontList(page, rows, code,title);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     *
     * 描述 办事统计
     *
     * @author Faker Li
     * @created 2015年12月2日 下午17:21:24
     * @param request
     * @param response
     */
    @RequestMapping("/sqtj")
    public void sqtj(HttpServletRequest request, HttpServletResponse response) {
        // 获取流程实例信息
        Map<String, Object> sqtj = new HashMap<String, Object>();
        int sqTotal = letterService.getAllSqNum("");
        int sqReplyTotal = letterService.getAllSqNum("1");
        sqtj.put("sqTotal", sqTotal);
        sqtj.put("sqReplyTotal", sqReplyTotal);
        JsonUtil.printJson(response, sqtj);
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
        //SqlFilter filter = new SqlFilter(request);
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> letter = null;
            /*List<Map<String, Object>> list = letterService.findBySqlFilter(filter, " where LETTER_ID='" + entityId
                    + "'");
            if (null != list && list.size() == 1) {
                letter = list.get(0);
            }*/
            letter = letterService.getByJdbc("T_HD_LETTER", new String[]{"LETTER_ID"}, new Object[]{entityId});
            request.setAttribute("letter", letter);
        }
        return new ModelAndView("hd/letter/info");
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
        String entityId = request.getParameter("LETTER_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String letterCode = letterService.getNextLetterCode(variables);
            variables.put("LETTER_CODE", letterCode);// 获取信件编号
            int letterPwd = StringUtil.getRandomIntNumber(899999, 100000);
            variables.put("LETTER_PWD", letterPwd);// 产生随机密码
            j.setJsonString("提交成功,信件编号：" + letterCode + " 查询密码：" + letterPwd);
        }
        SysUser sysUser = AppUtil.getLoginUser();
        String REPLY_CONTENT = request.getParameter("REPLY_CONTENT");
        String status = request.getParameter("AUDIT_STATUS");
        if (StringUtils.isNotEmpty(REPLY_CONTENT) && StringUtils.isNotEmpty(status) && status.equals("0")) {// 回复
            variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("REPLY_FLAG", "1");
            variables.put("REPLY_USER", sysUser.getUserId());
        }
        String recordId = letterService.saveOrUpdate(variables, "T_HD_LETTER", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 写信求诉记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 写信求诉记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveLetter")
    public void saveLetter(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("LETTER_ID");

        String type = request.getParameter("DEPT_OR_ITEM_TYPE");
        String itemId = request.getParameter("LETTER_ITEMID");

        String vcode = request.getParameter("vcode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);// 验证码
        if (StringUtils.isNotEmpty(vcode) && StringUtils.isNotEmpty(kaRandCode) && vcode.equals(kaRandCode)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            String letterCode = letterService.getNextLetterCode(variables);
            int letterPwd = StringUtil.getRandomIntNumber(899999, 100000);
            if (StringUtils.isEmpty(entityId)) {
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                variables.put("LETTER_CODE", letterCode);// 获取信件编号
                variables.put("LETTER_PWD", letterPwd);// 产生随机密码

                if (null != type && "0".equals(type)) {
                    Map<String, Object> serviceItem = letterService.getByJdbc("T_WSBS_SERVICEITEM",
                            new String[] { "ITEM_ID" }, new Object[] { itemId });
                    if (null != serviceItem) {
                        String deptCode = serviceItem.get("SSBMBM") == null ? "" : serviceItem.get("SSBMBM").toString();
                        if (StringUtils.isNotEmpty(deptCode)) {
                            Map<String, Object> department = letterService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                                    new String[] { "DEPART_CODE" }, new Object[] { deptCode });
                            if (null != department) {
                                variables.put("LETTER_DEPTID", department.get("DEPART_ID"));
                                variables.put("LETTER_DEPT", department.get("DEPART_NAME"));
                            }
                        }
                    }
                }
            }
            letterService.saveOrUpdate(variables, "T_HD_LETTER", entityId);
            result.put("msg", "提交成功,信件编号：" + letterCode + " 查询密码：" + letterPwd);
            result.put("success", true);

        } else {
            result.put("msg", "验证码填写错误!");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryLetter")
    public void queryLetter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String letterCode = request.getParameter("LETTER_CODE");
        String letterPwd = request.getParameter("LETTER_PWD");
        if (StringUtils.isNotEmpty(letterCode) && !letterCode.equals("undefined") && StringUtils.isNotEmpty(letterPwd)
                && !letterPwd.equals("undefined")) {
            Map<String, Object> letter = letterService.getByJdbc("T_HD_LETTER", new String[] { "LETTER_CODE",
                    "LETTER_PWD" }, new Object[] { letterCode, letterPwd });
            if (null != letter) {
                result.put("msg", "验证成功");
                result.put("success", true);
            } else {
                result.put("msg", "验证失败,信件编号或查询密码错误！");
                result.put("success", false);
            }
        } else {
            result.put("msg", "请填写信件编号或查询密码！");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String LETTER_CODE = request.getParameter("LETTER_CODE");
        String LETTER_PWD = request.getParameter("LETTER_PWD");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> letter = letterService.getByJdbc("T_HD_LETTER", new String[] { "LETTER_ID" },
                    new Object[] { entityId });
            request.setAttribute("letter", letter);
        } else if (StringUtils.isNotEmpty(LETTER_CODE) && !LETTER_CODE.equals("undefined")
                && StringUtils.isNotEmpty(LETTER_PWD) && !LETTER_PWD.equals("undefined")) {

            Map<String, Object> letter = letterService.getByJdbc("T_HD_LETTER", new String[] { "LETTER_CODE",
                    "LETTER_PWD" }, new Object[] { LETTER_CODE, LETTER_PWD });
            request.setAttribute("letter", letter);
        }
        return new ModelAndView("website/hd/xsqxDetail");
    }
    
}
