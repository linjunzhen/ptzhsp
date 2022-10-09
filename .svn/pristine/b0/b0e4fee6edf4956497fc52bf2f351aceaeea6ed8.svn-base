/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

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
import net.evecom.platform.hflow.dao.FlowTypeDao;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowTypeService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 流程类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowTypeService")
public class FlowTypeServiceImpl extends BaseServiceImpl implements FlowTypeService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowTypeDao dao;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
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
     * 
     * 描述 获取下拉框的值
     * @author Flex Hu
     * @created 2015年8月7日 下午4:07:36
     * @return
     */
    public List<Map<String,Object>> findForSelect(String param){
        StringBuffer sql = new StringBuffer("select T.TYPE_ID AS VALUE,T.TYPE_NAME AS TEXT ");
        sql.append(" FROM JBPM6_FLOWTYPE T ORDER BY T.TREE_SN ASC,T.CREATE_TIME ASC");
        return this.dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * 
     * 描述 根据sqlfilter获取数据列表
     * @author Flex Hu
     * @created 2015年8月11日 上午9:36:52
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TYPE_ID,T.TYPE_NAME,T.PATH FROM JBPM6_FLOWTYPE T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述 获取可被发起的流程定义数据
     * @author Flex Hu
     * @created 2015年8月11日 上午9:33:09
     * @return
     */
    public List<Map<String,Object>> findDefForStart(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN","asc");
        filter.addSorted("T.CREATE_TIME","asc");
        filter.addFilter("Q_T.PARENT_ID_=","0");
        List<Map<String,Object>> types = this.findBySqlFilter(filter);
        for(Map<String,Object> type:types){
            //获取流程定义PATH
            String path = (String) type.get("PATH");
            SqlFilter defFilter = new SqlFilter(request);
            defFilter.addSorted("T.CREATE_TIME", "desc");
            defFilter.addFilter("Q_P.PATH_LIKE", path);
            List<Map<String,Object>> defs = flowDefService.findBySqlFilter(defFilter);
            type.put("defs", defs);
        }
        return types;
    }
    
    /**
     * 
     * 描述 获取类别数据
     * @author Flex Hu
     * @created 2015年8月18日 下午4:10:41
     * @return
     */
    public List<Map<String,Object>> findTypes(String parentId){
        StringBuffer sql = new StringBuffer("SELECT T.TYPE_ID,T.TYPE_NAME,T.PATH FROM ");
        sql.append("JBPM6_FLOWTYPE T WHERE T.PARENT_ID=? ");
        sql.append(" ORDER BY T.TREE_SN ASC");
        return dao.findBySql(sql.toString(), new Object[]{parentId}, null);
    }
    
}
