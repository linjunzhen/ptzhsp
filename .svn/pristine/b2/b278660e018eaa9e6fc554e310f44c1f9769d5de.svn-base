/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcYydjService;

/**
 * 描述   异议登记业务controller
 * @author Allin Lin
 * @created 2020年4月29日 下午2:19:06
 */
@Controller
@RequestMapping("/bdcYydjController")
public class BdcYydjController extends BaseController {
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcYydjController.class);
    
    /**
     * 引入service
     */
    @Resource
    private BdcYydjService bdcYydjService;
    
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;

}
