/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.wsbs.dao.BsscDao;
import net.evecom.platform.wsbs.dao.TabletMenuDao;
import net.evecom.platform.wsbs.service.TabletMenuService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
@Service("tabletMenuService")
public class TabletMenuServiceImpl extends BaseServiceImpl implements TabletMenuService{

    /**
     * 所引入的dao
     */
    @Resource
    private TabletMenuDao dao;
    
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
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
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select T.TABLET_ID,T.TABLET_NAME,T.DEPART_ID,T.ITEM_IDS,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS ITEM_KIND,T.CREATE_TIME  ");
        sql.append("from T_WSBS_TABLE_MENU T  ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEPART_ID=D.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.ITEM_KIND ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemKind' AND T.STATUS=1 ");
        //获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        //非超管进行数据级别权限控制
        if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            //获取当前用户被授权的部门代码
            String authDepCodes  = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @Override
    public void removeCascade(String[] tabletIds) {
        //List<Map<String, Object>> list=this.findMenuById(tabletIds.toString(), null);
        for (String tabletId : tabletIds) {
            // 清除掉关联服务事项数据
            StringBuffer delSql = new StringBuffer("delete ").append(" FROM T_WSBS_DEPTGROUP WHERE TABLET_ID=?");
            dao.executeSql(delSql.toString(), new Object[] { tabletId });
            
            StringBuffer sql = new StringBuffer("delete ").append(" FROM T_WSBS_TABLE_MENU WHERE TABLET_ID=?");
            dao.executeSql(sql.toString(), new Object[] { tabletId });
//            Map<String, Object> tablet  = new HashMap<String, Object>();
//            tablet.put("STATUS",0);
//            dao.saveOrUpdate(tablet, "T_WSBS_TABLE_MENU", tabletId);
        }
    }
    @Override
    public List<Map<String, Object>> findTree(HttpServletRequest request,
            String tableName, String parentId, String targetCols) {
        StringBuffer sql = new StringBuffer("select ").append(targetCols);
        sql.append(" from ").append(tableName).append(" WHERE PATH like ? ");
        String path = null;
        if(parentId.equals("0")){
            path = "0.";
        }else{
            String primaryKeyName = (String) dao.getPrimaryKeyName(tableName).get(0);
            StringBuffer querySql = new StringBuffer("select PATH FROM ").append(tableName).append(" WHERE ")
                    .append(primaryKeyName).append("=? ");
            Map<String,Object> map = this.getEntityDao().getByJdbc(querySql.toString(),new Object[]{parentId});
            path = (String) map.get("PATH");
        }
        List params = new ArrayList();
        params.add(path+"%");
        Enumeration paramEnu = request.getParameterNames();
        while (paramEnu.hasMoreElements()) {
            String paramName = (String) paramEnu.nextElement();
            if (paramName.startsWith("Q_")) {
                String paramValue = (String) request.getParameter(paramName);
                if (StringUtils.isNotEmpty(paramValue)) {
                    SqlFilter.addRequestQueryParam(paramName, paramValue, sql, params);
                }
            }
        }
        List<Map<String, Object>> deptlist=findBSDeptForWebSite();
        StringBuffer ids=new StringBuffer();
        for (int i = 0; i < deptlist.size(); i++) {
            if(i>0){
                ids.append(",");
            }
            ids.append("'").append(deptlist.get(i).get("DEPART_ID")).append("'");
        }
        if(ids.length()>0){
            sql.append(" and (tree_level=2 or (tree_level=3 and depart_id in ("+ids+")))");
        }else{
            sql.append(" and tree_level in (2,3) ");
        }
        
        if(tableName.equals("T_MEETING_FILETYPE")){
            sql.append(" ORDER BY TREE_SN DESC,CREATE_TIME DESC ");
        }else{
            sql.append(" ORDER BY TREE_SN ASC,CREATE_TIME DESC ");
        }
        return this.getEntityDao().findBySql(sql.toString(),params.toArray(),null);
    }
    @Override
    public List<Map<String, Object>> findMenuById(String tabletId,String code) {
        StringBuffer sql = new StringBuffer("select U.GROUP_ID,U.TABLET_ID,U.ITEM_NAME,U.ITEM_ID,U.ITEM_CODE ");
        sql.append(" FROM T_WSBS_DEPTGROUP U WHERE U.TABLET_ID IN　");
        sql.append(StringUtil.getValueArray(tabletId));
        if (StringUtils.isNotEmpty(code)) {
            sql.append(" AND U.ITEM_CATALOGCODE='"+code+"' ");
        }
        sql.append(" ORDER BY U.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    @Override
    public void saveMenuItem(String[] itemIds, String[] itemNames,
            String[] itemCodes, String tabletId, String catalogCode) {
     // 先清除掉数据
        StringBuffer delSql = new StringBuffer("delete ").append(" FROM T_WSBS_DEPTGROUP WHERE TABLET_ID=?");
        dao.executeSql(delSql.toString(), new Object[] { tabletId });
        for (int i = 0; i < itemIds.length; i++) {
            String itemId=itemIds[i];
            String itemName=itemNames[i];
            String itemCode=itemCodes[i];
            Map<String, Object> item  = new HashMap<String, Object>();
            item.put("STATUS",1);
            item.put("TABLET_ID", tabletId);
            item.put("ITEM_ID", itemId);
            item.put("item_name", itemName);
            item.put("item_code", itemCode);
            item.put("item_catalogcode",catalogCode);
            item.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dao.saveOrUpdate(item, "T_WSBS_DEPTGROUP",null);
        }
    }
    @Override
    public List<Map<String, Object>> findBSDeptForWebSite() {
        StringBuffer sql = new StringBuffer("select B.DEPART_ID,B.DEPART_NAME ");
        sql.append(" from T_WSBS_TABLET_DEPT B WHERE 1=1 ");
        sql.append(" ORDER BY B.SORT ASC");
        return dao.findBySql(sql.toString(), null, null);
    }
    @Override
    public List<Map<String, Object>> findBSDeptBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select U.DEPART_ID,U.DEPART_NAME,U.tree_sn,U.DEPART_CODE ");
        sql.append(" FROM t_msjw_system_department U WHERE tree_level in (3)　and status=1 ");
     // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND U.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }

        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @Override
    public List<Map<String, Object>> findWorkOfficeById() {
        StringBuffer sql = new StringBuffer("select U.DEPART_ID,U.DEPART_NAME,U.TREE_SN,U.DEPART_CODE ");
        sql.append(" FROM T_WSBS_TABLET_DEPT U  ");
        sql.append(" ORDER BY U.tree_sn DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    @Override
    public void saveWorkDept(String[] itemIds, String[] itemNames,
            String[] itemCodes, String[] treeSns) {
       // 先清除掉数据
        StringBuffer delSql = new StringBuffer("delete ").append(" FROM T_WSBS_TABLET_DEPT ");
        dao.executeSql(delSql.toString(), new Object[] {});
        for (int i = 0; i < itemIds.length; i++) {
            String itemId=itemIds[i];
            String itemName=itemNames[i];
            String itemCode=itemCodes[i];
            String treesn=treeSns[i];
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] {itemId});
            Map<String, Object> item  = new HashMap<String, Object>();
            item.put("tree_sn", treesn);
            item.put("DEPART_ID", itemId);
            item.put("DEPART_name", itemName);
            item.put("DEPART_code", itemCode);
            item.put("PATH", department.get("PATH"));
            item.put("TREE_LEVEL", department.get("TREE_LEVEL"));
            item.put("PARENT_ID", department.get("PARENT_ID"));
            item.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            //dao.saveOrUpdate(item, "T_WSBS_TABLET_DEPT",itemId);
            dao.saveOrUpdateForAssignId(item, "T_WSBS_TABLET_DEPT",itemId);
        }
    }
    @Override
    public Map<String, Object> findfrontQlqdList(String page, String rows,
            String sxxz, String busTypeIds) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));

