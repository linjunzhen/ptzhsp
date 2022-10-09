/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

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
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.wsbs.dao.CommonProblemDao;
import net.evecom.platform.wsbs.service.CommonProblemService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 常见问题操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("commonProblemService")
public class CommonProblemServiceImpl extends BaseServiceImpl implements CommonProblemService {
    /**
     * 所引入的dao
     */
    @Resource
    private CommonProblemDao dao;
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
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.PROBLEM_ID,T.PROBLEM_TITLE,T.LASTER_UPDATETIME");
        sql.append(" FROM T_WSBS_COMMONPROBLEM T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 保存或者更新数据库表,级联更新中间表
     * @author Flex Hu
     * @created 2015年9月18日 下午5:10:05
     * @param commomProblem
     * @return
     */
    public String saveOrUpdateCascadeMiddle(Map<String, Object> commomProblem){
        String entityId = (String) commomProblem.get("PROBLEM_ID");
        String recordId = dao.saveOrUpdate(commomProblem,"T_WSBS_COMMONPROBLEM", entityId);
        //删除掉中间表数据
        dao.remove("T_WSBS_PROBLEM_BUSTYPE", "PROBLEM_ID",new Object[]{recordId});
        //获取选择的主题服务类别
        String typeIds = (String) commomProblem.get("BUS_TYPEIDS");
        dao.saveCommonBusType(recordId, typeIds.split(","));
        return recordId;
    }

    @Override
    public Map<String, Object> findfrontList(String page, String rows, String itemId) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_WSBS_COMMONPROBLEM T ");
        if (StringUtils.isNotEmpty(itemId)) {
            sql.append(" WHERE T.ITEM_ID = ? ");
            params.add(itemId);
        }
        sql.append(" ORDER BY T.LASTER_UPDATETIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    /**
     * 
     * 描述 所有发布事项的常见问题
     * @author Faker Li
     * @created 2015年12月10日 下午2:56:06
     * @param page
     * @param rows
     * @return
     * @see net.evecom.platform.wsbs.service.CommonProblemService#findCjwtList(java.lang.String, java.lang.String)
     */
    public Map<String, Object> findCjwtList(String page, String rows,String problemTitle,String busTypeIds) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT C.PROBLEM_ID, C.PROBLEM_TITLE,C.ANSWER_CONTENT, S.ITEM_NAME ");
        sql.append(" FROM T_WSBS_COMMONPROBLEM C LEFT JOIN T_WSBS_SERVICEITEM S  ");
        sql.append(" ON C.ITEM_ID = S.ITEM_ID WHERE S.FWSXZT = '1' OR C.ITEM_ID = 'bdcwt' ");
        if(!(StringUtils.isEmpty(busTypeIds)||busTypeIds.equals("undefined"))){
            sql.append("AND  S.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN "+StringUtil.getValueArray(busTypeIds)+" )");
        }
        if(StringUtils.isNotEmpty(problemTitle)){
            sql.append(" AND  C.PROBLEM_TITLE LIKE '%"+problemTitle.trim()+"%'");
        }
        sql.append(" ORDER BY C.LASTER_UPDATETIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    /**
     * 
     * 描述 所有发布的表格下载列表
     * @author Faker Li
     * @created 2015年12月17日 上午9:56:45
     * @return
     * @see net.evecom.platform.wsbs.service.CommonProblemService#findAllCjwtList()
     */
    public List<Map<String, Object>> findAllCjwtList() {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT distinct(C.PROBLEM_TITLE) ");
        sql.append(" FROM T_WSBS_COMMONPROBLEM C LEFT JOIN T_WSBS_SERVICEITEM S  ");
        sql.append(" ON C.ITEM_ID = S.ITEM_ID WHERE S.FWSXZT = '1' ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    /**
     * 根据sqlfilter获取到数据列表(历史记录)
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHisBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.PROBLEM_ID,T.PROBLEM_TITLE,T.LASTER_UPDATETIME");
        sql.append(" FROM T_WSBS_ITEM_COMMONPROBLEM_HIS T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
}
