/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.CommercialSealDao;
import net.evecom.platform.zzhy.service.CommercialSealService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 商事印章信息管理操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings("rawtypes")
@Service("commercialSealService")
public class CommercialSealServiceImpl extends BaseServiceImpl implements CommercialSealService {
    /**
     * 所引入的dao
     */
    @Resource
    private CommercialSealDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_COMMERCIAL_SEAL T ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Map<String, Object> saveFlowSealData(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));
        if(StringUtils.isEmpty(exeId)){
            exeId= StringUtil.getString(flowDatas.get("EXE_ID"));
        }
        Map<String, Object> exeInfo = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        String IS_FREE_ENGRAVE_SEAL = StringUtil.getString(exeInfo.get("IS_FREE_ENGRAVE_SEAL"));
        // 选择免费刻制印章
        if (StringUtils.isNotEmpty(IS_FREE_ENGRAVE_SEAL) && IS_FREE_ENGRAVE_SEAL.equals("1")) {
            // 获取业务表名称
            String busTableName = (String) exeInfo.get("BUS_TABLENAME");
            // 内资表单，将虚拟主表名替换真实主表名称
            if (busTableName.equals("T_COMMERCIAL_DOMESTIC")) {
                busTableName = "T_COMMERCIAL_COMPANY";
            }
            // 内资表单，将虚拟主表名替换真实主表名称
            if (busTableName.equals("T_COMMERCIAL_FOREIGN")) {
                busTableName = "T_COMMERCIAL_COMPANY";
            }
            // 获取业务表记录ID
            String busRecordId = (String) exeInfo.get("BUS_RECORDID");
            String primaryKey = "";
            List keyList = dao.getPrimaryKeyName(busTableName);
            if (null != keyList) {
                primaryKey = StringUtil.getString(keyList.get(0));
            }
            Map<String, Object> info = this.getByJdbc(busTableName, new String[] { primaryKey },
                    new Object[] { busRecordId });
            Map<String, Object> sealInfo = null;
            if (busTableName.equals("T_COMMERCIAL_COMPANY")) {// 获取内资企业印章信息
                sealInfo = getCompanySeal(flowDatas, exeId, exeInfo, info);
            } else if (busTableName.equals("T_COMMERCIAL_NZ_JOINTVENTURE")) {// 获取内资合伙企业印章信息
                sealInfo = getNzJointevntureSeal(flowDatas, exeId, exeInfo, info);
            } else if (busTableName.equals("T_COMMERCIAL_BRANCH")) {// 获取内资分公司印章信息
                sealInfo = getBranchSeal(flowDatas, exeId, exeInfo, info);
            } else if (busTableName.equals("T_COMMERCIAL_SOLELYINVEST")) {// 获取个独企业印章信息
                sealInfo = getSolelyinvestSeal(flowDatas, exeId, exeInfo, info);
            } else if (busTableName.equals("T_COMMERCIAL_INTERNAL_STOCK")) {// 获取股份企业印章信息
                sealInfo = getInternalStockSeal(flowDatas, exeId, exeInfo, info);
            }
            if (null != sealInfo) {
                this.saveOrUpdate(sealInfo, "T_COMMERCIAL_SEAL", null);
            }
        }
        return flowDatas;
    }

    /**
     * 
     * 描述 获取股份企业印章信息
     * 
     * @author Rider Chen
     * @created 2021年7月29日 上午11:01:29
     * @param flowDatas
     * @param exeId
     * @param exeInfo
     * @param info
     * @return
     */
    private Map<String, Object> getInternalStockSeal(Map<String, Object> flowDatas, String exeId,
            Map<String, Object> exeInfo, Map<String, Object> info) {
        Map<String, Object> sealInfo = new HashMap<String, Object>();
        String LEGAL_NAME = StringUtil.getString(info.get("LEGAL_NAME"));
        String LEGAL_IDNO = StringUtil.getString(info.get("LEGAL_IDNO"));
        String OPERATOR_NAME = StringUtil.getString(info.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(info.get("OPERATOR_IDNO"));
        sealInfo.put("EXE_ID", exeId);
        sealInfo.put("COMPANY_NAME", info.get("COMPANY_NAME"));
        sealInfo.put("SOCIAL_CREDITUNICODE",info.get("SOCIAL_CREDITUNICODE"));
        sealInfo.put("LEGAL_NAME", info.get("LEGAL_NAME"));
        sealInfo.put("LEGAL_MOBILE", info.get("LEGAL_MOBILE"));
        sealInfo.put("LEGAL_IDTYPE", info.get("LEGAL_IDTYPE"));
        sealInfo.put("LEGAL_IDNO", info.get("LEGAL_IDNO"));
        sealInfo.put("OPERATOR_NAME", info.get("OPERATOR_NAME"));
        sealInfo.put("OPERATOR_MOBILE", info.get("OPERATOR_MOBILE"));
        sealInfo.put("OPERATOR_IDTYPE", info.get("OPERATOR_IDTYPE"));
        sealInfo.put("OPERATOR_IDNO", info.get("OPERATOR_IDNO"));
        sealInfo.put("SEAL_PACKAGE", info.get("SEAL_PACKAGE"));
        Map<String, Object> legal = getSignInfo(exeId, LEGAL_NAME, LEGAL_IDNO);
        if (null != legal && legal.size() > 0) {
            sealInfo.put("LEGAL_SFZZM", legal.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("LEGAL_SFZFM", legal.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("LEGAL_SFZZM", info.get("LEGAL_SFZZM"));
            sealInfo.put("LEGAL_SFZFM", info.get("LEGAL_SFZFM"));
        }
        Map<String, Object> operator = getSignInfo(exeId, OPERATOR_NAME, OPERATOR_IDNO);
        if (null != operator && operator.size() > 0) {
            sealInfo.put("OPERATOR_SFZZM", operator.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("OPERATOR_SFZFM", operator.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("OPERATOR_SFZZM", info.get("OPERATOR_SFZZM"));
            sealInfo.put("OPERATOR_SFZFM", info.get("OPERATOR_SFZFM"));
        }

        String RESULT_FILE_ID = StringUtil.getString(flowDatas.get("RESULT_FILE_ID"));
        getFilePathToType(sealInfo, RESULT_FILE_ID, "YYZZ_PATH", "gif,jpg,jpeg,bmp,png");

        String OPERATOR_PHOTO_FILE_ID = StringUtil.getString(exeInfo.get("OPERATOR_PHOTO_FILE_ID"));
        getFilePathToType(sealInfo, OPERATOR_PHOTO_FILE_ID, "OPERATOR_PHOTO", "gif,jpg,jpeg,bmp,png");

        sealInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sealInfo.put("STATUS", 0);
        sealInfo.put("KZZT", 0);
        sealInfo.put("IS_OVERTIME", 0);
        return sealInfo;
    }

    /**
     * 
     * 描述 获取个独公司印章信息
     * 
     * @author Rider Chen
     * @created 2021年7月29日 上午11:02:16
     * @param flowDatas
     * @param exeId
     * @param exeInfo
     * @param info
     * @return
     */
    private Map<String, Object> getSolelyinvestSeal(Map<String, Object> flowDatas, String exeId,
            Map<String, Object> exeInfo, Map<String, Object> info) {
        Map<String, Object> sealInfo = new HashMap<String, Object>();
        String JBR_NAME = StringUtil.getString(info.get("JBR_NAME"));
        String JBR_ZJHM = StringUtil.getString(info.get("JBR_ZJHM"));
        sealInfo.put("EXE_ID", exeId);
        sealInfo.put("COMPANY_NAME", info.get("COMPANY_NAME"));
        sealInfo.put("SOCIAL_CREDITUNICODE", info.get("SOCIAL_CREDITUNICODE"));

        sealInfo.put("LEGAL_NAME", info.get("INVESTOR_NAME"));
        sealInfo.put("LEGAL_MOBILE", info.get("INVESTOR_MOBILE"));
        sealInfo.put("LEGAL_IDTYPE", info.get("INVESTOR_IDTYPE"));
        sealInfo.put("LEGAL_IDNO", info.get("INVESTOR_IDCARD"));
        
        sealInfo.put("OPERATOR_NAME", exeInfo.get("JBR_NAME"));
        sealInfo.put("OPERATOR_MOBILE", exeInfo.get("JBR_MOBILE"));
        sealInfo.put("OPERATOR_IDTYPE", exeInfo.get("JBR_ZJLX"));
        sealInfo.put("OPERATOR_IDNO", exeInfo.get("JBR_ZJHM"));

        sealInfo.put("SEAL_PACKAGE", info.get("SEAL_PACKAGE"));
        sealInfo.put("LEGAL_SFZZM", info.get("LEGAL_SFZZM"));
        sealInfo.put("LEGAL_SFZFM", info.get("LEGAL_SFZFM"));
        Map<String, Object> operator = getSignInfo(exeId, JBR_NAME, JBR_ZJHM);
        if (null != operator && operator.size() > 0) {
            sealInfo.put("OPERATOR_SFZZM", operator.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("OPERATOR_SFZFM", operator.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("OPERATOR_SFZZM", info.get("OPERATOR_SFZZM"));
            sealInfo.put("OPERATOR_SFZFM", info.get("OPERATOR_SFZFM"));
        }

        String RESULT_FILE_ID = StringUtil.getString(flowDatas.get("RESULT_FILE_ID"));
        getFilePathToType(sealInfo, RESULT_FILE_ID, "YYZZ_PATH", "gif,jpg,jpeg,bmp,png");

        String OPERATOR_PHOTO_FILE_ID = StringUtil.getString(exeInfo.get("OPERATOR_PHOTO_FILE_ID"));
        getFilePathToType(sealInfo, OPERATOR_PHOTO_FILE_ID, "OPERATOR_PHOTO", "gif,jpg,jpeg,bmp,png");

        sealInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sealInfo.put("STATUS", 0);
        sealInfo.put("KZZT", 0);
        sealInfo.put("IS_OVERTIME", 0);
        return sealInfo;
    }

    /**
     * 
     * 描述 获取分公司印章信息
     * 
     * @author Rider Chen
     * @created 2021年7月29日 上午11:02:16
     * @param flowDatas
     * @param exeId
     * @param exeInfo
     * @param info
     * @return
     */
    private Map<String, Object> getBranchSeal(Map<String, Object> flowDatas, String exeId, Map<String, Object> exeInfo,
            Map<String, Object> info) {
        Map<String, Object> sealInfo = new HashMap<String, Object>();
        String OPERATOR_NAME = StringUtil.getString(info.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(info.get("OPERATOR_IDNO"));
        sealInfo.put("EXE_ID", exeId);
        sealInfo.put("COMPANY_NAME", info.get("BRANCH_NAME"));
        sealInfo.put("SOCIAL_CREDITUNICODE", info.get("SOCIAL_CREDITUNICODE"));

        sealInfo.put("LEGAL_NAME", info.get("LEGAL_NAME"));
        sealInfo.put("LEGAL_MOBILE", info.get("LEGAL_MOBILE"));
        sealInfo.put("LEGAL_IDTYPE", info.get("LEGAL_IDTYPE"));
        sealInfo.put("LEGAL_IDNO", info.get("LEGAL_IDNO"));
        
        sealInfo.put("OPERATOR_NAME", info.get("OPERATOR_NAME"));
        sealInfo.put("OPERATOR_MOBILE", info.get("OPERATOR_MOBILE"));
        sealInfo.put("OPERATOR_IDTYPE", info.get("OPERATOR_IDTYPE"));
        sealInfo.put("OPERATOR_IDNO", info.get("OPERATOR_IDNO"));
        sealInfo.put("SEAL_PACKAGE", info.get("SEAL_PACKAGE"));
        sealInfo.put("LEGAL_SFZZM", info.get("LEGAL_SFZZM"));
        sealInfo.put("LEGAL_SFZFM", info.get("LEGAL_SFZFM"));
        Map<String, Object> operator = getSignInfo(exeId, OPERATOR_NAME, OPERATOR_IDNO);
        if (null != operator && operator.size() > 0) {
            sealInfo.put("OPERATOR_SFZZM", operator.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("OPERATOR_SFZFM", operator.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("OPERATOR_SFZZM", info.get("OPERATOR_SFZZM"));
            sealInfo.put("OPERATOR_SFZFM", info.get("OPERATOR_SFZFM"));
        }

        String RESULT_FILE_ID = StringUtil.getString(flowDatas.get("RESULT_FILE_ID"));
        getFilePathToType(sealInfo, RESULT_FILE_ID, "YYZZ_PATH", "gif,jpg,jpeg,bmp,png");

        String OPERATOR_PHOTO_FILE_ID = StringUtil.getString(exeInfo.get("OPERATOR_PHOTO_FILE_ID"));
        getFilePathToType(sealInfo, OPERATOR_PHOTO_FILE_ID, "OPERATOR_PHOTO", "gif,jpg,jpeg,bmp,png");

        sealInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sealInfo.put("STATUS", 0);
        sealInfo.put("KZZT", 0);
        sealInfo.put("IS_OVERTIME", 0);
        return sealInfo;
    }

    /**
     * 
     * 描述 获取合伙企业印章信息
     * 
     * @author Rider Chen
     * @created 2021年7月29日 上午11:02:16
     * @param flowDatas
     * @param exeId
     * @param exeInfo
     * @param info
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getNzJointevntureSeal(Map<String, Object> flowDatas, String exeId,
            Map<String, Object> exeInfo, Map<String, Object> info) {
        Map<String, Object> sealInfo = new HashMap<String, Object>();
        String OPERATOR_NAME = StringUtil.getString(info.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(info.get("OPERATOR_IDNO"));
        String SOCIAL_CREDITUNICODE = StringUtil.getString(info.get("SOCIAL_CREDITUNICODE"));
        sealInfo.put("EXE_ID", exeId);
        sealInfo.put("COMPANY_NAME", info.get("COMPANY_NAME"));
        sealInfo.put("SOCIAL_CREDITUNICODE", StringUtils.isNotEmpty(SOCIAL_CREDITUNICODE) ? SOCIAL_CREDITUNICODE
                : flowDatas.get("XKFILE_NUM"));
 
        String HOLDER_JSON = StringUtil.getString(info.get("HOLDER_JSON"));
        sealInfo.put("LEGAL_SFZZM", info.get("LEGAL_SFZZM"));
        sealInfo.put("LEGAL_SFZFM", info.get("LEGAL_SFZFM"));
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(HOLDER_JSON);
            for (Map<String, Object> map : holderList) {
                String IS_PARTNERSHIP = StringUtil.getString(map.get("IS_PARTNERSHIP"));
                // 合伙企业执行事务合伙人
                if (StringUtils.isNotEmpty(IS_PARTNERSHIP) && IS_PARTNERSHIP.equals("1")) {
                    String SHAREHOLDER_TYPE = StringUtil.getString(map.get("SHAREHOLDER_TYPE"));
                    // String SSSBLX = StringUtil.getString(map.get("SSSBLX"));
                    if (StringUtils.isNotEmpty(SHAREHOLDER_TYPE) && SHAREHOLDER_TYPE.equals("zrr")) {
                        sealInfo.put("LEGAL_NAME", map.get("SHAREHOLDER_NAME"));
                        sealInfo.put("LEGAL_MOBILE", map.get("CONTACT_WAY"));
                        sealInfo.put("LEGAL_IDTYPE", map.get("LICENCE_TYPE"));
                        sealInfo.put("LEGAL_IDNO", map.get("LICENCE_NO"));
                        Map<String, Object> legal = getSignInfo(exeId,
                                StringUtil.getString(map.get("SHAREHOLDER_NAME")),
                                StringUtil.getString(map.get("LICENCE_NO")));
                        if (null != legal && legal.size() > 0) {
                            sealInfo.put("LEGAL_SFZZM", legal.get("SIGN_IDPHOTO_FRONT"));
                            sealInfo.put("LEGAL_SFZFM", legal.get("SIGN_IDPHOTO_BACK"));
                        }
                    } else {
                        sealInfo.put("LEGAL_NAME", map.get("LICENCE_APPOINT_NAME"));
                        sealInfo.put("LEGAL_MOBILE", map.get("CONTACT_WAY"));
                        sealInfo.put("LEGAL_IDTYPE", map.get("LICENCE_APPOINT_IDTYPE"));
                        sealInfo.put("LEGAL_IDNO", map.get("LICENCE_APPOINT_IDNO"));
                        Map<String, Object> legal = getSignInfo(exeId,
                                StringUtil.getString(map.get("LICENCE_APPOINT_NAME")),
                                StringUtil.getString(map.get("LICENCE_APPOINT_IDNO")));
                        if (null != legal && legal.size() > 0) {
                            sealInfo.put("LEGAL_SFZZM", legal.get("SIGN_IDPHOTO_FRONT"));
                            sealInfo.put("LEGAL_SFZFM", legal.get("SIGN_IDPHOTO_BACK"));
                        }
                    }
                    break;
                }
            }
        }
        
        sealInfo.put("OPERATOR_NAME", info.get("OPERATOR_NAME"));
        sealInfo.put("OPERATOR_MOBILE", info.get("OPERATOR_MOBILE"));
        sealInfo.put("OPERATOR_IDTYPE", info.get("OPERATOR_IDTYPE"));
        sealInfo.put("OPERATOR_IDNO", info.get("OPERATOR_IDNO"));
        sealInfo.put("SEAL_PACKAGE", info.get("SEAL_PACKAGE"));
        Map<String, Object> operator = getSignInfo(exeId, OPERATOR_NAME, OPERATOR_IDNO);
        if (null != operator && operator.size() > 0) {
            sealInfo.put("OPERATOR_SFZZM", operator.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("OPERATOR_SFZFM", operator.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("OPERATOR_SFZZM", info.get("OPERATOR_SFZZM"));
            sealInfo.put("OPERATOR_SFZFM", info.get("OPERATOR_SFZFM"));
        }

        String RESULT_FILE_ID = StringUtil.getString(flowDatas.get("RESULT_FILE_ID"));
        getFilePathToType(sealInfo, RESULT_FILE_ID, "YYZZ_PATH", "gif,jpg,jpeg,bmp,png");

        String OPERATOR_PHOTO_FILE_ID = StringUtil.getString(exeInfo.get("OPERATOR_PHOTO_FILE_ID"));
        getFilePathToType(sealInfo, OPERATOR_PHOTO_FILE_ID, "OPERATOR_PHOTO", "gif,jpg,jpeg,bmp,png");

        sealInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sealInfo.put("STATUS", 0);
        sealInfo.put("KZZT", 0);
        sealInfo.put("IS_OVERTIME", 0);
        return sealInfo;
    }

    /**
     * 
     * 描述 获取内资企业印章信息
     * 
     * @author Rider Chen
     * @created 2021年7月29日 上午11:01:29
     * @param flowDatas
     * @param exeId
     * @param exeInfo
     * @param info
     * @return
     */
    private Map<String, Object> getCompanySeal(Map<String, Object> flowDatas, String exeId, Map<String, Object> exeInfo,
            Map<String, Object> info) {
        Map<String, Object> sealInfo = new HashMap<String, Object>();
        String LEGAL_NAME = StringUtil.getString(info.get("LEGAL_NAME"));
        String LEGAL_IDNO = StringUtil.getString(info.get("LEGAL_IDNO"));
        String OPERATOR_NAME = StringUtil.getString(info.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(info.get("OPERATOR_IDNO"));
        String SOCIAL_CREDITUNICODE = StringUtil.getString(info.get("SOCIAL_CREDITUNICODE"));
        sealInfo.put("EXE_ID", exeId);
        sealInfo.put("COMPANY_NAME", info.get("COMPANY_NAME"));
        sealInfo.put("SOCIAL_CREDITUNICODE", StringUtils.isNotEmpty(SOCIAL_CREDITUNICODE) ? SOCIAL_CREDITUNICODE
                : flowDatas.get("XKFILE_NUM"));
        sealInfo.put("LEGAL_NAME", info.get("LEGAL_NAME"));
        sealInfo.put("LEGAL_MOBILE", info.get("LEGAL_MOBILE"));
        sealInfo.put("LEGAL_IDTYPE", info.get("LEGAL_IDTYPE"));
        sealInfo.put("LEGAL_IDNO", info.get("LEGAL_IDNO"));
        sealInfo.put("OPERATOR_NAME", info.get("OPERATOR_NAME"));
        sealInfo.put("OPERATOR_MOBILE", info.get("OPERATOR_MOBILE"));
        sealInfo.put("OPERATOR_IDTYPE", info.get("OPERATOR_IDTYPE"));
        sealInfo.put("OPERATOR_IDNO", info.get("OPERATOR_IDNO"));
        sealInfo.put("SEAL_PACKAGE", info.get("SEAL_PACKAGE"));
        Map<String, Object> legal = getSignInfo(exeId, LEGAL_NAME, LEGAL_IDNO);
        if (null != legal && legal.size() > 0) {
            sealInfo.put("LEGAL_SFZZM", legal.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("LEGAL_SFZFM", legal.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("LEGAL_SFZZM", info.get("LEGAL_SFZZM"));
            sealInfo.put("LEGAL_SFZFM", info.get("LEGAL_SFZFM"));
        }
        Map<String, Object> operator = getSignInfo(exeId, OPERATOR_NAME, OPERATOR_IDNO);
        if (null != operator && operator.size() > 0) {
            sealInfo.put("OPERATOR_SFZZM", operator.get("SIGN_IDPHOTO_FRONT"));
            sealInfo.put("OPERATOR_SFZFM", operator.get("SIGN_IDPHOTO_BACK"));
        } else {
            sealInfo.put("OPERATOR_SFZZM", info.get("OPERATOR_SFZZM"));
            sealInfo.put("OPERATOR_SFZFM", info.get("OPERATOR_SFZFM"));
        }

        String RESULT_FILE_ID = StringUtil.getString(flowDatas.get("RESULT_FILE_ID"));
        getFilePathToType(sealInfo, RESULT_FILE_ID, "YYZZ_PATH", "gif,jpg,jpeg,bmp,png");

        String OPERATOR_PHOTO_FILE_ID = StringUtil.getString(exeInfo.get("OPERATOR_PHOTO_FILE_ID"));
        getFilePathToType(sealInfo, OPERATOR_PHOTO_FILE_ID, "OPERATOR_PHOTO", "gif,jpg,jpeg,bmp,png");

        sealInfo.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sealInfo.put("STATUS", 0);
        sealInfo.put("KZZT", 0);
        sealInfo.put("IS_OVERTIME", 0);
        return sealInfo;
    }

    /**
     * 
     * 描述：获取面签附件路径
     * 
     * @author Rider Chen
     * @created 2020年12月2日 上午9:54:18
     * @param exeid
     * @param name
     * @param idno
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getSignInfo(String exeid, String name, String idno) {
        Map<String, Object> signFilePath = new HashMap<String, Object>();
        List<Map<String, Object>> signList = dao.getAllByJdbc("T_COMMERCIAL_SIGN",
                new String[] { "EXE_ID", "SIGN_NAME", "SIGN_IDNO", "SIGN_FLAG" },
                new Object[] { exeid, name, idno, "1" });
        if (null != signList && signList.size() > 0) {
            Map<String, Object> signInfo = signList.get(0);
            String SIGN_WRITE = signInfo.get("SIGN_WRITE") == null ? "" : signInfo.get("SIGN_WRITE").toString();
            String SIGN_IDPHOTO_FRONT = signInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                    : signInfo.get("SIGN_IDPHOTO_FRONT").toString();
            String SIGN_IDPHOTO_BACK = signInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                    : signInfo.get("SIGN_IDPHOTO_BACK").toString();
            if (StringUtils.isNotEmpty(SIGN_WRITE)) {
                Map<String, Object> fileInfo = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                        new Object[] { SIGN_WRITE });
                signFilePath.put("SIGN_WRITE", fileInfo.get("FILE_PATH"));
            }
            if (StringUtils.isNotEmpty(SIGN_IDPHOTO_FRONT)) {
                Map<String, Object> fileInfo = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                        new Object[] { SIGN_IDPHOTO_FRONT });
                signFilePath.put("SIGN_IDPHOTO_FRONT", fileInfo.get("FILE_PATH"));
            }
            if (StringUtils.isNotEmpty(SIGN_IDPHOTO_BACK)) {
                Map<String, Object> fileInfo = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                        new Object[] { SIGN_IDPHOTO_BACK });
                signFilePath.put("SIGN_IDPHOTO_BACK", fileInfo.get("FILE_PATH"));
            }
        }
        return signFilePath;
    }

    @SuppressWarnings("unchecked")
    private void getFilePathToType(Map<String, Object> sealInfo, String fileIds, String key, String fileTypeAll) {
        if (StringUtils.isNotEmpty(fileIds)) {
            String[] ids = fileIds.split(";");
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> file = this.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                        new Object[] { ids[i] });
                if (null != file) {
                    String fileType = StringUtil.getString(file.get("FILE_TYPE"));
                    if (fileTypeAll.contains(fileType)) {
                        sealInfo.put(key, file.get("FILE_PATH"));
                        break;
                    }
                }
            }
        }
    }

}
