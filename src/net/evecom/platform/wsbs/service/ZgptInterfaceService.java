/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 中国平潭网上务业务接口
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年12月2日 上午10:15:19
 */
@SuppressWarnings("rawtypes")
public interface ZgptInterfaceService extends BaseService {
    /**
     * 描述 发布公示信息到中国平潭网公示栏目
     * 
     * @author Derek Zhang
     * @created 2015年12月2日 上午10:15:19
     * @param dataAbutment
     */
    public void publishGSXX(Map<String, Object> dataAbutment);
    
    /**
     * 描述 后置事件保存公示信息发布指令到接口中间表
     * 
     * @author Derek Zhang
     * @created 2015年12月2日 上午10:15:19
     * @param flowVars
     * @return
     */
    public Map<String, Object> publishGSXXSave(Map<String, Object> flowVars);
    /**
     * 
     * 描述 后置时间保存推送电子证照指令到接口中间表
     * @author Danto Huang
     * @created 2015-12-22 下午3:08:48
     * @param flowVars
     * @return
     */
    public Map<String, Object> publishDZZZSave(Map<String, Object> flowVars);
}
