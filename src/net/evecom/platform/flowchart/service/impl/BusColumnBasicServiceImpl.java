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
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.flowchart.dao.BusColumnBasicDao;
import net.evecom.platform.flowchart.service.BusColumnBasicService;
import net.evecom.platform.flowchart.service.BusSpecialService;
import net.evecom.platform.flowchange.service.BusSpecialChangeService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述：基本信息字段配置ServiceImpl
 * 
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-08-05
 */
@Service("busColumnBasicService")
public class BusColumnBasicServiceImpl extends BaseServiceImpl implements BusColumnBasicService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusColumnBasicServiceImpl.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * busSpecialService
     */
    @Resource
    private BusSpecialService busSpecialService;
    
    /**
     * busSpecialChangeService
     */
    @Resource
    private BusSpecialChangeService busSpecialChangeService; 
    
    /**
     * @Resource BusColumnBasicDao
     */
    @Resource
    private BusColumnBasicDao dao;

    /**
     * @Override GenericDao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /***
     * @Override datagrid
     */
    @Override
    public List<Map<String, Object>> datagrid(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select tt.id,d.depart_name unit_name,p.system_name,s.bus_name"
                + ",tt.bussys_unitcode, ");
        sql.append("tt.bussys_platcode,tt.process_code,tt.status from ( ");
        sql.append("select t.bussys_unitcode||'.'||t.bussys_platcode||'.'||t.process_code as ID, ");
        sql.append("t.bussys_unitcode,t.bussys_platcode,t.process_code,t.status ");
        sql.append("from t_lcjc_bus_column t where t.bussys_type = 1 ");
        sql.append("group by t.bussys_unitcode,t.bussys_platcode,t.process_code,t.status ");
        sql.append(") tt ");
        sql.append("inner join t_msjw_system_department d on tt.bussys_unitcode = d.depart_code ");
        sql.append("inner join t_lcjc_system_bussys p on tt.bussys_platcode = p.system_code ");
        sql.append("inner join t_lcjc_bus_special s on tt.process_code = s.bus_code ");
        //判断当前登录的权限
        SysUser curLoginUser = AppUtil.getLoginUser();
        //根据专项的归属进行权限约束
        if(!SysUser.ADMIN_RESKEY.equals(curLoginUser.getResKeys())){
            sql.append(" WHERE TT.process_code like '").append(curLoginUser.getDepCode()).append("%' ");
        }        
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }
    
    
    /**
     * 描述 获取基本信息字段变更的列表
     * @author Water Guo
     * @created 2015-9-18 下午5:07:58
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> columnAlterDatagrid(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select tt.id,d.depart_name unit_name,s.system_name," +
                "t1.bus_name,tt.bussys_platcode,");
        sql.append(" tt.process_code,tt.status,tt.apply_id ");
        sql.append(" from t_lcjc_bus_special_change t1 inner join (");
        sql.append(" select t.apply_id,t.bussys_unitcode || '.' || t.bussys_platcode || '.' ||");
        sql.append(" t.process_code as ID,t.bussys_unitcode,t.bussys_platcode, ");
        sql.append(" t.process_code,t.status ");
        sql.append(" from t_lcjc_bus_column_change t ");
        sql.append(" where t.bussys_type = 1 ");
        sql.append(" group by t.apply_id,t.bussys_unitcode,t.bussys_platcode,t.process_code,t.status ");
        sql.append(" ) tt on t1.bus_code = tt.process_code and t1.apply_id=tt.apply_id ");
        sql.append(" inner join t_lcjc_system_bussys s on tt.bussys_platcode  =  s.system_code ");
        sql.append(" inner join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_code = tt.bussys_unitcode ");
        
        //判断当前登录的权限
        SysUser curLoginUser = AppUtil.getLoginUser();
        //根据专项的归属进行权限约束
        if(!SysUser.ADMIN_RESKEY.equals(curLoginUser.getResKeys())){
            sql.append(" WHERE TT.process_code like '").append(curLoginUser.getDepCode()).append("%' ");
        }
        
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述 @Override listByProcessCode
     * @author Water Guo
     * @created 2015-8-18 上午11:17:46
     * @param processCode
     * @return
     */
    @Override
    public List<Map<String, Object>> listByProcessCode(String processCode) {
        StringBuffer sql = new StringBuffer("SELECT L.* FROM T_LCJC_BUS_COLUMN L WHERE L.BUSSYS_TYPE = 1 ");
        sql.append(" AND L.PROCESS_CODE = ? ORDER BY L.BUSSYS_SN ASC,L.CREATE_TIME DESC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new String[]{processCode}, null);
        return list;
    }

    /**
     * 描述 (业务变更)根据专项编码和系统平台编码查询字段
     * @author Water Guo
     * @created 2015-9-22 上午10:45:22
     * @param processCode
     * @param platCode
     * @return
     */
    @Override
    public List<Map<String, Object>> listChangeColumsByBusAndPlatCode(String processCode,
            String platCode,String applyId) {
        StringBuffer sql = new StringBuffer("SELECT L.* FROM T_LCJC_BUS_COLUMN_CHANGE L WHERE L.BUSSYS_TYPE = 1 ");
        sql.append(" AND L.PROCESS_CODE = ? AND BUSSYS_PLATCODE = ? and APPLY_ID= ? ");
        sql.append(" ORDER BY L.BUSSYS_SN ASC,L.CREATE_TIME DESC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new String[]{processCode,platCode,applyId}, 
            null);
        return list;
    }

    /***
     * 描述 : 针对可编剧表格的更新与保存
     * @author Water Guo
     * @created 2015-8-18 下午3:10:54
     * @param variables
     */
    public void saveOrUpdate(Map<String, Object> variables) {
        //字段基本信息
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String,Object> formMap = new HashMap<String, Object>();
        for(String str : formDatas.split("&")){
            String[] strs = str.split("=");
            if(strs.length > 1){
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String processCode = String.valueOf(formMap.get("PROCESS_CODE"));
        String bussysUnitcode = String.valueOf(formMap.get("BUSSYS_UNITCODE"));
        String bussysPlatcode = String.valueOf(formMap.get("BUSSYS_PLATCODE"));
        if(StringUtils.isEmpty(processCode) || "null".equals(processCode)){
            log.error("基本信息字段新增修改：专项编码为空！ERROR！");
        }
        //可编剧表格的信息
        String jsonDatas = String.valueOf(variables.get("jsonDatas")); 
        if(!"null".equals(jsonDatas) && StringUtils.isNotEmpty(jsonDatas)){
            Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);
            String insertedJson = String.valueOf(jsonMap.get("inserted"));
            String updatedJson = String.valueOf(jsonMap.get("updated"));
            String deletedJson = String.valueOf(jsonMap.get("deleted"));
            //当前用户信息
            SysUser user = AppUtil.getLoginUser();
            //获取该专项编码的基本信息字段的最大MaxSn
            int maxSn = this.getMaxSn(processCode,bussysPlatcode,"T_LCJC_BUS_COLUMN") +1;
            //新增
            if(!"[]".equals(insertedJson)){
                List<Map<String,Object>> insertedList = JSON.parseObject(insertedJson,
                        new TypeReference<List<Map<String,Object>>>(){});
                for (Map<String,Object> insertMap : insertedList) {
                    insertMap.put("BUSSYS_UNITCODE", bussysUnitcode);
                    insertMap.put("BUSSYS_PLATCODE", bussysPlatcode);
                    insertMap.put("PROCESS_CODE", processCode);
                    insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    insertMap.put("CREATE_USER", user.getFullname());
                    insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    insertMap.put("UPDATE_USER", user.getFullname());
                    insertMap.put("STATUS", 0);
                    insertMap.put("BUSSYS_TYPE", 1);
                    //TODO maxSn序列
                    insertMap.put("COLUMN_CODE", "field_"+maxSn++);
                    String recordId =  this.saveOrUpdate(insertMap, "T_LCJC_BUS_COLUMN", null);
                    sysLogService.saveLog("新增了ID为[" + recordId + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_ADD);
                }
            }
            //修改
            if(!"[]".equals(updatedJson)){
                List<Map<String,Object>> updatedList = JSON.parseObject(updatedJson,
                        new TypeReference<List<Map<String,Object>>>(){});
                for (Map<String,Object> updateMap : updatedList) {
                    updateMap.put("BUSSYS_UNITCODE", bussysUnitcode);
                    updateMap.put("BUSSYS_PLATCODE", bussysPlatcode);
                    updateMap.put("PROCESS_CODE", processCode);
                    updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    updateMap.put("UPDATE_USER", user.getFullname());
                    String serialId = String.valueOf(updateMap.get("SERIAL_ID"));
                    String recordId =  this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN", serialId);
                    sysLogService.saveLog("更新了ID为[" + recordId + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_EDIT);
                }
            }
            //删除
            if(!"[]".equals(deletedJson)){
                List<Map<String,Object>> deletedList = JSON.parseObject(deletedJson,
                        new TypeReference<List<Map<String,Object>>>(){});  
                for (Map<String,Object> deleteMap : deletedList) {
                    String serialId = String.valueOf(deleteMap.get("SERIAL_ID"));
                    this.remove("T_LCJC_BUS_COLUMN", "SERIAL_ID", new String[]{serialId});
                    sysLogService.saveLog("删除了ID为[" + serialId + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_DEL);
                }
            }
        }else{
            log.info("-->可编剧表格的信息:未操作可编辑表格！！<--");
        }
        
    }
    
    
    /**
     * 描述 （业务变更）变更的保存
     * @author Water Guo
     * @created 2015-9-22 上午11:07:22
     * @param variables
     */
    @Override
    public void changeSaveOrUpdate(Map<String, Object> variables) {
        //字段基本信息
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String,Object> formMap = new HashMap<String, Object>();
        for(String str : formDatas.split("&")){
            String[] strs = str.split("=");
            if(strs.length > 1){
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String processCode = String.valueOf(formMap.get("PROCESS_CODE"));
        String bussysUnitcode = String.valueOf(formMap.get("BUSSYS_UNITCODE"));
        String bussysPlatcode = String.valueOf(formMap.get("BUSSYS_PLATCODE"));
        String applyId = String.valueOf(formMap.get("applyId"));
        if(StringUtils.isEmpty(processCode) || "null".equals(processCode)){
            log.error("基本信息字段变更：专项编码为空！ERROR！");
        }
        if(StringUtils.isEmpty(bussysUnitcode) || "null".equals(bussysUnitcode)){
            log.error("基本信息字段变更：单位编码为空！ERROR！");
        }
        if(StringUtils.isEmpty(bussysPlatcode) || "null".equals(bussysPlatcode)){
            log.error("基本信息字段变更：系统平台编码为空！ERROR！");
        }
        if(StringUtils.isEmpty(applyId) || "null".equals(applyId)){
            log.error("基本信息字段变更：操作申报号为空！ERROR！");
        }
        //可编剧表格的信息
        String jsonDatas = String.valueOf(variables.get("jsonDatas")); 
        if(StringUtils.isNotEmpty(jsonDatas) && !"null".equals(jsonDatas)){
            Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);
            String insertedJson = String.valueOf(jsonMap.get("inserted"));
            String updatedJson = String.valueOf(jsonMap.get("updated"));
            String deletedJson = String.valueOf(jsonMap.get("deleted"));
            //当前用户信息
            SysUser user = AppUtil.getLoginUser();
            //获取该专项编码的基本信息字段的最大MaxSn
            int maxSn = this.getMaxSn(processCode,bussysPlatcode,"T_LCJC_BUS_COLUMN_CHANGE") +1;
            //新增
            if(!"[]".equals(insertedJson)){
                List<Map<String,Object>> insertedList = JSON.parseObject(insertedJson,
                        new TypeReference<List<Map<String,Object>>>(){});
                for (Map<String,Object> insertMap : insertedList) {
                    insertMap.put("SERIAL_ID", UUID.randomUUID().toString());
                    insertMap.put("BUSSYS_UNITCODE", bussysUnitcode);
                    insertMap.put("BUSSYS_PLATCODE", bussysPlatcode);
                    insertMap.put("PROCESS_CODE", processCode);
                    insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    insertMap.put("CREATE_USER", user.getUsername());
                    insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    insertMap.put("UPDATE_USER", user.getUsername());
                    insertMap.put("STATUS", 1);
                    insertMap.put("BUSSYS_TYPE", 1);
                    insertMap.put("APPLY_ID", applyId);//操作申报号
                    //TODO maxSn序列
                    insertMap.put("COLUMN_CODE", "field_"+maxSn++);
                    String recordId =  this.saveOrUpdate(insertMap, "T_LCJC_BUS_COLUMN_CHANGE", null);
                    sysLogService.saveLog("新增了ID为[" + recordId + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_ADD);
                }
            }
            //修改
            if(!"[]".equals(updatedJson)){
                List<Map<String,Object>> updatedList = JSON.parseObject(updatedJson,
                        new TypeReference<List<Map<String,Object>>>(){});
                for (Map<String,Object> updateMap : updatedList) {
                    updateMap.put("BUSSYS_UNITCODE", bussysUnitcode);
                    updateMap.put("BUSSYS_PLATCODE", bussysPlatcode);
                    updateMap.put("PROCESS_CODE", processCode);
                    updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    updateMap.put("UPDATE_USER", user.getUsername());
                    String serialId = String.valueOf(updateMap.get("CHANGE_ID"));
                    String recordId =  this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN_CHANGE", serialId);
                    sysLogService.saveLog("更新了ID为[" + recordId + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_EDIT);
                }
            }
            //删除
            if(!"[]".equals(deletedJson)){
                List<Map<String,Object>> deletedList = JSON.parseObject(deletedJson,
                        new TypeReference<List<Map<String,Object>>>(){});  
                for (Map<String,Object> deleteMap : deletedList) {
                    String serialId = String.valueOf(deleteMap.get("CHANGE_ID"));
                    this.remove("T_LCJC_BUS_COLUMN_CHANGE", "CHANGE_ID", new String[]{serialId});
                    sysLogService.saveLog("删除了ID为[" + serialId + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_DEL);
                }
            }
            //变更提交动作
            submitColumnChange(bussysUnitcode,bussysPlatcode,processCode,applyId);
        }
         
    }
    
    /***
     * 描述获取最大的排序编码
     * @author Water Guo
     * @created 2015-8-18 下午3:43:20
     * @param processCode
     * @return
     */
    public int getMaxSn(String processCode,String platCode,String tableName){
        return this.dao.getMaxSn(processCode,platCode,tableName);
    }


    /**
     * 描述 删除相关专项的基本信息字段的配置
     * @author Water Guo
     * @created 2015-8-19 上午9:12:03
     * @param selectColNames
     */
    public void multDel(String selectColNames) {
        String sql = "SELECT SERIAL_ID FROM T_LCJC_BUS_COLUMN WHERE BUSSYS_TYPE = 1 AND BUSSYS_UNITCODE=? " +
                " AND BUSSYS_PLATCODE= ? AND PROCESS_CODE = ? ";
        List<Map<String,Object>> list = null;
        String SERIAL_ID = null;
        for(String str : selectColNames.split(",")){
            String[] strs = str.split("[.]");
            if(strs.length > 2){
                list = dao.findBySql(sql, new String[] {strs[0],strs[1],strs[2]},null);
                for(Map<String,Object> object : list){
                    SERIAL_ID = String.valueOf(object.get("SERIAL_ID"));
                    if(StringUtils.isNotEmpty(SERIAL_ID)){
                        this.remove("T_LCJC_BUS_COLUMN", "SERIAL_ID", new String[]{SERIAL_ID});
                        sysLogService.saveLog("删除了ID为[" + SERIAL_ID
                                + "]的基本信息字段记录", SysLogService.OPERATE_TYPE_DEL);
                    }   
                    object = null;
                }          
            }else{
                log.error("ID截取字符串错误，split分割符.为[.] !!ERROR!!");
            }
        }
        
    }

    /**
     * 描述 （业务梳理）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-8-28 上午11:30:44
     * @param busCode,applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCode(String busCode,String applyId) {
        return dao.getListSysByBusCode(busCode,applyId);
    }

    /**
     * 
     * 描述 根据操作申报号，专项编码（过程编码）获取该专项（过程）的字段信息（历史表）
     * @author Water Guo
     * @created 2015-9-9 下午3:10:51
     * @param appliyId
     * @param processCode
     * @param type
     * @param platCode
     * @return
     */
    @Override
    public List<Map<String, Object>> listMaterialsColumn(String appliyId, String processCode, String type,
            String platCode) {
        List<Map<String, Object>> list;
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_COLUMN_HISTORY T WHERE 1=1 ");
        sql.append(" AND T.APPLY_ID = ? ");
        sql.append(" AND T.PROCESS_CODE = ? ");
        sql.append(" AND T.BUSSYS_TYPE = ? ");
        if("1".equals(type) && platCode != null){
            sql.append(" AND T.BUSSYS_PLATCODE = ? ");
        }
        sql.append(" ORDER BY T.BUSSYS_SN ASC ");
        if ("1".equals(type) && platCode != null){
            list = dao.findBySql(sql.toString(), new String[]{appliyId,processCode,type,platCode}, null);
        }else{
            list = dao.findBySql(sql.toString(), new String[]{appliyId,processCode,type}, null);
        }
        return list;
    }
    
    /**
     * 
     * 描述 根据操作申报号，专项编码（过程编码）获取该专项（过程）的字段信息（变更表）
     * @author Water Guo
     * @created 2015-9-9 下午3:10:51
     * @param appliyId
     * @param processCode
     * @param type
     * @param platCode
     * @return
     */
    @Override
    public List<Map<String, Object>> listMaterialsChangeColumn(String appliyId, String processCode, String type,
            String platCode) {
        List<Map<String, Object>> list;
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_COLUMN_CHANGE T WHERE 1=1 ");
        sql.append(" AND T.APPLY_ID = ? ");
        sql.append(" AND T.PROCESS_CODE = ? ");
        sql.append(" AND T.BUSSYS_TYPE = ? ");
        if("1".equals(type) && platCode != null){
            sql.append(" AND T.BUSSYS_PLATCODE = ? ");
        }
        sql.append(" ORDER BY T.BUSSYS_SN ASC ");
        if ("1".equals(type) && platCode != null){
            list = dao.findBySql(sql.toString(), new String[]{appliyId,processCode,type,platCode}, null);
        }else{
            list = dao.findBySql(sql.toString(), new String[]{appliyId,processCode,type}, null);
        }
        return list;
    }

    /**
     * 描述 确认提交基本信息业务梳理材料
     * @author Water Guo
     * @created 2015-9-16 下午4:18:42
     * @param id
     */
    public void submitColumn(String id) {
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        //更新基本信息表中的的状态
        String sql = "SELECT * FROM T_LCJC_BUS_COLUMN WHERE BUSSYS_TYPE = 1 AND BUSSYS_UNITCODE=? " +
                " AND BUSSYS_PLATCODE= ? AND PROCESS_CODE = ? ";
        List<Map<String,Object>> list = null;
        String SERIAL_ID = null;
        String[] strs = id.split("[.]");
        String buscode = null;
        if(strs.length > 2){
            buscode = strs[2];
            list = dao.findBySql(sql, new String[] {strs[0],strs[1],buscode},null);
            for(Map<String,Object> updateMap : list){
                SERIAL_ID = String.valueOf(updateMap.get("SERIAL_ID"));
                if(StringUtils.isNotEmpty(SERIAL_ID)){
                    //状态 0：暂存；1：确认；2：待审核；3：审核通过；4：审核不通过；5：关闭；
                    updateMap.put("STATUS", "1");
                    updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    updateMap.put("UPDATE_USER", user.getFullname());
                    String recordId =  this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN", SERIAL_ID);
                    sysLogService.saveLog(user.getFullname()+"更新了ID为[" + SERIAL_ID + "]的基本信息字段记录的状态为【确认】", 
                        SysLogService.OPERATE_TYPE_EDIT);
                }   
            }          
        }else{
            log.error("ID截取字符串错误，split分割符.为[.] !!ERROR!!");
        }
        //调用业务提交检查表的方法(添加提交表)
        busSpecialService.confirmAudit(buscode, "JCJBXX");
    }
    
    /**
     * 描述 基本信息字段变更确认
     * @author Water Guo
     * @created 2015-9-22 下午3:17:39
     * @param platCode
     * @param busCode
     */
    public void submitColumnChange(String unitcode,String platCode,String busCode,String applyId) {
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        //更新基本信息表中的的状态
        String sql = "SELECT * FROM T_LCJC_BUS_COLUMN_CHANGE WHERE BUSSYS_TYPE = 1 AND BUSSYS_UNITCODE=? " +
                " AND BUSSYS_PLATCODE= ? AND PROCESS_CODE = ? AND APPLY_ID = ? ";
        String SERIAL_ID = null;
        List<Map<String,Object>> list = dao.findBySql(sql, new String[] {unitcode,platCode,busCode,applyId},null);
        for(Map<String,Object> updateMap : list){
            SERIAL_ID = String.valueOf(updateMap.get("CHANGE_ID"));
            if(StringUtils.isNotEmpty(SERIAL_ID)){
                //状态 0：暂存；1：确认；2：待审核；3：审核通过；4：审核不通过；5：关闭；
                updateMap.put("STATUS", "1");
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", user.getUsername());
                String recordId =  this.saveOrUpdate(updateMap, "T_LCJC_BUS_COLUMN_CHANGE", SERIAL_ID);
                sysLogService.saveLog(user.getFullname()+"更新了专项为[" + busCode + "]的基本信息字段(变更)记录的状态为【确认】", 
                    SysLogService.OPERATE_TYPE_EDIT);
            }   
        }          
        //业务提交表中新增一条记录表示该变更信息已提交(变更确认动作)
        if(StringUtils.isNotEmpty(applyId) && !"null".equals(applyId)){
            busSpecialChangeService.confirmChange(applyId, busCode, "JCJBXX");
        }else{
            log.error("变更确认 applyId is null !! 基本信息字段变更动作无法确认。");
        }
    }

    /**
     * 描述 （变更业务）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-10-9 下午5:10:25
     * @param buscode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCodeChange(String buscode, String applyId) {
        return dao.getListSysByBusCodeChange(buscode,applyId);
    }

    /**
     * 描述 检查当前的专项的基本信息是否已存在
     * @author Water Guo
     * @created 2015-10-16 上午9:48:05
     * @param busscode
     * @param platcode
     * @return
     */
    public Boolean check(String busscode, String platcode) {
        String sql = "SELECT SERIAL_ID FROM T_LCJC_BUS_COLUMN WHERE BUSSYS_TYPE = 1 " +
                " AND BUSSYS_PLATCODE= ? AND PROCESS_CODE = ? ";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{platcode,busscode}, null);
        Boolean flag = false;
        if(list !=null && list.size() > 0){
            flag = true;
        }
        return flag;
    }
}
