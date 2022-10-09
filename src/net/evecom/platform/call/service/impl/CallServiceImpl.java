/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.call.dao.CallDao;
import net.evecom.platform.call.service.CallService;

/**
 * 描述 叫号管理
 * @author Danto Huang
 * @created 2016-1-13 上午11:28:10
 */
@Service("callService")
public class CallServiceImpl extends BaseServiceImpl implements CallService {

    /**
     * 引入dao
     */
    @Resource
    private CallDao dao;

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
     * 描述 获取叫号窗口管理列表
     * @author Danto Huang
     * @created 2016-1-13 下午3:21:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT T.*,CONCAT('(',CONCAT(T.FULLNAME,CONCAT(')(',CONCAT(T.USERNAME,')')))) AS USERNAMES ");
        sql.append("FROM T_CKBS_SERVICEWIN T ");
        /*sql.append("ckl.dic_name as ckywl FROM T_CKBS_SERVICEWIN T ");
        sql.append("left join (select d.dic_code,d.dic_name from t_msjw_system_dictionary d where ");
        sql.append("d.type_id = (select dt.type_id from t_msjw_system_dictype dt where dt.type_code='winDepartNo')");
        sql.append(")ckl on ckl.dic_code = t.windepart_no ");*/
        sql.append("WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 获取VIP管理列表
     * @author Danto Huang
     * @created 2016-1-13 下午3:21:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findVIPBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT T.* FROM T_CKBS_VIP T WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }    

