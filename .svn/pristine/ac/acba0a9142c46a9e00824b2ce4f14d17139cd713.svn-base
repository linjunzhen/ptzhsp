/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpClientUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.fjfda.dao.FjfdaTransDao;
import net.evecom.platform.fjfda.service.FjfdaTransService;
import net.evecom.platform.fjfda.util.TokenUtil;

/**
 * 描述 FjfdaTransService
 * @author Keravon Feng
 * @created 2019年2月26日 下午2:04:11
 */
@Service("fjfdaTransService")
public class FjfdaTransServiceImpl extends BaseServiceImpl implements FjfdaTransService {
    
    /**
     * 统一接口地址
     */
    private String devbaseUrl;
    
    /**
     * 统一接口授权码
     */
    private String grantcode;
    
    /**
     * 接口流程流转默认审核人员名称
     */
    private String assignerName;
    /**
     * 接口流程流转默认审核人员Code
     */
    private String assignerCode;
    /**
     * 所引入的Dao
     */
    @Resource
    private FjfdaTransDao dao;

    /**
     * 
     * 描述
     * @author Keravon Feng
     * @created 2019年3月15日 上午10:46:10
     */
    public FjfdaTransServiceImpl(){
        super();
        Properties properties = FileUtil.readProperties("project.properties");
        devbaseUrl = properties.getProperty("devbaseUrl");
        grantcode = properties.getProperty("grantcode");
        assignerName = properties.getProperty("assignerName");
        assignerCode = properties.getProperty("assignerCode");
    }
    
