/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.dao.CommonXKDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 描述：许可daoImpl
 * 
 * @author Water Guo
 * @created 2017-4-12 上午9:55:31
 */
@SuppressWarnings("rawtypes")
@Repository("commonXKDao")
public class CommonXKDaoImpl extends BaseDaoImpl implements CommonXKDao {
    /**
     * 
     * 描述：获得许可list
     * 
     * @author Water Guo
     * @created 2017-4-12 上午11:53:56
     * @param xktype
     * @param departId
     * @return
     * @see net.evecom.platform.wsbs.dao.CommonXKDao#findXKList(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findXKList(String itemId, String departId) {
        String sql = "select x.xkcontent as text, x.xkcontent as value from t_wsbs_xk x "
                + " where  x.item_id=? and "
                + "x.depart_id=?  order by x.create_time desc";
        return this.findBySql(sql, new Object[] { itemId, departId }, null);
    }
    /**
     * 
     * 描述：判断是否存在
     * @author Water Guo
     * @created 2017-4-13 上午8:38:23
     * @param xkId
     * @param departID
     * @param type
     * @param content
     * @return
     */
    public String getXKID(String itemId,String xkcontent){
        String xkId="";
        String departId = AppUtil.getLoginUser().getDepId();
        StringBuffer sql = new StringBuffer("select XK_ID as xkId from T_WSBS_XK");
        sql.append(" where ").append("item_id=? and ");
        sql.append("xkcontent = ? ");
        sql.append(" and depart_id=? ");
        Object[] object = null;        
        object = new Object[] { itemId, xkcontent,departId };
        Map<String,Object> map=this.getByJdbc(sql.toString(), object);
        if(map!=null){
            xkId=map.get("xkId").toString();
        }
        return xkId;
    }
}
