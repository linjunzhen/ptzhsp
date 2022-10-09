/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.model.TableInfo;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowNodeBean;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.flowchart.dao.BusProcessDao;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 业务过程dao impl
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Repository("busProcessDao")
public class BusProcessDaoImpl extends BaseDaoImpl implements BusProcessDao {
    /**
     * table name
     */
    private static final String TABLE_NAME = "T_LCJC_BUS_PROCESS";

    @Override
    public List queryProcessByTache(String tacheCode) {
        BusProcessInfo pro = null;
        StringBuffer sql = new StringBuffer("select * from ").append(TABLE_NAME);
        sql.append(" U WHERE U.tache_code='" + tacheCode + "'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        if (list.size() > 0) {
            List<BusProcessInfo> flist = new ArrayList<BusProcessInfo>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                pro = new BusProcessInfo();
                pro.setProcessId((String) map.get("process_id"));
                pro.setProcessCode((String) map.get("process_code"));
                pro.setProcessName((String) map.get("process_name"));
                pro.setTacheCode((String) map.get("tache_code"));
                flist.add(pro);
            }
            return flist;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveBatch(List plist, String unitcode, String user, String tacheCode) {
        //删除旧的过程数据
        List oldlist=queryProcessByTache(tacheCode);
        if(oldlist!=null){
            List oldIds=new ArrayList<String>();
            List newIds=new ArrayList<String>();
            for (int i = 0; i < plist.size(); i++) {
                FlowNodeBean pro = (FlowNodeBean) plist.get(i);
                newIds.add(pro.getId());
            }
            for (int i = 0; i < oldlist.size(); i++) {
                BusProcessInfo old=(BusProcessInfo) oldlist.get(i);
                if(!newIds.contains(old.getProcessId())){
                    oldIds.add(old.getProcessId());
                }
            }
            if(oldIds.size()>0){
                this.remove("t_lcjc_bus_process", "process_id", oldIds.toArray());
            }
        }
        
        //插入新的数据记录
        for (int i = 0; i < plist.size(); i++) {
            FlowNodeBean pro = (FlowNodeBean) plist.get(i);
            BusProcessInfo bus = getProcessByCode(pro.getId());
            if (bus != null) {
                Map<String, Object> reqData = new HashMap<String, Object>();
                reqData.put("PROCESS_ID", bus.getProcessId());
                reqData.put("PROCESS_CODE", pro.getId());
                reqData.put("PROCESS_NAME", pro.getText());
                reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("UPDATE_USER", user);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process", bus.getProcessId());
            } else {
                Map<String, Object> reqData = new HashMap<String, Object>();
                reqData.put("PROCESS_CODE", pro.getId());
                reqData.put("PROCESS_NAME", pro.getText());
                reqData.put("TACHE_CODE", tacheCode);
                reqData.put("UNIT_CODE", unitcode);
                reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("CREATE_USER", user);
                reqData.put("UPDATE_USER", user);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process", null);
            }
        }
    }

    @Override
    public void deleteProcessByBus(String busCode) {
        StringBuffer updateSql = new StringBuffer("delete from ").append(" t_lcjc_bus_process "
                + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { busCode });
    }

    @Override
    public BusProcessInfo getProcessByCode(String processCode) {
        StringBuffer sql = new StringBuffer("select * from t_lcjc_bus_process U");
        sql.append(" WHERE U.process_code='" + processCode + "'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        BusProcessInfo flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new BusProcessInfo();
            flow.setProcessCode((String) map.get("PROCESS_CODE"));
            flow.setProcessId((String) map.get("PROCESS_ID"));
            flow.setProcessName((String) map.get("PROCESS_NAME"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setStatus(String.valueOf(map.get("STATUS")));
            flow.setIsMonitorNode((String) map.get("IS_MONITORNODE"));
        }
        return flow;
    }

    @Override
    public void submitAudit(String tacheCode, String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" t_lcjc_bus_process set status=0,update_user=?,update_time=?"
                        + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { userId, date, busCode });
    }

    @Override
    public void cancelNodeAduit(String tacheCode, String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 撤销监察点状态
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" t_lcjc_bus_process set status=0,update_user=?,update_time=?"
                        + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { userId, date, busCode });
        // 撤销业务专项状态
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL set change_flag='-1',update_user=?,update_time=?" + "where bus_code=?");
        this.jdbcTemplate.update(bsql.toString(), new Object[] { userId, date, busCode });
        // 撤销环节状态
        StringBuffer tsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE set status=0,update_user=?,update_time=?" + "where bus_code=?");
        this.jdbcTemplate.update(tsql.toString(), new Object[] { userId, date, busCode });
    }

}
