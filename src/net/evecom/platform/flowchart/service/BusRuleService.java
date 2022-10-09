/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
/**
 * 描述 监察规则
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
public interface BusRuleService extends BaseService{
    /**
     * 描述 根据sqlfilter获取到监察规则列表
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 描述通过业务专项获取业务环节
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBus(String busCode);
    /**
     * 描述通过业务环节获取过程编码
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param tacheCode
     * @return
     */
    public List<Map<String, Object>> findProcessByTache(String tacheCode);
    /**
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByRuleId(String ruleId);
    /**
     * 描述 更新规则至提交审核状态
     * @author Toddle Chen
     * @created Sep 14, 2015 5:13:33 PM
     * @param ruleId
     * @param itemCode
     */
    public void editToAudit(String ruleId,String itemCode);
    /**
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessCode(String processCode);
    /**
     * 描述通过规则表达式删除隐藏开始过程编码记录
     * @author Toddle Chen
     * @created Oct 10, 2015 3:55:09 PM
     * @param ruleId
     */
    public void delStartProcessByRuleId(String ruleId);
}
