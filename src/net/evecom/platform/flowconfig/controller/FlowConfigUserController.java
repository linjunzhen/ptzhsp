/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowconfig.service.FlowConfigTypeService;
import net.evecom.platform.flowconfig.service.FlowConfigUserService;
import net.evecom.platform.flowconfig.service.FlowTemplateService;
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
 * 描述  工程建设预审环节审核人员配置Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowConfigUserController")
public class FlowConfigUserController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowConfigUserController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowConfigUserService flowConfigUserService;   
    /**
     * 引入Service
     */
    @Resource
    private FlowTemplateService flowTemplateService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("flowconfig/flowUser/list");
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        flowConfigUserService.remove("T_FLOW_CONFIG_USER","ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 工程建设预审环节审核人员配置记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  flowConfigUser = flowConfigUserService.getByJdbc("T_FLOW_CONFIG_USER",
                    new String[]{"ID"},new Object[]{entityId});
            request.setAttribute("flowConfigUser", flowConfigUser);
        }
        return new ModelAndView("flowconfig/flowConfigUser/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> flowConfigUser = flowConfigUserService.getByJdbc("T_FLOW_CONFIG_USER",
                new String[] { "STAGE_ID", "ITEM_ID" },
                new Object[] { variables.get("STAGE_ID"), variables.get("ITEM_ID") });
        if(null != flowConfigUser && flowConfigUser.size()>0){
            entityId = flowConfigUser.get("ID").toString();
        }
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = flowConfigUserService.saveOrUpdate(variables, "T_FLOW_CONFIG_USER", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 工程建设预审环节审核人员配置记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 工程建设预审环节审核人员配置记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    

    /**
     * 
     * 描述：构建类型树
     * @author Rider Chen
     * @created 2020年9月7日 下午3:13:20
     * @param request
     * @param response
     * @throws IOException
     * @see net.evecom.platform.base.controller.BaseController#tree(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> typeList = flowConfigUserService.findAllByTableName("T_FLOW_CONFIG_TYPE");
        List<Map<String, Object>> topList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : typeList) {
            // 获取ID值
            String id = map.get("TYPE_ID").toString();
            // 获取NAME值
            String name = (String) map.get("TYPE_NAME");

            map.put("id", id);
            map.put("name", name);
            
            /*
             * List<Map<String, Object>> stageList = flowConfigUserService.getAllByJdbc("T_FLOW_CONFIG_STAGE",
                    new String[] { "TYPE_ID" }, new Object[] { id });
             */
            filter.addFilter("Q_T.TYPE_ID_EQ", id);
            List<Map<String, Object>> stageList = flowTemplateService.findStageBySqlFilter(filter);
            //map.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> map1 : stageList) {
                // 获取ID值
                String stageId = map1.get("STAGE_ID").toString();
                // 获取NAME值
                String stageName = map1.get("NAME").toString();
                map1.put("id", stageId);
                map1.put("name", stageName);
                map1.put("pname", name);
                //map1.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                children.add(map1);
            }
            if (children.size() > 0) {
                map.put("children", children);
            }
            topList.add(map);

        }
        json = JSON.toJSONString(topList);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();

    }
}

