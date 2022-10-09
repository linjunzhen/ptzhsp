/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.*;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.CyjbService;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.website.service.XFDesignService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月22日 下午3:37:27
 */
@Controller
@RequestMapping("/busApplyController")
public class BusApplyController extends BaseController {
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * callService
     */
    @Resource
    private CallService callService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * 引入Service
     */
    @Resource
    private CyjbService cyjbService;
    /**
     * 引入projectWebsiteApplyService
     */
    @Resource
    private ProjectWebsiteApplyService projectWebsiteApplyService;
    
    /**
     * 引入Service
     */
    @Resource
    private XFDesignService xfDesignService;
    
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/4/27 16:26:00
     * @param
     * @return
     */
    @Resource
    private BdcGyjsjfwzydjService gyjsjfwzydjService;

    /**
     * 跳转申请流程信息界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/applyPage")
    public ModelAndView applyPage(HttpServletRequest request) {
        //把文件服务器的地址放入到session中
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();   
        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        session.setAttribute("fileServer", fileServer);
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申请方式
        String applyType = request.getParameter("applyType");
        // 获取是否平潭网格政务门户申请
        String ptwgType = request.getParameter("ptwgType");
        // 获取流程申报号
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        String categoryId = request.getParameter("categoryId");
        String EFLOW_ISQUERYDETAIL = request.getParameter("EFLOW_ISQUERYDETAIL");
        String acceptway = request.getParameter("acceptway");
        String lineId = request.getParameter("lineId");
        String zjlx = request.getParameter("zjlx");
        String PROJECT_CODE = request.getParameter("PROJECT_CODE");//投资项目编号（工程建设项目申报）
        request.setAttribute("PROJECT_CODE", PROJECT_CODE);
        String STAGE_ID = request.getParameter("STAGE_ID");//所属阶段ID（工程建设项目申报）
        request.setAttribute("STAGE_ID", STAGE_ID);
        if(PROJECT_CODE!=null && !"".equals(PROJECT_CODE)) {
            String prj_num = xfDesignService.getPrjNum(PROJECT_CODE);
            request.setAttribute("PRJ_NUM", prj_num);
        }
        // 定义是否是开始节点
        String EFLOW_IS_START_NODE = "";
        if (StringUtils.isNotEmpty(EFLOW_ISQUERYDETAIL) && EFLOW_ISQUERYDETAIL.equals("true")) {
            EFLOW_IS_START_NODE = "false";
        }
        Map<String, Object> execution = null;
        int flowVersion = 0;
        String currentNodeName = null, startNodeName = null, defId = null;
        // 是否归档流程查看
        String isFiled = request.getParameter("isFiled");
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("null")) {
            String tableName = "JBPM6_EXECUTION";
            if("1".equals(isFiled)) {
                tableName = "JBPM6_EXECUTION_EVEHIS";
            }
            execution = executionService.getByJdbc(tableName, new String[] { "EXE_ID" }, new Object[] { exeId });
            if(execution.get("PROJECT_CODE")!=null) {
                /*工程建设项目查看是否有国标行业与产业指导目录字段*/
//                execution = projectWebsiteApplyService.checkProjectIndustry(exeId, execution);
                PROJECT_CODE = execution.get("PROJECT_CODE").toString();
                if(categoryId==null || "".equals(categoryId)) {
                    Map<String,Object> stageInfo = projectWebsiteApplyService.findStageInfo(exeId, PROJECT_CODE);
                    if(stageInfo!=null && stageInfo.get("STAGE_ID")!=null) {
                        categoryId = stageInfo.get("STAGE_ID").toString();
                    }
                }
                List<Map<String,Object>> materList = projectWebsiteApplyService.findMaterList(categoryId, PROJECT_CODE);
                if(materList!=null && materList.size()>0) {
                    request.setAttribute("materListValue", true);
                    request.setAttribute("materList", materList);
                }
            }
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
            return ifNotNull(request, itemCode, applyType, ptwgType, exeId, acceptway, lineId, zjlx,
                    EFLOW_IS_START_NODE, execution, flowVersion, currentNodeName, defId, serviceItem);
        } else {
            return null;
        }
    }

    private ModelAndView ifNotNull(HttpServletRequest request, String itemCode, String applyType, String ptwgType,
            String exeId,
            String acceptway, String lineId, String zjlx, String EFLOW_IS_START_NODE,
            Map<String, Object> execution, int flowVersion, String currentNodeName, String defId,
            Map<String, Object> serviceItem) {
        if (flowVersion == 0) {
            String defKey = (String) serviceItem.get("DEF_KEY");// 获取流程定义KEY
            Map<String, Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_KEY" },
                    new Object[] { defKey });
            flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            defId = (String) flowDef.get("DEF_ID");
            currentNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
        }
        String formKey = (String) serviceItem.get("FORM_KEY");
        if ("T_BSFW_PTJINFO".equals(formKey) || "T_BSFW_JBJINFO".equals(formKey)) {
            setItemName(serviceItem);
        }
        String itemId = (String) serviceItem.get("ITEM_ID");// 获取项目ID
        Map<String, Object> nodeConfig = nodeConfigService.getByJdbc("JBPM6_NODECONFIG", new String[] { "NODE_NAME",
                "DEF_ID", "DEF_VERSION" }, new Object[] { currentNodeName, defId, flowVersion });
        if (nodeConfig != null) {
            request.setAttribute("nodeConfig", nodeConfig);
        }
        // 获取材料信息列表 仅获取事项材料列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        // 获取材料业务办理子项分类列表
        List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeList(itemId);
