/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年4月13日 下午1:58:41
 */
public interface EsuperResDao extends BaseDao {
    /**
     * 
     * 描述 根据实例ID和数据类型判断是否存在记录
     * @author Flex Hu
     * @created 2016年4月13日 下午2:55:26
     * @param exeId
     * @param dataType
     * @return
     */
    public boolean isExists(String exeId,String dataType);
    /**
     * 
     * 描述 判断数据是否存在
     * @author Flex Hu
     * @created 2016年4月14日 上午10:46:08
     * @param exeId
     * @param taskId
     * @param dataType
     * @return
     */
    public boolean isExists(String exeId,String taskId,String dataType);
    /**
     * 
     * 描述 获取过程ID
     * @author Flex Hu
     * @created 2016年5月4日 下午4:50:10
     * @param nodeName
     * @param itemCode
     * @return
     */
    public String getProcessId(String nodeName,String itemCode);
}
