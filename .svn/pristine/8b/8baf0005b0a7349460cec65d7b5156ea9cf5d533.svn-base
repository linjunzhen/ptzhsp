/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年4月22日 下午5:07:11
 */
public interface AgainMaterService extends BaseService {
    /**
     * 
     * 描述 保存二次审核的数据
     * @author Flex Hu
     * @created 2016年4月22日 下午5:09:02
     * @param jsonInfo
     * @param flowVars
     */
    public void saveDatas(String jsonInfo,Map<String,Object> flowVars);
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年4月23日 下午6:24:30
     * @param exeId
     * @param curnodename
     * @return
     */
    public List<Map<String, Object>> findAgainMaterByExeId(String exeId,String curnodename);
    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月6日 上午9:18:21
     * @param exeId
     * @param curnodename
     * @param string
     * @return
     */
    public String getYjByExeIdAnd(String exeId, String curnodename,
            String materId);
}
