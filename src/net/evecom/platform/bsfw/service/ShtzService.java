/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月10日 上午10:42:40
 */
public interface ShtzService extends BaseService {

    /**
     * 
     * 描述 计算协调出来的结果
     * @author Flex Hu
     * @created 2015年8月19日 下午5:15:45
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResult(Map<String,Object> flowVars);
    /**
     * 
     * 描述 计算是否协调一致的结果
     * @author Flex Hu
     * @created 2015年11月10日 上午11:16:50
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String,Object> flowVars);
    /**
     * 
     * 描述 获取投资项目流程数据集合
     * @author Flex Hu
     * @created 2015年11月27日 下午1:57:15
     * @return
     */
    public List<Map<String,Object>> findShtzJdDatas(String type,int jieduanValue);
    /**
     * 
     * 描述 获取投资项目流程数据集合
     * @author Flex Hu
     * @created 2015年11月27日 下午2:48:05
     * @param type
     * @return
     */
    public List<List<Map<String,Object>>> findShtzDatas(String type);
    /**
     * 
     * 描述 设置项目编号值
     * @author Flex Hu
     * @created 2015年12月2日 上午10:55:47
     * @param flowVars
     * @return
     */
    public Map<String,Object> setProjectNumber(Map<String,Object> flowVars);
    
    /**
     * 
     * 描述 获取省网办投资项目在线监管平台批量保存办理情况数据表
     * @author Rider Chen
     * @created 2015-12-29 上午11:42:21
     * @param projectCode
     * @return
     */
    public List<Map<String,Object>> findSwbtzxmData(String status,String projectCode);
}
