/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statistic.service.impl;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.statistic.dao.StatisticsNewDao;
import net.evecom.platform.statistic.job.JxkhDateStatistics;
import net.evecom.platform.statistic.service.StatisticsNewService;
import net.evecom.platform.system.model.SysUser;

import net.evecom.platform.system.service.WorkdayService;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 新报表操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("statisticsNewService")
public class StatisticsNewServiceImpl extends BaseServiceImpl implements StatisticsNewService {
    /**
     * 所引入的dao
     */
    @Resource
    private StatisticsNewDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/7 15:35:00
     * @param
     * @return
     */
    @Resource
    private WorkdayService workdayService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/18 15:53:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 描述:日志
     *
     * @author Madison You
     * @created 2019/10/16 18:08:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(StatisticsNewServiceImpl.class);

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Bryce Zhang
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
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
        totalsql.append("from VIEW_NEW_USER_ITEM_SXLXPJ t where 1=1 ");
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
        sql.append("from VIEW_NEW_USER_ITEM_SXLXPJ t ");
        sql.append("left join t_msjw_system_department p on p.depart_name = t.parent_name and p.tree_level=3 ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += "group by t.parent_name,t.depart_name,t.operator,t.operator_id ";
        // exeSql += "order by (counts_2+counts_1+counts_3) desc";
        exeSql += "order by t.parent_name,(counts_2+counts_1+counts_3) desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        if (list != null && list.size() > 0) {
            list.add(0, totalList.get(0));
        }
        return list;
    }
    
   
    /**
     * 
     * 描述 获取窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-15 上午09:54:17
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getCkbjltjStatist(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select T.CUR_WIN,sum(CASE T.CALL_STATUS WHEN '-1' THEN  0  ELSE 1 END) as total,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '1' THEN 1 ELSE 0 END) as sl,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '0' THEN 1 ELSE 0 END) as wcl,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '2' THEN 1 ELSE 0 END) as pass,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '6' THEN 1 ELSE 0 END) as jhz, 0 as other,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '5' THEN 1 ELSE 0 END) as bs,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '4' THEN 1 ELSE 0 END) as zx,");
        sql.append(" sum(CASE T.CALL_STATUS WHEN '3' THEN 1 ELSE 0 END) as lz");
        sql.append(" ,0 as YTL,0 as QTXD from VIEW_NEW_USER_ITEM t ");
        sql.append(" where 1=1 AND T.CUR_WIN IS NOT NULL");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  group by T.CUR_WIN order by T.CUR_WIN ";
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


    /**
     * 描述：一窗通用办办件明细表获取数据
     *
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    @Override
    public List<Map<String, Object>> findbjmxbDataBySqlFilter(SqlFilter filter) {
        if (filter.getRequest().getParameter("Q_t.create_time_>=") != null) {
            filter.removeFilter("Q_t.CREATE_TIME_GE");
        }
        ArrayList<String> params = new ArrayList<>();
        StringBuffer sqlP = new StringBuffer();
        StringBuffer sql = new StringBuffer("select distinct t.exe_id SBH,D.depart_name||'(' ||DC.depart_name||')' ");
        sql.append(" BM,s.item_name XMMC,t.subject BJMC,decode(s.is_yctb , '1' , '是' , 0 , '否') YCTB, ");
        sql.append(" dic2.dic_name BJLX,nvl(t.rev_starttime,'2019-01-01 10:10:10') SJKS");
        sql.append(" ,nvl(t.rev_endtime,'2019-01-01 10:10:10') SJJS , s.FZXSSZ , s.SLSHXS  ");
        sql.append(", t.CREATE_TIME BJKS, t.end_time BJJS ,decode(q.EVALUATE , '0' , ");
        sql.append(" '不满意' , '1' , '一般' , '2' , '满意' , '3' , '非常满意' , 4 , '不评价' , null , '未填写') BJPY , ");
        sql.append(" nvl(decode(x.effi_desc, 1, '提前完成', 2 , '按期完成' , 3 , '逾期' , 4 , '即办' ) , '未记录') WCQK  ");
        sql.append(" from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM s on s.item_code = t.item_code");
        sql.append(" left join JBPM6_EFFICINFO x on x.eflow_exeid = t.exe_id ");
        sql.append(" left join T_CKBS_QUEUERECORD q on q.exe_id= t.exe_id  ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = s.IMPL_DEPART_ID ");
//        sql.append(" LEFT JOIN (select CATALOG_CODE, D1.DEPART_ID,d1.depart_name from t_wsbs_serviceitem_catalog C");
//        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID = C.CHILD_DEPART_ID) DC ");
//        sql.append(" ON DC.CATALOG_CODE = s.CATALOG_CODE ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DC ON DC.DEPART_ID = s.zbcs_id");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DICTIONARY dic2 ON dic2.DIC_CODE = s.SXLX where 1=1 and t.run_status = 2 ");
        sql.append(" AND dic2.TYPE_CODE = 'ServiceItemType' and t.END_TIME is not null ");
        String sqlFilter = dao.getQuerySql(filter, sql.toString(), params);
        StringBuffer sqlFilterStr = new StringBuffer(sqlFilter);
        sqlFilterStr.append(" order by t.create_time desc");
        List<Map<String, Object>> list = null;
        if (filter.getRequest().getParameter("isPage")!= null) {
            list = dao.findBySql(sqlFilterStr.toString(), params.toArray(), null);
        } else {
            if(filter.getRequest().getParameter("SLRY") != null) {
                list = dao.findBySql(sqlFilterStr.toString(), params.toArray(), null);
            } else {
                list = dao.findBySql(sqlFilterStr.toString(), params.toArray(), filter.getPagingBean());
            }
        }
        if (list != null && !list.isEmpty()) {
            /*获取申报号集合*/
            ArrayList<String> SBHList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                String SBH = (String) map.get("SBH");
                SBHList.add(SBH);
            }
            int SBHLength = SBHList.size();
            int mod = SBHLength / 1000 + 1;
            /*查询受理与审核人员，排除开始环节，当in的数量大于1000则自动带上union*/
            for (int i = 0; i < mod ; i++) {
                sqlP.append("select * from ( select t.EXE_ID , t.ASSIGNER_NAME from jbpm6_task t ");
                sqlP.append(" where t.IS_REAL_HANDLED = 1 and TASK_NODENAME <> '开始'  and t.EXE_ID in (  ");
                if (i == mod - 1) {
                    for (int j = i * 1000; j < SBHList.size(); j++) {
                        sqlP.append("'");
                        sqlP.append(SBHList.get(j));
                        sqlP.append("',");
                    }
                } else {
                    for (int j = i * 1000; j < (i + 1) * 1000; j++) {
                        sqlP.append("'");
                        sqlP.append(SBHList.get(j));
                        sqlP.append("',");
                    }
                }
                sqlP.delete(sqlP.length() - 1, sqlP.length());
                sqlP.append(") order by t.EXE_ID , t.END_TIME ) union ");
            }
            sqlP.delete(sqlP.length() - 6, sqlP.length());
            List<Map<String, Object>> bySqlP = dao.findBySql(sqlP.toString(), null, null);
            ArrayList<String> strList = new ArrayList<>();
            int x;
            /*将申报号与受理人员姓名组成字符串，用|分隔*/
            for(String SBH : SBHList) {
                StringBuffer str = new StringBuffer();
                str.append(SBH);
                str.append("|");
                x = 0;
                for (Map<String, Object> sqlPMap : bySqlP) {
                    if (SBH.equals(sqlPMap.get("EXE_ID"))) {
                        str.append(sqlPMap.get("ASSIGNER_NAME"));
                        str.append(",");
                        x ++;
                    }
                    if (x > 1) {
                        break;
                    }
                }
                strList.add(str.substring(0, str.length() - 1));
            }
            /*处理字符串，只取第一个和第二个人员*/
            for(Map<String,Object> sqlMap : list) {
                for(String str : strList) {
                    if (str.indexOf('|') != -1) {
                        if (str.split("\\|")[0].equals(sqlMap.get("SBH"))) {
                            if (str.split("\\|")[1].indexOf(',') != -1) {
                                sqlMap.put("SLRY", str.split("\\|")[1].split(",")[0]);
                                sqlMap.put("SHRY", str.split("\\|")[1].split(",")[1]);
                            } else {
                                sqlMap.put("SLRY", str.split("\\|")[1]);
                                sqlMap.put("SHRY", "");
                            }
                        }
                    }
                }
                /*put进集合中*/
                String SJKS = (String) sqlMap.get("SJKS");
                String SJJS = (String) sqlMap.get("SJJS");
                String BJKS = (String) sqlMap.get("BJKS");
                String BJJS = (String) sqlMap.get("BJJS");
                if (SJKS != null && SJJS != null) {
                    long workMinuteSJ = new DateTimeUtil().getWorkMinute(SJKS, SJJS);
                    sqlMap.put("SLSC", workMinuteSJ);
                } else {
                    sqlMap.put("SLSC", null);
                }
                if (BJKS != null && BJJS != null) {
                    Long workMinuteBJ = new DateTimeUtil().getWorkMinute(BJKS, BJJS);
                    sqlMap.put("BJSC", workMinuteBJ);
                } else {
                    sqlMap.put("BJSC", null);
                }

                /*受理得分和审核得分*/
                double FZXSSZ = Double.parseDouble(sqlMap.get("FZXSSZ").toString());
                double SLSHXS = Double.parseDouble(sqlMap.get("SLSHXS").toString());
                String BJPY = (String) sqlMap.get("BJPY");
                String WCQK = (String) sqlMap.get("WCQK");
                double SLDF = FZXSSZ;
                double SHDF = FZXSSZ * SLSHXS;
                if (WCQK.equals("逾期")){
                    SLDF = 0;
                    SHDF = 0;
                } else {
                    if (BJPY.equals("满意")) {
                        SLDF = SLDF * 0.5;
                        SHDF = SHDF * 0.5;
                    } else if (BJPY.equals("不满意")) {
                        SLDF = -5;
                    }
                }
                sqlMap.put("SLDF",SLDF);
                sqlMap.put("SHDF",SHDF);
            }
        }
        return list;
    }

    /**
     * 描述：一窗通办办件等待时长统计表
     *
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    @Override
    public List<Map<String, Object>> findddsctjDataBySqlFilter(SqlFilter filter) {
        if (filter.getRequest().getParameter("Q_a.create_time_>=") != null) {
            filter.removeFilter("Q_A.CREATE_TIME_GE");
        }
        ArrayList<String> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.OPERATOR_NAME SLRY, nvl(a.BUSINESS_CODE,'合计') SXLB , ");
        sql.append(" case when a.BUSINESS_CODE in ('A','B','O','W','X','Q','J') then '不动产' ");
        sql.append(" when a.BUSINESS_CODE in ('C','D','Y') then '公积金' when a.BUSINESS_CODE in ('E','F') then '医保' ");
        sql.append(" when a.BUSINESS_CODE in ('G','H','K') then '社保' when a.BUSINESS_CODE in ");
        sql.append(" ('P') then '批量' end YWLX ,  ");
        sql.append(" count(CALL_STATUS) QHS ,  sum(case when a.CALL_STATUS =2 then 1 else 0 end) GHS  ");
        sql.append(" , sum(case when a.CALL_STATUS =1 then 1 else 0 end) YSLS, ");
        sql.append(" round(sum(case when to_char(to_date(OPER_TIME,'yyyy-mm-dd hh24:mi:ss'),'hh24') > 13 ");
        sql.append(" and to_char(to_date(CREATE_TIME,'yyyy-mm-dd hh24:mi:ss'),'hh24') <12 ");
        sql.append(" then ceil((to_date(to_char(to_date(OPER_TIME,'yyyy-mm-dd hh24:mi:ss') - 2/24, ");
        sql.append(" 'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') - to_date(CREATE_TIME  ");
        sql.append(" , 'yyyy-mm-dd hh24:mi:ss')) * 24 * 60) ");
        sql.append(" else ceil((To_date(a.OPER_TIME , 'yyyy-mm-dd hh24:mi:ss') - To_date(a.CREATE_TIME ");
        sql.append(" , 'yyyy-mm-dd hh24:mi:ss')) * 24*60) end) / count(CALL_STATUS),2) PJDHSC ");
        sql.append(" from T_CKBS_QUEUERECORD a left join T_CKBS_QUEUERECORD_ITEMDETAIL b ");
        sql.append(" on a.RECORD_ID = b.RECORD_ID  where a.OPERATOR_NAME is not  null  ");
        sql.append(" and a.OPERATOR_NAME not in ('超级管理员','12','测试用户') ");
        sql.append(" and REGEXP_LIKE(a.BUSINESS_CODE , '[a-zA-Z]')  ");
        String sqlFilter = dao.getQuerySql(filter, sql.toString(), params);
        StringBuffer sqlFilterStr = new StringBuffer(sqlFilter);
        sqlFilterStr.append(" group by rollup (a.OPERATOR_NAME, a.BUSINESS_CODE) order by OPERATOR_NAME ");
        return dao.findBySql(sqlFilterStr.toString(), params.toArray(), null);
    }

    /**
     * 描述：一窗通办办件统计表数据
     *
     * @author Madison You
     * @created 2019-07-05 上午11:28:01
     */
    @Override
    public List<Map<String, Object>> findycbjtjDataBySqlFilter(SqlFilter filter) {
        if (filter.getRequest().getParameter("Q_A.CREATE_TIME_>=") != null) {
            filter.removeFilter("Q_A.CREATE_TIME_GE");
        }
        ArrayList<Object> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" select d.OPERATOR_NAME SLRY , D.DEPART_NAME || '(' || DC.DEPART_NAME || ')' BM , ");
        sql.append(" c.DIC_CODE , nvl(d.EVALUATE, '-1') EVALUATE , nvl(a.REV_STARTTIME, '2019-01-01 09:00:00') ");
        sql.append(" REV_STARTTIME , nvl(a.REV_ENDTIME, '2019-01-01 09:10:00') REV_ENDTIME , ");
        sql.append(" a.END_TIME , a.CREATE_TIME from JBPM6_EXECUTION a left join T_WSBS_SERVICEITEM b  ");
        sql.append(" on a.ITEM_CODE = b.ITEM_CODE left join T_MSJW_SYSTEM_DEPARTMENT e on b.IMPL_DEPART_ID = e.DEPART_ID ");
