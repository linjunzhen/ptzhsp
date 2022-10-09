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

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.ExtraDicTypeService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 上午11:42:57
 */
@Controller
@RequestMapping("/extraDicTypeController")
public class ExtraDicTypeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExtraDicTypeController.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * extraDicTypeService
     */
    @Resource
    private ExtraDicTypeService extraDicTypeService;
    
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
        extraDicTypeService.removeByTypeId(selectColNames);
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 商事登记附加字典类别记录",SysLogService.OPERATE_TYPE_DEL);
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
            dicType = extraDicTypeService.getByJdbc("T_COMMERCIAL_DICTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
            
        }
        dicType.put("PARENT_ID", parentId);
        dicType.put("PARENT_NAME", parentName);
        request.setAttribute("dicType", dicType);
        return new ModelAndView("commercial/extraDictionary/dicTypeInfo");
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
        String recordId = extraDicTypeService.saveOrUpdateTreeData(parentId, treeData,"T_COMMERCIAL_DICTYPE",null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 商事登记附加字典类别记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 商事登记附加字典类别记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    public void getChildren(Map<String,Object> parentType,String parentId){
        List<Map<String,Object>> children = extraDicTypeService.findByParentId(parentId);
        if(children!=null&&children.size()>0){
            parentType.put("children", children);
            for(Map<String,Object> child:children){
                child.put("id", child.get("TYPE_ID"));
                child.put("name", child.get("TYPE_NAME"));
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
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        //获取topType
        List<Map<String,Object>> toplist = extraDicTypeService.findByParentId("0");
        for(Map<String,Object> top:toplist){
            top.put("id", top.get("TYPE_ID"));
            top.put("name", top.get("TYPE_NAME"));
            this.getChildren(top, (String)top.get("TYPE_ID"));
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年4月6日 下午2:29:03
     * @param request
     * @param response
     */
    @RequestMapping("/treeForSelect")
    public void treeForSelect(HttpServletRequest request, HttpServletResponse response) {
        String typeCode = request.getParameter("typeCode");
        Map<String, Object> root = extraDicTypeService.getByJdbc("T_COMMERCIAL_DICTYPE", new String[] { "TYPE_CODE" },
                new Object[] { typeCode });
        //获取topType
        List<Map<String,Object>> toplist = extraDicTypeService.findByParentId(root.get("TYPE_ID").toString());
        for(Map<String,Object> top:toplist){
            top.put("id", top.get("TYPE_CODE"));
            top.put("text", top.get("TYPE_NAME"));
            this.getChildrenForSelect(top, (String)top.get("TYPE_ID"));
        }
        //root.put("children", toplist);
        String json = JSON.toJSONString(toplist);
        this.setJsonString(json, response);
    }
    
    public void getChildrenForSelect(Map<String,Object> parentType,String parentId){
        List<Map<String,Object>> children = extraDicTypeService.findByParentId(parentId);
        if(children!=null&&children.size()>0){
            parentType.put("children", children);
            for(Map<String,Object> child:children){
                child.put("id", child.get("TYPE_CODE"));
                child.put("text", child.get("TYPE_NAME"));
                this.getChildrenForSelect(child, (String)child.get("TYPE_ID"));
            }
        }
    }
}
