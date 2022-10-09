/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.IdCardService;
import sun.misc.BASE64Decoder;

/**
 * 身份证信息
 * 
 * @author Charlie Wei
 */
@Controller
@RequestMapping("/idCardController")
public class IdCardController extends BaseController {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(IdCardController.class);
    /**
     * IdCardService
     */
    @Resource
    private IdCardService service;

    /**
     * 删除
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        // String selectColNames = request.getParameter("selectColNames");
        // service.remove("T_APP_MESSAGE", "MESSAGE_ID",
        // selectColNames.split(","));

        json.setMsg("删除成功");
        return json;
    }

    /**
     * 保存/修改
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> savedRecordMap = null;
        String cardNo = request.getParameter("CARDNO");

        if (StringUtils.isNotEmpty(cardNo)) {
            Map<String, Object> recordMap = service.getByJdbc("IDCARD", new String[] { "CARDNO" },
                    new Object[] { cardNo });
            if (recordMap == null) {// 如果身份证信息未曾保存
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                String savedEntityId = service.saveOrUpdate(variables, "IDCARD", null);

                // 图片流
                savedRecordMap = service.getByJdbc("IDCARD", new String[] { "ENTITYID" },
                        new Object[] { savedEntityId });
                String photo = String.valueOf(savedRecordMap.get("PHOTO"));

                // 照片保存路径
                Properties properties = FileUtil.readProperties("project.properties");
                String projectPath = properties.getProperty("AttachFilePath");
                String svePath = projectPath + "attachFiles\\IdCard\\" + cardNo + ".png";

                boolean pngResult = str2png(photo, svePath);

                if (pngResult) {
                    boolean transferResult = transferAlpha(svePath);

                    if (transferResult) {

                    }
                }
            }
        }

        return ajaxJson;
    }

    /**
     * base64字符串转png
     */
    private boolean str2png(String imgStr, String path) {
        boolean r = false;

        if (StringUtils.isNotEmpty(imgStr)) {
            OutputStream out = null;
            BASE64Decoder decoder = new BASE64Decoder();

            try {
                // 解密
                byte[] b = decoder.decodeBuffer(imgStr);
                // 处理数据
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                out = new FileOutputStream(path);
                out.write(b);
                out.flush();
                r = true;
            } catch (IOException e) {
//              e.printStackTrace();
              log.error("Catch an exception!", e);
                r = false;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
//                        e.printStackTrace();
                        log.error("Catch an exception!", e);
                    }
                }
            }
        }

        return r;
    }

    /**
     * 设置图片透明度
     */
    private boolean transferAlpha(String path) {
        boolean r = false;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        File file = new File(path);
        InputStream is;

        try {
            is = new FileInputStream(file);
            // 如果是MultipartFile类型，那么自身也有转换成流的方法：is = file.getInputStream();
            BufferedImage bi = ImageIO.read(is);
            Image image = (Image) bi;
            ImageIcon imageIcon = new ImageIcon(image);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);

                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }

                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }

            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            // 直接输出文件
            ImageIO.write(bufferedImage, "png", new File(path));
            // 转换成byte数组
            // ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

            r = true;
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("Catch an exception!", e);
            r = false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
        }

        return r;
    }

}
