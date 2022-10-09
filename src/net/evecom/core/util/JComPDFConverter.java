/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.JComException;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
import jp.ne.so_net.ga2.no_ji.jcom.excel8.ExcelApplication;
/**
 * 
 * 描述 word excel 格式转换类
 * @author Rider Chen
 * @created 2016年6月29日 上午9:44:53
 */
public class JComPDFConverter  {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(JComPDFConverter.class);
    /**
     * word转PDF常量
     */
    private static final int WDFORMATPDF = 17;
    /**
     * word转HTML常量
     */
    private static final int WDFORMATHTML = 8;

    public static void word2PDF(String inputFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "Word.Application");// 启动word
            app.put("Visible", false); // 设置word不可见
            IDispatch docs = (IDispatch) app.get("Documents"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { pdfFile, WDFORMATPDF });// 转换文档为pdf格式
            doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
    public static void word2Html(String inputFile, String htmlFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "Word.Application");// 启动word
            app.put("Visible", false); // 设置word不可见
            IDispatch docs = (IDispatch) app.get("Documents"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { htmlFile, WDFORMATHTML });// 转换文档为html格式
            doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
    public static void ppt2PDF(String inputFile, String pdfFile) {

    }

    public static void xls2PDF(String inputFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "Excel.Application");// 启动Excel
            app.put("Visible", false); // 设置word不可见
            IDispatch excels = (IDispatch) app.get("Workbooks"); // 获得Excel中所有打开的文档
            IDispatch excel = (IDispatch) excels.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            excel.method("SaveAs", new Object[] { pdfFile, WDFORMATPDF });// 转换文档为pdf格式
            excel.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
    

    /**
     * JCom调用MS Office转换excel为HTML
     * 
     * @param inputFile
     *            源文件绝对路径
     * @param htmlFile
     *            目标文件绝对路径
     */
    public  static  void excel2HTML(String inputFile, String htmlFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            ExcelApplication ex = new ExcelApplication(rm);
            ex.put("Visible", false);
            IDispatch excs = (IDispatch) ex.get("Workbooks");
            IDispatch doc = (IDispatch) excs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { htmlFile, 44 });
            doc.method("Close", new Object[] { false });
            ex.method("Quit", null);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Excel为PDF
     * 
     * @param inputFile
     *            源文件绝对路径
     * @param pdfFile
     *            目标文件绝对路径
     */
    public  static  void excel2PDF(String inputFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            ExcelApplication ex = new ExcelApplication(rm);
            ex.put("Visible", false);
            IDispatch excs = (IDispatch) ex.get("Workbooks");
            IDispatch doc = (IDispatch) excs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("ExportAsFixedFormat", new Object[] { 0, pdfFile });
            doc.method("Close", new Object[] { false });
            ex.method("Quit", null);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
    public static void main(String[]args) throws Exception{
        String inputFile = "D:\\1.doc";
        String pdfFile = "D:\\htmlFile\\test.html";
        JComPDFConverter.word2Html(inputFile, pdfFile);

//        String xlsFile = "D:\\111.xlsx";
//        String pdfFile1 = "D:\\test1.pdf";
//
//        String htmlFile1 = "D:/test12.html";
//
//        JComPDFConverter.excel2HTML(xlsFile, htmlFile1);
        //JComPDFConverter.excel2PDF(xlsFile, pdfFile1);
        
    }
}
