/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.dao;

import java.util.List;
import net.evecom.core.dao.BaseDao;

/**
 * 描述 文章操作dao
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ContentDao extends BaseDao {
    /**
     * 文章复制功能
     * 
     * @param ids
     *            文章IDs
     * @param moduleIds
     *            栏目IDs
     */
    public void multicopy(String[] ids, String[] moduleIds);

    /**
     * 文章剪切功能
     * 
     * @param ids
     *            文章IDs
     * @param moduleId
     *            栏目
     */
    public void paste(String[] ids, String moduleId);
    
    /**
     * 
     * 描述 更新文章附加表文章ID字段
     * @author Rider Chen
     * @created 2017年9月8日 下午2:43:16
     * @param fileIds
     * @param contentId
     */
    public void updateFileToContentId(String fileIds, String contentId);
}
