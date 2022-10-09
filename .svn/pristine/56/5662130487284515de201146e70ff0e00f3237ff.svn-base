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
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.AuditConfigDao;
import net.evecom.platform.hflow.service.AuditConfigService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 审批控件操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("auditConfigService")
public class AuditConfigServiceImpl extends BaseServiceImpl implements AuditConfigService {
    /**
     * 所引入的dao
     */
    @Resource
    private AuditConfigDao dao;
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
        StringBuffer sql = new StringBuffer("select T.* FROM JBPM6_AUDITCONFIG T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 为下拉框提供数据源
     * @author Flex Hu
     * @param dataType(1:审核人,2:任务决策)
     * @created 2015年8月14日 下午4:03:50
     * @return
     */
    public List<Map<String,Object>> findForSelect(String dataType){
        StringBuffer sql = new StringBuffer("select D.CONFIG_NAME as text,D.CONFIG_ID as value FROM")
            .append(" JBPM6_AUDITCONFIG D ");
        if(dataType.equals("1")){
            sql.append(" WHERE D.AUDITER_TYPE!=3");
        }else{
            sql.append(" WHERE D.AUDITER_TYPE=3");
        }
        sql.append(" ORDER BY D.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(),null,null);
    }
}
