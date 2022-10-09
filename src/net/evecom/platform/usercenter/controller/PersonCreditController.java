/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.usercenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.usercenter.service.PersonCreditService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年4月26日 下午2:51:37
 */
@Controller
@RequestMapping("/personCreditController")
public class PersonCreditController extends BaseController {

    /**
     * 
     */
    @Resource
    private PersonCreditService personCreditService;
    
    @RequestMapping(params="creditLevel")
    @ResponseBody
    public AjaxJson creditLevel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String devbase_url = properties.getProperty("devbase_url");
        String grantcode = properties.getProperty("grantcode");
        Map<String,Object> postParam = new HashMap<String,Object>();
        postParam.put("servicecode", "findByLevelRule");
        postParam.put("grantcode", grantcode);

        String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
        Map<String, Object> resultMap = (Map)JSON.parse(respContent);
        if((boolean) resultMap.get("success")){
            
            j.setSuccess(true);
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年4月27日 上午11:03:15
     * @param request
     * @return
     */
    @RequestMapping(params="queryPersonCreditLevel")
    @ResponseBody
    public AjaxJson queryPersonCreditLevel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String devbase_url = properties.getProperty("devbase_url");
        String grantcode = properties.getProperty("grantcode");
        Map<String,Object> postParam = new HashMap<String,Object>();
        postParam.put("servicecode", "findByCreditUserInfo");
        postParam.put("grantcode", grantcode);
        
        String ID_CARD = request.getParameter("ID_CARD");
        postParam.put("ID_CARD", ID_CARD);
        String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
        Map<String, Object> resultMap = (Map)JSON.parse(respContent);
        if (null != resultMap && (boolean) resultMap.get("success")) {
            JSONArray data = (JSONArray) resultMap.get("datas");
            List<Map> datalist = JSONObject.parseArray(data.toJSONString(), Map.class);
            if (datalist != null && datalist.size() > 0) {
                j.setJsonString(datalist.get(0).get("credit_level").toString());
            } else {
                j.setJsonString("");
            }
            j.setSuccess(true);
        } else {
            j.setJsonString("");
            j.setSuccess(false);
        }
        return j;
    }
}
