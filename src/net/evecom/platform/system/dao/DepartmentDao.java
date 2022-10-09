/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.Map;
import java.util.Set;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 部门操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface DepartmentDao extends BaseDao {
    /**
     * 
     * 描述 根据部门ID删除掉部门,级联更新用户的部门字段
     * @author Flex Hu
     * @created 2014年10月4日 下午1:57:02
     * @param depId
     */
    public void removeDepart(String depId);
    /**
     * 
     * 描述 获取根部门
     * @author Flex Hu
     * @created 2014年10月20日 下午2:32:54
     * @return
     */
    public Map<String,Object> getRootDepart();
    /**
     * 根据用户ID获取到所授权的业务编码集合
     * 
     * @param userId
     * @return
     */
    public Set<String> getBusCodeSetByUserId(String userId);
    /**
     * 
     * 描述 根据用户帐号获取部门信息
     * @author Flex Hu
     * @created 2016年1月30日 下午5:18:17
     * @param username
     * @return
     */
    public Map<String,Object> getDepInfoByUserAccount(String userAccount);
}
