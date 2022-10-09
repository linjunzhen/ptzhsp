/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.zzhy.dao.StatistCommercialDao;

import org.springframework.stereotype.Repository;

/**
 * 描述  商事登记统计分析操作dao
 * @author  Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("statistCommercialDao")
public class StatistCommercialDaoImpl extends BaseDaoImpl implements StatistCommercialDao {
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;

    /**
     * 描述单天所需要入库数量
     * 
     * @author Water Guo
     * @param type 
     * @created 2016年12月20日 上午10:19:14
     * @return
     */
    @Override
    public List<Map<String, Object>> getToDayNum(String type, String thisMonth) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_COMPANY t ");
        sql.append(" left join JBPM6_EXECUTION e on t.company_id = e.bus_recordid ");
        sql.append(" where t.company_id not in (select ts.company_id from T_COMMERCIAL_COMPANYSYNC ts) ");
        sql.append(" and e.run_status in (2,3) ");
        sql.append(" and t.register_type = ? ");
        sql.append(" and e.end_time like ? ");
        thisMonth= "%"+thisMonth+"%";
        return this.findBySql(sql.toString(), new Object[]{type,thisMonth}, null);
    }

    /**
     * 描述单天已入库数量
     * 
     * @author Water Guo
     * @created 2016年12月20日 上午10:19:14
     * @return
     */
    @Override
    public List<Map<String, Object>> getToTalNum(String dateNowStr, String type) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_STATIST t ");
        sql.append(" where t.statist_type = ?");
        sql.append(" and t.month = ?");
        return this.findBySql(sql.toString(), new Object[]{type,dateNowStr}, null);
    }

    @Override
    public List<Map<String, Object>> getGTToDayNum(String thisMonth) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_INDIVIDUAL t ");
        sql.append(" left join JBPM6_EXECUTION e on t.individual_id = e.bus_recordid ");
        sql.append(" where t.individual_id not in (select ts.individual_id from T_COMMERCIAL_INDIVIDUALSYNC ts) ");
        sql.append(" and e.run_status in (2,3) ");
        sql.append(" and e.end_time like ? ");
        thisMonth= "%"+thisMonth+"%";
        return this.findBySql(sql.toString(), new Object[]{thisMonth}, null);
    }

    @Override
    public List<Map<String, Object>> getGDToDayNum(String thisMonth) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_SOLELYINVEST t ");
        sql.append(" left join JBPM6_EXECUTION e on t.solely_id = e.bus_recordid ");
        sql.append(" where t.solely_id not in (select ts.solely_id from T_COMMERCIAL_SOLELYINVESTSYNC ts) ");
        sql.append(" and e.run_status in (2,3) ");
        sql.append(" and e.end_time like ? ");
        thisMonth= "%"+thisMonth+"%";
        return this.findBySql(sql.toString(), new Object[]{thisMonth}, null);
    }

    @Override
    public List<Map<String, Object>> getThisMonth(String thisMonth) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_STATIST t where t.month = ? ");
        sql.append(" and t.statist_type in (1,2,3,4) ");
        return this.findBySql(sql.toString(), new Object[]{thisMonth}, null);
    }

    @Override
    public List<Map<String, Object>> getThisMonthTotal(String thisMonth) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_STATIST t where t.month = ? ");
        sql.append(" and t.statist_type in (5) ");
        return this.findBySql(sql.toString(), new Object[]{thisMonth}, null);
    }

    @Override
    public List<Map<String, Object>> getTBinfo() {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_COMPANY t ");
        sql.append(" where t.COMPANY_ID not in (select ts.SYNC_ID from T_COMMERCIAL_SYNC ts) ");
        return this.findBySql(sql.toString(), new Object[]{}, null);
    }

    @Override
    public List<Map<String, Object>> getGTTBinfo() {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_INDIVIDUAL t ");
        sql.append(" where t.INDIVIDUAL_ID not in (select ts.SYNC_ID from T_COMMERCIAL_SYNC ts) ");
        return this.findBySql(sql.toString(), new Object[]{}, null);
    }
    @Override
    public List<Map<String, Object>> getGDTBinfo() {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" select t.* from T_COMMERCIAL_SOLELYINVEST t ");
        sql.append(" where t.SOLELY_ID not in (select ts.SYNC_ID from T_COMMERCIAL_SYNC ts) ");
        return this.findBySql(sql.toString(), new Object[]{}, null);
    }

    @Override
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT T.EXE_ID,t.SUBJECT,t.CUR_STEPNAMES,t.RUN_STATUS,t.CUR_STEPAUDITNAMES,t.ISNEEDSIGN, ");
        sql.append(" t.WORK_HOURS,t.CREATE_TIME,t.END_TIME,t.CREATOR_NAME FROM JBPM6_EXECUTION T LEFT JOIN ");
        sql.append(" JBPM6_FLOWDEF D ON T.DEF_ID = D.DEF_ID left join T_WSBS_SERVICEITEM ws on ws.item_code = ");
        sql.append(" t.item_code left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' and ws.ITEM_CODE IN ");
        sql.append(" (select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE = 'zzhyCode') ");
        sql.append(" union ");
        sql.append(" SELECT T.EXE_ID,t.SUBJECT,t.CUR_STEPNAMES,t.RUN_STATUS,t.CUR_STEPAUDITNAMES,t.ISNEEDSIGN, ");
        sql.append(" t.WORK_HOURS,t.CREATE_TIME,t.END_TIME,t.CREATOR_NAME FROM JBPM6_EXECUTION_EVEHIS T LEFT JOIN ");
        sql.append(" JBPM6_FLOWDEF D ON T.DEF_ID = D.DEF_ID left join T_WSBS_SERVICEITEM ws on ws.item_code = ");
        sql.append(" t.item_code left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' and ws.ITEM_CODE IN ");
        sql.append(" (select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE = 'zzhyCode') ");
        sql.append(" ) T where 1 = 1 ");
        //改成某些事项id
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
    public List<Map<String, Object>> countsDetailData(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        //String itemCodes = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSTJ_TJXZ");
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' ");
        //sql.append(" and ws.item_code in ("+itemCodes+") ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        //改成某些事项id
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }

    @Override
    public List<Map<String, Object>> banJSDetailData(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        sql.append(" and t.run_status in (2, 3, 4)");
        //改成某些事项id
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }

    @Override
    public List<Map<String, Object>> zBSDetailData(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        sql.append(" and t.run_status in (1)");
        //改成某些事项id
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }

    @Override
    public List<Map<String, Object>> tJSDetailData(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code ");
        sql.append(" where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' ");
        sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        sql.append(" and t.run_status in (5)");
        //改成某些事项id
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
}
