/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.wsbs.dao.CommonProblemDao;

/**
 * 描述  常见问题操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("commonProblemDao")
public class CommonProblemDaoImpl extends BaseDaoImpl implements CommonProblemDao {

    /**
     * 
     * 描述 保存问题服务类别中间表
     * @author Flex Hu
     * @created 2015年9月18日 下午5:17:33
     * @param problemId
     * @param typeIds
     */
    public void saveCommonBusType(String problemId,String[] typeIds){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_PROBLEM_BUSTYPE");
        sql.append("(TYPE_ID,PROBLEM_ID) values(?,?) ");
        for(String typeId:typeIds){
            this.jdbcTemplate.update(sql.toString(),new Object[]{typeId,problemId});
        }
    }
}
