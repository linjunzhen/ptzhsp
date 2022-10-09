/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.evecom.core.sm.Sm4Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.util.Jbpm6Constants;

/**
 * 描述  流程实例操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("executionDao")
public class ExecutionDaoImpl extends BaseDaoImpl implements ExecutionDao {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        //身份证和组织机构代码查询
        if(sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_T.SQRSFZ_LIKE").equals("")){
            if(sql.indexOf("where")<0&&sql.indexOf("WHERE")<0) {
                sql.append(" where 1=1 ");
            }
            String zjhm=sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            sql.append(" and (T.SQRSFZ  like '%").append(sm4Utils.encryptDataCBC(zjhm)).append("%' OR  T.SQJG_CODE like '%");
            sql.append(sm4Utils.encryptDataCBC(zjhm)).append("%')  ");
            sqlFilter.removeFilter("Q_T.SQRSFZ_LIKE");
        }
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilterAll(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        sqlFilter.getPagingBean().setPageSize(100);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述：更新推送状态
     * @author Water Guo
     * @created 2017-9-28 下午12:01:16
     * @param exeId
     */
    public void updateRes(String exeId){
        StringBuffer sql=new StringBuffer();
        sql.append("update t_bsfw_swbdata_res r set oper_status=0 where");
        sql.append(" exe_id='").append(exeId).append("'");
        sql.append(" and (r.data_type=41 or r.data_type=40)");
        this.jdbcTemplate.update(sql.toString());
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
     * 描述 获取被某个用户办理的流程
     * @author Flex Hu
     * @created 2015年8月22日 下午5:12:29
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledByUser(SqlFilter sqlFilter,String userAccount){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,D.DEF_KEY FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE TA.ASSIGNER_CODE=? AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' AND TA.IS_REAL_HANDLED=1 )");
        sql.append(" and T.ITEM_CODE NOT IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        params.add(userAccount);
        //asdfa
        //身份证和组织机构代码查询
        if(sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_T.SQRSFZ_LIKE").equals("")){
            String zjhm=sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            zjhm = sm4Utils.encryptDataCBC(zjhm);
            sql.append(" and (T.SQRSFZ  like '%").append(zjhm).append("%' OR  T.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_T.SQRSFZ_LIKE");
        }
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述：获取所有的办理的流程
     * @author Water Guo
     * @created 2017-10-16 下午2:36:12
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledAllUser(SqlFilter sqlFilter,String userAccount){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,D.DEF_KEY FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE  TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' AND TA.IS_REAL_HANDLED=1 )");
        sql.append(" and T.ITEM_CODE NOT IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        //asdfa
        //身份证和组织机构代码查询
        if(sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE")!=null&& !sqlFilter.getQueryParams()
                .get("Q_T.SQRSFZ_LIKE").equals("")){
            String zjhm=sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            zjhm = sm4Utils.encryptDataCBC(zjhm);
            sql.append(" and (T.SQRSFZ  like '%").append(zjhm).append("%' OR  T.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_T.SQRSFZ_LIKE");
        }
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述 获取商事被某个用户办理的流程
     * @author Flex Hu
     * @created 2015年8月22日 下午5:12:29
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findZzhyHandledByUser(SqlFilter sqlFilter,String userAccount){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,D.DEF_KEY FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE TA.ASSIGNER_CODE=? AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' )");
        sql.append(" and T.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        params.add(userAccount);
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_EXECUTION R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
    }
    
    /**
     * 
     * 描述 获取申报号
     * @author Flex Hu
     * @created 2015年8月27日 上午11:07:58
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<String> findExeIds(String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("select T.EXE_ID FROM ");
        sql.append(" JBPM6_EXECUTION T WHERE T.DEF_ID=? AND T.DEF_VERSION=? ");
        List<String> exeIds = this.jdbcTemplate.queryForList(sql.toString(), 
                new Object[]{defId,flowVersion},String.class);
        return exeIds;
    }
    
    /***
     * 
     * 描述 获取流程申报号
     * @author Flex Hu
     * @created 2015年11月12日 下午4:10:43
     * @return
     */
    public String getNextExeId(Map<String,Object> flowVars){
        String EFLOW_ASSIGNED_EXEID = (String) flowVars.get("EFLOW_ASSIGNED_EXEID");
        if(StringUtils.isNotEmpty(EFLOW_ASSIGNED_EXEID)){
            return EFLOW_ASSIGNED_EXEID;
        }else{
            StringBuffer exeId = new StringBuffer("FJPT");
            exeId.append(DateTimeUtil.getStrOfDate(new Date(), "yyMMdd"));
            int seqValue = this.jdbcTemplate.queryForInt("select SEQ_JBPM6_EXECUTION.nextval FROM DUAL");
            String nextValue = StringUtil.getFormatNumber(5, String.valueOf(seqValue));
            exeId.append(nextValue);
            return exeId.toString();
        }
       
    }
    
    /**
     * 
     * 描述 获取记录条数面向网站用户
     * @author Flex Hu
     * @created 2015年12月3日 下午2:42:42
     * @param userAccount
     * @return
     */
    public Map<String,String> getReportCountForWebSite(String userAccount){
        //获取总办件数量
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E ");
        sql.append("WHERE E.CREATOR_ACCOUNT=? AND E.SQFS='1' ");
        int zbjCount = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{userAccount});
        //获取办理中的数量
        sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E ");
        sql.append("WHERE E.CREATOR_ACCOUNT=? AND E.SQFS='1' ");
        sql.append(" AND E.RUN_STATUS=1 ");
        int blzCount = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{userAccount});
        //获取已办结的数量
        sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E ");
        sql.append("WHERE E.CREATOR_ACCOUNT=? AND E.SQFS='1' ");
        sql.append(" AND E.RUN_STATUS!=0 AND E.RUN_STATUS!=1 ");
        int ybjCount = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{userAccount});
        Map<String,String> countMap = new HashMap<String,String>();
        countMap.put("ZBJCOUNT", String.valueOf(zbjCount));
        countMap.put("BLZCOUNT", String.valueOf(blzCount));
        countMap.put("YBJCOUNT", String.valueOf(ybjCount));
        return countMap;
    }
    /**
     * 
     * 描述 根据时间和状态获取办件统计数
     * @author Faker Li
     * @created 2015年12月8日 上午10:30:34
     * @param time
     * @param state
     * @return
     */
    public int getTjByStateAndTime(String time, String state) {
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E WHERE 1=1 ");
        if(StringUtils.isNotEmpty(time)){
            sql.append("AND  E.CREATE_TIME LIKE '"+time+"%'");
        }
        if(StringUtils.isNotEmpty(state)&&state.equals("0")){
            sql.append("AND  E.RUN_STATUS NOT IN ('0','1')");
        }
        sql.append("AND E.ITEM_CODE IS NOT NULL");
        int count = this.jdbcTemplate.queryForInt(sql.toString());
        return count;
    }
    /**
     * 
     * 描述：根据办结时间和 状态来统计
     * @author Water Guo
     * @created 2017-6-16 下午4:40:45
     * @param time
     * @param state
     * @return
     */
    public int getTjByEndTime(String time,String state){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E WHERE 1=1 ");
        if(StringUtils.isNotEmpty(time)){
            sql.append("AND  E.END_TIME LIKE '"+time+"%'");
        }
        if(StringUtils.isNotEmpty(state)){
            sql.append("AND  E.RUN_STATUS="+state);
        }
        sql.append("  AND E.ITEM_CODE IS NOT NULL");
        int count = this.jdbcTemplate.queryForInt(sql.toString());
        return count;
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月10日 上午10:00:22
     * @param time
     * @param sqfs
     * @return
     */
    public int getSjsByTime(String time, String sqfs) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E WHERE 1=1 ");
        if(StringUtils.isNotEmpty(time)){
            sql.append("AND  E.CREATE_TIME LIKE '"+time+"%'");
        }
        if(StringUtils.isNotEmpty(sqfs)){
            sql.append("AND  E.SQFS = ?" );
            params.add(sqfs);
        }
        sql.append("AND E.ITEM_CODE IS NOT NULL");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
        return count;
    }
    /**
     * 
     * 描述 根据申请方式和申请时间、申请小时获取统计数
     * @author Faker Li
     * @created 2016年3月10日 下午3:54:53
     * @param sTime
     * @param eTime
     * @param hour
     * @return
     */
    public int getCountByHour(String sTime, String eTime, String hour,String sqfs) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_EXECUTION E WHERE 1=1 ");
        sql.append(" AND E.CREATE_TIME>=? and E.CREATE_TIME<=? "); 
        sql.append(" and to_char(to_date(E.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss'),'hh24')=? ");
        params.add(sTime);
        params.add(eTime);
        params.add(hour);
        if(StringUtils.isNotEmpty(sqfs)){
            sql.append("AND  E.SQFS = ?" );
            params.add(sqfs);
        }
        sql.append("AND E.ITEM_CODE IS NOT NULL");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
        return count;
    }
    /**
     * 
     * 描述 获取所有为补件状态的数据
     * @author Faker Li
     * @created 2016年3月23日 下午4:32:16
     * @return
     * @see net.evecom.platform.hflow.dao.ExecutionDao#findBjList()
     */
    public List<Map<String, Object>> findBjList() {
        StringBuffer sql = new StringBuffer("SELECT T.EXE_ID FROM JBPM6_EXECUTION T ");
        sql.append(" WHERE T.APPLY_STATUS IN ('4','7') AND T.RUN_STATUS = '1' ");
        return this.jdbcTemplate.queryForList(sql.toString());
    }
    /**
     * 
     * 描述   查询不动产抵押权人信息
     * @author Yanisin Shi
     * @param exeId
     * @param tableName
     * @return
     * create time 2021年9月26日
     */
    public List<Map<String, Object>> findBdcScdjInfoList(String exeId,String tableName) {
        if(tableName.contentEquals("T_BDC_DYQSCDJ")||tableName.contentEquals("T_BDCQLC_DYQSCDJ")||tableName.contentEquals("T_BDCQLC_DYQSCDJZB")){
        StringBuffer sql=new StringBuffer("");
        sql.append("select S.DYQRXX_NAME,S.DYQRXX_NATURE ,S.DYQRXX_IDNO  from "
                +tableName+ " S where S.YW_ID =(select t.bus_recordid from JBPM6_EXECUTION  t where exe_id='"
                +exeId +"' )");
        return this.jdbcTemplate.queryForList(sql.toString());
        }else{
            return null;
        }
    }
    /**
     * 
     * 描述 根据流程实例申报号获取承诺的工作日
     * @author Flex Hu
     * @created 2016年3月30日 上午10:56:48
     * @param exeId
     * @return
     */
    public int getPromiseWorkDayForItem(String exeId){
        StringBuffer sql = new StringBuffer("SELECT I.CNQXGZR FROM JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM I ON E.ITEM_CODE=I.ITEM_CODE");
        sql.append(" WHERE E.EXE_ID=? AND E.ITEM_CODE IS NOT NULL");
        return this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId});
    }
    @Override
    public List<Map<String, Object>> findHandledByHangup(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select distinct h.begin_time,T.* from JBPM6_HANGINFO H");
        sql.append(" ,JBPM6_TASK TA,JBPM6_EXECUTION T ");
        sql.append(" where H.TASK_ID=TA.PARENT_TASKID and TA.EXE_ID=T.EXE_ID ");
        sql.append(" AND TA.IS_AUDITED=1  AND TA.TASK_STATUS='-1' ");
        String exeSql = this.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    
     
    
}
