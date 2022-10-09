/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.FlowNodeDao;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.util.Jbpm6Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 描述 流程节点操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowNodeService")
public class FlowNodeServiceImpl extends BaseServiceImpl implements FlowNodeService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowNodeDao dao;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
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
     * 描述 获取来源节点信息
     * @author Flex Hu
     * @created 2015年8月14日 下午5:23:55
     * @param toKey
     * @param linkDataArray
     * @return
     */
    private List<Map<String,Object>> getFromNodes(int toKey,JSONArray linkDataArray){
        List<Map<String,Object>> formNodes = new ArrayList<Map<String,Object>>();
        for(int i =0;i<linkDataArray.size();i++){
            Map<String,Object> linkData = (Map<String, Object>) linkDataArray.get(i);
            String text = (String) linkData.get("text");
            //获取来源节点的KEY 
            int fromKey = (Integer) linkData.get("from");
            //获取目标节点的KEY
            int targetKey = (Integer) linkData.get("to");
            if(targetKey==toKey){
                Map<String,Object> formNode = new HashMap<String,Object>();
                formNode.put("FROMNODE_KEY",fromKey);
                formNode.put("FROMNODE_LABLE",text);
                formNodes.add(formNode);
            }
        }
        return formNodes;
    }
    
    /**
     * 
     * 描述 保存流程节点信息
     * @author Flex Hu
     * @created 2015年8月14日 下午5:23:14
     * @param defId
     * @param flowVersion
     * @param defJson
     */
    public void saveFlowNodes(String defId,int flowVersion,String defJson){
        Map<String,Object> map = JSON.parseObject(defJson,Map.class);
        JSONArray nodeDataArray =  (JSONArray) map.get("nodeDataArray");
        JSONArray linkDataArray =  (JSONArray) map.get("linkDataArray");
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> data = (Map<String, Object>) nodeDataArray.get(i);
            String text = (String) data.get("text");
            int key = (Integer) data.get("key");
            String nodeType = (String) data.get("nodeType");
            List<Map<String,Object>> formNodes = this.getFromNodes(key, linkDataArray);
            if(formNodes.size()>0){
                for(Map<String,Object> formNode:formNodes){
                    Map<String,Object> flowNode = new HashMap<String,Object>();
                    flowNode.put("DEF_ID", defId);
                    flowNode.put("FLOW_VERSION", flowVersion);
                    flowNode.put("NODE_NAME", text.trim());
                    flowNode.put("NODE_KEY", key);
                    flowNode.put("FROMNODE_KEY", formNode.get("FROMNODE_KEY"));
                    flowNode.put("FROMNODE_LABLE", formNode.get("FROMNODE_LABLE"));
                    flowNode.put("NODE_TYPE", nodeType);
                    dao.saveOrUpdate(flowNode,"JBPM6_FLOWNODE",null);
                }
            }else{
                Map<String,Object> flowNode = new HashMap<String,Object>();
                flowNode.put("DEF_ID", defId);
                flowNode.put("FLOW_VERSION", flowVersion);
                flowNode.put("NODE_NAME", text);
                flowNode.put("NODE_KEY", key);
                flowNode.put("NODE_TYPE", nodeType);
                dao.saveOrUpdate(flowNode,"JBPM6_FLOWNODE",null);
            }
        }
    }
    
    /**
     * 
     * 描述 获取节点的KEY
     * @author Flex Hu
     * @created 2015年8月14日 下午5:38:38
     * @param nodeName
     * @param defId
     * @param flowVersion
     * @return
     */
    public int getKey(String nodeName,String defId,int flowVersion){
        return dao.getKey(nodeName, defId, flowVersion);
    }
    
    /**
     * 
     * 描述 获取下一环节节点,递归查找是属于任务节点的节点
     * @author Flex Hu
     * @created 2015年8月15日 下午12:50:55
     * @param toNodes
     * @param fromKey
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findByToNodes(List<Map<String,Object>> toNodes,
            int fromKey,String defId,int flowVersion){
        if(toNodes==null){
            toNodes = new ArrayList<Map<String,Object>>();
        }
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_FLOWNODE T");
        sql.append(" WHERE T.DEF_ID=? AND T.FLOW_VERSION=? AND T.FROMNODE_KEY=? ");
        List<Map<String,Object>> findNodes = dao.findBySql(sql.toString(),
                new Object[]{defId,flowVersion,fromKey},null);
        for(Map<String,Object> findNode:findNodes){
            String nodeType = (String) findNode.get("NODE_TYPE");
            if(nodeType.equals(Jbpm6Constants.TASK_NODE)||nodeType.equals(Jbpm6Constants.START_NODE)){
                toNodes.add(findNode);
            }else{
                int childFromKey = Integer.parseInt(findNode.get("NODE_KEY").toString());
                this.findByToNodes(toNodes, childFromKey, defId, flowVersion);
            }
        }
        return toNodes;
    }
    
    /**
     * 
     * 描述 获取节点下一环节节点,只获取属于任务节点
     * @author Flex Hu
     * @created 2015年8月15日 下午12:30:59
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findNextNodes(String defId,String nodeName,int flowVersion){
        int nodeKey = this.getKey(nodeName, defId, flowVersion);
        return this.findByToNodes(null, nodeKey, defId, flowVersion);
    }
    
    /**
     * 
     * 描述 获取下一环节的节点列表面向下拉框
     * @author Flex Hu
     * @created 2015年8月15日 下午1:06:28
     * @param defIdAndNodeNameValue
     * @return
     */
    public List<Map<String,Object>> findNextTaskNodesForSelect(String defIdAndNodeNameValue){
        String defId = defIdAndNodeNameValue.split(",")[0];
        String nodeName = defIdAndNodeNameValue.split(",")[1];
        int flowVersion = flowDefService.getFlowVersion(defId);
        List<Map<String,Object>> nextTaskNodes = this.findNextNodes(defId, nodeName, flowVersion);
        for(Map<String,Object> nextTaskNode:nextTaskNodes){
            nextTaskNode.put("TEXT", nextTaskNode.get("NODE_NAME"));
            nextTaskNode.put("VALUE", nextTaskNode.get("NODE_NAME"));
        }
        return nextTaskNodes;
    }
    
    /**
     * 
     * 描述 获取节点的名称
     * @author Flex Hu
     * @created 2015年8月17日 上午10:20:40
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeName(String defId,int flowVersion,String nodeType){
        return dao.getNodeName(defId, flowVersion, nodeType);
    }
    
    /***
     * 
     * 描述 获取某个节点的指向节点信息
     * @author Flex Hu
     * @created 2015年8月19日 上午10:24:11
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findNextFlowNodes(String defId,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT F.* FROM JBPM6_FLOWNODE F WHERE F.FROMNODE_KEY=(");
        sql.append("SELECT distinct(T.NODE_KEY) FROM JBPM6_FLOWNODE T WHERE T.NODE_NAME=? ");
        sql.append("AND T.FLOW_VERSION=? AND T.DEF_ID=? )");
        sql.append("AND F.FLOW_VERSION=? AND F.DEF_ID=? ");
        return dao.findBySql(sql.toString(), new Object[]{nodeName,flowVersion
            ,defId,flowVersion,defId}, null);
    }
    
    /**
     * 
     * 描述 获取任务节点的名称
     * @author Flex Hu
     * @created 2015年8月23日 上午10:03:36
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<String> findTaskNodeNames(String defId,int flowVersion){
        return dao.findTaskNodeNames(defId, flowVersion);
    }
    
    /**
     * 
     * 描述 获取来源任务名称
     * @author Flex Hu
     * @created 2015年8月24日 上午9:29:40
     * @param defId
     * @param flowVersion
     * @param toTaskNodeName
     * @return
     */
    public List<String> findFromTaskNodeNames(String defId,int flowVersion,String toTaskNodeName){
        return dao.findFromTaskNodeNames(defId, flowVersion, toTaskNodeName);
    }
    
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion){
        dao.deleteByDefIdAndVersion(defId, flowVersion);
    }
    
    /**
     * 
     * 描述 获取节点信息
     * @author Flex Hu
     * @created 2015年11月10日 下午3:25:27
     * @param defId
     * @param flowVersion
     * @param nodeName
     * @return
     */
    public Map<String,Object> getFlowNode(String defId,int flowVersion,String nodeName){
        StringBuffer sql = new StringBuffer("select N.* from JBPM6_FLOWNODE N ");
        sql.append("WHERE N.DEF_ID=? AND N.FLOW_VERSION=? AND N.NODE_NAME=? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{defId,flowVersion,nodeName},null);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 拷贝流程节点数据
     * @author Flex Hu
     * @created 2016年3月31日 下午3:45:35
     * @param targetDefId
     * @param targetVersion
     * @param newDefId
     */
    public void copyFlowNodes(String targetDefId,int targetVersion,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_FLOWNODE(NODE_ID,DEF_ID,FLOW_VERSION");
        sql.append(",NODE_NAME,NODE_KEY,FROMNODE_KEY,FROMNODE_LABLE,NODE_TYPE)");
        sql.append(" SELECT RAWTOHEX(SYS_GUID()),?,BR.FLOW_VERSION,BR.NODE_NAME,");
        sql.append("BR.NODE_KEY,BR.FROMNODE_KEY,BR.FROMNODE_LABLE,BR.NODE_TYPE");
        sql.append(" FROM JBPM6_FLOWNODE BR WHERE BR.DEF_ID=? AND BR.FLOW_VERSION=?");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId,targetVersion});
    }
    @Override
    public List<Map<String, Object>> findPreFlowNodes(String defId,
            String nodeName, int flowVersion) {
        StringBuffer sql = new StringBuffer("SELECT F.* FROM JBPM6_FLOWNODE F WHERE F.NODE_KEY=(");
        sql.append("SELECT distinct(T.FROMNODE_KEY) FROM JBPM6_FLOWNODE T WHERE T.NODE_NAME=? ");
        sql.append("AND T.FLOW_VERSION=? AND T.DEF_ID=? )");
        sql.append("AND F.FLOW_VERSION=? AND F.DEF_ID=? ");
        return dao.findBySql(sql.toString(), new Object[]{nodeName,flowVersion
            ,defId,flowVersion,defId}, null);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-23 上午11:48:49
     * @param defId
     * @param flowVersion
     * @param key
     */
    public List<Map<String,Object>> getFlowNodeByKey(String defId,String flowVersion,String key){
        StringBuffer sql = new StringBuffer("SELECT F.* FROM JBPM6_FLOWNODE F WHERE F.NODE_KEY=? ");
        sql.append("AND F.FLOW_VERSION=? AND F.DEF_ID=? ");
        return dao.findBySql(sql.toString(), new Object[]{key,flowVersion
            ,defId}, null);
    }
    /**
     * 描述   获取特定节点有权限审核人员信息
     * @author Corliss Chen
     * @created 2017年3月17日 下午2:40:15
     * @param defId
     * @param flowVersion
     * @param key
     * @param nodeName
     * @return
     */
    @Override
    public Map<String, Object> getFlowNodeReviewerByKey(String defId, int flowVersion, String key,
            String nodeName) {
        StringBuffer sql = new StringBuffer("SELECT T.VARS_VALUE,T.AUDITER_RULE,A.CONFIG_NAME,A.CONFIG_CODE,"
                + "A.AUDITER_TYPE,A.SELECTOR_URL,A.SELECTOR_HEIGHT,A.SELECTOR_WIDTH FROM JBPM6_NODEAUDITERCONF T ");
        sql.append("LEFT JOIN JBPM6_AUDITCONFIG A ON T.CONFIG_ID = A.CONFIG_ID ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF JF ON T.DEF_ID = JF.DEF_ID ");
        sql.append("WHERE T.NEXT_NODE_NAME = ? AND T.DEF_ID = ? AND T.DEF_VERSION = ? ");
        sql.append("AND JF.DEF_KEY=? AND ROWNUM=1");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{nodeName,defId,flowVersion,key},null);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    
}
