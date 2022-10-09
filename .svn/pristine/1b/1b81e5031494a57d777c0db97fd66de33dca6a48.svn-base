/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 文章操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ContentService extends BaseService {
    /**
     * 文章复制功能
     * 
     * @param ids
     *            文章IDs
     * @param moduleIds
     *            栏目IDs
     */
    public void multicopy(String[] ids, String[] moduleIds);

    /**
     * 文章剪切功能
     * 
     * @param ids
     *            文章IDs
     * @param moduleId
     *            栏目
     */
    public void paste(String[] ids, String moduleId);
    
    /**
     * 
     * 描述 列表
     * @author Rider Chen
     * @created 2015-11-25 下午03:15:37
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findfrontList(String page, String rows, String moduleId);
    
    /**
     * 
     * 描述 接收公示公告信息
     * @author Rider Chen
     * @created 2015-12-15 上午10:31:16
     * @param map
     * @return
     * @throws IOException
     */
    public boolean dataSink(Map<String, String> map) ;
    
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @return
     */
    public String saveOrUpdateContent(Map<String,Object> colValues,String tableName,String entityId);
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @return
     */
    public String saveOrUpdateContentHits(Map<String,Object> colValues,String tableName,String entityId);
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @param seqName:序列名称
     * @return
     */
    public String saveOrUpdateContent(Map<String,Object> colValues,String tableName,String entityId,String seqName);
    /**
     * 
     * 描述 根据表名称,列名称,列对应值移除掉记录
     * @author Flex Hu
     * @created 2014年9月7日 上午11:40:43
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void removeContent(String tableName,String colName,Object[] colValues);
    

    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public Map<String,Object> getByJdbcContent(String tableName,String[] colNames,Object[] colValues);
    
    /**
     * 获取到手机APP文章数据
     * 
     * @param sqlFilter
     * @return
     */
    public Map<String, Object> findContentAppBySql(String page, String rows,String moduleId);
    /**
     * 获取到手机APP文章附件数据
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findContentFileAppBySql(String ctid);
    
    /**
     * 
     * 描述 根据附件ID获取附件
     * @author Rider Chen
     * @created 2017年9月8日 上午11:24:13
     * @param attachFileIds
     * @return
     */
    public List<Map<String, Object>> findContentFileBySql(String attachFileIds);    
    /**
     * 
     * 描述 更新文章附加表文章ID字段
     * @author Rider Chen
     * @created 2017年9月8日 下午2:43:16
     * @param fileIds
     * @param contentId
     */
    public void updateFileToContentId(String fileIds, String contentId);

    /**
     * 描述:如果是常见问题模块，那么需要记录到便民服务下的常见问题中
     *
     * @author Madison You
     * @created 2019/10/18 9:56:00
     * @param
     * @return
     */
    void saveCommonProblem(Map<String,Object> variables);
}
