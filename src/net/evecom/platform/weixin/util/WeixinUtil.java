/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.evecom.core.util.FileUtil;
import net.evecom.platform.weixin.controller.WeachatController;
import net.evecom.platform.weixin.model.WxAccessToken;
import net.evecom.platform.weixin.model.WxAccessTokenInfo;
import net.evecom.platform.weixin.resp.Menu;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述  公众平台通用接口工具类
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class WeixinUtil {
    /**
     * log4J声明
     */
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    /**
     * 获取access_token的接口地址（GET） 限200（次/天）
     */
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?" +
            "grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 菜单创建（POST） 限100（次/天）
     */
    public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?" +
            "access_token=ACCESS_TOKEN";
    /**
     * 微信JSSDK的ticket请求URL地址 
     */
    public final static String WEIXIN_JSSDK_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/" +
            "getticket?access_token=ACCESS_TOKEN&type=jsapi"; 
    /**
     * 微信appid
     */
    public final static String APP_ID="wx4c0383e173c689b3";
    /**
     * 密码
     */
    public final static String SECRET="d4624c36b6795d1d99dcf0547af5443d";
    
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
        BufferedReader bufferedReader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

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
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
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
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        } finally {  
            if (bufferedReader != null) {  
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
     * 获取access_token
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }
    
    /**
     * 初始化微信公众号access_token 项目启动时调用
     * 描述
     * @author Laura Song
     */
    public static void initWxAccessToken(){
        
        new Thread(new Runnable(){

            @Override
            public void run() {
                while(true){
                    try{
                        // 获取access_token
                        WxAccessTokenInfo.accessToken = getWxAccessToken();
                        if(WxAccessTokenInfo.accessToken != null){
                            // 休眠2小时
                            Thread.sleep(7000*1000);
                        }else{
                            Thread.sleep(1000*3);// 获取的access_token为空 休眠3秒
                        }
                        
                    }catch(Exception e){
                        System.out.println("发生异常:"+e.getMessage());
                        e.printStackTrace();
                        try{
                            Thread.sleep(1000*10);// 发生异常休眠1秒
                        } catch(Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
                
            }
            
        }).start();
        
    }
    
    /**
     * 获取微信access_token
     * @author Laura Song
     * @return
     */
    public static WxAccessToken getWxAccessToken(){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String appId=properties.getProperty("APP_ID");
        String appsecret=properties.getProperty("APP_SECRET");
        AccessToken token  = WeixinUtil.getAccessToken(appId, appsecret);
        WxAccessToken token1 = new WxAccessToken();
        if(null != token){
            token1.setTokenName(token.getToken());
            token1.setExpireSecond(token.getExpiresIn());            
        }
        return token1;
    }
    
    /**
     * 获取UnionId
     * 方法：@param openId
     * 方法：@return
     */
    public static String getUnionId(String openId){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String appId=properties.getProperty("APP_ID");
        String appScret=properties.getProperty("APP_SECRET");
        AccessToken accessToken=getAccessToken(appId,appScret);
        String access_token =accessToken.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + openId
                + "&lang=zh_CN";
        JSONObject jsonObject = httpRequest(url, "GET", null);
        String unionId=null;
       // 如果请求成功
        if (null != jsonObject) {
            try {
                //String subscribe=jsonObject.getString("subscribe");//是否关注，为0未关注
                unionId=jsonObject.getString("unionid");
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"));
            }
        }
        return unionId;
    }
    
    public static void main(String[] args) {
//        AccessToken accessToken=getAccessToken(APP_ID,SECRET);
//        String ticket=getJSSDKTicket();
//        log.info(ticket);
        createMenuDef();
        //log.info(getRandomString());
    }
    
    /**
     * 获取微信JSSDK的ticket 
     * @author Sundy Sun
     */
    public static String getJSSDKTicket() {  
        AccessToken accessToken = getAccessToken(APP_ID, SECRET);
        String access_token = accessToken.getToken();
        String returnString = "";
        String requestUrl = WEIXIN_JSSDK_TICKET_URL.replace("ACCESS_TOKEN", access_token);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                returnString = jsonObject.getString("ticket");
            } catch (JSONException e) {
                returnString = null;
            }
        }
        return returnString;
    } 
    
    /**
     * 获取微信JSSDK的ticket 
     * @author Sundy Sun
     */
    public static String getOpenByCode(String code) {  
        String returnStr="";
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String appId=properties.getProperty("APP_ID");
        String appScret=properties.getProperty("APP_SECRET");
        String requestUrl ="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId
                +"&secret="+appScret+"&code="+code+"&grant_type=authorization_code";
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        // 如果请求成功
        if (null != jsonObject) {
            try {
                returnStr = jsonObject.getString("openid");
            } catch (JSONException e) {
                returnStr = null;
            }
        }
        return returnStr;
    } 
    
    /**
     * 创建菜单
     * 
     * @param menu 菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        // 拼装创建菜单的url
        String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), 
                        jsonObject.getString("errmsg"));
            }
        }
        return result;
    }
    
    
    /**
     * 创建公众平台菜单
     * 
     */
    public static void createMenuDef()  {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String serviceUrl = properties.getProperty("serviceUrl");
        String serviceUrl1 = properties.getProperty("serviceUrl1");
        String appId=properties.getProperty("APP_ID");
        String appScret=properties.getProperty("APP_SECRET");
//        String bindurl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
//        appId+"&redirect_uri="+serviceUrl
//        +"/userBindController.do?toBind&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
        //String bindurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
                //+ serviceUrl + "/userBindController.do?toBind&response_type=code&state=1#wechat_redirect";
        String bindUrl=serviceUrl+"/userBindController.do?toBind";
        //String zxUrl=serviceUrl+"/busInteractController.do?toConsult";
        //String tousuUrl=serviceUrl+"/busInteractController.do?toTousu";
        String onlineUrl=serviceUrl+"/busInteractController.do?toOnline";
        String reviewUrl=serviceUrl+"/busInteractController.do?toReview";
        
        String queryUrl=serviceUrl1+"/apply-inquiry";// 办事进度
        //String quequeryUrl=serviceUrl+"/apply-quequery";// 排队号查询 查询当前排队号所处位数
        //String queYwqueryUrl=serviceUrl+"/apply-queYwquery";// 排队业务查询 
        //String handleGuideUrl = serviceUrl1 + "/handle-guide";//办事指南
        String homeUrl = serviceUrl1 + "/home";//首页
        String searchItemUrl = serviceUrl1 + "/search";//搜索页
        String bookUrl = serviceUrl1 + "/book";//办事预约
        String sfsyUrl = serviceUrl + "/webpage/bsdt/applyform/estate/expressInfo.jsp";
        
        
        String bindRe="";
        //String zxRe="";
        //String tousuRe="";
        String queryRe="";
        //String quequeryRe="";
        //String queqYwueryRe="";
        String onlineRe="";
        String reviewRe="";
        //String handleGuideRe="";
        String homeRe="";
        String searchItemRe="";
        String bookRe="";
        String sfsyRe="";
        try {
            bindRe= URLEncoder.encode(bindUrl, "utf-8"); 
            //zxRe= URLEncoder.encode(zxUrl, "utf-8");
            //tousuRe= URLEncoder.encode(tousuUrl, "utf-8");
            queryRe=URLEncoder.encode(queryUrl, "utf-8");
            //quequeryRe=URLEncoder.encode(quequeryUrl, "utf-8");
            //queqYwueryRe=URLEncoder.encode(queYwqueryUrl, "utf-8");
            onlineRe=URLEncoder.encode(onlineUrl, "utf-8");
            reviewRe=URLEncoder.encode(reviewUrl, "utf-8");
            //handleGuideRe = URLEncoder.encode(handleGuideUrl, "utf-8");
            homeRe = URLEncoder.encode(homeUrl, "utf-8");
            searchItemRe = URLEncoder.encode(searchItemUrl, "utf-8");
            bookRe = URLEncoder.encode(bookUrl, "utf-8");
            sfsyRe=URLEncoder.encode(sfsyUrl, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            log.error(e1.getMessage());
        } 
        //String code="http%3A%2F%2Fsunyan.pagekite.me%2Fptzhsp%2FuserBindController.do%3FtoBind";
        String bindauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+bindRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
       /* String zxauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+zxRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String tousuauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+tousuRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";*/
        String queryauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+queryRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
       // String quequeryauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
       //         appId+"&redirect_uri="+quequeryRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        //String queYwqueryauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
        //        appId+"&redirect_uri="+queqYwueryRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String onlineauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+onlineRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String reviewauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+reviewRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        //String handleGuide = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + 
        //        appId+"&redirect_uri="+handleGuideRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String home = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + 
                appId+"&redirect_uri="+homeRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String searchItem = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + 
                appId+"&redirect_uri="+searchItemRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String book = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + 
                appId+"&redirect_uri="+bookRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        
        String sfsy="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+sfsyRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        
//        String user_define_menu = "{\"button\":[{\"name\":\"办事查询\",\"sub_button\":["
//                +"{\"type\":\"view\",\"name\":\"进度查询\",\"key\":\"01_WORKQUERY\",\"url\":\""
//                +queryauth+"\"},"
//                +"{\"type\":\"view\",\"name\":\"业务排队查询\",\"key\":\"02_WORKQUERY\",\"url\":\""
//                +serviceUrl+"/callController.do?goQueQuery\"},"
//                +"{\"type\":\"view\",\"name\":\"排队查询\",\"key\":\"03_WORKQUERY\",\"url\":\""
//                +serviceUrl+"/callController.do?goBusWaitNum\"},"
//                +"{\"type\":\"click\",\"name\":\"我的办件\",\"key\":\"04_WORKQUERY\"},"
//                +"{\"type\":\"view\",\"name\":\"办事首页\",\"key\":\"05_WORKQUERY\",\"url\":\""
//                +home+"\"},"
//                +"{\"type\":\"view\",\"name\":\"事项查询\",\"key\":\"06_WORKQUERY\",\"url\":\""
//                +searchItem+"\"}]},"
//                + "{\"name\":\"服务中心\",\"sub_button\":["
//                +"{\"type\":\"view\",\"name\":\"办事指南\",\"key\":\"01_SERCENTER\",\"url\":\""
//                +serviceUrl+"/busInteractController.do?bsGuide\"},"
//                +"{\"type\":\"view\",\"name\":\"办件评价\",\"key\":\"02_SERCENTER\",\"url\":\""
//                +reviewauth+"\"},"
//                +"{\"type\":\"view\",\"name\":\"用户注册\",\"key\":\"03_SERCENTER\",\"url\":\""
//                +serviceUrl+"/wregisterController.do?register\"},"
//                +"{\"type\":\"view\",\"name\":\"账号绑定\",\"key\":\"04_SERCENTER\",\"url\":\""
//                +bindauth+"\"},"
//                +"{\"type\":\"view\",\"name\":\"办事预约\",\"key\":\"05_SERCENTER\",\"url\":\""
//                +book+"\"}]},"
//                + "{\"name\":\"咨询投诉\",\"sub_button\":["
//                + "{\"type\":\"view\",\"name\":\"业务咨询\",\"key\":\"01_CONSULT\",\"url\":\""
//                +zxauth+"\"},"
//                 + "{\"type\":\"view\",\"name\":\"网上评议\",\"key\":\"02_CONSULT\",\"url\":\""
//                +onlineauth+"\"},"
//                + "{\"type\":\"view\",\"name\":\"投诉监督\",\"key\":\"03_CONSULT\",\"url\":\""
//                +tousuauth+"\"},"
//                + "{\"type\":\"view\",\"name\":\"顺丰速运\",\"key\":\"04_CONSULT\",\"url\":\""
//                +sfsy+"\"}"
//                + "]}]}";
        String user_define_menu = "{\"button\":[{\"name\":\"办事查询\",\"sub_button\":["
                +"{\"type\":\"view\",\"name\":\"进度查询\",\"key\":\"01_WORKQUERY\",\"url\":\""
                +queryauth+"\"},"
                +"{\"type\":\"view\",\"name\":\"业务排队查询\",\"key\":\"02_WORKQUERY\",\"url\":\""
                +serviceUrl+"/callController.do?goQueQuery\"},"
                +"{\"type\":\"view\",\"name\":\"排队查询\",\"key\":\"03_WORKQUERY\",\"url\":\""
                +serviceUrl+"/callController.do?goBusWaitNum\"},"
                +"{\"type\":\"click\",\"name\":\"我的办件\",\"key\":\"04_WORKQUERY\"},"
                +"{\"type\":\"view\",\"name\":\"事项查询\",\"key\":\"06_WORKQUERY\",\"url\":\""
                +searchItem+"\"}]},"
                + "{\"name\":\"服务中心\",\"sub_button\":["
                +"{\"type\":\"view\",\"name\":\"办事指南\",\"key\":\"01_SERCENTER\",\"url\":\""
                +serviceUrl+"/busInteractController.do?bsGuide\"},"
                +"{\"type\":\"view\",\"name\":\"办件评价\",\"key\":\"02_SERCENTER\",\"url\":\""
                +reviewauth+"\"},"
                +"{\"type\":\"view\",\"name\":\"用户注册\",\"key\":\"03_SERCENTER\",\"url\":\""
                +serviceUrl+"/wregisterController.do?register\"},"
                +"{\"type\":\"view\",\"name\":\"账号绑定\",\"key\":\"04_SERCENTER\",\"url\":\""
                +bindauth+"\"},"
                +"{\"type\":\"view\",\"name\":\"办事预约\",\"key\":\"05_SERCENTER\",\"url\":\""
                +book+"\"}]},"
                + "{\"name\":\"办事评议\",\"sub_button\":["
                + "{\"type\":\"view\",\"name\":\"网上评议\",\"key\":\"02_CONSULT\",\"url\":\""
                +onlineauth+"\"},"
                +"{\"type\":\"view\",\"name\":\"办事首页\",\"key\":\"05_CONSULT\",\"url\":\""
                +home+"\"},"
                + "{\"type\":\"view\",\"name\":\"顺丰速运\",\"key\":\"04_CONSULT\",\"url\":\""
                +sfsy+"\"}"
                + "]}]}";

        // 此处改为自己想要的结构体，替换即可
        AccessToken accessToken=getAccessToken(appId,appScret);
        String access_token =accessToken.getToken();
        //System.out.println(access_token);
        log.info(user_define_menu);

        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
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
     * 创建公众平台菜单
     * 
     */
    public static void createMenuDef1()  {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String serviceUrl = properties.getProperty("serviceUrl");
        String appId=properties.getProperty("APP_ID");
        String appScret=properties.getProperty("APP_SECRET");
//        String bindurl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
//        appId+"&redirect_uri="+serviceUrl
//        +"/userBindController.do?toBind&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
        //String bindurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
                //+ serviceUrl + "/userBindController.do?toBind&response_type=code&state=1#wechat_redirect";
        String bindUrl=serviceUrl+"/userBindController.do?toBind";
        String zxUrl=serviceUrl+"/busInteractController.do?toConsult";
        String tousuUrl=serviceUrl+"/busInteractController.do?toTousu";
        String queryUrl=serviceUrl+"/busInteractController.do?toRateQuery";
        String onlineUrl=serviceUrl+"/busInteractController.do?toOnline";
        String reviewUrl=serviceUrl+"/busInteractController.do?toReview";
        
        String bindRe="";
        String zxRe="";
        String tousuRe="";
        String queryRe="";
        String onlineRe="";
        String reviewRe="";
        try {
            bindRe= URLEncoder.encode(bindUrl, "utf-8"); 
            zxRe= URLEncoder.encode(zxUrl, "utf-8");
            tousuRe= URLEncoder.encode(tousuUrl, "utf-8");
            queryRe=URLEncoder.encode(queryUrl, "utf-8");
            onlineRe=URLEncoder.encode(onlineUrl, "utf-8");
            reviewRe=URLEncoder.encode(reviewUrl, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            log.error(e1.getMessage());
        } 
        //String code="http%3A%2F%2Fsunyan.pagekite.me%2Fptzhsp%2FuserBindController.do%3FtoBind";
        String bindauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+bindRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String zxauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+zxRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String tousuauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+tousuRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String queryauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+queryRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String onlineauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+onlineRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String reviewauth="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                appId+"&redirect_uri="+reviewRe+"&response_type=code&scope=snsapi_base&state=123#wechat_redirec";
        String user_define_menu = "{\"button\":[{\"name\":\"办事查询\",\"sub_button\":["
                +"{\"type\":\"view\",\"name\":\"进度查询\",\"key\":\"01_WORKQUERY\",\"url\":\""
                +queryauth+"\"},"
                +"{\"type\":\"click\",\"name\":\"我的办件\",\"key\":\"02_WORKQUERY\"}]},"
                + "{\"name\":\"服务中心\",\"sub_button\":[" 
                +"{\"type\":\"view\",\"name\":\"办事指南\",\"key\":\"01_SERCENTER\",\"url\":\""
                +serviceUrl+"/busInteractController.do?bsGuide\"},"
                +"{\"type\":\"view\",\"name\":\"办件评价\",\"key\":\"02_SERCENTER\",\"url\":\""
                +reviewauth+"\"},"
                +"{\"type\":\"view\",\"name\":\"用户注册\",\"key\":\"03_SERCENTER\",\"url\":\"" 
                +serviceUrl+"/wregisterController.do?register\"},"
                +"{\"type\":\"view\",\"name\":\"账号绑定\",\"key\":\"04_SERCENTER\",\"url\":\""
                +bindauth+"\"}"+"]},"
                + "{\"name\":\"咨询投诉\",\"sub_button\":["
                + "{\"type\":\"view\",\"name\":\"业务咨询\",\"key\":\"01_CONSULT\",\"url\":\""
                +zxauth+"\"},"
                 + "{\"type\":\"view\",\"name\":\"网上评议\",\"key\":\"02_CONSULT\",\"url\":\""
                +onlineauth+"\"},"
                + "{\"type\":\"view\",\"name\":\"投诉监督\",\"key\":\"03_CONSULT\",\"url\":\""
                +tousuauth+"\"}"
                + "]}]}";
        // 此处改为自己想要的结构体，替换即可
        AccessToken accessToken=getAccessToken(appId,appScret);
        String access_token =accessToken.getToken();
        log.info(user_define_menu);

        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
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
     * chars
     */
    public static final char[] CHARS = { '1','2', '3', '4', '5', '6', '7', '8',
        '9', '0'};
    /**
     *  随机字符的字典
     */
    public static Random random = new Random();// 随机数
   /**
    * 获取随机数
    * @return
    */
    public static String getRandomString() {
        // 字符的缓存
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 4; i++) {// 循环 六次
            buf.append(CHARS[random.nextInt(CHARS.length)]);
        }
        log.info(buf.toString());
        return buf.toString();
    }
    
    
}
