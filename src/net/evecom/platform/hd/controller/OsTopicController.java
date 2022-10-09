/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hd.service.OsOptionService;
import net.evecom.platform.hd.service.OsQuestionService;
import net.evecom.platform.hd.service.OsTicketService;
import net.evecom.platform.hd.service.OsTopicService;
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
 * 描述 网上调查主题Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/osTopicController")
public class OsTopicController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(OsTopicController.class);
    /**
     * 引入Service
     */
    @Resource
    OsTopicService osTopicService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 引入Service
     */
    @Resource
    private OsQuestionService osQuestionService;

    /**
     * 引入Service
     */
    @Resource
    private OsOptionService osOptionService;

    /**
     * 引入Service
     */
    @Resource
    private OsTicketService osTicketService;

    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hd/os/list");
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
            variables.put("TOPICID", colnames[i]);
            variables.put("STATUS", "-1");
            osTopicService.saveOrUpdate(variables, "T_HD_OS_TOPIC", colnames[i]);
        }
        //osTopicService.remove("T_HD_OS_TOPIC", "TOPICID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 网上调查主题记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> osTopic = osTopicService.getByJdbc("T_HD_OS_TOPIC", new String[] { "TOPICID" },
                    new Object[] { entityId });
            request.setAttribute("osTopic", osTopic);
        }
        return new ModelAndView("hd/os/info");
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
        filter.addSorted("T.STATE", "asc");
        filter.addSorted("T.STARTDATE", "desc");
        List<Map<String, Object>> list = osTopicService.findBySqlFilter(filter,
                "where (T.STATUS IS NULL OR T.STATUS=1)");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
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
        String entityId = request.getParameter("TOPICID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = osTopicService.saveOrUpdate(variables, "T_HD_OS_TOPIC", entityId, "S_HD_OS_TOPIC");
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 网上调查主题记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 网上调查主题记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * easyui AJAX请求数据 用户中心我的咨询列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = osTopicService.findfrontList(page, rows);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-3 上午10:27:34
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "wsdc")
    public ModelAndView wsdc(HttpServletRequest request) {
        String topicid = request.getParameter("topicid");
        String type = request.getParameter("type");
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.SEQNUM", "desc");
        filter.addFilter("Q_T.TOPICID_=", topicid);
        List<Map<String, Object>> questionList = osQuestionService.findBySqlFilter(filter, "");
        for (Map<String, Object> questionMap : questionList) {
            String questionid = questionMap.get("QUESTIONID").toString();
            SqlFilter optionFilter = new SqlFilter(request);
            optionFilter.addSorted("T.SEQNUM", "desc");
            optionFilter.addFilter("Q_T.QUESTIONID_=", questionid);
            List<Map<String, Object>> optionList = osOptionService.findBySqlFilter(optionFilter, "");
            int count = 0;
            for (Map<String, Object> optionMap : optionList) {
                int counttype = Integer.parseInt(optionMap.get("COUNTTYPE").toString());
                int tickets = Integer.parseInt(optionMap.get("TICKETS").toString());
                int appendtickets = Integer.parseInt(optionMap.get("APPENDTICKETS").toString());
                if (counttype == 0) {
                    count += tickets;
                    optionMap.put("TICKETS", tickets);
                } else if (counttype == 1) {
                    count += appendtickets;
                    optionMap.put("TICKETS", appendtickets);
                } else {
                    count += tickets + appendtickets;
                    optionMap.put("TICKETS", tickets + appendtickets);
                }
            }
            questionMap.put("count", count);
            questionMap.put("options", optionList);
        }
        if (StringUtils.isNotEmpty(topicid) && !topicid.equals("undefined")) {
            Map<String, Object> osTopic = osTopicService.getByJdbc("T_HD_OS_TOPIC", new String[] { "TOPICID" },
                    new Object[] { topicid });
            String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            String startdate = osTopic.get("STARTDATE").toString();
            String enddate = osTopic.get("ENDDATE").toString();
            if (DateTimeUtil.strToDate(date).compareTo(DateTimeUtil.strToDate(startdate)) == -1) {
                osTopic.put("DATETYPE", 0);
            } else if (DateTimeUtil.strToDate(date).compareTo(DateTimeUtil.strToDate(enddate)) == 1) {
                osTopic.put("DATETYPE", 2);
            } else {
                osTopic.put("DATETYPE", 1);
            }

            request.setAttribute("osTopic", osTopic);
        }
        request.setAttribute("osQuestions", questionList);
        request.setAttribute("type", type);
        return new ModelAndView("website/hd/wsdcShow");
    }

    /**
     * 
     * 描述 前台添加投诉监督
     * 
     * @author Rider Chen
     * @created 2015-11-20 下午04:32:26
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveWsdc")
    public void saveWsdc(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        String topicId = request.getParameter("topicId");
        String vcode = request.getParameter("vcode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);// 验证码
        if (StringUtils.isNotEmpty(vcode) && StringUtils.isNotEmpty(kaRandCode) && vcode.equals(kaRandCode)) {
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String ip = request.getRemoteAddr();
            if (StringUtils.isNotEmpty(topicId) && !topicId.equals("undefined")) {
                SqlFilter filter = new SqlFilter(request);
                filter.addFilter("Q_T.TOPICID_=", topicId);
                List<Map<String, Object>> list = osQuestionService.findBySqlFilter(filter, "");
                for (Map<String, Object> osQuestion : list) {
                    if ("checkbox".equals(osQuestion.get("TYPE"))) {
                        String[] values = request.getParameterValues("Item_" + osQuestion.get("QUESTIONID"));
                        if (values == null)
                            continue;
                        for (String optionId : values) {
                            Map<String, Object> osOption = osOptionService.getByJdbc("T_HD_OS_OPTION",
                                    new String[] { "OPTIONID" }, new Object[] { optionId });
                            if (osOption != null) {
                                saveOptionOrTicket(time, ip, osQuestion, optionId, osOption);
                            }
                        }
                    } else if ("select".equals(osQuestion.get("TYPE"))) {
                        String optionId = request.getParameter("Item_" + osQuestion.get("QUESTIONID"));
                        Map<String, Object> osOption = osOptionService.getByJdbc("T_HD_OS_OPTION",
                                new String[] { "OPTIONID" }, new Object[] { optionId });
                        if (osOption != null) {
                            saveOptionOrTicket(time, ip, osQuestion, optionId, osOption);
                        }
                    } else if ("input".equals(osQuestion.get("TYPE")) || "textarea".equals(osQuestion.get("TYPE"))) {
                        Map<String, Object> osOption = osOptionService.getByJdbc("T_HD_OS_OPTION",
                                new String[] { "QUESTIONID" }, new Object[] { osQuestion.get("QUESTIONID") });
                        String values = request.getParameter("Item_" + osQuestion.get("QUESTIONID"));
                        if (osOption != null) {
                            osQuestion.put("CONTENT", values);
                            saveOptionOrTicket(time, ip, osQuestion, osOption.get("OPTIONID").toString(), osOption);
                        }
                    }
                }
                result.put("msg", "保存成功");
                result.put("success", true);
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
     * 描述 投票记录保存
     * 
     * @author Rider Chen
     * @created 2015-12-3 下午03:08:55
     * @param time
     * @param ip
     * @param osQuestion
     * @param optionId
     * @param osOption
     */
    @SuppressWarnings("unchecked")
    private void saveOptionOrTicket(String time, String ip, Map<String, Object> osQuestion, String optionId,
            Map<String, Object> osOption) {
        int tickets = 0;
        if (StringUtils.isNotEmpty(osOption.get("TICKETS").toString())) {
            tickets = Integer.parseInt(osOption.get("TICKETS").toString()) + 1;
        } else {
            tickets = tickets + 1;
        }
        osOption.put("TICKETS", tickets);
        // 保存投票统计
        osOptionService.saveOrUpdate(osOption, "T_HD_OS_OPTION", optionId, "S_HD_OS_OPTION");
        Map<String, Object> osTicket = new HashMap<String, Object>();
        osTicket.put("OPTIONID", optionId);
        osTicket.put("CONTENT", osOption.get("CONTENT"));
        osTicket.put("TYPE", osQuestion.get("TYPE"));
        osTicket.put("SUBMITDATE", time);
        osTicket.put("IP", ip);
        // 保存投票记录
        osTicketService.saveOrUpdate(osTicket, "T_HD_OS_TICKET", null, "S_HD_OS_TICKET");
    }
}
