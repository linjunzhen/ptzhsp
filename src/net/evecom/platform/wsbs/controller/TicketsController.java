/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.zhuozhengsoft.pageoffice.FileSaver;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.SerialNumberService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.wsbs.service.TicketsService;

/**
 * 描述 单据配置类Controller
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/ticketsController")
public class TicketsController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(TicketsController.class);
    /**
     * 引入Service
     */
    @Resource
    private TicketsService ticketsService;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
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
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;

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
        ticketsService.remove("T_WSBS_TICKETS", "TICKETS_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 单据配置类记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> tickets = ticketsService.getByJdbc("T_WSBS_TICKETS", new String[] { "TICKETS_ID" },
                    new Object[] { entityId });
            request.setAttribute("tickets", tickets);
        }
        return new ModelAndView("wsbs/tickets/ticketsInfo");
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/tickets/ticketsView");
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
        String serialres;
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("TICKETS_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = ticketsService.saveOrUpdate(variables, "T_WSBS_TICKETS", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 单据配置类记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 单据配置类记录", SysLogService.OPERATE_TYPE_ADD);
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
        List<Map<String, Object>> list = ticketsService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showTicketsTemplate")
    public ModelAndView showTicketsTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> tickets = ticketsService.getByJdbc("T_WSBS_TICKETS", new String[] { "TICKETS_ID" },
                    new Object[] { entityId });
            request.setAttribute("tickets", tickets);
        }
        return new ModelAndView("wsbs/tickets/showTicketsTemplate");
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "editTicketsTemplate")
    public ModelAndView editTicketsTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> tickets = ticketsService.getByJdbc("T_WSBS_TICKETS", new String[] { "TICKETS_ID" },
                    new Object[] { entityId });
            request.setAttribute("tickets", tickets);
        }
        return new ModelAndView("wsbs/tickets/editTicketsTemplate");
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showTickets")
    public ModelAndView showTickets(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> tickets = ticketsService.getByJdbc("T_WSBS_TICKETS", new String[] { "TICKETS_ID" },
                    new Object[] { entityId });
            request.setAttribute("tickets", tickets);
        }
        return new ModelAndView("wsbs/tickets/showTickets");
    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2015年10月14日 上午9:16:50
     * @param request
     * @param response
     * @see net.evecom.platform.base.controller.BaseController#tree(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping("/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("parentId");
        if (StringUtils.isNotEmpty(parentId) && !parentId.equals("undefined")) {
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("id", "0");
            root.put("name", "书签配置树");
            root.put("open", true);
            // root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
            root.put("PARENT_ID", "-1");
            root.put("TREE_LEVEL", 1);
            // 获取topType
            List<Map<String, Object>> toplist = dicTypeService.findByParentId(parentId);
            for (Map<String, Object> top : toplist) {
                top.put("id", "0");
                top.put("name", top.get("TYPE_NAME"));
                // top.put("icon",
                // "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(top, (String) top.get("TYPE_CODE"));
            }
            root.put("children", toplist);
            String json = JSON.toJSONString(root);
            this.setJsonString(json, response);
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2015年10月14日 上午10:05:42
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String typeCode) {
        List<Map<String, Object>> children = dictionaryService.findByTypeCode(typeCode);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("DIC_CODE"));
                child.put("name", child.get("DIC_NAME"));
                // child.put("icon",
                // "plug-in/easyui-1.4/themes/icons/folder_table.png");
            }
        }
    }

    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String ticketsIds = request.getParameter("ticketsIds");
        String ticketsNames = request.getParameter("ticketsNames");
        if (StringUtils.isNotEmpty(ticketsIds) && !ticketsIds.equals("undefined")) {
            request.setAttribute("ticketsIds", ticketsIds);
            request.setAttribute("ticketsNames", ticketsNames);
        }
        return new ModelAndView("wsbs/tickets/ticketsSelector");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request, HttpServletResponse response) {
        String ticketsIds = request.getParameter("ticketsIds");
        List<Map<String, Object>> list = ticketsService.findByTicketsId(ticketsIds);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 保存模版文件
     * 
     * @author Faker Li
     * @created 2016年1月12日 上午9:29:42
     * @param request
     * @param response
     */
    @RequestMapping("/savefile")
    public void savefile(HttpServletRequest request, HttpServletResponse response) {
        String str = "";
        String type = request.getParameter("type");
        String alias = request.getParameter("alias");
        if (StringUtils.isNotEmpty(request.getParameter("filename"))
                && !request.getParameter("filename").equals("undefined")) {
            str = request.getParameter("filename");
        } else if (StringUtils.isNotBlank(alias) && !alias.equals("undefined")) {
            str = alias + ".doc";
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            str = simpleDateFormat.format(new Date()) + ".doc";
        }
        FileSaver fs = new FileSaver(request, response);
        String filename = "";
        if (type.equals("tickets")) {
            filename = request.getSession().getServletContext().getRealPath("attachFiles//tickets//files/") + "/" + str;
        } else if (type.equals("documenttemplate")) {
            filename = request.getSession().getServletContext().getRealPath("attachFiles//documenttemplate//files/")
                    + "/" + str;
        } else if (type.equals("readtemplate")) {
            filename = request.getSession().getServletContext().getRealPath("attachFiles//readtemplate//files/") + "/"
                    + str;
        }
        fs.saveToFile(filename);
        fs.setCustomSaveResult(str);
        fs.close();
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selectedPrint")
    public void selectedPrint(HttpServletRequest request, HttpServletResponse response) {
        String itemCode = request.getParameter("ITEM_CODE");
        Map<String, Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_CODE" }, new Object[] { itemCode });
        Map<String, Object> ticketsInfo = this.serviceItemService
                .getBindTicketsIdANdNames((String) serviceItem.get("ITEM_ID"));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String ticketsIds = (String) ticketsInfo.get("TICKETS_IDS");
        if (StringUtils.isNotEmpty(ticketsIds)) {
            list = ticketsService.findByTicketsId(ticketsIds);
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
}
