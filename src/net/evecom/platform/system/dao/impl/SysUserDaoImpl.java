/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.dao.DepartmentDao;
import net.evecom.platform.system.dao.DicTypeDao;
import net.evecom.platform.system.dao.SysResDao;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.dao.SysUserDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.GlobalUrlService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午3:29:42
 */
@Repository("sysUserDaoImpl")
public class SysUserDaoImpl extends BaseDaoImpl implements SysUserDao {
    /**
     * logger
     */
    private static Log log = LogFactory.getLog(SysUserDaoImpl.class);
    /**
     * 
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * 
     */
    @Resource
    private GlobalUrlService globalUrlService;
    /**
     * sysRoleDao
     */
    @Resource
    private SysRoleDao sysRoleDao;
    /**
     * sysResDao
     */
    @Resource
    private SysResDao sysResDao;
    /**
     * departmentDao
     */
    @Resource
    private DepartmentDao departmentDao;
    /**
     * dicTypeDao
     */
    @Resource
    private DicTypeDao dicTypeDao;
    /**
     * 
     * 描述 根据帐号密码判断是否存在记录
     * @author Flex Hu
     * @created 2014年9月11日 下午3:51:37
     * @param username
     * @param password
     * @return 1:说明激活状态用户存在 0:用户名或者密码错误 -1:用户被禁用
     */
    public int isExistsUser(String username,String password){
        StringBuffer sql = new StringBuffer("select STATUS from T_MSJW_SYSTEM_SYSUSER ");
        sql.append("WHERE USERNAME=? ");
        int status = 0;
        if (StringUtils.isNotEmpty(password)) {
            sql.append(" AND PASSWORD=? ");
            try{
                status = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{username,password});
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }else {
            try{
                status = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{username});
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }
        return status;
    }

    /**
     * 
     * 描述 根据id判断是否存在记录
     * @author Water Guo
     * @created 2016年12月28日 下午3:51:37
     * @param userId
     * @return 1:说明激活状态用户存在 0:不存在 
     */
    @Override
    public int isExistsUserById(String userId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select STATUS from T_MSJW_SYSTEM_SYSUSER ");
        sql.append("WHERE USER_ID=? ");
        int status = 0;
        try{
            status = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{userId});
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return status;
    }
    
    /**
     * 
     * 描述 这个接口将会获取用户所相关的一切必要信息，如部门,权限等
     * @author Flex Hu
     * @created 2014年9月11日 下午4:29:04
     * @param username
     * @return
     */
    public SysUser getAllInfoByUsername(String username){
        SysUser sysUser = new SysUser();
        StringBuffer sql = new StringBuffer("select U.*,D.DEPART_ID,D.DEPART_CODE,D.DEPART_NAME");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER U");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID=D.DEPART_ID ");
        sql.append(" WHERE U.USERNAME=? ");
        Map<String,Object> map = jdbcTemplate.queryForMap(sql.toString(), new Object[]{username});
        map = encryptionService.mapDecrypt(map, "T_MSJW_SYSTEM_SYSUSER");
        sysUser.setUserId((String)map.get("USER_ID"));
        sysUser.setUsername((String)map.get("USERNAME"));
        sysUser.setFullname((String)map.get("FULLNAME"));
        sysUser.setPassword((String)map.get("PASSWORD"));
        if(map.get("MOBILE")!=null){
            sysUser.setMobile((String)map.get("MOBILE"));
        }

