/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyctb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import com.alibaba.fastjson.JSONObject;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.callyctb.service.CallYctbService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 一窗通办Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2019年3月12日15:05:15
 */
@Controller
@RequestMapping("/callYctbController")
public class CallYctbController extends BaseController {

    /**
     * 描述 二维码加密唯一识别号
     *
     * @author Madison You
     * @created 2019年7月29日21:04:15
     */
    private final String appId = "10001";

    /**
     * 描述 二维码加密密钥
     *
     * @author Madison You
     * @created 2019年7月29日21:04:15
     */
    private final String key = "d60JkQuMY7uDvI9x";

    /**
     * 描述 二维码加密网址
     *
     * @author Madison You
     * @created 2019年7月29日21:04:15
     */
    private final String postUrl = "https://zwfw.pingtanii.com/api/external/validCodeWithEncrypt";

    /**
     * 描述:发送到178服务器
     *
     * @author Madison You
     * @created 2019/11/28 18:00:00
     * @param
     * @return
     */
    private final String QR_URL = "http://192.168.129.178/callYctbController/yctbGetIdentityByQrcode.do";

    /**
     * 描述:转发到178服务器地址
     *
     * @author Madison You
     * @created 2019/12/2 10:34:00
     * @param
     * @return
     */
    private final String HLW_URL = "http://192.168.129.178/callYctbController/sendSceneNotifyForHlw.do";

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CallYctbController.class);
    /**
     * 引入Service
     */
    @Resource
    private CallYctbService callYctbService;
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
     * 引入dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callYctbService.remove("T_CKBS_YCTBQH", "YCTBQH_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 一窗通办记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "yctbqhView")
    public ModelAndView yctbqhView(HttpServletRequest request) {
        return new ModelAndView("callYctb/yctbqhpz/yctbqhView");
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String, Object> callYctb = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            callYctb = callYctbService.getByJdbc("T_CKBS_YCTBQH", new String[] { "YCTBQH_ID" },
                    new Object[] { entityId });
        }
        callYctb.put("PARENT_ID", parentId);
        callYctb.put("PARENT_NAME", parentName);
        request.setAttribute("callYctb", callYctb);
        return new ModelAndView("callYctb/yctbqhpz/yctbqhInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("YCTBQH_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = callYctbService.saveOrUpdateTreeData(parentId, variables, "T_CKBS_YCTBQH", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 一窗通办配置类别记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 一窗通办配置类别记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * easyui AJAX请求数据
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
        filter.addSorted("T.BUS_ID", "asc");
        List<Map<String, Object>> list = callYctbService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 获取当前类型的业务编码
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getBusinessCodes")
    public void getBusinessCodes(HttpServletRequest request, HttpServletResponse response) {
        String yctbqhId = request.getParameter("YCTBQH_ID");
        Map<String, Object> businessCodes = callYctbService.getBusinessCode(yctbqhId);
        String json = JSON.toJSONString(businessCodes);
        this.setJsonString(json, response);
    }

    /**
     * 将用户加入到部门当中去
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addBueiness")
    @ResponseBody
    public AjaxJson addBueiness(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String YCTBQH_ID = request.getParameter("YCTBQH_ID");
        String businessCodes = request.getParameter("businessCodes");
        if (StringUtils.isNotEmpty(businessCodes)) {
            String[] codes = businessCodes.split(",");
            callYctbService.remove("T_CKBS_YCTBQH_BUS", new String[] { "YCTBQH_ID" }, new Object[] { YCTBQH_ID });
            Map<String, Object> variables = null;
            for (String code : codes) {
                variables = new HashMap<String, Object>();
                variables.put("YCTBQH_ID", YCTBQH_ID);
                variables.put("BUSINESS_CODE", code);
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                callYctbService.saveOrUpdate(variables, "T_CKBS_YCTBQH_BUS", null);
            }
        }

        j.setMsg("加入业务成功");
        return j;
    }

    /**
     * 将业务移除
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "removeBueiness")
    @ResponseBody
    public AjaxJson removeBueiness(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String busIds = request.getParameter("busIds");
        if (StringUtils.isNotEmpty(busIds)) {
            String[] ids = busIds.split(",");
            for (String id : ids) {
                callYctbService.remove("T_CKBS_YCTBQH_BUS", new String[] { "BUS_ID" }, new Object[] { id });
            }
        }
        j.setMsg("解绑业务成功");
        return j;
    }

    /**
     * 
     * 描述： 一窗通办取号事项添加
     * 
     * @author Rider Chen
     * @created 2019年3月13日 上午11:30:54
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/addItemCart")
    @ResponseBody
    public AjaxJson addItemCart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String itemCode = request.getParameter("itemCode");
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isEmpty(itemCode)) {
            j.setMsg("无效事项信息！");
            j.setSuccess(false);
        } else if (!newCallService.isWinAccept(businessCode)) {
            j.setMsg("您选择的业务暂无对应办事窗口，请前往人工导引台咨询！");
            j.setSuccess(false);
        } else {
            // 获取session中的事项列表信息
            Map<String, Object> itemCart = AppUtil.getItemCart();
            // 判断取号事项中是否已经存在数据
            if (null != itemCart && itemCart.size() > 0 && itemCart.size() < 5) {
                // 当前事项不存在，则添加到session
                if (null == itemCart.get(itemCode)) {
                    Map<String, Object> map = null;
                    boolean isok = true;
                    String itemName = "";
                    for (Entry<String, Object> entry : itemCart.entrySet()) {
                        map = (Map<String, Object>) entry.getValue();
                        String bcode = map.get("businessCode").toString();
                        // 判断是否能同时取号
                        if (comparisonCode("1", businessCode) != comparisonCode("1", bcode)) {
                            isok = false;
                            itemName = map.get("itemName").toString();
                        }
                    }
                    if (isok) {
                        itemCart.put(itemCode, variables);
                        HttpSession session = AppUtil.getSession();
                        AppUtil.addItemCartToSession(session, itemCart);
                        j.setMsg("添加事项成功！");
                        j.setSuccess(true);
                    } else {
                        j.setMsg("该事项与已选择事项（" + itemName + "）冲突，无法同时办理取号！");
                        j.setSuccess(false);
                    }
                } else {
                    j.setMsg("该事项已经在取号列表，无需重复添加！");
                    j.setSuccess(false);
                }
            } else if (null != itemCart && itemCart.size() >= 5) {
                j.setMsg("最多添加五个事项！");
                j.setSuccess(false);
            } else {
                itemCart = new LinkedHashMap<String, Object>();
                itemCart.put(itemCode, variables);
                HttpSession session = AppUtil.getSession();
                AppUtil.addItemCartToSession(session, itemCart);
                j.setMsg("添加事项成功！");
                j.setSuccess(true);
            }

        }
        return j;
    }

    /**
     * 
     * 描述：业务编码比对
     * 
     * @author Rider Chen
     * @created 2019年3月15日 下午2:53:11
     * @param
     * @return
     */
    public int comparisonCode(String type, String code) {
        if ("1".equals(type)) {// 类型1 日常情况
            List<Map<String, Object>> list = dictionaryService.findList("YCTBQHRCQK", "ASC");
            for (Map<String, Object> map : list) {
                String dicName = map.get("DIC_NAME").toString();
                String dicCode = map.get("DIC_CODE").toString();
                if (dicName.contains(code)) {
                    return Integer.parseInt(dicCode);
                }
            }
        } else if ("2".equals(type)) {// 类型2 预警情况
            List<Map<String, Object>> list = dictionaryService.findList("YCTBQHYJQK", "ASC");
            for (Map<String, Object> map : list) {
                String dicName = map.get("DIC_NAME").toString();
                String dicCode = map.get("DIC_CODE").toString();
                if (dicName.contains(code)) {
                    return Integer.parseInt(dicCode);
                }
            }
        }
        return 0;
    }

    /**
     * 
     * 描述： 一窗通办取号事项删除
     * 
     * @author Rider Chen
     * @created 2019年3月13日 上午11:30:54
     * @param request
     * @return
     */
    @RequestMapping("/removeItemCart")
    @ResponseBody
    public AjaxJson removeItemCart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String itemCode = request.getParameter("itemCode");
        if (StringUtils.isEmpty(itemCode)) {
            j.setMsg("无效事项信息！");
            j.setSuccess(false);
        } else {
            Map<String, Object> itemCart = AppUtil.getItemCart();
            if (null != itemCart && itemCart.size() > 0) {// 取号事项购物车已经存在数据
                if (null == itemCart.get(itemCode)) {
                    j.setMsg("该事项不在取号列表，无需删除！");
                    j.setSuccess(false);
                } else {
                    itemCart.remove(itemCode);
                    HttpSession session = AppUtil.getSession();
                    AppUtil.addItemCartToSession(session, itemCart);
                    j.setMsg("删除事项成功！");
                    j.setSuccess(true);
                }
            } else {
                j.setMsg("取号列表为空！");
                j.setSuccess(false);
            }

        }
        return j;
    }

    /**
     * 
     * 描述： 一窗通办生成取号结果
     * 
     * @author Rider Chen
     * @created 2019年3月13日 上午11:30:54
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/yctbGenerateNo")
    @ResponseBody
    public AjaxJson yctbGenerateNo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        // 事项列表不能为空
        if (null != itemCart && itemCart.size() > 0) {
            Map<String, Object> variables = new HashMap<String, Object>();
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            String businessCode = itemMap.get("businessCode").toString();// 业务编码
            String roomNo = itemMap.get("roomNo").toString();// 所属大厅
            String itemCode = itemMap.get("itemCode").toString();// 事项编码
            String itemName = itemMap.get("itemName").toString();// 事项名称
            String defKey = itemMap.get("defKey").toString();// 流程key
            String materTypeId = itemMap.get("materTypeId").toString();// 事项子项ID

            String lineName = request.getParameter("lineName");// 取号人姓名
            String lineCardNo = request.getParameter("lineCardNo");// 取号人身份证号码
            String address = request.getParameter("address");// 取号人地址
            String zjlx = request.getParameter("LINE_ZJLX");// 取号人证件类型（默认：身份证SF）
            String isVip = request.getParameter("isVip");// 是否VIP
            String isAppoint = request.getParameter("isAppoint");// 是否预约
            String appointmentId = request.getParameter("appointmentId");// 预约ID
            /*判断是否是失信人员*/
            Map<String, Object> lostPromiseMap = newCallService.checkLostPromiseByBusCode(lineName, lineCardNo,
                   businessCode);
            Sm4Utils sm4 = new Sm4Utils();
            if (StringUtils.isEmpty(lineCardNo) || lineCardNo.equals("undefined")) {
                j.setMsg("无效取号信息！");
                j.setSuccess(false);
            } else if (!newCallService.isWinAccept(businessCode)) {
                j.setMsg("您选择的业务暂无对应办事窗口，请前往人工导引台咨询！");
                j.setSuccess(false);
            } else if (newCallService.isWaiting(businessCode, sm4.encryptDataCBC(lineCardNo))) {
                j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
                j.setSuccess(false);
            } else if ((boolean)lostPromiseMap.get("isLostPromise")){
                j.setMsg((String)lostPromiseMap.get("msg"));
                j.setSuccess(false);
            }else{
                variables.put("IS_YCTB", 1);// 是否一窗通办（0：否，1：是）
                variables.put("IS_ITEM_CHOOSE", 1);// 是否选择事项取号（0：否，1：是）

                variables.put("BUSINESS_CODE", businessCode);
                variables.put("LINE_ZJLX", zjlx);
                variables.put("LINE_NAME", lineName);
                variables.put("LINE_CARDNO", lineCardNo);
                variables.put("LINE_ADDRESS", address);
                if (StringUtils.isEmpty(roomNo)) {
                    roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                            new Object[] { businessCode }).get("BELONG_ROOM").toString();
                }
                if (StringUtils.isNotEmpty(businessCode)) {
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
                
                if (StringUtils.isNotEmpty(isVip)) {
                    variables.put("IS_VIP", isVip);
                }
                if (StringUtils.isNotEmpty(isAppoint)) {
                    variables.put("IS_APPOINTMENT", isAppoint);
                }
                String recordId="";
                //取号并发问题
                String synBusinessCode="takeNo"+businessCode;
                synchronized (synBusinessCode) {
                    variables = newCallService.getLineNo(variables);
                    recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", null);
                }
                String careWinNo = newCallService.getCareWin(businessCode);
                //如果属于A、B厅，那么返回大厅的等待人数
                int waitNum =0;
                int businessWaitNum=0;
                if("A".equals(roomNo)||"B".equals(roomNo)){
                    waitNum=newCallService.getWaitingNumByRoomNo(roomNo);
                    businessWaitNum=newCallService.getWaitingNumByBusinessCode(businessCode);
                }else{
                    waitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
                }

                Map<String, Object> line = new HashMap<String, Object>();
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
                
                if (isAppoint != null && isAppoint != "" && isAppoint.equals("1")) {
                    Map<String, Object> appointment = new HashMap<String, Object>();
                    appointment.put("NUM_ID", recordId);
                    appointment.put("IS_TAKE", "1");
                    
                    if (StringUtils.isNotEmpty(datetime)) {
                        appointment.put("TAKE_TIME", datetime);
                    }else{
                        appointment.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    }
                    
                    newCallService.saveOrUpdate(appointment, "T_CKBS_APPOINTMENT_APPLY", appointmentId);
                }

                // 把事项添加到子表
                Map<String, Object> itemDetail = null;
                Map<String, Object> map = null;
                for (Entry<String, Object> entry : itemCart.entrySet()) {
                    map = (Map<String, Object>) entry.getValue();
                    itemDetail = new HashMap<String, Object>();
                    itemDetail.put("RECORD_ID", recordId);
                    itemDetail.put("SUBBUS_CLASS", map.get("materTypeId"));
                    itemDetail.put("ITEM_CODE", map.get("itemCode"));
                    itemDetail.put("BUSINESS_CODE", map.get("businessCode"));
                    newCallService.saveOrUpdate(itemDetail, "T_CKBS_QUEUERECORD_ITEMDETAIL", null);
                }
                
                //小程序消息推送
                if(zjlx.equals("SF")){
                    String businessName = newCallService.getByJdbc("T_CKBS_BUSINESSDATA",
                            new String[] { "BUSINESS_CODE" }, new Object[] { businessCode }).get("BUSINESS_NAME")
                            .toString();
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
//                    sendSceneNotify(businessInfo);
                }
                
                j.setJsonString(JSON.toJSONString(line));
            }
        } else {
            j.setMsg("无效取号信息！");
            j.setSuccess(false);
        }
        HttpSession session = AppUtil.getSession();
        AppUtil.addItemCartToSession(session, null);
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
            //e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        
    }


    /**
     * 描述: 取号机扫描二维码取号并且返回身份信息
     *
     * @author Madison You
     * @created 2019年7月17日 上午08:37:54
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/yctbGetIdentityByQrcode")
    @ResponseBody
    public String yctbGetIdentityByQrcode(HttpServletRequest request , HttpServletResponse response){
        String qrcode = request.getParameter("qrcode");
        if (qrcode != null && qrcode.length() > 0) {
            long time = System.currentTimeMillis();
            String timeStr = String.valueOf(time);
            /*生成签名和加密数据*/
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId", appId);
            jsonObject.put("code",qrcode);
            jsonObject.put("scene","WINDOW_TAKE_NO");
            jsonObject.put("timestamp", timeStr);
            jsonObject.put("deviceInfo","device info");
            String sign = EncryptUtils.buildSign(jsonObject, key);
            String dataEncrypt = AesUtils.encryptToBase64(jsonObject.toString(), key);
            /*获取身份信息*/
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("appId", appId);
            jsonObject1.put("sign", sign);
            jsonObject1.put("data", dataEncrypt);
            String identity = doPost(postUrl, jsonObject1);
            return identity;
        } else {
            return "系统错误";
        }
    }

    /**
     * 描述:取号机扫描二维码取号并且返回身份信息(转发到178访问)
     *
     * @author Madison You
     * @created 2019/11/28 18:03:00
     * @param
     * @return
     */
    @RequestMapping("/yctbGetIdentityByQrcodeforHlw")
    @ResponseBody
    public String yctbGetIdentityByQrcodeforHlw(HttpServletRequest request, HttpServletResponse response) {
        String qrcode = request.getParameter("qrcode");
        HashMap<String, Object> params = new HashMap<>();
        params.put("qrcode", qrcode);
        String returnStr = HttpSendUtil.sendPostParams(QR_URL,  params);
        return returnStr;
    }

    /**
     * 描述: 发送post请求
     *
     * @author Madison You
     * @created 2019年7月17日 上午08:37:54
     * @return
     */
    public String doPost(String url, JSONObject json){
        String returnStr = HttpSendUtil.sendHttpsPostJson(url, null, json.toString(), "utf-8");
        return returnStr;
    }


    /**
     * 获取map中第一个数据值
     *
     * @param map
     *            数据源
     * @return
     */
    private static Object getFirstOrNull(Map<String, Object> map) {
        Object obj = null;
        for (Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 
     * 描述：跳转一窗通办取号业务事项选择
     * 
     * @author Rider Chen
     * @created 2019年3月21日 上午10:15:37
     * @param request
     * @return
     */
    @RequestMapping("/toYctbBusinessItemChoose")
    public ModelAndView toYctbBusinessItemChoose(HttpServletRequest request) {
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        String cleanItem = request.getParameter("cleanItem");

        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);

        // 清空session中的事项
        if (StringUtils.isNotEmpty(cleanItem) && "1".equals(cleanItem)) {
            Map<String, Object> itemCart = new LinkedHashMap<String, Object>();
            HttpSession session = AppUtil.getSession();
            AppUtil.addItemCartToSession(session, itemCart);
        }
        Map<String, Object> itemCart = AppUtil.getItemCart();
        if (null != itemCart) {
            request.setAttribute("itemNum", itemCart.size());
        } else {
            request.setAttribute("itemNum", 0);
        }

        return new ModelAndView("callYctb/takeNo/chooseYctbBusinessItem");
    }

    /**
     * 
     * 描述：跳转一窗通办取号业务事项选择
     * 
     * @author Rider Chen
     * @created 2019年3月21日 上午10:15:37
     * @param request
     * @return
     */
    @RequestMapping("/toYctbBusinessItemChooseMacW")
    public ModelAndView toYctbBusinessItemChooseMacW(HttpServletRequest request) {
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        String cleanItem = request.getParameter("cleanItem");

        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);

        // 清空session中的事项
        if (StringUtils.isNotEmpty(cleanItem) && "1".equals(cleanItem)) {
            Map<String, Object> itemCart = new LinkedHashMap<String, Object>();
            HttpSession session = AppUtil.getSession();
            AppUtil.addItemCartToSession(session, itemCart);
        }
        Map<String, Object> itemCart = AppUtil.getItemCart();
        if (null != itemCart) {
            request.setAttribute("itemNum", itemCart.size());
        } else {
            request.setAttribute("itemNum", 0);
        }

        return new ModelAndView("callYctb/takeNo/chooseYctbBusinessItemMacW");
    }
    /**
     * 
     * 描述：跳转到事项详情页面
     * 
     * @author Rider Chen
     * @created 2019年3月22日 下午3:32:36
     * @param request
     * @return
     */
    @RequestMapping("/toMaterChoose")
    public ModelAndView toMaterChoose(HttpServletRequest request) {
        SqlFilter filter = new SqlFilter(request);
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String businessCode = request.getParameter("businessCode");
        String businessName = request.getParameter("businessName");
        String departId = request.getParameter("departId");

        filter.addFilter("Q_S.ITEM_CODE_=", itemCode);
        List<Map<String, Object>> materList = applyMaterService.findForItem2(filter);

        List<Map<String, Object>> materTypeList = newCallService.findMaterByItemCode(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("defKey", defKey);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("businessName", businessName);
        request.setAttribute("departId", departId);
        Map<String, Object> itemCart  = AppUtil.getItemCart();
        if (null != itemCart) {
            request.setAttribute("itemNum", itemCart.size());
        } else {
            request.setAttribute("itemNum", 0);
        }
        if (materTypeList != null && materTypeList.size() > 0) {
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callYctb/takeNo/chooseMater");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("RECORD_ID", "");
            map.put("BUSCLASS_NAME", "");
            materTypeList.add(map);
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callYctb/takeNo/chooseMater");
        }
    }

    /**
     * 
     * 描述：跳转到事项详情页面
     * 
     * @author Rider Chen
     * @created 2019年3月22日 下午3:32:36
     * @param request
     * @return
     */
    @RequestMapping("/toMaterChooseMacW")
    public ModelAndView toMaterChooseMacW(HttpServletRequest request) {
        SqlFilter filter = new SqlFilter(request);
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String businessCode = request.getParameter("businessCode");
        String businessName = request.getParameter("businessName");
        String departId = request.getParameter("departId");

        filter.addFilter("Q_S.ITEM_CODE_=", itemCode);
        List<Map<String, Object>> materList = applyMaterService.findForItem2(filter);

        List<Map<String, Object>> materTypeList = newCallService.findMaterByItemCode(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("defKey", defKey);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("businessName", businessName);
        request.setAttribute("departId", departId);
        Map<String, Object> itemCart  = AppUtil.getItemCart();
        if (null != itemCart) {
            request.setAttribute("itemNum", itemCart.size());
        } else {
            request.setAttribute("itemNum", 0);
        }
        if (materTypeList != null && materTypeList.size() > 0) {
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callYctb/takeNo/chooseMaterMacW");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("RECORD_ID", "");
            map.put("BUSCLASS_NAME", "");
            materTypeList.add(map);
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callYctb/takeNo/chooseMaterMacW");
        }
    }
    /**
     * 
     * 描述：跳转到事项购物车页面
     * 
     * @author Rider Chen
     * @created 2019年3月22日 下午4:18:45
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/toItemCart")
    public ModelAndView toItemCart(HttpServletRequest request) {
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        request.setAttribute("itemCart", itemCart);
        if (null != itemCart && itemCart.size() > 0) {
            request.setAttribute("itemNum", itemCart.size());
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            request.setAttribute("itemMap", itemMap);
        } else {
            request.setAttribute("itemNum", 0);
        }
        return new ModelAndView("callYctb/takeNo/itemCart");
    }

    /**
     * 
     * 描述：跳转到事项购物车页面
     * 
     * @author Rider Chen
     * @created 2019年3月22日 下午4:18:45
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/toItemCartMacW")
    public ModelAndView toItemCartMacW(HttpServletRequest request) {
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        request.setAttribute("itemCart", itemCart);
        if (null != itemCart && itemCart.size() > 0) {
            request.setAttribute("itemNum", itemCart.size());
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            request.setAttribute("itemMap", itemMap);
        } else {
            request.setAttribute("itemNum", 0);
        }
        return new ModelAndView("callYctb/takeNo/itemCartMacW");
    }
    /**
     * 
     * 描述：跳转取号页面
     * 
     * @author Rider Chen
     * @created 2019年3月22日 下午4:18:45
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/yctbReadCard")
    public ModelAndView yctbReadCard(HttpServletRequest request) {
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        // 事项列表不能为空
        if (null != itemCart && itemCart.size() > 0) {
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            request.setAttribute("itemMap", itemMap);
        }
        return new ModelAndView("callYctb/takeNo/yctbReadCard");
    }
    /**
     * 
     * 描述：跳转取号页面
     * 
     * @author Rider Chen
     * @created 2019年3月22日 下午4:18:45
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/yctbReadCardWhite")
    public ModelAndView yctbReadCardWhite(HttpServletRequest request) {
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        // 事项列表不能为空
        if (null != itemCart && itemCart.size() > 0) {
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            request.setAttribute("itemMap", itemMap);
        }
        return new ModelAndView("callYctb/takeNo/yctbReadCardWhite");
    }


}
