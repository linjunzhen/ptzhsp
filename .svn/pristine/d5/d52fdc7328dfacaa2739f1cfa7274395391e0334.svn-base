/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.SwingUtilities;

import net.evecom.core.poi.WordReplaceParamUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

/**
 * 
 * 描述 pdf转图片
 * 
 * @author Rider Chen
 * @created 2017年2月23日 下午2:55:22
 */
public class PdfToImgUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(PdfToImgUtil.class);

    /**
     * 
     * 描述 pdf转化成图片
     * @author Rider Chen
     * @created 2017年2月23日 下午3:20:09
     * @param pdfPath
     * @return
     */
    public static List<String> pdfToPng(String pdfPath) {
        FileOutputStream out = null;
        File file = null;
        RandomAccessFile raf = null;
        List<String> list = new ArrayList<String>();
        try {
            file = new File(pdfPath);
            // load a pdf from a byte buffer
            raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

            PDFFile pdffile = new PDFFile(buf);

           // System.out.println("页数： " + pdffile.getNumPages());

            String attachFileFolderPath = "";
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uuId = UUIDGenerator.getUUID();
            String uploadFullPath = "attachFiles/pdfToImg/" + currentDate + "/" + uuId;
            // 建立全路径目录和临时目录
            File pdfFullPathFolder = new File(attachFileFolderPath + uploadFullPath);
            if (!pdfFullPathFolder.exists()) {
                pdfFullPathFolder.mkdirs();
            }

            //System.out.println("PdfFilePath is  :" + attachFileFolderPath + uploadFullPath);

            for (int i = 1; i <= pdffile.getNumPages(); i++) {
                // draw the first page to an image
                PDFPage page = pdffile.getPage(i);

                // get the width and height for the doc at the default zoom
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());

                // generate the image
                Image img = page.getImage(rect.width, rect.height, // width &
                                                                   // height
                        rect, // clip rect
                        null, // null for the ImageObserver
                        true, // fill background with white
                        true // block until drawing is done
                        );

                BufferedImage tag = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height, null);

                out = new FileOutputStream(attachFileFolderPath + uploadFullPath 
                        + "/" + uuId + "_" + i + ".png"); // 输出到文件流
                list.add(uploadFullPath + "/" + uuId + "_" + i   + ".png");
                

                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag); // JPEG编码
            }
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (raf != null) {
                    raf.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
                log.error(e2.getMessage());
            }
        }

    }
}
