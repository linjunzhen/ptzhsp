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
 * 描述 法律法规操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface LawReguService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据办事项目ID获取法律法规列表
     * @author Flex Hu
     * @created 2015年9月2日 上午9:10:14
     * @param guideId
     * @return
     */
    public List<Map<String,Object>> findByGuideId(String guideId);
    /**
     * 
     * 描述 更新中间表
     * @author Flex Hu
     * @created 2015年9月2日 上午10:10:51
     * @param guideId
     * @param lawIds
     */
    public void saveMiddleConfig(String guideId,String lawIds);
    /**
     * 
     * 描述 删除法律法规并且级联删除掉中间表数据
     * @author Flex Hu
     * @created 2015年9月2日 上午10:20:46
     * @param lawIds
     */
    public void deleteCascade(String[] lawIds);
}
