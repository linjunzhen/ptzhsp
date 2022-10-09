/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.ProjectItemService;
import net.evecom.platform.project.service.impl.ProjectItemServiceImpl;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 描述 工程建设项目流程事项表单Controller
 *
 * @author Madison You
 * @version 1.0
 * @created 2019年12月16日 上午9:15:15
 */
@Controller
@RequestMapping("projectItemController")
public class ProjectItemController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectApplyController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/16 20:00:00
     * @param
     * @return
     */
    @Resource
    private ProjectItemService projectItemService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/6 10:06:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:根据项目编码获取消防设计数据
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/7 15:44:00
     */
    @RequestMapping("/getProjectDetailInfoByXfsj")
    @ResponseBody
    public Map<String, Object> getProjectDetailInfoByXfsj(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> variables = new HashMap<>();
        String projectCode = request.getParameter("projectCode");
        String itemCode = dictionaryService.getDicCode("XFSZYSYJGPZ", "itemCode");
        Map<String, Object> executionMap = projectItemService.findExecutionByProjectAndItem(projectCode, itemCode);
        if (executionMap != null && !executionMap.isEmpty()) {
            String busRecordId = (String) executionMap.get("BUS_RECORDID");
            String busTablename = (String) executionMap.get("BUS_TABLENAME");
            Map<String, Object> busMap = projectItemService.getByJdbc(busTablename, new String[]{"FC_PROJECT_INFO_ID"},
                    new Object[]{busRecordId});
            variables.putAll(busMap);
            variables.putAll(executionMap);
            variables.put("returnStatus", true);
        } else {
            variables.put("returnStatus", false);
        }
        return variables;
    }

    /**
     * 描述:随机抽查页面
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/8 14:00:00
     */
    @RequestMapping("/randomCheckByXfysView")
    public ModelAndView randomCheckByXfysView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("website/applyforms/randomCheckByXfysView");
    }

    /**
     * 描述:随机抽查
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/8 13:56:00
     */
    @RequestMapping("/randomCheckByXfys")
    @ResponseBody
    public Map<String, Object> randomCheckByXfys(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> returnMap = new HashMap<>();
        HashMap<String, Object> variables = new HashMap<>();
        ArrayList<Object> randomNumList = new ArrayList<>();
        String busRecordId = request.getParameter("busRecordId");
        boolean isSelect = false;
        Map<String, Object> busRecordMap = projectItemService.getByJdbc("T_BSFW_GCJSXFYS",
                new String[]{"YW_ID"}, new Object[]{busRecordId});
        if (busRecordMap != null && !busRecordMap.isEmpty()) {
            String checknum = (String) busRecordMap.get("CHECKNUM");
            String checknumSystem = (String) busRecordMap.get("CHECKNUM_SYSTEM");
            returnMap.put("checkNum", checknum);
            if (checknumSystem != null) {
                returnMap.put("randomNumList", checknumSystem);
                if (checknumSystem.indexOf(checknum) != -1) {
                    isSelect = true;
                }
            } else {
                Random random = new Random();
                for (int i = 0; i < 5; i++) {
                    int randomNum = random.nextInt(100);
                    if (randomNum == Integer.parseInt(checknum)) {
                        isSelect = true;
                    }
                    randomNumList.add(randomNum);
                }
                returnMap.put("randomNumList", randomNumList.toString());
                variables.put("CHECKNUM_SYSTEM", randomNumList.toString());
                projectItemService.saveOrUpdate(variables, "T_BSFW_GCJSXFYS", busRecordId);
            }
        } else {
            return null;
        }
        returnMap.put("isSelect", isSelect);
        return returnMap;
    }

    /**
     * 描述:检测红线图
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 11:23:00
     */
    @RequestMapping("/checkRedLinePicture")
    @ResponseBody
    public Map<String, Object> checkRedLinePicture(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> returnMap = new HashMap<>();
        String base64Code = request.getParameter("base64Code");
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headMap = new HashMap<>();
        Properties properties = FileUtil.readProperties("project.properties");
        params.put("shapezip", base64Code);
        params.put("areaCode", "350128");
        String header = getZxAccessToken();
        headMap.put("Authorization", header);
        String url = properties.getProperty("ZX_REDLINE_CHECK_URL");
        String s = HttpSendUtil.sendPostParamsH(url, headMap, params);
        returnMap.put("success", false);
        if (s != null && s != "") {
            JSONObject result = JSONObject.parseObject(s);
            returnMap.put("msg",result.getString("msg"));
            if (result.getString("code").equals("30001")) {
                returnMap.put("success", true);
            }
        } else {
            returnMap.put("msg", "系统错误，请联系管理员");
        }
        return returnMap;
    }

    /**
     * 描述:发送林业数据到省网总线
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 11:41:00
     */
    @RequestMapping("/lyydspSendMsgToSwb")
    @ResponseBody
    public Map<String, Object> lyydspSendMsgToSwb(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> returnMap = new HashMap<>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Properties properties = FileUtil.readProperties("project.properties");
        HashMap<String, String> headMap = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> instanceMap = new HashMap<>();
        JSONObject json = new JSONObject();
        String zxSendDataUrl = properties.getProperty("ZX_SEND_DATA_URL");
        String zxCallbackUrl = properties.getProperty("ZX_CALLBACK_URL");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String header = getZxAccessToken();
        headMap.put("Authorization", header);
        params.put("id", "6a999a731aaf9a565593fb40d4f60110");
        params.put("callbackUrl", zxCallbackUrl);
        if (request.getParameter("isBz") != null) {
            String exeId = (String) variables.get("exeId");
            List<Map<String, Object>> bzList = projectItemService.getBjInfoList(exeId);
            if (bzList != null && !bzList.isEmpty()) {
                StringBuffer xmlStr = new StringBuffer();
                xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                xmlStr.append("<Case><SN>" + exeId + "</SN><Attrs>");
                for (Map<String, Object> bzMap : bzList) {
                    String bzCode = (String) bzMap.get("BZ_CODE");
                    String bzName = (String) bzMap.get("BZ_NAME");
                    String bzUrl = (String) bzMap.get("BZ_FILEURL");
                    String base64Code = projectItemService.getBase64FromUrl(uploadFileUrl + bzUrl);
                    String isBz = (String) bzMap.get("IS_BZ");
                    String bzFilename = (String) bzMap.get("BZ_FILENAME");
                    String sort = bzCode.substring(2);
                    if (isBz.equals("0")) {
                        returnMap.put("success", false);
                        returnMap.put("msg", "请将材料都上传后再补正");
                        return returnMap;
                    }
                    xmlStr.append("<Attr><Mode>1</Mode><Code>" + bzCode + "</Code><SeqNo>" + sort + "</SeqNo>");
                    xmlStr.append("<Name>" + bzName + "</Name><FileName>" + bzFilename + "</FileName>");
                    xmlStr.append("<Content>" + base64Code + "</Content></Attr>");
                }
                xmlStr.append("</Attrs></Case>");
                HashMap<String, Object> putMap = new HashMap<>();
                putMap.put("INFO_TYPE", "7");
                putMap.put("INFO_XML", xmlStr.toString());
                projectItemService.saveOrUpdate(putMap, "T_BSFW_SWZX_RETURNDATA", null);
                json.put("infoType", "7");
                json.put("infoXml", xmlStr.toString());
                params.put("parameters", json.toString());
                try {
                    String jsonString = HttpSendUtil.sendPostParamsH(zxSendDataUrl, headMap, params);
                    log.info("补正返回信息：" + jsonString);
                    returnMap.put("success", true);
                    returnMap.put("msg", "补正信息已发送成功");
                } catch (Exception e) {
                    log.info("补正信息发送失败," + e.getMessage());
                }
            } else {
                returnMap.put("success", false);
                returnMap.put("msg", "无需补正");
            }
        } else {
            returnMap.put("OPER_MSG", "提交成功");
            String exeId = (String) variables.get("EFLOW_EXEID");
            Map<String, Object> dataAbutment = projectItemService.getByJdbc("T_BSFW_DATAABUTMENT",
                    new String[]{"AABUT_CODE"}, new String[]{AllConstant.INTER_CODE_TZXM_LYYDSP});
            String configXml = (String) dataAbutment.get("CONFIG_XML");
            /*填充xml模板*/
            String infoXml = setBodyData(variables, configXml, instanceMap);
            json.put("infoType", "1");
            json.put("infoXml", infoXml);
            params.put("parameters", json.toString());
            try {
                String jsonString = HttpSendUtil.sendPostParamsH(zxSendDataUrl, headMap, params);
                this.parseLyReturnData(jsonString, exeId);
            } catch (Exception e) {
                log.info("项目信息发送失败," + e.getMessage());
            }
        }
        return returnMap;
    }

    /**
     * 描述:获取总线返回信息
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/16 20:40:00
     */
    @RequestMapping("/getReturnData")
    @ResponseBody
    public Map<String, Object> getReturnData(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuffer str = new StringBuffer();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(request.getInputStream(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                str.append(new String(buff, 0, length));
            }
            HashMap<String, Object> putMap = new HashMap<>();
            putMap.put("INFO_TYPE", "0");
            putMap.put("INFO_XML", str.toString());
            projectItemService.saveOrUpdate(putMap, "T_BSFW_SWZX_RETURNDATA", null);
        } catch (IOException e) {
            log.info(e.getMessage());
        } finally {
            reader.close();
        }
        return null;
    }

    /**
     * 描述:获取省网总线accesstoken
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 13:03:00
     */
    private String getZxAccessToken() {
        Properties properties = FileUtil.readProperties("project.properties");
        String zxAccessTokenUrl = properties.getProperty("ZX_ACCESS_TOKEN_URL");
        HashMap<String, Object> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", "44658b7e8ca14a80b5e553b6d624ed1c");
        params.put("client_secret", "Ga1noogh");
        String result = HttpSendUtil.sendPostParams(zxAccessTokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        String tokenType = jsonObject.getString("token_type");
        return tokenType + " " + accessToken;
    }

    /**
     * 描述:设置要传输的数据分包
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 14:33:00
     */
    private String setBodyData(Map<String, Object> variables, String configXml, Map<String, Object> instanceMap) {
        StringBuffer buffer = new StringBuffer();
        HashMap<String, Object> caseMap = new HashMap<>();
        HashMap<String, Object> proposerMap = new HashMap<>();
        HashMap<String, Object> personMap = new HashMap<>();
        HashMap<String, Object> unitMap = new HashMap<>();
        HashMap<String, Object> extendMap = new HashMap<>();
        HashMap<String, Object> attr = new HashMap<>();
        instanceMap.put("case", caseMap);
        caseMap.put("proposer", proposerMap);
        caseMap.put("extend", extendMap);
        caseMap.put("attr", attr);
        proposerMap.put("person", personMap);
        proposerMap.put("unit", unitMap);
        String unid = projectItemService.getUNID();
        /*case信息*/
        caseMap.put("rowGuid", unid);
        caseMap.put("sn", notNullForString(variables.get("EFLOW_EXEID")));
        caseMap.put("delareTime", notNullForString(variables.get("CREATE_TIME")));
        /*组织机构代码*/
        String orCode = dictionaryService.getDicCode("LYJGDM", "orCode");
        if (StringUtils.isNotEmpty(orCode)) {
            caseMap.put("deptCode", orCode);
        } else {
            caseMap.put("deptCode", "");
        }
        Object swItemCode = null;
        Map<String, Object> itemMap = projectItemService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[]{"ITEM_CODE"}, new String[]{notNullForString(variables.get("ITEM_CODE"))});
        if (itemMap != null && !itemMap.isEmpty()) {
            swItemCode = itemMap.get("SWB_ITEM_CODE");
        }
        caseMap.put("itemcode", notNullForString(swItemCode));
        caseMap.put("projectCode", notNullForString(variables.get("PROJECT_CODE")));
        Object projectName = null;
        Map<String, Object> projectMap = projectItemService.getByJdbc("SPGL_XMJBXXB",
                new String[]{"PROJECT_CODE"}, new Object[]{notNullForString(variables.get("PROJECT_CODE"))});
        if (projectMap != null && !projectMap.isEmpty()) {
            projectName = projectMap.get("PROJECT_NAME");
        }
        caseMap.put("projectName", projectName);
        caseMap.put("projectAddress", notNullForString(variables.get("PROJECT_ADDRESS")));
        caseMap.put("openWay", notNullForString(variables.get("OPEN_WAY")));
        caseMap.put("applyFrom", notNullForString(variables.get("APPLY_FROM")));
        /*proposer信息*/
        proposerMap.put("applyType", "1");
        proposerMap.put("areaCode", "350128");
        /*person信息*/
        personMap.put("nameGR", "");
        personMap.put("idTypeGR", "");
        personMap.put("idNameGR", "");
        personMap.put("idGR", "");
        personMap.put("mobilePhoneGR", "");
        personMap.put("sexGR", "");
        personMap.put("birthdayGR", "");
        personMap.put("addrGR", "");
        personMap.put("postcodeGR", "");
        personMap.put("emailGR", "");
        personMap.put("telGR", "");
        /*unit信息*/
        unitMap.put("unitTypeFR", notNullForString(variables.get("SQJG_TYPE")));
        unitMap.put("unitNameFR", notNullForString(variables.get("SQJG_NAME")));
        unitMap.put("unitIdcardType", "XYDM");
        unitMap.put("unitCodeFR", notNullForString(variables.get("UNIT_JG_CODE")));
        unitMap.put("unitLealPersonFR", notNullForString(variables.get("UNIT_LEAD_NAME")));
        unitMap.put("fridTypeFR", notNullForString(variables.get("UNIT_LEAD_TYPE")));
        unitMap.put("fridFR", notNullForString(variables.get("UNIT_LEAD_CARDNO")));
        unitMap.put("mobilePhoneFR", notNullForString(variables.get("UNIT_LEAD_PHONE")));
        unitMap.put("sexFR", notNullForString(variables.get("UNIT_LEAD_SEX")));
        unitMap.put("birthdayFR", notNullForString(variables.get("UNIT_LEAD_BIRTHDAY")));
        unitMap.put("addrFR", notNullForString(variables.get("UNIT_LEAD_ADDR")));
        unitMap.put("emailFR", notNullForString(variables.get("UNIT_LEAD_EMAIL")));
        unitMap.put("postcodeFR", notNullForString(variables.get("UNIT_LEAD_POSTCODE")));
        unitMap.put("telFR", notNullForString(variables.get("UNIT_LEAD_TEL")));
        /*proposer信息*/
        proposerMap.put("operatorName", notNullForString(variables.get("JBR_NAME")));
        proposerMap.put("operatorSex", notNullForString(variables.get("JBR_SEX")));
        proposerMap.put("operatorCertificateType", notNullForString(variables.get("JBR_ZJLX")));
        proposerMap.put("operatorCertificateNumber", notNullForString(variables.get("JBR_ZJHM")));
        proposerMap.put("operatorMobilePhone", notNullForString(variables.get("JBR_MOBILE")));
        proposerMap.put("operatorTel", notNullForString(variables.get("JBR_LXDH")));
        proposerMap.put("operatorMail", notNullForString(variables.get("JBR_EMAIL")));
        proposerMap.put("operatorPostcode", notNullForString(variables.get("JBR_POSTCODE")));
        proposerMap.put("operatorAddress", notNullForString(variables.get("JBR_ADDRESS")));
        proposerMap.put("operatorMemo", notNullForString(variables.get("JBR_REMARK")));
        /*extend信息*/
        extendMap.put("openingBank", notNullForString(variables.get("PAY_UNIT_BANK")));
        extendMap.put("bankAccount", notNullForString(variables.get("PAY_UNIT_BANKNAME")));
        extendMap.put("accountNumber", notNullForString(variables.get("PAY_UNIT_BANKCOUNT")));
        /*attrs信息*/
        try{
            setAttrsInfo(variables, attr);
        } catch (Exception e) {
            log.error("附件解析出错");
            log.error(e.getMessage(), e);
        }
        buffer.append(this.makeDataXml(instanceMap, configXml));
        /*存入数据库*/
        HashMap<String, Object> putMap = new HashMap<>();
        putMap.put("INFO_TYPE", "1");
        putMap.put("INFO_XML", "");
        projectItemService.saveOrUpdate(putMap, "T_BSFW_SWZX_RETURNDATA", null);
        return buffer.toString();
    }

    /**
     * 描述:填充附件信息
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/17 16:14:00
     */
    private void setAttrsInfo(Map<String, Object> variables, Map<String, Object> attr) {
        String exeId = (String) variables.get("EFLOW_EXEID");
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        attr.put("content1", "");
        attr.put("content2", "");
        attr.put("content3", "");
        attr.put("content4", "");
        attr.put("content5", "");
        List<Map<String, Object>> fileList = projectItemService.getFileInfoByExeId(exeId);
        if (fileList != null && !fileList.isEmpty()) {
            for (Map<String, Object> fileMap : fileList) {
                String attachKey = (String) fileMap.get("ATTACH_KEY");
                String filePath = (String) fileMap.get("FILE_PATH");
                if (attachKey.equals("11350128345071904JXK000065_01")) {
                    attr.put("content1", projectItemService.getBase64FromUrl(uploadFileUrl + filePath));
                } else if (attachKey.equals("11350128345071904JXK000065_02")) {
                    attr.put("content2", projectItemService.getBase64FromUrl(uploadFileUrl + filePath));
                } else if (attachKey.equals("11350128345071904JXK000065_03")) {
                    attr.put("content3", projectItemService.getBase64FromUrl(uploadFileUrl + filePath));
                } else if (attachKey.equals("11350128345071904JXK000065_04")) {
                    attr.put("content4", projectItemService.getBase64FromUrl(uploadFileUrl + filePath));
                } else if (attachKey.equals("11350128345071904JXK000065_09") 
                        || attachKey.equals("11350128345071904JXK000065_10")) {
                    attr.put("content5", projectItemService.getBase64FromUrl(uploadFileUrl + filePath));
                }
            }
        }
    }


    /**
     * 描述:接收总线流程数据接口
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/17 9:08:00
     */
    @RequestMapping("/receiveZxFlowData")
    @ResponseBody
    public Map<String, Object> receiveZxFlowData(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> flowJobMap = new HashMap<>();
        HashMap<String, Object> variables = new HashMap<>();
        String infoType = request.getParameter("infoType");
        String timestamp = request.getParameter("timestamp");
        String fromCode = request.getParameter("fromCode");
        String infoXml = request.getParameter("infoXml");
        variables.put("INFO_TYPE", infoType);
        variables.put("TIME_STAMP", timestamp);
        variables.put("FROM_CODE", fromCode);
        /*小于5m则保存*/
        if (StringUtils.isNotEmpty(infoXml) && infoXml.length() < 5000000) {
            variables.put("INFO_XML", infoXml);
        }
        /*存入xml文件*/
        if (StringUtils.isNotEmpty(fromCode) && fromCode.equals("11350000MB1954615D")) {
            try{
                projectItemService.saveOrUpdate(variables, "T_BSFW_SWZX_RETURNDATA", null);
                flowJobMap.put("code", "00");
                if (infoType.equals("2")) {
                    projectItemService.doFlowJob(infoXml);
                } else if (infoType.equals("5")) {
                    projectItemService.doFlowResultJob(infoXml);
                } else if (infoType.equals("3")) {
                    projectItemService.doFlowHandleUpJob(infoXml);
                } else if (infoType.equals("4")) {
                    projectItemService.doFlowRestartJob(infoXml);
                } else if (infoType.equals("6")) {
                    projectItemService.doFlowDzzzJob(infoXml);
                }
            } catch (Exception e) {
                log.error("林业流程执行出错");
                log.error(e.getMessage(), e);
                flowJobMap.put("code", "00");
            }
        }
        return flowJobMap;
    }

    /**
     * 描述:获取省林业局材料
     *
     * @author Madison You
     * @created 2019/12/19 19:35:00
     * @param
     * @return
     */
    @RequestMapping("/getSlyjMaterList")
    @ResponseBody
    public List<Map<String, Object>> getSlyjMaterList(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> list = projectItemService.getSlyjMaterList(exeId);
        return list;
    }

    /**
     * 描述:获取补件信息列表
     *
     * @author Madison You
     * @created 2019/12/19 19:35:00
     * @param
     * @return
     */
    @RequestMapping("/getBjInfoList")
    @ResponseBody
    public List<Map<String, Object>> getBjInfoList(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> list = projectItemService.getBjInfoList(exeId);
        return list;
    }

    /**
     * 描述:保存补正base64编码
     *
     * @author Madison You
     * @created 2019/12/22 18:21:00
     * @param
     * @return
     */
    @RequestMapping("/saveBzBase64Code")
    @ResponseBody
    public Map<String, Object> saveBzBase64Code(HttpServletRequest request, HttpServletResponse response) {
        String base64Code = request.getParameter("base64Code");
        String id = request.getParameter("id");
        String filename = request.getParameter("filename");
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, Object> returnMap = new HashMap<>();
        if (base64Code != null && id != null) {
            variables.put("BASE64_CODE", base64Code);
            variables.put("IS_BZ", "1");
            variables.put("BZ_FILENAME", filename);
            try {
                projectItemService.saveOrUpdate(variables, "T_BSFW_LYYDSPBZ", id);
                projectItemService.uploadBzFile(id, base64Code);
                returnMap.put("success", true);
            } catch (Exception e) {
                log.info("补正失败" + e.getMessage());
                returnMap.put("success", false);
            }
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }

    /**
     * 描述:补正
     *
     * @author Madison You
     * @created 2020/5/28 16:30:00
     * @param
     * @return
     */
    @RequestMapping("/saveBzFile")
    @ResponseBody
    public Map<String, Object> saveBzFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        String fileId = request.getParameter("fileId");
        String filePath = request.getParameter("filePath");
        String id = request.getParameter("id");
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> variabes = new HashMap<>();
        if (StringUtils.isNotEmpty(fileId)) {
            variabes.put("BZ_FILENAME", fileName);
            variabes.put("BZ_FILEID", fileId);
            variabes.put("BZ_FILEURL", filePath);
            variabes.put("IS_BZ", "1");
            try {
                projectItemService.saveOrUpdate(variabes, "T_BSFW_LYYDSPBZ", id);
                returnMap.put("success", true);
            } catch (Exception e) {
                returnMap.put("success", false);
            }
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }

    /**
     * 描述:工程建设项目当前在办环节人员信息表
     *
     * @author Madison You
     * @created 2020/3/5 14:22:00
     * @param
     * @return
     */
    @RequestMapping(params = "projectNodeStatis")
    public ModelAndView projectNodeStatis(HttpServletRequest request) {
        return new ModelAndView("project/projectDetail/projectNodeStatis");
    }

    /**
     * 描述:工程建设项目当前在办环节人员信息表数据
     *
     * @author Madison You
     * @created 2020/3/5 14:24:00
     * @param
     * @return
     */
    @RequestMapping(params = "projectNodeData")
    public void projectNodeData(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("isPage", "0");
        List<Map<String, Object>> list = projectItemService
                .getProjectNodeData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:导出工程建设项目当前在办环节人员信息表数据表
     *
     * @author Madison You
     * @created 2020/3/5 15:32:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportProjectNodeExcel")
    public void exportProjectNodeExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("isPage","1");
        List<Map<String, Object>> list = projectItemService
                .getProjectNodeData(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/project/projectDetail/projectNodeStatis.xls";
        int startRow = 3;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        for (Map<String, Object> map : list) {
            String taskStatus = map.get("TASK_STATUS").toString();
            if (StringUtils.isNotEmpty(taskStatus)) {
                if (taskStatus.equals("1")) {
                    map.put("TASK_STATUS", "正在审核");
                } else if (taskStatus.equals("2")) {
                    map.put("TASK_STATUS", "已审核");
                } else if (taskStatus.equals("3")) {
                    map.put("TASK_STATUS", "退回");
                } else if (taskStatus.equals("4")) {
                    map.put("TASK_STATUS", "转发");
                } else if (taskStatus.equals("5")) {
                    map.put("TASK_STATUS", "委托");
                } else if (taskStatus.equals("6")) {
                    map.put("TASK_STATUS", "结束流程");
                } else if (taskStatus.equals("7")) {
                    map.put("TASK_STATUS", "申请人撤回");
                } else if (taskStatus.equals("-1")) {
                    map.put("TASK_STATUS", "挂起");
                }
            }
        }
        ExcelUtil.exportXls(tplPath, list, "平潭综合实验区行政服务中心工程建设项目待审办件表.xls",
                excludeFields, response, startRow, startCol, datas,"",false,new int[]{1,1});
    }

    /**
     * 描述:获取林业用地审批办件发送返回信息
     *
     * @author Madison You
     * @created 2020/5/24 11:27:00
     * @param
     * @return
     */
    @RequestMapping("/getLyReturnData")
    @ResponseBody
    public List<Map<String, Object>> getLyReturnData(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        return projectItemService.findLyReturnData(exeId);
    }



    /**
     * 描述:数据包填充模板
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 16:56:00
     */
    private StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
    }

    /**
     * 描述:非空判定
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/16 14:34:00
     */
    private String notNullForString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 描述:保存并解析省网总线返回信息
     *
     * @author Madison You
     * @created 2020/5/24 11:01:00
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    private void parseLyReturnData(String jsonString, String exeId) {
        log.info("总线返回数据，申报号：" + exeId + "   " +  jsonString);
        HashMap<String, Object> variables = new HashMap<>();
        try{
            if (StringUtils.isNotEmpty(jsonString)) {
                Map<String, Object> jsonParse = (Map) JSON.parse(jsonString);
                /*Map<String,Object> resultMap = (Map)jsonParse.get("result");
                String sj_result = (String) resultMap.get("sj_result");
                if (StringUtils.isNotEmpty(sj_result)) {
                    Map<String,Object> map = (Map)JSON.parse(sj_result);
                    variables.put("message", StringUtil.getValue(map, "message"));
                    variables.put("exe_id", exeId);
                    if (Boolean.parseBoolean(StringUtil.getValue(map,"success"))) {
                        variables.put("status", "1");
                    } else {
                        variables.put("status", "0");
                    }
                    projectItemService.saveOrUpdate(variables, "T_BSFW_LYYDSP_SENDMSG", null);
                }*/
                if (jsonParse.get("code")!=null) {
                    variables.put("message", jsonParse.get("msg"));
                    variables.put("exe_id", exeId);
                    variables.put("status", jsonParse.get("code"));
                    projectItemService.saveOrUpdate(variables, "T_BSFW_LYYDSP_SENDMSG", null);
                }
            }
        } catch (Exception e) {
            log.info("解析省网总线返回信息解析失败，申报号：" + exeId);
            log.error(e.getMessage(), e);
        }
    }

}
