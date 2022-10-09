/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchange.dao.BusRuleChangeDao;
import net.evecom.platform.flowchange.service.BusRuleChangeService;
/**
 * 描述  监察规则
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busRuleChangeService")
public class BusRuleChangeServiceImpl extends BaseServiceImpl implements BusRuleChangeService{
    /**
     * 所引入的dao
     */
    @Resource
    private BusRuleChangeDao dao;
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
        StringBuffer sql = new StringBuffer("select T.*,A.PROCESS_NAME,B.TACHE_NAME,C.BUS_NAME ");
        sql.append(" from T_LCJC_BUS_RULECONFIG_CHANGE T");
        sql.append(" LEFT JOIN T_LCJC_BUS_PROCESS_CHANGE A ON T.PROCESS_CODE=A.PROCESS_CODE ");
        sql.append(" AND T.APPLY_ID=A.APPLY_ID ");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE_CHANGE B ON A.TACHE_CODE=B.TACHE_CODE AND A.APPLY_ID=B.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL_CHANGE C ON B.BUS_CODE=C.BUS_CODE AND B.APPLY_ID=C.APPLY_ID");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT E ON A.UNIT_CODE=E.DEPART_CODE ");
        sql.append(" WHERE T.IS_SHOW=1");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                 params.toArray(), sqlFilter.getPagingBean());
        return list;
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
     * 描述描述通过变更id及申报号删除隐藏开始过程编码记录
     * @author Toddle Chen
     * @created Oct 10, 2015 5:01:58 PM
     * @param changeId
     * @param applyId
     */
    public void delStartProcessByRuleId(String changeId,String applyId){
        dao.delStartProcessByRuleId(changeId, applyId);
    }
    /**
     * 描述 通过业务专项编码获取所属监察字段是否完整
     * @author Toddle Chen
     * @created Sep 14, 2015 6:11:05 PM
     * @param tacheCode
     * @return
     */    
    public boolean checkColumnByBusCode(String busCode,String applyId){
        return dao.checkColumnByBusCode(busCode, applyId);
    }
    /**
     * 描述通过业务专项获取业务环节
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBus(String busCode,String applyId){
        return dao.findTacheByBus(busCode, applyId);
    }
    /**
     * 描述通过业务环节获取过程编码
     * @author Toddle Chen
     * @created Sep 24, 2015 4:58:40 PM
     * @param tacheCode
     * @return
     */
    public List<Map<String, Object>> findProcessByTache(String tacheCode,String applyId){
        return dao.findProcessByTache(tacheCode, applyId);
    }
}
