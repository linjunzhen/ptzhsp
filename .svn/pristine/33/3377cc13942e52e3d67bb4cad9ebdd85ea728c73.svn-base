/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.sb.service.SbCommonService;
import net.evecom.platform.sb.service.SbQyzgcxService;
import net.evecom.platform.sb.util.SbCommonUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.PrintAttachService;
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
import java.util.*;


/**
 * 描述  企业职工基本养老保险信息查询服务Controller
 * @author Allin Lin
 * @created 2020年2月14日 下午4:18:50
 */
@Controller
@RequestMapping("/sbQyzgcxController")
public class SbQyzgcxController extends BaseController{

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SbQyzgcxController.class);


    /**
     * 引入sbCommonService
     */
    @Resource
    private SbCommonService sbCommonService;

    /**
     * 引入字典服务层
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 打印类service
     */
    @Resource
    private PrintAttachService printAttachService;
    /**
     * sbQyzgcxService
     */
    @Resource
    private SbQyzgcxService sbQyzgcxService;
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;

    /**
     * 描述    个人信息列表（不弹框）
     * @author Allin Lin
     * @created 2020年3月4日 下午5:48:28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getpersonInfo")
    @ResponseBody
    public AjaxJson getpersonInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object> persons= Maps.newHashMap();
        List<Map<String, Object>> infos= Lists.newArrayList();
        AjaxJson ajaxJson  = new AjaxJson();
        Map<String,Object> params;//查询参数
        String token = SbCommonUtil.getRealToken();
        if(null!=token){
            params = BeanUtil.getMapFromRequest(request);
            params.remove("findDWJBXX");
            log.info("获取人员基本和参保信息："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result = SbCommonUtil.sbCommonPost(params, "personInsureInfo", token,"2");
            if("200".equals(result.get("code"))){
                String data = result.get("data").toString();
                if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)
                        && !"[]".equals(data)){
                    infos = (List<Map<String, Object>>)JSON.parse(result.get("data").toString());
                    if(infos.size()>0){
                        Map<String,Object> personInfo=infos.get(0);
                        persons.putAll(personInfo);
                        persons.put("infos",infos);
                        ajaxJson.setJsonString(JSON.toJSONString(persons));
                        ajaxJson.setSuccess(true);
                        log.info("获取人员基本和参保信息返回结果："+JSON.toJSONString(infos));
                    }
                }else {
                    ajaxJson.setMsg("人员基本和参保信息查询为空！");
                    ajaxJson.setSuccess(false);
                }
            }else {
                ajaxJson.setMsg(result.get("message").toString());
                ajaxJson.setSuccess(false);
            }
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }
    
    /**
     * 跳转到打印缴费明细信息页面
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "printPaiedDetail")
    public ModelAndView printPaiedDetail(HttpServletRequest request) {
        String exeId = request.getParameter("EFLOW_EXEID");
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String printData = request.getParameter("data");//打印数据
        String GRBH = request.getParameter("GRBH");
        String SHBZHM = request.getParameter("SHBZHM");
        String XM = request.getParameter("XM");
        //log.info("打印数据："+printData);
        Map<String, Object>  regTable=sbQyzgcxService.getRegTableForPaiedDetail(printData);
        Map<String,Object> templateData=new HashMap<>();
        //Map<String,Object> buscord=exeDataService.getBuscordMap(exeId);
        templateData.put("GRBH",GRBH);
        templateData.put("SHBZHM",SHBZHM);
        templateData.put("XM",XM);
        templateData.put("PRINT_DATE", DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd"));
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        request.setAttribute("TemplateData", templateData);
        request.setAttribute("exeId", exeId);
        request.setAttribute("regTable",regTable);
        return new ModelAndView("wsbs/readtemplate/printPaiedTemplate");
    }
    /**
     * 描述    缴费明细列表（不弹框）
     * @author Allin Lin
     * @created 2020年3月4日 下午5:48:28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "findPayDetail")
    @ResponseBody
    public AjaxJson findPayDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
        List<Map<String, Object>> infos= Lists.newArrayList();
        AjaxJson ajaxJson  = new AjaxJson();
        String token = SbCommonUtil.getRealToken();
        if(null!=token){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("findPayDetail");
            //log.info("获取人员缴费信息："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result = SbCommonUtil.sbCommonPost(params, "payDetail", token,"2");
            if("200".equals(result.get("code"))){
                String data = result.get("data").toString();
                if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data) 
                        && !"[]".equals(data)){
                    infos = (List<Map<String, Object>>)JSON.parse(result.get("data").toString());
                    if(infos.size()>0){
                        ajaxJson.setJsonString(JSON.toJSONString(infos));
                        ajaxJson.setSuccess(true);
                        log.info("获取人员缴费明细结果："+JSON.toJSONString(infos));
                    }
                }else {
                    ajaxJson.setMsg("人员缴费明细查询为空！");
                    ajaxJson.setSuccess(false);
                }
            }else {
                ajaxJson.setMsg(result.get("message").toString());
                ajaxJson.setSuccess(false);
            }
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }

    /**
     * 描述    人员参保信息列表（不弹框）
     * @author Allin Lin
     * @created 2020年3月4日 下午5:48:28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "insuredDetail")
    @ResponseBody
    public AjaxJson insuredDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
        List<Map<String, Object>> infos= Lists.newArrayList();
        AjaxJson ajaxJson  = new AjaxJson();
        String token = SbCommonUtil.getRealToken();
        if(null!=token){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            log.info("获取人员参保信息："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result = SbCommonUtil.sbCommonPost(params, "insuredDetail", token,"2");
            if("200".equals(result.get("code"))){
                String data = result.get("data").toString();
                if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)){
                    infos = (List<Map<String, Object>>)JSON.parse(result.get("data").toString());
                    if(infos.size()>0){
                        ajaxJson.setJsonString(JSON.toJSONString(infos));
                    }
                }
            }
            log.info("获取人员参保信息返回结果："+JSON.toJSONString(infos));
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }
    /**
     * 描述 人员参保信息
     *
     * @author Allin Lin
     * @created 2019年10月11日 上午10:35:37
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "insuredDetailJson")
    public void insuredDetailJson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGCX",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("INSUREDDETAILJSON") != null
                    && !"[]".equals(record.get("INSUREDDETAILJSON").toString())) {
                list = JSONObject.parseArray(record.get("INSUREDDETAILJSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述 获取险种信息(参保登记)
     *
     * @author Allin Lin
     * @created 2019年10月11日 上午10:35:37
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "payDetailJson")
    public void payDetailJson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGCX",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("PAYDETAILJSON") != null
                    && !"[]".equals(record.get("PAYDETAILJSON").toString())) {
                list = JSONObject.parseArray(record.get("PAYDETAILJSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述 获取险种信息(参保登记)
     *
     * @author Allin Lin
     * @created 2019年10月11日 上午10:35:37
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "xzxxJson")
    public void xzxxJson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGCX",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("QYJY_XZXXJSON") != null
                    && !"[]".equals(record.get("QYJY_XZXXJSON").toString())) {
                list = JSONObject.parseArray(record.get("QYJY_XZXXJSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
}
