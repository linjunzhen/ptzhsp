/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.FlowFormDao;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowFormService;
import net.evecom.platform.hflow.service.FormFieldService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 流程表单操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowFormService")
public class FlowFormServiceImpl extends BaseServiceImpl implements FlowFormService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowFormDao dao;
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 
     */
    @Resource
    private FormFieldService formFieldService;
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
        StringBuffer sql = new StringBuffer("select T.FORM_ID,T.FORM_NAME,T.FORM_KEY,T.FORM_TYPE,T.CREATE_TIME ");
        sql.append(",P.TYPE_NAME,T.FORM_URL from JBPM6_FLOWFORM T LEFT JOIN JBPM6_FLOWTYPE P ON T.TYPE_ID=P.TYPE_ID");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 级联删除掉流程表单
     * @author Flex Hu
     * @created 2015年8月10日 上午10:48:29
     * @param formIds
     */
    public void removeCascade(String[] formIds){
        for(String formId:formIds){
            //先将绑定流程定义的ID制空
            flowDefService.updateBindFormIdToNull(formId);
            //根据表单ID删除掉字段信息
            formFieldService.deleteByFormId(formId);
        }
        dao.remove("JBPM6_FLOWFORM","FORM_ID",formIds);
    }
    
    /**
     * 
     * 描述 更新流程表单信息并且级联更新字段
     * @author Flex Hu
     * @created 2015年8月10日 下午4:13:32
     * @param flowForm
     * @throws Exception 
     */
    public void saveOrUpdateCascadeField(Map<String, Object> flowForm) throws Exception{
        String formId = (String) flowForm.get("FORM_ID");
        if(StringUtils.isEmpty(formId)){
            flowForm.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        formId = dao.saveOrUpdate(flowForm, "JBPM6_FLOWFORM", formId);
        //获取
        String url = this.getUrlByFlowForm(flowForm,null);
        formFieldService.saveOrUpdate(formId, url);
    }
    
    /**
     * 
     * 描述 根据流程表单获取最终的URL
     * @author Flex Hu
     * @created 2015年12月11日 上午10:14:45
     * @param flowForm
     * @return
     */
    public String getUrlByFlowForm(Map<String,Object> flowForm,Map<String,Object> existParams){
        String url = (String) flowForm.get("FORM_URL");
        StringBuffer returnUrl = new StringBuffer(url);
        //获取参数的JSON
        String paramJson = (String) flowForm.get("PARAM_JSON");
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
