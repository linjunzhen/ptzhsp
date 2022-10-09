/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import net.evecom.platform.bdc.dao.BdcQlcSffzInfoDao;
import net.evecom.platform.bdc.service.BdcQlcSffzInfoService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 不动产收费发证打证后台流程相关方法
 * @author Keravon Feng
 * @created 2019年12月17日 下午2:41:48
 */
@Service("bdcQlcSffzInfoService")
public class BdcQlcSffzInfoServiceImpl extends BaseServiceImpl implements BdcQlcSffzInfoService {

    /**
     * dao
     */
    @Resource
    private BdcQlcSffzInfoDao dao;
    
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;
    
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    
    /**
     * 描述  getEntityDao
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:43:13
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 获取填写标识码打证列表数据
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:58:03
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> findQzPrintListBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,T.EVEID,T.ITEMCODE,T.OPTYPE, ");
        sql.append(" T.CREATE_TIME,E.SUBJECT,E.CREATOR_NAME,");
        sql.append(" E.SQRMC,E.SQRSJH,E.JBR_NAME,");
        sql.append(" T.QZPRINT_STATE,T.QZPRINT_TIME,T.QZPRINT_USER,");
        sql.append(" E.CREATE_TIME APPLYTIME,E.RUN_STATUS ");
        sql.append(" FROM T_BDCQLC_SFFZINFO T, JBPM6_EXECUTION E ");
        sql.append(" WHERE T.EVEID = E.EXE_ID AND E.IS_FILED = '0' ");
        sql.append(" AND T.OPTYPE = 1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述 获取填写缴费领证信息列表数据
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:58:07
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> findJffzBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,T.EVEID,T.ITEMCODE,T.OPTYPE, ");
        sql.append(" T.CREATE_TIME,E.SUBJECT,E.CREATOR_NAME,");
        sql.append(" E.SQRMC,E.SQRSJH,E.JBR_NAME,");
        sql.append(" T.QZPRINT_STATE,T.QZPRINT_TIME,T.QZPRINT_USER,");
        sql.append(" E.CREATE_TIME APPLYTIME,E.RUN_STATUS, ");
        sql.append(" T.LZ_STATE,T.LZ_TIME,T.LZ_CZR ");
        sql.append(" FROM T_BDCQLC_SFFZINFO T, JBPM6_EXECUTION E ");
        sql.append(" WHERE T.EVEID = E.EXE_ID AND E.IS_FILED = '0' ");
        sql.append(" AND T.OPTYPE = 2 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述  调用流程开始环节绑定的保存事件方法
     * @author Keravon Feng
     * @created 2019年12月18日 下午4:39:02
     * @param variables
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    @Override
    public void doSaveBdcJffzinfo(Map<String, Object> variables) throws Exception {
        String eflow_defid = String.valueOf(variables.get("EFLOW_DEFID"));
        //通过流程定义获取流程节点数据保存方法
        if(variables.get("EFLOW_DEFID") != null && StringUtils.isNotEmpty(eflow_defid)) {
            int flowVersion = flowDefService.getFlowVersion(eflow_defid);
            List<String> exeCodeList = flowEventService.findEventCodeList(eflow_defid,"开始",
                    Jbpm6Constants.EVENTTYPE_BUS,flowVersion);
            for (String eventCode:exeCodeList) {
                if(eventCode != null && StringUtils.isNotEmpty(eventCode)){
                    String[] beanMethods = eventCode.split("[.]");
                    String beanId = beanMethods[0];
                    String method = beanMethods[1];
                    Object serviceBean = AppUtil.getBean(beanId);
                    if (serviceBean != null) {
                        Method invokeMethod= serviceBean.getClass().getDeclaredMethod(method,
                                new Class[] {Map.class});
                        variables = (Map<String, Object>) invokeMethod.invoke(serviceBean,
                                new Object[] {variables});

                    }
                }
            }
        }
        
    }

    /**
     * 描述  后台打证与收费发证环节提交
     * @author Keravon Feng
     * @created 2019年12月20日 上午10:27:49
     * @param variables
     * @return
     */
    @Override
    public Map<String, Object> saveBdcAudit(Map<String, Object> variables) {
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("success", true);
        String eveId = String.valueOf(variables.get("EVEID"));
        if(eveId != null && StringUtils.isNotEmpty(eveId)) {
            Map<String,Object> sffzinfo = dao.getByJdbc("T_BDCQLC_SFFZINFO",
                    new String[] {"EVEID"}, new Object[] {eveId});
            String optype = String.valueOf(sffzinfo.get("OPTYPE"));
            String is_finish = String.valueOf(variables.get("IS_FINISH"));
            if("1".equals(optype)) { 
                if(is_finish != null && StringUtils.isNotEmpty(is_finish)) {
                    if("1".equals(is_finish)) {
                        variables.put("CZ_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        dao.saveOrUpdate(variables, "T_BDCQLC_SFFZLOG", null);
                        sffzinfo.put("OPTYPE", "2");
                        sffzinfo.put("QZPRINT_STATE", "1");
                        sffzinfo.put("QZPRINT_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        sffzinfo.put("QZPRINT_USER", sysUser.getFullname());
                        dao.saveOrUpdate(sffzinfo,"T_BDCQLC_SFFZINFO", eveId);
                    }else {
                        result.put("success", false);
                        result.put("retMsg", "权证打印环节请先完成打证操作。");
                    }
                }else {
                    result.put("success", false);
                    result.put("retMsg", "权证打印环节请确认是否完成打证操作。");
                }
            }else if("2".equals(optype)){
                if(is_finish != null && StringUtils.isNotEmpty(is_finish)) {
                    if("1".equals(is_finish)) {
                        variables.put("CZ_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        dao.saveOrUpdate(variables, "T_BDCQLC_SFFZLOG", null);
                        sffzinfo.put("OPTYPE", "3");
                        sffzinfo.put("LZ_STATE", "1");
                        sffzinfo.put("LZ_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        sffzinfo.put("LZ_CZR", sysUser.getFullname());
                        dao.saveOrUpdate(sffzinfo,"T_BDCQLC_SFFZINFO", eveId);
                    }else {
                        result.put("success", false);
                        result.put("retMsg", "缴费发证环节请确认证件领取状态。");
                    }
                }else {
                    result.put("success", false);
                    result.put("retMsg", "缴费发证环节请确认证件领取状态。");
                }
            }
        }else {
            result.put("success", false);
            result.put("retMsg", "受理编号不可为空。");
        }
        return result;
    }

    /**
     * 描述 我的打证列表
     * @author Keravon Feng
     * @created 2020年1月2日 上午11:17:38
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findMyQzPrintListBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,T.EVEID,T.ITEMCODE,T.OPTYPE, ");
        sql.append(" T.CREATE_TIME,E.SUBJECT,E.CREATOR_NAME,");
        sql.append(" E.SQRMC,E.SQRSJH,E.JBR_NAME,");
        sql.append(" T.QZPRINT_STATE,T.QZPRINT_TIME,T.QZPRINT_USER,");
        sql.append(" E.CREATE_TIME APPLYTIME,E.RUN_STATUS ");
        sql.append(" FROM T_BDCQLC_SFFZINFO T, JBPM6_EXECUTION E ");
        sql.append(" WHERE T.EVEID = E.EXE_ID AND E.IS_FILED = '0' ");
        sql.append(" AND T.OPTYPE > 1 AND T.EVEID IN ");
        sql.append(" (SELECT G.EVEID FROM T_BDCQLC_SFFZLOG G ");
        sql.append(" WHERE G.CZ_HJMC = '权证打印' AND G.CZR_USER = ? )");
        SysUser sysUser = AppUtil.getLoginUser();
        String userId = sysUser.getUserId();
        params.add(userId);
        sql.append(" AND T.QZPRINT_STATE = 1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述 我的发证数据列表
     * @author Keravon Feng
     * @created 2020年1月2日 上午11:17:38
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findMyJffzBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,T.EVEID,T.ITEMCODE,T.OPTYPE, ");
        sql.append(" T.CREATE_TIME,E.SUBJECT,E.CREATOR_NAME,");
        sql.append(" E.SQRMC,E.SQRSJH,E.JBR_NAME,");
        sql.append(" T.QZPRINT_STATE,T.QZPRINT_TIME,T.QZPRINT_USER,");
        sql.append(" E.CREATE_TIME APPLYTIME,E.RUN_STATUS, ");
        sql.append(" T.LZ_STATE,T.LZ_TIME,T.LZ_CZR ");
        sql.append(" FROM T_BDCQLC_SFFZINFO T, JBPM6_EXECUTION E ");
        sql.append(" WHERE T.EVEID = E.EXE_ID AND E.IS_FILED = '0' ");
        sql.append(" AND T.OPTYPE > 2 AND T.EVEID IN ");
        sql.append(" (SELECT G.EVEID FROM T_BDCQLC_SFFZLOG G ");
        sql.append(" WHERE G.CZ_HJMC = '缴费发证' AND G.CZR_USER = ? )");
        SysUser sysUser = AppUtil.getLoginUser();
        String userId = sysUser.getUserId();
        params.add(userId);
        sql.append(" AND T.QZPRINT_STATE = 1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:查询不动产全流程办件
     *
     * @author Madison You
     * @created 2020/6/12 10:24:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findSheduleQueryDatagrid(SqlFilter filter) {
        ArrayList<Object> params = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.EXE_ID,T.SUBJECT,T.SQRMC,T.CREATE_TIME,T.CUR_STEPNAMES,T.CUR_STEPAUDITNAMES,  ");
        sql.append(" T.RUN_STATUS,T.CREATOR_NAME FROM JBPM6_EXECUTION T WHERE BUS_TABLENAME LIKE 'T_BDCQLC%' ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        return list;
    }

}
