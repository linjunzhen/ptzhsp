/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述:不动产全流程预购商品房
 *
 * @author Madison You
 * @created 2020/04/27 11:23
 */
public interface BdcYgspfService extends BaseService {

    /**
     * 描述:预购商品房抵押权预告登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 11:30:00
     * @param
     * @return
     */
    public void setYgspfdyqygdjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:预购商品房抵押权预告登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:15:00
     * @param
     * @return
     */
    public void setYgspfygdjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:预购商品房抵押权预告注销登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:21:00
     * @param
     * @return
     */
    public void setYgspfdyqygzxdjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:预购商品房抵预告注销登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:21:00
     * @param
     * @return
     */
    public void setYgspfygzxdjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:预购商品房抵押权预告变更登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:45:00
     * @param
     * @return
     */
    public void setYgspfdyqygdjbgData(Map<String, Map<String, Object>> args);

    /**
     * 描述:预购商品房预告登记变更数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:45:00
     * @param
     * @return
     */
    public void setYgspfygdjbgData(Map<String, Map<String, Object>> args);
    
    /**
     * 
     * 描述：保存审核意见到数据表
     * @author Rider Chen
     * @created 2020年8月16日 下午1:29:00
     * @param flowDatas
     * @return
     */
    public Map<String,Object> saveShyjData(Map<String,Object> flowDatas);
    
    /**
     * 
     * 描述：推送数据至FTP
     * @author Rider Chen
     * @created 2020年8月16日 下午3:16:58
     * @param map
     * @return
     */
    public Map<String,Object> pushFileToFtp(Map<String,Object> map);
    
    /**
     * 
     * 描述    预购商品房抵押预告登记业务材料数据初始化
     * @author Danto Huang
     * @created 2020年8月24日 下午2:52:52
     * @param flowVars
     * @return
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars);
    /**
     * 
     * 描述    生成预抵申请表
     * @author Danto Huang
     * @created 2020年8月25日 上午9:43:53
     * @param returnMap
     * @param path
     */
    public void generateSQB(Map<String, Object> returnMap,String path);
    
}
