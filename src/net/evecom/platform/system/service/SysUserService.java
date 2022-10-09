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
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午3:31:01
 */
public interface SysUserService extends BaseService {
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
     * 描述 这个接口将会获取用户所相关的一切必要信息，如部门,权限等
     * @author Flex Hu
     * @created 2014年9月11日 下午4:29:04
     * @param username
     * @return
     */
    public SysUser getAllInfoByUsername(String username);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 移除系统用户信息
     * @author Flex Hu
     * @created 2014年10月4日 下午4:08:13
     * @param userIds
     */
    public void removeCascade(String[] userIds);
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
     * 描述 增加或者修改用户
     * @author Flex Hu
     * @created 2014年10月11日 上午8:55:11
     * @param userData
     * @param entityId
     * @return
     */
    public String saveOrUpdateUser(Map<String,Object> userData,String entityId);
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
    /**
     * 
     * 描述 根据用户IDS获取列表数据
     * @author Flex Hu
     * @created 2015年9月23日 下午5:18:22
     * @param userIds
     * @return
     */
    public List<Map<String,Object>> findByUserId(String userIds);
    /**
     * 
     * 描述 根据帐号获取用户列表数据
     * @author Flex Hu
     * @created 2015年11月13日 下午4:31:25
     * @param userAccounts
     * @return
     */
    public List<Map<String,Object>> findByUserAccounts(String userAccounts);
    /**
     * 
     * 描述 根据DepCode和Reskey获取用户
     * @author Faker Li
     * @created 2015年10月26日 下午3:52:53
     * @param depCode
     * @return
     */
    public List<Map<String, Object>> getUserByDepCodeAndReskey(String depCode);
    
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
            String varName,String handlerRule);
    /**
     * 
     * 描述 获取审核人信息
     * @author Flex Hu
     * @created 2015年11月16日 上午10:28:53
     * @param userAccounts
     * @return
     */
    public List<FlowNextHandler> getHandlers(String userAccounts);
    /**
     * 
     * 描述 根据帐号编码获取用户信息
     * @author Flex Hu
     * @created 2015年11月25日 上午10:53:56
     * @param userAccount
     * @return
     */
    public Map<String,Object> getUserInfo(String userAccount);
    
    /**
     * 
     * 描述 获取需要自动禁用的用户
     * @author Rider Chen
     * @created 2017年5月16日 下午3:04:26
     * @param timeNum
     * @return
     */
    public List<Map<String, Object>> getDraftUser(int timeNum);
    
    /**
     * 描述 获取当前登录人员-流程审核人员插件
     * @author Bryce Zhang
     * @created 2017-5-22 下午2:12:26
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getCurLoginUser(Map<String,Object> flowVars, String varName, String handlerRule);
    
    
    /**
     * 
     * 描述 根据角色CODE获取系统用户列表信息
     * @author Rider Chen
     * @created 2017年6月22日 下午3:09:00
     * @param roleCode
     * @return
     */
    public List<Map<String,Object>> findUsersByRoleCode(String roleCode);

    /**
     * 
     * 描述 多次登入失败处理
     * @author Kester Chen
     * @created 2017年6月22日 下午3:09:00
     * @param username
     * @return
     */
    public void handWrongNum(String username);
    /**
     * 
     * 描述 重置登入失败次数
     * @author Kester Chen
     * @created 2017年6月22日 下午3:09:00
     * @param username
     * @return
     */
    public void reSetWrongNum(String username);
    /**
     * 
     * 描述    获取具备照片的人员
     * @author Danto Huang
     * @created 2019年1月21日 下午3:34:01
     * @return
     */
    public List<Map<String,Object>> findPhotoUsers();
    
    /**
     * 
     * 描述 根据用户IDS获取列表数据
     * @author Flex Hu
     * @created 2015年9月23日 下午5:18:22
     * @param userIds
     * @return
     */
    public List<Map<String,Object>> findByMoblie(String mobile);
    
    /**
     * 
     * 描述    根据身份证或者手机号获取用户
     * @author Danto Huang
     * @created 2021年9月3日 上午9:10:45
     * @param mobile
     * @param card
     * @return
     */
    public Map<String,Object> isExistsUserByMobileOrCard(String mobile, String card);
}
