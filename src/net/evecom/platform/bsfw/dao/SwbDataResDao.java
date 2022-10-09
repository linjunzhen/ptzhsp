/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月28日 下午4:20:49
 */
public interface SwbDataResDao extends BaseDao{
    /**
     * 
     * 描述 是否保存过办件信息类型的指令数据
     * @author Flex Hu
     * @created 2015年12月28日 下午5:04:13
     * @param exeId
     * @return
     */
    public boolean isHaveSaveBjxxInfo(String exeId);
}
