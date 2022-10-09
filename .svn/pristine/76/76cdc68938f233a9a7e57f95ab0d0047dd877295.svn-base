/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 
 * 描述 编号管理操作dao
 * 
 * @author Derek Zhang
 * @created 2015年10月7日 下午2:45:38
 */

@SuppressWarnings("rawtypes")
public interface SwbInterfaceDao extends BaseDao {
    /**
     * 
     * 描述 获取到需报送到省网办的办件信息数据(不包含工程建设数据)
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:40:06
     * @return
     */    
    public List<Map<String, Object>> findBusBasicInfoList(Connection conn);
    /**
     * 
     * 描述 获取到需报送到省网办的办件信息数据(工程建设数据)
     * @author Yanisin Shi
     * @param conn
     * @return
     */
    public List<Map<String, Object>>  findGcjsBusBasicInfoList(Connection conn);
    /**
     * 
     * 描述   获取数据库配置信息
     * @author Yanisin Shi
     * @param conn
     * @return
     */
    public List<Map<String, Object>> findDataAbutmentList(Connection conn);
    /**
     * 
     * 描述 获取值是改变状态
     * 
     * @author Water Guo
     * @created 2016年8月21日 下午1:40:06
     * @return
     */
    public void updateOperStatusToNine(Connection conn);

    /**
     * 
     * 描述 删除已删除流程的事项记录
     * 
     * @author Water Guo
     * @created 2016年8月21日 下午1:40:06
     * @return
     */
    public void deleteInfoNoInJbpm6Execution(Connection conn);

    /**
     * 
     * 描述 删除已删除流程的事项附件记录
     * 
     * @author Water Guo
     * @created 2016年8月21日 下午1:40:06
     * @return
     */
    public void deleteAttrNoInJbpm6Execution(Connection conn);
    
    /**
     * 
     * 描述 获取到需报送到省网办的办件信息数据
     * 
     * @author Water Guo
     * @created 2016年8月31日 下午1:40:06
     * @return
     */
    public List<Map<String, Object>> findBusBasicInfoList(Connection conn, int i);

    /**
     * 
     * 描述 获取父部门组织机构代码
     * 
     * @author Water Guo
     * @param conn 
     * @created 2016年9月5日 下午1:40:06
     * @return
     */
    public String getParentOrgCode(String parentId, Connection conn);
    /**
     *
     * 描述 获取到需报送到省网办的办件信息数据列表
     *
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:40:06
     * @return
     */
    public List<Map<String, Object>> findBusDataList(Connection conn,String Type);
    /**
     *
     * 描述 获取到需报送到省网办的办件信息数据列表
     *
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:40:06
     * @return
     */
    public List<Map<String, Object>> findBusDataList(Connection conn) ;

    
     /**
     * 描述
     * @author Yanisin Shi
     * @param conn
     * @return
     * create time 2021年9月19日
     */
     
    public List<Map<String, Object>> findGcAndJgDataList(Connection conn,String sn);
   
    /**
     * 
     * 描述 删除已删除流程的事项附件记录（包含归档数据）
     * @author Yanisin Shi
     * @param conn
     */
    void deleteAttrNoInJbpm6ExecutionNew(Connection conn, String sql);
     /**
      * 
      * 描述 删除已删除流程的事项记录(包含归档数据)
      * @author Yanisin Shi
      * @param conn
      * @param sql
      */
    void deleteInfoNoInJbpm6ExecutionNew(Connection conn, String sql);
    /**
     * 
     * 描述 查询10数据是否已经推送
     * @author Yanisin Shi
     * @return
     */
    public int searchTen(String exeid,String resid);
}
