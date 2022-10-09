/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.fjfda.service.FdaDictionaryService;
import net.evecom.platform.system.dao.DictionaryDao;

/**
 * 描述 字典信息操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("fdaDictionaryService")
public class FdaDictionaryServiceImpl extends BaseServiceImpl implements FdaDictionaryService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaDictionaryServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private DictionaryDao dao;
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
        StringBuffer sql = new StringBuffer("select D.*,T.TYPE_NAME from T_FJFDA_SYSTEM_DICTIONARY D ");
        sql.append("LEFT JOIN T_FJFDA_SYSTEM_DICTYPE T ON D.TYPE_ID=T.TYPE_ID ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据类别编码获取列表信息
     * @author Flex Hu
     * @created 2014年9月19日 下午5:59:32
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByTypeCode(String typeCode){
        return dao.findByTypeCode(typeCode);
    }
    
    /**
     * 
     * 描述 根据类别编码及自定义条件获取列表信息
     * @param typeCode
     * @param whereStr
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeAndWhere(String typeCode, String whereStr){
        Object[] params = {typeCode};
        StringBuffer sql = new StringBuffer();
        sql.append("select * from t_system_dictionary tsd ");
        sql.append("    join t_system_dictype tsdt on tsd.type_id=tsdt.type_id ");
        sql.append("where tsdt.type_code=? ");
        sql.append("    and " + whereStr);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params, null);
        return list;
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
        return dao.getMaxSn(typeId);
    }
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] dicIds){
        dao.updateSn(dicIds);
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
        return dao.findByTypeCodeAndDoType(typeCode,doType);
    }

    /**
     * 
     * 描述 查询字典届次列表
     * @author Roy Li
     * @created 2015-1-8 下午4:38:30
     * @param typeCode
     * @return
     * @see net.evecom.platform.system.service.DictionaryService#findPeriodByTypeCode(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findPeriodByTypeCode(String typeCode) {
        return dao.findPeriodByTypeCode(typeCode);
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
        return dao.findList(typeCode, orderType);
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
        return dao.findPcs(typeCode, orderType, subParentCode);
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
    public Map<String,Object> get(String typeCode,String dicCode){
        return dao.get(typeCode, dicCode);
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
    public boolean isExist(String typeId,String dicCode){
        return dao.isExist(typeId, dicCode);
    }
    
    /**
     * 
     * 描述 根据父亲类别编码和类别名称还有排序方式获取字典数据列表
     * @author Flex Hu
     * @created 2015年4月16日 上午11:02:52
     * @param parentTypeCode
     * @param typeName
     * @param orderType
     * @return
     */
    public List<Map<String, Object>> findList(String parentTypeCode, String typeName, String orderType) {
        StringBuffer sql = new StringBuffer("select * from T_FJFDA_SYSTEM_DICTIONARY D WHERE D.TYPE_ID=(");
        sql.append("select DT.TYPE_ID from T_FJFDA_SYSTEM_DICTYPE DT WHERE DT.TYPE_NAME=? ")
                .append(" AND DT.PARENT_ID=(select t.type_id from T_FJFDA_SYSTEM_DICTYPE t where t.type_code=?))")
                .append(" ORDER BY D.Dic_Sn ").append(orderType);
        return dao.findBySql(sql.toString(), new Object[]{typeName,parentTypeCode},null);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年5月26日 上午9:11:20
     * @param busType
     * @param typeCode
     * @param orderType
     * @return
     */
    public List<Map<String, Object>> findDataList(String busType, String typeCode, String orderType) {
        StringBuffer sql = new StringBuffer("select * from T_FJFDA_SYSTEM_DICTIONARY D WHERE D.TYPE_ID=(");
        sql.append("select D.TYPE_ID from T_FJFDA_SYSTEM_DICTYPE D WHERE D.TYPE_CODE=? ")
                .append(" AND D.PATH LIKE (SELECT P.PATH FROM T_FJFDA_SYSTEM_DICTYPE P WHERE ")
                .append("P.TYPE_CODE=?)||'%' )");
        sql.append(" ORDER BY D.DIC_SN ").append(orderType);
        return dao.findBySql(sql.toString(), new Object[] { typeCode, busType }, null);
    }
    
    /**
     * 
     * 描述 根据类别编码及自定义条件获取列表信息
     * @param typeCode
     * @param whereStr
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeAndWhereCRJ(String typeCode, String whereStr,String orderType){
        Object[] params = {typeCode};
        StringBuffer sql = new StringBuffer();
        sql.append("select * from t_msjw_system_dictionary tsd ");
        sql.append("    join t_msjw_system_dictype tsdt on tsd.type_id=tsdt.type_id ");
        sql.append("where tsdt.type_code=? ");
        sql.append("    and " + whereStr + " order by tsd.dic_sn " + orderType);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params, null);
        return list;
    }
    
    /**
     * 
     * 描述 获取字典的名称S
     * @author Flex Hu
     * @created 2015年4月28日 下午4:38:31
     * @param busCodes
     * @param typeCodes
     * @param dicCodes
     * @param itemNames
     * @return
     */
    public Map<String,String> getDicTextValues(String busCodes,String typeCodes,String dicCodes,String itemNames){
        Map<String,String> map = new HashMap<String,String>();
        String[] busCodesArray = busCodes.split(",");
        String[] typeCodesArray = typeCodes.split(",");
        String[] dicCodesArray = dicCodes.split(",");
        String[] itemNamesArray = itemNames.split(",");
        for(int i =0;i<itemNamesArray.length;i++){
            String dicName = "";
            try{
                dicName = dao.getTextValue(busCodesArray[i], typeCodesArray[i], dicCodesArray[i]);
            }catch(Exception e){
                log.error(e.getMessage());
            }
            map.put(itemNamesArray[i], dicName);
        }
        return map;
    }
    
    /**
     * 
     * 描述 根据类别编码获取字典数据
     * @author Flex Hu
     * @created 2015年8月7日 下午3:28:28
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String typeCode){
        StringBuffer sql = new StringBuffer("select D.DIC_NAME as text,D.DIC_CODE as value FROM")
                .append(" T_FJFDA_SYSTEM_DICTIONARY D WHERE D.TYPE_ID = (SELECT T.TYPE_ID "
                        + " FROM T_FJFDA_SYSTEM_DICTYPE T ")
                .append(" WHERE T.TYPE_CODE=? ) ORDER BY D.DIC_SN ASC,D.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), new Object[] { typeCode }, null);
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
        return dao.getDicNames(typeCode, dicCodes);
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
        return dao.getDicCode(typeCode, dicName);
    }

    /**
     * 
     * 描述  根据dic_ids获取字典列表
     * @author Faker Li
     * @created 2015年11月16日 下午5:09:33
     * @param bindFormIds
     * @return
     */
    public List<Map<String, Object>> findByDicIds(String dicIds) {
        StringBuffer sql = new StringBuffer("select D.DIC_ID,D.DIC_CODE,D.DIC_NAME");
        sql.append(" FROM T_FJFDA_SYSTEM_DICTIONARY D WHERE D.DIC_ID IN　");
        sql.append(StringUtil.getValueArray(dicIds));
        sql.append(" ORDER BY D.DIC_SN DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }

    @Override
    public String findByDicCodeAndTypeCode(String dicCode, String typeCode) {
        // TODO Auto-generated method stub
        StringBuffer dicName = new StringBuffer("");
        if(StringUtils.isEmpty(dicCode)){
            dicCode = "";
        }
        String []dicCodes = dicCode.split(",");
        for (String string : dicCodes) {
            if(StringUtils.isNotEmpty(dicName)){
                dicName.append(",");
            }
            Map<String,Object>  dictionary  =  dao.getByJdbc("T_FJFDA_SYSTEM_DICTIONARY",
                        new String[]{"DIC_CODE","TYPE_CODE"},new Object[]{string,typeCode});
            if(null!=dictionary){
                dicName.append(dictionary.get("DIC_NAME"));
            }
        }
        return dicName.toString();
    }
    
    /**
     * 
     * 描述 根据查询参数JSON获取数据列表
     * @created 2016年3月27日 上午11:16:25
     * @param queryParamsJson
     * @return
     */
    public List<Map<String,Object>> findList(String queryParamsJson){
        if(queryParamsJson.contains("&#39;")){
            queryParamsJson = queryParamsJson.replace("&#39;", "'");
        }
        Map<String,Object> queryParams = JSON.parseObject(queryParamsJson,Map.class);
        //获取字典类别编码
        String typeCode = (String) queryParams.get("TYPE_CODE");
        //获取排序方式
        String orderType = (String) queryParams.get("ORDER_TYPE");
        StringBuffer sql = new StringBuffer("SELECT T.DIC_CODE AS VALUE,T.DIC_NAME AS TEXT ");
        sql.append(",T.DIC_NAME AS LABEL ");
        sql.append("FROM T_FJFDA_SYSTEM_DICTIONARY T");
        sql.append(" WHERE T.TYPE_CODE=? ");
        sql.append(" ORDER BY T.DIC_SN ").append(orderType);
        return dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
    
    /**
     * 描述 根据查询参数JSON获取数据列表
     * @author Lina Lin
     * @created 2017年9月19日 下午1:23:23
     * @param typeCode
     * @param where
     * @return
     */
    public List<Map<String,Object>> findListByTypecodeAndWhere(String typeCode,String where){
        StringBuffer sql = new StringBuffer("SELECT T.DIC_CODE AS VALUE,T.DIC_NAME AS TEXT ");
        sql.append("FROM T_FJFDA_SYSTEM_DICTIONARY T");
        sql.append(" WHERE T.TYPE_CODE=?  ");
        sql.append(where).append(" ORDER BY T.DIC_SN DESC");
        return dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
}
