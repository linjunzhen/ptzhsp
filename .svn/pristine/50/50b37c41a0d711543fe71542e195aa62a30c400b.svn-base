/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.controller.BusApplyController;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 投资项目申报Controller(前端)
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年6月19日 上午17:45:12
 */

@Controller
@RequestMapping("/projectWebsiteApplyController")
public class ProjectWebsiteApplyController extends BaseController {
    /**
     * 引入字典类型常量
     */
    public static final String TYPE_CODE = "PROJECTTYPE";
    /**
     * projectWebsiteApplyService
     */
    @Resource
    private ProjectWebsiteApplyService projectWebsiteApplyService;

    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * projectApplyController
     */
    @Resource
    private ProjectApplyController projectApplyController;
    /**
     * busApplyController
     */
    @Resource
    private BusApplyController busApplyController;
    /**
     * 修改或者修改操作(前台)
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "websiteSaveOrUpdate")
    @ResponseBody
    public AjaxJson websiteSaveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        if (StringUtils.isEmpty(entityId) && userInfo != null) {
            variables.put("CREATOR_NAME", userInfo.get("YHMC").toString());
            variables.put("CREATOR_ID", userInfo.get("YHZH").toString());
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = projectWebsiteApplyService.saveOrUpdate(variables, "SPGL_XMJBXXB", entityId);
        String lerepInfo = variables.get("LEREP_INFO").toString();
        List<Map<String, Object>> lerepInfoList = JSON.parseObject(lerepInfo, List.class);
        // 删除项目单位信息
        projectWebsiteApplyService.remove("SPGL_XMDWXXB", "JBXX_ID", new Object[] { recordId });
        for (Map<String, Object> map : lerepInfoList) {
            map.put("JBXX_ID", recordId);
            // 保存项目单位信息
            projectWebsiteApplyService.saveOrUpdate(map, "SPGL_XMDWXXB", null);
        }
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 投资项目申报记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 投资项目申报记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到信息页面（前端）
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "websiteInfo")
    public ModelAndView websiteInfo(HttpServletRequest request) {
        String projectCode = request.getParameter("projectCode");
        String entityId = request.getParameter("entityId");
        String type = request.getParameter("type");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  projectApply = projectWebsiteApplyService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
            request.setAttribute("projectApply", projectApply);
        }
        //前台共性材料
        projectApplyController.receptionMaterInfo(request, projectCode);
        //后台批复材料
        projectApplyController.backstageMaterInfo(request, projectCode);
        //施工许可电子证照信息
        projectApplyController.sgkxMaterInfo(request, projectCode);
        //文件服务器
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();
        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        session.setAttribute("fileServer", fileServer);
        
        request.setAttribute("type", type);
        request.setAttribute("entityId", entityId);
        return new ModelAndView("tzxm/xmdjDetail");
    }
    
    /**
     * 跳转到进度查询页面（前端）
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "progressQuery")
    public void progressQuery(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(rows==null || "".equals(rows)) {
            rows = "15";//页面大小默认为15
        }
        if(page==null || "".equals(page)) {
            page = "1";//当前页默认第一页
        }
        String textValue = request.getParameter("textValue");
        String projectName = request.getParameter("projectName");
        String projectCode = request.getParameter("projectCode");
        if (textValue != null && !"".equals(textValue)) {
            textValue = URLDecoder.decode(textValue, "utf-8");
            textValue = textValue.trim();
        }
        if (projectName != null && !"".equals(projectName)) {
            projectName = URLDecoder.decode(projectName, "utf-8");
            projectName = projectName.trim();
        }
        if (projectCode != null && !"".equals(projectCode)) {
            projectCode = URLDecoder.decode(projectCode, "utf-8");
            projectCode = projectCode.trim();
        }
        List<Map<String, Object>> projectList = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = projectWebsiteApplyService.findProjectMap(page,
                rows, textValue, projectName, projectCode);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        if(resultList != null && resultList.size() > 0) {
            for(Map<String, Object> projectInfo : resultList) {
                String createTime = projectInfo.get("CREATE_TIME").toString();
                if(createTime!=null && !"".equals(createTime)) {
                    createTime = createTime.substring(0, 10);
                    projectInfo.put("CREATE_TIME", createTime);
                }
                String projectType = projectInfo.get("PROJECT_TYPE").toString();
                if(projectType!=null && !"".equals(projectType)) {
                    String typeName = dictionaryService.getDicNames(TYPE_CODE, projectType);
                    projectInfo.put("TYPE_NAME", typeName);
                }
                projectList.add(projectInfo);
            }
        }
        request.setAttribute("textValue", textValue);
        request.setAttribute("projectName", projectName);
        request.setAttribute("projectCode", projectCode);
        this.setListToJsonString(Integer.parseInt(resultMap.get("total").toString()), projectList,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到信息页面（进度查询）
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "projectInfo")
    public ModelAndView projectInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")){
            Map<String,Object>  projectApply = projectWebsiteApplyService.getByJdbc("SPGL_XMJBXXB",
                    new String[]{"ID"},new Object[]{entityId});
            request.setAttribute("projectApply", projectApply);
        }
        return new ModelAndView("tzxm/projectInfo");
    }
    
    /**
     * 首页信息查询跳转界面
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request) throws UnsupportedEncodingException {
        String textValue = request.getParameter("textValue");
        if (textValue != null && !"".equals(textValue)) {
            String encodeValue = URLEncoder.encode(textValue, "utf-8");
            request.setAttribute("encodeValue", encodeValue);
        }
        String projectName = request.getParameter("projectName");
        if (projectName != null && !"".equals(projectName)) {
            String encodeName = URLEncoder.encode(projectName, "utf-8");
            request.setAttribute("encodeName", encodeName);
        }
        String projectCode = request.getParameter("projectCode");
        if (projectCode != null && !"".equals(projectCode)) {
            String encodeCode = URLEncoder.encode(projectCode, "utf-8");
            request.setAttribute("encodeCode", encodeCode);
        }
        request.setAttribute("textValue", textValue);
        request.setAttribute("projectName", projectName);
        request.setAttribute("projectCode", projectCode);
        return new ModelAndView("tzxm/progressQuery");
    }
    
    /**
     * 
     * 描述 调整到工程项目信息明细界面
     * @author Scolder Lin
     * @created 2019年9月16日 上午10:42:27
     * @param request
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getApproveItem")
    public ModelAndView getApproveItem(HttpServletRequest request) throws ParseException {
        String accessToken = request.getParameter("access_token");
        String itemInstanceCode = request.getParameter("item_instance_code");
        String projectCode = request.getParameter("project_code");
        if (accessToken != null && !"".equals(accessToken) && !"null".equals(accessToken)) {
            String[] accessTokens = accessToken.split("_");
            String tokenId = accessTokens[1];
            String tokenSortNumber = accessTokens[0];
            Map<String, Object> tokenInfo = projectWebsiteApplyService.getByJdbc("T_MSJW_SYSTEM_TOKEN",
                    new String[] { "TOKEN_ID" }, new Object[] { tokenId });
            if (tokenInfo != null && tokenInfo.size() > 0) {
                if (tokenInfo.get("SORT_NUMBER") != null) {
                    String sortNumber = tokenInfo.get("SORT_NUMBER").toString();
                    if (tokenSortNumber.equals(sortNumber)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date rowDate = new Date();
                        long rowDateValue = rowDate.getTime();
                        Date createTime = sdf.parse(tokenInfo.get("CREATE_TIME").toString());
                        long createTimeValue = createTime.getTime();
                        if ((rowDateValue - createTimeValue) > 1000 * 60 * 30) {
                            request.setAttribute("returnInfo", "凭证过期，请重新生成");
                        } else {
                            boolean paramIsNull = false;
                            if (itemInstanceCode != null && !"".equals(itemInstanceCode)) {
                                itemInstanceCode = itemInstanceCode.trim();
                            } else {
                                paramIsNull = true;
                            }
                            if (projectCode != null && !"".equals(projectCode)) {
                                projectCode = projectCode.trim();
                            } else {
                                paramIsNull = true;
                            }
                            Map<String, Object> exeInfo = new HashMap<String, Object>();
                            String exeId = "";
                            String itemCode = "";
                            if (!paramIsNull) {
                                // 判断itemInstanceCode是否是exeId
                                exeInfo = projectWebsiteApplyService.getByJdbc("JBPM6_EXECUTION",
                                        new String[] { "EXE_ID" }, new Object[] { itemInstanceCode });
                                if (exeInfo == null || exeInfo.size() <= 0) {
                                    exeInfo = projectWebsiteApplyService.getByJdbc("JBPM6_EXECUTION_EVEHIS",
                                            new String[] { "EXE_ID" }, new Object[] { itemInstanceCode });
                                } 
                                if (exeInfo == null || exeInfo.size() <= 0) {
                                    exeInfo = projectWebsiteApplyService.getByJdbc("JBPM6_EXECUTION_EVEHIS",
                                            new String[] { "EXE_ID" }, new Object[] { itemInstanceCode });
                                    request.setAttribute("isFiled", "1");
                                    exeInfo = projectWebsiteApplyService.findExecuteInfo(itemInstanceCode, projectCode);
                                }
                                // 流程信息
                                if (exeInfo != null && exeInfo.size() > 0) {
                                    if (exeInfo.get("PROJECT_CODE") != null) {
                                        if (projectCode.equals(exeInfo.get("PROJECT_CODE").toString())) {
                                            itemCode = exeInfo.get("ITEM_CODE").toString();
                                            exeId = exeInfo.get("EXE_ID").toString();
                                            // 办理状态
                                            if (exeInfo.get("RUN_STATUS") != null) {
                                                String runStatus = exeInfo.get("RUN_STATUS").toString();
                                                if ("0".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "草稿");
                                                } else if ("1".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "正在办理");
                                                } else if ("2".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "已办结（正常结束）");
                                                } else if ("3".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "已办结（审核通过）");
                                                } else if ("4".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "已办结（审核不通过）");
                                                } else if ("5".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "已办结（退回）");
                                                } else if ("6".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "强制结束");
                                                } else if ("7".equals(runStatus)) {
                                                    exeInfo.put("runStatus", "预审不通过");
                                                } else {
                                                    exeInfo.put("runStatus", "未受理");
                                                }
                                            }
                                            // 申报来源
                                            if (exeInfo.get("SQFS") != null) {
                                                String applyType = exeInfo.get("SQFS").toString();
                                                if ("1".equals(applyType)) {
                                                    exeInfo.put("applyType", "网上申请");
                                                } else if ("2".equals(applyType)) {
                                                    exeInfo.put("applyType", "窗口收件");
                                                } else {
                                                    exeInfo.put("applyType", "省网办发起");
                                                }
                                            }
                                            // 项目信息
                                            Map<String, Object> projectMap = projectWebsiteApplyService.getByJdbc(
                                                    "SPGL_XMJBXXB", new String[] { "PROJECT_CODE" },
                                                    new Object[] { projectCode });
                                            if (projectMap != null && projectMap.size() > 0) {
                                                if (projectMap.get("LEREP_INFO") != null) {
                                                    String lerepInfo = projectMap.get("LEREP_INFO").toString();
                                                    if (lerepInfo.contains("[")) {
                                                        lerepInfo = lerepInfo.substring(1, lerepInfo.length() - 1);
                                                    }
                                                    // 法人信息
                                                    Map<String, Object> lerepMap = JSON.parseObject(lerepInfo,
                                                            Map.class);
                                                    request.setAttribute("lerepMap", lerepMap);
                                                }
                                            } else {
                                                request.setAttribute("returnInfo", "您的工程代码查询不到相应的记录");
                                            }
                                            request.setAttribute("projectMap", projectMap);
                                        } else {
                                            request.setAttribute("returnInfo", "您的审批事项实例编码与工程代码对应不上");
                                        }
                                    }
                                } else {
                                    request.setAttribute("returnInfo", "您的审批事项实例编码查询不到相应的记录");
                                }
                            } else {
                                request.setAttribute("returnInfo", "审批事项实例编码或工程代码为空");
                            }
                            if (exeId != null && !"".equals(exeId)) {
                                SqlFilter filter = new SqlFilter(request);
                                filter.addSorted("T.STEP_SEQ", "asc");
                                filter.addFilter("Q_T.EXE_ID_EQ", exeId);
                                filter.addFilter("Q_T.STEP_SEQ_NEQ", "0");
                                PagingBean pb = filter.getPagingBean();
                                pb.setStart(0);
                                pb.setPageSize(100);
                                // 办理过程
                                List<Map<String, Object>> handleList = flowTaskService.findBySqlFilter(filter);
                                if (handleList != null && handleList.size() > 0) {
                                    request.setAttribute("handleListValue", true);
                                }
                                request.setAttribute("handleList", handleList);
                                // 材料列表
                                Map<String, Object> itemInfo = projectWebsiteApplyService.getByJdbc(
                                        "T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" }, new Object[] { itemCode });
                                List<Map<String, Object>> applyMaters = new ArrayList<Map<String, Object>>();
                                if (itemInfo != null && itemInfo.size() > 0) {
                                    applyMaters = applyMaterService.findByItemId(itemInfo.get("ITEM_ID").toString(),
                                            exeId);
                                }
                                applyMaters = busApplyController.getFlowCaseInfo(exeInfo, request, applyMaters);
                                List<Map<String, Object>> materList = new ArrayList<Map<String, Object>>();
                                if (applyMaters != null && applyMaters.size() > 0) {
                                    for (Map<String, Object> applyMater : applyMaters) {
                                        int YJZZCL = 0;// 应交纸质材料
                                        int YJDZCL = 0;// 应交电子材料
                                        int SJZZCL = 0;// 实交纸质材料
                                        int SJDZCL = 0;// 实交电子材料
                                        List<Map<String, Object>> uploadFiles = (List<Map<String, Object>>) applyMater
                                                .get("uploadFiles");
                                        for (Map<String, Object> uploadFile : uploadFiles) {
                                            if (uploadFile.get("SQFS") != null) {// 附件收取方式
                                                String SQFS = uploadFile.get("SQFS").toString();
                                                if ("1".equals(SQFS)) {
                                                    YJDZCL++;
                                                    SJDZCL++;
                                                } else {
                                                    YJZZCL++;
                                                    SJZZCL++;
                                                }
                                            }
                                        }
                                        applyMater.put("YJZZCL", YJZZCL);
                                        applyMater.put("YJDZCL", YJDZCL);
                                        applyMater.put("SJZZCL", SJZZCL);
                                        applyMater.put("SJDZCL", SJDZCL);
                                        materList.add(applyMater);
                                    }
                                }
                                if (materList != null && materList.size() > 0) {
                                    request.setAttribute("applyMatersValue", true);
                                }
                                request.setAttribute("materList", materList);
                                // 事项信息
                                Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
                                request.setAttribute("serviceItem", serviceItem);
                            }
                            request.setAttribute("exeInfo", exeInfo);
                        }
                    } else {
                        request.setAttribute("returnInfo", "凭证信息不正确");
                    }
                } else {
                    request.setAttribute("returnInfo", "凭证信息不正确");
                }
            } else {
                request.setAttribute("returnInfo", "凭证信息不正确");
            }
        } else {
            request.setAttribute("returnInfo", "凭证信息不能为空");
        }

        return new ModelAndView("project/approveItem/projectDetail");
    }
    
    /**
     * 删除上传的文件
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @ResponseBody
    @RequestMapping(params = "delUpload")
    public AjaxJson delUpload(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String fileId = request.getParameter("selectColNames");
        if(fileId!=null && !"".equals(fileId)){
            projectWebsiteApplyService.removeUploadFile(fileId);
            j.setMsg("删除成功");
        }
        return j;
    }
    /**
     * 获取访问凭证（无访问参数，token值为定值）
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAccessToken")
    public  Map<String, Object> getAccessToken() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Properties projectProperties = FileUtil.readProperties("conf/config.properties");
        String accessToken = projectProperties.getProperty("accessToken");
        if(accessToken!=null && !"".equals(accessToken)) {
            resultMap.put("code", "00");
            resultMap.put("resultInfo", accessToken);
        }else {
            resultMap.put("code", "99");
            resultMap.put("resultInfo", "accessToken为空，请联系管理员");
        }
        return resultMap;
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping("/getAccessTokenByParam")
    public Map<String, Object> getAccessTokenByParam(String user, String password) {
        // 用户名：admin 密码：lin12345
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (user != null && !"".equals(user)) {
            if (!"admin".equals(user)) {
                resultMap.put("result", false);
                resultMap.put("fail_info", "用户名不正确");
                return resultMap;
            }
        } else {
            resultMap.put("result", false);
            resultMap.put("fail_info", "用户名不能为空");
            return resultMap;
        }
        if (password != null && !"".equals(password)) {
            if (!"lin12345".equals(password)) {
                resultMap.put("result", false);
                resultMap.put("fail_info", "密码不正确");
                return resultMap;
            }
        } else {
            resultMap.put("result", false);
            resultMap.put("fail_info", "密码不能为空");
            return resultMap;
        }
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowDate = new Date();
        tokenMap.put("token_user", user);
        tokenMap.put("token_password", password);
        String maxSortNumber = projectWebsiteApplyService.findMaxTokenSortNumber();
        int sortNumber = 0;
        if (maxSortNumber != null && !"".equals(maxSortNumber)) {
            sortNumber = Integer.valueOf(maxSortNumber);
        }
        sortNumber++;
        tokenMap.put("sort_number", sortNumber);
        tokenMap.put("create_time", sdf.format(nowDate));
        String tokenId = projectWebsiteApplyService.saveOrUpdate(tokenMap, "T_MSJW_SYSTEM_TOKEN", null);
        if (tokenId != null && !"".equals(tokenId)) {
            resultMap.put("result", true);
            resultMap.put("access_token", sortNumber + "_" + tokenId);
        } else {
            resultMap.put("result", true);
            resultMap.put("fail_info", "token值为空，请联系管理员");
        }
        return resultMap;
    }
}
