/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.bsfw.service.BdcDdjfxxService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 *
 * 描述： 国有建设及房屋转移controller
 *
 * @author Madison You
 * @created 2020年5月29日 上午12:14:56
 */
@Controller
@RequestMapping("/bdcGyjsjfwzydjController")
public class BdcGyjsjfwzydjController extends BaseController {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 9:23:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcGyjsjfwzydjController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/29 15:22:00
     * @param
     * @return
     */
    @Resource
    private BdcGyjsjfwzydjService bdcGyjsjfwzydjService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/29 15:22:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     */
    @Resource
    private  ExeDataService exeDataService;
    
    /**
     * 
     */
    @Resource
    private  BdcDdjfxxService bdcDdjfxxService;
    /**
     * 描述:获取土地用途数据
     *
     * @author Madison You
     * @created 2020/5/29 15:32:00
     * @param
     * @return
     */
    @RequestMapping("/loadTdytData")
    @ResponseBody
    public List<Map<String, Object>> loadTdytData(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> tdytList = dictionaryService.findDatasForSelect("ZDTDYT");
        return tdytList;
    }

    /**
     * 描述:获取宗地坐落区县数据
     *
     * @author Madison You
     * @created 2020/6/21 13:28:00
     * @param
     * @return
     */
    @RequestMapping("/loadZdzlqxData")
    @ResponseBody
    public List<Map<String, Object>> loadZdzlqxData(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> tdytList = dictionaryService.findDatasForSelect("ZDZLQX");
        return tdytList;
    }

    /**
     * 描述:获取宗地坐落镇数据
     *
     * @author Madison You
     * @created 2020/6/21 14:00:00
     * @param
     * @return
     */
    @RequestMapping("/loadZdzlzData")
    @ResponseBody
    public List<Map<String, Object>> loadZdzlzData(HttpServletRequest request, HttpServletResponse response) {
        String value = request.getParameter("value");
        if (StringUtils.isEmpty(value)) {
            value = "";
        }
        List<Map<String,Object>> list = bdcGyjsjfwzydjService.finZdzlData(value,"ZDZLZ");
        return list;
    }

    /**
     * 描述:获取宗地坐落乡村数据
     *
     * @author Madison You
     * @created 2020/6/21 14:00:00
     * @param
     * @return
     */
    @RequestMapping("/loadZdzlxcData")
    @ResponseBody
    public List<Map<String, Object>> loadZdzlxcData(HttpServletRequest request, HttpServletResponse response) {
        String value = request.getParameter("value");
        if (StringUtils.isEmpty(value)) {
            value = "";
        }
        List<Map<String,Object>> list = bdcGyjsjfwzydjService.finZdzlData(value,"ZDZLXC");
        return list;
    }

    /**
     * 描述:查询不动产单元号是否办理其它业务
     *
     * @author Madison You
     * @created 2020/6/28 15:58:00
     * @param
     * @return
     */
    @RequestMapping(params = "checkBdcdyh")
    @ResponseBody
    public Map<String, Object> checkBdcdyh(HttpServletRequest request , HttpServletResponse response) {
        Map<String,Object> returnMap = new HashMap<>();
        String bdcdyh = request.getParameter("bdcdyh");
        try{
            List<String> busList = bdcGyjsjfwzydjService.findIsDealBdcdyh(bdcdyh);
            if (busList != null && !busList.isEmpty()) {
                returnMap.put("success", true);
                returnMap.put("busList", busList);
            } else {
                returnMap.put("success", false);
            }
        } catch (Exception e) {
            log.info("查询不动产单元号在办业务出错");
            log.info(e.getMessage(), e);
        }
        return returnMap;
    }

    /**
     * 描述:不动产银行列表
     *
     * @author Madison You
     * @created 2020/8/18 9:18:00
     * @param
     * @return
     */
    @RequestMapping(params = "loadBdcBankList")
    @ResponseBody
    public List<Map<String, Object>> loadBdcBankList(HttpServletRequest request) {
        List<Map<String, Object>> list = bdcGyjsjfwzydjService.getBdcBankList(request);
        return list;
    }

    /**
     * 描述:新增不动产银行信息页面
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/8/18 9:30:00
     */
    @RequestMapping(params = "addBdcBankView")
    public ModelAndView addBdcBankView(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/bdcqlc/addBdcBankView");
    }

