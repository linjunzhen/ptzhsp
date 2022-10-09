/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年9月19日 下午3:11:08
 */
public interface RelatedRdgtApplyService extends BaseService {

    /**
     * 根据word模板内容占位符和设置，填充数据，并产生word文件
     * 供以客户端下载
     * @author Phil He
     * @created 2017-9-19 下午5:48:24
     * @param param
     * @param filepathString 模板文件路径
     * @param destpathString word文件生成保存路径
     */
    public void replaceWord(Map<String, Object> param, String filepathString, String destpathString);

    /**
     * 根据数据内容初始化word模板所需数据内容
     * @author Phil He
     * @created 2017-9-19 下午7:15:07
     * @param dataMap
     */
    public void initWordParams(Map<String, Object> dataMap);
}
