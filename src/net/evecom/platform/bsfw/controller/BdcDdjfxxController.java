/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONArray;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcExecutionService;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bsfw.dao.BdcApplyDao;
import net.evecom.platform.bsfw.service.BdcDdjfxxService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述  不动产-订单缴费信息controller()
 * @author Allin Lin
 * @created 2020年12月16日 上午10:32:57
 */
@Controller
@RequestMapping("/bdcDdjfxxController")
public class BdcDdjfxxController  extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcDdjfxxController.class);
    
    /**
     * 引入service
     */
    @Resource
    private BdcDdjfxxService bdcDdjfxxService;
    
    /**
     * 引入service
     */
    @Resource
    private BdcExecutionService bdcExecutionService;
    
    /**
     * 引入service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 引入service
     */
    @Resource
    private BdcGyjsjfwzydjService bdcGyjsjfwzydjService;
    
    /**
     * 引入service
     */
    @Resource
    private  ExeDataService exeDataService;
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入dao
     */
    @Resource
    private BdcApplyDao dao;
    
    /**
     * 描述    跳转到订单缴费信息
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bdcddjfxx")
    public ModelAndView bdcddjfxx(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hflow/bdc/bdcddjfxx");
    }
    
    /**
     * easyui AJAX请求测绘管理列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcDdjfxxService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/17 10:11:00
     * @param
     * @return
     */
     @RequestMapping(params = "multiDel")
     @ResponseBody
     public AjaxJson multiDel(HttpServletRequest request) {
         AjaxJson j = new AjaxJson();
         String selectColNames = request.getParameter("selectColNames");
         bdcDdjfxxService.remove("T_BDC_ORDERDETAILINFO","YW_ID",selectColNames.split(","));
         j.setMsg("删除成功");
         return j;
     }
     
     /**
     *
     * 校验是否能进行编辑
     * 
     * @param request
     * @return
     */
     @RequestMapping(params = "checkUpdateLimit")
     public void getResultFiles(HttpServletRequest request, HttpServletResponse response) {
         String entityId = request.getParameter("entityId");//主键ID
         Map<String, Object> result = new HashMap<String, Object>();
         Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",new String[] { "YW_ID" }, new Object[] { entityId });
         result.put("success", true);
         if("2".equals(StringUtil.getValue(bdcOrder, "ORDER_STATUS"))){
             result.put("success", false);
             result.put("msg", "当前记录无法进行编辑");
         }
         String json = JSON.toJSONString(result);
         this.setJsonString(json, response);
     }
     
     
     /**
     *
     * 显示订单缴费信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "create")
    public ModelAndView create(HttpServletRequest request) {
        String allowCount = "0";
        String entityId = request.getParameter("entityId");//主键ID
        Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                new String[] { "YW_ID" }, new Object[] { entityId });
        if(bdcOrder!=null){
            request.setAttribute("OrderItemTotalPrice", bdcOrder.get("ORDER_TOTALPRICE"));
        }
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("exeId", StringUtil.getValue(bdcOrder, "EXE_ID"));
        return new ModelAndView("bsfw/bdcDdjfxx/bdcOnlineChargeView");
    }
    
    /**
     * 增加或者修改订单缴费信息
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityKey = request.getParameter("KeyName");
        String entityId = request.getParameter(entityKey);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        StringBuffer updateSql = new StringBuffer("update ");
        updateSql.append(" T_BDC_ORDERDETAILINFO ");
        updateSql.append(" SET order_status= '");
        updateSql.append(StringUtil.getValue(variables, "ORDER_STATUS"));
        updateSql.append("' WHERE EXE_ID = ?");
        dao.executeSql(updateSql.toString(), new Object[] {entityId});
        return j;
    }
    
    
    /**
     * 描述:转发到互联网服务器
     * request中需要带入的参数：
     *      url:请求地址，必传
     *      headJson:请求头，可为null，可不传
     *      json:如果传输方式为json，则不为空
     *      method:HttpSendUtil中的方法，必传
     * @author Allin Lin
     * @created 2020年12月25日 上午9:31:28
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/forwardInternet")
    @ResponseBody
    public Map<String, Object> forwardInternet(HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = null;
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String pushInfoJson = StringUtil.getValue(requestMap, "pushInfoJson");
        String urlDicType = "";
        urlDicType = StringUtil.getValue(requestMap, "urlDicType");
        returnMap = bdcDdjfxxService.pushData(JSON.parseObject(pushInfoJson), urlDicType);
        return returnMap;
    }
    
    /**
     * 描述:转发到互联网服务器
     * request中需要带入的参数：
     *      url:请求地址，必传
     *      headJson:请求头，可为null，可不传
     *      json:如果传输方式为json，则不为空
     *      method:HttpSendUtil中的方法，必传
     * @author Allin Lin
     * @created 2020年12月25日 上午9:31:28
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/forwardInternetPayinfo")
    @ResponseBody
    public Map<String, Object> forwardInternetPayinfo(HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = null;
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String pushInfoJson = StringUtil.getValue(requestMap, "pushInfoJson");
        String token = StringUtil.getValue(requestMap, "token");
        String urlDicType = "";
        urlDicType = StringUtil.getValue(requestMap, "urlDicType");
        returnMap = bdcDdjfxxService.pushDataPayinfo(JSON.parseObject(pushInfoJson), urlDicType,token);
        return returnMap;
    }
    
    /**
     *  支付订单信息 
     * @param request
     * @return
     */
    @RequestMapping(params = "payinfo")
    public void payinfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> tokenMap = bdcDdjfxxService.getCreditToken();
        if("0".equals(tokenMap.get("code").toString())){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            Map<String, Object> payOrderInfoCreate = bdcDdjfxxService.payOrderInfoCreate(params, tokenMap.get("token").toString());
        }else{
            log.info("生成token失败");
        }
    }
    
    
    /**
     * 
     * 描述    不动产涉税缴费/完税成功发送短信提醒工作人员
     * @author Allin Lin
     * @created 2021年11月24日 下午10:31:57
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/sendJfWsMsgToUser")
    public void sendJfWsMsgToUser(HttpServletRequest request, HttpServletResponse response) {
        String sendInfoJson = request.getParameter("sendInfoJson");
        Map<String, Object>  result = new HashMap<String, Object>();
        String json = "";
        if(StringUtils.isNotEmpty(sendInfoJson)){
            List<Map> sendList = JSONArray.parseArray(sendInfoJson, Map.class);
            if(sendList.size()>0){
                for (Map send : sendList) {
                    result = bdcDdjfxxService.sendSuccessMsg(send);
                }
            }
        }else{
            result.put("msg", "缺失短信发送所需要的参数-sendInfoJson");
            result.put("success", false);
        }
        json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    发起订单修改通知
     * @author Jason Lin
     * @created 2021年11月21日 下午12:30:51
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushOrderDetailInfo")
    @ResponseBody
    public AjaxJson pushOrderDetailInfo(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson(); 
        String exeId  = request.getParameter("EXE_ID");
        String ORDER_TOTALPRICE  = request.getParameter("ORDER_TOTALPRICE");//收费总金额
        String UPDATEORDERSTATUS  = request.getParameter("UPDATEORDERSTATUS");//订单状态修改后的值
        String data  = request.getParameter("data").toString();
        List<Map> orderItems =  new ArrayList<Map>();//订单明细
        String  sssb_sffs = request.getParameter("sssb_sffs");//收费方式
        sssb_sffs = "0";
        Map<String, Object> orderDeatilInfo  = new HashMap<String, Object>();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(exeId)){
           if(org.apache.commons.lang3.StringUtils.isNotEmpty(data)){
               orderItems = JSONArray.parseArray(data, Map.class);//订单收费科目明细列表
           }
           Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                   new String[] { "EXE_ID" }, new Object[] { exeId });
           //1、数据存入待缴费列表
           if(bdcOrder!=null){
               String orderStatus  = StringUtil.getString(bdcOrder.get("ORDER_STATUS"));//缴费状态
               bdcOrder.put("ORDER_TOTALPRICE", Double.valueOf(ORDER_TOTALPRICE));
               bdcOrder.put("ORDERITEMS_JSON", JSON.toJSONString(orderItems));
               bdcOrder.put("CREATE_TIME",  DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
               if("2".equals(orderStatus)){
                   ajaxJson.setSuccess(false);
                   ajaxJson.setMsg("该办件号已缴费，不可修改！");
                   return ajaxJson;
               }else if("1".equals(orderStatus)){//缴费中，可修改
                   orderDeatilInfo.put("method", 2);
               }else if("3".equals(orderStatus)){//已取消，可修改
                   //新增缴费                                   
                   orderDeatilInfo.put("method", 2);
               }
               if(StringUtil.isNotEmpty(UPDATEORDERSTATUS)){
                   bdcOrder.put("ORDER_STATUS", UPDATEORDERSTATUS);
               }
               bdcGyjsjfwzydjService.saveOrUpdate(bdcOrder,
                       "T_BDC_ORDERDETAILINFO", StringUtil.getValue(bdcOrder, "YW_ID"));
           }
           //获取流程实例数据
           Map<String, Object> flowExe = bdcGyjsjfwzydjService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                   new Object[] { exeId });
           if (flowExe == null) {
               flowExe = bdcGyjsjfwzydjService.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                       new Object[] {exeId});
           }
           Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
           //2、组装业务参数、调用智慧岛订单支付接口
           orderDeatilInfo.put("exeId", exeId);
           orderDeatilInfo.put("itemCode", StringUtil.getString(flowExe.get("ITEM_CODE")));
           orderDeatilInfo.put("itemName", StringUtil.getString(flowExe.get("ITEM_NAME")));
           //用户取权利人的第一个
           String piJson = StringUtil.getValue(busRecord, "POWERPEOPLEINFO_JSON");
           List<Map> piList = new ArrayList<Map>();
           if (org.apache.commons.lang3.StringUtils.isNotEmpty(piJson)) {
               piList = JSONArray.parseArray(piJson, Map.class);
               orderDeatilInfo.put("userName", StringUtil.getString(piList.get(0).get("POWERPEOPLENAME")));
               String documentType = dictionaryService.getDicNames("DocumentType", 
                       StringUtil.getString(piList.get(0).get("POWERPEOPLEIDTYPE")));
               orderDeatilInfo.put("idType", documentType);
               orderDeatilInfo.put("idNo", StringUtil.getString(piList.get(0).get("POWERPEOPLEIDNUM")));
           }
           orderDeatilInfo.put("payAmount",Long.valueOf(ORDER_TOTALPRICE)*100);//金额单位从元转分
           orderDeatilInfo.put("orderTitle","不动产非税缴费");
           orderDeatilInfo.put("orderDetail","不动产非税缴费");
           List<Map<String, Object>> orderItemLists = new ArrayList<Map<String,Object>>();//订单明细集合
           Map<String,Object> orderItemMap = new HashMap<String, Object>();
           Map<String,Object> bizExp = new HashMap<String, Object>();
           List<Map<String, Object>> params = new ArrayList<Map<String,Object>>();
           Map<String,Object> param = new HashMap<String, Object>();
           Map<String,Object> params2 = new HashMap<String, Object>();
           List<Map<String, Object>> itemDetails = new ArrayList<Map<String,Object>>();
           orderItemMap.put("itemIndex", "1");
           orderItemMap.put("itemAmount", Long.valueOf(ORDER_TOTALPRICE)*100);
           orderItemMap.put("remark", "不动产非税缴费");
           bizExp.put("operation", "CREATE");
           param.put("letterDate", DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd"));
           param.put("payerName",StringUtil.getString(piList.get(0).get("POWERPEOPLENAME")));
           param.put("letterAmount",Long.valueOf(ORDER_TOTALPRICE)*100);
           String billCode = dictionaryService.getDicNames("ddConfig", "billCode");
           String author = dictionaryService.getDicNames("ddConfig", "author");
           params2.put("billCode", billCode);
           params2.put("author", author);
           for (Map<String, Object> orderItem : orderItems) {
             Map<String, Object> itemDetail = new HashMap<String, Object>();
             itemDetail.put("itemCode", StringUtil.getString(orderItem.get("OrderItemCode")));
             itemDetail.put("unit", StringUtil.getString(orderItem.get("OrderItemUnit")));
             itemDetail.put("std", Long.valueOf(StringUtil.getString(orderItem.get("OrderItemRule")))*100);
             itemDetail.put("number", Integer.valueOf(StringUtil.getString(orderItem.get("OrderItemNum"))));
             itemDetail.put("amt", Long.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")))*100);
             itemDetails.add(itemDetail);
           }
           params2.put("itemDetails", itemDetails);
           param.put("params", params2);
           params.add(param);
           bizExp.put("params", params);
           orderItemMap.put("bizExp", JSON.toJSONString(bizExp));
           orderItemLists.add(orderItemMap);
           orderDeatilInfo.put("orderItems",orderItemLists);
           //调接口，判断返回结果，成功更新缴费状态，失败给予提示
           Map<String,Object> tokenMap = bdcDdjfxxService.getCreditToken();
           if("0".equals(StringUtil.getString(tokenMap.get("code")))){
               log.info("办件号："+exeId +"-订单支付接口请求入参为："+JSON.toJSONString(orderDeatilInfo));
               Map<String, Object> payOrderInfoCreate = bdcDdjfxxService.
                       payOrderInfoCreate(orderDeatilInfo,
                       StringUtil.getString(tokenMap.get("token")));
               if(payOrderInfoCreate!=null){
                   log.info("办件号："+exeId +"-订单支付接口返回信息："+JSON.toJSONString(payOrderInfoCreate));
                   if("0".equals(StringUtil.getString(payOrderInfoCreate.get("code")))){
                       //存储收费方式
                       busRecord.put("SSSB_SFFS", sssb_sffs);
                       bdcGyjsjfwzydjService.saveOrUpdate(busRecord, 
                               "T_BDCQLC_GYJSJFWZYDJ", StringUtil.getValue(busRecord, "YW_ID"));
                       //更新缴费状态
                       Map<String, Object> bdcOrderdetail = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                               new String[] { "EXE_ID" }, new Object[] { exeId });
                       bdcOrderdetail.put("ORDER_STATUS", "1");
                       //同时将请求参数入库，留痕
                       bdcOrderdetail.put("ORDERSEND_JSON", JSON.toJSONString(orderDeatilInfo));
                       bdcGyjsjfwzydjService.saveOrUpdate(bdcOrderdetail, 
                               "T_BDC_ORDERDETAILINFO", StringUtil.getValue(bdcOrderdetail, "YW_ID"));
                       ajaxJson.setSuccess(true);
                       ajaxJson.setMsg("订单支付通知平潭通接口发起成功！");
                   }else{
                       ajaxJson.setSuccess(false);
                       ajaxJson.setMsg(""+StringUtil.getString(payOrderInfoCreate.get("msg")));
                   }
               }else{
                   ajaxJson.setSuccess(false);
                   ajaxJson.setMsg("接口服务异常！");
               }
           }else{
               ajaxJson.setSuccess(false);
               ajaxJson.setMsg("生成token失败");
               log.info("生成token失败");
           }
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("缺失对应的办件号！");
        }
        return ajaxJson;
    }
}
