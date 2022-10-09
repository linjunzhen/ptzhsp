/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.RelatedGenDao;
import net.evecom.platform.zzhy.service.RelatedGenService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年9月19日 下午3:12:31
 */
@Service("relatedGenService")
public class RelatedGenServiceImpl extends BaseServiceImpl implements RelatedGenService {
    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedGenServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private RelatedGenDao dao;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     * 描述 汽车租赁经营许可申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午5:26:27
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genCarRental(Map<String, Object> param, String filepathString, String destpathString){
        WordReplaceUtil.replaceWord(param, filepathString, destpathString);
    }

    /**
     * 
     * 描述 道路货物运输经营申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genRftApply(Map<String, Object> param, String filepathString, String destpathString){
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> purchaseList = null;
        List<Map<String, Object>> purchasePlanList = null;
        List<Map<String, Object>> currList = null;
        List<Map<String, Object>> employeesList = null;

        FileOutputStream fopts = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filepathString);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                setRftParams(param);
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                purchaseList = (List<Map<String, Object>>) param.get("purchaseList");
                purchasePlanList = (List<Map<String, Object>>) param.get("purchasePlanList");;
                currList = (List<Map<String, Object>>) param.get("currList");;
                employeesList = (List<Map<String, Object>>) param.get("employeesList");;
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;

                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, purchaseList, "pidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, purchasePlanList, "plidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, currList, "cidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, employeesList, "eidx");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, purchaseList,
                                    "pidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, purchasePlanList,
                                    "plidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, currList,
                                    "cidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, employeesList,
                                    "eidx");
                            WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }

            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
    /**
     * 
     * 描述 道路货物运输经营申请表数据处理
     * @author Danto Huang
     * @created 2017年9月21日 上午8:46:16
     * @param param
     * @throws Exception 
     */
    private void setRftParams(Map<String,Object> param) throws Exception{
        List<Map<String, Object>> purchaseList = null;
        List<Map<String, Object>> purchasePlanList = null;
        List<Map<String, Object>> currList = null;
        List<Map<String, Object>> employeesList = null;
        Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("pidx", "");
        empty.put("plidx", "");
        empty.put("cidx", "");
        empty.put("eidx", "");
        empty.put("BRAND_MODEL", "");
        empty.put("AMOUNT", "");
        empty.put("DEAD_WEIGHT", "");
        empty.put("TECH_LEVEL", "");
        empty.put("OUT_SIZE", "");
        empty.put("TRAN_CERT", "");
        empty.put("NAME", "");
        empty.put("SEX", "");
        empty.put("AGE", "");
        empty.put("TAKE_TIME", "");
        empty.put("CERT_NO", "");
        empty.put("CERT_TYPE", "");
        if (param.get("PURCHASE_JSON") != null) {
            purchaseList = JSON.parseObject(param.get("PURCHASE_JSON").toString(),List.class);
            for(int i=0;i<purchaseList.size();i++){
                Map<String,Object> purchase = purchaseList.get(i);
                purchase.put("pidx", String.valueOf(i+1));
            }
            param.put("purchaseList", purchaseList);
        }else{
            purchaseList = new ArrayList<Map<String,Object>>();
            purchaseList.add(empty);
            param.put("purchaseList", purchaseList);
        }
        if (param.get("PURCHASE_PLAN_JSON") != null) {
            purchasePlanList = JSON.parseObject(param.get("PURCHASE_PLAN_JSON").toString(),List.class);
            for(int i=0;i<purchasePlanList.size();i++){
                Map<String,Object> purchase = purchasePlanList.get(i);
                purchase.put("plidx", String.valueOf(i+1));
            }
            param.put("purchasePlanList", purchasePlanList);
        }else{
            purchasePlanList = new ArrayList<Map<String,Object>>();
            purchasePlanList.add(empty);
            param.put("purchasePlanList", purchasePlanList);
        }
        if (param.get("CURR_JSON") != null) {
            currList = JSON.parseObject(param.get("CURR_JSON").toString(), List.class);
            for(int i=0;i<currList.size();i++){
                Map<String,Object> curr = currList.get(i);
                curr.put("cidx", String.valueOf(i+1));
            }
            param.put("currList", currList);
        }else{
            currList = new ArrayList<Map<String,Object>>();
            currList.add(empty);
            param.put("currList", currList);
        }
        if (param.get("PLAN_EMPLOYEES") != null) {
            employeesList = JSON.parseObject(param.get("PLAN_EMPLOYEES").toString(),List.class);
            for(int i=0;i<employeesList.size();i++){
                Map<String,Object> employees = employeesList.get(i);
                employees.put("eidx", String.valueOf(i+1));
                String sex = null;
                if(employees.get("SEX").equals("1")){
                    sex = "男";
                }else{
                    sex = "女";
                }
                employees.put("SEX", sex);
            }
            param.put("employeesList", employeesList);
        }else{
            employeesList = new ArrayList<Map<String,Object>>();
            employeesList.add(empty);
            param.put("employeesList", employeesList);
        }
        String applyContent = param.get("APPLY_CONTENT").toString();
        param.put("A01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("A02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("A03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("C01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("C02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("C03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(applyContent.contains("01"))
            param.put("A01", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        if(applyContent.contains("02"))
            param.put("A02", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        if(applyContent.contains("03"))
            param.put("A03", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        String currContent = param.get("CURR_CONTENT")==null?"" : param.get("CURR_CONTENT").toString();
        if(currContent.contains("01"))
            param.put("C01", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        if(currContent.contains("02"))
            param.put("C02", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        if(currContent.contains("03"))
            param.put("C03", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
    }
    /**
     * 
     * 描述 道路危险货物运输经营申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genRdgtApply(Map<String, Object> param, String filepathString, String destpathString){
        
    }
    /**
     * 描述
     * @author Faker Li
     * @created 2017年9月20日 下午5:32:43
     * @param relatedMater
     * @param fileRullPath
     * @param string
     */
    @Override
    public void genJdcjssysRental(Map<String, Object> param,
            String filepathString, String destpathString) {
        param = reputData(param);
        CustomXWPFDocument doc = generateJdcjssysWord(param, filepathString);
        FileOutputStream fopts = null;
        try {
            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
    /**
     * 
     */
    private Map<String, Object> reputData(Map<String, Object> relatedMater) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.putAll(relatedMater);
        for (Map.Entry<String, Object> entry : relatedMater.entrySet()) {  
            if(entry.getKey().indexOf("CGJXYJB_")>-1
                     ||entry.getKey().indexOf("BGSZ_")>-1){
                result.remove(entry.getKey());
             }
            if(entry.getKey().indexOf("SZBZ_")>-1&&entry.getKey().indexOf("BGSZBZ_")<0){
                result.remove(entry.getKey());
            }
            if(entry.getKey().indexOf("GWZZ")>-1&&entry.getKey().length()==5){
                result.remove(entry.getKey());
            }
            if(entry.getKey().indexOf("GLZZ")>-1&&entry.getKey().length()==5){
                result.remove(entry.getKey());
            }
         }
        result.put("FRZSH", result.get("XLZSH"));
        for (Map.Entry<String, Object> entry : relatedMater.entrySet()) {  
           if(entry.getKey().indexOf("GWZZ")>-1||entry.getKey().indexOf("GLZZ")>-1
                    ||entry.getKey().indexOf("CGJXYJB_")>-1
                    ||entry.getKey().indexOf("SZBZ_")>-1
                    ||entry.getKey().indexOf("BGSZ_")>-1){
                if(entry.getValue()!=null){
                    String v = (String)entry.getValue();
                    if(v.equals("1")){
                        result.put(entry.getKey()+"_1", "√");
                        result.put(entry.getKey()+"_2", "");
                    }else if(v.equals("0")){
                        result.put(entry.getKey()+"_1", "");
                        result.put(entry.getKey()+"_2", "√");
                    }else{
                        result.put(entry.getKey()+"_1", "");
                        result.put(entry.getKey()+"_2", "");
                    }
                }
                
            }
          
        } 
        String XB = (String)relatedMater.get("XB");
        String newXb = dictionaryService.getDicNames("sex", XB);
        result.put("XB", newXb);
        String MZ = (String)relatedMater.get("MZ");
        String newMz = dictionaryService.getDicNames("nation", MZ);
        result.put("MZ", newMz);
        String WHCD = (String)relatedMater.get("WHCD");
        String newWhcd = dictionaryService.getDicNames("degree", WHCD);
        result.put("WHCD", newWhcd);
        
        String JLCD_DMZL = (String)relatedMater.get("JLCD_DMZL");
        result.put("JLCDA", "□");
        result.put("JLCDB", "□");
        result.put("JLCDC", "□");
        result.put("JLCDD", "□");
        if(JLCD_DMZL.equals("1")){
            result.put("JLCDA", "☑");
        }else if(JLCD_DMZL.equals("2")){
            result.put("JLCDB", "☑");
        }else if(JLCD_DMZL.equals("3")){
            result.put("JLCDC", "☑");
        }else if(JLCD_DMZL.equals("4")){
            result.put("JLCDD", "☑");
        }
        String JLCD_QTSZ = (String)relatedMater.get("JLCD_QTSZ");
        result.put("JLCDQA", "□");
        result.put("JLCDQB", "□");
        if(JLCD_QTSZ!=null){
            if(JLCD_QTSZ.indexOf("1")>-1){
                result.put("JLCDQA", "☑");
            }else if(JLCD_QTSZ.indexOf("2")>-1){
                result.put("JLCDQB", "☑");
            }
        }
        setJsonData(result);
        return result;
    }
    /**
     * 描述
     * @author Faker Li
     * @created 2017年9月20日 下午6:13:28
     * @param result
     */
    public void setJsonData(Map<String, Object> result) {
        Object ttsJson = result.get("THEORETICALINSTRUCTOR_JSON");
        if (ttsJson != null) {
            List<Map<String, Object>> ttsList = JSON.parseObject((String) ttsJson, List.class);
            for (int i = 0; i < ttsList.size(); i++) {
                ttsList.get(i).put("LLJYXH", i+1+"");
                ttsList.get(i).put("$JL$", ttsList.get(i).get("JL"));
                ttsList.get(i).remove("JL");
            }
            result.put("ttsList", ttsList);
        }
        Object practicalteacherJson = result.get("PRACTICALTEACHER_JSON");
        if (practicalteacherJson != null) {
            List<Map<String, Object>> practicalteacherList = 
                    JSON.parseObject((String) practicalteacherJson, List.class);
            for (int i = 0; i < practicalteacherList.size(); i++) {
                practicalteacherList.get(i).put("XCJYXH", i+1+"");
            }
            result.put("practicalteacherList", practicalteacherList);
        }
        Object administrativestaffJson = result.get("ADMINISTRATIVESTAFF_JSON");
        if (administrativestaffJson != null) {
            List<Map<String, Object>> administrativestaffList = 
                    JSON.parseObject((String) administrativestaffJson, List.class);
            for (int i = 0; i < administrativestaffList.size(); i++) {
                administrativestaffList.get(i).put("XZRYXH", i+1+"");
            }
            result.put("administrativestaffList", administrativestaffList);
        }
        Object coachvehicleJson = result.get("COACHVEHICLE_JSON");
        if (coachvehicleJson != null) {
            List<Map<String, Object>> coachvehicleList = JSON.parseObject((String) coachvehicleJson, List.class);
            for (int i = 0; i < coachvehicleList.size(); i++) {
                coachvehicleList.get(i).put("JLCLXH", i+1+"");
            }
            result.put("coachvehicleList", coachvehicleList);
        }
        
        String DEALER_PHOTO = result.get("DEALER_PHOTO") == null ? "" : result.get("DEALER_PHOTO").toString();
        if(StringUtils.isNotEmpty(DEALER_PHOTO)){
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath") + DEALER_PHOTO;
            Map map = new HashMap();
            map.put("content", image2byte(attachFileFolderPath));
            map.put("width","115");
            map.put("height","170");
            String fileType = FileUtil.getFileExtension(DEALER_PHOTO);
            map.put("type",fileType);
            result.put("DEALER_PHOTO", map);
        }
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2017年9月21日 上午9:05:07
     * @param path
     * @return
     */
    public byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
          input = new FileImageInputStream(new File(path));
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          byte[] buf = new byte[1024];
          int numBytesRead = 0;
          while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
          }
          data = output.toByteArray();
          output.close();
          input.close();
        }catch (FileNotFoundException ex1) {
         
        }catch (IOException ex1) {
         
        }
        return data;
      }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2017年9月20日 下午5:37:45
     * @param param
     * @param template
     * @return
     */
    private CustomXWPFDocument generateJdcjssysWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                List<Map<String, Object>> ttsList = (List<Map<String, Object>>) param.get("ttsList");
                List<Map<String, Object>> practicalteacherList = 
                        (List<Map<String, Object>>) param.get("practicalteacherList");
                List<Map<String, Object>> administrativestaffList =
                        (List<Map<String, Object>>) param.get("administrativestaffList");
                List<Map<String, Object>> coachvehicleList = 
                        (List<Map<String, Object>>) param.get("coachvehicleList");
                //List<XWPFTable> tables = doc.getTables();
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;
                    
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, ttsList, "LLJYXH");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, practicalteacherList, "XCJYXH");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, administrativestaffList, "XZRYXH");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, coachvehicleList, "JLCLXH");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, ttsList,
                                    "LLJYXH");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, practicalteacherList,
                                    "XCJYXH");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, administrativestaffList,
                                    "XZRYXH");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, coachvehicleList,
                                    "JLCLXH");
                            WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            
        }
        return doc;
    }
    /**
     * 
     * 描述 道路货物运输站经营申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     */
    public void genDLLKYSZApply(Map<String, Object> param, String filepathString, String destpathString){
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> personInfoList = null;

        FileOutputStream fopts = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filepathString);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                setDLLKYSZParams(param);
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                personInfoList = (List<Map<String, Object>>) param.get("personInfoList");;
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;

                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, personInfoList, "eidx");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, personInfoList,
                                    "eidx");
                            WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }

            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
    /**
     * 
     * 描述 道路货物运输站经营申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     * @throws Exception 
     */
    private void setDLLKYSZParams(Map<String,Object> param) throws Exception{
        String waitingHall = param.get("WAITING_HALL_AREA").toString();
        param.put("$WAITING_HALL_AREA$", waitingHall);
        param.remove("WAITING_HALL_AREA");
        List<Map<String, Object>> personInfoList = null;
        Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("eidx", "");
        empty.put("NAME", "");
        empty.put("SEX", "");
        empty.put("AGE", "");
        empty.put("POST", "");
        empty.put("JOB", "");
        empty.put("PROFESSIONALNUM", "");
        if (param.get("PERSON_INFOMATIONS") != null) {
            personInfoList = JSON.parseObject(param.get("PERSON_INFOMATIONS").toString(),List.class);
            for(int i=0;i<personInfoList.size();i++){
                Map<String,Object> employees = personInfoList.get(i);
                employees.put("eidx", String.valueOf(i+1));
                String sex = null;
                if(employees.get("SEX").equals("1")){
                    sex = "男";
                }else{
                    sex = "女";
                }
                employees.put("SEX", sex);
            }
            param.put("personInfoList", personInfoList);
        }else{
            personInfoList = new ArrayList<Map<String,Object>>();
            personInfoList.add(empty);
            param.put("personInfoList", personInfoList);
        }
        String BASICAL_EQUIPMENT = param.get("BASICAL_EQUIPMENT") == null ? "" : param.get("BASICAL_EQUIPMENT")
                .toString();
        param.put("BE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE5", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE6", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE7", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE8", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("BE9", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        String BE[] = null;
        if(StringUtils.isNotEmpty(BASICAL_EQUIPMENT)) {
            BE = BASICAL_EQUIPMENT.split(",");
            for(int i=0;i<BE.length;i++) {
                param.put("BE" + (Integer.parseInt(BE[i]) + 1), WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }
        }
        
        //设置智能系统设备
        String INTELLIGENT_EQUIPMENT = param.get("INTELLIGENT_EQUIPMENT") == null ? "" : 
            param.get("INTELLIGENT_EQUIPMENT").toString();
        param.put("IE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("IE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("IE3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("IE4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        String IE[] = null;
        if(StringUtils.isNotEmpty(INTELLIGENT_EQUIPMENT)) {
            IE = INTELLIGENT_EQUIPMENT.split(",");
            for(int i=0;i<IE.length;i++) {
                param.put("IE" + (Integer.parseInt(IE[i]) + 1), WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }
        }
    }
    
    /**
     * 
     * 描述 摩托车维修业户经营许可登记申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     */
    @Override
    public void genMBRAPPLYApply(Map<String, Object> param, String filepathString, String destpathString){
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> leaderList = null;
        List<Map<String, Object>> qaPersonList = null;
        List<Map<String, Object>> staffJson = null;
        List<Map<String, Object>> deviceJson = null;

        FileOutputStream fopts = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filepathString);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                setMBRAPPLYParams(param);
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                leaderList = (List<Map<String, Object>>) param.get("leaderList");;
                qaPersonList = (List<Map<String, Object>>) param.get("qaPersonList");;
                staffJson = (List<Map<String, Object>>) param.get("staffJson");;
                deviceJson = (List<Map<String, Object>>) param.get("deviceJson");;
                Iterator<XWPFTable> it = doc.getTablesIterator();
                int i = 0;
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;
                    
                    
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, leaderList, "lidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, qaPersonList, "qidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, staffJson, "sidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, deviceJson, "eidx");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, leaderList,
                                    "lidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, qaPersonList,
                                    "qidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, staffJson,
                                    "sidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, deviceJson,
                                    "eidx");
                            WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }

            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
    /**
     * 
     * 描述 摩托车维修业户经营许可登记申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     * @throws Exception 
     */
    private void setMBRAPPLYParams(Map<String,Object> param) throws Exception{
        List<Map<String, Object>> LEADER_JSON = null;
        List<Map<String, Object>> QAPERSON_JSON = null;
        List<Map<String, Object>> STAFF_JSON = null;
        List<Map<String, Object>> DEVICE_JSON = null;
        Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("lidx", "");
        empty.put("leaderName", "");
        empty.put("leaderPhone", "");
        empty.put("leaderMobile", "");
        
        empty.put("qidx", "");
        empty.put("qapersonName", "");
        empty.put("qapersonPhone", "");
        empty.put("qapersonMobile", "");
        
        empty.put("sidx", "");
        empty.put("staffName", "");
        empty.put("staffSex", "");
        empty.put("staffBirthDate", "");
        empty.put("staffSpecialty", "");
        empty.put("staffJobtitle", "");
        empty.put("staffWorktype", "");
        empty.put("staffPosition", "");
        empty.put("staffRemark", "");
        
        empty.put("eidx", "");
        empty.put("devName", "");
        empty.put("amount", "");
        empty.put("modelNum", "");
        empty.put("produceDate", "");
        empty.put("verifyDate", "");
        
      //是否特约维修
        String IS_SPECIALSERVICE = param.get("IS_SPECIALSERVICE") == null ? "" : param.get("IS_SPECIALSERVICE")
                .toString();
        if("1".equals(IS_SPECIALSERVICE)) {
            param.put("IS_SPECIALSERVICE", "是");//选中
        }else {
            param.put("IS_SPECIALSERVICE", "否");//选中
        }
        //是否特约维修
        String IS_RESCUESERVICE = param.get("IS_RESCUESERVICE") == null ? "" : param.get("IS_RESCUESERVICE")
                .toString();
        if("1".equals(IS_RESCUESERVICE)) {
            param.put("IS_RESCUESERVICE", "是");//选中
        }else {
            param.put("IS_RESCUESERVICE", "否");//选中
        }
        //是否连锁经营
        String IS_CHAINOPR = param.get("IS_CHAINOPR") == null ? "" : param.get("IS_SPECIALSERVICE")
                .toString();
        if("1".equals(IS_CHAINOPR)) {
            param.put("IS_CHAINOPR", "是");//选中
        }else {
            param.put("IS_CHAINOPR", "否");//选中
        }
        
        //质量检查人员 两个勾选框
        String SELFCHECKG = param.get("SELFCHECKG") == null ? "" : param.get("SELFCHECKG")
                .toString();
        param.put("SG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("SG0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if("0".equals(SELFCHECKG)) {
            param.put("SG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
        }else {
            param.put("SG0", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
        }
        
        //维修技术人员 两个勾选框
        String SELFCHECKJ = param.get("SELFCHECKJ") == null ? "" : param.get("SELFCHECKJ")
                .toString();
        param.put("SJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        param.put("SJ0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if("1".equals(SELFCHECKJ)) {
            param.put("SJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
        }else {
            param.put("SJ0", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
        }
        //number类型转String
        param.put("LABOR_HOURPRICE",param.get("LABOR_HOURPRICE").toString());
        param.put("IS_SPECIALSERVICE",param.get("IS_SPECIALSERVICE").toString());
        param.put("IS_RESCUESERVICE",param.get("IS_RESCUESERVICE").toString());
        param.put("RESCUE_CARNUM",param.get("RESCUE_CARNUM").toString());
        param.put("IS_CHAINOPR",param.get("IS_CHAINOPR").toString());
        param.put("TOTAL_AREA",param.get("TOTAL_AREA").toString());
        param.put("RECEPTIONROOM_AREA",param.get("RECEPTIONROOM_AREA").toString());
        param.put("FACTORY_AREA",param.get("FACTORY_AREA").toString());
        param.put("PARKINGLOT_AREA",param.get("PARKINGLOT_AREA").toString());
        param.put("QAPERSON_COUNT",param.get("QAPERSON_COUNT").toString());
        param.put("MAINTAINER_COUNT",param.get("MAINTAINER_COUNT").toString());
        param.put("RESTSTAFF_COUNT",param.get("RESTSTAFF_COUNT").toString());
        param.put("STAFF_TOTALCOUNT",param.get("STAFF_TOTALCOUNT").toString());
        setMBRAPPLYData(param,empty,LEADER_JSON,QAPERSON_JSON,STAFF_JSON,DEVICE_JSON);
    }

    /**
     *
     * 描述 摩托车维修业户经营许可登记申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     * @throws Exception 
     */
    private void setMBRAPPLYData(Map<String, Object> param, Map<String, Object> empty,
            List<Map<String, Object>> LEADER_JSON, List<Map<String, Object>> QAPERSON_JSON,
            List<Map<String, Object>> STAFF_JSON, List<Map<String, Object>> DEVICE_JSON) throws Exception {

        String bankAccount = param.get("COMPANY_BANK").toString();
        param.put("$COMPANY_BANK$", bankAccount);
        param.remove("COMPANY_BANK");
        String signDate = param.get("CREATE_TIME").toString().substring(0,10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        param.put("SIGN_DATE", (new SimpleDateFormat("yyyy年mm月dd日")).format(formatter.parse(signDate)));
        //业户管理负责人
        if (param.get("LEADER_JSON") != null) {
            LEADER_JSON = JSON.parseObject(param.get("LEADER_JSON").toString(),List.class);
            for(int i=0;i<LEADER_JSON.size();i++){
                Map<String,Object> employees = LEADER_JSON.get(i);
                employees.put("lidx", String.valueOf(i+1));
            }
            param.put("leaderList", LEADER_JSON);
        }else{
            LEADER_JSON = new ArrayList<Map<String,Object>>();
            LEADER_JSON.add(empty);
            param.put("leaderList", LEADER_JSON);
        }
        //质量检验人员
        if (param.get("QAPERSON_JSON") != null) {
            QAPERSON_JSON = JSON.parseObject(param.get("QAPERSON_JSON").toString(),List.class);
            for(int i=0;i<QAPERSON_JSON.size();i++){
                Map<String,Object> employees = QAPERSON_JSON.get(i);
                employees.put("qidx", String.valueOf(i+1));
            }
            param.put("qaPersonList", QAPERSON_JSON);
        }else{
            QAPERSON_JSON = new ArrayList<Map<String,Object>>();
            QAPERSON_JSON.add(empty);
            param.put("qaPersonList", QAPERSON_JSON);
        }
        //从业人员配备一览表
        if (param.get("STAFF_JSON") != null) {
            STAFF_JSON = JSON.parseObject(param.get("STAFF_JSON").toString(),List.class);
            for(int i=0;i<STAFF_JSON.size();i++){
                Map<String,Object> employees = STAFF_JSON.get(i);
                employees.put("sidx", String.valueOf(i+1));
                String sex = null;
                if("1".equals(employees.get("staffSex"))){
                    sex = "男";
                }else{
                    sex = "女";
                }
                employees.put("staffSex", sex);
                String staffEdu = null;
                if(employees.get("staffEdu").equals("1")){
                    staffEdu = "研究生";
                }else if(employees.get("staffEdu").equals("2")){
                    staffEdu = "大学本科";
                }else if("3".equals(employees.get("staffEdu"))) {
                    staffEdu = "大学专科";
                }else if("4".equals(employees.get("staffEdu"))) {
                    staffEdu = "中专";
                }else if("5".equals(employees.get("staffEdu"))) {
                    staffEdu = "技校";
                }else if("6".equals(employees.get("staffEdu"))) {
                    staffEdu = "高中";
                }else if("7".equals(employees.get("staffEdu"))) {
                    staffEdu = "初中";
                }else if("8".equals(employees.get("staffEdu"))) {
                    staffEdu = "小学";
                }else if("9".equals(employees.get("staffEdu"))) {
                    staffEdu = "文盲";
                }
                employees.put("staffEdu", staffEdu);
            }
            param.put("staffJson", STAFF_JSON);
        }else{
            STAFF_JSON = new ArrayList<Map<String,Object>>();
            STAFF_JSON.add(empty);
            param.put("staffJson", STAFF_JSON);
        }
        //设备条件
        if (param.get("DEVICE_JSON") != null) {
            DEVICE_JSON = JSON.parseObject(param.get("DEVICE_JSON").toString(),List.class);
            for(int i=0;i<DEVICE_JSON.size();i++){
                Map<String,Object> employees = DEVICE_JSON.get(i);
                employees.put("eidx", String.valueOf(i+1));
                //维修技术人员 两个勾选框
                String isOwned = employees.get("isOwned") == null ? "" : employees.get("isOwned")
                        .toString();
                //TODO
                employees.put("ZB1", "");// 未选中
                employees.put("ZB2", "");// 未选中
                if("0".equals(isOwned)) {
                    employees.put("ZB1", "√");//选中
                }else if("1".equals(isOwned)){
                    employees.put("ZB2", "√");//选中
                }
            }
            param.put("deviceJson", DEVICE_JSON);
        }else{
            DEVICE_JSON = new ArrayList<Map<String,Object>>();
            DEVICE_JSON.add(empty);
            param.put("deviceJson", DEVICE_JSON);
        }
    }
    
    /**
    *
    * 描述 道路旅客运输班线经营申请表数据处理
    * @author Curtis Chen
    * @created 2017年9月21日 14:45:16
    * @param param
    * @throws Exception
    */
    @Override
    public void genRPTMApply(Map<String, Object> param, String filepathString, String destpathString) {
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> conditionList = null;
        List<Map<String, Object>> employeesList = null;
        FileOutputStream fopts = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filepathString);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                setRPTMAPPLYParams(param);
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                conditionList = (List<Map<String, Object>>) param.get("conditionList");
                employeesList = (List<Map<String, Object>>) param.get("employeesList");
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, conditionList, "kcindex");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, employeesList, "epxh");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, conditionList,"kcindex");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, employeesList,"epxh");
                            WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
    *
    * 描述 摩托车维修业户经营许可登记申请表数据处理
    * @author Curtis Chen
    * @created 2017年9月21日 14:45:16
    * @param param
    * @throws Exception
    */
    private void setRPTMAPPLYParams(Map<String, Object> param) throws Exception{
        //已获许可经营范围
        String BUSS_SCOPE = param.get("BUSS_SCOPE") == null ? "" : param.get("BUSS_SCOPE").toString();
        if(!BUSS_SCOPE.trim().equals("")){
            if(BUSS_SCOPE.contains("xn")){
                param.put("CKJYFW1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKJYFW1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(BUSS_SCOPE.contains("xj")){
                param.put("CKJYFW2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKJYFW2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(BUSS_SCOPE.contains("sj")){
                param.put("CKJYFW3", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKJYFW3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(BUSS_SCOPE.contains("shj")){
                param.put("CKJYFW4", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKJYFW4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        }
        String TRAINLINE_TYPE = param.get("TRAINLINE_TYPE") == null ? "" : param.get("TRAINLINE_TYPE").toString();
        if(!TRAINLINE_TYPE.trim().equals("")){
            if(TRAINLINE_TYPE.contains("01")){
                param.put("CKBX1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKBX1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(TRAINLINE_TYPE.contains("02")){
                param.put("CKBX2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKBX2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(TRAINLINE_TYPE.contains("03")){
                param.put("CKBX3",WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKBX3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(TRAINLINE_TYPE.contains("04")){
                param.put("CKBX4", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKBX4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        }
        String ISLETTER_INTENT = param.get("ISLETTER_INTENT") == null ? "" : param.get("ISLETTER_INTENT").toString();
            if(ISLETTER_INTENT.equals("1")){
                param.put("ABC", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("ABC", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        param.remove("ISLETTER_INTENT");
        String ISLETTER_INTENT2 = param.get("ISLETTER_INTENT2") == null ? "" : param.get("ISLETTER_INTENT2").toString();
            if(ISLETTER_INTENT2.equals("1")){
                param.put("DEF", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("DEF", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        param.remove("ISLETTER_INTENT2");
        String TRAINLINE_TYPET = param.get("TRAINLINE_TYPET") == null ? "" : param.get("TRAINLINE_TYPET").toString();
        if(!TRAINLINE_TYPET.trim().equals("")){
            if(TRAINLINE_TYPET.contains("01")){
                param.put("YYBXA", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("YYBXA", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(TRAINLINE_TYPET.contains("02")){
                param.put("YYBXB", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("YYBXB", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(TRAINLINE_TYPET.contains("03")){
                param.put("YYBXC",WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("YYBXC", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(TRAINLINE_TYPET.contains("04")){
                param.put("YYBXD", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("YYBXD", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        }
        String BUSSCLASS = param.get("BUSSCLASS") == null ? "" : param.get("BUSSCLASS").toString();
        if(!BUSSCLASS.trim().equals("")){
            if(BUSSCLASS.contains("1")){
                param.put("CKBL1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKBL1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(BUSSCLASS.contains("2")){
                param.put("CKBL2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKBL2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        }
        String MANAGEMENT_MODE = param.get("MANAGEMENT_MODE") == null ? "" : param.get("MANAGEMENT_MODE").toString();
        if(!MANAGEMENT_MODE.trim().equals("")){
            if(MANAGEMENT_MODE.contains("1")){
                param.put("CKJYA", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKJYA", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
            if(MANAGEMENT_MODE.contains("2")){
                param.put("CKJYB", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));//选中
            }else{
                param.put("CKJYB", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));//未选中
            }
        }
        //业户管理负责人
        Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("kcindex", "");
        empty.put("BRAND_MODEL", "");
        empty.put("NUM", "");
        empty.put("SEAT_NUM", "");
        empty.put("CAR_TYPELEVEL", "");
        empty.put("CAR_TECHLEVEL", "");
        empty.put("CAR_WIDTH", "");
        empty.put("ISNX", "");
        empty.put("Pindex", "");
        empty.put("USERNAME", "");
        empty.put("SEX", "");
        empty.put("AGE", "");
        empty.put("GETTIME", "");
        empty.put("QUCODE", "");
        empty.put("QUTYPE", "");
        empty.put("ISTRAACC", "");
        List<Map<String, Object>> CAR_CONDITION_JSON = null;
        List<Map<String, Object>> PLAN_EMPLOYEES = null;
        if (param.get("CAR_CONDITION_JSON") != null) {
            CAR_CONDITION_JSON = JSON.parseObject(param.get("CAR_CONDITION_JSON").toString(),List.class);
            for(int i=0;i<CAR_CONDITION_JSON.size();i++){
                Map<String,Object> employees = CAR_CONDITION_JSON.get(i);
                employees.put("kcindex", String.valueOf(i+1));
                String isnx = null;
                if("0".equals(employees.get("ISNX"))){
                    isnx = "拟购";               
                }else{
                    isnx = "现有";
                }
                employees.put("ISNX", isnx);
                employees.put("ZWSL", employees.get("SEAT_NUM"));
                employees.put("KCSL", employees.get("NUM"));
                employees.remove("NUM");
                employees.remove("SEAT_NUM");
            }
            param.put("conditionList", CAR_CONDITION_JSON);
        }else{
            CAR_CONDITION_JSON = new ArrayList<Map<String,Object>>();
            CAR_CONDITION_JSON.add(empty);
            param.put("conditionList", CAR_CONDITION_JSON);
        }
        if (param.get("PLAN_EMPLOYEES") != null) {
            PLAN_EMPLOYEES = JSON.parseObject(param.get("PLAN_EMPLOYEES").toString(),List.class);
            for(int i=0;i<PLAN_EMPLOYEES.size();i++){
                Map<String,Object> employees = PLAN_EMPLOYEES.get(i);
                employees.put("epxh", String.valueOf(i+1));
                String sex = null;
                if("1".equals(employees.get("SEX"))){
                    sex = "男";
                }else{
                    sex = "女";
                }
                employees.put("SEX", sex);
                String istraacc = null;
                if("0".equals(employees.get("ISTRAACC"))){
                    istraacc = "是";
                }else{
                    istraacc = "否";
                }
                employees.put("ISTRAACC", istraacc);
            }
            param.put("employeesList", PLAN_EMPLOYEES);
        }else{
            PLAN_EMPLOYEES = new ArrayList<Map<String,Object>>();
            PLAN_EMPLOYEES.add(empty);
            param.put("employeesList", PLAN_EMPLOYEES);
        }
    }
    
    /**
     * 
     * 描述 三类汽车维修业户经营许可登记申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genCcrApply(Map<String, Object> param, String filepathString, String destpathString){
        CustomXWPFDocument doc = null;
        FileOutputStream fopts = null;
        try {
            setCcrParams(param);
            setCcrApplyList(param);
            setCrrApplyChk(param);
            setCrrApplyChk2(param);
            setCrrApplyChk3(param);
            setCrrApplyChk4(param);
            setCrrApplyEquipment(param);
            OPCPackage pack = POIXMLDocument.openPackage(filepathString);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                dealtable(doc,param);
            }

            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if (fopts != null) {
                try {
                    fopts.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 下午5:01:40
     * @param doc
     * @param param
     */
    private void dealtable(CustomXWPFDocument doc,Map<String, Object> param){
     // 处理表格
        List<Map<String,Object>> leaderList = (List<Map<String, Object>>) param.get("leaderList");
        List<Map<String,Object>> tleaderList = (List<Map<String, Object>>) param.get("tleaderList");
        List<Map<String,Object>> qaList = (List<Map<String, Object>>) param.get("qaList");
        List<Map<String,Object>> VARIETYSRVList = (List<Map<String, Object>>) param.get("VARIETYSRVList");
        List<Map<String,Object>> QUICKSRVList = (List<Map<String, Object>>) param.get("QUICKSRVList");
        List<Map<String,Object>> ENGINESRVList = (List<Map<String, Object>>) param.get("ENGINESRVList");
        List<Map<String,Object>> CARBODYSRVList = (List<Map<String, Object>>) param.get("CARBODYSRVList");
        List<Map<String,Object>> ELECTRICSYSSRVList = (List<Map<String, Object>>) param.get("ELECTRICSYSSRVList");
        List<Map<String,Object>> VARIATORSRVList = (List<Map<String, Object>>) param.get("VARIATORSRVList");
        List<Map<String,Object>> TIRESRVList = (List<Map<String, Object>>) param.get("TIRESRVList");
        List<Map<String,Object>> TIRELOCATESRVList = (List<Map<String, Object>>) param.get("TIRELOCATESRVList");
        List<Map<String,Object>> LUBRICATESRVList = (List<Map<String, Object>>) param.get("LUBRICATESRVList");
        List<Map<String,Object>> INJECTORSRVList = (List<Map<String, Object>>) param.get("INJECTORSRVList");
        List<Map<String,Object>> ELECINJECTORSRVList = (List<Map<String, Object>>) param.get("ELECINJECTORSRVList");
        List<Map<String,Object>> CRANKSRVList = (List<Map<String, Object>>) param.get("CRANKSRVList");
        List<Map<String,Object>> CYLINDERSRVList = (List<Map<String, Object>>) param.get("CYLINDERSRVList");
        List<Map<String,Object>> RADIATORSRVList = (List<Map<String, Object>>) param.get("RADIATORSRVList");
        List<Map<String,Object>> AIRCONDSRVList = (List<Map<String, Object>>) param.get("AIRCONDSRVList");
        List<Map<String,Object>> BEAUTYSRVList = (List<Map<String, Object>>) param.get("BEAUTYSRVList");
        List<Map<String,Object>> GLASSSRVList = (List<Map<String, Object>>) param.get("GLASSSRVList");
        List<Map<String,Object>> STAFFList = (List<Map<String, Object>>) param.get("STAFFList");
        Iterator<XWPFTable> it = doc.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();
            List<XWPFTableRow> rows = table.getRows();
            XWPFTableRow oldRow = null;

            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, leaderList, "mleadertip");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, tleaderList, "tleadertip");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, qaList, "qatip");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, VARIETYSRVList, "first");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, QUICKSRVList, "second");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, ENGINESRVList, "third");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, CARBODYSRVList, "four");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, ELECTRICSYSSRVList, "five");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, VARIATORSRVList, "six");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, TIRESRVList, "seven");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, TIRELOCATESRVList, "eight");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, LUBRICATESRVList, "nine");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, INJECTORSRVList, "ten");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, ELECINJECTORSRVList, "tsen");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, CRANKSRVList, "eleven");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, CYLINDERSRVList, "twelwe");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, RADIATORSRVList, "thirty");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, AIRCONDSRVList, "fty");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, BEAUTYSRVList, "fity");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, GLASSSRVList, "sty");
            WordReplaceUtil.addTableRow2(param, table, rows, oldRow, STAFFList, "seq");
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, leaderList,
                            "mleadertip");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, tleaderList,
                            "tleadertip");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, qaList,
                            "qatip");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, VARIETYSRVList,
                            "first");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, QUICKSRVList,
                            "second");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, ENGINESRVList,
                            "third");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, CARBODYSRVList,
                            "four");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, ELECTRICSYSSRVList,
                            "five");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, VARIATORSRVList,
                            "six");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, TIRESRVList,
                            "seven");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, TIRELOCATESRVList,
                            "eight");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, LUBRICATESRVList,
                            "nine");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, INJECTORSRVList,
                            "ten");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, ELECINJECTORSRVList,
                            "tsen");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, CRANKSRVList,
                            "eleven");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, CYLINDERSRVList,
                            "twelwe");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, RADIATORSRVList,
                            "thirty");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, AIRCONDSRVList,
                            "fty");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, BEAUTYSRVList,
                            "fity");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, GLASSSRVList,
                            "sty");
                    WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, STAFFList,
                            "seq");
                    WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                }
            }
        }
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月22日 上午11:00:47
     * @param param
     * @throws Exception
     */
    private void setCcrParams(Map<String,Object> param) throws Exception{
        String bankAccount = param.get("COMPANY_BANK").toString();
        param.put("$COMPANY_BANK$", bankAccount);
        param.remove("COMPANY_BANK");
        String signDate = param.get("CREATE_TIME").toString().substring(0,10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        param.put("SIGN_DATE", (new SimpleDateFormat("yyyy年mm月dd日")).format(formatter.parse(signDate)));
        String LABOR_HOURPRICE = String.valueOf(param.get("LABOR_HOURPRICE"));
        param.put("LABOR_HOURPRICE", LABOR_HOURPRICE);
        String TOTAL_AREA = String.valueOf(param.get("TOTAL_AREA"));
        String RESCUE_CARNUM = String.valueOf(param.get("RESCUE_CARNUM"));
        param.put("RESCUE_CARNUM", RESCUE_CARNUM);
        param.put("TOTAL_AREA", TOTAL_AREA);
        String RECEPTIONROOM_AREA = String.valueOf(param.get("RECEPTIONROOM_AREA"));
        param.put("RECEPTIONROOM_AREA", RECEPTIONROOM_AREA);
        String FACTORY_AREA = String.valueOf(param.get("FACTORY_AREA"));
        param.put("FACTORY_AREA", FACTORY_AREA);
        String PARKINGLOT_AREA = String.valueOf(param.get("PARKINGLOT_AREA"));
        param.put("PARKINGLOT_AREA", PARKINGLOT_AREA);
        String STAFF_TOTALCOUNT = String.valueOf(param.get("STAFF_TOTALCOUNT"));
        param.put("STAFF_TOTALCOUNT", STAFF_TOTALCOUNT);
        String MANAGER_COUNT = String.valueOf(param.get("MANAGER_COUNT"));
        param.remove("MANAGER_COUNT");
        param.put("${MANAGER_COUNT}", MANAGER_COUNT);
        String MAINTAINER_COUNT = String.valueOf(param.get("MAINTAINER_COUNT"));
        param.remove("MAINTAINER_COUNT");
        param.put("${MAINTAINER_COUNT}", MAINTAINER_COUNT);
        String RESTSTAFF_COUNT = String.valueOf(param.get("RESTSTAFF_COUNT"));
        param.put("RESTSTAFF_COUNT", RESTSTAFF_COUNT);
        String LEADER_COUNT = String.valueOf(param.get("LEADER_COUNT"));
        param.remove("LEADER_COUNT");
        param.put("${LEADER_COUNT}", LEADER_COUNT);
        String TECHLEADER_COUNT = String.valueOf(param.get("TECHLEADER_COUNT"));
        param.put("TECHLEADER_COUNT", TECHLEADER_COUNT);
        String QAPERSON_COUNT = String.valueOf(param.get("QAPERSON_COUNT"));
        param.put("QAPERSON_COUNT", QAPERSON_COUNT);
        String RECEPTIONIST_COUNT = String.valueOf(param.get("RECEPTIONIST_COUNT"));
        param.put("RECEPTIONIST_COUNT", RECEPTIONIST_COUNT);
        String CLERK_COUNT = String.valueOf(param.get("CLERK_COUNT"));
        param.put("CLERK_COUNT", CLERK_COUNT);
        String RESTMANAGER_COUNT = String.valueOf(param.get("RESTMANAGER_COUNT"));
        param.put("RESTMANAGER_COUNT", RESTMANAGER_COUNT);
        String MECHANIC_COUNT = String.valueOf(param.get("MECHANIC_COUNT"));
        param.put("MECHANIC_COUNT", MECHANIC_COUNT);
        String EAMAINTAINER_COUNT = String.valueOf(param.get("EAMAINTAINER_COUNT"));
        param.put("EAMAINTAINER_COUNT", EAMAINTAINER_COUNT);
        String METZLER_COUNT = String.valueOf(param.get("METZLER_COUNT"));
        param.put("METZLER_COUNT", METZLER_COUNT);
        String PAINTER_COUNT = String.valueOf(param.get("PAINTER_COUNT"));
        param.put("PAINTER_COUNT", PAINTER_COUNT);
        String QCPERSON_COUNT = String.valueOf(param.get("QCPERSON_COUNT"));
        param.put("QCPERSON_COUNT", QCPERSON_COUNT);
        String RESTMAINTAINER_COUNT = String.valueOf(param.get("RESTMAINTAINER_COUNT"));
        param.put("RESTMAINTAINER_COUNT", RESTMAINTAINER_COUNT);
        
        String IS_SPECIALSERVICE = String.valueOf(param.get("IS_SPECIALSERVICE"));
        param.remove("IS_SPECIALSERVICE");
        if(IS_SPECIALSERVICE.equals("1")){
            param.put("IS_SPECIALSERVICE", "是");
        }else{
            param.put("IS_SPECIALSERVICE", "否");
        }
        String IS_RESCUESERVICE = String.valueOf(param.get("IS_RESCUESERVICE"));
        param.remove("IS_RESCUESERVICE");
        if(IS_RESCUESERVICE.equals("1")){
            param.put("IS_RESCUESERVICE", "是");
        }else{
            param.put("IS_RESCUESERVICE", "否");
        }
        String IS_CHAINOPR = String.valueOf(param.get("IS_CHAINOPR"));
        param.remove("IS_CHAINOPR");
        if(IS_CHAINOPR.equals("1")){
            param.put("IS_CHAINOPR", "是");
        }else{
            param.put("IS_CHAINOPR", "否");
        }
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @throws Exception 
     * @created 2017年9月24日 下午9:03:12
     */
    private void setCrrApplyChk(Map<String,Object> param) throws Exception{
        String SELFCHECK_WORKSAFETYB = String.valueOf(param.get("SELFCHECK_WORKSAFETYB"));
        param.remove("SELFCHECK_WORKSAFETYB");
        param.put("SELFCHECK_WORKSAFETYB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_WORKSAFETYB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_WORKSAFETYB.equals("1")){
            param.put("SELFCHECK_WORKSAFETYB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_WORKSAFETYB.equals("0")){
            param.put("SELFCHECK_WORKSAFETYB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIETYSERVICED = String.valueOf(param.get("SELFCHECK_VARIETYSERVICED"));
        param.remove("SELFCHECK_VARIETYSERVICED");
        param.put("SELFCHECK_VARIETYSERVICED1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIETYSERVICED2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIETYSERVICED.equals("1")){
            param.put("SELFCHECK_VARIETYSERVICED1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIETYSERVICED.equals("0")){
            param.put("SELFCHECK_VARIETYSERVICED2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIETYSERVICEG = String.valueOf(param.get("SELFCHECK_VARIETYSERVICEG"));
        param.remove("SELFCHECK_VARIETYSERVICEG");
        param.put("SELFCHECK_VARIETYSERVICEG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIETYSERVICEG2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIETYSERVICEG.equals("1")){
            param.put("SELFCHECK_VARIETYSERVICEG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIETYSERVICEG.equals("0")){
            param.put("SELFCHECK_VARIETYSERVICEG2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIETYSERVICEJ = String.valueOf(param.get("SELFCHECK_VARIETYSERVICEJ"));
        param.remove("SELFCHECK_VARIETYSERVICEJ");
        param.put("SELFCHECK_VARIETYSERVICEJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIETYSERVICEJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIETYSERVICEJ.equals("1")){
            param.put("SELFCHECK_VARIETYSERVICEJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIETYSERVICEJ.equals("0")){
            param.put("SELFCHECK_VARIETYSERVICEJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIETYSERVICEM = String.valueOf(param.get("SELFCHECK_VARIETYSERVICEM"));
        param.remove("SELFCHECK_VARIETYSERVICEM");
        param.put("SELFCHECK_VARIETYSERVICEM1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIETYSERVICEM2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIETYSERVICEM.equals("1")){
            param.put("SELFCHECK_VARIETYSERVICEM1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIETYSERVICEM.equals("0")){
            param.put("SELFCHECK_VARIETYSERVICEM2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_QUICKSERVICED = String.valueOf(param.get("SELFCHECK_QUICKSERVICED"));
        param.remove("SELFCHECK_QUICKSERVICED");
        param.put("SELFCHECK_QUICKSERVICED1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_QUICKSERVICED2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_QUICKSERVICED.equals("1")){
            param.put("SELFCHECK_QUICKSERVICED1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_QUICKSERVICED.equals("0")){
            param.put("SELFCHECK_QUICKSERVICED2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_QUICKSERVICEG = String.valueOf(param.get("SELFCHECK_QUICKSERVICEG"));
        param.remove("SELFCHECK_QUICKSERVICEG");
        param.put("SELFCHECK_QUICKSERVICEG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_QUICKSERVICEG2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_QUICKSERVICEG.equals("1")){
            param.put("SELFCHECK_QUICKSERVICEG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_QUICKSERVICEG.equals("0")){
            param.put("SELFCHECK_QUICKSERVICEG2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_QUICKSERVICEJ = String.valueOf(param.get("SELFCHECK_QUICKSERVICEJ"));
        param.remove("SELFCHECK_QUICKSERVICEJ");
        param.put("SELFCHECK_QUICKSERVICEJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_QUICKSERVICEJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_QUICKSERVICEJ.equals("1")){
            param.put("SELFCHECK_QUICKSERVICEJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_QUICKSERVICEJ.equals("0")){
            param.put("SELFCHECK_QUICKSERVICEJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_ENGINESRVD = String.valueOf(param.get("SELFCHECK_ENGINESRVD"));
        param.remove("SELFCHECK_ENGINESRVD");
        param.put("SELFCHECK_ENGINESRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ENGINESRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ENGINESRVD.equals("1")){
            param.put("SELFCHECK_ENGINESRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ENGINESRVD.equals("0")){
            param.put("SELFCHECK_ENGINESRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_ENGINESRVG = String.valueOf(param.get("SELFCHECK_ENGINESRVG"));
        param.remove("SELFCHECK_ENGINESRVG");
        param.put("SELFCHECK_ENGINESRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ENGINESRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ENGINESRVG.equals("1")){
            param.put("SELFCHECK_ENGINESRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ENGINESRVG.equals("0")){
            param.put("SELFCHECK_ENGINESRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 上午8:17:58
     * @param param
     * @throws Exception
     */
    private void setCrrApplyChk2(Map<String,Object> param) throws Exception{
        String SELFCHECK_ENGINESRVJ = String.valueOf(param.get("SELFCHECK_ENGINESRVJ"));
        param.remove("SELFCHECK_ENGINESRVJ");
        param.put("SELFCHECK_ENGINESRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ENGINESRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ENGINESRVJ.equals("1")){
            param.put("SELFCHECK_ENGINESRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ENGINESRVJ.equals("0")){
            param.put("SELFCHECK_ENGINESRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_ENGINESRVM = String.valueOf(param.get("SELFCHECK_ENGINESRVM"));
        param.remove("SELFCHECK_ENGINESRVM");
        param.put("SELFCHECK_ENGINESRVM1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ENGINESRVM2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ENGINESRVM.equals("1")){
            param.put("SELFCHECK_ENGINESRVM1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ENGINESRVM.equals("0")){
            param.put("SELFCHECK_ENGINESRVM2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CARBODYSRVD = String.valueOf(param.get("SELFCHECK_CARBODYSRVD"));
        param.remove("SELFCHECK_CARBODYSRVD");
        param.put("SELFCHECK_CARBODYSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CARBODYSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CARBODYSRVD.equals("1")){
            param.put("SELFCHECK_CARBODYSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CARBODYSRVD.equals("0")){
            param.put("SELFCHECK_CARBODYSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CARBODYSRVG = String.valueOf(param.get("SELFCHECK_CARBODYSRVG"));
        param.remove("SELFCHECK_CARBODYSRVG");
        param.put("SELFCHECK_CARBODYSRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CARBODYSRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CARBODYSRVG.equals("1")){
            param.put("SELFCHECK_CARBODYSRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CARBODYSRVG.equals("0")){
            param.put("SELFCHECK_CARBODYSRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CARBODYSRVJ = String.valueOf(param.get("SELFCHECK_CARBODYSRVJ"));
        param.remove("SELFCHECK_CARBODYSRVJ");
        param.put("SELFCHECK_CARBODYSRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CARBODYSRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CARBODYSRVJ.equals("1")){
            param.put("SELFCHECK_CARBODYSRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CARBODYSRVJ.equals("0")){
            param.put("SELFCHECK_CARBODYSRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CARBODYSRVM = String.valueOf(param.get("SELFCHECK_CARBODYSRVM"));
        param.remove("SELFCHECK_CARBODYSRVM");
        param.put("SELFCHECK_CARBODYSRVM1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CARBODYSRVM2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CARBODYSRVM.equals("1")){
            param.put("SELFCHECK_CARBODYSRVM1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CARBODYSRVM.equals("0")){
            param.put("SELFCHECK_CARBODYSRVM2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CARBODYSRVP = String.valueOf(param.get("SELFCHECK_CARBODYSRVP"));
        param.remove("SELFCHECK_CARBODYSRVP");
        param.put("SELFCHECK_CARBODYSRVP1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CARBODYSRVP2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CARBODYSRVP.equals("1")){
            param.put("SELFCHECK_CARBODYSRVP1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CARBODYSRVP.equals("0")){
            param.put("SELFCHECK_CARBODYSRVP2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_ELECTRICSYSSRVD = String.valueOf(param.get("SELFCHECK_ELECTRICSYSSRVD"));
        param.remove("SELFCHECK_ELECTRICSYSSRVD");
        param.put("SELFCHECK_ELECTRICSYSSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ELECTRICSYSSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ELECTRICSYSSRVD.equals("1")){
            param.put("SELFCHECK_ELECTRICSYSSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ELECTRICSYSSRVD.equals("0")){
            param.put("SELFCHECK_ELECTRICSYSSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_ELECTRICSYSSRVG = String.valueOf(param.get("SELFCHECK_ELECTRICSYSSRVG"));
        param.remove("SELFCHECK_ELECTRICSYSSRVG");
        param.put("SELFCHECK_ELECTRICSYSSRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ELECTRICSYSSRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ELECTRICSYSSRVG.equals("1")){
            param.put("SELFCHECK_ELECTRICSYSSRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ELECTRICSYSSRVG.equals("0")){
            param.put("SELFCHECK_ELECTRICSYSSRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_ELECTRICSYSSRVJ = String.valueOf(param.get("SELFCHECK_ELECTRICSYSSRVJ"));
        param.remove("SELFCHECK_ELECTRICSYSSRVJ");
        param.put("SELFCHECK_ELECTRICSYSSRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_ELECTRICSYSSRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_ELECTRICSYSSRVJ.equals("1")){
            param.put("SELFCHECK_ELECTRICSYSSRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_ELECTRICSYSSRVJ.equals("0")){
            param.put("SELFCHECK_ELECTRICSYSSRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 上午8:17:58
     * @param param
     * @throws Exception
     */
    private void setCrrApplyChk3(Map<String,Object> param) throws Exception{
        String SELFCHECK_VARIATORSRVD = String.valueOf(param.get("SELFCHECK_VARIATORSRVD"));
        param.remove("SELFCHECK_VARIATORSRVD");
        param.put("SELFCHECK_VARIATORSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIATORSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIATORSRVD.equals("1")){
            param.put("SELFCHECK_VARIATORSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIATORSRVD.equals("0")){
            param.put("SELFCHECK_VARIATORSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIATORSRVG = String.valueOf(param.get("SELFCHECK_VARIATORSRVG"));
        param.remove("SELFCHECK_VARIATORSRVG");
        param.put("SELFCHECK_VARIATORSRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIATORSRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIATORSRVG.equals("1")){
            param.put("SELFCHECK_VARIATORSRVG1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIATORSRVG.equals("0")){
            param.put("SELFCHECK_VARIATORSRVG2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIATORSRVJ = String.valueOf(param.get("SELFCHECK_VARIATORSRVJ"));
        param.remove("SELFCHECK_VARIATORSRVJ");
        param.put("SELFCHECK_VARIATORSRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIATORSRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIATORSRVJ.equals("1")){
            param.put("SELFCHECK_VARIATORSRVJ1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIATORSRVJ.equals("0")){
            param.put("SELFCHECK_VARIATORSRVJ2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_VARIATORSRVM = String.valueOf(param.get("SELFCHECK_VARIATORSRVM"));
        param.remove("SELFCHECK_VARIATORSRVM");
        param.put("SELFCHECK_VARIATORSRVM1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_VARIATORSRVM2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_VARIATORSRVM.equals("1")){
            param.put("SELFCHECK_VARIATORSRVM1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_VARIATORSRVM.equals("0")){
            param.put("SELFCHECK_VARIATORSRVM2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_TIRESRVB = String.valueOf(param.get("SELFCHECK_TIRESRVB"));
        param.remove("SELFCHECK_TIRESRVB");
        param.put("SELFCHECK_TIRESRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_TIRESRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_TIRESRVB.equals("1")){
            param.put("SELFCHECK_TIRESRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_TIRESRVB.equals("0")){
            param.put("SELFCHECK_TIRESRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_TIRELOCATESRVB = String.valueOf(param.get("SELFCHECK_TIRELOCATESRVB"));
        param.remove("SELFCHECK_TIRELOCATESRVB");
        param.put("SELFCHECK_TIRELOCATESRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_TIRELOCATESRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_TIRELOCATESRVB.equals("1")){
            param.put("SELFCHECK_TIRELOCATESRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_TIRELOCATESRVB.equals("0")){
            param.put("SELFCHECK_TIRELOCATESRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_LUBRICATESRVB = String.valueOf(param.get("SELFCHECK_LUBRICATESRVB"));
        param.remove("SELFCHECK_LUBRICATESRVB");
        param.put("SELFCHECK_LUBRICATESRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_LUBRICATESRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_LUBRICATESRVB.equals("1")){
            param.put("SELFCHECK_LUBRICATESRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_LUBRICATESRVB.equals("0")){
            param.put("SELFCHECK_LUBRICATESRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_INJECTORSRVB = String.valueOf(param.get("SELFCHECK_INJECTORSRVB"));
        param.remove("SELFCHECK_INJECTORSRVB");
        param.put("SELFCHECK_INJECTORSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_INJECTORSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_INJECTORSRVB.equals("1")){
            param.put("SELFCHECK_INJECTORSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_INJECTORSRVB.equals("0")){
            param.put("SELFCHECK_INJECTORSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CRANKSRVB = String.valueOf(param.get("SELFCHECK_CRANKSRVB"));
        param.remove("SELFCHECK_CRANKSRVB");
        param.put("SELFCHECK_CRANKSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CRANKSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CRANKSRVB.equals("1")){
            param.put("SELFCHECK_CRANKSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CRANKSRVB.equals("0")){
            param.put("SELFCHECK_CRANKSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_CYLINDERSRVB = String.valueOf(param.get("SELFCHECK_CYLINDERSRVB"));
        param.remove("SELFCHECK_CYLINDERSRVB");
        param.put("SELFCHECK_CYLINDERSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_CYLINDERSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_CYLINDERSRVB.equals("1")){
            param.put("SELFCHECK_CYLINDERSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_CYLINDERSRVB.equals("0")){
            param.put("SELFCHECK_CYLINDERSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 上午8:17:58
     * @param param
     * @throws Exception
     */
    private void setCrrApplyChk4(Map<String,Object> param) throws Exception{
        String SELFCHECK_RADIATORSRVB = String.valueOf(param.get("SELFCHECK_RADIATORSRVB"));
        param.remove("SELFCHECK_RADIATORSRVB");
        param.put("SELFCHECK_RADIATORSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_RADIATORSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_RADIATORSRVB.equals("1")){
            param.put("SELFCHECK_RADIATORSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_RADIATORSRVB.equals("0")){
            param.put("SELFCHECK_RADIATORSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_AIRCONDSRVB = String.valueOf(param.get("SELFCHECK_AIRCONDSRVB"));
        param.remove("SELFCHECK_AIRCONDSRVB");
        param.put("SELFCHECK_AIRCONDSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_AIRCONDSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_AIRCONDSRVB.equals("1")){
            param.put("SELFCHECK_AIRCONDSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_AIRCONDSRVB.equals("0")){
            param.put("SELFCHECK_AIRCONDSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_BEAUTYSRVB = String.valueOf(param.get("SELFCHECK_BEAUTYSRVB"));
        param.remove("SELFCHECK_BEAUTYSRVB");
        param.put("SELFCHECK_BEAUTYSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_BEAUTYSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_BEAUTYSRVB.equals("1")){
            param.put("SELFCHECK_BEAUTYSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_BEAUTYSRVB.equals("0")){
            param.put("SELFCHECK_BEAUTYSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_BEAUTYSRVD = String.valueOf(param.get("SELFCHECK_BEAUTYSRVD"));
        param.remove("SELFCHECK_BEAUTYSRVD");
        param.put("SELFCHECK_BEAUTYSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_BEAUTYSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_BEAUTYSRVD.equals("1")){
            param.put("SELFCHECK_BEAUTYSRVD1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_BEAUTYSRVD.equals("0")){
            param.put("SELFCHECK_BEAUTYSRVD2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        String SELFCHECK_GLASSSRVB = String.valueOf(param.get("SELFCHECK_GLASSSRVB"));
        param.remove("SELFCHECK_GLASSSRVB");
        param.put("SELFCHECK_GLASSSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("SELFCHECK_GLASSSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(SELFCHECK_GLASSSRVB.equals("1")){
            param.put("SELFCHECK_GLASSSRVB1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }else if(SELFCHECK_GLASSSRVB.equals("0")){
            param.put("SELFCHECK_GLASSSRVB2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
    }
    /**
     * 
     * 描述 
     * @author Danto Huang
     * @created 2017年9月22日 上午11:00:08
     * @param param
     */
    private void setCcrApplyList(Map<String,Object> param){
        List<Map<String, Object>> leaderList = null;
        if (param.get("LEADER_JSON") != null) {
            leaderList = JSON.parseObject(param.get("LEADER_JSON").toString(),List.class);
            for(Map<String,Object> leader : leaderList){
                leader.put("mleadertip", "业户管理负责人");
                String leaderName = leader.get("leaderName").toString();
                leader.remove("leaderName");
                leader.put("mleaderName", leaderName);
                leader.put("mphonetip", "联系电话");
                String leaderPhone = leader.get("leaderPhone").toString();
                leader.remove("leaderPhone");
                String leaderMobile = leader.get("leaderMobile").toString();
                leader.remove("leaderMobile");
                leader.put("mphoneinfo", "办公："+leaderPhone+" 手机："+leaderMobile);
            }
            param.put("leaderList", leaderList);
        }
        List<Map<String, Object>> tleaderList = null;
        if (param.get("TECHLEADER_JSON") != null) {
            tleaderList = JSON.parseObject(param.get("TECHLEADER_JSON").toString(),List.class);
            for(Map<String,Object> leader : tleaderList){
                leader.put("tleadertip", "技术负责人");
                String leaderName = leader.get("techleaderName").toString();
                leader.remove("techleaderName");
                leader.put("tleaderName", leaderName);
                leader.put("tphonetip", "联系电话");
                String leaderPhone = leader.get("techleaderPhone").toString();
                leader.remove("techleaderPhone");
                String leaderMobile = leader.get("techleaderMobile").toString();
                leader.remove("techleaderMobile");
                leader.put("tphoneinfo", "办公："+leaderPhone+" 手机："+leaderMobile);
            }
            param.put("tleaderList", tleaderList);
        }
        List<Map<String, Object>> qaList = null;
        if (param.get("QAPERSON_JSON") != null) {
            qaList = JSON.parseObject(param.get("QAPERSON_JSON").toString(),List.class);
            for(Map<String,Object> leader : qaList){
                leader.put("qatip", "质量检验人员");
                String leaderName = leader.get("qapersonName").toString();
                leader.remove("qapersonName");
                leader.put("qaName", leaderName);
                leader.put("qaphonetip", "联系电话");
                String leaderPhone = leader.get("qapersonPhone").toString();
                leader.remove("qapersonPhone");
                String leaderMobile = leader.get("qapersonMobile").toString();
                leader.remove("qapersonMobile");
                leader.put("qaphoneinfo", "办公："+leaderPhone+" 手机："+leaderMobile);
            }
            param.put("qaList", qaList);
        }
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 上午8:41:35
     * @param param
     * @throws Exception 
     */
    private void setCrrApplyEquipment(Map<String,Object> param) throws Exception{
        if(param.get("VARIETYSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> VARIETYSRVList = JSON.parseObject(param.get("VARIETYSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> VARIETYSRV : VARIETYSRVList){
                VARIETYSRV.put("first", String.valueOf(VARIETYSRV.get("index")));
                String isowned = String.valueOf(VARIETYSRV.get("varietySrvIsOwned"));
                VARIETYSRV.put("inown", "");
                VARIETYSRV.put("outown", "");
                if(isowned.equals("0")){
                    VARIETYSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    VARIETYSRV.put("outown", "√");
                }
            }
            param.put("VARIETYSRVList", VARIETYSRVList);
        }
        if(param.get("QUICKSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> QUICKSRVList = JSON.parseObject(param.get("QUICKSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> QUICKSRV : QUICKSRVList){
                QUICKSRV.put("second", String.valueOf(QUICKSRV.get("index")));
                String isowned = String.valueOf(QUICKSRV.get("quickSrvIsOwned"));
                QUICKSRV.put("inown", "");
                QUICKSRV.put("outown", "");
                if(isowned.equals("0")){
                    QUICKSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    QUICKSRV.put("outown", "√");
                }
            }
            param.put("QUICKSRVList", QUICKSRVList);
        }
        if(param.get("ENGINESRVDEVICE_JSON")!=null){
            List<Map<String, Object>> ENGINESRVList = JSON.parseObject(param.get("ENGINESRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> ENGINESRV : ENGINESRVList){
                ENGINESRV.put("third", String.valueOf(ENGINESRV.get("index")));
                String isowned = String.valueOf(ENGINESRV.get("engineSrvIsOwned"));
                ENGINESRV.put("inown", "");
                ENGINESRV.put("outown", "");
                if(isowned.equals("0")){
                    ENGINESRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    ENGINESRV.put("outown", "√");
                }
            }
            param.put("ENGINESRVList", ENGINESRVList);
        }
        if(param.get("CARBODYSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> CARBODYSRVList = JSON.parseObject(param.get("CARBODYSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> CARBODYSRV : CARBODYSRVList){
                CARBODYSRV.put("four", String.valueOf(CARBODYSRV.get("index")));
                String isowned = String.valueOf(CARBODYSRV.get("carBodySrvIsOwned"));
                CARBODYSRV.put("inown", "");
                CARBODYSRV.put("outown", "");
                if(isowned.equals("0")){
                    CARBODYSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    CARBODYSRV.put("outown", "√");
                }
            }
            param.put("CARBODYSRVList", CARBODYSRVList);
        }
        if(param.get("ELECTRICSYSSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> ELECTRICSYSSRVList = JSON
                    .parseObject(param.get("ELECTRICSYSSRVDEVICE_JSON").toString(), List.class);
            for(Map<String,Object> ELECTRICSYSSRV : ELECTRICSYSSRVList){
                ELECTRICSYSSRV.put("five", String.valueOf(ELECTRICSYSSRV.get("index")));
                String isowned = String.valueOf(ELECTRICSYSSRV.get("electricSysSrvIsOwned"));
                ELECTRICSYSSRV.put("inown", "");
                ELECTRICSYSSRV.put("outown", "");
                if(isowned.equals("0")){
                    ELECTRICSYSSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    ELECTRICSYSSRV.put("outown", "√");
                }
            }
            param.put("ELECTRICSYSSRVList", ELECTRICSYSSRVList);
        }
        if(param.get("VARIATORSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> VARIATORSRVList = JSON.parseObject(param.get("VARIATORSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> VARIATORSRV : VARIATORSRVList){
                VARIATORSRV.put("six", String.valueOf(VARIATORSRV.get("index")));
                String isowned = String.valueOf(VARIATORSRV.get("variatorSrvIsOwned"));
                VARIATORSRV.put("inown", "");
                VARIATORSRV.put("outown", "");
                if(isowned.equals("0")){
                    VARIATORSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    VARIATORSRV.put("outown", "√");
                }
            }
            param.put("VARIATORSRVList", VARIATORSRVList);
        }
        setCrrApplyEquipment2(param);
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 上午8:41:35
     * @param param
     * @throws Exception 
     */
    private void setCrrApplyEquipment2(Map<String,Object> param) throws Exception{
        if(param.get("TIRESRVDEVICE_JSON")!=null){
            List<Map<String, Object>> TIRESRVList = JSON.parseObject(param.get("TIRESRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> TIRESRV : TIRESRVList){
                TIRESRV.put("seven", String.valueOf(TIRESRV.get("index")));
                String isowned = String.valueOf(TIRESRV.get("tireSrvIsOwned"));
                TIRESRV.put("inown", "");
                TIRESRV.put("outown", "");
                if(isowned.equals("0")){
                    TIRESRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    TIRESRV.put("outown", "√");
                }
            }
            param.put("TIRESRVList", TIRESRVList);
        }
        if(param.get("TIRELOCATESRVDEVICE_JSON")!=null){
            List<Map<String, Object>> TIRELOCATESRVList = JSON
                    .parseObject(param.get("TIRELOCATESRVDEVICE_JSON").toString(), List.class);
            for(Map<String,Object> TIRELOCATESRV : TIRELOCATESRVList){
                TIRELOCATESRV.put("eight", String.valueOf(TIRELOCATESRV.get("index")));
                String isowned = String.valueOf(TIRELOCATESRV.get("tireLocateSrvIsOwned"));
                TIRELOCATESRV.put("inown", "");
                TIRELOCATESRV.put("outown", "");
                if(isowned.equals("0")){
                    TIRELOCATESRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    TIRELOCATESRV.put("outown", "√");
                }
            }
            param.put("TIRELOCATESRVList", TIRELOCATESRVList);
        }
        if(param.get("LUBRICATESRVDEVICE_JSON")!=null){
            List<Map<String, Object>> LUBRICATESRVList = JSON
                    .parseObject(param.get("LUBRICATESRVDEVICE_JSON").toString(), List.class);
            for(Map<String,Object> LUBRICATESRV : LUBRICATESRVList){
                LUBRICATESRV.put("nine", String.valueOf(LUBRICATESRV.get("index")));
                String isowned = String.valueOf(LUBRICATESRV.get("lubricateSrvIsOwned"));
                LUBRICATESRV.put("inown", "");
                LUBRICATESRV.put("outown", "");
                if(isowned.equals("0")){
                    LUBRICATESRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    LUBRICATESRV.put("outown", "√");
                }
            }
            param.put("LUBRICATESRVList", LUBRICATESRVList);
        }
        if(param.get("INJECTORSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> INJECTORSRVList = JSON.parseObject(param.get("INJECTORSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> INJECTORSRV : INJECTORSRVList){
                INJECTORSRV.put("ten", String.valueOf(INJECTORSRV.get("index")));
                String isowned = String.valueOf(INJECTORSRV.get("injectorSrvIsOwned"));
                INJECTORSRV.put("inown", "");
                INJECTORSRV.put("outown", "");
                if(isowned.equals("0")){
                    INJECTORSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    INJECTORSRV.put("outown", "√");
                }
            }
            param.put("INJECTORSRVList", INJECTORSRVList);
        }
        if(param.get("ELECINJECTORSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> ELECINJECTORSRVList = JSON
                    .parseObject(param.get("ELECINJECTORSRVDEVICE_JSON").toString(), List.class);
            for(Map<String,Object> ELECINJECTORSRV : ELECINJECTORSRVList){
                ELECINJECTORSRV.put("tsen", String.valueOf(ELECINJECTORSRV.get("index")));
                String isowned = String.valueOf(ELECINJECTORSRV.get("elecInjectorSrvIsOwned"));
                ELECINJECTORSRV.put("inown", "");
                ELECINJECTORSRV.put("outown", "");
                if(isowned.equals("0")){
                    ELECINJECTORSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    ELECINJECTORSRV.put("outown", "√");
                }
            }
            param.put("ELECINJECTORSRVList", ELECINJECTORSRVList);
        }
        if(param.get("CRANKSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> CRANKSRVList = JSON.parseObject(param.get("CRANKSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> CRANKSRV : CRANKSRVList){
                CRANKSRV.put("eleven", String.valueOf(CRANKSRV.get("index")));
                String isowned = String.valueOf(CRANKSRV.get("crankSrvIsOwned"));
                CRANKSRV.put("inown", "");
                CRANKSRV.put("outown", "");
                if(isowned.equals("0")){
                    CRANKSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    CRANKSRV.put("outown", "√");
                }
            }
            param.put("CRANKSRVList", CRANKSRVList);
        }
        setCrrApplyEquipment3(param);
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 上午8:41:35
     * @param param
     * @throws Exception 
     */
    private void setCrrApplyEquipment3(Map<String,Object> param) throws Exception{
        if(param.get("CYLINDERSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> CYLINDERSRVList = JSON.parseObject(param.get("CYLINDERSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> CYLINDERSRV : CYLINDERSRVList){
                CYLINDERSRV.put("twelwe", String.valueOf(CYLINDERSRV.get("index")));
                String isowned = String.valueOf(CYLINDERSRV.get("cylinderSrvIsOwned"));
                CYLINDERSRV.put("inown", "");
                CYLINDERSRV.put("outown", "");
                if(isowned.equals("0")){
                    CYLINDERSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    CYLINDERSRV.put("outown", "√");
                }
            }
            param.put("CYLINDERSRVList", CYLINDERSRVList);
        }
        if(param.get("RADIATORSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> RADIATORSRVList = JSON.parseObject(param.get("RADIATORSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> RADIATORSRV : RADIATORSRVList){
                RADIATORSRV.put("thirty", String.valueOf(RADIATORSRV.get("index")));
                String isowned = String.valueOf(RADIATORSRV.get("radiatorSrvIsOwned"));
                RADIATORSRV.put("inown", "");
                RADIATORSRV.put("outown", "");
                if(isowned.equals("0")){
                    RADIATORSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    RADIATORSRV.put("outown", "√");
                }
            }
            param.put("RADIATORSRVList", RADIATORSRVList);
        }
        if(param.get("AIRCONDSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> AIRCONDSRVList = JSON.parseObject(param.get("AIRCONDSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> AIRCONDSRV : AIRCONDSRVList){
                AIRCONDSRV.put("fty", String.valueOf(AIRCONDSRV.get("index")));
                String isowned = String.valueOf(AIRCONDSRV.get("airCondSrvIsOwned"));
                AIRCONDSRV.put("inown", "");
                AIRCONDSRV.put("outown", "");
                if(isowned.equals("0")){
                    AIRCONDSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    AIRCONDSRV.put("outown", "√");
                }
            }
            param.put("AIRCONDSRVList", AIRCONDSRVList);
        }
        if(param.get("BEAUTYSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> BEAUTYSRVList = JSON.parseObject(param.get("BEAUTYSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> BEAUTYSRV : BEAUTYSRVList){
                BEAUTYSRV.put("fity", String.valueOf(BEAUTYSRV.get("index")));
                String isowned = String.valueOf(BEAUTYSRV.get("beautySrvIsOwned"));
                BEAUTYSRV.put("inown", "");
                BEAUTYSRV.put("outown", "");
                if(isowned.equals("0")){
                    BEAUTYSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    BEAUTYSRV.put("outown", "√");
                }
            }
            param.put("BEAUTYSRVList", BEAUTYSRVList);
        }
        if(param.get("GLASSSRVDEVICE_JSON")!=null){
            List<Map<String, Object>> GLASSSRVList = JSON.parseObject(param.get("GLASSSRVDEVICE_JSON").toString(),
                    List.class);
            for(Map<String,Object> GLASSSRV : GLASSSRVList){
                GLASSSRV.put("sty", String.valueOf(GLASSSRV.get("index")));
                String isowned = String.valueOf(GLASSSRV.get("glassSrvIsOwned"));
                GLASSSRV.put("inown", "");
                GLASSSRV.put("outown", "");
                if(isowned.equals("0")){
                    GLASSSRV.put("inown", "√");
                }else if(isowned.endsWith("1")){
                    GLASSSRV.put("outown", "√");
                }
            }
            param.put("GLASSSRVList", GLASSSRVList);
        }
        if(param.get("STAFF_JSON")!=null){
            List<Map<String, Object>> STAFFList = JSON.parseObject(param.get("STAFF_JSON").toString(),
                    List.class);
            for(Map<String,Object> STAFF : STAFFList){
                STAFF.put("seq", String.valueOf(STAFF.get("index")));
                if(STAFF.get("staffSex").equals("1")){
                    STAFF.put("staffSex", "男");
                }else{
                    STAFF.put("staffSex", "女");
                }
                String dgree = dictionaryService.get("degree", STAFF.get("staffEdu").toString()).get("DIC_NAME")
                        .toString();
                STAFF.put("staffEdu", dgree);
            }
            param.put("STAFFList", STAFFList);
        }
    }
}
