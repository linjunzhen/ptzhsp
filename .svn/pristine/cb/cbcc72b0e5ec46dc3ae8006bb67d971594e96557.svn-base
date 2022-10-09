/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class UploadPic {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(UploadPic.class);
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        String access_token=WeiXinUtils.getAccessToken();  
//        String picurl="http:\/\/mmbiz.qpic.cn\/mmbiz\/mIeYWGhpbr7lKx1HDjz3n" +
//              "fGMMhnnLmiboicPsbJibmYyU1cAqIMCUXQU2Dtu9P3kfw5nHt0GF9rmLVErrYzYvz6LA\/0";
        
        String filepath="D:\\yuyan\\mypicture\\faxing.png";    
        String urlStr = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+access_token;   
        Map<String, String> textMap = new HashMap<String, String>();  
        textMap.put("name", "testname");  
        Map<String, String> fileMap = new HashMap<String, String>();  
        fileMap.put("userfile", filepath);  
        String ret = formUpload(urlStr, textMap, fileMap);  
        log.info(ret);  
    }  
    
    /** 
     * 上传图片 
     *  
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */  
    public static String formUpload(String urlStr, Map<String, String> textMap,  
            Map<String, String> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        BufferedReader reader = null;  
        OutputStream out = null;  
        DataInputStream in = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent",  
                            "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type",  
                    "multipart/form-data; boundary=" + BOUNDARY);  
  
            out = new DataOutputStream(conn.getOutputStream());  
            // text  
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
                            "\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\""  
                            + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file  
            if (fileMap != null) {  
                in = fileMapNotNull(fileMap, out, in, BOUNDARY);  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据  
            StringBuffer strBuf = new StringBuffer();  
            reader = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            log.info("发送POST请求出错。" + urlStr);  
            log.error(e.getMessage());  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
            if (reader != null) {  
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }  
            }  
            if (in != null) {  
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }  
            }  
            if (out != null) {  
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }  
            }  
        }  
        return res;  
    }

    private static DataInputStream fileMapNotNull(Map<String, String> fileMap, OutputStream out, DataInputStream in,
            String BOUNDARY) throws IOException, FileNotFoundException {
        Iterator iter = fileMap.entrySet().iterator();  
        while (iter.hasNext()) {  
            Map.Entry entry = (Map.Entry) iter.next();  
            String inputName = (String) entry.getKey();  
            String inputValue = (String) entry.getValue();  
            if (inputValue == null) {  
                continue;  
            }  
            File file = new File(inputValue);  
            String filename = file.getName();  
            String contentType = new MimetypesFileTypeMap()  
                    .getContentType(file);  
            if (filename.endsWith(".png")) {  
                contentType = "image/png";  
            }  
            if (contentType == null || contentType.equals("")) {  
                contentType = "application/octet-stream";  
            }  
  
            StringBuffer strBuf = new StringBuffer();  
            strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
                    "\r\n");  
            strBuf.append("Content-Disposition: form-data; name=\""  
                    + inputName + "\"; filename=\"" + filename  
                    + "\"\r\n");  
            strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
            out.write(strBuf.toString().getBytes());  
  
            in = new DataInputStream(  
                    new FileInputStream(file));  
            int bytes = 0;  
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }  
            in.close();  
        }
        return in;
    }  
}
