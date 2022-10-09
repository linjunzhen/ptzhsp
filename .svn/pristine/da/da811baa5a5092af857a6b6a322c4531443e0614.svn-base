/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.cms.dao.AuditContentDao;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 文章审核操作dao
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("auditContentDao")
public class AuditContentDaoImpl extends BaseDaoImpl implements AuditContentDao {

    @Override
    public void updateAuditStatus(String contentId, String auditOpinion, int auditStatus) {
        // TODO Auto-generated method stub
        SysUser sysUser = AppUtil.getLoginUser();
        String sql = "UPDATE T_CMS_ARTICLE_AUDIT T SET T.AUDIT_STATUS = ? "
                +",AUDIT_USERID=?,AUDIT_USERNAME=? "
                + ", AUDIT_OPINION=? WHERE  T.AUDIT_STATUS = 0 and T.CONTENT_ID=? ";
//        String sql = "UPDATE T_CMS_ARTICLE_AUDIT T SET T.AUDIT_STATUS = ? "
//                + ", AUDIT_OPINION=? WHERE  T.AUDIT_STATUS = 0 and T.CONTENT_ID=? ";
        this.jdbcTemplate.update(
                sql,
                new Object[] { auditStatus, sysUser.getUserId(),
                        sysUser.getUsername(), auditOpinion, contentId });
    }

}
