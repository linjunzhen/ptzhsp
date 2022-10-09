/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import net.evecom.core.service.BaseService;
import net.evecom.platform.zzhy.model.data.SubmitDataTable;

/**
 * 描述 提交业务任务接口service
 * @author Water Guo
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SubmitTaskService extends BaseService {
   /**
    * 推送提交的数据
    */
   public void pushDataOfSubmit(SubmitDataTable submitDataTable);
}
