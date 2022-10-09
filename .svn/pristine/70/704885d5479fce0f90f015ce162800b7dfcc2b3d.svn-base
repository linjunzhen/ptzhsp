/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

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
import net.evecom.platform.zzhy.dao.NegativeListDao;
import net.evecom.platform.zzhy.service.NegativeListService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 负面清单操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("negativeListService")
public class NegativeListServiceImpl extends BaseServiceImpl implements NegativeListService {
    /**
     * 所引入的dao
     */
    @Resource
    private NegativeListDao dao;

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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, 
            Map<String, Object> variables) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_NEGATIVELIST T WHERE 1=1 ");
        String negativeListCodes = variables.get("negativeListCodes") == null ? "" : variables.get("negativeListCodes")
                .toString();
        if (StringUtils.isNotEmpty(negativeListCodes)) {
            sql.append(" and T.NEGATIVELIST_CODE IN ");
            sql.append(StringUtil.getValueArray(negativeListCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<Map<String, Object>> findByNegetiveListCOde(String codes) {
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_NEGATIVELIST T WHERE 1=1 ");
        sql.append(" and T.NEGATIVELIST_CODE IN 　");
        sql.append(StringUtil.getValueArray(codes));
        sql.append(" ORDER BY T.NEGATIVELIST_CODE DESC ");
        return dao.findBySql(sql.toString(), null, null);
    }
}