    /**
     * 描述:新增不动产银行信息
     *
     * @author Madison You
     * @created 2020/8/18 9:34:00
     * @param
     * @return
     */
    @RequestMapping(params = "addBdcBank")
    @ResponseBody
    public AjaxJson addBdcBank(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if (variables != null) {
                String bankCode = request.getParameter("BANK_CODE");
                String bankName = request.getParameter("BANK_NAME");
                Map<String,Object> bankMap1 = bdcGyjsjfwzydjService.getByJdbc("T_BDCQLC_BANK",
                        new String[]{"BANK_CODE"}, new Object[]{bankCode});
                if (bankMap1 != null) {
                    j.setSuccess(false);
                    j.setMsg("新增失败，已有此银行");
                    return j;
                }
                Map<String,Object> bankMap2 = bdcGyjsjfwzydjService.getByJdbc("T_BDCQLC_BANK",
                        new String[]{"BANK_NAME"}, new Object[]{bankName});
                if (bankMap2 != null) {
                    j.setSuccess(false);
                    j.setMsg("新增失败，已有此银行");
                    return j;
                }
                bdcGyjsjfwzydjService.saveOrUpdate(variables, "T_BDCQLC_BANK", null);
                j.setSuccess(true);
                j.setMsg("新增成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            j.setSuccess(false);
            j.setMsg("新增失败，请联系管理员");
        }
        return j;
    }

    /**
     * 描述:删除不动产银行信息
     *
     * @author Madison You
     * @created 2020/8/18 10:53:00
     * @param
     * @return
     */
    @RequestMapping(params = "delBdcBank")
    @ResponseBody
    public AjaxJson delBdcBank(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        try{
            if (!selectColNames.equals("")) {
                bdcGyjsjfwzydjService.remove("T_BDCQLC_BANK", new String[]{"BANK_ID"},
                        new Object[]{selectColNames});
                j.setMsg("删除成功");
                j.setSuccess(true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            j.setSuccess(false);
            j.setMsg("删除失败，请联系管理员");
        }
        return j;
    }

    /**
     * 描述:业务选择
     *
     * @author Madison You
     * @created 2020/9/16 9:47:00
     * @param
     * @return
     */
    @RequestMapping(params = "busSelectorItemView")
    public ModelAndView busSelectorItemView(HttpServletRequest request) {
        String zydjType = request.getParameter("zydjType");
        ModelAndView modelAndView = new ModelAndView();
        request.setAttribute("zydjType", zydjType);
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(zydjType)){
            if("4".equals(zydjType)){//存量房税费联办
                modelAndView = new ModelAndView("website/applyforms/bdcqlc/gyjsjfwzydj/clfsflb/busSelectorItemView");
            }else if("1".equals(zydjType)){//分户
                modelAndView = new ModelAndView("website/applyforms/bdcqlc/gyjsjfwzydj/fh/busSelectorItemView");
            }
        }else{
            modelAndView = new ModelAndView("website/applyforms/bdcqlc/gyjsjfwzydj/busSelectorItemView");
        }
        return modelAndView;
    }


    /**
     * 描述:获取涉税登记材料列表
     *
     * @author Madison You
     * @created 2020/12/1 14:15:00
     * @param
     * @return
     */
    @RequestMapping(params = "taxRelatedFileList")
    @ResponseBody
    public List<Map<String, Object>> taxRelatedFileList(HttpServletRequest request) {
        String fileIds = request.getParameter("fileIds");
        List<Map<String, Object>> list = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(fileIds)) {
            list = bdcGyjsjfwzydjService.getTaxRelatedFileList(fileIds);
        }
        return list;
    }

    
    /**
     * 
     * 描述    跳转至不动产在线收费页面
     * @author Allin Lin
     * @created 2021年11月18日 下午2:38:22
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcOnlineChargeView")
    public ModelAndView bdcOnlineChargeView(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String exeId = request.getParameter("exeId");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("exeId", exeId);
        Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                new String[] { "EXE_ID" }, new Object[] { exeId });
        if(bdcOrder!=null){
            request.setAttribute("OrderItemTotalPrice", bdcOrder.get("ORDER_TOTALPRICE"));
        }
        return new ModelAndView("bsdt/applyform/bdcqlc/gyjsjfwzydj/clfsflb/bdcOnlineChargeView");
    }
    
    
    /**
     * 
     * 描述    判断办件是否完成订单支付
     * @author Allin Lin
     * @created 2021年11月21日 下午3:07:03
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "isFinishOrderPay")
    @ResponseBody
    public AjaxJson isFinishOrderPay(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        String exeId  = request.getParameter("EXE_ID");
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(exeId)){
            Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            if(bdcOrder!=null && "2".equals(StringUtil.getString(bdcOrder.get("ORDER_STATUS")))){
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("已完成缴费！");
            }else{
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("未完成缴费！");
            }
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("缺失对应的办件号！");
        }
        return ajaxJson;
    }
    
    /**
     * 
     * 描述    发起订单支付通知
     * @author Allin Lin
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
        String data  = request.getParameter("data").toString();
        List<Map> orderItems =  new ArrayList<Map>();//订单明细
        String  sssb_sffs = request.getParameter("sssb_sffs");//收费方式
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
                   ajaxJson.setMsg("该办件号已缴费，不可重复发起缴费！");
                   return ajaxJson;
               }else if("1".equals(orderStatus)){//缴费中，可修改
                   orderDeatilInfo.put("method", 2);
               }else if("3".equals(orderStatus) || org.apache.commons.lang3.StringUtils.isEmpty(orderStatus)){
                   //新增缴费
                   orderDeatilInfo.put("method", 1);
               }
               bdcGyjsjfwzydjService.saveOrUpdate(bdcOrder,
                       "T_BDC_ORDERDETAILINFO", StringUtil.getValue(bdcOrder, "YW_ID"));
           }else{
               Map<String, Object> order = new HashMap<String, Object>();
               order.put("EXE_ID",  exeId);
               order.put("CREATE_TIME",  DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
               order.put("ORDER_TOTALPRICE", Double.valueOf(ORDER_TOTALPRICE));
               order.put("ORDERITEMS_JSON", JSON.toJSONString(orderItems));
               bdcGyjsjfwzydjService.saveOrUpdate(order,
                       "T_BDC_ORDERDETAILINFO", StringUtil.getValue(order, "YW_ID"));
               orderDeatilInfo.put("method", 1);
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
           orderDeatilInfo.put("payAmount",Double.valueOf(ORDER_TOTALPRICE)*100);//金额单位从元转分
           orderDeatilInfo.put("orderTitle","不动产非税缴费");
           orderDeatilInfo.put("orderDetail","不动产非税缴费");
           List<Map<String, Object>> orderItemLists = new ArrayList<Map<String,Object>>();//订单明细集合
           Map<String,Object> bizExp = new HashMap<String, Object>();//订单非税-博思
           Map<String,Object> orderItemMap = new HashMap<String, Object>();//订单明细
           List<Map<String, Object>> params = new ArrayList<Map<String,Object>>();
           orderItemMap.put("itemIndex", "1");
           orderItemMap.put("itemAmount", Double.valueOf(ORDER_TOTALPRICE)*100);
           orderItemMap.put("remark", "不动产非税缴费");
           bizExp.put("operation", "CREATE");
           //多个收费项目对应不同的票据代码
           for (Map<String, Object> orderItem : orderItems) {
             Map<String,Object> param = new HashMap<String, Object>();
             Map<String,Object> params2 = new HashMap<String, Object>();
             param.put("letterDate", DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd"));
             param.put("payerName",StringUtil.getString(piList.get(0).get("POWERPEOPLENAME")));
             param.put("letterAmount",Double.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")))*100);
             String author = dictionaryService.getDicNames("ddConfig", "author");
             params2.put("placeCode", StringUtil.getString(dictionaryService.
                     get("ddConfig", "placeCode").get("DIC_DESC")));
             params2.put("billCode", StringUtil.getString(orderItem.get("OrderBillCode")));
             params2.put("author", author);
             List<Map<String, Object>> itemDetails = new ArrayList<Map<String,Object>>();
             Map<String, Object> itemDetail = new HashMap<String, Object>();
             itemDetail.put("itemCode", StringUtil.getString(orderItem.get("OrderItemCode")));
             itemDetail.put("unit", StringUtil.getString(orderItem.get("OrderItemUnit")));
             itemDetail.put("std", Double.valueOf(StringUtil.getString(orderItem.get("OrderItemRule")))*100);
             itemDetail.put("number", Integer.valueOf(StringUtil.getString(orderItem.get("OrderItemNum"))));
             itemDetail.put("amt", Double.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")))*100);
             itemDetails.add(itemDetail);
             params2.put("itemDetails", itemDetails);
             param.put("params", params2);
             params.add(param);
           }
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
                       //下发短信，通知群众进行缴费（首次下单时）
                       if("1".equals(StringUtil.getString(orderDeatilInfo.get("method")))){
                           Map<String, Object> send = new HashMap<String, Object>();
                           send.put("exeId", exeId);
                           send.put("toPhone", StringUtil.getString(piList.get(0).get("POWERPEOPLEMOBILE")));//发送缴费人手机号
                           send.put("manName", StringUtil.getString(piList.get(0).get("POWERPEOPLENAME")));//发送缴费人
                           send.put("sendType", "1");
                           Map<String, Object> result = bdcDdjfxxService.sendSuccessMsg(send);
                           if ((boolean)result.get("success") == true) {
                               log.info("调用二手房转移通知群众缴费短信成功：返回" + result);
                           } else {
                               log.error("调用二手房转移通知群众缴费短信成功失败：返回" + result);
                           }
                       }
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
    
    /**
     * 
     * 描述    AjaxJosn请求获取不动产收费科目列表
     * @author Allin Lin
     * @created 2021年11月19日 上午11:34:40
     * @param request
     * @param response
     */
    @RequestMapping(params = "bdcOnlineChargedatagrid")
    public void bdcOnlineChargedatagrid(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(exeId)){
            Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            if(bdcOrder!=null){
                List<Map> orderItems = JSONArray.parseArray(StringUtil.
                        getValue(bdcOrder, "ORDERITEMS_JSON"), Map.class);
                if(orderItems.size()>0){
                    for (Map orderItem : orderItems) {
                        list.add(orderItem);
                    }
                }
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    显示不动产在线收费科目信息
     * @author Allin Lin
     * @created 2021年11月18日 下午3:46:35
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "onlineChargeInfo")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");//收费科目编码
        String exeId = request.getParameter("exeId");//申报号
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(entityId) && !"undefined".equals(entityId)) {//编辑操作
            Map<String, Object> orderCharge = new HashMap<String, Object>();
            Map<String, Object> bdcOrder = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            if(bdcOrder!=null){
                List<Map> orderItems = JSONArray.parseArray(StringUtil.
                        getValue(bdcOrder, "ORDERITEMS_JSON"), Map.class);
                if(orderItems.size()>0){
                    for (Map orderItem : orderItems) {
                        if(entityId.equals(StringUtil.getValue(orderItem, "OrderItemCode"))){
                            orderCharge = orderItem;
                            break;
                        }
                    }
                }
            }
            request.setAttribute("orderCharge", orderCharge);
        }
        request.setAttribute("exeId", exeId);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("bsdt/applyform/bdcqlc/gyjsjfwzydj/clfsflb/bdcOnlineChargeInfo");
    }
    
