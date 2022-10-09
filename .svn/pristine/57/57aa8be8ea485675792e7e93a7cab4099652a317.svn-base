/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.platform.zzhy.dao.RelatedGenDao;
import net.evecom.platform.zzhy.service.RelatedRfsApplyService;

import org.apache.commons.collections.MapUtils;
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
import com.google.common.collect.Collections2;

/**
 * 道路货物运输站（场）经营申请表服务
 * 
 * @author Phil He
 * @created 2017-9-19 下午5:51:53
 */
@Service
public class RelatedRfsApplyServiceImpl extends BaseServiceImpl implements RelatedRfsApplyService {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedRfsApplyServiceImpl.class);
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
        String fax = dataMap.get("FAX") != null ? dataMap.get("FAX").toString() : "";
        String applyEmail = dataMap.get("APPLY_EMAIL") != null ? dataMap.get("APPLY_EMAIL").toString() : "";

        dataMap.put("RESPO_NAME", respoName);
        dataMap.put("HANDLER_NAME", handlerName);
        dataMap.put("FAX", fax);
        dataMap.put("APPLY_EMAIL", applyEmail);

        // 设备、设施情况数据转换
        Object equipListJson = dataMap.get("EQUIPMENT_JSON");
        if (equipListJson != null) {
            List<Map<String, Object>> equipList = JSON.parseObject((String) equipListJson, List.class);
            dataMap.put("equipList", equipList);
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
                List<Map<String, Object>> equipList = (List<Map<String, Object>>) param.get("equipList");
                //List<XWPFTable> tables = doc.getTables();
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    // table.addRow(rows.get(0));
                    XWPFTableRow oldRow = null;

                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, equipList, "equipmentName");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, equipList,
                                    "equipmentName");
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