    /**
     * 
     * 描述 获取窗口办件叫号列表
     * @author Danto Huang
     * @created 2016-1-13 下午3:21:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,zjlx.dic_name zjlx, ");
        sql.append("case when t.is_appointment=0 or a.begin_time<=to_char(sysdate,'hh24:mi') ");
        sql.append("then 1 else 0 end appointcall FROM T_CKBS_NUMRECORD T ");
        sql.append("left join (select td.dic_code,td.dic_name  from t_msjw_system_dictionary ");
        sql.append("td where td.type_code='DocumentType') zjlx on zjlx.dic_code = t.line_zjlx ");
        sql.append("left join  t_bespeak_apply a on a.num_id = t.record_id ");
        sql.append("WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取取票分类
     * @author Danto Huang
     * @created 2016-1-16 下午2:32:00
     * @return
     */
    public List<Map<String,Object>> findDepartWait(String roomNo){
        StringBuffer sql = new StringBuffer("");
        sql.append("select t.*,room.dic_desc from T_CKBS_DEPART t ");
        sql.append("left join (select d.dic_code,d.dic_name,d.dic_desc from t_msjw_system_dictionary d ");
        sql.append("where d.type_id=(select s.type_id from t_msjw_system_dictype s where s.type_code='roomNo'))");
        sql.append(" room on room.dic_code=t.belong_no ");
        sql.append("order by decode(t.belong_no,?,1,0) desc,to_number(t.tree_sn) ");
        return dao.findBySql(sql.toString(), new Object[]{roomNo}, null);
    }
    /**
     *
     * 描述排队信息展示页面
     * @created 2019年03月17日 下午4:04:15
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> callingLineInfo(SqlFilter sqlFilter){
        String curDate=DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd");
        StringBuffer longWait=new StringBuffer();
        //等候时期最长的前三条
        longWait.append("SELECT * FROM ( SELECT T.*  ");
        longWait.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='0' ");
        longWait.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ?  ");
        longWait.append(" AND T.ROOM_NO IN('A','B')  ");
        longWait.append("ORDER BY T.CREATE_TIME ASC ) ");
        longWait.append(" WHERE ROWNUM<=3 ORDER BY ROWNUM ASC  ");
        List<Map<String,Object>> list = dao.findBySql(longWait.toString(),new String[]{curDate},null);

        StringBuffer sql=new StringBuffer("SELECT T.* ");
        sql.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='6' ");
        sql.append("   AND SUBSTR(T.CREATE_TIME, 0, 10) = ? ");
        sql.append(" AND T.ROOM_NO IN('A','B') ");
        sql.append(" ORDER BY T.CALLING_TIME DESC,T.CUR_WIN ASC ");
        list.addAll(dao.findBySql(sql.toString(),new String[]{curDate},null));
        return  list;
    }
    /**
    *
    * 描述排队信息展示页面
    * @created 2019年03月17日 下午4:04:15
    * @param sqlFilter
    * @return
    */
   public List<Map<String,Object>> yqyzCallingLineInfo(SqlFilter sqlFilter){
       String curDate=DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd");
       StringBuffer longWait=new StringBuffer();
       //等候时期最长的前三条
       longWait.append("SELECT * FROM ( SELECT T.*  ");
       longWait.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='0' ");
       longWait.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ?  ");
       longWait.append(" AND T.ROOM_NO IN('D')  ");
       longWait.append("ORDER BY T.CREATE_TIME ASC ) ");
       longWait.append(" WHERE ROWNUM<=3 ORDER BY ROWNUM ASC  ");
       List<Map<String,Object>> list = dao.findBySql(longWait.toString(),new String[]{curDate},null);

       StringBuffer sql=new StringBuffer("SELECT T.* ");
       sql.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='6' ");
       sql.append("   AND SUBSTR(T.CREATE_TIME, 0, 10) = ? ");
       sql.append(" AND T.ROOM_NO IN('D') ");
       sql.append(" ORDER BY T.CALLING_TIME DESC,T.CUR_WIN ASC ");
       list.addAll(dao.findBySql(sql.toString(),new String[]{curDate},null));
       return  list;
   }
   /**
   *
   * 描述排队信息展示页面
   * @created 2019年03月17日 下午4:04:15
   * @param sqlFilter
   * @return
   */
  public List<Map<String,Object>> htCallingLineInfo(SqlFilter sqlFilter){
      String curDate=DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd");
      StringBuffer longWait=new StringBuffer();
      //等候时期最长的前三条
      longWait.append("SELECT * FROM ( SELECT T.*  ");
      longWait.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='0' ");
      longWait.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ?  ");
      longWait.append(" AND T.ROOM_NO IN('H','I')  ");
      longWait.append("ORDER BY T.CREATE_TIME ASC ) ");
      longWait.append(" WHERE ROWNUM<=3 ORDER BY ROWNUM ASC  ");
      List<Map<String,Object>> list = dao.findBySql(longWait.toString(),new String[]{curDate},null);

      StringBuffer sql=new StringBuffer("SELECT T.* ");
      sql.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='6' ");
      sql.append("   AND SUBSTR(T.CREATE_TIME, 0, 10) = ? ");
      sql.append(" AND T.ROOM_NO IN('H','I') ");
      sql.append(" ORDER BY T.CALLING_TIME DESC,T.CUR_WIN ASC ");
      list.addAll(dao.findBySql(sql.toString(),new String[]{curDate},null));
      return  list;
  }
  /**
  *
  * 描述排队信息展示页面
  * @created 2019年03月17日 下午4:04:15
  * @param sqlFilter
  * @return
  */
 public List<Map<String,Object>> jsCallingLineInfo(SqlFilter sqlFilter,String roomNo){
     String curDate=DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd");
     StringBuffer longWait=new StringBuffer();
     //等候时期最长的前三条
     longWait.append("SELECT * FROM ( SELECT T.*  ");
     longWait.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='0' ");
     longWait.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ?  ");
     longWait.append(" AND T.ROOM_NO = ?  ");
     longWait.append("ORDER BY T.CREATE_TIME ASC ) ");
     longWait.append(" WHERE ROWNUM<=3 ORDER BY ROWNUM ASC  ");
     List<Map<String,Object>> list = dao.findBySql(longWait.toString(),new String[]{curDate,roomNo},null);

     StringBuffer sql=new StringBuffer("SELECT T.* ");
     sql.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='6' ");
     sql.append("   AND SUBSTR(T.CREATE_TIME, 0, 10) = ? ");
     sql.append(" AND T.ROOM_NO = ?  ");
     sql.append(" ORDER BY T.CALLING_TIME DESC,T.CUR_WIN ASC ");
     list.addAll(dao.findBySql(sql.toString(),new String[]{curDate,roomNo},null));
     return  list;
 }

