/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.GenPlatReqUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.ProjectFullTestService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import sun.misc.BASE64Decoder;

/**
 * 描述 全测合一Controller
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2021年6月18日 上午9:21:15
 */
@Controller
@RequestMapping("/projectFullTestController")
public class ProjectFullTestController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectFullTestController.class);

    /**
     * projectFullTestService
     */
    @Resource
    private ProjectFullTestService projectFullTestService;
    
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 项目列表视图
     * @param request
     * @return
     */
    @RequestMapping(params = "fullTestList")
    public ModelAndView projectList(HttpServletRequest request) {
        return new ModelAndView("project/fullTest/fullTestList");
    }
    
    /**
     * 项目列表数据获取
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "fullTestListData")
    public void fullTestListData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "ASC");
        filter.addSorted("T.FULLTEST_ID", "ASC");
        List<Map<String, Object>> fullTestList = projectFullTestService.findBySqlFilter(filter);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if(fullTestList!=null && fullTestList.size()>0) {
            for(Map<String, Object> fullTestMap : fullTestList) {
                String chbglx = (String) fullTestMap.get("CHBGLX");
                String chbglxName = dictionaryService.getDicNames("CHBGLX",chbglx);
                fullTestMap.put("CHBGLX_NAME", chbglxName);
                resultList.add(fullTestMap);
            }
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), resultList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到全测合一项目详情页(前台)
     * @param request
     * @return
     */
    @RequestMapping("/fullTestProject")
    public ModelAndView fullTestProject(HttpServletRequest request) {
        String project_code = request.getParameter("project_code");
        request.setAttribute("project_code", project_code);
        return new ModelAndView("project/fullTest/fullTestProject");
    }
    
    /**
     * 跳转到全测合一项目详情页
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "saveFullTestProject")
    @ResponseBody
    public AjaxJson saveFullTestProject(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> fullTestMap = new HashMap<String, Object>();
        String chbglxValue = (String)variables.get("chbglxValue");
        String projectCode = (String)variables.get("PROJECT_CODE");
        
        fullTestMap.put("PROJECT_CODE", projectCode);
        fullTestMap.put("PROJECT_NAME", variables.get("PROJECT_NAME"));
        if(chbglxValue!=null && !"".equals(chbglxValue)) {
            Map<String, Object> xmjbxxbInfo = projectFullTestService.getByJdbc("SPGL_XMJBXXB", 
                    new String[] { "PROJECT_CODE" }, new Object[] { projectCode });
            String zjfwcs_result = "";
            //推送数据给中介服务超市
            String enterprise_name = "";
            Properties properties = FileUtil.readProperties("conf/config.properties");
            if(xmjbxxbInfo!=null && xmjbxxbInfo.size()>0) {
                String ZJFWCS_URL = properties.getProperty("ZJFWCS_URL");
                String lerepInfo = (String) xmjbxxbInfo.get("LEREP_INFO");
                List<Map> lerepInfoList = JSONArray.parseArray(lerepInfo,Map.class);
                Map<String, Object> lerepInfoMap = lerepInfoList.get(0);
                enterprise_name = (String)lerepInfoMap.get("enterprise_name");
                String lerep_certno = (String)lerepInfoMap.get("lerep_certno");
                String contact_name = (String)lerepInfoMap.get("contact_name");
                String contact_tel = (String)lerepInfoMap.get("contact_tel");
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("projectName", (String)variables.get("PROJECT_NAME"));
                paramMap.put("enterpriseName", enterprise_name);
                paramMap.put("socialCreditCode", lerep_certno);
                paramMap.put("contactName", contact_name);
                paramMap.put("contactPhone", contact_tel);
                ZJFWCS_URL += "?projectPassphrase="+projectCode;
                zjfwcs_result = GenPlatReqUtil.sendPostParams(ZJFWCS_URL, paramMap);
            }else {
                j.setSuccess(false);
                j.setMsg("不存在！"+projectCode+"项目！");
                return j;
            }
            //推送数据给多规合一
            boolean isDghySendSuccess = true;
            if(enterprise_name!=null && !"".equals(enterprise_name)) {
                String DGHY_URL = properties.getProperty("DGHY_URL");
                //DGHY_URL += "?Pid="+projectCode;
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("pid", projectCode);
                paramMap.put("projectName", (String)variables.get("PROJECT_NAME"));
                paramMap.put("jsdw", enterprise_name);
                String cldw = "福州市勘测院";
                if(xmjbxxbInfo.get("CLDW")!=null) {
                    cldw = (String) xmjbxxbInfo.get("CLDW");
                }
                paramMap.put("cldw", cldw);
                paramMap.put("xmtssj", sdf.format(new Date()));
                String[] chbglxArr = chbglxValue.split(",");
                for(int i=0; i<chbglxArr.length; i++) {
                    String chbglx = chbglxArr[i];
                    //String chbglxName = dictionaryService.getDicNames("CHBGLX",chbglx);
                    paramMap.put("bkType", chbglx);
                    //paramMap.put("bkName", chbglxName);
                    String paramJson = JSON.toJSONString(paramMap);
                    //String dghy_result = GenPlatReqUtil.sendPostParams(DGHY_URL, paramMap);
                    String dghy_result = HttpRequestUtil.sendBdcPost(DGHY_URL, paramJson);
                    Map<String, Object> dghyResultMap = JSON.parseObject(dghy_result);
                    Integer dghySuccess = (Integer)dghyResultMap.get("code");
                    if(dghySuccess!=null && dghySuccess == 200) {
                        continue;
                    }else {
                        isDghySendSuccess = false;
                        break;
                    }
                }
            }
            Map<String, Object> zjfwcsResultMap = JSON.parseObject(zjfwcs_result);
            Boolean zjfwcsSuccess = (Boolean)zjfwcsResultMap.get("success");
            if(zjfwcsSuccess && isDghySendSuccess) {
                //删除旧数据
                projectFullTestService.remove("T_PROJECT_FULLTEST", "PROJECT_CODE", projectCode.split(","));
                //信息入库
                String[] chbglxArr = chbglxValue.split(",");
                for(int i=0; i<chbglxArr.length; i++) {
                    String chbglx = chbglxArr[i];
                    fullTestMap.put("CHBGLX", chbglx);
                    fullTestMap.put("CREATE_TIME", sdf.format(new Date()));
                    projectFullTestService.saveOrUpdate(fullTestMap, "T_PROJECT_FULLTEST", "");
                }
                j.setSuccess(true);
                j.setMsg("提交成功");
            }else {
                j.setSuccess(false);
                if(!zjfwcsSuccess) {
                    j.setMsg("推送中介服务超市失败！"+zjfwcsResultMap.get("message"));
                }else {
                    j.setMsg("推送多规合一失败！");
                }
            }
        }else {
            j.setSuccess(false);
            j.setMsg("请选择测绘报告类型！");
            
        }
        return j;
    }
    

    
    /**
     * 
     * @Description 获取全测合一附件接口
     * @author Luffy Cai
     * @date 2021年6月22日
     * @param request
     * @param response
     * @return
     * @throws IOException Map<String,Object>
     */
    @RequestMapping("/getFullTestFile")
    @ResponseBody
    public Map<String, Object> getFullTestFile(HttpServletRequest request, HttpServletResponse response)
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
            String projectNo = resultJson.getString("projectNo") == null ? "" : resultJson.getString("projectNo");
            String projectName = resultJson.getString("projectName") == null ? "" : resultJson.getString("projectName");
            String bkName = resultJson.getString("bkName") == null ? "" : resultJson.getString("bkName");
            String bkType = resultJson.getString("bkType") == null ? "" : resultJson.getString("bkType");
            String content = resultJson.getString("content") == null ? "" : resultJson.getString("content");
            String fileType = resultJson.getString("fileType") == null ? "" : resultJson.getString("fileType");
            String bsjstime = resultJson.getString("bsjstime") == null ? "" : resultJson.getString("bsjstime");
            String bkbbbh = resultJson.getString("bkbbbh") == null ? "" : resultJson.getString("bkbbbh");
            if (StringUtils.isNotEmpty(projectNo) && StringUtils.isNotEmpty(projectName)
                    && StringUtils.isNotEmpty(bkName) && StringUtils.isNotEmpty(bkType)
                    && StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(fileType)) {
                // 根据参数获取信息
                Map<String, Object> newSaveMap = new HashMap<String, Object>();
                newSaveMap.put("PROJECT_CODE", projectNo);
                newSaveMap.put("PROJECT_NAME", projectName);
                newSaveMap.put("CHBGLX", bkType);
                newSaveMap.put("BSJSTIME", bsjstime);
                newSaveMap.put("BKBBBH", bkbbbh);
                String FULLTEST_ID = projectFullTestService.saveOrUpdate(newSaveMap, "T_PROJECT_FULLTEST", null);
                if(StringUtils.isNotEmpty(FULLTEST_ID)) {
                    // 上传证照文件
                    String fileResult = uploadBase64File(content, fileType, "T_PROJECT_FULLTEST", FULLTEST_ID);
                    if (StringUtils.isNotEmpty(fileResult)) {
                        Map<String, Object> resultMap = JSON.parseObject(fileResult, Map.class);
                        Map<String, Object> fullTestMap = new HashMap<String, Object>();
                        String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                        Map<String, Object> data = (Map) resultMap.get("data");
                        fullTestMap.put("FULLTESTFILEID", data.get("fileId"));
                        fullTestMap.put("FULLTESTFILEPATH", data.get("filePath"));
                        fullTestMap.put("FULLTEST_ID", FULLTEST_ID);
                        fullTestMap.put("REPORT_NAME", bkName);
                        fullTestMap.put("REPORT_TIME", createTime);
                        projectFullTestService.saveOrUpdate(fullTestMap, "T_PROJECT_FULLTEST", FULLTEST_ID);
                        log.info("全测合一项目代码：" + projectNo + "-保存附件成功！，文件id为：" + data.get("fileId"));
                    }
                    returnMap.put("status", 0);
                    returnMap.put("message", "请求成功!");
                }else {
                    returnMap.put("status", 500);
                    returnMap.put("message", "信息保存失败!");
                }
                
            } else {
                returnMap.put("status", 500);
                returnMap.put("message", "请求失败,缺失请求参数!");
            }
        } else {
            returnMap.put("status", 500);
            returnMap.put("message", "请求失败,请求方式不规范!");
        }
        return returnMap;
    }

    private Map<String, Object> saveOrUpdate(Map<String, Object> newSaveMap, String string, Object object) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * @Description 描述 Base64文件上传
     * @author Luffy Cai
     * @date 2021年6月22日
     * @param base64Code
     * @param fileType
     * @param busTableName
     * @param busRecordId
     * @return String
     */
    public String uploadBase64File(String base64Code, String fileType, String busTableName, String busRecordId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String result = "";
        String unid = UUIDGenerator.getUUID();
        BASE64Decoder decoder = new BASE64Decoder();
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "qchywjsc");// 上传人ID
                param.put("uploaderName", "全测合一文件上传"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", unid + "." + fileType);// 上传附件名
                param.put("attachKey", "");// 材料编码
                param.put("busTableName", busTableName);// 业务表名
                param.put("busRecordId", busRecordId);// 业务表ID
                byte[] bys = decoder.decodeBuffer(base64Code);
                result = HttpRequestUtil.sendByteFilePost(url, bys, responesCode, app_id, password, param);
                        //HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
                return result;
            } catch (Exception e) {
                log.error("证照文件上传错误，busRecordId：" + busRecordId);
            }
        }
        return result;
    }
    
}