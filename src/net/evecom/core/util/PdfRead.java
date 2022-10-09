/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.awt.geom.RectangularShape;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

/**
 * 描述 pdf文本定位
 * 
 * @author Reuben Bao
 * @created 2019年4月1日 上午9:32:20
 */
public class PdfRead {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(PdfRead.class);

    /**
     * 描述 
     * 
     * @author Reuben Bao
     * @created 2019年4月1日 下午3:21:56
     */
    public class TestRenderListener implements RenderListener {
        /**
         * 描述 用来存放文字的矩形
         */
        List<Rectangle2D.Float> rectText = new ArrayList<Rectangle2D.Float>();
        /**
         * 描述 用来存放文字
         */
        List<String> textList = new ArrayList<String>();
        /**
         * 描述 用来存放文字的y坐标
         */
        List<Float> listY = new ArrayList<Float>();
        /**
         * 描述 用来存放每一行文字的坐标位置
         */
        List<Map<String, Rectangle2D.Float>> rows_text_rect = new ArrayList<Map<String, Rectangle2D.Float>>();

        /**
         * 描述 获取文本数据
         * 
         * @author Reuben Bao
         * @created 2019年4月1日 下午3:16:20
         * @param renderInfo
         */
        @Override
        public void renderText(TextRenderInfo renderInfo) {
            String text = renderInfo.getText();
            if (StringUtils.isNotEmpty(text) && text.trim().length() > 0) {
                RectangularShape rectBase = renderInfo.getBaseline().getBoundingRectange();
                // 获取文字下面的矩形
                Rectangle2D.Float rectAscen = renderInfo.getAscentLine().getBoundingRectange();
                // 计算出文字的边框矩形
                float leftX = (float) rectBase.getMinX();
                float leftY = (float) rectBase.getMinY() - 1;
                float rightX = (float) rectAscen.getMaxX();
                float rightY = (float) rectAscen.getMaxY() + 1;
                Rectangle2D.Float rect = new Rectangle2D.Float(leftX, leftY, rightX - leftX, rightY - leftY);
                if (listY.contains(rect.y)) {
                    int index = listY.indexOf(rect.y);
                    float tempx = rect.x > rectText.get(index).x ? rectText.get(index).x : rect.x;
                    rectText.set(index,
                            new Rectangle2D.Float(tempx, rect.y, rect.width + rectText.get(index).width, rect.height));
                } else {
                    rectText.add(rect);
                    listY.add(rect.y);
                }
                Map<String, Rectangle2D.Float> map = new HashMap<String, Rectangle2D.Float>();
                map.put(text, rect);
                rows_text_rect.add(map);
            }
        }

        /**
         * 描述 
         * 
         * @author Reuben Bao
         * @created 2019年4月1日 下午3:17:23
         */
        @Override
        public void endTextBlock() {}

        /**
         * 描述 
         * 
         * @author Reuben Bao
         * @created 2019年4月1日 下午3:17:26
         * @param arg0
         */
        @Override
        public void renderImage(ImageRenderInfo arg0) {}

        /**
         * 描述 
         * 
         * @author Reuben Bao
         * @created 2019年4月1日 下午3:17:29
         */
        @Override
        public void beginTextBlock() {}
    }

    /**
     * 描述 根据itext接口将pdf进行文本分段list，从分段list中查询文本为text的坐标
     * 
     * @author Reuben Bao
     * @created 2019年4月1日 下午1:39:32
     * @param pdfFilePath pdf 文件路径
     * @param text 匹配的字符串
     * @return
     */
    public Map<String, Object> readPdfByTextForXandY(String pdfFilePath, String text) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            PdfReader reader = new PdfReader(pdfFilePath);
            // 创建一个PDF解析对象
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                // 创建一个ImageRenderListener对象，该对象实现了RenderListener接口，作为处理PDF的主要类
                TestRenderListener listener = new TestRenderListener();
                // 解析PDF，并处理里面的文字
                parser.processContent(i, listener);
                // 获取文字的矩形边框
                for (Map<String, Rectangle2D.Float> textMap : listener.rows_text_rect) {
                    for (Map.Entry<String, Rectangle2D.Float> entry : textMap.entrySet()) {
                        // System.out.println(entry.getKey());
                        if (StringUtils.isNotEmpty(entry.getKey().trim()) && text.equals(entry.getKey().trim())) {
                            returnMap.put("TEXT", entry.getKey());
                            String x = String.valueOf(entry.getValue().getX());
                            x = x != null && x.indexOf(".") > -1 ? x.substring(0, x.indexOf(".")) : x;
                            String y = String.valueOf(entry.getValue().getY());
                            y = y != null && y.indexOf(".") > -1 ? y.substring(0, y.indexOf(".")) : y;
                            returnMap.put("locationX", x);
                            returnMap.put("locationY", y);
                            returnMap.put("pageNum", i);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取pdf文件指定文本坐标错误！");
        }
        return returnMap;
    }

}