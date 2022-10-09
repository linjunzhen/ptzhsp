/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.dao.CatalogDao;
import net.evecom.platform.wsbs.service.DepartCatalogService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-14 上午9:29:51
 */
@Service("separtCatalogService")
public class DepartCatalogServiceImpl extends BaseServiceImpl implements
        DepartCatalogService {
    /**
     * 所引入的dao
     */
    @Resource
    private CatalogDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
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
     * 描述   获取事项目录列表
     * @author Danto Huang
     * @created 2016-9-14 上午9:31:37
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select SC.CATALOG_ID,SC.CATALOG_NAME,SC.CATALOG_CODE,SC.DEPART_ID,D.DEPART_CODE,SC.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,D1.DEPART_NAME as CHILD_DEPART,SC.CREATE_TIME,SC.IS_USE,");
        sql.append("SC.SXXZ SXXZCODE ");
        sql.append("from T_WSBS_SERVICEITEM_CATALOG SC  ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON SC.DEPART_ID=D.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=SC.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=SC.CHILD_DEPART_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemKind' AND SC.STATUS=1 ");
        //获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        //非超管进行数据级别权限控制
        if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            //获取当前用户被授权的部门代码
            String authDepCodes  = curUser.getAuthDepCodes();
            sql.append(" AND D1.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:获取标准化事项目录列表
     *
     * @author Madison You
     * @created 2021/2/18 9:39:00
     * @param
     * @return
     */
    public List<Map<String, Object>> findStdBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.CATALOG_ID,T.CATALOG_NAME,CATALOG_STATE FROM T_WSBS_SERVICEITEM_STDCATALOG T WHERE 1 = 1 ");
        //获取当前用户信息
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }


    /**
     * 
     * 描述   删除目录
     * @author Danto Huang
     * @created 2016-9-20 下午3:40:43
     * @param selectColNames
     */
    public boolean removeCascade(String selectColNames){
        //String sql = "update T_WSBS_RIGHTBILL_CATALOG t set t.ITEM_CATALOGCODE='' where t.ITEM_CATALOGCODE=?";
        //String udpSql = "update T_WSBS_SERVICEITEM t set t.FWSXZT=3 where t.CATALOG_CODE=?";
        String checkSql = "select count(t.item_id) count from T_WSBS_SERVICEITEM t where t.CATALOG_CODE=?";
        String[] catalogIds = selectColNames.split(",");
        boolean flag = true;
        for(int i=0;i<catalogIds.length;i++){
            String catalogCode = this.getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_ID" },
                    new Object[] { catalogIds[i] }).get("CATALOG_CODE").toString();
            String count = dao.getByJdbc(checkSql, new Object[]{catalogCode}).get("COUNT").toString();
            if(Integer.valueOf(count)>0){
                flag = false;
                break;
            }
            //dao.executeSql(sql, new Object[]{catalogCode});
            //dao.executeSql(udpSql, new Object[]{catalogCode});
        }
        if(flag){
            for(int i=0;i<catalogIds.length;i++){                
                Map<String, Object> catalog  = new HashMap<String, Object>();
                catalog.put("STATUS",0);
                dao.saveOrUpdate(catalog, "T_WSBS_SERVICEITEM_CATALOG", catalogIds[i]);
            }
        }
        return flag;
        //this.remove("T_WSBS_SERVICEITEM_CATALOG", "CATALOG_ID", selectColNames.split(","));
    }
    
    /**
     * 
     * 描述    目录划转
     * @author Danto Huang
     * @created 2018年8月20日 下午4:13:16
     * @param departId
     * @param catalogIds
     */
    public void moveCatalog(String departId,String catalogIds){
        String[] catalogId = catalogIds.split(",");
        for(int i=0;i<catalogId.length;i++){
            Map<String ,Object> variables = new HashMap<String, Object>();
            variables.put("DEPART_ID", departId);
            variables.put("CHILD_DEPART_ID", "");
            dao.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_CATALOG", catalogId[i]);
        }
    }
    
    /**
     * 
     * 描述    目录变更，关联修改事项性质
     * @author Danto Huang
     * @created 2018年10月19日 上午9:38:49
     * @param catalogCode
     * @param sxxz
     */
    public void updateSxxzbyCatalog(String catalogCode ,String sxxz){
        String sql = "update T_WSBS_SERVICEITEM set SXXZ=? where CATALOG_CODE=?";
        dao.executeSql(sql, new Object[]{sxxz,catalogCode});
    }

    /**
     * 描述:查看目录事项
     *
     * @author Madison You
     * @created 2020/4/26 11:41:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getServiceItemByCatalog(String entityId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select a.item_name,a.item_code from t_wsbs_serviceitem a ");
        sql.append(" left join t_wsbs_serviceitem_catalog b on a.catalog_code = b.catalog_code ");
        sql.append(" where b.catalog_id = ? ");
        params.add(entityId);
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:查看标准化目录事项
     *
     * @author Madison You
     * @created 2021/2/22 9:35:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getStdServiceItemByCatalog(String entityId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT A.ITEM_NAME,A.ITEM_CODE FROM T_WSBS_SERVICEITEM A WHERE A.STANDARD_CATALOG_ID = ? ");
        sql.append(" ORDER BY CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(), new Object[]{entityId}, null);
    }
}
