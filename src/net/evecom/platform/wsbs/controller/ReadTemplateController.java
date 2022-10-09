/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.ArrayList;
import java.util.Date;
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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ReadTemplateService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  阅办模板Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/readTemplateController")
public class ReadTemplateController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ReadTemplateController.class);
    /**
     * 引入Service
     */
    @Resource
    private ReadTemplateService readTemplateService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
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
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        readTemplateService.remove("T_WSBS_READTEMPLATE","READ_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 阅办模板记录",SysLogService.OPERATE_TYPE_DEL);
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  readTemplate = readTemplateService.getByJdbc("T_WSBS_READTEMPLATE",
                    new String[]{"READ_ID"},new Object[]{entityId});
            request.setAttribute("readTemplate", readTemplate);
        }
        return new ModelAndView("wsbs/readtemplate/readTemplateInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("READ_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = readTemplateService.saveOrUpdate(variables, "T_WSBS_READTEMPLATE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 阅办模板记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 阅办模板记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/readtemplate/readTemplateView");
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
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.create_time","desc");
        List<Map<String, Object>> list = readTemplateService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "editReadTemplate")
    public ModelAndView editReadTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            @SuppressWarnings("unchecked")
            Map<String,Object>  readTemplate = readTemplateService.getByJdbc("T_WSBS_READTEMPLATE",
                    new String[]{"READ_ID"},new Object[]{entityId});
            request.setAttribute("readTemplate", readTemplate);
        }
        return new ModelAndView("wsbs/readtemplate/editReadTemplate");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showReadTemplate")
    public ModelAndView showReadTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            @SuppressWarnings("unchecked")
            Map<String,Object>  readTemplate = readTemplateService.getByJdbc("T_WSBS_READTEMPLATE",
                    new String[]{"READ_ID"},new Object[]{entityId});
            request.setAttribute("readTemplate", readTemplate);
        }
        return new ModelAndView("wsbs/readtemplate/showReadTemplate");
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String readIds = request.getParameter("readIds");
        if(StringUtils.isNotEmpty(readIds)&&!readIds.equals("undefined")){
            request.setAttribute("readIds", readIds);
        }
        return new ModelAndView("wsbs/readtemplate/readTemplateSelector");
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
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String readIds = request.getParameter("readIds");
        List<Map<String, Object>> list = readTemplateService.findByReadId(readIds);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
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
    public void selectedPrint(HttpServletRequest request,
            HttpServletResponse response) {
        String itemCode = request.getParameter("ITEM_CODE");
        String exeId = request.getParameter("EXE_ID");
        //String username = request.getParameter("username");
        String CUR_STEPNAMES = request.getParameter("CUR_STEPNAMES");//环节
//        String BUS_TABLENAME = request.getParameter("BUS_TABLENAME");//业务表名
        List<Map<String, Object>> taskInfos = serviceItemService.getAllByJdbc("JBPM6_TASK", 
                new String[] { "EXE_ID","TASK_NODENAME" },
                new Object[] { exeId,CUR_STEPNAMES });
        //String TASK_NODENAME="";
        String TASK_STATUS="";
        if (taskInfos.size()>0) {//在办状态的记录为0条
            /*TASK_NODENAME = taskInfos.get(0).get("TASK_NODENAME")==null?"":
                taskInfos.get(0).get("TASK_NODENAME").toString();*/
            TASK_STATUS = taskInfos.get(0).get("TASK_STATUS")==null?"":
                taskInfos.get(0).get("TASK_STATUS").toString();//所处状态
        }
        Map<String, Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", 
                new String[] { "ITEM_CODE" },new Object[] { itemCode });
        Map<String, Object> readInfo = this.serviceItemService
                .getBindReadIdANdNames((String)serviceItem.get("ITEM_ID"));
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        String readIds = (String)readInfo.get("READ_IDS");
        if(StringUtils.isNotEmpty(readIds)){
            list =  readTemplateService.findByReadId(readIds);
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : list) {
            String limitStatus = map.get("LIMIT_STATUS")==null?"":
                map.get("LIMIT_STATUS").toString();
            String limitNodename = map.get("LIMIT_NODENAME")==null?"":
                map.get("LIMIT_NODENAME").toString();
            Boolean isNeedStatus = true;
            Boolean isNeedStepNames = true;
//            String readName = map.get("READ_NAME")==null?"":
//                map.get("READ_NAME").toString();
//                System.out.println(readName);
            String readId = map.get("READ_ID")==null?"":
                map.get("READ_ID").toString();
            if (taskInfos.size()==0) {
                isNeedStatus = false;
                isNeedStepNames = false;
//                isNeedStatus = true;
//                isNeedStepNames = true;
//                String readName = map.get("READ_NAME")==null?"":
//                    map.get("READ_NAME").toString();
//                if (readName.equals("窗口受理通知单")) {
//                    System.out.println(readId);
//                    isNeedStatus = true;
//                    isNeedStepNames = true;
//                }
                //办结后仅两单据可以打印
                if (readId.equals("402881ae52339704015233c1c3540018")
                        ||readId.equals("402881ae52339704015233c94a09001e"
                                )) {
                    isNeedStatus = true;
                    isNeedStepNames = true;
                }
            }
            // 是否展示电子签证按钮
            String isSignButton = "false";
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "isSignButton", readId });
            if("是".equals(StringUtil.getValue(dictionary, "DIC_NAME"))) {
                isSignButton = "true";
            }
            map.put("IS_SIGN_BUTTON", isSignButton);
            if (StringUtils.isNotEmpty(limitStatus)) {
                String[] limitStatuses = limitStatus.split(",");
                for (String string : limitStatuses) {
                    if (TASK_STATUS.equals(string)) {
                        isNeedStatus = false;
                    }
                }
            }
            if (StringUtils.isNotEmpty(limitNodename)) {
                String[] limitNodenames = limitNodename.split(",");
                for (String string : limitNodenames) {
                    if (CUR_STEPNAMES.equals(string)) {
                        isNeedStepNames = false;
                    }
                }
            }
            if (isNeedStatus&&isNeedStepNames) {
                returnList.add(map);
            }

            /*判断是否超过打印次数*/
            boolean isOverLimit = false;
            int limitCount = Integer.parseInt(map.get("LIMIT_COUNT").toString());
            int count = readTemplateService.findLimitCountByReadName((String) map.get("READ_NAME"));
            if (limitCount != 0) {
                if (count >= limitCount) {
                    isOverLimit = true;
                }
            }
            map.put("isOverLimit", isOverLimit);

        }
        this.setListToJsonString(returnList.size(), returnList,
                null, JsonUtil.EXCLUDE, response);
    }
    
    @RequestMapping("/limitStatusSelector")
    public ModelAndView limitStatusSelector(HttpServletRequest request) {
        String readIds = request.getParameter("readIds");
        if(StringUtils.isNotEmpty(readIds)&&!readIds.equals("undefined")){
            request.setAttribute("readIds", readIds);
        }
        return new ModelAndView("wsbs/readtemplate/limitStatusSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitStatusDatagrid")
    public void limitStatusDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.TYPE_CODE_EQ", "LimitStatus");
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitStatusSelected")
    public void limitStatusSelected(HttpServletRequest request,
            HttpServletResponse response) {
        String readIds = request.getParameter("readIds");
        List<Map<String, Object>> list = dictionaryService.findByDicIds(readIds);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    
    @RequestMapping("/limitNodeNameSelector")
    public ModelAndView limitNodeNameSelector(HttpServletRequest request) {
        String readIds = request.getParameter("readIds");
        if(StringUtils.isNotEmpty(readIds)&&!readIds.equals("undefined")){
            request.setAttribute("readIds", readIds);
        }
        return new ModelAndView("wsbs/readtemplate/limitNodeNameSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitNodeNameDatagrid")
    public void limitNodeNameDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.TYPE_CODE_EQ", "LimitNodeName");
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitNodeNameSelected")
    public void limitNodeNameSelected(HttpServletRequest request,
            HttpServletResponse response) {
        String readIds = request.getParameter("readIds");
        List<Map<String, Object>> list = dictionaryService.findByDicIds(readIds);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
}

