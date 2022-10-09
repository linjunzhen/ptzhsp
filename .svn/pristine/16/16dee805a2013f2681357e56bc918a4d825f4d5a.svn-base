/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-12 上午10:00:25
 */
public interface BillRightService extends BaseService {

    /**
     * 
     * 描述   获取权利清单（目录）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillCatalogByFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述   删除目录清单
     * @author Danto Huang
     * @created 2016-9-13 上午9:16:53
     * @param selectColNames
     */
    public String removeCatalog(String selectColNames);

    /**
     * 
     * 描述   获取权利清单（事项）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillItemByFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述   子项数量更新
     * @author Danto Huang
     * @created 2016-9-13 下午1:57:52
     * @param billCatalogId
     */
    public void updateItemNum(String billCatalogId);
    /**
     * 
     * 描述   子项删除
     * @author Danto Huang
     * @created 2016-9-13 下午2:09:42
     * @param selectColNames
     */
    public String removeItems(String selectColNames);
    /**
     * 
     * 描述   获取未绑定权利清单（目录）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillCatalogForBind(SqlFilter sqlFilter);
    /**
     * 
     * 描述   获取未绑定权利清单（事项）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillItemForBind(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述 前台权责清单列表
     * @author Rider Chen
     * @created 2016年10月8日 上午9:39:45
     * @param page
     * @param rows
     * @param keyword
     * @param departId
     * @return
     */
    public Map<String, Object> findfrontQzqdList(String page, String rows,
            String keyword, String dicCodes, String departId, String sfzx);
    /**
     * 
     * 描述 前台入驻省网目录列表
     * @author Rider Chen
     * @created 2016年10月8日 上午9:39:45
     * @param page
     * @param rows
     * @param keyword
     * @param departId
     * @return
     */
    public Map<String, Object> findfrontRzswmlList(String page, String rows,
            String keyword, String dicCodes, String departId, String sfzx);

    /**
     * 新版权责清单前台展示
     * @param page
     * @param rows
     * @param keyword
     * @param dicCodes
     * @param departId
     * @param sfzx
     * @return
     */
    public Map<String, Object> findRightlList(String page, String rows, String keyword, String dicCodes,
                                              String departId, String sfzx);
    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年10月8日 上午9:45:36
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBillItemByList(String catalogId,String isZx,String keyword);
   /**
    * 
    * 描述 获取办事总数
    * @author Rider Chen
    * @created 2016年10月9日 上午8:53:33
    * @param keyword
    * @param dicCodes
    * @param departId
    * @return
    */
    public Map<String, Object> findfrontCount( String keyword,String dicCodes, String departId,String isOn);

    /**
     * 获取权责清单详细信息
     * @param rightId
     * @return
     */
    public Map<String,Object> getRightDetail(String rightId);
    /**
     * 
     * 描述 获取办事总数
     * @author Rider Chen
     * @created 2016年10月9日 上午8:53:33
     * @param keyword
     * @param dicCodes
     * @param departId
     * @return
     */
    public Map<String, Object> findfrontCountNew(String keyword, String dicCodes, String departId, String isOn);
    
    

    /**
     * 
     * 描述   根据权责清单目录ID获取权利清单（事项）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillItemById(String id);
}
