/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;

import java.util.Map;

/**
 * 描述 企业职工基本养老保险增、减员service
 * @author Allin Lin
 * @created 2020年2月18日 下午2:39:39
 */
public interface SbQyzgzjyService extends BaseService{
   
    /**
     * 先接口校验，再职工参保推送
     * @param ywId
     * @return
     */
    public AjaxJson pushZgCb(String ywId);
    
    /**
     * 职工减员接口推送
     * @param ywId
     * @return
     */
    public AjaxJson pushZgJy(String ywId);
}
