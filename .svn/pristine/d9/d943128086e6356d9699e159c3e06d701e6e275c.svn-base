/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import net.evecom.platform.ems.util.BASE64Encoder;
import sun.misc.BASE64Decoder;


/**
 * 描述:SM2工具类
 *
 * @author Madison You
 * @created 2020/12/17 9:45:00
 * @version 1.0
 */
public class Sm2UtilForSJJXXZX {

    /**
     * 获取公钥，私钥
     * [0]公钥
     * [1]私钥
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public static String[] getKeys() throws NoSuchAlgorithmException {
        /*获取一条SM2曲线参数*/
        X9ECParameters ecParameters = GMNamedCurves.getByName("sm2p256v1");

        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(ecParameters.getCurve(), ecParameters.getG(), ecParameters.getN());
        //1.创建密钥生成器
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        //2.初始化生成器,带上随机数
        keyPairGenerator.init(new ECKeyGenerationParameters(domainParameters, SecureRandom.getInstance("SHA1PRNG")));
        //3.生成密钥对
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = keyPairGenerator.generateKeyPair();

        //私钥，16进制格式
        BigInteger privatekey = ((ECPrivateKeyParameters) asymmetricCipherKeyPair.getPrivate()).getD();
        String privateKeyHex = privatekey.toString(16);

        //提取公钥点
        ECPoint ecPoint = ((ECPublicKeyParameters) asymmetricCipherKeyPair.getPublic()).getQ();
        //16进制格式，是否压缩公钥,公钥前面的02或者03表示是压缩公钥,04表示未压缩公钥,04的时候,可以去掉前面的04
        String publicKeyHex = Hex.toHexString(ecPoint.getEncoded(false));

        String[] str = new String[]{publicKeyHex, privateKeyHex};
        return str;
    }


    /**
     * 加密
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public static String encrypt(String jsonData, String publicKeyHex) {
        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(),
                sm2ECParameters.getN());

        //Hex公钥还原公钥
        byte[] puiKeyByte = Hex.decode(publicKeyHex);
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(puiKeyByte);
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);

        //新国密标准C1C3C2
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));

        byte[] arrayOfBytes = null;
        try {
            byte[] in = jsonData.getBytes();
            //SM2加密
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
        } catch (Exception e) {
            System.out.println("SM2加密时出现异常:" + e.getMessage());
            return null;
        }
        //byte[]转Hex密文
        return Hex.toHexString(arrayOfBytes);
    }

    /**
     * 解密
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public static String decrypt(String hexData, String privateKeyHex) {
        //获取一条SM2曲线参数
        X9ECParameters ecParameters = GMNamedCurves.getByName("sm2p256v1");
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(ecParameters.getCurve(), ecParameters.getG(), ecParameters.getN());

        //Hex密文转byte[]
        byte[] dataEncByte = Hex.decode(hexData);
        //Hex私钥还原私钥
        BigInteger privateKey = new BigInteger(privateKeyHex, 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKey, domainParameters);

        //新国密标准C1C3C2
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(false, privateKeyParameters);

        //processBlock得到Base64格式，并解码
        byte[] decryptByte = null;
        try {
            //SM2解密
            decryptByte = sm2Engine.processBlock(dataEncByte, 0, dataEncByte.length);
        } catch (InvalidCipherTextException e) {
            System.out.println("SM2解密时出现异常:" + e.getMessage());
            return null;
        }

        //得到明文
        String data = new String(decryptByte);
        return data;
    }


//    public static void main(String[] args) throws Exception {
//        String pubKey = "041c910eadde0e1cb1e4fcd19a4f1b91e521cdd64adc953875e0836e9948234e21504499143250963bed7c03f95380aef8b13f0e166c467623d0758d1c9bc97970";
//        String priKey = "86716a80924e99af1b2e52fce4795d7afcd76038faae889445e131991abdf9bf";
//        System.out.println("公钥：" + pubKey);
//        System.out.println("私钥：" + priKey);
//
//        String encBase64Data = new BASE64Encoder().encode(data.getBytes("utf-8"));
//        System.out.println("加密后的Base64：" + encBase64Data);
//
//        String encHexData = encrypt(encBase64Data, pubKey);
//        System.out.println("加密后的Hex密文：" + encHexData);
//
//        String decBase64Data = decrypt(encHexData, priKey);
//        System.out.println("解密后的Base64：" + decBase64Data);
//
//        String decData = new String(new BASE64Decoder().decodeBuffer(decBase64Data));
//        System.out.println("解密后的明文：" + decData);
//    }
    
    
}
