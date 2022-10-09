/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bdc.dao.BdcFwzldjbaDao;
import net.evecom.platform.hflow.util.Jbpm6Constants;

/**
 * 描述  不动产抵押权登记操作dao
 * @author  Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("bdcFwzldjbaDao")
public class BdcFwzldjbaDaoImpl extends BaseDaoImpl implements BdcFwzldjbaDao {
    
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*, ");
        sql.append(" s.fczmwj ,s.cz_addr,s.cz_qx_begin,s.cz_qx_end,s.zj ,s.bazmbh ,s.cjfs ,s.czbw ,s.zlyt ,s.jjjg_name,s.jjr_name ,");
        sql.append(" s.jjrlx_phone,s.slry ,s.sl_bz,s.czr_name ,s.qlbl ,s.czrxz,s.czr_card_type,s.czr_card_no,s.czr_phone,s.czr_sex,");
        sql.append(" s.czr_email,s.czr_address,s.cz_fddbr_name,s.cz_fddbr_card_type ,s.cz_fddbr_card_no ,s.cz_dlr_name,s.cz_dlr_card_type ,");
        sql.append(" s.cz_dlr_card_no ,s.fw_zl,s.fw_zh,s.fw_szc ,s.fw_hh,s.fw_zcs ,s.fw_ghyt,s.jgsj ,s.ytsm ,s.fw_xz,s.fw_jg,s.jyjg ,");
        sql.append(" s.dytdmj ,s.fttdmj ,s.jzmj ,s.fwgyqk ,s.zyjzmj ,s.ftjzmj ,s.qllx ,s.tdsyqr ,s.qzh,s.fw_bz,decode(s.bazmbh_status,'0','无效','1','有效') as bazmbh_status");        
        sql.append( " FROM JBPM6_EXECUTION T");
        sql.append(" LEFT JOIN JBPM6_FLOWDEF D ON T.DEF_ID=D.DEF_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM ws on ws.item_code = t.item_code ");
        sql.append(" inner join t_bdc_fwzldjba s on t.bus_recordid=s.yw_id ");
        sql.append(" where T.RUN_STATUS='2' ");
        
        //限制统计四个事项
      /*  sql.append(" and ws.ITEM_CODE IN ( select dic.dic_code from ");
        sql.append(" T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='bdcItemCode') ");*/
        //改成某些事项id
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = this.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        list = this.setExeWorkHours(list);
        return list;
    }
    /**
     * 
     * 描述 设置工时
     * @author Flex Hu
     * @created 2015年8月22日 下午5:13:49
     * @param list
     * @return
     */
    public List<Map<String,Object>> setExeWorkHours(List<Map<String, Object>> list){
         //遍历list,设置动态工时
        for(Map<String,Object> map:list){
            //获取流程状态
            int runStatus = Integer.parseInt(map.get("RUN_STATUS").toString());
            if(runStatus==Jbpm6Constants.RUNSTATUS_RUNING){
                //获取流程的创建事件
                String createTime = (String) map.get("CREATE_TIME");
                String endTime = DateTimeUtil.getStrOfDate(new Date(),
                        "yyyy-MM-dd HH:mm:ss");
                String timeConsuming = DateTimeUtil.getInternalTime(
                        createTime, endTime);
                map.put("WORK_HOURS", timeConsuming);
            }
        }
        return list;
    }
    
}
