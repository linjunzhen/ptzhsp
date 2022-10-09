/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.system.dao.WorkdayDao;

/**
 * 描述  工作日操作dao
 * @author  Roy Li
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("workdayDao")
public class WorkdayDaoImpl extends BaseDaoImpl implements WorkdayDao {
    /**
     * 
     * 描述 根据开始日期和传入的工作日数量计算截止日期
     * @author Flex Hu
     * @created 2015年11月7日 上午11:58:39
     * @param beginDate
     * @param workDayCount
     * @return
     */
    public String getDeadLineDate(String beginDate,int workDayCount){
        StringBuffer sql = new StringBuffer("select T.W_DATE from T_MSJW_SYSTEM_WORKDAY T ");
        sql.append("WHERE T.W_DATE>? AND T.W_SETID=2 ORDER BY T.W_DATE ASC");
        List<Map<String,Object>> workDays = this.findBySql(sql.toString(), 
                new Object[]{beginDate},new PagingBean(0,workDayCount));
        if(workDays!=null&&workDays.size()>0){
            return (String) workDays.get(workDays.size()-1).get("W_DATE");
        }else{
            return null;
        }
    }
    /**
     * 
     * 描述 根据开始日期和传入的自然日数量计算截止日期
     * @author Flex Hu
     * @created 2015年11月7日 上午11:58:39
     * @param beginDate
     * @param workDayCount
     * @return
     */
    public String getDeadLineDateNatural(String beginDate,int naturalDayCount){
        StringBuffer sql = new StringBuffer("select T.W_DATE from T_MSJW_SYSTEM_WORKDAY T ");
        sql.append("WHERE T.W_DATE>?  ORDER BY T.W_DATE ASC");
        List<Map<String,Object>> workDays = this.findBySql(sql.toString(), 
                new Object[]{beginDate},new PagingBean(0,naturalDayCount));
        if(workDays!=null&&workDays.size()>0){
            return (String) workDays.get(workDays.size()-1).get("W_DATE");
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 根据开始日期和结束日期获取工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午3:39:03
     * @param beginDate
     * @param endDate
     * @return
     */
    public int getWorkDayCount(String beginDate,String endDate){
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append("T_MSJW_SYSTEM_WORKDAY W WHERE W.W_DATE>? ");
        sql.append(" AND W.W_DATE<=? AND W.W_SETID=2 ORDER BY W.W_DATE ASC");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{beginDate,endDate});
        return count;
    }
    /**
     * 
     * 描述：根据开始日期和传入的工作日获取剩余工作日
     * @author Water Guo
     * @created 2017-3-23 上午10:19:21
     * @param beginDate
     * @param workDay
     * @return
     */
    public int getWorkDayRest(String beginDate,int workDay){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        String date=sdf.format(d);
        int workDayCout=getWorkDayCount(beginDate, date);
        int day=workDay-workDayCout;
        return day;
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月9日 下午5:09:51
     * @param sTime
     * @param eTime
     * @return
     */
    public List<Map<String, Object>> findWorkDayByseTime(String sTime, String eTime) {
        StringBuffer sql = new StringBuffer("select W.W_ID,W.W_DATE from ");
        sql.append("T_MSJW_SYSTEM_WORKDAY W WHERE W.W_DATE>=? ");
        sql.append(" AND W.W_DATE<=? AND W.W_SETID=2 ORDER BY W.W_DATE ASC");
        List<Map<String,Object>> workDays = this.findBySql(sql.toString(), new Object[]{sTime,eTime}, null);
        return workDays;
    }
}
