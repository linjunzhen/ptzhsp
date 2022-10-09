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
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.bsfw.dao.SwbDataResDao;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月28日 下午4:21:09
 */
@Repository("swbDataResDao")
public class SwbDataResDaoImpl extends BaseDaoImpl implements SwbDataResDao {
    
    /**
     * 
     * 描述 是否保存过办件信息类型的指令数据
     * @author Flex Hu
     * @created 2015年12月28日 下午5:04:13
     * @param exeId
     * @return
     */
    public boolean isHaveSaveBjxxInfo(String exeId){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) ");
        sql.append(" FROM T_BSFW_SWBDATA_RES R WHERE R.EXE_ID=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
}
