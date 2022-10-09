/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.test.util.UserModel;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.ShtzService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.SwbInterfaceService;

/**
 * 
 * 描述
 * 
 * @author Rider Chen
 * @created 2015-12-28 下午03:57:20
 */
@Controller
@RequestMapping("/shtzController")
public class ShtzController extends BaseController {

    /**
     * 引入Service
     */
    @Resource
    private SwbInterfaceService swbInterfaceService;

    /**
     * 引入Service
     */
    @Resource
    private ShtzService shtzService;

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-28 下午04:04:21
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String projectCode = request.getParameter("projectCode");
        String exeid = request.getParameter("exeid");
        request.setAttribute("projectCode", projectCode);
        request.setAttribute("exeid", exeid);
        return new ModelAndView("bsdt/applyform/approvalItemInfo");
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
        String EXE_ID = request.getParameter("EXE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(null==variables.get("deal_time")){//状态变更时间
            variables.put("deal_time", "");
        }
        if(null==variables.get("limit_days")){//事项办理时限
            variables.put("limit_days", "");
        }
        if(null==variables.get("plan_finish_date")){//事项计划办结时间
            variables.put("plan_finish_date", "");
        }
        if(null==variables.get("approval_number")){//批复文号
            variables.put("approval_number", "");
        }
        if(null==variables.get("validity_date")){//批复文件有效期限
            variables.put("validity_date", "");
        }
        variables.put("CREATE_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String entityId = shtzService.saveOrUpdate(variables, "T_BSFW_SWBTZXMDATA", null);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("OPER_STATUS", "0");
        resMap.put("EXE_ID", EXE_ID);
        resMap.put("DATA_TYPE", "00");
        resMap.put("OPER_TYPE", "I");
        resMap.put("HAS_ATTR", "0");
        resMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        resMap.put("INTER_TYPE", "30");
        resMap.put("TASK_ID", entityId);
        shtzService.saveOrUpdate(resMap, "T_BSFW_SWBDATA_RES", "");
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-29 上午11:33:27
     * @param request
     * @return
     */
    @RequestMapping(params = "verification")
    @ResponseBody
    public AjaxJson verification(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String projectCode = request.getParameter("projectCode");
        List<Map<String, Object>> list = shtzService.findSwbtzxmData("0", projectCode);
        if (null != list && list.size() > 0) {
            j.setSuccess(false);
            j.setMsg("信息已保存并将进行推送,是否继续保存?");
        }
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
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        String projectCode = request.getParameter("projectCode");
        List<Map> josnList;
        List<Map<String, Object>> listData;
        List<Map<String, Object>> list = shtzService.findSwbtzxmData("0", projectCode);
        if (null != list && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            String content = map.get("CONTENT").toString();
            josnList = JSON.parseArray(content, Map.class);
            this.setListToJsonString(josnList.size(), josnList, new String[] {}, JsonUtil.EXCLUDE, response);
        } else {
            listData = swbInterfaceService.getTZXMPLBLQKData(projectCode);
            this.setListToJsonString(listData.size(), listData, new String[] {}, JsonUtil.EXCLUDE, response);
        }
    }
}
