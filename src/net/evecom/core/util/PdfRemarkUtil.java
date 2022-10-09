/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年1月8日 下午5:42:44
 */
public class PdfRemarkUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(PdfRemarkUtil.class);
    /**
     * 给pdf添加水印图片
     * 
     * @param iconPath
     *            水印图片路径
     * @param srcPdfPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public static void markPdfByIcon(String iconPath, String srcPdfPath, String targerPath) {
        PdfReader reader = null;
        PdfStamper stamp = null;
        try {
            reader = new PdfReader(srcPdfPath);
            stamp = new PdfStamper(reader, new FileOutputStream(targerPath));
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte content;
            PdfGState gs = new PdfGState(); 

            Image image = Image.getInstance(iconPath);
            gs.setFillOpacity(1f);

            float positionWidth = 5;
            float positionHeight = 100;
            for(int i = 1; i < total; i++) {  
                content = stamp.getOverContent(i);
                Rectangle mediabox = reader.getPageSize(i);
                if(mediabox.getWidth()>530){
                    positionWidth = (mediabox.getWidth()-530)/2;
                }
                if(mediabox.getHeight()>530){
                    positionHeight = (mediabox.getHeight()-530)/2;
                }
                image.setAbsolutePosition(positionWidth, positionHeight);
                
                content.setGState(gs);
                content.addImage(image);  
            } 
           
            stamp.close();  
            reader.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally{
            try {
                if (null != stamp)
                    stamp.close();
                if (null != reader)
                    reader.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
