/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.zzhy.model.Robot;
import net.evecom.platform.zzhy.model.data.SubmitDataTable;
import net.evecom.platform.zzhy.service.CreatTaskInterService;
import net.evecom.platform.zzhy.service.SubmitTaskService;
import net.evecom.platform.zzhy.util.RobotFactory;
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 提交业务任务接口service
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("submitTaskService")
public class SubmitTaskServiceImpl extends BaseServiceImpl implements SubmitTaskService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CreatTaskInterService.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 所引入的dao
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 推送提交业务数据
     */
    @Override
    public void pushDataOfSubmit(SubmitDataTable submitDataTable) {
        //获取需要提交的数据
        List<Map<String, Object>> datas = getDataOfSubmitOfCompany(submitDataTable.getTableName()
                , submitDataTable.getBusRecordColName()
                , submitDataTable.getCurStepName());
        RobotUtils.pushDataExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (Map<String, Object> data : datas) {
                    //获取
                    Robot robot = submitDataTable.getRobotOfSubmit();
                    if (robot != null) {
                        Map<String, Object> jobParams = new HashMap<>();
                        jobParams.put("data", data);
                        jobParams.put("EXE_ID",StringUtil.getString(data.get("EXE_ID")));
                        jobParams.put("COMPANY_NAME",StringUtil.getString(data.get("COMPANY_NAME")));
                        //提交数据任务
                        boolean flag = robot.submitData(jobParams);
                        if (flag) {
                            //任务手动触发
                            flag = robot.execTask();
                            if (flag) {
                                //更新数据库为已推送状态
                                String companyId = StringUtil.getString(data.get(submitDataTable.getBusRecordColName()));
                                StringBuffer updateSql = new StringBuffer("update  ");
                                updateSql.append(submitDataTable.getRelTableName());
                                updateSql.append(" set  PUSH_STATUS_INTER=?  ");
                                updateSql.append(" where ").append(submitDataTable.getBusRecordColName()).append("=?");
                                dao.executeSql(updateSql.toString(), new Object[]{1, companyId});
                                saveLogOfCompanyName(data);
                                try {
                                    Thread.sleep(20 * 1000);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 内资合伙推送日志保存数据
     * @param data
     */
    private void saveLogOfCompanyName(Map<String,Object> data){
        String companyName=StringUtil.getString(data.get("COMPANY_NAME"));
        String exeId=StringUtil.getString(data.get("EXE_ID"));
        String logContent=String.format("推送开普云申报号为%s,公司名称为%s",exeId,companyName);
        Map<String,Object> person=new HashMap<>();
        person.put("PERSONNEL_NAME","公司秒批");
        person.put("USERNAME","商事秒批");
        person.put("PERSONNEL_ID","智能审批");
        sysLogService.saveLog(logContent,1,person);
    }
    /**
     * 获取推送提交的数据
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getDataOfSubmitOfCompany(String tableName, String pid, String curStepNames) {
        String newTableName = tableName;
        // 内资表单，将虚拟主表名替换真实主表名称
        if (tableName.equals("T_COMMERCIAL_DOMESTIC")) {
            newTableName = "T_COMMERCIAL_COMPANY";
        }
        // 外资表单，将虚拟主表名替换真实主表名称
        if (tableName.equals("T_COMMERCIAL_FOREIGN")) {
            newTableName = "T_COMMERCIAL_COMPANY";
        }
        StringBuffer sql = new StringBuffer("SELECT C.*,E.EXE_ID ");
        sql.append(" FROM JBPM6_EXECUTION E  ");
        sql.append(" JOIN ").append(newTableName).append(" C ON E.BUS_RECORDID=C.").append(pid);
        sql.append(" WHERE E.BUS_TABLENAME=? ");
        sql.append(" AND E.CUR_STEPNAMES=? ");
        sql.append(" AND C.PUSH_STATUS_INTER=? ");
        sql.append(" AND C.SSSBLX = 1 ");
        sql.append("  ORDER BY E.CREATE_TIME ASC ");
        PagingBean pagingBean=new PagingBean(0,4);
        List<Map<String, Object>> busRecords = dao.findBySql(sql.toString(),
                new Object[] { tableName, curStepNames, 0 }, pagingBean);
        encryptionService.listDecrypt(busRecords, newTableName);
        List<Map<String, Object>> datas = Lists.newArrayList();
        for (Map<String, Object> busRecord : busRecords) {
            String exeId = StringUtil.getString(busRecord.get("EXE_ID"));
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            busRecord.putAll(execution);
            Map<String, Object> data = Maps.newHashMap();
            for (Map.Entry<String, Object> entry : busRecord.entrySet()) {
                String key = entry.getKey();
                String value = StringUtil.getString(entry.getValue());
                if (key.indexOf("_JSON") > -1) {
                    data.put(key, JSON.parseObject(value, List.class));
                } else {
                    data.put(key, value);
                }
            }
            datas.add(data);
        }
        return datas;
    }
}
