/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.command;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.web.paging.PagingBean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:23:01
 */
public class HqlFilter {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(HqlFilter.class);
    /**
     * 
     */
    private HttpServletRequest request = null;
    /**
     * 分页工具类
     */
    private PagingBean pagingBean = null;
    /**
     * 查询参数MAP
     */
    private Map<String, Object> queryParams = new HashMap<String, Object>();
    /**
     * JOIN
     */
    private Map<String, Object> joinParams = new HashMap<String, Object>();
    /**
     * 排序参数MAP
     */
    private Map<String, String> orderParams = new LinkedHashMap<String, String>();

    /**
     * 针对单表的in 操作语句支持（不完美）暂时只能和 queryParams 一起用
     */
    private Map<String, Object[]> inParams = new HashMap<String, Object[]>();

    public Map<String, Object[]> getInParams() {
        return inParams;
    }

    public void setInParams(Map<String, Object[]> inParams) {
        this.inParams = inParams;
    }

    public Map<String, String> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(Map<String, String> orderParams) {
        this.orderParams = orderParams;
    }

    public Map<String, Object> getJoinParams() {
        return joinParams;
    }

    public void setJoinParams(Map<String, Object> joinParams) {
        this.joinParams = joinParams;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public PagingBean getPagingBean() {
        return pagingBean;
    }

    /**
     * 添加in语句的参数
     * 
     * @param key
     * @param value
     */
    public void addInParams(String key, Object[] value) {
        inParams.put(key, value);
    }

    /**
     * 从请求对象获取查询参数,并进行构造
     * <p>
     * 参数名格式必须为: Q_firstName_S_EQ 其中Q_表示该参数为查询的参数，firstName查询的字段名称，
     * S代表该参数的类型为字符串类型,该位置的其他值有：
     * D=日期，BD=BigDecimal，FT=float,N=Integer,SN=Short,S=字符串 EQ代表等于。 该位置的其他值有：<br/>
     * LT，GT，EQ，LE，GE,LK<br/>
     * 要别代表<,>,=,<=,>=,like的条件查询
     * <p>
     * 
     * @param request
     */
    public HqlFilter(HttpServletRequest request) {
        this.request = request;
        Enumeration paramEnu = request.getParameterNames();
        while (paramEnu.hasMoreElements()) {
            String paramName = (String) paramEnu.nextElement();
            if (paramName.startsWith("Q_")) {
                String paramValue = (String) request.getParameter(paramName);
                addFilter(paramName, paramValue);
            }
        }
        // 取得分页的信息
        Integer start = 0;
        Integer limit = PagingBean.DEFAULT_PAGE_SIZE;
        String s_start = request.getParameter("start");
        String s_limit = request.getParameter("limit");
        if (StringUtils.isNotEmpty(request.getParameter("page"))) {
            // 获取当前页
            int currentPage = Integer.parseInt(request.getParameter("page"));
            // 计算开始记录
            // 获取页数限制
            int rows = Integer.parseInt(request.getParameter("rows"));
            int startRecord = (currentPage - 1) * rows;
            s_start = String.valueOf(startRecord);
        }
        if (StringUtils.isNotEmpty(request.getParameter("rows"))) {
            s_limit = request.getParameter("rows");
        }
        if (StringUtils.isNotEmpty(s_start)) {
            start = Integer.valueOf(s_start);
        }
        if (StringUtils.isNotEmpty(s_limit)) {
            limit = Integer.valueOf(s_limit);
        }
        this.pagingBean = new PagingBean(start, limit);
    }

    public void addSorted(String fieldName, String order) {
        orderParams.put(fieldName, order);
    }

    public void addFilter(String paramName, String paramValue) {
        String[] fieldInfo = paramName.split("[_]");
        Object value = null;
        if (fieldInfo != null && fieldInfo.length >= 4) {
            // String nameField = fieldInfo[1];
            // 先判断是否有多对多查询
            String typeField = fieldInfo[3];
            value = convertObject(fieldInfo[2], paramValue, typeField);
            if (value != null) {
                if (typeField.equals("JOIN")) {
                    joinParams.put(paramName, value);
                } else {
                    queryParams.put(paramName, value);
                }
            }

        }
    }

    private Object convertObject(String type, String paramValue,
            String operateType) {
        if (StringUtils.isEmpty(paramValue))
            return null;
        Object value = null;
        try {
            if ("S".equals(type)) {// 大部的查询都是该类型，所以放至在头部
                value = paramValue;
            } else if ("D".equals(type)) {
                if (paramValue.contains("T00:00:00")) {
                    SimpleDateFormat sdf = null;
                    if (operateType.equals("GE")) {
                        paramValue = paramValue.replace("T", " ");
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        value = DateTimeUtil.getStrOfDate(
                                sdf.parse(paramValue), "yyyy-MM-dd");
                    } else if (operateType.equals("LE")) {
                        paramValue = paramValue.replace("T00:00:00",
                                " 23:59:59");
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = DateTimeUtil.getStrOfDate(
                                sdf.parse(paramValue), "yyyy-MM-dd HH:mm:ss");
                    }

                }
                /*
                 * value=DateUtils.parseDate(paramValue,new
                 * String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
                 */
            } else if ("L".equals(type)) {
                value = Long.valueOf(paramValue);
            } else if ("N".equals(type)) {
                value = Integer.valueOf(paramValue);
            } else if ("BD".equals(type)) {
                value = new BigDecimal(paramValue);
            } else if ("FT".equals(type)) {
                value = new Float(paramValue);
            } else if ("SN".equals(type)) {
                value = Short.valueOf(paramValue);
            } else if ("DB".equals(type)) {
                value = new Double(paramValue);
            } else {
                value = paramValue;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return value;
    }

    /**
     * 从请求对象获取查询参数,并进行构造
     * <p>
     * 参数名格式必须为: Q_firstName_S_EQ 其中Q_表示该参数为查询的参数，firstName查询的字段名称，
     * S代表该参数的类型为字符串类型,该位置的其他值有：
     * D=日期，BD=BigDecimal，FT=float,N=Integer,SN=Short,S=字符串 EQ代表等于。 该位置的其他值有：<br/>
     * LT，GT，EQ，LE，GE,LK,NEQ<br/>
     * 要别代表<,>,=,<=,>=,like,!=的条件查询
     * <p>
     * 
     * @param request
     */
    public static String convertOperate(String operate) {
        if (operate.equals("LT")) {
            return "<";
        } else if (operate.equals("GT")) {
            return ">";
        } else if (operate.equals("EQ")) {
            return "=";
        } else if (operate.equals("LE")) {
            return "<=";
        } else if (operate.equals("GE")) {
            return ">=";
        } else if (operate.equals("LK")) {
            return "like";
        } else if (operate.equals("NEQ")) {
            return "!=";
        } else {
            return null;
        }
    }
}
