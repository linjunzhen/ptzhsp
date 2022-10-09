/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.controller.DepartmentController;
import net.evecom.platform.system.dao.DepartmentDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 部门操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DepartmentServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private DepartmentDao dao;
    /**
     * 
     */
    @Resource
    private SysUserService sysUserService;
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
     * 
     * 描述 根据部门ID删除掉部门,级联更新用户的部门字段
     * @author Flex Hu
     * @created 2014年10月4日 下午1:57:02
     * @param depId
     */
    public void removeDepart(String depId){
        dao.removeDepart(depId);
    }
    /**
     * 
     * 描述 获取根部门
     * @author Flex Hu
     * @created 2014年10月20日 下午2:32:54
     * @return
     */
    public Map<String,Object> getRootDepart(){
        return dao.getRootDepart();
    }
    
    /**
     * 
     * 描述 根据部门编码获取数据
     * @author Flex Hu
     * @created 2014年10月30日 下午5:18:13
     * @param depCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> getByDepCode(String depCode){
        try{
            Map<String,Object> map = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[]{"DEPART_CODE"},new Object[]{depCode});
            return map;
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 
     * 描述 将sqlserver增加或者修改的部门数据转移到oracle
     * @author Flex Hu
     * @created 2014年10月31日 下午2:56:30
     * @param saveOrUpdateDatas
     */
    private void saveOrUpdateSqlServerDepDatas(List<Map<String,Object>> saveOrUpdateDatas){
        for(Map<String,Object> sqlServerData:saveOrUpdateDatas){
            String currentDepCode = (String)sqlServerData.get("YYBDM");
            String parentDepCode = (String)sqlServerData.get("LDEPAT");
            String currentDepName = (String)sqlServerData.get("YYBMC");
            Map<String,Object> currentDep = this.getByDepCode(currentDepCode);
            if(currentDep==null){
                currentDep = new HashMap<String,Object>();
                currentDep.put("STATUS",DepartmentService.NORMAL_STATUS);
                currentDep.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                currentDep.put("DEPART_CODE",currentDepCode);
            }
            currentDep.put("DEPART_NAME",currentDepName);
            String parentId = "";
            if(parentDepCode.equals("-1")){
                parentId = "0";
            }else{
                Map<String,Object> parentDep = this.getByDepCode(parentDepCode);
                parentId = (String) parentDep.get("DEPART_ID");
            }
            currentDep.put("PARENT_ID", parentId);
            log.info("开始同步部门编码为["+currentDepCode+"]的数据");
            this.saveOrUpdateTreeData(parentId, currentDep,"T_MSJW_SYSTEM_DEPARTMENT",null);
        }
    }
    /**
     * 
     * 描述 将sqlserver删除的部门数据迁移到oracle
     * @author Flex Hu
     * @created 2014年10月31日 下午2:58:06
     * @param delDatas
     */
    private void deleteSqlServerDepDatas(List<Map<String,Object>> delDatas){
        for(Map<String,Object> sqlServerData:delDatas){
            String delDepCode = (String) sqlServerData.get("TABLE_UNIQUEVALUES");
            Map<String,Object> oldDep = this.getByDepCode(delDepCode);
            if(oldDep!=null){
                String depId = (String) oldDep.get("DEPART_ID");
                this.removeDepart(depId);
            }
        }
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * @author Water Guo
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select "
                + "* from T_LCJC_SYSTEM_DEVELOPER T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据父亲ID获取部门数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId){
        String sql = "select * from T_MSJW_SYSTEM_DEPARTMENT WHERE";
        sql+=" PARENT_ID=? and STATUS='1' ORDER BY TREE_SN ASC,CREATE_TIME ASC";
        return dao.findBySql(sql, new Object[]{parentId}, null);
    }
    /**
     * 
     * 描述 根据父亲ID获取部门数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findDepartList(String parentId){
        String sql = "select DEPART_ID UNIT_ID,DEPART_CODE UNIT_CODE,DEPART_NAME UNIT_NAME" +
               " from T_MSJW_SYSTEM_DEPARTMENT WHERE STATUS='1' ";
        sql+="  ORDER BY TREE_SN ASC,CREATE_TIME ASC";
        return dao.findBySql(sql,null, null);
    }
    
    /**
     * 
     * 描述 新增或者修改用户,级联更新用户的区划
     * @author Flex Hu
     * @created 2015年9月14日 上午9:56:11
     * @param parentId
     * @param department
     * @return
     */
    @SuppressWarnings("unchecked")
    public String saveOrUpdateCascadeUser(String parentId,Map<String,Object> department){
        String recordId = this.saveOrUpdateTreeData(parentId, department,"T_MSJW_SYSTEM_DEPARTMENT",null);
        return recordId;
    }

    @Override
    public String saveDeveloper(String parentId, Map<String, Object> department) {
        String recordId = this.saveOrUpdateTreeData(parentId, department,"T_LCJC_SYSTEM_DEVELOPER",null);
        return recordId;
    }

    @Override
    public List<Map<String, Object>> findByParentId(String parentId,
            Integer unitType) {
        if(unitType != null){
            String sql = "select * from T_LCJC_SYSTEM_DEVELOPER WHERE";
            sql+=" PARENT_ID=? and UNIT_TYPE=? and STATUS=? ORDER BY TREE_SN ASC,CREATE_TIME ASC";
            return dao.findBySql(sql, new Object[]{parentId,unitType,1}, null);
        }else{
            String sql = "select * from T_LCJC_SYSTEM_DEVELOPER WHERE";
            sql+=" PARENT_ID=? and UNIT_TYPE<>? and STATUS=? ORDER BY TREE_SN ASC,CREATE_TIME ASC";
            return dao.findBySql(sql, new Object[]{parentId,3,1}, null);
        }
    }
    
    /**
     * 
     * 描述 获取一级处室
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String parentId){
        StringBuffer sql = new StringBuffer("select D.DEPART_NAME as text,D.DEPART_ID as value FROM").append(
                " T_MSJW_SYSTEM_DEPARTMENT D WHERE D.STATUS='1' D.PARENT_ID ="
                        + " (SELECT T.DEPART_ID FROM T_MSJW_SYSTEM_DEPARTMENT T").append(
                " WHERE T.PARENT_ID='0' AND T.STATUS='1') ORDER BY D.TREE_SN");
        return dao.findBySql(sql.toString(),null, null);
    }
    
    /**
     * 
     * 描述 根据用户帐号获取部门信息
     * @author Flex Hu
     * @created 2016年1月30日 下午5:18:17
     * @param username
     * @return
     */
    public Map<String,Object> getDepInfoByUserAccount(String userAccount){
        return dao.getDepInfoByUserAccount(userAccount);
    }
    
    /**
     * 
     * 描述 获取部门列表
     * @author Flex Hu
     * @created 2016年3月8日 下午1:39:30
     * @return
     */
    public List<Map<String,Object>> findDepList(){
        StringBuffer sql = new StringBuffer("SELECT D.DEPART_ID,D.DEPART_NAME,D.DEPART_CODE ");
        sql.append(" FROM T_MSJW_SYSTEM_DEPARTMENT D ");
        sql.append(" WHERE D.PARENT_ID!='0' AND D.STATUS='1' ORDER BY D.TREE_SN ASC,D.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), null, null);
    }
    /**
     * 
     * 描述    根据父亲ID获取部门数据(数据授权)
     * @author Danto Huang
     * @created 2016-9-28 上午8:51:06
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentIdAuth(String parentId){
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_DEPARTMENT T WHERE");
        sql.append(" T.PARENT_ID=? AND T.STATUS='1' ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND T.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        sql.append("ORDER BY T.TREE_SN ASC,T.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), new Object[]{parentId}, null);
    }
}
