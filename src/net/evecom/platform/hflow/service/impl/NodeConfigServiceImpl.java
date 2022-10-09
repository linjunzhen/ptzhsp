/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.hflow.dao.NodeConfigDao;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 节点配置操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("nodeConfigService")
public class NodeConfigServiceImpl extends BaseServiceImpl implements NodeConfigService {
    /**
     * 所引入的dao
     */
    @Resource
    private NodeConfigDao dao;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     *
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 覆盖获取实体dao方法
     * 描述
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
     * 描述 根据流程定义ID和流程节点名称获取配置的决策代码
     * @author Flex Hu
     * @created 2015年8月20日 下午4:31:56
     * @param defId
     * @param nodeName
     * @return
     */
    public String getTaskDecideCode(String defId,String nodeName,int flowVersion){
        return dao.getTaskDecideCode(defId, nodeName,flowVersion);
    }

    /**
     *
     * 描述 获取截止日期
     * @author Flex Hu
     * @created 2015年11月7日 下午12:35:31
     * @param nodeName
     * @param flowVars
     * @return
     */
    public String getDeadLineDate(String nodeName,Map<String,Object> flowVars){
        //获取流程定义ID
        String defId = (String) flowVars.get("EFLOW_DEFID");
        //获取版本号
        String flowVersion = String.valueOf(flowVars.get("EFLOW_DEFVERSION"));
        //获取配置信息
        Map<String, Object> nodeConfig = dao.getByJdbc("JBPM6_NODECONFIG", new String[] { "NODE_NAME", "DEF_ID",
            "DEF_VERSION" }, new Object[] { nodeName, defId, flowVersion });
        //获取申报号
        String EFLOW_EXEID = (String) flowVars.get("EFLOW_EXEID");
        //String s = EFLOW_EXEID.substring( 0, 4);
        String itemDeadLine = "";
        String nodeType = "";
        String sskey = "";
        List<Map<String,Object>> keylist = dictionaryService.findByTypeCode("ssdjlc");
        for(Map<String,Object> key : keylist){
            sskey = sskey.concat((String) key.get("DIC_CODE"));
        }
        String defKey = (String) flowVars.get("EFLOW_DEFKEY");
        Map<String, Object> node = dao.getByJdbc("JBPM6_FLOWNODE",
                new String[] { "DEF_ID", "FLOW_VERSION", "NODE_NAME" },
                new Object[] { defId, flowVersion, nodeName });
        if(node!=null&&sskey.indexOf(defKey)>=0){
            String fromKey = node.get("FROMNODE_KEY").toString();
            Map<String,Object> fromNode = flowNodeService.getFlowNodeByKey(defId,flowVersion,fromKey).get(0);
            if(fromNode.get("NODE_TYPE").equals("parallel")){
                nodeType = ((Map<String, Object>) dao
                        .getAllByJdbc("JBPM6_FLOWNODE", new String[] { "DEF_ID", "FLOW_VERSION", "NODE_KEY" },
                                new Object[] { defId, flowVersion, fromNode.get("FROMNODE_KEY") })
                        .get(0)).get("NODE_TYPE").toString();
                /*nodeType = dao
                        .getByJdbc("JBPM6_FLOWNODE",
                                new String[] { "DEF_ID", "FLOW_VERSION", "NODE_KEY" },
                                new Object[] { defId, flowVersion,fromNode.get("FROMNODE_KEY") })
                        .get("NODE_TYPE").toString();*/
            }else{
                nodeType = fromNode.get("NODE_TYPE").toString();
            }
        }
//        if (!"ptsy".equals(s)) {
        itemDeadLine = executionService.getDeadLineTimeForItem(EFLOW_EXEID,nodeType);
//        }
        //获取工作日信息
        int WORKDAY_LIMIT = Integer.parseInt(nodeConfig.get("WORKDAY_LIMIT").toString());
        //获取今天日期
        String today = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        if(WORKDAY_LIMIT==-1){
            return today + " 23:59";
        }else if(WORKDAY_LIMIT!=0){
            /*int hangCount = executionService.getHangDayCount(EFLOW_EXEID);
            String deadLineDate = workdayService.getDeadLineDate(today,WORKDAY_LIMIT+hangCount);*/
            String deadLineDate = workdayService.getDeadLineDate(today,WORKDAY_LIMIT);
            if(StringUtils.isNotEmpty(deadLineDate)){
                return deadLineDate + " 23:59";
            }else{
                return null;
            }
        }else if(StringUtils.isNotEmpty(itemDeadLine)){
            return itemDeadLine;
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 将拷贝旧版本的流配置信息到新的版本
     * @author Flex Hu
     * @created 2015年11月11日 下午2:11:34
     * @param defId
     * @param newVersion
     * @param oldVersion
     */
    public void saveNewFlowVersionConfig(String defId,int newVersion,int oldVersion){
        StringBuffer sql = new StringBuffer("select N.* from JBPM6_NODECONFIG N");
        sql.append(" WHERE N.DEF_ID=? AND N.DEF_VERSION=? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{defId,oldVersion}, null);
        for(Map<String,Object> map:list){
            Map<String,Object> config = map;
            config.put("NODE_CONFID","");
            config.put("DEF_VERSION",newVersion);
            dao.saveOrUpdate(config, "JBPM6_NODECONFIG",null);
        }
    }
    
    /**
     * 
     * 描述 获取省网办的发起节点的名称
     * @author Flex Hu
     * @created 2016年1月26日 下午3:06:05
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeNameForProvinceStart(String defId,int flowVersion){
        return dao.getNodeNameForProvinceStart(defId, flowVersion);
    }
    
    /**
     * 
     * 描述 拷贝节点配置信息
     * @author Flex Hu
     * @created 2016年3月31日 下午4:12:25
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyNodeConfigs(String targetDefId,int targetFlowVersion,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_NODECONFIG(NODE_CONFID,CONFIG_ID,NODE_NAME,DEF_ID");
        sql.append(",IS_ASSIGNDEADTIME,IS_ADDAGREECONTROL,DEF_VERSION,IS_TASKORDER,");
        sql.append("TASK_ORDER_CODE,WORKDAY_LIMIT,IS_ADDPASS,IS_PROVINCE_START");
        sql.append(",IS_ADDPERUAL,PUBDOC_RULE,UPLOAD_FILES)");
        sql.append("SELECT RAWTOHEX(SYS_GUID()),CONFIG_ID,NODE_NAME,?,IS_ASSIGNDEADTIME,");
        sql.append("IS_ADDAGREECONTROL,DEF_VERSION,IS_TASKORDER,TASK_ORDER_CODE,WORKDAY_LIMIT,");
        sql.append("IS_ADDPASS,IS_PROVINCE_START,IS_ADDPERUAL,PUBDOC_RULE,UPLOAD_FILES");
        sql.append(" FROM JBPM6_NODECONFIG  WHERE DEF_ID=? AND DEF_VERSION=?");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId,targetFlowVersion});
    }
}
