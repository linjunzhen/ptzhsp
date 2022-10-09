/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hd.dao.ConsultDao;
import net.evecom.platform.hd.service.ConsultService;

/**
 * 描述 业务咨询service
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Service("consultService")
public class ConsultServiceImpl extends BaseServiceImpl implements ConsultService {

    /**
     * 数据访问 dao
     */
    @Resource
    private ConsultDao dao;

    /**
     * 覆盖获取实体dao方法
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_HD_CONSULT U ");
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
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_CONSULT T ");
        sql.append(" WHERE T.CREATE_USER = '" + AppUtil.getLoginMember().get("YHZH") + "'");
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
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_CONSULT T ");
        sql.append(" WHERE T.CREATE_USER = '" + userName + "'");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    
    @Override
    public List<Map<String, Object>> findByItemSqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select U.ITEM_ID,U.ITEM_NAME from T_WSBS_SERVICEITEM U ");
        sql.append(whereSql);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<Map<String, Object>> findByItemSql(String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select U.ITEM_ID,U.ITEM_NAME from T_WSBS_SERVICEITEM U ");
        sql.append(whereSql);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    @Override
    public Map<String, Object> findfrontList(String page, String rows, String itemId) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_CONSULT T ");
        sql.append(" WHERE  T.REPLY_FLAG = 1 ");
        if (StringUtils.isNotEmpty(itemId)) {
            sql.append(" AND T.CONSULT_ITEMID = ? ");
            params.add(itemId);
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    @Override
    public String saveOrUpdateConsult(Map<String, Object> colValues, String tableName, String entityId) {
        // TODO Auto-generated method stub
        return this.saveOrUpdate(colValues, tableName, entityId);
    }

    /**
     * 
     * 描述 获取前台绿色通道的办事咨询列表
     * @author Faker Li
     * @created 2015年12月21日 下午3:26:47
     * @param page
     * @param rows
     * @param busTypeIds
     * @return
     */
    public Map<String, Object> findbsznLstdList(String page, String rows, String busTypeIds) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_CONSULT T ");
        sql.append(" WHERE  T.REPLY_FLAG = 1 ");
        if (StringUtils.isNotEmpty(busTypeIds)) {
            sql.append(" AND T.CONSULT_ITEMID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN "+StringUtil.getValueArray(busTypeIds)+" )");
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    @Override
    public List<Map<String, Object>> findZXBySql(String whereSql) {
        //Map<String, Object> mlist = new HashMap<String, Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_CONSULT T ");
        sql.append(whereSql);
        //sql.append(" WHERE T.CREATE_USER = '" + AppUtil.getLoginMember().get("YHZH") + "'");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }


}
