/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;

/**
 * 描述 城乡居民社会养老保险业务通用操作service
 * @author Allin Lin
 * @created 2020年2月18日 下午2:39:39
 */
public interface SbCommonService extends BaseService{
    /**
     *判断人员是否进行参保
     * @param idNo  身份证号
     * @param cbxzDicCode  参保险种：110-养老；410-工伤
     * @param isAlreadyNew 是否校验人员存在新参保申报业务，默认传false
     * @return
     */
    public AjaxJson isValidForNew(String idNo, String cbxzDicCode, String isAlreadyNew) ;
    /**
     *判断人员是否是最后一个人
     * @param dwNum
     * @param idNo  身份证号
     * @param cbxzDicCode  参保险种：110-养老；410-工伤
     * @return
     */
    public boolean isLastMan(String dwNum,String idNo,String cbxzDicCode);

}
