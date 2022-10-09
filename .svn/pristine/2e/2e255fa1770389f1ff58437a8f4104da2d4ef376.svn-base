/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 老年人优待证业务管理Service 
 * @author Bryce Zhang
 * @created 2017-5-15 上午9:06:51
 */
public interface OldAgeCardService extends BaseService{
    
    /**
     * 申请来源-现场办理
     */
    public static final int BUSINESS_SOURCE_WINDOW = 0;
    
    /**
     * 申请来源-五彩麒麟app
     */
    public static final int BUSINESS_SOURCE_WCQLAPP = 1;
    
    /**
     * 申请来源-岚岛网格app
     */
    public static final int BUSINESS_SOURCE_LDWGAPP = 2;
    
    /**
     * 描述 人像比对
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:07:38
     * @param name
     * @param idNum
     * @param imgBase64Code
     * @param requestIp
     * @return
     */
    public AjaxJson faceCompare(String name, String idNum, String imgBase64Code, String requestIp);
    
    /**
     * 描述 业务受理完毕的插件代码
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:08:08
     * @param flowDatas
     * @return
     */
    public Map<String, Object> doCompleteAffair(Map<String, Object> flowDatas);
    
    /**
     * 描述 根据sqlfilter，查询经我审批列表
     * @author Bryce Zhang
     * @created 2017-5-15 下午5:15:29
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 描述 根据sqlfilter，获取某用户办理的流程
     * @author Bryce Zhang
     * @created 2017-5-16 上午9:53:35
     * @param filter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findHandledByUser(SqlFilter filter, String userAccount);
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth(SqlFilter filter, SysUser loginUser);
    
    /**
     * 描述 根据身份证号和优待证类别，查询业务 
     * @author Bryce Zhang
     * @created 2017-5-26 上午11:25:25
     * @param idNum
     * @param cardType
     * @return
     */
    public Map<String, Object> getByIdnumAndCardType(String idNum, int cardType);
    
    /**
     * 描述 根据业务id，查询
     * @author Bryce Zhang
     * @created 2017-5-27 上午9:31:42
     * @param busId
     * @return
     */
    public Map<String, Object> getByBusId(String busId);
    
    /**
     * 描述 更新证件为注销
     * @author Bryce Zhang
     * @created 2017-5-27 下午5:19:51
     * @param busId
     * @param lostStateId
     * @param lostStateName
     * @param lostStatePath
     */
    public void updateUnregister(String busId, String lostStateId, String lostStateName, String lostStatePath);
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth4Exp(SqlFilter filter, SysUser loginUser);
    
    /**
     * 描述 老年人优待证业务数据报表生成
     * @author Bryce Zhang
     * @created 2017-5-29 下午8:42:22
     * @param list
     * @return
     */
    public HSSFWorkbook generateExcel(List<Map<String, Object>> list);
    
    /**
     * 描述 是否网上预审-流程决策插件代码
     * @author Bryce Zhang
     * @created 2017-8-1 下午5:39:57
     * @param flowVars
     * @return
     */
    public Set<String> getIsPreAudit(Map<String,Object> flowVars);
    
    /**
     * 描述 姓名身份证比对
     * @author Bryce Zhang
     * @created 2017-8-2 下午6:05:09
     * @param name
     * @param idNum
     * @param requestIp
     * @return
     */
    public AjaxJson idCompare(String name, String idNum, String requestIp);

}
