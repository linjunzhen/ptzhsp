/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Faker Li
 * @version 1.0
 * @created 2015年11月10日 上午10:42:40
 */
public interface ShtzThreeService extends BaseService {

    /**
     * 
     * 描述 计算协调出来的结果
     * @author Faker Li
     * @created 2015年8月19日 下午5:15:45
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResult(Map<String,Object> flowVars);
    /**
     * 
     * 描述 计算是否协调一致的结果
     * @author Faker Li
     * @created 2015年11月10日 上午11:16:50
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String,Object> flowVars);

    /**
     * 描述:是否协调一致适用于政府投资第一流程第二版
     *
     * @author Madison You
     * @created 2019/8/21 10:59:00
     * @param
     * @return
     */
    public Set<String> getIsCoodSameA(Map<String, Object> flowVars);
    /**
     * 
     * 描述 签发是否通过
     * @author Faker Li
     * @created 2015年11月11日 上午11:24:05
     * @param flowVars
     * @return
     */
    public Set<String> getQFIsPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 公示是否通过
     * @author Faker Li
     * @created 2015年11月11日 上午11:24:05
     * @param flowVars
     * @return
     */
    public Set<String> getGSIsPass(Map<String,Object> flowVars);
}
