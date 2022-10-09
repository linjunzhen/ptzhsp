/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年4月13日 下午1:59:49
 */
public interface EsuperResService extends BaseService {
    /**
     * 
     * 描述 保存监察数据
     * @author Flex Hu
     * @created 2016年4月13日 下午2:12:44
     * @param flowVars
     * @return
     */
    public Map<String,Object> saveEsuperRes(Map<String,Object> flowVars);
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
    /**
     * 消息类型:基本信息
     */
    public static final String MSG_NAME_BASEDATA = "basicDataSubmit";
    /**
     * 消息类型:监测信息
     */
    public static final String MSG_NAME_SUPERVIOSN ="supervisionDataSubmit";
    /**
     * 专项编码的头部
     */
    public static final String ITEM_HEAD = "PTZH";
    /**
     * 缺省状态
     */
    public static final String DEFAULT_STATUS = "000";
}
