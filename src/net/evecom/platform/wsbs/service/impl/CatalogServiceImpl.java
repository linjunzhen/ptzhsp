/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.dao.CatalogDao;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 事项目录操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("catalogService")
public class CatalogServiceImpl extends BaseServiceImpl implements CatalogService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CatalogServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private CatalogDao dao;
    /**
     * 
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Faker Li
     * @created 2015年10月27日 下午2:25:13
     * @param sqlFilter
     * @return
     * @see net.evecom.platform.wsbs.service.CatalogService#findBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select SC.CATALOG_ID,SC.CATALOG_NAME,SC.CATALOG_CODE,SC.DEPART_ID,SC.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,D1.DEPART_NAME as CHILD_DEPART  ");
        sql.append("from T_WSBS_SERVICEITEM_CATALOG SC  ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON SC.DEPART_ID=D.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=SC.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=SC.CHILD_DEPART_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND SC.STATUS=1 ");
        //获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        //非超管进行数据级别权限控制
        if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            //获取当前用户被授权的部门代码
            String authDepCodes  = curUser.getAuthDepCodes();
            sql.append(" AND D1.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取表中最大的排序值
     * @author Faker Li
     * @created 2015年10月27日 下午3:04:48
     * @return
     * @see net.evecom.platform.wsbs.service.CatalogService#getMaxSn()
     */
    public int getMaxSn() {
        return dao.getMaxSn();
    }
    /**
     * 
     * 描述 根据部门编码+目录性质字典值获取最大次序
     * @author Faker Li
     * @created 2015年10月28日 上午9:38:27
     * @param departsxxzcode
     * @return
     * @see net.evecom.platform.wsbs.service.CatalogService#getMaxNumCode(java.lang.String)
     */
    public String getMaxNumCode(String departsxxzcode) {
        int num = dao.getMaxNumCode(departsxxzcode)+1;
        String numcode = StringUtils.leftPad(String.valueOf(num), 3, '0');
        return numcode;
    }
    /**
     * 
     * 描述  根据目录ID伪删除目录
     * @author Faker Li
     * @created 2015年10月28日 上午10:01:12
     * @param catalogIds
     * @see net.evecom.platform.wsbs.service.CatalogService#removeCascade(java.lang.String[])
     */
    public void removeCascade(String[] catalogIds) {
        for (String catalogId : catalogIds) {
            Map<String, Object> catalog  = new HashMap<String, Object>();
            catalog.put("STATUS",0);
            dao.saveOrUpdate(catalog, "T_WSBS_SERVICEITEM_CATALOG", catalogId);
        }
        
    }
    /**
     * 
     * 描述 更新排序字段
     * @author Faker Li
     * @created 2015年10月28日 上午11:06:33
     * @param catalogIds
     * @see net.evecom.platform.wsbs.service.CatalogService#updateSn(java.lang.String[])
     */
    public void updateSn(String[] catalogIds) {
        dao.updateSn(catalogIds);
    }
    /**
     * 
     * 描述 根据catalogCode获取catalogname
     * @author Faker Li
     * @created 2015年10月29日 上午8:58:47
     * @param catalogCode
     * @return
     * @see net.evecom.platform.wsbs.service.CatalogService#getCatalogName(java.lang.String)
     */
    public Map<String,Object> getCatalogByCatalogCode(String catalogCode) {
        return dao.getCatalogByCatalogCode(catalogCode);
    }
    
    /**
     * 
     * 描述 导入目录
     * @author Flex Hu
     * @created 2016年3月19日 下午4:15:22
     
    public void impCataLogs(){
        File foldFile = new File("D:/ptdatas/公安");
        Set<String> fileFormats = new HashSet<String>();
        fileFormats.add("xls");
        String bigDepName = "区公安局";
        Map<String,Object> bigDep = serviceItemService.
                getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",new String[]{"DEPART_NAME"},new Object[]{bigDepName});
        String departId = (String) bigDep.get("DEPART_ID");
        String departCode = (String) bigDep.get("DEPART_CODE");
        List<File> xlsList = FileUtil.getFormatFiles(fileFormats,foldFile,null);
        for(File xls:xlsList){
            log.info("这里的绝对路径:"+xls.getAbsolutePath());
            Object catalogName = ExcelUtil.getCellValue(xls.getAbsolutePath(), 0, 4, 3);
            if(catalogName!=null){
                String CATALOG_NAME = catalogName.toString().trim();
                if(StringUtils.isNotEmpty(CATALOG_NAME)){
                    Map<String,Object> oldCataLog = dao.getByJdbc("T_WSBS_SERVICEITEM_CATALOG",
                            new String[]{"CATALOG_NAME"},new Object[]{CATALOG_NAME});
                    if(oldCataLog==null){
                        log.info("为空;"+CATALOG_NAME);
                        int maxSn = dao.getMaxSn();
                        String departsxxzcode = departCode+"XK";
                        Map<String,Object> cataLog = new HashMap<String,Object>();
                        cataLog.put("CATALOG_NAME",CATALOG_NAME);
                        cataLog.put("C_SN",maxSn+1);
                        cataLog.put("SXXZ", "XK");
                        cataLog.put("DEPART_ID",departId);
                        cataLog.put("DEPART_SXXZ_CODE",departsxxzcode);
                        String numcode = this.getMaxNumCode(departsxxzcode);
                        cataLog.put("NUM_CODE", numcode);
                        cataLog.put("CATALOG_CODE", departsxxzcode + numcode);
                        this.saveOrUpdate(cataLog, "T_WSBS_SERVICEITEM_CATALOG","");
                    }else{
                        log.info("不为空;"+CATALOG_NAME);
                    }
                }
            }
        }
    }
    */
    /**
     * 
     * 描述 导入事项
     * @author Flex Hu
     * @created 2016年3月19日 下午7:42:40
     
    public void impItems(){
        File foldFile = new File("D:/ptdatas");
        Set<String> fileFormats = new HashSet<String>();
        fileFormats.add("xls");
        List<File> xlsList = FileUtil.getFormatFiles(fileFormats,foldFile,null);
        for(File xls:xlsList){
            String xlsPath = xls.getAbsolutePath();
            log.info("路径:"+xlsPath);
            Map<String,Object> serviceItem = new HashMap<String,Object>();
            //获取目录名称
            String catalogName = (String) ExcelUtil.getCellValue(xlsPath, 0, 4,3);
            catalogName = catalogName.trim();
            //获取事项名称
            String itemName = (String) ExcelUtil.getCellValue(xlsPath, 0, 5,3);
            if(StringUtils.isEmpty(itemName)||"无".equals(itemName)){
                itemName = catalogName;
            }
            //获取目录信息
            Map<String,Object> catalog = this.getByJdbc("T_WSBS_SERVICEITEM_CATALOG",
                    new String[]{"CATALOG_NAME"},new Object[]{catalogName});
            //获取目录编码
            String catalogCode = (String) catalog.get("CATALOG_CODE");
            //获取事项信息
            Map<String,Object> oldItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_NAME","CATALOG_CODE"},new Object[]{itemName,catalogCode});
            if(oldItem==null){
                log.info("为空:"+itemName);
                //获取所属部门ID
                String DEPART_ID = (String) catalog.get("DEPART_ID");
                Map<String,Object> dep = this.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[]{"DEPART_ID"},new Object[]{DEPART_ID});
                String maxNumcode = serviceItemService.getMaxNumCode(catalogCode);
                serviceItem.put("ITEM_NAME", itemName);
                StringBuffer itemCode = new StringBuffer(catalogCode).append(maxNumcode);
                serviceItem.put("ITEM_CODE", itemCode.toString());
                serviceItem.put("SXXZ", catalog.get("SXXZ"));
                serviceItem.put("SXLX", "2");
                //获取备注
                String BZSM = (String) ExcelUtil.getCellValue(xlsPath, 0, 50,3);
                serviceItem.put("BZSM", BZSM);
                //获取行使依据
                String XSYJ = (String) ExcelUtil.getCellValue(xlsPath, 0,13,3);
                serviceItem.put("XSYJ", XSYJ);
                //获取申请条件
                String SQTJ = (String) ExcelUtil.getCellValue(xlsPath, 0,14,3);
                serviceItem.put("SQTJ", SQTJ);
                //获取办理流程
                String BLLC= (String) ExcelUtil.getCellValue(xlsPath, 0,44,3);
                serviceItem.put("BLLC", BLLC);
                serviceItem.put("RZBSDTFS", "in05");
                //获取承诺工作日
                String CNQXGZR = (String) ExcelUtil.getCellValue(xlsPath, 0,39,3);
                serviceItem.put("CNQXGZR",CNQXGZR);
                //获取承诺时限说明
                String CNQXSM = (String) ExcelUtil.getCellValue(xlsPath, 0,40,3);
                serviceItem.put("CNQXSM",CNQXSM);
                //获取法定时限
                String FDQX= (String) ExcelUtil.getCellValue(xlsPath, 0,38,3);
                serviceItem.put("FDQX",FDQX);
                //获取办理地点
                String BLDD = (String) ExcelUtil.getCellValue(xlsPath, 0,35,3);
                serviceItem.put("BLDD",BLDD);
                //获取办理部门
                String BLBM = (String) ExcelUtil.getCellValue(xlsPath, 0,23,3);
                serviceItem.put("BLBM",BLBM);
                //获取办公时间
                String BGSJ = (String) ExcelUtil.getCellValue(xlsPath, 0,37,3);
                serviceItem.put("BGSJ",BGSJ);
                //获取实施主题名称
                String SSZTMC = (String) ExcelUtil.getCellValue(xlsPath, 0,22,3);
                serviceItem.put("SSZTMC",SSZTMC);
                //获取联系电话
                String LXDH=  (String) ExcelUtil.getCellValue(xlsPath, 0,25,3);
                serviceItem.put("LXDH",LXDH);
                //获取监督电话
                String JDDH = (String) ExcelUtil.getCellValue(xlsPath, 0,48,3);
                serviceItem.put("JDDH",JDDH);
                serviceItem.put("SFSF", "0");
                //获取收费标准及依据
                String SFBZJYJ = (String) ExcelUtil.getCellValue(xlsPath, 0,33,3);
                serviceItem.put("SFBZJYJ", SFBZJYJ);
                int maxSn = serviceItemService.getMaxSn();
                serviceItem.put("SSBMBM", dep.get("DEPART_CODE"));
                serviceItem.put("BDLCDYID","4028b796513c2e1401513d6826290087");
                serviceItem.put("CATALOG_CODE", catalogCode);
                serviceItem.put("NUM_CODE", maxNumcode);
                //获取交通指引
                String TRAFFIC_GUIDE = (String) ExcelUtil.getCellValue(xlsPath, 0,36,3);
                serviceItem.put("TRAFFIC_GUIDE", TRAFFIC_GUIDE);
                serviceItem.put("FINISH_TYPE", "01");
                serviceItem.put("FINISH_GETTYPE", "01");
                //获取办理结果说明
                String FINISH_INFO = (String) ExcelUtil.getCellValue(xlsPath, 0,47,3);
                serviceItem.put("FINISH_INFO",FINISH_INFO);
                serviceItem.put("C_SN", maxSn+1);
                serviceItem.put("FWSXMXLB", "1");
                serviceItem.put("CATALOG_ID", catalog.get("CATALOG_ID"));
                serviceItem.put("ZBCS", BLBM);
                serviceItem.put("ITEMSTAR", "3");
                serviceItemService.saveOrUpdate(serviceItem, "T_WSBS_SERVICEITEM","");
            }else{
                log.info("不为空:"+itemName);
            }
        }
        
    }
    */
    /**
     * 
     * 描述 导入材料
     * @author Flex Hu
     * @created 2016年3月19日 下午8:18:40
     
    public void impMaters(){
        File foldFile = new File("D:/ptdatas");
        Set<String> fileFormats = new HashSet<String>();
        fileFormats.add("xls");
        List<File> xlsList = FileUtil.getFormatFiles(fileFormats,foldFile,null);
        for(File xls:xlsList){
            String xlsPath = xls.getAbsolutePath();
            log.info("路径:"+xlsPath);
            //获取目录名称
            String catalogName = (String) ExcelUtil.getCellValue(xlsPath, 0, 4,3);
            catalogName = catalogName.trim();
            //获取事项名称
            String itemName = (String) ExcelUtil.getCellValue(xlsPath, 0, 5,3);
            if(StringUtils.isEmpty(itemName)||"无".equals(itemName)){
                itemName = catalogName;
            }
            //获取目录信息
            Map<String,Object> catalog = this.getByJdbc("T_WSBS_SERVICEITEM_CATALOG",
                    new String[]{"CATALOG_NAME"},new Object[]{catalogName});
           //获取目录编码
            String catalogCode = (String) catalog.get("CATALOG_CODE");
            //获取事项信息
            Map<String,Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_NAME","CATALOG_CODE"},new Object[]{itemName,catalogCode});
            if(serviceItem!=null){
                String itemId = (String) serviceItem.get("ITEM_ID");
                String itemCode = (String) serviceItem.get("ITEM_CODE");
                Object count = dao.getObjectBySql("SELECT COUNT(*) FROM T_WSBS_SERVICEITEM_MATER WHERE ITEM_ID=? ",
                        new Object[]{itemId});
                if(Integer.parseInt(count.toString())==0){
                    log.info("为空..");
                    List<List<Object>> list = ExcelUtil.getExcelRowValues(xlsPath, 3, 3,1);
                    StringBuffer delSql = new StringBuffer("DELETE FROM T_WSBS_APPLYMATER A WHERE A.MATER_CODE");
                    delSql.append(" LIKE '").append(itemCode+"_mater_").append("%' ");
                    dao.executeSql(delSql.toString(), null);
                    StringBuffer insertSql = new StringBuffer("Insert into T_WSBS_SERVICEITEM_MATER(ITEM_ID,MATER_ID");
                    insertSql.append(",MATER_SN) values (?,?,?)");
                    for(int i =0;i<list.size();i++){
                        List<Object> datas = list.get(i);
                        String materName = (String) datas.get(0);
                        String materDesc = (String) datas.get(1);
                        if(StringUtils.isNotEmpty(materName)){
                            String materCode = itemCode+"_mater_"+i;
                            Map<String,Object> mater = new HashMap<String,Object>();
                            mater.put("MATER_CODE", materCode);
                            mater.put("MATER_NAME", materName);
                            mater.put("MATER_TYPE", "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.rar;");
                            mater.put("MATER_SIZE","5");
                            mater.put("MATER_DESC", materDesc);
                            mater.put("MATER_CLSMLX","复印件");
                            String materId = serviceItemService.saveOrUpdate(mater,"T_WSBS_APPLYMATER",null);
                            dao.executeSql(insertSql.toString(), new Object[]{itemId,materId,i});
                        }
                    }
                }else{
                    log.info("不为空..");
                }
                
            }
            
        }
    }
    */
}