        StringBuffer sql = new StringBuffer("select T.TABLET_ID,T.TABLET_NAME,T.DEPART_ID,T.ITEM_KIND ");
        sql.append(" from t_wsbs_table_menu T where status=1 ");
        if (StringUtils.isNotEmpty(sxxz)) {
            sql.append(" AND T.ITEM_KIND = ? ");
            params.add(sxxz);
        }
        if (StringUtils.isNotEmpty(busTypeIds)) {
            sql.append(" AND T.DEPART_ID IN (" + StringUtil.getValueArray(busTypeIds) + " )");
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < list.size(); i++) {
            List<Map<String, Object>> packlist = null;
            Map<String,Object> map=list.get(i);
            String tabletId=(String) map.get("TABLET_ID");
            StringBuffer psql = new StringBuffer(
                    "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
            psql.append("T.FWSXZT,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
            psql.append(" where T.FWSXZT='1' AND T.FWSXMXLB !='3' ");
            if (StringUtils.isNotEmpty(busTypeIds)) {
                psql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from t_wsbs_deptgroup ST WHERE ");
                psql.append("ST.TABLET_ID IN " + StringUtil.getValueArray(tabletId) + " )");
            }
            psql.append(" ORDER BY T.C_SN DESC");
            packlist = dao.findBySql(psql.toString(),null, null);
            map.put("itemlist", packlist);
            newlist.add(map);
        }
        
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", newlist);
        //mlist.put("list", list);
        return mlist;
        
    }
    @Override
    public List<Map<String, Object>> queryItemsByTablet(String itemIds) {
        //List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,");
        sql.append("T.FWSXZT from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM where 1=1 ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND T.SSBMBM in ").append(StringUtil.getValueArray(authDepCodes));
        }
        if (StringUtils.isNotEmpty(itemIds)) {
            sql.append(" AND T.ITEM_ID IN " + StringUtil.getValueArray(itemIds) + " ");
            return dao.findBySql(sql.toString(),null,null);
        }else{
            return null;
        }
    }
    @Override
    public List<Map<String, Object>> findCatalogBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select SC.CATALOG_ID,SC.CATALOG_NAME,SC.CATALOG_CODE,SC.DEPART_ID,SC.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,D1.DEPART_NAME as CHILD_DEPART  ");
        sql.append("from T_WSBS_SERVICEITEM_CATALOG SC  ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON SC.DEPART_ID=D.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=SC.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=SC.CHILD_DEPART_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND SC.STATUS=1 ");
        //获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        //非超管进行数据级别权限控制
        if(!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            //获取当前用户被授权的部门代码
            String authDepCodes  = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

}
