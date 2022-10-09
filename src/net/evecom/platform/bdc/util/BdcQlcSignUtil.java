/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.evecom.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.system.service.DictionaryService;


/**
 * 描述  不动产全流程材料签章工具类
 * @author Allin Lin
 * @created 2020年8月16日 下午12:29:53
 */
public class BdcQlcSignUtil {
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcQlcSignUtil.class);
    
    
    
   /** 
    * 描述    接口-创建外部个人用户   (创建外部机构)
    * @author Allin Lin
    * @created 2020年8月16日 下午6:03:21
    * @param variables
    * @param urlDicType  接口字典值
    * @return
    * @throws Exception
    */
    public static Map<String, Object> creExUserOrInstitutions(Map<String, Object> variables,String urlDicType)
            throws Exception{
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        Map<String, Object> result = new HashMap<String, Object>();
        String param = JSON.toJSONString(variables);
        String projectId = dictionaryService.get("bdcSignConfig", "projectId").get("DIC_DESC").toString();
        String secret = dictionaryService.get("bdcSignConfig", "secret").get("DIC_DESC").toString();
        String url = dictionaryService.get("bdcSignConfig", urlDicType).get("DIC_DESC").toString();
       /* String projectId = "1001002";//用户在天印系统唯一ID
        String secret = "c9ca18caea264f794fbb3341dbf36315";//密钥
        String url = "http://121.43.183.139:8035/V1/accounts/outerAccounts/create";//请求地址(外部用户)
        String url = "http://121.43.183.139:8035/V1/organizations/outerOrgans/create";//请求地址(外部机构)       
*/        String signature = getSHA(param, secret);//签名值 
        try {
          //调取接口
            String respContent = sendPost(url, param, "UTF-8", projectId, signature);
          //解析返回结果的json数据
            result = getResultByRespContent(respContent);
        }catch(Exception e){
            result.put("message", "未正确请求到天印签章业务接口："+urlDicType+",服务异常如下："+e.getMessage()); 
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                param,result.toString()));  
        return result;       
    }
    /**
     * 获取签章需要的随机数(1000,100000,)
     * @return
     */
    public static int getRandomNumOfSign(){
        return StringUtil.getRandomIntNumber(100000,1000);
    }
    /**
     * 描述    签章接口返回结果解析json
     * @author Allin Lin
     * @created 2020年2月23日 下午9:31:15
     * @param respContent
     * @return
     */
    public static  Map<String, Object> getResultByRespContent(String respContent){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(respContent)){
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            int code = Integer.parseInt(resultMap.get("errCode").toString());//响应代码(为0表示成功)
            if(resultMap.get("data")!=null && (code== 0)){       
                result.put("data", resultMap.get("data"));
            }
            result.put("errCode", code);
            result.put("errShow", resultMap.get("errShow"));
            result.put("msg", String.valueOf(resultMap.get("msg")));
            return result;          
        }else{
            result.put("msg","未正确请求到签章相关业务接口，服务异常");
            return result;
        }
    }
    
    
    
    /**
     * 描述    根据密钥进行HmacSHA256算法加密（转换成16位进制的字符串）
     * @author Allin Lin
     * @created 2020年8月16日 下午12:43:02
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String getSHA(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
           sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
    
    
    
    /**
     * 描述    向指定URL发送POST方法的请求
     * @author Allin Lin
     * @created 2020年2月23日 下午10:08:54
     * @param url 发送请求的url 
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。或json
     * @param responesCode 设置请求响应的编码格式 
     * @param projectId signature
     * @return
     */  
    public static String sendPost(String url, String param,String responesCode,String projectId,String signature){
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
            conn.setRequestProperty("X-timevale-project-id", projectId);
            conn.setRequestProperty("X-timevale-signature", signature);
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
    
    
    
    /** 
     * 描述    排序
     * @author Allin Lin
     * @created 2020年8月16日 下午8:07:27
     * @param params
     * @return
     */
    public static String getSortParams(Map<String, Object> params) {
        params.remove("sign");
        StringBuffer contnt = new StringBuffer();
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        // 这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            Object value = params.get(key);
            // 将值为空的参数排除
            /*
             * if (!StringUtil.isNull(value)) { keyList.add(key); }
             */
            keyList.add(key);
        }
        Collections.sort(keyList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        // 将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt.append(key).append(params.get(key));
        }
        return contnt.toString();
    }
    
    /**
     * 
     * @Description 接口-文件直传
     * @author Luffy Cai
     * @date 2020年8月17日
     * @param variables
     * @param urlDicType
     * @return
     * @throws Exception Map<String,Object>
     */
    public static Map<String, Object> uploadFiles(String filePath, String urlDicType) throws Exception {
        DictionaryService dictionaryService = (DictionaryService)AppUtil.getBean("dictionaryService");
        Map<String, Object> result = new HashMap<String, Object>();
         String projectId = dictionaryService.get("bdcSignConfig", "projectId").get("DIC_DESC").toString();
        String url = dictionaryService.get("bdcSignConfig", urlDicType).get("DIC_DESC").toString();
       /* String projectId = "1001002";// 用户在天印系统唯一ID
        String secret = "c9ca18caea264f794fbb3341dbf36315";// 密钥
        String url = "http://121.43.183.139:8035/V1/files/upload";// 请求地址
*/        try {
            // 调取接口
            String respContent = sendFilesPost(url, filePath, "UTF-8", projectId);
            // 解析返回结果的json数据
            result = getResultByRespContent(respContent);
        } catch (Exception e) {
            result.put("message", "未正确请求到天印签章业务接口：" + urlDicType + ",服务异常如下：" + e.getMessage());
        }
        log.info(String.format("urlDicType===%s,url===%s,queryResult===%s", urlDicType, url, result.toString()));
        return result;
    }    
    
    /**
     * 描述 向指定URL发送POST方法的请求
     * 
     * @author Luffy Cai
     * @created 2020年8月17日 上午10:08:54
     * @param url          发送请求的url
     * @param filePath     材料模板生成PDF文件路径
     * @param responesCode 设置请求响应的编码格式
     * @param projectId
     * @return
     */
    public static String sendFilesPost(String url, String filePath, String responesCode, String projectId) {
        OutputStream out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                return result.toString();
            }
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("X-timevale-project-id", projectId);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            out = new DataOutputStream(conn.getOutputStream());
            // 发送请求参数
            out.write(head);
            inFile = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = inFile.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            inFile.close();
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }
    
}
