/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcSsdjService;
import net.evecom.platform.bsfw.util.MaterDownloadUtil;
import net.evecom.platform.call.service.CallLogService;
import net.evecom.platform.system.model.SysUser;
import org.apache.commons.codec.binary.Hex;
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
import java.security.MessageDigest;
import java.util.*;

/**
 * 描述 不动产全流程涉税登记
 * @author Madison You
 * @created 2020年6月518日 19:32:19
 */
@Controller
@RequestMapping("/bdcSsdjController")
public class BdcSsdjController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcSsdjController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/6/19 15:33:00
     * @param
     * @return
     */
    @Resource
    private BdcSsdjService bdcSsdjService;

    /**
     * 描述:是否重发
     *
     * @author Madison You
     * @created 2020/12/1 10:17:00
     * @param
     * @return
     */
    private String sfcf = "0";

    /**
     * 描述:来源部门
     *
     * @author Madison You
     * @created 2020/12/1 10:33:00
     * @param
     * @return
     */
    private String lybm = "budongchan";

    /**
     * 描述:全局标志位：判断发送材料是否成功
     *
     * @author Madison You
     * @created 2020/12/3 9:51:00
     * @param
     * @return
     */
    private boolean isTaxFileSuccess = false;

    /**
     * 描述:全局标志位：判断发送材料清单是否成功
     *
     * @author Madison You
     * @created 2020/12/3 9:51:00
     * @param
     * @return
     */
    private boolean isTaxFileListSuccess = false;

    /**
     * 第三方接口调用日志
     */
    @Resource
    private CallLogService callLogService;

    /**
     * 描述:发送涉税登记信息
     *
     * @author Madison You
     * @created 2020/6/19 11:25:00
     * @param
     * @return
     */
    @RequestMapping("/sendBdcSsdjInfo")
    @ResponseBody
    public Map<String, Object> sendBdcSsdjInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String exeId = (String) variables.get("EXE_ID");
        Properties properties = FileUtil.readProperties("project.properties");
        String url = "";
        sfcf = "0";
