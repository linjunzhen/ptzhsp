/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.util.Jbpm6Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月5日 下午5:04:19
 */
public class FlowDefTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowDefTestCase.class);
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    
    /**
     * 
     * 描述 测试新生成的图片
     * @author Flex Hu
     * @created 2015年12月21日 上午10:55:07
     */
    @Test
    public void testGenNewImg(){
        Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",
                new String[]{"DEF_KEY"},new Object[]{"006"});
        //获取定义的XML文件
        String defXml = (String) flowDef.get("DEF_XML");
        Map<String,Set<String>> nodeNames = new HashMap<String,Set<String>>();
        Set<String> set1 = new HashSet<String>();
        set1.add("预审");
        set1.add("并联审查");
        nodeNames.put(AllConstant.COLOR_GREEN, set1);
        Set<String> set2 = new HashSet<String>();
        set2.add("拟核准《项目申请报告+》");
        nodeNames.put(AllConstant.COLOR_BLUE, set2);
        flowDefService.genFlowImgToDisk(defXml, "d:/1.png", nodeNames);
    }
    
    public static void main(String[] args){
        String defXml = FileUtil.getContentOfFile("d:/2.xml");
        String nsp = "xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\"";
        String replaceNsp = "eveflownsp=\"\"";
        defXml = defXml.replaceAll("stroke=\"transparent\"","stroke=\"none\"");
        defXml = defXml.replaceAll("fill=\"transparent\"","fill=\"none\"");
        //先将标准进行替换
        defXml = defXml.replaceAll("xmlns:xlink=\"http://www.w3.org/1999/xlink\"","");
        defXml = defXml.replaceAll("xmlns=\"http://www.w3.org/2000/svg\"",replaceNsp);
        log.info(defXml);
        Document document = XmlUtil.stringToDocument(defXml);
        Element root = document.getRootElement();
        List<Node> nodes = root.selectNodes("//text[@fill='whitesmoke']");
        log.info("大小:"+nodes.size());
    }
    
    public static void main2(String[] args){
        String defXml = FileUtil.getContentOfFile("d:/3.xml");
        String nsp = "xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\"";
        String replaceNsp = "eveflownsp=\"\"";
        defXml = defXml.replaceAll("stroke=\"transparent\"","stroke=\"none\"");
        defXml = defXml.replaceAll("fill=\"transparent\"","fill=\"none\"");
        //先将标准进行替换
        defXml = defXml.replaceAll(nsp, replaceNsp);
        Document document = XmlUtil.stringToDocument(defXml);
        Element root = document.getRootElement();
        List<Node> nodes = root.selectNodes("//text[@fill='whitesmoke']");
        for(Node node:nodes){
            Element textEle = (Element) node;
            String textValue = textEle.getText().trim();
            if(StringUtils.isEmpty(textValue)){
                textEle.getParent().remove(textEle);
            }
        }
        defXml = document.asXML();
        document = XmlUtil.stringToDocument(defXml);
        root = document.getRootElement();
        nodes = root.selectNodes("//text[@fill='whitesmoke']");
        for(Node node:nodes){
            Element textEle = (Element) node;
            //获取父亲节点
            Element parentEle = textEle.getParent();
            if(parentEle!=null){
                //获取孩子节点
                List<Node> textChilds =  parentEle.selectNodes("text");
                if(textChilds!=null&&textChilds.size()>=2){
                    StringBuffer finalText = new StringBuffer(textChilds.get(0).getText().trim());
                    for(int i =1;i<textChilds.size();i++){
                        String otherText = textChilds.get(i).getText().trim();
                        finalText.append(otherText);
                        parentEle.remove(textChilds.get(i));
                    }
                    textChilds.get(0).setText(finalText.toString());
                }
            }
           
        }
        log.info(root.asXML());
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月17日 上午11:00:06
     */
    @Test
    public void convertDefXmlToPng(){
        Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",
                new String[]{"DEF_ID"},new Object[]{"2c9084a64f05b347014f05b3fa8c0001"});
//        String defXml = (String) flowDef.get("DEF_XML");
//        Set<String> nodes =new HashSet<String>();
        //flowDefService.genFlowImgToDisk(defXml, null, "d:/1.png");
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月12日 上午11:09:10
     */
    @Test
    public void sysDefJson(){
        Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",
                new String[]{"DEF_ID"},new Object[]{"2c9084a64f05b347014f05b3fa8c0001"});
        String defJson = (String) flowDef.get("DEF_JSON");
        Map<String,Object> map = JSON.parseObject(defJson,Map.class);
        JSONArray nodeDataArray =  (JSONArray) map.get("nodeDataArray");
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> nodeData = (Map<String, Object>) nodeDataArray.get(i);
            //获取节点名称
            String nodeName = (String) nodeData.get("text");
            //获取节点类型
            String nodeType = (String) nodeData.get("nodeType");
            if(nodeType.equals(Jbpm6Constants.START_NODE)||nodeType.equals(Jbpm6Constants.TASK_NODE)){
            }
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月5日 下午5:05:04
     */
    @Test
    public void saveOrUpdate(){
        Map<String,Object> flowDef = new HashMap<String,Object>();
        flowDef.put("DEF_NAME", "测试请假流程");
        flowDef.put("TYPE_ID","2c9084a64efd15a2014efd1bea740011");
        flowDef.put("DEF_KEY", "TestFlow");
        flowDef.put("VERSION", 1);
        flowDef.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        flowDefService.saveOrUpdate(flowDef, "JBPM6_FLOWDEF",null);
    }
    
    @Test
    public void testGetXml(){
        Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",new String[]{"DEF_ID"},
                new Object[]{"4028818a50b66a9f0150b67ec7b50001"});
        log.info(flowDef.get("DEF_XML"));
    }
    
    @Test
    public void genFlowImgToDisk(){
        Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",new String[]{"DEF_ID"},
                new Object[]{"4028818a50b66a9f0150b67ec7b50001"});
        String defXml = (String) flowDef.get("DEF_XML");
        Set<String> currentNodeNames =new HashSet<String>();
        currentNodeNames.add("审批局项目投资处领导审签");
        //flowDefService.genFlowImgToDisk(defXml, currentNodeNames,"d:/1.png");
    }
    
    @Test
    public void copyFlowDefAndConfig(){
        flowDefService.copyFlowDefAndConfig("jjjlc16","拷贝流程","mycopy");
    }
}
