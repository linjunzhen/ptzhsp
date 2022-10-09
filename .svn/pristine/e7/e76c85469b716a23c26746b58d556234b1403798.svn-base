/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.usercenter.controller;

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
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.usercenter.service.TagService;

/**
 * 标签目录controller
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-2 上午8:38:51
 */
@Controller
@RequestMapping("/tagController")
public class TagController extends BaseController {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(TagController.class);
    /**
     * 引入tagService
     */
    @Resource
    private TagService tagService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     * 用户中心标签
     * @author Danto Huang
     * @created 2017-5-2 上午9:22:34
     * @param request
     */
    @RequestMapping(params="tagView")
    public ModelAndView tagView(HttpServletRequest request){
        return new ModelAndView("usercenter/tag/tagView");
    }
    /**
     * 
     * 标签列表 
     * @author Danto Huang
     * @created 2017-5-2 上午9:25:52
     * @param request
     * @param response
     */
    @RequestMapping(params="tagData")
    public void tagData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = tagService.findCatalogBySqlFilter(filter);
        for(Map<String,Object> map : list){
            map.put("_parentId", map.get("PARENT_TAG"));
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到类别信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "typeInfo")
    public ModelAndView typeInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  tagType = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            tagType = tagService.getByJdbc("T_USERCENTER_TAGTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
            
        }
        tagType.put("PARENT_ID", parentId);
        tagType.put("PARENT_NAME", parentName);
        request.setAttribute("tagType", tagType);
        return new ModelAndView("usercenter/tag/typeInfo");
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
        String recordId = tagService.saveOrUpdateTreeData(parentId,
                treeData, "T_USERCENTER_TAGTYPE", null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的用户中心标签类别记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 用户中心标签类别记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelType")
    @ResponseBody
    public AjaxJson multiDelType(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        List<Map<String,Object>> list = tagService.findByTypeId(selectColNames);
        if(list!=null&&list.size()>0){
            j.setMsg("该类别下存在记录，请先删除记录数据");
            j.setSuccess(false);
        }else{
            tagService.removeByTypeId(selectColNames);
            sysLogService.saveLog("删除了ID为["+selectColNames+"]的 用户中心标签类别记录",SysLogService.OPERATE_TYPE_DEL);
            j.setMsg("删除成功");
        }
        return j;
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
        tagService.remove("T_USERCENTER_TAG","TAG_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的用户中心标签信息记录",SysLogService.OPERATE_TYPE_DEL);
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
        Map<String,Object>  tagInfo  = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            tagInfo = tagService.getByJdbc("T_USERCENTER_TAG",new String[]{"TAG_ID"},new Object[]{entityId});
            typeId = (String) tagInfo.get("TYPE_ID");
            Map<String,Object> catalogType = tagService.
                    getByJdbc("T_USERCENTER_TAGTYPE",new String[]{"TYPE_ID"},new Object[]{typeId});
            tagInfo.put("TYPE_ID", typeId);
            tagInfo.put("TYPE_NAME",(String)catalogType.get("TYPE_NAME"));
        }else{
            tagInfo.put("TYPE_ID",typeId);
            tagInfo.put("TYPE_NAME",typeName);
        }
        request.setAttribute("tagInfo", tagInfo);
        return new ModelAndView("usercenter/tag/tagInfo");
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
        String entityId = request.getParameter("TAG_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            boolean isExist = tagService.isExist(request.getParameter("TYPE_ID"), 
                    request.getParameter("TAG_KEY"));
            if(isExist){
                j.setSuccess(false);
                j.setMsg("该标签类别下已存在相同编码的标签,创建失败!");
                return j;
            }
        }
        String recordId = tagService.saveOrUpdate(variables, "T_USERCENTER_TAG", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的用户中心标签记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 用户中心标签记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 跳转到选择器页面
     * @author Danto Huang
     * @created 2017-5-3 下午2:01:47
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String tagIds = request.getParameter("tagIds");
        String tagNames = request.getParameter("tagNames");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        if(StringUtils.isNotEmpty(tagIds)&&!tagIds.equals("undefined")){
            request.setAttribute("tagIds", tagIds);
            request.setAttribute("tagNames", tagNames);
        }
        return new ModelAndView("usercenter/tag/tagSelector");
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
        String tagIds = request.getParameter("tagIds");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(tagIds)){
            list = tagService.findByTagId(tagIds);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
}
