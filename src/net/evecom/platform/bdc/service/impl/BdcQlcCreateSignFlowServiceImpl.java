/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bdc.dao.BdcQlcMaterGenAndSignDao;
import net.evecom.platform.bdc.enumbdc.SignIdentity;
import net.evecom.platform.bdc.model.*;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcGyjsydsyqscdjService;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import net.evecom.platform.bdc.util.BdcQlcSignUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.statis.service.StatisticsService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述  不动产全流程全程网办创建签署流程Service
 *
 * @author Allin Lin
 * @created 2020年8月15日 上午11:09:52
 */
@Service("bdcQlcCreateSignFlowService")
public class BdcQlcCreateSignFlowServiceImpl extends BaseServiceImpl implements BdcQlcCreateSignFlowService {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcQlcCreateSignFlowServiceImpl.class);

    /**
     * 引入dao
     */
    @Resource
    private BdcQlcMaterGenAndSignDao dao;
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 
     */
    @Resource
    private StatisticsService statisticsService;


    /**
     * 描述   覆盖获取实体dao方法
     *
     * @return
     * @author Allin Lin
     * @created 2020年8月15日 上午11:20:20
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 签章流程启动
     *
     * @param signFlowBody
     * @throws Exception
     */
    public Map<String, Object> createSignFlow(SignFlowBody signFlowBody) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> signFlowMap = JSON.parseObject(JSON.toJSONString(signFlowBody), Map.class);
        Map<String, Object> result = BdcQlcSignUtil.creExUserOrInstitutions(signFlowMap, "signFlowStart");
        Map<String, Object> signUser = new HashMap<>();
        Map<String, Object> data = (Map) result.get("data");
        if (data == null) {
            response.put("SIGN_FLAG", false);
            response.put("SIGN_MSG", result.get("msg"));
            return response;
        }
        //更新签署状态
        String ywId = signFlowBody.getYwId();
        if (StringUtils.isNotEmpty(ywId)) {
            //返回来的流程id
            String signFlowId = StringUtil.getString(data.get("signFlowId"));
            signUser.put("SIGN_FLOWID", signFlowId);
            signUser.put("IS_SIGN","1");//签署状态改为已通知
            //不动产全程网办业务材料签章表
            dao.saveOrUpdate(signUser, "T_BDCQLC_MATERSIGNINFO", ywId);
        }
        response.put("SIGN_FLAG", true);
        return response;
    }


    /**
     * 创建询问笔录签章流程接口
     */
    public Map<String, Object> createSignFlowOfXwbl(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        //需要进行签章的申请人list
//        List<Map<String, Object>> sqrInfoList = (List<Map<String, Object>>) returnMap.get("sqrInfoList");
//        for (int i = 0; i < sqrInfoList.size(); i++) {
//            SignInfo signInfo=new SignInfo();
//            List<SignPo> signPos=Lists.newArrayList();
//            Map<String, Object> sqrInfo = sqrInfoList.get(i);
//            //申请人身份证号码
//            String sqrzjhm = StringUtil.getValue(sqrInfo, "sqrzjhm");
//            signInfo.setIdNo(sqrzjhm);
//            //位置信息
//            SignPo signPo = new SignPo();//层级1.1.1.1.1.1.1
//            signPo.setKey(XWBL_KEYWORD);//层级1.1.1.1.1.1.2
//            signPo.setKeyIndex(2);
//            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
//            signPo.setPosX(signPo.getPosX() + (i%2) * X_OFFSET_65);//层级1.1.1.1.1.1.3
//            signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
//            signPos.add(signPo);
//            signInfo.setSignPos(signPos);
//            signInfos.add(signInfo);
//        }
        SignInfo signInfo=new SignInfo();
        List<SignPo> signPos=Lists.newArrayList();
        String sqrzjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));//经办人证件号码
        signInfo.setIdNo(sqrzjhm);
        //位置信息
        SignPo signPo = new SignPo();//层级1.1.1.1.1.1.1
        signPo.setKey(XWBL_KEYWORD);//层级1.1.1.1.1.1.2
        signPo.setKeyIndex(2);
        signPo.setSignIdentity(SignIdentity.PERSON.getVal());
        signPo.setPosX(signPo.getPosX() );//层级1.1.1.1.1.1.3
        signPos.add(signPo);
        signInfo.setSignPos(signPos);
        signInfos.add(signInfo);

        return createSignFlowByPeoplesAndKeyword(exeId,XWBL_MATERNAME,signInfos);
    }

    /**
     * 创建申请表签章流程接口
     */
    public Map<String,Object>  createSignFlowOfSqb(Map<String, Object> flowVars,
                                                   Map<String, Object> returnMap) throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        //需要进行签章的申请人list
