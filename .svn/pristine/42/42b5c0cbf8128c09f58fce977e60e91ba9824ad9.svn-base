/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.EsuperResDao;
import net.evecom.platform.bsfw.service.EsuperResService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2016年4月13日 下午2:00:07
 */
@Service("esuperResService")
public class EsuperResServiceImpl extends BaseServiceImpl implements EsuperResService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EsuperResServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private EsuperResDao dao;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
    * 
    */
    @Resource
    private DepartmentService departmentService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
    * 
    */
    @Resource
    private FlowTaskService flowTaskService;
    /**
    * 
    */
    @Resource
    private FlowNodeService flowNodeService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 获取过程ID
     * @author Flex Hu
     * @created 2016年5月4日 下午4:50:10
     * @param nodeName
     * @param itemCode
     * @return
     */
    public String getProcessId(String nodeName,String itemCode){
        return dao.getProcessId(nodeName, itemCode);
    }

    /**
     * 
     * 描述 保存办理日志
     * 
     * @author Flex Hu
     * @created 2016年4月14日 上午9:44:34
     * @param flowVars
     * @param eflowExe
     * @param serviceItem
     */
    public void saveLogData(Map<String, Object> flowVars,Map<String, Object> eflowExe,Map<String, Object> serviceItem) {
        String exeId = (String) eflowExe.get("EXE_ID");
        // 获取流程定义ID
        String defId = (String) eflowExe.get("DEF_ID");
        // 定义流程版本号
        int flowVersion = Integer.parseInt(eflowExe.get("DEF_VERSION").toString());
        List<Map<String, Object>> taskList = flowTaskService.findHandleOverTaskForLog(exeId);
        for (Map<String, Object> task : taskList) {
            String taskId = (String) task.get("TASK_ID");
            boolean isExists = dao.isExists(exeId, taskId, "logDataSubmit");
            if (!isExists) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("BUS_ID", exeId);
                String processId = this.getProcessId(task.get("TASK_NODENAME").toString(),
                        eflowExe.get("ITEM_CODE").toString());
                data.put("PROCESS_ID", processId);
                data.put("DATA_TYPE", "logDataSubmit");
                data.put("STATUS", "-1");
                data.put("TASK_ID", taskId);
                // 定义任务状态
                String taskStatus = task.get("TASK_STATUS").toString();
                // 定义任务名称
                String taskName = (String) task.get("TASK_NODENAME");
                String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                String operType = "";
                if (taskName.equals(startNodeName)) {
                    operType = "申请";
                } else if (taskStatus.equals("2")) {
                    operType = "已审核";
                } else if (taskStatus.equals("3")) {
                    operType = "退回";
                } else if (taskStatus.equals("6")) {
                    operType = "办结";
                }
                String endTime = (String) task.get("END_TIME");
                endTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss"),
                        "yyyyMMddHHmmss");
                StringBuffer content = new StringBuffer("");
                content.append("<field_1>").append(endTime).append("</field_1>");
                content.append("<field_2>").append(task.get("ASSIGNER_NAME")).append("</field_2>");
                content.append("<field_3>").append(operType).append("</field_3>");
                content.append("<field_4>").append(task.get("HANDLE_OPINION")).append("</field_4>");
                content.append("<field_5>").append(task.get("TASK_NODENAME")).append("</field_5>");
                data.put("CONTENT", content.toString());
                dao.saveOrUpdate(data, "T_BSFW_ESUPER_RES", null);
            }
        }
    }
    

    /**
     * 
     * 描述 保存基本信息
     * 
     * @author Flex Hu
     * @created 2016年4月13日 下午2:50:46
     * @param flowVars
     * @param eflowExe
     * @param serviceItem
     */
    public void saveBasicData(Connection conn,Map<String, Object> flowVars, Map<String, Object> eflowExe,
            Map<String, Object> serviceItem) {
        String exeId = (String) eflowExe.get("EXE_ID");
        boolean isExists = DbUtil.isExists(conn, "T_DATATRANSFER_SOURCE",
                new String[]{"TRANSACTION_ID","MSG_NAME"}, 
                new Object[]{exeId,EsuperResService.MSG_NAME_BASEDATA});
        if (!isExists) {
            Map<String, Object> basicData = new HashMap<String, Object>();
            basicData.put("MESSAGE_NUM",System.currentTimeMillis());
            basicData.put("TRANSACTION_ID",exeId);
            basicData.put("MSG_NAME",EsuperResService.MSG_NAME_BASEDATA);
            basicData.put("BUSINESS_ID",exeId);
            basicData.put("VERSION","1.0");
            basicData.put("SOURCES_ID","PZWS001");
            basicData.put("SOURCES_CODE","PZWS001");
            basicData.put("ORIGINAL_ID","PZWS001");
            basicData.put("ORIGINAL_CODE","PZWS001");
            basicData.put("PROCESS_ID",EsuperResService.ITEM_HEAD+serviceItem.get("ITEM_CODE"));
            // 获取所属部门编码
            String depCode = (String) serviceItem.get("SSBMBM");
            // 获取事项的性质
            String SXXZ = (String) serviceItem.get("SXXZ");
            Map<String, Object> dic1 = dictionaryService.get("ServiceItemXz", SXXZ);
            // 获取事项类型
            String SXLX = (String) serviceItem.get("SXLX");
            Map<String, Object> dic2 = dictionaryService.get("ServiceItemType", SXLX);
            // 获取部门信息
            Map<String, Object> depInfo = departmentService.getByDepCode(depCode);
            String DEP_NAME = (String) depInfo.get("DEPART_NAME");
            // 获取申请人类型
            String BSYHLX = (String) eflowExe.get("BSYHLX");
            String yhlx = "";
            // 定义申请人名称
            String applyName = "";
            // 定义机构类型或者证件类型
            String jgOrzjType = "";
            // 定义证件号码
            String zjhm = "";
            // 定义经办人证件类型
            String jbrzjlx = "";
            if (BSYHLX.equals("1")) {
                yhlx = "个人用户";
                applyName = (String) eflowExe.get("SQRMC");
                zjhm = (String) eflowExe.get("SQRSFZ");
                jgOrzjType = (String) dictionaryService.get("DocumentType", (String) eflowExe.get("SQRZJLX")).get(
                        "DIC_NAME");
                jbrzjlx = (String) dictionaryService.get("DocumentType", (String) eflowExe.get("SQRZJLX")).get(
                        "DIC_NAME");
            } else {
                yhlx = "企业用户";
                applyName = (String) eflowExe.get("SQJG_NAME");
                zjhm = (String) eflowExe.get("SQJG_CODE");
                jgOrzjType = (String) dictionaryService.get("OrgType", (String) eflowExe.get("SQJG_TYPE")).get(
                        "DIC_NAME");
                jbrzjlx = (String) dictionaryService.get("DocumentType", (String) eflowExe.get("JBR_ZJLX")).get(
                        "DIC_NAME");
            }
           
            StringBuffer content = new StringBuffer("");
            content.append("<field_1>").append((String) serviceItem.get("ITEM_NAME")).append("</field_1>");
            content.append("<field_2>").append(DEP_NAME).append("</field_2>");
            content.append("<field_3>").append(dic1.get("DIC_NAME")).append("</field_3>");
            content.append("<field_4>").append(serviceItem.get("CNQXGZR")).append("个工作日").append("</field_4>");
            content.append("<field_5>").append(eflowExe.get("SUBJECT")).append("</field_5>");
            content.append("<field_6>").append(dic2.get("DIC_NAME")).append("</field_6>");
            content.append("<field_7>").append(yhlx).append("</field_7>");
            content.append("<field_8>").append(applyName).append("</field_8>");
            content.append("<field_9>").append(jgOrzjType).append("</field_9>");
            content.append("<field_10>").append(zjhm).append("</field_10>");
            content.append("<field_11>").append(eflowExe.get("JBR_NAME")).append("</field_11>");
            content.append("<field_12>").append(eflowExe.get("JBR_LXDH")).append("</field_12>");
            content.append("<field_13>").append(jbrzjlx).append("</field_13>");
            content.append("<field_14>").append(eflowExe.get("JBR_ZJHM")).append("</field_14>");
            basicData.put("XMLBODY", content.toString());
            basicData.put("STATUS",EsuperResService.DEFAULT_STATUS);
            basicData.put("IS_PARSE","1");
            basicData.put("RECEIVE_TYPE","1");
            DbUtil.saveOrUpdate(conn, "T_DATATRANSFER_SOURCE", basicData, null, false);
        }
    }

    /**
     * 
     * 描述 保存监察数据信息
     * 
     * @author Flex Hu
     * @created 2016年4月13日 下午2:50:46
     * @param flowVars
     * @param eflowExe
     * @param serviceItem
     */
    public void saveSupervisionData(Connection conn,Map<String, Object> flowVars, Map<String, Object> eflowExe,
            Map<String, Object> serviceItem) {
        String exeId = (String) eflowExe.get("EXE_ID");
        // 获取流程定义ID
        /*String defId = (String) eflowExe.get("DEF_ID");*/
        // 定义流程版本号
        /*int flowVersion = Integer.parseInt(eflowExe.get("DEF_VERSION").toString());*/
        List<Map<String, Object>> taskList = flowTaskService.findHandleOverTaskForLog(exeId);
        for (Map<String, Object> task : taskList) {
            String taskId = (String) task.get("TASK_ID");
            boolean isExists = DbUtil.isExists(conn, "T_DATATRANSFER_SOURCE",
                    new String[]{"TRANSACTION_ID","MSG_NAME"}, 
                    new Object[]{taskId,EsuperResService.MSG_NAME_SUPERVIOSN});
            if (!isExists) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("MESSAGE_NUM",System.currentTimeMillis());
                data.put("TRANSACTION_ID",taskId);
                data.put("MSG_NAME",EsuperResService.MSG_NAME_SUPERVIOSN);
                data.put("BUSINESS_ID",exeId);
                data.put("VERSION","1.0");
                data.put("SOURCES_ID","PZWS001");
                data.put("SOURCES_CODE","PZWS001");
                data.put("ORIGINAL_ID","PZWS001");
                data.put("ORIGINAL_CODE","PZWS001");
                String processId = this.getProcessId(task.get("TASK_NODENAME").toString(),
                        eflowExe.get("ITEM_CODE").toString());
                data.put("PROCESS_ID", processId);
                StringBuffer content = new StringBuffer("");
                content.append("<field_1>").append(DateTimeUtil
                        .getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</field_1>");
                data.put("XMLBODY", content.toString());
                data.put("STATUS",EsuperResService.DEFAULT_STATUS);
                data.put("IS_PARSE","1");
                data.put("RECEIVE_TYPE","1");
                DbUtil.saveOrUpdate(conn, "T_DATATRANSFER_SOURCE", data, null, false);
            }
        }
    }

    /**
     * 
     * 描述 保存监察数据
     * 
     * @author Flex Hu
     * @created 2016年4月13日 下午2:12:44
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveEsuperRes(Map<String, Object> flowVars) {
        // 获取是否是暂存操作
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE");
        
        if (isTempSave.equals("-1")) {
            Map<String,Object> dbUrlMap = dictionaryService.get("ExDataDbUrl","1");
            Map<String,Object> userMap = dictionaryService.get("ExDataDbUrl","2");
            Map<String,Object> passMap = dictionaryService.get("ExDataDbUrl","3");
            String dbUrl = dbUrlMap.get("DIC_DESC").toString();
            String username = userMap.get("DIC_DESC").toString();
            String password = passMap.get("DIC_DESC").toString();
            Connection conn = null;
            try {
                conn = DbUtil.getConnect(dbUrl, username, password);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
            // 获取流程实例ID
            String exeId = (String) flowVars.get("EFLOW_EXEID");
            // 获取流程实例数据
            Map<String, Object> eflowExe = jbpmService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String itemCode = (String) eflowExe.get("ITEM_CODE");
            if (StringUtils.isNotEmpty(itemCode)) {
                // 获取办事事项信息
                Map<String, Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                        new String[] { "ITEM_CODE" }, new Object[] { itemCode });
                if (serviceItem != null) {
                    // 获取承诺时限工作日
                    int CNQXGZR = Integer.parseInt(serviceItem.get("CNQXGZR").toString());
                    // 获取办件类型
                    String SXLX = (String) serviceItem.get("SXLX");
                    if (CNQXGZR != 0 || SXLX.equals("1")) {
                        try {
                            // 保存基本信息数据
                            //连接33那台机子的数据库 暂未启用  暂行注释 csk 2017 12 27 
//                            this.saveBasicData(conn,flowVars, eflowExe, serviceItem);
                            // 保存日志信息数据
                            //this.saveLogData(flowVars, eflowExe, serviceItem);
                            // 保存监察信息数据
                            //连接33那台机子的数据库 暂未启用  暂行注释 csk 2017 12 27 
//                            this.saveSupervisionData(conn,flowVars, eflowExe, serviceItem);
                        } catch (Exception e) {
                            // TODO: handle exception
                            log.error("", e);
                            //保存监察数据出错，继续执行后续操作
                            return flowVars;
                        }
                    }
                }
            }
            DbUtils.closeQuietly(conn);
        }
        return flowVars;
    }
}
