/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.commercial.dao.ExtraDictionaryDao;
import net.evecom.platform.commercial.service.ExtraDictionaryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 字典信息操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("extraDictionaryService")
public class ExtraDictionaryServiceImpl extends BaseServiceImpl implements ExtraDictionaryService {
    /**
     * logger
     */
    private static Log log = LogFactory.getLog(ExtraDictionaryServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ExtraDictionaryDao dao;
    /**
     * 
     * 覆盖获取实体dao方法
     * @author Danto Huang
     * @created 2021年3月30日 下午3:20:26
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
        StringBuffer sql = new StringBuffer("select D.*,T.TYPE_NAME from T_COMMERCIAL_DIC D ");
        sql.append("LEFT JOIN T_COMMERCIAL_DICTYPE T ON D.TYPE_ID=T.TYPE_ID ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:27:20
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId,String dicCode){
        return dao.isExist(typeId, dicCode);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:29:49
     * @param typeId
     * @return
     */
    public int getMaxSn(String typeId){
        return dao.getMaxSn(typeId);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午4:31:17
     * @param dicIds
     */
    public void updateSn(String[] dicIds){
        dao.updateSn(dicIds);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年4月2日 上午11:21:06
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> getDictonaryByTypeCode(String typeCode){
        String sql = "select * from T_COMMERCIAL_DIC t where t.TYPE_CODE=? order by t.DIC_SN";
        return dao.findBySql(sql, new Object[]{typeCode}, null);
    }
    
    /**
     * 
     * 根据类别编码获取字典数据
     * @author Danto Huang
     * @created 2021年4月7日 上午9:19:46
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String typeCode){
        StringBuffer sql = new StringBuffer("select D.DIC_NAME as text,D.DIC_CODE as value,D.DIC_DESC as dicdesc FROM")
                .append(" T_COMMERCIAL_DIC D WHERE D.TYPE_ID = (SELECT T.TYPE_ID FROM T_COMMERCIAL_DICTYPE T ")
                .append(" WHERE T.TYPE_CODE=? ) ORDER BY D.DIC_SN ASC,D.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }
    
    /**
     * 
     * 描述 根据类别编码和字典编码获取到字典
     * @author Flex Hu
     * @created 2015年3月8日 下午12:05:03
     * @param typeCode
     * @param dicCode
     * @return
     */
    public Map<String,Object> get(String typeCode,String dicCode){
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_DIC D").append(
                " WHERE D.DIC_CODE=? AND D.TYPE_ID IN (SELECT T.TYPE_ID FROM ").append(
                " T_COMMERCIAL_DIC T WHERE T.TYPE_CODE=? )");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), new Object[] { dicCode, typeCode });
        return map;
    }
}