        if(map.get("SEX")!=null){
            sysUser.setSex(Integer.parseInt(map.get("SEX").toString()));
        }
        if(map.get("EMAIL")!=null){
            sysUser.setEmail((String)map.get("EMAIL"));
        }
        if(map.get("STATUS")!=null){
            int status = Integer.parseInt(map.get("STATUS").toString());
            sysUser.setStatus(status);
        }
        if(map.get("CREATE_TIME")!=null){
            sysUser.setCreateTime((String)map.get("CREATE_TIME"));
        }
        if(map.get("DEPART_ID")!=null){
            sysUser.setDepId((String)map.get("DEPART_ID"));
        }
        if(map.get("DEPART_CODE")!=null){
            sysUser.setDepCode((String)map.get("DEPART_CODE"));
        }
        if(map.get("DEPART_NAME")!=null){
            sysUser.setDepName((String)map.get("DEPART_NAME"));
        }
        if(map.get("IS_MODIFYPASS")!=null){
            sysUser.setIsModifyPass((String)map.get("IS_MODIFYPASS"));
        }
        if(map.get("IS_ACCEPTMSG")!=null){
            sysUser.setIsAcceptMsg((String)map.get("IS_ACCEPTMSG"));
        }
        if(map.get("PHOTO_PATH")!=null){
            sysUser.setPhoto((String)map.get("PHOTO_PATH"));
        }
        if(map.get("USERCARD")!=null){
            sysUser.setUsercard((String)map.get("USERCARD"));
        }
        if(map.get("IS_UNIQUE_BIND")!=null){
            sysUser.setIsUniqueBind((String)map.get("IS_UNIQUE_BIND"));
        }
        List<Map<String,Object>> roleList = sysRoleDao.findByUserId((String)map.get("USER_ID"));
        Set<String> roleCodes= new HashSet<String>();
        Set<String> grantUrlSet = new HashSet<String>();
        StringBuffer resKeys = new StringBuffer("");
        //判断是否是超级管理员
        boolean isAdmin = false;
        for(Map<String,Object> role:roleList){
            String roleId = (String) role.get("ROLE_ID");
            if(roleId.equals("-1")){
                isAdmin = true;
            }
        }
        if(isAdmin){
            resKeys = new StringBuffer(SysUser.ADMIN_RESKEY);
        }else{
            Set<String> resKeySet = new HashSet<String>();
            for(Map<String,Object> role:roleList){
                String roleId = (String) role.get("ROLE_ID");
                String roleCode = (String) role.get("ROLE_CODE");
                roleCodes.add(roleCode);
                List<Map<String,Object>> resList = sysResDao.getResList(roleId);
                for(Map<String,Object> res : resList){
                    resKeySet.add((String) res.get("RES_KEY"));

                    String RES_URL = (String) res.get("RES_URL");
                    if(StringUtils.isNotEmpty(RES_URL)){
                        grantUrlSet.add(RES_URL);
                    }
                    String RES_OPERURLJSON = (String) res.get("RES_OPERURLJSON");
                    if(StringUtils.isNotEmpty(RES_OPERURLJSON)){
                        List<Map> urlList = JSON.parseArray(RES_OPERURLJSON,Map.class);
                        for(Map url:urlList){
                            String RES_OPERURL = (String) url.get("url");
                            grantUrlSet.add(RES_OPERURL);
                        }
                    }
                }
//                String keys = sysResDao.getGrantKeys(roleId);
//                if(StringUtils.isNotEmpty(keys)){
//                    String[] grantKeys = keys.split(",");
//                    for(String grantKey:grantKeys){
//                        resKeySet.add(grantKey);
//                    }
//                }
            }
            for(String resKey:resKeySet){
                resKeys.append(resKey).append(",");
            }
            if(resKeySet.size()>0){
                resKeys.deleteCharAt(resKeys.length()-1);
            }
            sysUser.setResKeySet(resKeySet);
            sysUser.setUrlSet(grantUrlSet);
            //用户当前用户被授权的部门信息
            Map<String,Object> depInfo = sysRoleDao.getDepInfoByUserId(sysUser.getUserId());
            sysUser.setAuthDepCodes((String)depInfo.get("DEP_CODES"));
        }
        sysUser.setResKeys(resKeys.toString());
        sysUser.setRoleCodes(roleCodes);
        String userInfoJson = JSON.toJSONString(sysUser);
        sysUser.setLoginUserInfoJson(StringEscapeUtils.escapeHtml3(userInfoJson));
        return sysUser;
    }
    
    /**
     * 
     * 描述 更新系统用户状态
     * @author Flex Hu
     * @created 2014年10月4日 下午4:50:21
     * @param userIds
     * @param status
     */
    public void updateUserStatus(String userIds,int status){
        StringBuffer sql = new StringBuffer();
        sql.append("update T_MSJW_SYSTEM_SYSUSER T SET T.STATUS=?,T.IS_DISABLE=0 ");
        /*if(status!=1){
            sql.append(",T.MOBILE='',T.USERCARD='',T.IS_UNIQUE_BIND='-1' ");
        }*/
        sql.append("WHERE T.USER_ID in ");
        String newUserIds = StringUtil.getValueArray(userIds);
        sql.append(newUserIds);
        this.jdbcTemplate.update(sql.toString(), new Object[]{status});
    }
    
    /**
     * 
     * 描述 将密码进行重置
     * @author Flex Hu
     * @created 2014年10月4日 下午5:36:33
     * @param userIds
     */
    public void updatePassword(String userIds){
        StringBuffer sql = new StringBuffer("update T_MSJW_SYSTEM_SYSUSER T SET ")
                .append(" T.PASSWORD=?,T.IS_MODIFYPASS=-1 WHERE T.USER_ID in ");
        String newUserIds = StringUtil.getValueArray(userIds);
        sql.append(newUserIds);
        String password  = StringUtil.encryptSha256(SysUser.DEFAULT_PASSWORD);
        this.jdbcTemplate.update(sql.toString(), new Object[]{password});
    }
    
    /**
     * 
     * 描述 将密码进行重置
     * @author Flex Hu
     * @created 2014年10月4日 下午5:36:33
     * @param userIds
     */
    public void updatePassword(String userIds,String password){
        StringBuffer sql = new StringBuffer("update T_MSJW_SYSTEM_SYSUSER T SET ")
                .append(" T.PASSWORD=?,T.IS_MODIFYPASS=-1 WHERE T.USER_ID in ");
        String newUserIds = StringUtil.getValueArray(userIds);
        sql.append(newUserIds);
        String pw  = StringUtil.encryptSha256(password);
        this.jdbcTemplate.update(sql.toString(), new Object[]{pw});
    }
    /**
     * 
     * 描述 获取
     * @author Flex Hu
     * @created 2014年10月8日 下午5:38:20
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findUsers(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.User_Id,T.USERNAME,T.FULLNAME,D.DEPART_NAME ");
        sql.append(",D.DEPART_CODE,T.MOBILE,T.EMAIL from T_MSJW_SYSTEM_SYSUSER T");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEPART_ID=D.DEPART_ID");
        String roleId = sqlFilter.getRequest().getParameter("roleId");
        if(StringUtils.isNotEmpty(roleId)){
            sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSUSER_SYSROLE UR ON UR.USER_ID=T.USER_ID  ");
            sql.append(" WHERE T.STATUS!=? ");
            params.add(SysUser.STATUS_DEL);
            sql.append(" AND UR.ROLE_ID=? ");
            params.add(roleId);
        }else{
            sql.append(" WHERE T.STATUS!=? ");
            params.add(SysUser.STATUS_DEL);
        }
        //获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        //非超管进行数据级别权限控制
        if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            //获取当前用户被授权的部门代码
            String authDepCodes  = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述  加入或者移除用户
     * @author Flex Hu
     * @created 2014年10月10日 上午10:29:59
     * @param departId
     * @param userIds
     * @param addOrRemove
     */
    public void updateDepId(String departId,String userIds,boolean isAdd){
        StringBuffer sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_SYSUSER U")
                .append(" SET U.DEPART_ID=? WHERE U.USER_ID in ");
        sql.append(StringUtil.getValueArray(userIds));
        if(isAdd){
            this.jdbcTemplate.update(sql.toString(), new Object[]{departId});
        }else{
            String rootDepId = (String) departmentDao.getRootDepart().get("DEPART_ID");
            sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_SYSUSER U").append(" SET U.DEPART_ID=? WHERE U.USER_ID IN ")
                    .append(StringUtil.getValueArray(userIds));
            this.jdbcTemplate.update(sql.toString(),new Object[]{rootDepId});
        }
        
    }
    
    /**
     * 
     * 根据角色ID获取系统用户列表信息，只包含用户ID和姓名
     * @author Flex Hu
     * @created 2014年10月11日 上午9:28:23
     * @param roleId
     * @return
     */
    public List<Map<String,Object>> findUsersByRoleId(String roleId){
        StringBuffer sql = new StringBuffer("select U.User_Id,u.fullname").append(" from T_MSJW_SYSTEM_SYSUSER U WHERE")
                .append(" U.USER_ID IN (SELECT UR.User_Id  from ")
                .append(" T_MSJW_SYSTEM_SYSUSER_SYSROLE UR WHERE UR.Role_Id=?)")
                .append(" AND U.STATUS!=? ORDER BY U.CREATE_TIME DESC");
        return this.findBySql(sql.toString(), new Object[]{roleId,SysUser.STATUS_DEL}, null);
    }
    
    /**
     * 根据用户组ID获取用户列表信息,只包含用户ID和姓名
     * 描述
     * @author Flex Hu
     * @created 2014年10月11日 下午5:30:06
     * @param groupId
     * @return
     */
    public List<Map<String,Object>> findUsersByGroupId(String groupId){
        StringBuffer sql = new StringBuffer("select U.User_Id,u.fullname").append(" from T_MSJW_SYSTEM_SYSUSER U WHERE")
                .append(" U.USER_ID IN (SELECT UR.User_Id  from ")
                .append(" T_SYSTEM_SYSUSER_GROUP UR WHERE UR.GROUP_ID=?)")
                .append(" AND U.STATUS!=? ORDER BY U.CREATE_TIME DESC");
        return this.findBySql(sql.toString(), new Object[]{groupId,SysUser.STATUS_DEL}, null);
    }
    
    /**
     * 
     * 描述 根据部门ID获取用户列表
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String,Object>> findUsersByDepId(String depId){
        StringBuffer sql = new StringBuffer("select USER_ID,USERNAME,FULLNAME,").append(
                "DEPART_ID FROM T_MSJW_SYSTEM_SYSUSER WHERE DEPART_ID=? AND ").append(
                " STATUS!=? ORDER BY CREATE_TIME DESC ");
        return this.findBySql(sql.toString(), new Object[]{depId,SysUser.STATUS_DEL}, null);
    }
    
    /**
     * 
     * 描述 根据部门ID获取用户IDS和用户NAMES
     * @author Flex Hu
     * @created 2015年9月14日 上午11:42:01
     * @param depId
     * @return
     */
    public Map<String,Object> getUserIdAndNames(String depId){
        StringBuffer sql = new StringBuffer("select WMSYS.WM_CONCAT(U.USER_ID) USER_IDS,");
        sql.append("WMSYS.WM_CONCAT(U.Fullname) USER_NAMES from T_MSJW_SYSTEM_SYSUSER U ");
        sql.append("WHERE U.DEPART_ID=? ORDER BY U.CREATE_TIME DESC");
        return this.jdbcTemplate.queryForMap(sql.toString(),new Object[]{depId});
    }
}
