/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
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
 * 描述  字典信息Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/dictionaryController")
public class DictionaryController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DictionaryController.class);
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * dicTypeService
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * FileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("system/dictionary/list");
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
        dictionaryService.remove("T_MSJW_SYSTEM_DICTIONARY","DIC_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 字典信息记录",SysLogService.OPERATE_TYPE_DEL);
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
            dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[]{"DIC_ID"},new Object[]{entityId});
            typeId = (String) dictionary.get("TYPE_ID");
            Map<String,Object> dicType = dicTypeService.
                    getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_ID"},new Object[]{typeId});
            dictionary.put("TYPE_ID", typeId);
            dictionary.put("TYPE_NAME",(String)dicType.get("TYPE_NAME"));
        }else{
            dictionary.put("TYPE_ID",typeId);
            dictionary.put("TYPE_NAME",typeName);
        }
        request.setAttribute("dictionary", dictionary);
        return new ModelAndView("system/dictionary/dictionaryInfo");
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
            boolean isExist = dictionaryService.isExist(request.getParameter("TYPE_ID"), 
                    request.getParameter("DIC_CODE"));
            if(isExist){
                j.setSuccess(false);
                j.setMsg("该字典类别下已存在相同编码的字典,创建失败!");
                return j;
            }
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String typeId = (String) variables.get("TYPE_ID");
            int maxSn = dictionaryService.getMaxSn(typeId);
            variables.put("DIC_SN", maxSn+1);
        }
        //获取字典类别
        Map<String,Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_ID"},
                new Object[]{variables.get("TYPE_ID")});
        variables.put("TYPE_CODE",dicType.get("TYPE_CODE"));
        dictionaryService.saveOrUpdate(variables, "T_MSJW_SYSTEM_DICTIONARY", entityId);
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
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年3月7日 下午5:30:43
     * @param request
     * @param response
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/loadData")
    public void loadData(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String typeCode = request.getParameter("typeCode");
        String orderType = request.getParameter("orderType");
        String controlId = request.getParameter("controlId");
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        String parentTypeCode = request.getParameter("parentTypeCode");
        String busType = request.getParameter("busType");
        String noScroll = request.getParameter("noScroll"); 
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(typeCode)&&StringUtils.isEmpty(busType)){
            list = dictionaryService.findList(typeCode, orderType);
        }else if(StringUtils.isNotEmpty(parentTypeCode)){
            String curTypeName = request.getParameter("typeName");
            list = dictionaryService.findList(parentTypeCode, curTypeName, orderType);
        }else if(StringUtils.isNotEmpty(busType)){
            list = dictionaryService.findDataList(busType, typeCode, orderType);
        }
        if(StringUtils.isNotEmpty(defaultEmpty)||StringUtils.isNotEmpty(defaultEmptyText)){
            Map<String,Object> map = new HashMap<String,Object>();
            String typeName = "";
            if(StringUtils.isNotEmpty(typeCode)&&StringUtils.isEmpty(busType)){
                Map<String,Object> dicType = dicTypeService.
                        getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_CODE"},new Object[]{typeCode});
                typeName= (String) dicType.get("TYPE_NAME");
            }else if(StringUtils.isNotEmpty(busType)){
                Map<String,String> texts = dicTypeService.getTextValues(busType,typeCode,"KEY");
                typeName = texts.get("KEY");
            }
            map.put("DIC_ID","");
            map.put("DIC_CODE","");
            if(StringUtils.isNotEmpty(defaultEmptyText)){
                map.put("DIC_NAME", "请选择"+defaultEmptyText);  
            }else{
                map.put("DIC_NAME", "请选择"+typeName);  
            }
            
            if(StringUtils.isNotEmpty(controlId)){
                map.put("CONTROLID", controlId);
            }
            if(StringUtils.isNotEmpty(noScroll)){
                map.put("noScroll", true);
            }
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述：
     * @author Rider Chen
     * @created 2019年12月9日 下午2:54:56
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/loadDataToDesc")
    public void loadDataToDesc(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String typeCode = request.getParameter("typeCode");
        String dicDesc = request.getParameter("dicDesc"); 
        List<Map<String,Object>> list = dictionaryService.findTwoDatasForSelect(typeCode, dicDesc);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述  加载派出所
     * @author Danto Huang
     * @created 2015-4-21 上午10:15:29
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/loadPcs")
    public void loadPcs(HttpServletRequest request,HttpServletResponse response){
        String defaultEmpty = request.getParameter("defaultEmpty");
        String typeCode = request.getParameter("typeCode");
        String orderType = request.getParameter("orderType");
        String controlId = request.getParameter("controlId");
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        String subParentCode = request.getParameter("subParentCode");
        String noScroll = request.getParameter("noScroll"); 
        List<Map<String,Object>> list;

        list = dictionaryService.findPcs(typeCode, orderType,subParentCode);
        if(StringUtils.isNotEmpty(defaultEmpty)||StringUtils.isNotEmpty(defaultEmptyText)){
            Map<String,Object> map = new HashMap<String,Object>();
            String typeName = "";
            if(StringUtils.isNotEmpty(typeCode)){
                Map<String,Object> dicType = dicTypeService.
                        getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_CODE"},new Object[]{typeCode});
                typeName= (String) dicType.get("TYPE_NAME");
            }
            map.put("DIC_ID","");
            map.put("DIC_CODE","");
            if(StringUtils.isNotEmpty(defaultEmptyText)){
                map.put("DIC_NAME", "请选择"+defaultEmptyText);  
            }else{
                map.put("DIC_NAME", "请选择"+typeName);  
            }
            if(StringUtils.isNotEmpty(controlId)){
                map.put("CONTROLID", controlId);
            }
            if(StringUtils.isNotEmpty(noScroll)){
                map.put("noScroll", true);
            }
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 加载数据字典URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request,
            HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String typeCode = request.getParameter("typeCode");
        String doType = request.getParameter("doType");
        List<Map<String,Object>> list;
        if(doType!=null&&!doType.equals("")){
            list = dictionaryService.findByTypeCodeAndDoType(typeCode, doType);
        }else{
            list = dictionaryService.findByTypeCode(typeCode);
        }
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("DIC_ID","");
            map.put("DIC_CODE","");
            map.put("DIC_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 加载数据字典URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "findMaterBusDatas")
    public void findMaterBusDatas(HttpServletRequest request,
            HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String itemId = request.getParameter("itemId");
        List<Map<String,Object>> list;
        list=dictionaryService.findMaterBusDatasForSelect(itemId);
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("VALUE","");
            map.put("TEXT", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    
    /**
     * 
     * 加载数据字典URL(自定义条件)
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "loadWhere")
    public void loadWhere(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String typeCode = request.getParameter("typeCode");
        String whereStr = request.getParameter("whereStr");
        List<Map<String,Object>> list = dictionaryService.findByTypeCodeAndWhere(typeCode, whereStr);
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("DIC_ID","");
            map.put("DIC_CODE","");
            map.put("DIC_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
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
        this.dictionaryService.updateSn(dicIds);
        j.setMsg("排序成功");
        return j;
    }
    
    /**
     * 
     * 描述 字典届次选择器
     * 
     * @author Roy Li
     * @created 2014-11-19 上午10:58:01
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        request.setAttribute("callbackFn", request.getParameter("callbackFn"));
        request.setAttribute("chkStyle", request.getParameter("chkStyle"));
        return new ModelAndView("system/dictionary/PeriodSelector");
    }
    
    /**
     * 
     * 加载数据字典URL(自定义条件)
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/loadCRJGJ")
    public void loadCRJGJ(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        String orderType = request.getParameter("orderType");
        String typeCode = request.getParameter("typeCode");
        String whereValue = request.getParameter("whereValue");
        String whereStr = "";
        if(StringUtils.isNotEmpty(typeCode)&&typeCode.equals("CRJGJ")){
            whereStr = " tsd.dic_desc like '"+whereValue+"%'";
        }else if(StringUtils.isNotEmpty(typeCode)&&typeCode.equals("CRJSLG")){
            whereStr = " tsd.dic_code like '"+whereValue+"%'";
        }
        List<Map<String,Object>> list = dictionaryService.findByTypeCodeAndWhereCRJ(typeCode, whereStr, orderType);
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            String typeName = "";
            if(StringUtils.isNotEmpty(typeCode)){
                Map<String,Object> dicType = dicTypeService.
                        getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_CODE"},new Object[]{typeCode});
                typeName= (String) dicType.get("TYPE_NAME");
            }
            map.put("DIC_ID","");
            map.put("DIC_CODE","");
            if(StringUtils.isNotEmpty(defaultEmptyText)){
                map.put("DIC_NAME", "请选择"+defaultEmptyText);  
            }else{
                map.put("DIC_NAME", "请选择"+typeName);  
            }
            list.add(0,map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 加载字典的显示值
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/textvalue")
    public void textvalue(HttpServletRequest request, HttpServletResponse response) {
        String busCodes = request.getParameter("busCodes");
        String typeCodes = request.getParameter("typeCodes");
        String dicCodes = request.getParameter("dicCodes");
        String itemNames = request.getParameter("itemNames");
        Map<String,String> result = dictionaryService.getDicTextValues(busCodes, typeCodes, dicCodes, itemNames);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 加载字典的显示值
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/textname")
    public void textname(HttpServletRequest request, HttpServletResponse response) {
        String typeCode = request.getParameter("typeCode");
        String dicCodes = request.getParameter("dicCodes");
        String dicname = dictionaryService.getDicNames(typeCode, dicCodes);
        this.setJsonString(dicname, response);
    }
    /**
     * 
     * 自动补全
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/auto")
    public void auto(HttpServletRequest request, HttpServletResponse response) {
        String typeCode = request.getParameter("typeCode");
        List<Map<String,Object>> list = dictionaryService.findByTypeCode(typeCode);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 获取统计图表树
     * @author Faker Li
     * @created 2016年3月9日 上午10:18:02
     * @param request
     * @param response
     */
    @RequestMapping("/tjtbTree")
    public void tjtbTree(HttpServletRequest request, HttpServletResponse response) {
        String typeCode = request.getParameter("TYPE_CODE");
        if (StringUtils.isNotEmpty(typeCode) && !typeCode.equals("undefined")) {
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("id", "0");
            root.put("name", "统计图表");
            root.put("open", true);
            //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
            root.put("PARENT_ID", "-1");
            root.put("TREE_LEVEL", 1);
            // 获取topType
            List<Map<String, Object>> children = dictionaryService.findByTypeCode(typeCode);
            if (children != null && children.size() > 0) {
                root.put("children", children);
                for (Map<String, Object> child : children) {
                    child.put("id", child.get("DIC_CODE"));
                    child.put("name", child.get("DIC_NAME"));
                    child.put("desc", child.get("DIC_DESC"));
                    //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                }
                
            }
            String json = JSON.toJSONString(root);
            this.setJsonString(json, response);
        }
    }
}

