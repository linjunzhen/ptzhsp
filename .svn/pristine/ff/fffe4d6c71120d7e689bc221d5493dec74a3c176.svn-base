/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceParamUtil;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.RelatedGenDao;
import net.evecom.platform.zzhy.service.RelatedRotpApplyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 道路货物运输站（场）经营申请表服务
 * 
 * @author Phil He
 * @created 2017-9-19 下午5:51:53
 */
@Service
public class RelatedRotpApplyServiceImpl extends BaseServiceImpl implements RelatedRotpApplyService {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedRotpApplyServiceImpl.class);
    /**
     * 持久层访问服务
     */
    @Resource
    private RelatedGenDao dao;

    /**
     * 描述
     */
    @Resource
    DictionaryService dictionaryService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Phil He
     * @created 2017-9-19 下午5:53:49
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 根据word模板内容占位符和设置，填充数据，并产生word文件 供以客户端下载
     * 
     * @created 2017-9-19 下午5:48:24
     * @param param
     * @param filepathString
     *            模板文件路径
     * @param destpathString
     *            word文件生成保存路径
     */
    @Override
    public void replaceWord(Map<String, Object> param, String filepathString, String destpathString) {
        CustomXWPFDocument doc = generateWord(param, filepathString);
        FileOutputStream fopts = null;
        try {
            fopts = new FileOutputStream(destpathString);
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
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
     * 设置填充word占位符的数据集合
     * 
     * @author Phil He
     * @created 2017-9-19 下午7:06:24
     * @param dataMap
     */
    @Override
    public void initWordParams(Map<String, Object> dataMap) {
        String respoName = dataMap.get("RESPO_NAME") != null ? dataMap.get("RESPO_NAME").toString() : "";
        String handlerName = dataMap.get("HANDLER_NAME") != null ? dataMap.get("HANDLER_NAME").toString() : "";
        String fax = dataMap.get("APPLY_PHONE") != null ? dataMap.get("APPLY_PHONE").toString() : "";
        String applyEmail = dataMap.get("APPLY_EMAIL") != null ? dataMap.get("APPLY_EMAIL").toString() : "";
        String shbus = dataMap.get("SHBUS") != null ? dataMap.get("SHBUS").toString() : "";
        String chbus = dataMap.get("CHBUS") != null ? dataMap.get("CHBUS").toString() : "";
        String shbus1 = dataMap.get("SHBUS1") != null ? dataMap.get("SHBUS1").toString() : "";
        String chbus1 = dataMap.get("CHBUS1") != null ? dataMap.get("CHBUS1").toString() : "";
        //类别字典 {text,value} 客运字典
        List<Map<String,Object>> passTransPortList = dictionaryService.findDatasForSelect("passTransPort");
        // 申请许可内容 班车客运
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(shbus.split(","), passTransPortList, "ABC"));
        //申请许可内容 包车客运
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(chbus.split(","), passTransPortList, "BBC"));
        //如申请扩大道路旅客 班车客运
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(shbus1.split(","), passTransPortList, "CBC"));
       //如申请扩大道路旅客 包车客运
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(chbus1.split(","), passTransPortList, "DBC"));
        dataMap.put("RESPO_NAME", respoName);
        dataMap.put("HANDLER_NAME", handlerName);
        dataMap.put("APPLY_PHONE", fax);
        dataMap.put("APPLY_EMAIL", applyEmail);
        final Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("ngidx", "");
        empty.put("xidx", "");
        empty.put("nidx", "");
        empty.put("yidx", "");
        empty.put("BRAND_MODEL", "");
        empty.put("NUM", "");
        empty.put("BUS_TYPELEVEL", "");
        empty.put("BUS_LEVEL", "");
        empty.put("BUS_WEIGHT", "");
        empty.put("GET_TIME", "");
        empty.put("REMARK", "");
        empty.put("AMOUNT", "");
        empty.put("SEAT", "");
        empty.put("CAR_TYPELEVEL", "");
        empty.put("CAR_TECHLEVEL", "");
        empty.put("CAR_WIDTH", "");
        empty.put("TRAN_CERT", "");
        empty.put("LICENSE", "");
        empty.put("SEX", "");
        empty.put("AGE", "");
        empty.put("USER", "");
        empty.put("NAME", "");
        empty.put("GETTIME", "");
        empty.put("QUCODE", "");
        empty.put("QUTYPE", "");
        empty.put("ISTRAACC", "");
        empty.put("BUS_", "");
        empty.put("LEVEL", "");
        empty.put("WEIGHT", "");
        empty.put("CAR_TECH", "");
        // 已购置客运客车情况数据转换
        Object purchaseBusListJson = dataMap.get("PURCHASEBUSJSON");
        if (purchaseBusListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) purchaseBusListJson, List.class);
            for(Map<String,Object> bus : equipList){
                bus.put("SEAT", bus.get("BUS_NUM"));
            }
            dataMap.put("purchaseBusList", equipList);
        }else{
            dataMap.put("purchaseBusList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
        //拟购置营运客车情况
        Object inpurchaseBusListJson = dataMap.get("INPURCHASEBUSJSON");
        if (inpurchaseBusListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) inpurchaseBusListJson, List.class);
            dataMap.put("inpurchaseBusList", equipList);
        }else{
            dataMap.put("inpurchaseBusList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
        //现有营运客车情况
        Object operatingBusListJson = dataMap.get("OPERATINGBUSJSON");
        if (operatingBusListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) operatingBusListJson, List.class);
            dataMap.put("operatingBusList", equipList);
        }else{
            dataMap.put("operatingBusList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
        //拟聘用营运客车驾驶员情况
        Object planmployeesListJson = dataMap.get("PLAN_EMPLOYEES");
        if (planmployeesListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) planmployeesListJson, List.class);
            dataMap.put("planmployeesList", equipList);
        }else{
            dataMap.put("planmployeesList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
    }

    /**
     * 根据数据结果动态生成word文件
     * 
     * @created 2017-9-19 下午7:06:42
     * @param param
     * @param template
     * @return
     */
    private CustomXWPFDocument generateWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                // 已购置客运客车情况
                int index=1;
                List<Map<String, Object>> purchaseList = (List<Map<String, Object>>) param.get("purchaseBusList");
                for(Map purchase:purchaseList){
                    purchase.put("yidx",String.valueOf(index++));
                }
                //拟购置营运客车情况
                index=1;
                List<Map<String, Object>> currList = (List<Map<String, Object>>) param.get("inpurchaseBusList");
                for (Map curr:currList) {
                    curr.put("ngidx",String.valueOf(index++));
                    curr.put("SEAT",curr.get("SEAT_NUM"));

                }
                //现有营运客车情况
                index=1;
                List<Map<String, Object>> planList = (List<Map<String, Object>>) param.get("operatingBusList");
                for(Map plan:planList){
                    plan.put("xidx",String.valueOf(index++));
                }
                //拟聘用营运客车驾驶员情况
                index=1;
                List<Map<String, Object>> devoteList = (List<Map<String, Object>>) param.get("planmployeesList");
                for(Map devote:devoteList){
                    devote.put("nidx",String.valueOf(index++));
                    //对性别进行替换
                    if (devote.get("SEX")!=null) {
                        if ("1".equals(devote.get("SEX"))) {
                            devote.put("SEX","男");
                        }else if("2".equals(devote.get("SEX"))){
                            devote.put("SEX","女");
                        }
                    }
                    if (devote.get("ISTRAACC")!=null) {
                        if ("0".equals(devote.get("ISTRAACC"))) {
                            devote.put("ISTRAACC","是");
                        }else if("1".equals(devote.get("ISTRAACC"))){
                            devote.put("ISTRAACC","否");
                        }
                    }
                }
                //List<XWPFTable> tables = doc.getTables();
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, purchaseList, "yidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, currList, "ngidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, planList, "xidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, devoteList, "nidx");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, purchaseList,
                                    "yidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, currList,
                                    "ngidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, planList,
                                    "xidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, devoteList,
                                    "nidx");
                            WordReplaceUtil.processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return doc;
    }
}
