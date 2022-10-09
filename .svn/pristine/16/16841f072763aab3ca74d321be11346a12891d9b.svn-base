/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.fjfda.service.FoodManagementService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述  食品经营Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/foodManagementController")
public class FoodManagementController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FoodManagementController.class);
    /**
     * 引入Service
     */
    @Resource
    private FoodManagementService foodManagementService;
    /**
     * 
     */
    @Resource
    private FlowTaskService flowTaskService;
    
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
     * 
     * 描述 跳转到食品经营许可相关表单
     * @author Flex Hu
     * @created 2016年6月21日 下午2:17:20
     * @param request
     * @return
     */
    public ModelAndView toFoodBusinessForm(HttpServletRequest request,String formPath){
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
                    EFLOW_IS_START_NODE, execution, flowVersion, currentNodeName, defId, serviceItem,formPath);
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
        Map<String, Object> EFLOW_VARS = new HashMap<String, Object>();
        Map<String, Object> eflow_busrecord = new HashMap<String, Object>();
        EFLOW_VARS.put("EFLOW_BUSRECORD", eflow_busrecord);
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
            applyMaters = getFlowCaseInfo(execution,EFLOW_VARS, request, applyMaters);
            ysqMaters = getFlowCaseInfo(execution,EFLOW_VARS, request, ysqMaters);
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
        getOtherData(request,EFLOW_VARS,busRecordId);        
        if (StringUtils.isNotEmpty(ptwgType)) {
            return new ModelAndView("bsdt/ptwgform/" + formKey);
        } else {
            return new ModelAndView("bsdt/applyform/" + frompath);
        }
    }
    
    /**
     * 
     * 描述
     * @author Keravon Feng
     * @created 2018年10月23日 下午4:12:55
     * @param execution
     * @param request
     * @param applyMaters
     * @return
     */
    public List<Map<String, Object>> getFlowCaseInfo(Map<String, Object> execution,
            Map<String, Object> EFLOW_VARS,HttpServletRequest request,
            List<Map<String, Object>> applyMaters) {
        String busRecordId = (String) execution.get("BUS_RECORDID");// 获取业务ID
        String busTableName = (String) execution.get("BUS_TABLENAME");// 获取业务表名称
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 获取业务表主键名称
        String busPkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
        // 获取业务记录
        Map<String, Object> busRecord = executionService.getByJdbc(busTableName, 
                new String[] { busPkName },
                new Object[] { busRecordId });
        applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        request.setAttribute("execution", execution);
        // 获取多证合一数据
        Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                new String[] { "COMPANY_ID" }, new Object[] { busRecordId });
        if (null != multiple) {
            busRecord.putAll(multiple);
        }
        Map<String,Object> eflow_busrecord = (Map)EFLOW_VARS.get("EFLOW_BUSRECORD");
        if(eflow_busrecord!=null){
            eflow_busrecord.putAll(busRecord); 
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
     * 
     * 描述 跳转到延续许可界面
     * @author Flex Hu
     * @created 2016年6月21日 上午9:02:00
     * @param request
     * @return
     */
    @RequestMapping("/continueInfo")
    public ModelAndView continueInfo(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "business/foodManagerContinue/foodInfoForm");
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(HttpServletRequest request) {
        return this.toFoodBusinessForm(request,"fjfda/foodManagement/foodInfoForm");
    }
    /**
     * 描述 获取其他子表数据
     * @author Faker Li
     * @created 2016年6月17日 上午11:48:51
     * @param request
     * @param EFLOW_VARS
     * @param bus_recordid
     */
    public void getOtherData(HttpServletRequest request,
            Map<String, Object> EFLOW_VARS, String bus_recordid) {
        //获取技术人员列表
        List<Map<String,Object>> technicalPersonnelList = foodManagementService.findPersonel(bus_recordid,"1");
        //获取从业人员列表
        List<Map<String,Object>> practitionersList = foodManagementService.findPersonel(bus_recordid,"2");
       if(technicalPersonnelList!=null){
            request.setAttribute("technicalPersonnelList", technicalPersonnelList);
        }
        if(practitionersList!=null){
            request.setAttribute("practitionersList", practitionersList);
        }
        //获取法人代表信息
        Map<String,Object> legalRepresentative = foodManagementService.getLegalRepresentative(bus_recordid);
        if(legalRepresentative!=null){
            EFLOW_VARS.put("legalRepresentative", legalRepresentative);
        }
        //获取代理人信息
        Map<String,Object> clinetMap = foodManagementService.getClinetMap(bus_recordid);
        if(clinetMap!=null){
            EFLOW_VARS.put("clinetMap", clinetMap);
        }
        //获取设备信息
        List<Map<String,Object>> facilityEquipmentList = foodManagementService.findFacilityEquipment(bus_recordid);
        if(facilityEquipmentList!=null){
            request.setAttribute("facilityEquipmentList", facilityEquipmentList);
        }
        //获取经营项目数据 
        Map<String,Object> ybzspxs = foodManagementService.getYxm(bus_recordid,"YBZSPXS");
        Map<String,Object> szspxs = foodManagementService.getYxm(bus_recordid,"SZSPXS");
        Map<String,Object> tsspxs = foodManagementService.getYxm(bus_recordid,"TSSPXS");
        Map<String,Object> qtlspxslb = foodManagementService.getYxm(bus_recordid,"QTLSPXSLB");
        Map<String,Object> dllspzs = foodManagementService.getYxm(bus_recordid,"DLLSPZS");
        Map<String,Object> qtlspzslb = foodManagementService.getYxm(bus_recordid,"QTLSPZSLB");
        Map<String,Object> eflow_busrecord = (Map)EFLOW_VARS.get("EFLOW_BUSRECORD");
        //获取上传的网站截图
        String picFileId = (String)eflow_busrecord.get("WZJTLJ");
        if(StringUtils.isNotEmpty(picFileId)){
           Map<String,Object> picFile = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", 
                    new String[]{"FILE_ID"}, new Object[]{picFileId});
           if(picFile!=null){
               request.setAttribute("WZJTLJ_FILE_ID", picFile.get("FILE_ID"));
               request.setAttribute("WZJTLJ_FILE_NAME", picFile.get("FILE_NAME"));
           }
           
        }
        
        if(ybzspxs!=null){
            eflow_busrecord.put("YBZSPXS",ybzspxs.get("JYXM_VALUE"));
        }
        if(szspxs!=null){
            eflow_busrecord.put("SZSPXS",szspxs.get("JYXM_VALUE"));        
        }
        if(tsspxs!=null){
            eflow_busrecord.put("TSSPXS",tsspxs.get("JYXM_VALUE"));
        }
        if(qtlspxslb!=null){
            eflow_busrecord.put("QTLSPXSLB",qtlspxslb.get("JYXM_VALUE"));
        }
        if(dllspzs!=null){
            eflow_busrecord.put("DLLSPZS",dllspzs.get("JYXM_VALUE"));
        }
        if(qtlspzslb!=null){
            eflow_busrecord.put("QTLSPZSLB",qtlspzslb.get("JYXM_VALUE"));
        }
        Map<String,Object> residenceInfo = foodManagementService.getAddressMap(bus_recordid,"1");
        Map<String,Object> placeOfBusiness = foodManagementService.getAddressMap(bus_recordid,"2");
        Map<String,Object> storagePlace = foodManagementService.getAddressMap(bus_recordid,"3");
        if(residenceInfo!=null){
            eflow_busrecord.putAll(residenceInfo);
        }
        if(placeOfBusiness!=null){
            eflow_busrecord.putAll(placeOfBusiness);
        }
        if(storagePlace!=null){
            eflow_busrecord.putAll(storagePlace);
        }
        String EFLOW_VARS_JSON = JSON.toJSONString(EFLOW_VARS);
        request.setAttribute("EFLOW_VARS", EFLOW_VARS);
        request.setAttribute("EFLOW_VARS_JSON", StringEscapeUtils.escapeHtml3(EFLOW_VARS_JSON));
    }
    /**
     * 跳转到技术人员界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "refreshTechnicalPersonnelDiv")
    public ModelAndView refreshTechnicalPersonnelDiv(HttpServletRequest request) {
        String currentTime =  UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String jyzmc = request.getParameter("jyzmc");
        request.setAttribute("jyzmc", jyzmc);
        return new ModelAndView("bsdt/applyform/fjfda/foodManagement/refreshTechnicalPersonnelDiv");
    }
    /**
     * 跳转到从业人员界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "refreshPractitionersDiv")
    public ModelAndView refreshPractitionersDiv(HttpServletRequest request) {
        String currentTime =  UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/fjfda/foodManagement/refreshPractitionersDiv");
    }
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "isExistsExeId")
    @ResponseBody
    public AjaxJson isExistsExeId(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String oldExeId = request.getParameter("OLD_EXEID");
        if(StringUtils.isNotEmpty(oldExeId)){
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{oldExeId});
            if(flowExe!=null){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年6月13日 下午7:19:58
     * @param request
     * @return
     */
    @RequestMapping(params = "storeAfterChangeDataJSON")
    @ResponseBody
    public AjaxJson storeAfterChangeDataJSON(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String afterChangeDataJSON = request.getParameter("afterChangeDataJSON");
        AppUtil.getSession().setAttribute("afterChangeDataJSON", afterChangeDataJSON);
        return j;
    }
    /**
     * 
     * 描述 对比数据
     * @author Faker Li
     * @created 2016年6月13日 下午7:24:02
     * @param request
     * @return
     */
    @RequestMapping(params = "comparisonData")
    public ModelAndView comparisonData(HttpServletRequest request) {
        String afterChangeDataJSON = (String) AppUtil.getSession().getAttribute("afterChangeDataJSON");
        Map<String,Object> afterChangeData = JSON.parseObject(afterChangeDataJSON,Map.class);
        String SOURCE_JBXX_ID =(String)afterChangeData.get("SOURCE_JBXX_ID");
        Map<String,Object> baseinfo = (Map)afterChangeData.get("BASEINFO");
        Map<String,Object> personnel = (Map)afterChangeData.get("PERSONNEL");
        Map<String,Object> sourcebaseinfo =  foodManagementService.getByJdbc("T_FJFDA_SPJYXK_JBXX",
                new String[]{"JBXX_ID"}, new Object[]{SOURCE_JBXX_ID});
        Map<String,Object> ybzspxs = foodManagementService.getYxm(SOURCE_JBXX_ID,"YBZSPXS");
        Map<String,Object> szspxs = foodManagementService.getYxm(SOURCE_JBXX_ID,"SZSPXS");
        Map<String,Object> tsspxs = foodManagementService.getYxm(SOURCE_JBXX_ID,"TSSPXS");
        Map<String,Object> qtlspxslb = foodManagementService.getYxm(SOURCE_JBXX_ID,"QTLSPXSLB");
        Map<String,Object> dllspzs = foodManagementService.getYxm(SOURCE_JBXX_ID,"DLLSPZS");
        Map<String,Object> qtlspzslb = foodManagementService.getYxm(SOURCE_JBXX_ID,"QTLSPZSLB");
        if(ybzspxs!=null)sourcebaseinfo.put("YBZSPXS",ybzspxs.get("JYXM_VALUE"));
        if(szspxs!=null)sourcebaseinfo.put("SZSPXS",szspxs.get("JYXM_VALUE"));        
        if(tsspxs!=null)sourcebaseinfo.put("TSSPXS",tsspxs.get("JYXM_VALUE"));
        if(qtlspxslb!=null)sourcebaseinfo.put("QTLSPXSLB",qtlspxslb.get("JYXM_VALUE"));
        if(dllspzs!=null)sourcebaseinfo.put("DLLSPZS",dllspzs.get("JYXM_VALUE"));
        if(qtlspzslb!=null) sourcebaseinfo.put("QTLSPZSLB",qtlspzslb.get("JYXM_VALUE"));
        Map<String,Object> residenceInfo = foodManagementService.getAddressMap(SOURCE_JBXX_ID,"1");
        Map<String,Object> placeOfBusiness = foodManagementService.getAddressMap(SOURCE_JBXX_ID,"2");
        Map<String,Object> storagePlace = foodManagementService.getAddressMap(SOURCE_JBXX_ID,"3");
        if(residenceInfo!=null)sourcebaseinfo.putAll(residenceInfo);
        if(placeOfBusiness!=null)sourcebaseinfo.putAll(placeOfBusiness);
        if(storagePlace!=null)sourcebaseinfo.putAll(storagePlace);
        Map<String,Object> sourcepersonnel=  foodManagementService.getByJdbc("T_FJFDA_SPJYXK_FRDB",
                new String[]{"JBXX_ID"}, new Object[]{SOURCE_JBXX_ID});
        request.setAttribute("baseinfo", baseinfo);
        request.setAttribute("personnel", personnel);
        request.setAttribute("sourcebaseinfo", sourcebaseinfo);
        request.setAttribute("sourcepersonnel", sourcepersonnel);
        //对比移除数据
        //contrastRemovalData(sourcebaseinfo,baseinfo);
        return new ModelAndView("business/foodManagementChange/comparisonData");
    }
    
    /**
     * 描述 申请人主动退回业务
     * @author Lina Lin
     * @created 2016年6月20日 下午3:08:03
     * @param request
     * @return
     */
    @RequestMapping("/turnBack")
    public ModelAndView turnBack(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "business/foodManagementTurnBack/turnBackInfoForm");
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String exeId = request.getParameter("exeId");
        String ITEM_CODE = request.getParameter("ITEM_CODE");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("undefined")) {
            request.setAttribute("exeId", exeId);
        }else{
            request.setAttribute("exeId", "null");
        }
        request.setAttribute("ITEM_CODE", ITEM_CODE);
        return new ModelAndView("business/foodManagementChange/flowDataSelector");
    }
    
    
    /**
     * 描述  食品经营许可证注销 页面跳转
     * @author Lina Lin
     * @created 2016年6月22日 下午5:47:10
     * @param request
     * @return
     */
    @RequestMapping("/withdraw")
    public ModelAndView withdraw(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "business/foodManagementWithdraw/withDrawInfoForm");
    }
    
    
    /**
     * 描述  食品经营许可证撤销 页面跳转
     * @author Lina Lin
     * @created 2016年6月22日 下午5:47:10
     * @param request
     * @return
     */
    @RequestMapping("/revoke")
    public ModelAndView revoke(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "business/foodManagementRevoke/revokeInfoForm");
    }
    
   /**
    * 描述  食品经营许可证补证 页面跳转
    * @author Lina Lin
    * @created 2016年6月23日 上午11:10:12
    * @param request
    * @return
    */
    @RequestMapping("/mend")
    public ModelAndView mend(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "business/foodManagementMend/mendInfoForm");
    }
    
    /**
     * 
     * 描述 跳转到换证许可界面
     * @created 2016年6月21日 上午9:02:00
     * @param request
     * @return
     */
    @RequestMapping("/replaceInfo")
    public ModelAndView replaceInfo(HttpServletRequest request) {
        return this.toFoodBusinessForm(request, "business/foodManagerReplace/foodInfoForm");
    }
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping("/isExistJYCS")
    @ResponseBody
    public AjaxJson isExistJYCS(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ITEM_CODE = request.getParameter("ITEM_CODE");
        String JYCSQS = request.getParameter("JYCSQS");
        String JYCSXS = request.getParameter("JYCSXS");
        String JYCSXZ = request.getParameter("JYCSXZ");
        String JYXXDZ = request.getParameter("JYXXDZ");
        String JYZMC = request.getParameter("JYZMC");
        String SHXYDMSFZHM = request.getParameter("SHXYDMSFZHM");
        String XKZBH_OLD = request.getParameter("XKZBH_OLD");
        String EXE_ID = request.getParameter("EXE_ID");
        if(StringUtils.isNotEmpty(ITEM_CODE)){
            int countNum = 0;
            Map<String,Object>  resultMapList = foodManagementService.
                    getCountNumByJycs(ITEM_CODE,JYCSQS,JYCSXS,JYCSXZ,JYXXDZ,EXE_ID);
            if(resultMapList==null){
                if(ITEM_CODE.equals("001XK00102")||ITEM_CODE.equals("001XK00103")){
                    Map<String, Object> xpjyxkxxMap =foodManagementService.getSpjyxkxxMap(XKZBH_OLD,"10,80");
                    if(null!=xpjyxkxxMap){
                        j.setSuccess(true);
                    }else{
                        j.setSuccess(false);
                        j.setMsg("许可证编号不存在！"); 
                    }
                }else{
                    j.setSuccess(true); 
                }
                
            }else{
                String msg = "该地址不可用：已存在其它办件中/已存在证照库中";
                boolean gcb = false;
                boolean jgb = false;
                if(resultMapList.get("jycsList")!=null){
                    List<Map<String,Object>> jycsList = (List)resultMapList.get("jycsList");
                    if(jycsList.size()>0){
                        gcb = true;
                        msg +=",申报号为:";
                    }
                    StringBuffer sb = new StringBuffer("");
                    for (int i = 0; i < jycsList.size(); i++) {
                         sb.append((String)jycsList.get(i).get("EXE_ID")).append(",");
                    }
                    msg += sb.toString();
                }
                if(resultMapList.get("spjyxkxxMap")!=null){
                    Map<String,Object> spjyxkxxMap = (Map)resultMapList.get("spjyxkxxMap");
                    if(ITEM_CODE.equals("001XK00102")||ITEM_CODE.equals("001XK00103")){
                        String jgjymc = (String)spjyxkxxMap.get("JYZMC");
                        String jgshxydmsfzhm =(String) spjyxkxxMap.get("SHXYDMSFZHM");
                        String XKZBH =(String) spjyxkxxMap.get("XKZBH");
                        if(JYZMC.equals(jgjymc)||SHXYDMSFZHM.equals(jgshxydmsfzhm)||XKZBH_OLD.equals(XKZBH)){
                            jgb = false;
                        }else{
                            msg +=",许可证编号为:";
                            jgb = true;
                            msg += (String)spjyxkxxMap.get("XKZBH")+",";
                        }
                    }else{
                        msg +=",许可证编号为:";
                        jgb = true;
                        msg += (String)spjyxkxxMap.get("XKZBH")+",";
                    }
                }
                if(gcb||jgb){
                    j.setSuccess(false);
                    j.setMsg(msg); 
                }else{
                    j.setSuccess(true);
                }
                
            }
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping("/validateJYCS")
    @ResponseBody
    public AjaxJson validateJYCS(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String xkzbh = request.getParameter("XKZBH");
        String jycs = request.getParameter("JYCS");
        String jyzmc = request.getParameter("JYZMC");
        String msg = "该经营场所不可用：已存在证照库中,请通过办件编辑修改经营场所地址";
        List<Map<String,Object>> licenseList = foodManagementService.getLicenseByJycsAndXkzbh(jycs,xkzbh,jyzmc);
        if(licenseList!=null&&licenseList.size()>0){
            Map<String,Object> licenseInfo = licenseList.get(0);
            String otherJyzmc = (String) licenseInfo.get("JYZMC");
            msg = "该经营场所已被【"+otherJyzmc+"】使用,<br/>请进入【许可审批】->【已办审批事项】菜单通过【申请编辑办件】按钮修改经营场所地址";
            j.setSuccess(false);
            j.setMsg(msg); 
        }else{
            j.setSuccess(true);
        }
        return j;
    }
    
    
    /**
     * 跳转到技术人员列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshTechnicalPersonnelDivByList")
    public ModelAndView refreshTechnicalPersonnelDivByList(HttpServletRequest request) {
        String technicalPersonnelListJson =  request.getParameter("technicalPersonnelListJson");
        List technicalPersonnelList = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(technicalPersonnelListJson)){
            technicalPersonnelList = JSON.parseArray(technicalPersonnelListJson, Map.class);
        }
        request.setAttribute("technicalPersonnelList", technicalPersonnelList);
        return new ModelAndView("business/foodManagement/refreshTechnicalPersonnelDivByList");
    }
    /**
     * 
     * 描述 获取食品经营数据
     * @author Faker Li
     * @created 2016年7月16日 上午9:48:59
     * @param request
     * @param response
     */
    @RequestMapping("/getSpjyxx")
    public void getSpjyxx(HttpServletRequest request, HttpServletResponse response) {
        String XKZBH_OLD = request.getParameter("XKZBH_OLD");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> xpjyxkxxMap =foodManagementService.getSpjyxkxxMap(XKZBH_OLD,"10,80");
        if(null!=xpjyxkxxMap){
            String jbxxId = xpjyxkxxMap.get("JBXX_ID")==null?"":xpjyxkxxMap.get("JBXX_ID").toString();
            if(StringUtils.isNotEmpty(jbxxId)){
                
                Map<String, Object> jbxxMap = foodManagementService.getByJdbc("T_FJFDA_SPJYXK_JBXX",  new String[] {
                "JBXX_ID" }, new Object[] {jbxxId });
                if(null!=jbxxMap){
                    getCommonBackFilllDate(xpjyxkxxMap, result, jbxxMap);
                }
            }else{
                Map<String,Object> jbxxMap = getNewMapByJyMap(xpjyxkxxMap);
                result.put("jbxxMap", jbxxMap);
            }
            result.put("msg", "验证成功");
            result.put("success", true);
        } else {
            result.put("msg", "系统无此证照数据，请手动输入！");
            result.put("success", false);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 描述 根据许可编号获取新的map
     * @author Faker Li
     * @created 2016年7月16日 上午10:13:04
     * @param xpjyxkxxMap
     * @return
     */
    private Map<String, Object> getNewMapByJyMap(Map<String, Object> xpjyxkxxMap) {
        Map<String,Object> m = new HashMap<String,Object>();
        m.putAll(xpjyxkxxMap);
        m.put("ZSXXDZ", xpjyxkxxMap.get("ZS"));
        m.put("JYXXDZ", xpjyxkxxMap.get("JYCS"));
        m.put("CCXXDZ", xpjyxkxxMap.get("CKDZ"));
        return m;
    }
    /**
     * 
     * 描述 验证许可证编号食品经营数据
     * @author Faker Li
     * @created 2016年7月16日 上午9:48:59
     * @param request
     * @param response
     */
    @RequestMapping("/validateXkzbh")
    public void validateXkzbh(HttpServletRequest request, HttpServletResponse response) {
        String XKZBH_OLD = request.getParameter("XKZBH_OLD");
        String IS_ONLY = request.getParameter("IS_ONLY");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> xpjyxkxxMap = null;
        if(StringUtils.isNotEmpty(IS_ONLY)&&IS_ONLY.equals("true")){
            xpjyxkxxMap = foodManagementService.getSpjyxkxxMap(XKZBH_OLD,"10,20,30,80");
        }else{
            xpjyxkxxMap = foodManagementService.getSpjyxkxxMapForValidateXkzbh(XKZBH_OLD,"10,20,30,80");
        }
                
        
        if(null!=xpjyxkxxMap){
            String jbxxId = xpjyxkxxMap.get("JBXX_ID")==null?"":xpjyxkxxMap.get("JBXX_ID").toString();
            if(StringUtils.isNotEmpty(jbxxId)){
                Map<String, Object> jbxxMap = foodManagementService.getByJdbc("T_FJFDA_SPJYXK_JBXX",  new String[] {
                "JBXX_ID" }, new Object[] {jbxxId });
                if(null!=jbxxMap){
                    Map<String,Object> placeOfBusiness = foodManagementService.getAddressMap(jbxxId,"2");
                    jbxxMap.putAll(placeOfBusiness);
                    result.put("jbxxMap", jbxxMap);
                    //法人信息
                    Map<String,Object> legalRepresentative = foodManagementService.getLegalRepresentative(jbxxId);
                    if(legalRepresentative!=null){
                        result.put("legalRepresentative", legalRepresentative);
                    }
                }
            }else{
                Map<String,Object> jbxxMap = getNewMapByJyMap(xpjyxkxxMap);
                result.put("jbxxMap", jbxxMap);
            }
            result.put("JYZMC", xpjyxkxxMap.get("JYZMC"));
            result.put("FDDBRFZR", xpjyxkxxMap.get("FDDBRFZR"));
            result.put("msg", "验证成功");
            result.put("success", true);
        } else {
            result.put("msg", "许可证编号不存在，请先手动导入该旧证信息（餐饮服务许可证或食品流通许可证），导入成功后再执行注销（或换证）操作！");
            result.put("success", false);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    
    /** 
     * 描述 验证退回业务受理号
     * @author Lina Lin
     * @created 2016年7月23日 上午10:43:50
     * @param request
     * @param response
     */
    @RequestMapping("/validateExeid")
    public void validateExeid(HttpServletRequest request, HttpServletResponse response) {
        String OLDEXE_ID = request.getParameter("OLDEXE_ID");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> executionMap =foodManagementService.getExecutionMap(OLDEXE_ID);
        if(null!=executionMap){
            result.put("FDDBRFZR", executionMap.get("EFLOW_MSGRECEIVER"));
            result.put("msg", "验证成功");
            result.put("success", true);
        } else {
            result.put("msg", "业务受理号不存在,请修改！");
            result.put("success", false);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 获取食品经营数据
     * @author Faker Li
     * @created 2016年7月16日 上午9:48:59
     * @param request
     * @param response
     */
    @RequestMapping("/getPatchSpjyxx")
    public void getPatchSpjyxx(HttpServletRequest request, HttpServletResponse response) {
        String XKZBH_OLD = request.getParameter("XKZBH_OLD").trim();
        XKZBH_OLD = XKZBH_OLD.replaceAll("，",",");
        String[] xkzbhArray = XKZBH_OLD.split(",");
        StringBuffer suString = new StringBuffer("");
        StringBuffer faString = new StringBuffer("");
        Map<String, Object> xpjyxkxxMap = null;
        Map<String, Object> result = new HashMap<String, Object>();
        for (int i = 0; i < xkzbhArray.length; i++) {
            Map<String, Object> xpjyxkxx = foodManagementService.getPatchSpjyxx(xkzbhArray[i].trim());
            if(xpjyxkxx!=null){
                xpjyxkxxMap = xpjyxkxx;
                if(suString.length()>0){
                    suString.append(",");
                }
                suString.append(xkzbhArray[i]);
            }else{
                if(faString.length()>0){
                    faString.append(",");
                }
                faString.append(xkzbhArray[i]);
            }
        }
        if(null!=xpjyxkxxMap){
            /*Map<String,Object> jbxxMap = getNewMapByJyMap(xpjyxkxxMap);*/
            result.put("jbxxMap", xpjyxkxxMap);
           /* String jbxxId = xpjyxkxxMap.get("JBXX_ID")==null?"":xpjyxkxxMap.get("JBXX_ID").toString();
            if(StringUtils.isNotEmpty(jbxxId)){
                
                Map<String, Object> jbxxMap = foodManagementService.getByJdbc("T_FJFDA_SPJYXK_JBXX",  new String[] {
                "JBXX_ID" }, new Object[] {jbxxId });
                if(null!=jbxxMap){
                    getCommonBackFilllDate(xpjyxkxxMap, result, jbxxMap);
                }
            }else{
               
            }*/
            if(StringUtils.isEmpty(faString)){
                result.put("msg", "数据查询成功，本次换证业务完成后系统自动完成旧证注销操作");
                result.put("allStatus", true);
            }else{
                result.put("msg", suString.toString()+"证号数据获取成功，"+faString.toString()+"证号数据获取失败，获取失败的证件在换成完成后无法完成旧证注销");
                result.put("allStatus", false);
            }
            result.put("success", true);
        } else {
            result.put("msg", "未查询到该证数据或者该证已过期，请先手动导入该旧证信息"
                    + "（餐饮服务许可证或食品流通许可证），导入成功后再执行注销（或换证）操作 ！");
            result.put("success", false);
            result.put("allStatus", false);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 描述 获取通用回填信息方法
     * @author Faker Li
     * @created 2016年7月25日 下午7:20:39
     * @param xpjyxkxxMap
     * @param result
     * @param jbxxMap
     */
    public void getCommonBackFilllDate(Map<String, Object> xpjyxkxxMap,
            Map<String, Object> result, Map<String, Object> jbxxMap) {
        String bus_recordid = jbxxMap.get("JBXX_ID").toString();
        //获取上传的网站截图
        String picFileId = (String)jbxxMap.get("WZJTLJ");
        if(StringUtils.isNotEmpty(picFileId)){
           Map<String,Object> picFile = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", 
                    new String[]{"FILE_ID"}, new Object[]{picFileId});
           jbxxMap.put("WZJTLJ_FILE_ID", picFile.get("FILE_ID"));
           jbxxMap.put("WZJTLJ_FILE_NAME", picFile.get("FILE_NAME"));
        }
        Map<String,Object> ybzspxs = foodManagementService.getYxm(bus_recordid,"YBZSPXS");
        Map<String,Object> szspxs = foodManagementService.getYxm(bus_recordid,"SZSPXS");
        Map<String,Object> tsspxs = foodManagementService.getYxm(bus_recordid,"TSSPXS");
        Map<String,Object> qtlspxslb = foodManagementService.getYxm(bus_recordid,"QTLSPXSLB");
        Map<String,Object> dllspzs = foodManagementService.getYxm(bus_recordid,"DLLSPZS");
        Map<String,Object> qtlspzslb = foodManagementService.getYxm(bus_recordid,"QTLSPZSLB");
        if(ybzspxs!=null){
            jbxxMap.put("YBZSPXS",ybzspxs.get("JYXM_VALUE"));
        }
        if(szspxs!=null){
            jbxxMap.put("SZSPXS",szspxs.get("JYXM_VALUE"));        
        }
        if(tsspxs!=null){
            jbxxMap.put("TSSPXS",tsspxs.get("JYXM_VALUE"));
        }
        if(qtlspxslb!=null){
            jbxxMap.put("QTLSPXSLB",qtlspxslb.get("JYXM_VALUE"));
        }
        if(dllspzs!=null){
            jbxxMap.put("DLLSPZS",dllspzs.get("JYXM_VALUE"));
        }
        if(qtlspzslb!=null){
            jbxxMap.put("QTLSPZSLB",qtlspzslb.get("JYXM_VALUE"));
        }
        Map<String,Object> residenceInfo = foodManagementService.getAddressMap(bus_recordid,"1");
        Map<String,Object> placeOfBusiness = foodManagementService.getAddressMap(bus_recordid,"2");
        Map<String,Object> storagePlace = foodManagementService.getAddressMap(bus_recordid,"3");
        if(residenceInfo!=null){
            jbxxMap.putAll(residenceInfo);
        }
        if(placeOfBusiness!=null){
            jbxxMap.putAll(placeOfBusiness);
        }
        if(storagePlace!=null){
            jbxxMap.putAll(storagePlace);
        }
        String YXQZ = (String)xpjyxkxxMap.get("YXQZ");
        jbxxMap.put("YXQZ", YXQZ);
        result.put("jbxxMap", jbxxMap);
        //法人信息
        Map<String,Object> legalRepresentative = foodManagementService.getLegalRepresentative(bus_recordid);
        if(legalRepresentative!=null){
            result.put("legalRepresentative", legalRepresentative);
        }
        //获取代理人信息
        Map<String,Object> clinetMap = foodManagementService.getClinetMap(bus_recordid);
        if(clinetMap!=null){
            result.put("clinetMap", clinetMap);
        }
        //获取设备信息
        List<Map<String,Object>> facilityEquipmentList = 
                foodManagementService.findFacilityEquipment(bus_recordid);
        if(facilityEquipmentList!=null){
            result.put("facilityEquipmentList", facilityEquipmentList);
        }
        //获取技术人员列表
        List<Map<String,Object>> technicalPersonnelList =
                foodManagementService.findPersonel(bus_recordid,"1");                  
        if(technicalPersonnelList!=null){
            result.put("technicalPersonnelList", technicalPersonnelList);
        }
      //获取从业人员列表
        List<Map<String,Object>> practitionersList = foodManagementService.findPersonel(bus_recordid,"2");
        if(practitionersList!=null){
            result.put("practitionersList", practitionersList);
        }
    }
    /**
     * 
     * 描述 获取统一信用代码
     * @author Faker Li
     * @created 2016年7月28日 上午10:06:43
     * @param request
     * @param response
     */
    @RequestMapping("/getCreditCodeData")
    public void getCreditCodeData(HttpServletRequest request, HttpServletResponse response) {
        String SHXYDMSFZHM = request.getParameter("SHXYDMSFZHM");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> shxydmMap =foodManagementService.getCreditCodeData(SHXYDMSFZHM);
        if(null!=shxydmMap){
            Map<String,Object> jbxxMap = new HashMap<String,Object>();
            Map<String,Object> legalRepresentative = new HashMap<String,Object>();
            jbxxMap.put("JYZMC", shxydmMap.get("ENT_NAME"));
            jbxxMap.put("ZGRS", shxydmMap.get("STAFF_NUM"));
            jbxxMap.put("JYXXDZ", shxydmMap.get("DOM"));
            jbxxMap.put("ZSXXDZ", shxydmMap.get("DOM"));
            result.put("jbxxMap", jbxxMap);
            legalRepresentative.put("FRXM", shxydmMap.get("LEREP"));
            legalRepresentative.put("FRGDDH", shxydmMap.get("TEL"));
            result.put("legalRepresentative", legalRepresentative);
            result.put("msg", "验证成功");
            result.put("success", true);
        } else {
            result.put("msg", "系统无此社会代码数据，请手动输入！");
            result.put("success", false);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 验证社会代码是否已经申报过
     * @author Faker Li
     * @created 2016年7月28日 上午10:16:50
     * @param request
     * @param response
     */
    @RequestMapping("/ajaxShxydmsfzhmExist")
    public void ajaxShxydmsfzhmExist(HttpServletRequest request, HttpServletResponse response) {
        String SHXYDMSFZHM = request.getParameter("SHXYDMSFZHM");
        String itemCode = request.getParameter("itemCode");
        if(itemCode.equals("001XK00101")||itemCode.equals("001XK00108")){
            itemCode = "001XK00101,001XK00108";
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list =foodManagementService.isExistShxydm(SHXYDMSFZHM,itemCode);
        if(list!=null&&list.size()>0){
            StringBuffer msg = new StringBuffer("");
            for (int i = 0; i < list.size(); i++) {
                if(i>0){
                    msg.append(",");
                }
                msg.append(list.get(i).get("EXE_ID"));
            }
            result.put("msg", "该信用代码"+SHXYDMSFZHM+"存在类似办件，<br/>办件编号(流水号)为"+msg.toString()
                    +"，<br/>请确认是否重复办理，若非重复办理可忽略本提示 ");
            result.put("success", true);
        }else{
            result.put("msg", "不存在历史申报记录！");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 跳转到从业人员列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshPractitionersDivByList")
    public ModelAndView refreshPractitionersDivByList(HttpServletRequest request) {
        String practitionersListListJson =  request.getParameter("practitionersListListJson");
        List practitionersList = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(practitionersListListJson)){
            practitionersList = JSON.parseArray(practitionersListListJson, Map.class);
        }
        request.setAttribute("practitionersList", practitionersList);
        return new ModelAndView("business/foodManagement/refreshPractitionersDivByList");
    }
}

