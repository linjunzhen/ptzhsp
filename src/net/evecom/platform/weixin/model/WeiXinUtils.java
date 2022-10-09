/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述 微信接口开发
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class WeiXinUtils {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WeiXinUtils.class);
    
    public static void main(String[] args) {
        //exec("dd").toString();
        addPicMatter("pic");
        //getCounts();
    }
    
    
    /**
     * 添加视频素材
     * @param cmd
     * @return
     */
    public static Object addVideoMatter(String cmd){
        String access_token=getAccessToken();
        String curl="curl https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+access_token
            +" -F media=@D:\\yuyan\\mypicture\\Wildlife.wmv -F" +
            "  description='{\"title\":video01, \"introduction\":test01}'";
//        String curl="curl -F media=@D:\\yuyan\\mypicture\\Wildlife.wmv" +
//        " https://api.weixin.qq.com/cgi-bin/material/add_material?type=image&media=yuyun&access_token=" +access_token;
        LineNumberReader br = null;
        try {
            Process process = Runtime.getRuntime().exec(curl);
            br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                log.info(line);
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }
    /**
     * 添加图片素材
     * @param cmd
     * @return
     */
    public static Object addPicMatter(String cmd){
        String access_token=getAccessToken();
        String curl="curl -F media=@D:\\yuyan\\mypicture\\yuyu.png" +
            " https://api.weixin.qq.com/cgi-bin/material/add_material?type=image&media=yuyun&access_token=" 
            +access_token;
        LineNumberReader br = null;
        try {
            Process process = Runtime.getRuntime().exec(curl);
            br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                log.info(line);
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }
    /**
     * 执行curl命令上传图片
     * @param cmd
     * @return
     */
    public static Object exec(String cmd) {
        String access_token=getAccessToken();
        String curl="curl -F upload=@D:\\yuyan\\mypicture\\faxing2.png " +
            "-F press=OK http://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" +access_token;
        LineNumberReader br = null;
        try {
            Process process = Runtime.getRuntime().exec(curl);
            br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                log.info(line);
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }
    public static void getImageId() {
        String access_token=getAccessToken();
        String action="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+access_token;
        String query="{\"type\":image,\"offset\":0,\"count\":2}";
//        String query="{\"media_id\":7F3CC79503DF}";
//        String action = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="+ access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
//            http.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(query.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
//            JSONObject obj = JSONArray.parseObject(message);
//            access_token=obj.getString("access_token");
//            return access_token;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    public static void getCounts() {
        String access_token=getAccessToken();
        String action="https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token="+access_token;
//        String query="{\"media_id\":7F3CC79503DF}";
//        String action = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="+ access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
//            http.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            //os.write(query.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
//            JSONObject obj = JSONArray.parseObject(message);
//            access_token=obj.getString("access_token");
//            return access_token;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 新增用久素材
     * @return
     */
    public static void addMatters(){
        String access_token=getAccessToken();
        String action="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="+access_token;
        String query="{\"articles\": [{\"title\": images01,\"thumb_media_id\": images001,"
                +"\"author\": Sundy,\"digest\": ,\"show_cover_pic\": 0,\"content\":,"
                +"\"content_source_url\": ''}]}";
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
//            http.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            //os.write(query.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
//            JSONObject obj = JSONArray.parseObject(message);
//            access_token=obj.getString("access_token");
//            return access_token;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 获取access_token
     */
    public static String getAccessToken() {
        // 此处改为自己想要的结构体，替换即可
        String access_token ="";
        String action = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=wx4c0383e173c689b3&secret=d4624c36b6795d1d99dcf0547af5443d";
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            //os.write(query.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
            JSONObject obj = JSONArray.parseObject(message);
            access_token=obj.getString("access_token");
            return access_token;
        }catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 获取access_token
     */
    public static String queryExpress() {
        // rand 随机数
        //id 快递公司名称，此处shentong就是申通
        //fronweb 是否从web提交 postid 快递号码，此处为 568629598688
        //sub 一个固定字段, %E6%9F%A5%E8%AF%A2解码后为 查询 两个汉字
        String action = "http://wap.kuaidi100.com/wap_result.jsp?rand=35447&id=yuantong&fromWeb=null" +
                "&postid=880848663085030449&sub=%E6%9F%A5%E8%AF%A";
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            //os.write(query.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
            //JSONObject obj = JSONArray.parseObject(message);
            //access_token=obj.getString("access_token");
            return message;
        }catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            //httpUrlConn.setSSLSocketFactory(ssf);
 
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
 
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
 
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
 
            // 将返回的输入流转换成字符串
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
 
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
//            System.err.println("Weixin server connection timed out.");
            log.error(ce.getMessage());
        } catch (Exception e) {
//            System.err.println("https request error:{}");
            log.error(e.getMessage());
        }finally{
            if (inputStreamReader!=null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return jsonObject;
    }
    
    /**
     * 添加客服
     */
    public static void addKefu() {
        String user_define_menu = "{\"kf_account\" : \"958678296@qq.com\",\"nickname\" " +
            ": \"客服1\",\"password\" : \"pswmd5\",}";
        // 此处改为自己想要的结构体，替换即可
        String access_token = "e-Kz6NsrjnxAu8DzEihoHh65lYohyXluTKh3D9XnocwU8NpXqsTuF9vqo" +
                "ZvWUjz6EDSET_geo7uU2k4HPMclf0zkvtjqAaOxyIMKSCxxIMIHACdAIAZSU";

        String action = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token="
                + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(user_define_menu.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
    /**
     * 创建菜单
     */
    public static void createMenu() {
        String user_define_menu = 
              "{\"button\":[{\"type\":\"click\",\"name\":\"消息管理\",\"key\":\"10_PROMANAGE\"}"
                + ",{\"name\":\"网上办事\",\"sub_button\":[" +
                "{\"type\":\"click\",\"name\":\"办件查询\",\"key\":\"02_BUSQUERY\"},"
                +"{\"type\":\"click\",\"name\":\"办事指南\",\"key\":\"02_WORKGUIDE\"}]},"
             + "{\"name\":\"公众服务\",\"sub_button\":[{\"type\":\"click\",\"name\":\"咨询\",\"key\":\"03_ZIXUN\"},"
                + "{\"type\":\"click\",\"name\":\"投诉\",\"key\":\"03_TOUSU\"}"
                + ",{\"type\":\"click\",\"name\":\"评议\",\"key\":\"03_PINGYI\"}"
                + ",{\"type\":\"click\",\"name\":\"网上缴费\",\"key\":\"03_ONLINEPAY\"}"
                + "]}]}";
        // 此处改为自己想要的结构体，替换即可
        String access_token = "e-Kz6NsrjnxAu8DzEihoHh65lYohyXluTKh3D9XnocwU8NpXqsTuF9vqo" +
            "ZvWUjz6EDSET_geo7uU2k4HPMclf0zkvtjqAaOxyIMKSCxxIMIHACdAIAZSU";

        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(user_define_menu.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            log.info(message);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
