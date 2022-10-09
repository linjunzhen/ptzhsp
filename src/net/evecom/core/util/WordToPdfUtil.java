/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.File;

import com.jacob.com.ComThread;
import com.jacob.com.Variant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * 描述
 * 
 * @author Rider Chen
 * @created 2017年7月12日 下午5:10:34
 */
public class WordToPdfUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WordToPdfUtil.class);
    /**
     * 不保存待定的更改。
     */
    static final int WDDONOTSAVECHANGES = 0;
    /**
     * word转PDF 格式
     */
    static final int WDFORMATPDF = 17;

    /**
     * PPT转PDF 格式
     */
    private static final Integer PPT_TO_PDF_OPERAND = 32;

    /**
     * EXCEL转PDF 格式
     */
    private static final Integer EXCEL_TO_PDF_OPERAND = 0;

    /**
     * 描述:WORD转PDF
     *
     * @author Madison You
     * @created 2020/11/10 16:57:00
     * @param
     * @return
     */
    public static boolean word2pdf(String source, String target) {
        log.info("Word转PDF开始启动...");
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            // log.info("打开文档：" + source);
            Dispatch doc = Dispatch.call(docs, "Open", source, false, true).toDispatch();
            // log.info("转换文档到PDF：" + target);
            File tofile = new File(target);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "SaveAs", target, WDFORMATPDF);
            Dispatch.call(doc, "Close", false);
            long end = System.currentTimeMillis();
            log.info("转换完成，用时：" + (end - start) + "ms");
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        } finally {
            if (app != null) {
                app.invoke("Quit", WDDONOTSAVECHANGES);
            }
        }
    }

    /**
     * 描述:PPT转PDF
     *
     * @author Madison You
     * @created 2020/11/10 16:57:00
     * @param
     * @return
     */
    public void ppt2pdf(String srcFilePath, String pdfFilePath) throws Exception {
        ActiveXComponent app = null;
        Dispatch ppt = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();

            /*
             * call param 4: ReadOnly param 5: Untitled指定文件是否有标题 param 6:
             * WithWindow指定文件是否可见
             */
            ppt = Dispatch.call(ppts, "Open", srcFilePath, true, true, false).toDispatch();
            Dispatch.call(ppt, "SaveAs", pdfFilePath, PPT_TO_PDF_OPERAND); // ppSaveAsPDF为特定值32

        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            if (ppt != null) {
                Dispatch.call(ppt, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();

        }
    }

    /**
     * 描述:EXCEL转PDF
     *
     * @author Madison You
     * @created 2020/11/10 16:26:00
     * @param 
     * @return 
     */
    public static boolean excel2Pdf(String inFilePath, String outFilePath) throws Exception {
        ActiveXComponent ax = null;
        Dispatch excel = null;
        try {
            ComThread.InitSTA();
            ax = new ActiveXComponent("Excel.Application");
            ax.setProperty("Visible", new Variant(false));
            ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏

            Dispatch excels = ax.getProperty("Workbooks").toDispatch();

            Object[] obj = new Object[] { inFilePath, new Variant(false), new Variant(false) };
            excel = Dispatch.invoke(excels, "Open", Dispatch.Method, obj, new int[9]).toDispatch();
            // 将excel表格 设置成A3的大小
            Dispatch sheets = Dispatch.call(excel, "Worksheets").toDispatch();
            Dispatch sheet = Dispatch.call(sheets, "Item", new Integer(1)).toDispatch();
            Dispatch pageSetup = Dispatch.call(sheet, "PageSetup").toDispatch();
            Dispatch.put(pageSetup, "PaperSize", new Integer(8));// A3是8，A4是9，A5是11等等

            // 转换格式
            Object[] obj2 = new Object[] { new Variant(EXCEL_TO_PDF_OPERAND), // PDF格式=0
                    outFilePath, new Variant(0) // 0=标准 (生成的PDF图片不会变模糊) ; 1=最小文件
            };
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, obj2, new int[1]);

        } catch (Exception es) {
            log.error(es);
            return false;
            // throw es;
        } finally {
            if (excel != null) {
                Dispatch.call(excel, "Close", new Variant(false));
            }
            if (ax != null) {
                ax.invoke("Quit", new Variant[] {});
                ax = null;
            }
            ComThread.Release();
        }
        return true;

    }
}
