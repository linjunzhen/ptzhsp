/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.UserGroupDao;
import net.evecom.platform.system.service.UserGroupService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 用户组操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("userGroupService")
public class UserGroupServiceImpl extends BaseServiceImpl implements UserGroupService {
    /**
     * 所引入的dao
     */
    @Resource
    private UserGroupDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_SYSTEM_USERGROUP T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 保存用户组和用户中间表
     * @author Flex Hu
     * @created 2014年10月11日 下午5:23:23
     * @param userIds
     * @param groupId
     */
    public void saveGroupUsers(String[] userIds,String groupId){
        dao.saveGroupUsers(userIds, groupId);
    }
    
    /**
     * 
     * 描述 删除用户组信息表,级联删除掉中间表数据
     * @author Flex Hu
     * @created 2014年10月11日 下午5:34:17
     * @param groupIds
     */
    public void removeCascade(String groupIds){
        StringBuffer delSql = new StringBuffer("delete from T_SYSTEM_SYSUSER_GROUP ").append("WHERE GROUP_ID IN ");
        String ids = StringUtil.getValueArray(groupIds);
        delSql.append(ids);
        this.dao.executeSql(delSql.toString(), null);
        dao.remove("T_SYSTEM_USERGROUP","GROUP_ID",groupIds.split(","));
    }
}
