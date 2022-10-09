/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.dao;

import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 多媒体中心图片类管理Dao
 * @author Bryce Zhang
 * @created 2016-10-19 下午5:04:40
 */
public interface ImageFileDao extends BaseDao{
    
    /**
     * 描述 根据类别，统计数量
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:51:40
     * @param fileType
     * @return
     */
    public int countByFileType(String fileType);
    
    /**
     * 描述 保存移动结果
     * @author Bryce Zhang
     * @created 2016-11-28 下午8:31:23
     * @param colValues
     */
    public void saveMove(Map<String, Object> colValues);

}
