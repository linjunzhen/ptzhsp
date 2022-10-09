/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.YbGwyjfqrService;

/**
 * 描述  公务员医疗补助费缴费确认业务控制类
 * @author Allin Lin
 * @created 2019年11月5日 下午3:20:50
 */
@Controller
@RequestMapping("/ybGwyjfqrController")
public class YbGwyjfqrController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbGwyjfqrController.class);
    
    /**
     * 引入的service
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 引入的service
     */
    @Resource
    private YbGwyjfqrService ybGwyjfqrService;
    
    /**
     * 描述   批量在职人员参保信息列表 (调取接口或后台取数据)
     * @author Allin Lin
     * @created 2019年11月5日 下午3:39:51
     */
    @RequestMapping(params = "zzdatagrid")
    public void zzdatagrid(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        List<Object> list = new ArrayList<Object>();//业务数据
        String ywId = request.getParameter("ywId");
        variables.remove("zzdatagrid");
        variables.remove("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String, Object> record = ybGwyjfqrService.getByJdbc("T_YBQLC_GWYJFQR", 
                    new String[] { "YW_ID" }, new Object[]{ywId});
            if(record != null && record.get("ZZRY_JSON")!=null && variables.size()<1){
                list = JSONObject.parseArray(record.get("ZZRY_JSON").toString());
            }else{
                //调取查询接口获取数据   
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    variables.put("token", tokenMap.get("token"));
                    //System.out.println(variables);
                    /*AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(variables, "zzryQuery");
                    if(dataJson.isSuccess()){
                       String jsonString = dataJson.getJsonString();
                        if(jsonString.length()>0){
                            list = (List<Map<String, Object>>) JSON.parse(jsonString);   
                        }
                    }else{
                        log.info(dataJson.getMsg());
                    }*/
                    String jsonString="[{\"DWID\":\"11\",\"ZZ_BXH\":\"350011\",\"ZZ_MC\":\"测试单位\","
                            + "\"ZZ_XM\":\"Allin\",\"ZZ_HM\":\"350321199711130725\",\"ZZ_SF\":\"b0\","
                       + "\"ZZ_XZ\":\"630\",\"ZZ_CBRQ\":\"20190101\",\"ZZ_ZT\":\"1\"},"
                       + "{\"DWID\":\"11\",\"ZZ_BXH\":\"350011\",\"ZZ_MC\":\"测试单位\","
                       + "\"ZZ_XM\":\"test\",\"ZZ_HM\":\"350321199711130725\",\"ZZ_SF\":\"b0\","
                       + "\"ZZ_XZ\":\"630\",\"ZZ_CBRQ\":\"20190101\",\"ZZ_ZT\":\"1\"}]";
                    list = JSON.parseObject(jsonString, List.class);
                }else{
                    log.info("获取token值失败！");
                }
            }
        } 
        if (list.size() > 0) {
            this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
        }  
    }
    
    /**
     * 描述   批量退休人员参保信息列表 
     * @author Allin Lin
     * @created 2019年11月5日 下午3:39:51
     */
    @RequestMapping(params = "txdatagrid")
    public void txdatagrid(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        List<Object> list = new ArrayList<Object>();//业务数据
        String ywId = request.getParameter("ywId");
        variables.remove("txdatagrid");
        variables.remove("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String, Object> record = ybGwyjfqrService.getByJdbc("T_YBQLC_GWYJFQR", 
                    new String[] { "YW_ID" }, new Object[]{ywId});
            if(record != null && record.get("TXRY_JSON")!=null){
                list = JSONObject.parseArray(record.get("TXRY_JSON").toString());
            }else{
                //调取查询接口获取数据
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    variables.put("token", tokenMap.get("token"));     
                    //System.out.println(variables);
                    /*AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(variables, "zzryQuery");
                    if(dataJson.isSuccess()){
                       String jsonString = dataJson.getJsonString();
                        if(jsonString.length()>0){
                            list = (List<Map<String, Object>>) JSON.parse(jsonString);   
                        }
                    }else{
                        log.info(dataJson.getMsg());
                    }*/
                    String jsonString="[{\"TXID\":\"11\",\"TX_BXH\":\"350011\",\"TX_MC\":\"测试单位\","
                            + "\"TX_XM\":\"Allin\",\"TX_HM\":\"350321199711130725\",\"TX_SF\":\"b0\","
                       + "\"TX_XZ\":\"630\",\"TX_CBRQ\":\"20190101\",\"TX_ZT\":\"1\"}]";
                    list = JSON.parseObject(jsonString, List.class);    
                }else{
                    log.info("获取token值失败！");
                }
            }
        } 
            this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
         
   
    }
    
    
    
    

}
