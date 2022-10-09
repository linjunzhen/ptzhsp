/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.FlowHangInfoDao;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 流程挂起信息表操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowHangInfoService")
public class FlowHangInfoServiceImpl extends BaseServiceImpl implements FlowHangInfoService {
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
     * 所引入的dao
     */
    @Resource
    private FlowHangInfoDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     * 描述更新流程挂起说明
     * @author Faker Li
     * @created 2016年4月14日 上午10:40:18
     * @param selectTaskIds
     * @see net.evecom.platform.hflow.service.FlowHangInfoService#endHangTime(java.lang.String)
     */
    public void hangExplain(String selectTaskIds,String explain, String fileid) {
        String[] taskIds = selectTaskIds.split(",");
        for (int i = 0; i < taskIds.length; i++) {
            String taskId = taskIds[i];
          //获取流程实例信息
            Map<String,Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{taskId});
            String parentTaskId = (String)flowTask.get("PARENT_TASKID");
            dao.hangExplain(parentTaskId,explain,fileid);
        }
        
    }
    /**
     * 
     * 描述更新流程挂机结束时间
     * @author Faker Li
     * @created 2016年4月14日 上午10:40:18
     * @param selectTaskIds
     * @see net.evecom.platform.hflow.service.FlowHangInfoService#endHangTime(java.lang.String)
     */
    public void endHangTime(String selectTaskIds) {
        String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String[] taskIds = selectTaskIds.split(",");
        for (int i = 0; i < taskIds.length; i++) {
            String taskId = taskIds[i];
          //获取流程实例信息
            Map<String,Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{taskId});
            String parentTaskId = (String)flowTask.get("PARENT_TASKID");
            dao.endHangTime(parentTaskId,endTime);
        }
        
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年4月14日 下午1:58:14
     * @param taskId
     * @return
     * @see net.evecom.platform.hflow.service.FlowHangInfoService#gethangAllTimeByTaskId(java.lang.String)
     */
    public int gethangAllTimeByTaskId(String taskId) {
        int hangAllTime = 0;
        List<Map<String,Object>> hangMapList;
        StringBuffer sql = new StringBuffer("SELECT * FROM JBPM6_HANGINFO T WHERE T.TASK_ID = ? "); 
        hangMapList = dao.findBySql(sql.toString(), new Object[]{taskId}, null);
        for (int i = 0; i < hangMapList.size(); i++) {
            Map<String,Object> e = hangMapList.get(i);
            String beginTime = (String)e.get("BEGIN_TIME");
            Date beginTimeDate = DateTimeUtil.format(beginTime, "yyyy-MM-dd");
            String endTime = (String)e.get("END_TIME");
            if(StringUtils.isEmpty(endTime)){
                endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            }
            Date endTimeDate = DateTimeUtil.format(endTime, "yyyy-MM-dd");
            String beginDate = DateTimeUtil.getStrOfDate(beginTimeDate, "yyyy-MM-dd");
            String endDate = DateTimeUtil.getStrOfDate(endTimeDate, "yyyy-MM-dd");
            int hangTime = this.workdayService.getWorkDayCount(beginDate, endDate);
            hangAllTime +=hangTime;
        }
        return hangAllTime;
    }
}
