/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 不动产登记相关接口Service
 * 
 * @author Scolder Lin
 * @created 2021年6月30日 下午11:33:45
 */
@SuppressWarnings("rawtypes")
public interface BdcRegisterInterfaceService extends BaseService {
    /**
     * 获取给定主表的数据
     * 
     * @created 2021/6/30 11:54:00
     * @author Scolder Lin
     * @param exeId
     * @param BUS_TABLENAME 主表名称
     * @return
     */
    Map<String, Object> getSourceDataByExeId(String exeId, String BUS_TABLENAME);
    /**
     * 查询未推送的外网申请数据上报数据
     * @return
     */
    List<Map<String, Object>> findExtranetApplyDataList();
    /**
     * 查询未推送的外网办结数据上报数据
     * @return
     */
    List<Map<String, Object>> findExtranetConcludeDataList();
    /**
     * 保存外网申请数据
     * 
     * @param variables 参数值
     * @return
     */
    public void appayDataStorage(Map<String, Object> variables);
    /**
     * 保存外网办结数据
     * 
     * @param variables 参数值
     * @return
     */
    public void concludeDataStorage(Map<String, Object> variables);
    /**
     * 办理数据推送到省外网
     * @param log
     * @throws IOException
     */
    public void extranetApplyReport(Log log) throws IOException;
    /**
     * 办结数据推送到省外网
     * @param log
     * @throws IOException
     */
    public void extranetConcludeReport(Log log) throws IOException;
    /**
     * 国有建设用地使用权及房屋所有权转移登记-全流程  办结后置事件
     * 
     * @author Scolder Lin
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveAfterBusData(Map<String, Object> flowDatas) throws Exception;
    /**
     * 保存推送到省网的办结数据（后置事件）
     * 
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveConcludeDataStorage(Map<String, Object> flowDatas);
}
