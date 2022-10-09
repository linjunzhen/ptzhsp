/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述:凯特签章
 *
 * @author Madison You
 * @created 2020年11月2日 10:25
 */
public interface KtStampService extends BaseService {

    /**
     * 描述:获取签章管理列表数据
     *
     * @author Madison You
     * @created 2020/11/2 10:30:00
     * @param
     * @return
     */
    List<Map<String, Object>> getStampManageList(SqlFilter filter);

    /**
     * 描述:自动签章方法
     *
     * @author Madison You
     * @created 2020/11/4 9:53:00
     * @param
     * @return
     */
    public void ktAutoStamp(Map<String, Object> stampMap, String oldStampFilePath, String newStampFilePath);

    /**
     * 描述:签章事项限制
     *
     * @author Madison You
     * @created 2020/11/5 10:03:00
     * @param
     * @return
     */
    boolean stampItemLimit(Map<String, Object> stampMap,Map<String,Object> itemMap);

    /**
     * 描述:签章环节限制
     *
     * @author Madison You
     * @created 2020/11/5 10:18:00
     * @param
     * @return
     */
    boolean stampNodeLimit(Map<String, Object> stampMap, Map<String, Object> exeMap);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/9 16:36:00
     * @param typeCode 字典编码
     * @param value 字段值
     * @return
     */
    public String getExcelSelectField(String typeCode, String value);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 11:27:00
     * @param
     * @return
     */
    public String getExcelSelectField(String typeCode, String value , String defaultValue);

    /**
     * 描述:获取签章所需要的模板ID
     *
     * @author Madison You
     * @created 2020/11/18 10:18:00
     * @param
     * @return
     */
    public String getSignFileId(Map<String, Object> exeMap, Map<String, Object> stampMap);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/18 19:51:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findByList(String busTableName,String busRecordId,String attachKey);
}
