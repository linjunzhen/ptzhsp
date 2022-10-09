/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import net.evecom.core.web.paging.PagingBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Flex Hu
 * 
 * @version 1.1
 */
public class SqlFilter {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SqlFilter.class);
    /**
     * 属性null
     */
    private HttpServletRequest request = null;
    /**
     * 方法Object>
     * 
     * @param 传入参数
     */
    private Map<String, Object> queryParams = new HashMap<String, Object>();
    /**
     * 方法String>
     * 
     * @param 传入参数
     */
    private Map<String, String> orderParams = new LinkedHashMap<String, String>();
    /**
     * 方法Object>
     * 
     * @param 传入参数
     */
    private Map<String, String> groupParams = new LinkedHashMap<String, String>();
    /**
     * 分页工具类
     */
    private PagingBean pagingBean = null;

    /**
     * 方法getOrderParams
     * 
     * @return 返回值String>
     */
    public Map<String, String> getOrderParams() {
        return orderParams;
    }

    /**
     * 
     * @param orderParams
     */
    public void setOrderParams(Map<String, String> orderParams) {
        this.orderParams = orderParams;
    }

    /**
     * 方法getQueryParams
     * 
     * @return 返回值Object>
     */
    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    /**
     * 
     * @param queryParams
     */
    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    /**
     * 方法getGroupParams
     * 
     * @return 返回值String>
     */
    public Map<String, String> getGroupParams() {
        return groupParams;
    }

    /**
     * 方法setGroupParams
     * @param groupParams
     */
    public void setGroupParams(Map<String, String> groupParams) {
        this.groupParams = groupParams;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月8日 下午2:05:23
     * @param pb
     */
    public SqlFilter(PagingBean pb){
        this.pagingBean = pb;
    }
    
    /**
     * 构造方法
     * @param request
     */
    public SqlFilter(HttpServletRequest request) {
        this.request = request;
        Enumeration paramEnu = request.getParameterNames();
        while (paramEnu.hasMoreElements()) {
            String paramName = (String) paramEnu.nextElement();
            if (paramName.startsWith("Q_")) {
                String paramValue = (String) request.getParameter(paramName);
                if (StringUtil.isNotEmpty(paramValue)) {
                    addFilter(paramName, paramValue.trim());
                }
            }
        }
        // 取得分页的信息
        Integer start = 0;
        Integer limit = PagingBean.DEFAULT_PAGE_SIZE;
        String s_start = request.getParameter("start");
        String s_limit = request.getParameter("limit");
        if (StringUtil.isNotEmpty(request.getParameter("page"))) {
            // 获取当前页
            int currentPage = Integer.parseInt(request.getParameter("page"));
            // 计算开始记录
            // 获取页数限制
            int rows = Integer.parseInt(request.getParameter("rows"));
            int startRecord = (currentPage - 1) * rows;
            s_start = String.valueOf(startRecord);
        }
        if (StringUtil.isNotEmpty(request.getParameter("rows"))) {
            s_limit = request.getParameter("rows");
        }
        if (StringUtil.isNotEmpty(s_start)) {
            start = Integer.valueOf(s_start);
        }
        if (StringUtil.isNotEmpty(s_limit)) {
            limit = Integer.valueOf(s_limit);
        }
        this.pagingBean = new PagingBean(start, limit);
    }
    /**
     * 
     * 描述 添加request中的参数到列表
     * @author Flex Hu
     * @created 2014年9月17日 下午3:18:13
     * @param paramName
     * @param paramValue
     */
    public static void addRequestQueryParam(String paramName,String paramValue,StringBuffer sql,List<Object> params){
        // 获取到字段的名称
        String fieldName = paramName.substring(paramName.indexOf("_") + 1,
                paramName.lastIndexOf("_"));
        // 获取到字段的查询条件
        String condition = paramName.substring(paramName.lastIndexOf("_") + 1,
                paramName.length());
        if(condition.equals("EQ")){
            condition = "=";
        }else if(condition.equals("NEQ")){
            condition = "!=";
        }else if(condition.equals("LT")){
            condition = "<";
        }else if(condition.equals("GT")){
            condition = ">";
        }else if(condition.equals("LE")){
            condition = "<=";
        }else if(condition.equals("GE")){
            condition = ">=";
        }
        sql.append(" and ").append(fieldName);
        if (condition.equals("LIKE")) {
            sql.append(" LIKE ? ");
            params.add("%"+paramValue+"%");
        } else if (condition.equals("LLIKE")) {
            sql.append(" LIKE ? ");
            params.add("%"+paramValue);
        } else if (condition.equals("RLIKE")) {
            sql.append(" LIKE ? ");
            params.add(paramValue+"%");
        } else if(condition.equals("IN")){
            sql.append(" in (");
            String[] newValues = paramValue.split(",");
            for(String newValue:newValues){
                sql.append("?,");
                params.add(newValue);
            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(") ");
        } else if(condition.equals("NOTIN")){
            sql.append(" not in (");
            String[] newValues = paramValue.split(",");
            for(String newValue:newValues){
                sql.append("?,");
                params.add(newValue);
            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(") ");
        }else if(condition.equals("NULL")){
            sql.append(" is null ");
        }else {
            sql.append(" ").append(condition).append("?").append(" ");
            params.add(paramValue);
        }
    }

    /**
     * 
     * @param fieldName
     * @param order
     */
    public void addSorted(String fieldName, String order) {
        orderParams.put(fieldName, order);
    }
    
    /**
     * 
     * 描述 移除filter
     * @author Flex Hu
     * @created 2016年3月8日 下午2:33:02
     * @param paramName
     */
    public void removeFilter(String paramName){
        this.getQueryParams().remove(paramName);
    }

    /**
     * 
     * @param paramName
     * @param paramValue
     */
    public void addFilter(String paramName, String paramValue) {
        String[] fieldInfo = paramName.split("[_]");
        try {
            if (fieldInfo != null && fieldInfo.length > 0) {
                if (StringUtil.isNotEmpty(paramValue)&&paramValue.contains("T00:00:00")) {
                    SimpleDateFormat sdf = null;
                    // 获取操作类型
                    String operateType = paramName.substring(
                            paramName.lastIndexOf("_") + 1, paramName.length());
                    if (operateType.equals("<=")) {
                        paramValue = paramValue.replace("T00:00:00",
                                " 23:59:59");
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        paramValue = DateTimeUtil.getStrOfDate(
                                sdf.parse(paramValue), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        paramValue = paramValue.replace("T", " ");
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        paramValue = DateTimeUtil.getStrOfDate(
                                sdf.parse(paramValue), "yyyy-MM-dd");
                    }
                }
                queryParams.put(paramName, paramValue);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
    
    /**
     * 
     * @param fieldName
     * @param group
     */
    public void addGrouped(String fieldName, String group) {
        groupParams.put(fieldName, group);
    }

    /**
     * 方法getRequest
     * 
     * @return 返回值HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 
     * @param request
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return the pagingBean
     */
    public PagingBean getPagingBean() {
        return pagingBean;
    }

    /**
     * @param pagingBean
     *            the pagingBean to set
     */
    public void setPagingBean(PagingBean pagingBean) {
        this.pagingBean = pagingBean;
    }
}
