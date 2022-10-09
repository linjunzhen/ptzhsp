/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 部门操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface DepartmentService extends BaseService {
    /**
     * 正常状态
     */
    public static final int NORMAL_STATUS = 1;
    /**
     * 删除状态
     */
    public static final int DEL_STATUS = 0;
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
     * 
     * 描述 根据部门编码获取数据
     * @author Flex Hu
     * @created 2014年10月30日 下午5:18:13
     * @param depCode
     * @return
     */
    public Map<String,Object> getByDepCode(String depCode);
    
   /**
    * 
    * 描述 根据sqlfilter获取到部门数据列表
    * @author Water Guo
    * @created 2015年1月23日 上午9:27:00
    * @param sqlFilter
    * @return
    */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据父亲ID获取部门数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId);
    
    /**
     * 
     * 描述 根据父亲ID、类型获取部门数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId,Integer unitType );
    /**
     * 
     * 描述 根据父亲ID获取部门数据
     * @author Flex Hu
     * @created 2014年10月20日 下午3:14:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findDepartList(String parentId);
    /**
     * 
     * 描述 新增或者修改用户,级联更新用户的区划
     * @author Flex Hu
     * @created 2015年9月14日 上午9:56:11
     * @param parentId
     * @param department
     * @return
     */
    public String saveOrUpdateCascadeUser(String parentId,Map<String,Object> department);
    /**
     * 保存开发商信息
     * @param parentId
     * @param department
     * @return
     */
    public String saveDeveloper(String parentId,Map<String,Object> department);
    /**
     * 
     * 描述 获取一级处室
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String parentId);
    /**
     * 
     * 描述 根据用户帐号获取部门信息
     * @author Flex Hu
     * @created 2016年1月30日 下午5:18:17
     * @param username
     * @return
     */
    public Map<String,Object> getDepInfoByUserAccount(String userAccount);
    /**
     * 
     * 描述 获取部门列表
     * @author Flex Hu
     * @created 2016年3月8日 下午1:39:30
     * @return
     */
    public List<Map<String,Object>> findDepList();
    /**
     * 
     * 描述    根据父亲ID获取部门数据(数据授权)
     * @author Danto Huang
     * @created 2016-9-28 上午8:51:06
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentIdAuth(String parentId);
}