//        sql.append(" LEFT JOIN (select CATALOG_CODE, D1.DEPART_ID,d1.depart_name from t_wsbs_serviceitem_catalog C ");
//        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID = C.CHILD_DEPART_ID) DC ON ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DC on b.ZBCS_ID = DC.DEPART_ID ");
        sql.append(" left join T_MSJW_SYSTEM_DICTIONARY c on c.DIC_CODE = b.SXLX ");
        sql.append(" LEFT JOIN T_CKBS_QUEUERECORD d on d.EXE_ID = a.EXE_ID where c.TYPE_CODE = 'ServiceItemType' ");
        sql.append(" and a.END_TIME is not null and d.CALL_STATUS = 1 ");
        String sqlFilter = dao.getQuerySql(filter, sql.toString(), params);
        StringBuffer sqlFilterStr = new StringBuffer(sqlFilter);
        sqlFilterStr.append(" order by d.OPERATOR_NAME , D.DEPART_NAME || '(' || DC.DEPART_NAME || ')' ");
        List<Map<String,Object>> sqlList = dao.findBySql(sqlFilterStr.toString(), params.toArray(), null);
        /*获取去重后的人员与部门*/
        Set<Map<String, Object>> sqlSet = new LinkedHashSet<>();
        for(Map<String,Object> sqlMap : sqlList) {
            Map<String, Object> map = new HashMap<>();
            map.put("SLRY",sqlMap.get("SLRY"));
            map.put("BM", sqlMap.get("BM"));
            sqlSet.add(map);
        }
        /*获取每个受理人员的最后一个事项的下标 , 用来添加每个人的合计
         * 1.获取受理人员去重后的arrayList
         * 2.获取受理人员没去重后的arrayList
         * */
        ArrayList<String> uniqueSLRYList = new ArrayList<>();
        LinkedHashSet<String> uniqueSLRYSet = new LinkedHashSet<>();
        for(Map<String,Object> uniqueSLRY : sqlSet) {
            uniqueSLRYList.add((String) uniqueSLRY.get("SLRY"));
            uniqueSLRYSet.add((String) uniqueSLRY.get("SLRY"));
        }
        ArrayList<Map<String,Object>> indexList = new ArrayList<>();
        for(String SLRY : uniqueSLRYSet) {
            int index = uniqueSLRYList.lastIndexOf(SLRY);
            HashMap<String, Object> map = new HashMap<>();
            map.put("SLRY", SLRY);
            map.put("index", index);
            indexList.add(map);
        }
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for(Map<String,Object> uniqueSqlMap : sqlSet) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            String uSLRY = (String) uniqueSqlMap.get("SLRY");
            String uBM = (String) uniqueSqlMap.get("BM");
            int BJHJ = 0,BJJB = 0,BJPT = 0,BJCN = 0,FCMYHJ = 0,FCMYJB = 0,FCMYPT = 0,FCMYCN = 0;
            int BMYHJ = 0, BMYJB = 0, BMYPT = 0, BMYCN = 0, MYHJ = 0;
            int MYJB = 0,MYPT = 0,MYCN = 0,YBHJ = 0,YBJB = 0,YBPT = 0,YBCN = 0;
            long workMinuteBJHJ = 0,workMinuteBJJB = 0,workMinuteBJPT = 0,workMinuteBJCN = 0;
            long workMinuteSLHJ = 0, workMinuteSLJB = 0, workMinuteSLPT = 0, workMinuteSLCN = 0;
            for (Map<String, Object> sqlMap : sqlList) {
                String SLRY = (String) sqlMap.get("SLRY");
                String BM = (String) sqlMap.get("BM");
                String DIC_CODE = (String) sqlMap.get("DIC_CODE");
                String EVALUATE = (String) sqlMap.get("EVALUATE");
                String REV_STARTTIME = (String) sqlMap.get("REV_STARTTIME");
                String REV_ENDTIME = (String) sqlMap.get("REV_ENDTIME");
                String END_TIME = (String) sqlMap.get("END_TIME");
                String CREATE_TIME = (String) sqlMap.get("CREATE_TIME");
                if (uSLRY.equals(SLRY) && uBM.equals(BM)) {
                    /*即办件1，普通件2，特殊件3*/
                    BJHJ++;
                    if (DIC_CODE.equals("1")) {
                        BJJB++;
                    } else if (DIC_CODE.equals("2")) {
                        BJPT++;
                    } else if (DIC_CODE.equals("3")) {
                        BJCN++;
                    }
                    /*0不满意，1一般，2满意，3非常满意，4不评价*/
                    if (EVALUATE.equals("3")) {
                        FCMYHJ++;
                        if (DIC_CODE.equals("1")) {
                            FCMYJB++;
                        } else if (DIC_CODE.equals("2")) {
                            FCMYPT++;
                        } else if (DIC_CODE.equals("3")) {
                            FCMYCN++;
                        }
                    } else if (EVALUATE.equals("0")) {
                        BMYHJ++;
                        if (DIC_CODE.equals("1")) {
                            BMYJB++;
                        } else if (DIC_CODE.equals("2")) {
                            BMYPT++;
                        } else if (DIC_CODE.equals("3")) {
                            BMYCN++;
                        }
                    } else if (EVALUATE.equals("2")) {
                        MYHJ++;
                        if (DIC_CODE.equals("1")) {
                            MYJB++;
                        } else if (DIC_CODE.equals("2")) {
                            MYPT++;
                        } else if (DIC_CODE.equals("3")) {
                            MYCN++;
                        }
                    } else if (EVALUATE.equals("1")) {
                        YBHJ++;
                        if (DIC_CODE.equals("1")) {
                            YBJB++;
                        } else if (DIC_CODE.equals("2")) {
                            YBPT++;
                        } else if (DIC_CODE.equals("3")) {
                            YBCN++;
                        }
                    }
                    /*计算办件时长*/
                    long workMinuteBJ = new DateTimeUtil().getWorkMinute(CREATE_TIME, END_TIME);
                    workMinuteBJHJ += workMinuteBJ;
                    if (DIC_CODE.equals("1")) {
                        workMinuteBJJB += workMinuteBJ;
                    } else if (DIC_CODE.equals("2")) {
                        workMinuteBJPT += workMinuteBJ;
                    } else if (DIC_CODE.equals("3")) {
                        workMinuteBJCN += workMinuteBJ;
                    }
//                    /*计算受理时长*/
                    long workMinuteSL = new DateTimeUtil().getWorkMinute(REV_STARTTIME, REV_ENDTIME);
                    workMinuteSLHJ += workMinuteSL;
                    if(DIC_CODE.equals("1")) {
                        workMinuteSLJB += workMinuteSL;
                    }else if(DIC_CODE.equals("2")) {
                        workMinuteSLPT += workMinuteSL;
                    }else if(DIC_CODE.equals("3")) {
                        workMinuteSLCN += workMinuteSL;
                    }
                }
            }
            /*计算平均办件平均受理时长，将分钟变为天时分钟*/
            long avgWorkMinuteBJHJ = 0,avgWorkMinuteBJJB = 0,avgWorkMinuteBJPT = 0,avgWorkMinuteBJCN = 0;
            long avgWorkMinuteSLHJ = 0,avgWorkMinuteSLJB = 0,avgWorkMinuteSLPT = 0,avgWorkMinuteSLCN = 0;
            if (BJHJ != 0) { avgWorkMinuteBJHJ = workMinuteBJHJ / BJHJ; }
            if (BJJB != 0) { avgWorkMinuteBJJB = workMinuteBJJB / BJJB; }
            if (BJPT != 0) { avgWorkMinuteBJPT = workMinuteBJPT / BJPT; }
            if (BJCN != 0) { avgWorkMinuteBJCN = workMinuteBJCN / BJCN; }
            if (BJHJ != 0) {avgWorkMinuteSLHJ = workMinuteSLHJ / BJHJ;}
            if (BJJB != 0) {avgWorkMinuteSLJB = workMinuteSLJB / BJJB;}
            if (BJPT != 0) {avgWorkMinuteSLPT = workMinuteSLPT / BJPT;}
            if (BJCN != 0) {avgWorkMinuteSLCN = workMinuteSLCN / BJCN;}
            map.put("SLRY", uSLRY);
            map.put("BM", uBM);
            map.put("BJHJ", BJHJ);
            map.put("BJJB", BJJB);
            map.put("BJPT", BJPT);
            map.put("BJCN", BJCN);
            map.put("BJSCHJ", changeMinuteToDayHourMinute(avgWorkMinuteBJHJ));
            map.put("BJSCJB", changeMinuteToDayHourMinute(avgWorkMinuteBJJB));
            map.put("BJSCPT", changeMinuteToDayHourMinute(avgWorkMinuteBJPT));
            map.put("BJSCCN", changeMinuteToDayHourMinute(avgWorkMinuteBJCN));
            map.put("SLSCHJ", changeMinuteToDayHourMinute(avgWorkMinuteSLHJ));
            map.put("SLSCJB", changeMinuteToDayHourMinute(avgWorkMinuteSLJB));
            map.put("SLSCPT", changeMinuteToDayHourMinute(avgWorkMinuteSLPT));
            map.put("SLSCCN", changeMinuteToDayHourMinute(avgWorkMinuteSLCN));
            map.put("FCMYHJ", FCMYHJ);
            map.put("FCMYJB", FCMYJB);
            map.put("FCMYPT", FCMYPT);
            map.put("FCMYCN", FCMYCN);
            map.put("MYHJ", MYHJ);
            map.put("MYJB", MYJB);
            map.put("MYPT", MYPT);
            map.put("MYCN", MYCN);
            map.put("YBHJ", YBHJ);
            map.put("YBJB", YBJB);
            map.put("YBPT", YBPT);
            map.put("YBCN", YBCN);
            map.put("BMYHJ", BMYHJ);
            map.put("BMYJB", BMYJB);
            map.put("BMYPT", BMYPT);
            map.put("BMYCN", BMYCN);
            map.put("BJSCHJFZ", workMinuteBJHJ);
            map.put("BJSCJBFZ", workMinuteBJJB);
            map.put("BJSCPTFZ", workMinuteBJPT);
            map.put("BJSCCNFZ", workMinuteBJCN);
            map.put("SLSCHJFZ", workMinuteSLHJ);
            map.put("SLSCJBFZ", workMinuteSLJB);
            map.put("SLSCPTFZ", workMinuteSLPT);
            map.put("SLSCCNFZ", workMinuteSLCN);
            list.add(map);
        }
        List<Map<String, Object>> allList = findTotal(list, indexList);
        return allList;
    }

    /*计算一窗通办办件统计表合计数据*/
    private List<Map<String,Object>> findTotal(List<Map<String,Object>> list , List<Map<String,Object>> indexList){
        int y = 0;
        for(Map<String,Object> index : indexList) {
            int BJHJ = 0,BJJB = 0,BJPT = 0,BJCN = 0,FCMYHJ = 0,FCMYJB = 0;
            int FCMYPT = 0, FCMYCN = 0, MYHJ = 0, MYJB = 0, MYPT = 0, MYCN = 0;
            int BMYHJ = 0,BMYJB = 0,BMYPT = 0,BMYCN = 0,YBHJ = 0,YBJB = 0,YBPT = 0,YBCN = 0;
            long workMinuteBJHJ = 0,workMinuteBJJB = 0,workMinuteBJPT = 0,workMinuteBJCN = 0;
            long workMinuteSLHJ = 0,workMinuteSLJB = 0,workMinuteSLPT = 0,workMinuteSLCN = 0;
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("SLRY", index.get("SLRY"));
            map.put("BM", "合计");
            for(Map<String,Object> map1 : list) {
                if (map1.get("SLRY").equals(index.get("SLRY"))) {
                    BJHJ += (int) map1.get("BJHJ");
                    BJJB += (int) map1.get("BJJB");
                    BJPT += (int) map1.get("BJPT");
                    BJCN += (int) map1.get("BJCN");
                    FCMYHJ += (int) map1.get("FCMYHJ");
                    FCMYJB += (int) map1.get("FCMYJB");
                    FCMYPT += (int) map1.get("FCMYPT");
                    FCMYCN += (int) map1.get("FCMYCN");
                    YBHJ += (int) map1.get("YBHJ");
                    YBJB += (int) map1.get("YBJB");
                    YBPT += (int) map1.get("YBPT");
                    YBCN += (int) map1.get("YBCN");
                    BMYHJ += (int) map1.get("BMYHJ");
                    BMYJB += (int) map1.get("BMYJB");
                    BMYPT += (int) map1.get("BMYPT");
                    BMYCN += (int) map1.get("BMYCN");
                    MYHJ += (int) map1.get("MYHJ");
                    MYJB += (int) map1.get("MYJB");
                    MYPT += (int) map1.get("MYPT");
                    MYCN += (int) map1.get("MYCN");
                    if (map1.get("BJSCHJFZ") != null) { workMinuteBJHJ += (long) map1.get("BJSCHJFZ"); }
                    if (map1.get("BJSCJBFZ") != null) { workMinuteBJJB += (long) map1.get("BJSCJBFZ"); }
                    if (map1.get("BJSCPTFZ") != null) { workMinuteBJPT += (long) map1.get("BJSCPTFZ"); }
                    if (map1.get("BJSCCNFZ") != null) { workMinuteBJCN += (long) map1.get("BJSCCNFZ"); }
                    if (map1.get("SLSCHJFZ") != null) { workMinuteSLHJ += (long) map1.get("SLSCHJFZ"); }
                    if (map1.get("SLSCJBFZ") != null) { workMinuteSLJB += (long) map1.get("SLSCJBFZ"); }
                    if (map1.get("SLSCPTFZ") != null) { workMinuteSLPT += (long) map1.get("SLSCPTFZ"); }
                    if (map1.get("SLSCCNFZ") != null) { workMinuteSLCN += (long) map1.get("SLSCCNFZ"); }
                }
            }
            long avgWorkMinuteBJHJ = 0,avgWorkMinuteBJJB = 0,avgWorkMinuteBJPT = 0,avgWorkMinuteBJCN = 0;
            long avgWorkMinuteSLHJ = 0;
            long avgWorkMinuteSLJB = 0;
            long avgWorkMinuteSLPT = 0;
            long avgWorkMinuteSLCN = 0;
            map.put("BJHJ", BJHJ);
            map.put("BJJB", BJJB);
            map.put("BJPT", BJPT);
            map.put("BJCN", BJCN);
            if (BJHJ != 0) {avgWorkMinuteBJHJ = workMinuteBJHJ / BJHJ;}
            if (BJJB != 0) { avgWorkMinuteBJJB = workMinuteBJJB / BJJB; }
            if (BJPT != 0) { avgWorkMinuteBJPT = workMinuteBJPT / BJPT; }
            if (BJCN != 0) { avgWorkMinuteBJCN = workMinuteBJCN / BJCN; }
            if (BJHJ != 0) { avgWorkMinuteSLHJ = workMinuteSLHJ / BJHJ; }
            if (BJJB != 0) { avgWorkMinuteSLJB = workMinuteSLJB / BJJB; }
            if (BJPT != 0) { avgWorkMinuteSLPT = workMinuteSLPT / BJPT; }
            if (BJCN != 0) { avgWorkMinuteSLCN = workMinuteSLCN / BJCN; }
            map.put("BJSCHJ", changeMinuteToDayHourMinute(avgWorkMinuteBJHJ));
            map.put("BJSCJB", changeMinuteToDayHourMinute(avgWorkMinuteBJJB));
            map.put("BJSCPT", changeMinuteToDayHourMinute(avgWorkMinuteBJPT));
            map.put("BJSCCN", changeMinuteToDayHourMinute(avgWorkMinuteBJCN));
            map.put("SLSCHJ", changeMinuteToDayHourMinute(avgWorkMinuteSLHJ));
            map.put("SLSCJB", changeMinuteToDayHourMinute(avgWorkMinuteSLJB));
            map.put("SLSCPT", changeMinuteToDayHourMinute(avgWorkMinuteSLPT));
            map.put("SLSCCN", changeMinuteToDayHourMinute(avgWorkMinuteSLCN));
            map.put("FCMYHJ", FCMYHJ);
            map.put("FCMYJB", FCMYJB);
            map.put("FCMYPT", FCMYPT);
            map.put("FCMYCN", FCMYCN);
            map.put("MYHJ", MYHJ);
            map.put("MYJB", MYJB);
            map.put("MYPT", MYPT);
            map.put("MYCN", MYCN);
            map.put("YBHJ", YBHJ);
            map.put("YBJB", YBJB);
            map.put("YBPT", YBPT);
            map.put("YBCN", YBCN);
            map.put("BMYHJ", BMYHJ);
            map.put("BMYJB", BMYJB);
            map.put("BMYPT", BMYPT);
            map.put("BMYCN", BMYCN);
            list.add((int)index.get("index") + 1 + y++, map);
        }
        return list;
    }

    /**
     * 描述：修改事项系数表的修改数据
     *
     * @author Madison You
     * @created 2019-07-12 上午11:28:01
     */
    @Override
    public List<Map<String, Object>> findsxxsszDataBySqlFilter(SqlFilter filter) {
        if (filter.getRequest().getParameter("Q_C.CREATE_TIME_>=") != null) {
            filter.removeFilter("Q_C.CREATE_TIME_GE");
        }
        String ITEM_NAME = filter.getRequest().getParameter("ITEM_NAME");
        String SSCS = filter.getRequest().getParameter("SSCS");
        String BJLX = filter.getRequest().getParameter("BJLX");
        String YCTB = filter.getRequest().getParameter("YCTB");
        String YS_EX = filter.getRequest().getParameter("YS_EX");
        ArrayList<Object> params1 = new ArrayList<>();
        ArrayList<Object> params2 = new ArrayList<>();
        /*查询所有办件*/
        StringBuffer sql = new StringBuffer("");
        sql.append(" select c.ITEM_CODE,  c.CREATE_TIME , c.END_TIME , c.REV_STARTTIME , c.REV_ENDTIME , c.SQFS ");
        sql.append(" from JBPM6_EXECUTION c left join T_WSBS_SERVICEITEM b on b.ITEM_CODE = c.ITEM_CODE ");
        sql.append(" where b.fwsxzt = 1 and c.END_TIME is not null ");
        String sqlFilter = dao.getQuerySql(filter, sql.toString(), params1);
        List<Map<String,Object>> sqlList = dao.findBySql(sqlFilter, params1.toArray(), null);
        StringBuffer uniqueSql = new StringBuffer("");
        /*查询所有事项*/
        uniqueSql.append(" select d.ITEM_CODE , d.item_name ZXMC , b.DEPART_NAME SSCS , d.FZXSSZ , d.SLSHXS , ");
        uniqueSql.append(" h.DIC_NAME YCTB , f.DIC_NAME BJLX , nvl(g.BJSL,0) BJSL from T_WSBS_SERVICEITEM d ");
        uniqueSql.append(" left join T_MSJW_SYSTEM_DEPARTMENT b on b.DEPART_CODE = d.SSBMBM left join (select ");
        uniqueSql.append(" DIC_CODE , DIC_NAME from T_MSJW_SYSTEM_DICTIONARY where TYPE_CODE = 'ServiceItemType') ");
        uniqueSql.append(" f on f.DIC_CODE = d.SXLX left join (select DIC_CODE , DIC_NAME from  ");
        uniqueSql.append(" T_MSJW_SYSTEM_DICTIONARY where TYPE_CODE = 'YesOrNo') h on d.IS_YCTB = h.DIC_CODE ");
        uniqueSql.append(" left join ( select  a.item_code , count(c.EXE_ID) BJSL ");
        uniqueSql.append(" from T_WSBS_SERVICEITEM a right join JBPM6_EXECUTION c on c.ITEM_CODE = a.ITEM_CODE ");
        uniqueSql.append(" where c.END_TIME is not null ");
        String uSqlFilter = dao.getQuerySql(filter, uniqueSql.toString(), params2);
        StringBuffer uSqlFilterStr = new StringBuffer(uSqlFilter);
        uSqlFilterStr.append(" group by a.item_code ) g on g.ITEM_CODE = d.ITEM_CODE where d.fwsxzt = 1 ");
        /*查询条件*/
        if (ITEM_NAME != null ) {
            uSqlFilterStr.append(" and d.ITEM_NAME like ? ");
            params2.add("%" + ITEM_NAME + "%");
        }
        if (SSCS != null ) {
            uSqlFilterStr.append(" and b.DEPART_NAME like ? ");
            params2.add("%" + SSCS + "%");
        }
        if (BJLX != null ) {
            uSqlFilterStr.append(" and f.DIC_CODE = ? ");
            params2.add(BJLX);
        }
        if (YCTB != null ) {
            uSqlFilterStr.append(" and d.IS_YCTB = ? ");
            params2.add(YCTB);
        }
        uSqlFilterStr.append(" order by G.BJSL , b.DEPART_NAME , d.ITEM_CODE  ");
        List<Map<String, Object>> uniqueSqlList;
        if (YS_EX == null) {
            uniqueSqlList = dao.findBySql(uSqlFilterStr.toString(), params2.toArray(), filter.getPagingBean());
        } else {
            uniqueSqlList = dao.findBySql(uSqlFilterStr.toString(), params2.toArray(), null);
        }
        /*事项与办件一一对应*/
        for(Map<String,Object> uniqueSqlMap : uniqueSqlList) {
            int BJSL = Integer.parseInt(uniqueSqlMap.get("BJSL").toString());
            int BJSLSL = BJSL;
            float FZXSSZ = Float.parseFloat(uniqueSqlMap.get("FZXSSZ").toString());
            float SLSHXS = Float.parseFloat((uniqueSqlMap).get("SLSHXS").toString());
            long sumBJTime = 0;
            long sumSLTime = 0;
            for(Map<String,Object> sqlMap : sqlList) {
                if (uniqueSqlMap.get("ITEM_CODE").equals(sqlMap.get("ITEM_CODE"))) {
                    /*平均办件时长*/
                    long intervalBJMinute = DateTimeUtil.getIntervalMinute((String) sqlMap.get("create_time"),
                            (String) sqlMap.get("end_time"));
                    sumBJTime += intervalBJMinute;
                    /*平均受理时长*/
                    String REV_STARTTIME = (String) sqlMap.get("REV_STARTTIME");
                    String REV_ENDTIME = (String) sqlMap.get("REV_ENDTIME");
                    String SQFS = (String) sqlMap.get("SQFS");
                    if (REV_ENDTIME != null && REV_STARTTIME != null && SQFS.equals("2")) {
                        long intervalSLMinute = DateTimeUtil.getIntervalMinute(REV_STARTTIME,
                                REV_ENDTIME);
                        sumSLTime += intervalSLMinute;
                    } else {
                        BJSLSL--;
                    }
                }
            }
            long avgBJTime = 0;
            long avgSLTime = 0;
            if (BJSL != 0) {
                avgBJTime = sumBJTime / BJSL;
            }
            if (BJSLSL != 0) {
                avgSLTime = sumSLTime / BJSLSL;
            }
            double GZRSL = FZXSSZ;
            double GZRSH = FZXSSZ * SLSHXS ;
            double XXRSL = FZXSSZ * 1.5;
            double XXRSH = FZXSSZ * SLSHXS * 1.5;
            double JJRSL = FZXSSZ * 2;
            double JJRSH = FZXSSZ * SLSHXS * 2;
            uniqueSqlMap.put("SXBM", uniqueSqlMap.get("ITEM_CODE"));
            uniqueSqlMap.put("PJBJSC", changeMinuteToDayHourMinute(avgBJTime));
            uniqueSqlMap.put("PJSLSC", changeMinuteToDayHourMinute(avgSLTime));
            uniqueSqlMap.put("GZRSL", StringUtils.getNumFormat(GZRSL));
            uniqueSqlMap.put("GZRSH", StringUtils.getNumFormat(GZRSH));
            uniqueSqlMap.put("XXRSL", StringUtils.getNumFormat(XXRSL));
            uniqueSqlMap.put("XXRSH", StringUtils.getNumFormat(XXRSH));
            uniqueSqlMap.put("JJRSL", StringUtils.getNumFormat(JJRSL));
            uniqueSqlMap.put("JJRSH", StringUtils.getNumFormat(JJRSH));
        }
        return uniqueSqlList;
    }


    /**
     * 描述:环土局专属行业列表
     *
     * @author Madison You
     * @created 2019/8/22 10:36:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> htIndustryList(HttpServletRequest request) {
        StringBuffer sql = new StringBuffer("");
        sql.append(" select * from T_MSJW_SYSTEM_HTINDUSTRY where 1=1 order by industry_code ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        return list;
    }

    /**
     * 描述: 绩效考核表格人员分组
     *
     * @author Madison You
     * @created 2019/9/2 16:27:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> ryfzData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer("");
        ArrayList<String> params = new ArrayList<>();
        sql.append(" select a.USER_ID , b.DEPART_NAME , a.FULLNAME , a.USERNAME , c.DIC_NAME GWLX , a.ZB ");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER a left join T_MSJW_SYSTEM_DEPARTMENT b on a.DEPART_ID = ");
        sql.append(" b.DEPART_ID left join (select dic_code,dic_name from T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" where type_code = 'GWLX') c on c.DIC_CODE = a.GWLX where 1 = 1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        querySql += " order by b.DEPART_NAME ";
        List<Map<String,Object>> list = dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述:工程建设项目查询数据
     *
     * @author Madison You
     * @created 2019/9/10 10:17:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> gcjsxmcxData(SqlFilter filter) {
        /*获取当前已办理的工程建设项目*/
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        sql.append(" select a.project_code , a.project_name , b.ENTERPRISE_NAME , b.CONTACT_NAME , ");
        sql.append(" b.CONTACT_PHONE , a.CREATE_TIME , a.SFLXYZ from SPGL_XMJBXXB a ");
        sql.append(" left join SPGL_XMDWXXB b on a.ID = b.JBXX_ID where 1 = 1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        querySql += " order by a.create_time desc ";
        List<Map<String,Object>> list = dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        return list;
    }


    /**
     * 描述:工程建设项目查询获取所有已上传材料
     *
     * @author Madison You
     * @created 2019/9/16 14:27:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> fetchGcjsxmcxMater(SqlFilter filter) {
        String projectCode = filter.getRequest().getParameter("PROJECT_CODE");
        ArrayList<String> params = new ArrayList<>();
        params.add(projectCode);
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from T_MSJW_SYSTEM_FILEATTACH where BUS_TABLERECORDID in ( ");
        sql.append(" select BUS_RECORDID from JBPM6_EXECUTION where PROJECT_CODE = ? ) ");
        List<Map<String,Object>> fileList = dao.findBySql(sql.toString(), params.toArray(), null);
        return fileList;
    }

    /**
     * 描述:工程建设项目当前已办理事项和办件
     *
     * @author Madison You
     * @created 2019/9/19 8:56:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> gcjsxmcxDealItemList(SqlFilter filter) {
        String projectCode = filter.getRequest().getParameter("PROJECT_CODE");
        ArrayList<String> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select EXE_ID,SUBJECT,CREATE_TIME,RUN_STATUS,ITEM_NAME from JBPM6_EXECUTION where 1=1 ");
        sql.append(" and PROJECT_CODE = ? ");
        params.add(projectCode);
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String,Object>> list = dao.findBySql(querySql, params.toArray(), null);
        return list;
    }

    /**
     * 描述:绩效考核得分人员数据
     *
     * @author Madison You
     * @created 2019/9/26 15:24:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> jxkhdfryData() {
        StringBuffer sql = new StringBuffer("");
        ArrayList<String> params = new ArrayList<>();
        sql.append(" select a.USER_ID , b.DEPART_NAME , a.FULLNAME  , c.DIC_NAME GWLX , a.ZB ");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER a left join T_MSJW_SYSTEM_DEPARTMENT b on a.DEPART_ID = ");
        sql.append(" b.DEPART_ID left join (select dic_code,dic_name from T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" where type_code = 'GWLX') c on c.DIC_CODE = a.GWLX where 1 = 1 ");
        sql.append(" order by b.DEPART_NAME ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:绩效考核办件分值
     *
     * @author Madison You
     * @created 2019/9/26 16:34:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> jxkhbjfzData(String date) {
        ArrayList<String> params = new ArrayList<>();
        StringBuffer sqlP = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        /*获取今天所有办件*/
        sql.append(" select distinct t.exe_id SBH,s.FZXSSZ,s.SLSHXS, ");
        sql.append(" decode(q.EVALUATE, '0', '不满意', '1', '一般', '2', '满意', '3', '非常满意','4','不评价',null,'未填写') BJPY, ");
        sql.append(" nvl(decode(x.effi_desc, '1', '提前完成', '2', '按期完成', '3', '逾期', '4', '即办'), '未记录') WCQK ");
        sql.append(" from JBPM6_EXECUTION t  left join T_WSBS_SERVICEITEM s on s.item_code = t.item_code ");
        sql.append(" left join JBPM6_EFFICINFO x on x.eflow_exeid = t.exe_id left join T_CKBS_QUEUERECORD ");
        sql.append(" q on q.exe_id = t.exe_id LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE = s.SXLX ");
        sql.append(" where 1 = 1 and t.run_status = '2' and t.CREATE_TIME like '" + date + "%' ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        ArrayList<String> SBHList = new ArrayList<>();
        /*提取申报号*/
        for (Map<String, Object> map : list) {
            String SBH = (String) map.get("SBH");
            SBHList.add(SBH);
        }
        /*获取今天办件中除去开始阶段的所有任务*/
        sqlP.append(" select t.EXE_ID, x.USER_ID ASSIGNER_CODE from jbpm6_task t left join T_MSJW_SYSTEM_SYSUSER  ");
        sqlP.append(" x on x.USERNAME = t.ASSIGNER_CODE where t.IS_REAL_HANDLED = 1 ");
        sqlP.append(" and TASK_NODENAME <> '开始' and t.EXE_ID in (select distinct t.exe_id from JBPM6_EXECUTION t ");
        sqlP.append("  where 1 = 1 and t.CREATE_TIME like '"+date+"%'");
        sqlP.append(" and t.run_status = 2) order by t.EXE_ID , t.END_TIME ");
        List<Map<String, Object>> bySqlP = dao.findBySql(sqlP.toString(), null, null);
        ArrayList<ArrayList> strList = new ArrayList<>();
        int x;
        /*将按照时间排序好的任务中提取出除去开始以外的最先的两个任务的办理人员，跟申报号组成list*/
        for(String SBH : SBHList) {
            ArrayList<Object> str = new ArrayList<>();
            str.add(SBH);
            x = 0;
            for(Map<String,Object> sqlMap : bySqlP) {
                if (SBH.equals(sqlMap.get("EXE_ID"))) {
                    str.add(sqlMap.get("ASSIGNER_CODE"));
                    x++;
                }
                if (x > 1) {
                    break;
                }
            }
            strList.add(str);
        }
        /*计算每个办件的受理审核得分*/
        for(Map<String,Object> sqlMap : list) {
            for(ArrayList str : strList) {
                if (str.get(0).equals(sqlMap.get("SBH"))) {
                    if (str.size() > 2) {
                        sqlMap.put("SLRY", str.get(1));
                        sqlMap.put("SHRY", str.get(2));
                    } else {
                        sqlMap.put("SLRY", str.get(1));
                        sqlMap.put("SHRY", "");
                    }
                }
            }
            double FZXSSZ = Double.parseDouble(sqlMap.get("FZXSSZ").toString());
            double SLSHXS = Double.parseDouble(sqlMap.get("SLSHXS").toString());
            String BJPY = (String) sqlMap.get("BJPY");
            String WCQK = (String) sqlMap.get("WCQK");
            double SLDF = FZXSSZ;
            double SHDF = FZXSSZ * SLSHXS;
            if (WCQK.equals("逾期")){
                SLDF = 0;
                SHDF = 0;
            } else {
                if (BJPY.equals("满意")) {
                    SLDF = SLDF * 0.5;
                    SHDF = SHDF * 0.5;
                } else if (BJPY.equals("不满意")) {
                    SLDF = -5;
                }
            }
            sqlMap.put("SLDF",SLDF);
            sqlMap.put("SHDF",SHDF);
        }
        return list;
    }

    /**
     * 描述:绩效考核得分计算
     *
     * @author Madison You
     * @created 2019/10/8 8:48:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> jxkhdfCal(SqlFilter filter) {
        StringBuffer rysql = new StringBuffer();
        StringBuffer dfsql = new StringBuffer();
        ArrayList<String> ryParams = new ArrayList<>();
        ArrayList<String> dfParams = new ArrayList<>();
        String createStartDate = filter.getRequest().getParameter("Q_a.create_time_>=");
        String createEndDate = filter.getRequest().getParameter("Q_a.create_time_<=");
        /*获取所有人员*/
        rysql.append(" select a.USER_ID , a.USERNAME ,  b.DEPART_ID , b.DEPART_NAME , a.FULLNAME , a.GWLX , a.ZB ");
        rysql.append(" from T_MSJW_SYSTEM_SYSUSER a left join T_MSJW_SYSTEM_DEPARTMENT b on a.DEPART_ID = ");
        rysql.append(" b.DEPART_ID  where 1 = 1  ");
        if (createStartDate != null) {
            filter.removeFilter("Q_a.create_time_>=");
        }
        if (createEndDate != null) {
            filter.removeFilter("Q_a.create_time_<=");
        }
        String queryRySql = dao.getQuerySql(filter, rysql.toString(), ryParams);
        queryRySql += " order by b.DEPART_NAME ";
        List<Map<String,Object>> ryList = dao.findBySql(queryRySql, ryParams.toArray(), filter.getPagingBean());
        /*获取人员得分*/
        dfsql.append(" select a.USER_ID , b.DEPART_ID , b.ZB , b.GWLX , sum(a.SLDF) SLDF ,sum(a.SHDF) SHDF from ");
        dfsql.append(" JBPM6_JXKHSTATIST a LEFT JOIN T_MSJW_SYSTEM_SYSUSER b on a.USER_ID = b.USER_ID where 1 = 1 ");
        if (createStartDate != null) {
            dfsql.append("and a.create_date >= '" + createStartDate.substring(0, 10) + "'");
        }
        if (createEndDate != null) {
            dfsql.append("and a.create_date <= '" + createEndDate.substring(0, 10) + "'");
        }
        dfsql.append(" group by a.USER_ID , b.ZB , b.GWLX , b.DEPART_ID ");
        List<Map<String,Object>> dfList = dao.findBySql(dfsql.toString(), dfParams.toArray(), null);
        for(Map<String,Object> ryMap : ryList) {
            String GWLX = (String)ryMap.get("GWLX");
            String ZB = (String)ryMap.get("ZB");
            String USER_ID = (String)ryMap.get("USER_ID");
            String DEPART_ID = (String) ryMap.get("DEPART_ID");
            /*A : 窗口人员 B : 复核、审批人员 C : 后台人员*/
            if (GWLX != null) {
                /*窗口人员得分*/
                if (GWLX.equals("A")) {
                    for (Map<String, Object> dfMap : dfList) {
                        if (USER_ID.equals(dfMap.get("USER_ID"))) {
                            ryMap.put("SLDF", dfMap.get("SLDF"));
                            ryMap.put("SHDF", dfMap.get("SHDF"));
                            ryMap.put("BJXJ", Float.parseFloat(dfMap.get("SLDF").toString())
                                    + Float.parseFloat(dfMap.get("SHDF").toString()));
                        }
                    }
                    /*复核、审批人员得分*/
                } else if (GWLX.equals("B")) {
                    double ALLSLDF = 0;
                    double ALLSHDF = 0;
                    int ALLRY = 0;
                    double personalSLDF = 0;
                    double personalSHDF = 0;
                    for(Map<String,Object> dfMap : dfList) {
                        double SLDF = Double.parseDouble(dfMap.get("SLDF").toString());
                        double SHDF = Double.parseDouble(dfMap.get("SHDF").toString());
                        String zb = dfMap.get("ZB") == null ? "x" : (String)dfMap.get("ZB");
                        /*包含组别的*/
                        if (ZB != null && "A".equals(dfMap.get("GWLX")) && ZB.contains(zb)){
                            ALLRY++;
                            ALLSLDF += SLDF;
                            ALLSHDF += SHDF;
                        }
                        if (USER_ID.equals(dfMap.get("USER_ID"))) {
                            ryMap.put("SLDF", dfMap.get("SLDF"));
                            ryMap.put("SHDF", dfMap.get("SHDF"));
                            personalSLDF = Double.parseDouble(dfMap.get("SLDF").toString());
                            personalSHDF = Double.parseDouble(dfMap.get("SHDF").toString());
                        }
                    }
                    double averageSLDF = ALLSLDF / ALLRY * 0.7;
                    double averageSHDF = ALLSHDF / ALLRY * 0.7;
                    ryMap.put("JCF", StringUtils.getNumFormat(averageSLDF + averageSHDF));
                    ryMap.put("BJXJ", StringUtils.getNumFormat(personalSLDF + personalSHDF
                            + averageSLDF + averageSHDF));
                    /*后台人员得分*/
                } else if (GWLX.equals("C")) {
                    double CKDF = 0;
                    int ALLRY = 0;
                    double ALLDF = 0;
                    double personalDF = 0;
                    double DF = 0;
                    int x = 0;
                    for(Map<String,Object> dfMap : dfList) {
                        /*同部门*/
                        if (DEPART_ID.equals(dfMap.get("DEPART_ID"))) {
                            /*同部门的窗口人员得分小计*/
                            if ("A".equals(dfMap.get("GWLX"))) {
                                CKDF += Double.parseDouble(dfMap.get("SLDF").toString());
                                CKDF += Double.parseDouble(dfMap.get("SHDF").toString());
                            }
                            /*同部门复核审批人员得分小计*/
                            if ("B".equals(dfMap.get("GWLX"))) {
                                /*复核、审核人员的受理得分*/
                                personalDF += Double.parseDouble(dfMap.get("SLDF").toString());
                                personalDF += Double.parseDouble(dfMap.get("SHDF").toString());
                                /*复核审核人员人数，跟定时器一个条件有关*/
                                String dfsZB = (String)dfMap.get("ZB");
                                /*复核审核人员的基础分，只需循环一次就可得出*/
                                if (x++ == 0) {
                                    for (Map<String, Object> dfsMap : dfList) {
                                        String dfszb = dfsMap.get("ZB") == null ? "x" : (String) dfsMap.get("ZB");
                                        /*包含组别的，这里不涉及部门，每个部门组别不重复*/
                                        if (dfsZB != null && "A".equals(dfsMap.get("GWLX")) && dfsZB.contains(dfszb)) {
                                            ALLRY++;
                                            ALLDF += Double.parseDouble(dfsMap.get("SLDF").toString());
                                            ALLDF += Double.parseDouble(dfsMap.get("SHDF").toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /*获取同部门下岗位类型为A的人数*/
                    int CKRY = getNumPeopleByGWLXandDep("A", DEPART_ID);
                    /*获取同部门下岗位类型为B的人数*/
                    int personalRY = getNumPeopleByGWLXandDep("B", DEPART_ID);
                    /*同部门复核审批人员得分计算*/
                    double averageDF = ALLDF / ALLRY * 0.7;
                    double averagePDF = personalDF;
                    /*后台人员得分计算 ： (窗口得分 + 复核人员基础分乘以人数 + 复核人员受理审核得分)/(窗口人员 + 复核人员)*/
                    if ((CKRY + personalRY) != 0) {
                        DF = (CKDF + averageDF * personalRY + averagePDF) / (CKRY + personalRY);
                        ryMap.put("JCF", StringUtils.getNumFormat(DF));
                        ryMap.put("BJXJ", StringUtils.getNumFormat(DF));
                    } else {
                        ryMap.put("JCF", 0);
                        ryMap.put("BJXJ", 0);
                    }
                }
            }
        }
        /*加分扣分计算*/
        List<Map<String, Object>> list = jxkhjfkfCal(filter, ryList);
        return list;
    }

    /**
     * 描述:绩效考核加分扣分计算
     *
     * @author Madison You
     * @created 2019/10/15 14:54:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> jxkhjfkfCal(SqlFilter filter , List<Map<String,Object>> ryList) {
        /*获取加分扣分列表*/
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        sql.append(" select * from JBPM6_JXJFKFSTATIST a WHERE 1 = 1 ");
        if (filter.getRequest().getParameter("Q_a.create_time_>=") != null) {
            sql.append(" and a.JFKF_EFFECT_DATE >= '" +
                    filter.getRequest().getParameter("Q_a.create_time_>=").substring(0,10) + "' ");
        }
        if (filter.getRequest().getParameter("Q_a.create_time_<=") != null) {
            sql.append(" and a.JFKF_EFFECT_DATE <= '" +
                    filter.getRequest().getParameter("Q_a.create_time_<=").substring(0,10) + "' ");
        }
        List<Map<String,Object>> jfkfList = dao.findBySql(sql.toString(), params.toArray(), null);
        /*加分扣分计算*/
        for(Map<String,Object> ryMap : ryList) {
            /*将得分为空的都变成0*/
            Object JCF = ryMap.get("JCF");
            Object SLDF = ryMap.get("SLDF");
            Object SHDF = ryMap.get("SHDF");
            Object BJXJ = ryMap.get("BJXJ");
            if (JCF == null) {
                ryMap.put("JCF", 0);
            }
            if (SLDF == null) {
                ryMap.put("SLDF", 0);
            }
            if (SHDF == null) {
                ryMap.put("SHDF", 0);
            }
            if (BJXJ == null) {
                ryMap.put("BJXJ", 0);
                BJXJ = 0;
            }
            /*计算扣分*/
            double JFXJ = 0;
            double GRJFXJ = 0;
            double JTJFXJ = 0;
            double KFXJ = 0;
            String USER_ID = (String)ryMap.get("USER_ID");
            for(Map<String,Object> jfkfMap : jfkfList) {
                String JFKF_USERIDS = jfkfMap.get("JFKF_USERIDS").toString();
                if (JFKF_USERIDS.contains(USER_ID)) {
                    Object JFKF_TYPE = jfkfMap.get("JFKF_TYPE");
                    /*加分*/
                    double FZ = Double.parseDouble(jfkfMap.get("JFKF_FZ").toString());
                    if (JFKF_TYPE.equals("JF")) {
                        Object JF_TYPE = jfkfMap.get("JF_TYPE");
                        JFXJ += FZ;
                        /*加分类型*/
                        if (JF_TYPE.equals("GRJF")) {
                            GRJFXJ += FZ;
                        } else if (JF_TYPE.equals("JTJF")) {
                            JTJFXJ += FZ;
                        }
                        /*扣分*/
                    } else {
                        KFXJ += FZ;
                    }
                }
            }
            ryMap.put("GRJF", GRJFXJ);
            ryMap.put("JTJF", JTJFXJ);
            ryMap.put("JFXJ", JFXJ);
            ryMap.put("ZFJLKF", KFXJ);
            /*计算得分合计*/
            double DFHJ = Double.parseDouble(BJXJ.toString()) + JFXJ - KFXJ;
            ryMap.put("DFHJ", StringUtils.getNumFormat(DFHJ));
        }
        return ryList;
    }


    /*将分钟数转为天时分*/
    private String changeMinuteToDayHourMinute(long minutes){
        StringBuffer str = new StringBuffer();
        long day = minutes / (60 * 24);
        long hour = (minutes - day * 24 * 60) / 60;
        long minute = (minutes - day * 24 * 60 - hour * 60);
        if (day > 0) {
            str.append(day + "天");
        }
        if (hour > 0) {
            str.append(hour + "时");
        }
        if (minute > 0) {
            str.append(minute + "分");
        }
        return str.toString();
    }

    /**
     * 描述:根据岗位类型和部门获取人数
     *
     * @author Madison You
     * @created 2019/10/14 9:26:00
     * @param
     * @return
     */
    public int getNumPeopleByGWLXandDep(String gwlx , String department){
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        params.add(gwlx);
        params.add(department);
        sql.append(" select count(*) RS from T_MSJW_SYSTEM_SYSUSER where GWLX = ? and DEPART_ID = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        Map<String, Object> map = list.get(0);
        return Integer.parseInt(map.get("RS").toString());
    }

    /**
     * 描述:绩效考核加分减分数据
     *
     * @author Madison You
     * @created 2019/10/14 17:13:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> jxjfkfData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        sql.append(" select a.JFKF_DATE , d.FULLNAME , b.DIC_NAME JFKF_TYPE , c.DIC_NAME JF_TYPE ,JFKF_USERIDS ,");
        sql.append("  JFKF_USERNAMES , JFKF_CONTENT , JFKF_FZ,JFKF_ID,JFKF_EFFECT_DATE from JBPM6_JXJFKFSTATIST ");
        sql.append(" a left join (select DIC_CODE,DIC_NAME from T_MSJW_SYSTEM_DICTIONARY where TYPE_CODE='JXFSLX') ");
        sql.append(" b on b.DIC_CODE = a.JFKF_TYPE left join (select DIC_CODE,DIC_NAME from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY where TYPE_CODE = 'JFLX') c on c.DIC_CODE = a.JF_TYPE ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER d on d.USER_ID = a.OPERATE_ID WHERE 1 = 1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        querySql += " order by a.JFKF_DATE desc ";
        List<Map<String,Object>> list = dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        return list;
    }


    /**
     * 描述:统计所有还在网上预审的办件
     *
     * @author Madison You
     * @created 2019/10/16 10:14:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> wsysData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        sql.append(" select t.exe_id,t.create_time,D.depart_name,DC.DEPART_NAME depart_name_z, ");
        sql.append(" s.item_name,t.subject,t.cur_stepauditnames ,  ta.assigner_name,u.mobile,ta.assigner_code ");
        sql.append(" from JBPM6_EXECUTION t left join jbpm6_task ta on ta.exe_id = t.exe_id ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER u on ta.assigner_code = u.username ");
        sql.append(" left join T_WSBS_SERVICEITEM s on s.item_code = t.item_code  ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D  ON D.DEPART_ID = S.IMPL_DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DC  ON DC.DEPART_ID = S.ZBCS_ID ");
        sql.append(" where 1=1   and t.cur_stepnames = '网上预审' and t.run_status = 1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        querySql += " order by t.exe_id ";
        return dao.findBySql(querySql, params.toArray(), null);
    }

    /**
     * 描述:预约取号统计数据
     *
     * @author Madison You
     * @created 2020/2/1 11:36:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> callAppointData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select c.DEPART_NAME,b.BUSINESS_NAME,count(a.RECORD_ID) NUM from T_CKBS_APPOINTMENT_APPLY a ");
        sql.append(" left join T_CKBS_BUSINESSDATA b on a.BUSINESS_CODE = b.BUSINESS_CODE ");
        sql.append(" left join T_MSJW_SYSTEM_DEPARTMENT c on b.DEPART_ID = c.DEPART_ID ");
        sql.append(" where 1=1 and RECORD_FORM = '2'");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        querySql += " group by c.DEPART_NAME,b.BUSINESS_NAME ";
        querySql += " order by c.DEPART_NAME,b.BUSINESS_NAME ";
        return dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
    }

    /**
     * 描述:不动产业务受理情况数据
     *
     * @author Madison You
     * @created 2020/4/7 9:54:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> bdcCallData(SqlFilter filter) {
        String isExp = filter.getRequest().getParameter("isExp");
        Map<String, Object> queryParams = filter.getQueryParams();
        if (queryParams != null) {
            String start = (String) queryParams.get("Q_A.STATIS_DATE_>=");
            String end = (String) queryParams.get("Q_A.STATIS_DATE_<=");
            if (start != null) {
                queryParams.put("Q_A.STATIS_DATE_>=", start.substring(0, 10));
                queryParams.put("Q_A.STATIS_DATE_<=", end.substring(0, 10));
            }
        }
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select STATIS_DATE,ZSLL,ZQHL,BDCDJYWSL_QHL,BDCDJYWSL_SLL,BDCDJYWSL_GHL,BDCDJYWSL_QTQK, ");
        sql.append(" BDCDJYWSL_PJSLSC,BDCDJYWSL_PJBJSC,JFLZ_QHL,JFLZ_WCL,JFLZ_GHL,JFLZ_QTQK,WYWXZJ_QHL, ");
        sql.append(" WYWXZJ_WCL,WYWXZJ_GHL,WYWXZJ_QTQK,CLFWQ_QHL,CLFWQ_SLL,CLFWQ_GHL,CLFWQ_QTQK,PLSL_QHL, ");
        sql.append(" PLSL_SLL,PLSL_GHL,PLSL_QTQK,PLSL_PJSLSC,PLSL_PJBJSC ");
        sql.append(" from T_CKBS_BDC_CALLDATA a where 1 = 1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        querySql += " order by a.statis_date desc  ";
        if (isExp != null && isExp.equals("1")) {
            return dao.findBySql(querySql, params.toArray(), null);
        } else {
            return dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        }
    }

    /**
     * 描述:统计不动产每日业务受理情况数据
     *
     * @author Madison You
     * @created 2020/4/7 10:26:00
     * @param
     * @return
     */
    @Override
    public void bdcCallDataCal(String today) {
        Map<String, Object> statisMap = new HashMap<>();
//        statisMap.put("STATIS_DATE", today);//统计时间（yyyy-mm-dd）

        /*初始化，全部值置为0或空字符串*/
        this.bdcCallDataCalInit(statisMap);

        /*获取当日全部不动产业务受理情况数据*/
        List<Map<String, Object>> totalList = this.bdcCalByBusinessCode(today,
                "('A','B','O','W','X','J','Q')");
        if (totalList != null && !totalList.isEmpty()) {
            int totalQhl = 0;
            int totalSll = 0;
            for (Map<String, Object> totalMap : totalList) {
                totalQhl++;
                String callStatus = StringUtil.getValue(totalMap, "CALL_STATUS");
                if (callStatus.equals("1")) {
                    totalSll++;
                }
            }
            statisMap.put("ZSLL", totalSll); //总受理量
            statisMap.put("ZQHL", totalQhl); //总取号量
        }

        /*获取当日不动产登记业务受理情况数据*/
        List<Map<String, Object>> bdcdjywList = this.bdcCalByBusinessCode(today, "('A','B','X')");
        if (bdcdjywList != null && !bdcdjywList.isEmpty()) {
            int bdcdjywQhl = 0;
            int bdcdjywSll = 0;
            int bdcdjywGhl = 0;
            int bdcdjywQtqk = 0;
            int bdcdjywRev = 0;
            int bdcdjywRevDiffDate = 0;
            int bdcdjywPjslsc = 0;
            int bdcdjywExe = 0;
            int bdcdjywExeDiffDate = 0;
            int bdcdjywExePjbjsc = 0;

            for (Map<String, Object> bdcdjywMap : bdcdjywList) {
                bdcdjywQhl++;
                String callStatus = StringUtil.getValue(bdcdjywMap, "CALL_STATUS");
                String createTime = StringUtil.getValue(bdcdjywMap, "CREATE_TIME");
                String endTime = StringUtil.getValue(bdcdjywMap, "END_TIME");
                String revStarttime = StringUtil.getValue(bdcdjywMap, "REV_STARTTIME");
                String revEndtime = StringUtil.getValue(bdcdjywMap, "REV_ENDTIME");
                String exeId = StringUtil.getValue(bdcdjywMap, "EXE_ID");
                if (callStatus.equals("1")) {
                    bdcdjywSll++;
                    /*计算不动产登记业务受理单件业务平均受理时长*/
                    if (!revStarttime.equals("") && !revEndtime.equals("") &&
                            StringUtil.compareDateF(revStarttime,revEndtime,"yyyy-MM-dd HH:mm:ss") == 1) {
                        bdcdjywRev++;
                        try{
                            bdcdjywRevDiffDate += this.compareDate(revStarttime, revEndtime, "0");
                        } catch (Exception e) {
                            log.info("获取当日不动产登记业务受理情况数据日期格式转换错误,申报号为：" + exeId);
                        }
                    }

                    /*计算不动产登记业务受理单件业务办结平均时长*/
                    if (!createTime.equals("") && !endTime.equals("")) {
                        bdcdjywExe++;
                        bdcdjywExeDiffDate += workdayService.getWorkDayCount(createTime, endTime);
                    }
                } else if (callStatus.equals("2")) {
                    bdcdjywGhl++;
                } else {
                    bdcdjywQtqk++;
                }
            }

            if (bdcdjywRev != 0) {
                bdcdjywPjslsc = bdcdjywRevDiffDate / bdcdjywRev;
            }
            if (bdcdjywExe != 0) {
                bdcdjywExePjbjsc = bdcdjywExeDiffDate / bdcdjywExe;
            }

            statisMap.put("BDCDJYWSL_QHL", bdcdjywQhl); //不动产登记业务受理取号量
            statisMap.put("BDCDJYWSL_SLL", bdcdjywSll); //不动产登记业务受理受理量
            statisMap.put("BDCDJYWSL_GHL", bdcdjywGhl); //不动产登记业务受理过号量
            statisMap.put("BDCDJYWSL_QTQK", bdcdjywQtqk); //不动产登记业务受理其他情况
            statisMap.put("BDCDJYWSL_PJSLSC", bdcdjywPjslsc); //不动产登记业务受理单件业务平均受理时长
            statisMap.put("BDCDJYWSL_PJBJSC", bdcdjywExePjbjsc); //不动产登记业务受理单件业务办结平均时长
        }

        /*获取当日缴费领证业务受理情况数据*/
        List<Map<String, Object>> jflzList = this.bdcCalByBusinessCode(today, "('J')");
        if (jflzList != null && !jflzList.isEmpty()) {
            int jflzQhl = 0;
            int jflzWcl = 0;
            int jflzGhl = 0;
            int jflzQtqk = 0;
            for (Map<String, Object> jflzMap : jflzList) {
                jflzQhl++;
                String callStatus = StringUtil.getValue(jflzMap, "CALL_STATUS");
                if (callStatus.equals("1")) {
                    jflzWcl++;
                } else if (callStatus.equals("2")) {
                    jflzGhl++;
                } else {
                    jflzQtqk++;
                }
            }
            statisMap.put("JFLZ_QHL", jflzQhl); //缴费领证取号量
            statisMap.put("JFLZ_WCL", jflzWcl); //缴费领证完成量
            statisMap.put("JFLZ_GHL", jflzGhl); //缴费领证过号量
            statisMap.put("JFLZ_QTQK", jflzQtqk); //缴费领证其他情况
        }

        /*获取当日物业维修资金业务受理情况数据*/
        List<Map<String, Object>> wywxzjList = this.bdcCalByBusinessCode(today, "('Q')");
        if (wywxzjList != null && !wywxzjList.isEmpty()) {
            int wywxzjQhl = 0;
            int wywxzjWcl = 0;
            int wywxzjGhl = 0;
            int wywxzjQtqk = 0;
            for (Map<String, Object> wywxzjMap : wywxzjList) {
                wywxzjQhl++;
                String callStatus = StringUtil.getValue(wywxzjMap, "CALL_STATUS");
                if (callStatus.equals("1")) {
                    wywxzjWcl++;
                } else if (callStatus.equals("2")) {
                    wywxzjGhl++;
                } else {
                    wywxzjQtqk++;
                }
            }
            statisMap.put("WYWXZJ_QHL", wywxzjQhl); //缴费领证取号量
            statisMap.put("WYWXZJ_WCL", wywxzjWcl); //缴费领证完成量
            statisMap.put("WYWXZJ_GHL", wywxzjGhl); //缴费领证过号量
            statisMap.put("WYWXZJ_QTQK", wywxzjQtqk); //缴费领证其他情况
        }

        /*获取当日存量房网签业务受理情况数据*/
        List<Map<String, Object>> clfwqList = this.bdcCalByBusinessCode(today, "('W')");
        if (clfwqList != null && !clfwqList.isEmpty()) {
            int clfwqQhl = 0;
            int clfwqWcl = 0;
            int clfwqGhl = 0;
            int clfwqQtqk = 0;
            for (Map<String, Object> clfwqMap : clfwqList) {
                clfwqQhl++;
                String callStatus = StringUtil.getValue(clfwqMap, "CALL_STATUS");
                if (callStatus.equals("1")) {
                    clfwqWcl++;
                } else if (callStatus.equals("2")) {
                    clfwqGhl++;
                } else {
                    clfwqQtqk++;
                }
            }
            statisMap.put("CLFWQ_QHL", clfwqQhl); //存量房网签取号量
            statisMap.put("CLFWQ_SLL", clfwqWcl); //存量房网签受理量
            statisMap.put("CLFWQ_GHL", clfwqGhl); //存量房网签过号量
            statisMap.put("CLFWQ_QTQK", clfwqQtqk); //存量房网签其他情况
        }

        /*获取当日批量受理业务受理情况数据*/
        List<Map<String, Object>> plslList = this.bdcCalByBusinessCode(today, "('O')");
        if (plslList != null && !plslList.isEmpty()) {
            int plslQhl = 0;
            int plslSll = 0;
            int plslGhl = 0;
            int plslQtqk = 0;
            int plslRev = 0;
            int plslRevDiffDate = 0;
            int plslPjslsc = 0;
            int plslExe = 0;
            int plslExeDiffDate = 0;
            int plslExePjbjsc = 0;
            for (Map<String, Object> plslMap : plslList) {
                plslQhl++;
                String callStatus = StringUtil.getValue(plslMap, "CALL_STATUS");
                String createTime = StringUtil.getValue(plslMap, "CREATE_TIME");
                String endTime = StringUtil.getValue(plslMap, "END_TIME");
                String revStarttime = StringUtil.getValue(plslMap, "REV_STARTTIME");
                String revEndtime = StringUtil.getValue(plslMap, "REV_ENDTIME");
                String exeId = StringUtil.getValue(plslMap, "EXE_ID");
                if (callStatus.equals("1")) {
                    plslSll++;
                    /*计算批量受理业务受理单件业务平均受理时长*/
                    if (!revStarttime.equals("") && !revEndtime.equals("") &&
                            StringUtil.compareDateF(revStarttime,revEndtime,"yyyy-MM-dd HH:mm:ss") == 1) {
                        plslRev++;
                        try{
                            plslRevDiffDate += this.compareDate(revStarttime, revEndtime, "0");
                        } catch (Exception e) {
                            log.info("获取当日批量受理业务受理情况数据日期格式转换错误,申报号为：" + exeId);
                        }
                    }
                    /*计算批量受理业务受理单件业务办结平均时长*/
                    if (!createTime.equals("") && !endTime.equals("")) {
                        plslExe++;
                        plslExeDiffDate += workdayService.getWorkDayCount(createTime, endTime);
                    }
                } else if (callStatus.equals("2")) {
                    plslGhl++;
                } else {
                    plslQtqk++;
                }
            }

            if (plslRev != 0) {
                plslPjslsc = plslRevDiffDate / plslRev;
            }
            if (plslExe != 0) {
                plslExePjbjsc = plslExeDiffDate / plslExe;
            }

            statisMap.put("PLSL_QHL", plslQhl); //批量受理取号量
            statisMap.put("PLSL_SLL", plslSll); //批量受理受理量
            statisMap.put("PLSL_GHL", plslGhl); //批量受理过号量
            statisMap.put("PLSL_QTQK", plslQtqk); //批量受理其他情况
            statisMap.put("PLSL_PJSLSC", plslPjslsc); //批量受理单件业务平均受理时长
            statisMap.put("PLSL_PJBJSC", plslExePjbjsc); //批量受理单件业务办结平均时长
        }

        dao.saveOrUpdateForAssignId(statisMap, "T_CKBS_BDC_CALLDATA", today);


    }

    /**
     * 描述:获取某一时间不动产某项业务的受理情况
     *
     * @author Madison You
     * @created 2020/4/7 10:55:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> bdcCalByBusinessCode(String today, String businessCode) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        params.add(today + " 00:00:00");
        params.add(today + " 23:59:59");
        sql.append(" select t.record_id,t.call_status,t.exe_id,x.create_time,x.end_time,x.rev_starttime,  ");
        sql.append(" x.rev_endtime from t_ckbs_queuerecord t left join jbpm6_execution x on t.exe_id = x.exe_id ");
        sql.append(" where 1 = 1 ");
        sql.append(" and t.create_time >= ? and t.create_time < ? and t.business_code in ").append(businessCode);
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:查询部门申请人数据
     *
     * @author Madison You
     * @created 2021/1/18 15:40:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getDepartSqrData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        List list = null;
        Map<String, Object> queryParams = filter.getQueryParams();
        String startTime = (String)queryParams.get("Q_T.CREATE_TIME_GE");
        String endTime = (String)queryParams.get("Q_T.CREATE_TIME_LE");
        String rowsnum = (String)queryParams.get("Q_T.ROWNUM_EQ");
        String department = (String)queryParams.get("Q_T.DEPART_NAME_EQ");
        String isExport = (String)queryParams.get("isExport");
        sql.append(" select * from ( select t.*,row_number() over(partition by t.depart_name order by t.depart_name) ");
        sql.append(" rn from ( select d.depart_name,a.exe_id,a.subject,a.sqrmc,a.sqrsjh from ( ");
        sql.append(" select exe_id,item_code,subject,sqrmc,sqrsjh,create_time from jbpm6_execution union ");
        sql.append(" select exe_id,item_code,subject,sqrmc,sqrsjh,create_time from jbpm6_execution_evehis ");
        sql.append(" ) a left join t_wsbs_serviceitem b on a.item_code = b.item_code ");
//        sql.append(" left join t_wsbs_serviceitem_catalog c on b.catalog_code = c.catalog_code ");
//        sql.append(" left join t_msjw_system_department d on c.depart_id = d.depart_id ");
        sql.append(" left join t_msjw_system_department d on b.impl_depart_id = d.depart_ID ");
        sql.append(" where a.sqrmc is not null and sqrsjh is not null  ");
        if (department != null && department.length() > 0) {
            sql.append(" and d.depart_name like '%").append(department).append("%'");
        }
        if (startTime != null && startTime.length() > 0) {
            sql.append(" and a.create_time >= '").append(startTime).append("'");
        }
        if (endTime != null && endTime.length() > 0) {
            sql.append(" and a.create_time <= '").append(endTime).append("'");
        }
        sql.append(" ) t  ) where 1 = 1 ");
        if (rowsnum != null && rowsnum.length() > 0) {
            sql.append(" and rn <= ").append(rowsnum);
        }
        if (isExport != null && Objects.equals(isExport, "1")) {
            list = dao.findBySql(sql.toString(), null, null);
        } else {
            list = dao.findBySql(sql.toString(), null, filter.getPagingBean());
        }
        return encryptionService.listDecrypt(list, "JBPM6_EXECUTION");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/23 9:30:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> mrsltjXsData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        List<Map<String, Object>> list = null;
        String startDate = filter.getRequest().getParameter("START_DATE");
        String startDateElse = filter.getRequest().getParameter("START_DATE_ELSE");
        String endDate = filter.getRequest().getParameter("END_DATE");
        String endDateElse = filter.getRequest().getParameter("END_DATE_ELSE");
        if (Objects.nonNull(startDateElse)) {
            startDate = startDateElse;
        }
        if (Objects.nonNull(endDateElse)) {
            endDate = endDateElse;
        }
        if (Objects.nonNull(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        if (Objects.nonNull(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        sql.append(" select B.DEPART_NAME,C.DEPART_NAME CHILD_DEPART_NAME,D.ITEM_NAME,A.ZCBJ,A.ZZBLZ,A.SHBTG,A.QZJS, ");
        sql.append(" NVL(A.GQL,0) GQL from ( SELECT X.IMPL_DEPART_ID,X.ZBCS_ID,X.ITEM_CODE,X.ZCBJ,X.ZZBLZ,X.SHBTG, ");
        sql.append(" X.QZJS,Y.GQL FROM (SELECT B.IMPL_DEPART_ID,B.ZBCS_ID, B.ITEM_CODE,COUNT(CASE WHEN A.RUN_STATUS = 2  ");
        sql.append(" THEN 'ZCBJ' END) ZCBJ,COUNT(CASE WHEN A.RUN_STATUS = 1 THEN 'ZZBLZ' END) ZZBLZ,COUNT(CASE WHEN ");
        sql.append(" A.RUN_STATUS IN (4,5,7) THEN 'SHBTG' END) SHBTG,COUNT(CASE WHEN A.RUN_STATUS = 6 THEN 'QZJS' END) QZJS ");
        sql.append(" FROM VIEW_JBPM6_EXECUTION_ALL A LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE WHERE 1 = 1 ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.CREATE_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.CREATE_TIME <= '").append(endDate).append("'");
        }
        sql.append(" AND A.SQFS IN (1,3) GROUP BY B.IMPL_DEPART_ID,B.ZBCS_ID,B.ITEM_CODE ORDER BY B.IMPL_DEPART_ID,B.ZBCS_ID ");
        sql.append(" ) X LEFT JOIN ( SELECT D.IMPL_DEPART_ID,D.ZBCS_ID,D.ITEM_CODE,COUNT(B.EXE_ID) GQL FROM JBPM6_HANGINFO A ");
        sql.append(" LEFT JOIN (SELECT EXE_ID,TASK_ID,CREATE_TIME FROM JBPM6_TASK ");
        sql.append(" ) B ON A.TASK_ID = B.TASK_ID LEFT JOIN JBPM6_EXECUTION C ON B.EXE_ID = C.EXE_ID ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM D ON C.ITEM_CODE = D.ITEM_CODE WHERE 1 = 1 ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.BEGIN_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.BEGIN_TIME <= '").append(endDate).append("'");
        }
        sql.append(" AND C.SQFS IN (1,3) ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND B.CREATE_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND B.CREATE_TIME <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY D.IMPL_DEPART_ID,D.ZBCS_ID,D.ITEM_CODE ) Y ON X.ITEM_CODE = Y.ITEM_CODE ) A LEFT JOIN ");
        sql.append(" T_MSJW_SYSTEM_DEPARTMENT B ON A.IMPL_DEPART_ID = B.DEPART_ID LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT C  ");
        sql.append(" ON A.ZBCS_ID = C.DEPART_ID LEFT JOIN T_WSBS_SERVICEITEM D ON A.ITEM_CODE = D.ITEM_CODE ");
        sql.append(" ORDER BY  B.DEPART_NAME,C.DEPART_NAME ");
        if (filter.getQueryParams().get("isExport") != null) {
            list = dao.findBySql(sql.toString(), null, null);
        } else {
            list = dao.findBySql(sql.toString(), null, filter.getPagingBean());
        }

        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/23 9:30:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> mrsltjXxData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        List<Map<String, Object>> list = null;
        String startDate = filter.getRequest().getParameter("START_DATE");
        String startDateElse = filter.getRequest().getParameter("START_DATE_ELSE");
        String endDate = filter.getRequest().getParameter("END_DATE");
        String endDateElse = filter.getRequest().getParameter("END_DATE_ELSE");
        if (Objects.nonNull(startDateElse)) {
            startDate = startDateElse;
        }
        if (Objects.nonNull(endDateElse)) {
            endDate = endDateElse;
        }
        if (Objects.nonNull(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        if (Objects.nonNull(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        sql.append(" SELECT X.CUR_WIN,O.BUSINESS_NAME,X.ZXS,X.LZS,X.GHS,X.QCSX,X.SLZL,X.ZCBJ,X.ZZBL,X.SHBTG, ");
        sql.append(" X.QZJS,NVL(Y.GQL,0) GQL,NVL(Z.WDY,0) WDY FROM ( SELECT A.CUR_WIN,A.BUSINESS_CODE, ");
        sql.append(" COUNT(CASE WHEN A.CALL_STATUS = 4 THEN 'ZXS' END) ZXS,COUNT(CASE WHEN A.CALL_STATUS = 3 THEN 'LZS' END) LZS, ");
        sql.append(" COUNT(CASE WHEN A.CALL_STATUS = 2 THEN 'GHS' END) GHS,COUNT(CASE WHEN A.CALL_STATUS = 7 THEN 'QCSX' END) QCSX, ");
        sql.append(" COUNT(CASE WHEN A.CALL_STATUS = 1 THEN 'SLZL' END) SLZL,COUNT(CASE WHEN A.EXE_ID IS NOT NULL AND B.RUN_STATUS ");
        sql.append(" = 2 THEN 'ZCBJ' END) ZCBJ,COUNT(CASE WHEN A.EXE_ID IS NOT NULL AND B.RUN_STATUS = 1 THEN 'ZZBL' END) ZZBL, ");
        sql.append(" COUNT(CASE WHEN A.EXE_ID IS NOT NULL AND B.RUN_STATUS IN (4,5,7) THEN 'SHBTG' END) SHBTG, ");
        sql.append(" COUNT(CASE WHEN A.EXE_ID IS NOT NULL AND B.RUN_STATUS = 6 THEN 'QZJS' END) QZJS FROM T_CKBS_QUEUERECORD A ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION B ON A.EXE_ID = B.EXE_ID LEFT JOIN T_WSBS_SERVICEITEM C ON B.ITEM_CODE = C.ITEM_CODE ");
        sql.append(" WHERE 1 = 1 ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.CREATE_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.CREATE_TIME <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY A.CUR_WIN,A.BUSINESS_CODE ) X LEFT JOIN ( SELECT C.CUR_WIN,C.BUSINESS_CODE,COUNT(A.EXE_ID) GQL FROM ");
        sql.append(" JBPM6_EXECUTION A LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE LEFT JOIN T_CKBS_QUEUERECORD C  ");
        sql.append(" ON A.EXE_ID = C.EXE_ID WHERE A.EXE_ID IN ( SELECT B.EXE_ID FROM JBPM6_HANGINFO A LEFT JOIN (SELECT EXE_ID, ");
        sql.append(" TASK_ID FROM JBPM6_TASK UNION SELECT EXE_ID,TASK_ID FROM JBPM6_TASK_EVEHIS) B ON A.TASK_ID = B.TASK_ID WHERE 1 = 1 ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.BEGIN_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.BEGIN_TIME <= '").append(endDate).append("'");
        }
        sql.append(" ） ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.CREATE_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.CREATE_TIME <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY C.CUR_WIN,C.BUSINESS_CODE ) Y ON X.CUR_WIN = Y.CUR_WIN AND X.BUSINESS_CODE = Y.BUSINESS_CODE LEFT JOIN ");
        sql.append(" ( SELECT C.CUR_WIN,C.BUSINESS_CODE,COUNT(A.EXE_ID) WDY FROM JBPM6_EXECUTION A LEFT JOIN T_WSBS_SERVICEITEM B ");
        sql.append(" ON A.ITEM_CODE = B.ITEM_CODE LEFT JOIN T_CKBS_QUEUERECORD C ON A.EXE_ID = C.EXE_ID WHERE A.EXE_ID IN ( ");
        sql.append(" SELECT EXE_ID FROM JBPM6_EXECUTION A LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" LEFT JOIN T_WSBS_PRINTATTACH C ON A.EXE_ID = C.FLOW_EXEID WHERE A.RUN_STATUS = 4 AND C.FILE_ID IS NULL ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.CREATE_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.CREATE_TIME <= '").append(endDate).append("'");
        }
        sql.append(" ) ");
        if (Objects.nonNull(startDate)) {
            sql.append(" AND A.CREATE_TIME >= '").append(startDate).append("'");
        }
        if (Objects.nonNull(endDate)) {
            sql.append(" AND A.CREATE_TIME <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY C.CUR_WIN,C.BUSINESS_CODE ) Z ON X.CUR_WIN = Z.CUR_WIN AND X.BUSINESS_CODE = Z.BUSINESS_CODE ");
        sql.append(" LEFT JOIN T_CKBS_BUSINESSDATA O ON X.BUSINESS_CODE = O.BUSINESS_CODE ORDER BY CUR_WIN,BUSINESS_NAME ");
        if (filter.getQueryParams().get("isExport") != null) {
            list = dao.findBySql(sql.toString(), null, null);
        } else {
            list = dao.findBySql(sql.toString(), null, filter.getPagingBean());
        }
        return list;
    }

    /**
     * 描述:初始化，全部值置为0或空字符串
     *
     * @author Madison You
     * @created 2020/4/7 14:21:00
     * @param
     * @return
     */
    private void bdcCallDataCalInit(Map<String, Object> statisMap) {
        statisMap.put("ZSLL", 0); //总受理量
        statisMap.put("ZQHL", 0); //总取号量
        statisMap.put("BDCDJYWSL_QHL", 0); //不动产登记业务受理取号量
        statisMap.put("BDCDJYWSL_SLL", 0); //不动产登记业务受理受理量
        statisMap.put("BDCDJYWSL_GHL", 0); //不动产登记业务受理过号量
        statisMap.put("BDCDJYWSL_QTQK", 0); //不动产登记业务受理其他情况
        statisMap.put("BDCDJYWSL_PJSLSC", ""); //不动产登记业务受理单件业务平均受理时长
        statisMap.put("BDCDJYWSL_PJBJSC", ""); //不动产登记业务受理单件业务办结平均时长
        statisMap.put("JFLZ_QHL", 0); //缴费领证取号量
        statisMap.put("JFLZ_WCL", 0); //缴费领证完成量
        statisMap.put("JFLZ_GHL", 0); //缴费领证过号量
        statisMap.put("JFLZ_QTQK", 0); //缴费领证其他情况
        statisMap.put("WYWXZJ_QHL", 0); //物业维修资金取号量
        statisMap.put("WYWXZJ_WCL", 0); //物业维修资金完成量
        statisMap.put("WYWXZJ_GHL", 0); //物业维修资金过号量
        statisMap.put("WYWXZJ_QTQK", 0); //物业维修资金其他情况
        statisMap.put("CLFWQ_QHL", 0); //存量房网签取号量
        statisMap.put("CLFWQ_SLL", 0); //存量房网签受理量
        statisMap.put("CLFWQ_GHL", 0); //存量房网签过号量
        statisMap.put("CLFWQ_QTQK", 0); //存量房网签其他情况
        statisMap.put("PLSL_QHL", 0); //批量受理取号量
        statisMap.put("PLSL_SLL", 0); //批量受理受理量
        statisMap.put("PLSL_GHL", 0); //批量受理过号量
        statisMap.put("PLSL_QTQK", 0); //批量受理其他情况
        statisMap.put("PLSL_PJSLSC", ""); //批量受理单件业务平均受理时长
        statisMap.put("PLSL_PJBJSC", ""); //批量受理单件业务办结平均时长
    }

    /**
     * 描述:比较两时间相差多少(0:分钟)
     *
     * @author Madison You
     * @created 2020/4/7 14:58:00
     * @param
     * @return
     */
    private int compareDate(String startTime, String endTime, String type) throws ParseException {
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        long end = endDate.getTime();
        long start = startDate.getTime();
        long diff = end - start;
        long diffDate = 0;
        int t = 0;
        if (type.equals("0")) {
            diffDate = diff / (1000 * 60);
        }
        return Integer.parseInt(String.valueOf(diffDate));
    }

}
