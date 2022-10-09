/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.model.TableInfo;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.bsfw.dao.ShtzDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月10日 上午10:42:08
 */
@Repository("shtzDao")
public class ShtzDaoImpl extends BaseDaoImpl implements ShtzDao {
    
    /**
     * 
     * 描述 获取项目编号的序列号
     * @author Flex Hu
     * @created 2015年12月2日 上午11:23:35
     * @param flowVars
     * @return
     */
    public String getXmbhSeq(Map<String,Object> flowVars){
        int seqValue = this.jdbcTemplate.queryForInt("select SEQ_BSFW_PROJECTNUMBER.nextval FROM DUAL");
        String nextValue = StringUtil.getFormatNumber(6, String.valueOf(seqValue));
        return nextValue;
    }
}
