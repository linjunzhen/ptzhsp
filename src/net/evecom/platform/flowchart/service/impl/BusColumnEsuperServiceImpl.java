/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.BusAuditDao;
import net.evecom.platform.flowchart.dao.BusColumnEsuperDao;
import net.evecom.platform.flowchart.service.BusColumnEsuperService;
import net.evecom.platform.system.dao.DictionaryDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
/**
 * 描述  监察字段
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busColumnEsuperService")
public class BusColumnEsuperServiceImpl extends BaseServiceImpl implements BusColumnEsuperService{
    /**
     * 所引入的dao
     */
    @Resource
    private BusColumnEsuperDao dao;
    /**
     * 提交审核dao
     */
    @Resource
    private BusAuditDao busAuditDao;
    /**
     * 字典dao
     */
    @Resource
    private DictionaryDao dictionaryDao;
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
     * 描述 根据sqlfilter获取到监察字段列表
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select A.PROCESS_ID,A.PROCESS_CODE,A.PROCESS_NAME,A.TACHE_CODE, ");
        sql.append(" A.UNIT_CODE,B.TACHE_NAME,C.BUS_NAME,D.STATUS,C.UPDATE_TIME ");
        sql.append(" from T_LCJC_BUS_PROCESS A");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE B ON A.TACHE_CODE=B.TACHE_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL C ON B.BUS_CODE=C.BUS_CODE");
        sql.append(" LEFT JOIN (select bussys_unitcode,bussys_platcode,process_code,status ");
        sql.append("  from t_lcjc_bus_column where bussys_type=2 group by bussys_unitcode, ");
        sql.append(" bussys_platcode,process_code,status) D ON D.PROCESS_CODE=A.PROCESS_CODE ");
        sql.append(" LEFT JOIN t_msjw_system_department E ON A.UNIT_CODE=E.DEPART_CODE ");
        sql.append(" where A.IS_MONITORNODE=1");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                 params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 描述 根据sqlfilter获取到监察字段列表
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findColumnBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.SERIAL_ID,T.COLUMN_NAME,T.FIELD_TYPE,T.BUSSYS_SN ");
        sql.append(" from T_LCJC_BUS_COLUMN T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                 params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 描述 根据条件获取到监察字段列表
     * @author Toddle Chen
     * @created Aug 7, 2015 11:25:41 AM
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findListByProcessCode(String processCode){
        return dao.findListByProcessCode(processCode);
    }
    /**
     * 描述 通过过程ID获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessID(String processID){
        return dao.findMapByProcessID(processID);
    }
    /**
     * 描述 通过过程编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessCode(String processCode){
        return dao.findMapByProcessCode(processCode);
    }
    /**
     * 描述 保存更新提交审核项目表
     * @author Toddle Chen
     * @created Sep 14, 2015 11:08:05 AM
     * @param processCode
     * @param itemCode
     */
    public void editToAudit(String processCode,String itemCode){
        if (StringUtils.isNotBlank(processCode)) {
            String typeCode = "BUSAUDIT";
            Map<String, Object> processMap = dao.findMapByProcessCode(processCode);
            Map<String, Object> dicMap = dictionaryDao.get(typeCode, itemCode);
            
            String unitCode = String.valueOf(processMap.get("UNIT_CODE"));
            String busCode = String.valueOf(processMap.get("BUS_CODE"));
//            String tacheCode = String.valueOf(processMap.get("TACHE_CODE"));
            String auditId = busAuditDao.getAuditIdByItemcode(unitCode, busCode, itemCode);
            
            //判断是否符合提交审核要求
            if(dao.checkColumnByBusCode(busCode)){
                //构建表数据
                Map<String, Object> auditInfo = new HashMap<String, Object>();
                auditInfo.put("BUS_CODE", busCode);
                auditInfo.put("UNIT_CODE", unitCode);
                auditInfo.put("CONFIG_CODE", itemCode);
                auditInfo.put("CONFIG_NAME", dicMap.get("DIC_NAME"));
                auditInfo.put("TREE_SN", dicMap.get("DIC_SN"));
                auditInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                auditInfo.put("CREATE_USER", "admin");
                auditInfo.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                auditInfo.put("UPDATE_USER", "evecom");
                auditInfo.put("STATUS", "1");            
                this.saveOrUpdate(auditInfo, "T_LCJC_BUS_AUDIT", auditId);
            }
        }        
    }
    /**
     * 描述 更新监察字段
     * @author Toddle Chen
     * @created Aug 6, 2015 8:49:27 PM
     * @param jsonDatas
     */
    public void saveEditColumn(String processCode,String jsonDatas){
        dao.saveEditColumn(processCode,jsonDatas);
    }
    /**
     * 描述 通过过程删除监察字段
     * @author Toddle Chen
     * @created Oct 8, 2015 4:55:37 PM
     * @param selectNames
     */
    public void removeColumnByProcess(String selectNames){
        dao.removeColumnByProcess(selectNames);
    }
}
