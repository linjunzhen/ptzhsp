/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 不动产登记操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface BdcExecutionService extends BaseService {
    /**
     * 获取不动产待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBdcNeedMeHandle(SqlFilter sqlFilter,boolean isHaveHandUp);
    
    /**
     * 
     * 描述  获取不动产被某个用户办理的流程
     * @author Rider Chen
     * @created 2017年3月4日 下午5:47:11
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findBdcHandledByUser(SqlFilter sqlFilter,String userAccount);
    
    /**
     * 
     * 描述：
     * @author Rider Chen
     * @created 2019年2月22日 上午11:10:08
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);
    
    /**
     * 
     * 描述 异常办件统计
     * 
     * @author Water Guo
     * @created 2016年12月26日 上午10:19:14
     * @param params
     */
    public List<Map<String, Object>> getAdnormalStatist(SqlFilter filter);
    
    /**
     * 
     * 描述 统计管理层报表
     * 
     * @author Water Guo
     * @created 2016年12月20日 上午10:19:14
     * @param params
     */
    public List<Map<String, Object>> getIndPerDataStatist(SqlFilter filter);
    
    
    /**
     * 
     * 描述： 办件总数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> countsDetailData(SqlFilter filter);
    /**
     * 
     * 描述： 办结总数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> banJSDetailData(SqlFilter filter);
    /**
     * 
     * 描述：在办数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> zBSDetailData(SqlFilter filter);
    /**
     * 
     * 描述： 退件数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> tJSDetailData(SqlFilter filter);
    
    /**
     * 
     * 描述：判断补发不动产登记证明类型（受理）
     * @author Rider Chen
     * @created 2019年10月31日 上午9:15:52
     * @param flowVars
     * @return
     */
    public Set<String> isBfbdcdjzmSlType(Map<String, Object> flowVars);    /**
     * 
     * 描述：判断补发不动产登记证明类型（复审）
     * @author Rider Chen
     * @created 2019年10月31日 上午9:15:52
     * @param flowVars
     * @return
     */
    public Set<String> isBfbdcdjzmFsType(Map<String, Object> flowVars);

    /**
     * 描述 获取配置的银行需要受理的业务清单
     * @author Keravon Feng
     * @created 2020年1月9日 下午3:06:35
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter filter);

    /**
     * 描述:开发商业务受理数据列表
     *
     * @author Madison You
     * @created 2020/8/13 11:11:00
     * @param
     * @return
     */
    public List<Map<String, Object>> findByKfsywslListSqlFilter(SqlFilter filter);
    
    
   /**
    * 
    * 描述： 设置办件ID至取号信息表
    * @author Rider Chen
    * @created 2020年8月19日 下午5:10:35
    * @param flowVars
    * @return
    */
    public Map<String,Object> setCkbsQueuerecordToExeId(Map<String,Object> flowVars);

    /**
     * 描述:不动产受理通知单表格数据
     *
     * @author Madison You
     * @created 2020/11/3 14:35:00
     * @param
     * @return
     */
    public Map<String,Object> bdcSltzdTableData(Map<String,Map<String,Object>> args);

    /**
     * 描述:获取测绘公司列表()
     *
     * @author Madison You
     * @created 2020/12/16 10:36:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getDrawOrgList(String typeCode);

    /**
     * 描述:测绘公司绑定
     *
     * @author Madison You
     * @created 2020/12/16 17:07:00
     * @param
     * @return
     */
    public Map<String, Object> bindDrawOrg(Map<String,Object> flowVars);

    /**
     * 描述:是否首次测绘
     *
     * @author Madison You
     * @created 2020/12/17 16:32:00
     * @param
     * @return
     */
    public Set<String> isFirstDraw(Map<String, Object> flowVars);

}
