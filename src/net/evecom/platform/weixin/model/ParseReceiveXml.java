/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class ParseReceiveXml {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ParseReceiveXml.class);
    /**
     * 解析微信xml消息
     * @param strXml
     * @return
     */
    public ReceiveXmlEntity getMsgEntity(String strXml){
        ReceiveXmlEntity msg = null;
        try {
            if (strXml.length() <= 0 || strXml == null)
                return null;
             
            // 将字符串转化为XML文档对象
            Document document = DocumentHelper.parseText(strXml);
            // 获得文档的根节点
            Element root = document.getRootElement();
            // 遍历根节点下所有子节点
            Iterator<?> iter = root.elementIterator();
            
            // 遍历所有结点
            msg = new ReceiveXmlEntity();
            //利用反射机制，调用set方法
            //获取该实体的元类型
            Class<?> c = Class.forName("com.basicwechat.entity.ReceiveXmlEntity");
            msg = (ReceiveXmlEntity)c.newInstance();//创建这个实体的对象
            
            while(iter.hasNext()){
                Element ele = (Element)iter.next();
                //获取set方法中的参数字段（实体类的属性）
                Field field = c.getDeclaredField(ele.getName());
                //获取set方法，field.getType())获取它的参数数据类型
                Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
                //调用set方法
                method.invoke(msg, ele.getText());
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("xml 格式异常: "+ strXml);
            log.error(e.getMessage());
        }
        return msg;
    }
}
