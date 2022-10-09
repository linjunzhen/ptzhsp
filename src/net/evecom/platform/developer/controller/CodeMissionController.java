/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.model.TableColumn;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.Fileinfo;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.ZipCompressUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.developer.service.CodeMissionService;
import net.evecom.platform.developer.service.CodeProjectService;
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
 * 描述 代码任务Controller
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/codeMissionController")
public class CodeMissionController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CodeMissionController.class);
    /**
     * 引入Service
     */
    @Resource
    private CodeMissionService codeMissionService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private ControlConfigService controlConfigService;
    /**
     * codeProjectService
     */
    @Resource
    private CodeProjectService codeProjectService;

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("developer/codeMission/list");
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        codeMissionService.remove("T_MSJW_DEVELOPER_CODEMISSION", "MISSION_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 代码任务记录", SysLogService.OPERATE_TYPE_DEL);
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
        String projectId = request.getParameter("projectId");
        Map<String, Object> codeMission = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            codeMission = codeMissionService.getByJdbc("T_MSJW_DEVELOPER_CODEMISSION", new String[] { "MISSION_ID" },
                    new Object[] { entityId });
        } else {
            codeMission.put("PROJECT_ID", projectId);
        }
        request.setAttribute("codeMission", codeMission);
        return new ModelAndView("developer/codeMission/info");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("MISSION_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        codeMissionService.saveOrUpdate(variables, "T_MSJW_DEVELOPER_CODEMISSION", entityId);
        j.setMsg("保存成功");
        return j;
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
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("M.CREATE_TIME", "desc");
        String projectId = request.getParameter("Q_M.PROJECT_ID_=");
        if (StringUtils.isEmpty(projectId)) {
            filter.addFilter("Q_M.PROJECT_ID_=", "-1");
        }
        List<Map<String, Object>> list = codeMissionService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到代码设计界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "design")
    public ModelAndView design(HttpServletRequest request) {
        String missionId = request.getParameter("missionId");
        String genCode = controlConfigService.getGenCodeByMissionId(missionId);
        request.setAttribute("missionId", missionId);
        request.setAttribute("genCode", genCode);
        return new ModelAndView("developer/codeMission/design");
    }

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goConfig")
    public ModelAndView goConfig(HttpServletRequest request) {
        String missionId = request.getParameter("missionId");
        String viewName = request.getParameter("viewName");
        String configId = request.getParameter("configId");
        String parentId = request.getParameter("PARENT_ID");
        String controlName = request.getParameter("CONTROL_NAME");
        Map<String, Object> controlConfig = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(configId) && !configId.equals("undefined")) {
            controlConfig =controlConfigService.getByJdbc("T_MSJW_DEVELOPER_CONTROLCONFIG",new String[] { "CONFIG_ID" },
                    new Object[] { configId });
        }
        controlConfig.put("MISSION_ID", missionId);
        controlConfig.put("PARENT_ID", parentId);
        controlConfig.put("CONTROL_NAME", controlName);
        request.setAttribute("controlConfig", controlConfig);
        return new ModelAndView("developer/codeMission/" + viewName);
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "genCode")
    @ResponseBody
    public AjaxJson genCode(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String missionId = request.getParameter("missionId");
        codeMissionService.genCodeToDisk(missionId);
        j.setMsg("生成成功");
        return j;
    }

    /**
     * 预览代码
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "preview")
    public ModelAndView preview(HttpServletRequest request) {
        String missionId = request.getParameter("missionId");
        String myCode = codeMissionService.getCodeByMissionId(missionId);
        request.setAttribute("mycode", StringUtil.htmlEncode(myCode));
        return new ModelAndView("developer/codeMission/previewcode");
    }

    /**
     * 
     * 加载任务对应的实体类
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "entitys")
    public void entitys(HttpServletRequest request, HttpServletResponse response) {
        String missionId = request.getParameter("missionId");
        String projectId = codeMissionService.getProjectId(missionId);
        List<Map<String, Object>> tables = codeProjectService.findTableInfos(projectId);
        String json = JSON.toJSONString(tables);
        this.setJsonString(json, response);
    }

    /**
     * 项目路径页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectTree")
    public ModelAndView projectTree(HttpServletRequest request) {
        return new ModelAndView("developer/codeMission/projectTree");
    }

    /**
     * 项目路径页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectTreeJson")
    public void getProjectFoldTree(HttpServletRequest request, HttpServletResponse response) {
        // 设置JSP的生成路径
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取项目代码地址
        String projectPath = properties.getProperty("projectPath")+"WebRoot\\";
        String json = "";
        Fileinfo.fileList.clear();
        Map<String, Object> fileMap = null;
        fileMap = new HashMap<String, Object>();
        fileMap.put("name", "工程项目");
        fileMap.put("id", 1);
        fileMap.put("pId", 0);
        fileMap.put("path", projectPath);
        //fileMap.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        fileMap.put("isParent", false);
        Fileinfo.fileList.add(fileMap);
        Fileinfo.readfiles(projectPath);
        Fileinfo.fileList =Fileinfo.sortList(Fileinfo.fileList); ;
        json = JSON.toJSONString(Fileinfo.fileList);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 项目复制到新的文件夹
     * 
     * @return
     */
    @RequestMapping(params = "copyFile")
    public void copyFile(HttpServletRequest request, HttpServletResponse response) {
        Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        String pattern="00";
        java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
        String nowTime = year + "" + df.format(month) + "" + df.format(date);
        String filePath = request.getParameter("filePath");
        // 设置JSP的生成路径
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取项目代码地址
        String projectPath = properties.getProperty("projectPath")+"WebRoot\\";
        String disk = projectPath.substring(0, 2);
        String file = properties.getProperty("author");
        String[] path = filePath.split(",");
        for (int i = 0; i < path.length; i++) {
            String fileName = path[i];
            path[i] = projectPath + path[i];
            FileUtil.copyFolder(path[i], disk + "\\" + file + "\\" + nowTime + "\\" + fileName);
        }

        ZipCompressUtil.zip(new File(disk+"\\"+file+"\\"+nowTime),disk+"\\" + file + "\\" + nowTime + ".zip");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String json = "{'nowTime':'" + nowTime + "','info':'已经压缩到 " + disk + "/" + file + "/" + nowTime + ".zip'}";
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        out.print(json);
        out.flush();
        out.close();
    }
}
