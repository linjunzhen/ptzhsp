/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.flowchart.dao.BusColumnEsuperDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;
/**
 * 描述 监察字段
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busColumnEsuperDao")
public class BusColumnEsuperDaoImpl extends BaseDaoImpl implements BusColumnEsuperDao{
    /**
     * 描述 通过过程ID获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessID(String processID){
        Map<String, Object> processMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("select A.*,B.TACHE_NAME,B.BUS_CODE,C.BUS_NAME,D.DEPART_NAME UNIT_NAME ");
        sql.append(" from T_LCJC_BUS_PROCESS A");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE B ON A.TACHE_CODE=B.TACHE_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL C ON B.BUS_CODE=C.BUS_CODE");
        sql.append(" LEFT JOIN t_msjw_system_department D ON C.UNIT_CODE=D.DEPART_CODE");
        sql.append(" WHERE A.PROCESS_ID=?");
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
        StringBuffer sql = new StringBuffer("select A.*,B.TACHE_NAME,B.BUS_CODE,C.BUS_NAME,D.DEPART_NAME UNIT_NAME ");
        sql.append(" from T_LCJC_BUS_PROCESS A");
        sql.append(" LEFT JOIN T_LCJC_BUS_TACHE B ON A.TACHE_CODE=B.TACHE_CODE");
        sql.append(" LEFT JOIN T_LCJC_BUS_SPECIAL C ON B.BUS_CODE=C.BUS_CODE");
        sql.append(" LEFT JOIN t_msjw_system_department D ON C.UNIT_CODE=D.DEPART_CODE");
        sql.append(" WHERE A.PROCESS_CODE=?");
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
        sql.append(" from T_LCJC_BUS_COLUMN T ");
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
        sql.append(" from T_LCJC_BUS_COLUMN T WHERE T.PROCESS_CODE=? ");
        return jdbcTemplate.queryForInt(sql.toString(), new Object[]{processCode});
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
     * 描述 通过业务专项编码获取所属监察字段是否完整
     * @author Toddle Chen
     * @created Sep 14, 2015 6:11:05 PM
     * @param tacheCode
     * @return
     */    
    public boolean checkColumnByBusCode(String busCode){
        StringBuffer sql = new StringBuffer("select t.process_code, " );
        sql.append(" (select count(*) from t_lcjc_bus_column a where a.process_code=t.process_code) as cn");
        sql.append(" from t_lcjc_bus_process t where is_monitornode=1 and  tache_code in ");
        sql.append(" (SELECT TACHE_CODE FROM T_LCJC_BUS_TACHE WHERE IS_VALID=1 AND BUS_CODE=? )");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[]{busCode}, null);
        for(Map<String, Object> countMap : list){
            String nproces = String.valueOf(countMap.get("CN"));
            if("0".equals(nproces)){
                return false;
            }
        }
        return true;
    }
    /**
     * 描述 更新监察字段
     * @author Toddle Chen
     * @created Aug 6, 2015 8:49:27 PM
     * @param jsonDatas
     */
    public void saveEditColumn(String processCode,String jsonDatas){
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);        
        String insertedJson = String.valueOf(jsonMap.get("inserted"));
        String updatedJson = String.valueOf(jsonMap.get("updated"));
        String deletedJson = String.valueOf(jsonMap.get("deleted"));
        
        String unitCode = getUnitCode(processCode);
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        if(!"[]".equals(insertedJson)){
            List<Map<String,Object>> insertedList = JSON.parseObject(insertedJson,
                new TypeReference<List<Map<String,Object>>>(){});
            for (Map<String,Object> insertMap : insertedList) {
                insertMap.put("BUSSYS_UNITCODE", unitCode);
                insertMap.put("PROCESS_CODE", processCode);
                insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("CREATE_USER", user.getFullname());
                insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_USER", user.getFullname());
                insertMap.put("IS_SHOW", 0);
                insertMap.put("STATUS", 1);
                insertMap.put("BUSSYS_TYPE", 2);
                int maxSn = getMaxSn(processCode) +1;
                insertMap.put("COLUMN_CODE", "field_"+maxSn);
                this.saveOrUpdate(insertMap, "T_LCJC_BUS_COLUMN", null);
                /*String serialId = UUIDGenerator.getUUID();
                int maxSn = this.getMaxSn(processCode) +1;
                String columnCode = "field_"+maxSn;
                String columnName = String.valueOf(insertMap.get("COLUMN_NAME"));
                Integer bussysSn = Integer.valueOf(String.valueOf(insertMap.get("BUSSYS_SN")));
                StringBuffer sql = new StringBuffer("INSERT INTO T_LCJC_BUS_COLUMN");
                sql.append("(SERIAL_ID,PROCESS_CODE,BUSSYS_TYPE,COLUMN_CODE,COLUMN_NAME,");
                sql.append(" BUSSYS_SN,CREATE_TIME,CREATE_USER)");
                sql.append(" VALUES(?,?,?,?,?,?,?,?)");
                this.executeSql(sql.toString(), new Object[]{serialId,processCode,2,columnCode,columnName,
                bussysSn,DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd HH:mm:ss"),"evecom"});*/
            }
        }
        if(!"[]".equals(updatedJson)){
            List<Map<String,Object>> updatedList = JSON.parseObject(updatedJson,
                new TypeReference<List<Map<String,Object>>>(){});
            for (Map<String,Object> updateMap : updatedList) {
//                updateMap.put("BUSSYS_UNITCODE", unitCode);
//                updateMap.put("PROCESS_CODE", processCode);
                updateMap.put("STATUS", 1);
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", user.getFullname());
                String serialId = String.valueOf(updateMap.get("SERIAL_ID"));
                this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN", serialId);
                /*String serialId = String.valueOf(updateMap.get("SERIAL_ID"));
                String columnName = String.valueOf(updateMap.get("COLUMN_NAME"));
                Integer bussysSn = Integer.valueOf(String.valueOf(updateMap.get("BUSSYS_SN")));
                StringBuffer sql = new StringBuffer("UPDATE T_LCJC_BUS_COLUMN T");
                sql.append(" SET T.COLUMN_NAME=?,BUSSYS_SN=?");
                sql.append(" WHERE T.SERIAL_ID=?");
                this.executeSql(sql.toString(), new Object[]{columnName,bussysSn,serialId});*/
            }
        }
        if(!"[]".equals(deletedJson)){
            List<Map<String,Object>> deletedList = JSON.parseObject(deletedJson,
                new TypeReference<List<Map<String,Object>>>(){});  
            for (Map<String,Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("SERIAL_ID"));
                this.remove("T_LCJC_BUS_COLUMN", "SERIAL_ID", new String[]{serialId});
                /*StringBuffer sql = new StringBuffer("DELETE FROM T_LCJC_BUS_COLUMN T");
                sql.append(" WHERE T.SERIAL_ID=?");
                this.executeSql(sql.toString(), new Object[]{serialId});*/
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
            StringBuffer sql = new StringBuffer("DELETE FROM T_LCJC_BUS_COLUMN");
            sql.append(" WHERE PROCESS_CODE IN(SELECT PROCESS_CODE FROM T_LCJC_BUS_PROCESS ");
            sql.append(" WHERE PROCESS_ID=?");
            sql.append(" )");
            this.executeSql(sql.toString(), new Object[]{changeId});
        }
    }
}
