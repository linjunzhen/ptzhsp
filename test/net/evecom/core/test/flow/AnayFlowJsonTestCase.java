/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FileUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月6日 下午5:06:33
 */
public class AnayFlowJsonTestCase extends BaseTestCase{
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(AnayFlowJsonTestCase.class);
    /**
     * 描述
     * @author Flex Hu
     * @created 2015年8月6日 下午5:06:33
     * @param args
     */
    public static void main(String[] args) {
        Map<String,Object> flow1 = new HashMap<String,Object>();
        flow1.put("name", "fdsf");
        flow1.put("key", "fds");
        Map<String,Object> flow2 = flow1;
        flow2.put("name", "ddddd");
        log.info(flow2.get("name"));
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月14日 下午5:11:37
     * @param toKey
     * @param linkDataArray
     * @return
     */
    private List<Map<String,Object>> getFromNode(int toKey,JSONArray linkDataArray){
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
     * 描述
     * @author Flex Hu
     * @created 2015年8月14日 下午4:16:55
     */
    @Test
    public void testFindNextNodeNames(){
        String json = FileUtil.getContentOfFile("d:/test.json");
        Map<String,Object> map = JSON.parseObject(json,Map.class);
        JSONArray nodeDataArray =  (JSONArray) map.get("nodeDataArray");
        JSONArray linkDataArray =  (JSONArray) map.get("linkDataArray");
        List<Map<String,Object>> flowNodes = new ArrayList<Map<String,Object>>();
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> data = (Map<String, Object>) nodeDataArray.get(i);
            String text = (String) data.get("text");
            int key = (Integer) data.get("key");
            String nodeType = (String) data.get("nodeType");
            List<Map<String,Object>> formNodes = this.getFromNode(key, linkDataArray);
            if(formNodes.size()>0){
                for(Map<String,Object> formNode:formNodes){
                    Map<String,Object> flowNode = new HashMap<String,Object>();
                    flowNode.put("NODE_NAME", text);
                    flowNode.put("NODE_KEY", key);
                    flowNode.put("FROMNODE_KEY", formNode.get("FROMNODE_KEY"));
                    flowNode.put("FROMNODE_LABLE", formNode.get("FROMNODE_LABLE"));
                    flowNode.put("NODE_TYPE", nodeType);
                    flowNodes.add(flowNode);
                }
            }else{
                Map<String,Object> flowNode = new HashMap<String,Object>();
                flowNode.put("NODE_NAME", text);
                flowNode.put("NODE_KEY", key);
                flowNode.put("NODE_TYPE", nodeType);
                flowNodes.add(flowNode);
            }
        }
        for(Map<String,Object> flowNode:flowNodes){
        }
    }
    /**
     * 
     * 描述 判断是否存在节点名称重名
     * @author Flex Hu
     * @created 2015年8月6日 下午5:25:16
     */
    @Test
    public void testFindSameNodes(){
        String json = FileUtil.getContentOfFile("d:/test.json");
        //log.info(json.replaceAll("[\\t\\n\\r]", ""));
        Map<String,Object> map = JSON.parseObject(json,Map.class);
        JSONArray nodeDataArray =  (JSONArray) map.get("nodeDataArray");
        //获取节点类型
        Set<String> nodeNames = new HashSet<String>();
        String sameNodeName = "";
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> data = (Map<String, Object>) nodeDataArray.get(i);
            //获取节点类型
            String nodeType = (String) data.get("nodeType");
            //获取节点名称
            String nodeName = (String) data.get("text");
            if(nodeType.equals("start")||nodeType.equals("task")){
                if(nodeNames.contains(nodeName)){
                    sameNodeName = nodeName;
                    break;
                }else{
                    nodeNames.add(nodeName);
                }
            }
        }
        log.info("相同节点:"+sameNodeName);
       
    }

}
