/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.jkzx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.jkzx.dao.JkzxDao;
import net.evecom.platform.jkzx.service.JkzxService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 监控中心操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2017年3月15日 09:26:34
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("jkzxService")
public class JkzxServiceImpl extends BaseServiceImpl implements JkzxService {
    /**
     * 所引入的dao
     */
    @Resource
    private JkzxDao dao;
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
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public List<Map<String, Object>> findBySqlBmbjltj(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.DEPART_NAME, COUNT(*) as NUM,t.tree_sn as SORTCODE from VIEW_JKZX_DEPART_ITEM t  ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " GROUP BY T.depart_name,t.tree_sn  order by t.tree_sn ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlDepartItem(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select a.DEPART_NAME,SUM(a.ZMQ) as zmq,SUM(a.FZMQ) as fzmq, ");
        sql.append(" SUM(a.ZMQORFZMQ) as ZMQORFZMQ,a.SORTCODE from ");
        sql.append(" (select  d.depart_name, count(*) as zmq,0 as fzmq,0 as ZMQORFZMQ ,");
        sql.append(" d.tree_sn SORTCODE from t_wsbs_serviceitem  s, ");
        sql.append(" t_wsbs_serviceitem_catalog c,  t_msjw_system_department   d ");
        sql.append(" where s.catalog_code = c.catalog_code ");
        sql.append(" and c.depart_id = d.depart_id  and s.fwsxzt = 1 and s.fta_flag = 1");
        sql.append(" group by d.depart_name,d.tree_sn");

        sql.append(" union all");
        sql.append(" select  d.depart_name,0 as zmq, count(*) as fzmq,0 as ZMQORFZMQ,");
        sql.append("  d.tree_sn SORTCODE from t_wsbs_serviceitem  s,");
        sql.append("  t_wsbs_serviceitem_catalog c,  t_msjw_system_department   d");
        sql.append(" where s.catalog_code = c.catalog_code ");
        sql.append(" and c.depart_id = d.depart_id  and s.fwsxzt = 1 ");
        sql.append(" and (s.fta_flag = 2 or s.fta_flag is null) ");
        sql.append(" group by d.depart_name,d.tree_sn");

