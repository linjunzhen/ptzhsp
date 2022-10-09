/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.system.service.UserGroupService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  用户组Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/userGroupController")
public class UserGroupController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(UserGroupController.class);
    /**
     * 引入Service
     */
    @Resource
    private UserGroupService userGroupService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        userGroupService.removeCascade(selectColNames);
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 用户组记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  userGroup = userGroupService.getByJdbc("T_SYSTEM_USERGROUP",
                    new String[]{"GROUP_ID"},new Object[]{entityId});
            request.setAttribute("userGroup", userGroup);
        }
        return new ModelAndView("system/usergroup/UserGroupInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("GROUP_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = userGroupService.saveOrUpdate(variables, "T_SYSTEM_USERGROUP", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 用户组记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 用户组记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "UserGroupView")
    public ModelAndView userGroupView(HttpServletRequest request) {
        return new ModelAndView("system/usergroup/UserGroupView");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = userGroupService.findBySqlFilter(filter);
        for(Map<String,Object> group:list){
            StringBuffer userIds = new StringBuffer("");
            StringBuffer userNames = new StringBuffer("");
            String groupId = (String) group.get("GROUP_ID");
            List<Map<String,Object>> users = sysUserService.findUsersByGroupId(groupId);
            for(int i = 0;i<users.size();i++){
                Map<String,Object> user = users.get(i);
                if(i>0){
                    userIds.append(",");
                    userNames.append(",");
                }
                userIds.append(user.get("USER_ID"));
                userNames.append(user.get("FULLNAME"));
            }
            group.put("USER_IDS",userIds.toString());
            group.put("USER_NAMES",userNames.toString());
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 分配用户
     * @param request
     * @return
     */
    @RequestMapping(params = "grantUsers")
    @ResponseBody
    public AjaxJson grantUsers(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String groupId = request.getParameter("groupId");
        String userIds = request.getParameter("userIds");
        userGroupService.saveGroupUsers(userIds.split(","), groupId);
        j.setMsg("分配用户成功");
        return j;
    }
}

