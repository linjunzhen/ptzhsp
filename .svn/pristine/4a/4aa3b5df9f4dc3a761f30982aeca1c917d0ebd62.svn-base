/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class SignUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SignUtil.class);
    /**
     * 与微信开发模式中的token保持一致
     */
    private static String token="weixinCourse";
    /**
     * 校验签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        //对token,timestamp,nonce按字典排序
        String[] paramArr=new String[]{token,timestamp,nonce};
        Arrays.sort(paramArr);
        //将排序后的结果拼接成一个字符串
        String content=paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
        
        String ciphertext=null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            //对拼接后的字符串进行SHA-1加密
            byte[] digest=md.digest(content.toString().getBytes());
            ciphertext=byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return ciphertext!=null?ciphertext.equals(signature.toUpperCase()):false;
        
    }
    /**
     * 将字节数组转化为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray){
        StringBuffer strdist=new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            strdist.append(byteToHexString(byteArray[i]));
        }
        return strdist.toString();
    }
    /**
     * 将字节转化为十六进制字符串
     * @param mbyte
     * @return
     */
    private static String byteToHexString(byte mbyte){
        char[] digit={ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr=new char[2];
        tempArr[0] = digit[(mbyte >>> 4) & 0X0F];
        tempArr[1] = digit[mbyte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
