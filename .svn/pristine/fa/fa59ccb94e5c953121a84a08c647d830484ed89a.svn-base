/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 不动产抵押权登记操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface BdcFwzldjbaService extends BaseService {

    /**
     * 
     * 描述 一审数据回填
     * 
     * @author Roger Li
     * @created 2019年12月26日 上午10:46:02
     * @param args
     */
    public void setRedrawData(Map<String, Map<String, Object>> args);
    
    
    /**
     * 
     * @Description 初始化业务数据
     * @author Luffy Cai
     * @date 2020年8月25日
     * @param returnMap
     * @param flowVars  void
     */
    public void initGenValue(Map<String, Object> returnMap,Map<String, Object> flowVars);

    /**
     * 描述:查找分户办件
     *
     * @author Madison You
     * @created 2020/8/26 14:40:00
     * @param
     * @return
     */
    List<Map<String, Object>> findBdcfhData(String bdcdyh);
    
    /**
     * 描述   设置抵押权首次登记转本业务数据
     * @author Allin Lin
     * @created 2020年9月24日 下午5:28:41
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    public  void setDyqscdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution);
    
    /** 
     * 描述    回填银行申请表-抵押人字段信息
     * @author Allin Lin
     * @created 2020年9月24日 下午5:49:38
     * @param returnMap
     * @param busInfo
     * @param executions
     */
    public void initYhDjdyrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution);
    
    
    /**
     * 
     * 描述    抵押权首次登记（以及转本登记）业务数据回填
     * @author Allin Lin
     * @created 2020年12月8日 上午10:47:55
     * @param args
     */
    public void setDyqscdjData(Map<String, Map<String, Object>> args);
    
    /**
     * 
     * 描述    （抵押权首次登记）银行申请表签章
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:43
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bankSign(Map<String, Object> flowVars,Map<String, Object> returnMap) throws Exception;


    
     /**
     * 描述  打印模板数据回填
     * @author Yanisin Shi
     * @param templateData
     * @param exeId
     * create time 2021年8月6日
     */
     
    public void getTemplateDataMapByExeId(Map<String, Object> templateData, String exeId);

 /**
  * 
  * 描述       设置首次登记模板数据
  * @author Yanisin Shi
  * @param args
  * create time 2021年8月17日
  */
    public void setBdcFwzldjData(Map<String, Map<String, Object>> args);
    
    
    /**
     * 描述:初始化审批表字段
     *
     * @author Madison You
     * @created 2020/4/27 8:43:00
     * @param
     * @return
     */
    public void bdcFwzldjInitSpbVariables(Map<String, Object> returnMap);
    /**
     * 
     * 描述  后置事件查找业务数据
     * @author Yanisin Shi
     * @param itemcode
     * @param type
     * @param busId
     * @return
     * create time 2021年8月18日
     */
    public Map<String, Object> getSubRecordInfo(String itemcode, String type, String busId);
    
    /**
     * 
     * 描述      自定义流程保存业务数据方法
     * @author Yanisin Shi
     * @param flowDatas
     * @return
     * create time 2021年8月20日
     */
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) ; 
    /**
     * 
     * 描述 获取人员列表
     * @param ids
     * @return
     */
    public List<Map<String,Object>> findListForResult(String YW_ID);


    
     /**
     * 描述
     * @author Yanisin Shi
     * @param returnMap
     * @param busInfo
     * @param execution
     * create time 2021年8月21日
     */
     
    public void setFwzldjData(Map<String, Object> returnMap, Map<String, Object> busInfo, Map<String, Object> execution);


    
     /**
     * 描述
     * @author Yanisin Shi
     * @param filter
     * @return
     * create time 2021年8月23日
     */
     
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);


    
     /**
     * 描述
     * @author Yanisin Shi
     * @param returnMap
     * @param exeId
     * create time 2021年8月24日
     */
     
    public void getSqbTemplateDataMapByExeId(Map<String, Object> returnMap, String exeId);
    
}
