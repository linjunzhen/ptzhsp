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
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.fjfda.service.FdaDicTypeService;
import net.evecom.platform.system.dao.DicTypeDao;

/**
 * 描述 字典类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("fdaDicTypeService")
public class FdaDicTypeServiceImpl extends BaseServiceImpl implements FdaDicTypeService {
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
        return dao.getByJdbc("T_FJFDA_SYSTEM_DICTYPE",new String[]{"TYPE_CODE"},new Object[]{typeCode});
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
        return dao.getByJdbc("T_FJFDA_SYSTEM_DICTYPE", new String[] { "TYPE_CODE", "PARENT_ID" }, 
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
    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月30日 上午9:34:56
     * @param queryParamsJson
     * @return
     */
    @Override
    public List<Map<String, Object>> findList(String queryParamsJson) {
        if(queryParamsJson.contains("&#39;")){
            queryParamsJson = queryParamsJson.replace("&#39;", "'");
        }
        Map<String,Object> queryParams = JSON.parseObject(queryParamsJson,Map.class);
        //获取字典类别编码
        String typeCode = (String) queryParams.get("TYPE_CODE");
        //需要除去字典类别编码
        String notTypeCode = (String) queryParams.get("NOT_TYPE_CODE");
        //获取排序方式
        String orderType = (String) queryParams.get("ORDER_TYPE");
        StringBuffer sql = new StringBuffer("SELECT T.TYPE_CODE AS VALUE,T.TYPE_NAME AS TEXT ");
        sql.append("FROM T_FJFDA_SYSTEM_DICTYPE T");
        sql.append(" WHERE T.PARENT_ID IN ( SELECT SD.TYPE_ID FROM T_FJFDA_SYSTEM_DICTYPE SD ");
        sql.append(" WHERE SD.TYPE_CODE = ? ) ");
        if(StringUtils.isNotEmpty(notTypeCode)){
            sql.append(" AND T.TYPE_CODE NOT IN ").append(StringUtil.getValueArray(notTypeCode));
        }
        sql.append(" ORDER BY T.TREE_SN ").append(orderType);
        return dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月30日 下午4:36:22
     * @param queryParamsJson
     * @return
     * @see net.evecom.platform.system.service.DicTypeService#findYearsList(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findYearsList(String queryParamsJson) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int currentYear =  DateTimeUtil.getCurrentYear();
        for (int i = -2; i <= 20; i++) {
            Map<String, Object> yearMap = new HashMap<String,Object>();
            yearMap.put("VALUE", currentYear+i+"");
            yearMap.put("TEXT", (currentYear+i)+"年");
            list.add(yearMap);
        }
        return list;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年6月13日 下午6:47:12
     * @param parentId
     * @param datas
     * @return
     */
    private void findCityDatas(String parentId,Map<String,Object> dicType){
        StringBuffer sql = new StringBuffer("SELECT D.TYPE_ID,D.TYPE_NAME,D.TYPE_CODE");
        sql.append(" FROM T_FJFDA_SYSTEM_DICTYPE D WHERE D.PARENT_ID=? ");
        sql.append(" ORDER BY D.TREE_SN ASC");
        List<Map<String,Object>> children = dao.findBySql(sql.toString(), new Object[]{parentId}, null);
        if(children!=null&&children.size()>0){
            dicType.put("children", children);
            for(Map<String,Object> child:children){
                this.findCityDatas(child.get("TYPE_ID").toString(), child);
            }
        }
    }
    
    /**
     * 
     * 描述 获取福建区划数据
     * @author Flex Hu
     * @created 2016年6月13日 下午6:38:26
     * @return
     */
    public List<Map<String,Object>> findFjDatas(){
        StringBuffer sql = new StringBuffer("SELECT D.TYPE_ID,D.TYPE_NAME,D.TYPE_CODE ");
        sql.append("FROM T_FJFDA_SYSTEM_DICTYPE D WHERE D.TYPE_CODE=? ");
        List<Map<String,Object>> fjDatas = dao.findBySql(sql.toString(), new Object[]{"350000"},null);
        for(Map<String,Object> fj:fjDatas){
            String parentId = (String) fj.get("TYPE_ID");
            this.findCityDatas(parentId, fj);
        }
        return fjDatas;
    }
    
    /**
     * 根据孩子类别IDS获取上级类别信息
     * @param childTypeIds
     * @return
     */
    public Map<String,Object> getParentInfo(String childTypeIds){
        StringBuffer sql = new StringBuffer("SELECT WM_CONCAT(T.TYPE_NAME) AS TYPE_NAMES");
        sql.append(",WM_CONCAT(T.TYPE_CODE) AS TYPE_CODES FROM T_FJFDA_SYSTEM_DICTYPE T WHERE T.TYPE_ID IN (");
        sql.append("SELECT T.PARENT_ID from T_FJFDA_SYSTEM_DICTYPE T");
        sql.append(" WHERE T.TYPE_ID IN ").append(StringUtil.getValueArray(childTypeIds));
        sql.append(") AND T.TYPE_CODE!='xzqbm' ORDER BY T.CREATE_TIME ASC");
        Map<String,Object> result = dao.getByJdbc(sql.toString(), null);
        if(result.get("TYPE_NAMES")==null){
            result = new HashMap<String,Object>();
            result.put("TYPE_NAMES","福建省");
            result.put("TYPE_CODES","350000");
        }
        return result;
    }
    
    /**
     * 根据类别编码获取类别IDS
     * @param typeCodes
     * @return
     */
    public String getTypeIds(String typeCodes){
        StringBuffer sql = new StringBuffer("SELECT WM_CONCAT(D.TYPE_ID) FROM T_FJFDA_SYSTEM_DICTYPE D");
        sql.append(" WHERE D.TYPE_CODE IN").append(StringUtil.getValueArray(typeCodes));
        String typeIds = (String) dao.getObjectBySql(sql.toString(),null);
        return typeIds;
    }
    
    /**
     * 获取城市和地区编码
     * @param orignCodes
     * @return
     */
    public Map<String,String> getCityAndAreaCode(String orignCodes){
        StringBuffer cityCodes = new StringBuffer("");
        StringBuffer areaCodes = new StringBuffer("");
        Map<String,String> result = new HashMap<String,String>();
        for(String code:orignCodes.split(",")){
            Map<String,Object> dicType = dao.getByJdbc("T_FJFDA_SYSTEM_DICTYPE",
                    new String[]{"TYPE_CODE"},new Object[]{code});
            String TREE_LEVEL = dicType.get("TREE_LEVEL").toString();
            if(TREE_LEVEL.equals("5")){
                cityCodes.append(code).append(",");
            }else if(TREE_LEVEL.equals("6")){
                areaCodes.append(code).append(",");
            }
        }
        if(cityCodes.length()>0){
            result.put("cityCodes", cityCodes.deleteCharAt(cityCodes.length()-1).toString());
        }
        if(areaCodes.length()>0){
            result.put("areaCodes", areaCodes.deleteCharAt(areaCodes.length()-1).toString());
        }
        return result;
    }
    /**
     * 描述 根据父亲编码获取所有的子数据
     * @author Faker Li
     * @created 2016年8月4日 上午11:26:12
     * @param typeCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllChildList(String typeCode) {
        Map<String,Object> dicMap = dao.getByJdbc("SELECT D.* FROM T_FJFDA_SYSTEM_DICTYPE D WHERE D.TYPE_CODE=? ", 
                new Object[]{typeCode});
        if(dicMap!=null){
            String path = (String)dicMap.get("PATH");
            StringBuffer sql = new StringBuffer("SELECT D.* FROM T_FJFDA_SYSTEM_DICTYPE D WHERE D.PATH LIKE ? ");
            sql.append(" AND D.TYPE_CODE!=? ORDER BY D.TREE_SN ASC ");
            List<Map<String, Object>> dicMapList =  dao.findBySql(sql.toString(), 
                    new Object[]{path+"%",typeCode}, null);
            return dicMapList;
        }else{
            return null;
        }
        
    }
    
    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年4月17日 下午4:05:11
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId) {
        List<Map<String, Object>> children = this
                .findByParentId(parentId);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("TYPE_ID"));
                child.put("name", child.get("TYPE_NAME"));
                child.put("icon",
                        "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, (String) child.get("TYPE_ID"));
            }
        }
    }
    
    /**
     * 获取字典类别树形JSON
     * @return
     */
    public String getDicTypeTreeJson(){
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "字典类别树");
        root.put("open", true);
        root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        // 获取topType
        List<Map<String, Object>> toplist = this.findByParentId("0");
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("TYPE_ID"));
            top.put("name", top.get("TYPE_NAME"));
            top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildren(top, (String) top.get("TYPE_ID"));
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        return json;
    }
    
}
