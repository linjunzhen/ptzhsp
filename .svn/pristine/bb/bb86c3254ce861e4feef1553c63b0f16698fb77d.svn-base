/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.filter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.SysEhcacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 描述  缓存新增管理Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {

    /**
     * log4j声明
     */
    private static Log log = LogFactory.getLog(MethodCacheInterceptor.class);
    /**
     * 设置cache
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
     * 拦截service/dao方法，查找结果是否存在，如果存在，返回cache中的值， 否则，返回数据库查询结果，并将查询结果放入cache
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String targetName = invocation.getThis().getClass().getName();// 类名
        String methodName = invocation.getMethod().getName();// 方法名
        Object[] arguments = invocation.getArguments();// 参数
        Object result;
        String cacheKey = getCacheKey(StringUtil.getMd5Encode(targetName+"."+methodName),arguments);
        Element element = null;
        synchronized (this) {
            element = cache.get(cacheKey);
            if (element == null) {
                //log.debug(cacheKey + "  加入到缓存  ,缓存方法" +targetName+"."+methodName);
                result = invocation.proceed();// 获取到所拦截方法的返回值
                element = new Element(cacheKey, (Serializable) result);
                cache.put(element);
            } else {
                //log.debug(cacheKey + " 使用到缓存  " + cache.getName());
            }
        }
    
        return element.getObjectValue();
        
    }
  //cacheKey是cache的唯一标示，包括包名.类名.方法名.参数
    private String getCacheKey(String cacheKey,
            Object[] arguments) {
        // TODO Auto-generated method stub
        
        StringBuffer sb = new StringBuffer();
        sb.append(cacheKey);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                if( arguments[i]instanceof Object[]){
                    for (Object object : (Object[])arguments[i]) {
                        sb.append(".").append(object);
                    }
                }else{
                    sb.append(".").append(arguments[i]);
                }
            }
        }
        return sb.toString();

    }

}
