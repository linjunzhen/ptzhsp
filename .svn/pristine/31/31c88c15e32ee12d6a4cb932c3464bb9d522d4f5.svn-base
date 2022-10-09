/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.project.dao.GcjsxfZjkDao;
import net.evecom.platform.project.service.GcjsxfZjkService;

/**
 * 描述：工程建设项目消防验收相关专家库
 * @author Keravon Feng
 * @created 2021年11月16日 上午11:11:03
 */
@Service("gcjsxfZjkService")
public class GcjsxfZjkServiceImpl extends BaseServiceImpl implements GcjsxfZjkService {

    /**
     * dao
     */
    @Resource
    private GcjsxfZjkDao dao;
    
    /**
     * 描述  getEntityDao
     * @author Keravon Feng
     * @created 2021年11月16日 上午11:14:23
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 数据列表findBySqlFilter
     * @author Keravon Feng
     * @created 2021年11月16日 下午2:42:18
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM T_BSFW_GCJSXF_ZJK T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述 删除
     * @author Keravon Feng
     * @created 2021年11月16日 下午3:21:02
     * @param ids
     */
    @Override
    public void multiDel(String[] ids) {
        dao.remove("T_BSFW_GCJSXF_ZJK", "JBXX_ID", ids);
    }
    
    

}
