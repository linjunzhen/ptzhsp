/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.zzhy.dao.GtptxxglDao;
import net.evecom.platform.zzhy.service.GtptxxglService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 个体平台信息管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("gtptxxglService")
public class GtptxxglServiceImpl extends BaseServiceImpl implements GtptxxglService {
    /**
     * 所引入的dao
     */
    @Resource
    private GtptxxglDao dao;
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
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_COMMERCIAL_GTPTXXGL T ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        } 
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @Override
    public Set<String> getIsFaceSignPass(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
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
            resultNodes.add("工商审批");
            if("1".equals(isNeedSign)) {
                //修正办件基本表面签状态修改为面签
                String sql = "update jbpm6_execution t set t.isfacesign=1 where t.exe_id=?";
                dao.executeSql(sql, new Object[]{exeId});
            }
        } else {
            //办件基本表面签状态修改为未面签
            String sql = "update jbpm6_execution t set t.isfacesign=0 where t.exe_id=?";
            dao.executeSql(sql, new Object[]{exeId});
            resultNodes.add(ExeDataService.ID_IDENTIFY_TASKNAME);
        }
        return resultNodes;
    }
    @Override
    public Set<String> getIsAutoApproval(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        //申报类型 0 普通，1 秒批
        String sssblx = flowVars.get("SSSBLX")==null?"":flowVars.get("SSSBLX").toString();
        Set<String> resultNodes = new HashSet<String>();
        if(StringUtils.isNotEmpty(sssblx) && sssblx.equals("1")){
            resultNodes.add(ExeDataService.ID_IDENTIFY_TASKNAME);
        } else{
            resultNodes.add("预审开始");            
        }
        return resultNodes;
    }
    @SuppressWarnings("unchecked")
    @Override
    public String getTaskStatus(String exeId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT TASK_ID,FROMTASK_IDS,TASK_NODENAME," +
                "ASSIGNER_CODE,TASK_STATUS,fromtask_nodenames from JBPM6_TASK T ");
        sql.append(" WHERE (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') AND T.EXE_ID=? ");
        sql.append(" AND T.ASSIGNER_TYPE = '2' ");
        sql.append(" AND T.IS_AUDITED=1  ORDER BY T.CREATE_TIME DESC");
        //查询当前待办的任务,获取前一个任务
        List<Map<String,Object>> tasks = dao.findBySql(sql.toString(),new Object[]{exeId}, null);
        if(tasks!=null&&tasks.size()>0){
            String taskStatus=tasks.get(0).get("TASK_STATUS").toString();
            return taskStatus;
        }
        return null;
    }
}
