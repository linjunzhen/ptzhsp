/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.DocumentTemplateDao;
import net.evecom.platform.wsbs.service.DocumentTemplateService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 公文配置操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("documentTemplateService")
public class DocumentTemplateServiceImpl extends BaseServiceImpl implements DocumentTemplateService {
    /**
     * 所引入的dao
     */
    @Resource
    private DocumentTemplateDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
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
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.DOCUMENT_ID,T.DOCUMENT_NAME,");
        sql.append("S.SERIAL_NAME from T_WSBS_DOCUMENTTEMPLATE T ");
        sql.append("LEFT JOIN t_wsbs_serialnumber S ON S.SERIAL_ID=T.SERIAL_ID");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述  根据documentId获取公文模板
     * @author Faker Li
     * @created 2015年10月19日 下午2:43:04
     * @param documentIds
     * @return
     * @see net.evecom.platform.wsbs.service.DocumentTemplateService#findByDocumentIds(java.lang.String)
     */
    public List<Map<String, Object>> findByDocumentIds(String documentIds) {
        StringBuffer sql = new StringBuffer("select U.DOCUMENT_ID,U.DOCUMENT_NAME,U.DOCUMENT_DOC");
        sql.append(" FROM T_WSBS_DOCUMENTTEMPLATE U WHERE U.DOCUMENT_ID IN　");
        sql.append(StringUtil.getValueArray(documentIds));
        sql.append(" ORDER BY U.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月10日 下午5:30:46
     * @param documentTemplate
     * @param variables
     * @return
     */
    @Override
    public String getUrlByDocumentTemplate(
            Map<String, Object> documentTemplate, Map<String, Object> existParams) {
        String url = (String) documentTemplate.get("HTML_URL");
        StringBuffer returnUrl = new StringBuffer(url);
        //获取参数的JSON
        String paramJson = (String) documentTemplate.get("PARAM_JSON");
        if(StringUtils.isNotEmpty(paramJson)){
            List paramList = JSON.parseArray(paramJson, Map.class);
            if(url.indexOf("?")!=-1){
                returnUrl.append("&");
            }else{
                returnUrl.append("?");
            }
            for(int i = 0;i<paramList.size();i++){
                Map<String,Object> param = (Map<String, Object>) paramList.get(i);
                //获取参数名称
                String paramName = (String) param.get("PARAM_NAME");
                //获取缺省值
                String paramValue = (String) param.get("PARAM_VALUE");
                if(existParams!=null&&existParams.get(paramName)!=null){
                    paramValue = (String) existParams.get(paramName);
                }
                //获取参数值
                if(i>0){
                    returnUrl.append("&");
                }
                returnUrl.append(paramName).append("=").append(paramValue);
            }
        }
        return returnUrl.toString();
    }
}
