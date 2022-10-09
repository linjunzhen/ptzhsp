/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcRegisterInterfaceDao;
import net.evecom.platform.bdc.service.BdcRegisterInterfaceService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.sf.json.JSONArray;

/**
 * 
 * 描述 不动产登记相关接口Service
 * 
 * @author Scolder Lin
 * @created 2021年6月30日 下午11:35:22
 */
@SuppressWarnings("rawtypes")
@Service("bdcRegisterInterfaceService")
public class BdcRegisterInterfaceServiceImpl extends BaseServiceImpl implements BdcRegisterInterfaceService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcRegisterInterfaceServiceImpl.class);
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入dao
     */
    @Resource
    private BdcRegisterInterfaceDao dao;
    
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;

    /**
     * 引入dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getSourceDataByExeId(String exeId, String BUS_TABLENAME) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT t.*, e.* ");
        sql.append(" FROM "+BUS_TABLENAME+" t ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION e ");
        sql.append(" ON T.YW_ID = E.BUS_RECORDID ");
        sql.append(" WHERE E.EXE_ID = ? ");
        params.add(exeId);
        List<Map<String, Object>> resultList = dao.findBySql(sql.toString(), params.toArray(), null);
        if(resultList!=null && resultList.size()>0) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findExtranetApplyDataList() {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT t.* ");
        sql.append(" FROM T_INTERFACE_EXTRANETAPPLY t ");
        sql.append(" WHERE t.SEND_STATUS = ? ");
        params.add("0");
        sql.append(" ORDER BY t.TJSJ ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findExtranetConcludeDataList() {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT t.* ");
        sql.append(" FROM T_INTERFACE_EXTRANETCONCLUDE t ");
        sql.append(" WHERE t.SEND_STATUS = ? ");
        params.add("0");
        sql.append(" ORDER BY t.BJSJ ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void appayDataStorage(Map<String, Object> variables) {
        try {
            String ITEM_CODE = (String) variables.get("ITEM_CODE");
            String  EFLOW_BUSTABLENAME  = StringUtil.getString(variables.get("EFLOW_BUSTABLENAME"));
            /**
             * 569262478QR00310                 国有建设用地使用权及房屋所有权转移登记-全流程
             * 345071904QR01404                 国有建设用地使用权及房屋所有权转移登记—水电气联办
             * EFLOW_BUSTABLENAME以T_BDCQLC_GYJSJFWZYDJ开头   国有建设用地使用权及房屋所有权转移登记-全流程拆分后的6个事项
             * 569262478GF03217                 不动产全流程国有建设用地使用权首次登记(暂时不推送信息)
             * 345071904QR01303                 抵押权首次登记(本地事项编码：569262478GF03102)
             * 11350128345071904JQR000039L      抵押权首次登记（最高额抵押权首次登记）
             * 345071904QR01306                 抵押权首次登记-全流程测试(本地事项编码：569262478GF03216)
             * 345071904QR01302                 抵押权注销登记(本地事项编码：569262478GF03103)
             * 201609140001GF00113              抵押权注销登记-全流程测试
             * 569262478GF02409                 不动产全流程预购商品房抵押预告登记-测试
             * 569262478GF02410                 不动产全流程预购商品房预告登记
             */
            log.info("item_code:"+ITEM_CODE);
            log.info("EFLOW_BUSTABLENAME:"+EFLOW_BUSTABLENAME);
            if("569262478QR00310".equals(ITEM_CODE) || "569262478GF02410".equals(ITEM_CODE) 
                    /*|| "569262478GF03217".equals(ITEM_CODE)*/ || "345071904QR01303".equals(ITEM_CODE)
                    || "345071904QR01306".equals(ITEM_CODE) || "345071904QR01302".equals(ITEM_CODE)
                    || "201609140001GF00113".equals(ITEM_CODE) || "569262478GF02409".equals(ITEM_CODE)
                    || "11350128345071904JQR000039L".equals(ITEM_CODE) || "345071904QR01404".equals(ITEM_CODE)
                    || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0) {
                Map<String, Object> pushData = new HashMap<String,Object>();
                String EXE_ID = (String)variables.get("EFLOW_EXEID");
                //判断该申报号是否存在于推送表
                Map<String, Object> recordInfo = this.getByJdbc("T_INTERFACE_EXTRANETAPPLY", 
                        new String[] { "SQBH" }, new Object[] { EXE_ID });
                log.info("EXE_ID:"+EXE_ID);
                if(recordInfo==null || recordInfo.size()==0) {
                    String CATALOG_NAME = "";
                    if(variables.get("CATALOG_NAME")!=null) {
                        CATALOG_NAME = (String)variables.get("CATALOG_NAME");
                        CATALOG_NAME = transformCatalogName(CATALOG_NAME);
                    }
                    //外网业务归类：抵押类/预告类/一手房转移类/二手房转移类/其他类。联办时，请优先填写一手转移类/二手房转移类/抵押类。
                    String BZ_DJLXMC = "";
                    String ITEM_NAME = "";
                    String SFLB = "";
                    String RECTYPE = "";
                    
                    if("569262478QR00310".equals(ITEM_CODE) || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0
                            || "345071904QR01404".equals(ITEM_CODE)) {
                        String ZYDJ_TYPE = "";
                        if(variables.get("ZYDJ_TYPE")!=null) {
                            ZYDJ_TYPE = (String)variables.get("ZYDJ_TYPE");
                            if("1".equals(ZYDJ_TYPE)) {
                                BZ_DJLXMC = "一手房转移类";
                                RECTYPE = "11350128345071904JGF000017";
                                SFLB = "0";
                            }else if("2".equals(ZYDJ_TYPE)) {
                                BZ_DJLXMC = "二手房转移类";
                                RECTYPE = "11350128345071904JQR000084";
                                SFLB = "0";
                            }else if("3".equals(ZYDJ_TYPE)){
                                BZ_DJLXMC = "其他类";
                                RECTYPE = "11350128345071904JQR000056";
                                SFLB = "0";
                            }else if("4".equals(ZYDJ_TYPE)){
                                BZ_DJLXMC = "二手房转移类";
                                RECTYPE = "11350128345071904JQR000054";
                                SFLB = "1";
                            }else if("5".equals(ZYDJ_TYPE)){
                                BZ_DJLXMC = "一手房转移类";
                                RECTYPE = "11350128345071904JQR000053";
                                SFLB = "1";
                            }else{
                                BZ_DJLXMC = "其他类";
                                RECTYPE = "11350128345071904JQR000066";
                                SFLB = "0";
                            }
                        }
                        if(variables.get("ITEM_NAME")!=null) {
                            ITEM_NAME = (String)variables.get("ITEM_NAME");
                        }
                    }else {
                        Map<String, Object> transformInfo = transformInfoByItemCode(ITEM_CODE);
                        ITEM_NAME = (String)transformInfo.get("ITEM_NAME");
                        BZ_DJLXMC = (String)transformInfo.get("BZ_DJLXMC");
                        RECTYPE = (String)transformInfo.get("RECTYPE");
                        SFLB = "0";
                    }
                    
                    List<Map<String, Object>> sqrList = new ArrayList<Map<String, Object>>();
                    List<Map<String, Object>> dyList = new ArrayList<Map<String, Object>>();
                    Map<String, Object> sourceData = new HashMap<String, Object>();
                    Map<String, Object> dyMap = new HashMap<String, Object>();
                    if("569262478QR00310".equals(ITEM_CODE) || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0
                            || "345071904QR01404".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_GYJSJFWZYDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", variables.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("569262478GF02410".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_YGSPFYGDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("BDCDYH")!=null) {
                            dyMap.put("bdcdyh", variables.get("BDCDYH"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("BDCDYH"));
                        }
                        dyMap.put("bdczl", sourceData.get("ZL"));
                    }/*else if("569262478GF03217".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_GYJSSCDJ");
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", variables.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }*/else if("345071904QR01303".equals(ITEM_CODE) || "11350128345071904JQR000039L".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDC_DYQSCDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", variables.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("345071904QR01306".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_DYQSCDJZB");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", variables.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("345071904QR01302".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDC_DYQZXDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("BDCDYH")!=null) {
                            dyMap.put("bdcdyh", variables.get("BDCDYH"));
                        }else {
                            String ZXMX_JSON = (String)sourceData.get("ZXMX_JSON");
                            List<Map> jsonList = JSON.parseArray(ZXMX_JSON,Map.class);
                            if(jsonList!=null && jsonList.size()>0) {
                                Map<String, Object> zxmxMap = jsonList.get(0);
                                dyMap.put("bdcdyh", zxmxMap.get("BDCDYH"));
                            }else {
                                dyMap.put("bdcdyh", "");
                            }
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("201609140001GF00113".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_DYQZXDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("BDCDYH")!=null) {
                            dyMap.put("bdcdyh", variables.get("BDCDYH"));
                        }else {
                            String ZXMX_JSON = (String)sourceData.get("ZXMX_JSON");
                            List<Map> jsonList = JSON.parseArray(ZXMX_JSON,Map.class);
                            if(jsonList!=null && jsonList.size()>0) {
                                Map<String, Object> zxmxMap = jsonList.get(0);
                                dyMap.put("bdcdyh", zxmxMap.get("BDCDYH"));
                            }else {
                                dyMap.put("bdcdyh", "");
                            }
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("569262478GF02409".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_YGSPFDYQYGDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(variables.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", variables.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }
                    dyList.add(dyMap);

                    //申请人信息
                    if(sourceData!=null && sourceData.size()>0) {
                        Map<String, Object> sqrMap = new HashMap<String, Object>();
                        sqrMap.put("sqbh", EXE_ID);
                        sqrMap.put("sqr", sourceData.get("SQRMC"));
                        String ZJLXBM = "";
                        if(sourceData.get("SQRZJLX")!=null) {
                            String SQRZJLX = (String)sourceData.get("SQRZJLX");
                            ZJLXBM = transformCardType(SQRZJLX);
                        }
                        sqrMap.put("ZJLXBM", ZJLXBM);
                        Sm4Utils sm4 = new Sm4Utils();
                        String SQRSFZ = "";
                        if(sourceData.get("SQRSFZ") != null) {
                            SQRSFZ = (String) sourceData.get("SQRSFZ");
                            SQRSFZ = sm4.decryptDataCBC(SQRSFZ);
                        }
                        
                        String SQRSJH = "";
                        if(sourceData.get("SQRSJH") != null) {
                            SQRSJH = (String) sourceData.get("SQRSJH");
                            SQRSJH = sm4.decryptDataCBC(SQRSJH);
                        }
                        sqrMap.put("ZJHM", SQRSFZ);
                        sqrMap.put("LXDH", SQRSJH);
                        sqrList.add(sqrMap);
                    }
                    pushData.put("QYDM", "351001");
                    pushData.put("SZS", "351000");
                    pushData.put("SZQ", "351001");
                    pushData.put("SQBH", EXE_ID);
                    pushData.put("DJLX", CATALOG_NAME);
                    pushData.put("BZ_DJLXMC", BZ_DJLXMC);
                    pushData.put("QLLX", ITEM_NAME);
                    pushData.put("SFLB", SFLB);
                    pushData.put("RECTYPE", RECTYPE);
                    pushData.put("SQRLIST", JSONArray.fromObject(sqrList).toString());
                    pushData.put("DYLIST", JSONArray.fromObject(dyList).toString());
                    pushData.put("SEND_STATUS", "0");
                    log.info("saveData:success");
                    this.saveOrUpdate(pushData, "T_INTERFACE_EXTRANETAPPLY", null);
                   
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public void concludeDataStorage(Map<String, Object> variables) {
        String ITEM_CODE = (String) variables.get("ITEM_CODE");
        String EXE_ID = (String)variables.get("EFLOW_EXEID");
        String  EFLOW_BUSTABLENAME  = StringUtil.getString(variables.get("EFLOW_BUSTABLENAME"));
        //判断该申报号是否存在于推送表
        Map<String, Object> recordInfo = this.getByJdbc("T_INTERFACE_EXTRANETCONCLUDE", 
                new String[] { "SQBH" }, new Object[] { EXE_ID });
        if(recordInfo==null || recordInfo.size()==0) {
            Map<String, Object> pushData = new HashMap<String,Object>();
            String BDC_SZZH = "";
            if("569262478QR00310".equals(ITEM_CODE) || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0
                    || "345071904QR01404".equals(ITEM_CODE)) {
                String tableName = "T_BDCQLC_GYJSJFWZYDJ";
                /*if("569262478GF03217".equals(ITEM_CODE)) {
                    tableName = "T_BDCQLC_GYJSSCDJ";
                }*/
                Map<String, Object> sourceData = this.getSourceDataByExeId(EXE_ID, tableName);
                if(sourceData!=null && sourceData.size()>0) {
                    if(sourceData.get("POWERPEOPLEINFO_JSON")!=null) {
                        String powerInfoJson = (String)sourceData.get("POWERPEOPLEINFO_JSON");
                        List<Map> powerInfoList = JSON.parseArray(powerInfoJson,Map.class);
                        if(powerInfoList!=null && powerInfoList.size()>0) {
                            Map<String, Object> powerInfoMap = powerInfoList.get(0);
                            if(powerInfoMap.get("BDC_SZZH")!= null) {
                                BDC_SZZH = (String)powerInfoMap.get("BDC_SZZH");
                                BDC_SZZH = BDC_SZZH+"等"+powerInfoList.size()+"本";
                            }
                        }
                    }
                }
            }else if("345071904QR01303".equals(ITEM_CODE) || 
                     "345071904QR01306".equals(ITEM_CODE) || "11350128345071904JQR000039L".equals(ITEM_CODE)) {
                String tableName = "T_BDC_DYQSCDJ";
                if("345071904QR01306".equals(ITEM_CODE)) {
                    tableName = "T_BDCQLC_DYQSCDJZB";
                }
                Map<String, Object> sourceData = this.getSourceDataByExeId(EXE_ID, tableName);
                if(sourceData!=null && sourceData.size()>0) {
                    if(sourceData.get("DYMX_JSON")!=null) {
                        String dymxJson = (String)sourceData.get("DYMX_JSON");
                        List<Map> dymxInfoList = JSON.parseArray(dymxJson,Map.class);
                        if(dymxInfoList!=null && dymxInfoList.size()>0) {
                            Map<String, Object> dymxInfoMap = dymxInfoList.get(0);
                            if(dymxInfoMap.get("BDCQZH")!= null) {
                                BDC_SZZH = (String)dymxInfoMap.get("BDCQZH");
                                BDC_SZZH = BDC_SZZH+"等"+dymxInfoList.size()+"本";
                            }
                        }
                    }
                }
            }else if("569262478GF02409".equals(ITEM_CODE)) {
                Map<String, Object> sourceData = this.getSourceDataByExeId(EXE_ID, "T_BDCQLC_YGSPFDYQYGDJ");
                if(sourceData!=null && sourceData.size()>0) {
                    if(sourceData.get("YGXX_JSON")!=null) {
                        String ygxxJson = (String)sourceData.get("YGXX_JSON");
                        List<Map> ygxxInfoList = JSON.parseArray(ygxxJson,Map.class);
                        if(ygxxInfoList!=null && ygxxInfoList.size()>0) {
                            Map<String, Object> ygxxInfoMap = ygxxInfoList.get(0);
                            if(ygxxInfoMap.get("BDCDJZMH")!= null) {
                                BDC_SZZH = (String)ygxxInfoMap.get("BDCDJZMH");
                                BDC_SZZH = BDC_SZZH+"等"+ygxxInfoList.size()+"本";
                            }
                        }
                    }
                }
            }else if("345071904QR01302".equals(ITEM_CODE) || "201609140001GF00113".equals(ITEM_CODE)) {
                String tableName = "T_BDC_DYQZXDJ";
                if("201609140001GF00113".equals(ITEM_CODE)) {
                    tableName = "T_BDCQLC_DYQZXDJ";
                }
                Map<String, Object> sourceData = this.getSourceDataByExeId(EXE_ID, tableName);
                if(sourceData!=null && sourceData.size()>0) {
                    if(sourceData.get("ZXMX_JSON")!=null) {
                        String zxmxJson = (String)sourceData.get("ZXMX_JSON");
                        List<Map> zxmxInfoList = JSON.parseArray(zxmxJson,Map.class);
                        if(zxmxInfoList!=null && zxmxInfoList.size()>0) {
                            Map<String, Object> zxmxInfoMap = zxmxInfoList.get(0);
                            if(zxmxInfoMap.get("BDCQZH")!= null) {
                                BDC_SZZH = (String)zxmxInfoMap.get("BDCQZH");
                                BDC_SZZH = BDC_SZZH+"等"+zxmxInfoList.size()+"本";
                            }
                        }
                    }
                }
            }else if("569262478GF02410".equals(ITEM_CODE)){
                Map<String, Object> sourceData = this.getSourceDataByExeId(EXE_ID, "T_BDCQLC_YGSPFYGDJ");
                if(sourceData!=null && sourceData.size()>0) {
                    if(sourceData.get("BDC_BDCDJZMH")!=null) {
                        BDC_SZZH = (String)sourceData.get("BDC_BDCDJZMH");
                        BDC_SZZH = BDC_SZZH+"等1本";
                    }
                }
            }
            pushData.put("QYDM", "351001");
            pushData.put("SZS", "351000");
            pushData.put("SZQ", "351001");
            pushData.put("SQBH", EXE_ID);
            pushData.put("YWH", EXE_ID.substring(4, 16));
            pushData.put("CQZH", BDC_SZZH);
            pushData.put("SEND_STATUS", "0");
            log.info("推送办结数据到省网，数据："+pushData);
            this.saveOrUpdate(pushData, "T_INTERFACE_EXTRANETCONCLUDE", null);
        }
    }
    
    /**
     * 登记类型名称转换
     * 
     * @param catalogName
     * @return 
     */
    public String transformCatalogName(String catalogName){
        //登记类型：首次登记/转移登记/变更登记/注销登记/更正登记/异议登记/预告登记/查封登记/其它登记
        if(catalogName.contains("首次")) {
            return "首次登记";
        }else if(catalogName.contains("转移")) {
            return "转移登记";
        }else if(catalogName.contains("变更")) {
            return "变更登记";
        }else if(catalogName.contains("注销")) {
            return "注销登记";
        }else if(catalogName.contains("更正")) {
            return "更正登记";
        }else if(catalogName.contains("异议")) {
            return "异议登记";
        }else if(catalogName.contains("预告")) {
            return "预告登记";
        }else if(catalogName.contains("查封")) {
            return "查封登记";
        }
        return "其它登记";
    }
    /**
     * 根据事项编码获取事项信息
     * 
     * @param itemCode  事项编码
     * @return
     */
    public Map<String, Object> transformInfoByItemCode(String itemCode){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if("569262478GF02410".equals(itemCode)) {
            resultMap.put("ITEM_NAME", "预购商品房预告登记");
            resultMap.put("BZ_DJLXMC", "预告类");
            resultMap.put("RECTYPE", "11350128345071904JGF000003");
        }/*else if("569262478GF03217".equals(itemCode)) {
            resultMap.put("ITEM_NAME", "国有建设用地使用权首次登记");
            resultMap.put("BZ_DJLXMC", "其他类");
            resultMap.put("RECTYPE", "11350128345071904JGF000014");
        }*/else if("345071904QR01303".equals(itemCode) || 
                   "345071904QR01306".equals(itemCode) || "11350128345071904JQR000039L".equals(itemCode)) {
            resultMap.put("ITEM_NAME", "抵押权首次登记");
            resultMap.put("BZ_DJLXMC", "抵押类");
            resultMap.put("RECTYPE", "11350128345071904JGF000012");
        }else if("345071904QR01302".equals(itemCode) || "201609140001GF00113".equals(itemCode)) {
            resultMap.put("ITEM_NAME", "抵押权注销登记");
            resultMap.put("BZ_DJLXMC", "其他类");
            resultMap.put("RECTYPE", "11350128345071904JGF000025");
        }else {
            resultMap.put("ITEM_NAME", "预购商品房抵押预告登记");
            resultMap.put("BZ_DJLXMC", "预告类");
            resultMap.put("RECTYPE", "11350128345071904JGF000005");
        }
        return resultMap;
    }
    
    /**
     * 证件类型转换
     * 
     * @param SQRZJLX 申请人证件类型
     * @return
     */
    public String transformCardType(String SQRZJLX) {
        if("SF".equals(SQRZJLX)) {
            return "1";
        }else if("YYZZ".equals(SQRZJLX)) {
            return "7";
        }else if("JGDM".equals(SQRZJLX)) {
            return "6";
        }else if("JG".equals(SQRZJLX) || "SB".equals(SQRZJLX)) {
            return "5";
        }else if("HZ".equals(SQRZJLX)) {
            return "3";
        }else if("TWTX".equals(SQRZJLX)) {
            return "22";
        }else if("HKSF".equals(SQRZJLX) || "AMSF".equals(SQRZJLX) || "TWSF".equals(SQRZJLX)) {
            return "2";
        }else if("GATX".equals(SQRZJLX)) {
            return "21";
        }
        return "99";
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void extranetApplyReport(Log log) throws IOException{
        Properties cofProperties = FileUtil.readProperties("conf/config.properties");
        Properties properties = FileUtil.readProperties("project.properties");
        String EXTRANET_URL = cofProperties.getProperty("EXTRANET_URL");
        String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
        List<Map<String, Object>> pushDataList = this.findExtranetApplyDataList();
        if(pushDataList!=null && pushDataList.size()>0) {
            for(Map<String, Object> pushDataMap : pushDataList) {
                String SQBH = (String)pushDataMap.get("SQBH");
                String md5Encryption = StringUtil.getMd5Encode("Pt91m&2-"+SQBH);
                String base64Encryption1 = Base64.getEncoder().encodeToString(md5Encryption.getBytes());
                String token = Base64.getEncoder().encodeToString(base64Encryption1.getBytes());
                String qydm = (String)pushDataMap.get("QYDM");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("szs", pushDataMap.get("SZS"));
                map.put("szq", pushDataMap.get("SZQ"));
                map.put("sqbh", SQBH);
                map.put("djlx", pushDataMap.get("DJLX"));
                map.put("bz_djlxmc", pushDataMap.get("BZ_DJLXMC"));
                map.put("qllx", pushDataMap.get("QLLX"));
                map.put("sflb", pushDataMap.get("SFLB"));
                map.put("tjsj", pushDataMap.get("TJSJ"));
                map.put("sqrlist", pushDataMap.get("SQRLIST"));
                map.put("dylist", pushDataMap.get("DYLIST"));
                String mapToString = JSON.toJSONString(map);
                mapToString = mapToString.replace("\\", "");
                mapToString = mapToString.replace("\"[", "[");
                mapToString = mapToString.replace("]\"", "]");
                String dt1 = Base64.getEncoder().encodeToString(mapToString.toString().getBytes("UTF-8"));
                String dt = Base64.getEncoder().encodeToString(dt1.toString().getBytes("UTF-8"));
                
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("token", token);
                paramMap.put("qydm", qydm);
                paramMap.put("dt", dt);
                Map<String,Object> params = new HashMap<>();
                params.put("url", EXTRANET_URL);
                params.put("method","sendHttpPostJson");
                params.putAll(paramMap);
                String dghy_result = HttpSendUtil.sendPostParams(forwardUrl, params);
                
                /*String paramJson = JSON.toJSONString(paramMap);
                String dghy_result = HttpRequestUtil.sendBdcPost(EXTRANET_URL, paramJson);*/
                Map<String, Object> dghyResultMap = JSON.parseObject(dghy_result);
                if(dghyResultMap!=null && dghyResultMap.size()>0) {
                    if(dghyResultMap.get("statuscode")!=null) {
                        String resultStatus = (String)dghyResultMap.get("statuscode");
                        if(resultStatus!=null && "0000".equals(resultStatus)) {
                            pushDataMap.put("SEND_STATUS", "1");
                            pushDataMap.put("S_WWSQBH", dghyResultMap.get("s_wwsqbh"));
                        }else {
                            pushDataMap.put("SEND_STATUS", "2");//推送失败
                            pushDataMap.put("FAIL_CODE", resultStatus);
                            /*returnMap.put("status", resultStatus);
                            returnMap.put("message", "推送失败!失败的主表ID为："+(String)pushDataMap.get("EXTRANETAPPLY_ID"));
                            sendSuccess = false;
                            break;*/
                        }
                        this.saveOrUpdate(pushDataMap, 
                                "T_INTERFACE_EXTRANETAPPLY", (String)pushDataMap.get("EXTRANETAPPLY_ID"));
                    }else {
                        pushDataMap.put("SEND_STATUS", "2");//推送失败
                        pushDataMap.put("FAIL_CODE", "0004");//statuscode为空
                        this.saveOrUpdate(pushDataMap, 
                                "T_INTERFACE_EXTRANETAPPLY", (String)pushDataMap.get("EXTRANETAPPLY_ID"));
                    }
                }else {
                    pushDataMap.put("SEND_STATUS", "2");//推送失败
                    pushDataMap.put("FAIL_CODE", "0005");//dghy_result为空
                    this.saveOrUpdate(pushDataMap, 
                            "T_INTERFACE_EXTRANETAPPLY", (String)pushDataMap.get("EXTRANETAPPLY_ID"));
                }
            }
            /*if(sendSuccess) {
                returnMap.put("message", "推送成功!");
            }*/
            log.info("已推送!");
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void extranetConcludeReport(Log log) throws IOException{
        Properties cofProperties = FileUtil.readProperties("conf/config.properties");
        Properties properties = FileUtil.readProperties("project.properties");
        String EXTRANET_URL = cofProperties.getProperty("CONCLUDE_URL");
        String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
        List<Map<String, Object>> extranetConcludeList = this.findExtranetConcludeDataList();
        if(extranetConcludeList!=null && extranetConcludeList.size()>0) {
            //boolean sendSuccess = true;
            for(Map<String, Object> concludeDataMap : extranetConcludeList) {
                String SQBH = (String)concludeDataMap.get("SQBH");
                Map<String, Object> extranetApplyMap = this.getByJdbc("T_INTERFACE_EXTRANETAPPLY", 
                        new String[] { "SQBH" }, new Object[] { SQBH });
                String md5Encryption = StringUtil.getMd5Encode("Pt91m&2-"+SQBH);
                String base64Encryption1 = Base64.getEncoder().encodeToString(md5Encryption.getBytes());
                String token = Base64.getEncoder().encodeToString(base64Encryption1.getBytes());
                String qydm = (String)concludeDataMap.get("QYDM");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("szs", concludeDataMap.get("SZS"));
                map.put("szq", concludeDataMap.get("SZQ"));
                map.put("sqbh", SQBH);
                if(extranetApplyMap!=null && extranetApplyMap.size()>0) {
                    map.put("s_wwsqbh", extranetApplyMap.get("S_WWSQBH"));
                    map.put("rectype", extranetApplyMap.get("RECTYPE"));
                }
                /*else {
                    returnMap.put("message", "省外网不存在申报号为："+SQBH+"的申请数据");
                    sendSuccess = false;
                    break;
                }*/
                map.put("ywh", concludeDataMap.get("YWH"));
                map.put("cqzh", concludeDataMap.get("CQZH"));
                map.put("bjsj", concludeDataMap.get("BJSJ"));
                String mapToString = JSON.toJSONString(map);
                String dt1 = Base64.getEncoder().encodeToString(mapToString.toString().getBytes("UTF-8"));
                String dt = Base64.getEncoder().encodeToString(dt1.toString().getBytes("UTF-8"));
                
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("token", token);
                paramMap.put("qydm", qydm);
                paramMap.put("dt", dt);
                Map<String,Object> params = new HashMap<>();
                params.put("url", EXTRANET_URL);
                params.put("method","sendHttpPostJson");
                params.putAll(paramMap);
                String dghy_result = HttpSendUtil.sendPostParams(forwardUrl, params);
                /*String paramJson = JSON.toJSONString(paramMap);
                String dghy_result = HttpRequestUtil.sendBdcPost(EXTRANET_URL, paramJson);*/
                Map<String, Object> dghyResultMap = JSON.parseObject(dghy_result);
                if(dghyResultMap!=null && dghyResultMap.size()>0) {
                    if(dghyResultMap.get("statuscode")!=null) {
                        String resultStatus = (String)dghyResultMap.get("statuscode");
                        if(resultStatus!=null && "0000".equals(resultStatus)) {
                            concludeDataMap.put("SEND_STATUS", "1");
                        }else {
                            concludeDataMap.put("SEND_STATUS", "2");//推送失败
                            concludeDataMap.put("FAIL_CODE", resultStatus);
                            /*returnMap.put("status", resultStatus);
                            returnMap.put("message", "推送失败!失败的主表ID为："+(String)concludeDataMap.get("EXTRANETCONCLUDE_ID"));
                            sendSuccess = false;
                            break;*/
                        }
                        this.saveOrUpdate(concludeDataMap, 
                                "T_INTERFACE_EXTRANETCONCLUDE", (String)concludeDataMap.get("EXTRANETCONCLUDE_ID"));
                    }else {
                        concludeDataMap.put("SEND_STATUS", "2");//推送失败
                        concludeDataMap.put("FAIL_CODE", "0004");//statuscode为空
                        this.saveOrUpdate(concludeDataMap, 
                                "T_INTERFACE_EXTRANETCONCLUDE", (String)concludeDataMap.get("EXTRANETCONCLUDE_ID"));
                    }
                }else {
                    concludeDataMap.put("SEND_STATUS", "2");//推送失败
                    concludeDataMap.put("FAIL_CODE", "0005");//dghy_result为空
                    this.saveOrUpdate(concludeDataMap, 
                            "T_INTERFACE_EXTRANETCONCLUDE", (String)concludeDataMap.get("EXTRANETCONCLUDE_ID"));
                }
            }
            /*if(sendSuccess) {
                returnMap.put("message", "推送成功!");
            }*/
            log.info("已推送!");
        }
    }
    
    /**
     * 国有建设用地使用权及房屋所有权转移登记-全流程（办结后置事件）
     * 
     * @author Scolder Lin
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveAfterBusData(Map<String, Object> flowDatas) throws Exception {
        String isTempSave = (String) flowDatas.get("EFLOW_ISTEMPSAVE");
        if("-1".equals(isTempSave)) {
            //保存要推送到省网的办结数据
            concludeDataStorage(flowDatas);
            
            String EFLOW_BUSRECORDID = "";
            if(flowDatas.get("EFLOW_BUSRECORDID")!=null) {
                EFLOW_BUSRECORDID = (String)flowDatas.get("EFLOW_BUSRECORDID");
            }
            
            
            if(flowDatas.get("EFLOW_BUSTABLENAME")!=null &&
                    "T_BDCQLC_GYJSJFWZYDJCLFSFLB".equals(flowDatas.get("EFLOW_BUSTABLENAME"))) {
                flowDatas.put("ISSHOWLX", "1");//买房一件事套餐流程，子流程不展示类型
            }
            
            //判断是否发起水电气新流程
            boolean isSdqbl = false;
            String IS_POWTRANSFER = "";
            if(flowDatas.get("IS_POWTRANSFER")!=null) {
                IS_POWTRANSFER = (String)flowDatas.get("IS_POWTRANSFER");
                if("1".equals(IS_POWTRANSFER)) {
                    isSdqbl = true;
                }
            }else {
                flowDatas.put("concludeType", "1");//自动办结
            }
            String IS_WATERTRANSFER = "";
            if(flowDatas.get("IS_WATERTRANSFER")!=null) {
                IS_WATERTRANSFER = (String)flowDatas.get("IS_WATERTRANSFER");
                if("1".equals(IS_WATERTRANSFER)) {
                    isSdqbl = true;
                }
            }else {
                flowDatas.put("concludeType", "1");//自动办结
            }
            String IS_GASTRANSFER = "";
            if(flowDatas.get("IS_GASTRANSFER")!=null) {
                IS_GASTRANSFER = (String)flowDatas.get("IS_GASTRANSFER");
                if("1".equals(IS_GASTRANSFER)) {
                    isSdqbl = true;
                }
            }else {
                flowDatas.put("concludeType", "1");//自动办结
            }
            String IS_SVATRANSFER = "";
            if(flowDatas.get("IS_SVATRANSFER")!=null) {
                IS_SVATRANSFER = (String)flowDatas.get("IS_SVATRANSFER");
                if("1".equals(IS_SVATRANSFER)) {
                    isSdqbl = true;
                }
            }else {
                flowDatas.put("concludeType", "1");//自动办结
            }
            //防止自动办结无法发起水电气广流程
            if(flowDatas.get("concludeType")!=null && "1".equals((String)flowDatas.get("concludeType"))) {
                String exeId = "";
                if(flowDatas.get("EXE_ID")!=null) {
                    exeId = (String) flowDatas.get("EXE_ID");
                }else if(flowDatas.get("EFLOW_EXEID")!=null) {
                    exeId = (String) flowDatas.get("EFLOW_EXEID");
                }
                flowDatas.put("EXE_ID", exeId);
                if(!"".equals(exeId)) {
                    Map<String, Object> exeMap = this.getByJdbc("JBPM6_EXECUTION",
                            new String[]{"EXE_ID"}, new Object[]{exeId});
                    if(exeMap!=null && exeMap.size()>0) {
                        EFLOW_BUSRECORDID = (String) exeMap.get("BUS_RECORDID");
                        flowDatas.put("EFLOW_BUSRECORDID", EFLOW_BUSRECORDID);
                        String tableName = (String) exeMap.get("BUS_TABLENAME");
                        if("T_BDCQLC_GYJSJFWZYDJCLFSFLB".equals(tableName)){//买房一件事套餐流程，子流程不展示类型
                            flowDatas.put("ISSHOWLX", "1");//不展示
                        }
                        if("T_BDCQLC_GYJSJFWZYDJ".equals(tableName) || tableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0) {
                            Map<String, Object> recordMap = this.getByJdbc("T_BDCQLC_GYJSJFWZYDJ",
                                    new String[]{"YW_ID"}, new Object[]{EFLOW_BUSRECORDID});
                            if(recordMap.get("IS_POWTRANSFER")!=null) {
                                IS_POWTRANSFER = (String)recordMap.get("IS_POWTRANSFER");
                                if("1".equals(IS_POWTRANSFER)) {
                                    isSdqbl = true;
                                }
                            }
                            if(recordMap.get("IS_WATERTRANSFER")!=null) {
                                IS_WATERTRANSFER = (String)recordMap.get("IS_WATERTRANSFER");
                                if("1".equals(IS_WATERTRANSFER)) {
                                    isSdqbl = true;
                                }
                            }
                            if(recordMap.get("IS_GASTRANSFER")!=null) {
                                IS_GASTRANSFER = (String)recordMap.get("IS_GASTRANSFER");
                                if("1".equals(IS_GASTRANSFER)) {
                                    isSdqbl = true;
                                }
                            }
                            if(recordMap.get("IS_SVATRANSFER")!=null) {
                                IS_SVATRANSFER = (String)recordMap.get("IS_SVATRANSFER");
                                if("1".equals(IS_SVATRANSFER)) {
                                    isSdqbl = true;
                                }
                            }
                        }
                    }
                }
            }
            if(isSdqbl) {
                Map<String, Object> recordMap = this.getByJdbc("T_BDCQLC_GYJSJFWZYDJ", new String[] { "YW_ID" },
                        new Object[] { EFLOW_BUSRECORDID });
                String ITEM_CODE = "";
                Properties properties = FileUtil.readProperties("conf/gyqlcsdq.properties");
                //获取不动产水电气联办的证书编号及证书名称
                log.info("不动产水电气flowDatas:" + flowDatas);
                if(StringUtils.isNotEmpty((String)flowDatas.get("xkfile_num"))){
                    recordMap.put("XKFILE_NUM", (String)flowDatas.get("xkfile_num"));
                }
                if(StringUtils.isNotEmpty((String)flowDatas.get("xkfile_name"))){
                    recordMap.put("XKFILE_NAME", (String)flowDatas.get("xkfile_name"));
                }
                if(IS_POWTRANSFER!=null && "1".equals(IS_POWTRANSFER)) {
                    ITEM_CODE = properties.getProperty("POW_ITEM_CODE");
                    Map<String, Object> dllcMap = createFlow(ITEM_CODE, flowDatas);
                    String POW_EXEID = (String)dllcMap.get("EFLOW_EXEID");
                    recordMap.put("POW_EXEID", POW_EXEID);
                }
                if(IS_WATERTRANSFER!=null && "1".equals(IS_WATERTRANSFER)) {
                    ITEM_CODE = properties.getProperty("WATER_ITEM_CODE");
                    Map<String, Object> sllcMap = createFlow(ITEM_CODE, flowDatas);
                    String WATER_EXEID = (String)sllcMap.get("EFLOW_EXEID");
                    recordMap.put("WATER_EXEID", WATER_EXEID);
                }
                if(IS_GASTRANSFER!=null && "1".equals(IS_GASTRANSFER)) {
                    ITEM_CODE = properties.getProperty("GAS_ITEM_CODE");
                    Map<String, Object> rqlcMap = createFlow(ITEM_CODE, flowDatas);
                    String GAS_EXEID = (String)rqlcMap.get("EFLOW_EXEID");
                    recordMap.put("GAS_EXEID", GAS_EXEID);
                }
                if(IS_SVATRANSFER!=null && "1".equals(IS_SVATRANSFER)) {
                    ITEM_CODE = properties.getProperty("SVA_ITEM_CODE");
                    Map<String, Object> gdlcMap = createFlow(ITEM_CODE, flowDatas);
                    String SVA_EXEID = (String)gdlcMap.get("EFLOW_EXEID");
                    recordMap.put("SVA_EXEID", SVA_EXEID);
                }
                this.saveOrUpdate(recordMap, "T_BDCQLC_GYJSJFWZYDJ", EFLOW_BUSRECORDID);
            }
        }
        log.info("水电气后置事件开始End");
        return flowDatas;
    }
    
    /**
     * 创建新流程
     * 
     * @param ITEM_CODE
     * @param flowDatas
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> createFlow(String ITEM_CODE, Map<String, Object> flowDatas) throws Exception {
        Map<String,Object> flowVars = new HashMap<String,Object>();
        String exeId = "";
        if(flowDatas.get("EFLOW_EXEID")!=null) {
            exeId = (String)flowDatas.get("EFLOW_EXEID");
        }else if(flowDatas.get("EXE_ID")!=null) {
            exeId = (String)flowDatas.get("EXE_ID");
        }
         
        Map<String, Object> item = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"}, new Object[]{ITEM_CODE});
        Map<String,Object> def = dao.getByJdbc("JBPM6_FLOWDEF", new String[]{"DEF_ID"}, new Object[]{item.get("BDLCDYID")});
        Map<String,Object> form = dao.getByJdbc("JBPM6_FLOWFORM", new String[]{"FORM_ID"}, new Object[]{def.get("BIND_FORMID")});
        Map<String, Object> exeMap = this.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{exeId});
        flowVars.put("EFLOW_CREATORACCOUNT","admin");
        String subject = (String)exeMap.get("SUBJECT");
        flowVars.put("EFLOW_CREATORNAME", subject.split("-")[0]);
        if(StringUtils.isEmpty(String.valueOf(flowVars.get("EFLOW_CREATORNAME")))){
            flowVars.put("EFLOW_CREATORNAME", flowDatas.get("CKSJRYXM"));
        }
        if(StringUtils.isEmpty(String.valueOf(flowVars.get("EFLOW_CREATORNAME")))){
            flowVars.put("EFLOW_CREATORNAME", "系统自动生成");
        }
        flowVars.put("ISSHOWLX",StringUtil.getString(flowDatas.get("ISSHOWLX")));
        flowVars.put("EFLOW_DEFKEY", def.get("DEF_KEY"));
        flowVars.put("EFLOW_BUSTABLENAME", form.get("FORM_KEY"));
        flowVars.put("EFLOW_DEFID", def.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION",def.get("VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL","false");
        flowVars.put("EFLOW_ISTEMPSAVE","-1");
        flowVars.put("ITEM_NAME", item.get("ITEM_NAME"));
        flowVars.put("ITEM_CODE", item.get("ITEM_CODE"));
        flowVars.put("SSBMBM", item.get("SSBMBM"));
        flowVars.put("SQFS","1");
        //获取当前运行节点
        String currentOperNodeName = flowNodeService.getNodeName(item.get("BDLCDYID").toString(), 
                ((BigDecimal)def.get("VERSION")).intValue(), Jbpm6Constants.START_NODE);
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES",currentOperNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME",currentOperNodeName);
        flowVars.put("isSendMessage", "notsend");
        
        //获取发起节点名称
        if(StringUtils.isNotEmpty(currentOperNodeName)){
            String nextStepJson = this.jbpmService.getNextStepsJson(def.get("DEF_ID").toString(), 
                    ((BigDecimal)def.get("VERSION")).intValue(),currentOperNodeName, flowVars);
            if(StringUtils.isNotEmpty(nextStepJson)){
                flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
            }
        }
        if(flowDatas.get("concludeType")!=null && "1".equals((String)flowDatas.get("concludeType"))) {
            concludeAutoGYJSJFWZYDJ(flowVars, flowDatas);
        }else {
            concludeGYJSJFWZYDJ(flowVars, flowDatas);
        }
        
        //创建新流程完成
        return jbpmService.doFlowJob(flowVars);
    }
    /**
     * 办结国有建设用地使用权及房屋所有权转移登记-全流程
     * @param flowVars
     * @param flowDatas
     * @return
     */
    public Map<String, Object> concludeGYJSJFWZYDJ(Map<String, Object> flowVars, Map<String, Object> flowDatas){
        //插入主表数据
        flowVars.put("ZYDJ_TYPE",flowDatas.get("ZYDJ_TYPE"));
        flowVars.put("ESTATE_NUM",flowDatas.get("ESTATE_NUM"));
        flowVars.put("TAKECARD_TYPE",flowDatas.get("TAKECARD_TYPE"));
        flowVars.put("APPLICANT_UNIT",flowDatas.get("APPLICANT_UNIT"));
        flowVars.put("DZWQLLX",flowDatas.get("DZWQLLX"));
        flowVars.put("LOCATED",flowDatas.get("LOCATED"));
        flowVars.put("NOTIFY_NAME",flowDatas.get("NOTIFY_NAME"));
        flowVars.put("NOTIFY_TEL",flowDatas.get("NOTIFY_TEL"));
        flowVars.put("QZR_NAME",flowDatas.get("QZR_NAME"));
        flowVars.put("QZR_ZJH",flowDatas.get("QZR_ZJH"));
        flowVars.put("HTBAH",flowDatas.get("HTBAH"));
        flowVars.put("IS_FINISHTAX",flowDatas.get("IS_FINISHTAX"));
        flowVars.put("REMARK",flowDatas.get("REMARK"));
        flowVars.put("IS_POWTRANSFER",flowDatas.get("IS_POWTRANSFER"));
        flowVars.put("POW_NUM",flowDatas.get("POW_NUM"));
        flowVars.put("IS_WATERTRANSFER",flowDatas.get("IS_WATERTRANSFER"));
        flowVars.put("WATER_NUM",flowDatas.get("WATER_NUM"));
        flowVars.put("IS_GASTRANSFER",flowDatas.get("IS_GASTRANSFER"));
        flowVars.put("GAS_NUM",flowDatas.get("GAS_NUM"));
        flowVars.put("IS_SVATRANSFER",flowDatas.get("IS_SVATRANSFER"));
        flowVars.put("SVA_NUM",flowDatas.get("SVA_NUM"));
        flowVars.put("POWERPEOPLEINFO_JSON",flowDatas.get("POWERPEOPLEINFO_JSON"));
        
        //插入申报对象信息（个人）
        flowVars.put("BSYHLX",flowDatas.get("BSYHLX"));
        flowVars.put("SQRMC",flowDatas.get("SQRMC"));
        flowVars.put("SQRXB",flowDatas.get("SQRXB"));
        flowVars.put("SQRZJLX",flowDatas.get("SQRZJLX"));
        flowVars.put("SQRSFZ",flowDatas.get("SQRSFZ"));
        flowVars.put("SQRSJH",flowDatas.get("SQRSJH"));
        flowVars.put("SQRDHH",flowDatas.get("SQRDHH"));
        flowVars.put("SQRLXDZ",flowDatas.get("SQRLXDZ"));
        flowVars.put("SQRYJ",flowDatas.get("SQRYJ"));
        //插入申报对象信息（法人）
        flowVars.put("SQJG_NAME",flowDatas.get("SQJG_NAME"));
        flowVars.put("SQJG_TYPE",flowDatas.get("SQJG_TYPE"));
        flowVars.put("SQJG_CODE",flowDatas.get("SQJG_CODE"));
        flowVars.put("SQJG_CREDIT_CODE",flowDatas.get("SQJG_CREDIT_CODE"));
        flowVars.put("REGIST_NUM",flowDatas.get("REGIST_NUM"));
        flowVars.put("SQJG_LEALPERSON",flowDatas.get("SQJG_LEALPERSON"));
        flowVars.put("SQJG_TEL",flowDatas.get("SQJG_TEL"));
        flowVars.put("SQJG_LEALPERSON_ZJLX",flowDatas.get("SQJG_LEALPERSON_ZJLX"));
        flowVars.put("SQJG_LEALPERSON_ZJHM",flowDatas.get("SQJG_LEALPERSON_ZJHM"));
        flowVars.put("SQJG_ADDR",flowDatas.get("SQJG_ADDR"));
        //经办人信息
        flowVars.put("SFXSJBRXX",flowDatas.get("SFXSJBRXX"));
        flowVars.put("JBR_NAME",flowDatas.get("JBR_NAME"));
        flowVars.put("JBR_SEX",flowDatas.get("JBR_SEX"));
        flowVars.put("JBR_ZJLX",flowDatas.get("JBR_ZJLX"));
        flowVars.put("JBR_ZJHM",flowDatas.get("JBR_ZJHM"));
        flowVars.put("JBR_MOBILE",flowDatas.get("JBR_MOBILE"));
        flowVars.put("JBR_LXDH",flowDatas.get("JBR_LXDH"));
        flowVars.put("JBR_POSTCODE",flowDatas.get("JBR_POSTCODE"));
        flowVars.put("JBR_EMAIL",flowDatas.get("JBR_EMAIL"));
        flowVars.put("JBR_ADDRESS",flowDatas.get("JBR_ADDRESS"));
        flowVars.put("JBR_REMARK",flowDatas.get("JBR_REMARK"));
        flowVars.put("CKSJRYXM",flowDatas.get("CKSJRYXM"));
        
        return flowVars;
    }
    
    /**
     * 自动办结国有建设用地使用权及房屋所有权转移登记-全流程
     * @param flowVars
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> concludeAutoGYJSJFWZYDJ(Map<String, Object> flowVars, Map<String, Object> flowDatas){
        String exeId = (String) flowDatas.get("EXE_ID");
        String recordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        Map<String, Object> exeMap = this.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"}, new Object[]{exeId});
        Map<String, Object> recordMap = this.getByJdbc("T_BDCQLC_GYJSJFWZYDJ",
                new String[]{"YW_ID"}, new Object[]{recordId});
        //插入主表数据
        flowVars.put("ZYDJ_TYPE",recordMap.get("ZYDJ_TYPE"));
        flowVars.put("ESTATE_NUM",recordMap.get("ESTATE_NUM"));
        flowVars.put("TAKECARD_TYPE",recordMap.get("TAKECARD_TYPE"));
        flowVars.put("APPLICANT_UNIT",recordMap.get("APPLICANT_UNIT"));
        flowVars.put("DZWQLLX",recordMap.get("DZWQLLX"));
        flowVars.put("LOCATED",recordMap.get("LOCATED"));
        flowVars.put("NOTIFY_NAME",recordMap.get("NOTIFY_NAME"));
        flowVars.put("NOTIFY_TEL",recordMap.get("NOTIFY_TEL"));
        flowVars.put("QZR_NAME",recordMap.get("QZR_NAME"));
        flowVars.put("QZR_ZJH",recordMap.get("QZR_ZJH"));
        flowVars.put("HTBAH",recordMap.get("HTBAH"));
        flowVars.put("IS_FINISHTAX",recordMap.get("IS_FINISHTAX"));
        flowVars.put("REMARK",recordMap.get("REMARK"));
        flowVars.put("IS_POWTRANSFER",recordMap.get("IS_POWTRANSFER"));
        flowVars.put("POW_NUM",recordMap.get("POW_NUM"));
        flowVars.put("IS_WATERTRANSFER",recordMap.get("IS_WATERTRANSFER"));
        flowVars.put("WATER_NUM",recordMap.get("WATER_NUM"));
        flowVars.put("IS_GASTRANSFER",recordMap.get("IS_GASTRANSFER"));
        flowVars.put("GAS_NUM",recordMap.get("GAS_NUM"));
        flowVars.put("IS_SVATRANSFER",recordMap.get("IS_SVATRANSFER"));
        flowVars.put("SVA_NUM",recordMap.get("SVA_NUM"));
        flowVars.put("POWERPEOPLEINFO_JSON",recordMap.get("POWERPEOPLEINFO_JSON"));
        
        //插入申报对象信息（个人）
        flowVars.put("BSYHLX",exeMap.get("BSYHLX"));
        flowVars.put("SQRMC",exeMap.get("SQRMC"));
        flowVars.put("SQRXB",exeMap.get("SQRXB"));
        flowVars.put("SQRZJLX",exeMap.get("SQRZJLX"));
        flowVars.put("SQRSFZ",exeMap.get("SQRSFZ"));
        flowVars.put("SQRSJH",exeMap.get("SQRSJH"));
        flowVars.put("SQRDHH",exeMap.get("SQRDHH"));
        flowVars.put("SQRLXDZ",exeMap.get("SQRLXDZ"));
        flowVars.put("SQRYJ",exeMap.get("SQRYJ"));
        
        //插入申报对象信息（法人）
        flowVars.put("SQJG_NAME",exeMap.get("SQJG_NAME"));
        flowVars.put("SQJG_TYPE",exeMap.get("SQJG_TYPE"));
        flowVars.put("SQJG_CODE",exeMap.get("SQJG_CODE"));
        flowVars.put("SQJG_CREDIT_CODE",exeMap.get("SQJG_CREDIT_CODE"));
        flowVars.put("REGIST_NUM",exeMap.get("REGIST_NUM"));
        flowVars.put("SQJG_LEALPERSON",exeMap.get("SQJG_LEALPERSON"));
        flowVars.put("SQJG_TEL",exeMap.get("SQJG_TEL"));
        flowVars.put("SQJG_LEALPERSON_ZJLX",exeMap.get("SQJG_LEALPERSON_ZJLX"));
        flowVars.put("SQJG_LEALPERSON_ZJHM",exeMap.get("SQJG_LEALPERSON_ZJHM"));
        flowVars.put("SQJG_ADDR",exeMap.get("SQJG_ADDR"));
        
        //经办人信息
        flowVars.put("SFXSJBRXX",exeMap.get("SFXSJBRXX"));
        flowVars.put("JBR_NAME",exeMap.get("JBR_NAME"));
        flowVars.put("JBR_SEX",exeMap.get("JBR_SEX"));
        flowVars.put("JBR_ZJLX",exeMap.get("JBR_ZJLX"));
        flowVars.put("JBR_ZJHM",exeMap.get("JBR_ZJHM"));
        flowVars.put("JBR_MOBILE",exeMap.get("JBR_MOBILE"));
        flowVars.put("JBR_LXDH",exeMap.get("JBR_LXDH"));
        flowVars.put("JBR_POSTCODE",exeMap.get("JBR_POSTCODE"));
        flowVars.put("JBR_EMAIL",exeMap.get("JBR_EMAIL"));
        flowVars.put("JBR_ADDRESS",exeMap.get("JBR_ADDRESS"));
        flowVars.put("JBR_REMARK",exeMap.get("JBR_REMARK"));
        flowVars.put("CKSJRYXM",exeMap.get("CREATOR_NAME"));
        
        return flowVars;
    }
    
    /**
     * 保存推送到省网的办结数据（后置事件）
     * 
     * @author Scolder Lin
     * @param flowDatas
     * @created 2021年7月28日 上午9:32:05
     * @return
     * @throws Exception 
     */
    @Override
    public Map<String, Object> saveConcludeDataStorage(Map<String, Object> flowDatas){
        String isTempSave = (String) flowDatas.get("EFLOW_ISTEMPSAVE");
        if("-1".equals(isTempSave)) {
            //保存要推送到省网的办结数据
            concludeDataStorage(flowDatas);
        }
        return flowDatas;
    }
}
