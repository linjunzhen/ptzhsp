/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.WinMailUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述 WinMail测试用例
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年12月15日 上午10:38:32
 */
public class WinMailTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WinMailTestCase.class);
    /**
     * 
     * 描述 添加用户
     * @author Flex Hu
     * @created 2014年12月15日 下午4:05:55
     */
    @Test
    public void addUser(){
        int result = WinMailUtil.addUser("king", "King123");
        log.info(result);
    }
    /**
     * 
     * 描述 删除用户
     * @author Flex Hu
     * @created 2014年12月15日 下午4:02:49
     */
    @Test
    public void deleteUser(){
        int result = WinMailUtil.deleteUser("fjrdtest");
        log.info(result);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年12月15日 下午3:55:28
     */
    @Test
    public void changePassword(){
        int result = WinMailUtil.changePassword("fjrd", "Fjrd123");
        log.info(result);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年12月15日 上午11:14:31
     */
    @Test
    public void getNewMailCount(){
        int result = WinMailUtil.getNewMailCount("chenjiangnan", "chenjiangnan@123");
        log.info(result);
    }
    /**
     * 
     * 描述 测试生成新用户文本文件
     * @author Flex Hu
     * @created 2014年12月29日 上午11:01:41
     */
    @Test
    public void testGenNewUserText(){
        /*StringBuffer str = new StringBuffer("#邮箱地址,密码\n");
        str.append("user1@fjrd.gov.cn,{md5}")
        .append(StringUtil.getMd5Encode("123456")).append("\n");
        str.append("user2@fjrd.gov.cn,{md5}")
        .append(StringUtil.getMd5Encode("123456")).append("\n");
        //log.info(str.toString());
        FileUtil.writeTextToDisk("d:/user.txt", str.toString());*/
    }

    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2014年12月15日 上午10:38:32
     * @param args
     */
    public static void main(String[] args) {
        String ip = "192.168.0.40:8088";
        log.info(ip.substring(0, ip.indexOf(":")));
    }
}
