/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

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
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import javafx.scene.control.Alert;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.YbGwyssfwqrService;

/**
 * 描述 医保-国家公务员医疗补助实施单位确认业务控制类
 * 
 * @author Allin Lin
 * @created 2019年10月23日 下午2:23:50
 */
@Controller
@RequestMapping("/ybGwyssfwqrController")
public class YbGwyssfwqrController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbGwyssfwqrController.class);

    /**
     * 引入的service
     */
    @Resource
    private YbGwyssfwqrService ybGwyssfwqrService;
    
    /**
     * 引入service
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述 跳转到居民单位信息选择器界面
     * 
     * @author Allin Lin
     * @created 2019年10月23日 下午2:28:23
     * @param request
     * @return
     */
    @RequestMapping(params = "showSelectUnitInfos")
    public ModelAndView ybSelectUnitInfos(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/gwyssfwqr/showSelectUnitInfos");
    }

    /**
     * 描述 获取单位险种信息
     * @author Allin Lin
     * @created 2019年11月1日 下午3:41:17
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params="xzxxJson")
    public void xzxxJson(HttpServletRequest request,HttpServletResponse response){
        List list = new ArrayList<Map>();
        String ywId = request.getParameter("ywId");//业务ID
        String exeId = request.getParameter("exeId");//流程实例ID
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_GWYSSFWQR", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record != null && record.get("XZXX_JSON")!=null){
                if (exeId != null && !"".equals(exeId)) {
                    Map<String, Object> execution = ybCommonService.getByJdbc("JBPM6_EXECUTION",
                            new String[] { "EXE_ID" }, new Object[] { exeId });
                    if (execution.get("CUR_STEPNAMES") != null
                            && "受理".equals(execution.get("CUR_STEPNAMES").toString())) {
                        //退回事项
                        //List<Map<String,Object>> diclist = dictionaryService.findByTypeCode("TypeOfInsurance");
                        List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                                "TypeOfInsurance", "310,320,610,520");
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
                                        if(xzInfo.get("aae030")!=null) {
                                            xzxx.put("aae030", xzInfo.get("aae030").toString());
                                        }//开始日期                                                
                                        if(xzInfo.get("aab033")!=null) {
                                            xzxx.put("aab033", xzInfo.get("aab033").toString());
                                        }//征收方式
                                        xzxx.put("isCheck", true);
                                        list.add(xzxx);
                                        isPut = true;
                                        break;
                                    }
                                }
                            }
                            if(!isPut) {
                                xzxx.put("aae140", dic.get("VALUE"));//险种类型
                                xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//开始日期
                                xzxx.put("aab033", "2");//征收方式默认税务征收
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
                        "TypeOfInsurance", "310,320,610,520");
                for(Map<String,Object> dic : diclist){   
                    Map<String,Object> xzxx = new HashMap<String, Object>();
                    xzxx.put("aae140", dic.get("VALUE"));//险种类型
                   /*xzxx.put("CB_TIME", DateTimeUtil.dateToStr(new Date(), "yyyyMMdd"));*/
                    xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//参保开始日期
                    xzxx.put("aab033", "2");//征收方式默认税务征收
                    list.add(xzxx);
                }
            }
        } 
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述     查询单位信息列表
     * @author Allin Lin
     * @created 2019年11月20日 下午4:22:10
     * @param request
     * @param response
     */
    @RequestMapping(params = "departQueryDatagrid")
    public void departQueryDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("departQueryDatagrid");
            params.put("token", tokenMap.get("token"));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "departQuery");
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
     * 描述     查询单位险种信息
     * @author Allin Lin
     * @created 2019年11月21日 下午2:31:11
     * @param request
     * @param response
     */
    @RequestMapping(params = "xzxxQueryDatagrid")
    public void xzxxQueryDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        if ((boolean) tokenMap.get("success")) {
            Map<String, Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("xzxxQueryDatagrid");
            params.put("token", tokenMap.get("token"));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "xzxxQuery");
            if (dataJson.isSuccess()) {
                String jsonString = dataJson.getJsonString();
                if (jsonString.length() > 0) {
                    List<Map<String, Object>> xzxxlist = (List<Map<String, Object>>) JSON.parse(jsonString);
                    /*List<Map<String, Object>> diclist = dictionaryService.
                            findByTypeCode("TypeOfInsurance"); */
                    List<Map<String,Object>> diclist = dictionaryService.findDatasForSelectIn(
                            "TypeOfInsurance", "310,320,610,520");
                    for (Map<String, Object> dic : diclist) {
                        Map<String, Object> xzxx = new HashMap<String, Object>();
                        String dicCode = dic.get("VALUE").toString();
                        boolean isPut = false;
                        for (int i = 0; i < xzxxlist.size(); i++) {
                            Map xzInfo = (Map) xzxxlist.get(i);
                            if (xzInfo.get("aae140") != null) {
                                String xzType = xzInfo.get("aae140").toString();
                                if (xzType.equals(dicCode)) {
                                    xzxx.put("aae140", xzType);//险种类型
                                    if (xzInfo.get("aae041") != null) {//开始日期
                                        xzxx.put("aae030", xzInfo.get("aae041").toString());
                                    }
                                    if (xzInfo.get("aae042") != null) {//截止日期
                                        xzxx.put("aae042", xzInfo.get("aae042").toString());
                                    }
                                    if (xzInfo.get("aab051") != null) {//参保状态
                                        xzxx.put("aab051", xzInfo.get("aab051").toString());
                                    }
                                    if (xzInfo.get("aab033") != null) {//征收方式
                                        xzxx.put("aab033", xzInfo.get("aab033").toString());
                                    }
                                    xzxx.put("isCheck", true);
                                    list.add(xzxx);
                                    isPut = true;
                                    break;
                                }
                            }
                        }
                        if (!isPut) {
                            xzxx.put("aae140", dic.get("VALUE"));// 险种类型
                            xzxx.put("aae030", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));// 参保日期
                            xzxx.put("aab033", "2");//征收方式默认税务征收
                            list.add(xzxx);
                        }
                    }
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
     * 描述    单位补助险种信息推送医保系统
     * @author Allin Lin
     * @created 2019年11月22日 下午3:52:21
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushYb")
    @ResponseBody
    public AjaxJson pushYb(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_GWYSSFWQR", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null && !"".equals(record)&& !"undefined".equals(record)){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));
                    //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
                    SysUser sysUser = AppUtil.getLoginUser();
                    String jbIdcard = sysUser.getUsercard();
                    pushInfo.put("spry_aac002", jbIdcard);
                    pushInfo.put("aab001", record.get("DWBH"));//单位编号
                    pushInfo.put("ywlx00", record.get("YWLX"));//业务类型
                    List<Map<String, Object>> xzxx_json = (List<Map<String, Object>>) JSON
                            .parse(record.get("XZXX_JSON").toString());
                    for(int i=0;i<xzxx_json.size();i++){
                        if("320".equals(xzxx_json.get(i).get("aae140"))){
                            pushInfo.put("aae030", xzxx_json.get(i).get("aae030"));//参保开始日期
                            pushInfo.put("aab033", xzxx_json.get(i).get("aab033"));//征收方式
                            break;
                        }
                    } 
                    ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushGwySsfw");  
                    if(ajaxJson.isSuccess()){              
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        ybCommonService.saveOrUpdate(record, "T_YBQLC_GWYSSFWQR", record.get("YW_ID").toString());
                    }
                }else{
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("推送医保系统，获取token值失败！");
                    log.info("获取token值失败！");
                }
            }
        }
        return ajaxJson;
    }
}
