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
import net.evecom.core.util.*;
import net.evecom.platform.zzhy.dao.JurisdictionDao;
import net.evecom.platform.zzhy.service.JurisdictionService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 辖区分局管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("jurisdictionService")
public class JurisdictionServiceImpl extends BaseServiceImpl implements JurisdictionService {
    /**
     * 所引入的dao
     */
    @Resource
    private JurisdictionDao dao;
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_COMMERCIAL_JURISDICTION T ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        } 
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<Map<String, Object>> findDatasForSelect(String status) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(
                "select D.XQ_NAME as text,D.XQ_NAME as value,D.XQ_KEYWORD as XQ_KEYWORD FROM")
                        .append(" T_COMMERCIAL_JURISDICTION D WHERE D.STATUS = ? ORDER BY D.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), new Object[] { status }, null);
    }
    /**
     * 获取辖区管理局
     * @param registerAddr
     * @return
     */
    public String getLocalArea(String registerAddr){
        StringBuffer sql=new StringBuffer("SELECT * FROM ");
        sql.append(" T_COMMERCIAL_JURISDICTION T WHERE T.STATUS='1' ");
        List<Map<String,Object>> localAreas=dao.findBySql(sql.toString(),null,null);
        for(Map<String,Object> localArea:localAreas){
            String xqName= StringUtil.getString(localArea.get("XQ_NAME"));
            String xqKeyWords= StringUtil.getString(localArea.get("XQ_KEYWORD"));
            if(StringUtils.isNotEmpty(xqKeyWords)){
                String[] xqKeyWordArray=xqKeyWords.split(",");
                for(String xqKeyWord:xqKeyWordArray){
                    if(registerAddr.indexOf(xqKeyWord)>-1){
                        return xqName;
                    }
                }
            }
        }
        return "";
    }
}
