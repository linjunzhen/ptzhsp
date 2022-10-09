/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tyjk.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 平潭综合审批统一接口
 * 
 * @author Keravon Feng
 * @created 2018年7月23日 上午10:05:04
 */
public interface FlowWebService extends BaseService {

    /**
     * 
     * 描述 流程启动
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 下午4:03:57
     * @param flowInfo
     * @return
     */
    public String flowStart(String uuid, String flowInfoJson);

    /**
     * 
     * 描述 流程环节执行
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 上午8:48:36
     * @param flowInfo
     * @return
     */
    public String flowExecute(String uuid, String flowInfoJson);

    /**
     * 
     * 描述 流程环节执行
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 上午8:48:36
     * @param flowInfo
     * @return
     */
    public String getItemApplyMaters(String uuid, String itemCode);

    /**
     * 
     * 描述：退件
     * 
     * @author Rider Chen
     * @created 2018年10月29日 上午8:47:47
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String notAccept(String uuid, String flowInfoJson);

    /**
     * 
     * 描述：挂起
     * 
     * @author Rider Chen
     * @created 2018年10月29日 上午8:47:47
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String handUp(String uuid, String flowInfoJson);

    /**
     * 
     * 描述：重启
     * 
     * @author Rider Chen
     * @created 2018年10月29日 上午8:47:47
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String reStart(String uuid, String flowInfoJson);

    /**
     * 
     * 描述：保存附件信息
     * 
     * @author Rider Chen
     * @created 2018年10月30日 上午10:27:32
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String saveFile(String uuid, String flowInfoJson);

    /**
     * 
     * 描述：获取附件流信息
     * 
     * @author Rider Chen
     * @created 2018年11月16日 上午10:02:20
     * @param platType
     * @return
     */
    public List<Map<String, Object>> getFileTrans(String platType);

    /**
     * 
     * 描述： 获取平台综合审批系统叫号信息
     * 
     * @author Rider Chen
     * @created 2019年1月21日 上午9:40:33
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String getCallNumber(String uuid, String flowInfoJson);

    /**
     * 
     * 描述： 获取平台综合审批系统取号排队信息
     * 
     * @author Rider Chen
     * @created 2019年1月21日 上午9:40:33
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String getQueryLine(String uuid, String flowInfoJson);

    /**
     * 
     * 描述： 办件结果查询
     * 
     * @author Rider Chen
     * @created 2019年1月21日 上午10:48:12
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String getQueryResult(String uuid, String flowInfoJson);

    /**
     * 
     * 描述：删除一个月前的数据
     * @author Rider Chen
     * @created 2019年6月14日 下午5:21:48
     */
    public void delFileTrans();
    /**
     *
     * 描述 网上用户启动流程
     *
     * @author Keravon Feng
     * @created 2018年7月23日 下午4:03:57
     * @return
     */
    String onlineFlowStart(String uuid, String flowInfoJson);
    /**
     * 网上申请获取闽政通用户信息
     * @param flowInfo
     * @return
     */
    public Map<String,Object> getMztUserForOnlineApply(Map<String,Object> flowInfo);
    /**
     * 描述 根据闽政通账号获取用户账号名
     *
     * @author Keravon Feng
     * @created 2018年7月23日 上午10:09:38
     * @param flowInfoJson
     * @return
     */
    public String getAccountByMztInfo(String uuid, String flowInfoJson);
    
    
   /**
    * 
    * 描述：退回补件接口
    * @author Rider Chen
    * @created 2020年12月7日 下午3:32:11
    * @param uuid
    * @param flowInfoJson
    * @return
    */
    public String exeBjFlow(String uuid, String flowInfoJson);
    
    /**
     * 
     * 描述：查询流程节点配置
     * @author Rider Chen
     * @created 2020年12月7日 下午3:32:11
     * @param uuid
     * @param flowInfoJson
     * @return
     */
    public String queryFlowConfig(String exeId);
}
