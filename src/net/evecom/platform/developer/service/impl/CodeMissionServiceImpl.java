/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableColumn;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.developer.dao.CodeMissionDao;
import net.evecom.platform.developer.model.CodeTableInfo;
import net.evecom.platform.developer.service.CodeMissionService;
import net.evecom.platform.developer.service.CodeProjectService;
import net.evecom.platform.developer.service.ControlConfigService;

import org.springframework.stereotype.Service;

/**
 * 描述 代码任务操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("codeMissionService")
public class CodeMissionServiceImpl extends BaseServiceImpl implements CodeMissionService {
    /**
     * 所引入的dao
     */
    @Resource
    private CodeMissionDao dao;
    /**
     * 引入controlConfigService
     */
    @Resource
    private ControlConfigService controlConfigService;
    /**
     * codeProjectService
     */
    @Resource
    private CodeProjectService codeProjectService;
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select M.Mission_Id,M.JSP_NAME,M.CREATE_TIME,M.IS_WINDOW,");
        sql.append("P.PROJECT_NAME from T_MSJW_DEVELOPER_CODEMISSION M LEFT JOIN T_MSJW_DEVELOPER_PROJECT P ON ")
                .append(" M.PROJECT_ID=P.PROJECT_ID  ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据任务ID获取项目ID
     * @author Flex Hu
     * @created 2014年9月21日 上午8:20:50
     * @param missionId
     * @return
     */
    public String getProjectId(String missionId){
        return dao.getProjectId(missionId);
    }
    
    /**
     * 
     * 描述 根据任务获取代码
     * @author Flex Hu
     * @created 2014年10月3日 上午8:05:05
     * @param missionId
     * @return
     */
    public String getCodeByMissionId(String missionId){
        Map<String,Object> mission = dao.getByJdbc("T_MSJW_DEVELOPER_CODEMISSION",
                new String[]{"MISSION_ID"},new Object[]{missionId});
        StringBuffer jspCode = new StringBuffer("<%@ page language=\"java\" "
                + "import=\"java.util.*\" pageEncoding=\"UTF-8\"%>\n");
        String isWindow = mission.get("IS_WINDOW").toString();
        if(isWindow.equals("1")){
            jspCode.append("<%@include file=\"/webpage/common/baseinclude.jsp\"%>\n");
        }
        String genCode = controlConfigService.getGenCodeByMissionId(missionId);
        jspCode.append(genCode);
        return jspCode.toString();
    }
    /**
     * 
     * 描述 生成任务代码到磁盘
     * @author Flex Hu
     * @created 2014年9月21日 上午10:08:02
     * @param missionId
     */
    public void genCodeToDisk(String missionId){
        Map<String,Object> mission = dao.getByJdbc("T_MSJW_DEVELOPER_CODEMISSION",
                new String[]{"MISSION_ID"},new Object[]{missionId});
        String projectId = (String) mission.get("PROJECT_ID");
        Map<String,Object> project = codeProjectService.getByJdbc("T_MSJW_DEVELOPER_PROJECT",
                new String[]{"PROJECT_ID"}, new Object[]{projectId});
        //Map<String,Object> ftlRoot = new HashMap<String,Object>();
        String jspName = (String) mission.get("JSP_NAME");
        String packageName = (String) project.get("PACKAGE_NAME");
        CodeTableInfo mainTableInfo = codeProjectService.getMainTableInfo(projectId);
        // 设置JSP的生成路径
        Properties properties = FileUtil.readProperties("project.properties");
        //获取项目代码地址
        String projectPath = properties.getProperty("projectPath");
        //开始生成JSP代码
        String jspFolderPath = projectPath + "WebRoot/webpage/"
                + packageName + "/"+mainTableInfo.getClassName().toLowerCase()+"/";
        StringBuffer jspCode = new StringBuffer("<%@ page language=\"java\" "
                + "import=\"java.util.*\" pageEncoding=\"UTF-8\"%>\n");
        String isWindow = mission.get("IS_WINDOW").toString();
        if(isWindow.equals("1")){
            jspCode.append("<%@include file=\"/webpage/common/baseinclude.jsp\"%>\n");
        }
        String genCode = controlConfigService.getGenCodeByMissionId(missionId);
        jspCode.append(genCode);
        FileUtil.writeTextToDisk(jspFolderPath+jspName+".jsp",jspCode.toString());
        //结束生成JSP代码
        
        //开始往JAVA中插入跳转视图代码
        //生成表格操作相关JAVA代码
        this.genDataGridJavaCodeToDisk(missionId, jspName, packageName, projectPath);
        //结束往JAVA中插入跳转视图代码
       
    }
    /**
     * 
     * 描述 生成dagagrid涉及的JAVA代码到磁盘
     * @author Flex Hu
     * @created 2014年9月24日 下午3:09:56
     * @param missionId
     * @param jspName
     * @param packageName
     * @param projectPath
     */
    public void genDataGridJavaCodeToDisk(String missionId,String jspName,String packageName,String projectPath){
        List<Map<String,Object>> dataGrids = controlConfigService.findControl(missionId,
                ControlConfigService.CONTROL_TYPE_DATAGRID);
        Map<String,Object> ftlRoot = new HashMap<String,Object>();
        for(Map<String,Object> datagrid:dataGrids){
            String bindClassName = (String) datagrid.get("BIND_ENTITYNAME");
            String queryDataGridSql = (String) datagrid.get("SQL_CONTENT");
            ftlRoot.put("JSPNAME", jspName);
            ftlRoot.put("packageName", packageName);
            ftlRoot.put("className", bindClassName);
            ftlRoot.put("queryDataGridSql", queryDataGridSql);
            //获取Controller模版文件的内容
            String forwordToJspFtl = FreeMarkerUtil.getFtlContent("ForwardToJsp.ftl");
            String forwordToJspResult = FreeMarkerUtil.getResultString(forwordToJspFtl, ftlRoot);
            //获取源controller的路径
            String controllerPath = projectPath + "src/net/evecom/platform/"
                    + packageName + "/controller/"+bindClassName+"Controller.java";
            File targetController = new File(controllerPath);
            String addMethodedJava = FileUtil.addMethodToJava(targetController,forwordToJspResult);
            FileUtil.writeTextToFile(targetController, addMethodedJava);
            //获取数据源方法模版内容
            String dataSourceContent = FreeMarkerUtil.getFtlContent("DataGridSource.ftl");
            String dataSourceResult = FreeMarkerUtil.getResultString(dataSourceContent, ftlRoot);
            String addDataSourcedJava = FileUtil.addMethodToJava(targetController,dataSourceResult);
            FileUtil.writeTextToFile(targetController, addDataSourcedJava);
            //获取数据service模版内容
             //获取源service的路径
            String servicePath = projectPath + "src/net/evecom/platform/"
                    + packageName + "/service/"+bindClassName+"Service.java";
            File targetService = new File(servicePath);
            String dataGridServiceContent = FreeMarkerUtil.getFtlContent("DataGridService.ftl");
            String dataGridServiceContentedJava = FileUtil.addMethodToJava(targetService,dataGridServiceContent);
            FileUtil.writeTextToFile(targetService, dataGridServiceContentedJava);
            //获取数据源serviceImpl模版内容
            //获取源serviceImpl的路径
            String serviceImplPath = projectPath + "src/net/evecom/platform/"
                    + packageName + "/service/impl/"+bindClassName+"ServiceImpl.java";
            File targetServiceImpl = new File(serviceImplPath);
            String dataGridServiceImplFtl = FreeMarkerUtil.getFtlContent("DataGridServiceImp.ftl");
            String dataGridServiceImplContentResult = FreeMarkerUtil.
                    getResultString(dataGridServiceImplFtl, ftlRoot);
            String dataGridServiceImplContentedJava = FileUtil
                    .addMethodToJava(targetServiceImpl,dataGridServiceImplContentResult);
            FileUtil.writeTextToFile(targetServiceImpl, dataGridServiceImplContentedJava);
        }
    }
    
    /**
     * 
     * 描述 获取数据库列值
     * @author Flex Hu
     * @created 2014年9月21日 下午8:06:53
     * @param missionId
     * @param isForQuery
     * @param parentId
     * @return
     */
    public List<TableColumn> findTableColumns(String missionId,String isForQuery,String parentId){
        List<TableColumn> columns = new ArrayList<TableColumn>();
        if(isForQuery.equals("1")){
            Map<String,Object> parent = dao.getByJdbc("T_MSJW_DEVELOPER_CONTROLCONFIG",
                    new String[]{"CONFIG_ID"},new Object[]{parentId});
            String sqlContent = (String) parent.get("SQL_CONTENT");
            columns = dao.findQueryResultColsBySql(sqlContent);
        }else{
            Map<String,Object> codeMission = dao.getByJdbc("T_MSJW_DEVELOPER_CODEMISSION",
                    new String[]{"MISSION_ID"},new Object[]{missionId});
            String projectId = (String) codeMission.get("PROJECT_ID");
            List<CodeTableInfo> tableInfos = codeProjectService.findByProject(projectId);
            for(CodeTableInfo tableInfo:tableInfos){
                List<TableColumn> cols = dao.findColumns(tableInfo.getTableName());
                columns.addAll(cols);
            }
        }
        return columns;
    }
    
}
