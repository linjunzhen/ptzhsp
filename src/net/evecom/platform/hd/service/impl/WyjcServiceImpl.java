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

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hd.dao.WyjcDao;
import net.evecom.platform.hd.service.WyjcService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 我要纠错操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings("rawtypes")
@Service("wyjcService")
public class WyjcServiceImpl extends BaseServiceImpl implements WyjcService {
    /**
     * 所引入的dao
     */
    @Resource
    private WyjcDao dao;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_HD_ERROR T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:查询疑点难点问题列表业务层
     *
     * @author Madison You
     * @created 2019/8/5 8:59:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findQuestionList(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        String questionNum = filter.getRequest().getParameter("questionNum");
        String questionId = filter.getRequest().getParameter("questionId");
        sql.append(" select * from T_HD_QUESTION where 1=1 ");
        if (questionNum != null && questionNum.length() > 0) {
            sql.append(" and QUESTION_CODE = ? ");
            params.add(questionNum);
        }
        if (questionId == null && questionNum.length() == 0) {
            sql.append(" and question_ispublic = 1 ");
        }
        if (questionId != null && questionId.length() > 0) {
            sql.append(" and QUESTION_ID = ? ");
            params.add(questionId);
        }
        sql.append(" order by create_time desc ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
}
