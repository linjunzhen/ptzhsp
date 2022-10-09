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
import net.evecom.platform.zzhy.service.RelatedRdgtApplyService;
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

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 道路危险货物运输经营申请表服务
 * 
 * @author Phil He
 * @created 2017-9-19 下午5:51:53
 */
@Service("rdgtApplyService")
public class RelatedRdgtApplyServiceImpl extends BaseServiceImpl implements RelatedRdgtApplyService {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedRdgtApplyServiceImpl.class);
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
        // 数据空值处理 null->空字符串
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String value = dataMap.get(key)!=null?dataMap.get(key).toString():"";
            dataMap.put(key, value);
        }
        
        //CONTENT_PRODUCT_PLAN
        String contentProductPlan = (String)dataMap.get("CONTENT_PRODUCT_PLAN");
        dataMap.put("$CONTENT_PRODUCT_PLAN$", contentProductPlan);
        dataMap.remove("CONTENT_PRODUCT_PLAN");
        //CONTENT_PRODUCT_CURR
        String contentProductCurr = (String)dataMap.get("CONTENT_PRODUCT_CURR");
        dataMap.put("$CONTENT_PRODUCT_CURR$", contentProductCurr);
        dataMap.remove("CONTENT_PRODUCT_CURR");
        //CONTENT_PRODUCT
        String contentProduct = (String)dataMap.get("CONTENT_PRODUCT");
        dataMap.put("$CONTENT_PRODUCT$", contentProduct);
        dataMap.remove("CONTENT_PRODUCT");
        
        // radio或者checkbox特殊处理
        //String applyType = (String)dataMap.get("APPLY_TYPE"); //申请许可类型（1：首次申请，2：申请扩大范围）
        //类别字典 {text,value}
        List<Map<String,Object>> catalogList = dictionaryService.findDatasForSelect("rdgtCatalog");
        //项别字典 {text,value}
        List<Map<String,Object>> rdgtItems = dictionaryService.findDatasForSelect("rdgtItem");
        ////////////////申请许可checkbox处理==========begin
        // CONTENT_CATEGORY 申请许可内容类别
        String contentCategory = (String) dataMap.get("CONTENT_CATEGORY");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(contentCategory.split(","), catalogList, "CC"));
        // CONTENT_ITEM 申请许可内容项别
        String contentItem = (String) dataMap.get("CONTENT_ITEM");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(contentItem.split(","), rdgtItems, "CI"));
        // CONTENT_PRODUCT 申请许可内容品名
        
        // CONTENT_CATEGORY_CURR 现有类别
        String contentCurrCate = (String) dataMap.get("CONTENT_CATEGORY_CURR");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(contentCurrCate.split(","), catalogList, "CCC"));
        // CONTENT_ITEM_CURR 现有项别
        String contentCurrItem = (String) dataMap.get("CONTENT_ITEM_CURR");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(contentCurrItem.split(","), rdgtItems, "CIC"));
        // CONTENT_PRODUCT_CURR 现有品名

        // /CONTENT_CATEGORY_PLAN 扩大申请许可内容类别
        String contentPlanCate = (String) dataMap.get("CONTENT_CATEGORY_PLAN");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(contentPlanCate.split(","), catalogList, "CCP"));
        // CONTENT_ITEM_PLAN 扩大申请许可内容项别
        String contentPlanItem = (String) dataMap.get("CONTENT_ITEM_PLAN");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(contentPlanItem.split(","), rdgtItems, "CIP"));
        // CONTENT_PRODUCT_PLAN 扩大申请许可内容品名
        ////////////////申请许可checkbox处理==========End
        
        ///////////停车场地所有权checkbox处理======begin
        String parkingBelong = (String) dataMap.get("PARKING_BELONG"); //停车场地所有权  自有，租借（年限）
        List<Map<String,Object>> belongMap = new ArrayList<Map<String,Object>>(2);
        belongMap.add(new HashMap<String, Object>() {
            {
                put("text", "自有");
                put("value", "1");
            }
        });
        belongMap.add(new HashMap<String, Object>() {
            {
                put("text", "租借");
                put("value", "0");
            }
        });
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(parkingBelong.split(","), belongMap, "PB"));
        ///////////停车场地所有权checkbox处理======end
        
