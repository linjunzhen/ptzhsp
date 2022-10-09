/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.BusAuditDao;
import net.evecom.platform.flowchart.service.BusAuditService;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busAuditService")
public class BusAuditServiceImpl extends BaseServiceImpl implements BusAuditService {
    /**
     * 所引入的dao
     */
    @Resource
    private BusAuditDao dao;

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
     * 描述 根据sqlfilter获取到审核核对状态列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_LCJC_BUS_AUDIT T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 提交第一次审核 调用存储过程
     * 
     * @author John Zhang
     * @created 2015-9-1 下午03:26:11
     * @param applyId
     * @param busCode
     */
    public void firstAudit(String applyId, String busCode) {
        dao.firstAudit(applyId, busCode);
    }

    /**
     * 提交第二次审核 调用存储过程
     * 
     * @author John Zhang
     * @created 2015-9-1 下午03:26:11
     * @param applyId
     * @param busCode
     */
    public void secondSubmit(String applyId, String busCode) {
        dao.makeAudit(applyId, busCode);
    }

    /**
     * 改变监察梳理审核状态
     * 
     * @author John Zhang
     * @created 2015-9-10 上午09:21:09
     * @param busCode
     */
    public void changeStatus(String busCode, String status) {
        dao.changeStatus(busCode, status);
    }

    /**
     * 
     * 第一阶段审核改变状态
     * 
     * @author John Zhang
     * @created 2015-9-23 下午04:10:12
     * @param busCode
     * @param status
     */
    public void firstStatus(String busCode, String status) {
        dao.firstStatus(busCode, status);
    }

    /**
     * 判断新增 监察审核表
     * 
     * @author John Zhang
     * @created 2015-9-14 上午10:47:36
     * @param busCode
     */
    public void judgeAudit(String busCode) {
        dao.judgeAudit(busCode);
    }

    /**
     * 流程审核后置事件
     * 
     * @author John Zhang
     * @created 2015-9-15 下午12:52:54
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterAudit(Map<String, Object> flowDatas) {
        
     // 获取业务表名称
        //String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        Map<String, Object> applyInfo = this.getByJdbc("T_LCJC_APPLYINFO", new String[] { "APPLY_ID" },
                new Object[] { busRecordId });
        Map<String, Object> excution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { flowDatas.get("EFLOW_EXEID") });
        String runStatus = excution.get("RUN_STATUS").toString();
        String isPass = flowDatas.get("EFLOW_IS_PASS").toString();
        if("1".equals(runStatus) && "0".equals(isPass)){
            if ("1".equals(applyInfo.get("TYPE_STAGE"))) {
                dao.firstStatus(applyInfo.get("BUS_CODE").toString(), "4");
            } else {
                this.changeStatus(applyInfo.get("BUS_CODE").toString(), "4");
            }
            Object taskId = flowDatas.get("EFLOW_CURRENTTASK_ID");
            applyInfo.put("TASK_ID", taskId);
            this.saveOrUpdate(applyInfo, "T_LCJC_APPLYINFO", 
                    applyInfo.get("APPLY_ID").toString());
            Map<String, Object> taskInfo = new HashMap<String, Object>();
            taskInfo.put("TASK_ID", taskId);
            taskInfo.put("TASK_STATUS", "3");
            this.saveOrUpdate(taskInfo, "JBPM6_TASK", 
                    taskInfo.get("TASK_ID").toString());
        }
        if (!"1".equals(runStatus) && "1".equals(isPass)) {
            if ("1".equals(applyInfo.get("TYPE_STAGE"))) {
                dao.firstStatus(applyInfo.get("BUS_CODE").toString(), "3");
            } else {
                this.changeStatus(applyInfo.get("BUS_CODE").toString(), "3");
            }
            Map<String, Object> special = this.getByJdbc("T_LCJC_BUS_SPECIAL", new String[]{"EXE_ID"}, 
                    new Object[]{flowDatas.get("EFLOW_EXEID")});
            special.put("EXE_ID", null);
            this.saveOrUpdate(special, "T_LCJC_BUS_SPECIAL", special.get("BUS_ID").toString());
        }
        return flowDatas;
    }

    /**
     * 编办审核分支判断
     * @author John Zhang
     * @created 2015年11月25日 上午9:11:58
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForBb(Map<String,Object> flowVars){
        String isPass = (String) flowVars.get("EFLOW_IS_PASS");
        Set<String> resultNodes = new HashSet<String>();
        if("1".equals(isPass)){//通过
            resultNodes.add("监察厅审核");
        }else{
            resultNodes.add("开始");
        }
        return resultNodes;
    }
    
    /**
     * 监察厅审核分支判断
     * @author John Zhang
     * @created 2015年11月25日 上午9:11:58
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForJct(Map<String,Object> flowVars){
        String personName = (String) flowVars.get("EFLOW_IS_PASS");
        Set<String> resultNodes = new HashSet<String>();
        if(personName.equals("1")){//通过
            resultNodes.add("结束");
        }else{
            resultNodes.add("开始");
        }
        return resultNodes;
    } 
    
    /**
     * 
     * 开始节点分支判断
     * @author John Zhang
     * @created 2015年11月25日 下午3:11:26
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForStart(Map<String,Object> flowVars){
        String currTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK", 
                new String[]{"TASK_ID"} , new Object[]{currTaskId});
        Set<String> resultNodes = new HashSet<String>();
        if(currentTask != null && currentTask.get("FROMTASK_IDS") != null){
            resultNodes.add((String) currentTask.get("FROMTASK_NODENAMES"));
        }else{
            resultNodes.add("编办审核");
        }
        return resultNodes;
    }
    
    /**
     * 
     * 开始节点分支判断
     * @author John Zhang
     * @created 2015年11月25日 下午3:11:26
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForJxStart(Map<String,Object> flowVars){
        String currTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK", 
                new String[]{"TASK_ID"} , new Object[]{currTaskId});
        Set<String> resultNodes = new HashSet<String>();
        if(currentTask != null && currentTask.get("FROMTASK_IDS") != null){
            resultNodes.add((String) currentTask.get("FROMTASK_NODENAMES"));
        }else{
            resultNodes.add("经信中心审核");
        }
        return resultNodes;
    }
}
