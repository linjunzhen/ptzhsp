/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.sb.dao.SbExecutionDao;
import org.springframework.stereotype.Repository;

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
@Repository("sbExecutionDao")
public class SbExecutionDaoImpl extends BaseDaoImpl implements SbExecutionDao {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 17:52:00
     * @param 
     * @return 
     */
    @Override
    public List<Map<String, Object>> findSbHandledByUser(SqlFilter sqlFilter, String userAccount) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,D.DEF_KEY FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE TA.ASSIGNER_CODE=? AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' )");
        sql.append(" and T.ITEM_CODE IN ( select dic.dic_code from ")
                .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='sbItemCode') ");
        params.add(userAccount);
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 18:02:00
     * @param 
     * @return 
     */
    public List<Map<String,Object>> setExeWorkHours(List<Map<String, Object>> list){
        //遍历list,设置动态工时
        for(Map<String,Object> map:list){
            //获取流程状态
            int runStatus = Integer.parseInt(map.get("RUN_STATUS").toString());
            if(runStatus== Jbpm6Constants.RUNSTATUS_RUNING){
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
}
