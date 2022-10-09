/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.wsbs.dao.SceneInfoDao;
import net.evecom.platform.wsbs.service.SceneInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 场景导航操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("sceneInfoService")
public class SceneInfoServiceImpl extends BaseServiceImpl implements SceneInfoService {
    /**
     * 所引入的dao
     */
    @Resource
    private SceneInfoDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 添加项目到场景导航
     * @author Flex Hu
     * @created 2015年9月1日 上午11:23:58
     * @param sceneId
     * @param guideIds
     */
    public void saveGuides(String sceneId,String guideIds){
        dao.saveGuides(sceneId, guideIds);
    }
    
    /**
     * 
     * 描述 移除办事项目
     * @author Flex Hu
     * @created 2015年9月1日 上午11:53:46
     * @param sceneId
     * @param guideIds
     */
    public void removeGuides(String sceneId,String guideIds){
        dao.removeGuides(sceneId, guideIds);
    }
}
