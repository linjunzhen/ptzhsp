/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 描述 不动产全流程涉税登记
 * @author Madison You
 * @created 2020年6月518日 19:32:19
 */
public interface BdcSsdjService extends BaseService {

    /**
     * 描述:获取涉税登记最新受理号
     *
     * @author Madison You
     * @created 2020/6/27 14:26:00
     * @param
     * @return
     */
    public String getSsdjSlh(String exeId);

    /**
     * 描述:获取最新涉税登记返回信息
     *
     * @author Madison You
     * @created 2020/7/1 11:31:00
     * @param
     * @return
     */
    public Map<String,Object> getLateSsdjInfo(String exeId);

    /**
     * 描述:交易ID取最新涉税登记返回信息
     *
     * @author Madison You
     * @created 2020/7/14 17:09:00
     * @param
     * @return
     */
    public Map<String, Object> getLateSsdjInfoBySjbbh(String sjbbh);

    /**
     * 描述:根据申报号查询事项子部门
     *
     * @author Madison You
     * @created 2020/12/23 9:20:00
     * @param
     * @return
     */
    String getItemChildDeptByExeId(String exeId);

    /**
     * 描述:根据事项编码查询事项子部门
     *
     * @author Madison You
     * @created 2020/12/23 9:42:00
     * @param
     * @return
     */
    String getItemChildDeptByItemCode(String itemCode);
}
