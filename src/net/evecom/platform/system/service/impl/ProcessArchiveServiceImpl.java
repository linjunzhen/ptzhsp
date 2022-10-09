/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.ProcessArchiveService;
import net.evecom.platform.zhsp.service.ServiceHandleService;

/**
 * 描述 数据归档
 * 
 * @author Reuben Bao
 * @created 2019年3月28日 上午10:26:57
 */
@SuppressWarnings("rawtypes")
@Service("processArchiveService")
public class ProcessArchiveServiceImpl extends BaseServiceImpl implements ProcessArchiveService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ProcessArchiveServiceImpl.class);

    /**
     * 描述 dao
     */
    @Resource
    private SysSendMsgDao dao;

    /**
     * 描述 字典
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述 服务管理
     */
    @Resource
    private ServiceHandleService serviceHandleService;

    /**
     * 描述 流程实例
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 描述 获取dao
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午10:25:45
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 定时器用流程归档
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午9:47:33
     */
    @Override
    public void processArchive() {
        String exeIdsStr = "";
        StringBuffer exeIds = new StringBuffer();
        // 查询字典表配置的天数前的流程数据
        List<Map<String, Object>> exectionList = serviceHandleService.getExecutionListByDicCode();
        if (exectionList != null && exectionList.size() > 0) {
            for (Map<String, Object> executionMap : exectionList) {
                exeIds.append("'").append(StringUtil.getValue(executionMap, "EXE_ID")).append("',");
            }
            exeIdsStr = exeIds.toString().substring(0, exeIds.toString().length() - 1);
            // 进行归档操作
            this.processArchiveByExeId(exeIdsStr);
        }
        // 归档成功后删除已归档的数据
        // 1、删除jbpm6_execution已归档数据
        this.serviceHandleService.deleteExecutionByIsFiled();
        // 2、删除jbpm6_task已归档数据
        this.serviceHandleService.deleteTaskByIsFiled();
        // 3、删除jbpm6_result已归档数据
        this.serviceHandleService.deleteResultByIsFiled();
    }

    /**
     * 描述 执行归档操作并删除已归档数据
     * 
     * @author Reuben Bao
     * @created 2019年8月15日 下午2:37:24
     * @param exeIds
     */
    public void processArchive(String exeIds) {
        // 进行归档操作
        this.processArchiveByExeId(exeIds);
        // 归档成功后删除已归档的数据
        // 1、删除jbpm6_execution已归档数据
        this.serviceHandleService.deleteExecutionByIsFiled();
        // 2、删除jbpm6_task已归档数据
        this.serviceHandleService.deleteTaskByIsFiled();
        // 3、删除jbpm6_result已归档数据
        this.serviceHandleService.deleteResultByIsFiled();
    }
    
    /**
     * 描述 删除已归档流程
     * 
     * @author Reuben Bao
     * @created 2019年8月16日 上午9:20:58
     */
    public void deleteFiledFlows() {
        // 归档成功后删除已归档的数据
        // 1、删除jbpm6_execution已归档数据
        this.serviceHandleService.deleteExecutionByIsFiled();
        // 2、删除jbpm6_task已归档数据
        this.serviceHandleService.deleteTaskByIsFiled();
        // 3、删除jbpm6_result已归档数据
        this.serviceHandleService.deleteResultByIsFiled();
    }

    /**
     * 描述 根据exeId做流程归档
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午3:06:14
     * @param exeIds
     */
    @SuppressWarnings("unchecked")
    public void processArchiveByExeId(String exeIds) {
        if (StringUtils.isNotEmpty(exeIds)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * from jbpm6_execution t where t.exe_id in ").append(" ( ").append(exeIds)
                    .append(" ) ");
            // 查询 jbpm6_execution归档
            List<Map<String, Object>> exectionList = this.dao.findBySql(sql.toString(), new Object[] {}, null);
            exectionList = encryptionService.listDecrypt(exectionList, "JBPM6_EXECUTION");
            this.setFiled(exectionList, "jbpm6_execution", "jbpm6_execution_evehis", "EXE_ID");
            sql.setLength(0);
            // 查询 jbpm6_task 归档
            sql.append(" select * from jbpm6_task t where t.exe_id in ").append(" ( ").append(exeIds).append(" ) ");
            List<Map<String, Object>> taskList = this.dao.findBySql(sql.toString(), new Object[] {}, null);
            this.setFiled(taskList, "jbpm6_task", "jbpm6_task_evehis", "TASK_ID");
            sql.setLength(0);
            // 查询 jbpm6_flow_result 归档
            sql.append(" select * from jbpm6_flow_result t where t.exe_id in ").append(" ( ").append(exeIds)
                    .append(" ) ");
            List<Map<String, Object>> resultList = this.dao.findBySql(sql.toString(), new Object[] {}, null);
            this.setFiled(resultList, "jbpm6_flow_result", "jbpm6_flow_result_evehis", "RESULT_ID");
        }
    }

    /**
     * 描述 归档后更新原数据表is_filed字段为1
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午3:26:39
     * @param dataList 保存的数据集合
     * @param busTableName 业务表
     * @param filedTableName 备份表
     * @param keyId 业务表主键
     */
    @SuppressWarnings("unchecked")
    public void setFiled(List<Map<String, Object>> dataList, String busTableName, String filedTableName, String keyId) {
        // 业务主键
        String entityId = "";
        StringBuffer sql = new StringBuffer();
        try {
            if (dataList != null && dataList.size() > 0) {
                for (Map<String, Object> dataMap : dataList) {
                    // 业务主键ID
                    String busRecordId = StringUtil.getValue(dataMap, keyId);
                    String exeId = StringUtil.getValue(dataMap, "EXE_ID");
                    // 保存
                    entityId = this.saveOrUpdateForAssignId(dataMap, filedTableName, busRecordId);
                    // 保存成功后更新归档表的IS_FILED字段为1，标识已完成数据归档
                    sql.append(" update ").append(busTableName).append(" set is_filed = '1' where exe_id = '")
                            .append(exeId).append("'");
                    this.dao.executeSql(sql.toString(), null);
                    sql.setLength(0);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("数据归档报错，业务主键为=" + entityId);
        }

    }
}
