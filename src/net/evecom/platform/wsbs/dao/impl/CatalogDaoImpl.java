/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.CatalogDao;

/**
 * 描述  事项目录操作dao
 * @author  Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("catalogDao")
public class CatalogDaoImpl extends BaseDaoImpl implements CatalogDao {

    /**
     * 
     * 描述 获取表中最大的排序值
     * @author Faker Li
     * @created 2015年10月27日 下午3:06:23
     * @return
     * @see net.evecom.platform.wsbs.dao.CatalogDao#getMaxSn()
     */
    public int getMaxSn() {
        StringBuffer sql = new StringBuffer("select max(C_SN) FROM T_WSBS_SERVICEITEM_CATALOG "
                + "");
        return this.jdbcTemplate.queryForInt(sql.toString());
    }

    /**
     * 
     * 描述 根据部门编码+目录性质字典值获取最大次序
     * @author Faker Li
     * @created 2015年10月28日 上午9:39:44
     * @param departsxxzcode
     * @return
     * @see net.evecom.platform.wsbs.dao.CatalogDao#getMaxNumCode(java.lang.String)
     */
    public int getMaxNumCode(String departsxxzcode) {
        StringBuffer sql = new StringBuffer("select max(NUM_CODE) FROM T_WSBS_SERVICEITEM_CATALOG "
                + " WHERE DEPART_SXXZ_CODE = ?");
        return this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{departsxxzcode});
    }

    /**
     * 
     * 描述 更新排序字段
     * @author Faker Li
     * @created 2015年10月28日 上午11:07:28
     * @param catalogIds
     * @see net.evecom.platform.wsbs.dao.CatalogDao#updateSn(java.lang.String[])
     */
    public void updateSn(String[] catalogIds) {
        int[] oldSns = new int[catalogIds.length];
        StringBuffer sql = new StringBuffer("");
        sql.append("select C_SN FROM T_WSBS_SERVICEITEM_CATALOG ").append(" WHERE CATALOG_ID=? ");
        for (int i = 0; i < catalogIds.length; i++) {
            int dicSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { catalogIds[i] });
            oldSns[i] = dicSn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer("update T_WSBS_SERVICEITEM_CATALOG T ")
                .append(" SET T.C_SN=? WHERE T.CATALOG_ID=? ");
        for (int i = 0; i < catalogIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], catalogIds[i] });
        }
        
    }

    /**
     * 
     * 描述 根据catalogCode获取catalogname
     * @author Faker Li
     * @created 2015年10月29日 上午9:01:29
     * @param catalogCode
     * @return
     * @see net.evecom.platform.wsbs.dao.CatalogDao#getCatalogName(java.lang.String)
     */
    public Map<String,Object> getCatalogByCatalogCode(String catalogCode) {
        StringBuffer sql = new StringBuffer("select t.* ");
        sql.append(" from T_WSBS_SERVICEITEM_CATALOG t where t.catalog_code=? ");
        return this.jdbcTemplate.queryForMap(sql.toString(),new Object[] { catalogCode});
    }

}
