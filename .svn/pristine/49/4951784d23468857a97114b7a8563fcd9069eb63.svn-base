/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
/**
 * @author Scolder Lin
 *
 */
public interface UserProjectService extends BaseService {
    /**
     * 
     * 描述 请求数据 用户中心我的项目列表
     * @author Scolder Lin
     * @param page
     * @param rows
     * @param yhzh
     * @return
     */
    Map<String, Object> findfrontList(String page, String rows,String yhzh);
    
    /**
     * 
     * 描述：获取分类下的事项列表
     * @author Scolder Lin
     * @created 2019年6月17日 上午10:13:22
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述：获取项目事项列表信息
     * @author Scolder Lin
     * @created 2019年8月21日 上午11:13:12
     * @param projectCode
     * @return
     */
    public List<Map<String, Object>> findProjectItemList(String projectCode);
    /**
     * 删除项目事项信息
     * @param itemId
     * @param projectCode
     */
    public void deleteProjectItemInfo(String itemId, String projectCode);
    
    /**
     * 
     * 描述：获取事项Id列表信息
     * @author Scolder Lin
     * @created 2019年8月22日 上午17:13:12
     * @param categoryId
     * @return
     */
    public List<Map<String, Object>> findItemIdList(String categoryId);
    /**
     * 判断文件是否存在
     * @param projectFileId
     * @return
     */
    public boolean findFileIsExist(String projectFileId);
}
