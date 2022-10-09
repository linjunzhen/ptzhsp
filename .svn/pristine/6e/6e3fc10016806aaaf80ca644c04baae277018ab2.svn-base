/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;

/**
 * 描述  医保-用人单位基本医疗保险、生育保险信息注销登记业务控制类
 * @author Allin Lin
 * @created 2019年10月18日 下午3:00:36
 */
@Controller
@RequestMapping("/ybYrdwzxController")
public class YbYrdwzxController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbYrdwzxController.class);
    
    /**
     * 描述     跳转到参保信息核对标记查询选择器界面
     * @author Allin Lin
     * @created 2019年10月18日 下午3:13:20
     * @param request
     * @return
     */
    @RequestMapping(params = "showSelectUnitFlag")
    public ModelAndView selectUnitInfos(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");    
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/yrdwzx/showSelectUnitFlag");
    }
    
    /**
     * 描述    注销单位信息查询
     * @author Allin Lin
     * @created 2019年10月18日 下午3:52:10
     * @param request
     * @return
     */
    @RequestMapping(params = "showSelectZxUnit")
    public ModelAndView showSelectZXUnit(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");    
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/yrdwzx/showSelectZxUnit");
    }
    
    
    /**
     * 描述    获取单位信息核对标记信息列表
     * @author Allin Lin
     * @created 2019年11月14日 下午4:52:26
     * @param request
     * @param response
     */
    @RequestMapping(params = "unitFlagDatagrid")
    public void unitFlagDatagrid(HttpServletRequest request,HttpServletResponse response){
        /*Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        System.out.println(variables);
        List  list = new ArrayList<Map<String,Object>>(); //业务数据
        //调取远程接口获取数据
        AjaxJson ajaxJson=ybCommonService.commonAjaxJsonOfYb(variables, "bdcdaxxcxUrl");
        String jsonString = ajaxJson.getJsonString();
        String result = ajaxJson.getSuccess();
        if (StringUtils.isNotEmpty(jsonString)&&result==true) {
            tokenData = JSON.parseObject(jsonString, Map.class);
            String token = tokenData.get(0).get("TOKEN").toString();
            variables.put("Token", token);
            if(StringUtils.isNotEmpty(token)){
                AjaxJson ajaxJson1=ybCommonService.commonAjaxJsonOfYb(variables, "bdcdaxxcxUrl");
                String jsonString1 = ajaxJson.getJsonString(); 
                if (StringUtils.isNotEmpty(jsonString1)) {
                    list = JSON.parseObject(jsonString1, List.class);
                } 
            }
        }*/  
        List<Map<String,Object>> list;//业务数据
        String jsonString="[{\"CBID\":\"11\",\"CB_SBBM\":\"350011\",\"CB_DWBXH\":\"351028\","
                + "\"CB_DWBH\":\"2019234\",\"CB_DWMC\":\"测试单位\",\"CB_DWDAH\":\"334455\","
           + "\"CS_LSGX\":\"390\",\"CS_DWLB\":\"004\",\"CS_SH\":\"87654567\",\"CB_DWLX\":\"03\","
           + "\"CB_SHBZ\":\"04\",\"CB_HDBJ\":\"1\"}]";
        list = (List<Map<String, Object>>) JSON.parse(jsonString); 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);         
    }
    
    
    /**
     * 描述    获取注销单位信息列表
     * @author Allin Lin
     * @created 2019年11月18日 上午9:39:38
     * @param request
     * @param response
     */
    @RequestMapping(params = "zxInfoDatagrid")
    public void zxInfoDatagrid(HttpServletRequest request,HttpServletResponse response){
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        // System.out.println(variables);
        List<Map<String,Object>> list;//业务数据
        String jsonString="[{\"ZXID\":\"11\",\"ZX_JGBM\":\"350011\",\"ZX_DWBXH\":\"351028\","
                + "\"ZX_DWMC\":\"测试单位\",\"ZX_DABH\":\"334455\",\"ZX_HDBJ\":\"是\","
           + "\"ZX_SH\":\"87654567\",\"ZX_DWRS\":\"03\",\"ZX_DSBDJG\":\"1\"}]";
        list = (List<Map<String, Object>>) JSON.parse(jsonString);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
           
    }
    

}