    /**
     * 
     * 描述    新增或修改收费科目信息
     * @author Allin Lin
     * @created 2021年11月19日 上午10:15:17
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateOrderItem")
    @ResponseBody
    public AjaxJson saveOrUpdateOrderItem(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String EXE_ID = request.getParameter("EXE_ID");//申报号
        String entityId = request.getParameter("entityId");//收费科目编码（一笔订单中是唯一的）
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> orderDetail = new HashMap<String, Object>();
        List<Map<String, Object>> orderItemList = new ArrayList<Map<String,Object>>();
        double orderTotalPrice = 0.00; 
        //判断是新增 or 修改操作
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(EXE_ID)){
            Map<String, Object> orderDetailInfo = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                    new String[] { "EXE_ID" }, new Object[] { EXE_ID });
            if(orderDetailInfo!=null){
                List<Map> orderItems = JSONArray.parseArray(StringUtil.
                        getValue(orderDetailInfo, "ORDERITEMS_JSON"), Map.class);
                //判断是已有收费订单中-新增收费明细还是修改收费明细
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(entityId) && !"undefined".equals(entityId)){//修改
                    if(orderItems.size()>0){
                        for (Map orderItem : orderItems) {
                            if(entityId.equals(StringUtil.getValue(orderItem, "OrderItemCode"))){
                                variables.remove("EXE_ID");
                                variables.remove("entityId");
                                variables.remove("saveOrUpdateOrderItem");
                                orderItem.putAll(variables);
                                break;
                            }
                        }
                        //计算订单总额
                        for (Map orderItem : orderItems) {
                            orderTotalPrice += Double.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")));
                        }
                    }
                    orderDetailInfo.remove("ORDERITEMS_JSON");
                    orderDetailInfo.put("ORDERITEMS_JSON", JSON.toJSONString(orderItems));
                    orderDetailInfo.put("ORDER_TOTALPRICE", (double) Math.round(orderTotalPrice * 100) / 100);
                    String recordId =  bdcGyjsjfwzydjService.saveOrUpdate(orderDetailInfo,
                            "T_BDC_ORDERDETAILINFO", StringUtil.getValue(orderDetailInfo, "YW_ID"));
                    sysLogService.saveLog("修改了ID为["+recordId+"]的不动产-收费科目信息记录",SysLogService.OPERATE_TYPE_EDIT);
                    j.setMsg("保存成功");
                    j.setSuccess(true);
                }else{
                    //判断新增的收费明细科目编码是否已经存在，存在则提示在原有基础上修改即可。
                    boolean isExist = false;
                    if(orderItems.size()>0){
                        for (Map orderItem : orderItems) {
                            if(StringUtil.getValue(variables, "OrderItemCode").
                                    equals(StringUtil.getValue(orderItem, "OrderItemCode"))){
                                isExist = true;
                                break;
                            }
                        }
                    }
                    if(isExist==true){
                        j.setSuccess(false);
                        j.setMsg("同笔订单中该收费编码已添加，请在原有的收费明细上进行修改即可！");
                    }else{
                        variables.remove("EXE_ID");
                        variables.remove("entityId");
                        variables.remove("saveOrUpdateOrderItem");
                        orderItems.add(variables);
                        orderDetailInfo.put("ORDERITEMS_JSON", JSON.toJSONString(orderItems));
                        //计算订单总额
                        for (Map orderItem : orderItems) {
                            orderTotalPrice += Double.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")));
                        }
                        orderDetailInfo.put("ORDER_TOTALPRICE", (double) Math.round(orderTotalPrice * 100) / 100);
                        String recordId =  bdcGyjsjfwzydjService.saveOrUpdate(orderDetailInfo, 
                                "T_BDC_ORDERDETAILINFO", StringUtil.getValue(orderDetailInfo, "YW_ID"));
                        sysLogService.saveLog("修改了ID为["+recordId+"]的不动产-收费科目信息记录",SysLogService.OPERATE_TYPE_EDIT);
                        j.setMsg("保存成功");
                        j.setSuccess(true);
                    }
                    
                }
            }else {//新增缴费订单的第一笔收费明细
                orderDetail.put("EXE_ID", EXE_ID);
                variables.remove("EXE_ID");
                variables.remove("entityId");
                variables.remove("saveOrUpdateOrderItem");
                orderItemList.add(variables);
                orderDetail.put("ORDERITEMS_JSON", JSON.toJSONString(orderItemList));
                //计算订单总额
                for (Map orderItem : orderItemList) {
                    orderTotalPrice += Double.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")));
                }
                orderDetail.put("ORDER_TOTALPRICE", (double) Math.round(orderTotalPrice * 100) / 100);
                String recordId = bdcGyjsjfwzydjService.saveOrUpdate(orderDetail, "T_BDC_ORDERDETAILINFO", null);
                sysLogService.saveLog("新增了ID为["+recordId+"]的不动产-收费科目信息记录",SysLogService.OPERATE_TYPE_ADD);
                j.setMsg("保存成功");
                j.setSuccess(true);
            }
        }
        return j;
    }
    
    
    /**
     * 
     * 描述    删除收费科目信息
     * @author Allin Lin
     * @created 2021年11月19日 上午11:05:37
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDelOnineCharge")
    @ResponseBody
    public AjaxJson multiDelOnineCharge(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");//收费科目编码
        String EXE_ID = request.getParameter("EXE_ID");//收费申报号
        String[] orderItemCodes = selectColNames.split(";");
        double orderTotalPrice = 0.00; 
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(EXE_ID)){
            Map<String, Object> orderDetailInfo = bdcGyjsjfwzydjService.getByJdbc("T_BDC_ORDERDETAILINFO",
                    new String[] { "EXE_ID" }, new Object[] { EXE_ID });
            List<Map> orderItems = JSONArray.parseArray(StringUtil.
                    getValue(orderDetailInfo, "ORDERITEMS_JSON"), Map.class);
            if(orderItemCodes.length>0){
                for (String orderItemCode : orderItemCodes) {
                    for (Map orderItem : orderItems) {
                        if(orderItemCode.equals(StringUtil.getString(orderItem.get("OrderItemCode")))){
                            orderItems.remove(orderItem);
                            break;
                        }
                    }
                } 
              orderDetailInfo.remove("ORDERITEMS_JSON");
              orderDetailInfo.put("ORDERITEMS_JSON", JSON.toJSONString(orderItems));
              //计算订单总额
              for (Map orderItem : orderItems) {
                  orderTotalPrice += Double.valueOf(StringUtil.getString(orderItem.get("OrderItemPrice")));
              }
              orderDetailInfo.put("ORDER_TOTALPRICE", (double) Math.round(orderTotalPrice * 100) / 100);
              bdcGyjsjfwzydjService.saveOrUpdate(orderDetailInfo, "T_BDC_ORDERDETAILINFO", 
                      StringUtil.getValue(orderDetailInfo, "YW_ID"));
            }
        }
        j.setMsg("删除成功");
        return j;
    }
}
