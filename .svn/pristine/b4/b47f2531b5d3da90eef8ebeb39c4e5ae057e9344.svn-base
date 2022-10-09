/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import net.evecom.core.service.BaseService;
import net.evecom.platform.system.model.SysUser;
import org.apache.commons.logging.Log;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 描述 省网办接口服务业务接口 省网办数据解析服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月3日 上午10:19:14
 */
@SuppressWarnings("rawtypes")
public interface SwbDataParseService extends BaseService {
    /**
     * 描述 获取省网办上的待办统计数据
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @return
     */
    public Map<String, Integer> getPendingTaskCount(SysUser sysUser);

    /**
     * 
     * 描述 获取省网办上的待办任务
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @return
     */
    public List<Map<String, String>> getPendingTaskList(SysUser sysUser);

    /**
     * 
     * 描述 获取省网办上的单点登录的停牌
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @return
     */
    public Map<String, String> getSwbLoginDynamicPwd(SysUser sysUser);

    /**
     * 
     * 描述
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 上午11:18:23
     * @param dataAbutment
     */
    /*public void sendMsgByList(Map<String, Object> dataAbutment);*/

    /**
     * 
     * 描述 从省网办同步办件数据
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @param params
     */
    public void parseSwbDataFromProa(Log log);

    /**
     * 解析content内容获取基本字段信息
     * @param content
     * @param type
     * @return
     */
    Map<String,Object> getDataByContent(String content,String type);
    /**
     * 
     * 描述 从省网办解析返回信息
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @param params
     */
    public void parseReturnInfos(Log log);
}
