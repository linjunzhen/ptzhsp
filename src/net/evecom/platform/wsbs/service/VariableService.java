/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 内部流程公文虚拟表管理操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface VariableService extends BaseService {

    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月10日 下午6:06:00
     * @param exeId
     * @param documentId
     * @param fromUserName
     * @param toUserName
     * @param nodeName
     * @return
     */
    Map<String, Object> getMapByMoreId(String exeId, String documentId,
            String fromUserName, String toUserName, String nodeName);

    /**
     * 描述 保存HTML信息
     * @author Faker Li
     * @created 2016年5月12日 下午5:19:44
     * @param variables
     * @return
     */
    public String saveHtmlData(Map<String, Object> dataMap,String xnb_id);

    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月16日 下午1:54:40
     * @param xnbId
     * @return
     */
    public Map<String, Object> getMapByXnbId(String xnbId);
    
}
