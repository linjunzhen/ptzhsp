/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.star.corba.ObjectKey;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.bsfw.service.ZftzService;
import net.evecom.platform.ems.waybillone.BASE64Encoder;
import net.evecom.platform.ems.waybillone.HttpUtil;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.dao.FlowEventDao;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述 流程事件操作service
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings("rawtypes")
@Service("flowEventService")
public class FlowEventServiceImpl extends BaseServiceImpl implements FlowEventService {
    /**
     * logger
     */
    private static Log logger = LogFactory.getLog(FlowEventServiceImpl.class);
    /**
     * 前置事件
     */
    public static final String EVENTTYPE_START = "1";
    /**
     * 缺省业务存储事件
     */
    public static final String EVENTTYPE_BUS = "2";
    /**
     * 后置事件
     */
    public static final String EVENTTYPE_END = "3";

    /**
     * 所引入的dao
     */
    @Resource
    private FlowEventDao dao;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * fileAttachService
     */
    @Resource
    private ZftzService zftzService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * encryptionService
     */
    @Resource
    private ExeDataService exeDataService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Flex Hu
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
     * 描述 保存或者更新节点的事件配置
     * 
     * @author Flex Hu
     * @created 2015年8月12日 下午4:40:30
     * @param nodeDataArray
     * @param defId
     */
    @SuppressWarnings("unchecked")
    public void saveOrUpdate(JSONArray nodeDataArray, String defId, int flowVersion, int oldVersion) {
        for (int i = 0; i < nodeDataArray.size(); i++) {
            Map<String, Object> nodeData = (Map<String, Object>) nodeDataArray.get(i);
            // 获取节点名称
            String nodeName = (String) nodeData.get("text");
            nodeName = nodeName.trim();
            // 获取节点类型
            String nodeType = (String) nodeData.get("nodeType");
            if (nodeType.equals(Jbpm6Constants.START_NODE) || nodeType.equals(Jbpm6Constants.TASK_NODE)) {
                // 判断是否存在记录
                boolean isExist = dao.isExists(defId, EVENTTYPE_BUS, nodeName, flowVersion);
                if (!isExist) {
                    // 进行缺省事件的保存
                    Map<String, Object> jbpmEvent = new HashMap<String, Object>();
                    jbpmEvent.put("EVENT_TYPE", EVENTTYPE_BUS);
                    jbpmEvent.put("EVENT_CODE", Jbpm6Constants.DEFAULT_BUS_EVENT);
                    jbpmEvent.put("DEF_ID", defId);
                    jbpmEvent.put("NODE_NAME", nodeName);
                    jbpmEvent.put("DEF_VERSION", flowVersion);
                    Map<String, Object> oldEvent = dao.getByJdbc("JBPM6_EVENT",
                            new String[] { "DEF_ID", "NODE_NAME", "DEF_VERSION" },
                            new Object[] { defId, nodeName, oldVersion });
                    if (oldEvent != null) {
                        jbpmEvent.put("EVENT_TYPE", oldEvent.get("EVENT_TYPE"));
                        jbpmEvent.put("EVENT_CODE", oldEvent.get("EVENT_CODE"));
                    }
                    dao.saveOrUpdate(jbpmEvent, "JBPM6_EVENT", null);
                }
                // 保存旧开始和结束事件
                saveOldEvent(defId, flowVersion, oldVersion, nodeName);
            } else {
                Map<String, Object> oldEvent = dao.getByJdbc("JBPM6_EVENT",
                        new String[] { "DEF_ID", "NODE_NAME", "DEF_VERSION" },
                        new Object[] { defId, nodeName, oldVersion });
                if (oldEvent != null) {
                    Map<String, Object> jbpmEvent = oldEvent;
                    jbpmEvent.put("EVENT_ID", "");
                    jbpmEvent.put("DEF_VERSION", flowVersion);
                    dao.saveOrUpdate(jbpmEvent, "JBPM6_EVENT", null);
                }
            }
        }
    }

    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年12月25日 上午11:00:05
     * @param defId
     * @param flowVersion
     * @param oldVersion
     * @param nodeName
     */
    @SuppressWarnings("unchecked")
    private void saveOldEvent(String defId, int flowVersion, int oldVersion, String nodeName) {
        Map<String, Object> oldStartEvent = dao.getByJdbc("JBPM6_EVENT",
                new String[] { "DEF_ID", "NODE_NAME", "DEF_VERSION", "EVENT_TYPE" },
                new Object[] { defId, nodeName, oldVersion, EVENTTYPE_START });
        if (oldStartEvent != null) {
            Map<String, Object> jbpmEvent = oldStartEvent;
            jbpmEvent.put("EVENT_ID", "");
            jbpmEvent.put("DEF_VERSION", flowVersion);
            dao.saveOrUpdate(jbpmEvent, "JBPM6_EVENT", null);
        }
        Map<String, Object> oldEndEvent = dao.getByJdbc("JBPM6_EVENT",
                new String[] { "DEF_ID", "NODE_NAME", "DEF_VERSION", "EVENT_TYPE" },
                new Object[] { defId, nodeName, oldVersion, EVENTTYPE_END });
        if (oldEndEvent != null) {
            Map<String, Object> jbpmEvent = oldEndEvent;
            jbpmEvent.put("EVENT_ID", "");
            jbpmEvent.put("DEF_VERSION", flowVersion);
            dao.saveOrUpdate(jbpmEvent, "JBPM6_EVENT", null);
        }
    }

