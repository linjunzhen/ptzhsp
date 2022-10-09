/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.service.impl;

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
import net.evecom.platform.flowconfig.dao.FlowConfigTypeDao;
import net.evecom.platform.flowconfig.service.FlowConfigTypeService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 集成流程类型配置操作service
 * @author Neil Yu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowConfigTypeService")
public class FlowConfigTypeServiceImpl extends BaseServiceImpl implements FlowConfigTypeService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowConfigTypeDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Neil Yu
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
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select *  from T_FLOW_CONFIG_TYPE T where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据类别编码获取类别数据
     * @author Flex Hu
     * @created 2014年10月22日 上午9:20:31
     * @param typeCode
     * @return
     */
    @Override
    public List<Map<String,Object>> findForSelect(String value){
        
        StringBuffer sql = new StringBuffer("select  T.TYPE_NAME as text,T.TYPE_ID"
                + " as value,T.SPLCLX as dicdesc FROM ").append(
                "T_FLOW_CONFIG_TYPE T ORDER BY T.SPLCLX ASC ");
        return dao.findBySql(sql.toString(), new Object[] {}, null);
    }
}


