/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import net.sf.json.xml.XMLSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jdom.input.SAXBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 描述 XML工具类
 * 
 * @author Flex Hu
 * @version 1.1
 * 
 */
public class XmlUtil {
    /**
     * 日志对象
     */
    private static Log logger = LogFactory.getLog(XmlUtil.class);

    /**
     * 将doucment对象转换成字符串
     * 
     * @author Flex Hu
     * @param document
     * @return
     */
    public static String docToString(Document document) {
        String s = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            OutputFormat format = new OutputFormat("  ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            logger.error("docToString error:" + ex.getMessage());
        }
        return s;
    }

    /**
     * 将字符串转换成document对象
     * 
     * @author Flex Hu
     * @param s
     * @return
     */
    public static Document stringToDocument(String s) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(s);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            // logger.error("stringToDocument error:" + ex.getMessage());
        }
        return doc;
    }

    /**
     * 将document对象转换成XML文件
     * 
     * @author Flex Hu
     * @param document
     * @param filename
     * @return
     */
    public static boolean docToXmlFile(Document document, String filename) {
        boolean flag = true;
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)), format);
            writer.write(document);
            writer.close();
        } catch (Exception ex) {
            flag = false;
            logger.error("docToXmlFile error:" + ex.getMessage());
        }
        return flag;
    }

    /**
     * 将字符串转换成XML文件
     * 
     * @author Flex Hu
     * @param str
     * @param filename
     * @return
     */
    public static boolean stringToXmlFile(String str, String filename) {
        boolean flag = true;
        try {
            Document doc = DocumentHelper.parseText(str);
            flag = docToXmlFile(doc, filename);
        } catch (Exception ex) {
            flag = false;
            logger.error("stringToXmlFile error:" + ex.getMessage());
        }
        return flag;
    }

    /**
     * 加载document对象
     * 
     * @author Flex Hu
     * @param filename
     * @return
     */
    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");
            saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            document = saxReader.read(new File(filename));
        } catch (Exception ex) {
            logger.error("load XML File error:" + ex.getMessage());
        }
        return document;
    }

    /**
     * 加载document对象
     * 
     * @author Flex Hu
     * @param file
     * @return
     */
    public static Document load(File file) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");
            saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            document = saxReader.read(file);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return document;
    }

    /**
     * 加载流返回document
     * 
     * @author Flex Hu
     * @param is
     * @return
     */
    public static Document load(InputStream is) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();

            saxReader.setEncoding("UTF-8");
            document = saxReader.read(is);
        } catch (Exception ex) {
            logger.error("load XML File error:" + ex.getMessage());
        }
        return document;
    }

    /**
     * 加载流返回document
     * 
     * @author Flex Hu
     * @param is
     * @param encode
     * @return
     */
    public static Document load(InputStream is, String encode) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(encode);
            document = saxReader.read(is);
        } catch (Exception ex) {
            logger.error("load XML File error:" + ex.getMessage());
        }
        return document;
    }

    /**
     * 
     * @author Flex Hu
     * @param document
     * @param stylesheet
     * @return
     * @throws Exception
     */
    public static Document styleDocument(Document document, String stylesheet) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);
        Document transformedDoc = result.getDocument();
        return transformedDoc;
    }

    /**
     * 在某个节点下面插入孩子节点元素
     * 
     * @author Flex Hu
     * @param document
     * @param parentElement
     * @param elementTagName
     * @return
     */
    public static Element insertElement(Document document, String parentElementName, String elementTagName) {
        Element root = document.getRootElement();
        Element parentElement = (Element) root.selectSingleNode("//" + parentElementName);
        return parentElement.addElement(elementTagName);
    }

    /**
     * 根据传入的XML和元素标签名称获取到节点列表
     * 
     * @author Flex Hu
     * @param xmlStr
     * @param elementTagName
     * @return
     */
    public static List<Node> findChildNodes(String xmlStr, String elementTagName) {
        Document document = XmlUtil.stringToDocument(xmlStr.toString());
        Element root = document.getRootElement();
        List<Node> list = root.selectNodes("//" + elementTagName);
        return list;
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年1月23日 下午2:49:10
     * @param xmlpath
     * @return
     */
    public static Document loadXMLDoc(String xmlpath, Map<String, String> map) {
        SAXReader reader = new SAXReader(new DocumentFactory());
        reader.getDocumentFactory().setXPathNamespaceURIs(map);
        try {
            return reader.read(new File(xmlpath));
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年1月23日 下午2:50:53
     * @param doc
     * @param realpath
     * @return
     */
    public static boolean writeXMLDoc(Document doc, String realpath) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setNewlines(true);
        XMLWriter writer = null;
        // URL url = XmlUtils.class.getClassLoader().getResource(filename);
        try {
            String filepath = realpath;
            filepath = URLDecoder.decode(filepath, "UTF-8");
            OutputStream out = new FileOutputStream(filepath);
            writer = new XMLWriter(out, format);
            writer.write(doc);
            writer.flush();
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 
     * 描述 XML转换成Map
     * 
     * @author Flex Hu
     * @created 2015年10月16日 下午8:37:22
     * @param xml
     * @return
     */
    public static Map<String, Object> xml2Map(String xml, String encode) {
        String json = xml2Json(xml,encode);
        Map<String, Object> map = JSON.parseObject(json, Map.class);
        return map;
    }

    /**
     * 
     * 描述 转换成JSON
     * 
     * @author Flex Hu
     * @created 2015年10月16日 下午8:34:51
     * @param xml
     * @return
     */
    public static String xml2Json(String xml,String encode) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes(encode));
            SAXBuilder sb = new SAXBuilder();
            org.jdom.Document doc = sb.build(is);
            org.jdom.Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * 描述 将XML转成JSON
     * @author Flex Hu
     * @created 2016年1月26日 下午5:11:55
     * @param xml
     * @return
     */
    public static String xml2Json(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();  
        return xmlSerializer.read(xml).toString(); 
    }

    /**
     * 
     * 描述 迭代元素
     * 
     * @author Flex Hu
     * @created 2015年10月16日 下午8:34:20
     * @param element
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Map iterateElement(org.jdom.Element element) {
        List jiedian = element.getChildren();
        org.jdom.Element et = null;
        Map obj = new HashMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (org.jdom.Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

    /**
     * 
     * 描述 将XML转换成MAP
     * 
     * @author Flex Hu
     * @created 2015年10月16日 下午3:03:31
     * @param element
     * @param data
     * @return
     */
    public static Object xml2Map(Element element, boolean isRoot) {
        Map<String, Object> data = new HashMap<String, Object>();
        // 定义值MAP
        Map<String, Object> valueMap = new HashMap<String, Object>();
        // 获取孩子节点列表
        List<Element> elements = element.elements();
        if (elements.size() == 0) {
            data.put(element.getName(), element.getText());
            if (!isRoot) {
                return element.getText();
            }
        } else if (elements.size() == 1) {
            data.put(elements.get(0).getName(), xml2Map(elements.get(0), false));
        } else {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Element> tempMap = new HashMap<String, Element>();
            for (Element ele : elements) {
                tempMap.put(ele.getName(), ele);
            }
            Set<String> keySet = tempMap.keySet();
            for (String keyName : keySet) {
                Namespace namespace = tempMap.get(keyName).getNamespace();
                List<Element> elements2 = element.elements(new QName(keyName, namespace));
                if (elements2.size() > 1) {
                    List<Object> list = new ArrayList<Object>();
                    for (Element ele : elements2) {
                        list.add(xml2Map(ele, false));
                    }
                    valueMap.put(keyName, list);
                } else {
                    valueMap.put(keyName, xml2Map(elements2.get(0), false));
                }
            }
            data.put(element.getName(), valueMap);
        }
        return data;
    }

    /**
     * 
     * 描述 xml转Map处理XML节占对象核心方法
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 下午4:20:53
     * @param map
     * @param ele
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void ele2Map(Map map, Element ele) {
        List<Element> elements = ele.elements();
        if (elements.size() == 0) {
            map.put(ele.getName(), ele.getText());
        } else if (elements.size() == 1) {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            ele2Map(tempMap, elements.get(0));
            map.put(elements.get(0).getName(), tempMap);
        } else {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            for (Element element : elements) {
                tempMap.put(element.getName(), null);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = elements.get(0).getNamespace();
                List<Element> elements2 = ele.elements(new QName(string, namespace));
                if (elements2.size() > 1) {
                    List<Map> list = new ArrayList<Map>();
                    for (Element element : elements2) {
                        Map<String, Object> tempMap1 = new HashMap<String, Object>();
                        ele2Map(tempMap1, element);
                        list.add(tempMap1);
                    }
                    map.put(string, list);
                } else {
                    if (elements2.get(0).elements().size() == 0) {
                        map.put(string, elements2.get(0).getText());
                    } else {
                        Map<String, Object> tempMap1 = new HashMap<String, Object>();
                        ele2Map(tempMap1, elements2.get(0));
                        map.put(string, tempMap1);
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 xml转为Map对象
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 下午4:20:21
     * @param xmlString
     * @return
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static Object xml2Map(String xmlString) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlString);
        Element rootElement = doc.getRootElement();
        if (rootElement.elements().size() == 0) {
            return rootElement.getText();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> rootMap = new HashMap<String, Object>();
        List<Attribute> attributes = rootElement.attributes();
        String rootName = rootElement.getName();
        // 根节点的属性转为map键值对
        for (Attribute attribute : attributes) {
            rootMap.put(attribute.getName(), attribute.getValue());
        }
        map.put(rootName, rootMap);
        ele2Map(map, rootElement);
        return map;
    }
}