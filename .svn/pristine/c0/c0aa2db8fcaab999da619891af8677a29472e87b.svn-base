/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 系统角色操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SysRoleService extends BaseService {
    
    /**
     * 根据sqlfilter获取到角色数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 级联删除掉角色相关信息
     * @author Flex Hu
     * @created 2014年10月3日 下午6:18:31
     * @param roleIds
     */
    public void removeRoleCascade(String[] roleIds);
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
     * 描述 根据用户ID获取到所拥有的角色列表
     * @author Flex Hu
     * @created 2014年10月11日 上午8:58:55
     * @param userId
     * @return
     */
    public List<Map<String,Object>> findByUserId(String userId);
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
     * 描述 获取所有角色组列表
     * @author Mason Wang
     * @return
     */
    public List<Map<String,Object>> findAllRolesGroup();
    /**
     * 
     * 描述 保存或者更新角色权限
     * @author Flex Hu
     * @created 2014年10月21日 下午3:23:18
     * @param roleId
     * @param resIds
     * @param request
     */
    public void saveOrUpdateRoleRights(String roleId,String[] resIds,HttpServletRequest request);
    /**
     * 
     * 描述 根据角色ID获取被勾选的数据ID
     * @author Flex Hu
     * @created 2014年10月21日 下午3:38:20
     * @param roleId
     * @return
     */
    public String getCheckFjDataId(String roleId);
    /**
     * 
     * 描述  获取网上办事业务
     * @author Danto Huang
     * @created 2015-7-31 上午9:38:38
     * @return
     */
    public List<Map<String, Object>> findBusType();
    /**
     * 
     * 描述 根据角色ID获取授权区划的信息
     * @author Flex Hu
     * @created 2015年9月14日 下午5:46:24
     * @param roleId
     * @return
     */
    public Map<String,Object> getDepInfo(String roleId);
    /**
     * 
     * 描述 根据用户id获取被授权的区划信息
     * @author Flex Hu
     * @created 2015年9月15日 上午9:10:19
     * @param userId
     * @return
     */
    public Map<String,Object> getDepInfoByUserId(String userId);
    /**
     * 
     * 描述  判断登录的人是否分配了对应的角色
     * @author Derek Zhang
     * @created 2015年10月16日 下午2:21:29
     * @param userId  roleCodes 角色Code 多个用逗号分隔
     * @return
     */
    public boolean hasRoleByCode(String userId, String[] roleCodes);
    
    /**
     * 
     * 描述 保存角色数据授权中间表
     * @author Flex Hu
     * @created 2015年9月14日 下午5:09:36
     * @param roleId
     * @param checkDataIds
     */
    public void saveRoleData(String roleId,String[] checkDataIds,String[] checkDataCodes);
    
    /**
     * 
     * 描述 保存角色栏目授权中间表
     * @author Rider Chen
     * @created 2016年9月2日 上午9:21:57
     * @param roleId
     * @param checkModuleIds
     */
    public void saveRoleMoudle(String roleId,String[] checkModuleIds);

    /**
     * 
     * 描述  根据角色ID获取授权栏目ID
     * @author Rider Chen
     * @created 2016年9月2日 上午9:14:57
     * @param roleId
     * @return
     */
    public Map<String,Object> getRoleIdToModuleID(String roleId);
    /**
     * 
     * 描述  保存角色授权操作日志
     * @author Kester Chen
     * @created 2020年5月11日 下午2:40:57
     * @param roleId
     * @param resIds
     * @param request
     */
    public void saveOrUpdateRoleRightsLog(String roleId, String[] resIds, HttpServletRequest request);
}
