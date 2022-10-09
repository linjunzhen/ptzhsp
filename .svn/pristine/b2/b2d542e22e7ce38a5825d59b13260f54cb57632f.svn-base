/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 
 * 描述
 * @author Faker Li
 * @created 2016年3月11日 上午9:40:01
 */
public interface StatisticalReportService extends BaseService {
    /**
     * 
     * 描述 根据申请方式获取当天立即办结数
     * @author Faker Li
     * @created 2016年3月11日 上午10:10:25
     * @param time
     * @param sqfs
     * @return
     */
    public int getBjsByTime(String time, String sqfs);
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月11日 下午4:19:59
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);

}
