/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.DepartmentDao;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysRoleService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月25日 下午2:43:38
 */
public class SysUserServiceTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SysUserServiceTestCase.class);
    /**
     * 
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 
     */
    @Resource
    private DepartmentDao departmentDao;
    /**
     * 
     */
    @Resource
    private SysRoleDao sysRoleDao;
    /**
     * 
     */
    @Resource
    private SysRoleService sysRoleService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年12月21日 下午5:13:17
     */
    @Test
    public void isExistsUser(){
        //String password = StringUtil.encryptSha256("123456");
        String password = "jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=";
        int fds = sysUserService.isExistsUser("youtianbiao-nyfzc", password);
        log.info(fds);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年11月25日 下午2:44:15
     */
    @Test
    public void getUserInfo(){
        Map<String,Object> userInfo = sysUserService.getUserInfo("zhangsan");
        log.info("部门:"+userInfo.get("DEPART_NAME").toString());
    }
    
    /**
     * 
     * 描述 初始化各部门管理人员角色
     * @author Flex Hu
     * @created 2015年12月16日 下午2:35:00
     */
    @Test
    public void initManagerRole(){
        //获取二级部门列表
        StringBuffer sql = new StringBuffer("SELECT T.DEPART_NAME,T.DEPART_CODE");
        sql.append(" from T_MSJW_SYSTEM_DEPARTMENT t where t.parent_id=?");
        List<Map<String,Object>> list = departmentDao.findBySql(sql.toString(), 
                new Object[]{"7D120C9034154F0E0000280000000037"},null);
        for(Map<String,Object> map:list){
            String departName = (String) map.get("DEPART_NAME");
            String departCode = (String) map.get("DEPART_CODE");
            Map<String,Object> role = new HashMap<String,Object>();
            role.put("ROLE_NAME", departName+"普通件分管领导");
            role.put("ROLE_CODE", departCode+"_PTJFGLD");
            sysRoleService.saveOrUpdate(role, "T_MSJW_SYSTEM_SYSROLE","");
        }
    }
    
    /**
     * 
     * 描述 初始化各部门管理员权限
     * @author Flex Hu
     * @created 2015年12月16日 下午3:25:08
     */
    @SuppressWarnings("unchecked")
    @Test
    public void initManagerRoleRights(){
        String tempBack = "_PTJFGLD";
        String tempRoleCode = "569262478"+tempBack;
        String tempRoleId = "2c93f48151aa04810151aa0481ce0001";
        StringBuffer sql = new StringBuffer("SELECT R.ROLE_ID,R.ROLE_CODE,R.ROLE_NAME");
        sql.append(" FROM T_MSJW_SYSTEM_SYSROLE R WHERE R.ROLE_CODE!=? ");
        sql.append(" AND R.Role_Code LIKE '%").append(tempBack).append("' ORDER BY R.CREATE_TIME DESC ");
        List<Map<String,Object>> roleList = sysRoleDao.findBySql(sql.toString(), 
                new Object[]{tempRoleCode}, null);
        StringBuffer sql1 = new StringBuffer("SELECT * FROM T_MSJW_SYSTEM_RES_ROLE RR");
        sql1.append(" WHERE RR.ROLE_ID=? ");
        List<Map<String,Object>> rrList = sysRoleDao.findBySql(sql1.toString(),
                new Object[]{tempRoleId}, null);
        for(Map<String,Object> role:roleList){
            //获取角色ID
            String roleId = (String) role.get("ROLE_ID");
            //获取角色编码
            String roleCode = (String) role.get("ROLE_CODE");
            log.info("roleCode:"+roleCode+",roleName:"+role.get("ROLE_NAME")+",roleId:"+roleId);
            //获取部门编码
            String depCode = roleCode.split(tempBack)[0];
            //初始化操作级别的权限
            for(Map<String,Object> rr:rrList){
                StringBuffer insertSql = new StringBuffer("INSERT INTO ");
                insertSql.append("T_MSJW_SYSTEM_RES_ROLE(RES_ID,ROLE_ID)");
                insertSql.append(" values(?,?)");
                sysRoleDao.executeSql(insertSql.toString(),new Object[]{rr.get("RES_ID"),roleId});
            }
            Map<String,Object> depart = departmentDao.
                    getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", 
                            new String[]{"DEPART_CODE"},new Object[]{depCode});
            String depId = (String) depart.get("DEPART_ID");
            StringBuffer insertSql = new StringBuffer("INSERT INTO ");
            insertSql.append("T_MSJW_SYSTEM_SYSROLE_DATA (ROLE_ID,DEP_ID,DEP_CODE) ");
            insertSql.append(" values(?,?,?) ");
            sysRoleDao.executeSql(insertSql.toString(), new Object[]{roleId,depId,depCode});
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年12月16日 下午5:15:07
     */
    @Test
    public void testInitUser(){
        List<List<Object>> rows= ExcelUtil.getExcelRowValues("d:/1.xls", 0, 0);
        List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
        for(List<Object> row:rows){
            String fullname = (String) row.get(0);
            String username = StringUtil.getPingYin(fullname);
            String password = StringUtil.encryptSha256(SysUser.DEFAULT_PASSWORD);
            Map<String,Object> sysUser = new HashMap<String,Object>();
            sysUser.put("USERNAME", username);
            sysUser.put("FULLNAME", fullname);
            sysUser.put("PASSWORD", password);
            sysUser.put("STATUS", 1);
            sysUser.put("SEX", 1);
            userList.add(sysUser);
        }
        //获取二级部门列表
        StringBuffer sql = new StringBuffer("SELECT D.DEPART_NAME,D.DEPART_ID,count(D.DEPART_ID)");
        sql.append(" AS NUMER FROM T_MSJW_SYSTEM_DEPARTMENT D ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSUSER U ON D.DEPART_ID=U.DEPART_ID ");
        sql.append("GROUP BY  D.DEPART_ID,D.DEPART_NAME");
        List<Map<String,Object>> list = departmentDao.findBySql(sql.toString(), 
                null,null);
        int index = 0;
        for(Map<String,Object> map:list){
            int number = Integer.parseInt(map.get("NUMER").toString());
            String DEPART_ID = (String) map.get("DEPART_ID");
            if(number<=3){
                Map<String,Object> sysUser = userList.get(index);
                sysUser.put("DEPART_ID", DEPART_ID);
                log.info(sysUser.get("FULLNAME"));
                sysRoleDao.saveOrUpdate(sysUser,"T_MSJW_SYSTEM_SYSUSER","");
                index = index+1;
                sysUser = userList.get(index);
                sysUser.put("DEPART_ID", DEPART_ID);
                log.info(sysUser.get("FULLNAME"));
                sysRoleDao.saveOrUpdate(sysUser,"T_MSJW_SYSTEM_SYSUSER","");
                index = index+1;
                sysUser = userList.get(index);
                sysUser.put("DEPART_ID", DEPART_ID);
                log.info(sysUser.get("FULLNAME"));
                sysRoleDao.saveOrUpdate(sysUser,"T_MSJW_SYSTEM_SYSUSER","");
                index = index+1;
            }
        }
        
    }
   
    /**
     * 
     * 描述 初始化角色用户
     * @author Flex Hu
     * @created 2015年12月16日 下午8:55:05
     */
    @Test
    public void initRoleUsers(){
        String tempBack = "_PTJFGLD";
        StringBuffer sql = new StringBuffer("SELECT R.ROLE_ID,R.ROLE_CODE,R.ROLE_NAME");
        sql.append(" FROM T_MSJW_SYSTEM_SYSROLE R ");
        sql.append(" WHERE R.Role_Code LIKE '%").append(tempBack).append("' ORDER BY R.CREATE_TIME DESC ");
        List<Map<String,Object>> roleList = sysRoleDao.findBySql(sql.toString(), 
                null, null);
        for(Map<String,Object> role:roleList){
            //获取角色ID
            String roleId = (String) role.get("ROLE_ID");
            //获取角色编码
            String roleCode = (String) role.get("ROLE_CODE");
            //获取部门编码
            String depCode = roleCode.split(tempBack)[0];
            Map<String,Object> depart = departmentDao.
                    getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", 
                            new String[]{"DEPART_CODE"},new Object[]{depCode});
            String depId = (String) depart.get("DEPART_ID");
            List<Map<String,Object>> users = sysUserService.findUsersByDepId(depId);
            Map<String,Object> oldUser =  users.get(2);
            StringBuffer insertSql = new StringBuffer("INSERT INTO ");
            insertSql.append("T_MSJW_SYSTEM_SYSUSER_SYSROLE (ROLE_ID,USER_ID) ");
            insertSql.append(" values(?,?) ");
            sysRoleDao.executeSql(insertSql.toString(), new Object[]{roleId,oldUser.get("USER_ID")});
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年12月18日 下午4:50:47
     */
    @Test
    public void updatePassword(){
        StringBuffer sql = new StringBuffer("select T.USER_ID,T.USERNAME");
        sql.append(" from T_MSJW_SYSTEM_SYSUSER t where t.create_time>='2015-12-18 16:30:00' ");
        List<Map<String,Object>> userList = sysRoleDao.findBySql(sql.toString(),null,null);
        String updateSql = "UPDATE T_MSJW_SYSTEM_SYSUSER U SET U.PASSWORD=? WHERE U.USER_ID=?";
        for(Map<String,Object> user:userList){
            String userId = (String) user.get("USER_ID");
            String username = (String) user.get("USERNAME");
            String password = username+"123456";
            String dePass = StringUtil.encryptSha256(password);
            sysRoleDao.executeSql(updateSql, new Object[]{dePass,userId});
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年12月18日 上午9:42:51
     * @param args
     */
    public static void main(String[] args){
        log.info(StringUtil.encryptSha256("evecom@123"));
    }
    
}