    /**
     * 
     * 描述 存储业务数据
     * 
     * @author Flex Hu
     * @created 2015年8月12日 下午4:52:20
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        // 获取上传的附件ID
        String fileIds = (String) flowDatas.get("EFLOW_FILEATTACHIDS");
        if (StringUtils.isNotEmpty(fileIds)) {
            // 进行附件业务表记录ID的更新
            fileAttachService.updateTableName(fileIds, busRecordId);
        }
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            fileAttachService.saveItemMaterFiles(submitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        // 保存产业奖补信息
        setCyjbInfo(flowDatas, busRecordId);
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);

        // 商事多证合一
        if (busTableName.contains("T_COMMERCIAL_")) {
            this.remove("T_COMMERCIAL_MULTIPLE", "COMPANY_ID", new Object[] { busRecordId });
            Map<String, Object> multipleMap = new HashMap<String, Object>();
            multipleMap.putAll(flowDatas);
            multipleMap.put("COMPANY_ID", busRecordId);
            this.saveOrUpdate(multipleMap, "T_COMMERCIAL_MULTIPLE", null);
        }

        return flowDatas;
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年10月12日 下午5:37:48
     * @param flowDatas
     * @param busRecordId
     */
    @SuppressWarnings({ "unchecked" })
    private void setCyjbInfo(Map<String, Object> flowDatas, String busRecordId) {
        String CyjbJson = (String) flowDatas.get("CyjbJson");
        if (StringUtils.isNotEmpty(CyjbJson)) {
            // 保存信息前，先删除
            dao.remove("T_BSFW_PTJ_CYJB", new String[] { "YW_ID" }, new Object[] { busRecordId });
            List<Map> cyjbList = JSON.parseArray(CyjbJson, Map.class);
            for (int i = 0; i < cyjbList.size(); i++) {
                Map<String, Object> productCpxxMap = cyjbList.get(i);
                productCpxxMap.put("YW_ID", busRecordId);
                dao.saveOrUpdate(productCpxxMap, "T_BSFW_PTJ_CYJB", "");
            }
        }
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* FROM JBPM6_EVENT T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 保存事件配置信息
     * 
     * @author Flex Hu
     * @created 2015年8月13日 上午9:59:37
     * @param defId
     * @param nodeName
     * @param eventJson
     */
    @SuppressWarnings({ "unchecked" })
    public void saveEvent(String defId, String nodeName, String eventJson, int flowVersion) {
        // 先清除掉数据
        dao.deleteEvent(defId, nodeName, flowVersion);
        List<Map> eventList = JSON.parseArray(eventJson, Map.class);
        for (Map<String, Object> event : eventList) {
            event.put("DEF_ID", defId);
            event.put("NODE_NAME", nodeName);
            event.put("DEF_VERSION", flowVersion);
            dao.saveOrUpdate(event, "JBPM6_EVENT", null);
        }
    }

    // /**
    // *
    // * 描述 获取事件配置代码
    // *
    // * @author Flex Hu
    // * @created 2015年8月18日 上午11:21:25
    // * @param defId
    // * @param nodeName
    // * @param eventType
    // * @return
    // */
    // 2020年4月27日Adrian Bian 注掉
    // public String getEventCode(String defId, String nodeName, String
    // eventType, int flowVersion) {
    // return dao.getEventCode(defId, nodeName, eventType, flowVersion);
    // }

    /**
     * 
     * 描述 删除数据
     * 
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId, int flowVersion) {
        dao.deleteByDefIdAndVersion(defId, flowVersion);
    }

    /**
     * 
     * 描述 拷贝事件数据
     * 
     * @author Flex Hu
     * @created 2016年3月31日 上午11:10:49
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyEvents(String targetDefId, int targetFlowVersion, String newDefId) {
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_EVENT(EVENT_ID,EVENT_TYPE,");
        sql.append("EVENT_CODE,DEF_ID,NODE_NAME,EVENT_STATUS,DEF_VERSION");
        sql.append(") SELECT RAWTOHEX(SYS_GUID()),BR.EVENT_TYPE,BR.EVENT_CODE,");
        sql.append("?,BR.NODE_NAME,BR.EVENT_STATUS,BR.DEF_VERSION");
        sql.append(" FROM JBPM6_EVENT BR WHERE BR.DEF_ID=? ");
        sql.append(" AND BR.DEF_VERSION=? ");
        dao.executeSql(sql.toString(), new Object[] { newDefId, targetDefId, targetFlowVersion });
    }

    /**
     * 
     * 描述 存储业务数据
     * 
     * @author Flex Hu
     * @created 2015年8月12日 下午4:52:20
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveTzxmZTBBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        // 进行招投标流程业务子表数据保存操作
        zftzService.saveOrUpdateZTBDateItems(flowDatas, busRecordId);
        // 获取上传的附件ID
        String fileIds = (String) flowDatas.get("EFLOW_FILEATTACHIDS");
        if (StringUtils.isNotEmpty(fileIds)) {
            // 进行附件业务表记录ID的更新
            fileAttachService.updateTableName(fileIds, busRecordId);
        }
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            fileAttachService.saveItemMaterFiles(submitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }

    /**
     *
     * 描述 生成社会保险登记表
     * 
     * @created 2019年3月4日 上午9:59:37
     */
    @SuppressWarnings("unchecked")
    public void createSocialForm(String exeId) {
        Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        // 获取业务表名称
        String busTableName = (String) execution.get("BUS_TABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) execution.get("BUS_RECORDID");
        Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
        // 获取结果信息
        Map<String, Object> flowResult = null;
        flowResult = executionService.getByJdbc("JBPM6_FLOW_RESULT", new String[] { "EXE_ID" }, new Object[] { exeId });
        if (flowResult != null) {
            String resultId = String.valueOf(flowResult.get("RESULT_ID"));
            busRecord.putAll(flowResult);
            setSpecialValue(busRecord,exeId);
            createSocialPdf(busRecord, resultId);
        }
    }

    /**
     * 描述:社会保险登记表数据回填
     *
     * @author Madison You
     * @created 2020/11/11 15:25:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> shbxdjbTableData(Map<String, Map<String, Object>> args) {
        Map<String, Object> exeMap = args.get("exeMap");
        Map<String, Object> busMap = args.get("busMap");
        exeMap.putAll(busMap);
        String exeId = StringUtil.getValue(exeMap, "EXE_ID");
        FlowData flowData = new FlowData(exeId, FlowData.RESULT_MAP);
        Map<String, Object> resultMap = flowData.getResultMap();
        if (resultMap != null) {
            exeMap.putAll(resultMap);
        }
        Map<String, Object> returnMap = exeMap;
        setSpecialValue(returnMap,exeId);
        return returnMap;
    }


    /**
     * 描述:养老工伤保险申请表数据回填
     *
     * @author Madison You
     * @created 2020/11/11 10:59:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> ylgsbxsqbTableData(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String, Object> busMap = args.get("busMap");
        returnMap.put("dwbh", "");
        returnMap.put("dwmc", StringUtil.getValue(busMap, "COMPANY_NAME"));
        returnMap.put("tyshxydm", StringUtil.getValue(busMap, "SOCIAL_CREDITUNICODE"));
        returnMap.put("wbjbrxm", StringUtil.getValue(busMap, "INSURANCE_OPERRATOR"));
        returnMap.put("wbjbrsfzh", StringUtil.getValue(busMap, "INSURANCE_IDNO"));
        returnMap.put("wbjbrsjhm", StringUtil.getValue(busMap, "INSURANCE_PHONE"));
        returnMap.put("date", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return returnMap;
    }


    /**
     * 创建社会登记表
     * 
     * @param busRecord
     * @param resultId
     */
    @SuppressWarnings("unchecked")
    private void createSocialPdf(Map<String, Object> busRecord, String resultId) {
        // fileRullPath=attachFiles/applymater/DATE_20190222/shdjb.docx
        Properties properties = FileUtil.readProperties("project.properties");
        String attachmentFilePath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        // 模板路径
        String socialRelPath = dictionaryService.getDicCode("wxConfig", "socialRelPath");
        String filefullPath = attachmentFilePath + socialRelPath;
        // 建立全路径目录和临时目录
        // newWordFilePath
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        // 生成word的路径
        String wordFilePath = properties.getProperty("PdfFilePath").replace("\\", "/");
        File wordPathFolder = new File(wordFilePath + uploadFullPath);
        if (!wordPathFolder.exists()) {
            wordPathFolder.mkdirs();
        }
        String newWordFilePath = wordFilePath + uploadFullPath + "/" + uuId + ".doc";
        // 替换模版字段,生成word
        WordReplaceUtil.replaceWord(busRecord, filefullPath, newWordFilePath);
        String pdfFilePath = wordFilePath + uploadFullPath + "/" + uuId + ".pdf";
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);

        // 保存附件信息
        String app_id = "0001";// 分配的用户名
        String password = "bingo666";// 分配的密码
        String responesCode = "UTF-8";// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "402881e65127d9ea015127d9ea830000");// 上传人ID
        param.put("uploaderName", "系统自动上传"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        param.put("name", "社会保险登记表.pdf");// 上传附件名
        param.put("busTableName", "JBPM6_FLOW_RESULT");// 业务表名
        param.put("busRecordId", resultId);// 业务表ID

        // 文件服务器地址
        String url = attachmentFilePath + "upload/file";// 上传路径
        String resultMsg = HttpRequestUtil.sendFilePost(url, pdfFilePath, responesCode, app_id, password, param);
        Map<String, Object> result = JSON.parseObject(resultMsg, Map.class);
        Map<String, Object> data = (Map<String, Object>) result.get("data");
        String fileId = data.get("fileId").toString();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SOCIAL_FILEID", fileId);
        dao.saveOrUpdate(map, "JBPM6_FLOW_RESULT", resultId);
    }

    /**
     * 社会保险登记表设置特殊值
     * 
     * @param busRecord
     * @return
     */
    private void setSpecialValue(Map<String, Object> busRecord,String exeId) {
        // 公司类型
        getDicName(busRecord, "yxzrgssl", "COMPANY_SETNATURE");
        // 经营年限
        String isLongTerm = String.valueOf(busRecord.get("ISLONG_TERM"));
        if ("1".equals(isLongTerm)) {
            busRecord.put("EFFECTYEAR", "长期");
        } else {
            String closeTime = String.valueOf(busRecord.get("CLOSE_TIME"));
            int endTime = Integer.parseInt(closeTime.substring(0, 4));
            String effectTime = String.valueOf(busRecord.get("EFFECT_TIME"));
            int beginTime = Integer.parseInt(effectTime.substring(0, 4));
            busRecord.put("EFFECTYEAR", endTime - beginTime);
        }
        // 法人信息
        getDicName(busRecord, "DocumentType", "LEGAL_IDTYPE");

        // 合伙人委托人
        busRecord.putAll(exeDataService.getHhLegal(exeId));
        busRecord.put("LEGAL_IDTYPE","身份证");
        // 险种
        String insuranceType = dictionaryService.getDicNames("insuranceType",
                String.valueOf(busRecord.get("INSURANCE_TYPE")));
        if (StringUtils.isNotEmpty(insuranceType)) {
            for (int i = 0; i < 10; i++) {
                busRecord.put("INSURANCENAME" + i, "");
            }
            String[] insuranceTypes = insuranceType.split(",");
            for (int i = 0; i < insuranceTypes.length; i++) {
                busRecord.put("INSURANCENAME" + i, insuranceTypes[i]);
            }
        }
        // 注册地址
        String registerAddr = busRecord.get("REGISTER_ADDR") == null ? "" : busRecord.get("REGISTER_ADDR").toString();
        if (StringUtils.isEmpty(registerAddr)) {
            busRecord.put("REGISTER_ADDR", String.valueOf(busRecord.get("BUSINESS_PLACE")));
        }
        busRecord.put("INSURANCEPHONUM", String.valueOf(busRecord.get("INSURANCE_PHONE")));
        // 联系电话
        String phone = busRecord.get("CONTACT_PHONE") == null ? "" : busRecord.get("CONTACT_PHONE").toString();
        busRecord.put("CONTACTPHONUM", phone);
        if (StringUtils.isEmpty(phone)) {
            busRecord.put("CONTACTPHONUM", busRecord.get("PHONE")==null ? "" : busRecord.get("PHONE").toString());
        }
        // 公司名称
        String companyName = busRecord.get("COMPANY_NAME") == null ? "" : busRecord.get("COMPANY_NAME").toString();
        if (StringUtils.isEmpty(companyName)) {
            busRecord.put("COMPANY_NAME", String.valueOf(busRecord.get("BRANCH_NAME")));
        }
    }

    /**
     * 描述
     *
     * @author Rider Chen
     * @created 2016年12月15日 下午2:51:47
     * @param map
     * @param typeCode
     * @param name
     */
    public void getDicName(Map<String, Object> map, String typeCode, String name) {
        String value = map.get(name) == null ? "" : map.get(name).toString();
        map.put(name, dictionaryService.getDicNames(typeCode, value));
    }

    /**
     * 根据主键id和tableName获取业务表数据
     * 
     * @param busRecordId
     * @param tableName
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getBuscordByIdAndTableName(String busRecordId, String busTableName) {
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        String busPkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);// 获取业务表主键名称
        // 获取业务记录
        Map<String, Object> busRecord = executionService.getByJdbc(busTableName, new String[] { busPkName },
                new Object[] { busRecordId });
        return busRecord;
    }

    /**
     * 
     * 描述 存储商事业务数据
     * 
     * @author Danto Huang
     * @created 2016-11-15 下午3:29:44
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveCommercialBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        // 获取上传的附件ID
        String fileIds = (String) flowDatas.get("EFLOW_FILEATTACHIDS");
        if (StringUtils.isNotEmpty(fileIds)) {
            // 进行附件业务表记录ID的更新
            fileAttachService.updateTableName(fileIds, busRecordId);
        }
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            fileAttachService.saveItemMaterFiles(submitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        // 多证合一
        this.remove("T_COMMERCIAL_MULTIPLE", "COMPANY_ID", new Object[] { busRecordId });
        Map<String, Object> multipleMap = new HashMap<String, Object>();
        multipleMap.putAll(flowDatas);
        multipleMap.put("COMPANY_ID", busRecordId);
        this.saveOrUpdate(multipleMap, "T_COMMERCIAL_MULTIPLE", null);
        encryptionService.mapDecrypt(flowDatas, busTableName);
        // 进行业务子表数据保存操作
        this.saveOrUpdateCommercialItems(flowDatas, busRecordId);

        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }

    /**
     * 
     * 描述
     * 
     * @author Danto Huang
     * @created 2016-11-15 下午3:33:14
     * @param flowDatas
     * @param busRecordId
     */
    @SuppressWarnings({ "unchecked" })
    private void saveOrUpdateCommercialItems(Map<String, Object> flowDatas, String busRecordId) {
        // 保存股东信息，先删除后保存
        String holderJson = null == flowDatas.get("HOLDER_JSON") ? "" : flowDatas.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(holderJson)) {
            List<Map> holders = JSON.parseArray(holderJson, Map.class);
            this.remove("T_COMMERCIAL_SHAREHOLDER", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> holder : holders) {
                holder.put("COMPANY_ID", busRecordId);
                this.saveOrUpdate(holder, "T_COMMERCIAL_SHAREHOLDER", null);
            }

        }
        // 保存董事信息，先删除后保存
        String directorJson = null == flowDatas.get("DIRECTOR_JSON") ? "" : flowDatas.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(directorJson)) {
            List<Map> directors = JSON.parseArray(directorJson, Map.class);
            this.remove("T_COMMERCIAL_DIRECTOR", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> director : directors) {
                director.put("COMPANY_ID", busRecordId);
                this.saveOrUpdate(director, "T_COMMERCIAL_DIRECTOR", null);
            }
        }

        // 保存监事信息，先删除后保存
        String supervisorJson = null == flowDatas.get("SUPERVISOR_JSON") ? ""
                : flowDatas.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(supervisorJson)) {
            List<Map> supervisors = JSON.parseArray(supervisorJson, Map.class);
            this.remove("T_COMMERCIAL_SUPERVISOR", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> supervisor : supervisors) {
                supervisor.put("COMPANY_ID", busRecordId);
                this.saveOrUpdate(supervisor, "T_COMMERCIAL_SUPERVISOR", null);
            }
        }

