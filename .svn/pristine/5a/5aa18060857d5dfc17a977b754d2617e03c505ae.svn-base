/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.*;
import net.evecom.platform.system.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.hd.service.CommentService;
import net.evecom.platform.hd.service.ComplainService;
import net.evecom.platform.hd.service.ConsultService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.weixin.service.UserBindService;
import net.evecom.platform.weixin.service.WHomeService;
import net.evecom.platform.weixin.util.WeixinUtil;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BspjService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 业务交互
 * @author Sundy Sun
 * @version v1.0
 */
@Controller
@RequestMapping("busInteractController")
public class BusInteractController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(RegisterController.class);
    /**
     * 引入Service
     */
    @Resource
    private UserInfoService userInfoService;
    /**
     * 引入service
     */
    @Resource
    private ConsultService consultService;
    /**
     * 引入Service
     */
    @Resource
    private UserBindService userBindService;
    /**
     * 引入Service
     */
    @Resource
    private WHomeService whomeService;
    /**
     * 引入Service
     */
    @Resource
    private ComplainService complainService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private BspjService bspjService;
    /**
     * 引入Service
     */
    @Resource
    private CommentService commentService;
    /**
     * 引入Service
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     *
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 引入Service
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;


    /**
     *
     * 跳转到咨询页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toConsult")
    public ModelAndView toConsult(HttpServletRequest request) {
        String openId=checkOpenId(request);
        Map<String, Object> userInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});
        if(userInfo!=null){
            Map<String, Object> user= userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{userInfo.get("USER_NAME")});
          //获取用户状态
            String state=(String) user.get("YHZT");
            if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                return new ModelAndView("weixin/noUse");
            }else{
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, user);
                return new ModelAndView("weixin/interact/busiZX");
            }
        }else{
            return new ModelAndView("weixin/nobind");
        }
    }

    public String checkOpenId(HttpServletRequest request){
        String openId=(String) AppUtil.getOpenID();
        String code = request.getParameter("code");//我们要的code
        if(StringUtils.isNotEmpty(openId)){
            return openId;
        }else{
            openId=request.getParameter("openID");
            if(StringUtils.isNotEmpty(openId)){
                HttpSession session = AppUtil.getSession();
                AppUtil.addOpenIdToSession(session, openId);
            }else{
                openId=WeixinUtil.getOpenByCode(code);
                HttpSession session = AppUtil.getSession();
                AppUtil.addOpenIdToSession(session, openId);
            }
            return openId;
        }
    }

    /**
     *
     * 描述 前台业务咨询
     *
     * @author Rider Chen
     * @created 2015-11-20 下午04:32:26
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "zxSubmit")
    public void zxSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId=(String) AppUtil.getOpenID();
        Map<String, Object> bindInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});

        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("CONSULT_ID");
        //String deptId = request.getParameter("CONSULT_DEPTID");
        //String dept = request.getParameter("CONSULT_DEPT");
        String userName =(String) bindInfo.get("USER_NAME");// request.getParameter("userName");
        String userPass =(String) bindInfo.get("USER_PWD");// request.getParameter("userPass");
        String consultType = "1";//request.getParameter("CONSULT_TYPE");
        //String itemId = request.getParameter("CONSULT_ITEMID");
        Map<String, Object> user = AppUtil.getLoginMember();
        int status = 0;
        if (null != user && StringUtils.isNotEmpty(user.get("YHZH").toString())) {// 判断是否已经登录
            status = 1;
            userName = user.get("YHZH").toString();
        } else {
            if(StringUtils.isNotEmpty(userName)&&StringUtils.isNotEmpty(userPass)){
                status = userInfoService.isExistsUser(userName,userPass);// StringUtil.getMd5Encode(userPass));
            }
        }
        if (status == 1) {// 用户状态正常
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            Map<String, Object> userMap = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { userName });
            variables.put("CREATE_USER", userName);
            variables.put("CREATE_USERNAME", userMap.get("YHMC"));
            consultService.saveOrUpdate(variables, "T_HD_CONSULT", entityId);

            result.put("msg", "保存成功");
            result.put("success", true);
        } else {
            result.put("msg", "用户名或者密码错误");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     *
     * 跳转到已提交咨询页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toZXList")
    public ModelAndView toZXList(HttpServletRequest request) {
        //Map<String,Object> user=AppUtil.getLoginMember();
        //String openId=AppUtil.getOpenID();
        String whereSql=" where T.CREATE_USER = '"+AppUtil.getLoginMember().get("YHZH")+ "'";
        List<Map<String, Object>> nlist=consultService.findZXBySql(whereSql);
        request.setAttribute("zxList", nlist);
        return new ModelAndView("weixin/interact/busizxList");
    }
    /**
     *
     * 跳转到咨询页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toTousu")
    public ModelAndView toTousu(HttpServletRequest request) {
        String openId=checkOpenId(request);
        Map<String, Object> userInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});
        if(userInfo!=null){
            Map<String, Object> user= userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{userInfo.get("USER_NAME")});
          //获取用户状态
            String state=(String) user.get("YHZT");
            if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                return new ModelAndView("weixin/noUse");
            }else{
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, user);
                return new ModelAndView("weixin/interact/tousu");
            }
        }else{
            return new ModelAndView("weixin/nobind");
        }
    }

    /**
     *
     * 描述 前台业务咨询
     *
     * @author Rider Chen
     * @created 2015-11-20 下午04:32:26
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "tousuSave")
    public void tousuSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("COMPLAIN_ID");
        Map<String, Object> user = AppUtil.getLoginMember();
        String userId = (String) user.get("USER_ID");//request.getParameter("CREATE_USERID");
        if (StringUtils.isNotEmpty(userId)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if(StringUtils.isEmpty(entityId)){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            String userName=(String) user.get("YHZH");
            String name=(String) user.get("YHMC");
            String tel=(String) user.get("SJHM");
            String email=(String) user.get("DZYX");
            variables.put("CREATE_USERNAME", userName);
            variables.put("CREATE_USERID", userId);
            variables.put("COMPLAIN_NAME", name);
            variables.put("COMPLAIN_PHONE", tel);
            variables.put("COMPLAIN_EMAIL", email);
            complainService.saveOrUpdate(variables, "T_HD_COMPLAIN", entityId);
            result.put("msg", "保存成功");
            result.put("success", true);
        }else{
            result.put("msg", "保存失败,未登录");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     *
     * 跳转到办事进度查询页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toRateQuery")
    public ModelAndView toRateQuery(HttpServletRequest request) {
        String exeId=request.getParameter("exeId");
        if(StringUtils.isNotEmpty(exeId)){
            // 获取流程实例信息
            Map<String, Object> flowExe = null;
            flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID"}, new Object[] {
                exeId});
            if (flowExe != null) {
                flowExe.put("success", true);
                if(flowExe.get("CUR_STEPNAMES")==null){
                    String defId = (String) flowExe.get("DEF_ID");
                    int flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
                    String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                    flowExe.put("CUR_STEPNAMES", startNodeName);
                }else{
                    flowExe.put("CUR_STEPNAMES", flowExe.get("CUR_STEPNAMES").toString().split(",")[0]);
                }
                //获取办理状态
                String runStatus = flowExe.get("RUN_STATUS").toString();
                if(!runStatus.equals("0")&&!runStatus.equals("1")){
                    flowExe.put("CUR_STEPNAMES", "已办结");
                }
                Map<String, Object> flowMappedInfo = flowMappedService.getFlowMapInfo((String) flowExe.get("DEF_ID"),
                        flowExe.get("CUR_STEPNAMES").toString().split(",")[0], "2");
                if(flowMappedInfo!=null){
                    flowExe.put("YS_NAME",flowMappedInfo.get("YS_NAME"));
                }
                String time=flowExe.get("CREATE_TIME").toString().substring(0,10);
                flowExe.put("CREATE_TIME", time);
                String state="";
                if("0".equals(runStatus)){
                    state="草稿";
                }else if("1".equals(runStatus)){
                    state="<b style=\"color: #008000;\">正在办理</b>";
                }else{
                    state="<b style=\"color:blue\">已办结</b>";
                }
                flowExe.put("state", state);
            }
            request.setAttribute("flowExe", flowExe);
            return new ModelAndView("weixin/bsquery/bjqueryView");
        }else{
            return new ModelAndView("weixin/bsquery/ratequery");
        }
    }
    /**
     *
     * 跳转到办事进度查询页面old
     * @param request
     * @return
     */
    @RequestMapping(params = "toRateQueryOld")
    public ModelAndView toRateQueryOld(HttpServletRequest request) {
        String openId=checkOpenId(request);
        Map<String, Object> userInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});
        if(userInfo!=null){
            Map<String, Object> user= userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{userInfo.get("USER_NAME")});
            HttpSession session = AppUtil.getSession();
            AppUtil.addMemberToSession(session, user);
            return new ModelAndView("weixin/bsquery/ratequery");
        }else{
            return new ModelAndView("weixin/nobind");
        }
    }

    /**
     *
     * 跳转到网上评议页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toOnline")
    public ModelAndView toOnline(HttpServletRequest request) {
        String openId=checkOpenId(request);
        Map<String, Object> userInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});
        if(userInfo!=null){
            Map<String, Object> user= userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{userInfo.get("USER_NAME")});
            //获取用户状态
            String state=(String) user.get("YHZT");
            if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                return new ModelAndView("weixin/noUse");
            }else{
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, user);
                return new ModelAndView("weixin/interact/onlinePy");
            }
        }else{
            return new ModelAndView("weixin/nobind");
        }
    }

    /**
     *
     * 跳转到网上评议页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toOnlineNew")
    public ModelAndView toOnlineNew(HttpServletRequest request) {
        String trustticket = request.getParameter("trustticket");
        String openid = request.getParameter("openid");
        Properties projectProperties = FileUtil.readProperties("project.properties");
        Properties configProperties = FileUtil.readProperties("conf/config.properties");
        String wxRedirectUrl = projectProperties.getProperty("WX_REDIRECT_URL");
        String wxUserinfoUrl = projectProperties.getProperty("WX_USERINFO_URL");
        String serviceUrl = configProperties.getProperty("serviceUrl");
        if (StringUtils.isEmpty(trustticket) || StringUtils.isEmpty(openid)) {
            String redirect = wxRedirectUrl + "redirect=" + serviceUrl + "/busInteractController.do?toOnlineNew";
            return new ModelAndView("redirect:" + redirect);
        } else {
            Map<String, String> headMap = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("trustticket", trustticket);
            jsonObject.put("openid", openid);
            String result = HttpSendUtil.sendHttpsPostJson(wxUserinfoUrl, headMap, jsonObject.toJSONString(), "UTF-8");
            Map<String, Object> resultMap = sysLogService.mztWxUserInfor(result);
            if (resultMap != null) {
                String state = resultMap.get("YHZT").toString();
                if (!state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)) {
                    HttpSession session = AppUtil.getSession();
                    AppUtil.addMemberToSession(session, resultMap);
                    return new ModelAndView("weixin/interact/onlinePy");
                } else {
                    return new ModelAndView("weixin/noUse");
                }
            } else {
                return new ModelAndView("weixin/noUserInfo");
            }
        }
    }
    /**
     * 
     * 描述 前台添加网上评议
     * 
     * @author Rider Chen
     * @created 2015-11-20 下午04:32:26
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveComment")
    public void saveComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> user = AppUtil.getLoginMember();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) user.get("USER_ID");//request.getParameter("CREATE_USERID");
        String entityId = request.getParameter("COMMENT_ID");
        String deptId = request.getParameter("COMMENT_DEPTID");
        if (StringUtils.isNotEmpty(userId)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
//            if(StringUtils.isEmpty(entityId)){
//                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
//            }
            variables.put("COMMENT_DEPTID", deptId);
            String name=(String) user.get("YHMC");
            String tel=(String) user.get("SJHM");
            String email=(String) user.get("DZYX");
            String zy=(String) user.get("ZY");
            //variables.put("CREATE_USERNAME", userName);
            variables.put("USER_ID", userId);
            variables.put("COMMENT_NAME", name);
            variables.put("COMMENT_JOB", zy);
            variables.put("COMMENT_EMAIL", email);
            variables.put("COMMENT_PHONE", tel);
            
            commentService.saveOrUpdate(variables, "T_HD_COMMENT", entityId);
            result.put("msg", "保存成功");
            result.put("success", true);
        }else{
            result.put("msg", "保存失败,未登录");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     *
     * 跳转到办件评价页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toReview")
    public ModelAndView toReview(HttpServletRequest request) {
        String openId=checkOpenId(request);
        Map<String, Object> userInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});
        if(userInfo!=null){
            Map<String, Object> user= userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{userInfo.get("USER_NAME")});
            //获取用户状态
            String state=(String) user.get("YHZT");
            if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                return new ModelAndView("weixin/noUse");
            }else{
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, user);
                List<Map<String, Object>> list=executionService.findWXWdbjList((String)user.get("YHZH"));
                for (int i = 0; i < list.size(); i++) {
                    Map<String,Object> map=list.get(i);
                    String zstate=String.valueOf(map.get("RUN_STATUS"));
                    //String pjstate=(String) map.get("isPj");
                    if("0".equals(zstate)){
                        list.get(i).put("zstate", "草稿");
                    }else if("1".equals(zstate)){
                        list.get(i).put("zstate", "办理中");
                    }else{
                        list.get(i).put("zstate", "已办结");
                    }
                }
                request.setAttribute("reviewList", list);
                return new ModelAndView("weixin/bsquery/bjreview");
            }
        }else{
            return new ModelAndView("weixin/nobind");
        }
    }
    
    /**
     * 
     * 跳转到办件评价页面
     * @param request
     * @return
     */
    @RequestMapping(params = "toReviewNew")
    public ModelAndView toReviewNew(HttpServletRequest request) {
        String trustticket = request.getParameter("trustticket");
        String openid = request.getParameter("openid");
        Properties projectProperties = FileUtil.readProperties("project.properties");
        Properties configProperties = FileUtil.readProperties("conf/config.properties");
        String wxRedirectUrl = projectProperties.getProperty("WX_REDIRECT_URL");
        String wxUserinfoUrl = projectProperties.getProperty("WX_USERINFO_URL");
        String serviceUrl = configProperties.getProperty("serviceUrl");
        if (StringUtils.isEmpty(trustticket) || StringUtils.isEmpty(openid)) {
            String redirect = wxRedirectUrl + "redirect=" + serviceUrl + "/busInteractController.do?toReviewNew";
            return new ModelAndView("redirect:" + redirect);
        } else {
            Map<String, String> headMap = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("trustticket", trustticket);
            jsonObject.put("openid", openid);
            String result = HttpSendUtil.sendHttpsPostJson(wxUserinfoUrl, headMap, jsonObject.toJSONString(), "UTF-8");
            Map<String, Object> resultMap = sysLogService.mztWxUserInfor(result);
            //获取用户状态
            if (resultMap != null) {
                String state = (String) resultMap.get("YHZT");
                if (state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)) {
                    return new ModelAndView("weixin/noUse");
                } else {
                    HttpSession session = AppUtil.getSession();
                    AppUtil.addMemberToSession(session, resultMap);
                    List<Map<String, Object>> list = executionService.findWXWdbjList((String) resultMap.get("YHZH"));
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map = list.get(i);
                        String zstate = String.valueOf(map.get("RUN_STATUS"));
                        if ("0".equals(zstate)) {
                            list.get(i).put("zstate", "草稿");
                        } else if ("1".equals(zstate)) {
                            list.get(i).put("zstate", "办理中");
                        } else {
                            list.get(i).put("zstate", "已办结");
                        }
                    }
                    request.setAttribute("reviewList", list);
                    return new ModelAndView("weixin/bsquery/bjreview");
                }
            } else {
                return new ModelAndView("weixin/noUserInfo");
            }
        }
    }

    /**
     * 
     * 跳转到咨询页面
     * @param request
     * @return
     */
    @RequestMapping(params = "reviewInfo")
    public ModelAndView reviewInfo(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        //String bjcxmm = (String) request.getParameter("bscxmm").trim();
        String itemCode = request.getParameter("itemCode");
        Map<String,Object>  bspj = new HashMap<String, Object>();
        bspj.put("EXE_ID", exeId);
        //bspj.put("YHZH", AppUtil.getLoginMember().get("YHZH"));
        bspj.put("FWSXBM",itemCode);
        
        // 获取流程实例信息
        Map<String, Object> flowExe = null;
        flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID", "ITEM_CODE" }, new Object[] {
            exeId,itemCode});
        if(flowExe!=null){
            bspj.put("CREATE_TIME", flowExe.get("CREATE_TIME"));
            bspj.put("END_TIME", flowExe.get("END_TIME"));
            bspj.put("ITEM_NAME", flowExe.get("ITEM_NAME"));
        }
        request.setAttribute("bspj", bspj);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("weixin/bsquery/reviewInfo");
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "savePjxx")
    public void savePjxx(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("PJ_ID");
        String exeId = request.getParameter("EXE_ID");
        String yhzh = (String)AppUtil.getLoginMember().get("YHZH");
        Map<String,Object>  bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
                new String[]{"EXE_ID","YHZH"},new Object[]{exeId,yhzh});
        if(bspj==null){
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            variables.put("YHZH", yhzh);
            if(StringUtils.isEmpty(entityId)){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            bspjService.saveOrUpdate(variables, "T_WSBS_BSPJ", entityId);
            result.put("msg", "评价成功");
            result.put("success", true);
        }else{
            result.put("msg", "此办事记录已经评价");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 跳转到办事指南
     * @param request
     * @return
     */
    @RequestMapping(params = "bsGuide")
    public ModelAndView bsGuide(HttpServletRequest request) {
        return new ModelAndView("weixin/bsguide/bsguide");
    }
    
    /**
     * 
     * 跳转到指南列表信息界面
     * @param request
     * @return
     */
    @RequestMapping(params = "guideList")
    public ModelAndView guideList(HttpServletRequest request) {
        String busCode ="grbs";// request.getParameter("busCode");
        if(StringUtils.isNotEmpty(busCode)&&busCode.equals("grbs")){
            request.setAttribute("navi", "个人办事");
            request.setAttribute("shenqiObj", "个人");
            request.setAttribute("busType", "grbs");
            List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("GRZTFL");
            List<Map<String,Object>>   rslist = busTypeService.findByTypeCodeForWebSite("GRRSSJ");
            List<Map<String,Object>>  tdlist = busTypeService.findByTypeCodeForWebSite("GRTDRQ");
            request.setAttribute("ztlist", ztlist);
            request.setAttribute("rslist", rslist);
            request.setAttribute("tdlist", tdlist);
        }else if(StringUtils.isNotEmpty(busCode)&&busCode.equals("frbs")){
            request.setAttribute("navi", "法人办事");
            request.setAttribute("shenqiObj", "法人");
            request.setAttribute("busType", "frbs");
            List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("FRZTFL");
            List<Map<String,Object>>   dxlist = busTypeService.findByTypeCodeForWebSite("QYDXFL");
            List<Map<String,Object>>  jylist = busTypeService.findByTypeCodeForWebSite("QYJYHD");
            request.setAttribute("ztlist", ztlist);
            request.setAttribute("dxlist", dxlist);
            request.setAttribute("jylist", jylist);
        }
        
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        //String busType = request.getParameter("busT");
        String[] sfzx = request.getParameterValues("SFZX[]");
        String sfzxbl = "0";
        if (sfzx != null) {
            sfzxbl = "1";
        }
        List<Map<String, Object>> list = null;
//        if (StringUtils.isNotEmpty(itemName) || StringUtils.isNotEmpty(busTypeIds)) {
//            list = serviceItemService.findWXListByName(busCode, itemName, busTypeIds,sfzxbl);
//        } else {
//            list = serviceItemService.findALLPublishItemsFront(busCode);
//        }
        log.info("grbs busCode:"+busCode+"itemName:"+itemName+"busTypeIds:"+busTypeIds+"sfzxbl:"+sfzxbl);
        list = serviceItemService.findWXListByName(busCode, itemName, busTypeIds,sfzxbl);
        
        request.setAttribute("busCode", busCode);
        request.setAttribute("itemList", list);
        return new ModelAndView("weixin/bsguide/guideList");
//        if(StringUtils.isNotEmpty(busCode)&&busCode.equals("grbs")){
//            return new ModelAndView("weixin/bsguide/guideList");
//        }else{
//            return new ModelAndView("weixin/bsguide/frguideList");
//        }
        
    }
    
    /**
     * 
     * 跳转到指南列表信息界面
     * @param request
     * @return
     */
    @RequestMapping(params = "frguideList")
    public ModelAndView frguideList(HttpServletRequest request) {
        String busCode ="frbs";// request.getParameter("busCode");
        request.setAttribute("navi", "法人办事");
        request.setAttribute("shenqiObj", "法人");
        request.setAttribute("busType", "frbs");
        List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("FRZTFL");
        List<Map<String,Object>>   dxlist = busTypeService.findByTypeCodeForWebSite("QYDXFL");
        List<Map<String,Object>>  jylist = busTypeService.findByTypeCodeForWebSite("QYJYHD");
        request.setAttribute("ztlist", ztlist);
        request.setAttribute("dxlist", dxlist);
        request.setAttribute("jylist", jylist);
        
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        //String busType = request.getParameter("busT");
        String[] sfzx = request.getParameterValues("SFZX[]");
        String sfzxbl = "0";
        if (sfzx != null) {
            sfzxbl = "1";
        }
        List<Map<String, Object>> list = null;
        log.info("frbs busCode:"+busCode+"itemName:"+itemName+"busTypeIds:"+busTypeIds+"sfzxbl:"+sfzxbl);
        list = serviceItemService.findWXListByName(busCode, itemName, busTypeIds,sfzxbl);
        
        request.setAttribute("busCode", busCode);
        request.setAttribute("itemList", list);
        return new ModelAndView("weixin/bsguide/frguideList");
    }
    
    /**
     * 
     * 描述 办事列表
     * @author Sundy Sun
     * @param request
     * @param response
     */
    @RequestMapping(params="loadbsSearch")
    public void loadbsSearch(HttpServletRequest request, HttpServletResponse response) {
        String itemName = request.getParameter("ITEM_NAME");
        String busTypeIds = request.getParameter("busTypeIds");
        String busType = request.getParameter("busType");
        String[] sfzx = request.getParameterValues("SFZX[]");
        String sfzxbl = "0";
        if (sfzx != null) {
            sfzxbl = "1";
        }
        List<Map<String, Object>> list = null;
//        if (StringUtils.isNotEmpty(itemName) || StringUtils.isNotEmpty(busTypeIds)) {
//            list = serviceItemService.findWXListByName(busType, itemName, busTypeIds,sfzxbl);
//        } else {
//            list = serviceItemService.findALLPublishItemsFront(busType);
//        }
        list = serviceItemService.findWXListByName(busType, itemName, busTypeIds,sfzxbl);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("itemList", list);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 跳转到指南详情
     * @param request
     * @return
     */
    @RequestMapping(params = "bsguideInfo")
    public ModelAndView bsguideInfo(HttpServletRequest request) {
        String itemCode = request.getParameter("itemCode");
        Map<String, Object> serviceItem = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(itemCode) && !itemCode.equals("undefined")) {
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });
            if (null != serviceItem) {
                // 获取项目ID
                String itemId = (String) serviceItem.get("ITEM_ID");
                // 获取材料信息列表
                List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId,null);
                serviceItem.put("applyMaters", applyMaters);
                // 办件类型（取字典类别:ServiceItemType)
                serviceItem.put("SXLX", dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SXLX"),
                        "ServiceItemType"));
                // 事项性质(取字典类别: ServiceItemXz)
                serviceItem.put("SXXZ", dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SXXZ"),
                        "ServiceItemXz"));
                // 是否收费(取字典类别 YesOrNo)
                serviceItem.put("SFSF", dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SFSF"),
                        "YesOrNo"));
                // 收费方式(取字典类别: ChargeType)
                serviceItem.put("SFFS", dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SFFS"),
                        "ChargeType"));
                // 办理结果(取字典类别: FinishType)
                serviceItem.put("FINISH_TYPE", dictionaryService.findByDicCodeAndTypeCode((String) serviceItem
                        .get("FINISH_TYPE"), "FinishType"));
                // 结果获取方式(取字典类别: FinishGetType)
                serviceItem.put("FINISH_GETTYPE", dictionaryService.findByDicCodeAndTypeCode((String) serviceItem
                        .get("FINISH_GETTYPE"), "FinishGetType"));
                Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                        new String[] { "DIC_CODE" }, new Object[] { serviceItem.get("ITEM_CODE") });
                if(null!=dictionary){
                    String dicName = dictionary.get("DIC_NAME").toString();
                    if(StringUtils.isNotEmpty(dicName)){
                        String[] dicNames =dicName.split(",");
                        serviceItem.put("TZLX", dictionary.get("TYPE_CODE"));
                        if (dicNames.length==2) {
                            serviceItem.put("DQJD", Integer.parseInt(dicNames[0]));
                            serviceItem.put("DQLC", Integer.parseInt(dicNames[1]));
                        }
                    }
                }
                Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[] { "DEPART_CODE" }, new Object[] { (String) serviceItem.get("SSBMBM") });
                serviceItem.put("SSBMBM", department.get("DEPART_NAME"));
                serviceItem.put("deptId", department.get("DEPART_ID"));
                
                
                Map<String, Object> e = busTypeService.getIdAndNamesByItemId(itemId);
                String busTypenames = "";
                String typeids = e.get("TYPE_IDS").toString();
                if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                        || typeids.contains("4028818c512273e70151227569a40001")
                        || typeids.contains("4028818c512273e70151229ae7e00003")
                        || typeids.contains("4028818c512273e7015122a38a130004")) {
                    busTypenames += "个人";
                }
                if (typeids.contains("4028818c512273e7015122a452220005")
                        || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                        || typeids.contains("4028818c512273e7015122c81f850007")
                        || typeids.contains("4028818c512273e7015122c872dc0008")
                        || typeids.contains("402883494fd4f3aa014fd4fc68260003")) {
                    if (busTypenames.length() > 1) {
                        busTypenames += ",";
                    }
                    busTypenames += "企业";
                }
                serviceItem.put("busTypenames", busTypenames);
            }

        }
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("busCode", itemCode);
        String busType=request.getParameter("busType");
        log.info("bsguideInfo busType:"+busType);
        request.setAttribute("busType", busType);
        return new ModelAndView("weixin/bsguide/bsguideInfo");
    }

    /**
     * 描述:微信预约部门列表页面
     *
     * @author Madison You
     * @created 2020/7/30 17:27:00
     * @param
     * @return
     */
    @RequestMapping(params = "wxBookDepartListView")
    public ModelAndView wxBookDepartListView(HttpServletRequest request) {
        String trustticket = request.getParameter("trustticket");
        String openid = request.getParameter("openid");
        Properties projectProperties = FileUtil.readProperties("project.properties");
        Properties configProperties = FileUtil.readProperties("conf/config.properties");
        String wxRedirectUrl = projectProperties.getProperty("WX_REDIRECT_URL");
        String wxUserinfoUrl = projectProperties.getProperty("WX_USERINFO_URL");
        String serviceUrl = configProperties.getProperty("serviceUrl");
        if (StringUtils.isEmpty(trustticket) || StringUtils.isEmpty(openid)) {
            String redirect = wxRedirectUrl + "redirect=" + serviceUrl + "/busInteractController.do?wxBookDepartListView";
            return new ModelAndView("redirect:" + redirect);
        } else {
            Map<String, String> headMap = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("trustticket", trustticket);
            jsonObject.put("openid", openid);
            Map<String, Object> resultMap = null;
            HttpSession session = AppUtil.getSession();
            session.setAttribute("openid", openid);
            session.setAttribute("trustticket", trustticket);
            Object curLoginMember = session.getAttribute("curLoginMember");
            if (curLoginMember != null) {
                resultMap = (Map<String, Object>) curLoginMember;
            } else {
                String result = HttpSendUtil.sendHttpsPostJson(wxUserinfoUrl, headMap, jsonObject.toJSONString(), "UTF-8");
                resultMap = sysLogService.mztWxUserInfor(result);
            }
            if (resultMap != null) {
                String state = resultMap.get("YHZT").toString();
                if (!state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)) {
                    AppUtil.addMemberToSession(session, resultMap);
                    List<Map<String, Object>> wxBookDepartList = whomeService.getWxBookDepartList();
                    List<Map<String, Object>> wxBookAppointList = whomeService.getWxBookAppointList(resultMap);
                    request.setAttribute("appointList", wxBookAppointList);
                    request.setAttribute("list", wxBookDepartList);
                    return new ModelAndView("weixin/book/wxBookDepartListView");
                } else {
                    return new ModelAndView("weixin/noUse");
                }
            } else {
                return new ModelAndView("weixin/noUserInfo");
            }
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 17:51:00
     * @param
     * @return
     */
    @RequestMapping(params = "wxBookBusinessChooseView")
    public ModelAndView wxBookBusinessChooseView(HttpServletRequest request) {
        List<Map<String, Object>> businessChooseList = whomeService.getWxBookBusinessChooseList(request);
        request.setAttribute("list", businessChooseList);
        return new ModelAndView("weixin/book/wxBookBusinessChooseView");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 18:08:00
     * @param
     * @return
     */
    @RequestMapping(params = "wxBookListView")
    public ModelAndView wxBookListView(HttpServletRequest request) {
        List<Map<String, Object>> wxBookTimeList = whomeService.getWxBookTimeList(request);
        Map<String, Object> loginMember = AppUtil.getLoginMember();
        request.setAttribute("wxBookTimeList", wxBookTimeList);
        request.setAttribute("businessCode",request.getParameter("businessCode"));
        request.setAttribute("departId",request.getParameter("departId"));
        request.setAttribute("loginMember", loginMember);
        return new ModelAndView("weixin/book/wxBookListView");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/31 16:21:00
     * @param
     * @return
     */
    @RequestMapping("/wxBookCancelAppoint")
    @ResponseBody
    public Map<String, Object> wxBookCancelAppoint(HttpServletRequest request) {
        String recordId = request.getParameter("recordId");
        HashMap<String, Object> variables = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        variables.put("STATUS", "0");
        serviceItemService.saveOrUpdate(variables, "T_CKBS_APPOINTMENT_APPLY", recordId);
        returnMap.put("success", true);
        returnMap.put("msg", "取消成功");
        return returnMap;
    }

}
