/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 字典信息操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface DictionaryDao extends BaseDao {
    /**
     * 
     * 描述 根据类别编码获取列表信息
     * @author Flex Hu
     * @created 2014年9月19日 下午5:59:32
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByTypeCode(String typeCode);
    /**
     * 
     * 描述 获取某个字典类别下字典的最大排序
     * @author Flex Hu
     * @created 2014年10月3日 上午11:54:19
     * @param typeId
     * @return
     */
    public int getMaxSn(String typeId);
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] dicIds);
    /**
     * 
     * 描述 根据类别编码和使用位置获取列表信息
     * @author Danto Huang
     * @created 2014-12-12 下午5:59:15
     * @param typeCode
     * @param doType
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeAndDoType(String typeCode,String doType);
    
    /**
     * 
     * 描述 查询字典届次列表
     * @author Roy Li
     * @created 2015-1-8 下午4:39:37
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findPeriodByTypeCode(String typeCode);
    /**
     * 
     * 描述 根据类别编码和排序类型获取字典数据
     * @author Flex Hu
     * @created 2015年3月8日 上午8:40:10
     * @param typeCode
     * @param orderType
     * @return
     */
    public List<Map<String,Object>> findList(String typeCode,String orderType);
    
    /**
     * 
     * 描述 获取派出所字典数据
     * @author Flex Hu
     * @created 2015年3月8日 上午8:40:10
     * @param typeCode
     * @param orderType
     * @param subParentCode
     * @return
     */
    public List<Map<String,Object>> findPcs(String typeCode,String orderType,String subParentCode);
    /**
     * 
     * 描述 根据类别编码和字典编码获取到字典
     * @author Flex Hu
     * @created 2015年3月8日 下午12:05:03
     * @param typeCode
     * @param dicCode
     * @return
     */
    public Map<String,Object> get(String typeCode,String dicCode);
    /**
     * 
     * 描述 判断某个字典类别下是否存在相同编码的字典
     * @author Flex Hu
     * @created 2015年3月28日 上午9:34:32
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId,String dicCode);
    /**
     * 
     * 描述 获取字典的显示值
     * @author Flex Hu
     * @created 2015年4月28日 下午4:16:52
     * @param busCode
     * @param typeCode
     * @param itemCode
     * @return
     */
    public String getTextValue(String busCode,String typeCode,String dicCode);
    /**
     * 
     * 描述 获取字典名称,如果传入多个dicCode,那么字典名称将会以逗号拼接返回
     * @author Flex Hu
     * @created 2015年9月1日 上午9:12:25
     * @param typeCode
     * @param dicCodes
     * @return
     */
    public String getDicNames(String typeCode,String dicCodes);
    /**
     * 
     * 描述 根据类别编码和字典名称获取字典编码
     * @author Flex Hu
     * @created 2015年10月19日 下午2:42:29
     * @param typeCode
     * @param dicName
     * @return
     */
    public String getDicCode(String typeCode,String dicName);
}
