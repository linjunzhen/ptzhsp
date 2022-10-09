/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.zzhy.dao.StatistCommercialDao;
import net.evecom.platform.zzhy.model.statistics.CompanyInfoFactory;
import net.evecom.platform.zzhy.model.statistics.CompanyInfoTemplate;
import net.evecom.platform.zzhy.service.StatistCommercialService;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述 商事登记统计分析操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("statistCommercialService")
public class StatistCommercialServiceImpl extends BaseServiceImpl implements StatistCommercialService {
    /**
     * 所引入的dao
     */
    @Resource
    private StatistCommercialDao dao;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
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
     * dictionaryService
     */
    @Autowired
    private DictionaryService dictionaryService;
    /**
     * 覆盖获取实体dao方法
     * 描述
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, Map<String, Object> variables) {
        List<Object> params = new ArrayList<Object>();
        //获取当前年月
        Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String thisMonth = sdf.format(d);
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_STATIST T ");
        sql.append(" where t.month = ?");
        params.add(thisMonth);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 统计商事新增企业数量
     * 
     * @author Water Guo
     * @created 2016年12月20日 上午10:19:14
     * @param params
     */
    public void statistNewEnterpriseNum(Log log){
        //定时器一天执行一次
        //获取当前年月
        log.info("开始调用【商事统计】定时任务，解析处理办件数据....");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String thisMonth = sdf.format(d);
//        String thisMonth = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSDS_thisMonth");
        //获取上一月份
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        String lastMonth = new SimpleDateFormat("yyyy-MM").format(c.getTime());
//        String lastMonth = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "SSDS_lastMonth");
        //统计内资
        statistDomestic(thisMonth, lastMonth,"0");
        //统计外资
        statistDomestic(thisMonth, lastMonth,"1");
        //统计个体
        statistGT(thisMonth, lastMonth);
        statistGD(thisMonth, lastMonth);
        statistTotal(thisMonth);
        log.info("结束调用【商事统计】定时任务，解析处理办件数据....");
    }

