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

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowNodeBean;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.flowchange.dao.FlowChangeDao;
import net.evecom.platform.flowchange.model.FlowChangeView;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Repository("flowChangeDao")
public class FlowChangeDaoImpl extends BaseDaoImpl implements FlowChangeDao {
    /**
     * VIEW name
     */
    private static final String VIEW_NAME = "view_tache_change";
    /**
     * table name
     */
    private static final String TABLE_NAME = "T_LCJC_BUS_TACHE_CHANGE";

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.evecom.platform.flowchange.dao.FlowChangeDao#getFlowChange(java.lang
     * .String)
     */
    @Override
    public FlowChangeView getFlowChange(String tachecode) {
        StringBuffer sql = new StringBuffer("select * from view_tache_change U");
        sql.append(" WHERE U.tache_code='" + tachecode + "'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        FlowChangeView flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new FlowChangeView();
            flow.setTacheId((String) map.get("tache_id"));
            flow.setFlowInfo((String) map.get("flow_info"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setBusCode((String) map.get("BUSI_CODE"));
            flow.setTreeSn((Integer) map.get("tree_sn"));
            flow.setTacheName((String) map.get("tache_name"));
            flow.setFlowHeight((String) map.get("flow_height"));
            flow.setOldFlow((String) map.get("old_flow"));
        }
        return flow;
    }

    @Override
    public void updateFlowInfo(String tacheId, String flowdata, String flowheight) {
        StringBuffer updateSql = new StringBuffer("update ").append(TABLE_NAME).append(
                " set flow_info='" + flowdata + "',flow_height='" + flowheight + "' where tache_id='" + tacheId + "'");
        this.jdbcTemplate.update(updateSql.toString());
    }

    @Override
    public List<TacheFlow> getFlowByBusiCode(String busiCode, String applyId) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer("select * from ").append(TABLE_NAME);
        sql.append(" U WHERE U.bus_code='" + busiCode + "' and apply_id='" + applyId + "' order by tree_sn");
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
                flow.setApplyId((String) map.get("apply_id"));
                String tname=flow.getTacheName();
                int x=i*135+20;
                if(i==0){x=10;}
                String html=
                    "<tspan x='"+x+"' y=26 class='abbreviation'>";
                String html2=
                    "<tspan x='"+x+"' y=42 class='abbreviation'>";
                if(tname.length()<9){
                    html+=tname;
                }else if(tname.length()<17){
                    html+=tname.substring(0, 8)+"</tspan>";
                    html+=html2+tname.substring(8,tname.length());
                }else{
                    html="<tspan x='"+x+"' y=20 class='abbreviation'>";
                    html+=tname.substring(0, 8)+"</tspan>";
                    html+="<tspan x='"+x+"' y=36 class='abbreviation' >";
                    html+=tname.substring(8,16)+"</tspan>";
                    html+="<tspan x='"+x+"' y=51 class='abbreviation' >";
                    html+=tname.substring(16, tname.length()<24?tname.length():24);
                }
                html+="</tspan>";
                flow.setNameHtml(html);
                flist.add(flow);
            }
            return flist;
        }
        return null;
    }

    @Override
    public TacheFlow getFlowByTacheCode(String tachecode, String applyId) {
        StringBuffer sql = new StringBuffer("select * from ").append(TABLE_NAME);
        sql.append(" U WHERE U.tache_code='" + tachecode + "' and apply_id='" + applyId + "'");
        // TacheFlow flow=(TacheFlow) this.getUniqueBySql(sql.toString(), new
        // Object[] {tachecode});
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        TacheFlow flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new TacheFlow();
            flow.setTacheId((String) map.get("tache_id"));
            flow.setFlowInfo((String) map.get("flow_info"));
            flow.setFlowSvg((String) map.get("flow_svg"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setBusCode((String) map.get("BUS_CODE"));
            flow.setTreeSn(String.valueOf(map.get("tree_sn")));
            flow.setTacheName((String) map.get("tache_name"));
            flow.setFlowHeight((String) map.get("flow_height"));
            flow.setApplyId((String) map.get("apply_id"));
        }
        return flow;
    }

    @Override
    public List<TacheFlow> getChangeFlowByBusId(String busId, String applyId) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer("select U.* from T_LCJC_BUS_TACHE_change U,T_LCJC_BUS_SPECIAL_CHANGE s");
        sql.append(" WHERE U.bus_code=s.bus_code and s.apply_id=U.apply_id and s.bus_id='" + busId 
                + "' and U.apply_id='" + applyId
                + "' order by U.tree_sn");
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
                flow.setApplyId((String) map.get("apply_id"));
                String tname=flow.getTacheName();
                int x=i*135+20;
                if(i==0){x=10;}
                String html=
                    "<tspan x='"+x+"' y=26 class='abbreviation'>";
                String html2=
                    "<tspan x='"+x+"' y=42 class='abbreviation'>";
                if(tname.length()<9){
                    html+=tname;
                }else if(tname.length()<17){
                    html+=tname.substring(0, 8)+"</tspan>";
                    html+=html2+tname.substring(8,tname.length());
                }else{
                    html="<tspan x='"+x+"' y=20 class='abbreviation'>";
                    html+=tname.substring(0, 8)+"</tspan>";
                    html+="<tspan x='"+x+"' y=36 class='abbreviation' >";
                    html+=tname.substring(8,16)+"</tspan>";
                    html+="<tspan x='"+x+"' y=51 class='abbreviation' >";
                    html+=tname.substring(16, tname.length()<24?tname.length():24);
                }
                html+="</tspan>";
                flow.setNameHtml(html);
                flist.add(flow);
            }
            return flist;
        }
        return null;
    }

    @Override
    public void submitAudit(String tacheCode, String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 环节状态提交
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE_CHANGE set status=2  where bus_code=?");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { busCode });
        // 业务专项状态提交
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL_change set change_state=2,update_user=?,update_time=?"
                        + "where bus_code=?");
        this.jdbcTemplate.update(bsql.toString(), new Object[] { userId, date, busCode });
    }

    @Override
    public void endAudit(String state, String applyId, String userId, String busCode, String busName) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 环节状态副本提交
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE_CHANGE set status=? where apply_id=?");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { state, applyId });

        // 业务专项副本状态提交
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL_CHANGE set status=?, change_flag=?,update_user=?,update_time=?"
                        + "where apply_id=?");
        this.jdbcTemplate.update(bsql.toString(), new Object[] { state, "-1", userId, date, applyId });
        // 更新业务专项信息
        StringBuffer bsql2 = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL set change_flag=?,update_user=?,update_time=?,apply_id=? "
                        + " where bus_code=?");
        this.jdbcTemplate.update(bsql2.toString(), new Object[] { "-1", userId, date, applyId, busCode });
        // 删除旧表的环节数据
        this.remove("t_lcjc_bus_tache", "bus_code", new Object[] { busCode });
        // 将新的环节数据覆盖到正式表
        StringBuffer sql = new StringBuffer();
        sql.append("insert into t_lcjc_bus_tache select * from t_lcjc_bus_tache_change").append(
                " where apply_id='" + applyId + "'");
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void monitorNodePassAudit(String state, String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // //环节状态提交
        // StringBuffer updateSql = new StringBuffer("update ")
        // .append(" T_LCJC_BUS_TACHE set status=? where bus_code=?");
        // this.jdbcTemplate.update(updateSql.toString(), new Object[]
        // {state,busCode });
        // 过程点状态
        StringBuffer updateSql2 = new StringBuffer("update  ").append(" t_lcjc_bus_process set status=? "
                + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql2.toString(), new Object[] { state, busCode });
        // 业务专项状态提交
        int num = Integer.parseInt(state) + 3;
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL set change_state=?,change_flag=?,update_user=?,update_time=?"
                        + "where bus_code=?");
        this.jdbcTemplate.update(bsql.toString(), new Object[] { num, "-1", userId, date, busCode });
    }

    @Override
    public void coverOldTaches(String applyId) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into t_lcjc_bus_tache select * from t_lcjc_bus_tache_change").append(
                " where apply_id='" + applyId + "'");
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void copyBusItem(String busCode, String date) {
        StringBuffer sql = new StringBuffer();
        // String nextSeq = UUIDGenerator.getUUID();
        String applyId = busCode + "." + date;
        String busId = busCode + date;
        sql.append("insert into T_LCJC_BUS_SPECIAL_change(bus_id,bus_code,Bus_Name,Unit_Code,")
                .append("Tree_Sn,Create_Time,Create_User,status,Change_Flag,apply_id)")
                .append(" select '" + busId + "',bus_code,Bus_Name,Unit_Code,Tree_Sn,Create_Time,Create_User")
                .append(" ,'0','1','" + applyId + "' from T_LCJC_BUS_SPECIAL t ")
                .append(" where bus_code='" + busCode + "'");
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void copyTaches(String busCode, String date) {
        StringBuffer sql = new StringBuffer();
        // sql.append("insert into t_lcjc_bus_tache_change select * from t_lcjc_bus_tache")
        // .append(" where bus_code='"+busCode+"'");
        // String nextSeq = UUIDGenerator.getUUID();
        String applyId = busCode + "." + date;
        sql.append("insert into t_lcjc_bus_tache_change(tache_id,tache_code,tache_name,bus_code,")
                .append("unit_code,flow_info,tree_sn,create_date,create_user,flow_height,status,apply_id)")
                .append(" select to_char(tache_id)||'" + date
                        + "',tache_code,tache_name,bus_code,unit_code,flow_info,tree_sn,")
                .append(" create_time,create_user,flow_height,'0','" + applyId + "' from t_lcjc_bus_tache t ")
                .append(" where bus_code='" + busCode + "'");
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void applyAuditPass(String applyId, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 环节状态提交
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE_CHANGE set status=1  where applyId=?");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { applyId });
        // 业务专项状态提交
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL_CHANGE set change_state=1,update_user=?,update_time=?"
                        + "where apply_id=?");
        this.jdbcTemplate.update(bsql.toString(), new Object[] { userId, date, applyId });
    }

    @Override
    public void saveProcessBatch(List plist, String unitcode, String user, String tacheCode) {
        // StringBuffer sql = new StringBuffer();
        // sql
        // .append("insert into t_lcjc_bus_process (PROCESS_CODE, PROCESS_NAME,"
        // + " TACHE_CODE, UNIT_CODE, CREATE_TIME, CREATE_USER, STATUS)");
        // sql.append(" values(?,?,?,?,?,?,-1)");
        // StringBuffer updateSql = new StringBuffer();
        // updateSql
        // .append(
        // "update t_lcjc_bus_process set process_name=?,update_time=?,update_user=?")
        // .append(" where process_code=?");
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
                reqData.put("CREATE_USER", user);
                this.saveOrUpdate(reqData, "t_lcjc_bus_process", null);
            }
        }
    }

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

}
