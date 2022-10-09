/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hd.dao.OsTopicDao;
import net.evecom.platform.hd.service.OsTopicService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 网上调查主题操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("osTopicService")
public class OsTopicServiceImpl extends BaseServiceImpl implements OsTopicService {
    /**
     * 所引入的dao
     */
    @Resource
    private OsTopicDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.TOPICID,T.TITLE,T.STARTDATE"
                + ",T.ENDDATE,T.STATE,T.CREATE_TIME from T_HD_OS_TOPIC T ");
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
        StringBuffer sql = new StringBuffer("SELECT T.TOPICID,T.TITLE,T.STARTDATE,T.ENDDATE"
                + ",T.STATE,T.CONTENT,T.RESULTCONTENT,(CASE WHEN STARTDATE > to_char(sysdate,'yyyy-MM-dd')"
                + " THEN 0  WHEN ENDDATE < to_char(sysdate,'yyyy-MM-dd') THEN 2  ELSE 1 END  ) as DATETYPE"
                + " FROM T_HD_OS_TOPIC T ");
        sql.append(" WHERE T.STATE in (1,2)");
        sql.append(" ORDER BY T.STATE ASC, T.STARTDATE DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
}
