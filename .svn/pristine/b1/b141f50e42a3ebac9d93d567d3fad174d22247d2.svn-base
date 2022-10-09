/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-3-3 上午9:09:36
 */
public interface ItemBespeakService extends BaseService {

    /**
     * 
     * 描述   是否可网上预约
     * @author Danto Huang
     * @created 2017-3-3 上午10:44:43
     * @param itemCode
     * @return
     */
    public List<Map<String, Object>> isOnlineBespeak(String itemCode);
    /**
     * 
     * 描述   获取用户部门预约信息
     * @author Danto Huang
     * @created 2017-3-21 下午4:34:28
     * @param departId
     * @param idCard
     * @return
     */
    public List<Map<String, Object>> getUserBespeakListByDepartId(String departId,String idCard);
}
