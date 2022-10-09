/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.taxilicense.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.taxilicense.dao.TaxiLisenceDao;
import net.evecom.platform.taxilicense.service.TaxiLisenceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * 描述
 * @author Bennett Zhang
 * @created 2021年12月14日 上午11:46:00
 */
@Service("taxiLisenceService")
public class TaxiLisenceServiceImpl extends BaseServiceImpl implements TaxiLisenceService {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(TaxiLisenceServiceImpl.class);
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;

    /**
     * 所引入的dao
     */
    @Resource
    private TaxiLisenceDao dao;

    /**
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     *
     * 描述 决策接口：是否通过 taxiLisenceService.getYSPass
     * @author Bennett Zhang
     * @created 2021-12-13 上午9:28:30
     * @param flowVars
     * @return
     */
    public Set<String> getIsPass(Map<String, Object> flowVars){
        boolean isPass = true;
        Set<String> resultNodes = new HashSet<String>();
        String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "TASK_ID" }, new Object[] { currentTaskId });
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String fromTaskId = currentTask.get("FROMTASK_IDS").toString();
        String currentTaskNodeName = currentTask.get("TASK_NODENAME").toString();
        List<Map<String,Object>> fromTasks = flowTaskService.findFromtasks(fromTaskId, exeId);
        Map<String,Object> fromTask = fromTasks.get(0);
        if(fromTask != null){
            if(!flowVars.get("IS_PASS").equals("1")){
                isPass = false;
            }
        }
        if (!isPass) {
            if(currentTaskNodeName.equals("窗口预审")){
                resultNodes.add("开始");
            }else{
                resultNodes.add("窗口预审");
            }
        } else {
            if(currentTaskNodeName.equals("窗口预审")){
                resultNodes.add("公安审核");
            }else{
                resultNodes.add("核发");
            }
        }
        return resultNodes;
    }


    /**
     * 获取审批完成 结束的出租车资格证一件事申请
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> passedTaxiLisenceList(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT  E.EXE_ID,E.SUBJECT,E.CREATOR_NAME,E.CREATE_TIME,E.SQRMC,E.RUN_STATUS,E.CUR_STEPNAMES,");
        sql.append("ZGZ.ZGZ_APPLY_STATUS,ZGZ.YW_ID,ZGZ.ZGZ_APPLY_USERNAME,ZGZ.ZGZ_APPLY_CARDNUM,");
        sql.append("ZGZ.ZGZ_APPLY_MOBILENUMBER,ZGZ.ZGZ_APPLY_TYPE,ZGZ.ZGZ_APPLY_FIRSTGETJSZTIME,");
        sql.append("ZGZ.ZGZ_EXAM_SCORE,ZGZ.ZGZ_AREAEXAM_SCORE,ZGZ.ZGZ_APPLY_ALLOWCARTYPE,ZGZ.ZGZ_AREAEXAM_TIME,ZGZ.ZGZ_CYZGZ_NUM");
        sql.append(" FROM  JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_CZCZGZ_YGXTGETSCORE ZGZ ON E.BUS_RECORDID=ZGZ.YW_ID");
        sql.append(" WHERE  E.BUS_TABLENAME='T_CZCZGZ_YGXTGETSCORE'");
        //申请状态查询
        if (sqlFilter.getQueryParams().get("Q_ZGZ.ZGZ_APPLY_STATUS_=") != null
                && !sqlFilter.getQueryParams().get("Q_ZGZ.ZGZ_APPLY_STATUS_=").equals("")){
            if("已退件".equals(sqlFilter.getQueryParams().get("Q_ZGZ.ZGZ_APPLY_STATUS_="))){
                sql.append(" AND E.RUN_STATUS=4");
                sqlFilter.removeFilter("Q_ZGZ.ZGZ_APPLY_STATUS_=");
            }else{
                sql.append(" AND E.RUN_STATUS!=4");
            }
        }
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
     * 导出 列表数据
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> exportDatas(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT ZGZ.ZGZ_APPLY_USERNAME,ZGZ.ZGZ_APPLY_STATUS,");
        sql.append("case ZGZ.ZGZ_APPLY_SEX when '1' then '男' when '2' then '女' else '' end,");
        sql.append("E.CREATE_TIME,ZGZ.ZGZ_APPLY_ADDRESS,ZGZ.ZGZ_APPLY_CARDNUM,");
        sql.append("ZGZ.ZGZ_APPLY_TRAININGUNIT,ZGZ.ZGZ_APPLY_ALLOWCARTYPE,ZGZ.ZGZ_APPLY_FIRSTGETJSZTIME");
        sql.append(" FROM  JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_CZCZGZ_YGXTGETSCORE ZGZ ON E.BUS_RECORDID=ZGZ.YW_ID");
        sql.append(" WHERE  E.BUS_TABLENAME='T_CZCZGZ_YGXTGETSCORE'");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);

        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }



    /**
     * 描述 获取导入对应的数据库数据
     * @param
     * @return
     */
    public Map<String, Object> getImportData(String exeId){
        StringBuffer sql = new StringBuffer("SELECT ZGZ.*");
        sql.append(" FROM  JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_CZCZGZ_YGXTGETSCORE ZGZ ON E.BUS_RECORDID=ZGZ.YW_ID");
        sql.append(" WHERE E.BUS_TABLENAME='T_CZCZGZ_YGXTGETSCORE' ");
        sql.append(" AND E.EXE_ID=?");
        sql.append(" ORDER BY E.CREATE_TIME DESC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),new Object[]{exeId},null);
        if(list==null){
            return  null;
        }
        return list.get(0);
    }


    /**
     *
     * 描述 流程提交后置事件
     * taxiLisenceService.updateZGZApplyStaus
     * @param flowVars
     * @return
     */
    public Map<String, Object> updateZGZApplyStaus(Map<String,Object> flowVars){
        String eflowIstempsave = StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));// 退回不执行
        String curRunningNode = StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));// 当前环节
