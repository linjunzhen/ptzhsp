/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 老年人优待证业务管理Dao 
 * @author Bryce Zhang
 * @created 2017-5-15 上午9:05:54
 */
public interface OldAgeCardDao extends BaseDao{
    
    /**
     * 描述 根据sqlfilter，获取某用户办理的流程
     * @author Bryce Zhang
     * @created 2017-5-16 上午9:53:35
     * @param filter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findHandledByUser(SqlFilter filter, String userAccount);
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth(SqlFilter filter, SysUser loginUser);
    
    /**
     * 描述 注销优待证号非cardNum的申请人证件
     * @author Bryce Zhang
     * @created 2017-5-26 下午12:42:48
     * @param idNum
     * @param cardNum
     */
    public void updateDeRegister(String idNum, String cardNum);
    
    /**
     * 描述 得到绿证下一编号
     * @author Bryce Zhang
     * @created 2017-5-26 下午12:56:27
     * @return
     */
    public String getGreenCardNextNum();
    
    /**
     * 描述 得到红证下一编号
     * @author Bryce Zhang
     * @created 2017-5-26 下午12:56:27
     * @return
     */
    public String getRedCardNextNum();
    
    /**
     * 描述 更新证件为注销
     * @author Bryce Zhang
     * @created 2017-5-27 下午5:19:51
     * @param busId
     * @param lostStateId
     * @param lostStateName
     * @param lostStatePath
     */
    public void updateUnregister(String busId, String lostStateId, String lostStateName, String lostStatePath);
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth4Exp(SqlFilter filter, SysUser loginUser);

}
