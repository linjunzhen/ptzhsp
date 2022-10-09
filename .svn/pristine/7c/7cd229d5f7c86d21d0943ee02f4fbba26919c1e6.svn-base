/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcExecutionDao;
import net.evecom.platform.bdc.service.BdcExecutionService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.dao.SysUserDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.WorkdayService;

import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 不动产登记操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("bdcExecutionService")
public class BdcExecutionServiceImpl extends BaseServiceImpl implements BdcExecutionService {
    /**
     * 所引入的dao
     */
    @Resource
    private BdcExecutionDao dao;
    

    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    
    /**
     * 所引入的dao
     */
    @Resource
    private SysUserDao sysUserDao;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * flowTaskService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * flowTaskService
     */
    @Resource
    private PrintAttachService printAttachService;
    /**
     * flowTaskService
     */
    @Resource
    private BdcSpbPrintConfigService spbPrintConfigService;
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
    public List<Map<String, Object>> findBdcNeedMeHandle(SqlFilter sqlFilter, boolean isHaveHandUp) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,E.EXE_ID,E.APPLY_STATUS,T.FROMTASK_IDS,");
        sql.append("E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,B.BJXX_ID,T.TASK_DEADTIME,T.TASK_STATUS,");
        sql.append("E.JBR_NAME,E.SQRMC,T.CREATE_TIME AS TASK_CTIME,T.END_TIME ");
        sql.append(" FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON T.EXE_ID=E.EXE_ID");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON T.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE T.IS_AUDITED=1  AND T.ASSIGNER_TYPE=1 ");
        sql.append(" and E.ITEM_CODE IN ( select dic.dic_code from ")
        .append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        if(isHaveHandUp){
            sql.append(" AND T.TASK_STATUS IN ('-1','1') ");
        }else{
            sql.append(" AND T.TASK_STATUS=1 ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        String gsspBeginTime = "";
        for (Map<String, Object> map : list) {
            // 获取来源任务ID
            String fromTaskIds = (String) map.get("FROMTASK_IDS");
            //获取任务截止时间
            String taskDeadTime = (String) map.get("TASK_DEADTIME");
            //获取流程ID
            String taskId = (String) map.get("TASK_ID");
            //结束时间
            String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");  
       
            gsspBeginTime = (String) map.get("TASK_CTIME"); 
            map.put("isYs", true);
            if(StringUtils.isNotEmpty(taskDeadTime)){
                int leftWorkDay = this.getLeftWorkDay(taskDeadTime,taskId);
                map.put("LEFT_WORKDAY", leftWorkDay);
            }else{
                map.put("LEFT_WORKDAY", -2);
            }
            if(StringUtils.isNotEmpty(gsspBeginTime)){
                map.put("WORK_HOURS",DateTimeUtil.getWorkTime(gsspBeginTime, endTime));                
            }
       
            
            if (StringUtils.isNotEmpty(fromTaskIds)) {
                Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { fromTaskIds.split(",")[0] });
                // 获取退回意见
                String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                map.put("BACKOPINION", BACKOPINION);
            }
        }
        return list;
    }
    
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年4月20日 上午11:44:02
     * @param exeId
     * @param taskNodeName
     * @return
     */
    public List<Map<String, Object>> findTaskList(String exeId, String taskNodeName) {
        StringBuffer sql = new StringBuffer("select t.* from JBPM6_TASK t ");
        sql.append(" where t.task_nodename = ? and t.exe_id = ? ");
        sql.append(" and t.fromtask_ids in ( ");
        sql.append(" select fromtask_ids from JBPM6_TASK where task_nodename = ?  and exe_id = ? ");
        sql.append(" group by fromtask_ids ) order by t.create_time desc");
        return dao.findBySql(sql.toString(), new Object[]{taskNodeName,exeId,taskNodeName,exeId},null);
    }
    /**
     * 
     * 描述 根据任务截止时间获取剩余工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午4:15:04
     * @param taskDeadTime
     * @return 0:表示今天截止 -1:已超期
     */
    public int getLeftWorkDay(String taskDeadTime,String taskId){
        //获取挂起的工作天数
        int hangAllTime = 0;
        if(StringUtils.isNotEmpty(taskId)){
          //获取流程实例信息
            Map<String,Object> flowTask = this.getByJdbc("JBPM6_TASK",
                    new String[]{"TASK_ID"},new Object[]{taskId});
            String parentTaskId = (String)flowTask.get("PARENT_TASKID");
            //获取挂起的工作天数
            hangAllTime = flowHangInfoService.gethangAllTimeByTaskId(parentTaskId);
        }
        
        Date taskDeadDay = DateTimeUtil.getDateOfStr(taskDeadTime, "yyyy-MM-dd HH:mm");
        String endDate = DateTimeUtil.getStrOfDate(taskDeadDay, "yyyy-MM-dd");
        if(hangAllTime>0){
            String eDate = this.workdayService.getDeadLineDate(endDate, hangAllTime);
            if(StringUtils.isNotEmpty(eDate)){
                 endDate = eDate;
            }
        }
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        if(endDate.equals(currentDate)){
            return 0;
        }else{
            int leftWorkDay = this.workdayService.getWorkDayCount(currentDate, endDate);
            if(leftWorkDay==0){
                return -1;
            }else{
                return leftWorkDay;
            }
        }
    }
    @Override
    public List<Map<String, Object>> findBdcHandledByUser(SqlFilter sqlFilter, String userAccount) {
        // TODO Auto-generated method stub
        return dao.findBdcHandledByUser(sqlFilter, userAccount);
    }
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter) {
        // TODO Auto-generated method stub
        return dao.findBySqlFilter(filter);
    }
    @Override
    public List<Map<String, Object>> getAdnormalStatist(SqlFilter filter) {
        // TODO Auto-generated method stub
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
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");
        
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
    @Override
    public List<Map<String, Object>> getIndPerDataStatist(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        String sqlString = "select us.FULLNAME,t.creator_id,"
                + "sum(case when t.run_status != 0 then 1 else 0 end)as COUNTS,"
                + "sum(case when t.run_status in (2, 3, 4) then 1 else 0 end)as BANJS,"
                + "sum(case when t.run_status in (5) then 1 else 0 end)as TJS,"
                + "sum(case when t.run_status in (1) then 1 else 0 end)as ZBS "
                + "from JBPM6_EXECUTION t "
                + "join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code "
                + "join T_MSJW_SYSTEM_SYSUSER us on us.USER_ID = t.creator_id "
                + "where 1 = 1  "
                + "and ws.ITEM_CODE IN ( select dic.dic_code from " +
                "T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') "
                + "and t.creator_id is not null";

        String exeSql = dao.getQuerySql(filter, sqlString, params);
        exeSql = exeSql + " group by us.FULLNAME,t.creator_id order by COUNTS desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }
    @Override
    public List<Map<String, Object>> countsDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        return dao.countsDetailData(filter);
    }
    @Override
    public List<Map<String, Object>> banJSDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        return dao.banJSDetailData(filter);
    }
    @Override
    public List<Map<String, Object>> zBSDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        return dao.zBSDetailData(filter);
    }
    @Override
    public List<Map<String, Object>> tJSDetailData(SqlFilter filter) {
        // TODO Auto-generated method stub
        return dao.tJSDetailData(filter);
    }
    @Override
    public Set<String> isBfbdcdjzmSlType(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        boolean isNeed = false;
        String type = flowVars.get("TYPE").toString();
        if(type.equals("1")){
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("复审");
        } else {
            resultNodes.add("初审");
        }
        return resultNodes;
    }
    @Override
    public Set<String> isBfbdcdjzmFsType(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        boolean isNeed = false;
        String type = flowVars.get("TYPE").toString();
        if(type.equals("1")){
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("登簿");
        } else {
            resultNodes.add("审核");
        }
        return resultNodes;
    }
    
    /**
     * 描述 获取配置的银行需要受理的业务清单
     * @author Keravon Feng
     * @created 2020年1月9日 下午3:07:54
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID=T.IMPL_DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1' ");
        sql.append("AND T.ITEM_CODE IN (SELECT DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE = 'bankApplyCode') ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:开发商业务受理数据列表
     *
     * @author Madison You
     * @created 2020/8/13 11:12:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findByKfsywslListSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID=T.IMPL_DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1' ");
        sql.append("AND T.ITEM_CODE IN (SELECT DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE = 'kfsywslApplyCode') ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> setCkbsQueuerecordToExeId(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        String lineId = (String) flowVars.get("lineId");
        SysUser curLoginUser = null;
        try {
            curLoginUser = AppUtil.getLoginUser();
            if (null != curLoginUser) {
                Map<String, Object> variable = new HashMap<String, Object>();
                variable.put("CALL_STATUS", "1");
                variable.put("OPERATOR", curLoginUser.getFullname());
                variable.put("OPERATOR_ID", curLoginUser.getUserId());
                variable.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                variable.put("EXE_ID", exeId);
                if (StringUtils.isNotEmpty(lineId)) {
                    Map<String, Object> queue = this.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                            new Object[] { lineId });
                    if (queue != null) {
                        variable.put("OPERATOR_NAME", curLoginUser.getFullname());
                        this.dao.saveOrUpdate(variable, "T_CKBS_QUEUERECORD", lineId);
                    } else {
                        this.dao.saveOrUpdate(variable, "T_CKBS_NUMRECORD", lineId);
                    }

                    String hjmc = "";
                    if (flowVars.get("HJMC") != null) {
                        hjmc = flowVars.get("HJMC").toString();
                    }
                    if ("待受理".equals(hjmc)) {
                        // 获取当前登录用户信息
                        String eflowCreatorid = curLoginUser.getUserId();
                        int isExist = sysUserDao.isExistsUserById(eflowCreatorid);
                        if (1 == isExist) {
                            Map<String, Object> flowExe = null;
                            if (StringUtils.isNotEmpty(exeId)) {
                                flowExe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                                        new Object[] { exeId });
                                flowExe.put("OPERATOR_ID", eflowCreatorid);
                                flowExe.put("CREATOR_NAME", curLoginUser.getFullname());
                                flowExe.put("CREATOR_ACCOUNT", curLoginUser.getUsername());
                                flowExe.put("CREATOR_ID", eflowCreatorid);
                                flowExe.put("CREATOR_PHONE", curLoginUser.getMobile());
                                flowExe.put("SQFS", 2);
                                dao.saveOrUpdate(flowExe, "JBPM6_EXECUTION", exeId);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // 省下发事项无当前登录人
            return flowVars;
        }
        return flowVars;
    }

    /**
     * 描述:不动产受理通知单表格数据
     *
     * @author Madison You
     * @created 2020/11/3 14:37:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> bdcSltzdTableData(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String, Object> exeMap = args.get("exeMap");
        Map<String, Object> itemMap = args.get("itemMap");
        Map<String, Object> busMap = args.get("busMap");
        String exeId = StringUtil.getValue(exeMap, "EXE_ID");
        String busRecordid = StringUtil.getValue(exeMap, "BUS_RECORDID");
        String busTablename = StringUtil.getValue(exeMap, "BUS_TABLENAME");
        String itemId = StringUtil.getValue(itemMap, "ITEM_ID");
        returnMap.put("xmcxmm", StringUtil.getValue(exeMap, "BJCXMM"));
        returnMap.put("xmsqbh", exeId);
        returnMap.put("blsxmc", StringUtil.getValue(itemMap, "ITEM_NAME"));
        /*项目申请人员改为权利人*/
        returnMap.put("xmsqry", getXmsqry(exeMap,busMap));
        returnMap.put("spfwlx", dictionaryService.getDicNames("ServiceItemType", StringUtil.getValue(itemMap, "SXLX")));
        returnMap.put("zdxm", "");
        returnMap.put("blxmmc", StringUtil.getValue(busMap, "SBMC"));
        returnMap.put("cnsxgzr", StringUtil.getValue(itemMap, "CNQXGZR"));
        returnMap.put("jbrname", StringUtil.getValue(exeMap, "JBR_NAME"));
        returnMap.put("tshjjsx", printAttachService.getTshjName(itemId));
        String sfsf = StringUtil.getValue(itemMap, "SFSF");
        if ("0".equals(sfsf)) {
            sfsf = "不收费";
        } else {
            String sffs = StringUtil.getValue(itemMap, "SFFS");
            String yiju = StringUtil.getValue(itemMap, "SFBZJYJ");
            yiju = replaceBlank(yiju);
            sffs = dictionaryService.findByDicCodeAndTypeCode(sffs, "ChargeType");
            sfsf += "    收费方式：" + sffs + "    收费标准及依据：" + yiju;
        }
        returnMap.put("sfyjjbz", sfsf);
        returnMap.put("jbrlxdh", StringUtil.getValue(exeMap, "JBR_LXDH"));
        returnMap.put("jbrmobile", StringUtil.getValue(exeMap, "JBR_MOBILE"));
        returnMap.put("jbrlxdz", StringUtil.getValue(exeMap, "JBR_ADDRESS"));
        returnMap.put("zl", getBdcZl(busMap));

        Set<String> wsset = printAttachService.findwsqcllb(busRecordid, busTablename, itemId, exeId);
        StringBuffer wsStr = new StringBuffer();
        for (String str : wsset) {
            Map<String, Object> applyMater = printAttachService.getByJdbc("T_WSBS_APPLYMATER",
                    new String[] { "MATER_CODE" }, new Object[] { str });
            Map<String, Object> sapplyMater = printAttachService.getByJdbc("T_WSBS_SERVICEITEM_MATER",
                    new String[] { "MATER_ID","ITEM_ID" },
                    new Object[] { applyMater.get("MATER_ID").toString(),itemId});
            String mater_isneed = StringUtil.getValue(sapplyMater, "MATER_ISNEED");
            if ("1".equals(mater_isneed)) {
                wsStr.append(applyMater.get("MATER_NAME").toString().replaceAll("\r|\n", "  ")).append("\n");
            }
        }
        if (StringUtils.isNotEmpty(wsStr.toString())) {
            returnMap.put("wsqcllb", wsStr.toString());// 未收材料列表
        }else {
            returnMap.put("wsqcllb", "无");// 未收材料列表
        }

        Set<String> ysset = printAttachService.findysqcllb(busRecordid, busTablename);
        StringBuffer ysStr = new StringBuffer();
        for (String str : ysset) {
            Map<String, Object> applyMater = printAttachService.getByJdbc("T_WSBS_APPLYMATER",
                    new String[] { "MATER_CODE" }, new Object[] { str });
            if (applyMater != null) {
                String maNameString = StringUtil.getValue(applyMater, "MATER_NAME");
                if (StringUtils.isNotEmpty(maNameString)) {
                    ysStr.append(maNameString.replaceAll("\r|\n", "  ")).append("\n");
                }
            }
        }
        if (StringUtils.isNotEmpty(ysStr.toString())) {
            returnMap.put("ysqcllb", ysStr.toString());// 已收材料列表
        } else {
            returnMap.put("ysqcllb", "无");// 已收材料列表
        }

        returnMap.put("remark", StringUtil.getValue(busMap, "REMARK"));
        returnMap.put("blckdh", StringUtil.getValue(itemMap, "LXDH"));
        return returnMap;
    }

    /**
     * 描述:获取测绘公司列表()
     *
     * @author Madison You
     * @created 2020/12/16 10:36:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getDrawOrgList(String typeCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT USER_ID value,FULLNAME text FROM T_MSJW_SYSTEM_SYSUSER ");
        sql.append(" WHERE USERNAME LIKE 'chgs%' AND STATUS = 1 ");
        return (List) dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 描述:绑定测绘公司与坐落
     *
     * @author Madison You
     * @created 2020/12/16 14:57:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> bindDrawOrg(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK");
        String istempsave = StringUtil.getValue(flowVars, "EFLOW_ISTEMPSAVE");
        if (!Objects.equals(istempsave, "1")) {
            if (!(StringUtils.isNotEmpty(isBack) && Boolean.parseBoolean(isBack))) {
                String chgsId = StringUtil.getValue(flowVars, "CHGS_ID");
                String located = StringUtil.getValue(flowVars, "LOCATED");
                Map<String,Object> variables = new HashMap<>();
                variables.put("EXE_ID", StringUtil.getValue(flowVars, "EFLOW_EXEID"));
                variables.put("SQR", StringUtil.getValue(flowVars, "SQRMC"));
                variables.put("LOCATED", located);
                variables.put("SURVEY_USERID", chgsId);
                Map<String, Object> userMap = dao.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                        new String[]{"USER_ID"}, new Object[]{chgsId});
                variables.put("SURVEY_USERNAME", StringUtil.getValue(userMap, "FULLNAME"));
                List<Map<String, Object>> surveyList = dao.getAllByJdbc("T_BDC_SURVEY",
                        new String[]{"LOCATED"}, new Object[]{located});
                /*
                 * 根据坐落查询所有的此坐落的信息
                 *       1.有此坐落的信息
                 *               1.有默认的
                 *                   1.默认的就是此公司   直接更新
                 *                   2.默认的不是此公司   添加一条非默认的
                 *               2.无默认的
                 *                   添加一条无默认的
                 *       2.无此坐落的信息
                 *           新增一条默认的此坐落和此测绘公司
                 * */
                if (surveyList != null) {
                    if (surveyList.size() > 0) {//表中存在此对应关系
                        Map<String, Object> mrSurveyMap = null;
                        for (Map<String, Object> surveyMap : surveyList) {
                            String isMr = StringUtil.getValue(surveyMap, "IS_MR");
                            if (Objects.equals(isMr, "1")) {
                                mrSurveyMap = surveyMap;
                            }
                            break;
                        }
                        if (mrSurveyMap != null) {
                            String mrUserId = StringUtil.getValue(mrSurveyMap, "SURVEY_USERID");
                            if (Objects.equals(mrUserId, chgsId)) {
                                dao.saveOrUpdate(variables, "T_BDC_SURVEY",
                                        StringUtil.getValue(mrSurveyMap, "YW_ID"));
                            } else {
                                variables.put("IS_MR", "0");
                                dao.saveOrUpdate(variables, "T_BDC_SURVEY", null);
                            }
                        } else {
                            variables.put("IS_MR", "0");
                            dao.saveOrUpdate(variables, "T_BDC_SURVEY", null);
                        }
                    } else { //表中不存在此对应关系，则新增一条，非默认的对应关系
                        variables.put("IS_MR", "1");
                        dao.saveOrUpdate(variables, "T_BDC_SURVEY", null);
                    }
                }
            }
        }
        return flowVars;
    }

    /**
     * 描述:是否首次测绘（决策事件）
     *
     * @author Madison You
     * @created 2020/12/17 16:32:00
     * @param
     * @return
     */
    @Override
    public Set<String> isFirstDraw(Map<String, Object> flowVars) {
        Set<String> resultNodes = new HashSet<String>();
        String sfscch = StringUtil.getValue(flowVars, "SFSCCH");
        if (StringUtils.isNotEmpty(sfscch)) {
            if (Objects.equals(sfscch, "1")) {
                resultNodes.add("权籍调查");
            } else {
                resultNodes.add("初审");
            }
        }
        return resultNodes;
    }

    /**
     * 替换换行、制表符
     * @param str
     * @return
     */
    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("  ");
        }
        return dest;
    }

    /**
     * 描述:获取不动产坐落
     *
     * @author Madison You
     * @created 2020/11/3 15:47:00
     * @param
     * @return
     */
    private String getBdcZl(Map<String, Object> busMap) {
        String zl = "";
        zl = StringUtil.getValue(busMap, "ZL");
        if (StringUtils.isEmpty(zl)) {
            zl = StringUtil.getValue(busMap, "BDCZL");
            if (StringUtils.isEmpty(zl)) {
                zl = StringUtil.getValue(busMap, "LOCATED");
                if (StringUtils.isNotEmpty(zl)) {
                    zl = StringUtil.getValue(busMap, "FW_ZL");
                }
            }
        }
        return zl;
    }

    /**
     * 描述:获取权利人
     *
     * @author Madison You
     * @created 2020/11/3 17:26:00
     * @param
     * @return
     */
    private String getXmsqry(Map<String,Object> exeMap , Map<String,Object> busMap){
        String xmsqry = "";
        String busTablename = StringUtil.getValue(exeMap, "BUS_TABLENAME");
        if (busTablename.equals("T_BDCQLC_GYJSJFWZYDJ")) {
            String piJson = StringUtil.getValue(busMap, "POWERPEOPLEINFO_JSON");
            if (StringUtils.isNotEmpty(piJson)) {
                StringBuffer qlrxm = new StringBuffer();
                List<Map> piList = JSONArray.parseArray(piJson, Map.class);
                for (Map<String, Object> piMap : piList) {
                    qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                }
                xmsqry = spbPrintConfigService.bdcStringOutFormate(qlrxm);
            }
        } else {
            String bsyhlx = StringUtil.getValue(exeMap, "BSYHLX");
            if ("1".equals(bsyhlx)) {
                xmsqry = StringUtil.getValue(exeMap, "SQRMC");
            } else {
                xmsqry = StringUtil.getValue(exeMap, "SQJG_NAME");
            }
        }
        return xmsqry;
    }


}
