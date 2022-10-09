/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.platform.system.service.SysEhcacheService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
/**
 * 
 * 描述 获取缓存列表
 * @author Faker Li
 * @created 2015年11月18日 下午12:36:33
 */
public class SelectEhcacheList extends RegexpMethodPointcutAdvisor {
    /**
     * log4j声明
     */
    private static Log log = LogFactory.getLog(SelectEhcacheList.class);
    /**
     * 引入Service
     */
    @Resource
    private SysEhcacheService sysEhcacheService;

    public void setPatterns(String[] patterns) {
        log.debug("开始获取缓存列表----------------------");
        List<String> list = new ArrayList<String>();
        try {
            List<Map<String, Object>> elist = sysEhcacheService.findByStatue("1");
            if (elist != null & elist.size() > 0) {
                for (int i = 0; i < elist.size(); i++) {
                    String estr = elist.get(i).get("EHCACHE_CLASS_NAME").toString().trim();
                    list.add(estr);
                    log.debug("缓存" + i + ":" + estr);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (list.size() > 0) {
            patterns = list.toArray(new String[list.size()]);
        }
        super.setPatterns(patterns);
    }
}
