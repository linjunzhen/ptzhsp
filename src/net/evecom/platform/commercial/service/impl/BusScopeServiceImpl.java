/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import net.evecom.core.util.AjaxJsonCode;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.zzhy.util.ExcelLogs;
import net.evecom.platform.zzhy.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.commercial.dao.BusScopeDao;
import net.evecom.platform.commercial.service.BusScopeService;

/**
 * 描述  商事-经营范围管理Service
 * @author Allin Lin
 * @created 2020年11月19日 下午3:00:52
 */
@Service("busScopeService")
public class BusScopeServiceImpl extends BaseServiceImpl implements BusScopeService{
    
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BusScopeServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private BusScopeDao dao;
    /**
     * 门类id
     */
    private String mlId="";
    /**
     * 大类id
     */
    private String dlId="";
    /**
     * 中类id
     */
    private String zlId="";

    /**
     * 覆盖获取实体dao方法
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.ID,T.BUSSCOPE_CODE,T.BUSSCOPE_NAME,T.ITEM_TYPE, ");
        sql.append(" T.IS_USABLE,T.IS_MP ");
        sql.append(" FROM T_WSBS_BUSSCOPE T ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述    根据子行业IDS获取已选子行业列表数据
     * @author Allin Lin
     * @created 2020年11月21日 下午3:24:34
     * @param childIndustryIds
     * @return
     */
    public List<Map<String,Object>> findBychildIndustryIds(String childIndustryIds){
        StringBuffer sql = new StringBuffer("select T.INDUSTRY_ID,T.INDUSTRY_NAME,T.PARENT_ID");
        sql.append(" FROM T_COMMERCIAL_INDUSTRY T WHERE T.INDUSTRY_ID IN　");
        sql.append(StringUtil.getValueArray(childIndustryIds));
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }

    /**
     * 根据excel附件fileId导入行业分类
     * @param fileId
     * @return
     */
    public AjaxJsonCode importXlsDataByFileId(String fileId){
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        //获取文件输入流
        Map<String,Object> fileAttach=dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{fileId});
        String relPath=StringUtil.getString(fileAttach.get("FILE_PATH"));
        Properties properties = FileUtil.readProperties("project.properties");
        String urlattachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        String filePath = urlattachFileFolderPath + relPath;
        InputStream inputStream= null;
        try {
            inputStream = new FileInputStream(new File(filePath));
        } catch (Exception e) {
        }
        ExcelLogs logs =new ExcelLogs();
        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
        //先删除行业表所有信息
        StringBuffer sql=new StringBuffer();
        sql.append(" delete  from ");
        sql.append(" T_WSBS_INDUSTRY2020 ");
        dao.executeSql(sql.toString(),null);
        //excel导入
        for(Map m : importExcel){
            importXlsDataOfIndustry(m);
        }
        return ajaxJsonCode;
    }
    /**
     * 描述    导入行业类别数据
     * @created 2020年11月21日 下午3:24:34
     * @param industry
     * @return
     */
    public AjaxJsonCode importXlsDataOfIndustry(Map industry){
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        String ml=StringUtil.getString(industry.get("门类（即主行业）"));
        if(StringUtils.isNotEmpty(ml)){
            String[] mlArray=ml.split(" ");
            String industryName=StringUtil.getString(mlArray[1]);
            String industryCode=StringUtil.getString(mlArray[0]);
            mlId=saveIndustry(INIT_INDUID,industryName,industryCode);
        }

        String dl=StringUtil.getString(industry.get("大类"));
        //当门类的内容为空时，采用上一级门类作为父类id
        if(StringUtils.isNotEmpty(dl)){
            String[] dlArray=dl.split(" ");
            String industryName=StringUtil.getString(dlArray[1]);
            String industryCode=StringUtil.getString(dlArray[0]);
            dlId=saveIndustry(mlId,industryName,industryCode);
        }

        String zl=StringUtil.getString(industry.get("中类"));
        //当大类的内容为空时，采用上一级大类作为父类id
        if(StringUtils.isNotEmpty(zl)){
            String[] strArray=zl.split(" ");
            String industryName=StringUtil.getString(strArray[1]);
            String industryCode=StringUtil.getString(strArray[0]);
            zlId=saveIndustry(dlId,industryName,industryCode);
        }

        String xl=StringUtil.getString(industry.get("小类"));
        //当大类的内容为空时，采用上一级大类作为父类id
        if(StringUtils.isNotEmpty(xl)){
            String[] strArray=xl.split(" ");
            String industryName=StringUtil.getString(strArray[1]);
            String industryCode=StringUtil.getString(strArray[0]);
            saveIndustry(zlId,industryName,industryCode);
        }
        return ajaxJsonCode;
    }

    /**
     * 保存行业类别，并返回主键id
     * @param parentId
     * @param industryName
     * @param industryCode
     * @return
     */
    public String saveIndustry(String parentId,String industryName,String industryCode){
        Map<String,Object> inMap= Maps.newHashMap();
        String induStryId="";
        StringBuffer sql=new StringBuffer("select * from ");
        sql.append(" T_WSBS_INDUSTRY2020 i where i.PARENT_ID=? ");
        sql.append(" AND  I.INDU_CODE= ? ");
        Map<String,Object> industryTable=dao.getByJdbc(sql.toString(),new Object[]{parentId,industryCode});
        if(industryTable==null){
            inMap.put("PARENT_ID",parentId);
            inMap.put("INDU_NAME",industryName);
            inMap.put("INDU_CODE",industryCode);
            induStryId=dao.saveOrUpdate(inMap,"T_WSBS_INDUSTRY2020",industryCode);
            return  induStryId;
        }
        return StringUtil.getString(industryTable.get("INDU_ID"));
    }
    /**
     * 描述    根据父id获取行业内容
     * @author Allin Lin
     * @created 2020年11月21日 下午3:24:34
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findIndustryCategory(String parentId){
        StringBuffer sql = new StringBuffer("select * ");
        sql.append(" FROM T_WSBS_INDUSTRY2020 T WHERE T.PARENT_ID =?　");
        sql.append(" ORDER BY T.INDU_CODE ASC ");
        return dao.findBySql(sql.toString(),new Object[]{parentId},null);
    }
}
