/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.dao.TabletMenuDao;
import net.evecom.platform.wsbs.service.BillRightService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-12 上午10:01:14
 */
@Service("billRightService")
public class BillRightServiceImpl extends BaseServiceImpl implements
        BillRightService {

    /**
     * 所引入的dao
     */
    @Resource
    private TabletMenuDao dao;
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
     * 描述   获取权利清单（目录）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBillCatalogByFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.ITEM_CATALOGCODE,T.BILL_ID,D.DEPART_NAME, ");
        sql.append("T.BILL_NAME,DIC.DIC_NAME AS ITEM_KIND,T.CREATE_TIME, ");
        sql.append("T.ITEM_NUM,T.IS_PUBLIC,DIC1.DIC_NAME AS FTA_FLAG FROM T_WSBS_RIGHTBILL_CATALOG T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = T.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=T.ITEM_KIND ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.FTA_FLAG ");
        sql.append("WHERE DIC.TYPE_CODE='ServiceItemXz' AND DIC1.TYPE_CODE='isFta' AND T.STATUS=1 ");

        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述   删除目录清单
     * @author Danto Huang
     * @created 2016-9-13 上午9:16:53
     * @param selectColNames
     */
    public String removeCatalog(String selectColNames){
        String[] ids = selectColNames.split(",");
        String returnMsg = null;
        Map<String ,Object> catalog = new HashMap<String, Object>();
        catalog.put("STATUS", 0);
        String sql = "update T_WSBS_RIGHTBILL_ITEM t set t.STATUS=0 where t.CATALOG_BILL_ID=?";
        
        for(int i=0;i<ids.length;i++){
            Map<String, Object> catalogBill = this.getByJdbc(
                    "T_WSBS_RIGHTBILL_CATALOG", new String[] { "BILL_ID" },
                    new Object[] { ids[i] });
            if(catalogBill.get("ITEM_CATALOGCODE")!=null){
                String querySql = "select t.item_code from T_WSBS_SERVICEITEM t where t.CATALOG_CODE=? and t.FWSXZT<>3";
                List<Map<String, Object>> items = dao.findBySql(querySql,
                        new Object[] { catalogBill.get("ITEM_CATALOGCODE") },
                        null);
                if(items!=null&&items.size()>0){
                    returnMsg = "“" + catalogBill.get("BILL_NAME")
                            + "”已绑定目录和事项，请将部门服务事项中对应的目录和事项删除或取消后，再进行清单删除";
                }else{
                    returnMsg = "“" + catalogBill.get("BILL_NAME")
                            + "”已绑定目录，请将部门服务事项中对应的目录删除后，再进行清单删除";
                }
                break;
            }else{
                dao.executeSql(sql, new Object[]{ids[i]});
                dao.saveOrUpdate(catalog, "T_WSBS_RIGHTBILL_CATALOG", ids[i]);
            }
                                
        }
        return returnMsg;
    }

    /**
     * 
     * 描述   获取权利清单（事项）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillItemByFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.BILL_ID,T.BILL_NAME,T.CREATE_TIME,DIC.DIC_NAME AS FTA_FLAG,T.IS_PUBLIC ");
        sql.append("FROM T_WSBS_RIGHTBILL_ITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=T.FTA_FLAG ");
        sql.append("WHERE T.STATUS=1 AND DIC.TYPE_CODE='isFta'");

        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }    

    /**
     * 
     * 描述   子项数量更新
     * @author Danto Huang
     * @created 2016-9-13 下午1:57:52
     * @param billCatalogId
     */
    public void updateItemNum(String billCatalogId){
        String sql = "select count(*) count from T_WSBS_RIGHTBILL_ITEM t where t.CATALOG_BILL_ID=?";
        String count = dao.getByJdbc(sql, new Object[]{billCatalogId}).get("count").toString();
        Map<String ,Object> variables = new HashMap<String, Object>();
        variables.put("ITEM_NUM", count+"个");
        this.saveOrUpdate(variables, "T_WSBS_RIGHTBILL_CATALOG", billCatalogId);
    }
    
    /**
     * 
     * 描述   子项删除
     * @author Danto Huang
     * @created 2016-9-13 下午2:09:42
     * @param selectColNames
     */
    public String removeItems(String selectColNames){
        String[] ids = selectColNames.split(",");
        String returnMsg = null;
        for(int i=0;i<ids.length;i++){
            Map<String,Object> itemBill= this
                    .getByJdbc("T_WSBS_RIGHTBILL_ITEM",
                            new String[] { "BILL_ID" }, new Object[] { ids[i] });
            String billCatalogId = itemBill.get("CATALOG_BILL_ID").toString();
            if(itemBill.get("SERVICEITEM_CODE")!=null){
                String itemCode = itemBill.get("SERVICEITEM_CODE").toString();
                Map<String, Object> item = this
                        .getByJdbc("T_WSBS_SERVICEITEM",
                                new String[] { "ITEM_CODE" },
                                new Object[] { itemCode });
                if (!item.get("FWSXZT").equals("3")) {
                    returnMsg = "“" + itemBill.get("BILL_NAME")
                            + "”已绑定事项，请将部门服务事项中对应的事项删除或取消后，再进行清单删除";
                    break;
                }else{
                    this.remove("T_WSBS_RIGHTBILL_ITEM", new String[]{"BILL_ID"}, new Object[]{ids[i]});
                    this.updateItemNum(billCatalogId);
                }
            }else{
                this.remove("T_WSBS_RIGHTBILL_ITEM", new String[]{"BILL_ID"}, new Object[]{ids[i]});
                this.updateItemNum(billCatalogId);
            }
        }
        return returnMsg;
    }
    
    /**
     * 
     * 描述   获取未绑定权利清单（目录）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBillCatalogForBind(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.BILL_ID,D.DEPART_NAME,T.BILL_NAME,DIC.DIC_NAME AS ITEM_KIND,T.CREATE_TIME, ");
        sql.append("T.ITEM_NUM,T.DEPART_ID,T.ITEM_KIND AS SXXZ,T.FTA_FLAG FROM T_WSBS_RIGHTBILL_CATALOG T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = T.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=T.ITEM_KIND ");
        sql.append("WHERE DIC.TYPE_CODE='ServiceItemXz' AND T.STATUS=1 AND T.ITEM_CATALOGCODE IS NULL ");

        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述   获取未绑定权利清单（事项）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBillItemForBind(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.BILL_ID,T.DEPART_ID,T.BILL_NAME,C.BILL_NAME AS CATALOG_NAME,C.ITEM_CATALOGCODE,");
        sql.append("T.RZBSDTFS,");
        sql.append("D.DEPART_NAME,D.DEPART_CODE,C.ITEM_KIND,T.FTA_FLAG FROM T_WSBS_RIGHTBILL_ITEM T ");
        sql.append("LEFT JOIN T_WSBS_RIGHTBILL_CATALOG C ON C.BILL_ID = T.CATALOG_BILL_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = T.DEPART_ID ");
        sql.append("WHERE T.STATUS=1 AND T.SERVICEITEM_CODE IS NULL ");

        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }

        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public Map<String, Object> findfrontQzqdList(String page, String rows, String keyword, String dicCodes,
            String departId, String sfzx) {
        // TODO Auto-generated method stub
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();

        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.BILL_ID,T.DEPART_ID,D.DEPART_NAME,T.BILL_NAME,DIC.DIC_NAME AS ITEM_KIND,T.CREATE_TIME, ");
        sql.append("T.ITEM_NUM,T.IS_PUBLIC,DIC1.DIC_NAME AS FTA_FLAG FROM T_WSBS_RIGHTBILL_CATALOG T ");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON T.ITEM_CATALOGCODE = C.CATALOG_CODE ");
        sql.append("  LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON C.CHILD_DEPART_ID = D1.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = T.DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=T.ITEM_KIND ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.FTA_FLAG ");
        sql.append("WHERE DIC.TYPE_CODE='ServiceItemXz' AND DIC1.TYPE_CODE='isFta' AND T.STATUS=1 AND T.IS_PUBLIC='1'");
        //限制发布库及存在省网编码
//        sql.append(" AND (select count(*) from T_WSBS_SERVICEITEM s where s.catalog_id = c.catalog_id  ");
//        sql.append(" and s.swb_item_code is not null AND s.FWSXZT = '1') > 0  ");
        sql.append(" AND (select count(*) from T_WSBS_RIGHTBILL_ITEM i where i.catalog_bill_id = t.bill_id  ");
        sql.append(" and i.serviceitem_code is not null  AND i.IS_PUBLIC ='1'  ");
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND i.BILL_NAME like ? ");
            params.add("%" + keyword + "%");
        }
        sql.append(" )>0 ");
        if (StringUtils.isNotEmpty(dicCodes)) {
            String dicCode = StringUtil.getValueArray(dicCodes);
            sql.append(" AND T.ITEM_KIND in " + dicCode);
        }
        if (StringUtils.isNotEmpty(departId)) {
            String dept = StringUtil.getValueArray(departId);
            sql.append(" AND T.DEPART_ID in  " + dept);
        }
        sql.append(" ORDER BY  D1.TREE_SN ASC, C.C_SN DESC,T.CREATE_TIME DESC,T.BILL_ID desc ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        for (Map<String, Object> map : list) {
            String catalogId = map.get("BILL_ID") == null ? "" : map.get("BILL_ID").toString();
            if (StringUtils.isNotEmpty(catalogId)) {
                List<Map<String, Object>> itemList = findBillItemByList(catalogId, sfzx, keyword);
                if (null != itemList && itemList.size() > 0) {
                    map.put("itemList", itemList);
                }
            }
            map.put("pageNum", page);
            map.put("limitNum", rows);
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    @Override
    public Map<String, Object> findfrontRzswmlList(String page, String rows, String keyword, String dicCodes,
            String departId, String sfzx) {
        // TODO Auto-generated method stub
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();

        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT c.catalog_code,c.catalog_id, c.depart_id, D.DEPART_NAME, c.catalog_name, ");
        sql.append(" DIC.DIC_NAME  AS ITEM_KIND, c.create_time, t.item_num,T.IS_PUBLIC ");
        sql.append(" FROM T_WSBS_SERVICEITEM_CATALOG c ");
        sql.append(" LEFT JOIN T_WSBS_RIGHTBILL_CATALOG t ON T.ITEM_CATALOGCODE = C.CATALOG_CODE ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON C.CHILD_DEPART_ID = D1.DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = c.depart_id ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE = c.sxxz ");
        sql.append(" WHERE DIC.TYPE_CODE = 'ServiceItemXz' ");
        sql.append(" AND (select count(*) from T_WSBS_SERVICEITEM s where s.catalog_code = c.catalog_code ");
        sql.append(" and s.swb_item_code is not null AND s.FWSXZT = '1' ");
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND s.item_name like ? ");
            params.add("%" + keyword + "%");
        }
        sql.append(" )>0 ");
        if (StringUtils.isNotEmpty(dicCodes)) {
            String dicCode = StringUtil.getValueArray(dicCodes);
            sql.append(" AND c.sxxz in " + dicCode);
        }
        if (StringUtils.isNotEmpty(departId)) {
            String dept = StringUtil.getValueArray(departId);
            sql.append(" AND c.DEPART_ID in  " + dept);
        }
        sql.append(" ORDER BY  D1.TREE_SN ASC, C.C_SN DESC,c.CREATE_TIME DESC,c.catalog_id desc ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        for (Map<String, Object> map : list) {
            String catalogCode = map.get("catalog_code") == null ? "" : map.get("catalog_code").toString();
            if (StringUtils.isNotEmpty(catalogCode)) {
                List<Map<String, Object>> itemList = findRzswItemByList(catalogCode, sfzx, keyword);
                if (null != itemList && itemList.size() > 0) {
                    map.put("itemList", itemList);
                }
            }
            map.put("pageNum", page);
            map.put("limitNum", rows);
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    /**
     * 新版权责清单前台展示
     * @param page
     * @param rows
     * @param keyword
     * @param dicCodes
     * @param departId
     * @param sfzx
     * @return
     */
    @Override
    public Map<String, Object> findRightlList(String page, String rows, String keyword, String dicCodes,
                                                   String departId, String sfzx) {
        // TODO Auto-generated method stub
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();

        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT R.RIGHT_NAME,COUNT(*),R.IMPL_DEPART_ID  ");
        sql.append(" FROM T_SMOGA_BILLOFRIGHTS R ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D  ON D.DEPART_ID = R.IMPL_DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC  ON DIC.DIC_CODE = R.RIGHT_TYPE ");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM s ON S.RIGHT_ID=R.RIGHT_ID ");
        sql.append(" WHERE DIC.TYPE_CODE = 'ServiceItemXz'  ");
        sql.append(" AND R.STATUS= '1' ");
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND (R.RIGHT_NAME like ? or R.SUBITEM_NAME LIKE ?)  ");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        if (StringUtils.isNotEmpty(dicCodes)) {
            String dicCode = StringUtil.getValueArray(dicCodes);
            sql.append(" AND R.RIGHT_TYPE in " + dicCode);
        }
        if (StringUtils.isNotEmpty(departId)) {
            String dept = StringUtil.getValueArray(departId);
            sql.append(" AND R.IMPL_DEPART_ID in  " + dept);
        }
        sql.append("   GROUP BY R.RIGHT_NAME,R.IMPL_DEPART_ID ORDER BY R.RIGHT_NAME ASC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        //查找权责子项
        for (Map<String, Object> map : list) {
            String rightName = map.get("RIGHT_NAME") == null ? "" : map.get("RIGHT_NAME").toString();
            String implDepartId=String.valueOf(map.get("IMPL_DEPART_ID"));
            if (StringUtils.isNotEmpty(rightName)) {
                List<Map<String, Object>> itemList = findSubRightItem(rightName,implDepartId);
                if (null != itemList && itemList.size() > 0) {
                    map.put("itemList", itemList);
                }
            }
            map.put("pageNum", page);
            map.put("limitNum", rows);
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    /**
     * 获取权责清单详细信息
     * @param rightId
     * @return
     */
    public Map<String,Object> getRightDetail(String rightId){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT R.*,DIC1.DIC_NAME AS DICTYPECODE,S.ITEM_NAME,S.ITEM_CODE, ");
        sql.append(" DIC2.DIC_NAME AS DICLEVEL ");
        sql.append("FROM T_SMOGA_BILLOFRIGHTS R ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON ");
        sql.append(" R.RIGHT_TYPE=DIC1.DIC_CODE ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S  ON R.RIGHT_ID=S.RIGHT_ID ");
        sql.append("  left join t_msjw_system_dictionary dic2  on r.exercise_level=dic2.dic_code ");
        sql.append(" where dic1.type_code='ServiceItemXz' ");
        sql.append("  and (dic2.type_code = 'xzcj' or dic2.type_code is null) ");
        sql.append(" and r.right_id=?");
        Map<String,Object> rightItem=dao.getByJdbc(sql.toString(),new Object[]{rightId});
        return rightItem;
    }
    /**
     * 查找权责子项
     * @param rightName
     * @param implDepartId
     * @return
     */
    public List<Map<String,Object>> findSubRightItem(String rightName,String implDepartId){
        StringBuffer sql=new StringBuffer();
        sql.append("select * from T_SMOGA_BILLOFRIGHTS r");
        sql.append(" where r.right_name=? and r.IMPL_DEPART_ID=? and r.STATUS=1 ");
        List<Map<String,Object>> subRightItems=dao.findBySql(sql.toString(),
                new Object[]{rightName,implDepartId},null);
        return subRightItems;
    }
    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年10月8日 上午9:45:36
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBillItemByList(String catalogId,String isZx,String keyword) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.BILL_ID,T.BILL_NAME,T.CREATE_TIME,DIC.DIC_NAME AS FTA_FLAG, ");
        sql.append(" T.IS_PUBLIC,T.SERVICEITEM_CODE,ITEM.RZBSDTFS ");
        sql.append(" FROM T_WSBS_RIGHTBILL_ITEM T ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=T.FTA_FLAG ");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM ITEM on T.SERVICEITEM_CODE = ITEM.ITEM_CODE ");
        sql.append(" WHERE T.STATUS=1 AND DIC.TYPE_CODE='isFta'  AND T.IS_PUBLIC ='1' ");
        sql.append(" AND T.SERVICEITEM_CODE  IS NOT NULL ");
        sql.append(" AND ITEM.SWB_ITEM_CODE  IS NOT NULL ");
        sql.append(" AND ITEM.FWSXZT  = '1' ");
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND T.BILL_NAME like ? ");
            params.add("%" + keyword + "%");
        }
        if (StringUtils.isNotEmpty(isZx)) {
            if(isZx.equals("1")){
                sql.append(" AND ITEM.RZBSDTFS !='in01' " );
            }
        }
        if (StringUtils.isNotEmpty(catalogId)) {
            sql.append(" and T.CATALOG_BILL_ID=? ");
            params.add(catalogId);
        }
        
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年10月8日 上午9:45:36
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findRzswItemByList(String catalogId,String isZx,String keyword) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT s.item_id,s.item_name,s.create_time,s.item_code,s.RZBSDTFS ");
        sql.append(" FROM T_WSBS_SERVICEITEM s WHERE s.SWB_ITEM_CODE IS NOT NULL AND s.FWSXZT = '1' ");
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND s.item_name like ? ");
            params.add("%" + keyword + "%");
        }
        if (StringUtils.isNotEmpty(isZx)) {
            if(isZx.equals("1")){
                sql.append(" AND s.RZBSDTFS !='in01' " );
            }
        }
        if (StringUtils.isNotEmpty(catalogId)) {
            sql.append(" and s.catalog_code=? ");
            params.add(catalogId);
        }
        
        sql.append(" ORDER BY s.CREATE_TIME DESC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    @Override
    public Map<String, Object> findfrontCount(String keyword, String dicCodes, String departId,String isOn) {
        // TODO Auto-generated method stub 
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(*) as total ");
        sql.append(" FROM T_WSBS_RIGHTBILL_ITEM T ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=T.FTA_FLAG ");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM ITEM on T.SERVICEITEM_CODE = ITEM.ITEM_CODE ");
        sql.append(" WHERE T.STATUS=1 AND DIC.TYPE_CODE='isFta' AND T.IS_PUBLIC ='1' ");
        sql.append(" AND T.SERVICEITEM_CODE  IS NOT NULL ");

        if (StringUtils.isNotEmpty(isOn)) {
            if(isOn.equals("1")){
                sql.append(" AND ITEM.RZBSDTFS !='in01' AND ITEM.RZBSDTFS !='in02'" );
            }
        }
        sql.append(" and T.CATALOG_BILL_ID IN (");
        sql.append(" SELECT C.BILL_ID FROM T_WSBS_RIGHTBILL_CATALOG C WHERE  C.STATUS=1 "); 
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND C.BILL_NAME like ? ");
            params.add("%" + keyword + "%");
        }
        if (StringUtils.isNotEmpty(dicCodes)) {
            String dicCode = StringUtil.getValueArray(dicCodes);
            sql.append(" AND C.ITEM_KIND in " + dicCode);
        }

        if (StringUtils.isNotEmpty(departId)) {
            String dept = StringUtil.getValueArray(departId);
            sql.append(" AND C.DEPART_ID in  " + dept);
        }
        sql.append(" ) ORDER BY T.CREATE_TIME DESC ");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), params.toArray());
        return map;
    }
    
    @Override
    public Map<String, Object> findfrontCountNew(String keyword, String dicCodes, String departId,String isOn) {
        // TODO Auto-generated method stub 
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(*) as total FROM T_WSBS_SERVICEITEM s ");
        sql.append(" WHERE s.FWSXZT = '1' ");
//        sql.append(" AND s.swb_item_code  IS NOT NULL ");

        if (StringUtils.isNotEmpty(isOn)) {
            if(isOn.equals("1")){
                sql.append(" AND s.RZBSDTFS !='in01' AND s.RZBSDTFS !='in02'" );
            }
        }
        sql.append(" and s.catalog_code IN (");
        sql.append(" SELECT C.Catalog_Code FROM T_WSBS_SERVICEITEM_CATALOG C WHERE  C.STATUS=1 "); 
        if (StringUtils.isNotEmpty(keyword)) {
            sql.append(" AND C.catalog_name like ? ");
            params.add("%" + keyword + "%");
        }
        if (StringUtils.isNotEmpty(dicCodes)) {
            String dicCode = StringUtil.getValueArray(dicCodes);
            sql.append(" AND C.sxxz in " + dicCode);
        }

        if (StringUtils.isNotEmpty(departId)) {
            String dept = StringUtil.getValueArray(departId);
            sql.append(" AND C.DEPART_ID in  " + dept);
        }
        sql.append(" ) ORDER BY s.CREATE_TIME DESC ");
        Map<String, Object> map = dao.getByJdbc(sql.toString(), params.toArray());
        return map;
    }

    @Override
    public List<Map<String, Object>> findBillItemById(String id) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_RIGHTBILL_ITEM t where t.catalog_bill_id = ?  ");
        params.add(id);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                params.toArray(), null);
        return list;
    }
}