//        List<Map<String, Object>> sqrInfoList = (List<Map<String, Object>>) returnMap.get("sqrInfoList");
//        for (int i = 0; i < sqrInfoList.size(); i++) {
//            SignInfo signInfo=new SignInfo();
//            List<SignPo> signPos=Lists.newArrayList();
//            Map<String, Object> sqrInfo = sqrInfoList.get(i);
//            //申请人身份证号码
//            String sqrzjhm = StringUtil.getValue(sqrInfo, "sqrzjhm");
//            signInfo.setIdNo(sqrzjhm);
//            //位置信息
//            SignPo signPo = new SignPo();
//            signPo.setKey(SQS_KEYWORD);
//            signPo.setKeyIndex(2);
//            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
//            signPo.setPosX(signPo.getPosX() + (i%2) * X_OFFSET_65);//x轴偏移量
//            signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
//            signPos.add(signPo);
//            signInfo.setSignPos(signPos);
//            signInfos.add(signInfo);
//        }
        String sqrzjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));//经办人证件号码

        SignInfo signInfo = new SignInfo();
        List<SignPo> signPos = Lists.newArrayList();
        //申请人身份证号码
        signInfo.setIdNo(sqrzjhm);
        //位置信息
        SignPo signPo = new SignPo();
        signPo.setKey(SQS_KEYWORD);
        signPo.setKeyIndex(2);
        signPo.setSignIdentity(SignIdentity.PERSON.getVal());
        signPo.setPosX(signPo.getPosX());//x轴偏移量
        signPos.add(signPo);
        signInfo.setSignPos(signPos);
        signInfos.add(signInfo);

        return createSignFlowByPeoplesAndKeyword(exeId,SQS_MATERNAME,signInfos);
    }

    /**
     * 描述:存量房税费联办创建申请表签章
     *
     * @author Madison You
     * @created 2020/9/11 15:02:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> createSignFlowOfSqb1(Map<String, Object> flowVars,
                                                    Map<String, Object> returnMap) throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        /*签章----权属来源----卖方*/
        List<Map<String, Object>> creQslyList = (List) returnMap.get("creQslyList");
        if (creQslyList != null && !creQslyList.isEmpty()) {
            if (creQslyList.size() == 1) {  //一个人的情况
                String sqrzjhm = StringUtil.getValue(creQslyList.get(0), "sqrzjhm");
                String type = StringUtil.getValue(creQslyList.get(0), "type");
                String isWcnr = StringUtil.getValue(creQslyList.get(0), "isWcnr");
                if (type.equals("GR")) {  //个人签章的情况
                    List<SignPo> signPos = Lists.newArrayList();
                    SignInfo signInfo = new SignInfo();
                    signInfo.setIdNo(sqrzjhm);
                    SignPo signPo = new SignPo();
                    if (isWcnr.equals("0")) {
                        signPo.setKey(SQS_KEYWORD);
                    } else {
                        signPo.setKey(SQS_KEYWORD1);
                    }
                    signPo.setKeyIndex(1);
                    signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                    signPo.setPosX(signPo.getPosX());
                    signPos.add(signPo);
                    signInfo.setSignPos(signPos);
                    signInfos.add(signInfo);
                } else {  //企业签章的情况
                    String licenseNumber = StringUtil.getValue(creQslyList.get(0), "licenseNumber");
                    SignInfo signInfoQy=new SignInfo();
                    List<SignPo> signPosQy=Lists.newArrayList();
                    signInfoQy.setIdNo(sqrzjhm);
                    Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                            new String[]{"LICENSE_NUMBER"},new Object[]{licenseNumber});
                    String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
                    signInfoQy.setAuthorizationOrganizeId(organizeid);
                    SignPo signPoQy = new SignPo();
                    signPoQy.setKey(SQS_KEYWORD);
                    signPoQy.setKeyIndex(1);
                    signPoQy.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
                    signPoQy.setPosX(X_OFFSET_65);
                    signPosQy.add(signPoQy);
                    signInfoQy.setSignPos(signPosQy);
                    signInfos.add(signInfoQy);

                    SignInfo signInfoJb=new SignInfo();
                    List<SignPo> signPosJb=Lists.newArrayList();
                    //申请人身份证号码
                    signInfoJb.setIdNo(sqrzjhm);
                    //代理人：（签章）位置信息
                    SignPo signPoJb = new SignPo();
                    signPoJb.setKey(SQS_KEYWORD1);
                    signPoJb.setKeyIndex(1);
                    signPoJb.setSignIdentity(SignIdentity.PERSON.getVal());
                    signPoJb.setPosX(signPoJb.getPosX());
                    signPosJb.add(signPoJb);

                    signInfoJb.setSignPos(signPosJb);
                    signInfos.add(signInfoJb);
                }
            } else { //两个人签章的情况
                for (int i = 0; i < creQslyList.size(); i++) {
                    SignInfo signInfo=new SignInfo();
                    List<SignPo> signPos=Lists.newArrayList();
                    Map<String, Object> creQslyMap = creQslyList.get(i);
                    //申请人身份证号码
                    String sqrzjhm = StringUtil.getValue(creQslyMap, "sqrzjhm");
                    signInfo.setIdNo(sqrzjhm);
                    //位置信息
                    SignPo signPo = new SignPo();
                    signPo.setKey(SQS_KEYWORD);
                    signPo.setKeyIndex(1);
                    signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                    signPo.setPosX(signPo.getPosX() + (i%2) * X_OFFSET_65);//x轴偏移量
                    signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                    signPos.add(signPo);
                    signInfo.setSignPos(signPos);
                    signInfos.add(signInfo);
                }
            }
        }
        /*签章----权利人----买方*/
        List<Map<String, Object>> creQlrList = (List) returnMap.get("creQlrList");
        if (creQlrList != null && !creQlrList.isEmpty()) {
            if (creQlrList.size() == 1) {
                List<SignPo> signPos=Lists.newArrayList();
                String sqrzjhm = StringUtil.getValue(creQlrList.get(0), "sqrzjhm");
                String type = StringUtil.getValue(creQlrList.get(0), "type");
                String isWcnr = StringUtil.getValue(creQlrList.get(0), "isWcnr");
                if (type.equals("GR")) {
                    SignInfo signInfo = new SignInfo();
                    signInfo.setIdNo(sqrzjhm);
                    SignPo signPo = new SignPo();
                    //未成年人签代理人位置
                    if (isWcnr.equals("0")) {
                        signPo.setKey(SQS_KEYWORD);
                    } else {
                        signPo.setKey(SQS_KEYWORD1);
                    }
                    signPo.setKeyIndex(2);
                    signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                    signPo.setPosX(signPo.getPosX());
                    signPos.add(signPo);
                    signInfo.setSignPos(signPos);
                    signInfos.add(signInfo);
                } else {
                    String licenseNumber = StringUtil.getValue(creQlrList.get(0), "licenseNumber");
                    SignInfo signInfoQy=new SignInfo();
                    List<SignPo> signPosQy=Lists.newArrayList();
                    signInfoQy.setIdNo(sqrzjhm);
                    Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                            new String[]{"LICENSE_NUMBER"},new Object[]{licenseNumber});
                    String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
                    signInfoQy.setAuthorizationOrganizeId(organizeid);
                    SignPo signPoQy = new SignPo();
                    signPoQy.setKey(SQS_KEYWORD);
                    signPoQy.setKeyIndex(2);
                    signPoQy.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
                    signPoQy.setPosX(X_OFFSET_65 );
                    signPosQy.add(signPoQy);
                    signInfoQy.setSignPos(signPosQy);
                    signInfos.add(signInfoQy);

                    SignInfo signInfoJb=new SignInfo();
                    List<SignPo> signPosJb=Lists.newArrayList();
                    //申请人身份证号码
                    signInfoJb.setIdNo(sqrzjhm);
                    //代理人：（签章）位置信息
                    SignPo signPoJb = new SignPo();
                    signPoJb.setKey(SQS_KEYWORD1);
                    signPoJb.setKeyIndex(2);
                    signPoJb.setSignIdentity(SignIdentity.PERSON.getVal());
                    signPoJb.setPosX(signPoJb.getPosX());
                    signPosJb.add(signPoJb);

                    signInfoJb.setSignPos(signPosJb);
                    signInfos.add(signInfoJb);
                }
            } else {
                for (int i = 0; i < creQlrList.size(); i++) {
                    SignInfo signInfo=new SignInfo();
                    List<SignPo> signPos=Lists.newArrayList();
                    Map<String, Object> creQlrMap = creQlrList.get(i);
                    //申请人身份证号码
                    String sqrzjhm = StringUtil.getValue(creQlrMap, "sqrzjhm");
                    signInfo.setIdNo(sqrzjhm);
                    //位置信息
                    SignPo signPo = new SignPo();
                    signPo.setKey(SQS_KEYWORD);
                    signPo.setKeyIndex(2);
                    signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                    signPo.setPosX(signPo.getPosX() + (i%2) * X_OFFSET_65);//x轴偏移量
                    signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                    signPos.add(signPo);
                    signInfo.setSignPos(signPos);
                    signInfos.add(signInfo);
                }
            }
        }
        return createSignFlowByPeoplesAndKeyword(exeId,SQS_MATERNAME,signInfos,DateTimeUtil.getLaterHourDate(12));
    }


    /**
     * 创建银行申请表签章流程接口
     */
    public Map<String,Object>  createSignFlowOfBankSqb(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();

        String sqrzjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));//经办人证件号码
        String yhlxzjhm=StringUtil.getString(returnMap.get("yhlxzjhm"));//银行经办人证件号码
        if(StringUtils.equalsIgnoreCase(sqrzjhm,yhlxzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo.setKeyIndex(2);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX() );
            signPos.add(signPo);
            SignPo signPo1 = new SignPo();
            signPo1.setKey(BANKSQS_KEYWORD);
            signPo1.setKeyIndex(2);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo1.getPosX() );
            signPos.add(signPo1);

            //询问人签字位置信息
            SignPo signPo3 = new SignPo();
            signPo3.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo3.setKeyIndex(1);
            signPo3.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo3.setPosX(signPo3.getPosX()+30);
            signPos.add(signPo3);
            //代理人：（签章）位置信息
            SignPo signPo4 = new SignPo();
            signPo4.setKey(BANKSQS_KEYWORD);
            signPo4.setKeyIndex(1);
            signPo4.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo4.setPosX(signPo4.getPosX());
            signPos.add(signPo4);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }else{
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo.setKeyIndex(2);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX() );
            signPos.add(signPo);
            SignPo signPo1 = new SignPo();
            signPo1.setKey(BANKSQS_KEYWORD);
            signPo1.setKeyIndex(2);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo1.getPosX() );
            signPos.add(signPo1);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }

        String yhlxzjhmGz=StringUtil.getString(returnMap.get("yhlxzjhm"));//经办人证件号码
        //银行公章
        if(StringUtils.isNotEmpty(yhlxzjhmGz)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(yhlxzjhmGz);
            String yhdm=StringUtil.getString(returnMap.get("yhdm"));//银行证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{yhdm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            //位置信息，银行公章
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_65 );
            signPos.add(signPo);

            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //经办人
        if(StringUtils.isNotEmpty(yhlxzjhm)&&!StringUtils.equalsIgnoreCase(sqrzjhm,yhlxzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(yhlxzjhm);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX()+30);
            signPos.add(signPo);
            //代理人：（签章）位置信息
            SignPo signPo1 = new SignPo();
            signPo1.setKey(BANKSQS_KEYWORD);
            signPo1.setKeyIndex(1);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo.getPosX());
            signPos.add(signPo1);

            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BANKSQS_MATERNAME,signInfos);
    }
    /**
     * 创建抵押期限申请表签章流程接口
     */
    public Map<String,Object>  createSignFlowOfDyqxSqb(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        //需要进行签章的申请人list
//        List<Map<String, Object>> sqrInfoList = (List<Map<String, Object>>) returnMap.get("sqrInfoList");
//        for (int i = 0; i < sqrInfoList.size(); i++) {
//            SignInfo signInfo=new SignInfo();
//            List<SignPo> signPos=Lists.newArrayList();
//            Map<String, Object> sqrInfo = sqrInfoList.get(i);
//            //申请人身份证号码
//            String sqrzjhm = StringUtil.getValue(sqrInfo, "sqrzjhm");
//            signInfo.setIdNo(sqrzjhm);
//            //位置信息
//            SignPo signPo = new SignPo();
//            signPo.setKey(QYQXSQS_KEYWORD);
//            signPo.setKeyIndex(1);
//            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
//            signPo.setPosX(X_OFFSET_65*(i+2));
//            signPo.setPosY(-20);
//            signPos.add(signPo);
//            signInfo.setSignPos(signPos);
//            signInfos.add(signInfo);
//        }
        String sqrzjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));//经办人证件号码
        if(StringUtils.isNotEmpty(sqrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(QYQXSQS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_OFFSET_65);
            signPo.setPosY(-20);
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }

        String yhlxzjhmGz=StringUtil.getString(returnMap.get("yhlxzjhm"));//银行经办证件号码
        //银行公章
        if(StringUtils.isNotEmpty(yhlxzjhmGz)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(yhlxzjhmGz);
            String yhdm=StringUtil.getString(returnMap.get("yhdm"));//银行证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{yhdm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(QYQXSQS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_65*6);
            signPo.setPosY(-200);
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,QYQXSQS_MATERNAME,signInfos);
    }
    /**
     * 创建指定位置申请表签章流程接口
     */
    public Map<String,Object>  createSignFlowOfAbsolutePos(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        //经办人
        String yhlxzjhm=StringUtil.getString(returnMap.get("yhlxzjhm"));//联系人经办人证件号码
        if(StringUtils.isNotEmpty(yhlxzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(yhlxzjhm);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setSignType(1);//单页签
            signPo.setPosPage("1");
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_ABSOLUTE_OFFSET_300);
            signPo.setPosY(Y_ABSOLUTE_OFFSET_350);
            signPos.add(signPo);

            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,QYQXSQS_MATERNAME,signInfos);
    }
    /**
     * 无法提供预购商品房贷款抵押声明签章接口
     */
    public Map<String,Object>  createSignFlowOfWftgsmSqb(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        //需要进行签章的申请人list
//        List<Map<String, Object>> sqrInfoList = (List<Map<String, Object>>) returnMap.get("sqrInfoList");
//        for (int i = 0; i < sqrInfoList.size(); i++) {
//            SignInfo signInfo=new SignInfo();
//            List<SignPo> signPos=Lists.newArrayList();
//            Map<String, Object> sqrInfo = sqrInfoList.get(i);
//            //申请人身份证号码
//            String sqrzjhm = StringUtil.getValue(sqrInfo, "sqrzjhm");
//            signInfo.setIdNo(sqrzjhm);
//            //位置信息
//            SignPo signPo = new SignPo();
//            signPo.setKey(WFTGSM_KEYWORD);
//            signPo.setKeyIndex(1);
//            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
//            signPo.setPosX(X_OFFSET_65*(i+2));
//            signPo.setPosY(-20);
//            signPos.add(signPo);
//            signInfo.setSignPos(signPos);
//            signInfos.add(signInfo);
//        }
        String sqrzjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));//经办人证件号码
        if(StringUtils.isNotEmpty(sqrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(WFTGSM_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_OFFSET_65);
            signPo.setPosY(-20);
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }

        String yhlxzjhmGz=StringUtil.getString(returnMap.get("yhlxzjhm"));//银行经办人证件号码
        //银行法人章,公章
        if(StringUtils.isNotEmpty(yhlxzjhmGz)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(yhlxzjhmGz);
            String yhdm=StringUtil.getString(returnMap.get("yhdm"));//银行证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{yhdm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(WFTGSM_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(390);
            signPo.setPosY(-100);
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,WFTGSM_MATERNAME,signInfos);
    }
    
    /**
     * 创建申请书签章流程接口
     * @param exeId  办件id
     * @param materName  材料名称
     * @param sqrInfoList  签章信息人列表(sqrzjhm:申请人证件号码；signPos：签章位置信息集合）
     * @return
     * @throws Exception
     */
    public Map<String,Object>  createSignFlowByPeoplesAndKeyword(String exeId, String materName
            ,List<SignInfo> sqrInfoList) throws Exception {
        SignFlowBody signFlowBody = getSignFlowBodyByMaterName(exeId,materName);//层级1
        //具体签章人信息和位置信息
        List<Signer> signers = Lists.newArrayList();//层级1.1
        //需要进行签章的申请人list
        for (int i = 0; i < sqrInfoList.size(); i++) {
            Signer signer = new Signer();//层级1.1.1
            SignInfo signInfo = sqrInfoList.get(i);
            //申请人身份证号码
            String sqrzjhm =signInfo.getIdNo();
            Map<String, Object> user = dao.getByJdbc("T_BDCQLC_SIGNEXUSER",
                    new String[]{"USRE_NUMBER"}, new Object[]{sqrzjhm});
            //办理人用户Id
            String accountId = StringUtil.getString(user.get("ACCOUNTID"));
            signer.setAccountId(accountId);
            signer.setAuthorizationOrganizeId(signInfo.getAuthorizationOrganizeId());
            //签署文档具体信息
            List<SignDocDetail> signDocDetails = Lists.newArrayList();//层级1.1.1.1
            SignDocDetail signDocDetail = new SignDocDetail();//层级1.1.1.1.1
            //文档信息
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM  T_BDCQLC_MATERSIGNINFO  ");
            sql.append(" WHERE EXE_ID=? AND IS_SIGN=0  AND MATER_NAME=?");
            Map<String, Object> mater = dao.getByJdbc(sql.toString(), new Object[]{exeId, materName});
            String docFilekey = StringUtil.getValue(mater, "FILE_KEY");
            signDocDetail.setDocFilekey(docFilekey);//层级1.1.1.1.1.2
            //关键字、签章位置信息
            List<SignPo> signPos=signInfo.getSignPos();

            signDocDetail.setSignPos(signPos);
            signDocDetails.add(signDocDetail);
            signer.setSignDocDetails(signDocDetails);
            signers.add(signer);
        }
        signFlowBody.setSigners(signers);
        return createSignFlow(signFlowBody);
    }
    /**
     * 创建申请书签章流程接口
     * @param exeId  办件id
     * @param materName  材料名称
     * @param sqrInfoList  签章信息人列表(sqrzjhm:申请人证件号码；signPos：签章位置信息集合）
     * @param signValidity 过期时间
     * @return
     * @throws Exception
     */
    public Map<String,Object>  createSignFlowByPeoplesAndKeyword(String exeId, String materName
            ,List<SignInfo> sqrInfoList , String signValidity) throws Exception {
        SignFlowBody signFlowBody = getSignFlowBodyByMaterName(exeId,materName);//层级1
        //具体签章人信息和位置信息
        List<Signer> signers = Lists.newArrayList();//层级1.1
        //需要进行签章的申请人list
        for (int i = 0; i < sqrInfoList.size(); i++) {
            Signer signer = new Signer();//层级1.1.1
            SignInfo signInfo = sqrInfoList.get(i);
            //申请人身份证号码
            String sqrzjhm =signInfo.getIdNo();
            Map<String, Object> user = dao.getByJdbc("T_BDCQLC_SIGNEXUSER",
                    new String[]{"USRE_NUMBER"}, new Object[]{sqrzjhm});
            //办理人用户Id
            String accountId = StringUtil.getString(user.get("ACCOUNTID"));
            signer.setAccountId(accountId);
            signer.setAuthorizationOrganizeId(signInfo.getAuthorizationOrganizeId());
            //签署文档具体信息
            List<SignDocDetail> signDocDetails = Lists.newArrayList();//层级1.1.1.1
            SignDocDetail signDocDetail = new SignDocDetail();//层级1.1.1.1.1
            //文档信息
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM  T_BDCQLC_MATERSIGNINFO  ");
            sql.append(" WHERE EXE_ID=? AND IS_SIGN=0  AND MATER_NAME=?");
            Map<String, Object> mater = dao.getByJdbc(sql.toString(), new Object[]{exeId, materName});
            String docFilekey = StringUtil.getValue(mater, "FILE_KEY");
            signDocDetail.setDocFilekey(docFilekey);//层级1.1.1.1.1.2
            //关键字、签章位置信息
            List<SignPo> signPos=signInfo.getSignPos();

            signDocDetail.setSignPos(signPos);
            signDocDetails.add(signDocDetail);
            signer.setSignDocDetails(signDocDetails);
            signers.add(signer);
        }
        /*将accountId相同的签章信息合并到一起*/
        List<Signer> newSigners = filterSameSigner(signers);
        signFlowBody.setSigners(newSigners);
        signFlowBody.setSignValidity(signValidity);
        return createSignFlow(signFlowBody);
    }
    /**
     * 获取申请书发起流程实体类
     *
     * @param exeId
     * @return
     * @throws ParseException 
     */
    public SignFlowBody getSignFlowBodyByMaterName(String exeId,String materName) throws ParseException {
        List<SignDoc> signDocs = Lists.newArrayList();
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        SignFlowBody signFlowBody = new SignFlowBody();
        //设置文件签署有效期（普通件默认48小时&即办件做区别）
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        Map<String, Object> serviceItem = this.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"} , new Object[]{
                execution.get("ITEM_CODE")});
        if("1".equals(StringUtil.getString(serviceItem.get("SXLX")))){//即办件
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String deadLineDate = DateTimeUtil.getStrOfDate(time, "yyyy-MM-dd HH:mm");//截止时间
            //当天17:30前的办件，截止时间为当天23:59:59秒
            String createTimeDated = DateTimeUtil.getStrOfDate(time, "yyyy-MM-dd");
            deadLineDate = String.format("%s %s", createTimeDated, "23:59:59");
            //即办件晚于下班时间（17:30）收件或在周末收件截至时间为最近一个工作日
            deadLineDate = statisticsService.getJbjDeadLineDate(time, deadLineDate, "即办件");
            long diff;
            //计算两个时间的毫秒时间差
            diff = format.parse(deadLineDate).getTime() - time.getTime();
            long nh = 1000*60*60;//一小时的毫秒数
            long hour = diff/nh;
            signFlowBody.setSignValidity(DateTimeUtil.getStrOfDate(
                DateTimeUtil.getBeforeNDate(time,(int)hour, "h"),"yyyy-MM-dd HH:mm:ss"));
        }
        StringBuffer subject = new StringBuffer();
        subject.append(StringUtil.getString(busRecord.get("SBMC")));
        subject.append("(").append(materName).append(")");
        signFlowBody.setSubject(subject.toString());
        signFlowBody.setSignDocs(signDocs);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM  T_BDCQLC_MATERSIGNINFO  ");
        sql.append(" WHERE EXE_ID=? AND IS_SIGN=0 AND MATER_NAME=? ");
        List<Map<String, Object>> materSignInfos = dao.findBySql(sql.toString(),
                new Object[]{exeId, materName}, null);
        for (Map<String, Object> materSignInfo : materSignInfos) {
            //业务值id
            //signFlowBody.setBizNo(StringUtil.getValue(materSignInfo, "YW_ID"));
            signFlowBody.setYwId(StringUtil.getValue(materSignInfo, "YW_ID"));
            SignDoc signDoc = new SignDoc();
            signDoc.setDocFilekey(StringUtil.getValue(materSignInfo, "FILE_KEY"));
            signDocs.add(signDoc);
        }

        return signFlowBody;
    }
    /**
     * 创建预告约定书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYgyds(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String lzrzjhm=StringUtil.getValue(returnMap,"LZRZJHM"); //权力代理人证件号码
        String dlrzjhm=StringUtil.getString(returnMap.get("DLRZJHM"));//义务代理人证件号码
        //权利代理人不为空，盖代理人章，代理人为空，盖权利人章。
        if(StringUtils.isNotEmpty(lzrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(lzrzjhm);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(YGYDS_KEYWORD_DLR);
            signPo.setKeyIndex(2);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            if(Objects.equals(dlrzjhm,lzrzjhm)){
                SignPo signPo1 = new SignPo(); //询问人签字位置信息
                signPo1.setKey(YGYDS_KEYWORD_DLR);
                signPo1.setKeyIndex(1);
                signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo1.setPosX(signPo1.getPosX());
                //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPos.add(signPo1);
            }
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }else{
            //需要进行签章的权利人
            List<Map<String, Object>> qyInfoList = (List<Map<String, Object>>) returnMap.get("QLR_LIST");
            for (int i = 0; i < qyInfoList.size(); i++) {
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                Map<String, Object> sqrInfo = qyInfoList.get(i);
                String msfzjhm=StringUtil.getValue(sqrInfo,"MSFZJHM");
                signInfo.setIdNo(msfzjhm);
                //位置信息
                SignPo signPo = new SignPo();
                signPo.setKey(YGYDS_KEYWORD_MSR);
                signPo.setKeyIndex(1);
                signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo.setPosX(X_OFFSET_65 + (i%2) * X_OFFSET_65);
                signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPos.add(signPo);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            }
        }
        //义务人（公章）
        if(StringUtils.isNotEmpty(dlrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);
            String zrfzjhm=StringUtil.getString(returnMap.get("ZRFZJHM"));//企业证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{zrfzjhm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            SignPo signPo = new SignPo();//位置信息，企业公章
            signPo.setKey(YGYDS_KEYWORD_CRR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(signPo.getPosX() );
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //义务人代理人（个人章）
        if(StringUtils.isNotEmpty(dlrzjhm)&&!Objects.equals(dlrzjhm,lzrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);  //申请人身份证号码
            SignPo signPo = new SignPo(); //询问人签字位置信息
            signPo.setKey(YGYDS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        String materName=YGSPF_MATERNAME;
        String htlx=StringUtil.getValue(flowVars,"HTLX");
        if("拆迁安置协议".equals(htlx)){
            materName=YGAZF_MATERNAME;
        }
        return createSignFlowByPeoplesAndKeyword(exeId,materName,signInfos);
    }

    /**
     * 创建预告不动产申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYgbdcsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String lzrzjhm=StringUtil.getValue(returnMap,"LZRZJHM"); //权力代理人证件号码
        String dlrzjhm=StringUtil.getString(returnMap.get("DLRZJHM"));//义务代理人证件号码
        //权利代理人不为空，盖代理人章，代理人为空，盖权利人章。
        if(StringUtils.isNotEmpty(lzrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(lzrzjhm);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(YGSQS_KEYWORD_BXWR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            //位置二
            SignPo signPo1 = new SignPo();
            signPo1.setKey(YGSQS_KEYWORD_DLR);
            signPo1.setKeyIndex(2);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo1.getPosX());
            //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo1);
            if(StringUtils.equalsIgnoreCase(lzrzjhm,dlrzjhm)){
                SignPo signPo2 = new SignPo(); //询问人签字位置信息
                signPo2.setKey(YGSQS_KEYWORD_DLR);
                signPo2.setKeyIndex(1);
                signPo2.setSignIdentity(SignIdentity.PERSON.getVal());
                //signPo2.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPo2.setPosX(signPo2.getPosX());
                signPos.add(signPo2);
            }
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }else{
            //需要进行签章的权利人
            List<Map<String, Object>> qyInfoList = (List<Map<String, Object>>) returnMap.get("QLR_LIST");
            for (int i = 0; i < qyInfoList.size(); i++) {
                boolean leagal=false;
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                Map<String, Object> sqrInfo = qyInfoList.get(i);
                String msfzjhm=StringUtil.getValue(sqrInfo,"MSFZJHM");
                signInfo.setIdNo(msfzjhm);
                //位置信息
                SignPo signPo = new SignPo();
                signPo.setKey(YGSQS_KEYWORD_BXWR);
                signPo.setKeyIndex(1);
                signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo.setPosX(X_OFFSET_65 + (i%2)* X_OFFSET_65);
                signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPos.add(signPo);
                //位置2
                SignPo signPo1 = new SignPo();
                signPo1.setKey(YGSQS_KEYWORD_DLR);
                signPo1.setKeyIndex(2);
                signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo1.setPosX(signPo1.getPosX() + (i%2) * X_OFFSET_65);
                signPo1.setPosY(signPo1.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPos.add(signPo1);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            }
        }
        //义务人（公章）
        if(StringUtils.isNotEmpty(dlrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);
            String zrfzjhm=StringUtil.getString(returnMap.get("ZRFZJHM"));//企业证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{zrfzjhm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            SignPo signPo = new SignPo();//位置信息，企业公章
            signPo.setKey(YGSQS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_65);
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //义务人代理人（个人章）
        if(StringUtils.isNotEmpty(dlrzjhm)&&!StringUtils.equalsIgnoreCase(dlrzjhm,lzrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);  //申请人身份证号码
            SignPo signPo = new SignPo(); //询问人签字位置信息
            signPo.setKey(YGSQS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,YGSQS_MATERNAME,signInfos);
    }
    /**
     * 创建预抵约定书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYdyds(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String lzrzjhm=StringUtil.getValue(returnMap,"DLR2_ZJNO"); //义务代理人证件号码
        String dlrzjhm=StringUtil.getString(returnMap.get("DLR_ZJNO"));//权利代理人证件号码
        //义务代理人不为空，盖代理人章，代理人为空，盖义务人章。
        if(StringUtils.isNotEmpty(lzrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(lzrzjhm);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(YDYDS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_OFFSET_80);
            //signPo.setSealType("0");
            signPos.add(signPo);
            if(StringUtils.equalsIgnoreCase(lzrzjhm,dlrzjhm)){
                SignPo signPo1 = new SignPo(); //询问人签字位置信息
                signPo1.setKey(YDYDS_KEYWORD_DLR);
                signPo1.setKeyIndex(2);
                signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo1.setPosX(X_OFFSET_80);
                //signPo1.setSealType("0");
                signPos.add(signPo1);
            }
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }else{
            //需要进行签章的权利人
            List<Map<String, Object>> qyInfoList = (List<Map<String, Object>>) returnMap.get("YWR_LIST");
            for (int i = 0; i < qyInfoList.size(); i++) {
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                Map<String, Object> sqrInfo = qyInfoList.get(i);
                String msfzjhm=StringUtil.getValue(sqrInfo,"YWR_ZJNO");
                signInfo.setIdNo(msfzjhm);
                //位置信息
                SignPo signPo = new SignPo();
                signPo.setKey(YDYDS_KEYWORD_DYR);
                signPo.setKeyIndex(1);
                signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo.setPosX(X_OFFSET_80+ (i%2) * X_OFFSET_65);
                signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo.setSealType("0");
                signPos.add(signPo);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            }
        }
        //权利人（公章）
        if(StringUtils.isNotEmpty(dlrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);
            String zrfzjhm=StringUtil.getString(returnMap.get("QLR_ZJNO"));//企业证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{zrfzjhm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            SignPo signPo = new SignPo();//位置信息，企业公章
            signPo.setKey(YDYDS_KEYWORD_DYQR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_80 );
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //权利人代理人（个人章）
        if(StringUtils.isNotEmpty(dlrzjhm)&&!StringUtils.equalsIgnoreCase(lzrzjhm,dlrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);  //申请人身份证号码
            SignPo signPo = new SignPo(); //询问人签字位置信息
            signPo.setKey(YDYDS_KEYWORD_DLR);
            signPo.setKeyIndex(2);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_OFFSET_80);
            //signPo.setSealType("0");
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        String materName=YDSPF_MATERNAME;
        String htlx=StringUtil.getValue(flowVars,"HT_LX");
        if("拆迁安置协议".equals(htlx)){
            materName=YDAZF_MATERNAME;
        }
        return createSignFlowByPeoplesAndKeyword(exeId,materName,signInfos);
    }

    /**
     * 创建预抵不动产申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYdbdcsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String dlr2Zjno=StringUtil.getValue(returnMap,"DLR2_ZJNO"); //义务人代理人证件号码
        String dlrzjhm=StringUtil.getString(returnMap.get("DLR_ZJNO"));//权利代理人证件号码
        //义务代理人不为空，盖代理人章，代理人为空，盖义务人章。
        if(StringUtils.isNotEmpty(dlr2Zjno)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(dlr2Zjno);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(YDSQS_KEYWORD_BXWR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");
            signPos.add(signPo);
            //位置二
            SignPo signPo1 = new SignPo();
            signPo1.setKey(YDSQS_KEYWORD_DLR);
            signPo1.setKeyIndex(2);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo1.getPosX());
            //signPo1.setSealType("0");
            signPos.add(signPo1);
            if(StringUtils.equalsIgnoreCase(dlr2Zjno,dlrzjhm)){
                SignPo signPo2 = new SignPo(); //询问人签字位置信息
                signPo2.setKey(YDSQS_KEYWORD_DLR);
                signPo2.setKeyIndex(1);
                signPo2.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo2.setPosX(signPo2.getPosX());
                //signPo2.setSealType("0");
                signPos.add(signPo2);
            }
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }else{
            //需要进行签章的义务人
            List<Map<String, Object>> qyInfoList = (List<Map<String, Object>>) returnMap.get("YWR_LIST");
            for (int i = 0; i < qyInfoList.size(); i++) {
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                Map<String, Object> sqrInfo = qyInfoList.get(i);
                String msfzjhm = StringUtil.getValue(sqrInfo, "YWR_ZJNO");
                signInfo.setIdNo(msfzjhm);
                //位置信息
                SignPo signPo = new SignPo();
                signPo.setKey(YDSQS_KEYWORD_BXWR);
                signPo.setKeyIndex(1);
                signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo.setPosX(X_OFFSET_65 + (i%2) * X_OFFSET_65);
                signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo.setSealType("0");
                signPos.add(signPo);
                //位置2
                SignPo signPo1 = new SignPo();
                signPo1.setKey(YDSQS_KEYWORD_DLR);
                signPo1.setKeyIndex(2);
                signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo1.setPosX(X_OFFSET_65 + (i%2) * X_OFFSET_65);
                signPo1.setPosY(signPo1.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo1.setSealType("0");
                signPos.add(signPo1);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            }
        }
        //权利人（公章）
        if(StringUtils.isNotEmpty(dlrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);
            String zrfzjhm=StringUtil.getString(returnMap.get("QLR_ZJNO"));//企业证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{zrfzjhm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            SignPo signPo = new SignPo();//位置信息，企业公章
            signPo.setKey(YDSQS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_65 );
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //权利人代理人（个人章）
        if(StringUtils.isNotEmpty(dlrzjhm)&&!StringUtils.equalsIgnoreCase(dlr2Zjno,dlrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(dlrzjhm);  //申请人身份证号码
            SignPo signPo = new SignPo(); //询问人签字位置信息
            signPo.setKey(YDSQS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,YDSQS_MATERNAME,signInfos);
    }

    /**
     * 描述:将accountId相同的签章信息合并到一起
     *
     * @author Madison You
     * @created 2020/9/14 14:09:00
     * @param
     * @return
     */
    private List<Signer> filterSameSigner(List<Signer> signers) {
        List<Signer> newSigners = new ArrayList<>();
        List<String> accountIdList = new ArrayList<>();
        for (Signer signer : signers) {
            String accountId = signer.getAccountId();
            String authorizationOrganizeId = signer.getAuthorizationOrganizeId();
            List<SignDocDetail> signDocDetails = signer.getSignDocDetails();
            List<SignPo> signPos = signDocDetails.get(0).getSignPos();
            if (StringUtils.isEmpty(authorizationOrganizeId)) {
                if (!accountIdList.contains(accountId)) {
                    newSigners.add(signer);
                    accountIdList.add(accountId);
                } else {
                    for (Signer newSigner : newSigners) {
                        if (newSigner.getAccountId().equals(accountId) && StringUtils.isEmpty(newSigner.getAuthorizationOrganizeId())) {
                            List<SignPo> newSignPos = newSigner.getSignDocDetails().get(0).getSignPos();
                            newSignPos.add(signPos.get(0));
                        }
                    }
                }
            } else {
                newSigners.add(signer);
            }
        }
        return newSigners;
    }
 
    
    /**
     * 创建抵押权注销登记-注销申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfDyqzxdjsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String APPLICANT_TYPE = StringUtil.getString(flowVars.get("APPLICANT_TYPE"));//申请方式(1:单方申请 2：双方申请)
        List<SignInfo> signInfos=Lists.newArrayList();        
        String jbzjhm=StringUtil.getValue(returnMap,"JBZJHM"); //抵押权人经办人证件号码
        //抵押权人-经办人（个人章）
        if(StringUtils.isNotEmpty(jbzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(jbzjhm);  //申请人身份证号码
            SignPo signPo = new SignPo(); //代理人签字位置信息
            signPo.setKey(ZXSQS_KEYWORD_DLR);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //申请方式为双方申请时-抵押人签章（个人章）
        if("2".equals(APPLICANT_TYPE)){
          //需要进行签章的抵押人
            List<Map<String, Object>> dyrInfoList = (List<Map<String, Object>>) returnMap.get("DYR_LIST");
            for (int i = 0; i < dyrInfoList.size(); i++) {
                boolean leagal=false;
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                Map<String, Object> dyrInfo = dyrInfoList.get(i);
                String dyrzjhm=StringUtil.getValue(dyrInfo,"DYRZJHM");
                signInfo.setIdNo(dyrzjhm);
                //位置信息
                SignPo signPo = new SignPo();
                signPo.setKey(ZXSQS_KEYWORD_BXWR);
                signPo.setKeyIndex(1);
                signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo.setPosX(signPo.getPosX() + (i%2)* X_OFFSET_65);
                signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPos.add(signPo);
                //位置2
                SignPo signPo1 = new SignPo();
                signPo1.setKey(ZXSQS_KEYWORD_DLR);
                signPo1.setKeyIndex(2);
                signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo1.setPosX(X_OFFSET_65 + (i%2) * X_OFFSET_65);
                signPo1.setPosY(signPo1.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
                signPos.add(signPo1);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            }
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcDyqzxdjServiceImpl.ZXSQS_MATERNAME,signInfos);
    }
    
    /**
     * 创建抵押权注销登记-结清证明签章流程接口
     */
    public Map<String,Object>  createSignFlowOfDyqzxdjjqzm(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception{
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String jbrZjhm=StringUtil.getString(returnMap.get("JBZJHM"));//抵押权人经办证件号码
        List<SignInfo> signInfos=Lists.newArrayList(); 
        //银行公章
        if(StringUtils.isNotEmpty(jbrZjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(jbrZjhm);
            String yhdm=StringUtil.getString(returnMap.get("DYQRXX_IDNO"));//银行证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{yhdm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(ZXJQZM_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_65);
            signPo.setPosY(-60);
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcDyqzxdjServiceImpl.JQZM_MATERNAME,signInfos);        
    }
 
    
    /**
     * 创建国有建设用地使用权首次登记-不动产登记申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfGyjsscdjsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        boolean isGrFlag = (boolean)returnMap.get("isGrFlag");//权利人性质(true:个人  false：企业)
        List<SignInfo> signInfos=Lists.newArrayList();        
        //需要进行签章的权利人(权利人为个人、本人签章；权利人为企业，经办签个人章)
        List<Map<String, Object>> qlrInfoList = (List<Map<String, Object>>) returnMap.get("qlrInfoList");
        for (int i = 0; i < qlrInfoList.size(); i++) {
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            Map<String, Object> qlrInfo = qlrInfoList.get(i);
            String qlrzjhm;
            if(isGrFlag){//个人
                 qlrzjhm=StringUtil.getValue(qlrInfo,"sqrzjhm");
                 signInfo.setIdNo(qlrzjhm);
                 //位置信息
                 SignPo signPo = new SignPo();
                 signPo.setKey(GYSCDJSQS_KEYWORD_BXWR);
                 signPo.setKeyIndex(2);
                 signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                 signPo.setPosX(signPo.getPosX() + (i%2)* X_OFFSET_65);
                 signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                 signPos.add(signPo);
                 signInfo.setSignPos(signPos);
                 signInfos.add(signInfo);                
            }else{//企业
                 qlrzjhm=StringUtil.getValue(qlrInfo,"dlrzjhm");
                 signInfo.setIdNo(qlrzjhm);
                 //位置信息
                 SignPo signPo = new SignPo();
                 signPo.setKey(GYSCDJSQS_KEYWORD_BXWR);
                 signPo.setKeyIndex(4);
                 signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                 signPo.setPosX(signPo.getPosX() + (i%2)* X_OFFSET_65);
                 signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                 signPos.add(signPo);
                 signInfo.setSignPos(signPos);
                 signInfos.add(signInfo);
            }
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsydsyqscdjService.DJSQS_MATERNAME,signInfos);
    }
    
    /**
     * 创建国有建设用地使用权首次登记-授权委托书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfGyjsscdjwts(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception {
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        List<Map<String, Object>> qlrInfoList = (List<Map<String, Object>>) returnMap.get("qlrInfoList");
        List<SignInfo> signInfos=Lists.newArrayList();        
        for (int i = 0; i < qlrInfoList.size(); i++) {
            Map<String, Object> qlrInfo = qlrInfoList.get(i);
            String dlrzjhm=StringUtil.getValue(qlrInfo,"dlrzjhm");
            //权利人（公章）
            if(StringUtils.isNotEmpty(dlrzjhm)){
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                signInfo.setIdNo(dlrzjhm);
                String zrfzjhm=StringUtil.getString(qlrInfo.get("sqrzjhm"));//企业证照号码
                Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                        new String[]{"LICENSE_NUMBER"},new Object[]{zrfzjhm});
                String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
                signInfo.setAuthorizationOrganizeId(organizeid);
                SignPo signPo = new SignPo();//位置信息，企业公章
                signPo.setKey(GYSCDJWTS_KEYWORD);
                signPo.setKeyIndex(1);
                signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
                signPo.setPosX(X_OFFSET_65);
                signPos.add(signPo);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            }
            //权利人代理人（个人章）
            if(StringUtils.isNotEmpty(dlrzjhm)){
                SignInfo signInfo=new SignInfo();
                List<SignPo> signPos=Lists.newArrayList();
                signInfo.setIdNo(dlrzjhm);  //代理人身份证号码
                SignPo signPo = new SignPo(); //位置信息
                signPo.setKey(GYSCDJWTS_KEYWORD);
                signPo.setKeyIndex(2);
                signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                signPo.setPosX(X_OFFSET_65);
                signPos.add(signPo);
                signInfo.setSignPos(signPos);
                signInfos.add(signInfo);
            } 
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsydsyqscdjService.WTS_MATERNAME,signInfos);
    }
    
    
    /**
     * 创建国有建设用地使用权及房屋所有权首次登记-不动产登记申请书签章流程接口
     */
   /* public Map<String,Object>  createSignFlowOfGyjsydjfwscdjsqs(Map<String, Object> flowVars,
            Map<String, Object> returnMap)throws Exception{
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        boolean isGrFlag = (boolean)returnMap.get("isGrFlag");//权利人性质(true:个人  false：企业)
        List<SignInfo> signInfos=Lists.newArrayList();        
        //需要进行签章的权利人(权利人为个人、本人签章；权利人为企业，经办签个人章)
        List<Map<String, Object>> qlrInfoList = (List<Map<String, Object>>) returnMap.get("qlrInfoList");
        for (int i = 0; i < qlrInfoList.size(); i++) {
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            Map<String, Object> qlrInfo = qlrInfoList.get(i);
            String qlrzjhm;
            if(isGrFlag){//个人
                 qlrzjhm=StringUtil.getValue(qlrInfo,"sqrzjhm");
                 signInfo.setIdNo(qlrzjhm);
                 //位置信息
                 SignPo signPo = new SignPo();
                 signPo.setKey(GYJSYDJFWSCDJSQS_KEYWORD_BXWR);
                 signPo.setKeyIndex(2);
                 signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                 signPo.setPosX(signPo.getPosX() + (i%2)* X_OFFSET_65);
                 signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                 signPos.add(signPo);
                 signInfo.setSignPos(signPos);
                 signInfos.add(signInfo);                
            }else{//企业
                 qlrzjhm=StringUtil.getValue(qlrInfo,"dlrzjhm");
                 signInfo.setIdNo(qlrzjhm);
                 //位置信息
                 SignPo signPo = new SignPo();
                 signPo.setKey(GYJSYDJFWSCDJSQS_KEYWORD_BXWR);
                 signPo.setKeyIndex(4);
                 signPo.setSignIdentity(SignIdentity.PERSON.getVal());
                 signPo.setPosX(signPo.getPosX() + (i%2)* X_OFFSET_65);
                 signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
                 signPos.add(signPo);
                 signInfo.setSignPos(signPos);
                 signInfos.add(signInfo);
            }
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsydjfwsyqscdjService.DJSQS_MATERNAME,signInfos);
    }*/
    
    /**
     * 创建抵押权首次登记-银行申请表签章流程接口
     */
    public Map<String, Object> createSignFlowOfDyqscdjBankSqb(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception {
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();

        String sqrzjhm=StringUtil.getString(flowVars.get("JBR_ZJHM"));//经办人证件号码
        String yhlxzjhm=StringUtil.getString(returnMap.get("yhlxzjhm"));//银行经办人证件号码
        if(StringUtils.equalsIgnoreCase(sqrzjhm,yhlxzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo.setKeyIndex(2);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            SignPo signPo1 = new SignPo();
            signPo1.setKey(BANKSQS_KEYWORD);
            signPo1.setKeyIndex(2);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPo1.setPosX(signPo1.getPosX() );
            signPos.add(signPo1);

            //询问人签字位置信息
            SignPo signPo3 = new SignPo();
            signPo3.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo3.setKeyIndex(1);
            signPo3.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo3.setPosX(signPo3.getPosX()+30);
            //signPo3.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo3);
            //代理人：（签章）位置信息
            SignPo signPo4 = new SignPo();
            signPo4.setKey(BANKSQS_KEYWORD);
            signPo4.setKeyIndex(1);
            signPo4.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo4.setPosX(signPo4.getPosX());
            //signPo4.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo4);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }else{
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo.setKeyIndex(2);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX() );
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            SignPo signPo1 = new SignPo();
            signPo1.setKey(BANKSQS_KEYWORD);
            signPo1.setKeyIndex(2);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo1.getPosX() );
            //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo1);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }

        String yhlxzjhmGz=StringUtil.getString(returnMap.get("yhlxzjhm"));//经办人证件号码
        //银行公章
        if(StringUtils.isNotEmpty(yhlxzjhmGz)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(yhlxzjhmGz);
            String yhdm=StringUtil.getString(returnMap.get("yhdm"));//银行证照号码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{yhdm});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            //位置信息，银行公章
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(X_OFFSET_65 );
            signPos.add(signPo);

            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //经办人
        if(StringUtils.isNotEmpty(yhlxzjhm)&&!StringUtils.equalsIgnoreCase(sqrzjhm,yhlxzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //申请人身份证号码
            signInfo.setIdNo(yhlxzjhm);
            //询问人签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BANKSQS_KEYWORD_XWRQZ);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX()+30);
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            //代理人：（签章）位置信息
            SignPo signPo1 = new SignPo();
            signPo1.setKey(BANKSQS_KEYWORD);
            signPo1.setKeyIndex(1);
            signPo1.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo1.setPosX(signPo.getPosX());
            //signPo1.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo1);

            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BANKSQS_MATERNAME,signInfos);
    }

    /**
     * 存量房税费联办业务-税务证明事项告知承诺书签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfSwzmCns(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String sqrzjhm=StringUtil.getString(flowVars.get("SSSB_QSRZJHM"));//纳税人证件号码
        if(StringUtils.isNotEmpty(sqrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BdcGyjsjfwzydjService.SWZMCNS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_OFFSET_80);
            signPo.setPosY(signPo.getPosY());
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsjfwzydjService.SWZMCNS_MATERNAME,signInfos);
    }
    
    /**
     * 存量房税费联办业务-存量房评估补充信息表签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfPgBcXx(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String sqrzjhm=StringUtil.getString(flowVars.get("SSSB_QSRZJHM"));//纳税人证件号码
        if(StringUtils.isNotEmpty(sqrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BdcGyjsjfwzydjService.CLFPGBCXX_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(X_OFFSET_80);
            signPo.setPosY(signPo.getPosY());
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsjfwzydjService.CLFPGBCXX_MATERNAME,signInfos);
    }
    
    /**
     * 存量房税费联办业务-家庭唯一生活用房承诺书签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfJtWyYfCns(Map<String, Object> flowVars,
            Map<String, Object> returnMap,Map<String, Object> fwqlrMap,int index) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String sqrzjhm=StringUtil.getString(fwqlrMap.get("sqrzjhm"));//房屋所有权利人证件号码
        if(StringUtils.isNotEmpty(sqrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BdcGyjsjfwzydjService.WYSFYFCNS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX()+ ((index-1)%2) * X_OFFSET_65);
            signPo.setPosY(signPo.getPosY()+ Y_OFFSET_25*((index-1)/Y_OFFSET_FACTOR));
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,index+"-"+BdcGyjsjfwzydjService.WYSFYFCNS_MATERNAME,signInfos);
    }
    
    /**
     * 存量房税费联办业务-个人无偿赠与不动产登记表签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfBdcZyDjb(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        String sqrzjhm=StringUtil.getString(flowVars.get("SSSB_QSRZJHM"));//纳税人证件号码
        if(StringUtils.isNotEmpty(sqrzjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(sqrzjhm);
            //位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BdcGyjsjfwzydjService.BDCZY_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(80);
            signPo.setPosY(signPo.getPosY());
            signPos.add(signPo);
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsjfwzydjService.BDCZY_MATERNAME,signInfos);
    }
    
    /**
     * 存量房税费联办业务-实名办税授权委托书签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfSmBsSqWts(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();

        String jbZjhm=StringUtil.getString(returnMap.get("JBR_ZJHM"));//经办人证件号码
        //企业公章
        if(StringUtils.isNotEmpty(jbZjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            signInfo.setIdNo(jbZjhm);
            String SQJG_CREDIT_CODE=StringUtil.getString(returnMap.get("SQJG_CREDIT_CODE"));//企业统一社会信用代码
            Map<String,Object> signexinstitution=dao.getByJdbc("T_BDCQLC_SIGNEXINSTITUTION",
                    new String[]{"LICENSE_NUMBER"},new Object[]{SQJG_CREDIT_CODE});
            String organizeid=StringUtil.getString(signexinstitution.get("ORGANIZEID"));//机构ID(天印签章系统)
            signInfo.setAuthorizationOrganizeId(organizeid);
            //位置信息，企业公章
            SignPo signPo = new SignPo();
            signPo.setKey(BdcGyjsjfwzydjService.SMBSSQWTS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.ORGANIZE.getVal());//盖章类型
            signPo.setPosX(signPo.getPosX()*2);
            signPo.setPosY(signPo.getPosY());
            signPos.add(signPo);

            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        //经办人（企业法人）
        if(StringUtils.isNotEmpty(jbZjhm)){
            SignInfo signInfo=new SignInfo();
            List<SignPo> signPos=Lists.newArrayList();
            //身份证号码
            signInfo.setIdNo(jbZjhm);
            //法人（经办）签字位置信息
            SignPo signPo = new SignPo();
            signPo.setKey(BdcGyjsjfwzydjService.SMBSSQWTS_KEYWORD);
            signPo.setKeyIndex(1);
            signPo.setSignIdentity(SignIdentity.PERSON.getVal());
            signPo.setPosX(signPo.getPosX()*3);
            signPo.setPosY(signPo.getPosY());
            //signPo.setSealType("0");//0:手绘印章1,模板印章,多选用','隔开,不传默认模板章
            signPos.add(signPo);
            
            signInfo.setSignPos(signPos);
            signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsjfwzydjService.SMBSSQWTS_MATERNAME,signInfos);
    }
    
    /**
     * 存量房税费联办业务-不动产权属转移涉税补充信息确认单签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfSsbcxxQrd(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception{
        String exeId = flowVars.get("EFLOW_EXEID").toString();//申报号
        List<SignInfo> signInfos=Lists.newArrayList();
        //需要进行签章的申请人list
        List<Map<String, Object>> sqrInfoList = (List<Map<String, Object>>) returnMap.get("sqrInfoList");
        for (int i = 0; i < sqrInfoList.size(); i++) {
              SignInfo signInfo=new SignInfo();
              List<SignPo> signPos=Lists.newArrayList();
              Map<String, Object> sqrInfo = sqrInfoList.get(i);
              //申请人身份证号码
              String sqrzjhm = StringUtil.getValue(sqrInfo, "sqrzjhm");
              signInfo.setIdNo(sqrzjhm);
              //位置信息
              SignPo signPo = new SignPo();//层级1.1.1.1.1.1.1
              signPo.setKey(BdcGyjsjfwzydjService.SSBCXXQRD_KEYWORD);//层级1.1.1.1.1.1.2
              signPo.setKeyIndex(1);
              signPo.setSignIdentity(SignIdentity.PERSON.getVal());
              signPo.setPosX(signPo.getPosX() + (i%2) * X_OFFSET_65);//层级1.1.1.1.1.1.3
              signPo.setPosY(signPo.getPosY()+Y_OFFSET_25*(i/Y_OFFSET_FACTOR));//y轴偏移量
              signPos.add(signPo);
              signInfo.setSignPos(signPos);
              signInfos.add(signInfo);
        }
        return createSignFlowByPeoplesAndKeyword(exeId,BdcGyjsjfwzydjService.SSBCXXQRD_MATERNAME,signInfos);
    }
}
