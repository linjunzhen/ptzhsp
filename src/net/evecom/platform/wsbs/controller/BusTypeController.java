/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.Date;
import java.util.HashMap;
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
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.ServiceItemService;
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
 * 描述  业务类别Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/busTypeController")
public class BusTypeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusTypeController.class);
    /**
     * 引入Service
     */
    @Resource
    private BusTypeService busTypeService;
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
        busTypeService.remove("T_WSBS_BUSTYPE","TYPE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 业务类别记录",SysLogService.OPERATE_TYPE_DEL);
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
        Map<String,Object>  busType = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            busType = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
            
        }
        busType.put("PARENT_ID", parentId);
        busType.put("PARENT_NAME", parentName);
        request.setAttribute("busType", busType);
        return new ModelAndView("wsbs/applymater/busTypeInfo");
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
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        busTypeService.saveOrUpdateTreeData(parentId, treeData,"T_WSBS_BUSTYPE",null);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到场景式主题列表页
     * 
     * @param request
     * @return
     */
    @RequestMapping("/sceneNavi")
    public ModelAndView sceneNavi(HttpServletRequest request) {
        String busCode = request.getParameter("busCode");
        if(StringUtils.isNotEmpty(busCode)&&busCode.equals("GRZTFL")){
            request.setAttribute("navi", "个人办事");
        }else if(StringUtils.isNotEmpty(busCode)&&busCode.equals("FRZTFL")){
            request.setAttribute("navi", "法人办事");
        }
        List<Map<String,Object>>  list = busTypeService.findByTypeCodeForWebSite(busCode);
        request.setAttribute("busCode", busCode);
        request.setAttribute("list", list);
        return new ModelAndView("website/bsdt/sceneNaviList");
    }
    
    /**
     * 跳转到场景式主题列表页
     * 
     * @param request
     * @return
     */
    @RequestMapping("/sceneNaviLink")
    public ModelAndView sceneNaviLink(HttpServletRequest request) {
        //String busCode = request.getParameter("busCode");
        String typeCode = request.getParameter("typeCode");
        List<Map<String, Object>> list = null;
        Map<String,Object>  busType = new HashMap<String,Object>();
        if (StringUtils.isNotEmpty(typeCode) && !typeCode.equals("undefined")) {
            busType = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                    new String[]{"TYPE_CODE"},new Object[]{typeCode});
            if(busType==null){
                return new ModelAndView("error");
            }
            String treeSn=busType.get("TREE_LEVEL").toString();
            if(!"3".equals(treeSn)){
                return new ModelAndView("error");
            }
            String typeId=(String) busType.get("TYPE_ID");
            list = serviceItemService.findfrontList(typeId);
            
            String parentId=(String) busType.get("PARENT_ID");
            Map<String,Object> parent = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                    new String[]{"TYPE_ID"},new Object[]{parentId});
            String busCode =(String) parent.get("TYPE_CODE");
            if(StringUtils.isNotEmpty(busCode)&&busCode.equals("GRZTFL")){
                request.setAttribute("navi", "个人办事");
            }else if(StringUtils.isNotEmpty(busCode)&&busCode.equals("FRZTFL")){
                request.setAttribute("navi", "法人办事");
            }
            request.setAttribute("busCode", busCode);
        }
        request.setAttribute("list", list);
        request.setAttribute("busType", busType);
        return new ModelAndView("website/bsdt/sceneNaviInfo");
    }
    /**
     * 
     * 描述 获取APP类别列表
     * @author Faker Li
     * @created 2016年1月20日 下午2:25:03
     * @param request
     * @param response
     */
    @RequestMapping("/appBusTypelist")
    public void appBusTypelist(HttpServletRequest request, HttpServletResponse response) {
        String busCode = request.getParameter("busCode");
        String busId = request.getParameter("busId");
        List<Map<String,Object>>   list = busTypeService.findByTypeCodeForWebSite(busCode);
        Map<String,Object> busMap = new HashMap<String, Object>();
        busMap.put("rows", list);
        busMap.put("id", busId);
        String json = JSON.toJSONString(busMap);
        this.setJsonString(json, response);
    }
}

