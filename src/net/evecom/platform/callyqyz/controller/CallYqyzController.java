/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyqyz.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.callyctb.controller.CallYctbController;
import net.evecom.platform.callyctb.service.CallYctbService;
import net.evecom.platform.callyqyz.service.CallYqyzService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 描述:一企一证取号
 *
 * @author Madison You
 * @created 2019-12-26 17:30:00
 */
@Controller
@RequestMapping("/callYqyzController")
public class CallYqyzController extends BaseController {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/26 19:12:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(CallYqyzController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/26 19:12:00
     * @param
     * @return
     */
    @Resource
    private CallYqyzService callYqyzService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 9:09:00
     * @param
     * @return
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 12:52:00
     * @param
     * @return
     */
    @Resource
    private ApplyMaterService applyMaterService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:14:00
     * @param 
     * @return 
     */
    @Resource
    private DepartmentService departmentService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 12:53:00
     * @param
     * @return
     */
    @Resource
    private NewCallService newCallService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 15:04:00
     * @param
     * @return
     */
    @Resource
    private CallYctbController callYctbController;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:09:00
     * @param 
     * @return 
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:10:00
     * @param 
     * @return 
     */
    @Resource
    private CatalogService catalogService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:27:00
     * @param
     * @return
     */
    @Resource
    private BusTypeService busTypeService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:28:00
     * @param
     * @return
     */
    @Resource
    private FlowMappedService flowMappedService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:15:00
     * @param
     * @return 
     */
    @Resource
    private DepartServiceItemService departServiceItemService;

    /**
     * 描述:跳转选择证照页面
     *
     * @author Madison You
     * @created 2019/12/26 17:49:00
     * @param
     * @return
     */
    @RequestMapping("/toYqyzZzTypeMacW")
    public ModelAndView toYqyzZzTypeMacW(HttpServletRequest request) {
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String macType = request.getParameter("macType");
        String cleanItem = request.getParameter("cleanItem");
        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("macType", macType);
        request.setAttribute("cleanItem", cleanItem);
        return new ModelAndView("callYqyz/takeNo/yqyzZzTypeMacW");
    }

    /**
     * 描述:跳转业务选择页面
     *
     * @author Madison You
     * @created 2019/12/26 17:50:00
     * @param
     * @return
     */
    @RequestMapping("/toYqyzBusinessItemChooseMacW")
    public ModelAndView toYqyzBusinessItemChooseMacW(HttpServletRequest request) {
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        String cleanItem = request.getParameter("cleanItem");
        String zzType = request.getParameter("zzType");

        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("zzType", zzType);

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
        
        //跨省通办业务
        String jspUrl = "callYqyz/takeNo/chooseYqyzBusinessItemMacW";
        if("kstb".equals(zzType)) {
            jspUrl = "callYqyz/takeNo/chooseYqyzBusinessItemKstb";
        } else if("Sntb".equals(zzType)) {
            jspUrl = "callYqyz/takeNo/chooseYqyzBusinessItemSntb";
        }
        return new ModelAndView(jspUrl);
    }

    /**
     * 描述:一企一证业务配置
     *
     * @author Madison You
     * @created 2019/12/26 19:11:00
     * @param
     * @return
     */
    @RequestMapping(params = "yqyzqhView")
    public ModelAndView yctbqhView(HttpServletRequest request) {
        return new ModelAndView("callYqyz/yqyzqhpz/yqyzqhView");
    }

    /**
     * 描述:一企一证配置数据
     *
     * @author Madison You
     * @created 2019/12/26 19:13:00
     * @param
     * @return
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        filter.addSorted("T.BUS_ID", "asc");
        List<Map<String, Object>> list = callYqyzService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:一企一证添加业务
     *
     * @author Madison You
     * @created 2019/12/27 9:00:00
     * @param
     * @return
     */
    @RequestMapping(params = "getYqyzBusinessCodes")
    public void getYqyzBusinessCodes(HttpServletRequest request, HttpServletResponse response) {
        String yctbqhId = request.getParameter("YQYZQH_ID");
        Map<String, Object> businessCodes = callYqyzService.getBusinessCode(yctbqhId);
        String json = JSON.toJSONString(businessCodes);
        this.setJsonString(json, response);
    }

    /**
     * 描述:将用户加入到部门当中去
     *
     * @author Madison You
     * @created 2019/12/27 9:01:00
     * @param
     * @return
     */
    @RequestMapping(params = "addYqyzBueiness")
    @ResponseBody
    public AjaxJson addYqyzBueiness(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String YQYZQH_ID = request.getParameter("YQYZQH_ID");
        String businessCodes = request.getParameter("businessCodes");
        if (StringUtils.isNotEmpty(businessCodes)) {
            String[] codes = businessCodes.split(",");
            callYqyzService.remove("T_CKBS_YQYZQH_BUS", new String[] { "YQYZQH_ID" }, new Object[] { YQYZQH_ID });
            Map<String, Object> variables = null;
            for (String code : codes) {
                variables = new HashMap<String, Object>();
                variables.put("YQYZQH_ID", YQYZQH_ID);
                variables.put("BUSINESS_CODE", code);
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                callYqyzService.saveOrUpdate(variables, "T_CKBS_YQYZQH_BUS", null);
            }
        }

        j.setMsg("加入业务成功");
        return j;
    }

    /**
     * 描述:将业务移除
     *
     * @author Madison You
     * @created 2019/12/27 9:04:00
     * @param
     * @return
     */
    @RequestMapping(params = "removeYqyzBueiness")
    @ResponseBody
    public AjaxJson removeYqyzBueiness(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String busIds = request.getParameter("busIds");
        if (StringUtils.isNotEmpty(busIds)) {
            String[] ids = busIds.split(",");
            for (String id : ids) {
                callYqyzService.remove("T_CKBS_YQYZQH_BUS", new String[] { "BUS_ID" }, new Object[] { id });
            }
        }
        j.setMsg("解绑业务成功");
        return j;
    }

    /**
     * 描述:删除业务
     *
     * @author Madison You
     * @created 2019/12/27 9:08:00
     * @param
     * @return
     */
    @RequestMapping(params = "multiYqyzDel")
    @ResponseBody
    public AjaxJson multiYqyzDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callYqyzService.remove("T_CKBS_YQYZQH", "YQYZQH_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 一企一证记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 描述:信息页面
     *
     * @author Madison You
     * @created 2019/12/27 9:15:00
     * @param
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String, Object> callYqyz = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            callYqyz = callYqyzService.getByJdbc("T_CKBS_YQYZQH", new String[] { "YQYZQH_ID" },
                    new Object[] { entityId });
        }
        callYqyz.put("PARENT_ID", parentId);
        callYqyz.put("PARENT_NAME", parentName);
        request.setAttribute("callYqyz", callYqyz);
        return new ModelAndView("callYqyz/yqyzqhpz/yqyzqhInfo");
    }

