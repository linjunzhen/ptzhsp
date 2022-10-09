/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sso.service.impl;

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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.sso.dao.SingleSignOnDao;
import net.evecom.platform.sso.service.SingleSignOnService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 单点登录操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("singleSignOnService")
public class SingleSignOnServiceImpl extends BaseServiceImpl implements SingleSignOnService {
    /**
     * 所引入的dao
     */
    @Resource
    private SingleSignOnDao dao;

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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_SYSTEM_SINGLESIGNON T ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public void saveLog(Map<String, Object> info, Map<String, Object> param, Map<String, Object> result,
            String logContent, int type) {
        // TODO Auto-generated method stub
        HttpServletRequest request = AppUtil.getRequest();
        String idAddress = BrowserUtils.getIpAddr(request);
        Map<String, Object> logInfo = new HashMap<String, Object>();
        logInfo.put("LOG_PARAM", JSON.toJSONString(param));
        logInfo.put("IP_ADDRESS", idAddress);
        logInfo.put("LOG_RESULT", JSON.toJSONString(result));
        if (null != info && info.size() > 0) {
            logInfo.put("SSO_USERNAME", info.get("SSO_USERNAME"));
            logInfo.put("SSO_DW", info.get("SSO_DW"));
        }
        logInfo.put("LOG_CONTENT", logContent);
        logInfo.put("OPERATE_TYPE", type);
        dao.saveOrUpdate(logInfo, "T_SYSTEM_SINGLESIGNON_LOG", null);
    }
}
