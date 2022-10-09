/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.util;

import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;

import java.util.HashMap;
import java.util.Properties;

/**
 * 描述 推送到省服务总线的工具包
 * @author Adrian Bian
 * @created 2020年1月6日 下午12:15:43
 */
public class ProjectOfProvinceUtil {
    /**
     * 描述:获取省网总线accesstoken
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 13:03:00
     */
    public static String getZxAccessToken() {
        Properties properties = FileUtil.readProperties("project.properties");
        String zxAccessTokenUrl = properties.getProperty("ZX_ACCESS_TOKEN_URL");
        String grant_type = properties.getProperty("ZX_GRANT_TYPE");
        String client_id = properties.getProperty("ZX_CLIENT_ID");
        String client_secret = properties.getProperty("ZX_CLIENT_SECRET");
        HashMap<String, Object> params = new HashMap<>();
        params.put("grant_type", grant_type);
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        String result = HttpSendUtil.sendPostParams(zxAccessTokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        String tokenType = jsonObject.getString("token_type");
        return tokenType + " " + accessToken;
    }

//    public static void main(String[] args) {
//        System.out.println(getZxAccessToken());
//    }
}
