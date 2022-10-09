/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.system.dao.DepartmentDao;

import org.springframework.stereotype.Repository;

/**
 * 描述  部门操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("departmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl implements DepartmentDao {

    /**
     * 
     * 描述 根据部门ID删除掉部门,级联更新用户的部门字段
     * @author Flex Hu
     * @created 2014年10月4日 下午1:57:02
     * @param depId
     */
    public void removeDepart(String depId){
        StringBuffer updateSql = new StringBuffer("update T_MSJW_SYSTEM_SYSUSER ")
                .append("SET DEPART_ID=null WHERE DEPART_ID=? ");
        this.jdbcTemplate.update(updateSql.toString(), new Object[]{depId});
        
        String sql = "update T_MSJW_SYSTEM_DEPARTMENT t set t.STATUS='0' where t.DEPART_ID=?";
        this.jdbcTemplate.update(sql, new String[]{depId});
        //this.remove("T_MSJW_SYSTEM_DEPARTMENT","DEPART_ID",new String[]{depId});
    }
    
    /**
     * 
     * 描述 获取根部门
     * @author Flex Hu
     * @created 2014年10月20日 下午2:32:54
     * @return
     */
    public Map<String,Object> getRootDepart(){
        String sql = "select * from T_MSJW_SYSTEM_DEPARTMENT WHERE PARENT_ID=? AND STATUS='1' ORDER BY TREE_SN ASC ";
        List<Map<String,Object>> list = this.findBySql(sql, new Object[]{"0"}, null);
        return list.get(0);
    }
    /**
     * 根据用户ID获取到所授权的业务编码集合
     * 
     * @param userId
     * @return
     */
    public Set<String> getBusCodeSetByUserId(String userId){
        StringBuffer sql = new StringBuffer("select unit_code from t_lcjc_system_busunit where unit_id in (");
        sql.append(" select distinct(unit_id) from t_lcjc_system_busunitrole where role_id in ");
        sql.append(" (select role_id from t_lcjc_system_sysuser_sysrole where user_id=?) ) ");
        List<Map<String,Object>> list = this.findBySql(sql.toString(), new Object[]{userId}, null);
        Set<String> codes = new HashSet<String>();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                codes.add((String) map.get("UNIT_CODE"));
            }
        }
        return codes;
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
        StringBuffer sql = new StringBuffer("SELECT D.DEPART_ID,D.DEPART_NAME FROM T_MSJW_SYSTEM_SYSUSER U ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID=D.DEPART_ID WHERE U.USERNAME=?");
        return this.getMapBySql(sql.toString(), new Object[]{userAccount});
    }
}
