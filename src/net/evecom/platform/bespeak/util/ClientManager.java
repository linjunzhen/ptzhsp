/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

import net.evecom.platform.bespeak.model.UserInfo;

/**
 * 描述
 * 
 * @author Panda Chen
 * @created 2016-11-2 下午04:00:49
 */
public class ClientManager {
    /**
     * instance
     */
    private static ClientManager instance = new ClientManager();
    /**
     * util
     */
    PropertiesUtil util = new PropertiesUtil("project.properties");
    /**
     * webservice
     */
    private String webservice = util.readProperty("webservice");
    /**
     * namespace
     */
    private String namespace = util.readProperty("namespace");
    /**
     * 
     * 描述
     * @author Panda Chen
     * @created 2016-11-22 下午01:52:41
     */
    private ClientManager() {

    }
    /**
     * 公众用户
     */
    private Map<String, UserInfo> map = new HashMap<String, UserInfo>();
    
    /**
     * 
     * 描述 添加用户在线
     * 
     * @author Panda Chen
     * @created 2015-3-17 下午12:56:41
     * @param sessionId
     * @param client
     */
    public void addClinet(String sessionId, UserInfo client) {
        map.put(sessionId, client);
    }

    /**
     * 
     * 描述 添加用户在线
     * 
     * @author Panda Chen
     * @created 2015-3-17 下午12:56:41
     * @param sessionId
     * @param client
     */
    public void addClinet(UserInfo client) {
        HttpSession session = ContextHolderUtil.getSession();
        map.put(session.getId(), client);
    }

    /**
     * 
     * 描述 移除用户在线
     * 
     * @author Panda Chen
     * @created 2015-3-17 下午12:56:35
     * @param sessionId
     */
    public void removeClinet(String sessionId) {
        map.remove(sessionId);
    }
    /**
     * 
     * 描述 移除用户在线
     * @author Panda Chen
     * @created 2016-11-22 下午02:06:00
     */
    public void removeClient(){
        HttpSession session = ContextHolderUtil.getSession();
        map.remove(session.getId());
    }
    /**
     * 
     * 描述 获取session
     * 
     * @author Panda Chen
     * @created 2015-3-17 下午12:56:27
     * @param sessionId
     * @return
     */
    public UserInfo getClient(String sessionId) {
        return map.get(sessionId);
    }

    /**
     * 
     * 描述 获取session
     * 
     * @author Panda Chen
     * @created 2015-3-17 下午12:56:27
     * @param sessionId
     * @return
     */
    public UserInfo getClient() {
        HttpSession session = ContextHolderUtil.getSession();
        return map.get(session.getId());
    }

    /**
     * @author Panda Chen
     * @created 2015-3-14 下午11:27:34
     * @return type
     */
    public static ClientManager getInstance() {
        return instance;
    }

    /**
     * @author Panda Chen
     * @created 2015-3-14 下午11:27:34
     * @param instance
     */
    public static void setInstance(ClientManager instance) {
        ClientManager.instance = instance;
    }

    /**
     * @author Panda Chen
     * @created 2015-3-14 下午11:27:34
     * @return type
     */
    public Map<String, UserInfo> getMap() {
        return map;
    }

    /**
     * @author Panda Chen
     * @created 2015-3-14 下午11:27:34
     * @param map
     */
    public void setMap(Map<String, UserInfo> map) {
        this.map = map;
    }

    /**
     * @author Panda Chen
     * @created 2016-11-2 下午05:08:48
     * @return type
     */
    public String getWebservice() {
        return webservice;
    }

    /**
     * @author Panda Chen
     * @created 2016-11-2 下午05:08:48
     * @param webservice
     */
    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    /**
     * @author Panda Chen
     * @created 2016-12-7 上午09:36:30
     * @return type
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @author Panda Chen
     * @created 2016-12-7 上午09:36:30
     * @param namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
