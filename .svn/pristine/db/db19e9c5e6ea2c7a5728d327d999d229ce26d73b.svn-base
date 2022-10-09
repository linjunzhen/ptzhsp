/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.base.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 表单自定义控件扩展方法
 * @author Flex Hu
 * @version 1.0
 * @created 2015年9月3日 上午8:13:56
 */
@Controller
@RequestMapping("/eveControlController")
public class EveControlController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EveControlController.class);
    /**
     * 
     * 
     * 重新加载下拉框数据
     * @param request
     * @param response
     */
    @RequestMapping("/reloadCheckBox")
    public void reloadCheckBox(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer checkboxHtml = new StringBuffer("<ul>");
        String dataInterface = request.getParameter("dataInterface");
        String dataParams = request.getParameter("dataParams");
        String value = request.getParameter("value");
        String name = request.getParameter("name");
        String clazz = request.getParameter("clazz");
        String beanId = dataInterface.split("[.]")[0];
        String method = dataInterface.split("[.]")[1];
        Object serviceBean = AppUtil.getBean(beanId);
        Set<String> checkValues = new HashSet<String>();
        if(StringUtils.isNotEmpty(value)){
            for(String v:value.split(",")){
                checkValues.add(v);
            }
        }
        if (serviceBean != null) {
            List<Map<String, Object>> datas;
            try {
                Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { String.class });
                datas = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                        new Object[] { dataParams });
                for(Map<String,Object> data:datas){
                    StringBuffer li = new StringBuffer("<li>");
                    //获取value值
                    String dataValue = (String) data.get("VALUE");
                    //获取text值
                    String text = (String) data.get("TEXT");
                    li.append(text).append("<input type=\"checkbox\" ");
                    li.append(" name=\"").append(name).append("\" ");
                    if(StringUtils.isNotEmpty(clazz)){
                        li.append(" class=\"").append(clazz).append("\" ");
                    }
                    li.append(" value=\"").append(dataValue).append("\" ");
                    if(StringUtils.isNotEmpty(value)){
                        if(checkValues.contains(dataValue)){
                            li.append(" checked=\"checked\" ");
                        }
                    }
                    Iterator it = data.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                        if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                            li.append(e.getKey()).append("=\"").append(e.getValue())
                            .append("\" ");
                        }
                    }
                    li.append(">").append("</li>");
                    checkboxHtml.append(li);
                }
                checkboxHtml.append("</ul>");
            } catch (Exception e1) {
                log.error(e1.getMessage());
            } 
        }
        Map<String,String> result = new HashMap<String,String>();
        result.put("CHECKBOXHTML", checkboxHtml.toString());
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 
     * 重新加载下拉框数据
     * @param request
     * @param response
     */
    @RequestMapping("/reloadSelect")
    public void reloadSelect(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer selectContent = new StringBuffer("");
        String dataInterface = request.getParameter("dataInterface");
        String dataParams = request.getParameter("dataParams");
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        String value = request.getParameter("value");
        if(StringUtils.isNotEmpty(defaultEmptyText)){
            selectContent.append("<option value=\"\" >").append(defaultEmptyText).append("</option>");
        }
        String beanId = dataInterface.split("[.]")[0];
        String method = dataInterface.split("[.]")[1];
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean != null) {
            Method invokeMethod;
            try {
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { String.class });
                List<Map<String,Object>> datas = (List<Map<String,Object>>) invokeMethod.invoke(serviceBean,
                        new Object[] { dataParams });
                for(Map<String,Object> data:datas){
                    StringBuffer option = new StringBuffer("<option");
                    //获取value值
                    String dataValue = (String) data.get("VALUE");
                    //获取text值
                    String text = (String) data.get("TEXT");
                    option.append(" value=\"").append(dataValue).append("\" ");
                    if(StringUtils.isNotEmpty(value)&&value.equals(dataValue)){
                        option.append(" selected=\"selected\" ");
                    }
                    Iterator it = data.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                        if(!e.getKey().equals("VALUE")&&!e.getKey().equals("TEXT")){
                            option.append(e.getKey()).append("=\"").append(e.getValue())
                            .append("\" ");
                        }
                    }
                    option.append(">").append(text).append("</option>");
                    selectContent.append(option);
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                log.error(e1.getMessage());
            } 
        }
        Map<String,String> result = new HashMap<String,String>();
        result.put("SELECTCONTENT", selectContent.toString());
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "treeSelector")
    public ModelAndView treeSelector(HttpServletRequest request) {
        return new ModelAndView("common/TreeSelector");
    }
}
