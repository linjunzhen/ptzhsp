/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.platform.hflow.controller.MaterConfigController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 描述：取号机二维码加密解密功能
 *
 * @author Madison You
 * @created 2019-07-29 下午08:28:01
 */
public class EncryptUtils{

    /**
     * 描述：加密码
     *
     * @author Madison You
     * @created 2019-07-29 下午08:28:01
     */
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);

    /**
     * 描述：生成sign(1.所有参数除了sign排序连成1个字符串 2.字符串最后加上key 3.sha1算法生成签名)
     *
     * @author Madison You
     * @created 2019-07-29 下午08:28:01
     */
    public static String buildSign(JSONObject jsonObject , String key) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (Map.Entry<String, Object> entry: jsonObject.entrySet()){
            if (entry.getValue() != null){
                treeMap.put(entry.getKey(), entry.getValue());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry: treeMap.entrySet()){
            stringBuilder.append(entry.getValue());
        }
        stringBuilder.append(key);
        return sha1(stringBuilder.toString());
    }

    /**
     * 描述：生成数据 aes
     *
     * @author Madison You
     * @created 2019-07-29 下午08:28:01
     */
    public static String buildData(JSONObject jsonObject, String key){
        return AesUtils.encryptToBase64(jsonObject.toJSONString(), key);
    }

    public static JSONObject decrypt(String data, String merKey){
        String jsonStr = AesUtils.decryptFromBase64(data, merKey);
        return JSON.parseObject(jsonStr);
    }

    public static String sha1(String srcStr){
        return hash("SHA-1", srcStr);
    }

    public static String hash(String algorithm, String srcStr){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes(StandardCharsets.UTF_8));
            return toHex(bytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes){
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }
}
