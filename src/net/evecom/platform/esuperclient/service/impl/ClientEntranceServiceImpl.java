/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuperclient.service.impl;

import net.evecom.platform.esuperclient.model.SupervisionBusFileRs;
import net.evecom.platform.esuperclient.model.SupervisionBusInfoRs;
import net.evecom.platform.esuperclient.model.SupervisionHeader;
import net.evecom.platform.esuperclient.model.SupervisionWarnInfo;
import net.evecom.platform.esuperclient.service.ClientEntranceServiceIF;

/**
 * 描述
 * 
 * @author Derek Zhang
 * @created 2016年2月19日 下午4:41:42
 */
public class ClientEntranceServiceImpl implements ClientEntranceServiceIF {

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年2月19日 下午4:41:42
     * @param supervisionHeader
     * @param supervisionWarnInfo
     * @return
     */
    @Override
    public String clientWarnEntrance(SupervisionHeader supervisionHeader, SupervisionWarnInfo supervisionWarnInfo) {
        // 具体实现
        String warnReason = supervisionWarnInfo.getWarnReason();
        return warnReason;
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年2月19日 下午4:41:42
     * @param supervisionHeader
     * @param supervisionBusFileRs
     * @return
     */
    @Override
    public String clientFileEntrance(SupervisionHeader supervisionHeader, SupervisionBusFileRs supervisionBusFileRs) {
        // 具体实现
        String warnReason = supervisionBusFileRs.getMsgTransactionID();
        return warnReason;
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年2月19日 下午4:41:42
     * @param supervisionHeader
     * @param supervisionBusInfoRs
     * @return
     */
    @Override
    public String clientInfoEntrance(SupervisionHeader supervisionHeader, SupervisionBusInfoRs supervisionBusInfoRs) {
        // 具体实现待完善
        return supervisionBusInfoRs.getMsgTransactionID();
    }

}
