/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 不动产-订单缴费信息service()
 * @author Allin Lin
 * @created 2020年12月16日 上午10:59:49
 */
public interface BdcDdjfxxService extends BaseService{
       
    /**
     * 根据sqlfilter获取订单缴费信息数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public List<Map<String, Object>> findBySqlFileterExport(SqlFilter sqlFilter);
    
    /**
     * 描述:平潭通认证接口Token获取
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public Map<String,Object>  getCreditToken();
    
    /**
     * 描述:支付订单信息接口
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public Map<String,Object>  payOrderInfoCreate(Map<String,Object> payInfo,String token);
    
    
    /**
     * 描述:平潭通认证接口Token获取
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public Map<String, Object> queryAjaxJson(Map<String, Object> info, String urlDicType,String forwardUrl);
    
    /**
     * 描述:平潭通认证接口Token获取
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public Map<String, Object> pushData(Map<String,Object> info,String urlDicType);
    
    /**
     * 描述:平潭通订单信息发起
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public Map<String, Object> pushDataPayinfo(Map<String,Object> info,String urlDicType,String token);
    
    /**
     * 描述:订单信息短信通知
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public Map<String, Object> sendSuccessMsg(Map<String, Object> sendMsgMap);
    
}
