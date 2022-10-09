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
 * 描述 阅办模板操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ReadTemplateService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据readid获取阅办表单
     * @author Faker Li
     * @created 2015年10月19日 下午3:50:58
     * @param readIds
     * @return
     */
    public List<Map<String, Object>> findByReadId(String readIds);

    /**
     * 描述:根据阅办模板名称查询今日打印次数
     *
     * @author Madison You
     * @created 2020/9/29 15:40:00
     * @param
     * @return
     */
    public int findLimitCountByReadName(String readName);
}
