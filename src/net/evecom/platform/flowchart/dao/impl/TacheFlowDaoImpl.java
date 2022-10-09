/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.flowchart.dao.TacheFlowDao;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.FlowChartInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 监察流程Controller
 * 
 * @author Sundy Sun
 * @version 2.0
 */
@Repository("tacheFlowDao")
public class TacheFlowDaoImpl extends BaseDaoImpl implements TacheFlowDao {

    /**
     * table name
     */
    private static final String TABLE_NAME = "T_LCJC_BUS_TACHE";

    /**
     * 根据业务专项ID查找流程
     */
    @Override
    public List<TacheFlow> getFlowByBusiCode(String busiCode) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer("select * from T_LCJC_BUS_TACHE U");
        sql.append(" WHERE U.bus_code='" + busiCode + "' order by tree_sn");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString());
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
                flow.setUnitCode((String) map.get("unit_code"));
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

    /**
     * 根据流程环节查找对应流程实体
     */
    @Override
    public TacheFlow getFlowByTacheId(String tacheid) {
        StringBuffer sql = new StringBuffer("select * from T_LCJC_BUS_TACHE U");
        sql.append(" WHERE U.tache_id='" + tacheid + "'");
        // TacheFlow flow=(TacheFlow) this.getUniqueBySql(sql.toString(), new
        // Object[] {tacheid});
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString());
        TacheFlow flow = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            flow = new TacheFlow();
            flow.setTacheId((String) map.get("tache_id"));
            flow.setFlowInfo((String) map.get("flow_info"));
            flow.setTacheCode((String) map.get("tache_code"));
            flow.setBusCode((String) map.get("BUSI_CODE"));
            flow.setTreeSn(String.valueOf(map.get("tree_sn")));
            flow.setTacheName((String) map.get("tache_name"));
            flow.setFlowHeight((String) map.get("flow_height"));
        }
        return flow;
    }

    /**
     * 根据流程环节id获取流程对象
     */
    @Override
    public TacheFlow getFlowByTacheCode(String tachecode) {
        StringBuffer sql = new StringBuffer("select * from T_LCJC_BUS_TACHE U");
        sql.append(" WHERE U.tache_code='" + tachecode + "'");
        // TacheFlow flow=(TacheFlow) this.getUniqueBySql(sql.toString(), new
        // Object[] {tachecode});
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString());
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
        }
        return flow;
    }

    /**
     * 更新
     */
    @Override
    public void updateFlowInfo(String tachecode, String flowdata,
            String flowheight) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 先清除掉数据
        // StringBuffer updateSql = new StringBuffer("update ")
        // .append(" T_LCJC_BUS_TACHE set flow_info='" + flowdata
        // + "',flow_height='" + flowheight
        // + "' where tache_code='" + tachecode + "'");
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE set flow_info=?"
                        + ",flow_height=?,update_time=?"
                        + " where tache_code=?");
        // jdbcTemplate.update(
        // "update tb_test1 set name=？,password=？ where id = ?",
        // new PreparedStatementSetter(){
        // @Override
        // public void setValues(PreparedStatement ps) throws SQLException {
        // ps.setString(1, user.getUsername());
        // ps.setString(2, user.getPassword());
        // ps.setInt(3, user.getId());
        // }
        // }
        // );

        this.jdbcTemplate.update(updateSql.toString(), new Object[] { flowdata,
            flowheight, date, tachecode });
    }

    @Override
    public void updateBusiState(String status, String busCode, String busId,
            String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer(
                "update T_LCJC_BUS_SPECIAL set status=?,update_user=?,update_time=?");
        String params = null;
        if (StringUtils.isNotEmpty(busCode)) {
            updateSql.append("   where bus_code=?");
            params = busCode;
        } else {
            updateSql.append("   where bus_id=?");
            params = busId;
        }
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { status,
            userId, date, params });
    }

    @Override
    public BusSpecialInfo getBusByBusCode(String buscode) {
        StringBuffer sql = new StringBuffer(
                "select * from T_LCJC_BUS_SPECIAL U");
        sql.append(" WHERE U.bus_code='" + buscode + "'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString());
        BusSpecialInfo bus = null;
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            bus = new BusSpecialInfo();
            bus.setBusCode((String) map.get("bus_code"));
            bus.setBusName((String) map.get("bus_name"));
            bus.setStatus(Integer.valueOf(map.get("status").toString()));
            bus.setApplyId((String) map.get("apply_id"));
            bus.setUnitCode((String) map.get("unit_code"));
        }
        return bus;
    }

    
    @Override
    public List<TacheFlow> getFlowByBusId(String busid) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer(
                "select U.* from T_LCJC_BUS_TACHE U,T_LCJC_BUS_SPECIAL s");
        sql.append(" WHERE U.bus_code=s.bus_code and s.bus_id='" + busid
                + "' order by U.tree_sn");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString());
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
                flow.setFlowHeight((String) map.get("flow_height"));
                flist.add(flow);
            }
            return flist;
        }
        return null;
    }

    @Override
    public void submitAudit(String tacheCode, String busCode) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE set status=1,update_time=?  where bus_code=?");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { date,
            busCode });
    }

    @Override
    public void flowPassAudit(String state, String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        // 环节状态提交
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_TACHE set status=? where bus_code=?");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { state,
            busCode });
        // 过程点状态
        StringBuffer updateSql2 = new StringBuffer("update  ")
                .append(" t_lcjc_bus_process set status=? "
                        + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql2.toString(), new Object[] { state,
            busCode });
        // 业务专项状态提交
        StringBuffer bsql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_SPECIAL set status=?, change_state=?,change_flag=?,update_user=?,update_time=?"
                        + "where bus_code=?");
        this.jdbcTemplate.update(bsql.toString(), new Object[] { state, state,
            "-1", userId, date, busCode });
    }

}
