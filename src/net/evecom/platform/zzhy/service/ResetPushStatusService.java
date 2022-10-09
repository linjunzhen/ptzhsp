/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import net.evecom.core.service.BaseService;

/**
 * 描述 重置推送状态(商事专网)
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ResetPushStatusService extends BaseService {
   /**
    * 重置推送状态
    */
   public void resetPushStatus();
}
