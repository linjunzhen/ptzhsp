/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.service;


import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月1日 上午11:03:45
 */
public interface WebSiteService extends BaseService {
    /**
     * 
     * 描述 根据流程实例设置当前环节的名称和审核人数据
     * @author Flex Hu
     * @created 2015年12月1日 上午11:07:07
     * @param flowExe
     * @param eFlowObj
     * @return
     */
    public Map<String,Object> getFlowCurNodeAndOper(Map<String,Object> flowExe,Map<String,Object> eFlowObj);

    /**
     * 描述:获取环土局行业项目类别
     *
     * @author Madison You
     * @created 2019/8/19 9:52:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getHTIndustry();

    /**
     * 描述: 获取环土局项目列表
     *
     * @author Madison You
     * @created 2019/8/19 11:08:00
     * @param
     * @return
     */
    List<Map<String,Object>> getHTProject(HttpServletRequest request);

    /**
     * 描述:获取环土局项目列表详情
     *
     * @author Madison You
     * @created 2019/8/19 12:41:00
     * @param
     * @return
     */
    Map<String, Object> getHTProjectDetail(HttpServletRequest request);

    /**
     * 描述:模块内容查询数据
     *
     * @author Madison You
     * @created 2019/10/17 17:17:00
     * @param
     * @return
     */
    Map<String, Object> findModuleSearchData(SqlFilter filter);
}
