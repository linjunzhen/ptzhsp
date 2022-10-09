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
 * 描述 场景导航操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SceneInfoService extends BaseService {
    
    /**
     * 
     * 描述 添加项目到场景导航
     * @author Flex Hu
     * @created 2015年9月1日 上午11:23:58
     * @param sceneId
     * @param guideIds
     */
    public void saveGuides(String sceneId,String guideIds);
    /**
     * 
     * 描述 移除办事项目
     * @author Flex Hu
     * @created 2015年9月1日 上午11:53:46
     * @param sceneId
     * @param guideIds
     */
    public void removeGuides(String sceneId,String guideIds);
}
