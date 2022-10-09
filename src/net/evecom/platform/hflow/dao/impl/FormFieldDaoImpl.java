/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.FormFieldDao;

/**
 * 描述  表单字段操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("formFieldDao")
public class FormFieldDaoImpl extends BaseDaoImpl implements FormFieldDao {

    /**
     * 
     * 描述 根据表单ID删除掉字段数据
     * @author Flex Hu
     * @created 2015年8月10日 下午6:56:33
     * @param formId
     */
    public void deleteByFormId(String formId){
        this.jdbcTemplate.update("delete from JBPM6_FORMFIELD F WHERE F.FORM_ID=?",new Object[]{formId});
    }
}
