/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import java.util.Date;
import java.util.HashMap;
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
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.zzhy.service.GtptxxglService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  个体平台信息管理Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/gtptxxglController")
public class GtptxxglController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(GtptxxglController.class);
    /**
     * 引入Service
     */
    @Resource
    private GtptxxglService gtptxxglService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    
    
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
        gtptxxglService.remove("T_COMMERCIAL_GTPTXXGL","PT_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 个体平台信息管理记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("zzhy/gtptxxgl/list");
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  gtptxxgl = gtptxxglService.getByJdbc("T_COMMERCIAL_GTPTXXGL",
                    new String[]{"PT_ID"},new Object[]{entityId});
            request.setAttribute("gtptxxgl", gtptxxgl);
        }
        return new ModelAndView("zzhy/gtptxxgl/info");
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
        String entityId = request.getParameter("PT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = gtptxxglService.saveOrUpdate(variables, "T_COMMERCIAL_GTPTXXGL", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 个体平台信息管理记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 个体平台信息管理记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.PT_ID", "DESC");
        List<Map<String, Object>> list = gtptxxglService.findBySqlFilter(filter, null);
        encryptionService.listDecrypt(list, "T_COMMERCIAL_GTPTXXGL");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridWw")
    public void datagridWw(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.PT_ID", "DESC");
        List<Map<String, Object>> list = gtptxxglService.findBySqlFilter(filter, null);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                new String[] { "PT_ID", "PT_NAME", "PT_HY", "PT_ZZXS", "LIAISON_NAME", "CREATE_TIME" },
                JsonUtil.INCLUDE, response);
    }
    /**
     * 平台信息查询
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "gtptxxSelector")
    public ModelAndView gtptxxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("website/zzhy/gtptxxSelector");
    }
    /**
     * 
     * 描述：判断平台验证码是否正确
     * @author Rider Chen
     * @created 2020年11月30日 下午5:15:40
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/ptyzmsfzq")
    @ResponseBody
    public void ptyzmsfzq(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String validateCode = request.getParameter("validateCode");
        String id = request.getParameter("PT_ID");
        String pwd = request.getParameter("PT_PWD");
        String kaRandCode = (String) request.getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(kaRandCode)
                && validateCode.equals(kaRandCode)) {
            Map<String, Object> gtptxxgl = gtptxxglService.getByJdbc("T_COMMERCIAL_GTPTXXGL", new String[] { "PT_ID" },
                    new Object[] { id });
            if (null != gtptxxgl && gtptxxgl.size() > 0) {
                String PT_PWD = gtptxxgl.get("PT_PWD") == null ? "" : gtptxxgl.get("PT_PWD").toString();
                if (StringUtils.isNotEmpty(PT_PWD) && StringUtils.isNotEmpty(pwd) && pwd.equals(PT_PWD)) {
                    result.put("success", true);
                    result.put("msg", "验证正确!");
                } else {
                    result.put("success", false);
                    result.put("msg", "平台验证码错误!");
                }

            } else {
                result.put("success", false);
                result.put("msg", "平台信息错误!");
            }

        } else {
            result.put("success", false);
            result.put("msg", "验证码填写错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述：根据平台ID获取平台信息
     * @author Rider Chen
     * @created 2020年12月1日 上午10:30:21
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getGtptxx")
    @ResponseBody
    public void getGtptxx(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String id = request.getParameter("PT_ID");
        if (StringUtils.isNotEmpty(id)) {
            Map<String, Object> gtptxxgl = gtptxxglService.getByJdbc("T_COMMERCIAL_GTPTXXGL", new String[] { "PT_ID" },
                    new Object[] { id });
            if (null != gtptxxgl) {
                String BUSINESS_PLACE = "";
                int REGISTER_ADDR_NUM = gtptxxgl.get("REGISTER_ADDR_NUM") == null ? 1
                        : Integer.parseInt(gtptxxgl.get("REGISTER_ADDR_NUM").toString());
                BUSINESS_PLACE = gtptxxgl.get("BUSINESS_PLACE").toString() + REGISTER_ADDR_NUM +"（集群注册）";

                gtptxxgl.put("REGISTER_ADDR_NUM", REGISTER_ADDR_NUM + 1);
                gtptxxglService.saveOrUpdate(gtptxxgl, "T_COMMERCIAL_GTPTXXGL", gtptxxgl.get("PT_ID").toString());
                gtptxxgl.put("BUSINESS_PLACE", BUSINESS_PLACE);
                StringBuffer INDIVIDUAL_NAME = new StringBuffer("平潭");
                INDIVIDUAL_NAME.append(gtptxxgl.get("PT_NAME")).append(gtptxxgl.get("PT_HY"))
                        .append(gtptxxgl.get("PT_ZZXS"));
                gtptxxgl.put("INDIVIDUAL_NAME", INDIVIDUAL_NAME);
                StringBuffer WORD_NUM = new StringBuffer("");
                WORD_NUM.append(gtptxxgl.get("PT_NAME"));
                gtptxxgl.put("WORD_NUM", WORD_NUM);

                gtptxxgl.remove("PT_PWD");
                encryptionService.mapDecrypt(gtptxxgl, "T_COMMERCIAL_GTPTXXGL");
                result.put("success", true);
                result.put("msg", "验证正确!");
                result.put("data", gtptxxgl);
            } else {
                result.put("success", false);
                result.put("msg", "未查询到平台数据!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "参数错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
   /**
    * 
    * 描述 根据办件流水号获取办件状态
    * @author Rider Chen
    * @created 2021年2月22日 下午4:14:18
    * @param request
    * @param response
    */
    @RequestMapping("/getTaskStatus")
    @ResponseBody
    public void getTaskStatus(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String exeId = request.getParameter("exeId");
        if (StringUtils.isNotEmpty(exeId)) {
            String taskStatus = gtptxxglService.getTaskStatus(exeId);
            result.put("success", true);
            result.put("msg", "获取成功!");
            result.put("data", taskStatus);
        } else {
            result.put("success", false);
            result.put("msg", "参数错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
}

