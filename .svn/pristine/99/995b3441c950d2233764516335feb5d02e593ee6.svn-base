/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpClientUtil;
import net.evecom.platform.fjfda.service.FjfdaTransService;
import net.evecom.platform.fjfda.service.FoodManagementService;
import net.evecom.platform.fjfda.util.TokenUtil;

/**
 * 描述 省食药监平台报送受理数据的定时任务接口
 * @author Keravon Feng
 * @created 2019年2月25日 上午10:12:07
 */
public class FjfdaCreateItemJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FjfdaCreateItemJob.class);
    
    /**
     * 接口IP地址
     */
    private String url;
    
    /**
     * 接口地址
     */
    private String postUrl;
    
    /**
     * foodManagementService
     */
    @Resource
    private FoodManagementService foodManagementService;
    /**
     * 描述 构造方法
     * @author Keravon Feng
     * @created 2019年2月25日 上午10:16:17
     */
    public FjfdaCreateItemJob(){
        super();
        if (foodManagementService == null) {
            foodManagementService = (FoodManagementService) AppUtil.getBean("foodManagementService");
        }
        Properties properties = FileUtil.readProperties("project.properties");
        url = properties.getProperty("fjfdaURL");
        postUrl = url+"service/createItem.do";
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        //获取未上传的业务数据
        List<Map<String,Object>> list = foodManagementService.listTrans(50);
        log.info("++++++++开始批量报送食药业务数据++++++++");
        if(list != null && list.size() > 0){
            for(Map<String,Object> map : list){
                String ywId = (String)map.get("YW_ID");
                String busRecordId = (String)map.get("BUS_RECORDID");
                String itemCode = (String)map.get("ITEM_CODE");
                Map<String,Object> transdata = foodManagementService.getByJdbc("T_FJFDA_TRANS_DATA", 
                        new String[]{"BUS_RECORDID"}, new Object[]{busRecordId});
                if(transdata != null){
                    String datajson = String.valueOf(transdata.get("DATA_JSON"));
                    Map<String,Object> param = new HashMap<String, Object>();
                    String tokenjson = TokenUtil.getToken();
                    JSONObject json = JSON.parseObject(tokenjson);
                    param.put("token", json.getString("token"));
                    param.put("item_code", itemCode);
                    param.put("data", JSON.parse(datajson));
                    Map<String,Object> result = TokenUtil.doPostJson(HttpClientUtil.getPlainHttpClient(),
                            postUrl,param);
                    if(result != null && "true".equals(String.valueOf(result.get("result")))){
                        map.put("TRANS_STATE", "1");
                        map.put("RUN_STATE", "0");
                        map.put("RUN_STATUS", "已报送");
                        map.put("RESP_INFO", JSON.toJSONString(result));
                        map.put("FJFDA_EVEID", result.get("item_id"));
                    }else{
                        map.put("TRANS_STATE", "-1");
                        map.put("RESP_INFO", JSON.toJSONString(result));
                    }
                    foodManagementService.saveOrUpdate(map, "T_FJFDA_TRANS", ywId);
                }
            }
        }
        log.info("++++++++结束批量报送食药业务数据+++++++++++");
    }
}
