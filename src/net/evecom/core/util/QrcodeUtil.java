/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import com.swetake.util.Qrcode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 描述 二维码工具类
 * @author Madison You
 * @created 2020年07月20日 23:33:20
 */
public class QrcodeUtil {

    /**
     * 描述:log声明
     *
     * @author Madison You
     * @created 2020/7/20 23:33:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(QrcodeUtil.class);

    /**
     * 描述:生成二维码内存图片
     *
     * @author Madison You
     * @created 2020/7/20 23:34:00
     * @param codeValue 码值
     * @param codeSize 二维码尺寸，取值1-40
     * @return
     */
    public static BufferedImage qrcodeCommon(String codeValue, int codeSize) {
        BufferedImage bufImg =null;
        //Qrcode对象：字符串->boolean[][]
        Qrcode qrCodeHandler = new Qrcode();
        //设置二维码的排错率：7% L<M<Q<H30%  ：排错率越高,可存储的信息越少；但是对二维码清晰对要求越小
        qrCodeHandler.setQrcodeErrorCorrect('M');
        //可存放的信息类型：N：数字、  A：数字+A-Z  B：所有
        qrCodeHandler.setQrcodeEncodeMode('B');
        //尺寸：取值范围：1-40
        qrCodeHandler.setQrcodeVersion(codeSize);
        try{
            byte[] contentBytes = codeValue.getBytes("UTF-8");
            boolean[][] codeOut = qrCodeHandler.calQrcode(contentBytes);
            int imgSize = 67 + 12 * (codeSize - 1);
            //BufferedImage：内存中的图片
            bufImg = new BufferedImage(imgSize,imgSize,BufferedImage.TYPE_INT_RGB );
            //创建一个画板
            Graphics2D gs = bufImg.createGraphics();
            gs.setBackground(Color.WHITE);//将画板的背景色设置为白色
            gs.clearRect( 0,0, imgSize,imgSize); //初始化
            gs.setColor(Color.BLACK);//设置 画板上 图像的颜色（二维码的颜色）
            int pixoff = 2;
            for(int j=0;j<codeOut.length;j++) {
                for(int i=0;i<codeOut.length;i++) {
                    if(codeOut[j][i]) {
                        gs.fillRect(j*3+pixoff , i*3+pixoff, 3, 3);
                    }
                }
            }
            gs.dispose(); //释放空间
            bufImg.flush(); //清理
        } catch (Exception e) {
            log.error("生成二维码内存图片出错");
        }
        return bufImg;
    }

    /**
     * 描述:将二维码图片转为ByteArrayOutputStream字节码
     *
     * @author Madison You
     * @created 2020/7/20 23:43:00
     * @param codeValue 码值
     * @param imgType 二维码图片类型
     * @param size 二维码尺寸，取值1-40
     * @param bos
     * @return
     */
    public static void createBosQrcode(String codeValue, String imgType, int size, ByteArrayOutputStream bos) {
        try{
            BufferedImage bufImg =   qrcodeCommon(codeValue,size);
            ImageIO.write(bufImg, imgType, bos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("生成二维码图片出错");
        }
    }
}
