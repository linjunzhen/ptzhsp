/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;

/**
 * 描述 WinMail工具类
 * @author Flex Hu
 * @version 1.0
 * @created 2014年12月15日 上午11:04:11
 */
public class WinMailUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WinMailUtil.class);
    /**
     * 
     * 描述 创建邮箱用户
     * @author Flex Hu
     * @created 2014年12月15日 下午4:05:24
     * @param username
     * @param password
     * @return
     */
    public static int addUser(String username,String password){
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取邮件系统数据库地址
        String mailDbPath = properties.getProperty("mailDbPath");
        // 获取邮件系统的域名
        String mailDomain = properties.getProperty("mailDomain");
        ReleaseManager rm = new ReleaseManager();
        int result = 0;
        try {
            IDispatch ids = new IDispatch(rm, "MailServerCtrl.MailDBInterface");
            Object[] param1 = new Object[] { mailDbPath };
            ids.method("InitControl", param1);
            Object[] param2 = new Object[] { username, mailDomain,password};
            result = Integer.parseInt(ids.method("AddUser", param2).toString());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            rm.release();
        }
        return result;
    }
    
    /**
     * 
     * 描述 根据用户名称删除掉用户
     * @author Flex Hu
     * @created 2014年12月15日 下午4:01:51
     * @param username
     * @return
     */
    public static int deleteUser(String username){
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取邮件系统数据库地址
        String mailDbPath = properties.getProperty("mailDbPath");
        // 获取邮件系统的域名
        String mailDomain = properties.getProperty("mailDomain");
        ReleaseManager rm = new ReleaseManager();
        int result = 0;
        try {
            IDispatch ids = new IDispatch(rm, "MailServerCtrl.MailDBInterface");
            Object[] param1 = new Object[] { mailDbPath };
            ids.method("InitControl", param1);
            Object[] param2 = new Object[] { username, mailDomain};
            result = Integer.parseInt(ids.method("DeleteUser", param2).toString());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            rm.release();
        }
        return result;
    }
    
    /**
     * 
     * 描述 修改邮件用户密码
     * @author Flex Hu
     * @created 2014年12月15日 下午3:54:49
     * @param username
     * @param newPassword
     * @return
     */
    public static int changePassword(String username,String newPassword){
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取邮件系统数据库地址
        String mailDbPath = properties.getProperty("mailDbPath");
        // 获取邮件系统的域名
        String mailDomain = properties.getProperty("mailDomain");
        ReleaseManager rm = new ReleaseManager();
        int result = 0;
        try {
            IDispatch ids = new IDispatch(rm, "MailServerCtrl.MailDBInterface");
            Object[] param1 = new Object[] { mailDbPath };
            ids.method("InitControl", param1);
            Object[] param2 = new Object[] { username, mailDomain, "",newPassword};
            result = Integer.parseInt(ids.method("ChangePassword", param2).toString());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            rm.release();
        }
        return result;
    }
    /**
     * 
     * 描述 根据用户名和密码获取收件箱中新邮件的数量值
     * @author Flex Hu
     * @created 2014年12月15日 上午11:13:45
     * @param username
     * @param password
     * @return
     */
    public static int getNewMailCount(String username,String password){
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取邮件系统数据库地址
        String mailDbPath = properties.getProperty("mailDbPath");
        // 获取邮件系统的域名
        String mailDomain = properties.getProperty("mailDomain");
        ReleaseManager rm = new ReleaseManager();
        int result = 0;
        try {
            IDispatch ids = new IDispatch(rm, "MailServerCtrl.MailDBInterface");
            Object[] param1 = new Object[] { mailDbPath };
            ids.method("InitControl", param1);
            Object[] param2 = new Object[] { username, mailDomain, password, "inbox", 1 };
            result = Integer.parseInt(ids.method("GetFolderMsgCount", param2).toString());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            rm.release();
        }
        return result;
    }
}
