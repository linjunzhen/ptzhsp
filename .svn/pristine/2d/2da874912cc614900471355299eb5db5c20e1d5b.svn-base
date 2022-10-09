/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcExecutionService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.yb.service.YbExecutionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 描述： 医保办件
 *
 * @author Madison You
 * @created 2021年2月2日 上午10:14:56
 */
@Controller
@RequestMapping("/ybExecutionController")
public class YbExecutionController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbExecutionController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/2 10:24:00
     * @param
     * @return
     */
    @Resource
    private YbExecutionService ybExecutionService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/2 10:29:00
     * @param
     * @return
     */
    @Resource
    private BdcExecutionService bdcExecutionService;

    /**
     * 跳转到医保待预审界面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goYbDwys")
    public ModelAndView goZzhyDwys(HttpServletRequest request) {
        return new ModelAndView("hflow/yb/ybDwysTask");
    }

    /**
     * 跳转到医保待受理界面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goYbNeedMeHandle")
    public ModelAndView goZzhyNeedMeHandle(HttpServletRequest request) {
        return new ModelAndView("hflow/yb/ybNeedmeHandlerTask");
    }

    /**
     * 跳转到医保待审批界面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goYbDwsp")
    public ModelAndView goZzhyDwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/yb/ybDwspTask");
    }

    /**
     * 跳转到医保已审批界面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goYbJwsp")
    public ModelAndView goZzhyJwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/yb/ybJwspTask");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "ybNeedMeHandle")
    public void ybNeedMeHandle(HttpServletRequest request, HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if (StringUtils.isNotEmpty(isHaveHandup)) {
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "ASC");
        List<Map<String, Object>> list = ybExecutionService.findYbNeedMeHandle(filter, haveHandUp);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 17:57:00
     * @param
     * @return
     */
    @RequestMapping(params = "ybHandledByMe")
    public void ybHandledByMe(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("userAccount",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        String userAccount = sysUser.getUsername();
        List<Map<String, Object>> list = ybExecutionService.findYbHandledByUser(filter, userAccount);
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> dic = this.ybExecutionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("DEF_KEY"), "ZFJointDefKey" });
            if (dic != null) {
                map.put("DIC_STATE", 1);
            }
            newlist.add(map);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), newlist, null, JsonUtil.EXCLUDE, response);
    }
}
