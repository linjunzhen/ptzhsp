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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.flowchart.dao.BusSpecialDao;
import net.evecom.platform.flowchart.service.BusSpecialService;
import net.evecom.platform.system.dao.DictionaryDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 业务专项
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busSpecialService")
public class BusSpecialServiceImpl extends BaseServiceImpl implements BusSpecialService {
    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(BusSpecialServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private BusSpecialDao dao;

    /**
     * 所引入的dao
     */
    @Resource
    private DictionaryDao dicDao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
     * 描述 根据sqlfilter获取到业务专项列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:17:21 PM
     * @param sqlFilter
     * @return
     * @see net.evecom.platform.flow.service.BusSpecialService#findSpecialBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findSpecialBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,A.DEPART_NAME UNIT_NAME from T_LCJC_BUS_SPECIAL T");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT A ON T.UNIT_CODE=A.DEPART_CODE ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述 根据sqlfilter获取到业务环节列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findTacheBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,A.BUS_NAME from T_LCJC_BUS_TACHE T");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL A ON T.BUS_CODE=A.BUS_CODE ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述 根据sqlfilter获取到过程编码列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findProcessBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,A.TACHE_NAME from T_LCJC_BUS_PROCESS T");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE A ON T.TACHE_CODE=A.TACHE_CODE ");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL B ON B.BUS_CODE=A.BUS_CODE ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述 根据业务编码查询业务环节列表
     * 
     * @author Toddle Chen
     * @created Aug 5, 2015 11:07:34 AM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBuscode(String busCode) {
        return dao.findTacheByBuscode(busCode);
    }

    /**
     * 描述 根据业务编码查询业务对象
     * 
     * @author Toddle Chen
     * @created Aug 8, 2015 10:07:54 PM
     * @param busCode
     * @return
     */
    public Map<String, Object> getBusByBuscode(String busCode) {
        return dao.getBusByBuscode(busCode);
    }

    /**
     * 根据当前用户的所属单位编码查询专项
     * 
     * @author Water Guo
     * @param unitCode
     * @return list
     */
    @Override
    public List<Map<String, Object>> findSysByUnitCode(String unitCode) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_SPECIAL WHERE UNIT_CODE = ?");
        sql.append(" ORDER BY BUS_CODE ASC,TREE_SN ASC ");
        return dao.findBySql(sql.toString(), new String[] { unitCode }, null);
    }

    /**
     * 描述 根据sqlfilter获取到业务专项列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:28 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByUnitCode(String unitCode) {
        return dao.findByUnitCode(unitCode);
    }

    /**
     * 根据单位编码 获取专项编码最大值
     * 
     * @author John Zhang
     * @created 2015-8-31 上午09:48:12
     * @param unitCode
     * @return
     */
    public int getMaxBusCode(String unitCode) {
        StringBuffer sql = new StringBuffer("SELECT BUS_CODE FROM T_LCJC_BUS_SPECIAL WHERE UNIT_CODE = ?");
        sql.append(" ORDER BY BUS_CODE DESC ");
        List<Map<String, Object>> result = dao.findBySql(sql.toString(), new String[] { unitCode }, null);
        if (result.size() == 0) {
            return 1;
        } else {
            String maxBusCode = result.get(0).get("BUS_CODE").toString().substring(unitCode.length());
            return Integer.parseInt(maxBusCode) + 1;
        }
    }

    /**
     * 获取业务环节编码最大值
     * 
     * @author John Zhang
     * @created 2015-8-31 下午03:33:44
     * @param busCode
     * @return
     */
    public int getMaxTacheCode(String busCode) {
        StringBuffer sql = new StringBuffer("SELECT TACHE_CODE FROM T_LCJC_BUS_TACHE WHERE BUS_CODE = ?");
        sql.append(" ORDER BY TACHE_CODE DESC ");
        List<Map<String, Object>> result = dao.findBySql(sql.toString(), new String[] { busCode }, null);
        if (result.size() == 0) {
            return 1;
        } else {
            String tacheCode = result.get(0).get("TACHE_CODE").toString();
            String maxBusCode = tacheCode.substring(tacheCode.length() - 1);
            return Integer.parseInt(maxBusCode) + 1;
        }
    }

