/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.system.dao.ExcelConfigDao;
import net.evecom.platform.system.service.ExcelConfigService;
import net.evecom.platform.system.service.FileAttachService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 Excel导出配置操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("excelConfigService")
public class ExcelConfigServiceImpl extends BaseServiceImpl implements ExcelConfigService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ExcelConfigServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ExcelConfigDao dao;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.Config_Id,T.TPL_NAME,"
                + "T.TPL_KEY,T.START_ROW,T.START_COL FROM T_MSJW_SYSTEM_EXCELCONFIG T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 获取配置的查询条件
     * @author Flex Hu
     * @created 2014年10月4日 下午7:57:36
     * @param configId
     * @return
     */
    public List findQuerys(String configId){
        Map<String,Object> pro = this.getByJdbc("T_MSJW_SYSTEM_EXCELCONFIG",
                new String[]{"CONFIG_ID"},new Object[]{configId});
        String tableInfo = (String) pro.get("SEARCH_CONFIG");
        return JSON.parseArray(tableInfo, Map.class);
    }
    
    /**
     * 生成EXCEL文件,返回路径
     * 
     * @author Flex Hu
     * @param excelConfig
     * @return
     */
    public String genExcel(Map<String,Object> excelConfig, HttpServletRequest request,
            PagingBean pb,HttpServletResponse response){
        List dataList = null;
        String excelName = request.getParameter("excelName");
        String sqlContent = (String) excelConfig.get("SQL_CONTENT");
        String orderCondition = (String) excelConfig.get("ORDER_CONDITION");
        // 获取SQL语句
        StringBuffer sql = new StringBuffer(sqlContent);
        if (sql.indexOf("where") == -1 && sql.indexOf("WHERE") == -1) {
            sql.append(" where 1=1 ");
        }
        // 获取配置的查询条件
        List queryParams = new ArrayList();
        List<Map<String,Object>> searchs = this.findQuerys((String)excelConfig.get("CONFIG_ID"));
        if (searchs != null && searchs.size() > 0) {
            for (Map<String,Object> search : searchs) {
                String paramName = (String) search.get("paramName");
                if (StringUtils.isNotEmpty(request.getParameter(paramName))) {
                    String condition = (String) search.get("queryCondition");
                    String value = request.getParameter(paramName);
                    sql.append(" and ").append(paramName);
                    if (condition.equals("LIKE")) {
                        sql.append(" LIKE '%").append(value).append("%' ");
                    } else {
                        sql.append(condition).append("? ");
                        queryParams.add(value);
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(orderCondition)) {
            sql.append(" ").append(orderCondition);
        }
        dataList = dao.findBySql(sql.toString(), queryParams.toArray(),
                null, pb);
        String path = this.getExcelPath(dataList, excelConfig, excelName,response);
        return path;
    }

    /**
     * 
     * 描述 生成EXCEL数据
     * @author Flex Hu
     * @created 2014年10月4日 下午10:44:18
     * @param dataList
     * @param excelConfig
     * @param excelName
     * @return
     */
    private String getExcelPath(List dataList, Map<String,Object> excelConfig,
            String excelName,HttpServletResponse response) {
        int startRow = Integer.parseInt(excelConfig.get("START_ROW").toString());
        int startCol = Integer.parseInt(excelConfig.get("START_COL").toString());
        String fileId = (String) excelConfig.get("TPL_PATH");
        Map<String,Object> fileAttach = fileAttachService.
                getByJdbc("T_MSJW_SYSTEM_FILEATTACH",new String[]{"FILE_ID"},new Object[]{fileId});
        Properties properties = FileUtil.readProperties("project.properties");
        String filePath = properties.getProperty("uploadFileUrlIn");
        // 获取模版路径
        String tplPath = filePath + fileAttach.get("FILE_PATH");
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        try {
            
            fs = new POIFSFileSystem(HttpRequestUtil.getStreamDownloadOutFile(tplPath));
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error("",e1);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            log.error("",e1);
        }
        // 创建sheet页
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);
        // 内容样式
        HSSFCellStyle contentStyle = hssfworkbook.createCellStyle();
        for (int i = 0; i < dataList.size(); i++) {
            Object[] results = (Object[]) dataList.get(i);
            // 取得第一行
            HSSFRow hssfrow = hssfsheet.createRow(startRow - 1 + i);
            hssfrow.setHeight((short) 1000);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 靠右
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            contentStyle.setWrapText(true);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            // contentStyle.set
            // 开始向右创建单元格
            for (int j = 0; j < results.length; j++) {
                String value = "";
                HSSFCell cell = hssfrow.createCell(startCol - 1 + j);
                cell.setCellStyle(contentStyle); // 设置数据单元格的样式
                if (results[j] != null) {
                    value = results[j].toString();
                }
                cell.setCellValue(new HSSFRichTextString(value));
            }
        }
        OutputStream ouputStream=null;
        try {
            String excelNameadd=excelName+".xls";
            String tx = new String(excelNameadd.getBytes("GBK"), "ISO8859-1");
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.ms-excel");
            // log.info("fielName:"+tx);
            resp.setHeader("Content-disposition", "attachment;filename=" + tx);
            ouputStream = resp.getOutputStream();
            hssfworkbook.write(ouputStream);
            return filePath + excelName + ".xls";
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if(ouputStream!=null){
               try {  
                   ouputStream.flush();
                   ouputStream.close();
               } catch (IOException e) {
                   log.error(e.getMessage());
               }
            }
        }
        return null;
    }
    /**
     * 
     * 描述：生成EXCEL文件,返回路径
     * @author Water Guo
     * @created 2017-3-1 上午10:22:31
     * @param excelConfig
     * @param request
     * @param pb
     * @param response
     * @param exportType
     * @return
     */
    @Override
    public String genExcel(Map<String, Object> excelConfig, HttpServletRequest request, PagingBean pb,
            HttpServletResponse response, String exportType) {
        List dataList = null;
        String excelName = request.getParameter("excelName");
        String sqlContent = (String) excelConfig.get("SQL_CONTENT");
        String orderCondition = (String) excelConfig.get("ORDER_CONDITION");
        // 获取SQL语句
        StringBuffer sql = new StringBuffer(sqlContent);
        if (sql.indexOf("where") == -1 && sql.indexOf("WHERE") == -1) {
            sql.append(" where 1=1 ");
        }
        // 获取配置的查询条件
        List queryParams = new ArrayList();
        List<Map<String,Object>> searchs = this.findQuerys((String)excelConfig.get("CONFIG_ID"));
        if (searchs != null && searchs.size() > 0) {
            for (Map<String,Object> search : searchs) {
                String paramName = (String) search.get("paramName");
                if (StringUtils.isNotEmpty(request.getParameter(paramName))) {
                    String condition = (String) search.get("queryCondition");
                    String value = request.getParameter(paramName);
                    //以省网办编码为主
                    if("T.ITEM_CODE".equals(paramName)){
                        value=value.trim();
                        sql.append(" and ");
                        sql.append(" (T.ITEM_CODE LIKE '%").append(value).append("%' ");
                        sql.append(" or T.swb_item_code like '").append("%");
                        sql.append(value).append("%') ");
                        continue;
                    }
                    sql.append(" and ").append(paramName);
                    if (condition.equals("LIKE")) {
                        sql.append(" LIKE '%").append(value).append("%' ");
                    } else {
                        sql.append(condition).append("? ");
                        queryParams.add(value);
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(orderCondition)) {
            sql.append(" ").append(orderCondition);
        }
        if("0".equals(exportType)){
            sql=new StringBuffer(sqlContent);
            queryParams = new ArrayList();
        }
        dataList = dao.findBySql(sql.toString(), queryParams.toArray(),
                null, pb);
        String path = this.getExcelPath(dataList, excelConfig, excelName,response);
        return path;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 10:53:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> querySqlExportConfigData(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" SELECT T.* FROM T_MSJW_SYSTEM_SQLEXPORTCONFIG T WHERE 1 = 1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 14:07:00
     * @param 
     * @return 
     */
    @Override
    public List<Map<String, Object>> exportSqlExportConfig(Map<String, Object> sqlMap) {
        List<Map<String, Object>> list = null;
        if (Objects.nonNull(sqlMap)) {
            String sqlContent = StringUtil.getValue(sqlMap, "SQL_CONTENT");
            String sqlParams = StringUtil.getValue(sqlMap, "SQL_PARAMS");
            Object[] sqlParamsArr = null;
            if (StringUtils.isNotEmpty(sqlParams)) {
                sqlParamsArr = sqlParams.split(",");
            }
            if (StringUtils.isNotEmpty(sqlContent)) {
                list = dao.findBySql(sqlContent, sqlParamsArr, null);
            }
        }
        return list;
    }
}
