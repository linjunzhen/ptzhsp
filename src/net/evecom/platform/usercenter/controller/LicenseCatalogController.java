/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.usercenter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.usercenter.service.LicenseCatalogService;

/**
 * 描述    证照目录
 * @author Danto Huang
 * @created 2020年3月4日 下午3:26:58
 */
@Controller
@RequestMapping("/licenseCatalogController")
public class LicenseCatalogController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(LicenseCatalogController.class);

    /**
     * 
     */
    @Resource
    private LicenseCatalogService licenseCatalogService;
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月4日 下午3:29:43
     * @param request
     * @return
     */
    @RequestMapping(params="licenseCatalogSelector")
    public ModelAndView licenseCatalogSelector(HttpServletRequest request){
        String BINDCATALOG_CODE = request.getParameter("BINDCATALOG_CODE");
        request.setAttribute("BINDCATALOG_CODE", BINDCATALOG_CODE);
        return new ModelAndView("usercenter/license/licenseCatalogSelector");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月4日 下午3:31:06
     * @param request
     * @param response
     */
    @RequestMapping(params="licenseCatalogData")
    public void licenseCatalogData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        PagingBean page = filter.getPagingBean();
        int start = page.getStart();
        int pageSize = page.getPageSize();
        int pageNo;
        if(start==0){
            pageNo = 1;
        }else{
            pageNo = start/pageSize+1;
        }
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String devbase_url = properties.getProperty("devbase_url");
        String grantcode = properties.getProperty("grantcode");
        Map<String,Object> postParam = new HashMap<String,Object>();
        postParam.put("servicecode", "findByLicenseCatalog");
        String CATALOG_NAME = request.getParameter("CATALOG_NAME");
        if(StringUtils.isNotEmpty(CATALOG_NAME)){
            postParam.put("CATALOG_NAME", CATALOG_NAME);
        }
        String CATALOG_CODE = request.getParameter("CATALOG_CODE");
        if(StringUtils.isNotEmpty(CATALOG_CODE)){
            postParam.put("CATALOG_CODE", CATALOG_CODE);
        }
        postParam.put("grantcode", grantcode);
        postParam.put("pageSize", String.valueOf(pageSize));
        postParam.put("pageNo", String.valueOf(pageNo));
        String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
        Map<String, Object> resultMap = (Map)JSON.parse(respContent);
        List<Map> datalist;
        int total;
        if((boolean) resultMap.get("success")){
            Map<String,Object> variables = (Map<String, Object>) JSON.parse((String) resultMap.get("resultMsg"));
            total = (int) variables.get("totalItems");
            JSONArray data = (JSONArray) variables.get("data");
            datalist = JSONObject.parseArray(data.toJSONString(), Map.class);
        }else{
            datalist = new ArrayList<Map>();
            total = 0;
        }
        this.setListToJsonString(total, datalist, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月5日 下午4:38:16
     * @param request
     * @param reponse
     */
    @RequestMapping(params="materBindSelected")
    public void materBindSelected(HttpServletRequest request,HttpServletResponse response){
        String BINDCATALOG_CODE = request.getParameter("BINDCATALOG_CODE");
        List<Map> datalist;
        int total;
        if(StringUtils.isNotEmpty(BINDCATALOG_CODE)){
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String devbase_url = properties.getProperty("devbase_url");
            String grantcode = properties.getProperty("grantcode");
            Map<String,Object> postParam = new HashMap<String,Object>();
            postParam.put("servicecode", "findByLicenseCatalog");
            postParam.put("CATALOG_CODE", BINDCATALOG_CODE);
            postParam.put("grantcode", grantcode);
            String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            if((boolean) resultMap.get("success")){
                Map<String,Object> variables = (Map<String, Object>) JSON.parse((String) resultMap.get("resultMsg"));
                total = (int) variables.get("totalItems");
                JSONArray data = (JSONArray) variables.get("data");
                datalist = JSONObject.parseArray(data.toJSONString(), Map.class);
            }else{
                datalist = new ArrayList<Map>();
                total = 0;
            }
        }else{
            datalist = new ArrayList<Map>();
            total = 0;
        }
        this.setListToJsonString(total, datalist, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    跳转共享材料选择界面
     * @author Danto Huang
     * @created 2020年3月16日 下午3:57:01
     * @param request
     * @return
     */
    @RequestMapping("/goShareMaterAndLicense")
    public ModelAndView goShareMaterAndLicense(HttpServletRequest request){
        String holderCode = request.getParameter("holderCode");
        String creditlevel = request.getParameter("creditlevel");
        String PERSON_CREDIT_USE = request.getParameter("PERSON_CREDIT_USE");
        request.setAttribute("holderCode", holderCode);
        request.setAttribute("creditlevel", creditlevel);
        request.setAttribute("PERSON_CREDIT_USE", PERSON_CREDIT_USE);
        return new ModelAndView("usercenter/license/shareMaterAndLicenseSelector");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月16日 下午4:19:25
     * @param request
     * @param response
     */
    @RequestMapping("/shareMaterAndLicenseData")
    public void shareMaterAndLicenseData(HttpServletRequest request,HttpServletResponse response){
        String holderCode = request.getParameter("holderCode");
        String catalogName=request.getParameter("CATALOG_NAME");
        String catalogCode=request.getParameter("CATALOG_CODE");
        List<Map> datalist;
        int total;
        if(StringUtils.isNotEmpty(holderCode)){
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String devbase_url = properties.getProperty("devbase_url");
            String grantcode = properties.getProperty("grantcode");
            Map<String,Object> postParam = new HashMap<String,Object>();
            postParam.put("servicecode", "findHolderCodeByLicenseData");
            postParam.put("HOLDER_CODE", holderCode);
            postParam.put("CATALOG_NAME",catalogName);
            postParam.put("CATALOG_CODE",catalogCode);
            postParam.put("grantcode", grantcode);
            String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            if((boolean) resultMap.get("success")){
                Map<String,Object> variables = (Map<String, Object>) JSON.parse((String) resultMap.get("resultMsg"));
                JSONArray data = (JSONArray) variables.get("data");
                datalist = JSONObject.parseArray(data.toJSONString(), Map.class);
                total = datalist.size();
            }else{
                datalist = new ArrayList<Map>();
                total = 0;
            }
        }else{
            datalist = new ArrayList<Map>();
            total = 0;
        }
        
        this.setListToJsonString(total, datalist, null, JsonUtil.EXCLUDE, response);
        
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月19日 下午7:26:28
     * @param request
     * @return
     */
    @RequestMapping("/licenseFiles")
    public ModelAndView licenseFiles(HttpServletRequest request){
        String SERIALNUMBER = request.getParameter("SERIALNUMBER");
        request.setAttribute("SERIALNUMBER", SERIALNUMBER);
        return new ModelAndView("usercenter/license/licenseFiles");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月19日 下午7:27:46
     * @param request
     * @param response
     */
    @RequestMapping("/licenseFilesData")
    public void licenseFilesData(HttpServletRequest request,HttpServletResponse response){
        String SERIALNUMBER = request.getParameter("SERIALNUMBER");
        List<Map> datalist;
        int total;
        if(StringUtils.isNotEmpty(SERIALNUMBER)){
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String devbase_url = properties.getProperty("devbase_url");
            String grantcode = properties.getProperty("grantcode");
            Map<String,Object> postParam = new HashMap<String,Object>();
            postParam.put("servicecode", "findNumberByLicenseData");
            postParam.put("SERIALNUMBER", SERIALNUMBER);
            postParam.put("grantcode", grantcode);
            String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            if((boolean) resultMap.get("success")){
                Map<String,Object> variables = (Map<String, Object>) JSON.parse((String) resultMap.get("resultMsg"));
                Map<String,Object> data = (Map<String, Object>) variables.get("data");
                datalist = JSONObject.parseArray(data.get("FILE_JSON").toString(), Map.class);
                total = datalist.size();
            }else{
                datalist = new ArrayList<Map>();
                total = 0;
            }
        }else{
            datalist = new ArrayList<Map>();
            total = 0;
        }
        
        this.setListToJsonString(total, datalist, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年3月19日 下午10:31:09
     * @param request
     * @return
     */
    @RequestMapping("/uploadShareMater")
    @ResponseBody
    public AjaxJson uploadShareMater(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String SERIALNUMBER = request.getParameter("SERIALNUMBER");
        String MATER_CODE = request.getParameter("MATER_CODE");
        String busTableName = request.getParameter("busTableName");
        String uploadUserId = request.getParameter("uploadUserId");
        String uploadUserName = request.getParameter("uploadUserName");
        if(StringUtils.isNotEmpty(SERIALNUMBER)){
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String devbase_url = properties.getProperty("devbase_url");
            String grantcode = properties.getProperty("grantcode");
            Map<String,Object> postParam = new HashMap<String,Object>();
            postParam.put("servicecode", "findNumberByLicenseData");
            postParam.put("SERIALNUMBER", SERIALNUMBER);
            postParam.put("grantcode", grantcode);
            String respContent = HttpSendUtil.sendPostParams(devbase_url, postParam);
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            if((boolean) resultMap.get("success")){
                Map<String,Object> variables = (Map<String, Object>) JSON.parse((String) resultMap.get("resultMsg"));
                Map<String,Object> data = (Map<String, Object>) variables.get("data");
                List<Map> datalist = JSONObject.parseArray(data.get("FILE_JSON").toString(), Map.class);
                String fileResource = "";//材料来源（1：智能填单）
                if(data.get("DATA_RESOURCE")!=null){
                     fileResource = data.get("DATA_RESOURCE").toString();
                }
                if(datalist!=null&&datalist.size()>0){
                    for(Map<String,Object> file : datalist){
                        Map<String,Object> attach = new HashMap<String, Object>();
                        String filepath = file.get("FILE_PATH").toString();
                        String realpath = filepath.substring(filepath.lastIndexOf("uploadfile"));
                        String fileType = filepath.substring(filepath.lastIndexOf(".")+1);
                        attach.put("FILE_NAME", file.get("FILE_NAME"));
                        attach.put("FILE_PATH", realpath);
                        attach.put("FILE_TYPE", fileType);
                        attach.put("ATTACH_KEY", MATER_CODE);
                        attach.put("SQFS", 1);
                        attach.put("SFSQ", 1);
                        attach.put("PLAT_TYPE", 1);
                        attach.put("BUS_TABLENAME", busTableName);
                        attach.put("UPLOADER_ID", uploadUserId);
                        attach.put("UPLOADER_NAME", uploadUserName);
                        if("image".equals(file.get("ATTACH_TYPE"))){
                            attach.put("IS_IMG", 1);
                        }else{
                            attach.put("IS_IMG", 0);
                        }
                        if("1".equals(fileResource)){
                            attach.put("IS_HIS", 2); //智能填单
                        }else{
                            attach.put("IS_HIS", 1);//历史共享材料
                        }
                        
                        String attachId = licenseCatalogService.saveOrUpdate(attach, "T_MSJW_SYSTEM_FILEATTACH", null);
                        file.put("attachId", attachId);
                    }
                    j.setJsonString(JSON.toJSONString(datalist));
                    j.setSuccess(true);
                }
            }else{
                j.setSuccess(false);
                j.setMsg("材料上传失败");
            }
        }else{
            j.setSuccess(false);
            j.setMsg("材料上传失败");
        }
        return j;
    }
}
