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
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午3:28:22
 */
public interface SysUserDao extends BaseDao {
    /**
     * 
     * 描述 根据帐号密码判断是否存在记录
     * @author Flex Hu
     * @created 2014年9月11日 下午3:51:37
     * @param username
     * @param password
     * @return 1:说明激活状态用户存在 0:用户名或者密码错误 -1:用户被禁用
     */
    public int isExistsUser(String username,String password);
    /**
     * 
     * 描述 根据id判断是否存在记录
     * @author Water Guo
     * @created 2016年12月28日 下午3:51:37
     * @param userId
     * @return 1:说明激活状态用户存在 0:不存在 
     */
    public int isExistsUserById(String userId);
    /**
     * 
     * 描述 这个接口将会获取用户所相关的一切必要信息，如部门,权限等
     * @author Flex Hu
     * @created 2014年9月11日 下午4:29:04
     * @param username
     * @return
     */
    public SysUser getAllInfoByUsername(String username);
    /**
     * 
     * 描述 更新系统用户状态
     * @author Flex Hu
     * @created 2014年10月4日 下午4:50:21
     * @param userIds
     * @param status
     */
    public void updateUserStatus(String userIds,int status);
    /**
     * 
     * 描述 将密码进行重置
     * @author Flex Hu
     * @created 2014年10月4日 下午5:36:33
     * @param userIds
     */
    public void updatePassword(String userIds);
    /**
     * 
     * 描述 将密码进行重置
     * @author Flex Hu
     * @created 2014年10月4日 下午5:36:33
     * @param userIds
     */
    public void updatePassword(String userIds,String password);
    /**
     * 
     * 描述 获取
     * @author Flex Hu
     * @created 2014年10月8日 下午5:38:20
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findUsers(SqlFilter sqlFilter);
    /**
     * 
     * 描述  加入或者移除用户
     * @author Flex Hu
     * @created 2014年10月10日 上午10:29:59
     * @param departId
     * @param userIds
     * @param addOrRemove
     */
    public void updateDepId(String departId,String userIds,boolean isAdd);
    /**
     * 
     * 根据角色ID获取系统用户列表信息，只包含用户ID和姓名
     * @author Flex Hu
     * @created 2014年10月11日 上午9:28:23
     * @param roleId
     * @return
     */
    public List<Map<String,Object>> findUsersByRoleId(String roleId);
    /**
     * 根据用户组ID获取用户列表信息,只包含用户ID和姓名
     * 描述
     * @author Flex Hu
     * @created 2014年10月11日 下午5:30:06
     * @param groupId
     * @return
     */
    public List<Map<String,Object>> findUsersByGroupId(String groupId);
    /**
     * 
     * 描述 根据部门ID获取用户列表
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String,Object>> findUsersByDepId(String depId);
    /**
     * 
     * 描述 根据部门ID获取用户IDS和用户NAMES
     * @author Flex Hu
     * @created 2015年9月14日 上午11:42:01
     * @param depId
     * @return
     */
    public Map<String,Object> getUserIdAndNames(String depId);
}
