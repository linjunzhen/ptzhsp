/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述：提示修改密码DataPWDChanceService
 * @author Water Guo
 * @created 2017-12-11 下午2:20:07
 */
@SuppressWarnings("rawtypes")
public interface DataPWDChanceService extends BaseService {

    /**
     * 描述 办件警告短信
     * 
     * @author Water Guo
     * @created 2017年03月02日 下午1:28:08
     * @param dataAbutment
     */
    public void dataPWDChanceMsg();
}
