/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.sb.service.SbCommonService;
import net.evecom.platform.sb.service.SbQyzgzjyService;
import net.evecom.platform.sb.util.SbCommonUtil;
import net.evecom.platform.system.service.DictionaryService;
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


/**
 * 描述  企业职工基本养老保险增减员服务Controller
 * @author Allin Lin
 * @created 2020年2月14日 下午4:18:50
 */
@Controller
@RequestMapping("/sbQyzjyController")
public class SbQyzjyController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SbQyzjyController.class);
    
    
    /**
     * 引入sbCommonService
     */
    @Resource
    private SbCommonService sbCommonService;
    
    /**
     * 引入字典服务层 
     */
    @Resource
    public DictionaryService dictionaryService;
    /**
     * @Resource
     */
    @Resource
    public SbQyzgzjyService sbQyzgzjyService;
    /**
     * 描述    个人参保信息查询弹窗界面
     * @author Allin Lin
     * @created 2020年2月14日 下午4:22:30
     * @param request
     * @return
     */
    @RequestMapping(params = "personInsureSelector")
    public ModelAndView personInsureSelector(HttpServletRequest request) {
        Map<String,Object> params = BeanUtil.getMapFromRequest(request);
        String aac001 = StringUtil.getString(params.get("aac001"));
        request.setAttribute("aac001", aac001);
        String aac002 = StringUtil.getString(params.get("aac002"));
        request.setAttribute("aac002", aac002);
        return new ModelAndView("bsdt/applyform/sbqlc/qyzgzjy/personInsureSelector");
    }

    
    /**
     * 描述     单位基本信息列表(弹框)
     * @author Allin Lin
     * @created 2020年2月21日 上午10:45:33
     * @param request
     * @param response
     */
    @RequestMapping(params="findDWJBXX")
    public void findDWJBXX(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> dwxx = new HashMap<String, Object>();//单位信息
        String token = SbCommonUtil.getRealToken();//获取令牌值
        if(null!=token){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("findDWJBXX");
            log.info("单位信息查询参数："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result;
            try {
                result = SbCommonUtil.sbCommonGet(params, "dwxxcx", token);
                if("200".equals(result.get("code"))){
                    String data = result.get("data").toString();
                    if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)){
                        Map<String, Object> info = (Map<String, Object>)JSON.parse(result.get("data").toString());
                        Map<String, Object> jbxx = (Map<String, Object>)info.get("ab01");//单位基本信息
                        Map<String, Object> fjxx = (Map<String, Object>)info.get("ab01Extra");//单位附加信息
                        if(info.get("ab02s")!=null){
                            dwxx.put("cbxx", info.get("ab02s"));//单位参保信息
                            List<Map<String, Object>> cbxx = (List<Map<String, Object>>)info.get("ab02s");
                            dwxx.put("CBXX_JBJGDM",cbxx.get(0).get("aab034"));//经办机构代码
                        }
                        if(jbxx!=null){
                            dwxx.put("JBXX_DWGLM", jbxx.get("aab999"));//单位管理码
                            dwxx.put("JBXX_DWMC", jbxx.get("aab004"));//单位名称
                            dwxx.put("JBXX_DWJC", jbxx.get("bab151"));//单位简称
                            dwxx.put("JBXX_DWZT", jbxx.get("bab102"));//单位状态
                            dwxx.put("JBXX_BZ", jbxx.get("aae013"));//备注
                            dwxx.put("GJXX_ZZLX", jbxx.get("bab101"));//证照类型
                            dwxx.put("GJXX_ZZHM", jbxx.get("aab003"));//证照号码
                            dwxx.put("GJXX_DWLX", jbxx.get("aab019"));//单位类型
                            dwxx.put("GJXX_DWGLLX", jbxx.get("bab103"));//单位管理类型
                            dwxx.put("GJXX_TJLX", jbxx.get("bab107"));//统计类型
                            dwxx.put("GJXX_LSGX", jbxx.get("aab021"));//隶属关系
                            dwxx.put("GJXX_FXLB", jbxx.get("aaa149"));//行业风险类别
                            dwxx.put("GJXX_JQLX", jbxx.get("aab020"));//经济类型
                            dwxx.put("GJXX_PZCLRQ", jbxx.get("aab999"));//批准成立日期
                            dwxx.put("SWJGXX_SH", jbxx.get("aab030"));//税务电脑编码
                            dwxx.put("SWJGXX_JGDM", jbxx.get("aaz066"));//税务机构代码
                            dwxx.put("JBXX_DWBH", jbxx.get("aab001"));//单位编号
                            dwxx.put("JBXX_DWZT", jbxx.get("bab102"));//单位状态
                            dwxx.put("QYCB_CLRQ", StringUtil.getString(jbxx.get("aae047")));//成立日期
                        }
                        if(fjxx!=null){
                            dwxx.put("JBXX_ZGYXM", fjxx.get("aab016"));//专管员姓名
                            dwxx.put("JBXX_ZJHM", fjxx.get("bab165"));//专管员身份证号码
                            dwxx.put("JBXX_LXHM", fjxx.get("bab176"));//专管员联系号码
                            dwxx.put("JBXX_LXDH", fjxx.get("aae005"));//联系电话
                            dwxx.put("JBXX_DZYX", fjxx.get("aae159"));//联系电子邮箱
                            dwxx.put("JBXX_YZBM", fjxx.get("aae007"));//邮政编码
                            dwxx.put("JBXX_CZDH", fjxx.get("aae387"));//传真电话
                            dwxx.put("JBXX_DZ", fjxx.get("aae006"));//地址
                            dwxx.put("JBXX_ZZZL", fjxx.get("aab006"));//工商登记执照种类
                            dwxx.put("JBXX_ZZHM", fjxx.get("aab007"));//工商登记执照号码
                            dwxx.put("JBXX_FZRQ", fjxx.get("aab008"));//工商登记发照日期
                            dwxx.put("JBXX_YXNX", fjxx.get("aab009"));//工商登记有效年限
                            dwxx.put("JBXX_KSRQ", fjxx.get("bab155"));//工商有效期限开始日期
                            dwxx.put("JBXX_JZRQ", fjxx.get("bab156"));//工商有效期限截止日期
                            dwxx.put("GJXX_XZQY", fjxx.get("aab301"));//所属行政区域
                            dwxx.put("GJXX_SSHY", fjxx.get("aab022"));//所属行业
                            dwxx.put("GJXX_CLRQ", fjxx.get("aae049"));//成立日期
                            dwxx.put("GJXX_FRXM", fjxx.get("aab013"));//法定代表人姓名
                            dwxx.put("GJXX_FRZJLX", fjxx.get("aac058"));//法定代表人证件类型
                            dwxx.put("GJXX_FRZJHM", fjxx.get("aab014"));//法定代表人证件号码
                            dwxx.put("GJXX_FRLXDH", fjxx.get("bab157"));//法定代表人联系电话
                            dwxx.put("GJXX_ZGYXM", fjxx.get("aab016"));//专管员姓名
                            dwxx.put("GJXX_ZGYZJHM", fjxx.get("bab165"));//专管员身份证号码
                            dwxx.put("GJXX_ZGYLXHM", fjxx.get("bab176"));//专管员联系号码
                            dwxx.put("GJXX_ZGYSSBM", fjxx.get("bab162"));//专管员所属部门
                            dwxx.put("GJXX_ZGYBMFZR", fjxx.get("bab163"));//专管员所属部门负责人
                            dwxx.put("GJXX_BMFZRDH", fjxx.get("bab164"));//专管员部门负责人电话
                        }
                        list.add(dwxx);
                    }
                }
                log.info("单位基本信息接口返回结果："+JSON.toJSONString(list));
            } catch (Exception e) {
                log.error("获取社保单位基本信息和参保信息接口异常："+e.getMessage());
            }
        }else{
            log.info("获取授权令牌值失败！");
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 获取险种信息(参保登记)
     *
     * @author Allin Lin
     * @created 2019年10月11日 上午10:35:37
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "xzxxJson")
    public void xzxxJson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGZJY",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("QYJY_XZXXJSON") != null
                    && !"[]".equals(record.get("QYJY_XZXXJSON").toString())) {
                list = JSONObject.parseArray(record.get("QYJY_XZXXJSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述    个人信息列表（不弹框）
     * @author Allin Lin
     * @created 2020年3月4日 下午5:48:28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getpersonInfo")
    @ResponseBody
    public AjaxJson getpersonInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,Object> persons= Maps.newHashMap();
        List<Map<String, Object>> infos= Lists.newArrayList();
        AjaxJson ajaxJson  = new AjaxJson();
        Map<String,Object> params;//查询参数
        String token = SbCommonUtil.getRealToken();
        if(null!=token){
            params = BeanUtil.getMapFromRequest(request);
            params.remove("findDWJBXX");
            log.info("获取人员基本和参保信息："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result = SbCommonUtil.sbCommonPost(params, "personInsureInfo", token,"2");
            if ("200".equals(result.get("code"))) {
                String data = result.get("data").toString();
                if (data != null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)
                        && !"[]".equals(data)) {
                    infos = (List<Map<String, Object>>) JSON.parse(result.get("data").toString());
                    if (infos.size() > 0) {
                        Map<String, Object> personInfo = infos.get(0);
                        persons.putAll(personInfo);// 人员基本信息
                        persons.put("infos", infos);// 人员参保信息
                        ajaxJson.setJsonString(JSON.toJSONString(persons));
                        ajaxJson.setSuccess(true);
                        log.info("获取人员基本和参保信息返回结果："+JSON.toJSONString(infos));
                    }
                }else {
                    ajaxJson.setMsg("人员基本和参保信息查询为空！");
                    ajaxJson.setSuccess(false);
                }
            }else {
                ajaxJson.setMsg(result.get("message").toString());
                ajaxJson.setSuccess(false);
            }
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }

    /**
     * 描述    职工新参保数据推送社保系统
     * @author Allin Lin
     * @created 2019年11月25日 下午5:30:57
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushZgCb")
    @ResponseBody
    public AjaxJson pushZgCb(HttpServletRequest request,HttpServletResponse response){
        String ywId  = request.getParameter("ywId");
        AjaxJson ajaxJson=new AjaxJson();
        if(StringUtils.isNotEmpty(ywId)){
            ajaxJson= sbQyzgzjyService.pushZgCb(ywId);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("没有提供业务id");
        }
        return ajaxJson;
    }

    /**
     * 描述    职工减员数据推送社保系统
     * @author Allin Lin
     * @created 2019年11月25日 下午5:30:57
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushZgJy")
    @ResponseBody
    public AjaxJson pushZgJy(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            ajaxJson=sbQyzgzjyService.pushZgJy(ywId);
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("没有提供业务id");
        }
        return ajaxJson;
    }



    /**
     * 描述     获取人员基本和参保信息(弹框)
     * @author Allin Lin
     * @created 2020年2月21日 上午10:45:33
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="findPersonInsureInfo")
    public void findPersonInsureInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<Map<String, Object>> infos= Lists.newArrayList();
        String token = SbCommonUtil.getRealToken();//获取令牌值
        if(null!=token){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("findPersonInsureInfo");
            log.info("获取人员基本和参保信息："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result = SbCommonUtil.sbCommonPost(params, "personInsureInfo", token,"2");
            if("200".equals(result.get("code"))){
                String data = result.get("data").toString();
                if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)
                        && !"[]".equals(data)){
                    infos = (List<Map<String, Object>>)JSON.parse(result.get("data").toString());
                }
            }
            log.info("获取人员基本和参保信息返回结果："+JSON.toJSONString(infos));
        }else{
            log.info("获取授权令牌值失败！");
        }
        this.setListToJsonString(infos.size(), infos,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述    企业职工已参保险种信息
     * @author Allin Lin
     * @created 2020年2月18日 上午9:47:30
     * @param request
     * @param response
     */
    @RequestMapping(params = "findXZXX")
    public void findXZXX(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGBGDJ", 
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("XZXX_JSON") != null) {
                list = (List<Map<String, Object>>)JSON.parse(record.get("XZXX_JSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 获取险种信息(参保登记)
     *
     * @author Allin Lin
     * @created 2019年10月11日 上午10:35:37
     * @param request
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "personXzxxJson")
    public void personXzxxJson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGZJY",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("QYCB_RYCBXXJSON") != null
                    && !"[]".equals(record.get("QYCB_RYCBXXJSON").toString())) {
                list = JSONObject.parseArray(record.get("QYCB_RYCBXXJSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 描述    单位基本信息列表（不弹框）
     * @author Allin Lin
     * @created 2020年3月4日 下午5:48:28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getDwxx")
    @ResponseBody
    public AjaxJson getDwxx(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        Map<String, Object> dwxx = new HashMap<String, Object>();//单位信息
        Map<String,Object> params = new HashMap<String, Object>();//查询参数
        List<Map<String, Object>> xzxx;//人员险种信息
        String token = SbCommonUtil.getRealToken();
        if(null!=token){
            params.put("aab999", request.getParameter("dwglm"));
            params.put("queryExtraFlag", 1);
            log.info("单位信息查询参数："+JSON.toJSONString(params));
            //调取单位基本信息接口
            Map<String, Object> result;
            try {
                result = SbCommonUtil.sbCommonGet(params, "dwxxcx", token);
                if("200".equals(result.get("code"))){
                    String data = result.get("data").toString();
                    if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)
                            && !"[]".equals(data)){
                        Map<String, Object> info = (Map<String, Object>)JSON.parse(result.get("data").toString());
                        Map<String, Object> jbxx = (Map<String, Object>)info.get("ab01");//单位基本信息
                        Map<String, Object> fjxx = (Map<String, Object>)info.get("ab01Extra");//单位附加信息
                        if(info.get("ab02s")!=null){
                            //dwxx.put("cbxx", info.get("ab02s"));//单位参保信息
                            List<Map<String, Object>> cbxx = (List<Map<String, Object>>)info.get("ab02s");
                            dwxx.put("QYCB_JBJGDM",cbxx.get(0).get("aab034"));//经办机构代码
                            //初始化人员参加险种信息
                            xzxx = initPersonXzInfo(cbxx);                          
                            dwxx.put("cbxx", xzxx);//人员参保信息                           
                        }
                        if(jbxx!=null){
                            dwxx.put("QYCB_DWGLM", jbxx.get("aab999"));//单位管理码
                            dwxx.put("QYCB_DWMC", jbxx.get("aab004"));//单位名称
                            dwxx.put("QYCB_DWJC", jbxx.get("bab151"));//单位简称
                            dwxx.put("QYCB_DWZT", jbxx.get("bab102"));//单位状态
                            dwxx.put("QYCB_BZ", jbxx.get("aae013"));//备注
                            dwxx.put("QYCB_ZZLX", jbxx.get("bab101"));//证照类型
                            dwxx.put("QYCB_JJLX",jbxx.get("aab020"));//经济类型
                            dwxx.put("QYCB_ZZHM", jbxx.get("aab003"));//证照号码
                            dwxx.put("QYCB_DWLX", jbxx.get("aab019"));//单位类型
                            dwxx.put("QYCB_DWGLLX", jbxx.get("bab103"));//单位管理类型
                            dwxx.put("QYCB_TJLX", jbxx.get("bab107"));//统计类型
                            dwxx.put("QYCB_LSGX", jbxx.get("aab021"));//隶属关系
                            dwxx.put("QYCB_FXLB", jbxx.get("aaa149"));//行业风险类别
                            dwxx.put("QYCB_JQLX", jbxx.get("aab020"));//经济类型
                            dwxx.put("QYCB_PZCLRQ", jbxx.get("aab999"));//批准成立日期
                            dwxx.put("SWJGXX_SH", jbxx.get("aab030"));//税务电脑编码
                            dwxx.put("SWJGXX_JGDM", jbxx.get("aaz066"));//税务机构代码
                            dwxx.put("QYCB_DWBH", jbxx.get("aab001"));//单位编号
                            dwxx.put("QYCB_DWZT", jbxx.get("bab102"));//单位状态

                        }
                        if(fjxx!=null){
                            dwxx.put("QYCB_LXDH", fjxx.get("aae005"));//联系电话
                            dwxx.put("QYCB_DZYX", fjxx.get("aae159"));//联系电子邮箱
                            dwxx.put("QYCB_YZBM", fjxx.get("aae007"));//邮政编码
                            dwxx.put("QYCB_CZDH", fjxx.get("aae387"));//传真电话
                            dwxx.put("QYCB_DZ", fjxx.get("aae006"));//地址
                            dwxx.put("QYCB_ZZZL", fjxx.get("aab006"));//工商登记执照种类
                            dwxx.put("QYCB_ZZHM", fjxx.get("aab007"));//工商登记执照号码
                            dwxx.put("QYCB_FZRQ", fjxx.get("aab008"));//工商登记发照日期
                            dwxx.put("QYCB_YXNX", fjxx.get("aab009"));//工商登记有效年限
                            dwxx.put("QYCB_KSRQ", fjxx.get("bab155"));//工商有效期限开始日期
                            dwxx.put("QYCB_JZRQ", fjxx.get("bab156"));//工商有效期限截止日期
                            dwxx.put("QYCB_XZQY", fjxx.get("aab301"));//所属行政区域
                            dwxx.put("QYCB_SSHY", fjxx.get("aab022"));//所属行业
                            dwxx.put("QYCB_CLRQ", fjxx.get("aae049"));//成立日期
                            dwxx.put("QYCB_FRXM", fjxx.get("aab013"));//法定代表人姓名
                            dwxx.put("QYCB_FRZJLX", fjxx.get("aac058"));//法定代表人证件类型
                            dwxx.put("QYCB_FRZJHM", fjxx.get("aab014"));//法定代表人证件号码
                            dwxx.put("QYCB_FRLXDH", fjxx.get("bab157"));//法定代表人联系电话
                            dwxx.put("QYCB_ZGYXM", fjxx.get("aab016"));//专管员姓名
                            dwxx.put("QYCB_ZGYZJHM", fjxx.get("bab165"));//专管员身份证号码
                            dwxx.put("QYCB_ZGYLXHM", fjxx.get("bab176"));//专管员联系号码
                            dwxx.put("QYCB_ZGYSSBM", fjxx.get("bab162"));//专管员所属部门
                            dwxx.put("QYCB_ZGYBMFZR", fjxx.get("bab163"));//专管员所属部门负责人
                            dwxx.put("QYCB_BMFZRDH", fjxx.get("bab164"));//专管员部门负责人电话
                        }
                        ajaxJson.setJsonString(JSON.toJSONString(dwxx));
                        ajaxJson.setSuccess(true);
                        log.info("单位基本和参保信息接口返回结果："+JSON.toJSONString(dwxx));
                    }else{
                        ajaxJson.setMsg("单位基本和参保信息查询为空！");
                        ajaxJson.setSuccess(false);
                    }
                }else{
                    ajaxJson.setMsg(result.get("message").toString());
                    ajaxJson.setSuccess(false);
                }
            } catch (Exception e) {
                log.error("获取社保单位基本信息和参保信息接口异常："+e.getMessage());
            }
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }
    
    /**
     * 描述    初始化人员险种信息（养老保险、工伤保险）
     * @author Allin Lin
     * @created 2020年4月21日 下午2:23:28
     * @param cbxx
     * @return
     */
    private List<Map<String, Object>> initPersonXzInfo(List<Map<String, Object>>cbxx){
        Date date = new Date();
        List<Map<String, Object>> xzInfo = new ArrayList<Map<String,Object>>();
        Map<String, Object> ylbx = new HashMap<String, Object>();//险种-城镇企业职工养老保险
        Map<String, Object> gsbx = new HashMap<String, Object>();//险种-工伤保险
        ylbx.put("aae140", "110");//城镇企业职工养老保险
        ylbx.put("ajc050", cbxx.get(0).get("aab050"));//单位登记参保日期
        ylbx.put("baeai6", DateTimeUtil.dateToStr(date,"yyyyMMdd").substring(0,6));//账户年月
        ylbx.put("baeaf2", DateTimeUtil.dateToStr(date,"yyyyMMdd"));//参保日期
        ylbx.put("baeac7", DateTimeUtil.dateToStr(date,"yyyyMMdd"));//首次参保地个人实行缴费时间
        ylbx.put("baeac8", DateTimeUtil.dateToStr(date,"yyyyMMdd"));//本人首次缴费时间
        ylbx.put("aac066","002");//参保身份-默认个人参保
        ylbx.put("aac012","17");//个人身份-默认职员
        ylbx.put("aab033","2" );////征收方式-默认税务征收
        xzInfo.add(ylbx);
        gsbx.put("aae140", "410");//工伤保险
        gsbx.put("ajc050", cbxx.get(0).get("aab050"));//单位登记参保日期
        gsbx.put("baeai6", DateTimeUtil.dateToStr(date,"yyyyMMdd").substring(0,6));//账户年月
        gsbx.put("baeaf2", DateTimeUtil.dateToStr(date,"yyyyMMdd"));//参保日期
        gsbx.put("baeac7", DateTimeUtil.dateToStr(date,"yyyyMMdd"));//首次参保地个人实行缴费时间
        gsbx.put("baeac8", DateTimeUtil.dateToStr(date,"yyyyMMdd"));//本人首次缴费时间
        gsbx.put("aac066","002");//参保身份-默认个人参保
        gsbx.put("aac012","17");//个人身份-默认职员
        gsbx.put("aab033","2" );////征收方式-默认税务征收
        xzInfo.add(gsbx);
        return xzInfo;
    }
}
