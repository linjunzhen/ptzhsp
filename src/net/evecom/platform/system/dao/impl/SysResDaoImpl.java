/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.system.dao.SysResDao;

import org.springframework.stereotype.Repository;

/**
 * 描述  系统资源操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("sysResDao")
public class SysResDaoImpl extends BaseDaoImpl implements SysResDao {

    /**
     * 
     * 描述 根据角色ID删除掉资源和角色中间表数据
     * @author Flex Hu
     * @created 2014年10月3日 下午4:43:14
     * @param roleId
     */
    public void removeSysResRoleByRoleId(String roleId){
        StringBuffer sql = new StringBuffer("delete from T_MSJW_SYSTEM_RES_ROLE RR ").append("WHERE RR.ROLE_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{roleId});
    }
    
    /**
     * 
     * 描述 根据角色ID获取到当前被勾选的资源
     * @author Flex Hu
     * @created 2014年10月3日 下午4:52:32
     * @param roleId
     * @return
     */
    public String getGrantResIds(String roleId){
        StringBuffer sql = new StringBuffer("select RES_ID FROM").append(" "
                + "T_MSJW_SYSTEM_RES_ROLE RR WHERE RR.ROLE_ID=? ");
        List<String> list = this.jdbcTemplate.
                queryForList(sql.toString(),String.class,new Object[]{roleId});
        StringBuffer ids = new StringBuffer("");
        for(int i =0;i<list.size();i++){
            if(i>0){
                ids.append(",");
            }
            ids.append(list.get(i));
        }
        return ids.toString();
    }
    
    /**
     * 
     * 描述 根据角色ID获取被授权的资源KEYS
     * @author Flex Hu
     * @created 2014年10月11日 下午4:19:34
     * @param roleId
     * @return
     */
    public String getGrantKeys(String roleId){
        StringBuffer sql = new StringBuffer("select R.Res_Key from T_MSJW_SYSTEM_RES R ").append(
                "WHERE R.RES_ID IN (select RR.RES_ID from T_MSJW_SYSTEM_RES_ROLE RR ").append("WHERE RR.ROLE_ID=?)");
        List<String> list = this.jdbcTemplate.
                queryForList(sql.toString(),String.class,new Object[]{roleId});
        StringBuffer keys = new StringBuffer("");
        for(int i =0;i<list.size();i++){
            if(i>0){
                keys.append(",");
            }
            keys.append(list.get(i));
        }
        return keys.toString();
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 上午9:34:53
     * @param roleId
     * @return
     */
    public List<Map<String,Object>> getResList(String roleId){
        StringBuffer sql = new StringBuffer("select R.* from T_MSJW_SYSTEM_RES R ").append(
                "WHERE R.RES_ID IN (select RR.RES_ID from T_MSJW_SYSTEM_RES_ROLE RR ").append("WHERE RR.ROLE_ID=?)");
        return this.findBySql(sql.toString(), new Object[]{roleId}, null);
    }
}
