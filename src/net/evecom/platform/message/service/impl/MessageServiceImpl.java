/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.message.service.impl;

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
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.message.dao.MessageDao;
import net.evecom.platform.message.service.MessageService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 消息提醒操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl implements MessageService {
    /**
     * 所引入的dao
     */
    @Resource
    private MessageDao dao;
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
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,
            String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.MESSAGE_ID,T.MESSAGE_TITLE,T.MESSAGE_CONTENT" +
                ",T.MESSAGE_STATUS,T.CREATE_TIME,T.RELEASE_TIME from T_APP_MESSAGE T ");
        sql.append(whereSql);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findMessageAppBySql(String page, String rows,
            String esn) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        params.add(esn);
        params.add(esn);
        StringBuffer sql = new StringBuffer("select T.MESSAGE_ID, T.MESSAGE_TITLE, T.MESSAGE_CONTENT, T.MESSAGE_STATUS"
                + ", to_date(T.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as CREATETIME ");
        sql.append(",(CASE (SELECT count(T1.MESSAGE_ID) FROM T_APP_MESSAGE_ESN T1 "
                + "WHERE T1.ESN=? AND T1.MESSAGE_ID=T.MESSAGE_ID AND T1.STATUS =1)  "
                + "WHEN 1 THEN  1  ELSE  0 END) AS STATUS ");
        sql.append(" from T_APP_MESSAGE T ");
        sql.append(" WHERE T.MESSAGE_STATUS = 1 ");
        if (StringUtils.isNotEmpty(esn)) {
            sql.append("  and T.MESSAGE_ID NOT IN(SELECT T1.MESSAGE_ID FROM T_APP_MESSAGE_ESN T1 "
                    + "WHERE T1.ESN=? AND T1.STATUS = 0 ) ");
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCount(String esn) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select COUNT(T.MESSAGE_ID) as COUNT from T_APP_MESSAGE T ");
        sql.append(" WHERE T.MESSAGE_STATUS = 1 ");
        if(StringUtils.isNotEmpty(esn)){
            sql.append("  and T.MESSAGE_ID NOT IN(SELECT T1.MESSAGE_ID FROM T_APP_MESSAGE_ESN T1 WHERE T1.ESN=? ) ");
        }
        Map<String, Object> map = dao.getByJdbc(sql.toString(), new Object[]{esn});
        return map;
    }
}
