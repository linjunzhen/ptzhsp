/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.dao.ZxInterfaceDao;
import net.evecom.platform.wsbs.service.BdcDataToSwbService;
import net.evecom.platform.wsbs.service.SwbInterfaceService;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 描述 不动产定时任务，生成要报送省网的数据
 * 
 * @author Yanisin Shi
 */
@Service("bdcDataToSwbService")
public class BdcDataToSwbServiceImpl extends BaseServiceImpl implements BdcDataToSwbService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 11:15:00
     * @param
     * @return
     */
    private Logger log = LoggerFactory.getLogger(BdcDataToSwbServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 10:28:00
     * @param
     * @return
     */
    @Resource
    private ZxInterfaceDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 14:35:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:08:00
     * @param
     * @return
     */
    @Resource
    private SwbInterfaceDao swbInterfaceDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:28:00
     * @param
     * @return
     */
    @Resource
    private SwbInterfaceService swbInterfaceService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:26:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/5/7 9:38:00
     * @param
     * @return
     */
    private Properties properties = FileUtil.readProperties("project.properties");

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 10:28:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    
   
    @Override
    public void processData() {
        try{
        appayDataStorage();
        concludeDataStorage();
        log.info("《---------------------结束生成要推送到省网的不动产业务数据（办理、办结）---------------------------------》");
        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
    }
    /**
     * 
     * 描述     定时任务插入t_interface_extranetapply
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
             *
     * @author Yanisin Shi
     */
    public void appayDataStorage() {
        try {
           
            StringBuffer sql=new StringBuffer("select e.exe_id, e.bus_recordid, e.bus_tablename,e.item_code");
            sql.append(" from jbpm6_execution e ");
            sql.append(" where item_code in  ('569262478QR00310','345071904QR01404','569262478GF03217','345071904QR01303',");
            sql.append(" '11350128345071904JQR000039L','345071904QR01306','345071904QR01302','201609140001GF00113',");
            sql.append(" '569262478GF02409','569262478GF02410') ");
            sql.append(" and not exists(select 1 from t_interface_extranetapply t where t.sqbh = e.exe_id) ");
            sql.append(" and e.create_time> '2021-09-01 00:00:00'");
            List<Map<String,Object>>executionMapList=dao.findBySql(sql.toString(),null, null);
        
            if(executionMapList!=null){
                for( int i=0;i<executionMapList.size();i++){
             String ITEM_CODE = StringUtil.getString(executionMapList.get(i).get("ITEM_CODE"));
             String EFLOW_BUSTABLENAME = StringUtil.getString(executionMapList.get(i).get("BUS_TABLENAME"));
             String EXE_ID=StringUtil.getString(executionMapList.get(i).get("EXE_ID"));
             String bus_recordid=StringUtil.getString(executionMapList.get(i).get("BUS_RECORDID"));
             String  ITEM_NAME=StringUtil.getString(executionMapList.get(i).get("ITEM_NAME"));
        Map<String,Object> busMap=dao.getByJdbc(EFLOW_BUSTABLENAME,new String[]{"YW_ID"}, new String[]{bus_recordid});
        Map<String,Object> serviceItem=dao.getByJdbc("T_WSBS_SERVICEITEM",new String[]{"ITEM_CODE"}, new String[]{ITEM_CODE});

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
            //log.info("item_code:"+ITEM_CODE);
           // log.info("EFLOW_BUSTABLENAME:"+EFLOW_BUSTABLENAME);
             if("569262478QR00310".equals(ITEM_CODE) || "569262478GF02410".equals(ITEM_CODE) 
                        /*|| "569262478GF03217".equals(ITEM_CODE)*/ || "345071904QR01303".equals(ITEM_CODE)
                        || "345071904QR01306".equals(ITEM_CODE) || "345071904QR01302".equals(ITEM_CODE)
                        || "201609140001GF00113".equals(ITEM_CODE) || "569262478GF02409".equals(ITEM_CODE)
                        || "11350128345071904JQR000039L".equals(ITEM_CODE) || "345071904QR01404".equals(ITEM_CODE)
                        || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0) {
                    Map<String, Object> pushData = new HashMap<String,Object>();
                   // String EXE_ID = (String)variables.get("EFLOW_EXEID");
               // log.info("EXE_ID:"+EXE_ID);
                    String CATALOG_NAME = "";
                    if(serviceItem.get("ITEM_NAME")!=null) {
                        CATALOG_NAME = (String)serviceItem.get("ITEM_NAME");
                        CATALOG_NAME = transformCatalogName(CATALOG_NAME);
                    //外网业务归类：抵押类/预告类/一手房转移类/二手房转移类/其他类。联办时，请优先填写一手转移类/二手房转移类/抵押类。
                    String BZ_DJLXMC = "";
                  //  String ITEM_NAME = "";
                    String SFLB = "";
                    String RECTYPE = "";
                    
                    if("569262478QR00310".equals(ITEM_CODE) || EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0
                            || "345071904QR01404".equals(ITEM_CODE)) {
                        
                        String ZYDJ_TYPE = "";
                        //根据bus_recordid获取数据表证件类型
                      
                        if(busMap.get("ZYDJ_TYPE")!=null) {
                            ZYDJ_TYPE = (String)busMap.get("ZYDJ_TYPE");
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
                        if(busMap.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", busMap.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("569262478GF02410".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_YGSPFYGDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(busMap.get("BDCDYH")!=null) {
                            dyMap.put("bdcdyh", busMap.get("BDCDYH"));
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
                        if(busMap.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", busMap.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("345071904QR01306".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDCQLC_DYQSCDJZB");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(busMap.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", busMap.get("ESTATE_NUM"));
                        }else {
                            dyMap.put("bdcdyh", sourceData.get("ESTATE_NUM"));
                        }
                        dyMap.put("bdczl", sourceData.get("LOCATED"));
                    }else if("345071904QR01302".equals(ITEM_CODE)) {
                        sourceData = getSourceDataByExeId(EXE_ID, "T_BDC_DYQZXDJ");
                        //单元信息
                        dyMap.put("sqbh", EXE_ID);
                        if(busMap.get("BDCDYH")!=null) {
                            dyMap.put("bdcdyh", busMap.get("BDCDYH"));
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
                        if(busMap.get("BDCDYH")!=null) {
                            dyMap.put("bdcdyh", busMap.get("BDCDYH"));
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
                        if(busMap.get("ESTATE_NUM")!=null) {
                            dyMap.put("bdcdyh", busMap.get("ESTATE_NUM"));
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
                    StringBuffer taskSql=new StringBuffer("");
                    taskSql.append("select max(t.end_time) as END_TIME from JBPM6_TASK t where t.exe_id='"+EXE_ID+"' and t.task_nodename='受理'");
                    taskSql.append(" and t.step_seq<>0 ");
                    Map<String,Object> taskMap=dao.getByJdbc(taskSql.toString(),null);
                    pushData.put("TJSJ",StringUtil.getString(taskMap.get("END_TIME")));
                    
                  //  log.info("saveData:success");
                    log.info("TJSJ"+pushData.get("TJSJ")+"SQBH:"+pushData.get("SQBH")+"DJLX:"+pushData.get("DJLX")
                    +"BZ_DJLXMC:"+pushData.get("BZ_DJLXMC")
                    +"SQBH:"+pushData.get("SQBH")+"QLLX:"+pushData.get("QLLX")
                    +"SFLB:"+pushData.get("SFLB") +"RECTYPE:"+pushData.get("RECTYPE")
                    +"SQRLIST:"+pushData.get("SQRLIST") +"DYLIST:"+pushData.get("DYLIST")
                     
                    
                            );
                    
                    
                    dao.saveOrUpdate(pushData, "T_INTERFACE_EXTRANETAPPLY", null);
                   
                }
            }
             }
            }
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        }
    }
    /**
     * 
     * 描述
     * @author Yanisin Shi
     * @param exeId
     * @param BUS_TABLENAME
     * @return
     */
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
 /**
  * 
  * 描述
  * @author Yanisin Shi
  * @param itemCode
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
  * 
  * 描述
  * @author Yanisin Shi
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
  * 
  * 描述
  * @author Yanisin Shi
  * @param SQRZJLX
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
 /**
  * 
  * 描述     定时任务插入T_INTERFACE_EXTRANETCONCLUDE
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
          *
  * @author Yanisin Shi
  */
 public void concludeDataStorage() {
     StringBuffer sql=new StringBuffer("select e.exe_id, e.bus_recordid, e.bus_tablename,e.item_code,e.end_time");
     sql.append(" from jbpm6_execution e  ");
     sql.append(" where item_code in  ('569262478QR00310','345071904QR01404','569262478GF03217','345071904QR01303',");
     sql.append(" '11350128345071904JQR000039L','345071904QR01306','345071904QR01302','201609140001GF00113',");
     sql.append(" '569262478GF02409','569262478GF02410') ");
     sql.append(" and not exists(select 1 from T_INTERFACE_EXTRANETCONCLUDE t where t.sqbh = e.exe_id) ");
     sql.append(" and e.end_time is not null and e.create_time>'2021-09-01 00:00:00'");
        List<Map<String,Object>>executionMapList=dao.findBySql(sql.toString(),null, null);
    
        if(executionMapList!=null){
            for( int i=0;i<executionMapList.size();i++){
         String ITEM_CODE = StringUtil.getString(executionMapList.get(i).get("ITEM_CODE"));
         String EFLOW_BUSTABLENAME = StringUtil.getString(executionMapList.get(i).get("BUS_TABLENAME"));
         String EXE_ID=StringUtil.getString(executionMapList.get(i).get("EXE_ID"));
         String bus_recordid=StringUtil.getString(executionMapList.get(i).get("BUS_RECORDID"));
         String  ITEM_NAME=StringUtil.getString(executionMapList.get(i).get("ITEM_NAME"));
         String end_time=StringUtil.getString(executionMapList.get(i).get("END_TIME"));
    Map<String,Object> busMap=dao.getByJdbc(EFLOW_BUSTABLENAME,new String[]{"YW_ID"}, new String[]{bus_recordid});
    Map<String,Object> serviceItem=dao.getByJdbc("T_WSBS_SERVICEITEM",new String[]{"ITEM_CODE"}, new String[]{ITEM_CODE});
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
            //bjsj
            pushData.put("BJSJ",end_time);
            //log.info("推送办结数据到省网，数据："+pushData);
            dao.saveOrUpdate(pushData, "T_INTERFACE_EXTRANETCONCLUDE", null);
        }
    }
 }  

}
