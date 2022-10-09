/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 事项目录操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface CatalogService extends BaseService {

    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Faker Li
     * @created 2015年10月27日 下午2:23:42
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取目录表最大的排序值
     * @author Faker Li
     * @created 2015年10月27日 下午3:02:40
     * @return
     */
    public int getMaxSn();
    /**
     * 
     * 描述 根据部门编码+目录性质字典值获取最大次序
     * @author Faker Li
     * @created 2015年10月28日 上午9:37:13
     * @param departsxxzcode
     * @return
     */
    public String getMaxNumCode(String departsxxzcode);
    /**
     * 
     * 描述 伪删除办事目录
     * @author Faker Li
     * @created 2015年10月28日 上午9:59:57
     * @param split
     */
    public void removeCascade(String[] catalogIds);
    /**
     * 
     * 描述 更新排序
     * @author Faker Li
     * @created 2015年10月28日 上午11:05:12
     * @param catalogIds
     */
    public void updateSn(String[] catalogIds);
    /**
     * 
     * 描述 根据catalogCode获取catalogname
     * @author Faker Li
     * @created 2015年10月29日 上午8:57:44
     * @param catalogCode
     * @return
     */
    public Map<String,Object> getCatalogByCatalogCode(String catalogCode);
    /**
     * 
     * 描述 导入目录
     * @author Flex Hu
     * @created 2016年3月19日 下午4:15:22
     *//*
    public void impCataLogs();
    *//**
     * 
     * 描述 导入事项
     * @author Flex Hu
     * @created 2016年3月19日 下午7:42:40
     *//*
    public void impItems();
    *//**
     * 
     * 描述 导入材料
     * @author Flex Hu
     * @created 2016年3月19日 下午8:18:40
     *//*
    public void impMaters();*/
}