    /**
     * 确认审核核对项
     * 
     * @author John Zhang
     * @created 2015-9-16 下午04:37:21
     * @param busCode
     * @param configCode
     */
    public void confirmAudit(String busCode, String configCode) {
        Map<String, Object> audit = null;
        try {
            audit = dao.getByJdbc("T_LCJC_BUS_AUDIT", new String[] { "BUS_CODE", "CONFIG_CODE" }, new Object[] {
                busCode, configCode });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (audit == null) {
            audit = new HashMap<String, Object>();
            Map<String, Object> dic = dicDao.get("BUSAUDIT", configCode);
            Map<String, Object> special = dao.getBusByBuscode(busCode);
            audit.put("BUS_CODE", busCode);
            audit.put("UNIT_CODE", special.get("UNIT_CODE"));
            audit.put("CONFIG_CODE", configCode);
            audit.put("CONFIG_NAME", dic.get("DIC_NAME"));
            audit.put("TREE_SN", dic.get("TREE_SN"));
            audit.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            audit.put("CREATE_USER", AppUtil.getLoginUser().getUsername());
            audit.put("UPDATE_USER", AppUtil.getLoginUser().getUsername());
            audit.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            audit.put("STATUS", "1");
        } else {
            audit.put("STATUS", "1");
        }
        dao.saveOrUpdate(audit, "T_LCJC_BUS_AUDIT", audit.get("AUDIT_ID") == null ? null : audit.get("AUDIT_ID")
                .toString());
    }

    /**
     * like删除数据
     * 
     * @author John Zhang
     * @created 2015-10-21 上午09:30:42
     * @param tableName
     * @param colName
     * @param values
     */
    public void removeByLike(String tableName, String colName, Object[] values) {
        String sql = "delete from " + tableName + " where " + colName + " like ?";
        dao.executeSql(sql, values);
    }

    /**
     * like查询数据
     * 
     * @author John Zhang
     * @created 2015-10-21 上午09:30:42
     * @param tableName
     * @param colName
     * @param values
     */
    public List<Map<String, Object>> getByLike(String tableName, String colName, Object[] values) {
        String sql = "select T.* from " + tableName + " T where T." + colName + " like ?";
        return dao.findBySql(sql, values, null);
    }

    /**
     * 根据专项编码查询业务基本信息
     * 
     * @author John Zhang
     * @created 2015-11-12 上午10:23:28
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findBasicColumn(String busCode, SqlFilter filter) {
        String sql = "select T.*,p.system_name from T_LCJC_BUS_COLUMN T "
                + "left join  t_lcjc_system_bussys p on p.system_code = T.bussys_platcode " + "where T.process_code=? "
                + "and T.bussys_type='1' order by T.bussys_sn asc";
        PagingBean pgb = filter == null?null:filter.getPagingBean();
        return dao.findBySql(sql, new Object[] { busCode }, pgb);
    }

    /**
     * 
     * 根据专项编码获取监察字段
     * 
     * @author John Zhang
     * @created 2015-11-12 下午03:35:20
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findEsuperColumn(String busCode, SqlFilter filter) {
        String sql = "select T.*,p.process_name,ta.tache_name from T_LCJC_BUS_COLUMN T "
                + "left join T_LCJC_BUS_PROCESS p on p.process_code = T.process_code "
                + "left join T_LCJC_BUS_TACHE ta on p.tache_code = ta.tache_code "
                + "where ta.bus_code =? order by ta.TREE_SN asc,p.TREE_SN asc,T.BUSSYS_SN asc";
        PagingBean pgb = filter == null?null:filter.getPagingBean();
        return dao.findBySql(sql, new Object[] { busCode }, pgb);
    }
}
