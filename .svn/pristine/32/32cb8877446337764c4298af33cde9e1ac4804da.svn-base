/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 多媒体中心附件类管理Service
 * @author Bryce Zhang
 * @created 2016-10-19 下午4:37:08
 */
public interface AttachFileService extends BaseService{
    
    /**
     * 允许上传的文件后缀，多个逗号分割
     */
    public static final String ACCEPTFILETYPES = "rar,zip,pdf,doc,docx,xls,xlsx,ppt,pptx,vsd,edc,dwg,ofd";
    
    /**
     * 允许上传的文件后缀，多个逗号分割
     */
    public static final String ACCEPEXCELTYPES = "xls,xlsx";
    
    /**
     * 文件大小限制-字节数
     */
    public static final long FILESIZELIMIT = 1073741824L;

    /**
     * 文件大小限制
     */
    public static final String FORMAT_FILESIZELIMIT = "1024M";
    
    /**
     * 描述 根据sqlFilter获取列表
     * @author Bryce Zhang
     * @created 2016-10-20 上午10:23:54
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
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
    
    /**
     * 描述 根据主键id集合，查询列表
     * @author Bryce Zhang
     * @created 2016-12-1 上午10:21:46
     * @param attachIds
     * @return
     */
    public List<Map<String, Object>> findByIds(String attachIds);

}
