/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.zzhy.dao.EnterpriseChangeDao;
import net.evecom.platform.zzhy.service.EnterpriseChangeService;

/**
 * 描述
 * @author Danto Huang
 * @created 2021年4月29日 上午9:33:58
 */
@Service("enterpriseChangeService")
public class EnterpriseChangeServiceImpl extends BaseServiceImpl implements EnterpriseChangeService {
    /**
     * 
     */
    @Resource
    private EnterpriseChangeDao dao;

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
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2021年4月29日 上午9:36:05
     * @param flowVars
     * @return
     * @see net.evecom.platform.zzhy.service.EnterpriseChangeService#getPerAuditPass(java.util.Map)
     */
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
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2021年4月29日 上午9:41:06
     * @param flowVars
     * @return
     * @see net.evecom.platform.zzhy.service.EnterpriseChangeService#getSignAuditPass(java.util.Map)
     */
    @Override
    public Set<String> getSignAuditPass(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        String FACESIGNPASS = StringUtil.getString(flowVars.get("FACESIGNPASS"));
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));
        Set<String> resultNodes = new HashSet<String>();
        if (StringUtils.isNotEmpty(FACESIGNPASS) && FACESIGNPASS.equals("1")) {
            // 修正办件基本表面签状态修改为面签
            String sql = "update jbpm6_execution t set t.isfacesign=1 where t.exe_id=?";
            dao.executeSql(sql, new Object[] { exeId });
            resultNodes.add("工商审批");
        } else {
            // 办件基本表面签状态修改为未面签
            String sql = "update jbpm6_execution t set t.isfacesign=0 where t.exe_id=?";
            dao.executeSql(sql, new Object[] { exeId });
            resultNodes.add("身份认证");
        }
        return resultNodes;
    }
}
