/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.FlowDefDao;

/**
 * 描述  流程定义操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("flowDefDao")
public class FlowDefDaoImpl extends BaseDaoImpl implements FlowDefDao {

    /**
     * 
     * 描述 根据流程定义KEY判断是否存在
     * @author Flex Hu
     * @created 2015年8月7日 上午9:01:13
     * @param defKey
     * @return
     */
    public boolean isExists(String defKey){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_FLOWDEF F");
        sql.append(" WHERE F.DEF_KEY=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{defKey});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 将绑定表单的ID更新为空
     * @author Flex Hu
     * @created 2015年8月10日 上午10:49:42
     * @param formId
     */
    public void updateBindFormIdToNull(String formId){
        String sql = "update JBPM6_FLOWDEF F SET F.BIND_FORMID=null WHERE F.BIND_FORMID=?";
        this.jdbcTemplate.update(sql,new Object[]{formId});
    }
    
    /**
     * 
     * 描述 根据流程定义获取流程版本号
     * @author Flex Hu
     * @created 2015年8月15日 下午1:12:57
     * @param defId
     * @return
     */
    public int getFlowVersion(String defId){
        StringBuffer sql = new StringBuffer("select F.VERSION FROM JBPM6_FLOWDEF F");
        sql.append(" WHERE F.DEF_ID=? ");
        int flowVersion = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{defId});
        return flowVersion;
    }
    
    /**
     * 
     * 描述 根据流程定义ID获取流程定义名称S
     * @author Flex Hu
     * @created 2015年10月26日 下午5:20:54
     * @param defIds
     * @return
     */
    public String getDefNames(String defIds){
        StringBuffer sql = new StringBuffer("select WMSYS.WM_CONCAT(t.def_name) DEFNAMES");
        sql.append(" from JBPM6_FLOWDEF t where t.def_id in").append(StringUtil.getValueArray(defIds));
        String defNames = (String) this.getObjectBySql(sql.toString(), null);
        return defNames;
    }
}
