/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
/**
 * 
 * 描述 RegisterCode
 * @author Kester Chen
 * @created 2018-3-29 上午12:41:51
 */
public class RegisterCode extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 清空缓冲区
        response.reset();
        // 设置响应类型
        response.setContentType("image/png");
        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(80, 28, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        g.setColor(this.getRandColor(230, 250));
        g.fillRect(0, 0, 80, 28);
        // 增加干扰线
        Random r = new Random();
        for (int i = 0; i < 133; i++) {
            g.setColor(this.getRandColor(150, 210));
            int x1 = r.nextInt(70);
            int y1 = r.nextInt(20);
            int x2 = r.nextInt(12);
            int y2 = r.nextInt(12);
            g.drawLine(x1, y1, x2 + x1, y2 + y1);
        }

        StringBuffer regCode = new StringBuffer( "");
        g.setFont(new Font("Times New Roman", Font.BOLD, 18));
        for (int i = 0; i < Constant.REGCODE_LEN; i++) {
            g.setColor(this.getRandColor(50, 150));
            int yzflag = i + 1;
            if (yzflag == 6) {
                yzflag = 1;
            }
            int num = this.getCode(yzflag);
            char temp;
            if (num > 10) { // 返回的是字符
                temp = (char) num;
                regCode.append(temp);
                g.drawString(String.valueOf(temp), 13 * i + 6, 16);
            } else {// 返回的是数字
                regCode.append(num);
                g.drawString(String.valueOf(num), 13 * i + 6, 16);
            }

        }

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "PNG", sos);
        sos.close();

        request.getSession().setAttribute(Constant.REGCODE_SESSION, regCode);

    }

    private Color getRandColor(int low, int high) {
        Random ran = new Random();
        int r = low + ran.nextInt(high - low);
        int g = low + ran.nextInt(high - low);
        int b = low + ran.nextInt(high - low);

        return new Color(r, g, b);
    }

    private int getCode(int yzmFlag) {
        /*
         * 1 : 0-9 2 : a-z 3 : A-Z
         */
        Random r = new Random();
        int code = 0;

        int flag = r.nextInt(yzmFlag);
        if (flag == 0) {
            code = r.nextInt(10);
        } else if (flag == 1) {
            code = getCodeByScope('a', 'z');
        } else {
            code = getCodeByScope('A', 'Z');
        }
        return code;
    }

    private int getCodeByScope(int low, int high) {
        Random ran = new Random();
        return low + ran.nextInt(high - low);

    }
}
