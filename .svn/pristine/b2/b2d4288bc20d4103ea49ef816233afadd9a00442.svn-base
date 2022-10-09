/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.SwbItemDataService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.SendTaskNoticeService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.wsbs.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 描述 服务事项Controller
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/serviceItemController")
public class ServiceItemController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ServiceItemController.class);
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * sendTaskNoticeService
     */
    @Resource
    private SendTaskNoticeService sendTaskNoticeService;
    /**
     * flowMappedService
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private CommonProblemService commonProblemService;
    
    /**
     * 引入Service
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
    /**
     * 引入Service
     */
    @Resource
    private SwbItemDataService swbItemDataService;
    
    /**
     * 
     * 描述 接收事项选择器
     * @author Kester Chen
     * @created 2018-4-20 上午11:23:26
     * @param request
     * @return
     */
    @RequestMapping("/swbRecSelector")
    public ModelAndView catalogSelector(HttpServletRequest request) {
        return new ModelAndView("wsbs/departServiceItem/swbRecSelector");
    }
    /**
     * 
     * 描述   获取未接收事项清单
     * @author Kester Chen
     * @created 2018-4-20 上午11:28:36
     * @param request
     * @param response
     */
    @RequestMapping(params="swbRecDatas")
    public void catalogForBind(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = serviceItemService.swbRecDatas(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

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
        serviceItemService.remove("T_WSBS_SERVICEITEM", "ITEM_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 服务事项记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 方法del for cancel
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelCan")
    @ResponseBody
    public AjaxJson multiDelCan(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        List<Map<String, Object>> list=serviceItemService.findByItemIds(selectColNames);
        if(list!=null&&list.size()>0){
            j.setSuccess(false);
            j.setMsg("要删除的服务事项存在对应的办件，禁止删除");
        }else{
            serviceItemService.remove("T_WSBS_SERVICEITEM", "ITEM_ID", selectColNames.split(","));
            sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 服务事项记录", SysLogService.OPERATE_TYPE_DEL);
            j.setMsg("删除成功");
        }
        return j;
    }
    /**
     * 办事指南跳转
     *
     * @param request
     * @return
     */
    @RequestMapping("/itemDetail")
    public void itemDetail(HttpServletRequest request,HttpServletResponse  response) {
        String itemCode = request.getParameter("ITEM_CODE");
        Map<String,Object> serviceItem=serviceItemService.getItemInfoByItemCode(itemCode);
        String json = JSON.toJSONString(serviceItem);
        this.setJsonString(json, response);
    }
    /**
     * 办事指南跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping("/bsznDetail")
    public ModelAndView bsznDetail(HttpServletRequest request) {
        SqlFilter filter = new SqlFilter(request);
        String itemCode = request.getParameter("itemCode");
        String projectType = request.getParameter("projectType");//项目工程类型，该类型为1时，办事指南信息界面不显示在线办理按钮
        Map<String, Object> serviceItem = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(itemCode) && !itemCode.equals("undefined")) {
            serviceItem = getServiceItem(itemCode);

        }
        String itemId = "";
        List<Map<String,Object>> specialLink = new ArrayList<Map<String,Object>>();
        if(null != serviceItem){
            itemId = (String)serviceItem.get("ITEM_ID");
            if(StringUtils.isNotEmpty(itemId)){
                specialLink = departServiceItemService.getSpecialLink(itemId);
            }
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
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查与决定");
            }else if (resultList.size() == 3) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
            }else if (resultList.size() == 4) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
                resultList.get(3).put("NODE_NAME", "制证与送达");
            }else if (resultList.size() == 5) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
                resultList.get(3).put("NODE_NAME", "制证");
                resultList.get(4).put("NODE_NAME", "送达");
            }
            serviceItem.put("HJMXS", resultList);
        }
        //以省网办编码为主
        if(serviceItem.get("SWB_ITEM_CODE")!=null&&
                serviceItem.get("SWB_ITEM_CODE")!=""){
            String code=serviceItem.get("SWB_ITEM_CODE").toString();
            serviceItem.put("CODE", code);
        }else{
            serviceItem.put("CODE", serviceItem.get("ITEM_CODE").toString());
        }
        request.setAttribute("specialLink", specialLink);
        setServiceItemFDSX(serviceItem);
        getXJDetail(serviceItem);
        if(serviceItem.get("AGENCYSERVICE_ID")!=null){
//            String AGENCYSERVICE_NAME = serviceItemService.getByJdbc("T_SMOGA_AGENCYSERVICE",
//                    new String[] { "SERVICE_ID" }, new Object[] { serviceItem.get("AGENCYSERVICE_ID") })
//                    .get("AGENCY_ITEM_NAME").toString();
            Map<String, Object> agenMap = serviceItemService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                    new String[] { "SERVICE_ID" }, new Object[] { serviceItem.get("AGENCYSERVICE_ID") });
            if (agenMap!=null) {
                String AGENCYSERVICE_NAME = agenMap.get("AGENCY_ITEM_NAME")==null?
                        "":agenMap.get("AGENCY_ITEM_NAME").toString();
                serviceItem.put("AGENCYSERVICE_NAME", AGENCYSERVICE_NAME);
            }
        }
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("typeName", "bszn");
        request.setAttribute("cnType", "serviceItem");
        String uuid= UUIDGenerator.getUUID();
        HttpSession session=request.getSession();
        session.setAttribute("GSRF",uuid);
        request.setAttribute("GSRF",uuid);
        if(projectType!=null && !"".equals(projectType)) {
            request.setAttribute("projectType", projectType);
        }
        return new ModelAndView("website/bszn/bsznDetail");
    }
    /**
     * 
     * 描述：获取
     * @author Water Guo
     * @created 2017-12-18 下午4:36:11
     * @param serviceItem
     */
    private void getXJDetail(Map<String,Object> serviceItem){
        String ckcs1=(String)serviceItem.get("CKCS_1")==null?"":
            serviceItem.get("CKCS_1").toString();
        String ckcs2=serviceItem.get("CKCS_2")==null?"":
            serviceItem.get("CKCS_2").toString();
        String ckcs3=(String)serviceItem.get("CKCS_3")==null?"":
            serviceItem.get("CKCS_3").toString();
        String ckcs4=(String)serviceItem.get("CKCS_4")==null?"":
            serviceItem.get("CKCS_4").toString();
        String rzbsdtfs=(String)serviceItem.get("RZBSDTFS");
        //几星级
        String xj="";
        //网上申请，去窗口次数跑几次
        String nTime="0";
        if(StringUtils.isNotEmpty(ckcs2)&&!("-1").equals(ckcs2)){
            xj="三星";
            nTime=ckcs2;
        }else if(StringUtils.isNotEmpty(ckcs3)&&!("-1").equals(ckcs3)){
            xj="四星";
            nTime=ckcs3;
        }else if(StringUtils.isNotEmpty(ckcs4)&&!("-1").equals(ckcs4)){
            xj="五星";
            nTime=ckcs4;
        }
        String SFGCJSXM =serviceItem.get("SFGCJSXM")==null?"":
            serviceItem.get("SFGCJSXM").toString();
        if(!SFGCJSXM.equals("1")) {
            if("in01".equals(rzbsdtfs)){
                xj="一星";
            }else if("in02".equals(rzbsdtfs)){
                xj="二星";
            }
        }

        if("-1".equals(nTime)){
           serviceItem.put("NTIME", "未明确");
        }else{
            serviceItem.put("NTIME","跑"+nTime+"趟");
        }
        if(StringUtils.isEmpty(ckcs1)){
            serviceItem.put("CTIME", "未明确");
         }else{
             serviceItem.put("CTIME", "跑"+ckcs1+"趟");
         }
        serviceItem.put("XJ",xj);
    }
    /**
     * 
     * 描述：获取最大星级
     * @author Water Guo
     * @created 2017-12-18 下午3:55:56
     * @param ckcs2
     * @param ckcs3
     * @param ckcs4
     * @return
     */
    private String getMaxckcs(String ckcs2,String ckcs3,String ckcs4){
        if(StringUtils.isNotEmpty(ckcs2)){
            return "ckcs2";
        }else if(StringUtils.isNotEmpty(ckcs3)){
            return "ckcs3";
        }else if(StringUtils.isNotEmpty(ckcs4)){
            return "ckcs4";
        }else{
            return "";
        }
    }
    /**
     * 
     * 描述 手机App办事指南入口
     * @author Rider Chen
     * @created 2016年1月27日 下午5:12:17
     * @param request
     * @param response
     */
    @RequestMapping("/bsznAppDetail")
    public void bsznAppDetail(HttpServletRequest request, HttpServletResponse response) {
        String itemCode = request.getParameter("itemCode");
        Map<String, Object> serviceItem = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(itemCode) && !itemCode.equals("undefined")) {
            serviceItem = getServiceItem(itemCode);
        }

        String json = JSON.toJSONString(serviceItem);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述  获取办事详细信息
     * @author Rider Chen
     * @created 2016年1月27日 下午5:09:27
     * @param itemCode
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getServiceItem(String itemCode) {
        Map<String, Object> serviceItem;
        serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new Object[] { itemCode });
//        Map<String, Object> catalogInfo = serviceItemService.getByJdbc(
//                "T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_CODE" },
//                new Object[] { serviceItem.get("CATALOG_CODE") });
//        serviceItem.put("CATALOG_NAME", catalogInfo.get("CATALOG_NAME"));
        serviceItem.put("CATALOG_NAME", StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME"));
        if (null != serviceItem) {
            Iterator i = serviceItem.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry e = (Map.Entry) i.next();
                if (null == e.getValue()) {// 等于null转换为""
                    serviceItem.put(e.getKey().toString(), "");
                }
            }
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // 获取材料信息列表
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId,null);
            // 获取材料业务办理子项分类列表
            List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeList(itemId);
            serviceItem.put("applyMaters", applyMaters);
            serviceItem.put("handleTypes", handleTypes);
            String papersub =serviceItem.get("PAPERSUB")==null?
                    "":serviceItem.get("PAPERSUB").toString();
            papersub=papersub.replace("1", "窗口收取");
            papersub=papersub.replace("2", "邮递收取");
            serviceItem.put("PAPERSUB",papersub);
            
            // 办件类型（取字典类别:ServiceItemType)
            serviceItem.put("SXLX",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SXLX"), "ServiceItemType"));
            // 事项性质(取字典类别: ServiceItemXz)
            serviceItem.put("SXXZ",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SXXZ"), "ServiceItemXz"));
            // 是否收费(取字典类别 YesOrNo)
            serviceItem.put("SFSF",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SFSF"), "YesOrNo"));
            // 收费方式(取字典类别: ChargeType)
            serviceItem.put("SFFS",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SFFS"), "ChargeType"));
            // 办理结果(取字典类别: FinishType)
            serviceItem.put("FINISH_TYPE",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("FINISH_TYPE"), "FinishType"));
            // 结果获取方式(取字典类别: FinishGetType)
            serviceItem.put("FINISH_GETTYPE", dictionaryService.findByDicCodeAndTypeCode(
                    (String) serviceItem.get("FINISH_GETTYPE"), "FinishGetType"));
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "DIC_CODE" }, new Object[] { serviceItem.get("ITEM_CODE") });
            if (null != dictionary) {
                String dicName = dictionary.get("DIC_NAME").toString();
                if (StringUtils.isNotEmpty(dicName)) {
                    String[] dicNames = dicName.split(",");
                    serviceItem.put("TZLX", dictionary.get("TYPE_CODE"));
                    if (dicNames.length == 2) {
                        serviceItem.put("DQJD", Integer.parseInt(dicNames[0]));
                        serviceItem.put("DQLC", Integer.parseInt(dicNames[1]));
                    }
                }
            }
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_CODE" }, new Object[] { (String) serviceItem.get("SSBMBM") });
            serviceItem.put("SSBMBM", department.get("DEPART_NAME"));
            serviceItem.put("deptId", department.get("DEPART_ID"));

            Map<String, Object> e = busTypeService.getIdAndNamesByItemId(itemId);
            String busTypenames = "";
            String typeids = e.get("TYPE_IDS").toString();
            if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                    || typeids.contains("4028818c512273e70151227569a40001")
                    || typeids.contains("4028818c512273e70151229ae7e00003")
                    || typeids.contains("4028818c512273e7015122a38a130004")) {
                busTypenames += "个人";
            }
            if (typeids.contains("4028818c512273e7015122a452220005")
                    || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                    || typeids.contains("4028818c512273e7015122c81f850007")
                    || typeids.contains("4028818c512273e7015122c872dc0008")) {
                if (busTypenames.length() > 1) {
                    busTypenames += ",";
                }
                busTypenames += "企业";
            }
            serviceItem.put("busTypenames", busTypenames);
        }
        return serviceItem;
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
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                    new Object[] { entityId });
            Map<String, Object> typeItem = busTypeService.getIdAndNamesByItemId(entityId);
            serviceItem.put("BUS_TYPEIDS", typeItem.get("TYPE_IDS"));
            serviceItem.put("BUS_TYPENAMES", typeItem.get("TYPE_NAMES"));
            Map<String, Object> userInfo = this.serviceItemService.getBindUserIdANdNames(entityId);
            serviceItem.put("PREAUDITER_IDS", userInfo.get("USER_IDS"));
            serviceItem.put("PREAUDITER_NAMES", userInfo.get("FULL_NAMES"));
            Map<String, Object> ticketsInfo = this.serviceItemService.getBindTicketsIdANdNames(entityId);
            serviceItem.put("TICKETS_IDS", ticketsInfo.get("TICKETS_IDS"));
            serviceItem.put("TICKETS_NAMES", ticketsInfo.get("TICKETS_NAMES"));
            Map<String, Object> documentInfo = this.serviceItemService.getBindDocumentIdANdNames(entityId);
            serviceItem.put("DOCUMENT_IDS", documentInfo.get("DOCUMENT_IDS"));
            serviceItem.put("DOCUMENT_NAMES", documentInfo.get("DOCUMENT_NAMES"));
            Map<String, Object> readInfo = this.serviceItemService.getBindReadIdANdNames(entityId);
            serviceItem.put("READ_IDS", readInfo.get("READ_IDS"));
            serviceItem.put("READ_NAMES", readInfo.get("READ_NAMES"));
            Map<String, Object> serviceItemForm = this.serviceItemService.getBindFormIdAndName(entityId);
            serviceItem.put("BINDFORM_IDS", serviceItemForm.get("BINDFORM_IDS"));
            serviceItem.put("BINDFORM_NAMES", serviceItemForm.get("BINDFORM_NAMES"));
            // 获取绑定的流程定义ID
            String defId = (String) serviceItem.get("BDLCDYID");
            if (StringUtils.isNotEmpty(defId)) {
                serviceItem.put("BDLCDYNAME", flowDefService.getDefNames(defId));
            }
            // 获取所属目录
//            String catalogCode = (String) serviceItem.get("CATALOG_CODE");
//            if (StringUtils.isNotEmpty(catalogCode)) {
//                Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
//                serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
//            }
            serviceItem.put("CATALOG_NAME", StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME"));
        } else {
            serviceItem = new HashMap<String, Object>();
            serviceItem.put("FWDX", "1");
            serviceItem.put("SFSF", "0");
            serviceItem.put("FWSXMXLB", "1");
            serviceItem.put("RZBSDTFS", "in05");
        }
        //维护日志
        List<Map<String, Object>> uplist = serviceItemService.findLogByItemId(entityId,"1");
        serviceItem.put("uplogList", uplist);
        List<Map<String, Object>> fabulist = serviceItemService.findLogByItemId(entityId,"2");
        serviceItem.put("fabulogList", fabulist);
        List<Map<String, Object>> quxiaolist = serviceItemService.findLogByItemId(entityId,"3");
        List<Map<String, Object>> chexiaolist = serviceItemService.findLogByItemId(entityId,"-1");
        quxiaolist.addAll(chexiaolist);
        serviceItem.put("canclogList", quxiaolist);
        
        request.setAttribute("serviceItem", serviceItem);
        if (StringUtils.isNotEmpty(request.getParameter("shan")) && !request.getParameter("shan").equals("undefined")) {
            request.setAttribute("shan", request.getParameter("shan"));// 区别草稿中的修改与审核库中的修改
        }
        return new ModelAndView("wsbs/serviceitem/serviceiteminfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        String entityId = request.getParameter("ITEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        // 获取编码值
        String itemCode = request.getParameter("ITEM_CODE");
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isExists = serviceItemService.isExists(itemCode);
        if (StringUtils.isEmpty(entityId) && isExists) {
            result.put("success", false);
            result.put("msg", "事项编码重复,请修改");
        } else {
            // 获取所属目录
//            String catalogCode = (String) request.getParameter("CATALOG_CODE");
//            if (StringUtils.isNotEmpty(catalogCode)) {
//                Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
//                variables.put("SXXZ", (String) catalog.get("SXXZ"));
//                Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
//                        new String[] { "DEPART_ID" }, new Object[] { (String) catalog.get("DEPART_ID") });
//                variables.put("SSBMBM", (String) department.get("DEPART_CODE"));
//            }
            if (StringUtils.isEmpty(entityId)) {
                int maxSn = serviceItemService.getMaxSn();
                variables.put("C_SN", maxSn + 1);
            }else{
                variables.put("IS_PUBLISH_EDIT", 1);
            }
            variables.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Map<String,Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" },new Object[] {entityId});
            String currentStep=(String) variables.get("currentStep");
            if("1".equals(currentStep)){
                String oldSXLX="";
                if(serviceItem!=null){
                    oldSXLX=dictionaryService.findByDicCodeAndTypeCode(
                            (String) serviceItem.get("SXLX"), "ServiceItemType");
                }
                String newSXLX=dictionaryService.findByDicCodeAndTypeCode(
                        (String) variables.get("SXLX"), "ServiceItemType");
                if(!oldSXLX.equals(newSXLX)){
                    if("即办件".equals(newSXLX)){
                        variables.put("CNQXGZR", "0");
                    }
                }
                String IMPL_DEPART_ID = request.getParameter("IMPL_DEPART_ID");
                if (StringUtils.isNotEmpty(IMPL_DEPART_ID)) {
                    Map<String,Object> departMap = serviceItemService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                            new String[]{"DEPART_ID"}, new Object[]{IMPL_DEPART_ID});
                    if (departMap != null) {
                        variables.put("SSBMBM", StringUtil.getValue(departMap, "DEPART_CODE"));
                    }
                }
            }
            String recordId = serviceItemService.saveOrUpdateCascade(variables);
            
            /*记录日志*/
            if (StringUtils.isNotEmpty(entityId)) {
                sysLogService.saveLog("修改了ID为[" + entityId + "]的 服务事项记录", SysLogService.OPERATE_TYPE_EDIT);
                //添加维护记录
                StringBuffer str = new StringBuffer();
                if("1".equals(currentStep)){
                    currentStepOne(request, entityId, variables, serviceItem,result);
                    currentStepNine(request, entityId, variables, serviceItem);
                }else if("2".equals(currentStep)){
                    String oldtext=(String) variables.get("OLDRZBSDTFS_TEXT");
                    String newtext=(String) variables.get("RZBSDTFS_TEXT");
                    if(StringUtils.isNotEmpty(oldtext)&&!oldtext.equals(newtext)){
//                        str+="'"+oldtext+"'修改为'"+newtext+"'";
                        str.append(itemLogNote(oldtext, newtext));
                        saveItemLog(str.toString(),entityId,"1",request);
                    }else if(StringUtils.isEmpty(oldtext)){
//                        str+="'"+oldtext+"'修改为'"+newtext+"'";
                        str.append(itemLogNote(oldtext, newtext));
                        saveItemLog(str.toString(),entityId,"1",request);
                    }
                    saveItemLog("修改了申请方式",entityId,"1",request);
                }else if("3".equals(currentStep)){
                    str.append("时限配置部分");
                    String oldwork=serviceItem.get("CNQXGZR")!=null?serviceItem.get("CNQXGZR").toString():"";
                    String newwork=(String) variables.get("CNQXGZR");
                    String oldqx=(String) serviceItem.get("CNQXSM")!=null?(String) serviceItem.get("CNQXSM"):"";
                    String newqx=(String) variables.get("CNQXSM");
                    String oldlaw=(String) serviceItem.get("FDQX")!=null?(String) serviceItem.get("FDQX"):"";
                    String newlaw=(String) variables.get("FDQX");
                    if(!oldwork.equals(newwork)){
//                        str+="<br/>【承诺时限工作日】:'"+oldwork+"'修改为'"+newwork+"';";
                        str.append(itemLogNote("承诺时限工作日",oldwork, newwork));
                    }
                    if(!oldqx.equals(newqx)){
//                        str+="<br/>【承诺时限说明】:'"+oldqx+"'修改为'"+newqx+"';";
                        str.append(itemLogNote("承诺时限说明",oldqx, newqx));
                    }
                    if(!oldlaw.equals(newlaw)){
//                        str+="<br/>【法定时限说明】:'"+oldlaw+"'修改为'"+newlaw+"';";
                        str.append(itemLogNote("法定时限说明",oldlaw, newlaw));
                    }
                    saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
                }else if("4".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的办公指引部分",entityId,"1",request);
                }else if("5".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的申请材料部分",entityId,"1",request);
                }else if ("6".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的邮递服务部分",entityId,"1",request);
                }else if ("7".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的清单配置部分",entityId,"1",request);
                }else if ("8".equals(currentStep)){
                    currentStepTen(request, entityId, serviceItem);
                }else if("9".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的收费配置部分",entityId,"1",request);
                }else if("10".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的预审人员部分",entityId,"1",request);
                }else if("11".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的常见问题部分",entityId,"1",request);
                }else if ("12".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的其它配置部分",entityId,"1",request);
                }
            } else {
                sysLogService.saveLog("新增了ID为[" + recordId + "]的 服务事项记录", SysLogService.OPERATE_TYPE_ADD);
            }
            result.put("success", true);
            result.put("itemId", recordId);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    private void currentStepOne(HttpServletRequest request, String entityId, Map<String, Object> variables,
            Map<String, Object> serviceItem,Map<String, Object> result) {
        StringBuffer str = new StringBuffer("基本信息");
        String oldSXXZ=(String) serviceItem.get("ITEM_NAME");
        String newSXXZ=(String) variables.get("ITEM_NAME");
        String oldSXLX=dictionaryService.findByDicCodeAndTypeCode(
                (String) serviceItem.get("SXLX"), "ServiceItemType");
        String newSXLX=dictionaryService.findByDicCodeAndTypeCode(
                (String) variables.get("SXLX"), "ServiceItemType");
        String oldSsdw = (String) serviceItem.get("IMPL_DEPART");
        String newSsdw = (String) variables.get("IMPL_DEPART");
        String oldZbcs = (String) serviceItem.get("ZBCS");
        String newZbcs = (String) variables.get("ZBCS");

        if(!oldSXXZ.equals(newSXXZ)){
//            str.append("【事项名称】:'").append(oldSXXZ).append("'修改为'").append(newSXXZ).append("';");
            str.append(itemLogNote("事项名称", oldSXXZ, newSXXZ));
        }
        if(!oldSXLX.equals(newSXLX)){
//            str+="【办件类型】:'"+oldSXLX+"'修改为'"+newSXLX+"';";
            str.append(itemLogNote("办件类型", oldSXLX, newSXLX));
            if("即办件".equals(oldSXLX)){
                result.put("SXLXflag",2);
            }else if("即办件".equals(newSXLX)){
                result.put("SXLXflag",1);
            }
        }
        if (!Objects.equals(oldSsdw, newSsdw)) {
//            str+="【实施单位】:'"+oldSsdw+"'修改为'"+newSsdw+"';";
            str.append(itemLogNote("实施单位", oldSsdw, newSsdw));
        }
        if (!Objects.equals(oldZbcs, newZbcs)) {
//            str+="【主办处室】:'"+oldZbcs+"'修改为'"+newZbcs+"';";
            str.append(itemLogNote("主办处室", oldZbcs, newZbcs));
        }
        saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
    }
    private void currentStepTen(HttpServletRequest request, String entityId, Map<String, Object> serviceItem) {
        StringBuffer str = new StringBuffer("其他配置");
        String oldLXR=(String) serviceItem.get("BDLCDYID")!=null?(String) serviceItem.get("BDLCDYID"):"";
        Map<String,Object> newItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_ID" },new Object[] {entityId});
        String newLXR=(String) newItem.get("BDLCDYID");
        String olddefNames = "";
        String newdefNames = "";
        if(StringUtils.isNotEmpty(oldLXR)){
            olddefNames = flowDefService.getDefNames(oldLXR);
        }
        if(StringUtils.isNotEmpty(newLXR)){
            newdefNames = flowDefService.getDefNames(newLXR);
        }
        if(!oldLXR.equals(newLXR)){
//            str+="【流程配置】:'"+olddefNames+"'修改为'"+newdefNames+"';";
            str.append(itemLogNote("流程配置", olddefNames, newdefNames));
            saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
        }
    }
    private void currentStepNine(HttpServletRequest request, String entityId, Map<String, Object> variables,
            Map<String, Object> serviceItem) {
        StringBuffer str = new StringBuffer("办理结果部分");
        String oldLXR = (String) serviceItem.get("FINISH_TYPE") != null ? (String) serviceItem
                .get("FINISH_TYPE") : "";
        String newLXR = (String) variables.get("FINISH_TYPE") != null ? (String) variables
                .get("FINISH_TYPE") : "";
        String oldLXDH = (String) serviceItem.get("FINISH_GETTYPE") != null ? (String) serviceItem
                .get("FINISH_GETTYPE") : "";
        String newLXDH = (String) variables.get("FINISH_GETTYPE") != null ? (String) variables
                .get("FINISH_GETTYPE") : "";
        String oldtxt = (String) serviceItem.get("FINISH_INFO") != null ? (String) serviceItem
                .get("FINISH_INFO") : "";
        String newtxt = (String) variables.get("FINISH_INFO") != null ? (String) variables
                .get("FINISH_INFO") : "";
        boolean flag = false;
        if (!oldLXR.equals(newLXR)) {
            String oldname = dictionaryService.findByDicCodeAndTypeCode(
                            (String) serviceItem.get("FINISH_TYPE"),"FinishType");
            String newname = dictionaryService.findByDicCodeAndTypeCode(
                            (String) variables.get("FINISH_TYPE"),"FinishType");
//            str += "【办理结果】:'" + oldname + "'修改为'" + newname + "';";
            str.append(itemLogNote("办理结果", oldname, newname));
            flag = true;
        }
        if(!oldLXDH.equals(newLXDH)){
            String oldname=dictionaryService.findByDicCodeAndTypeCode(oldLXDH, "FinishGetType");
            String newname=dictionaryService.findByDicCodeAndTypeCode(newLXDH, "FinishGetType");
//            str+="【获取方式】:'"+oldname+"'修改为'"+newname+"';";
            str.append(itemLogNote("获取方式", oldname, newname));
            flag=true;
        }
        if(!oldtxt.equals(newtxt)){
//            str+="【结果获取说明】:'"+oldtxt+"'修改为'"+newtxt+"';";
            str.append(itemLogNote("结果获取说明", oldtxt, newtxt));
            flag=true;
        }
        if(flag){
            saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
        }
    }
    
    /**
     * 
     * 跳转到日志列表界面
     * @param request
     * @return
     */
    @RequestMapping(params = "itemLogList")
    public ModelAndView itemLogList(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitemlog");
    }
    /**
     * 获取类别数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "logdatagrid")
    public void logdatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("L.OPERATE_TIME","desc");
//        List<Map<String, Object>> list = serviceItemService.findLogBySqlFilter(filter);
//        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
//                null, JsonUtil.EXCLUDE, response);
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
        List<Map<String, Object>> list = serviceItemService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitemview");
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "delmaters")
    @ResponseBody
    public AjaxJson delmaters(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String itemId = request.getParameter("itemId");

        if (StringUtils.isNotEmpty(itemId)) {
            String status = "";
            status = departServiceItemService
                    .getByJdbc("T_WSBS_SERVICEITEM",
                            new String[] { "ITEM_ID" },
                            new Object[] { itemId }).get("FWSXZT").toString();
            if(status.equals("1")){
                serviceItemService.updateFwsxzt(itemId, "-1");
                status = "-1";
                saveItemLog("已发布事项撤销回草稿库",itemId,"-1",request);
            }
        }
        
        applyMaterService.deleteMaterItem(selectColNames, itemId);
        
        //维护日志信息
        List<Map<String,Object>> matterlist = applyMaterService.findByMaterIds(selectColNames);
        StringBuffer str=new StringBuffer("修改了服务事项记录的申请材料部分：移除材料编码为：");
        for (int i = 0; i < matterlist.size(); i++) {
            str.append(matterlist.get(i).get("MATER_CODE")).append(";");
        }
        saveItemLog(str.toString(),itemId,"1",request);
        
        j.setMsg("移除成功");
        return j;
    }
    /**
     * save item log
     * @param content
     * @param itemId
     * @param type
     * @param request
     */
    public void saveItemLog(String content,String itemId,String type,HttpServletRequest request){
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE",type);
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        serviceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "bindmaters")
    @ResponseBody
    public AjaxJson bindmaters(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String materIds = request.getParameter("materIds");
        String itemId = request.getParameter("itemId");
        applyMaterService.saveMaterItem(materIds, itemId);
        j.setMsg("绑定成功");
        return j;
    }

    /**
     * 绑定用户
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "bindUsers")
    @ResponseBody
    public AjaxJson bindUsers(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userIds = request.getParameter("userIds");
        String itemId = request.getParameter("itemId");
        
        Map<String, Object> olduserInfo = this.serviceItemService.getBindUserIdANdNames(itemId);
        serviceItemService.saveItemUsers(itemId, userIds);
        //添加维护记录
        Map<String, Object> newuserInfo = this.serviceItemService.getBindUserIdANdNames(itemId);
        String content="修改了【预审人员】部分";
        String oldids=(String) olduserInfo.get("USER_IDS");
        if(StringUtils.isEmpty(oldids)){
            content+=":预审人员'"+"' 修改为 '"+newuserInfo.get("FULL_NAMES")+"'";
            saveItemLog(content,itemId,"1",request);
        }
        if(StringUtils.isNotEmpty(oldids)&&!oldids.equals(newuserInfo.get("USER_IDS"))){
            content+=":预审人员'"+olduserInfo.get("FULL_NAMES")+"' 修改为 '"+newuserInfo.get("FULL_NAMES")+"'";
            saveItemLog(content,itemId,"1",request);
        }
        
        j.setMsg("操作成功");
        return j;
    }

    /**
     * 绑定票单
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "bindTickets")
    @ResponseBody
    public AjaxJson bindTickets(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ticketsIds = request.getParameter("ticketsIds");
        String itemId = request.getParameter("itemId");
        serviceItemService.saveItemTickets(itemId, ticketsIds);
        j.setMsg("操作成功");
        return j;
    }

    /**
     * 绑定公文
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "bindDocument")
    @ResponseBody
    public AjaxJson bindDocument(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String documentIds = request.getParameter("documentIds");
        String itemId = request.getParameter("itemId");
        serviceItemService.saveItemDocument(itemId, documentIds);
        j.setMsg("操作成功");
        return j;
    }

    /**
     * 绑定阅办
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "bindRead")
    @ResponseBody
    public AjaxJson bindRead(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String readIds = request.getParameter("readIds");
        String itemId = request.getParameter("itemId");
        serviceItemService.saveItemRead(itemId, readIds);
        j.setMsg("操作成功");
        return j;
    }

    /**
     * 
     * 描述 跳转到办事事项TAB页
     * 
     * @author Faker Li
     * @created 2015年10月20日 下午3:18:11
     * @param request
     * @return
     */
    @RequestMapping(params = "tab")
    public ModelAndView tab(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitemtab");
    }

    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "draftdatagrid")
    public void draftdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serviceItemService.findByDraftSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到审核列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "auditing")
    public ModelAndView auditing(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitemauditing");
    }

    /**
     * easyui AJAX请求数据 审核库列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "auditingdatagrid")
    public void auditingdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = serviceItemService.findByAuditingSqlFilter(filter);
        if (list != null)
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到退回意见信息填写页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "auditingBackInfo")
    public ModelAndView auditingBackInfo(HttpServletRequest request) {
        String itemIds = request.getParameter("itemIds");
        String state = request.getParameter("state");
        String fileFlag=request.getParameter("fileFlag");
        if (itemIds.split(",").length == 1) {
            request.setAttribute("state", state);
        }
        request.setAttribute("itemIds", itemIds);
        request.setAttribute("fileFlag",fileFlag);
        return new ModelAndView("wsbs/serviceitem/serviceitemauditingBackInfo");
    }
    @RequestMapping(params="applyInfo")
    public ModelAndView applyInfo(HttpServletRequest request) {
        String itemIds = request.getParameter("itemIds");
        String state = request.getParameter("state");
        String fileFlag=request.getParameter("fileFlag");
        if (itemIds.split(",").length == 1) {
            request.setAttribute("state", state);
        }
        request.setAttribute("itemIds", itemIds);
        request.setAttribute("fileFlag",fileFlag);
        return new ModelAndView("wsbs/serviceitem/serviceitemApplyInfo");
    }
    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "updateTHYJ")
    @ResponseBody
    public AjaxJson updateTHYJ(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> serviceItem;
        String entityId = request.getParameter("ITEM_IDS");
        int sfty = Integer.parseInt(request.getParameter("SFTY").toString());
        int isSend=Integer.parseInt(request.getParameter("isSend").toString());
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        String  thyj=variables.get("THYJ")==null?"":variables.get("THYJ").toString();
        variables.put("BACKOR_ID", curUser.getUserId());
        variables.put("BACKOR_NAME", curUser.getFullname());
        variables.put("USERNAME", curUser.getFullname());
        variables.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String[] itemids = entityId.split(",");
        for (int i = 0; i < itemids.length; i++) {
            variables.put("ITEM_ID", itemids[i].toString());
            serviceItem = null;
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                    new Object[] { itemids[i].toString() });
            if (serviceItem != null) {
                if(isSend==1){
                    sendTaskNoticeService.sendServiceitemMsg(serviceItem,thyj,sfty);
                }
                if (serviceItem.get("FWSXZT").equals("2") && sfty == 1) {
                    variables.put("FWSXZT", "8");
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemids[i].toString());
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
                } else if (serviceItem.get("FWSXZT").equals("5") && sfty == 1) {
                    variables.put("FWSXZT", "3");
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemids[i].toString());
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
                } else if (serviceItem.get("FWSXZT").equals("2") && sfty == 2) {
                    updateServiceItem(serviceItem,variables,itemids[i].toString(),sfty);
                } else if (serviceItem.get("FWSXZT").equals("5") && sfty == 2) {
                    if("1".equals(serviceItem.get("IS_PUBLISH_EDIT"))){
                        departServiceItemService.updateHisStatus(itemids[i].toString());
                        //departServiceItemService.updateRelateVersion(itemids[i].toString(), version);
                        variables.put("IS_PUBLISH_EDIT", 0);
                    }
                    String version = "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss");
                    variables.put("C_VERSION", version);
                    variables.put("FWSXZT", "1");
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemids[i].toString());
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
                } else if (serviceItem.get("FWSXZT").equals("8") && sfty == 1) {
                    if("1".equals(serviceItem.get("IS_PUBLISH_EDIT"))){
                        departServiceItemService.updateHisStatus(itemids[i].toString());
                        //departServiceItemService.updateRelateVersion(itemids[i].toString(), version);
                        variables.put("IS_PUBLISH_EDIT", 0);
                    }
                    String version = "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss");
                    variables.put("C_VERSION", version);
                    variables.put("FWSXZT", "1");
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemids[i].toString());
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
                } else if (serviceItem.get("FWSXZT").equals("8") && sfty == 2) {
                    variables.put("FWSXZT", "4");
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemids[i].toString());
                    serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
                }else{
                    updateServiceItem(serviceItem,variables,itemids[i].toString(),sfty);
                }
                //添加维护记录
                Map<String,Object> data=new HashMap<String, Object>();
                setServiceItemLog(serviceItem, sfty, data);
                data.put("ITEM_ID", serviceItem.get("ITEM_ID"));
                data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                SysUser sysUser = AppUtil.getLoginUser();
                data.put("FULLNAME",sysUser.getFullname());
                data.put("USERNAME",sysUser.getUsername());
                data.put("USERID", sysUser.getUserId());
                String idAddress = BrowserUtils.getIpAddr(request);
                data.put("IP_ADDRESS",idAddress);
                String logid =serviceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
            }

        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述：事项状态修改
     * @author Water Guo
     * @created 2017-7-27 下午2:50:51
     * @param variables
     * @param itemid
     */
    public void updateServiceItem(Map<String,Object> serviceitem,Map<String,Object> variables,
            String itemid,int sfty){
        String fwsxzt=serviceitem.get("FWSXZT")==null?"":
            serviceitem.get("FWSXZT").toString();
//        String  thyj=variables.get("THYJ")==null?"":variables.get("THYJ").toString();
        variables.put("item_id",itemid);
        if(fwsxzt.equals("-1")&&sfty==1){
            variables.put("FWSXZT", "2");
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemid);
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
        }else if(fwsxzt.equals("4")&&sfty==1){
            variables.put("FWSXZT", "2");
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemid);
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
        }else if(fwsxzt.equals("1")&&sfty==1){
            variables.put("FWSXZT", "5");
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemid);
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
        }else if(fwsxzt.equals("2")&&sfty==2){
            variables.put("FWSXZT", "4");
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemid);
            serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_OPINION", null);
        }
    }
    /**
     * 描述：事项日志
     * @author Water Guo
     * @created 2017-7-27 下午2:48:48
     * @param serviceItem
     * @param sfty
     * @param data
     */
    private void setServiceItemLog(Map<String, Object> serviceItem, int sfty, Map<String, Object> data) {
        if (serviceItem.get("FWSXZT").equals("2") && sfty == 1) {
            data.put("OPERATE_TYPE", "2");
            data.put("OPERATE_CONTENT","从审核库中发布了事项编码为 [" +serviceItem.get("ITEM_CODE")+ "] 的服务事项记录");
        } else if (serviceItem.get("FWSXZT").equals("5") && sfty == 1) {
            data.put("OPERATE_TYPE", "3");
            data.put("OPERATE_CONTENT","取消了事项编码为 [" +serviceItem.get("ITEM_CODE")+ "] 的服务事项记录");
        }else if(serviceItem.get("FWSXZT").equals("8")&&sfty==1){
            data.put("OPERATE_TYPE", "2");
            data.put("OPERATE_CONTENT", "从拟发布库发布了事项编码为["+serviceItem.get("ITEM_CODE")+"]的服务事项记录");
        }else if(serviceItem.get("FWSXZT").equals("-1")&&sfty==1){
            data.put("OPERATE_TYPE", "2");
            data.put("OPERATE_CONTENT", "从草稿库申请了事项编码为["+serviceItem.get("ITEM_CODE")+"]的服务事项记录");
        }else if(serviceItem.get("FWSXZT").equals("1")&&sfty==1){
            data.put("OPERATE_TYPE", "5");
            data.put("OPERATE_CONTENT", "从发布库库取消事项编码为["+serviceItem.get("ITEM_CODE")+"]的服务事项记录");
        }else if(serviceItem.get("FWSXZT").equals("4")&&sfty==1){
            data.put("OPERATE_TYPE", "2");
            data.put("OPERATE_CONTENT", "从退回库申请了事项编码为["+serviceItem.get("ITEM_CODE")+"]的服务事项记录");
        }else if(serviceItem.get("FWSXZT").equals("2")&&sfty==2){
            data.put("OPERATE_TYPE", "2");
            data.put("OPERATE_CONTENT","从审核库中退回了事项编码为 [" +serviceItem.get("ITEM_CODE")+ "] 的服务事项记录");
        }else if(serviceItem.get("FWSXZT").equals("8")&&sfty==2){
            data.put("OPERATE_TYPE", "2");
            data.put("OPERATE_CONTENT","从拟发布库中退回了事项编码为 [" +serviceItem.get("ITEM_CODE")+ "] 的服务事项记录");
        }
    }

    /**
     * 跳转到审核列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "publish")
    public ModelAndView publish(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitempublish");
    }

    /**
     * easyui AJAX请求数据 发布库列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "publishdatagrid")
    public void publishdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //filter.addSorted("T.C_SN", "desc");
        filter.addSorted("ecount", "desc");
        List<Map<String, Object>> list = serviceItemService.findByPublishSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 发布库列表
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/allServiceItemdatagrid")
    public void allServiceItemdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //filter.addSorted("T.C_SN", "desc");
        filter.addSorted("ecount", "desc");
        List<Map<String, Object>> list = serviceItemService.findByAllServiceItemSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 发布库列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "publishYctbDatagrid")
    public void publishYctbDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //filter.addSorted("T.C_SN", "desc");
        filter.addSorted("ecount", "desc");
        List<Map<String, Object>> list = serviceItemService.findByPublishYctbSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到取消库列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "cancel")
    public ModelAndView cancel(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitemcancel");
    }

    /**
     * 方法批量取消
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateFwsxzt")
    @ResponseBody
    public AjaxJson updateFwsxzt(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String selectColNames = request.getParameter("selectColNames");
        String state = request.getParameter("state");
        serviceItemService.updateFwsxzt(selectColNames, state);
        String[] itemids = selectColNames.split(",");
        if (StringUtils.isNotEmpty(state) && state.equals("1")) {
            sysLogService.saveLog("审核通过或者恢复了ID为[" + selectColNames
                    + "]的 服务事项记录", SysLogService.OPERATE_TYPE_EDIT);
            for (int i = 0; i < itemids.length; i++) {
                saveItemLog("恢复发布了服务事项",itemids[i],"2",request);
            }
        } else if (StringUtils.isNotEmpty(state) && state.equals("5")) {
            sysLogService.saveLog("取消了ID为[" + selectColNames + "]的 服务事项记录",
                    SysLogService.OPERATE_TYPE_EDIT);
            for (int i = 0; i < itemids.length; i++) {
                saveItemLog("取消了服务事项",itemids[i],"3",request);
            }
        } else if (StringUtils.isNotEmpty(state) && state.equals("2")) {
            for (int i = 0; i < itemids.length; i++) {
                variables.put("ITEM_ID", itemids[i].toString());
                variables.put("AUDITING_IDS", AppUtil.getLoginUser()
                        .getUserId());
                variables.put("AUDITING_NAMES",  AppUtil.getLoginUser()
                        .getFullname());
                saveItemLog("申请审核了服务事项",itemids[i],"2",request);
                serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", itemids[i].toString());
                sysLogService.saveLog("申请审核了ID为[" + itemids[i].toString() + "]的 服务事项记录",
                        SysLogService.OPERATE_TYPE_EDIT);
            }

        }
        j.setMsg("提交成功");
        return j;
    }

    /**
     * 跳转到审核列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "back")
    public ModelAndView back(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/serviceitemback");
    }

    /**
     * 根据目录编码获取下个项目序号值
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "getMaxNumCode")
    @ResponseBody
    public AjaxJson getMaxNumCode(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String catalogCode = request.getParameter("catalogCode");
        if (StringUtils.isNotEmpty(catalogCode) && !catalogCode.equals("undefined")) {
            String maxNumCode = serviceItemService.getMaxNumCode(catalogCode);
            j.setJsonString(maxNumCode);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "detailedInfo")
    public ModelAndView detailedInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                    new Object[] { entityId });
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
            String sxsx = dictionaryService.findByDicCodeAndTypeCode((String)serviceItem.get("FTA_FLAG"),"isFta");
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
//            String catalogCode = (String) serviceItem.get("CATALOG_CODE");
//            if (StringUtils.isNotEmpty(catalogCode)) {
//                Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
//                serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
//            }
            serviceItem.put("CATALOG_NAME", StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME"));
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(entityId,null);
            serviceItem.put("applyMaters", applyMaters);
            
            serviceItem = setLcsz(entityId,request,serviceItem);
        }
        
        //维护日志
        List<Map<String, Object>> uplist = serviceItemService.findLogByItemId(entityId,"1");
        //业务表维护日志
        List<Map<String, Object>> busLogList = sysLogService.getBusTableLogs("ITEM_ID", "T_WSBS_SERVICEITEM",
                new String[] { "ITEM_ID" }, new Object[] { entityId });
        uplist.addAll(busLogList);//代码质量
        serviceItem.put("uplogList", uplist);
        //排序
        this.listSortByTime(uplist,"OPERATE_TIME");
        List<Map<String, Object>> fabulist = serviceItemService.findLogByItemId(entityId,"2");
        //serviceItem.put("fabulogList", fabulist);
        List<Map<String, Object>> quxiaolist = serviceItemService.findLogByItemId(entityId,"3");
        List<Map<String, Object>> chexiaolist = serviceItemService.findLogByItemId(entityId,"-1");
        fabulist.addAll(chexiaolist);
        fabulist.addAll(quxiaolist);
        fabulist=this.listSortByTime(fabulist,"OPERATE_TIME");
        serviceItem.put("fabulogList", fabulist);
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
        return new ModelAndView("wsbs/serviceitem/serviceitemdetailedinfo");
    }
    /**
     * 
     * 描述    流程节点设置信息
     * @author Danto Huang
     * @created 2018年11月30日 上午10:34:16
     * @param entityId
     * @param request
     * @param serviceItem
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
     * 
     * 描述    窗口次数信息
     * @author Danto Huang
     * @created 2018年11月30日 上午10:22:57
     * @param serviceItem
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
    /**
     * 
     * 描述：list排序
     * @author Water Guo
     * @created 2017-11-20 上午10:19:35
     * @param list
     * @param sortName
     * @return
     */
    public List<Map<String,Object>> listSortByTime(List<Map<String,Object>> list,final String sortName){
        try {
            // 按时间sortName进行排序
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    Date day0= DateTimeUtil.getDateOfStr(arg0.get(sortName).toString(),"yyyy-MM-dd HH:mm:ss");
                    Date day1= DateTimeUtil.getDateOfStr(arg1.get(sortName).toString(),"yyyy-MM-dd HH:mm:ss");
                    if(day0.after(day1)){
                      return -1;  
                    }else{
                      return 1;
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }
    /**
     * 
     * 描述：获取特殊材料
     * @author Water Guo
     * @created 2017-4-5 上午11:33:09
     * @param itemId
     * @return
     * @see net.evecom.platform.wsbs.service.PrintAttachService#getTscl(java.lang.String)
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
     * 描述：法定时限
     * 
     * @author Water Guo
     * @created 2017-5-18 下午2:53:37
     * @param serviceItem
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
     * 绑定表单
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "bindForm")
    @ResponseBody
    public AjaxJson bindForm(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String bindFormIds = request.getParameter("bindFormIds");
        String itemId = request.getParameter("ITEM_ID");
        serviceItemService.saveItemFormIds(itemId, bindFormIds);
        j.setMsg("操作成功");
        return j;
    }

    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        findServiceItemForPageList(request, response);
    }
    /**
     * easyui AJAX请求数据 列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "pagelistnew")
    public void pagelistnew(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String busType = request.getParameter("busType");
        String[] sfzx = request.getParameterValues("SFZX[]");
        String sfzxbl = "0";
        if (sfzx != null) {
            sfzxbl = "1";
        }
        Map<String, Object> mapList =serviceItemService.findfrontListNew(page, rows, 
                busType, busTypeIds,itemName, sfzxbl);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "jskzxnew")
    public void jskzxnew(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        String page = "0";
        String rows = "10";
        String busType = request.getParameter("busType");
        Map<String, Object> mapList = null;
        Map<String, Object> mapList2 = null;
        mapList = serviceItemService.findfrontListByNameAndbusTypeIds(page, rows, busType, itemName, busTypeIds, "1");
        mapList2 = serviceItemService.findfrontListByNameAndbusTypeIds(page, rows, busType, itemName, busTypeIds, "0");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(mapList);
        list.add(mapList2);
        JsonUtil.printJson(response, list);
    }
    /**
     * 
     * 描述 获取服务事项列表分页
     * @author Faker Li
     * @created 2016年1月20日 下午2:27:30
     * @param request
     * @param response
     */
    public void findServiceItemForPageList(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String busType = request.getParameter("busType");
        String[] sfzx = request.getParameterValues("SFZX[]");
        String sfzxbl = "0";
        if (sfzx != null) {
            sfzxbl = "1";
        }
        Map<String, Object> mapList = null;
        if (StringUtils.isNotEmpty(itemName) || StringUtils.isNotEmpty(busTypeIds)) {
            mapList = serviceItemService.findfrontListByNameAndbusTypeIds(page, rows, busType, itemName, busTypeIds,
                    sfzxbl);
        } else {
            mapList = serviceItemService.findfrontList(page, rows, busType, sfzxbl);
        }
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     *
     * 描述 获取服务事项列表分页（机器人）
     * @author Faker Li
     * @created 2016年1月20日 下午2:27:30
     * @param request
     * @param response
     */
    @RequestMapping("/findItemForRobot")
    public void findItemForRobot(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        List<Map<String,Object>> list=serviceItemService.findItemForRobot(itemName,page,rows);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到场景式导航信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/sceneNaviInfo")
    public ModelAndView sceneNaviInfo(HttpServletRequest request) {

        String busCode = request.getParameter("busCode");
        if(StringUtils.isNotEmpty(busCode)&&busCode.equals("GRZTFL")){
            request.setAttribute("navi", "个人办事");
        }else if(StringUtils.isNotEmpty(busCode)&&busCode.equals("FRZTFL")){
            request.setAttribute("navi", "法人办事");
            
        }
        String entityId = request.getParameter("entityId");
        List<Map<String, Object>> list = null;
        Map<String,Object>  busType = new HashMap<String,Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            list = serviceItemService.findfrontList(entityId);
            busType = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
        }
        request.setAttribute("busCode", busCode);
        request.setAttribute("list", list);  
        request.setAttribute("busType", busType);
        return new ModelAndView("website/bsdt/sceneNaviInfo");
    }
    /**
     * 
     * 描述 前台场景式导航
     * @author Rider Chen
     * @created 2016-1-8 上午09:07:42
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/sceneNavi")
    public void sceneNavi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        String busCode = request.getParameter("busCode");
        if(StringUtils.isNotEmpty(busCode)&&busCode.equals("GRZTFL")){            
            result.put("navi", "个人办事");
        }else if(StringUtils.isNotEmpty(busCode)&&busCode.equals("FRZTFL")){
            result.put("navi", "法人办事");
        }
        String entityId = request.getParameter("entityId");
        List<Map<String, Object>> list = null;
        Map<String,Object>  busType = new HashMap<String,Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            list = serviceItemService.findfrontList(entityId);
            busType = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
        }
        result.put("busCode", busCode);
        result.put("busType", busType);
        result.put("list", list);  
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 方法updateSn
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] itemIds = request.getParameterValues("itemIds[]");
        this.serviceItemService.updateSn(itemIds);
        j.setMsg("排序成功");
        return j;
    }

    /**
     * 
     * 描述 接收
     * @author Kester Chen
     * @created 2018-1-15 上午10:47:44
     */
    @RequestMapping(params = "receiveSwbItem")
    @ResponseBody
    public AjaxJson receiveSwbItem(){
        AjaxJson j = new AjaxJson();
        List<Map<String,Object>> swbDatas = swbItemDataService.findNeedToCreate();
        if(swbDatas!=null&&swbDatas.size()>0){
            log.info("发现省网办未处理下发事项数据量为:"+swbDatas.size());
        }
        log.info("开始处理省网办下发事项................................");
        for(Map<String,Object> swbData:swbDatas){
            try {
                swbItemDataService.createItem(swbData);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        log.info("处理省网办下发事项结束................................");
        j.setMsg("接收成功");
        return j;
    }
    /**
     * 
     * 描述 接收
     * @author Kester Chen
     * @created 2018-1-15 上午10:47:44
     */
    @RequestMapping(params = "receiveSwbItemByIds")
    @ResponseBody
    public AjaxJson receiveSwbItemByIds(HttpServletRequest request, HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String unid = request.getParameter("unid");
        log.info(unid);
        List<Map<String,Object>> swbDatas = swbItemDataService.findNeedToCreateByIds(unid);
        if(swbDatas!=null&&swbDatas.size()>0){
            log.info("发现省网办未处理下发事项数据量为:"+swbDatas.size());
        }
        log.info("开始处理省网办下发事项................................");
        for(Map<String,Object> swbData:swbDatas){
            try {
                Map<String, Object> returnMap = swbItemDataService.createItemNew(swbData);
                j.setSuccess(true);
                j.setMsg("接收成功");
                if (returnMap != null && !returnMap.isEmpty()) {
                    j.setSuccess(false);
                    j.setMsg((String)returnMap.get("msg"));
                    return j;
                }
            } catch (Exception e) {
                j.setSuccess(false);
                j.setMsg("接收失败");
                log.error(e.getMessage(),e);
            }
        }
        log.info("处理省网办下发事项结束................................");
        return j;
    }
    /**
     * 
     * 描述 移除
     * @author Kester Chen
     * @created 2018-6-1 下午3:26:42
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "deleteSwbItemByIds")
    @ResponseBody
    public AjaxJson deleteSwbItemByIds(HttpServletRequest request, 
            HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String unid = request.getParameter("unid");
        swbItemDataService.deleteSwbItemByIds(unid);
        j.setMsg("成功移除");
        return j;
    }
    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "jskzx")
    public void jskzx(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        String page = "0";
        String rows = "10";
        String busType = request.getParameter("busType");
        Map<String, Object> mapList = null;
        Map<String, Object> mapList2 = null;
        mapList = serviceItemService.findfrontListByNameAndbusTypeIds(page, rows, busType, itemName, busTypeIds, "1");
        mapList2 = serviceItemService.findfrontListByNameAndbusTypeIds(page, rows, busType, itemName, busTypeIds, "0");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(mapList);
        list.add(mapList2);
        JsonUtil.printJson(response, list);
    }

    /**
     * 
     * 描述 前台所有发布事项
     * 
     * @author Faker Li
     * @created 2015年12月1日 上午11:21:24
     * @param request
     * @param response
     */
    @RequestMapping("/allItemList")
    public void allItemList(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        //String busType = request.getParameter("busType");
        String sfzxbl = "0";
        Map<String, Object> mapList = null;
        mapList = serviceItemService.findfrontListByNameAndbusTypeIds(page, rows, "", itemName, "", sfzxbl);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 根据catalogCode更新事项所属部门编码
     * @author Faker Li
     * @created 2015年12月16日 下午4:50:10
     * @param request
     * @return
     */
    @RequestMapping(params = "updateSSBMBM")
    @ResponseBody
    public AjaxJson updateSSBMBM(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String departId = request.getParameter("departId");
        String catalogCode = request.getParameter("catalogCode");
        String catalogId = request.getParameter("catalogId");
        if(StringUtils.isNotEmpty(departId)&&StringUtils.isNotEmpty(catalogCode)&&StringUtils.isNotEmpty(catalogId)){
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] {departId});
            if(department!=null&&StringUtils.isNotEmpty((String)department.get("DEPART_CODE"))){
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("DEPART_ID", departId);
                variables.put("CATALOG_ID", catalogId);
                catalogService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_CATALOG", catalogId);
                this.serviceItemService.updateSSBMBM(catalogCode,(String)department.get("DEPART_CODE"));
                j.setMsg("目录转移成功");
            }else{
                j.setSuccess(false);
                j.setMsg("目录转移失败");
            }
        }else{
            j.setSuccess(false);
            j.setMsg("目录转移失败");
        }
        return j;
    }
    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/qlqdlist")
    public void qlqdlist(HttpServletRequest request, HttpServletResponse response) {
        String sxxz = request.getParameter("SXXZ");
        String busTypeIds = request.getParameter("busTypeIds");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = serviceItemService.findfrontQlqdList(page, rows,sxxz,busTypeIds);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 获取APP服务事项分页列表
     * @author Faker Li
     * @created 2016年1月20日 下午2:25:03
     * @param request
     * @param response
     */
    @RequestMapping("/appPagelist")
    public void appPagelist(HttpServletRequest request, HttpServletResponse response) {
        findServiceItemForPageList(request, response);
    }

    /**
     * 
     * 描述 跳转事项选择器
     * @author Danto Huang
     * @created 2016-1-15 上午11:33:13
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/ItemSelector");
    }

    /**
     * 
     * 描述 跳转事项选择器
     * @author Danto Huang
     * @created 2016-1-15 上午11:33:13
     * @param request
     * @return
     */
    @RequestMapping("/yctbSelector")
    public ModelAndView ybtbSelector(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/YctbItemSelector");
    }
    /**
     * 
     * 描述 跳转事项选择器
     * @author Danto Huang
     * @created 2016-1-15 上午11:33:13
     * @param request
     * @return
     */
    @RequestMapping("/getxnjc")
    public void getxnjc(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> maps = new HashMap<String,Object>();
        //2c93f48251a4c13a0151a4c140c4000c  区行政审批局
        //40288b9f530d91f8015310e880410019  区国税局
        //40288b9f53e626f30153eaa89fec01aa  区气象局
        //40288b9f530d91f8015310e7b6fa0016  县烟草局
        //2c90b38a57db50910157e0757aa32349  区消防支队
        //2c90b38a5c48186d015c6258137449cd  区人民银行
        //2c90b38a5c10f1d3015c1451f35b2503  流水镇
        //2c90b38a5c10f1d3015c1524dd9c4809  苏澳镇
        //2c90b38a5c10f1d3015c15254bb84821  平原镇
        //2c90b38a5c10f1d3015c1525727a4833  潭城镇
        //2c90b38a5c10f1d3015c1525a90b4840  澳前镇
        //2c90b38a5c10f1d3015c1525e9cd4842  北厝镇
        //2c90b38a5c10f1d3015c1526331b4844  敖东镇
        //2c90b38a5c10f1d3015c15265e144872  屿头乡
        //2c90b38a5c10f1d3015c15268f934886  大练乡
        //2c90b38a5c10f1d3015c1526bc9f4888  白青乡
        //2c90b38a5c10f1d3015c1526f3204890  芦洋乡
        //2c90b38a5c10f1d3015c152731614894  中楼乡
        //2c90b38a5c10f1d3015c152777154899  东庠乡
        //2c90b38a5c10f1d3015c1527a2d8489f  岚城乡
        //2c90b38a5c10f1d3015c1527cb3048a1  南海乡
        //2c93f48251a4c13a0151a4c13a1f0000  区党工委、管委会办公室
        Map<String, Object> qdgwgwhbgs = serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13a1f0000");
        //2c93f48251a4c13a0151a4c13aed0001  区党群工作部
        Map<String, Object> qdqgzb = serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13aed0001");
        //2c93f48251a4c13a0151a4c13b6b0002  区台湾工作部
        Map<String, Object> qtwgzb = serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13b6b0002");
        //2c93f48251a4c13a0151a4c13f8f000a  区经济发展局
        Map<String, Object> qjjfzj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13f8f000a");
        //2c93f48251a4c13a0151a4c13c420004  区环境与国土资源局
        Map<String, Object> qhjygtzyj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13c420004");
        //2c93f48251a4c13a0151a4c13cc20005  区交通与建设局
        Map<String, Object> qjtyjsj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13cc20005");
        //2c93f48251a4c13a0151a4c13d4e0006  区公安局
        Map<String, Object> qgaj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13d4e0006");
        //2c93f48251a4c13a0151a4c13bcc0003  区财政金融局
        Map<String, Object> qczjrj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13bcc0003");
        //2c93f48251a4c13a0151a4c13ded0007  区社会事业局
        Map<String, Object> qshsyj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13ded0007");
        //2c93f48251a4c13a0151a4c13e750008  区市场监督管理局
        Map<String, Object> qscjdlj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13e750008");
        //2c93f48251a4c13a0151a4c13ef80009  区综合执法局
        Map<String, Object> qzhzfj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13ef80009");
        //40288b9f53e626f30153eab076e701bd  区农村发展局
        Map<String, Object> qncfzj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eab076e701bd");
        //40288b9f55f7d8c401560090cde70f0d  区教育局
        Map<String, Object> qjyj= serviceItemService.getXnjcTaotal("40288b9f55f7d8c401560090cde70f0d");
        //40288b9f53e626f30153eab2275101c8  区卫生和计划生育局
        Map<String, Object> qwshjhsyj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eab2275101c8");
        //40288b9f538c6d2301538dacd7ad0007  区规划局
        Map<String, Object> qghj= serviceItemService.getXnjcTaotal("40288b9f538c6d2301538dacd7ad0007");
        //40288b9f53e626f30153eab0f4bd01bf  区旅游发展局
        Map<String, Object> qlyfzj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eab0f4bd01bf");
        //区廉政办公室
//        Map<String, Object> qlzbgs= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c14042000b");
        //区行政审批局
//        Map<String, Object> qxzspj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c140c4000c");
        int[] xzxk = new int[16];
        int[] ggfw = new int[16];
        xzxk[0] = Integer.parseInt(qdgwgwhbgs.get("tbxzxktotal").toString());
        ggfw[0] = Integer.parseInt(qdgwgwhbgs.get("tbggfwtotal").toString());
        xzxk[1] = Integer.parseInt(qdqgzb.get("tbxzxktotal").toString());
        ggfw[1] = Integer.parseInt(qdqgzb.get("tbggfwtotal").toString());
        xzxk[2] = Integer.parseInt(qtwgzb.get("tbxzxktotal").toString());
        ggfw[2] = Integer.parseInt(qtwgzb.get("tbggfwtotal").toString());
        xzxk[3] = Integer.parseInt(qlyfzj.get("tbxzxktotal").toString());
        ggfw[3] = Integer.parseInt(qlyfzj.get("tbggfwtotal").toString());
        xzxk[4] = Integer.parseInt(qjjfzj.get("tbxzxktotal").toString());
        ggfw[4] = Integer.parseInt(qjjfzj.get("tbggfwtotal").toString());
        xzxk[5] = Integer.parseInt(qhjygtzyj.get("tbxzxktotal").toString());
        ggfw[5] = Integer.parseInt(qhjygtzyj.get("tbggfwtotal").toString());
        xzxk[6] = Integer.parseInt(qjtyjsj.get("tbxzxktotal").toString());
        ggfw[6] = Integer.parseInt(qjtyjsj.get("tbggfwtotal").toString());
        xzxk[7] = Integer.parseInt(qgaj.get("tbxzxktotal").toString());
        ggfw[7] = Integer.parseInt(qgaj.get("tbggfwtotal").toString());
        xzxk[8] = Integer.parseInt(qczjrj.get("tbxzxktotal").toString());
        ggfw[8] = Integer.parseInt(qczjrj.get("tbggfwtotal").toString());
        xzxk[9] = Integer.parseInt(qshsyj.get("tbxzxktotal").toString());
        ggfw[9] = Integer.parseInt(qshsyj.get("tbggfwtotal").toString());
        xzxk[10] = Integer.parseInt(qscjdlj.get("tbxzxktotal").toString());
        ggfw[10] = Integer.parseInt(qscjdlj.get("tbggfwtotal").toString());
        xzxk[11] = Integer.parseInt(qzhzfj.get("tbxzxktotal").toString());
        ggfw[11] = Integer.parseInt(qzhzfj.get("tbggfwtotal").toString());
        xzxk[12] = Integer.parseInt(qncfzj.get("tbxzxktotal").toString());
        ggfw[12] = Integer.parseInt(qncfzj.get("tbggfwtotal").toString());
        xzxk[13] = Integer.parseInt(qjyj.get("tbxzxktotal").toString());
        ggfw[13] = Integer.parseInt(qjyj.get("tbggfwtotal").toString());
        xzxk[14] = Integer.parseInt(qwshjhsyj.get("tbxzxktotal").toString());
        ggfw[14] = Integer.parseInt(qwshjhsyj.get("tbggfwtotal").toString());
        xzxk[15] = Integer.parseInt(qghj.get("tbxzxktotal").toString());
        ggfw[15] = Integer.parseInt(qghj.get("tbggfwtotal").toString());
        
        maps.put("xzxk", xzxk);
        maps.put("ggfw", ggfw);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }

    /**
     * 描述:省网下发事项关联目录
     *
     * @author Madison You
     * @created 2020/8/31 15:30:00
     * @param
     * @return
     */
    @RequestMapping(params = "linkSwbItemCatalogView")
    public ModelAndView linkSwbItemCatalogView(HttpServletRequest request) {
        String unid = request.getParameter("unid");
        request.setAttribute("UNID", unid);
        return new ModelAndView("wsbs/departServiceItem/linkSwbItemCatalogView");
    }

    /**
     * 描述:保存关联的目录
     *
     * @author Madison You
     * @created 2020/8/31 16:04:00
     * @param
     * @return
     */
    @RequestMapping(params = "saveOrUpdateLinkCatalog")
    @ResponseBody
    public AjaxJson saveOrUpdateLinkCatalog(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = new HashMap<>();
        j.setSuccess(false);
        j.setMsg("关联失败");
        String unid = request.getParameter("UNID");
        String catalogCode = request.getParameter("CATALOG_CODE");
        try{
            if (StringUtils.isNotEmpty(unid) && StringUtils.isNotEmpty(catalogCode)) {
                Map<String, Object> catalogMap = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM_CATALOG",
                        new String[]{"CATALOG_CODE"}, new Object[]{catalogCode});
                if (catalogMap != null) {
                    String catalogName = StringUtil.getValue(catalogMap, "CATALOG_NAME");
                    String catalogId = StringUtil.getValue(catalogMap, "CATALOG_ID");
                    Map<String,Object> swbItemMap = swbItemDataService.getByJdbc("PROVINCEITEMINFO", new String[]{"UNID"}, new Object[]{unid});
                    if (swbItemMap != null) {
                        Document document = XmlUtil.stringToDocument((String) swbItemMap.get("content"));
                        Element root = document.getRootElement();
                        String serviceInfo = root.selectSingleNode("//Case/Body/ServiceInfo").asXML();
                        String serviceInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceInfo, "UTF-8");
                        JSONObject serviceInfOobj = JSONObject.parseObject(serviceInfoJson);
                        String ServiceName = getObjStringInfo(serviceInfOobj,"ServiceName");
                        String Unid = getObjStringInfo(serviceInfOobj,"Unid");
                        if (StringUtils.isNotEmpty(Unid)) {
                            boolean isExitCatalog = serviceItemService.findExitCatalog(Unid);
                            if (isExitCatalog) {
                                j.setSuccess(false);
                                j.setMsg("已存在目录，无需关联");
                                return j;
                            }
                        }
                        String UUID = UUIDGenerator.getUUID().toUpperCase();
                        /*构建xml*/
                        StringBuffer xmlStr = new StringBuffer();
                        xmlStr.append("<?xml version=\"1.0\" encoding=\"GB2312\"?><Case type=\"101\" id=\"");
                        xmlStr.append(UUID).append("\"><Router>");
                        xmlStr.append("<FromAreaCode>350000</FromAreaCode><FromAreaName><![CDATA[福建省]]>");
                        xmlStr.append("</FromAreaName><ToAreaCode>350128</ToAreaCode><ToAreaName><![CDATA[平潭县]]>");
                        xmlStr.append("</ToAreaName><Time><![CDATA[2020-07-08 11:52:00]]></Time></Router>");
                        xmlStr.append("<Body><ServiceCataRelations><ServiceCataRelation id=\"");
                        xmlStr.append(UUIDGenerator.getUUID().toUpperCase()).append("\">");
                        xmlStr.append("<ServiceUnid><![CDATA[").append(Unid).append("]]></ServiceUnid><ServiceName><![CDATA[");
                        xmlStr.append(ServiceName).append("]]></ServiceName>");
                        xmlStr.append("<CataDirectoryUnid><![CDATA[").append(catalogId).append("]]></CataDirectoryUnid>");
                        xmlStr.append("<CataDirectoryName><![CDATA[").append(catalogName).append("]]></CataDirectoryName>");
                        xmlStr.append("<BaseCode><![CDATA[").append(catalogCode).append("]]></BaseCode><LocalBaseCode></LocalBaseCode>");
                        xmlStr.append("<TaskCode></TaskCode><LocalTaskCode></LocalTaskCode><TaskHandleCode>");
                        xmlStr.append("</TaskHandleCode><RelationState><![CDATA[N]]></RelationState>");
                        xmlStr.append("</ServiceCataRelation></ServiceCataRelations></Body></Case>");

                        variables.put("UNID", UUID);
                        variables.put("TYPE", "101");
                        variables.put("XSDTYPE", "3");
                        variables.put("COPYRIGHT", "1");
                        variables.put("ACTION", "I");
                        variables.put("FROMAREACODE", "350000");
                        variables.put("FROMAREANAME", "福建省");
                        variables.put("TOAREACODE", "350128");
                        variables.put("TOAREANAME", "平潭县");
                        variables.put("APPLYFROM", "3");
                        variables.put("TCHAR1", "");
                        variables.put("TCHAR2", "");
                        variables.put("TCHAR3", "");
                        variables.put("TCHAR4", "");
                        variables.put("TBLOB1", "");
                        variables.put("TBLOB2", "");
                        variables.put("CREATETIME", new Date());
                        variables.put("CONTENT", xmlStr.toString());
                        variables.put("DEPT_UNID", getObjStringInfo(serviceInfOobj,"DeptUnid"));
                        variables.put("DEPT_NAME", getObjStringInfo(serviceInfOobj,"DeptName"));
                        variables.put("SERVICE_UNID", getObjStringInfo(serviceInfOobj,"Unid"));
                        variables.put("SERVICE_NAME", ServiceName);
                        variables.put("PARENT_UNID", getObjStringInfo(serviceInfOobj,"ParentUnid"));
                        variables.put("PARENT_NAME", getObjStringInfo(serviceInfOobj,"ParentName"));
                        serviceItemService.saveOrUpdate(variables, "PROVINCEITEMINFO", UUID);
                        j.setSuccess(true);
                        j.setMsg("关联成功");
                    }
                }
            }
        } catch (Exception e) {
            log.error("省网下发事项关联目录失败");
            log.error(e.getMessage(), e);
        }
        return j;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/31 17:08:00
     * @param 
     * @return 
     */
    private String getObjStringInfo(JSONObject serviceInfOobj, String cs) {
        String returnInfoString = (JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)==null?
                "":(String) ((JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)).get(0);
        return returnInfoString;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/19 11:05:00
     * @param
     * @return
     */
    private String itemLogNote(String columnName,String oldColumn,String newColumn){
        StringBuffer str = new StringBuffer();
        str.append("【").append(columnName).append("】:'").append(oldColumn)
                .append("'修改为'").append(newColumn).append("';");
        return str.toString();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/19 11:51:00
     * @param 
     * @return 
     */
    private String itemLogNote(String oldColumn,String newColumn){
        StringBuffer str = new StringBuffer();
        str.append(oldColumn).append("'修改为'").append(newColumn).append("';");
        return str.toString();
    }
}