//        String busRecordId = "";
//        String busTableName = "";
//        if (execution != null) {
//            busRecordId = execution.get("BUS_RECORDID") == null ?
//                    "" : execution.get("BUS_RECORDID").toString();// 获取业务ID
//            busTableName = execution.get("BUS_TABLENAME") == null ?
//                    "" : execution.get("BUS_TABLENAME").toString();// 获取业务表名称
//        }
//        List<Map<String, Object>> ysqMaters = null;
//        if (StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(busTableName)) {
//            //根据业务表名和id获取该办件已收取的电子档材料附件
//            ysqMaters = fileAttachService.findYsqByList(busTableName, busRecordId);
//        }
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
//            ysqMaters = getFlowCaseInfo(execution, request, ysqMaters);
            applyType = (String) execution.get("SQFS");
        }
        //开始设定工程建设项目相同附件描述智能回填
        String projectCode = request.getParameter("PROJECT_CODE");//投资项目编号（工程建设项目申报）        
        if(null == execution && StringUtils.isNotEmpty(projectCode)){
            applyMaterService.setSameUploadFiles(applyMaters, formKey, projectCode);
            applyMaterService.setSameKeyUploadFiles(applyMaters, formKey, projectCode);
            
        }
        
        serviceItem.put("APPLY_TYPE", applyType);
        /*生成一个用于识别电子证照反馈信息的UUID*/
        serviceItem.put("CREDIT_FEEDBACK_MARK", UUIDGenerator.getUUID());
        // 查找指定的事项需要特殊处理的环节
        Map<String, Object> dealItem = serviceItemService.getParticularDealNode(itemCode);
        // 定义材料列表JSON
        String ysqMatersJson = "";
