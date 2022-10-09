/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import net.evecom.core.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述 打印附件表操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface PrintAttachService extends BaseService {
    /**
     * 
     * 描述 根据流程实例ID获取打印日志
     * @author Faker Li
     * @created 2016年1月14日 上午8:47:40
     * @param exeId
     * @return
     */
    List<Map<String, Object>> findByExeId(String exeId);

    /**
     * 
     * 描述：获取特殊材料
     * @author Water Guo
     * @created 2017-4-5 上午11:32:08
     * @param itemId
     * @return
     */
    String getTshj(String itemId);

    /**
     * 描述:获取特殊环节名称
     *
     * @author Madison You
     * @created 2020/11/3 15:32:00
     * @param
     * @return
     */
    String getTshjName(String itemId);

    /**
     * 
     * 描述  获取回填数据
     * @author Faker Li
     * @created 2016年4月20日 上午10:15:36
     * @param templateData
     * @param exeId
     */
    void getTemplateDataMapByExeId(Map<String, Object> templateData, String exeId);

    /**
     *
     * @param templateData
     * @param exeId
     */
    void getBackDataMapByItemId(Map<String, Object> templateData, String exeId);

    /**
     * 设置不动产查询数据
     * @param variable
     * @return
     */
    public Map<String, Object> setEstateQueryData(Map<String, Object> variable);

    /**
     * 描述 获取不动产权力人数据
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 上午11:26:42
     * @param busTableName
     * @param busRecordId
     * @param returnMap
     */
    public void getPowerPeopleList(String busTableName, String busRecordId, Map<String, Object> returnMap);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/4 9:20:00
     * @param 
     * @return 
     */
    public Set<String> findwsqcllb(String busId, String busTable, String itemId, String exeId);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/4 9:29:00
     * @param 
     * @return 
     */
    public Set<String> findysqcllb(String busId, String busTable);
}
