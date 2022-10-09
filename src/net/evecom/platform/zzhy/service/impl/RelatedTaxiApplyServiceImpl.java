/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.platform.zzhy.dao.RelatedGenDao;
import net.evecom.platform.zzhy.service.RelatedTaxiApplyService;
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
public class RelatedTaxiApplyServiceImpl extends BaseServiceImpl implements RelatedTaxiApplyService {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedTaxiApplyServiceImpl.class);
    /**
     * 持久层访问服务
     */
    @Resource
    private RelatedGenDao dao;

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
        // 数据空值处理 null->空字符串
        // radio或者checkbox特殊处理
        // for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
        // String key = entry.getKey();
        // String value = dataMap.get(key)!=null?dataMap.get(key).toString():"";
        // dataMap.put(key, value);
        // }
        //
        String respoName = dataMap.get("RESPO_NAME") != null ? dataMap.get("RESPO_NAME").toString() : "";
        String handlerName = dataMap.get("HANDLER_NAME") != null ? dataMap.get("HANDLER_NAME").toString() : "";
        String fax = dataMap.get("APPLY_PHONE") != null ? dataMap.get("APPLY_PHONE").toString() : "";
        String applyEmail = dataMap.get("APPLY_EMAIL") != null ? dataMap.get("APPLY_EMAIL").toString() : "";

        dataMap.put("RESPO_NAME", respoName);
        dataMap.put("HANDLER_NAME", handlerName);
        dataMap.put("APPLY_PHONE", fax);
        dataMap.put("APPLY_EMAIL", applyEmail);


        final Map<String,Object> empty = new HashMap<String, Object>();
        empty.put("pnidx", "");
        empty.put("xidx", "");
        empty.put("nidx", "");
        empty.put("pidx", "");
        empty.put("BRAND_MODEL", "");
        empty.put("AMOUNT", "");
        empty.put("SEAT", "");
        empty.put("TECH_LEVEL", "");
        empty.put("TECH_LEVELTYPE", "");
        empty.put("NOTE", "");
        empty.put("TRANSPORT_NO", "");
        empty.put("NAME", "");
        empty.put("SEX", "");
        empty.put("AGE", "");
        empty.put("TAKE_TIME", "");
        empty.put("CERT_NO", "");
        empty.put("CERT_TYPE", "");
        // 拟购置营运车辆情况数据转换
        Object purchaseListJson = dataMap.get("PURCHASE_PLAN_JSON");
        if (purchaseListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) purchaseListJson, List.class);
            dataMap.put("purchaseList", equipList);
        }else{
            dataMap.put("purchaseList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
        //现有营运车辆情况
        Object currListJson = dataMap.get("CURR_JSON");
        if (currListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) currListJson, List.class);
            dataMap.put("currList", equipList);
        }else{
            dataMap.put("currList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
        //聘用或者拟聘用巡游出租汽车驾驶员情况
        Object planListJson = dataMap.get("PLAN_EMPLOYEES");
        if (planListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) planListJson, List.class);
            dataMap.put("planList", equipList);
        }else{
            dataMap.put("planList",new ArrayList<Map<String,Object>>(){{add(empty);}});
        }
        //拟投入车辆情况
        Object devoteListJson = dataMap.get("DEVOTE_PLAN_JSON");
        if (devoteListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) devoteListJson, List.class);
            dataMap.put("devoteList", equipList);
        }else{
            dataMap.put("devoteList",new ArrayList<Map<String,Object>>(){{add(empty);}});
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
                // 拟购置营运车辆情况
                int index=1;
                List<Map<String, Object>> purchaseList = (List<Map<String, Object>>) param.get("purchaseList");
                for(Map purchase:purchaseList){
                    purchase.put("ngidx",String.valueOf(index++));
                }
                //现有营运车辆情况
                index=1;
                List<Map<String, Object>> currList = (List<Map<String, Object>>) param.get("currList");
                for (Map curr:currList) {
                    curr.put("xidx",String.valueOf(index++));
                }
                //聘用或者拟聘用巡游出租汽车驾驶员情况
                index=1;
                List<Map<String, Object>> planList = (List<Map<String, Object>>) param.get("planList");
                for(Map plan:planList){
                    //对性别进行替换
                    if ( plan.get("SEX")!=null) {
                        if ("1".equals(plan.get("SEX"))) {
                            plan.put("SEX","男");
                        }else if("2".equals(plan.get("SEX"))){
                            plan.put("SEX","女");
                        }
                    }
                    plan.put("pidx",String.valueOf(index++));
                }
                //拟投入车辆情况
                index=1;
                List<Map<String, Object>> devoteList = (List<Map<String, Object>>) param.get("devoteList");
                for(Map devote:devoteList){
                    devote.put("nidx",String.valueOf(index++));
                }
                //List<XWPFTable> tables = doc.getTables();
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    XWPFTableRow oldRow = null;
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, purchaseList, "ngidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, currList, "xidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, planList, "pidx");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, devoteList, "nidx");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, purchaseList,
                                    "ngidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, currList,
                                    "xidx");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, planList,
                                    "pidx");
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
