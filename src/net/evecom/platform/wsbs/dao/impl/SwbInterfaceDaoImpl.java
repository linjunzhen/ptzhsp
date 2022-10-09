/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
    
import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DbUtil;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 * 
 * 描述 省网办接口服务
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月7日 下午2:46:30
 */
@SuppressWarnings("rawtypes")
@Repository("swbInterfaceDao")
public class SwbInterfaceDaoImpl extends BaseDaoImpl implements SwbInterfaceDao {
    /**
     * 
     * 描述 获取到需报送到省网办的办件信息数据(不包含工程建设数据)
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:40:06
     * @return
     */
    public List<Map<String, Object>> findBusBasicInfoList(Connection conn) {
        StringBuffer sql = new StringBuffer("with GCJS as (select distinct r.exe_id from T_BSFW_SWBDATA_RES R ");
       sql.append(" inner join view_jbpm6_execution_new e on r.exe_id=e.exe_id ");
       sql.append("inner join t_wsbs_serviceitem  t on e.item_code=t.item_code where t.SFGCJSXM=1) ");
       sql.append("SELECT * FROM (SELECT A.* FROM (SELECT R.RES_ID,R.DATA_TYPE,R.INTER_TYPE, ");
       sql.append("R.OPER_TYPE,R.HAS_ATTR,R.EXE_ID,R.TASK_ID,R.OTHER_STATUS ");
       sql.append("FROM T_BSFW_SWBDATA_RES R ");
       sql.append("WHERE R.INTER_TYPE = ? AND R.OPER_STATUS = 0 ");
       sql.append("and not exists (select 1 from view_jbpm6_execution_new e where r.exe_id=e.exe_id ");
       sql.append("and e.SQRMC is  null and e.sqjg_name is null) ");
       sql.append("and not exists (select 1 from GCJS G WHERE R.EXE_ID=G.EXE_ID ) ");
       sql.append("ORDER BY R.DATA_TYPE ASC, R.CREATE_TIME ASC ) a ) ");
       sql.append("where rownum <= (select count from t_swb_ts_count where type='swbbjcount') ");
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql.toString(), new Object[] { AllConstant.DATA_INTER_TYPE_SWB },false);
        return dataList;
    }
    /**
     * 
     * 描述    获取到需报送到省网办的办件信息数据(工程建设数据)
     * @author Yanisin Shi
     * @param conn
     * @return
     * @see net.evecom.platform.wsbs.dao.SwbInterfaceDao#findGcjsBusBasicInfoList(java.sql.Connection)
     */
    public List<Map<String, Object>> findGcjsBusBasicInfoList(Connection conn) {
        StringBuffer sql=new StringBuffer(" ");
        sql.append(" SELECT * FROM (SELECT A.* ");
        sql.append(" FROM (SELECT R.RES_ID,R.DATA_TYPE,R.INTER_TYPE,R.OPER_TYPE,R.HAS_ATTR,R.EXE_ID,R.TASK_ID,R.OTHER_STATUS ");
        sql.append(" FROM T_BSFW_SWBDATA_RES R ");
        sql.append(" inner join view_jbpm6_execution_new e on r.exe_id=e.exe_id ");
        sql.append(" inner join t_wsbs_serviceitem  t on e.item_code=t.item_code ");
        sql.append(" WHERE R.INTER_TYPE = ? AND R.OPER_STATUS = 0 ");
        sql.append(" and not exists (select 1 from view_jbpm6_execution_new e where r.exe_id=e.exe_id and ");
        sql.append(" e.SQRMC is  null and e.sqjg_name is null) ");
        sql.append(" and t.SFGCJSXM=1 ");
        sql.append(" ORDER BY R.DATA_TYPE ASC, R.CREATE_TIME ASC ) a ) where ");
        sql.append(" rownum <= (select count from t_swb_ts_count where type='GcjsCount') ");
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql.toString(), new Object[] { AllConstant.DATA_INTER_TYPE_SWB },false);
        return dataList;
    }
  /**
   * 
   * 描述  获取数据库配置信息
   * @author Yanisin Shi
   * @param conn
   * @return
   * @see net.evecom.platform.wsbs.dao.SwbInterfaceDao#findDataAbutmentList(java.sql.Connection)
   */
    public List<Map<String, Object>> findDataAbutmentList(Connection conn) {
        String sql = "select T.* from T_BSFW_DATAABUTMENT t where t.AABUT_CODE IN ('0006','0008','0009','0010','0011','0012','0016','0024','0037') order by t.AABUT_CODE asc ";
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql,null,false);
        return dataList;
    }
    /**
     *
     * 描述 获取到需报送到省网办的办件信息数据列表
     *
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:40:06
     * @return
     */
    public List<Map<String, Object>> findBusDataList(Connection conn,String Type) {
        StringBuffer sql=new StringBuffer(" SELECT * FROM (SELECT A.*,ROWNUM AS ROWNUMBER  FROM (");
        sql.append(" SELECT * FROM T_PROV_SERVICEBUSDATA S ");
        sql.append(" WHERE S.STATUS =0 AND S.TYPE= ?");
        sql.append(" AND S.ORIGINAL_DATA IS NOT NULL ");
        sql.append(" ORDER BY S.CREATE_TIME ASC ");
        sql.append("  ) a ) where rownumber <= 100 ");
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql.toString(), new Object[] { Type});
        return dataList;
    }
    
    /**
     *
     * 描述 获取到需报送到省网办的办件信息数据列表
     *
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:40:06
     * @return
     */
    public List<Map<String, Object>> findBusDataList(Connection conn) {
        StringBuffer sql=new StringBuffer(" ");
        sql.append("SELECT S.ID,S.SWB_ITEM_CODE,S.CREATE_TIME, S.STATUS,");
        sql.append(" S.ATTRIDS,S.APASINFO,S.PROPOSER,S.OPERATOR,S.ATTRS,"); 
        sql.append(" S.OPINIONSORNODE,S.EXE_ID,S.TYPE,S.RESULTATTRS,");
        sql.append(" S.EXPRESS,S.SWB_ITEM_NAME,S.ROUTER,S.FORMINFOS,");
        sql.append(" S.SN,S.DOCUMENTS,S.SMS,S.EXTEND,S.SURFACE,S.EXPRESSSERVICE,");
        sql.append(" S.UUID,S.ORIGINAL_DATA,'350128' AS AREACODE,S.SWB_ITEM_CODE AS ORIGNAL_ITEM_CODE"); 
        sql.append(" FROM T_PROV_SERVICEBUSDATA S where S.ORIGINAL_DATA is not null and S.SWB_ITEM_CODE is not null ");
        sql.append(" and  S.status=0 and S.type=10 and s.create_time between (select starttime from t_swb_ts_count where type='zxtoswbcount') "); 
        sql.append(" and (select endtime from t_swb_ts_count where type='zxtoswbcount' )  ");
        sql.append(" and rownum<=(select count from t_swb_ts_count where type='zxtoswbcount')");
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql.toString(), new Object[] { },false);
        return dataList;
    }
    /**
     * 
     * 描述 获取到需报送到省网办的办件信息数据
     * 
     * @author Water Guo
     * @created 2016年8月31日 下午1:40:06
     * @return
     */
    @Override
    public List<Map<String, Object>> findBusBasicInfoList(Connection conn, int i) {
        String sql = "SELECT * FROM (SELECT A.*,ROWNUM AS ROWNUMBER "
                + " FROM (SELECT R.RES_ID,R.DATA_TYPE,R.INTER_TYPE,R.OPER_TYPE,R.HAS_ATTR,R.EXE_ID,R.TASK_ID "
                + " FROM T_BSFW_SWBDATA_RES R WHERE R.INTER_TYPE = ? AND R.OPER_STATUS = 0  AND R.DATA_TYPE = ?"
                + " ORDER BY R.DATA_TYPE ASC, R.CREATE_TIME ASC ) a ) where rownumber <= 500 ";
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql, new Object[] { AllConstant.DATA_INTER_TYPE_SWB , i });
        return dataList;
    }

    /**
     * 
     * 描述 获取后进行操作状态的更新避免重复获取
     * 
     * @author Water Guo
     * @created 2016年8月21日 下午1:40:06
     * @return
     */
    @Override
    public void updateOperStatusToNine(Connection conn) {
        // TODO Auto-generated method stub
        String sql = "UPDATE T_BSFW_SWBDATA_RES T " + "SET T.OPER_STATUS = 9 "
                + "WHERE T.RES_ID IN (SELECT M.RES_ID FROM " + "(SELECT RES.*, ROWNUM AS ROWNUMBER "
                + "FROM T_BSFW_SWBDATA_RES RES " + "WHERE RES.INTER_TYPE = ? AND RES.OPER_STATUS = 0 "
                + "ORDER BY RES.DATA_TYPE ASC, RES.CREATE_TIME ASC) M " + "WHERE M.ROWNUMBER <= 300)";
        DbUtil.update(conn, sql, new Object[] { AllConstant.DATA_INTER_TYPE_SWB }, false);

    }

    /**
     * 
     * 描述 删除已删流程的事项信息
     * 
     * @author Water Guo
     * @created 2016年8月21日 下午1:40:06
     * @return
     */
    @Override
    public void deleteInfoNoInJbpm6Execution(Connection conn) {
        // TODO Auto-generated method stub
        String sql = "delete from t_bsfw_swbdata_res r " + "where not exists "
                + "(select 1 from jbpm6_execution e where e.exe_id = r.exe_id) and r.oper_status in (0,2)";
        DbUtil.update(conn, sql, null, false);
    }
    /**
     * 
     * 描述 删除已删流程的事项信息
     * @author Yanisin Shi
     * @param conn
     * @see net.evecom.platform.wsbs.dao.SwbInterfaceDao#deleteInfoNoInJbpm6Execution(java.sql.Connection)
     */
    @Override
    public void deleteInfoNoInJbpm6ExecutionNew(Connection conn,String str) {
        String jbpm6_execution=str;
        // TODO Auto-generated method stub
        String sql = "delete from t_bsfw_swbdata_res r " + "where not exists "
                + "(select 1 from "+jbpm6_execution+" e where e.exe_id = r.exe_id) and r.oper_status in (0,2)";
        DbUtil.update(conn, sql, null, false);
    }

    /**
     * 
     * 描述 删除已删流程的事项附件信息
     * 
     * @author Water Guo
     * @created 2016年8月21日 下午1:40:06
     * @return
     */
    @Override
    public void deleteAttrNoInJbpm6Execution(Connection conn) {
        // TODO Auto-generated method stub
        String sql = "delete from t_bsfw_swbdata_attr r " + "where not exists "
                + "(select 1 from jbpm6_execution e, t_bsfw_swbdata_res res "
                + "where e.exe_id = res.exe_id and res.res_id = r.res_id) and r.oper_status in(0,2)";
        DbUtil.update(conn, sql, null, false);
    }
    /**
     * 
     * 描述 删除已删流程的事项附件信息 (包含归档信息部分)
     * @author Yanisin Shi
     * @param conn
     */
    @Override
    public void deleteAttrNoInJbpm6ExecutionNew(Connection conn,String str) {
        String jbpm6_execution=str;
        // TODO Auto-generated method stub
        String sql = "delete from t_bsfw_swbdata_attr r " + "where not exists "
                + "(select 1 from "+jbpm6_execution+" e, t_bsfw_swbdata_res res "
                + "where e.exe_id = res.exe_id and res.res_id = r.res_id) and r.oper_status in(0,2)";
        DbUtil.update(conn, sql, null, false);
    }

    @Override
    public String getParentOrgCode(String parentId, Connection conn) {
        // TODO Auto-generated method stub
        String sqlString = "select t.org_code as orgCode from T_MSJW_SYSTEM_DEPARTMENT t " +
                "where t.depart_id = ?";
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sqlString, new Object[] { parentId });
        return dataList.get(0).get("orgCode").toString();
    }
    
     /**
     * 描述
     * @author Yanisin Shi
     * @param conn
     * @return
     * @see net.evecom.platform.wsbs.dao.SwbInterfaceDao#findGcAndJgDataList(java.sql.Connection)
     * create time 2021年9月19日
     */
     
    @Override
    public List<Map<String, Object>> findGcAndJgDataList(Connection conn,String sn) {
        StringBuffer sql=new StringBuffer(" ");
        sql.append(" SELECT * ");
        sql.append(" FROM T_PROV_SERVICEBUSDATA S");
        sql.append(" WHERE S.STATUS = 0 ");
        sql.append(" AND S.ORIGINAL_DATA IS NOT NULL ");
        sql.append(" AND S.TYPE IN ('80','85')  ");    
        sql.append(" AND S.SN= ?  ORDER BY S.TYPE,S.CREATE_TIME"); 
        List<Map<String, Object>> dataList = null;
        dataList = DbUtil.findBySql(conn, sql.toString(), new Object[] {sn},false);
        return dataList;
    }
    /**
     * 
     * 描述 查询10数据是否已经推送 
     * @author Yanisin Shi
     * @return
     * @see net.evecom.platform.wsbs.dao.SwbInterfaceDao#searchTen()
     */
    @Override
    public int searchTen(String exeid,String resid) {
        // TODO Auto-generated method stub
        String sqfs="select sqfs from view_jbpm6_execution_new e where exe_id='"+exeid+"'";
        int j= this.jdbcTemplate.queryForInt(sqfs);
        if (j==3){
            //申请方式为3代表省网下发办件，省网下发办件不包含10类型数据
            return 1;
        }else{
            try { 
            String search = "select count(*) from t_bsfw_swbdata_res where data_type=10 and oper_status= 1 and exe_id ='"+exeid+"'";
            int i = this.jdbcTemplate.queryForInt(search);
            //如果10未传，将取走状态改成未取。
            if(i==0){
               String updsql="update t_bsfw_swbdata_res set oper_status=0 where res_id='"+resid+"' and exe_id ='"+exeid+"'"; 
               this.jdbcTemplate.execute(updsql);
            }
            return i;
        } catch (EmptyResultDataAccessException e) {
           return 0;
        }
        
        }
    }
}
