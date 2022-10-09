/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.ProjectFinishManageService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.website.service.XFDesignService;
import net.evecom.platform.wsbs.service.ProjectService;

/**
 * 描述 竣工验收备案信息Controller
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月17日 上午10:43:15
 */
@Controller
@RequestMapping("/projectFinishManageController")
public class ProjectFinishManageController extends BaseController {
    /**
     * 引入Service
     */
    @Resource
    private ProjectFinishManageService projectFinishManageService;

    /**
     * projectService
     */
    @Resource
    private ProjectService projectService;
    
    /**
     * xfDesignService
     */
    @Resource
    private XFDesignService xfDesignService;
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 竣工验收事项列表
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "projectFinishItemData")
    public void projectFinishItemData(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String projectCode = request.getParameter("projectCode");
        String itemCode = request.getParameter("itemCode");
        /*
         * if (projectCode == null || "".equals(projectCode)) { projectCode =
         * "2019-350128-50-01-080522";// 测试数据 }
         */
        if (projectCode != null && !"".equals(projectCode)) {
            Map<String, Object> exeMap = projectFinishManageService.findExeInfo(projectCode, itemCode);
            if (exeMap != null && exeMap.size() > 0) {
                String status = exeMap.get("RUN_STATUS").toString();
                if(status.equals("2")) {
                    String ywid = exeMap.get("BUS_RECORDID").toString();
                    String tableName = exeMap.get("BUS_TABLENAME").toString();
                    // 获取施工许可信息
                    Map<String, Object> sgxkMap  = projectFinishManageService.getByJdbc(tableName, new String[] { "YW_ID" },
                            new Object[] { ywid });
                    if (sgxkMap != null && sgxkMap.size() > 0) {
                        if (sgxkMap.get("SBMC") != null) {
                            sgxkMap.remove("SBMC");
                        }
                        result.put("success", true);
                        result.put("data", sgxkMap);  
                    }                
                }else {//施工许可未办结
                    result.put("success", false);
                    result.put("code", "-1");
                    result.put("msg", "建设工程施工许可证核发事项未办结");  
                }
            }
        } else {
            result.put("success", false);
            result.put("code", "0");//无施工许可证核发事项
            result.put("msg", "未查询到施工许可信息");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 参建各方责任主体列表信息
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "cjgfzrztList")
    public void cjgfzrztList(HttpServletRequest request, HttpServletResponse response) {
        String ywId = request.getParameter("ywId");
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (ywId != null && !"".equals(ywId)) {
            Map<String, Object> resultMap = projectFinishManageService.getByJdbc("TB_PROJECT_FINISH_MANAGE",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (resultMap.get("CJGFZRZT_JSON") != null && !"[]".equals(resultMap.get("CJGFZRZT_JSON").toString())) {
                String cjgfzrztJson = resultMap.get("CJGFZRZT_JSON").toString();
                resultList = JSON.parseObject(cjgfzrztJson, List.class);
            }
        }
        if (resultList == null || resultList.size() == 0) {
            String projectCode = request.getParameter("projectCode");
            if (projectCode != null && !"".equals(projectCode)) {
                Map<String, Object> exeMap = projectFinishManageService.findExeInfo(projectCode, null);
                if (exeMap != null && exeMap.size() > 0 && exeMap.get("RUN_STATUS").toString().equals("2")) {
                    String ywid = exeMap.get("BUS_RECORDID").toString();
                    String tableName = exeMap.get("BUS_TABLENAME").toString();
                    // 获取施工许可信息
                    Map<String, Object> sgxkMap = projectFinishManageService.getByJdbc(tableName,
                            new String[] { "YW_ID" }, new Object[] { ywid });
                    if (sgxkMap != null && sgxkMap.size() > 0) {
                        if (sgxkMap.get("CJGFZRZT_JSON") != null && !"".equals(sgxkMap.get("CJGFZRZT_JSON").toString())
                                && !"[]".equals(sgxkMap.get("CJGFZRZT_JSON").toString())) {
                            String CJGFZRZT_JSON = sgxkMap.get("CJGFZRZT_JSON").toString();
                            resultList = JSON.parseObject(CJGFZRZT_JSON, List.class);
                        } else {
                            if (sgxkMap.get("SGDW_JSON") != null) {
                                String SGDW_JSON = sgxkMap.get("SGDW_JSON").toString();
                                resultList = formatJson(resultList, SGDW_JSON, "SGDW_JSON");
                            }
                            if (sgxkMap.get("JLDW_JSON") != null) {
                                String JLDW_JSON = sgxkMap.get("JLDW_JSON").toString();
                                resultList = formatJson(resultList, JLDW_JSON, "JLDW_JSON");
                            }
                            if (sgxkMap.get("KCDW_JSON") != null) {
                                String KCDW_JSON = sgxkMap.get("KCDW_JSON").toString();
                                resultList = formatJson(resultList, KCDW_JSON, "KCDW_JSON");
                            }
                            if (sgxkMap.get("SJDW_JSON") != null) {
                                String SJDW_JSON = sgxkMap.get("SJDW_JSON").toString();
                                resultList = formatJson(resultList, SJDW_JSON, "SJDW_JSON");
                            }
                        }
                    }
                }
            }
        }
        
        if (resultList == null || resultList.size() == 0) {
            String projectCode = request.getParameter("projectCode");
            resultList = projectFinishManageService.findZrztList(projectCode);
            resultList = formatZrztJson(resultList);
        }        
        this.setListToJsonString(resultList.size(), resultList, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * json转换
     * 
     * @param resultList
     * @param json
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> formatJson(List<Map<String, Object>> resultList, String json, String type) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(json)) {
            List<Map<String, Object>> list = JSON.parseObject(json, List.class);
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    if ("SGDW_JSON".equals(type)) {
                        resultMap.put("cjgfzrztName", "施工总包单位");
                        resultMap.put("isEdit", false);
                    } else if ("JLDW_JSON".equals(type)) {
                        resultMap.put("cjgfzrztName", "监理单位");
                        resultMap.put("isEdit", false);
                    } else if ("KCDW_JSON".equals(type)) {
                        resultMap.put("cjgfzrztName", " 勘察单位");
                        resultMap.put("isEdit", true);
                    } else if ("SJDW_JSON".equals(type)) {
                        resultMap.put("cjgfzrztName", " 设计单位");
                        resultMap.put("isEdit", true);
                    }
                    if (map.get("CORPNAME") != null) {
                        resultMap.put("CORPNAME", map.get("CORPNAME").toString());
                    }
                    if (map.get("LEGAL_NAME") != null) {
                        resultMap.put("LEGAL_NAME", map.get("LEGAL_NAME").toString());
                    }
                    if (map.get("PERSONNAME") != null) {
                        resultMap.put("PERSONNAME", map.get("PERSONNAME").toString());
                    }
                    if (map.get("PERSONPHONE") != null) {
                        resultMap.put("PERSONPHONE", map.get("PERSONPHONE").toString());
                    }
                    resultList.add(resultMap);
                    resultMap = new HashMap<String, Object>();
                }
            }
        }
        return resultList;
    }

    /**
     * 
     * @Description 责任主体json转换
     * @author Luffy Cai
     * @date 2020年9月16日
     * @param resultList
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> formatZrztJson(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> returnList =  new ArrayList<Map<String, Object>>();
        if (resultList != null && resultList.size() > 0) {
            for (Map<String, Object> map : resultList) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                if (map.get("FC_CORP_INFO_ID") != null) {
                    resultMap.put("ID", map.get("FC_CORP_INFO_ID").toString());
                }
                if (map.get("CORP_ROLE_NUM") != null) {
                    String roleNum = map.get("CORP_ROLE_NUM").toString();
                    if(roleNum.equals("1")) {
                        resultMap.put("cjgfzrztName", "勘察企业");
                    }else if(roleNum.equals("2")) {
                        resultMap.put("cjgfzrztName", "设计企业");
                    }else if(roleNum.equals("3")) {
                        resultMap.put("cjgfzrztName", "施工企业");
                    }else if(roleNum.equals("4")) {
                        resultMap.put("cjgfzrztName", "监理企业");
                    }else if(roleNum.equals("5")) {
                        resultMap.put("cjgfzrztName", "工程总承包单位");
                    }else if(roleNum.equals("6")) {
                        resultMap.put("cjgfzrztName", "质量检测机构");
                    }else if(roleNum.equals("7")) {
                        resultMap.put("cjgfzrztName", "建设单位");
                    }else {
                        resultMap.put("cjgfzrztName", "其他");
                    }
                }
                
                if (map.get("CORP_NAME") != null) {
                    resultMap.put("CORPNAME", map.get("CORP_NAME").toString());
                }
                if (map.get("LEGAL_REPRESENT") != null) {
                    resultMap.put("LEGAL_NAME", map.get("LEGAL_REPRESENT").toString());
                }
                if (map.get("PERSON_NAME") != null) {
                    resultMap.put("PERSONNAME", map.get("PERSON_NAME").toString());
                }
                if (map.get("PERSON_PHONE") != null) {
                    resultMap.put("PERSONPHONE", map.get("PERSON_PHONE").toString());
                }
                returnList.add(resultMap);
            }
        }
        return returnList;
    } 
    
    
    /**
     * 
     * 描述：获取工程项目有关环节（文书）编号编码
     * 
     * @author Luffy Cai
     * @created 2020年4月23日 下午2:47:12
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPrjLinkCode")
    public Map<String, Object> getPrjLinkCode(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String proNum = request.getParameter("proNum");
        String codetype = request.getParameter("codetype");
        if (StringUtils.isEmpty(proNum) || StringUtils.isEmpty(codetype)) {
            result.put("status", false);
            result.put("msg", "报建编号和代码类型为空");
        } else {
            String prjCode = projectService.getGcxmCodeByProNum(proNum, codetype);
            if (StringUtils.isNotEmpty(prjCode)) {
                result.put("status", true);
                result.put("code", prjCode);
            } else {
                result.put("status", false);
                result.put("msg", "返回编号为空，请联系管理员");
            }
        }
        return result;
    }

    
    /**
     * 责任主体弹窗信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "zrztInfo")
    @SuppressWarnings("unchecked")
    public ModelAndView zrztInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String prj_num =  request.getParameter("prj_num");
        String prj_code =  request.getParameter("prj_code");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            // 责任主体信息
            Map<String, Object> zrztxxInfo = xfDesignService.getByJdbc("TB_FC_PROJECT_CORP_INFO",
                    new String[] { "FC_CORP_INFO_ID" }, new Object[] { entityId });
            if (zrztxxInfo != null && zrztxxInfo.size() > 0) {
                request.setAttribute("zrztxxValue", true);
                request.setAttribute("zrztxxInfo", zrztxxInfo);
            }
        }
        request.setAttribute("prj_num", prj_num);
        request.setAttribute("prj_code", prj_code);
        return new ModelAndView("website/applyforms/jgxk/zrztxxWindow");
    }    

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelZrzt")
    @ResponseBody
    public AjaxJson multiDelZrzt(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        xfDesignService.remove("TB_FC_PROJECT_CORP_INFO", "FC_CORP_INFO_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的责任主体信息记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
   
    /**
     * 修改或者修改操作(责任主体)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateZrzt")
    @ResponseBody
    public AjaxJson saveOrUpdateZrzt(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("FC_CORP_INFO_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("ZRZT_TYPE", "2");//责任主体类型标志 竣工验收事项：2   2020/9/16添加
        String recordId = xfDesignService.saveOrUpdate(variables, "TB_FC_PROJECT_CORP_INFO", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的责任主体信息记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的责任主体信息记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }    
    
    
}