 /**
  * 描述:
  *
  * @author Madison You
  * @created 2021/3/30 9:56:00
  * @param
  * @return
  */
    @Override
    public List<Map<String, Object>> xmtzCallingLineInfo(SqlFilter filter) {
        String curDate=DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd");
        StringBuffer longWait=new StringBuffer();
        //等候时期最长的前三条
        longWait.append("SELECT * FROM ( SELECT T.*  ");
        longWait.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='0' ");
        longWait.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ?  ");
        longWait.append(" AND T.ROOM_NO IN('D') AND T.BUSINESS_CODE = '028' ");
        longWait.append("ORDER BY T.CREATE_TIME ASC ) ");
        longWait.append(" WHERE ROWNUM<=3 ORDER BY ROWNUM ASC  ");
        List<Map<String,Object>> list = dao.findBySql(longWait.toString(),new String[]{curDate},null);

        StringBuffer sql=new StringBuffer("SELECT T.* ");
        sql.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='6' ");
        sql.append("   AND SUBSTR(T.CREATE_TIME, 0, 10) = ? ");
        sql.append(" AND T.ROOM_NO IN('D') AND T.BUSINESS_CODE = '028' ");
        sql.append(" ORDER BY T.CALLING_TIME DESC,T.CUR_WIN ASC ");
        list.addAll(dao.findBySql(sql.toString(),new String[]{curDate},null));
        return  list;
    }

    /**
     * 
     * 描述 获取预约部门
     * @author Danto Huang
     * @created 2016-1-16 下午2:32:00
     * @return
     */
    public List<Map<String,Object>> findAppointDepartWait(String cardNo){
        StringBuffer sql = new StringBuffer("");
        sql.append("select t.*,room.dic_desc from T_CKBS_DEPART t ");
        sql.append("left join (select d.dic_code, d.dic_name, d.dic_desc from t_msjw_system_dictionary d ");
        sql.append("where d.type_id = (select s.type_id from t_msjw_system_dictype s where s.type_code = 'roomNo')) ");
        sql.append("room on room.dic_code = t.belong_no ");
        sql.append("where t.depart_id in ");
        sql.append("(select (case sd.tree_level when 3 then sd.depart_id when 4 then sd.parent_id end) ");
        sql.append("as adepart_id from T_BESPEAK_APPLY a ");
        sql.append("left join t_msjw_system_department sd on sd.depart_id = a.depart_id ");
        sql.append("where a.card = ?) order by t.belong_no ");

        return dao.findBySql(sql.toString(), new Object[]{cardNo}, null);
    }

