/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 多媒体类别管理Service
 * @author Bryce Zhang
 * @created 2016-11-28 下午3:34:45
 */
public interface FileTypeService extends BaseService{
    
    /**
     * 多媒体类型-附件
     */
    public static final String ATTACHTYPE_ATTACH = "attach";
    
    /**
     * 多媒体类型-音频
     */
    public static final String ATTACHTYPE_AUDIO = "audio";
    
    /**
     * 多媒体类型-图片
     */
    public static final String ATTACHTYPE_IMAGE = "image";
    
    /**
     * 多媒体类型-flash
     */
    public static final String ATTACHTYPE_FLASH = "flash";
    
    /**
     * 多媒体类型-video
     */
    public static final String ATTACHTYPE_VIDEO = "video";
    
    /**
     * 多媒体类型-excel
     */
    public static final String ATTACHTYPE_EXCEL = "excel";
    
    /**
     * 描述 根据父亲节点，查询
     * @author Bryce Zhang
     * @created 2016-11-28 下午3:55:54
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId);
    
    /**
     * 描述 根据类别id，删除
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:46:11
     * @param typeId
     */
    public void removeByTypeId(String typeId);
    
    /**
     * 描述 根据类别，统计多媒体资源数量
     * @author Bryce Zhang
     * @created 2016-11-28 下午6:12:36
     * @param fileType
     * @return
     */
    public int countFileNumByType(String fileType);

}
