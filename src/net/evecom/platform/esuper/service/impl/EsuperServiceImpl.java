/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.esuper.dao.EsuperDao;
import net.evecom.platform.esuper.service.EsuperService;
import net.evecom.platform.system.service.DictionaryService;

import org.springframework.stereotype.Service;

/**
 * 描述 电子监察数据解析处理业务service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年1月27日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
@Service("esuperService")
public class EsuperServiceImpl extends BaseServiceImpl implements EsuperService {

    /**
     * @Resource EsuperDao
     */
    @Resource
    private EsuperDao dao;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述 Dao
     * 
     * @author Derek Zhang
     * @created 2016年1月27日 下午3:14:27
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @author Derek Zhang
     * @created 2016年3月2日 下午3:09:52
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.super_id,t.business_id,t.process_name,"
                + "t.warn_type,decode(t.warn_type,");
        sql.append("'2','红灯','1','黄灯','0','未预警',t.warn_type) as warn_typename,t.warn_info,t.warn_reason,");
        sql.append("to_char(to_date(t.reply_limit,'yyyymmdd'),'yyyy-mm-dd') as reply_limit,t.task_id,t.esuper_id,");
        sql.append("t.fb_id,t.fb_status,decode(t.fb_status,0,'未反馈',1,'已反馈') as fb_statusname ");
        sql.append("from t_bsfw_esuper_info t ");        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

}
