/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
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
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.ProjectSgxkService;
import net.evecom.platform.wsbs.service.ProjectService;

/**
 * 
 * 描述：工程建设施工许可
 * @author Rider Chen
 * @created 2019年12月7日 下午6:42:09
 */
@Controller
@RequestMapping("/projectSgxkController")
public class ProjectSgxkController extends BaseController {

    /**
     * projectService
     */
    @Resource
    private ProjectService projectService;
    
    /**
     * projectSgxkService
     */
    @Resource
    private ProjectSgxkService projectSgxkService;
    
    /**
     * 跳转到施工图审查合格证书编号界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshChartreviewnumDiv")
    public ModelAndView refreshChartreviewnumDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/chartreviewnumDiv");
    }
    /**
     * 跳转到建设单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJsdwDiv")
    public ModelAndView refreshJsdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/jsdwDiv");
    }
    /**
     * 跳转到代建、工程总承包（EPC）、PPP等单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshDjdwDiv")
    public ModelAndView refreshDjdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/djdwDiv");
    }
    /**
     * 跳转到监理单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJldwDiv")
    public ModelAndView refreshJldwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/jldwDiv");
    }
    /**
     * 跳转到勘察单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshKcdwDiv")
    public ModelAndView refreshKcdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/kcdwDiv");
    }
    /**
     * 跳转到设计单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshSjdwDiv")
    public ModelAndView refreshSjdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/sjdwDiv");
    }
    /**
     * 跳转到施工图审查单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshSgtscdwDiv")
    public ModelAndView refreshSgtscdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/sgtscdwDiv");
    }
    /**
     * 跳转到控制价（预算价）计价文件编制单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshKzjdwDiv")
    public ModelAndView refreshKzjdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/kzjdwDiv");
    }

    /**
     * 跳转到检测单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJcdwDiv")
    public ModelAndView refreshJcdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/jcdwDiv");
    }
    /**
     * 跳转到招标代理单位界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshZbdwDiv")
    public ModelAndView refreshZbdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/zbdwDiv");
    }
    /**
     * 跳转到单位工程界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshDwgcDiv")
    public ModelAndView refreshDwgcDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/dwgcDiv");
    }
    /**
     * 跳转到桩基工程界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshZjgcDiv")
    public ModelAndView refreshZjgcDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/zjgcDiv");
    }    
    
    /**
     * 跳转到上部(地下室)工程界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshSbgcDiv")
    public ModelAndView refreshSbgcDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/sbgcDiv");
    }     
    
    
    /**
     * 跳转到施工人员界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshSgryDiv")
    public ModelAndView refreshSgryDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String CORPNAME = request.getParameter("CORPNAME");
        request.setAttribute("CORPNAME", CORPNAME);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/sgryDiv");
    }
    /**
     * 跳转到施工人员信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addSgry")
    public ModelAndView addSgry(HttpServletRequest request) {
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String info = request.getParameter("info");
        if (StringUtils.isNotEmpty(info)) {
            Map<String, Object> map = JSON.parseObject(info, Map.class);
            request.setAttribute("info", map);
        }
        request.setAttribute("index", index);
        request.setAttribute("type", type);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/addSgry");
    }  
    /**
     * 跳转到监理人员界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJlryDiv")
    public ModelAndView refreshJlryDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String CORPNAME = request.getParameter("CORPNAME");
        request.setAttribute("CORPNAME", CORPNAME);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/jlryDiv");
    }
    /**
     * 跳转到监理人员信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addJlry")
    public ModelAndView addJlry(HttpServletRequest request) {
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String info = request.getParameter("info");
        if (StringUtils.isNotEmpty(info)) {
            Map<String, Object> map = JSON.parseObject(info, Map.class);
            request.setAttribute("info", map);
        }
        request.setAttribute("index", index);
        request.setAttribute("type", type);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/addJlry");
    }

    /**
     * 跳转到单位信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addDwInfo")
    public ModelAndView addDwInfo(HttpServletRequest request) {
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String formName = request.getParameter("formName");
        String info = request.getParameter("info");
        String YW_ID = request.getParameter("YW_ID");
        if (StringUtils.isNotEmpty(info)) {
            Map<String, Object> map = JSON.parseObject(info, Map.class);
            request.setAttribute("info", map);
            String childrendwgcJson = map.get("CHILDRENDWGC_JSON") == null ? ""
                    : map.get("CHILDRENDWGC_JSON").toString();
            if (StringUtils.isNotEmpty(childrendwgcJson)) {
                List<Map<String, Object>> childrendwgcList = JSON.parseObject(childrendwgcJson, List.class);
                for (Map<String, Object> map2 : childrendwgcList) {
                    String mapjson = JSON.toJSONString(map2);
                    map2.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
                }
                request.setAttribute("childrendwgcList", childrendwgcList);
            }
        } else if (StringUtils.isNotEmpty(YW_ID)) {
            Map<String, Object> sgxk = projectService.getByJdbc("T_BSFW_GCJSSGXK", new String[] { "YW_ID" },
                    new Object[] { YW_ID });
            if (null != sgxk && sgxk.size() > 0) {
                String DWGC_JSON = sgxk.get("DWGC_JSON") == null ? "" : sgxk.get("DWGC_JSON").toString();
                if (StringUtils.isNotEmpty(DWGC_JSON)) {
                    List<Map<String, Object>> dwgcList = JSON.parseObject(DWGC_JSON, List.class);
                    if (null != dwgcList && dwgcList.size() > 0) {
                        Map<String, Object> map = dwgcList.get(Integer.parseInt(index));
                        request.setAttribute("info", map);
                        String childrendwgcJson = map.get("CHILDRENDWGC_JSON") == null ? ""
                                : map.get("CHILDRENDWGC_JSON").toString();
                        if (StringUtils.isNotEmpty(childrendwgcJson)) {
                            List<Map<String, Object>> childrendwgcList = JSON.parseObject(childrendwgcJson, List.class);
                            for (Map<String, Object> map2 : childrendwgcList) {
                                String mapjson = JSON.toJSONString(map2);
                                map2.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
                            }
                            request.setAttribute("childrendwgcList", childrendwgcList);
                        }
                    }
                }
            }

        }
        request.setAttribute("index", index);
        request.setAttribute("type", type);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/gcjsxm/gcjssgxk/children/" + formName);
    }
    
    /**
     * 跳转到桩基工程信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addZjInfo")
    public ModelAndView addZjInfo(HttpServletRequest request) {
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String formName = request.getParameter("formName");
        String info = request.getParameter("info");
        String YW_ID = request.getParameter("YW_ID");
        if (StringUtils.isNotEmpty(info)) {
            Map<String, Object> map = JSON.parseObject(info, Map.class);
            request.setAttribute("info", map);
            String childrenzjgcJson = map.get("CHILDRENZJGC_JSON") == null ? ""
                    : map.get("CHILDRENZJGC_JSON").toString();
            if (StringUtils.isNotEmpty(childrenzjgcJson)) {
                List<Map<String, Object>> childrenzjgcList = JSON.parseObject(childrenzjgcJson, List.class);
                for (Map<String, Object> map2 : childrenzjgcList) {
                    String mapjson = JSON.toJSONString(map2);
                    map2.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
                }
                request.setAttribute("childrenzjgcList", childrenzjgcList);
            }
        } else if (StringUtils.isNotEmpty(YW_ID)) {
            Map<String, Object> sgxk = projectService.getByJdbc("T_BSFW_GCJSSGXK", new String[] { "YW_ID" },
                    new Object[] { YW_ID });
            if (null != sgxk && sgxk.size() > 0) {
                String ZJGC_JSON = sgxk.get("ZJGC_JSON") == null ? "" : sgxk.get("ZJGC_JSON").toString();
                if (StringUtils.isNotEmpty(ZJGC_JSON)) {
                    List<Map<String, Object>> zjgcList = JSON.parseObject(ZJGC_JSON, List.class);
                    if (null != zjgcList && zjgcList.size() > 0) {
                        Map<String, Object> map = zjgcList.get(Integer.parseInt(index));
                        request.setAttribute("info", map);
                        String childrenzjgcJson = map.get("CHILDRENZJGC_JSON") == null ? ""
                                : map.get("CHILDRENZJGC_JSON").toString();
                        if (StringUtils.isNotEmpty(childrenzjgcJson)) {
                            List<Map<String, Object>> childrenzjgcList = JSON.parseObject(childrenzjgcJson, List.class);
                            for (Map<String, Object> map2 : childrenzjgcList) {
                                String mapjson = JSON.toJSONString(map2);
                                map2.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
                            }
                            request.setAttribute("childrenzjgcList", childrenzjgcList);
                        }
                    }
                }
            }

        }
        request.setAttribute("index", index);
        request.setAttribute("type", type);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/gcjsxm/gcjssgxk/children/" + formName);
    }    
    
    /**
     * 跳转到上部工程信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addSbInfo")
    public ModelAndView addSbInfo(HttpServletRequest request) {
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String formName = request.getParameter("formName");
        String info = request.getParameter("info");
        String YW_ID = request.getParameter("YW_ID");
        if (StringUtils.isNotEmpty(info)) {
            Map<String, Object> map = JSON.parseObject(info, Map.class);
            request.setAttribute("info", map);
            String childrensbgcJson = map.get("CHILDRENSBGC_JSON") == null ? ""
                    : map.get("CHILDRENSBGC_JSON").toString();
            if (StringUtils.isNotEmpty(childrensbgcJson)) {
                List<Map<String, Object>> childrensbgcList = JSON.parseObject(childrensbgcJson, List.class);
                for (Map<String, Object> map2 : childrensbgcList) {
                    String mapjson = JSON.toJSONString(map2);
                    map2.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
                }
                request.setAttribute("childrensbgcList", childrensbgcList);
            }
        } else if (StringUtils.isNotEmpty(YW_ID)) {
            Map<String, Object> sgxk = projectService.getByJdbc("T_BSFW_GCJSSGXK", new String[] { "YW_ID" },
                    new Object[] { YW_ID });
            if (null != sgxk && sgxk.size() > 0) {
                String SBGC_JSON = sgxk.get("SBGC_JSON") == null ? "" : sgxk.get("SBGC_JSON").toString();
                if (StringUtils.isNotEmpty(SBGC_JSON)) {
                    List<Map<String, Object>> sbgcList = JSON.parseObject(SBGC_JSON, List.class);
                    if (null != sbgcList && sbgcList.size() > 0) {
                        Map<String, Object> map = sbgcList.get(Integer.parseInt(index));
                        request.setAttribute("info", map);
                        String childrensbgcJson = map.get("CHILDRENSBGC_JSON") == null ? ""
                                : map.get("CHILDRENSBGC_JSON").toString();
                        if (StringUtils.isNotEmpty(childrensbgcJson)) {
                            List<Map<String, Object>> childrensbgcList = JSON.parseObject(childrensbgcJson, List.class);
                            for (Map<String, Object> map2 : childrensbgcList) {
                                String mapjson = JSON.toJSONString(map2);
                                map2.put("ROW_JSON", StringEscapeUtils.escapeHtml3(mapjson));
                            }
                            request.setAttribute("childrensbgcList", childrensbgcList);
                        }
                    }
                }
            }

        }
        request.setAttribute("index", index);
        request.setAttribute("type", type);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/gcjsxm/gcjssgxk/children/" + formName);
    }
    
    
    
    /**
     * 
     * 描述：获取报建编号
     * @author Rider Chen
     * @created 2020年4月23日 下午2:47:12
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPrjCode")
    public  Map<String, Object> getPrjCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String XZQHCode = request.getParameter("XZQHCode");
        String codetype = request.getParameter("codetype");
        if(StringUtils.isEmpty(XZQHCode)||StringUtils.isEmpty(codetype)){
            resultMap.put("status", false);
            resultMap.put("msg", "区划代码和代码类型为空"); 
        } else{
            String prjCode = projectService.getPrjCode(XZQHCode, codetype);
            if(StringUtils.isNotEmpty(prjCode)) {
                resultMap.put("status", true);
                resultMap.put("code", prjCode);
            }else {
                resultMap.put("status", false);
                resultMap.put("msg", "返回编号为空，请联系管理员");
            }
        }
        return resultMap;
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "enterpriseSelector")
    public ModelAndView enterpriseSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/enterpriseSelector");
    }
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "enterpriseDatagrid")
    public void enterpriseDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String qyname = variables.get("qyname") == null ? "" : variables.get("qyname").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(qyname)){ 
            Map<String, Object> params = new HashMap<>();
            Map<String, String> headMap = new HashMap<>();
            Properties properties = FileUtil.readProperties("project.properties");
            params.put("name", qyname);
            params.put("areaCode", "350128");
            String header = getZxAccessToken();
            headMap.put("Authorization", header);
            String url = properties.getProperty("ZX_ENTERPRISE_URL");
            String result = HttpSendUtil.sendPostParamsH(url, headMap, params);
            if (StringUtils.isNotEmpty(result)) {
                Map<String, Object> map = JSON.parseObject(result, Map.class);
                String code = map.get("code")==null?"":map.get("code").toString();
                if(StringUtils.isNotEmpty(code) && code.equals("01")){//调用成功/调用成功,无数据
                    list = (List<Map<String, Object>>) map.get("data");
                }
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:获取省网总线accesstoken
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 13:03:00
     */
    private String getZxAccessToken() {
        Properties properties = FileUtil.readProperties("project.properties");
        String zxAccessTokenUrl = properties.getProperty("ZX_ACCESS_TOKEN_URL");
        String grant_type = properties.getProperty("ZX_GRANT_TYPE");
        String client_id = properties.getProperty("ZX_CLIENT_ID");
        String client_secret = properties.getProperty("ZX_CLIENT_SECRET");
        HashMap<String, Object> params = new HashMap<>();
        params.put("grant_type", grant_type);
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        String result = HttpSendUtil.sendPostParams(zxAccessTokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        String tokenType = jsonObject.getString("token_type");
        return tokenType + " " + accessToken;
    }
    
    /**
     * 跳转到子单位信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "addDwgcChildrenInfo")
    public ModelAndView addDwgcChildrenInfo(HttpServletRequest request) {
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String formName = request.getParameter("formName");
        String info = request.getParameter("info");
        String sgdwCorpName = request.getParameter("sgdwCorpName");
        String jldwCorpName = request.getParameter("jldwCorpName");
        if (StringUtils.isNotEmpty(info)) {
            Map<String, Object> map = JSON.parseObject(info, Map.class);
            request.setAttribute("info", map);
        }
        request.setAttribute("index", index);
        request.setAttribute("type", type);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        if(StringUtils.isNotEmpty(sgdwCorpName)){
            request.setAttribute("sgdwCorpName", sgdwCorpName.split(","));
        }
        if(StringUtils.isNotEmpty(jldwCorpName)){
            request.setAttribute("jldwCorpName", jldwCorpName.split(","));
        }
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/children/"+formName);
    }
    
    
    /**
     * 跳转桩基工程界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshZjgcTabDiv")
    public ModelAndView refreshZjgcTabDiv(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/gcjsxm/gcjssgsk/zjgc");
    }    
    
    /**
     * 
     * @Description 获取部四库一平台施工许可证编号
     * @author Luffy Cai
     * @date 2020年11月19日
     * @param request
     * @return Map<String,Object>
     */
    @ResponseBody
    @RequestMapping("/getConstrnum")
    public Map<String, Object> getConstrnum(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String proNum = request.getParameter("proNum");
        if (StringUtils.isEmpty(proNum)) {
            result.put("status", false);
            result.put("msg", "缺失必要参数");
        } else {
            String constrnum = projectService.getConstrnum(proNum);
            result.put("status", true);
            result.put("constrnum", constrnum);
        }
        return result;
    }
    
    
    /**
     * 
     * @Description 获取电子证照施工许可证编号
     * @author Luffy Cai
     * @date 2020年11月19日
     * @param request
     * @return Map<String,Object>
     */
    @ResponseBody
    @RequestMapping("/getCertNum")
    public Map<String, Object> getCertNum(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String proType = request.getParameter("proType");
        if (StringUtils.isEmpty(proType)) {
            result.put("status", false);
            result.put("msg", "缺失必要参数");
        } else {
            String certNum = projectService.getCertNum(proType);
            result.put("status", true);
            result.put("certNum", certNum);
        }
        return result;
    }
    
    /**
     * 
     * @Description 获取电子证照施工许可证信息
     * @author Luffy Cai
     * @date 2021年4月1日
     * @param request
     * @return Map<String,Object>
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/getCertificate")
    public Map<String, Object> getCertificate(HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        String ywId = request.getParameter("YW_ID");
        String exeId = request.getParameter("EXE_ID");
        if (StringUtils.isEmpty(ywId)) {
            result.put("status", false);
            result.put("msg", "缺失必要参数");
        } else {
            Map<String, Object> sgxk = projectService.getByJdbc("T_BSFW_GCJSSGXK", new String[] { "YW_ID" },new Object[] {ywId});            
            sgxk.put("EXE_ID", exeId);
            result = projectSgxkService.getCertificatePreviewUrl(sgxk);
        }
        return result;
    }    
    
}
