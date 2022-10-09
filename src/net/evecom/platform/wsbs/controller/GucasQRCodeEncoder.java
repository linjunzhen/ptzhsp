/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swetake.util.Qrcode;

/**
 * 
 * 描述 生成二维码
 * 
 * @author Kester Chen
 * @created 2018年9月19日 上午11:00:53
 */
public class GucasQRCodeEncoder {

    /**
     * mAXDATASIZESMALL
     */
    public static int mAXDATASIZESMALL = 120;

    /**
     * mAXDATASIZELARGE
     */
    public static int mAXDATASIZELARGE = 500;

    /**
     * Encoding the information to a QRCode, size of the information must be
     * less than 84 byte.
     * 
     * @param srcValue
     * @param qrcodePicfilePath
     * @return
     */
    public static boolean encode(String srcValue, String qrcodePicfilePath) {
        int MAX_DATA_LENGTH = mAXDATASIZESMALL; // 限制生成二维码的数据最大大小
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageWidth = 128; /*
                               * 113是预先计算出来的.
                               * 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
                               */
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(0); // 37字节, (37-1)*3+2+3-1+1 = 113
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 ;
                         * 生成的image的宽度大小必须 >=该值, 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
            // System.out.println("二维码数据长度(字节):" + qrcodeDataLen);
        } else {
            // System.out.println("Generate QRCode image error! Data size is "
            // + dataLength + ", it is lager than 84 bytes.");
            return false;
        }
        g.dispose();
        bi.flush();
        /* generate image */
        File f = new File(qrcodePicfilePath);
        String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
        try {
            ImageIO.write(bi, suffix, f); // "png"
        } catch (IOException ioe) {
            // System.out.println("Generate QRCode image error!"
            // + ioe.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Encoding the information to a QRCode, size of the information must be
     * less tah 500 byte.
     * 
     * @param srcValue
     * @param qrcodePicfilePath
     * @return
     */
    public static boolean encode500(String srcValue, String qrcodePicfilePath) {
        int MAX_DATA_LENGTH = mAXDATASIZELARGE; // 限制生成二维码的数据最大大小.
        // 500字节的原始数据, 生成二维码时, 是89宽度
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageWidth = 269; /*
                               * 269是预先计算出来的.
                               * 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
                               */
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(18); // 0<= version <=40; 89字节,
                                         // (89-1)*3+2+3-1+1 = 269
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 =
                         * 136; 生成的image的宽度大小必须 >=(136+1), 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
            // System.out.println("二维码数据长度(字节):" + qrcodeDataLen);
        } else {
            return false;
        }
        g.dispose();
        bi.flush();
        /* generate image */
        File f = new File(qrcodePicfilePath);
        String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
        // System.out.println(suffix);
        try {
            ImageIO.write(bi, suffix, f); // "png"
        } catch (IOException ioe) {
            // System.out.println("Generate QRCode image error!"
            // + ioe.getMessage());
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws Exception {

        String data = "https://blog.csdn.net/tongdengquan/article/details/78973475";
        // System.out.println("字节数: " + data.getBytes().length);
        String qrCodeName = "imgs\\qrCode_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        GucasQRCodeEncoder.encode(data, qrCodeName);
        // System.out.println(GucasQRCodeDecoder.decode(qrCodeName));
    }
}