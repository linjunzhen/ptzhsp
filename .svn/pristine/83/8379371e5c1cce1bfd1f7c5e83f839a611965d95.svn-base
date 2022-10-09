/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述 不动产接口操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface BdcQueryService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 获得符合in的sql语句
     * @param str   源字符串
     * @param symbol  符号
     * @return  'a','b'
     */
    public String getSplitForSqlIn(String str,String symbol);
    /**
     *转化为json数据
     * @return
     */
    public String getJsonResultOfBdcQuery(List<Map<String,Object>> family) throws Exception;
    /**
     * 通过不动产接口获取权利人信息
     * @return
     */
    public List<Map<String,Object>> queryOgligeeOfDataByIF(List<Map<String,Object>> family)
            throws Exception;
    /**
     * 通过不动产普通接口获取信息
     * @param info  查询的信息
     * @param urlDicType
     * @return
     * @throws Exception
     */
    public String queryDataOfBdcByIF(Map<String,Object> info,String urlDicType)
            throws Exception;
    /**
     * 通过不动产共用查询接口获取信息
     * @param info  查询的信息
     * @param urlDicType 为 entrusteUrl是不动产委托备案接口服务
     * announceUrl是不动产预告档案查询接口服务   contractUrl是房地产交易合同备案查询接口
     * @return    "error":接口异常；"05":查询无数据；其他数据正常；
     * @throws Exception
     */
    public AjaxJson queryAjaxJsonOfBdc(Map<String,Object> info, String urlDicType) ;
}
