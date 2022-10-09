/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.NodeAuditerDao;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.service.NodeAuditerService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 节点审核操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("nodeAuditerService")
public class NodeAuditerServiceImpl extends BaseServiceImpl implements NodeAuditerService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(NodeAuditerServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private NodeAuditerDao dao;
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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.AUDITER_ID,T.NEXT_NODE_NAME,A.CONFIG_NAME");
        sql.append(",T.AUDITER_RULE,T.VARS_VALUE,DIC.DIC_NAME from JBPM6_NODEAUDITERCONF T ");
        sql.append("LEFT JOIN JBPM6_AUDITCONFIG A ON T.CONFIG_ID=A.CONFIG_ID LEFT JOIN ");
        sql.append("(SELECT D.* FROM T_MSJW_SYSTEM_DICTIONARY D WHERE D.TYPE_ID=(SELECT P.TYPE_ID FROM ");
        sql.append("T_MSJW_SYSTEM_DICTYPE P WHERE P.TYPE_CODE='FlowAuditerRule')) DIC ON T.AUDITER_RULE=DIC.DIC_CODE");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 获取审核人配置
     * @author Flex Hu
     * @created 2015年8月19日 上午11:17:01
     * @param nodeName
     * @param nextNodeName
     * @param defId
     * @return
     */
    public Map<String,Object> getNodeAuditer(String nodeName,String nextNodeName,String defId,int flowVersion){
        return dao.getNodeAuditer(nodeName, nextNodeName, defId,flowVersion);
    }
    
    /**
     * 
     * 描述 根据节点审核人配置获取审核信息列表
     * @author Flex Hu
     * @created 2015年8月19日 上午11:25:52
     * @param nodeAuditer
     * @return
     */
    public List<FlowNextHandler> findNextHandler(Map<String,Object> flowVars,Map<String,Object> nodeAuditer){
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        String exeCode = (String) nodeAuditer.get("CONFIG_CODE");
        String[] beanMethods = exeCode.split("[.]");
        String beanId = beanMethods[0];
        String method = beanMethods[1];
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean != null) {
            Method invokeMethod;
            try {
                invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                        new Class[] { Map.class, String.class, String.class });
                handlers = (List<FlowNextHandler>) invokeMethod.invoke(serviceBean, new Object[] { flowVars,
                    (String) nodeAuditer.get("VARS_VALUE"), (String) nodeAuditer.get("AUDITER_RULE") });
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
        return handlers;
    }
    
    /**
     * 
     * 描述 将拷贝旧版本的流程审核人配置信息到新的版本
     * @author Flex Hu
     * @created 2015年11月11日 下午2:11:34
     * @param defId
     * @param newVersion
     * @param oldVersion
     */
    public void saveNewFlowVersionAuditer(String defId,int newVersion,int oldVersion){
        StringBuffer sql = new StringBuffer("select N.* FROM JBPM6_NODEAUDITERCONF N");
        sql.append(" WHERE N.DEF_ID=? AND N.DEF_VERSION=? ORDER BY N.CREATE_TIME ASC");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{defId,oldVersion}, null);
        for(Map<String,Object> map:list){
            Map<String,Object> newNodeAuditer = map;
            newNodeAuditer.put("AUDITER_ID","");
            newNodeAuditer.put("DEF_VERSION",newVersion);
            dao.saveOrUpdate(newNodeAuditer, "JBPM6_NODEAUDITERCONF",null);
        }
    }
    
    /**
     * 
     * 描述 拷贝下一环节审核人数据
     * @author Flex Hu
     * @created 2016年3月31日 下午4:04:11
     * @param targetDefId
     * @param targetVersion
     * @param newDefId
     */
    public void copyNodeAuditers(String targetDefId,int targetVersion,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_NODEAUDITERCONF(AUDITER_ID,CONFIG_ID,");
        sql.append("VARS_VALUE,DEF_ID,NODE_NAME,NEXT_NODE_NAME,AUDITER_RULE,DEF_VERSION)");
        sql.append("SELECT RAWTOHEX(SYS_GUID()),BR.CONFIG_ID,BR.VARS_VALUE,?,");
        sql.append("BR.NODE_NAME,BR.NEXT_NODE_NAME,BR.AUDITER_RULE,BR.DEF_VERSION");
        sql.append(" FROM JBPM6_NODEAUDITERCONF BR WHERE BR.DEF_ID=? AND BR.DEF_VERSION=?");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId,targetVersion});
    }
    
}
