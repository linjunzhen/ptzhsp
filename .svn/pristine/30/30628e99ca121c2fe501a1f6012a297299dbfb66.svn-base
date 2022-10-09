/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.HandlerConfigDao;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.HandlerConfigService;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月19日 上午9:19:48
 */
@Service("handlerConfigService")
public class HandlerConfigServiceImpl extends BaseServiceImpl implements HandlerConfigService {
    /**
     * 所引入的dao
     */
    @Resource
    private HandlerConfigDao dao;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 覆盖获取实体dao方法
     * 描述
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
     * 描述 获取指定角色审核人的接口
     * @author Flex Hu
     * @created 2015年8月19日 上午9:22:17
     * @param flowVars
     * @param receiveRoleId
     * @return
     */
    public List<FlowNextHandler> getAssignRoleHandlers(Map<String,Object> flowVars,
            String receiveRoleCodes,String handlerRule){
        String targetRoleCodes = StringUtil.getValueArray(receiveRoleCodes);
        StringBuffer sql = new StringBuffer("select U.User_Id,u.FULLNAME,u.USERNAME");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER U ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID=D.DEPART_ID ");
        sql.append(" WHERE U.USER_ID IN (SELECT UR.USER_ID FROM T_MSJW_SYSTEM_SYSUSER_SYSROLE UR ");
        sql.append(" WHERE UR.ROLE_ID IN (SELECT R.ROLE_ID FROM T_MSJW_SYSTEM_SYSROLE ");
        sql.append(" R WHERE R.ROLE_CODE IN ").append(targetRoleCodes).append("))");
        sql.append(" AND U.STATUS!=0 ");
        //实现为当前审核人同部门人员规则
        if(StringUtils.isNotEmpty(handlerRule)&&handlerRule.equals("1")){
            SysUser sysUser = AppUtil.getLoginUser();
            Map<String,Object> curUser = dao.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[]{"USER_ID"},new Object[]{sysUser.getUserId()}); 
            sql.append(" AND U.DEPART_ID='").append(curUser.get("DEPART_ID").toString()).append("' ");
        }
        List<Map<String,Object>> users = dao.findBySql(sql.toString(), null, null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:users){
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }
    
    /**
     * 
     * 描述 根据流程变量值获取审核人
     * @author Flex Hu
     * @created 2015年8月20日 下午12:53:31
     * @param flowVars
     * @param varName:这个变量值的内容是用户帐号,用逗号拼接开的
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getVarsHandlers(Map<String,Object> flowVars,
            String varName,String handlerRule){
        //获取帐号值
        String userAccounts = (String) flowVars.get(varName);
        String targetUserAccounts = StringUtil.getValueArray(userAccounts);
        StringBuffer sql = new StringBuffer("select U.User_Id,u.FULLNAME,u.USERNAME,U.MOBILE,U.EMAIL");
        sql.append(",D.DEPART_CODE,D.DEPART_NAME from T_MSJW_SYSTEM_SYSUSER U ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID=D.DEPART_ID ");
        sql.append(" WHERE U.USERNAME in ").append(targetUserAccounts);
        sql.append(" AND U.STATUS!=0 ");
        List<Map<String,Object>> users = dao.findBySql(sql.toString(), null, null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for(Map<String,Object> user:users){
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }
    
    /**
     * 
     * 描述 获取发起人数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:13:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FlowNextHandler> getStartHandlers(Map<String,Object> flowVars,
            String varName,String handlerRule){
        //获取流程实例ID
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
            new String[]{"EXE_ID"},new Object[]{exeId});
        //获取申请方式
        String sqfs = (String) flowExe.get("SQFS");
        String userAccount = (String) flowExe.get("CREATOR_ACCOUNT");
        StringBuffer sql = null;
        if("1".equals(sqfs)){
            sql = new StringBuffer("SELECT U.USER_ID,U.YHMC,U.YHZH,U.SJHM FROM T_BSFW_USERINFO U ");
            sql.append("WHERE U.YHZH=? ");
        }else{
            sql = new StringBuffer("select U.User_Id,u.FULLNAME,u.USERNAME,U.MOBILE,U.EMAIL");
            sql.append(",D.DEPART_CODE,D.DEPART_NAME from T_MSJW_SYSTEM_SYSUSER U ");
            sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID=D.DEPART_ID ");
            sql.append(" WHERE U.USERNAME=? ");
        }
        Map<String,Object> user = dao.getMapBySql(sql.toString(), new Object[]{userAccount});
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        FlowNextHandler handler = new FlowNextHandler();
        if("1".equals(sqfs)){
            handler.setNextStepAssignerCode((String)user.get("YHZH"));
            handler.setNextStepAssignerName((String)user.get("YHMC"));
            handler.setNextStepAssignerType(AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
        }else{
            handler.setNextStepAssignerCode((String)user.get("USERNAME"));
            handler.setNextStepAssignerName((String)user.get("FULLNAME"));
            handler.setNextStepAssignerType(AllConstant.ASSIGNER_TYPE_SYSTEMUSER);
        }
        handlers.add(handler);
        return handlers;
    }

    /**
     * 描述:不动产全流程专用（根据表单上填写的测绘机构名称获取账号）
     *
     * @author Madison You
     * @created 2020/12/15 16:27:00
     * @param
     * @return
     */
    @Override
    public List<FlowNextHandler> getDrawOrgHandlers(Map<String, Object> flowVars,
                                                    String varName, String handlerRule) {
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        FlowNextHandler handler = new FlowNextHandler();
        String chgsId = StringUtil.getValue(flowVars, "CHGS_ID");
        Map<String, Object> chgsMap = dao.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                new String[]{"USER_ID"}, new Object[]{chgsId});
        if (chgsMap != null) {
            handler.setNextStepAssignerCode(StringUtil.getValue(chgsMap, "USERNAME"));
            handler.setNextStepAssignerName(StringUtil.getValue(chgsMap, "FULLNAME"));
            handler.setNextStepAssignerType(AllConstant.ASSIGNER_TYPE_SYSTEMUSER);
        }
        handlers.add(handler);
        return handlers;
    }
}