    /**
     * 描述:修改操作
     *
     * @author Madison You
     * @created 2019/12/27 9:16:00
     * @param
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("YQYZQH_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = callYqyzService.saveOrUpdateTreeData(parentId, variables, "T_CKBS_YQYZQH", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 一企一证配置类别记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 一企一证配置类别记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述:材料信息页面
     *
     * @author Madison You
     * @created 2019/12/27 13:03:00
     * @param
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
        String zzType = request.getParameter("zzType");

        Properties properties = FileUtil.readProperties("conf/config.properties");
        String serviceUrl = properties.getProperty("serviceUrl");

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
        request.setAttribute("zzType", zzType);
        request.setAttribute("serviceUrl", serviceUrl);
        Map<String, Object> itemCart  = AppUtil.getItemCart();
        if (null != itemCart) {
            request.setAttribute("itemNum", itemCart.size());
        } else {
            request.setAttribute("itemNum", 0);
        }
        if (materTypeList != null && materTypeList.size() > 0) {
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callYqyz/takeNo/chooseMaterMacW");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("RECORD_ID", "");
            map.put("BUSCLASS_NAME", "");
            materTypeList.add(map);
            request.setAttribute("materTypeList", materTypeList);
            request.setAttribute("materList", materList);
            return new ModelAndView("callYqyz/takeNo/chooseMaterMacW");
        }
    }

    /**
     * 描述:事项列表页面
     *
     * @author Madison You
     * @created 2019/12/27 13:03:00
     * @param
     * @return
     */
    @RequestMapping("/toYqyzItemCartMacW")
    public ModelAndView toYqyzItemCartMacW(HttpServletRequest request) {
        String zzType = request.getParameter("zzType");
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
        request.setAttribute("zzType", zzType);
        return new ModelAndView("callYqyz/takeNo/yqyzItemCartMacW");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 13:12:00
     * @param
     * @return
     */
    @RequestMapping("/toYqyzItemCart")
    public ModelAndView toYqyzItemCart(HttpServletRequest request) {
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
        return new ModelAndView("callYqyz/takeNo/yqyzItemCart");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 13:02:00
     * @param
     * @return
     */
    @RequestMapping("/toYqyzBusinessItemChoose")
    public ModelAndView toYqyzBusinessItemChoose(HttpServletRequest request) {
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

        return new ModelAndView("callYqyz/takeNo/chooseYqyzBusinessItem");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/27 13:15:00
     * @param
     * @return
     */
    @RequestMapping("/yqyzReadCard")
    public ModelAndView yqyzReadCard(HttpServletRequest request) {
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        // 事项列表不能为空
        if (null != itemCart && itemCart.size() > 0) {
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            request.setAttribute("itemMap", itemMap);
        }
        return new ModelAndView("callYqyz/takeNo/yqyzReadCard");
    }

    /**
     * 描述:一企一证生成取号结果
     *
     * @author Madison You
     * @created 2019/12/27 14:12:00
     * @param
     * @return
     */
    @RequestMapping("/yqyzGenerateNo")
    @ResponseBody
    public AjaxJson yqyzGenerateNo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
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
            String zzType = request.getParameter("zzType");
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
            } else if ((boolean) lostPromiseMap.get("isLostPromise")) {
                j.setMsg((String) lostPromiseMap.get("msg"));
                j.setSuccess(false);
            } else {
                variables.put("IS_YCTB", 0);// 是否一窗通办（0：否，1：是）
                variables.put("IS_ITEM_CHOOSE", 0);// 是否选择事项取号（0：否，1：是）,非一窗通办必须为0
                /*证照合办绑定唯一一个业务*/
                if (zzType != null && zzType.equals("zzhb")) {
                    String zzhbBusinessCode = dictionaryService.getDicCode("YQYZJBPZ", "zzhbBusinessCode");
                    variables.put("BUSINESS_CODE", zzhbBusinessCode);
                } else {
                    variables.put("BUSINESS_CODE", businessCode);
                }
                variables.put("LINE_ZJLX", zjlx);
                variables.put("LINE_NAME", lineName);
                variables.put("LINE_CARDNO", lineCardNo);
                variables.put("LINE_ADDRESS", address);
                if (StringUtils.isEmpty(roomNo)) {
                    roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[]{"BUSINESS_CODE"},
                            new Object[]{businessCode}).get("BELONG_ROOM").toString();
                }
                if (StringUtils.isNotEmpty(businessCode)) {
                    roomNo = newCallService.getByJdbc("T_CKBS_BUSINESSDATA", new String[]{"BUSINESS_CODE"},
                            new Object[]{businessCode}).get("BELONG_ROOM").toString();
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
                String recordId = "";
                //取号并发问题
                String synBusinessCode = "takeNo" + businessCode;
                synchronized (synBusinessCode) {
                    variables = newCallService.getLineNo(variables);
                    recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", null);
                }
                String careWinNo = "";
                /*证照合办绑定唯一业务编码*/
                if (zzType != null && zzType.equals("zzhb")) {
                    careWinNo = newCallService.getCareWin("500");
                } else {
                    careWinNo = newCallService.getCareWin(businessCode);
                }
                //如果属于A、B厅，那么返回大厅的等待人数
                int waitNum = 0;
                int businessWaitNum = 0;
                if ("A".equals(roomNo) || "B".equals(roomNo) || "D".equals(roomNo)) {
                    waitNum = newCallService.getWaitingNumByRoomNo(roomNo);
                    businessWaitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
                } else {
                    waitNum = newCallService.getWaitingNumByBusinessCode(businessCode);
                }
                Map<String, Object> line = new HashMap<String, Object>();
                line.put("lineNo", variables.get("LINE_NO"));
                line.put("waitNum", waitNum);
                line.put("winNo", careWinNo);
                line.put("roomNo", roomNo);
                line.put("businessWaitNum", businessWaitNum);
                
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
                    } else {
                        appointment.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    }

                    newCallService.saveOrUpdate(appointment, "T_CKBS_APPOINTMENT_APPLY", appointmentId);
                }
                // 把事项添加到子表
                Map<String, Object> itemDetail = null;
                Map<String, Object> map = null;
                for (Map.Entry<String, Object> entry : itemCart.entrySet()) {
                    map = (Map<String, Object>) entry.getValue();
                    itemDetail = new HashMap<String, Object>();
                    itemDetail.put("RECORD_ID", recordId);
                    itemDetail.put("SUBBUS_CLASS", map.get("materTypeId"));
                    itemDetail.put("ITEM_CODE", map.get("itemCode"));
                    itemDetail.put("BUSINESS_CODE", map.get("businessCode"));
                    newCallService.saveOrUpdate(itemDetail, "T_CKBS_QUEUERECORD_ITEMDETAIL", null);
                }

                //小程序消息推送
                if (zjlx.equals("SF")) {
                    String businessName = newCallService.getByJdbc("T_CKBS_BUSINESSDATA",
                            new String[]{"BUSINESS_CODE"}, new Object[]{businessCode}).get("BUSINESS_NAME")
                            .toString();
                    Map<String, Object> lineInfo = new HashMap<String, Object>();
                    lineInfo.put("subjectInfo", "平潭综合实验区行政服务中心");
                    lineInfo.put("lineNo", variables.get("LINE_NO"));
                    lineInfo.put("time", variables.get("CREATE_TIME"));
                    lineInfo.put("queueInfo", roomNo + "区等候人数：" + waitNum);
                    lineInfo.put("itemInfo", businessName + "等候人数：" + (businessWaitNum == 0 ? "" : businessWaitNum));
                    lineInfo.put("remark", "请留意" + roomNo + "区" + careWinNo + "号窗口");
                    Map<String, Object> businessInfo = new HashMap<String, Object>();
                    businessInfo.put("lineCardNo", lineCardNo);
                    businessInfo.put("lineInfoJson", JSON.toJSONString(lineInfo));
                    sendSceneNotify(businessInfo);
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
    * 描述:发送取号信息到小程序
    *
    * @author Madison You
    * @created 2019/12/27 14:21:00
    * @param
    * @return
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
     * 描述:一企一证取号事项添加
     *
     * @author Madison You
     * @created 2019/12/27 15:02:00
     * @param
     * @return
     */
    @RequestMapping("/addYqyzItemCart")
    @ResponseBody
    public AjaxJson addYqyzItemCart(HttpServletRequest request) {
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
                    for (Map.Entry<String, Object> entry : itemCart.entrySet()) {
                        map = (Map<String, Object>) entry.getValue();
                        String bcode = map.get("businessCode").toString();
                        // 判断是否能同时取号
                        if (callYctbController.comparisonCode("1", businessCode) !=
                                callYctbController.comparisonCode("1", bcode)) {
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
     * 描述:移除事项
     *
     * @author Madison You
     * @created 2019/12/27 15:51:00
     * @param 
     * @return 
     */
    @RequestMapping("/removeYqyzItemCart")
    @ResponseBody
    public AjaxJson removeYqyzItemCart(HttpServletRequest request) {
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
     * 描述:读卡页面
     *
     * @author Madison You
     * @created 2019/12/27 16:23:00
     * @param 
     * @return 
     */
    @RequestMapping("/yqyzReadCardWhite")
    public ModelAndView yqyzReadCardWhite(HttpServletRequest request) {
        // 获取取号事项列表
        Map<String, Object> itemCart = AppUtil.getItemCart();
        String zzType = request.getParameter("zzType");
        // 事项列表不能为空
        if (null != itemCart && itemCart.size() > 0) {
            // 获取事项列表中的第一个主事项添加到主表中，其他事项添加到子表
            Map<String, Object> itemMap = (Map<String, Object>) getFirstOrNull(itemCart);
            request.setAttribute("itemMap", itemMap);
        }
        request.setAttribute("zzType", zzType);
        return new ModelAndView("callYqyz/takeNo/yqyzReadCardWhite");
    }

    /**
     * 描述:检查是否选择营业执照和许可证件两种业务
     *
     * @author Madison You
     * @created 2019/12/30 15:16:00
     * @param
     * @return
     */
    @RequestMapping("/checkYqyzItem")
    @ResponseBody
    public Map<String, Object> checkYqyzItem(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        Map<String, Object> itemCart = AppUtil.getItemCart();
        HashMap<String, Object> returnMap = new HashMap<>();
        StringBuffer busStr = new StringBuffer();
        for (Map.Entry<String, Object> entry : itemCart.entrySet()) {
            Map<String,Object> itemMap = (Map<String,Object>)entry.getValue();
            busStr.append((String) itemMap.get("businessCode"));
            busStr.append(",");
        }
        filter.addFilter("Q_B.BUSINESS_CODE_IN", busStr.toString());
        boolean flag = callYqyzService.findBusinessCatalog(filter);
        returnMap.put("flag", flag);
        return returnMap;
    }

    /**
     * 描述:判定事项数量
     *
     * @author Madison You
     * @created 2020/1/9 16:53:00
     * @param
     * @return
     */
    @RequestMapping("/countYqyzItemNum")
    @ResponseBody
    public Map<String, Object> countYqyzItemNum(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> itemCart = AppUtil.getItemCart();
        HashMap<String, Object> returnMap = new HashMap<>();
        int size = itemCart.size();
        boolean flag = true;
        if (size > 1) {
            flag = false;
        }
        returnMap.put("flag", flag);
        return returnMap;
    }

    /**
     * 描述:一企一证服务指南页面
     *
     * @author Madison You
     * @created 2020/1/2 9:50:00
     * @param
     * @return
     */
    @RequestMapping("/toYqyzItemDetail")
    public ModelAndView toYqyzItemDetail(HttpServletRequest request) {
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String defKey = request.getParameter("defKey");
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String businessCode = request.getParameter("businessCode");
        String businessName = request.getParameter("businessName");
        String departId = request.getParameter("departId");
        String zzType = request.getParameter("zzType");

        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("defKey", defKey);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("businessName", businessName);
        request.setAttribute("departId", departId);
        request.setAttribute("zzType", zzType);

        String entityId = "";
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(itemCode) && !itemCode.equals("undefined")) {
            serviceItem = callYqyzService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });
            entityId = (String) serviceItem.get("ITEM_ID");
            Map<String, Object> typeItem = busTypeService.getIdAndNamesByItemId(entityId);
            serviceItem.put("BUS_TYPEIDS", typeItem.get("TYPE_IDS"));
            serviceItem.put("BUS_TYPENAMES", typeItem.get("TYPE_NAMES"));
            String papersub = serviceItem.get("papersub")==null?"":serviceItem.get("papersub").toString();
            if (StringUtils.isNotEmpty(papersub)) {
                papersub = dictionaryService.findByDicCodeAndTypeCode(papersub, "paperMaterSub");
            }
            serviceItem.put("papersubString", papersub);
            String sxxz = dictionaryService.findByDicCodeAndTypeCode(serviceItem.get("SXXZ").toString(),
                    "ServiceItemXz");
            serviceItem.put("SXXZ", sxxz);
            String sxsx = dictionaryService.findByDicCodeAndTypeCode(serviceItem.get("FTA_FLAG").toString(),"isFta");
            serviceItem.put("sxsx", sxsx);
            String sffs = serviceItem.get("SFFS")==null?"":serviceItem.get("SFFS").toString();
            if(StringUtils.isNotEmpty(sffs)){
                sffs = dictionaryService.findByDicCodeAndTypeCode(sffs,"ChargeType");
            }
            serviceItem.put("sffs", sffs);
            String finishType = serviceItem.get("FINISH_TYPE")==null?"":serviceItem.get("FINISH_TYPE").toString();
            if(StringUtils.isNotEmpty(finishType)){
                finishType = dictionaryService.findByDicCodeAndTypeCode(serviceItem.get("FINISH_TYPE").toString(),
                        "FinishType");
            }
            serviceItem.put("finishType", finishType);
            String finishGettype = serviceItem.get("FINISH_GETTYPE") == null ? ""
                    : serviceItem.get("FINISH_GETTYPE").toString();
            if(StringUtils.isNotEmpty(finishGettype)){
                finishGettype = dictionaryService
                        .findByDicCodeAndTypeCode(serviceItem.get("FINISH_GETTYPE").toString(), "FinishGetType");
            }
            serviceItem.put("finishGettype", finishGettype);
            String rzbsdtfs = serviceItem.get("RZBSDTFS")==null?"":serviceItem.get("RZBSDTFS").toString();
            if(StringUtils.isNotEmpty(rzbsdtfs)){
                sffs = dictionaryService.findByDicCodeAndTypeCode(rzbsdtfs,"RZBSDTFS");
            }
            serviceItem.put("rzbsdtfs", rzbsdtfs);
            serviceItem = setCkcsInfo(serviceItem);

            List<Map<String, Object>> busTypenames = busTypeService.getParentNamesAndNamesByItemId(entityId);
            serviceItem.put("busTypenames", busTypenames);
            //以省网办编码为主
            if(serviceItem.get("SWB_ITEM_CODE")!=null){
                String SWB_ITEM_CODE=serviceItem.get("SWB_ITEM_CODE").toString();
                serviceItem.put("CODE", SWB_ITEM_CODE);
            }else{
                serviceItem.put("CODE",serviceItem.get("ITEM_CODE"));
            }
            // 获取所属目录
            String catalogCode = (String) serviceItem.get("CATALOG_CODE");
            if (StringUtils.isNotEmpty(catalogCode)) {
                Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
                serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
            }
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(entityId,null);
            serviceItem.put("applyMaters", applyMaters);

            serviceItem = setLcsz(entityId,request,serviceItem);
        }
        
        //法定时限
        setServiceItemFDSX(serviceItem);
        qckcs(serviceItem);
        //特殊环节
        String tshj=getTshj(entityId);
        serviceItem.put("tshj",tshj);
        //办理流程
        String defId = serviceItem.get("BDLCDYID")==null?"":serviceItem.get("BDLCDYID").toString();
        List<Map<String, Object>> mapped = flowMappedService.getByDefidAndNodeName(defId);
        StringBuffer bllc=new StringBuffer();
        for (Map<String, Object> map : mapped) {
            if(StringUtils.isNotEmpty(bllc)){
                bllc.append(" → ");
            }
            bllc.append(map.get("YS_NAME"));
        }
        serviceItem.put("BLLC", bllc);
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("departId", departId);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("zzType", zzType);
        return new ModelAndView("callYqyz/takeNo/yqyzItemDetail");
    }

    /*
     *去现场次数
     */
    private void qckcs(Map<String, Object> serviceItem) {
        String ckcs1 = serviceItem.get("CKCS_1")==null?
                "":serviceItem.get("CKCS_1").toString();
        String ckcs2 = serviceItem.get("CKCS_2")==null?
                "":serviceItem.get("CKCS_2").toString();
        String ckcs3 = serviceItem.get("CKCS_3")==null?
                "":serviceItem.get("CKCS_3").toString();
        String ckcs4 = serviceItem.get("CKCS_4")==null?
                "":serviceItem.get("CKCS_4").toString();
        StringBuffer sbfsqxccs=new StringBuffer();
        if (StringUtils.isNotEmpty(ckcs1)) {
            if (ckcs1.equals("-1")) {
                sbfsqxccs.append("窗口申请，去现场次数未承诺。 ");
            }else {
                sbfsqxccs.append("窗口申请，去现场最多跑");
                sbfsqxccs.append(ckcs1);
                sbfsqxccs.append("趟。 ");
            }
        }
        if (ckcs2.equals("-1") && ckcs3.equals("-1") && ckcs4.equals("-1")) {
            sbfsqxccs.append("网上申请，去现场次数未承诺。");
        } else {
            if (StringUtils.isNotEmpty(ckcs2)&&!ckcs2.equals("-1")) {
                sbfsqxccs.append("网上申请，去现场最多跑");
                sbfsqxccs.append(ckcs2);
                sbfsqxccs.append("趟。");
            } else if (StringUtils.isNotEmpty(ckcs3)&&!ckcs3.equals("-1")) {
                sbfsqxccs.append("网上申请，去现场最多跑");
                sbfsqxccs.append(ckcs3);
                sbfsqxccs.append("趟。");
            } else if (!ckcs4.equals("0") && StringUtils.isNotEmpty(ckcs4)) {
                sbfsqxccs.append("网上申请，去现场最多跑");
                sbfsqxccs.append(ckcs4);
                sbfsqxccs.append("趟。");
            } else if (StringUtils.isNotEmpty(ckcs4) && ckcs4.equals("0")) {
                sbfsqxccs.append("网上申请，一趟不用跑。");
            }
        }
        serviceItem.put("SBFSQXCCS", sbfsqxccs);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:13:00
     * @param 
     * @return 
     */
    public String getTshj(String itemId){
        StringBuffer sbTS=new StringBuffer();
        String tshj="";
        String link_limittime="";
        List<Map<String, Object>> tshjList = departmentService.getAllByJdbc
                ("T_WSBS_SERVICEITEM_LINK", new String[]{"ITEM_ID"}, new Object[]{itemId});
        if(tshjList.size()>0){
            for (int i = 0; i < tshjList.size(); i++) {
                int j=i+1;
                sbTS.append("    特殊环节"+j+"");
                sbTS.append("&nbsp;&nbsp;&nbsp;");
                sbTS.append("      名称： ");
                sbTS.append(tshjList.get(i).get("LINK_NAME"));
                sbTS.append("&nbsp;&nbsp;&nbsp;");
                sbTS.append("      时限： ");
                link_limittime=tshjList.get(i).get("LINK_LIMITTIME")==null?"":
                        tshjList.get(i).get("LINK_LIMITTIME").toString();
                if("0".equals(link_limittime)){
                    link_limittime="无";
                    sbTS.append(link_limittime);
                }else{
                    sbTS.append(link_limittime+" 个工作日");
                }
                sbTS.append("&nbsp;&nbsp;&nbsp;");
                sbTS.append("      设定依据： ");
                sbTS.append(tshjList.get(i).get("LINK_BASIS"));
                sbTS.append("</br>");
            }
            tshj=sbTS.toString();
        }else{
            tshj="  无";
        }
        return tshj;
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
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:13:00
     * @param 
     * @return 
     */
    private Map<String,Object> setLcsz(String entityId,HttpServletRequest request,Map<String,Object> serviceItem){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.NODE_KEY","asc");
        String itemId = entityId;
        if(StringUtils.isEmpty(itemId)||itemId.equals("undefined")){
            itemId = request.getParameter("Q_T.ITEM_ID_EQ");
        }
        if(StringUtils.isNotEmpty(itemId)){
            filter.addFilter("Q_T.ITEM_ID_=",itemId);
            filter.addSorted("T.RECORD_ID","asc");
            List<Map<String, Object>> list = departServiceItemService.getDefNode(filter);
            List<Map<String, Object>> resultList =  new ArrayList<Map<String,Object>>();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                String nodeName = map.get("NODE_NAME")==null?
                        "":map.get("NODE_NAME").toString();
                if ("开始".equals(nodeName)
                        ||"结束".equals(nodeName)
                        ||"待受理".equals(nodeName)
                        ||"网上预审".equals(nodeName)
                        ||"受理自动跳转".equals(nodeName)) {

                }else{
                    resultList.add(map);
                }
            }
            if (resultList.size() == 2) {
                resultList.get(1).put("NODE_NAME", "审查与决定");
            }else if (resultList.size() == 3) {
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
            }else if (resultList.size() == 4) {
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
                resultList.get(3).put("NODE_NAME", "制证与送达");
            }else if (resultList.size() == 5) {
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
                resultList.get(3).put("NODE_NAME", "制证");
                resultList.get(4).put("NODE_NAME", "送达");
            }
            List<Map<String, Object>> nodeInfos = resultList;
            serviceItem.put("nodeInfos", nodeInfos);
        }
        return serviceItem;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:12:00
     * @param 
     * @return 
     */
    public void setServiceItemFDSX(Map<String, Object> serviceItem) {
        String FDSXGZR = "";
        if (null != serviceItem.get("FDSXGZR") && !"".equals(serviceItem.get("FDSXGZR"))) {
            FDSXGZR = serviceItem.get("FDSXGZR").toString();
            if (FDSXGZR.equals("-1")) {
                FDSXGZR = "无";
            } else {
                String FDSXLX = "";
                if (null == serviceItem.get("FDSXLX") || "".equals(serviceItem.get("FDSXLX"))) {
                    FDSXLX = "个工作日";
                } else {
                    FDSXLX = serviceItem.get("FDSXLX").toString();
                    if (FDSXLX.equals("y")) {
                        FDSXLX = "个月";
                    } else if (FDSXLX.equals("zrr")) {
                        FDSXLX = "个自然日";
                    } else if (FDSXLX.equals("gzr")) {
                        FDSXLX = "个工作日";
                    }
                }
                FDSXGZR = FDSXGZR + FDSXLX;
            }
        } else {
            FDSXGZR = "无";
        }
        if("0".equals(serviceItem.get("FDSXGZR").toString().trim())){
            FDSXGZR="当日办结";
        }
        serviceItem.put("FDSXGZR", FDSXGZR);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/1/2 11:11:00
     * @param 
     * @return 
     */
    private Map<String,Object> setCkcsInfo(Map<String,Object> serviceItem){
        String ckcs_1 = serviceItem.get("ckcs_1")==null?
                "":serviceItem.get("ckcs_1").toString();
        String ckcs_2 = serviceItem.get("ckcs_2")==null?
                "":serviceItem.get("ckcs_2").toString();
        String ckcs_3 = serviceItem.get("ckcs_3")==null?
                "":serviceItem.get("ckcs_3").toString();
        String ckcs_4 = serviceItem.get("ckcs_4")==null?
                "":serviceItem.get("ckcs_4").toString();
        StringBuffer ckcString = new StringBuffer("");
        if (StringUtils.isNotEmpty(ckcs_1)&&!ckcs_1.equals("-1")) {
            ckcString.append("行政服务中心窗口受理");
            ckcString.append(ckcs_1);
            ckcString.append("次；");
        }
        if (StringUtils.isNotEmpty(ckcs_2)&&!ckcs_2.equals("-1")) {
            ckcString.append("网上申请和预审，窗口纸质材料收件受理");
            ckcString.append(ckcs_2);
            ckcString.append("次；");
        }
        if (StringUtils.isNotEmpty(ckcs_3)&&!ckcs_3.equals("-1")) {
            ckcString.append("网上申请、预审和受理，窗口纸质核验办结");
            ckcString.append(ckcs_3);
            ckcString.append("次；");
        }
        if (StringUtils.isNotEmpty(ckcs_4)&&!ckcs_4.equals("-1")) {
            ckcString.append("提供全流程网上办理，申请人在办结后到窗口领取结果");
            ckcString.append(ckcs_4);
            ckcString.append("次；");
        }
        serviceItem.put("ckcString", ckcString);
        return serviceItem;
    }

}
