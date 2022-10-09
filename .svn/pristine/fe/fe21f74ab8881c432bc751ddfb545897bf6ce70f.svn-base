/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.dao;

import net.evecom.core.dao.BaseDao;

import java.util.Map;

/**
 * 描述 写信求诉操作dao
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface LetterDao extends BaseDao {
    /**
     * 
     * 描述 获取信件编号
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午9:46:31
     * @param letterVars
     * @return
     */
    public String getNextLetterCode(Map<String, Object> letterVars);

    /**
     *
     * @param replyFlag
     * @return
     */
    public int getAllSqNum(String replyFlag);
}
