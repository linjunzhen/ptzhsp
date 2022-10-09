/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.dao.DataSourceDao;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月17日 下午3:45:43
 */
@Service("dataSourceService")
public class DataSourceServiceImpl extends BaseServiceImpl implements DataSourceService {
    /**
     * 所引入的dao
     */
    @Resource
    private DataSourceDao dao;
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
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
     * 判断一个记录是否存在
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsRecord(String tableName,String fieldName,Object fieldValue,String recordId){
        return dao.isExistsRecord(tableName, fieldName, fieldValue,recordId);
    }
    /**
     * 判断一个流程记录状态在[1:正在办理中,2:已办结(正常结束),3:已办结(审核通过)。]是否存在相同的公司名称记录
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean isExistsFlowRecord(String tableName,String fieldName,Object fieldValue,String recordId){
        return dao.isExistsFlowRecord(tableName, fieldName, fieldValue,recordId);
    }

    @Override
    public boolean isExistsFlowToBdcdyhRecord(String tableName, String fieldName, Object fieldValue, String recordId) {
        // TODO Auto-generated method stub
        return dao.isExistsFlowToBdcdyhRecord(tableName, fieldName, fieldValue, recordId);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> findAllBdcdyh(Object fieldValue, String exeId) {
        // TODO Auto-generated method stub
        Map<String, Object> returnMap = new HashMap<>();
        List<String> bdcdyhList = new ArrayList<>();
        List<String> param = null;
        /* 查询字典表中设置的不动产业务及不动产单元号字段 */
        List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("bdcdyhpdsfzb");
        for (Map<String, Object> dicMap : dicList) {
            param = new ArrayList<>();
            String busTableColumn = (String) dicMap.get("DIC_NAME");
            String busTableName = (String) dicMap.get("DIC_CODE");
            String jsonColumn = (String) dicMap.get("DIC_DESC");
            String tablePrimaryKeyName = dao.getPrimaryKeyName(busTableName).get(0).toString();
            StringBuffer sql = new StringBuffer();
            // 当字段是JSON格式时，对字段进行处理
            if (StringUtils.isNotEmpty(busTableColumn) && busTableColumn.contains("_JSON")) {
                sql.append(" SELECT C.ITEM_NAME,A.EXE_ID,B." + busTableColumn + " FROM JBPM6_EXECUTION A JOIN ")
                        .append(busTableName);
                sql.append(" B ON A.BUS_RECORDID = B.").append(tablePrimaryKeyName);
                sql.append(" JOIN T_WSBS_SERVICEITEM C ON A.ITEM_CODE = C.ITEM_CODE ");
                sql.append(" WHERE A.RUN_STATUS in(1,2) ");
                if (StringUtils.isNotEmpty(exeId)) {
                    sql.append(" AND ").append(" A.EXE_ID<>?");
                    param.add(exeId);
                }
                sql.append(" union all ");
                sql.append(" SELECT C.ITEM_NAME,A.EXE_ID,B." + busTableColumn + " FROM JBPM6_EXECUTION_EVEHIS A JOIN ")
                        .append(busTableName);
                sql.append(" B ON A.BUS_RECORDID = B.").append(tablePrimaryKeyName);
                sql.append(" JOIN T_WSBS_SERVICEITEM C ON A.ITEM_CODE = C.ITEM_CODE ");
                sql.append(" WHERE A.RUN_STATUS in(1,2) ");
                if (StringUtils.isNotEmpty(exeId)) {
                    sql.append(" AND ").append(" A.EXE_ID<>?");
                    param.add(exeId);
                }
                List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
                listfor: for (Map<String, Object> map : list) {
                    String columnValue = map.get(busTableColumn) == null ? "" : map.get(busTableColumn).toString();
                    if (StringUtils.isNotEmpty(columnValue)) {
                        List<Map> columnlist = JSON.parseArray(columnValue, Map.class);
                        for (Map map2 : columnlist) {
                            String value = map2.get(jsonColumn) == null ? "" : map2.get(jsonColumn).toString();
                            if (StringUtils.isNotEmpty(value) && value.equals(fieldValue)) {
                                bdcdyhList.add(map.get("ITEM_NAME").toString() + "(" + map.get("EXE_ID") + ")");
                                break listfor; // 结束for循环
                            }
                        }
                    }
                }
            } else {
                param = new ArrayList<>();
                sql.append(" SELECT C.ITEM_NAME,A.EXE_ID FROM JBPM6_EXECUTION A JOIN ").append(busTableName);
                sql.append(" B ON A.BUS_RECORDID = B.").append(tablePrimaryKeyName);
                sql.append(" JOIN T_WSBS_SERVICEITEM C ON A.ITEM_CODE = C.ITEM_CODE ");
                sql.append(" WHERE A.RUN_STATUS in(1,2) ");
                sql.append(" AND ").append(busTableColumn).append(" = ? ");
                param.add(fieldValue.toString());
                if (StringUtils.isNotEmpty(exeId)) {
                    sql.append(" AND ").append(" A.EXE_ID<>?");
                    param.add(exeId);
                }
                sql.append(" union all ");
                sql.append(" SELECT C.ITEM_NAME,A.EXE_ID FROM JBPM6_EXECUTION_EVEHIS A JOIN ").append(busTableName);
                sql.append(" B ON A.BUS_RECORDID = B.").append(tablePrimaryKeyName);
                sql.append(" JOIN T_WSBS_SERVICEITEM C ON A.ITEM_CODE = C.ITEM_CODE ");
                sql.append(" WHERE A.RUN_STATUS in(1,2) ");
                sql.append(" AND ").append(busTableColumn).append(" = ? ");
                param.add(fieldValue.toString());
                if (StringUtils.isNotEmpty(exeId)) {
                    sql.append(" AND ").append(" A.EXE_ID<>?");
                    param.add(exeId);
                }
                List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
                if (null != list && list.size() > 0) {
                    StringBuffer exeIds = new StringBuffer();
                    for (Map<String, Object> map : list) {
                        exeIds.append(map.get("EXE_ID")).append("，");
                    }
                    bdcdyhList.add(list.get(0).get("ITEM_NAME") + "(" + exeIds.substring(0, exeIds.length() - 1) + ")");
                }
            }
        }
        if (null != bdcdyhList && bdcdyhList.size() > 0) {
            returnMap.put("success", true);
            returnMap.put("busList", bdcdyhList);
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/11 16:58:00
     * @param
     * @return
     */
    @Override
    public boolean isExistsFlowToBdcdyhRecord(String busTableName, String fieldValue , String busRecordId) {
        boolean flag = false;
        Map<String, Object> dicMap = dictionaryService.get("bdcdyhpdsfzb", busTableName);
        String tablePrimaryKeyName = dao.getPrimaryKeyName(busTableName).get(0).toString();
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        if (dicMap != null) {
            String dicName = StringUtil.getValue(dicMap, "DIC_NAME");
            String dicDesc = StringUtil.getValue(dicMap, "DIC_DESC");
            if (StringUtils.isNotEmpty(dicName) && dicName.contains("_JSON")) {
                sql.append(" SELECT EXE_ID FROM (SELECT EXE_ID,BUS_RECORDID,RUN_STATUS FROM JBPM6_EXECUTION ");
                sql.append(" UNION SELECT EXE_ID,BUS_RECORDID,RUN_STATUS FROM JBPM6_EXECUTION_EVEHIS) WHERE BUS_RECORDID IN ( ");
                sql.append(" SELECT YW_ID FROM ").append(busTableName).append(" WHERE ").append(dicName).append(" LIKE '%");
                sql.append(fieldValue).append("%'");
                if (StringUtils.isNotEmpty(busRecordId)) {
                    sql.append(" AND ").append(tablePrimaryKeyName).append(" != ? ");
                }
                sql.append(" ) ");
                sql.append(" AND RUN_STATUS in (1) ");
                if (StringUtils.isNotEmpty(busRecordId)) {
                    params.add(busRecordId);
                }
                List<Map<String,Object>> bySql = dao.findBySql(sql.toString(), params.toArray(), null);
                if (bySql != null && !bySql.isEmpty()) {
                    listfor: for (Map<String, Object> map : bySql) {
                        String exeId = StringUtil.getValue(map, "EXE_ID");
                        Map<String, Object> busMap = new FlowData(exeId, FlowData.BUS_MAP).getBusMap();
                        String jsonInfo = StringUtil.getValue(busMap, dicName);
                        if (StringUtils.isNotEmpty(jsonInfo)) {
                            List<Map> jsonList = JSON.parseArray(jsonInfo, Map.class);
                            for (Map map2 : jsonList) {
                                String value = StringUtil.getValue(map2, dicDesc);
                                if (StringUtils.isNotEmpty(value) && value.equals(fieldValue)) {
                                    flag = true;
                                    break listfor;
                                }
                            }
                        }
                    }
                }
            } else {
                sql.append(" SELECT EXE_ID FROM (SELECT EXE_ID,BUS_RECORDID,RUN_STATUS FROM JBPM6_EXECUTION ");
                sql.append(" UNION SELECT EXE_ID,BUS_RECORDID,RUN_STATUS FROM JBPM6_EXECUTION_EVEHIS) WHERE BUS_RECORDID IN ( ");
                sql.append(" SELECT YW_ID FROM ").append(busTableName).append(" WHERE ").append(dicName).append(" = ? ");
                if (StringUtils.isNotEmpty(busRecordId)) {
                    sql.append(" AND ").append(tablePrimaryKeyName).append(" != ? ");
                }
                sql.append(" ) ");
                sql.append(" AND RUN_STATUS in (1) ");
                params.add(fieldValue);
                if (StringUtils.isNotEmpty(busRecordId)) {
                    params.add(busRecordId);
                }
                List bySql = dao.findBySql(sql.toString(), params.toArray(), null);
                if (bySql != null && !bySql.isEmpty()) {
                    flag = true;
                }
            }
        }
        return flag;
    }


}
