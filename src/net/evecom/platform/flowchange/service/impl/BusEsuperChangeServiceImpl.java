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

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchange.dao.BusEsuperChangeDao;
import net.evecom.platform.flowchange.service.BusEsuperChangeService;
/**
 * 描述  监察字段变更
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busEsuperChangeService")
public class BusEsuperChangeServiceImpl extends BaseServiceImpl implements BusEsuperChangeService{
    /**
     * 所引入的dao
     */
    @Resource
    private BusEsuperChangeDao dao;
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
        sql.append(" A.UNIT_CODE,A.APPLY_ID,A.CHANGE_ID,B.TACHE_NAME,C.BUS_NAME,D.STATUS,C.UPDATE_TIME ");
        sql.append(" from T_LCJC_BUS_PROCESS_CHANGE A ");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE_CHANGE B ON A.TACHE_CODE=B.TACHE_CODE AND A.APPLY_ID=B.APPLY_ID ");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL_CHANGE C ON B.BUS_CODE=C.BUS_CODE AND B.APPLY_ID=C.APPLY_ID ");
        sql.append(" LEFT JOIN (select bussys_unitcode,process_code,apply_id,status ");
        sql.append("  from t_lcjc_bus_column_change where bussys_type=2 group by ");
        sql.append(" bussys_unitcode,process_code,apply_id,status) D ");
        sql.append(" ON D.PROCESS_CODE=A.PROCESS_CODE AND D.APPLY_ID = A.APPLY_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT E ON A.UNIT_CODE=E.depart_CODE ");
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
        StringBuffer sql = new StringBuffer("select T.CHANGE_ID,T.COLUMN_NAME,T.FIELD_TYPE,T.BUSSYS_SN ");
        sql.append(" from T_LCJC_BUS_COLUMN_CHANGE T ");
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
     * 描述 更新监察字段
     * @author Toddle Chen
     * @created Aug 6, 2015 8:49:27 PM
     * @param jsonDatas
     */
    public void saveEditColumn(String processCode,String jsonDatas,String applyId){
        dao.saveEditColumn(processCode,jsonDatas,applyId);
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
     * 描述 根据条件获取到监察字段列表
     * @author Toddle Chen
     * @created Aug 7, 2015 11:25:41 AM
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findColumnByProcessCode(String processCode,String applyId){
        return dao.findColumnByProcessCode(processCode, applyId);
    }
}
