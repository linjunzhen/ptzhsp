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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.sb.service.SbCommonService;
import net.evecom.platform.sb.util.SbCommonUtil;


/**
 * 描述  企业职工基本养老保险在职人员个人信息变更Controller
 * @author Luffy Cai
 * @created 2020年3月11日 下午4:18:50
 */
@Controller
@RequestMapping("/sbQyzgxxbgController")
public class SbQyzgxxbgController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SbQyzgxxbgController.class);
    
    
    /**
     * 引入sbCommonService
     */
    @Resource
    private SbCommonService sbCommonService;
    
    
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
       /* if(!dwglm.isEmpty()){
            request.setAttribute("dwglm", dwglm);
        } */  
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
        Map<String,Object> params = BeanUtil.getMapFromRequest(request);
        String aac001 = StringUtil.getString(params.get("aac001"));
        request.setAttribute("aac001", aac001);
        String aac002 = StringUtil.getString(params.get("aac002"));
        request.setAttribute("aac002", aac002);
        return new ModelAndView("bsdt/applyform/sbqlc/qyzgxxbg/sbgrxxbgcxSelector");
    }    
 
    /**
     * 描述     获取人员基本和参保信息(弹框)
     * @author Luffy Cai
     * @created 2020年3月19日 上午10:45:33
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
     * 描述    获取人员基本和参保信息接口
     * @author Luffy Cai
     * @created 2020年3月16日 下午5:48:28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getPersonInsureInfo")
    @ResponseBody
    public AjaxJson getPersonInsureInfo(HttpServletRequest request,HttpServletResponse response){
        AjaxJson ajaxJson  = new AjaxJson();
        Map<String, Object> ryjbxx = new HashMap<String, Object>();//人员基本信息
        Map<String,Object> params = new HashMap<String, Object>();//查询参数
        String token = SbCommonUtil.getRealToken();
        if(null!=token){
            params.put("aac001", request.getParameter("grbh"));//个人编号
            params.put("aab999", request.getParameter("shbzhm"));//社会保障号码
            params.put("aac002", request.getParameter("shzh"));//身份证号
            log.info("人员信息变更查询参数："+JSON.toJSONString(params));
            //调取人员基本和参保信息接口
            try {
                Map<String, Object> result = SbCommonUtil.sbCommonGet(params, "personInsureInfo", token);
                if("200".equals(result.get("code"))){
                    String data = result.get("data").toString();
                    if(data!=null && !"".equals(data) && !"undefined".equals(data) && !"{}".equals(data)){
                        Map<String, Object> info = (Map<String, Object>)JSON.parse(result.get("data").toString());
                        if(info!=null){
                            ryjbxx.put("JBXX_TCQ", info.get("aaa027"));//统筹区
                            ryjbxx.put("DWBH", info.get("aab001"));//单位编号
                            ryjbxx.put("JBXX_DWMC", info.get("aab004"));//单位名称
                            ryjbxx.put("JBXX_DWMC", info.get("aab019"));//单位类型
                            ryjbxx.put("JBXX_DWJC", info.get("aab033"));//征收方式
                            ryjbxx.put("JBXX_DWZT", info.get("aab034"));//社保机构代码
                            ryjbxx.put("JBXX_BZ", info.get("aab301"));//常住所属行政区
                            ryjbxx.put("JBXX_TCQ", info.get("aab999"));//单位管理码
                            ryjbxx.put("JBXX_DWMC", info.get("aac001"));//个人编号
                            ryjbxx.put("JBXX_DWJC", info.get("aac002"));//身份证号/社会保障号
                            ryjbxx.put("JBXX_DWZT", info.get("aac003"));//姓名
                            ryjbxx.put("JBXX_BZ", info.get("aac004"));//性别
                            ryjbxx.put("JBXX_TCQ", info.get("aac005"));//民族
                            ryjbxx.put("JBXX_DWMC", info.get("aac006"));//出生日期
                            ryjbxx.put("JBXX_DWJC", info.get("aac007"));//参加工作日期
                            ryjbxx.put("JBXX_DWZT", info.get("aac008"));//人员参保状态
                            ryjbxx.put("JBXX_BZ", info.get("aac009"));//户口性质
                            ryjbxx.put("JBXX_TCQ", info.get("aac010"));//户口所在地址
                            ryjbxx.put("JBXX_DWMC", info.get("aac011"));//学历(文化程度）
                            ryjbxx.put("JBXX_DWJC", info.get("aac012"));//个人身份
                            ryjbxx.put("JBXX_DWZT", info.get("aac013"));//用工形式
                            ryjbxx.put("JBXX_BZ", info.get("aac014"));//专业技术职务系列名称 (专业技术职称)
                            ryjbxx.put("JBXX_TCQ", info.get("aac015"));//工人技术等级
                            ryjbxx.put("JBXX_DWMC", info.get("aac016"));//就业状态
                            ryjbxx.put("JBXX_DWJC", info.get("aac017"));//婚姻状态
                            ryjbxx.put("JBXX_DWZT", info.get("aac020"));//行政职务(行政职务级别)
                            ryjbxx.put("JBXX_BZ", info.get("aac031"));//参保缴费状态
                            ryjbxx.put("JBXX_TCQ", info.get("aac049"));//首次参保日期(业务）
                            ryjbxx.put("JBXX_DWMC", info.get("aac058"));//证件类型
                            ryjbxx.put("JBXX_DWJC", info.get("aac060"));//生存状态
                            ryjbxx.put("JBXX_DWZT", info.get("aac066"));//参保身份
                            ryjbxx.put("JBXX_BZ", info.get("aac084"));//险种离退休标志
                            ryjbxx.put("JBXX_BZ", info.get("aac114"));//是否异地居住
                            ryjbxx.put("JBXX_TCQ", info.get("aac147"));//证件号码
                            ryjbxx.put("JBXX_DWMC", info.get("aac224"));//特殊人群标志
                            ryjbxx.put("JBXX_DWJC", info.get("aac227"));//人员状态标识
                            ryjbxx.put("JBXX_DWZT", info.get("aae004"));//联系人姓名
                            ryjbxx.put("JBXX_TCQ", info.get("aae005"));//联系电话
                            ryjbxx.put("JBXX_DWMC", info.get("aae006"));//通讯地址
                            ryjbxx.put("JBXX_DWJC", info.get("aae007"));//邮政编码
                            ryjbxx.put("JBXX_DWZT", info.get("aae030"));//本次参保日期(开始日期)
                            ryjbxx.put("JBXX_BZ", info.get("aae031"));//截止日期
                            ryjbxx.put("JBXX_DWZT", info.get("aae100Ac02"));//当前参保信息有效标识
                            ryjbxx.put("JBXX_BZ", info.get("aae100Ac20"));//社会参保明细信息有效标识
                            ryjbxx.put("JBXX_TCQ", info.get("aae140"));//险种类型
                            ryjbxx.put("JBXX_DWMC", info.get("aae200"));//视同缴费月数
                            ryjbxx.put("JBXX_DWJC", info.get("aae206"));//账户建立年月
                            ryjbxx.put("JBXX_DWZT", info.get("aaf022"));//编制类型
                            ryjbxx.put("JBXX_BZ", info.get("aic161"));//离退休类别
                            ryjbxx.put("JBXX_BZ", info.get("aic162"));//离退休日期
                            ryjbxx.put("JBXX_TCQ", info.get("ajc050"));//本次参加工作日期 （来单位时间）
                            ryjbxx.put("JBXX_DWMC", info.get("bac108"));//异地参保月数
                            ryjbxx.put("JBXX_DWJC", info.get("bac118"));//建账前累计缴费月数
                            ryjbxx.put("JBXX_DWZT", info.get("bac119"));//建账类型
                            ryjbxx.put("JBXX_TCQ", info.get("bac151"));//身份证出生日期
                            ryjbxx.put("JBXX_DWMC", info.get("bac169"));//建账前视同缴费月数
                            ryjbxx.put("JBXX_DWMC", info.get("bac170"));//85-95年缴费月数
                            ryjbxx.put("JBXX_DWJC", info.get("bac172"));//申报年月
                            ryjbxx.put("JBXX_DWZT", info.get("bac180"));//职工岗位
                            ryjbxx.put("JBXX_BZ", info.get("bae101"));//险种代码
                            ryjbxx.put("JBXX_BZ", info.get("bae102"));//是否基本保险
                            ryjbxx.put("JBXX_TCQ", info.get("bae103"));//险种大类编码
                            ryjbxx.put("JBXX_DWMC", info.get("bae152"));//上次核定月份
                            ryjbxx.put("JBXX_DWJC", info.get("bae156"));//统筹范围内转出日期
                            ryjbxx.put("JBXX_DWZT", info.get("bae175"));//缴费档次
                            ryjbxx.put("JBXX_TCQ", info.get("baea78"));//本次参加工作日期 （来单位时间）=ajc050
                            ryjbxx.put("JBXX_DWMC", info.get("baea97"));//连续工龄
                            ryjbxx.put("JBXX_TCQ", info.get("baeac7"));//首次参保地实行个人缴费时间
                            ryjbxx.put("JBXX_DWMC", info.get("baeac8"));//本人首次缴费时间
                            ryjbxx.put("JBXX_DWJC", info.get("baeaf1"));//缴费状态=aac031
                            ryjbxx.put("JBXX_DWZT", info.get("baeaf2"));//本次参保日期(开始日期)=aae030
                            ryjbxx.put("JBXX_TCQ", info.get("baeai5"));//92年6月前特殊工种
                            ryjbxx.put("JBXX_DWMC", info.get("baeai6"));//建立个人帐户年月=aae206
                            ryjbxx.put("JBXX_DWMC", info.get("baeaj0"));//建账前累计缴费月数
                            ryjbxx.put("JBXX_DWJC", info.get("baeaj1"));//建账前视同缴费月数
                            ryjbxx.put("JBXX_DWZT", info.get("baeaj2"));//85-95年缴费月数=bac170
                            ryjbxx.put("JBXX_BZ", info.get("cityid"));//所属市
                            ajaxJson.setJsonString(JSON.toJSONString(ryjbxx));
                            ajaxJson.setSuccess(true);
                            log.info("获取人员基本和参保信息接口返回结果："+JSON.toJSONString(ryjbxx));
                        }else{
                            ajaxJson.setMsg("人员信息变更查询为空！");
                            ajaxJson.setSuccess(false);
                        }
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
     * 描述 获取险种信息(参保登记)
     *
     * @author Allin Lin
     * @created 2019年10月11日 上午10:35:37
     * @param request
     * @param response
     */
    @RequestMapping(params = "xzxxJson")
    public void xzxxJson(HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList<Map<String, Object>>();//险种信息
        String ywId = request.getParameter("ywId");
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGXXBG",
                    new String[] { "YW_ID" }, new Object[] { ywId });
            if (record != null && record.get("CBXZXX_JSON") != null
                    && !"[]".equals(record.get("CBXZXX_JSON").toString())) {
                list = JSONObject.parseArray(record.get("CBXZXX_JSON").toString());
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    } 
    
    
    /**
     * 描述    申报人员信息变更业务服务数据推送社保系统
     * @author Luffy Cai
     * @created 2020年3月18日 下午5:10:35
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "peopleInfoRenew")
    @ResponseBody
    public AjaxJson peopleInfoRenew(HttpServletRequest request,HttpServletResponse response) {
        AjaxJson ajaxJson  = new AjaxJson();
        String ywId  = request.getParameter("ywId");
        String ywlsh = "";//业务流水号
        Map<String,Object> info = new  HashMap<String, Object>();//推送信息
        Map<String,Object> grxx = new  HashMap<String, Object>();//个人信息
        List<Map<String, Object>> ac003VoList = new ArrayList<Map<String,Object>>();//个人信息变更批量数据
        if(StringUtils.isNotEmpty(ywId)){
            Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGXXBG", 
                    new String[] {"YW_ID"}, new Object[] {ywId});
            if(record!=null){
                String token = SbCommonUtil.getRealToken();
                if(token!=null && !"undefined".equals(token) && !"".equals(token)){
                    if((record.get("YWLSH"))==null
                         || "undefined".equals(record.get("YWLSH").toString()) 
                         || "".equals(record.get("YWLSH").toString())){
                        ywlsh = String.valueOf(SbCommonUtil.getSbId());
                        record.put("YWLSH", ywlsh);
                        sbCommonService.saveOrUpdate(record, "T_SBQLC_QYZGXXBG", ywId);
                    }else{
                        ywlsh = record.get("YWLSH").toString();
                    }
                    log.info("申报号："+ywId+"对应社保系统的业务流水号为："+ywlsh);
                    //组装推送数据
                    grxx.put("baeba6", ywlsh);//业务流水号
                    grxx.put("aab001", record.get("DWBH"));//单位编号
                    grxx.put("aab034", record.get("BLRSZJG"));//办理人所在机构
                    grxx.put("aac001", record.get("RYYBXX_GRBH"));//人员编号
                    grxx.put("aac002", record.get("RYYBXX_SHBZH"));//身份证号码
                    grxx.put("aac003", record.get("RYYBXX_XM"));//姓名
                    grxx.put("aae036", record.get("CREATE_TIME"));//办理时间，申报业务时间
                    grxx.put("aac005", record.get("RYYBXX_MZ"));//民族
                    grxx.put("aac009", record.get("RYYBXX_HKXZ"));//户口性质         
                    grxx.put("aac010", record.get("RYYBXX_HKSZDZ"));//户口所在地
                    grxx.put("aac011", record.get("RYYBXX_XL"));//文化程度
                    grxx.put("aac014", record.get("RYYBXX_ZYJSZWXLMC"));//专业技术职称              
                    grxx.put("aac015", record.get("RYYBXX_ZYJSZWDJ"));//工人技术等级
                    grxx.put("aac017", record.get("RYYBXX_HYZK"));//婚姻状态        
                    grxx.put("aae005", record.get("JZDZ_CBRLXDH"));//联系电话
                    grxx.put("aae006", record.get("JZDZ_JZDLXDZ"));//通讯地址
                    grxx.put("aae007", record.get("JZDZ_JZDYZBM"));//邮政编码         
                    ac003VoList.add(grxx);
                    info.put("ac003VoList", ac003VoList);
                    log.info("人员信息变更业务推送信息："+JSON.toJSONString(info));
                    Map<String, Object> result;
                    try {
                        result = SbCommonUtil.sbCommonPost(info, "peopleInfoRenew",token,"2");
                        if("200".equals(result.get("code")) && "success".equals(result.get("message"))){
                            Map<String, Object> data = (Map<String, Object>)result.get("data");
                            List<Map<String, Object>> error = (List<Map<String, Object>>)data.get("error");
                            List<Map<String, Object>> success = (List<Map<String, Object>>)data.get("success");
                            if(error.size()<1 && success.size()>=1){
                                ajaxJson.setSuccess(true);
                                ajaxJson.setMsg("业务流水号："+success.get(0).get("serialNumber")+"申报社保系统成功！");
                                record.put("PUSH_FLAG", "1");//推送成功更新标志位
                                sbCommonService.saveOrUpdate(record, "T_SBQLC_QYZGXXBG", ywId);
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
