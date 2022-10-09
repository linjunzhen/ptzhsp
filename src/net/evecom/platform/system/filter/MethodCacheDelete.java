/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.filter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.SysEhcacheService;
import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
/**
 * 描述  删除缓存
 * @author Faker Li
 * @version 1.0
 * @created 2015年11月17日 上午17:15:15
 */
public class MethodCacheDelete implements AfterReturningAdvice, InitializingBean {
    /**
     * log4j声明
     */
    private static Log log = LogFactory.getLog(MethodCacheDelete.class);
    
    /**
     * 引入Service
     */
    @Resource
    private SysEhcacheService sysEhcacheService;
    
    public MethodCacheDelete() {
        super();
    }
    /**
     * 定义cache
     */
    private Cache cache;
    
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    /**
     * 
     * 描述 初始化
     * @author Faker Li
     * @created 2015年11月17日 下午8:46:19
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        Assert.notNull(cache, "Need a cache,please use setCache() to create");
    }
    
    /**
     * 
     * 描述 实现删除缓存
     * @author Faker Li
     * @created 2015年11月17日 下午5:13:25
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Throwable
     */
    public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
        List list = cache.getKeys(); 
        String targetName = arg3.getClass().getName();// 类名
        String methodName = arg1.getName();// 方法名
        String classname = targetName+"."+methodName;
        Map<String,Object> sysEhcache = sysEhcacheService.getByJdbc("T_MSJW_SYSTEM_EHCACHE",
                new String[]{"EHCACHE_CLASS_NAME","EHCACHE_STATUE"},new Object[]{classname,"0"});
        if(sysEhcache!=null&&!sysEhcache.isEmpty()){
            String[] str = sysEhcache.get("EHCACHE_DEL_KEY").toString().split(",");
            for (int i = 0; i < str.length; i++) {
                String cacheKey = str[i];
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).toString().indexOf(cacheKey)>=0) {
                        cache.remove(list.get(j).toString());
                       // log.debug("移除" + list.get(j).toString() + " ,缓存方法" + targetName + "." + methodName);
                    }
                }
            }
            
        }
    }

}
