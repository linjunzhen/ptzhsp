/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程历史部署操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface HistDeployService extends BaseService {
    /**
     * 
     * 描述 保存或者更新流程历史部署信息
     * @author Flex Hu
     * @created 2015年8月14日 上午8:42:19
     * @param defId
     * @param flowDef
     */
    public void saveOrUpdate(String defId,Map<String,Object> flowDef);
    /**
     * 
     * 描述 获取流程版本列表
     * @author Flex Hu
     * @created 2015年8月27日 上午10:34:02
     * @param sqlFitler
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findBySqlFitler(SqlFilter sqlFitler,String defId);
    /**
     * 
     * 描述 级联删除掉流程版本
     * @author Flex Hu
     * @created 2015年8月27日 上午10:46:46
     * @param defId
     * @param flowVersions
     */
    public void deleteHistoryVersionCascade(String defId,String[] flowVersions);
}
