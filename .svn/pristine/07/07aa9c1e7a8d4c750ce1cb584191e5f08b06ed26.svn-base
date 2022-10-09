/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.HistDeployDao;
import net.evecom.platform.hflow.service.ButtonRightService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FieldRightService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.HistDeployService;

import org.springframework.stereotype.Service;

/**
 * 描述 流程历史部署操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("histDeployService")
public class HistDeployServiceImpl extends BaseServiceImpl implements HistDeployService {
    /**
     * 所引入的dao
     */
    @Resource
    private HistDeployDao dao;
    /**
     * buttonRightService
     */
    @Resource
    private ButtonRightService buttonRightService;
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * fieldRightService
     */
    @Resource
    private FieldRightService fieldRightService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
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
     * 描述 保存或者更新流程历史部署信息
     * @author Flex Hu
     * @created 2015年8月14日 上午8:42:19
     * @param defId
     * @param flowDef
     */
    public void saveOrUpdate(String defId,Map<String,Object> flowDef){
        Map<String,Object> histDeploy = new HashMap<String,Object>();
        histDeploy.put("DEF_ID", defId);
        histDeploy.put("DEF_VERSION", flowDef.get("VERSION"));
        histDeploy.put("DEF_JSON", flowDef.get("DEF_JSON"));
        histDeploy.put("DEF_XML", flowDef.get("DEF_XML"));
        histDeploy.put("DEF_KEY", flowDef.get("DEF_KEY"));
        dao.saveOrUpdate(histDeploy, "JBPM6_HIST_DEPLOY",null);
    }
    
    
    /**
     * 
     * 描述 获取流程版本列表
     * @author Flex Hu
     * @created 2015年8月27日 上午10:34:02
     * @param sqlFitler
     * @param defId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBySqlFitler(SqlFilter sqlFilter,String defId){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select D.DEF_ID,D.DEF_VERSION,D.DEF_KEY");
        sql.append(" from JBPM6_HIST_DEPLOY D WHERE D.DEF_ID=? ");
        sql.append(" AND D.DEF_VERSION != (SELECT F.VERSION FROM JBPM6_FLOWDEF F");
        sql.append(" WHERE F.DEF_ID=? )");
        params.add(defId);
        params.add(defId);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 级联删除掉流程版本
     * @author Flex Hu
     * @created 2015年8月27日 上午10:46:46
     * @param defId
     * @param flowVersions
     */
    public void deleteHistoryVersionCascade(String defId,String[] flowVersions){
        for(String flowVersion:flowVersions){
            dao.delete(defId, Integer.parseInt(flowVersion));
            buttonRightService.deleteByDefIdAndVersion(defId,Integer.parseInt(flowVersion));
            flowEventService.deleteByDefIdAndVersion(defId, Integer.parseInt(flowVersion));
            fieldRightService.deleteByDefIdAndVersion(defId, Integer.parseInt(flowVersion));
            flowNodeService.deleteByDefIdAndVersion(defId,  Integer.parseInt(flowVersion));
            dao.remove("JBPM6_NODEAUDITERCONF",new String[]{"DEF_ID","DEF_VERSION"},new Object[]{defId,flowVersion});
            dao.remove("JBPM6_NODECONFIG",new String[]{"DEF_ID","DEF_VERSION"},new Object[]{defId,flowVersion});
            executionService.deleteExeCascadeTask(defId, Integer.parseInt(flowVersion));
        }
    }
}
