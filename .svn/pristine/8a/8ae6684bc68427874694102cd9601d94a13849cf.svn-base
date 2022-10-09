/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyctb.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.callyctb.dao.CallYctbDao;
import net.evecom.platform.callyctb.service.CallYctbService;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 一窗通办操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("callYctbService")
public class CallYctbServiceImpl extends BaseServiceImpl implements CallYctbService {
    /**
     * 所引入的dao
     */
    @Resource
    private CallYctbDao dao;

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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.*,B.BUSINESS_NAME,D.TYPE_NAME from T_CKBS_YCTBQH_BUS T ");
        sql.append(" LEFT JOIN T_CKBS_YCTBQH D ON T.YCTBQH_ID = D.YCTBQH_ID  ");
        sql.append(" LEFT JOIN T_CKBS_BUSINESSDATA B ON T.BUSINESS_CODE = B.BUSINESS_CODE");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public Map<String, Object> getBusinessCode(String yctbqhId) {
        // TODO Auto-generated method stub
        return dao.getBusinessCode(yctbqhId);
    }
}
