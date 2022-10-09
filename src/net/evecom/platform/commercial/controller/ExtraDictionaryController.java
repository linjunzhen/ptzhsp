/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.controller;

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

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.ExtraDicTypeService;
import net.evecom.platform.commercial.service.ExtraDictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 下午3:22:32
 */
@Controller
@RequestMapping("/extraDictionaryController")
public class ExtraDictionaryController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExtraDictionaryController.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * sysLogService
     */
    @Resource
    private ExtraDicTypeService extraDicTypeService;
    /**
     * extraDictionaryService
     */
    @Resource
    private ExtraDictionaryService extraDictionaryService;
    
    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("commercial/extraDictionary/list");
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
        extraDictionaryService.remove("T_COMMERCIAL_DIC","DIC_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 商事登记附加字典信息记录",SysLogService.OPERATE_TYPE_DEL);
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
        String typeId = request.getParameter("TYPE_ID");
        String typeName = request.getParameter("TYPE_NAME");
        Map<String,Object>  dictionary  = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            dictionary = extraDictionaryService.getByJdbc("T_COMMERCIAL_DIC",
                    new String[]{"DIC_ID"},new Object[]{entityId});
            typeId = (String) dictionary.get("TYPE_ID");
            Map<String,Object> dicType = extraDictionaryService.
                    getByJdbc("T_COMMERCIAL_DICTYPE",new String[]{"TYPE_ID"},new Object[]{typeId});
            dictionary.put("TYPE_ID", typeId);
            dictionary.put("TYPE_NAME",(String)dicType.get("TYPE_NAME"));
        }else{
            dictionary.put("TYPE_ID",typeId);
            dictionary.put("TYPE_NAME",typeName);
        }
        request.setAttribute("dictionary", dictionary);
        return new ModelAndView("commercial/extraDictionary/dictionaryInfo");
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
        String entityId = request.getParameter("DIC_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            boolean isExist = extraDictionaryService.isExist(request.getParameter("TYPE_ID"), 
                    request.getParameter("DIC_CODE"));
            if(isExist){
                j.setSuccess(false);
                j.setMsg("该字典类别下已存在相同编码的字典,创建失败!");
                return j;
            }
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String typeId = (String) variables.get("TYPE_ID");
            int maxSn = extraDictionaryService.getMaxSn(typeId);
            variables.put("DIC_SN", maxSn+1);
        }
        //获取字典类别
        Map<String,Object> dicType = extraDictionaryService.getByJdbc("T_COMMERCIAL_DICTYPE",new String[]{"TYPE_ID"},
                new Object[]{variables.get("TYPE_ID")});
        variables.put("TYPE_CODE",dicType.get("TYPE_CODE"));
        extraDictionaryService.saveOrUpdate(variables, "T_COMMERCIAL_DIC", entityId);
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
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.DIC_SN","desc");
        filter.addSorted("D.CREATE_TIME","desc");
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = extraDictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 方法updateSn
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] dicIds = request.getParameterValues("dicIds[]");
        extraDictionaryService.updateSn(dicIds);
        j.setMsg("排序成功");
        return j;
    }
}