        // 保存经理信息，先删除后保存
        String managerJson = null == flowDatas.get("MANAGER_JSON") ? "" : flowDatas.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(managerJson)) {
            List<Map> managers = JSON.parseArray(managerJson, Map.class);
            this.remove("T_COMMERCIAL_MANAGER", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> manager : managers) {
                manager.put("COMPANY_ID", busRecordId);
                this.saveOrUpdate(manager, "T_COMMERCIAL_MANAGER", null);
            }
        }

        // 保存投资者信息，先删除后保存
        String investChineseJson = null == flowDatas.get("DOMESTICINVESTOR_JSON") ? ""
                : flowDatas.get("DOMESTICINVESTOR_JSON").toString();
        if (StringUtils.isNotEmpty(investChineseJson)) {
            List<Map> invests = JSON.parseArray(investChineseJson, Map.class);
            this.remove("T_COMMERCIAL_INVESTOR_DOMESTIC", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> invest : invests) {
                invest.put("COMPANY_ID", busRecordId);
                String investorId = this.saveOrUpdate(invest, "T_COMMERCIAL_INVESTOR_DOMESTIC", null);
                String zftzzsjkzrJson = invest.get("ZFTZZSJKZR_JSON") == null ? ""
                        : invest.get("ZFTZZSJKZR_JSON").toString();
                if (StringUtils.isNotEmpty(zftzzsjkzrJson)) {
                    List<Map> zftzzsjkzr = JSON.parseArray(zftzzsjkzrJson, Map.class);
                    this.remove("T_COMMERCIAL_INVESTOR_CONTROL", "INVESTOR_ID", new Object[] { investorId });
                    for (Map<String, Object> map : zftzzsjkzr) {
                        map.put("INVESTOR_ID", investorId);
                        this.saveOrUpdate(map, "T_COMMERCIAL_INVESTOR_CONTROL", null);
                    }
                }
            }

        }

        String investForeignJson = null == flowDatas.get("FOREIGNINVESTOR_JSON") ? ""
                : flowDatas.get("FOREIGNINVESTOR_JSON").toString();
        if (StringUtils.isNotEmpty(investForeignJson)) {
            List<Map> invests = JSON.parseArray(investForeignJson, Map.class);
            this.remove("T_COMMERCIAL_INVESTOR_FOREIGN", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> invest : invests) {
                invest.put("COMPANY_ID", busRecordId);
                String investorId = this.saveOrUpdate(invest, "T_COMMERCIAL_INVESTOR_FOREIGN", null);
                String wftzzsjkzrJson = invest.get("WFTZZSJKZR_JSON") == null ? ""
                        : invest.get("WFTZZSJKZR_JSON").toString();
                if (StringUtils.isNotEmpty(wftzzsjkzrJson)) {
                    List<Map> wftzzsjkzr = JSON.parseArray(wftzzsjkzrJson, Map.class);
                    this.remove("T_COMMERCIAL_INVESTOR_CONTROL", "INVESTOR_ID", new Object[] { investorId });
                    for (Map<String, Object> map : wftzzsjkzr) {
                        map.put("INVESTOR_ID", investorId);
                        this.saveOrUpdate(map, "T_COMMERCIAL_INVESTOR_CONTROL", null);
                    }
                }
            }

        }

        String attorneyJson = null == flowDatas.get("ATTORNEY_JSON") ? "" : flowDatas.get("ATTORNEY_JSON").toString();
        if (StringUtils.isNotEmpty(attorneyJson)) {
            List<Map> attorneys = JSON.parseArray(attorneyJson, Map.class);
            this.remove("T_COMMERCIAL_ATTORNEY", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> attorney : attorneys) {
                attorney.put("COMPANY_ID", busRecordId);
                this.saveOrUpdate(attorney, "T_COMMERCIAL_ATTORNEY", null);
            }

        }

        String controllerJson = null == flowDatas.get("CONTROLLER_JSON") ? ""
                : flowDatas.get("CONTROLLER_JSON").toString();
        if (StringUtils.isNotEmpty(controllerJson)) {
            List<Map> controllers = JSON.parseArray(controllerJson, Map.class);
            this.remove("T_COMMERCIAL_CONTROLLER", "COMPANY_ID", new Object[] { busRecordId });
            for (Map<String, Object> controller : controllers) {
                controller.put("COMPANY_ID", busRecordId);
                this.saveOrUpdate(controller, "T_COMMERCIAL_CONTROLLER", null);
            }

        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> sendMatEMSInfo(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        String matSendtype = flowDatas.get("busRecord[MAT_SENDTYPE]") == null ? ""
                : flowDatas.get("busRecord[MAT_SENDTYPE]").toString();
        if ("02".equals(matSendtype)) {
            String itemCode = flowDatas.get("ITEM_CODE") == null ? "" : flowDatas.get("ITEM_CODE").toString();
            Map<String, Object> itemInfo = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });
            String MAT_SEND_ADDRESSEE = flowDatas.get("busRecord[MAT_SEND_ADDRESSEE]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_ADDRESSEE]").toString();
            String MAT_SEND_ADDR = flowDatas.get("busRecord[MAT_SEND_ADDR]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_ADDR]").toString();
            String MAT_SEND_MOBILE = flowDatas.get("busRecord[MAT_SEND_MOBILE]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_MOBILE]").toString();
            String MAT_SEND_PROV = flowDatas.get("busRecord[MAT_SEND_PROV]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_PROV]").toString();
            String MAT_SEND_CITY = flowDatas.get("busRecord[MAT_SEND_CITY]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_CITY]").toString();
            String MAT_SEND_COUNTY = flowDatas.get("busRecord[MAT_SEND_COUNTY]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_COUNTY]").toString();
            String MAT_SEND_POSTCODE = flowDatas.get("busRecord[MAT_SEND_POSTCODE]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_POSTCODE]").toString();
            String MAT_SEND_REMARKS = flowDatas.get("busRecord[MAT_SEND_REMARKS]") == null ? ""
                    : flowDatas.get("busRecord[MAT_SEND_REMARKS]").toString();
            String path = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "path");
            String appkey = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "appkey");
            String appsecret = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "appsecret");
            String method = "ems.inland.waybill.got";
            String authorization = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "authorization");
            String txLogisticID = "MAT"
                    + (flowDatas.get("busRecord[EXE_ID]") == null ? "" : flowDatas.get("busRecord[EXE_ID]").toString());
            String itemAddr = itemInfo.get("ITEM_SEND_ADDR") == null ? "" : itemInfo.get("ITEM_SEND_ADDR").toString();
            String orderType = "1";
            String itemName = itemInfo.get("ITEM_SEND_ADDRESSEE") == null ? ""
                    : itemInfo.get("ITEM_SEND_ADDRESSEE").toString();
            String itemPostCode = itemInfo.get("ITEM_SEND_POSTCODE") == null ? ""
                    : itemInfo.get("ITEM_SEND_POSTCODE").toString();
            String itemMobile = itemInfo.get("ITEM_SEND_MOBILE") == null ? ""
                    : itemInfo.get("ITEM_SEND_MOBILE").toString();
            // 省
            String itemProv = itemInfo.get("ITEM_SEND_PROV") == null ? "" : itemInfo.get("ITEM_SEND_PROV").toString();
            String itemCity = itemInfo.get("ITEM_SEND_CITY") == null ? "" : itemInfo.get("ITEM_SEND_CITY").toString();
            String itemCounty = itemInfo.get("ITEM_SEND_COUNTY") == null ? ""
                    : itemInfo.get("ITEM_SEND_COUNTY").toString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            String timestamp = df.format(new Date());
            Map<String, String> map = new HashMap<String, String>();
            map.put("timestamp", timestamp);
            map.put("app_key", appkey);
            map.put("authorization", authorization);
            map.put("method", method);
            map.put("format", "json");
            map.put("version", "V3.01");
            map.put("size", "1");
            map.put("charset", "GBK");
            Map<String, Object> gotInfo = new HashMap<String, Object>();
            gotInfo.put("txLogisticID", txLogisticID);
            gotInfo.put("orderType", orderType);
            gotInfo.put("remark", MAT_SEND_REMARKS);
            Map<String, Object> collect = new HashMap<String, Object>();
            collect.put("address", MAT_SEND_ADDR);
            collect.put("city", MAT_SEND_CITY);
            collect.put("mobile", MAT_SEND_MOBILE);
            collect.put("name", MAT_SEND_ADDRESSEE);
            collect.put("phone", MAT_SEND_MOBILE);
            collect.put("postCode", MAT_SEND_POSTCODE);
            collect.put("prov", MAT_SEND_PROV);
            collect.put("county", MAT_SEND_COUNTY);
            JSONObject collectJSONObj = JSONObject.parseObject(JSON.toJSONString(collect));
            gotInfo.put("collect", collectJSONObj);

            Map<String, Object> receiver = new HashMap<String, Object>();
            receiver.put("address", itemAddr);
            receiver.put("city", itemCity);
            receiver.put("mobile", itemMobile);
            receiver.put("name", itemName);
            receiver.put("phone", itemMobile);
            receiver.put("postCode", itemPostCode);
            receiver.put("prov", itemProv);
            receiver.put("county", itemCounty);
            JSONObject receiverJSONObj = JSONObject.parseObject(JSON.toJSONString(receiver));
            gotInfo.put("receiver", receiverJSONObj);

            Map<String, Object> sender = new HashMap<String, Object>();
            sender.put("address", MAT_SEND_ADDR);
            sender.put("city", MAT_SEND_CITY);
            sender.put("mobile", MAT_SEND_MOBILE);
            sender.put("name", MAT_SEND_ADDRESSEE);
            sender.put("phone", MAT_SEND_MOBILE);
            sender.put("postCode", MAT_SEND_POSTCODE);
            sender.put("prov", MAT_SEND_PROV);
            sender.put("county", MAT_SEND_COUNTY);
            JSONObject senderJSONObj = JSONObject.parseObject(JSON.toJSONString(sender));
            gotInfo.put("sender", senderJSONObj);

            JSONObject gotInfoJSONObj = JSONObject.parseObject(JSON.toJSONString(gotInfo));
            map.put("gotInfo", gotInfoJSONObj.toString());
            String content = getSortParams(map) + appsecret;// 排序
            String sign = sign(content, "GBK");// 加密
            map.put("sign", sign);
            String body = toKey(map);
            String reslut = "";
            String contentType = "application/x-www-form-urlencoded;charset=GBK";// post请求
            String charset = "GBK";// 编码
            try {
                reslut = HttpUtil.sendHttpPost(path, body, charset, null, contentType);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                logger.info(e);
            }
            Map<String, Object> EMSInfo = new HashMap<String, Object>();
            EMSInfo.put("TXLOGISTICID", txLogisticID);
            EMSInfo.put("BODY", body);
            EMSInfo.put("SENDMSG", reslut);
            dao.saveOrUpdate(EMSInfo, "T_BSFW_EMSINFO", null);
        }
        return flowDatas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> sendResultEMSInfo(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        String finishGettype = flowDatas.get("FINISH_GETTYPE") == null ? ""
                : flowDatas.get("FINISH_GETTYPE").toString();
        if ("02".equals(finishGettype)) {
            String itemCode = flowDatas.get("ITEM_CODE") == null ? "" : flowDatas.get("ITEM_CODE").toString();
            Map<String, Object> itemInfo = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });
            String RESULT_SEND_ADDRESSEE = flowDatas.get("RESULT_SEND_ADDRESSEE") == null ? ""
                    : flowDatas.get("RESULT_SEND_ADDRESSEE").toString();
            String RESULT_SEND_ADDR = flowDatas.get("RESULT_SEND_ADDR") == null ? ""
                    : flowDatas.get("RESULT_SEND_ADDR").toString();
            String RESULT_SEND_MOBILE = flowDatas.get("RESULT_SEND_MOBILE") == null ? ""
                    : flowDatas.get("RESULT_SEND_MOBILE").toString();
            String RESULT_SEND_PROV = flowDatas.get("RESULT_SEND_PROV") == null ? ""
                    : flowDatas.get("RESULT_SEND_PROV").toString();
            String RESULT_SEND_CITY = flowDatas.get("RESULT_SEND_CITY") == null ? ""
                    : flowDatas.get("RESULT_SEND_CITY").toString();
            String RESULT_SEND_COUNTY = flowDatas.get("RESULT_SEND_COUNTY") == null ? ""
                    : flowDatas.get("RESULT_SEND_COUNTY").toString();
            String RESULT_SEND_POSTCODE = flowDatas.get("RESULT_SEND_POSTCODE") == null ? ""
                    : flowDatas.get("RESULT_SEND_POSTCODE").toString();
            String RESULT_SEND_REMARKS = flowDatas.get("RESULT_SEND_REMARKS") == null ? ""
                    : flowDatas.get("RESULT_SEND_REMARKS").toString();
            String path = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "path");
            String appkey = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "appkey");
            String appsecret = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "appsecret");
            // -------------------------------------------
            String method = "ems.inland.waybill.got";
            String authorization = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "authorization");
            // -------------gotInfo根节点，gotInfo对象（XML节点名称gotInfo）
            String txLogisticID = "RES"
                    + (flowDatas.get("busRecord[EXE_ID]") == null ? "" : flowDatas.get("busRecord[EXE_ID]").toString());
            String itemAddr = itemInfo.get("ITEM_SEND_ADDR") == null ? "" : itemInfo.get("ITEM_SEND_ADDR").toString();
            // String sender = itemInfo.get("ITEM_SEND_ADDRESSEE")==null?
            // "":itemInfo.get("ITEM_SEND_ADDRESSEE").toString();
            // String receiver = flowDatas.get("RESULT_SEND_ADDR")==null?
            // "":flowDatas.get("RESULT_SEND_ADDR").toString();
            String orderType = "2";
            // String psegCode = itemInfo.get("ITEM_SEND_POSTCODE") == null ? ""
            // : itemInfo.get("ITEM_SEND_POSTCODE")
            // .toString();
            // --------------Address对象---------------item
            String itemName = itemInfo.get("ITEM_SEND_ADDRESSEE") == null ? ""
                    : itemInfo.get("ITEM_SEND_ADDRESSEE").toString();
            String itemPostCode = itemInfo.get("ITEM_SEND_POSTCODE") == null ? ""
                    : itemInfo.get("ITEM_SEND_POSTCODE").toString();
            String itemMobile = itemInfo.get("ITEM_SEND_MOBILE") == null ? ""
                    : itemInfo.get("ITEM_SEND_MOBILE").toString();
            // 省
            String itemProv = itemInfo.get("ITEM_SEND_PROV") == null ? "" : itemInfo.get("ITEM_SEND_PROV").toString();
            String itemCity = itemInfo.get("ITEM_SEND_CITY") == null ? "" : itemInfo.get("ITEM_SEND_CITY").toString();
            String itemCounty = itemInfo.get("ITEM_SEND_COUNTY") == null ? ""
                    : itemInfo.get("ITEM_SEND_COUNTY").toString();
            // String itemAddress = itemInfo.get("ITEM_SEND_ADDR") == null ? ""
            // : itemInfo.get("ITEM_SEND_ADDR")
            // .toString();
            // --------------
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            String timestamp = df.format(new Date());
            Map<String, String> map = new HashMap<String, String>();
            map.put("timestamp", timestamp);
            // map.put("timestamp", "2017-12-12 14:49:59");
            map.put("app_key", appkey);
            map.put("authorization", authorization);
            map.put("method", method);
            map.put("format", "json");
            map.put("version", "V3.01");
            map.put("size", "1");
            // map.put("charset", "UTF-8");
            map.put("charset", "GBK");
            // map.put("bizcode", "06");
            // map.put("count", "1");

            Map<String, Object> gotInfo = new HashMap<String, Object>();
            gotInfo.put("txLogisticID", txLogisticID);
            gotInfo.put("orderType", orderType);
            gotInfo.put("remark", RESULT_SEND_REMARKS);
            Map<String, Object> collect = new HashMap<String, Object>();
            collect.put("address", itemAddr);
            collect.put("city", itemCity);
            collect.put("mobile", itemMobile);
            collect.put("name", itemName);
            collect.put("phone", itemMobile);
            collect.put("postCode", itemPostCode);
            collect.put("prov", itemProv);
            collect.put("county", itemCounty);
            JSONObject collectJSONObj = JSONObject.parseObject(JSON.toJSONString(collect));
            gotInfo.put("collect", collectJSONObj);

            Map<String, Object> receiver = new HashMap<String, Object>();
            receiver.put("address", RESULT_SEND_ADDR);
            receiver.put("city", RESULT_SEND_CITY);
            receiver.put("mobile", RESULT_SEND_MOBILE);
            receiver.put("name", RESULT_SEND_ADDRESSEE);
            receiver.put("phone", RESULT_SEND_MOBILE);
            receiver.put("postCode", RESULT_SEND_POSTCODE);
            receiver.put("prov", RESULT_SEND_PROV);
            receiver.put("county", RESULT_SEND_COUNTY);
            JSONObject receiverJSONObj = JSONObject.parseObject(JSON.toJSONString(receiver));
            gotInfo.put("receiver", receiverJSONObj);

            Map<String, Object> sender = new HashMap<String, Object>();
            sender.put("address", itemAddr);
            sender.put("city", itemCity);
            sender.put("mobile", itemMobile);
            sender.put("name", itemName);
            sender.put("phone", itemMobile);
            sender.put("postCode", itemPostCode);
            sender.put("prov", itemProv);
            sender.put("county", itemCounty);
            JSONObject senderJSONObj = JSONObject.parseObject(JSON.toJSONString(sender));
            gotInfo.put("sender", senderJSONObj);

            JSONObject gotInfoJSONObj = JSONObject.parseObject(JSON.toJSONString(gotInfo));
            map.put("gotInfo", gotInfoJSONObj.toString());
            String content = getSortParams(map) + appsecret;// 排序
            // String sign = sign(content, "UTF-8");// 加密
            String sign = sign(content, "GBK");// 加密
            // System.out.println(sign);
            map.put("sign", sign);
            String body = toKey(map);
            // System.out.println("拼接的字符串>>>>>>>>>>>>>>>" + body);
            String reslut = "";
            // String contentType =
            // "application/x-www-form-urlencoded;charset=UTF-8";// post请求
            String contentType = "application/x-www-form-urlencoded;charset=GBK";// post请求
            // String charset = "UTF-8";// 编码
            String charset = "GBK";// 编码
            try {
                reslut = HttpUtil.sendHttpPost(path, body, charset, null, contentType);
                // System.out.println("返回的结果为>>>>>>" + reslut);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.info(e);
                // e.printStackTrace();
            }
            Map<String, Object> EMSInfo = new HashMap<String, Object>();
            EMSInfo.put("TXLOGISTICID", txLogisticID);
            EMSInfo.put("BODY", body);
            EMSInfo.put("SENDMSG", reslut);
            dao.saveOrUpdate(EMSInfo, "T_BSFW_EMSINFO", null);
        }
        return flowDatas;
    }

    public static String sign(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }
        String sign = "";
        try {
            content = new String(content.getBytes(), charset);
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");// MD5

            sign = BASE64Encoder.encode(md5.digest(content.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            logger.info(e);
            // e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            logger.info(e);
            // e.printStackTrace();
        }
        return sign;
    }

    public static String toKey(Map<String, String> params) {
        // String content = "";
        StringBuffer content = new StringBuffer("");
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        // 这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            //String value = params.get(key);
            // 将值为空的参数排除
            /*
             * if (!StringUtil.isNull(value)) { keyList.add(key); }
             */
            keyList.add(key);
        }

        // 将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if (i == keyList.size() - 1) {
                // content += key + "=" + params.get(key);
                content.append(key).append("=").append(params.get(key));
                return content.toString();
            }
            // content += key + "=" + params.get(key) + "&";
            content.append(key).append("=").append(params.get(key)).append("&");

        }
        return content.toString();
    }

    public static String getSortParams(Map<String, String> params) {
        params.remove("sign");
        // String contnt = "";
        StringBuffer contnt = new StringBuffer("");
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        // 这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            //String value = params.get(key);
            // 将值为空的参数排除
            /*
             * if (!StringUtil.isNull(value)) { keyList.add(key); }
             */
            keyList.add(key);
        }
        Collections.sort(keyList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        // 将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt.append(key).append(params.get(key));
            // contnt += key + params.get(key);
        }
        return contnt.toString();
    }

    /**
     *
     * 描述 获取事件配置代码
     * 
     * @author Adrian Bian
     * @created 2020年03月19日 上午11:21:25
     * @param defId
     * @param nodeName
     * @param eventType
     * @param flowVersion
     * @return
     */
    @Override
    public List<String> findEventCodeList(String defId, String nodeName, String eventType, int flowVersion) {
        return dao.findEventCodeList(defId, nodeName, eventType, flowVersion);
    }

    /**
     * 
     * 描述： 工程建设项目分发流程存储业务数据
     * 
     * @author Rider Chen
     * @created 2020年9月8日 下午5:56:26
     * @param flowDatas
     * @return
     * @see net.evecom.platform.hflow.service.FlowEventService#saveBusData(java.util.Map)
     */
    @SuppressWarnings({ "unchecked" })
    public Map<String, Object> saveDistributeBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        flowDatas.put("CREATOR_NAME", flowDatas.get("uploadUserName"));
        flowDatas.put("CREATOR_ID", flowDatas.get("uploadUserId"));
        flowDatas.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        // 项目编码
        String PROJECTCODE = flowDatas.get("PROJECTCODE") == null ? "" : flowDatas.get("PROJECTCODE").toString();
        Map<String, Object> xmjbxx = projectApplyService.getByJdbc("SPGL_XMJBXXB", new String[] { "PROJECT_CODE" },
                new Object[] { PROJECTCODE });
        String jbxxid = "";
        if (null != xmjbxx && xmjbxx.size() >= 0) {
            jbxxid = xmjbxx.get("ID").toString();
        }

        String recordId = projectApplyService.saveOrUpdate(flowDatas, "SPGL_XMJBXXB", jbxxid);
        String lerepInfo = flowDatas.get("LEREP_INFO") == null ? "" : flowDatas.get("LEREP_INFO").toString();
        if (StringUtils.isNotEmpty(lerepInfo)) {
            List<Map<String, Object>> lerepInfoList = JSON.parseObject(lerepInfo, List.class);
            // 删除项目单位信息
            projectApplyService.remove("SPGL_XMDWXXB", "JBXX_ID", new Object[] { recordId });
            for (Map<String, Object> map : lerepInfoList) {
                map.put("JBXX_ID", recordId);
                // 保存项目单位信息
                projectApplyService.saveOrUpdate(map, "SPGL_XMDWXXB", null);
            }
        }

        // 获取上传的附件ID
        String fileIds = (String) flowDatas.get("EFLOW_FILEATTACHIDS");
        if (StringUtils.isNotEmpty(fileIds)) {
            // 进行附件业务表记录ID的更新
            fileAttachService.updateTableName(fileIds, busRecordId);
        }
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            String itemCode = flowDatas.get("ITEM_CODE") == null ? "" : flowDatas.get("ITEM_CODE").toString();
            String newSubmitMaterFileJson = getSubmitMaterFileJson(flowDatas, submitMaterFileJson, itemCode);
            fileAttachService.saveItemMaterFiles(newSubmitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }

    /**
     * 
     * 描述：根据事项编码获取上传材料信息
     * 
     * @author Rider Chen
     * @created 2020年9月9日 上午9:42:01
     * @param flowDatas
     * @param submitMaterFileJson
     * @param itemCode
     * @return
     */
    private String getSubmitMaterFileJson(Map<String, Object> flowDatas, String submitMaterFileJson, String itemCode) {
        // 获取材料信息列表 仅获取事项材料列表
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        String itemId = serviceItem.get("ITEM_ID").toString();
        String exeId = flowDatas.get("EFLOW_EXEID") == null ? "" : flowDatas.get("EFLOW_EXEID").toString();
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        List<Map> fileList = JSON.parseArray(submitMaterFileJson, Map.class);
        List<Map> newFileList = new ArrayList<Map>();
        if (null != applyMaters && applyMaters.size() > 0) {
            for (Map<String, Object> map : applyMaters) {
                String MATER_CODE = map.get("MATER_CODE").toString();
                for (Map fileMap : fileList) {
                    String ATTACH_KEY = fileMap.get("ATTACH_KEY").toString();
                    if (MATER_CODE.equals(ATTACH_KEY)) {
                        newFileList.add(fileMap);
                    }
                }
            }
        }
        String newSubmitMaterFileJson = JSON.toJSONString(newFileList);
        return newSubmitMaterFileJson;
    }
}
