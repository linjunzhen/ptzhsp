/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 系统角色操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface SysRoleDao extends BaseDao {
    /**
     * 
     * 描述 获取所有角色列表
     * @author Flex Hu
     * @created 2014年10月8日 下午5:05:08
     * @return
     */
    public List<Map<String,Object>> findAllRoles();
    /**
     * 
     * 描述 获取角色列表
     * @author Flex Hu
     * @created 2014年10月10日 下午2:18:25
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findRoles(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存用户角色中间表
     * @author Flex Hu
     * @created 2014年10月11日 上午8:46:07
     * @param userId
     * @param roleIds
     */
    public void saveRoleUser(String userId,String[] roleIds);
    /**
     * 
     * 描述 保存角色用户中间表
     * @author Flex Hu
     * @created 2014年10月11日 上午9:55:49
     * @param userIds
     * @param roleId
     */
    public void saveRoleUser(String[] userIds,String roleId);
    /**
     * 
     * 描述 根据用户ID获取到所拥有的角色列表
     * @author Flex Hu
     * @created 2014年10月11日 上午8:58:55
     * @param userId
     * @return
     */
    public List<Map<String,Object>> findByUserId(String userId);
    /**
     * 描述 获取所有角色组列表
     * @author Mason Wang
     * @return
     */
    public List<Map<String,Object>> findAllRolesGroup();
    /**
     * 
     * 描述 根据角色ID获取授权部门的信息
     * @author Flex Hu
     * @created 2015年9月14日 下午5:46:24
     * @param roleId
     * @return
     */
    public Map<String,Object> getDepInfo(String roleId);
    /**
     * 
     * 描述 根据用户id获取被授权的部门的信息
     * @author Flex Hu
     * @created 2015年9月15日 上午9:10:19
     * @param userId
     * @return
     */
    public Map<String,Object> getDepInfoByUserId(String userId);
    /**
     * 描述  判断登录的人是否分配了对应的角色
     * @author Derek Zhang
     * @created 2015年10月16日 下午2:21:29
     * @param userId  roleCodes 角色Code 多个用逗号分隔
     * @return
     */
    public boolean hasRoleByCode(String userId, String[] roleCodes);
}
