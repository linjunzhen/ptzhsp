/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

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
import net.evecom.platform.bsfw.dao.DataAbutmentDao;
import net.evecom.platform.bsfw.service.DataAbutmentService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 对接配置操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("dataAbutmentService")
public class DataAbutmentServiceImpl extends BaseServiceImpl implements DataAbutmentService {
    /**
     * 所引入的dao
     */
    @Resource
    private DataAbutmentDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
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
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.AABUT_ID,T.AABUT_CODE,T.AABUT_NAME");
        sql.append(",T.AABUT_TYPE,T.AABUT_INTERFACE,T.INVOKE_TYPE,T.NETWORK_TYPE");
        sql.append("  FROM T_BSFW_DATAABUTMENT T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据接口调用方式获取列表数据(aabut_code =0019 停用)
     * @author Flex Hu
     * @created 2015年10月12日 下午5:18:09
     * @param typeValue
     * @return
     */
    public List<Map<String,Object>> findByInvokeType(String typeValue,String networkType){
        StringBuffer sql = new StringBuffer("select T.* from T_BSFW_DATAABUTMENT");
        sql.append(" T WHERE T.INVOKE_TYPE=? AND T.NETWORK_TYPE=? and t.aabut_code not in ('0019') ORDER BY T.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(), new Object[]{typeValue,networkType},null);
    }
    
}
