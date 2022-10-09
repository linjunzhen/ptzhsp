/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 系统资源操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SysResService extends BaseService {
    /**
     * 
     * 描述 对角色进行授权
     * @author Flex Hu
     * @created 2014年10月3日 下午4:42:10
     * @param roleId
     * @param resIds
     */
    public void saveSysResAndRole(String roleId,String[] resIds);
    /**
     * 
     * 描述 根据角色ID获取到当前被勾选的资源
     * @author Flex Hu
     * @created 2014年10月3日 下午4:52:32
     * @param roleId
     * @return
     */
    public String getGrantResIds(String roleId);
    /**
     * 
     * 描述 根据角色ID删除掉资源和角色中间表数据
     * @author Flex Hu
     * @created 2014年10月3日 下午4:43:14
     * @param roleId
     */
    public void removeSysResRoleByRoleId(String roleId);
    /**
     * 
     * 描述 根据父亲ID获取孩子菜单列表
     * @author Flex Hu
     * @created 2014年12月2日 下午5:03:25
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findSubMenusByParentId(String parentId);
    /**
     * 
     * 描述 根据登录用户判断是否被授权该资源
     * @author Flex Hu
     * @created 2014年12月2日 下午5:11:37
     * @param resKey
     * @param sysUser
     * @return
     */
    public boolean isGranted(String resKey,SysUser sysUser);
    /**
     * 
     * 描述 获取系统
     * @author Faker Li
     * @created 2015年3月6日 上午10:09:43
     * @param parentId
     * @return
     * @see net.evecom.platform.system.service.SysResService#findSubMenusByParentId(java.lang.String)
     */
    public List<Map<String, Object>> findSubMenus(String parentId);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月31日 上午9:57:22
     * @param parentId
     * @param treeData
     * @return
     */
    public String saveOrUpdateRes(String parentId,Map<String,Object> treeData);
    
}
