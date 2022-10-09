/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQueryService;
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
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述 不动产接口Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/bdcQueryController")
public class BdcQueryController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcQueryController.class);
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 接口调用通用
     */
    @RequestMapping(params = "queryBdc")
    @ResponseBody
    public AjaxJson queryBdc(HttpServletRequest request){
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String url = request.getParameter("URL");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, url);
        return ajaxJson;
    }
    
    /**
     * 不动产委托备案接口服务
     */
    @RequestMapping(params = "testEntruste")
    @ResponseBody
    public AjaxJson testEntruste(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //不动产单元号，三个参数任意赋值一个不为空就行
        map.put("bdcdyh", "351001015003GB00007F00020025");
        //房屋编码
        map.put("fwbm", "");
        //不动产权证号
        map.put("bdcqzh", "");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "entrusteUrl");
        return ajaxJson;
    }
    /**
     * 不动产预告档案查询接口服务
     */
    @RequestMapping(params = "testAnnounce")
    @ResponseBody
    public AjaxJson testAnnounce(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //不动产单元号，8个参数任意赋值一个不为空就行
        map.put("bdcdyh", "351001012009GB00571F00070349");
        //不动产登记证明号
        map.put("bdcdjzmh", "");
        //权利人姓名
        map.put("qlr", "");
        //权利人证件号码
        map.put("qlrzjh", "");
        //义务人
        map.put("ywr", "");
        //义务人证件号码
        map.put("ywrzjh", "");
        //业务号
        map.put("ywh", "");
        //坐落
        map.put("bdczl", "");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "announceUrl");
        return ajaxJson;
    }
    /**
     * 不动产委托备案接口服务
     */
    @RequestMapping(params = "testContract")
    @ResponseBody
    public AjaxJson testContract(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //买卖合同号，8个参数任意赋值一个不为空就行
        map.put("fwmmhth", "");
        //买方姓名
        map.put("msfxm", "");
        //买方证件号
        map.put("msfzjhm", "");
        //卖方姓名
        map.put("zrfxm", "");
        //房屋坐落
        map.put("zl", "平潭综合实验区兴港中路（原金井二路）西侧，诚意路（原天大山北路）南侧正荣·悦玺3#、3-1#楼1903室");
        //预售许可证号
        map.put("ysxkzbh", "");
        //项目名称
        map.put("xmmc", "");
        //备案类型
        map.put("balx", "");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "contractUrl");
        return ajaxJson;
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
        bdcQueryService.remove("T_BDC_QUERY", "ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 不动产接口记录", SysLogService.OPERATE_TYPE_DEL);
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
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> bdcQuery = bdcQueryService.getByJdbc("T_BDC_QUERY", new String[] { "ID" },
                    new Object[] { entityId });
            request.setAttribute("bdcQuery", bdcQuery);
        }
        return new ModelAndView("bdc/bdcQuery/info");
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
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = bdcQueryService.saveOrUpdate(variables, "T_BDC_QUERY", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 不动产接口记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 不动产接口记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 不动产登记查询
     * 
     * @param request
     * @return
     */
    @RequestMapping("/bdcIndex")
    public ModelAndView bdcIndex(HttpServletRequest request) {
        // 拼接报文
        Map<String, Object> param = new HashMap<String, Object>();
        String url = "";
        // 今日统计
        url = "Report/TodayStat/?";
        Map<String, Object> todayStat = getResult(url, param, "GET");
        request.setAttribute("todayStat", todayStat);

        // 商品住宅月销售排行
        url = "Report/GoodsTopMonthSell/?";
        param = new HashMap<String, Object>();
        param.put("PageSize", 5);
        param.put("PageNumber", 1);
        Map<String, Object> goodsTopMonthSell = getResult(url, param, "POST");
        request.setAttribute("goodsTopMonthSell", goodsTopMonthSell);

        // 可售楼盘统计
        url = "Building/CanSaleStat/?";
        param = new HashMap<String, Object>();
        Map<String, Object> CanSaleStat = getResult(url, param, "GET");
        request.setAttribute("CanSaleStat", CanSaleStat);

        // 预售公示信息查询
        url = "Project/PreSalePublicInfo/?";
        param = new HashMap<String, Object>();
        param.put("PageSize", 5);
        param.put("PageNumber", 1);
        Map<String, Object> PreSalePublicInfo = getResult(url, param, "POST");
        request.setAttribute("PreSalePublicInfo", PreSalePublicInfo);

        return new ModelAndView("website/bdc/bdcIndex");
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年5月22日 上午11:02:26
     * @param url
     * @param param
     * @param type
     * @return
     */
    private Map<String, Object> getResult(String url, Map<String, Object> param, String type) {
        String url_param = getUrlParam(url);
        String paramJson = "";
        if (null != param && param.size() > 0) {
            paramJson = JSON.toJSONString(param);
        }
        String result = "";
        if (StringUtils.isNotEmpty(type) && type.equals("GET")) {
            result = HttpRequestUtil.sendBdcGet(url_param, paramJson);
        } else {
            result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        }
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        return resultMap;
    }

    /**
     * 可售楼盘查询
     * 
     * @param request
     * @return
     */
    @RequestMapping("/kslpcx")
    public ModelAndView kslpcx(HttpServletRequest request) {
        String url = "Building/Salable/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> kslpcxMap = JSON.parseObject(result, Map.class);
        request.setAttribute("kslpcxMap", kslpcxMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/kslpcx");
    }

    /**
     * 可售楼盘统计列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/kslptjList")
    public ModelAndView kslptjList(HttpServletRequest request) {
        String url = "Building/CanSaleStatList/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/kslptjList");
    }

    /**
     * 可售楼盘统计列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ysggxx")
    public ModelAndView ysggxx(HttpServletRequest request) {
        String url = "Project/PreSalePublicInfo/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/ysggxx");
    }
    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "queryDatagrid")
    public void queryDatagrid(HttpServletRequest request,
                         HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.NAME","desc");
        List<Map<String, Object>> list = bdcQueryService.findBySqlFilter(filter);
        if(list != null) {
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }

    /**
     * 当日签约信息列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/drqyxxlb")
    public ModelAndView drqyxxlb(HttpServletRequest request) {
        String url = "Report/TodaySign/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = "";
        if (null != variables && variables.size() > 0) {
            paramJson = JSON.toJSONString(variables);
        }
        String result = HttpRequestUtil.sendBdcGet(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/drqyxxlb");
    }

    /**
     * 商品住宅单幢排行
     * 
     * @param request
     * @return
     */
    @RequestMapping("/spzzddph")
    public ModelAndView spzzddph(HttpServletRequest request) {
        String url = "Report/GoodsHouseBuildingSingleTop/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/spzzddph");
    }

    /**
     * 商品住宅销售每月行情对比
     * 
     * @param request
     * @return
     */
    @RequestMapping("/xsmyhqdb")
    public ModelAndView xsmyhqdb(HttpServletRequest request) {
        String url = "Report/ListStatMonths/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("PageSize", 12);
        variables.put("PageNumber", 1);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) resultMap.get("Items");
        if (null != items && items.size() > 0) {
            for (int i = 0; i < items.size() - 1; i++) {
                Map<String, Object> map = items.get(i);
                Map<String, Object> map1 = items.get(i + 1);
                double endUnitPrice = Double.parseDouble(map.get("UnitPrice").toString());
                double beginUnitPrice = Double.parseDouble(map1.get("UnitPrice").toString());
                double num = (endUnitPrice - beginUnitPrice) / beginUnitPrice;
                items.get(i).put("HBZJ", StringUtil.getPercentFormatDouble(num, 2));
            }
        }
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/xsmyhqdb");
    }

    /**
     * 可售楼盘查询
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ysgsxxcx")
    public ModelAndView ysgsxxcx(HttpServletRequest request) {
        String url = "Building/CanSaleStatList/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> CanSaleStatList = JSON.parseObject(result, Map.class);
        request.setAttribute("CanSaleStatList", CanSaleStatList);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/kslpcx");
    }

    /**
     * 商品住宅月销售排行榜
     * 
     * @param request
     * @return
     */
    @RequestMapping("/spzzyxsphb")
    public ModelAndView spzzyxsphb(HttpServletRequest request) {
        String url = "Report/GoodsTopMonthSell/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/spzzyxsphb");
    }

    /**
     * 商品住宅项目价格排行榜
     * 
     * @param request
     * @return
     */
    @RequestMapping("/spzzxmjgphb")
    public ModelAndView spzzxmjgphb(HttpServletRequest request) {
        String url = "Report/ListTopProjectSell/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/spzzxmjgphb");
    }

    /**
     * 项目详情
     * 
     * @param request
     * @return
     */
    @RequestMapping("/getProject")
    public ModelAndView getProject(HttpServletRequest request) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        // 项目信息
        String url = "Project/Get/?";
        String url_param = getUrlParam(url);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> xmxxMap = JSON.parseObject(result, Map.class);

        request.setAttribute("xmxxMap", xmxxMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/projectInfo");
    }

    /**
     * 项目预售信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("/getCaseProject")
    public ModelAndView getCaseProject(HttpServletRequest request) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        // 项目信息
        String url = "Project/CaseProjectInfo/?";
        String url_param = getUrlParam(url);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> xmxxMap = JSON.parseObject(result, Map.class);

        request.setAttribute("xmxxMap", xmxxMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/caseProjectInfo");
    }

    /**
     * 项目楼盘信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("/buildings")
    public ModelAndView buildings(HttpServletRequest request) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        // 楼盘ID
        String BuildingInfoID = request.getParameter("BuildingInfoID");
        variables.put("BuildingInfoID", BuildingInfoID);
        // 获取楼盘信息
        String url = "Project/BuildingInfo/?";
        String url_param = getUrlParam(url);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        
        Map<String, Object> lpxxMap = JSON.parseObject(result, Map.class);
        Map<String, Object> lpxxInfoMap = new HashMap<String, Object>();
        // 获取楼盘信息
        lpxxInfoMap = getLpxxInfoMap(lpxxMap, lpxxInfoMap);

        String ProjectID = request.getParameter("ProjectID");
        String CaseID = request.getParameter("CaseID");
        Map<String, Object> xmxxMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(CaseID)){
            // 项目信息
            url = "Project/CaseProjectInfo/?";
            url_param = getUrlParam(url);
            result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
            xmxxMap = JSON.parseObject(result, Map.class);            
        }else if(StringUtils.isNotEmpty(ProjectID)){
            // 项目信息
            url = "Project/Get/?";
            url_param = getUrlParam(url);
            result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
            xmxxMap = JSON.parseObject(result, Map.class);  
        }

        int totalUnit = 0;
        int beginTotalFloor = 0;
        List<Map<String, Object>> unitList = new ArrayList<Map<String, Object>>();
        List<Object> unitNum = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(result)) {
            // houseMap = JSON.parseObject(result, Map.class);
            if (null != lpxxMap) {
                Map<String, Object> returnDate = (Map<String, Object>) lpxxMap.get("ReturnData");
                List<Map<String, Object>> houseList = (List<Map<String, Object>>) returnDate.get("HouseCenterDisplay");
                int totalFloor = 0;
                if (null != houseList && houseList.size() > 0) {
                    // 获取单元个数
                    for (Map<String, Object> map : houseList) {
                        String unit = map.get("Unit") == null ? "1" : map.get("Unit").toString();
                        if (!unitNum.contains(unit)) {
                            unitNum.add(unit);
                        }
                    }
                    // 获取每个单元有多少个单元号以及总共多少个单元号
                    totalUnit = getUnit(totalUnit, unitList, unitNum, houseList, totalFloor);
                    // 总 层 数
                    totalFloor = houseList.get(0).get("TotalFloor") == null ? 0 : Integer.parseInt(houseList.get(0)
                            .get("TotalFloor").toString());
                    for (Map<String, Object> map : houseList) {
                        // 所 在 层
                        String foloor = map.get("OnFloor") == null ? "" : map.get("OnFloor").toString();
                        String[] onFloors = foloor.split("-");
                        if (null != onFloors && onFloors.length == 2 && StringUtils.isNotEmpty(onFloors[0])) {
                            if (beginTotalFloor > Integer.parseInt(onFloors[0])) {
                                beginTotalFloor = Integer.parseInt(onFloors[0]);
                            }
                        } else {
                            if (beginTotalFloor > Integer.parseInt(foloor)) {
                                beginTotalFloor = Integer.parseInt(foloor);
                            }
                        }

                    }
                    if (beginTotalFloor < 0) {
                        totalFloor = totalFloor + beginTotalFloor;
                    }
                }
                List<List<Map<String, Object>>> newHouseList = new ArrayList<List<Map<String, Object>>>();
                List<String> houseIdList = new ArrayList<String>();
                setHouseList(beginTotalFloor, houseList, totalFloor, newHouseList, houseIdList, unitList, totalUnit);
                request.setAttribute("houseList", newHouseList);
            }
        }
        request.setAttribute("ProjectID", ProjectID);
        request.setAttribute("CaseID", CaseID);
        request.setAttribute("unitList", unitList);
        request.setAttribute("totalUnit", totalUnit);
        request.setAttribute("lpxxInfoMap", lpxxInfoMap);
        request.setAttribute("xmxxMap", xmxxMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/xmlpInfo");
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年5月26日 下午2:07:04
     * @param totalUnit
     * @param unitList
     * @param unitNum
     * @param houseList
     * @param totalFloor
     * @return
     */
    private int getUnit(int totalUnit, List<Map<String, Object>> unitList, List<Object> unitNum,
            List<Map<String, Object>> houseList, int totalFloor) {
        Map<String, Object> unitMap = null;
        for (int i = 0; i < unitNum.size(); i++) {
            unitMap = new HashMap<String, Object>();
            String newUnit = "";
            int newUnitID = 0;
            for (Map<String, Object> map : houseList) {
                String houseUnitId = getUnitId(map.get("HouseID").toString());
                String unitID = map.get("UnitID") == null ? getUnitId(map.get("HouseID").toString(), newUnitID) : map
                        .get("UnitID").toString();
                if (StringUtils.isNotEmpty(houseUnitId) && !unitID.equals(houseUnitId) && !houseUnitId.equals("0")) {
                    unitID = houseUnitId;
                    map.put("UnitID", houseUnitId);
                }
                String unit = map.get("Unit") == null ? "1" : map.get("Unit").toString();
                if (!newUnit.equals((String) unitNum.get(i))) {
                    newUnit = (String) unitNum.get(i);
                    unitMap.put("Unit", (String) unitNum.get(i));
                }

                int unitNumer = Integer.parseInt(unitID);
                if (newUnitID < unitNumer && newUnit.equals(unit)) {
                    newUnitID = unitNumer;
                    unitMap.put("UnitID", newUnitID);
                }
            }
            totalUnit = totalFloor + newUnitID;
            unitList.add(unitMap);
        }

        // 按单元排序
        Collections.sort(unitList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                double sum0 = Double.parseDouble(arg0.get("Unit") == null ? "0" : arg0.get("Unit").toString());
                double sum1 = Double.parseDouble(arg1.get("Unit") == null ? "0" : arg1.get("Unit").toString());
                if (sum1 < sum0) {
                    return 1;
                } else if (sum1 == sum0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return totalUnit;
    }

    /**
     * 
     * 描述 根据房号获取单元号
     * 
     * @author Rider Chen
     * @created 2017年5月26日 下午5:19:34
     * @param str
     * @return
     */
    public String getUnitId(String str) {
        if (str.contains("-")) {
            str = str.split("-")[0];
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String s = m.replaceAll("").trim();
        if (s.length() >= 3) {
            return s.substring(s.length() - 2, s.length());
        } else if (s.length() > 0) {
            return s;
        } else {
            return "0";
        }
    }

    /**
     * 
     * 描述 根据房号获取单元号
     * 
     * @author Rider Chen
     * @created 2017年5月26日 下午5:19:34
     * @param str
     * @return
     */
    public String getUnitId(String str, int total) {
        if (str.contains("-")) {
            str = str.split("-")[0];
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String s = m.replaceAll("").trim();
        if (s.length() >= 3) {
            return s.substring(s.length() - 2, s.length());
        } else if (s.length() > 0) {
            return s;
        } else {
            return (0 + total) + "";
        }
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年5月26日 下午2:03:07
     * @param BuildingInfoID
     * @param lpxxMap
     * @param lpxxInfoMap
     * @return
     */
    private Map<String, Object> getLpxxInfoMap(Map<String, Object> lpxxMap, Map<String, Object> lpxxInfoMap) {
        if (null != lpxxMap && lpxxMap.size() > 0) {
            Map<String, Object> returnDate = (Map<String, Object>) lpxxMap.get("ReturnData");
            List<Map<String, Object>> list = (List<Map<String, Object>>) returnDate.get("BuildingStat");
            if (null != list && list.size() > 0) {
                lpxxInfoMap = list.get(0);
            }
        }
        return lpxxInfoMap;
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年5月26日 下午2:00:45
     * @param paramJson
     * @return
     */
    private Map<String, Object> getHousePurpose(String paramJson) {
        String url;
        String url_param;
        String result;
        url = "Project/HousePurpose/?";
        url_param = getUrlParam(url);
        result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> fwytMap = JSON.parseObject(result, Map.class);
        List<Map<String, Object>> ReturnData = (List<Map<String, Object>>) fwytMap.get("ReturnData");
        if (null != ReturnData && ReturnData.size() < 1) {
            fwytMap.put("ReturnData", null);
        }
        return fwytMap;
    }

    /**
     * 描述 定义楼层
     * 
     * @author Rider Chen
     * @created 2017年5月26日 上午9:25:48
     * @param beginTotalFloor
     * @param houseList
     * @param totalFloor
     * @param newHouseList
     * @param houseIdList
     * @param houseNum
     */
    private void setHouseList(int beginTotalFloor, List<Map<String, Object>> oldhouseList, int totalFloor,
            List<List<Map<String, Object>>> newHouseList, List<String> houseIdList, List<Map<String, Object>> unitList,
            int totalUnit) {
        List<Map<String, Object>> list;
        Map<String, Object> floorMap = null;// 定义楼层
        Map<String, Object> newHouseMap = null;// 定义每层房屋
        Map<String, Object> houseListMap = null;// 定义每层房屋
        List<Map<String, Object>> houseList = null;
        List<Map<String, Object>> unitHouseList = null;
        for (int i = totalFloor; i > beginTotalFloor; i--) {
            houseList = new ArrayList<Map<String, Object>>();
            unitHouseList = new ArrayList<Map<String, Object>>();// 每层的单元房屋集合
            for (int j = 0; j < unitList.size(); j++) {
                list = new ArrayList<Map<String, Object>>();
                Map<String, Object> unitMap = (Map<String, Object>) unitList.get(j);
                int maxUnitID = Integer
                        .parseInt(unitMap.get("UnitID") == null ? "0" : unitMap.get("UnitID").toString());
                int oldUnit = Integer.parseInt(unitMap.get("Unit") == null ? "0" : unitMap.get("Unit").toString());
                if (j > 0) {
                    maxUnitID = maxUnitID - 1;
                }
                for (int x = 0; x <= maxUnitID; x++) {
                    list.add(new HashMap<String, Object>());
                }
                floorMap = new HashMap<String, Object>();
                int num = i <= 0 ? i + beginTotalFloor : i;
                if (j == 0) {
                    floorMap.put("OnFloor", num);
                    floorMap.put("UnitID", 0);
                    list.set(0, floorMap);
                }
                for (Map<String, Object> map : oldhouseList) {
                    int onFloor = 0;// 当前楼层
                    // 跨楼层
                    int beginOnFloor = 0;// 开始楼层
                    int endOnFloor = 0;// 结束楼层
                    String foloor = map.get("OnFloor") == null ? "" : map.get("OnFloor").toString();
                    String houseID = map.get("HouseID") == null ? "" : map.get("HouseID").toString();
                    String[] onFloors = foloor.split("-");
                    if (null != onFloors && onFloors.length == 2 && StringUtils.isNotEmpty(onFloors[0])) {
                        beginOnFloor = Integer.parseInt(onFloors[0]);
                        endOnFloor = Integer.parseInt(onFloors[1]);
                    } else {
                        onFloor = Integer.parseInt(foloor);
                    }
                    // 单元号
                    int UnitID = map.get("UnitID") == null ? Integer.parseInt(getUnitId(map.get("HouseID").toString(),
                            maxUnitID)) : Integer.parseInt(map.get("UnitID").toString());
                    if (j > 0) {
                        UnitID = UnitID - 1;
                    }
                    int Unit = map.get("Unit") == null ? 1 : Integer.parseInt(map.get("Unit").toString());
                    if (num == onFloor && Unit == oldUnit) {
                        if (null != list.get(UnitID) && list.get(UnitID).size() > 0) {
                            list.add(map);
                        } else {
                            list.set(UnitID, map);
                        }
                    } else if (num >= beginOnFloor && num <= endOnFloor && Unit == oldUnit) {
                        houseListMap = new HashMap<String, Object>();
                        houseListMap.putAll(map);
                        if (null != houseIdList && !houseIdList.contains(houseID)) {
                            houseListMap.put("floorNum", endOnFloor - beginOnFloor + 1);
                            houseListMap.put("endOnFloor", endOnFloor);
                            houseListMap.put("beginOnFloor", beginOnFloor);
                            houseListMap.put("isShow", 1);
                            houseIdList.add(houseID);
                        } else if (null != houseIdList && houseIdList.contains(houseID)) {
                            houseListMap.put("isShow", 0);
                        }
                        if (null != list.get(UnitID) && list.get(UnitID).size() > 0) {
                            list.add(houseListMap);
                        } else {
                            list.set(UnitID, houseListMap);
                        }
                    }
                }
                unitHouseList.addAll(list);
            }
            newHouseMap = new HashMap<String, Object>();
            newHouseMap.put("list", unitHouseList);
            houseList.add(newHouseMap);
            newHouseList.add(houseList);

        }
    }

    /**
     * 户室信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("/houseInfo")
    public ModelAndView houseInfo(HttpServletRequest request) {
        String url = "Project/houseInfo/?";
        String url_param = getUrlParam(url);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String paramJson = JSON.toJSONString(variables);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        
        // 项目信息
        url = "Project/Get/?";
        url_param = getUrlParam(url);
        result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> xmxxMap = JSON.parseObject(result, Map.class);

        request.setAttribute("xmxxMap", xmxxMap);
        request.setAttribute("resultMap", resultMap);
        request.setAttribute("variables", variables);
        return new ModelAndView("website/bdc/houseInfo");
    }

    /**
     * 不动产登记查询
     * 
     * @param request
     * @return
     */
    @RequestMapping("/bdccx")
    public ModelAndView bdccx(HttpServletRequest request) {
        String url = "Bdc/Query/?";
        String url_param = getUrlParam(url);
        String CaseId = request.getParameter("CaseId");
        // 拼接报文
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("CaseId", CaseId);
        String paramJson = JSON.toJSONString(param);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> bdcMap = JSON.parseObject(result, Map.class);
        request.setAttribute("bdcMap", bdcMap);
        return new ModelAndView("website/bdc/bdcdjcx");
    }

    /**
     * 合同签约情况查询
     * 
     * @param request
     * @return
     */
    @RequestMapping("/hyqyqkcx")
    public ModelAndView hyqyqkcx(HttpServletRequest request) {
        String url = "Contract/SingQuery/?";
        String url_param = getUrlParam(url);
        String LicenceID = request.getParameter("LicenceID");
        String IDCard = request.getParameter("IDCard");
        String SignPwd = request.getParameter("SignPwd");
        // 拼接报文
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("LicenceID", LicenceID);
        param.put("IDCard", IDCard);
        param.put("SignPwd", SignPwd);
        String paramJson = JSON.toJSONString(param);
        String result = HttpRequestUtil.sendBdcPost(url_param, paramJson);
        Map<String, Object> bdcMap = JSON.parseObject(result, Map.class);

        request.setAttribute("bdcMap", bdcMap);
        return new ModelAndView("website/bdc/hyqyqkcx");
    }

    /**
     * 描述 获取完整的接口链接
     * 
     * @author Rider Chen
     * @created 2017年5月18日 下午5:28:42
     * @param url
     *            接口方法地址
     *            接口系统级参数
     * @return
     */
    private static String getUrlParam(String url) {
        Properties properties = FileUtil.readProperties("project.properties");
        // 获取接口地址
        String bdcUrl = properties.getProperty("bdc_url") + url;
        // 获取接口账号
        String bdcUid = properties.getProperty("bdc_uid");
        // 获取接口密码
        String bdcPwd = properties.getProperty("bdc_pwd");
        Date date = new Date();
        // 获取时间戳
        long time = getSecondTimestampTwo(date);
        // 按ASCII排序
        SortedMap<String, Object> apiParam = new TreeMap<String, Object>();
        apiParam.put("uid", bdcUid);
        apiParam.put("pwd", bdcPwd);
        apiParam.put("time", time);
        String org = getSortedStr(apiParam);
        // 获取请求签名
        String sign = StringUtil.encryptSha1(org).toUpperCase();
        apiParam.put("sign", sign);
        // 获取接口完整地址
        String urlParam = bdcUrl + getSortedStr(apiParam);
        return urlParam;
    }

    /**
     * 获取精确到秒的时间戳
     * 
     * @param date
     * @return
     */
    public static int getSecondTimestampTwo(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * 描述 获取参数
     * 
     * @author Rider Chen
     * @created 2017年5月18日 下午3:13:58
     * @param apiParam
     * @param org
     * @return
     */
    private static String getSortedStr(SortedMap<String, Object> apiParam) {
        StringBuffer org=new StringBuffer();
        Set<Entry<String, Object>> entry1 = apiParam.entrySet();
        Iterator<Entry<String, Object>> it = entry1.iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (StringUtils.isNotEmpty(org)) {
                org.append("&");
            }
            org.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return org.toString();
    }
}
