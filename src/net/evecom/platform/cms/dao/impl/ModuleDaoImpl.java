/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.cms.dao.ModuleDao;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述  栏目操作dao
 * @author  Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("moduleDao")
public class ModuleDaoImpl extends BaseDaoImpl implements ModuleDao {

    @Override
    public List<Map<String, Object>> findByParentId(String parentId) {
        String sql = "select * from T_CMS_ARTICLE_MODULE WHERE PARENT_ID=? ORDER BY TREE_SN ASC";
        return this.findBySql(sql, new Object[]{parentId}, null);
    }

    @Override
    public List<Map<String, Object>> findByContentId(String id) {
        String sql = "select CONTENT_ID,CONTENT_TITLE from T_CMS_ARTICLE_CONTENT  WHERE MODULE_ID=?  "
                + "order by istop desc, release_time desc ";
        return this.findBySql(sql, new Object[]{id}, null);
    }
    @Override
    public List<Map<String, Object>> findRoleByParentId(String parentId) {
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        Set<String> roleCodes = sysUser.getRoleCodes();
        StringBuffer codes=new StringBuffer();
        for (String string : roleCodes) {
            codes.append(",'").append(string).append("'");
        }
        if(StringUtils.isNotEmpty(codes) ){
            codes =new StringBuffer(codes.substring(1, codes.length()));
        }
        String sql = "select distinct M.* from T_CMS_ARTICLE_MODULE M join "
                + " T_SYSTEM_MODULE_SYSROLE S ON M.MODULE_ID = S.MODULE_ID "
                + " WHERE M.PARENT_ID=?  and s.role_id in(select r.role_id "
                + " from t_msjw_system_sysrole r where r.role_code" 
                +" in ("+codes.toString()+")) ORDER BY M.TREE_SN ASC ";
        return this.findBySql(sql, new Object[]{parentId}, null);
    }
}
