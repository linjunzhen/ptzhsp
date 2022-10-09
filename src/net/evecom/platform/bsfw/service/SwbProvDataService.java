/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import net.evecom.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 描述
 * @author Water Guo
 * @version 1.0
 * @created 2018年1月26日 下午3:21:05
 */
public interface SwbProvDataService extends BaseService {
    /**
     * 
     * 描述 获取省网办数据中需要被启动流程的数据列表
     * @author Water Guo
     * @created 2018年1月26日 下午3:40:21
     * @return
     */
    public List<Map<String,Object>> findNeedToStart();
    /**
     * 
     * 描述 根据省网办的办件基本JSON构建基本信息的MAP
     * @author Water Guo
     * @created 2018年1月26日 下午4:04:45
     * @param baseInfoJson
     * @return
     */
    public Map<String,Object> getBjBaseInfo(String baseInfoJson,Map<String,Object> swbData);
    /**
     * 
     * 描述 根据省网办的申报基本JSON构建基本信息的MAP
     * @author Water Guo
     * @created 2018年1月26日 下午5:28:23
     * @param applyBaseJson
     * @return
     */
    public Map<String,Object> getApplyBaseInfo(String applyBaseJson);
    /**
     * 
     * 描述 根据省网办的经办人JSON构建基本信息的MAP
     * @author Water Guo
     * @created 2018年1月27日 上午10:01:56
     * @param jbrBaseJson
     * @return
     */
    public Map<String,Object> getJbrBaseInfo(String jbrBaseJson);
    /**
     * 
     * 描述 获取省网办的附件JSON
     * @author Water Guo
     * @created 2018年1月27日 上午11:29:26
     * @param attJson
     * @param flowVars
     * @return
     */
    public String getSwbAttachFileJson(String attJson,Map<String,Object> flowVars);
    /**
     * 
     * 描述 启动省网办传输过来的流程
     * @author Water Guo
     * @created 2018年1月27日 下午3:56:21
     * @param swbData
     * @return
     */
    public Map<String,Object> startSwbFlow(Map<String,Object> swbData) throws Exception;
    /**
     * 
     * 描述 更新数据的状态
     * @author Water Guo
     * @created 2018年1月27日 下午4:08:51
     * @param dataId
     * @param status
     */
    public void updateDataStatus(String dataId,int status);
}
