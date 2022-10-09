/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hflow.service.ComEffiService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 商事效率统计
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("comEffiService")
public class ComEffiServiceImpl extends BaseServiceImpl implements ComEffiService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ComEffiServiceImpl.class);
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;

    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;

    /**
     * 
     * 描述 获取dao
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:52:27
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void comEffiStatistics(Log log) {
        // TODO Auto-generated method stub
        String sql = "select e.RUN_STATUS,e.exe_id,e.item_code,e.end_time from jbpm6_execution e "
                + "left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code " + "where s.ITEM_CODE IN  "
                + "(select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') "
                + "and e.exe_id not in (select ec.eflow_exeid from JBPM6_EFFICINFO ec) ";
        // +"and e.exe_id = 'FJPT17030144006'";
        List<Map<String, Object>> dataList = this.dao.findBySql(sql, new Object[] {}, null);
        if (dataList != null && dataList.size() > 0) {
            log.info("开始统计商事办件效率数据......");
            log.info("商事办件效率数据数量：" + dataList.size());
            for (Map<String, Object> dataRes : dataList) {
                this.taskEffiStatistics(dataRes);
            }
            log.info("结束统计商事办件效率数据......");
        }

    }

    @SuppressWarnings("unchecked")
    private void taskEffiStatistics(Map<String, Object> dataRes) {
        // TODO Auto-generated method stub
        // 获取流程状态
        String runStatus = dataRes.get("RUN_STATUS").toString();
        // 事项编码
        String itemCode = (String) dataRes.get("ITEM_CODE");
        // 事项办结时间
        String exeEndTime = StringUtil.getString(dataRes.get("END_TIME"));
        if (StringUtils.isEmpty(exeEndTime)) {
            exeEndTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        String exeid = (String) dataRes.get("EXE_ID");
        StringBuffer sql = new StringBuffer("select T.* FROM JBPM6_TASK T ");
        sql.append(" where t.EXE_ID = ?");
        sql.append(" order by T.STEP_SEQ ASC");
        List<Map<String, Object>> list = this.dao.findBySql(sql.toString(), new Object[] { exeid }, null);
        String bgbazxsxbm = getDicCodes("BGBAZXSXBM");
        String bgsxbm = dictionaryService.getDicCode("BGBAZXSXBM", "变更");
        String gsspBeginTime = "";
        for (Map<String, Object> task : list) {
            String beginTime = (String) task.get("CREATE_TIME");
            // 获取办结时间
            String endTime = (String) task.get("END_TIME");
            if (StringUtils.isEmpty(endTime)) {
                endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                task.put("WORK_HOURS", DateTimeUtil.getWorkTime(beginTime, endTime));
            } else {
                task.put("WORK_HOURS", DateTimeUtil.getWorkTime(beginTime, endTime));
            }
            // 获取流程节点
            String taskNodeName = (String) task.get("TASK_NODENAME");
            if (bgbazxsxbm.contains(itemCode)) {// 变更备案注销
                if (taskNodeName.contains("网上预审")) { // 网上预审 1天超时处理
                    String endDay = DateTimeUtil.getStrOfDate(DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss"),
                            "yyyy-MM-dd");
                    String createTime = task.get("CREATE_TIME").toString();
                    String createDay = DateTimeUtil
                            .getStrOfDate(DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    int days = workdayService.getWorkDayCount(createDay, endDay);
                    if (days > 1) {
                        String insertSQL = "INSERT INTO JBPM6_EFFICINFO " + "(eflow_exeid, effi_desc) VALUES ('" + exeid
                                + "',3)";
                        dao.executeSql(insertSQL, null);
                        return;
                    }

                } else if (taskNodeName.equals("工商审批")) {
                    gsspBeginTime = beginTime;
                }else if (taskNodeName.equals("办结")) {
                    if(StringUtils.isEmpty(gsspBeginTime)){
                        gsspBeginTime = beginTime;
                    }
                }
                if (StringUtils.isNotEmpty(gsspBeginTime)) {
                    String endDay = DateTimeUtil
                            .getStrOfDate(DateTimeUtil.getDateOfStr(exeEndTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    String createTime = task.get("CREATE_TIME").toString();
                    String createDay = DateTimeUtil
                            .getStrOfDate(DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    int days = workdayService.getWorkDayCount(createDay, endDay);
                    if (days > 3 && itemCode.equals(bgsxbm)) {//变更工商审批三天超时
                        String insertSQL = "INSERT INTO JBPM6_EFFICINFO " + "(eflow_exeid, effi_desc) VALUES ('" + exeid
                                + "',3)";
                        dao.executeSql(insertSQL, null);
                        return;
                    } else if(days > 1 && !itemCode.equals(bgsxbm)){//注销备案工商审批一天超时
                        String insertSQL = "INSERT INTO JBPM6_EFFICINFO " + "(eflow_exeid, effi_desc) VALUES ('" + exeid
                                + "',3)";
                        dao.executeSql(insertSQL, null);
                        return;
                    }
                }
            } else {
                if (taskNodeName.contains("工商预审") || taskNodeName.contains("国税预审") || taskNodeName.contains("商务处预审")) {
                    // 工商预审，国税预审，商务处预审（外资） 两天 超时处理
                    String endDay = DateTimeUtil.getStrOfDate(DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm:ss"),
                            "yyyy-MM-dd");
                    String createTime = task.get("CREATE_TIME").toString();
                    String createDay = DateTimeUtil
                            .getStrOfDate(DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
                    int days = workdayService.getWorkDayCount(createDay, endDay);
                    if (days > 2) {
                        String insertSQL = "INSERT INTO JBPM6_EFFICINFO " + "(eflow_exeid, effi_desc) VALUES ('" + exeid
                                + "',3)";
                        dao.executeSql(insertSQL, null);
                        return;
                    }

                } else if (taskNodeName.contains("工商审批")) {
                    // 工商审批到办结时长超时处理
                    gsspBeginTime = beginTime;
                    if (StringUtils.isNotEmpty(gsspBeginTime)) {
                        long leftWorkday = new DateTimeUtil().getWorkMinute(gsspBeginTime, endTime);
                        if (leftWorkday > 180) {
                            String insertSQL = "INSERT INTO JBPM6_EFFICINFO " + "(eflow_exeid, effi_desc) VALUES ('"
                                    + exeid + "',3)";
                            dao.executeSql(insertSQL, null);
                            return;
                        }
                    }
                } else {// 不处理

                }
            }
        }
        if ((!"0".equals(runStatus)) && (!"1".equals(runStatus))) {
            // 未超时且流程已办结
            // 0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),
            // 4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回)
            String insertSQL = "INSERT INTO JBPM6_EFFICINFO (eflow_exeid, effi_desc) VALUES ('" + exeid + "',2)";
            dao.executeSql(insertSQL, null);
            return;
        } else {// 不处理

        }
    }
    /**
     * 
     * 描述 获取变更备案注销事项编码
     * @author Rider Chen
     * @created 2021年6月25日 下午6:11:29
     * @param typeCode
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getDicCodes(String typeCode) {
        String bgbazxsxbm = "";
        StringBuffer dicsql = new StringBuffer("SELECT WMSYS.WM_CONCAT(D.dic_code) as DIC_CODE");
        dicsql.append(" FROM T_MSJW_SYSTEM_DICTIONARY D ");
        dicsql.append(" WHERE D.TYPE_ID=(SELECT T.TYPE_ID ");
        dicsql.append(" FROM T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=?) ");
        dicsql.append(" ORDER BY D.DIC_SN ASC ");
        Map<String, Object> dicMap = dao.getByJdbc(dicsql.toString(), new Object[] { typeCode });
        if (null != dicMap && dicMap.size() > 0) {
            bgbazxsxbm = StringUtil.getString(dicMap.get("DIC_CODE"));
        }
        return bgbazxsxbm;
    }

}
