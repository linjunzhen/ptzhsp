/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.alibaba.fastjson.JSON;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述   社保业务通用方法
 * @author Allin Lin
 * @created 2020年2月20日 下午10:38:45
 */
public class SbCommonUtil {
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SbCommonUtil.class);
    
    
    /**
     * 令牌值
     */
    public static  String token;
    
    
    /**
     * 过期时间点（时间戳毫秒数）
     */
    public static  long expiration;
    
    /**
     * 刷新令牌过期时间点（时间戳毫秒数）
     */
    public static  long refreshTxpiration;
    
    /**
     * 刷新令牌token值
     */
    public static  String refreshToken;
         
    /**
     * 描述    获取授权实时有效令牌值：token
     * @author Allin Lin
     * @created 2020年2月20日 下午10:55:20
     * @return
     */
    public static String getRealToken(){
        SimpleDateFormat sdff=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        log.info("系统当前缓存令牌值："+token+ ",-时间对应为："+sdff.format(new Date(System.currentTimeMillis()))+
                ",过期时间点："+expiration+"-时间对应为："+sdff.format(new Date(expiration))+
                ",刷新令牌过期时间点:"+refreshTxpiration+"-时间对应为："+sdff.format(new Date(refreshTxpiration))+
                ",刷新令牌token值："+refreshToken);
       /* SimpleDateFormat sdff=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long current = System.currentTimeMillis();//系统当前时间戳
        String dq = sdff.format(new Date(current));   
        log.info("系统当前时间："+dq);     
        if(expiration != 0){
            String sd = sdff.format(new Date(expiration));   
            log.info("授权获取令牌值接口，过期时间点："+sd); 
        }*/       
        if(token==null|| "".equals(token)||"undefined".equals(token)){
            //重新调取授权获取令牌值接口
            Map<String, Object> result = getOauthToken("getToken");
            if(result.get("token")!=null){
                token = result.get("token").toString();
                expiration = Long.parseLong(result.get("expiration").toString());
                refreshTxpiration = Long.parseLong(result.get("refreshTxpiration").toString());             
                refreshToken = result.get("refreshToken").toString();
            }
        }else{
            //判断token值是否失效
            long currentTime = System.currentTimeMillis()+(5*60*1000);//系统当前时间戳,（目前我方时间慢对方5分钟）
            if(currentTime>expiration && currentTime <=refreshTxpiration){
               //调取授权刷新令牌接口
                Map<String, Object> result = getOauthRefreshToken("getToken",refreshToken);
                if(result.get("token")!=null){
                    token = result.get("token").toString();
                    expiration = Long.parseLong(result.get("expiration").toString());
                    refreshTxpiration = Long.parseLong(result.get("refreshTxpiration").toString());           
                    refreshToken = result.get("refreshToken").toString();
                }
            }else if(currentTime >refreshTxpiration){
               //重新调取授权获取令牌值接口
                Map<String, Object> result = getOauthToken("getToken");
                if(result.get("token")!=null){
                    token = result.get("token").toString();
                    expiration = Long.parseLong(result.get("expiration").toString());
                    refreshTxpiration = Long.parseLong(result.get("refreshTxpiration").toString());       
                    refreshToken = result.get("refreshToken").toString();
                }
            }
        }
        log.info("当前实时有效令牌值token为："+token);
        return token;
    }
    
    
    /** 
     * 描述      授权获取令牌Token接口
     * @author Allin Lin
     * @created 2020年2月21日 上午12:10:28
     * @param urlDicType 接口字典值
     * @return
     */
    public static Map<String, Object> getOauthToken(String urlDicType){
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        //接口参数需后续配置成字典值
        String username = dictionaryService.get("sbConfig", "username").get("DIC_DESC").toString();
        String password = dictionaryService.get("sbConfig", "password").get("DIC_DESC").toString();
        String grant_type = dictionaryService.get("sbConfig", "grant_type").get("DIC_DESC").toString();
        String client_id = dictionaryService.get("sbConfig", "client_id").get("DIC_DESC").toString();
        String client_secret = dictionaryService.get("sbConfig", "client_secret").get("DIC_DESC").toString();
        StringBuffer urlBuffer=new StringBuffer("");
        String url = dictionaryService.get("sbConfig", urlDicType).get("DIC_DESC").toString();
        //接口返回参数设置
        String respContent = "";//接口响应信息 
        Map<String, Object> result = new HashMap<String, Object>();
        urlBuffer.append("username=").append(username)
        .append("&password=").append(password).append("&grant_type=")
        .append(grant_type).append("&client_id=").append(client_id)
        .append("&client_secret=").append(client_secret);
        try {
            respContent = HttpRequestUtil.sendPost(url, urlBuffer.toString());
            //解析返回结果的json数据
            result = getResultByToken(respContent);
        } catch (Exception e) {
            log.error("调用接口-授权获取令牌值，异常："+e.getMessage());        
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                urlBuffer.toString(),result.toString()));
        return result;
    }
    
    
    
    
    /**
     * 描述    授权刷新令牌Token
     * @author Allin Lin
     * @created 2020年2月20日 下午10:58:01
     * @return
     */
    public static Map<String, Object> getOauthRefreshToken(String urlDicType,String refreshToken){ 
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        String refresh_token = refreshToken;
        //接口参数需后续配置成字典值
        String grant_type =  dictionaryService.get("sbConfig", "refreshGrantType").get("DIC_DESC").toString();;
        String client_id =  dictionaryService.get("sbConfig", "client_id").get("DIC_DESC").toString();;
        String client_secret =  dictionaryService.get("sbConfig", "client_secret").get("DIC_DESC").toString();;
        StringBuffer urlBuffer=new StringBuffer("");
        String url = dictionaryService.get("sbConfig", urlDicType).get("DIC_DESC").toString();
        //接口返回参数设置
        String respContent = "";//接口响应信息 
        Map<String, Object> result = new HashMap<String, Object>();
        urlBuffer.append("refresh_token=").append(refresh_token)
        .append("&grant_type=").append(grant_type).append("&client_id=")
        .append(client_id).append("&client_secret=").append(client_secret);
        try {
            respContent = HttpRequestUtil.sendPost(url, urlBuffer.toString());
            //解析返回结果的json数据
            result = getResultByToken(respContent);
        } catch (Exception e) {
            log.error("调用接口-授权刷新获取令牌值失败，异常："+e.getMessage());        
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                urlBuffer.toString(),result.toString()));
        return result;
    }
    
    
    /**
     * 描述    解析授权获取令牌值接口返回结果
     * @author Allin Lin
     * @created 2020年2月20日 下午11:28:47
     * @param respContent
     * @return
     */
    public static  Map<String, Object> getResultByToken(String respContent){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(respContent)){
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            String value = String.valueOf(resultMap.get("value"));//令牌值
            if(!"".equals(value) && null!=value && !"undefined".equals(value)){
                if(resultMap.get("refreshToken")!=null){ 
                    Map<String, Object> refreshMap = (Map<String, Object>)resultMap.get("refreshToken");
                    result.put("refreshTxpiration", refreshMap.get("expiration"));//刷新时间戳
                    result.put("refreshToken", refreshMap.get("value"));//刷新令牌值
                }
                if(resultMap.get("expiration")!=null){ //过期时间戳
                    result.put("expiration", resultMap.get("expiration"));
                }
                result.put("token", resultMap.get("value"));//令牌值
                return result;//接口请求成功
            }else if(resultMap.get("code")!=null){
                result.put("code", resultMap.get("code"));
                result.put("message", resultMap.get("message"));
                return result;//接口请求失败
            }else if(resultMap.get("error")!=null){
                result.put("error", resultMap.get("error"));
                result.put("error_description", resultMap.get("error_description"));
                return result;//接口请求失败
            }else {
                result.put("timestamp", resultMap.get("timestamp"));
                result.put("status", resultMap.get("status"));
                result.put("error", resultMap.get("error"));
                result.put("message", resultMap.get("message"));
                result.put("path", resultMap.get("path"));
                return result;//接口异常
            }
        }else{
            result.put("message","未正确请求到授权获取令牌值接口，服务异常");
            return result;
        }
    }

    /**
     * 描述    获取社保业务流水号
     * @author Allin Lin
     * @created 2020年3月11日 上午10:34:11
     * @return
     */
    public static synchronized long getSbId(){
        String ywlsh = "";
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        Map<String, Object> variables = dictionaryService.get("sbConfig", "ywlsh");
        ywlsh = variables.get("DIC_DESC").toString();
        long newYwlsh=Long.parseLong(ywlsh)+1;
        variables.put("DIC_DESC",newYwlsh);
        dictionaryService.saveOrUpdate(variables, "T_MSJW_SYSTEM_DICTIONARY", variables.get("DIC_ID").toString());
        return newYwlsh;
    }
   /**
    * 描述    社保业务通用接口（GET请求）
    * @author Allin Lin
    * @created 2020年2月23日 下午9:07:13
    * @param info 查询参数
    * @param token  令牌值
    * @param urlDicType 接口字典值
    * @return
    * @throws Exception 
    */
    public static Map<String, Object> sbCommonGet(Map<String, Object> info,String urlDicType,String token) 
            throws Exception{
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        Map<String, Object> result = new HashMap<String, Object>();//存放结果集合
        String param = "";
        StringBuffer urlBuffer=new StringBuffer("");
        String respContent = "";
        String url = dictionaryService.get("sbConfig", urlDicType).get("DIC_DESC").toString();
        Iterator iterator=info.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Object> entry=(Map.Entry)iterator.next();
            String key=entry.getKey();
            String val=URLEncoder.encode(StringUtil.getString(entry.getValue()),"UTF-8");
            urlBuffer.append(key).append("=").append(val).append("&");
        }
        if(urlBuffer.length() > 0){
            param = urlBuffer.substring(0, urlBuffer.length()-1);
        } 
        try {
            respContent = sendSbGet(url, param, "UTF-8", token);
            //解析返回结果的json数据
            result = getResultByRespContent(respContent);
        }catch(Exception e){
            result.put("message", "未正确请求到社保业务接口："+urlDicType+",服务异常如下："+e.getMessage()); 
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                param,result.toString()));
        return result;
    }
    
    /**
     * 描述    社保业务通用接口（POST请求）
     * @author Allin Lin
     * @created 2020年2月23日 下午10:04:07
     * @param info 查询参数
     * @param urlDicType 接口字典值
     * @param token 令牌值
     * @param type 参数类型（1：参数拼接,如：param=value&param1=value1 2：参数封装成json格式）
     * @return urlDicType 接口字典值
     * @throws Exception
     */
    public static Map<String, Object> sbCommonPost(Map<String, Object> info,String urlDicType,String token,String type) 
            throws Exception{
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        Map<String, Object> result = new HashMap<String, Object>();//存放结果集合       
        StringBuffer urlBuffer=new StringBuffer("");
        String respContent = "";
        String url = dictionaryService.get("sbConfig", urlDicType).get("DIC_DESC").toString();
        String param = "";
        if("2".equals(type)){
            param = JSON.toJSONString(info);
        }else if("1".equals(type)){ 
            Iterator iterator=info.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,Object> entry=(Map.Entry)iterator.next();
                String key=entry.getKey();
                String val=URLEncoder.encode(StringUtil.getString(entry.getValue()),"UTF-8");
                urlBuffer.append(key).append("=").append(val).append("&");
            }
            if(urlBuffer.length() > 0){
                param = urlBuffer.substring(0, urlBuffer.length()-1);
            }  
        }        
        try {
            respContent = sendSbPost(url, param, "UTF-8", token);
            //解析返回结果的json数据
            result = getResultByRespContent(respContent);
        }catch(Exception e){
            result.put("message", "未正确请求到社保业务接口："+urlDicType+",服务异常如下："+e.getMessage()); 
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                param,result.toString()));
        return result;
    }
    
    
    
    
    
    /**
     * 描述    社保相关业务接口返回结果解析json
     * @author Allin Lin
     * @created 2020年2月23日 下午9:31:15
     * @param respContent
     * @return
     */
    public static  Map<String, Object> getResultByRespContent(String respContent){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(respContent)){
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            String code = String.valueOf(resultMap.get("code"));//响应代码
            if(resultMap.get("data")!=null){       
                result.put("data", resultMap.get("data"));
            }
            result.put("code", code);
            result.put("message", String.valueOf(resultMap.get("message")));
            return result;          
        }else{
            result.put("message","未正确请求到社保相关业务接口，服务异常");
            return result;
        }
    }
    
    /** 
     * 描述    向指定URL发送GET方法的请求
     * @author Allin Lin
     * @created 2020年2月23日 下午9:23:12
     * @param url  发送请求的url
     * @param param   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param responesCode 设置请求响应的编码格式 
     * @param authorization  token令牌值
     * @return   URL 所代表远程资源的响应结果
     */
    public static String sendSbGet(String url, String param,String responesCode,String authorization){
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json");           
            connection.setRequestProperty("Authorization", "Bearer "+ authorization);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
        return result.toString(); 
    }
    
    /**
     * 描述    向指定URL发送POST方法的请求
     * @author Allin Lin
     * @created 2020年2月23日 下午10:08:54
     * @param url 发送请求的url 
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param responesCode 设置请求响应的编码格式 
     * @param authorization token令牌值
     * @return
     */
    public static String sendSbPost(String url, String param,String responesCode,String authorization){
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+ authorization);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流 
            //2016年8月11日 14:53:01 增加输出流编码Rider Chen
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), responesCode));
            // 发送请求参数
            out.print(param);
            
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }
    
}
