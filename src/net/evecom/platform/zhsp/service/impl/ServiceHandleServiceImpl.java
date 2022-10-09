/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zhsp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.dao.WorkdayDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.zhsp.dao.ServiceHandleDao;
import net.evecom.platform.zhsp.service.ServiceHandleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * 描述
 * @author Danto Huang
 * @created 2015-11-23 上午9:54:10
 */
@Service("serviceHandleService")
public class ServiceHandleServiceImpl extends BaseServiceImpl implements ServiceHandleService {
    /**
     * 所引入的dao
     */
    @Resource
    private ServiceHandleDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao executionDao;
    /**
     * 所引入的dao
     */
    @Resource
    private WorkdayDao workdayDao;
    /**
     * 所引入的flowHangInfoService
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    /**
     * 所引入的WorkdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * 所引入的WorkdayService
     */
    @Resource
    private StatisticsService statisticsService;
    /**
     * 属性jdbcTemplate
     */
    protected JdbcTemplate jdbcTemplate;


    /**
     * 
     * 描述 覆盖获取实体dao方法
     * @author Danto Huang
     * @created 2015-11-23 上午9:55:40
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述 
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:42:13
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 获取待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandle(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        String sxlx = sqlFilter.getQueryParams().get("Q_TWS.SXLX_EQ").toString();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,E.SQRMC FROM JBPM6_TASK T");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM TWS ON TWS.BDLCDYID = T.DEF_ID AND TWS.ITEM_CODE=E.ITEM_CODE ");
        if (sxlx.equals("2")) {
            sql.append("LEFT JOIN T_BSFW_PTJINFO TBP ON E.BUS_RECORDID=TBP.YW_ID ");
        } else if (sxlx.equals("1")) {
            sql.append("LEFT JOIN T_BSFW_JBJINFO TBJ ON E.BUS_RECORDID=TBJ.YW_ID ");
        }
        sql.append(" WHERE T.IS_AUDITED=1 AND T.TASK_STATUS=1 ");
        // 身份证和组织机构代码查询
        if (sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE") != null
                && !sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE").equals("")) {
            String zjhm = sqlFilter.getQueryParams().get("Q_E.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            zjhm = sm4Utils.encryptDataCBC(zjhm);
            sql.append(" and (E.SQRSFZ  like '%").append(zjhm).append("%' OR  E.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_E.SQRSFZ_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述  获取被某个用户办理的流程
     * @author Danto Huang
     * @created 2015-11-23 下午2:58:14
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findHandledByUser(SqlFilter sqlFilter, String userAccount) {
        List<Object> params = new ArrayList<Object>();
        String sxlx = sqlFilter.getQueryParams().get("Q_TWS.SXLX_EQ").toString();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM TWS ON TWS.ITEM_CODE=T.ITEM_CODE ");
        if (sxlx.equals("2")) {
            sql.append("LEFT JOIN T_BSFW_PTJINFO TBP ON T.BUS_RECORDID=TBP.YW_ID ");
        } else if (sxlx.equals("1")) {
            sql.append("LEFT JOIN T_BSFW_JBJINFO TBJ ON T.BUS_RECORDID=TBJ.YW_ID ");
        }
        sql.append("WHERE T.EXE_ID IN (SELECT TA.EXE_ID FROM JBPM6_TASK TA ");
        sql.append("WHERE TA.ASSIGNER_CODE=? AND TA.IS_AUDITED=1 AND TA.TASK_STATUS!='1' ");
        sql.append("AND TA.TASK_STATUS!='-1' )");
        // 身份证和组织机构代码查询
        if (sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE") != null
                && !sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").equals("")) {
            String zjhm = sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            zjhm = sm4Utils.encryptDataCBC(zjhm);
            sql.append(" and (T.SQRSFZ  like '%").append(zjhm).append("%' OR  T.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_T.SQRSFZ_LIKE");
        }
        params.add(userAccount);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        list = executionDao.setExeWorkHours(list);
        return list;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        String sxlx = sqlFilter.getQueryParams().get("Q_TWS.SXLX_EQ").toString();
        StringBuffer sql = new StringBuffer("SELECT T.*,TWS.CNQXGZR FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM TWS ON TWS.ITEM_CODE=T.ITEM_CODE ");
        if (sxlx.equals("2")) {
            sql.append("LEFT JOIN T_BSFW_PTJINFO TBP ON T.BUS_RECORDID=TBP.YW_ID ");
        } else if (sxlx.equals("1")) {
            sql.append("LEFT JOIN T_BSFW_JBJINFO TBJ ON T.BUS_RECORDID=TBJ.YW_ID ");
        }
        if (sqlFilter.getQueryParams().get("Q_tsd.DEPART_ID_=") != null) {
            String level = this
                    .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                            new Object[] { sqlFilter.getQueryParams().get("Q_tsd.DEPART_ID_=") })
                    .get("TREE_LEVEL").toString();
            if (level.equals("4")) {
                sql.append("left join T_WSBS_SERVICEITEM_CATALOG tsc on tsc.catalog_code = tws.catalog_code ");
                sqlFilter.addFilter("Q_tsc.CHILD_DEPART_ID_=",
                        sqlFilter.getQueryParams().get("Q_tsd.DEPART_ID_=").toString());
                sqlFilter.removeFilter("Q_tsd.DEPART_ID_=");
            } else {
                sql.append("LEFT JOIN t_msjw_system_department tsd ON tsd.depart_code = t.ssbmbm");
            }
        }
        // sql.append("LEFT JOIN t_msjw_system_department tsd ON tsd.depart_code = t.ssbmbm ");
        // 身份证和组织机构代码查询
        if (sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE") != null
                && !sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").equals("")) {
            if (sql.indexOf("where") < 0 && sql.indexOf("WHERE") < 0) {
                sql.append(" where 1=1 ");
            }
            String zjhm = sqlFilter.getQueryParams().get("Q_T.SQRSFZ_LIKE").toString();
            Sm4Utils sm4Utils = new Sm4Utils();
            zjhm = sm4Utils.encryptDataCBC(zjhm);
            sql.append(" and (T.SQRSFZ  like '%").append(zjhm).append("%' OR  T.SQJG_CODE like '%");
            sql.append(zjhm).append("%')  ");
            sqlFilter.removeFilter("Q_T.SQRSFZ_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        list = executionDao.setExeWorkHours(list);
        // 设置剩余时间和是否超期
        setResTime(list);
        return list;
    }

    /**
     * 
     * 描述：剩余时间和是否超期
     * @author Water Guo
     * @created 2017-3-23 上午11:07:31
     * @param list
     */
    public void setResTime(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            StringBuffer sql = new StringBuffer("select j.end_time,j.TASK_DEADTIME,j.task_nodename,"
                    + "j.create_time,j.step_seq,j.task_id   from jbpm6_task j where  j.exe_id='");
            String exeId = map.get("EXE_ID").toString();
            sql.append(exeId).append("'");
            sql.append("and j.step_seq  in (select max(step_seq) from  jbpm6_task ");
            sql.append("where exe_id = '").append(exeId).append("')");
            Map<String, Object> task = dao.getByJdbc(sql.toString(), new Object[] {});
            if(task==null) continue;
            // 截止时间
            String taskDeadTime = StringUtil.getString(task.get("TASK_DEADTIME"));
            String taskId = (String) task.get("TASK_ID");
            String endTime = (String) task.get("END_TIME");
            // 获取办结时间
            if (StringUtils.isEmpty(endTime)) {
                // 没有办结时间的时候，当前的时间作为办结
                endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            }
            if (StringUtils.isNotEmpty(taskDeadTime)) {
                // 获取挂起的工作天数
                int hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(taskId);
                // 截止时间
                Date taskDeadDate = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
                // taskDeadDate = DateTimeUtil.getNextDay(taskDeadDate, 1);
                String taskTime = this.workdayService.getDeadLineDate(taskDeadTime, 1);
                taskDeadDate = DateTimeUtil.getDateOfStr(taskTime, "yyyy-MM-dd");
                String endDate = DateTimeUtil.getStrOfDate(taskDeadDate, "yyyy-MM-dd");
                if (hangAllTime > 0) {
                    String eDate = this.workdayService.getDeadLineDate(endDate, hangAllTime);
                    if (StringUtils.isNotEmpty(eDate)) {
                        endDate = eDate;
                    }
                }
                int remainDay = workdayService.getWorkDayCount(endTime, endDate);
                if (remainDay > 1) {
                    map.put("SFCQ", 1);
                    map.put("RES_TIME", remainDay - 1 + "个工作日");
                } else if (remainDay == 1) {
                    map.put("SFCQ", 1);
                    map.put("RES_TIME", "当日办结");
                } else if (remainDay == 0) {
                    map.put("SFCQ", 0);
                    map.put("RES_TIME", "超期");
                }
            } else {
                String crqxgzr = map.get("CNQXGZR") + "";
                // 承若期限为0
                if ("0".equals(crqxgzr)) {
                    String createTime = (String) map.get("CREATE_TIME");
                    Date createDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
                    String deadLineDateStr=statisticsService.getJbjDeadLineDate(createDate,createTime,"即办件");
                    Date deadLineDate= DateTimeUtil.getDateOfStr(deadLineDateStr, "yyyy-MM-dd");
                    Date endDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd");

                    if (endDate.after(deadLineDate)) {
                        map.put("SFCQ", 0);
                        map.put("RES_TIME", "超期");
                    } else {
                        map.put("SFCQ", 1);
                        map.put("RES_TIME", "未超期");
                    }
                } else {
                    map.put("SFCQ", 1);
                    map.put("RES_TIME", "未超期");
                }
            }
        }
    }

    /**
     * 
     * 描述：是否超期
     * @author Water Guo
     * @created 2017-3-23 下午2:16:06
     * @param list
     */
    public void setSFCQ(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            String endTime = (String) map.get("END_TIME");
            if (null != endTime) {
                String beginTime = (String) map.get("CREATE_TIME");
                int totalDay = workdayDao.getWorkDayCount(beginTime, endTime);
                String crsj = map.get("CNQXGZR").toString();
                int crDay = Integer.parseInt(crsj);
                int rDay = crDay - totalDay;
                if (rDay >= 0) {
                    map.put("SFCQ", 1);
                } else {
                    map.put("SFCQ", 0);
                }
            }
        }
    }

    /**
     * 描述 流程历史数据查询
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午9:20:33
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findHisBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*, tws.cnqxgzr from jbpm6_execution t"
                + "  left join t_wsbs_serviceitem tws on tws.item_code = t.item_code where 1 = 1"
                + "   and t.run_status != '0' and run_status != '1' and t.is_filed != '1'"
                + "   and t.end_time is not null and (round((to_date(to_char(sysdate, 'yyyy-MM-dd hh24:mi:ss'),"
                + "     'yyyy-MM-dd hh24:mi:ss') - to_date(t.end_time, 'yyyy-MM-dd hh24:mi:ss')), 0) > "
                + "   (select dic_name from t_msjw_system_dictionary dic where dic.dic_code = 'filedFlowRuleItem'))");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
    }

    /**
     * 描述 已归档流程查询
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午9:52:20
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findHisFiledFlowBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" SELECT T.*,TWS.CNQXGZR FROM JBPM6_EXECUTION_EVEHIS T ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM TWS ON TWS.ITEM_CODE=T.ITEM_CODE  ");
        /*获取用户部门树形等级为3的部门*/
        /*  SysUser loginUser = AppUtil.getLoginUser();
      if (!loginUser.getUsername().equals("admin")) {
            String departId = loginUser.getDepId();
            Map<String, Object> departInfo = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[]{"DEPART_ID"}, new Object[]{departId});
            if (departInfo != null) {
                String treeLevel = departInfo.get("TREE_LEVEL").toString();
                sql.append(" and t.SSBMBM = (select DEPART_CODE from T_MSJW_SYSTEM_DEPARTMENT ");
                if (treeLevel.equals("4")) {
                    sql.append(" where DEPART_ID = '" + departInfo.get("PARENT_ID") + "') ");
                } else {
                    sql.append(" where DEPART_ID = '" + departId + "') ");
                }
            }
        }*/
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DC ON DC.DEPART_ID=TWS.ZBCS_ID  where  DC.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
    }

    /**
     * 描述 根据办理结束时间查询流程实例表
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午9:29:57
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getExecutionListByEndTime(String startDate, String endDate) {
        // 根据流程办理的开始、结束时间查询流程实例表jbpm6_exection表获取exeIds
        StringBuffer sql = new StringBuffer();
        sql.append("select * from jbpm6_execution t where t.is_filed != '1' and t.end_time >= ? and t.end_time <= ? ");
        return this.dao.findBySql(sql.toString(), new String[] { startDate + " 00:00:00", endDate + " 23:59:59" },
                null);
    }
    
    /**
     * 描述 根据字典配置的归档周期查询流程实例表数据总数
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午10:46:22
     * @return
     */
    public int getExecutionCountByDicCode(){
        StringBuffer sql = new StringBuffer();
        sql.append("select NVL(count(*),0) NEEDCOUNT from jbpm6_execution t where 1 = 1"
                + "   and t.run_status != '0' and run_status != '1' and t.is_filed != '1'"
                + "   and t.end_time is not null and (round((to_date(to_char(sysdate, 'yyyy-MM-dd'),"
                + "     'yyyy-MM-dd') - to_date(substr(t.end_time, 0, 10), 'yyyy-MM-dd')), 0) >= "
                + "   (select dic_name from t_msjw_system_dictionary dic where dic.dic_code = 'filedFlowRuleItem'))");
        return Integer.valueOf(dao.getByJdbc(sql.toString(), null).get("NEEDCOUNT").toString());
    }

    /**
     * 描述 根据字典配置的归档周期查询流程实例表数据
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午10:46:22
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getExecutionListByDicCode() {
        StringBuffer sql = new StringBuffer();
        PagingBean paginBean = new PagingBean();
        paginBean.setStart(0);
        paginBean.setPageSize(500);
        sql.append("select t.*, tws.cnqxgzr from jbpm6_execution t"
                + "  left join t_wsbs_serviceitem tws on tws.item_code = t.item_code where 1 = 1"
                + "   and t.run_status != '0' and run_status != '1' and t.is_filed != '1'"
                + "   and t.end_time is not null and (round((to_date(to_char(sysdate, 'yyyy-MM-dd'),"
                + "     'yyyy-MM-dd') - to_date(substr(t.end_time, 0, 10), 'yyyy-MM-dd')), 0) >= "
                + "   (select dic_name from t_msjw_system_dictionary dic where dic.dic_code = 'filedFlowRuleItem'))");
        return this.dao.findBySql(sql.toString(), new String[] {}, paginBean);
    }
    
    /**
     * 描述 删除已归档的流程实例
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:11:02
     */
    public void deleteExecutionByIsFiled() {
        StringBuffer sql = new StringBuffer();
        sql.append(" delete from jbpm6_execution t where t.is_filed = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[] {"1"});
    }
    
    /**
     * 描述 删除已归档的流程实例
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:11:02
     */
    public void deleteTaskByIsFiled() {
        StringBuffer sql = new StringBuffer();
        sql.append(" delete from jbpm6_task t where t.is_filed = '1' ");
        this.jdbcTemplate.update(sql.toString());
    }
    
    /**
     * 描述 删除已归档的流程实例
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:11:02
     */
    public void deleteResultByIsFiled() {
        StringBuffer sql = new StringBuffer();
        sql.append(" delete from jbpm6_flow_result t where t.is_filed = '1' ");
        this.jdbcTemplate.update(sql.toString());
    }
}
