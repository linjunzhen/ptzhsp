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
import net.evecom.platform.zzhy.dao.FieldAuditDao;
import net.evecom.platform.zzhy.service.FieldAuditService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 商事字段审核操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("fieldAuditService")
public class FieldAuditServiceImpl extends BaseServiceImpl implements FieldAuditService {
    /**
     * 所引入的dao
     */
    @Resource
    private FieldAuditDao dao;
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, Map<String, Object> variables) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_FIELDAUDIT T WHERE 1=1 ");
        String isShow = variables.get("isShow")==null?"0": variables.get("isShow").toString();
        String isShowDelete = variables.get("isShowDelete")==null?"0": variables.get("isShowDelete").toString();
        if(isShow.equals("0")){
            sql.append(" and T.CONFIRM_STATUS = 0 ");
        }
        if(isShowDelete.equals("0")){
            sql.append(" and T.isdelete = 0 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }
}
