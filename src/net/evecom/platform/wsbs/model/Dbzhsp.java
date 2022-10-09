/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.model;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 *
 * 描述 综合审批数据库连接信息
 *
 * @author Water Guo
 * @version 1.0
 * @created 2015年12月12日 下午12:31:56
 */
public class Dbzhsp {
    /**
     * 属性ptzhspDbUrl
     */
    private String  ptzhspDbUrl;
    /**
     * 属性ptzhspDbUsername
     */
    private String  ptzhspDbUsername;
    /**
     * 属性ptzhspDbPassword
     */
    private String     ptzhspDbPassword;
    /**
     * 单例对象
     */
    private static Dbzhsp dbzhsp;

    public String getPtzhspDbUrl() {
        return ptzhspDbUrl;
    }

    public void setPtzhspDbUrl(String ptzhspDbUrl) {
        this.ptzhspDbUrl = ptzhspDbUrl;
    }

    public String getPtzhspDbUsername() {
        return ptzhspDbUsername;
    }

    public void setPtzhspDbUsername(String ptzhspDbUsername) {
        this.ptzhspDbUsername = ptzhspDbUsername;
    }

    public String getPtzhspDbPassword() {
        return ptzhspDbPassword;
    }

    public void setPtzhspDbPassword(String ptzhspDbPassword) {
        this.ptzhspDbPassword = ptzhspDbPassword;
    }
    /**
     * 初始化数据库信息
     */
    private  Dbzhsp(){

    }

    /**
     * 单例模式获取Dbzhsp对象
     * @return
     */
    public synchronized static Dbzhsp getDbzhsp(){
        if(dbzhsp==null){
            dbzhsp=new Dbzhsp();
            DictionaryService dictionaryService=(DictionaryService)AppUtil.getBean("dictionaryService");
            String dbUrl=dictionaryService.getDicNames("NWReadWWDB", "dbUrl");
            String dbUsername=dictionaryService.getDicNames("NWReadWWDB", "dbUser");
            String dbPassword=dictionaryService.getDicNames("NWReadWWDB", "dbPassword");
            dbzhsp.setPtzhspDbUrl(dbUrl);
            dbzhsp.setPtzhspDbUsername(dbUsername);
            dbzhsp.setPtzhspDbPassword(dbPassword);
        }
        return dbzhsp;
    }
}