//        if (ysqMaters != null && ysqMaters.size() > 0) {
//            ysqMatersJson = JsonUtil.jsonStringFilter(ysqMaters, new String[] { "uploadFiles" }, true);
//        }
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("handleTypes", handleTypes);
//        if(ysqMaters!=null && ysqMaters.size()>0) {
//            request.setAttribute("ysqMatersValue", true);
//        }
//        request.setAttribute("ysqMaters", ysqMaters);
        request.setAttribute("ysqMatersJson", StringEscapeUtils.escapeHtml3(ysqMatersJson));
        request.setAttribute("EFLOW_IS_START_NODE", EFLOW_IS_START_NODE);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        request.setAttribute("dealItem", dealItem);
        /*获取取号信息*/
        getRecordInfo(request, lineId, formKey);
        setRequestVal( acceptway, lineId, zjlx, request);
        String itemdetail_id = request.getParameter("itemdetail_id");
        if(StringUtils.isNotEmpty(itemdetail_id)){
            request.setAttribute("itemdetail_id", itemdetail_id);
        }
        String subBusClass = request.getParameter("subBusClass");        
        if(StringUtils.isNotEmpty(subBusClass)&&!subBusClass.equals("undefined")){
            subBusClass = applyMaterService.getByJdbc("T_WSBS_SERVICEITEM_BUSCLASS", new String[] { "RECORD_ID" },
                    new Object[] { subBusClass }).get("BUSCLASS_NAME").toString();
            request.setAttribute("subBusClass", subBusClass);
        }
        if (StringUtils.isNotEmpty(ptwgType)) {
            return new ModelAndView("bsdt/ptwgform/" + formKey);
        } else {
            return new ModelAndView("bsdt/applyform/" + formKey);
        }
    }

    /**
     * 描述:获取排队信息
     *
     * @author Madison You
     * @created 2020/12/24 15:47:00
     * @param
     * @return
     */
    private void getRecordInfo(HttpServletRequest request, String lineId , String formKey) {
        if (!formKey.startsWith("T_COMMERCIAL_")) {
            Map<String, Object> recordMap = serviceItemService.getByJdbc("T_CKBS_QUEUERECORD",
                    new String[]{"RECORD_ID"}, new Object[]{lineId});
            request.setAttribute("lineName", StringUtil.getValue(recordMap, "LINE_NAME"));
            request.setAttribute("lineCard", StringUtil.getValue(recordMap, "LINE_CARDNO"));
            request.setAttribute("lineAddress", StringUtil.getValue(recordMap, "LINE_ADDRESS"));
            request.setAttribute("zjlx", StringUtil.getValue(recordMap, "LINE_ZJLX"));
        }
    }

    /**
     * 
     * 描述：set
     * 
     * @author Water Guo
     * @created 2017-4-6 上午9:34:50
     */
    public void setItemName(Map<String, Object> serviceItem) {
        String itemName = serviceItem.get("ITEM_NAME").toString();
        String catalogCode = (String) serviceItem.get("CATALOG_CODE");
        String stdCatalogName = StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME");
        String catalogName = "";
        if (StringUtils.isNotEmpty(stdCatalogName)) {
            catalogName = stdCatalogName;
        } else {
            if (catalogCode != null) {
                catalogName = serviceItemService
                        .getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_CODE" },
                                new Object[] { catalogCode }).get("CATALOG_NAME").toString();
            }
        }
        itemName = catalogName + "【" + itemName + "】";
        serviceItem.put("ITEM_NAME", itemName);
    }

    /**
     * set
     *
     * @param lineId
     * @param zjlx
     * @param request
     */
    public void setRequestVal( String acceptway, String lineId, String zjlx,
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
                    if (StringUtils.isNotEmpty(acceptway)) {
                        request.setAttribute("acceptway", acceptway);
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
                    if (StringUtils.isNotEmpty(acceptway)) {
                        request.setAttribute("acceptway", acceptway);
                    }
                }
            }
        } else {
            if (StringUtils.isNotEmpty(lineId)) {
                request.setAttribute("lineId", lineId);
            }
            if (StringUtils.isNotEmpty(zjlx)) {
                request.setAttribute("zjlx", zjlx);
            }
            if (StringUtils.isNotEmpty(acceptway)) {
                request.setAttribute("acceptway", acceptway);
            }
        }

        request.setAttribute("flowstage", request.getParameter("flowstage"));
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
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        // 内资表单，将虚拟主表名替换真实主表名称
        if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //国有转移6个事项虚拟主表替换真实表名称
        if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String busPkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);// 获取业务表主键名称
        // 获取业务记录
        Map<String, Object> busRecord = executionService.getByJdbc(busTableName, new String[] { busPkName },
                new Object[] { busRecordId });
        //国有转移事项特殊处理
        if("T_BDCQLC_GYJSJFWZYDJ".equals(busTableName)){
            busTableName = (String) execution.get("BUS_TABLENAME");// 获取业务表名称
        }
        applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        // 绑定产业奖补信息
        if (StringUtils.isNotEmpty(busRecordId)) {
            List<Map<String, Object>> cyjbList = cyjbService.findCyjbList(busRecordId);
            if (cyjbList != null && cyjbList.size() > 0) {
                request.setAttribute("cyjbList", cyjbList);
            }
        }
        request.setAttribute("execution", execution);
        // 获取多证合一数据
        Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                new String[] { "COMPANY_ID" }, new Object[] { busRecordId });
        if (null != multiple) {
            busRecord.putAll(multiple);
        }
        request.setAttribute("busRecord", busRecord);
        // 设置商事登记事项参数
        setCompany(request, busRecord);
        //商事变更事项公司参数
        setCommercialChange(request,busRecord);
        // 设置工程建设施工许可事项参数
        setProjectSgxk(request, busRecord);
        return applyMaters;
    }

    /**
     * 
     * 商事登记变更参数    
     * @author Danto Huang
     * @created 2021年4月28日 上午10:00:16
     * @param busRecord
     */
    private void setCommercialChange(HttpServletRequest request, Map<String, Object> busRecord){
        
        String COMPANY_TYPE = busRecord.get("COMPANY_TYPE") == null ? "" : busRecord.get("COMPANY_TYPE").toString();
        String COMPANY_TYPE_CHANGE = busRecord.get("COMPANY_TYPE_CHANGE") == null ? "" : busRecord.get("COMPANY_TYPE_CHANGE").toString();
        //变更公司类型
        if(StringUtils.isNotEmpty(COMPANY_TYPE)){
            Map<String,Object> type = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[] { "TYPE_CODE" }, new Object[] { COMPANY_TYPE });
            if(type!=null){
                busRecord.put("COMPANY_TYPE_NAME", type.get("TYPE_NAME"));
            }
        }
        if(StringUtils.isNotEmpty(COMPANY_TYPE_CHANGE)){
            Map<String,Object> typeChange = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[] { "TYPE_CODE" }, new Object[] { COMPANY_TYPE_CHANGE });
            if(typeChange!=null){
                busRecord.put("COMPANY_TYPE_NAME_CHANGE", typeChange.get("TYPE_NAME"));
            }
        }
        //涉及变更时，最终股东列表
        List<Map<String,Object>> newHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> yHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> xHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> sameHolderList = new ArrayList<Map<String,Object>>();
        boolean isStockTransfer = false;
        // 股东信息
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            request.setAttribute("holderList", holderList);
            
            //股权变更旧股东股权判断
            for (Map<String, Object> holder : holderList) {  
                if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))){
                    if(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN"))) > 0){
                        yHolderList.add(holder);
                    }
                    if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS")))){
                        if(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))
                                <(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS"))))){
                            isStockTransfer = true;
                        } 
                    }
                }
            }
        }

        request.setAttribute("isStockTransfer", isStockTransfer);
        // 股东信息(变更)
        String HOLDER_JSON_CHANGE = busRecord.get("HOLDER_JSON_CHANGE") == null ? ""
                : busRecord.get("HOLDER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON_CHANGE)) {
            List<Map<String, Object>> holderChangeList = JSON.parseObject(HOLDER_JSON_CHANGE, List.class);
            for(Map<String,Object> holder : holderChangeList){
                String gqlyJson = holder.get("GQLY_JSON").toString();
                List<Map<String, Object>> gqlyList = JSON.parseObject(gqlyJson, List.class);
                holder.put("gqlyList", gqlyList);
                
                xHolderList.add(holder);
            }
            request.setAttribute("holderChangeList", holderChangeList);
        }
        
        //剔除新旧股东同名情况
        if(yHolderList.size()>0 && xHolderList.size()>0){
            for (Map<String, Object> xHolder : xHolderList) {
                for (Map<String, Object> yHolder : yHolderList) {
                    if(StringUtil.getString(xHolder.get("SHAREHOLDER_NAME"))
                            .equals(StringUtil.getString(yHolder.get("SHAREHOLDER_NAME")))){
                        sameHolderList.add(yHolder);
                    } 
                } 
            }
            if(sameHolderList.size()>0){
                for (Map<String, Object> same : sameHolderList) {
                    yHolderList.remove(same);
                }
            }
            newHolderList.addAll(yHolderList);
            newHolderList.addAll(xHolderList);
        }else if(xHolderList.size()<=0){
            newHolderList.addAll(yHolderList);
        }else if(yHolderList.size()<=0){
            newHolderList.addAll(xHolderList);
        }
        request.setAttribute("newHolderList", newHolderList);
        
        // 董事信息
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            
            //变更职务转码
            boolean havechairman = false;//是否有董事长,有董事长代表设立董事会
            for(Map<String,Object> director : directorList){                
                if(director.get("DIRECTOR_JOB_OLD")!=null){
                    if("01".equals(director.get("DIRECTOR_JOB_OLD"))){
                        havechairman = true;
                    }
                }
                String jobName = dictionaryService.getDicNames("ssdjzw", StringUtil.getString(director.get("DIRECTOR_JOB_OLD")));
                director.put("DIRECTOR_JOB_OLD_NAME", jobName);
            }
            request.setAttribute("havechairman", havechairman);            
            request.setAttribute("directorList", directorList);
        }
        // 董事信息（变更）
        boolean haveNewChairman = false;//是否有董事长,有董事长代表设立董事会
        String DIRECTOR_JSON_CHANGE = busRecord.get("DIRECTOR_JSON_CHANGE") == null ? ""
                : busRecord.get("DIRECTOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON_CHANGE)) {
            List<Map<String, Object>> directorChangeList = JSON.parseObject(DIRECTOR_JSON_CHANGE, List.class);
            
            for(Map<String,Object> directorChange : directorChangeList){ 
                String jobName = dictionaryService.getDicNames("ssdjzw", directorChange.get("DIRECTOR_JOB").toString());
                directorChange.put("DIRECTOR_JOB_NAME", jobName);
                if(directorChange.get("DIRECTOR_JOB")!=null){
                    if("01".equals(directorChange.get("DIRECTOR_JOB"))){
                        haveNewChairman = true;
                    }
                }
            }
            request.setAttribute("directorChangeList", directorChangeList);
        }
        request.setAttribute("haveNewChairman", haveNewChairman);            
        // 监事信息
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            //变更职务转码
            for(Map<String,Object> supervisor : supervisorList){
                if(supervisor.get("SUPERVISOR_JOB_OLD")!=null){
                    String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB_OLD").toString());
                    supervisor.put("SUPERVISOR_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("supervisorList", supervisorList);
        }
        // 监事信息(变更)
        String SUPERVISOR_JSON_CHANGE = busRecord.get("SUPERVISOR_JSON_CHANGE") == null ? ""
                : busRecord.get("SUPERVISOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON_CHANGE)) {
            List<Map<String, Object>> supervisorChangeList = JSON.parseObject(SUPERVISOR_JSON_CHANGE, List.class);
            //变更职务转码
            for(Map<String,Object> supervisor : supervisorChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB").toString());
                supervisor.put("SUPERVISOR_JOB_NAME", jobName);
            }
            request.setAttribute("supervisorChangeList", supervisorChangeList);
        }
        // 经理信息
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            //变更职务转码
            for(Map<String,Object> manager : managerList){
                if(manager.get("MANAGER_JOB_OLD")!=null){
                    String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB_OLD").toString());
                    manager.put("MANAGER_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("managerList", managerList);
        }
        // 经理信息(变更)
        String MANAGER_JSON_CHANGE = busRecord.get("MANAGER_JSON_CHANGE") == null ? ""
                : busRecord.get("MANAGER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON_CHANGE)) {
            List<Map<String, Object>> managerChangeList = JSON.parseObject(MANAGER_JSON_CHANGE, List.class);
          //变更职务转码
            for(Map<String,Object> manager : managerChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB").toString());
                manager.put("MANAGER_JOB_NAME", jobName);
            }
            request.setAttribute("managerChangeList", managerChangeList);
        }
    }
    
    private void setProjectSgxk(HttpServletRequest request, Map<String, Object> busRecord) {
        // 外施工图审查合格证书编号信息
        setRequestList(request, busRecord, "CHARTREVIEWNUM_JSON", "chartreviewnumList");
        // 建设单位信息
        setRequestList(request, busRecord, "JSDW_JSON", "jsdwList");
        // 建设单位信息(变更后)
        setRequestList(request, busRecord, "JSDW_JSON_AFTER", "jsdwAfterList");
        // 代建单位信息
        setRequestList(request, busRecord, "DJDW_JSON", "djdwList");
        // 施工单位信息
        setRequestList(request, busRecord, "SGDW_JSON", "sgdwList" ,"SGRY");
        // 施工单位信息(变更后)
        setRequestList(request, busRecord, "SGDW_JSON_AFTER", "sgdwAfterList" ,"SGRY");
        // 监理单位信息
        setRequestList(request, busRecord, "JLDW_JSON", "jldwList" ,"JLRY");
        // 监理单位信息(变更后)
        setRequestList(request, busRecord, "JLDW_JSON_AFTER", "jldwAfterList" ,"JLRY");
        // 勘察单位信息
        setRequestList(request, busRecord, "KCDW_JSON", "kcdwList");
        // 设计单位信息
        setRequestList(request, busRecord, "SJDW_JSON", "sjdwList");
        // 施工图审查单位信息
        setRequestList(request, busRecord, "SGTSCDW_JSON", "sgtscdwList");
        // 控制价（预算价）计价文件编制单位信息
        setRequestList(request, busRecord, "KZJDW_JSON", "kzjdwList");
        // 招标代理单位信息
        setRequestList(request, busRecord, "ZBDW_JSON", "zbdwList");
        // 检测单位信息
        setRequestList(request, busRecord, "JCDW_JSON", "jcdwList");
        // 单位工程信息
        setRequestList(request, busRecord, "DWGC_JSON", "dwgcList");
        // 桩基工程信息
        setRequestList(request, busRecord, "ZJGC_JSON", "zjgcList");
        // 上部工程信息
        setRequestList(request, busRecord, "SBGC_JSON", "sbgcList");
    }
    @SuppressWarnings("unchecked")
    private void setRequestList(HttpServletRequest request, Map<String, Object> busRecord, String key,
            String listName) {
        String json = busRecord.get(key) == null ? ""
                : busRecord.get(key).toString();
        if (StringUtils.isNotEmpty(json)) {
            List<Map<String, Object>> list = JSON.parseObject(json, List.class);
            for (Map<String, Object> map : list) {
                String mapjson  = JSON.toJSONString(map);
                map.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
            }
            request.setAttribute(listName, list);
        }
    }

    @SuppressWarnings("unchecked")
    private void setRequestList(HttpServletRequest request, Map<String, Object> busRecord, String key,
            String listName,String childName) {
        String json = busRecord.get(key) == null ? ""
                : busRecord.get(key).toString();
        if (StringUtils.isNotEmpty(json)) {
            List<Map<String, Object>> list = JSON.parseObject(json, List.class);
            for (Map<String, Object> map : list) {
                String childJson = map.get(childName)==null?"":map.get(childName).toString();
                if (StringUtils.isNotEmpty(childJson)) {
                    List<Map<String, Object>> childList = JSON.parseObject(childJson, List.class);  
                    for (Map<String, Object> map2 : childList) {
                        String child2Json = map2.get(childName)==null?"":map2.get(childName).toString(); 
                        if (StringUtils.isNotEmpty(childJson)) 
                            map2.put(childName, StringEscapeUtils.escapeHtml3(child2Json));
                    }
                    map.put(childName+"LIST", childList); 
                }
                String mapjson  = JSON.toJSONString(map);
                map.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
            }
            request.setAttribute(listName, list);
        }
    }
    /**
     * 描述 设置商事登记事项参数
     * 
     * @author Rider Chen
     * @created 2017年4月19日 下午2:33:24
     * @param request
     * @param busRecord
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setCompany(HttpServletRequest request, Map<String, Object> busRecord) {
        // 外资投资者信息
        String FOREIGNINVESTOR_JSON = busRecord.get("FOREIGNINVESTOR_JSON") == null ? "" : busRecord.get(
                "FOREIGNINVESTOR_JSON").toString();
        if (StringUtils.isNotEmpty(FOREIGNINVESTOR_JSON)) {
            List<Map<String, Object>> foreigninvestorList = JSON.parseObject(FOREIGNINVESTOR_JSON, List.class);
            for (Map<String, Object> map : foreigninvestorList) {
                String wftzzsjkzrJson = map.get("WFTZZSJKZR_JSON") == null ? "" : map.get("WFTZZSJKZR_JSON").toString();
                if (StringUtils.isNotEmpty(wftzzsjkzrJson)) {
                    List<Map> wftzzsjkzr = JSON.parseArray(wftzzsjkzrJson, Map.class);
                    map.put("wftzzsjkzrList", wftzzsjkzr);
                }
            }
            request.setAttribute("foreigninvestorList", foreigninvestorList);
        }
        // 中方投资者信息
        String DOMESTICINVESTOR_JSON = busRecord.get("DOMESTICINVESTOR_JSON") == null ? "" : busRecord.get(
                "DOMESTICINVESTOR_JSON").toString();

        if (StringUtils.isNotEmpty(DOMESTICINVESTOR_JSON)) {
            List<Map<String, Object>> domesticinvestorList = JSON.parseObject(DOMESTICINVESTOR_JSON, List.class);
            for (Map<String, Object> map : domesticinvestorList) {
                String zftzzsjkzrJson = map.get("ZFTZZSJKZR_JSON") == null ? "" : map.get("ZFTZZSJKZR_JSON").toString();
                if (StringUtils.isNotEmpty(zftzzsjkzrJson)) {
                    List<Map> zftzzsjkzr = JSON.parseArray(zftzzsjkzrJson, Map.class);
                    map.put("zftzzsjkzrList", zftzzsjkzr);
                }
            }
            request.setAttribute("domesticinvestorList", domesticinvestorList);
        }
        // 股东信息
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            request.setAttribute("holderList", holderList);
        }
        // 董事信息
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            request.setAttribute("directorList", directorList);
        }
        // 原董事信息
        String OLD_DIRECTOR_JSON = busRecord.get("OLD_DIRECTOR_JSON") == null ? ""
                : busRecord.get("OLD_DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            List<Map<String, Object>> oldDirectorList = JSON.parseObject(OLD_DIRECTOR_JSON, List.class);
            request.setAttribute("oldDirectorList", oldDirectorList);
        }
        
        // 银行账号信息及委托扣款协议JSON
        String ACCOUNTANDAGREEMENTJSON = busRecord.get("ACCOUNTANDAGREEMENTJSON") == null ? ""
                : busRecord.get("ACCOUNTANDAGREEMENTJSON").toString();
        if (StringUtils.isNotEmpty(ACCOUNTANDAGREEMENTJSON)) {
            List<Map<String, Object>> accountAndAgreementList = JSON.parseObject(ACCOUNTANDAGREEMENTJSON,
                    List.class);
            
            request.setAttribute("accountAndAgreementList", accountAndAgreementList);
        }
        
        // 申领发票JSON
        String APPLYINVOICE_JSON = busRecord.get("APPLYINVOICE_JSON") == null ? ""
                : busRecord.get("APPLYINVOICE_JSON").toString();
        if (StringUtils.isNotEmpty(APPLYINVOICE_JSON)) {
            List<Map<String, Object>> invoiceapplyList = JSON.parseObject(APPLYINVOICE_JSON, List.class);
            
            request.setAttribute("invoiceapplyList", invoiceapplyList);
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/4/27 16:22:00
     * @param
     * @return
     */
    @RequestMapping(params = "msbasqFileList")
    @ResponseBody
    public Map<String, Object> msbasqFileList(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        String sdpqFileIds = request.getParameter("sdpqFileIds");
        String zfjFileIds = request.getParameter("zfjFileIds");
        String ghjFileIds = request.getParameter("ghjFileIds");
        String zrzyFileIds = request.getParameter("zrzyFileIds");
        String sthjFileIds = request.getParameter("sthjFileIds");
        List<Map<String, Object>> sdpqFileList = null;
        if (StringUtils.isNotEmpty(sdpqFileIds)) {
            sdpqFileList = gyjsjfwzydjService.getTaxRelatedFileList(sdpqFileIds);
        }
        List<Map<String, Object>> zfjFileList = null;
        if (StringUtils.isNotEmpty(zfjFileIds)) {
            zfjFileList = gyjsjfwzydjService.getTaxRelatedFileList(zfjFileIds);
        }
        List<Map<String, Object>> ghjFileList = null;
        if (StringUtils.isNotEmpty(ghjFileIds)) {
            ghjFileList = gyjsjfwzydjService.getTaxRelatedFileList(ghjFileIds);
        }
        List<Map<String, Object>> zrzyFileList = null;
        if (StringUtils.isNotEmpty(zrzyFileIds)) {
            zrzyFileList = gyjsjfwzydjService.getTaxRelatedFileList(zrzyFileIds);
        }
        List<Map<String, Object>> sthjFileList = null;
        if (StringUtils.isNotEmpty(sthjFileIds)) {
            sthjFileList = gyjsjfwzydjService.getTaxRelatedFileList(sthjFileIds);
        }
        returnMap.put("sdpqFileList", sdpqFileList);
        returnMap.put("zfjFileList", zfjFileList);
        returnMap.put("ghjFileList", ghjFileList);
        returnMap.put("zrzyFileList", zrzyFileList);
        returnMap.put("sthjFileList", sthjFileList);
        return returnMap;
    }

}
