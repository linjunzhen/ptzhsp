/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

import java.io.IOException;
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
import net.evecom.platform.hd.service.CommentService;
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
 * 描述  网上评议Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/commentController")
public class CommentController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CommentController.class);
    /**
     * 引入Service
     */
    @Resource
    private CommentService commentService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 
     * 描述 view
     * @author Rider Chen
     * @created 2015-11-24 下午03:40:46
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hd/comment/list");
    }
    /**
     * 
     * 描述 前台公众用户登录
     * @author Rider Chen
     * @created 2015-12-14 上午09:07:41
     * @param request
     * @return
     */
    @RequestMapping(params = "wspy")
    public ModelAndView tsjd(HttpServletRequest request) {
        return new ModelAndView("website/hd/wspy");
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
        List<Map<String, Object>>  list = commentService.findBySqlFilter(filter,
                "where (U.STATUS IS NULL OR U.STATUS=1)");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
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
        Map<String, Object> variables=new HashMap<String, Object>();
        String[] colnames=selectColNames.split(",");
        for (int i = 0; i < colnames.length; i++) {
            variables.put("COMMENT_ID", colnames[i]);
            variables.put("STATUS", "-1");
            commentService.saveOrUpdate(variables, "T_HD_COMMENT", colnames[i]);
        }
        //commentService.remove("T_HD_COMMENT","COMMENT_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 网上评议记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  comment = commentService.getByJdbc("T_HD_COMMENT",
                    new String[]{"COMMENT_ID"},new Object[]{entityId});
            comment.put("COMMENT_FZZC", getComment(comment.get("COMMENT_FZZC").toString()));
            comment.put("COMMENT_BSXL", getComment(comment.get("COMMENT_BSXL").toString()));
            comment.put("COMMENT_LJQZ", getComment(comment.get("COMMENT_LJQZ").toString()));
            comment.put("COMMENT_YFXZ", getComment(comment.get("COMMENT_YFXZ").toString()));
            comment.put("COMMENT_FWZL", getComment(comment.get("COMMENT_FWZL").toString()));
            request.setAttribute("comment", comment);
        }
        return new ModelAndView("hd/comment/info");
    }
    /**
     * 
     * 描述  满意度格式化
     * @author Rider Chen
     * @created 2015-11-25 上午10:38:12
     * @return
     */
    public String getComment(String val){
        
        if(val.equals("0")){
            return "<font color='red'>不满意</font>";
        }else if(val.equals("1")){
            return "<font color='blue'>基本满意</font>";
        }else if(val.equals("2")){
            return "<font color='green'>满意</font>";
        }else{
            return "<font color='yellow'>不了解</font>";
        }
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
        String entityId = request.getParameter("COMMENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = commentService.saveOrUpdate(variables, "T_HD_COMMENT", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 网上评议记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 网上评议记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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

        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("COMMENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        commentService.saveOrUpdate(variables, "T_HD_COMMENT", entityId);
        result.put("msg", "保存成功");
        result.put("success", true);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
}

