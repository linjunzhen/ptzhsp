/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 描述：用于取号机扫二维码取号功能的加密解密
 *
 * @author Madison You
 * @created 2019-07-29 下午08:55:01
 */
public class AesUtils{

    /**
     * 描述：密码
     *
     * @author Madison You
     * @created 2019-07-29 下午08:55:01
     */
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 描述：加密
     *
     * @author Madison You
     * @created 2019-07-29 下午08:55:01
     */
    public static byte[] encrypt(byte[] data, byte[] key){
        try{
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(key));
            return cipher.doFinal(data);
        }catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 描述：解密
     *
     * @author Madison You
     * @created 2019-07-29 下午08:55:01
     */
    public static byte[] decrypt(byte[] data, byte[] key){
        try{
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(key));
            return cipher.doFinal(data);
        }catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encryptToBase64(String data, String key){
        byte[] valueByte = encrypt(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(valueByte);
    }

    public static String decryptFromBase64(String data, String key){
        byte[] originalData = Base64.getDecoder().decode(data.getBytes());
        byte[] valueByte = decrypt(originalData, key.getBytes(StandardCharsets.UTF_8));
        return new String(valueByte, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec getSecretKeySpec (byte[] key){
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        byte[] enCodeFormat = secretKey.getEncoded();
        return new SecretKeySpec(enCodeFormat,"AES");
    }

}
















































