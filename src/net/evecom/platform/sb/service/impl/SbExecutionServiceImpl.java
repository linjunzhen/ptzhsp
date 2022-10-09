/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.sb.dao.SbExecutionDao;
import net.evecom.platform.sb.service.SbExecutionService;
import net.evecom.platform.system.service.WorkdayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * 描述： 社保办件
 *
 * @author Madison You
 * @created 2021年2月2日 上午10:14:56
 */
@Service("sbExecutionService")
public class SbExecutionServiceImpl extends BaseServiceImpl implements SbExecutionService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 10:48:00
     * @param 
     * @return 
     */
    @Resource
    private SbExecutionDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 10:49:00
     * @param 
     * @return 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 10:49:00
     * @param 
     * @return 
     */
    @Resource
    private WorkdayService workdayService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 10:50:00
     * @param 
     * @return 
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 10:19:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findSbNeedMeHandle(SqlFilter sqlFilter, boolean isHaveHandUp) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.END_TIME ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE IN ( select dic.dic_code from ")
                .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='sbItemCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        String gsspBeginTime = "";
        for (Map<String, Object> map : list) {
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            //结束时间
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");

            gsspBeginTime = (String) map.get("TASK_CTIME");
            map.put("isYs", true);
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            if(StringUtils.isNotEmpty(gsspBeginTime)){
                map.put("WORK_HOURS",DateTimeUtil.getWorkTime(gsspBeginTime, endTime));
            }


            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 17:48:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findSbHandledByUser(SqlFilter sqlFilter, String userAccount) {
        // TODO Auto-generated method stub
        return dao.findSbHandledByUser(sqlFilter, userAccount);
    }

    /**
     *
     * 描述 根据任务截止时间获取剩余工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    public int getLeftWorkDay(String taskDeadTime,String taskId){
        //获取挂起的工作天数
        int hangAllTime = 0;
        if(StringUtils.isNotEmpty(taskId)){
            //获取流程实例信息
            Map<String,Object> flowTask = this.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{taskId});
            String parentTaskId = (String)flowTask.get("PARENT_TASKID");
            //获取挂起的工作天数
            hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(parentTaskId);
        }

        Date taskDeadDay = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
        String endDate = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
        if(hangAllTime>0){
            String eDate = this.workdayService.getDeadLineDate(endDate, hangAllTime);
            if(StringUtils.isNotEmpty(eDate)){
                endDate = eDate;
            }
        }
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        if(endDate.equals(currentDate)){
            return 0;
        }else{
            int leftWorkDay = this.workdayService.getWorkDayCount(currentDate, endDate);
            if(leftWorkDay==0){
                return -1;
            }else{
                return leftWorkDay;
            }
        }
    }


}