    /**
     * 描述 GenericDao
     * @author Keravon Feng
     * @created 2019年2月26日 下午2:06:28
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 findBySqlFilter
     * @author Keravon Feng
     * @created 2019年2月26日 下午2:12:24
     * @param filter
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM T_FJFDA_TRANS T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述 数据上报至省平台受理
     * @author Keravon Feng
     * @created 2019年2月28日 上午10:49:45
     * @param yw_Id
     * @return
     */
    @Override
    public Map<String, Object> createItemRemote(String ywId) {
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("success", false);
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("fjfdaURL");
        String postUrl = url+"service/createItem.do";
        Map<String,Object> map = dao.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{ywId});
        if(map != null){
            Map<String,Object> transdata = dao.getByJdbc("T_FJFDA_TRANS_DATA", 
                    new String[]{"BUS_RECORDID"}, 
                    new Object[]{String.valueOf(map.get("BUS_RECORDID"))});
            if(transdata != null){
                String datajson = String.valueOf(transdata.get("DATA_JSON"));
                Map<String,Object> param = new HashMap<String, Object>();
                String tokenjson = TokenUtil.getToken();
                JSONObject json = JSON.parseObject(tokenjson);
                param.put("token", json.getString("token"));
                param.put("item_code", transdata.get("ITEM_CODE"));
                param.put("data", JSON.parse(datajson));
                Map<String,Object> result = TokenUtil.doPostJson(HttpClientUtil.getPlainHttpClient(),
                        postUrl,param);
                if(result != null && "true".equals(String.valueOf(result.get("result")))){
                    map.put("TRANS_STATE", "1");
                    map.put("RUN_STATE", "0");
                    map.put("RUN_STATUS", "已报送");
                    map.put("RESP_INFO", JSON.toJSONString(result));
                    map.put("FJFDA_EVEID", result.get("item_id"));
                    res.put("success", true);
                    res.put("msg", "上报成功!");                    
                }else{
                    map.put("TRANS_STATE", "-1");
                    map.put("RESP_INFO", JSON.toJSONString(result));
                    res.put("success", false);
                    res.put("msg", "上报失败："+JSON.toJSONString(result));
                }
                dao.saveOrUpdate(map, "T_FJFDA_TRANS", ywId);
            }else{
                res.put("msg", "失败，报文数据未构建。");
            }
        }else{
            res.put("msg", "失败，任务丢失。");
        }
        return res;
    }

    /**
     * 描述 材料重新上传
     * @author Keravon Feng
     * @created 2019年2月28日 上午11:35:23
     * @param yw_Id
     * @return
     */
    @Override
    public void matersReUpload(String yw_Id) {
        Set<String> busRecordIdSets = new HashSet<String>();
        Properties properties = FileUtil.readProperties("project.properties");
        String path = properties.getProperty("AttachFilePath");
        StringBuffer sql = new StringBuffer(" SELECT T.YW_ID,T.BUS_RECORDID,T.FILEPATH FROM T_FJFDA_TRANS_MATERS T ");
        sql.append(" WHERE T.BUS_RECORDID = ? ORDER BY T.CREATE_TIME ASC ");
        Map<String,Object> obj = dao.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{yw_Id});
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{String.valueOf(obj.get("BUS_RECORDID"))}, null); 
        if (list.size() > 0) {
            for (Map<String, Object> map : list) {
                if (map.get("FILEPATH") != null) {
                    String filepath = path + (String) map.get("FILEPATH");
                    Map<String, Object> res = TokenUtil.uploadFile(filepath);
                    String ywId = String.valueOf(map.get("YW_ID"));
                    if (res != null && "true".equals(String.valueOf(res.get("success")))) {
                        busRecordIdSets.add(String.valueOf(map.get("BUS_RECORDID")));
                        map.put("TRANS_STATE", "1");
                        dao.saveOrUpdate(map, "T_FJFDA_TRANS_MATERS", ywId);
                    } else {
                        map.put("TRANS_STATE", "-1");
                        dao.saveOrUpdate(map, "T_FJFDA_TRANS_MATERS", ywId);
                    }
                }
            }
            // 更新传输任务主表的附件上传状态
            for (String busrecordId : busRecordIdSets) {
                Map<String, Object> trans = dao.getByJdbc("T_FJFDA_TRANS",
                        new String[] { "BUS_RECORDID" }, new Object[] { busrecordId });
                if (trans != null) {
                    trans.put("IS_MATERS_UPLOAD", "1");
                    dao.saveOrUpdate(trans, "T_FJFDA_TRANS", String.valueOf(trans.get("YW_ID")));
                }
            }
        }
    }

    /**
     * 描述 获取省平台对应业务的流程状态
     * @author Keravon Feng
     * @created 2019年3月5日 上午9:54:22
     * @param fjfdaeveId
     * @return
     */
    @Override
    public Map<String, Object> getStatusByEveId(String fjfdaeveId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("fjfdaURL");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("item_id", fjfdaeveId);
        String tokenjson = TokenUtil.getToken();
        JSONObject json = JSON.parseObject(tokenjson);
        map.put("token", json.getString("token"));
        Map<String,Object> result  = TokenUtil.doGet(url + "service/getItemStatus.do", map);
        return result;
    }
    
    /**
     * 描述 获取省平台对应业务的办结接口信息
     * @author Keravon Feng
     * @created 2019年3月5日 下午4:15:45
     * @param fjfdaeveId
     * @return
     */
    public Map<String, Object> getResultByEveId(String fjfdaeveId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("fjfdaURL");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("item_id", fjfdaeveId);
        String tokenjson = TokenUtil.getToken();
        JSONObject json = JSON.parseObject(tokenjson);
        map.put("token", json.getString("token"));
        Map<String,Object> result  = TokenUtil.doGet(url + "service/getItemResult.do", map);
        return result;
    }

    /**
     * 描述 获取需要更新业务状态的数据
     * @author Keravon Feng
     * @created 2019年3月5日 上午10:01:47
     * @param pageSize
     * @return
     */
    @Override
    public List<Map<String, Object>> listUpdateStatusData(int pageSize) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.YW_ID,T.BUS_RECORDID,T.FJFDA_EVEID,T.RUN_STATUS,");
        sql.append(" T.BUS_ITEMCODE,T.BUS_EVEID,T.RUN_STATE FROM T_FJFDA_TRANS T ");
        sql.append(" WHERE T.RUN_STATE < ? AND T.TRANS_STATE = 1 ORDER BY T.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{3}, new PagingBean(0, pageSize));
    }

    /**
     * 描述 updateFlowRunStatus
     * @author Keravon Feng
     * @created 2019年3月11日 下午4:58:38
     * @param yw_Id
     * @return
     */
    @Override
    public Map<String, Object> updateFlowRunStatus(Map<String,Object> map) {
        Map<String,Object> result = null;
        String fjfdaeveId = String.valueOf(map.get("FJFDA_EVEID"));
        String ywId = String.valueOf(map.get("YW_ID"));
        String bus_itemcode = String.valueOf(map.get("BUS_ITEMCODE"));
        String bus_eveid = String.valueOf(map.get("BUS_EVEID"));
        String run_status = String.valueOf(map.get("RUN_STATUS"));
        String run_state = String.valueOf(map.get("RUN_STATE"));
        Map<String,Object> statusMap = this.getStatusByEveId(fjfdaeveId);
        if(statusMap != null && "true".equals(String.valueOf(statusMap.get("result")))){
            String status = String.valueOf(statusMap.get("status")); 
            if(!status.equals(run_status)){
                //已受理，审批中，已结束
                if("已受理".equals(status)){
                    result = startRunStatus(ywId, bus_itemcode, bus_eveid, statusMap);
                }else if("审批中".equals(status)){
                    if("0".equals(run_state)){
                        result = startRunStatus(ywId, bus_itemcode, bus_eveid, statusMap);
                    }else{
                        result = updateRunStatus(ywId, bus_itemcode, bus_eveid, statusMap);
                    }
                }else if("已结束".equals(status)){
                    Map<String,Object> resultMap = this.getResultByEveId(fjfdaeveId);
                    if(resultMap != null && "true".equals(String.valueOf(resultMap.get("result")))){
                        String resultStatus = String.valueOf(resultMap.get("status"));                            
                        if("已办结(正常结束)".equals(resultStatus)){ 
                            if("0".equals(run_state)){
                                result = startRunStatus(ywId, bus_itemcode, bus_eveid, statusMap);
                            }else if("1".equals(run_state)){
                                //必须先把本地流程流转到审核节点
                                result = updateRunStatus(ywId, bus_itemcode, bus_eveid, statusMap);
                            }else{
                                //调用接口完成本地流程正常办结操作                               
                                if(resultMap.get("zjxx") != null){
                                    Map<String,Object> zjxx = JSON.parseObject(
                                            String.valueOf(resultMap.get("zjxx")), Map.class);
                                    Map<String,Object> resultAudit = new HashMap<String, Object>();
                                    resultAudit.put("TASK_NODENAME", "办结");
                                    resultAudit.put("itemCode", bus_itemcode);
                                    resultAudit.put("EFLOW_HANDLE_OPINION", resultMap.get("opinion"));
                                    resultAudit.put("EXE_ID", bus_eveid);
                                    result = doFlowResultExecute(resultAudit,ywId,zjxx);
                                } 
                            }
                        }else{
                            //调用接口完成本地流程非正常办结操作（退件）
                            Map<String,Object> noaccept = new HashMap<String, Object>();
                            noaccept.put("exeId", bus_eveid);
                            noaccept.put("EFLOW_HANDLE_OPINION", resultMap.get("opinion"));
                            result = doFlowNoAcceptExecute(noaccept,ywId);
                        }
                        resultMap.clear();
                    }
                }
            }
        }
        statusMap.clear();
        map.clear();
        return result;
    }
    
    /**
     * 描述 上报成功及更新本地流程为受理状态
     * @author Keravon Feng
     * @created 2019年3月12日 下午3:21:19
     * @param ywId
     * @param bus_itemcode
     * @param bus_eveid
     * @param statusMap
     */
    public Map<String,Object> startRunStatus(String ywId, String bus_itemcode, 
            String bus_eveid, Map<String, Object> statusMap) {
        Map<String,Object> result = null;
        List<Map<String, Object>> auditLogs = 
                (List<Map<String, Object>>) statusMap.get("audit");
        String eflow_handle_opinion = "";
        for(Map<String, Object> auditLog : auditLogs){
            if("开始".equals(String.valueOf(auditLog.get("TASK_NODENAME"))) 
                    && "已审核".equals(String.valueOf(auditLog.get("TASK_STATUS")))){
                eflow_handle_opinion = String.valueOf(auditLog.get("ASSIGNER_NAME"))+"于"+
                         String.valueOf(auditLog.get("CREATE_TIME"))+"受理开始。"+
                 (auditLog.get("HANDLE_OPINION") != null ? 
                         "意见："+String.valueOf(auditLog.get("HANDLE_OPINION")):"");
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("EFLOW_HANDLE_OPINION", eflow_handle_opinion);
                params.put("TASK_NODENAME", "受理");
                params.put("itemCode", bus_itemcode);
                params.put("EXE_ID", bus_eveid);
                result = doFlowStart(params,ywId);
                break ;
            }
        }
        return result;
    }
    /**
     * 描述 执行本地流程至受理状态
     * @author Keravon Feng
     * @created 2019年3月12日 下午5:48:44
     * @param audit
     * @param ywId
     * @return
     */
    private Map<String,Object> doFlowStart(Map<String,Object> audit,String ywId){
        audit.put("ASSIGNER_NAME", assignerName);
        audit.put("ASSIGNER_CODE", assignerCode);
        audit.put("servicecode", "ptzhspexecuteflow");
        audit.put("grantcode", grantcode);
        Map<String,Object> result = TokenUtil.doPost(devbaseUrl, audit);
        Map<String,Object> trans = dao.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{ywId});
        if(result != null && "true".equals(String.valueOf(result.get("success")))){            
            if(trans != null){
                trans.put("RUN_STATE", 1);
                trans.put("RUN_STATUS", "已受理");
                trans.put("RESP_INFO", JSON.toJSONString(result));
                dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);                
            }
        }else{
            trans.put("RESP_INFO", JSON.toJSONString(result));
            dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);
        }
        return result;
    }
    
    /**
     * 描述 更新本地流程到审核节点
     * @author Keravon Feng
     * @created 2019年3月5日 下午5:09:49
     * @param ywId
     * @param bus_itemcode
     * @param bus_eveid
     * @param statusMap
     */
    private Map<String,Object> updateRunStatus(String ywId, String bus_itemcode, 
            String bus_eveid, Map<String, Object> statusMap) {
        Map<String,Object> result = null;
        List<Map<String, Object>> auditLogs = 
                (List<Map<String, Object>>) statusMap.get("audit");
        String eflow_handle_opinion = "";
        for(Map<String, Object> auditLog : auditLogs){
            if("受理".equals(String.valueOf(auditLog.get("TASK_NODENAME"))) 
                    && "已审核".equals(String.valueOf(auditLog.get("TASK_STATUS")))){
                eflow_handle_opinion = String.valueOf(auditLog.get("ASSIGNER_NAME"))+"于"+
                         String.valueOf(auditLog.get("CREATE_TIME"))+"受理审核完成。"+
                 (auditLog.get("HANDLE_OPINION") != null ? 
                         "意见："+String.valueOf(auditLog.get("HANDLE_OPINION")):"");
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("EFLOW_HANDLE_OPINION", eflow_handle_opinion);
                params.put("TASK_NODENAME", "审核");
                params.put("itemCode", bus_itemcode);
                params.put("EXE_ID", bus_eveid);
                result = doFlowExecute(params,ywId);
                break ;
            }
        }
        return result;
    }
    
    /**
     * 描述 调用数据支撑平台接口
     * @author Keravon Feng
     * @created 2019年3月5日 下午2:42:22
     * @param audit
     * @param ywId
     * @return
     */
    private Map<String,Object> doFlowExecute(Map<String,Object> audit,String ywId){
        audit.put("ASSIGNER_NAME", assignerName);
        audit.put("ASSIGNER_CODE", assignerCode);
        audit.put("servicecode", "ptzhspexecuteflow");
        audit.put("grantcode", grantcode);
        Map<String,Object> result = TokenUtil.doPost(devbaseUrl, audit);
        Map<String,Object> trans = dao.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{ywId});
        if(result != null && "true".equals(String.valueOf(result.get("success")))){            
            if(trans != null){
                trans.put("RUN_STATE", 2);
                trans.put("RUN_STATUS", "审批中");
                trans.put("RESP_INFO", JSON.toJSONString(result));
                dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);                
            }
        }else{
            trans.put("RESP_INFO", JSON.toJSONString(result));
            dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);
        }
        return result;
    }
    
    /**
     * 描述 正常办结操作
     * @author Keravon Feng
     * @created 2019年3月5日 下午5:14:57
     * @param audit
     * @param ywId
     * @param zjxx
     * @return
     */
    private Map<String,Object> doFlowResultExecute(Map<String,Object> audit,String ywId,Map<String,Object> zjxx){
        //audit.put("ASSIGNER_NAME", "超级管理员");
        //audit.put("ASSIGNER_CODE", "admin");
        audit.put("ASSIGNER_NAME", assignerName);
        audit.put("ASSIGNER_CODE", assignerCode);
        audit.put("servicecode", "ptzhspendflow");
        audit.put("grantcode", grantcode);
        audit.put("RESULT_FILE_URL", "无");
        audit.put("RESULT_FILE_ID", "无");
        audit.put("RUN_MODE", "无"); 
        audit.put("SDCONTENT", zjxx.get("ZZMC") != null ? zjxx.get("ZZMC") : "无");
        audit.put("XKCONTENT", zjxx.get("ZZMC") != null ? zjxx.get("ZZMC") : "无");
        audit.put("CLOSE_TIME", zjxx.get("YXQZ")!= null ? zjxx.get("YXQZ") : "无");
        audit.put("EFFECT_TIME", zjxx.get("FZRQ")!= null ? zjxx.get("FZRQ") : "无");
        audit.put("XKDEPT_ID", "无");
        audit.put("XKDEPT_NAME", zjxx.get("FZJG")!= null ? zjxx.get("FZJG") : "无");
        audit.put("XKFILE_NAME", zjxx.get("ZZMC") != null ? zjxx.get("ZZMC") : "无");
        audit.put("XKFILE_NUM", zjxx.get("XKZBH") != null ? zjxx.get("XKZBH") : "无");        
        Map<String,Object> result = TokenUtil.doPost(devbaseUrl, audit);
        Map<String,Object> trans = dao.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{ywId});
        if(result != null && "true".equals(String.valueOf(result.get("success")))){            
            if(trans != null){
                trans.put("RUN_STATE", 3);
                trans.put("RUN_STATUS", "已结束");
                trans.put("RESP_INFO", JSON.toJSONString(result));
                dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);                
            }
        }else{
            trans.put("RESP_INFO", JSON.toJSONString(result));
            dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);
        }
        return result;
    }
    
    /**
     * 描述 doFlowNoAcceptExecute
     * @author Keravon Feng
     * @created 2019年3月6日 上午9:55:41
     * @param audit
     * @param ywId
     * @param zjxx
     * @return
     */
    private Map<String,Object> doFlowNoAcceptExecute(Map<String,Object> audit,String ywId){
        //audit.put("ASSIGNER_NAME", "超级管理员");
        audit.put("ASSIGNER_NAME", assignerName);
        //audit.put("ASSIGNER_CODE", "admin");
        audit.put("ASSIGNER_CODE", assignerCode);
        audit.put("servicecode", "ptzhspnotaccept");
        audit.put("grantcode", grantcode);
        Map<String,Object> result = TokenUtil.doPost(devbaseUrl, audit);
        Map<String,Object> trans = dao.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{ywId});
        if(result != null && "true".equals(String.valueOf(result.get("success")))){            
            if(trans != null){
                trans.put("RUN_STATE", 3);
                trans.put("RUN_STATUS", "已结束");
                trans.put("RESP_INFO", JSON.toJSONString(result));
                dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);                
            }
        }else{
            trans.put("RESP_INFO", JSON.toJSONString(result));
            dao.saveOrUpdate(trans, "T_FJFDA_TRANS", ywId);
        }
        return result;
    }
}
