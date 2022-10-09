/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.efile.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.efile.service.ElectronicFileService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述可信电子文件业务相关Controller
 * 
 * @author Luffy Cai
 *
 */
@Controller
@RequestMapping("/electronicFileController")
public class ElectronicFileController extends BaseController {

    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * electronicFileService
     */
    @Resource
    private ElectronicFileService electronicFileService;

    /**
     * 
     * @Description 事项与材料目录获取接口
     * @author Luffy Cai
     * @date 2021年8月11日
     * @param request
     * @return Map<String,Object>
     */
    @RequestMapping("/getEfileProjectCatalog")
    @ResponseBody
    public Map<String, Object> getEfileProjectCatalog(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String itemId = request.getParameter("itemId") == null ? "" : request.getParameter("itemId").toString();
        String projectName = request.getParameter("projectName") == null ? ""
                : request.getParameter("projectName").toString();
        String projectCode = request.getParameter("projectCode") == null ? ""
                : request.getParameter("projectCode").toString();
        if (!StringUtils.isEmpty(itemId) && !StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(projectCode)) {
            result = electronicFileService.getEfileProjectCatalog(itemId, projectName, projectCode);
        } else {
            result.put("success", false);
            result.put("msg", "缺少必要参数！");
        }
        return result;
    }

}
