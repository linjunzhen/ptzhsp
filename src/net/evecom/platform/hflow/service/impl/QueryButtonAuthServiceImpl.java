/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.QueryButtonAuthDao;
import net.evecom.platform.hflow.service.QueryButtonAuthService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 查询按钮权限操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("queryButtonAuthService")
public class QueryButtonAuthServiceImpl extends BaseServiceImpl implements QueryButtonAuthService {
    /**
     * 所引入的dao
     */
    @Resource
    private QueryButtonAuthDao dao;
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
     * 描述 保存或者获取列表
     * @author Flex Hu
     * @created 2016年1月12日 上午9:00:51
     * @param buttonList
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>>  saveOrGetList(List<Map<String,Object>> buttonList
            ,String defId,int flowVersion){
        for(Map<String,Object> button:buttonList){
            String buttonKey = (String) button.get("BUTTON_KEY");
            //获取权限配置信息
            boolean exists = dao.isExists(defId, flowVersion, buttonKey);
            if(!exists){
                Map<String,Object> buttonAuth = new HashMap<String,Object>();
                buttonAuth.put("DEF_ID", defId);
                buttonAuth.put("BUTTON_KEY", buttonKey);
                buttonAuth.put("FLOW_VERSION", flowVersion);
                dao.saveOrUpdate(buttonAuth, "JBPM6_QUERYBUTTONAUTH", null);
            }
        }
        StringBuffer sql = new StringBuffer("SELECT Q.AUTH_ID,Q.BUTTON_KEY,B.BUTTON_NAME");
        sql.append(",Q.Is_Auth FROM JBPM6_QUERYBUTTONAUTH Q LEFT JOIN ");
        sql.append("JBPM6_BUTTON B ON Q.BUTTON_KEY=B.BUTTON_KEY WHERE Q.DEF_ID=?");
        sql.append(" AND Q.FLOW_VERSION=? ORDER BY B.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), new Object[]{defId,flowVersion}, null);
    }
    
    /**
     * 
     * 描述 保存按钮权限
     * @author Flex Hu
     * @created 2016年1月12日 上午10:51:38
     * @param variables
     */
    public void saveButtonAuths(Map<String,Object> variables){
        String defId = (String) variables.get("defId");
        String flowVersion = (String) variables.get("flowVersion");
        int flowVers = Integer.parseInt(flowVersion);
        Iterator it = variables.entrySet().iterator();
        StringBuffer sql = new StringBuffer("update JBPM6_QUERYBUTTONAUTH J");
        sql.append(" SET J.IS_AUTH=? WHERE J.DEF_ID=? AND J.BUTTON_KEY=? ");
        sql.append(" AND J.FLOW_VERSION=? ");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String fieldName = (String) entry.getKey();
            Object val = entry.getValue();
            if(StringUtils.isNotEmpty(val.toString())){
                dao.executeSql(sql.toString(), new Object[]{val,defId,fieldName,flowVers});
            }
        }
    }
    
    /**
     * 
     * 描述 根据流程定义ID和版本号获取查看按钮列表
     * @author Flex Hu
     * @created 2016年1月12日 上午11:26:30
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findList(String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT B.BUTTON_NAME,B.BUTTON_KEY,B.BUTTON_FUN");
        sql.append(" FROM JBPM6_BUTTON B LEFT JOIN JBPM6_QUERYBUTTONAUTH A ");
        sql.append(" ON B.BUTTON_KEY=A.BUTTON_KEY WHERE A.DEF_ID=? ");
        sql.append(" AND A.FLOW_VERSION=? AND A.IS_AUTH=1 ORDER BY B.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), new Object[]{defId,flowVersion}, null);
    }
    
    /**
     * 
     * 描述 拷贝查询按钮权限
     * @author Flex Hu
     * @created 2016年3月31日 下午4:17:40
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyQueryButtons(String targetDefId,int targetFlowVersion,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_QUERYBUTTONAUTH(AUTH_ID,"
                + "DEF_ID,BUTTON_KEY,IS_AUTH,FLOW_VERSION)");
        sql.append(" SELECT RAWTOHEX(SYS_GUID()),?,BUTTON_KEY,IS_AUTH,FLOW_VERSION");
        sql.append(" FROM JBPM6_QUERYBUTTONAUTH  WHERE DEF_ID=? AND FLOW_VERSION=?");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId,targetFlowVersion});
    }
}
