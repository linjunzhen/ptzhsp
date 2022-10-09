/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.commercial.dao.IndustryDao;
import net.evecom.platform.commercial.service.IndustryService;

/**
 * 描述   商事登记-行业管理service
 * @author Allin Lin
 * @created 2020年11月18日 上午11:03:32
 */
@Service("industryService")
public class IndustryServiceImpl extends BaseServiceImpl implements IndustryService{

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(IndustryServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private IndustryDao dao;

    /**
     * 覆盖获取实体dao方法
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 根据sqlfilter获取到数据列表(主行业列表)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*");
        sql.append(" FROM T_COMMERCIAL_INDUSTRY T ");
        sql.append(" WHERE T.PARENT_ID = '0' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    获取指定主行业ID下的子行业信息
     * @author Allin Lin
     * @created 2020年11月18日 下午2:45:40
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findChildIndustryBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*");
        sql.append(" FROM T_COMMERCIAL_INDUSTRY T ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     *
     * 描述    获取指定主行业信息
     * @author Allin Lin
     * @created 2020年11月18日 下午2:45:40
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBussinessBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.ID,");
        sql.append("t.BUSSCOPE_CODE,t.BUSSCOPE_NAME,t.INDU_CODE,t.INDU_NAME,t.CHILD_INDUSTRYIDS");
        sql.append(",t.CHILD_INDUSTRYNAMES");
        sql.append(",i1.indu_name as ml_name ");
        sql.append(",i2.indu_name as dl_name ");
        sql.append(" ,i3.indu_name as zl_name");
        sql.append(",i4.indu_name as xl_name ");
        sql.append(" from T_WSBS_BUSSCOPE t  ");
        sql.append("left join t_wsbs_industry2020 i1 on t.ml_name=i1.indu_code  ");
        sql.append("left join t_wsbs_industry2020 i2 on t.dl_name=i2.indu_code  ");
        sql.append("left join t_wsbs_industry2020 i3 on t.zl_name=i3.indu_code   ");
        sql.append("left join t_wsbs_industry2020 i4 on t.xl_name=i4.indu_code  ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    级联删除主行业相关信息
     * @author Allin Lin
     * @created 2020年11月18日 下午1:03:34
     * @param roleIds
     */
    public void removeIndustryCascade(String[] industryIds){
        this.remove("T_COMMERCIAL_INDUSTRY", "INDUSTRY_ID", industryIds);
        this.remove("T_COMMERCIAL_INDUSTRY", "PARENT_ID", industryIds);//相关子行业信息
    }
}
