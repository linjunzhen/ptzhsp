/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.fjfda.service.FoodProductionService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 食品生产Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/foodProductionController")
public class FoodProductionController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FoodProductionController.class);
    /**
     * 引入Service
     */
    @Resource
    private FoodProductionService foodProductionService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * departmentService
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * nodeConfigService
     */
    @Resource
    private NodeConfigService nodeConfigService;
    
    /**
     * callService
     */
    @Resource
    private CallService callService;

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
        foodProductionService.remove("T_FJFDA_SPSCXK_JBXX", "JBXX_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 食品生产记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 描述 流程表单跳转
     * @author Keravon Feng
     * @created 2018年10月22日 上午11:05:35
     * @param request
     * @param frompath
     * @return
     */
    public ModelAndView toFoodBusinessForm(HttpServletRequest request,String frompath) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申请方式
        String applyType = request.getParameter("applyType");
        // 获取是否平潭网格政务门户申请
        String ptwgType = request.getParameter("ptwgType");
        // 获取流程申报号
        String exeId = request.getParameter("exeId");
        String EFLOW_ISQUERYDETAIL = request.getParameter("EFLOW_ISQUERYDETAIL");
        String lineName = request.getParameter("lineName");
        String lineCard = request.getParameter("lineCard");
        String lineId = request.getParameter("lineId");
        String zjlx = request.getParameter("zjlx");
        // 定义是否是开始节点
        String EFLOW_IS_START_NODE = "";
        if (StringUtils.isNotEmpty(EFLOW_ISQUERYDETAIL) && EFLOW_ISQUERYDETAIL.equals("true")) {
            EFLOW_IS_START_NODE = "false";
        }
        Map<String, Object> execution = null;
        int flowVersion = 0;
        String currentNodeName = null, startNodeName = null, defId = null;
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            execution = executionService
                    .getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            // 获取流程状态
            String runStatus = execution.get("RUN_STATUS").toString();
            if (runStatus.equals("0")) {
                EFLOW_IS_START_NODE = "true";
            } else {
                // 获取当前流程流转节点
                currentNodeName = (String) execution.get("CUR_STEPNAMES");
                defId = (String) execution.get("DEF_ID");
                flowVersion = Integer.parseInt(execution.get("DEF_VERSION").toString());
                // 获取开始节点的名称
                startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                if (startNodeName.equals(currentNodeName)) {
                    EFLOW_IS_START_NODE = "true";
                } else {
                    EFLOW_IS_START_NODE = "false";
                }
            }
            itemCode = (String) execution.get("ITEM_CODE");
        } else {
            EFLOW_IS_START_NODE = "true";
        }
        Map<String, Object> serviceItem = 
                serviceItemService.getItemAndDefInfoNew(itemCode);// getItemAndDefInfo(itemCode);
        if (serviceItem != null) {
            return ifNotNull(request, itemCode, applyType, ptwgType, exeId, lineName, lineCard, lineId, zjlx,
                    EFLOW_IS_START_NODE, execution, flowVersion, currentNodeName, defId, serviceItem,frompath);
        } else {
            return null;
        }
    }
    
    /**
     * 描述 ifNotNull
     * @author Keravon Feng
     * @created 2018年10月22日 上午10:56:51
     * @return
     */
    private ModelAndView ifNotNull(HttpServletRequest request, String itemCode, String applyType, String ptwgType,
            String exeId, String lineName, String lineCard, String lineId, String zjlx, String EFLOW_IS_START_NODE,
            Map<String, Object> execution, int flowVersion, String currentNodeName, String defId,
            Map<String, Object> serviceItem,String frompath) {
        if (flowVersion == 0) {
            String defKey = (String) serviceItem.get("DEF_KEY");// 获取流程定义KEY
            Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_KEY" },
                    new Object[] { defKey });
            flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            defId = (String) flowDef.get("DEF_ID");
            currentNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
        }
        String formKey = (String) serviceItem.get("FORM_KEY");
        String itemId = (String) serviceItem.get("ITEM_ID");// 获取项目ID
        Map<String, Object> nodeConfig = nodeConfigService.getByJdbc("JBPM6_NODECONFIG", new String[] { "NODE_NAME",
                "DEF_ID", "DEF_VERSION" }, new Object[] { currentNodeName, defId, flowVersion });
        if (nodeConfig != null) {
            request.setAttribute("nodeConfig", nodeConfig);
        }
        // 获取材料信息列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        String busRecordId = "";
        String busTableName = "";
        if (execution != null) {
            busRecordId = execution.get("BUS_RECORDID") == null ?
                    "" : execution.get("BUS_RECORDID").toString();// 获取业务ID
            busTableName = execution.get("BUS_TABLENAME") == null ?
                    "" : execution.get("BUS_TABLENAME").toString();// 获取业务表名称
        }
        List<Map<String, Object>> ysqMaters = null;
        if (StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(busTableName)) {
            ysqMaters = fileAttachService.findYsqByList(busTableName, busRecordId);
        }
        List<Map<String, Object>> filterApplyMaters = new ArrayList<Map<String, Object>>();
        if (applyMaters != null && applyMaters.size() > 0) {
            filterApplyMaters.addAll(applyMaters);
        }
        if (StringUtils.isNotEmpty(currentNodeName) && StringUtils.isNotEmpty(exeId) && applyMaters != null
                && applyMaters.size() > 0) {
            String sysUserName = AppUtil.getLoginUser() == null ? "" : AppUtil.getLoginUser().getUsername();
            List<Map<String, Object>> filterMater = applyMaterService.findFilterMater(sysUserName, currentNodeName,
                    exeId);
            if (filterMater != null && filterMater.size() > 0) {
                applyMaters.clear();
                for (int i = 0; i < filterMater.size(); i++) {
                    Map<String, Object> m = filterMater.get(i);
                    for (int j = 0; j < filterApplyMaters.size(); j++) {
                        Map<String, Object> fmap = filterApplyMaters.get(j);
                        if (m.get("MATER_ID").toString().equals(fmap.get("MATER_ID").toString())) {
                            applyMaters.add(fmap);
                        }
                    }
                }
            }
        }
        if (StringUtils.isEmpty(applyType)) {
            applyType = "2";
        }
        // 获取流程实例信息
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            applyMaters = getFlowCaseInfo(execution, request, applyMaters);
            ysqMaters = getFlowCaseInfo(execution, request, ysqMaters);
            applyType = (String) execution.get("SQFS");
        }
        serviceItem.put("APPLY_TYPE", applyType);
        // 查找指定的事项需要特殊处理的环节
        Map<String, Object> dealItem = serviceItemService.getParticularDealNode(itemCode);
        // 定义材料列表JSON
        String ysqMatersJson = "";
        if (ysqMaters != null && ysqMaters.size() > 0) {
            ysqMatersJson = JsonUtil.jsonStringFilter(ysqMaters, new String[] { "uploadFiles" }, true);
        }
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("ysqMaters", ysqMaters);
        request.setAttribute("ysqMatersJson", StringEscapeUtils.escapeHtml3(ysqMatersJson));
        request.setAttribute("EFLOW_IS_START_NODE", EFLOW_IS_START_NODE);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        request.setAttribute("dealItem", dealItem);
        setRequestVal(lineName, lineCard, lineId, zjlx, request);
        // 获取其他子表数据
        getOtherFormData(request, busRecordId);        
        if (StringUtils.isNotEmpty(ptwgType)) {
            return new ModelAndView("bsdt/ptwgform/" + formKey);
        } else {
            return new ModelAndView("bsdt/applyform/" + frompath);
        }
    }
    
    /**
     * 设置值
     * 
     * @param execution
     * @param request
     * @param applyMaters
     * @return
     */
    public List<Map<String, Object>> getFlowCaseInfo(Map<String, Object> execution, HttpServletRequest request,
            List<Map<String, Object>> applyMaters) {
        String busRecordId = (String) execution.get("BUS_RECORDID");// 获取业务ID
        String busTableName = (String) execution.get("BUS_TABLENAME");// 获取业务表名称
        //食药监保健食品生产虚拟主表替换真实表名称
        if(busTableName.equals("T_FJFDA_SPSCXK_JBXX1")){
            busTableName = "T_FJFDA_SPSCXK_JBXX";
        }
        String busPkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);// 获取业务表主键名称
        // 获取业务记录
        Map<String, Object> busRecord = executionService.getByJdbc(busTableName, new String[] { busPkName },
                new Object[] { busRecordId });
        applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        request.setAttribute("execution", execution);
        // 获取多证合一数据
        Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                new String[] { "COMPANY_ID" }, new Object[] { busRecordId });
        if (null != multiple) {
            busRecord.putAll(multiple);
        }
        request.setAttribute("busRecord", busRecord);
        return applyMaters;
    }
    
    /**
     * set
     * 
     * @param lineName
     * @param lineCard
     * @param lineId
     * @param zjlx
     * @param request
     */
    public void setRequestVal(String lineName, 
            String lineCard, String lineId, String zjlx, 
            HttpServletRequest request) {

        String takenoway = request.getParameter("takenoway");
        if (StringUtils.isNotEmpty(takenoway) && "3".equals(takenoway)) {
            SqlFilter filter = new SqlFilter(request);
            String username = AppUtil.getLoginUser().getUsername();
            Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
            if (winInfo != null) {
                filter.addFilter("Q_T.OPERATOR_ID_=", AppUtil.getLoginUser().getUserId());
            } else if (!AppUtil.getLoginUser().getUsername().equals("admin")) {
                filter.addFilter("Q_T.CUR_WIN_=", "0");
            }
            filter.addFilter("Q_T.CALL_STATUS_!=", "0");
//            filter.addSorted("DECODE(T.OPER_TIME,NULL,0,1)", "DESC");
//            filter.addSorted("T.OPER_TIME", "DESC");
            filter.addSorted("T.CREATE_TIME", "DESC");
            List<Map<String, Object>> list = callService.findQueuingBySqlFilter(filter);
            String lastExeId = list.get(0).get("EXE_ID") == null ? "" : list.get(0).get("EXE_ID").toString();
            if (StringUtils.isNotEmpty(lastExeId)) {
                Map<String, Object> lastExeInfo = callService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { lastExeId });
                String bsyhlx = lastExeInfo.get("BSYHLX") == null ? "" : lastExeInfo.get("BSYHLX").toString();
                String sfxsjbrxx = lastExeInfo.get("SFXSJBRXX") == null ? "" : lastExeInfo.get("SFXSJBRXX").toString();
                if (StringUtils.isNotEmpty(takenoway)) {
                    request.setAttribute("takenoway", takenoway);
                }
                if (StringUtils.isNotEmpty(bsyhlx)) {
                    request.setAttribute("bsyhlx", bsyhlx);
                }
                if (StringUtils.isNotEmpty(sfxsjbrxx)) {
                    request.setAttribute("sfxsjbrxx", sfxsjbrxx);
                }
                if ("1".equals(bsyhlx)) {
                    String SQRMC = lastExeInfo.get("SQRMC") == null ? "" : lastExeInfo.get("SQRMC").toString();
                    String SQRZJLX = lastExeInfo.get("SQRZJLX") == null ? "" : lastExeInfo.get("SQRZJLX").toString();
                    String SQRSFZ = lastExeInfo.get("SQRSFZ") == null ? "" : lastExeInfo.get("SQRSFZ").toString();
                    String SQRSJH = lastExeInfo.get("SQRSJH") == null ? "" : lastExeInfo.get("SQRSJH").toString();
                    String SQRDHH = lastExeInfo.get("SQRDHH") == null ? "" : lastExeInfo.get("SQRDHH").toString();
                    String SQRLXDZ = lastExeInfo.get("SQRLXDZ") == null ? "" : lastExeInfo.get("SQRLXDZ").toString();
                    String SQRYJ = lastExeInfo.get("SQRYJ") == null ? "" : lastExeInfo.get("SQRYJ").toString();

                    String JBR_MOBILE = lastExeInfo.get("JBR_MOBILE") == null ? "" : lastExeInfo.get("JBR_MOBILE")
                            .toString();
                    if (StringUtils.isNotEmpty(JBR_MOBILE)) {
                        request.setAttribute("JBR_MOBILE", JBR_MOBILE);
                    }
                    if (StringUtils.isNotEmpty(SQRMC)) {
                        request.setAttribute("lineName", SQRMC);
                    }
                    if (StringUtils.isNotEmpty(SQRSFZ)) {
                        request.setAttribute("lineCard", SQRSFZ);
                    }
                    if (StringUtils.isNotEmpty(lineId)) {
                        request.setAttribute("lineId", lineId);
                    }
                    if (StringUtils.isNotEmpty(SQRZJLX)) {
                        request.setAttribute("zjlx", SQRZJLX);
                    }
                    if (StringUtils.isNotEmpty(SQRSJH)) {
                        request.setAttribute("SQRSJH", SQRSJH);
                    }
                    if (StringUtils.isNotEmpty(SQRDHH)) {
                        request.setAttribute("SQRDHH", SQRDHH);
                    }
                    if (StringUtils.isNotEmpty(SQRLXDZ)) {
                        request.setAttribute("SQRLXDZ", SQRLXDZ);
                    }
                    if (StringUtils.isNotEmpty(SQRYJ)) {
                        request.setAttribute("SQRYJ", SQRYJ);
                    }

                } else if ("2".equals(bsyhlx)) {
                    String SQJG_NAME = lastExeInfo.get("SQJG_NAME") == null ? "" : lastExeInfo.get("SQJG_NAME")
                            .toString();
                    String SQJG_TYPE = lastExeInfo.get("SQJG_TYPE") == null ? "" : lastExeInfo.get("SQJG_TYPE")
                            .toString();
                    String SQJG_CODE = lastExeInfo.get("SQJG_CODE") == null ? "" : lastExeInfo.get("SQJG_CODE")
                            .toString();
                    String SQJG_LEALPERSON = lastExeInfo.get("SQJG_LEALPERSON") == null ? "" : lastExeInfo.get(
                            "SQJG_LEALPERSON").toString();
                    String SQJG_TEL = lastExeInfo.get("SQJG_TEL") == null ? "" : lastExeInfo.get("SQJG_TEL").toString();
                    String SQJG_ADDR = lastExeInfo.get("SQJG_ADDR") == null ? "" : lastExeInfo.get("SQJG_ADDR")
                            .toString();

                    String JBR_MOBILE = lastExeInfo.get("JBR_MOBILE") == null ? "" : lastExeInfo.get("JBR_MOBILE")
                            .toString();
                    if (StringUtils.isNotEmpty(JBR_MOBILE)) {
                        request.setAttribute("JBR_MOBILE", JBR_MOBILE);
                    }
                    if (StringUtils.isNotEmpty(lineName)) {
                        request.setAttribute("lineName", lineName);
                    }
                    if (StringUtils.isNotEmpty(lineCard)) {
                        request.setAttribute("lineCard", lineCard);
                    }
                    if (StringUtils.isNotEmpty(lineId)) {
                        request.setAttribute("lineId", lineId);
                    }
                    if (StringUtils.isNotEmpty(zjlx)) {
                        request.setAttribute("zjlx", zjlx);
                    }
                    if (StringUtils.isNotEmpty(SQJG_NAME)) {
                        request.setAttribute("SQJG_NAME", SQJG_NAME);
                    }
                    if (StringUtils.isNotEmpty(SQJG_TYPE)) {
                        request.setAttribute("SQJG_TYPE", SQJG_TYPE);
                    }
                    if (StringUtils.isNotEmpty(SQJG_CODE)) {
                        request.setAttribute("SQJG_CODE", SQJG_CODE);
                    }
                    if (StringUtils.isNotEmpty(SQJG_LEALPERSON)) {
                        request.setAttribute("SQJG_LEALPERSON", SQJG_LEALPERSON);
                    }
                    if (StringUtils.isNotEmpty(SQJG_TEL)) {
                        request.setAttribute("SQJG_TEL", SQJG_TEL);
                    }
                    if (StringUtils.isNotEmpty(SQJG_ADDR)) {
                        request.setAttribute("SQJG_ADDR", SQJG_ADDR);
                    }
                }
            }
        } else {
            if (StringUtils.isNotEmpty(lineName)) {
                request.setAttribute("lineName", lineName);
            }
            if (StringUtils.isNotEmpty(lineCard)) {
                request.setAttribute("lineCard", lineCard);
            }
            if (StringUtils.isNotEmpty(lineId)) {
                request.setAttribute("lineId", lineId);
            }
            if (StringUtils.isNotEmpty(zjlx)) {
                request.setAttribute("zjlx", zjlx);
            }
        }

        request.setAttribute("flowstage", request.getParameter("flowstage"));
    }

    /**
     * 跳转到新申请页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "fjfda/foodProduction/foodInfoForm");
    }
    
    /**
     * 描述 获取其他子表信息
     * @author Keravon Feng
     * @created 2018年10月23日 上午11:29:39
     * @param request
     * @param busRecordId
     */
    private void getOtherFormData(HttpServletRequest request, String bus_recordid) {
     // 获取技术人员列表
        List<Map<String, Object>> jsryList = foodProductionService.findJsry(bus_recordid);
        request.setAttribute("jsryList", jsryList);
        // 获取产品信息列表
        List<Map<String, Object>> cpxxList = foodProductionService.findCpxx(bus_recordid,"","");
        request.setAttribute("cpxxList", cpxxList);
        List<Map<String, Object>> jgcsList = foodProductionService.findJgcs(bus_recordid);
        Map<String, List> jgcs = new LinkedHashMap<String, List>(); 
        for (Map<String, Object> map : jgcsList) {
            String lbid = map.get("LBBH").toString();
            if (jgcs.containsKey(lbid)) {
                jgcs.get(lbid).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                jgcs.put(lbid, j);
            }
        }
        request.setAttribute("jgcs", jgcs);
        List<Map<String, Object>> sbssList = foodProductionService.findSbss(bus_recordid);
        Map<String, List> sbss = new LinkedHashMap<String, List>(); 
        for (Map<String, Object> map : sbssList) {
            String lbid = map.get("LBMC").toString();
            if (sbss.containsKey(lbid)) {
                sbss.get(lbid).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                sbss.put(lbid, j);
            }
        }
        request.setAttribute("sbss", sbss);
        List<Map<String, Object>> jyyqList = foodProductionService.findJyyq(bus_recordid);
        request.setAttribute("jyyqList", jyyqList);
        request.setAttribute("aqzdList", foodProductionService.findAqzd(bus_recordid));
        Map<String, Object> EFLOW_VARS = new HashMap<String, Object>();
        Map<String, Object> eflow_busrecord = new HashMap<String, Object>();
        Map<String, Object> residenceInfo = foodProductionService.getAddressMap(bus_recordid, "1");
        List<Map<String, Object>> scdzList = foodProductionService.getDzList(bus_recordid,"2");
        //foodProductionService.getAddressMap(bus_recordid, "2");
        List<Map<String, Object>> storagePlace = foodProductionService.getDzList(bus_recordid, "3");
        if (residenceInfo != null) {
            eflow_busrecord.putAll(residenceInfo);
        }
        if (scdzList != null) {
            eflow_busrecord.put("scdzList",scdzList);
        }
        if (storagePlace != null) {
            eflow_busrecord.put("ckdzList",storagePlace);
        }
        EFLOW_VARS.put("EFLOW_BUSRECORD", eflow_busrecord);
        String EFLOW_VARS_JSON = JSON.toJSONString(EFLOW_VARS);
        request.setAttribute("EFLOW_VARS", EFLOW_VARS);
        request.setAttribute("EFLOW_VARS_JSON", StringEscapeUtils.escapeHtml3(EFLOW_VARS_JSON));
        
    }
    /**
     * 
     * 描述 获取其他子表数据
     * 
     * @author Rider Chen
     * @created 2016年7月6日 上午11:29:34
     * @param request
     * @param EFLOW_VARS
     * @param bus_recordid
     */
    public void getOtherData(HttpServletRequest request, Map<String, Object> EFLOW_VARS, String bus_recordid,
            String isQueryDetail,String EFLOW_ASSIGNER_TYPE) {
        // 获取技术人员列表
        List<Map<String, Object>> jsryList = foodProductionService.findJsry(bus_recordid);
        request.setAttribute("jsryList", jsryList);
        // 获取产品信息列表
        List<Map<String, Object>> cpxxList = foodProductionService.findCpxx(bus_recordid, 
                isQueryDetail,EFLOW_ASSIGNER_TYPE);
        request.setAttribute("cpxxList", cpxxList);

        List<Map<String, Object>> jgcsList = foodProductionService.findJgcs(bus_recordid);
        Map<String, List> jgcs = new LinkedHashMap<String, List>(); 
        for (Map<String, Object> map : jgcsList) {
            String lbid = map.get("LBBH").toString();
            if (jgcs.containsKey(lbid)) {
                jgcs.get(lbid).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                jgcs.put(lbid, j);
            }
        }
        request.setAttribute("jgcs", jgcs);
        List<Map<String, Object>> sbssList = foodProductionService.findSbss(bus_recordid);
        Map<String, List> sbss = new LinkedHashMap<String, List>(); 
        for (Map<String, Object> map : sbssList) {
            String lbid = map.get("LBMC").toString();
            if (sbss.containsKey(lbid)) {
                sbss.get(lbid).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                sbss.put(lbid, j);
            }
        }
        request.setAttribute("sbss", sbss);
        List<Map<String, Object>> jyyqList = foodProductionService.findJyyq(bus_recordid);
        request.setAttribute("jyyqList", jyyqList);
        request.setAttribute("aqzdList", foodProductionService.findAqzd(bus_recordid));
        Map<String, Object> eflow_busrecord = (Map) EFLOW_VARS.get("EFLOW_BUSRECORD");
        Map<String, Object> residenceInfo = foodProductionService.getAddressMap(bus_recordid, "1");
        List<Map<String, Object>> scdzList = foodProductionService.getDzList(bus_recordid,"2");
        //foodProductionService.getAddressMap(bus_recordid, "2");
        List<Map<String, Object>> storagePlace = foodProductionService.getDzList(bus_recordid, "3");
        if (residenceInfo != null) {
            eflow_busrecord.putAll(residenceInfo);
        }
        if (scdzList != null) {
            eflow_busrecord.put("scdzList",scdzList);
        }
        if (storagePlace != null) {
            eflow_busrecord.put("ckdzList",storagePlace);
        }
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
        String entityId = request.getParameter("JBXX_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = foodProductionService.saveOrUpdate(variables, "T_FJFDA_SPSCXK_JBXX", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 食品生产记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 食品生产记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到技术人员界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshTechnicalPersonnelDiv")
    public ModelAndView refreshTechnicalPersonnelDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/fjfda/foodProduction/refreshTechnicalPersonnelDiv");
    }

    /**
     * 跳转到产品信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshCpxxDiv")
    public ModelAndView refreshCpxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        if(!StringUtils.isEmpty(request.getParameter("curDepartId"))){
            request.setAttribute("curDepartId", request.getParameter("curDepartId"));
        }
        request.setAttribute("ZZXX_STATE", request.getParameter("ZZXX_STATE"));
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/fjfda/foodProduction/cpxxDiv");
    }
    
    @RequestMapping("/refreshScdzDiv")
    public ModelAndView refreshScdzDiv(HttpServletRequest request){
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        request.setAttribute("name",request.getParameter("name"));
        return new ModelAndView("bsdt/applyform/fjfda/foodProduction/scdzDiv");
    }
    
    @RequestMapping("/refreshHealthCpxxDiv")
    public ModelAndView refreshHealthCpxxDiv(HttpServletRequest request){
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        request.setAttribute("ZZXX_STATE", request.getParameter("ZZXX_STATE"));
        return new ModelAndView("bsdt/applyform/fjfda/healthFood/cpxxDiv");
    }
    /**
     * easyui AJAX请求数据 产品信息部门
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getCpxxDept")
    public void getCpxxDept(HttpServletRequest request, HttpServletResponse response) {
        String typeId = request.getParameter("typeId");// 食品类别ID
        // 食品类别
        Map<String, Object> busType = foodProductionService.getByJdbc("T_FJFDA_BUSTYPE", new String[] { "TYPE_ID" },
                new Object[] { typeId });
        String json = "";
        //区县平潭综合实验区(对省级而言固定为平潭实验区)
        Map<String,Object> aree = new HashMap<String, Object>();
        aree.put("DEPART_ID", "402885c6550146ec01550146ecf70004");
        aree.put("DEPART_NAME", "平潭综合实验区市场监督管理局");
        aree.put("DEPART_CODE", "402885c6550146ec01550146ecf70004");
        aree.put("CITY_CODE", "350128");
        aree.put("ISPROVINCE", "0");
        aree.put("TYPE_CODE", busType.get("TYPE_CODE"));
        json = JSON.toJSONString(aree);
        this.setJsonString(json, response);
    }
    
   /**
    * 
    * 描述 获取食品生产信息
    * @author Rider Chen
    * @created 2016年7月11日 上午10:55:56
    * @param request
    * @param response
    */
    @RequestMapping("/getSpscxx")
    public void getSpscxx(HttpServletRequest request, HttpServletResponse response) {
        String SPSCXKZBH = request.getParameter("SPSCXKZBH");
        String ISPROVINCE = request.getParameter("ISPROVINCE");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> xpscxkxxMap =foodProductionService.getSpscxkxxMap(SPSCXKZBH,ISPROVINCE,"0");
        if(null!=xpscxkxxMap){
            List<Map<String,Object>> cpList = foodProductionService.getZzCp(
                    xpscxkxxMap.get("ZZXX_ID").toString(), "0");
            if(cpList.size() != 0){
                String bus_recordid = xpscxkxxMap.get("JBXX_ID")==null?"":xpscxkxxMap.get("JBXX_ID").toString();
                //Map<String, Object> jbxxMap = foodProductionService.getByJdbc("T_FJFDA_SPSCXK_JBXX",
                        //new String[]{"JBXX_ID"}, new Object[]{bus_recordid});
                //xpscxkxxMap.putAll(jbxxMap);
                result.put("jbxxMap", xpscxkxxMap);
                //住所地址
                Map<String, Object> residenceInfo = foodProductionService.getAddressMap(bus_recordid, "1");
                result.put("zsdzMap", residenceInfo);
                //住所地址
                Map<String, Object> zcdz = foodProductionService.getAddressMap(bus_recordid, "3");
                result.put("ckdzMap", zcdz);
                //生产地址
                List<Map<String, Object>> scdzList = foodProductionService.getDzList(bus_recordid,"2");
                result.put("scdzList", scdzList);
                result.put("cpxxList", cpList);
                result.put("msg", "验证成功");
                result.put("success", true);
            }else{
                result.put("msg", "验证失败");
                result.put("success", false);
                result.put("msg", "验证失败，该食品生产许可证编号只有保健食品，请申请食品生产许可相关事项！");
            }
        } else {
            result.put("msg", "验证失败,食品生产许可证编号不存在！");
            result.put("success", false);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到 保健食品核发
     * 
     * @param request
     * @return
     */
    @RequestMapping("/healthInfo")
    public ModelAndView healthInfo(HttpServletRequest request) {
        return this.toHealthFoodForm(request, "fjfda/healthFood/foodInfoForm");
    }
    /**
     * 
     * 描述  保健食品生产许可变更
     * @author John Zhang
     * @created 2016年8月24日 上午9:21:40
     * @param request
     * @return
     */
    @RequestMapping("/healthChangeInfo")
    public ModelAndView healthChangeInfo(HttpServletRequest request){
        return this.toHealthFoodForm(request, "business/healthFoodChange/foodInfoForm");
    }
    /**
     * 描述 保健食品生产许可换证
     * @author John Zhang
     * @created 2016年8月24日 上午9:22:36
     * @param request
     * @return
     */
    @RequestMapping("/healthReplaceInfo")
    public ModelAndView healthReplaceInfo(HttpServletRequest request){
        return this.toHealthFoodForm(request, "business/healthFoodChange/foodInfoForm");
    }   
    
    /**
     * 描述 跳转到保健食品
     * @author Keravon Feng
     * @created 2018年10月25日 上午9:56:52
     * @param request
     * @param frompath
     * @return
     */
    public ModelAndView toHealthFoodForm(HttpServletRequest request,String frompath){
     // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申请方式
        String applyType = request.getParameter("applyType");
        // 获取是否平潭网格政务门户申请
        String ptwgType = request.getParameter("ptwgType");
        // 获取流程申报号
        String exeId = request.getParameter("exeId");
        String EFLOW_ISQUERYDETAIL = request.getParameter("EFLOW_ISQUERYDETAIL");
        String lineName = request.getParameter("lineName");
        String lineCard = request.getParameter("lineCard");
        String lineId = request.getParameter("lineId");
        String zjlx = request.getParameter("zjlx");
        // 定义是否是开始节点
        String EFLOW_IS_START_NODE = "";
        if (StringUtils.isNotEmpty(EFLOW_ISQUERYDETAIL) && EFLOW_ISQUERYDETAIL.equals("true")) {
            EFLOW_IS_START_NODE = "false";
        }
        Map<String, Object> execution = null;
        int flowVersion = 0;
        String currentNodeName = null, startNodeName = null, defId = null;
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            execution = executionService
                    .getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            // 获取流程状态
            String runStatus = execution.get("RUN_STATUS").toString();
            if (runStatus.equals("0")) {
                EFLOW_IS_START_NODE = "true";
            } else {
                // 获取当前流程流转节点
                currentNodeName = (String) execution.get("CUR_STEPNAMES");
                defId = (String) execution.get("DEF_ID");
                flowVersion = Integer.parseInt(execution.get("DEF_VERSION").toString());
                // 获取开始节点的名称
                startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                if (startNodeName.equals(currentNodeName)) {
                    EFLOW_IS_START_NODE = "true";
                } else {
                    EFLOW_IS_START_NODE = "false";
                }
            }
            itemCode = (String) execution.get("ITEM_CODE");
            
        } else {
            EFLOW_IS_START_NODE = "true";
        }
        Map<String, Object> serviceItem = 
                serviceItemService.getItemAndDefInfoNew(itemCode);// getItemAndDefInfo(itemCode);
        if (serviceItem != null) {
            return ifNotNull2(request, itemCode, applyType, ptwgType, exeId, lineName, lineCard, lineId, zjlx,
                    EFLOW_IS_START_NODE, execution, flowVersion, currentNodeName, defId, serviceItem,frompath);
        } else {
            return null;
        }
    }
    
    /**
     * 
     * 描述
     * @author Keravon Feng
     * @created 2018年10月25日 上午9:57:20
     * @return
     */
    private ModelAndView ifNotNull2(HttpServletRequest request, String itemCode, String applyType, String ptwgType,
            String exeId, String lineName, String lineCard, String lineId, String zjlx, String EFLOW_IS_START_NODE,
            Map<String, Object> execution, int flowVersion, String currentNodeName, String defId,
            Map<String, Object> serviceItem,String frompath) {
        if (flowVersion == 0) {
            String defKey = (String) serviceItem.get("DEF_KEY");// 获取流程定义KEY
            Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_KEY" },
                    new Object[] { defKey });
            flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            defId = (String) flowDef.get("DEF_ID");
            currentNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
        }
        String formKey = (String) serviceItem.get("FORM_KEY");
        String itemId = (String) serviceItem.get("ITEM_ID");// 获取项目ID
        Map<String, Object> nodeConfig = nodeConfigService.getByJdbc("JBPM6_NODECONFIG", new String[] { "NODE_NAME",
                "DEF_ID", "DEF_VERSION" }, new Object[] { currentNodeName, defId, flowVersion });
        if (nodeConfig != null) {
            request.setAttribute("nodeConfig", nodeConfig);
        }
        // 获取材料信息列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        String busRecordId = "";
        String busTableName = "";
        if (execution != null) {
            busRecordId = execution.get("BUS_RECORDID") == null ?
                    "" : execution.get("BUS_RECORDID").toString();// 获取业务ID
            busTableName = execution.get("BUS_TABLENAME") == null ?
                    "" : execution.get("BUS_TABLENAME").toString();// 获取业务表名称
        }
        List<Map<String, Object>> ysqMaters = null;
        if (StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(busTableName)) {
            ysqMaters = fileAttachService.findYsqByList(busTableName, busRecordId);
        }
        List<Map<String, Object>> filterApplyMaters = new ArrayList<Map<String, Object>>();
        if (applyMaters != null && applyMaters.size() > 0) {
            filterApplyMaters.addAll(applyMaters);
        }
        if (StringUtils.isNotEmpty(currentNodeName) && StringUtils.isNotEmpty(exeId) && applyMaters != null
                && applyMaters.size() > 0) {
            String sysUserName = AppUtil.getLoginUser() == null ? "" : AppUtil.getLoginUser().getUsername();
            List<Map<String, Object>> filterMater = applyMaterService.findFilterMater(sysUserName, currentNodeName,
                    exeId);
            if (filterMater != null && filterMater.size() > 0) {
                applyMaters.clear();
                for (int i = 0; i < filterMater.size(); i++) {
                    Map<String, Object> m = filterMater.get(i);
                    for (int j = 0; j < filterApplyMaters.size(); j++) {
                        Map<String, Object> fmap = filterApplyMaters.get(j);
                        if (m.get("MATER_ID").toString().equals(fmap.get("MATER_ID").toString())) {
                            applyMaters.add(fmap);
                        }
                    }
                }
            }
        }
        if (StringUtils.isEmpty(applyType)) {
            applyType = "2";
        }
        // 获取流程实例信息
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            applyMaters = getFlowCaseInfo(execution, request, applyMaters);
            ysqMaters = getFlowCaseInfo(execution, request, ysqMaters);
            applyType = (String) execution.get("SQFS");
        }
        serviceItem.put("APPLY_TYPE", applyType);
        // 查找指定的事项需要特殊处理的环节
        Map<String, Object> dealItem = serviceItemService.getParticularDealNode(itemCode);
        // 定义材料列表JSON
        String ysqMatersJson = "";
        if (ysqMaters != null && ysqMaters.size() > 0) {
            ysqMatersJson = JsonUtil.jsonStringFilter(ysqMaters, new String[] { "uploadFiles" }, true);
        }
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("ysqMaters", ysqMaters);
        request.setAttribute("ysqMatersJson", StringEscapeUtils.escapeHtml3(ysqMatersJson));
        request.setAttribute("EFLOW_IS_START_NODE", EFLOW_IS_START_NODE);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        request.setAttribute("dealItem", dealItem);
        setRequestVal(lineName, lineCard, lineId, zjlx, request);
        // 获取其他子表数据
        Map<String, Object> EFLOW_VARS = new HashMap<String, Object>();
        EFLOW_VARS.put("EFLOW_IS_START_NODE", EFLOW_IS_START_NODE);
        getOtherHealthFormData(request,EFLOW_VARS,execution,busRecordId);        
        if (StringUtils.isNotEmpty(ptwgType)) {
            return new ModelAndView("bsdt/ptwgform/" + formKey);
        } else {
            return new ModelAndView("bsdt/applyform/" + frompath);
        }
    }
    /**
     * 描述 获取其它子表的信息
     * @author Keravon Feng
     * @created 2018年10月25日 上午9:46:39
     * @param request
     * @param execution
     * @param busRecordId
     */
    private void getOtherHealthFormData(HttpServletRequest request,
            Map<String, Object> EFLOW_VARS, 
            Map<String, Object> execution, String bus_recordid) {
        Map<String, Object> eflow_busrecord = new HashMap<String, Object>();
        if(execution != null){
         // 获取其他子表数据
            // 获取技术人员列表
            List<Map<String, Object>> jsryList = foodProductionService.findJsry(bus_recordid);
            request.setAttribute("jsryList", jsryList);
            // 获取产品信息列表
            List<Map<String, Object>> cpxxList = foodProductionService.findCpxx(bus_recordid, 
                    "","");
            request.setAttribute("cpxxList", cpxxList);

            List<Map<String, Object>> jgcsList = foodProductionService.findJgcs(bus_recordid);
            request.setAttribute("jgcsList", jgcsList);
            List<Map<String, Object>> sbssList = foodProductionService.findSbss(bus_recordid);
            request.setAttribute("sbssList", sbssList);
            List<Map<String, Object>> jyyqList = foodProductionService.findJyyq(bus_recordid);
            request.setAttribute("jyyqList", jyyqList);
            request.setAttribute("aqzdList", foodProductionService.findAqzd(bus_recordid));
            Map<String, Object> residenceInfo = foodProductionService.getAddressMap(bus_recordid, "1");
            List<Map<String, Object>> scdzList = foodProductionService.getDzList(bus_recordid,"2");
            //foodProductionService.getAddressMap(bus_recordid, "2");
            List<Map<String, Object>> storagePlace = foodProductionService.getDzList(bus_recordid, "3");
            if (residenceInfo != null) {
                eflow_busrecord.putAll(residenceInfo);
            }
            if (scdzList != null) {
                eflow_busrecord.put("scdzList",scdzList);
            }
            if (storagePlace != null) {
                eflow_busrecord.put("ckdzList",storagePlace);
            }
        }
        EFLOW_VARS.put("EFLOW_BUSRECORD", eflow_busrecord);
        String EFLOW_VARS_JSON = JSON.toJSONString(EFLOW_VARS);
        request.setAttribute("EFLOW_VARS", EFLOW_VARS);
        request.setAttribute("EFLOW_VARS_JSON", StringEscapeUtils.escapeHtml3(EFLOW_VARS_JSON));
    }

    /**
     * 描述  加工场所html
     * @author John Zhang
     * @created 2016年8月17日 下午7:28:06
     * @param request
     * @return
     */
    @RequestMapping("/getJgcsHtml")
    public ModelAndView getJgcsHtml(HttpServletRequest request) {
        List<Map<String, Object>> jgcsList = foodProductionService.findJgcs(request.getParameter("jbxx_id"));
        Map<String, List> jgcs = new HashMap<String, List>(); 
        for (Map<String, Object> map : jgcsList) {
            String lbid = map.get("LBBH").toString();
            if (jgcs.containsKey(lbid)) {
                jgcs.get(lbid).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                jgcs.put(lbid, j);
            }
        }
        Map<String, Object> EFLOW_VARS = new HashMap<String, Object>();
        EFLOW_VARS.put("EFLOW_IS_START_NODE", "true");
        request.setAttribute("jgcs", jgcs);
        request.setAttribute("EFLOW_VARS", EFLOW_VARS);
        return new ModelAndView("business/foodProduction/processingPlaceForm");
    }
    /**
     * 描述 获取检验仪器html
     * @author John Zhang
     * @created 2016年11月9日 上午10:34:28
     * @param request
     * @return
     */
    @RequestMapping("/getJyyqHtml")
    public ModelAndView getJyyqHtml(HttpServletRequest request){
        List<Map<String, Object>> jyyqList = foodProductionService.findJyyq(request.getParameter("jbxx_id"));
        request.setAttribute("jyyqList", jyyqList);
        Map<String, Object> EFLOW_VARS = new HashMap<String, Object>();
        EFLOW_VARS.put("EFLOW_IS_START_NODE", "true");
        request.setAttribute("EFLOW_VARS", EFLOW_VARS);
        return new ModelAndView("bsdt/applyform/fjfda/foodProduction/jyyqForm");
    }
    
    /**
     * 描述  设备设施html
     * @author John Zhang
     * @created 2016年8月17日 下午7:28:06
     * @param request
     * @return
     */
    @RequestMapping("/getSbssHtml")
    public ModelAndView getSbssHtml(HttpServletRequest request) {
        List<Map<String, Object>> sbssList = foodProductionService.findSbss(request.getParameter("jbxx_id"));
        Map<String, List> sbss = new HashMap<String, List>(); 
        for (Map<String, Object> map : sbssList) {
            String lbid = map.get("LBID").toString();
            if (sbss.containsKey(lbid)) {
                sbss.get(lbid).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                sbss.put(lbid, j);
            }
        }
        Map<String, Object> EFLOW_VARS = new HashMap<String, Object>();
        EFLOW_VARS.put("EFLOW_IS_START_NODE", "true");
        request.setAttribute("sbss", sbss);
        request.setAttribute("EFLOW_VARS", EFLOW_VARS);
        return new ModelAndView("bsdt/applyform/fjfda/foodProduction/equipmentForm");
    }
    /**
     * 根据邮政编码 获取部门
     */
    @RequestMapping("/getDepartId")
    public void getDepartId(HttpServletRequest request,HttpServletResponse response){
        Map<String, String> result = new HashMap<String, String>();
        Map<String, Object> city = foodProductionService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] {
                "CITY_CODE", "TREE_LEVEL","PARENT_ID"},
                new Object[] { request.getParameter("CITY_CODE"), "3","7D120C9034154F0E0000280000000037"});
        Map<String, Object> province = departmentService.getRootDepart();
        result.put("success", "true");
        result.put("city", city.get("DEPART_ID").toString());
        result.put("province", province.get("DEPART_ID").toString());
        this.setJsonString(JSON.toJSONString(result), response);
    }
    /**
     * 描述 根据许可证号获取保健食品信息
     * @author John Zhang
     * @created 2016年8月24日 下午5:57:07
     * @param request
     * @param response
     */
    @RequestMapping("/getHealthSpscxx")
    public void getHealthSpscxx(HttpServletRequest request,HttpServletResponse response){
        String xkzbh = request.getParameter("XKZBH");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> xpscxkxxMap =foodProductionService.getSpscxkxxMap(xkzbh,"1","1");
        if(xpscxkxxMap != null){
             List<Map<String,Object>> cpList = foodProductionService.getZzCp(
                    xpscxkxxMap.get("ZZXX_ID").toString(), "2");
            if(cpList.size() != 0){
                String bus_recordid = xpscxkxxMap.get("JBXX_ID")==null?"":xpscxkxxMap.get("JBXX_ID").toString();
                //Map<String, Object> jbxxMap = foodProductionService.getByJdbc("T_FJFDA_SPSCXK_JBXX",
                        //new String[]{"JBXX_ID"}, new Object[]{bus_recordid});
                //xpscxkxxMap.putAll(jbxxMap);
                result.put("jbxxMap", xpscxkxxMap);
                // 获取技术人员列表
                List<Map<String, Object>> jsryList = foodProductionService.findJsry(bus_recordid);
                result.put("jsryList", jsryList);
                //住所地址
                Map<String, Object> residenceInfo = foodProductionService.getAddressMap(bus_recordid, "1");
                result.put("zsdzMap", residenceInfo);
                //住所地址
                Map<String, Object> zcdz = foodProductionService.getAddressMap(bus_recordid, "3");
                result.put("ckdzMap", zcdz);
                result.put("aqzdList", foodProductionService.findAqzd(bus_recordid));
                //生产地址
                List<Map<String, Object>> scdzList = foodProductionService.getDzList(bus_recordid,"2");
                result.put("scdzList", scdzList);
                List<Map<String, Object>> jgcsList = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> jyyqList = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> sbssList = new ArrayList<Map<String, Object>>();
                String lastJbxxid = "";
                for (Map<String, Object> cp : cpList) {
                    String jbxxId = (String)cp.get("JBXX_ID");
                    if(!lastJbxxid.equals(jbxxId) && jbxxId != null){
                        lastJbxxid = jbxxId;
                        jgcsList.addAll(foodProductionService.findJgcs(jbxxId));
                        jyyqList.addAll(foodProductionService.findJyyq(jbxxId));
                        sbssList.addAll(foodProductionService.findSbss(jbxxId));
                    }
                }
                result.put("jgcsList", jgcsList);
                result.put("sbssList", sbssList);
                result.put("jyyqList", jyyqList);
                result.put("cpxxList", cpList);
                result.put("msg", "验证成功");
                result.put("success", true);
            }else{
                result.put("msg", "验证失败");
                result.put("success", false);
                result.put("msg", "验证失败，该食品生产许可证编号不存在保健食品，请申请保健食品生产许可相关事项！");
            }
        }else {
            result.put("msg", "验证失败,食品生产许可证编号不存在！");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述 判断是否有历史证照数据
     * @author John Zhang
     * @created 2016年8月30日 下午4:08:29
     * @param request
     * @return
     */
    @RequestMapping("/isExistsShxydm")
    @ResponseBody
    public AjaxJson isExistsShxydm(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String shxydm = request.getParameter("SHXYDMSFZHM");
        String isHealth = request.getParameter("IS_HEALTH");
        if(StringUtils.isNotEmpty(shxydm)){
            Map<String,Object> resultMapList = foodProductionService.getByShxydm(shxydm, isHealth);
            if(resultMapList==null){
                j.setSuccess(true);
            }else{
                String msg = "该统一社会信用代码已存在证照库中,仅可申报变更业务。";
                j.setMsg(msg); 
                j.setSuccess(false);
            }
        }
        return j;
    }
    
    @RequestMapping("getOldSpscxx")
    public void getOldSpscxx(HttpServletRequest request,HttpServletResponse response){
        String json = "";
        String xkzbh = request.getParameter("XKZBH");
        Map<String, Object> xkxx = foodProductionService.getByJdbc("T_FJFDA_SPSCXKXX",
                new String[]{"XKZBH","XKZZT"}, new Object[]{xkzbh, "10"});
        if(xkxx != null){
            String jbxxId = (String) (xkxx.get("MERGE_JBXX_ID")==null?xkxx.get("JBXX_ID"):xkxx.get("MERGE_JBXX_ID"));
            if(jbxxId != null){
                xkxx.put("CPXXLIST", foodProductionService.getCpxxInfo(jbxxId, ""));
                json = JSON.toJSONString(xkxx);
            }
        }
        this.setJsonString(json, response);
    }
    
    /**
     * 描述 选择产品操作
     * @author John Zhang
     * @created 2017年2月27日 下午2:09:48
     * @param request
     * @return
     */
    @RequestMapping("/cpSelector")
    public ModelAndView cpSelector(HttpServletRequest request){
        List<Map<String,Object>> cpList = foodProductionService.getZzCp(
                request.getParameter("ZZXX_ID"), "1");
        Map<String,List<Map<String,Object>>> cpxxMap = new LinkedHashMap<String,List<Map<String,Object>>>();
        for (Map<String, Object> map : cpList) {
            String jbxxId = map.get("JBXX_ID").toString();
            if (cpxxMap.containsKey(jbxxId)) {
                cpxxMap.get(jbxxId).add(map);
            }else{
                List j = new ArrayList<Map<String, Object>>();
                j.add(map);
                cpxxMap.put(jbxxId, j);
            }
        }
        request.setAttribute("cpxxMap", cpxxMap);
        return new ModelAndView("bsdt/applyform/fjfda/foodProductionChange/cpSelect"); 
    }
    /**
     * 描述
     * @author John Zhang
     * @created 2017年2月27日 下午3:41:19
     * @param request
     * @param response
     */
    @RequestMapping("/getOtherData")
    public void getOtherData(HttpServletRequest request,HttpServletResponse response){
        String JBXX_ID = request.getParameter("JBXX_ID");
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> jsryList = foodProductionService.findJsry(JBXX_ID);
        //List<Map<String, Object>> jyyqList = foodProductionService.findJyyq(JBXX_ID);
       // result.put("jyyqList", jyyqList);
        result.put("aqzdList", foodProductionService.findAqzd(JBXX_ID));
        result.put("jsryList", jsryList);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述  删除证照旧产品
     * @author John Zhang
     * @created 2017年5月11日 下午5:32:23
     * @param request
     * @return
     */
    @RequestMapping(params="delOldCpxx")
    @ResponseBody
    public AjaxJson delOldCpxx(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        foodProductionService.remove("T_FJFDA_SPSCXKXX_CP", new String[]{"SPSCXKXX_CP_ID"}, 
                new Object[]{request.getParameter("cpid")});
        Map<String, Object> docData = (Map<String, Object>) AppUtil.getSession().getAttribute("DOCUMENT_DATAS");
        List<Map<String, Object>> cpxxList = (List<Map<String, Object>>)docData.get("CPXXLIST");
        Iterator<Map<String, Object>> it = cpxxList.iterator();
        while(it.hasNext()){
            Map<String, Object> cpxx = it.next();
            if(request.getParameter("cpid").equals((String)cpxx.get("SPSCXKXX_CP_ID"))){
                it.remove();
                break;
            }
        }
        docData.put("CPXXLIST", cpxxList);
        AppUtil.getSession().setAttribute("DOCUMENT_DATAS",docData);
        return j;
    }
    
    /**
     * 描述 根据流程实例 获取办件信息
     * @author John Zhang
     * @created 2017年2月27日 下午3:41:19
     * @param request
     * @param response
     */
    @RequestMapping("/getInfoByExeid")
    public void getInfoByExeid(HttpServletRequest request,HttpServletResponse response){
        String exe_id = request.getParameter("exe_id");
        Map<String, Object> result = foodProductionService.getByJdbc("T_FJFDA_SPSCXK_JBXX",
                new String[]{"EFLOW_EXEID"}, new Object[]{exe_id});
        if(result == null){
            this.setJsonString("{}", response);
            return;
        }
        String bus_recordid = (String)result.get("JBXX_ID");
      //住所地址
        Map<String, Object> residenceInfo = foodProductionService.getAddressMap(bus_recordid, "1");
        result.put("zsdzMap", residenceInfo);
        //住所地址
        Map<String, Object> zcdz = foodProductionService.getAddressMap(bus_recordid, "3");
        result.put("ckdzMap", zcdz);
        //生产地址
        List<Map<String, Object>> scdzList = foodProductionService.getDzList(bus_recordid,"2");
        result.put("scdzList", scdzList);
        result.put("aqzdList", foodProductionService.findAqzd(bus_recordid));
        result.put("jsryList", foodProductionService.findJsry(bus_recordid));
        result.put("cpxxList", foodProductionService.findCpxx(bus_recordid, null, null));
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
}