        sql.append(" union all");
        sql.append(" select  d.depart_name,0 as zmq, 0 as fzmq,count(*) as ZMQORFZMQ,");
        sql.append("  d.tree_sn SORTCODE from t_wsbs_serviceitem  s,");
        sql.append("  t_wsbs_serviceitem_catalog c,  t_msjw_system_department   d");
        sql.append(" where s.catalog_code = c.catalog_code and ");
        sql.append(" c.depart_id = d.depart_id  and s.fwsxzt = 1 and s.fta_flag = 3 ");
        sql.append(" group by d.depart_name,d.tree_sn) a");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "   group by a.depart_name,a.SORTCODE order by a.SORTCODE ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlSsbjxx(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select d.depart_name,  e.item_name,t.LINE_NAME as SQR, t.cur_win as SLCK, ");
        sql.append(" t.oper_time as HANDLE_TIME, t.exe_id, e.run_status ");
        sql.append(" from T_CKBS_NUMRECORD t  join JBPM6_EXECUTION e on t.exe_id = e.exe_id ");
        sql.append(" join t_wsbs_serviceitem s on e.item_code = s.item_code");
        sql.append(" join t_wsbs_serviceitem_catalog c on s.catalog_id = c.catalog_id");
        sql.append(" join t_msjw_system_department d on c.depart_id = d.depart_id");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " order by t.oper_time desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), new PagingBean(1, 150));
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlCkjhxx(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select t.LINE_NAME as JBR, t.create_time as TAKE_TIME," +
                "min(c.create_time) as FIRST_CALL_TIME,count(*) as CALL_NUM, ");
        sql.append(" t.oper_time as HANDLE_TIME, t.exe_id ");
        sql.append(" from T_CKBS_NUMRECORD t  left join T_CKBS_CALLWAIT c on t.record_id = c.record_id  ");
        sql.append(" where c.type = 'callNum' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " group by  t.record_id,t.LINE_NAME,t.oper_time,t.create_time,t.exe_id order by t.create_time desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), new PagingBean(1, 150));
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlCkjhl(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.cur_win, count(t.cur_win) as NUM, t.cur_win as SORTCODE  ");
        sql.append("  from T_CKBS_NUMRECORD t where t.cur_win is not null");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " group by t.cur_win order by to_number(regexp_substr(t.cur_win, '[0-9]*[0-9]', 1)) asc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlBmpjmydtj(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub

        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from VIEW_BMPJMYD t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        // exeSql += " GROUP BY T.depart_name,t.tree_sn  order by t.tree_sn ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlCkpjmydtj(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub

        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from VIEW_CKPJMYD t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        // exeSql += " GROUP BY T.depart_name,t.tree_sn  order by t.tree_sn ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findByEffiItemSqlFilter(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.ITEM_NAME,");
        sql.append(" count(t.ITEM_NAME) as COUNTS,");
        sql.append(" sum(t.effi_desc_1) as EFFI_DESC_ONE,");
        sql.append(" sum(t.effi_desc_2) as EFFI_DESC_TWO,");
        sql.append(" sum(t.effi_desc_3) as EFFI_DESC_THREE,");
        sql.append(" sum(t.effi_desc_4) as EFFI_DESC_FOUR");
        sql.append(" from VIEW_EFFI_ITEM t where 1=1 ");

        if (sqlFilter.getQueryParams().get("Q_T.DEPART_ID_=") != null
                && !sqlFilter.getQueryParams().get("Q_T.DEPART_ID_=").equals("")) {
            String departId = sqlFilter.getQueryParams().get("Q_T.DEPART_ID_=").toString();
            sql.append(" and (T.DEPART_ID = '").append(departId);
            sql.append("' or T.CHILD_DEPART_ID = '").append(departId).append("') ");
            sqlFilter.removeFilter("Q_T.DEPART_ID_=");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  group by t.item_name order by  COUNTS desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        for (Map<String, Object> map : list) {
            double couns = Integer.parseInt(map.get("COUNTS").toString());// 总数
            double EFFI_DESC_1 = Integer.parseInt(map.get("EFFI_DESC_ONE").toString());// 提前办结数
            double EFFI_DESC_3 = Integer.parseInt(map.get("EFFI_DESC_THREE").toString());// 超期办结数
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
    public List<Map<String, Object>> getYqbjmxStatist(SqlFilter filter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.depart_name,e.exe_id, ");
        sql.append("e.subject,s.item_name,sxlx.dic_name sxlx, ");
        sql.append("s.cnqxgzr,e.create_time,e.end_time,e.creator_name,e.creator_phone from JBPM6_EFFICINFO t ");
        sql.append("left join jbpm6_execution e on e.exe_id = t.eflow_exeid ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append("left join t_wsbs_serviceitem_catalog c on c.catalog_code = s.catalog_code ");
        sql.append("left join t_msjw_system_department d on d.depart_id = c.child_depart_id ");
        sql.append(" join (select sd.dic_code,sd.dic_name from t_msjw_system_dictionary sd where sd.type_id=");
        sql.append("(select type_id from t_msjw_system_dictype where type_code='ServiceItemType'))sxlx ");
        sql.append("on sxlx.dic_code = s.sxlx ");
        sql.append("where t.effi_desc=3 ");
        if (filter.getQueryParams().get("Q_T.DEPART_ID_=") != null
                && !filter.getQueryParams().get("Q_T.DEPART_ID_=").equals("")) {
            String departId = filter.getQueryParams().get("Q_T.DEPART_ID_=").toString();
            sql.append(" and (c.child_depart_id = '").append(departId);
            sql.append("' or c.depart_id = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_=");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), new PagingBean(1, 150));
        
        for (Map<String, Object> map : list) {
            //获取任务截止时间  
            String createTime = (String) map.get("CREATE_TIME");
            if(StringUtils.isNotEmpty(createTime)){
                String cnqxgzr = map.get("CNQXGZR")==null?"":map.get("CNQXGZR").toString();
                int cnqxgzrint = 0;
                if (StringUtils.isNotEmpty(cnqxgzr)) {
                    cnqxgzrint = Integer.parseInt(cnqxgzr);
                }
                Date createTimeDate = DateTimeUtil.getDateOfStr(createTime, "yyyy-MM-dd HH:mm");
                String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");
                if (cnqxgzrint>0) {
                    deadLineDate = workdayService.getDeadLineDate(deadLineDate, cnqxgzrint);
                    deadLineDate += " 00:00:00";
                }
                //获取办结时间
                String endTime = (String) map.get("END_TIME");
                //获取流程ID
                String taskId = (String) map.get("TASK_ID");
                if(StringUtils.isNotEmpty(deadLineDate) && StringUtils.isNotEmpty(endTime)){
                    Date endTimedDate = DateTimeUtil.getDateOfStr(endTime, "yyyy-MM-dd HH:mm");
                    int overdueWorkDay = flowTaskService.getOverdueWorkDay(deadLineDate,taskId,endTimedDate);
                    map.put("OVERDUE_WORKDAY", overdueWorkDay);
                }else{
                    map.put("OVERDUE_WORKDAY", -2);
                }
            }
        }
        
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySqlBjlx(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer totalSql = new StringBuffer("SELECT SUM(T.SJ_XJ) SJ_XJ,SUM(T.SJ_JBJXJ) SJ_JBJXJ,");
        totalSql.append("SUM(T.SJ_PTJXJ) SJ_PTJXJ,SUM(T.SJ_TSJXJ) SJ_TSJXJ FROM JBPM6_STATIST T");
        String realTotalSql = dao.getQuerySql(sqlFilter, totalSql.toString(), params);
        List<Map<String, Object>> totalList = dao.findBySql(realTotalSql, params.toArray(), null);
        return totalList;
    }

    @Override
    public List<Map<String, Object>> findBySqlLetter(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer( "select t.* from T_HD_LETTER t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += "  order by t.create_time desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), new PagingBean(1, 150));
        return list;
    }
}
