/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.dao.BdcQlcMaterGenAndSignDao;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;

/**
 * 描述 不动产全流程全程网办材料生成及签章Controller
 * 
 * @author Luffy Cai
 * @created 2020年8月17日 上午10:49:40
 */
@Controller
@RequestMapping("/bdcQlcMaterGenAndSignController")
public class BdcQlcMaterGenAndSignController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcQlcMaterGenAndSignController.class);

    /**
     * 引入service
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    /**
     * 所引入的dao
     */
    @Resource
    private BdcQlcMaterGenAndSignDao dao;

    /**
     * 
     * @Description 签署流程回调接口
     * @author Luffy Cai
     * @date 2020年8月17日
     * @param request
     * @return Map<String,Object>
     * @throws IOException
     */
    @RequestMapping("/signFlowsCallBack")
    @ResponseBody
    public Map<String, Object> signFlowsCallBack(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HashMap<String, Object> returnMap = new HashMap<>();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String acceptjson = "";
        BufferedReader br = new BufferedReader(
                new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        acceptjson = sb.toString();
        if (StringUtils.isNotEmpty(acceptjson)) {
            JSONObject resultJson = JSONObject.parseObject(acceptjson);
            log.info("签署流程回调接口数据:" + resultJson.toString());
            String action = resultJson.getString("action") == null ? "" : resultJson.getString("action");
            String status = resultJson.getString("status") == null ? "" : resultJson.getString("status");
            String flowId = resultJson.getString("flowId") == null ? "" : resultJson.getString("flowId");
            String createTime = resultJson.getString("createTime") == null ? "" : resultJson.getString("createTime");
            String flowType = resultJson.getString("flowType") == null ? "" : resultJson.getString("flowType");
            String resultDescription = resultJson.getString("resultDescription") == null ? ""
                    : resultJson.getString("resultDescription");
            if (StringUtils.isNotEmpty(action)) {
                int errCode = 0;
                //List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> list;
                if (action.equals("SIGN_FLOW_FINISH")) {// 终结状态回调
                    // 获取完结状态回调数据
                    Map<String, Object> variables = bdcQlcMaterGenAndSignService.getSignFlowFinishMap(resultJson);
                    bdcQlcMaterGenAndSignService.savaSignFlowsInfo(variables, flowId);
                    // 保存签署文档信息
                    String finishDocUrlBeans = resultJson.getString("finishDocUrlBeans") == null ? ""
                            : resultJson.getString("finishDocUrlBeans");
                    if (StringUtils.isNotEmpty(finishDocUrlBeans)) {
                        list = JSON.parseObject(finishDocUrlBeans, List.class);
                        for (Map<String, Object> docInfo : list) {
                            bdcQlcMaterGenAndSignService.savaMaterSignInfo(docInfo, flowId, status);
                        }
                    }
                    //保存签章状态
                    StringBuffer sql = new StringBuffer("UPDATE T_BDCQLC_MATERSIGNINFO T ");
                    sql.append("SET T.IS_SIGN=? WHERE T.SIGN_FLOWID=?");
                    dao.executeSql(sql.toString(),new Object[]{status,flowId});
                    
                }

                if (action.equals("SIGN_FLOW_UPDATE")) {// 过程状态更新
                    // 获取过程更新状态回调数据
                    Map<String, Object> variables = bdcQlcMaterGenAndSignService.getSignFlowUpdateMap(resultJson);
                    bdcQlcMaterGenAndSignService.savaSignFlowsInfo(variables, flowId);
                }

                if (action.equals("SIGN_FLOW_NOTIFY")) {// 签署通知
                    String waitingToSignAccount = resultJson.getString("waitingToSignAccount") == null ? ""
                            : resultJson.getString("waitingToSignAccount");
                    list = JSON.parseObject(waitingToSignAccount, List.class);
                    if (null != list && list.size() > 0) {
                        Map<String, Object> variables = new HashMap<String, Object>();
                        for (Map<String, Object> accountInfo : list) {
                            String accountId = (String) accountInfo.get("accountId");
                            String accountUid = (String) accountInfo.get("accountUid");
                            String name = (String) accountInfo.get("name");
                            String type = accountInfo.get("type").toString();
                            variables.put("ACTION", action);
                            variables.put("BEGINTIME", createTime);
                            variables.put("SIGN_FLOWID", flowId);
                            variables.put("FLOW_TYPE", flowType);
                            variables.put("RESDESCRIPTION", resultDescription);
                            variables.put("STATUS", status);
                            variables.put("ACCOUNTID", accountId);
                            variables.put("ACCOUNTUID", accountUid);
                            variables.put("SIGN_NAME", name);
                            variables.put("TYPE", type);
                            bdcQlcMaterGenAndSignService.savaSignFlowsInfo(variables, flowId);
                        }
                    }
                }
                returnMap.put("errCode", errCode);
                returnMap.put("msg", "请求成功!");
                log.info("签署流程号："+flowId+"-回调类型为："+action+"-签署回调通知成功！");
            } else {
                returnMap.put("errCode", 1);
                returnMap.put("msg", "缺失必要参数!");
                log.info("签署流程号："+flowId+"-回调通知缺失回调类型参数！");
            }
        } else {
            returnMap.put("errCode", 1);
            returnMap.put("msg", "请求失败,缺失请求参数!");
        }
        return returnMap;
    }

    /**
     * 
     * @Description 获取签章材料列表
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = "signFiles")
    public ModelAndView signFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        // 获取签章材料列表
        List<Map<String, Object>> materList = bdcQlcMaterGenAndSignService.findSignMaterListByExeId(exeId);
        request.setAttribute("materList", materList);
        return new ModelAndView("bsdt/applyform/bdcqlc/bdcQlcMaterSignList");
    }

    /**
     * 
     * @Description 跳转到签署过程明细页面
     * @author Luffy Cai
     * @date 2020年8月21日
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = "goSignFlowDetail")
    public ModelAndView goSignFlowDetail(HttpServletRequest request) {
        String flowId = request.getParameter("SIGN_FLOWID");
        request.setAttribute("flowId", flowId);
        return new ModelAndView("bsdt/applyform/bdcqlc/bdcQlcSignFlowInfo");
    }

    /**
     * 
     * @Description 获取签署流程信息列表
     * @author Luffy Cai
     * @date 2020年8月20日
     * @param request
     * @param response void
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        String flowId = request.getParameter("SIGN_FLOWID");
        List<Map<String, Object>> list = bdcQlcMaterGenAndSignService.findSignFlowListByflowId(flowId);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * @Description 下载签署文档
     * @author Luffy Cai
     * @date 2020年8月22日
     * @param request
     * @param response void
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downLoadSignMater")
    public void downLoadSignMater(HttpServletRequest request, HttpServletResponse response) {
        String ywId = request.getParameter("YW_ID").toString();
        Map<String, Object> materInfo = bdcQlcMaterGenAndSignService.getByJdbc("T_BDCQLC_MATERSIGNINFO",
                new String[] { "YW_ID" }, new Object[] { ywId });
        String materName = materInfo.get("MATER_NAME").toString();
        String downloadDocUrl = materInfo.get("DOWNLOAD_DOCURL").toString();
        String str1 = downloadDocUrl.substring(0, downloadDocUrl.indexOf("/esignproweb"));
        String str2 = downloadDocUrl.substring(str1.length() + 1, downloadDocUrl.length());
        downloadDocUrl = "http://192.168.130.114:8888/" + str2;
        try {
            downLoadFile(response, downloadDocUrl, materName);
        } catch (Exception e) {
            log.error("业务号："+ywId+" 下载文档错误，错误信息："+e.getMessage());
        }
    }
    
    /**
     * 
     * @Description 文件下载
     * @author Luffy Cai
     * @date 2020年8月23日
     * @param response
     * @param downloadDocUrl
     * @param fileName
     * @throws Exception void
     */
    public void downLoadFile(HttpServletResponse response, String downloadDocUrl, String fileName) throws Exception {
        URL url = new URL(downloadDocUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        response.reset();
        response.setContentType("application/x-msdownload");
        String newFileName = null;
        if (StringUtils.isNotEmpty(fileName)) {
            newFileName = fileName + ".pdf";
        } else {
            newFileName = new Date().getTime() + ".pdf";
        }
        // 采用中文文件名需要在此处转码
        newFileName = new String(newFileName.getBytes("GB2312"), "ISO_8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + newFileName);
        response.addHeader("Content-Length", "" + urlCon.getContentLength());
        while ((len = in.read(buffer)) > 0)
            out.write(buffer, 0, len);
        in.close();
        out.close();
    }
    
}
