/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.OtherSysUserService;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 外部系统用户与本系统用户映射绑定业务controller
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月23日 上午9:15:42
 */
@Controller
@RequestMapping("/otherSystemController")
public class OtherSystemController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(OtherSystemController.class);
    /**
     * otherSysUserService
     */
    @Resource
    private OtherSysUserService otherSysUserService;

    /**
     * 
     * 描述 获取当前登录用户信息
     * 
     * @author Flex Hu
     * @created 2014年9月20日 下午5:53:10
     * @param request
     * @param response
     */
    @RequestMapping(params = "buildUser")
    @ResponseBody
    public AjaxJson buildUser(HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> othUser = BeanUtil.getMapFromRequest(request);
        if (othUser.get("OTHER_USERNAME") == null
                || othUser.get("OTHER_PASSWORD") == null) {
            j.setSuccess(false);
            j.setMsg("帐号和密码不能为空！");
            return j;
        }
        SysUser user = AppUtil.getLoginUser();
        othUser.put("USER_ID", user.getUserId());
        othUser.put("USER_NAME", user.getUsername());
        othUser.put("CREATE_TIME",
                DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        othUser.put("LAST_LOGINTIME",
                DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        othUser.put(
                "OTHER_USERNAME",
                StringUtil.getBase64Decode(
                        (String) othUser.get("OTHER_USERNAME"), "UTF-8"));
        if (this.otherSysUserService.isExistsOtherSysUser(user.getUserId(),
                AllConstant.SYS_OTHER_SYSTEM_CODE)) {
            this.otherSysUserService.delOtherSysUser(user.getUserId(),
                    AllConstant.SYS_OTHER_SYSTEM_CODE);
        }
        boolean b = this.otherSysUserService.saveOtherSysUser(othUser);
        j.setSuccess(b);
        return j;
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "ssxtLogin")
    public ModelAndView sysUserView(HttpServletRequest request) {
        String sysCode = request.getParameter("sysCode");
        if (StringUtils.isEmpty(sysCode)
                || sysCode.equals(AllConstant.SYS_OTHER_SYSTEM_CODE)) {
            request.setAttribute("sysCode", AllConstant.SYS_OTHER_SYSTEM_CODE);
        }
        SysUser sysuser = AppUtil.getLoginUser();
        if (this.otherSysUserService.isExistsOtherSysUser(sysuser.getUserId(),
                AllConstant.SYS_OTHER_SYSTEM_CODE)) {
            request.setAttribute("hasOtherUser", "1");
            Map<String, Object> otherUser = otherSysUserService
                    .getOtherSysUser(sysuser.getUserId(),
                            AllConstant.SYS_OTHER_SYSTEM_CODE);
            otherUser.put(
                    "OTHER_PASSWORD",
                    StringUtil.getBase64Decode(
                            (String) otherUser.get("OTHER_PASSWORD"), "UTF-8"));
            request.setAttribute("otherUser", otherUser);
            this.otherSysUserService.updateLastLoginTime(sysuser.getUserId(),
                    AllConstant.SYS_OTHER_SYSTEM_CODE);
            log.info("用户【" + sysuser.getUsername() + "】登录商事系统！");
        } else {
            request.setAttribute("hasOtherUser", "0");
        }
        return new ModelAndView("system/sysuser/SSXTUserLogin");
    }

}
