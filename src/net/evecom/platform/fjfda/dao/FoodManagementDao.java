/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 食品经营操作dao
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FoodManagementDao extends BaseDao {

    /**
     * 描述 根据经营场所获取条数
     * @author Faker Li
     * @created 2016年7月15日 下午1:18:32
     * @param jYCS
     * @return
     */
    public int getCountNumByJycs(String jYCS);

    /**
     * 描述 根据经营场所获取结果数据
     * @author Faker Li
     * @created 2016年7月29日 下午1:02:51
     * @param string
     * @return
     */
    public Map<String, Object> getSpjyxkxxByJycs(String jycs,String exe_id);

}
