/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 字典类别操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface DicTypeDao extends BaseDao {
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
     * 描述  根据父类编码和业务类型获取数据列表
     * @author Water Guo
     * @created 2015-4-20 下午3:15:54
     * @param tableName
     * @param colNames
     * @return
     */
    public List<Map<String,Object>> getListByJdbc(String tableName,String typeCode);
    /**
     * 
     * 描述 根据大类编码和类别编码获取类别名称
     * @author Flex Hu
     * @created 2015年4月29日 下午2:46:50
     * @param busCode
     * @param typeCode
     * @return
     */
    public String getTypeName(String busCode,String typeCode);
    /**
     * 
     * 描述  根据字典名获取字典编码
     * @author Water Guo
     * @created 2015-4-20 下午3:15:54
     * @param tableName
     * @param colNames
     * @return
     */
    public String getDicCode(String tableName, String dicName);
    
}