//        String newNode = StringUtil.getString(flowVars.get("EFLOW_NEWTASK_NODENAMES"));// 新环节
        Map<String,Object> newRecord = new HashMap<>();
        String ywId = (String) flowVars.get("busRecord[YW_ID]");
        newRecord.put("YW_ID",ywId);
        // 窗口通过、窗口不通过、公安通过、公安不通过、未考试、考试通过、考试不通过、未核发、已核发、已办结、办理中
        switch (curRunningNode){
            case "开始":
                if(StringUtils.isEmpty(ywId)){
                    ywId = (String) flowVars.get("EFLOW_BUSRECORDID");
                    newRecord.put("YW_ID",ywId);
                }

                if("1".equals(eflowIstempsave))//暂存
                    newRecord.put("ZGZ_APPLY_STATUS","草稿");
                else
                    newRecord.put("ZGZ_APPLY_STATUS","办理中");
                break;
            case "窗口预审":
                if("true".equals(eflowIsback))
                    newRecord.put("ZGZ_APPLY_STATUS","窗口不通过");
                else
                    newRecord.put("ZGZ_APPLY_STATUS","窗口通过");
                break;
            case "公安审核":
                if("true".equals(eflowIsback))
                    newRecord.put("ZGZ_APPLY_STATUS","公安不通过");
                else
                    newRecord.put("ZGZ_APPLY_STATUS","公安通过");
                break;
            case "核发":
                //是否退件操作
                if(flowVars.get("ZGZ_APPLY_STATUS") != null && StringUtils.isNotEmpty(flowVars.get("ZGZ_APPLY_STATUS").toString())){
                    newRecord.put("ZGZ_APPLY_STATUS","已退件");
                }else{
                    newRecord.put("ZGZ_APPLY_STATUS","已核发");
                }
                break;
            case "办结":
                if("true".equals(eflowIsback))
                    newRecord.put("ZGZ_APPLY_STATUS","未核发");
                else
                    newRecord.put("ZGZ_APPLY_STATUS","已办结");
                break;
            default:
                break;
        }

        this.saveOrUpdate(newRecord,"T_CZCZGZ_YGXTGETSCORE",ywId);

        return flowVars;
    }




}
