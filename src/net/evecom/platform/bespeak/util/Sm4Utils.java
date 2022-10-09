/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 描述
 * @author Rider Chen
 * @created 2017年6月30日 上午10:32:23
 */
public class Sm4Utils {
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(Sm4Utils.class);

    /**
     * 16位加密KEY
     */
    private String secretKey = "";
    /**
     * 16位加密KEY
     */
    private String iv = "";
    /**
     * 
     */
    private boolean hexString = false;
    /**
     * 描述
     * @author Toddle Chen
     * @created Jul 28, 2017 5:59:40 PM
     */
    public Sm4Utils() {
    }
    
    /**
     * 描述
     * 
     * @author Toddle Chen
     * @created Jul 27, 2017 11:34:36 AM
     * @param secretkey
     * @param ivString
     * @param hexBool
     */
    public Sm4Utils(String secretkey, String ivString, boolean hexBool) {
        this.secretKey = secretkey;
        this.iv = ivString;
        this.hexString = hexBool;
    }
    /**
     * 描述
     * @author Toddle Chen
     * @created Jul 28, 2017 5:59:54 PM
     * @param plainText
     * @return
     */
    public String encryptDataECB(String plainText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4SetkeyEnc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4CryptEcb(ctx, plainText.getBytes("UTF-8"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    /**
     * 描述
     * @author Toddle Chen
     * @created Jul 28, 2017 6:00:01 PM
     * @param cipherText
     * @return
     */
    public String decryptDataECB(String cipherText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4SetkeyDec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4CryptEcb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    /**
     * 描述
     * @author Toddle Chen
     * @created Jul 28, 2017 6:00:04 PM
     * @param plainText
     * @return
     */
    public String encryptDataCBC(String plainText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4SetkeyEnc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4CryptCbc(ctx, ivBytes, plainText.getBytes("UTF-8"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    /**
     * 描述
     * @author Toddle Chen
     * @created Jul 28, 2017 6:00:08 PM
     * @param cipherText
     * @return
     */
    public String decryptDataCBC(String cipherText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4SetkeyDec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4CryptCbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    /**
     * 描述
     * @author Toddle Chen
     * @created Jul 28, 2017 6:00:11 PM
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String plainText = "本文件是福建省经济信息中心拟建的";
        Sm4Utils sm4 = new Sm4Utils("4028b8815ccf2ffe", "EveComF54A3s2D1g", false);
        // sm4.secretKey =
        // "4028b8815ccf2ffe";//"UISwD9fW6cFh9SNS";//JeF8U9wHFOMfs2Y8//平台分配的加密盐
        // sm4.hexString = false;
        // sm4.iv = "EveComF54A3s2D1g";//初始化向量IV
        log.info("ECB模式");
        String cipherText = sm4.encryptDataECB(plainText);
        log.info("密文: " + cipherText);
        plainText = sm4.decryptDataECB(cipherText);
        log.info("明文: " + plainText);

        log.info("CBC模式");
        cipherText = sm4.encryptDataCBC(plainText);
        log.info("密文: " + cipherText);
        plainText = sm4.decryptDataCBC(cipherText);
        log.info("明文: " + plainText);
        
    }
}