//        isTaxFileSuccess = false;//将材料发送是否成功置为false
//        isTaxFileListSuccess = false;//将材料发送清单是否成功置为false
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        returnMap.put("msg", "发送失败，请联系管理员");
        Map<String, String> headerMap = setBearHeadMap(exeId);
        Map<String,Object> body = new HashMap<>();
        List<Map<String,Object>> data = new ArrayList<>();
        Map<String,Object> dataMap = new HashMap<>();
        Map<String,Object> packageHead = new HashMap<>();
        body.put("data", data);
        body.put("packageHead", packageHead);
        data.add(dataMap);
        String result = "";
        JSONObject json = new JSONObject();
        long LOG_START_TIME = 0l;//请求开始时间戳
        boolean isCall = false;//是否调用第三方接口
        try{
            bdcSsdjService.saveOrUpdate(variables, "T_BDCQLC_GYJSJFWZYDJ", variables.get("YW_ID").toString());
            String SSDJ_TYPE = (String) variables.get("SSDJ_TYPE");
            if (SSDJ_TYPE.equals("1")) {
                setZlfBody(dataMap,packageHead,variables);
                url = properties.getProperty("SSDJ_ZLFJY_URL");
                json = new JSONObject(body);
                log.info("新增房请求数据：" + json.toJSONString());
                result = HttpSendUtil.sendHttpPostJson(url, headerMap, json.toJSONString(), "UTF-8");
            } else if (SSDJ_TYPE.equals("2")) {
                setClfBody(dataMap, packageHead, variables);
                url = properties.getProperty("SSDJ_CLFJY_URL");
                json = new JSONObject(body);
                log.info("存量房请求数据：" + json.toJSONString());
                LOG_START_TIME = DateTimeUtil.getTimestamp();//请求开始时间戳
                isCall = true;
                result = HttpSendUtil.sendHttpPutJson(url, headerMap, json.toJSONString(), "UTF-8");
            }
            log.info("发送不动产涉税登记申报号：" + exeId + ",返回结果为：" + result);
            parseBdcSsdjResult(result,returnMap,variables);
//            /*发送材料清单信息*/
//            String buyFileIds = StringUtil.getValue(variables, "SSDJ_BUY_FILEID");
//            Map<String,Object> busMap = bdcSsdjService.getByJdbc("T_BDCQLC_GYJSJFWZYDJ",
//                    new String[]{"YW_ID"}, new Object[]{StringUtil.getValue(variables,"YW_ID")});
//            String IS_TAXFILE_SUCCESS = StringUtil.getValue(busMap, "IS_TAXFILE_SUCCESS");
//            if (StringUtils.isNotEmpty(buyFileIds) && !Objects.equals(IS_TAXFILE_SUCCESS,"1")) {
//                sendBdcSsdjFileListInfo(resultMap, variables, headerMap);
//                sendBdcSsdjFileContentInfo(resultMap, variables, headerMap);
//                /*材料发送成功与否的返回*/
//                dillBdcSsdjFileReturnInfo(returnMap,variables);
//            }

        } catch (Exception e) {
            log.error("发送不动产涉税登记信息失败，申报号：" + exeId + "，返回结果为：" + result);
            log.error(e.getMessage(), e);
            returnMap.put("success", false);
        }finally {
            if(isCall){//2涉税登记存量房交易接口
                /*callLogService.save(LOG_START_TIME,json.toJSONString()
                    , StringUtil.getBoolean(returnMap,"success")?1:0,result,2);*/
                callLogService.save(LOG_START_TIME,json.toJSONString()
                        , StringUtil.getBoolean(returnMap,"success")?1:0,result,2,exeId);
            }
        }

        return returnMap;
    }

    /**
     * 描述:材料发送成功与否的返回
     *
     * @author Madison You
     * @created 2020/12/3 9:59:00
     * @param
     * @return
     */
    private void dillBdcSsdjFileReturnInfo(Map<String, Object> returnMap,Map<String,Object> variables, String msg) {
        String ywId = StringUtil.getValue(variables, "YW_ID");
        if (isTaxFileSuccess && isTaxFileListSuccess) {
            returnMap.put("success", true);
            returnMap.put("fileSuccess", true);
            returnMap.put("msg", "材料上传成功");
            Map<String, Object> busMap = new HashMap<>();
            busMap.put("IS_TAXFILE_SUCCESS", "1");
            bdcSsdjService.saveOrUpdate(busMap, "T_BDCQLC_GYJSJFWZYDJ", ywId);
        } else {
            returnMap.put("fileSuccess", false);
            returnMap.put("msg", msg);
        }
    }

    /**
     * 描述:发送涉税登记信息材料信息
     *
     * @author Madison You
     * @created 2020/12/2 15:23:00
     * @param
     * @return
     */
    @RequestMapping("/sendBdcSsdjFileInfo")
    @ResponseBody
    public Map<String, Object> sendBdcSsdjFileInfo(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        isTaxFileSuccess = false;
        isTaxFileListSuccess = false;
        sfcf = "0";
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String exeId = StringUtil.getValue(variables, "EXE_ID");
        Map<String, Object> saveMap = bdcSsdjService.getLateSsdjInfo(exeId);
        if (saveMap == null) {
            returnMap.put("fileSuccess", false);
            returnMap.put("msg", "请先进行涉税申报！");
            return returnMap;
        } else {
            sfcf = "1";
        }
        String msg = "材料发送失败，请联系管理员";
        Map<String, String> headerMap = setBearHeadMap(exeId);
        String buyFileIds = StringUtil.getValue(variables, "SSDJ_BUY_FILEID");
        if (StringUtils.isNotEmpty(buyFileIds)) {
            msg = sendBdcSsdjFileListInfo(saveMap, variables, headerMap);
            if(isTaxFileListSuccess) {
                msg = sendBdcSsdjFileContentInfo(saveMap, variables, headerMap);
            }
            dillBdcSsdjFileReturnInfo(returnMap, variables, msg);
        }
        return returnMap;
    }


    /**
     * 描述:涉税登记信息页面
     *
     * @author Madison You
     * @created 2020/7/1 11:23:00
     * @param
     * @return
     */
    @RequestMapping(params = "getSsdjInfoView")
    public ModelAndView getSsdjInfoView(HttpServletRequest request , HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        long LOG_START_TIME = 0l;//请求开始时间戳
        boolean isCall = false;//是否调用第三方接口
        int LOG_RESPONSE_STATUS = 0;//接口调用状态 0失败,1成功
        JSONObject json = new JSONObject();
        String result = "";
        try {
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> lateSsdjInfo = bdcSsdjService.getLateSsdjInfo(exeId);
                if (lateSsdjInfo != null) {
                    String SJBBH = StringUtil.getValue(lateSsdjInfo, "SJBBH");
                    String SLH = StringUtil.getValue(lateSsdjInfo, "SLH");
                    Properties properties = FileUtil.readProperties("project.properties");
                    String SSDJ_WSXX_URL = properties.getProperty("SSDJ_WSXX_URL");
                    Map<String, Object> body = new HashMap<>();
                    List<Map<String, Object>> data = new ArrayList<>();
                    Map<String, Object> dataMap = new HashMap<>();
                    Map<String, Object> packageHead = new HashMap<>();
                    body.put("data", data);
                    body.put("packageHead", packageHead);
                    data.add(dataMap);
                    setPackageHead("1", "0", "99", SJBBH, packageHead);
                    dataMap.put("sjbbh", SJBBH);
                    dataMap.put("slh", SLH);
                    Map<String, String> headerMap = setBearHeadMap(exeId);
                    json = new JSONObject(body);
                    LOG_START_TIME = DateTimeUtil.getTimestamp();//请求开始时间戳
                    isCall = true;
                    result = HttpSendUtil.sendHttpPostJson(SSDJ_WSXX_URL, headerMap, json.toJSONString(), "UTF-8");
                    parseBdcSsdjWsxxResult(result, request);
                }
            }
        } catch (Exception e) {
            log.error("获取涉税登记信息出错，申报号为：" + exeId);
            log.error(e.getMessage(), e);
        } finally {
            if(isCall){//3涉税登记获取完税信息接口
                if (StringUtil.isNotEmpty(result)) {
                    Map<String,Object> resultMap = JSONObject.parseObject(result, Map.class);
                    if (StringUtil.isNotEmpty(resultMap.get("data"))) {
                        Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");
                        if(StringUtil.isNotEmpty(dataMap.get("packageHead"))){
                            Map<String,Object> packageHeadMap = (Map<String, Object>) dataMap.get("packageHead");
                            if(StringUtil.isNotEmpty(packageHeadMap.get("recodeZt"))
                                    &&packageHeadMap.get("recodeZt").toString().equals("0")){
                                LOG_RESPONSE_STATUS = 1;
                            }
                        }
                    }
                }
                //callLogService.save(LOG_START_TIME,json.toJSONString(), LOG_RESPONSE_STATUS,result,3);
                callLogService.save(LOG_START_TIME,json.toJSONString(), LOG_RESPONSE_STATUS,result,3,exeId);
            }
        }
        return new ModelAndView("bsdt/applyform/bdcqlc/gyjsjfwzydj/getSsdjInfoView");
    }

    /**
     * 描述:发送材料清单信息
     *
     * @author Madison You
     * @created 2020/12/1 9:25:00
     * @param
     * @return
     */
    private String sendBdcSsdjFileListInfo(Map<String, Object> saveMap ,
                                     Map<String, Object> variables,Map<String, String> headerMap) {
        String buyFileIds = StringUtil.getValue(variables, "SSDJ_BUY_FILEID");
        String slh = StringUtil.getValue(saveMap, "SLH");
        String msg = "";
        if (StringUtils.isNotEmpty(buyFileIds)) {
            Properties properties = FileUtil.readProperties("project.properties");
            String fileListUrl = properties.getProperty("SSDJ_FILE_LIST");
            String sjbbh = StringUtil.getValue(saveMap, "SJBBH");
            String[] buyFileIdArr = buyFileIds.split(",");
            for (int i=0; i<buyFileIdArr.length; i++) {
                String buyFileId = buyFileIdArr[i];
                Map<String,Object> body = new HashMap<>();
                List<Map<String,Object>> data = new ArrayList<>();
                Map<String,Object> packageHead = new HashMap<>();
                packageHead.put("jls", 1);
                packageHead.put("scrq", DateTimeUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS"));
                packageHead.put("sfcf", sfcf);
                packageHead.put("sjbbh",sjbbh);
                packageHead.put("sjblx","04");
                packageHead.put("dwdm","123501280503281865");
                packageHead.put("dwmc","平潭综合实验区行政服务中心不动产登记处(平潭综合实验区行政服务中心)");
                packageHead.put("xzqhsz_dm","350128");
                body.put("packageHead", packageHead);
                Map<String,Object> dataMap = new HashMap<>();
                Map<String, Object> fileMap = bdcSsdjService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"}, new Object[]{buyFileId});
                dataMap.put("code", "CL" + (i+1));
                dataMap.put("name", StringUtil.getValue(fileMap, "FILE_NAME"));
                dataMap.put("scrq", DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss"));
                dataMap.put("sjbbh", sjbbh);
                dataMap.put("slh", slh);
                dataMap.put("type", "符合减免条件的有关材料");
                dataMap.put("wjly", lybm);
                dataMap.put("uuid", StringUtil.getValue(fileMap, "FILE_ID"));
                data.add(dataMap);
                body.put("data", data);

                JSONObject json = new JSONObject(body);
                log.info("材料清单数据：" + json.toJSONString());
                String result = HttpSendUtil.sendHttpPostJson(fileListUrl, headerMap, json.toJSONString(), "UTF-8");
                if (StringUtils.isNotEmpty(result)) {
                    Map<String,Object> resultMap = JSONObject.parseObject(result, Map.class);
                    Map<String, Object> resultData = (Map<String, Object>) resultMap.get("data");
                    if(resultData!=null && resultData.size()>0) {
                        Map<String, Object> resultHead = (Map<String, Object>) resultData.get("packageHead");
                        Map<String, Object> recordMap = (Map<String, Object>) resultData.get("record");
                        String reMsgZT = "";
                        if(recordMap!=null && recordMap.size()>0) {
                            reMsgZT = StringUtil.getValue(recordMap, "reMsgZT");
                        }else if(resultHead!=null && resultHead.size()>0) {
                            reMsgZT = StringUtil.getValue(resultHead, "remsgZt");
                        }
                        if (!reMsgZT.contains("成功")) {
                            isTaxFileListSuccess = false;
                            log.info("发送不动产涉税登记材料清单数据失败，申报号：" + sjbbh + ",第"+i+"个附件接口失败状态：" + reMsgZT);
                            msg = "第"+(i+1)+"个附件材料清单接口失败,失败状态："+reMsgZT;
                            break;
                        } else {
                            log.info("发送不动产涉税登记材料清单数据申报号：" + sjbbh + ",附件ID："+buyFileId+",返回结果为：" + result);
                            isTaxFileListSuccess = true;
                        }
                    }else {
                        if(resultMap.get("resultMap")!=null) {
                            String status = (String)resultMap.get("resultMap");
                            msg = "第"+(i+1)+"个附件材料清单接口获取数据失败，status："+status;
                        }
                        msg = "第"+(i+1)+"个附件材料清单接口获取数据失败";
                        isTaxFileListSuccess = false;
                        break;
                    }
                }else {
                    isTaxFileListSuccess = false;
                    log.info("发送不动产涉税登记材料清单数据失败，申报号：" + sjbbh + ",第"+i+"个附件无接口返回值");
                    msg = "第"+(i+1)+"个附件材料清单接口无返回值";
                    break;
                }
            }
        }else {
            if(StringUtils.isEmpty(buyFileIds)){
                msg = "材料清单的buyFileIds为空";
            }
            if(StringUtils.isEmpty(slh)){
                msg = "材料清单的申请号(SLH)为空";
            }
            isTaxFileListSuccess = false;
        }
        return msg;
    }

    /**
     * 描述:发送材料附件信息
     *
     * @author Madison You
     * @created 2020/12/1 10:53:00
     * @param
     * @return
     */
    private String sendBdcSsdjFileContentInfo(Map<String, Object> saveMap,
                                            Map<String, Object> variables, Map<String, String> headerMap) {
        String buyFileIds = StringUtil.getValue(variables, "SSDJ_BUY_FILEID");
        String slh = StringUtil.getValue(saveMap, "SLH");
        String msg = "";
        if (StringUtils.isNotEmpty(buyFileIds) && StringUtils.isNotEmpty(slh)) {
            Properties properties = FileUtil.readProperties("project.properties");
            String fileDetailUrl = properties.getProperty("SSDJ_FILE_DETAIL");
            String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn");
            String sjbbh = StringUtil.getValue(saveMap, "SJBBH");
            String[] buyFileIdArr = buyFileIds.split(",");
            
            for (int i=0; i<buyFileIdArr.length; i++) {
                Map<String,Object> body = new HashMap<>();
                Map<String,Object> packageHead = new HashMap<>();
                List<Map<String,Object>> bdcClmxList = new ArrayList<>();
                packageHead.put("jls", 1);
                packageHead.put("scrq", DateTimeUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS"));
                packageHead.put("sfcf", sfcf);
                packageHead.put("sjbbh",sjbbh);
                packageHead.put("sjblx","05");
                packageHead.put("dwdm","123501280503281865");
                packageHead.put("dwmc","平潭综合实验区行政服务中心不动产登记处(平潭综合实验区行政服务中心)");
                packageHead.put("xzqhsz_dm","350128");
                body.put("packageHead", packageHead);
                
                String buyFileId = buyFileIdArr[i];
                Map<String,Object> bdcClmxMap = new HashMap<>();
                Map<String, Object> fileMap = bdcSsdjService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"}, new Object[]{buyFileId});
                bdcClmxMap.put("uuid", StringUtil.getValue(fileMap, "FILE_ID"));
                bdcClmxMap.put("xh", (i+1));
                bdcClmxMap.put("sjbbh", sjbbh);
                bdcClmxMap.put("slh", slh);
                bdcClmxMap.put("dept", lybm);
                bdcClmxMap.put("wjly", "xc/wl");
                bdcClmxMap.put("type", "符合减免条件的有关材料");
                bdcClmxMap.put("name", StringUtil.getValue(fileMap, "FILE_NAME"));
                bdcClmxMap.put("code", "CL" + (i+1));
                bdcClmxMap.put("arrfomat", StringUtil.getValue(fileMap, "FILE_TYPE"));
                bdcClmxMap.put("content", MaterDownloadUtil.getBase64CodeByUrlFile(
                        uploadFileUrlIn + StringUtil.getValue(fileMap, "FILE_PATH")));
                
                bdcClmxList.add(bdcClmxMap);
                body.put("data", bdcClmxList);
                
                JSONObject json = new JSONObject(body);
                String result = HttpSendUtil.sendHttpPostJson(fileDetailUrl, headerMap, json.toJSONString(), "UTF-8");
                log.info("接口调用返回结果：" + result);
                if (StringUtils.isNotEmpty(result)) {
                    Map<String,Object> resultMap = JSONObject.parseObject(result, Map.class);
                    Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");
                    if(dataMap!=null && dataMap.size()>0) {
                        Map<String, Object> packageHeadMap = (Map<String, Object>) dataMap.get("packageHead");
                        Map<String, Object> recordMap = (Map<String, Object>) dataMap.get("data");
                        String recodeZt = (String) packageHeadMap.get("recodeZt");
                        String remsgZt = (String) packageHeadMap.get("remsgZt");
                        if (!Objects.equals(recodeZt, "0")) {
                            isTaxFileSuccess = false;
                            log.info("发送不动产涉税登记材料基本信息及实体数据失败，申报号：" + sjbbh + ",第"+i+"个附件接口失败状态为" + recodeZt);
                            msg = "第"+(i+1)+"个附件信息接口失败，状态为:" + recodeZt+",报错信息为:"+remsgZt;
                            break;
                        } else {
                            log.info("发送不动产涉税登记材料基本信息及实体数据申报号：" + sjbbh + ",附件ID："+buyFileId+"返回结果为：" + result);
                            isTaxFileSuccess = true;
                        }
                    }else {
                        msg = "第"+(i+1)+"个附件信息接口获取数据失败";
                        isTaxFileSuccess = false;
                        break;
                    }
                }else {
                    isTaxFileSuccess = false;
                    log.info("发送不动产涉税登记材料基本信息及实体数据失败，申报号：" + sjbbh + ",第"+i+"个附件接口无返回值" );
                    msg = "第"+(i+1)+"个附件信息接口无返回值";
                    break;
                }
            }
        }else {
            if(StringUtils.isEmpty(buyFileIds)){
                msg = "附件信息的buyFileIds为空";
            }
            if(StringUtils.isEmpty(slh)){
                msg = "附件信息的申请号(SLH)为空";
            }
            isTaxFileSuccess = false;
        }
        return msg;
    }

    /**
     * 描述:解析完税信息
     *
     * @author Madison You
     * @created 2020/7/1 11:51:00
     * @param
     * @return
     */
    private void parseBdcSsdjWsxxResult(String result,HttpServletRequest request) {
        Map<String, Object> requestMap = new HashMap<>();
        if (StringUtils.isNotEmpty(result)) {
            Map<String,Object> resultMap = JSONObject.parseObject(result, Map.class);
            Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");
            if (dataMap != null) {
                Map<String, Object> parseDataMap = (Map<String, Object>) dataMap.get("data");
                if (parseDataMap != null) {
                    Map<String, Object> recordMap = (Map<String, Object>) parseDataMap.get("record");
                    Map<String,Object> packageHeadMap = (Map<String, Object>) dataMap.get("packageHead");
                    String recodeZt = (String) packageHeadMap.get("recodeZt");
                    if (recodeZt.equals("0")) {
                        String sjbbh = StringUtil.getValue(recordMap, "sjbbh");
                        /*获取此交易id的涉税申报数据，若是存在附件，则不继续上传*/
                        Map<String, Object> ssdjMap = bdcSsdjService.getLateSsdjInfoBySjbbh(sjbbh);
                        if (recordMap != null) {
                            requestMap.put("sjbbh", sjbbh);
                            requestMap.put("slh", StringUtil.getValue(recordMap, "slh"));
                            requestMap.put("reMsg", StringUtil.getValue(recordMap, "reMsg"));
                            Map<String, Object> bdcWsxxMap = (Map<String, Object>) recordMap.get("bdcWsxx");
                            if (bdcWsxxMap != null) {
                                /*完税信息*/
                                requestMap.put("csfse", StringUtil.getValue(bdcWsxxMap, "csfse"));
                                requestMap.put("zyfse", StringUtil.getValue(bdcWsxxMap, "zyfse"));
                                requestMap.put("operator", StringUtil.getValue(bdcWsxxMap, "operator"));
                                requestMap.put("yjsfze", StringUtil.getValue(bdcWsxxMap, "yjsfze"));
                                requestMap.put("zyfse", StringUtil.getValue(bdcWsxxMap, "zyfse"));
                                requestMap.put("csfse", StringUtil.getValue(bdcWsxxMap, "csfse"));
                                requestMap.put("pgdj", StringUtil.getValue(bdcWsxxMap, "pgdj"));
                                requestMap.put("pgjg", StringUtil.getValue(bdcWsxxMap, "pgjg"));

                                String content = StringUtil.getValue(bdcWsxxMap, "content");
                                String fileId = StringUtil.getValue(ssdjMap, "FILE_ID");
                                if (StringUtils.isNotEmpty(fileId)) {
                                    requestMap.put("fileId", fileId);
                                } else {
                                    String uploadFileId = uploadSsdjWsxxFile(content, sjbbh);
                                    if (StringUtils.isNotEmpty(uploadFileId)) {
                                        Map<String, Object> fileSaveMap = new HashMap<>();
                                        fileSaveMap.put("FILE_ID", uploadFileId);
                                        bdcSsdjService.saveOrUpdate(fileSaveMap, "T_BDCQLC_SSDJ_RETURNINFO",
                                                ssdjMap.get("SSDJ_ID").toString());
                                        requestMap.put("fileId", uploadFileId);
                                    }
                                }
                            }
                        }
                    } else {
                        requestMap.put("reMsg", StringUtil.getValue(recordMap, "reMsgZT"));
                    }
                }
            }
        }
        request.setAttribute("requestMap", requestMap);
    }


    /**
     * 描述:解析结果（）
     *
     * @author Madison You
     * @created 2020/6/22 9:22:00
     * @param
     * @return
     */
    private Map<String,Object> parseBdcSsdjResult(String result,Map<String,Object> returnMap , Map<String,Object> variables) {
        Map<String, Object> saveMap = new HashMap<>();
        if (StringUtils.isNotEmpty(result)) {
            Map<String,Object> resultMap = JSONObject.parseObject(result, Map.class);
            Object status = resultMap.get("status");
            if (status != null) {
                returnMap.put("msg", "发送失败：" + resultMap.get("message"));
            } else {
                Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");
                if (dataMap != null) {
                    Map<String, Object> parseDataMap = (Map<String, Object>) dataMap.get("data");
                    if (parseDataMap != null) {
                        Map<String, Object> recordMap = (Map<String, Object>) parseDataMap.get("record");
                        Map<String,Object> packageHeadMap = (Map<String, Object>) dataMap.get("packageHead");
                        String recodeZt = (String) packageHeadMap.get("recodeZt");
                        saveMap.put("EXE_ID", variables.get("EXE_ID"));
                        saveMap.put("SJBBH", recordMap.get("sjbbh"));
                        saveMap.put("SLH", recordMap.get("slh"));
                        saveMap.put("CODE", recodeZt);
                        saveMap.put("RES_JSON", result);
                        if (recodeZt.equals("0")) {
                            Map<String, Object> busMap = new HashMap<>();
                            busMap.put("IS_TAX_SUCCESS", "1");
                            bdcSsdjService.saveOrUpdate(busMap, "T_BDCQLC_GYJSJFWZYDJ", StringUtil.getValue(variables, "YW_ID"));
                            bdcSsdjService.saveOrUpdate(saveMap, "T_BDCQLC_SSDJ_RETURNINFO", null);
                            returnMap.put("success", true);
                            returnMap.put("msg", "发送成功,受理编号为：" + recordMap.get("slh"));
                        } else if (recodeZt.equals("-1")) {
                            returnMap.put("success", false);
                            returnMap.put("msg", "发送失败：" + recordMap.get("reMsgZT"));
                        }

                    }
                }
            }
        }
        return saveMap;
    }

    /**
     * 描述:上传涉税登记完税信息附件
     *
     * @author Madison You
     * @created 2020/7/2 9:23:00
     * @param
     * @return
     */
    private String uploadSsdjWsxxFile(String content,String sjbbh) {
        String uploadFileId = "";
        if (StringUtils.isNotEmpty(content)) {
            Properties properties = FileUtil.readProperties("project.properties");
            String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
            String url = uploadFileUrl + "upload/file";// 上传路径
            String app_id = "0001";// 分配的用户名
            String password = "bingo666";// 分配的密码
            String responesCode = "UTF-8";// 编码
            String uuId = UUIDGenerator.getUUID();
            String fileName = uuId + ".pdf";//文件名称
            Map<String, Object> param = new HashMap<>();
            SysUser loginUser = AppUtil.getLoginUser();
            param.put("uploaderId", loginUser.getUserId());// 上传人ID
            param.put("uploaderName", loginUser.getUsername()); // 上传人姓名
            param.put("typeId", "0");// 上传类型ID，默认0
            param.put("name", fileName);// 上传附件名
            param.put("attachKey", "");// 材料编码
            param.put("busTableName", "T_BDCQLC_SSDJ_RETURNINFO");// 业务表名
            param.put("busRecordId", sjbbh);// 业务表ID
            String result = HttpRequestUtil.sendBase64FilePost(url, content, responesCode, app_id, password, param);
            if (StringUtils.isNotEmpty(result)) {
                Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
                Map<String,Object> data = (Map) resultMap.get("data");
                uploadFileId = StringUtil.getValue(data, "fileId");
            }
        }
        return uploadFileId;
    }

    /**
     * 描述:设置存量房请求体
     *
     * @author Madison You
     * @created 2020/6/22 8:36:00
     * @param
     * @return
     */
    private void setClfBody(Map<String, Object> dataMap, Map<String, Object> packageHead, Map<String, Object> variables) {
        String exeId = StringUtil.getValue(variables, "EXE_ID");
        String uuid = exeId;
        /*判断是否重发*/
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String,Object> ssdjMap = bdcSsdjService.getLateSsdjInfo(exeId);
            if (ssdjMap != null) {
                sfcf = "1";
                dataMap.put("slh", ssdjMap.get("SLH"));
                uuid = ssdjMap.get("SJBBH").toString();
            }
        }
        setPackageHead("1", sfcf, "02" , uuid , packageHead);
        dataMap.put("bcjydj", StringUtil.getValue(variables, "SSDJ_FWXX_BCJYDJ"));
        dataMap.put("bcjyje", StringUtil.getValue(variables, "SSDJ_FWXX_BCJYJE"));
        dataMap.put("bdcdyh", StringUtil.getValue(variables, "SSDJ_FWXX_BDCDYH"));
        dataMap.put("bz", StringUtil.getValue(variables, "SSDJ_FWXX_BZ"));
        dataMap.put("ccmj", "");
        dataMap.put("cg", "");
        dataMap.put("cgqk", "");
        dataMap.put("cxDm", StringUtil.getValue(variables, "SSDJ_FWXX_CX"));
        dataMap.put("dj", StringUtil.getValue(variables, "SSDJ_FWXX_DJ"));
        dataMap.put("dlwzys", "");
        dataMap.put("dy", "");
        dataMap.put("fclxDm", StringUtil.getValue(variables, "SSDJ_FWXX_FCLX"));
        dataMap.put("fcxzDm", StringUtil.getValue(variables, "SSDJ_FWXX_FCXZ"));
        dataMap.put("fczbh", "");
        dataMap.put("fjh", StringUtil.getValue(variables, "SSDJ_FWXX_FJH"));
        dataMap.put("fwdz", StringUtil.getValue(variables, "SSDJ_FWXX_FWDZ"));
        dataMap.put("fwszdjdxzDm", StringUtil.getValue(variables, "SSDJ_FWXX_FWSZDXZJD"));
        dataMap.put("fwszdswjgDm", StringUtil.getValue(variables, "SSDJ_FWXX_FWSDNSJG"));
        dataMap.put("fwszdxzqhszDm", StringUtil.getValue(variables, "SSDJ_FWXX_XZQH"));
        dataMap.put("fwzh", "");
        dataMap.put("htbh", StringUtil.getValue(variables, "SSDJ_FWXX_JYHTBH"));
        dataMap.put("htje", StringUtil.getValue(variables, "SSDJ_FWXX_HTJE"));
        dataMap.put("htqdsj", formateDateTime(StringUtil.getValue(variables, "SSDJ_FWXX_HTQDSJ"), 10));
        dataMap.put("htsjsj", "");
        dataMap.put("htydfwjfsj", StringUtil.getValue(variables, "SSDJ_FWXX_JFSJ"));
        dataMap.put("jcnf", formateDateTime(StringUtil.getValue(variables, "SSDJ_FWXX_JCNF"), 4));
        dataMap.put("js", "");
        dataMap.put("jzjglxDm", "");
        dataMap.put("jzmj", StringUtil.getValue(variables, "SSDJ_FWXX_JZMJ"));
        dataMap.put("lc", "");
        dataMap.put("ljqk", "");
        dataMap.put("lydc", "");
        dataMap.put("mk", "");
        dataMap.put("pqDm", StringUtil.getValue(variables, "SSDJ_FWXX_PQDM"));
        dataMap.put("pqmc", StringUtil.getValue(variables, "SSDJ_FWXX_PQMC"));
        dataMap.put("qszydxDm", StringUtil.getValue(variables, "SSDJ_FWXX_QSZYDX"));
        dataMap.put("qszyfsDm", StringUtil.getValue(variables, "SSDJ_FWXX_QSZYFS"));
        dataMap.put("qszyytDm", StringUtil.getValue(variables, "SSDJ_FWXX_QSZYYT"));
        dataMap.put("sfcf", sfcf);
        dataMap.put("sjbbh", uuid);
        dataMap.put("sjjfsj", "");
        dataMap.put("tdsyzbh", "");
        dataMap.put("xh", "1");
        dataMap.put("ywdt", StringUtil.getValue(variables, "SSDJ_FWXX_YWDT"));
        dataMap.put("zjjglxdh", "");
        dataMap.put("zjjgmc", "");
        dataMap.put("zlc", "");
        dataMap.put("zxqk", "");
        dataMap.put("zytdmj", "");
        dataMap.put("zzmj", "");
        List<Map<String,Object>> bdcFcGyrxxList = new ArrayList<>();
        dataMap.put("bdcFcGyrxx", bdcFcGyrxxList);
        int i = 0;
        /*承受方*/
        String buyJsonInfo = StringUtil.getValue(variables,"buyJsonInfo");
        if (StringUtils.isNotEmpty(buyJsonInfo)) {
            List<Map> buyList = JSONArray.parseArray(buyJsonInfo, Map.class);
            for (Map<String, Object> buyMap : buyList) {
                Map<String,Object> bdcFcGyrxxMap = new HashMap<>();
                bdcFcGyrxxMap.put("fwtcDm", StringUtil.getValue(buyMap,"SSDJ_BUY_FWTC"));
                bdcFcGyrxxMap.put("gjDm", StringUtil.getValue(buyMap,"SSDJ_BUY_GJ"));
                bdcFcGyrxxMap.put("jyfe", StringUtil.getValue(buyMap,"SSDJ_BUY_JYFE"));
                bdcFcGyrxxMap.put("lxdh", StringUtil.getValue(buyMap,"SSDJ_BUY_LXDH"));
                bdcFcGyrxxMap.put("lxdz", StringUtil.getValue(buyMap,"SSDJ_BUY_LXDZ"));
                bdcFcGyrxxMap.put("scqdfwcb", "");
                bdcFcGyrxxMap.put("scqdfwfsDm", "");
                bdcFcGyrxxMap.put("scqdfwsj", "");
                bdcFcGyrxxMap.put("sfyws", StringUtil.getValue(buyMap,"SSDJ_BUY_SFYWS"));
                bdcFcGyrxxMap.put("sjbbh", uuid);
                bdcFcGyrxxMap.put("ssxxlxdh", StringUtil.getValue(buyMap,"SSDJ_BUY_SSXXLXDH"));
                bdcFcGyrxxMap.put("szfe", StringUtil.getValue(buyMap,"SSDJ_BUY_SZFE"));
                bdcFcGyrxxMap.put("xh", ++i);
                bdcFcGyrxxMap.put("xm", StringUtil.getValue(buyMap,"SSDJ_BUY_XM"));
                bdcFcGyrxxMap.put("zjhm", StringUtil.getValue(buyMap,"SSDJ_BUY_ZJHM"));
                bdcFcGyrxxMap.put("zjlxDm", StringUtil.getValue(buyMap,"SSDJ_BUY_ZJLX"));
                bdcFcGyrxxMap.put("zrfcsfbz", "1");
                bdcFcGyrxxList.add(bdcFcGyrxxMap);
            }
        }

        String sellJsonInfo = StringUtil.getValue(variables,"sellJsonInfo");
        if (StringUtils.isNotEmpty(sellJsonInfo)) {
            List<Map> sellList = JSONArray.parseArray(sellJsonInfo, Map.class);
            for (Map<String, Object> sellMap : sellList) {
                Map<String,Object> bdcFcGyrxxMap = new HashMap<>();
                bdcFcGyrxxMap.put("fwtcDm", StringUtil.getValue(sellMap,"SSDJ_SELL_FWTC"));
                bdcFcGyrxxMap.put("gjDm", StringUtil.getValue(sellMap,"SSDJ_SELL_GJ"));
                bdcFcGyrxxMap.put("jyfe", StringUtil.getValue(sellMap,"SSDJ_SELL_JYFE"));
                bdcFcGyrxxMap.put("lxdh", StringUtil.getValue(sellMap,"SSDJ_SELL_LXDH"));
                bdcFcGyrxxMap.put("lxdz", StringUtil.getValue(sellMap,"SSDJ_SELL_LXDZ"));
                bdcFcGyrxxMap.put("scqdfwcb", StringUtil.getValue(sellMap,"SSDJ_SELL_SCQDFWCB"));
                bdcFcGyrxxMap.put("scqdfwfsDm", StringUtil.getValue(sellMap,"SSDJ_SELL_SCQDFWFS"));
                bdcFcGyrxxMap.put("scqdfwsj", StringUtil.getValue(sellMap,"SSDJ_SELL_SCQDFWSJ"));
                bdcFcGyrxxMap.put("sfyws", StringUtil.getValue(sellMap,"SSDJ_SELL_SFYWS"));
                bdcFcGyrxxMap.put("sjbbh", uuid);
                bdcFcGyrxxMap.put("ssxxlxdh", StringUtil.getValue(sellMap,"SSDJ_SELL_SSXXLXDH"));
                bdcFcGyrxxMap.put("szfe", StringUtil.getValue(sellMap,"SSDJ_SELL_SZFE"));
                bdcFcGyrxxMap.put("xh", ++i);
                bdcFcGyrxxMap.put("xm", StringUtil.getValue(sellMap,"SSDJ_SELL_XM"));
                bdcFcGyrxxMap.put("zjhm", StringUtil.getValue(sellMap,"SSDJ_SELL_ZJHM"));
                bdcFcGyrxxMap.put("zjlxDm", StringUtil.getValue(sellMap,"SSDJ_SELL_ZJLX"));
                bdcFcGyrxxMap.put("zrfcsfbz", "0");
                bdcFcGyrxxList.add(bdcFcGyrxxMap);
            }
        }
    }

    /**
     * 描述:设置增量房请求体
     *
     * @author Madison You
     * @created 2020/6/19 15:11:00
     * @param
     * @return
     */
    private void setZlfBody(Map<String,Object> dataMap , Map<String,Object> packageHead , Map<String,Object> variables) {
        String exeId = StringUtil.getValue(variables, "EXE_ID");
        String uuid = exeId;
        dataMap.put("slh", "");
        /*判断是否重发*/
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String,Object> ssdjMap = bdcSsdjService.getLateSsdjInfo(exeId);
            if (ssdjMap != null) {
                sfcf = "1";
                dataMap.put("slh", ssdjMap.get("SLH"));
                uuid = ssdjMap.get("SJBBH").toString();
            }
        }
        setPackageHead("1", sfcf, "01" , uuid , packageHead);
        dataMap.put("bdcdyh", StringUtil.getValue(variables, "SSDJ_FYXX_BDCDYH"));
        dataMap.put("cxDm", StringUtil.getValue(variables, "SSDJ_FYXX_CXDM"));
        dataMap.put("dj", StringUtil.getValue(variables, "SSDJ_FYXX_DJ"));
        dataMap.put("dqyskje", StringUtil.getValue(variables, "SSDJ_FYXX_DQYSKJE"));
        dataMap.put("dqyskssyf", StringUtil.getValue(variables, "SSDJ_FYXX_DQYSKSSYF"));
        dataMap.put("fwdz", StringUtil.getValue(variables, "SSDJ_FYXX_FWDZ"));
        dataMap.put("fwszdjdxzDm", StringUtil.getValue(variables, "SSDJ_FYXX_FWSZDXZJD"));
        dataMap.put("fwszdswjgDm", StringUtil.getValue(variables, "SSDJ_FYXX_FWSDNSJG"));
        dataMap.put("fwszdxzqhszDm", StringUtil.getValue(variables, "SSDJ_FYXX_XZQH"));
        dataMap.put("htje", StringUtil.getValue(variables, "SSDJ_FYXX_HTJE"));
        dataMap.put("htqdsj", formateDateTime(StringUtil.getValue(variables, "SSDJ_FYXX_HTQDSJ"), 10));
        dataMap.put("jyjg", StringUtil.getValue(variables,"SSDJ_FYXX_JYJG"));
        dataMap.put("jzjglxDm", StringUtil.getValue(variables,"SSDJ_FYXX_JZJGLX"));
        dataMap.put("jzmj", StringUtil.getValue(variables,"SSDJ_FYXX_JZMJ"));
        dataMap.put("kfqymc", StringUtil.getValue(variables,"SSDJ_FYXX_KFQYMC"));
        dataMap.put("kfqynsrsbh", StringUtil.getValue(variables,"SSDJ_FYXX_NSRSBH"));
        dataMap.put("qszydxDm", StringUtil.getValue(variables,"SSDJ_FYXX_QSZYDX"));
        dataMap.put("qszyfsDm", StringUtil.getValue(variables,"SSDJ_FYXX_QSZYFS"));
        dataMap.put("qszyytDm", StringUtil.getValue(variables,"SSDJ_FYXX_QSZYYT"));
        dataMap.put("sjbbh", uuid);
        dataMap.put("zlc", StringUtil.getValue(variables,"SSDJ_FYXX_FWZLC"));
        dataMap.put("bz", StringUtil.getValue(variables,"SSDJ_FYXX_FYXXBZ"));
        dataMap.put("dy", "");
        dataMap.put("fjh", "");
        dataMap.put("fwzh", "");
        dataMap.put("htbh", StringUtil.getValue(variables,"SSDJ_FYXX_JYHTBH"));
        dataMap.put("lc", "");
        dataMap.put("tnmj", "");
        dataMap.put("xh", "1");

        List<Map<String,Object>> bdcFcGyrxxList = new ArrayList<>();
        dataMap.put("bdcFcGyrxx", bdcFcGyrxxList);
        String buyJsonInfo = StringUtil.getValue(variables,"buyJsonInfo");
        if (StringUtils.isNotEmpty(buyJsonInfo)) {
            List<Map> buyList = JSONArray.parseArray(buyJsonInfo, Map.class);
            int i = 0;
            for (Map<String, Object> buyMap : buyList) {
                i++;
                Map<String,Object> bdcFcGyrxxMap = new HashMap<>();
                bdcFcGyrxxList.add(bdcFcGyrxxMap);
                bdcFcGyrxxMap.put("fwtcDm", StringUtil.getValue(buyMap, "SSDJ_BUY_FWTC"));
                bdcFcGyrxxMap.put("gjDm", StringUtil.getValue(buyMap, "SSDJ_BUY_GJ"));
                bdcFcGyrxxMap.put("jyfe", StringUtil.getValue(buyMap, "SSDJ_BUY_JYFE"));
                bdcFcGyrxxMap.put("lxdh", StringUtil.getValue(buyMap, "SSDJ_BUY_LXDH"));
                bdcFcGyrxxMap.put("lxdz", StringUtil.getValue(buyMap, "SSDJ_BUY_LXDZ"));
                bdcFcGyrxxMap.put("sfyws", StringUtil.getValue(buyMap, "SSDJ_BUY_SFYWS"));
                bdcFcGyrxxMap.put("sjbbh", uuid);
                bdcFcGyrxxMap.put("szfe", StringUtil.getValue(buyMap,"SSDJ_BUY_SZFE"));
                bdcFcGyrxxMap.put("xm", StringUtil.getValue(buyMap,"SSDJ_BUY_XM"));
                bdcFcGyrxxMap.put("zjhm", StringUtil.getValue(buyMap,"SSDJ_BUY_ZJHM"));
                bdcFcGyrxxMap.put("zjlxDm", StringUtil.getValue(buyMap,"SSDJ_BUY_ZJLX"));
                bdcFcGyrxxMap.put("zrfcsfbz", "1");
                bdcFcGyrxxMap.put("scqdfwcb", "");
                bdcFcGyrxxMap.put("scqdfwfsDm", "");
                bdcFcGyrxxMap.put("scqdfwsj", "");
                bdcFcGyrxxMap.put("xh", i);
                bdcFcGyrxxMap.put("ssxxlxdh", StringUtil.getValue(buyMap,"SSDJ_BUY_SSXXLXDH"));
            }
        }
    }



    /**
     * 描述:设置包请求头
     *
     * @author Madison You
     * @created 2020/6/19 15:26:00
     * @param
     * @return
     */
    private void setPackageHead(String jsl, String sfcf , String sjblx,String uuid , Map<String,Object> packageHead) {
        packageHead.put("jls", jsl);
        packageHead.put("scrq", DateTimeUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
        packageHead.put("sfcf", sfcf);
        packageHead.put("sjbbh",uuid);
        packageHead.put("sjblx",sjblx);
    }


    /**
     * 描述:登录并设置bear请求头
     *
     * @author Madison You
     * @created 2020/6/19 8:50:00
     * @param 
     * @return 
     */
    private Map<String,String> setBearHeadMap(String exeId) {
        Map<String,String> headMap = new HashMap<>();
        Map<String,String> tokenHeadMap = new HashMap<>();
        JSONObject json = new JSONObject();
        Properties properties = FileUtil.readProperties("project.properties");
        String ssdjLoginUrl = properties.getProperty("SSDJ_LOGIN_URL");
        String ssdjLoginUsername = properties.getProperty("SSDJ_LOGIN_USERNAME");
        String ssdjLoginPassword = properties.getProperty("SSDJ_LOGIN_PASSWORD");
        setUsualHeadMap(headMap, exeId);
        json.put("username", ssdjLoginUsername);
        json.put("password", ssdjLoginPassword);
        String result = HttpSendUtil.sendHttpPostJson(ssdjLoginUrl, headMap, json.toJSONString(), "UTF-8");
        try{
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultJson = JSONObject.parseObject(result);
                String token = resultJson.getString("token");
                if (StringUtils.isNotEmpty(token)) {
                    setUsualHeadMap(tokenHeadMap, exeId);
                    tokenHeadMap.put("Authorization",token);
                }
            }
        } catch (Exception e) {
            log.error("涉税登记获取token解析错误：" + result);
            log.error(e.getMessage(), e);
        }
        return tokenHeadMap;
    }


    /**
     * 描述:设置通用请求头
     *
     * @author Madison You
     * @created 2020/6/19 8:33:00
     * @param
     * @return
     */
    private void setUsualHeadMap(Map<String, String> headMap,String exeId) {
        String str = new Date().getTime()/1000 + "vkpfWU4Rcz2Ba8lwJ5RpAfMCyE2GXMtR" +
                "123456789abcdefg" +new Date().getTime()/1000;
        String signature = "";
        try{
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            //signature = new String(Hex.encodeHex(instance.digest(str.getBytes())));
            signature = Hex.encodeHexString(instance.digest(str.getBytes()));
        } catch (Exception e) {
            log.error("涉税登记登录加密错误");
            log.error(e.getMessage(), e);
        }
        headMap.put("x-tif-paasid","bigdatainter");
        headMap.put("x-tif-timestamp", new Date().getTime() / 1000 + "");
        headMap.put("x-tif-signature",signature);
        headMap.put("x-tif-nonce","123456789abcdefg");

        if (StringUtils.isNotEmpty(exeId)) {
            Map<String,Object> exeMap = callLogService.getItemInfo(exeId);
            if(StringUtil.isNotEmpty(exeMap)){
                headMap.put("re-app",(String)exeMap.get("IMPL_DEPART"));
            }
            /*String departName = bdcSsdjService.getItemChildDeptByExeId(exeId);
            if (StringUtils.isNotEmpty(departName)) {
                headMap.put("re-app",departName);
            }*/
        }
    }


    /**
     * 描述:时间转换
     *
     * @author Madison You
     * @created 2020/6/19 16:06:00
     * @param
     * @return
     */
    private String formateDateTime(String dateTime,int count) {
        String dateTimeParse = "";
        if (StringUtils.isNotEmpty(dateTime)) {
            if (dateTime.length() >= count) {
                dateTimeParse = dateTime.substring(0, count);
            }
        }
        return dateTimeParse;
    }

}
