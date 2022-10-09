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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.YbYrdwcbService;

/**
 * 描述  用人单位基本医疗保险、生育保险参保登记业务Controller
 * @author Allin Lin
 * @created 2019年11月12日 下午3:35:06
 */
@Controller
@RequestMapping("/ybYrdwcbController")
public class YbYrdwcbController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbYrdwcbController.class);
    
    
    /**
     * 引入service
     */
    @Resource
    private YbYrdwcbService ybYrdwcbService;
    
    /**
     * 引入YbCommonService
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 引入字典服务层 
     */
    @Resource
    public DictionaryService dictionaryService;
    
    
    /**
     * 描述    获取险种信息
     * @author Allin Lin
     * @created 2019年11月12日 下午5:27:42
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "paramjson")
    public void paramjson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map>();
        String ywId = request.getParameter("ywId");
        String exeId = request.getParameter("exeId");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_YRDWCB", 
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("XZXX_JSON") != null
                    && !"[]".equals(record.get("XZXX_JSON").toString())) {
                if (exeId != null && !"".equals(exeId)) {
                    Map<String, Object> execution = ybCommonService.getByJdbc("JBPM6_EXECUTION",
                            new String[] { "EXE_ID" }, new Object[] { exeId });
                    if (execution.get("CUR_STEPNAMES") != null
                            && "受理".equals(execution.get("CUR_STEPNAMES").toString())) {
                        //退回事项
                        List<Map<String,Object>> diclist = dictionaryService.findByTypeCodeAndWhere("TypeOfInsurance", 
                                "tsd.dic_code in (\'310\',\'510\',\'520\',\'610\')");                       
                        List xzxxlist = JSONObject.parseArray(record.get("XZXX_JSON").toString());
                        for(Map<String,Object> dic : diclist){
                            Map<String,Object> xzxx = new HashMap<String, Object>();
                            String dicCode = dic.get("DIC_CODE").toString();
                            boolean isPut = false;
                            for(int i=0; i<xzxxlist.size(); i++) {
                                Map xzInfo = (Map) xzxxlist.get(i);
                                if(xzInfo.get("aae140")!=null) {
                                    String xzType = xzInfo.get("aae140").toString();
                                    if(xzType.equals(dicCode)) {
                                        xzxx.put("aae140", xzType);
                                        if(xzInfo.get("aab033")!=null) {
                                            xzxx.put("aab033", xzInfo.get("aab033").toString());
                                        }
                                        if(xzInfo.get("aab050")!=null) {
                                            xzxx.put("aab050", xzInfo.get("aab050").toString());
                                        }                            
                                        xzxx.put("ck", true);
                                        list.add(xzxx);
                                        isPut = true;
                                        break;
                                    }
                                }
                            }
                            if(!isPut) {
                                xzxx.put("aae140", dic.get("DIC_CODE"));//险种类型
                                xzxx.put("aab050", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//参保日期
                                xzxx.put("aab033", "");//征收方式
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
                List<Map<String,Object>> diclist = dictionaryService.
                        findByTypeCodeAndWhere("TypeOfInsurance", "tsd.dic_code in (\'310\',\'510\',\'520\',\'610\')"); 
                for(Map<String,Object> dic : diclist){
                    Map<String,Object> xzxx = new HashMap<String, Object>();
                    xzxx.put("aae140", dic.get("DIC_CODE"));
                    xzxx.put("aab050", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));
                    xzxx.put("aab033", "");
                    list.add(xzxx);
                }
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 描述    用人单位参保登记信息推送医保
     * @author Allin Lin
     * @created 2019年12月31日 下午4:28:14
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushYrdwCb")
    @ResponseBody
    public AjaxJson pushYrdwCb(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        Map<String,Object> basic = new  HashMap<String, Object>();//基本信息
        Map<String,Object> insuCommon = new  HashMap<String, Object>();//参保信息      
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_YRDWCB", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null && !"".equals(record)&& !"undefined".equals(record)){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));
                    basic.put("aab004", record.get("DWMC"));//单位名称
                    basic.put("aab019", record.get("DWLX"));//单位类型
                    basic.put("aab020", record.get("DWLB"));//单位类别
                    basic.put("aab065", record.get("TSDWLB"));//特殊单位类别
                    basic.put("aab030", record.get("SH"));//税号
                    basic.put("aab301", record.get("XZQH"));//所属行政区划                   
                    basic.put("aab021", record.get("LSGX"));//隶属关系
                    basic.put("aaz066", record.get("SWJGBH"));//税务机构编号
                    basic.put("bab511", record.get("DWDAH"));//单位档案号
                    
                    basic.put("aab002", record.get("SBBM"));//社保编码
                    basic.put("aab003", record.get("ZZJGDM"));//组织机构代码
                    basic.put("aab006", record.get("ZZZL"));//工商执照种类                   
                    basic.put("aab007", record.get("ZZHM"));//工商执照号码               
                    basic.put("aab008", record.get("FZRQ"));//工商发照日期
                    basic.put("aab009", record.get("YXNX"));//工商有效年限
                    basic.put("aab022", record.get("HYDM"));//行业代码
                    basic.put("aab023", record.get("ZGBM"));//主管部门
                    basic.put("bab509", record.get("FZJG"));//工商登记发照机关
                    basic.put("baf110", record.get("HTH"));//合同号                   
                    basic.put("aae048", record.get("CLBM"));//批准成立部门
                    basic.put("aae049", record.get("CLRQ"));//批准成立日期
                    basic.put("aae051", record.get("CLWH"));//批准成立文号                 
                    basic.put("aae013", record.get("REMARK"));//备注           
                    basic.put("aab090", record.get("TYXYDM"));//社会统一信用代码
                    basic.put("aae007", record.get("YZBM"));//邮政编码
                    basic.put("aae006", record.get("DZ"));//地址
                    basic.put("aab013", record.get("FRXM"));//法人姓名
                    basic.put("aab014", record.get("FRZJHM"));//法人证件号码
                    basic.put("bab500", record.get("FRZW"));//法人职务                   
                    basic.put("aae004", record.get("LXRXM"));//联系人姓名
                    basic.put("aae005", record.get("LXDH"));//联系人电话
                    basic.put("aab069", record.get("DWDH"));//单位电话
                    basic.put("bae976", record.get("LXDH"));//联系人证件号码                                     
                    insuCommon.put("aaz065", record.get("YHHH"));//银行行号
                    /*insuCommon.put("aae100", record.get("UNIT_FILE"));//有效标志*/ 
                    insuCommon.put("aab340", record.get("KHHMC"));//开户行名称
                    insuCommon.put("aae009", record.get("JFZHM"));//缴费账号名
                    insuCommon.put("aae010", record.get("JFZH"));//缴费账号                
                    pushInfo.put("basic", basic);
                    pushInfo.put("insuCommon", insuCommon);
                    List<Map<String, Object>> xzxx_json = (List<Map<String, Object>>) JSON
                            .parse(record.get("XZXX_JSON").toString());
                    pushInfo.put("insuList", xzxx_json);
                    log.info("用人单位参保登记："+JSON.toJSONString(pushInfo));
                    ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushYrdwcb"); 
                    if(ajaxJson.isSuccess()){              
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        ybCommonService.saveOrUpdate(record, "T_YBQLC_YRDWCB", ywId);
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
}
