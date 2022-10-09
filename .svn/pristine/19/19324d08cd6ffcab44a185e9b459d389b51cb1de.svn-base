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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.BusAuditDao;
import net.evecom.platform.flowchart.dao.BusRuleDao;
import net.evecom.platform.flowchart.service.BusRuleService;
import net.evecom.platform.system.dao.DictionaryDao;
/**
 * 描述  监察规则
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busRuleService")
public class BusRuleServiceImpl extends BaseServiceImpl implements BusRuleService{
    /**
     * 所引入的dao
     */
    @Resource
    private BusRuleDao dao;
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
     * 描述 根据sqlfilter获取到监察规则列表
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,decode(t.status,0,'预审',1,'预审',2,'审核',3,'办结') "
                + " as PROCESS_NAME,"
                + " decode(t.status,0,'普通件办理环节',1,'即办件办理环节',2,'普通件办理环节',3,'即办件办理环节') "
                + "as TACHE_NAME,C.BUS_NAME ");
        sql.append(" from T_LCJC_BUS_RULECONFIG T");
        sql.append(" LEFT JOIN T_LCJC_BUS_PROCESS A ON T.PROCESS_CODE=A.PROCESS_CODE ");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE B ON A.TACHE_CODE=B.TACHE_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL C ON B.BUS_CODE=C.BUS_CODE");
        sql.append(" LEFT JOIN t_msjw_system_department E ON A.UNIT_CODE=E.DEPART_CODE ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                 params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 描述通过业务专项获取业务环节
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBus(String busCode){
        return dao.findTacheByBus(busCode);
    }
    /**
     * 描述通过业务环节获取过程编码
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param tacheCode
     * @return
     */
    public List<Map<String, Object>> findProcessByTache(String tacheCode){
        return dao.findProcessByTache(tacheCode);
    }
    /**
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByRuleId(String ruleId){
        return dao.findMapByRuleId(ruleId);
    }
    /**
     * 描述 更新规则至提交审核状态
     * @author Toddle Chen
     * @created Sep 14, 2015 5:13:33 PM
     * @param ruleId
     * @param itemCode--JCGZPZ
     */
    public void editToAudit(String ruleId,String itemCode){
        if (StringUtils.isNotEmpty(ruleId)) {
            String typeCode = "BUSAUDIT";
            Map<String, Object> ruleMap = dao.findMapByRuleId(ruleId);
            Map<String, Object> dicMap = dictionaryDao.get(typeCode, itemCode);
            
//            String tacheCode = String.valueOf(ruleMap.get("TACHE_CODE"));
            String busCode = String.valueOf(ruleMap.get("BUS_CODE"));
            String unitCode = String.valueOf(ruleMap.get("UNIT_CODE"));
            String auditId = busAuditDao.getAuditIdByItemcode(unitCode, busCode, itemCode);
            //判断是否符合提交审核要求
            if(dao.checkRuleByBusCode(busCode)){
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
     * 描述 通过规则编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessCode(String processCode){
        return dao.findMapByProcessCode(processCode);
    }
    /**
     * 描述通过规则表达式删除隐藏开始过程编码记录
     * @author Toddle Chen
     * @created Oct 10, 2015 3:55:09 PM
     * @param ruleId
     */
    public void delStartProcessByRuleId(String ruleId){
        dao.delStartProcessByRuleId(ruleId);
    }
}
