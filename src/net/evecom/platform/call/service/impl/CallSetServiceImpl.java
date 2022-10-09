/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.call.dao.CallSetDao;
import net.evecom.platform.call.service.CallSetService;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年6月26日 上午11:09:42
 */
@Service("callSetService")
public class CallSetServiceImpl extends BaseServiceImpl implements CallSetService {

    /**
     * 引入dao
     */
    @Resource
    private CallSetDao dao;

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-1-13 上午11:29:48
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述    业务管理数据
     * @author Danto Huang
     * @created 2018年6月27日 上午9:24:32
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getBusManageData(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name,dic.dic_name BELONG_ROOMNAME from T_CKBS_BUSINESSDATA t ");
        sql.append("left join t_msjw_system_department d on d.depart_id = t.depart_id ");
        sql.append("left join (select dic_code,dic_name from t_msjw_system_dictionary where type_id=(select type_id ");
        sql.append("from t_msjw_system_dictype where type_code='roomNo')) dic on dic.dic_code = t.belong_room ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    业务选择数据
     * @author Danto Huang
     * @created 2018年6月27日 下午5:23:34
     * @return
     */
    public List<Map<String,Object>> findBusinessForSelect(){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.BUSINESS_CODE,t.BUSINESS_NAME from T_CKBS_BUSINESSDATA t ");
        sql.append("where t.SERVICE_STATUS=1 order by t.BUSINESS_CODE");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        return list;
    }

    /**
     * 
     * 描述    获取业务名称
     * @author Danto Huang
     * @created 2018年6月27日 下午6:37:08
     * @param businessCodes
     * @return
     */
    public String getBusinessNamees(String businessCodes){
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(D.BUSINESS_NAME) names");
        sql.append(" FROM T_CKBS_BUSINESSDATA D ");
        sql.append(" WHERE D.BUSINESS_CODE IN ").append(StringUtil.getValueArray(businessCodes));
        sql.append(" ORDER BY D.BUSINESS_CODE ASC ");
        String names = dao.getByJdbc(sql.toString(), null).get("names").toString();
        return names;
    }
    
    /**
     * 
     * 描述    根据业务编码获取列表数据
     * @author Danto Huang
     * @created 2018年7月12日 下午4:06:04
     * @param businessCodes
     * @return
     */
    public List<Map<String,Object>> findBybusinessCode(String businessCodes){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name  from T_CKBS_BUSINESSDATA t ");
        sql.append("left join t_msjw_system_department d on d.depart_id = t.depart_id ");
        sql.append("where t.BUSINESS_CODE in ");
        sql.append(StringUtil.getValueArray(businessCodes));
        sql.append(" ORDER BY t.BELONG_ROOM,d.TREE_SN,t.BUSINESS_CODE ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述    业务数据是否已被使用
     * @author Danto Huang
     * @created 2018年9月26日 下午2:39:00
     * @param businessCode
     * @return
     */
    public boolean isUsingBusData(String businessCode){
        String sql = "select * from T_CKBS_QUEUERECORD t where t.business_code=? ";
        List list = dao.findBySql(sql, new Object[]{businessCode}, null);
        sql = "select * from T_CKBS_WIN_USER t where t.WIN_BUSINESS_CODES like ? ";
        List<Map<String,Object>> userList = dao.findBySql(sql.toString(), new Object[]{"%"+businessCode+"%"}, null);
        if((list!=null&&list.size()>0)||(userList!=null&&userList.size()>0)){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述    取号部门图标数据列表
     * @author Danto Huang
     * @created 2018年6月28日 下午4:02:24
     * @return
     */
    public List<Map<String,Object>> findDepartIconList(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select i.depart_id,d.depart_name,b.icon_path,i.belong_room from ");
        sql.append("(select t.depart_id,wm_concat(distinct(t.belong_room)) belong_room ");
        sql.append("from T_CKBS_BUSINESSDATA t group by t.depart_id) i ");
        sql.append("left join t_msjw_system_department d on d.depart_id=i.depart_id ");
        sql.append("left join T_CKBS_BUSINESSDATA_ICON b on b.depart_id=i.depart_id "); 
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        if(list!=null&&list.size()>0){
            for(Map<String,Object> map : list){
                Map<String, Object> variable = dao.getByJdbc("T_CKBS_BUSINESSDATA_ICON", new String[] { "DEPART_ID" },
                        new Object[] { map.get("DEPART_ID") });
                if(variable==null){
                    variable = new HashMap<String, Object>();
                    variable.put("DEPART_ID", map.get("DEPART_ID"));
                    variable.put("BELONG_ROOM", map.get("BELONG_ROOM"));
                    dao.saveOrUpdate(variable, "T_CKBS_BUSINESSDATA_ICON", null);
                }else{
                    variable.put("BELONG_ROOM", map.get("BELONG_ROOM"));
                    dao.saveOrUpdate(variable, "T_CKBS_BUSINESSDATA_ICON", variable.get("RECORD_ID").toString());
                }
            }
        }
        return list;
    }
    
    /**
     * 
     * 描述    窗口屏数据
     * @author Danto Huang
     * @created 2018年6月27日 下午2:46:49
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinScreenBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_CKBS_WIN_SCREEN t where 1=1 ");        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
        
    }
    
    /**
     * 
     * 描述    窗口编号选择数据
     * @author Danto Huang
     * @created 2018年6月28日 上午10:05:59
     * @return
     */
    public List<Map<String,Object>> findWinNoForSelect(){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.WIN_NO as TEXT,t.WIN_NO as VALUE from T_CKBS_WIN_SCREEN t ");
        sql.append("order by to_number(t.WIN_NO)");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        return list;
    }
    
    /**
     * 
     * 描述    窗口屏编号选择数据
     * @author Danto Huang
     * @created 2018年6月28日 上午10:05:59
     * @return
     */
    public List<Map<String,Object>> findScreenNoForSelect(){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.SCREEN_NO as TEXT,t.SCREEN_NO as VALUE from T_CKBS_WIN_SCREEN t ");
        sql.append("order by to_number(t.SCREEN_NO)");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        return list;
    }

    /**
     * 
     * 描述    窗口人员数据
     * @author Danto Huang
     * @created 2018年6月27日 下午5:02:15
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinUserBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,u.fullname as cur_username,g.group_name from T_CKBS_WIN_USER t ");
        sql.append("left join T_MSJW_SYSTEM_SYSUSER u on u.user_id = t.cur_userid ");
        sql.append("left join T_CKBS_WIN_GROUP g on g.group_id = t.win_group ");
        sql.append("where 1=1 ");
        if(sqlFilter.getQueryParams().get("Q_T.WORK_STATUS_=")!=null){
            String workStatus = sqlFilter.getQueryParams().get("Q_T.WORK_STATUS_=").toString();
            sqlFilter.removeFilter("Q_T.WORK_STATUS_=");
            if(StringUtils.isNotEmpty(workStatus)){
                if(workStatus.equals("1")){
                    sql.append("and t.cur_userid is not null ");
                }else if(workStatus.equals("0")){
                    sql.append("and t.cur_userid is null ");
                }
            }
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    窗口分组选择列表
     * @author Danto Huang
     * @created 2019年3月13日 上午10:38:35
     * @return
     */
    public List<Map<String,Object>> findWinGroupForSelect(){
        StringBuffer sql = new StringBuffer(" ")
                .append(" select D.GROUP_NAME as TEXT, D.GROUP_ID as VALUE ")
                .append(" FROM T_CKBS_WIN_GROUP D ")
                .append(" order by d.CREATE_TIME asc ");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * 描述 获取13、14、15窗口业务配置
     * @author Kester Chen
     * @created 2019年3月16日 下午6:18:03
     * @return
     * @see net.evecom.platform.call.service.CallSetService#findWinUser()
     */
    public List<Map<String,Object>> findWinUser(){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* ");
        sql.append("from T_CKBS_WIN_USER t ");
        sql.append("where t.win_no in ('13','14','15') ");
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    
    /**
     * 
     * 描述 获取13、14、15窗口业务配置
     * @author Kester Chen
     * @created 2019年3月16日 下午6:18:03
     * @return
     * @see net.evecom.platform.call.service.CallSetService#findWinUser()
     */
    public List<Map<String,Object>> findWinUserByWinNo(String winNo){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* ");
        sql.append("from T_CKBS_WIN_USER t ");
        sql.append("where t.win_no in ("+winNo+") ");
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    /**
     * 
     * 描述    获取正在使用窗口
     * @author Danto Huang
     * @created 2018年7月13日 下午5:21:38
     * @return
     */
    public List<Map<String,Object>> findUsingWin(){
        StringBuffer sql =  new StringBuffer();
        sql.append("select t.record_id,t.win_no,t.screen_no,t.win_business_codes,");
        sql.append("t.cur_userid,u.username from T_CKBS_WIN_USER t ");
        sql.append("left join T_CKBS_WIN_SCREEN s on s.win_no = t.win_no ");
        sql.append("left join T_MSJW_SYSTEM_SYSUSER u on u.user_id = t.cur_userid ");
        sql.append("where t.cur_userid is not null ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
        return list;
    }

    /**
     * 
     * 描述    获取业务设置部门
     * @author Danto Huang
     * @created 2018年7月19日 上午9:40:19
     * @return
     */
    public List<Map<String, Object>> findDepart() {
        StringBuffer sql = new StringBuffer();
        sql.append("select b.depart_id,d.depart_name from ");
        sql.append("(select t.depart_id from T_CKBS_BUSINESSDATA t where t.service_status=1 group by t.depart_id) b ");
        sql.append("left join t_msjw_system_department d on d.depart_id = b.depart_id ");
        sql.append("order by d.tree_sn ");
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * 
     * 描述    根据部门ID获取业务数据
     * @author Danto Huang
     * @created 2018年7月19日 上午11:21:00
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId){
        String sql = "select t.* from T_CKBS_BUSINESSDATA t where t.depart_id=? and t.service_status=1 "
                + "and t.is_appoint=1";
        return dao.findBySql(sql, new Object[]{parentId}, null);
    }
    
    /**
     * 
     * 描述    获取网上预约时段设置数据列表
     * @author Danto Huang
     * @created 2018年7月20日 上午10:21:43
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findAppointSetBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_CKBS_APPOINTMENT_SET T ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }
}
