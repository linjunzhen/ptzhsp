/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bdc.dao.BdcExecutionDao;
import net.evecom.platform.hflow.util.Jbpm6Constants;

/**
 * 描述  不动产登记操作dao
 * @author  Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("bdcExecutionDao")
public class BdcExecutionDaoImpl extends BaseDaoImpl implements BdcExecutionDao {

    @Override
    public List<Map<String, Object>> findBdcHandledByUser(SqlFilter sqlFilter, String userAccount) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,D.DEF_KEY FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE TA.ASSIGNER_CODE=? AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' )");
        sql.append(" and T.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        params.add(userAccount);
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
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
    
    
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" where 1 = 1 ");
        //限制统计四个事项
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        //改成某些事项id
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    @Override
    public List<Map<String, Object>> countsDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        //String itemCodes = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSTJ_TJXZ");
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where 1 = '1' ");
        //sql.append(" and ws.item_code in ("+itemCodes+") ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        //改成某些事项id
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    @Override
    public List<Map<String, Object>> banJSDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where 1 = 1 ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        sql.append(" and t.run_status in (2, 3, 4)");
        //改成某些事项id
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    @Override
    public List<Map<String, Object>> zBSDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where 1= 1 ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        sql.append(" and t.run_status in (1)");
        //改成某些事项id
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    @Override
    public List<Map<String, Object>> tJSDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where 1 = 1 ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        sql.append(" and t.run_status in (5)");
        //改成某些事项id
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
}
