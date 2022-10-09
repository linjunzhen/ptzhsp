/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.${packageName}.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.${packageName}.dao.${className}Dao;

/**
 * 描述  ${mainEntityName}操作dao
 * @author  ${author}
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("${className?uncap_first}Dao")
public class ${className}DaoImpl extends BaseDaoImpl implements ${className}Dao {

}
