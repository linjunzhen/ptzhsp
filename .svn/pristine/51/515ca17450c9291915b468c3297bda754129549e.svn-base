/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.call.dao.CallDao;

/**
 * 描述 叫号管理
 * @author Danto Huang
 * @created 2016-1-13 上午11:25:37
 */
@Repository("callDao")
public class CallDaoImpl extends BaseDaoImpl implements CallDao {
    /**
     * 
     * 描述 获取最大取号顺序
     * @author Danto Huang
     * @created 2016-1-17 上午10:48:20
     * @param winNo
     * @return
     */
    public int getMaxTakeSn(String winNo){
        String sql = "select decode(max(t.TAKE_SN),null,0,max(t.TAKE_SN)) FROM T_CKBS_NUMRECORD t "
                + "WHERE t.WIN_NO=? and substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd')";
        return this.jdbcTemplate.queryForInt(sql, new Object[] { winNo });
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
        String sql = "select count(*) from T_CKBS_NUMRECORD t where t.win_no = ? and t.call_status = '0'" +
                "and substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd')";
        int count = this.jdbcTemplate.queryForInt(sql, new Object[]{winNo});
        return String.valueOf(count);
    }
}
