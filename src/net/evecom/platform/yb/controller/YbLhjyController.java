/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
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
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;

/**
 * 描述    灵活就业基本医疗保险业务控制类
 * @author Danto Huang
 * @created 2019年10月16日 上午9:14:37
 */
@Controller
@RequestMapping("/ybLhjyController")
public class YbLhjyController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbLhjyController.class);
    /**
     * 
     */
    @Resource
    private YbCommonService ybCommonService;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 
     * 描述    单位信息查询（参保）
     * @author Danto Huang
     * @created 2019年10月16日 上午9:18:01
     * @param request
     * @return
     */
    @RequestMapping(params = "departQueryForSelect")
    public ModelAndView departQueryForSelect(HttpServletRequest request){
        return new ModelAndView("bsdt/applyform/ybqlc/lhjycbdj/showDepartQuery");
    }

    /**
     * 
     * 描述    查询单位(参保)
     * @author Danto Huang
     * @created 2019年10月16日 上午9:47:09
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping(params="departQueryData")
    public void departQueryData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("departQueryData");
            params.put("token", tokenMap.get("token"));
            log.info("单位信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "departQuery");
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
     * 
     * 描述    单位花名册查询
     * @author Danto Huang
     * @created 2019年10月16日 上午9:18:01
     * @param request
     * @return
     */
    @RequestMapping(params = "rosterQuery")
    public ModelAndView rosterQuery(HttpServletRequest request){
        String departNo = request.getParameter("departNo");
        request.setAttribute("departNo", departNo);
        return new ModelAndView("bsdt/applyform/ybqlc/lhjycbdj/rosterQuery");
    }

    /**
     * 
     * 描述    查询花名册
     * @author Danto Huang
     * @created 2019年10月16日 上午9:47:09
     * @param request
     * @param response
     */
    @RequestMapping(params="rosterQueryData")
    public void rosterQueryData(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("rosterQueryData");
            params.put("token", tokenMap.get("token"));
            log.info("单位花名册信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "rosterQuery");
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
     * 
     * 描述    获取险种信息
     * @author Danto Huang
     * @created 2019年11月1日 上午10:15:24
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params="xzxxJson")
    public void xzxxJson(HttpServletRequest request,HttpServletResponse response){
        List list = new ArrayList<Map<String, Object>>();
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
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_LHJYCBDJ", 
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("XZXX_JSON") != null
                    && !"[]".equals(record.get("XZXX_JSON").toString())) {
                if (exeId != null && !"".equals(exeId)) {
                    Map<String, Object> execution = ybCommonService.getByJdbc("JBPM6_EXECUTION",
                            new String[] { "EXE_ID" }, new Object[] { exeId });
                    if (execution.get("CUR_STEPNAMES") != null
                            && "受理".equals(execution.get("CUR_STEPNAMES").toString())) {
                        //退回事项
                        //List<Map<String,Object>> diclist = dictionaryService.findByTypeCode("TypeOfInsurance");
                        List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                                "TypeOfInsurance", "310,610");
                        List xzxxlist = JSONObject.parseArray(record.get("XZXX_JSON").toString());
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
                                xzxx.put("aae140", dic.get("VALUE"));//险种
                                xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//开始日期
                                xzxx.put("aae031", "99991231");//截止日期
                                xzxx.put("aac031", "1");
                                list.add(xzxx);
                            }
                        }
                    }else {
                        list = JSONObject.parseArray(record.get("XZXX_JSON").toString());
                    }
                }else {
                    list = JSONObject.parseArray(record.get("XZXX_JSON").toString());
                }
            }else{
                //List<Map<String,Object>> diclist = dictionaryService.findByTypeCode("TypeOfInsurance");
                List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                        "TypeOfInsurance", "310,610");
                for(Map<String,Object> dic : diclist){
                    Map<String,Object> xzxx = new HashMap<String, Object>();
                    xzxx.put("aae140", dic.get("VALUE"));//险种
                    xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//开始日期
                    xzxx.put("aae031", "99991231");//截止日期
                    xzxx.put("aac031", "1");
                    list.add(xzxx);
                }
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    单位信息查询（续保）
     * @author Danto Huang
     * @created 2019年10月16日 上午9:18:01
     * @param request
     * @return
     */
    @RequestMapping(params = "xbDepartQueryForSelect")
    public ModelAndView xbDepartQueryForSelect(HttpServletRequest request){
        return new ModelAndView("bsdt/applyform/ybqlc/lhjyxbdj/showDepartQuery");
    }

    /**
     * 
     * 描述    查询单位(续保)
     * @author Danto Huang
     * @created 2019年10月16日 上午9:47:09
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
     * 
     * 描述    参保人信息查询
     * @author Danto Huang
     * @created 2019年10月16日 上午9:18:01
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
        return new ModelAndView("bsdt/applyform/ybqlc/lhjyxbdj/showCbrQuery");
    }

    /**
     * 
     * 描述    查询参保人信息
     * @author Danto Huang
     * @created 2019年10月16日 上午9:47:09
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
               + "\"aac066\":\"e0\",\"aac031\":\"1\",\"aac058\":\"1\"},{\"aab999\":\"351102\",\"aab004\":\"平潭实验中学\","
               + "\"aab001\":\"5\",\"aac002\":\"350321199711130725\",\"bac503\":\"12345678\",\"aac003\":\"test\","
               + "\"aac066\":\"01\",\"aac031\":\"1\",\"aac058\":\"1\"}]";
            list = (List<Map<String, Object>>) JSON.parse(jsonString); 
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    获取参保人信息
     * @author Danto Huang
     * @created 2019年11月1日 下午4:07:36
     * @param request
     * @param response
     */
    @RequestMapping(params="cbrxxJson")
    public void cbrxxJson(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_LHJYXBDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("CBRXX_JSON")!=null){
                list = (List<Map<String,Object>>)JSON.parse(record.get("CBRXX_JSON").toString());
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    灵活就业参保数据推送医保系统
     * @author Allin Lin
     * @created 2019年11月26日 上午11:29:48
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushLhjyCb")
    @ResponseBody
    public AjaxJson pushLhjyCb(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        Map<String,Object> basic = new  HashMap<String, Object>();//基本信息
        Map<String,Object> insuCommon = new  HashMap<String, Object>();//参保信息     
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_LHJYCBDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null && !"".equals(record) && !"undefined".equals(record)){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));
                    basic.put("aac002", record.get("ZJHM"));//证件号码
                    basic.put("aac058", record.get("ZJLX"));//证件类型
                    basic.put("aac003", record.get("XM"));//姓名
                    basic.put("bae950", record.get("ZJYXQQS"));//证件有效期始
                    basic.put("bae951", record.get("ZJYXQJZ"));//证件有效期止
                    basic.put("aac004", record.get("SEX"));//性别
                    basic.put("aac005", record.get("NATION"));//民族
                    basic.put("aac006", record.get("BIRTHDAY"));//出生日期
                    basic.put("bac504", record.get("JCLDGXSJ"));//解除劳动关系时间
                    basic.put("aac033", record.get("YDCBKSSJ"));//异地参保开始日期
                    basic.put("aac007", record.get("CJGZSJ"));//参加工作日期
                    basic.put("aac009", record.get("HKXZ"));//户口性质
                    basic.put("aab301", record.get("SSXZQH"));//所属行政区划
                    basic.put("aac028", record.get("DYXSJB"));//农民工标识
                    basic.put("aac017", record.get("HYZK"));//婚姻状况
                    basic.put("bae528", record.get("MOBILE"));//手机号码 
                    basic.put("aae005", record.get("PHONE"));//联系电话
                    basic.put("aae007", record.get("POSTCODE"));//邮政编码
                    basic.put("aae006", record.get("TXDZ"));//通讯地址
                    basic.put("aac016", record.get("JYZT"));//就业状态    
                    basic.put("bac505", record.get("JZGBBZ"));//军转干部标志
                    basic.put("aac060", record.get("RYSCZT"));//人员生存状态
                    basic.put("aac020", record.get("JOB"));//职务
                    basic.put("aae013", record.get("REMARK"));//备注
                    insuCommon.put("aab001", record.get("DWBH"));//单位编号
                    insuCommon.put("bab511", record.get("DWDAH"));//单位档案号
                    insuCommon.put("aab004", record.get("DWMC"));//单位名称
                    insuCommon.put("aab999", record.get("DWBXH"));//单位保险号
                    insuCommon.put("aaz065", record.get("YHHH"));//银行行号
                    insuCommon.put("aab340", record.get("KHHMC"));//开户行名称
                    insuCommon.put("aae010", record.get("JFZH"));//缴费账号
                    insuCommon.put("aae009", record.get("JFZHMC"));//缴费账号名
                    insuCommon.put("aac066", record.get("CBSF"));//参保身份
                    insuCommon.put("aac040", record.get("SBGZ"));//申报工资
                    //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
                    SysUser sysUser = AppUtil.getLoginUser();
                    String jbIdcard = sysUser.getUsercard();
                    insuCommon.put("spry_aac002", jbIdcard);
                    pushInfo.put("basic", basic);
                    pushInfo.put("insuCommon", insuCommon);
                    List<Map<String, Object>> xzxx_json = (List<Map<String, Object>>) JSON
                            .parse(record.get("XZXX_JSON").toString());
                    pushInfo.put("insuList", xzxx_json);//险种集合
                    log.info("灵活就业新参保推送业务数据："+JSON.toJSONString(pushInfo));
                    ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushZgcb");
                    if(ajaxJson.isSuccess()){              
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        ybCommonService.saveOrUpdate(record, "T_YBQLC_LHJYCBDJ", ywId);
                    }
                }else{
                    log.info("获取token值失败！");
                }
            }
        }
        return ajaxJson;
    }
    
    /**
     * 描述     续保校验
     * @author Allin Lin
     * @created 2019年11月29日 上午9:14:12
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
     * @created 2019年12月3日 下午4:22:20
     * @return
     */
    @RequestMapping(params = "pushLhjyXb")
    @ResponseBody
    public AjaxJson pushLhjyXb(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        //System.out.println("续保保存传递参数：" + data);
        Map<String, Object> variables = (Map<String, Object>) JSON.parse(data.toString());
        Map<String, Object> tokenMap = ybCommonService.getToken();
        if ((boolean) tokenMap.get("success")) {
            variables.put("token", tokenMap.get("token"));
            // ajaxJson = ybCommonService.commonAjaxJsonOfYb(variables,"pushLhjyXb");
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("处理成功");
        } else {
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    /**
     * 描述    审核-后台保存参保人信息
     * @author Allin Lin
     * @created 2019年12月4日 上午11:10:58
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
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_LHJYXBDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            record.put("CBRXX_JSON", data);
            ybCommonService.saveOrUpdate(record, "T_YBQLC_LHJYXBDJ", ywId);
            ajaxJson.setSuccess(true);
        }
        return ajaxJson;
    }
    
}
