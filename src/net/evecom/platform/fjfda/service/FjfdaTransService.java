/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 食药监业务上报任务查询service
 * @author Keravon Feng
 * @created 2019年2月26日 下午1:59:47
 */
public interface FjfdaTransService extends BaseService {

    /**
     * 描述 findBySqlFilter
     * @author Keravon Feng
     * @created 2019年2月26日 下午2:12:08
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);

    /**
     * 描述 业务数据上报至食药监
     * @author Keravon Feng
     * @created 2019年2月28日 上午10:48:44
     * @param yw_Id
     * @return
     */
    public Map<String, Object> createItemRemote(String yw_Id);

    /**
     * 描述 材料附件重新报送
     * @author Keravon Feng
     * @created 2019年2月28日 上午11:32:41
     * @param yw_Id
     * @return
     */
    public void matersReUpload(String yw_Id);

    /**
     * 调用接口获取省平台的业务受理状态
     */
    public Map<String, Object> getStatusByEveId(String fjfdaeveId);
    
    /**
     * 描述 获取省平台对应业务的办结接口信息
     * @author Keravon Feng
     * @created 2019年3月5日 下午4:16:46
     * @param fjfdaeveId
     * @return
     */
    public Map<String, Object> getResultByEveId(String fjfdaeveId);

    /**
     * 描述 获取需要更新业务状态的数据
     * @author Keravon Feng
     * @created 2019年3月5日 上午10:01:04
     * @param i
     * @return
     */
    public List<Map<String, Object>> listUpdateStatusData(int i);

    /**
     * 描述 updateFlowRunStatus
     * @author Keravon Feng
     * @created 2019年3月11日 下午4:57:38
     * @param yw_Id
     * @return
     */
    public Map<String, Object> updateFlowRunStatus(Map<String,Object> map);

}
