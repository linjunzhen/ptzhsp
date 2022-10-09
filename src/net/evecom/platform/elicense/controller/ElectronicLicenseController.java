/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.elicense.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.elicense.service.ElectronicLicenseService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 电子证照业务相关controller
 * 
 * @author Roy Li
 *
 */
@Controller
@RequestMapping("/electronicLicenseController")
public class ElectronicLicenseController extends BaseController {

    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * electronicLicenseService
     */
    @Resource
    private ElectronicLicenseService electronicLicenseService;

}
