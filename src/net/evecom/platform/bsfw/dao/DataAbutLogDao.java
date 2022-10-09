/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao;

import java.util.List;
import net.evecom.core.dao.BaseDao;

/**
 * 描述 对接日志操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface DataAbutLogDao extends BaseDao {
    /**
     * 
     * 描述 获取日志ID
     * @author Flex Hu
     * @created 2015年10月13日 下午3:48:58
     * @param abutCode
     * @param busIdValue
     * @return
     */
    public String getAbutLogId(String abutCode,String busIdValue);
}
