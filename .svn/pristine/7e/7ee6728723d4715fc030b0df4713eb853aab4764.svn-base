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
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.zzhy.model.Robot;
import net.evecom.platform.zzhy.model.data.SubmitDataTable;
import net.evecom.platform.zzhy.model.data.SubmitDataTableFactory;
import net.evecom.platform.zzhy.service.CreatTaskInterService;
import net.evecom.platform.zzhy.service.SubmitTaskService;
import net.evecom.platform.zzhy.util.RobotFactory;
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述 创建任务接口service
 * 只有一天的时间，没时间封装
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("creatTaskInterService")
public class CreatTaskInterServiceImpl extends BaseServiceImpl implements CreatTaskInterService {
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
     * 所引入的dao
     */
    @Resource
    private SubmitTaskService submitTaskService;
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
     *
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 推送名称查询数据
     */
    @Override
    @SuppressWarnings("unchecked")
    public void pushDataOfQueryCompanyName() {
        //获取需要查重的公司名(状态1 内资)
        List<Map<String, Object>> companyNames = findNeedPushCompanyName("1");
        RobotUtils.checkNameExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (Map<String, Object> companyName : companyNames) {
                    //获取
                    Robot robot = RobotFactory.getRobotOfQuery();
                    if (robot != null) {
                        //提交数据任务
                        boolean flag = robot.submitData(companyName);
                        if (flag) {
                            //任务手动触发
                            flag = robot.execTask();
                            if (flag) {
                                //更新数据库为已推送状态
                                companyName.put("SEND_STATUS", "1");
                                dao.saveOrUpdate(companyName, "T_COMMERCIAL_COMPANYNAME",
                                        StringUtil.getString(companyName.get("ID")));
                                try {
                                    Thread.sleep(10 * 1000);
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
     * 推送提交数据
     */
    @Override
    public void pushDataOfSubmit(){
        //内资有限公司
        SubmitDataTable yxData= SubmitDataTableFactory.getYxData();
        submitTaskService.pushDataOfSubmit(yxData);
        //内资合伙
        SubmitDataTable hhData=SubmitDataTableFactory.getHhData();
        submitTaskService.pushDataOfSubmit(hhData);
    }

    /**
     * 获取需要查重的公司名
     * @return
     */
     private List<Map<String,Object>> findNeedPushCompanyName(String nameType){
         StringBuffer sql=new StringBuffer("SELECT ID,WORD_NUM  ");
         sql.append("FROM T_COMMERCIAL_COMPANYNAME  C ");
         sql.append(" where c.send_status='0'  and c.name_type=? ");
         sql.append(" order by c.CREATE_TIME asc ");
         PagingBean pagingBean=new PagingBean(0,5);
         List<Map<String,Object>> companyNames=dao.findBySql(sql.toString(),new Object[]{nameType},pagingBean);
         return companyNames;
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
        sql.append(" AND C.SBMC NOT LIKE '%测试%' ");//新增不推送测试数据进入内网
        sql.append(" AND C.IS_TEST=0 ");//新增只推送不在测试阶段的数据进入内网
        sql.append("  ORDER BY E.CREATE_TIME ASC ");
        String pushNum=StringUtil.getString(dictionaryService.get("robotConfig", "pushNum").get("DIC_DESC"));
        PagingBean pagingBean=new PagingBean(0,Integer.parseInt(pushNum));
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

    @SuppressWarnings("unchecked")
    @Override
    public void pushDataOfQueryCompanyNameToGt() {
        // TODO Auto-generated method stub
        log.info("----------调用个体名称查重接口开始----------");
        //获取需要查重的公司名(状态2 个体)
        List<Map<String, Object>> companyNames = findNeedPushCompanyName("2");
        if (null != companyNames && companyNames.size() > 0) {
            log.info("调用个体名称查重数量：" + companyNames.size());
            RobotUtils.checkNameExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    for (Map<String, Object> companyName : companyNames) {
                        //获取
                        Robot robot = RobotFactory.getRobotOfQueryToGt();
                        if (robot != null) {
                            log.info("空闲的名称查重数据机器人获取成功(个体)");
                            //提交数据任务
                            boolean flag = robot.submitData(companyName);
                            if (flag) {
                                flag = robot.execTask();
                                if (flag) {
                                    //更新数据库为已推送状态
                                    companyName.put("SEND_STATUS", "1");
                                    dao.saveOrUpdate(companyName, "T_COMMERCIAL_COMPANYNAME",
                                            StringUtil.getString(companyName.get("ID")));
                                    try {
                                        Thread.sleep(10 * 1000);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }
                        } else {
                            log.info("空闲的名称查重数据机器人获取失败(个体)");
                        }
                    }
                }
            });
        }
        log.info("----------调用个体名称查重接口开始----------");
    }

    @Override
    public void pushDataOfSubmitToGt() {
        // TODO Auto-generated method stub
        log.info("----------调用个体业务接口开始----------");
        //获取需要提交的数据
        List<Map<String, Object>> datas = getDataOfSubmitOfCompany("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_ID"
                , ExeDataService.ID_AUTO_TASKNAME_MP);
        if (null != datas && datas.size() > 0) {
            log.info("个体业务提交数量：" + datas.size());
            RobotUtils.pushDataExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    for (Map<String, Object> data : datas) {
                        //获取
                        Robot robot = RobotFactory.getRobotOfSubmitToGt();
                        if (robot != null) {
                            log.info("空闲的业务数据提交机器人获取成功(个体)");
                            Map<String, Object> jobParams = new HashMap<>();
                            jobParams.put("data", data);
                            jobParams.put("EXE_ID",StringUtil.getString(data.get("EXE_ID")));
                            jobParams.put("INDIVIDUAL_NAME",StringUtil.getString(data.get("INDIVIDUAL_NAME")));
                            String pushInterval=StringUtil.getString(dictionaryService.
                                    get("robotConfig", "pushInterval").get("DIC_DESC"));
                            int pushIntervalNum=Integer.parseInt(pushInterval);
                            //提交数据任务
                            boolean flag = robot.submitData(jobParams);
                            if (flag) {
                                //任务手动触发
                                flag = robot.execTask();
                                if (flag) {
                                    //更新数据库为已推送状态
                                    String pid = StringUtil.getString(data.get("INDIVIDUAL_ID"));
                                    StringBuffer updateSql = new StringBuffer("update T_COMMERCIAL_INDIVIDUAL ");
                                    updateSql.append(" set  PUSH_STATUS_INTER=?,PUSH_INTER_TIME=?  ");
                                    updateSql.append(" where INDIVIDUAL_ID=? ");
                                    String pushInterTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                                    dao.executeSql(updateSql.toString(), new Object[]{1,pushInterTime, pid});
                                    saveLogOfCompanyName(data);
                                    try {
                                        Thread.sleep(pushIntervalNum * 1000);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }
                        } else {
                            log.info("空闲的业务数据提交机器人获取失败(个体)");
                        }
                    }
                }
            });
        }
        log.info("----------调用个体业务接口结束----------");
    }
    /**
     * 个体推送日志保存数据
     * @param data
     */
    @SuppressWarnings("unchecked")
    private void saveLogOfCompanyName(Map<String,Object> data){
        try {
            String companyName=StringUtil.getString(data.get("INDIVIDUAL_NAME"));
            String exeId=StringUtil.getString(data.get("EXE_ID"));
            String logContent=String.format("推送开普云申报号为%s,个体商户名称为%s",exeId,companyName);
            Map<String,Object> log=new HashMap<>();
            log.put("EXE_ID",exeId);
            log.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            log.put("MSG",logContent);
            dao.saveOrUpdate(log, "T_COMMERCIAL_PUSH_LOG", null);
        } catch (Exception e) {
            // TODO: handle exception
            log.info("保存个体推送日志出错",e);
        }
    }
}
