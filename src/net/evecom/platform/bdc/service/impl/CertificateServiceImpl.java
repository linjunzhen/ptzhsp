/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.CertificateDao;
import net.evecom.platform.bdc.service.CertificateService;

/**
 * 
 * 描述 证书模板Service
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月13日 下午5:02:39
 */
@Service("certificateService")
public class CertificateServiceImpl extends BaseServiceImpl implements CertificateService {

    /**
     * 引入dao
     */
    @Resource
    private CertificateDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 根据sqlFilter获取到数据列表
     * 
     * @author Roger Li
     * @created 2019年12月18日 下午4:18:53
     * @param sqlfilter
     * @return List<Map>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.CERTIFICATE_ID,T.CERTIFICATE_NAME FROM T_WSBS_CERTIFICATE T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 根据certificateId获取证书模板信息
     * 
     * @author Roger Li
     * @created 2019年12月18日 下午4:18:53
     * @param certificateIds
     * @return List<Map>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findByCertificateId(String certificateIds) {
        StringBuffer sql = new StringBuffer("select R.CERTIFICATE_ID,R.CERTIFICATE_NAME,R.CERTIFICATE_DOC");
        sql.append(" ,R.LIMIT_STATUS,R.LIMIT_STATUS_NAME,R.LIMIT_NODENAME　");
        sql.append(" FROM T_WSBS_CERTIFICATE R WHERE R.CERTIFICATE_ID IN　");
        sql.append(StringUtil.getValueArray(certificateIds));
        sql.append(" ORDER BY R.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(), null, null);
    }
}
