/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 描述： 审批表打印Controller
 *
 * @author Madison You
 * @created 2020年5月29日 上午12:14:56
 */
@Controller
@RequestMapping("/bdcSpbPrintConfigController")
public class BdcSpbPrintConfigController extends BaseController {


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 9:23:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcSpbPrintConfigController.class);


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/29 15:22:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbPrintConfigService;

    /**
     * 描述:获取打印模板名称
     *
     * @author Madison You
     * @created 2020/5/30 10:26:00
     * @param
     * @return
     */
    @RequestMapping("/getTemplateName")
    @ResponseBody
    public Map<String,Object> getTemplateName(HttpServletRequest request , HttpServletResponse response) {
        String itemCode = request.getParameter("itemCode");
        String tableName = request.getParameter("tableName");
        Map<String, Object> returnMap = new HashMap<>();
        try {
            Map<String, Object> templateMap = bdcSpbPrintConfigService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[]{"DIC_CODE", "DIC_DESC"}, new Object[]{itemCode, tableName});
            if (templateMap != null) {
                String templateName = (String) templateMap.get("TYPE_CODE");
                returnMap.put("templateName", templateName);
                returnMap.put("success", true);
            } else {
                returnMap.put("success", false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnMap.put("success", false);
        }
        return returnMap;
    }

}
