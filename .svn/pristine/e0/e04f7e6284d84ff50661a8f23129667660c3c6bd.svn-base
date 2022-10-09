/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.ArrayList;
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
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcYgspfygdjService;

/**
 * 描述 预购商品房预告登记Controller
 * @author Allin Lin
 * @created 2019年3月13日 上午10:49:40
 */
@Controller
@RequestMapping("/bdcYgspfygdjController")
public class BdcYgspfygdjController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcYgspfygdjController.class);
    
    /**
     * 引入service
     */
    @Resource
    private BdcYgspfygdjService bdcYgspfygdjService;
    
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 跳转到房地产合同备案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "fdchtbaxxcxSelector")
    public ModelAndView bdcdaxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("bsdt/applyform/bdcygspfygdj/fdchtbaxxcxSelector");
    }
    
    /**
     * 跳转到房地产合同备案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "fdchtbaxxcxNewSelector")
    public ModelAndView bdcdaxxcxNewSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("bsdt/applyform/bdcqlc/bdcqlcygspfygdj/fdchtbaxxcxSelector");
    }
    
    /**
     * easyui AJAX请求列表数据(获取房地产合同备案信息列表)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "fdchtbaxxcxDatagrid")
    public void bdcdaxxcxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String fwmmhth = variables.get("fwmmhth") == null ? "" : variables.get("fwmmhth").toString();
        String ysxkzbh = variables.get("ysxkzbh") == null ? "" : variables.get("ysxkzbh").toString();
        String msfxm = variables.get("msfxm") == null ? "" : variables.get("msfxm").toString();
        String msfzjhm = variables.get("msfzjhm") == null ? "" : variables.get("msfzjhm").toString();
        String xmmc = variables.get("xmmc") == null ? "" : variables.get("xmmc").toString();
        String zrfxm = variables.get("zrfxm") == null ? "" : variables.get("zrfxm").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(fwmmhth)||StringUtils.isNotEmpty(ysxkzbh)
                ||StringUtils.isNotEmpty(msfxm)||StringUtils.isNotEmpty(msfzjhm)
                ||StringUtils.isNotEmpty(xmmc)||StringUtils.isNotEmpty(zrfxm)){
           AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "contractUrl");
           String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述    根据不动产单元号获取房屋产权状态
     * @author Allin Lin
     * @created 2019年3月13日 下午3:00:16
     * @param request
     * @return
     */
    @RequestMapping(params="getCqztByBdcdyh")
    @ResponseBody
    public AjaxJson getCqztByBdcdyh(HttpServletRequest request){
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        AjaxJson j = new AjaxJson();
        List<Map<String,Object>> list = null; 
        if(StringUtils.isNotEmpty(bdcdyh)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "fwztjyUrl");  
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) { 
                j.setSuccess(true);
                list = JSON.parseObject(jsonString, List.class);
                j.setJsonString(JSON.toJSONString(list));
            }else{
                j.setSuccess(false);
            } 
        }     
        return j;
    }
    
    
    /**
     * 跳转到权利人界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshQlrDiv")
    public ModelAndView refreshQlrDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/bdcqlc/bdcqlcygspfygdj/qlrDiv");
    }
    
    /**
     * 跳转到权利人界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshYwrDiv")
    public ModelAndView refreshYwrDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/bdcqlc/bdcqlcygspfdyqygdj/ywrDiv");
    }
    
    /**
     * 
     * 描述    保存预购抵押预告登记信息(预购商品房抵押预告登记)
     * @author Allin Lin
     * @created 2020年11月26日 下午4:41:06
     * @param request
     * @param response
     * @return
     */
     @RequestMapping(params = "saveDyqdjxx")
     @ResponseBody
     public Map<String, Object> saveDyqdjxx(HttpServletRequest request, HttpServletResponse response){
         Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
         Map<String,Object> returnMap = new HashMap<>();
         returnMap.put("success", false);
         String busTableName = (String) variables.get("busTableName");
         String busRecordId = (String) variables.get("busRecordId");
         try{
             if (StringUtils.isNotEmpty(busTableName) && StringUtils.isNotEmpty(busRecordId)) {
                 bdcYgspfygdjService.saveOrUpdate(variables, busTableName, busRecordId);
                 returnMap.put("success", true);
             }
         } catch (Exception e) {
             log.error(e.getMessage(), e);
         }
         return returnMap;
     }
}
