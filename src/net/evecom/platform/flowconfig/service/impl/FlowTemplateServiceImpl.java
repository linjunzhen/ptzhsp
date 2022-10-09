/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.service.impl;

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
import net.evecom.platform.flowconfig.dao.FlowTemplateDao;
import net.evecom.platform.flowconfig.service.FlowTemplateService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 工程建设项目流程模版配置操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowTemplateService")
public class FlowTemplateServiceImpl extends BaseServiceImpl implements FlowTemplateService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowTemplateDao dao;   
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findStageBySqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select *  from T_FLOW_CONFIG_STAGE T left join T_WSBS_SERVICEITEM f ");
        sql.append(" on f.item_id=t.item_id where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findItemBySqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.ID,T.STAGE_ID,T.ITEM_ID,T.SORT,T.CREATE_TIME ");
        sql.append(",F.ITEM_NAME,F.ITEM_CODE,F.BDLCDYID,T.IS_KEY_ITEM,T.IS_SITE_OPTIONAL ");
        sql.append(" from T_FLOW_CONFIG_ITEM T");
        sql.append(" left join T_WSBS_SERVICEITEM F on F.item_id=T.item_id   ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        for (Map<String, Object> map : list) {
            Map<String, Object> userinfo = serviceItemService.getBindUserIdANdNames(map.get("ITEM_ID").toString());
            map.put("USER_NAME", userinfo.get("FULL_NAMES"));
            map.put("USER_ID", userinfo.get("USER_IDS"));
        }
        return list;
    }
}
