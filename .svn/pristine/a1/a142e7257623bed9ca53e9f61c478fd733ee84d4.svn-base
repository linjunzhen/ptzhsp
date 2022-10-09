/*
  * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.fjfda.dao.FoodProductionDao;
import net.evecom.platform.fjfda.service.FoodProductionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述 食品生产操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("foodProductionService")
public class FoodProductionServiceImpl extends BaseServiceImpl implements FoodProductionService {
    /**
     * 所引入的dao
     */
    @Resource
    private FoodProductionDao dao;
    
    /**
     * 引入flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    
    
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public List<Map<String, Object>> findCpxx(String jbxxId,String isQueryDetail,String EFLOW_ASSIGNER_TYPE) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_CPXX C ");
        sql.append("WHERE C.JBXX_ID=? ");
        params.add(jbxxId);
        sql.append(" ORDER BY C.CPXH ASC");
        
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    @Override
    public List<Map<String, Object>> findJsry(String jbxxId) {

        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_JSRY J ");
        sql.append("WHERE J.JBXX_ID=?  ORDER BY J.CREATE_TIME ASC");
        params.add(jbxxId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    @Override
    public Map<String, Object> getWtdlr(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_WTDLR W ");
        sql.append("WHERE W.JBXX_ID=?   ");
        params.add(jbxxId);
        return dao.getByJdbc(sql.toString(), params.toArray());
    }

    @Override
    public Set<String> getApplicationFormResult(Map<String, Object> flowVars) {
        String sqfs = (String) flowVars.get("SQFS");
        Set<String> resultNodes = new HashSet<String>();
        if(StringUtils.isNotEmpty(sqfs)){
            if(sqfs.equals("1")){
                resultNodes.add("预审");
            }else{
                resultNodes.add("受理");
            }
        }
        return resultNodes;
    }

    @Override
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        if("T_FJFDA_SPSCXK_JBXX1".equals(busTableName)){
            busTableName = "T_FJFDA_SPSCXK_JBXX";
        }
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
       
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        flowDatas.put("JBXX_ID", busRecordId);
        // 保存地址信息
        setAddressInfo(flowDatas, busRecordId);
        // 保存技术人员信息
        setJsryInfo(flowDatas, busRecordId);
        // 保存产品信息
        setCpxxInfo(flowDatas, busRecordId);
        
        //保存加工场所信息
        setJgcsxx(flowDatas, busRecordId);
        
        //保存设备设施信息
        setSbssxx(flowDatas, busRecordId);
        //保存检验仪器
        setJyyq(flowDatas, busRecordId);
        //保存安全制度
        setAqzd(flowDatas, busRecordId);
        
        
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            fileAttachService.saveItemMaterFiles(submitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }

    /**
     * 描述 保存设备设施信息
     * @author John Zhang
     * @created 2016年10月9日 下午3:49:43
     * @param flowDatas
     * @param busRecordId
     */
    private void setSbssxx(Map<String, Object> flowDatas, String busRecordId){
        dao.remove("T_FJFDA_SPSCXK_SBSS", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String sbssJson = (String) flowDatas.get("sbssJson");
        if (StringUtils.isNotEmpty(sbssJson)) {
            List<Map> sbssList = JSON.parseArray(sbssJson, Map.class);
            for (int i = 0; i < sbssList.size(); i++) {
                Map<String, Object> sbss = sbssList.get(i);
                sbss.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(sbss, "T_FJFDA_SPSCXK_SBSS", "");
            }
        }
    }
    /**
     * 描述 保存检验仪器
     * @author John Zhang
     * @created 2016年10月9日 下午3:50:02
     * @param flowDatas
     * @param busRecordId
     */
    private void setJyyq(Map<String, Object> flowDatas, String busRecordId){
        dao.remove("T_FJFDA_SPSCXK_JYYQ", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String jyyqJson = (String) flowDatas.get("jyyqJson");
        if (StringUtils.isNotEmpty(jyyqJson)) {
            List<Map> jyyqList = JSON.parseArray(jyyqJson, Map.class);
            for (int i = 0; i < jyyqList.size(); i++) {
                Map<String, Object> jyyq = jyyqList.get(i);
                jyyq.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(jyyq, "T_FJFDA_SPSCXK_JYYQ", "");
            }
        }
    }
    /**
     * 描述 保存安全制度清单
     * @author John Zhang
     * @created 2016年10月9日 下午3:50:19
     * @param flowDatas
     * @param busRecordId
     */
    private void setAqzd(Map<String, Object> flowDatas, String busRecordId){
        dao.remove("T_FJFDA_SPSCXK_AQZD", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String aqzdJson = (String) flowDatas.get("aqzdJson");
        if (StringUtils.isNotEmpty(aqzdJson)) {
            List<Map> aqzdList = JSON.parseArray(aqzdJson, Map.class);
            for (int i = 0; i < aqzdList.size(); i++) {
                Map<String, Object> aqzd = aqzdList.get(i);
                aqzd.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(aqzd, "T_FJFDA_SPSCXK_AQZD", "");
            }
        }
    }
    /**
     * 描述 保存保健产品信息
     * @author John Zhang
     * @created 2016年8月24日 上午11:36:27
     * @param flowDatas
     * @param busRecordId
     */
    private void setHealthCpxx(Map<String, Object> flowDatas, String busRecordId){
        dao.remove("T_FJFDA_BJSPSCXK_CPXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String healthCpxxJson = (String) flowDatas.get("healthCpxxJson");
        if (StringUtils.isNotEmpty(healthCpxxJson)) {
            List<Map> cpxxList = JSON.parseArray(healthCpxxJson, Map.class);
            for (int i = 0; i < cpxxList.size(); i++) {
                Map<String, Object> cpxx = cpxxList.get(i);
                cpxx.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(cpxx, "T_FJFDA_BJSPSCXK_CPXX", "");
            }
        }
    }
    
    /**
     * 
     * 描述 保存地址信息
     * 
     * @author Rider Chen
     * @created 2016年7月5日 下午6:35:27
     * @param flowDatas
     * @param busRecordId
     */
    private void setAddressInfo(Map<String, Object> flowDatas, String busRecordId) {
        // 保存三个地址信息，先删除
        dao.remove("T_FJFDA_SPSCXK_DZXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        // 保存住所信息
        Map<String, Object> residenceInfo = new HashMap<String, Object>();
        residenceInfo.put("DZLB", "1");
        residenceInfo.put("SQZZZM", flowDatas.get("ZSQS"));
        residenceInfo.put("XZZXXJS", flowDatas.get("ZSXS"));
        residenceInfo.put("XZJD", flowDatas.get("ZSXZ"));
        residenceInfo.put("CLNMPHM", flowDatas.get("ZSXXDZ"));
        residenceInfo.put("JBXX_ID", busRecordId);
        dao.saveOrUpdate(residenceInfo, "T_FJFDA_SPSCXK_DZXX", "");
        // 保存生产场所
        /*Map<String, Object> placeOfBusiness = new HashMap<String, Object>();
        placeOfBusiness.put("DZLB", "2");
        placeOfBusiness.put("SQZZZM", flowDatas.get("JYCSQS"));
        placeOfBusiness.put("XZZXXJS", flowDatas.get("JYCSXS"));
        placeOfBusiness.put("XZJD", flowDatas.get("JYCSXZ"));
        placeOfBusiness.put("CLNMPHM", flowDatas.get("JYXXDZ"));
        placeOfBusiness.put("JBXX_ID", busRecordId);
        dao.saveOrUpdate(placeOfBusiness, "T_FJFDA_SPSCXK_DZXX", "");*/
        String scdzJson = (String) flowDatas.get("scdzJson");
        if (StringUtils.isNotEmpty(scdzJson)) {
            List<Map> scdzJsonList = JSON.parseArray(scdzJson, Map.class);
            for (int i = 0; i < scdzJsonList.size(); i++) {
                Map<String, Object> scdzMap = new HashMap<String, Object>();
                scdzMap.put("DZLB", "2");
                scdzMap.put("JBXX_ID", busRecordId);
                scdzMap.put("XZZXXJS", scdzJsonList.get(i).get("JYCSXS"));
                scdzMap.put("SQZZZM", scdzJsonList.get(i).get("JYCSQS"));
                scdzMap.put("XZJD", scdzJsonList.get(i).get("JYCSXZ"));
                scdzMap.put("CLNMPHM", scdzJsonList.get(i).get("JYXXDZ"));
                dao.saveOrUpdate(scdzMap, "T_FJFDA_SPSCXK_DZXX", "");
            }
        }
        // 保存仓储场所
        String ckdzJson = (String) flowDatas.get("ckdzJson");
        if (StringUtils.isNotEmpty(ckdzJson)) {
            List<Map> ckdzJsonList = JSON.parseArray(ckdzJson, Map.class);
            for (int i = 0; i < ckdzJsonList.size(); i++) {
                Map<String, Object> scdzMap = new HashMap<String, Object>();
                scdzMap.put("DZLB", "3");
                scdzMap.put("JBXX_ID", busRecordId);
                scdzMap.put("XZZXXJS", ckdzJsonList.get(i).get("CCCSXS"));
                scdzMap.put("SQZZZM", ckdzJsonList.get(i).get("CCCSQS"));
                scdzMap.put("XZJD", ckdzJsonList.get(i).get("CCCSXZ"));
                scdzMap.put("CLNMPHM", ckdzJsonList.get(i).get("CCXXDZ"));
                dao.saveOrUpdate(scdzMap, "T_FJFDA_SPSCXK_DZXX", "");
            }
        }
        if(flowDatas.get("CCCSQS") != null && 
                flowDatas.get("healthCpxxJson")!= null &&
                !StringUtils.isEmpty(flowDatas.get("CCCSQS").toString())){
            Map<String, Object> storagePlace = new HashMap<String, Object>();
            storagePlace.put("DZLB", "3");
            storagePlace.put("SQZZZM", flowDatas.get("CCCSQS"));
            storagePlace.put("XZZXXJS", flowDatas.get("CCCSXS"));
            storagePlace.put("XZJD", flowDatas.get("CCCSXZ"));
            storagePlace.put("CLNMPHM", flowDatas.get("CCXXDZ"));
            storagePlace.put("JBXX_ID", busRecordId);
            dao.saveOrUpdate(storagePlace, "T_FJFDA_SPSCXK_DZXX", "");
        }
    }

    /**
     * 
     * 描述 保存技术人员信息
     * 
     * @author Rider Chen
     * @created 2016年7月5日 下午6:35:27
     * @param flowDatas
     * @param busRecordId
     */
    private void setJsryInfo(Map<String, Object> flowDatas, String busRecordId) {
        // 保存信息前，先删除
        dao.remove("T_FJFDA_SPSCXK_JSRY", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String technicalpersonneljson = (String) flowDatas.get("TechnicalPersonnelJson");
        if (StringUtils.isNotEmpty(technicalpersonneljson)) {
            List<Map> technicalpersonnelList = JSON.parseArray(technicalpersonneljson, Map.class);
            for (int i = 0; i < technicalpersonnelList.size(); i++) {
                Map<String, Object> technicalpersonneltMap = technicalpersonnelList.get(i);
                technicalpersonneltMap.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(technicalpersonneltMap, "T_FJFDA_SPSCXK_JSRY", "");
            }
        }
    }
    
    /**
     * 
     * 描述 保存产品信息
     * 
     * @author Rider Chen
     * @created 2016年7月5日 下午6:35:27
     * @param flowDatas
     * @param busRecordId
     */
    private void setCpxxInfo(Map<String, Object> flowDatas, String busRecordId) {        
        String isStartNode = flowDatas.get("EFLOW_IS_START_NODE") == null ? "false" : flowDatas.get(
                "EFLOW_IS_START_NODE").toString();
        if (isStartNode.equals("true")) {
            // 保存信息前，先删除
            dao.remove("T_FJFDA_SPSCXK_CPXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        } else {
            //SysUser sysUser = AppUtil.getLoginUser();
           /* if (sysUser != null) {
                // 保存三个地址信息，先删除
                dao.remove("T_FJFDA_SPSCXK_CPXX", new String[] { "JBXX_ID", "CPXX_DEPTID" }, new Object[] {
                        busRecordId, sysUser.getDepId() });
            } else {*/

                // 保存信息前，先删除
            dao.remove("T_FJFDA_SPSCXK_CPXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
            //}
        }
        String ProductCpxxJson = (String) flowDatas.get("ProductCpxxJson");
        if (StringUtils.isNotEmpty(ProductCpxxJson)) {
            List<Map> productCpxxList = JSON.parseArray(ProductCpxxJson, Map.class);
            for (int i = 0; i < productCpxxList.size(); i++) {
                Map<String, Object> productCpxxMap = productCpxxList.get(i);
                productCpxxMap.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(productCpxxMap, "T_FJFDA_SPSCXK_CPXX", "");
            }
        }
    }

    @Override
    public Map<String, Object> getAddressMap(String jbxxId, String dzlb) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        if(StringUtils.isNotEmpty(dzlb)){
            if(dzlb.equals("1")){
                sql.append("select DZXX_ID,SQZZZM ZSQS,XZZXXJS ZSXS,XZJD ZSXZ,CLNMPHM ZSXXDZ  ");
            }else if(dzlb.equals("2")){
                sql.append("select DZXX_ID,SQZZZM JYCSQS,XZZXXJS JYCSXS,XZJD JYCSXZ,CLNMPHM JYXXDZ  ");
            }else if(dzlb.equals("3")){
                sql.append("select DZXX_ID,SQZZZM CCCSQS,XZZXXJS CCCSXS,XZJD CCCSXZ,CLNMPHM CCXXDZ  ");
            }else{
                sql.append("select *  ");
            }
            sql.append(" from T_FJFDA_SPSCXK_DZXX D WHERE D.JBXX_ID=? AND D.DZLB=?   ");
            params.add(jbxxId);
            params.add(dzlb);
            return dao.getByJdbc(sql.toString(), params.toArray());
        }else{
            return null;
        }
    }

    @Override
    public Map<String, Object> getSpscxkxxMap(String xkzbh, String isProvince, String sqlb) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select J.* ");
        sql.append("FROM T_FJFDA_SPSCXKXX J  ");
        sql.append("WHERE J.XKZBH=? And XKZZT ='10'");
        params.add(xkzbh);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return null!=list&&list.size()>0?list.get(0):null;
    }
    
    /**
     * 
     * 描述回填证照结果表数据
     * @author Usher Song
     * @created 2016年9月27日 上午10:20:23
     * @param productionInfo
     * @param licenseInfo 证照信息
     */
    private void backFillLicInfo(Map<String, Object> productionInfo, Map<String, Object> licenseInfo) {
        if(licenseInfo!=null){
            productionInfo.put("YXQZ", licenseInfo.get("YXQZ"));
            productionInfo.put("FZRQ", licenseInfo.get("FZRQ"));
            productionInfo.put("FZJG", licenseInfo.get("FZJG"));
            productionInfo.put("FZJG_ID", licenseInfo.get("FZJG_ID"));
            productionInfo.put("RCJDGLJG", licenseInfo.get("RCJDGLJG"));
            productionInfo.put("RCJDGLJG_ID", licenseInfo.get("RCJDGLJG_ID"));
            productionInfo.put("RCJDGLRY", licenseInfo.get("RCJDGLRY"));
            productionInfo.put("QFR", licenseInfo.get("QFR"));
            productionInfo.put("GXJGS", licenseInfo.get("GXJGS"));
            productionInfo.put("GXJGS_ID", licenseInfo.get("GXJGS_ID"));
            //productionInfo.put("SQRMC", licenseInfo.get("SQRMC"));
        }
    }
    
    
    /**
     * 
     * 描述 品种明细内容超出显示区域 需要换行
     * @author Usher Song
     * @created 2016年11月7日 下午4:19:12
     * @param cpxxList
     * @return
     */
    private List<Map<String, Object>> handleCpxxList(List<Map<String, Object>> cpxxList) {
//        List<Map<String,Object>> cpInfoList = new ArrayList<Map<String,Object>>(); 
//        int length = 40;
//        for (Map<String, Object> cpInfo : cpxxList) {
//            String cpPzmx = cpInfo.get("CPXX_PZMX").toString();
//            if(cpPzmx.length()<length){
//                cpxxList.add(cpInfo);
//            }else{
//                int groupSize = cpPzmx.length()/length;
//                int lastGroupSize = cpPzmx.length()%length;
//                Map<String,Object> newCpxx = new HashMap<String,Object>();
//                for(int i=0;i<groupSize-1;i++){
//                    cpInfo.put("CPXX_PZMX", cpPzmx.substring(i*length, i*length+groupSize));
//                    newCpxx.putAll(cpInfo);
//                    cpxxList.add(cpInfo);
//                }
//            }
//        }
        return cpxxList;
    }

    public List<Map<String,Object>> getSpscInfoListByShxydm(){
        return null;
    }
    
    
    
    /**
     * 
     * 描述 根据基本信息ID与部门ID获取产品信息
     * @author Rider Chen
     * @created 2016年7月12日 上午9:31:01
     * @param jbxxId
     * @param deptId
     * @return
     */
    @Override
    public List<Map<String, Object>> getCpxxInfo(String jbxxId,String deptId){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select  C.*,");
        sql.append(" (select T.TYPE_CODE from T_WSBS_BUSTYPE T where T.TYPE_ID = C.CPXX_SPLB) CPXX_SPBH");
        sql.append(" from T_FJFDA_SPSCXK_CPXX C ");
        sql.append(" WHERE C.JBXX_ID=? ");
        params.add(jbxxId);
        if(StringUtils.isNotEmpty(deptId)){
            sql.append(" and C.CPXX_DEPTID=? ");
            params.add(deptId);
        }
        sql.append(" ORDER BY C.CREATE_TIME ASC");
        
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    /**
     * 
     * 描述 根据基本信息ID与地址类型获取地址
     * @author Rider Chen
     * @created 2016年7月12日 上午9:31:25
     * @param jbxxId
     * @param dzlb
     * @return
     */
    public String getAdderss(String jbxxId,int dzlb){
        StringBuffer sql = new StringBuffer("select (SELECT '福建省'||T.TYPE_NAME FROM "
                + "T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE = D.SQZZZM) ");
        List<Object> params = new ArrayList<Object>();
        sql.append(" || (SELECT T.TYPE_NAME FROM T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE = D.XZZXXJS) ");
        sql.append(" || (SELECT T.DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY T WHERE T.DIC_CODE = D.XZJD) ");
        sql.append(" ||CLNMPHM AS ADDRESS ");
        sql.append(" from T_FJFDA_SPSCXK_DZXX D  ");
        sql.append(" WHERE D.JBXX_ID = ? AND D.DZLB = ?");
        params.add(jbxxId);
        params.add(dzlb);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(),null);
        StringBuffer address = new StringBuffer("");
        /*if(){
            address =  map.get("ADDRESS")==null?"": map.get("ADDRESS").toString();
        }*/
        for(int i=0;i<list.size();i++){
            if(i != 0){
                address.append(";");
            }
            address.append(list.get(i).get("ADDRESS")==null?"": list.get(i).get("ADDRESS").toString());
        }
        return address.toString();
    }
    /**
     * 
     * 描述
     * @author Usher Song
     * @created 2016年9月13日 上午11:13:27
     * @param jbxxId
     * @param dzlb
     * @return
     */
    public Map<String,Object> getAdderssMap(String jbxxId,int dzlb){
        StringBuffer sql = new StringBuffer("select DZXX_ID,");
        List<Object> params = new ArrayList<Object>();
        sql.append(" CLNMPHM AS ADDRESS ");
        sql.append(" from T_FJFDA_SPSCXK_DZXX D  ");
        sql.append(" WHERE D.JBXX_ID = ? AND D.DZLB = ?");
        params.add(jbxxId);
        params.add(dzlb);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(),null);
       
        return list.size()>0?list.get(0):null;
    }
    
    /**
     * 获取生产地址
     * @author John Zhang
     * @created 2016年9月13日 下午2:21:12
     * @param jbxxId
     * @param dzlb
     * @return
     */
    public List<Map<String,Object>> getScdzs(String jbxxId,int dzlb){
        StringBuffer sql = new StringBuffer("select DZXX_ID,");
        List<Object> params = new ArrayList<Object>();
        sql.append(" CLNMPHM AS ADDRESS ");
        sql.append(" from T_FJFDA_SPSCXK_DZXX D  ");
        sql.append(" WHERE D.JBXX_ID = ? AND D.DZLB = ?");
        params.add(jbxxId);
        params.add(dzlb);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(),null);
       
        return list;
    }
    
    @Override
    public Map<String, Object> saveZzxxData(Map<String, Object> flowDatas) {
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
           //住所地址
        flowDatas.put("ZS", getAdderss(busRecordId, 1));
        //生产地址
        flowDatas.put("SCDZ", getAdderss(busRecordId, 2));
        //仓库地址
        flowDatas.put("WSCKDZ", getAdderss(busRecordId, 3));
        
        flowDatas.put("JBXX_ID", busRecordId);
        dao.saveOrUpdate(flowDatas, "T_FJFDA_SPSCXK_ZZXX", "");
        return flowDatas;
    }
    /**
     * 描述 保存加工场所信息
     * @author John Zhang
     * @created 2016年8月15日 下午5:38:50
     * @param flowDatas
     */
    private void setJgcsxx(Map<String, Object> flowDatas, String busRecordId){
        String isStartNode = flowDatas.get("EFLOW_IS_START_NODE") == null ? "false" : flowDatas.get(
                "EFLOW_IS_START_NODE").toString();
        if ("true".equals(isStartNode)) {
            // 保存信息前，先删除
            dao.remove("T_FJFDA_SPSCXK_JGCSXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        } else {
            // 保存信息前，先删除
            dao.remove("T_FJFDA_SPSCXK_JGCSXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        }
               
        String jgcsxxJson = (String) flowDatas.get("JGCSXXJson");
        if (StringUtils.isNotEmpty(jgcsxxJson)) {
            List<Map> jgcsList = JSON.parseArray(jgcsxxJson, Map.class);
            for (int i = 0; i < jgcsList.size(); i++) {
                Map<String, Object> productCpxxMap = jgcsList.get(i);
                productCpxxMap.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(productCpxxMap, "T_FJFDA_SPSCXK_JGCSXX", "");
            }
        }
    }

    /**
     * 描述 获取加工场所信息
     * @author John Zhang
     * @created 2016年8月16日 上午10:50:21
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#findJgcs(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findJgcs(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_JGCSXX J ");
        sql.append("WHERE J.JBXX_ID=? ORDER BY J.LBBH,J.JGXH ASC");
        params.add(jbxxId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    

    /**
     * 描述
     * @author John Zhang
     * @created 2016年8月23日 上午9:50:29
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#getScdzList(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getDzList(String jbxxId, String dzlb) {
        String sql = "select DZXX_ID,SQZZZM JYCSQS,XZZXXJS JYCSXS,XZJD JYCSXZ,CLNMPHM JYXXDZ"
                +" from T_FJFDA_SPSCXK_DZXX D WHERE D.JBXX_ID=? AND D.DZLB=?";
        return this.dao.findBySql(sql, new Object[]{jbxxId,dzlb}, null);
    }

    /**
     * 描述 根据社会信用代码 查询已通过审核的食品生产基本信息  SC证只能一条是有效的
     * @author John Zhang
     * @created 2016年8月23日 下午5:03:04
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#findByShxydm()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findByShxydm(String shxydm, String jbxx_id) {
        String sql = "select * from T_FJFDA_SPSCXKXX where SHXYDMSFZHM = ? and XKZZT = '10'";
        List<Map<String, Object>> xkxxList = dao.findBySql(sql,
                new Object[]{shxydm}, null);
        Map<String, Object> xkxx = null;
        for (Map<String, Object> map : xkxxList) {  //有效的SC证书一企一证
            if(map.get("XKZBH").toString().indexOf("SC")>=0){
                xkxx = map;
                break;
            }
        }
        if(xkxx == null){
            return xkxx;
        }
        //String jbxxId = xkxx.get("JBXX_ID")==null?"":xkxx.get("JBXX_ID").toString();
            if(jbxx_id != null && jbxx_id.equals((String)xkxx.get("JBXX_ID"))){
                return null;
            }
            xkxx.put("CPXXLIST", this.dao.findBySql("select * from t_fjfda_spscxkxx_cp where zzxx_id=? order by cpxh",
                        new Object[]{xkxx.get("ZZXX_ID")}, null));
        return xkxx;
    }
    /**
     * 描述 获取保健食品 产品信息
     * @author John Zhang
     * @created 2016年9月6日 下午5:22:48
     */
    @Override
    public List<Map<String, Object>> getBjCpxxInfo(String jbxxId){
        List<Map<String, Object>> cpxxList = getHealthCpxx(jbxxId);
        Map<String, Object> result = new HashMap<String, Object>();
        StringBuffer pzmx = new StringBuffer("");
        StringBuffer pzwh = new StringBuffer("");
        for (int i = 0; i < cpxxList.size(); i++) {
            if( i > 0 ){
                pzmx.append("、"); 
                pzwh.append("、");
            }
            pzmx.append(cpxxList.get(i).get("CPMC"));
            pzwh.append(cpxxList.get(i).get("PZWH"));
        }
        result.put("CPXX_SPLB", "保健食品");
        result.put("CPXX_SPLB_NAME", "保健食品");
        result.put("CPXX_SPBH", "27");
        result.put("CPXX_LBBH", "2701");
        result.put("CPXX_LBBH_CODE", "2701");
        result.put("CPXX_LBMC", "保健食品");//CPXX_PZMX ,CPXX_CPZXBZWH
        result.put("CPXX_PZMX", pzmx.toString());
        result.put("CPXX_CPZXBZWH",pzwh.toString());
        result.put("CPXX_DEPTNAME", "福建省食品药品监督管理局");
        List<Map<String,Object>> bjspCpList=new ArrayList<Map<String,Object>>();
        bjspCpList.add(result);
        return bjspCpList;
    }
    /**
     * 描述注销流程 后置事件  审核通过后
     * @author John Zhang
     * @created 2016年8月23日 下午5:18:03
     * @param flowvars
     * @see net.evecom.platform.business.service.FoodProductionService#updateZzxxState(java.util.Map)
     */
    @Override
    public Map<String, Object> updateZzxxState(Map<String, Object> flowvars) {
        if("false".equals(flowvars.get("EFLOW_ISBACK")) || 
                flowvars.get("EFLOW_ISBACK") == null){
            Map<String, Object> xkxx = this.getByJdbc("T_FJFDA_SPSCXKXX",
                    new String[]{"XKZBH","XKZZT"}, new Object[]{flowvars.get("XKZBH_OLD"),"10"});
            if(xkxx != null){
                xkxx =  new HashMap<String, Object>();
                xkxx.put("XKZZT", "60");
            }
            xkxx.putAll(flowvars);
            dao.saveOrUpdate(xkxx, "T_FJFDA_SPSCXKXX", (String)xkxx.get("ZZXX_ID"));
        }
        return  flowvars;
    }
    

    /**
     * 描述 
     * @author John Zhang
     * @created 2016年8月23日 下午7:44:54
     * @param flowvars
     * @see net.evecom.platform.business.service.FoodProductionService#updateXkzbh(java.util.Map)
     */
    @Override
    public void updateXkzbh(Map<String, Object> flowvars) {
        if("false".equals(flowvars.get("EFLOW_ISBACK")) || flowvars.get("EFLOW_ISBACK") == null){
            String sqlTemp = "update T_FJFDA_SPSCXKXX_TEMP t set t.XKZBH=? where t.JBXX_ID = ?";
            dao.executeSql(sqlTemp, new Object[]{flowvars.get("XKZBH").toString(),
                    flowvars.get("JBXX_ID").toString()});
        }
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2016年8月23日 下午7:55:39
     * @param flowvars
     * @see net.evecom.platform.business.service.FoodProductionService#addTempXkxx(java.util.Map)
     */
    @Override
    public Map<String, Object> addTempXkxx(Map<String, Object> flowvars) {
        if("false".equals(flowvars.get("EFLOW_ISBACK")) || 
                flowvars.get("EFLOW_ISBACK") == null){
            Map<String,Object> tempMap = new HashMap<String, Object>();
            
            tempMap.put("SQLB", "0");
            tempMap.put("ISPROVINCE", flowvars.get("ISPROVINCE"));
            if("1".equals(flowvars.get("SQLB"))){
                tempMap.put("ISPROVINCE", "1");
            }
            tempMap.put("JBXX_ID", flowvars.get("BUS_RECORDID"));
            tempMap.put("XKZBH", flowvars.get("XKZBH"));
            tempMap.put("SHXYDMSFZHM", flowvars.get("SHXYDMSFZHM"));
            tempMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            tempMap.put("ZZXX_STATE", flowvars.get("ZZXX_STATE"));
            dao.saveOrUpdate(tempMap, "T_FJFDA_SPSCXKXX_TEMP", "");
        }
        return flowvars;
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2016年8月24日 下午4:12:49
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#getHealthCpxx(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> getHealthCpxx(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_BJSPSCXK_CPXX C ");
        sql.append("WHERE C.JBXX_ID=? ");
        params.add(jbxxId);
        sql.append(" ORDER BY C.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     *  
     * 描述 获取行政区划编码
     * @author Usher Song
     * @created 2017年3月1日 下午6:07:04
     * @param jbxxId
     * @return
     */
    private void putXzqhbm(String jbxxId,Map<String,Object> productInfo) {
        String xzqhbm = "";
        String sqzzzm = "";
        List<Map<String,Object>> scdzList = this.getDzList(jbxxId,"2");
        if(scdzList!=null&&scdzList.size()>0){
            xzqhbm = scdzList.get(0).get("JYCSXS").toString();
            sqzzzm = scdzList.get(0).get("JYCSQS").toString();
            //行政区划编码特殊处理
            if(sqzzzm.equals("350128")){ 
                //平潭
                xzqhbm ="350128";
            }else if(xzqhbm.equals("3509001")){
               //宁德东桥经济开发区
                xzqhbm ="350900";
            }
        }
        productInfo.put("XZQHBM", xzqhbm);
        productInfo.put("CITY_CODE", sqzzzm);
    }
    
    /**
     * 
     * 描述 保存证照数据
     * @author Usher Song
     * @created 2016年8月19日 下午3:29:37
     * @param liceseData
     * @param printConfigMap
     */
    @SuppressWarnings("unchecked")
    @Override
    public String saveOrUpdateToResult(Map<String, Object> liceseData, Map<String, Object> printConfigMap) {
        String itemCode = (String) liceseData.get("ITEM_CODE");
        liceseData.put("DEPART_ID", AppUtil.getLoginUser().getDepId());
        String zzxxId = "";
        String xkzbh = (String)liceseData.get("XKZBH");
        Map<String,Object> resultMap = this.getByJdbc("t_fjfda_spscxkxx", 
                new String[]{"XKZBH","XKZZT"}, new Object[]{xkzbh,"10"});
        dao.executeSql("update T_FJFDA_SPSCXKXX set xkzzt='70',BMCX_SYN_FLAG='2',"
                + "state_change_reason='换发证书' where xkzbh = ? and xkzzt='10'",
                new Object[]{liceseData.get("XKZBH_OLD")});
        if("350128".equals(xkzbh.substring(5,11))){
            liceseData.put("ZSQS", "350128");
            liceseData.put("ZSQS", "350128");
        }else{
            liceseData.put("ZSXS", xkzbh.substring(5, 11));
            liceseData.put("ZSQS", xkzbh.substring(5, 9)+"00");
        }
        if(resultMap!=null){
            zzxxId = resultMap.get("ZZXX_ID").toString();
            if(liceseData.get("JBXX_ID").toString().equals((String)resultMap.get("JBXX_ID"))){
                if("001XK00201".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "首次");
                }
                if("001XK00202".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "简单变更");
                }
                if("001XK00203".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "一般变更");
                }
                if("001XK00204".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "延续");
                }
                if("001XK00205".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "补办");
                }
                if("001XK00206".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "注销");
                }
                if("001XK00207".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "保健食品首次");
                }
                if("001XK00208".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "保健食品变更");
                }
                if("001XK00209".equals(itemCode)){
                    resultMap.put("STATE_CHANGE_REASON", "保健食品换证");
                }
                resultMap.putAll(liceseData);
                dao.saveOrUpdate(resultMap, "T_FJFDA_SPSCXKXX", zzxxId);
            }else{
                Map<String,Object> updateStateMap = new HashMap<String,Object>();
                updateStateMap.put("XKZZT", "70");
                dao.saveOrUpdate(updateStateMap, "T_FJFDA_SPSCXKXX", zzxxId);
                zzxxId = dao.saveOrUpdate(liceseData, "T_FJFDA_SPSCXKXX", null);
            }
        }else{
            zzxxId = dao.saveOrUpdate(liceseData, "T_FJFDA_SPSCXKXX", null);
        }
        Map<String,Object> jbxx = this.getByJdbc("T_FJFDA_SPSCXK_JBXX", 
                new String[]{"JBXX_ID"}, new Object[]{liceseData.get("JBXX_ID")});
        if(jbxx != null){
            liceseData.remove("CREATE_TIME");
            liceseData.remove("XKZBH_OLD");
            jbxx.putAll(liceseData);
            dao.saveOrUpdate(jbxx, "T_FJFDA_SPSCXK_JBXX", (String)jbxx.get("JBXX_ID"));
        }
        //合并打印
        /*if(liceseData.get("isMergePrint")!=null){
             if(resultMap!=null&&resultMap.get("MERGE_JBXX_ID")!=null){
                 liceseData.put("MERGE_JBXX_ID", resultMap.get("MERGE_JBXX_ID"));
             }
            liceseData.put("CPXXLIST",this.getMergeCpxx(newJbxxId, zzxxId));
        }
        //产品信息
        List<Map<String,Object>> cpxxList = (List<Map<String, Object>>) liceseData.get("CPXXLIST");
        //食品生产许可（新办）、换证  保健食品生产换证
        if(itemCode.equals("001XK00201")||itemCode.equals("001XK00209")||itemCode.equals("001XK00207")){
                //如果存在编号，则更新结果表
           if(StringUtils.isNotEmpty(zzxxId)){
                  liceseData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                   //如果新产生的jbxx_id和结果表相同，说明是重复打证，直接更新结果表
                  boolean curIsBj = false;
                    dao.saveOrUpdate(liceseData, "T_FJFDA_SPSCXKXX", zzxxId);
                    dao.remove("T_FJFDA_SPSCXKXX_CP", "ZZXX_ID", new Object[]{zzxxId});
                    Map<String,Object> updateStateMap = new HashMap<String,Object>();
                    updateStateMap.put("XKZZT", "70");
                    updateStateMap.put("STATE_CHANGE_REASON", "已办理过新证");
                    dao.saveOrUpdate(updateStateMap, "T_FJFDA_SPSCXKXX", zzxxId);
                    zzxxId = dao.saveOrUpdate(liceseData, "T_FJFDA_SPSCXKXX", null);
            }else{
                zzxxId = dao.saveOrUpdate(liceseData, "T_FJFDA_SPSCXKXX", null);
          }
        }else{
          
        }*/
        dao.remove("T_FJFDA_SPSCXKXX_CP", "ZZXX_ID", new Object[]{zzxxId});
      //产品信息
        List<Map<String,Object>> cpxxList = (List<Map<String, Object>>) liceseData.get("CPXXLIST");
        //保存产品信息
        for (Map<String, Object> cpxx : cpxxList) {
            cpxx.put("ZZXX_ID", zzxxId);
            dao.saveOrUpdate(cpxx, "T_FJFDA_SPSCXKXX_CP", null);
        }
        return zzxxId;
    }
    
    /**
     * 
     * 描述
     * @author Usher Song
     * @created 2016年8月24日 上午11:29:15
     * @param xkzbh
     * @return
     */
    public boolean isLicenseCodeExist(String xkzbh) {
        StringBuffer sql = new StringBuffer(
                "SELECT T.ZZXX_ID FROM T_FJFDA_SPSCXKXX T WHERE T.XKZBH=? ");
        List<Map<String, Object>> list = 
                dao.findBySql(sql.toString(), new Object[] {xkzbh}, null);
        if (list.size() < 1)
            return false;
        else
            return true;
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2016年8月30日 下午3:23:28
     * @param xhxydm
     * @param itemCode
     * @param exeId
     * @return
     * @see net.evecom.platform.business.service.
     * FoodProductionService#getCountByShxydm(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> getCountByShxydm(String shxydm, String itemCode, String exeId, String hasHealth) {
        Map<String, Object> spscxkxx = this.getByJdbc("T_FJFDA_SPSCXKXX", 
                new String[]{"SHXYDMSFZHM","HAS_HEALTH"},
                new Object[]{shxydm,hasHealth});
        List<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT E.EXE_ID FROM JBPM6_EXECUTION E WHERE E.RUN_STATUS IN (1) ");
        sql.append("AND E.BUS_TABLENAME = 'T_FJFDA_SPSCXK_JBXX' ");
       
        if("0".equals(hasHealth)){
            sql.append("AND E.ITEM_CODE IN ('001XK00203','001XK00202','001XK00204','001XK00205','001XK00206') ");
        }else{
            sql.append("AND E.ITEM_CODE IN ('001XK00208','001XK00209') ");
        }
        if(StringUtils.isNotEmpty(exeId)){
            sql.append(" AND E.EXE_ID !=? ");
            params.add(exeId);
        }
        List<Map<String,Object>> exeList = dao.findBySql(sql.toString(), params.toArray(), null);
        if(spscxkxx == null || exeList.size() > 0){
            return null;
        }else{
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("spscxkxx", spscxkxx);
            resultMap.put("exeList", exeList);
            return resultMap;
        }
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2016年9月1日 上午10:27:56
     * @param shxydm
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#getByShxydm(java.lang.String)
     */
    @Override
    public Map<String, Object> getByShxydm(String shxydm, String isHealth) {
        Map<String, Object> spscxkxx = this.getByJdbc("T_FJFDA_SPSCXKXX", 
                new String[]{"SHXYDMSFZHM","XKZZT"},
                new Object[]{shxydm,"10"});
        if(spscxkxx != null){
            if(this.getZzCp(spscxkxx.get("ZZXX_ID").toString(), 
                    isHealth).size() > 0){
                return spscxkxx;
            }else{
                return null;
            }
        }
        return spscxkxx;
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2016年9月1日 上午11:47:36
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#findSbss(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findSbss(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_SBSS J ");
        sql.append("WHERE J.JBXX_ID=? ORDER BY J.LBMC,J.SBXH ASC");
        params.add(jbxxId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 描述 地址信息另存
     * @author John Zhang
     * @created 2016年9月16日 下午1:19:35
     * @param dzxxId
     */
    private void saveOtherDzxx(Object dzxxId,String newJbxxId){
        Map<String, Object> dzxx = dao.getByJdbc("T_FJFDA_SPSCXK_DZXX", new String[]{"DZXX_ID"},
                new Object[]{dzxxId});
        if(dzxx == null){
            return;
        }
        if(newJbxxId.equals(dzxx.get("JBXX_ID").toString())){
            if("1".equals((String)dzxx.get("SPLB"))){
                return;
            }
        }else{
            dzxx.put("DZXX_ID", null);
            dzxx.put("JBXX_ID",newJbxxId);
        }
        dao.saveOrUpdate(dzxx, "T_FJFDA_SPSCXK_DZXX",(String)dzxx.get("DZXX_ID"));
    }
    /**
     * 描述 合并数据 
     * @author John Zhang
     * @created 2016年9月7日 下午5:03:19
     * @param zzJbxxId 旧数据jbxxid
     * @param curJbxxId 新数据jbxxid
     * @param newJbxxId 合并后基本信息id
     */
    private void mergerData(String zzJbxxId, String curJbxxId, String newJbxxId) {
        List<Map<String,Object>> oldScdzs = this.getDzList(zzJbxxId,"2");
        List<Map<String,Object>> newScdzs = this.getDzList(curJbxxId,"2");
        List<Map<String, Object>> scdzResult = compareScdz(newJbxxId, oldScdzs, newScdzs);
        List<Map<String,Object>> oldCpxx = this.findCpxx(zzJbxxId,null,null);
        List<Map<String,Object>> newCpxx = this.findCpxx(curJbxxId,null,null);
        newCpxx.addAll(oldCpxx);
        List<Map<String,Object>> oldJgcs = this.findJgcs(zzJbxxId);
        List<Map<String,Object>> newJgcs = this.findJgcs(curJbxxId);
        newJgcs.addAll(oldJgcs);
        List<Map<String,Object>> oldJSbss = this.findSbss(zzJbxxId);
        List<Map<String,Object>> newSbss = this.findSbss(curJbxxId);
        newSbss.addAll(oldJSbss);
        dao.remove("T_FJFDA_SPSCXK_DZXX", new String[]{"JBXX_ID"}, new Object[]{newJbxxId});
        dao.remove("T_FJFDA_SPSCXK_CPXX", new String[]{"JBXX_ID"}, new Object[]{newJbxxId});
        dao.remove("T_FJFDA_SPSCXK_JGCSXX", new String[]{"JBXX_ID"}, new Object[]{newJbxxId});
        dao.remove("T_FJFDA_SPSCXK_SBSS", new String[]{"JBXX_ID"}, new Object[]{newJbxxId});
        for (int i = 0; i < scdzResult.size(); i++) {
            dao.saveOrUpdate(scdzResult.get(i), "T_FJFDA_SPSCXK_DZXX", null);
        }
        for (int i = 0; i < newCpxx.size(); i++) {
            newCpxx.get(i).put("JBXX_ID", newJbxxId);
            newCpxx.get(i).put("CPXX_ID", null);
            dao.saveOrUpdate(newCpxx.get(i), "T_FJFDA_SPSCXK_CPXX", null);
        }
        for (int i = 0; i < newJgcs.size(); i++) {
            newJgcs.get(i).put("JBXX_ID", newJbxxId);
            newJgcs.get(i).put("JGCS_ID", null);
            dao.saveOrUpdate(newJgcs.get(i), "T_FJFDA_SPSCXK_JGCSXX", null);
        }
        for (int i = 0; i < newSbss.size(); i++) {
            newSbss.get(i).put("JBXX_ID", newJbxxId);
            newSbss.get(i).put("SBSS_ID", null);
            dao.saveOrUpdate(newSbss.get(i), "T_FJFDA_SPSCXK_SBSS", null);
        }
    }

    /**
     * 描述 获得合并后的数据
     * @author John Zhang
     * @created 2016年9月13日 下午3:58:16
     * @param curJbxxId
     * @param oldJbxxId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMergeCpxx(String curJbxxId, String zzxxId){
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();;
        if(!StringUtils.isEmpty(curJbxxId) && !StringUtils.isEmpty(zzxxId)){
            Map<String,Object> oldZzxx = this.getByJdbc("T_FJFDA_SPSCXKXX", 
                new String[]{"ZZXX_ID"},  new Object[]{zzxxId});
           /* Map<String,Object> curJbxx = this.getByJdbc("T_FJFDA_SPSCXK_JBXX", 
                new String[]{"JBXX_ID"},  new Object[]{curJbxxId});*/
            if(oldZzxx!= null){
                List<Map<String, Object>> curCpList = this.findCpxx(curJbxxId, null, null);
                List<Map<String, Object>> cpxxList = dao.findBySql(
                        "select * from T_FJFDA_SPSCXKXX_CP where ZZXX_ID =? order by cpxh", 
                        new Object[]{zzxxId}, null);
                Iterator<Map<String,Object>> oldIt = cpxxList.iterator();
                List<String> jbxxIds = new ArrayList<String>();
                jbxxIds.add(curJbxxId);
                while(oldIt.hasNext()){
                    Map<String,Object> cp = oldIt.next();
                    for (Map<String, Object> curCp : curCpList) {
                        if(cp.get("SPSCXKXX_CP_ID").toString().equals((String)curCp.get("SPSCXKXX_CP_ID"))){
                            oldIt.remove();
                            if(!StringUtils.isEmpty((String)cp.get("JBXX_ID"))){
                                jbxxIds.add((String)cp.get("JBXX_ID"));
                            }
                            break;
                        }
                        if(cp.get("CPXX_LBBH").toString().equals((String)curCp.get("CPXX_LBBH"))){
                            if(cp.get("CPXX_PZMX").toString().equals(((String)curCp.get("CPXX_PZMX")))){
                                oldIt.remove();
                                break;
                            }
                        }
                    }
                }
                oldIt = cpxxList.iterator();
                while(oldIt.hasNext()){
                    Map<String,Object> cp = oldIt.next();
                    for (String jbxxId : jbxxIds) {
                        if(jbxxId.equals((String)cp.get("JBXX_ID"))){
                            oldIt.remove();
                            break;
                        }
                    }
                }
                result.addAll(cpxxList);
                result.addAll(curCpList);
                /*if(curJbxx.get("FROM_JBXX_ID")!= null){//从哪个基本信息变更过来
                    if(curJbxx.get("FROM_JBXX_ID").toString().equals(oldJbxxId)){//若当前基本信息是从历史证照数据变更过来
                        return this.getCpxxInfo(curJbxxId, "");
                    }else{ //先剔除对方被删除的产品  然后合并数据
                        List<Map<String,Object>> newList = this.getCpxxInfo(curJbxxId, "");
                        List<Map<String,Object>> oldList = this.getCpxxInfo(oldJbxxId,"");
                        List<Map<String, Object>> newRemoveItems = findRemoveCpxx(newList,
                                curJbxx.get("FROM_JBXX_ID").toString());
                        List<Map<String, Object>> oldRemoveItems = findRemoveCpxx(oldList,
                                oldJbxx.get("FROM_JBXX_ID").toString());
                        this.removeCpxx(newList, oldRemoveItems);
                        this.removeCpxx(oldList, newRemoveItems);
                        return this.mergeCpxx(newList, oldList);
                    }
                }else{//为null 则为新开办  直接返回当前申请跟历史证照数据产品集合
                    return mergeCpxx(this.getCpxxInfo(curJbxxId, ""),this.getCpxxInfo(oldJbxxId, ""));
                }*/
            }
        }
        return result;
    }
    
    /**
     * 描述 合并产品记录
     * @author John Zhang
     * @created 2016年9月13日 下午3:37:42
     * @return
     */
    public List<Map<String, Object>> mergeCpxx(List<Map<String,Object>> newList,List<Map<String,Object>> oldList){
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result.addAll(newList);
        for (Map<String, Object> omap : oldList) {
            boolean exists = false;
            for (Map<String, Object> nmap : result) {
                String oldCpxxLbbh = omap.get("CPXX_LBBH").toString();
                String newCpxxLbbh = nmap.get("CPXX_LBBH").toString();
                if(oldCpxxLbbh.equals(newCpxxLbbh)){
                    exists = true;
                    String newCpxxMx = nmap.get("CPXX_PZMX").toString();
                    String oldCpxxMx = omap.get("CPXX_PZMX").toString();
                    String newCpxxMxId = nmap.get("CPXX_PZMXID").toString();
                    String oldCpxxMxId = omap.get("CPXX_PZMXID").toString();
                    nmap.putAll(mergeMx(newCpxxMx,oldCpxxMx,newCpxxMxId,oldCpxxMxId));
                    break;
                }
            }
            if(!exists){
                result.add(omap);
            }
        }
        return result;
    }
    /**
     * 描述 合并产品明细
     * @author John Zhang
     * @created 2016年9月13日 下午6:58:51
     * @param newCpxxMx
     * @param oldCpxxMx
     * @param newMxIds
     * @param oldMxIds
     * @return
     */
    public Map<String,Object> mergeMx(String newCpxxMx,String oldCpxxMx, String newMxIds, String oldMxIds){
        LinkedList<String> newCpxxList = new LinkedList<String>();
        String[] newCpxxName = newCpxxMx.replaceAll(" ", ",").split(",");
        String[] oldCpxxName = oldCpxxMx.replaceAll(" ", ",").split(",");
        String[] newCpxxMxIds = newMxIds.split(",");
        String[] oldCpxxMxIds = oldMxIds.split(",");
        LinkedList<String> newCpxxIdList = new LinkedList<String>();
        for (int i = 0; i < newCpxxMxIds.length; i++) {
            newCpxxIdList.add(newCpxxMxIds[i]);
            newCpxxList.add(newCpxxName[i]);
        }
        for (int i = 0; i < oldCpxxMxIds.length; i++) {
            if(!newCpxxIdList.contains(oldCpxxMxIds[i])){
                newCpxxIdList.add(i,oldCpxxMxIds[i]);
                newCpxxList.add(oldCpxxName[i]);
            }
        }
        StringBuffer mxName = new StringBuffer("");
        StringBuffer mxIds = new StringBuffer("");
        for (int i = 0;i < newCpxxIdList.size();i++) {
            if(i>0){
                mxName.append(",");
                mxIds.append(",");
            }
            mxName.append(newCpxxList.get(i));
            mxIds.append(newCpxxIdList.get(i));
        }
        String names = mxName.toString().replaceAll("\\[,", "\\[ ");
        Map<String, Object> result = new HashMap<String ,Object>();
        result.put("CPXX_PZMX",names);
        result.put("CPXX_PZMXID",mxIds.toString());
        return result;
    }
    
    /**
     * 描述 查询被删除产品
     * @author John Zhang
     * @created 2016年9月13日 下午3:57:53
     * @param curJbxxId
     * @param oldJbxxId
     * @return 
     */
    public List<Map<String, Object>> findRemoveCpxx(List<Map<String, Object>> cpxxList, String fromJbxxId){
        List<Map<String, Object>> oldCpxxList = this.findCpxx(fromJbxxId, "", "");
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> omap : oldCpxxList) {
            boolean isCompared = false;
            for (Map<String, Object> nmap : cpxxList) {
                if(omap.get("CPXX_LBBH").toString().equals(nmap.get("CPXX_LBBH").toString())){
                    isCompared = true;
                    String oldCpxxMx = omap.get("CPXX_PZMX").toString();
                    String newCpxxMxId = nmap.get("CPXX_PZMXID").toString();
                    String oldCpxxMxId = omap.get("CPXX_PZMXID").toString();
                    String[] oldCpxxName = oldCpxxMx.replaceAll(" ", ",").split(",");
                    String[] newCpxxMxIds = newCpxxMxId.split(",");
                    String[] oldCpxxMxIds = oldCpxxMxId.split(",");
                    List<String> newCpxxList = new ArrayList<String>();
                    for (int i = 0; i < newCpxxMxIds.length; i++) {
                        newCpxxList.add(newCpxxMxIds[i]);
                    }
                    for (int i = 0; i < oldCpxxMxIds.length; i++) {
                        if(!newCpxxList.contains(oldCpxxMxIds[i])){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("mx", oldCpxxName[i]);
                            map.put("mxId", oldCpxxMxIds[i]);
                            items.add(map);
                        }
                    }
                }
            }
            if(!isCompared){
                String oldCpxxMx = omap.get("CPXX_PZMX").toString();
                String oldCpxxMxId = omap.get("CPXX_PZMXID").toString();
                String[] oldCpxxName = oldCpxxMx.replaceAll(" ", ",").split(",");
                String[] oldCpxxMxIds = oldCpxxMxId.split(",");
                for (int i = 0; i < oldCpxxMxIds.length; i++) {
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("mx", oldCpxxName[i]);
                    map.put("mxId", oldCpxxMxIds[i]);
                    items.add(map);
                }
            }
        }
        return items;
    }
    /**
     * 描述 删除产品
     * @author John Zhang
     * @created 2016年9月13日 下午8:28:05
     * @param cpxxList
     * @param removeItems
     */
    public void removeCpxx(List<Map<String, Object>> cpxxList,List<Map<String, Object>> removeItems){
        for (Map<String, Object> item : removeItems) {
            Iterator<Map<String, Object>> it = cpxxList.iterator();
            while(it.hasNext()){
                Map<String, Object> nmap = it.next();
                String newCpxxMxId = nmap.get("CPXX_PZMXID").toString();
                String newCpxxMx = nmap.get("CPXX_PZMX").toString();
                if(newCpxxMxId.contains(item.get("mxId").toString())){
                    newCpxxMx = newCpxxMx.replace(item.get("mx").toString(), "");
                    newCpxxMxId = newCpxxMxId.replace(item.get("mxId").toString(), "");
                    Pattern p = Pattern.compile("[,]+");  
                    Matcher m1 = p.matcher(newCpxxMx);
                    Matcher m2 = p.matcher(newCpxxMxId);
                    newCpxxMx = m1.replaceAll(",");
                    newCpxxMxId = m2.replaceAll(",");
                    nmap.put("CPXX_PZMX", newCpxxMx);
                    nmap.put("CPXX_PZMXID", newCpxxMxId);
                    if(newCpxxMx.equals(",")){
                        it.remove();
                    }
                }
            }
        }
    }
    
    /**
     * 描述 比较生产地址
     * @author John Zhang 
     * @created 2016年9月6日 下午4:43:07
     * @param newJbxxId
     * @param oldScdzs
     * @param newScdzs
     * @return
     */
    private List<Map<String, Object>> compareScdz(String newJbxxId, 
            List<Map<String, Object>> oldScdzs, List<Map<String, Object>> newScdzs) {
        List<Map<String, Object>> scdzResult = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> scdz : newScdzs) {
            //select SQZZZM JYCSQS,XZZXXJS JYCSXS,XZJD JYCSXZ,CLNMPHM JYXXDZ
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("JBXX_ID", newJbxxId);
            result.put("SQZZZM", scdz.get("JYCSQS"));
            result.put("XZZXXJS", scdz.get("JYCSXS"));
            result.put("XZJD", scdz.get("JYCSXZ"));
            result.put("CLNMPHM", scdz.get("JYXXDZ"));
            result.put("DZLB", "2");
            scdzResult.add(result);
            for (int i = oldScdzs.size()-1;i >= 0; i--) {
                Map<String, Object> old = oldScdzs.get(i);
                boolean isDiff = false;
                for(Entry<String, Object> entry:scdz.entrySet()){
                    String oldValue = (String) old.get(entry.getKey());
                    String newValue = (String) entry.getValue();
                    if(newValue != null && !newValue.equals(oldValue)){
                        isDiff = true;
                        break;
                    }
                }
                if(!isDiff){
                    oldScdzs.remove(i);
                }
            }
        }
        for (Map<String, Object> scdz : oldScdzs) {
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("JBXX_ID", newJbxxId);
            result.put("SQZZZM", scdz.get("JYCSQS"));
            result.put("XZZXXJS", scdz.get("JYCSXS"));
            result.put("XZJD", scdz.get("JYCSXZ"));
            result.put("CLNMPHM", scdz.get("JYXXDZ"));
            result.put("DZLB", "2");
            scdzResult.add(result);
        }
        return scdzResult;
    }

    /**
     * 
     * 描述 获取证照确认页面数据
     * @author Usher Song
     * @created 2016年8月30日 上午11:57:42
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> getPrintJumpPageData(HttpServletRequest request) {
        String publishDatas =  request.getParameter("publishDatas");
        JSONArray publishDatasArray = JSONArray.parseArray(publishDatas);
        Map<String,Object> fzxxInfo = new HashMap<String,Object>();
        if(publishDatasArray!=null&&publishDatasArray.size()>0){
            JSONObject obj = (JSONObject) publishDatasArray.get(0);
            if(obj.get("XKZBH")!=null){
                fzxxInfo.put("XKZBH", obj.get("XKZBH"));
            }
            fzxxInfo.put("WSCKDZ", obj.get("WSCKDZ"));
            fzxxInfo.put("WSCKDZID", obj.get("WSCKDZID"));
            fzxxInfo.put("ZS", obj.get("ZS"));
            fzxxInfo.put("ZSDZID", obj.get("ZSDZID"));
            fzxxInfo.put("SCDZ", obj.get("SCDZ"));
            fzxxInfo.put("SCDZID", obj.get("SCDZID"));
            fzxxInfo.put("SPLB", obj.get("SPLB"));
            fzxxInfo.put("FDDBRXM", obj.get("FDDBRXM"));
            fzxxInfo.put("SQRMC", obj.get("SQRMC"));

            if(obj.get("isMergePrint")!=null&&obj.get("isMergePrint").equals("true")){
                if(obj.get("ZSDZID")!=null){
                   fzxxInfo.put("ZS", this.getAddressById(obj.get("ZSDZID").toString()));
                }
                if(obj.get("WSCDZID")!=null){
                    String[] wsckdzIds = obj.get("WSCDZID").toString().split(",");
                    StringBuilder wsckdzStr = new StringBuilder();
                    for (String wsckdzId : wsckdzIds) {
                        wsckdzStr.append(this.getAddressById(wsckdzId)+"，");
                    }
                    fzxxInfo.put("WSCKDZ", wsckdzStr.substring(0,wsckdzStr.lastIndexOf("，")));
                 }  
                if(obj.get("SCDZID")!=null){
                   String[] scdzIds = obj.get("SCDZID").toString().split(",");
                   StringBuilder scdzStr = new StringBuilder();
                   for (String scdzId : scdzIds) {
                       scdzStr.append(this.getAddressById(scdzId)+"，");
                   }
                   fzxxInfo.put("SCDZ", scdzStr.substring(0,scdzStr.lastIndexOf("，")));
                }
            }
        }
        return fzxxInfo;
    }
    
    
    /**
     * 
     * 描述
     * @author Usher Song
     * @created 2016年9月13日 上午11:13:27
     * @param jbxxId
     * @param dzlb
     * @return
     */
    public String getAddressById(String dzxxId){
        StringBuffer sql = new StringBuffer("select (SELECT '福建省'||T.TYPE_NAME FROM "
                + "T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE = D.SQZZZM) ");
        List<Object> params = new ArrayList<Object>();
        sql.append(" || (SELECT T.TYPE_NAME FROM T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE = D.XZZXXJS) ");
        sql.append(" || (SELECT T.DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY T WHERE T.DIC_CODE = D.XZJD) ");
        sql.append(" ||CLNMPHM AS ADDRESS ");
        sql.append(" from T_FJFDA_SPSCXK_DZXX D  ");
        sql.append(" WHERE D.DZXX_ID = ? ");
        params.add(dzxxId);
        Map<String,Object> addressMap = dao.getMapBySql(sql.toString(), params.toArray());
        return addressMap!=null?addressMap.get("ADDRESS").toString():null;
    }

    @Override
    public void saveMergeInfo(Map<String, Object> licenseData) {
        
    }
    

    /**
     * 描述
     * @author John Zhang
     * @created 2016年10月9日 下午3:59:26
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#findJyyq(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findJyyq(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_JYYQ J ");
        sql.append("WHERE J.JBXX_ID=? ORDER BY J.JYYQ_XH ASC");
        params.add(jbxxId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2016年10月9日 下午3:59:26
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#findAqzd(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findAqzd(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPSCXK_AQZD J ");
        sql.append("WHERE J.JBXX_ID=? ORDER BY J.AQZD_XH ASC");
        params.add(jbxxId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 描述 申请书数据回填
     * @author John Zhang
     * @created 2016年11月3日 下午3:36:23
     * @param vars
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#setWordValuesForSqs(java.util.Map)
     */
    @Override
    public Map<String, Object> setWordValuesForSqs(Map<String, Object> vars) {
        Map<String,Object> result =new HashMap<String,Object>();
        String RECORD_ID = (String) vars.get("RECORD_ID");
        if(StringUtils.isNotEmpty(RECORD_ID)){
            Map<String,Object> flowMap = this.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{RECORD_ID});
            Map<String,Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", 
                    new String[]{"DEF_ID"}, new Object[]{flowMap.get("DEF_ID")});
            String JBXX_ID = (String)flowMap.get("BUS_RECORDID");
            result = this.getByJdbc("T_FJFDA_SPSCXK_JBXX",
                    new String[]{"JBXX_ID"},new Object[]{JBXX_ID});
            //受理号
            result.put("EXE_ID", RECORD_ID);
            //申请类型判断
            String[] sqlx = result.get("SQLX")==null?new String[]{"1"}:
                result.get("SQLX").toString().split(",");
            result.put("SQLX_A", "□");
            result.put("SQLX_B", "□");
            result.put("SQLX_C", "□");
            result.put("SQLX_D", "□");
            result.put("SQLX_E", "□");
            if(sqlx.length > 1){
                result.put("SQLX_A", "☑");
                result.put("SQLX_B", "□");
            }else{
                if("1".equals(sqlx[0])){
                    result.put("SQLX_A", "☑");
                }else{
                    result.put("SQLX_B", "☑");
                }
            }
            String defKey = flowDef.get("DEF_KEY").toString();
            if(defKey.contains("011")){
                result.put("SQLX_C", "☑");
            }else if(defKey.contains("013")||defKey.contains("014")){
                result.put("SQLX_D", "☑");
            }else{
                result.put("SQLX_E", "☑");
            }
            //住所地址
            Map<String,Object> zs = getAdderssMap(JBXX_ID, 1);
            result.put("ZS", zs==null?"":zs.get("ADDRESS"));
            //生产地址getScdzs
            Map<String,Object> scdz = getAdderssMap(JBXX_ID, 2);
            result.put("SCDZ", scdz==null?"":scdz.get("ADDRESS"));
            //仓库地址
            Map<String,Object> wsckdz = getAdderssMap(JBXX_ID, 3);
            result.put("WSCKDZ", wsckdz==null?"":wsckdz.get("ADDRESS"));
            result.put("SQSJ_TIME",DateTimeUtil.formatDateStr(result.get("SQSJ_TIME").toString(), 
                    "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日"));
            List<Map<String,Object>> JSRYLIST = findJsry(JBXX_ID);
            for (Map<String, Object> map : JSRYLIST) {
                map.put("JSRY_XB", "1".equals((String)map.get("JSRY_XB"))?"男":"女");
            }
            result.put("JSRY", JSRYLIST);
            List<Map<String,Object>> cpxxList = getCpxxInfo(JBXX_ID,null);
            result.put("CPXXLIST", cpxxList);
            List<Map<String, Object>> jgcsList = this.findJgcs(JBXX_ID);
            Map<String, List> jgcs = new HashMap<String, List>(); 
            for (Map<String, Object> map : jgcsList) {
                String lbid = map.get("LBBH").toString();
                if (jgcs.containsKey(lbid)) {
                    jgcs.get(lbid).add(map);
                }else{
                    List j = new ArrayList<Map<String, Object>>();
                    j.add(map);
                    jgcs.put(lbid, j);
                }
            }
            result.put("jgcs", jgcs);
            List<Map<String, Object>> sbssList = this.findSbss(JBXX_ID);
            result.put("sbssList", sbssList);
            if(!"spsc01".equals(defKey)){
                Map<String, List> sbss = new HashMap<String, List>(); 
                for (Map<String, Object> map : sbssList) {
                    String lbid = map.get("LBID").toString();
                    if (sbss.containsKey(lbid)) {
                        sbss.get(lbid).add(map);
                    }else{
                        List j = new ArrayList<Map<String, Object>>();
                        j.add(map);
                        sbss.put(lbid, j);
                    }
                    for (Map<String, Object> cp : cpxxList) {
                        if(lbid.equals((String)cp.get("CPXX_LBBH"))){
                            map.put("CPMC", cp.get("CPXX_LBMC"));
                            break;
                        }
                    }
                }
                result.put("sbss", sbss);
            }
            List<Map<String, Object>> jyyqList = this.findJyyq(JBXX_ID);
            Map<String, List> jyyq = new HashMap<String, List>(); 
            for (Map<String, Object> map : jyyqList) {
                String lbid = map.get("JBXX_ID").toString();
                if (jyyq.containsKey(lbid)) {
                    jyyq.get(lbid).add(map);
                }else{
                    List j = new ArrayList<Map<String, Object>>();
                    j.add(map);
                    jyyq.put(lbid, j);
                }
            }
            result.put("jyyq", jyyq);
            result.put("aqzd", this.findAqzd(JBXX_ID));
        }
        return result;
    }

    /**
     * 描述  回填许可文书数据
     * @author John Zhang
     * @created 2016年11月9日 下午3:50:31
     * @param docDatas
     * @param flowVars
     * @see net.evecom.platform.business.service.FoodProductionService#setWordTemplateData(java.util.Map, java.util.Map)
     */
    @Override
    public void setWordTemplateData(Map<String, Object> docDatas, Map<String, Object> flowVars) {
        Map<String, Object> eflowBusRecord = (Map<String, Object>) flowVars.get("EFLOW_BUSRECORD");
        docDatas.put("SQRMC", eflowBusRecord.get("SQRMC"));
        List<Map<String,Object>> cpxxList = this.getCpxxInfo(eflowBusRecord.get("JBXX_ID").toString(),null);
        StringBuffer splb = new StringBuffer("");
        for (int i=0;i<cpxxList.size();i++) {
            if(i>0){
                splb.append("、");
            }
            splb.append(cpxxList.get(i).get("CPXX_LBMC"));
        }
        docDatas.put("SPLB",splb);
        docDatas.put("SCDZ",this.getAdderss(eflowBusRecord.get("JBXX_ID").toString(), 2));
        docDatas.put("SPSC_LXR", eflowBusRecord.get("LXR"));
        docDatas.put("XKZBH", eflowBusRecord.get("XKZBH"));
        docDatas.put("LXRYDDD", eflowBusRecord.get("LXRYDDD"));
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2017年1月6日 上午9:31:20
     * @param vars
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#setWordValuesForSqsBZ(java.util.Map)
     */
    @Override
    public Map<String, Object> setWordValuesForSqsBZ(Map<String, Object> vars) {
        Map<String,Object> result =new HashMap<String,Object>();
        String RECORD_ID = (String) vars.get("RECORD_ID");
        if(StringUtils.isNotEmpty(RECORD_ID)){
            Map<String,Object> flowMap = this.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{RECORD_ID});
            /*Map<String,Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", 
                    new String[]{"DEF_ID"}, new Object[]{flowMap.get("DEF_ID")});*/
            String JBXX_ID = (String)flowMap.get("BUS_RECORDID");
            result = this.getByJdbc("T_FJFDA_SPSCXK_JBXX",
                    new String[]{"JBXX_ID"},new Object[]{JBXX_ID});
            
            Map<String,Object> xkzxx = this.getByJdbc("T_FJFDA_SPSCXKXX", 
                    new String[]{"XKZBH","XKZZT"},new Object[]{result.get("XKZBH_OLD"),"10"});
            if(xkzxx != null){
                result.put("FZRQ", xkzxx.get("FZRQ"));
            }
          //申请类型判断
            String[] sqlx = result.get("SQLX")==null?new String[]{"1"}:
                result.get("SQLX").toString().split(",");
            String sqlxStr = "";
            if(sqlx.length>1){
                sqlxStr = "食品、食品添加剂";
            }else if("1".equals(sqlx[0])){
                sqlxStr = "食品";
            }else if("2".equals(sqlx[0])){
                sqlxStr = "食品添加剂";
            }
            result.put("SQLX",sqlxStr);
          //住所地址
            Map<String,Object> zs = getAdderssMap(JBXX_ID, 1);
            result.put("ZS", zs==null?"":zs.get("ADDRESS"));
            //生产地址getScdzs
            Map<String,Object> scdz = getAdderssMap(JBXX_ID, 2);
            result.put("SCDZ", scdz==null?"":scdz.get("ADDRESS"));
            //仓库地址
            Map<String,Object> wsckdz = getAdderssMap(JBXX_ID, 3);
            result.put("WSCKDZ", wsckdz==null?"":wsckdz.get("ADDRESS"));
            //仓库地址
            //受理号
            result.put("EXE_ID", RECORD_ID);
        }
        return result;
    }

    /**
     * 描述
     * @author John Zhang
     * @created 2017年1月6日 上午9:33:11
     * @param vars
     * @return
     * @see net.evecom.platform.business.service.FoodProductionService#setWordValuesForSqsBJ(java.util.Map)
     */
    @Override
    public Map<String, Object> setWordValuesForSqsBJ(Map<String, Object> vars) {
        Map<String,Object> result =new HashMap<String,Object>();
        String RECORD_ID = (String) vars.get("RECORD_ID");
        if(StringUtils.isNotEmpty(RECORD_ID)){
            Map<String,Object> flowMap = this.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{RECORD_ID});
            Map<String,Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", 
                    new String[]{"DEF_ID"}, new Object[]{flowMap.get("DEF_ID")});
            String JBXX_ID = (String)flowMap.get("BUS_RECORDID");
            result = this.getByJdbc("T_FJFDA_SPSCXK_JBXX",
                    new String[]{"JBXX_ID"},new Object[]{JBXX_ID});
            //受理号
            result.put("EXE_ID", RECORD_ID);
            result.put("BJSP", "☑");
            result.put("SC", "□");
            result.put("BG", "□");
            result.put("YX", "□");
            result.put("BB", "□");
            result.put("ZX", "□");
            if("BJSPSCXKHF".contains(flowDef.get("DEF_KEY").toString())){
                result.put("SC", "☑");
            }
            if ("BJSPSCXKBG".contains(flowDef.get("DEF_KEY").toString())) {
                result.put("BG", "☑");
            }
            if ("BJSPSCXKHZ".contains(flowDef.get("DEF_KEY").toString())) {
                result.put("YX", "☑");
            }
          //住所地址
            Map<String,Object> zs = getAdderssMap(JBXX_ID, 1);
            result.put("ZS", zs==null?"":zs.get("ADDRESS"));
            //生产地址getScdzs
            Map<String,Object> scdz = getAdderssMap(JBXX_ID, 2);
            result.put("SCDZ", scdz==null?"":scdz.get("ADDRESS"));
            //仓库地址
            Map<String,Object> wsckdz = getAdderssMap(JBXX_ID, 3);
            result.put("WSCKDZ", wsckdz==null?"":wsckdz.get("ADDRESS"));
            
            result.put("SQSJ_TIME",DateTimeUtil.formatDateStr(result.get("SQSJ_TIME").toString(), 
                    "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日"));
            List<Map<String,Object>> JSRYLIST = findJsry(JBXX_ID);
            for (Map<String, Object> map : JSRYLIST) {
                map.put("JSRY_XB", "1".equals((String)map.get("1"))?"男":"女");
            }
            result.put("jsryList", JSRYLIST);
            result.put("cpxxList", this.findCpxx(JBXX_ID, "", ""));
            List<Map<String, Object>> jgcsList = this.findJgcs(JBXX_ID);
            result.put("jgcsList", jgcsList);
            List<Map<String, Object>> sbssList = this.findSbss(JBXX_ID);
            result.put("sbssList", sbssList);
            List<Map<String, Object>> jyyqList = this.findJyyq(JBXX_ID);
            result.put("jyyqList", jyyqList);
            result.put("aqzdList", this.findAqzd(JBXX_ID));
        }
        return result;
    }

    /**
     * 
     * 描述
     * @author John Zhang
     * @created 2017年2月26日 下午3:16:43
     * @param zzxxId
     * @param isHealth
     * @return
     */
    @Override
    public List<Map<String, Object>> getZzCp(String zzxxId, String isHealth) {
        StringBuffer sql = new StringBuffer("select * FROM T_FJFDA_SPSCXKXX_CP where ZZXX_ID=? ");
        List<Object> params = new ArrayList<Object>();
        params.add(zzxxId);
        if("2".equals(isHealth)){
            sql.append(" and CPXX_ISPROVINCE = '2'");
        }else if("1".equals(isHealth)){
            sql.append(" and CPXX_ISPROVINCE != '2'");
        }
        sql.append(" ORDER BY LBBH,CPXH ASC");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 
     * 描述 地址信息处理
     * @author Usher Song
     * @created 2017年2月28日 下午6:19:45
     * @param productionInfo
     */
    @Override
    public void handleAdress(Map<String, Object> productionInfo) {
        List<Map<String,Object>> scdzList = (List<Map<String, Object>>) productionInfo.get("SCDZ"); 
        StringBuilder scdzStr = new StringBuilder();
        StringBuilder scdzId = new StringBuilder();
        for (Map<String, Object> scdz : scdzList) {
            scdzId.append(scdz.get("DZXX_ID")+"，");
            scdzStr.append(scdz.get("ADDRESS")+"，");
        }
        if(scdzStr.length()>0){
            productionInfo.put("SCDZ", scdzStr.substring(0, scdzStr.lastIndexOf("，")));
            productionInfo.put("SCDZID", scdzId.substring(0, scdzId.lastIndexOf("，")));
        }else{
            productionInfo.put("SCDZ", "");
            productionInfo.put("SCDZID", "");
        }
        List<Map<String,Object>> wsckdzList = new ArrayList<Map<String,Object>>();  
        if(productionInfo.get("WSCKDZ")!=null){
            wsckdzList = (List<Map<String, Object>>) productionInfo.get("WSCKDZ");
        }
        StringBuilder wsckdzStr = new StringBuilder();
        StringBuilder wsckdzId = new StringBuilder();
        for (Map<String, Object> wsckdz : wsckdzList) {
            wsckdzId.append(wsckdz.get("DZXX_ID")+"，");
            wsckdzStr.append(wsckdz.get("ADDRESS")+"，");
        }
        if(wsckdzStr.length()>0){
            productionInfo.put("WSCKDZ", wsckdzStr.substring(0, wsckdzStr.lastIndexOf("，")));
            productionInfo.put("WSCKDZID", wsckdzId.substring(0, wsckdzId.lastIndexOf("，")));
        }else{
            productionInfo.put("WSCKDZ", "");
            productionInfo.put("WSCKDZID", "");
        }
    }

    /**
     * 
     * 描述  许可证编号处理
     * @author Usher Song
     * @created 2017年2月28日 下午6:31:19
     * @param productionInfo
     */
    @Override
    public void handleXkzbh(Map<String, Object> productionInfo) {
        String curJbxxId = "";
        if(productionInfo.get("JBXX_ID") != null){
            curJbxxId = productionInfo.get("JBXX_ID").toString();
        }
        String xkzbhOld = (String) productionInfo.get("XKZBH_OLD");
        if(StringUtils.isNotEmpty(xkzbhOld)&&xkzbhOld.contains("SC")){
            productionInfo.put("XKZBH", productionInfo.get("XKZBH_OLD"));
        }else if(productionInfo.get("XKZBH")==null){
            String sqlx = "1";
            if (productionInfo.get("SQLX") != null) {
                sqlx = productionInfo.get("SQLX").toString();
            }
            // 如果申请类型同时为食品和食品添加剂的时候 默认第一个
            if (sqlx.indexOf(",") != 0) {
                sqlx = sqlx.split(",")[0];
            }
            // 根据社会信用代码查找该企业是否存在已生成的许可证号,若存在直接使用且保存证号到食品生产业务表,若不存在则生成新的编号
            Map<String, Object> existProductLic = this.findByShxydm(productionInfo.get("SHXYDMSFZHM").toString(),
                    curJbxxId);
            String xkzbh = "";
            if (existProductLic != null && existProductLic.get("XKZBH") != null) {
                xkzbh = existProductLic.get("XKZBH").toString();
                Map<String, Object> xkzbhMap = new HashMap<String, Object>();
                xkzbhMap.put("XKZBH", xkzbh);
                dao.saveOrUpdate(xkzbhMap, "T_FJFDA_SPSCXK_JBXX", curJbxxId);
            } else {
                this.putXzqhbm(curJbxxId,productionInfo);
                String xzqhbm = productionInfo.get("XZQHBM").toString();
                xkzbh = this.generateAndSaveLicenseCode(curJbxxId, sqlx, productionInfo.get("SPBH").toString(), xzqhbm);
            }
            productionInfo.put("XKZBH", xkzbh);
        }
    }

    @Override
    public Map<String, Object> afterSpbj(Map<String, Object> flowVars) {
        //String isPass = flowVars.get("EFLOW_ISPASS").toString();
        String eflowIsBack = (String)flowVars.get("EFLOW_ISBACK");
        //不等于退回时
        if(flowVars.get("EFLOW_ISBACK")==null||eflowIsBack.equals("false")){
             String xkzbhOld = (String)flowVars.get("XKZBH_OLD");
             String itemCode = (String)flowVars.get("ITEM_CODE");
             String reason = "";
             if(xkzbhOld!=null){
                 if("001XK00201".equals(itemCode)){
                     reason = "首次";
                 }
                 if("001XK00202".equals(itemCode)){
                     reason = "简单变更";
                 }
                 if("001XK00203".equals(itemCode)){
                     reason = "一般变更";
                 }
                 if("001XK00204".equals(itemCode)){
                     reason = "延续";
                 }
                 if("001XK00205".equals(itemCode)){
                     reason = "补办";
                 }
                 if("001XK00206".equals(itemCode)){
                     reason= "注销";
                 }
                 if("001XK00207".equals(itemCode)){
                     reason = "保健食品首次";
                 }
                 if("001XK00208".equals(itemCode)){
                     reason = "保健食品变更";
                 }
                 if("001XK00209".equals(itemCode)){
                     reason = "保健食品换证";
                 }
                 dao.executeSql("update T_FJFDA_SPSCXKXX set xkzzt='70',"
                         + "state_change_reason='"+reason+"' where xkzbh = ? and xkzzt='10'",
                         new Object[]{xkzbhOld});
             }
        }
        return flowVars;
    }

    /**
     * 描述 afterPrintZz
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:41:13
     * @param map
     * @return
     */
    @Override
    public String afterPrintZz(Map<String, Object> map) {
        return null;
    }

    /**
     * 
     * 描述 findHandleDatas
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:41:26
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findHandleDatas(SqlFilter sqlFilter) {
        return null;
    }

    /**
     * 描述 zxHandle
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:41:33
     * @param flowVars
     * @return
     */
    @Override
    public Map<String, Object> zxHandle(Map<String, Object> flowVars) {
        return null;
    }

    /**
     * 描述 getProductionInfo
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:41:43
     * @param flowVars
     * @param deptId
     * @return
     */
    @Override
    public Map<String, Object> getProductionInfo(Map<String, Object> flowVars, String deptId) {
        return null;
    }

    /**
     * 描述 generateSCLicenseCode
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:41:53
     * @param sqlx
     * @param typeCode
     * @param orderCode
     * @param xzqhbm
     * @return
     */
    @Override
    public String generateSCLicenseCode(String sqlx, String typeCode, String orderCode, String xzqhbm) {
        return null;
    }

    /**
     * 描述 generateAndSaveLicenseCode
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:40:59
     * @param jbxxId
     * @param sqlx
     * @param spbh
     * @param xzqhbm
     * @return
     */
    @Override
    public String generateAndSaveLicenseCode(String jbxxId, String sqlx, String spbh, String xzqhbm) {
        return null;
    }

    /**
     * 描述afterCreateTransData
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:40:49
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> afterCreateTransData(Map<String, Object> flowDatas) {
        String bus_recordid = (String)flowDatas.get("EFLOW_BUSRECORDID");
        String eflow_eveId = (String)flowDatas.get("EFLOW_EXEID");
        //退回流程，不执行
        String isback = (String)flowDatas.get("EFLOW_ISBACK");
        String eflow_istempsave  = (String)flowDatas.get("EFLOW_ISTEMPSAVE");
        if(!("true".equals(isback)) && !("1".equals(eflow_istempsave))){
            Map<String,Object> data = new HashMap<String,Object>();
            buildTransData(data,flowDatas);
            //构造材料信息
            Map<String,Map<String,Object>> attachs = new HashMap<String, Map<String,Object>>();
            String submitmaterfilejson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
            if(StringUtils.isNotEmpty(submitmaterfilejson)){
                List<Map> maters = JSON.parseArray(submitmaterfilejson, Map.class);
                if(maters != null && maters.size() > 0){
                    //删除待传材料列表业务相关的数据
                    dao.remove("T_FJFDA_TRANS_MATERS", new String[] { "BUS_RECORDID" }, new Object[] { bus_recordid });
                    for(Map map : maters){
                        String attach_key = String.valueOf(map.get("ATTACH_KEY"));
                        String file_id = String.valueOf(map.get("FILE_ID"));
                        Map<String,Object> fileInfo = fileAttachService.getFileAttachObject(file_id);
                        Map<String,Object> fileMap = new HashMap<String, Object>();
                        fileMap.put("fileRealName", fileInfo.get("FILE_NAME"));
                        String filePath  = (String)fileInfo.get("FILE_PATH");
                        if(filePath != null){
                            fileMap.put("fileName", filePath.substring(filePath.lastIndexOf("/")+1));
                        }else{
                            fileMap.put("fileName", "error");
                        }
                        fileMap.put("fileSize", String.valueOf(fileInfo.get("FILE_LENGTH"))); 
                        attachs.put(attach_key,fileMap);
                        Map<String,Object> transMaters = new HashMap<String, Object>();
                        transMaters.putAll(fileMap);
                        transMaters.put("FILEID", file_id);
                        transMaters.put("BUS_RECORDID", bus_recordid);
                        transMaters.put("FILEPATH", filePath);
                        transMaters.put("MATER_CODE", attach_key);
                        dao.saveOrUpdate(transMaters, "T_FJFDA_TRANS_MATERS", null);
                    }
                }
            }
            data.put("attachs", attachs);
            //把构造的报文结果存储在数据库中
            dao.remove("T_FJFDA_TRANS_DATA", new String[] { "BUS_RECORDID" }, new Object[] { bus_recordid });
            Map<String,Object> transData = new HashMap<String, Object>();
            transData.put("ITEM_CODE", flowDatas.get("FJFDA_ITEM_CODE"));
            transData.put("BUS_RECORDID", bus_recordid);
            transData.put("DATA_JSON", JSON.toJSONString(data));
            dao.saveOrUpdate(transData, "T_FJFDA_TRANS_DATA", null);
            //把当前的办件数据入库
            dao.remove("T_FJFDA_TRANS", new String[] { "BUS_RECORDID" }, new Object[] { bus_recordid });
            Map<String,Object> trans = new HashMap<String, Object>();
            trans.put("BUS_RECORDID", bus_recordid);
            trans.put("BUS_EVEID", eflow_eveId);
            trans.put("ITEM_CODE", flowDatas.get("FJFDA_ITEM_CODE"));
            trans.put("BUS_ITEMCODE", flowDatas.get("ITEM_CODE"));
            trans.put("BUS_ITEMNAME", flowDatas.get("ITEM_NAME"));
            dao.saveOrUpdate(trans, "T_FJFDA_TRANS", null);
        }
        return flowDatas;
    }

    /**
     * 描述 构造具体的报文
     * @author Keravon Feng
     * @created 2019年2月26日 上午9:42:43
     * @param data
     * @param flowDatas
     */
    private void buildTransData(Map<String, Object> data, Map<String, Object> flowDatas) {
        data.put("SQLX", flowDatas.get("SQLX"));
        data.put("SQRMC", flowDatas.get("SQRMC"));
        data.put("YYZZZCH", flowDatas.get("YYZZZCH"));
        data.put("SHXYDMSFZHM", flowDatas.get("SHXYDMSFZHM"));
        data.put("FDDBRXM", flowDatas.get("FDDBRXM"));
        data.put("LXR", flowDatas.get("LXR"));
        data.put("LXRGDDD", flowDatas.get("LXRGDDD"));
        data.put("LXRYDDD", flowDatas.get("LXRYDDD"));
        data.put("CZ", flowDatas.get("CZ"));
        data.put("ZSQS", flowDatas.get("ZSQS"));
        data.put("ZSXS", flowDatas.get("ZSXS"));
        data.put("ZSXZ", flowDatas.get("ZSXZ"));
        data.put("ZSXXDZ", flowDatas.get("ZSXXDZ"));
        String scdzJson = (String)flowDatas.get("scdzJson");
        if(scdzJson != null && StringUtils.isNotEmpty(scdzJson)){
            List<Map> scdzs = JSON.parseArray(scdzJson, Map.class);
            data.put("scdzJson", scdzs);
        }
        data.put("CCCSQS", flowDatas.get("CCCSQS"));
        data.put("CCCSXS", flowDatas.get("CCCSXS"));
        data.put("CCCSXZ", flowDatas.get("CCCSXZ"));
        data.put("CCXXDZ", flowDatas.get("CCXXDZ"));
        data.put("BZ", flowDatas.get("BZ"));
        String technicalPersonnelJson = (String)flowDatas.get("TechnicalPersonnelJson");
        if(technicalPersonnelJson != null && StringUtils.isNotEmpty(technicalPersonnelJson)){
            List<Map> technicalPersonnels = JSON.parseArray(technicalPersonnelJson, Map.class);
            data.put("TechnicalPersonnelJson", technicalPersonnels);
        }
        String productCpxxJson = (String)flowDatas.get("ProductCpxxJson");
        if(productCpxxJson != null && StringUtils.isNotEmpty(productCpxxJson)){
            List<Map> productCpxxs = JSON.parseArray(productCpxxJson, Map.class);
            data.put("ProductCpxxJson", productCpxxs); 
        }
        String jGCSXXJson = (String)flowDatas.get("JGCSXXJson");
        if(jGCSXXJson != null && StringUtils.isNotEmpty(jGCSXXJson)){
            List<Map> jGCSXXs = JSON.parseArray(jGCSXXJson, Map.class);
            data.put("JGCSXXJson", jGCSXXs);
        }
        String sbssJson = (String)flowDatas.get("sbssJson");
        if(sbssJson != null && StringUtils.isNotEmpty(sbssJson)){
            List<Map> sbss = JSON.parseArray(sbssJson, Map.class);
            data.put("sbssJson", sbss);
        }
        String jyyqJson = (String)flowDatas.get("jyyqJson");
        if(jyyqJson != null && StringUtils.isNotEmpty(jyyqJson)){
            List<Map> jyyqs = JSON.parseArray(jyyqJson, Map.class);
            data.put("jyyqJson", jyyqs);
        }
        data.put("JYYQ_JGMC", flowDatas.get("JYYQ_JGMC"));
        data.put("JYYQ_WJMC", flowDatas.get("JYYQ_WJMC"));
        data.put("JYYQ_WJWH", flowDatas.get("JYYQ_WJWH"));
        data.put("JYYQ_HTWJ", flowDatas.get("JYYQ_HTWJ"));
        String aqzdJson = (String)flowDatas.get("aqzdJson");
        if(aqzdJson != null && StringUtils.isNotEmpty(aqzdJson)){
            List<Map> aqzds = JSON.parseArray(aqzdJson, Map.class);
            data.put("aqzdJson", aqzds);
        }
    }
}
