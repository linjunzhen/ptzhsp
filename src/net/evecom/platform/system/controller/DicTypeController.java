/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  字典类别Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/dicTypeController")
public class DicTypeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DicTypeController.class);
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("system/dicType/list");
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
        dicTypeService.removeByTypeId(selectColNames);
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 字典类别记录",SysLogService.OPERATE_TYPE_DEL);
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
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  dicType = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
            
        }
        dicType.put("PARENT_ID", parentId);
        dicType.put("PARENT_NAME", parentName);
        request.setAttribute("dicType", dicType);
        return new ModelAndView("system/dictionary/dicTypeInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateTree")
    @ResponseBody
    public AjaxJson saveOrUpdateTree(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("TYPE_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = dicTypeService.saveOrUpdateTreeData(parentId, treeData,"T_MSJW_SYSTEM_DICTYPE",null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 字典类别记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 字典类别记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 加载字典类别
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        String parentCode = request.getParameter("parentTypeCode");
        String controlId = request.getParameter("controlId");
        //业务类别（服务大厅的子类别，如户政、治安的编码）
        String busType = request.getParameter("busType");
        String noScroll = request.getParameter("noScroll"); 
        List<Map<String,Object>> list;
        if(StringUtils.isNotEmpty(busType)){
            list = dicTypeService.findByParentCodeAndBusType(parentCode, busType);
        }else{
            list = dicTypeService.findByParentCode(parentCode);
        }
        if(StringUtils.isNotEmpty(defaultEmptyText)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("TYPE_ID","");
            map.put("TYPE_NAME","请选择"+defaultEmptyText);
            map.put("TYPE_CODE","");
            map.put("PARENT_ID","");
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
     * 描述 查询下拉树
     * @author Rider Chen
     * @created 2015-12-31 下午02:40:04
     */
    @RequestMapping("/selectTree")
    public void selectTree(HttpServletRequest request, HttpServletResponse response){
        String typeCode =  request.getParameter("id");
        List<Map<String,Object>> children ;
        if(StringUtils.isNotEmpty(typeCode)){
            children = dicTypeService.findByParentCode(typeCode);
        }else{
            children = dicTypeService.findByParentCode("INDUSTRY");
        }
        for (Map<String, Object> child : children) {
            child.put("id", child.get("TYPE_CODE"));
            child.put("text", child.get("TYPE_NAME"));
           
            List<Map<String,Object>> list = dicTypeService.findByParentCode(child.get("TYPE_CODE").toString());
            if(null!=list&&list.size()>0){
                child.put("isLeaf", false);
                child.put("state", "closed");
            }else{
                child.put("isLeaf", true);
            }
        }
        String json = JSON.toJSONString(children);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 查询下拉树
     * @author Rider Chen
     * @created 2015-12-31 下午02:40:04
     */
    @RequestMapping("/selectIndustryStructureTree")
    public void selectIndustryStructureTree(HttpServletRequest request, HttpServletResponse response){
        String typeCode =  request.getParameter("id");
        List<Map<String,Object>> children ;
        if(StringUtils.isNotEmpty(typeCode)){
            children = dicTypeService.findByParentCode(typeCode);
        }else{
            children = dicTypeService.findByParentCode("INDUSTRYSTRUCTURE");
        }
        for (Map<String, Object> child : children) {
            child.put("id", child.get("TYPE_CODE"));
            child.put("text", child.get("TYPE_NAME"));
           
            List<Map<String,Object>> list = dicTypeService.findByParentCode(child.get("TYPE_CODE").toString());
            if(null!=list&&list.size()>0){
                child.put("isLeaf", false);
                child.put("state", "closed");
            }else{
                child.put("isLeaf", true);
            }
        }
        String json = JSON.toJSONString(children);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 查询下拉树
     * @author Rider Chen
     * @created 2015-12-31 下午02:40:04
     */
    @RequestMapping("/placeTree")
    public void placeTree(HttpServletRequest request, HttpServletResponse response){
        String typeCode =  request.getParameter("id");
        List<Map<String,Object>> children ;
        if(StringUtils.isNotEmpty(typeCode)){
            children = dicTypeService.findByParentCode(typeCode);
        }else{
            children = dicTypeService.findByParentCode("placeCode");
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("TYPE_NAME", "平潭综合实验区");
            map.put("TYPE_CODE", "350128");
            map.put("isLeaf","true");
            children.add(map);
        }
        for (Map<String, Object> child : children) {
            child.put("id", child.get("TYPE_CODE"));
            child.put("text", child.get("TYPE_NAME"));
           
            List<Map<String,Object>> list = dicTypeService.findByParentCode(child.get("TYPE_CODE").toString());
            if(null!=list&&list.size()>0){
                child.put("isLeaf", false);
                child.put("state", "closed");
            }else{
                child.put("isLeaf", true);
            }
        }
        String json = JSON.toJSONString(children);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 根据TYPE_CODE查询类别值
     * @author Rider Chen
     * @created 2015-12-31 下午02:40:04
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/info")
    public void info(HttpServletRequest request, HttpServletResponse response){
        String typeCode =  request.getParameter("typeCode");
        String path = request.getParameter("path");
        Map<String, Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE", new String[] { "TYPE_CODE" },
                new Object[] { typeCode });
        if(StringUtils.isNotEmpty(path)){
            if(null!=dicType){
                String dicPath = dicType.get("PATH").toString();
                if(dicPath.contains(path)){//是否包含
                    String json = JSON.toJSONString(dicType);
                    this.setJsonString(json, response);
                } else{
                    Map<String, Object> r = new HashMap<String, Object>();
                    r.put("success", false);
                    r.put("msg", "不存在该编码");
                    String json = JSON.toJSONString(r);
                    this.setJsonString(json, response);
                }
            }else{
                Map<String, Object> r = new HashMap<String, Object>();
                r.put("success", false);
                r.put("msg", "不存在该编码");
                r.put("TYPE_CODE", "");
                r.put("TYPE_NAME", "");
                String json = JSON.toJSONString(r);
                this.setJsonString(json, response);
            }
        } else{
            String json = JSON.toJSONString(dicType);
            this.setJsonString(json, response);
        }
    }
    /**
     * 
     * 描述：行政编码初始化
     * @author Water Guo
     * @created 2017-3-15 上午8:40:45
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/placeInfo")
    public void placeInfo(HttpServletRequest request, HttpServletResponse response){
        //String typeCode =  request.getParameter("typeCode");
        Map<String, Object> dicType;
        dicType=new HashMap<String, Object>();
        dicType.put("TYPE_NAME", "平潭综合实验区");
        dicType.put("TYPE_CODE", "350128");
        String json = JSON.toJSONString(dicType);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 根据TYPE_CODE查询类别值
     * @author Water Guo
     * @created 2015-12-31 下午02:40:04
     */
    @RequestMapping("/dic")
    public void dic(HttpServletRequest request, HttpServletResponse response){
        String typeCode =  request.getParameter("typeCode");
        List<Map<String, Object>> dicType = dicTypeService.getListByJdbc("T_MSJW_SYSTEM_DICTIONARY",  typeCode );
        String json = JSON.toJSONString(dicType);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年4月17日 下午4:05:11
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String,Object> parentType,String parentId){
        List<Map<String,Object>> children = dicTypeService.findByParentId(parentId);
        if(children!=null&&children.size()>0){
            parentType.put("children", children);
            for(Map<String,Object> child:children){
                child.put("id", child.get("TYPE_ID"));
                child.put("name", child.get("TYPE_NAME"));
                //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, (String)child.get("TYPE_ID"));
            }
        }
    }
    
    @RequestMapping("/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("id", "0");
        root.put("name","字典类别树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        //获取topType
        List<Map<String,Object>> toplist = dicTypeService.findByParentId("0");
        for(Map<String,Object> top:toplist){
            top.put("id", top.get("TYPE_ID"));
            top.put("name", top.get("TYPE_NAME"));
            //top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildren(top, (String)top.get("TYPE_ID"));
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
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
        String itemNames = request.getParameter("itemNames");
        Map<String,String> result = dicTypeService.getTextValues(busCodes, typeCodes, itemNames);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述： 根据类别名称与父ID获取信息
     * 
     * @author Rider Chen
     * @created 2020年8月28日 上午9:10:30
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getInfoToName")
    public void getInfoToName(HttpServletRequest request, HttpServletResponse response) {
        String TYPE_NAME = request.getParameter("TYPE_NAME");
        String PARENT_ID = request.getParameter("PARENT_ID");
        Map<String, Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                new String[] { "TYPE_NAME", "PARENT_ID" }, new Object[] { TYPE_NAME, PARENT_ID });
        String json = JSON.toJSONString(dicType);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述    树形下拉框数据
     * @author Danto Huang
     * @created 2021年6月18日 上午11:10:30
     * @param request
     * @param response
     */
    @RequestMapping("/loadSelectTree")
    public void loadSelectTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> root = new HashMap<String,Object>();
        String parentCode = request.getParameter("parentCode");
        root = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                new String[] { "TYPE_CODE" }, new Object[] { parentCode });
        root.put("id", root.get("TYPE_ID"));
        root.put("text", root.get("TYPE_NAME"));
        //获取topType
        List<Map<String,Object>> toplist = dicTypeService.findByParentId(root.get("TYPE_ID").toString());
        for(Map<String,Object> top:toplist){
            top.put("id", top.get("TYPE_ID"));
            top.put("text", top.get("TYPE_NAME"));
            top.put("state","closed");
            this.loadSelectChildren(top, (String)top.get("TYPE_ID"));
        }
        root.put("children", toplist);
        List<Map<String,Object>> treelist = new ArrayList<Map<String,Object>>();
        treelist.add(root);
        String json = JSON.toJSONString(treelist);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年6月18日 上午11:17:42
     * @param parentType
     * @param parentId
     */
    public void loadSelectChildren(Map<String,Object> parentType,String parentId){
        List<Map<String,Object>> children = dicTypeService.findByParentId(parentId);
        if(children!=null&&children.size()>0){
            parentType.put("children", children);
            for(Map<String,Object> child:children){
                child.put("id", child.get("TYPE_ID"));
                child.put("text", child.get("TYPE_NAME"));
                this.loadSelectChildren(child, (String)child.get("TYPE_ID"));
            }
        }
    }
}

