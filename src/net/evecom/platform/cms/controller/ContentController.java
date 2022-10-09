/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.cms.service.AuditContentService;
import net.evecom.platform.cms.service.ContentService;
import net.evecom.platform.cms.service.ModuleService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 描述 文章Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/contentController")
public class ContentController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ContentController.class);
    /**
     * 引入Service
     */
    @Resource
    private ContentService contentService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;

    /**
     * 引入Service
     */
    @Resource
    private ModuleService moduleService;
    /**
     * 引入Service
     */
    @Resource
    private AuditContentService auditContentService;

    /**
     * 
     * 描述 查看文章
     * 
     * @author Rider Chen
     * @created 2015-12-9 上午08:57:38
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(HttpServletRequest request) {
        String contentId = request.getParameter("contentId");
        Map<String, Object> content = new HashMap<String, Object>();
        Map<String, Object> module = new HashMap<String, Object>();
        String currentNav = request.getParameter("currentNav");
        if (StringUtils.isNotEmpty(contentId) && !contentId.equals("undefined")) {
            content = contentService.getByJdbc("T_CMS_ARTICLE_CONTENT", new String[] { "CONTENT_ID" },
                    new Object[] { contentId });
            int hits = content.get("HITS") == null ? 0 : Integer.parseInt(content.get("HITS").toString());
            /*更新访问记录会出现一个缓存错误，导致发布信息出现问题*/
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("HITS", hits + 1);
            contentService.saveOrUpdateContentHits(temp, "T_CMS_ARTICLE_CONTENT", contentId);
            content.put("ctid", "ct_" + content.get("MODULE_ID") + "_" + contentId);

            module = moduleService.getByJdbcModule("T_CMS_ARTICLE_MODULE", new String[] { "MODULE_ID" },
                    new Object[] { content.get("MODULE_ID") });
            if (null != module) {
                if (StringUtils.isEmpty(currentNav)) {
                    String path = (String) module.get("PATH");
                    String pids = path.replace(".", ",").substring(2, path.length() - 1);
                    String[] pidsArray = pids.split(",");
                    currentNav = getCurrentNav(pidsArray);
                }
            }
        }
        request.setAttribute("currentNav", currentNav);
        request.setAttribute("content", content);
        request.setAttribute("module", module);
        int contentType = content.get("CONTENTTYPE") == null ? 0 : Integer.valueOf(content.get("CONTENTTYPE")
                .toString());
        String linkurl = content.get("LINKURL") == null ? "" : content.get("LINKURL").toString();
        if (contentType == 2) {
            if (StringUtils.isEmpty(linkurl)) {
                log.error("错误:id=" + content.get("CONTENT_ID") + "类型为：网页链接 的文章。但没有网页链接地址（linkurl为空）");
                return new ModelAndView("redirect:/error.html");
            } else {
                if (linkurl.indexOf("http://") == -1 && linkurl.indexOf("https://") == -1) {
                    linkurl = "http://" + linkurl;
                }
                return new ModelAndView("redirect:" + linkurl);
            }
        } else if (contentType == 1) {
            if (StringUtils.isEmpty(linkurl)) {
                log.error("错误:id=" + content.get("CONTENT_ID") + "类型为：文件类型 的文章。但没有文件链接地址（linkurl为空）");
                return new ModelAndView("redirect:/error.html");
            } else {
                if (linkurl.indexOf("http://") == -1 && linkurl.indexOf("https://") == -1) {
                    Properties projectProperties = FileUtil.readProperties("project.properties");
                    String fileServer = projectProperties.getProperty("uploadFileUrl");
                    linkurl = fileServer + linkurl;
                }
                return new ModelAndView("redirect:" + linkurl);
            }
        } else {
            // 获取模版
            if (null != module) {
                String detail_template = module.get("DETAIL_TEMPLATE") == null ? "" : (String) module
                        .get("DETAIL_TEMPLATE");
                if (StringUtils.isNotEmpty(detail_template)) {
                    return new ModelAndView(detail_template);
                }
            }
        }
        // 默认模版
        return new ModelAndView("website/cms/info");
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        String moduleId = request.getParameter("moduleId");
        String currentNav = request.getParameter("currentNav");
        Map<String, Object> module = null;
        if (StringUtils.isNotEmpty(moduleId) && !moduleId.equals("undefined")) {
            module = moduleService.getByJdbcModule("T_CMS_ARTICLE_MODULE", new String[] { "MODULE_ID" },
                    new Object[] { moduleId });

            if (null != module) {
                if (StringUtils.isEmpty(currentNav)) {
                    String path = (String) module.get("PATH");
                    String pids = path.replace(".", ",").substring(2, path.length() - 1);
                    String[] pidsArray = pids.split(",");
                    currentNav = getCurrentNav(pidsArray);
                }
                String parentId = module.get("PARENT_ID").toString();
                if ("0".equals(parentId)) {
                    module.put("PARENT_ID", module.get("MODULE_ID"));
                }
            }

        }
        request.setAttribute("currentNav", currentNav);
        request.setAttribute("module", module);
        if (null != module) {
            if (module.get("PROPERTY_CLASS").toString().equals("5")) {// 外链类型的栏目
                String linkurl = module.get("LINKURL") == null ? "" : module.get("LINKURL").toString();
                if (StringUtils.isEmpty(linkurl)) {
                    log.error("错误:id=" + module.get("MODULE_ID") + "类型为：外链类型 的栏目。但没有链接地址（linkurl为空）");
                    return new ModelAndView("redirect:/error.html");
                } else {
                    if (linkurl.indexOf("http://") == -1 && linkurl.indexOf("https://") == -1) {
                        linkurl = "http://" + linkurl;
                    }
                    return new ModelAndView("redirect:" + linkurl);
                }
            } else {
                if ("403".equals(moduleId)) {
                    return new ModelAndView("site/bdc/info/index");
                }
                String list_template = module.get("LIST_TEMPLATE") == null ? "" : (String) module.get("LIST_TEMPLATE");
                if (StringUtils.isNotEmpty(list_template)) {
                    return new ModelAndView(list_template);
                }
            }
        }
        return new ModelAndView("website/cms/list");
    }

    /**
     * 
     * 描述 获取导航标识
     * 
     * @author Rider Chen
     * @created 2015-12-10 上午11:31:42
     * @param pidsArray
     * @return
     */
    public String getCurrentNav(String[] pidsArray) {
        for (String pid : pidsArray) {
            if ("22".equals(pid)) {// 机构设置
                return "jgsz";
            }
            if ("41".equals(pid)||"302".equals(pid)) {// 政策法规
                return "zcfg";
            }
            if ("49".equals(pid)) {// 文件资料
                return "wjzl";
            }
            if ("52".equals(pid)) {// 用户指南
                return "yhzn";
            }
            if ("44".equals(pid)) {// 青年文明号
                return "qnwmh";
            }
            if ("46".equals(pid)) {// 党建工作
                return "djgz";
            }
            if ("141".equals(pid)) {// 信息公开
                return "xxgk";
            }
        }
        return "sy";
    }

    /**
     * easyui AJAX请求数据 文章列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String moduleId = request.getParameter("moduleId");
        Map<String, Object> mapList = contentService.findfrontList(page, rows, moduleId);
        List<Map<String, Object>> list = (List) mapList.get("list");
        if (StringUtils.isNotEmpty(request.getParameter("endindex"))) {
            int endindex = request.getParameter("endindex") == null ? 0 : Integer.parseInt(request.getParameter(
                    "endindex").toString());
            int timeout = request.getParameter("timeout") == null ? 0 : Integer.parseInt(request
                    .getParameter("timeout").toString());
            for (Map<String, Object> map : list) {
                map.put("ITEMTITLE",
                        WebUtil.subString4JSPveiw(map.get("ITEMTITLE").toString(), endindex, true,
                                map.get("ITEMRELDATE").toString(), timeout));
            }
        }
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), list, null, JsonUtil.EXCLUDE,
                response);
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
        contentService.removeContent("T_CMS_ARTICLE_CONTENT", "CONTENT_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 文章记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 方法multiPublish
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiPublish")
    @ResponseBody
    public AjaxJson multiPublish(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String status = request.getParameter("status");
        if (StringUtils.isNotEmpty(selectColNames)) {
            String[] ids = selectColNames.split(",");
            for (String id : ids) {
                Map<String, Object> content = new HashMap<String, Object>();
                content.put("CONTENT_STATUS", status);
                if ("1".equals(status)) {
                    content.put("AUDIT_STATUS", 4);
                    /*如果是常见问题模块，那么需要记录到便民服务下的常见问题中*/
                    Map<String,Object> variables = contentService.getByJdbc("T_CMS_ARTICLE_CONTENT",
                            new String[]{"CONTENT_ID"}, new Object[]{Integer.parseInt(id)});
                    contentService.saveCommonProblem(variables);
                } else {
                    content.put("AUDIT_STATUS", 3);
                }
                contentService.saveOrUpdateContent(content, "T_CMS_ARTICLE_CONTENT", id);
            }
        }
        if ("1".equals(status)) {
            sysLogService.saveLog("发布了ID为[" + selectColNames + "]的 文章记录", SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("发布成功");
        } else {
            sysLogService.saveLog("取消发布了ID为[" + selectColNames + "]的 文章记录", SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("取消成功");
        }

        return j;
    }

    /**
     * 文章复制
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multicopy")
    @ResponseBody
    public AjaxJson multicopy(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String selectmoduleIds = request.getParameter("selectmoduleIds");
        if (StringUtils.isNotEmpty(selectColNames) && StringUtils.isNotEmpty(selectmoduleIds)) {
            String[] ids = selectColNames.split(",");
            String[] moduleIds = selectmoduleIds.split(",");
            contentService.multicopy(ids, moduleIds);

            sysLogService.saveLog("把【" + StringUtil.getStringByArray(ids)
                    + "】的文章信息复制到目标栏目【" + StringUtil.getStringByArray(moduleIds)
                    + "】", SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("复制成功");
        }
        return j;
    }

    /**
     * 文章剪切
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "paste")
    @ResponseBody
    public AjaxJson paste(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String moduleId = request.getParameter("moduleId");
        if (StringUtils.isNotEmpty(selectColNames)) {
            String[] ids = selectColNames.split(",");
            contentService.paste(ids, moduleId);
            sysLogService.saveLog("把【" + StringUtil.getStringByArray(ids) + "】的文章剪切到目标栏目【" + moduleId + "】",
                    SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("剪切成功");
        }
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
        Map<String, Object> content = new HashMap<String, Object>();
        request.setAttribute("curdate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            /*会有一个bug产生缓存，已修复*/
            content = contentService.getByJdbc("T_CMS_ARTICLE_CONTENT", new String[] { "CONTENT_ID" },
                    new Object[] { entityId });
            Map<String, Object> module = moduleService.getByJdbcModule("T_CMS_ARTICLE_MODULE",
                    new String[] { "MODULE_ID" }, new Object[] { content.get("MODULE_ID") });
            content.put("MODULE_NAME", module.get("MODULE_NAME"));
            request.setAttribute("content", content);
            int CONTENTTYPE = content.get("CONTENTTYPE") == null ? 0 : Integer.parseInt(content.get("CONTENTTYPE")
                    .toString());
            if (CONTENTTYPE == 1) {
                return new ModelAndView("cms/article/editFile");
            } else if (CONTENTTYPE == 2) {
                return new ModelAndView("cms/article/editWeb");
            } else if (CONTENTTYPE == 3) {
                return new ModelAndView("cms/article/editMedia");
            } else {
                return new ModelAndView("cms/article/editInfo");
            }
        } else {
            content.put("MODULE_NAME", request.getParameter("MODULE_NAME"));
            content.put("MODULE_ID", request.getParameter("MODULE_ID"));
            request.setAttribute("content", content);
            return new ModelAndView("cms/article/add");
        }
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONTENT_ID");
        String attachFileIds = request.getParameter("attachFileIds");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("AUDIT_STATUS", 0);
        }
        if (StringUtils.isNotBlank(attachFileIds)) {
            variables.put("IS_ATTACH", 1);
        }
        String recordId = contentService.saveOrUpdateContent(variables, "T_CMS_ARTICLE_CONTENT", entityId,
                "S_CMS_ARTICLE_CONTENT");
        // 判断是否含有附件ID的值
        if (StringUtils.isNotBlank(attachFileIds)) {
            if (StringUtils.isNotEmpty(entityId)) {
                contentService.updateFileToContentId(attachFileIds, entityId);
                //fileAttachService.updateBusTableRecordId(attachFileIds.split(","), entityId);
            } else {
                contentService.updateFileToContentId(attachFileIds, recordId);
                //fileAttachService.updateBusTableRecordId(attachFileIds.split(","), recordId);
            }
        }
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 文章记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            SysUser sysUser = AppUtil.getLoginUser();
            Map<String, Object> audit = new HashMap<String, Object>();
            audit.put("CONTENT_ID", recordId);
            audit.put("AUDIT_USERNAME", sysUser.getUsername());
            audit.put("AUDIT_USERID", sysUser.getUserId());
            audit.put("CURRENT_STATUS", "0");
            audit.put("NEXT_STATUS", "1");
            audit.put("AUDIT_STATUS", "0");
            audit.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            auditContentService.saveOrUpdate(audit, "T_CMS_ARTICLE_AUDIT", "");
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 文章记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "autosummary")
    @ResponseBody
    public AjaxJson autosummary(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String contentText = request.getParameter("contentText");
        String contentLength = request.getParameter("contentLength");
        int length = 60;
        if (contentLength != null && !contentLength.equals("") && StringUtil.isNumeric(contentLength)) {
            length = Integer.parseInt(contentLength);
        }
        String str = "";
        if (StringUtils.isNotEmpty(contentText)) {
            str = JsoupUtil.getSummary(contentText, length) + "...";
        } else {
            str = "";
        }
        j.setMsg(str);
        return j;
    }

    /**
     * easyui AJAX请求数据 手机APP文章列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/contentPagelist")
    public void contentPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String moduleId = request.getParameter("moduleId");
        Map<String, Object> mapList = contentService.findContentAppBySql(page, rows, moduleId);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据 手机详情
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/getContent")
    public void getContent(HttpServletRequest request, HttpServletResponse response) {
        String ctid = request.getParameter("ctid");
        Map<String, Object> content = contentService.getByJdbcContent("T_CMS_ARTICLE_CONTENT",
                new String[] { "CONTENT_ID" }, new Object[] { ctid });
        List<Map<String, Object>> list = contentService.findContentFileAppBySql(ctid);
        content.put("FILELIST", list);
        String json = JSON.toJSONString(content);
        this.setJsonString(json, response);
    }
    /**
     * 保存文章附件
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveFile")
    @ResponseBody
    public AjaxJson saveFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String fileName = request.getParameter("fileName");
        String fileUrl = request.getParameter("fileUrl");
        String filePath = request.getParameter("filePath");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("FILE_NAME", fileName);
        variables.put("FILE_URL", fileUrl);
        variables.put("FILE_PATH", filePath);
        variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String recordId = auditContentService.saveOrUpdate(variables, "T_CMS_ARTICLE_FILE", "");
        sysLogService.saveLog("新增了ID为[" + recordId + "]的 文章记录", SysLogService.OPERATE_TYPE_ADD);
        
        j.setMsg(recordId);
        return j;
    }
    /**
     * 
     * 列表页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "fileList")
    public ModelAndView fileList(HttpServletRequest request) {
        String contentId = request.getParameter("contentId");
        String attachFileIds = request.getParameter("attachFileIds");
        if(StringUtils.isNotEmpty(contentId) && StringUtils.isEmpty(attachFileIds)){
            List<Map<String, Object>> list = contentService.getAllByJdbc("T_CMS_ARTICLE_FILE",
                    new String[] { "CONTENT_ID" }, new Object[] { contentId });
            StringBuffer fileIds = new StringBuffer("");
            if(null != list && list.size() > 0){
                for (Map<String, Object> map : list) {
                    if(fileIds.length()>0){
                        fileIds.append(",");
                    }
                    fileIds.append(map.get("FILE_ID"));
                }
                attachFileIds = fileIds.toString();
            }
        }
        request.setAttribute("attachFileIds", attachFileIds);
        return new ModelAndView("cms/article/fileList");
    }
    /**
     * easyui AJAX请求数据 信息采编
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "fileDatagrid")
    public void fileDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String attachFileIds = request.getParameter("attachFileIds");
        List<Map<String, Object>> list = contentService.findContentFileBySql(attachFileIds);
        this.setListToJsonString(list.size(), list, null,
                JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiFileDel")
    @ResponseBody
    public AjaxJson multiFileDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        contentService.remove("T_CMS_ARTICLE_FILE","FILE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 文章附件记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 保存文章附件
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "savePrePublicTask")
    @ResponseBody
    public AjaxJson savePrePublicTask(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String TASK_ID = request.getParameter("TASK_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("TASK_STATUS", 0);
        variables.put("TASK_OWNERID", sysUser.getUserId());
        variables.put("TASK_OWNERNAME", sysUser.getFullname());
        variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String recordId = contentService.saveOrUpdate(variables, "T_ARTICLE_PREPUBLISHTASK", TASK_ID);
        sysLogService.saveLog("新增了ID为[" + recordId + "]的 预约发布记录", SysLogService.OPERATE_TYPE_ADD);        
        j.setMsg("预约成功");
        return j;
    }
    
    /**
     * 
     * 预约发布页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "prePublicInfo")
    public ModelAndView prePublicInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        List<Map<String, Object>> list = moduleService.getAllByJdbc("T_ARTICLE_PREPUBLISHTASK",
                new String[] { "CONTENT_ID","TASK_STATUS" }, new Object[] { entityId,0 });
        Map<String, Object> taskInfo = null;
        if(null != list && list.size() > 0){
            taskInfo =  list.get(0);
        }else{
            taskInfo = new HashMap<String, Object>();
            taskInfo.put("CONTENT_ID", entityId);
        }
        request.setAttribute("taskInfo", taskInfo);
        return new ModelAndView("cms/module/taskInfo");
    }

}
