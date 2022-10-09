/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 不动产收费发证打证后台流程相关方法
 * @author Keravon Feng
 * @created 2019年12月17日 下午2:41:15
 */
public interface BdcQlcSffzInfoService extends BaseService {
    /**
     * 描述 获取填写标识码打证列表数据
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:58:03
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findQzPrintListBySqlFilter(SqlFilter filter);
    
    /**
     * 描述 获取填写缴费领证信息列表数据
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:58:07
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findJffzBySqlFilter(SqlFilter filter);

    /**
     * 描述 主要是保存表单上的数据并完成登记发证状态的变更
     * @author Keravon Feng
     * @created 2019年12月18日 下午3:55:26
     * @param variables
     */
    public void doSaveBdcJffzinfo(Map<String, Object> variables) throws Exception;

    /**
     * 
     * 描述  后台打证与收费发证环节提交
     * @author Keravon Feng
     * @created 2019年12月20日 上午10:23:52
     * @param variables
     */
    public Map<String, Object> saveBdcAudit(Map<String, Object> variables);

    /**
     * 描述 我的打证列表
     * @author Keravon Feng
     * @created 2020年1月2日 上午11:16:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findMyQzPrintListBySqlFilter(SqlFilter filter);

    /**
     * 描述 我的发证
     * @author Keravon Feng
     * @created 2020年1月2日 下午2:27:45
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findMyJffzBySqlFilter(SqlFilter filter);

    /**
     * 描述:查询不动产全流程办件
     *
     * @author Madison You
     * @created 2020/6/12 10:23:00
     * @param
     * @return
     */
    List<Map<String, Object>> findSheduleQueryDatagrid(SqlFilter filter);


}
