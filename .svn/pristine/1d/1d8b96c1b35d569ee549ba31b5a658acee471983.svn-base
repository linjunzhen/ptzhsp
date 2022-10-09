/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述   不动产历史遗留问题查询service
 * @author Allin Lin
 * @created 2021年1月14日 下午4:26:44
 */
public interface BdcLsylwtcxService extends BaseService{
    
    /**
     * 
     * 描述    不动产历史遗留问题数据回填
     * @author Allin Lin
     * @created 2021年1月14日 下午4:38:18
     * @param args
     */
    public void setLsylwtcxData(Map<String, Map<String, Object>> args);
    
    /**
     * 
     * 描述     批量导入人员绑定流程实例
     * @author Allin Lin
     * @created 2021年2月1日 下午4:24:35
     * @param flowVars
     * @return
     */
    public Map<String,Object> bindPersonExeId(Map<String, Object> flowVars);
     
    /**
     * 描述   批量收件人员独立生成办件并受理 
     * @author Allin Lin
     * @created 2021年2月1日 下午4:58:13
     * @param recordId
     * @return
     */
    public Map<String,Object> acceptPersonExe(String recordId);
    
    /**
     * 
     * 描述    加载不动产历史遗留问题批量件人员信息
     * @author Allin Lin
     * @created 2021年2月2日 上午10:58:13
     * @param filter
     * @return
     */
    public List<Map<String,Object>> loadPLperson(SqlFilter filter);

//    /**
//     * 描述:是否在资源局之后审批
//     *
//     * @author Madison You
//     * @created 2021/4/8 15:19:00
//     * @param
//     * @return
//     */
//    Boolean isAfterZfjApprove(String exeId);
}
