/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.Des;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.BdcApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月20日 下午5:46:40
 */
@Controller
@RequestMapping("/sysUserController")
public class SysUserController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SysUserController.class);
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
     * sysRoleService
     */
    @Resource
    private SysRoleService sysRoleService;
    /**
     * departmentService
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:33:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:34:00
     * @param
     * @return
     */
    @Resource
    private DicTypeService dicTypeService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/10/28 9:21:00
     * @param
     * @return
     */
    @Resource
    private BdcApplyService bdcApplyService;
    
    /**
     * 删除用户信息
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        this.sysUserService.removeCascade(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 用户信息记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述 获取当前登录用户信息
     * @author Flex Hu
     * @created 2014年9月20日 下午5:53:10
     * @param request
     * @param response
     */
    @RequestMapping(params = "getLoginUser")
    @ResponseBody
    public void getLoginUser(HttpServletRequest request,HttpServletResponse response) {
        String json = AppUtil.getLoginUserJson();
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "SysUserView")
    public ModelAndView sysUserView(HttpServletRequest request) {
        return new ModelAndView("system/sysuser/SysUserView");
    }
    
    /**
     * 显示用户基本信息
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String departId = request.getParameter("departId");
        Map<String,Object> sysUser = null;
        Map<String,Object> depart= null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            sysUser = sysUserService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[]{"USER_ID"},new Object[]{entityId});
            List<Map<String,Object>> roleList = sysRoleService.findByUserId(entityId);
            StringBuffer roleIds = new StringBuffer("");
            StringBuffer roleNames = new StringBuffer("");
            for(int i =0;i<roleList.size();i++){
                Map<String,Object> role = roleList.get(i);
                if(i>0){
                    roleIds.append(",");
                    roleNames.append(",");
                }
                roleIds.append(role.get("ROLE_ID"));
                roleNames.append(role.get("ROLE_NAME"));
            }
            departId = (String) sysUser.get("DEPART_ID");
            depart = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[]{"DEPART_ID"},new Object[]{departId});
            sysUser.put("ROLE_IDS", roleIds.toString());
            sysUser.put("ROLE_NAMES", roleNames.toString());
            sysUser.put("DEPART_ID", depart.get("DEPART_ID"));
            sysUser.put("DEPART_NAME", depart.get("DEPART_NAME"));
            /*签名文件回填*/
            String signFileId = (String) sysUser.get("SIGN_FILE_ID");
            if (StringUtils.isNotEmpty(signFileId)) {
                Map<String,Object> fileAttach = departmentService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"}, new Object[]{signFileId});
                if (fileAttach != null) {
                    sysUser.put("signFileName", fileAttach.get("FILE_NAME"));
                }
            }
        }else{
            sysUser = new HashMap<String,Object>();
            if(StringUtils.isNotEmpty(departId)){
                depart = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[]{"DEPART_ID"},new Object[]{departId});
                sysUser.put("DEPART_ID", depart.get("DEPART_ID"));
                sysUser.put("DEPART_NAME", depart.get("DEPART_NAME"));
            }
        }
        request.setAttribute("sysUser", sysUser);
        return new ModelAndView("system/sysuser/SysUserInfo");
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
        filter.addSorted("T.USER_ID","desc");
        List<Map<String, Object>> list = sysUserService.findBySqlFilter(filter);
        for(Map<String,Object> user:list){
            String userId = (String) user.get("USER_ID");
            StringBuffer roleNames = new StringBuffer("");
            List<Map<String,Object>> roleList = sysRoleService.findByUserId(userId);
            for(int i =0;i<roleList.size();i++){
                Map<String,Object> role = roleList.get(i);
                if(i>0){
                    roleNames.append(",");
                }
                roleNames.append(role.get("ROLE_NAME"));
            }
            user.put("ROLE_NAMES", roleNames.toString());
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 增加或者修改用户信息
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("USER_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        int passNum = StringUtil.getRandomIntNumber(9000, 1000);
        if(StringUtils.isEmpty(entityId)){
            String password = StringUtil.encryptSha256(SysUser.DEFAULT_PASSWORD+passNum);
            variables.put("PASSWORD", password);
            variables.put("STATUS", SysUser.STATUS_ACTIVE);
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = sysUserService.saveOrUpdateUser(variables, entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的用户信息记录",SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("保存成功");
        }else{
            j.setMsg("新增用户成功,初始密码为"+SysUser.DEFAULT_PASSWORD+passNum);
            sysLogService.saveLog("新增了ID为["+recordId+"]的用户信息记录",SysLogService.OPERATE_TYPE_ADD);
        }
        
        return j;
    }
    
    /**
     * easyui AJAX请求数据 列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "updateAcceptMsg")
    @ResponseBody
    public AjaxJson updateAcceptMsg(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userId = request.getParameter("userId");
        String isAcceptMsg = request.getParameter("isAcceptMsg");
        Map<String, Object> variables =new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(userId)){
            variables.put("IS_ACCEPTMSG", isAcceptMsg);
            sysUserService.saveOrUpdate(variables, "T_MSJW_SYSTEM_SYSUSER", userId);
            j.setMsg("更新成功");
        }else{
            j.setSuccess(false);
            j.setMsg("修改失败!");
        }
        return j;
    }
    /**
     * easyui AJAX请求数据 列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "updateCardAndMobile")
    @ResponseBody
    public AjaxJson updateCardAndMobile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userId = request.getParameter("userId");
        String MOBILE = request.getParameter("MOBILE");
        String USERCARD = request.getParameter("USERCARD");
        Map<String, Object> variables =new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(userId)){
            variables.put("MOBILE", MOBILE);
            variables.put("USERCARD", USERCARD);
            variables.put("IS_UNIQUE_BIND", "1");
            sysUserService.saveOrUpdate(variables, "T_MSJW_SYSTEM_SYSUSER", userId);
            SysUser sysUser = AppUtil.getLoginUser();
            sysUser.setIsUniqueBind("1");
            AppUtil.addUserToSession(request.getSession(), sysUser);
            j.setMsg("更新成功");
        }else{
            j.setSuccess(false);
            j.setMsg("修改失败!");
        }
        return j;
    }
    
    /**
     * easyui AJAX请求数据 列表
     * @param request
     * @param response
     */
    @RequestMapping(params = "updateMobile")
    @ResponseBody
    public AjaxJson updateMobile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userId = request.getParameter("userId");
        String mobile = request.getParameter("mobile");
        Map<String, Object> variables =new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(userId)){
            variables.put("mobile", mobile);
            sysUserService.saveOrUpdate(variables, "T_MSJW_SYSTEM_SYSUSER", userId);
            j.setMsg("更新成功");
        }else{
            j.setSuccess(false);
            j.setMsg("修改失败!");
        }
        return j;
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "updateStatus")
    @ResponseBody
    public AjaxJson updateStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String status = request.getParameter("status");
        this.sysUserService.updateUserStatus(selectColNames, Integer.parseInt(status));
        j.setMsg("更新状态成功");
        return j;
    }   
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "updateAutoStatus")
    @ResponseBody
    public AjaxJson updateAutoStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] userIds = selectColNames.split(",");
        for (String userId : userIds) {
            Map<String,Object> user = sysUserService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[]{"USER_ID"},new Object[]{userId});
            String status = request.getParameter("status");
            if(StringUtils.isNotEmpty(status) && "1".equals(status)){
                user.put("STATUS", status);
                user.put("IS_DISABLE", 1);
                user.put("ACTIVATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }else{
                user.put("STATUS", status);
                user.put("IS_DISABLE", 0);
                user.put("ACTIVATE_TIME","");
            }
            sysUserService.saveOrUpdate(user,"T_MSJW_SYSTEM_SYSUSER",userId);
        }
        j.setMsg("用户激活成功，将在5天之后自动禁用");
        return j;
    }
    
    /**
     * 重置密码操作
     * @param request
     * @return
     */
    @RequestMapping(params = "resetPass")
    @ResponseBody
    public AjaxJson resetPass(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        int passNum = StringUtil.getRandomIntNumber(9000, 1000);
        this.sysUserService.updatePassword(selectColNames,SysUser.DEFAULT_PASSWORD+passNum);
        j.setMsg("成功将所选用户密码重置为:"+SysUser.DEFAULT_PASSWORD+passNum);
        return j;
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "updatePass")
    @ResponseBody
    public AjaxJson updatePass(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userId = request.getParameter("USER_ID");
        String oldPassword= StringUtil.encryptSha256(Des.strDec(request.getParameter("OLD_PASSWORD"), "1", "2", "3"));
        String password= Des.strDec(request.getParameter("PASSWORD"), "1", "2", "3");
        String mobile= Des.strDec(request.getParameter("MOBILE"), "1", "2", "3");
        String USERCARD= Des.strDec(request.getParameter("USERCARD"), "1", "2", "3");
        Map<String,Object> user = sysUserService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                new String[]{"USER_ID"},new Object[]{userId});
        String userOldPassword = (String) user.get("PASSWORD");
        if(!userOldPassword.equals(oldPassword)){
            j.setSuccess(false);
            j.setMsg("原始密码输入错误!修改失败!");
        }else{
            String newPassword = StringUtil.encryptSha256(password);
            user.put("PASSWORD", newPassword);
            user.put("MOBILE", mobile);
            user.put("USERCARD", USERCARD);
            user.put("IS_MODIFYPASS","1");
            user.put("IS_UNIQUE_BIND","1");
            sysUserService.saveOrUpdate(user,"T_MSJW_SYSTEM_SYSUSER",userId);
            SysUser sysUser = AppUtil.getLoginUser();
            sysUser.setIsModifyPass("1");
            sysUser.setIsUniqueBind("1");
            AppUtil.addUserToSession(request.getSession(), sysUser);
            j.setMsg("成功修改密码");
        }
        return j;
    }
    
    /**
     * 
     * 加载人员数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String,Object>> list = sysUserService.findUsers(filter);
        for(Map<String,Object> map:list){
            map.put("PINYIN", StringUtil.getFirstLetter((String)map.get("FULLNAME")));
        }
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("USER_ID","");
            map.put("FULLNAME", "请选择用户");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String userIds = request.getParameter("userIds");
        String userNames = request.getParameter("userNames");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if(StringUtils.isNotEmpty(userIds)&&!userIds.equals("undefined")){
            request.setAttribute("userIds", userIds);
            request.setAttribute("userNames", userNames);
        }
        return new ModelAndView("system/sysuser/SysUserSelector");
    }
    
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "showPass")
    public ModelAndView showPass(HttpServletRequest request) {
        String isForce = request.getParameter("isForce");
        if(StringUtils.isNotEmpty(isForce)){
            request.setAttribute("isForce", isForce);
        }
        return new ModelAndView("system/sysuser/PasswordInfo");
    }
    
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "showCardAndMobile")
    public ModelAndView showCardAndMobile(HttpServletRequest request) {
        String isForce = request.getParameter("isForce");
        if(StringUtils.isNotEmpty(isForce)){
            request.setAttribute("isForce", isForce);
        }
        return new ModelAndView("system/sysuser/cardAndMobile");
    }
    
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "showMobile")
    public ModelAndView showMobile(HttpServletRequest request) {
        String isForce = request.getParameter("isForce");
        if(StringUtils.isNotEmpty(isForce)){
            request.setAttribute("isForce", isForce);
        }
        return new ModelAndView("system/sysuser/mobileInfo");
    }
    
    /**
     * 
     * 加载人员数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "userIdAndName")
    public void userIdAndName(HttpServletRequest request,
            HttpServletResponse response) {
        String depId = request.getParameter("depId");
        Map<String,Object> idAndName = this.sysUserService.getUserIdAndNames(depId);
        String json = JSON.toJSONString(idAndName);
        this.setJsonString(json, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String userIds = request.getParameter("userIds");
        String userAccounts = request.getParameter("userAccounts");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(userIds)){
            list = sysUserService.findByUserId(userIds);
        }else if(StringUtils.isNotEmpty(userAccounts)){
            list = sysUserService.findByUserAccounts(userAccounts);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * 
     * 描述    用户头像设置
     * @author Danto Huang
     * @created 2019年1月7日 上午9:40:20
     * @param request
     * @return
     */
    @RequestMapping(params="showHeadPortrait")
    public ModelAndView showHeadPortrait(HttpServletRequest request){
        String userId = AppUtil.getLoginUser().getUserId();
        Map<String, Object> userInfo = sysUserService.getByJdbc("T_MSJW_SYSTEM_SYSUSER", new String[] { "USER_ID" },
                new Object[] { userId });
        if(userInfo.get("PHOTO_PATH")!=null){
            request.setAttribute("PHOTO_PATH", userInfo.get("PHOTO_PATH"));
            request.setAttribute("PHOTO_ID", userInfo.get("PHOTO_ID"));
        }
        return new ModelAndView("system/sysuser/headPortraitInfo");
    }
    
    /**
     * 
     * 描述    保存用户头像
     * @author Danto Huang
     * @created 2019年1月7日 上午10:28:05
     * @param request
     */
    @RequestMapping(params="updateHeadPortrait")
    @ResponseBody
    public AjaxJson updateHeadPortrait(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("USER_ID");
        Map<String, Object> user = BeanUtil.getMapFromRequest(request);
        sysUserService.saveOrUpdate(user, "T_MSJW_SYSTEM_SYSUSER", entityId);
        String userName = sysUserService
                .getByJdbc("T_MSJW_SYSTEM_SYSUSER", new String[] { "USER_ID" }, new Object[] { entityId })
                .get("USERNAME").toString();
        HttpSession session = AppUtil.getSession();
        SysUser sysUser = sysUserService.getAllInfoByUsername(userName);
        AppUtil.addUserToSession(session, sysUser);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    具备照片人员
     * @author Danto Huang
     * @created 2019年1月21日 下午3:23:04
     * @param request
     * @return
     */
    @RequestMapping(params="allPhotoUser")
    @ResponseBody
    public AjaxJson allPhotoUser(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        List<Map<String,Object>> list = sysUserService.findPhotoUsers();
        if(list!=null&&list.size()>0){
            j.setMsg("loginController.do?checkFacelogin");
        }else{
            list = new ArrayList<Map<String,Object>>();
        }
        j.setJsonString(JSON.toJSONString(list));
        return j;
    }

    /**
     * 描述:新增用户分组
     *
     * @author Madison You
     * @created 2020/10/28 9:12:00
     * @param
     * @return
     */
    @RequestMapping(params = "addUserGroupView")
    public ModelAndView addUserGroupView(HttpServletRequest request) {
        String typeCode = request.getParameter("typeCode");
        Map<String,Object> dicType =  dicTypeService.getByTypeCode(typeCode);
        if(dicType != null){
            Map<String,Object> dictionary = new HashMap<String, Object>();
            dictionary.put("TYPE_ID", dicType.get("TYPE_ID"));
            dictionary.put("TYPE_CODE", dicType.get("TYPE_CODE"));
            dictionary.put("TYPE_NAME", dicType.get("TYPE_NAME"));
            request.setAttribute("dictionary", dictionary);
        }
        return new ModelAndView("system/sysuser/addUserGroupView");
    }

    /**
     * 描述:保存用户分组
     *
     * @author Madison You
     * @created 2020/10/28 9:19:00
     * @param
     * @return
     */
    @RequestMapping(params = "saveUserGroup")
    @ResponseBody
    public AjaxJson saveUserGroup(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DIC_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            boolean isExist1 = dictionaryService.isExist(request.getParameter("TYPE_ID"),
                    request.getParameter("DIC_CODE"));
            if(isExist1){
                j.setSuccess(false);
                j.setMsg("该编码已存在,创建失败!");
                return j;
            }
            boolean isExist2 = bdcApplyService.isExist(request.getParameter("TYPE_ID"),
                    request.getParameter("DIC_NAME"));
            if(isExist2){
                j.setSuccess(false);
                j.setMsg("该名称已存在,创建失败!");
                return j;
            }
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String typeId = (String) variables.get("TYPE_ID");
            int maxSn = dictionaryService.getMaxSn(typeId);
            variables.put("DIC_SN", maxSn+1);
            if(variables.get("DIC_CODE")==null){
                variables.put("DIC_CODE", maxSn+1);
            }
            //获取字典类别
            Map<String,Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_ID"},
                    new Object[]{variables.get("TYPE_ID")});
            variables.put("TYPE_CODE",dicType.get("TYPE_CODE"));
            dictionaryService.saveOrUpdate(variables, "T_MSJW_SYSTEM_DICTIONARY", entityId);
            j.setMsg("保存成功");
        }
        return j;
    }

}
