/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 描述 单点登录回调内容加解密
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年5月23日 上午10:33:51
 */
public class AESOperator {
    /**
     * 密钥
     */
    private static final String KEY = "sNqcHTjzHy^Pv!jb";
    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * aes加密
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncryptCode(String content, String key) throws Exception {
        return aesEncrypt(content, key);
    }

    /**
     * aes解密
     * 
     * @param encrypt
     *            内容
     * @return
     * @throws Exception
     */
    public static String aesDecryptCode(String encrypt, String key) throws Exception {
        return aesDecrypt(encrypt, key);
    }

    /**
     * aes解密
     * 
     * @param encrypt
     *            内容
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt) throws Exception {
        return aesDecrypt(encrypt, KEY);
    }

    /**
     * aes加密
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) throws Exception {
        return aesEncrypt(content, KEY);
    }

    /**
     * 将byte[]转为各种进制的字符串
     * 
     * @param bytes
     *            byte[]
     * @param radix
     *            可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * 
     * @param bytes
     *            待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * base 64 decode
     * 
     * @param base64Code
     *            待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * AES加密
     * 
     * @param content
     *            待加密的内容
     * @param encryptKey
     *            加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES加密为base 64 code
     * 
     * @param content
     *            待加密的内容
     * @param encryptKey
     *            加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     * 
     * @param encryptBytes
     *            待解密的byte[]
     * @param decryptKey
     *            解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, "UTF-8");
    }

    /**
     * 将base 64 code AES解密
     * 
     * @param encryptStr
     *            待解密的base 64 code
     * @param decryptKey
     *            解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }
}
