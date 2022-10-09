/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.fjfda.service.FdaBusTypeService;
import net.evecom.platform.wsbs.dao.BusTypeDao;

/**
 * 描述 业务类别操作service
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("fdaBusTypeService")
public class FdaBusTypeServiceImpl extends BaseServiceImpl implements
        FdaBusTypeService {
    /**
     * 所引入的dao
     */
    @Resource
    private BusTypeDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
     * 描述 根据材料ID获取IDS和NAMES
     * 
     * @author Flex Hu
     * @created 2015年9月17日 下午5:41:55
     * @param materId
     * @return
     */
    public Map<String, Object> getIdAndNamesByMaterId(String materId) {
        return dao.getIdAndNamesByMaterId(materId);
    }

    /**
     * 
     * 描述 根据服务对象值获取列表记录
     * 
     * @author Flex Hu
     * @created 2015年9月18日 上午11:46:35
     * @param fwdxValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findDatasForSelect(String fwdxValue) {
        String typeCode = "";
        if (fwdxValue.equals("1")) {
            typeCode = "public";
        } else if (fwdxValue.equals("2")) {
            typeCode = "enterprise";
        } else if (fwdxValue.equals("3")) {
            typeCode = "DepartService";
        }
        StringBuffer sql = new StringBuffer(
                "select T.TYPE_ID as value,T.TYPE_NAME as text from T_FJFDA_BUSTYPE T");
        sql.append(" WHERE T.PARENT_ID=(SELECT P.TYPE_ID FROM T_FJFDA_BUSTYPE P WHERE P.TYPE_CODE=?)");
        sql.append(" ORDER BY T.TREE_SN ASC");
        return dao.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }

    /**
     * 
     * 描述 保存类别项目中间表
     * 
     * @author Flex Hu
     * @created 2015年9月22日 下午3:17:40
     * @param typeIds
     * @param itemId
     */
    public void saveBusTypeItem(String[] typeIds, String itemId) {
        dao.remove("T_WSBS_SERVICEITEM_TYPE", "ITEM_ID",
                new String[] { itemId });
        dao.saveBusTypeItem(typeIds, itemId);
    }

    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * 
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String, Object> getIdAndNamesByItemId(String itemId) {
        return dao.getIdAndNamesByItemId(itemId);
    }

    /**
     * 
     * 描述 根据类别编码获取数据列表
     * 
     * @author Flex Hu
     * @created 2015年11月20日 下午5:16:59
     * @param typeCode
     * @return
     */
    public List<Map<String, Object>> findByTypeCodeForWebSite(String typeCode) {
        StringBuffer sql = new StringBuffer(
                "select B.TYPE_ID,B.TYPE_NAME,B.ICON_PATH");
        sql.append(" from T_FJFDA_BUSTYPE B WHERE B.PARENT_ID=");
        sql.append("(SELECT T.TYPE_ID FROM T_FJFDA_BUSTYPE T WHERE T.TYPE_CODE=? )");
        sql.append(" ORDER BY B.TREE_SN ASC");
        return dao.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年5月26日 下午4:48:38
     * @param queryParamsJson
     * @return
     */
    @Override
    public List<Map<String, Object>> findListForSelect(String queryParamsJson) {
        if(queryParamsJson.contains("&#39;")){
            queryParamsJson = queryParamsJson.replace("&#39;", "'");
        }
        Map<String, Object> queryParams = JSON.parseObject(queryParamsJson,
                Map.class);
        // 获取类别父ID
        String parentId = (String) queryParams.get("PARENT_ID");
        // 获取排序方式
        String orderType = (String) queryParams.get("ORDER_TYPE");
        //DepartId
        String departId1 = (String) queryParams.get("DEPARTID_1");
        String departId2 = (String) queryParams.get("DEPARTID_2");
        StringBuffer sql = new StringBuffer(
                "SELECT T.TYPE_ID AS VALUE,T.TYPE_NAME AS TEXT ");
        sql.append("FROM T_FJFDA_BUSTYPE T");
        sql.append(" WHERE T.PARENT_ID=? ");
        List<Object> params = new ArrayList<Object>();
        params.add(parentId);
       
        if(!StringUtils.isEmpty(departId2)){
            sql.append("AND T.DECENTRALIZATION_DEPART_ID like ? ");
            params.add("%"+departId2+"%");
        }
        if(!StringUtils.isEmpty(departId1)){
            sql.append("AND exists (select * from T_FJFDA_BUSTYPE D ");
            sql.append("WHERE D.PARENT_ID = T.TYPE_ID AND D.DECENTRALIZATION_DEPART_ID like ?) ");
            params.add("%"+departId1+"%");
        }
        sql.append(" ORDER BY T.TREE_SN ").append(orderType);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    @Override
    public List<Map<String, Object>> findListForCheckbox(String queryParamsJson) {
        Map<String, Object> queryParams = JSON.parseObject(queryParamsJson,
                Map.class);
        // 获取类别父ID
        String parentId = (String) queryParams.get("PARENT_ID");
        // 获取排序方式
        //String orderType = (String) queryParams.get("ORDER_TYPE");

        StringBuffer sql = new StringBuffer(
                "select B.TYPE_CODE AS VALUE,B.TYPE_NAME AS TEXT ");
        sql.append(" from T_FJFDA_BUSTYPE B ");
        sql.append(" start with B.PARENT_ID = ? ");
        sql.append(" connect by nocycle prior B.TYPE_ID = B.PARENT_ID ");
        return dao.findBySql(sql.toString(), new Object[] { parentId }, null);
    }

    /**
     * 描述  食品生产证照类别
     * @author John Zhang
     * @created 2017年3月5日 下午11:56:50
     * @param queryParamsJson
     * @return
     */
    @Override
    public List<Map<String,Object>> findListForSpsc(String queryParamsJson){
        StringBuffer sql = new StringBuffer(
                "SELECT T.TYPE_ID AS VALUE,T.TYPE_NAME AS TEXT ");
        sql.append("FROM T_FJFDA_BUSTYPE T ");
        sql.append(" where T.PARENT_ID in (?,?) ");
        sql.append(" order by T.TYPE_CODE asc");
        return dao.findBySql(sql.toString(), 
                new Object[] { "402848df54e0c4970154e0c63aa30002",
            "402848df54e0e6560154e17bfb86007b"}, null);
    }
}
