/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.Des;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.hd.service.ConsultService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Controller
@RequestMapping("/consultController")
public class BusConsultController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusConsultController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * userInfoService
     */
    @Resource
    private UserInfoService userInfoService;
    /**
     * 引入service
     */
    @Resource
    private ConsultService consultService;

    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hd/busconsult/busConsultlist");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/viewYwzx")
    public ModelAndView viewYwzx(HttpServletRequest request) {
        String CONSULT_ITEMID = request.getParameter("itemId");
        String CONSULT_ITEMS = request.getParameter("itemName");
        if (StringUtils.isEmpty(CONSULT_ITEMS) && StringUtils.isNotEmpty(CONSULT_ITEMID)
                && !CONSULT_ITEMID.equals("undefined")) {
            Map<String, Object> serviceItem = consultService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" }, new Object[] { CONSULT_ITEMID });
            if (null != serviceItem) {
                CONSULT_ITEMS = serviceItem.get("ITEM_NAME").toString();
            }
        }
        request.setAttribute("CONSULT_ITEMID", CONSULT_ITEMID);
        request.setAttribute("CONSULT_ITEMS", CONSULT_ITEMS);
        return new ModelAndView("website/hd/zxhd");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/viewDept")
    public ModelAndView viewDept(HttpServletRequest request) {
        String deptCode = request.getParameter("deptCode");
        String deptName = "";
        String deptId = "";
        if (StringUtils.isNotEmpty(deptCode) && !deptCode.equals("undefined")) {
            Map<String, Object> department = consultService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_CODE" }, new Object[] { deptCode });
            if (null != department) {
                deptName = department.get("DEPART_NAME").toString();
                deptId = department.get("DEPART_ID").toString();
            }
        }
        request.setAttribute("deptId", deptId);
        request.setAttribute("deptCode", deptCode);
        request.setAttribute("deptName", deptName);
        return new ModelAndView("website/hd/zxhd");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("U.CREATE_TIME", "desc");
        List<Map<String, Object>> list = consultService.findBySqlFilter(filter,
                "where (U.STATUS IS NULL OR U.STATUS=1) ");
        //if ("__ALL".equals(resKey)) {// 判断是否超级管理员
        //    list = consultService.findBySqlFilter(filter, "where (U.STATUS IS NULL OR U.STATUS=1) ");
        //} else if (ywzxsly) {// 判断是否业务咨询受理员
        //    list = consultService.findBySqlFilter(filter,
        //            " where U.REPLY_FLAG = 0 AND (U.CONSULT_DEPTID is null or U.AUDIT_USER is null ) " +
        //            " AND (U.STATUS IS NULL OR U.STATUS=1) ");
        //} else if (ywzxbmld) {// 判断是否业务咨询部门审核员
        //    filter.addFilter("Q_U.CONSULT_DEPTID_=", sysUser.getDepId());
        //    list = consultService.findBySqlFilter(filter, " where (U.STATUS IS NULL OR U.STATUS=1)");
        //}else{
        //    this.setListToJsonString(0, list, null, JsonUtil.EXCLUDE, response);
        //}
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> serviceItem = consultService.getByJdbc("T_HD_CONSULT", new String[] { "CONSULT_ID" },
                    new Object[] { entityId });
            Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { serviceItem.get("CREATE_USER") });

            request.setAttribute("userInfo", userInfo);
            request.setAttribute("busConsult", serviceItem);
        }
        // DictionaryService dictionaryService = (DictionaryService)
        // AppUtil.getBean("dictionaryService");
        // List<Map<String, Object>> list =
        // dictionaryService.findByTypeCode("SerialParameter");
        // request.setAttribute("serialParam", list);
        return new ModelAndView("hd/busconsult/busConsultInfo");
    }

    /**
     * 跳转到用户中心信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/yhzxInfo")
    public ModelAndView yhzxInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> serviceItem = consultService.getByJdbc("T_HD_CONSULT", new String[] { "CONSULT_ID" },
                    new Object[] { entityId });
            Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { serviceItem.get("CREATE_USER") });

            request.setAttribute("userInfo", userInfo);
            request.setAttribute("busConsult", serviceItem);
        }
        return new ModelAndView("website/yhzx/zxxx");
    }
    /**
     * 
     * 
     * 加载前台咨询事项信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = consultService.findByItemSql(" where U.FWSXZT = 1 ");
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("ITEM_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        return new ModelAndView("website/hd/ItemSelector");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/loadDatagrid")
    public void loadDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("U.CREATE_TIME", "desc");
        List<Map<String, Object>> list = consultService.findByItemSqlFilter(filter, " where U.FWSXZT = 1 ");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
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
    @RequestMapping("/saveConsult")
    public void saveConsult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gsrf=request.getParameter("GSRF");
        String gsrfOfSession=String.valueOf(request.getSession().getAttribute("GSRF"));
        if(StringUtils.isEmpty(gsrf)||!gsrf.equals(gsrfOfSession)){
            return;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("CONSULT_ID");
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        if(StringUtils.isNotEmpty(userPass)){
            userPass=Des.strDec(request.getParameter("userPass"), "1", "2", "3");
        }
        String consultType = request.getParameter("CONSULT_TYPE");
        String itemId = request.getParameter("CONSULT_ITEMID");
        String yhzh = request.getParameter("YHZH");
        Map<String, Object> user = AppUtil.getLoginMember();
        int status = 0;
        if (null != user && StringUtils.isNotEmpty(user.get("YHZH").toString())) {// 判断是否已经登录
            status = 1;
            userName = user.get("YHZH").toString();
        } else if (StringUtils.isNotEmpty(yhzh)) {// 手机端判断
            status = 1;
            userName = yhzh;
        }else {
            if(StringUtils.isNotEmpty(userName)&&StringUtils.isNotEmpty(userPass)){
                status = userInfoService.isExistsUser(userName, StringUtil.getMd5Encode(userPass));   
            }
        }
        if (status == 1) {// 用户状态正常
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            Map<String, Object> userMap = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { userName });
            if(null!=consultType&&"0".equals(consultType)){
                Map<String, Object> serviceItem = userInfoService.getByJdbc("T_WSBS_SERVICEITEM",
                        new String[] { "ITEM_ID" }, new Object[] { itemId });
                if(null!=serviceItem){
                    String deptCode = serviceItem.get("SSBMBM")==null?"":serviceItem.get("SSBMBM").toString();
                    if(StringUtils.isNotEmpty(deptCode)){
                        Map<String, Object> department = userInfoService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                                new String[] { "DEPART_CODE" }, new Object[] { deptCode });
                        if(null!=department){
                            variables.put("CONSULT_DEPTID", department.get("DEPART_ID"));
                            variables.put("CONSULT_DEPT", department.get("DEPART_NAME"));
                        }
                    }
                }
            }
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
     * easyui AJAX请求数据 用户中心我的咨询列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = consultService.findfrontList(page, rows);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 手机APP用户中心我的咨询列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/consultPagelist")
    public void consultPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String userName = request.getParameter("userName");
        Map<String, Object> mapList = consultService.findfrontAppList(page, rows, userName);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 办事指南咨询列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/bsznPagelist")
    public void bsznPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String itemId = request.getParameter("itemId");
        Map<String, Object> mapList = consultService.findfrontList(page, rows, itemId);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONSULT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        String status = request.getParameter("AUDIT_STATUS");

        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 直接回复
            variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("REPLY_FLAG", "1");
            variables.put("REPLY_USER", sysUser.getUserId());
        } else {// 转发部门
            variables.put("AUDIT_USER", sysUser.getUserId());
        }
        String recordId = consultService.saveOrUpdateConsult(variables, "T_HD_CONSULT", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            if (StringUtils.isNotEmpty(status) && status.equals("1")) {
                sysLogService.saveLog("回复了ID为[" + entityId + "]的业务咨询记录", SysLogService.OPERATE_TYPE_EDIT);
            } else {
                sysLogService.saveLog("转发了ID为[" + entityId + "]的业务咨询记录", SysLogService.OPERATE_TYPE_EDIT);
            }

        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的业务咨询记录", SysLogService.OPERATE_TYPE_ADD);
        }

        j.setMsg("保存成功");
        return j;
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        Map<String, Object> variables=new HashMap<String, Object>();
        String[] colnames=selectColNames.split(",");
        for (int i = 0; i < colnames.length; i++) {
            variables.put("consult_id", colnames[i]);
            variables.put("STATUS", "-1");
            consultService.saveOrUpdate(variables, "T_HD_CONSULT", colnames[i]);
        }
        //consultService.remove("T_HD_CONSULT", "consult_id", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的业务咨询记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> serviceItem = consultService.getByJdbc("T_HD_CONSULT", new String[] { "CONSULT_ID" },
                    new Object[] { entityId });
            Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { serviceItem.get("CREATE_USER") });

            request.setAttribute("userInfo", userInfo);
            request.setAttribute("busConsult", serviceItem);
        }
        return new ModelAndView("website/hd/ywzxDetail");
    }
    /**
     * easyui AJAX请求数据 绿色通道办事指南咨询列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/bsznLstdlist")
    public void bsznLstdlist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String busTypeIds = request.getParameter("busTypeIds");
        Map<String, Object> mapList = consultService.findbsznLstdList(page, rows, busTypeIds);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
}
