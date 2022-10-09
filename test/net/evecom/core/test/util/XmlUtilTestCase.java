/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import javax.xml.xpath.*;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.XmlUtil;

/**
 * 
 * 描述
 * 
 * @author Water Guo
 * @created 2015年1月23日 上午10:45:37
 */
public class XmlUtilTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(XmlUtilTestCase.class);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月17日 上午11:14:19
     * @param args
     */
    public static void main(String[] args){
        String xml = FileUtil.getContentOfFile("d:/1.xml");
        String json = XmlUtil.xml2Json(xml);
        log.info(json);
        Map<String,Object> map = JSON.parseObject(json,Map.class);
        //log.info(map.toString());
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月17日 下午12:29:58
     */
    @Test
    public void queryNameSpace(){
        /*List<Node> nodes = root.selectNodes("//*[local-name()='text' and "
                + "namespace-uri()='http://www.w3.org/2000/svg'][@fill='whitesmoke']");*/
    }
    
    @Test
    public void testChangeXml(){
        String xml = FileUtil.getContentOfFile("d:/userel.xml");
        Document document = XmlUtil.stringToDocument(xml);
        Element root = document.getRootElement();
        Element node = (Element) root.selectSingleNode("//data");
        List<Element> childs = node.elements();
        for(Element child:childs){
            //log.info(child.getName()+","+child.getText());
            String field = "${"+child.getName().toUpperCase()+"!}";
            child.setText(field);
        }
        FileUtil.writeTextToDisk("d:/user_el.xml", document.asXML());
    }
    
    @Test
    public void testSelectSingleNode2(){
        String xml = FileUtil.getContentOfFile("d:/info.xml");
        Document document = XmlUtil.stringToDocument(xml);
        Element root = document.getRootElement();
        //判断是否成功
        //String result = root.selectSingleNode("result").getText();
        //log.info(result);
        log.info(root.selectSingleNode("/root/applyType").getText());
    }
    /**
     * 测试DOM4J 查询单个节点
     * @author Flex Hu
     */
    @Test
    public void testSelectSingleNode(){
        StringBuffer xmlString = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\" ?> ");
        xmlString.append("<root><InsertValue>");
        xmlString.append("<ywid>100000001</ywid><ywsy>我的测试</ywsy>");
        xmlString.append("</InsertValue></root>");
        Document document = XmlUtil.stringToDocument(xmlString.toString());
        Element root = document.getRootElement();
        log.info(root.selectSingleNode("//ywsy").getText());
    }
    

    /**
     * 
     * 描述 测试DOM4J 查询单个节点,根据磁盘上文件
     * @author Flex Hu
     * @throws DocumentException 
     * @created 2015年2月9日 上午10:29:41
     */
    @Test
    public void testSelectSingleNodeByFile() throws DocumentException{
        SAXReader reader = new SAXReader(new DocumentFactory());
        Map<String, String> map=new HashMap<String, String>();  
        map.put("jpdl","http://jbpm.org/4.3/jpdl");
        reader.getDocumentFactory().setXPathNamespaceURIs(map);  
        Document document = reader.read(new File("d:/leave.jpdl.xml"));
        String attributeName= "name";
        String attributeValue = "开始";
        //String xpath = "//jpdl:*[normalize-space(@"+attributeName+")='"+attributeValue+"']";
        //String xpath = "//jpdl:*[@name='开始']";
        String xpath = "//jpdl:start[@name='开始']";
        Node node = document.selectSingleNode(xpath);
        log.info(node.getName());
    }
    
    /**
     * 
     * 描述 将字符串转成document,获取节点
     * @author Flex Hu
     * @created 2015年2月9日 下午4:49:01
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testSelectSingleNodeByString() throws DocumentException, UnsupportedEncodingException{
        SAXReader reader = new SAXReader(new DocumentFactory());
        Map<String, String>map=new HashMap<String, String>();  
        map.put("jpdl","http://jbpm.org/4.3/jpdl");
        reader.getDocumentFactory().setXPathNamespaceURIs(map);  
        String xmlString = FileUtil.getContentOfFile("d:/leave.jpdl.xml");
        //Document document = reader.read(new File("d:/leave.jpdl.xml"));
        InputStream  in_withcode=new ByteArrayInputStream(xmlString.getBytes("UTF-8"));  
        Document document = reader.read(in_withcode);
        String attributeName= "name";
        String attributeValue = "开始";
        //String xpath = "//jpdl:*[normalize-space(@"+attributeName+")='"+attributeValue+"']";
        //String xpath = "//jpdl:*[@name='开始']";
        String xpath = "//jpdl:start[@name='开始']";
        Node node = document.selectSingleNode(xpath);
        log.info(node.getName());
        log.info("父亲名称"+node.getParent().getName());
        Element thisNode = (Element) node;
        log.info("name:"+thisNode.attributeValue("name"));
        List<Element> childElements = thisNode.elements();
        log.info("孩子大小是："+childElements.size());
        for(Element element:childElements){
            log.info("element:"+element.getName()+",名称是："+element.attributeValue("to"));
        }
        //String xmlString = FileUtil.getContentOfFile("d:/leave.jpdl.xml");
        //Document document = XmlUtil.stringToDocument(xmlString.toString());
        //List elb2 = root.selectNodes("/root/b[@id='b2']");
        //String xpath = "//process/start/@name";
        //Node node = root.selectSingleNode(xpath);
        //log.info("node:"+node.getName());
        //log.info(root.selectSingleNode("//ywsy").getText());
    }

}
