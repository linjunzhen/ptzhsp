/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.mobile.dao.PalmCommercialDao;
import net.evecom.platform.mobile.service.PalmCommercialService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述
 * 
 * @author Panda Chen
 * @created 2016-12-13 下午04:11:57
 */
@Service("palmCommercialServiceImpl")
public class PalmCommercialServiceImpl extends BaseServiceImpl implements PalmCommercialService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(PalmCommercialServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    PalmCommercialDao dao;

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:18:00
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:17:49
     * @param page
     * @param rows
     * @param moduleId
     * @return
     */
    public Map<String, Object> findContentListForApp(String page, String rows, String moduleId, String title) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        params.add(moduleId);
        if (StringUtils.isNotEmpty(title)) {
            params.add("%" + title + "%");
        }
        StringBuffer sql = new StringBuffer("SELECT T.CONTENT_ID,T.MODULE_ID,T.CONTENT_TITLE,T.CONTENT_TEXT"
                + ",to_date(T.RELEASE_TIME, 'yyyy-mm-dd hh24:mi:ss') as ITEMRELDATE FROM T_CMS_ARTICLE_CONTENT T ");
        sql.append(" WHERE T.content_status = 1 AND T.content_delete = 0 AND T.module_id in ");
        sql.append(" (select t.module_id from t_cms_article_module t start with t.module_id=? ");
        sql.append(" connect by prior t.module_id = t.parent_id) ");
        if (StringUtils.isNotEmpty(title)) {
            sql.append(" and T.CONTENT_TITLE like ? ");
        }
        sql.append(" ORDER BY T.istop desc,T.RELEASE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;

    }

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2017-3-12 上午11:00:42
     * @param page
     * @param rows
     * @param title
     * @return
     * @see net.evecom.platform.mobile.service.PalmCommercialService#getNegativeList(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public Map<String, Object> getNegativeList(String page, String rows, String title) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        if (StringUtils.isNotEmpty(title)) {
            params.add("%" + title + "%");
        }
        StringBuffer sql = new StringBuffer("select t.* from t_commercial_negativelist t ");
        if (StringUtils.isNotEmpty(title)) {
            sql.append(" where t.NEGATIVELIST_NAME like ? ");
        }
        sql.append(" ORDER BY t.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;

    }

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2017-3-12 上午11:00:38
     * @param page
     * @param rows
     * @param title
     * @return
     * @see net.evecom.platform.mobile.service.PalmCommercialService#getTzIndustryList(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public Map<String, Object> getTzIndustryList(String page, String rows, String title) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        if (StringUtils.isNotEmpty(title)) {
            params.add("%" + title + "%");
        }
        StringBuffer sql = new StringBuffer("select t.* from t_wsbs_industry t where IS_TZINDUSTRY = '1' ");
        if (StringUtils.isNotEmpty(title)) {
            sql.append(" and INDU_NAME like ? ");
        }
        sql.append(" ORDER BY t.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;

    }

    /**
     * 
     * 描述findContentInfoForApp
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:17:56
     * @param ctid
     * @return
     */
    public List<Map<String, Object>> findContentInfoForApp(String ctid) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        params.add(ctid);
        StringBuffer sql = new StringBuffer("select t.filename, t.filepath, t.filetype"
                + ", t.fileid from view_article_fileattach t where t.tid =? ");
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;

    }

    /**
     * 
     * 描述根据sql获取列表
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:56:35
     * @param sql
     * @param queryParams
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> getListBySql(String sql, Object[] queryParams, String page, String rows) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        List<Map<String, Object>> list = dao.findBySql(sql, queryParams, pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    /**
     * 
     * 描述根据sql获取列表
     * 
     * @author Panda Chen
     * @created 2016-12-19 下午04:43:25
     * @param sql
     * @param queryParams
     * @return
     */
    public List<Map<String, Object>> getListBySql(String sql, Object[] queryParams) {
        List<Map<String, Object>> list = dao.findBySql(sql, queryParams, null);
        return list;
    }
}
