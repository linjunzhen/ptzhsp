/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hd.service.WyjcService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 描述 我要纠错Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/wyjcController")
public class WyjcController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WyjcController.class);
    /**
     * 引入Service
     */
    @Resource
    private WyjcService wyjcService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * sysUserService
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
        wyjcService.remove("T_HD_ERROR", "ERROR_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 我要纠错记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hd/wyjc/list");
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
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> wyjc = wyjcService.getByJdbc("T_HD_ERROR", new String[] { "ERROR_ID" },
                    new Object[] { entityId });
            request.setAttribute("wyjc", wyjc);
        }
        return new ModelAndView("hd/wyjc/info");
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
        String entityId = request.getParameter("ERROR_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        SysUser sysUser = AppUtil.getLoginUser();
        String REPLY_CONTENT = request.getParameter("REPLY_CONTENT");
        if (StringUtils.isNotEmpty(REPLY_CONTENT)) {// 处理情况
            variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("REPLY_FLAG", "1");
            variables.put("REPLY_USER", sysUser.getUserId());
        }
        String recordId = wyjcService.saveOrUpdate(variables, "T_HD_ERROR", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 我要纠错记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 我要纠错记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
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
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = wyjcService.findBySqlFilter(filter, variables);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
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
    @RequestMapping("/saveError")
    public void saveError(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("ERROR_ID");
        String errorType = request.getParameter("ERROR_TYPE");

        String vcode = request.getParameter("vcode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);// 验证码
        if (StringUtils.isNotEmpty(vcode) && StringUtils.isNotEmpty(kaRandCode) && vcode.equals(kaRandCode)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if (StringUtils.isEmpty(entityId)) {
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            wyjcService.saveOrUpdate(variables, "T_HD_ERROR", entityId);
            result.put("msg", "提交成功,您提交的问题将在10个工作日内办理");
            result.put("success", true);
            StringBuffer content = new StringBuffer("");
            content.append("您好,有一份网站纠错信息，标题为“")
            .append(request.getParameter("ERROR_TITLE")).append("”");
            content.append(",请您及时处理！");
            if (StringUtils.isNotEmpty(errorType) && "网页错误".equals(errorType)) {
                // 我要纠错角色用户
                List<Map<String, Object>> wyjcUserList = sysUserService.findUsersByRoleCode("wyjc");
                if (null != wyjcUserList && wyjcUserList.size() > 0) {
                    for (Map<String, Object> user : wyjcUserList) {
                        String mobile = (String) user.get("MOBILE");
                        String isAcceptMsg = (String) user.get("IS_ACCEPTMSG");
                        if (StringUtils.isNotEmpty(mobile) && "1".equals(isAcceptMsg)) {
                            log.info("发送号码:" + mobile + ",的内容是:" + content.toString());
                            SendMsgUtil.saveSendMsg(content.toString(), mobile);
                        }
                    }
                }
            } else {
                // 我要建议角色用户
                List<Map<String, Object>> wyjyUserList = sysUserService.findUsersByRoleCode("wyjy");
                if (null != wyjyUserList && wyjyUserList.size() > 0) {
                    for (Map<String, Object> user : wyjyUserList) {
                        String mobile = (String) user.get("MOBILE");
                        String isAcceptMsg = (String) user.get("IS_ACCEPTMSG");
                        if (StringUtils.isNotEmpty(mobile) && "1".equals(isAcceptMsg)) {
                            log.info("发送号码:" + mobile + ",的内容是:" + content.toString());
                            SendMsgUtil.saveSendMsg(content.toString(), mobile);
                        }
                    }
                }
            }
        } else {
            result.put("msg", "验证码填写错误!");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     *
     * 描述  堵点难点问题征集前台提交
     *
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveQuestion")
    public void saveQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("ERROR_ID");
        String vcode = request.getParameter("vcode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);// 验证码
        if (StringUtils.isNotEmpty(vcode) && StringUtils.isNotEmpty(kaRandCode) && vcode.equals(kaRandCode)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            //查询编码
            String tel=StringUtil.getString(variables.get("QUESTION_PHONE"));
            Random ra =new Random();
            int ranodom=ra.nextInt(1000);
            String questionCode =tel+ranodom;
            variables.put("QUESTION_CODE",questionCode);
            wyjcService.saveOrUpdate(variables, "T_HD_QUESTION", entityId);
            result.put("msg", questionCode);
            result.put("success", true);
        } else {
            result.put("msg", "验证码填写错误!");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 描述:查询疑点难点问题列表控制器
     *
     * @author Madison You
     * @created 2019/8/5 8:50:00
     * @param
     * @return
     */
    @RequestMapping("/findQuestionList")
    public void findQuestionList(HttpServletRequest request , HttpServletResponse response ){
        SqlFilter sqlFilter = new SqlFilter(request);
        List<Map<String,Object>> list = wyjcService.findQuestionList(sqlFilter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:查询疑点难点问题详细信息
     *
     * @author Madison You
     * @created 2019/8/5 14:56:00
     * @param
     * @return
     */
    @RequestMapping("/findQuestionDetail")
    public ModelAndView findQuestionDetail(HttpServletRequest request , HttpServletResponse response){
        SqlFilter sqlFilter = new SqlFilter(request);
        List<Map<String, Object>> list = wyjcService.findQuestionList(sqlFilter);
        request.setAttribute("question",list.get(0));
        return new ModelAndView("website/hd/wtzjDetail");
    }



}
