/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.sb.dao.SbCommonDao;
import net.evecom.platform.sb.service.SbCommonService;
import net.evecom.platform.sb.service.SbQyzgzjyService;
import net.evecom.platform.sb.util.SbCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述  企业职工基本养老保险增、减员通用操作services
 * @author Allin Lin
 * @created 2020年2月18日 下午2:39:54
 */
@Service("/sbQyzgzjyService")
public class SbQyzgzjyServiceImpl extends BaseServiceImpl implements SbQyzgzjyService {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SbQyzgzjyServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private SbCommonDao dao;
    
    /**
     * sbCommonService
     */
    @Resource
    private SbCommonService sbCommonService;
    
    /**
     * 描述 覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年2月18日 下午2:43:47
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    
    /**
     * 
     * 描述
     * @author Allin Lin
     * @created 2020年4月21日 上午10:50:37
     * @param ywId
     * @return
     */
    @Override
    public AjaxJson pushZgCb(String ywId) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> info = new HashMap<String, Object>();//推送信息
        Map<String, Object> cbxx;//参保信息
        List<Map<String, Object>> ac001VoList = Lists.newArrayList();
        List<Map<String, Object>> xzxxs = getXzxxForCb(ywId);//参保信息
        cbxx = getZgcbInfo(ywId);//需要推送的参保信息
        String idNo = StringUtil.getString(cbxx.get("aac002"));//身份证号
        //通过接口校验是否有效申报
        for (Map<String, Object> xzxx : xzxxs) {
            String pushFlag = StringUtil.getValue(xzxx, "PUSH_FLAG");
            if ("1".equals(pushFlag)) continue;
            String cbxzDicCode = StringUtil.getString(xzxx.get("aae140"));
            ajaxJson = sbCommonService.isValidForNew(idNo, cbxzDicCode, "true");
            if (!ajaxJson.isSuccess()) return ajaxJson;
        }
        //再推送到接口
        String token = SbCommonUtil.getRealToken();
        if(token != null && !"undefined".equals(token)&&!"".equals(token)){
           for (Map<String, Object> xzxx : xzxxs) {
                String pushFlag = StringUtil.getValue(xzxx, "PUSH_FLAG");
                if ("1".equals(pushFlag)) continue;
                Map<String,Object> vo=new HashMap<>();
                vo.putAll(cbxx);
                String cbxzDicCode = StringUtil.getString(xzxx.get("aae140"));
                vo.put("baeba6",StringUtil.getValue(xzxx,"YWLSH"));//业务流水号
                vo.put("aae140",cbxzDicCode);//险种类型
                ac001VoList.add(vo);
            }
            info.put("ac001VoList", ac001VoList);
            log.info("职工信息增员推送信息：" + JSON.toJSONString(info));
            try {
                Map<String, Object> result =SbCommonUtil.sbCommonPost(info,"personNew",token,"2");
                if ("200".equals(result.get("code")) && "success".equals(result.get("message"))) {                  
                    //String errorMsg= "";//申报失败提示信息
                    Map<String, Object> data = (Map<String, Object>) result.get("data");
                    List<Map<String, Object>> error = (List<Map<String, Object>>) data.get("error");
                    List<Map<String, Object>> success = (List<Map<String, Object>>) data.get("success");
                    if (error.size() < 1 && success.size() >=1) {//推送均成功
                        for(Map<String,Object> s:success){
                            String serialNumber=StringUtil.getValue(s,"serialNumber");                       
                            for(Map<String,Object> xzxx:xzxxs){
                                String ywlsh=StringUtil.getValue(xzxx,"YWLSH");
                                if(ywlsh.equals(serialNumber)){
                                    xzxx.put("PUSH_FLAG",1);
                                }
                            }
                        }
                        ajaxJson.setSuccess(true);
                        ajaxJson.setMsg("申报至平潭社保系统成功！");
                        HashMap<String,Object> record=Maps.newHashMap();                    
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        record.put("QYCB_RYCBXXJSON",JSONObject.toJSONString(xzxxs));
                        sbCommonService.saveOrUpdate(record, "T_SBQLC_QYZGZJY", ywId);
                    } else {//存在推送失败的信息                   
                        if(success.size() >= 1){                           
                            for(Map<String,Object> s:success){
                                String serialNumber=StringUtil.getValue(s,"serialNumber");
                                for(Map<String,Object> xzxx:xzxxs){
                                    String ywlsh=StringUtil.getValue(xzxx,"YWLSH");
                                    if(ywlsh.equals(serialNumber)){
                                        xzxx.put("PUSH_FLAG",1);
                                    }
                                }
                            }
                            HashMap<String,Object> busrecord= Maps.newHashMap();
                            busrecord.put("QYCB_RYCBXXJSON",JSONObject.toJSONString(xzxxs));
                            dao.saveOrUpdate(busrecord,"T_SBQLC_QYZGZJY",ywId);
                        }
                        String errorNum="";//申报失败流水号
                        for (Map<String, Object> e : error) {                           
                            errorNum = errorNum.concat(StringUtil.getValue(e,"serialNumber").concat("、"));
                            //errorMsg += StringUtil.getValue(e,"errorMsg")+"、";
                        }
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("业务流水号：" + errorNum.substring(0, errorNum.length()-1)
                                + "申报社保系统失败,错误原因如下：" + error.get(0).get("errorMsg").toString());
                    }
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg(result.get("message").toString());
                }
            } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("接口异常");
                log.error("企业职工基本养老保险增员数据推送异常：" + e.getMessage());
            }
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }
   
    /**
     * 获取企业职工增员基本信息
     * @param ywId
     * @return
     */
    private Map<String,Object> getZgcbInfo(String  ywId){
        Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGZJY",
                new String[] {"YW_ID"}, new Object[] {ywId});
        Map<String,Object> dwxx = new  HashMap<String, Object>();
        dwxx.put("aab001", record.get("QYCB_DWBH"));//单位编号
        dwxx.put("aab033","2");// 缴费方式
        dwxx.put("aab034", record.get("QYCB_JBJGDM"));//办理人所在机构
        dwxx.put("aac002",record.get("QYCB_ZJHM"));//公民身份号码
        dwxx.put("aac003",record.get("QYCB_XM"));//姓名
        dwxx.put("aac004",record.get("QYCB_XB"));//性别
        dwxx.put("aac005",record.get("QYCB_MZ"));//民族
        dwxx.put("aac006",record.get("QYCB_CSRQ"));//出生日期
        dwxx.put("aac007",record.get("QYCB_GZSJ"));//参加工作时间
        dwxx.put("aac008","1");//人员参保状态
        dwxx.put("aac009",record.get("QYCB_HKXZ"));//户口性质
        dwxx.put("aac010",record.get("QYCB_HKDZ"));//户口所在地
        dwxx.put("aac011",record.get("QYCB_XL"));//文化程度
        dwxx.put("aac012",record.get("QYCB_GRSF"));//个人身份
        dwxx.put("aac013",record.get("QYCB_YGXZ"));//用工形式
        dwxx.put("aac014",record.get("QYCB_ZWMC"));//专业技术职称
        dwxx.put("aac015",record.get("QYCB_ZWDJ"));//工人技术等级
        dwxx.put("aac017",record.get("QYCB_HYZK"));//婚姻状态
        dwxx.put("aac020",record.get("QYCB_XZZW"));//行政职务级别
        dwxx.put("aac028",record.get("QYCB_NMGBS"));//农民工标识
        dwxx.put("aac058",record.get("QYCB_ZJLX"));//证件类型
        dwxx.put("aae005",record.get("QYCB_CBRDH"));//联系电话
        dwxx.put("aae006",record.get("QYCB_SBDZDDZ"));//通讯地址
        dwxx.put("aae007",record.get("QYCB_JZDYZBM"));//邮政编码
        dwxx.put("aae011", AppUtil.getLoginUser().getUsername());//经办人姓名
        dwxx.put("aae013",record.get("QYCB_BZ"));//备注
        dwxx.put("aae036", DateTimeUtil.getStrOfDate(new Date(),"yyyyMMdd"));//办理时间
        dwxx.put("ajc050",record.get("QYCB_LDWSJ"));//来单位时间
        dwxx.put("aac031","1");//参保标志
        dwxx.put("baeaf2",record.get("QYCB_CBRQ"));//本次参保日期
        return dwxx;
    }

    /**
     * 获取企业职工增员参保需要推送的险种类型
     * @param ywId
     * @return
     */
    private List<Map<String,Object>> getXzxxForCb(String  ywId){
        Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGZJY",
                new String[] {"YW_ID"}, new Object[] {ywId});
        //险种信息类型
        List<Map<String,Object>> cbxxs=(List) JSONObject.
                parseArray(StringUtil.getString(record.get("QYCB_RYCBXXJSON")));
        for(Map<String,Object> cbxx:cbxxs){
            //推送标志位，为1代表已推送，无需再推送
            String pushFlag=StringUtil.getValue(cbxx,"PUSH_FLAG");
            if("1".equals(pushFlag)) continue;
            String ywlsh=StringUtil.getValue(cbxx,"YWLSH");//业务流水号
            if(StringUtils.isEmpty(ywlsh)){
                ywlsh= String.valueOf(SbCommonUtil.getSbId());
                cbxx.put("YWLSH",ywlsh);
            }
        }
        //保存业务流水号
        record.put("QYCB_RYCBXXJSON",JSONObject.toJSONString(cbxxs));
        dao.saveOrUpdate(record,"T_SBQLC_QYZGZJY",ywId);
        return  cbxxs;
    }
   
    /**
     * 企业职工减员接口推送
     * @param ywId
     * @return
     */
    @Override
    public AjaxJson pushZgJy(String ywId) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> info = new HashMap<String, Object>();//推送信息
        Map<String, Object> jyxx;//信息
        List<Map<String, Object>> Ac006VoList = Lists.newArrayList();
        List<Map<String, Object>> xzxxs = getXzxxForJy(ywId);//参保信息
        jyxx = getZgjyInfo(ywId);//需要推送的参保信息
        //推送到接口
        String token = SbCommonUtil.getRealToken();
        if (token != null && !"undefined".equals(token) && !"".equals(token)) {
            for (Map<String, Object> xzxx : xzxxs) {
                String pushFlag = StringUtil.getValue(xzxx, "PUSH_FLAG");
                if ("1".equals(pushFlag)) continue;
                Map<String,Object> vo=new HashMap<>();
                vo.putAll(jyxx);
                String cbxzDicCode = StringUtil.getString(xzxx.get("aae140"));
                vo.put("baeba8",StringUtil.getValue(xzxx,"YWLSH"));//业务申报流水号
                vo.put("aae140",cbxzDicCode);//险种类型
                Ac006VoList.add(vo);
            }
            info.put("Ac006VoList", Ac006VoList);
            log.info("企业职工减员推送信息：" + JSON.toJSONString(info));
            try {
                Map<String, Object> result = SbCommonUtil.sbCommonPost(info, "peopleStopInsurance", token, "2");
                if ("200".equals(result.get("code")) && "success".equals(result.get("message"))){
                    Map<String, Object> data = (Map<String, Object>) result.get("data");
                    List<Map<String, Object>> error = (List<Map<String, Object>>) data.get("error");
                    List<Map<String, Object>> success = (List<Map<String, Object>>) data.get("success");
                    if (error.size() < 1 && success.size() >= 1) {//推送均成功                      
                        for(Map<String,Object> s:success){
                            String serialNumber=StringUtil.getValue(s,"serialNumber"); 
                            for(Map<String,Object> xzxx:xzxxs){
                                String ywlsh=StringUtil.getValue(xzxx,"YWLSH");
                                if(ywlsh.equals(serialNumber)){
                                    xzxx.put("PUSH_FLAG",1);
                                }
                            }
                        }
                        ajaxJson.setSuccess(true);
                        ajaxJson.setMsg("申报至平潭社保系统成功！");
                        HashMap<String,Object> record=Maps.newHashMap();
                        record.put("PUSH_FLAG", "1");//推送成功更新标志位
                        record.put("QYJY_XZXXJSON",JSONObject.toJSONString(xzxxs));
                        sbCommonService.saveOrUpdate(record, "T_SBQLC_QYZGZJY", ywId);
                    }else {//存在推送失败的信息                        
                        if(success.size() >= 1){
                            for(Map<String,Object> s:success){
                                String serialNumber=StringUtil.getValue(s,"serialNumber");
                                for(Map<String,Object> xzxx:xzxxs){
                                    String ywlsh=StringUtil.getValue(xzxx,"YWLSH");
                                    if(ywlsh.equals(serialNumber)){
                                        xzxx.put("PUSH_FLAG",1);
                                    }
                                }
                            }
                            HashMap<String,Object> busrecord= Maps.newHashMap();
                            busrecord.put("QYJY_XZXXJSON",JSONObject.toJSONString(xzxxs));
                            dao.saveOrUpdate(busrecord,"T_SBQLC_QYZGZJY",ywId);
                        }
                        String errorNum="";//申报失败流水号
                        for (Map<String, Object> e : error) {
                            errorNum = errorNum.concat(StringUtil.getValue(e,"serialNumber").concat("、"));
                            //errorMsg += StringUtil.getValue(e,"errorMsg")+"、";
                        }
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("业务流水号：" + errorNum.substring(0, errorNum.length()-1)
                                + "申报社保系统失败,错误原因如下：" +error.get(0).get("errorMsg").toString());
                    }
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg(result.get("message").toString());
                }
            } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("接口异常");
                log.error("企业职工基本养老保险减员数据推送异常：" + e.getMessage());
            }
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("授权获取令牌值失败！");
        }
        return ajaxJson;
    }

    /**
     * 获取企业职工减员info
     * @param ywId
     * @return
     */
    private Map<String,Object> getZgjyInfo(String ywId){
        Map<String,Object> jyInfo=Maps.newHashMap();
        Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGZJY",
                new String[] {"YW_ID"}, new Object[] {ywId});
        jyInfo.put("aab001",record.get("QYJY_DWBH"));//单位编号
        jyInfo.put("aab034", record.get("QYCB_JBJGDM"));//办理人所在机构
        jyInfo.put("aac001",record.get("QYJY_GRBH"));//人员编号
        jyInfo.put("aac002",record.get("QYJY_SHBZHM"));//公民身份号码
        jyInfo.put("aac003",record.get("QYJY_XM"));//姓名
        jyInfo.put("aae011", AppUtil.getLoginUser().getUsername());//经办人姓名
        jyInfo.put("aae013",record.get("QYJY_BZ"));//备注
        jyInfo.put("aae036", DateTimeUtil.getStrOfDate(new Date(),"yyyyMMdd"));//办理时间
        jyInfo.put("baea26",record.get("QYJY_ZDRQ"));//减员日期
        return  jyInfo;
    }
    
    /**
     * 获取企业职工减员险种信息
     * @param ywId
     * @return
     */
    private List<Map<String,Object>> getXzxxForJy(String  ywId){
        Map<String,Object> record = sbCommonService.getByJdbc("T_SBQLC_QYZGZJY",
                new String[] {"YW_ID"}, new Object[] {ywId});
        //险种信息类型
        List<Map<String,Object>> jyxxs=(List) JSONObject.
                parseArray(StringUtil.getString(record.get("QYJY_XZXXJSON")));
        for(Map<String,Object> jyxx:jyxxs){
            String ywlsh=StringUtil.getValue(jyxx,"YWLSH");//业务流水号
            if(StringUtils.isEmpty(ywlsh)){
                ywlsh= String.valueOf(SbCommonUtil.getSbId());
                jyxx.put("YWLSH",ywlsh);
            }
        }
        //保存业务流水号
        HashMap<String,Object> busrecord= Maps.newHashMap();
        busrecord.put("QYJY_XZXXJSON",JSONObject.toJSONString(jyxxs));
        dao.saveOrUpdate(busrecord,"T_SBQLC_QYZGZJY",ywId);
        return  jyxxs;
    }

}
