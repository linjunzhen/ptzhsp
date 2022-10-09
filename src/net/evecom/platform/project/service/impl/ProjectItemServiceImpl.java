/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.ems.waybillone.BASE64Encoder;
import net.evecom.platform.hflow.controller.MaterConfigController;
import net.evecom.platform.project.dao.ProjectItemDao;
import net.evecom.platform.project.service.ProjectItemService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.tyjk.service.FlowWebService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 描述 工程建设项目流程事项表单ServiceImpl
 *
 * @author Madison You
 * @version 1.0
 * @created 2019年12月16日 上午9:15:15
 */
@Service("projectItemService")
public class ProjectItemServiceImpl extends BaseServiceImpl implements ProjectItemService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);

    /**
     * 接口的key
     */
    private static String guid = "40288b9f555926ed01555d9e4dab192f";

   /**
    * 描述:
    *
    * @author Madison You
    * @created 2019/12/16 20:00:00
    * @param 
    * @return 
    */
    @Resource
    private ProjectItemDao projectItemDao;

   /**
    * 描述:
    *
    * @author Madison You
    * @created 2019/12/16 20:18:00
    * @param 
    * @return 
    */
    @Override
    protected GenericDao getEntityDao() {
        return projectItemDao;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/17 9:30:00
     * @param
     * @return
     */
    @Resource
    private FlowWebService flowWebService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/13 15:15:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:获取当前项目消防设计申报号
     *
     * @author Madison You
     * @created 2019/12/7 15:53:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> findExecutionByProjectAndItem(String projectCode, String itemCode) {
        StringBuffer sql = new StringBuffer();
        List<Map<String, Object>> list = null;
        ArrayList<Object> params = new ArrayList<>();
        params.add(projectCode);
        params.add(itemCode);
        sql.append(" select * from JBPM6_EXECUTION where PROJECT_CODE = ? and ITEM_CODE = ? and RUN_STATUS = 2 ");
        sql.append(" order by create_time desc ");
        list = projectItemDao.findBySql(sql.toString(), params.toArray(), null);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 描述:获取unid
     *
     * @author Madison You
     * @created 2019/12/16 14:09:00
     * @param
     * @return
     */
    @Override
    public String getUNID() {
        Map m = projectItemDao.getByJdbc("select RAWTOHEX(sys_guid()) as unid from dual ", new Object[] {});
        return (String) m.get("unid");
    }

    /**
     * 描述:环节流转
     *
     * @author Madison You
     * @created 2019/12/17 9:19:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> doFlowJob(String infoXml) {
        HashMap<String, Object> returnMap = new HashMap<>();
        JSONArray attrArr = null;
        JSONArray documentArr = null;
        String exeId = null;
        String opinion = "";
        String OpinionType = "";
        String result = null;
        String caption = "";
        try {
            Document document = XmlUtil.stringToDocument(infoXml);
            Element rootElement = document.getRootElement();
            exeId = rootElement.selectSingleNode("//Case//SN").getText();
            OpinionType = rootElement.selectSingleNode("//Case//FlowInfo//finishnode//OpinionType").getText();
            caption = rootElement.element("FlowInfo").element("finishnode").attributeValue("caption");
            opinion = rootElement.selectSingleNode("//Case//FlowInfo//finishnode//opinion").getText();
            String attrs = rootElement.selectSingleNode("//Case/FlowInfo/finishnode//Attrs").asXML();
            String documents = rootElement.selectSingleNode("//Case/FlowInfo/finishnode//Documents").asXML();
            String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
            String documentsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + documents, "UTF-8");
            JSONObject attrObj = JSONObject.parseObject(attrsJson);
            JSONObject documentObj = JSONObject.parseObject(documentsJson);
            attrArr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
            documentArr = ((JSONArray) ((JSONObject) documentObj.get("Documents")).get("Document"));
        } catch (Exception e) {
            log.info("解析流程过程xml出错" + e.getMessage());
        }
        /*保存流程过程文件*/
        saveFlowFile(attrArr,exeId,documentArr);
        JSONObject flowInfoJosn = new JSONObject();
        if (OpinionType.equals("1") || OpinionType.equals("9")) {
            if (!caption.equals("办结")) {
                String ASSIGNER_CODE = dictionaryService.getDicNames("LYYDSPHJSHRDY", caption);
                String ASSIGNER_NAME = (String)dictionaryService.get("LYYDSPHJSHRDY", caption).get("DIC_DESC");
                flowInfoJosn.put("EFLOW_HANDLE_OPINION", opinion);
                flowInfoJosn.put("ASSIGNER_NAME", ASSIGNER_NAME);
                flowInfoJosn.put("ASSIGNER_CODE", ASSIGNER_CODE);
                flowInfoJosn.put("TASK_NODENAME", caption);
                flowInfoJosn.put("itemCode", "345071904XK02901");
                flowInfoJosn.put("EXE_ID", exeId);
                result = flowWebService.flowExecute(guid, flowInfoJosn.toString());
            }
        } else {
            flowInfoJosn.put("ASSIGNER_CODE","sunlixin");
            flowInfoJosn.put("EFLOW_HANDLE_OPINION",opinion);
            flowInfoJosn.put("exeId", exeId);
            result  =  flowWebService.notAccept(guid, flowInfoJosn.toString());
        }
        log.info("流程执行信息：" + result);
        if (result != null) {
            JSONObject resultJson = JSONObject.parseObject(result);
            String resultCode = resultJson.getString("resultCode");
            if (resultCode.equals("001")) {
                log.info("林业用地审批" + caption + "环节流转成功");
                returnMap.put("code", "00");
            } else {
                returnMap.put("code", "-1");
            }
        } else {
            returnMap.put("code", "-1");
        }
        return returnMap;
    }

    /**
     * 描述:将url转为base64字符串
     *
     * @author Madison You
     * @created 2019/12/17 16:53:00
     * @param
     * @return
     */
    public String getBase64FromUrl(String url) {
        InputStream inputStream = null;
        String encode = null;
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            inputStream = urlConnection.getInputStream();
            byte[] data = null;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
            encode = BASE64Encoder.encode(data);
        } catch (IOException e) {
            log.info(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
        return encode;
    }

    /**
     * 描述:根据exeid获取附件信息
     *
     * @author Madison You
     * @created 2019/12/17 16:59:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getFileInfoByExeId(String exeId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        params.add(exeId);
        sql.append(" SELECT B.FILE_NAME,FILE_PATH,ATTACH_KEY FROM JBPM6_EXECUTION A LEFT JOIN ");
        sql.append(" T_MSJW_SYSTEM_FILEATTACH B ON A.BUS_RECORDID = B.BUS_TABLERECORDID WHERE A.EXE_ID = ? ");
        sql.append(" ORDER BY B.CREATE_TIME ");
        List<Map<String,Object>> list = projectItemDao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:获取办件结果信息
     *
     * @author Madison You
     * @created 2019/12/18 10:39:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> doFlowResultJob(String infoXml) {
        HashMap<String, Object> returnMap = new HashMap<>();
        JSONArray attrArr = null;
        String exeId = null;
        String reason = "";
        String DocName = "";
        String DocCode = "";
        String CertifiedTime = "";
        String EffectiveBegin = "";
        String EffectiveEnd = "";
        String CertifiedDeptCode = "";
        String Holder = "";
        String ProjectName = "";
        try {
            Document document = XmlUtil.stringToDocument(infoXml);
            Element rootElement = document.getRootElement();
            exeId = rootElement.selectSingleNode("//Case//SN").getText();
            reason = rootElement.selectSingleNode("//Case//reason").getText();
            DocName = rootElement.selectSingleNode("//Case//License//DocName").getText();
            DocCode = rootElement.selectSingleNode("//Case//License//DocCode").getText();
            CertifiedTime = rootElement.selectSingleNode("//Case//License//CertifiedTime").getText();
            EffectiveBegin = rootElement.selectSingleNode("//Case//License//EffectiveBegin").getText();
            EffectiveEnd = rootElement.selectSingleNode("//Case//License//EffectiveEnd").getText();
            CertifiedDeptCode = rootElement.selectSingleNode("//Case//License//CertifiedDeptCode").getText();
            ProjectName = rootElement.selectSingleNode("//Case//ProjInfo//ProjectName").getText();
            Holder = rootElement.selectSingleNode("//Case//License//Holder").getText();
            String attrs = rootElement.selectSingleNode("//Case//Attrs").asXML();
            String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
            JSONObject attrObj = JSONObject.parseObject(attrsJson);
            attrArr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
        } catch (Exception e) {
            log.info("解析结果xml文件出错"+e.getMessage());
        }
        /*保存结果文件*/
        Map<String, Object> resultFileMap = saveResultFile(attrArr, exeId);
        String fileIdStr = (String) resultFileMap.get("fileIdStr");
        String filePathStr = (String) resultFileMap.get("filePathStr");
        /*处理流程*/
        JSONObject flowInfoJosn = new JSONObject();
        flowInfoJosn.put("RESULT_FILE_URL", filePathStr);
        flowInfoJosn.put("RESULT_FILE_ID", fileIdStr);
        flowInfoJosn.put("CUR_NODE", "办结");
        flowInfoJosn.put("RUN_MODE", "");
        String XKCONTENT = ProjectName + "申请办理使用林地审核同意书";
        String SDCONTENT = "1、使用林地审核同意书\n2、"+ProjectName+"使用林地现状图";
        flowInfoJosn.put("SDCONTENT", SDCONTENT);
        flowInfoJosn.put("XKCONTENT", XKCONTENT);
        flowInfoJosn.put("ISLONG_TERM", "");
        flowInfoJosn.put("CLOSE_TIME", EffectiveEnd);
        flowInfoJosn.put("EFFECT_TIME", EffectiveBegin);
        flowInfoJosn.put("XKDEPT_ID", "40288b9f520b65cb01520b9a7d85003b");
        flowInfoJosn.put("XKDEPT_NAME", "区农业农村局");
        flowInfoJosn.put("XKFILE_NAME", DocName);
        flowInfoJosn.put("XKFILE_NUM", DocCode);
        flowInfoJosn.put("EFLOW_HANDLE_OPINION", reason);
        String ASSIGNER_CODE = dictionaryService.getDicNames("LYYDSPHJSHRDY", "办结");
        String ASSIGNER_NAME = (String) dictionaryService.get("LYYDSPHJSHRDY", "办结").get("DIC_DESC");
        flowInfoJosn.put("ASSIGNER_NAME", ASSIGNER_NAME);
        flowInfoJosn.put("ASSIGNER_CODE", ASSIGNER_CODE);
        flowInfoJosn.put("TASK_NODENAME", "办结");
        flowInfoJosn.put("itemCode", "345071904XK02901");
        flowInfoJosn.put("EXE_ID", exeId);
        String result = flowWebService.flowExecute(guid, flowInfoJosn.toJSONString());
        log.info("流程执行信息：" + result);
        if (result != null) {
            JSONObject resultJson = JSONObject.parseObject(result);
            String resultCode = resultJson.getString("resultCode");
            if (resultCode.equals("001")) {
                /*将剩余结果信息存入表中*/
                Map<String,Object> resultMap = projectItemDao.getByJdbc("JBPM6_FLOW_RESULT",
                        new String[]{"EXE_ID"}, new Object[]{exeId});
                if (resultMap != null && !resultMap.isEmpty()) {
                    HashMap<String, Object> variables = new HashMap<>();
                    variables.put("XK_TYPE", "3");
                    variables.put("XKDECIDE_TIME", CertifiedTime);
                    variables.put("XK_USC", CertifiedDeptCode);
                    variables.put("XK_HOLDER", Holder);
                    projectItemDao.saveOrUpdate(variables, "JBPM6_FLOW_RESULT", (String) resultMap.get("RESULT_ID"));
                    log.info("林业用地审批办结");
                }
                returnMap.put("code", "00");
            } else {
                returnMap.put("code", "-1");
            }
        } else {
            returnMap.put("code", "-1");
        }
        return returnMap;
    }

    /**
     * 描述:计时暂停(挂起)
     *
     * @author Madison You
     * @created 2019/12/18 15:59:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> doFlowHandleUpJob(String infoXml) {
        HashMap<String, Object> returnMap = new HashMap<>();
        String exeId = "";
        String reason = "";
        Document document = null;
        JSONArray attrArr = null;
        Element rootElement = null;
        try {
            document = XmlUtil.stringToDocument(infoXml);
            rootElement = document.getRootElement();
            exeId = rootElement.selectSingleNode("//Case//SN").getText();
            reason = rootElement.selectSingleNode("//Case//reason").getText();
        } catch (Exception e) {
            log.info("解析挂起xml出错，" + e.getMessage());
        }
        if (reason.equals("补件")) {
            String attrs = rootElement.selectSingleNode("//Case//Extend//Attrs").asXML();
            String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
            JSONObject attrObj = JSONObject.parseObject(attrsJson);
            attrArr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
            saveBjAttr(exeId, attrArr);
            returnMap.put("code", "00");
            log.info("林业用地审批补正成功");
        } else {
            /*根据挂起原因和申报号获取挂起环节id*/
            String handleUpId = getHandleUpId(reason, exeId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ASSIGNER_CODE", "sunlixin");
            jsonObject.put("link_man_tel", "18850746674");
            jsonObject.put("link_man", "孙丽新");
            jsonObject.put("LinkId", handleUpId);
            jsonObject.put("exeId", exeId);
            String result  =  flowWebService.handUp(guid, jsonObject.toString());
            if (result != null) {
                JSONObject resultJson = JSONObject.parseObject(result);
                String resultCode = resultJson.getString("resultCode");
                if (resultCode.equals("001")) {
                    log.info("林业用地审批挂起成功");
                    returnMap.put("code", "00");
                } else {
                    returnMap.put("code", "-1");
                }
            } else {
                returnMap.put("code", "-1");
            }
        }
        return returnMap;
    }

    /**
     * 描述:保存补件信息
     *
     * @author Madison You
     * @created 2019/12/19 19:17:00
     * @param
     * @return
     */
    private void saveBjAttr(String exeId, JSONArray attrArr) {
        if (attrArr != null) {
            for (int m = 0; m < attrArr.size(); m++) {
                JSONObject jObj = ((JSONObject) attrArr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    String Code = getjObjInfo(jObj, "Code");
                    String Name = getjObjInfo(jObj, "Name");
                    String Content = getjObjInfo(jObj, "Content");
                    String Count = getjObjInfo(jObj, "Count");
                    HashMap<String, Object> variables = new HashMap<>();
                    variables.put("BZ_CODE", Code);
                    variables.put("BZ_NAME", Name);
                    variables.put("BZ_Content", Content);
                    variables.put("BZ_Count", Count);
                    variables.put("EXE_ID", exeId);
                    projectItemDao.saveOrUpdate(variables, "T_BSFW_LYYDSPBZ", null);
                }
            }
        }
    }

    /**
     * 描述:重启流程
     *
     * @author Madison You
     * @created 2019/12/18 16:18:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> doFlowRestartJob(String infoXml) {
        HashMap<String, Object> returnMap = new HashMap<>();
        JSONArray attrArr = null;
        String exeId = "";
        String opinion = "";
        try{
            Document document = XmlUtil.stringToDocument(infoXml);
            Element rootElement = document.getRootElement();
            exeId = rootElement.selectSingleNode("//Case//SN").getText();
            opinion = rootElement.selectSingleNode("//Case//opinion").getText();
            String attrs = rootElement.selectSingleNode("//Case//Attrs").asXML();
            String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
            JSONObject attrObj = JSONObject.parseObject(attrsJson);
            attrArr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
            saveResultFile(attrArr, exeId);
        }catch (Exception e){
            log.info("解析重启xml文件失败，" + e.getMessage());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exeId", exeId);
        jsonObject.put("EXPLAIN_FILE_ID", "40288b9f53bdf4080153c03fa38301ce");
        jsonObject.put("EXPLAIN", opinion);
        jsonObject.put("ASSIGNER_CODE", "sunlixin");
        String result  =  flowWebService.reStart(guid, jsonObject.toString());
        log.info("重启信息，" + result);
        if (result != null) {
            JSONObject resultJson = JSONObject.parseObject(result);
            String resultCode = resultJson.getString("resultCode");
            if (resultCode.equals("001")) {
                returnMap.put("code", "00");
            } else {
                returnMap.put("code", "-1");
            }
        } else {
            returnMap.put("code", "-1");
        }
        return returnMap;
    }

    /**
     * 描述:获取省林业局传来材料
     *
     * @author Madison You
     * @created 2019/12/19 19:37:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getSlyjMaterList(String exeId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select b.FILE_NAME,b.FILE_PATH,b.FILE_ID from JBPM6_EXECUTION a ");
        sql.append(" left join T_MSJW_SYSTEM_FILEATTACH b on a.BUS_RECORDID = b.BUS_TABLERECORDID ");
        sql.append(" where a.EXE_ID = ? and b.ATTACH_KEY is null and FILE_ID is not null ");
        params.add(exeId);
        List<Map<String,Object>> list = projectItemDao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }


    /**
     * 描述:获取补件信息列表
     *
     * @author Madison You
     * @created 2019/12/19 20:10:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBjInfoList(String exeId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select * from t_bsfw_lyydspbz where EXE_ID = ? ");
        sql.append(" order by create_time desc ");
        params.add(exeId);
        List<Map<String,Object>> list = projectItemDao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:获取电子证照信息
     *
     * @author Madison You
     * @created 2019/12/19 20:37:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> doFlowDzzzJob(String infoXml) {
        HashMap<String, Object> returnMap = new HashMap<>();
        String DocName = "";
        String DocContent = "";
        String exeId = "";
        try {
            Document document = XmlUtil.stringToDocument(infoXml);
            Element rootElement = document.getRootElement();
            DocName = rootElement.selectSingleNode("//Case//Proposer//DocName").getText();
            DocContent = rootElement.selectSingleNode("//Case//DocContent").getText();
            exeId = rootElement.selectSingleNode("//Case//SN").getText();
            saveDzzz(exeId, DocName + ".edc", DocContent);
            returnMap.put("code","00");
            log.info("林业用地审批电子证照保存成功");
        } catch (Exception e) {
            log.info("解析电子证照xml文件失败，" + e.getMessage());
        }
        return returnMap;
    }

    /**
     * 描述:根据typeCode和dicCode获取dicName
     *
     * @author Madison You
     * @created 2019/12/23 11:06:00
     * @param
     * @return
     */
    @Override
    public String getDicByTypeCodeAndDicCode(String typeCode, String dicCode) {
        String dicName = "";
        Map<String, Object> dicMap = projectItemDao.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[]{"TYPE_CODE", "DIC_CODE"}, new Object[]{typeCode, dicCode});
        if (dicMap != null && !dicMap.isEmpty()) {
            dicName = dicMap.get("DIC_NAME").toString();
        }
        return dicName;
    }

    /**
     * 描述:保存电子证照
     *
     * @author Madison You
     * @created 2019/12/19 20:42:00
     * @param
     * @return
     */
    private void saveDzzz(String exeId, String docName, String docContent) {
        String busRecordid = "";
        String busTablename = "";
        if (exeId != null) {
            Map<String,Object> exeMap = projectItemDao.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            if (exeMap != null && !exeMap.isEmpty()) {
                busRecordid = (String)exeMap.get("BUS_RECORDID");
                busTablename = (String)exeMap.get("BUS_TABLENAME");
            }
        }
        if (docContent != null && !docContent.equals("")) {
            uploadFile(busRecordid, busTablename, docContent, docName);
        }
    }

    /**
     * 描述:保存结果附件
     *
     * @author Madison You
     * @created 2019/12/18 11:32:00
     * @param
     * @return
     */
    private Map<String,Object> saveResultFile(JSONArray attrArr, String exeId) {
        HashMap<String, Object> returnMap = new HashMap<>();
        String busRecordid = "";
        String busTablename = "";
        StringBuffer fileIdStr = new StringBuffer();
        StringBuffer filePathStr = new StringBuffer();
        /*获取业务表*/
        if (exeId != null) {
            Map<String,Object> exeMap = projectItemDao.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            if (exeMap != null && !exeMap.isEmpty()) {
                busRecordid = (String)exeMap.get("BUS_RECORDID");
                busTablename = (String)exeMap.get("BUS_TABLENAME");
            }
        }
        if (attrArr != null) {
            for (int m = 0; m < attrArr.size(); m++) {
                JSONObject jObj = ((JSONObject) attrArr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    String FileName = getjObjInfo(jObj, "FileName");
                    String Content = getjObjInfo(jObj, "Content");
                    String result = uploadFile(busRecordid, busTablename, Content, FileName);
                    if (StringUtils.isNotEmpty(result)) {
                        Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
                        Map<String,Object> data = (Map) resultMap.get("data");
                        fileIdStr.append(data.get("fileId")).append(",");
                        filePathStr.append(data.get("filePath")).append(",");
                    }
                }
            }
            returnMap.put("fileIdStr", fileIdStr.substring(0, fileIdStr.length() - 1));
            returnMap.put("filePathStr", filePathStr.substring(0, filePathStr.length() - 1));
        }
        return returnMap;
    }

    /**
     * 描述:保存流程执行文件
     *
     * @author Madison You
     * @created 2019/12/17 13:06:00
     * @param
     * @return
     */
    private void saveFlowFile(JSONArray attrArr , String exeId , JSONArray documentArr) {
        HashMap<String, Object> variables = new HashMap<>();
        String busRecordid = "";
        String busTablename = "";
        StringBuffer documentTypeStr = new StringBuffer();
        StringBuffer documentNameStr = new StringBuffer();
        StringBuffer documentDocNoStr = new StringBuffer();
        StringBuffer documentFileIdStr = new StringBuffer();
        /*获取业务表*/
        if (exeId != null) {
            Map<String,Object> exeMap = projectItemDao.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            if (exeMap != null && !exeMap.isEmpty()) {
                busRecordid = (String)exeMap.get("BUS_RECORDID");
                busTablename = (String)exeMap.get("BUS_TABLENAME");
            }
        }
        /*保存文件*/
        if (attrArr != null) {
            for (int m = 0; m < attrArr.size(); m++) {
                JSONObject jObj = ((JSONObject) attrArr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    String FileName = getjObjInfo(jObj, "FileName");
                    String Content = getjObjInfo(jObj, "Content");
                    uploadFile(busRecordid, busTablename, Content, FileName);
                }
            }
        }
        /*保存收件通知书、受理通知书附件*/
        if (documentArr != null) {
            for (int m = 0; m < documentArr.size(); m++) {
                JSONObject jObj = ((JSONObject) documentArr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    String Type = getjObjInfo(jObj, "Type");
                    String Name = getjObjInfo(jObj, "Name");
                    String DocNo = getjObjInfo(jObj, "DocNo");
                    String Content = getjObjInfo(jObj, "Content");
                    String fileId = uploadDocument(busRecordid, busTablename, Content, Name+".pdf");
                    documentTypeStr.append(Type).append(",");
                    documentNameStr.append(Name).append(",");
                    documentDocNoStr.append(DocNo).append(",");
                    documentFileIdStr.append(fileId).append(",");
                }
            }
        }
        /*保存单据信息*/
        if (!documentNameStr.toString().equals("")) {
            variables.put("DOCUMENT_NAME_STR", documentNameStr.substring(0,documentNameStr.length()-1));
            variables.put("DOCUMENT_DOCNO_STR", documentDocNoStr.substring(0,documentDocNoStr.length()-1));
            variables.put("DOCUMENT_FILEID_STR", documentFileIdStr.substring(0,documentFileIdStr.length()-1));
            variables.put("DOCUMENT_TYPE_STR", documentTypeStr.substring(0,documentTypeStr.length()-1));
            projectItemDao.saveOrUpdate(variables, busTablename, busRecordid);
        }
    }


    /**
     * 描述:上传受理单据到文件服务器
     *
     * @author Madison You
     * @created 2019/12/17 15:27:00
     * @param
     * @return
     */
    private String uploadDocument(String busRecordid, String busTablename, String base64Code, String fileName) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String fileId = "";
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "lyj");// 上传人ID
                param.put("uploaderName", "林业局"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName);// 上传附件名
                param.put("attachKey", "");// 材料编码
                param.put("busTableName", busTablename);// 业务表名
                param.put("busRecordId", busRecordid);// 业务表ID
                param.put("SFHZD", "1");// 是否回执单
                String result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode,
                        app_id, password, param);
                if (StringUtils.isNotEmpty(result)) {
                    Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
                    Map<String,Object> data = (Map) resultMap.get("data");
                    fileId = (String) data.get("fileId");
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return fileId;
    }

    /**
     * 描述:上传文件到文件服务器
     *
     * @author Madison You
     * @created 2019/12/17 13:26:00
     * @param
     * @return
     */
    public String uploadFile(String busRecordid, String busTablename, String base64Code , String fileName) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String result = "";
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "lyj");// 上传人ID
                param.put("uploaderName", "林业局"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName);// 上传附件名
                param.put("attachKey", "");// 材料编码
                param.put("busTableName", busTablename);// 业务表名
                param.put("busRecordId", busRecordid);// 业务表ID
                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return result;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/23 12:49:00
     * @param
     * @return
     */
    @Override
    public void uploadBzFile(String id, String base64Code) {
        Map<String,Object> bzMap = projectItemDao.getByJdbc("T_BSFW_LYYDSPBZ",
                new String[]{"YW_ID"}, new Object[]{id});
        if (bzMap != null && !bzMap.isEmpty()) {
            String fileName = (String) bzMap.get("BZ_FILENAME");
            String bzCode = (String) bzMap.get("BZ_CODE");
            String typeCode = "LYYDSPCL";
            String attachKey = getDicByTypeCodeAndDicCode(typeCode, bzCode);
            String exeId = (String) bzMap.get("EXE_ID");
            Map<String,Object> exeMap = projectItemDao.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"}, new Object[]{exeId});
            String busRecordid = (String) exeMap.get("BUS_RECORDID");
            String busTablename = (String) exeMap.get("BUS_TABLENAME");
            String s = uploadFileBz(busRecordid, busTablename, base64Code, fileName, attachKey);
            String fileId = "";
            if (StringUtils.isNotEmpty(s)) {
                Map<String,Object> resultMap = JSON.parseObject(s, Map.class);
                Map<String,Object> data = (Map) resultMap.get("data");
                fileId = (String) data.get("fileId");
            }
            HashMap<String, Object> putMap = new HashMap<>();
            putMap.put("sqfs", "1");
            projectItemDao.saveOrUpdate(putMap, "T_MSJW_SYSTEM_FILEATTACH", fileId);
        }

    }

    /**
     * 描述:工程建设项目当前在办环节人员信息表数据
     *
     * @author Madison You
     * @created 2020/3/5 14:24:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getProjectNodeData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select exe_id,project_code,subject,ENTERPRISE_NAME,CONTACT_NAME,CONTACT_TEL,item_name, ");
        sql.append(" task_nodename,task_status,listagg(assigner_name, ',') within group (order by exe_id)  ");
        sql.append("ASSIGNER_NAMES,CNQXGZR,create_time,task_deadtime from (select a.exe_id,a.project_code,a.subject,");
        sql.append(" g.ENTERPRISE_NAME,g.CONTACT_NAME,g.CONTACT_TEL,b.item_name,c.task_nodename,c.assigner_name, ");
        sql.append(" c.task_status,d.mobile,a.create_time,e.CNQXGZR,c.task_deadtime from jbpm6_execution a ");
        sql.append(" left join t_wsbs_serviceitem b on a.item_code = b.item_code ");
        sql.append(" left join jbpm6_task c on a.exe_id = c.exe_id ");
        sql.append(" left join t_msjw_system_sysuser d on c.assigner_code = d.username ");
        sql.append(" left join t_wsbs_serviceitem e on e.item_code = a.item_code ");
        sql.append(" left join SPGL_XMJBXXB f on a.PROJECT_CODE = f.PROJECT_CODE ");
        sql.append(" left join SPGL_XMDWXXB g on g.JBXX_ID = f.ID ");
        sql.append(" where a.project_code is not null and a.run_status != '2' and a.run_status != '7' ");
        sql.append(" and c.task_status in ('1', '-1') and c.task_nodename != '联合审查申请' ");
        sql.append(" and c.task_nodename != '规划选址及用地意向申请' and c.task_nodename != '评审申请' ");
        sql.append(" and c.assigner_name is not null order by project_code, exe_id) ");
        sql.append(" group by exe_id, project_code, subject,ENTERPRISE_NAME,CONTACT_NAME, ");
        sql.append(" item_name,CONTACT_TEL, task_nodename,task_status,CNQXGZR, create_time, task_deadtime ");
        if (filter.getQueryParams().get("isPage") != null &&
                filter.getQueryParams().get("isPage").equals("1")) {
            return projectItemDao.findBySql(sql.toString(), params.toArray(), null);
        } else {
            return projectItemDao.findBySql(sql.toString(), params.toArray(), filter.getPagingBean());
        }
    }

    /**
     * 描述:查询林业用地审批办件发送返回信息
     *
     * @author Madison You
     * @created 2020/5/24 11:27:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findLyReturnData(String exeId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from T_BSFW_LYYDSP_SENDMSG where exe_id = ? order by create_time desc");
        return projectItemDao.findBySql(sql.toString(), new Object[]{exeId}, null);
    }

    /**
     * 描述:上传补正文件
     *
     * @author Madison You
     * @created 2019/12/23 13:16:00
     * @param
     * @return
     */
    private String uploadFileBz(String busRecordid, String busTablename, String base64Code, String fileName ,
                              String attachKey) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String result = "";
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "lyj");// 上传人ID
                param.put("uploaderName", "林业局"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName);// 上传附件名
                param.put("attachKey", attachKey);// 材料编码
                param.put("busTableName", busTablename);// 业务表名
                param.put("busRecordId", busRecordid);// 业务表ID
                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return result;
    }

    /**
     * 描述:根据挂起原因和申报号获取挂起环节id
     *
     * @author Madison You
     * @created 2019/12/18 17:25:00
     * @param
     * @return
     */
    private String getHandleUpId(String reason, String exeId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        String recordId = "";
        if (reason.equals("现场查验")) {
            reason = "现场勘察";
        } else if (reason.equals("公告公示")) {
            reason = "公示";
        } else if (reason.equals("森林植被恢复费缴费")) {
            reason = "缴费";
        }
        params.add(exeId);
        params.add(reason);
        sql.append(" select c.record_id from jbpm6_execution a ");
        sql.append(" left join T_WSBS_SERVICEITEM b on a.ITEM_CODE = b.ITEM_CODE ");
        sql.append(" left join T_WSBS_SERVICEITEM_LINK c on b.ITEM_ID = c.ITEM_ID ");
        sql.append(" where a.EXE_ID = ? and c.LINK_NAME = ? ");
        List<Map<String,Object>> list = projectItemDao.findBySql(sql.toString(), params.toArray(), null);
        if (list != null && !list.isEmpty()) {
            Map<String, Object> map = list.get(0);
            recordId = (String) map.get("RECORD_ID");
        }
        return recordId;
    }

    /**
     * 描述:获取附件信息
     *
     * @author Madison You
     * @created 2019/12/17 13:23:00
     * @param
     * @return
     */
    private String getjObjInfo(JSONObject jObj, String aString) {
        return ((JSONArray) (jObj).get(aString))==null?
                "":((JSONArray) (jObj).get(aString)).get(0).toString();
    }

}
