/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.flowchange.dao.BusEsuperChangeDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;
/**
 * 描述 监察字段变更
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busEsuperChangeDao")
public class BusEsuperChangeDaoImpl extends BaseDaoImpl implements BusEsuperChangeDao{
    /**
     * 描述 通过过程ID获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessID(String processID){
        Map<String, Object> processMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("select A.*,B.TACHE_NAME,C.BUS_NAME,D.depart_name UNIT_NAME ");
        sql.append(" from T_LCJC_BUS_PROCESS_CHANGE A");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE_CHANGE B ON A.TACHE_CODE=B.TACHE_CODE AND A.APPLY_ID=B.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL_CHANGE C ON B.BUS_CODE=C.BUS_CODE AND B.APPLY_ID=C.APPLY_ID");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON C.UNIT_CODE=D.depart_CODE");
        sql.append(" WHERE A.CHANGE_ID=?");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{processID}, null);
        if(list.size()>0){
            processMap = list.get(0);
        }
        return processMap;
    }
    /**
     * 描述 通过过程编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessCode(String processCode){
        Map<String, Object> processMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("select A.*,B.TACHE_CODE,B.TACHE_NAME,C.BUS_CODE, ");
        sql.append(" C.BUS_NAME,D.depart_name UNIT_NAME from T_LCJC_BUS_PROCESS_CHANGE A");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE_CHANGE B ON A.TACHE_CODE=B.TACHE_CODE AND A.APPLY_ID=B.APPLY_ID");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL_CHANGE C ON B.BUS_CODE=C.BUS_CODE AND B.APPLY_ID=C.APPLY_ID");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON C.UNIT_CODE=D.depart_CODE");
        sql.append(" WHERE A.PROCESS_CODE=? ORDER BY A.UPDATE_TIME DESC");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{processCode}, null);
        if(list.size()>0){
            processMap = list.get(0);
        }
        return processMap;
    }
    /**
     * 描述 根据条件获取到监察字段列表
     * @author Toddle Chen
     * @created Aug 7, 2015 11:25:41 AM
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findListByProcessCode(String processCode){
        StringBuffer sql = new StringBuffer("select T.SERIAL_ID,T.COLUMN_CODE,T.COLUMN_NAME ");
        sql.append(" from T_LCJC_BUS_COLUMN_CHANGE T ");
        sql.append(" WHERE T.PROCESS_CODE=? AND T.BUSSYS_TYPE=? ");
        sql.append(" ORDER BY T.BUSSYS_SN ASC");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{processCode,2}, null);
        return list;
    }
    /**
     * 描述 获取最大排序
     * @author Toddle Chen
     * @created Aug 6, 2015 9:32:17 PM
     * @param processCode
     * @return
     */
    public int getMaxSn(String processCode){
        StringBuffer sql = new StringBuffer("select nvl(max(substr(T.COLUMN_CODE,7,2)),0) ");
        sql.append(" from T_LCJC_BUS_COLUMN_CHANGE T WHERE T.PROCESS_CODE=? ");
        return jdbcTemplate.queryForInt(sql.toString(), new Object[]{processCode});
    }
    /**
     * 描述 根据过程编码获取最新申报号
     * @author Toddle Chen
     * @created Sep 21, 2015 3:43:20 PM
     * @param processCode
     * @return
     */
    public String getApplyId(String processCode){
        StringBuffer sql = new StringBuffer("select max(T.APPLY_ID) as APPLYID ");
        sql.append(" from T_LCJC_BUS_PROCESS_CHANGE T WHERE T.PROCESS_CODE=? ");
        Map applyMap = jdbcTemplate.queryForMap(sql.toString(), new Object[]{processCode});
        if(applyMap!= null){
            return String.valueOf(applyMap.get("APPLYID"));
        }
        return null;
    }
    /**
     * 描述 通过过程编码获取单位编码
     * @author Toddle Chen
     * @created Sep 21, 2015 4:14:46 PM
     * @param processCode
     * @return
     */
    public String getUnitCode(String processCode){
        StringBuffer sql = new StringBuffer("select T.UNIT_CODE from T_LCJC_BUS_PROCESS T");
        sql.append(" WHERE T.PROCESS_CODE=? ");
        Map applyMap = jdbcTemplate.queryForMap(sql.toString(), new Object[]{processCode});
        if(applyMap!= null){
            return String.valueOf(applyMap.get("UNIT_CODE"));
        }
        return null;
    }
    /**
     * 描述 通过过程编码获取单位编码
     * @author Toddle Chen
     * @created Sep 21, 2015 4:14:46 PM
     * @param processCode
     * @return
     */
    public String getUnitCode(String processCode,String applyId){
        StringBuffer sql = new StringBuffer("select T.UNIT_CODE from T_LCJC_BUS_PROCESS_CHANGE T");
        sql.append(" WHERE T.PROCESS_CODE=? AND T.APPLY_ID=? ");
        Map applyMap = jdbcTemplate.queryForMap(sql.toString(), new Object[]{processCode,applyId});
        if(applyMap!= null){
            return String.valueOf(applyMap.get("UNIT_CODE"));
        }
        return null;
    }
    /**
     * 描述 更新监察字段
     * @author Toddle Chen
     * @created Aug 6, 2015 8:49:27 PM
     * @param jsonDatas
     */
    public void saveEditColumn(String processCode,String jsonDatas,String applyId){
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);        
        String insertedJson = String.valueOf(jsonMap.get("inserted"));
        String updatedJson = String.valueOf(jsonMap.get("updated"));
        String deletedJson = String.valueOf(jsonMap.get("deleted"));
        
        String unitCode = this.getUnitCode(processCode,applyId);
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        if(!"[]".equals(insertedJson)){
            List<Map<String,Object>> insertedList = JSON.parseObject(insertedJson,
                new TypeReference<List<Map<String,Object>>>(){});
            for (Map<String,Object> insertMap : insertedList) {
                String serialId = UUIDGenerator.getUUID();
                insertMap.put("BUSSYS_UNITCODE", unitCode);
                insertMap.put("SERIAL_ID", serialId);
                insertMap.put("PROCESS_CODE", processCode);
                insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("CREATE_USER", user.getFullname());
                insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_USER", user.getFullname());
                insertMap.put("IS_SHOW", 0);
                insertMap.put("STATUS", 1);
                insertMap.put("BUSSYS_TYPE", 2);
                int maxSn = this.getMaxSn(processCode) +1;
                insertMap.put("COLUMN_CODE", "field_"+maxSn);
//                String applyId = this.getApplyId(processCode);
                insertMap.put("APPLY_ID",applyId);
                this.saveOrUpdate(insertMap, "T_LCJC_BUS_COLUMN_CHANGE", null);
                /*int maxSn = getMaxSn(processCode) +1;
                String columnCode = "field_"+maxSn;
                String columnName = String.valueOf(insertMap.get("COLUMN_NAME"));
                Integer bussysSn = Integer.valueOf(String.valueOf(insertMap.get("BUSSYS_SN")));
                StringBuffer sql = new StringBuffer("INSERT INTO T_LCJC_BUS_COLUMN_CHANGE");
                sql.append("(CHANGE_ID,SERIAL_ID,PROCESS_CODE,BUSSYS_TYPE,COLUMN_CODE,COLUMN_NAME,");
                sql.append(" BUSSYS_SN,APPLY_ID,CREATE_TIME,CREATE_USER)");
                sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?)");
                this.executeSql(sql.toString(), new Object[]{serialId,serialId,processCode,2,
                columnCode,columnName,bussysSn,applyId,
                DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd HH:mm:ss"),"evecom"});*/
            }
        }
        if(!"[]".equals(updatedJson)){
            List<Map<String,Object>> updatedList = JSON.parseObject(updatedJson,
                new TypeReference<List<Map<String,Object>>>(){});
            for (Map<String,Object> updateMap : updatedList) {
//                updateMap.put("BUSSYS_UNITCODE", unitCode);
//                updateMap.put("PROCESS_CODE", processCode);
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", user.getFullname());
                updateMap.put("STATUS", 1);
                String serialId = String.valueOf(updateMap.get("CHANGE_ID"));
                this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN_CHANGE", serialId);
                /*String serialId = String.valueOf(updateMap.get("CHANGE_ID"));
                String columnName = String.valueOf(updateMap.get("COLUMN_NAME"));
                Integer bussysSn = Integer.valueOf(String.valueOf(updateMap.get("BUSSYS_SN")));
                StringBuffer sql = new StringBuffer("UPDATE T_LCJC_BUS_COLUMN_CHANGE T");
                sql.append(" SET T.COLUMN_NAME=?,BUSSYS_SN=?");
                sql.append(" WHERE T.CHANGE_ID=?");
                this.executeSql(sql.toString(), new Object[]{columnName,bussysSn,serialId});*/
            }
        }
        if(!"[]".equals(deletedJson)){
            List<Map<String,Object>> deletedList = JSON.parseObject(deletedJson,
                new TypeReference<List<Map<String,Object>>>(){});  
            for (Map<String,Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("CHANGE_ID"));
                this.remove("T_LCJC_BUS_COLUMN_CHANGE", "CHANGE_ID", new String[]{serialId});
                /*StringBuffer sql = new StringBuffer("DELETE FROM T_LCJC_BUS_COLUMN_CHANGE T");
                sql.append(" WHERE T.CHANGE_ID=?");
                this.executeSql(sql.toString(), new Object[]{serialId});*/
            }
        }
        //变更提交审核
        this.submitColumnChange(processCode, applyId);
    }
    /**
     * 描述 提交审核
     * @author Toddle Chen
     * @created Oct 13, 2015 5:02:41 PM
     * @param processCode
     * @param applyId
     */
    public void submitColumnChange(String processCode,String applyId) {
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        //更新基本信息表中的的状态
        String sql = "SELECT * FROM T_LCJC_BUS_COLUMN_CHANGE WHERE BUSSYS_TYPE = 2 AND PROCESS_CODE=? " +
                " AND APPLY_ID= ? ";
        String SERIAL_ID = null;
        List<Map<String,Object>> list = this.findBySql(sql, new String[] {processCode,applyId},null);        
        for(Map<String,Object> updateMap : list){
            SERIAL_ID = String.valueOf(updateMap.get("CHANGE_ID"));
            if(StringUtils.isNotEmpty(SERIAL_ID)){
                //状态 0：暂存；1：确认；2：待审核；3：审核通过；4：审核不通过；5：关闭；
                updateMap.put("STATUS", "1");
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", user.getUsername());
                this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN_CHANGE", SERIAL_ID);
            }   
        }          
        
    }
    /**
     * 描述 通过过程删除监察字段
     * @author Toddle Chen
     * @created Oct 8, 2015 4:55:37 PM
     * @param selectNames
     */
    public void removeColumnByProcess(String selectNames){
        String[] changeIds = selectNames.split(",");
        for(String changeId : changeIds){
            StringBuffer sql = new StringBuffer("DELETE FROM T_LCJC_BUS_COLUMN_CHANGE");
            sql.append(" WHERE PROCESS_CODE IN(SELECT PROCESS_CODE FROM T_LCJC_BUS_PROCESS_CHANGE ");
            sql.append(" WHERE CHANGE_ID=?");
            sql.append(" )");
            this.executeSql(sql.toString(), new Object[]{changeId});
        }
    }
    /**
     * 描述 通过业务专项编码获取所属监察字段是否完整
     * @author Toddle Chen
     * @created Sep 14, 2015 6:11:05 PM
     * @param tacheCode
     * @return
     */    
    public boolean checkColumnByBusCode(String busCode,String applyId){
        StringBuffer sql = new StringBuffer("select t.process_code,(select count(*) from ");
        sql.append(" t_lcjc_bus_column_change a where a.process_code=t.process_code and a.apply_id=t.apply_id) ");
        sql.append(" as cn from t_lcjc_bus_process_change t where is_monitornode=1 and apply_id=? ");
        sql.append(" and tache_code in (SELECT TACHE_CODE FROM T_LCJC_BUS_TACHE_CHANGE ");
        sql.append(" WHERE IS_VALID=1 AND BUS_CODE=? AND APPLY_ID=? ) ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{applyId,busCode,applyId}, null);
        for(Map<String, Object> countMap : list){
            String nproces = String.valueOf(countMap.get("CN"));
            if("0".equals(nproces)){
                return false;
            }
        }
        return true;
    }
    /**
     * 描述 根据条件获取到监察字段列表
     * @author Toddle Chen
     * @created Aug 7, 2015 11:25:41 AM
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findColumnByProcessCode(String processCode,String applyId){
        StringBuffer sql = new StringBuffer("select T.SERIAL_ID,T.COLUMN_CODE,T.COLUMN_NAME ");
        sql.append(" from T_LCJC_BUS_COLUMN_CHANGE T ");
        sql.append(" WHERE T.PROCESS_CODE=? AND T.BUSSYS_TYPE=? AND T.APPLY_ID=? ");
        sql.append(" ORDER BY T.BUSSYS_SN ASC");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{processCode,2,applyId}, null);
        return list;
    }
}
