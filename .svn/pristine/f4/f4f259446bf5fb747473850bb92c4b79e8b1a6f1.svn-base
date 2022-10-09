/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 施工许可
 * 
 * @author Rider Chen
 * @created 2021年3月16日 上午10:28:51
 */
@SuppressWarnings("rawtypes")
public interface ProjectSgxkService extends BaseService {

    /**
     * 
     * 描述 施工许可证照数据提交（自动签章）
     * 
     * @author Rider Chen
     * @created 2021年3月16日 上午10:39:43
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public Map<String, Object> beforeSgxkCertificateSubmit(Map<String, Object> flowDatas) throws Exception;

    /**
     * 
     * 描述 施工许可证照数据提交（不自动签章）
     * 
     * @author Rider Chen
     * @created 2021年3月26日 上午9:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public Map<String, Object> beforeSgxkDataSubmit(Map<String, Object> flowDatas) throws Exception;

    /**
     * 
     * 描述 施工许可证照证照文件获取
     * 
     * @author Rider Chen
     * @created 2021年3月26日 上午9:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public Map<String, Object> beforeSgxkgetCertificateFile(Map<String, Object> flowDatas) throws Exception;
    
    
    /**
     * 
     * 描述  获取电子证照信息预览页面接口
     * 
     * @author Rider Chen
     * @created 2021年3月26日 上午9:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public Map<String, Object> getCertificatePreviewUrl(Map<String, Object> flowDatas) throws Exception;    
    
    
    
    
    
}
