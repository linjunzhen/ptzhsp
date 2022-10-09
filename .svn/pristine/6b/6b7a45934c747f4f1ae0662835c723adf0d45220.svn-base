/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年10月20日 上午9:46:05
 */
public class EcacheUtilTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EcacheUtilTestCase.class);
    /***
     * 手动创建一个cache
     */
    @Test
    public void addEcacheTest() {
        String cacheName = "sysLogCache";
        CacheManager manager = new CacheManager("src/conf/ehcache.xml");
        Cache cache = manager.getCache(cacheName);
        Element element = new Element("key1", "value1");
        cache.put(element);
        log.info(cache.get("key1").getObjectValue());
    }

    /**
     * 手动移除一个cache
     */
    @Test
    public void deleteEcacheTest() {
        String cacheName = "sysLogCache";
        CacheManager manager = new CacheManager("src/conf/ehcache.xml");
        Cache cache = manager.getCache(cacheName);
        manager.removeCache("sysLogCache");
        log.info(cache);
    }

    /**
     * 修改cache的数据
     */
    @Test
    public void updateEcacheTest() {
        String cacheName = "sysLogCache";
        CacheManager manager = new CacheManager("src/conf/ehcache.xml");
        Cache cache = manager.getCache(cacheName);
        Element element = new Element("key1", "value1");
        cache.put(element);
        log.info(cache.get("key1").getObjectValue());
        cache.put(new Element("key1", "value2"));
        log.info(cache.get("key1").getObjectValue());
    }

    /**
     * 获得cache中的数据
     */
    @Test
    public void queryEcacheTest() {
        String cacheName = "sysLogCache";
        CacheManager manager = new CacheManager("src/conf/ehcache.xml");
        Cache cache = manager.getCache(cacheName);
        Element element = new Element("key1", "value1");
        Double d = new Double(12.22);
        Element element2 = new Element("key2", d);
        cache.put(element);
        cache.put(element2);
        for (int i = 0; i < cache.getKeys().size(); i++) {
            Object thekey = cache.getKeys().get(i);
            Object thevalue = cache.get(thekey).getObjectValue();
            log.info("key:" + thekey.toString() + ",value:" + thevalue);
        }
    }
}
