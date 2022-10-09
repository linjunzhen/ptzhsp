/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 窗口签到服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年07月05日 上午9:39:36
 */
@SuppressWarnings("rawtypes")
public interface WinSignService extends BaseService {
    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年07月05日 上午9:39:36
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter, String codes, String names, String userCode,
            String vIp);

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年7月7日 上午10:42:26
     * @param userId
     * @param string
     * @param ipAddr
     */
    public Map<String, Object> doSign(SysUser sysUser, String userWinAddr, String ipAddr);

    /**
     * 描述   判断是否是需要签到的IP
     * @author Derek Zhang
     * @created 2016年7月9日 上午11:32:49
     * @param userWinIp
     * @param clientIp
     * @return
     */
    public boolean isNeedSign(String userWinIp, String clientIp);

    /**
     * 描述  判断是否是当日首次签到
     * @author Derek Zhang
     * @created 2016年7月9日 下午1:27:54
     * @param userWinIp
     * @param ipAddr
     * @return
     */
    public List<Map<String, Object>> isFirstSignByCurDate(String userWinIp, String ipAddr);

}
