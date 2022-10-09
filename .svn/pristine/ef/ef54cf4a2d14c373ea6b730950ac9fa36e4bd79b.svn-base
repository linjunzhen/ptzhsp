/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.yb.service.YbCommonService;
/**
 * 
 * 描述 医保信息变更控制类
 * @author Reggie Lin
 * @created 2019年10月18日 下午3:00:21
 */
@Controller
@RequestMapping("/ybCbryxxbgController")
public class YbCbryxxbgController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbCbryxxbgController.class);
    
    /**
     * 引入service
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 
     * 描述  参保人信息查询弹窗界面 
     * @author Reggie Lin
     * @created 2019年10月18日 下午3:02:00
     * @param request
     * @return
     */
    @RequestMapping(params = "cbrQueryForSelect")
    public ModelAndView cbrQueryForSelect(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");  
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/cbryxxbg/showCbrQuery");
    }
    
   /**
    * 
    * 描述 参保人信息   
    * @author Reggie Lin
    * @created 2019年10月18日 下午3:47:09
    * @param request
    * @param response
    */
    @RequestMapping(params="cbrQueryData")
    public void cbrQueryData(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("cbrQueryData");
            params.put("token", tokenMap.get("token"));
          /*  AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(params, "cbrQuery");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    list = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                 
            }else{
                log.info(dataJson.getMsg());
            }*/
            String jsonString="[{\"XZ\":\"320\",\"DWMC\":\"测试单位\",\"XM\":\"test\","
                    + "\"CBZT\":\"1\",\"ZJHM\":\"350321197812130987\",\"DWBXH\":\"350321\",\"CBSF\":\"01\"}]";
            list = (List<Map<String, Object>>) JSON.parse(jsonString); 
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);  
    }
    
    
    /**
     * 描述    参保人员险种信息查询
     * @author Allin Lin
     * @created 2019年12月30日 下午4:33:37
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
           String jsonString="[{\"XZLX\":\"320\",\"STRAT_DATE\":\"20160901\",\"END_DATE\":\"99991231\","
                    + "\"CBZT\":\"1\"},{\"XZLX\":\"310\",\"STRAT_DATE\":\"20160901\",\"END_DATE\":\"99991231\","
                    + "\"CBZT\":\"1\"},{\"XZLX\":\"330\",\"STRAT_DATE\":\"20160901\",\"END_DATE\":\"99991231\","
                    + "\"CBZT\":\"1\"}]";   
           list = (List<Map<String, Object>>) JSON.parse(jsonString); 
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    
    /** 
     * 描述    获取险种信息
     * @author Allin Lin
     * @created 2019年12月30日 下午5:08:37
     * @param request
     * @param response
     */
    @RequestMapping(params="findXZXX")
    public void findXZXX(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String ywId = request.getParameter("ywId");//业务ID
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CBRYXXBG", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("XZXX_JSON")!=null){
                list = (List<Map<String, Object>>) JSON.parse(record.get("XZXX_JSON").toString());               
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 描述    参保人变更信息推送医保
     * @author Allin Lin
     * @created 2019年12月31日 上午9:50:07
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushCbrXxBg")
    @ResponseBody
    public AjaxJson pushCbrXxBg(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CBRYXXBG", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null && !"".equals(record)&& !"undefined".equals(record)){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));                     
                    //System.out.println(JSON.toJSONString(pushInfo));
                    //ajaxJson = ybCommonService.commonAjaxJsonOfYb(pushInfo, "pushCbrXxBg");  
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
