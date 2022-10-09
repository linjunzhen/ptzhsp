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
import net.evecom.platform.bsfw.dao.DataAbutLogDao;
import net.evecom.platform.bsfw.service.DataAbutLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 对接日志操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("dataAbutLogService")
public class DataAbutLogServiceImpl extends BaseServiceImpl implements DataAbutLogService {
    /**
     * 所引入的dao
     */
    @Resource
    private DataAbutLogDao dao;
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
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.LOG_ID,T.ABUT_CODE,T.ABUT_NAME,T.ABUT_TIME");
        sql.append(",T.ABUT_RESULT,T.ABUT_DESC,T.BUS_IDVALUE FROM T_BSFW_DATAABUTLOG T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 新增或者保存日志
     * @author Flex Hu
     * @created 2015年10月13日 下午3:10:46
     * @param dataAbutLog
     */
    @SuppressWarnings("unchecked")
    public void saveOrUpdateLog(Map<String,Object> dataAbutLog){
        //获取编码
        String abutCode = (String) dataAbutLog.get("ABUT_CODE");
        //获取业务标识值
        String busIdValue = (String) dataAbutLog.get("BUS_IDVALUE");
        String logId = dao.getAbutLogId(abutCode, busIdValue);
        if(StringUtils.isNotEmpty(logId)){
            dao.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG",logId);
        }else{
            dao.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG",null);
        }
    }
}
