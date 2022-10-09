/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.statis.dao.StatisticsDao;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.WorkdayService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2016年3月8日 上午11:18:29
 */
@Service("statisticsService")
public class StatisticsServiceImpl extends BaseServiceImpl implements StatisticsService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(StatisticsServiceImpl.class);

    /**
     * 所引入的dao
     */
    @Resource
    private StatisticsDao dao;
    /**
     * 
     */
    @Resource
    private DepartmentService departmentService;
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
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     *
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Flex Hu
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
     * 描述 根据日期保存统计数据
     * 
     * @author Flex Hu
     * @created 2016年3月8日 下午1:46:44
     * @param date
     */
    public void saveOrUpdateStatis(String date) {
        log.info("开始执行办件统计同步任务任务......");
        List<Map<String, Object>> depList = this.departmentService.findDepList();
        String beginTime = date + " 00:00:00", endTime = date + " 23:59:59";
        for (Map<String, Object> dep : depList) {
            if (isDepVirtual(dep.get("DEPART_ID").toString()))
                continue;
            String depName = (String) dep.get("DEPART_NAME"), depCode = (String) dep.get("DEPART_CODE");
            Map<String, Object> staticsInfo = dao.getByJdbc("JBPM6_STATIST",
                    new String[] { "DEP_CODE", "STATIST_DATE" }, new Object[] { depCode, date });
            if (staticsInfo == null) {
                staticsInfo = new HashMap<String, Object>();
            }
            SqlFilter filter = new SqlFilter(new PagingBean());
            filter.addFilter("Q_D.DEPART_CODE_EQ", depCode);
            filter.addFilter("Q_E.CREATE_TIME_>=", beginTime);
            filter.addFilter("Q_E.CREATE_TIME_<=", endTime);
            filter.removeFilter("E.CREATE_TIME");
            int SJ_XJ = dao.getStaticCount(filter);// 获取收件的小计
            filter.addFilter("Q_S.SXLX_EQ", "1");// 获取收件即办件小计
            int SJ_JBJXJ = dao.getStaticCount(filter);
            filter.addFilter("Q_S.SXLX_EQ", "2");// 获取收件普通件小计
            int SJ_PTJXJ = dao.getStaticCount(filter);

            // 获取办件渠道（网上申请）办件小计
            filter.removeFilter("Q_S.SXLX_EQ");
            filter.addFilter("Q_E.SQFS_EQ", "1");
            int WSSQ_XJ = dao.getStaticCount(filter);
            // 获取办件渠道（窗口收件）办件小计
            filter.addFilter("Q_E.SQFS_EQ", "2");
            int CKSJ_XJ = dao.getStaticCount(filter);
            // 获取办件渠道（省网办发起）办件小计
            filter.addFilter("Q_E.SQFS_EQ", "3");
            int SWFQ_XJ = dao.getStaticCount(filter);
            // 获取办件渠道（省市联动）办件小计
            filter.addFilter("Q_E.SQFS_EQ", "4");
            int SSLD_XJ = dao.getStaticCount(filter);

            filter.removeFilter("Q_E.SQFS_EQ");
            // 获取收件特殊件小计
            filter.addFilter("Q_S.SXLX_EQ", "3");
            int SJ_TSJXJ = dao.getStaticCount(filter);
            filter.removeFilter("Q_E.CREATE_TIME_>=");
            filter.removeFilter("Q_E.CREATE_TIME_<=");
            filter.addFilter("Q_E.END_TIME_>=", beginTime);
            filter.addFilter("Q_E.END_TIME_<=", endTime);
            // 获取办结的小计
            filter.removeFilter("Q_S.SXLX_EQ");
            filter.addFilter("Q_E.RUN_STATUS_IN", "2,3");
            int BJ_XJ = dao.getStaticCount(filter);
            filter.addFilter("Q_S.SXLX_EQ", "1");
            int BJ_JBJ = dao.getStaticCount(filter);// 获取办结即办件小计
            filter.removeFilter("Q_I.EFFI_DESC_EQ");
            filter.addFilter("Q_I.EFFI_DESC_EQ", "2");
            int BJ_JBJAS = dao.getStaticCount(filter);// 获取办结即办件小计按时
            filter.removeFilter("Q_I.EFFI_DESC_EQ");
            filter.addFilter("Q_I.EFFI_DESC_EQ", "3");
            int BJ_JBJYQ = dao.getStaticCount(filter);// 获取办结即办件小计预期
            filter.removeFilter("Q_I.EFFI_DESC_EQ");
//            int BJ_JBJAS = dao.getStaticCountAS(filter);// 获取办结即办件小计按时
//            int BJ_JBJYQ = dao.getStaticCountYQ(filter);// 获取办结即办件小计预期
            filter.removeFilter("Q_S.SXLX_EQ");
            filter.addFilter("Q_S.SXLX_EQ", "2");
            int BJ_PTJ = dao.getStaticCount(filter);// 获取办结普通件小计
            filter.removeFilter("Q_S.SXLX_EQ");
            filter.addFilter("Q_S.SXLX_EQ", "3");
            int BJ_TSJ = dao.getStaticCount(filter);// 获取办结特殊小计
            // 获取办结普通件提前小计
            filter.removeFilter("Q_S.SXLX_EQ");
            filter.addFilter("Q_S.SXLX_EQ", "2");
            filter.addFilter("Q_I.EFFI_DESC_EQ", "1");
            int BJ_PTJTQ = dao.getStaticCount(filter);
            filter.removeFilter("Q_I.EFFI_DESC_EQ");
            filter.addFilter("Q_I.EFFI_DESC_EQ", "2");
            int BJ_PTJAS = dao.getStaticCount(filter);// 获取办结普通件按时小计
            filter.removeFilter("Q_I.EFFI_DESC_EQ");
            filter.addFilter("Q_I.EFFI_DESC_EQ", "3");
            int BJ_PTJYQ = dao.getStaticCount(filter);// 获取办结普通件逾期小计
            filter.removeFilter("Q_I.EFFI_DESC_EQ");
            filter.addFilter("Q_I.EFFI_DESC_EQ", "4");
            int BJ_PTJJB = dao.getStaticCount(filter);// 获取办结普通件即办小计
            // 获取待办信息
            filter = new SqlFilter(new PagingBean());
            // 获取待办小计
            filter.addFilter("Q_E.RUN_STATUS_EQ", "1");
            filter.addFilter("Q_D.DEPART_CODE_EQ", depCode);
            filter.addFilter("Q_E.CREATE_TIME_>=", beginTime);
            filter.addFilter("Q_E.CREATE_TIME_<=", endTime);
            int DB_XJ = dao.getHandingCount(filter);
            // 获取待办补件小计
            filter.addFilter("Q_E.APPLY_STATUS_IN", "4,7");
            int DB_BJ = dao.getHandingCount(filter), DB_ZB = DB_XJ;// 获取待办在办小计
            // 获取退件
            filter.removeFilter("Q_E.RUN_STATUS_EQ");
            filter.removeFilter("Q_E.CREATE_TIME_>=");
            filter.removeFilter("Q_E.CREATE_TIME_<=");
            filter.removeFilter("Q_E.APPLY_STATUS_IN");
            filter.addFilter("Q_E.END_TIME_>=", beginTime);
            filter.addFilter("Q_E.END_TIME_<=", endTime);
            filter.addFilter("Q_E.RUN_STATUS_IN", "4,5,6");
            int TJ_XJ = dao.getHandingCount(filter);
            // 获取退件即办件小计
            filter.addFilter("Q_S.SXLX_EQ", "1");
            int TJ_JBJ = dao.getHandingCount(filter);
            filter.removeFilter("Q_S.SXLX_EQ");
            filter.addFilter("Q_S.SXLX_EQ", "2");
            int TJ_PTJ = dao.getHandingCount(filter);// 获取退件普通件小计
            double PTJ_TQL = 0;
            if (BJ_PTJ != 0) {
                PTJ_TQL = BJ_PTJTQ / BJ_PTJ;
            }
            String PTJ_TQLBFB = StringUtil.getPercentFormat(PTJ_TQL, 2);// 定义普通件提前办结率百分比
            staticsInfo.put("DEP_CODE", depCode);
            staticsInfo.put("DEP_NAME", depName);
            staticsInfo.put("STATIST_DATE", date);
            staticsInfo.put("SJ_XJ", SJ_XJ);
            staticsInfo.put("SJ_JBJXJ", SJ_JBJXJ);
            staticsInfo.put("SJ_PTJXJ", SJ_PTJXJ);
            staticsInfo.put("SJ_TSJXJ", SJ_TSJXJ);
            staticsInfo.put("BJ_XJ", BJ_XJ);
            staticsInfo.put("BJ_JBJ", BJ_JBJ);
            staticsInfo.put("BJ_PTJ", BJ_PTJ);
            staticsInfo.put("BJ_JBJAS", BJ_JBJAS);
            staticsInfo.put("BJ_JBJYQ", BJ_JBJYQ);
            staticsInfo.put("BJ_TSJ", BJ_TSJ);
            staticsInfo.put("BJ_PTJTQ", BJ_PTJTQ);
            staticsInfo.put("BJ_PTJAS", BJ_PTJAS);
            staticsInfo.put("BJ_PTJYQ", BJ_PTJYQ);
            staticsInfo.put("DB_XJ", DB_XJ);
            staticsInfo.put("DB_BJ", DB_BJ);
            staticsInfo.put("DB_ZB", DB_ZB - DB_BJ);
            staticsInfo.put("TJ_XJ", TJ_XJ);
            staticsInfo.put("PTJ_TQL", PTJ_TQL);
            staticsInfo.put("PTJ_TQLBFB", PTJ_TQLBFB);
            staticsInfo.put("BJ_PTJJB", BJ_PTJJB);
            staticsInfo.put("TJ_JBJ", TJ_JBJ);
            staticsInfo.put("TJ_PTJ", TJ_PTJ);
            // 添加办件渠道来源统计
            staticsInfo.put("WSSQ_XJ", WSSQ_XJ);
            staticsInfo.put("CKSJ_XJ", CKSJ_XJ);
            staticsInfo.put("SWFQ_XJ", SWFQ_XJ);
            staticsInfo.put("SSLD_XJ", SSLD_XJ);

            StringBuffer sql = new StringBuffer("select decode(sum(total),null,0,sum(total)) as total");
            sql.append(" from VIEW_BJZTTJ_COUNT_ITEM t where t.depart_code='").append(depCode).append("'");
            sql.append(" and t.CREATE_TIME>='").append(beginTime);
            sql.append("' and t.CREATE_TIME<='").append(endTime).append("'");
            staticsInfo.put("YDJ", dao.getByJdbc(sql.toString(), null).get("total"));
            StringBuffer sql_new = new StringBuffer("select decode(sum(total),null,0,sum(total)) as total");
            sql_new.append(" from VIEW_NEW_BJZTTJ_COUNT_ITEM t where t.depart_code='").append(depCode).append("'");
            sql_new.append(" and t.CREATE_TIME>='").append(beginTime);
            sql_new.append("' and t.CREATE_TIME<='").append(endTime).append("'");
            staticsInfo.put("YDJ_NEW", dao.getByJdbc(sql_new.toString(), null).get("total"));

            String entityId = (String) staticsInfo.get("STATIC_ID");
            dao.saveOrUpdate(staticsInfo, "JBPM6_STATIST", entityId);
        }
        log.info("结束执行办件统计同步任务任务......");
    }

    /**
     * 
     * 描述 是否虚拟部门
     * 
     * @author Danto Huang
     * @created 2016-5-19 下午4:24:38
     * @param departId
     * @return
     */
    private boolean isDepVirtual(String departId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String mappingDep = properties.getProperty(departId);
        if (StringUtils.isEmpty(mappingDep)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * 描述 更加时间段保存数据
     * 
     * @author Flex Hu
     * @created 2016年3月8日 下午4:13:07
     * @param beginDate
     * @param endDate
     */
    public void saveOrUpdateStatis(String beginDate, String endDate) {
        long count = DateTimeUtil.getIntervalTime(beginDate, endDate, "yyyy-MM-dd", 4);
        Date nowdate = DateTimeUtil.getDateOfStr(beginDate, "yyyy-MM-dd");
        for (int i = 0; i <= count; i++) {
            Date nextDay = DateTimeUtil.getNextDay(nowdate, i);
            String targetDate = DateTimeUtil.getStrOfDate(nextDay, "yyyy-MM-dd");
            this.saveOrUpdateStatis(targetDate);
        }
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        if (sqlFilter.getRequest().getParameter("Q_T.STATIST_DATE_>=") != null) {
            sqlFilter.removeFilter("Q_T.STATIST_DATE_GE");
        }
        StringBuffer totalSql = new StringBuffer("SELECT ''AS PARENT_NAME,SYSDATE AS DEP_CODE,'总计' AS DEP_NAME,");
        totalSql.append("SUM(T.YDJ) YDJ,SUM(T.YDJ_NEW) YDJ_NEW,");
        totalSql.append("SUM(T.WSSQ_XJ) WSSQ_XJ,SUM(T.CKSJ_XJ) CKSJ_XJ,SUM(T.SWFQ_XJ) SWFQ_XJ,SUM(T.SSLD_XJ) SSLD_XJ,");
        totalSql.append("SUM(T.SJ_XJ) SJ_XJ" + ",SUM(T.SJ_JBJXJ) SJ_JBJXJ,SUM(T.SJ_PTJXJ) SJ_PTJXJ,");
        totalSql.append("SUM(T.SJ_TSJXJ) SJ_TSJXJ,SUM(T.BJ_XJ) BJ_XJ,SUM(T.BJ_JBJ) BJ_JBJ,");
        totalSql.append("SUM(T.BJ_JBJAS) BJ_JBJAS,SUM(T.BJ_JBJYQ) BJ_JBJYQ,SUM(T.BJ_TSJ) BJ_TSJ,");
        totalSql.append("SUM(T.BJ_PTJ) BJ_PTJ,SUM(T.BJ_PTJTQ) BJ_PTJTQ,SUM(T.BJ_PTJAS) BJ_PTJAS");
        totalSql.append(",SUM(T.BJ_PTJYQ) BJ_PTJYQ,SUM(T.BJ_PTJJB) BJ_PTJJB,SUM(T.DB_XJ) DB_XJ,SUM(T.DB_ZB) DB_ZB,");
        totalSql.append("SUM(T.DB_BJ) DB_BJ,SUM(T.TJ_XJ) TJ_XJ,SUM(T.BJ_PTJJB+T.BJ_JBJ) JBL FROM JBPM6_STATIST T ");
        totalSql.append(" WHERE 1=1 AND T.DEP_CODE NOT LIKE 'z0000000%' AND T.DEP_CODE NOT LIKE 'x0000000%' ");// 去除乡镇
        String realTotalSql = dao.getQuerySql(sqlFilter, totalSql.toString(), params);
        List<Map<String, Object>> totalList = dao.findBySql(realTotalSql, params.toArray(), null);

        StringBuffer sql = new StringBuffer("SELECT CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN T.DEP_NAME ");
        sql.append("ELSE F.DEPART_NAME END AS PARENT_NAME,T.DEP_CODE,T.DEP_NAME,");
        sql.append(" decode(SUM(T.YDJ),null,0,SUM(T.YDJ)) YDJ,SUM(T.YDJ_NEW) YDJ_NEW, ");
        sql.append("SUM(T.WSSQ_XJ) WSSQ_XJ,SUM(T.CKSJ_XJ) CKSJ_XJ,SUM(T.SWFQ_XJ) SWFQ_XJ,SUM(T.SSLD_XJ) SSLD_XJ,");
        sql.append("SUM(T.SJ_XJ) SJ_XJ,SUM(T.SJ_JBJXJ) SJ_JBJXJ,SUM(T.SJ_PTJXJ) SJ_PTJXJ,");
        sql.append("SUM(T.SJ_TSJXJ) SJ_TSJXJ,SUM(T.BJ_XJ) BJ_XJ,");
        sql.append("SUM(T.BJ_JBJ) BJ_JBJ,SUM(T.BJ_JBJAS) BJ_JBJAS,SUM(T.BJ_JBJYQ) BJ_JBJYQ,");
        sql.append("SUM(T.BJ_TSJ) BJ_TSJ,SUM(T.BJ_PTJ) BJ_PTJ,SUM(T.BJ_PTJTQ) BJ_PTJTQ,");
        sql.append("SUM(T.BJ_PTJAS) BJ_PTJAS,SUM(T.BJ_PTJYQ) BJ_PTJYQ,SUM(T.BJ_PTJJB) BJ_PTJJB,SUM(T.DB_XJ) DB_XJ,");
        sql.append("SUM(T.DB_ZB) DB_ZB,SUM(T.DB_BJ) DB_BJ,SUM(T.TJ_XJ) TJ_XJ,SUM(T.BJ_PTJJB+T.BJ_JBJ) JBL ");
        sql.append("FROM JBPM6_STATIST T ");
        sql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEP_CODE = D.DEPART_CODE ");

//        sql.append("JOIN (SELECT S.DEPART_ID FROM T_CKBS_DEPART_CHILD S UNION select t.DEPART_ID FROM ");
//        sql.append("T_CKBS_DEPART t where t.depart_id not in(SELECT S.Parent_Id FROM T_CKBS_DEPART_CHILD S) ");
        // ---------------取号管理自助取号单位管理子部门管理配置 去掉显示所有部门-------------------
        sql.append("JOIN (SELECT S.DEPART_ID FROM T_CKBS_DEPART_CHILD S where ");
        sql.append("s.parent_id<>'2c90b38a5779662501579f6a0b820e0d' ");
        sql.append(" UNION select t.DEPART_ID FROM T_CKBS_DEPART t ");
        sql.append("where not exists(SELECT S.Parent_Id FROM T_CKBS_DEPART_CHILD S where s.parent_id = t.depart_id) ");
        sql.append(") CS ON CS.DEPART_ID = D.DEPART_ID ");
        // ---------------------------------------------------------------------------------
        sql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT F ON F.DEPART_ID = D.PARENT_ID ");
        params = new ArrayList<Object>();
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " GROUP BY F.DEPART_NAME,T.DEP_CODE,T.DEP_NAME,F.TREE_SN,D.TREE_SN "
                + "ORDER BY (CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN D.TREE_SN ELSE F.TREE_SN END),D.TREE_SN";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        // ------不动产------
        StringBuffer bdcSql = new StringBuffer("SELECT CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN T.DEP_NAME ");
        bdcSql.append("ELSE F.DEPART_NAME END AS PARENT_NAME,T.DEP_CODE,T.DEP_NAME,");
        bdcSql.append(" decode(SUM(T.YDJ),null,0,SUM(T.YDJ)) YDJ, SUM(T.YDJ_NEW) YDJ_NEW,");
        bdcSql.append("SUM(T.WSSQ_XJ) WSSQ_XJ,SUM(T.CKSJ_XJ) CKSJ_XJ,SUM(T.SWFQ_XJ) SWFQ_XJ,SUM(T.SSLD_XJ) SSLD_XJ,");
        bdcSql.append("SUM(T.SJ_XJ) SJ_XJ,SUM(T.SJ_JBJXJ) SJ_JBJXJ,SUM(T.SJ_PTJXJ) SJ_PTJXJ,");
        bdcSql.append("SUM(T.SJ_TSJXJ) SJ_TSJXJ,SUM(T.BJ_XJ) BJ_XJ,");
        bdcSql.append("SUM(T.BJ_JBJ) BJ_JBJ,SUM(T.BJ_JBJAS) BJ_JBJAS,SUM(T.BJ_JBJYQ) BJ_JBJYQ,");
        bdcSql.append("SUM(T.BJ_TSJ) BJ_TSJ,SUM(T.BJ_PTJ) BJ_PTJ,SUM(T.BJ_PTJTQ) BJ_PTJTQ,");
        bdcSql.append("SUM(T.BJ_PTJAS) BJ_PTJAS,SUM(T.BJ_PTJYQ) BJ_PTJYQ,SUM(T.BJ_PTJJB) BJ_PTJJB,SUM(T.DB_XJ) DB_XJ,");
        bdcSql.append("SUM(T.DB_ZB) DB_ZB,SUM(T.DB_BJ) DB_BJ,SUM(T.TJ_XJ) TJ_XJ,SUM(T.BJ_PTJJB+T.BJ_JBJ) JBL ");
        bdcSql.append("FROM JBPM6_STATIST T ");
        bdcSql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEP_CODE = D.DEPART_CODE ");
        bdcSql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT F ON F.DEPART_ID = D.PARENT_ID ");
        bdcSql.append("where t.dep_code = '201609140001' ");
        params = new ArrayList<Object>();
        String exebdcSql = dao.getQuerySql(sqlFilter, bdcSql.toString(), params);
        exebdcSql += " GROUP BY F.DEPART_NAME,T.DEP_CODE,T.DEP_NAME,F.TREE_SN,D.TREE_SN "
                + "ORDER BY (CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN D.TREE_SN ELSE F.TREE_SN END),D.TREE_SN";
        List<Map<String, Object>> bdcList = dao.findBySql(exebdcSql, params.toArray(), null);
        // ------不动产预登记------
        StringBuffer bdcydjSql = new StringBuffer("select sum(YDJ) YDJZL,sum(YDJ_NEW) YDJZL_NEW from ( ");
        bdcydjSql.append("SELECT CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN T.DEP_NAME ");
        bdcydjSql.append("ELSE F.DEPART_NAME END AS PARENT_NAME,T.DEP_CODE,T.DEP_NAME,");
        bdcydjSql.append(" decode(SUM(T.YDJ),null,0,SUM(T.YDJ)) YDJ, ");
        bdcydjSql.append(" decode(SUM(T.YDJ_NEW),null,0,SUM(T.YDJ_NEW)) YDJ_NEW, ");
        bdcydjSql
                .append("SUM(T.WSSQ_XJ) WSSQ_XJ,SUM(T.CKSJ_XJ) CKSJ_XJ,SUM(T.SWFQ_XJ) SWFQ_XJ,SUM(T.SSLD_XJ) SSLD_XJ,");
        bdcydjSql.append("SUM(T.SJ_XJ) SJ_XJ,SUM(T.SJ_JBJXJ) SJ_JBJXJ,SUM(T.SJ_PTJXJ) SJ_PTJXJ,");
        bdcydjSql.append("SUM(T.SJ_TSJXJ) SJ_TSJXJ,SUM(T.BJ_XJ) BJ_XJ,");
        bdcydjSql.append("SUM(T.BJ_JBJ) BJ_JBJ,SUM(T.BJ_TSJ) BJ_TSJ,SUM(T.BJ_PTJ) BJ_PTJ,SUM(T.BJ_PTJTQ) BJ_PTJTQ,");
        bdcydjSql.append("SUM(T.BJ_PTJAS) BJ_PTJAS,SUM(T.BJ_PTJYQ) BJ_PTJYQ,");
        bdcydjSql.append("SUM(T.BJ_PTJJB) BJ_PTJJB,SUM(T.DB_XJ) DB_XJ,");
        bdcydjSql.append("SUM(T.DB_ZB) DB_ZB,SUM(T.DB_BJ) DB_BJ,SUM(T.TJ_XJ) TJ_XJ,SUM(T.BJ_PTJJB+T.BJ_JBJ) JBL ");
        bdcydjSql.append("FROM JBPM6_STATIST T ");
        bdcydjSql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEP_CODE = D.DEPART_CODE ");
        bdcydjSql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT F ON F.DEPART_ID = D.PARENT_ID ");
//        bdcSql.append("where f.depart_id = '2c90b38a5779662501579f6a0b820e0d' ");
        bdcydjSql.append("where t.dep_code in ('20161007001','20161007003',");
        bdcydjSql.append("'20161007002','20161007009','20170713','20170919001','20171103','20161007012',");
        bdcydjSql.append("'2017051601','20170527001','20170414',");
        bdcydjSql.append("'20161007004','20161007005','20161007006','20161007007','20161007008') ");
        params = new ArrayList<Object>();
        String exebdcydjSql = dao.getQuerySql(sqlFilter, bdcydjSql.toString(), params);
        exebdcydjSql += " GROUP BY F.DEPART_NAME,T.DEP_CODE,T.DEP_NAME,F.TREE_SN,D.TREE_SN "
                + "ORDER BY (CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN D.TREE_SN ELSE F.TREE_SN END),D.TREE_SN)";
        List<Map<String, Object>> bdcydjList = dao.findBySql(exebdcydjSql, params.toArray(), null);
        // --------
        if (list != null && list.size() > 0) {
            list.add(0, totalList.get(0));
        }
        if (bdcList != null && bdcList.size() > 0) {
//            list.add(bdcList.get(0));
            Map<String, Object> mapbdc;
            mapbdc = bdcList.get(0);
            if (bdcydjList != null && bdcydjList.size() > 0) {
                mapbdc.put("YDJ", bdcydjList.get(0).get("YDJZL"));
                mapbdc.put("YDJ_NEW", bdcydjList.get(0).get("YDJZL_NEW"));
            }
            list.add(mapbdc);
        }
        for (Map<String, Object> map : list) {
            int BJ_PTJ = Integer.parseInt(map.get("BJ_PTJ").toString());
            int BJ_PTJTQ = Integer.parseInt(map.get("BJ_PTJTQ").toString());
            int BJ_PTJJB = Integer.parseInt(map.get("BJ_PTJJB").toString());
            double PTJ_TQL = 0;
            // 获取普通件提前办结率
            if (BJ_PTJ != 0) {
                PTJ_TQL = (double) (BJ_PTJTQ + BJ_PTJJB) / BJ_PTJ;
            }

            int JBL = Integer.parseInt(map.get("JBL").toString());
            map.remove("JBL");

            // 定义普通件提前办结率百分比
            String PTJ_TQLBFB = StringUtil.getPercentFormat(PTJ_TQL, 2);
            map.put("PTJ_TQLBFB", PTJ_TQLBFB);
            int BJ_XJ = Integer.parseInt(map.get("BJ_XJ").toString()) - Integer.parseInt(map.get("BJ_TSJ").toString());
            double JBJL_VALUE = 0;
            if (BJ_XJ != 0) {
                JBJL_VALUE = (double) JBL / BJ_XJ;
            }
            // 即办结率百分比
            String JBJL = StringUtil.getPercentFormat(JBJL_VALUE, 2);
            map.put("JBL", JBL);
            map.put("JBJL", JBJL);
        }

        return list;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findXZBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params;
        // ------乡镇------
        StringBuffer xzsql = new StringBuffer("SELECT CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN T.DEP_NAME ");
        xzsql.append("ELSE F.DEPART_NAME END AS PARENT_NAME,T.DEP_CODE,T.DEP_NAME,");
        // xzsql.append(" decode(SUM(T.YDJ),null,0,SUM(T.YDJ)) YDJ, ");
        xzsql.append("SUM(T.SJ_XJ) SJ_XJ,SUM(T.SJ_JBJXJ) SJ_JBJXJ,SUM(T.SJ_PTJXJ) SJ_PTJXJ,");
        xzsql.append("SUM(T.SJ_TSJXJ) SJ_TSJXJ,SUM(T.BJ_XJ) BJ_XJ,");
        xzsql.append("SUM(T.BJ_JBJ) BJ_JBJ,SUM(T.BJ_TSJ) BJ_TSJ,SUM(T.BJ_PTJ) BJ_PTJ,SUM(T.BJ_PTJTQ) BJ_PTJTQ,");
        xzsql.append("SUM(T.BJ_PTJAS) BJ_PTJAS,SUM(T.BJ_PTJYQ) BJ_PTJYQ,SUM(T.BJ_PTJJB) BJ_PTJJB,SUM(T.DB_XJ) DB_XJ,");
        xzsql.append("SUM(T.DB_ZB) DB_ZB,SUM(T.DB_BJ) DB_BJ,SUM(T.TJ_XJ) TJ_XJ,SUM(T.BJ_PTJJB+T.BJ_JBJ) JBL ");
        xzsql.append("FROM JBPM6_STATIST T ");
        xzsql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEP_CODE = D.DEPART_CODE ");
        xzsql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT F ON F.DEPART_ID = D.PARENT_ID ");
        xzsql.append("where (t.dep_code like 'z000%'  ");
        xzsql.append("  or t.dep_code like 'x000%') ");
        params = new ArrayList<Object>();
        String exexzsql = dao.getQuerySql(sqlFilter, xzsql.toString(), params);
        exexzsql += " GROUP BY F.DEPART_NAME,T.DEP_CODE,T.DEP_NAME,F.TREE_SN,D.TREE_SN "
                + "ORDER BY (CASE F.DEPART_NAME WHEN '平潭综合实验区' THEN D.TREE_SN ELSE F.TREE_SN END),D.TREE_SN";
        List<Map<String, Object>> list = dao.findBySql(exexzsql, params.toArray(), null);

        for (Map<String, Object> map : list) {
            int BJ_PTJ = Integer.parseInt(map.get("BJ_PTJ").toString());
            int BJ_PTJTQ = Integer.parseInt(map.get("BJ_PTJTQ").toString());
            double PTJ_TQL = 0;
            // 获取普通件提前办结率
            if (BJ_PTJ != 0) {
                PTJ_TQL = (double) BJ_PTJTQ / BJ_PTJ;
            }

            int JBL = Integer.parseInt(map.get("JBL").toString());
            map.remove("JBL");

            // 定义普通件提前办结率百分比
            String PTJ_TQLBFB = StringUtil.getPercentFormat(PTJ_TQL, 2);
            // map.put("PTJ_TQL", PTJ_TQL);
            map.put("PTJ_TQLBFB", PTJ_TQLBFB);
            int BJ_XJ = Integer.parseInt(map.get("BJ_XJ").toString()) - Integer.parseInt(map.get("BJ_TSJ").toString());
            double JBJL_VALUE = 0;
            if (BJ_XJ != 0) {
                JBJL_VALUE = (double) JBL / BJ_XJ;
            }
            // 即办结率百分比
            String JBJL = StringUtil.getPercentFormat(JBJL_VALUE, 2);
            map.put("JBL", JBL);
            map.put("JBJL", JBJL);
        }

        return list;
    }

    /**
     * 
     * 描述：事项数量一览
     * 
     * @author Water Guo
     * @created 2017-3-23 下午3:34:01
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findsxslylDataBySqlFilter(SqlFilter sqlFilter) {
        String tjlx = sqlFilter.getQueryParams().get("TJLX") == null ? ""
                : sqlFilter.getQueryParams().get("TJLX").toString();
        String departId = sqlFilter.getQueryParams().get("Q_D.DEPART_ID_EQ") == null ? ""
                : sqlFilter.getQueryParams().get("Q_D.DEPART_ID_EQ").toString();
        String childDepartId = sqlFilter.getQueryParams().get("Q_DC.DEPART_ID_EQ") == null ? ""
                : sqlFilter.getQueryParams().get("Q_DC.DEPART_ID_EQ").toString();

        List<Object> params = new ArrayList<Object>();
        StringBuffer itemNumSql = makeSql(sqlFilter, tjlx, departId, childDepartId, params);

        String realItemNumSql = dao.getQuerySql(sqlFilter, itemNumSql.toString(), params);
        List<Map<String, Object>> itemNumList = dao.findBySql(realItemNumSql, params.toArray(), null);

        StringBuffer totalNumSql = makeTotalSql(sqlFilter, tjlx, departId, childDepartId, params);

        String realTotalNumSql = dao.getQuerySql(sqlFilter, totalNumSql.toString(), params);
        List<Map<String, Object>> totalNumList = dao.findBySql(realTotalNumSql, params.toArray(), null);

        if (itemNumList != null && itemNumList.size() > 0) {
            itemNumList.add(0, totalNumList.get(0));
        }
        return itemNumList;
    }

    private StringBuffer makeTotalSql(SqlFilter sqlFilter, String tjlx, String departId, String childDepartId,
            List<Object> params) {
        StringBuffer totalNumSql = new StringBuffer(" ");
        totalNumSql.append(" SELECT '' as PARENT_DEPART_NAME , '总计' as Depart_Name, ");
        totalNumSql.append(" SUM(TOTAL.ctotal) AS ctotal,SUM(TOTAL.total) AS total, ");
        totalNumSql.append(" SUM(TOTAL.CXKNUM) AS CXKNUM,SUM(TOTAL.XKNUM) AS XKNUM, ");
        totalNumSql.append(" SUM(TOTAL.CGFNUM) AS CGFNUM,SUM(TOTAL.GFNUM) AS GFNUM, ");
        totalNumSql.append(" SUM(TOTAL.CQTNUM) AS CQTNUM,SUM(TOTAL.QTNUM) AS QTNUM, ");
        totalNumSql.append(" SUM(TOTAL.jbjNUM) AS jbjNUM,SUM(TOTAL.cnjNUM) AS cnjNUM, ");
        totalNumSql.append(" SUM(TOTAL.tsjNUM) AS tsjNUM, ");
        totalNumSql.append(" SUM(TOTAL.rzswNUM) AS rzswNUM,SUM(TOTAL.wrzswNUM) AS wrzswNUM, ");
        totalNumSql.append(" round(SUM(TOTAL.rzswNUM)*0.1/SUM(TOTAL.total)*1000,2) as rzbl, ");
        totalNumSql.append(" SUM(TOTAL.ktwsNUM) AS ktwsNUM,SUM(TOTAL.wktwsNUM) AS wktwsNUM, ");
        totalNumSql.append(" round(SUM(TOTAL.ktwsNUM)*0.1/SUM(TOTAL.total)*1000,2) as ktbl,  ");
        totalNumSql.append(" SUM(TOTAL.ZDPYTB) AS ZDPYT,SUM(TOTAL.ZDPLTB) AS ZDPLT ");
        totalNumSql.append(" from ( ");
        totalNumSql.append(" select d.depart_name as parent_depart_name,DC.Depart_Name,");
        totalNumSql.append(" cn.ctotal,count (t.item_code) as total,");
        totalNumSql.append(" cn.CXKNUM,sum(case when T.SXXZ='XK' then 1 else 0 end) as XKNUM, ");
        totalNumSql.append(" cn.CGFNUM,sum(case when T.SXXZ='GF' then 1 else 0 end) as GFNUM, ");
        totalNumSql.append(" cn.CQTNUM,sum(case when T.SXXZ='XK' then ");
        totalNumSql.append(" 0 when T.SXXZ='GF' then 0 else 1 end) as QTNUM, ");
        totalNumSql.append(" sum(case when T.Sxlx='1' then 1 else 0 end) as jbjNUM, ");
        totalNumSql.append(" sum(case when T.Sxlx='2' then 1 else 0 end) as cnjNUM, ");
        totalNumSql.append(" sum(case when T.Sxlx='3' then 1 else 0 end) as tsjNUM, ");
        totalNumSql.append(" sum(case when T.Swb_Item_Code is not null then 1 else 0 end) as rzswNUM, ");
        totalNumSql.append(" sum(case when T.Swb_Item_Code is null then 1 else 0 end) as wrzswNUM, ");
        totalNumSql.append(" sum(case when T.Swb_Item_Code is not null then 1 else 0 end) * 0.1/ ");
        totalNumSql.append(" (sum(case when T.Swb_Item_Code is not null then 1 else 0 end)+ ");
        totalNumSql.append(" sum(case when T.Swb_Item_Code is null then 1 else 0 end))* ");
        totalNumSql.append(" 1000 as rzbl, ");
        totalNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then  ");
        totalNumSql.append(" 1 when T.RZBSDTFS='in05' then 1 else 0 end) as ktwsNUM, ");
        totalNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then ");
        totalNumSql.append(" 0 when T.RZBSDTFS='in05' then 0 else 1 end) as wktwsNUM, ");
        totalNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then  ");
        totalNumSql.append(" 1 when T.RZBSDTFS='in05' then 1 else 0 end) *0.1/ ");
        totalNumSql.append(" (sum(case when T.RZBSDTFS = 'in04' then 1 when T.RZBSDTFS='in05' then 1 else 0 end)+ ");
        totalNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then 0 when T.RZBSDTFS='in05' then 0 else 1 end))* ");
        totalNumSql.append(" 1000 as ktbl , sum(case when replace(t.CKCS_2 || t.CKCS_3 || t.CKCS_4 , '-1' , '') ");
        totalNumSql.append(" = '1' then 1 else 0 end) ZDPYTB , ");
        totalNumSql.append(" sum(case when replace(t.CKCS_2 || t.CKCS_3 || t.CKCS_4 , '-1' , '') = '0'  ");
        totalNumSql.append(" then 1 else 0 end) ZDPLTB ");
        totalNumSql.append(" from T_WSBS_SERVICEITEM t ");
        totalNumSql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE = T.SSBMBM ");
        totalNumSql.append(" LEFT JOIN (select CATALOG_CODE, D1.DEPART_ID,d1.depart_name ");
        totalNumSql.append(" from t_wsbs_serviceitem_catalog C ");
        totalNumSql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ");
        totalNumSql.append(" ON D1.DEPART_ID = C.CHILD_DEPART_ID) DC ");
        totalNumSql.append(" ON DC.CATALOG_CODE = T.CATALOG_CODE ");
        totalNumSql.append(" left join (select c1.depart_id,c1.child_depart_id,d3.depart_code, ");
        totalNumSql.append(" count (c1.catalog_id) as ctotal, ");
        totalNumSql.append(" sum(case when c1.SXXZ='XK' then 1 else 0 end) as CXKNUM, ");
        totalNumSql.append(" sum(case when c1.SXXZ='GF' then 1 else 0 end) as CGFNUM, ");
        totalNumSql.append(" sum(case when c1.SXXZ='XK' then 0 when c1.SXXZ='GF' then 0 else 1 end) as CQTNUM ");
        totalNumSql.append(" from t_wsbs_serviceitem_catalog C1 ");
        totalNumSql.append(" left join T_MSJW_SYSTEM_DEPARTMENT D3 ");
        totalNumSql.append(" on d3.depart_id = c1.child_depart_id ");
        totalNumSql.append(" WHERE C1.STATUS =1 ");
        totalNumSql.append(" group by c1.depart_id,c1.child_depart_id,d3.depart_code) CN ");
        totalNumSql.append(" on cn.child_depart_id=dc.depart_id ");
        totalNumSql.append(" WHERE 1=1 ");
        totalNumSql.append(" and T.FWSXZT = '1' ");
        if (!tjlx.equals("")) {
            if ("0".equals(tjlx)) {
                totalNumSql.append(" ");
            } else if ("1".equals(tjlx)) {
                totalNumSql.append(" and t.swb_item_code is not null ");
            }
            sqlFilter.removeFilter("TJLX");
        }
        params.clear();
        if (!departId.equals("")) {
            totalNumSql.append(" and d.depart_id = ? ");
            params.add(departId);
            sqlFilter.removeFilter("Q_D.DEPART_ID_EQ");
        }
        if (!childDepartId.equals("")) {
            totalNumSql.append(" and DC.depart_id = ? ");
            params.add(childDepartId);
            sqlFilter.removeFilter("Q_DC.DEPART_ID_EQ");
        }
        totalNumSql.append(" group by  d.depart_name,DC.Depart_Name,cn.CXKNUM,cn.CGFNUM,cn.CQTNUM,cn.ctotal ");
        totalNumSql.append(" order by d.depart_name ");
        totalNumSql.append(" ) TOTAL ");
        return totalNumSql;
    }

    private StringBuffer makeSql(SqlFilter sqlFilter, String tjlx, String departId, String childDepartId,
            List<Object> params) {
        StringBuffer itemNumSql = new StringBuffer(" ");
        itemNumSql.append(" select d.depart_name as parent_depart_name,DC.Depart_Name,");
        itemNumSql.append(" cn.ctotal,count (t.item_code) as total,");
        itemNumSql.append(" cn.CXKNUM,sum(case when T.SXXZ='XK' then 1 else 0 end) as XKNUM, ");
        itemNumSql.append(" cn.CGFNUM,sum(case when T.SXXZ='GF' then 1 else 0 end) as GFNUM, ");
        itemNumSql.append(" cn.CQTNUM,sum(case when T.SXXZ='XK' then 0 when T.SXXZ='GF' then 0 else 1 end)  ");
        itemNumSql.append(" as QTNUM, ");
        itemNumSql.append(" sum(case when T.Sxlx='1' then 1 else 0 end) as jbjNUM, ");
        itemNumSql.append(" sum(case when T.Sxlx='2' then 1 else 0 end) as cnjNUM, ");
        itemNumSql.append(" sum(case when T.Sxlx='3' then 1 else 0 end) as tsjNUM, ");
        itemNumSql.append(" sum(case when T.Swb_Item_Code is not null then 1 else 0 end) as rzswNUM, ");
        itemNumSql.append(" sum(case when T.Swb_Item_Code is null then 1 else 0 end) as wrzswNUM, ");
        itemNumSql.append(" round(sum(case when T.Swb_Item_Code is not null then 1 else 0 end) * 0.1/ ");
        itemNumSql.append(" (sum(case when T.Swb_Item_Code is not null then 1 else 0 end)+ ");
        itemNumSql.append(" sum(case when T.Swb_Item_Code is null then 1 else 0 end))* ");
        itemNumSql.append(" 1000,2) as rzbl, ");
        itemNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then ");
        itemNumSql.append(" 1 when T.RZBSDTFS='in05' then 1 else 0 end) as ktwsNUM, ");
        itemNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then ");
        itemNumSql.append(" 0 when T.RZBSDTFS='in05' then 0 else 1 end) as wktwsNUM, ");
        itemNumSql.append(" round(sum(case when T.RZBSDTFS = 'in04' then 1 when T.RZBSDTFS='in05' then 1 ");
        itemNumSql.append(" else 0 end) *0.1/  ");
        itemNumSql.append(" (sum(case when T.RZBSDTFS = 'in04' then 1 when T.RZBSDTFS='in05' then 1 else 0 end)+ ");
        itemNumSql.append(" sum(case when T.RZBSDTFS = 'in04' then 0 when T.RZBSDTFS='in05' then 0 else 1 end))* ");
        itemNumSql.append(" 1000,2) as ktbl , sum(case when replace(t.CKCS_2 || t.CKCS_3 || t.CKCS_4 , '-1' , '') ");
        itemNumSql.append(" = '1' then 1 else 0 end) ZDPYT , ");
        itemNumSql.append(" sum(case when replace(t.CKCS_2 || t.CKCS_3 || t.CKCS_4 , '-1' , '') = '0' ");
        itemNumSql.append(" then 1 else 0 end) ZDPLT ");
        itemNumSql.append(" from T_WSBS_SERVICEITEM t ");
        itemNumSql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE = T.SSBMBM ");
        itemNumSql.append(" LEFT JOIN (select CATALOG_CODE, D1.DEPART_ID,d1.depart_name ");
        itemNumSql.append(" from t_wsbs_serviceitem_catalog C ");
        itemNumSql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ");
        itemNumSql.append(" ON D1.DEPART_ID = C.CHILD_DEPART_ID) DC ");
        itemNumSql.append(" ON DC.CATALOG_CODE = T.CATALOG_CODE ");
        itemNumSql.append(" left join (select c1.depart_id,c1.child_depart_id,d3.depart_code, ");
        itemNumSql.append(" count (c1.catalog_id) as ctotal, ");
        itemNumSql.append(" sum(case when c1.SXXZ='XK' then 1 else 0 end) as CXKNUM, ");
        itemNumSql.append(" sum(case when c1.SXXZ='GF' then 1 else 0 end) as CGFNUM, ");
        itemNumSql.append(" sum(case when c1.SXXZ='XK' then 0 when c1.SXXZ='GF' then 0 else 1 end) as CQTNUM ");
        itemNumSql.append(" from t_wsbs_serviceitem_catalog C1 ");
        itemNumSql.append(" left join T_MSJW_SYSTEM_DEPARTMENT D3 ");
        itemNumSql.append(" on d3.depart_id = c1.child_depart_id ");
        itemNumSql.append(" WHERE C1.STATUS =1 ");
        itemNumSql.append(" group by c1.depart_id,c1.child_depart_id,d3.depart_code) CN ");
        itemNumSql.append(" on cn.child_depart_id=dc.depart_id ");
        itemNumSql.append(" WHERE 1=1 ");
        itemNumSql.append(" and T.FWSXZT = '1' ");
        if (!tjlx.equals("")) {
            if ("0".equals(tjlx)) {
                itemNumSql.append(" ");
            } else if ("1".equals(tjlx)) {
                itemNumSql.append(" and t.swb_item_code is not null ");
            }
            sqlFilter.removeFilter("TJLX");
        }
        if (!departId.equals("")) {
            itemNumSql.append(" and d.depart_id = ? ");
            params.add(departId);
            sqlFilter.removeFilter("Q_D.DEPART_ID_EQ");
        }
        if (!childDepartId.equals("")) {
            itemNumSql.append(" and DC.depart_id = ? ");
            params.add(childDepartId);
            sqlFilter.removeFilter("Q_DC.DEPART_ID_EQ");
        }
        itemNumSql.append(" group by  d.depart_name,DC.Depart_Name,cn.CXKNUM,cn.CGFNUM,cn.CQTNUM,cn.ctotal  ");
        itemNumSql.append(" order by d.depart_name ");
        return itemNumSql;
    }

    /**
     * 
     * 描述 获取
     * 
     * @author Flex Hu
     * @created 2016年3月9日 上午11:23:57
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findJbjlList(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        // 定义总数SQL
        StringBuffer totalSql = new StringBuffer("SELECT '' AS PARENT_NAME,'总计' AS DEP_NAME,");
        // 定义部门SQL
        StringBuffer depSql = new StringBuffer("SELECT CASE P.DEPART_NAME WHEN '平潭综合实验区' THEN T.DEP_NAME ");
        depSql.append("ELSE P.DEPART_NAME END AS PARENT_NAME,T.DEP_NAME,");
        // 定义公共SQL
        StringBuffer commonSql = new StringBuffer("SUM(T.SJ_JBJXJ+T.SJ_PTJXJ) SJ_XJ,SUM(T.SJ_JBJXJ) SJ_JBJXJ,");
        commonSql.append("SUM(T.SJ_PTJXJ) SJ_PTJXJ,SUM(T.BJ_JBJ+T.BJ_PTJ) BJ_XJ,");
        commonSql.append("SUM(T.BJ_PTJJB) BJ_PTJJB,SUM(T.BJ_PTJTQ) BJ_PTJTQ,SUM(T.BJ_PTJAS) BJ_PTJAS,");
        commonSql.append("SUM(T.BJ_PTJYQ) BJ_PTJYQ,SUM(T.BJ_JBJ) BJ_JBJ,SUM(T.DB_XJ) DB_XJ,");
        commonSql.append("SUM(T.DB_ZB) DB_ZB,SUM(T.DB_BJ) DB_BJ,SUM(T.TJ_XJ) TJ_XJ,");
        commonSql.append("SUM(T.TJ_JBJ) TJ_JBJ,SUM(T.TJ_PTJ) TJ_PTJ,");
        commonSql.append("SUM(T.BJ_PTJJB+T.BJ_JBJ) JBL FROM JBPM6_STATIST T ");
        String realTotalSql = dao.getQuerySql(sqlFilter, totalSql.append(commonSql).toString(), params);
        List<Map<String, Object>> totalList = dao.findBySql(realTotalSql, params.toArray(), null);
        params = new ArrayList<Object>();
        StringBuffer joinsql = new StringBuffer();
        joinsql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEP_CODE = D.DEPART_CODE ");
        joinsql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT P ON P.DEPART_ID = D.PARENT_ID ");
        joinsql.append("JOIN (SELECT S.DEPART_ID FROM T_CKBS_DEPART_CHILD S UNION select t.DEPART_ID FROM ");
        joinsql.append("T_CKBS_DEPART t where ");
        joinsql.append("not exists(SELECT S.Parent_Id FROM T_CKBS_DEPART_CHILD S where s.parent_id = t.depart_id) ");
//        joinsql.append("T_CKBS_DEPART t where t.depart_id not in(SELECT S.Parent_Id FROM T_CKBS_DEPART_CHILD S) ");
        joinsql.append(") CS ");
        joinsql.append("ON CS.DEPART_ID = D.DEPART_ID ");
        String exeSql = dao.getQuerySql(sqlFilter, depSql.append(commonSql).append(joinsql).toString(), params);
        // exeSql += " GROUP BY T.DEP_NAME ORDER BY T.DEP_NAME ASC";
        exeSql += "GROUP BY P.DEPART_NAME,T.DEP_NAME,P.TREE_SN,D.TREE_SN "
                + "ORDER BY (CASE P.DEPART_NAME WHEN '平潭综合实验区' THEN D.TREE_SN ELSE P.TREE_SN END),D.TREE_SN";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        list.add(0, totalList.get(0));
        for (Map<String, Object> map : list) {
            int JBL = Integer.parseInt(map.get("JBL").toString());
            int BJ_XJ = Integer.parseInt(map.get("BJ_XJ").toString());
            double JBJL_VALUE = 0;
            // 获取普通件提前办结率
            if (BJ_XJ != 0) {
                JBJL_VALUE = (double) JBL / BJ_XJ;
            }
            // 定义普通件提前办结率百分比
            String JBJL = StringUtil.getPercentFormat(JBJL_VALUE, 2);
            map.put("JBJL", JBJL);
        }
        return list;
    }

    /**
     * 根据sqlfilter获取到数据列表 (行政服务中心人员办件评议)
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findByUserItemSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select case t.parent_name");
        sql.append(" when '平潭综合实验区' then t.depart_name else t.parent_name end as parent_name");
        sql.append(",t.depart_name,t.CUR_WIN,t.operator,count(*) as counts");
        sql.append(",sum(evaluate_0 + evaluate_1 + evaluate_2 + evaluate_3) as sums");
        sql.append(",sum(t.evaluate_3) as evaluate_3" + ",sum(t.evaluate_2) as evaluate_2");
        sql.append(",sum(t.evaluate_1) as evaluate_1" + ",  sum(t.evaluate_0) as evaluate_0");
        sql.append(" from VIEW_USER_ITEM t");
        sql.append(" left join t_msjw_system_department p on p.depart_name = t.parent_name and p.tree_level=3");
        sql.append(" where t.call_status not in(0,2,6) ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " group by t.CUR_WIN,t.parent_name,t.depart_name,t.operator,t.TREE_SN,p.TREE_SN "
                + "order by (case t.parent_name when '平潭综合实验区' then t.TREE_SN else p.TREE_SN end)"
                + ",t.TREE_SN,t.CUR_WIN";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);

        int COUNTS = 0;
        int SUMS = 0;
        int EVALUATE_0 = 0;
        int EVALUATE_1 = 0;
        int EVALUATE_2 = 0;
        int EVALUATE_3 = 0;
        for (Map<String, Object> map : list) {
            COUNTS += Integer.parseInt(map.get("COUNTS").toString());
            SUMS += Integer.parseInt(map.get("SUMS").toString());
            EVALUATE_0 += Integer.parseInt(map.get("EVALUATE_0").toString());
            EVALUATE_1 += Integer.parseInt(map.get("EVALUATE_1").toString());
            EVALUATE_2 += Integer.parseInt(map.get("EVALUATE_2").toString());
            EVALUATE_3 += Integer.parseInt(map.get("EVALUATE_3").toString());
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("PARENT_NAME", "");
        map.put("DEPART_NAME", "");
        map.put("CUR_WIN", "");
        map.put("OPERATOR", "总计");
        map.put("COUNTS", COUNTS);
        map.put("SUMS", SUMS);
        map.put("EVALUATE_3", EVALUATE_3);
        map.put("EVALUATE_2", EVALUATE_2);
        map.put("EVALUATE_1", EVALUATE_1);
        map.put("EVALUATE_0", EVALUATE_0);
        list.add(map);
        return list;
    }

    /**
     * 根据sqlfilter获取到数据列表 (审批事项统计表)
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findByEffiItemSqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.ITEM_NAME,");
        sql.append(" count(t.ITEM_NAME) as COUNTS,");
        sql.append(" sum(t.effi_desc_1) as EFFI_DESC_1,");
        sql.append(" sum(t.effi_desc_2) as EFFI_DESC_2,");
        sql.append(" sum(t.effi_desc_3) as EFFI_DESC_3,");
        sql.append(" sum(t.effi_desc_4) as EFFI_DESC_4");
        sql.append(" from VIEW_EFFI_ITEM t where 1=1 ");

        StringBuffer sql2 = new StringBuffer("select t.ITEM_NAME,");
        sql2.append(" 0 as COUNTS, 0 as EFFI_DESC_1,");
        sql2.append(" 0 as EFFI_DESC_2, 0 as EFFI_DESC_3, 0 as EFFI_DESC_4");
        sql2.append(" from t_wsbs_serviceitem t");
        sql2.append(" left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_code=t.SSBMBM");
        sql2.append(" where t.item_code not in ");
        sql2.append(" (select e.item_code from view_effi_item e ) and t.fwsxzt = 1");

        if (sqlFilter.getQueryParams().get("Q_T.DEPART_ID_=") != null
                && !sqlFilter.getQueryParams().get("Q_T.DEPART_ID_=").equals("")) {
            String departId = sqlFilter.getQueryParams().get("Q_T.DEPART_ID_=").toString();
            sql.append(" and (T.DEPART_ID = '").append(departId);
            sql.append("' or T.CHILD_DEPART_ID = '").append(departId).append("') ");

            sql2.append(" and (d.DEPART_ID = '").append(departId);
            sql2.append("' or T.ZBCS_ID = '").append(departId).append("') ");
            sqlFilter.removeFilter("Q_T.DEPART_ID_=");
        }
        if (sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ") != null
                && !sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ").equals("")) {
            String departId = sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ").toString();
            sql.append(" and (T.DEPART_ID = '").append(departId);
            sql.append("' or T.CHILD_DEPART_ID = '").append(departId).append("') ");

            sql2.append(" and (d.DEPART_ID = '").append(departId);
            sql2.append("' or T.ZBCS_ID = '").append(departId).append("') ");
            sqlFilter.removeFilter("Q_T.DEPART_ID_EQ");
        }

        // 添加查询条件（办件渠道）
        if (sqlFilter.getQueryParams().get("Q_T.SQFS_EQ") != null
                && !sqlFilter.getQueryParams().get("Q_T.SQFS_EQ").equals("")) {
            String sqfs = sqlFilter.getQueryParams().get("Q_T.SQFS_EQ").toString();
            sql.append(" and T.SQFS = ").append(sqfs);
        }

        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  group by t.item_name order by  COUNTS desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);

        List<Map<String, Object>> list0 = dao.findBySql(sql2.toString(), null, null);
        list.addAll(list0);

        for (Map<String, Object> map : list) {
            double couns = Integer.parseInt(map.get("COUNTS").toString());// 总数
            double EFFI_DESC_1 = Integer.parseInt(map.get("EFFI_DESC_1").toString());// 提前办结数
            double EFFI_DESC_3 = Integer.parseInt(map.get("EFFI_DESC_3").toString());// 超期办结数
            double tqbjl = 0;// 提前办结率
            double cqbjl = 0;// 超期办结率
            if (EFFI_DESC_1 != 0) {
                tqbjl = EFFI_DESC_1 / couns;
            }
            if (EFFI_DESC_3 != 0) {
                cqbjl = EFFI_DESC_3 / couns;
            }
            map.put("TQBJL", StringUtil.getPercentFormat(tqbjl, 2));
            map.put("CQBJL", StringUtil.getPercentFormat(cqbjl, 2));
        }
        return list;
    }

    /**
     * 根据sqlfilter获取到数据列表 (时间段总效率报表)
     * 
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findByCountEffiItemSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select ''as times,");
        sql.append(" count(t.ITEM_NAME) as COUNTS,");
        sql.append(" sum(t.effi_desc_1) as EFFI_DESC_1,");
        sql.append(" sum(t.effi_desc_2) as EFFI_DESC_2,");
        sql.append(" sum(t.effi_desc_3) as EFFI_DESC_3,");
        sql.append(" sum(t.effi_desc_4) as EFFI_DESC_4");
        sql.append(" from VIEW_EFFI_ITEM t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  order by  COUNTS desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        for (Map<String, Object> map : list) {
            double couns = map.get("COUNTS") == null ? 0 : Integer.parseInt(map.get("COUNTS").toString());// 总数
            double EFFI_DESC_1 = map.get("EFFI_DESC_1") == null ? 0
                    : Integer.parseInt(map.get("EFFI_DESC_1").toString());// 提前办结数
            double EFFI_DESC_3 = map.get("EFFI_DESC_3") == null ? 0
                    : Integer.parseInt(map.get("EFFI_DESC_3").toString());// 超期办结数
            double tqbjl = 0;// 提前办结率
            double cqbjl = 0;// 超期办结率
            if (EFFI_DESC_1 != 0) {
                tqbjl = EFFI_DESC_1 / couns;
            }
            if (EFFI_DESC_3 != 0) {
                cqbjl = EFFI_DESC_3 / couns;
            }
            map.put("TQBJL", StringUtil.getPercentFormat(tqbjl, 2));
            map.put("CQBJL", StringUtil.getPercentFormat(cqbjl, 2));
        }
        return list;
    }

    /**
     * 
     * 描述 获取时限类型统计数据
     * 
     * @author Flex Hu
     * @created 2016年3月10日 上午11:12:30
     * @param filter
     * @return
     */
    public Map<String, Object> getSxlxStatist(SqlFilter filter) {
        Map<String, Object> info = new HashMap<String, Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer totalSql = new StringBuffer("SELECT COUNT(*) TOTAL_COUNT FROM JBPM6_EXECUTION T");
        totalSql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON T.ITEM_CODE=S.ITEM_CODE");
        totalSql.append(" WHERE S.ITEM_CODE IS NOT NULL ");
        String realTotalSql = dao.getQuerySql(filter, totalSql.toString(), params);
        List<Map<String, Object>> totalList = dao.findBySql(realTotalSql, params.toArray(), null);
        info.put("TOTAL_COUNT", totalList.get(0).get("TOTAL_COUNT"));
        StringBuffer sxlxSql = new StringBuffer("SELECT COUNT(S.SXLX) SXLX_COUNT,SXLX FROM JBPM6_EXECUTION T");
        sxlxSql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON T.ITEM_CODE=S.ITEM_CODE ");
        sxlxSql.append(" WHERE S.ITEM_CODE IS NOT NULL ");
        params = new ArrayList<Object>();
        String exeSql = dao.getQuerySql(filter, sxlxSql.toString(), params);
        exeSql += " GROUP BY S.SXLX ORDER BY S.SXLX ASC";
        List<Map<String, Object>> sxlxList = dao.findBySql(exeSql, params.toArray(), null);
        for (Map<String, Object> sxlx : sxlxList) {
            String sxlxType = (String) sxlx.get("SXLX");
            if (StringUtils.isNotEmpty(sxlxType)) {
                if (sxlxType.equals("1")) {
                    sxlx.put("LX_NAME", "即办件");
                } else if (sxlxType.equals("2")) {
                    sxlx.put("LX_NAME", "普通件");
                } else if (sxlxType.equals("3")) {
                    sxlx.put("LX_NAME", "特殊件");
                }
            }
        }
        info.put("sxlxList", sxlxList);
        return info;
    }

    /**
     * 
     * 描述 获取办件统计总数数据
     * 
     * @author Rider Chen
     * @created 2016-3-10 下午03:43:12
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getBjzstjStatist(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select sum(count(t.run_status)) as COUNTS,");
        sql.append(" sum(CASE t.run_status WHEN 0 THEN count(t.run_status) ELSE 0 END) AS cgs,");
        sql.append(" sum(CASE t.run_status WHEN 1 THEN  count(t.run_status) ELSE 0 END) AS zbs,");
        sql.append(" sum(CASE t.run_status WHEN 2 THEN count(t.run_status) ELSE 0 END"
                + "+CASE t.run_status WHEN 3 THEN count(t.run_status) ELSE 0 END) AS bjs,");
        sql.append(" sum(CASE t.run_status WHEN 4 THEN count(t.run_status) ELSE 0 END"
                + "+CASE t.run_status WHEN 5 THEN count(t.run_status) ELSE 0 END"
                + "+CASE t.run_status  WHEN 6 THEN count(t.run_status) ELSE 0 END) AS tjs ");
        sql.append(" from JBPM6_EXECUTION t");
        sql.append(" join t_wsbs_serviceitem t1");
        sql.append(" on t.item_code = t1.item_code");
        sql.append(" where t.run_status in (0,1,2,3,4,5,6)");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  group by t.run_status ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);

        return list;
    }

    /**
     * 
     * 描述 获取办件统计总数数据
     * 
     * @author Rider Chen
     * @created 2016-3-10 下午03:43:12
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> getPyjgtjStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select sum(evaluate_0 + evaluate_1 " + "+ evaluate_2 + evaluate_3) as sums,");
        sql.append(" sum(t.evaluate_3) as evaluate_3,");
        sql.append(" sum(t.evaluate_2) as evaluate_2,");
        sql.append(" sum(t.evaluate_1) as evaluate_1,");
        sql.append(" sum(t.evaluate_0) as evaluate_0");
        sql.append(" from VIEW_USER_ITEM t  ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述 获取收件类型统计数据
     * 
     * @author Rider Chen
     * @created 2016-3-10 下午03:43:12
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> getSjlxtjStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select sum(CASE t.SQFS WHEN '1' THEN  COUNT(t.SQFS)  ELSE 0 END  + ");
        sql.append("CASE t.SQFS WHEN '2' THEN COUNT(t.SQFS)  ELSE  0  END +");
        sql.append("  CASE t.SQFS  WHEN '3' THEN COUNT(t.SQFS) ELSE  0 END) as counts,");
        sql.append(" sum(CASE t.SQFS WHEN '1' THEN COUNT(t.SQFS) ELSE 0 END) as wssq,");
        sql.append("  sum(CASE t.SQFS WHEN '2' THEN COUNT(t.SQFS) ELSE 0 END) as cksj,");
        sql.append("  sum(CASE t.SQFS WHEN '3' THEN COUNT(t.SQFS) ELSE 0 END) as swbsf");
        sql.append("   from VIEW_EFFI_ITEM t");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += "  group by t.SQFS ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述 根据日期获取办结的数量列表
     * 
     * @author Flex Hu
     * @created 2016年3月10日 下午5:28:03
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> findOver(String beginDate, String endDate) {
        StringBuffer sql = new StringBuffer("SELECT COUNT(substr(T.END_TIME, 0, 10)) END_COUNT,");
        sql.append("substr(T.END_TIME, 0, 10) WORK_DAY ");
        sql.append("FROM JBPM6_EXECUTION T WHERE T.RUN_STATUS IN (2,3) ");
        sql.append("AND substr(T.END_TIME, 0, 10) IN (");
        sql.append("SELECT W.W_DATE FROM T_MSJW_SYSTEM_WORKDAY W ");
        sql.append("WHERE W.W_SETID=2 AND W.W_DATE>=?");
        sql.append(" AND W.W_DATE<=? ) AND T.END_TIME IS NOT NULL ");
        sql.append(" AND T.ITEM_CODE IS NOT NULL ");
        sql.append(" GROUP BY substr(T.END_TIME, 0, 10) ORDER BY substr(T.END_TIME, 0, 10) asc");
        return dao.findBySql(sql.toString(), new Object[] { beginDate, endDate }, null);
    }

    /**
     * 
     * 描述获取小于目标时间的收件数量
     * 
     * @author Flex Hu
     * @created 2016年3月10日 下午8:03:30
     * @param date
     * @return
     */
    private int getHandleCount(String date) {
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM JBPM6_EXECUTION E WHERE E.CREATE_TIME<=");
        sql.append("? AND E.ITEM_CODE IS NOT NULL");
        Object count = this.dao.getObjectBySql(sql.toString(), new Object[] { date + " 23:59:59" });
        return Integer.parseInt(count.toString());
    }

    /**
     * 
     * 描述 根据时间获取办结效率信息
     * 
     * @author Flex Hu
     * @created 2016年3月10日 下午7:57:59
     * @param beginDate
     * @param endDate
     * @return
     */
    public Map<String, Object> getBjxlStatist(String beginDate, String endDate) {
        Map<String, Object> info = new HashMap<String, Object>();
        List<Map<String, Object>> list = this.findOver(beginDate, endDate);
        double ZXL = 0.00;
        for (Map<String, Object> map : list) {
            String workDay = map.get("WORK_DAY").toString();
            Integer END_COUNT = Integer.parseInt(map.get("END_COUNT").toString());
            int handleCount = this.getHandleCount(workDay);
            // 定义办结效率
            float BJ_XL = 0;
            if (handleCount != 0) {
                BJ_XL = (float) END_COUNT / handleCount;
            }
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
            String number = df.format(BJ_XL);// 返回的是String类型
            double handleXl = 0;// = StringUtil.getPercentFormatDouble(BJ_XL,
                                // 1);
            ZXL += handleXl;
            map.put("BJ_XL", handleXl);
        }
        double pjxl = ZXL / list.size();
        DecimalFormat df = new DecimalFormat("0.00");
        info.put("PJ_XL", df.format(pjxl));
        info.put("BJ_XLLIST", list);
        return info;
    }

    /**
     * 
     * 描述 获取窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-15 上午09:54:17
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getCkbjltjStatist(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select case td.depart_name  WHEN '平潭综合实验区' then t.depart_name ");
        sql.append(" else td.depart_name end parent_name,");
        sql.append(" T.DEPART_NAME,T.CUR_WIN,sum(CASE T.CALL_STATUS WHEN '-1' THEN  0  ELSE 1 END) as total,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '1' THEN 1 ELSE 0 END) as sl,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '0' THEN 1 ELSE 0 END) as wcl,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '2' THEN 1 ELSE 0 END) as pass,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '6' THEN 1 ELSE 0 END) as jhz, 0 as other,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '5' THEN 1 ELSE 0 END) as bs,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '4' THEN 1 ELSE 0 END) as zx,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '3' THEN 1 ELSE 0 END) as lz");
        sql.append(" ,0 as YTL,0 as QTXD from VIEW_USER_ITEM t ");
        sql.append(" left join t_msjw_system_department tsd on tsd.depart_code = t.depart_code");
        sql.append(" left join t_msjw_system_department td on td.depart_id = tsd.parent_id");
        sql.append(" where 1=1 ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND T.depart_code in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  group by T.CUR_WIN, T.DEPART_NAME,td.depart_name,td.tree_sn,tsd.tree_sn "
                + "order by case td.depart_name  WHEN '平潭综合实验区' then tsd.tree_sn else td.tree_sn end,"
                + "tsd.tree_sn,T.CUR_WIN ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);

        int TOTAL = 0;// 总数
        int SL = 0;// 受理数
        int WCL = 0;// 未处理数
        int PASS = 0;// 过号数
        int JHZ = 0;// 叫号
        int OTHER = 0;// 其他收件数
        int BS = 0;// 办事数
        int ZX = 0;// 咨询数
        int LZ = 0;// 领照数
        int YTL = 0;// 一条龙审批数
        int QTXD = 0;// 其他导向数
        for (Map<String, Object> map : list) {
            TOTAL += Integer.parseInt(map.get("TOTAL").toString());
            SL += Integer.parseInt(map.get("SL").toString());
            WCL += Integer.parseInt(map.get("WCL").toString());
            PASS += Integer.parseInt(map.get("PASS").toString());
            JHZ += Integer.parseInt(map.get("JHZ").toString());
            OTHER += Integer.parseInt(map.get("OTHER").toString());
            BS += Integer.parseInt(map.get("BS").toString());
            ZX += Integer.parseInt(map.get("ZX").toString());
            LZ += Integer.parseInt(map.get("LZ").toString());
            YTL += Integer.parseInt(map.get("YTL").toString());
            QTXD += Integer.parseInt(map.get("QTXD").toString());
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("PARENT_NAME", "");
        map.put("DEPART_NAME", "");
        map.put("CUR_WIN", "总计");
        map.put("TOTAL", TOTAL);
        map.put("SL", SL);
        map.put("WCL", WCL);
        map.put("PASS", PASS);
        map.put("JHZ", JHZ);
        map.put("OTHER", OTHER);
        map.put("BS", BS);
        map.put("ZX", ZX);
        map.put("LZ", LZ);
        map.put("YTL", YTL);
        map.put("QTXD", QTXD);
        list.add(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> findDeptEffByFilter(SqlFilter sqlFilter, String begin, String end, String id) {
        // List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        // sql.append("round(chaoqi/BJ_XJ,2)*100||'%'
        // chaoql,round(anshi/BJ_XJ,2)*100||'%' anshil");
        // sql.append(" SELECT T.DEP_CODE,T.DEP_NAME,SUM(T.BJ_PTJ)
        // BJ_PTJ,SUM(BJ_PTJTQ)");
        // sql.append(" as tiqian,SUM(BJ_PTJAS) anshi,SUM(BJ_PTJYQ) as chaoqi,");
        // sql.append("substr(statist_date,0,7) stadate from JBPM6_STATIST T ");
        // sql.append(" where 1=1 ");

        sql.append(" select T.DEPART_ID,T.DEPART_NAME DEP_NAME,count(t.ITEM_NAME) as BJ_PTJ,");
        sql.append(" sum(t.effi_desc_1) as tiqian,");
        sql.append(" sum(t.effi_desc_2) as anshi,");
        sql.append(" sum(t.effi_desc_3) as chaoqi,");
        sql.append(" sum(t.effi_desc_4) as EFFI_DESC_4,substr(end_time,0,7) stadate ");
        sql.append(" from VIEW_EFFI_ITEM t ");
        sql.append(" where 1=1 ");

        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and t.depart_id='" + id + "' ");
        } else {
            sql.append(" and t.depart_id='" + AppUtil.getLoginUser().getUserId() + "' ");
        }
        if (StringUtils.isNotEmpty(begin)) {
            sql.append(" and substr(t.end_time,0,7)>='" + begin + "'");
        }
        if (StringUtils.isNotEmpty(end)) {
            sql.append(" and substr(t.end_time,0,7)<='" + end + "' ");
        }
        // sql.append(" GROUP BY
        // T.DEP_CODE,T.DEP_NAME,substr(statist_date,0,7),PTJ_TQLBFB ORDER BY T.DEP_CODE
        // ASC ");
        sql.append(" GROUP BY T.DEPART_ID,T.DEPART_NAME,substr(end_time,0,7) ORDER BY T.DEPART_ID ASC,stadate desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        for (Map<String, Object> map : list) {
            double tiqian = Integer.parseInt(map.get("tiqian").toString());
            double chaoqi = Integer.parseInt(map.get("chaoqi").toString());
            int sum = Integer.parseInt(map.get("BJ_PTJ").toString());
            double tiqianl = 0;
            double chaoql = 0;
            String chaoqil = "0%";
            String tiql = "0%";
            // 获取普通件提前办结率
            if (sum != 0) {
                tiqianl = tiqian / sum;
                chaoql = chaoqi / sum;
                chaoqil = StringUtil.getPercentFormat(chaoql, 2);
                tiql = StringUtil.getPercentFormat(tiqianl, 2);
            }
            map.put("TIQL", tiql);
            map.put("CHAOQL", chaoqil);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findHorizonEffByFilter(SqlFilter sqlFilter, String begin, String end) {
        // List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select T.DEPART_ID,T.DEPART_NAME,count(t.ITEM_NAME) as BJ_PTJ,");
        sql.append(" sum(t.effi_desc_1) as tiqian,");
        sql.append(" sum(t.effi_desc_2) as anshi,");
        sql.append(" sum(t.effi_desc_3) as chaoqi,");
        sql.append(" sum(t.effi_desc_4) as EFFI_DESC_4");
        sql.append(" from VIEW_EFFI_ITEM t ");
        sql.append(" where 1=1 ");

        if (StringUtils.isNotEmpty(begin)) {
            sql.append(" and end_time>='" + begin + " 00:00:00'");
        }
        if (StringUtils.isNotEmpty(end)) {
            sql.append(" and end_time<='" + end + " 23:59:59' ");
        }

        // List<Object> params = new ArrayList<Object>();
        // StringBuffer sql = new StringBuffer(" ");
        // sql.append(" SELECT T.DEP_CODE,T.DEP_NAME,SUM(T.BJ_PTJ)
        // BJ_PTJ,SUM(BJ_PTJTQ)");
        // sql.append(" as tiqian,SUM(BJ_PTJAS) anshi,SUM(BJ_PTJYQ) as chaoqi ");
        // sql.append(" from JBPM6_STATIST T ");
        // sql.append("JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEP_CODE = D.DEPART_CODE ");
        // sql.append("JOIN (SELECT S.DEPART_ID FROM T_CKBS_SERVICEWIN S GROUP BY
        // S.DEPART_ID) CS ");
        // sql.append("ON CS.DEPART_ID = D.DEPART_ID ");
        // sql.append(" where 1=1 ");

        sql.append(" GROUP BY T.DEPART_ID,T.DEPART_NAME ORDER BY BJ_PTJ ASC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        for (Map<String, Object> map : list) {
            double tiqian = Integer.parseInt(map.get("tiqian").toString());
            double chaoqi = Integer.parseInt(map.get("chaoqi").toString());
            double sum = Integer.parseInt(map.get("BJ_PTJ").toString());
            double tiqianl = 0;
            double chaoql = 0;
            String chaoqil = "0%";
            String tiql = "0%";
            // 获取普通件提前办结率
            if (sum != 0) {
                tiqianl = tiqian / sum;
                chaoql = chaoqi / sum;
                chaoqil = StringUtil.getPercentFormat(chaoql, 2);
                tiql = StringUtil.getPercentFormat(tiqianl, 2);
            }
            map.put("TIQL", tiql);
            map.put("CHAOQL", chaoqil);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findStageTotalByFilter(SqlFilter sqlFilter, String begin, String end) {
        // List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select ");
        sql.append(" substr(end_time,0,7) end_date,count(t.ITEM_NAME) as COUNTS,");
        sql.append(" sum(t.effi_desc_1) as EFFI_DESC_1,");
        sql.append(" sum(t.effi_desc_2) as EFFI_DESC_2,");
        sql.append(" sum(t.effi_desc_3) as EFFI_DESC_3,");
        sql.append(" sum(t.effi_desc_4) as EFFI_DESC_4");
        sql.append(" from VIEW_EFFI_ITEM t ");
        sql.append(" where 1=1 ");

        if (StringUtils.isNotEmpty(begin)) {
            sql.append(" and substr(end_time,0,7)>='" + begin + "'");
        }
        if (StringUtils.isNotEmpty(end)) {
            sql.append(" and substr(end_time,0,7)<='" + end + "' ");
        }
        sql.append(" GROUP BY substr(end_time,0,7) ");
        sql.append("  order by  end_date desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        for (Map<String, Object> map : list) {
            double couns = Integer.parseInt(map.get("COUNTS").toString());// 总数
            double EFFI_DESC_1 = Integer.parseInt(map.get("EFFI_DESC_1").toString());// 提前办结数
            double EFFI_DESC_3 = Integer.parseInt(map.get("EFFI_DESC_3").toString());// 超期办结数
            double tqbjl = 0;// 提前办结率
            double cqbjl = 0;// 超期办结率
            if (EFFI_DESC_1 != 0) {
                tqbjl = EFFI_DESC_1 / couns;
            }
            if (EFFI_DESC_3 != 0) {
                cqbjl = EFFI_DESC_3 / couns;
            }
            map.put("TQBJL", StringUtil.getPercentFormat(tqbjl, 2));
            map.put("CQBJL", StringUtil.getPercentFormat(cqbjl, 2));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getBjzttjStatist(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select (CASE t.parent_name  WHEN '平潭综合实验区' THEN  t.DEPART_NAME    "
                + "ELSE t.parent_name  END)as parent_name,0 as num_,t.DEPART_NAME,"
                + "sum(counts) as counts,sum(banjs) as banjs,sum(zbs) as zbs,");
        sql.append(" sum(bujs) as bujs,sum(tjs) as tjs,sum(total) as total,sum(sl) as sl,");
        sql.append(" sum(wcl) as wcl,sum(pass) as pass,sum(jhz) as jhz,sum(other) as other,sum(bs) as bs,");
        sql.append(" sum(zx) as zx,sum(lz) as lz,sum(ytl) as ytl,sum(qtxd) as qtxd");
        sql.append(" from VIEW_BJZTTJ_COUNT_ITEM t ");
        sql.append(" left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_code = t.depart_code ");
        sql.append(" left join T_MSJW_SYSTEM_DEPARTMENT p on p.depart_id = d.parent_id ");
        sql.append(" where 1=1");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND T.depart_code in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        // exeSql +=
        // " group by t.parent_name,t.DEPART_NAME order by parent_name ";

        exeSql += " GROUP BY t.parent_name,t.DEPART_NAME,p.TREE_SN,d.TREE_SN "
                + "ORDER BY (CASE t.parent_name WHEN '平潭综合实验区' THEN d.TREE_SN ELSE p.TREE_SN END),d.TREE_SN";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        // 办件统计
        int COUNTS = 0;// 办件总数
        int BANJS = 0;// 办结数
        int ZBS = 0;// 在办数
        int BUJS = 0;// 补件数
        int TJS = 0;// 退件数
        // 预登记统计
        int TOTAL = 0;// 总数
        int SL = 0;// 受理数
        int WCL = 0;// 未处理数
        int PASS = 0;// 过号数
        int JHZ = 0;// 叫号
        int OTHER = 0;// 其他收件数
        int BS = 0;// 办事数
        int ZX = 0;// 咨询数
        int LZ = 0;// 领照数
        int YTL = 0;// 一条龙审批数
        int QTXD = 0;// 其他导向数

        int i = 1;
        for (Map<String, Object> map : list) {
            map.put("NUM_", i);
            i++;
            // 办件统计
            COUNTS += Integer.parseInt(map.get("COUNTS").toString());
            BANJS += Integer.parseInt(map.get("BANJS").toString());
            ZBS += Integer.parseInt(map.get("ZBS").toString());
            BUJS += Integer.parseInt(map.get("BUJS").toString());
            TJS += Integer.parseInt(map.get("TJS").toString());
            // 预登记统计
            TOTAL += Integer.parseInt(map.get("TOTAL").toString());
            SL += Integer.parseInt(map.get("SL").toString());
            WCL += Integer.parseInt(map.get("WCL").toString());
            PASS += Integer.parseInt(map.get("PASS").toString());
            JHZ += Integer.parseInt(map.get("JHZ").toString());
            OTHER += Integer.parseInt(map.get("OTHER").toString());
            BS += Integer.parseInt(map.get("BS").toString());
            ZX += Integer.parseInt(map.get("ZX").toString());
            LZ += Integer.parseInt(map.get("LZ").toString());
            YTL += Integer.parseInt(map.get("YTL").toString());
            QTXD += Integer.parseInt(map.get("QTXD").toString());
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        double counts_double = COUNTS;
        double banjs_double = BANJS;
        map.put("PARENT_NAME", "");
        map.put("NUM_", "平均办结率(" + StringUtil.getPercentFormat(banjs_double / counts_double, 2) + ")");
        map.put("DEPART_NAME", "合计");

        map.put("COUNTS", COUNTS);
        map.put("BANJS", BANJS);
        map.put("ZBS", ZBS);
        map.put("BUJS", BUJS);
        map.put("TJS", TJS);

        map.put("TOTAL", TOTAL);
        map.put("SL", SL);
        map.put("WCL", WCL);
        map.put("PASS", PASS);
        map.put("JHZ", JHZ);
        map.put("OTHER", OTHER);
        map.put("BS", BS);
        map.put("ZX", ZX);
        map.put("LZ", LZ);
        map.put("YTL", YTL);
        map.put("QTXD", QTXD);
        list.add(map);
        return list;
    }

    /**
     * 
     * 描述 审批事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSpsxmxStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from VIEW_DEPART_ITEM T where 1=1 ");
        if (filter.getQueryParams().get("Q_T.DEPART_ID_=") != null
                && !filter.getQueryParams().get("Q_T.DEPART_ID_=").equals("")) {
            String departId = filter.getQueryParams().get("Q_T.DEPART_ID_=").toString();
            sql.append(" and (T.DEPART_ID = '").append(departId);
            sql.append("' or T.PARENT_ID = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_=");
        }
        if (filter.getQueryParams().get("Q_T.RUN_STATUS_=") != null
                && !filter.getQueryParams().get("Q_T.RUN_STATUS_=").equals("")) {
            String runStatus = filter.getQueryParams().get("Q_T.RUN_STATUS_=").toString();
            // 0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回)
            if ("0".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '0'");// 草稿
            } else if ("1".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '7'");// 预审不通过
            } else if ("2".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '1'");// 在办
            } else if ("3".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS in ('2','3')");// 办结
            } else if ("4".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '4'");// 审核不通过
            } else if ("5".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '5'");// 退件
            } else if ("6".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '6'");// 强制结束
            }
            filter.removeFilter("Q_T.RUN_STATUS_=");
        }
        // 收件是否超期
        if (filter.getQueryParams().get("Q_T.IS_REV_REMAINTIME_=") != null
                && !filter.getQueryParams().get("Q_T.IS_REV_REMAINTIME_=").equals("")) {
            String timeOut = filter.getQueryParams().get("Q_T.IS_REV_REMAINTIME_=").toString();
            if ("0".equals(timeOut)) {
                sql.append(" and T.REV_REMAINTIME != '00:00'");// 草稿
            } else if ("1".equals(timeOut)) {
                sql.append(" and T.REV_REMAINTIME = '00:00'");// 预审不通过
            }
            filter.removeFilter("Q_T.IS_REV_REMAINTIME_=");
        }
        if (filter.getQueryParams().get("Q_t.EVALUATE_=") != null
                && !filter.getQueryParams().get("Q_t.EVALUATE_=").equals("")) {
            String pjxx = dictionaryService.getDicNames("PJXX",
                    filter.getQueryParams().get("Q_t.EVALUATE_=").toString());
            sql.append(" and T.EVALUATE = '").append(pjxx).append("'");
            filter.removeFilter("Q_t.EVALUATE_=");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        return list;
    }

    /**
     * 
     * 描述 审批事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSpsxmxStatistForExp(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.exe_id,t.depart_name,t.subject,t.item_name,t.evaluate,");
        sql.append("t.dic_name,t.bjlx,t.sqr,t.jbr_mobile,t.create_time,");
        sql.append("t.end_time,t.creator_name,t.status,t.cnqxgzr,t.jbr_name,t.xkfile_num,");
        sql.append("t.xkfile_name,t.xkdept_name,t.effect_time,t.close_time,");
        sql.append("t.xkcontent,t.run_mode,t.file_name,t.cur_node");
        sql.append(",t.rev_starttime,t.rev_endtime,t.rev_remaintime");
        sql.append("   from VIEW_DEPART_ITEM T where 1=1 ");
        if (filter.getQueryParams().get("Q_T.DEPART_ID_EQ") != null
                && !filter.getQueryParams().get("Q_T.DEPART_ID_EQ").equals("")) {
            String departId = filter.getQueryParams().get("Q_T.DEPART_ID_EQ").toString();
            sql.append(" and (T.DEPART_ID = '").append(departId);
            sql.append("' or T.PARENT_ID = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_EQ");
        }
        if (filter.getQueryParams().get("Q_T.RUN_STATUS_EQ") != null
                && !filter.getQueryParams().get("Q_T.RUN_STATUS_EQ").equals("")) {
            String runStatus = filter.getQueryParams().get("Q_T.RUN_STATUS_EQ").toString();
            // 0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回)
            if ("0".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '0'");// 草稿
            } else if ("1".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '7'");// 预审不通过
            } else if ("2".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '1'");// 在办
            } else if ("3".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS in ('2','3')");// 办结
            } else if ("4".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '4'");// 审核不通过
            } else if ("5".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '5'");// 退件
            } else if ("6".equals(runStatus)) {
                sql.append(" and T.RUN_STATUS = '6'");// 强制结束
            }
            filter.removeFilter("Q_T.RUN_STATUS_EQ");
        }
        // 收件是否超期
        if (filter.getQueryParams().get("Q_T.IS_REV_REMAINTIME_EQ") != null
                && !filter.getQueryParams().get("Q_T.IS_REV_REMAINTIME_EQ").equals("")) {
            String timeOut = filter.getQueryParams().get("Q_T.IS_REV_REMAINTIME_EQ").toString();
            if ("0".equals(timeOut)) {
                sql.append(" and T.REV_REMAINTIME != '00:00'");// 草稿
            } else if ("1".equals(timeOut)) {
                sql.append(" and T.REV_REMAINTIME = '00:00'");// 预审不通过
            }
            filter.removeFilter("Q_T.IS_REV_REMAINTIME_EQ");
        }
        if (filter.getQueryParams().get("Q_t.EVALUATE_EQ") != null
                && !filter.getQueryParams().get("Q_t.EVALUATE_EQ").equals("")) {
            String pjxx = dictionaryService.getDicNames("PJXX",
                    filter.getQueryParams().get("Q_t.EVALUATE_EQ").toString());
            sql.append(" and T.EVALUATE = '").append(pjxx).append("'");
            filter.removeFilter("Q_t.EVALUATE_EQ");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述：导出所有事项
     * 
     * @author Water Guo
     * @created 2017-3-8 下午5:09:57
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getAllItemForExp(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.depart_name,t.child_depart_name,");
//        sql.append("T.CATALOG_NAME,T.ITEM_NAME,T.DIC_NAME,T.ITEM_CODE,T.BJLX,T.SWB,T.SWB_ITEM_CODE,");
        sql.append("T.ITEM_NAME,T.DIC_NAME,T.ITEM_CODE,T.BJLX,T.SWB,T.SWB_ITEM_CODE,");
        sql.append("T.ZTNAME,T.UPDATE_TIME,T.WANGSHEN,T.WAILIAN,T.APPLY_URL");
        sql.append("   from VIEW_ALL_SERVICEITEM T where 1=1 ");
        // 以省网办编码为主
        if (filter.getQueryParams().get("Q_T.ITEM_CODE_LIKE") != null
                && !"".equals(filter.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))) {
            String item_code = filter.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            filter.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述 处理事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getClsxmxStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from VIEW_DEPART_ITEM_DEAL T where 1=1 ");
        if (filter.getQueryParams().get("Q_T.DEPART_ID_=") != null
                && !filter.getQueryParams().get("Q_T.DEPART_ID_=").equals("")) {
            String departId = filter.getQueryParams().get("Q_T.DEPART_ID_=").toString();
            sql.append(" and (T.DEPART_ID = '").append(departId);
            sql.append("' or T.PARENT_ID = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_=");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 处理事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getYqbjmxStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.depart_name,e.exe_id, ");
        sql.append("e.subject,s.item_name,sxlx.dic_name sxlx, ");
        sql.append("s.cnqxgzr,e.create_time,e.end_time,e.creator_name,e.creator_phone from JBPM6_EFFICINFO t ");
        sql.append("left join jbpm6_execution e on e.exe_id = t.eflow_exeid ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
//        sql.append("left join t_wsbs_serviceitem_catalog c on c.catalog_code = s.catalog_code ");
//        sql.append("left join t_msjw_system_department d on d.depart_id = c.child_depart_id ");
        sql.append("left join t_msjw_system_department d on d.depart_id = s.zbcs_id ");
        sql.append(" join (select sd.dic_code,sd.dic_name from t_msjw_system_dictionary sd where sd.type_id=");
        sql.append("(select type_id from t_msjw_system_dictype where type_code='ServiceItemType'))sxlx ");
        sql.append("on sxlx.dic_code = s.sxlx ");
        sql.append("where t.effi_desc=3 ");
        // 限制统计四个事项
        sql.append(" and s.ITEM_CODE not IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode' ");
        sql.append(" or DIC.TYPE_CODE='projectItemCode' )");
        // 不查询工程建设事项
        sql.append(" and s.sfgcjsxm !='1' ");
        // 过滤“历史遗留问题”和“民宿”
        sql.append(" and e.item_code not in ('569262478QR00322','569262478QR00323','345071904XK01005','345071904XK01004') ");
        if (filter.getQueryParams().get("Q_T.DEPART_ID_=") != null
                && !filter.getQueryParams().get("Q_T.DEPART_ID_=").equals("")) {
            String departId = filter.getQueryParams().get("Q_T.DEPART_ID_=").toString();
//            sql.append(" and (c.child_depart_id = '").append(departId);
//            sql.append("' or c.depart_id = '").append(departId).append("') ");
            sql.append(" and (s.zbcs_id = '").append(departId);
            sql.append("' or d.parent_id = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_=");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String,Object>> allList = dao.findBySql(exeSql,params.toArray(),null);
        List<Map<String,Object>> instantWorkList = getInstantWork(allList);//根据逾期办结事项明细记录获取办件筛选为即办件的记录
        List<String> noVerDueExeIdList = calcOverDueDayWorkDay(allList,instantWorkList);//未超期即办件事项ID
        deleteNoOverDue(noVerDueExeIdList);//删除未超期即办件记录
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        list = encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
        instantWorkList = getInstantWork(list);//根据逾期办结事项明细记录获取办件筛选为即办件的记录
        for (Map<String, Object> map : list) {
            //String createTime = (String) map.get("CREATE_TIME"); //获取任务开始时间
            String createTime = getCreateTime(map,instantWorkList); //获取任务开始时间
            if (StringUtils.isNotEmpty(createTime)) {
                String cnqxgzr = map.get("CNQXGZR") == null ? "" : map.get("CNQXGZR").toString();
                int cnqxgzrint = 0;
                if (StringUtils.isNotEmpty(cnqxgzr)) {
                    cnqxgzrint = Integer.parseInt(cnqxgzr);
                }
                Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
                String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
                if (cnqxgzrint > 0) {
                    deadLineDate = workdayService.getDeadLineDate(deadLineDate, cnqxgzrint);
                    deadLineDate += " 00:00:00";
                }
                // 当即办件，创建时间为5：30分后，往后顺延一天
                String sxlx = StringUtil.getString(map.get("SXLX"));
                deadLineDate = getJbjDeadLineDate(createTimeDate, deadLineDate, sxlx);

                // 获取办结时间
                String endTime = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                if (StringUtils.isEmpty(endTime)) {
                    endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                }
                Date endTimedDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
                // 获取流程ID
                String taskId = (String) map.get("TASK_ID");
                if (StringUtils.isNotEmpty(deadLineDate)) {
                    int overdueWorkDay = flowTaskService.getOverdueWorkDay(deadLineDate, taskId, endTimedDate);
                    map.put("OVERDUE_WORKDAY", overdueWorkDay);
                } else {
                    map.put("OVERDUE_WORKDAY", -2);
                }
            }
        }
        return list;
    }

    /**
     * 根据逾期办结事项明细记录获取办件筛选为即办件的记录
     * @param list
     * @return
     */
    public List<Map<String,Object>> getInstantWork(List<Map<String,Object>> list){
        List<Map<String,Object>> instantWorkList = new ArrayList<>();
        if(list!=null&&list.size()>0){
            List<String> exeIdList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if(StringUtils.isNotEmpty((String)map.get("SXLX"))&&StringUtils.isNotEmpty((String)map.get("EXE_ID"))
                        &&map.get("SXLX").toString().equals("即办件")){
                    exeIdList.add(map.get("EXE_ID").toString());
                }
            }

            if(exeIdList!=null&&exeIdList.size()>0){
                String exeIds = "'" + StringUtils.join(exeIdList,"','") + "'";
                StringJoiner stringJoiner = new StringJoiner(" ");
                stringJoiner.add("SELECT A.EXE_ID,MAX(A.END_TIME) AS CREATE_TIME")
                        .add("FROM JBPM6_TASK A WHERE A.EXE_ID IN (" + exeIds + ")")
                        .add("AND A.TASK_NODENAME = ? GROUP BY A.EXE_ID");
                instantWorkList = dao.findBySql(stringJoiner.toString(), new Object[]{"开始"}, null);
            }
        }

        return instantWorkList;
    }

    /**
     * 获取即办件超期时间的记录
     * @param allList
     * @param instantWorkList
     * @return
     */
    public List<String> calcOverDueDayWorkDay(List<Map<String,Object>> allList
            ,List<Map<String,Object>> instantWorkList){
        List<String> list = new ArrayList<>();
        for (Map<String, Object> map : allList) {
            if(StringUtils.isEmpty((String)map.get("EXE_ID"))||StringUtils.isEmpty((String)map.get("SXLX"))
                    ||!map.get("SXLX").toString().equals("即办件")){
                continue;
            }

            String createTime = getCreateTime(map,instantWorkList); //获取任务开始时间
            if (StringUtils.isNotEmpty(createTime)) {
                String cnqxgzr = map.get("CNQXGZR") == null ? "" : map.get("CNQXGZR").toString();
                int cnqxgzrint = 0;
                if (StringUtils.isNotEmpty(cnqxgzr)) {
                    cnqxgzrint = Integer.parseInt(cnqxgzr);
                }
                Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
                String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
                if (cnqxgzrint > 0) {
                    deadLineDate = workdayService.getDeadLineDate(deadLineDate, cnqxgzrint);
                    deadLineDate += " 00:00:00";
                }
                // 当即办件，创建时间为5：30分后，往后顺延一天
                String sxlx = StringUtil.getString(map.get("SXLX"));
                deadLineDate = getJbjDeadLineDate(createTimeDate, deadLineDate, sxlx);

                // 获取办结时间
                String endTime = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                if (StringUtils.isEmpty(endTime)) {
                    endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                }
                Date endTimedDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
                // 获取流程ID
                String taskId = (String) map.get("TASK_ID");
                int overdueWorkDay = -2;
                if (StringUtils.isNotEmpty(deadLineDate)) {
                    overdueWorkDay = flowTaskService.getOverdueWorkDay(deadLineDate, taskId, endTimedDate);
                }
                map.put("OVERDUE_WORKDAY", overdueWorkDay);

                if(overdueWorkDay==0||overdueWorkDay==-1||overdueWorkDay==-2){//未超期
                    list.add(map.get("EXE_ID").toString());
                }
            }
        }

        return list;
    }

    /**
     * 删除未超期的即办件记录
     * @param list
     */
    public void deleteNoOverDue(List<String> list){
        if(list!=null&&list.size()>0){
            List<Object> params = new ArrayList<>();
            StringJoiner stringJoiner = new StringJoiner(" ");
            stringJoiner.add("DELETE FROM JBPM6_EFFICINFO WHERE EFFI_DESC = ?");
            params.add(3);
            List<String> sqlWhere = new ArrayList<>();
            for(String str : list){
                sqlWhere.add("EFLOW_EXEID = ?");
                params.add(str);
            }
            stringJoiner.add("and (" + StringUtils.join(sqlWhere," OR ") + ")");
            dao.executeSql(stringJoiner.toString(), params.toArray());
        }
    }

    /**
     * 获取即办件的任务开始时间
     * @param map
     * @param instantWorkList
     * @return
     */
    public String getCreateTime(Map<String,Object> map,List<Map<String,Object>> instantWorkList){
        String createTime = (String) map.get("CREATE_TIME");
        String exeId = (String)map.get("EXE_ID");
        if(StringUtils.isNotEmpty(exeId)&&StringUtils.isNotEmpty((String)map.get("SXLX"))
                &&map.get("SXLX").toString().equals("即办件")&&instantWorkList!=null&&instantWorkList.size()>0){
            for(Map<String,Object> instantWork : instantWorkList){
                if(StringUtils.isNotEmpty((String)instantWork.get("EXE_ID"))
                        &&instantWork.get("EXE_ID").toString().equals(exeId)
                        &&StringUtils.isNotEmpty((String)instantWork.get("CREATE_TIME"))){
                    createTime = instantWork.get("CREATE_TIME").toString();
                    break;
                }
            }
        }

        return createTime;
    }

    /**
     * 即办件晚于下班时间收件或在周末收件截至时间为最近一个工作日
     * 
     * @param createTimeDate yyyy-MM-dd HH:mm
     * @param deadLineDate   yyyy-MM-dd
     * @param sxlx           即办件
     * @return
     */
    public String getJbjDeadLineDate(Date createTimeDate, String deadLineDate, String sxlx) {
        String goOffTime = dictionaryService.getDicNames("commutingtime", "goOffTime");
        String createTimeDated = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd");
        goOffTime = String.format("%s %s", createTimeDated, goOffTime);
        Date goOffDate = DateTimeUtil.getDateOfStr(goOffTime, "yyyy-MM-dd HH:mm");
        if ((createTimeDate.after(goOffDate) || workdayService.isHoliDay(createTimeDated)) && "即办件".equals(sxlx)) {
            deadLineDate = workdayService.getDeadLineDate(deadLineDate, 1);
            deadLineDate += " 23:59:59";
        }
        return deadLineDate;
    }

    /**
     * 
     * 描述 处理事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getYqbjmxStatistForExp(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.depart_name,e.exe_id,e.subject,s.item_name,sxlx.dic_name sxlx,");
        sql.append("e.create_time,e.end_time,e.creator_name,s.cnqxgzr, '' as OVERDUE_WORKDAY from JBPM6_EFFICINFO t ");
        sql.append("left join jbpm6_execution e on e.exe_id = t.eflow_exeid ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append("left join t_wsbs_serviceitem_catalog c on c.catalog_code = s.catalog_code ");
        sql.append("left join t_msjw_system_department d on d.depart_id = c.child_depart_id ");
        sql.append("left join (select sd.dic_code,sd.dic_name from t_msjw_system_dictionary sd where sd.type_id=");
        sql.append("(select type_id from t_msjw_system_dictype where type_code='ServiceItemType'))sxlx ");
        sql.append("on sxlx.dic_code = s.sxlx ");
        sql.append("where t.effi_desc=3 ");
        // 限制统计四个事项
        sql.append(" and s.ITEM_CODE not IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode' ");
        sql.append(" or DIC.TYPE_CODE='projectItemCode' ) ");
        // 不查询工程建设事项
        sql.append(" and s.sfgcjsxm !='1' ");
        // 过滤“历史遗留问题”和“民宿”
        sql.append(" and e.item_code not in ('569262478QR00322','569262478QR00323','345071904XK01005','345071904XK01004') ");
        if (filter.getQueryParams().get("Q_T.DEPART_ID_EQ") != null
                && !filter.getQueryParams().get("Q_T.DEPART_ID_EQ").equals("")) {
            String departId = filter.getQueryParams().get("Q_T.DEPART_ID_EQ").toString();
            sql.append(" and (c.child_depart_id = '").append(departId);
            sql.append("' or c.depart_id = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_EQ");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        List<Map<String,Object>> instantWorkList = getInstantWork(list);//根据逾期办结事项明细记录获取办件筛选为即办件的记录
        for (Map<String, Object> map : list) {
            //String createTime = (String) map.get("CREATE_TIME"); //获取任务开始时间
            String createTime = getCreateTime(map,instantWorkList); //获取任务开始时间
            if (StringUtils.isNotEmpty(createTime)) {
                String cnqxgzr = map.get("CNQXGZR") == null ? "" : map.get("CNQXGZR").toString();
                int cnqxgzrint = 0;
                if (StringUtils.isNotEmpty(cnqxgzr)) {
                    cnqxgzrint = Integer.parseInt(cnqxgzr);
                }
                Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
                String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
                if (cnqxgzrint > 0) {
                    deadLineDate = workdayService.getDeadLineDate(deadLineDate, cnqxgzrint);
                    deadLineDate += " 00:00:00";
                }
                // 当即办件，创建时间为5：30分后，往后顺延一天
                String sxlx = StringUtil.getString(map.get("SXLX"));
                deadLineDate = getJbjDeadLineDate(createTimeDate, deadLineDate, sxlx);
                // 获取办结时间
                String endTime = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                if (StringUtils.isEmpty(endTime)) {
                    endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                }
                Date endTimedDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
                // 获取流程ID
                String taskId = (String) map.get("TASK_ID");
                if (StringUtils.isNotEmpty(deadLineDate)) {
                    int overdueWorkDay = flowTaskService.getOverdueWorkDay(deadLineDate, taskId, endTimedDate);
                    if (overdueWorkDay > 0) {
                        map.put("OVERDUE_WORKDAY", "超出" + overdueWorkDay + "个工作日");
                    }
                }
            }
        }

        return list;
    }

    @Override
    public Map<String, Object> getLjbjsj() {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT (SUM(BJS) + SUM(WBJS)) AS COUNTS, SUM(BJS) BJS, SUM(WBJS) WBJS");
        sql.append(" FROM (select COUNT(*) AS BJS, 0 AS WBJS");
        sql.append(" from JBPM6_EXECUTION t");
        sql.append("  where t.run_status in (2, 3)");
        sql.append(" UNION ALL");
        sql.append("  select 0 AS BJS, COUNT(*) AS WBJS");
        sql.append("  from JBPM6_EXECUTION t");
        sql.append(" where t.run_status NOT in (2, 3)) M");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), new Object[] {});
        double COUNTS = Integer.parseInt(map.get("COUNTS").toString());
        double BJS = Integer.parseInt(map.get("BJS").toString());

        double bjl = 0;// 办结率
        if (COUNTS != 0) {
            bjl = BJS / COUNTS;
        }
        map.put("BJL", StringUtil.getPercentFormat(bjl, 2));
        return map;
    }

    @Override
    public Map<String, Object> getYearLjbjsj() {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT (SUM(BJS)+SUM(WBJS)) AS COUNTS,SUM(BJS) BJS,SUM(WBJS) WBJS "
                + "FROM (select COUNT(*) AS BJS,0 AS WBJS");
        sql.append("  from JBPM6_EXECUTION t");
        sql.append("  where  t.run_status  in(2,3)  and t.create_time between to_char(sysdate, 'yyyy') || '-01-01' ");
        sql.append(" and to_char(sysdate + 1, 'yyyy-MM-dd')");
        sql.append(" UNION ALL");
        sql.append(" select 0 AS BJS,COUNT(*) AS WBJS");
        sql.append(" from JBPM6_EXECUTION t");
        sql.append("  where  t.run_status NOT in(2,3)  and t.create_time between to_char(sysdate, 'yyyy') ");
        sql.append(" || '-01-01' and to_char(sysdate + 1, 'yyyy-MM-dd')) M");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), new Object[] {});
        double COUNTS = Integer.parseInt(map.get("COUNTS").toString());
        double BJS = Integer.parseInt(map.get("BJS").toString());

        double bjl = 0;// 办结率
        if (COUNTS != 0) {
            bjl = BJS / COUNTS;
        }
        map.put("BJL", StringUtil.getPercentFormat(bjl, 2));
        return map;
    }

    @Override
    public Map<String, Object> getMonthLjbjsj() {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT (SUM(BJS)+SUM(WBJS)) AS COUNTS,SUM(BJS) BJS,SUM(WBJS) WBJS "
                + "FROM (select COUNT(*) AS BJS,0 AS WBJS");
        sql.append("  from JBPM6_EXECUTION t");
        sql.append("  where  t.run_status  in(2,3)  and t.create_time between to_char(sysdate, 'yyyy-mm') || '-01' ");
        sql.append(" and to_char(sysdate + 1, 'yyyy-MM-dd')");
        sql.append(" UNION ALL");
        sql.append(" select 0 AS BJS,COUNT(*) AS WBJS");
        sql.append(" from JBPM6_EXECUTION t");
        sql.append("  where  t.run_status NOT in(2,3)  and t.create_time between to_char(sysdate, 'yyyy-mm')");
        sql.append(" || '-01' and to_char(sysdate + 1, 'yyyy-MM-dd')) M");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), new Object[] {});
        double COUNTS = Integer.parseInt(map.get("COUNTS").toString());
        double BJS = Integer.parseInt(map.get("BJS").toString());

        double bjl = 0;// 办结率
        if (COUNTS != 0) {
            bjl = BJS / COUNTS;
        }
        map.put("BJL", StringUtil.getPercentFormat(bjl, 2));
        return map;
    }

    @Override
    public Map<String, Object> getDayLjbjsj() {
        StringBuffer sql = new StringBuffer("SELECT (SUM(BJS)+SUM(WBJS)) AS COUNTS,SUM(BJS) BJS,SUM(WBJS) WBJS "
                + "FROM (select COUNT(*) AS BJS,0 AS WBJS");
        sql.append("  from JBPM6_EXECUTION t");
        sql.append("  where  t.run_status  in(2,3)  and t.create_time between to_char(sysdate, 'yyyy-MM-dd') and");
        sql.append(" to_char(sysdate + 1, 'yyyy-MM-dd')");
        sql.append(" UNION ALL");
        sql.append(" select 0 AS BJS,COUNT(*) AS WBJS");
        sql.append(" from JBPM6_EXECUTION t");
        sql.append("  where  t.run_status NOT in(2,3)  and t.create_time between to_char(sysdate, 'yyyy-MM-dd') and");
        sql.append(" to_char(sysdate + 1, 'yyyy-MM-dd')) M");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), new Object[] {});
        double COUNTS = Integer.parseInt(map.get("COUNTS").toString());
        double BJS = Integer.parseInt(map.get("BJS").toString());

        double bjl = 0;// 办结率
        if (COUNTS != 0) {
            bjl = BJS / COUNTS;
        }
        map.put("BJL", StringUtil.getPercentFormat(bjl, 2));
        return map;
    }

    /**
     * 
     * 描述 投资事项逾期办结事项明细
     * 
     * @author Danto Huang
     * @created 2016-4-13 下午2:53:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getTzxmyqbjmxStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.*,e.creator_phone,s.cnqxgzr,s.item_name,sxlx.dic_name sxlx from T_BSFW_TZXMYQMX t ");
        sql.append(" left join jbpm6_execution e on e.exe_id = t.exe_id ");
        sql.append(" left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append("left join (select sd.dic_code,sd.dic_name from t_msjw_system_dictionary sd where sd.type_id=");
        sql.append("(select type_id from t_msjw_system_dictype where type_code='ServiceItemType'))sxlx ");
        sql.append("on sxlx.dic_code = s.sxlx ");
        sql.append(" where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());

        for (Map<String, Object> map : list) {
            // 获取任务截止时间
            String createTime = (String) map.get("CREATE_TIME");
            String cnqxgzr = map.get("CNQXGZR") == null ? "" : map.get("CNQXGZR").toString();
            int cnqxgzrint = Integer.parseInt(cnqxgzr);
            Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
            String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
            if (cnqxgzrint > 0) {
                deadLineDate = workdayService.getDeadLineDate(deadLineDate, cnqxgzrint);
                deadLineDate += " 00:00:00";
            }
            // 获取办结时间
            String endTime = (String) map.get("END_TIME");
            Date endTimedDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
            // 获取流程ID
            String taskId = (String) map.get("TASK_ID");
            if (StringUtils.isNotEmpty(deadLineDate)) {
                int overdueWorkDay = flowTaskService.getOverdueWorkDay(deadLineDate, taskId, endTimedDate);
                map.put("OVERDUE_WORKDAY", overdueWorkDay);
            } else {
                map.put("OVERDUE_WORKDAY", -2);
            }
        }

        return list;
    }

    /**
     * 
     * 描述
     * 
     * @author Danto Huang
     * @created 2016-10-19 下午4:21:10
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getTzxmyqbjmxStatistForExp(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.exe_id,t.subject,t.task_nodename,t.create_time,t.end_time,"
                + "t.dead_time,t.assigner_name from T_BSFW_TZXMYQMX t where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findByCyjbItemSqlFilter(SqlFilter sqlFilter) {
        List<Object> paramsc = new ArrayList<Object>();
        StringBuffer sqlc = new StringBuffer();
        sqlc.append("select * from VIEW_ALL_CYJBITEM t where 1 = 1 ");
        String exeSqlc = dao.getQuerySql(sqlFilter, sqlc.toString(), paramsc);
        exeSqlc += "order by t.create_time desc,t.exe_id desc ";
        List<Map<String, Object>> listc = dao.findBySql(exeSqlc, paramsc.toArray(), sqlFilter.getPagingBean());
        return listc;
    }

    @Override
    public List<Map<String, Object>> findByCyjbExcelSqlFilter(SqlFilter sqlFilter) {
        List<Object> paramsc = new ArrayList<Object>();
        StringBuffer sqlc = new StringBuffer();
        sqlc.append("select * from VIEW_ALL_CYJBITEM_EXPORT t where 1 = 1 ");
        String exeSqlc = dao.getQuerySql(sqlFilter, sqlc.toString(), paramsc);
        exeSqlc += "order by t.create_time desc ";
        List<Map<String, Object>> listc = dao.findBySql(exeSqlc, paramsc.toArray(), null);
        return listc;
    }

    /**
     * 
     * 描述
     * 
     * @author Danto Huang
     * @created 2016-10-31 上午11:08:37
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSljPjStatist(SqlFilter filter) {
        List<Object> totalparams = new ArrayList<Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer totalsql = new StringBuffer();
        totalsql.append("select '' as parent_name,'总计' as operator,");
        totalsql.append("sum(t.counts_2) + sum(t.counts_1) + sum(t.counts_3) as COUNTSTOTAL,");
        totalsql.append("sum(t.counts_2) counts_2,sum(t.counts_1) counts_1,sum(t.counts_3) counts_3,");
        totalsql.append("sum(t.sums_2) sums_2,sum(t.sums_1) sums_1,sum(t.sums_3) sums_3,");
        totalsql.append("sum(t.evaluate_3_2)evaluate_3_2,sum(t.evaluate_3_1)evaluate_3_1,");
        totalsql.append("sum(t.evaluate_3_3)evaluate_3_3,");
        totalsql.append("sum(t.evaluate_2_2)evaluate_2_2,sum(t.evaluate_2_1)evaluate_2_1,");
        totalsql.append("sum(t.evaluate_2_3)evaluate_2_3,");
        totalsql.append("sum(t.evaluate_1_2)evaluate_1_2,sum(t.evaluate_1_1)evaluate_1_1,");
        totalsql.append("sum(t.evaluate_1_3)evaluate_1_3,");
        totalsql.append("sum(t.evaluate_0_2)evaluate_0_2,sum(t.evaluate_0_1)evaluate_0_1,");
        totalsql.append("sum(t.evaluate_0_3)evaluate_0_3 ");
        totalsql.append("from VIEW_USER_ITEM_SXLXPJ t where 1=1 ");
        String exeTotalSql = dao.getQuerySql(filter, totalsql.toString(), totalparams);
        List<Map<String, Object>> totalList = dao.findBySql(exeTotalSql, totalparams.toArray(), null);
        StringBuffer sql = new StringBuffer();
        sql.append("select t.parent_name,t.operator,");
        sql.append("sum(t.counts_2) + sum(t.counts_1) + sum(t.counts_3) as COUNTSTOTAL,");
        sql.append("sum(t.counts_2) counts_2,sum(t.counts_1) counts_1,sum(t.counts_3) counts_3,");
        sql.append("sum(t.sums_2) sums_2,sum(t.sums_1) sums_1,sum(t.sums_3) sums_3,");
        sql.append("sum(t.evaluate_3_2)evaluate_3_2,sum(t.evaluate_3_1)evaluate_3_1,sum(t.evaluate_3_3)evaluate_3_3,");
        sql.append("sum(t.evaluate_2_2)evaluate_2_2,sum(t.evaluate_2_1)evaluate_2_1,sum(t.evaluate_2_3)evaluate_2_3,");
        sql.append("sum(t.evaluate_1_2)evaluate_1_2,sum(t.evaluate_1_1)evaluate_1_1,sum(t.evaluate_1_3)evaluate_1_3,");
        sql.append("sum(t.evaluate_0_2)evaluate_0_2,sum(t.evaluate_0_1)evaluate_0_1,sum(t.evaluate_0_3)evaluate_0_3 ");
        sql.append("from VIEW_USER_ITEM_SXLXPJ t ");
        sql.append("left join t_msjw_system_department p on p.depart_name = t.parent_name and p.tree_level=3 ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += "group by t.parent_name,t.depart_name,t.operator,t.operator_id ";
//        exeSql += "order by (counts_2+counts_1+counts_3) desc";
        exeSql += "order by t.parent_name,(counts_2+counts_1+counts_3) desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        if (list != null && list.size() > 0) {
            list.add(0, totalList.get(0));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySwbItemSqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select row_number()over");
        sql.append(" (order by t.tree_sn asc,t.catalog_name desc,t.item_name desc  ) rowno,t.* ");
        sql.append(" from VIEW_SWB_ITEM t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "order by t.tree_sn asc,t.catalog_name desc,t.item_name desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySwbMaterSqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select row_number()over ");
        sql.append(" ( order by t.tree_sn asc,t.catalog_name desc,t.item_name desc ) rowno,t.* ");
        sql.append(" from VIEW_SWB_MATER t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " order by t.tree_sn asc,t.catalog_name desc,t.item_name desc  ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySwbLogSqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select row_number() over( ORDER BY t.update_time asc) AS rowno ,t.* from VIEW_SWB_LOG t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " order by t.update_time asc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getSqlStatist(SqlFilter filter) {
        // TODO Auto-generated method stub
        Map<String, Object> dataAbutment = dao.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { "SQLTJ" });
        String sql = (String) dataAbutment.get("CONFIG_XML");
        String column = dataAbutment.get("WEBSERVICE_URL") == null ? "" : dataAbutment.get("WEBSERVICE_URL").toString();
        String[] columns = column.split(",");
        List<Object> params = new ArrayList<Object>();
        List<Map<String, Object>> list = dao.findBySql(sql, params.toArray(), null);
        Sm4Utils sm4 = new Sm4Utils();
        for (Map<String, Object> map : list) {
            for (Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() == null ? "" : entry.getValue().toString();
                if (StringUtils.isNotEmpty(value)) {
                    for (String columnName : columns) {
                        if (columnName.toUpperCase().equals(key.toUpperCase())) {
                            String val = (String) map.get(key);
                            String newVal = sm4.decryptDataCBC(val);
                            if (StringUtils.isNotEmpty(newVal)) {
                                map.put(key, newVal);
                            } else {
                                map.put(key, val);
                            }
                        }
                    }
                }
            }

        }
        return list;
    }

}
