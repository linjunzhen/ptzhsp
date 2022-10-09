/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.call.dao.NewCallDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年7月3日 下午3:57:24
 */
@Repository("newCallDao")
public class NewCallDaoImpl extends BaseDaoImpl implements NewCallDao {

    /**
     * 
     * 描述    获取最大取号顺序
     * @author Danto Huang
     * @created 2018年7月5日 下午3:53:09
     * @param businessCode
     * @return
     */
    public int getMaxTakeSn(String businessCode){
        String sql = "select decode(max(t.TAKE_SN),null,0,max(t.TAKE_SN)) FROM T_CKBS_QUEUERECORD t "
                + "WHERE t.BUSINESS_CODE=? and substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd')";
        return this.jdbcTemplate.queryForInt(sql, new Object[] { businessCode });
    }

    /**
     * 
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param businessCode
     * @return
     */
    public int getWaitingNumByBusinessCode(String businessCode){
        String sql = "select count(*) from T_CKBS_QUEUERECORD t where t.BUSINESS_CODE = ? and t.call_status = '0'" +
                "and substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd')";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{businessCode});
    }
    /**
     *
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param roomNo
     * @return
     */
    public int getWaitingNumByRoomNo(String roomNo){
        String sql = "select count(*) from T_CKBS_QUEUERECORD t where t.ROOM_NO = ? and t.call_status = '0'" +
                "and substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd')";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{roomNo});
    }
}
