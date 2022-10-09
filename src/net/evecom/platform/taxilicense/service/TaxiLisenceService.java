/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.taxilicense.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.model.FlowNextHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 描述
 * @author Bennett Zhang
 * @created 2021年12月14日 下午10:14:49
 */
public interface TaxiLisenceService extends BaseService {

    /**
     *
     * 描述 决策接口：是否通过
     * @author Bennett Zhang
     * @created 2021-12-13 上午9:28:30
     * @param flowVars
     * @return
     */
    public Set<String> getIsPass(Map<String, Object> flowVars);

    /**
     * 获取审批完成 结束的出租车资格证一件事申请
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> passedTaxiLisenceList(SqlFilter sqlFilter);



    /**
     * 导出 列表数据
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> exportDatas(SqlFilter sqlFilter);

    /**
     * 描述 获取导入对应的数据库数据
     * @param
     * @return
     */
    public Map<String, Object> getImportData(String exeId);


    /**
     *
     * 描述 流程提交后置事件
     * taxiLisenceService.updateZGZApplyStaus
     * @param flowVars
     * @return
     */
    public Map<String, Object> updateZGZApplyStaus(Map<String,Object> flowVars);


}
