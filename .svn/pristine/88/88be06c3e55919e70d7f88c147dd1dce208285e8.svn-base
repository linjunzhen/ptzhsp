/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.model.TableColumn;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.developer.service.CodeMissionService;
import net.evecom.platform.developer.service.ControlConfigService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  配置控件Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/controlConfigController")
public class ControlConfigController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ControlConfigController.class);
    /**
     * 引入Service
     */
    @Resource
    private ControlConfigService controlConfigService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * codeMissionService
     */
    @Resource
    private CodeMissionService codeMissionService;
    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("developer/controlConfig/list");
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
        controlConfigService.removeTreeDataCascadeChild("T_MSJW_DEVELOPER_CONTROLCONFIG", selectColNames);
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
            Map<String,Object>  controlConfig = controlConfigService.getByJdbc("T_MSJW_DEVELOPER_CONTROLCONFIG",
                    new String[]{"CONFIG_ID"},new Object[]{entityId});
            request.setAttribute("controlConfig", controlConfig);
        }
        return new ModelAndView("developer/controlConfig/info");
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
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String controlType = (String) variables.get("CONTROL_TYPE");
        if(controlType.equals(ControlConfigService.CONTROL_TYPE_LAYOUT)){
            controlConfigService.saveLayoutControl(variables);
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_DATAGRID)){
            controlConfigService.saveDataGridControl(variables);
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_TAB)){
            String tabValues = request.getParameter("TAB_VALUES");
            String[] tabs = tabValues.split(",");
            for(String tab:tabs){
                variables.put("CONTROL_NAME", tab);
                controlConfigService.saveOrUpdateTreeData(parentId, variables, "T_MSJW_DEVELOPER_CONTROLCONFIG",null);
            }
        }else{
            controlConfigService.saveOrUpdateTreeData(parentId, variables, "T_MSJW_DEVELOPER_CONTROLCONFIG",null);
        }
        return j;
    }
    
    /**
     * 控件配置界面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goControlConf")
    public ModelAndView goControlConf(HttpServletRequest request) {
        String missionId = request.getParameter("missionId");
        String parentId = request.getParameter("PARENT_ID");
        request.setAttribute("missionId", missionId);
        request.setAttribute("parentId", parentId);
        return new ModelAndView("developer/codeMission/controlConfigTab");
    }
    
    /**
     * 控件配置界面跳转
     * 
     * @return
     */
    @RequestMapping(params = "controlInfo")
    public ModelAndView controlInfo(HttpServletRequest request) {
        String missionId = request.getParameter("missionId");
        String parentId = request.getParameter("parentId");
        String controlType = request.getParameter("controlType");
        request.setAttribute("CONTROL_TYPE", controlType);
        request.setAttribute("MISSION_ID", missionId);
        request.setAttribute("PARENT_ID", parentId);
        //判断父亲组件是否是表格组件
        Map<String,Object> parent = this.controlConfigService.getByJdbc("T_MSJW_DEVELOPER_CONTROLCONFIG",
                new String[]{"CONFIG_ID"},new Object[]{parentId});
        String parentControlType = (String) parent.get("CONTROL_TYPE");
        if (parentControlType.equals(ControlConfigService.CONTROL_TYPE_DATAGRID)
                || parentControlType.equals(ControlConfigService.CONTROL_TYPE_TREELIST)) {
            request.setAttribute("IS_FORQUERY", "1");
        } else{
            request.setAttribute("IS_FORQUERY","0");
        }
        if(controlType.equals(ControlConfigService.CONTROL_TYPE_DATAGRID)){
            return new ModelAndView("developer/codeMission/datagridConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_TEXT)){
            return new ModelAndView("developer/codeMission/textConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_DATE)){
            return new ModelAndView("developer/codeMission/dateConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_NUMBER)){
            return new ModelAndView("developer/codeMission/numberConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_COMBOBOX)){
            return new ModelAndView("developer/codeMission/comboboxConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_FORMLAYOUT)){
            return new ModelAndView("developer/codeMission/formLayoutConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_HIDDEN)){
            return new ModelAndView("developer/codeMission/hiddenConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_TEXTAREA)){
            return new ModelAndView("developer/codeMission/textareaConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_RADIO)){
            return new ModelAndView("developer/codeMission/radioConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_CHECKBOX)){
            return new ModelAndView("developer/codeMission/checkboxConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_CMOBOTREE)){
            return new ModelAndView("developer/codeMission/comboTreeConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_UPLOAD)){
            return new ModelAndView("developer/codeMission/uploadConfig");
        }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_EWEB)){
            return new ModelAndView("developer/codeMission/ewebConfig");
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 加载数据源表字段
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "tableColumns")
    public void tableColumns(HttpServletRequest request,
            HttpServletResponse response) {
        String isForQuery = request.getParameter("isForQuery");
        String missionId = request.getParameter("missionId");
        String parentId = request.getParameter("parentId");
        List<TableColumn> columns = codeMissionService.findTableColumns(missionId, isForQuery, parentId);
        String json = JSON.toJSONString(columns);
        this.setJsonString(json, response);
    }
    
}

