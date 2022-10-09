/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import net.evecom.core.dao.BaseDao;

/**
 * 描述 场景导航操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface SceneInfoDao extends BaseDao {
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
     * 描述 判断记录是否存在
     * @author Flex Hu
     * @created 2015年9月1日 上午11:25:09
     * @param sceneId
     * @param guideId
     * @return
     */
    public boolean isExists(String sceneId,String guideId);
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
