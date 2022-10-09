/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.system.dao.SysUserDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysRoleService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午3:31:22
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {
    /**
     * dao
     */
    @Resource
    private SysUserDao dao;
    /**
     * sysRoleService
     */
    @Resource
    private SysRoleService sysRoleService;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 根据帐号密码判断是否存在记录
     * 
     * @author Flex Hu
     * @created 2014年9月11日 下午3:51:37
     * @param username
     * @param password
     * @return 1:说明激活状态用户存在 0:用户名或者密码错误 -1:用户被禁用
     */
    public int isExistsUser(String username, String password) {
        return dao.isExistsUser(username, password);
    }

    /**
     * 
     * 描述 这个接口将会获取用户所相关的一切必要信息，如部门,权限等
     * 
     * @author Flex Hu
     * @created 2014年9月11日 下午4:29:04
     * @param username
     * @return
     */
    public SysUser getAllInfoByUsername(String username) {
        return dao.getAllInfoByUsername(username);
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.User_Id,T.USERNAME,T.FULLNAME,T.MOBILE,T.STATUS,D.DEPART_NAME,T.IS_DISABLE ");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER T LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEPART_ID=D.DEPART_ID");
        sql.append(" WHERE T.STATUS!=? ");
        params.add(SysUser.STATUS_DEL);
        //获取角色ID
        String roleId = sqlFilter.getRequest().getParameter("ROLE_ID");
        if(StringUtils.isNotEmpty(roleId)){
            sql.append(" AND T.USER_ID IN (SELECT UR.USER_ID FROM");
            sql.append(" T_MSJW_SYSTEM_SYSUSER_SYSROLE UR WHERE UR.ROLE_ID=? )");
            params.add(roleId);
        }
        String noAuth = sqlFilter.getRequest().getParameter("noAuth");
        if(!(StringUtils.isNotEmpty(noAuth)&&noAuth.equals("true"))){
            //获取当前用户信息
            SysUser curUser = AppUtil.getLoginUser();
            //非超管进行数据级别权限控制
            if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
                //获取当前用户被授权的部门代码
                String authDepCodes  = curUser.getAuthDepCodes();
                sql.append(" AND D.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
            }
        }
        
        Map<String, Object> config = dao.getByJdbc("ENCRYPTION_CONFIG_TABLE",
                new String[] { "BUSTABLE_NAME" }, new Object[] { "T_MSJW_SYSTEM_SYSUSER" });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            List<Map<String, Object>> columnList = dao.getAllByJdbc("ENCRYPTION_CONFIG_COLUMN",
                    new String[] { "CONFIG_ID" }, new Object[] { config.get("CONFIG_ID") });
            if(columnList!=null&&columnList.size()>0){
                for(Map<String,Object> column : columnList){
                    String columnName = (String) column.get("COLUMN_NAME");
                    if("MOBILE".equals(columnName)&&sqlFilter.getQueryParams().get("Q_T.MOBILE_=")!=null){
                        String mobile = sm4.encryptDataCBC(sqlFilter.getQueryParams().get("Q_T.MOBILE_=").toString());
                        sqlFilter.getQueryParams().put("Q_T.MOBILE_=", mobile);
                    }
                    if("USERCARD".equals(columnName)){
                        String card = sm4.encryptDataCBC(sqlFilter.getQueryParams().get("Q_T.USERCARD_=").toString());
                        sqlFilter.getQueryParams().put("Q_T.USERCARD_=", card);
                    }
                }
            }
        }
        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_MSJW_SYSTEM_SYSUSER");
        
        return list;
    }

    /**
     * 
     * 描述 移除系统用户信息
     * 
     * @author Flex Hu
     * @created 2014年10月4日 下午4:08:13
     * @param userIds
     */
    public void removeCascade(String[] userIds) {
        for (String userId : userIds) {
            Map<String, Object> sysUser = dao.getByJdbc("T_MSJW_SYSTEM_SYSUSER", new String[] { "USER_ID" },
                    new Object[] { userId });
            String username = (String) sysUser.get("USERNAME");
            if (!username.equals("admin")) {
                // 移除角色用户中间表数据
                StringBuffer sql = new StringBuffer("delete from T_MSJW_SYSTEM_SYSUSER_SYSROLE")
                    .append(" WHERE USER_ID=? ");
                dao.executeSql(sql.toString(), new Object[] { userId });
                String newUserName = UUIDGenerator.getUUID() + "_" + username;
                sysUser.put("USERNAME", newUserName);
                sysUser.put("STATUS", SysUser.STATUS_DEL);
                sysUser.put("MOBILE", "");
                sysUser.put("USERCARD", "");
                sysUser.put("IS_DISABLE", 0);
                dao.saveOrUpdate(sysUser, "T_MSJW_SYSTEM_SYSUSER", userId);
                //将用户的所在部门进行更新
                StringBuffer updateDepSql = new StringBuffer("UPDATE T_MSJW_SYSTEM_SYSUSER T");
                updateDepSql.append(" SET T.DEPART_ID=? WHERE T.USER_ID=? ");
                dao.executeSql(updateDepSql.toString(), new Object[] {"",userId});
            }
        }
    }

    /**
     * 
     * 描述 更新系统用户状态
     * 
     * @author Flex Hu
     * @created 2014年10月4日 下午4:50:21
     * @param userIds
     * @param status
     */
    public void updateUserStatus(String userIds, int status) {
        dao.updateUserStatus(userIds, status);
    }

    /**
     * 
     * 描述 将密码进行重置
     * 
     * @author Flex Hu
     * @created 2014年10月4日 下午5:36:33
     * @param userIds
     */
    public void updatePassword(String userIds) {
        dao.updatePassword(userIds);
    }
    /**
     * 
     * 描述 将密码进行重置
     * 
     * @author Flex Hu
     * @created 2014年10月4日 下午5:36:33
     * @param userIds
     */
    public void updatePassword(String userIds,String password) {
        dao.updatePassword(userIds,password);
    }
    /**
     * 
     * 描述 获取
     * 
     * @author Flex Hu
     * @created 2014年10月8日 下午5:38:20
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findUsers(SqlFilter sqlFilter) {
        List<Map<String, Object>> list = dao.findUsers(sqlFilter);
        return list;
    }

    /**
     * 
     * 描述 加入或者移除用户
     * 
     * @author Flex Hu
     * @created 2014年10月10日 上午10:29:59
     * @param departId
     * @param userIds
     * @param addOrRemove
     */
    public void updateDepId(String departId, String userIds, boolean isAdd) {
        dao.updateDepId(departId, userIds, isAdd);
    }

    /**
     * 
     * 描述 增加或者修改用户
     * 
     * @author Flex Hu
     * @created 2014年10月11日 上午8:55:11
     * @param userData
     * @param entityId
     * @return
     */
    public String saveOrUpdateUser(Map<String, Object> userData, String entityId) {
        String recordId = dao.saveOrUpdate(userData, "T_MSJW_SYSTEM_SYSUSER", entityId);
        String roleIds = (String) userData.get("ROLE_IDS");
        sysRoleService.saveRoleUser(recordId, roleIds.split(","));
        return recordId;
    }

    /**
     * 
     * 根据角色ID获取系统用户列表信息，只包含用户ID和姓名
     * 
     * @author Flex Hu
     * @created 2014年10月11日 上午9:28:23
     * @param roleId
     * @return
     */
    public List<Map<String, Object>> findUsersByRoleId(String roleId) {
        return dao.findUsersByRoleId(roleId);
    }

    /**
     * 根据用户组ID获取用户列表信息,只包含用户ID和姓名 描述
     * 
     * @author Flex Hu
     * @created 2014年10月11日 下午5:30:06
     * @param groupId
     * @return
     */
    public List<Map<String, Object>> findUsersByGroupId(String groupId) {
        return dao.findUsersByGroupId(groupId);
    }

    /**
     * 
     * 描述 根据部门ID获取用户列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findUsersByDepId(String depId) {
        return dao.findUsersByDepId(depId);
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
        return dao.getUserIdAndNames(depId);
    }
    
    /**
     * 
     * 描述 根据用户IDS获取列表数据
     * @author Flex Hu
     * @created 2015年9月23日 下午5:18:22
     * @param userIds
     * @return
     */
    public List<Map<String,Object>> findByUserId(String userIds){
        StringBuffer sql = new StringBuffer("select U.USER_ID,U.USERNAME,U.FULLNAME,U.MOBILE");
        sql.append(" FROM T_MSJW_SYSTEM_SYSUSER U WHERE U.USER_ID IN　");
        sql.append(StringUtil.getValueArray(userIds));
        sql.append(" ORDER BY U.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    
    /**
     * 
     * 描述 根据帐号获取激活用户列表数据
     * @author Flex Hu
     * @created 2015年11月13日 下午4:31:25
     * @param userAccounts
     * @return
     */
    public List<Map<String,Object>> findByUserAccounts(String userAccounts){
        StringBuffer sql = new StringBuffer("select U.USER_ID,U.USERNAME,U.FULLNAME,U.IS_ACCEPTMSG");
        sql.append(",U.MOBILE,D.DEPART_ID,D.DEPART_NAME");
        sql.append(" FROM T_MSJW_SYSTEM_SYSUSER U ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID=D.DEPART_ID ");
        sql.append(" WHERE U.STATUS=1 AND U.USERNAME IN　");
        sql.append(StringUtil.getValueArray(userAccounts));
        sql.append(" ORDER BY U.CREATE_TIME DESC ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(),null,null);
        
        encryptionService.listDecrypt(list, "T_MSJW_SYSTEM_SYSUSER");
        
        return list;
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年10月26日 下午3:53:47
     * @param depCode
     * @return
     * @see net.evecom.platform.system.service.SysUserService#getUserByDepCodeAndReskey(java.lang.String)
     */
    public List<Map<String, Object>> getUserByDepCodeAndReskey(String depCode) {
        StringBuffer sql = new StringBuffer("select SU.USER_ID,SU.FULLNAME from T_MSJW_SYSTEM_SYSUSER SU ");
        sql.append(" WHERE SU.USER_ID IN (select SR.USER_ID from T_MSJW_SYSTEM_SYSUSER_SYSROLE SR LEFT ");
        sql.append("JOIN T_MSJW_SYSTEM_RES_ROLE RR ON SR.ROLE_ID = RR.ROLE_ID LEFT JOIN ");
        sql.append("T_MSJW_SYSTEM_SYSROLE_DATA SD ON SR.ROLE_ID = SD.ROLE_ID LEFT JOIN T_MSJW_SYSTEM_RES");
        sql.append(" R ON R.RES_ID = RR.RES_ID where R.res_key = 'shqx' AND SD.DEP_CODE = ? )");
        return dao.findBySql(sql.toString(), new Object[] {depCode}, null);
    }
    
    /**
     * 
     * 描述 获取具体用户预审人员
     * @author Flex Hu
     * @created 2015年8月20日 下午1:13:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getConfirmUsers(Map<String,Object> flowVars,
            String varName,String handlerRule){
        StringBuffer sql = new StringBuffer("SELECT U.USER_ID,U.USERNAME,U.FULLNAME,U.MOBILE");
        sql.append(" FROM T_MSJW_SYSTEM_SYSUSER U WHERE U.USERNAME in");
        sql.append(StringUtil.getValueArray(varName));
        List<Map<String,Object>> users = dao.findBySql(sql.toString(),null,null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:users){
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }
    
    /**
     * 
     * 描述 获取审核人信息
     * @author Flex Hu
     * @created 2015年11月16日 上午10:28:53
     * @param userAccounts
     * @return
     */
    public List<FlowNextHandler> getHandlers(String userAccounts){
        StringBuffer sql = new StringBuffer("SELECT U.USER_ID,U.USERNAME,U.FULLNAME,U.MOBILE");
        sql.append(" FROM T_MSJW_SYSTEM_SYSUSER U WHERE U.USERNAME in");
        sql.append(StringUtil.getValueArray(userAccounts));
        List<Map<String,Object>> users = dao.findBySql(sql.toString(),null,null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:users){
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }
    
    /**
     * 
     * 描述 根据帐号编码获取用户信息
     * @author Flex Hu
     * @created 2015年11月25日 上午10:53:56
     * @param userAccount
     * @return
     */
    public Map<String,Object> getUserInfo(String userAccount){
        StringBuffer sql = new StringBuffer("select U.USER_ID,U.USERNAME,U.FULLNAME,");
        sql.append("U.MOBILE,D.DEPART_ID,D.DEPART_CODE,D.DEPART_NAME ");
        sql.append("from T_MSJW_SYSTEM_SYSUSER U LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON");
        sql.append(" U.DEPART_ID=D.DEPART_ID WHERE U.USERNAME=? ");
        Map<String,Object> userInfo = dao.getMapBySql(sql.toString(),new Object[]{userAccount});
        return userInfo;
    }

    @Override
    public List<Map<String, Object>> getDraftUser(int timeNum) {
        timeNum=workToNatureDay(timeNum);
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.*,to_date(trunc(sysdate)) -");
        sql.append(" to_date(trunc(to_date(t.ACTIVATE_TIME,'yyyy-mm-dd hh24:mi:ss'))) as timenum");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER t where t.IS_DISABLE = 1 and ");
        sql.append(" to_date(trunc(sysdate)) - to_date(trunc(to_date(t.ACTIVATE_TIME,'yyyy-mm-dd hh24:mi:ss'))) > ?");
        params.add(timeNum);
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
    /**
     * 
     * 描述：自然日换算为工作日
     * @author Water Guo
     * @created 2018-1-16 上午9:22:27
     * @param natDay
     * @return
     */
    public  int workToNatureDay(int workDay){
        String beginDate=DateTimeUtil.dateToStr(new Date());
        String endDate=workdayService.getDeadLineDate(beginDate, workDay);
        int natureDay=DateTimeUtil.getDaysBetween(beginDate, endDate);
        return natureDay;
    }
    /**
     * 描述 获取当前登录人员-流程审核人员插件
     * @author Bryce Zhang
     * @created 2017-5-22 下午2:12:26
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getCurLoginUser(Map<String,Object> flowVars, String varName, String handlerRule){
        SysUser loginUser = AppUtil.getLoginUser();
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        FlowNextHandler handler = new FlowNextHandler();
        handler.setNextStepAssignerCode(loginUser.getUsername());
        handler.setNextStepAssignerName(loginUser.getFullname());
        handlers.add(handler);
        return handlers;
    }

    @Override
    public List<Map<String, Object>> findUsersByRoleCode(String roleCode) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select U.* ")
                .append(" from T_MSJW_SYSTEM_SYSUSER U WHERE")
                .append(" U.USER_ID IN (SELECT UR.User_Id  from ")
                .append(" T_MSJW_SYSTEM_SYSUSER_SYSROLE UR ")
                .append(" JOIN T_MSJW_SYSTEM_SYSROLE R ON UR.ROLE_ID=R.ROLE_ID ")
                .append("  WHERE R.Role_Code=?)")
                .append(" AND U.STATUS!=? ORDER BY U.CREATE_TIME DESC");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{roleCode,SysUser.STATUS_DEL}, null);
        return list;
    }

    @Override
    public void handWrongNum(String username) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" ")
        .append(" select t.* from T_MSJW_SYSTEM_SYSUSER t where t.username = ?");
        List<Map<String, Object>> userInfoList = dao.findBySql(sql.toString(), new Object[]{username}, null);
        if (userInfoList==null||userInfoList.size()==0) {
            
        }else {
            String wrongNumString = userInfoList.get(0).get("WRONGNUM")==null?
                    "0":userInfoList.get(0).get("WRONGNUM").toString();
            int wrongNum = Integer.parseInt(wrongNumString);
            StringBuffer updatSql = new StringBuffer(" ");
            if (wrongNum>4) {
                updatSql.append("update T_MSJW_SYSTEM_SYSUSER t set t.status =  -1 where t.username = ?");
                dao.executeSql(updatSql.toString(), new Object[]{username});
            }else {
                updatSql.append("update T_MSJW_SYSTEM_SYSUSER t set t.wrongnum = t.wrongnum+1 where t.username = ?");
                dao.executeSql(updatSql.toString(), new Object[]{username});
            }
        }
    }

    @Override
    public void reSetWrongNum(String username) {
        // TODO Auto-generated method stub
        StringBuffer updatSql = new StringBuffer(" ");
        updatSql.append("update T_MSJW_SYSTEM_SYSUSER t set t.wrongnum = 0 where t.username = ?");
        dao.executeSql(updatSql.toString(), new Object[]{username});
    }
    
    /**
     * 
     * 描述    获取具备照片的人员
     * @author Danto Huang
     * @created 2019年1月21日 下午3:34:01
     * @return
     */
    public List<Map<String,Object>> findPhotoUsers(){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.username,t.PHOTO_PATH from T_MSJW_SYSTEM_SYSUSER t ");
        sql.append("where t.status=1 and t.PHOTO_PATH is not null");
        return dao.findBySql(sql.toString(), null, null);
    }

    @Override
    public List<Map<String, Object>> findByMoblie(String mobile) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.username from T_MSJW_SYSTEM_SYSUSER t ");
        sql.append("where t.status=1 and t.MOBILE = ? ");
        params.add(new Sm4Utils().encryptDataCBC(mobile));
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述    根据身份证或者手机号获取用户
     * @author Danto Huang
     * @created 2021年9月3日 上午9:10:45
     * @param mobile
     * @param card
     * @return
     */
    public Map<String,Object> isExistsUserByMobileOrCard(String mobile, String card){
        Map<String,Object> result = new HashMap<String, Object>();
        String sql = "select user_id,username,status from T_MSJW_SYSTEM_SYSUSER where MOBILE=? or USERCARD=?";

        Map<String, Object> config = dao.getByJdbc("ENCRYPTION_CONFIG_TABLE",
                new String[] { "BUSTABLE_NAME" }, new Object[] { "T_MSJW_SYSTEM_SYSUSER" });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            List<Map<String, Object>> columnList = dao.getAllByJdbc("ENCRYPTION_CONFIG_COLUMN",
                    new String[] { "CONFIG_ID" }, new Object[] { config.get("CONFIG_ID") });
            if(columnList!=null&&columnList.size()>0){
                for(Map<String,Object> column : columnList){
                    String columnName = (String) column.get("COLUMN_NAME");
                    if("MOBILE".equals(columnName)){
                        mobile = sm4.encryptDataCBC(mobile);                      
                    }
                    if("USERCARD".equals(columnName)){
                        card = sm4.encryptDataCBC(card);                      
                    }
                }
            }
        }
        
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{mobile, card}, null);
        if(list==null||list.size()==0){
            result.put("success", false);
            result.put("errcode", "1");
            result.put("msg", "无匹配登录用户，请联系管理员！");
        }else if(list.size()>1){
            result.put("success", false);
            result.put("errcode", "2");
            result.put("msg", "审批系统存在重复用户，手机号和身份证绑定信息不唯一！");
        }else{
            result.put("success", true);
            result.put("user", list.get(0));
        }
        return result;
    }
}