/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.encryption.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年5月12日 上午9:44:59
 */
@Controller
@RequestMapping("/encryptionController")
public class EncryptionController extends BaseController {

    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月12日 上午10:08:13
     * @param request
     * @return
     */
    @RequestMapping(params="encryptionConfigView")
    public ModelAndView encryptionConfigView(HttpServletRequest request){        
        return new ModelAndView("encryption/encryptionConfigView");
    }
    
    /**
     * 
     * 描述 加载树
     * @author Danto Huang
     * @created 2020年5月12日 上午10:40:52
     * @param request
     * @param response
     */
    @RequestMapping("/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("id", "0");
        root.put("name","加密表");
        root.put("open", true);
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        //获取topType
        List<Map<String,Object>> toplist = encryptionService.findConfigTableList("0");
        for(Map<String,Object> top:toplist){
            top.put("id", top.get("CONFIG_ID"));
            top.put("name", top.get("CONFIG_NAME").toString().concat("(").concat(top.get("BUSTABLE_NAME").toString())
                    .concat(")"));
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
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
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  configInfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            configInfo = encryptionService.getByJdbc("ENCRYPTION_CONFIG_TABLE",
                    new String[]{"CONFIG_ID"},new Object[]{entityId});
            
        }
        configInfo.put("PARENT_ID", parentId);
        configInfo.put("PARENT_NAME", parentName);
        request.setAttribute("configInfo", configInfo);
        return new ModelAndView("encryption/encryptionConfigInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateTree")
    @ResponseBody
    public AjaxJson saveOrUpdateTree(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONFIG_ID");
        String parentId = request.getParameter("PARENT_ID");
        String tableName = request.getParameter("BUSTABLE_NAME");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));

            String countSql = "select count(*) totalcount from "+tableName;
            BigDecimal total = (BigDecimal) encryptionService.getObjectBySql(countSql, null);
            treeData.put("UNENCRYPTED_HISNUM", total);
        }
        
        String recordId = encryptionService.saveOrUpdateTreeData(parentId, treeData,"ENCRYPTION_CONFIG_TABLE",null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的加密表记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的加密表记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
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
        encryptionService.removeByTableId(selectColNames);
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的加密表记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    加密字段列表
     * @author Danto Huang
     * @created 2020年5月12日 下午2:47:38
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.CREATE_TIME","asc");
        List<Map<String, Object>> list = encryptionService.findConfigListBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月12日 下午3:17:07
     * @param request
     * @return
     */
    @RequestMapping(params="columnInfo")
    public ModelAndView columnInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String configId = request.getParameter("CONFIG_ID");
        String configName = request.getParameter("CONFIG_NAME");
        Map<String,Object> columnInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            columnInfo = encryptionService.getByJdbc("ENCRYPTION_CONFIG_COLUMN", new String[] { "COLUMN_ID" },
                    new Object[] { entityId });
        }else{
            columnInfo.put("CONFIG_ID", configId);
            columnInfo.put("CONFIG_NAME", configName);
        }
        request.setAttribute("columnInfo", columnInfo);
        return new ModelAndView("encryption/columnInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月12日 下午3:18:37
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateColumn")
    @ResponseBody
    public AjaxJson saveOrUpdateColumn(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("COLUMN_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = encryptionService.saveOrUpdate(variables, "ENCRYPTION_CONFIG_COLUMN", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的加密表字段记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的加密表字段记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelColumn")
    @ResponseBody
    public AjaxJson multiDelColumn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        encryptionService.remove("ENCRYPTION_CONFIG_COLUMN", "COLUMN_ID", selectColNames.split(","));;
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的加密表记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    跳转待加密列表
     * @author Danto Huang
     * @created 2020年5月19日 下午4:24:32
     * @param request
     * @return
     */
    @RequestMapping(params="toTableEncryption")
    public ModelAndView toTableEncryption(HttpServletRequest request){
        String configId = request.getParameter("configId");
        if(StringUtils.isNotEmpty(configId)){
            Map<String, Object> config = encryptionService.getByJdbc("ENCRYPTION_CONFIG_TABLE",
                    new String[] { "CONFIG_ID" }, new Object[] { configId });
            config.put("DEAD_DATE", config.get("CREATE_TIME").toString().substring(0, 10));
            request.setAttribute("config", config);            
            List list = encryptionService.getAllByJdbc("ENCRYPTION_CONFIG_COLUMN",
                    new String[] { "CONFIG_ID" }, new Object[] { configId });
            request.setAttribute("columnlist", list);
        }
        return new ModelAndView("encryption/encryptionTableInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月21日 上午9:49:09
     * @param request
     * @param response
     */
    @RequestMapping(params="needEncryptionData")
    public void needEncryptionData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = encryptionService.findNeedEncryptionDataByTable(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年5月13日 上午11:15:38
     * @param request
     * @return
     */
    @RequestMapping(params="tableEncryption")
    @ResponseBody
    public AjaxJson tableEncryption(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String configId = request.getParameter("configId");
        encryptionService.tableEncryption(selectColNames,configId);
        j.setMsg("操作成功");
        return j;
    }
}
