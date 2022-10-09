/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.call.service.CallSetService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.PushDataToSJJXXZXService;
import net.evecom.platform.thread.PushDateToSJJXXZXRunnable;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年7月3日 下午4:01:09
 */
@Controller
@RequestMapping("/newCallController")
public class NewCallController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(NewCallController.class);

    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入callSetService
     */
    @Resource
    private CallSetService callSetService;
    /**
     * 引入dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    
    /**
     * 引入省经济中心信息推送service
     */
    @Resource
    private PushDataToSJJXXZXService pushDataToSJJXXZXService;
    
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/23 14:50:00
     * @param
     * @return
     */
    private final String SMS_SEND_TEMPLATEID = "184909";

    /**
     * 描述:转发到178服务器地址
     *
     * @author Madison You
     * @created 2019/12/2 10:34:00
     * @param
     * @return
     */
    private final String HLW_URL = "http://192.168.129.178/newCallController/sendSceneNotifyForHlw.do";
    
    /**
     * 
     * 描述    跳转取号部门选择
     * @author Danto Huang
     * @created 2018年7月3日 下午4:03:23
     * @param request
     * @return
     */
    @RequestMapping("/toDeptChoose")
    public ModelAndView toDeptChoose(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        List<Map<String,Object>> departList = newCallService.findTakeNoDepart(roomNo);
        String lc = dictionaryService.get("roomNo", roomNo).get("DIC_DESC").toString();
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("lc", lc);
        request.setAttribute("departList", departList);
        return new ModelAndView("callnew/takeNo/chooseDept"); 
    }
    
    /**
     * 
     * 描述    跳转取号业务选择
     * @author Danto Huang
     * @created 2018年7月4日 上午11:06:23
     * @param request
     * @return
     */
    @RequestMapping("/toBusinessChoose")
    public ModelAndView toBusinessChoose(HttpServletRequest request){
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        List<Map<String,Object>> businessList = newCallService.findBusinessByDepartId(departId);
        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("businessList", businessList);
        return new ModelAndView("callnew/takeNo/chooseBusiness");
    }
    
    /**
     * 
     * 描述    跳转一窗通办取号业务选择
     * @author Danto Huang
     * @created 2018年7月4日 上午11:06:23
     * @param request
     * @return
     */
    @RequestMapping("/toYctbBusinessChoose")
    public ModelAndView toYctbBusinessChoose(HttpServletRequest request){
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        List<Map<String,Object>> businessList = newCallService.findBusinessByDepartId(departId);
        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("businessList", businessList);
        return new ModelAndView("callnew/takeNo/chooseYctbBusiness");
    }

    /**
     * 
     * 描述    跳转一窗通办取号业务事项选择
     * @author Danto Huang
     * @created 2018年7月4日 上午11:06:23
     * @param request
     * @return
     */
    @RequestMapping("/toYctbBusinessItemChoose")
    public ModelAndView toYctbBusinessItemChoose(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String macType = request.getParameter("macType");
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
//        filter.addFilter("Q_CB.DEPART_ID_=", departId);
        filter.addSorted("ecount", "desc");
        List<Map<String,Object>> businessItemList = newCallService.findYctbBusinessItemDataBySqlFilter2(filter);
        List<Map<String,Object>> businessItemList1 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> businessItemList2 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> businessItemList3 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> businessItemList4 = new ArrayList<Map<String,Object>>();
        //List<Map<String,Object>> businessItemList5 = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : businessItemList) {
            String businessCode = map.get("BUSINESS_CODE")==null?
                    "":map.get("BUSINESS_CODE").toString();
            if ("A".equals(businessCode)||"B".equals(businessCode)
                    ||"O".equals(businessCode)||"W".equals(businessCode)
                    ||"X".equals(businessCode)||"J".equals(businessCode)||"Q".equals(businessCode)) {
                businessItemList1.add(map);
            } else if ("C".equals(businessCode)||"D".equals(businessCode)
                    ||"Y".equals(businessCode)) {
                businessItemList2.add(map);
            } else if ("E".equals(businessCode)
                    ||"F".equals(businessCode)||"K".equals(businessCode)) {
                businessItemList3.add(map);
            } else if ("G".equals(businessCode)||"H".equals(businessCode)
                    ||"P".equals(businessCode)) {
                businessItemList4.add(map);
            }
        }
        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("macType", macType);
        request.setAttribute("businessItemList1", businessItemList1);
        request.setAttribute("businessItemList2", businessItemList2);
        request.setAttribute("businessItemList3", businessItemList3);
        request.setAttribute("businessItemList4", businessItemList4);
        return new ModelAndView("callnew/takeNo/chooseYctbBusinessItem");
    }
    /**
     * 
     * 描述    跳转一窗通办取号业务事项选择
     * @author Danto Huang
     * @created 2018年7月4日 上午11:06:23
     * @param request
     * @return
     */
    @RequestMapping("/toYctbBusinessItemChooseMacW")
    public ModelAndView toYctbBusinessItemChooseMacW(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String macType = request.getParameter("macType");
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
//        filter.addFilter("Q_CB.DEPART_ID_=", departId);
        filter.addSorted("ecount", "desc");
        List<Map<String,Object>> businessItemList = newCallService.findYctbBusinessItemDataBySqlFilter2(filter);
        List<Map<String,Object>> businessItemList1 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> businessItemList2 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> businessItemList3 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> businessItemList4 = new ArrayList<Map<String,Object>>();
        //List<Map<String,Object>> businessItemList5 = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : businessItemList) {
            String businessCode = map.get("BUSINESS_CODE")==null?
                    "":map.get("BUSINESS_CODE").toString();
            if ("A".equals(businessCode)||"B".equals(businessCode)
                    ||"O".equals(businessCode)||"W".equals(businessCode)
                    ||"X".equals(businessCode)||"J".equals(businessCode)||"Q".equals(businessCode)) {
                businessItemList1.add(map);
            } else if ("C".equals(businessCode)||"D".equals(businessCode)
                    ||"Y".equals(businessCode)) {
                businessItemList2.add(map);
            } else if ("E".equals(businessCode)
                    ||"F".equals(businessCode)||"K".equals(businessCode)) {
                businessItemList3.add(map);
            } else if ("G".equals(businessCode)||"H".equals(businessCode)
                    ||"P".equals(businessCode)) {
                businessItemList4.add(map);
            }
        }
        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("macType", macType);
        request.setAttribute("businessItemList1", businessItemList1);
        request.setAttribute("businessItemList2", businessItemList2);
        request.setAttribute("businessItemList3", businessItemList3);
        request.setAttribute("businessItemList4", businessItemList4);
        return new ModelAndView("callnew/takeNo/chooseYctbBusinessItemMacW");
    }
    /**
     * 
     * 描述 根据业务信息转跳
     * @author Kester Chen
     * @created 2019年3月6日 下午2:47:04
     * @param request
     * @return
     */
    @RequestMapping("/toYctbItemChoose")
    public ModelAndView toYctbItemChoose(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        String businessCode = request.getParameter("businessCode");
        request.setAttribute("businessCode", businessCode);
        String businessName = request.getParameter("businessName");
        request.setAttribute("businessName", businessName);
        String departId = request.getParameter("departId");
        request.setAttribute("departId", departId);
        filter.addFilter("Q_t.BUSINESS_CODE_=", businessCode);
        List<Map<String,Object>> itemList = newCallService.findYctbItemDataBySqlFilter(filter);
        request.setAttribute("itemList", itemList);
        return new ModelAndView("callnew/takeNo/chooseYctbItem");
    }
    /**
     * 
     * 描述 
     * @author Kester Chen
     * @created 2019年3月8日 下午3:49:27
     * @param request
     * @return
     */
    @RequestMapping(params="toMaterChoose")
    public ModelAndView toMaterChoose(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String businessCode = request.getParameter("businessCode");
        String businessName = request.getParameter("businessName");
        String departId = request.getParameter("departId");

        filter.addFilter("Q_S.ITEM_CODE_=",itemCode);
        List<Map<String, Object>> materList = applyMaterService.findForItem2(filter);
        
        List<Map<String,Object>> materTypeList = newCallService.findMaterByItemCode(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("defKey", defKey);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("businessName", businessName);
        request.setAttribute("departId", departId);
        if(materTypeList!=null&&materTypeList.size()>0){
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callnew/takeNo/chooseMater"); 
        }else{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("RECORD_ID", "");
            map.put("BUSCLASS_NAME", "");
            materTypeList.add(map);
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callnew/takeNo/chooseMater"); 
//            return new ModelAndView("callnew/takeNo/readCard");
        }
    }
    /**
     * 
     * 描述 
     * @author Kester Chen
     * @created 2019年3月8日 下午3:49:27
     * @param request
     * @return
     */
    @RequestMapping(params="toMaterChooseMacW")
    public ModelAndView toMaterChooseMacW(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String businessCode = request.getParameter("businessCode");
        String businessName = request.getParameter("businessName");
        String departId = request.getParameter("departId");

        filter.addFilter("Q_S.ITEM_CODE_=",itemCode);
        List<Map<String, Object>> materList = applyMaterService.findForItem2(filter);
        
        List<Map<String,Object>> materTypeList = newCallService.findMaterByItemCode(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("defKey", defKey);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("businessName", businessName);
        request.setAttribute("departId", departId);
        if(materTypeList!=null&&materTypeList.size()>0){
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callnew/takeNo/chooseMaterMacW"); 
        }else{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("RECORD_ID", "");
            map.put("BUSCLASS_NAME", "");
            materTypeList.add(map);
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callnew/takeNo/chooseMaterMacW"); 
//            return new ModelAndView("callnew/takeNo/readCard");
        }
    }
    /**
     * 
     * 描述 进入办事部门明细
     * @author Danto Huang
     * @created 2016-1-16 下午10:17:14
     * @param request
     * @return
     */
    @RequestMapping(params="toMaterTypeChoose")
    public ModelAndView toMaterTypeChoose(HttpServletRequest request){
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String roomNo = request.getParameter("roomNo");
        String businessCode = request.getParameter("businessCode");
        String businessName = request.getParameter("businessName");
        String departId = request.getParameter("departId");
        List<Map<String,Object>> materTypeList = newCallService.findByItemCode(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("defKey", defKey);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("businessName", businessName);
        request.setAttribute("departId", departId);
        if(materTypeList!=null&&materTypeList.size()>0){
            request.setAttribute("materTypeList", materTypeList);
            return new ModelAndView("callnew/takeNo/chooseMaterType"); 
        }else{
            return new ModelAndView("callnew/takeNo/readCard");
        }
    }
    /**
     * 
     * 描述    生成取号结果
     * @author Danto Huang
     * @created 2018年7月4日 下午5:26:04
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/generateNo")
    @ResponseBody
    public AjaxJson generateNo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Map<String,Object> variables = new HashMap<String, Object>();
        String businessCode = request.getParameter("businessCode");
        String lineName = request.getParameter("lineName");
        String roomNo = request.getParameter("roomNo");
//        List<Map<String,Object>> list = newCallService.findYctbWaitingList();
//        if (list.size()<40) {//小于80时去除13、14、15号窗口BDHF业务
//            List<Map<String, Object>> winUserlist = callSetService.findWinUser();
//            for (Map<String, Object> map : winUserlist) {
//                String winBusinessCodes = map.get("WIN_BUSINESS_CODES")==null?
//                        "":map.get("WIN_BUSINESS_CODES").toString();
//                String winBusinessNames = map.get("WIN_BUSINESS_NAMES")==null?
//                        "":map.get("WIN_BUSINESS_NAMES").toString();
//                if (StringUtils.isNotEmpty(winBusinessCodes)&&winBusinessCodes.endsWith(",B,D,F,H")) {
//                    winBusinessCodes = winBusinessCodes.replace(",B,D,F,H", "");
//                    winBusinessNames = winBusinessNames.replace(",不动产快件,公积金快件,医保快件,社保快件", "");
//                    map.put("WIN_BUSINESS_CODES", winBusinessCodes);
//                    map.put("WIN_BUSINESS_NAMES", winBusinessNames);
//                    callSetService.saveOrUpdate(map, "T_CKBS_WIN_USER", map.get("RECORD_ID").toString());
//                }
//            }
//        } else if (list.size()>50) {//大于100时增加13、14、15号窗口BDHF业务
//            List<Map<String, Object>> winUserlist = callSetService.findWinUser();
//            for (Map<String, Object> map : winUserlist) {
//                String winBusinessCodes = map.get("WIN_BUSINESS_CODES")==null?
//                        "":map.get("WIN_BUSINESS_CODES").toString();
//                String winBusinessNames = map.get("WIN_BUSINESS_NAMES")==null?
//                        "":map.get("WIN_BUSINESS_NAMES").toString();
//                if (StringUtils.isNotEmpty(winBusinessCodes)
//                        &&!winBusinessCodes.endsWith(",B,D,F,H")) {
//                    winBusinessCodes = winBusinessCodes+",B,D,F,H";
//                    winBusinessNames = winBusinessNames+",不动产快件,公积金快件,医保快件,社保快件";
//                    map.put("WIN_BUSINESS_CODES", winBusinessCodes);
//                    map.put("WIN_BUSINESS_NAMES", winBusinessNames);
//                    callSetService.saveOrUpdate(map, "T_CKBS_WIN_USER", map.get("RECORD_ID").toString());
//                }
//            }
//        }
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String materTypeId = request.getParameter("materTypeId");
        String lineCardNo = request.getParameter("lineCardNo");
        String zjlx = request.getParameter("LINE_ZJLX");
        String isVip = request.getParameter("isVip");
        String isAppoint = request.getParameter("isAppoint");
        String appointmentId = request.getParameter("appointmentId");
        String address = request.getParameter("address");
        Map<String, Object> lostPromiseMap = newCallService.checkLostPromiseByBusCode(lineName, lineCardNo,
                businessCode);
        Sm4Utils sm4 = new Sm4Utils();
        if(StringUtils.isEmpty(businessCode)||StringUtils.isEmpty(lineCardNo)||lineCardNo.equals("undefined")){
            j.setMsg("无效取号信息！");
            j.setSuccess(false);
        }else if(!newCallService.isWinAccept(businessCode)){
            j.setMsg("您选择的业务暂无对应办事窗口，请前往人工导引台咨询！");
            j.setSuccess(false);
        }else if(newCallService.isWaiting(businessCode, sm4.encryptDataCBC(lineCardNo))){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else if ((boolean)lostPromiseMap.get("isLostPromise")) {
            j.setMsg((String)lostPromiseMap.get("msg"));
            j.setSuccess(false);
        }else{
            variables.put("BUSINESS_CODE", businessCode);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            variables.put("LINE_ADDRESS", address);
            if(StringUtils.isEmpty(roomNo)){
                roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                        new Object[] { businessCode }).get("BELONG_ROOM").toString();
            }
            variables.put("ROOM_NO", roomNo);
            variables.put("ITEM_CODE", itemCode);
            variables.put("ITEM_NAME", itemName);
            variables.put("DEF_KEY", defKey);
            variables.put("MATER_TYPE_ID", materTypeId);
            
            /**
             * 2019年9月10日 10:48:21  更改取号时间为文件服务器时间
             */
            Properties properties = FileUtil.readProperties("project.properties");
            String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
            String url = uploadFileUrlIn + "upload/getFileSystemTime";
            String resultMsg = HttpRequestUtil.sendBdcPost(url, "", "UTF-8");
            String datetime = "";
            if (StringUtils.isNotEmpty(resultMsg)) {
                Map<String, Object> result = JSON.parseObject(resultMsg, Map.class);
                if (null != result && result.size() > 0) {
                    datetime = result.get("data") == null ? "" : result.get("data").toString();                   
                }
            }
            if (StringUtils.isNotEmpty(datetime)) {
                variables.put("CREATE_TIME", datetime);
            }
            
            if(StringUtils.isNotEmpty(isVip)){
                variables.put("IS_VIP", isVip);
            }
            if(StringUtils.isNotEmpty(isAppoint)){
                variables.put("IS_APPOINTMENT", isAppoint);
            }
            variables = newCallService.getLineNo(variables);
            
            String recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", null);
            //如果属于A、B厅，那么返回大厅的等待人数
            int waitNum =0;
            int businessWaitNum=0;
            if("A".equals(roomNo)||"B".equals(roomNo)){
                waitNum=newCallService.getWaitingNumByRoomNo(roomNo);
                businessWaitNum=newCallService.getWaitingNumByBusinessCode(businessCode);
            }else{
                waitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
            }
            String careWinNo = newCallService.getCareWin(businessCode);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", variables.get("LINE_NO"));
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            line.put("businessWaitNum",businessWaitNum);
            
            //获取办事（取号）用户信息表中的手机号
            String cardNo = sm4.encryptDataCBC(lineCardNo);
            Map<String, Object> lineUser = newCallService.getByJdbc("T_BSFW_LINEUSERS", 
                    new String[]{"LINE_CARDNO"}, new Object[]{cardNo});
            if(lineUser==null){
                //将用户信息更新
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("LINE_NAME", lineName);
                user.put("LINE_CARDNO", lineCardNo);
                newCallService.saveOrUpdate(user, "T_BSFW_LINEUSERS", null);
                line.put("userMobile", "");
            }else if(StringUtils.isEmpty(StringUtil.getString(lineUser.get("LINE_MOBILE")))){
                line.put("userMobile", "");
            }else{//存在用户信息
                String mobile = StringUtil.getString(lineUser.get("LINE_MOBILE"));//手机号中间4位数做隐藏
                String replaceMobile =mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
                line.put("userMobile",replaceMobile);
            }
            
            if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                Map<String,Object> appointment = new HashMap<String, Object>();
                appointment.put("NUM_ID", recordId);
                appointment.put("IS_TAKE", "1");
                if (StringUtils.isNotEmpty(datetime)) {
                    appointment.put("TAKE_TIME", datetime);
                }else{
                    appointment.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                }
                newCallService.saveOrUpdate(appointment, "T_CKBS_APPOINTMENT_APPLY", appointmentId);
            }
            //小程序消息推送
            if(zjlx.equals("SF")){
                String businessName = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                        new Object[] { businessCode }).get("BUSINESS_NAME").toString();
                Map<String,Object> lineInfo = new HashMap<String, Object>();
                lineInfo.put("subjectInfo", "平潭综合实验区行政服务中心");
                lineInfo.put("lineNo", variables.get("LINE_NO"));
                lineInfo.put("time", variables.get("CREATE_TIME"));
                lineInfo.put("queueInfo", roomNo+"区等候人数："+waitNum);
                lineInfo.put("itemInfo", businessName+"等候人数："+(businessWaitNum==0?"":businessWaitNum));
                lineInfo.put("remark", "请留意"+roomNo+"区"+careWinNo+"号窗口");
                Map<String,Object> businessInfo = new HashMap<String, Object>();
                businessInfo.put("lineCardNo", lineCardNo);
                businessInfo.put("lineInfoJson", JSON.toJSONString(lineInfo));
                HttpSendUtil.sendPostParams(HLW_URL, businessInfo);
//                sendSceneNotify(businessInfo);
            }
            //推送至省经济中心取号实时记录
            if(StringUtil.isNotEmpty(variables)){
                variables.put("LINE_CARDNO_TWO", lineCardNo);
                System.out.println("variables   " + variables);
                String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
                if ("开".equals(switchTurn)) {
                    if(isAppoint != null && "1".equals(isAppoint.toString())){
                        //预约取号
                        pushDataToSJJXXZXService.pushDateToSJJXXZX(variables, "addQueueOrderInfoTwo");
                    }else{
                        //普通现场取号
                        pushDataToSJJXXZXService.pushDateToSJJXXZX(variables, "addQueueTaskInfo");
                    }
                }
            }
            j.setJsonString(JSON.toJSONString(line));
        }
        return j;
    }

    /**
     * 描述:推送小程序取号通知消息162转发到178
     *
     * @author Madison You
     * @created 2019/12/2 10:30:00
     * @param
     * @return
     */
    @RequestMapping("/sendSceneNotifyForHlw")
    @ResponseBody
    public void sendSceneNotifyForHlw(HttpServletRequest request,HttpServletResponse response) {
        String lineCardNo = request.getParameter("lineCardNo");
        String lineInfoJson = request.getParameter("lineInfoJson");
        HashMap<String, Object> businessInfo = new HashMap<>();
        businessInfo.put("lineCardNo", lineCardNo);
        businessInfo.put("lineInfoJson", lineInfoJson);
        sendSceneNotify(businessInfo);
    }
    
    /**
     * 
     * 描述    推送小程序取号通知消息
     * @author Danto Huang
     * @created 2019年7月26日 下午3:19:12
     * @param businessInfo
     */
    private void sendSceneNotify(Map<String,Object> businessInfo){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String appId = properties.getProperty("APPLET_NOTIFY_APPID");
        String secretKey = properties.getProperty("APPLET_NOTIFY_KEY");
        String url = properties.getProperty("APPLET_NOTIFY_URL");
        Map<String,String> dataMap = new HashMap<String, String>();
        dataMap.put("appId", appId);
        dataMap.put("scene", "WINDOW_TAKE_NO");
        dataMap.put("timestamp", String.valueOf((new Date()).getTime()));
        dataMap.put("number", businessInfo.get("lineCardNo").toString());
        dataMap.put("businessInfo", businessInfo.get("lineInfoJson").toString());
        String dataJson = JSON.toJSONString(dataMap);
        try {
            String data = AesUtils.encryptToBase64(dataJson,secretKey);
            String sign = EncryptUtils.buildSign(JSON.parseObject(dataJson), secretKey);
            Map<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("appId", appId);
            paramMap.put("data", data);
            paramMap.put("sign", sign);
            HttpRequestUtil.sendBdcPost(url, JSON.toJSONString(paramMap));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        
    }


    /**
     * 
     * 描述    根据身份证信息跳转预约取号列表
     * @author Danto Huang
     * @created 2018年7月20日 下午5:00:48
     * @param request
     * @return
     */
    @RequestMapping("/toAppointChoose")
    public ModelAndView toAppointChoose(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        Sm4Utils sm4 = new Sm4Utils();
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
          filter.addFilter("Q_t.DATE_TIME_=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.CARD_=", sm4.encryptDataCBC(cardNo));
        }
        filter.addFilter("Q_t.STATUS_=", "1");
        filter.addSorted("t.DATE_TIME", "asc");
        List<Map<String,Object>> appointList = newCallService.findAppointmentBySqlFilter(filter);
        request.setAttribute("appointList", appointList);
        
        return new ModelAndView("callnew/takeNo/appointChooseView"); 
    }

    /**
     * 
     * 描述    根据身份证信息跳转预约取号列表
     * @author Danto Huang
     * @created 2018年7月20日 下午5:00:48
     * @param request
     * @return
     */
    @RequestMapping("/toAppointChooseMacW")
    public ModelAndView toAppointChooseMacW(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        Sm4Utils sm4 = new Sm4Utils();
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
          filter.addFilter("Q_t.DATE_TIME_=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.CARD_=", sm4.encryptDataCBC(cardNo));
        }
        filter.addFilter("Q_t.STATUS_=", "1");
        filter.addFilter("Q_t.IS_TAKE_=", "0");
        filter.addSorted("t.DATE_TIME", "asc");
        List<Map<String,Object>> appointList = newCallService.findAppointmentBySqlFilter(filter);
        /**
         * 身份证字母处理
         */
        if(cardNo!=null&&(appointList==null||appointList.size()==0)) {
            if (cardNo.matches(".*[a-zA-z].*")) {
                filter.addFilter("Q_t.CARD_=", sm4.encryptDataCBC(cardNo.toLowerCase()));
                appointList = newCallService.findAppointmentBySqlFilter(filter);
            }
        }
        request.setAttribute("appointList", appointList);
        
        return new ModelAndView("callnew/takeNo/appointChooseMacWView"); 
    }
    /**
     * 
     * 描述    生成预约取号
     * @author Danto Huang
     * @created 2018年7月20日 下午5:16:09
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/appointmentTakeNo")
    @ResponseBody
    public AjaxJson appointmentTakeNo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> appointment = newCallService.getByJdbc("T_CKBS_APPOINTMENT_APPLY",
                new String[] { "RECORD_ID" }, new Object[] { entityId });
        String businessName = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                new Object[] { appointment.get("BUSINESS_CODE") }).get("BUSINESS_NAME").toString();
        appointment.put("BUSINESS_NAME", businessName);
        
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = new HashMap<String, Object>();
        String businessCode = appointment.get("BUSINESS_CODE") == null ? ""
                : appointment.get("BUSINESS_CODE").toString();
        String lineName = appointment.get("NAME") == null ? "" : appointment.get("NAME").toString();
        String lineCardNo = appointment.get("CARD") == null ? "" : appointment.get("CARD").toString();
        String zjlx = "SF";
        String isVip = "0";
        String isAppoint = "1";
        String appointmentId = appointment.get("RECORD_ID") == null ? "" : appointment.get("RECORD_ID").toString();
        Map<String,Object> lostPromiseMap = newCallService.checkLostPromiseByBusCode(lineName,
                lineCardNo, businessCode);
        Sm4Utils sm4 = new Sm4Utils();
        if(StringUtils.isEmpty(businessCode)||StringUtils.isEmpty(lineCardNo)||lineCardNo.equals("undefined")){
            j.setMsg("无效取号信息！");
            j.setSuccess(false);
        }else if(!newCallService.isWinAccept(businessCode)){
            j.setMsg("您选择的业务暂无对应办事窗口，请前往人工导引台咨询！");
            j.setSuccess(false);
        }else if(newCallService.isWaiting(businessCode, sm4.encryptDataCBC(lineCardNo))){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else if ((boolean)lostPromiseMap.get("isLostPromise")) {
            j.setMsg((String)lostPromiseMap.get("msg"));
            j.setSuccess(false);
        }else{
            variables.put("BUSINESS_CODE", businessCode);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            String roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                        new Object[] { businessCode }).get("BELONG_ROOM").toString();
            variables.put("ROOM_NO", roomNo);
            variables.put("IS_VIP", isVip);
            variables.put("IS_APPOINTMENT", isAppoint);
            variables = newCallService.getLineNo(variables);
            /**
             * 2019年9月10日 10:48:21  更改取号时间为文件服务器时间
             */
            Properties properties = FileUtil.readProperties("project.properties");
            String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
            String url = uploadFileUrlIn + "upload/getFileSystemTime";
            String resultMsg = HttpRequestUtil.sendBdcPost(url, "", "UTF-8");
            String datetime = "";
            if (StringUtils.isNotEmpty(resultMsg)) {
                Map<String, Object> result = JSON.parseObject(resultMsg, Map.class);
                if (null != result && result.size() > 0) {
                    datetime = result.get("data") == null ? "" : result.get("data").toString();
                   
                }
            }
            if (StringUtils.isNotEmpty(datetime)) {
                variables.put("CREATE_TIME", datetime);
            }
            
            String recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", null);
            int waitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
            String careWinNo = newCallService.getCareWin(businessCode);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", variables.get("LINE_NO"));
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            
            //获取办事（取号）用户信息表中的手机号
            String cardNo = sm4.encryptDataCBC(lineCardNo);
            Map<String, Object> lineUser = newCallService.getByJdbc("T_BSFW_LINEUSERS", 
                    new String[]{"LINE_CARDNO"}, new Object[]{cardNo});
            if(lineUser==null){
                //将用户信息更新
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("LINE_NAME", lineName);
                user.put("LINE_CARDNO", lineCardNo);
                newCallService.saveOrUpdate(user, "T_BSFW_LINEUSERS", null);
                line.put("userMobile", "");
            }else if(StringUtils.isEmpty(StringUtil.getString(lineUser.get("LINE_MOBILE")))){
                line.put("userMobile", "");
            }else{//存在用户信息
                String mobile = StringUtil.getString(lineUser.get("LINE_MOBILE"));//手机号中间4位数做隐藏
                String replaceMobile =mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
                line.put("userMobile",replaceMobile);
            }
            
            Map<String,Object> appointmentTemp = new HashMap<String, Object>();
            appointmentTemp.put("NUM_ID", recordId);
            appointmentTemp.put("IS_TAKE", "1");
            if (StringUtils.isNotEmpty(datetime)) {
                appointmentTemp.put("TAKE_TIME", datetime);
            }else{
                appointmentTemp.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            newCallService.saveOrUpdate(appointmentTemp, "T_CKBS_APPOINTMENT_APPLY", appointmentId);
            
            j.setJsonString(JSON.toJSONString(line));
        }
        return j;
    }

    /**
     * 
     * 描述    根据身份证信息跳转查询取号列表
     * @author Danto Huang
     * @created 2018年7月20日 下午5:40:07
     * @param request
     * @return
     */
    @RequestMapping("/toAgainChoose")
    public ModelAndView toAgainChoose(HttpServletRequest request){
        Sm4Utils sm4 = new Sm4Utils();
        SqlFilter filter = new SqlFilter(request);
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.CREATE_TIME_=")==null){
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            time = "%"+time+"%";
            filter.addFilter("Q_t.CREATE_TIME_Like", time);
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.line_cardno_=", sm4.encryptDataCBC(cardNo));
        }
        filter.addSorted("t.CREATE_TIME", "asc");
        List<Map<String,Object>> appointList = newCallService.findAgainDataBySqlFilter(filter);
        request.setAttribute("appointList", appointList);

        return new ModelAndView("callnew/takeNo/againChooseView"); 
    }

    /**
     * 
     * 描述    根据身份证信息跳转查询取号列表
     * @author Danto Huang
     * @created 2018年7月20日 下午5:40:07
     * @param request
     * @return
     */
    @RequestMapping("/toYctbAgainChoose")
    public ModelAndView toYctbAgainChoose(HttpServletRequest request){
        Sm4Utils sm4 = new Sm4Utils();
        SqlFilter filter = new SqlFilter(request);
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.CREATE_TIME_=")==null){
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            time = "%"+time+"%";
            filter.addFilter("Q_t.CREATE_TIME_Like", time);
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.line_cardno_=", sm4.encryptDataCBC(cardNo));
        }
        filter.addSorted("t.CREATE_TIME", "asc");
        List<Map<String,Object>> appointList = newCallService.findAgainDataBySqlFilter(filter);
        request.setAttribute("appointList", appointList);

        return new ModelAndView("callnew/takeNo/againChooseViewYctb"); 
    }
    /**
     * 
     * 描述    根据身份证信息跳转查询取号列表
     * @author Danto Huang
     * @created 2018年7月20日 下午5:40:07
     * @param request
     * @return
     */
    @RequestMapping("/toAgainChooseMacW")
    public ModelAndView toAgainChooseMacW(HttpServletRequest request){
        Sm4Utils sm4 = new Sm4Utils();
        SqlFilter filter = new SqlFilter(request);
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.CREATE_TIME_=")==null){
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            time = "%"+time+"%";
            filter.addFilter("Q_t.CREATE_TIME_Like", time);
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.line_cardno_=", sm4.encryptDataCBC(cardNo));
        }
        filter.addSorted("t.CREATE_TIME", "asc");
        List<Map<String,Object>> appointList = newCallService.findAgainDataBySqlFilter(filter);
        request.setAttribute("appointList", appointList);

        return new ModelAndView("callnew/takeNo/againChooseMacWView"); 
    }
    /**
     * 
     * 描述    生成重新取号
     * @author Danto Huang
     * @created 2018年7月20日 下午5:50:47
     * @param request
     * @return
     */
    @RequestMapping("/againTakeNo")
    @ResponseBody
    public AjaxJson againTakeNo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        Map<String, Object> appointment = newCallService.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                new Object[] { entityId });
        String businessCode = appointment.get("BUSINESS_CODE").toString();
        int waitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
        String careWinNo = newCallService.getCareWin(businessCode);
        String roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                new Object[] { businessCode }).get("BELONG_ROOM").toString();

        Map<String,Object> line = new HashMap<String, Object>();
        line.put("lineNo", appointment.get("LINE_NO"));
        line.put("waitNum", waitNum);
        line.put("winNo", careWinNo);
        line.put("roomNo", roomNo);
        //获取办事（取号）用户信息表中的手机号
        Sm4Utils sm4 = new Sm4Utils();
        String  cardNo = sm4.encryptDataCBC(StringUtil.getString(appointment.get("LINE_CARDNO")));
        Map<String, Object> lineUser = newCallService.getByJdbc("T_BSFW_LINEUSERS", 
                new String[]{"LINE_CARDNO"}, new Object[]{cardNo});
        if(lineUser==null){
            //将用户信息更新
            Map<String, Object> user = new HashMap<String, Object>();
            user.put("LINE_NAME", appointment.get("LINE_NAME"));
            user.put("LINE_CARDNO", appointment.get("LINE_CARDNO"));
            newCallService.saveOrUpdate(user, "T_BSFW_LINEUSERS", null);
            line.put("userMobile", "");
        }else if(StringUtils.isEmpty(StringUtil.getString(lineUser.get("LINE_MOBILE")))){
            line.put("userMobile", "");
        }else{//存在用户信息
            String mobile = StringUtil.getString(lineUser.get("LINE_MOBILE"));//手机号中间4位数做隐藏
            String replaceMobile =mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
            line.put("userMobile",replaceMobile);
        }
        j.setJsonString(JSON.toJSONString(line));
        return j;
    }
    
    /**
     * 
     * 描述    base64图片上传+取号
     * @author Danto Huang
     * @created 2018年7月10日 下午3:23:19
     * @param request
     * @return
     */
    @RequestMapping(params = "videoInputTakeNo")
    @ResponseBody
    public AjaxJson videoInputTakeNo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Map<String,Object> variables = new HashMap<String, Object>();
        String businessCode = request.getParameter("businessCode");
        String lineName = request.getParameter("lineName");
        String roomNo = request.getParameter("roomNo");
        String lineCardNo = request.getParameter("lineCardNo");
        String zjlx = request.getParameter("LINE_ZJLX");
        String isVip = request.getParameter("isVip");
        String isAppoint = request.getParameter("isAppoint");
        
        String fileIds = saveStreamFile(request);
        if(!newCallService.isWinAccept(businessCode)){
            j.setMsg("您选择的业务暂无对应办事窗口，请前往人工导引台咨询！");
            j.setSuccess(false);
        }else if(newCallService.isWaiting(businessCode, lineCardNo)){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else{
            variables.put("BUSINESS_CODE", businessCode);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            if(StringUtils.isEmpty(roomNo)){
                roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                        new Object[] { businessCode }).get("BELONG_ROOM").toString();
            }
            variables.put("ROOM_NO", roomNo);
            if(StringUtils.isNotEmpty(isVip)){
                variables.put("IS_VIP", isVip);
            }
            if(StringUtils.isNotEmpty(isAppoint)){
                variables.put("IS_APPOINTMENT", isAppoint);
            }
            variables = newCallService.getLineNo(variables);
            variables.put("FILEIDS", fileIds);
            variables.put("TAKENOWAY", "2");
            variables.put("TAKE_NO_USER",AppUtil.getLoginUser().getFullname());
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            
            newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", null);
            int waitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
            String careWinNo = newCallService.getCareWin(businessCode);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", variables.get("LINE_NO"));
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            
            j.setJsonString(JSON.toJSONString(line));
        }
        return j;
    }
    /**
     * 
     * 描述    保存 base64图片
     * @author Danto Huang
     * @created 2018年7月10日 下午3:28:32
     * @param request
     * @return
     */
    private String saveStreamFile(HttpServletRequest request){
        String imgStrAll = request.getParameter("base64Code"); 
        String[] jsonArr=imgStrAll.split(";");
        String base64Code = ""; 
//        List<Map<String, Object>> resultlList = new ArrayList<Map<String, Object>>();
        String fileIds = "";
        for (int i = 0; i < jsonArr.length; i++) {
            base64Code = jsonArr[i];
            if(StringUtils.isEmpty(base64Code)){
                continue;
            }
            // 定义上传目录的根路径
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath") ;
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = "attachFiles/image/"+currentDate;
            String uuId = UUIDGenerator.getUUID();
            // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(attachFileFolderPath+uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            uploadFullPath +="/"+uuId+".jpg";
            //把base64文件保存到本地
            FileUtil.decodeBase64File(base64Code, attachFileFolderPath+uploadFullPath);

            String fileName = uuId+".jpg";
            String filePath = uploadFullPath;
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String fileType = FileUtil.getFileExtension(fileName);
            String uploadUserId = request.getParameter("uploadUserId"); 
            String uploadUserName = request.getParameter("uploadUserName"); 
            // 获取业务表名称
            String busTableName = request.getParameter("busTableName");
            // 获取业务表记录ID
            String busTableRecordId = request.getParameter("busRecordId");
            String attachKey = request.getParameter("attachKey");
            String FLOW_EXEID= request.getParameter("FLOW_EXEID");
            String FLOW_TASKID = request.getParameter("FLOW_TASKID");
            String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");
            String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");
            String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");
            String SFHZD = "-1";

            Map<String, Object> fileAttach = new HashMap<String, Object>();
            fileAttach.put("FILE_NAME", fileName);
            fileAttach.put("FILE_PATH", filePath);
            fileAttach.put("CREATE_TIME", createTime);
            fileAttach.put("FILE_TYPE", fileType);
            fileAttach.put("UPLOADER_ID", uploadUserId);
            fileAttach.put("UPLOADER_NAME", uploadUserName);
            fileAttach.put("BUS_TABLENAME", busTableName);
            fileAttach.put("BUS_TABLERECORDID", busTableRecordId);
            fileAttach.put("ATTACH_KEY", attachKey);
            fileAttach.put("FLOW_EXEID", FLOW_EXEID);
            fileAttach.put("FLOW_TASKID", FLOW_TASKID);
            fileAttach.put("FLOW_TASKNAME", FLOW_TASKNAME);
            fileAttach.put("UPLOADER_DEPID", UPLOADER_DEPID);
            fileAttach.put("UPLOADER_DEPNAME", UPLOADER_DEPNAME);
            fileAttach.put("SFHZD", SFHZD);
            fileAttach.put("IS_IMG","1");
            String fileId = fileAttachService
                    .saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
            fileIds=fileIds+fileId+";";
        }
        return fileIds;
    }
    
    /**
     * 
     * 描述    跳转取号管理
     * @author Danto Huang
     * @created 2018年7月5日 下午4:25:41
     * @param request
     * @return
     */
    @RequestMapping(params="queueRecordView")
    public ModelAndView queueRecordView(HttpServletRequest request){
        return new ModelAndView("callnew/takeNo/queueRecordView");
    }
    
    /**
     * 
     * 描述    AJAX请求数据(管理员取号)
     * @author Danto Huang
     * @created 2018年7月5日 下午4:33:29
     * @param request
     * @param response
     */
    @RequestMapping(params="queueRecordGrid")
    public void queueRecordGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.IS_VIP", "desc");
        filter.addSorted("t.IS_TOP", "desc");
        filter.addSorted("t.TOP_TIME", "desc");
        filter.addSorted("t.CREATE_TIME", "asc");
        if(!filter.getQueryParams().containsKey("Q_T.CALL_STATUS_=")){
            filter.addFilter("Q_T.CALL_STATUS_=", "0");
        }
        
        List<Map<String, Object>> list = newCallService.findQueueListBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }

    
    /**
     * 
     * 描述    AJAX请求数据(排队数据列表)
     * @author Danto Huang
     * @created 2018年7月5日 下午4:33:29
     * @param request
     * @param response
     */
    @RequestMapping("/waitingGrid")
    public void waitingGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.CALL_STATUS_=", "0");
        String businessCode = request.getParameter("businessCode");
        if(businessCode!=null){
            filter.addFilter("Q_T.BUSINESS_CODE_=", businessCode);
        }

        List<Map<String, Object>> list = newCallService.findQueueListBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    /**
     *
     * 描述    AJAX请求数据(排队数据列表)
     * @author Danto Huang
     * @created 2018年7月5日 下午4:33:29
     * @param request
     * @param response
     */
    @RequestMapping("/waitingNumOfRoomNum")
    public void waitingNumOfRoomNum(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.CALL_STATUS_=", "0");
        String roomNo = request.getParameter("roomNo");
        this.setListToJsonString(newCallService.getWaitingNumByRoomNo(roomNo), null,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述    管理员取号
     * @author Danto Huang
     * @created 2018年7月5日 下午4:48:33
     * @param request
     * @return
     */
    @RequestMapping(params="assistTakeInfo")
    public ModelAndView assistTakeInfo(HttpServletRequest request){
        return new ModelAndView("callnew/takeNo/assistTakeInfo");
    }
    
    /**
     * 
     * 描述    跳转置顶
     * @author Danto Huang
     * @created 2018年7月5日 下午5:27:19
     * @param request
     * @return
     */
    @RequestMapping(params="toTopReason")
    public ModelAndView toTopReason(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        request.setAttribute("entityId", entityId);
        return new ModelAndView("callnew/takeNo/toTopReason");
    }
    
    /**
     * 
     * 描述    置顶
     * @author Danto Huang
     * @created 2018年7月5日 下午5:29:21
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="toTop")
    @ResponseBody
    public AjaxJson toTop(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("entityId");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("IS_TOP", "1");
        variables.put("TOP_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
        j.setMsg("置顶成功");
        return j;
    }
    
    /**
     * 
     * 描述    取消置顶
     * @author Danto Huang
     * @created 2018年7月5日 下午5:26:11
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="cancelTop")
    @ResponseBody
    public AjaxJson cancelTop(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("entityId");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("IS_TOP", "0");
        variables.put("TOP_TIME", "");
        variables.put("TOTOPREASON", "");
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    进入叫号页面
     * @author Danto Huang
     * @created 2018年7月6日 上午10:22:42
     * @param request
     * @return
     */
    @RequestMapping(params="queuingView")
    public ModelAndView queuingView(HttpServletRequest request){
        return new ModelAndView("callnew/queuing/queuingtab");
    }    

    /**
     * 
     * 描述    进入叫号页面(一窗通办)
     * @author Danto Huang
     * @created 2018年7月6日 上午10:22:42
     * @param request
     * @return
     */
    @RequestMapping(params="yctbQueuingView")
    public ModelAndView yctbQueuingView(HttpServletRequest request){
        return new ModelAndView("callnew/yctbQueuing/queuingtab");
    }
    
    /**
     * 
     * 描述    窗口选择
     * @author Danto Huang
     * @created 2018年7月6日 上午10:49:40
     * @param request
     * @return
     */
    @RequestMapping(params="winSelect")
    public ModelAndView winSelect(HttpServletRequest request){
        return new ModelAndView("callnew/queuing/winSelect");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年7月6日 上午11:05:01
     * @param request
     * @param response
     */
    @RequestMapping(params="winSelectDatagrid")
    public void winSelectDatagrid(HttpServletRequest request,HttpServletResponse response){
        List<Map<String, Object>> list = newCallService.findUserWin();
        if(list!=null&&list.size()>0){
            for(Map<String,Object> win : list){
                if(win.get("CUR_USERID")!=null){
                    String userId = win.get("CUR_USERID").toString();
                    if(StringUtils.isNotEmpty(userId)){
                        Map<String, Object> user = newCallService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                                new String[] { "USER_ID" }, new Object[] { userId });

                        win.put("CUR_USERNAME", user.get("FULLNAME"));
//                        if(!AuthInterceptor.sessionMap.containsKey(user.get("USERNAME"))){
//                            win.put("CUR_USERID", "");
//                            newCallService.saveOrUpdate(win, "T_CKBS_WIN_USER", win.get("RECORD_ID").toString());
//                        }else{
//                            win.put("CUR_USERNAME", user.get("FULLNAME"));
//                        }
                    }
                }
            }
        }
        this.setListToJsonString(0, list,null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述    窗口占用更新
     * @author Danto Huang
     * @created 2018年7月13日 下午3:33:27
     * @param request
     * @return
     */
    @RequestMapping(params="selectWinInfo")
    @ResponseBody
    public AjaxJson selectWinInfo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        String userId = AppUtil.getLoginUser().getUserId();
        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("CUR_USERID", userId);
        variables.put("RECORD_ID", recordId);
        variables.put("COUNTERSTATUS", "1");
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
        if ("开".equals(switchTurn)) {
            pushDataToSJJXXZXService.pushDateToSJJXXZX(variables, "addCounterNowInfo");
        }
        newCallService.saveOrUpdate(variables, "T_CKBS_WIN_USER", recordId);
        String isContinue = newCallService
                .getByJdbc("T_CKBS_WIN_USER", new String[] { "RECORD_ID" }, new Object[] { recordId })
                .get("IS_CONTINUE").toString();
        j.setJsonString(isContinue);
        return j;
    }

    /**
     * 
     * 描述    关闭叫号页面清除窗口占用
     * @author Danto Huang
     * @created 2018年7月13日 下午4:10:28
     * @param request
     * @return
     */
    @RequestMapping(params="clearWinSelect")
    @ResponseBody
    public AjaxJson clearWinSelect(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String winNo = request.getParameter("winNo");
        String entityId = newCallService
                .getByJdbc("T_CKBS_WIN_USER", new String[] { "WIN_NO" },
                        new Object[] { winNo }).get("RECORD_ID").toString();
        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("CUR_USERID", "");
        newCallService.saveOrUpdate(variables, "T_CKBS_WIN_USER", entityId);
        return j;
    }
    
    /**
     * 
     * 描述    AJAX请求数据(窗口叫号列表)
     * @author Danto Huang
     * @created 2018年7月6日 下午4:41:16
     * @param request
     * @param response
     */
    @RequestMapping(params="queuingGrid")
    public void queuingGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        String business = request.getParameter("business");
        String winNo = request.getParameter("winNo");
        if (StringUtils.isNotEmpty(winNo)) {
            filter.addFilter("Q_T.CUR_WIN_=", winNo);
            Map<String, Object> winUserInfo = newCallService.getByJdbc("T_CKBS_WIN_USER",
                    new String[]{"WIN_NO"}, new Object[]{winNo});
            ArrayList<String> notinBusinessCodes = new ArrayList<>();
            String priorBusinessCodes = (String) winUserInfo.get("PRIOR_BUSINESS_CODES");
            String winBusinessCodes = (String) winUserInfo.get("WIN_BUSINESS_CODES");
            if (priorBusinessCodes != null) {
                /*获取所有业务编码，找到not in*/
                String[] winBusinessCodesArr = winBusinessCodes.split("\\,");
                for(String businessCode : winBusinessCodesArr) {
                    if (!priorBusinessCodes.contains(businessCode)) {
                        notinBusinessCodes.add(businessCode);
                    }
                }
                String notinStr = String.join(",", notinBusinessCodes);
                filter.addFilter("Q_T.BUSINESS_CODE_NOTIN", notinStr);
            }
        }
        if(StringUtils.isNotEmpty(business)){
            filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
        }else{
            if(!username.equals("admin")){
                filter.addFilter("Q_T.BUSINESS_CODE_=", "none");
            }
        }
        filter.addSorted("currentCall", "desc");
        filter.addSorted("T.IS_VIP", "desc");
        filter.addSorted("T.IS_TOP", "desc");
        filter.addSorted("T.TOP_TIME", "desc");
        filter.addSorted("T.IS_FORWARD", "desc");
        filter.addSorted("appointcall", "desc");
        filter.addSorted("T.IS_APPOINTMENT", "desc");
        filter.addSorted("T.CREATE_TIME", "asc");
        filter.addSorted("T.TAKE_SN", "asc");
        List<Map<String, Object>> list = newCallService.findQueuingBySqlFilter(filter);
        if (list.size()==0) {
            //单双号窗口优先业务没有的时候叫其他业务
            filter.addFilter("Q_T.CUR_WIN_=", winNo);
            if(StringUtils.isNotEmpty(business)){
                filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
            }else{
                if(!username.equals("admin")){
                    filter.addFilter("Q_T.BUSINESS_CODE_=", "none");
                }
            }
            //单双号窗口优先业务没有的时候叫其他业务
            list = newCallService.findQueuingBySqlFilter(filter);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    叫号
     * @author Danto Huang
     * @created 2018年7月6日 下午6:01:38
     * @param request
     * @return
     */
    @RequestMapping(params="callQueuing")
    @ResponseBody
    public AjaxJson callQueuing(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String type = request.getParameter("type");
        String winNo = request.getParameter("winNo");
        Map<String,Object> variables = new HashMap<String, Object>();
        String voice = "";
        Map<String,Object> winScreen = newCallService
                .getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "WIN_NO" },
                        new Object[] { winNo });
        String ledinfo = winScreen.get("DEPARTINFO").toString();
        String wordNum = winScreen.get("WORD_NUM").toString();
        boolean callSucc = true;
        if(type.equals("callNum")){
            String recordId = request.getParameter("recordId");
            Map<String,Object> callrecord = newCallService
                    .getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" }, new Object[] { recordId });
            String callStatus = callrecord.get("CALL_STATUS").toString();
            //
            String sql = "select decode(min(t.take_sn),null,0,min(t.take_sn)) "
                    + "from T_CKBS_QUEUERECORD t where t.business_code=? and t.call_status=0 and t.is_appointment=0";
            String businessCode = callrecord.get("BUSINESS_CODE").toString();
            Integer minSn = Integer.valueOf(newCallService.getObjectBySql(sql, new Object[]{businessCode}).toString());
            String cruWin = "";
            if(callrecord.get("CUR_WIN")!=null){
                cruWin = callrecord.get("CUR_WIN").toString();
            }
            if(callStatus.equals("6")&&!cruWin.equals(winNo)){
                voice = "此号已在其他窗口叫号办理中， 请刷新等待列表重新叫号";
                callSucc = false;
            } else if (minSn < Integer.parseInt(callrecord.get("TAKE_SN").toString())&& minSn>0
                    && !callrecord.get("IS_VIP").equals("1") && !callrecord.get("IS_TOP").equals("1")
                    && !callrecord.get("IS_APPOINTMENT").equals("1")&& !callrecord.get("IS_FORWARD").equals("1")) {
                voice = "此号之前还有等待中排队号， 请刷新等待列表重新叫号";
                callSucc = false;
            }else{
                String lineNo = request.getParameter("lineNo");
                Map<String,Object> call = new HashMap<String, Object>();
                call.put("CALL_STATUS", "6");
                call.put("CUR_WIN", winNo);
                call.put("OPERATOR_NAME", AppUtil.getLoginUser().getFullname());
                call.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
                if("115".equals(winNo)){//更改115号窗口叫号播报信息
                    voice = "请"+lineNo+"号，到台胞接待室";
                }else{
                    voice = "请"+lineNo+"号，到"+winNo+"号窗口";
                }
                if(wordNum.equals("8")){
                    ledinfo = ledinfo+"#   请"+lineNo+"号";
                }else if(wordNum.equals("12")){
                    ledinfo = ledinfo+"#       请"+lineNo+"号";
                }
                variables.put("RECORD_ID", recordId);
                variables.put("LINE_NO", lineNo);
                variables.put("WIN_NO", winNo);
                variables.put("VOICE", voice);
                variables.put("LEDINFO", ledinfo);
                setCallingTime(call);
                newCallService.saveOrUpdate(call, "T_CKBS_QUEUERECORD", recordId);
            }
        }else if(type.equals("CallingWait")){
            if(wordNum.equals("8")){
                ledinfo = ledinfo+"#     暂停服务";
            }else if(wordNum.equals("12")){
                ledinfo = ledinfo+"#         暂停服务";
            }
            voice = "暂停服务";
            variables.put("WIN_NO", winNo);
            variables.put("LEDINFO", ledinfo); 
            variables.put("VOICE", voice);     
        }else if(type.equals("CallingService")){
            if(wordNum.equals("8")){
                ledinfo = ledinfo+"#     欢迎您";
            }else if(wordNum.equals("12")){
                ledinfo = ledinfo+"#         欢迎您";
            }
            voice = "欢迎您";
            variables.put("WIN_NO", winNo);
            variables.put("LEDINFO", ledinfo);  
            variables.put("VOICE", voice);         
        }
        if(callSucc){
            String roomNo = winScreen.get("BELONG_ROOM").toString();
            variables.put("ROOM_NO", roomNo);
            variables.put("TYPE", type);  
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("OPERATOR", AppUtil.getLoginUser().getFullname());
            newCallService.saveOrUpdate(variables, "T_CKBS_CALLWAIT", null);
        }
        //推送至省经济中心取号实时记录
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
        if ("开".equals(switchTurn)) {
            if(StringUtil.isNotEmpty(variables)){
                switch (type) {
                    case "callNum":
                        pushDataToSJJXXZXService.pushDateToSJJXXZX(variables, "addQueueCallInfo");
                        break;
                    case "CallingWait":
                        variables.put("RECORD_ID", winScreen.get("RECORD_ID"));
                        variables.put("DEPARTINFO", winScreen.get("DEPARTINFO"));
                        variables.put("COUNTERSTATUS", "2");
                        pushDataToSJJXXZXService.pushDateToSJJXXZX(variables, "addCounterNowInfoStop");
                        break;
                    default:
                        break;
                }
                //pushDateToSJJXXZX(variables,"addQueueCallInfo");
            }
        }
        j.setMsg(voice);
        return j;
    }

    /**
     * 设置呼叫时间
     * @param call
     */
    private void setCallingTime(Map<String,Object> call){
        String callingTime=call.get("CALLING_TIME")==null?"":
                call.get("CALLING_TIME").toString();
        if(StringUtils.isEmpty(callingTime)){
            call.put("CALLING_TIME",DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
    }
    /**
     * 
     * 描述    企业设立登记事项绑定
     * @author Danto Huang
     * @created 2018年7月6日 下午6:25:25
     * @param request
     * @return
     */
    @RequestMapping(params="setCompany")
    public ModelAndView setCompany(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("call/queuing/setCompany");
    }
    
    /**
     * 
     * 描述：
     * @author Rider Chen
     * @created 2020年8月19日 下午4:05:16
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="bdcWaitAccept")
    public ModelAndView bdcWaitAccept(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("callnew/queuing/bdcWaitAcceptSelector");
    }

    /**
     * 
     * 描述    不动产登记
     * @author Danto Huang
     * @created 2018年7月6日 下午6:27:52
     * @param request
     * @return
     */
    @RequestMapping(params="bdcAccept")
    @ResponseBody
    public AjaxJson bdcAccept(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CALL_STATUS", "1");
        variables.put("OPERATOR_NAME", AppUtil.getLoginUser().getFullname());
        variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
        variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    跳转过号原因
     * @author Danto Huang
     * @created 2018年7月16日 上午8:57:16
     * @param request
     * @return
     */
    @RequestMapping(params="passSeason")
    public ModelAndView passSeason(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        request.setAttribute("recordId", recordId);
        return new ModelAndView("callnew/queuing/passReason");
    }
    
    /**
     * 
     * 描述    过号
     * @author Danto Huang
     * @created 2018年7月9日 上午8:57:38
     * @param request
     * @return
     */
    @RequestMapping(params="queuingPass")
    @ResponseBody
    public AjaxJson queuingPass(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("CALL_STATUS", "2");
        variables.put("OPERATOR_NAME", AppUtil.getLoginUser().getFullname());
        variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
        variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    咨询
     * @author Danto Huang
     * @created 2018年7月9日 上午9:06:40
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingDealzx")
    public ModelAndView goQueuingDealzx(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("callnew/queuing/queuingDealZX");
    }
    
    /**
     * 
     * 描述    领照
     * @author Danto Huang
     * @created 2018年7月9日 上午9:09:09
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingDeallz")
    public ModelAndView goQueuingDeallz(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("callnew/queuing/queuingDealLZ");
    }
    
    /**
     * 
     * 描述    处理信息保存
     * @author Danto Huang
     * @created 2018年7月9日 上午9:10:50
     * @param request
     * @return
     */
    @RequestMapping(params="queuingDeal")
    @ResponseBody
    public AjaxJson queuingDeal(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("OPERATOR_NAME", AppUtil.getLoginUser().getFullname());
        variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
        variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    跳转转发叫号记录
     * @author Danto Huang
     * @created 2018年7月9日 下午2:39:41
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingForward")
    public ModelAndView goQueuingForward(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("recordId", recordId);
        request.setAttribute("businessCode", record.get("BUSINESS_CODE"));
        return new ModelAndView("callnew/queuing/queuingForward");
    }

    /**
     * 
     * 描述    跳转转发叫号记录
     * @author Danto Huang
     * @created 2018年7月9日 下午2:39:41
     * @param request
     * @return
     */
    @RequestMapping(params="goYctbQueuingForward")
    public ModelAndView goYctbQueuingForward(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("recordId", recordId);
        request.setAttribute("businessCode", record.get("BUSINESS_CODE"));
        return new ModelAndView("callnew/queuing/yctbQueuingForward");
    }
    /**
     * 
     * 描述    转发结果
     * @author Danto Huang
     * @created 2018年7月9日 下午2:53:53
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="queuingForward")
    @ResponseBody
    public AjaxJson queuingForward(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("RECORD_ID");
        String toWin = request.getParameter("TO_WIN");
        Map<String, Object> forward = BeanUtil.getMapFromRequest(request);
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                new Object[] { recordId });
        forward.put("LINE_NO", record.get("LINE_NO"));
        forward.put("FROM_WIN", record.get("CUR_WIN"));
        forward.put("FROM_USER", record.get("OPERATOR_ID"));
        newCallService.saveOrUpdate(forward, "T_CKBS_FORWARD_LOG", null);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("IS_FORWARD", "1");
        variables.put("CUR_WIN", toWin);
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    当天已办理记录
     * @author Danto Huang
     * @created 2018年7月9日 上午9:28:01
     * @param request
     * @param response
     */
    @RequestMapping(params="queuingDoneDayGrid")
    public void queuingDoneDayGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        String doneWinNo = request.getParameter("doneWinNo");
        filter.addFilter("Q_T.OPERATOR_ID_=", AppUtil.getLoginUser().getUserId());
        if(StringUtils.isNotEmpty(doneWinNo)){
            filter.addFilter("Q_T.CUR_WIN_=", doneWinNo);
        }else{
            filter.addFilter("Q_T.CUR_WIN_=", "none");
        }
        filter.addSorted("DECODE(T.OPER_TIME,NULL,0,1)", "DESC");
        filter.addSorted("T.OPER_TIME", "DESC");
        filter.addSorted("T.CALLING_TIME", "DESC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String, Object>> list = newCallService.findQueuingDayDoneBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    跳转已办理记录
     * @author Danto Huang
     * @created 2018年7月9日 下午4:22:48
     * @param request
     * @return
     */
    @RequestMapping(params="queuingDoneView")
    public ModelAndView queuingDoneView(HttpServletRequest request){
        return new ModelAndView("callnew/queuing/queuingDoneView");
    }
    
    /**
     * 
     * 描述    已办理记录数据列表
     * @author Danto Huang
     * @created 2018年7月9日 下午4:24:49
     * @param request
     * @param response
     */
    @RequestMapping(params="queuingDoneGrid")
    public void queuingDoneGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        /*if(!AppUtil.getLoginUser().getUsername().equals("admin")){
            filter.addFilter("Q_T.OPERATOR_ID_=", AppUtil.getLoginUser().getUserId());
        }*/
        filter.addSorted("DECODE(T.OPER_TIME,NULL,0,1)", "DESC");
        filter.addSorted("T.OPER_TIME", "DESC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String, Object>> list = newCallService.findQueuingDoneBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    跳转转发记录
     * @author Danto Huang
     * @created 2018年7月9日 下午5:35:46
     * @param request
     * @return
     */
    @RequestMapping(params="queuingForwardView")
    public ModelAndView queuingForwardView(HttpServletRequest request){
        return new ModelAndView("callnew/queuing/queuingForwardView");
    }
    
    /**
     * 
     * 描述    转发记录列表
     * @author Danto Huang
     * @created 2018年7月9日 下午5:36:54
     * @param request
     * @param response
     */
    @RequestMapping(params="queuingForwardGrid")
    public void queuingForwardGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        /*if(!AppUtil.getLoginUser().getUsername().equals("admin")){
            filter.addFilter("Q_T.FROM_USER_=", AppUtil.getLoginUser().getUserId());
        }*/
        List<Map<String, Object>> list = newCallService.findQueuingFowardBySqlFilter(filter);
        
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述    跳转评价
     * @author Danto Huang
     * @created 2018年7月10日 下午4:18:13
     * @param request
     * @return
     */
    @RequestMapping(params="evaluateInfo")
    public ModelAndView evaluateInfo(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        request.setAttribute("recordId", recordId);
        return new ModelAndView("callnew/queuing/evaluate");
    }
    
    /**
     * 
     * 描述    评价结果
     * @author Danto Huang
     * @created 2018年7月10日 下午4:19:23
     * @param request
     * @return
     */
    @RequestMapping(params="evaluate")
    @ResponseBody
    public AjaxJson evaluate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        String UserJson = request.getParameter("UserJson")== null ? "" : request.getParameter("UserJson").toString();
        String evaLevel = request.getParameter("eval")== null ? "" : request.getParameter("eval").toString();
        String evaLeveln = request.getParameter("evaln")== null ? "" : request.getParameter("evaln").toString();
        String strInfo = request.getParameter("strInfo")== null ? "" : request.getParameter("strInfo").toString();
        if(StringUtils.isNotEmpty(evaLevel)) {//按键器评价方式
            Map<String, Object> variables = new HashMap<String, Object>();
            String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            variables.put("EVALUATE", evaLevel);
            variables.put("EVALUATETIME", evaluateTime);
            variables.put("EVALUATETYPE", "2");
            newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
            /*如果是差评*/
            dealNagetiveEvaluate(recordId, evaLevel, evaluateTime);
        }
        if (StringUtils.isNotEmpty(evaLeveln)) {//液晶评价器2
            Map<String, Object> variables = new HashMap<String, Object>();
            String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String flag = evaLeveln;
            String evals = "4";
            if ("5".equals(evaLeveln)) {
                evals = "3";
            } else if("4".equals(evaLeveln)){
                evals = "2";
            }else if("3".equals(evaLeveln)){
                evals = "1";
            }else if("2".equals(evaLeveln)){
                evals = "0";
            }else if("1".equals(evaLeveln)){
                evals = "5";
            }
            variables.put("EVALUATE", evals);
            variables.put("EVALUATETIME", evaluateTime);
            variables.put("EVALUATETYPE", "1");
            newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);

            //保存好差评明细信息
            Map<String,Object> info = new HashMap<String, Object>();
            info.put("RECORD_ID", recordId);
            info.put("EVA_LEVEL", flag);
            info.put("EVA_INFO", strInfo);
            info.put("EVALUATETIME", evaluateTime);
            newCallService.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", null);
            /*如果是差评*/
            dealNagetiveEvaluate(recordId, evals, evaluateTime);
        }
        if(StringUtils.isNotEmpty(UserJson)) {//平板评价方式
            Map<String,Object> obj = (Map<String, Object>) JSON.parse(UserJson);
            String eval = String.valueOf(obj.get("EvaluatorLevel"));
            if("5".equals(eval)){
                eval = "3";
            }else if("4".equals(eval)){
                eval = "2";
            }else if("3".equals(eval)){
                eval = "1";
            }else if("2".equals(eval)){
                eval = "0";
            }else if("1".equals(eval)){
                eval = "5";
            }
            Map<String, Object> variables = new HashMap<String, Object>();
            String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            variables.put("EVALUATE", eval);
            variables.put("EVALUATETIME", evaluateTime);
            variables.put("EVALUATETYPE", "1");
            newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", recordId);
            
            //保存好差评明细信息
            Map<String,Object> info = new HashMap<String, Object>();
            info.put("RECORD_ID", recordId);
            info.put("EVA_LEVEL", obj.get("EvaluatorLevel"));
            info.put("EVA_INFO", obj.get("Info"));
            info.put("EVALUATETIME", evaluateTime);
            newCallService.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", null);
            /*如果是差评*/
            dealNagetiveEvaluate(recordId, eval, evaluateTime);
        }
        j.setMsg("完成评价");
        return j;
    }

    /**
     * 描述:差评处理
     *
     * @author Madison You
     * @created 2020/11/23 16:30:00
     * @param
     * @return
     */
    private void dealNagetiveEvaluate(String recordId , String eval , String evaluateTime) {
        if (Objects.equals(eval, "0") || Objects.equals(eval,"5")) {
            /*差评数据即时发送短信提醒*/
            try{
                newCallService.sendSmsNagetiveEvaluate(recordId, eval, evaluateTime);
            } catch (Exception e) {
                log.error("差评数据即时发送短信提醒错误，recordId：" + recordId, e);
            }
        }
    }
    
    /**
     * 
     * 描述    保存一次性告知单打印记录
     * @author Danto Huang
     * @created 2018年9月10日 下午5:41:30
     * @param request
     */
    @RequestMapping(params="savePrintRecord")
    public AjaxJson savePrintRecord(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        String itemCode = request.getParameter("itemCode");
        String OPER_REMARK_SQR = request.getParameter("OPER_REMARK_SQR");
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        Map<String,Object> print = new HashMap<String, Object>();
        print.put("RECORD_ID", recordId);
        print.put("LINE_NO", record.get("LINE_NO"));
        print.put("OPERATOR_ID", record.get("OPERATOR_ID"));
        print.put("WIN_NO", record.get("CUR_WIN"));
        print.put("WIN_BUSINESS", record.get("BUSINESS_CODE"));
        print.put("ITEM_CODE", itemCode);
        print.put("OPER_REMARK_SQR", OPER_REMARK_SQR);
        newCallService.saveOrUpdate(print, "T_CKBS_POSABLE_PRINT", null);
        return j;
    }
    
    /**
     * 
     * 描述    一次性告知单打印记录
     * @author Danto Huang
     * @created 2018年9月10日 下午6:43:01
     * @param request
     * @return
     */
    @RequestMapping(params="printRecordView")
    public ModelAndView printRecordView(HttpServletRequest request){
        return new ModelAndView("callnew/queuing/printRecordView");
    }
    /**
     * 
     * 描述     一次性告知单打印记录列表
     * @author Danto Huang
     * @created 2018年9月10日 下午6:43:40
     * @param request
     * @param response
     */
    @RequestMapping(params="printRecordGrid")
    public void printRecordGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = newCallService.findPrintRcordBySqlFilter(filter);
        
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月18日 上午9:12:39
     * @param request
     * @return
     */
    @RequestMapping(params="mztIdentify")
    @ResponseBody
    public AjaxJson mztIdentify(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String idCard = request.getParameter("idCard");
        String name = request.getParameter("name");
        String faceImg = request.getParameter("faceImg");
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String mztidentifyUrl = properties.getProperty("mztidentifyUrl");
        String authCode = properties.getProperty("authcode");
        try {
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("INVOKESERVICE_CODE","050");
            paramsMap.put("INVOKECALLER_CODE",authCode);
            paramsMap.put("CITIZEN_ID_NUMBER",idCard);
            paramsMap.put("FULL_NAME",name);
            paramsMap.put("FACE_IMG",faceImg);
            String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
            Map<String,Object> clientParam = new HashMap<String,Object>();
            clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);
            String resultJson = sendPostParams(mztidentifyUrl,clientParam);
            Map<String,Object> result = (Map<String, Object>) JSON.parse(resultJson);
            if((Boolean) result.get("success")){
                j.setSuccess(true);
                j.setMsg("身份验证通过");
            }else{
                j.setSuccess(false);
                j.setMsg("身份验证不通过，认证平台返回消息："+result.get("msg"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return j;
    }

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月18日 上午9:41:15
     * @param url
     * @param params
     * @return
     */
    public static String sendPostParams(String url,Map<String,Object> params){
        DefaultHttpClient httpclient = new DefaultHttpClient(
                new ThreadSafeClientConnManager());
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String,Object> entry = (Entry<String, Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(),
                        (String) entry.getValue())); 
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            String result =  httpclient.execute(httpost,responseHandler).toString();
            httpclient.getConnectionManager().shutdown();
            return result;
        } catch (ClientProtocolException e) {
            log.error("",e);
        } catch (IOException e) {
            log.error("",e);
        }
        return null;
    }
    /**
     * 
     * 描述    AJAX请求数据(窗口叫号列表)
     * @author Danto Huang
     * @created 2018年7月6日 下午4:41:16
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="yctbQueuingGrid1")
    public void yctbQueuingGrid1(HttpServletRequest request,HttpServletResponse response){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String waitingMax = properties.getProperty("waitingMax");
        
        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        String business = request.getParameter("business");
        String winNo = request.getParameter("winNo");
        
        if(StringUtils.isNotEmpty(winNo)){
            filter.addFilter("Q_T.CUR_WIN_=", winNo);
            if (winNo.equals("01")
                    ||winNo.equals("03")
                    ||winNo.equals("05")
                    ||winNo.equals("07")
                    ||winNo.equals("09")
                    ||winNo.equals("11")) {
                //单数窗口优先叫AB业务     请注意是否影响该部分
                filter.addFilter("Q_T.BUSINESS_CODE_IN", 
                        "A,B");
            }else if (winNo.equals("02")
                    ||winNo.equals("04")
                    ||winNo.equals("06")
                    ||winNo.equals("08")
                    ||winNo.equals("10")
                    ||winNo.equals("12")) {
                //双号窗口优先叫其他业务
                filter.addFilter("Q_T.BUSINESS_CODE_IN", "C,D,E,F,G,H,K,O,P,W,X,Y,Z,J,Q");
            }
        }        
        filter.addSorted("currentCall", "desc");
        filter.addSorted("T.IS_VIP", "desc");
        filter.addSorted("T.IS_TOP", "desc");
        filter.addSorted("T.TOP_TIME", "desc");
        filter.addSorted("T.IS_FORWARD", "desc");
        filter.addSorted("appointcall", "desc");
        filter.addSorted("T.IS_APPOINTMENT", "desc");
        filter.addSorted("T.CREATE_TIME", "asc");
        List<Map<String, Object>> list = newCallService.findYctbQueuingBySqlFilter(filter);
        if (list.size()==0) {
            //单双号窗口优先业务没有的时候叫其他业务
            if(StringUtils.isNotEmpty(business)){
                int waiting = newCallService.getTotalQueuingByWinBusiness(winNo, business);
                if(waiting>Integer.parseInt(waitingMax)){
                    Map<String, Object> winInfo = newCallService.getByJdbc("T_CKBS_WIN_USER", new String[] { "WIN_NO" },
                            new Object[] { winNo });
                    if(winInfo.get("WARN_BUSINESS_CODES")!=null){
                        filter.addFilter("Q_T.BUSINESS_CODE_IN", winInfo.get("WARN_BUSINESS_CODES").toString());
                    }else{
                        filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
                    }
                }else{
                    filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
                }
            }else{
                if(!username.equals("admin")){
                    filter.addFilter("Q_T.BUSINESS_CODE_=", "none");
                }
            }
            list = newCallService.findYctbQueuingBySqlFilter(filter);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述    AJAX请求数据(窗口叫号列表)
     * @author Danto Huang
     * @created 2018年7月6日 下午4:41:16
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="yctbQueuingGrid")
    public void yctbQueuingGrid(HttpServletRequest request,HttpServletResponse response){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String waitingMax = properties.getProperty("waitingMax");
        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        String business = request.getParameter("business");
        String winNo = request.getParameter("winNo");
        if(StringUtils.isNotEmpty(winNo)){
            filter.addFilter("Q_T.CUR_WIN_=", winNo);
            Map<String, Object> winUserInfo = newCallService.getByJdbc("T_CKBS_WIN_USER",
                    new String[]{"WIN_NO"}, new Object[]{winNo});
            ArrayList<String> notinBusinessCodes = new ArrayList<>();
            String priorBusinessCodes = (String) winUserInfo.get("PRIOR_BUSINESS_CODES");
            String winBusinessCodes = (String) winUserInfo.get("WIN_BUSINESS_CODES");
            if (priorBusinessCodes != null) {
                /*获取所有业务编码，找到not in*/
                String[] winBusinessCodesArr = winBusinessCodes.split("\\,");
                for(String businessCode : winBusinessCodesArr) {
                    if (!priorBusinessCodes.contains(businessCode)) {
                        notinBusinessCodes.add(businessCode);
                    }
                }
                String notinStr = String.join(",", notinBusinessCodes);
                filter.addFilter("Q_T.BUSINESS_CODE_NOTIN", notinStr);
            }
        }
        if(StringUtils.isNotEmpty(business)){
            int waiting = newCallService.getTotalQueuingByWinBusiness(winNo, business);
            if(waiting>Integer.parseInt(waitingMax)){
                Map<String, Object> winInfo = newCallService.getByJdbc("T_CKBS_WIN_USER", new String[] { "WIN_NO" },
                        new Object[] { winNo });
                if(winInfo.get("WARN_BUSINESS_CODES")!=null){
                    filter.addFilter("Q_T.BUSINESS_CODE_IN", winInfo.get("WARN_BUSINESS_CODES").toString());
                }else{
                    filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
                }
            }else{
                filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
            }
        }else{
            if(!username.equals("admin")){
                filter.addFilter("Q_T.BUSINESS_CODE_=", "none");
            }
        }
        filter.addSorted("currentCall", "desc");
        filter.addSorted("T.IS_VIP", "desc");
        filter.addSorted("T.IS_TOP", "desc");
        filter.addSorted("T.TOP_TIME", "desc");
        filter.addSorted("T.IS_FORWARD", "desc");
        filter.addSorted("appointcall", "desc");
        filter.addSorted("T.IS_APPOINTMENT", "desc");
        filter.addSorted("T.CREATE_TIME", "asc");
        filter.addSorted("T.TAKE_SN", "asc");
        List<Map<String, Object>> list = newCallService.findYctbQueuingBySqlFilter(filter);
        if (list.size()==0) {
            //单双号窗口优先业务没有的时候叫其他业务
            filter.addFilter("Q_T.CUR_WIN_=", winNo);
            if(StringUtils.isNotEmpty(business)){
                int waiting = newCallService.getTotalQueuingByWinBusiness(winNo, business);
                if(waiting>Integer.parseInt(waitingMax)){
                    Map<String, Object> winInfo = newCallService.getByJdbc("T_CKBS_WIN_USER", new String[] { "WIN_NO" },
                            new Object[] { winNo });
                    if(winInfo.get("WARN_BUSINESS_CODES")!=null){
                        filter.addFilter("Q_T.BUSINESS_CODE_IN", winInfo.get("WARN_BUSINESS_CODES").toString());
                    }else{
                        filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
                    }
                }else{
                    filter.addFilter("Q_T.BUSINESS_CODE_IN", business);
                }
            }else{
                if(!username.equals("admin")){
                    filter.addFilter("Q_T.BUSINESS_CODE_=", "none");
                }
            }
            //单双号窗口优先业务没有的时候叫其他业务
            list = newCallService.findYctbQueuingBySqlFilter(filter);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述    跳转当前叫号待办理事项列表
     * @author Danto Huang
     * @created 2019年3月13日 下午2:57:37
     * @param request
     * @return
     */
    @RequestMapping(params="selectedItemSelector")
    public ModelAndView selectedItemSelector(HttpServletRequest request){
        String lineId = request.getParameter("lineId");
        String lineCard = request.getParameter("lineCard");
        String zjlx = request.getParameter("zjlx");
        String lineName = request.getParameter("lineName");
        request.setAttribute("lineId", lineId);
        request.setAttribute("lineCard", lineCard);
        request.setAttribute("zjlx", zjlx);
        request.setAttribute("lineName", lineName);
        return new ModelAndView("callnew/yctbQueuing/selectedItemSelector");
    }
    
    /**
     * 
     * 描述    当前叫号待办理事项列表
     * @author Danto Huang
     * @created 2019年3月13日 下午4:11:38
     * @param request
     * @param response
     */
    @RequestMapping(params="curSelectedItem")
    public void curSelectedItem(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("i.business_code", "asc");
        filter.addSorted("i.item_code", "asc");
        List<Map<String,Object>> list = newCallService.findCurLineSelectedItems(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    一窗通办咨询
     * @author Danto Huang
     * @created 2019年3月15日 上午9:27:05
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="goYctbQueuingDealzx")
    public ModelAndView goYctbQueuingDealzx(HttpServletRequest request){
        String detailId = request.getParameter("detailId");
        Map<String, Object> detail = newCallService.getByJdbc("T_CKBS_QUEUERECORD_ITEMDETAIL",
                new String[] { "DETAIL_ID" }, new Object[] { detailId });
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                new Object[] { detail.get("RECORD_ID") });
        request.setAttribute("detail", detail);
        request.setAttribute("record", record);
        return new ModelAndView("callnew/yctbQueuing/queuingDealZX");
    }

    /**
     * 
     * 描述    一窗通办领照
     * @author Danto Huang
     * @created 2019年3月15日 上午9:57:19
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="goYctbQueuingDeallz")
    public ModelAndView goYctbQueuingDeallz(HttpServletRequest request){
        String detailId = request.getParameter("detailId");
        Map<String, Object> detail = newCallService.getByJdbc("T_CKBS_QUEUERECORD_ITEMDETAIL",
                new String[] { "DETAIL_ID" }, new Object[] { detailId });
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                new Object[] { detail.get("RECORD_ID") });
        request.setAttribute("detail", detail);
        request.setAttribute("record", record);
        return new ModelAndView("callnew/yctbQueuing/queuingDealLZ");
    }

    /**
     * 
     * 描述    一窗通办取错事项
     * @author Danto Huang
     * @created 2019年3月15日 上午9:57:19
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="goYctbQueuingDealMistake")
    public ModelAndView goYctbQueuingDealMistake(HttpServletRequest request){
        String detailId = request.getParameter("detailId");
        Map<String, Object> detail = newCallService.getByJdbc("T_CKBS_QUEUERECORD_ITEMDETAIL",
                new String[] { "DETAIL_ID" }, new Object[] { detailId });
        Map<String, Object> record = newCallService.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                new Object[] { detail.get("RECORD_ID") });
        request.setAttribute("detail", detail);
        request.setAttribute("record", record);
        return new ModelAndView("callnew/yctbQueuing/queuingDealMistake");
    }
    
    /**
     * 
     * 描述    一窗通办处理保存
     * @author Danto Huang
     * @created 2019年3月15日 上午10:23:39
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="yctbQueuingDeal")
    @ResponseBody
    public AjaxJson yctbQueuingDeal(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("DETAIL_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD_ITEMDETAIL", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    验证是否当前办理的子项已全部办理
     * @author Danto Huang
     * @created 2019年3月14日 下午2:56:37
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="checkLineItemDeal")
    @ResponseBody
    public AjaxJson checkLineItemDeal(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String lineId = request.getParameter("lineId");
        List<Map<String, Object>> list = newCallService.getAllByJdbc("T_CKBS_QUEUERECORD_ITEMDETAIL",
                new String[] { "RECORD_ID", "DETAIL_STATUS" }, new Object[] { lineId, "0" });
        if(list!=null&&list.size()>0){
            j.setSuccess(false);
        }else{
            newCallService.updateQueueRecordByDeatil(lineId);
            j.setSuccess(true);
        }
        return j;
    }
    
    /**
     * 
     * 描述    一窗通办窗口 获取快件业务正在办理情况
     * @author Danto Huang
     * @created 2019年3月19日 下午4:50:49
     * @param request
     * @return
     */
    @RequestMapping(params="isExpressItemDealing")
    @ResponseBody
    public AjaxJson isExpressItemDealing(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String curWin = request.getParameter("curWin");
        Map<String,Object> result = newCallService.isExpressItemDealing(curWin);
        if((Boolean) result.get("isDealing")){
            j.setSuccess(true);
            j.setJsonString(result.get("businessCode").toString());
        }else{
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 描述:省网评价页面
     *
     * @author Madison You
     * @created 2021/1/6 14:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "proEvaluateView")
    public ModelAndView proEvaluateView(HttpServletRequest request) {
        request.setAttribute("recordId", request.getParameter("recordId"));
        return new ModelAndView("callnew/evaluate/evaluate");
    }

   /**
    * 
    * 描述    指定用户下发短信提醒
    * @author Allin Lin
    * @created 2021年3月11日 下午8:33:53
    * @param request
    * @param response
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(params="sendMsgToUser")
   @ResponseBody
   public void sendMsgToUser(HttpServletRequest request, HttpServletResponse response) {
       AjaxJson json = new AjaxJson();
       String lineNo = request.getParameter("lineNo");//当前排队号
       String winNo = request.getParameter("winNo");//窗口号
       String userJsonString = request.getParameter("user");//排队用户信息
       Map<String, Object> user = (Map<String, Object> )JSON.parse(userJsonString);
       Sm4Utils sm4 = new Sm4Utils();
       Map<String, Object> msgRecord = new HashMap<String, Object>();
       //1.判断该叫号用户是否存在手机号，存在且该排队记录未发送过短信、则进行短信下发
       String lineCardNo = StringUtil.getString(user.get("LINE_CARDNO"));
       if(StringUtils.isNotEmpty(lineCardNo)){
           String cardNo = sm4.encryptDataCBC(lineCardNo);
           Map<String, Object> lineUser = newCallService.getByJdbc("T_BSFW_LINEUSERS",
                   new String[]{"LINE_CARDNO"}, new Object[]{cardNo});
           if(lineUser!=null){
               String lineMobile = StringUtil.getString(lineUser.get("LINE_MOBILE"));
               Map<String, Object> sendRecord =  newCallService.getByJdbc("T_BSFW_LINEUSERS_MSGRECORD",
                       new String[]{"QUEUE_RECORDID"}, new Object[]{user.get("RECORD_ID")});
               if(StringUtils.isNotEmpty(lineMobile) && sendRecord==null){
                   //进行短信下发
                   msgRecord.put("QUEUE_RECORDID", user.get("RECORD_ID"));
                   msgRecord.put("LINE_NO", user.get("LINE_NO"));
                   msgRecord.put("LINE_NAME", user.get("LINE_NAME"));
                   msgRecord.put("LINE_MOBILE", lineMobile);
                   msgRecord.put("CREATOR_NAME",AppUtil.getLoginUser().getFullname());
                   String msg = "\""+user.get("LINE_NAME")+",您好！当前排队已叫号到"+lineNo+",您之前还有3位用户,请到"+
                           user.get("ROOM_NO")+"服务大厅,"+winNo+"号窗口"+"等候叫号\"";
                   msgRecord.put("MSG",msg);
                   Map<String, Object> resultMap;
                try {
                    resultMap = SmsSendUtil.sendSms(StringUtil.getString(user.get("LINE_NAME")) + "," + 
                       lineNo+","+StringUtil.getString(user.get("ROOM_NO")), lineMobile,SMS_SEND_TEMPLATEID);
                    if (resultMap != null) {
                        String resultcode = resultMap.get("resultcode").toString();
                        StringBuffer logInfo = new StringBuffer();
                        if (Objects.equals(resultcode, "0")) {
                            msgRecord.put("MSG_STATUS","1");//发送成功
                            log.info(logInfo.append("排队号为").append(user.get("LINE_NO"))
                                    .append("发送短信提醒成功,手机号码为：").append(lineMobile));
                        } else {
                           msgRecord.put("MSG_STATUS","0");//发送失败
                           logInfo.append("排队号为").append(user.get("LINE_NO")).append("发送短信提醒失败，失败信息为：")
                                    .append(JSON.toJSONString(resultMap)).append("，手机号码为").append(lineMobile);
                            log.info(logInfo);
                        }
                        msgRecord.put("RETURN_MSG",JSON.toJSONString(resultMap));
                        newCallService.saveOrUpdate(msgRecord, "T_BSFW_LINEUSERS_MSGRECORD", null);
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
               }
           }
       }
       json.setJsonString("排队号:"+user.get("LINE_NO")+"-"+user.get("LINE_NAME")+"-完成短信下发！");
   }
   
   /**
    * 
    * 描述    叫号发送记录查询页面
    * @author Allin Lin
    * @created 2021年3月22日 下午3:40:24
    * @param request
    * @return
    */
   @RequestMapping(params="msgRecordView")
   public ModelAndView msgRecordView(HttpServletRequest request){
       return new ModelAndView("callnew/queuing/msgRecordView");
   }
   
   /**
    * 
    * 描述      叫号发送记录列表信息
    * @author Danto Huang
    * @created 2018年9月10日 下午6:43:40
    * @param request
    * @param response
    */
   @RequestMapping(params="lineMsgRecordGrid")
   public void lineMsgRecordGrid(HttpServletRequest request,HttpServletResponse response){
       SqlFilter filter = new SqlFilter(request);
       List<Map<String, Object>> list = newCallService.findlineMsgRcordBySqlFilter(filter);
       this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
               null, JsonUtil.EXCLUDE, response);
   }

    /**
     * 描述:省网评价页面--审批后台
     *
     * @author Madison You
     * @created 2021/1/6 14:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "evaluatePc")
    public ModelAndView evaluatePc(HttpServletRequest request) {
        request.setAttribute("EXE_ID", request.getParameter("EXE_ID"));
        return new ModelAndView("callnew/evaluate/evaluatePc");
    }
    
    /**
     * 描述:省网评价页面--审批前台
     *
     * @author Madison You
     * @created 2021/1/6 14:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "evaluateWeb")
    public ModelAndView evaluateWeb(HttpServletRequest request) {
        request.setAttribute("EXE_ID", request.getParameter("EXE_ID"));
        return new ModelAndView("callnew/evaluate/evaluateWeb");
    }

    /**
     *
     * 描述    评价结果--PC审批后台
     * @author Danto Huang
     * @created 2018年7月10日 下午4:19:23
     * @param request
     * @return
     */
    @RequestMapping(params="saveEvaluatePc")
    @ResponseBody
    public AjaxJson saveEvaluatePc(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String EXE_ID = request.getParameter("EXE_ID");
        String evaLeveln = request.getParameter("evaln")== null ? "" : request.getParameter("evaln").toString();
        String strInfo = request.getParameter("strInfo")== null ? "" : request.getParameter("strInfo").toString();
        j.setSuccess(false);
        if (StringUtils.isNotEmpty(evaLeveln)) {//液晶评价器2
            Map<String, Object> variables = new HashMap<String, Object>();
            String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String flag = evaLeveln;//5：非常满意4：满意3：基本满意2：不满意1：非常不满意
            String evals = "4";//评价结果（3：非常满意，2：满意，1：一般，0：不满意，4：不评价
            if ("5".equals(evaLeveln)) {
                evals = "3";
            } else if("4".equals(evaLeveln)){
                evals = "2";
            }else if("3".equals(evaLeveln)){
                evals = "1";
            }else if("2".equals(evaLeveln)){
                evals = "0";
            }else if("1".equals(evaLeveln)){
                evals = "5";
            }

            variables.put("EXE_ID", EXE_ID);
            variables.put("EVALUATE", evals);
            variables.put("EVALUATETIME", evaluateTime);
            variables.put("EVALUATETYPE", "3");//评价方式（1：平板，2：按键器,3审批后台,4WEB）
            variables.put("CALL_STATUS","-1");//审批后台
            variables.put("ROOM_NO","B");//B厅
            variables.put("CUR_WIN","99");//99窗口
            String recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", "");

            //保存好差评明细信息
            Map<String,Object> info = new HashMap<String, Object>();
            info.put("RECORD_ID", recordId);
            info.put("EVA_LEVEL", flag);//5：非常满意4：满意3：基本满意2：不满意1：非常不满意
            info.put("EVA_INFO", strInfo);
            info.put("EVALUATETIME", evaluateTime);
            newCallService.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", null);
            /*如果是差评*/
            dealNagetiveEvaluate(recordId, evals, evaluateTime);
            j.setSuccess(true);
        }
        j.setMsg("完成评价");
        return j;
    }
    
    /**
    *
    * 描述    评价结果--PC审批后台
    * @author Danto Huang
    * @created 2018年7月10日 下午4:19:23
    * @param request
    * @return
    */
   @RequestMapping(params="saveEvaluateWeb")
   @ResponseBody
   public AjaxJson saveEvaluateWeb(HttpServletRequest request){
       AjaxJson j = new AjaxJson();
       String EXE_ID = request.getParameter("EXE_ID");
       String evaLeveln = request.getParameter("evaln")== null ? "" : request.getParameter("evaln").toString();
       String strInfo = request.getParameter("strInfo")== null ? "" : request.getParameter("strInfo").toString();
       j.setSuccess(false);
       if (StringUtils.isNotEmpty(evaLeveln)) {//液晶评价器2
           Map<String, Object> variables = new HashMap<String, Object>();
           String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
           String flag = evaLeveln;//5：非常满意4：满意3：基本满意2：不满意1：非常不满意
           String evals = "4";//评价结果（3：非常满意，2：满意，1：一般，0：不满意，4：不评价
           if ("5".equals(evaLeveln)) {
               evals = "3";
           } else if("4".equals(evaLeveln)){
               evals = "2";
           }else if("3".equals(evaLeveln)){
               evals = "1";
           }else if("2".equals(evaLeveln)){
               evals = "0";
           }else if("1".equals(evaLeveln)){
               evals = "5";
           }

           variables.put("EXE_ID", EXE_ID);
           variables.put("EVALUATE", evals);
           variables.put("EVALUATETIME", evaluateTime);
           variables.put("EVALUATETYPE", "4");//评价方式（1：平板，2：按键器,3审批后台,4WEB）
           variables.put("CALL_STATUS","-1");//审批后台
           variables.put("ROOM_NO","B");//B厅
           variables.put("CUR_WIN","99");//99窗口
           String recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", "");

           //保存好差评明细信息
           Map<String,Object> info = new HashMap<String, Object>();
           info.put("RECORD_ID", recordId);
           info.put("EVA_LEVEL", flag);//5：非常满意4：满意3：基本满意2：不满意1：非常不满意
           info.put("EVA_INFO", strInfo);
           info.put("EVALUATETIME", evaluateTime);
           newCallService.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", null);
           /*如果是差评*/
           dealNagetiveEvaluate(recordId, evals, evaluateTime);
           j.setSuccess(true);
       }
       j.setMsg("完成评价");
       return j;
   }
   
   
}
