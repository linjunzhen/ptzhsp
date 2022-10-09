/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.ZgjbylbxService;
import sun.util.logging.resources.logging;

/**
 * 描述 职工医疗保险业务控制层
 * @author Allin Lin
 * @created 2019年12月6日 下午3:54:22
 */
@Controller
@RequestMapping("/zgjbylbxController")
public class ZgjbylbxController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ZgjbylbxController.class);
    
    /**
     * 引入字典服务层 
     */
    @Resource
    public DictionaryService dictionaryService;
    /**
     * 引入ZgjbylbxService
     */
    @Resource
    public ZgjbylbxService zgjbylbxService;
    
    /**
     * 引入YbCommonService
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 单位信息查询弹窗界面(职工参保、续保)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "dwxxcxSelector")
    public ModelAndView dwxxcxSelector(HttpServletRequest request) {
        String type = request.getParameter("type");
        String view="";
        if (type != null && "cb".equals(type)) {
            view = "bsdt/applyform/ybqlc/zgjbylbxcb/selector/dwxxcxSelector";
        } else if (type != null && "xb".equals(type)) {
            view = "bsdt/applyform/ybqlc/zgjbylbxxb/selector/dwxxcxSelector";
        }
        return new ModelAndView(view);
    }
    
    /**
     * 单位花名册查询弹窗界面(参保)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "dwhmccxSelector")
    public ModelAndView dwhmccxSelector(HttpServletRequest request) {
        String unitNo = request.getParameter("unitNo");
        request.setAttribute("unitNo", unitNo);
        return new ModelAndView("bsdt/applyform/ybqlc/zgjbylbxcb/selector/dwhmccxSelector");
    }
    
    /**
     * 查询参保人弹窗界面-停保
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "cxcbrTBSelector")
    public ModelAndView cxcbrTBSelector(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/zgjbylbx/selector/cxcbrTBSelector");
    }
    
    /**
     * 险种列表信息(参保)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "findXZXX")
    public void findXZXX(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map>();
        String ywId = request.getParameter("ywId");
        String exeId = request.getParameter("exeId");
        //参保日期默认为次月1号(注意12月份的跨年)
        Calendar lastDate = Calendar.getInstance();
        int year = lastDate.get(Calendar.YEAR);//年
        int month = lastDate.get(Calendar.MONTH)+1;//月
        if(month == 12){
            lastDate.set(Calendar.YEAR,year+1);
            lastDate.set(Calendar.MONTH,0);
        }else{
            lastDate.set(Calendar.MONTH, month);
        }
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_ZGCB", 
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("XZXXINFO_JSON") != null
                    && !"[]".equals(record.get("XZXXINFO_JSON").toString())) {
                if (exeId != null && !"".equals(exeId)) {
                    Map<String, Object> execution = ybCommonService.getByJdbc("JBPM6_EXECUTION",
                            new String[] { "EXE_ID" }, new Object[] { exeId });
                    if (execution.get("CUR_STEPNAMES") != null
                            && "受理".equals(execution.get("CUR_STEPNAMES").toString())) {
                        //退回事项
                        //List<Map<String,Object>> diclist = dictionaryService.findByTypeCode("TypeOfInsurance");
                        List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                                "TypeOfInsurance", "310,610,510");
                        List xzxxlist = JSONObject.parseArray(record.get("XZXXINFO_JSON").toString());
                        for(Map<String,Object> dic : diclist){
                            Map<String,Object> xzxx = new HashMap<String, Object>();
                            String dicCode = dic.get("VALUE").toString();
                            boolean isPut = false;
                            for(int i=0; i<xzxxlist.size(); i++) {
                                Map xzInfo = (Map) xzxxlist.get(i);
                                if(xzInfo.get("aae140")!=null) {
                                    String xzType = xzInfo.get("aae140").toString();
                                    if(xzType.equals(dicCode)) {
                                        xzxx.put("aae140", xzType);
                                        if(xzInfo.get("aae030")!=null) {
                                            xzxx.put("aae030", xzInfo.get("aae030").toString());
                                        }
                                        if(xzInfo.get("aae031")!=null) {
                                            xzxx.put("aae031", xzInfo.get("aae031").toString());
                                        }
                                        if(xzInfo.get("aac031")!=null) {
                                            xzxx.put("aac031", xzInfo.get("aac031").toString());
                                        }
                                        xzxx.put("isCheck", true);
                                        list.add(xzxx);
                                        isPut = true;
                                        break;
                                    }
                                }
                            }
                            if(!isPut) {
                                xzxx.put("aae140", dic.get("VALUE"));
                                xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));
                                xzxx.put("aae031", "99991231");
                                xzxx.put("aac031", "1");
                                list.add(xzxx);
                            }
                        }
                    }else {
                        list = JSONObject.parseArray(record.get("XZXXINFO_JSON").toString());
                    }
                }else {
                    list = JSONObject.parseArray(record.get("XZXXINFO_JSON").toString());
                }
            }else{
                //List<Map<String,Object>> diclist = dictionaryService.findByTypeCode("TypeOfInsurance");
                List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                        "TypeOfInsurance", "310,610,510");
                for(Map<String,Object> dic : diclist){
                    Map<String,Object> xzxx = new HashMap<String, Object>();
                    xzxx.put("aae140", dic.get("VALUE"));
                    xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));
                    xzxx.put("aae031", "99991231");
                    xzxx.put("aac031", "1");
                    list.add(xzxx);
                }
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 单位信息查询(参保) 
     * @param request
     * @return
     */
    @RequestMapping(params = "findDWXXInfo")
    public void findDWXXInfo(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> dwxxList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("findDWXXInfo");
            params.put("token", tokenMap.get("token"));
            //log.info("单位信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "departQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    dwxxList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(dwxxList.size(), dwxxList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 单位花名册查询(参保)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "findDWHMCInfo")
    public void findDWHMCInfo(HttpServletRequest request, HttpServletResponse response) {    
        List<Map<String,Object>> dwhmcList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("findDWHMCInfo");
            params.put("token", tokenMap.get("token"));
            //log.info("单位花名册查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "rosterQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    dwhmcList = (List<Map<String, Object>>) JSON.parse(jsonString);
                }              
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
           log.info("获取token值失败！");
        }
        this.setListToJsonString(dwhmcList.size(), dwhmcList, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述    职工新参保数据推送医保系统
     * @author Allin Lin
     * @created 2019年11月25日 下午5:30:57
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushZgCb")
    @ResponseBody
    public AjaxJson pushZgCb(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        Map<String,Object> basic = new  HashMap<String, Object>();//基本信息
        Map<String,Object> insuCommon = new  HashMap<String, Object>();//参保信息      
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_ZGCB", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null && !"".equals(record)){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));
                    basic.put("aac002", record.get("DOCUMENT_NUM"));//证件号码
                    basic.put("aac058", record.get("DOCUMENT_TYPE"));//证件类型
                    basic.put("aac003", record.get("INSURED_NAME"));//姓名
                    basic.put("bae950", record.get("DOCUMENT_START_TIME"));//证件有效期始
                    basic.put("bae951", record.get("DOCUMENT_END_TIME"));//证件有效期止
                    basic.put("aac004", record.get("SEX"));//性别
                    basic.put("aac005", record.get("NATION"));//民族
                    basic.put("aac006", record.get("BIRTH_DATE"));//出生日期
                    basic.put("bac504", record.get("UNLABOR_RELATIONS_TIME"));//解除劳动关系时间
                    basic.put("aac033", record.get("DIFF_INSURED_STARTTIME"));//异地参保开始日期
                    basic.put("aac007", record.get("WORKING_DATE"));//参加工作日期
                    basic.put("aac009", record.get("POPULATE_NATURE"));//户口性质
                    basic.put("aab301", record.get("ADMIN_DIVISION"));//所属行政区划
                    basic.put("aac028", record.get("ENJOY_LEVEL"));//农民工标识
                    basic.put("aac017", record.get("MARITAL_STATUS"));//婚姻状况
                    basic.put("bae528", record.get("MOBILE_PHONE"));//手机号码 
                    basic.put("aae005", record.get("CONTACT_NUMBER"));//联系电话
                    basic.put("aae007", record.get("POST_CODE"));//邮政编码
                    basic.put("aae006", record.get("ADDRESS"));//通讯地址
                    basic.put("aac016", record.get("EMPLOYMENT_STATUS"));//就业状态    
                    basic.put("bac505", record.get("CADRE_SIGN"));//军转干部标志
                    basic.put("aac060", record.get("SURVIVAL_STATE"));//人员生存状态
                    basic.put("aac020", record.get("POST_NAME"));//职务
                    basic.put("aae013", record.get("REMARK"));//备注
                    insuCommon.put("aab001", record.get("UNIT_NUM"));//单位编号
                    insuCommon.put("bab511", record.get("UNIT_FILE"));//单位档案号
                    insuCommon.put("aab004", record.get("UNIT_NAME"));//单位名称
                    insuCommon.put("aab999", record.get("UNIT_INSURANCE"));//单位保险号
                    insuCommon.put("aaz065", record.get("BANK_CODE"));//银行行号
                    insuCommon.put("aab340", record.get("OPEN_BANK_NAME"));//开户行名称
                    insuCommon.put("aae010", record.get("PAYMENT_ACCOUNT"));//缴费账号
                    insuCommon.put("aae009", record.get("PAYMENT_ACCOUNT_NAME"));//缴费账号名
                    insuCommon.put("aac066", record.get("INSURED_IDENTITY"));//参保身份
                    insuCommon.put("aac040", record.get("DECLARATION_WARGS"));//申报工资
                    //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
                    SysUser sysUser = AppUtil.getLoginUser();
                    String jbIdcard = sysUser.getUsercard();
                    insuCommon.put("spry_aac002", jbIdcard);
                    pushInfo.put("basic", basic);
                    pushInfo.put("insuCommon", insuCommon);
                    List<Map<String, Object>> xzxx_json = (List<Map<String, Object>>) JSON
                            .parse(record.get("XZXXINFO_JSON").toString());
                    pushInfo.put("insuList", xzxx_json);
                    //log.info(JSON.toJSONString(pushInfo));
                    ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushZgcb"); 
                    if(ajaxJson.isSuccess()){              
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        ybCommonService.saveOrUpdate(record, "T_YBQLC_ZGCB", ywId);
                    }
                }else{
                    log.info("获取token值失败！");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("推送医保系统接口通讯异常！");
                }
            }
        }
        return ajaxJson;
    }
    
    
    /**
     * 描述    单位信息查询（续保）
     * @author Allin Lin
     * @created 2019年12月4日 下午3:31:53
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="xbDepartQueryData")
    public void xbDepartQueryData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("xbDepartQueryData");
            params.put("token", tokenMap.get("token"));
            //log.info("续保单位信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "xb_departQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length()>0){
                    list = (List<Map<String, Object>>) JSON.parse(jsonString);      
                }
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    参保人信息查询界面(续保)
     * @author Allin Lin
     * @created 2019年12月4日 下午3:42:05
     * @param request
     * @return
     */
    @RequestMapping(params = "cbrQueryForSelect")
    public ModelAndView cbrQueryForSelect(HttpServletRequest request){
        String DWBXH = request.getParameter("DWBXH");
        String DWMC = request.getParameter("DWMC");
        String DWBH = request.getParameter("DWBH");
        request.setAttribute("DWBXH", DWBXH);
        request.setAttribute("DWMC", DWMC);    
        request.setAttribute("DWBH", DWBH);       
        return new ModelAndView("bsdt/applyform/ybqlc/zgjbylbxxb/selector/showCbrQuery");
    }
    
    /**
     * 描述    参保人信息查询(续保)
     * @author Allin Lin
     * @created 2019年12月4日 下午3:45:04
     * @param request
     * @param response
     */
    @RequestMapping(params="cbrQueryData")
    public void cbrQueryData(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("cbrQueryData");
            params.put("token", tokenMap.get("token"));
            //System.out.println("续保参保人信息查询参数："+JSON.toJSONString(params));
           /* AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(params, "cbrQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length()>0){
                     list = (List<Map<String, Object>>) JSON.parse(jsonString);        
                }              
            }else{
                log.info(dataJson.getMsg());
            }*/
            String jsonString="[{\"aab999\":\"351100\",\"aab004\":\"测试单位\",\"aab001\":\"5\","
               + "\"aac002\":\"350321199711130725\",\"bac503\":\"12345678\",\"aac003\":\"测试人\","
               + "\"aac066\":\"e0\",\"aac058\":\"1\",\"aac031\":\"1\",},{\"aab999\":\"351102\",\"aab004\":\"平潭实验中学\","
               + "\"aab001\":\"5\",\"aac002\":\"350321199711130725\",\"bac503\":\"12345678\",\"aac003\":\"test\","
               + "\"aac066\":\"01\",\"aac058\":\"1\",\"aac031\":\"1\"}]";
            list = (List<Map<String, Object>>) JSON.parse(jsonString); 
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    获取参保人信息
     * @author Allin Lin
     * @created 2019年12月4日 下午3:53:45
     * @param request
     * @param response
     */
    @RequestMapping(params="cbrxxJson")
    public void cbrxxJson(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_ZGXB", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("CBRXX_JSON")!=null){
                list = (List<Map<String, Object>>)JSON.parse(record.get("CBRXX_JSON").toString());
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 描述     续保校验
     * @author Allin Lin
     * @created 2019年12月4日 下午3:57:40
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "cbrCheck")
    @ResponseBody
    public AjaxJson cbrCheck(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        //System.out.println("续保校验传递参数："+data);
        Map<String, Object> variables = (Map<String, Object>)JSON.parse(data.toString());
        variables.remove("rowIndex");
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            variables.put("token", tokenMap.get("token"));
            //ajaxJson = ybCommonService.commonAjaxJsonOfYb(variables, "checkXb");  
            ajaxJson.setSuccess(true);
            ajaxJson.setJsonString("{\"bae619\":\"校验通过\",\"bae620\":\"\"}");
            //ajaxJson.setMsg("处理失败：原因：证件号码不合格");
        }else{
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    /** 
     * 描述    续保信息推送医保
     * @author Allin Lin
     * @created 2019年12月4日 下午4:04:28
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushZgXb")
    @ResponseBody
    public AjaxJson pushZgXb(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        //System.out.println("续保保存传递参数：" + data);
        Map<String, Object> variables = (Map<String, Object>) JSON.parse(data.toString());
        Map<String, Object> tokenMap = ybCommonService.getToken();
        if ((boolean) tokenMap.get("success")) {
            variables.put("token", tokenMap.get("token"));
            // ajaxJson = ybCommonService.commonAjaxJsonOfYb(variables,"pushZgXb");
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("处理成功");
        } else {
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    /**
     * 描述    审核-后台保存参保人信息（续保）
     * @author Allin Lin
     * @created 2019年12月4日 下午4:06:47
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveCbrXx")
    @ResponseBody
    public AjaxJson saveCbrXx(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        String ywId = request.getParameter("ywId");
        //System.out.println("参保人信息："+data);
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_ZGXB", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            record.put("CBRXX_JSON", data);
            ybCommonService.saveOrUpdate(record, "T_YBQLC_ZGXB", ywId);
            ajaxJson.setSuccess(true);
        }
        return ajaxJson;
    }
    
    /**
     * 描述    后台获取参保人信息（停保）
     * @author Allin Lin
     * @created 2019年12月6日 上午10:20:53
     * @param request
     * @param response
     */
    @RequestMapping(params="tbCbrJson")
    public void tbCbrJson(HttpServletRequest request,HttpServletResponse response){
        List list = new ArrayList<Map>();//业务数据
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_ZGTB", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("CBRXX_JSON")!=null){
                list = JSONObject.parseArray(record.get("CBRXX_JSON").toString());
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述  查询参保人信息弹框界面（停保）   
     * @author Allin Lin
     * @created 2019年12月6日 上午10:24:00
     * @param request
     * @return
     */
    @RequestMapping(params = "cbrQuery")
    public ModelAndView cbrQuery(HttpServletRequest request){       
        return new ModelAndView("bsdt/applyform/ybqlc/zgjbylbxtb/selector/showCbrQuery");
    }
    
    /**
     * 描述    参保人信息查询（停保）
     * @author Allin Lin
     * @created 2019年12月6日 上午10:58:16
     * @param request
     * @param response
     */
    @RequestMapping(params="tbCbrQueryData")
    public void tbCbrQueryData(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("tbCbrQueryData");
            params.put("token", tokenMap.get("token"));
            log.info("续保参保人信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "tbCbrQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length()>0){
                     list = (List<Map<String, Object>>) JSON.parse(jsonString);        
                }              
            }else{
                log.info(dataJson.getMsg());
            }     
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 描述    停保信息推送医保
     * @author Allin Lin
     * @created 2019年12月6日 下午2:35:59
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushZgTb")
    @ResponseBody
    public AjaxJson pushZgTb(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> variables = new HashMap<String, Object>();
        String data = request.getParameter("data").toString();//基本信息
        String zdyy = request.getParameter("zdyy");
        String bgyy = request.getParameter("bgyy");
        String ifupdate = request.getParameter("ifupdate");
        String bz = request.getParameter("bz");//备注
        //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
        SysUser sysUser = AppUtil.getLoginUser();
        String jbIdcard = sysUser.getUsercard();
        variables.put("spry_aac002", jbIdcard);
        variables.put("aae160", bgyy);//变更原因
        variables.put("zdyy", zdyy);//中断原因(传中文)
        variables.put("ifupdate", ifupdate);//默认传0即可
        variables.put("aae013", bz);//备注
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        Map<String, Object> item = (Map<String, Object>) JSON.parse(data.toString());
        items.add(item);
        //log.info("基本信息："+JSON.toJSONString(items));
        variables.put("items", items);//基本信息
        Map<String, Object> tokenMap = ybCommonService.getToken();
        if ((boolean) tokenMap.get("success")) {
            variables.put("token", tokenMap.get("token"));
            ajaxJson = ybCommonService.pushInfoOfYb(variables,"pushZgTb");
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取token值失败！");
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    /**
     * 描述    审核-后台保存参保人信息（停保）
     * @author Allin Lin
     * @created 2019年12月6日 下午2:45:36
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveTbCbrXx")
    @ResponseBody
    public AjaxJson saveTbCbrXx(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        String ywId = request.getParameter("ywId");
        //System.out.println("参保人信息："+data);
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_ZGTB", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            record.put("CBRXX_JSON", data);
            ybCommonService.saveOrUpdate(record, "T_YBQLC_ZGTB", ywId);
            ajaxJson.setSuccess(true);
        }
        return ajaxJson;
    }
}
