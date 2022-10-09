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
import net.evecom.platform.zzhy.model.Robot;
import net.evecom.platform.zzhy.model.TableNameEnum;
import net.evecom.platform.zzhy.model.data.SubmitDataTable;
import net.evecom.platform.zzhy.model.data.SubmitDataTableFactory;
import net.evecom.platform.zzhy.service.CreatTaskInterService;
import net.evecom.platform.zzhy.service.MsgSendService;
import net.evecom.platform.zzhy.service.ResetPushStatusService;
import net.evecom.platform.zzhy.service.SubmitTaskService;
import net.evecom.platform.zzhy.util.RobotFactory;
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述 创建任务接口service 只有一天的时间，没时间封装
 * 
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("resetPushStatusService")
public class ResetPushStatusServiceImpl extends BaseServiceImpl implements ResetPushStatusService {
    /**
     * 统计办件量Map，一般用redis最好。
     */
    public static ConcurrentHashMap<String, Integer> statics = new ConcurrentHashMap();
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private MsgSendService msgSendService;
    /**
     * 所引入的dao
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 重新推送在智能审批环节超过1小时的办件
     */
    @SuppressWarnings("unchecked")
    @Override
    public void resetPushStatus() {
        List<Map<String, Object>> list = findExeInAutoTask();// 获取在智能审批的办件
        int resetNum = 0;
        for (Map<String, Object> map : list) {
            String busTableName = StringUtil.getString(map.get("BUS_TABLENAME"));
            String busRecordId = StringUtil.getString(map.get("BUS_RECORDID"));
            StringBuffer updateSql = new StringBuffer();
            if (Objects.equals(busTableName, TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal())
                    || Objects.equals(busTableName, TableNameEnum.T_COMMERCIAL_FOREIGN.getVal())) {
                busTableName = "T_COMMERCIAL_COMPANY";// 内资外资表转换
            }
            String primaryKeyName = dao.getPrimaryKeyName(busTableName).get(0).toString();
            Map<String, Object> busRecord = dao.getByJdbc(busTableName, new String[] { primaryKeyName },
                    new Object[] { busRecordId }); // 业务数据
            String PUSH_INTER_TIME = StringUtil.getString(busRecord.get("PUSH_INTER_TIME"));
            String curDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            long dayNum = 0;
            if (StringUtils.isNotEmpty(PUSH_INTER_TIME)) {
                dayNum = DateTimeUtil.getIntervalTime(PUSH_INTER_TIME, curDate, "yyyy-MM-dd HH:mm:ss", 2);
            }
            if (Objects.equals(StringUtil.getString(busRecord.get("PUSH_STATUS_INTER")), "1")
                    && (dayNum > 1 || StringUtils.isEmpty(PUSH_INTER_TIME))) {// 推送超过1小时把已推送状态重置（推送时间为空也进行重置）
                updateSql.append("update ").append(busTableName).append(" set PUSH_STATUS_INTER='0' where ");
                updateSql.append(primaryKeyName).append("=?");
                dao.executeSql(updateSql.toString(), new Object[] { busRecordId });
                resetNum++;
            }
        }
        // 发送短信
        if (CollectionUtils.isNotEmpty(list)) {
            sendMsgForNotice(resetNum, list.size());
        }
    }

    /**
     * 超过15条发送短信提醒相关人
     */
    @SuppressWarnings("unchecked")
    public void sendMsgForNotice(int resetNum, int size) {
        if (size > 5) {
            String mobileStr = StringUtil.getString(dictionaryService.get("MpMsg", "mobile").get("DIC_DESC"));// 短信提醒者
            String curDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            if (statics.size() > 7) {
                statics.clear();
            }
            // 重推数量统计
            int historyResetNum = 0;
            String curDateStatis = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            if (statics.get(curDateStatis) != null) {
                historyResetNum = statics.get(curDateStatis);
            }
            statics.put(curDateStatis, historyResetNum + resetNum);
            // 当天办结数量
            StringBuffer sql = new StringBuffer("SELECT EXE_ID ");
            sql.append(" FROM JBPM6_EXECUTION E  WHERE E.RUN_STATUS='2' ");
            sql.append("  AND E.END_TIME LIKE '").append(curDateStatis).append("%' AND E.ISNEEDSIGN='1' ");
            List<Map<String, Object>> overList = dao.findBySql(sql.toString(), null, null);
            int overSize = 0;
            if (CollectionUtils.isNotEmpty(overList)) {
                overSize = overList.size();
            }
            // 当天退回办件；
            StringBuffer backSql = new StringBuffer(" SELECT E.EXE_ID ");
            backSql.append("  FROM JBPM6_EXECUTION E  , T_WSBS_BJXX T ");
            backSql.append(" WHERE E.EXE_ID =T.EXE_ID AND E.BUS_TABLENAME LIKE 'T_COMMERCIAL%' ");
            backSql.append("  and t.create_time like '").append(curDateStatis).append("%' ");
            List<Map<String, Object>> backList = dao.findBySql(backSql.toString(), new Object[] {}, null);
            int backSize = 0;
            if (CollectionUtils.isNotEmpty(backList)) {
                backSize = backList.size();
            }

            String msgTemplate = "当前时间%s,本次重新推送数量%s；截止至当前待推送量为%s，今日办结量为%s,退回办件量为%s。";
            String msg = String.format(msgTemplate, curDate, resetNum, size, overSize, backSize);
            if (StringUtils.isNotEmpty(mobileStr)) {
                String[] mobiles = mobileStr.split(",");
                for (String mobile : mobiles) {
                    msgSendService.saveMsg(msg, mobile);
                }
            }
        }
    }

    /**
     * 获取在智能审批的办件
     * 
     * @return
     */
    private List<Map<String, Object>> findExeInAutoTask() {
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT E.EXE_ID,E.BUS_RECORDID,E.BUS_TABLENAME ");
        sql.append("  FROM JBPM6_EXECUTION E JOIN  JBPM6_TASK T  ON E.EXE_ID=T.EXE_ID  ");
        sql.append("where  e.cur_stepnames=?  and t.task_nodename=?  ");
        sql.append(" and E.BUS_TABLENAME LIKE 'T_COMMERCIAL_%' ");
        sql.append(" and t.end_time is  null and t.assigner_code is not null ");
        sql.append("  and t.create_time< ?   ");
        Date beforeOneHour = DateTimeUtil.getBeforeNDate(new Date(), -1, "h");
        String beforeOneHoure = DateTimeUtil.dateToStr(beforeOneHour, "yyyy-MM-dd HH:mm:ss");
        return dao.findBySql(sql.toString(),
                new Object[] { ExeDataService.ID_AUTO_TASKNAME_MP, ExeDataService.ID_AUTO_TASKNAME_MP, beforeOneHoure },
                null);
    }
}
