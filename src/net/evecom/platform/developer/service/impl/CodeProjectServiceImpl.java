/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.developer.dao.CodeProjectDao;
import net.evecom.platform.developer.model.CodeTableInfo;
import net.evecom.platform.developer.service.CodeProjectService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("codeProjectService")
public class CodeProjectServiceImpl extends BaseServiceImpl implements CodeProjectService {
    /**
     * 所引入的dao
     */
    @Resource
    private CodeProjectDao dao;
    /**
     * 引入logService
     */
    @Resource
    private SysLogService sysLogService;
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
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM T_MSJW_DEVELOPER_PROJECT ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 新增或者修改代码建模项目
     * @author Flex Hu
     * @created 2014年9月16日 上午9:21:03
     * @param request
     */
    public void saveOrUpdateCodeProject(HttpServletRequest request){
        String entityId = request.getParameter("PROJECT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String projectId = dao.saveOrUpdate(variables, "T_MSJW_DEVELOPER_PROJECT", entityId);
        //获取是否生成框架代码
        String isGenCode = request.getParameter("ISGEN_CODE");
        if(isGenCode.equals("1")){
            Properties properties = FileUtil.readProperties("project.properties");
            //获取项目代码地址
            String projectPath = properties.getProperty("projectPath");
            //获取原作者
            String author = properties.getProperty("author");
            //生成服务器端简单框架代码
            variables.put("PROJECT_ID", projectId);
            this.genServerSimpleCrudCode(projectPath, author, variables);
        }else{
            
        }
    }
    /**
     * 
     * 描述 生成服务器端简单框架代码
     * @author Flex Hu
     * @created 2014年9月16日 上午9:37:16
     * @param projectPath
     * @param busData
     */
    private void genServerSimpleCrudCode(String projectPath,String author,Map<String,Object> busData){
        String projectId = (String) busData.get("PROJECT_ID");
        CodeTableInfo mainTable = this.getMainTableInfo(projectId);
        //包名称
        String packageName = (String) busData.get("PACKAGE_NAME");
        //主表名称
        String mainTableName = mainTable.getTableName();
        //主表对应类名称
        String className = mainTable.getClassName();
        //主表实体类中文名
        String mainEntityName = mainTable.getChineseName();
        //获取主键名称
        String primaryKeyName = (String) dao.getPrimaryKeyName(mainTableName).get(0);
        busData.put("packageName", packageName);
        busData.put("mainTableName", mainTableName);
        busData.put("className", className);
        busData.put("mainEntityName", mainEntityName);
        busData.put("author", author);
        busData.put("primaryKeyName", primaryKeyName);
        // 定义生成Dao字符串模板
        // 设置dao代码的生成路径
        String daoPath = projectPath + "src/net/evecom/platform/" + packageName
                + "/dao/";
        FreeMarkerUtil.writeFileToDisk(busData,"DaoFtl.ftl", daoPath+className+ "Dao.java");
        
         // 设置daoImp代码的生成路径
        String daoImplPath = projectPath + "src/net/evecom/platform/"
                + packageName + "/dao/impl/";
        FreeMarkerUtil.writeFileToDisk(busData,"DaoImplFtl.ftl", daoImplPath
                + className + "DaoImpl.java");
        
        // 设置service代码的生成路径
        String servicePath = projectPath + "src/net/evecom/platform/"
                + packageName + "/service/";
        FreeMarkerUtil.writeFileToDisk(busData,"ServiceFtl.ftl", servicePath
                + className + "Service.java");
        
        // 设置serviceImpl代码的生成路径
        String serviceImplPath = projectPath + "src/net/evecom/platform/"
                + packageName + "/service/impl/";
        FreeMarkerUtil.writeFileToDisk(busData,"ServiceImplFtl.ftl", serviceImplPath
                + className + "ServiceImpl.java");
        
        // 设置action代码的生成路径
        String controllerPath = projectPath + "src/net/evecom/platform/"
                + packageName + "/controller/";
        FreeMarkerUtil.writeFileToDisk(busData,"ControllerFtl.ftl", controllerPath
                + className + "Controller.java");
    }
    
    /**
     * 
     * 描述 获取所有的建模包
     * @author Flex Hu
     * @created 2014年9月17日 上午8:56:12
     * @return
     */
    public List<Map<String,Object>> findAllProject(){
        return dao.findAllProject();
    }
    
    /**
     * 
     * 描述 根据项目ID删除掉数据
     * @author Flex Hu
     * @created 2014年9月19日 上午10:42:26
     * @param projectId
     */
    public void removeByProjectId(String projectId) {
        dao.removeByProjectId(projectId);
    }
    /**
     * 
     * 描述 根据项目ID获取到配置的数据库信息
     * @author Flex Hu
     * @created 2014年9月20日 上午10:53:32
     * @param projectId
     * @return
     */
    public List findTableInfos(String projectId){
        Map<String,Object> pro = this.getByJdbc("T_MSJW_DEVELOPER_PROJECT",
                new String[]{"PROJECT_ID"},new Object[]{projectId});
        String tableInfo = (String) pro.get("TABLEINFO_JSON");
        return JSON.parseArray(tableInfo, Map.class);
    }
    /**
     * 
     * 描述 根据项目ID获取所配置的表格信息
     * @author Flex Hu
     * @created 2014年9月20日 下午8:51:12
     * @param projectId
     * @return
     */
    public List<CodeTableInfo> findByProject(String projectId){
        Map<String,Object> prj = this.getByJdbc("T_MSJW_DEVELOPER_PROJECT",
                new String[]{"PROJECT_ID"},new Object[]{projectId});
        String jsonStr = (String) prj.get("TABLEINFO_JSON");
        List<CodeTableInfo> tableInfos = JSON.parseArray(jsonStr, CodeTableInfo.class);
        for(CodeTableInfo tableInfo:tableInfos){
            String primaryKeyName = (String) this.getPrimaryKeyName(tableInfo.getTableName()).get(0);
            tableInfo.setPrimaryKeyName(primaryKeyName);
        }
        return tableInfos;
    }
    
    /**
     * 
     * 描述 获取配置的主表信息
     * @author Flex Hu
     * @created 2014年9月20日 下午8:57:27
     * @param projectId
     * @return
     */
    public CodeTableInfo getMainTableInfo(String projectId){
        List<CodeTableInfo> tableInfos = this.findByProject(projectId);
        CodeTableInfo targetTable = null;
        for(CodeTableInfo tableInfo:tableInfos){
            if(tableInfo.getIsMainTable().equals("1")){
                targetTable = tableInfo;
                break;
            }
        }
        return targetTable;
    }
}
