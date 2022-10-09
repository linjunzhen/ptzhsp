/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import net.evecom.core.sm.Sm4Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 描述 不动产登记资料查询二维码数据加密类
 * @author Madison You
 * @created 2020年07月20日 23:33:20
 */
public class BdcDataSecretUtil {

    /**
     * 描述:log声明
     *
     * @author Madison You
     * @created 2020/7/20 23:33:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcDataSecretUtil.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 8:39:00
     * @param
     * @return
     */
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 22:19:00
     * @param 
     * @return 
     */
    public static final String KEY = "0123456789abcdef";

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 8:39:00
     * @param
     * @return
     */
    private static SecretKeySpec getSecretKeySpec (byte[] key){
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        byte[] enCodeFormat = secretKey.getEncoded();
        return new SecretKeySpec(enCodeFormat,"AES");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 8:39:00
     * @param
     * @return
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(key));
            return cipher.doFinal(data);
        } catch (Exception e){
            throw new RuntimeException("不动产登记资料查询二维码数据加密错误", e);
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 8:39:00
     * @param
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(key));
            return cipher.doFinal(data);
        } catch (Exception e){
            throw new RuntimeException("不动产登记资料查询二维码数据加密错误", e);
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 8:39:00
     * @param
     * @return
     */
    public static String encryptToBase64(String data, String key){
        byte[] valueByte = encrypt(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(valueByte);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/21 8:39:00
     * @param
     * @return
     */
    public static String decryptFromBase64(String data, String key){
        byte[] originalData = Base64.getDecoder().decode(data.getBytes());
        byte[] valueByte = decrypt(originalData, key.getBytes(StandardCharsets.UTF_8));
        return new String(valueByte, StandardCharsets.UTF_8);
    }

}
