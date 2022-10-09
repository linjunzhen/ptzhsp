/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.w3c.dom.Node;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 描述 word文档输出
 * 
 * @author Danto Huang
 * @created 2015-1-8 上午9:09:29
 */
public class GCJSWordUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(GCJSWordUtil.class);
    //模板文件地址
    //private static String inputUrl = "C:\\Users\\17689\\Desktop\\demo\\001.docx";
    /**
     * 新产生的临时文件
     */
    private static String tempUrl = "D:\\pdfFile\\attachFiles\\sccl\\temp\\temp.docx";
    //输出文件地址
    //private static String outputUrl = "C:\\Users\\17689\\Desktop\\demo\\out.docx";

    /** docx中定义的部分常量引用  **/
    public static final String BOOKMARK_END_TAG = "w:bookmarkEnd";

    /**
     * main函数
     * @param args
     */
//    public static void main(String[] args) {
//        try{
//            URL url = null;
//            URLConnection conn = null;
//            InputStream inStream =null;
//            url = new URL("http://xzfwzx.pingtan.gov.cn:8083/ptzhspFile/attachFiles/sccl/001.docx");
//            conn = url.openConnection();
//            inStream = conn.getInputStream();
//            XWPFDocument document = new XWPFDocument(inStream);
//            URL outUrl = new URL("http://xzfwzx.pingtan.gov.cn:8083/ptzhspFile/attachFiles/sccl/002.docx");
//            URLConnection conn2 =  outUrl.openConnection();
//            OutputStream outputStream = conn2.getOutputStream();
//            document.write(outputStream);
//            outputStream.close();
//            File file2 = new File(outputUrl);
//            FileOutputStream stream2 = new FileOutputStream(file2);
//            document.write(stream2);
//            stream2.close();
//        }catch (Exception e ){
//
//
//        }
//
//
//        Map<String ,Object> param = new HashMap<>();
//
//
//        Map<String, Object> testMap = new HashMap<String, Object>();
//        testMap.put("BUILDCORPNAME", "长威");
//        testMap.put("LEGALPERSON", "Sophie");
//        testMap.put("OWNERSHIP", "Sophie");
//        testMap.put("PRJFUNCTIONNUM500", "\uF0A3");
//
//
//        try{
//            testMap.put("PRJFUNCTIONNUM600", GCJSWordUtil.getCTSym("Wingdings 2", "F052"));
//        }catch (Exception e){
//        }
//        GCJSWordUtil.generateWord(inputUrl, outputUrl, testMap);
//    }
    /**
     * 处理空值
     * @param ob
     * @return
     */
    private static String handleEmpty(Object ob){
        String value = ob == null ? "" : ob.toString();
        return value;
    }
    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     * @param inputUrl 模板存放地址
     * @param outputUrl 新文档存放地址
     * @param textMap 需要替换的信息集合
     * @return 成功返回true,失败返回false
     */
    public static Map<String ,Object> generateWord(String busTableName,String inputUrl, String outputUrl,
                                    Map<String, Object> textMap) {

        //模板转换默认成功
        boolean generateFlag = true;
        Map<String ,Object> resultInfo = new HashMap<>();
        Long fileLength = 0L;
        try {
            //获取docx解析对象
            //XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            URL url = null;
            URLConnection conn = null;
            InputStream inStream =null;
            url = new URL(inputUrl);
            conn = url.openConnection();
            inStream = conn.getInputStream();
            XWPFDocument document = new XWPFDocument(inStream);
            //解析替换文本段落对象
            GCJSWordUtil.changeText(document, textMap);
            //解析替换表格对象
            GCJSWordUtil.changeTable(busTableName,document, textMap,outputUrl);

            File file = new File(outputUrl);
            fileLength = file.length();
            inStream.close();
            //生成新的word
//            File file = new File(outputUrl);
//            FileOutputStream stream = new FileOutputStream(file);
//            document.write(stream);
//            stream.close();

        } catch (IOException e) {
            log.error(e.getMessage());
            generateFlag = false;
        }

        resultInfo.put("fileLength",fileLength);
        resultInfo.put("generateFlag",generateFlag);

        return resultInfo;

    }
    /**
     * 判断文本中是否包含$
     * @param text 文本
     * @return 包含返回true,不包含返回false
     */
    public static boolean checkText(String text){
        boolean check  =  false;
        if(text.indexOf("$")!= -1){
            check = true;
        }
        return check;

    }
    /**
     * 匹配传入信息集合与模板
     * @param value 模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, Object> textMap){
        Set<Entry<String, Object>> textSets = textMap.entrySet();
        for (Entry<String, Object> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${"+textSet.getKey()+"}";
            if(value.indexOf(key)!= -1){
                value = (String) textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if(checkText(value)){
            value = "";
        }
        return value;
    }
    /**
     * 替换段落文本
     * @param document docx解析对象
     * @param dataMap 需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, Object> dataMap){
        //获取普通段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        //替换段落中文字
        changeParagraphText(paragraphs,dataMap);

    }

    /**
     * 替换文字
     * @param paragraphs
     * @param dataMap
     */
    private static void changeParagraphText(List<XWPFParagraph> paragraphs,Map<String, Object> dataMap){

        for (XWPFParagraph paragraph : paragraphs) {

            CTP ctp = paragraph.getCTP();

            for(int dwI = 0;dwI < ctp.sizeOfBookmarkStartArray();dwI++){
                CTBookmark bookmark = ctp.getBookmarkStartArray(dwI);
                String bkName = bookmark.getName();
                if(bkName.indexOf("PO_")>-1){
                    bkName = bkName.substring(bkName.indexOf("T_")+2,bkName.lastIndexOf('_'));
                    if(dataMap.containsKey(bkName)){

                        XWPFRun run = paragraph.createRun();
                        //run.setText((String)dataMap.get(bkName));
                        String text = dataMap.get(bkName) == null ? ""
                                : dataMap.get(bkName).toString();
                        if(dataMap.get(bkName) instanceof CTSym){
                            CTSym ctsym = (CTSym) dataMap.get(bkName);
                            //run.setText(text, 0);
                            try {
                                CTRPr pRpr = GCJSWordUtil.getRunCTRPr(paragraph, run);
                                // 设置字体大小
                                CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
                                sz.setVal(new BigInteger("24"));
                                List<CTSym> symList = new ArrayList<CTSym>();
                                symList.add(ctsym);
                                int size = symList.size();
                                run.getCTR().setSymArray((CTSym[]) symList.toArray(new CTSym[size]));
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }else{
                            run.setText((String)dataMap.get(bkName));
                        }


                        Node firstNode = bookmark.getDomNode();
                        Node nextNode = firstNode.getNextSibling();
                        while(nextNode != null){
                            // 循环查找结束符
                            String nodeName = nextNode.getNodeName();
                            if(nodeName.equals(BOOKMARK_END_TAG)){
                                break;
                            }

                            // 删除中间的非结束节点，即删除原书签内容
                            Node delNode = nextNode;
                            nextNode = nextNode.getNextSibling();

                            ctp.getDomNode().removeChild(delNode);
                        }

                        if(nextNode == null){
                            // 始终找不到结束标识的，就在书签前面添加
                            ctp.getDomNode().insertBefore(run.getCTR().getDomNode(),firstNode);
                        }else{
                            // 找到结束符，将新内容添加到结束符之前，即内容写入bookmark中间
                            ctp.getDomNode().insertBefore(run.getCTR().getDomNode(),nextNode);
                        }
                    }
                }

            }
        }
    }

    /**
     * 替换表格对象方法
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     */
    public static void changeTable(String busTableName,XWPFDocument document, Map<String, Object> textMap
            ,String outPutUrl){
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格，且不循环表头
            XWPFTable table = tables.get(i);
            if(table.getRows().size()>1){

                List<XWPFTableRow> rows = table.getRows();
                //遍历表格,并使用书签填充模板
                eachTable(rows, textMap);

                //施工许可申请需要处理json字段
                if("T_BSFW_GCJSSGXK".equals(busTableName)){
                    //为json字段动态插入列
                    insertTable(document,table,textMap);
                    //合并json字段单元格并填充表格
                    mergeTable(document,table,textMap,outPutUrl);
                }else if("TB_FC_PROJECT_INFO".equals(busTableName)){
                    //两张动态表格，仅处理一次即可
                    if(i==tables.size()-1){
                        //消防设计审查处理责任主体信息与单体建筑物信息
                        insertTableForXFSJ(document,tables,textMap);
                        mergeTableForXFSJ(document,tables,textMap,outPutUrl);
                    }
                }else if("TB_PROJECT_FINISH_MANAGE1".equals(busTableName)){
                    insertTableForJGYS(document,table,textMap,18);
                    mergeTableForJGYS(document,table,textMap,outPutUrl,18);
                }else if("TB_PROJECT_FINISH_MANAGE2".equals(busTableName)){
                    insertTableForJGYS2(document,table,textMap,14);
                    mergeTableForJGYS2(document,table,textMap,outPutUrl,14);
                }else {
                    try{
                        File file2 = new File(outPutUrl);
                        FileOutputStream stream2 = new FileOutputStream(file2);
                        document.write(stream2);
                        stream2.close();
                    }catch (IOException e ){
                        log.error(e.getMessage());
                    }

                }
            }
        }
    }

    /**
     * 遍历表格
     * @param rows 表格行对象
     * @param dataMap 需要替换的信息集合
     */
    public static void eachTable(List<XWPFTableRow> rows ,Map<String, Object> dataMap){
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                changeParagraphText(paragraphs,dataMap);
            }
        }
    }

    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param datamap 插入数据集合
     */
    public static void insertTable(XWPFDocument document,XWPFTable table, Map<String, Object> datamap){
        /**
         * 需求是在完整表格中插入行
         * table.addNewRowBetween(16,17);//POI中只有函数名，无具体实现代码
         * table.insertNewTableRow(16);//可以插入行，但样式会变，不适合复杂表格
         * 故采用复制固定样式一行指定位置插入，然后重新赋值的方式去满足需求
         * Q：当存在新插入行的时候该位置合并单元格无效 A1:先输出表格进磁盘，然后重新获取合并赋值
         */
        Map<String, Object> forHandleJson = new LinkedHashMap<String, Object>();//先进先出
        forHandleJson.put("JSDW_JSON",datamap.get("JSDW_JSON"));//工程总包单位
        forHandleJson.put("DJDW_JSON",datamap.get("DJDW_JSON"));//PPP/代建单位
        forHandleJson.put("SGDW_JSON",datamap.get("SGDW_JSON"));//施工总包单位
        forHandleJson.put("KCDW_JSON",datamap.get("KCDW_JSON"));//勘察单位
        forHandleJson.put("SJDW_JSON",datamap.get("SJDW_JSON"));//设计单位
        forHandleJson.put("JLDW_JSON",datamap.get("JLDW_JSON"));//监理单位


        String value ;
        int insertedRowCount = 0;//已插入行数
        int beginRowNumber = 15;//动态表格起始行
        for( Map.Entry<String,Object> entry : forHandleJson.entrySet()){

            if(entry.getKey().indexOf("_JSON")>0 && entry.getValue() != null){
                int rowNumber = beginRowNumber + insertedRowCount;//此字段起始行
                value = entry.getValue() != null ? entry.getValue().toString() : "";
                //解析JSON字段
                List<Map<String, Object>> valueList = JSON.parseObject(value, List.class);//不能处理""
                for(int i=0; i < valueList.size()-1; i++){
                    //为动态表格插入行
                    table.addRow(table.getRow(rowNumber),beginRowNumber+insertedRowCount+1);
                    insertedRowCount++;
                }
            }
            beginRowNumber++;
        }

        try {
            File file = new File(tempUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        }catch (Exception e ){
            log.error(e.getMessage());
        }
    }

    /**
     * 合并单元格
     * @param document
     * @param table
     * @param datamap
     */
    private static void mergeTable(XWPFDocument document,XWPFTable table, Map<String, Object> datamap,String outPutUrl){


        Map<String, Object> forHandleJson = new LinkedHashMap<String, Object>();//先进先出
        forHandleJson.put("JSDW_JSON",datamap.get("JSDW_JSON"));//工程总包单位
        forHandleJson.put("DJDW_JSON",datamap.get("DJDW_JSON"));//PPP/代建单位
        forHandleJson.put("SGDW_JSON",datamap.get("SGDW_JSON"));//施工总包单位
        forHandleJson.put("KCDW_JSON",datamap.get("KCDW_JSON"));//勘察单位
        forHandleJson.put("SJDW_JSON",datamap.get("SJDW_JSON"));//设计单位
        forHandleJson.put("JLDW_JSON",datamap.get("JLDW_JSON"));//监理单位

        String value ;
        int insertedRowCount = 0;//已插入行数
        int beginRowNumber = 15;//动态表格起始行

        try {
            //使用临时文件合并单元格并赋值
            XWPFDocument document2 = new XWPFDocument(POIXMLDocument.openPackage(tempUrl));
            List<XWPFTable> tables = document2.getTables();
            XWPFTable table2 = tables.get(0);
            for( Map.Entry<String,Object> entry : forHandleJson.entrySet()){

                if(entry.getKey().indexOf("_JSON")>0 && entry.getValue() != null){
                    int rowNumber = beginRowNumber + insertedRowCount;//此字段起始行
                    value = entry.getValue() != null ? entry.getValue().toString() : "";
                    //解析JSON字段
                    List<Map<String, Object>> valueList = JSON.parseObject(value, List.class);//不能处理""
                    if(valueList.size()>1){
                        XWPFTableCell cell = table2.getRow(rowNumber).getCell(0);
                        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                    }
                    for(int j=0; j < valueList.size(); j++){
                        if(j>0){
                            XWPFTableCell cell2 = table2.getRow(rowNumber+j).getCell(0);
                            cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                            insertedRowCount++;
                        }
                        Map<String,Object > jsonValue = valueList.get(j);

                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(1),
                                handleEmpty(jsonValue.get("CORPNAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(2),
                                handleEmpty(jsonValue.get("LEGAL_NAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(3),
                                handleEmpty(jsonValue.get("PERSONNAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(4),
                                handleEmpty(jsonValue.get("IDCARDTYPENUM")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(5),
                                handleEmpty(jsonValue.get("PERSONIDCARD")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(6),
                                handleEmpty(jsonValue.get("PERSONPHONE")));
                    }
                }
                beginRowNumber++;
            }
            //输出
            File file2 = new File(outPutUrl);
            FileOutputStream stream2 = new FileOutputStream(file2);
            document2.write(stream2);
            stream2.close();
        }catch (Exception e ){
           log.error(e.getMessage());
        }
    }
    
    
    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param datamap 插入数据集合
     */
    public static void insertTableForJGYS(XWPFDocument document,XWPFTable table, Map<String, Object> datamap,int beginRowNum){
        /**
         * 需求是在完整表格中插入行
         * table.addNewRowBetween(16,17);//POI中只有函数名，无具体实现代码
         * table.insertNewTableRow(16);//可以插入行，但样式会变，不适合复杂表格
         * 故采用复制固定样式一行指定位置插入，然后重新赋值的方式去满足需求
         * Q：当存在新插入行的时候该位置合并单元格无效 A1:先输出表格进磁盘，然后重新获取合并赋值
         */
        Map<String, Object> forHandleJson = new LinkedHashMap<String, Object>();//先进先出
        forHandleJson.put("JSDW_JSON",datamap.get("SGDW_JSON"));//工程总包单位
        forHandleJson.put("DJDW_JSON",datamap.get("DJDW_JSON"));//PPP/代建单位
        forHandleJson.put("SGDW_JSON",datamap.get("SGDW_JSON"));//施工总包单位
        forHandleJson.put("KCDW_JSON",datamap.get("KCDW_JSON"));//勘察单位
        forHandleJson.put("SJDW_JSON",datamap.get("SJDW_JSON"));//设计单位
        forHandleJson.put("JLDW_JSON",datamap.get("JLDW_JSON"));//监理单位


        String value ;
        int insertedRowCount = 0;//已插入行数
        int beginRowNumber = beginRowNum;//动态表格起始行
        for( Map.Entry<String,Object> entry : forHandleJson.entrySet()){

            if(entry.getKey().indexOf("_JSON")>0 && entry.getValue() != null){
                int rowNumber = beginRowNumber + insertedRowCount;//此字段起始行
                value = entry.getValue() != null ? entry.getValue().toString() : "";
                //解析JSON字段
                List<Map<String, Object>> valueList = JSON.parseObject(value, List.class);//不能处理""
                for(int i=0; i < valueList.size()-1; i++){
                    //为动态表格插入行
                    table.addRow(table.getRow(rowNumber),beginRowNumber+insertedRowCount+1);
                    insertedRowCount++;
                }
            }
            beginRowNumber++;
        }

        try {
            File file = new File(tempUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        }catch (Exception e ){
            log.error(e.getMessage());
        }
    }

    /**
     * 合并单元格
     * @param document
     * @param table
     * @param datamap
     */
    private static void mergeTableForJGYS(XWPFDocument document,XWPFTable table, Map<String, Object> datamap,String outPutUrl,int beginRowNum){


        Map<String, Object> forHandleJson = new LinkedHashMap<String, Object>();//先进先出
        forHandleJson.put("JSDW_JSON",datamap.get("SGDW_JSON"));//工程总包单位
        forHandleJson.put("DJDW_JSON",datamap.get("DJDW_JSON"));//PPP/代建单位
        forHandleJson.put("SGDW_JSON",datamap.get("SGDW_JSON"));//施工总包单位
        forHandleJson.put("KCDW_JSON",datamap.get("KCDW_JSON"));//勘察单位
        forHandleJson.put("SJDW_JSON",datamap.get("SJDW_JSON"));//设计单位
        forHandleJson.put("JLDW_JSON",datamap.get("JLDW_JSON"));//监理单位

        String value ;
        int insertedRowCount = 0;//已插入行数
        int beginRowNumber = beginRowNum;//动态表格起始行

        try {
            //使用临时文件合并单元格并赋值
            XWPFDocument document2 = new XWPFDocument(POIXMLDocument.openPackage(tempUrl));
            List<XWPFTable> tables = document2.getTables();
            XWPFTable table2 = tables.get(0);
            for( Map.Entry<String,Object> entry : forHandleJson.entrySet()){

                if(entry.getKey().indexOf("_JSON")>0 && entry.getValue() != null){
                    int rowNumber = beginRowNumber + insertedRowCount;//此字段起始行
                    value = entry.getValue() != null ? entry.getValue().toString() : "";
                    //解析JSON字段
                    List<Map<String, Object>> valueList = JSON.parseObject(value, List.class);//不能处理""
                    if(valueList.size()>1){
                        XWPFTableCell cell = table2.getRow(rowNumber).getCell(0);
                        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                    }
                    for(int j=0; j < valueList.size(); j++){
                        if(j>0){
                            XWPFTableCell cell2 = table2.getRow(rowNumber+j).getCell(0);
                            cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                            insertedRowCount++;
                        }
                        Map<String,Object > jsonValue = valueList.get(j);

                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(1),
                                handleEmpty(jsonValue.get("CORPNAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(2),
                                handleEmpty(jsonValue.get("LEGAL_NAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(3),
                                handleEmpty(jsonValue.get("PERSONNAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(4),
                                handleEmpty(jsonValue.get("IDCARDTYPENUM")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(5),
                                handleEmpty(jsonValue.get("PERSONIDCARD")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(6),
                                handleEmpty(jsonValue.get("PERSONPHONE")));
                    }
                }
                beginRowNumber++;
            }
            //输出
            File file2 = new File(outPutUrl);
            FileOutputStream stream2 = new FileOutputStream(file2);
            document2.write(stream2);
            stream2.close();
        }catch (Exception e ){
           log.error(e.getMessage());
        }
    }   
    
    
    
    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param datamap 插入数据集合
     */
    public static void insertTableForJGYS2(XWPFDocument document,XWPFTable table, Map<String, Object> datamap,int beginRowNum){
        /**
         * 需求是在完整表格中插入行
         * table.addNewRowBetween(16,17);//POI中只有函数名，无具体实现代码
         * table.insertNewTableRow(16);//可以插入行，但样式会变，不适合复杂表格
         * 故采用复制固定样式一行指定位置插入，然后重新赋值的方式去满足需求
         * Q：当存在新插入行的时候该位置合并单元格无效 A1:先输出表格进磁盘，然后重新获取合并赋值
         */
        Map<String, Object> forHandleJson = new LinkedHashMap<String, Object>();//先进先出
        forHandleJson.put("JSDW_JSON",datamap.get("SGDW_JSON"));//工程总包单位
        forHandleJson.put("DJDW_JSON",datamap.get("DJDW_JSON"));//PPP/代建单位
        forHandleJson.put("SGDW_JSON",datamap.get("SGDW_JSON"));//施工总包单位
        forHandleJson.put("KCDW_JSON",datamap.get("KCDW_JSON"));//勘察单位
        forHandleJson.put("SJDW_JSON",datamap.get("SJDW_JSON"));//设计单位
        forHandleJson.put("JLDW_JSON",datamap.get("JLDW_JSON"));//监理单位


        String value ;
        int insertedRowCount = 0;//已插入行数
        int beginRowNumber = beginRowNum;//动态表格起始行
        for( Map.Entry<String,Object> entry : forHandleJson.entrySet()){

            if(entry.getKey().indexOf("_JSON")>0 && entry.getValue() != null){
                int rowNumber = beginRowNumber + insertedRowCount;//此字段起始行
                value = entry.getValue() != null ? entry.getValue().toString() : "";
                //解析JSON字段
                List<Map<String, Object>> valueList = JSON.parseObject(value, List.class);//不能处理""
                for(int i=0; i < valueList.size()-1; i++){
                    //为动态表格插入行
                    table.addRow(table.getRow(rowNumber),beginRowNumber+insertedRowCount+1);
                    insertedRowCount++;
                }
            }
            beginRowNumber++;
        }

        try {
            File file3 = new File(tempUrl);
            FileOutputStream stream = new FileOutputStream(file3);
            document.write(stream);
            stream.close();

        }catch (Exception e ){
            log.error(e.getMessage());
        }
    }

    /**
     * 合并单元格
     * @param document
     * @param table
     * @param datamap
     */
    private static void mergeTableForJGYS2(XWPFDocument document,XWPFTable table, Map<String, Object> datamap,String outPutUrl,int beginRowNum){


        Map<String, Object> forHandleJson = new LinkedHashMap<String, Object>();//先进先出
        forHandleJson.put("JSDW_JSON",datamap.get("SGDW_JSON"));//工程总包单位
        forHandleJson.put("DJDW_JSON",datamap.get("DJDW_JSON"));//PPP/代建单位
        forHandleJson.put("SGDW_JSON",datamap.get("SGDW_JSON"));//施工总包单位
        forHandleJson.put("KCDW_JSON",datamap.get("KCDW_JSON"));//勘察单位
        forHandleJson.put("SJDW_JSON",datamap.get("SJDW_JSON"));//设计单位
        forHandleJson.put("JLDW_JSON",datamap.get("JLDW_JSON"));//监理单位

        String value ;
        int insertedRowCount = 0;//已插入行数
        int beginRowNumber = beginRowNum;//动态表格起始行

        try {
            //使用临时文件合并单元格并赋值
            XWPFDocument document3 = new XWPFDocument(POIXMLDocument.openPackage(tempUrl));
            List<XWPFTable> tables = document3.getTables();
            XWPFTable table2 = tables.get(0);
            for( Map.Entry<String,Object> entry : forHandleJson.entrySet()){

                if(entry.getKey().indexOf("_JSON")>0 && entry.getValue() != null){
                    int rowNumber = beginRowNumber + insertedRowCount;//此字段起始行
                    value = entry.getValue() != null ? entry.getValue().toString() : "";
                    //解析JSON字段
                    List<Map<String, Object>> valueList = JSON.parseObject(value, List.class);//不能处理""
                    if(valueList.size()>1){
                        XWPFTableCell cell = table2.getRow(rowNumber).getCell(0);
                        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                    }
                    for(int j=0; j < valueList.size(); j++){
                        if(j>0){
                            XWPFTableCell cell2 = table2.getRow(rowNumber+j).getCell(0);
                            cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                            insertedRowCount++;
                        }
                        Map<String,Object > jsonValue = valueList.get(j);

                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(1),
                                handleEmpty(jsonValue.get("CORPNAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(2),
                                handleEmpty(jsonValue.get("LEGAL_NAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(3),
                                handleEmpty(jsonValue.get("PERSONNAME")));
                        genParagraphForCell(table2,table2.getRow(rowNumber+j).getCell(4),
                                handleEmpty(jsonValue.get("PERSONPHONE")));
                    }
                }
                beginRowNumber++;
            }
            //输出
            File file4 = new File(outPutUrl);
            FileOutputStream stream3 = new FileOutputStream(file4);
            document3.write(stream3);
            stream3.close();
        }catch (Exception e ){
           log.error(e.getMessage());
        }
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
    private static CTSym getCTSym(String wingType, String charStr) throws Exception {
        CTSym sym = CTSym.Factory.parse("<xml-fragment w:font=\"" + wingType + "\" w:char=\"" + charStr
                + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\""
                + " xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
        return sym;
    }

    /**
     * 为单元格生成段落并填充
     * @param table
     * @param cell
     * @param text
     */
    private static void genParagraphForCell(XWPFTable table,XWPFTableCell cell, String text){
        XWPFTableCell tempCell = table.getRow(16).getCell(0);
        XWPFParagraph op = tempCell.getParagraphs().get(0);
        String pStyle = op.getStyle();
        String fontFamily = op.getRuns().get(0).getFontFamily();
        int fontSize = op.getRuns().get(0).getFontSize();

        cell.removeParagraph(0);
        XWPFParagraph p = cell.addParagraph();
        p.setStyle(pStyle);
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.setFontSize(fontSize);
        r.setFontFamily(fontFamily);
        r.setText(text);
    }

    /**
     * 为单元格生成段落并填充
     * @param table
     * @param cell
     * @param text
     */
    private static void genParagraphForCellByTempRow(XWPFTable table,XWPFTableCell cell, String text,XWPFTableCell tempCell){
        //XWPFTableCell tempCell = table.getRow(16).getCell(0);
//        XWPFParagraph op = tempCell.getParagraphs().get(0);
//        String pStyle = op.getStyle();
//        String fontFamily = op.getRuns().get(0).getFontFamily();
        String pStyle="";
        String fontFamily="";
        //int fontSize = op.getRuns().get(0).getFontSize();

        cell.removeParagraph(0);
        XWPFParagraph p = cell.addParagraph();
        p.setStyle(pStyle);
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.setFontSize(16);
        r.setFontFamily(fontFamily);
        r.setText(text);
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
    private static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
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
     * 为消防设计表格插入数据，行数不够添加新行
     * @param tables 需要插入数据的表格
     * @param datamap 插入数据集合
     */
    public static void insertTableForXFSJ(XWPFDocument document,List<XWPFTable> tables, Map<String, Object> datamap){
        XWPFTable table1 = tables.get(0);
        XWPFTable table2 = tables.get(1);
        if(datamap.get("zrztxx")!=null){
            //所有责任主体信息
            List<Map<String,Object>> zrztxxList = (List<Map<String,Object>>) datamap.get("zrztxx");
            //设计单位
            List<Map<String,Object>> sjdwList = new ArrayList<>();
            //施工单位
            List<Map<String,Object>> sgdwList = new ArrayList<>();
            //监理单位
            List<Map<String,Object>> jldwList = new ArrayList<>();
            for(int i=0;i<zrztxxList.size();i++){
                String corpRoleNum = (String)zrztxxList.get(i).get("CORP_ROLE_NUM");
                if("2".equals(corpRoleNum)){
                    sjdwList.add(zrztxxList.get(i));
                }else if("3".equals(corpRoleNum)){
                    sgdwList.add(zrztxxList.get(i));
                }else if("4".equals(corpRoleNum)){
                    jldwList.add(zrztxxList.get(i));
                }
            }

            //已插入行数
            int insertedRowCount = 0;
            //动态表格起始行
            int beginRowNumber = 5;
            if(sjdwList.size()>1){
                int rowNumber = beginRowNumber + insertedRowCount;
                for(int i = 0; i <sjdwList.size()-1;i++){
                    table1.addRow(table1.getRow(rowNumber),beginRowNumber+insertedRowCount+1);
                    insertedRowCount++;
                }
            }
            if(sgdwList.size()>5){
                int rowNumber = beginRowNumber + insertedRowCount;
                for(int i = 0;i<sgdwList.size()-5;i++){
                    table1.addRow(table1.getRow(rowNumber),beginRowNumber+insertedRowCount+6);
                    insertedRowCount++;
                }
            }
            if(jldwList.size()>1){
                int rowNumber = beginRowNumber + insertedRowCount+5;
                for(int i = 0; i <jldwList.size()-1;i++){
                    table1.addRow(table1.getRow(rowNumber),beginRowNumber+insertedRowCount+6);
                    insertedRowCount++;
                }
            }
        }
        if(datamap.get("dtjzwxx")!=null){
            //已插入行数
            int insertedRowCount = 0;
            //动态表格起始行
            int beginRowNumber = 2;
            List<Map<String,Object>> dtjzwxxList = (List<Map<String,Object>>) datamap.get("dtjzwxx");
            if(dtjzwxxList.size()>4){
                for (int i=0;i< dtjzwxxList.size()-4;i++){
                    int rowNumber = beginRowNumber + insertedRowCount;
                    table2.addRow(table2.getRow(rowNumber),beginRowNumber+insertedRowCount+1);
                    insertedRowCount++;
                }
            }

        }
        //输出扩充表格的临时文件
        exportTemp(document);
    }
    /**
     * 消防设计合并单元格
     * @param document
     * @param tables
     * @param datamap
     */
    private static void mergeTableForXFSJ(XWPFDocument document,List<XWPFTable> tables, Map<String, Object> datamap,String outPutUrl){

        try {
            //使用临时文件合并单元格并赋值
            XWPFDocument tempDocument = new XWPFDocument(POIXMLDocument.openPackage(tempUrl));
            List<XWPFTable> tempTables = tempDocument.getTables();
            XWPFTable table1 = tempTables.get(0);
            XWPFTable table2 = tempTables.get(1);
            if(datamap.get("zrztxx")!=null){
                //所有责任主体信息
                List<Map<String,Object>> zrztxxList = (List<Map<String,Object>>) datamap.get("zrztxx");
                //设计单位
                List<Map<String,Object>> sjdwList = new ArrayList<>();
                //施工单位
                List<Map<String,Object>> sgdwList = new ArrayList<>();
                //监理单位
                List<Map<String,Object>> jldwList = new ArrayList<>();
                for(int i=0;i<zrztxxList.size();i++){
                    String corpRoleNum = (String)zrztxxList.get(i).get("CORP_ROLE_NUM");
                    if("2".equals(corpRoleNum)){
                        sjdwList.add(zrztxxList.get(i));
                    }else if("3".equals(corpRoleNum)){
                        sgdwList.add(zrztxxList.get(i));
                    }else if("4".equals(corpRoleNum)){
                        jldwList.add(zrztxxList.get(i));
                    }
                }

                String value ;
                //已插入行数
                int insertedRowCount = 0;
                //动态表格起始行
                int beginRowNumber = 5;
                if(sjdwList.size()>0){
                    int rowNumber = beginRowNumber + insertedRowCount;
                    //合并单元格起始位置
                    if(sjdwList.size()>1){
                        XWPFTableCell cell = table1.getRow(rowNumber).getCell(0);
                        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                    }
                    for(int i = 0; i <sjdwList.size();i++){
                        if(i>0){
                            //接续合并单元格
                            XWPFTableCell cell2 = table1.getRow(rowNumber+i).getCell(0);
                            cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                            insertedRowCount++;
                        }
                        //填充表格
                        Map<String,Object> valueMap = sjdwList.get(i);
                        fillXFSJCell(table1,rowNumber+i,valueMap);
                    }
                    beginRowNumber++;
                }
                if(sgdwList.size()>0){
                    //模板比较特殊，超过5条才合并单元格
                    if(sgdwList.size()>5){
                        int rowNumber = beginRowNumber + insertedRowCount;
                        //合并单元格起始位置
                        XWPFTableCell cell = table1.getRow(rowNumber).getCell(0);
                        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                        for(int i = 0;i<sgdwList.size();i++){
                            if(i>4){
                                XWPFTableCell cell2 = table1.getRow(rowNumber+i).getCell(0);
                                cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                                insertedRowCount++;
                            }
                            Map<String,Object> valueMap = sgdwList.get(i);
                            fillXFSJCell(table1,rowNumber+i,valueMap);
                        }
                    }else{
                        int rowNumber = beginRowNumber + insertedRowCount;
                        for(int i = 0;i<sgdwList.size();i++){
                            Map<String,Object> valueMap = sgdwList.get(i);
                            fillXFSJCell(table1,rowNumber+i,valueMap);
                        }
                    }
                    beginRowNumber+=5;
                }

                if(jldwList.size()>0){
                    int rowNumber = beginRowNumber + insertedRowCount;
                    if(jldwList.size()>1){
                        XWPFTableCell cell = table1.getRow(rowNumber).getCell(0);
                        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                    }
                    for(int i = 0; i <jldwList.size();i++){
                        if(i>0){
                            XWPFTableCell cell2 = table1.getRow(rowNumber+i).getCell(0);
                            cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                            insertedRowCount++;
                        }
                        Map<String,Object> valueMap = jldwList.get(i);
                        fillXFSJCell(table1,rowNumber+i,valueMap);
                    }
                    beginRowNumber++;
                }
            }
            if(datamap.get("dtjzwxx")!=null){
                //已插入行数
                int insertedRowCount = 0;
                //动态表格起始行
                int beginRowNumber = 2;
                List<Map<String,Object>> dtjzwxxList = (List<Map<String,Object>>) datamap.get("dtjzwxx");
                for (int i=0;i< dtjzwxxList.size();i++){
                    int rowNumber = beginRowNumber + insertedRowCount;
                    Map<String,Object> valueMap = dtjzwxxList.get(i);
                    fillXFSJDtjzwxxCell(table2,rowNumber+i,valueMap);
                }
            }
            //输出
            File outFile = new File(outPutUrl);
            FileOutputStream outStream = new FileOutputStream(outFile);
            tempDocument.write(outStream);
            outStream.close();
        }catch (Exception e ){
            log.error(e.getMessage());
        }


    }

    /**
     * 填充消防设计责任主体信息单元格
     * @param table
     * @param pos
     * @param valueMap
     */
    private static void fillXFSJCell(XWPFTable table,int pos,Map<String,Object> valueMap){
        XWPFTableCell tempCell = table.getRow(pos).getCell(0);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(1),
                handleEmpty(valueMap.get("CORP_NAME")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(2),
                handleEmpty(valueMap.get("CORP_LEVEL")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(3),
                handleEmpty(valueMap.get("LEGAL_REPRESENT")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(4),
                handleEmpty(valueMap.get("PERSON_NAME")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(5),
                handleEmpty(valueMap.get("PERSON_PHONE")),tempCell);
    }
    /**
     * 填充消防设计责任主体信息单元格
     * @param table
     * @param pos
     * @param valueMap
     */
    private static void fillXFSJDtjzwxxCell(XWPFTable table,int pos,Map<String,Object> valueMap){
        XWPFTableCell tempCell = table.getRow(pos).getCell(0);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(0),
                handleEmpty(valueMap.get("SUB_PRJ_NAME")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(1),
                handleEmpty(valueMap.get("FC_STRUCTURE_TYPE_NUM")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(2),
                handleEmpty(valueMap.get("REFRACTORY_LEVEL_NUM")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(3),
                handleEmpty(valueMap.get("FLOOR_COUNT")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(4),
                handleEmpty(valueMap.get("BOTTOM_FLOOR_COUNT")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(5),
                handleEmpty(valueMap.get("BUILD_HEIGHT")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(6),
                handleEmpty(valueMap.get("BUILD_AREA")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(7),
                handleEmpty(valueMap.get("FLOOR_BUILD_AREA")),tempCell);
        genParagraphForCellByTempRow(table,table.getRow(pos).getCell(8),
                handleEmpty(valueMap.get("BOTTOM_FLOOR_BUILD_AREA")),tempCell);

    }
    /**
     * 输出临时扩充表格文件
     * @param document
     */
    private static void exportTemp(XWPFDocument document){
        try {
            File file = new File(tempUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        }catch (Exception e ){
            log.error(e.getMessage());
        }
    }
}