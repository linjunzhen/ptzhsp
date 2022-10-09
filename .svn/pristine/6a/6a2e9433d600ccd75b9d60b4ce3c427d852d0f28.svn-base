/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.SysResService;
import net.evecom.platform.system.service.SysRoleService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 系统角色操作service
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {
    /**
     * 所引入的dao
     */
    @Resource
    private SysRoleDao dao;
    /**
     * sysResService
     */
    @Resource
    private SysResService sysResService;
    /**
     * 引入Service
     */
    @Resource
    private SysRoleService sysRoleService;
    /**
     * dicTypeService
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 覆盖获取实体dao方法 描述
     * 
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
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.ROLE_ID,T.ROLE_NAME,T.ROLE_CODE,T.CREATE_TIME");
        sql.append(" FROM T_MSJW_SYSTEM_SYSROLE T ");
        //获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        //非超管进行数据级别权限控制
        if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            //获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" WHERE T.ROLE_ID in (");
            sql.append("select SD.ROLE_ID from T_MSJW_SYSTEM_SYSROLE_DATA SD WHERE SD.DEP_CODE");
            sql.append(" in ").append(StringUtil.getValueArray(authDepCodes)).append(")");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 级联删除掉角色相关信息
     * 
     * @author Flex Hu
     * @created 2014年10月3日 下午6:18:31
     * @param roleIds
     */
    public void removeRoleCascade(String[] roleIds) {
        for (String roleId : roleIds) {
            sysResService.removeSysResRoleByRoleId(roleId);
        }
        this.remove("T_MSJW_SYSTEM_ROLEBUSREL", "ROLE_ID", roleIds);
        this.remove("T_MSJW_SYSTEM_SYSUSER_SYSROLE", "ROLE_ID", roleIds);
        this.remove("T_MSJW_SYSTEM_SYSROLE", "ROLE_ID", roleIds);
    }

    /**
     * 
     * 描述 获取所有角色列表
     * 
     * @author Flex Hu
     * @created 2014年10月8日 下午5:05:08
     * @return
     */
    public List<Map<String, Object>> findAllRoles() {
        return dao.findAllRoles();
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
    public List<Map<String, Object>> findRoles(SqlFilter sqlFilter) {
        return dao.findRoles(sqlFilter);
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
        dao.saveRoleUser(userId, roleIds);
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
        return dao.findByUserId(userId);
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
        dao.saveRoleUser(userIds, roleId);
    }

    /**
     * 描述 获取所有角色组列表
     * 
     * @author Mason Wang
     * @return
     */
    public List<Map<String, Object>> findAllRolesGroup() {
        return dao.findAllRolesGroup();
    }

    /**
     * 
     * 描述 保存或者更新角色权限
     * 
     * @author Flex Hu
     * @created 2014年10月21日 下午3:23:18
     * @param roleId
     * @param resIds
     * @param request
     */
    @SuppressWarnings("unchecked")
    public void saveOrUpdateRoleRights(String roleId, String[] resIds, HttpServletRequest request) {
        this.sysResService.saveSysResAndRole(roleId, resIds);
        String[] checkDataIds = request.getParameterValues("checkDataIds[]");
        String[] checkDataCodes = request.getParameterValues("checkDataCodes[]");
        this.saveRoleData(roleId, checkDataIds, checkDataCodes);
    }
    
    /**
     * 
     * 描述 保存角色数据授权中间表
     * @author Flex Hu
     * @created 2015年9月14日 下午5:09:36
     * @param roleId
     * @param checkDataIds
     */
    public void saveRoleData(String roleId,String[] checkDataIds,String[] checkDataCodes){
        //先清除掉中间表数据
        this.remove("T_MSJW_SYSTEM_SYSROLE_DATA",new String[]{"ROLE_ID"},new Object[]{roleId});
        StringBuffer sql = new StringBuffer("insert into T_MSJW_SYSTEM_SYSROLE_DATA");
        sql.append("(ROLE_ID,DEP_ID,DEP_CODE) values(?,?,?) ");
        for(int i = 0;i<checkDataIds.length;i++){
            dao.executeSql(sql.toString(), new Object[]{roleId,checkDataIds[i],checkDataCodes[i]});
        }
    }
    
    /**
     * 
     * 描述 根据角色ID获取被勾选的数据ID
     * @author Flex Hu
     * @created 2014年10月21日 下午3:38:20
     * @param roleId
     * @return
     */
    public String getCheckFjDataId(String roleId){
        Map<String,Object> role = dao.getByJdbc("T_MSJW_SYSTEM_SYSROLE",new String[]{"ROLE_ID"},
                new Object[]{roleId});
        String provinceCode= (String) role.get("PROVINCE_CODE");
        String cityCode= (String) role.get("CITY_CODE");
        String areaCode= (String) role.get("AREA_CODE");
        Map<String,Object> dicType = null;
        if(StringUtils.isNotEmpty(areaCode)){
            dicType = dicTypeService.getByTypeCode(areaCode);
        }else if(StringUtils.isNotEmpty(cityCode)){
            dicType = dicTypeService.getByTypeCode(cityCode);
        }else if(StringUtils.isNotEmpty(provinceCode)){
            dicType = dicTypeService.getByTypeCode(provinceCode);
        }
        if(dicType!=null){
            return (String) dicType.get("TYPE_ID");
        }else{
            return "";
        }
    }
    

    /**
     * 
     * 描述  获取网上办事业务
     * @author Danto Huang
     * @created 2015-7-31 上午9:38:38
     * @return
     */
    public List<Map<String, Object>> findBusType(){
        String sql = "select t.typename,t.categoryid from T_BUSINESS_HANDLECATEGORY t " +
            "where t.parentid=0 order by t.categoryid";
        return dao.findBySql(sql, null, null);
    }
    
    /**
     * 
     * 描述 根据角色ID获取授权区划的信息
     * @author Flex Hu
     * @created 2015年9月14日 下午5:46:24
     * @param roleId
     * @return
     */
    public Map<String,Object> getDepInfo(String roleId){
        return dao.getDepInfo(roleId);
    }
    
    /**
     * 
     * 描述 根据用户id获取被授权的区划信息
     * @author Flex Hu
     * @created 2015年9月15日 上午9:10:19
     * @param userId
     * @return
     */
    public Map<String,Object> getDepInfoByUserId(String userId){
        return dao.getDepInfoByUserId(userId);
    }
    /**
     * 描述  判断登录的人是否分配了对应的角色
     * @author Derek Zhang
     * @created 2015年10月16日 下午2:21:29
     * @param userId  roleCodes 角色Code 多个用逗号分隔
     * @return
     */
    @Override
    public boolean hasRoleByCode(String userId, String[] roleCodes) {
        return dao.hasRoleByCode(userId,roleCodes);
    }
    
    @Override
    public Map<String, Object> getRoleIdToModuleID(String roleId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select WMSYS.WM_CONCAT(T.MODULE_ID) MODULE_IDS ");
        sql.append("   from T_SYSTEM_MODULE_SYSROLE T ");
        sql.append(" WHERE T.ROLE_ID=? ");
        return dao.getByJdbc(sql.toString(),  new Object[] { roleId });
    }
    
    @Override
    public void saveRoleMoudle(String roleId, String[] checkModuleIds) {
        // TODO Auto-generated method stub
        if(null!=checkModuleIds&&checkModuleIds.length>0){
            //先清除掉中间表数据
            this.remove("T_SYSTEM_MODULE_SYSROLE",new String[]{"ROLE_ID"},new Object[]{roleId});
            for(int i = 0;i<checkModuleIds.length;i++){
                Map<String, Object> colValues = new HashMap<String, Object>();
                colValues.put("ROLE_ID", roleId);
                colValues.put("MODULE_ID", checkModuleIds[i]);
                dao.saveOrUpdate(colValues, "T_SYSTEM_MODULE_SYSROLE", null);
            }            
        }
    }

    @Override
    public void saveOrUpdateRoleRightsLog(String roleId, String[] resIds, HttpServletRequest request) {
        // TODO Auto-generated method stub
        StringBuffer modifyLog = new StringBuffer();
        Map<String,Object> role = dao.getByJdbc("T_MSJW_SYSTEM_SYSROLE",new String[]{"ROLE_ID"},
                new Object[]{roleId});
        String roleName = role.get("ROLE_NAME")==null?
                "":role.get("ROLE_NAME").toString();
        String roleCode = role.get("ROLE_CODE")==null?
                "":role.get("ROLE_CODE").toString();
        modifyLog.append("修改了角色名称为"+roleName+"角色编码为"+roleCode+"的角色。");
        //获取原操作权限
        String oldResIds = sysResService.getGrantResIds(roleId);
        //获取原数据权限
        Map<String,Object> oldDepInfo = dao.getDepInfo(roleId);
        String oldDepInfos = oldDepInfo.get("DEP_IDS")==null?
                "":oldDepInfo.get("DEP_IDS").toString();
        String[] checkDataIds = request.getParameterValues("checkDataIds[]");
        //获取原栏目权限
        Map<String,Object> oldModuleInfo = sysRoleService.getRoleIdToModuleID(roleId);
        String oldModuleInfos = oldModuleInfo.get("MODULE_IDS")==null?
                "":oldModuleInfo.get("MODULE_IDS").toString();
        String[] checkModuleIds = request.getParameterValues("checkModuleIds[]");
        //判断新增操作权限
        StringBuffer addresIds = new StringBuffer();
        for (String resId : resIds) {
            if (!oldResIds.contains(resId)) {
                addresIds.append("'");
                addresIds.append(resId);
                addresIds.append("',");
            }
        }
        if (StringUtils.isNotEmpty(addresIds)) {
            StringBuffer resNames = getResName(addresIds);
            modifyLog.append("【新增】"+resNames.toString()+"的【操作授权】；");
        }
        //判断新增数据权限
        StringBuffer addDepIds = new StringBuffer();
        for (String checkDataId : checkDataIds) {
            if (!oldDepInfos.contains(checkDataId)) {
                addDepIds.append("'");
                addDepIds.append(checkDataId);
                addDepIds.append("',");
            }
        }
        if (StringUtils.isNotEmpty(addDepIds)) {
            StringBuffer depNames = getDepName(addDepIds);
            modifyLog.append("【新增】"+depNames.toString()+"的【数据授权】；");
        }
        //判断新增栏目权限
        StringBuffer addModuleIds = new StringBuffer();
        List<String> oldModuleInfosList=Arrays.asList(oldModuleInfos);
        if (checkModuleIds!=null) {
            for (String checkModuleId : checkModuleIds) {
                if (!oldModuleInfosList.contains(checkModuleId)) {
                    addModuleIds.append("'");
                    addModuleIds.append(checkModuleId);
                    addModuleIds.append("',");
                }
            }
        }
        if (StringUtils.isNotEmpty(addModuleIds)) {
            StringBuffer moduleNames = getModName(addModuleIds);
            modifyLog.append("【新增】"+moduleNames.toString()+"的【栏目授权】；");
        }
        //判断减少的操作权限
        StringBuffer removeResIds = new StringBuffer();
        List<String> resIdsList=Arrays.asList(resIds);
        String[] oldResIdss = oldResIds.split(",");
        for (String oldResId : oldResIdss) {
            if(!resIdsList.contains(oldResId)){
                removeResIds.append("'");
                removeResIds.append(oldResId);
                removeResIds.append("',");
            }
        }
        if (StringUtils.isNotEmpty(removeResIds)) {
            StringBuffer resNames = getResName(removeResIds);
            modifyLog.append("【删除】"+resNames.toString()+"的【操作授权】；");
        }
        //判断减少的数据权限
        StringBuffer removeDepIds = new StringBuffer();
        List<String> depIdsList=Arrays.asList(checkDataIds);
        String[] oldDepInfoss = oldDepInfos.split(",");
        for (String oldDepInfoId : oldDepInfoss) {
            if(!depIdsList.contains(oldDepInfoId)){
                removeDepIds.append("'");
                removeDepIds.append(oldDepInfoId);
                removeDepIds.append("',");
            }
        }
        if (StringUtils.isNotEmpty(removeDepIds)) {
            StringBuffer depNames = getDepName(removeDepIds);
            modifyLog.append("【删除】"+depNames.toString()+"的【数据授权】；");
        }
        //判断减少的栏目权限
        StringBuffer removeModuleIds = new StringBuffer();
        String[] oldModuleInfoss = oldModuleInfos.split(",");
        if (checkModuleIds==null) {
            for (String oldModuleInfoId : oldModuleInfoss) {
                removeModuleIds.append("'");
                removeModuleIds.append(oldModuleInfoId);
                removeModuleIds.append("',");
            }
        }else {
            List<String> moduleIdsList=Arrays.asList(checkModuleIds);
            for (String oldModuleInfoId : oldModuleInfoss) {
                if(!moduleIdsList.contains(oldModuleInfoId)){
                    removeModuleIds.append("'");
                    removeModuleIds.append(oldModuleInfoId);
                    removeModuleIds.append("',");
                }
            }
        }
        if (StringUtils.isNotEmpty(removeModuleIds)) {
            StringBuffer moduleNames = getModName(removeModuleIds);
            modifyLog.append("【删除】"+moduleNames.toString()+"的【栏目授权】；");
        }
        
        if (StringUtils.isNotEmpty(modifyLog)) {
            Map<String, Object> colValues = new HashMap<String, Object>();
            SysUser sysUser = AppUtil.getLoginUser();
            String fullName = sysUser.getFullname();
            String userName = sysUser.getUsername();
            String userId = sysUser.getUserId();
            String browser = BrowserUtils.checkBrowse(request);
            String idAddress = BrowserUtils.getIpAddr(request);
            colValues.put("BROWSER", browser);
            colValues.put("LOG_CONTENT", modifyLog);
            colValues.put("OPERATE_TYPE", "6");
            colValues.put("FULLNAME", fullName);
            colValues.put("USERNAME", userName);
            colValues.put("USERID", userId);
            colValues.put("IP_ADDRESS", idAddress);
            dao.saveOrUpdate(colValues, "T_MSJW_SYSTEM_SYSLOG", null);
        }
    }

    private StringBuffer getModName(StringBuffer moduleIds) {
        StringBuffer sql = new StringBuffer("select t.* from T_CMS_ARTICLE_MODULE t ");
        sql.append(" where t.module_id in ( ");
        sql.append(moduleIds);
        sql.append(" '1124') ");
        List<Map<String, String>> addModuleList = dao.findBySql(sql.toString(), null, null);
        StringBuffer moduleNames = new StringBuffer();
        for (Map<String, String> map : addModuleList) {
            String moduleName = map.get("MODULE_NAME")==null?
                    "":map.get("MODULE_NAME").toString();
            moduleNames.append(moduleName);
            moduleNames.append(";");
        }
        return moduleNames;
    }

    private StringBuffer getDepName(StringBuffer depIds) {
        StringBuffer sql = new StringBuffer("select t.* from t_msjw_system_department t ");
        sql.append(" where t.depart_id in ( ");
        sql.append(depIds);
        sql.append(" '1124') ");
        List<Map<String, String>> addDepList = dao.findBySql(sql.toString(), null, null);
        StringBuffer depNames = new StringBuffer();
        for (Map<String, String> map : addDepList) {
            String depName = map.get("DEPART_NAME")==null?
                    "":map.get("DEPART_NAME").toString();
            depNames.append(depName);
            depNames.append(";");
        }
        return depNames;
    }

    private StringBuffer getResName(StringBuffer resIds) {
        StringBuffer sql = new StringBuffer("select t.* from T_MSJW_SYSTEM_RES t ");
        sql.append(" where t.res_id in ( ");
        sql.append(resIds);
        sql.append(" '1124') ");
        List<Map<String, String>> addResList = dao.findBySql(sql.toString(), null, null);
        StringBuffer resNames = new StringBuffer();
        for (Map<String, String> map : addResList) {
            String resName = map.get("RES_NAME")==null?
                    "":map.get("RES_NAME").toString();
            resNames.append(resName);
            resNames.append(";");
        }
        return resNames;
    }
}
