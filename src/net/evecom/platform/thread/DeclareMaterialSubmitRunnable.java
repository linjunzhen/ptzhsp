/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.thread;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.elicense.service.ElectronicLicenseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 电子证照-申报登记信息材料提交 队列线程
 * @author Michael Lin
 * @Created 2019/10/8.
 */
public class DeclareMaterialSubmitRunnable implements Runnable{

    /**
     * 电子证照业务相关service
     */
    @Autowired
    private ElectronicLicenseService electronicLicenseService;

    /**
     * 数据对象
     */
    private Map<String, Object> itemData;

    /**
     * 初始化实例对象
     * @param itemData 数据对象
     */
    public DeclareMaterialSubmitRunnable(Map<String, Object> itemData) {
        this.itemData = itemData;
        this.electronicLicenseService = (ElectronicLicenseService) AppUtil.getBean("electronicLicenseService");
    }

    /**
     * 执行
     */
    @Override
    public void run() {
        electronicLicenseService.declareMaterialSubmit(itemData);
    }
}
