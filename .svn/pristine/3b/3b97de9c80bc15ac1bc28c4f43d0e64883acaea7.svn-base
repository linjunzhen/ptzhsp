/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.util.ArrayList;
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
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import javafx.scene.control.Alert;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;

/**
 * 描述 医保-公务员危重病医疗费用的补助审核业务控制类
 * @author Allin Lin
 * @created 2019年10月25日 下午2:27:33
 */
@Controller
@RequestMapping("/ybGwybzshController")
public class YbGwybzshController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbGwybzshController.class);
    
    
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
     * 描述 跳转到可登记人员信息查询选择器界面
     * @author Allin Lin
     * @created 2019年10月25日 下午2:49:38
     * @param request
     * @return
     */
    @RequestMapping(params = "showSelectDjInfos")
    public ModelAndView showSelectDjInfos(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");  
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/gwybzsh/showSelectDjInfos");
    }
    
    /**
     * 描述    跳转到已登记人员信息查询选择器界面
     * @author Allin Lin
     * @created 2019年11月11日 下午3:43:21
     * @param request
     * @return
     */
    @RequestMapping(params = "showSelectYdjInfos")
    public ModelAndView showSelectYdjInfos(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");  
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("bsdt/applyform/ybqlc/gwybzsh/showSelectYdjInfos");
    }
    
    
    /**
     * 描述    信息变更界面
     * @author Allin Lin
     * @created 2019年10月25日 下午3:34:55
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String data = request.getParameter("data");
        request.setAttribute("data", JSON.parse(data));
        return new ModelAndView("bsdt/applyform/ybqlc/gwybzsh/updateInfo");
    }
    
    
    /**
     * 描述    公务员可登记人员信息列表
     * @author Allin Lin
     * @created 2019年11月7日 上午10:15:56
     * @param request
     * @param response
     */
    @RequestMapping(params = "djInfosDatagrid")
    public void djInfosDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        //log.info("token值："+tokenMap.get("token"));
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("djInfosDatagrid");
            params.put("token", tokenMap.get("token"));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "djInfosQuery");
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
     * 描述     公务员已登记人员信息列表
     * @author Allin Lin
     * @created 2019年11月8日 下午2:44:19
     * @param request
     * @param response
     */
    @RequestMapping(params = "yDjDatagrid")
    public void bgxxDatagrid(HttpServletRequest request,HttpServletResponse response){       
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//业务数据
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("yDjDatagrid");
            params.put("token", tokenMap.get("token"));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "ydjInfosQuery");
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
     * 描述    公务员补助审核信息推送医保系统
     * @author Allin Lin
     * @created 2019年12月17日 下午4:26:43
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushYb")
    @ResponseBody
    public AjaxJson pushYb(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        String ywId  = request.getParameter("ywId");//业务ID
        String type = request.getParameter("type"); //申报类型     
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = ybCommonService.getByJdbc("T_YBQLC_GWYBZSH", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null){
                Map<String,Object> tokenMap = ybCommonService.getToken();
                if((boolean) tokenMap.get("success")){
                    ajaxJson = getPushResult(type, record, tokenMap.get("token").toString());
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
     * 描述    公务员补助审核具体子项业务数据推送医保
     * @author Allin Lin
     * @created 2019年12月17日 下午4:38:22
     * @param type(1:公务员危重病登记、2：公务员危重病变更、3、公务员危重病注销)
     * @param record
     * @return
     */
    public AjaxJson getPushResult(String type,Map<String, Object> record,String token){
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        AjaxJson ajaxJson = new AjaxJson();
        //业务办理推送经办用户证件号码（适配医保内网用户体系）-当前登录用户
     /*   SysUser sysUser = AppUtil.getLoginUser();
        String jbIdcard = sysUser.getUsercard();*/
        if("1".equals(type)){
            pushInfo.put("token", token);
            //pushInfo.put("spry_aac002", jbIdcard);
            pushInfo.put("aac002", record.get("ZJHM"));//身份证
            pushInfo.put("aac001", record.get("GRBH"));//个人编号
            pushInfo.put("aac003", record.get("XM"));//姓名
            pushInfo.put("bac503", record.get("GRBXH"));//个人保险号
            pushInfo.put("aab999", record.get("DWBXH"));//单位保险号
            pushInfo.put("orgcode",record.get("FZX"));//分中心
            pushInfo.put("aab004", record.get("DWMC"));//单位名称
            pushInfo.put("aac066", record.get("GZZT"));//参保身份（工作状态）
            pushInfo.put("bac508", record.get("TSRYLB"));//特殊人员类别
            pushInfo.put("aae012", record.get("WBZBM"));//危重病类别
            pushInfo.put("aae041", record.get("QSSJ"));//起始年月
            pushInfo.put("aae042", record.get("JZSJ"));//终止年月
            pushInfo.put("aae009", record.get("YHHM"));//银行户名
            pushInfo.put("aae010", record.get("YHZH"));//银行账号
            pushInfo.put("aac058", record.get("REMARK"));//备注
            log.info("公务员危重病登记推送信息："+JSON.toJSONString(pushInfo));          
            ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushGwyWzbDj");  
        }else if("2".equals(type)){
            Map<String, Object> bgxx_json = (Map<String, Object>) JSON.parse(record.get("BGXX_JSON").toString());
            pushInfo.put("token", token);
            //pushInfo.put("spry_aac002", jbIdcard);
            pushInfo.put("baz518", bgxx_json.get("BG_RYLBID"));//特殊人员类别ID
            pushInfo.put("baz522", bgxx_json.get("BG_WZBID"));//公务员危病重ID
            pushInfo.put("aac002", bgxx_json.get("BG_HM"));//身份证
            pushInfo.put("aac001", bgxx_json.get("BG_GRBH"));//个人编号
            pushInfo.put("orgcode",bgxx_json.get("BG_FZX"));//分中心
            pushInfo.put("aac003", bgxx_json.get("BG_XM"));//姓名
            pushInfo.put("bac503", bgxx_json.get("BG_BXH"));//个人保险号
            pushInfo.put("aab999", bgxx_json.get("BG_DWBXH"));//单位保险号
            pushInfo.put("aab004", bgxx_json.get("BG_DWMC"));//单位名称
            pushInfo.put("aac066", bgxx_json.get("BG_GZZT"));//工作状态
            pushInfo.put("bac508", bgxx_json.get("BG_RYLB"));//特殊人员类别
            pushInfo.put("aae012", bgxx_json.get("BG_WBZBM"));//危病重类别
            pushInfo.put("aae041", bgxx_json.get("BG_QSNY"));//起始年月
            pushInfo.put("aae042", bgxx_json.get("BG_ZZNY"));//终止年月
            pushInfo.put("aae009", bgxx_json.get("BG_YHHM"));//银行户名
            pushInfo.put("aae010", bgxx_json.get("BG_YHZH"));//银行账户
            pushInfo.put("aac058", bgxx_json.get("BG_BZ"));//备注    
            log.info("公务员危重病变更推送信息："+JSON.toJSONString(pushInfo));
            ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushGwyWzbBg");  
        }else if("3".equals(type)){
            Map<String, Object> zxxx_json = (Map<String, Object>) JSON.parse(record.get("ZXXX_JSON").toString());
            pushInfo.put("token", token);
            //pushInfo.put("spry_aac002", jbIdcard);
            pushInfo.put("baz518", zxxx_json.get("ZX_RYLBID"));//特殊人员类别ID
            pushInfo.put("baz522", zxxx_json.get("ZX_WZBID"));//公务员危病重ID
            pushInfo.put("aac002", zxxx_json.get("ZX_HM"));//身份证
            pushInfo.put("aac001", zxxx_json.get("ZX_GRBH"));//个人编号
            pushInfo.put("orgcode",zxxx_json.get("ZX_FZX"));//分中心
            pushInfo.put("aac003", zxxx_json.get("ZX_XM"));//姓名
            pushInfo.put("bac503", zxxx_json.get("ZX_BXH"));//个人保险号
            pushInfo.put("aab999", zxxx_json.get("ZX_DWBXH"));//单位保险号
            pushInfo.put("aab004", zxxx_json.get("ZX_DWMC"));//单位名称
            pushInfo.put("aac066", zxxx_json.get("ZX_GZZT"));//工作状态
            pushInfo.put("bac508", zxxx_json.get("ZX_RYLB"));//特殊人员类别
            pushInfo.put("aae012", zxxx_json.get("ZX_WBZBM"));//危病重类别
            pushInfo.put("aae041", zxxx_json.get("ZX_QSNY"));//起始年月
            pushInfo.put("aae042", zxxx_json.get("ZX_ZZNY"));//终止年月
            pushInfo.put("aae009", zxxx_json.get("ZX_YHHM"));//银行户名
            pushInfo.put("aae010", zxxx_json.get("ZX_YHZH"));//银行账户
            pushInfo.put("aac058", zxxx_json.get("ZX_BZ"));//备注   
            log.info("公务员危重病注销推送信息："+JSON.toJSONString(pushInfo));
            ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "pushGwyWzbZx");
        }
        if(ajaxJson.isSuccess()){              
            record.put("PUSH_FLAG", "1");//推送成功更新标志位
            ybCommonService.saveOrUpdate(record, "T_YBQLC_GWYBZSH", record.get("YW_ID").toString());
        }
       return ajaxJson; 
    }
}
