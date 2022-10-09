/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.ExcelQueryParams;
import net.evecom.platform.system.service.ExcelConfigService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  Excel导出配置Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/excelConfigController")
public class ExcelConfigController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExcelConfigController.class);
    /**
     * 引入Service
     */
    @Resource
    private ExcelConfigService excelConfigService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        excelConfigService.remove("T_MSJW_SYSTEM_EXCELCONFIG","CONFIG_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 Excel导出配置记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  excelConfig = excelConfigService.getByJdbc("T_MSJW_SYSTEM_EXCELCONFIG",
                    new String[]{"CONFIG_ID"},new Object[]{entityId});
            request.setAttribute("excelConfig", excelConfig);
        }
        return new ModelAndView("system/excelconfig/ExcelConfigInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONFIG_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = excelConfigService.saveOrUpdate(variables, "T_MSJW_SYSTEM_EXCELCONFIG", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 Excel导出配置记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 Excel导出配置记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "ExcelConfigView")
    public ModelAndView excelConfigView(HttpServletRequest request) {
        return new ModelAndView("system/excelconfig/ExcelConfigView");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = excelConfigService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "querys")
    public void querys(HttpServletRequest request,
            HttpServletResponse response) {
        String configId= request.getParameter("configId");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(configId)){
            list = this.excelConfigService.findQuerys(configId);
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到导出页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        Map<String,Object> expParams = BeanUtil.getMapFromRequest(request);
        List<ExcelQueryParams> queryParams = new ArrayList<ExcelQueryParams>();
        Iterator ite = expParams.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, Object> entry = (Entry<String, Object>) ite
                    .next();
            String key = entry.getKey();// map中的key
            Object value = entry.getValue();// 上面key对应的value
            ExcelQueryParams param = new ExcelQueryParams(key.toString(),value.toString());
            queryParams.add(param);
        }
        request.setAttribute("queryParams", queryParams);
        return new ModelAndView("system/excelconfig/ExcelExportSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "exp")
    @ResponseBody
    public void exp(HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String tplKey = request.getParameter("tplKey");
        String excelName= request.getParameter("excelName");
        Map<String,Object>  excelConfig = excelConfigService.getByJdbc("T_MSJW_SYSTEM_EXCELCONFIG",
                new String[]{"TPL_KEY"},new Object[]{tplKey});
        PagingBean pb = null;
        String exportType = request.getParameter("exportType");
  /*      if(exportType.equals("1")){
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                pb = getInitPagingBean(request);
            }
        }*/
        excelConfigService.genExcel(excelConfig,
                request, pb,response,exportType);
        j.setMsg(excelName+".xls");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 10:46:00
     * @param
     * @return
     */
    @RequestMapping(params = "sqlExportConfigView")
    public ModelAndView sqlExportConfigView(HttpServletRequest request) {
        return new ModelAndView("system/excelconfig/sqlExportConfigView");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 11:01:00
     * @param 
     * @return 
     */
    @RequestMapping(params = "sqlExportConfigData")
    public void sqlExportConfigData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = excelConfigService.querySqlExportConfigData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 11:09:00
     * @param 
     * @return 
     */
    @RequestMapping(params = "sqlExportConfigInfo")
    public ModelAndView sqlExportConfigInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  excelConfig = excelConfigService.getByJdbc("T_MSJW_SYSTEM_SQLEXPORTCONFIG",
                    new String[]{"SQL_ID"},new Object[]{entityId});
            if (Objects.nonNull(excelConfig)) {
                String templateFileId = StringUtil.getValue(excelConfig, "EXCEL_FILE_ID");
                if (StringUtils.isNotEmpty(templateFileId)) {
                    Map<String, Object> fileMap = excelConfigService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                            new String[]{"FILE_ID"}, new Object[]{templateFileId});
                    if (fileMap != null) {
                        excelConfig.put("EXCEL_FILE_NAME", fileMap.get("FILE_NAME"));
                    }
                }
            }
            request.setAttribute("sqlExportConfig", excelConfig);
        }
        return new ModelAndView("system/excelconfig/sqlExportConfigInfo");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 11:32:00
     * @param
     * @return
     */
    @RequestMapping(params = "sqlExportConfigSaveOrUpdate")
    @ResponseBody
    public AjaxJson sqlExportConfigSaveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SQL_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = excelConfigService.saveOrUpdate(variables, "T_MSJW_SYSTEM_SQLEXPORTCONFIG", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 sql导出配置记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 sql导出配置记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 11:55:00
     * @param
     * @return
     */
    @RequestMapping(params = "sqlExportConfigMultiDel")
    @ResponseBody
    public AjaxJson sqlExportConfigMultiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        excelConfigService.remove("T_MSJW_SYSTEM_SQLEXPORTCONFIG","SQL_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 Excel导出配置记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 描述:
     *
     * @param
     * @return
     * @author Madison You
     * @created 2021/3/24 14:05:00
     */
    @RequestMapping(params = "exportSqlExportConfig")
    @ResponseBody
    public void exportSqlExportConfig(HttpServletRequest request, HttpServletResponse response) {
        String sqlId = request.getParameter("sqlId");
        Map<String, Object> sqlMap = excelConfigService.getByJdbc("T_MSJW_SYSTEM_SQLEXPORTCONFIG",
                new String[]{"SQL_ID"}, new Object[]{sqlId});
        List<Map<String, Object>> dataList = excelConfigService.exportSqlExportConfig(sqlMap);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/system/excelconfig/sqlExportEmpty.xls";
        if (Objects.nonNull(sqlMap)) {
            String excelFileId = StringUtil.getValue(sqlMap, "EXCEL_FILE_ID");
            if (StringUtils.isNotEmpty(excelFileId)) {
                Map<String, Object> fileMap = excelConfigService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"}, new Object[]{excelFileId});
                if (Objects.nonNull(fileMap)) {
                    Properties properties = FileUtil.readProperties("project.properties");
                    String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn");
                    String filePath = StringUtil.getValue(fileMap, "FILE_PATH");
                    tplPath = uploadFileUrlIn + filePath;
                }
            }
        }
        Set<String> excludeFields = new HashSet<>();
        int startRow = Integer.parseInt(StringUtil.nullORSpaceToDefault(StringUtil.getValue(
                sqlMap, "START_ROW"), "1"));
        int startCol = Integer.parseInt(StringUtil.nullORSpaceToDefault(StringUtil.getValue(
                sqlMap, "START_COL"), "1"));
        excludeFields.add("RN");
        excludeFields.add("CURRENTROW");
        List<ExcelReplaceDataVO> datas = new ArrayList<>();
        ExcelUtil.exportXls(tplPath, dataList, "统计.xls", excludeFields,
                response, startRow, startCol, datas, "", false, new int[]{0});
    }

}

