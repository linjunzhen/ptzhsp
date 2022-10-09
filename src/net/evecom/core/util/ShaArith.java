/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 描述 编码解码
 * @author Toddle Chen
 * @created Mar 12, 2015 5:08:59 PM
 */
public class ShaArith {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ShaArith.class);
    /**
     * ABCDE
     */
    private static final int[] ABCDE = {0x67452301,0xefcdab89,0x98badcfe,0x10325476, 0xc3d2e1f0 };
    /**
     * 摘要数据存储数组 
     */
    private static int[] digestInt = new int[5];
    /**
     * 计算过程中的临时数据存储数组
     */
    private static int[] tmpData = new int[80]; // 计算sha-1摘要
    /**
     * 描述 process_input_bytes
     * @author Toddle Chen
     * @created Mar 12, 2015 5:15:08 PM
     * @param bytedata
     * @return
     */
    private static int processInputBytes(byte[] bytedata) {
        //初试化常量 
        System.arraycopy(ABCDE, 0, digestInt, 0, ABCDE.length);
        //格式化输入字节数组，补10及长度数据
        byte[] newbyte = byteArrayFormatData(bytedata);
        //获取数据摘要计算的数据单元个数
        int MCount = newbyte.length / 64;
        //循环对每个数据单元进行摘要计算
        for (int pos = 0; pos < MCount; pos++) {
            //将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中
            for (int j = 0; j < 16; j++) {
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
            }
            //摘要计算函数
            encrypt();
        }
        return 20;
    } 
    /**
     * 描述 格式化输入字节数组格式
     * @author Toddle Chen
     * @created Mar 12, 2015 5:15:52 PM
     * @param bytedata
     * @return
     */
    private static byte[] byteArrayFormatData(byte[] bytedata) {
        // 补0数量
        int zeros = 0; // 补位后总位数
        int size = 0; // 原始数据长度
        int n = bytedata.length; // 模64后的剩余位数
        int m = n % 64; // 计算添加0的个数以及添加10后的总长度
        if (m < 56) {
            zeros = 55 - m;
            size = n - m + 64;
        } else if (m == 56) {
            zeros = 63;
            size = n + 8 + 64;
        } else {
            zeros = 63 - m + 56;
            size = (n + 64) - m + 64;
        } 
        // 补位后生成的新数组内容
        byte[] newbyte = new byte[size];
        //复制数组的前面部分
        System.arraycopy(bytedata, 0, newbyte, 0, n);
        //获得数组Append数据元素的位置
        int l = n; // 补1操作
        newbyte[l++] = (byte) 0x80;
        //补0操作
        for (int i = 0; i < zeros; i++) {
            newbyte[l++] = (byte) 0x00;
        }
        //计算数据长度，补数据长度位共8字节，长整型
        long N = (long) n * 8;
        byte h8 = (byte) (N & 0xFF);
        byte h7 = (byte) ((N >> 8) & 0xFF);
        byte h6 = (byte) ((N >> 16) & 0xFF);
        byte h5 = (byte) ((N >> 24) & 0xFF);
        byte h4 = (byte) ((N >> 32) & 0xFF);
        byte h3 = (byte) ((N >> 40) & 0xFF);
        byte h2 = (byte) ((N >> 48) & 0xFF);
        byte h1 = (byte) (N >> 56);
        newbyte[l++] = h1;
        newbyte[l++] = h2;
        newbyte[l++] = h3;
        newbyte[l++] = h4;
        newbyte[l++] = h5;
        newbyte[l++] = h6;
        newbyte[l++] = h7;
        newbyte[l++] = h8;
        return newbyte;
    }
    /**
     * 描述 f1
     * @author Toddle Chen
     * @created Mar 12, 2015 5:16:41 PM
     * @param x
     * @param y
     * @param z
     * @return
     */
    private static int f1(int x, int y, int z) {
        return (x & y) | (~x & z);
    }
    /**
     * 描述 f2
     * @author Toddle Chen
     * @created Mar 12, 2015 5:16:55 PM
     * @param x
     * @param y
     * @param z
     * @return
     */
    private static int f2(int x, int y, int z) {
        return x ^ y ^ z;
    }
    /**
     * 描述 f3
     * @author Toddle Chen
     * @created Mar 12, 2015 5:17:02 PM
     * @param x
     * @param y
     * @param z
     * @return
     */
    private static int f3(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }
    /**
     * 描述 f4
     * @author Toddle Chen
     * @created Mar 12, 2015 5:17:09 PM
     * @param x
     * @param y
     * @return
     */
    private static int f4(int x, int y) {
        return (x << y) | x >>> (32 - y);
    }
    /**
     * 单元摘要计算函数
     */
    private static void encrypt() {
        for (int i = 16; i <= 79; i++) {
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14]
                    ^ tmpData[i - 16], 1);
        }
        int[] tmpabcde = new int[5];
        for (int i1 = 0; i1 < tmpabcde.length; i1++) {
            tmpabcde[i1] = digestInt[i1];
        }
        for (int j = 0; j <= 19; j++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[j] + 0x5a827999;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int k = 20; k <= 39; k++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[k] + 0x6ed9eba1;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int l = 40; l <= 59; l++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[l] + 0x8f1bbcdc;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int m = 60; m <= 79; m++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[m] + 0xca62c1d6;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int i2 = 0; i2 < tmpabcde.length; i2++) {
            digestInt[i2] = digestInt[i2] + tmpabcde[i2];
        }
        for (int n = 0; n < tmpData.length; n++) {
            tmpData[n] = 0;
        }
    }
    /**
     * 描述 4字节数组转换为整数
     * @author Toddle Chen
     * @created Mar 12, 2015 5:17:34 PM
     * @param bytedata
     * @param i
     * @return
     */
    private static int byteArrayToInt(byte[] bytedata, int i) {
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16)
                | ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
    } 
    /**
     * 描述 整数转换为4字节数组
     * @author Toddle Chen
     * @created Mar 12, 2015 5:17:43 PM
     * @param intValue
     * @param byteData
     * @param i
     */
    private static void intToByteArray(int intValue, byte[] byteData, int i) {
        byteData[i] = (byte) (intValue >>> 24);
        byteData[i + 1] = (byte) (intValue >>> 16);
        byteData[i + 2] = (byte) (intValue >>> 8);
        byteData[i + 3] = (byte) intValue;
    } 
    /**
     * 描述 将字节转换为十六进制字符串
     * @author Toddle Chen
     * @created Mar 12, 2015 5:17:50 PM
     * @param ib
     * @return
     */
    private static String byteToHexString(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    } 
    /**
     * 描述 将字节数组转换为十六进制字符串
     * @author Toddle Chen
     * @created Mar 12, 2015 5:17:55 PM
     * @param bytearray
     * @return
     */
    private static String byteArrayToHexString(byte[] bytearray) {
        StringBuffer strDigest = new StringBuffer();
        for (int i = 0; i < bytearray.length; i++) {
            strDigest.append(byteToHexString(bytearray[i]));
        }
        return strDigest.toString();
    }
    
    /**
     * 描述 计算sha-1摘要，返回相应的字节数组
     * @author Toddle Chen
     * @created Mar 12, 2015 5:18:01 PM
     * @param byteData
     * @return
     */
    public static byte[] getDigestOfBytes(byte[] byteData) {
        processInputBytes(byteData);
        byte[] digest = new byte[20];
        for (int i = 0; i < digestInt.length; i++) {
            intToByteArray(digestInt[i], digest, i * 4);
        }
        return digest;
    } 
    /**
     * 描述 计算sha-1摘要，返回相应的十六进制字符串
     * @author Toddle Chen
     * @created Mar 12, 2015 5:18:08 PM
     * @param byteData
     * @return
     */
    public static String getDigestOfString(byte[] byteData) {
        return byteArrayToHexString(getDigestOfBytes(byteData));
    } 
    /**
     * 描述 测试
     * @author Toddle Chen
     * @created Mar 12, 2015 5:18:17 PM
     * @param args
     * @throws Exception
     */
    /*public static void main(String[] args) throws Exception {
        String param = "f:/zw-12.jpg";
        File file = new File(param); //A37ABA826DE8CF610FF4E792146936363F988E6B
        byte[] b = FileUtil.getByte(file);
        log.info("加密前：" + param);
        String digest = getDigestOfString(b);
        log.info("加密后：" + digest);
    }*/
}
