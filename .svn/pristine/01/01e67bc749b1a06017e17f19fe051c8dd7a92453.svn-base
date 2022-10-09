/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowNodeBean;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.flowchange.dao.ProcessChangeDao;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

import org.springframework.stereotype.Repository;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Repository("processChangeDao")
public class ProcessChangeDaoImpl extends BaseDaoImpl implements ProcessChangeDao {

    public List queryProcessByTache(String tacheCode, String applyId) {
        BusProcessInfo pro = null;
        StringBuffer sql = new StringBuffer("select * from ").append("t_lcjc_bus_process_change");
        sql.append(" U WHERE U.tache_code='" + tacheCode + "' and apply_id='" + applyId + "'");
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
                pro.setChangeId((String) map.get("change_id"));
                pro.setApplyId((String) map.get("apply_id"));
                flist.add(pro);
            }
            return flist;
        }
        return null;
    }

    @Override
    public void saveBatch(List plist, String unitcode, String user, String tacheCode, String applyId) {
        // 删除旧的多余节点数据
        StringBuffer newIdsStr=new StringBuffer();
        for (int i = 0; i < plist.size(); i++) {
            FlowNodeBean pro = (FlowNodeBean) plist.get(i);
            newIdsStr.append("'").append(pro.getId()).append("',");
        }
        newIdsStr=new StringBuffer(newIdsStr.substring(0, newIdsStr.lastIndexOf(",")));
        StringBuffer sql = new StringBuffer("select * from ").append("t_lcjc_bus_process_change");
        sql.append(" U WHERE U.apply_id='" + applyId + "' and U.tache_code='"+tacheCode
                +"' and process_code not in (" + newIdsStr.toString() + ")");
        List<Map<String, Object>> dellist = jdbcTemplate.queryForList(sql.toString());
        for (int i = 0; i < dellist.size(); i++) {
            this.remove("t_lcjc_bus_process_change", "change_id", new Object[]{dellist.get(i).get("CHANGE_ID")});
        }
        
        
//        List oldlist = queryProcessByTache(tacheCode, applyId);
//        if (oldlist != null) {
//            List oldIds = new ArrayList<String>();
//            List newIds = new ArrayList<String>();
//            for (int i = 0; i < plist.size(); i++) {
//                FlowNodeBean pro = (FlowNodeBean) plist.get(i);
//                newIds.add(pro.getId());
//            }
//            for (int i = 0; i < oldlist.size(); i++) {
//                BusProcessInfo old = (BusProcessInfo) oldlist.get(i);
//                if (!newIds.contains(old.getProcessId())) {
//                    oldIds.add(old.getProcessId());
//                    this.remove("t_lcjc_bus_process_change", new String[] { "process_id", "apply_id" }, new Object[] {
//                            old.getProcessId(), applyId });
//                }
//            }
//        }
        for (int i = 0; i < plist.size(); i++) {
            FlowNodeBean pro = (FlowNodeBean) plist.get(i);
            BusProcessInfo bus = getProcessByCode(pro.getId(), applyId);
            if (bus != null) {
                Map<String, Object> reqData = new HashMap<String, Object>();
                reqData.put("PROCESS_ID", bus.getProcessId());
                reqData.put("PROCESS_CODE", pro.getId());
                reqData.put("PROCESS_NAME", pro.getText());
                reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("UPDATE_USER", user);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process_change", bus.getChangeId());
            } else {
                Map<String, Object> reqData = new HashMap<String, Object>();
                reqData.put("PROCESS_CODE", pro.getId());
                reqData.put("PROCESS_NAME", pro.getText());
                reqData.put("TACHE_CODE", tacheCode);
                reqData.put("UNIT_CODE", unitcode);
                reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("CREATE_USER", user);
                reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                reqData.put("UPDATE_USER", user);
                reqData.put("apply_id", applyId);
                String nextProcessId = UUIDGenerator.getUUID();
                reqData.put("process_id", nextProcessId);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process_change", null);
            }
        }
    }

    @Override
    public void copyMonitorNode(String busCode, String userId, String applyId) {
        StringBuffer sql = new StringBuffer();
        // String nextSeq = UUIDGenerator.getUUID();
        // String date=DateTimeUtil.getStrOfDate(new Date(),
        // "yyyy-MM-dd HH:mm");
        String sid = DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmm");
        //List<Object> params = new ArrayList<Object>();
        // copy过程数据到副本
        sql.append("insert into t_lcjc_bus_process_change(process_id,process_code,process_name,tache_code,")
                .append("status,unit_code,tree_sn,create_time,create_user,is_monitornode,apply_id)")
                .append(" select to_char(process_id)||'" + sid + "',process_code,process_name,tache_code,'0',")
                .append("unit_code,tree_sn,create_time,create_user,is_monitornode,'" + applyId
                        + "' from t_lcjc_bus_process p "
                        + "where p.tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code='" + busCode
                        + "')");
        this.jdbcTemplate.update(sql.toString());
        // copy要素数据到副本
        List<TacheFlow> list = getFlowByBusiCode(busCode);
        for (int i = 0; i < list.size(); i++) {
            sql = new StringBuffer();
            TacheFlow flow = list.get(i);
            sql.append("insert into t_lcjc_bus_ruleconfig_change(rule_id,process_code,super_elements")
                    .append(" ,is_exist,obj_type,judge_conditions,warn_status,is_artificial,rule_desc,")
                    .append("create_time,create_user,update_time,update_user,version,status,judge_desc,apply_id)")
                    .append(" select to_char(rule_id)||'" + sid + "',process_code,super_elements,")
                    .append("is_exist,obj_type,judge_conditions,warn_status,is_artificial,rule_desc,")
                    .append("create_time,create_user,update_time,update_user,version,'0',judge_desc")
                    .append(" ,'" + applyId + "' from t_lcjc_bus_ruleconfig r where r.process_code in "
                            + " (select process_code from t_lcjc_bus_process where tache_code='" + flow.getTacheCode()
                            + "')");
            this.jdbcTemplate.update(sql.toString());
        }
    }

    public List<TacheFlow> getFlowByBusiCode(String busiCode) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer("select * from T_LCJC_BUS_TACHE");
        sql.append(" U WHERE U.bus_code='" + busiCode + "' order by tache_code");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        if (list.size() > 0) {
            List<TacheFlow> flist = new ArrayList<TacheFlow>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                flow = new TacheFlow();
                flow.setTacheId((String) map.get("tache_id"));
                flow.setFlowInfo((String) map.get("flow_info"));
                flow.setTacheCode((String) map.get("tache_code"));
                flow.setBusCode((String) map.get("BUS_CODE"));
                flow.setTreeSn(String.valueOf(map.get("tree_sn")));
                flow.setTacheName((String) map.get("tache_name"));
                flow.setFlowHeight((String) map.get("flow_height"));
                flist.add(flow);
            }
            return flist;
        }
        return null;
    }

    @Override
    public BusProcessInfo getProcessByCode(String processCode, String applyId) {
        StringBuffer sql = new StringBuffer("select * from t_lcjc_bus_process_change U");
        sql.append(" WHERE U.process_code='" + processCode + "' and apply_id='" + applyId + "'");
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
            flow.setChangeId((String) map.get("change_id"));
        }
        return flow;
    }

}
