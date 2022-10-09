/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.util;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * sm3算法加密
 * 
 * @author Scolder Lin
 * @version 2021-12-30
 */

@Slf4j
public class Sm3Utils {
    /**
     * UTF-8编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 编码Sm3Utils
     */
    private Sm3Utils() {
        Security.addProvider(new BouncyCastleProvider());
    }
    /**
     * Sm3UtilsInstance
     */
    private static class Sm3UtilsInstance {
        /**
         * INSTANCE
         */
        private static final Sm3Utils INSTANCE = new Sm3Utils();
    }
    /**
     * Sm3UtilsInstance
     */
    public static Sm3Utils getInstance() {
        return Sm3Utils.Sm3UtilsInstance.INSTANCE;
    }

    /**
     * sm3算法加密
     *
     * @param paramStr
     *            待加密字符串
     * @return 返回加密后，固定长度=32的16进制字符串
     * @explain
     */
    public String encrypt(String paramStr) {
        // 将返回的hash值转换成16进制字符串
        String resultHexString = null;
        // 将字符串转换成byte数组
        byte[] srcData = new byte[0];
        try {
            srcData = paramStr.getBytes(ENCODING);
            // 调用hash()
            byte[] resultHash = hash(srcData);
            // 将返回的hash值转换成16进制字符串
            resultHexString = ByteUtils.toHexString(resultHash);
        } catch (UnsupportedEncodingException e) {
            // log.error("", e);
        }
        return resultHexString;
    }

    /**
     * 返回长度=32的byte数组
     *
     * @param srcData
     * @return
     * @explain 生成对应的hash值
     */
    private byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 通过密钥进行加密
     *
     * @param key
     *            密钥
     * @param srcData
     *            被加密的byte数组
     * @return
     * @explain 指定密钥进行加密
     */
    /*private byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }*/

    /**
     * 判断源数据与加密数据是否一致
     *
     * @param srcStr
     *            原字符串
     * @param sm3HexString
     *            16进制字符串
     * @return 校验结果
     * @explain 通过验证原数组和生成的hash数组是否为同一数组，验证2者是否为同一数据
     */
    private boolean verify(String srcStr, String sm3HexString) {
        boolean flag = false;
        try {
            byte[] srcData = srcStr.getBytes(ENCODING);
            byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
            byte[] newHash = hash(srcData);
            if (Arrays.equals(newHash, sm3Hash))
                flag = true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        try {
            // 测试二：json
            String json = "{\"name\":\"Marydon\",\"website\":\"http://www.cnblogs.com/Marydon20170307\"}";
            String hex = Sm3Utils.getInstance().encrypt(json);
            System.out.println(hex);// 0b0880f6f2ccd817809a432420e42b66d3772dc18d80789049d0f9654efeae5c
            // 验证加密后的16进制字符串与加密前的字符串是否相同
            boolean flag = Sm3Utils.getInstance().verify(json, hex);
            System.out.println(flag);// true
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
