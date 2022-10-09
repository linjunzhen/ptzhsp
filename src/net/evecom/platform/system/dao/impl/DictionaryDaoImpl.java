/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.DictionaryDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 描述  字典信息操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("dictionaryDao")
public class DictionaryDaoImpl extends BaseDaoImpl implements DictionaryDao {

    /**
     * 
     * 描述 根据类别编码获取列表信息
     * @author Flex Hu
     * @created 2014年9月19日 下午5:59:32
     * @param typeCode
     * @return
     */
    public List<Map<String, Object>> findByTypeCode(String typeCode) {
        StringBuffer sql = new StringBuffer(
                "select D.DIC_ID,D.DIC_NAME,D.DIC_CODE,D.DIC_DESC FROM T_MSJW_SYSTEM_DICTIONARY").append(
                " D WHERE D.TYPE_ID = (SELECT T.TYPE_ID FROM ").append(
                "T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=? ) ORDER BY D.DIC_SN ASC,D.CREATE_TIME DESC ");
        return this.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }
    
    /**
     * 
     * 描述 获取某个字典类别下字典的最大排序
     * @author Flex Hu
     * @created 2014年10月3日 上午11:54:19
     * @param typeId
     * @return
     */
    public int getMaxSn(String typeId){
        StringBuffer sql = new StringBuffer("select max(DIC_SN) FROM T_MSJW_SYSTEM_DICTIONARY "
                + "").append(" WHERE TYPE_ID=? ");
        return this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{typeId});
    }
    
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] dicIds){
        int[] oldSns = new int[dicIds.length];
        StringBuffer sql = new StringBuffer("select DIC_SN FROM T_MSJW_SYSTEM_DICTIONARY ").append(" WHERE DIC_ID=? ");
        for (int i = 0; i < dicIds.length; i++) {
            int dicSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { dicIds[i] });
            oldSns[i] = dicSn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer("update T_MSJW_SYSTEM_DICTIONARY T ")
                .append(" SET T.DIC_SN=? WHERE T.DIC_ID=? ");
        for (int i = 0; i < dicIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], dicIds[i] });
        }
    }
    
    /**
     * 
     * 描述 根据类别编码和使用位置获取列表信息
     * @author Danto Huang
     * @created 2014-12-12 下午5:59:15
     * @param typeCode
     * @param doType
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeAndDoType(String typeCode,String doType){
        StringBuffer sql = new StringBuffer("select D.DIC_ID,D.DIC_NAME,D.DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY D ");
        if(typeCode.equals("ProposeFlowPathState")){
            sql.append("WHERE D.TYPE_ID ='4028b77249d01cbe0149d028b7890011' ");
        }else if(typeCode.equals("BillFlowPathState")){
            sql.append("WHERE D.TYPE_ID ='4028b77249d01cbe0149d031a4870057' ");
        }else{
            sql.append("WHERE D.TYPE_ID = (SELECT T.TYPE_ID FROM ")
                .append("T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=? )");
        }
        if(doType.equals("billproposeview")){
            sql.append(" and D.DIC_CODE>4 ");
        }
        sql.append("ORDER BY D.DIC_SN ASC,D.CREATE_TIME DESC ");
        if(typeCode.equals("ProposeFlowPathState")||typeCode.equals("BillFlowPathState")){
            return this.findBySql(sql.toString(), null, null);
        }else{
            return this.findBySql(sql.toString(), new Object[]{typeCode}, null);
        }
        
    }

    /**
     * 
     * 描述 查询字典届次列表
     * @author Roy Li
     * @created 2015-1-8 下午4:40:44
     * @param typeCode
     * @return
     * @see net.evecom.platform.system.dao.DictionaryDao#findPeriodByTypeCode(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findPeriodByTypeCode(String typeCode) {
        StringBuffer sql = new StringBuffer(
                "SELECT T.TYPE_ID,T.TYPE_NAME,T.TYPE_CODE from T_MSJW_SYSTEM_DICTYPE T ")
                .append(" WHERE T.TREE_LEVEL > 3 ")
                .append(" START WITH T.TYPE_CODE =? ")
                .append(" CONNECT BY PRIOR T.TYPE_ID=T.PARENT_ID ");
        return this.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }
    
    /**
     * 
     * 描述 根据类别编码和排序类型获取字典数据
     * @author Flex Hu
     * @created 2015年3月8日 上午8:40:10
     * @param typeCode
     * @param orderType
     * @return
     */
    public List<Map<String,Object>> findList(String typeCode,String orderType){
        StringBuffer sql = new StringBuffer("select D.DIC_ID,D.DIC_NAME,D.DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY")
                .append(" D WHERE D.TYPE_ID = (SELECT T.TYPE_ID FROM ")
                .append("T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=? ) ORDER BY D.DIC_SN ").append(orderType);
        return this.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }
    
    /**
     * 
     * 描述 获取派出所字典数据
     * @author Flex Hu
     * @created 2015年3月8日 上午8:40:10
     * @param typeCode
     * @param orderType
     * @param subParentCode
     * @return
     */
    public List<Map<String,Object>> findPcs(String typeCode,String orderType,String subParentCode){
        StringBuffer sql = new StringBuffer("select D.DIC_ID,D.DIC_NAME,D.DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY")
                .append(" D WHERE D.TYPE_ID = (SELECT T.TYPE_ID FROM ")
                .append("T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=? ) and D.dic_name like ? ");
        sql.append(" and substr(D.dic_code, 0, 6) = ?  ORDER BY D.DIC_SN ").append(orderType);
        return this.findBySql(sql.toString(), new Object[] { typeCode,"%派出所",subParentCode}, null);
    }
    
    /**
     * 
     * 描述 根据类别编码和字典编码获取到字典
     * @author Flex Hu
     * @created 2015年3月8日 下午12:05:03
     * @param typeCode
     * @param dicCode
     * @return
     */
    public Map<String, Object> get(String typeCode, String dicCode) {
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_DICTIONARY D").append(
                " WHERE D.DIC_CODE=? AND D.TYPE_ID IN (SELECT T.TYPE_ID FROM ").append(
                " T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=? )");
        Map<String, Object> map = this.getByJdbc(sql.toString(), new Object[] { dicCode, typeCode });
        return map;
    }
    
    /**
     * 
     * 描述 判断某个字典类别下是否存在相同编码的字典
     * @author Flex Hu
     * @created 2015年3月28日 上午9:34:32
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId, String dicCode) {
        StringBuffer sql = new StringBuffer("select count(*) from")
                .append(" T_MSJW_SYSTEM_DICTIONARY WHERE TYPE_ID=? AND DIC_CODE=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[] { typeId, dicCode });
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * 描述 获取字典的显示值
     * @author Flex Hu
     * @created 2015年4月28日 下午4:16:52
     * @param busCode
     * @param typeCode
     * @param dicCode
     * @return
     */
    public String getTextValue(String busCode,String typeCode,String dicCode){
        StringBuffer sql = new StringBuffer("select D.DIC_NAME from T_MSJW_SYSTEM_DICTIONARY D");
        sql.append(" LEFT JOIN t_Msjw_System_Dictype T ").append(
                "ON D.TYPE_ID=T.TYPE_ID WHERE T.TYPE_CODE=? AND D.DIC_CODE=? ");
        List params = new ArrayList();
        params.add(typeCode);
        params.add(dicCode);
        if (StringUtils.isNotEmpty(busCode)) {
            sql.append("AND T.PARENT_ID=(SELECT P.TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE P ").append(
                    "WHERE P.TYPE_CODE=? )");
            params.add(busCode);
        }
        String dicName = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),String.class);
        return dicName;
        
    }
    
    /**
     * 
     * 描述 获取字典名称,如果传入多个dicCode,那么字典名称将会以逗号拼接返回
     * @author Flex Hu
     * @created 2015年9月1日 上午9:12:25
     * @param typeCode
     * @param dicCodes
     * @return
     */
    public String getDicNames(String typeCode,String dicCodes){
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(D.dic_name)");
        sql.append(" FROM T_MSJW_SYSTEM_DICTIONARY D ");
        sql.append(" WHERE D.TYPE_ID=(SELECT T.TYPE_ID ");
        sql.append(" FROM T_MSJW_SYSTEM_DICTYPE T WHERE T.TYPE_CODE=?) ");
        sql.append(" AND D.DIC_CODE IN ").append(StringUtil.getValueArray(dicCodes));
        sql.append(" ORDER BY D.DIC_SN ASC ");
        String dicNames = this.jdbcTemplate.queryForObject(sql.toString(),
                new Object[]{typeCode},String.class);
        return dicNames;
    }
    
    /**
     * 
     * 描述 根据类别编码和字典名称获取字典编码
     * @author Flex Hu
     * @created 2015年10月19日 下午2:42:29
     * @param typeCode
     * @param dicName
     * @return
     */
    public String getDicCode(String typeCode,String dicName){
        StringBuffer sql = new StringBuffer("select D.DIC_CODE from T_MSJW_SYSTEM_DICTIONARY D");
        sql.append(" WHERE D.TYPE_CODE=? AND D.DIC_NAME=? ");
        String dicCode =  (String) this.getObjectBySql(sql.toString(),
                new Object[]{typeCode,dicName});
        return dicCode;
    }
}
