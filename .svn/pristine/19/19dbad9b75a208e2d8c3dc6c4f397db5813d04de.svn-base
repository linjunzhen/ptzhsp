/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hd.dao.ComplainDao;
import net.evecom.platform.hd.service.ComplainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 投诉监督操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl implements ComplainService {
    /**
     * 所引入的dao
     */
    @Resource
    private ComplainDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_HD_COMPLAIN U ");
        sql.append(whereSql);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 问题列表datagrid
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findQuestionBySqlFilter(SqlFilter sqlFilter,String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_HD_QUESTION U ");
        sql.append(whereSql);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @Override
    public Map<String, Object> findfrontList(String page, String rows) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_COMPLAIN T ");
        sql.append(" WHERE T.CREATE_USERID = '" + AppUtil.getLoginMember().get("USER_ID") + "'");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    @Override
    public Map<String, Object> findfrontAppList(String page, String rows,
            String userName) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_COMPLAIN T ");
        sql.append(" WHERE T.CREATE_USERID = '" + userName + "'");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

}
