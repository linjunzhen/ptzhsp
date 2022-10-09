/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.ptwg.dao.ItemBespeakDao;
import net.evecom.platform.ptwg.service.ItemBespeakService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-3-3 上午9:10:22
 */
@Service("/itemBespeakService")
public class ItemBespeakServiceImpl extends BaseServiceImpl implements
        ItemBespeakService {
    /**
     * 引入dao
     */
    @Resource
    private ItemBespeakDao dao;
    
    /**
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述   是否可网上预约
     * @author Danto Huang
     * @created 2017-3-3 上午10:44:43
     * @param itemCode
     * @return
     */
    public List<Map<String, Object>> isOnlineBespeak(String itemCode){
        String sql = "select c.* from t_wsbs_serviceitem_catalog c "
                + "left join t_wsbs_serviceitem s on s.catalog_code=c.catalog_code "
                + "where s.item_code=? ";
        String childDepartId = dao.getByJdbc(sql, new Object[]{itemCode}).get("CHILD_DEPART_ID").toString();
        
        String checkSql = "select * from T_BESPEAK_ONLINE_CONFIG t where t.depart_id=?";
        List<Map<String, Object>> list = dao.findBySql(checkSql, new Object[]{childDepartId}, null);
        return list;
    }
    
    /**
     * 
     * 描述   获取用户部门预约信息
     * @author Danto Huang
     * @created 2017-3-21 下午4:34:28
     * @param departId
     * @param idCard
     * @return
     */
    public List<Map<String, Object>> getUserBespeakListByDepartId(String departId,String idCard){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.depart_id,t.date_time,t.begin_time,t.end_time from T_BESPEAK_APPLY t ");
        sql.append("where t.status=1 and t.is_take=0 and t.depart_id=? and t.card=? ");
        sql.append("order by t.date_time asc,t.begin_time asc ");
        return dao.findBySql(sql.toString(), new Object[]{departId,idCard}, null);
    }
}
