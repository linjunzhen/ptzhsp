/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 医保业务通用操作-实现类
 * @author Allin Lin
 * @created 2019年10月22日 上午9:19:55
 */
public interface YbCommonService extends BaseService{
    
    /**
     * 描述    医保通用查询接口（GET请求）
     * @author Allin Lin
     * @created 2019年10月22日 上午9:22:42
     * @param info 查询的信息（参数）
     * @param urlDicType 医保相关接口配置的字典编码
     * @return  200 接口成功；其他接口失败
     */
    public AjaxJson queryAjaxJsonOfYb(Map<String, Object>info,String urlDicType);
    
   /**
    * 描述    医保通用查询接口获取数据
    * @author Allin Lin
    * @created 2019年10月22日 上午9:50:27
    * @param info 查询的信息（参数）
    * @param urlDicType 医保相关接口配置的字典编码
    * @return 200 接口成功；其他接口失败
    * @throws Exception
    */
    public  Map<String, Object> queryDataOfYb(Map<String,Object> info,String urlDicType)
            throws Exception;
    
    /**
     * 
     * 描述    获取医保接口授权码（token值）
     * @author Danto Huang
     * @created 2019年11月13日 上午10:58:42
     * @return
     */
    public Map<String,Object> getToken();
    
    /**
     * 描述   医保校验、推送接口（POST请求）
     * @author Allin Lin
     * @created 2020年1月7日 上午9:27:42
     * @param info
     * @param urlDicType
     * @return
     */
    public AjaxJson pushInfoOfYb(Map<String, Object>info,String urlDicType);
    
    /** 
     * 描述     医保校验、推送数据
     * @author Allin Lin
     * @created 2020年1月7日 上午10:51:28
     * @param info
     * @param urlDicType
     * @return
     */
    public Map<String, Object> pushDataOfYb(Map<String,Object> info,String urlDicType);
    /**
     * 
     * 描述    导入批量办件人员
     * @author Danto Huang
     * @created 2020年12月31日 下午4:00:27
     * @param excelPath
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> impPersonInfo(String excelPath,String exeId,String impTableName) throws Exception;
    /**
     * 
     * 描述    批量导入人员绑定流程实例
     * @author Danto Huang
     * @created 2021年1月4日 上午9:10:59
     * @param flowVars
     * @return
     */
    public Map<String,Object> bindPersonExeId(Map<String, Object> flowVars);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年1月4日 上午10:54:08
     * @param filter
     * @return
     */
    public List<Map<String,Object>> loadPLperson(SqlFilter filter);
    /**
     * 
     * 描述    批量收件人员独立生成办件并受理
     * @author Danto Huang
     * @created 2021年1月4日 下午3:18:07
     * @param recordId
     * @return
     */
    public Map<String,Object> acceptPersonExe(String recordId);
}
