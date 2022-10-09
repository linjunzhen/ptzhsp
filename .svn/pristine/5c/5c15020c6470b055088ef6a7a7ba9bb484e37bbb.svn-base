/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

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
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.YbYrdwbgService;

/**
 * 描述  医保-用人单位基本医疗保险、生育保险信息变更登记业务控制类
 * @author Allin Lin
 * @created 2019年10月17日 下午3:10:05
 */
@Controller
@RequestMapping("/ybYrdwbgController")
public class YbYrdwbgController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbYrdwbgController.class);
    
    
    /**
     * 引入的service
     */
    @Resource
    private YbYrdwbgService ybYrdwbgService;
    
    /**
     * 引入service
     */
    @Resource
    private YbCommonService ybCommonService;
  
    /**
     * 描述     跳转到单位信息查询选择器界面
     * @author Allin Lin
     * @created 2019年10月17日 下午3:13:20
     * @param request
     * @return
     */
    @RequestMapping(params = "selectUnitInfos")
    public ModelAndView selectUnitInfos(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");  
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/yrdwbg/showSelectUnitInfos");
    }
    
    
    /**
     * 描述    获取险种信息
     * @author Allin Lin
     * @created 2019年11月13日 下午5:02:15
     * @param request
     * @param response
     */
    @RequestMapping(params = "paramjson")
    public void paramjson(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String ywId = request.getParameter("ywId");//业务ID
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_YRDWBG", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("XZXX_JSON")!=null){
                list = (List<Map<String, Object>>) JSON.parse(record.get("XZXX_JSON").toString());               
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    
    /**
     * 描述     获取单位信息列表
     * @author Allin Lin
     * @created 2019年11月14日 上午9:20:32
     * @param request
     * @param response
     */
    @RequestMapping(params = "unitInfosDatagrid")
    public void unitInfosDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitInfosDatagrid");
            params.put("token", tokenMap.get("token"));
          /*  AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(params, "yrdwbgQuery");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    list = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                 
            }else{
                log.info(dataJson.getMsg());
            }*/
            String jsonString="[{\"YWH\":\"11\",\"SBBM\":\"350011\",\"DWBXH\":\"351028\","
                    + "\"CBRQ\":\"20190909\",\"DWMC\":\"测试单位\",\"DWDAH\":\"334455\","
               + "\"DWLX\":\"390\",\"DWBH\":\"004\",\"SH\":\"87654567\",\"LSGX\":\"03\","
               + "\"DWLB\":\"04\",\"SHBZ\":\"1\",\"HDBZ\":\"0\"}]";
            list = (List<Map<String, Object>>) JSON.parse(jsonString);          
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    获取单位险种信息
     * @author Allin Lin
     * @created 2020年1月2日 下午3:42:23
     * @param request
     * @param response
     */
    @RequestMapping(params = "xzxxDatagrid")
    public void xzxxDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("xzxxDatagrid");
            params.put("token", tokenMap.get("token"));
            /*AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(params, xzxxQuery");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                  list = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                 
            }else{
                log.info(dataJson.getMsg());
            }*/
           String jsonString="[{\"XZ_TYPE\":\"320\",\"BEGIN_TIME\":\"20160901\",\"ZSFS\":\"2\","
                    + "\"FLAG\":\"1\"},{\"XZ_TYPE\":\"310\",\"BEGIN_TIME\":\"20160901\",\"ZSFS\":\"2\","
                    + "\"FLAG\":\"1\"},{\"XZ_TYPE\":\"330\",\"BEGIN_TIME\":\"20160901\",\"ZSFS\":\"2\","
                    + "\"FLAG\":\"1\"}]";   
           list = (List<Map<String, Object>>) JSON.parse(jsonString); 
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    
    /**
     * 描述    用人单位信息变更登记推送医保
     * @author Allin Lin
     * @created 2020年1月3日 上午9:33:12
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushYrdwBg")
    @ResponseBody
    public AjaxJson pushYrdwBg(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_YRDWBG", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null && !"".equals(record)&& !"undefined".equals(record)){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));                     
                    //System.out.println(JSON.toJSONString(pushInfo));
                    //ajaxJson = ybCommonService.commonAjaxJsonOfYb(pushInfo, "pushYrdwBg");  
                    ajaxJson.setSuccess(true);
                    ajaxJson.setMsg("处理成功");
                }else{
                    log.info("获取token值失败！");
                }
            }
        }
        return ajaxJson;
    }
}
