/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service;

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
public interface FdaDicTypeService extends BaseService {
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
     * 描述 根据类别编码获取到数据列表
     * @author Flex Hu
     * @created 2014年10月22日 下午4:10:09
     * @param typeCodeSet
     * @return
     */
    public List<Map<String,Object>> findByCodeSet(Set<String> typeCodeSet);

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
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年5月30日 上午9:34:36
     * @param queryParamsJson
     * @return
     */
    public List<Map<String,Object>> findList(String queryParamsJson);
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年5月30日 上午9:34:36
     * @param queryParamsJson
     * @return
     */
    public List<Map<String,Object>> findYearsList(String queryParamsJson);
    /**
     * 
     * 描述 获取福建区划数据
     * @author Flex Hu
     * @created 2016年6月13日 下午6:38:26
     * @return
     */
    public List<Map<String,Object>> findFjDatas();
    /**
     * 根据孩子类别IDS获取上级类别信息
     * @param childTypeIds
     * @return
     */
    public Map<String,Object> getParentInfo(String childTypeIds);
    /**
     * 根据类别编码获取类别IDS
     * @param typeCodes
     * @return
     */
    public String getTypeIds(String typeCodes);
    /**
     * 获取城市和地区编码
     * @param orignCodes
     * @return
     */
    public Map<String,String> getCityAndAreaCode(String orignCodes);
    /**
     * 描述 根据父亲编码获取所有的子数据
     * @author Faker Li
     * @created 2016年8月4日 上午11:25:45
     * @param typeCode
     * @return
     */
    public List<Map<String, Object>> getAllChildList(String typeCode);
    /**
     * 获取字典类别树形JSON
     * @return
     */
    public String getDicTypeTreeJson();
    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年4月17日 下午4:05:11
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId);
}
