/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import net.evecom.core.util.AjaxJson;
import net.evecom.platform.bdc.service.BdcLsylwtcxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:不动产历史遗留问题
 *
 * @author Madison You
 * @created 15:13
 */
@Controller
@RequestMapping("/bdcLsylwtcxController")
public class BdcLsylwtcxController {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/4/13 16:04:00
     * @param 
     * @return 
     */
    @Autowired
    private BdcLsylwtcxService lsylwtcxService;

//    /**
//     * 描述:是否在资源局之后审批
//     *
//     * @author Madison You
//     * @created 2021/4/8 15:15:00
//     * @param
//     * @return
//     */
//    @RequestMapping(params = "isAfterZyjApprove")
//    @ResponseBody
//    public Object isAfterZfjApprove(HttpServletRequest request) {
//        AjaxJson ajaxJson = new AjaxJson();
//        String exeId = request.getParameter("exeId");
//        Boolean flag = lsylwtcxService.isAfterZfjApprove(exeId);
//        ajaxJson.setSuccess(flag);
//        return ajaxJson;
//    }

}
