/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 下午3:18:51
 */
public interface ExtraDictionaryService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:27:20
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId,String dicCode);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:29:49
     * @param typeId
     * @return
     */
    public int getMaxSn(String typeId);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午4:31:17
     * @param dicIds
     */
    public void updateSn(String[] dicIds);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年4月2日 上午11:21:06
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> getDictonaryByTypeCode(String typeCode);
    /**
     * 
     * 根据类别编码获取字典数据
     * @author Danto Huang
     * @created 2021年4月7日 上午9:19:46
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String typeCode);
    
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
}
