/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.system.dao.DicTypeDao;

/**
 * 描述  字典类别操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("dicTypeDao")
public class DicTypeDaoImpl extends BaseDaoImpl implements DicTypeDao {

    /**
     * 
     * 描述 根据字典类别ID删除掉数据
     * @author Flex Hu
     * @created 2014年9月19日 下午2:41:57
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        StringBuffer deleteDic = new StringBuffer("delete from T_MSJW_SYSTEM_DICTIONARY D")
            .append(" WHERE D.TYPE_ID=? ");
        this.jdbcTemplate.update(deleteDic.toString(), new Object[]{typeId});
        this.remove("T_MSJW_SYSTEM_DICTYPE","TYPE_ID",new Object[]{typeId});
    }
    
    /**
     * 
     * 描述 根据父亲ID获取类别数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId){
        String sql = "select * from T_MSJW_SYSTEM_DICTYPE WHERE PARENT_ID=? ORDER BY TREE_SN ASC";
        return this.findBySql(sql, new Object[]{parentId}, null);
    }
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @return
     */
    public List<Map<String,Object>> getListByJdbc(String tableName,String typeCode){
        String sql ="select t.DIC_CODE,t.DIC_NAME from "+ tableName +" t where t.TYPE_CODE = '"+typeCode+"'";
        return this.jdbcTemplate.queryForList(sql.toString());
    }
    
    /**
     * 
     * 描述 根据类别编码获取类别数据
     * @author Flex Hu
     * @created 2014年10月22日 上午9:20:31
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByParentCode(String typeCode){
        StringBuffer sql = new StringBuffer("select T.* FROM ").append(
                "T_MSJW_SYSTEM_DICTYPE T WHERE T.PARENT_ID=(SELECT P.TYPE_ID ").append(
                "FROM T_MSJW_SYSTEM_DICTYPE P WHERE P.TYPE_CODE=?) ORDER BY T.TREE_SN ASC ");
        return this.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
    
    /**
     * 
     * 描述 根据类别编码获取到数据列表
     * @author Flex Hu
     * @created 2014年10月22日 下午4:10:09
     * @param typeCodeSet
     * @return
     */
    public List<Map<String,Object>> findByCodeSet(Set<String> typeCodeSet){
        StringBuffer typeCodes = new StringBuffer("(");
        for(String typeCode:typeCodeSet){
            typeCodes.append("'").append(typeCode).append("',");
        }
        typeCodes.deleteCharAt(typeCodes.length()-1);
        typeCodes.append(")");
        StringBuffer sql = new StringBuffer("select T.* FROM ").append("T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE IN ")
                .append(typeCodes).append(" ORDER BY T.TREE_SN ASC ");
        return this.findBySql(sql.toString(),null, null);
    }

    /**
     * 
     * 描述  根据父类编码和业务类型获取数据列表
     * @author Danto Huang
     * @created 2015-4-20 下午3:15:54
     * @param typeCode
     * @param busType
     * @return
     */
    public List<Map<String,Object>> findByParentCodeAndBusType(String typeCode,String busType){
        Map<String, Object> busMap = this.getByJdbc("T_MSJW_SYSTEM_DICTYPE", new String[] { "TYPE_CODE" },
                new Object[] { busType });
        String busPath = busMap.get("PATH").toString();
        StringBuffer sql = new StringBuffer("select T.* FROM ").append(
                "T_MSJW_SYSTEM_DICTYPE T WHERE T.PARENT_ID=(SELECT P.TYPE_ID ").append(
                "FROM T_MSJW_SYSTEM_DICTYPE P WHERE P.TYPE_CODE=? ");
        /*sql.append(" and P.PATH LIKE '").append(busPath).append("%'");
        sql.append(") and T.PATH LIKE '").append(busPath).append("%'");*/
        sql.append(" and P.PATH LIKE ? ");
        sql.append(") and T.PATH LIKE ? ");
        sql.append(" ORDER BY T.TREE_SN ASC ");
        return this.findBySql(sql.toString(), new Object[]{typeCode,busPath+"%",busPath+"%"}, null);
    }
    
    /**
     * 
     * 描述 根据大类编码和类别编码获取类别名称
     * @author Flex Hu
     * @created 2015年4月29日 下午2:46:50
     * @param busCode
     * @param typeCode
     * @return
     */
    public String getTypeName(String busCode, String typeCode) {
        StringBuffer sql = new StringBuffer("select D.TYPE_NAME from T_MSJW_SYSTEM_DICTYPE D ").append(
                "WHERE D.TYPE_CODE=? AND D.PATH LIKE (SELECT P.PATH FROM T_MSJW_SYSTEM_DICTYPE P ").append(
                "WHERE P.TYPE_CODE=?)||'%' ");
        String typeName = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] { typeCode, busCode },
                String.class);
        return typeName;
    }

    @Override
    public String getDicCode(String tableName, String dicName) {
        // TODO Auto-generated method stub
        String sql ="select t.DIC_CODE from "+ tableName +" t where t.DIC_NAME = '"+dicName+"'";
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString());
        String dicCode = list.get(0).get("DIC_CODE").toString();
        return dicCode;
    }
}
