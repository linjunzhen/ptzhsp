/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.project.dao.ProjectApplyDao;

/**
 * 描述  投资项目申报操作dao
 * @author  Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("projectApplyDao")
public class ProjectApplyDaoImpl extends BaseDaoImpl implements ProjectApplyDao {


    @Override
    public String getSeqValue(String seqName) {
        // TODO Auto-generated method stub
        int seqValue = this.jdbcTemplate.queryForInt("select "+seqName+".nextval FROM DUAL");
        return seqValue+"";
    }

}
