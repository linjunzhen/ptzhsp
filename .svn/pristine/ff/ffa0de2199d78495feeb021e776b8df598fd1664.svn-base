/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bsfw.dao.FdaStaticDao;
import net.evecom.platform.bsfw.service.FdaStaticService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.WorkdayService;

/**
 * 描述
 * @author Danto Huang
 * @created 2019年2月25日 下午4:06:26
 */
@Service("fdaStaticService")
public class FdaStaticServiceImpl extends BaseServiceImpl implements FdaStaticService {

    /**
     * 引入dao
     */
    @Resource
    private FdaStaticDao dao;
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * 
     */
    @Resource
    private FlowTaskService flowTaskService;

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2019年2月25日 下午4:07:45
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述    逾期办件列表
     * @author Danto Huang
     * @created 2019年2月25日 下午4:09:15
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getAdnormalStatist(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.depart_name,e.exe_id, ");
        sql.append("e.subject,s.item_name,sxlx.dic_name sxlx,e.CUR_STEPNAMES, ");
        sql.append("s.cnqxgzr,e.create_time,e.end_time,e.creator_name,e.creator_phone from JBPM6_EFFICINFO t ");
        sql.append("left join jbpm6_execution e on e.exe_id = t.eflow_exeid ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append("left join t_msjw_system_department d on d.depart_id = s.zbcs_id ");
        sql.append("left join (select sd.dic_code,sd.dic_name from t_msjw_system_dictionary sd where sd.type_id=");
        sql.append("(select type_id from t_msjw_system_dictype where type_code='ServiceItemType'))sxlx ");
        sql.append("on sxlx.dic_code = s.sxlx ");
        sql.append("where t.effi_desc=3 ");
        sql.append(" and s.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        
        for (Map<String, Object> map : list) {
            //获取任务截止时间  
            String createTime = (String) map.get("CREATE_TIME");
            String cnqxgzr = map.get("CNQXGZR")==null?"":map.get("CNQXGZR").toString();
            int cnqxgzrint = Integer.parseInt(cnqxgzr);
            Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
            String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
            if (cnqxgzrint>0) {
                deadLineDate = workdayService.getDeadLineDate(deadLineDate, cnqxgzrint);
                deadLineDate += " 00:00:00";
            }
            //获取办结时间
            String endTime = (String) map.get("END_TIME");
            if (StringUtils.isEmpty(endTime)) {
                endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            }
            Date endTimedDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if(StringUtils.isNotEmpty(deadLineDate)){
                int overdueWorkDay = flowTaskService.getOverdueWorkDay(deadLineDate,taskId,endTimedDate);
                map.put("OVERDUE_WORKDAY", overdueWorkDay);
            }else{
                map.put("OVERDUE_WORKDAY", -2);
            }
        }        
        return list;
    }
    

    /**
     * 
     * 描述    窗口人员办件量
     * @author Danto Huang
     * @created 2019年2月25日 下午4:28:48
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getIndPerDataStatist(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        String sqlString = "select us.FULLNAME,t.CREATOR_ID,"
                + "sum(case when t.run_status != 0 then 1 else 0 end)as COUNTS,"
                + "sum(case when t.run_status in (2, 3, 4) then 1 else 0 end)as BANJS,"
                + "sum(case when t.run_status in (5) then 1 else 0 end)as TJS,"
                + "sum(case when t.run_status in (1) then 1 else 0 end)as ZBS "
                + "from JBPM6_EXECUTION t "
                + "left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code "
                + "left join T_MSJW_SYSTEM_SYSUSER us on us.USER_ID = t.CREATOR_ID "
                + "where ws.ITEM_CODE IN ( select dic.dic_code from " +
                "T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') "
                + "and t.CREATOR_ID is not null";

        String exeSql = dao.getQuerySql(filter, sqlString, params);
        exeSql = exeSql + " group by us.FULLNAME,t.CREATOR_ID order by COUNTS desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }
    

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:29:07
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> countsDetailData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    
    /**
     * 
     * 描述 设置工时
     * @author Flex Hu
     * @created 2015年8月22日 下午5:13:49
     * @param list
     * @return
     */
    public List<Map<String,Object>> setExeWorkHours(List<Map<String, Object>> list){
         //遍历list,设置动态工时
        for(Map<String,Object> map:list){
            //获取流程状态
            int runStatus = Integer.parseInt(map.get("RUN_STATUS").toString());
            if(runStatus==Jbpm6Constants.RUNSTATUS_RUNING){
                //获取流程的创建事件
                String createTime = (String) map.get("CREATE_TIME");
                String endTime = DateTimeUtil.getStrOfDate(new Date(),
                        "yyyy-MM-dd HH:mm:ss");
                String timeConsuming = DateTimeUtil.getInternalTime(
                        createTime, endTime);
                map.put("WORK_HOURS", timeConsuming);
            }
        }
        return list;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:34:21
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> banJSDetailData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        sql.append(" and t.run_status in (2, 3, 4)");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:38:43
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> zBSDetailData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        sql.append(" and t.run_status in (1)");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:38:58
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> tJSDetailData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='fdaCode') ");
        sql.append(" and t.run_status in (5)");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
}
