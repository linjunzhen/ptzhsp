/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.model.SysUser;

import org.springframework.stereotype.Repository;

/**
 * 描述 系统角色操作dao
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("sysRoleDao")
public class SysRoleDaoImpl extends BaseDaoImpl implements SysRoleDao {
    /**
     * 
     * 描述 获取所有角色列表
     * 
     * @author Flex Hu
     * @created 2014年10月8日 下午5:05:08
     * @return
     */
    public List<Map<String, Object>> findAllRoles() {
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_SYSROLE");
        sql.append(" ORDER BY CREATE_TIME DESC ");
        return this.jdbcTemplate.queryForList(sql.toString());
    }

    /**
     * 
     * 描述 获取角色列表
     * 
     * @author Flex Hu
     * @created 2014年10月10日 下午2:18:25
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findRoles(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        sqlFilter.addSorted("R.CREATE_TIME", "DESC");
        StringBuffer sql = new StringBuffer("select R.* FROM T_MSJW_SYSTEM_SYSROLE R ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" WHERE R.ROLE_ID in (");
            sql.append("select SD.ROLE_ID from T_MSJW_SYSTEM_SYSROLE_DATA SD WHERE SD.DEP_CODE");
            sql.append(" in ").append(StringUtil.getValueArray(authDepCodes)).append(")");
        }
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述 保存用户角色中间表
     * 
     * @author Flex Hu
     * @created 2014年10月11日 上午8:46:07
     * @param userId
     * @param roleIds
     */
    public void saveRoleUser(String userId, String[] roleIds) {
        // 先清除掉数据
        StringBuffer delSql = new StringBuffer("delete ").append(" FROM T_MSJW_SYSTEM_SYSUSER_SYSROLE WHERE USER_ID=?");
        this.jdbcTemplate.update(delSql.toString(), new Object[] { userId });
        StringBuffer sql = new StringBuffer("insert into ")
                .append("T_MSJW_SYSTEM_SYSUSER_SYSROLE(USER_ID,ROLE_ID) VALUES(?,?) ");
        for (String roleId : roleIds) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { userId, roleId });
        }
    }

    /**
     * 
     * 描述 保存角色用户中间表
     * 
     * @author Flex Hu
     * @created 2014年10月11日 上午9:55:49
     * @param userIds
     * @param roleId
     */
    public void saveRoleUser(String[] userIds, String roleId) {
        // 先清除掉数据
        StringBuffer delSql = new StringBuffer("delete ").append(" FROM T_MSJW_SYSTEM_SYSUSER_SYSROLE WHERE ROLE_ID=?");
        this.jdbcTemplate.update(delSql.toString(), new Object[] { roleId });
        StringBuffer sql = new StringBuffer("insert into ")
                .append("T_MSJW_SYSTEM_SYSUSER_SYSROLE(USER_ID,ROLE_ID) VALUES(?,?) ");
        for (String userId : userIds) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { userId, roleId });
        }
    }

    /**
     * 
     * 描述 根据用户ID获取到所拥有的角色列表
     * 
     * @author Flex Hu
     * @created 2014年10月11日 上午8:58:55
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findByUserId(String userId) {
        StringBuffer sql = new StringBuffer("select R.* from T_MSJW_SYSTEM_SYSROLE R ")
                .append("WHERE R.ROLE_ID IN (SELECT UR.ROLE_ID  from ")
                .append("T_MSJW_SYSTEM_SYSUSER_SYSROLE UR WHERE UR.USER_ID=?) ").append("ORDER BY R.CREATE_TIME DESC ");
        return this.findBySql(sql.toString(), new Object[] { userId }, null);
    }

    /**
     * 
     * 描述 获取所有角色组列表
     * 
     * @author Mason Wang
     * @return
     */
    public List<Map<String, Object>> findAllRolesGroup() {
        StringBuffer sql = new StringBuffer("select D.DIC_ID,D.DIC_NAME,D.DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY")
                .append(" D WHERE D.TYPE_ID = (SELECT T.TYPE_ID FROM ").append(
                        "T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=? ) ORDER BY D.DIC_SN DESC,D.CREATE_TIME DESC ");
        return this.findBySql(sql.toString(), new Object[] { "RoleGroup" }, null);
    }

    /**
     * 
     * 描述 根据角色ID获取授权部门的信息
     * 
     * @author Flex Hu
     * @created 2015年9月14日 下午5:46:24
     * @param roleId
     * @return
     */
    public Map<String, Object> getDepInfo(String roleId) {
        StringBuffer sql = new StringBuffer("select WMSYS.WM_CONCAT(D.DEP_ID) DEP_IDS,");
        sql.append("WMSYS.WM_CONCAT(D.DEP_CODE) DEP_CODES ");
        sql.append("from T_MSJW_SYSTEM_SYSROLE_DATA D WHERE ");
        sql.append(" D.ROLE_ID=? ");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { roleId });
    }

    /**
     * 
     * 描述 根据用户id获取被授权的区划信息
     * 
     * @author Flex Hu
     * @created 2015年9月15日 上午9:10:19
     * @param userId
     * @return
     */
    public Map<String, Object> getDepInfoByUserId(String userId) {
        StringBuffer sql = new StringBuffer("select  "
                + "RTRIM(XMLAGG(XMLPARSE(CONTENT DEP_ID || ',' WELLFORMED)) .GETCLOBVAL(), ',') as DEP_IDS");
        sql.append(",WMSYS.WM_CONCAT(D.DEP_CODE) DEP_CODES ");
        sql.append("from T_MSJW_SYSTEM_SYSROLE_DATA D WHERE D.ROLE_ID IN(");
        sql.append("select R.ROLE_ID from T_MSJW_SYSTEM_SYSROLE R WHERE R.ROLE_ID IN(");
        sql.append("SELECT SR.ROLE_ID FROM T_MSJW_SYSTEM_SYSUSER_SYSROLE ");
        sql.append("SR WHERE SR.USER_ID=?))");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { userId });
    }

    /**
     * 描述 判断登录的人是否分配了对应的角色
     * 
     * @author Derek Zhang
     * @created 2015年10月16日 下午2:21:29
     * @param userId
     *            roleCodes 角色Code 多个用逗号分隔
     * @return
     */
    public boolean hasRoleByCode(String userId, String[] roleCodes) {
        StringBuffer sql = new StringBuffer("select count(*) ");
        sql.append("from t_msjw_system_sysuser_sysrole res,");
        sql.append("t_msjw_system_sysrole r where r.role_id = res.role_id ");
        sql.append("and user_id=? and role_code in (");
        List<String> list = new ArrayList<String>();
        list.add(userId);
        for (String newValue : roleCodes) {
            sql.append("?,");
            list.add(newValue);
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") ");
        long count = this.jdbcTemplate.queryForLong(sql.toString(), list.toArray());
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
}
