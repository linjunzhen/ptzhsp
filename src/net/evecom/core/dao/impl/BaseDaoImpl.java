/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:43:49
 */
@Repository
public class BaseDaoImpl<T> extends GenericDaoImpl<T, String> implements
        BaseDao<T> {
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:10:55
     * @param sessionFactory
     */
    @Autowired
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:11:02
     * @param jdbcTemplate
     * @see net.evecom.core.dao.impl.GenericDaoImpl#setJdbcTemplate(org.springframework.jdbc.core.JdbcTemplate)
     */
    @Autowired
    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}
