/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 字典类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface DicTypeService extends BaseService {
    /**
     * 
     * 描述 根据字典类别ID删除掉数据
     * @author Flex Hu
     * @created 2014年9月19日 下午2:41:57
     * @param typeId
     */
    public void removeByTypeId(String typeId);
    /**
     * 
     * 描述 根据父亲ID获取类别数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId);
    /**
     * 
     * 描述 根据字典类别获取字典值
     * @author Water Guo
     * @created 2016年9月30日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> getListByJdbc(String tableName,String typeCode);
    /**
     * 
     * 描述 根据字典名称获取字典编码
     * @author Water Guo
     * @created 2016年12月07日 下午3:14:14
     * @param parentId
     * @return
     */
    public String getDicCode(String tableName,String dicName);
    /**
     * 
     * 描述 根据类别编码获取到类别
     * @author Flex Hu
     * @created 2014年10月21日 下午3:42:18
     * @param typeCode
     * @return
     */
    public Map<String,Object> getByTypeCode(String typeCode);
    /**
     * 
     * 描述 根据类别编码获取到类别
     * @author Flex Hu
     * @created 2014年10月21日 下午3:42:18
     * @param typeCode
     * @return
     */
    public Map<String,Object> getByTypeCode(String typeCode,String parentId);
    /**
     * 
     * 描述 根据类别编码获取类别数据
     * @author Flex Hu
     * @created 2014年10月22日 上午9:20:31
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByParentCode(String typeCode);
    /**
     * 
     * 描述 根据类别编码获取类别数据
     * @author Flex Hu
     * @created 2014年10月22日 上午9:20:31
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByParentCodeForSelect(String typeCode);
    /**
     * 
     * 描述 根据类别编码获取到数据列表
     * @author Flex Hu
     * @created 2014年10月22日 下午4:10:09
     * @param typeCodeSet
     * @return
     */
    public List<Map<String,Object>> findByCodeSet(Set<String> typeCodeSet);
    /**
     *
     * 描述 获取省份列表
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentCode
     * @return
     */
    public List<Map<String,Object>> findProvince(String parentCode);

    /**
     * 
     * 描述  根据父类编码和业务类型获取数据列表
     * @author Danto Huang
     * @created 2015-4-20 下午3:15:54
     * @param typeCode
     * @param busType
     * @return
     */
    public List<Map<String,Object>> findByParentCodeAndBusType(String typeCode,String busType);
    /**
     * 
     * 描述 获取字典类别的显示值
     * @author Flex Hu
     * @created 2015年4月29日 下午2:52:06
     * @param busCodes
     * @param typeCodes
     * @param itemNames
     * @return
     */
    public Map<String,String> getTextValues(String busCodes,String typeCodes,String itemNames);
}
