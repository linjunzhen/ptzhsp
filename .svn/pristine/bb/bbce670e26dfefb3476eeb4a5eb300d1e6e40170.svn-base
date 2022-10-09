/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.execl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.BusTypeDao;
import net.evecom.platform.wsbs.dao.ServiceItemDao;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
/**
 * 
 * 描述
 * @author Faker Li
 * @created 2015年11月12日 上午10:27:28
 */
public class ExeclItem20151207Case extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ExeclItem20151207Case.class);
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 所引入的dao
     */
    @Resource
    private ServiceItemDao dao;
    /**
     * 
     */
    @Resource
    private BusTypeDao typedao;
    
    
    /**
     * 所引入的service
     */ 
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年11月12日 上午10:24:46
     */
    @Test
    public void startFlow(){//流转过程人员选择器的优化和群组增加标识区分管理等开发工作
//        String excelPath = "C:\\Users\\user\\Desktop\\11.xls";
        List<List<Object>> list = new ArrayList<List<Object>>();
        // List<List<Object>> list = ExcelUtil.getExcelRowValuesSheet(excelPath,
        // 1,1,1);
//        List<Object> dataList = new ArrayList<Object>();
//        String sxxz = "";
//        String departId = "";
//        String departcode = "";
        for (int i = 0; i < list.size(); i++) {
            List<Object> rowValue = list.get(i);
               /*sxxz = getsxxz(rowValue.get(5).toString().trim());
               departId = getdepartId(rowValue.get(10).toString().trim());
               departcode = getdepartcode(rowValue.get(10).toString().trim());
               String catalogCode = savaCatalog(rowValue.get(0).toString().trim(),departcode+sxxz,departId,sxxz);
               Map<String, Object> variables = new HashMap<String,Object>();
               Map<String,Object>  catalog = catalogService.getCatalogByCatalogCode(catalogCode);
               String  maxNumCode = serviceItemService.getMaxNumCode(catalogCode);
               variables.put("SXXZ",sxxz);
               variables.put("ITEM_CODE", catalogCode+maxNumCode);
               variables.put("CATALOG_CODE", catalogCode);
               variables.put("NUM_CODE", maxNumCode);
               variables.put("SSBMBM", departcode);
               variables.put("FWSXZT", "1");
               variables.put("ITEM_NAME",rowValue.get(2).toString().trim());
               if(rowValue.get(7).toString().trim().equals("即办件")){
               variables.put("SXLX","1");
               }else{
                   variables.put("SXLX","2"); 
               }
               variables.put("RZBSDTFS","in02");
               variables.put("c_sn",1655+i);
               variables.put("CNQXGZR", rowValue.get(12).toString().trim());
               variables.put("LXR", rowValue.get(13).toString().trim());
               variables.put("LXDH", rowValue.get(14).toString().trim());
               String itemId = serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", "");
               Map<String,Object> busType7 = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                       new String[]{"PARENT_ID","TYPE_NAME"},new Object[]{
                       "402883494fd4f3aa014fd4fc68260003",rowValue.get(10).toString().trim()});
               if(busType7!=null){
                   String typeIds = "402883494fd4f3aa014fd4fc68260003,"+busType7.get("TYPE_ID");
                   busTypeService.saveBusTypeItem(typeIds.split(","), itemId);
               }*/
            log.info(i + rowValue.get(2).toString().trim());
        }
               
        
    }
    
    
    public String getsxxz(String sxxz){
        String s ="";
        if("行政许可".equals(sxxz)) {
            s="XK";
        }else if(sxxz.equals("公共服务")||sxxz.contains("公共")){
            s="GF";
        }else if(sxxz.equals("行政审批")||sxxz.contains("行政审批")){
            s="XP";
        }else if(sxxz.equals("其他")||sxxz.contains("其他")){
            s="QT";
        }else if(sxxz.equals("非行政许可")){
            s="FK";
        }
        return s;
    }
    public String getdepartId(String departId){
        String did ="";
        if(departId.equals("国税局")|| departId.contains("国税局")){
            did="40288b9f530d91f8015310dbea6c000d";
        }else if(departId.equals("地税局")||departId.contains("地税局")){
            did="40288b9f530d91f8015310d988ec000b";
        }else if(departId.equals("烟草局")||departId.contains("烟草局")){
            did="40288b9f530d91f8015310d94d440008";
        }
        
        return did;
    }
    public String getdepartcode(String departId){
        String did ="";
        if(departId.equals("国税局")||departId.contains("国税局")){
            did="10000003";
        }else if(departId.equals("地税局")||departId.contains("地税局")){
            did="10000002";
        }else if(departId.equals("烟草局")||departId.contains("烟草局")){
            did="10000001";
        }
        return did;
    }
    
    public String savaCatalog(String catalogname,String catalogcode,String departId,String sxx){
        String entityId = "";
        String code ="";
        Map<String,Object>  catalog= new HashMap<String,Object>();
        catalog = catalogService.getByJdbc("T_WSBS_SERVICEITEM_CATALOG",
                new String[]{"CATALOG_NAME","DEPART_SXXZ_CODE","DEPART_ID"},
                new Object[]{catalogname,catalogcode,departId});
        if(catalog!=null){
            code =(String) catalog.get("CATALOG_CODE");
        }else{
            Map<String, Object> variables = new HashMap<String,Object>();
            variables.put("CATALOG_NAME",catalogname);
            variables.put("SXXZ",sxx);
            variables.put("DEPART_ID",departId);
            variables.put("STATUS","1");
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            int maxSn = catalogService.getMaxSn();
            variables.put("C_SN", maxSn+1);
            variables.put("DEPART_SXXZ_CODE",catalogcode);
            String numcode = catalogService.getMaxNumCode(catalogcode);
            variables.put("NUM_CODE",numcode);
            variables.put("CATALOG_CODE",catalogcode+numcode);
            catalogService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_CATALOG", entityId);
            code = catalogcode+numcode;
        }
        return code;
    }
    
    public String savaItem(String code,String sx,String name,String ssyj, String dcode){
        String entityId = "";
        Map<String, Object> variables = new HashMap<String,Object>();
        Map<String,Object>  catalog = catalogService.getCatalogByCatalogCode(code);
        String  maxNumCode = serviceItemService.getMaxNumCode(code);
        variables.put("SXXZ",(String)catalog.get("SXXZ"));
        variables.put("ITEM_CODE", code+maxNumCode);
        variables.put("CATALOG_CODE", code);
        variables.put("NUM_CODE", maxNumCode);
        variables.put("SSBMBM", dcode);
        variables.put("FWSXZT", "-1");
        variables.put("ITEM_NAME",name);
        variables.put("XSYJ",ssyj);
        variables.put("SXLX","1");
        serviceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", entityId);
        return code+maxNumCode;
    }
    
    /**
     * 描述    excel批量导入数据字典
     * @author Allin Lin
     * @created 2020年2月19日 下午8:24:54
     */
    @Test
    public  void startImport(){
        String excelPath = "D:\\xzqy.xls";
        List<List<Object>> list = ExcelUtil.getExcelRowValues(excelPath, 1, 0);
        //DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        for (int i = 0; i < list.size(); i++) {
            List<Object> rowValue = list.get(i);
            System.out.println("导入第"+(i+1)+"数据:字典值为："+rowValue.get(0)+"、字典名称为："+rowValue.get(1)+"\n");
            Map<String, Object> variables = new HashMap<String,Object>();       
            variables.put("DIC_CODE", rowValue.get(0).toString().trim());//字典编码
            variables.put("DIC_NAME", rowValue.get(1).toString().trim());//字典名称
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss")); //入库时间 
            /*String typeId = "4028b79f705b4e6801705c419eff0013";
            int maxSn = dictionaryService.getMaxSn(typeId);*/
            variables.put("DIC_SN", i+1);//字典排序值   
            variables.put("TYPE_CODE","XZQY");//字典类别编码
            variables.put("TYPE_ID", "4028b79f705b4e6801705c419eff0013");
            dictionaryService.saveOrUpdate(variables, "T_MSJW_SYSTEM_DICTIONARY", null);          
        }       
    }
    
}
