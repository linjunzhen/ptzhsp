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

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceParamUtil;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.RelatedGenDao;
import net.evecom.platform.zzhy.service.RelatedAbcrApplyService;
import net.evecom.platform.zzhy.service.RelatedRdgtApplyService;
import net.evecom.platform.zzhy.service.RelatedRfsApplyService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 一、二类汽车维修企业经营许可登记申请表
 * 
 * @author Phil He
 * @created 2017-9-19 下午5:51:53
 */
@Service("abcrApplyService")
public class RelatedAbcrApplyServiceImpl extends BaseServiceImpl implements RelatedAbcrApplyService {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(RelatedAbcrApplyServiceImpl.class);
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
        setParamfirst(dataMap);

        // radio或者checkbox特殊处理
        List<Map<String, Object>> dicMap = new ArrayList<Map<String, Object>>(2); // □已培训且符合要求
                                                                                  // □未培训或不符合要求
        dicMap.add(new HashMap<String, Object>() {
            {
                put("text", "已培训且符合要求");
                put("value", "1");
            }
        });
        dicMap.add(new HashMap<String, Object>() {
            {
                put("text", "未培训或不符合要求");
                put("value", "0");
            }
        });
        // SELFCHECK_TECHLEADERC
        String SELFCHECK_TECHLEADERC = (String) dataMap.get("SELFCHECK_TECHLEADERC");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_TECHLEADERC.split(","), dicMap, "TECH"));
        // SELFCHECK_QAPERSONC
        String SELFCHECK_QAPERSONC = (String) dataMap.get("SELFCHECK_QAPERSONC");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_QAPERSONC.split(","), dicMap, "QA"));
        // SELFCHECK_MECHANICC
        String SELFCHECK_MECHANICC = (String) dataMap.get("SELFCHECK_MECHANICC");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_MECHANICC.split(","), dicMap, "MECH"));
        // SELFCHECK_EAMAINTAINERC
        String SELFCHECK_EAMAINTAINERC = (String) dataMap.get("SELFCHECK_EAMAINTAINERC");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_EAMAINTAINERC.split(","), dicMap, "EAMA"));
        // SELFCHECK_METZLERC
        String SELFCHECK_METZLERC = (String) dataMap.get("SELFCHECK_METZLERC");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_METZLERC.split(","), dicMap, "METZ"));
        // SELFCHECK_PAINTERC
        String SELFCHECK_PAINTERC = (String) dataMap.get("SELFCHECK_PAINTERC");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_PAINTERC.split(","), dicMap, "PAIN"));
        // SELFCHECK_GASCARMAINTAINERB
        String SELFCHECK_GASCARMAINTAINERB = (String) dataMap.get("SELFCHECK_GASCARMAINTAINERB");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_GASCARMAINTAINERB.split(","), dicMap, "GASA"));
        // SELFCHECK_WORKSAFETYG
        String SELFCHECK_WORKSAFETYG = (String) dataMap.get("SELFCHECK_WORKSAFETYG");
        dataMap.putAll(WordReplaceParamUtil.initCheckBoxValues(SELFCHECK_WORKSAFETYG.split(","), dicMap, "WST"));

        // //////////////checkbox处理==========End

        // ////// 表格json数据处理
     // LEADER_JSON 企业管理负责人信息json
        String leaderJson = (String) dataMap.get("LEADER_JSON");
        List<Map<String, Object>> leaderList = JSON.parseObject(leaderJson, List.class);
        for (int i = 0, max = leaderList.size(); i < max; i++) {
            Map<String, Object> leader = leaderList.get(i);
            leader.put("leaderHeader", "企业管理负责人");
            leader.put("phoneHeader", "联系电话");
            leader.put("phoneStr","办公："+leader.get("leaderPhone")+"  手机："+leader.get("leaderMobile"));
        }
        dataMap.put("leaderList", leaderList);
        // TECHLEADER_JSON 技术负责人信息json
        String techLeaderJson = (String) dataMap.get("TECHLEADER_JSON");
        List<Map<String, Object>> techLeaderList = JSON.parseObject(techLeaderJson, List.class);
        for (int i = 0, max = techLeaderList.size(); i < max; i++) {
            Map<String, Object> techLeader = techLeaderList.get(i);
            techLeader.put("techLeaderHeader", "技术负责人");
            techLeader.put("techPhoneHeader", "联系电话");
            techLeader.put("techPhoneStr","办公："+techLeader.get("techleaderPhone")
                    +"  手机："+techLeader.get("techleaderMobile"));
        }
        dataMap.put("techLeaderList", techLeaderList);
        // QAPERSON_JSON 质量检验人员信息json
        String qapersonJson = (String) dataMap.get("QAPERSON_JSON");
        List<Map<String, Object>> qaPersonList = JSON.parseObject(qapersonJson, List.class);
        for (int i = 0, max = qaPersonList.size(); i < max; i++) {
            Map<String, Object> qaPerson = qaPersonList.get(i);
            qaPerson.put("qaLeaderHeader", "质量检验人员");
            qaPerson.put("qaPhoneHeader", "联系电话");
            qaPerson.put("qaPhoneStr","办公："+qaPerson.get("qapersonPhone")
                    +"  手机："+qaPerson.get("qapersonMobile"));
        }
        dataMap.put("qaPersonList", qaPersonList);

        // STAFF_JSON 从业人员信息json
        String staffJson = (String) dataMap.get("STAFF_JSON");
        List<Map<String, Object>> staffList = JSON.parseObject(staffJson, List.class);
        for (int i = 0, max = staffList.size(); i < max; i++) {
            Map<String, Object> staff = staffList.get(i);
            if (staff.get("staffIndex") == null) {
                staff.put("staffIndex", i + 1 + "");
            }
            if(staff.get("staffSex").equals("1")){
                staff.put("staffSex", "男");
            }else{
                staff.put("staffSex", "女");
            }

            String dgree = dictionaryService.get("degree", staff.get("staffEdu").toString()).get("DIC_NAME")
                    .toString();
            staff.put("staffEdu", dgree);
        }
        dataMap.put("staffList", staffList);
        // INSTRUMENT_JSON 仪表工具信息json
        this.initRadioParam(dataMap, "INSTRUMENT_JSON",
                "instrumentIsOwned", "CHARTDEVICE_INDEX", "instrumentList");
        // DEDICATEDDEVICE_JSON 专用设备信息json
        this.initRadioParam(dataMap, "DEDICATEDDEVICE_JSON",
                "dedicatedDeviceIsOwned", "SPECDEVICE_INDEX", "dedicateList");
        // INSPECTDEVICE_JSON 检测设备信息json
        this.initRadioParam(dataMap, "INSPECTDEVICE_JSON", "inspectDeviceIsOwned", "CHECKDEVICE_INDEX", "inspectList");
        // COMMONDEVICE_JSON 通用设备信息json
        this.initRadioParam(dataMap, "COMMONDEVICE_JSON", "commonDeviceIsOwned", "COMMDEVICE_INDEX", "commList");
        // SPECIALDEVICE_JSON 专用设施设备
        String specDeviceJson = (String) dataMap.get("SPECIALDEVICE_JSON");
        if (StringUtils.isEmpty(specDeviceJson)) {
            specDeviceJson = "[{\"deviceName\":\"\",\"modelNum\":\"\","
                    + "\"amount\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"remark\":\"\",\"specIndex\":\"\"}]";
        }
        List<Map<String, Object>> specDeviceList = JSON.parseObject(specDeviceJson, List.class);
        for (int i = 0, max = specDeviceList.size(); i < max; i++) {
            Map<String, Object> specDevice = specDeviceList.get(i);
            String index = specDevice.get("index").toString();
            if (StringUtils.isNotEmpty(index)&& specDevice.get("specIndex") == null) {
                specDevice.put("specIndex", index);
            }
        }
        dataMap.put("specDeviceList", specDeviceList);
       
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年9月25日 下午5:40:13
     * @param dataMap
     */
    private void setParamfirst(Map<String,Object> dataMap){
     // 数据空值处理 null->空字符串
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String value = dataMap.get(key) != null ? dataMap.get(key).toString() : "";
            dataMap.put(key, value);
        }
        String bankAccount = dataMap.get("COMPANY_BANK").toString();
        dataMap.put("$COMPANY_BANK$", bankAccount);
        dataMap.remove("COMPANY_BANK");
        String IS_SPECIALSERVICE = String.valueOf(dataMap.get("IS_SPECIALSERVICE"));
        dataMap.remove("IS_SPECIALSERVICE");
        if(IS_SPECIALSERVICE.equals("1")){
            dataMap.put("IS_SPECIALSERVICE", "是");
        }else{
            dataMap.put("IS_SPECIALSERVICE", "否");
        }
        String IS_RESCUESERVICE = String.valueOf(dataMap.get("IS_RESCUESERVICE"));
        dataMap.remove("IS_RESCUESERVICE");
        if(IS_RESCUESERVICE.equals("1")){
            dataMap.put("IS_RESCUESERVICE", "是");
        }else{
            dataMap.put("IS_RESCUESERVICE", "否");
        }
        String IS_CHAINOPR = String.valueOf(dataMap.get("IS_CHAINOPR"));
        dataMap.remove("IS_CHAINOPR");
        if(IS_CHAINOPR.equals("1")){
            dataMap.put("IS_CHAINOPR", "是");
        }else{
            dataMap.put("IS_CHAINOPR", "否");
        }
        // 重复名称字段处理
        // String contentProductPlan =
        // (String)dataMap.get("CONTENT_PRODUCT_PLAN");
        // dataMap.put("$CONTENT_PRODUCT_PLAN$", contentProductPlan);
        // dataMap.remove("CONTENT_PRODUCT_PLAN");
        
       // TECHLEADER_COUNT
        String techLeaderCn = (String) dataMap.get("TECHLEADER_COUNT");
        dataMap.put("$TECHLEADER_CNT$", techLeaderCn);
        dataMap.remove("TECHLEADER_COUNT");
        // RESTMANAGER_COUNT
        String restManagerCn = (String) dataMap.get("RESTMANAGER_COUNT");
        dataMap.put("$RESTMANAGER_CNT$", restManagerCn);
        dataMap.remove("RESTMANAGER_COUNT");
        // RESTMAINTAINER_COUNT
        String restMaintainerCn = (String) dataMap.get("RESTMAINTAINER_COUNT");
        dataMap.put("$RESTMAINTAINER_CNT$", restMaintainerCn);
        dataMap.remove("RESTMAINTAINER_COUNT");
        
        String eaMaintainerCn = (String) dataMap.get("EAMAINTAINER_COUNT");
        dataMap.put("$EAMAINTAINER_CNT$", eaMaintainerCn);
        dataMap.remove("EAMAINTAINER_COUNT");
        
        // SAFETYADMIN_JSON 安全管理人员信息json
        String staffAdminJson = (String) dataMap.get("SAFETYADMIN_JSON");
        if (StringUtils.isEmpty(staffAdminJson)) {
            staffAdminJson = "[{\"staffName\":\"\",\"staffBirthDate\":\"\",\"staffSpecialty\":\"\","
                    + "\"staffJobtitle\":\"\",\"staffWorktype\":\"\",\"staffPosition\":\"\",\"staffRemark\":\"\","
                    + "\"staffSex\":\"\",\"staffEdu\":\"\",\"saveStaffIndex\":\"\"}]";
        }
        List<Map<String, Object>> staffAdminList = JSON.parseObject(staffAdminJson, List.class);
        for (int i = 0, max = staffAdminList.size(); i < max; i++) {
            Map<String, Object> adminStaff = staffAdminList.get(i);
            String index = adminStaff.get("index").toString();
            if (StringUtils.isNotEmpty(index)&& adminStaff.get("saveStaffIndex") == null) {
                adminStaff.put("saveStaffIndex", index);
                if(adminStaff.get("staffSex").equals("1")){
                    adminStaff.put("staffSex", "男");
                }else{
                    adminStaff.put("staffSex", "女");
                }

                String dgree = dictionaryService.get("degree", adminStaff.get("staffEdu").toString()).get("DIC_NAME")
                        .toString();
                adminStaff.put("staffEdu", dgree);
            }
        }
        dataMap.put("staffAdminList", staffAdminList);
    }
    
    /**
     * radio类型数据特殊处理
     * @author Phil He
     * @created 2017-9-22 上午11:37:04
     * @param dataMap
     * @param jsonField
     * @param radioField
     * @param indexField
     * @param listName
     */
    private void initRadioParam(Map<String,Object> dataMap,String jsonField,String radioField,String indexField,
            String listName){
        String dataJson = (String) dataMap.get(jsonField);
        List<Map<String, Object>> dataList = JSON.parseObject(dataJson, List.class);
        for (int i = 0, max = dataList.size(); i < max; i++) {
            Map<String, Object> data = dataList.get(i);
            data.put(radioField+"00", "");
            data.put(radioField+"01", "");
            String radioData = (String) data.get(radioField);
            if ("0".equals(radioData)) {
                data.put(radioField+"00", "√");
            } else if ("1".equals(radioData)) {
                data.put(radioField+"01", "√");
            }
            if (data.get(indexField) == null) {
                data.put(indexField, i + 1 + "");
            }
            data.remove(radioField);
        }
        dataMap.put(listName, dataList);
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
            String signDate = param.get("CREATE_TIME").toString().substring(0,10);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            param.put("SIGN_DATE", (new SimpleDateFormat("yyyy年mm月dd日")).format(formatter.parse(signDate)));
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                WordReplaceUtil.processParagraphs(paragraphList, param, doc);
                // 处理表格
                List<Map<String, Object>> leaderList = (List<Map<String, Object>>) param.get("leaderList");
                List<Map<String, Object>> techLeaderList = (List<Map<String, Object>>) param.get("techLeaderList");
                List<Map<String, Object>> qaPersonList = (List<Map<String, Object>>) param.get("qaPersonList");
                List<Map<String, Object>> staffList = (List<Map<String, Object>>) param.get("staffList");
                List<Map<String, Object>> instrumentList = (List<Map<String, Object>>) param.get("instrumentList");
                List<Map<String, Object>> dedicateList = (List<Map<String, Object>>) param.get("dedicateList");
                List<Map<String, Object>> inspectList = (List<Map<String, Object>>) param.get("inspectList");
                List<Map<String, Object>> commList = (List<Map<String, Object>>) param.get("commList");
                List<Map<String, Object>> specDeviceList = (List<Map<String, Object>>) param.get("specDeviceList");
                List<Map<String, Object>> staffAdminList = (List<Map<String, Object>>) param.get("staffAdminList");

                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    // table.addRow(rows.get(0));
                    XWPFTableRow oldRow = null;

                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, leaderList, "leaderHeader");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, techLeaderList, "techLeaderHeader");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, qaPersonList, "qaLeaderHeader");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, staffList, "staffIndex");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, instrumentList, "CHARTDEVICE_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, dedicateList, "SPECDEVICE_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, inspectList, "CHECKDEVICE_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, commList, "COMMDEVICE_INDEX");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, specDeviceList, "specIndex");
                    WordReplaceUtil.addTableRow2(param, table, rows, oldRow, staffAdminList, "saveStaffIndex");
                    
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            
                            WordReplaceUtil
                                    .setTableRow(param, doc, cells, paragraphListTable, leaderList, "leaderHeader");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, techLeaderList,
                                    "techLeaderHeader");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, qaPersonList,
                                    "qaLeaderHeader");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, staffList, "staffIndex");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, instrumentList,
                                    "CHARTDEVICE_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, dedicateList,
                                    "SPECDEVICE_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, inspectList,
                                    "CHECKDEVICE_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, commList,
                                    "COMMDEVICE_INDEX");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, specDeviceList,
                                    "specIndex");
                            WordReplaceUtil.setTableRow(param, doc, cells, paragraphListTable, staffAdminList,
                                    "saveStaffIndex");
                            
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
