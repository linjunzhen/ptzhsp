/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 业务类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FdaBusTypeService extends BaseService {
    /**
     * 
     * 描述 根据材料ID获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月17日 下午5:41:55
     * @param materId
     * @return
     */
    public Map<String,Object> getIdAndNamesByMaterId(String materId);
    /**
     * 
     * 描述 根据服务对象值获取列表记录
     * @author Flex Hu
     * @created 2015年9月18日 上午11:46:35
     * @param fwdxValue
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String fwdxValue);
    /**
     * 
     * 描述 保存类别项目中间表
     * @author Flex Hu
     * @created 2015年9月22日 下午3:17:40
     * @param typeIds
     * @param itemId
     */
    public void saveBusTypeItem(String[] typeIds,String itemId);
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getIdAndNamesByItemId(String itemId);
    /**
     * 
     * 描述 根据类别编码获取数据列表
     * @author Flex Hu
     * @created 2015年11月20日 下午5:16:59
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeForWebSite(String typeCode);
    /**
     * 
     * 描述 获取下拉框接口
     * @author Faker Li
     * @created 2016年5月26日 下午4:48:11
     * @param queryParamsJson
     * @return
     */
    public List<Map<String,Object>> findListForSelect(String queryParamsJson);
    
    /**
     * 
     * 描述 获取多选框数据接口
     * @author Rider Chen
     * @created 2016年7月5日 下午5:26:05
     * @param queryParamsJson
     * @return
     */
    public List<Map<String,Object>> findListForCheckbox(String queryParamsJson);
    
    /**
     * 描述  食品生产证照类别
     * @author John Zhang
     * @created 2017年3月5日 下午11:56:50
     * @param queryParamsJson
     * @return
     */
    public List<Map<String,Object>> findListForSpsc(String queryParamsJson);
}
