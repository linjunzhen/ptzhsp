/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.pay.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bs.pubutil.basic.BsMD5Util;
import com.bs.security.BsSignUtil;
import com.bs.security.BsSignUtil.SignType;
import com.bs.security.epay.client.BsSignWithMac;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.SwbDataResService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.pay.service.PayService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 
 * 描述
 * @author Kester Chen
 * @created 2020年2月8日 上午10:22:18
 */
@Controller
@RequestMapping("/payController")
public class PayController extends BaseController {

    /**
     * 
     */
    private static String pubKey = "OTIwYjlkM2RkNWEwMzRkMTVlNjRiMWJmODU1"
            + "NjI5ZjY4ZjM5Y2U5ZjA3ZTZhOTEwYmI2Yzk2MWJhMWQxNDI4ODVlYjU1N"
            + "Tg1YjdjNTQ4Mzg4YWUzOThlZGYyNjljMTc1MjVlZjZkMzQwMGFmYmQyZj"
            + "lhMmMwZGVhN2E2MzE4MGY";
    /**
     * 
     */
    private static String privateKey = "OTIwYjlkM2RkNWEwMzRkMTVlNjRiMWJm"
            + "ODU1NjI5ZjY4ZjM5Y2U5ZjA3ZTZhOTEwYmI2Yzk2MWJhMWQxNDI4ODVlY"
            + "jU1NTg1YjdjNTQ4Mzg4YWUzOThlZGYyNjljMTc1MjVlZjZkMzQwMGFmYm"
            + "QyZjlhMmMwZGVhN2E2MzE4MGY=";
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(PayController.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;

    /**
     * 引入payService
     */
    @Resource
    private PayService payService;
    
    /**
     * 
     */
    @Resource
    private SwbDataResService swbDataResService;
    /**
     * 
     */
    @Resource
    private DictionaryService  dictionaryService;
    

    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2020年2月8日 上午10:27:04
     * @param request
     * @return
     */
    @RequestMapping(params="goPaySuccess")
    public ModelAndView goPaySuccess(HttpServletRequest request){
        String ordNum = request.getParameter("ordNum");
        String payMent = request.getParameter("payMent");
        String success = request.getParameter("success");
        String orgPara = request.getParameter("orgPara");
        String tranTime = request.getParameter("tranTime");
        String sign = request.getParameter("sign");
        String bankNo = request.getParameter("bankNo");
        String bankCode = request.getParameter("bankCode");
        String bankSign = request.getParameter("bankSign");
        
        BigDecimal big= new BigDecimal(payMent);
        boolean versign = BsSignWithMac.verifyPayOrderSign(ordNum,big,success,orgPara,tranTime,sign,pubKey);
        Map<String,String> params = new HashMap<String, String>();
        params.put("BANKNO", bankNo);
        params.put("BANKCODE", bankCode);
//        boolean verBankSign = BsSignUtil.verfitySign(params, privateKey, bankSign, SignType.MD5);
        boolean verBankSign = BsSignUtil.verfitySign(params, pubKey, bankSign, SignType.MD5);

        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("ORDNUM", ordNum);
        variables.put("PAYMENT", payMent);
        variables.put("SUCCESS", success);
        variables.put("ORGPARA", orgPara);
        variables.put("TRANTIME", tranTime);
        variables.put("SIGN", sign);
        variables.put("BANKNO", bankNo);
        variables.put("BANKCODE", bankCode);
        variables.put("BANKSIGN", bankSign);
        String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        variables.put("CREATE_TIME", createTime);
        variables.put("TYPE", "1");
        variables.put("VERSIGN", versign);
        variables.put("VERBANKSIGN", verBankSign);

        request.setAttribute("ordNum", ordNum);
        request.setAttribute("payMent", payMent);
        request.setAttribute("success", success);
        request.setAttribute("orgPara", orgPara);
        request.setAttribute("tranTime", tranTime);
        request.setAttribute("sign", sign);
        request.setAttribute("bankNo", bankNo);
        request.setAttribute("bankCode", bankCode);
        request.setAttribute("bankSign", bankSign);
        return new ModelAndView("pay/paySuccess");
    } 

    /**
     * 
     */
    @RequestMapping(params = "goAccPay")
    @ResponseBody
    public AjaxJson goAccPay(HttpServletRequest request) {

        String ordNum = request.getParameter("ordNum");
        String payMent = request.getParameter("payMent");
        String success = request.getParameter("success");
        String orgPara = request.getParameter("orgPara");
        String tranTime = request.getParameter("tranTime");
        String sign = request.getParameter("sign");
        String bankNo = request.getParameter("bankNo");
        String bankCode = request.getParameter("bankCode");
        String bankSign = request.getParameter("bankSign");
        
        BigDecimal big= new BigDecimal(payMent);
        boolean versign = BsSignWithMac.verifyPayOrderSign(ordNum,big,success,orgPara,tranTime,sign,pubKey);
        Map<String,String> params = new HashMap<String, String>();
        params.put("BANKNO", bankNo);
        params.put("BANKCODE", bankCode);
//      boolean verBankSign = BsSignUtil.verfitySign(params, privateKey, bankSign, SignType.MD5);
        boolean verBankSign = BsSignUtil.verfitySign(params, pubKey, bankSign, SignType.MD5);

        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("ORDNUM", ordNum);
        variables.put("PAYMENT", payMent);
        variables.put("SUCCESS", success);
        variables.put("ORGPARA", orgPara);
        variables.put("TRANTIME", tranTime);
        variables.put("SIGN", sign);
        variables.put("BANKNO", bankNo);
        variables.put("BANKCODE", bankCode);
        variables.put("BANKSIGN", bankSign);
        String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        variables.put("CREATE_TIME", createTime);
        variables.put("TYPE", "2");
        variables.put("VERSIGN", versign);
        variables.put("VERBANKSIGN", verBankSign);

        AjaxJson j = new AjaxJson();
        j.setMsg("Y");
        return j;
    }

    /**
     * 
     * 描述 发起缴费通知
     * @author Kester Chen
     * @created 2020年2月27日 上午9:37:08
     * @param request
     * @return
     */
    @RequestMapping("/initiatePayment")
    @ResponseBody
    public AjaxJson initiatePayment(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String payStr = request.getParameter("payStr");
        String exeId = request.getParameter("EXE_ID");
        String BEGINTIME = request.getParameter("BEGINTIME");
        String ENDTIME = request.getParameter("ENDTIME");
        String REMARK = request.getParameter("REMARK");
        String PROCESSEDOPINION = request.getParameter("PROCESSEDOPINION");
        Map<String, Object> execution = payService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        String name = execution.get("CUR_STEPNAMES")==null?
                "":execution.get("CUR_STEPNAMES").toString();
        String sname = name;
        SysUser sysUser = AppUtil.getLoginUser();
        String processedUser = sysUser.getFullname();
        String processedTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String resId = swbDataResService.saveJftzxxDataRes(exeId);
        List<Map<String,Object>> list = (List)JSONArray.parseArray(payStr);
        for (Map<String, Object> map : list) {
            String chargeDetailaMount = map.get("payNum")==null?
                    "":map.get("payNum").toString();
            String chargeId = map.get("RECORD_ID")==null?
                    "":map.get("RECORD_ID").toString();
            Map<String, Object> chargeInfo = payService.getByJdbc("T_WSBS_SERVICEITEM_CHARGE", 
                    new String[] { "RECORD_ID" },new Object[] { chargeId });
            String chargeDetailStandard = chargeInfo.get("CHARGEDETAILSTANDARD")==null?
                    "":chargeInfo.get("CHARGEDETAILSTANDARD").toString();
            double a = Double.parseDouble(chargeDetailaMount);
            double b = Double.parseDouble(chargeDetailStandard);
            double c = a*b;
            String chargeDetailPayment = ""+c;
            String code = "2";
            String OTHER_STATUS = "2";
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] {"TYPE_CODE", "DIC_NAME" }, new Object[] { "SWBDYZ", name }
            );
            if (dictionary != null) {
                OTHER_STATUS = (String) dictionary.get("DIC_CODE");
                code = OTHER_STATUS;
            }
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("CODE", code);
            variables.put("NAME", name);
            variables.put("SNAME", sname);
            variables.put("PROCESSEDUSER", processedUser);
            variables.put("PROCESSEDTIME", processedTime);
            variables.put("PROCESSEDOPINION", PROCESSEDOPINION);
            variables.put("REMARK", REMARK);
            variables.put("CHARGEDETAILAMOUNT", chargeDetailaMount);
            variables.put("CHARGEDETAILPAYMENT", chargeDetailPayment);
            variables.put("RESID", resId);
            variables.put("CHARGEID", chargeId);
            variables.put("EXEID", exeId);
            variables.put("BEGINTIME", BEGINTIME + " 00:00:00");
            variables.put("ENDTIME", ENDTIME + " 23:59:59");
            payService.saveOrUpdate(variables, "T_BSFW_PAYINFO", null);
        }
        execution.put("PAY_STATUS", "2");
        payService.saveOrUpdate(execution, "JBPM6_EXECUTION", exeId);
        
         j.setMsg("缴费请求发起成功");
        return j;
    }
}
