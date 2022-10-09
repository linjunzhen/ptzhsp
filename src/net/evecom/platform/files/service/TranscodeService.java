/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 视频转码管理Service
 * @author Bryce Zhang
 * @created 2016-10-27 下午6:19:33
 */
public interface TranscodeService extends BaseService{
    
    /**
     * 描述 获取某媒体的转码配置
     * @author Bryce Zhang
     * @created 2016-10-27 下午6:42:50
     * @param mediaType
     * @return
     */
    public Map<String, Object> getConfByMedia(String mediaType);
    
    /**
     * 描述 保存或更新转码配置
     * @author Bryce Zhang
     * @created 2016-10-28 上午9:31:19
     * @param variables
     */
    public void saveOrUpdateConf(Map<String, Object> variables);

}
