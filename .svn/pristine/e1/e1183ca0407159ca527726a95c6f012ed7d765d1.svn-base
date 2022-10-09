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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.zzhy.dao.CancelDao;
import net.evecom.platform.zzhy.service.CancelService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 商事企业注销操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("cancelService")
public class CancelServiceImpl extends BaseServiceImpl implements CancelService {
    /**
     * 所引入的dao
     */
    @Resource
    private CancelDao dao;

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

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findNewSignInfo(String exeId) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.* from T_COMMERCIAL_SIGN t ");
        sql.append(" where t.exe_id = ? and t.sign_flag = 1 ");
        sql.append(" order by t.sign_time desc ");
        params.add(exeId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Set<String> getPerAuditPass(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        boolean shzdshIsOk = (Boolean) flowVars.get("shzdshIsOk");
        String ISNEEDSIGN = StringUtil.getString(flowVars.get("ISNEEDSIGN"));
        Set<String> resultNodes = new HashSet<String>();
        if (shzdshIsOk) {
            if(StringUtils.isNotEmpty(ISNEEDSIGN) && ISNEEDSIGN.equals("1")){
                resultNodes.add("身份认证");
            } else{
                resultNodes.add("窗口办理");
            }
        } else {
            resultNodes.add("开始");
        }
        return resultNodes;
    }

    @Override
    public Set<String> getSignAuditPass(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        String FACESIGNPASS = StringUtil.getString(flowVars.get("FACESIGNPASS"));
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));
        Set<String> resultNodes = new HashSet<String>();
        if (StringUtils.isNotEmpty(FACESIGNPASS) && FACESIGNPASS.equals("1")) {
            //修正办件基本表面签状态修改为面签
            String sql = "update jbpm6_execution t set t.isfacesign=1 where t.exe_id=?";
            dao.executeSql(sql, new Object[]{exeId});
            resultNodes.add("工商审批");
        } else {
            //办件基本表面签状态修改为未面签
            String sql = "update jbpm6_execution t set t.isfacesign=0 where t.exe_id=?";
            dao.executeSql(sql, new Object[]{exeId});
            resultNodes.add("身份认证");
        }
        return resultNodes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findNewTaskInfo(String exeId, String taskNodeName) {
        // TODO Auto-generated method stub

        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from JBPM6_TASK t  ");
        sql.append("  where t.exe_id = ? and t.task_nodename = ? and t.is_audited=1 ");
        sql.append(" order by t.create_time desc ");
        params.add(exeId);
        params.add(taskNodeName);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
