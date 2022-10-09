/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.CyjbPtDao;
import net.evecom.platform.bsfw.service.BusApplyService;
import net.evecom.platform.bsfw.service.CyjbPtService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 产业奖补相关
 * @author Keravon Feng
 * @created 2018年9月14日 下午5:44:53
 */
@Service("cyjbPtService")
public class CyjbPtServiceImpl extends BaseServiceImpl implements CyjbPtService {
    /**
     * dao
     */
    @Resource
    private CyjbPtDao dao;
    
    /**
     * busApplyService
     */
    @Resource
    private BusApplyService busApplyService;
    
    /**
     * 
     * 描述
     * @author Keravon Feng
     * @created 2018年9月14日 下午5:54:46
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.User_Id,T.USERNAME,T.FULLNAME,T.MOBILE,T.STATUS,D.DEPART_NAME,T.IS_DISABLE ");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER T LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEPART_ID=D.DEPART_ID");
        sql.append(" WHERE T.STATUS!=? ");
        params.add(SysUser.STATUS_DEL);
        //获取角色ID
        String roleId = sqlFilter.getRequest().getParameter("ROLE_ID");
        if(StringUtils.isNotEmpty(roleId)){
            sql.append(" AND T.USER_ID IN (SELECT UR.USER_ID FROM");
            sql.append(" T_MSJW_SYSTEM_SYSUSER_SYSROLE UR WHERE UR.ROLE_ID=? )");
            params.add(roleId);
        }
        //通过codes获取相应的用户数据
        String roleCodes = sqlFilter.getRequest().getParameter("ROLECODES");
        if(StringUtils.isNotEmpty(roleCodes)){
            String[] role_codes = roleCodes.split(",");
            if(role_codes != null && role_codes.length > 0){
                sql.append(" AND T.USER_ID IN (SELECT UR.USER_ID FROM");
                sql.append(" T_MSJW_SYSTEM_SYSUSER_SYSROLE UR WHERE UR.ROLE_ID IN (");
                sql.append(" SELECT ROLE_ID FROM T_MSJW_SYSTEM_SYSROLE  WHERE ROLE_CODE IN (");
                for(String roleCode : role_codes){
                    sql.append("?,");
                    params.add(roleCode);
                }
                sql.deleteCharAt(sql.lastIndexOf(","));
                sql.append(")))");
            }
        }
        
        String noAuth = sqlFilter.getRequest().getParameter("noAuth");
        if(!(StringUtils.isNotEmpty(noAuth)&&noAuth.equals("true"))){
            //获取当前用户信息
            SysUser curUser = AppUtil.getLoginUser();
            //非超管进行数据级别权限控制
            if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
                //获取当前用户被授权的部门代码
                String authDepCodes  = curUser.getAuthDepCodes();
                sql.append(" AND D.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
            }
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述 产业奖补后置事件
     * @author Keravon Feng
     * @created 2018年9月19日 上午9:00:14
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveAfterBusData(Map<String, Object> flowDatas) {
         //退回流程，不执行
         String isback = (String)flowDatas.get("EFLOW_ISBACK");
         if(!"true".equals(isback)){
             // 获取业务表名称
             String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
             // 获取业务表记录ID
             String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
             Map<String,Object> personInfo = new HashMap<String, Object>();
             //当前节点名称
             String currNodeName = (String) flowDatas.get("EFLOW_CUREXERUNNINGNODENAMES");
             String currAuditName = (String) flowDatas.get("busRecord[CUR_STEPAUDITNAMES]");
             String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
             //审批件扫描件
             String submitmaterfilejson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
             if(StringUtils.isNotEmpty(submitmaterfilejson)){
                 List<Map> maters = JSON.parseArray(submitmaterfilejson, Map.class);
                 if(maters != null && maters.size() > 0){
                     for(Map map : maters){
                         String attach_key = String.valueOf(map.get("ATTACH_KEY"));
                         //一般默认把审批表的扫描件放在_01的位置
                         if(attach_key.endsWith("_01")){ 
                             personInfo.put("ATTACH_FILEID", map.get("FILE_ID"));
                             break;
                         }
                     }
                 }
             }
             if("开始".equals(currNodeName)){
                 //如果是窗口取号的事项，要把取号信息与办件进行关联
                 busApplyService.start(flowDatas);
             }else if("区税务局经办填写意见".equals(currNodeName)){
                 personInfo.put("QTEX_JB_PERSON", currAuditName);              
                 personInfo.put("QTEX_JB_JBTIME", createTime); 
             }else if("区税务局审核".equals(currNodeName)){
                 personInfo.put("QTEX_JB_AUDIT", currAuditName);              
                 personInfo.put("QTEX_JB_AUDITTIME", createTime);   
             }else if("行业主管部门经办人填写意见".equals(currNodeName)){
                 personInfo.put("HYZG_JB_PERSON", currAuditName);              
                 personInfo.put("HYZG_JB_TIME", createTime);   
             }else if("行业主管部门审核".equals(currNodeName)){
                 personInfo.put("HYZG_JB_AUDIT", currAuditName);              
                 personInfo.put("HYZG_AUDIT_TIME", createTime);   
             }else if("审核人审核".equals(currNodeName)){
                 personInfo.put("QXZSPJ_AUDIT_TIME", createTime);   
             }else if("领导审核".equals(currNodeName)){
                 personInfo.put("HYZG_JB_LEADER", currAuditName);    
             }
             dao.saveOrUpdate(personInfo, busTableName, busRecordId);
        }
        return flowDatas;
    }
}
