/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 法律法规操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface LawReguDao extends BaseDao {
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
     * 描述 根据指南ID删除掉中间表配置信息
     * @author Flex Hu
     * @created 2015年9月2日 上午9:54:08
     * @param guideId
     */
    public void deleteMiddelConfig(String guideId);
    /**
     * 
     * 描述 更新中间表
     * @author Flex Hu
     * @created 2015年9月2日 上午10:10:51
     * @param guideId
     * @param lawIds
     */
    public void saveMiddleConfig(String guideId,String lawIds);
}