    /**
     * 
     * 描述 取号人是否VIP
     * @author Danto Huang
     * @created 2016-1-17 上午10:40:30
     * @param cardNo
     * @return
     */
    public boolean isVip(String cardNo){
        Map<String,Object> vip = dao.getByJdbc("T_CKBS_VIP", new String[]{"VIP_CARDNO"}, new Object[]{cardNo});
        if(vip==null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 
     * 描述 获取最大取号顺序
     * @author Danto Huang
     * @created 2016-1-17 上午10:48:20
     * @param winNo
     * @return
     */
    public int getMaxTakeSn(String winNo){
        return dao.getMaxTakeSn(winNo);
    }

    /**
     * 
     * 描述 根据部门ID获取窗口列表
     * @author Danto Huang
     * @created 2016-1-18 上午9:38:20
     * @param departId
     * @return
     */
    public List<Map<String,Object>> getWinListByDepartId(String departId){
        String sql = "select t.depart_name,t.win_no from T_CKBS_SERVICEWIN where t.depart_id=? order by t.win_no";
        return dao.findBySql(sql, new Object[]{departId}, null);
    }

    /**
     * 
     * 描述 根据部门ID获取所属单位下一个可生成排队号窗口列表
     * @author Danto Huang
     * @created 2016-1-18 上午10:15:56
     * @param departId
     * @return
     */
    public String getNextWinByDepartId(String departId){
        String winNo = null;
        StringBuffer sql = new StringBuffer("");
        sql.append("select tcs.win_no,decode(wm.take_sn, null, 0, wm.take_sn) take_sn from T_CKBS_SERVICEWIN tcs ");
        sql.append("left join (select t.win_no, max(t.take_sn) take_sn from t_ckbs_numrecord t ");
        sql.append("where substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
        sql.append("group by t.win_no) wm on wm.win_no = tcs.win_no where tcs.depart_id =? and tcs.is_use=1 ");
        sql.append("group by tcs.win_no,wm.take_sn order by take_sn,tcs.win_no ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{departId}, null);
        if(list!=null&&list.size()>0){
            for(Map map : list){
                if(map.get("TAKE_SN").toString().equals("0")){
                    winNo = map.get("WIN_NO").toString();
                    break;
                }
            }
            if(winNo==null){
                sql = new StringBuffer("select win_no from (select tcs.win_no, ");
                sql.append("decode(wm.take_sn, null, 0, wm.take_sn) take_sn from T_CKBS_SERVICEWIN tcs ");
                sql.append("left join (select t.win_no, max(t.take_sn) take_sn from t_ckbs_numrecord t ");
                sql.append("where substr(t.create_time, 0, 10)=to_char(sysdate, 'yyyy-mm-dd') group by t.win_no) wm ");
                sql.append("on wm.win_no = tcs.win_no where tcs.depart_id = ? ");
                sql.append("and take_sn = (select max(take_sn) from (select tcs.win_no, ");
                sql.append("decode(wm.take_sn, null, 0, wm.take_sn) take_sn from T_CKBS_SERVICEWIN tcs ");
                sql.append("left join (select t.win_no, max(t.take_sn) take_sn from t_ckbs_numrecord t ");
                sql.append("where substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
                sql.append("group by t.win_no) wm on wm.win_no = tcs.win_no where tcs.depart_id = ?)))");
                sql.append(" group by win_no ");
                /*List<Map<String,Object>> winList = */
                 /* dao.findBySql(sql.toString(), new Object[]{departId,departId}, null);*/
                winNo = list.get(0).get("win_no").toString();
            }
            return winNo;
        }else{
            return null;
        }   
    }

    /**
     * 
     * 描述 窗口等待人数
     * @author Danto Huang
     * @created 2016-1-19 上午11:54:40
     * @param winNo
     * @return
     */
    public String getWaitCountByWinNO(String winNo){
        return dao.getWaitCountByWinNO(winNo);        
    }

    /**
     * 
     * 描述 获取等待广播的叫号记录
     * @author Danto Huang
     * @created 2016-1-25 下午6:24:44
     * @return
     */
    public List<Map<String,Object>> getWaitCall(){
        String sql = "select t.* from T_CKBS_CALLWAIT t where t.take_status=0";
        return dao.findBySql(sql, null, null);
    }

    /**
     * 
     * 描述   是否窗口办事部门
     * @author Danto Huang
     * @created 2016-5-22 下午2:05:56
     * @param winNo
     * @return
     */
    public boolean isWinDepart(String winNo){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from T_CKBS_SERVICEWIN t where t.windepart_no like ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{"%"+winNo+"%"}, null);
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 
     * 描述  是否已在等待队列中
     * @author Danto Huang
     * @created 2016-2-2 下午3:22:31
     * @param departId
     * @param cardNo
     * @return
     */
    public boolean isWaiting(String winNo,String cardNo){
        StringBuffer sql = new StringBuffer();
        /*sql.append("select tcn.* from T_CKBS_SERVICEWIN t ");
        sql.append("left join t_ckbs_numrecord tcn on t.win_no = tcn.win_no ");*/
        sql.append("select * from t_ckbs_numrecord t ");
        sql.append("where t.win_no=? and t.line_cardno=? and t.call_status='0' ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{winNo,cardNo}, null);
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 
     * 描述 根据用户名获取窗口信息
     * @author Danto Huang
     * @created 2016-2-17 下午5:22:14
     * @param username
     * @return
     */
    public Map<String,Object> getWinInfoByUsername(String username){
        String sql = "select * from T_CKBS_SERVICEWIN t where t.username=? and t.is_use=1";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{username}, null);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    

    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-2-26 下午4:13:24
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String winNo){
        StringBuffer sql = new StringBuffer("select D.WIN_NO as text,D.WIN_NO as value FROM")
                .append(" T_CKBS_SERVICEWIN D WHERE D.WIN_NO<>? GROUP BY D.WIN_NO ORDER BY D.WIN_NO ASC");
        return dao.findBySql(sql.toString(), new Object[]{winNo}, null);
    }
    /**
     * 
     * 描述 获取取号等待数据列表
     * @author Danto Huang
     * @created 2016-2-29 下午3:22:09
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findNolistBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.record_id,t.cur_win,t.line_no,t.line_name,t.line_cardno,t.is_vip,t.create_time, ");
        sql.append("ckl.dic_name as ckywl,t.call_status,t.TOTOPREASON from T_CKBS_NUMRECORD t ");
        sql.append("left join (select d.dic_code,d.dic_name from t_msjw_system_dictionary d where ");
        sql.append("d.type_id = (select dt.type_id from t_msjw_system_dictype dt where dt.type_code='winDepartNo')");
        sql.append(")ckl on ckl.dic_code = t.win_no ");
        sql.append("where substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd') ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取窗口单位
     * @author Danto Huang
     * @created 2016-3-16 下午3:02:28
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findWinDepartBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_CKBS_DEPART t where 1=1 ");        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 获取窗口单位子部门
     * @author Danto Huang
     * @created 2016-3-16 下午3:02:28
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findChildDepartBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_CKBS_DEPART_CHILD t where 1=1 ");        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 获取屏编号
     * @author Danto Huang
     * @created 2016-3-24 下午4:41:20
     * @param winNo
     * @return
     */
    public String getScreenNoByWinNo(String winNo){
        String sql = "select * from T_CKBS_WIN_SCREEN t where t.win_no=?";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{winNo}, null);
        if(list!=null&&list.size()>0){
            return list.get(0).get("SCREEN_NO").toString();
        }else{
            return null;
        }
    }

    /**
     * 
     * 描述 获取窗口屏绑定关系列表
     * @author Danto Huang
     * @created 2016-4-19 下午2:34:44
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
     * 描述
     * @author Danto Huang
     * @created 2016-2-26 下午4:13:24
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findWinsForSelect(String win){
        StringBuffer sql = new StringBuffer("select D.WIN_NO as text,D.WIN_NO as value,D.SCREEN_NO FROM")
                .append(" T_CKBS_WIN_SCREEN D where D.USE_STATUS='1' ORDER BY to_number(D.WIN_NO) ASC");
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * 
     * 描述   定时过号
     * @author Danto Huang
     * @created 2016-5-22 下午1:12:50
     * @param date
     */
    public void overDueNo(String date){
        String sql = "update T_CKBS_NUMRECORD t set t.call_Status=2 "
                + "where (t.call_status=0 or t.call_status=6) and t.create_time<?";
        dao.executeSql(sql, new Object[]{date});
    }

    /**
     * 
     * 描述   获取取号部门大类
     * @author Danto Huang
     * @created 2016-5-22 下午2:32:12
     * @param departId
     * @return
     */
    public String getWinDepartNo(String departId){
        String sql = "select * from T_CKBS_DEPART_BUS t where t.depart_id=?";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{departId}, null);
        if(list!=null&&list.size()>0){
            return list.get(0).get("BUS_CODE").toString();
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述   获取排队号留意窗口
     * @author Danto Huang
     * @created 2016-5-23 下午6:47:50
     * @param winDepartNo
     * @return
     */
    public String getCareWin(String winDepartNo){
        String sql = "select wmsys.wm_concat(tw.win_no) win_no from(select t.win_no from t_ckbs_servicewin t "
                + "where t.windepart_no like ? group by t.win_no) tw";
        return dao.getByJdbc(sql, new Object[]{"%"+winDepartNo+"%"}).get("WIN_NO").toString();
    }

    /**
     * 
     * 描述    获取叫号记录列表
     * @author Danto Huang
     * @created 2016-5-24 上午8:56:22
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getCallRecord(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from t_ckbs_callwait t where t.type='callNum' ");        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述   根据部门id获取所属大厅
     * @author Danto Huang
     * @created 2016-6-20 下午2:53:24
     * @param deptId
     * @return
     */
    public Map<String,Object> getRoomNoByDeptId(String deptId){
        String sql = "select t.* from T_CKBS_DEPART t where t.depart_id in "
                + "(?,(select d.parent_id from t_msjw_system_department d where d.depart_id = ?))";
        List<Map<String,Object>> list=dao.findBySql(sql, new Object[]{deptId,deptId}, null);
        return list.get(0);
    }
    
    /**
     * 
     * 描述   根据窗口号获取所属大厅
     * @author Danto Huang
     * @created 2016-6-20 下午4:01:03
     * @param winNo
     * @return
     */
    public String getRoomNoByWinNo(String winNo){
        String userName = AppUtil.getLoginUser().getUsername();
        StringBuffer sql = new StringBuffer("");
        sql.append("select t.belong_no from T_CKBS_SERVICEWIN t where t.username=? and t.win_no=? ");
        sql.append("group by t.belong_no ");
        /*sql.append("select t.* from T_CKBS_DEPART t where t.depart_id in ");
        sql.append("((select t.depart_id from T_CKBS_SERVICEWIN t where t.win_no = ? and t.username = ?),");
        sql.append("(select d.parent_id from t_msjw_system_department d where d.depart_id = ");
        sql.append("(select t.depart_id from T_CKBS_SERVICEWIN t where t.win_no = ? and t.username = ?)))");*/
        return dao.getByJdbc(sql.toString(), new Object[]{userName,winNo}).get("belong_no").toString();
    }
    
    /**
     * 
     * 描述   获取部门业务关系对应列表
     * @author Danto Huang
     * @created 2016-6-22 上午11:37:23
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getDepartBusList(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,bus.dic_name bus_name from T_CKBS_DEPART_BUS t ");
        sql.append("left join (select d.dic_code,d.dic_name from t_msjw_system_dictionary d where ");
        sql.append("d.type_id = (select dt.type_id from t_msjw_system_dictype dt where dt.type_code='winDepartNo')");
        sql.append(")bus on bus.dic_code = t.bus_code where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
        
    }
    /**
     * 
     * 描述   获取取号子部门
     * @author Danto Huang
     * @created 2016-7-5 下午3:25:15
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId){
        String sql = "select * from T_CKBS_DEPART_CHILD WHERE";
        sql+=" PARENT_ID=? ORDER BY TREE_SN ASC";
        return dao.findBySql(sql, new Object[]{parentId}, null);
    }
    /**
     * 
     * 描述   获取预约列表
     * @author Danto Huang
     * @created 2016-12-5 上午9:33:04
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAppointmentBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name from T_BESPEAK_APPLY t ");
        sql.append("left join t_msjw_system_department d on d.depart_id = t.depart_id ");
        sql.append("where 1=1 ");
        sql.append("and t.status<>0 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述   获取预约列表
     * @author Danto Huang
     * @created 2016-12-5 上午9:33:04
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAppointmentDataBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name,d.parent_id from T_BESPEAK_APPLY t ");
        sql.append("left join t_msjw_system_department d on d.depart_id = t.depart_id ");
        sql.append("where 1=1 ");
        sql.append("and t.status = 1 ");
        sql.append("and t.is_take = 0 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取重新取号列表（已经取号但未叫号）
     * @author Water Guo
     * @created 2017-5-12 上午10:30:36
     * @param filter
     * @return
     * @see net.evecom.platform.call.service.CallService#findAgainDataBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String,Object>> findAgainDataBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name,d.parent_id from T_CKBS_NUMRECORD t ");
        sql.append("left join t_msjw_system_department d on d.depart_id = t.depart_id ");
        sql.append("where 1=1 ");
        sql.append("and t.call_status = 0 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述   定时作废网上预约
     * @author Danto Huang
     * @created 2016-12-9 下午2:14:46
     * @param date
     */
    public void passAppointment(String date){
        long currentTime = System.currentTimeMillis();
        currentTime -= 30*60*1000;
        String timeInterval = DateTimeUtil.getStrOfDate(new Date(currentTime), "HH:mm");
        StringBuffer sql = new StringBuffer();
        sql.append("update T_BESPEAK_APPLY t set t.status=2 ");
        sql.append("where t.is_take=0 and t.status=1 and t.date_time=? and t.begin_time<? ");
        
        dao.executeSql(sql.toString(), new Object[]{date,timeInterval});
        
        sql = new StringBuffer();
        sql.append("update T_CKBS_APPOINTMENT_APPLY t set t.status=2 ");
        sql.append("where t.is_take=0 and t.status=1 and t.date_time=? and t.begin_time<? ");
        
        dao.executeSql(sql.toString(), new Object[]{date,timeInterval});
    }

    @Override
    public boolean isExist(String lineNo) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(*) as num from T_CKBS_NUMRECORD t where t.line_no = ?  ");
        sql.append(" and substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{lineNo}, null);
        String num =  list.get(0).get("num").toString();
        if (num.equals("0")) {
            return false;
        }
        return true;
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年10月24日 下午4:42:06
     * @param lineNo
     * @param lineDate
     * @return
     */
    public Map<String,Object> getRecordByNoAndDate(String lineNo,String lineDate){
        String sql = "select * from T_CKBS_NUMRECORD t where t.line_no=? and substr(t.create_time,0,10)=? ";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{lineNo,lineDate}, null);
        if(list==null||list.size()==0){
            return null;
        }else{
            return list.get(0);
        }
    }

    /**
     * 
     * 描述    根据业务编码获取排队信息
     * @author Danto Huang
     * @created 2019年4月30日 上午10:11:15
     * @param sqlFilter
     * @return
     */
   public List<Map<String,Object>> callingLineInfoForMarriage(SqlFilter sqlFilter){
        String curDate=DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd");
        StringBuffer longWait=new StringBuffer();
        //等候时期最长的前三条
        longWait.append("SELECT * FROM ( SELECT T.*  ");
        longWait.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='0' ");
        longWait.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ?  ");
        longWait.append(" AND T.ROOM_NO = 'Y' ");
        longWait.append("ORDER BY T.CREATE_TIME ASC ) ");
        longWait.append(" WHERE ROWNUM<=3 ORDER BY ROWNUM ASC  ");
        List<Map<String,Object>> list = dao.findBySql(longWait.toString(),new String[]{curDate},null);

        StringBuffer sql=new StringBuffer("SELECT T.* ");
        sql.append(" FROM T_CKBS_QUEUERECORD T WHERE T.CALL_STATUS='6' ");
        sql.append(" AND SUBSTR(T.CREATE_TIME, 0, 10) = ? ");
        sql.append(" AND T.ROOM_NO = 'Y' ");
        sql.append(" ORDER BY T.CALLING_TIME DESC,T.CUR_WIN ASC ");
        list.addAll(dao.findBySql(sql.toString(),new String[]{curDate},null));
        return  list;
    }
}
