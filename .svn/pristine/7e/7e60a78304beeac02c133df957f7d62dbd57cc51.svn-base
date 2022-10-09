/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import net.evecom.platform.system.job.CreditDataInputSynchoJob;
import net.evecom.platform.system.service.CreditDataInputService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 信用数据推送转发接口，由于定时器设置在公用网络区服务器上，所以由公用网络区审批应用服务器1转发到互联网区行政服务中心网站1.
 *      再发送数据到信用数据平台
 *
 * @author Madison You
 * @version 1.0
 * @created 2019年11月11日 上午16:07:56
 */
@Controller
@RequestMapping("/creditDataInputController")
public class CreditDataInputController {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(CreditDataInputController.class);

    /**
     * 引入 CreditDataInputService
     */
    @Resource
    private CreditDataInputService creditDataInputService;

    /**
     * 描述:市场主体信用数据请求转发
     *
     * @author Madison You
     * @created 2019/11/11 16:13:00
     * @param
     * @return
     */
    @RequestMapping("/creditDataInputSend")
    public void creditDataInputSend(HttpServletRequest request , HttpServletResponse response) {
        creditDataInputService.creditDataInput(log);
    }

}
