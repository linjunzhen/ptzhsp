/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.encryption.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年5月12日 上午9:40:23
 */
public interface EncryptionService extends BaseService {

    /**
     * 
     * 描述    加密表配置树查询
     * @author Danto Huang
     * @created 2020年5月12日 上午10:32:48
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findConfigTableList(String parentId);
    /**
     * 
     * 描述    根据加密表配置id删除
     * @author Danto Huang
     * @created 2020年5月12日 上午10:53:59
     * @param tableId
     */
    public void removeByTableId(String tableId);
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月12日 下午3:15:42
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findConfigListBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述    查询表待加密记录
     * @author Danto Huang
     * @created 2020年5月21日 上午10:25:13
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findNeedEncryptionDataByTable(SqlFilter filter);
    /**
     * 
     * 描述    表加密
     * @author Danto Huang
     * @created 2020年5月13日 下午2:12:19
     * @param selectColNames
     * @param configId
     */
    public void tableEncryption(String selectColNames,String configId);
    /**
     * 
     * 描述    List数据解密
     * @author Danto Huang
     * @created 2020年5月19日 下午3:23:40
     * @param list
     * @param tableName
     * @return
     */
    public List<Map<String,Object>> listDecrypt(List<Map<String,Object>> list,String tableName);
    /**
     * 
     * 描述    Map数据解密
     * @author Danto Huang
     * @created 2020年5月19日 下午3:23:54
     * @param map
     * @param tableName
     * @return
     */
    public Map<String,Object> mapDecrypt(Map<String,Object> map,String tableName);
}
