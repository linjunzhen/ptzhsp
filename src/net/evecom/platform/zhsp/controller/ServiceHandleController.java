/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zhsp.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.ProcessArchiveService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.zhsp.service.ServiceHandleService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2015-11-23 上午9:56:29
 */
@Controller
@RequestMapping("/serviceHandleController")
public class ServiceHandleController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ServiceHandleController.class);
    /**
     * 引入Service
     */
    @Resource
    private ServiceHandleService serviceHandleService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 引入 processArchiveService
     */
    private ProcessArchiveService processArchiveService;

    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 
     * 描述 跳转审批
     * @author Danto Huang
     * @created 2015-11-23 上午10:00:52
     * @param request
     * @return
     */
    @RequestMapping(params = "toHandle")
    public ModelAndView toHandle(HttpServletRequest request) {
        String type = request.getParameter("handleType");
        String target = "";
        if (type.equals("1")) {
            target = "jbj";
        } else if (type.equals("2")) {
            target = "ptj";
        } else if (type.equals("3")) {
            target = "tsj";
        }
        return new ModelAndView("zhsp/" + target + "/handletab");
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2015-11-23 上午11:19:25
     * @param request
     * @return
     */
    @RequestMapping(params = "goNeedHandle")
    public ModelAndView goNeedHandle(HttpServletRequest request) {
        String type = request.getParameter("handleType");
        String target = "";
        if (type.equals("1")) {
            target = "jbj";
        } else if (type.equals("2")) {
            target = "ptj";
        } else if (type.equals("3")) {
            target = "tsj";
        }
        return new ModelAndView("zhsp/" + target + "/needHandle");
    }

    /**
     * 
     * 描述 待办列表datagrid
     * @author Danto Huang
     * @created 2015-11-23 下午2:44:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "needHandle")
    public void needHandle(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("E.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serviceHandleService.findNeedMeHandle(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2015-11-23 上午11:19:25
     * @param request
     * @return
     */
    @RequestMapping(params = "goHandleByMe")
    public ModelAndView goHandleByMe(HttpServletRequest request) {
        String type = request.getParameter("handleType");
        String target = "";
        if (type.equals("1")) {
            target = "jbj";
        } else if (type.equals("2")) {
            target = "ptj";
        } else if (type.equals("3")) {
            target = "tsj";
        }
        return new ModelAndView("zhsp/" + target + "/handledByMe");
    }

    /**
     * 
     * 描述 经办列表datagrid
     * @author Danto Huang
     * @created 2015-11-23 下午2:44:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "handledByMe")
    public void handledByMe(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("userAccount",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        String userAccount = sysUser.getUsername();
        List<Map<String, Object>> list = serviceHandleService.findHandledByUser(filter, userAccount);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2015-11-23 上午11:19:25
     * @param request
     * @return
     */
    @RequestMapping(params = "goHandleMonitor")
    public ModelAndView goHandleMonitor(HttpServletRequest request) {
        String type = request.getParameter("handleType");
        String target = "";
        if (type.equals("1")) {
            target = "jbj";
        } else if (type.equals("2")) {
            target = "ptj";
        } else if (type.equals("3")) {
            target = "tsj";
        }
        return new ModelAndView("zhsp/" + target + "/handleMonitor");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "handleMonitor")
    public void handleMonitor(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serviceHandleService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 历史流程查询
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午9:17:45
     * @param request
     * @param response
     */
    @RequestMapping(params = "hisHandleMonitor")
    public void hisHandleMonitor(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serviceHandleService.findHisBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 已归档流程查询
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午9:47:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "hisFiledFlowList")
    public void hisFiledFlowList(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serviceHandleService.findHisFiledFlowBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 手动归档
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 下午3:05:00
     * @param request
     * @param response
     */
    @RequestMapping(params = "processArchive")
    @ResponseBody
    public AjaxJson processArchive(HttpServletRequest request) {
        AjaxJson resultJson = new AjaxJson();
        try {
            // 根据流程exeIds做流程归档操作，前台复选框数据
            this.setFiledByExeIds(request, resultJson);
            // 根据流程结束时间做流程归档操作
            this.setFiledByDates(request, resultJson);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            resultJson.setSuccess(false);
            resultJson.setMsg("归档报错，请联系管理员！");
        }
        return resultJson;
    }

    /**
     * 描述 
     * 
     * @author Reuben Bao
     * @created 2019年7月4日 下午12:55:18
     * @param request
     * @param resultJson
     */
    @SuppressWarnings("unchecked")
    public void setFiledByExeIds(HttpServletRequest request, AjaxJson resultJson) {
        // 根据流程exeIds做流程归档操作，前台复选框数据
        String checkedItems = request.getParameter("CHECKED_ITEMS");
        StringBuffer exeIds = new StringBuffer();
        if (StringUtils.isNotEmpty(checkedItems)) {
            List<Map<String, Object>> checkList = (List<Map<String, Object>>) JSON.parse(checkedItems);
            if (checkList != null && checkList.size() > 0) {
                for (Map<String, Object> checkMap : checkList) {
                    exeIds.append("'").append(StringUtil.getValue(checkMap, "EXE_ID")).append("',");
                }
            }
            // 按选择的申请单号归档
            if (exeIds != null) {
                // 流程归档接口调用
                if (processArchiveService == null) {
                    processArchiveService = (ProcessArchiveService) AppUtil.getBean("processArchiveService");
                }
                processArchiveService
                        .processArchiveByExeId(exeIds.toString().substring(0, exeIds.toString().length() - 1));
            }
            exeIds.setLength(0);
        }
    }

    /**
     * 描述 按时间段执行流程归档操作
     * 
     * @author Reuben Bao
     * @created 2019年7月4日 下午12:55:22
     * @param request
     * @param resultJson
     */
    public void setFiledByDates(HttpServletRequest request, AjaxJson resultJson) {
        // 根据流程结束时间做流程归档操作
        String startDate = request.getParameter("START_DATE");
        String endDate = request.getParameter("END_DATE");
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            // 获取配置字典数据
            String dics = dictionaryService.findByDicCodeAndTypeCode("filedFlowRuleItem", "filedFlowRule");
            String dicDate = DateTimeUtil.dateToStr(DateTimeUtil.getNextDay(new Date(), Integer.valueOf(dics) * -1));
            if (DateTimeUtil.getDaysBetween(startDate, endDate) > 30) {
                resultJson.setSuccess(false);
                resultJson.setMsg("单次归档时间区间为30天以内！");
            } else if (StringUtils.isNotEmpty(dics) && DateTimeUtil.getDaysBetween(dicDate, endDate) > 0) {
                // 判断流程办理结束时间是否小于系统字典配置时间
                resultJson.setSuccess(false);
                resultJson.setMsg("归档结束日期需小于系统配置日期：" + dicDate);
            } else {
                // 按时间段执行流程归档操作
                this.processArchive(startDate, endDate, resultJson);
            }
        }
    }

    /**
     * 描述 按时间段执行流程归档操作
     * 
     * @author Reuben Bao
     * @created 2019年8月15日 下午6:26:00
     * @param startDate
     * @param endDate
     * @param resultJson
     */
    public void processArchive(String startDate, String endDate, AjaxJson resultJson) {
        StringBuffer exeIds = new StringBuffer();
        List<Map<String, Object>> exectionList = (List<Map<String, Object>>) this.serviceHandleService
                .getExecutionListByEndTime(startDate, endDate);
        if (exectionList != null && exectionList.size() > 0) {
            if (exectionList.size() > 1000) {
                resultJson.setSuccess(false);
                resultJson.setMsg("单次流程归档上限为1000条！");
            } else {
                // 流程归档接口调用
                if (processArchiveService == null) {
                    processArchiveService = (ProcessArchiveService) AppUtil.getBean("processArchiveService");
                }
                for (int i = 0; i < exectionList.size(); i++) {
                    Map<String, Object> itemMap = exectionList.get(i);
                    exeIds.append("'").append(StringUtil.getValue(itemMap, "EXE_ID")).append("',");
                    // 每50条执行一次数据库操作
                    if (i % 50 == 0 || (exectionList.size() - i < 50 && i == exectionList.size() - 1)) {
                        String exeIdsStr = exeIds.toString().substring(0, exeIds.toString().length() - 1);
                        processArchiveService.processArchiveByExeId(exeIdsStr);
                        exeIds.setLength(0);
                    }
                }
            }
        }
    }

    /**
     * 描述 已完结流程查询
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午2:04:09
     * @param request
     * @return
     */
    @RequestMapping(params = "goEndFlow")
    public ModelAndView goEndFlow(HttpServletRequest request) {
        return new ModelAndView("hflow/filedFlow/endFlow");
    }

    /**
     * 描述 已归档流程查询
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午2:04:18
     * @param request
     * @return
     */
    @RequestMapping(params = "goEndFiled")
    public ModelAndView goEndFiled(HttpServletRequest request) {
        return new ModelAndView("hflow/filedFlow/endFiled");
    }
}
