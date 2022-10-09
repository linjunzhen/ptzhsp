/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchart.dao.AllMaterialsDao;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 allMaterialsDaoImpl 
 * @author Water Guo
 * @created 2015-8-31 下午5:25:26
 */
@Repository("allMaterialsDao")
public class AllMaterialsDaoImpl extends BaseDaoImpl 
          implements AllMaterialsDao {

    /***
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息(历史表)
     * @author Water Guo
     * @created 2015-9-1 上午8:57:23
     * @param applyId
     * @param buscode
     * @return
     */
    public List<TacheFlow> getFlowByApplyId(String applyId, String buscode) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_TACHE_HISTORY U");
        sql.append(" WHERE U.BUS_CODE=? AND U.APPLY_ID = ? ORDER BY TREE_SN ASC,U.BUS_CODE ASC");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString(),new Object[]{buscode,applyId});
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
    
    /***
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息（从变更表中获取相应操作流水的环节信息）
     * @author Water Guo
     * @created 2015-9-1 上午8:57:23
     * @param applyId
     * @param buscode
     * @return
     */
    public List<TacheFlow> getChangeFlowByApplyId(String applyId, String buscode) {
        TacheFlow flow = null;
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_TACHE_CHANGE U");
        sql.append(" WHERE U.BUS_CODE=? AND U.APPLY_ID = ? ORDER BY TREE_SN ASC,U.BUS_CODE ASC");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
                .toString(),new Object[]{buscode,applyId});
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

}
