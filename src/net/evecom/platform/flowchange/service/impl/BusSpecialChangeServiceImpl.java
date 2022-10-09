/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchange.dao.BusAuditChangeDao;
import net.evecom.platform.flowchange.dao.BusSpecialChangeDao;
import net.evecom.platform.flowchange.service.BusSpecialChangeService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 业务专项
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:54:46 PM
 */
@Service("busSpecialChangeService")
public class BusSpecialChangeServiceImpl extends BaseServiceImpl implements BusSpecialChangeService {
    /**
     * 所引入的dao
     */
    @Resource
    private BusSpecialChangeDao dao;
    /**
     * busAuditChangeDao
     */
    @Resource
    private BusAuditChangeDao busAuditChangeDao;

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
        StringBuffer sql = new StringBuffer("select T.*,A.DEPART_NAME UNIT_NAME,app.APPLY_NAME "
                + "from T_LCJC_BUS_SPECIAL_CHANGE T");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT A ON T.UNIT_CODE=A.DEPART_CODE");
        sql.append(" LEFT JOIN T_LCJC_APPLYINFO app ON T.APPLY_ID=app.APPLY_ID");
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
        StringBuffer sql = new StringBuffer("select T.*from T_LCJC_BUS_TACHE_CHANGE T");
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
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_SPECIAL_CHANGE WHERE UNIT_CODE = ?");
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
     * 获取业务环节编码最大值
     * 
     * @author John Zhang
     * @created 2015-8-31 下午03:33:44
     * @param busCode
     * @return
     */
    public int getMaxTacheCode(String busCode) {
        StringBuffer sql = new StringBuffer("SELECT TACHE_CODE FROM T_LCJC_BUS_TACHE_CHANGE WHERE BUS_CODE = ?");
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
     * 获取业务专项正在发起的变更
     * 
     * @author John Zhang
     * @created 2015-9-6 下午04:56:56
     * @param busCode
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findByBusCodeAndUserId(String busCode) {
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM T_LCJC_BUS_SPECIAL_CHANGE WHERE BUS_CODE = ? AND STATUS in ('0','1','2')");
        return dao.findBySql(sql.toString(), new String[] { busCode }, null);
    }

    /**
     * 获取未发起变更的列表
     * 
     * @author John Zhang
     * @created 2015-9-18 上午10:00:22
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findNotChanged(String name) {
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_LCJC_BUS_SPECIAL T " + "WHERE" + " T.BUS_NAME LIKE '%"
                + name + "%' AND" + " T.BUS_ID NOT IN ("
                + "SELECT BUS_ID FROM T_LCJC_BUS_SPECIAL_CHANGE TC WHERE TC.BUS_CODE = T.BUS_CODE "
                + "AND TC.STATUS != 3) AND T.STATUS = '3' and t.VERSION = '2'");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 发起变更
     * 
     * @author John Zhang
     * @created 2015-9-2 上午11:37:25
     * @param formData
     */
    public void goChange(Map<String, Object> specialInfo, String changeCode) {
        SysUser sysUser = AppUtil.getLoginUser();
        String busCode = specialInfo.get("BUS_CODE").toString();
        Map<String, Object> applyInfo = new HashMap<String, Object>();
        applyInfo.put("APPLY_ID", busCode + "." + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmm"));
        applyInfo.put("APPLY_USERID", sysUser.getUserId());
        applyInfo.put("APPLY_USERNAME", sysUser.getUsername());
        applyInfo.put("BUS_UNITCODE", specialInfo.get("UNIT_CODE"));
        applyInfo.put("APPLY_TYPE", "1");
        applyInfo.put("APPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        applyInfo.put("LAST_APPLY_ID", specialInfo.get("APPLY_ID"));
        applyInfo.put("TYPE_STAGE", "-1");
        applyInfo.put("APPLY_NAME", specialInfo.get("APPLY_NAME").toString());
        applyInfo.put("BUS_CODE", busCode);
        this.saveOrUpdateForAssignId(applyInfo, "T_LCJC_APPLYINFO", applyInfo.get("APPLY_ID").toString());
        dao.goChange((String) applyInfo.get("APPLY_ID"), specialInfo.get("BUS_CODE").toString(), changeCode);
        // busAuditChangeDao.changeStatus((String) applyInfo.get("APPLY_ID"),
        // busCode, "0");
    }

    /**
     * 确认变更项
     * 
     * @author John Zhang
     * @created 2015-9-17 下午05:13:22
     * @param applyId
     * @param busCode
     * @param configName
     * @return
     */
    public boolean confirmChange(String applyId, String busCode, String configName) {
        Map<String, Object> audit = this.getByJdbc("T_LCJC_BUS_AUDIT_CHANGE", new String[] { "APPLY_ID", "BUS_CODE",
            "CONFIG_CODE" }, new Object[] { applyId, busCode, configName });
        if (audit == null) {
            return false;
        } else {
            audit.put("STATUS", "1");
            this.saveOrUpdate(audit, "T_LCJC_BUS_AUDIT_CHANGE", audit.get("AUDIT_ID").toString());
            return true;
        }
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
        // String sql = "delete from "+ tableName
        // +" where "+colName+" like ? and APPLY_ID = ?";
        String sql = "update " + tableName + " set IS_VALID='0'" + " where " + colName + " like ? and APPLY_ID = ?";
        dao.executeSql(sql, values);
    }
}
