/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.BjxxDao;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.statis.service.impl.StatisticsServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 
 * 描述
 * @author Faker Li
 * @created 2015年11月6日 下午12:15:43
 */
@Service("bjxxService")
public class BjxxServiceImpl extends BaseServiceImpl implements BjxxService {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BjxxServiceImpl.class);

    /**
     * 所引入的dao
     */
    @Resource
    private BjxxDao dao;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    
    /**
     * commercialService
     */
    @Resource
    private CommercialService commercialService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 获取即办件和普通件的审核人
     * @author Flex Hu
     * @created 2015年12月10日 下午3:52:08
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsers(Map<String,Object> flowVars,
            String varName,String handlerRule){
        //获取所属部门编码
        String depCode = (String) flowVars.get("SSBMBM");
        StringBuffer sql = new StringBuffer("SELECT SU.USER_ID,SU.USERNAME,SU.FULLNAME");
        sql.append(",SU.MOBILE FROM T_MSJW_SYSTEM_SYSUSER SU");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSUSER_SYSROLE");
        sql.append(" SSR ON SU.USER_ID=SSR.USER_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DD ON SU.DEPART_ID=DD.DEPART_ID ");
        sql.append(" WHERE SSR.ROLE_ID IN (SELECT SR.ROLE_ID FROM T_MSJW_SYSTEM_SYSROLE SR");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_RES_ROLE RR");
        sql.append(" ON SR.ROLE_ID=RR.ROLE_ID LEFT JOIN T_MSJW_SYSTEM_RES R");
        sql.append(" ON RR.RES_ID=R.RES_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSROLE_DATA SD");
        sql.append(" ON SD.ROLE_ID=SR.ROLE_ID ");
        sql.append("WHERE R.RES_KEY=? )");
        sql.append(" AND SU.STATUS!=0 AND DD.DEPART_CODE=?");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{varName,depCode},null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:list){
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }

    /**
     *
     * 描述 根据流程实例ID删除补件信息
     * @author Faker Li
     * @created 2015年12月16日 下午3:07:33
     * @param string
     * @see net.evecom.platform.bsfw.service.BjxxService#removeByExeid(java.lang.String)
     */
    public void removeByExeid(String exeId) {
        dao.remove("T_WSBS_BJXX", "EXE_ID", new Object[]{exeId});
    }
    

    /**
     * 
     * 描述 获取行政和公共服务审核人(子部门权限控制)
     * @author Danto Huang
     * @created 2016-3-3 下午4:32:26
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsersWithChild(Map<String,Object> flowVars,
            String varName,String handlerRule){
      //获取所属部门编码
        String itemCode = (String) flowVars.get("ITEM_CODE");
        String[] vars = varName.split(",");
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT SU.USER_ID,SU.USERNAME,SU.FULLNAME");
        sql.append(",SU.MOBILE FROM T_MSJW_SYSTEM_SYSUSER SU");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSUSER_SYSROLE");
        sql.append(" SSR ON SU.USER_ID=SSR.USER_ID ");
        //sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DD ON SU.DEPART_ID=DD.DEPART_ID ");
        //sql.append(" LEFT JOIN T_WSBS_SERVICEITEM TWS ON TWS.SSBMBM=DD.DEPART_CODE");
        sql.append(" WHERE SSR.ROLE_ID IN (SELECT SR.ROLE_ID FROM T_MSJW_SYSTEM_SYSROLE SR");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_RES_ROLE RR");
        sql.append(" ON SR.ROLE_ID=RR.ROLE_ID LEFT JOIN T_MSJW_SYSTEM_RES R");
        sql.append(" ON RR.RES_ID=R.RES_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSROLE_DATA SD");
        sql.append(" ON SD.ROLE_ID=SR.ROLE_ID ");
        sql.append("WHERE R.RES_KEY in (");
        for(int i=0;i<vars.length;i++){
            sql.append("?,");
            params.add(vars[i]);
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(") AND SD.DEP_CODE=?) AND SU.STATUS!=0 ");
        //sql.append(") AND SD.DEP_CODE=?) AND SU.STATUS!=0 AND TWS.ITEM_CODE=?");
        sql.append(" GROUP BY SU.USER_ID, SU.USERNAME, SU.FULLNAME, SU.MOBILE ");
        String sql1 = "select tsd.depart_code,tsd.depart_name from t_wsbs_serviceitem t"
                + " left join t_wsbs_serviceitem_catalog s on s.catalog_code = t.catalog_code "
                + "left join t_msjw_system_department tsd on tsd.depart_id = s.child_depart_id "
                + "where t.item_code=?";
        String childDepart = dao.getByJdbc(sql1, new Object[]{itemCode}).get("DEPART_CODE").toString();
        params.add(childDepart);
        //params.add(itemCode);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),params.toArray(), null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:list){
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }
    
    /**
     * 
     * 描述   获取行政和公共服务审核人(根据服务事项流程环节审核人设置)
     * @author Danto Huang
     * @created 2016-9-29 上午9:45:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsersByItemSet(Map<String,Object> flowVars,
            String varName,String handlerRule){
        String itemCode = (String) flowVars.get("ITEM_CODE");
        String defId = (String) flowVars.get("EFLOW_DEFID");
        //String version  = (String) flowVars.get("EFLOW_DEFVERSION");
        String nextNode = (String) flowVars.get("EFLOW_NEXTNODENAME");
        //String currentOperNode = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_SERVICEITEM_AUDITER t ");
        sql.append("where t.def_id = ? and t.node_name = ? ");
        sql.append("and t.item_id = (select s.item_id ");
        sql.append("from t_wsbs_serviceitem s where s.item_code = ?) ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                new Object[] { defId, nextNode, itemCode }, null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:list){
            String[] userAccounts = user.get("USER_ACCOUNT").toString().split(",");
            String[] userNames = user.get("USER_NAME").toString().split(",");
            for(int i=0;i<userAccounts.length;i++){
                FlowNextHandler handler = new FlowNextHandler();
                handler.setNextStepAssignerCode(userAccounts[i]);
                handler.setNextStepAssignerName(userNames[i]);
                handlers.add(handler);
            }
        }
        return handlers;
    }
    /**
     * 
     * 描述   获取行政和公共服务审核人(根据服务事项流程环节审核人设置)
     * @author Danto Huang
     * @created 2016-9-29 上午9:45:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsersByItemSetSl(Map<String,Object> flowVars,
            String varName,String handlerRule){
        String itemCode = (String) flowVars.get("ITEM_CODE");
        String defId = (String) flowVars.get("EFLOW_DEFID");
        //String version  = (String) flowVars.get("EFLOW_DEFVERSION");
        String nextNode = "受理";
        //String currentOperNode = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_SERVICEITEM_AUDITER t ");
        sql.append("where t.def_id = ? and t.node_name = ? ");
        sql.append("and t.item_id = (select s.item_id ");
        sql.append("from t_wsbs_serviceitem s where s.item_code = ?) ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                new Object[] { defId, nextNode, itemCode }, null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:list){
            String[] userAccounts = user.get("USER_ACCOUNT").toString().split(",");
            String[] userNames = user.get("USER_NAME").toString().split(",");
            for(int i=0;i<userAccounts.length;i++){
                FlowNextHandler handler = new FlowNextHandler();
                handler.setNextStepAssignerCode(userAccounts[i]);
                handler.setNextStepAssignerName(userNames[i]);
                handlers.add(handler);
            }
        }
        return handlers;
    }
    
    /**
     * 
     * 描述   是否需要网上预审
     * @author Danto Huang
     * @created 2016-10-20 下午4:48:27
     * @param flowVars
     * @return
     */
    public Set<String> getIsPreAudit(Map<String, Object> flowVars){
        boolean isNeed = false;
        String sqfs = flowVars.get("SQFS").toString();
        if(sqfs.equals("1")){
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("网上预审");
        } else {
            resultNodes.add("受理");
        }
        return resultNodes;
    }

    /**
     * 描述:是否需要网上预审(小微企业用地预申报)
     *
     * @author Madison You
     * @created 2019/9/18 8:42:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsPreAuditXW(Map<String, Object> flowVars) {
        boolean isNeed = false;
        String sqfs = flowVars.get("SQFS").toString();
        if(sqfs.equals("1")){
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("网上预审");
        } else {
            resultNodes.add("一证受理");
        }
        return resultNodes;
    }

    /**
     * 描述:是否直接跳转到区行政审批局经办人填写意见（产业奖补）
     *
     * @author Madison You
     * @created 2019/11/13 17:41:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsToTXYJ(Map<String, Object> flowVars) {
        String TO_TXYJ = flowVars.get("TO_TXYJ").toString();
        Set<String> resultNodes = new HashSet<String>();
        if (TO_TXYJ.equals("1")) {
            resultNodes.add("区行政审批局经办人填写意见");
        } else {
            resultNodes.add("行业主管部门经办人填写意见");
        }
        return resultNodes;
    }

    /**
     * 描述:是否直接跳转到区行政审批局经办人填写意见（产业奖补）
     *
     * @author Madison You
     * @created 2020/8/31 8:44:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsToTXYJT(Map<String, Object> flowVars) {
        String TO_TXYJT = flowVars.get("TO_TXYJT").toString();
        Set<String> resultNodes = new HashSet<String>();
        if (StringUtils.isNotEmpty(TO_TXYJT)) {
            if (TO_TXYJT.equals("1")) {
                resultNodes.add("区行政审批局经办人填写意见");
            } else {
                resultNodes.add("行业主管部门审核");
            }
        } else {
            resultNodes.add("行业主管部门审核");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 是否需四星及以上事项
     * @author Kester Chen
     * @created 2017-9-13 上午9:28:30
     * @param flowVars
     * @return
     */
    public Set<String> getIsFourStar(Map<String, Object> flowVars){
        boolean isNeed = false;
        String itemCode = flowVars.get("ITEM_CODE").toString();
        String sqfs = (String) flowVars.get("SQFS");
        Map<String, Object> currentTask = this.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_CODE" }, new Object[] { itemCode });
        String ckcs3 = currentTask.get("CKCS_3")==null?
                "0":currentTask.get("CKCS_3").toString();
        String ckcs4 = currentTask.get("CKCS_4")==null?
                "0":currentTask.get("CKCS_4").toString();
        if(ckcs3.equals("1")||ckcs4.equals("1")){
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (StringUtils.isNotEmpty(sqfs)) {
            if (isNeed) {
                if (sqfs.equals("3")) {
                    resultNodes.add("受理");
                } else {
                    resultNodes.add("待受理");//本地先都走待受理
                }
            } else {
                resultNodes.add("待受理");
            }
        } else {
            resultNodes.add("受理");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 是否需五星及以上事项
     * @author Kester Chen
     * @created 2017-9-13 上午9:28:30
     * @param flowVars
     * @return
     */
    public Set<String> getIsFiveStar(Map<String, Object> flowVars){
        boolean isNeed = false;
        String itemCode = flowVars.get("ITEM_CODE").toString();
        Map<String, Object> currentTask = this.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_CODE" }, new Object[] { itemCode });
        if (currentTask.get("CKCS_4") != null) {
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("受理自动跳转");
        } else {
            resultNodes.add("受理");
        }
        return resultNodes;
    }
    /**
     *
     * 描述     是否并联审批通过
     * @author Danto Huang
     * @created 2016-11-11 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsParallelAuditPass(Map<String, Object> flowVars){
        String sql = "update jbpm6_execution t set t.apply_status=? where t.exe_id=?";
        boolean isPass = true;
        Set<String> resultNodes = new HashSet<String>();
        String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "TASK_ID" }, new Object[] { currentTaskId });
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String fromTaskId = currentTask.get("FROMTASK_IDS").toString();
        String currentTaskNodeName = currentTask.get("TASK_NODENAME").toString();
        List<Map<String,Object>> fromTasks = flowTaskService.findFromtasks(fromTaskId, exeId);
        for(Map<String,Object> fromTask : fromTasks){
            if(fromTask.get("IS_PASS").equals("1")){
                continue;
            }else{
                isPass = false;
                break;
            }
        }
        if (!isPass) {
            if(currentTaskNodeName.equals("预审意见汇总")){
                resultNodes.add("开始");
                dao.executeSql(sql, new Object[]{"3",exeId});
            }else{
                resultNodes.add("窗口办理");
            }
        } else {
            if(currentTaskNodeName.equals("预审意见汇总")){
                resultNodes.add("窗口办理");
                dao.executeSql(sql, new Object[]{"5",exeId});
            }else{
                //商事与信用接口数据推送
                //sendToCredit(exeId);
                resultNodes.add("办结");
                dao.executeSql(sql, new Object[]{"8",exeId});
            }
        }
        return resultNodes;
    }

    /**
     * 
     * 描述     是否并联审批通过
     * @author Danto Huang
     * @created 2016-11-11 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsParallelAuditPassForSign(Map<String, Object> flowVars){
        String sql = "update jbpm6_execution t set t.apply_status=? where t.exe_id=?";
        boolean isPass = true;
        Set<String> resultNodes = new HashSet<String>();
        String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "TASK_ID" }, new Object[] { currentTaskId });
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String fromTaskId = currentTask.get("FROMTASK_IDS").toString();
        String currentTaskNodeName = currentTask.get("TASK_NODENAME").toString();
        List<Map<String,Object>> fromTasks = flowTaskService.findFromtasks(fromTaskId, exeId);
        for(Map<String,Object> fromTask : fromTasks){
            if(fromTask.get("IS_PASS").equals("1")){
                continue;
            }else{
                isPass = false;
                break;
            }
        }
        if (!isPass) {
            if(currentTaskNodeName.equals("预审意见汇总")){
                resultNodes.add("开始");
                dao.executeSql(sql, new Object[]{"3",exeId});
            }else{
                resultNodes.add("窗口办理");
            }
        } else {
            if(currentTaskNodeName.equals("预审意见汇总")){
                resultNodes.add(ExeDataService.ID_IDENTIFY_TASKNAME);
                dao.executeSql(sql, new Object[]{"5",exeId});
            }else{
                //商事与信用接口数据推送
                //sendToCredit(exeId);
                resultNodes.add("办结");
                dao.executeSql(sql, new Object[]{"8",exeId});
            }
        }
        return resultNodes;
    }

    /**
     * 面签审核是否通过
     * @param flowVars
     * @return
     */
    public Set<String> getIsFaceSignPass(Map<String, Object> flowVars){
        boolean isFaceSignPass = false;
        //面签是否通过
        String faceSignPass = flowVars.get("FACESIGNPASS")==null?
                "":flowVars.get("FACESIGNPASS").toString();
        String exeId = flowVars.get("EFLOW_EXEID")==null?
                "":flowVars.get("EFLOW_EXEID").toString();
        //是否需要面签
        String isNeedSign = flowVars.get("ISNEEDSIGN")==null?
                "":flowVars.get("ISNEEDSIGN").toString();
        //如果办件是不需要面签的，直接跳转到并审环节
        if(StringUtils.isEmpty(isNeedSign)||"0".equals(isNeedSign)){
            isFaceSignPass = true;
        }
        if(StringUtils.isNotEmpty(faceSignPass)&&"1".equals(faceSignPass)){
            isFaceSignPass = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isFaceSignPass) {
            resultNodes.add("并审开始");
            if("1".equals(isNeedSign)) {
                //修正办件基本表面签状态修改为面签
                String sql = "update jbpm6_execution t set t.isfacesign=1 where t.exe_id=?";
                dao.executeSql(sql, new Object[]{exeId});
            }
        } else {
            //办件基本表面签状态修改为未面签
            String sql = "update jbpm6_execution t set t.isfacesign=0 where t.exe_id=?";
            dao.executeSql(sql, new Object[]{exeId});
            //移除法人股东的信息
            //dao.remove("T_COMMERCIAL_COMPANY_LEGALFILE",new String[]{"EXE_ID"},new Object[]{exeId});
            resultNodes.add(ExeDataService.ID_IDENTIFY_TASKNAME);
        }
        return resultNodes;
    }
    /**
     * 
     * 描述：商事与信用接口数据推送
     * @author Water Guo
     * @created 2017-12-26 上午11:10:52
     */
    public void sendToCredit(String exeId){
        Map<String,Object> execution=dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"}, new Object[]{exeId});
        String defId=(String)execution.get("DEF_ID");
        //@defId--内外资流程 id
        if("2c90b38a58b9ca320158bd0fedc505c5".equals(defId)||
                "2c90b38a58b9ca320158bd0ea4760450".equals(defId)){
            commercialService.getEicpEntInfo(exeId);
        }
    }
    /**
     * 
     * 描述  商事1+N事项审核人
     * @author Danto Huang
     * @created 2017年9月27日 上午10:36:46
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getComRelatedAuditer(Map<String,Object> flowVars,
            String varName,String handlerRule){
        List<FlowNextHandler> handlers = null;
        String nextNode = (String) flowVars.get("EFLOW_NEXTNODENAME");
        String relatedItemCode = null;
        Map<String,Object> busRecord = (Map<String, Object>) flowVars.get("busRecord");
        if(flowVars.get("ITEMCODES")!=null&&StringUtils.isNotEmpty((String) flowVars.get("ITEMCODES"))){
            relatedItemCode = flowVars.get("ITEMCODES").toString();
        }else if(busRecord!=null&&busRecord.get("ITEMCODES")!=null){
            relatedItemCode = busRecord.get("ITEMCODES").toString();
        }
        if(relatedItemCode!=null&&StringUtils.isNotEmpty(relatedItemCode)){
            String[] relatedItemCodes = relatedItemCode.split(",");
            StringBuffer allAccount = new StringBuffer("");
            for(int i=0;i<relatedItemCodes.length;i++){
                String ssbmbm = dao.getByJdbc("T_COMMERCIAL_RELATED_ITEM", new String[] { "ITEM_CODE" },
                        new Object[] { relatedItemCodes[i] }).get("SSBMBM").toString();
                if(!ssbmbm.equals(varName)){
                    continue;
                }
                StringBuffer sql = new StringBuffer();
                sql.append("select t.* from T_WSBS_SERVICEITEM_AUDITER t ");
                sql.append("where t.item_id = (select s.item_id from t_wsbs_serviceitem s where s.item_code = ?) ");
                sql.append("and t.def_id=(select s.bdlcdyid from t_wsbs_serviceitem s where s.item_code = ?) ");
                sql.append("and t.node_type='task' and t.node_name<>'网上预审' ");
                sql.append("order by t.node_key ");
                List<Map<String, Object>> nodes = dao.findBySql(sql.toString(),
                        new Object[] { relatedItemCodes[i], relatedItemCodes[i] }, null);
                String[] userNames = null;
                String[] userAccounts = null;
                if(nextNode.contains("预审")){
                    userAccounts = nodes.get(0).get("USER_ACCOUNT").toString().split(",");
                    userNames = nodes.get(0).get("USER_NAME").toString().split(",");
                }else if(nextNode.contains("审批")){
                    userAccounts = nodes.get(nodes.size()-2).get("USER_ACCOUNT").toString().split(",");
                    userNames = nodes.get(nodes.size()-2).get("USER_NAME").toString().split(",");
                }
                for(int j=0;j<userAccounts.length;j++){
                    if(allAccount.indexOf(userAccounts[j])!=-1){
                        continue;
                    }
                    allAccount.append(",").append(userAccounts[j]);
                    FlowNextHandler handler = new FlowNextHandler();
                    handler.setNextStepAssignerCode(userAccounts[j]);
                    handler.setNextStepAssignerName(userNames[j]);
                    if(handlers==null){
                        handlers = new ArrayList<FlowNextHandler>();
                    }
                    handlers.add(handler);
                }
            }
        }
        if(handlers==null){
            handlers = new ArrayList<FlowNextHandler>();
        }
        return handlers;
    }


    /**
     * 描述:获取退回意见
     *
     * @author Madison You
     * @created 2019/12/10 11:43:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getBjxxInfo(String exeId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        Map<String, Object> map = null;
        params.add(exeId);
        sql.append(" select * from T_WSBS_BJXX where EXE_ID = ? order by create_time desc ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (list != null && !list.isEmpty()) {
            map = list.get(0);
        }
        return map;
    }

    @Override
    public Set<String> getIsPreAuditBdcqcwb(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        boolean isNeed = false;
        String sqfs = flowVars.get("SQFS").toString();
        //前台申报方式 （1：智能审批走待受理环节，不经过网上预审环节  0：全程网办 走网上预审环节  2(预抵与首登全程网办个性化流程)）
        String isQcwb = flowVars.get("ISQCWB") == null ? "0" : flowVars.get("ISQCWB").toString();
        String pttSqfs = StringUtil.getString(flowVars.get("PTT_SQFS"));//平潭通申请方式
        if (("1".equals(sqfs) && "0".equals(isQcwb)) || ("2".equals(pttSqfs) && "2".equals(isQcwb))) {
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("网上预审");//网上申请或全程网办（预抵、首登业务为个性化全程网办流程）
        }else if(("1".equals(sqfs) && "1".equals(isQcwb)) || ("1".equals(sqfs) && "2".equals(isQcwb))){
            resultNodes.add("待受理");//智能审批
        }else{
            resultNodes.add("受理");//窗口收件
        }
        return resultNodes;
    }
    
    /**
     * 
     * 描述    根据日期同步办件（用户）信息至办事（取号）用户信息表中
     * @author Allin Lin
     * @created 2021年3月17日 下午2:14:07
     * @param date
     */
    public void  saveOrUpdateLineUsers (String date){
        log.info("开始执行办件(用户信息)同步至办事（取号）用户信息表任务......");
        Sm4Utils sm4 = new Sm4Utils();
        String beginTime = date + " 00:00:00", endTime = date + " 23:59:59";
        int count = 0;
        //查询当天所有办件用户信息（去除身份证重复数据）
        StringBuffer sql = new StringBuffer("SELECT S.JBR_NAME,S.JBR_MOBILE, S.JBR_ZJHM FROM  ");
        sql.append(" (SELECT T.JBR_NAME,T.JBR_MOBILE, T.JBR_ZJHM, ");
        sql.append(" ROW_NUMBER() OVER (PARTITION BY T.JBR_ZJHM ORDER BY T.CREATE_TIME DESC) AS rn ");
        sql.append("FROM jbpm6_execution t WHERE T.CREATE_TIME >= ? AND  T.CREATE_TIME <= ?) S where S.RN=1 ");
        List<Map<String, Object>> bjxxList = dao.findBySql(sql.toString(), new Object[]{beginTime,endTime}, null);
        Map<String, Object> synUserInfo = new HashMap<String,Object>();
        //过滤已存在用户，同步未存在用户信息
        for (Map<String, Object> bjxx : bjxxList) {
            Map<String, Object> lineUser = dao.getByJdbc("T_BSFW_LINEUSERS",
                    new String[]{"LINE_CARDNO"}, new Object[]{bjxx.get("JBR_ZJHM")});
            if(lineUser==null){
                String cardNo = sm4.decryptDataCBC(StringUtil.getString(bjxx.get("JBR_ZJHM")));
                String mobile = sm4.decryptDataCBC(StringUtil.getString(bjxx.get("JBR_MOBILE")));
                synUserInfo.put("LINE_NAME", bjxx.get("JBR_NAME"));
                synUserInfo.put("LINE_CARDNO", cardNo);
                synUserInfo.put("LINE_MOBILE", mobile);
                count++;
                dao.saveOrUpdate(synUserInfo, "T_BSFW_LINEUSERS", null);
            }else{
                //更新
                String cardNo = sm4.decryptDataCBC(StringUtil.getString(bjxx.get("JBR_ZJHM")));
                String mobile = sm4.decryptDataCBC(StringUtil.getString(bjxx.get("JBR_MOBILE")));
                synUserInfo.put("LINE_NAME", bjxx.get("JBR_NAME"));
                synUserInfo.put("LINE_CARDNO", cardNo);
                synUserInfo.put("LINE_MOBILE", mobile);
                synUserInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                count++;
                dao.saveOrUpdate(synUserInfo, "T_BSFW_LINEUSERS", StringUtil.getString(lineUser.get("YW_ID")));
            }
        }
        log.info("结束执行办件(用户信息)同步至办事（取号）用户信息表任务(共计"+count+"条记录)......");
    }
}
