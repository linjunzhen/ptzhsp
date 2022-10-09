/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 竣工验收备案信息Service
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月17日 上午10:43:15
 */
@SuppressWarnings("rawtypes")
public interface ProjectFinishManageService extends BaseService {
    /**
     * 获取流程信息
     * 
     * @param projectCode
     * @param itemCode
     * @return
     */
    Map<String, Object> findExeInfo(String projectCode, String itemCode);
    
    /**
     * 
     * 描述：向住建上报数据接口(质量竣工验收监督)
     * @author Luffy Cai
     * @created 2020年5月7日 下午4:39:10
     * @return
     */
    public void pushFlowinfo();
    
    /**
     * 
     * 描述：向住建上报数据接口(消防验收（备案）申请信息)
     * @author Luffy Cai
     * @created 2020年5月9日 下午4:39:10
     * @return
     */
    public void pushXfysFlowinfo();
    
    /**
     * 
     * @Description 根据项目代码获取责任主体信息
     * @author Luffy Cai
     * @date 2020年9月16日
     * @param projectCode
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findZrztList(String projectCode);
}
