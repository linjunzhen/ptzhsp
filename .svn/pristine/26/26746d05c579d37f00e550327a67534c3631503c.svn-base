/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 序号处理接口
 * 
 * @author Derek Zhang
 * @created 2015年10月7日 下午12:14:35
 */
@SuppressWarnings("rawtypes")
public interface SerialSequenceService extends BaseService {

    /**
     * 描述 返回4位序号，左补0
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午12:48:42
     * @param num
     * @return
     */
    public String leftfourzeoSequence(Integer num);

    /**
     * 描述 返回自然序列
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午12:48:42
     * @param num
     * @return
     */
    public String generalSequence(Integer num);

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2015年10月4日 下午2:03:22
     * @return
     */
    public List<Map<String, Object>> getSequenceTypeDicList(String sequenceType);
}
