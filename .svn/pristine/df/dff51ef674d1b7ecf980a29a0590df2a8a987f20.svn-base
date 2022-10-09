/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.io.UnsupportedEncodingException;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年12月16日 上午9:24:25
 */
public class Encrypt {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(Encrypt.class);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年12月16日 上午9:30:32
     * @param args
     */
    public static void main(String[] args){
        String enCode = Encrypt.getBase64("fjrd@123");
        log.info(enCode);
        String deCode = Encrypt.getFromBase64(enCode);
        log.info(deCode);
    }
    /**
     * 
     * 描述 加密算法
     * @author Flex Hu
     * @created 2014年12月16日 上午9:30:47
     * @param str
     * @return
     */
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            log.error(e.getMessage());  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    /**
     * 
     * 描述 解密算法
     * @author Flex Hu
     * @created 2014年12月16日 上午9:30:56
     * @param s
     * @return
     */
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                log.error(e.getMessage());  
            }  
        }  
        return result;  
    }  
}
