/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.cms.dao.ModuleDao;
import net.evecom.platform.cms.service.ModuleService;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 栏目操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("moduleService")
public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {
    /**
     * 所引入的dao
     */
    @Resource
    private ModuleDao dao;
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
    @Override
    public List<Map<String, Object>> findByParentId(String parentId) {
        // TODO Auto-generated method stub
        return dao.findByParentId(parentId);
    }
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from VIEW_ARTICLE_CONTENT T ");
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        if (!"__ALL".equals(resKey)) {// 判断是否超级管理员
            // 获取角色编码集合
            Set<String> roleCodes = sysUser.getRoleCodes();
            String codes = "";
            for (String string : roleCodes) {
                codes +=",'"+string+"'";
            }
            if(StringUtils.isNotEmpty(codes) ){
                codes = codes.substring(1, codes.length());
            }
            sql.append(" WHERE T.MODULE_ID in(select S.MODULE_ID"
                    + " from  T_SYSTEM_MODULE_SYSROLE S WHERE s.role_id in (select r.role_id "
                    + " from t_msjw_system_sysrole r where r.role_code in ("+codes+"))) ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @Override
    public List<Map<String, Object>> findByContentId(String id) {
        // TODO Auto-generated method stub
        return dao.findByContentId(id);
    }
    @Override
    public Map<String, Object> getByJdbcModule(String tableName, String[] colNames, Object[] colValues) {
        // TODO Auto-generated method stub
        return this.getByJdbc(tableName, colNames, colValues);
    }
    @Override
    public void removeModule(String tableName, String colName, Object[] colValues) {
        // TODO Auto-generated method stub
        this.remove(tableName, colName, colValues);
    }
    @Override
    public String saveOrUpdateTreeDataModule(String parentId, Map<String, Object> treeData, String tableName,
            String seqName) {
        // TODO Auto-generated method stub
        return this.saveOrUpdateTreeData(parentId, treeData, tableName, seqName);
    }
    @Override
    public List<Map<String, Object>> findViewBySqlFilter(SqlFilter sqlFilter,String userId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from VIEW_ARTICLE_CONTENT T ");
        if(StringUtils.isNotEmpty(userId)){
            sql.append(" WHERE T.tid in (select distinct A.content_id FROM T_CMS_ARTICLE_AUDIT A WHERE AUDIT_USERID='")
            .append(userId+"') ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    @Override
    public List<Map<String, Object>> findRoleByParentId(String parentId) {
        // TODO Auto-generated method stub
        return dao.findRoleByParentId(parentId);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBzhjsDatagrid(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* ");
        sql.append(" FROM T_CMS_ARTICLE_CONTENT T ");
        sql.append(" LEFT JOIN T_CMS_ARTICLE_MODULE M ");
        sql.append(" ON T.MODULE_ID = M.MODULE_ID ");
        sql.append(" WHERE T.CONTENT_STATUS = '1' ");
        sql.append(" AND M.PATH LIKE '0.663.%' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
    }
}
