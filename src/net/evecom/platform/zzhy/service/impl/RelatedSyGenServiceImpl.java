/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.RelatedGenDao;
import net.evecom.platform.zzhy.service.RelatedSyGenService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年10月17日 上午11:27:19
 */
@Service("relatedSyGenService")
public class RelatedSyGenServiceImpl extends BaseServiceImpl implements RelatedSyGenService {
    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedSyGenServiceImpl.class);
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
     * 描述 《食品经营许可证》申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genFoodlicApply(Map<String, Object> param, String filepathString, String destpathString){
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> techList = null;
        List<Map<String, Object>> manageList = null;
        List<Map<String, Object>> employeeList = null;
        List<Map<String, Object>> equipList = null;

        FileOutputStream fopts = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filepathString);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                setFoodlicParams(param);
                setFoodlicParamSec(param);
                setFoodlicList(param);
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                techList = (List<Map<String, Object>>) param.get("techList");
                manageList = (List<Map<String, Object>>) param.get("manageList");
                employeeList = (List<Map<String, Object>>) param.get("employeeList");
                equipList = (List<Map<String, Object>>) param.get("equipList");
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;

                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, techList, "tindex");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, manageList, "mindex");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, employeeList, "eindex");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, equipList, "eidx");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, techList,
                                    "tindex");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, manageList,
                                    "mindex");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, employeeList,
                                    "eindex");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, equipList,
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
     * 描述 《食品经营许可证》申请表数据处理
     * @author Danto Huang
     * @created 2017年10月17日 下午2:02:12
     * @param param
     * @throws Exception
     */
    private void setFoodlicParams(Map<String,Object> param) throws Exception{
        param.put("MAIN_BUSINESS01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("MAIN_BUSINESS02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("MAIN_BUSINESS03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));

        param.put("BUS_PROJECT01$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT01-1$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT01-2$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT02$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT02-1$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT02-2$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT03$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT03-1$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT03-2$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT03-3$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT03-4$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT04$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT05$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT06$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT07$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT08$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT09$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("BUS_PROJECT10$", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        
        param.put("IS_NETWORK1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_NETWORK0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_STORE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_STORE0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_CENTRAL_KITCHEN1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_CENTRAL_KITCHEN0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_TOGETHER_DELIVERY1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_TOGETHER_DELIVERY0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_VENDING1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_VENDING0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_EDU1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_EDU0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_BULK_SALE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_BULK_SALE0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_BREWED_SALE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("IS_BREWED_SALE0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(param.get("MAIN_BUSINESS")!=null){
            String mainBusiness = param.get("MAIN_BUSINESS").toString();
            String[] mainBusinesses = mainBusiness.split(",");
            for(int i=0;i<mainBusinesses.length;i++){
                param.put("MAIN_BUSINESS".concat(mainBusinesses[i]), WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
            }
        }
        param.remove("MAIN_BUSINESS");
        if(param.get("BUS_PROJECT")!=null){
            String busProject = param.get("BUS_PROJECT").toString();
            String[] busProjects = busProject.split(",");
            for(int i=0;i<busProjects.length;i++){
                param.put(("BUS_PROJECT".concat(busProjects[i])).concat("$"),
                        WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
            }
        }
        param.remove("BUS_PROJECT");
        if(param.get("IS_NETWORK")!=null){
            String yesOrNo = param.get("IS_NETWORK").toString();
            param.put("IS_NETWORK"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_NETWORK");
        if(param.get("IS_STORE")!=null){
            String yesOrNo = param.get("IS_STORE").toString();
            param.put("IS_STORE"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_STORE");
        if(param.get("IS_CENTRAL_KITCHEN")!=null){
            String yesOrNo = param.get("IS_CENTRAL_KITCHEN").toString();
            param.put("IS_CENTRAL_KITCHEN"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_CENTRAL_KITCHEN");
        if(param.get("IS_TOGETHER_DELIVERY")!=null){
            String yesOrNo = param.get("IS_TOGETHER_DELIVERY").toString();
            param.put("IS_TOGETHER_DELIVERY"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_TOGETHER_DELIVERY");
        if(param.get("IS_VENDING")!=null){
            String yesOrNo = param.get("IS_VENDING").toString();
            param.put("IS_VENDING"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_VENDING");
        if(param.get("IS_EDU")!=null){
            String yesOrNo = param.get("IS_EDU").toString();
            param.put("IS_EDU"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_EDU");
        if(param.get("IS_BULK_SALE")!=null){
            String yesOrNo = param.get("IS_BULK_SALE").toString();
            param.put("IS_BULK_SALE"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_BULK_SALE");
        if(param.get("IS_BREWED_SALE")!=null){
            String yesOrNo = param.get("IS_BREWED_SALE").toString();
            param.put("IS_BREWED_SALE"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("IS_BREWED_SALE");
    }    

    /**
     * 
     * 描述 《食品经营许可证》申请表数据处理
     * @author Danto Huang
     * @created 2017年10月17日 下午2:02:12
     * @param param
     * @throws Exception
     */
    private void setFoodlicParamSec(Map<String,Object> param) throws Exception{
        String nation = dictionaryService.get("nation", param.get("LEADER_NATION").toString()).get("DIC_NAME")
                .toString();
        param.put("LEADER_NATION", nation);
        String cardType = dictionaryService.get("DocumentType", param.get("LEADER_CARDTYPE").toString()).get("DIC_NAME")
                .toString();
        param.put("LEADER_CARDTYPE", cardType);
        if(param.get("LEADER_SEX").equals("1")){
            param.put("LEADER_SEX", "男");
        }else{
            param.put("LEADER_SEX", "女");
        }
        param.put("NATURE01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("NATURE02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("NATURE03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("NATURE04", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(param.get("NATURE")!=null){
            String nature = param.get("NATURE").toString();
            String[] natures = nature.split(",");
            for(int i=0;i<natures.length;i++){
                param.put("NATURE".concat(natures[i]), WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
            }
        }
        param.remove("NATURE");

        param.put("CHECKCOPY1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("CHECKCOPY0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("CHECKSELF1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("CHECKSELF0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("CHECKFORM1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("CHECKFORM0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("LICRECIVE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        param.put("LICRECIVE0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        if(param.get("CHECKCOPY")!=null){
            String yesOrNo = param.get("CHECKCOPY").toString();
            param.put("CHECKCOPY"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("CHECKCOPY");
        if(param.get("CHECKSELF")!=null){
            String yesOrNo = param.get("CHECKSELF").toString();
            param.put("CHECKSELF"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("CHECKSELF");
        if(param.get("CHECKFORM")!=null){
            String yesOrNo = param.get("CHECKFORM").toString();
            param.put("CHECKFORM"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("CHECKFORM");
        if(param.get("LICRECIVE")!=null){
            String yesOrNo = param.get("LICRECIVE").toString();
            param.put("LICRECIVE"+yesOrNo, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        param.remove("LICRECIVE");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        if(param.get("AGENT_STARTDATE")!=null){
            String startDate = param.get("AGENT_STARTDATE").toString().substring(0,10);
            param.put("AGENT_STARTDATE", (new SimpleDateFormat("yyyy年mm月dd日")).format(formatter.parse(startDate)));
        }
        if(param.get("AGENT_ENDDATE")!=null){
            String endDate = param.get("AGENT_ENDDATE").toString().substring(0,10);
            param.put("AGENT_ENDDATE", (new SimpleDateFormat("yyyy年mm月dd日")).format(formatter.parse(endDate)));
        }
    }

    /**
     * 
     * 描述 《食品经营许可证》申请表数据处理
     * @author Danto Huang
     * @created 2017年10月17日 下午2:02:12
     * @param param
     * @throws Exception
     */
    private void setFoodlicList(Map<String,Object> param) throws Exception{
        List<Map<String, Object>> techList = null;
        List<Map<String, Object>> manageList = null;
        List<Map<String, Object>> employeeList = null;
        List<Map<String, Object>> equipList = null;
        Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("tindex", "");
        empty.put("mindex", "");
        empty.put("eindex", "");
        empty.put("NAME", "");
        empty.put("SEX", "");
        empty.put("NATION", "");
        empty.put("DOMICILE", "");
        empty.put("ID_TYPE", "");
        empty.put("ID_NO", "");
        empty.put("JOB", "");
        empty.put("PHONE", "");
        empty.put("POINTDEP", "");
        empty.put("HEALTHNO", "");
        empty.put("WTYPE", "");
        empty.put("SENDDEP", "");
        if(param.get("SECURITY_TECHJSON")!=null){
            techList = JSON.parseObject(param.get("SECURITY_TECHJSON").toString(),List.class);
            for(int i=0;i<techList.size();i++){
                Map<String,Object> tech = techList.get(i);
                tech.put("tindex", String.valueOf(i+1));
                String sex = dictionaryService.get("sex", tech.get("SEX").toString()).get("DIC_NAME").toString();
                tech.put("SEX", sex);
                String nation = dictionaryService.get("nation", tech.get("NATION").toString()).get("DIC_NAME")
                        .toString();
                tech.put("NATION", nation);
                String DocumentType = dictionaryService.get("DocumentType", tech.get("ID_TYPE").toString())
                        .get("DIC_NAME").toString();
                tech.put("ID_TYPE", DocumentType);
            }
            param.put("techList", techList);
        }else{
            techList = new ArrayList<Map<String,Object>>();
            techList.add(empty);
            param.put("techList", techList);
        }
        if(param.get("SECURITY_MANJSON")!=null){
            manageList = JSON.parseObject(param.get("SECURITY_MANJSON").toString(),List.class);
            for(int i=0;i<manageList.size();i++){
                Map<String,Object> tech = manageList.get(i);
                tech.put("mindex", String.valueOf(i+1));
                String sex = dictionaryService.get("sex", tech.get("SEX").toString()).get("DIC_NAME").toString();
                tech.put("SEX", sex);
                String nation = dictionaryService.get("nation", tech.get("NATION").toString()).get("DIC_NAME")
                        .toString();
                tech.put("NATION", nation);
                String DocumentType = dictionaryService.get("DocumentType", tech.get("ID_TYPE").toString())
                        .get("DIC_NAME").toString();
                tech.put("ID_TYPE", DocumentType);
            }
            param.put("manageList", manageList);
        }else{
            manageList = new ArrayList<Map<String,Object>>();
            manageList.add(empty);
            param.put("manageList", manageList);
        }
        if(param.get("EMPLOYEEJSON")!=null){
            employeeList = JSON.parseObject(param.get("EMPLOYEEJSON").toString(),List.class);
            for(int i=0;i<employeeList.size();i++){
                Map<String,Object> tech = employeeList.get(i);
                tech.put("eindex", String.valueOf(i+1));
                String sex = dictionaryService.get("sex", tech.get("SEX").toString()).get("DIC_NAME").toString();
                tech.put("SEX", sex);
                String nation = dictionaryService.get("nation", tech.get("NATION").toString()).get("DIC_NAME")
                        .toString();
                tech.put("NATION", nation);
                String DocumentType = dictionaryService.get("DocumentType", tech.get("ID_TYPE").toString())
                        .get("DIC_NAME").toString();
                tech.put("ID_TYPE", DocumentType);
            }
            param.put("employeeList", employeeList);
        }else{
            employeeList = new ArrayList<Map<String,Object>>();
            employeeList.add(empty);
            param.put("employeeList", employeeList);
        }
        if(param.get("EQUIPMENTJSON")!=null){
            equipList = JSON.parseObject(param.get("EQUIPMENTJSON").toString(),List.class);
            for(int i=0;i<equipList.size();i++){
                Map<String,Object> tech = equipList.get(i);
                tech.put("eidx", String.valueOf(i+1));
            }
            param.put("equipList", equipList);
        }else{
            equipList = new ArrayList<Map<String,Object>>();
            empty = new HashMap<String, Object>();
            empty.put("eidx", "");
            empty.put("EQUIPNAME", "");
            empty.put("COUNT", "");
            empty.put("LOCATION", "");
            empty.put("REMARK", "");
            equipList.add(empty);
            param.put("equipList", equipList);
        }
        
    }
}