    private void statistTotal(String thisMonth) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> thisMonthList = dao.getThisMonth(thisMonth);
        List<Map<String, Object>> thisMonthTotalList = dao.getThisMonthTotal(thisMonth);
        int totalMonthNum = 0;
        int totalLastMonthNum = 0;
        double totalInvestment = 0.0;
        if (thisMonthList != null && !thisMonthList.isEmpty() && thisMonthList.size() > 0) {
            for (Map<String, Object> thisMonthInfo : thisMonthList) {
                totalMonthNum += Integer.parseInt(thisMonthInfo.get("MONTH_NUM").toString());
                totalLastMonthNum += Integer.parseInt(thisMonthInfo.get("LAST_MONTH_NUM").toString());
                totalInvestment += Double.parseDouble(thisMonthInfo.get("TOTAL_INVESTMENT").toString());
                }
            }
        //int addNum = ((totalMonthNum-totalLastMonthNum)>0?(totalMonthNum-totalLastMonthNum):0);
        int addNum = totalMonthNum-totalLastMonthNum;
        String addRatio = getPercent(addNum,totalLastMonthNum);
        if (thisMonthTotalList.size()==0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("STATIST_TYPE", "5");
            map.put("MONTH_NUM", totalMonthNum);
            map.put("ADD_NUM", addNum);
            map.put("ADD_RATIO", addRatio);
            map.put("LAST_MONTH_NUM", totalLastMonthNum);
            map.put("MONTH", thisMonth);
            map.put("TOTAL_INVESTMENT", totalInvestment+"");
            dao.saveOrUpdate(map, "T_COMMERCIAL_STATIST", null);
        }else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("STATIST_TYPE", "5");
            map.put("MONTH_NUM", totalMonthNum);
            map.put("ADD_NUM", addNum);
            map.put("ADD_RATIO", addRatio);
            map.put("LAST_MONTH_NUM", totalLastMonthNum);
            map.put("MONTH", thisMonth);
            map.put("TOTAL_INVESTMENT", totalInvestment+"");
            dao.saveOrUpdate(map, "T_COMMERCIAL_STATIST",
                    thisMonthTotalList.get(0).get("STATIST_ID").toString());
        }
    }

    private void statistGD(String thisMonth, String lastMonth) {
        // TODO Auto-generated method stub
        //获取单天所需要入库数量
        List<Map<String, Object>> toDayList = dao.getGDToDayNum(thisMonth);
        int toDayNum = toDayList.size();
        String stype = "4";
        List<Map<String, Object>> thisMonthList = dao.getToTalNum(thisMonth,stype);
        //将已统计数据存入SYNC表
        double totalInvestment = 0.0;
        if (toDayList != null && !toDayList.isEmpty() && toDayList.size() > 0) {
            for (Map<String, Object> toDayInfo : toDayList) {
                Map<String, Object> map = new HashMap<String, Object>();
                double investment = toDayInfo.get("INVESTMENT")==null?
                        0.0:Double.parseDouble(toDayInfo.get("INVESTMENT").toString());
                totalInvestment += investment ;
                map.put("SOLELY_ID", toDayInfo.get("SOLELY_ID"));
                dao.saveOrUpdate(map, "T_COMMERCIAL_SOLELYINVESTSYNC", null);
            }
        }
        String totalInvestmentsString = totalInvestment+"";
        changeCommercialStatist(thisMonth, lastMonth, toDayNum, stype, thisMonthList, totalInvestmentsString);
        
    }

    private void statistGT(String thisMonth, String lastMonth) {
        // TODO Auto-generated method stub
        //获取单天所需要入库数量
        List<Map<String, Object>> toDayList = dao.getGTToDayNum(thisMonth);
        int toDayNum = toDayList.size();
        String stype = "3";
        List<Map<String, Object>> thisMonthList = dao.getToTalNum(thisMonth,stype);
        //将已统计数据存入SYNC表
        double totalInvestment = 0.0;
        if (toDayList != null && !toDayList.isEmpty() && toDayList.size() > 0) {
            for (Map<String, Object> toDayInfo : toDayList) {
                Map<String, Object> map = new HashMap<String, Object>();
                double investment = toDayInfo.get("CAPITAL_AMOUNT")==null?
                        0.0:Double.parseDouble(toDayInfo.get("CAPITAL_AMOUNT").toString());
                totalInvestment += investment ;
                map.put("INDIVIDUAL_ID", toDayInfo.get("INDIVIDUAL_ID"));
                dao.saveOrUpdate(map, "T_COMMERCIAL_INDIVIDUALSYNC", null);
            }
        }
        String totalInvestmentsString = totalInvestment+"";
        changeCommercialStatist(thisMonth, lastMonth, toDayNum, stype, thisMonthList, totalInvestmentsString);
        
    }

    private void statistDomestic(String thisMonth, String lastMonth, String type) {
        //获取单天所需要入库数量
        List<Map<String, Object>> toDayList = dao.getToDayNum(type,thisMonth);
        int toDayNum = toDayList.size();
        //获取昨日累计数量
        String stype = (Integer.parseInt(type)+1)+"";
        List<Map<String, Object>> thisMonthList = dao.getToTalNum(thisMonth,stype);
        //将已统计数据存入SYNC表
        double totalInvestment = 0.0;
        if (toDayList != null && !toDayList.isEmpty() && toDayList.size() > 0) {
            for (Map<String, Object> toDayInfo : toDayList) {
                Map<String, Object> map = new HashMap<String, Object>();
                double investment = toDayInfo.get("INVESTMENT")==null?
                        0.0:Double.parseDouble(toDayInfo.get("INVESTMENT").toString());
                totalInvestment += investment ;
                map.put("COMPANY_ID", toDayInfo.get("COMPANY_ID"));
                dao.saveOrUpdate(map, "T_COMMERCIAL_COMPANYSYNC", null);
            }
        }
        String totalInvestmentsString = totalInvestment+"";
        changeCommercialStatist(thisMonth, lastMonth, toDayNum, stype, thisMonthList, totalInvestmentsString);
    }

    private void changeCommercialStatist(String thisMonth, String lastMonth, int toDayNum, String stype,
            List<Map<String, Object>> thisMonthList, String totalInvestmentsString) {
        if (thisMonthList.size()==0) {
            //新建记录
            List<Map<String, Object>> lastMonthList = dao.getToTalNum(lastMonth,stype);
            Map<String, Object> lastMonthMap = new HashMap<String, Object>();
            int lastMonthNum = 0;
            String lastMonthNumString = "0";
            int addNum = 0;
            String addRatio = "0.0%";
            if (lastMonthList.size()==0) {
                addNum = ((toDayNum-0)>0?(toDayNum-0):0);
            }else {
                lastMonthMap = lastMonthList.get(0);
                lastMonthNum = Integer.parseInt(lastMonthMap.get("MONTH_NUM").toString());
                lastMonthNumString = lastMonthMap.get("MONTH_NUM").toString();
                addNum = ((toDayNum-lastMonthNum)>0?(toDayNum-lastMonthNum):0);
                addRatio = getPercent(addNum,lastMonthNum);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("STATIST_TYPE", stype);
            map.put("MONTH_NUM", toDayNum);
            map.put("ADD_NUM", addNum);
            map.put("ADD_RATIO", addRatio);
            map.put("LAST_MONTH_NUM", lastMonthNumString);
            map.put("MONTH", thisMonth);
            map.put("TOTAL_INVESTMENT", totalInvestmentsString);
            dao.saveOrUpdate(map, "T_COMMERCIAL_STATIST", null);
        }else {
          //更新T_COMMERCIAL_STATIST
            Map<String, Object> thisMonthMap;
            thisMonthMap = thisMonthList.get(0);
            int monthNum = Integer.parseInt(thisMonthMap.get("MONTH_NUM").toString());
            double total = Double.parseDouble(totalInvestmentsString);
            total += Double.parseDouble(thisMonthMap.get("TOTAL_INVESTMENT").toString());
            monthNum = monthNum + toDayNum;
            int lastMonthNum = Integer.parseInt(thisMonthMap.get("LAST_MONTH_NUM").toString());
            int addNum = monthNum-lastMonthNum;
            String addRatio = getPercent(addNum,lastMonthNum);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("MONTH_NUM", monthNum);
            map.put("ADD_NUM", addNum);
            map.put("ADD_RATIO", addRatio);
            map.put("TOTAL_INVESTMENT", total+"");
            dao.saveOrUpdate(map, "T_COMMERCIAL_STATIST", thisMonthMap.get("STATIST_ID").toString());
        }
    }
    public String getPercent(Integer num,Integer totalPeople ){
        String percent ;
        Double p3 = 0.0;
        if(totalPeople == 0){
            p3 = num*1.0/1;
        }else{
            p3 = num*1.0/totalPeople;
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
        percent = nf.format(p3);
        return percent;
    }

    @Override
    public List<Map<String, Object>> getTotalInvestmentBySqlFilter(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        //获取当前年月
        Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm");
        String thisMonth = sdf.format(d);
        //获取去年一月份
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        String beginMonth = new SimpleDateFormat("yyyy").format(c.getTime());
        beginMonth = beginMonth+"-01";
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_STATIST T ");
        sql.append(" where t.month >= ? and t.month <= ? and t.STATIST_TYPE = 5");
        params.add(beginMonth);
        params.add(thisMonth);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    
    }
    @Override
    public List<Map<String, Object>> getIndPerDataStatist(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        String sqlString = "select us.FULLNAME,t.operator_id,"
                + "sum(case when t.run_status != 0 then 1 else 0 end)as COUNTS,"
                + "sum(case when t.run_status in (2, 3, 4) then 1 else 0 end)as BANJS,"
                + "sum(case when t.run_status in (5) then 1 else 0 end)as TJS,"
                + "sum(case when t.run_status in (1) then 1 else 0 end)as ZBS "
                + "from JBPM6_EXECUTION t "
                + "left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code "
                + "left join T_MSJW_SYSTEM_SYSUSER us on us.USER_ID = t.operator_id "
                + "left join T_WSBS_SERVICEITEM_CATALOG sc on sc.catalog_code = ws.catalog_code "
                + "where sc.CHILD_DEPART_ID = '40288b9f54bf0b4d0154bf2981820056' " 
                + "and ws.ITEM_CODE IN ( select dic.dic_code from " +
                "T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') "
                + "and t.operator_id is not null";

        String exeSql = dao.getQuerySql(filter, sqlString, params);
        exeSql = exeSql + " group by us.FULLNAME,t.operator_id order by COUNTS desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }
    
    public List<Map<String, Object>> getAdnormalStatist(SqlFilter filter) {
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
        //限制统计四个事项
        sql.append(" and s.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='zzhyCode') ");
        //商事登记处
            String departId = "40288b9f54bf0b4d0154bf2981820056";
            sql.append(" and (s.zbcs_id = '").append(departId);
            sql.append("' or s.impl_depart_id = '").append(departId).append("') ");
            filter.removeFilter("Q_T.DEPART_ID_=");
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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        return dao.findBySqlFilter(sqlFilter);
    }
    /**
     * 企业办件信息
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findInfoBySqlFilter(SqlFilter sqlFilter) {
        //查找企业办件
        List<Map<String, Object>> list = findCompanyInfo(sqlFilter);
        //获取各个办件业务的详细信息
        for (Map<String, Object> busRecord : list) {
            String exeId = StringUtil.getString(busRecord.get("EXE_ID"));
            String busTableName = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
            //获取业务类
            CompanyInfoTemplate companyInfoTemplate = CompanyInfoFactory.createCompanyInfoTemplate(busTableName);
            if (Objects.nonNull(companyInfoTemplate)) {
                //办件详细信息
                Map<String, Object> companyInfo = companyInfoTemplate.getInfoByExeId(exeId);
                //是否长期
                String isLongTerm = StringUtil.getString(busRecord.get("ISLONG_TERM"));
                if (Objects.equals("1", isLongTerm)) {
                    companyInfo.put("CLOSE_TIME", "长期");
                } else {
                    companyInfo.put("CLOSE_TIME", busRecord.get("CLOSE_TIME"));
                }
                busRecord.putAll(companyInfo);
            }
        }
        return list;
    }
    /**
     * 企业办件信息导出
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findInfoBySqlFilterForExp(SqlFilter sqlFilter) {
        //查找企业办件
        List<Map<String, Object>> list = findCompanyInfo(sqlFilter);
        List<Map<String, Object>> results=Lists.newArrayList();
        //获取各个办件业务的详细信息
        for (Map<String, Object> busRecord : list) {
            String exeId = StringUtil.getString(busRecord.get("EXE_ID"));
            String busTableName = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
            //获取业务类
            CompanyInfoTemplate companyInfoTemplate = CompanyInfoFactory.createCompanyInfoTemplate(busTableName);
            if (Objects.nonNull(companyInfoTemplate)) {
                //办件详细信息
                Map<String,Object> newCompanyInfo=new LinkedHashMap<>();
                Map<String, Object> companyInfo = companyInfoTemplate.getInfoByExeId(exeId);
                newCompanyInfo.put("exeId",exeId);
                newCompanyInfo.put("XKFILE_NUM",busRecord.get("XKFILE_NUM"));
                newCompanyInfo.put("COMPANY_NAME",companyInfo.get("COMPANY_NAME"));
                newCompanyInfo.put("LEGAL_NAME",companyInfo.get("LEGAL_NAME"));
                newCompanyInfo.put("INVESTMENT",companyInfo.get("INVESTMENT"));
                newCompanyInfo.put("CONTACT_PHONE",companyInfo.get("CONTACT_PHONE"));
                newCompanyInfo.put("BUSSINESS_SCOPE",companyInfo.get("BUSSINESS_SCOPE"));
                //newCompanyInfo.put("CREATE_TIME",companyInfo.get("CREATE_TIME"));
                newCompanyInfo.put("EFFECT_TIME", busRecord.get("EFFECT_TIME"));
                //是否长期
                String isLongTerm = StringUtil.getString(busRecord.get("ISLONG_TERM"));
                if (Objects.equals("1", isLongTerm)) {
                    newCompanyInfo.put("CLOSE_TIME", "长期");
                } else {
                    newCompanyInfo.put("CLOSE_TIME", busRecord.get("CLOSE_TIME"));
                }
                newCompanyInfo.put("REGISTER_ADDR", companyInfo.get("REGISTER_ADDR"));
//                String isMp=dictionaryService.findByDicCodeAndTypeCode
//                        (StringUtil.getString(busRecord.get("SSSBLX")), "YesOrNo");
//                newCompanyInfo.put("SSSBLX",  isMp);
                results.add(newCompanyInfo);
            }
        }
        return results;
    }
    /**
     * 查找企业办件信息
     * @param sqlFilter
     * @return
     */
    private List<Map<String,Object>>  findCompanyInfo(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        String isHistory=StringUtil.getString(sqlFilter.getRequest().getParameter("isHistory"));
        sql.append(" SELECT T.EXE_ID ,T.BUS_TABLENAME,SSSBLX ");
        sql.append(",EFFECT_TIME,CLOSE_TIME,XKFILE_NUM,ISLONG_TERM ");
        if(Objects.equals("1",isHistory)){
            sql.append(" FROM VIEW_COMPANY_INFO_ALL T    ");
        }else{
            sql.append(" FROM VIEW_COMPANY_INFO T   ");
        }
        sql.append(" left join jbpm6_flow_result R  ON T.EXE_ID=R.EXE_ID ");
        //获取各个办件的企业信息
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<Map<String, Object>> countsDetailData(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        return dao.countsDetailData(sqlFilter);
    }

    @Override
    public List<Map<String, Object>> banJSDetailData(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        return dao.banJSDetailData(sqlFilter);
    }

    @Override
    public List<Map<String, Object>> zBSDetailData(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        return dao.zBSDetailData(sqlFilter);
    }

    @Override
    public List<Map<String, Object>> tJSDetailData(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        return dao.tJSDetailData(sqlFilter);
    }

    @Override
    public List<Map<String, Object>> getUserTaskBySqlFilter(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.assigner_name,count(*) counts from JBPM6_TASK t ");
        sql.append(" join JBPM6_FLOWDEF f on t.def_id = f.def_id ");
        sql.append(" where f.def_key in ('ss001', 'ss002', 'ss003', 'ss004') ");
        sql.append(" and t.is_real_handled = 1  and t.assigner_type = 1 ");
        sql.append(" and t.is_real_handled = 1  and t.assigner_type = 1 ");
        sql.append(" and t.handle_opinion is not null ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += " group by t.assigner_name order by counts desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }

}
