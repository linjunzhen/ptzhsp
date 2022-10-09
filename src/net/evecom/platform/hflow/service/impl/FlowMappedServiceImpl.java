/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import net.evecom.platform.hflow.dao.FlowMappedDao;
import net.evecom.platform.hflow.service.FlowMappedService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 流程映射类操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowMappedService")
public class FlowMappedServiceImpl extends BaseServiceImpl implements FlowMappedService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowMappedDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
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
     * @author Faker Li
     * @created 2015年12月22日 下午2:51:47
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.YS_ID,T.YS_NAME,T.YS_CN,T.YS_TYPE from T_WSBS_FLOWYS T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getByDefidAndNodeName(String defId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.YS_ID,T.YS_NAME,T.YS_CN,T.DEF_NAMES from T_WSBS_FLOWYS T");
        sql.append(" WHERE  T.DEF_ID = ? AND T.YS_TYPE = '2' ORDER BY T.YS_CN ASC");
        params.add(defId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                params.toArray(), null);
        return list;
    }
    /**
     * 
     * 描述  根据流程定义ID和节点名称获取映射列表
     * @author Faker Li
     * @created 2015年12月23日 上午9:04:05
     * @param defId
     * @param nodeName
     * @return
     */
    public Map<String, Object> getByDefidAndNodeName(String defId, String nodeName) {
        Map<String, Object> listMap = new HashMap<String, Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.YS_ID,T.YS_NAME,T.YS_CN,T.DEF_NAMES from T_WSBS_FLOWYS T");
        sql.append(" WHERE  T.DEF_ID = ? AND T.YS_TYPE = '2' ORDER BY T.YS_CN ASC");
        params.add(defId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                params.toArray(), null);
        if (list.size() == 2) {
            list.get(1).put("YS_NAME", "审查与决定");
        }else if (list.size() == 3) {
            list.get(1).put("YS_NAME", "审查");
            list.get(2).put("YS_NAME", "决定");
        }else if (list.size() == 4) {
            list.get(1).put("YS_NAME", "审查");
            list.get(2).put("YS_NAME", "决定");
            list.get(3).put("YS_NAME", "制证与送达");
        }else if (list.size() == 5) {
            list.get(1).put("YS_NAME", "审查");
            list.get(2).put("YS_NAME", "决定");
            list.get(3).put("YS_NAME", "制证");
            list.get(4).put("YS_NAME", "送达");
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setGroupingSize(0);
        df.setRoundingMode(RoundingMode.FLOOR);
        String nodeWidth = df.format(100.0/(list.size()))+"%";
        if(StringUtils.isEmpty(nodeName)){
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("nodeWidth", nodeWidth);
                list.get(i).put("nodeClass", "flow_done");
            }
            listMap.put("allHighLight", "100%");
        }else{
            int nodeNow = 1;
            Set<String> set = null;
            String allHighLight = df.format(100.0/(list.size()-1));
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("nodeWidth", nodeWidth);
                set = StringUtil.getSet((String)list.get(i).get("DEF_NAMES"));
                if(set.contains(nodeName)){
                    list.get(i).put("nodeClass", "flow_active");
                    nodeNow = Integer.parseInt((String)list.get(i).get("YS_CN"));
                }
            }
            for (int i = 0; i < list.size(); i++) {
                if(Integer.parseInt((String)list.get(i).get("YS_CN"))<nodeNow){
                    list.get(i).put("nodeClass", "flow_done");
                }else if(Integer.parseInt((String)list.get(i).get("YS_CN"))>nodeNow){
                    list.get(i).put("nodeClass", "flow_nodone");
                }
            }
            listMap.put("allHighLight", (nodeNow-1)*Double.parseDouble(allHighLight)+"%");
        }
        int flow_progress =100;
        if( 0!=list.size() ){
            flow_progress = 100/(list.size()*2) +1;
        }
        listMap.put("mappedList", list);
        listMap.put("flow_progress",flow_progress+"%");
        return listMap;
    }
    /**
     * 
     * 描述  保存流程到映射节点并且保存到映射子表
     * @author Faker Li
     * @created 2015年12月28日 下午2:18:25
     * @param variables
     * @param string
     * @return
     */
    public String saveOrUpdateYs(Map<String, Object> variables, String tableName) {
        String entityId = (String)variables.get("YS_ID");
        String defNames = (String)variables.get("DEF_NAMES");
        String recordId = this.saveOrUpdate(variables, tableName, entityId);
        if(StringUtils.isNotEmpty(entityId)){
            dao.remove("T_WSBS_FLOWYSNODES", new String[]{"YS_ID"},new Object[]{entityId});
            //保存子表
            dao.saveYsNode(entityId,defNames.split(","));
        }else{
            dao.saveYsNode(recordId,defNames.split(","));
        }
        return recordId;
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称和映射类型获取映射信息
     * @author Flex Hu
     * @created 2015年12月28日 下午3:27:16
     * @param defId
     * @param nodeName
     * @param mapType
     * @return
     */
    public Map<String,Object> getFlowMapInfo(String defId,String nodeName,String mapType){
        return dao.getFlowMapInfo(defId, nodeName, mapType);
    }
}
