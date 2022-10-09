/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.awt.ItemSelectable;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.YbCxjmcbxbService;
import net.evecom.platform.yb.service.impl.YbCommonServiceImpl;


/**
 * 描述 医保-城乡居民参保、续保登记控制类
 * 
 * @author Allin Lin
 * @created 2019年10月11日 上午10:29:50
 */
@Controller
@RequestMapping("/ybCxjmcbxbController")
public class YbCxjmcbxbController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbCxjmcbxbController.class);
    
    /**
     * 所引入的service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 所引入的services
     */
    @Resource
    private YbCxjmcbxbService ybCxjmcbxbService;
    
    /**
     * 引入service
     */
    @Resource
    private YbCommonService ybCommonService;
    
    
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
        String exeId = request.getParameter("exeId");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CXJMDJ", 
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("XZXX_JSON") != null
                    && !"[]".equals(record.get("XZXX_JSON").toString())) {
                if (exeId != null && !"".equals(exeId)) {
                    Map<String, Object> execution = ybCommonService.getByJdbc("JBPM6_EXECUTION",
                            new String[] { "EXE_ID" }, new Object[] { exeId });
                    if (execution.get("CUR_STEPNAMES") != null
                            && "受理".equals(execution.get("CUR_STEPNAMES").toString())) {
                        //退回至受理事项
                        //List<Map<String,Object>> diclist = dictionaryService.findByTypeCode("TypeOfInsurance");
                        List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                                "TypeOfInsurance", "390");
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
                                        xzxx.put("aae140", xzType);//险种类型
                                        if(xzInfo.get("aae030")!=null) {//开始日期
                                            xzxx.put("aae030", xzInfo.get("aae030").toString());
                                        }
                                        if(xzInfo.get("aae031")!=null) {//截止日期
                                            xzxx.put("aae031", xzInfo.get("aae031").toString());
                                        }
                                        if(xzInfo.get("aac066")!=null) {//参保身份
                                            xzxx.put("aac066", xzInfo.get("aac066").toString());
                                        }
                                        xzxx.put("ck", true);
                                        list.add(xzxx);
                                        isPut = true;
                                        break;
                                    }
                                }
                            }
                            if(!isPut) {
                                xzxx.put("aae140", dic.get("VALUE"));
                                xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));
                                xzxx.put("aae031", "");
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
                        "TypeOfInsurance", "390");
                for(Map<String,Object> dic : diclist){
                    Map<String,Object> xzxx = new HashMap<String, Object>();
                    xzxx.put("aae140", dic.get("VALUE"));
                    xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));
                    xzxx.put("aae031", "");
                    xzxx.put("ck", true);
                    list.add(xzxx);
                }
            }
        }        
       this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);    
    }
    
    /**
     * 描述    城乡居民新参保登记信息推送医保系统
     * @author Allin Lin
     * @created 2019年12月24日 下午2:40:06
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushCxjmCb")
    @ResponseBody
    public AjaxJson pushCxjmCb(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息     
        Map<String,Object> basic = new  HashMap<String, Object>();//居民基本信息
        Map<String,Object> insuCommon = new  HashMap<String, Object>();//居民险种单位体
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CXJMDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
                    SysUser sysUser = AppUtil.getLoginUser();
                    String jbIdcard = sysUser.getUsercard();
                    basic.put("spry_aac002", jbIdcard);
                    basic.put("aac002", record.get("ZJHM"));//证件号码
                    basic.put("aac058", record.get("ZJLX"));//证件类型
                    basic.put("aac003", record.get("NAME"));//姓名
                    basic.put("bae950", record.get("ZJ_BEGIN_TIME"));//证件有效期始
                    basic.put("bae951", record.get("ZJ_END_TIME"));//证件有效期止
                    basic.put("aac004", record.get("SEX"));//性别
                    basic.put("aac005", record.get("MZ"));//民族
                    basic.put("aac006", record.get("BIRTHDAY"));//出生日期
                    basic.put("aac009", record.get("HKXZ"));//户口性质
                    basic.put("aab301", record.get("XZQH"));//所属行政区划
                    basic.put("bae528", record.get("MOBILE"));//手机号码 
                    basic.put("bac519", record.get("XSE"));//是否新生儿
                    basic.put("aae007", record.get("YZBM"));//邮政编码                 
                    basic.put("bac503", record.get("GRBXH"));//个人保险号
                    basic.put("aac017", record.get("HYZK"));//婚姻状况
                    basic.put("bac505", record.get("JZGBBZ"));//军转干部标志
                    basic.put("aac060", record.get("RYSCZT"));//人员生存状态
                    basic.put("aae005", record.get("PHONE"));//联系电话
                    basic.put("aae006", record.get("TXDZ"));//通讯地址
                    basic.put("bae914", record.get("RXSJ"));//入学时间
                    basic.put("bae915", record.get("BYSJ"));//拟毕业时间                   
                    basic.put("bae916", record.get("YXMC"));//院系名称
                    basic.put("bae958", record.get("NJ"));//年级
                    basic.put("bae959", record.get("BJ"));//班级
                    basic.put("bae961", record.get("HZSFZ")); //户主身份证
                    basic.put("bae430", record.get("PHONE"));//联系电话
                   //basic.put("bae431", record.get("ADDRESS"));//户主姓名
                    basic.put("bae432", record.get("YHZGX"));//与户主关系
                    basic.put("bae917", record.get("PKS"));////是否贫困生
                    basic.put("aic160", record.get("DY_BEGIN_TIME"));//待遇开始日期
                    basic.put("aae013", record.get("REMARK"));//备注
                    insuCommon.put("bab507", record.get("DWID"));//居民单位ID
                    insuCommon.put("bab505", record.get("DWBH"));//居民单位编码
                    insuCommon.put("bab506", record.get("DWMC"));//居民单位名称                   
                    pushInfo.put("basic", basic);
                    pushInfo.put("insuCommon", insuCommon);
                    pushInfo.put("token", tokenMap.get("token"));              
                    List<Map<String, Object>> xzxx_json = (List<Map<String, Object>>) JSON
                            .parse(record.get("XZXX_JSON").toString());
                    pushInfo.put("insuList", xzxx_json);//居民险种体
                    //log.info("城乡居民新参保登记推送信息："+JSON.toJSONString(pushInfo));
                    ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushCxjmCb");  
                    if(ajaxJson.isSuccess()){              
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        ybCommonService.saveOrUpdate(record, "T_YBQLC_CXJMDJ", ywId);
                    }                                 
                }else{
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("推送医保系统，获取token值失败！");
                    log.info("获取Token值失败！");
                }
            }
        }
        return ajaxJson;
    }
    
    /**
     * 描述    居民续保-获取参保人信息
     * @author Allin Lin
     * @created 2019年10月14日 下午3:31:35
     * @param request
     * @param response
     */
    @RequestMapping(params = "getCbrxx")
    public void getCbrxx(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CXJMDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("CBRXX_JSON")!=null){
                list = (List<Map<String,Object>>)JSON.parse(record.get("CBRXX_JSON").toString());
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    跳转到居民单位信息查询选择器界面（参保登记）
     * @author Allin Lin
     * @created 2019年10月12日 上午9:35:57
     * @param request
     * @return
     */
    @RequestMapping(params = "ybSelectUnitInfos")
    public ModelAndView ybSelectUnitInfos(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");    
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/cxjmcbxb/showSelectUnitInfos");
    }
    
    /** 
     * 描述    跳转到居民单位人员清册查询选择器界面
     * @author Allin Lin
     * @created 2019年10月12日 下午3:14:46
     * @param request
     * @return
     */
    @RequestMapping(params = "ybSelectPersonInfos")
    public ModelAndView ybSelectPersonInfos(HttpServletRequest request){
        String departNo = request.getParameter("departNo");
        request.setAttribute("departNo", departNo);
        return new ModelAndView("bsdt/applyform/ybqlc/cxjmcbxb/showSelectPersonInfos");
    }
        
    /**
     * 描述    跳转到查询调动人员选择器界面
     * @author Allin Lin
     * @created 2019年10月15日 上午10:15:48
     * @param request
     * @return
     */
    @RequestMapping(params = "ybSelectDdPersons")
    public ModelAndView ybSelectDdPersons(HttpServletRequest request){ 
        /*String DWMC = request.getParameter("aab004");//单位名称        
        String DWBH = request.getParameter("aab001");//单位编号
        request.setAttribute("aab001",DWBH);
        request.setAttribute("aab004",DWMC );*/
        return new ModelAndView("bsdt/applyform/ybqlc/cxjmcbxb/showSelectDdPersons");
    }
    
    
    /** 
     * 描述    跳转到查询居民信息选择器界面
     * @author Allin Lin
     * @created 2019年10月15日 上午10:56:23
     * @param request
     * @return
     */
    @RequestMapping(params = "ybSelectJmInfos")
    public ModelAndView ybSelectJmInfos(HttpServletRequest request){  
        return new ModelAndView("bsdt/applyform/ybqlc/cxjmcbxb/showSelectJmInfos");
    }
    
    /**
     * 描述     居民单位信息列表（参保登记）
     * @author Allin Lin
     * @created 2019年11月19日 下午4:52:05
     * @param request
     * @param response
     */
    @RequestMapping(params = "unitInfosDatagrid")
    public void unitInfosDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitInfosDatagrid");
            params.put("token", tokenMap.get("token"));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "unitInfosOfPerson");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
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
     * 描述     居民单位人员清册列表(参保登记)
     * @author Allin Lin
     * @created 2019年11月20日 上午9:59:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "personInfosDatagrid")
    public void personInfosDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("personInfosDatagrid");
            params.put("token", tokenMap.get("token"));
            //System.out.println("查询参数："+ params);
            /*AjaxJson dataJson = ybCommonService.commonAjaxJsonOfYb(params, "personInfosQuery");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    list = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                 
            }else{
                log.info(dataJson.getMsg());
            }*/
            String jsonString="[{\"YWH_ID\":\"11\",\"DWBH\":\"350011\",\"DWMC\":\"测试单位\","
                    + "\"GRBXH\":\"351100\",\"ZJHM\":\"350321199711130725\",\"CBKSRQ\":\"20190901\","
               + "\"XM\":\"Allin\",\"ZJLX\":\"1\",\"CBSF\":\"e0\",\"XB\":\"2\",\"MZ\":\"01\",\"PHONE\":\"12345678\","
               + "\"MOBILE\":\"13055278035\"}]";
            list = JSON.parseObject(jsonString, List.class);
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 描述    社区或学校信息列表（续保）
     * @author Allin Lin
     * @created 2019年12月20日 下午3:58:37
     * @param request
     * @param response
     */
    @RequestMapping(params = "sqOrSchoolDatagrid")
    public void sqOrSchoolDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("sqOrSchoolDatagrid");
            params.put("token", tokenMap.get("token"));   
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "unitInfosQuery");          
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
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
     * 描述    调动人员信息列表（续保）
     * @author Allin Lin
     * @created 2019年12月23日 上午10:34:50
     * @param request
     * @param response
     */
    @RequestMapping(params = "ddPersonsDatagrid")
    public void ddPersonsDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("ddPersonsDatagrid");
            params.put("token", tokenMap.get("token"));       
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "movePersonsQuery");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    list = (List<Map<String, Object>>) JSON.parse(jsonString);
                    for (Map<String, Object> map : list) {
                        map.put("bac524",String.valueOf(YbCommonServiceImpl.getXbPchId()));//新参保批次号
                    }
                }                 
            }else{
                log.info("居民续保单位人员查询接口异常："+dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 描述    续保参保人校验（续保登记）
     * @author Allin Lin
     * @created 2019年12月23日 上午11:22:27
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
            //ajaxJson = ybCommonService.commonAjaxJsonOfYb(variables, "checkCxXb");  
            ajaxJson.setSuccess(true);
            ajaxJson.setJsonString("{\"bae619\":\"校验通过\",\"bae620\":\"\"}");
            //ajaxJson.setMsg("处理失败：原因：证件号码不合格");
        }else{
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    /**
     * 描述 居民信息列表（停保减员登记）    
     * @author Allin Lin
     * @created 2019年12月23日 下午4:13:54
     * @param request
     * @param response
     */
    @RequestMapping(params = "jmInfosDatagrid")
    public void jmInfosDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("jmInfosDatagrid");
            params.put("token", tokenMap.get("token"));
            log.info("停保人员查询参数："+ params);
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "tbPersonInfosQuery");
            if(dataJson.isSuccess()){      
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    list = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                 
            }else{
                log.info(dataJson.getMsg());
            }         
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(list.size(),list,null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 描述   后台获取居民信息列表（停保减员登记） 
     * @author Allin Lin
     * @created 2019年12月23日 下午4:47:55
     * @param request
     * @param response
     */
    @RequestMapping(params="tbxxJson")
    public void tbxxJson(HttpServletRequest request,HttpServletResponse response){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//业务数据
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CXJMDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("TBXX_JSON")!=null){
                list = ( List<Map<String, Object>>)JSON.parse(record.get("TBXX_JSON").toString());
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述   城乡居民续保信息推送医保（续保登记） 
     * @author Allin Lin
     * @created 2019年12月24日 上午10:50:42
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushCxjmXb")
    @ResponseBody
    public AjaxJson pushCxjmXb(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> variables = new HashMap<String, Object>();//续保数据体
        String data = request.getParameter("data");
        Map<String, Object> item = (Map<String, Object>) JSON.parse(data.toString());  
        //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
        SysUser sysUser = AppUtil.getLoginUser();
        String jbIdcard = sysUser.getUsercard();
        item.put("spry_aac002", jbIdcard);
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        items.add(item);
        Map<String, Object> tokenMap = ybCommonService.getToken();
        if ((boolean) tokenMap.get("success")) {
            variables.put("token", tokenMap.get("token"));
            variables.put("items", items);
            ajaxJson = ybCommonService.pushInfoOfYb(variables,"pushCxjmXb");
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取token值失败！");
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    
    /**
     * 描述  审核-后台保存续保参保人信息（续保登记）  
     * @author Allin Lin
     * @created 2019年12月24日 上午10:57:43
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveXbCbrXx")
    @ResponseBody
    public AjaxJson saveXbCbrXx(HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CXJMDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            record.put("CBRXX_JSON", data);
            ybCommonService.saveOrUpdate(record, "T_YBQLC_CXJMDJ", ywId);
            ajaxJson.setSuccess(true);
        }
        return ajaxJson;
    }
    
    /**
     * 描述    城乡居民停保减员居民信息推送医保
     * @author Allin Lin
     * @created 2019年12月24日 上午11:18:00
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushCxjmTb")
    @ResponseBody
    public AjaxJson pushCxjmTb(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");//基本信息
        String zdyy = request.getParameter("zdyy");//中断原因
        Map<String, Object> variables = new HashMap<String, Object>();
        //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
        SysUser sysUser = AppUtil.getLoginUser();
        String jbIdcard = sysUser.getUsercard();
        variables.put("spry_aac002", jbIdcard);
        variables.put("aae160", zdyy);//中断原因
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        Map<String, Object> dataMap = (Map<String, Object>) JSON.parse(data.toString());
        Map<String, Object> item = new HashMap<String, Object>();
        variables.put("aac066", StringUtil.getString(dataMap.get("aac066")));//参保身份
        item.put("aac001", StringUtil.getString(dataMap.get("aac001")));//个人编码
        item.put("bab507", StringUtil.getString(dataMap.get("bab507")));//居民单位ID
        //variables.putAll((Map<String, Object>)JSON.parse(data.toString()));
        items.add(item);
        //log.info("居民停保减员基本信息："+JSON.toJSONString(items));
        variables.put("items", items);//基本信息
        Map<String, Object> tokenMap = ybCommonService.getToken();
        if ((boolean) tokenMap.get("success")) {
            variables.put("token", tokenMap.get("token"));
            ajaxJson = ybCommonService.pushInfoOfYb(variables,"pushCxjmTb");          
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取token值失败！");
            log.info("获取token值失败！");
        }
        return ajaxJson;
    }
    
    /** 
     * 描述  审核-后台保存城乡居民停保减员居民信息（停保登记）
     * @author Allin Lin
     * @created 2019年12月24日 上午11:21:35
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
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_CXJMDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            record.put("TBXX_JSON", data);
            ybCommonService.saveOrUpdate(record, "T_YBQLC_CXJMDJ", ywId);
            ajaxJson.setSuccess(true);
        }
        return ajaxJson;
    }
    
}
