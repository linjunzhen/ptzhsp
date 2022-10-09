/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.poi;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.*;
import net.evecom.core.web.servlet.DownLoadServlet;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;
import org.w3c.dom.Node;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 描述 word替换
 * 
 * @author Rider Chen
 * @created 2016年11月30日 上午11:20:25
 */
public class WordReplaceUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WordReplaceUtil.class);

    /**
     * 根据指定的参数值、模板，生成 word 文档
     * 
     * @param param
     *            需要替换的变量
     * @param template
     *            模板
     */
    public static CustomXWPFDocument generateWord(Map<String, Object> param, InputStream template) {
        CustomXWPFDocument doc = null;
        try {
            doc = new CustomXWPFDocument(template);
            if (param != null && param.size() > 0) {

                replaceWordParam(param, doc);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("", e);
        }
        return doc;
    }

    /**
     * 根据指定的参数值、模板，生成 word 文档
     * 
     * @param param
     *            需要替换的变量
     * @param template
     *            模板
     */
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {

                replaceWordParam(param, doc);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("", e);
        }
        return doc;
    }

    @SuppressWarnings("unchecked")
    private static void replaceWordParam(Map<String, Object> param, CustomXWPFDocument doc) {
        // 处理段落
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        List<Object> fahqtzjwfdbList = (LinkedList<Object>) param.get("fahqtzjwfdbList");
        for (XWPFParagraph xwpfParagraph : paragraphList) {
            boolean isok = addFahqtzjwfdbList(xwpfParagraph, doc, fahqtzjwfdbList, "fahqtzjwfdbList");
            if (isok) {
                break;
            }
        }
        paragraphList = doc.getParagraphs();
        processParagraphs(paragraphList, param, doc);

        // 股东
        List<Map<String, Object>> holderList = (List<Map<String, Object>>) param.get("holderList");
        // 投资者
        List<Map<String, Object>> investorList = (List<Map<String, Object>>) param.get("investorList");
        // 董事
        List<Map<String, Object>> directorList = (List<Map<String, Object>>) param.get("directorList");
        // 监理
        List<Map<String, Object>> supervisorList = (List<Map<String, Object>>) param.get("supervisorList");
        // 经理
        List<Map<String, Object>> managerList = (List<Map<String, Object>>) param.get("managerList");
        // 董事监理经理集合（内资企业备案使用）
        List<Map<String, Object>> dsjsjlList = (List<Map<String, Object>>) param.get("dsjsjlList");

        // 外商投资企业最终实际控制人信息
        List<Map<String, Object>> controllerList = (List<Map<String, Object>>) param.get("controllerList");

        // 执行事务合伙人
        List<Map<String, Object>> partnerList = (List<Map<String, Object>>) param.get("partnerList");

        List<Map<String, Object>> bdcList = (List<Map<String, Object>>) param.get("bdcList");
        /*医保个人缴费记录明细*/
        List<Map<String, Object>> grjfjlList = (List<Map<String, Object>>) param.get("grjfjlList");
        
        /*变更选项集合*/
        List<Map<String, Object>> bgXzList = (List<Map<String, Object>>) param.get("bgXzList");
        
        //设置股东（发起人）、外国投资者出资情况
        List<Map<String, Object>> holderCzList = (List<Map<String, Object>>) param.get("holderCzList");
        
        //商事变更股东身份证正反面集合
        List<Map<String, Object>> bgHolderList = (List<Map<String, Object>>) param.get("bgHolderList");

        // 处理表格
        List<XWPFTable> tableList = doc.getTables();
        for (XWPFTable table : tableList) {
            List<XWPFTableRow> rows = table.getRows();
            boolean isok = addTableRowAndCell(param, table, rows, doc, investorList, "外商投资企业投资者基本信息");
            if (isok) {
                break;
            }
            isok = addTable(param, table, rows, doc, partnerList, "PARTNER_NAME");
            if (isok) {
                break;
            }
        }
        // 处理表格
        Iterator<XWPFTable> it = doc.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();
            List<XWPFTableRow> rows = table.getRows();
            // table.addRow(rows.get(0));
            XWPFTableRow oldRow = null;

            // 当表格为股东（发起人）出资情况时添加 表格
            addTableRow(param, table, rows, oldRow, holderList, "SHAREHOLDER_NAME");
            addTableRow(param, table, rows, oldRow, investorList, "INVESTOR_NAME");
            // 当表格为董事时添加 表格
            addTableRow3(param, table, rows, oldRow, directorList, "DIRECTOR_NAME", doc);
            // 当表格为监理时添加 表格
            addTableRow3(param, table, rows, oldRow, supervisorList, "SUPERVISOR_NAME",doc);
            // 当表格为经理时添加 表格
            addTableRow3(param, table, rows, oldRow, managerList, "MANAGER_NAME",doc);
            // 当表格为董事监理经理时添加 表格
            addTableRow3(param, table, rows, oldRow, dsjsjlList, "DSJSJL_NAME",doc);
            // 当表格为外商投资企业最终实际控制人信息时添加 表格
            addTableRow(param, table, rows, oldRow, controllerList, "CONTROLLER_NAME");

            addTableRow(param, table, rows, oldRow, bdcList, "bdcxh");

            addTableRow(param, table, rows, oldRow, grjfjlList, "dwmc");
            
            //当表格为变更集合时，添加表格
            addTableRow3(param, table, rows, oldRow, bgXzList, "BGSX",doc);
          
            //当表格为股东（发起人）、外国投资者出资情况集合时，添加表格
            addTableRow3(param, table, rows, oldRow, holderCzList, "SHAREHOLDER_BG_NAME",doc);
            
            //当表格为商事变更股东身份证正反面集合时，添加表格
            addTableRow3(param, table, rows, oldRow, bgHolderList, "HOLDERPHOTOFRONT",doc);

            

            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                    // 当表格为股东（发起人）出资情况时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, holderList, "SHAREHOLDER_NAME");

                    setTableRow(param, doc, cells, paragraphListTable, investorList, "INVESTOR_NAME");
                    // 当表格为董事时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, directorList, "DIRECTOR_NAME");
                    // 当表格为监理时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, supervisorList, "SUPERVISOR_NAME");
                    // 当表格为经理时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, managerList, "MANAGER_NAME");
                    // 当表格为董事监理经理时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, dsjsjlList, "DSJSJL_NAME");
                    // 当表格为外商投资企业最终实际控制人信息时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, controllerList, "CONTROLLER_NAME");

                    setTableRow(param, doc, cells, paragraphListTable, bdcList, "bdcxh");

                    setTableRow(param, doc, cells, paragraphListTable, grjfjlList, "dwmc");
                    
                    //当表格为变更集合时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, bgXzList, "BGSX");
                    
                    //当表格为股东（发起人）、外国投资者出资情况集合时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, holderCzList, "SHAREHOLDER_BG_NAME");
                    
                     //当表格为商事变更股东身份证正反面集合时替换表格数据
                    setTableRow(param, doc, cells, paragraphListTable, bgHolderList, "HOLDERPHOTOFRONT");
                    
                    
                    // 替换其他数据
                    processParagraphs(paragraphListTable, param, doc);
                }
            }
        }

        // 处理页眉
        List<XWPFHeader> headerList = doc.getHeaderList();
        for (XWPFHeader xwpfHeader : headerList) {
            List<XWPFParagraph> HeaderParagraphList = xwpfHeader.getParagraphs();
            processParagraphs(HeaderParagraphList, param, doc);
        }
        // 新增文档不显示表格与文字
        String gsdjsqsfb = StringUtil.getString(param.get("gsdjsqsfb"));
        if (StringUtils.isNotEmpty(gsdjsqsfb) && gsdjsqsfb.equals("delete")) {// 法定代表人不做变更时公司登记（备案）申请书中不出现附表1
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("fddbrxx", "");
            dataMap.put("fddbrfb1", "");
            dataMap.put("fddbrfb2", "");
            deleteBookMarkText(doc, dataMap, true, 2);// 通过书签名删除内容
        }
        
        // 新增文档不显示表格与文字(企业内资变更审批)
        String bgfrxx = StringUtil.getString(param.get("bgfrxx"));
        if (StringUtils.isNotEmpty(bgfrxx) && bgfrxx.equals("delete")) {// 法定代表人不做变更时公司登记（备案）申请书中不出现附表1
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("fddbrxx", "");
            dataMap.put("fddbrfb1", "");
            deleteBookMarkText(doc, dataMap, true, 3);// 通过书签名删除内容
        }
        String dsjsjlxx = StringUtil.getString(param.get("dsjsjlxx"));
        if (StringUtils.isNotEmpty(dsjsjlxx) && dsjsjlxx.equals("delete")) {// 董事/监事/经理不做备案时公司登记（备案）申请书中不出现附表2
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("dsjsjlxx", "");
            dataMap.put("dsjsjlfb2", "");
            if(StringUtils.isNotEmpty(bgfrxx) && bgfrxx.equals("delete")){
                deleteBookMarkText(doc, dataMap, true, 4);// 通过书签名删除内容 
            }else{
                deleteBookMarkText(doc, dataMap, true, 4);// 通过书签名删除内容
            }
            
        }
    }
    /**
     * 
     * 描述 根据书签名删除内容
     * @author Rider Chen
     * @created 2021年5月12日 下午5:25:21
     * @param doc
     *      word文档
     * @param dataMap
     *      书签集合
     * @param isDelTable
     *      是否删除表格
     * @param delTableNum
     *      需要删除表格的位置
     */
    public static void deleteBookMarkText(CustomXWPFDocument doc, Map<String, String> dataMap, boolean isDelTable,
            int delTableNum) {
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        List<Integer> pList = new ArrayList<>();
        for (int i = 0; i < paragraphList.size(); i++) {
            XWPFParagraph paragraph = paragraphList.get(i);
            CTP ctp = paragraph.getCTP();
            for (int dwI = 0; dwI < ctp.sizeOfBookmarkStartArray(); dwI++) {
                CTBookmark bookmark = ctp.getBookmarkStartArray(dwI);
                if (dataMap.containsKey(bookmark.getName())) {//判断书签名称
                    pList.add(i);
                    List<XWPFRun> XWPFRun = paragraph.getRuns();
                    for (XWPFRun run : XWPFRun) {
                        run.setText(dataMap.get(bookmark.getName()), 0);//替换书签内容
                        run.removeBreak();
                    }

                }
            }

        }
        // 遍历list删除
        for (int i = pList.size() - 1; i >= 0; i--) {
            doc.removeBodyElement(pList.get(i) + 2);
        }

        if (isDelTable) {//删除模版中的表格
            // 处理表格
            List<XWPFTable> tableList = doc.getTables();
            for (int i = 0; i < tableList.size(); i++) {
                if (i == delTableNum) {
                    XWPFTable table = tableList.get(i);
                    List<XWPFTableRow> rows = table.getRows();
                    int rowLength = rows.size();
                    for (int j = 0; j < rowLength; j++) {
                        table.removeRow(0);
                    }
                }
            }
        }
    }
    /**
     * 
     * 描述 根据关键字更改行数据
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午4:15:02
     * @param param
     * @param doc
     * @param row
     * @param cells
     * @param paragraphListTable
     * @param key
     */
    public static void setTableRow(Map<String, Object> param, CustomXWPFDocument doc, List<XWPFTableCell> cells,
            List<XWPFParagraph> paragraphListTable, List<Map<String, Object>> dataList, String key) {

        if (paragraphListTable != null && paragraphListTable.size() > 0) {
            for (XWPFParagraph paragraph : paragraphListTable) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (null != dataList && text != null && text.indexOf(key) != -1) {
                        for (int i = 0; i < dataList.size(); i++) {
                            for (XWPFTableCell cell1 : cells) {
                                List<XWPFParagraph> paragraphListTable1 = cell1.getParagraphs();
                                // 替换信息
                                processParagraphs(paragraphListTable1, dataList.get(i), doc);
                                // 替换信息
                                processParagraphs(paragraphListTable1, param, doc);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 根据关键字添加表格
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午4:05:29
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param key
     */
    private static boolean addFahqtzjwfdbList(XWPFParagraph paragraph, CustomXWPFDocument doc, List<Object> list,
            String key) {
        boolean isok = false;

        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null && text.indexOf(key) != -1) {
                run.setText("", 0);
                isok = true;
                // 全体合伙人签名
                addFahqtzjwfdbText(list, doc);
            }
        }

        return isok;
    }
    /**
     * 描述 全体合伙人签名
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午1:52:35
     * @param doc
     */
    private static void addFahqtzjwfdbText(List<Object> list, CustomXWPFDocument doc) {
        if(null != list){
            for (int i = 0; i < list.size(); i++) {
                XWPFParagraph p = doc.createParagraph();
                XWPFRun run = p.createRun();// 创建段落文本
                String s = (String) list.get(i);
                if (s.equals("法人或其他组织委派代表的委托书")) {
                    p.setPageBreak(true);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    run.setText("", 0);
                    run.setFontSize(16);
                    run.setBold(true);
                } else {
                    p.setAlignment(ParagraphAlignment.LEFT);
                    run.setFontSize(12);
                    run.setBold(false);
                }
                run.setText(s);
                run.addBreak();
            }
        }
    }

    /**
     * 
     * 描述 根据关键字添加表格
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午4:05:29
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param key
     */
    private static boolean addTable(Map<String, Object> param, XWPFTable table, List<XWPFTableRow> rows,
            CustomXWPFDocument doc, List<Map<String, Object>> dataList, String key) {
        boolean isok = false;
        Map<String, Object> map;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                if (paragraphListTable != null && paragraphListTable.size() > 0) {
                    for (XWPFParagraph paragraph : paragraphListTable) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.indexOf(key) != -1 && null != dataList) {
                                for (int i = 1; i < dataList.size(); i++) {
                                    map = dataList.get(i);
                                    doc.createTable();
                                    // 执行事务合伙人（含委派代表）信息
                                    setQthhrqmTableCallText(table, param, map);
                                    doc.setTable(2 + i, table);
                                    if((i+1)<dataList.size()){
                                        XWPFParagraph p = doc.createParagraph();
                                        p.setPageBreak(true);                                        
                                    }
                                    isok = true;
                                }
                                map = dataList.get(0);
                                // 执行事务合伙人（含委派代表）信息
                                setQthhrqmTableCallText(table, param, map);
                                // 全体合伙人签名
                                //addQthhrqmText(param, doc);
                            }
                        }
                    }
                }
            }
        }
        return isok;
    }

    /**
     * 
     * 描述 执行事务合伙人（含委派代表）信息
     * 
     * @author Rider Chen
     * @created 2018年6月15日 下午3:09:25
     * @param table
     * @param param
     * @param map
     */
    private static void setQthhrqmTableCallText(XWPFTable table, Map<String, Object> param, Map<String, Object> map) {
        // 姓 名
        setCellText(table, 0, 1, "" + map.get("PARTNER_NAME"));
        // 移动电话
        setCellText(table, 0, 3, "" + map.get("PARTNER_MOBILE"));
        // 固定电话
        //setCellText(table, 0, 3, "" + map.get("PARTNER_FIXEDLINE"));
        // 电子邮箱
        // setCellText(table, 1, 3, "" + map.get("PARTNER_EMAIL"));
        // 身份证件类型
        setCellText(table, 1, 1, "" + map.get("PARTNER_IDTYPE"));
        // 身份证件号码
        setCellText(table, 1, 3, "" + map.get("PARTNER_IDNO"));
    }

    /**
     * 描述 全体合伙人签名
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午1:52:35
     * @param doc
     */
    private static void addQthhrqmText(Map<String, Object> param, CustomXWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();// 创建段落文本

        r.setFontSize(12);
        r.setBold(false);
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.setText("全体合伙人签名：  ");
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.setText("                                                 " + param.get("FILL_DATE"));

    }

    /**
     * 
     * 描述 根据关键字判断增加行
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午4:05:29
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param key
     */
    private static boolean addTableRowAndCell(Map<String, Object> param, XWPFTable table, List<XWPFTableRow> rows,
            CustomXWPFDocument doc, List<Map<String, Object>> dataList, String key) {
        boolean isok = false;
        Map<String, Object> map;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                if (paragraphListTable != null && paragraphListTable.size() > 0) {
                    for (XWPFParagraph paragraph : paragraphListTable) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.indexOf(key) != -1) {
                                for (int i = 1; i < dataList.size(); i++) {
                                    map = dataList.get(i);
                                    doc.createTable();
                                    // 添加自贸区设立外资企业备案申报表和承诺书投资者信息
                                    setTableCallText(table, param, map);
                                    doc.setTable(1 + i, table);
                                    doc.createParagraph();
                                    isok = true;
                                }
                                map = dataList.get(0);
                                // 添加自贸区设立外资企业备案申报表和承诺书投资者信息
                                setTableCallText(table, param, map);
                                // 添加自贸区设立外资企业备案申报表和承诺书说明
                                addBasbbText(param, doc);
                            }
                        }
                    }
                }
            }
        }
        return isok;
    }

    /**
     * 描述 添加自贸区设立外资企业备案申报表和承诺书说明
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午1:52:35
     * @param doc
     */
    private static void addBasbbText(Map<String, Object> param, CustomXWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();// 创建段落文本
        r.setFontSize(12);
        r.setBold(true);
        r.setText("");
        r.setText("二、在线提交材料");
        r.addBreak();
        r = p.createRun();// 创建段落文本
        r.setFontSize(12);
        r.setBold(false);
        r.setText("（一）外商投资企业名称预先核准材料或外商投资企业营业执照");
        r.addBreak();
        r.setText("（二）外商投资企业的全体投资者（或外商投资股份有限公司的全体发起人）或其授权代表签署的《外商投资企业设立备案申报承诺书》");
        r.addBreak();
        r.setText("（三）全体投资者（或全体发起人）或外商投资企业指定代表或者共同委托代理人的证明，包括授权委托书及被委托人的身份证明");
        r.addBreak();
        r.setText("（四）外商投资企业投资者委托他人签署相关文件的证明，包括授权委托书及被委托人的身份证明（未委托他人签署相关文件的，无需提供）");
        r.addBreak();
        r.setText("（五）投资者的主体资格证明或自然人身份证明");
        r.addBreak();
        r.setText("（六）法定代表人的自然人身份证明");
        r.addBreak();
        r.setText("前述文件原件为外文的，应同时上传提交中文翻译件，外商投资企业或其投资者应确保中文翻译件内容与外文原件内容保持一致。");
        r.addBreak();
        r = p.createRun();// 创建段落文本
        r.setFontSize(12);
        r.setBold(true);
        r.setText("");
        r.setText("三、承诺书");
        r.addBreak();
        r = p.createRun();// 创建段落文本
        r.setFontSize(12);
        r.setBold(false);
        r.setText("外商投资企业设立备案申报承诺书");
        r.addBreak();
        r.setText("根据中华人民共和国有关法律法规规定，现提交" + param.get("COMPANY_NAME") + "（下称企业）设立备案信息并承诺：");
        r.addBreak();
        r.setText("一、已知晓备案申报的各项规定内容。");
        r.addBreak();
        r.setText("二、所填报的备案信息完整、真实、准确。");
        r.addBreak();
        r.setText("三、所提供的备案书面材料完整、合法、有效。");
        r.addBreak();
        r.setText("四、企业所从事经营活动不涉及国家规定实施准入特别管理措施范围。");
        r.addBreak();
        r.setText("五、申报内容是各方投资者或企业最高权力机构真实意思的表示。");
        r.addBreak();
        r.setText("六、遵守中华人民共和国法律、行政法规、规章规定，所从事的投资经营活动不损害中华人民共和国主权、国家安全和社会公共利益。");
        r.addBreak();
        r.setText("七、不伪造、变造、出租、出借、转让《外商投资企业设立备案回执》。");
        r.addBreak();
        r.setText("以上如有违反，将承担相应法律责任。");
        r.addBreak();
        r.setText("承诺人 名称：" + param.get("tzzxxNames"));
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.setText("承诺人/授权代表签字、盖章：  ");
        r.addBreak();
        r.addBreak();
        r.addBreak();
        r.setText("     ");
        r.addBreak();
        r.setText("                                             " + param.get("FILL_DATE"));
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午1:51:42
     * @param table
     * @param map
     */
    private static void setTableCallText(XWPFTable table, Map<String, Object> param, Map<String, Object> map) {
        // 姓名/名称
        setCellText(table, 1, 1, "" + map.get("INVESTOR_NAME"));
        // 姓名/名称（英文）
        setCellText(table, 1, 2,
                map.get("INVESTOR_CONTROL_NAME_ENG") == null ? "" : map.get("INVESTOR_CONTROL_NAME_ENG").toString());
        // 国籍（或地区）或地址/注册地或注册地址
        setCellText(table, 2, 1, "" + map.get("INVESTOR_COUNTRY"));
        // 证照号码
        setCellText(table, 3, 1, "证件类型：" + map.get("INVESTOR_IDTYPE") + "  号码：" + map.get("INVESTOR_IDNO"));
        // 认缴出资额
        setCellText(table, 4, 1, map.get("CONTRIBUTION").toString() + "万" + param.get("CURRENCY"));
        // 权益比例
        setCellText(table, 4, 2, "权益比例：" + map.get("PROPORTION"));
        // 出资方式
        StringBuffer sb = new StringBuffer();
        WordReplaceParamUtil.getStringCzfs(map, param.get("CURRENCY").toString(), sb);
        setCellText(table, 6, 1, sb.toString());
        // 出资期限
        setCellText(table, 7, 1, "" + param.get("PAYMENT_PERIOD"));
        // 资金来源地
        setCellText(table, 8, 1, "" + map.get("FUNDS_SOURCE"));
        String ZFTZZSJKZR_JSON = map.get("ZFTZZSJKZR_JSON") == null ? "" : map.get("ZFTZZSJKZR_JSON").toString();
        String WFTZZSJKZR_JSON = map.get("WFTZZSJKZR_JSON") == null ? "" : map.get("WFTZZSJKZR_JSON").toString();
        List<Map<String, Object>> tzzsjkList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(ZFTZZSJKZR_JSON)) {
            tzzsjkList = JSON.parseObject(ZFTZZSJKZR_JSON, List.class);

        }
        if (StringUtils.isNotEmpty(WFTZZSJKZR_JSON)) {
            tzzsjkList = JSON.parseObject(WFTZZSJKZR_JSON, List.class);
        }

        List<Object> list = new LinkedList<Object>();
        for (Map<String, Object> map1 : tzzsjkList) {
            try {
                // 控制人类别类型
                new WordReplaceParamUtil().getDicName(map1, "controllerType", "INVESTOR_CONTROL_TYPE");
                // 实际控制方式
                new WordReplaceParamUtil().getDicName(map1, "controlMode", "INVESTOR_CONTROL_METHOD");
                list.add("姓名/名称（中文）：" + map1.get("INVESTOR_CONTROL_NAME") + "      姓名/名称（英文）："
                        + map1.get("INVESTOR_CONTROL_NAME_ENG"));
                list.add("国籍（或地区）/注册地：" + map1.get("INVESTOR_CONTROL_PLACE"));
                list.add("证照号码：" + map1.get("INVESTOR_CONTROL_IDNO"));
                list.add("控制人类别：" + map1.get("INVESTOR_CONTROL_TYPE"));
                list.add("实际控制方式：" + map1.get("INVESTOR_CONTROL_METHOD"));
                list.add("控制方式详细说明：" + map1.get("INVESTOR_CONTROL_METHOD_OTHER"));
                list.add("");
                list.add("");
            } catch (Exception e) {
                // TODO: handle exception
                log.error("", e);
            }
        }
        // 投资者最终实际控制人信息姓名/名称
        setCellText(table, 9, 1, list);
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午1:55:40
     * @param table
     * @param row
     * @param cell
     * @param text
     */
    private static void setCellText(XWPFTable table, int row, int cell, String text) {
        table.getRow(row).getCell(cell).removeParagraph(0);
        table.getRow(row).getCell(cell).addParagraph().createRun().setText(text, 0);
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午1:55:40
     * @param table
     * @param row
     * @param cell
     * @param text
     */
    private static void setCellText(XWPFTable table, int row, int cell, List<Object> list) {
        table.getRow(row).getCell(cell).removeParagraph(0);
        XWPFRun run = table.getRow(row).getCell(cell).addParagraph().createRun();
        run.setText("", 0);
        for (int i = 0; i < list.size(); i++) {
            String s = (String) list.get(i);
            run.setText(s);
            run.addBreak();
        }
    }

    /**
     * 
     * 描述 根据关键字判断增加行
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午4:05:29
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param key
     */
    public static void addTableRow(Map<String, Object> param, XWPFTable table, List<XWPFTableRow> rows,
            XWPFTableRow oldRow, List<Map<String, Object>> dataList, String key) {
        int rowNum = 1;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                if (paragraphListTable != null && paragraphListTable.size() > 0) {
                    for (XWPFParagraph paragraph : paragraphListTable) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.indexOf(key) != -1) {
                                oldRow = row;
                                if (null != dataList) {
                                    for (int i = 0; i < dataList.size(); i++) {
                                        if (i > 0) {
                                            rowNum++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int rNum = table.getRows().size();
        Map<String, Object> map;
        for (int i = 1; i < rowNum; i++) {
            map = dataList.get(i);
            table.createRow();
            List<XWPFTableCell> cells = oldRow.getTableCells();
            XWPFTableRow row = table.getRow(i + rNum - 1);

            row.setHeight(oldRow.getHeight());
            List<XWPFTableCell> newCells = row.getTableCells();
            for (int j = newCells.size(); j < cells.size(); j++) {
                row.createCell();
            }
            for (int j = 0; j < cells.size(); j++) {
                XWPFParagraph oldp = cells.get(j).getParagraphs().get(0);
                if (newCells.size() > j) {
                    XWPFParagraph p = newCells.get(j).addParagraph();
                    if (key.equals("DIRECTOR_NAME") || key.equals("SUPERVISOR_NAME") || key.equals("MANAGER_NAME")
                            || key.equals("CONTROLLER_NAME")) {
                        p.setAlignment(ParagraphAlignment.LEFT);
                    } else {
                        p.setAlignment(ParagraphAlignment.CENTER);
                    }
                    p.setStyle(oldp.getStyle());
                    XWPFRun r = p.createRun();// 创建段落文本
                    r.setFontSize(10);
                    r.setBold(false);
                    String text = map.get(cells.get(j).getText()) == null ? cells.get(j).getText()
                            : map.get(cells.get(j).getText()).toString();
                    boolean isSetText = true;
                    for (Entry<String, Object> entry : map.entrySet()) {
                        String key1 = entry.getKey();
                        if (cells.get(j).getText().indexOf(key1) != -1) {
                            Object value = entry.getValue();
                            if (value instanceof List) {// 列表段落
                                isSetText = false;
                                if (!key1.equals("investorList")) {
                                    List<Object> list = (LinkedList<Object>) value;
                                    for (int h = 0; h < list.size(); h++) {
                                        String s = (String) list.get(h);
                                        r.setText(s);
                                        r.addBreak();
                                    }
                                }
                            } else if (value instanceof CTSym) {
                                // 特殊符号
                                isSetText = false;
                                CTSym ctsym = (CTSym) value;
                                r.setText(text.replace(key, ""), 0);
                                try {
                                    CTRPr pRpr = getRunCTRPr(p, r);
                                    // 设置字体大小
                                    CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
                                    sz.setVal(new BigInteger("24"));
                                    List<CTSym> symList = new ArrayList<CTSym>();
                                    symList.add(ctsym);
                                    int size = symList.size();
                                    r.getCTR().setSymArray((CTSym[]) symList.toArray(new CTSym[size]));
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    log.error("", e);
                                }
                            }
                        }
                    }
                    if (isSetText) {
                        // 设置值
                        setRunText(key, map, r, text);
                    }
                }

            }
        }
    }
    /**
     *
     * 描述 根据关键字判断增加行
     *
     * @author Rider Chen
     * @created 2016年12月14日 下午4:05:29
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param key
     */
    public static void addTableRow3(Map<String, Object> param, XWPFTable table, List<XWPFTableRow> rows,
                                    XWPFTableRow oldRow, List<Map<String, Object>> dataList, String key,
                                     CustomXWPFDocument doc) {
        int rowNum = 1;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                if (paragraphListTable != null && paragraphListTable.size() > 0) {
                    for (XWPFParagraph paragraph : paragraphListTable) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.indexOf(key) != -1) {
                                oldRow = row;
                                if (null != dataList) {
                                    for (int i = 0; i < dataList.size(); i++) {
                                        if (i > 0) {
                                            rowNum++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int rNum = table.getRows().size();
        Map<String, Object> map;
        for (int i = 1; i < rowNum; i++) {
            map = dataList.get(i);
            table.createRow();
            List<XWPFTableCell> cells = oldRow.getTableCells();
            XWPFTableRow row = table.getRow(i + rNum - 1);

            row.setHeight(oldRow.getHeight());
            List<XWPFTableCell> newCells = row.getTableCells();
            for (int j = newCells.size(); j < cells.size(); j++) {
                row.createCell();
            }
            for (int j = 0; j < cells.size(); j++) {
                XWPFParagraph oldp = cells.get(j).getParagraphs().get(0);
                if (newCells.size() > j) {
                    XWPFParagraph p = newCells.get(j).addParagraph();
                    if (key.equals("DIRECTOR_NAME") || key.equals("SUPERVISOR_NAME") || key.equals("MANAGER_NAME")
                            || key.equals("CONTROLLER_NAME")) {
                        p.setAlignment(ParagraphAlignment.LEFT);
                    } else {
                        p.setAlignment(ParagraphAlignment.CENTER);
                    }
                    p.setStyle(oldp.getStyle());
                    XWPFRun r = p.createRun();// 创建段落文本
                    r.setFontSize(10);
                    r.setBold(false);
                    String text = map.get(cells.get(j).getText()) == null ? cells.get(j).getText()
                            : map.get(cells.get(j).getText()).toString();

                    boolean isSetText = true;
                    for (Entry<String, Object> entry : map.entrySet()) {
                        String key1 = entry.getKey();
                        if (cells.get(j).getText().indexOf(key1) != -1) {
                            Object value = entry.getValue();
                            if (value instanceof List) {// 列表段落
                                isSetText = false;
                                if (!key1.equals("investorList")) {
                                    List<Object> list = (LinkedList<Object>) value;
                                    for (int h = 0; h < list.size(); h++) {
                                        String s = (String) list.get(h);
                                        r.setText(s);
                                        r.addBreak();
                                    }
                                }
                            } else if (value instanceof CTSym) {
                                // 特殊符号
                                isSetText = false;
                                CTSym ctsym = (CTSym) value;
                                r.setText(text.replace(key, ""), 0);
                                try {
                                    CTRPr pRpr = getRunCTRPr(p, r);
                                    // 设置字体大小
                                    CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
                                    sz.setVal(new BigInteger("24"));
                                    List<CTSym> symList = new ArrayList<CTSym>();
                                    symList.add(ctsym);
                                    int size = symList.size();
                                    r.getCTR().setSymArray((CTSym[]) symList.toArray(new CTSym[size]));
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    log.error("", e);
                                }
                            }  else if (value instanceof Map) {// 图片替换
                                text = text.replace(key, "");
                                Map pic = (Map) value;
                                int width = Integer.parseInt(pic.get("width").toString());
                                int height = Integer.parseInt(pic.get("height").toString());
                                int picType = getPictureType(pic.get("type").toString());
                                byte[] byteArray = (byte[]) pic.get("content");
                                ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                try {
                                    String ind = doc.addPictureData(byteInputStream, picType);
                                    doc.createPicture(ind, doc.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG),
                                            width, height, r);
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                }
                            }
                        }
                    }
                    if (isSetText) {
                        // 设置值
                        setRunText3(param,key, map, r, text,doc);
                    }
                }

            }
        }
    }

    /**
     * 
     * 描述 根据关键字判断增加行
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午4:05:29
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param key
     */
    public static void addTableRow2(Map<String, Object> param, XWPFTable table, List<XWPFTableRow> rows,
            XWPFTableRow oldRow, List<Map<String, Object>> dataList, String key) {
        int rowNum = 1;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                if (paragraphListTable != null && paragraphListTable.size() > 0) {
                    for (XWPFParagraph paragraph : paragraphListTable) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.indexOf(key) != -1) {
                                oldRow = row;
                                for (int i = 0; i < dataList.size(); i++) {
                                    if (i > 0) {
                                        rowNum++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int rNum = table.getRows().size();
        Map<String, Object> map;
        for (int i = 1; i < rowNum; i++) {
            map = dataList.get(i);
            table.createRow();
            List<XWPFTableCell> cells = oldRow.getTableCells();
            XWPFTableRow row = table.getRow(i + rNum - 1);

            row.setHeight(oldRow.getHeight());
            List<XWPFTableCell> newCells = row.getTableCells();
            for (int j = newCells.size(); j < cells.size(); j++) {
                row.createCell();
            }
            for (int j = 0; j < cells.size(); j++) {
                XWPFParagraph oldp = cells.get(j).getParagraphs().get(0);
                if (newCells.size() > j) {
                    XWPFParagraph p = newCells.get(j).addParagraph();
                    // p.setAlignment(ParagraphAlignment.CENTER);
                    p.setStyle(oldp.getStyle());
                    XWPFRun r = p.createRun();// 创建段落文本
                    // r.setFontSize(10);
                    r.setBold(false);
                    String text = map.get(cells.get(j).getText()) == null ? cells.get(j).getText()
                            : map.get(cells.get(j).getText()).toString();

                    for (Entry<String, Object> entry : map.entrySet()) {
                        String key1 = entry.getKey();
                        if (cells.get(j).getText().indexOf(key1) != -1) {
                            Object value = entry.getValue();
                            if (value instanceof String) {
                                r.setText(text.replace(key, value.toString()));
                            } else if (value instanceof List) {// 列表段落
                                List<Object> list = (LinkedList<Object>) value;
                                for (int h = 0; h < list.size(); h++) {
                                    String s = (String) list.get(h);
                                    r.setText(s);
                                    r.addBreak();
                                }
                            } else if (value instanceof CTSym) {// 特殊符号
                                CTSym ctsym = (CTSym) value;
                                r.setText(text.replace(key, ""), 0);
                                try {
                                    CTRPr pRpr = getRunCTRPr(p, r);
                                    // 设置字体大小
                                    CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
                                    sz.setVal(new BigInteger("24"));
                                    List<CTSym> symList = new ArrayList<CTSym>();
                                    symList.add(ctsym);
                                    int size = symList.size();
                                    r.getCTR().setSymArray((CTSym[]) symList.toArray(new CTSym[size]));
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    log.error("", e);
                                }
                            } else {
                                if (null != value) {
                                    r.setText(text.replace(key, value.toString()));
                                } else {
                                    text = text.replace(key, "");
                                }
                            }
                        }
                    }

                }

            }
        }
    }
    
    /**
     * 
     * 描述     根据关键字判断并在所在行下方增加行
     * @author Danto Huang
     * @created 2020年8月26日 下午5:20:14
     * @param param
     * @param table
     * @param rows
     * @param oldRow
     * @param dataList
     * @param key
     */
    public static void addTableRow3(Map<String, Object> param, XWPFTable table, List<XWPFTableRow> rows,
            XWPFTableRow oldRow, List<Map<String, Object>> dataList, String key) {
        int rowNum = 1;
        //int rowindex = 0;
        //int insertindex = 0;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            
            for (XWPFTableCell cell : cells) {
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                if (paragraphListTable != null && paragraphListTable.size() > 0) {
                    for (XWPFParagraph paragraph : paragraphListTable) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.indexOf(key) != -1) {
                                oldRow = row;
                                //insertindex = rowindex;
                                if (null != dataList) {
                                    for (int i = 0; i < dataList.size(); i++) {
                                        if (i > 0) {
                                            rowNum++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //rowindex++;
        }
        int rNum = table.getRows().size();
        Map<String, Object> map;
        for (int i = 1; i < rowNum; i++) {
            map = dataList.get(i);
            XWPFTableRow targetRow = table.insertNewTableRow(i + rNum - 1);
            targetRow.getCtRow().setTrPr(oldRow.getCtRow().getTrPr());
            targetRow.setHeight(oldRow.getHeight());
            //需要复制的行的列
            List<XWPFTableCell> cells = oldRow.getTableCells();
            List<XWPFTableCell> newCells = targetRow.getTableCells();
            XWPFTableCell targetCell = null;
            XWPFTableCell sourceCell = null;
            for (int j = newCells.size(); j < cells.size(); j++) {
                targetCell = targetRow.addNewTableCell();
                sourceCell = cells.get(j);
                // 列属性
                if (sourceCell.getCTTc() != null) {
                    targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
                }
                // 删除段落
                for (int pos = 0; pos < targetCell.getParagraphs().size(); pos++) {
                    targetCell.removeParagraph(pos);
                }
                // 添加段落
                for (XWPFParagraph sp : sourceCell.getParagraphs()) {
                    XWPFParagraph targetP = targetCell.addParagraph();
                    // 设置段落样式
                    targetP.getCTP().setPPr(sp.getCTP().getPPr());
                    // 移除所有的run
                    for (int pos = targetP.getRuns().size() - 1; pos >= 0; pos--) {
                        targetP.removeRun(pos);
                    }
                    // copy 新的run
                    for (XWPFRun s : sp.getRuns()) {
                        XWPFRun targetrun = targetP.createRun();
                        // 设置run属性
                        targetrun.getCTR().setRPr(s.getCTR().getRPr());

                        String text = map.get(cells.get(j).getText()) == null ? cells.get(j).getText()
                                : map.get(cells.get(j).getText()).toString();
                        targetrun.setText(text);
                    }
                }
            }
            
        }
    }

    /**
     * 
     * 描述 设置值
     * 
     * @author Rider Chen
     * @created 2016年12月15日 下午2:23:07
     * @param key
     * @param map
     * @param r
     * @param text
     */
    private static void setRunText(String key, Map<String, Object> map, XWPFRun r, String text) {
        if (key.equals("DIRECTOR_NAME")) {
            r.setText("姓名：" + map.get("DIRECTOR_NAME") + "     职务：" + map.get("DIRECTOR_JOB") + "     产生方式："
                    + map.get("DIRECTOR_GENERATION_MODE"));
            String DIRECTOR_COUNTRY = map.get("DIRECTOR_COUNTRY") == null ? "" : map.get("DIRECTOR_COUNTRY").toString();
            if (StringUtils.isNotEmpty(DIRECTOR_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("DIRECTOR_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("DIRECTOR_IDTYPE") + "     身份证件号码：" + map.get("DIRECTOR_IDNO"));
            setAddBreak(r, 6);
            r.setText("OPERATOR_FPHOTO");
            setAddBreak(r, 6);
            r.setText("（身份证件复印件粘贴处）");
            setAddBreak(r, 6);
            r.setText("OPERATOR_BPHOTO");
        } else if (key.equals("SUPERVISOR_NAME")) {
            r.setText("姓名：" + map.get("SUPERVISOR_NAME") + "     职务：" + map.get("SUPERVISOR_JOB") + "     产生方式："
                    + map.get("SUPERVISOR_GENERATION_MODE"));
            String SUPERVISOR_COUNTRY = map.get("SUPERVISOR_COUNTRY") == null ? ""
                    : map.get("SUPERVISOR_COUNTRY").toString();
            if (StringUtils.isNotEmpty(SUPERVISOR_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("SUPERVISOR_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("SUPERVISOR_IDTYPE") + "     身份证件号码：" + map.get("SUPERVISOR_IDNO"));
            setAddBreak(r, 6);
            r.setText("                         （身份证件复印件粘贴处）");
        } else if (key.equals("MANAGER_NAME")) {
            r.setText("姓名：" + map.get("MANAGER_NAME") + "     职务：" + map.get("MANAGER_JOB") + "     产生方式："
                    + map.get("MANAGER_GENERATION_MODE"));
            String MANAGER_COUNTRY = map.get("MANAGER_COUNTRY") == null ? "" : map.get("MANAGER_COUNTRY").toString();
            if (StringUtils.isNotEmpty(MANAGER_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("MANAGER_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("MANAGER_IDTYPE") + "     身份证件号码：" + map.get("MANAGER_IDNO"));
            setAddBreak(r, 6);
            r.setText("                         （身份证件复印件粘贴处）");
        } else if (key.equals("CONTROLLER_NAME")) {
            r.setText("姓名/名称（中文）：" + map.get("CONTROLLER_NAME") + "      姓名/名称（英文）：" + map.get("ENGCONTROLLERNAME"));
            setAddBreak(r, 1);
            r.setText("国籍（或地区）/注册地：" + map.get("REGISTERED_PLACE"));
            setAddBreak(r, 1);
            r.setText("证照号码：" + map.get("CONTROLLER_IDNO"));
            setAddBreak(r, 1);
            r.setText("控制人类别：" + map.get("CONTROLLER_TYPE"));
            setAddBreak(r, 1);
            r.setText("实际控制方式：" + map.get("CONTROL_METHOD"));
            setAddBreak(r, 1);
            r.setText("控制方式详细说明：" + map.get("CONTROLMETHODOTHER"));
        } else {
            r.setText(text);
        }
    }
    /**
     *
     * 描述 设置值
     *
     * @author Rider Chen
     * @created 2016年12月15日 下午2:23:07
     * @param key
     * @param map
     * @param r
     * @param text
     */
    private static void setRunText3(Map<String, Object> param, String key, Map<String, Object> map, XWPFRun r,
            String text, CustomXWPFDocument doc) {
        if (key.equals("DIRECTOR_NAME")) {
            r.setText("姓名：" + map.get("DIRECTOR_NAME") + "     职务：" + map.get("DIRECTOR_JOB") + "     产生方式："
                    + map.get("DIRECTOR_GENERATION_MODE"));
            String DIRECTOR_COUNTRY = map.get("DIRECTOR_COUNTRY") == null ? "" : map.get("DIRECTOR_COUNTRY").toString();
            if (StringUtils.isNotEmpty(DIRECTOR_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("DIRECTOR_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("DIRECTOR_IDTYPE") + "     身份证件号码：" + map.get("DIRECTOR_IDNO"));
            setAddBreak(r, 1);
            String exeId = StringUtil.getString(param.get("EXE_ID"));
            r.setText("（身份证件复印件粘贴处）");
            setAddBreak(r, 1);
            setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("DIRECTOR_NAME")),
                    StringUtil.getString(map.get("DIRECTOR_IDNO")), exeId, "SIGN_IDPHOTO_FRONT");
            r.setText("    ");
            setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("DIRECTOR_NAME")),
                    StringUtil.getString(map.get("DIRECTOR_IDNO")), exeId, "SIGN_IDPHOTO_BACK");
        } else if (key.equals("SUPERVISOR_NAME")) {
            r.setText("姓名：" + map.get("SUPERVISOR_NAME") + "     职务：" + map.get("SUPERVISOR_JOB") + "     产生方式："
                    + map.get("SUPERVISOR_GENERATION_MODE"));
            String SUPERVISOR_COUNTRY = map.get("SUPERVISOR_COUNTRY") == null ? ""
                    : map.get("SUPERVISOR_COUNTRY").toString();
            if (StringUtils.isNotEmpty(SUPERVISOR_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("SUPERVISOR_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("SUPERVISOR_IDTYPE") + "     身份证件号码：" + map.get("SUPERVISOR_IDNO"));

            String BUS_TABLENAME = StringUtil.getString(param.get("BUS_TABLENAME"));
            if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_SSNZQYBA")) {
                setAddBreak(r, 1);
                String exeId = StringUtil.getString(param.get("EXE_ID"));
                r.setText("（身份证件复印件粘贴处）");
                setAddBreak(r, 1);
                setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("SUPERVISOR_NAME")),
                        StringUtil.getString(map.get("SUPERVISOR_IDNO")), exeId, "SIGN_IDPHOTO_FRONT");
                r.setText("    ");
                setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("SUPERVISOR_NAME")),
                        StringUtil.getString(map.get("SUPERVISOR_IDNO")), exeId, "SIGN_IDPHOTO_BACK");
            } else {
                setAddBreak(r, 6);
                r.setText("（身份证件复印件粘贴处）");
            }

        } else if (key.equals("MANAGER_NAME")) {
            r.setText("姓名：" + map.get("MANAGER_NAME") + "     职务：" + map.get("MANAGER_JOB") + "     产生方式："
                    + map.get("MANAGER_GENERATION_MODE"));
            String MANAGER_COUNTRY = map.get("MANAGER_COUNTRY") == null ? "" : map.get("MANAGER_COUNTRY").toString();
            if (StringUtils.isNotEmpty(MANAGER_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("MANAGER_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("MANAGER_IDTYPE") + "     身份证件号码：" + map.get("MANAGER_IDNO"));
            String BUS_TABLENAME = StringUtil.getString(param.get("BUS_TABLENAME"));
            if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_SSNZQYBA")) {
                setAddBreak(r, 1);
                String exeId = StringUtil.getString(param.get("EXE_ID"));
                r.setText("    ");
                r.setText("（身份证件复印件粘贴处）");
                setAddBreak(r, 1);
                setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("MANAGER_NAME")),
                        StringUtil.getString(map.get("MANAGER_IDNO")), exeId, "SIGN_IDPHOTO_FRONT");
                r.setText("    ");
                setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("MANAGER_NAME")),
                        StringUtil.getString(map.get("MANAGER_IDNO")), exeId, "SIGN_IDPHOTO_BACK");
            } else {
                setAddBreak(r, 6);
                r.setText("（身份证件复印件粘贴处）");
            }
        } else if (key.equals("DSJSJL_NAME")) {
            r.setText("姓名：" + map.get("DSJSJL_NAME") + "     职务：" + map.get("DSJSJL_JOB") + "     产生方式："
                    + map.get("DSJSJL_GENERATION_MODE"));
            String DSJSJL_COUNTRY = map.get("DSJSJL_COUNTRY") == null ? "" : map.get("DSJSJL_COUNTRY").toString();
            if (StringUtils.isNotEmpty(DSJSJL_COUNTRY)) {
                r.setText("     国别（地区）:" + map.get("DSJSJL_COUNTRY"));
            }
            setAddBreak(r, 1);
            r.setText("身份证件类型：" + map.get("DSJSJL_IDTYPE") + "     身份证件号码：" + map.get("DSJSJL_IDNO"));
            String BUS_TABLENAME = StringUtil.getString(param.get("BUS_TABLENAME"));
            if (StringUtils.isNotEmpty(BUS_TABLENAME) && (BUS_TABLENAME.equals("T_COMMERCIAL_SSNZQYBA")
                    || BUS_TABLENAME.equals("T_COMMERCIAL_CHANGE_DOMESTIC"))) {
                setAddBreak(r, 3);
                String exeId = StringUtil.getString(param.get("EXE_ID"));
                if(BUS_TABLENAME.equals("T_COMMERCIAL_CHANGE_DOMESTIC")){
                    r.setText("（身份证件复、影印件粘贴处）"); 
                }else{
                    r.setText("（身份证件复印件粘贴处）");
                }
                setAddBreak(r, 1);
                setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("DSJSJL_NAME")),
                        StringUtil.getString(map.get("DSJSJL_IDNO")), exeId, "SIGN_IDPHOTO_FRONT");
                r.setText("    ");
                setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("DSJSJL_NAME")),
                        StringUtil.getString(map.get("DSJSJL_IDNO")), exeId, "SIGN_IDPHOTO_BACK");
            }else {
                setAddBreak(r, 6);
                r.setText("（身份证件复印件粘贴处）");
            }
        }else if(key.equals("HOLDERPHOTOFRONT")){//图片过滤不设值文本
           /* r.setText(map.get("HOLDERFRONT") + "                     " + map.get("HOLDERBACK"));
            setAddBreak(r, 6);
            String exeId = StringUtil.getString(param.get("EXE_ID"));
            setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("HOLDER_NAME")),
                    StringUtil.getString(map.get("HOLDER_IDNO")), exeId, "SIGN_IDPHOTO_FRONT");
            setImgByNameAndExeId(doc, r, StringUtil.getString(map.get("HOLDER_NAME")),
                    StringUtil.getString(map.get("HOLDER_IDNO")), exeId, "SIGN_IDPHOTO_BACK");*/
        }else if (key.equals("CONTROLLER_NAME")) {
            r.setText("姓名/名称（中文）：" + map.get("CONTROLLER_NAME") + "      姓名/名称（英文）：" + map.get("ENGCONTROLLERNAME"));
            setAddBreak(r, 1);
            r.setText("国籍（或地区）/注册地：" + map.get("REGISTERED_PLACE"));
            setAddBreak(r, 1);
            r.setText("证照号码：" + map.get("CONTROLLER_IDNO"));
            setAddBreak(r, 1);
            r.setText("控制人类别：" + map.get("CONTROLLER_TYPE"));
            setAddBreak(r, 1);
            r.setText("实际控制方式：" + map.get("CONTROL_METHOD"));
            setAddBreak(r, 1);
            r.setText("控制方式详细说明：" + map.get("CONTROLMETHODOTHER"));
        } else {
            r.setText(text);
        }
    }
    /**
     * 设置图片到word里
     * @param r
     * @param name
     * @param idNo
     * @param exeId
     */
    @SuppressWarnings("unchecked")
    private static void setImgByNameAndExeId (CustomXWPFDocument doc,
            XWPFRun r,String name ,String idNo,String exeId,String colName){
        ExeDataService exeDataService= (ExeDataService) AppUtil.getBean("exeDataService");
        Map<String,Object> sign=exeDataService.getByJdbc("T_COMMERCIAL_SIGN",
                new String[]{"EXE_ID","SIGN_NAME","SIGN_IDNO","SIGN_FLAG"}
                ,new Object[]{exeId,name,idNo,"1"});
        if(sign==null){
            return;
        }
        String fileId=StringUtil.getString(sign.get(colName));
        if(StringUtils.isEmpty(fileId)){
            return ;
        }
        Map<String,Object> fileAttach=exeDataService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{fileId});
        String relPath=StringUtil.getString(fileAttach.get("FILE_PATH"));
        Properties properties = FileUtil.readProperties("project.properties");
        String urlattachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        String attachFileFolderPath = urlattachFileFolderPath + relPath;
        InputStream fis=null;
        try {
            fis=DownLoadServlet.getStreamDownloadOutFile(attachFileFolderPath);
            byte[] byteArray = IOUtils.toByteArray(fis);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
            String ind = doc.addPictureData(byteInputStream, XWPFDocument.PICTURE_TYPE_PNG);
            doc.createPicture(ind, doc.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG),
                    280,180, r);
        } catch (Exception e) {
            log.info(e.getMessage());
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }

    /**
     * 
     * 描述 表格列换行
     * 
     * @author Rider Chen
     * @created 2016年12月15日 下午2:26:54
     * @param r
     * @param num
     */
    private static void setAddBreak(XWPFRun r, int num) {
        for (int i = 0; i < num; i++) {
            r.addBreak();// 换行
        }
    }
    /**
     * 处理段落
     * 
     * @param paragraphList
     */
    @SuppressWarnings("rawtypes")
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param,
            CustomXWPFDocument doc) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {// 文本替换
                                    text = text.replace(key, value.toString());
                                } else if (value instanceof CTSym) {// 特殊符号
                                    isSetText = false;
                                    CTSym ctsym = (CTSym) value;
                                    run.setText(text.replace(key, ""), 0);
                                    try {
                                        CTRPr pRpr = getRunCTRPr(paragraph, run);
                                        // 设置字体大小
                                        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
                                        sz.setVal(new BigInteger("24"));
                                        List<CTSym> symList = new ArrayList<CTSym>();
                                        symList.add(ctsym);
                                        int size = symList.size();
                                        run.getCTR().setSymArray((CTSym[]) symList.toArray(new CTSym[size]));
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        log.error(e.getMessage());
                                    }
                                } else if (value instanceof List) {// 列表段落
                                    isSetText = false;
                                    if (!key.equals("investorList") && !key.equals("fahqtzjwfdbList")) {
                                        List<Object> list = (LinkedList<Object>) value;
                                        run.setText("", 0);
                                        for (int i = 0; i < list.size(); i++) {
                                            String s = (String) list.get(i);
                                            run.setText(s);
                                            run.addBreak();
                                        }
                                    }
                                } else if (value instanceof Map) {// 图片替换
                                    text = text.replace(key, "");
                                    Map pic = (Map) value;
                                    int width = Integer.parseInt(pic.get("width").toString());
                                    int height = Integer.parseInt(pic.get("height").toString());
                                    int picType = getPictureType(pic.get("type").toString());
                                    byte[] byteArray = (byte[]) pic.get("content");
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                    try {
                                        String ind = doc.addPictureData(byteInputStream, picType);
                                        doc.createPicture(ind, doc.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG),
                                                width, height, run);
                                    } catch (Exception e) {
                                        log.error(e.getMessage());
                                    }
                                } else if (value instanceof Set) {// 多图片替换
                                    text = text.replace(key, "");
                                    Set picset = (Set)value;
                                    for (Object object : picset) {
                                        Map pic = (Map) object;
                                        int width = Integer.parseInt(pic.get("width").toString());
                                        int height = Integer.parseInt(pic.get("height").toString());
                                        int picType = getPictureType(pic.get("type").toString());
                                        byte[] byteArray = (byte[]) pic.get("content");
                                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                        try {
                                            String ind = doc.addPictureData(byteInputStream, picType);
                                            doc.createPicture(ind, doc.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG),
                                                    width, height, run);
                                            run.setText(" ");
                                        } catch (Exception e) {
                                            log.error("",e);
                                        }
                                    }
                                } else {
                                    if (null != value) {
                                        text = text.replace(key, value.toString());
                                    } else {
                                        text = text.replace(key, "");
                                    }
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月20日 下午2:29:40
     * @param p
     * @param isInsert
     * @param isNewLine
     * @return
     */
    public static XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert, boolean isNewLine) {
        XWPFRun pRun = null;
        if (isInsert) {
            pRun = p.createRun();
        } else {
            if (p.getRuns() != null && p.getRuns().size() > 0) {
                pRun = p.getRuns().get(0);
            } else {
                pRun = p.createRun();
            }
        }
        if (isNewLine) {
            pRun.addBreak();
        }
        return pRun;
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月20日 下午2:21:32
     * @param wingType
     * @param charStr
     * @return
     * @throws Exception
     */
    public static CTSym getCTSym(String wingType, String charStr) throws Exception {
        CTSym sym = CTSym.Factory.parse("<xml-fragment w:font=\"" + wingType + "\" w:char=\"" + charStr
                + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\""
                + " xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
        return sym;
    }

    /**
     * 
     * 描述 得到XWPFRun的CTRPr
     * 
     * @author Rider Chen
     * @created 2016年12月20日 下午2:18:09
     * @param p
     * @param pRun
     * @return
     */
    public static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
        CTRPr pRpr = null;
        if (pRun.getCTR() != null) {
            pRpr = pRun.getCTR().getRPr();
            if (pRpr == null) {
                pRpr = pRun.getCTR().addNewRPr();
            }
        } else {
            pRpr = p.getCTP().addNewR().addNewRPr();
        }
        return pRpr;
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     * 
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     * 
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    log.error("关闭流失败");
                }
            }
        }
        return byteArray;
    }

    /**
     * 
     * 描述 替换word
     * 
     * @author Rider Chen
     * @created 2016年11月30日 下午12:37:29
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public static void replaceWord(Map<String, Object> param, String filepathString, String destpathString) {
        CustomXWPFDocument doc = null;
        FileOutputStream fopts = null;
        InputStream in = null;
        try {
            //判断模版地址是否是网络地址，是网络地址需要先进行下载
            if (filepathString.contains("http://") || filepathString.contains("https://")) {
                in = DownLoadServlet.getStreamDownloadOutFile(filepathString);
                doc = generateWord(param, in);
            } else {
                doc = generateWord(param, filepathString);
            }
            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("", e);
        } finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭in流异常", e);
                }
            }
        }
    }

    /**
     * 描述:生成excel模板替换文档
     *
     * @author Madison You
     * @created 2020/11/6 11:04:00
     * @param
     * @return
     */
    public static void replaceExcel(Map<String,Object> params , String filepathString, String destpathString) {
        InputStream in = null;
        FileOutputStream fos = null;
        try{
            //判断模版地址是否是网络地址，是网络地址需要先进行下载
            if (filepathString.contains("http://") || filepathString.contains("https://")) {
                in = DownLoadServlet.getStreamDownloadOutFile(filepathString);
            } else {
                in = new FileInputStream(filepathString);
            }
            XSSFWorkbook workbook = WordReplaceUtil.generateExcel(params, in);
            fos = new FileOutputStream(destpathString);
            workbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try{
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try{
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 描述:通用excel生成
     *
     * @author Madison You
     * @created 2020/11/6 14:02:00
     * @param params  回填数据
     * @param in   输入流
     * @return
     */
    public static XSSFWorkbook generateExcel(Map<String, Object> params, InputStream in) {
        XSSFWorkbook workbook = null;
        try{
            workbook = new XSSFWorkbook(in);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheetAt = workbook.getSheetAt(i);
                for (Row row : sheetAt) {
                    if (row != null) {
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            /*设置cell类型*/
                            if (cell != null) {
                                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            }
                            String value= cell.getStringCellValue();
                            if (cell == null || value == null) {
                                continue;
                            }
                            if (!Objects.equals(cell, "")) {
                                if (params != null) {
                                    Iterator<String> iterator = params.keySet().iterator();
                                    while (iterator.hasNext()) {
                                        String k = iterator.next();
                                        String v = (String) params.get(k);
                                        if (Objects.equals(k, value)) {
                                            cell.setCellValue(v);
                                            break;
                                        } else if (value.contains(k)) {
                                            int index = value.indexOf(k);
                                            int len = k.length();
                                            cell.setCellValue(value.substring(0, index) + v
                                                    + value.substring(index + len));
                                        }
                                    }
                                }
                            } else {
                                cell.setCellValue("");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("excel文档模板替换失败", e);
        }
        return workbook;
    }

}
