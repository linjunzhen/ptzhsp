/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/***
 * 描述：监察字段，基本信息字段配置
 * @author Water Guo
 * @version 2.0
 * @date 2015-08-05
 */
public interface BusColumnBasicService extends BaseService{

    /**
     * 
     * @param filter
     * @return
     */
    public List<Map<String, Object>> datagrid(SqlFilter filter);

    /**
     * 描述 根据专项编码查询该专项所设置的基本信息字段
     * @author Water Guo
     * @created 2015-8-18 上午11:16:08
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> listByProcessCode(String processCode);

    /***
     * 描述（业务梳理）基本信息字段新增或修改
     * @author Water Guo
     * @created 2015-8-18 下午3:09:15
     * @param variables
     */
    public void saveOrUpdate(Map<String, Object> variables);

    /**
     * 描述 （业务变更）变更的保存
     * @author Water Guo
     * @created 2015-9-22 上午11:05:27
     * @param variables
     */
    public void changeSaveOrUpdate(Map<String, Object> variables);

    /***
     * 描述 删除基本信息字段配置
     * @author Water Guo
     * @created 2015-8-19 上午9:10:56
     * @param selectColNames
     */
    public void multDel(String selectColNames);

    /**
     * 描述 根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-8-28 上午11:28:21
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCode(String busCode,String applyId);

    /**
     * 描述 根据操作申报号，专项编码（过程编码）获取该专项（过程）的字段信息（数据源历史表）
     * @author Water Guo
     * @created 2015-9-9 下午2:42:05
     * @param appliyId
     * @param processCode
     * @param type
     * @param platCode
     * @return
     */
    public List<Map<String, Object>> listMaterialsColumn(String appliyId, String processCode, String type,
            String platCode);
    
    /**
     * 描述 根据操作申报号，专项编码（过程编码）获取该专项（过程）的字段信息（数据源变更表）
     * @author Water Guo
     * @created 2015-10-10 下午10:48:07
     * @param appliyId
     * @param processCode
     * @param type
     * @param platCode
     * @return
     */
    public List<Map<String, Object>> listMaterialsChangeColumn(String appliyId, String processCode, String type,
            String platCode);

    /***
     * 描述 确认提交基本信息业务梳理材料
     * @author Water Guo
     * @created 2015-9-16 下午4:17:00
     * @param id
     */
    public void submitColumn(String id);

    /**
     * 描述获取基本信息字段变更的列表
     * @author Water Guo
     * @created 2015-9-18 下午5:06:51
     * @param filter
     * @return
     */
    public List<Map<String, Object>> columnAlterDatagrid(SqlFilter filter);

    /**
     * (业务变更)根据专项编码和系统平台编码查询字段
     * @author Water Guo
     * @created 2015-9-22 上午10:44:06
     * @param processCode
     * @param platCode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> listChangeColumsByBusAndPlatCode(String processCode,
         String platCode,String applyId);

    /**
     * 描述 （变更业务）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-9-24 下午3:22:57
     * @param buscode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCodeChange(String buscode, String applyId);

    /**
     * 描述 检查当前的专项的基本信息是否已存在
     * @author Water Guo
     * @created 2015-10-16 上午9:47:04
     * @param busscode
     * @param platcode
     * @return
     */
    public Boolean check(String busscode, String platcode);

}
