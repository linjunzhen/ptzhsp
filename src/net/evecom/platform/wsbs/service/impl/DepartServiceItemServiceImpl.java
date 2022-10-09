/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.platform.hflow.service.ExeDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.dao.ServiceItemDao;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-18 下午3:30:25
 */
@Service("departServiceItemService")
public class DepartServiceItemServiceImpl extends BaseServiceImpl implements
        DepartServiceItemService {

    /**
     * 所引入的dao
     */
    @Resource
    private ServiceItemDao dao;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     *
     */
    @Resource
    private ExeDataService exeDataService;
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
     * 描述   获取事项列表
     * @author Danto Huang
     * @created 2016-9-18 下午3:27:36
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        String fwsxzt=sqlFilter.getQueryParams().get("Q_T.FWSXZT_EQ")==null?"":
            sqlFilter.getQueryParams().get("Q_T.FWSXZT_EQ").toString();
        StringBuffer sql = new StringBuffer("");
        sql.append("select T.RIGHT_ID,T.ITEM_ID,T.ITEM_NAME,T.CNQXGZR,T.THYJ,");
        sql.append("T.AUDITING_NAMES,T.BACKOR_NAME,t.IS_FROM_SWB,");
        //以省网办编码为主
        sql.append("CASE WHEN length(T.SWB_ITEM_CODE)>0 THEN T.SWB_ITEM_CODE ELSE T.ITEM_CODE END ITEM_CODE,");
        sql.append("OP1.FILEATTACH_PATH ,OP1.FILE_NAME,OP1.THYJ AS PTHYJ,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,T.FWSXZT");
//        sql.append(",T.SWB_ITEM_CODE,T.APPLY_URL,C.CATALOG_NAME");
        sql.append(",T.SWB_ITEM_CODE,T.APPLY_URL");
        sql.append(", (SELECT L1.operate_time FROM (  select L.operate_time,L.item_id ");
        sql.append("  from T_WSBS_SERVICEITEM_LOG L order by L.operate_time desc ) L1");
        sql.append("  WHERE L1.item_id = T.ITEM_ID and ROWNUM =1) AS operate_time");
        sql.append(" from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN VIEW_WSBS_SERVICEITEM_LOG QL ON T.ITEM_ID=QL.ITEM_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION OP1 WHERE OP1.FWSXZT='");
        sql.append(fwsxzt).append("') OP1 ON OP1.ITEM_ID=T.ITEM_ID ");
        /*2019/11/15修改缺失事项性质和行政许可不能查询到的bug  Madison You*/
        sql.append("LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY  ");
        sql.append(" WHERE TYPE_CODE='ServiceItemXz') DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE='ServiceItemType') DIC2 ON DIC2.DIC_CODE=T.SXLX ");
//        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON C.CATALOG_CODE = T.CATALOG_CODE ");
//        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=C.CHILD_DEPART_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=T.ZBCS_ID ");
        sql.append("WHERE 1=1 ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        Object flag = sqlFilter.getQueryParams().get("Q_T.FWSXZT_EQ");
        // 非超管进行数据级别权限控制
        if (!(curUser.getResKeys().equals(SysUser.ADMIN_RESKEY) || Objects.equals(flag,"-1"))) {
            // 获取当前用户被授权的部门代码
            /*如果事项处在发布库*/
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D2.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        //以省网办编码为主
        if(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE")!=null
                &&!"".equals(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))){
            String item_code=sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            sqlFilter.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述：事项汇总
     * @author Water Guo
     * @created 2017-3-5 下午7:31:11
     * @param sqlFilter
     * @return
     * 
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        String ckcs = sqlFilter.getQueryParams().get("Q_T.CKCS_EQ")==null?
                "":sqlFilter.getQueryParams().get("Q_T.CKCS_EQ").toString();
        StringBuffer sql = new StringBuffer("select *   " );
        sql.append(" from VIEW_ALL_SERVICEITEM T WHERE  1=1   ");
        if (StringUtils.isNotEmpty(ckcs) ) {
            sql.append(" and (t.ckcs_1 = " + ckcs + " or t.ckcs_2 = " + ckcs
                    + " or t.ckcs_3 = " + ckcs + " or t.ckcs_4 = " + ckcs
                    + ") ");
            sqlFilter.removeFilter("Q_T.CKCS_EQ");
        }
        //以省网办编码为主
        if(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE")!=null
                &&!"".equals(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))){
            String item_code=sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            sqlFilter.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述    更新或者保存服务项目
     * @author Danto Huang
     * @created 2016-9-20 下午2:01:40
     * @param serviceItem
     * @return
     */
    public String saveOrUpdateCascade(Map<String, Object> serviceItem) {
        String entityId = (String) serviceItem.get("ITEM_ID");
        String itemId = dao.saveOrUpdate(serviceItem, "T_WSBS_SERVICEITEM",
                entityId);
        // 获取勾选主题类别IDS 
        if(serviceItem.get("currentStep")!=null){
            String currentStep=(String) serviceItem.get("currentStep");
            if("1".equals(currentStep)){
                String typeIds = serviceItem.get("BUS_TYPEIDS").toString();
                if(serviceItem.get("SSBMBM")!=null){
                    String ssbmbm = (String) serviceItem.get("SSBMBM");
                    String departId = "";
                    Map<String, Object> busType = busTypeService.getByJdbc("T_WSBS_BUSTYPE",
                            new String[] { "TYPE_CODE" }, new Object[] { ssbmbm });
                    if (busType!=null) {
                        departId = busType.get("TYPE_ID").toString();
                    }
                    typeIds += departId;
                }
                if (StringUtils.isNotEmpty(typeIds)) {
                    busTypeService.saveBusTypeItem(typeIds.split(","), itemId);
                }
            }
            
        }
        return itemId;
    }

    /**
     * 
     * 描述   删除服务事项
     * @author Danto Huang
     * @created 2016-9-20 下午3:40:43
     * @param selectColNames
     */
    public void removeCascade(String selectColNames){
        this.remove("T_WSBS_SERVICEITEM_TYPE", "ITEM_ID", selectColNames.split(","));
        this.remove("T_WSBS_PREAUDITER", "ITEM_ID", selectColNames.split(","));
        this.remove("T_WSBS_SERVICEITEM_MATER", "ITEM_ID", selectColNames.split(","));
        this.remove("T_WSBS_SERVICEITEM_READ", "ITEM_ID", selectColNames.split(","));
        this.remove("T_WSBS_SERVICEITEM_TYPE", "ITEM_ID", selectColNames.split(","));
        String sql = "update T_WSBS_RIGHTBILL_ITEM t set t.serviceitem_code='' where t.serviceitem_code=?";
        String[] itemIds = selectColNames.split(",");
        for(int i=0;i<itemIds.length;i++){
            String itemCode = this
                    .getByJdbc("T_WSBS_SERVICEITEM",
                            new String[] { "ITEM_ID" },
                            new Object[] { itemIds[i] }).get("ITEM_CODE")
                    .toString();
            dao.executeSql(sql, new Object[]{itemCode});
        }
        this.remove("T_WSBS_SERVICEITEM", "ITEM_ID", selectColNames.split(","));
    }
    
    /**
     * 
     * 描述   特殊环节列表
     * @author Danto Huang
     * @created 2016-9-21 下午4:40:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSpecialLink(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_SERVICEITEM_LINK t";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据事项ID或者特殊环节列表
     * @author Rider Chen
     * @created 2017年5月2日 下午2:17:35
     * @param itemId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getSpecialLink(String itemId){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_SERVICEITEM_LINK t where t.ITEM_ID = ? order by t.CREATE_TIME asc";
        params.add(itemId);
        List<Map<String, Object>> list = dao.findBySql(sql, params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述   流程环节审核人设置数据
     * @author Danto Huang
     * @created 2016-9-28 下午3:02:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getDefNode(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_SERVICEITEM_AUDITER t "
                + "left join T_WSBS_SERVICEITEM s on s.item_id=t.item_id where s.BDLCDYID=t.def_id ";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述   保存流程审核节点
     * @author Danto Huang
     * @created 2016-9-28 下午4:03:05
     * @param itemId
     * @param defId
     */
    public void saveItemDefNode(String itemId,String defId){
        this.remove("T_WSBS_SERVICEITEM_AUDITER", new String[]{"ITEM_ID","DEF_ID"}, new Object[]{itemId,defId});
        String sql = "select t.node_name,t.node_key,t.node_type from JBPM6_FLOWNODE t where t.def_id=? "
                + "and t.flow_version=(select max(d.version) from jbpm6_flowdef d where d.def_id=?) "
                + "and t.node_type<>'decision' and t.node_type<>'parallel' and t.node_type<>'join' "
                + "group by t.node_name,t.node_key,t.node_type order by t.node_key desc ";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{defId,defId}, null);
        for(Map<String,Object> node : list){
            String entityId = null;
            Map<String, Object> old = this.getByJdbc(
                    "T_WSBS_SERVICEITEM_AUDITER", new String[] { "ITEM_ID",
                            "DEF_ID","NODE_NAME" }, new Object[] { itemId, defId,node.get("NODE_NAME") });
            if(old!=null){
                entityId = old.get("RECORD_ID").toString();
            }
            Map<String,Object> audit = new HashMap<String, Object>();
            audit.put("ITEM_ID", itemId);
            audit.put("DEF_ID", defId);
            audit.put("NODE_NAME", node.get("NODE_NAME"));
            audit.put("NODE_KEY", node.get("NODE_KEY"));
            audit.put("NODE_TYPE", node.get("NODE_TYPE"));
            dao.saveOrUpdate(audit,"T_WSBS_SERVICEITEM_AUDITER", entityId);
        }
    }
    /**
     * 
     * 描述：获取流程点
     * @author Water Guo
     * @created 2017-4-9 下午8:55:01
     * @param defId
     * @return
     * @see net.evecom.platform.wsbs.service.DepartServiceItemService#getNodeNamesByDefid(java.lang.String)
     */
    public List<Map<String, Object>> getNodeNamesByDefid(String defId){
        String sql = "select t.node_name,t.node_key,t.node_type from JBPM6_FLOWNODE t where t.def_id=? "
                + "and t.flow_version=(select max(d.version) from jbpm6_flowdef d where d.def_id=?) "
                + "and t.node_type<>'decision' and t.node_type<>'parallel' and t.node_type<>'join' "
                + "group by t.node_name,t.node_key,t.node_type order by t.node_key desc ";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{defId,defId}, null);
        return list;
    }
    /**
     * 
     * 描述    获取审核列表页
     * @author Danto Huang
     * @created 2016-10-10 下午1:31:01
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByAuditingSqlFilter(SqlFilter sqlFilter) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_NAME,T.CNQXGZR,T.AUDITING_NAMES,T.BACKOR_NAME,");
        //以省网办编码为主
        sql.append("CASE WHEN length(T.swb_item_code) > 0 THEN t.swb_item_code ELSE  t.item_code END ITEM_CODE,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,T.FWSXZT ");
        sql.append(",OP1.fileattach_path ,OP1.file_name,OP1.thyj,OP1.USERNAME AS USERNAME1");
        sql.append(",OP2.THYJ AS THYJ2,OP2.CREATE_TIME AS THSJ2,OP2.USERNAME AS USERNAME2");
        sql.append(",OP2.fileattach_path as fileattach_path2,OP2.file_name as file_name2");
        sql.append(",OP3.THYJ AS THYJ3,OP3.CREATE_TIME AS THSJ3,OP3.USERNAME AS USERNAME3");
        sql.append(",OP3.fileattach_path as fileattach_path3,OP3.file_name as file_name3");
        sql.append(", (SELECT L1.operate_time FROM (  select L.operate_time,L.item_id ");
        sql.append("  from T_WSBS_SERVICEITEM_LOG L order by L.operate_time desc ) L1");
        sql.append("  WHERE L1.item_id = T.ITEM_ID and ROWNUM =1) AS operate_time");
        sql.append(" from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P1 WHERE P1.FWSXZT='2') OP1 ");
        sql.append("ON T.ITEM_ID=OP1.ITEM_ID ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P2 WHERE P2.FWSXZT='4') OP2 ");
        sql.append("ON T.ITEM_ID=OP2.ITEM_ID ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P3 WHERE P3.FWSXZT='5') OP3 ");
        sql.append("ON T.ITEM_ID=OP3.ITEM_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
//        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON C.CATALOG_CODE = T.CATALOG_CODE ");
//        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=C.CHILD_DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=T.ZBCS_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND (T.FWSXZT='2' or T.FWSXZT='5') ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D2.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        //以省网办编码为主
        if(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE")!=null
                &&!"".equals(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))){
            String item_code=sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            sqlFilter.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        list = dao.findBySql(exeSql, params.toArray(),
                sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述：获取拟发布库的列表
     * @author Water Guo
     * @created 2017-3-22 上午9:51:37
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPrePublicSqlFilter(SqlFilter sqlFilter) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_NAME,T.CNQXGZR,T.AUDITING_NAMES,T.BACKOR_NAME,");
        //以省网办编码为主
        sql.append("CASE WHEN length(T.swb_item_code) > 0 THEN t.swb_item_code ELSE  t.item_code END ITEM_CODE,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,T.FWSXZT ");
        sql.append(",OP1.fileattach_path ,OP1.file_name,OP1.thyj,OP1.USERNAME AS USERNAME1");
        sql.append(",OP2.THYJ AS THYJ2,OP2.CREATE_TIME AS THSJ2,OP2.USERNAME AS USERNAME2");
        sql.append(",OP3.THYJ AS THYJ3,OP3.CREATE_TIME AS THSJ3,OP3.USERNAME AS USERNAME3");
        sql.append(",OP3.fileattach_path as fileattach_path3,OP3.file_name as file_name3");
        sql.append(", (SELECT L1.operate_time FROM (  select L.operate_time,L.item_id ");
        sql.append("  from T_WSBS_SERVICEITEM_LOG L order by L.operate_time desc ) L1");
        sql.append("  WHERE L1.item_id = T.ITEM_ID and ROWNUM =1) AS operate_time");
        sql.append(" from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P1 WHERE P1.FWSXZT='8') OP1 ");
        sql.append("ON T.ITEM_ID=OP1.ITEM_ID ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P2 WHERE P2.FWSXZT='4') OP2 ");
        sql.append("ON T.ITEM_ID=OP2.ITEM_ID ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P3 WHERE P3.FWSXZT='2') OP3 ");
        sql.append("ON T.ITEM_ID=OP3.ITEM_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
//        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON C.CATALOG_CODE = T.CATALOG_CODE ");
//        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=C.CHILD_DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=T.ZBCS_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND (T.FWSXZT='8') ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D2.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        // 以省网办编码为主
        if (sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE") != null
                && !"".equals(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))) {
            String item_code = sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            sqlFilter.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        list = dao.findBySql(exeSql, params.toArray(),
                sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    获取发布库列表
     * @author Danto Huang
     * @created 2016-10-10 下午2:14:24
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_NAME,T.CNQXGZR,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,T.RECEIVEDAY,");
        //以省网办编码为主
        sql.append("OP1.FILEATTACH_PATH,OP1.FILE_NAME,OP1.THYJ,");
        sql.append("OP2.FILEATTACH_PATH AS FILEATTACH_PATH2,OP2.FILE_NAME AS FILE_NAME2,");
        sql.append("OP2.THYJ AS NFBYJ");
        sql.append(",OP3.THYJ AS THYJ3,OP3.CREATE_TIME AS THSJ3,OP3.USERNAME AS USERNAME3");
        sql.append(",OP3.fileattach_path as fileattach_path3,OP3.file_name as file_name3");
        sql.append(",CASE WHEN length(T.swb_item_code) > 0 THEN t.swb_item_code ELSE  t.item_code END ITEM_CODE,");
        sql.append("D.DEPART_NAME,D2.DEPART_NAME as DEPART_NAMEC,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,T.FWSXZT,J.DEF_KEY,L.OPERATE_TIME");
        sql.append(" from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P1 WHERE P1.FWSXZT='8') OP1 ");
        sql.append("ON T.ITEM_ID=OP1.ITEM_ID ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P2 WHERE P2.FWSXZT='1') OP2 ");
        sql.append("ON T.ITEM_ID=OP2.ITEM_ID ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION P3 WHERE P3.FWSXZT='2') OP3 ");
        sql.append("ON T.ITEM_ID=OP3.ITEM_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN VIEW_WSBS_SERVICEITEM_LOG L ON T.ITEM_ID=L.ITEM_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
//        sql.append(" LEFT JOIN (select CATALOG_CODE,D1.DEPART_ID,D1.DEPART_NAME from t_wsbs_serviceitem_catalog C ");
//        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID) DC ");
//        sql.append("ON DC.CATALOG_CODE=T.CATALOG_CODE ");
//        sql.append(" LEFT JOIN t_wsbs_serviceitem_catalog C ON C.CATALOG_CODE=T.CATALOG_CODE ");
//        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=C.CHILD_DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=T.ZBCS_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1'");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D2.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        //以省网办编码为主
        if(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE")!=null
                &&!"".equals(sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))){
            String item_code=sqlFilter.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            sqlFilter.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月8日 下午4:21:56
     * @param itemCodes
     * @return
     */
    public List<Map<String,Object>> findByItemCodes(String itemCodes){
        StringBuffer sql = new StringBuffer("select U.ITEM_ID,U.ITEM_CODE,U.ITEM_NAME,D.DEPART_NAME ");
        sql.append("FROM T_WSBS_SERVICEITEM U ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=U.SSBMBM ");
        sql.append("WHERE U.ITEM_CODE IN　");
        sql.append(StringUtil.getValueArray(itemCodes));
        sql.append(" ORDER BY U.C_SN DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }

    /**
     * 
     * 描述    收费明细
     * @author Danto Huang
     * @created 2018年8月27日 下午5:46:18
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getChargeData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_SERVICEITEM_CHARGE t ";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    自查条件
     * @author Danto Huang
     * @created 2018年9月3日 下午5:41:50
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSelfExamData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select * from T_WSBS_SERVICEITEM_SELFEXAM t where 1=1 ";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述    获取自查条件最大排序值
     * @author Danto Huang
     * @created 2018年9月4日 上午10:45:38
     * @return
     */
    public int getMaxExamSn(){
        return dao.getMaxExamSn();
    }
    
    /**
     * 
     * 描述    商户管理数据
     * @author Danto Huang
     * @created 2018年9月20日 上午10:59:40
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getMerchantData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,pay.dic_name pay_way_name from T_SERVICEITEM_MERCHANT t ");
        sql.append("left join (select dic_code,dic_name from t_msjw_system_dictionary d where d.type_id=(");
        sql.append("select type_id from t_msjw_system_dictype where type_code='payWay')) pay ");
        sql.append("on pay.dic_code = t.pay_way ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    判断事项当前绑定流程审核人员已设置
     * @author Danto Huang
     * @created 2018年9月25日 上午10:09:22
     * @param itemId
     * @param defId
     * @return
     */
    public boolean defNodeSetDone(String itemId,String defId){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from T_WSBS_SERVICEITEM_AUDITER t where t.item_id=? and t.def_id=? ");
        sql.append("and t.node_type = 'task' and t.node_name<>'网上预审' and t.node_name<>'待受理' ");
        sql.append("and t.node_name<>'受理自动跳转' and t.node_name<>'办结自动跳转' and ( t.user_id is null ");
        sql.append("or t.REVIEW_STANDARD is null )");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{itemId,defId}, null);
        if(list!=null&&list.size()>0){
            return false;
        }else{
            return true;
        }

    }

    /**
     * 判断是否是工程建设项目节点
     */
    public boolean isGcjsDefTypeNode(String defId){
        StringBuffer sql=new StringBuffer("SELECT T.TYPE_NAME  ");
        sql.append(" FROM JBPM6_FLOWDEF  F  ");
        sql.append(" LEFT JOIN JBPM6_FLOWTYPE T  ON  ");
        sql.append(" F.TYPE_ID=T.TYPE_ID WHERE F.DEF_ID=? ");
        Map<String,Object> defNode=exeDataService.getFirstByJdbc(sql.toString(),new Object[]{defId});
        String defNodeName=StringUtil.getString(defNode.get("TYPE_NAME"));
        if(Objects.equals(defNodeName,GCJS_DEF_TYPENAME)){
           return true;
        }
        return false;
    }
    /**
     * 
     * 描述    保存到历史版本库
     * @author Danto Huang
     * @created 2018年10月9日 上午10:44:16
     * @param entityId
     * @param version
     */
    public void copyToHis(String entityId,String version){
        copyItemToHis(entityId,version);
        copyAuditerToHis(entityId,version);
        copyPreAuditerToHis(entityId,version);
        copyLinkToHis(entityId,version);
        copyChargeToHis(entityId,version);
        copyProblemToHis(entityId,version);
        copyMatterToHis(entityId,version);
        copySelfExamToHis(entityId,version);
        //copyTypeToHis(entityId,version);
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_PUBLISH_EDIT", 1);
        dao.saveOrUpdate(variable, "T_WSBS_SERVICEITEM", entityId);
    }
    
    /**
     * 
     * 描述    事项信息主表复制历史纪录表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:34:37
     * @param entityId
     */
    private void copyItemToHis(String entityId,String version){
        StringBuffer sql = new StringBuffer("insert into t_wsbs_item_his ( ");
        sql.append("item_id,item_code,item_name,sxxz,create_time,sxlx,bzsm,fwdx,xsyj,sqtj,bllc,rzbsdtfs,apply_url, ");
        sql.append("cnqxgzr,cnqxsm,fdqx,bldd,blbm,bgsj,ssztmc,sfddwss,lxdh,jddh,sfsf,sfbzjyj,fwsxzt,pxz,slxzsm, ");
        sql.append("ssbmbm,sffs,thyj,auditing_ids,auditing_names,backor_id,backor_name,bdlcdyid,catalog_code, ");
        sql.append("num_code,preday,receiveday,traffic_guide,finish_type,finish_gettype,finish_info,c_sn,fwsxmxlb, ");
        sql.append("catalog_id,ssqh,wssqfs,zbcs,swb_item_id,swb_item_code,lxr,itemstar,sfzcgq,update_time, ");
        sql.append("impl_depart,gftype,mxyhdx,grztfl,grrssj,grtdrq,frztfl,qydxfl,qyjyhd,sqfs,sqzhyq_2,sqzhyq_3, ");
        sql.append("sqzhyq_4,ckcs_1,ckcs_2,ckcs_3,ckcs_4,papersub,item_send_addr,item_send_addressee,message_rec, ");
        sql.append("fta_flag,fdsxgzr,fdsxlx,sflstd,keyword,is_undertake,bill_type,bill_type_other,fileattach_path, ");
        sql.append("file_name,fileattach_path2,file_name2,nfbyj,wcjlhmc,wcjlhwh,is_from_swb,swb_item_code_ecqr, ");
        sql.append("right_id,is_invest,right_source,right_source_other,is_appoint,agencyservice_id,cnsm_1,cnsm_2, ");
        sql.append("cnsm_3,cnsm_4,impl_depart_xz,hbcs,lbjg,jdbm,rzfs,ckms,zy_remark,coordinates,sffssm,pay_way, ");
        sql.append("result_name,result_desc,result_path,exercise_level,tbfw,handling_form,appointment_support, ");
        sql.append("onlinepay_support,express_support,timelimit_support,system_level,last_synchronize_time, ");
        sql.append("is_synchronize,item_remark,subject,C_VERSION,EFFECT_STATUS,IS_NEED_AGENCY,EXPRESS_NAME, ");
        sql.append("OFFICE_PHONE,CNSXLX,SYNC_LXR,SYNC_MOBILE,ITEM_SEND_MOBILE,ITEM_SEND_POSTCODE,ITEM_SEND_REMARKS,");
        sql.append("ITEM_SEND_PROV,ITEM_SEND_CITY ");
        sql.append(") ");
        sql.append("select ");
        sql.append("item_id,item_code,item_name,sxxz,create_time,sxlx,bzsm,fwdx,xsyj,sqtj,bllc,rzbsdtfs,apply_url,");
        sql.append("cnqxgzr,cnqxsm,fdqx,bldd,blbm,bgsj,ssztmc,sfddwss,lxdh,jddh,sfsf,sfbzjyj,fwsxzt,pxz,slxzsm,");
        sql.append("ssbmbm,sffs,thyj,auditing_ids,auditing_names,backor_id,backor_name,bdlcdyid,catalog_code,");
        sql.append("num_code,preday,receiveday,traffic_guide,finish_type,finish_gettype,finish_info,c_sn,fwsxmxlb,");
        sql.append("catalog_id,ssqh,wssqfs,zbcs,swb_item_id,swb_item_code,lxr,itemstar,sfzcgq,update_time,");
        sql.append("impl_depart,gftype,mxyhdx,grztfl,grrssj,grtdrq,frztfl,qydxfl,qyjyhd,sqfs,sqzhyq_2,sqzhyq_3,");
        sql.append("sqzhyq_4,ckcs_1,ckcs_2,ckcs_3,ckcs_4,papersub,item_send_addr,item_send_addressee,message_rec,");
        sql.append("fta_flag,fdsxgzr,fdsxlx,sflstd,keyword,is_undertake,bill_type,bill_type_other,fileattach_path,");
        sql.append("file_name,fileattach_path2,file_name2,nfbyj,wcjlhmc,wcjlhwh,is_from_swb,swb_item_code_ecqr,");
        sql.append("right_id,is_invest,right_source,right_source_other,is_appoint,agencyservice_id,cnsm_1,cnsm_2,");
        sql.append("cnsm_3,cnsm_4,impl_depart_xz,hbcs,lbjg,jdbm,rzfs,ckms,zy_remark,coordinates,sffssm,pay_way,");
        sql.append("result_name,result_desc,result_path,exercise_level,tbfw,handling_form,appointment_support,");
        sql.append("onlinepay_support,express_support,timelimit_support,system_level,last_synchronize_time,");
        sql.append("is_synchronize,item_remark,subject,'"+version+"','0',IS_NEED_AGENCY,EXPRESS_NAME,OFFICE_PHONE,");
        sql.append("CNSXLX,SYNC_LXR,SYNC_MOBILE,ITEM_SEND_MOBILE,ITEM_SEND_POSTCODE,ITEM_SEND_REMARKS,");
        sql.append("ITEM_SEND_PROV,ITEM_SEND_CITY ");
        sql.append("from t_wsbs_serviceitem where item_id=? ");
        dao.executeSql(sql.toString(), new Object[]{entityId});
    }
    /**
     * 
     * 描述    复制流程环节审核人至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copyAuditerToHis(String itemId,String version){
        Map<String,Object> item = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_ID"}, new Object[]{itemId});
        String defId = item.get("BDLCDYID").toString();
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_AUDITER_HIS (");
        sql.append("RECORD_ID,ITEM_ID,DEF_ID,NODE_NAME,NODE_KEY,NODE_TYPE,USER_ACCOUNT,USER_NAME,USER_ID,TIME_LIMIT, ");
        sql.append("TIME_TYPE,C_VERSION,EFFECT_STATUS,SUIT_SITUATION,REVIEW_CONTENT,REVIEW_STANDARD,EXAMINANT, ");
        sql.append("HANDLE_OPINIONS,RESPONSIBILITY,ATTENTION_MATTER,KEYPOINTS ");
        sql.append(") ");
        sql.append("select ");
        sql.append("RECORD_ID,ITEM_ID,DEF_ID,NODE_NAME,NODE_KEY,NODE_TYPE,USER_ACCOUNT,USER_NAME,USER_ID,TIME_LIMIT,");
        sql.append("TIME_TYPE,'"+version+"','0',SUIT_SITUATION,REVIEW_CONTENT,REVIEW_STANDARD,EXAMINANT, ");
        sql.append("HANDLE_OPINIONS,RESPONSIBILITY,ATTENTION_MATTER,KEYPOINTS ");
        sql.append("from T_WSBS_SERVICEITEM_AUDITER where ITEM_ID=? and DEF_ID=? ");
        dao.executeSql(sql.toString(), new Object[]{itemId,defId});
    }
    /**
     * 
     * 描述    复制预审人员至历史表
     * @author Danto Huang
     * @created 2018年11月12日 下午3:09:44
     * @param itemId
     */
    private void copyPreAuditerToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_PREAUDITER_HIS (");
        sql.append("ITEM_ID,USER_ID,C_VERSION,EFFECT_STATUS ");
        sql.append(") ");
        sql.append("select ");
        sql.append("ITEM_ID,USER_ID,'"+version+"','0' from T_WSBS_PREAUDITER where ITEM_ID=?");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    复制收费明细至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copyChargeToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_CHARGE_HIS (");
        sql.append("RECORD_ID,ITEM_ID,CHARGE_DETAIL,CHARGE_AMOUNT,UNITS,RECEIPT_NUM,PROJECT_CODE,IS_USE,CREATE_TIME, ");
        sql.append("C_VERSION,EFFECT_STATUS ");
        sql.append(") ");
        sql.append("select ");
        sql.append("RECORD_ID,ITEM_ID,CHARGE_DETAIL,CHARGE_AMOUNT,UNITS,RECEIPT_NUM,PROJECT_CODE,IS_USE,CREATE_TIME,");
        sql.append("'"+version+"','0' from T_WSBS_SERVICEITEM_CHARGE where ITEM_ID=?");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    复制常见问题至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copyProblemToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_COMMONPROBLEM_HIS (");
        sql.append("PROBLEM_ID,PROBLEM_TITLE,ANSWER_CONTENT,LASTER_UPDATETIME,ITEM_ID, ");
        sql.append("C_VERSION,EFFECT_STATUS ");
        sql.append(") ");
        sql.append("select ");
        sql.append("PROBLEM_ID,PROBLEM_TITLE,ANSWER_CONTENT,LASTER_UPDATETIME,ITEM_ID,");
        sql.append("'"+version+"','0' from T_WSBS_COMMONPROBLEM where ITEM_ID=?");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    复制特殊环节至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copyLinkToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_LINK_HIS (");
        sql.append("RECORD_ID,ITEM_ID,LINK_NAME,LINK_LIMITTIME,LINK_BASIS,CREATE_TIME,LIMIT_DESC,LINK_REMARK, ");
        sql.append("C_VERSION,EFFECT_STATUS,OPERATOR_ID,OPERATOR_NAME,SUIT_SITUATION,REVIEW_CONTENT,REVIEW_STANDARD,");
        sql.append("TRANSACTOR,HANDLE_OPINIONS,RESPONSIBILITY,ATTENTION_MATTER ");
        sql.append(") ");
        sql.append("select ");
        sql.append("RECORD_ID,ITEM_ID,LINK_NAME,LINK_LIMITTIME,LINK_BASIS,CREATE_TIME,LIMIT_DESC,LINK_REMARK,");
        sql.append("'"+version+"','0',OPERATOR_ID,OPERATOR_NAME,SUIT_SITUATION,REVIEW_CONTENT,REVIEW_STANDARD, ");
        sql.append("TRANSACTOR,HANDLE_OPINIONS,RESPONSIBILITY,ATTENTION_MATTER ");
        sql.append("from T_WSBS_SERVICEITEM_LINK where ITEM_ID=? ");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    复制材料至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copyMatterToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_MATER_HIS (");
        sql.append("ITEM_ID,MATER_ID,MATER_SN,MATER_ISNEED,MATER_FPARA, ");
        sql.append("C_VERSION,EFFECT_STATUS ");
        sql.append(") ");
        sql.append("select ");
        sql.append("ITEM_ID,MATER_ID,MATER_SN,MATER_ISNEED,MATER_FPARA,");
        sql.append("'"+version+"','0' from T_WSBS_SERVICEITEM_MATER where ITEM_ID=?");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    复制自查条件至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copySelfExamToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_SELFEXAM_HIS  (");
        sql.append("RECORD_ID,EXAM_TITLE,EXAM_TYPE,EXAM_CONTENT,EXAM_STATUS,EXAM_SN,ITEM_ID,CREATE_TIME, ");
        sql.append("BUS_HANDLE_TYPE,C_VERSION,EFFECT_STATUS ");
        sql.append(") ");
        sql.append("select ");
        sql.append("RECORD_ID,EXAM_TITLE,EXAM_TYPE,EXAM_CONTENT,EXAM_STATUS,EXAM_SN,ITEM_ID,CREATE_TIME,");
        sql.append("BUS_HANDLE_TYPE,'"+version+"','0' from T_WSBS_SERVICEITEM_SELFEXAM where ITEM_ID=?");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    复制业务业务类别至历史表
     * @author Danto Huang
     * @created 2018年10月15日 上午9:55:57
     * @param itemId
     */
    private void copyTypeToHis(String itemId,String version){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_ITEM_TYPE_HIS (");
        sql.append("ITEM_ID,TYPE_ID,C_VERSION,EFFECT_STATUS");
        sql.append(")");
        sql.append("select ");
        sql.append("ITEM_ID,TYPE_ID,'"+version+"','0' from T_WSBS_SERVICEITEM_TYPE where ITEM_ID=?");
        dao.executeSql(sql.toString(), new Object[]{itemId});
    }
    /**
     * 
     * 描述    历史版本记录生效状态更新
     * @author Danto Huang
     * @created 2018年10月17日 上午10:58:58
     * @param itemId
     */
    public void updateHisStatus(String itemId){
        Map<String, Object> item = dao.getByJdbc("t_wsbs_serviceitem", new String[]{"ITEM_ID"}, new Object[]{itemId});
        String version = item.get("C_VERSION") == null ? "" : item.get("C_VERSION").toString();
        String sql = "update t_wsbs_item_his set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_AUDITER_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_CHARGE_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_COMMONPROBLEM_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_LINK_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_MATER_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_SELFEXAM_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        /*dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_TYPE_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";*/
        dao.executeSql(sql, new Object[]{itemId,version});
        sql = "update T_WSBS_ITEM_PREAUDITER_HIS set EFFECT_STATUS='1' where item_id=? and c_version=?";
        dao.executeSql(sql, new Object[]{itemId,version});
    }
    /**
     * 
     * 描述    更新关联表版本号
     * @author Danto Huang
     * @created 2018年10月30日 上午8:52:49
     * @param itemId
     * @param version
     */
    public void updateRelateVersion(String itemId,String version){
        String sql = "update T_WSBS_SERVICEITEM_AUDITER set c_version=? where item_id=? ";
        dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_SERVICEITEM_CHARGE set c_version=? where item_id=? ";
        dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_COMMONPROBLEM set c_version=? where item_id=? ";
        dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_SERVICEITEM_LINK set c_version=? where item_id=? ";
        dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_SERVICEITEM_MATER set c_version=? where item_id=? ";
        dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_SERVICEITEM_SELFEXAM set c_version=? where item_id=? ";
        /*dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_SERVICEITEM_TYPE set c_version=? where item_id=? ";*/
        dao.executeSql(sql, new Object[]{version,itemId});
        sql = "update T_WSBS_PREAUDITER set c_version=? where item_id=? ";
        dao.executeSql(sql, new Object[]{version,itemId});
    }    

    /**
     * 
     * 描述    历史版本查询
     * @author Danto Huang
     * @created 2018年11月9日 下午3:59:53
     * @param fitler
     * @return
     */
    public List<Map<String, Object>> findItemHisBySqlfilter(SqlFilter fitler) {
        List<Object> params = new ArrayList<Object>();
        String fwsxzt = fitler.getQueryParams().get("Q_T.FWSXZT_EQ") == null ? ""
                : fitler.getQueryParams().get("Q_T.FWSXZT_EQ").toString();
        StringBuffer sql = new StringBuffer("");
        sql.append("select T.RIGHT_ID,T.ITEM_ID,T.ITEM_NAME,T.CNQXGZR,T.THYJ,");
        sql.append("T.AUDITING_NAMES,T.BACKOR_NAME,t.IS_FROM_SWB,t.C_VERSION,");
        // 以省网办编码为主
        sql.append("CASE WHEN length(T.SWB_ITEM_CODE)>0 THEN T.SWB_ITEM_CODE ELSE T.ITEM_CODE END ITEM_CODE,");
        sql.append("OP1.FILEATTACH_PATH ,OP1.FILE_NAME,OP1.THYJ AS PTHYJ,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,T.FWSXZT");
        sql.append(",T.SWB_ITEM_CODE,T.APPLY_URL,C.CATALOG_NAME");
        sql.append(", (SELECT L1.operate_time FROM (  select L.operate_time,L.item_id ");
        sql.append("  from T_WSBS_SERVICEITEM_LOG L order by L.operate_time desc ) L1");
        sql.append("  WHERE L1.item_id = T.ITEM_ID and ROWNUM =1) AS operate_time");
        sql.append(" from T_WSBS_ITEM_HIS T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN (SELECT * FROM VIEW_WSBS_OPINION OP1 WHERE OP1.FWSXZT='");
        sql.append(fwsxzt).append("') OP1 ON OP1.ITEM_ID=T.ITEM_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON C.CATALOG_CODE = T.CATALOG_CODE ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID=C.CHILD_DEPART_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D2.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        // 以省网办编码为主
        if (fitler.getQueryParams().get("Q_T.ITEM_CODE_LIKE") != null
                && !"".equals(fitler.getQueryParams().get("Q_T.ITEM_CODE_LIKE"))) {
            String item_code = fitler.getQueryParams().get("Q_T.ITEM_CODE_LIKE").toString();
            sql.append(" and (t.item_code like '").append("%").append(item_code).append("%");
            sql.append("' or t.swb_item_code like '").append("%");
            sql.append(item_code).append("%").append("') ");
            fitler.removeFilter("Q_T.ITEM_CODE_LIKE");
        }
        String exeSql = dao.getQuerySql(fitler, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), fitler.getPagingBean());
        return list;
    }
    

    /**
     * 
     * 描述    根据项目ID和版本号获取绑定的用户IDSNAMES
     * @author Danto Huang
     * @created 2018年11月12日 上午10:23:55
     * @param itemId
     * @param version
     * @return
     */
    public  Map<String, Object> getHisBindUserIdANdNames(String itemId,String version){
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(SU.USER_ID) USER_IDS");
        sql.append(",WMSYS.WM_CONCAT(SU.Fullname) FULL_NAMES FROM T_MSJW_SYSTEM_SYSUSER SU");
        sql.append(" WHERE SU.USER_ID IN (select P.USER_ID from T_WSBS_ITEM_PREAUDITER_HIS P");
        sql.append(" WHERE P.ITEM_ID=? AND P.C_VERSION=? AND P.EFFECT_STATUS='1') ORDER BY SU.CREATE_TIME DESC");
        return dao.getByJdbc(sql.toString(), new Object[]{itemId,version});
    }
    /**
     * 
     * 描述    特殊环节列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月12日 下午4:41:09
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSpecialLinkHis(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_ITEM_LINK_HIS t";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述    材料列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月12日 下午4:41:09
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getMatterHis(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(",T.RAW_NUM,T.ISNEED_RAW,T.MATER_XZ,T.MATER_CLSMLX,T.MATER_CLSM");
        sql.append(",F1.FILE_NAME AS CLMB,F2.FILE_NAME AS CLYB,T.MATER_SSYW");
        sql.append(",T.MATER_DESC,BC.BUSCLASS_NAME,t.gatherorver,t.mater_source,t.mater_source_type");
        sql.append(",T.PAGECOPY_NUM,T.PAGE_NUM");
        sql.append(",SM.MATER_ISNEED,SM.MATER_SN,SM.MATER_FPARA from T_WSBS_ITEM_MATER_HIS SM ");
        sql.append("LEFT JOIN T_WSBS_APPLYMATER T ON SM.MATER_ID=T.MATER_ID ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_BUSCLASS BC ON BC.RECORD_ID = T.MATER_SSYW ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F1  ON F1.FILE_ID=T.MATER_PATH ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F2  ON F2.FILE_ID=T.MATER_PATH2 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        //获取拼凑的子项名
        list=getBusClass(list);
        return list;
    }
    /**
     * 
     * 描述：获取拼凑的子项名
     * @author Water Guo
     * @created 2018-1-25 上午11:04:49
     * @param list
     * @return
     */
    private List<Map<String,Object>> getBusClass(List<Map<String,Object>> list){
        for(Map<String,Object> map:list){
            String materSsyw=String.valueOf(map.get("MATER_SSYW"));
            String[] materSsyws=materSsyw.split(",");
            if(materSsyws.length>1){
                StringBuffer busClassNames=new StringBuffer("");
                for(String str:materSsyws){
                    if (StringUtils.isNotEmpty(str)) {
                        Map<String,Object> busClass=this.getByJdbc("T_WSBS_SERVICEITEM_BUSCLASS",
                                new String[]{"RECORD_ID"}, new Object[]{str});
                        busClassNames.append(busClass.get("BUSCLASS_NAME")).append(",");
                    }
                }
                map.put("BUSCLASS_NAME", busClassNames.substring(0, busClassNames.length()-1));
            }
        }
        return list;
    }
    /**
     * 
     * 描述    收费明细列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月13日 上午10:01:11
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getChargeDataHis(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_ITEM_CHARGE_HIS t ";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    自查条件列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月13日 上午10:01:23
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSelfExamDataHis(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select * from T_WSBS_ITEM_SELFEXAM_HIS t where 1=1 ";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述   流程环节审核人设置数据(历史版本)
     * @author Danto Huang
     * @created 2016-9-28 下午3:02:06
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getDefNodeHis(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        String sql = "select t.* from T_WSBS_ITEM_AUDITER_HIS t "
                + "left join T_WSBS_ITEM_HIS s on s.item_id=t.item_id "
                + "where s.BDLCDYID=t.def_id and s.c_version=t.c_version and s.effect_status=1 ";
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findByBusinessCode(String businessCode) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select U.ITEM_ID,U.ITEM_CODE,U.ITEM_NAME,D.DEPART_NAME ");
        sql.append("FROM T_WSBS_SERVICEITEM U ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=U.SSBMBM ");
        sql.append("WHERE U.BUSINESS_CODE = ?　");
        sql.append(" ORDER BY U.C_SN DESC ");

        List<Object> params = new ArrayList<Object>();
        params.add(businessCode);
        return dao.findBySql(sql.toString(),params.toArray(),null);
    }

    @Override
    public void updateItemCodes(String itemCodes, String businessCode) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();

        StringBuffer updateSql = new StringBuffer("UPDATE  T_WSBS_SERVICEITEM T SET   T.BUSINESS_CODE = '' ");
        updateSql.append(" WHERE T.BUSINESS_CODE = ? ");
        StringBuffer sql = new StringBuffer("UPDATE  T_WSBS_SERVICEITEM T SET   T.BUSINESS_CODE = ?");
        sql.append(" WHERE T.ITEM_CODE IN  ");
        sql.append(StringUtil.getValueArray(itemCodes));
        params.add(businessCode);
        //清除事项绑定的业务编码
        dao.executeSql(updateSql.toString(), params.toArray());
        //绑定业务编码
        dao.executeSql(sql.toString(), params.toArray());
    }

    /**
     * 描述:判断材料是否完整
     *
     * @author Madison You
     * @created 2019/11/12 17:40:00
     * @param
     * @return
     */
    @Override
    public Map<String,Object> checkServiceItemApplyMater(Map<String, Object> item) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        Map<String, Object> returnMap = new HashMap<>();
        params.add(item.get("ITEM_ID"));
        sql.append(" select c.MATER_ID,c.COLLECT_METHOD,MATER_CLSMLX from T_WSBS_SERVICEITEM a  ");
        sql.append(" left join T_WSBS_SERVICEITEM_MATER b on a.ITEM_ID = b.ITEM_ID ");
        sql.append(" left join T_WSBS_APPLYMATER c on b.MATER_ID = c.MATER_ID ");
        sql.append(" where a.ITEM_ID = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (list != null) {
            for (Map<String, Object> map : list) {
                Object COLLECT_METHOD = map.get("COLLECT_METHOD");
                Object MATER_CLSMLX = map.get("MATER_CLSMLX");
                Object MATER_ID = map.get("MATER_ID");
                if (MATER_ID != null) {
                    if (COLLECT_METHOD == null) {
                        returnMap.put("msg", "材料收取方式未填写");
                        returnMap.put("flag", false);
                        return returnMap;
                    }
                    if (MATER_CLSMLX == null) {
                        returnMap.put("msg", "材料介质未填写");
                        returnMap.put("flag", false);
                        return returnMap;
                    }
                }
            }
        }
        returnMap.put("flag", true);
        return returnMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/6 10:35:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findByStampId(String stampId) {
        Map<String, Object> stampMap = dao.getByJdbc("T_BSFW_STAMPMANAGE", new String[]{"STAMP_ID"}, new Object[]{stampId});
        String itemcodelimit = StringUtil.getValue(stampMap, "STAMP_ITEMCODELIMIT");
        //StringBuffer sql = new StringBuffer();
        if (StringUtils.isNotEmpty(itemcodelimit)) {
            return findByItemCodes(itemcodelimit);
        } else {
            return null;
        }
    }

    /**
     * 描述:判断部门是否正确
     *
     * @author Madison You
     * @created 2021/2/24 9:19:00
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> checkServiceItemDepart(Map<String, Object> item) {
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("flag", true);
        String implDepartId = StringUtil.getValue(item, "IMPL_DEPART_ID");
        String implDepart = StringUtil.getValue(item, "IMPL_DEPART");
        String zbcsId = StringUtil.getValue(item, "ZBCS_ID");
        String zbcs = StringUtil.getValue(item, "ZBCS");
        if (StringUtils.isEmpty(implDepartId) || StringUtils.isEmpty(implDepart)) {
            returnMap.put("msg", "基本信息中实施主体未填写");
            returnMap.put("flag", false);
        }
        if (StringUtils.isEmpty(zbcsId) || StringUtils.isEmpty(zbcs)) {
            returnMap.put("msg", "基本信息中主办处室未填写");
            returnMap.put("flag", false);
        }
        Map<String, Object> zbcsDepartMap = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                new String[]{"DEPART_ID"}, new Object[]{zbcsId});
        String sql = "select t.* from T_MSJW_SYSTEM_DEPARTMENT t WHERE T.PARENT_ID =? ";
        List<Map<String, Object>> parentZbcsDepartList = dao.findBySql(sql, new Object[]{zbcsId}, null);
        if (zbcsDepartMap != null) {
            if ((!Objects.equals(implDepartId, StringUtil.getValue(zbcsDepartMap, "PARENT_ID"))
                    && !Objects.equals(implDepartId, "40288b9f54bf0b4d0154bf2981820056")
                    && !implDepartId.equals(zbcsId)) 
                    || (implDepartId.equals(zbcsId) && null != parentZbcsDepartList && parentZbcsDepartList.size()>0)) {
                returnMap.put("msg", "基本信息中实施主体与主办处室部门关系无法对应");
                returnMap.put("flag", false);
            }
        } else {
            returnMap.put("msg", "主办处室选择错误，请重新选择");
            returnMap.put("flag", false);
        }
        return returnMap;
    }

    /**
     * 描述:获取本地新建事项数量
     *
     * @author Madison You
     * @created 2021/3/4 14:52:00
     * @param
     * @return
     */
    @Override
    public int getLocalItemCount() {
        List<Map<String,Object>> list = dao.findBySql("SELECT COUNT(*) SUM FROM T_WSBS_SERVICEITEM WHERE IS_LOCAL = 1",
                null, null);
        if (list != null && !list.isEmpty()) {
            return Integer.parseInt(list.get(0).get("SUM").toString());
        }
        return 0;
    }
    
    /**
     * 
     * 描述    待划转数据
     * @author Danto Huang
     * @created 2021年5月18日 下午4:02:20
     * @param itemIds
     * @return
     */
    public List<Map<String,Object>> selectedForTransferData(String itemIds){
        StringBuffer sql = new StringBuffer();
        sql.append("select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,D.DEPART_NAME,CD.DEPART_NAME CHILD_DEPART_NAME ");
        sql.append("from T_WSBS_SERVICEITEM T ");
        sql.append("left join T_MSJW_SYSTEM_DEPARTMENT D on D.DEPART_CODE=T.SSBMBM ");
        sql.append("left join T_MSJW_SYSTEM_DEPARTMENT CD on CD.DEPART_ID=T.ZBCS_ID ");
        sql.append("where T.ITEM_ID in ").append(StringUtil.getValueArray(itemIds));
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * 
     * 描述    事项划转
     * @author Danto Huang
     * @created 2021年5月19日 上午10:53:58
     * @param request
     * @return
     */
    public void doTransfer(HttpServletRequest request){
        String itemIds = request.getParameter("itemIds");
        String IMPL_DEPART_ID = request.getParameter("IMPL_DEPART_ID");
        String IMPL_DEPART = request.getParameter("IMPL_DEPART");
        String ZBCS_ID = request.getParameter("ZBCS_ID");
        String ZBCS = request.getParameter("ZBCS");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("IMPL_DEPART_ID", IMPL_DEPART_ID);
        map.put("IMPL_DEPART", IMPL_DEPART);
        map.put("ZBCS_ID", ZBCS_ID);
        map.put("ZBCS", ZBCS);
        Map<String, Object> depart = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                new Object[] { IMPL_DEPART_ID });
        map.put("SSBMBM", depart.get("DEPART_CODE"));
        if(StringUtils.isNotEmpty(itemIds)){
            String[] itemIdArr = itemIds.split(",");
            for(String itemId : itemIdArr){
                Map<String, Object> item = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                        new Object[] { itemId });
                map.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                dao.saveOrUpdate(map, "T_WSBS_SERVICEITEM", itemId);
                
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("OPERATE_CONTENT", "服务事项划转：由“" + item.get("IMPL_DEPART") + "（" + item.get("ZBCS") + "）”划转至“"
                        + IMPL_DEPART + "（" + ZBCS + "）”");
                data.put("ITEM_ID", itemId);
                data.put("OPERATE_TYPE",1);
                data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                SysUser sysUser = AppUtil.getLoginUser();
                data.put("FULLNAME",sysUser.getFullname());
                data.put("USERNAME",sysUser.getUsername());
                data.put("USERID", sysUser.getUserId());
                String idAddress = BrowserUtils.getIpAddr(request);
                data.put("IP_ADDRESS",idAddress);
                dao.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
            }
        }
    }
}
