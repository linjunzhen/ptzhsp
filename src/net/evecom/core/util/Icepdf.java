/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;
import java.awt.image.BufferedImage;  
import java.awt.image.RenderedImage;  
import java.io.File;  
import java.io.IOException;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;  
  
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icepdf.core.pobjects.Document;  
import org.icepdf.core.util.GraphicsRenderingHints;  
/**
 * 描述：pdf转图片
 * @author Water Guo
 * @created 2017-4-26 上午9:15:38
 */
public class Icepdf {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(Icepdf.class);
    /**
     * 
     * 描述：pdf转图片
     * @author Water Guo
     * @created 2017-4-26 上午10:01:13
     * @param pdfPath
     * @param path
     */
    public static List<String> pdf2Pic(String pdfPath) {
        String attachFileFolderPath = "";
        List<String> list = new ArrayList<String>();
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uuId = UUIDGenerator.getUUID();
        String uploadFullPath = "attachFiles/pdfToImg/" + currentDate + "/" + uuId;
        // 建立全路径目录和临时目录
        String path=attachFileFolderPath + uploadFullPath;
        File pdfFullPathFolder = new File(attachFileFolderPath + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        Document document = new Document();
        document.setFile(pdfPath);
        float scale = 2.5f;// 缩放比例
        float rotation = 0f;// 旋转角度
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                    org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;
            try {
                String imgName = "/" + uuId + "_" + i + ".png";
                File file = new File(path + imgName);
                ImageIO.write(rendImage, "png", file);
                list.add(uploadFullPath+imgName);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            image.flush();
        }
        document.dispose();
        return list;
    }
}