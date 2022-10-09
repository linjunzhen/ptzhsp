/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bsfw.dao.BdcSurveyDao;
import net.evecom.platform.bsfw.service.BdcSurveyService;

/**
 * 描述 不动产-测绘管理service()
 * @author Allin Lin
 * @created 2020年12月16日 上午11:01:07
 */
@Service("bdcSurveyService")
public class BdcSurveyServiceImpl extends BaseServiceImpl implements BdcSurveyService{
    
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcSurveyServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private BdcSurveyDao dao;

    /**
     * 描述  覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年12月16日 上午11:10:28
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 根据sqlfilter获取测绘管理数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.YW_ID,T.LOCATED,T.SURVEY_USERID,T.SURVEY_USERNAME, ");
        sql.append(" T.SURVEY_STATUS,T.IS_MR,T.SQR,T.EXE_ID,T.CREATE_TIME,T.CREATE_NAME ");
        sql.append(" FROM T_BDC_SURVEY T ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFileterExport(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.LOCATED,T.SURVEY_USERNAME,B.DIC_NAME SURVEY_STATUS,C.DIC_NAME IS_MR, ");
        sql.append(" T.SQR,T.EXE_ID,T.CREATE_NAME,T.CREATE_TIME  FROM T_BDC_SURVEY T ");
        sql.append(" LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY WHERE TYPE_CODE = 'CHZT') B ");
        sql.append(" ON B.DIC_CODE = T.SURVEY_STATUS LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE = 'YESNO') C ON C.DIC_CODE = T.IS_MR WHERE 1 = 1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

}
