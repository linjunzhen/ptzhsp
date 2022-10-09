/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.ReadTemplateDao;
import net.evecom.platform.wsbs.service.ReadTemplateService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 阅办模板操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("readTemplateService")
public class ReadTemplateServiceImpl extends BaseServiceImpl implements ReadTemplateService {
    /**
     * 所引入的dao
     */
    @Resource
    private ReadTemplateDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
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
        StringBuffer sql = new StringBuffer("select T.READ_ID,T.READ_NAME FROM T_WSBS_READTEMPLATE T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据readid获取阅办表单
     * @author Faker Li
     * @created 2015年10月19日 下午3:52:04
     * @param readIds
     * @return
     * @see net.evecom.platform.wsbs.service.ReadTemplateService#findByReadId(java.lang.String)
     */
    public List<Map<String, Object>> findByReadId(String readIds) {
        StringBuffer sql = new StringBuffer("select R.READ_ID,R.READ_NAME,R.READ_DOC");
        sql.append(" ,R.LIMIT_STATUS,R.LIMIT_STATUS_NAME,R.LIMIT_NODENAME,R.LIMIT_COUNT　");
        sql.append(" FROM T_WSBS_READTEMPLATE R WHERE R.READ_ID IN　");
        sql.append(StringUtil.getValueArray(readIds));
        sql.append(" ORDER BY R.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/9/29 15:40:00
     * @param
     * @return
     */
    @Override
    public int findLimitCountByReadName(String readName) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(*) SUM FROM T_WSBS_PRINTATTACH ");
        sql.append(" where to_char(to_date(CREATE_TIME,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') ");
        sql.append(" AND FILE_NAME = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{readName}, null);
        return Integer.parseInt(list.get(0).get("SUM").toString());
    }
}