//        WordReplaceParamUtil.initGridParam(dataMap, new HashMap<String, String>() {
//            {
//                put("PURCHASE_JSON", "purchaseList");
//                put("PURCHASE_PLAN_JSON", "purhcasePlanList");
//                put("CURR_JSON", "currCarList");
//                put("PLAN_EMPLOYEES", "planEmployeeList");
//            }
//        });
        initWordData(dataMap);
    }


    /**
     * 设置填充word占位符的数据集合
     *
     * @author Phil He
     * @created 2017-9-19 下午7:06:24
     * @param dataMap
     */
    public void initWordData(Map<String, Object> dataMap){
        // 表格json数据处理
        //已购置车辆情况json
        String purchaseJson = (String)dataMap.get("PURCHASE_JSON");
        if(StringUtils.isEmpty(purchaseJson) ){
            purchaseJson = "[{\"OLD_INDEX\":\"\",\"BRAND_MODEL\":\"\",\"AMOUNT\":\"\",\"CAR_TYPE\":\"\","
                    + "\"TECH_LEVEL\":\"\",\"CAR_WEIGHT\":\"\",\"DEAD_WEIGHT\":\"\","
                    + "\"AXLE_NUM\":\"\",\"OUT_SIZE\":\"\",\"VOLUME\":\"\",\"NAMEANDDES\":\"\","
                    + "\"COMM_EQUIP\":\"\",\"RUN_RECORD\":\"\"}]";
        }
        List<Map<String,Object>> purcharseList = JSON.parseObject( purchaseJson, List.class);
        for(int i = 0,max = purcharseList.size();i<max;i++){
            Map<String,Object> purcharse = purcharseList.get(i);
            String COMM_EQUIP = (String)purcharse.get("COMM_EQUIP");
            if(StringUtils.isNotEmpty(COMM_EQUIP)){
                purcharse.put("COMM_EQUIP", "1".equals(COMM_EQUIP)?"是":"否");
            }
            String RUN_RECORD = (String)purcharse.get("RUN_RECORD");
            if(StringUtils.isNotEmpty(RUN_RECORD)){
                purcharse.put("RUN_RECORD", "1".equals(RUN_RECORD)?"是":"否");
            }
            if(purcharse.get("OLD_INDEX") == null){
                purcharse.put("OLD_INDEX", i+1+"");
            }
        }
        dataMap.put("purchaseList", purcharseList);

        //拟购置货物运输车辆情况json
        String purcharsePlanJson = (String)dataMap.get("PURCHASE_PLAN_JSON");
        if(StringUtils.isEmpty(purcharsePlanJson)){
            purcharsePlanJson = "[{\"PLAN_INDEX\":\"\",\"BRAND_MODEL\":\"\",\"AMOUNT\":\"\",\"CAR_TYPE\":\"\","
                    + "\"TECH_LEVEL\":\"\",\"CAR_WEIGHT\":\"\",\"DEAD_WEIGHT\":\"\","
                    + "\"AXLE_NUM\":\"\",\"OUT_SIZE\":\"\",\"VOLUME\":\"\",\"NAMEANDDES\":\"\","
                    + "\"COMM_EQUIP\":\"\",\"RUN_RECORD\":\"\"}]";
        }
        List<Map<String,Object>> purchasePlanList = JSON.parseObject(purcharsePlanJson, List.class);
        for(int i = 0,max = purchasePlanList.size();i<max;i++){
            Map<String,Object> purcharsePlan = purchasePlanList.get(i);
            String COMM_EQUIP = (String)purcharsePlan.get("COMM_EQUIP");
            if(StringUtils.isNotEmpty(COMM_EQUIP)){
                purcharsePlan.put("COMM_EQUIP", "1".equals(COMM_EQUIP)?"是":"否");
            }
            String RUN_RECORD = (String)purcharsePlan.get("RUN_RECORD");
            if(StringUtils.isNotEmpty(RUN_RECORD)){
                purcharsePlan.put("RUN_RECORD", "1".equals(RUN_RECORD)?"是":"否");
            }
            if(purcharsePlan.get("PLAN_INDEX") == null){
                purcharsePlan.put("PLAN_INDEX", i+1+"");
            }

        }
        dataMap.put("purchasePlanList", purchasePlanList);

        //现有车辆情况json
        String currCarJson = (String)dataMap.get("CURR_JSON");
        if(StringUtils.isEmpty(currCarJson)){
            currCarJson = "[{\"NOW_INDEX\":\"\",\"TRAN_CERT\":\"\",\"BRAND_MODEL\":\"\",\"AMOUNT\":\"\","
                    + "\"CAR_TYPE\":\"\",\"TECH_LEVEL\":\"\",\"CAR_WEIGHT\":\"\",\"DEAD_WEIGHT\":\"\","
                    + "\"AXLE_NUM\":\"\",\"OUT_SIZE\":\"\",\"VOLUME\":\"\",\"NAMEANDDES\":\"\","
                    + "\"COMM_EQUIP\":\"\",\"RUN_RECORD\":\"\"}]";
        }
        List<Map<String,Object>> currCarList =
                (List<Map<String,Object>>)JSON.parseObject( currCarJson, List.class);
        for(int i = 0,max = currCarList.size();i<max;i++){
            Map<String,Object> car = currCarList.get(i);
            //表格是否字段值处理 1：是 0：否
            String COMM_EQUIP = (String)car.get("COMM_EQUIP");
            if(StringUtils.isNotEmpty(COMM_EQUIP)){
                car.put("COMM_EQUIP", "1".equals(COMM_EQUIP)?"是":"否");
            }
            String RUN_RECORD = (String)car.get("RUN_RECORD");
            if(StringUtils.isNotEmpty(RUN_RECORD)){
                car.put("RUN_RECORD", "1".equals(RUN_RECORD)?"是":"否");
            }
            if(car.get("NOW_INDEX") == null){
                car.put("NOW_INDEX", i+1+"");
            }

        }
        dataMap.put("currCarList", currCarList);

        //拟聘用专职安全管理人员和从业人员情况
        String planEmployeeJson = (String)dataMap.get("PLAN_EMPLOYEES");
        if(StringUtils.isEmpty(planEmployeeJson)){
            planEmployeeJson = "[{\"EMPLOYEE_INDEX\":\"\",\"NAME\":\"\",\"SEX\":\"\","
                    + "\"AGE\":\"\",\"JOBTYPE\":\"\",\"TAKE_TIME\":\"\"," + "\"CERT_NO\":\"\",\"CERT_TYPE\":\"\"}]";
        }
        List<Map<String,Object>> planEmployeeList = JSON.parseObject( planEmployeeJson, List.class);
        for(int i = 0,max = planEmployeeList.size();i<max;i++){
            Map<String,Object> employee = planEmployeeList.get(i);
            if(employee.get("EMPLOYEE_INDEX") == null){
                employee.put("EMPLOYEE_INDEX", i+1+"");
            }
            String sex = null;
            if(employee.get("SEX").equals("1")){
                sex = "男";
            }else{
                sex = "女";
            }
            employee.put("SEX", sex);
        }
        dataMap.put("planEmployeeList", planEmployeeList);
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
                List<Map<String, Object>> purchaseList = (List<Map<String, Object>>) param.get("purchaseList");
                List<Map<String, Object>> purhcasePlanList = (List<Map<String, Object>>) param.get("purchasePlanList");
                List<Map<String, Object>> currCarList = (List<Map<String, Object>>) param.get("currCarList");
                List<Map<String, Object>> planEmployeeList = (List<Map<String, Object>>) param.get("planEmployeeList");
               
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    // table.addRow(rows.get(0));
                    XWPFTableRow oldRow = null;

                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, purchaseList, "OLD_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, purhcasePlanList, "PLAN_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, currCarList, "NOW_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, planEmployeeList, "EMPLOYEE_INDEX");
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, purchaseList,
                                    "OLD_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, purhcasePlanList,
                                    "PLAN_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, currCarList,
                                    "NOW_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, planEmployeeList,
                                    "EMPLOYEE_INDEX");
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
