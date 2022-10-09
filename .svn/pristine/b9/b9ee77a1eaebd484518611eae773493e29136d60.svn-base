/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.evecom.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.sb.service.SbCommonService;
import net.evecom.platform.sb.util.SbCommonUtil;
import net.evecom.platform.system.service.DictionaryService;


/**
 * 描述  企业职工基本养老保险变更登记服务Controller
 * @author Allin Lin
 * @created 2020年2月14日 下午4:18:50
 */
@Controller
@RequestMapping("/sbQyzgbgdjController")
public class SbQyzgbgdjController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SbQyzgbgdjController.class);
    
    
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
     * 描述    单位基本信息查询弹窗界面
     * @author Allin Lin
     * @created 2020年2月14日 下午4:22:30
     * @param request
     * @return
     */
    @RequestMapping(params = "dwjbxxSelector")
    public ModelAndView dwjbxxSelector(HttpServletRequest request) {
        String dwglm = request.getParameter("dwglm"); 
        request.setAttribute("dwglm", dwglm);
        return new ModelAndView("bsdt/applyform/sbqlc/qyzgbgdj/dwjbxxSelector");
    }
    /**
     * 描述    个人参保信息查询弹窗界面
     * @author Allin Lin
     * @created 2020年2月14日 下午4:22:30
     * @param request
     * @return
     */
    @RequestMapping(params = "personInsureSelector")
    public ModelAndView personInsureSelector(HttpServletRequest request) {   
        String aac001 = request.getParameter("aac001");     
        request.setAttribute("aac001", aac001);
        String aac002 = request.getParameter("aac002");
        request.setAttribute("aac002", aac002);
        return new ModelAndView("bsdt/applyform/sbqlc/qyzgzjy/personInsureSelector");
    }
    /** 
     * 描述    银行基本信息查询弹窗界面
     * @author Allin Lin
     * @created 2020年2月18日 上午11:17:33
     * @param request
     * @return
     */
    @RequestMapping(params = "yhxxSelector")
    public ModelAndView yhxxSelector(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/sbqlc/qyzgbgdj/yhxxSelector");
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
            if("200".equals(result.get("code"))){
                String data = result.get("data").toString();
                if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)){
                    infos = (List<Map<String, Object>>)JSON.parse(result.get("data").toString());
                    if(infos.size()>0){
                        persons.putAll(infos.get(0));
                        persons.put("infos",infos);
                        ajaxJson.setJsonString(JSON.toJSONString(persons));
                    }
                }
            }
            log.info("获取人员基本和参保信息返回结果："+JSON.toJSONString(infos));
        }else{
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
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
            params.remove("findDWJBXX");
            log.info("获取人员基本和参保信息："+JSON.toJSONString(params));
            //调取单位基本信息接口（test）
            Map<String, Object> result = SbCommonUtil.sbCommonPost(params, "personInsureInfo", token,"2");
            if("200".equals(result.get("code"))){
                String data = result.get("data").toString();
                if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)){
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
                        }
                        if(fjxx!=null){
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
                        ajaxJson.setJsonString(JSON.toJSONString(dwxx));
                        ajaxJson.setSuccess(true);
                        log.info("单位基本信息接口返回结果："+JSON.toJSONString(dwxx));
                    }else{
                        ajaxJson.setMsg("单位信息查询为空！");
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
     * 描述    企业职工基本养老保险变更登记服务数据推送社保系统
     * @author Allin Lin
     * @created 2020年3月9日 下午5:10:35
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "pushZgBgDj")
    @ResponseBody
    public AjaxJson pushZgBgDj(HttpServletRequest request,HttpServletResponse response) {
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        String ywlsh = "";//业务流水号
        Map<String,Object> info = new  HashMap<String, Object>();//推送信息
        Map<String,Object> dwxx = new  HashMap<String, Object>();//单位信息
        List<Map<String, Object>> ab101VoList = new ArrayList<Map<String,Object>>();//单位信息变更批量数据
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGBGDJ", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null){
                String token = SbCommonUtil.getRealToken();
                if(token!=null && !"undefined".equals(token) && !"".equals(token)){
                    if((record.get("YWLSH"))==null
                         || "undefined".equals(record.get("YWLSH").toString()) 
                         || "".equals(record.get("YWLSH").toString())){
                        ywlsh = String.valueOf(SbCommonUtil.getSbId());
                        record.put("YWLSH", ywlsh);
                        sbCommonService.saveOrUpdate(record, "T_SBQLC_QYZGBGDJ", ywId);
                    }else{
                        ywlsh = record.get("YWLSH").toString();
                    }
                    log.info("申报号："+ywId+"对应社保系统的业务流水号为："+ywlsh);
                    //组装推送数据
                    dwxx.put("baeba8", ywlsh);//业务流水号
                    dwxx.put("aab001", record.get("JBXX_DWBH"));//单位编号
                    dwxx.put("aab004", record.get("JBXX_DWMC"));//单位名称
                    dwxx.put("aab013", record.get("GJXX_FRXM"));//法定代表人姓名
                    dwxx.put("aab014", record.get("GJXX_FRZJHM"));//法定代表人身份证号码
                    dwxx.put("bab157", record.get("GJXX_FRLXDH"));//法定代表人电话
                    dwxx.put("aab034", record.get("CBXX_JBJGDM"));//办理人所在机构
                    dwxx.put("aae036", record.get("CREATE_TIME"));//申报业务时间
                    dwxx.put("aae011", AppUtil.getLoginUser().getUsername());//经办人姓名               
                    dwxx.put("aae005", record.get("JBXX_LXDH"));//联系电话
                    dwxx.put("aae006", record.get("JBXX_DZ"));//地址
                    dwxx.put("aae007", record.get("JBXX_YZBM"));//邮政编码               
                    dwxx.put("aae159", record.get("JBXX_DZYX"));//联系电子邮箱
                    dwxx.put("aae387", record.get("JBXX_CZDH"));//传真电话               
                    dwxx.put("aab016", record.get("GJXX_ZGYXM"));//专管员姓名
                    dwxx.put("bab176", record.get("GJXX_ZGYLXHM"));//专管员联系号码
                    dwxx.put("bab165", record.get("GJXX_ZGYZJHM"));//专管员身份证号码                
                    dwxx.put("bab151", record.get("JBXX_DWJC"));//单位简称
                    ab101VoList.add(dwxx);
                    info.put("ab101VoList", ab101VoList);
                    log.info("职工信息变更登记推送信息："+JSON.toJSONString(info));
                    Map<String, Object> result;
                    try {
                        result = SbCommonUtil.sbCommonPost(info, "dwxxbg",token,"2");
                        if("200".equals(result.get("code")) && "success".equals(result.get("message"))){
                            Map<String, Object> data = (Map<String, Object>)result.get("data");
                            List<Map<String, Object>> error = (List<Map<String, Object>>)data.get("error");
                            List<Map<String, Object>> success = (List<Map<String, Object>>)data.get("success");
                            if(error.size()<1 && success.size()>=1){
                                ajaxJson.setSuccess(true);
                                ajaxJson.setMsg("业务流水号："+success.get(0).get("serialNumber")+"申报社保系统成功！");
                                record.put("PUSH_FLAG", "1");//推送成功更新标志位
                                sbCommonService.saveOrUpdate(record, "T_SBQLC_QYZGBGDJ", ywId);
                            }else{
                                ajaxJson.setSuccess(false);
                                ajaxJson.setMsg("业务流水号："+error.get(0).get("serialNumber")+"申报社保系统失败,错误原因如下："+
                                        error.get(0).get("errorMsg"));
                            }
                        }else{
                            ajaxJson.setSuccess(false);
                            ajaxJson.setMsg(result.get("message").toString());
                        }
                    } catch (Exception e) {
                        log.error("企业职工基本养老保险变更登记服务数据推送异常："+e.getMessage());
                    }
                }else{
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("授权获取令牌值失败！");
                }
            }
        }
        return ajaxJson;
    }
}
