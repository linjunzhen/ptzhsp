/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.thread;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.PushDataToSJJXXZXService;

/**
 * 推送数据至省经济中心 队列线程
 * @author Michael Lin
 * @Created 2019/10/8.
 */
public class PushDateToSJJXXZXRunnable implements Runnable{

    /**
     * 推送省经济信息中心服务接口service
     */
    @Autowired
    private PushDataToSJJXXZXService pushDataToSJJXXZXService;

    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 数据对象
     */
    private Map<String, Object> variables;

    /**
     * 操作类型
     */
    private String operationType;
    
    /**
     * 初始化实例对象
     * @param variables 数据对象
     * @param operationType 操作类型
     */
    public PushDateToSJJXXZXRunnable(Map<String,Object> variables,String operationType) {
        this.variables = variables;
        this.operationType = operationType;
        this.pushDataToSJJXXZXService = (PushDataToSJJXXZXService) AppUtil.getBean("pushDataToSJJXXZXService");
    }

    /**
     * 执行
     */
    @Override
    public void run() {
        System.out.println("进入了子线程方法Start");
        Map<String, Object> requestParam = new HashMap<String, Object>();
        requestParam.put("variables",new JSONObject(variables).toString());
        requestParam.put("operationType", operationType);
        this.queryAjaxJson(requestParam, "SJJXXZX_FORWARD_URL",variables.get("FORWARDURL").toString());
    }
    
    /**
     * 描述 订单缴费转发通用方法
     * @author Allin Lin
     * @created 2019年10月22日 上午9:32:02
     * @param info 查询的信息（参数）
     * @param urlDicType 订单缴费相关接口配置的字典值
     * @return 
     */
    public Map<String, Object> queryAjaxJson(Map<String, Object> info, String urlDicType,String forwardUrl) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String response = HttpSendUtil.sendPostParams(forwardUrl, info);
            if (StringUtils.isNotEmpty(response)) {
                result = JSON.parseObject(response, Map.class);
            }
        } catch (Exception e) {
            System.out.println("接口-"+urlDicType+"获取数据异常:"+ e);
        }
        return result;
    }
}
