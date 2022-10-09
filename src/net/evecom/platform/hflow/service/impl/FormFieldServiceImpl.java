/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.FormFieldDao;
import net.evecom.platform.hflow.service.FormFieldService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * 描述 表单字段操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("formFieldService")
public class FormFieldServiceImpl extends BaseServiceImpl implements FormFieldService {
    /**
     * 所引入的dao
     */
    @Resource
    private FormFieldDao dao;
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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* ");
        sql.append(" FROM JBPM6_FORMFIELD T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据url生成表单字段
     * @author Flex Hu
     * @created 2015年8月10日 下午4:03:37
     * @param formId
     * @param url
     * @throws IOException 
     */
    public void saveOrUpdate(String formId,String url) throws Exception{
        HttpServletRequest request = AppUtil.getRequest();
        String requestURL = request.getRequestURL().toString();
        String targetIp = requestURL.substring(0, requestURL.lastIndexOf("/"));
        String targetUrl = (targetIp+"/"+url);
        Document detailDoc = Jsoup.connect(targetUrl).get();
        Elements detailCotents = detailDoc.select("*[name]");
        Set<String> fieldNames = new HashSet<String>();
        //先清除掉数据
        dao.deleteByFormId(formId);
        for(Element ele:detailCotents){
            String tagName = ele.tagName();
            String fieldName = ele.attr("name");
            Map<String,Object> formField = new HashMap<String,Object>();
            formField.put("FORM_ID", formId);
            formField.put("FIELD_NAME", fieldName);
            formField.put("FIELD_CONTROLTYPE", tagName);
            formField.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            if(!fieldNames.contains(fieldName)){
                dao.saveOrUpdate(formField,"JBPM6_FORMFIELD",null);
            }
            fieldNames.add(fieldName);
        }
    }
    
    /**
     * 
     * 描述 根据表单ID删除掉字段数据
     * @author Flex Hu
     * @created 2015年8月10日 下午6:56:33
     * @param formId
     */
    public void deleteByFormId(String formId){
        dao.deleteByFormId(formId);
    }
    
    /**
     * 
     * 描述 根据表单ID获取字段列表
     * @author Flex Hu
     * @created 2015年8月12日 上午9:27:29
     * @param formId
     * @return
     */
    public List<Map<String,Object>> findByFormId(String formId){
        StringBuffer sql = new StringBuffer("SELECT F.* FROM JBPM6_FORMFIELD F");
        sql.append(" WHERE F.FORM_ID=? ORDER BY F.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{formId}, null);
    }
}
