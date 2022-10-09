/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.BspjService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  办事评价Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/bspjController")
public class BspjController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BspjController.class);
    /**
     * 引入Service
     */
    @Resource
    private BspjService bspjService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
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
        bspjService.remove("T_WSBS_BSPJ","PJ_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 办事评价记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
                    new String[]{"PJ_ID"},new Object[]{entityId});
            request.setAttribute("bspj", bspj);
        }
        return new ModelAndView("wsbs/bjpj/info");
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
        String entityId = request.getParameter("PJ_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = bspjService.saveOrUpdate(variables, "T_WSBS_BSPJ", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 办事评价记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 办事评价记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/pjxx")
    public ModelAndView pjxx(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        String itemCode = request.getParameter("itemCode");
        Map<String,Object>  bspj = new HashMap<String, Object>();
        bspj.put("EXE_ID", exeId);
        //bspj.put("YHZH", AppUtil.getLoginMember().get("YHZH"));
        bspj.put("FWSXBM",itemCode);
        request.setAttribute("bspj", bspj);
        return new ModelAndView("website/yhzx/bjpj");
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "savePjxx")
    @ResponseBody
    public AjaxJson savePjxx(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("PJ_ID");
        String exeId = request.getParameter("EXE_ID");
        String yhzh = (String)AppUtil.getLoginMember().get("YHZH");
        Map<String,Object>  bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
                new String[]{"EXE_ID","YHZH"},new Object[]{exeId,yhzh});
        if(bspj==null){
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            variables.put("YHZH", yhzh);
            if(StringUtils.isEmpty(entityId)){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            bspjService.saveOrUpdate(variables, "T_WSBS_BSPJ", entityId);
            j.setMsg("评价成功");
        }else{
            j.setSuccess(false);
            j.setMsg("此办事记录已经评价!");
        }
        return j;
    }
    /**
     * easyui AJAX请求数据 用户中心我的评价列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params ="pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows"); 
        String itemCode = request.getParameter("itemCode");
        Map<String, Object> mapList;
        if(StringUtils.isNotEmpty(itemCode)){
            mapList = bspjService.findfrontList(page, rows, itemCode);
        }else{
            mapList = bspjService.findfrontList(page, rows);
        }
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 用户中心我的评价列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/bsznPagelist")
    public void bsznPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows"); 
        String itemCode = request.getParameter("itemCode");
        Map<String, Object> mapList;
        if(StringUtils.isNotEmpty(itemCode)){
            mapList = bspjService.findfrontList(page, rows, itemCode);
        }else{
            mapList = bspjService.findfrontList(page, rows);
        }
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述：评价明细表
     * @author Water Guo
     * @created 2017-3-27 下午4:30:34
     * @param request
     * @return
     */
    @RequestMapping(params = "pjmxb")
    public ModelAndView pjmxb(HttpServletRequest request) {
        return new ModelAndView("statis/pjmxb");
    }
    /**
     * 
     * 描述：评价列表
     * @author Water Guo
     * @created 2017-3-27 下午5:07:06
     * @param request
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","DESC");
        List<Map<String, Object>> list = bspjService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:获取省网评价信息
     *
     * @author Madison You
     * @created 2019/10/21 21:56:00
     * @param
     * @return
     */
    @RequestMapping("/getSwpjMessage")
    @ResponseBody
    public Map<String, Object> getSwpjMessage(HttpServletRequest request, HttpServletResponse response)
            throws RemoteException, ServiceException {
        String exeId = request.getParameter("EXE_ID");
        HashMap<String, Object> returnMap = new HashMap<>();
        String currentStep = null;
        String swbItemCode = null;
        String swbItemId = null;
        if (exeId != null) {
            Map<String, Object> record = bspjService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            /*判断省网的评价结果链接是否为空*/
            if (record.get("SWPJ_URL") == null) {
                /*如果当前环节为空，就是已办结*/
                currentStep = record.get("CUR_STEPNAMES") == null ? "办结" : (String) record.get("CUR_STEPNAMES");
                String itemCode = (String) record.get("ITEM_CODE");
                Map<String, Object> itemMap = bspjService.getByJdbc("T_WSBS_SERVICEITEM",
                        new String[]{"ITEM_CODE"}, new Object[]{itemCode});
                swbItemCode = (String) itemMap.get("SWB_ITEM_CODE");
                swbItemId = (String) itemMap.get("SWB_ITEM_ID");
                /*有些事项没有省网编码，就不评价*/
                if (swbItemId != null) {
                    Map<String, Object> swpjData = bspjService.findSwpjData(swbItemCode, exeId, currentStep, "1");
                    if (swpjData.get("code").equals("200")) {
                        HashMap<String, Object> linkMap = new HashMap<>();
                        linkMap.put("SWPJ_URL", swpjData.get("url"));
                        bspjService.saveOrUpdate(linkMap, "JBPM6_EXECUTION", exeId);
                        returnMap.put("status", "ypj");
                        returnMap.put("url", swpjData.get("url"));
                        return returnMap;
                    } else if (swpjData.get("code").equals("500")) {
                        Map<String, Object> swpjLink = bspjService.findSwpjLink(swbItemCode, exeId,
                                currentStep, "1", "1");
                        returnMap.put("status", "wpj");
                        returnMap.put("url", swpjLink.get("url"));
                        return returnMap;
                    }
                }
            }else {
                /*代表已评价*/
                returnMap.put("status", "ypj");
                returnMap.put("url", record.get("SWPJ_URL"));
                return returnMap;
            }
        }
        returnMap.put("status", "error");
        return returnMap;
    }
}

