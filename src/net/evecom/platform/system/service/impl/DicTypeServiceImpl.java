/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.DicTypeDao;
import net.evecom.platform.system.service.DicTypeService;

import org.springframework.stereotype.Service;

/**
 * 描述 字典类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("dicTypeService")
public class DicTypeServiceImpl extends BaseServiceImpl implements DicTypeService {
    /**
     * 所引入的dao
     */
    @Resource
    private DicTypeDao dao;
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
     * 描述 根据字典类别ID删除掉数据
     * @author Flex Hu
     * @created 2014年9月19日 下午2:41:57
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        dao.removeByTypeId(typeId);
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
        return dao.findByParentId(parentId);
    }
    
    /**
     * 
     * 描述 根据类别编码获取到类别
     * @author Flex Hu
     * @created 2014年10月21日 下午3:42:18
     * @param typeCode
     * @return
     */
    public Map<String,Object> getByTypeCode(String typeCode){
        return dao.getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_CODE"},new Object[]{typeCode});
    }
    /**
     *
     * 描述 获取省份列表
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentCode
     * @return
     */
    public List<Map<String,Object>> findProvince(String parentCode){
        parentCode=parentCode.replaceAll("0000","");
        if(parentCode.indexOf("XZQHDM")==-1){
            parentCode="XZQHDM"+parentCode;
        }
        List<Map<String,Object>> results= Lists.newArrayList();
        List<Map<String,Object>> provinces=dao.findByParentCode(parentCode);
        for(Map<String,Object> province: provinces){
            HashMap<String,Object> result= Maps.newHashMap();
            String value=StringUtil.getString(province.get("TYPE_CODE"));
            value = Pattern.compile("[^0-9]").matcher(value).replaceAll("").trim();
            result.put("VALUE", StringUtil.getAppendNumber(6,value));
            result.put("TEXT",StringUtil.getString(province.get("TYPE_NAME")));
            result.put("TYPE_CODE",StringUtil.getString(province.get("TYPE_CODE")));
            results.add(result);
        }
        return results;
    }
    /**
     * 
     * 描述 根据类别编码获取到类别
     * @author Flex Hu
     * @created 2014年10月21日 下午3:42:18
     * @param typeCode
     * @return
     */
    public Map<String,Object> getByTypeCode(String typeCode,String parentId){
        return dao.getByJdbc("T_MSJW_SYSTEM_DICTYPE", new String[] { "TYPE_CODE", "PARENT_ID" }, 
                new Object[] {typeCode, parentId });
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
        return dao.findByParentCode(typeCode);
    }

    /**
     * 
     * 描述 根据类别编码获取类别数据
     * @author Flex Hu
     * @created 2014年10月22日 上午9:20:31
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByParentCodeForSelect(String typeCode){
        
        StringBuffer sql = new StringBuffer("select  T.TYPE_NAME as text,T.TYPE_CODE"
                + " as value,T.TREE_SN as dicdesc FROM ").append(
                "T_MSJW_SYSTEM_DICTYPE T WHERE T.PARENT_ID=(SELECT P.TYPE_ID ").append(
                "FROM T_MSJW_SYSTEM_DICTYPE P WHERE P.TYPE_CODE=?) ORDER BY T.TREE_SN ASC ");
        return dao.findBySql(sql.toString(), new Object[] { typeCode }, null);
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
        return dao.findByCodeSet(typeCodeSet);
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
        return dao.findByParentCodeAndBusType(typeCode,busType);
    }
    
    /**
     * 
     * 描述 获取字典类别的显示值
     * @author Flex Hu
     * @created 2015年4月29日 下午2:52:06
     * @param busCodes
     * @param typeCodes
     * @param itemNames
     * @return
     */
    public Map<String,String> getTextValues(String busCodes,String typeCodes,String itemNames){
        Map<String,String> values = new HashMap<String,String>();
        String[] busCodesArray = busCodes.split(",");
        String[] typeCodesArray = typeCodes.split(",");
        String[] itemNamesArray = itemNames.split(",");
        for(int i =0;i<busCodesArray.length;i++){
            String typeName = dao.getTypeName(busCodesArray[i], typeCodesArray[i]);
            values.put(itemNamesArray[i], typeName);
        }
        return values;
    }
    @Override
    public List<Map<String, Object>> getListByJdbc(String tableName, String typeCode) {
        return dao.getListByJdbc(tableName, typeCode);
    }
    @Override
    public String getDicCode(String tableName, String dicName) {
        return dao.getDicCode(tableName, dicName);
    }
}
