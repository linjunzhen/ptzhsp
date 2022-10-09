/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

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
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.system.dao.SysResDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysResService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 系统资源操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("sysResService")
public class SysResServiceImpl extends BaseServiceImpl implements SysResService {
    /**
     * 所引入的dao
     */
    @Resource
    private SysResDao dao;
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
     * 描述 对角色进行授权
     * @author Flex Hu
     * @created 2014年10月3日 下午4:42:10
     * @param roleId
     * @param resIds
     */
    public void saveSysResAndRole(String roleId, String[] resIds) {
        // 先删除掉中间表数据
        dao.removeSysResRoleByRoleId(roleId);
        StringBuffer sql = new StringBuffer("INSERT INTO T_MSJW_SYSTEM_RES_ROLE(")
                .append("RES_ID,ROLE_ID) VALUES(?,?)");
        for (String resId : resIds) {
            dao.executeSql(sql.toString(), new Object[] { resId, roleId });
        }
    }
    /**
     * 
     * 描述 根据角色ID获取到当前被勾选的资源
     * @author Flex Hu
     * @created 2014年10月3日 下午4:52:32
     * @param roleId
     * @return
     */
    public String getGrantResIds(String roleId){
        return dao.getGrantResIds(roleId);
    }
    /**
     * 
     * 描述 根据角色ID删除掉资源和角色中间表数据
     * @author Flex Hu
     * @created 2014年10月3日 下午4:43:14
     * @param roleId
     */
    public void removeSysResRoleByRoleId(String roleId){
        dao.removeSysResRoleByRoleId(roleId);
    }
    
    /**
     * 
     * 描述 根据父亲ID获取孩子菜单列表
     * @author Flex Hu
     * @created 2014年12月2日 下午5:03:25
     * @param parentId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findSubMenusByParentId(String parentId) {
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_RES R WHERE R.PARENT_ID=? ")
                .append(" AND R.RES_TYPE=? ORDER BY R.TREE_SN ASC,R.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), new Object[] { parentId, 1 }, null);
    }
    
    /**
     * 
     * 描述 根据登录用户判断是否被授权该资源
     * @author Flex Hu
     * @created 2014年12月2日 下午5:11:37
     * @param resKey
     * @param sysUser
     * @return
     */
    public boolean isGranted(String resKey,SysUser sysUser){
        String sysUserResKey = sysUser.getResKeys();
        if(sysUserResKey.equals(SysUser.ADMIN_RESKEY)){
            return true;
        }else{
            Set<String> sysUserResSet = sysUser.getResKeySet();
            if(sysUserResSet.contains(resKey)){
                return true;
            }else{
                return false;
            }
        }
    }
    /**
     * 
     * 描述 获取系统
     * @author Faker Li
     * @created 2015年3月6日 上午10:09:43
     * @param parentId
     * @return
     * @see net.evecom.platform.system.service.SysResService#findSubMenusByParentId(java.lang.String)
     */
    public List<Map<String, Object>> findSubMenus(String parentId) {
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_RES R WHERE R.PARENT_ID=? ")
                .append("AND R.RES_TYPE=? ORDER BY R.TREE_SN ASC,R.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), new Object[] { parentId, 1 }, null);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月31日 上午9:57:22
     * @param parentId
     * @param treeData
     * @return
     */
    public String saveOrUpdateRes(String parentId,Map<String,Object> treeData){
        String recordId = this.saveOrUpdateTreeData(parentId, treeData,"T_MSJW_SYSTEM_RES",null);
        //获取是否公共菜单
        String isPublic = (String) treeData.get("IS_PUBLIC");
        if(StringUtils.isNotEmpty(isPublic)&&isPublic.equals("1")){
            StringBuffer sql = new StringBuffer("INSERT INTO T_MSJW_SYSTEM_RES_ROLE(RES_ID,ROLE_ID)");
            sql.append(" SELECT '").append(recordId).append("',R.ROLE_ID FROM T_MSJW_SYSTEM_SYSROLE R ");
            sql.append("WHERE R.ROLE_ID NOT IN (SELECT RR.ROLE_ID FROM T_MSJW_SYSTEM_RES_ROLE RR");
            sql.append(" WHERE RR.RES_ID=? )");
            dao.executeSql(sql.toString(), new Object[]{recordId});
        }
        return recordId;
    }
}
