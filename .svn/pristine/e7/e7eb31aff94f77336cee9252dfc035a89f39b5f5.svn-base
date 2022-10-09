/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.poi;

import com.alibaba.fastjson.JSON;
import net.evecom.core.model.TableColumn;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.core.web.servlet.DownLoadServlet;
import net.evecom.platform.commercial.service.ExtraDictionaryService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.TableNameEnum;
import net.evecom.platform.zzhy.service.CancelService;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * 
 * 描述 替换模版设置参数
 * 
 * @author Rider Chen
 * @created 2016年12月21日 下午12:00:08
 */
@SuppressWarnings("unchecked")
public class WordReplaceParamUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WordReplaceParamUtil.class);
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private CancelService cancelService;

    /**
     * 引入Service
     */
    @Resource
    private ExtraDictionaryService extraDictionaryService;

    /**
     * 描述 设置其他参数
     * 
     * @author Rider Chen
     * @created 2016年12月15日 下午2:31:40
     * @param busRecord
     */
    public void setParam(Map<String, Object> busRecord) {
        formatData(busRecord); // 格式时间
        String OPRRATE_TERM_TYPE = busRecord.get("OPRRATE_TERM_TYPE") == null ? ""
                : busRecord.get("OPRRATE_TERM_TYPE").toString(); // 经营期限
        setBussinessYears(busRecord, OPRRATE_TERM_TYPE);
        // 企业名称（英文）
        String COMPANY_NAME_ENG = busRecord.get("COMPANY_NAME_ENG") == null ? ""
                : busRecord.get("COMPANY_NAME_ENG").toString();
        busRecord.put("engCompanyName", COMPANY_NAME_ENG);
        setWzCompany(busRecord);
        // 设置经营场所
        setAddr(busRecord);
        // 设置印章信息
        setSeal(busRecord);
        String BUS_TABLENAME = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
        // 不等于商事内资注销登记
        if (StringUtils.isNotEmpty(BUS_TABLENAME) && !BUS_TABLENAME.equals("T_COMMERCIAL_CANCEL")) { // 设置股东信息
            setHolder(busRecord);
        }
        String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));// 法定代表人姓名
        String LEGAL_JOB = StringUtil.getString(busRecord.get("LEGAL_JOB"));// 法定代表人职务
        String YFDBRMC = StringUtil.getString(busRecord.get("YFDBRMC"));// 原法定代表人姓名
        String YFDBRZW = StringUtil.getString(busRecord.get("YFDBRZW"));// 原法定代表人职务
        // 股东会议决议
        List<Object> gdhyjyList = new LinkedList<Object>();
        // 董事会议决议
        List<Object> dshyjyList = new LinkedList<Object>();
        // 不等于商事内资注销登记
        if (StringUtils.isNotEmpty(BUS_TABLENAME) && !BUS_TABLENAME.equals("T_COMMERCIAL_CANCEL")) {
            // 设置董事信息
            setDirector(busRecord, LEGAL_NAME, gdhyjyList, dshyjyList);
            // 设置监事信息
            setSupervisor(busRecord, LEGAL_NAME, gdhyjyList, dshyjyList);
            // 设置经理信息
            setManager(busRecord, LEGAL_NAME, gdhyjyList, dshyjyList);
        }
        // 设置投资者信息
        setTzzxx(busRecord);
        // 设置外资
        setForeign(busRecord);
        // 设置个体商户参数
        setIndividual(busRecord);
        // 设置内资分公司
        if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_BRANCH")) {
            setBranch(busRecord);
        } else if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_NZ_JOINTVENTURE")) { // 设置合伙企业参数
            setJointVenture(busRecord);
        } else if(StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_SOLELYINVEST")){ // 设置个独企业参数
            setSolelyinvest(busRecord);
        }
        // 设置外资分公司
        setForeignBranch(busRecord);
        // 设置变更事项
        setChangeItem(busRecord);
        // 外商投资企业法律文件送达授权委托书基本信息
        setAttorney(busRecord);
        // 登记类型（0：内资，1：外资）
        String REGISTER_TYPE = busRecord.get("REGISTER_TYPE") == null ? "" : busRecord.get("REGISTER_TYPE").toString();
        if (REGISTER_TYPE.equals("1")) {
            dshyjyList.add("以上人员任期均为三年，其任职资格符合《公司法》、《中外合资企业法》" + "、《平潭综合实验区商事登记管理办法》等相关法律法规关于监事、总经理任职的规定。");
        }
        List<Object> list = new LinkedList<Object>();
        for (int i = 0; i < dshyjyList.size(); i++) {
            String value = (String) dshyjyList.get(i);
            list.add("   " + StringUtil.convertInteger(i + 1) + "、" + value);
        }
        busRecord.put("DSHJY", list);
        if (REGISTER_TYPE.equals("0")) {
            gdhyjyList.add("表决通过" + busRecord.get("FILL_DATE") + "制定的公司章程。");
        } else {
            gdhyjyList
                    .add("以上人员任期均为三年，其任职资格符合《中华人民共和国公司法》、《外资企业法》" + "、《平潭综合实验区商事登记管理办法》及本公司章程关于执行董事、法定代表人、监事、经理等任职规定。");
        }
        list = new LinkedList<Object>();
        for (int i = 0; i < gdhyjyList.size(); i++) {
            String value = (String) gdhyjyList.get(i);
            list.add("   " + StringUtil.convertInteger(i + 1) + "、" + value);
        }
        busRecord.put("HYJY", list);
        // 设置字典信息
        getTypeName(busRecord);
        // 判断内资股份特有输出方法（）201605170002XK00107
        if ("201605170002XK00107".equals(busRecord.get("ITEM_CODE"))) {
            setNzgf(busRecord);
            setNzgfWrite(busRecord);
        }
        if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_CANCEL")) {
            // 商事内资注销登记
            setCommercialCancel(busRecord);//设置面签模板数据回填
        } else if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_SSNZQYBA")) {
            // 商事内资备案登记
            setCommercialSsnzqyba(busRecord);//设置面签模板数据回填
            // 2、一人股东决定
            List<Object> yrgdjdList = new LinkedList<Object>();
            // 股东会议决议
            List<Object> gdhyList = new LinkedList<Object>();
            // 董事会议决议
            List<Object> dshyList = new LinkedList<Object>();
            StringBuffer DSZNAMES = new StringBuffer();// 董事长 执行董事集合
            setOldList(busRecord, yrgdjdList, gdhyList, dshyList, DSZNAMES);
            // 设置董事信息
            setSsnzqybaDirector(busRecord, DSZNAMES, yrgdjdList, gdhyList, dshyList);
            // 设置监事信息
            setSsnzqybaSupervisor(busRecord, LEGAL_NAME, yrgdjdList, gdhyList, dshyList);
            // 设置经理信息
            setSsnzqybaManager(busRecord, LEGAL_NAME, yrgdjdList, gdhyList, dshyList);
            // 设置董事监事经理
            setDsJsJlList(busRecord);

            busRecord.put("yrgdjdList", yrgdjdList);
            busRecord.put("gdhyList", gdhyList);
            busRecord.put("dshyList", dshyList);
            if (StringUtils.isNotEmpty(DSZNAMES)) {
                DSZNAMES = DSZNAMES.deleteCharAt(DSZNAMES.length() - 1);
            }
            busRecord.put("DSZNAMES", DSZNAMES);
            if (YFDBRZW.equals(LEGAL_JOB) && YFDBRMC.equals(LEGAL_NAME)) {// 法定代表人不做变更时公司登记（备案）申请书中不出现附表1
                busRecord.put("gsdjsqsfb", "delete");
            }
        }
        setGjjxx(busRecord);
    }
    /**
     * 
     * 描述 设置股份公司签字信息
     * @author Rider Chen
     * @created 2021年8月18日 下午5:31:39
     * @param busRecord
     */
    private void setNzgfWrite(Map<String, Object> busRecord) {
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));
        String LEGAL_IDNO = StringUtil.getString(busRecord.get("LEGAL_IDNO"));
        // 法定代表人面签签名
        Map<String, Object> getSignInfo = getSignInfo(EXE_ID, LEGAL_NAME, LEGAL_IDNO);
        busRecord.put("LEGAL_WRITE", "");
        String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
        setWordToImg(busRecord, SIGN_WRITE, "LEGAL_WRITE", "100", "40");
        //经办人面签签名
        getSignInfo = getSignInfo(EXE_ID, StringUtil.getString(busRecord.get("OPERATOR_NAME")),
                StringUtil.getString(busRecord.get("OPERATOR_IDNO")));
        busRecord.put("OPERATOR_WRITE", "");
        SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
        setWordToImg(busRecord, SIGN_WRITE, "OPERATOR_WRITE", "100", "40");
    }
    /**
     * 
     * 描述 设置公积金信息
     * 
     * @author Rider Chen
     * @created 2021年6月29日 上午10:55:58
     * @param busRecord
     */
    private void setGjjxx(Map<String, Object> busRecord) {
        try {

            String ISFUNDSREGISTER = StringUtil.getString(busRecord.get("ISFUNDSREGISTER"));// 是否办理公积金登记
            String FUNDS_GJJLX = StringUtil.getString(busRecord.get("FUNDS_GJJLX"));// 类型
            String FUNDS_GJJSTYH = StringUtil.getString(busRecord.get("FUNDS_GJJSTYH"));// 受托银行
            String FUNDS_GJJLSGX = StringUtil.getString(busRecord.get("FUNDS_GJJLSGX"));// 隶属关系
            String FUNDS_GJJDWXZ = StringUtil.getString(busRecord.get("FUNDS_GJJDWXZ"));// 单位性质
            String FUNDS_GJJJJLX = StringUtil.getString(busRecord.get("FUNDS_GJJJJLX"));// 经济类型
            String FUNDS_GJJHYFL = StringUtil.getString(busRecord.get("FUNDS_GJJHYFL"));// 行业分类
            String FUNDS_GJJDWLX = StringUtil.getString(busRecord.get("FUNDS_GJJDWLX"));// 单位类型
            String FUNDS_IDTYPE = StringUtil.getString(busRecord.get("FUNDS_IDTYPE"));// 经办人证件类型
            String FUNDS_DWSCHJSJ = StringUtil.getString(busRecord.get("FUNDS_DWSCHJSJ"));// 单位首次汇缴时间
            // 判断类型是否是公积金
            if (StringUtils.isNotEmpty(ISFUNDSREGISTER) && ISFUNDSREGISTER.equals("1")
                    && StringUtils.isNotEmpty(FUNDS_GJJLX) && FUNDS_GJJLX.equals("ZFGJJ")) {
                initDicCTSym(busRecord, FUNDS_GJJSTYH, "FUNDSGJJSTYH", "GJJSTYH", 2);// 受托银行
                initDicCTSym(busRecord, FUNDS_GJJLSGX, "FUNDSGJJLSGX", "GJJLSGX", 2);// 隶属关系
                initDicCTSym(busRecord, FUNDS_GJJDWXZ, "FUNDSGJJDWXZ", "GJJDWXZ", 2);// 单位性质
                initDicCTSym(busRecord, FUNDS_GJJJJLX, "FUNDSGJJJJLX", "GJJJJLX", 2);// 经济类型
                initDicCTSym(busRecord, FUNDS_GJJHYFL, "FUNDSGJJHYFL", "GJJHYFL", 2);// 行业分类
                initDicCTSym(busRecord, FUNDS_GJJDWLX, "FUNDSGJJDWLX", "GJJDWLX", 2);// 单位类型
                initDicCTSym(busRecord, FUNDS_IDTYPE, "FUNDSIDTYPE", "GJJZJLX", 2);// 经办人证件类型
                busRecord.put("FUNDSDWSCHJSJ1",  WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("FUNDSDWSCHJSJ2",  WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                if(StringUtils.isNotEmpty(FUNDS_DWSCHJSJ)){
                    busRecord.put("FUNDSDWSCHJSJ"+FUNDS_DWSCHJSJ,
                            WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
    }

    /**
     * 
     * 描述 根据字典值设置选中
     * 
     * @author Rider Chen
     * @created 2021年6月29日 上午11:12:26
     * @param busRecord
     * @param FUNDS_GJJJJLX
     * @param cname
     * @param typeCode
     * @param formatLength
     * @throws Exception
     */
    private void initDicCTSym(Map<String, Object> busRecord, String value, String cname, String typeCode,
            int formatLength) throws Exception {
        List<Map<String, Object>> diclist = dictionaryService.findByTypeCode(typeCode);
        for (Map<String, Object> map : diclist) {
            String dicCode = StringUtil.getString(map.get("DIC_CODE"));
            busRecord.put(cname + StringUtil.getFormatNumber(formatLength, dicCode),
                    WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        }
        if (StringUtils.isNotEmpty(value)) {
            busRecord.put(cname + StringUtil.getFormatNumber(formatLength, value),
                    WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
    }
    /**
     * 
     * 描述：设置董事监事经理信息
     * 
     * @author Rider Chen
     * @created 2021年4月20日 上午9:41:45
     * @param busRecord
     */
    private void setDsJsJlList(Map<String, Object> busRecord) {
        // 董事
        List<Map<String, Object>> directorList = (List<Map<String, Object>>) busRecord.get("directorList");
        // 监理
        List<Map<String, Object>> supervisorList = (List<Map<String, Object>>) busRecord.get("supervisorList");
        // 经理
        List<Map<String, Object>> managerList = (List<Map<String, Object>>) busRecord.get("managerList");
        // 董事监理经理
        List<Map<String, Object>> dsjsjlList = new LinkedList<Map<String, Object>>();
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        Map<String, Object> info = null;
        int num = 0;
        if (null != directorList && directorList.size() > 0) {
            for (Map<String, Object> map : directorList) {
                info = new HashMap<String, Object>();
                info.put("DSJSJL_NAME", map.get("DIRECTOR_NAME"));
                info.put("DSJSJL_JOB", map.get("DIRECTOR_JOB"));
                info.put("DSJSJL_GENERATION_MODE", map.get("DIRECTOR_GENERATION_MODE"));
                info.put("DSJSJL_COUNTRY", map.get("DIRECTOR_COUNTRY"));
                info.put("DSJSJL_IDTYPE", map.get("DIRECTOR_IDTYPE"));
                info.put("DSJSJL_IDNO", map.get("DIRECTOR_IDNO"));
                info.put("DSJSJLPHOTOFRONT", "");
                info.put("DSJSJLPHOTOBACK", "");
                if (num == 0) {
                    setDsJsJlSign(EXE_ID, info);
                }
                dsjsjlList.add(info);
                num++;
            }
        }
        if (null != supervisorList && supervisorList.size() > 0) {

            for (Map<String, Object> map : supervisorList) {
                info = new HashMap<String, Object>();
                info.put("DSJSJL_NAME", map.get("SUPERVISOR_NAME"));
                info.put("DSJSJL_JOB", map.get("SUPERVISOR_JOB"));
                info.put("DSJSJL_GENERATION_MODE", map.get("SUPERVISOR_GENERATION_MODE"));
                info.put("DSJSJL_COUNTRY", map.get("SUPERVISOR_COUNTRY"));
                info.put("DSJSJL_IDTYPE", map.get("SUPERVISOR_IDTYPE"));
                info.put("DSJSJL_IDNO", map.get("SUPERVISOR_IDNO"));
                info.put("DSJSJLPHOTOFRONT", "");
                info.put("DSJSJLPHOTOBACK", "");
                if (num == 0) {
                    setDsJsJlSign(EXE_ID, info);
                }
                dsjsjlList.add(info);
                num++;
            }
        }
        if (null != managerList && managerList.size() > 0) {
            for (Map<String, Object> map : managerList) {
                info = new HashMap<String, Object>();
                info.put("DSJSJL_NAME", map.get("MANAGER_NAME"));
                info.put("DSJSJL_JOB", map.get("MANAGER_JOB"));
                info.put("DSJSJL_GENERATION_MODE", map.get("MANAGER_GENERATION_MODE"));
                info.put("DSJSJL_COUNTRY", map.get("MANAGER_COUNTRY"));
                info.put("DSJSJL_IDTYPE", map.get("MANAGER_IDTYPE"));
                info.put("DSJSJL_IDNO", map.get("MANAGER_IDNO"));
                info.put("DSJSJLPHOTOFRONT", "");
                info.put("DSJSJLPHOTOBACK", "");
                if (num == 0) {
                    setDsJsJlSign(EXE_ID, info);
                }
                dsjsjlList.add(info);
                num++;
            }
        }

        busRecord.put("dsjsjlList", dsjsjlList);
    }

    /**
     * 
     * 描述： 设置面签信息
     * 
     * @author Rider Chen
     * @created 2021年4月20日 上午9:55:59
     * @param EXE_ID
     * @param info
     */
    public void setDsJsJlSign(String EXE_ID, Map<String, Object> info) {
        String DSJSJL_NAME = StringUtil.getString(info.get("DSJSJL_NAME"));
        String DSJSJL_IDNO = StringUtil.getString(info.get("DSJSJL_IDNO"));
        // 面签信息
        Map<String, Object> getSignInfo = getSignInfo(EXE_ID, DSJSJL_NAME, DSJSJL_IDNO);
        // 面签身份证
        String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
        setWordToImg(info, SIGN_IDPHOTO_FRONT, "DSJSJLPHOTOFRONT", "280", "180");
        // 面签身份证
        String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
        setWordToImg(info, SIGN_IDPHOTO_BACK, "DSJSJLPHOTOBACK", "280", "180");
    }

    /**
     * 
     * 描述 商事内资备案登记参数
     * 
     * @author Rider Chen
     * @created 2021年4月12日 上午10:16:10
     * @param busRecord
     */
    @SuppressWarnings("rawtypes")
    private void setCommercialSsnzqyba(Map<String, Object> busRecord) {
        try {
            setCommercialSsnzqybaCTSym(busRecord);

            String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
            String OPERATOR_NAME = StringUtil.getString(busRecord.get("OPERATOR_NAME"));
            String OPERATOR_IDNO = StringUtil.getString(busRecord.get("OPERATOR_IDNO"));
            // 经办人面签签名
            Map<String, Object> getSignInfo = getSignInfo(EXE_ID, OPERATOR_NAME, OPERATOR_IDNO);
            busRecord.put("OPERATOR_WRITE", "");
            String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(busRecord, SIGN_WRITE, "OPERATOR_WRITE", "100", "40");
            // 经办人面签身份证
            String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
            busRecord.put("OPERATORIDPHOTOFRONT", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "OPERATORIDPHOTOFRONT", "280", "180");
            // 经办人面签身份证
            String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
            busRecord.put("OPERATORIDPHOTOBACK", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_BACK, "OPERATORIDPHOTOBACK", "280", "180");

            String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));
            String LEGAL_IDNO = StringUtil.getString(busRecord.get("LEGAL_IDNO"));
            // 法定代表人面签签名
            getSignInfo = getSignInfo(EXE_ID, LEGAL_NAME, LEGAL_IDNO);
            busRecord.put("LEGAL_WRITE", "");
            SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(busRecord, SIGN_WRITE, "LEGAL_WRITE", "100", "40");
            // 法定代表人面签身份证
            SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
            busRecord.put("LEGALPHOTOFRONT", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "LEGALPHOTOFRONT", "280", "180");
            // 法定代表人面签身份证
            SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
            busRecord.put("LEGALPHOTOBACK", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_BACK, "LEGALPHOTOBACK", "280", "180");

            busRecord.put("SIGNTIME", getNewSignTime(EXE_ID));

            Set holderWrite = new HashSet();
            String HOLDER_JSON = StringUtil.getString(busRecord.get("HOLDER_JSON"));
            List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(HOLDER_JSON);
            StringBuffer holderNames = new StringBuffer();
            StringBuffer holderCcfp = new StringBuffer("");
            for (Map<String, Object> map : holderList) {
                String SHAREHOLDER_TYPE = StringUtil.getString(map.get("SHAREHOLDER_TYPE"));
                String SHAREHOLDER_NAME = StringUtil.getString(map.get("SHAREHOLDER_NAME"));
                String LICENCE_NO = StringUtil.getString(map.get("LICENCE_NO"));
                String LEGAL_PERSON = StringUtil.getString(map.get("LEGAL_PERSON"));
                String LEGAL_IDNO_PERSON = StringUtil.getString(map.get("LEGAL_IDNO_PERSON"));
                if (StringUtils.isNotEmpty(SHAREHOLDER_TYPE) && SHAREHOLDER_TYPE.equals("zrr")) {
                    getSignInfo = getSignInfo(EXE_ID, SHAREHOLDER_NAME, LICENCE_NO);
                } else {
                    getSignInfo = getSignInfo(EXE_ID, LEGAL_PERSON, LEGAL_IDNO_PERSON);
                }
                SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
                setWordToImg(holderWrite, SIGN_WRITE, "100", "40");
                holderNames.append(SHAREHOLDER_NAME).append("、");
                holderCcfp.append("股东").append(SHAREHOLDER_NAME).append("金额0元；");
            }
            busRecord.put("holderCcfp", holderCcfp.toString());
            busRecord.put("holderNames", holderNames.deleteCharAt(holderNames.length() - 1));
            busRecord.put("holderWrite", holderWrite);

            //加盖企业公章用印承诺书
            busRecord.put("COMPANY_WRITE", "");
            Map<String, Object> companyMap = dictionaryService.getByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                    new String[] { "EXE_ID","UPLOAD_SIGN" }, new Object[] { EXE_ID,"1" });
            if(companyMap!=null){
                if(StringUtils.isNotEmpty(StringUtil.getString(companyMap.get("LEGAL_FILEID")))){
                    Map<String, Object> fileInfo = dictionaryService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                            new String[] { "FILE_ID" }, new Object[] { companyMap.get("LEGAL_FILEID")});
                    String COMPANY_WRITE = StringUtil.getString(fileInfo.get("FILE_PATH"));
                    setWordToImg(busRecord, COMPANY_WRITE, "COMPANY_WRITE", "680", "580"); 
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("", e);
        }
    }

    /**
     * 
     * 描述 设置注销登记模版特殊符号
     * 
     * @author Rider Chen
     * @created 2021年3月31日 下午2:09:22
     * @param busRecord
     * @throws Exception
     */
    private void setCommercialSsnzqybaCTSym(Map<String, Object> busRecord) throws Exception {
        // 备案事项
        String BASX = StringUtil.getString(busRecord.get("BASX"));
        busRecord.put("BAXZ01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BAXZ02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BAXZ03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        String[] basxs = BASX.split(",");
        StringBuffer BAITEMNAME = new StringBuffer("");
        for (String baxz : basxs) {
            if (baxz.equals("1")) {
                busRecord.put("BAXZ01", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                BAITEMNAME.append("董事备案").append("、");
            } else if (baxz.equals("2")) {
                busRecord.put("BAXZ02", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                BAITEMNAME.append("监事备案").append("、");
            } else if (baxz.equals("3")) {
                busRecord.put("BAXZ03", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                BAITEMNAME.append("经理备案").append("、");
            }
        }
        busRecord.put("BAITEMNAME", BAITEMNAME.deleteCharAt(BAITEMNAME.length() - 1));
    }

    private void setOldList(Map<String, Object> busRecord, List<Object> yrgdhyList, List<Object> gdhyList,
            List<Object> dshyList, StringBuffer DSZNAMES) {
        String BASX = StringUtil.getString(busRecord.get("BASX"));
        String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));
        String[] basxs = BASX.split(",");
        String OLDDSZ = "";

        String OLD_DIRECTOR_JSON = busRecord.get("OLD_DIRECTOR_JSON") == null ? ""
                : busRecord.get("OLD_DIRECTOR_JSON").toString();
        List<Map<String, Object>> oldDirectorList = null;
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            oldDirectorList = JSON.parseObject(OLD_DIRECTOR_JSON, List.class);
            busRecord.put("dsrysl", oldDirectorList.size() + "");
            int FDSZSIZE = 0;
            // 替换董事字典数据
            for (Map<String, Object> map : oldDirectorList) {
                String DIRECTOR_JOB = (String) map.get("DIRECTOR_JOB");
                // 副董事长
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                    FDSZSIZE++;
                    busRecord.put("FGDSRMF", map.get("DIRECTOR_APPOINTOR"));
                }
            }
            if (FDSZSIZE > 0) {
                busRecord.put("fdszrysl", "副董事长" + FDSZSIZE + "人，");
            } else {
                busRecord.put("fdszrysl", "");
            }
        }
        String OLD_MANAGER_JSON = busRecord.get("OLD_MANAGER_JSON") == null ? ""
                : busRecord.get("OLD_MANAGER_JSON").toString();
        List<Map<String, Object>> oldManagerList = null;
        if (StringUtils.isNotEmpty(OLD_MANAGER_JSON)) {
            oldManagerList = JSON.parseObject(OLD_MANAGER_JSON, List.class);
        }
        String LEGAL_JOB = StringUtil.getString(busRecord.get("LEGAL_JOB"));
        String YFDBRZW = StringUtil.getString(busRecord.get("YFDBRZW"));
        setOldBasxList(busRecord, yrgdhyList, gdhyList, dshyList, DSZNAMES, BASX, LEGAL_NAME, basxs, OLDDSZ,
                oldDirectorList, oldManagerList, LEGAL_JOB, YFDBRZW);

        if (YFDBRZW.equals("20") && !LEGAL_JOB.equals("经理") && !BASX.contains("3")) {// 原法定代表人为经理且不进行经理备案进行变更
            for (Map<String, Object> map : oldManagerList) {
                String MANAGER_NAME = StringUtil.getString(map.get("MANAGER_NAME"));
                // 职务
                getDicName(map, "ssdjzw", "MANAGER_JOB");
                StringBuffer yrgdjd = new StringBuffer("    " + StringUtil.convertInteger(yrgdhyList.size() + 1) + "、解聘"
                        + MANAGER_NAME + map.get("MANAGER_JOB"));
                StringBuffer gdhy = new StringBuffer("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、解聘"
                        + MANAGER_NAME + map.get("MANAGER_JOB"));
                StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、解聘"
                        + MANAGER_NAME + map.get("MANAGER_JOB"));
                if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(MANAGER_NAME) && YFDBRZW.equals("20")) {
                    yrgdjd.append("兼法定代表人职务。");
                    gdhy.append("兼法定代表人职务。");
                    dshy.append("兼法定代表人职务。");

                    if (null != oldDirectorList && oldDirectorList.size() == 1) {// 旧董事是董事会的情况下，股东会议与决定不显示经理
                        yrgdhyList.add(yrgdjd.toString());
                        gdhyList.add(gdhy.toString());
                    } else {
                        dshyList.add(dshy.toString());
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 设置备案信息
     * 
     * @author Rider Chen
     * @created 2021年5月18日 下午5:28:50
     * @param busRecord
     * @param yrgdhyList
     * @param gdhyList
     * @param dshyList
     * @param DSZNAMES
     * @param BASX
     * @param LEGAL_NAME
     * @param basxs
     * @param OLDDSZ
     * @param oldDirectorList
     * @param oldManagerList
     * @param LEGAL_JOB
     * @param YFDBRZW
     */
    private void setOldBasxList(Map<String, Object> busRecord, List<Object> yrgdhyList, List<Object> gdhyList,
            List<Object> dshyList, StringBuffer DSZNAMES, String BASX, String LEGAL_NAME, String[] basxs, String OLDDSZ,
            List<Map<String, Object>> oldDirectorList, List<Map<String, Object>> oldManagerList, String LEGAL_JOB,
            String YFDBRZW) {
        if (((YFDBRZW.equals("01") && !LEGAL_JOB.equals("董事长")) || (YFDBRZW.equals("30") && !LEGAL_JOB.equals("执行董事")))
                && !BASX.contains("1")) {// 原法定代表人为董事长或者执行董事且不进行董事备案进行变更
            for (Map<String, Object> map : oldDirectorList) {
                String DIRECTOR_NAME = StringUtil.getString(map.get("DIRECTOR_NAME"));
                String DIRECTOR_JOB = StringUtil.getString(map.get("DIRECTOR_JOB"));
                // 职务
                getDicName(map, "ssdjzw", "DIRECTOR_JOB");
                if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                        && (map.get("DIRECTOR_JOB").equals("董事长") || map.get("DIRECTOR_JOB").equals("执行董事"))) {
                    StringBuffer yrgdjd = new StringBuffer("    " + StringUtil.convertInteger(yrgdhyList.size() + 1)
                            + "、免去" + DIRECTOR_NAME + map.get("DIRECTOR_JOB"));
                    StringBuffer gdhy = new StringBuffer("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、免去"
                            + DIRECTOR_NAME + map.get("DIRECTOR_JOB"));
                    StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、免去"
                            + DIRECTOR_NAME + map.get("DIRECTOR_JOB"));
                    if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                            && (YFDBRZW.equals("01") || YFDBRZW.equals("30"))) {
                        dshy.append("兼法定代表人职务。");
                        if (DIRECTOR_JOB.equals("01")) {
                            dshyList.add(dshy.toString());
                        } else {
                            yrgdjd.append("兼法定代表人职务。");
                            gdhy.append("兼法定代表人职务。");
                            yrgdhyList.add(yrgdjd.toString());
                            gdhyList.add(gdhy.toString());
                        }
                    }
                }
            }
        }
        for (String baxz : basxs) {
            if (baxz.equals("1")) {
                if (null != oldDirectorList && oldDirectorList.size() > 0) {
                    // 按职务对董事进行
                    Collections.sort(oldDirectorList, new Comparator<Map<String, Object>>() {
                        public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                            double sum0 = Double.parseDouble(arg0.get("DIRECTOR_JOB").toString());
                            double sum1 = Double.parseDouble(arg1.get("DIRECTOR_JOB").toString());
                            if (sum1 > sum0) {
                                return -1;
                            } else if (sum1 == sum0) {
                                return 0;
                            } else {
                                return 1;
                            }
                        }
                    });
                    for (Map<String, Object> map : oldDirectorList) {
                        String DIRECTOR_NAME = StringUtil.getString(map.get("DIRECTOR_NAME"));
                        String DIRECTOR_JOB = StringUtil.getString(map.get("DIRECTOR_JOB"));
                        // 董事长
                        if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("01")) {
                            if (DSZNAMES.indexOf(DIRECTOR_NAME) < 0) {
                                DSZNAMES.append(DIRECTOR_NAME).append("、");
                            }
                            OLDDSZ = DIRECTOR_NAME;
                        }
                        // 执行董事
                        if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("30")) {
                            if (DSZNAMES.indexOf(DIRECTOR_NAME) < 0) {
                                DSZNAMES.append(DIRECTOR_NAME).append("、");
                            }
                        }

                        // 职务
                        getDicName(map, "ssdjzw", "DIRECTOR_JOB");
                        StringBuffer yrgdjd = new StringBuffer(
                                "    " + StringUtil.convertInteger(yrgdhyList.size() + 1) + "、免去" + DIRECTOR_NAME);
                        StringBuffer gdhy = new StringBuffer(
                                "    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、免去" + DIRECTOR_NAME);
                        StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1)
                                + "、免去" + DIRECTOR_NAME + map.get("DIRECTOR_JOB"));
                        if (DIRECTOR_JOB.equals("01") || DIRECTOR_JOB.equals("02")) {
                            yrgdjd.append("董事");
                            gdhy.append("董事");
                        } else {
                            yrgdjd.append(map.get("DIRECTOR_JOB"));
                            gdhy.append(map.get("DIRECTOR_JOB"));
                        }
                        if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                                && (map.get("DIRECTOR_JOB").equals("董事长") || map.get("DIRECTOR_JOB").equals("执行董事"))
                                && (YFDBRZW.equals("01") || YFDBRZW.equals("30"))) {
                            if (!DIRECTOR_JOB.equals("01") && !DIRECTOR_JOB.equals("02")) {
                                yrgdjd.append("兼法定代表人");
                                gdhy.append("兼法定代表人");
                            }
                            dshy.append("兼法定代表人");
                        }
                        yrgdjd.append("职务。");
                        gdhy.append("职务。");
                        dshy.append("职务。");

                        yrgdhyList.add(yrgdjd.toString());
                        gdhyList.add(gdhy.toString());

                        if (DIRECTOR_JOB.equals("01") || DIRECTOR_JOB.equals("02")) {// 董事会决议只体现董事长和副董事长的任免
                            dshyList.add(dshy.toString());
                        }
                    }
                }
            } else if (baxz.equals("2")) {
                String OLD_SUPERVISOR_JSON = busRecord.get("OLD_SUPERVISOR_JSON") == null ? ""
                        : busRecord.get("OLD_SUPERVISOR_JSON").toString();
                if (StringUtils.isNotEmpty(OLD_SUPERVISOR_JSON)) {
                    List<Map<String, Object>> oldList = JSON.parseObject(OLD_SUPERVISOR_JSON, List.class);
                    for (Map<String, Object> map : oldList) {
                        String SUPERVISOR_NAME = StringUtil.getString(map.get("SUPERVISOR_NAME"));
                        // 职务
                        getDicName(map, "ssdjzw", "SUPERVISOR_JOB");
                        yrgdhyList.add("    " + StringUtil.convertInteger(yrgdhyList.size() + 1) + "、免去"
                                + SUPERVISOR_NAME + map.get("SUPERVISOR_JOB") + "职务。");
                        gdhyList.add("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、免去" + SUPERVISOR_NAME
                                + map.get("SUPERVISOR_JOB") + "职务。");
                        // dshyList.add(StringUtil.convertInteger(dshyList.size()+1)+"、免去"+SUPERVISOR_NAME+map.get("SUPERVISOR_JOB")+"职务。");
                    }

                }
            } else if (baxz.equals("3")) {
                if (null != oldManagerList && oldManagerList.size() > 0) {
                    for (Map<String, Object> map : oldManagerList) {
                        String MANAGER_NAME = StringUtil.getString(map.get("MANAGER_NAME"));
                        // 职务
                        getDicName(map, "ssdjzw", "MANAGER_JOB");
                        StringBuffer yrgdjd = new StringBuffer("    " + StringUtil.convertInteger(yrgdhyList.size() + 1)
                                + "、解聘" + MANAGER_NAME + map.get("MANAGER_JOB"));
                        StringBuffer gdhy = new StringBuffer("    " + StringUtil.convertInteger(gdhyList.size() + 1)
                                + "、解聘" + MANAGER_NAME + map.get("MANAGER_JOB"));
                        StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1)
                                + "、解聘" + MANAGER_NAME + map.get("MANAGER_JOB"));
                        if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(MANAGER_NAME)
                                && YFDBRZW.equals("20")) {
                            yrgdjd.append("兼法定代表人");
                            gdhy.append("兼法定代表人");
                            dshy.append("兼法定代表人");
                        }
                        yrgdjd.append("职务。");
                        gdhy.append("职务。");
                        dshy.append("职务。");
                        if (null != oldDirectorList && oldDirectorList.size() == 1) {// 旧董事是董事会的情况下，股东会议与决定不显示经理
                            yrgdhyList.add(yrgdjd.toString());
                            gdhyList.add(gdhy.toString());
                        } else {
                            dshyList.add(dshy.toString());
                        }

                    }
                }
            }
        }

        busRecord.put("OLDDSZ", OLDDSZ);
    }

    /**
     * 
     * 描述 设置备案董事信息
     * 
     * @author Rider Chen
     * @created 2021年4月12日 下午2:07:01
     * @param busRecord
     * @param LEGAL_NAME
     * @param gdhyjyList
     * @param dshyjyList
     */
    @SuppressWarnings("rawtypes")
    private void setSsnzqybaDirector(Map<String, Object> busRecord, StringBuffer DSZNAMES, List<Object> yrgdjdList,
            List<Object> gdhyList, List<Object> dshyList) {

        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));
        String LEGAL_JOB = StringUtil.getString(busRecord.get("LEGAL_JOB"));
        String OLDDSZ = StringUtil.getString(busRecord.get("OLDDSZ"));
        // 董事集合
        StringBuffer DIRECTORNAMES = new StringBuffer("");
        String gdhyname = "董事会";
        Set newDirectorWrite = new HashSet();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            DSZNAMES = new StringBuffer();// 董事会备案时只显示新董事，不做董事备案显示旧董事
            // 删除不在表中存在的字段
            directorList = removeMapKey(directorList, "T_COMMERCIAL_DIRECTOR");
            busRecord.put("directorList", directorList);
            // 按职务对董事进行
            Collections.sort(directorList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    double sum0 = Double.parseDouble(arg0.get("DIRECTOR_JOB").toString());
                    double sum1 = Double.parseDouble(arg1.get("DIRECTOR_JOB").toString());
                    if (sum1 > sum0) {
                        return -1;
                    } else if (sum1 == sum0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            int num = 0; busRecord.put("dsrysl", directorList.size() + "");
            int FDSZSIZE = 0;
            // 替换董事字典数据
            for (Map<String, Object> map : directorList) {
                // 董事名称
                String DIRECTOR_NAME = map.get("DIRECTOR_NAME") == null ? "" : map.get("DIRECTOR_NAME").toString();
                String DIRECTOR_JOB = (String) map.get("DIRECTOR_JOB");
                // 副董事长
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                    FDSZSIZE++;
                    busRecord.put("FGDSRMF", map.get("DIRECTOR_APPOINTOR"));
                }
                // 职务
                getDicName(map, "ssdjzw", "DIRECTOR_JOB");
                // 证件类型
                getDicName(map, "DocumentType", "DIRECTOR_IDTYPE");
                // 产生方式
                getDicName(map, "ssdjcsfs", "DIRECTOR_GENERATION_MODE");
                // 董事长
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("01")) {
                    if (DSZNAMES.indexOf(DIRECTOR_NAME) < 0) {
                        DSZNAMES.append(DIRECTOR_NAME).append("、");
                    }
                    StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、选举"
                            + DIRECTOR_NAME + "为公司" + map.get("DIRECTOR_JOB"));
                    if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                            && map.get("DIRECTOR_JOB").toString().equals(LEGAL_JOB)) {
                        dshy.append("兼法定代表人");
                    }
                    dshy.append("，任期三年。");
                    dshyList.add(dshy.toString());
                    OLDDSZ = DIRECTOR_NAME;
                } else if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                    dshyList.add("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、选举" + DIRECTOR_NAME + "为公司"
                            + map.get("DIRECTOR_JOB") + "，任期三年。");
                }
                // 执行董事
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("30")) {
                    gdhyname = "执行董事";
                    if (DSZNAMES.indexOf(DIRECTOR_NAME) < 0) {
                        DSZNAMES.append(DIRECTOR_NAME).append("、");
                    }
                } else {
                    gdhyname = "董事会";
                }

                StringBuffer yrgdjd = new StringBuffer(
                        "    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、重新委派" + DIRECTOR_NAME + "为公司");
                StringBuffer gdhy = new StringBuffer(
                        "    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、重新选举" + DIRECTOR_NAME + "为公司");
                if (DIRECTOR_JOB.equals("01") || DIRECTOR_JOB.equals("02")) {
                    yrgdjd.append("董事");
                    gdhy.append("董事");
                } else {
                    yrgdjd.append(map.get("DIRECTOR_JOB"));
                    gdhy.append(map.get("DIRECTOR_JOB"));
                }
                DIRECTOR_JOB = (String) map.get("DIRECTOR_JOB");
                if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                        && DIRECTOR_JOB.equals(LEGAL_JOB)) {
                    if (!DIRECTOR_JOB.equals("董事长") && !DIRECTOR_JOB.equals("副董事长")) {
                        yrgdjd.append("兼法定代表人");
                        gdhy.append("兼法定代表人");
                    }
                }
                yrgdjd.append("，任期三年。");
                gdhy.append("，任期三年。");
                yrgdjdList.add(yrgdjd.toString());
                gdhyList.add(gdhy.toString());

                DIRECTORNAMES.append(DIRECTOR_NAME).append("、");
                map.put("DIRECTORPHOTOFRONT", "");
                map.put("DIRECTORPHOTOBACK", "");
                String DIRECTOR_IDNO = StringUtil.getString(map.get("DIRECTOR_IDNO"));
                // 董事面签信息
                Map<String, Object> getSignInfo = getSignInfo(EXE_ID, DIRECTOR_NAME, DIRECTOR_IDNO);
                if (num == 0) {
                    // 董事面签身份证
                    String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
                    setWordToImg(map, SIGN_IDPHOTO_FRONT, "DIRECTORPHOTOFRONT", "280", "180");
                    // 董事面签身份证
                    String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
                    setWordToImg(map, SIGN_IDPHOTO_BACK, "DIRECTORPHOTOBACK", "280", "180");
                }
                String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? ""
                        : getSignInfo.get("SIGN_WRITE").toString();
                setWordToImg(newDirectorWrite, SIGN_WRITE, "100", "40");
                num++;
            }
            if (FDSZSIZE > 0) {
                busRecord.put("fdszrysl", "副董事长" + FDSZSIZE + "人，");
            } else {
                busRecord.put("fdszrysl", "");
            }
        }
        if (StringUtils.isNotEmpty(OLDDSZ)) {
            busRecord.put("OLDDSZ", OLDDSZ);
        }
        busRecord.put("newDirectorWrite", newDirectorWrite);
        DIRECTORNAMES = setOldDirector(busRecord, yrgdjdList, gdhyList, dshyList, DIRECTOR_JSON, LEGAL_NAME, LEGAL_JOB,
                DIRECTORNAMES);
        if (StringUtils.isNotEmpty(DIRECTORNAMES)) {
            DIRECTORNAMES = DIRECTORNAMES.deleteCharAt(DIRECTORNAMES.length() - 1);
        }
        busRecord.put("DIRECTORNAMES", DIRECTORNAMES);
        busRecord.put("gdhyname", gdhyname);
    }

    /**
     * 
     * 描述 设置旧董事
     * 
     * @author Rider Chen
     * @created 2021年5月11日 下午3:27:53
     * @param busRecord
     * @param yrgdjdList
     * @param gdhyList
     * @param dshyList
     * @param DIRECTOR_JSON
     * @param LEGAL_NAME
     * @param LEGAL_JOB
     * @param DIRECTORNAMES
     * @return
     */
    @SuppressWarnings("rawtypes")
    private StringBuffer setOldDirector(Map<String, Object> busRecord, List<Object> yrgdjdList, List<Object> gdhyList,
            List<Object> dshyList, String DIRECTOR_JSON, String LEGAL_NAME, String LEGAL_JOB,
            StringBuffer DIRECTORNAMES) {
        String OLD_DIRECTOR_JSON = busRecord.get("OLD_DIRECTOR_JSON") == null ? ""
                : busRecord.get("OLD_DIRECTOR_JSON").toString();
        List<Map<String, Object>> directorList = null;
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            if (null != directorList && directorList.size() == 1) {
                DIRECTORNAMES = new StringBuffer("");
            }
        }
        List<Map<String, Object>> oldDirectorList = null;
        Set newDirectorWrite = new HashSet();
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            oldDirectorList = JSON.parseObject(OLD_DIRECTOR_JSON, List.class);
            String BASX = StringUtil.getString(busRecord.get("BASX"));
            String YFDBRZW = StringUtil.getString(busRecord.get("YFDBRZW"));
            for (Map<String, Object> map : oldDirectorList) {
                String DIRECTOR_NAME = StringUtil.getString(map.get("DIRECTOR_NAME"));
                String DIRECTOR_JOB = StringUtil.getString(map.get("DIRECTOR_JOB"));

                if (StringUtils.isEmpty(DIRECTOR_JSON)) {
                    DIRECTORNAMES.append(DIRECTOR_NAME).append("、");
                } else if (null != directorList && directorList.size() == 1) {
                    DIRECTORNAMES.append(DIRECTOR_NAME).append("、");
                }
                // 职务
                getDicName(map, "ssdjzw", "DIRECTOR_JOB");
                if (((LEGAL_JOB.equals("董事长") && !YFDBRZW.equals("01"))
                        || (LEGAL_JOB.equals("执行董事") && !YFDBRZW.equals("30")))
                        && ((map.get("DIRECTOR_JOB").equals("董事长")) || map.get("DIRECTOR_JOB").equals("执行董事"))
                        && !BASX.contains("1")) {// 法定代表人为董事长或者执行董事不进行董事备案,且原法定代表人职务不为董事长或者执行董事
                    StringBuffer yrgdjd = new StringBuffer(
                            "    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、重新委派" + DIRECTOR_NAME + "为公司");
                    StringBuffer gdhy = new StringBuffer(
                            "    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、重新选举" + DIRECTOR_NAME + "为公司");
                    if (DIRECTOR_JOB.equals("01") || DIRECTOR_JOB.equals("02")) {
                        yrgdjd.append("董事");
                        gdhy.append("董事");
                    } else {
                        yrgdjd.append(map.get("DIRECTOR_JOB"));
                        gdhy.append(map.get("DIRECTOR_JOB"));
                    }
                    StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、选举"
                            + DIRECTOR_NAME + "为公司" + map.get("DIRECTOR_JOB"));
                    if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)) {
                        if (!DIRECTOR_JOB.equals("01") && !DIRECTOR_JOB.equals("02")) {
                            yrgdjd.append("兼法定代表人职务");
                            gdhy.append("兼法定代表人职务");
                        }
                        yrgdjd.append("，任期三年。");
                        gdhy.append("，任期三年。");
                        dshy.append("兼法定代表人职务，任期三年。");
                        yrgdjdList.add(yrgdjd.toString());
                        gdhyList.add(gdhy.toString());
                        dshyList.add(dshy.toString());
                    }
                } else if ((YFDBRZW.equals("01")
                        || YFDBRZW.equals("30"))&&LEGAL_JOB.equals("经理")
                        && ((map.get("DIRECTOR_JOB").equals("董事长")) || map.get("DIRECTOR_JOB").equals("执行董事"))
                        && !BASX.contains("1")) {// 法定代表人为董事长或者执行董事不进行董事备案,且原法定代表人职务为董事长或者执行董事变更为经理
                    StringBuffer yrgdjd = new StringBuffer(
                            "    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、重新委派" + DIRECTOR_NAME + "为公司");
                    StringBuffer gdhy = new StringBuffer(
                            "    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、重新选举" + DIRECTOR_NAME + "为公司");
                    if (DIRECTOR_JOB.equals("01") || DIRECTOR_JOB.equals("02")) {
                        yrgdjd.append("董事");
                        gdhy.append("董事");
                    } else {
                        yrgdjd.append(map.get("DIRECTOR_JOB"));
                        gdhy.append(map.get("DIRECTOR_JOB"));
                    }
                    StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、重新选举"
                            + DIRECTOR_NAME + "为公司" + map.get("DIRECTOR_JOB"));
                    yrgdjd.append("，任期三年。");
                    gdhy.append("，任期三年。");
                    dshy.append("，任期三年。");
                    yrgdjdList.add(yrgdjd.toString());
                    gdhyList.add(gdhy.toString());
                    dshyList.add(dshy.toString());
                }
                
                String DIRECTOR_IDNO = StringUtil.getString(map.get("DIRECTOR_IDNO"));
                // 董事面签信息
                Map<String, Object> getSignInfo = getSignInfo(EXE_ID, DIRECTOR_NAME, DIRECTOR_IDNO);
                String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? ""
                        : getSignInfo.get("SIGN_WRITE").toString();
                setWordToImg(newDirectorWrite, SIGN_WRITE, "100", "40");
            }
        }
        if (StringUtils.isEmpty(DIRECTOR_JSON) || (null != directorList && directorList.size() == 1)) {
            busRecord.put("newDirectorWrite", newDirectorWrite);
        }
        return DIRECTORNAMES;
    }

    /**
     * 
     * 描述 设置备案监事信息
     * 
     * @author Rider Chen
     * @created 2021年4月13日 上午10:00:29
     * @param busRecord
     * @param LEGAL_NAME
     * @param yrgdjdList
     * @param gdhyList
     * @param dshyList
     */
    private void setSsnzqybaSupervisor(Map<String, Object> busRecord, String LEGAL_NAME, List<Object> yrgdjdList,
            List<Object> gdhyList, List<Object> dshyList) {

        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            busRecord.put("jsrysl", supervisorList.size() + "");
            // 删除不在表中存在的字段
            supervisorList = removeMapKey(supervisorList, "T_COMMERCIAL_SUPERVISOR");
            busRecord.put("supervisorList", supervisorList);
            StringBuffer JSNAMES = new StringBuffer("");
            String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
            int num = 0;
            // 替换监理字典数据
            for (Map<String, Object> map : supervisorList) {
                // 职务
                getDicName(map, "ssdjzw", "SUPERVISOR_JOB");
                // 证件类型
                getDicName(map, "DocumentType", "SUPERVISOR_IDTYPE");
                // 产生方式
                getDicName(map, "ssdjcsfs", "SUPERVISOR_GENERATION_MODE");
                String SUPERVISOR_NAME = map.get("SUPERVISOR_NAME") == null ? ""
                        : map.get("SUPERVISOR_NAME").toString();
                JSNAMES.append(SUPERVISOR_NAME).append("、");

                yrgdjdList.add("    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、重新委派" + SUPERVISOR_NAME
                        + "为公司" + map.get("SUPERVISOR_JOB") + "，任期三年。");
                gdhyList.add("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、重新选举" + SUPERVISOR_NAME + "为公司"
                        + map.get("SUPERVISOR_JOB") + "，任期三年。");
                map.put("SUPERVISORPHOTOFRONT", "");
                map.put("SUPERVISORPHOTOBACK", "");
                if (num == 0) {
                    String SUPERVISOR_IDNO = StringUtil.getString(map.get("SUPERVISOR_IDNO"));
                    // 监事面签信息
                    Map<String, Object> getSignInfo = getSignInfo(EXE_ID, SUPERVISOR_NAME, SUPERVISOR_IDNO);
                    // 监事面签身份证
                    String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
                    setWordToImg(map, SIGN_IDPHOTO_FRONT, "SUPERVISORPHOTOFRONT", "280", "180");
                    // 监事面签身份证
                    String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
                    setWordToImg(map, SIGN_IDPHOTO_BACK, "SUPERVISORPHOTOBACK", "280", "180");

                }
                num++;
            }
            // 监事信息
            if (StringUtils.isNotEmpty(JSNAMES)) {
                JSNAMES = JSNAMES.deleteCharAt(JSNAMES.length() - 1);
            }
            busRecord.put("JSNAMES", JSNAMES);
        }

    }

    /**
     * 
     * 描述 设置备案经理信息
     * 
     * @author Rider Chen
     * @created 2021年4月13日 上午10:00:18
     * @param busRecord
     * @param LEGAL_NAME
     * @param yrgdjdList
     * @param gdhyList
     * @param dshyList
     */
    private void setSsnzqybaManager(Map<String, Object> busRecord, String LEGAL_NAME, List<Object> yrgdjdList,
            List<Object> gdhyList, List<Object> dshyList) {

        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        String BASX = StringUtil.getString(busRecord.get("BASX"));
        String OLD_DIRECTOR_JSON = busRecord.get("OLD_DIRECTOR_JSON") == null ? ""
                : busRecord.get("OLD_DIRECTOR_JSON").toString();
        List<Map<String, Object>> oldDirectorList = null;
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            oldDirectorList = JSON.parseObject(OLD_DIRECTOR_JSON, List.class);
        }
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? ""
                : busRecord.get("DIRECTOR_JSON").toString();
        List<Map<String, Object>> directorList = null;
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
        }
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {

            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            // 删除不在表中存在的字段
            managerList = removeMapKey(managerList, "T_COMMERCIAL_MANAGER");
            busRecord.put("managerList", managerList);
            StringBuffer JLNAMES = new StringBuffer("");
            String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
            String LEGAL_JOB = StringUtil.getString(busRecord.get("LEGAL_JOB"));
            int num = 0;
            // 替换经理字典数据
            for (Map<String, Object> map : managerList) {
                // 职务
                getDicName(map, "ssdjzw", "MANAGER_JOB");
                // 证件类型
                getDicName(map, "DocumentType", "MANAGER_IDTYPE");
                // 产生方式
                getDicName(map, "ssdjcsfs", "MANAGER_GENERATION_MODE");
                String MANAGER_NAME = map.get("MANAGER_NAME") == null ? "" : map.get("MANAGER_NAME").toString();
                JLNAMES.append(MANAGER_NAME).append("，");
                StringBuffer yrgdjd = new StringBuffer("    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                StringBuffer gdhy = new StringBuffer("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                String MANAGER_JOB = (String) map.get("MANAGER_JOB");
                if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(MANAGER_NAME)
                        && MANAGER_JOB.equals(LEGAL_JOB)) {
                    yrgdjd.append("兼法定代表人");
                    gdhy.append("兼法定代表人");
                    dshy.append("兼法定代表人");
                }
                yrgdjd.append("，任期三年。");
                gdhy.append("，任期三年。");
                dshy.append("，任期三年。");
                // 新董事是董事会的情况下，股东会议与决定不显示经理聘任
                if (null != directorList && directorList.size() == 1) {
                    yrgdjdList.add(yrgdjd.toString());
                    gdhyList.add(gdhy.toString());
                } else if (null != directorList && directorList.size() > 1) {
                    dshyList.add(dshy.toString());
                }

                map.put("MANAGERPHOTOFRONT", "");
                map.put("MANAGERPHOTOBACK", "");
                if (num == 0) {
                    String MANAGER_IDNO = StringUtil.getString(map.get("MANAGER_IDNO"));
                    // 经理面签信息
                    Map<String, Object> getSignInfo = getSignInfo(EXE_ID, MANAGER_NAME, MANAGER_IDNO);
                    // 经理面签身份证
                    String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
                    setWordToImg(map, SIGN_IDPHOTO_FRONT, "MANAGERPHOTOFRONT", "280", "180");
                    // 经理面签身份证
                    String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
                    setWordToImg(map, SIGN_IDPHOTO_BACK, "MANAGERPHOTOBACK", "280", "180");
                }
                num++;
            }
            // 经理信息
            if (StringUtils.isNotEmpty(JLNAMES)) {
                JLNAMES = JLNAMES.deleteCharAt(JLNAMES.length() - 1);
            }
            busRecord.put("JSNAMES", JLNAMES);
        }

        String OLD_MANAGER_JSON = busRecord.get("OLD_MANAGER_JSON") == null ? ""
                : busRecord.get("OLD_MANAGER_JSON").toString();
        List<Map<String, Object>> oldManagerList = null;
        if (StringUtils.isNotEmpty(OLD_MANAGER_JSON)) {
            oldManagerList = JSON.parseObject(OLD_MANAGER_JSON, List.class);
        }
        String LEGAL_JOB = StringUtil.getString(busRecord.get("LEGAL_JOB"));
        String YFDBRZW = StringUtil.getString(busRecord.get("YFDBRZW"));
        if (!YFDBRZW.equals("20") && LEGAL_JOB.equals("经理") && !BASX.contains("3")) {// 法定代表人为经理不进行经理备案，且原法定代表人职务不为经理时
            for (Map<String, Object> map : oldManagerList) {
                String MANAGER_NAME = StringUtil.getString(map.get("MANAGER_NAME"));
                // 职务
                getDicName(map, "ssdjzw", "MANAGER_JOB");
                StringBuffer yrgdjd = new StringBuffer("    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                StringBuffer gdhy = new StringBuffer("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(MANAGER_NAME)) {
                    yrgdjd.append("兼法定代表人职务，任期三年。");
                    gdhy.append("兼法定代表人职务，任期三年。");
                    dshy.append("兼法定代表人职务，任期三年。");
                    if ((null != oldDirectorList && oldDirectorList.size() == 1 && !BASX.contains("1"))
                            || (null != directorList && directorList.size() == 1 && BASX.contains("1"))) {// 旧董事不为董事会时，股东决定与会议不显示经理聘任
                        yrgdjdList.add(yrgdjd.toString());
                        gdhyList.add(gdhy.toString());
                    } else {
                        dshyList.add(dshy.toString());
                    }

                }
            }
        } else if (YFDBRZW.equals("20") && !LEGAL_JOB.equals("经理") && !BASX.contains("3")) {// 法定代表人为经理不进行经理备案，且原法定代表人职务为经理变更为董事长或者执行董事时
            for (Map<String, Object> map : oldManagerList) {
                String MANAGER_NAME = StringUtil.getString(map.get("MANAGER_NAME"));
                // 职务
                getDicName(map, "ssdjzw", "MANAGER_JOB");
                StringBuffer yrgdjd = new StringBuffer("    " + StringUtil.convertInteger(yrgdjdList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                StringBuffer gdhy = new StringBuffer("    " + StringUtil.convertInteger(gdhyList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                StringBuffer dshy = new StringBuffer("    " + StringUtil.convertInteger(dshyList.size() + 1) + "、聘任"
                        + MANAGER_NAME + "为公司" + map.get("MANAGER_JOB"));
                yrgdjd.append("，任期三年。");
                gdhy.append("，任期三年。");
                dshy.append("，任期三年。");
                if ((null != oldDirectorList && oldDirectorList.size() == 1 && !BASX.contains("1"))
                        || (null != directorList && directorList.size() == 1 && BASX.contains("1"))) {// 旧董事不为董事会时，股东决定与会议不显示经理聘任
                    yrgdjdList.add(yrgdjd.toString());
                    gdhyList.add(gdhy.toString());
                } else {
                    dshyList.add(dshy.toString());
                }
            }
        }
    }

    /**
     * 
     * 描述 商事内资注销登记参数
     * 
     * @author Rider Chen
     * @created 2021年3月30日 下午5:57:29
     * @param busRecord
     */
    @SuppressWarnings("rawtypes")
    private void setCommercialCancel(Map<String, Object> busRecord) {
        try {
            setCommercialCancelCTSym(busRecord);

            String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
            String OPERATOR_NAME = StringUtil.getString(busRecord.get("OPERATOR_NAME"));
            String OPERATOR_IDNO = StringUtil.getString(busRecord.get("OPERATOR_IDNO"));
            // 经办人面签签名
            Map<String, Object> getSignInfo = getSignInfo(EXE_ID, OPERATOR_NAME, OPERATOR_IDNO);
            busRecord.put("OPERATOR_WRITE", "");
            String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(busRecord, SIGN_WRITE, "OPERATOR_WRITE", "100", "40");
            // 经办人面签身份证
            String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
            busRecord.put("OPERATORIDPHOTOFRONT", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "OPERATORIDPHOTOFRONT", "280", "180");
            // 经办人面签身份证
            String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
            busRecord.put("OPERATORIDPHOTOBACK", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_BACK, "OPERATORIDPHOTOBACK", "280", "180");

            String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));
            String LEGAL_IDNO = StringUtil.getString(busRecord.get("LEGAL_IDNO"));
            // 法定代表人面签签名
            getSignInfo = getSignInfo(EXE_ID, LEGAL_NAME, LEGAL_IDNO);
            busRecord.put("LEGAL_WRITE", "");
            SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(busRecord, SIGN_WRITE, "LEGAL_WRITE", "100", "40");
            busRecord.put("SIGNTIME", getNewSignTime(EXE_ID));
            String NOTICE_START_DATE = busRecord.get("NOTICE_START_DATE") == null ? ""
                    : busRecord.get("NOTICE_START_DATE").toString();
            if (StringUtils.isNotEmpty(NOTICE_START_DATE)) {
                busRecord.put("NOTICE_START_DATE",
                        DateTimeUtil.formatDateStr(NOTICE_START_DATE, "yyyy-MM-dd", "yyyy年MM月dd日"));
            }
            String NOTICE_END_DATE = busRecord.get("NOTICE_END_DATE") == null ? ""
                    : busRecord.get("NOTICE_END_DATE").toString();
            if (StringUtils.isNotEmpty(NOTICE_END_DATE)) {
                busRecord.put("NOTICE_END_DATE",
                        DateTimeUtil.formatDateStr(NOTICE_END_DATE, "yyyy-MM-dd", "yyyy年MM月dd日"));
            }
            busRecord.put("QSZCY_WRITE", "");
            String QSZCY_JSON = StringUtil.getString(busRecord.get("QSZCY_JSON"));
            Set qszcyWrite = new HashSet();
            if (StringUtils.isNotEmpty(QSZCY_JSON)) {
                List<Map<String, Object>> qszcyList = (List<Map<String, Object>>) JSON.parse(QSZCY_JSON);
                for (Map<String, Object> map : qszcyList) {
                    String QSZ_JOB = StringUtil.getString(map.get("QSZ_JOB"));
                    String QSZ_NAME = StringUtil.getString(map.get("QSZ_NAME"));
                    String QSZ_IDNO = StringUtil.getString(map.get("QSZ_IDNO"));
                    getSignInfo = getSignInfo(EXE_ID, QSZ_NAME, QSZ_IDNO);
                    SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? ""
                            : getSignInfo.get("SIGN_WRITE").toString();
                    if (StringUtils.isNotEmpty(QSZ_JOB) && QSZ_JOB.equals("1")) {// 清算组成员负责人
                        // 清算组成员负责人面签签名
                        setWordToImg(busRecord, SIGN_WRITE, "QSZCY_WRITE", "100", "40");
                    }
                    setWordToImg(qszcyWrite, SIGN_WRITE, "100", "40");
                }
            }
            busRecord.put("qszcyWrite", qszcyWrite);

            Set holderWrite = new HashSet();
            String HOLDER_JSON = StringUtil.getString(busRecord.get("HOLDER_JSON"));
            StringBuffer holderNames = new StringBuffer();
            StringBuffer holderCcfp = new StringBuffer("");
            if (StringUtils.isNotEmpty(HOLDER_JSON)) {
                List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(HOLDER_JSON);
                for (Map<String, Object> map : holderList) {
                    String SHAREHOLDER_TYPE = StringUtil.getString(map.get("SHAREHOLDER_TYPE"));
                    String SHAREHOLDER_NAME = StringUtil.getString(map.get("SHAREHOLDER_NAME"));
                    String LICENCE_NO = StringUtil.getString(map.get("LICENCE_NO"));
                    String LEGAL_PERSON = StringUtil.getString(map.get("LEGAL_PERSON"));
                    String LEGAL_IDNO_PERSON = StringUtil.getString(map.get("LEGAL_IDNO_PERSON"));
                    if (StringUtils.isNotEmpty(SHAREHOLDER_TYPE) && SHAREHOLDER_TYPE.equals("zrr")) {
                        getSignInfo = getSignInfo(EXE_ID, SHAREHOLDER_NAME, LICENCE_NO);
                    } else {
                        getSignInfo = getSignInfo(EXE_ID, LEGAL_PERSON, LEGAL_IDNO_PERSON);
                    }
                    SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? ""
                            : getSignInfo.get("SIGN_WRITE").toString();
                    setWordToImg(holderWrite, SIGN_WRITE, "100", "40");
                    holderNames.append(SHAREHOLDER_NAME).append("、");
                    holderCcfp.append("股东").append(SHAREHOLDER_NAME).append("金额0元；");
                }
                holderNames = holderNames.deleteCharAt(holderNames.length() - 1);
            }
            busRecord.put("holderCcfp", holderCcfp.toString());
            busRecord.put("holderNames", holderNames.toString());
            busRecord.put("holderWrite", holderWrite);

            String QSZBARQ = StringUtil.getString(busRecord.get("QSZBARQ"));
            String QSZCLRQ = StringUtil.getString(busRecord.get("QSZCLRQ"));
            if (StringUtils.isNotEmpty(QSZBARQ)) {
                busRecord.put("QSZBARQ", DateTimeUtil.formatDateStr(QSZBARQ, "yyyy-MM-dd", "yyyy年MM月dd日"));
            }
            if (StringUtils.isNotEmpty(QSZCLRQ)) {
                busRecord.put("QSZCLRQ", DateTimeUtil.formatDateStr(QSZCLRQ, "yyyy-MM-dd", "yyyy年MM月dd日"));
            }
            // 职务
            getDicName(busRecord, "ssdjzw", "DIRECTOR_JOB");

            //加盖企业公章用印承诺书
            busRecord.put("COMPANY_WRITE", "");
            Map<String, Object> companyMap = dictionaryService.getByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                    new String[] { "EXE_ID","UPLOAD_SIGN" }, new Object[] { EXE_ID,"1" });
            if(companyMap!=null){
                if(StringUtils.isNotEmpty(StringUtil.getString(companyMap.get("LEGAL_FILEID")))){
                    Map<String, Object> fileInfo = dictionaryService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                            new String[] { "FILE_ID" }, new Object[] { companyMap.get("LEGAL_FILEID")});
                    String COMPANY_WRITE = StringUtil.getString(fileInfo.get("FILE_PATH"));
                    setWordToImg(busRecord, COMPANY_WRITE, "COMPANY_WRITE", "680", "580"); 
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("", e);
        }
    }

    /**
     * 
     * 描述 设置注销登记模版特殊符号
     * 
     * @author Rider Chen
     * @created 2021年3月31日 下午2:09:22
     * @param busRecord
     * @throws Exception
     */
    private void setCommercialCancelCTSym(Map<String, Object> busRecord) throws Exception {
        // 适用情形1
        String SYQX1 = StringUtil.getString(busRecord.get("SYQX1"));
        busRecord.put("SYQX01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("SYQX02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("SYQX03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("SYQX04", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        // 适用情形2
        String SYQX2 = StringUtil.getString(busRecord.get("SYQX2"));
        busRecord.put("SYQX05", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("SYQX06", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("SYQX07", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("SYQX08", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(SYQX1) && SYQX1.equals("1")) {
            busRecord.put("SYQX01", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            if (StringUtils.isNotEmpty(SYQX2) && SYQX2.equals("1")) {
                busRecord.put("SYQX05", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else if (StringUtils.isNotEmpty(SYQX2) && SYQX2.equals("2")) {
                busRecord.put("SYQX06", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            }
        } else if (StringUtils.isNotEmpty(SYQX1) && SYQX1.equals("2")) {
            busRecord.put("SYQX02", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            if (StringUtils.isNotEmpty(SYQX2) && SYQX2.equals("1")) {
                busRecord.put("SYQX07", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else if (StringUtils.isNotEmpty(SYQX2) && SYQX2.equals("2")) {
                busRecord.put("SYQX08", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            }
        } else if (StringUtils.isNotEmpty(SYQX1) && SYQX1.equals("3")) {
            busRecord.put("SYQX03", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(SYQX1) && SYQX1.equals("4")) {
            busRecord.put("SYQX04", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }

        // 注销原因
        String CANCEL_REASON = StringUtil.getString(busRecord.get("CANCEL_REASON"));
        String CANCEL_REASON_OTHER = StringUtil.getString(busRecord.get("CANCEL_REASON_OTHER"));
        busRecord.put("CANCELREASONOTHER", CANCEL_REASON_OTHER);
        busRecord.put("CANCELREASON1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("CANCELREASON2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("CANCELREASON3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("CANCELREASON4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("CANCELREASON5", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("CANCELREASON6", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("CANCELREASON7", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("101")) {
            busRecord.put("CANCELREASON1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("102")) {
            busRecord.put("CANCELREASON2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("103")) {
            busRecord.put("CANCELREASON3", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("104")) {
            busRecord.put("CANCELREASON4", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("105")) {
            busRecord.put("CANCELREASON5", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("106")) {
            busRecord.put("CANCELREASON6", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(CANCEL_REASON) && CANCEL_REASON.equals("199")) {
            busRecord.put("CANCELREASON7", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        // 清税情况
        String QSQK = StringUtil.getString(busRecord.get("QSQK"));
        busRecord.put("QS_QK1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("QS_QK2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(QSQK) && QSQK.equals("1")) {
            busRecord.put("QS_QK1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(QSQK) && QSQK.equals("0")) {
            busRecord.put("QS_QK2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        // 对外投资清理完毕标准
        String DWTZQLWBBZ = StringUtil.getString(busRecord.get("DWTZQLWBBZ"));
        busRecord.put("DWTZQLWBBZ", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(DWTZQLWBBZ) && DWTZQLWBBZ.equals("1")) {
            busRecord.put("DWTZQLWBBZ", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        // 债权债务清理情况完成标准
        String ZQZWQLQKWCBZ = StringUtil.getString(busRecord.get("ZQZWQLQKWCBZ"));
        busRecord.put("ZQZWQLQKWCBZ", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(ZQZWQLQKWCBZ) && ZQZWQLQKWCBZ.equals("1")) {
            busRecord.put("ZQZWQLQKWCBZ", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        // 非法人分支机构注销完毕标准
        String FFRFZJGZXWBBZ = StringUtil.getString(busRecord.get("FFRFZJGZXWBBZ"));
        busRecord.put("FFRFZJGZXWBBZ", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(FFRFZJGZXWBBZ) && FFRFZJGZXWBBZ.equals("1")) {
            busRecord.put("FFRFZJGZXWBBZ", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
    }

    /**
     * 
     * 描述 根据办件号获取最近的一个面签信息
     * 
     * @author Rider Chen
     * @created 2021年3月30日 下午6:54:37
     * @param exeid
     * @return
     */
    public String getNewSignTime(String exeid) {
        if (cancelService == null) {
            cancelService = (CancelService) AppUtil.getBean("cancelService");
        }
        String signTime = "";
        Map<String, Object> signinfo = cancelService.findNewSignInfo(exeid);
        if (null != signinfo && signinfo.size() > 0) {
            signTime = StringUtil.getString(signinfo.get("SIGN_TIME"));
            if (StringUtils.isNotEmpty(signTime)) {
                signTime = DateTimeUtil.formatDateStr(signTime, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日");
            }
        } else {
            Map<String, Object> taskInfo = cancelService.findNewTaskInfo(exeid, "窗口办理");
            if (null != taskInfo && taskInfo.size() > 0) {
                signTime = StringUtil.getString(taskInfo.get("CREATE_TIME"));
                if (StringUtils.isNotEmpty(signTime)) {
                    signTime = DateTimeUtil.formatDateStr(signTime, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日");
                }
            }
        }
        return signTime;
    }

    /**
     *
     * @param busRecord
     */
    private void setChangeItem(Map<String, Object> busRecord) {
        // 变更事项
        String changeRegs = busRecord.get("CHANGEREGS") == null ? "" : busRecord.get("CHANGEREGS").toString();
        if (StringUtils.isNotEmpty(changeRegs)) {
            int j = 1;
            if (changeRegs.indexOf("0") != -1) {
                StringBuffer busScoDesc = new StringBuffer("");
                busScoDesc.append("第").append(getChineseNum(j)).append("条原为：“公司的经营范围：");
                busScoDesc.append(String.valueOf(busRecord.get("OLD_BUS_SCOPE")));
                busScoDesc.append("”。\n" + "现修改为：“公司的经营范围：  ");
                busScoDesc.append(String.valueOf(busRecord.get("NEW_BUS_SCOPE")));
                busScoDesc.append("  ”。\n").append("\n");
                j++;
                busRecord.put("BUSSCODESC", busScoDesc.toString());
            }
            if (changeRegs.indexOf("2") != -1) {
                StringBuffer regAddrDesc = new StringBuffer("");
                regAddrDesc.append("第").append(getChineseNum(j)).append("条原为：“公司住所：");
                regAddrDesc.append(String.valueOf(busRecord.get("OLD_REG_ADDR")));
                regAddrDesc.append("”。\n" + "现修改为：“公司住所：  ");
                regAddrDesc.append(String.valueOf(busRecord.get("NEW_REG_ADDR")));
                regAddrDesc.append("  ”。\n").append("\n");
                j++;
                busRecord.put("REGADDRDESC", regAddrDesc.toString());
            }
        }
        // 备案事项
        String records = busRecord.get("RECORDS") == null ? "" : busRecord.get("RECORDS").toString();
        if (StringUtils.isNotEmpty(records)) {
            StringBuffer busRecDesc = new StringBuffer("");
            busRecDesc.append("变更、备案");
            busRecord.put("REITEMDES", busRecDesc.toString());
        } else {
            StringBuffer busRecDesc = new StringBuffer("");
            busRecDesc.append("变更");
            busRecord.put("REITEMDES", busRecDesc.toString());
        }
    }

    /**
     * 转换成中文
     * 
     * @param i
     * @return
     */
    private String getChineseNum(int i) {
        String chineseNum = "";
        switch (i) {
            case 1:
                chineseNum = "一";
                break;
            case 2:
                chineseNum = "二";
                break;
            case 3:
                chineseNum = "三";
                break;
            default:
                chineseNum = "";
                break;
        }
        return chineseNum;
    }

    /**
     * 
     * 描述 设置内资合伙企业
     * 
     * @author Rider Chen
     * @created 2018年6月14日 下午5:38:48
     * @param busRecord
     */
    private void setJointVenture(Map<String, Object> busRecord) {
        
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        String HOLDER_JSON = StringUtil.getString(busRecord.get("HOLDER_JSON")); 
        String LEGAL_NAME = "";
        String LEGAL_IDNO = "";
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(HOLDER_JSON);
            for (Map<String, Object> map : holderList) {
                String IS_PARTNERSHIP = StringUtil.getString(map.get("IS_PARTNERSHIP"));
                // 合伙企业执行事务合伙人
                if (StringUtils.isNotEmpty(IS_PARTNERSHIP) && IS_PARTNERSHIP.equals("1")) {
                    String SHAREHOLDER_TYPE = StringUtil.getString(map.get("SHAREHOLDER_TYPE"));
                    if (StringUtils.isNotEmpty(SHAREHOLDER_TYPE) && SHAREHOLDER_TYPE.equals("zrr")) {
                        LEGAL_NAME = StringUtil.getString(map.get("SHAREHOLDER_NAME"));
                        LEGAL_IDNO = StringUtil.getString(map.get("LICENCE_NO"));
                    } else {
                        LEGAL_NAME = StringUtil.getString(map.get("LICENCE_APPOINT_NAME"));
                        LEGAL_IDNO = StringUtil.getString(map.get("LICENCE_APPOINT_IDNO"));
                    }
                    break;
                }
            }
        }
        busRecord.put("LEGAL_NAME", LEGAL_NAME);
        busRecord.put("LEGAL_IDNO", LEGAL_IDNO);
        // 法定代表人面签签名
        Map<String, Object> getSignInfo = getSignInfo(EXE_ID, LEGAL_NAME, LEGAL_IDNO);
        busRecord.put("LEGAL_WRITE", "");
        String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
        setWordToImg(busRecord, SIGN_WRITE, "LEGAL_WRITE", "100", "40");
    }

    /**
     * 描述 设置外资企业信息
     * 
     * @author Rider Chen
     * @created 2017年4月25日 上午10:57:26
     * @param busRecord
     * @throws Exception
     */
    private void setWzCompany(Map<String, Object> busRecord) {
        try {
            // 设置企业类型选中。
            setCompanyNature(busRecord);
            // 设置业务类型
            setBusinessType(busRecord);

            // 设置企业投资者实际控制人信息
            setController(busRecord);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("", e);
        }
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年4月26日 上午10:36:19
     * @param busRecord
     */
    private void setController(Map<String, Object> busRecord) {
        String CONTROLLER_JSON = busRecord.get("CONTROLLER_JSON") == null ? ""
                : busRecord.get("CONTROLLER_JSON").toString();
        List<Map<String, Object>> controllerList = new ArrayList<Map<String, Object>>();

        if (StringUtils.isNotEmpty(CONTROLLER_JSON)) {
            controllerList = JSON.parseObject(CONTROLLER_JSON, List.class);
            for (Map<String, Object> map : controllerList) {
                try {

                    String CONTROLLER_NAME_ENG = map.get("CONTROLLER_NAME_ENG") == null ? ""
                            : map.get("CONTROLLER_NAME_ENG").toString();
                    map.put("ENGCONTROLLERNAME", CONTROLLER_NAME_ENG);
                    String CONTROL_METHOD_OTHER = map.get("CONTROL_METHOD_OTHER") == null ? ""
                            : map.get("CONTROL_METHOD_OTHER").toString();
                    map.put("CONTROLMETHODOTHER", CONTROL_METHOD_OTHER);
                    // 控制人类别类型
                    getDicName(map, "controllerType", "CONTROLLER_TYPE");
                    // 实际控制方式
                    getDicName(map, "controlMode", "CONTROL_METHOD");

                } catch (Exception e) {
                    // TODO: handle exception
                    log.error("", e);
                }
            }
        }
        busRecord.put("controllerList", controllerList);
    }

    /**
     * 描述 设置业务类型
     * 
     * @author Rider Chen
     * @created 2017年4月26日 上午9:25:57
     * @param busRecord
     * @throws Exception
     */
    private void setBusinessType(Map<String, Object> busRecord) throws Exception {
        String BUSINESS_TYPE = busRecord.get("BUSINESS_TYPE") == null ? "" : busRecord.get("BUSINESS_TYPE").toString();
        busRecord.put("BUSINESSTYPE01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE04", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE05", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE06", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE07", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE08", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE09", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE10", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE11", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE12", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("BUSINESSTYPE13", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(BUSINESS_TYPE)) {
            // 选中
            busRecord.put("BUSINESSTYPE" + BUSINESS_TYPE, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }

        String RESEARCH_TYPE = busRecord.get("RESEARCH_TYPE") == null ? "" : busRecord.get("RESEARCH_TYPE").toString();
        busRecord.put("RESEARCHTYPE01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("RESEARCHTYPE02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(RESEARCH_TYPE)) {
            // 选中
            busRecord.put("RESEARCHTYPE" + RESEARCH_TYPE, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }

        String FUNCTION_TYPE = busRecord.get("FUNCTION_TYPE") == null ? "" : busRecord.get("FUNCTION_TYPE").toString();
        busRecord.put("FUNCTIONTYPE01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FUNCTIONTYPE02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FUNCTIONTYPE03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FUNCTIONTYPE04", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FUNCTIONTYPE05", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FUNCTIONTYPE06", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FUNCTIONTYPE07", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(FUNCTION_TYPE)) {
            // 选中
            busRecord.put("FUNCTIONTYPE" + FUNCTION_TYPE, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
        // 经营范围不涉及国家规定实施的准入特别管理措施
        String NO_ACCESS_MANAGE = busRecord.get("NO_ACCESS_MANAGE") == null ? ""
                : busRecord.get("NO_ACCESS_MANAGE").toString();
        busRecord.put("NO_ACCESS_MANAGE", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(NO_ACCESS_MANAGE)) {
            busRecord.put("NO_ACCESS_MANAGE", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        String IS_DUTY_FREE = busRecord.get("IS_DUTY_FREE") == null ? "" : busRecord.get("IS_DUTY_FREE").toString();
        busRecord.put("ISDUTYFREE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("ISDUTYFREE0", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(IS_DUTY_FREE)) {
            busRecord.put("ISDUTYFREE" + IS_DUTY_FREE, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        String DUTY_FREE_TYPE = busRecord.get("DUTY_FREE_TYPE") == null ? ""
                : busRecord.get("DUTY_FREE_TYPE").toString();
        busRecord.put("DUTYFREETYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("DUTYFREETYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(DUTY_FREE_TYPE)) {
            // 选中
            busRecord.put("DUTYFREETYPE" + DUTY_FREE_TYPE, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
    }

    /**
     * 描述 设置企业类型
     * 
     * @author Rider Chen
     * @created 2017年4月26日 上午8:32:59
     * @param busRecord
     * @throws Exception
     */
    private void setCompanyNature(Map<String, Object> busRecord) throws Exception {
        String COMPANY_NATURE = busRecord.get("COMPANY_NATURE") == null ? ""
                : busRecord.get("COMPANY_NATURE").toString();
        busRecord.put("COMPANYNATURE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("COMPANYNATURE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("COMPANYNATURE3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("COMPANYNATURE4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(COMPANY_NATURE) && COMPANY_NATURE.equals("01")) {
            busRecord.put("COMPANYNATURE1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(COMPANY_NATURE) && COMPANY_NATURE.equals("02")) {
            busRecord.put("COMPANYNATURE2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(COMPANY_NATURE) && COMPANY_NATURE.equals("03")) {
            busRecord.put("COMPANYNATURE3", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(COMPANY_NATURE) && COMPANY_NATURE.equals("04")) {
            busRecord.put("COMPANYNATURE4", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }

        String JOINT_STOCK_TYPE = busRecord.get("JOINT_STOCK_TYPE") == null ? ""
                : busRecord.get("JOINT_STOCK_TYPE").toString();
        busRecord.put("JOINTSTOCKTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("JOINTSTOCKTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(JOINT_STOCK_TYPE) && JOINT_STOCK_TYPE.equals("1")) {
            busRecord.put("JOINTSTOCKTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(JOINT_STOCK_TYPE) && JOINT_STOCK_TYPE.equals("0")) {
            busRecord.put("JOINTSTOCKTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        String UNLISTED_TYPE = busRecord.get("UNLISTED_TYPE") == null ? "" : busRecord.get("UNLISTED_TYPE").toString();
        busRecord.put("UNLISTEDTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("UNLISTEDTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(UNLISTED_TYPE) && UNLISTED_TYPE.equals("1")) {
            busRecord.put("UNLISTEDTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(UNLISTED_TYPE) && UNLISTED_TYPE.equals("2")) {
            busRecord.put("UNLISTEDTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }

        String ESTABLISH_PATTERN = busRecord.get("NEW_TYPE") == null ? "" : busRecord.get("NEW_TYPE").toString();
        busRecord.put("ESTABLISHPATTERN1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("ESTABLISHPATTERN2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("ESTABLISHPATTERN3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("ESTABLISHPATTERN4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("ESTABLISHPATTERN5", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(ESTABLISH_PATTERN) && ESTABLISH_PATTERN.equals("01")) {
            busRecord.put("ESTABLISHPATTERN1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(ESTABLISH_PATTERN) && ESTABLISH_PATTERN.equals("02")) {
            busRecord.put("ESTABLISHPATTERN2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(ESTABLISH_PATTERN) && ESTABLISH_PATTERN.equals("03")) {
            busRecord.put("ESTABLISHPATTERN3", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(ESTABLISH_PATTERN) && ESTABLISH_PATTERN.equals("04")) {
            busRecord.put("ESTABLISHPATTERN4", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(ESTABLISH_PATTERN) && ESTABLISH_PATTERN.equals("05")) {
            busRecord.put("ESTABLISHPATTERN5", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        // 是否自贸试验区内
        String FAT_TYPE = busRecord.get("FAT_TYPE") == null ? "" : busRecord.get("FAT_TYPE").toString();
        busRecord.put("FATTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put("FATTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(FAT_TYPE) && FAT_TYPE.equals("1")) {
            busRecord.put("FATTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else if (StringUtils.isNotEmpty(FAT_TYPE) && FAT_TYPE.equals("0")) {
            busRecord.put("FATTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
    }

    /**
     * 初始化所有是checkbox类型的字段的数据
     * 
     * @author Phil He
     * @created 2017-9-20 下午4:35:27
     * @param dataMap
     * @param fieldMap
     */
    public static void initCheckBoxParam(Map<String, Object> dataMap, Map<String, Map<String, Object>> checkFieldMap) {
        // for(int i = 0,max = fieldNameArr.length;i<max;i++) {
        // String field = fieldNameArr[i];
        // String fieldValue = (String)dataMap.get(field);
        // if(StringUtils.isNotEmpty(fieldValue)) {
        // String[] checkValues = fieldValue.split(",");
        // Map<String,Object> checksMap =
        // WordReplaceParamUtil.initCheckBoxValues(checkValues, dicMap, preFix);
        // dataMap.putAll(checksMap);
        // }
        // }

    }

    /**
     * 初始化表格json数据集合
     * 
     * @author Phil He
     * @created 2017-9-20 下午4:53:12
     * @param dataMap
     * @param gridFieldMap
     *            {fieldName,keyName}
     */
    public static void initGridParam(Map<String, Object> dataMap, Map<String, String> gridFieldMap) {
        for (Map.Entry<String, String> entry : gridFieldMap.entrySet()) {
            String gridJson = (String) dataMap.get(entry.getKey());
            if (entry.getKey() != null) {
                dataMap.put(entry.getValue(), JSON.parseObject(gridJson, List.class));
            }
        }
    }

    /**
     * 根据传入的字典数组和选中数组初始化checkbox 使用word的特殊字段代替复选框
     * 
     * @author Phil He
     * @created 2017-9-20 下午4:13:52
     * @param checkValues
     * @param dicMap
     * @param preFix
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initCheckBoxValues(String[] checkValues, List<Map<String, Object>> dicMap,
            String preFix) {
        Map<String, Object> resultMap = new HashMap<String, Object>(dicMap.size());
        for (int i = 0, max = dicMap.size(); i < max; i++) {
            try {
                String dicValue = (String) dicMap.get(i).get("value");
                String templateKey = "$" + preFix + StringUtils.leftPad(i + "", 2, '0') + "$";
                if (checkValues != null && ArrayUtils.indexOf(checkValues, dicValue) != -1) {
                    resultMap.put(templateKey, WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                } else {
                    resultMap.put(templateKey, WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        return resultMap;
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年3月13日 上午9:01:01
     * @param busRecord
     */
    private void setAddr(Map<String, Object> busRecord) {
        // 住所
        String REGISTER_ADDR = busRecord.get("REGISTER_ADDR") == null ? "" : busRecord.get("REGISTER_ADDR").toString();
        // 经营场所
        String BUSSINESS_ADDR = busRecord.get("BUSSINESS_ADDR") == null ? ""
                : busRecord.get("BUSSINESS_ADDR").toString();
        if (StringUtils.isNotEmpty(BUSSINESS_ADDR) && StringUtils.isNotEmpty(REGISTER_ADDR)) {
            if (!REGISTER_ADDR.equals(BUSSINESS_ADDR)) {
                if (BUSSINESS_ADDR.equals("无")) {
                    busRecord.put("BUSSINESSADDR", "");
                } else {
                    busRecord.put("BUSSINESSADDR", "经营场所：" + BUSSINESS_ADDR);
                }
            } else {
                busRecord.put("BUSSINESSADDR", "");
            }
        } else {
            busRecord.put("BUSSINESSADDR", "");
        }
        if (StringUtils.isNotEmpty(REGISTER_ADDR)) {
            REGISTER_ADDR = REGISTER_ADDR.replace("（仅作为联络地址）", "");
            busRecord.put("REGISTER_ADDR", REGISTER_ADDR);
        }
    }

    /**
     * 描述 设置经营期限
     * 
     * @author Rider Chen
     * @created 2017年3月1日 下午4:59:56
     * @param busRecord
     * @param OPRRATE_TERM_TYPE
     */
    private void setBussinessYears(Map<String, Object> busRecord, String OPRRATE_TERM_TYPE) {
        List<Object> list = new LinkedList<Object>();
        if (OPRRATE_TERM_TYPE.equals("1")) {
            busRecord.put("BUSSINESS_BZ", "公司营业期限届满，可以通过修改公司章程而存续。公司延长营业期限须办理变更登记。");
            busRecord.put("BUSSINESS_BK", "（一）、（二）、（四）、（五）");
            busRecord.put("BUSSINESS_YEARS", busRecord.get("BUSSINESS_YEARS") + "年");
            busRecord.put("BUSSINESS_THXY", "有《合伙企业法》第四十五条规定的情形之一的，合伙人可以退伙。");
            list.add("（一）公司章程规定的营业期限届满；");
            list.add("    （二）股东决定解散；");
            list.add("    （三）因公司合并或者分立需要解散；");
            list.add("    （四）依法被吊销营业执照、责令关闭或者被撤销；");
            list.add("    （五）人民法院依照《公司法》第一百八十三条的规定予以解散；");
        } else if (OPRRATE_TERM_TYPE.equals("0")) {
            busRecord.put("BUSSINESS_BZ", "");
            busRecord.put("BUSSINESS_BK", "（一）、（三）、（四）");
            busRecord.put("BUSSINESS_YEARS", "长期");
            busRecord.put("BUSSINESS_THXY", "合伙人在不给合伙企业事务执行造成不利影响的情况下，可以退伙，但应当提前三十日通知其他合伙人。");
            list.add("（一）股东决定解散；");
            list.add("    （二）因公司合并或者分立需要解散；");
            list.add("    （三）依法被吊销营业执照、责令关闭或者被撤销；");
            list.add("    （四）人民法院依照《公司法》第一百八十三条的规定予以解散；");
        }
        busRecord.put("BUSSINESS_LIST", list);
    }

    /**
     * 描述 个体商户参数
     * 
     * @author Rider Chen
     * @created 2016年12月29日 下午3:04:15
     * @param busRecord
     */
    private void setIndividual(Map<String, Object> busRecord) {
        try {
            // 经营形式（0：个人经营，1：家庭经营）
            String MANAGE_FORM = busRecord.get("MANAGE_FORM") == null ? "" : busRecord.get("MANAGE_FORM").toString();
            busRecord.put("MANAGEFORM1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("MANAGEFORM2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            if (StringUtils.isNotEmpty(MANAGE_FORM) && MANAGE_FORM.equals("0")) {
                busRecord.put("MANAGEFORM1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else if (StringUtils.isNotEmpty(MANAGE_FORM) && MANAGE_FORM.equals("1")) {
                busRecord.put("MANAGEFORM2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            }
            // 参加经营的家庭成员JSON
            String FAMILY_JSON = busRecord.get("FAMILY_JSON") == null ? "" : busRecord.get("FAMILY_JSON").toString();
            List<Map<String, Object>> familyList;
            List<Object> list = new LinkedList<Object>();
            if (StringUtils.isNotEmpty(FAMILY_JSON)) {
                familyList = JSON.parseObject(FAMILY_JSON, List.class);
                for (Map<String, Object> map : familyList) {
                    list.add("姓名：" + map.get("FAMILY_NAME") + "       身份证号码：" + map.get("FAMILY_IDNO"));
                }
            }
            busRecord.put("FAMILYLIST", list);

            // 经营者照片

            busRecord.put("DEALER_PHOTO", "");
            /**
             * String DEALER_PHOTO = busRecord.get("DEALER_PHOTO") == null ? ""
             * : busRecord.get("DEALER_PHOTO").toString();
             * setWordToImg(busRecord, DEALER_PHOTO, "DEALER_PHOTO", "115",
             * "140");
             **/
            // 联络员身份证正面
            String LIAISON_SFZZM = busRecord.get("LIAISON_SFZZM") == null ? ""
                    : busRecord.get("LIAISON_SFZZM").toString();
            busRecord.put("LIAISON_SFZZM", "");
            setWordToImg(busRecord, LIAISON_SFZZM, "LIAISON_SFZZM", "220", "140");
            // 联络员身份证反面
            String LIAISON_SFZFM = busRecord.get("LIAISON_SFZFM") == null ? ""
                    : busRecord.get("LIAISON_SFZFM").toString();
            busRecord.put("LIAISON_SFZFM", "");
            setWordToImg(busRecord, LIAISON_SFZFM, "LIAISON_SFZFM", "220", "140");

            String EXE_ID = busRecord.get("EXE_ID") == null ? "" : busRecord.get("EXE_ID").toString();
            String DEALER_NAME = busRecord.get("DEALER_NAME") == null ? "" : busRecord.get("DEALER_NAME").toString();
            String DEALER_IDCARD = busRecord.get("DEALER_IDCARD") == null ? ""
                    : busRecord.get("DEALER_IDCARD").toString();
            // 经营者面签签名
            Map<String, Object> getSignInfo = getSignInfo(EXE_ID, DEALER_NAME, DEALER_IDCARD);
            busRecord.put("SIGN_WRITE", "");
            String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(busRecord, SIGN_WRITE, "SIGN_WRITE", "100", "40");
            // 经营者面签身份证
            String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
            busRecord.put("SIGN_IDPHOTO_FRONT", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "SIGN_IDPHOTO_FRONT", "220", "140");
            // 经营者面签身份证
            String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                    : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
            busRecord.put("SIGN_IDPHOTO_BACK", "");
            setWordToImg(busRecord, SIGN_IDPHOTO_BACK, "SIGN_IDPHOTO_BACK", "220", "140");

            // 注册地址取得方式
            String RESIDENCE_REGISTER_WAYOFGET = busRecord.get("RESIDENCE_REGISTER_WAYOFGET") == null ? ""
                    : busRecord.get("RESIDENCE_REGISTER_WAYOFGET").toString();

            busRecord.put("RESIDENCEREGISTERWAYOFGET01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("RESIDENCEREGISTERWAYOFGET02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("RESIDENCEREGISTERWAYOFGET03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("RESIDENCEREGISTERWAYOFGET04", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("RESIDENCEREGISTERWAYOFGET05", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("RESIDENCEREGISTERWAYOFGET23", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            if (StringUtils.isNotEmpty(RESIDENCE_REGISTER_WAYOFGET)) {
                busRecord.put("RESIDENCEREGISTERWAYOFGET" + RESIDENCE_REGISTER_WAYOFGET,
                        WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                if (RESIDENCE_REGISTER_WAYOFGET.equals("02") || RESIDENCE_REGISTER_WAYOFGET.equals("03")) {
                    busRecord.put("RESIDENCEREGISTERWAYOFGET23", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                }
            }
            // 注册地址军队房产
            String ARMY_REGISTER_HOURSE = busRecord.get("ARMY_REGISTER_HOURSE") == null ? ""
                    : busRecord.get("ARMY_REGISTER_HOURSE").toString();
            busRecord.put("ARMYREGISTERHOURSE01", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("ARMYREGISTERHOURSE02", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("ARMYREGISTERHOURSE03", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("ARMYREGISTERHOURSE23", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            if (StringUtils.isNotEmpty(ARMY_REGISTER_HOURSE)) {
                busRecord.put("ARMYREGISTERHOURSE" + ARMY_REGISTER_HOURSE,
                        WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                if (!ARMY_REGISTER_HOURSE.equals("01")) {
                    busRecord.put("ARMYREGISTERHOURSE23", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中

                }
            }
            // 法律文书送达地址(同注册地址)
            String IS_REGISTER_ADDR = busRecord.get("IS_REGISTER_ADDR") == null ? "1"
                    : busRecord.get("IS_REGISTER_ADDR").toString();
            String BUSINESS_PLACE = busRecord.get("BUSINESS_PLACE") == null ? ""
                    : busRecord.get("BUSINESS_PLACE").toString();
            String busTableName = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
            if (Objects.equals(busTableName, TableNameEnum.T_COMMERCIAL_INDIVIDUAL.getVal())) {
                if (StringUtils.isNotEmpty(IS_REGISTER_ADDR) && IS_REGISTER_ADDR.equals("1")) {
                    busRecord.put("LAW_SEND_ADDR", BUSINESS_PLACE);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("", e);
        }
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
    public Map<String, Object> getSignInfo(String exeid, String name, String idno) {
        Map<String, Object> signFilePath = new HashMap<String, Object>();
        if (dictionaryService == null) {
            dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        }
        List<Map<String, Object>> signList = dictionaryService.getAllByJdbc("T_COMMERCIAL_SIGN",
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
                Map<String, Object> fileInfo = dictionaryService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[] { "FILE_ID" }, new Object[] { SIGN_WRITE });
                signFilePath.put("SIGN_WRITE", fileInfo.get("FILE_PATH"));
            }
            if (StringUtils.isNotEmpty(SIGN_IDPHOTO_FRONT)) {
                Map<String, Object> fileInfo = dictionaryService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[] { "FILE_ID" }, new Object[] { SIGN_IDPHOTO_FRONT });
                signFilePath.put("SIGN_IDPHOTO_FRONT", fileInfo.get("FILE_PATH"));
            }
            if (StringUtils.isNotEmpty(SIGN_IDPHOTO_BACK)) {
                Map<String, Object> fileInfo = dictionaryService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[] { "FILE_ID" }, new Object[] { SIGN_IDPHOTO_BACK });
                signFilePath.put("SIGN_IDPHOTO_BACK", fileInfo.get("FILE_PATH"));
            }
        }
        return signFilePath;
    }

    /**
     * 
     * 描述：设置word图片
     * 
     * @author Rider Chen
     * @created 2020年12月1日 下午3:02:45
     * @param busRecord
     * @param path
     * @param key
     * @param width
     * @param height
     */
    @SuppressWarnings("rawtypes")
    private void setWordToImg(Map<String, Object> busRecord, String path, String key, String width, String height) {
        if (StringUtils.isNotEmpty(path)) {
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("uploadFileUrl") + path;
            attachFileFolderPath = attachFileFolderPath.replace("\\", "/");
            Map map = new HashMap();
            map.put("content", image2byte(attachFileFolderPath));
            map.put("width", width);
            map.put("height", height);
            String fileType = FileUtil.getFileExtension(path);
            map.put("type", fileType);
            busRecord.put(key, map);
        }
    }

    /**
     * 
     * 描述：设置word图片
     * 
     * @author Rider Chen
     * @created 2020年12月1日 下午3:02:45
     * @param busRecord
     * @param path
     * @param width
     * @param height
     */
    @SuppressWarnings("rawtypes")
    private void setWordToImg(Set<Map> set, String path, String width, String height) {
        if (StringUtils.isNotEmpty(path)) {
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("uploadFileUrl") + path;
            attachFileFolderPath = attachFileFolderPath.replace("\\", "/");
            Map map = new HashMap();
            map.put("content", image2byte(attachFileFolderPath));
            map.put("width", width);
            map.put("height", height);
            String fileType = FileUtil.getFileExtension(path);
            map.put("type", fileType);
            set.add(map);
        }
    }

    /**
     * 
     * 描述 图片转换成byte[]
     * 
     * @author Rider Chen
     * @created 2017年1月13日 上午10:44:58
     * @param path
     * @return
     */
    public byte[] image2byte(String path) {
        byte[] data = null;
        InputStream input = null;
        ByteArrayOutputStream output = null;
        try {
            input = DownLoadServlet.getStreamDownloadOutFile(path);
            output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
        }
        return data;
    }

    /**
     * 描述 个独企业参数
     * 
     * @author Rider Chen
     * @created 2016年12月29日 下午3:04:15
     * @param busRecord
     */
    private void setSolelyinvest(Map<String, Object> busRecord) {
        try {
            // 出资方式（1：以个人财产出资，2：以家庭共有财产作为个人出资）
            String INVEST_TYPE = busRecord.get("INVEST_TYPE") == null ? "" : busRecord.get("INVEST_TYPE").toString();
            busRecord.put("INVESTTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("INVESTTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            if (StringUtils.isNotEmpty(INVEST_TYPE) && INVEST_TYPE.equals("1")) {
                busRecord.put("INVESTTYPE1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else if (StringUtils.isNotEmpty(INVEST_TYPE) && INVEST_TYPE.equals("2")) {
                busRecord.put("INVESTTYPE2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            }

            // 申领电子 营业执照 （1：是，0：否）
            String LICENSE_APPLY = busRecord.get("LICENSE_APPLY") == null ? ""
                    : busRecord.get("LICENSE_APPLY").toString();
            busRecord.put("LICENSEAPPLY1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("LICENSEAPPLY2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            if (StringUtils.isNotEmpty(LICENSE_APPLY) && LICENSE_APPLY.equals("1")) {
                busRecord.put("LICENSEAPPLY1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else if (StringUtils.isNotEmpty(LICENSE_APPLY) && LICENSE_APPLY.equals("0")) {
                busRecord.put("LICENSEAPPLY2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            }
            // 申领电子 营业执照 （1：是，0：否）
            String LICENSE_PAPER_APPLY = busRecord.get("LICENSE_PAPER_APPLY") == null ? ""
                    : busRecord.get("LICENSE_PAPER_APPLY").toString();
            busRecord.put("LICENSEPAPERAPPLY1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put("LICENSEPAPERAPPLY2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            if (StringUtils.isNotEmpty(LICENSE_PAPER_APPLY) && LICENSE_PAPER_APPLY.equals("1")) {
                busRecord.put("LICENSEPAPERAPPLY1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else if (StringUtils.isNotEmpty(LICENSE_PAPER_APPLY) && LICENSE_PAPER_APPLY.equals("0")) {
                busRecord.put("LICENSEPAPERAPPLY2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            }
            

            String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
            String LEGAL_NAME = StringUtil.getString(busRecord.get("INVESTOR_NAME")); 
            String LEGAL_IDNO = StringUtil.getString(busRecord.get("INVESTOR_IDCARD")); 
            busRecord.put("LEGAL_NAME", LEGAL_NAME);
            busRecord.put("LEGAL_IDNO", LEGAL_IDNO);
            // 法定代表人面签签名
            Map<String, Object> getSignInfo = getSignInfo(EXE_ID, LEGAL_NAME, LEGAL_IDNO);
            busRecord.put("LEGAL_WRITE", "");
            String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(busRecord, SIGN_WRITE, "LEGAL_WRITE", "100", "40");

            busRecord.put("OPERATORNAME", busRecord.get("JBR_NAME"));
            busRecord.put("OPERATORIDNO", busRecord.get("JBR_ZJHM"));
            busRecord.put("OPERATOR_WRITE", "");
            busRecord.put("VALIDITY_START_DATE", "");
            busRecord.put("VALIDITY_START_DATE", "");
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 描述 外商投资企业法律文件送达授权委托书基本信息
     * 
     * @author Rider Chen
     * @created 2016年12月21日 下午4:39:48
     * @param busRecord
     */
    private void setAttorney(Map<String, Object> busRecord) {
        String ATTORNEY_JSON = busRecord.get("ATTORNEY_JSON") == null ? "" : busRecord.get("ATTORNEY_JSON").toString();
        List<Map<String, Object>> attorneyList;

        List<Object> list = new LinkedList<Object>();
        if (StringUtils.isNotEmpty(ATTORNEY_JSON)) {
            attorneyList = JSON.parseObject(ATTORNEY_JSON, List.class);
            for (Map<String, Object> map : attorneyList) {

                list.add("授权人：" + map.get("AUTHORIZER"));
                list.add("被授权人：" + map.get("DELEGATEE"));
                list.add("授权范围：授予" + map.get("DELEGATEE") + "代表" + map.get("AUTHORIZER")
                        + "在中国境内接受企业登记机关法律文件送达，直至解除授权为止。");
                list.add("被授权人联系人：" + map.get("DELEGATEE_CONTRACTNAME"));
                list.add("被授权人地址：" + map.get("DELEGATEE_ADDR"));
                list.add("邮政编码: " + map.get("DELEGATEE_POSTCODE") + "                   电子邮件: "
                        + map.get("DELEGATEE_EMAIL"));
                list.add("移动电话: " + map.get("DELEGATEE_MOBILE") + "                   固定电话: "
                        + map.get("DELEGATEE_FIXEDLINE"));
                list.add("");
                list.add("授权人签字或盖章");
                list.add(map.get("AUTHORIZER") + "（签章） ");
                list.add("");
                list.add("被授权人签字或盖章");
                list.add(map.get("DELEGATEE") + "（签章）");
                list.add("");
                list.add("                                                      " + busRecord.get("FILL_DATE"));
                list.add("");
                list.add("");
            }
        }
        busRecord.put("attorneyList", list);
    }

    /**
     * 描述 设置投资者信息
     * 
     * @author Rider Chen
     * @created 2016年12月21日 上午11:16:03
     * @param busRecord
     */
    private void setTzzxx(Map<String, Object> busRecord) {
        // 投资者姓名
        StringBuffer tzzNames = new StringBuffer();
        StringBuffer tzzxxNames = new StringBuffer();
        StringBuffer tzzZxsfhhrName = new StringBuffer();
        // 国别
        StringBuffer countryNames = new StringBuffer();
        // 实际出资额
        double tzzPaidinCounts = 0;
        // 投资者出资额
        StringBuffer tzzCzes = new StringBuffer();
        // 投资者委派董事
        StringBuffer tzzWpds = new StringBuffer();
        // 投资者集合
        List<Object> tzzList = new LinkedList<Object>();
        // 投资者出资情况
        List<Object> tzzczqkList = new LinkedList<Object>();
        // 投资者签字
        List<Object> tzzqzList = new LinkedList<Object>();
        // 企业或者其他投资人
        List<Object> list1 = new LinkedList<Object>();

        // 币种
        String CURRENCY = (String) busRecord.get("CURRENCY");
        List<Map<String, Object>> jwtzzList;

        // 外方投资方信息
        String FOREIGNINVESTOR_JSON = busRecord.get("FOREIGNINVESTOR_JSON") == null ? ""
                : busRecord.get("FOREIGNINVESTOR_JSON").toString();
        // 执行事务合伙人
        List<Map<String, Object>> partnerList = new ArrayList<Map<String, Object>>();

        if (StringUtils.isNotEmpty(FOREIGNINVESTOR_JSON)) {
            jwtzzList = JSON.parseObject(FOREIGNINVESTOR_JSON, List.class);
            // 删除不在表中存在的字段
            jwtzzList = removeMapKey(jwtzzList, "T_COMMERCIAL_INVESTOR_FOREIGN");

            if (null != jwtzzList) {
                // 设置外方投资者信息
                tzzPaidinCounts = setJwtzz(busRecord, tzzNames, tzzxxNames, tzzZxsfhhrName, tzzPaidinCounts, tzzCzes,
                        tzzWpds, tzzList, tzzczqkList, tzzqzList, CURRENCY, jwtzzList, countryNames, partnerList,
                        list1);
            }

            // 中方投资方信息
            String DOMESTICINVESTOR_JSON = busRecord.get("DOMESTICINVESTOR_JSON") == null ? ""
                    : busRecord.get("DOMESTICINVESTOR_JSON").toString();
            List<Map<String, Object>> zftzzList = new ArrayList<Map<String, Object>>();
            if (StringUtils.isNotEmpty(DOMESTICINVESTOR_JSON)) {
                zftzzList = JSON.parseObject(DOMESTICINVESTOR_JSON, List.class);
                // 删除不在表中存在的字段
                zftzzList = removeMapKey(zftzzList, "T_COMMERCIAL_INVESTOR_DOMESTIC");
                tzzPaidinCounts = setZftzz(tzzNames, tzzxxNames, tzzZxsfhhrName, countryNames, tzzPaidinCounts, tzzCzes,
                        tzzWpds, tzzList, tzzczqkList, tzzqzList, CURRENCY, zftzzList, partnerList, list1, busRecord);
            }

            if (StringUtils.isNotEmpty(tzzNames)) {
                busRecord.put("tzzNames", tzzNames.substring(1, tzzNames.length()));
            }
            if (StringUtils.isNotEmpty(tzzxxNames)) {
                busRecord.put("tzzxxNames", tzzxxNames.substring(1, tzzxxNames.length()));
            }
            if (StringUtils.isNotEmpty(tzzCzes)) {
                busRecord.put("tzzCzes", tzzCzes.substring(1, tzzCzes.length()));
            }
            if (StringUtils.isNotEmpty(tzzWpds)) {
                tzzWpds = new StringBuffer(tzzWpds.substring(1, tzzWpds.length()));
                busRecord.put("tzzWpds", tzzWpds.toString());
            }
            busRecord.put("tzzList", tzzList);
            busRecord.put("tzzZxsfhhrName", tzzZxsfhhrName);
            busRecord.put("tzzPaidinCounts", tzzPaidinCounts);
            busRecord.put("countryNames", countryNames);
            if (null == partnerList || partnerList.size() == 0) {
                partnerList = new ArrayList<Map<String, Object>>();
                Map<String, Object> partner = new HashMap<String, Object>();
                partner.put("PARTNER_NAME", "");
                partner.put("PARTNER_MOBILE", "");
                partner.put("PARTNER_IDTYPE", "");
                partner.put("PARTNER_IDNO", "");
                partnerList.add(partner);
            }
            busRecord.put("partnerList", partnerList);

            List<Object> list = new LinkedList<Object>();
            for (int i = 0; i < tzzczqkList.size(); i++) {
                String value = (String) tzzczqkList.get(i);
                list.add("    11." + (i + 1) + " " + value);
            }
            busRecord.put("tzzczqkList", list);
            busRecord.put("tzzqzList", tzzqzList);
            jwtzzList.addAll(zftzzList);
            if (null != jwtzzList) {
                busRecord.put("investorNums", jwtzzList.size());
                busRecord.put("investorCounts", jwtzzList.size() + 1);
            }
            busRecord.put("investorList", jwtzzList);
            busRecord.put("fahqtzjwfdbList", list1);
            setMostHolder(busRecord, jwtzzList);
        }
    }

    private double setZftzz(StringBuffer tzzNames, StringBuffer tzzxxNames, StringBuffer tzzZxsfhhrName,
            StringBuffer countryNames, double tzzPaidinCounts, StringBuffer tzzCzes, StringBuffer tzzWpds,
            List<Object> tzzList, List<Object> tzzczqkList, List<Object> tzzqzList, String CURRENCY,
            List<Map<String, Object>> zftzzList, List<Map<String, Object>> partnerList, List<Object> list1,
            Map<String, Object> busRecord) {
        for (Map<String, Object> map : zftzzList) {
            String INVESTOR_NAME = (String) map.get("INVESTOR_NAME");// 投资者名称
            String INVESTOR_COUNTRY = (String) map.get("INVESTOR_COUNTRY");// 国别（地区）
            String CONTRIBUTION = (String) map.get("CONTRIBUTION");// 出资额
            String PROPORTION = (String) map.get("PROPORTION");// 占注册资本比例
            String DIRECTOR_NUM = (String) map.get("DIRECTOR_NUM");// 委派董事人数
            // 证件类型
            getDicName(map, "DocumentType", "INVESTOR_IDTYPE");
            tzzNames.append("、").append(INVESTOR_COUNTRY).append("的").append(INVESTOR_NAME);
            tzzxxNames.append("、").append(INVESTOR_NAME);
            tzzCzes.append(";").append(INVESTOR_NAME).append("认缴出资 ").append(CONTRIBUTION).append(" 万元")
                    .append(CURRENCY).append("，占注册资本的 ").append(PROPORTION);
            tzzWpds.append("，").append(INVESTOR_NAME).append("委派");
            tzzWpds.append(DIRECTOR_NUM).append("名");
            setTzzxx(map, tzzList, tzzczqkList, tzzqzList, CURRENCY, map, INVESTOR_NAME, INVESTOR_COUNTRY);

            // 承担责任方式
            String DUTY_MODE = map.get("DUTY_MODE") == null ? "" : map.get("DUTY_MODE").toString();
            if (StringUtils.isNotEmpty(DUTY_MODE)) {
                if ("有限责任".equals(DUTY_MODE)) {
                    map.put("INVESTOR_NAME", "有限合伙人：" + INVESTOR_NAME);
                } else if ("无限责任".equals(DUTY_MODE)) {
                    map.put("INVESTOR_NAME", "普通合伙人：" + INVESTOR_NAME);
                }
            }
            // 是否执行事务合伙人
            String IS_PARTNERSHIP = map.get("IS_PARTNERSHIP") == null ? "" : map.get("IS_PARTNERSHIP").toString();

            String INVESTOR_JOINT_TYPE = map.get("INVESTOR_JOINT_TYPE") == null ? ""
                    : map.get("INVESTOR_JOINT_TYPE").toString();
            if (StringUtils.isNotEmpty(IS_PARTNERSHIP) && "1".equals(IS_PARTNERSHIP)) {

                Map<String, Object> partner = new HashMap<String, Object>();
                if (!INVESTOR_JOINT_TYPE.equals("zrr")) {
                    getInvestorList(list1, map, busRecord);
                    if (null != map.get("INVESTOR_APPOINT_NAME") && !"".equals(map.get("INVESTOR_APPOINT_NAME"))) {
                        INVESTOR_NAME = INVESTOR_NAME + "（委派代表：" + map.get("INVESTOR_APPOINT_NAME") + "）";
                    }
                }
                // 证件类型
                getDicName(map, "DocumentType", "INVESTOR_APPOINT_IDTYPE");
                String INVESTOR_IDTYPE = map.get("INVESTOR_IDTYPE") + "、" + map.get("INVESTOR_APPOINT_IDTYPE");
                String INVESTOR_IDNO = map.get("INVESTOR_IDNO") + "、" + map.get("INVESTOR_APPOINT_IDNO");
                partner.put("PARTNER_NAME", INVESTOR_NAME);
                partner.put("PARTNER_MOBILE", map.get("INVESTOR_PHONE"));
                partner.put("PARTNER_IDTYPE", INVESTOR_IDTYPE);
                partner.put("PARTNER_IDNO", INVESTOR_IDNO);
                partnerList.add(partner);

                if (StringUtils.isNotEmpty(tzzZxsfhhrName)) {
                    tzzZxsfhhrName.append("、").append(INVESTOR_NAME);
                } else {
                    tzzZxsfhhrName.append(INVESTOR_NAME);
                }
                if (StringUtils.isNotEmpty(countryNames)) {
                    countryNames.append("、").append(INVESTOR_COUNTRY);
                } else {
                    countryNames.append(INVESTOR_COUNTRY);
                }
            }
            // 实缴出资额（万元）
            double PAIDIN_QUOTA = map.get("PAIDIN_QUOTA") == null ? 0
                    : Double.parseDouble(map.get("PAIDIN_QUOTA").toString());
            tzzPaidinCounts = tzzPaidinCounts + PAIDIN_QUOTA;
            // 设置出资方式
            setInvestmentType(map);
        }
        return tzzPaidinCounts;
    }

    private double setJwtzz(Map<String, Object> busRecord, StringBuffer tzzNames, StringBuffer tzzxxNames,
            StringBuffer tzzZxsfhhrName, double tzzPaidinCounts, StringBuffer tzzCzes, StringBuffer tzzWpds,
            List<Object> tzzList, List<Object> tzzczqkList, List<Object> tzzqzList, String CURRENCY,
            List<Map<String, Object>> jwtzzList, StringBuffer countryNames, List<Map<String, Object>> partnerList,
            List<Object> list1) {
        for (Map<String, Object> map : jwtzzList) {
            String INVESTOR_NAME = (String) map.get("INVESTOR_NAME");// 投资者名称
            String INVESTOR_COUNTRY = (String) map.get("INVESTOR_COUNTRY");// 国别（地区）
            String CONTRIBUTION = (String) map.get("CONTRIBUTION");// 出资额
            String PROPORTION = (String) map.get("PROPORTION");// 占注册资本比例
            String DIRECTOR_NUM = (String) map.get("DIRECTOR_NUM");// 委派董事人数
            // 证件类型
            getDicName(map, "DocumentType", "INVESTOR_IDTYPE");
            tzzNames.append("、").append(INVESTOR_COUNTRY).append("的").append(INVESTOR_NAME);
            tzzxxNames.append("、").append(INVESTOR_NAME);
            tzzCzes.append("；").append(INVESTOR_NAME).append("认缴出资 ").append(CONTRIBUTION).append(" 万元")
                    .append(CURRENCY).append("，占注册资本的 ").append(PROPORTION);
            tzzWpds.append("，").append(INVESTOR_NAME).append("委派");
            tzzWpds.append(DIRECTOR_NUM).append("名");
            setTzzxx(busRecord, tzzList, tzzczqkList, tzzqzList, CURRENCY, map, INVESTOR_NAME, INVESTOR_COUNTRY);

            /* 实缴出资额 */
            int compareTime = (int) busRecord.get("COMPARE_TIME");
            if (compareTime == -1) {
                map.put("WZSJCZE", 0);
            } else {
                map.put("WZSJCZE", CONTRIBUTION);
            }

            // 承担责任方式
            String DUTY_MODE = map.get("DUTY_MODE") == null ? "" : map.get("DUTY_MODE").toString();
            if (StringUtils.isNotEmpty(DUTY_MODE)) {
                if ("有限责任".equals(DUTY_MODE)) {
                    map.put("INVESTOR_NAME", "有限合伙人：" + INVESTOR_NAME);
                } else if ("无限责任".equals(DUTY_MODE)) {
                    map.put("INVESTOR_NAME", "普通合伙人：" + INVESTOR_NAME);
                }
            }
            // 是否执行事务合伙人
            String IS_PARTNERSHIP = map.get("IS_PARTNERSHIP") == null ? "" : map.get("IS_PARTNERSHIP").toString();
            if (StringUtils.isNotEmpty(IS_PARTNERSHIP) && "1".equals(IS_PARTNERSHIP)) {

                Map<String, Object> partner = new HashMap<String, Object>();
                getInvestorList(list1, map, busRecord);
                if (null != map.get("INVESTOR_APPOINT_NAME") && !"".equals(map.get("INVESTOR_APPOINT_NAME"))) {
                    INVESTOR_NAME = INVESTOR_NAME + "（委派代表：" + map.get("INVESTOR_APPOINT_NAME") + "）";
                }
                // 证件类型
                getDicName(map, "DocumentType", "INVESTOR_APPOINT_IDTYPE");
                String INVESTOR_IDTYPE = map.get("INVESTOR_IDTYPE") + "、" + map.get("INVESTOR_APPOINT_IDTYPE");
                String INVESTOR_IDNO = map.get("INVESTOR_IDNO") + "、" + map.get("INVESTOR_APPOINT_IDNO");
                partner.put("PARTNER_NAME", INVESTOR_NAME);
                partner.put("PARTNER_MOBILE", map.get("INVESTOR_PHONE"));
                partner.put("PARTNER_IDTYPE", INVESTOR_IDTYPE);
                partner.put("PARTNER_IDNO", INVESTOR_IDNO);
                partnerList.add(partner);

                if (StringUtils.isNotEmpty(tzzZxsfhhrName)) {
                    tzzZxsfhhrName.append("、").append(INVESTOR_NAME);
                } else {
                    tzzZxsfhhrName.append(INVESTOR_NAME);
                }
                if (StringUtils.isNotEmpty(countryNames)) {
                    countryNames.append("、").append(INVESTOR_COUNTRY);
                } else {
                    countryNames.append(INVESTOR_COUNTRY);
                }
            }
            // 实缴出资额（万元）
            double PAIDIN_QUOTA = map.get("PAIDIN_QUOTA") == null ? 0
                    : Double.parseDouble(map.get("PAIDIN_QUOTA").toString());
            tzzPaidinCounts = tzzPaidinCounts + PAIDIN_QUOTA;

            // 设置出资方式
            setInvestmentType(map);
        }
        return tzzPaidinCounts;
    }

    /**
     * 描述 设置出资最多股东
     * 
     * @author Rider Chen
     * @created 2016年12月22日 下午2:32:43
     * @param busRecord
     * @param jwtzzList
     */
    private void setMostHolder(Map<String, Object> busRecord, List<Map<String, Object>> jwtzzList) {
        String MOSTHOLDER = "";
        // 按投资总额进行倒序
        Collections.sort(jwtzzList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                double sum0 = Double.parseDouble(arg0.get("CONTRIBUTION").toString());
                double sum1 = Double.parseDouble(arg1.get("CONTRIBUTION").toString());
                if (sum1 > sum0) {
                    return 1;
                } else if (sum1 == sum0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        if (null != jwtzzList && jwtzzList.size() > 0) {
            MOSTHOLDER = jwtzzList.get(0).get("INVESTOR_NAME").toString();
            busRecord.put("MOSTHOLDER", MOSTHOLDER);
        }
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月21日 下午4:42:30
     * @param busRecord
     * @param tzzList
     * @param tzzczqkList
     * @param tzzqzList
     * @param CURRENCY
     * @param map
     * @param INVESTOR_NAME
     * @param INVESTOR_COUNTRY
     */
    private void setTzzxx(Map<String, Object> busRecord, List<Object> tzzList, List<Object> tzzczqkList,
            List<Object> tzzqzList, String CURRENCY, Map<String, Object> map, String INVESTOR_NAME,
            String INVESTOR_COUNTRY) {
        tzzList.add("    名称：" + INVESTOR_NAME);
        tzzList.add("    法定地址：" + map.get("INVESTOR_ADDR"));
        tzzList.add("    联系电话：" + map.get("INVESTOR_PHONE") + "      国籍：" + INVESTOR_COUNTRY);
        tzzList.add("    证件类型：" + map.get("INVESTOR_IDTYPE") + "      证件号码：" + map.get("INVESTOR_IDNO"));
        tzzList.add("");
        // 投资者出资情况
        setTzzCzqk(map, tzzczqkList, CURRENCY, INVESTOR_NAME);
        tzzqzList.add(INVESTOR_NAME + "（签章）：");
        tzzqzList.add("");
        tzzqzList.add("");
    }

    /**
     * 描述 投资者出资情况
     * 
     * @author Rider Chen
     * @created 2016年12月21日 下午1:52:19
     * @param busRecord
     * @param tzzczqkList
     * @param CURRENCY
     * @param INVESTOR_NAME
     */
    public static void setTzzCzqk(Map<String, Object> map, List<Object> tzzczqkList, String CURRENCY,
            String INVESTOR_NAME) {
        StringBuffer sb = new StringBuffer();
        sb.append(INVESTOR_NAME + ":");
        getStringCzfs(map, CURRENCY, sb);

        tzzczqkList.add(sb.toString());
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年1月9日 下午2:11:58
     * @param map
     * @param CURRENCY
     * @param sb
     */
    public static void getStringCzfs(Map<String, Object> map, String CURRENCY, StringBuffer sb) {
        // 境外现汇
        getCzqk(map, CURRENCY, sb, "INVESTMENT_ABROAD_EXCHANGE", "境外现汇");
        // 境外人民币
        getCzqk(map, CURRENCY, sb, "INVESTMENT_ABROAD_RMB", "境外人民币");
        // 境内人民币
        getCzqk(map, CURRENCY, sb, "INVESTMENT_DOMESTIC_RMB", "境内人民币");
        // 实物
        getCzqk(map, CURRENCY, sb, "INVESTMENT_MATERIAL", "实物");
        // 技术出资
        getCzqk(map, CURRENCY, sb, "INVESTMENT_TECHNOLOGY", "技术出资");
        // 土地使用权
        getCzqk(map, CURRENCY, sb, "INVESTMENT_LAND", "土地使用权");
        // 股权
        getCzqk(map, CURRENCY, sb, "INVESTMENT_STOCK", "股权");
        // 其他
        getCzqk(map, CURRENCY, sb, "INVESTMENT_OTHER", "其他");
    }

    /**
     * 描述 拼接出资情况
     * 
     * @author Rider Chen
     * @created 2016年12月21日 下午1:48:55
     * @param busRecord
     * @param CURRENCY
     * @param sb
     * @param key
     * @param keyword
     */
    private static void getCzqk(Map<String, Object> map, String CURRENCY, StringBuffer sb, String key, String keyword) {
        String sum = map.get(key) == null ? "" : map.get(key).toString();
        if (StringUtils.isNotEmpty(sum)) {
            sb.append(keyword).append(sum).append("万元").append(CURRENCY).append(";");
        }
    }

    /**
     * 描述 设置印章信息
     * 
     * @author Rider Chen
     * @created 2016年12月20日 下午1:35:38
     * @param busRecord
     */
    private void setSeal(Map<String, Object> busRecord) {
        try {
            busRecord.put("FRWZCZ", "");
            busRecord.put("YZGYS", "");
            String IS_FREE_ENGRAVE_SEAL = StringUtil.getString(busRecord.get("IS_FREE_ENGRAVE_SEAL"));
            if(StringUtils.isNotEmpty(IS_FREE_ENGRAVE_SEAL)){
                String SEAL_PACKAGE = StringUtil.getString(busRecord.get("SEAL_PACKAGE"));
                busRecord.put("FRWZCZ", "橡胶");
                busRecord.put("YZGYS", "");
                if(StringUtils.isNotEmpty(SEAL_PACKAGE) && SEAL_PACKAGE.equals("1")){//套餐一
                    busRecord.put("FRWZCZ", "橡胶");
                    busRecord.put("YZGYS", "漳州市盾安印章制作服务有限公司");
                } else if(StringUtils.isNotEmpty(SEAL_PACKAGE) && SEAL_PACKAGE.equals("2")){//套餐二
                    busRecord.put("FRWZCZ", "牛角");
                    busRecord.put("YZGYS", "平潭综合实验区鑫楚印章制作有限公司");
                }
            } else {
                // 企业公章
                setSeal(busRecord, "OFFICIAL");
                // 财务专用章
                setSeal(busRecord, "FINANCE");
                // 发票专用章
                setSeal(busRecord, "BILL");
                // 合同公章
                setSeal(busRecord, "CONTRACT");
                // 法人印章
                setSeal(busRecord, "LEGALSEAL");
            }       
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 描述 设置印章选中
     * 
     * @author Rider Chen
     * @created 2016年12月20日 下午3:23:46
     * @param busRecord
     * @param key
     * @throws Exception
     */
    private void setSeal(Map<String, Object> busRecord, String key) throws Exception {
        String TYPE = busRecord.get(key + "_TYPE") == null ? "" : busRecord.get(key + "_TYPE").toString();
        busRecord.put(key + "_OTHER", busRecord.get(key + "_SPECOTHER"));
        if (StringUtils.isNotEmpty(TYPE) && TYPE.equals("1")) {
            busRecord.put(key + "_TYPE", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        } else {
            busRecord.put(key + "_TYPE", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        }
        String SPEC = busRecord.get(key + "_SPEC") == null ? "" : busRecord.get(key + "_SPEC").toString();
        busRecord.put(key + "SPEC1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        busRecord.put(key + "SPEC2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        if (StringUtils.isNotEmpty(SPEC) && SPEC.equals("1")) {
            busRecord.put(key + "SPEC1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            busRecord.put(key + "SPEC2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
        } else if (StringUtils.isNotEmpty(SPEC) && SPEC.equals("0")) {
            busRecord.put(key + "SPEC1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            busRecord.put(key + "SPEC2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
        }
        String MATERIAL = busRecord.get(key + "_MATERIAL") == null ? "" : busRecord.get(key + "_MATERIAL").toString();
        busRecord.put(key + "MATERIAL1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        busRecord.put(key + "MATERIAL2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        busRecord.put(key + "MATERIAL3", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        busRecord.put(key + "MATERIAL4", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
        busRecord.put(key + "MATERIAL5", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));

        if (StringUtils.isNotEmpty(MATERIAL) && MATERIAL.equals("01")) {
            busRecord.put(key + "MATERIAL1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        } else if (StringUtils.isNotEmpty(MATERIAL) && MATERIAL.equals("02")) {
            busRecord.put(key + "MATERIAL2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        } else if (StringUtils.isNotEmpty(MATERIAL) && MATERIAL.equals("03")) {
            busRecord.put(key + "MATERIAL3", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        } else if (StringUtils.isNotEmpty(MATERIAL) && MATERIAL.equals("04")) {
            busRecord.put(key + "MATERIAL4", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        } else if (StringUtils.isNotEmpty(MATERIAL) && MATERIAL.equals("05")) {
            busRecord.put(key + "MATERIAL5", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
        }
    }

    /**
     * 描述 设置经理信息
     * 
     * @author Rider Chen
     * @created 2016年12月19日 下午2:22:11
     * @param busRecord
     * @param LEGAL_NAME
     * @param hyjyList
     */
    private void setManager(Map<String, Object> busRecord, String LEGAL_NAME, List<Object> gdhyjyList,
            List<Object> dshyjyList) {
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {

            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            // 删除不在表中存在的字段
            managerList = removeMapKey(managerList, "T_COMMERCIAL_MANAGER");
            busRecord.put("managerList", managerList);

            // 人员构成经理信息
            List<Object> rygcjllist = new LinkedList<Object>();
            // 法定代表人职务
            String LEGAL_JOB = (String) busRecord.get("LEGAL_JOB");
            // 替换经理字典数据
            for (Map<String, Object> map : managerList) {
                String MANAGER_JOB = (String) map.get("MANAGER_JOB");
                // 职务
                getDicName(map, "ssdjzw", "MANAGER_JOB");
                // 证件类型
                getDicName(map, "DocumentType", "MANAGER_IDTYPE");
                // 产生方式
                getDicName(map, "ssdjcsfs", "MANAGER_GENERATION_MODE");
                String MANAGER_NAME = map.get("MANAGER_NAME") == null ? "" : map.get("MANAGER_NAME").toString();

                // 任命方
                String MANAGER_APPOINTOR = map.get("MANAGER_APPOINTOR") == null ? ""
                        : map.get("MANAGER_APPOINTOR").toString();
                StringBuffer GDHYJY = new StringBuffer();
                StringBuffer DSHYJY = new StringBuffer();
                if (StringUtils.isNotEmpty(MANAGER_APPOINTOR) && (MANAGER_APPOINTOR.equals("股东会")
                        || MANAGER_APPOINTOR.equals("股东") || MANAGER_APPOINTOR.equals("投资者"))) {
                    GDHYJY.append(map.get("MANAGER_GENERATION_MODE")).append(MANAGER_NAME).append("担任公司")
                            .append(map.get("MANAGER_JOB"));
                    if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(MANAGER_NAME)
                            && MANAGER_JOB.equals(LEGAL_JOB)) {
                        GDHYJY.append("兼法定代表人");
                    }
                    GDHYJY.append("，任期").append(map.get("MANAGER_OFFICETERM")).append("年。");
                } else if (StringUtils.isNotEmpty(MANAGER_APPOINTOR) && MANAGER_APPOINTOR.equals("董事会")) {
                    DSHYJY.append(map.get("MANAGER_GENERATION_MODE")).append(MANAGER_NAME).append("担任公司")
                            .append(map.get("MANAGER_JOB"));
                    if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(MANAGER_NAME)
                            && MANAGER_JOB.equals(LEGAL_JOB)) {
                        DSHYJY.append("兼法定代表人");
                    }
                    DSHYJY.append("，任期").append(map.get("MANAGER_OFFICETERM")).append("年。");
                }
                if (null != DSHYJY && !DSHYJY.toString().equals("")) {
                    dshyjyList.add(DSHYJY.toString());
                }
                if (null != GDHYJY && !GDHYJY.toString().equals("")) {
                    gdhyjyList.add(GDHYJY.toString());
                }

                // 设置人员构成
                rygcjllist.add(map.get("MANAGER_JOB") + "：" + MANAGER_NAME + "  国籍：" + map.get("MANAGER_COUNTRY")
                        + " 委派方：" + MANAGER_APPOINTOR);
            }
            busRecord.put("rygcjllist", rygcjllist);
        }
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2017年3月1日 下午1:04:15
     * @param managerList
     * @param tableName
     */
    private List<Map<String, Object>> removeMapKey(List<Map<String, Object>> oldlist, String tableName) {
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        if (dictionaryService == null) {
            dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        }
        List<TableColumn> column = dictionaryService.findColumns(tableName);
        Map<String, Object> newMap = null;
        Set<String> key = null;
        for (Map<String, Object> map : oldlist) {
            newMap = new HashMap<String, Object>();
            key = map.keySet();
            for (String string : key) {
                for (TableColumn tableColumn : column) {
                    if (string.equals(tableColumn.getColumnName())) {
                        newMap.put(string, map.get(string));
                    }
                }
            }
            newList.add(newMap);
        }
        return newList;
    }

    /**
     * 描述 设置监理信息
     * 
     * @author Rider Chen
     * @created 2016年12月19日 下午2:21:53
     * @param busRecord
     * @param LEGAL_NAME
     * @param hyjyList
     */
    private void setSupervisor(Map<String, Object> busRecord, String LEGAL_NAME, List<Object> gdhyjyList,
            List<Object> dshyjyList) {
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);

            busRecord.put("jsrysl", supervisorList.size() + "");

            // 删除不在表中存在的字段
            supervisorList = removeMapKey(supervisorList, "T_COMMERCIAL_SUPERVISOR");
            busRecord.put("supervisorList", supervisorList);
            String JSNAMES = "";
            // 人员构成监事信息
            List<Object> rygcjslist = new LinkedList<Object>();
            // 法定代表人职务
            String LEGAL_JOB = (String) busRecord.get("LEGAL_JOB");

            // 监事会议决议
            List<Object> jshyjyList = new LinkedList<Object>();
            int zgjsNum = 0;
            String zgjsName = "";
            String qtjsName = "";
            // 替换监理字典数据
            for (Map<String, Object> map : supervisorList) {
                String SUPERVISOR_JOB = (String) map.get("SUPERVISOR_JOB");
                String fileId = (String) busRecord.get("FILEID");
                if (StringUtils.isNotEmpty(fileId) && fileId.equals("2c90b38a5c3dfb96015c3e5d542310ea")) {
                    if (StringUtils.isNotEmpty(SUPERVISOR_JOB) && SUPERVISOR_JOB.equals("11")) {

                        // 职务
                        getDicName(map, "ssdjzw", "SUPERVISOR_JOB");
                        // 证件类型
                        getDicName(map, "DocumentType", "SUPERVISOR_IDTYPE");
                        // 产生方式
                        getDicName(map, "ssdjcsfs", "SUPERVISOR_GENERATION_MODE");
                        String SUPERVISOR_NAME = map.get("SUPERVISOR_NAME") == null ? ""
                                : map.get("SUPERVISOR_NAME").toString();
                        JSNAMES += "，" + SUPERVISOR_NAME;
                        // 设置监事会主席
                        if (StringUtils.isNotEmpty(SUPERVISOR_JOB) && SUPERVISOR_JOB.equals("11")) {
                            busRecord.put("JSHZXNAME", SUPERVISOR_NAME);
                        }
                        // 任命方
                        String SUPERVISOR_APPOINTOR = map.get("SUPERVISOR_APPOINTOR") == null ? ""
                                : map.get("SUPERVISOR_APPOINTOR").toString();
                        // 设置职工监事人员
                        if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && SUPERVISOR_APPOINTOR.equals("职工代表大会")) {
                            zgjsNum++;
                            zgjsName += "，" + SUPERVISOR_NAME;
                        } else {
                            qtjsName += "，" + SUPERVISOR_NAME;
                        }
                        StringBuffer GDHYJY = new StringBuffer();
                        StringBuffer DSHYJY = new StringBuffer();
                        if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && (SUPERVISOR_APPOINTOR.equals("股东会")
                                || SUPERVISOR_APPOINTOR.equals("股东") || SUPERVISOR_APPOINTOR.equals("投资者"))) {
                            GDHYJY.append(map.get("SUPERVISOR_GENERATION_MODE")).append(SUPERVISOR_NAME).append("担任公司")
                                    .append(map.get("SUPERVISOR_JOB"));
                            if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(SUPERVISOR_NAME)
                                    && SUPERVISOR_JOB.equals(LEGAL_JOB)) {
                                GDHYJY.append("兼法定代表人");
                            }
                            GDHYJY.append("，任期").append(map.get("SUPERVISOR_OFFICETERM")).append("年。");
                        } else if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && SUPERVISOR_APPOINTOR.equals("董事会")) {
                            DSHYJY.append(map.get("SUPERVISOR_GENERATION_MODE")).append(SUPERVISOR_NAME).append("担任公司")
                                    .append(map.get("SUPERVISOR_JOB"));
                            if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(SUPERVISOR_NAME)
                                    && SUPERVISOR_JOB.equals(LEGAL_JOB)) {
                                DSHYJY.append("兼法定代表人");
                            }
                            DSHYJY.append("，任期").append(map.get("SUPERVISOR_OFFICETERM")).append("年。");
                        }

                        StringBuffer JSHYJY = new StringBuffer();
                        JSHYJY.append(map.get("SUPERVISOR_GENERATION_MODE")).append(SUPERVISOR_NAME).append("担任公司")
                                .append(map.get("SUPERVISOR_JOB"));
                        JSHYJY.append("，任期").append(map.get("SUPERVISOR_OFFICETERM")).append("年。");
                        if (null != JSHYJY && !JSHYJY.toString().equals("")) {
                            jshyjyList.add(JSHYJY.toString());
                        }
                        if (null != DSHYJY && !DSHYJY.toString().equals("")) {
                            dshyjyList.add(DSHYJY.toString());
                        }
                        if (null != GDHYJY && !GDHYJY.toString().equals("")) {
                            gdhyjyList.add(GDHYJY.toString());
                        }

                        // 设置人员构成
                        rygcjslist.add(map.get("SUPERVISOR_JOB") + "：" + SUPERVISOR_NAME + "  国籍："
                                + map.get("SUPERVISOR_COUNTRY") + " 委派方：" + SUPERVISOR_APPOINTOR);

                    }
                } else {

                    // 职务
                    getDicName(map, "ssdjzw", "SUPERVISOR_JOB");
                    // 证件类型
                    getDicName(map, "DocumentType", "SUPERVISOR_IDTYPE");
                    // 产生方式
                    getDicName(map, "ssdjcsfs", "SUPERVISOR_GENERATION_MODE");
                    String SUPERVISOR_NAME = map.get("SUPERVISOR_NAME") == null ? ""
                            : map.get("SUPERVISOR_NAME").toString();
                    JSNAMES += "，" + SUPERVISOR_NAME;
                    // 设置监事会主席
                    if (StringUtils.isNotEmpty(SUPERVISOR_JOB) && SUPERVISOR_JOB.equals("11")) {
                        busRecord.put("JSHZXNAME", SUPERVISOR_NAME);
                    }
                    // 任命方
                    String SUPERVISOR_APPOINTOR = map.get("SUPERVISOR_APPOINTOR") == null ? ""
                            : map.get("SUPERVISOR_APPOINTOR").toString();
                    // 设置职工监事人员
                    if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && SUPERVISOR_APPOINTOR.equals("职工代表大会")) {
                        zgjsNum++;
                        zgjsName += "，" + SUPERVISOR_NAME;
                    } else {
                        qtjsName += "，" + SUPERVISOR_NAME;
                    }
                    StringBuffer GDHYJY = new StringBuffer();
                    StringBuffer DSHYJY = new StringBuffer();
                    if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && (SUPERVISOR_APPOINTOR.equals("股东会")
                            || SUPERVISOR_APPOINTOR.equals("股东") || SUPERVISOR_APPOINTOR.equals("投资者"))) {
                        GDHYJY.append(map.get("SUPERVISOR_GENERATION_MODE")).append(SUPERVISOR_NAME).append("担任公司")
                                .append(map.get("SUPERVISOR_JOB"));
                        if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(SUPERVISOR_NAME)
                                && SUPERVISOR_JOB.equals(LEGAL_JOB)) {
                            GDHYJY.append("兼法定代表人");
                        }
                        GDHYJY.append("，任期").append(map.get("SUPERVISOR_OFFICETERM")).append("年。");
                    } else if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && SUPERVISOR_APPOINTOR.equals("董事会")) {
                        DSHYJY.append(map.get("SUPERVISOR_GENERATION_MODE")).append(SUPERVISOR_NAME).append("担任公司")
                                .append(map.get("SUPERVISOR_JOB"));
                        if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(SUPERVISOR_NAME)
                                && SUPERVISOR_JOB.equals(LEGAL_JOB)) {
                            DSHYJY.append("兼法定代表人");
                        }
                        DSHYJY.append("，任期").append(map.get("SUPERVISOR_OFFICETERM")).append("年。");
                    }

                    StringBuffer JSHYJY = new StringBuffer();
                    JSHYJY.append(map.get("SUPERVISOR_GENERATION_MODE")).append(SUPERVISOR_NAME).append("担任公司")
                            .append(map.get("SUPERVISOR_JOB"));
                    JSHYJY.append("，任期").append(map.get("SUPERVISOR_OFFICETERM")).append("年。");
                    if (null != JSHYJY && !JSHYJY.toString().equals("")) {
                        jshyjyList.add(JSHYJY.toString());
                    }
                    if (null != DSHYJY && !DSHYJY.toString().equals("")) {
                        dshyjyList.add(DSHYJY.toString());
                    }
                    if (null != GDHYJY && !GDHYJY.toString().equals("")) {
                        gdhyjyList.add(GDHYJY.toString());
                    }

                    // 设置人员构成
                    rygcjslist.add(map.get("SUPERVISOR_JOB") + "：" + SUPERVISOR_NAME + "  国籍："
                            + map.get("SUPERVISOR_COUNTRY") + " 委派方：" + SUPERVISOR_APPOINTOR);
                }
            }
            // 监事会信息
            if (StringUtils.isNotEmpty(JSNAMES)) {
                JSNAMES = JSNAMES.substring(1, JSNAMES.length());
            }
            busRecord.put("JSNAMES", JSNAMES);
            if (StringUtils.isNotEmpty(zgjsName)) {
                zgjsName = zgjsName.substring(1, zgjsName.length());
            }
            busRecord.put("zgjsName", zgjsName);
            if (StringUtils.isNotEmpty(qtjsName)) {
                qtjsName = qtjsName.substring(1, qtjsName.length());
            }
            busRecord.put("qtjsName", qtjsName);
            busRecord.put("ZGJSSL", zgjsNum + "");
            busRecord.put("rygcjslist", rygcjslist);

            List<Object> list = new LinkedList<Object>();
            for (int i = 0; i < jshyjyList.size(); i++) {
                String value = (String) jshyjyList.get(i);
                list.add("   " + StringUtil.convertInteger(i + 1) + "、" + value);
            }
            busRecord.put("jshyjyList", list);
        }
    }

    /**
     * 描述 设置董事信息
     * 
     * @author Rider Chen
     * @created 2016年12月19日 下午2:21:35
     * @param busRecord
     * @param LEGAL_NAME
     * @param hyjyList
     */
    private void setDirector(Map<String, Object> busRecord, String LEGAL_NAME, List<Object> gdhyjyList,
            List<Object> dshyjyList) {
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            // 删除不在表中存在的字段
            directorList = removeMapKey(directorList, "T_COMMERCIAL_DIRECTOR");
            busRecord.put("directorList", directorList);
            busRecord.put("dsnum", directorList.size() + "名董事组成");

            busRecord.put("dsrysl", directorList.size() + "");
            // 按职务对董事进行
            Collections.sort(directorList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    double sum0 = Double.parseDouble(arg0.get("DIRECTOR_JOB").toString());
                    double sum1 = Double.parseDouble(arg1.get("DIRECTOR_JOB").toString());
                    if (sum1 > sum0) {
                        return -1;
                    } else if (sum1 == sum0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            // 职务最高董事
            busRecord.put("MOSTDIRECTOR", directorList.get(0).get("DIRECTOR_NAME"));
            // 职务最高董事任命方
            busRecord.put("ZGDSRMF", directorList.get(0).get("DIRECTOR_APPOINTOR"));
            // 董事集合
            String DIRECTORNAMES = "";
            // 公司设立性质编码
            String COMPANY_SETNATURE = busRecord.get("COMPANY_SETNATURE") == null ? ""
                    : busRecord.get("COMPANY_SETNATURE").toString();
            String DSNAMES = "";

            List<Object> list = new LinkedList<Object>();
            // 人员构成董事信息
            List<Object> rygcdslist = new LinkedList<Object>();
            // 法定代表人职务
            String LEGAL_JOB = (String) busRecord.get("LEGAL_JOB");
            busRecord.put("FGDSRMF", "");
            int num = 0;
            int FDSZSIZE = 0;
            // 替换董事字典数据
            for (Map<String, Object> map : directorList) {
                String DIRECTOR_JOB = (String) map.get("DIRECTOR_JOB");
                // 设置董事长任命方
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("01")) {
                    // busRecord.put("FGDSRMF", map.get("DIRECTOR_APPOINTOR"));
                }
                // 副董事长
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                    FDSZSIZE++;
                    busRecord.put("FGDSRMF", map.get("DIRECTOR_APPOINTOR"));
                }
                // 设置法定代表人任命方
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals(LEGAL_JOB)) {
                    busRecord.put("FDDBRRMF", map.get("DIRECTOR_APPOINTOR"));
                }
                // 职务
                getDicName(map, "ssdjzw", "DIRECTOR_JOB");
                // 证件类型
                getDicName(map, "DocumentType", "DIRECTOR_IDTYPE");
                // 产生方式
                getDicName(map, "ssdjcsfs", "DIRECTOR_GENERATION_MODE");
                // 董事名称
                String DIRECTOR_NAME = map.get("DIRECTOR_NAME") == null ? "" : map.get("DIRECTOR_NAME").toString();
                DIRECTORNAMES += "、" + DIRECTOR_NAME;
                if (StringUtils.isNotEmpty(COMPANY_SETNATURE)
                        && (COMPANY_SETNATURE.equals("02") || COMPANY_SETNATURE.equals("04"))) {
                    DSNAMES += "、" + DIRECTOR_NAME;
                }
                num++;
                int breakNum = 11;
                if (num >= directorList.size()) {
                    breakNum = 0;
                }
                // 设置委派书
                setDirectorWfsList(busRecord, map, DIRECTOR_NAME, list, breakNum);
                // 设置会议记录
                setDirectorList(LEGAL_NAME, DIRECTOR_JOB, LEGAL_JOB, gdhyjyList, dshyjyList, COMPANY_SETNATURE, map,
                        DIRECTOR_NAME);
                // 设置人员构成
                rygcdslist.add(map.get("DIRECTOR_JOB") + "：" + DIRECTOR_NAME + "  国籍：" + map.get("DIRECTOR_COUNTRY")
                        + " 委派方：" + map.get("DIRECTOR_APPOINTOR"));
            }
            if (FDSZSIZE > 0) {
                busRecord.put("fdszrysl", "副董事长" + FDSZSIZE + "人，");
            } else {
                busRecord.put("fdszrysl", "");
            }
            busRecord.put("rygcdslist", rygcdslist);
            busRecord.put("DirectorWfsList", list);
            if (StringUtils.isNotEmpty(DIRECTORNAMES)) {
                DIRECTORNAMES = DIRECTORNAMES.substring(1, DIRECTORNAMES.length());
                busRecord.put("DIRECTORNAMES", DIRECTORNAMES);
            }
            // 董事会信息
            StringBuffer DSHXX = new StringBuffer();
            if (StringUtils.isNotEmpty(DSNAMES)) {
                DSNAMES = DSNAMES.substring(1, DSNAMES.length());
                DSHXX.append(directorList.get(0).get("DIRECTOR_GENERATION_MODE")).append(DSNAMES).append("担任公司首届董事会董事")
                        .append("，任期三年。");
            }
            if (null != DSHXX && !DSHXX.toString().equals("")) {
                gdhyjyList.add(DSHXX.toString());
            }
        }

    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月22日 上午10:29:31
     * @param busRecord
     * @param map
     * @param DIRECTOR_NAME
     */
    private void setDirectorWfsList(Map<String, Object> busRecord, Map<String, Object> map, String DIRECTOR_NAME,
            List<Object> list, int num) {
        list.add("                           委派书");
        list.add("");
        list.add("  根据《公司法》、《中外合资企业法》、《平潭综合实验区商事登记管理办法》以及" + busRecord.get("COMPANY_NAME") + "章程的相关规定，现决定委派"
                + DIRECTOR_NAME + "（证件号：" + map.get("DIRECTOR_IDNO") + "）担任" + busRecord.get("COMPANY_NAME") + "的"
                + map.get("DIRECTOR_JOB") + "，以上人员任期均为三年，且符合相关法律、法规以及本公司章程关于法定代表人、董事等高级管理人员的任职规定。");
        list.add("");
        list.add("");
        list.add("                               委派人:" + map.get("DIRECTOR_APPOINTOR"));
        list.add("                               " + busRecord.get("FILL_DATE"));
        for (int i = 0; i < num; i++) {
            list.add("");
        }
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月22日 上午10:20:41
     * @param LEGAL_NAME
     * @param gdhyjyList
     * @param dshyjyList
     * @param COMPANY_SETNATURE
     * @param map
     * @param DIRECTOR_NAME
     */
    private void setDirectorList(String LEGAL_NAME, String DIRECTOR_JOB, String LEGAL_JOB, List<Object> gdhyjyList,
            List<Object> dshyjyList, String COMPANY_SETNATURE, Map<String, Object> map, String DIRECTOR_NAME) {
        // 任命方
        String DIRECTOR_APPOINTOR = map.get("DIRECTOR_APPOINTOR") == null ? ""
                : map.get("DIRECTOR_APPOINTOR").toString();
        StringBuffer GDHYJY = new StringBuffer();
        StringBuffer DSHYJY = new StringBuffer();
        if (StringUtils.isNotEmpty(DIRECTOR_APPOINTOR) && (DIRECTOR_APPOINTOR.equals("股东会")
                || DIRECTOR_APPOINTOR.equals("股东") || DIRECTOR_APPOINTOR.equals("投资者"))) {
            if (StringUtils.isEmpty(COMPANY_SETNATURE) || COMPANY_SETNATURE.equals("01")
                    || COMPANY_SETNATURE.equals("03")
                    || (!COMPANY_SETNATURE.equals("02") && !COMPANY_SETNATURE.equals("04"))) {
                GDHYJY = new StringBuffer();
                GDHYJY.append(map.get("DIRECTOR_GENERATION_MODE")).append(DIRECTOR_NAME).append("担任公司")
                        .append(map.get("DIRECTOR_JOB"));
                if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                        && DIRECTOR_JOB.equals(LEGAL_JOB)) {
                    GDHYJY.append("兼法定代表人");
                }
                GDHYJY.append("，任期").append(map.get("DIRECTOR_OFFICETERM")).append("年。");
            }

        } else if (StringUtils.isNotEmpty(DIRECTOR_APPOINTOR) && DIRECTOR_APPOINTOR.equals("董事会")) {
            DSHYJY.append(map.get("DIRECTOR_GENERATION_MODE")).append(DIRECTOR_NAME).append("担任公司")
                    .append(map.get("DIRECTOR_JOB"));
            if (StringUtils.isNotEmpty(LEGAL_NAME) && LEGAL_NAME.equals(DIRECTOR_NAME)
                    && DIRECTOR_JOB.equals(LEGAL_JOB)) {
                DSHYJY.append("兼法定代表人");
            }
            DSHYJY.append("，任期").append(map.get("DIRECTOR_OFFICETERM")).append("年。");
        }
        if (StringUtils.isEmpty(COMPANY_SETNATURE) || COMPANY_SETNATURE.equals("01") || COMPANY_SETNATURE.equals("03")
                || (!COMPANY_SETNATURE.equals("02") && !COMPANY_SETNATURE.equals("04"))) {
            if (null != GDHYJY && !GDHYJY.toString().equals("")) {
                gdhyjyList.add(GDHYJY.toString());
            }
        }
        if (null != DSHYJY && !DSHYJY.toString().equals("")) {
            dshyjyList.add(DSHYJY.toString());
        }
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月15日 下午2:51:47
     * @param map
     * @param typeCode
     * @param name
     */
    public void getDicName(Map<String, Object> map, String typeCode, String name) {
        String value = map.get(name) == null ? "" : map.get(name).toString();
        if (dictionaryService == null) {
            dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        }
        map.put(name, dictionaryService.getDicNames(typeCode, value));
    }

    /**
     * 描述 用于取得复选框的替换值
     * 
     * @param map
     * @param typeCode
     *            字典类型
     * @param name
     *            字段名
     * @param joinMark
     *            连接符号
     */
    public void getDicNameOfCheck(Map<String, Object> map, String typeCode, String name, String joinMark) {
        String value = map.get(name) == null ? "" : map.get(name).toString();
        if (dictionaryService == null) {
            dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        }
        String[] values = value.split(",");
        StringBuffer dicNames = new StringBuffer("");
        for (String v : values) {
            String dicName = dictionaryService.getDicNames(typeCode, v);
            dicNames.append(dicName).append(joinMark);
        }
        map.put(name, dicNames.substring(0, dicNames.length() - 1).toString());
    }

    /**
     * 
     * 描述 设置股东信息
     * 
     * @author Rider Chen
     * @created 2016年12月14日 下午3:16:11
     * @param busRecord
     */
    private void setHolder(Map<String, Object> busRecord) {
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            // 删除不在表中存在的字段
            holderList = removeMapKey(holderList, "T_COMMERCIAL_SHAREHOLDER");
            StringBuffer HOLDERNAMES = new StringBuffer();
            StringBuffer PARTNERNAMES = new StringBuffer();
            StringBuffer LEGALNAMES = new StringBuffer();
            StringBuffer partCountrys = new StringBuffer();
            StringBuffer LICENCEAPPOINTNAMES = new StringBuffer();
            double PAIDINQUOTAS = 0;
            String MOSTHOLDER = "";
            // 企业或者其他投资人
            List<Object> list1 = new LinkedList<Object>();
            // 按投资总额进行倒序
            Collections.sort(holderList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    double sum0 = Double.parseDouble(arg0.get("CONTRIBUTIONS").toString());
                    double sum1 = Double.parseDouble(arg1.get("CONTRIBUTIONS").toString());
                    if (sum1 > sum0) {
                        return 1;
                    } else if (sum1 == sum0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            if (null != holderList && holderList.size() > 0) {
                MOSTHOLDER = holderList.get(0).get("SHAREHOLDER_NAME").toString();
                busRecord.put("MOSTHOLDER", MOSTHOLDER);
                busRecord.put("HOLDERSIZE", holderList.size());
                busRecord.put("HOLDERCOUNT", holderList.size() + 1);
            }
            // 执行事务合伙人
            List<Map<String, Object>> partnerList = new ArrayList<Map<String, Object>>();
            int YXHHRNUM = 0;
            // 替换企业字典数据
            for (Map<String, Object> map : holderList) {
                // 拼接股东名称
                String name = map.get("SHAREHOLDER_NAME") == null ? "" : map.get("SHAREHOLDER_NAME").toString();
                if (StringUtils.isNotEmpty(HOLDERNAMES)) {
                    HOLDERNAMES.append("、").append(name);
                } else {
                    HOLDERNAMES.append(name);
                }
                String holderType = map.get("SHAREHOLDER_TYPE") == null ? "" : map.get("SHAREHOLDER_TYPE").toString();
                // 股东类型
                getDicName(map, "ssdjgdlx", "SHAREHOLDER_TYPE");
                // 证件类型
                getDicName(map, "DocumentType", "LICENCE_TYPE");
                // 设置出资方式
                setInvestmentType(map);
                // 承担责任方式
                String DUTY_MODE = map.get("DUTY_MODE") == null ? "" : map.get("DUTY_MODE").toString();
                if (StringUtils.isNotEmpty(DUTY_MODE)) {
                    if ("有限责任".equals(DUTY_MODE)) {
                        map.put("SHAREHOLDER_NAME", "有限合伙人：" + name);
                        YXHHRNUM++;
                    } else if ("无限责任".equals(DUTY_MODE)) {
                        map.put("SHAREHOLDER_NAME", "普通合伙人：" + name);
                    }
                }
                // 是否执行事务合伙人
                String IS_PARTNERSHIP = map.get("IS_PARTNERSHIP") == null ? "" : map.get("IS_PARTNERSHIP").toString();
                if (StringUtils.isNotEmpty(IS_PARTNERSHIP) && "1".equals(IS_PARTNERSHIP)) {
                    Map<String, Object> partner = new HashMap<String, Object>();
                    String LICENCE_TYPE = map.get("LICENCE_TYPE").toString();
                    String LICENCE_NO = map.get("LICENCE_NO").toString();
                    if (!holderType.equals("zrr")) {
                        getHolderList(list1, map, busRecord);
                        if (null != map.get("LICENCE_APPOINT_NAME") && !"".equals(map.get("LICENCE_APPOINT_NAME"))) {
                            name = name + "（委派代表：" + map.get("LICENCE_APPOINT_NAME") + "）";
                        }
                        // 证件类型
                        getDicName(map, "DocumentType", "LICENCE_APPOINT_IDTYPE");
                        LICENCE_TYPE = LICENCE_TYPE + "、" + map.get("LICENCE_APPOINT_IDTYPE");
                        LICENCE_NO = LICENCE_NO + "、" + map.get("LICENCE_APPOINT_IDNO");
                    }
                    partner.put("PARTNER_NAME", name);
                    partner.put("PARTNER_MOBILE", map.get("CONTACT_WAY"));
                    partner.put("PARTNER_IDTYPE", LICENCE_TYPE);
                    partner.put("PARTNER_IDNO", LICENCE_NO);
                    String partCountry = map.get("SHAREHOLDER_COMPANYCOUNTRY") == null ? ""
                            : map.get("SHAREHOLDER_COMPANYCOUNTRY").toString();
                    if (StringUtils.isNotEmpty(PARTNERNAMES)) {
                        PARTNERNAMES.append("、").append(name);
                        partCountrys.append("、").append(partCountry);
                    } else {
                        PARTNERNAMES.append(name);
                        partCountrys.append(partCountry);
                    }
                    partnerList.add(partner);
                }
                // 合伙企业或其他组织机构法定代表人姓名
                String LICENCE_APPOINT_NAME = map.get("LICENCE_APPOINT_NAME") == null ? ""
                        : map.get("LICENCE_APPOINT_NAME").toString();
                if (StringUtils.isNotEmpty(LICENCE_APPOINT_NAME)) {
                    if (StringUtils.isNotEmpty(LICENCEAPPOINTNAMES)) {
                        LICENCEAPPOINTNAMES.append("、").append(LICENCE_APPOINT_NAME);
                    } else {
                        LICENCEAPPOINTNAMES.append(LICENCE_APPOINT_NAME);
                    }
                }
                // 合伙企业或其他组织机构法定代表人姓名
                String LEGAL_PERSON = map.get("LEGAL_PERSON") == null ? "" : map.get("LEGAL_PERSON").toString();
                if (StringUtils.isNotEmpty(LEGAL_PERSON)) {
                    if (StringUtils.isNotEmpty(LEGALNAMES)) {
                        LEGALNAMES.append("、").append(LEGAL_PERSON);
                    } else {
                        LEGALNAMES.append(LEGAL_PERSON);
                    }
                }
                // 实缴出资额（万元）
                double PAIDIN_QUOTA = map.get("PAIDIN_QUOTA") == null ? 0
                        : Double.parseDouble(map.get("PAIDIN_QUOTA").toString());
                PAIDINQUOTAS = PAIDINQUOTAS + PAIDIN_QUOTA;
                if (null != map.get("PAIDIN_DATE") && StringUtils.isNotEmpty(map.get("PAIDIN_DATE").toString())) {
                    map.put("PAIDIN_DATE", DateTimeUtil.getChinaDate(map.get("PAIDIN_DATE").toString()));
                }
            }
            // 股东名称集合（如：股东1,股东2）
            busRecord.put("HOLDERNAMES", String.valueOf(HOLDERNAMES));
            // 执行事务合伙人
            busRecord.put("PARTNERNAMES", String.valueOf(PARTNERNAMES));
            busRecord.put("PARTCOUNTRYS", String.valueOf(partCountrys));
            // 合伙企业或其他组织机构法定代表人
            busRecord.put("LEGALNAMES", String.valueOf(LEGALNAMES));
            // 委派代表
            busRecord.put("LICENCEAPPOINTNAMES", String.valueOf(LICENCEAPPOINTNAMES));
            // 实缴出资总额
            busRecord.put("PAIDINQUOTAS", String.valueOf(PAIDINQUOTAS));
            busRecord.put("holderList", holderList);
            busRecord.put("fahqtzjwfdbList", list1);
            if (null == partnerList || partnerList.size() == 0) {
                partnerList = new ArrayList<Map<String, Object>>();
                Map<String, Object> partner = new HashMap<String, Object>();
                partner.put("PARTNER_NAME", "");
                partner.put("PARTNER_MOBILE", "");
                partner.put("PARTNER_IDTYPE", "");
                partner.put("PARTNER_IDNO", "");
                partnerList.add(partner);
            }
            // 执行事务合伙人
            busRecord.put("partnerList", partnerList);
            // 有限合伙人数
            busRecord.put("YXHHRNUM", YXHHRNUM);

        }

    }

    /**
     * 
     * 描述 拼接法人或其他组织委派代表的委托书
     * 
     * @author Rider Chen
     * @created 2018年6月19日 上午9:28:11
     * @param list1
     * @param map
     */
    private void getHolderList(List<Object> list1, Map<String, Object> map, Map<String, Object> busRecord) {
        list1.add("法人或其他组织委派代表的委托书");
        list1.add("");
        list1.add("    我单位作为合伙企业 " + busRecord.get("COMPANY_NAME") + " 的执行事务合伙人，现委托 " + map.get("LICENCE_APPOINT_NAME")
                + " 代表我单位执行合伙事务。");
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("委托单位法定代表人（负责人）签字：");
        list1.add("");
        list1.add("                                           委托单位印章");
        list1.add("                                           " + busRecord.get("FILL_DATE"));
    }

    /**
     * 
     * 描述 拼接法人或其他组织委派代表的委托书
     * 
     * @author Rider Chen
     * @created 2018年6月19日 上午9:28:11
     * @param list1
     * @param map
     */
    private void getInvestorList(List<Object> list1, Map<String, Object> map, Map<String, Object> busRecord) {
        list1.add("法人或其他组织委派代表的委托书");
        list1.add("");
        list1.add("    我单位作为合伙企业 " + busRecord.get("COMPANY_NAME") + " 的执行事务合伙人，现委托 " + map.get("INVESTOR_APPOINT_NAME")
                + " 代表我单位执行合伙事务。");
        list1.add("");
        list1.add("");
        list1.add("");
        list1.add("委托单位法定代表人（负责人）签字：");
        list1.add("");
        list1.add("                                           委托单位印章");
        list1.add("                                           " + busRecord.get("FILL_DATE"));
    }

    /**
     * 描述
     * 
     * @author Rider Chen
     * @created 2016年12月21日 下午2:34:02
     * @param busRecord
     */
    private void getTypeName(Map<String, Object> busRecord) {
        // 企业说明
        getDicName(busRecord, "ssdjqyfl", "ENTERPRISE_STATEMENT");
        // 法定代表人职务
        getDicName(busRecord, "ssdjzw", "LEGAL_JOB");
        // 法定代表人证件类型
        getDicName(busRecord, "DocumentType", "LEGAL_IDTYPE");
        // 法定代表人产生方式
        getDicName(busRecord, "ssdjcsfs", "LEGAL_PRODUCEMODE");
        // 经办人证件类型
        getDicName(busRecord, "DocumentType", "OPERATOR_IDTYPE");
        // 办税人证件类型
        getDicName(busRecord, "DocumentType", "FINANCE_IDTYPE");
        // 财务负责人证件类型
        getDicName(busRecord, "DocumentType", "TAXMAN_IDTYPE");
        // 联络员信息证件类型
        getDicName(busRecord, "DocumentType", "LIAISON_IDTYPE");
        // 取得方式类型
        getDicName(busRecord, "wayOfGet", "RESIDENCE_WAYOFGET");
        // 军队房产类型
        getDicName(busRecord, "armyHourse", "ARMY_HOURSE");
        busRecord.put("ARMYHOURSE_REMARKS", StringUtil.getString(busRecord.get("ARMYHOURSE_REMARKS")));
        // 注册地址取得方式类型
        getDicName(busRecord, "wayOfGet", "RESIDENCE_REGISTER_WAYOFGET");
        // 注册地址军队房产类型
        getDicName(busRecord, "armyHourse", "ARMY_REGISTER_HOURSE");
        busRecord.put("ARMYHOURSE_REGISTER_REMARKS",
                StringUtil.getString(busRecord.get("ARMYHOURSE_REGISTER_REMARKS")));
        /************ 个独 *********/
        // 投资人证件类型
        getDicName(busRecord, "DocumentType", "INVESTOR_IDTYPE");
        // 投资人性别
        getDicName(busRecord, "sex", "INVESTOR_SEX");
        // 投资人民族
        getDicName(busRecord, "nation", "INVESTOR_NATION");
        // 投资人文化程度
        getDicName(busRecord, "degree", "INVESTOR_DEGREE");
        // 投资人政治面貌
        getDicName(busRecord, "political", "INVESTOR_POLITICAL");

        /************ 个体 *********/
        // 政治面貌
        getDicName(busRecord, "political", "DEALER_POLITICAL");
        // 民族
        getDicName(busRecord, "nation", "DEALER_NATION");
        // 文化程度
        getDicName(busRecord, "degree", "DEALER_DEGREE");
        // 性别
        getDicName(busRecord, "sex", "DEALER_SEX");
        /************ 变更 *********/
        // 勾选事项
        getDicName(busRecord, "changeRegs", "CHANGEREGS");
    }

    /**
     * 描述 格式化时间
     * 
     * @author Rider Chen
     * @created 2016年12月20日 上午11:11:24
     * @param busRecord
     */
    private void formatData(Map<String, Object> busRecord) {
        /* 外资实缴出资额时间对比 */
        String PAYMENT_PERIOD = busRecord.get("PAYMENT_PERIOD") == null ? ""
                : busRecord.get("PAYMENT_PERIOD").toString();
        String CREATE_TIME = busRecord.get("CREATE_TIME") == null ? "" : busRecord.get("CREATE_TIME").toString();
        int i = StringUtil.compareDate(PAYMENT_PERIOD, CREATE_TIME.substring(0, 10));
        busRecord.put("COMPARE_TIME", i);
        // 出资全部缴付 到位期限格式化
        if (StringUtils.isNotEmpty(PAYMENT_PERIOD)) {
            busRecord.put("PAYMENT_PERIOD", DateTimeUtil.getChinaDate(PAYMENT_PERIOD) + "前缴清");
        }
        // 出租（借）开始时间
        String LEASE_START_DATE = busRecord.get("LEASE_START_DATE") == null ? ""
                : busRecord.get("LEASE_START_DATE").toString();
        if (StringUtils.isNotEmpty(LEASE_START_DATE)) {
            busRecord.put("LEASE_START_DATE", DateTimeUtil.getChinaDate(LEASE_START_DATE));
        }
        // 出租（借）结束时间
        String LEASE_END_DATE = busRecord.get("LEASE_END_DATE") == null ? ""
                : busRecord.get("LEASE_END_DATE").toString();
        if (StringUtils.isNotEmpty(LEASE_END_DATE)) {
            busRecord.put("LEASE_END_DATE", DateTimeUtil.getChinaDate(LEASE_END_DATE));
        }
        // 租赁合同签订时间
        String SINGING_TIME = busRecord.get("SINGING_TIME") == null ? "" : busRecord.get("SINGING_TIME").toString();
        if (StringUtils.isNotEmpty(SINGING_TIME)) {
            busRecord.put("SINGING_TIME", DateTimeUtil.getChinaDate(SINGING_TIME));
        }
        // 填表时间格式化
        String FILL_DATE = busRecord.get("FILL_DATE") == null ? "" : busRecord.get("FILL_DATE").toString();
        if (StringUtils.isNotEmpty(FILL_DATE)) {
            busRecord.put("FILL_DATE", DateTimeUtil.getChinaDate(FILL_DATE));
        }
        // 指定或委托的有效期限格式化
        String VALIDITY_START_DATE = busRecord.get("VALIDITY_START_DATE") == null ? ""
                : busRecord.get("VALIDITY_START_DATE").toString();
        if (StringUtils.isNotEmpty(VALIDITY_START_DATE)) {
            busRecord.put("VALIDITY_START_DATE", DateTimeUtil.getChinaDate(VALIDITY_START_DATE));
        }

        // 指定或委托的有效期限格式化
        String VALIDITY_END_DATE = busRecord.get("VALIDITY_END_DATE") == null ? ""
                : busRecord.get("VALIDITY_END_DATE").toString();
        if (StringUtils.isNotEmpty(VALIDITY_END_DATE)) {
            busRecord.put("VALIDITY_END_DATE", DateTimeUtil.getChinaDate(VALIDITY_END_DATE));
        }
        // 采集时间
        String COLLECT_TIME = busRecord.get("COLLECT_TIME") == null ? "" : busRecord.get("COLLECT_TIME").toString();
        if (StringUtils.isNotEmpty(COLLECT_TIME)) {
            busRecord.put("COLLECT_TIME", DateTimeUtil.getChinaDate(COLLECT_TIME));
        }
        // 创建时间
        if (StringUtils.isNotEmpty(CREATE_TIME)) {
            busRecord.put("CREATE_TIME", DateTimeUtil.getChinaDate(CREATE_TIME));
        }
    }

    /**
     * 描述 设置出资方式
     * 
     * @author Rider Chen
     * @created 2016年12月15日 下午4:28:40
     * @param map
     */
    private void setInvestmentType(Map<String, Object> map) {
        String INVESTMENTTYPE1 = "";
        String INVESTMENTTYPE2 = "";
        String currency = "";
        boolean isCurrency = false;
        boolean isNotCurrency = false;
        List<Object> list1 = new LinkedList<Object>();
        List<Object> list2 = new LinkedList<Object>();
        double huobi = 0;
        // 出资方式（万元）（现金_境外外汇）
        String INVESTMENT_ABROAD_EXCHANGE = map.get("INVESTMENT_ABROAD_EXCHANGE") == null ? ""
                : map.get("INVESTMENT_ABROAD_EXCHANGE").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_ABROAD_EXCHANGE) && !INVESTMENT_ABROAD_EXCHANGE.equals("0")) {
            INVESTMENTTYPE1 += ",境外现汇";
            isCurrency = true;
            huobi = huobi + Double.parseDouble(INVESTMENT_ABROAD_EXCHANGE);
        }
        // 出资方式（万元）（现金_境外人民币）
        String INVESTMENT_ABROAD_RMB = map.get("INVESTMENT_ABROAD_RMB") == null ? ""
                : map.get("INVESTMENT_ABROAD_RMB").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_ABROAD_RMB) && !INVESTMENT_ABROAD_RMB.equals("0")) {
            INVESTMENTTYPE1 += ",境外人民币";
            isCurrency = true;
            huobi = huobi + Double.parseDouble(INVESTMENT_ABROAD_RMB);
        }
        // 出资方式（万元）（现金_境内人民币）
        String INVESTMENT_DOMESTIC_RMB = map.get("INVESTMENT_DOMESTIC_RMB") == null ? ""
                : map.get("INVESTMENT_DOMESTIC_RMB").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_DOMESTIC_RMB) && !INVESTMENT_DOMESTIC_RMB.equals("0")) {
            INVESTMENTTYPE1 += ",境内人民币";
            isCurrency = true;
            huobi = huobi + Double.parseDouble(INVESTMENT_DOMESTIC_RMB);
        }
        // 出资方式（万元）（现金）
        String INVESTMENT_CASH = map.get("INVESTMENT_CASH") == null ? "" : map.get("INVESTMENT_CASH").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_CASH) && !INVESTMENT_CASH.equals("0")) {
            INVESTMENTTYPE1 += ",现金";
            isCurrency = true;
            huobi = huobi + Double.parseDouble(INVESTMENT_CASH);
        }

        if (isCurrency) {
            currency = "货币";
            list1.add("货币");
            list2.add("货币:" + huobi);
        }

        // 出资方式（万元）（实物）
        String INVESTMENT_MATERIAL = map.get("INVESTMENT_MATERIAL") == null ? ""
                : map.get("INVESTMENT_MATERIAL").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_MATERIAL) && !INVESTMENT_MATERIAL.equals("0")) {
            INVESTMENTTYPE2 += ",实物";
            isNotCurrency = true;

            list1.add("实物");
            list2.add("实物:" + INVESTMENT_MATERIAL);
        }
        // 出资方式（万元）（技术出资）
        String INVESTMENT_TECHNOLOGY = map.get("INVESTMENT_TECHNOLOGY") == null ? ""
                : map.get("INVESTMENT_TECHNOLOGY").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_TECHNOLOGY) && !INVESTMENT_TECHNOLOGY.equals("0")) {
            INVESTMENTTYPE2 += ",技术出资";
            isNotCurrency = true;
            list1.add("技术出资");
            list2.add("技术出资:" + INVESTMENT_TECHNOLOGY);
        }
        // 出资方式（万元）（土地使用权）
        String INVESTMENT_LAND = map.get("INVESTMENT_LAND") == null ? "" : map.get("INVESTMENT_LAND").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_LAND) && !INVESTMENT_LAND.equals("0")) {
            INVESTMENTTYPE2 += ",土地使用权";
            isNotCurrency = true;
            list1.add("土地使用权");
            list2.add("土地使用权:" + INVESTMENT_LAND);
        }
        // 出资方式（万元）（股权）
        String INVESTMENT_STOCK = map.get("INVESTMENT_STOCK") == null ? "" : map.get("INVESTMENT_STOCK").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_STOCK) && !INVESTMENT_STOCK.equals("0")) {
            INVESTMENTTYPE2 += ",股权";
            isNotCurrency = true;
            list1.add("股权");
            list2.add("股权:" + INVESTMENT_STOCK);
        }
        // 出资方式（万元）（其他）
        String INVESTMENT_OTHER = map.get("INVESTMENT_OTHER") == null ? "" : map.get("INVESTMENT_OTHER").toString();
        if (StringUtils.isNotEmpty(INVESTMENT_OTHER) && !INVESTMENT_OTHER.equals("0")) {
            INVESTMENTTYPE2 += ",其他";
            isNotCurrency = true;
            list1.add("其他");
            list2.add("其他:" + INVESTMENT_OTHER);
        }
        if (StringUtils.isNotEmpty(INVESTMENTTYPE1)) {
            INVESTMENTTYPE1 = INVESTMENTTYPE1.substring(1, INVESTMENTTYPE1.length());
        }
        if (StringUtils.isNotEmpty(INVESTMENTTYPE2)) {
            INVESTMENTTYPE2 = INVESTMENTTYPE2.substring(1, INVESTMENTTYPE2.length());
        }
        if (isNotCurrency) {
            if (StringUtils.isNotEmpty(currency)) {
                currency += ",";
            }
            currency += "非货币";
        } else {
            list2 = new LinkedList<Object>();
            list2.add(huobi + "");
        }
        map.put("CZFS", list1);
        map.put("RJCZE", list2);
    }

    /**
     * 
     * 描述 设置内资股份
     * 
     * @author Panda Chen
     * @created 2018年6月11日 上午9:50:11
     * @param busRecord
     */
    private void setNzgf(Map<String, Object> busRecord) {

        String LEGAL_JOB = busRecord.get("LEGAL_JOB") == null ? "" : busRecord.get("LEGAL_JOB").toString();
        String nzgffrzw = LEGAL_JOB;
        // String nzgffrzw = "";
        // if(StringUtils.isNotEmpty(LEGAL_JOB)){
        // if("01".equals(LEGAL_JOB)){
        // nzgffrzw = "董事长";
        // }
        // if("20".equals(LEGAL_JOB)){
        // nzgffrzw = "经理";
        // }
        // }
        busRecord.put("nzgffrzw", nzgffrzw);
        String ESTABLISH_MODE = busRecord.get("ESTABLISH_MODE") + "";
        try {
            if (StringUtils.isBlank(ESTABLISH_MODE) || "null".equals(ESTABLISH_MODE)) {
                busRecord.put("nzfgESTABLISHMODE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("nzfgESTABLISHMODE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中

            } else if ("2".equals(ESTABLISH_MODE)) {
                busRecord.put("nzfgESTABLISHMODE1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                busRecord.put("nzfgESTABLISHMODE2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
            } else {
                busRecord.put("nzfgESTABLISHMODE1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                busRecord.put("nzfgESTABLISHMODE2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e);
            // e.printStackTrace();
        }
        // HOLDER_JSON股东信息json
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        // 股东数量
        Integer gdsl = 0;
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            gdsl = holderList.size();
        }
        // DIRECTOR_JSON董事信息json
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        // 职工代表担任董事数量
        Integer nzgfdszgdbsl = 0;
        // 非职工代表担任董事数量
        Integer nzgfdsfzgdbsl = 0;
        // 副董事长数量
        Integer nzgffdszsl = 0;
        // 董事数量
        Integer nzgfdssl = 0;
        // 董事会决议信息
        List<Object> nzfgdshjyxx = new LinkedList<Object>();
        // 董事会决议序号
        Integer nzfgdshjyxh = 0;
        // 董事信息
        List<Object> nzgfdsxx = new LinkedList<Object>();
        // 董事会成员签字信息
        List<Object> nzgfdscyqz = new LinkedList<Object>();
        // 全体职工职工代表
        String nzgfqtzgzgdb = "";
        // 全体职工职工代表数量
        Integer nzgfqtzgslzgdb = 0;
        // 职工代表大会纪要会议议题
        String nzgfzgdbdhjyhyyt = "";
        // 职工代表大会纪要一致通过信息
        String nzgfzgdbdhjyyztgxx = "";
        // 职工代表大会纪要一致通过信息
        List<Object> nzgfzgdbdhjyqz = new LinkedList<Object>();
        // 职工代表董事
        String nzgfzgdbds = "";
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            busRecord.put("nzgfdssl", directorList.size() + "");

            List<Map<String, Object>> directorJsonList = JSON.parseObject(DIRECTOR_JSON, List.class);
            // 删除不在表中存在的字段
            directorList = removeMapKey(directorJsonList, "T_COMMERCIAL_DIRECTOR");
            // 按职务对董事进行
            Collections.sort(directorJsonList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    double sum0 = Double.parseDouble(arg0.get("DIRECTOR_JOB").toString());
                    double sum1 = Double.parseDouble(arg1.get("DIRECTOR_JOB").toString());
                    if (sum1 > sum0) {
                        return -1;
                    } else if (sum1 == sum0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            for (Map<String, Object> map : directorJsonList) {
                nzgfdssl++;
                String representative = map.get("DIRECTOR_REPRESENTATIVE") + "";
                if (representative != null && "1".equals(representative)) {
                    nzgfdszgdbsl++;
                    if (StringUtils.isNotBlank(nzgfzgdbds)) {
                        nzgfzgdbds += "、" + map.get("DIRECTOR_NAME");
                    } else {
                        nzgfzgdbds += map.get("DIRECTOR_NAME");
                    }
                } else {
                    nzgfdsfzgdbsl++;
                }
                Object job = map.get("DIRECTOR_JOB");
                if (job != null && "02".equals(representative)) {
                    nzgffdszsl++;
                }
                nzgfdsxx.add("    " + nzgfdssl + "、选举" + map.get("DIRECTOR_NAME") + "为公司董事，任期"
                        + map.get("DIRECTOR_OFFICETERM") + "年。其中，" + gdsl + "名赞成，代表股份" + busRecord.get("INVESTMENT")
                        + "万股；0名反对，代表股份0万股；0名弃权，代表股份0万股，赞成人数符合法定比例。");
                nzgfdscyqz.add("    " + map.get("DIRECTOR_NAME") + "（签名）：");
                String DIRECTOR_JOB = (String) map.get("DIRECTOR_JOB");
                // 设置董事长任命方
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("01")) {
                    nzfgdshjyxh++;
                    String msg = "    " + nzfgdshjyxh + "、 选举" + map.get("DIRECTOR_NAME") + "为公司董事长";
                    // 设置法定代表人任命方
                    if ("董事长".equals(LEGAL_JOB)) {
                        msg += "，并为公司的法定代表人";
                    }
                    msg += "，任期三年";
                    nzfgdshjyxx.add(msg);
                }
                // 副董事长
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                    nzfgdshjyxh++;
                    String msg = "    " + nzfgdshjyxh + "、 选举" + map.get("DIRECTOR_NAME") + "为公司副董事长";
                    msg += "，任期三年";
                    nzfgdshjyxx.add(msg);
                }

                if (StringUtils.isNotBlank(nzgfqtzgzgdb)) {
                    nzgfqtzgzgdb += "、" + map.get("DIRECTOR_NAME");
                } else {
                    nzgfqtzgzgdb += map.get("DIRECTOR_NAME");
                }
                nzgfqtzgslzgdb++;
                nzgfzgdbdhjyqz.add("    " + map.get("DIRECTOR_NAME") + "（签名）：");
            }
        }
        busRecord.put("nzgfdszgdbsl", nzgfdszgdbsl);
        busRecord.put("nzgfdsfzgdbsl", nzgfdsfzgdbsl);
        busRecord.put("nzgffdszsl", nzgffdszsl);
        busRecord.put("NZGFDSXX", nzgfdsxx);
        busRecord.put("nzgfdscyqz", nzgfdscyqz);

        // SUPERVISOR_JSON监事信息json
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        // 监事会成员数量
        Integer nzgfjshcysl = 0;
        // 监事会职工代表数量
        Integer nzgfjshzgdbsl = 0;
        // 监事会非职工代表数量
        Integer nzgfjshfzgdbsl = 0;
        // 监事会副主席数量
        Integer nzgfjshfzxsl = 0;
        // 监事会监事数量
        Integer nzgfjssl = 0;
        // 监事会监事成员
        String nzgfjshjscx = "";
        // 监事会全体监事姓名
        String nzgfjshqtjs = "";
        // 监事信息非职工代表监事
        List<Object> nzgfjshxxfzgdb = new LinkedList<Object>();
        // 监事信息非职工代表人员
        String nzgfjshfzgdbry = "";
        // 监事信息职工代表人员
        String nzgfjshzgdbry = "";

        // 监事会决议一致通过信息
        List<Object> nzgfjshjyyztgxx = new LinkedList<Object>();
        // 监事会决议一致通过信息
        List<Object> nzgfjshjyqz = new LinkedList<Object>();
        // 监事会副主席姓名
        String nzgfjshfzxxm = "";
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            for (Map<String, Object> map : supervisorList) {
                Object job = map.get("SUPERVISOR_JOB");
                if (job != null && "11".equals(job)) {
                    nzgfjshjyyztgxx.add("    一、选举" + map.get("SUPERVISOR_NAME") + "为首届监事会主席，任期三年。");
                    nzgfjshjyqz.add("    " + map.get("SUPERVISOR_NAME") + "（签字）：");
                }
            }

            for (Map<String, Object> map : supervisorList) {
                if (StringUtils.isBlank(nzgfjshqtjs)) {
                    nzgfjshqtjs += map.get("SUPERVISOR_NAME");
                } else {
                    nzgfjshqtjs += "、" + map.get("SUPERVISOR_NAME");

                }
                nzgfjshcysl++;
                String representative = map.get("SUPERVISOR_REPRESENTATIVE") + "";
                if (representative != null && "1".equals(representative)) {
                    nzgfjshzgdbsl++;
                    if (StringUtils.isBlank(nzgfjshzgdbry)) {
                        nzgfjshzgdbry += map.get("SUPERVISOR_NAME");
                    } else {
                        nzgfjshzgdbry += "、" + map.get("SUPERVISOR_NAME");
                    }

                    if (StringUtils.isNotBlank(nzgfqtzgzgdb)) {
                        nzgfqtzgzgdb += "、" + map.get("SUPERVISOR_NAME");
                    } else {
                        nzgfqtzgzgdb += map.get("SUPERVISOR_NAME");
                    }
                    nzgfqtzgslzgdb++;
                    nzgfzgdbdhjyqz.add("    " + map.get("SUPERVISOR_NAME") + "（签名）：");
                } else {
                    nzgfjshfzgdbsl++;
                    nzgfjshxxfzgdb.add("    " + nzgfjshfzgdbsl + "、选举" + map.get("SUPERVISOR_NAME") + "为公司监事，任期"
                            + map.get("SUPERVISOR_OFFICETERM") + "年。其中，" + gdsl + "名赞成，代表股份"
                            + busRecord.get("INVESTMENT") + "万股；0名反对，代表股份0万股；0名弃权，代表股份0万股，赞成人数符合法定比例。");
                    if (StringUtils.isBlank(nzgfjshfzgdbry)) {
                        nzgfjshfzgdbry += map.get("SUPERVISOR_NAME");
                    } else {
                        nzgfjshfzgdbry += "、" + map.get("SUPERVISOR_NAME");
                    }
                }
                Object job = map.get("SUPERVISOR_JOB");
                if (job != null && "12".equals(job)) {
                    nzgfjshfzxsl++;

                    if (StringUtils.isNotBlank(nzgfjshfzxxm)) {
                        nzgfjshfzxxm += "、" + map.get("SUPERVISOR_NAME");
                    } else {
                        nzgfjshfzxxm += map.get("SUPERVISOR_NAME");
                    }
                    nzgfjshjyqz.add("    " + map.get("SUPERVISOR_NAME") + "（签字）：");
                }
                if (job != null && "10".equals(job)) {
                    nzgfjssl++;
                    if (StringUtils.isNotBlank(nzgfjshjscx)) {
                        nzgfjshjscx += "、" + map.get("SUPERVISOR_NAME");
                    } else {
                        nzgfjshjscx += map.get("SUPERVISOR_NAME");
                    }
                }

            }
        }
        busRecord.put("nzgfjshcysl", nzgfjshcysl);
        busRecord.put("NZGFJSHZGDBSL", nzgfjshzgdbsl);
        busRecord.put("nzgfjshfzgdbsl", nzgfjshfzgdbsl);
        busRecord.put("nzgfjshfzxsl", nzgfjshfzxsl);
        String nzgfzcfzxxx = "";
        if (nzgfjshfzxsl > 0) {
            nzgfzcfzxxx = "监事会设主席一人，设副主席 " + nzgfjshfzxsl + "人。监事会主席和副主席由全体监事过半数选举产生。监事会主席召集和主持监事会会议；"
                    + "监事会主席不能履行职务或者不履行职务的，由监事会副主席召集和主持监事会会议；" + "监事会副主席不能履行职务或者不履行职务的，由半数以上监事共同推举一名监事召集和主持监事会会议。";
        } else {
            nzgfzcfzxxx = "监事会设主席一人。监事会主席由全体监事过半数选举产生。" + "监事会主席召集和主持监事会会议；监事会主席不能履行职务或者不履行职务的，"
                    + "由半数以上监事共同推举一名监事召集和主持监事会会议。";
        }
        busRecord.put("nzgfzcfzxxx", nzgfzcfzxxx);
        busRecord.put("nzgfjshjscx", nzgfjshjscx);
        busRecord.put("NZGFJSHXXFZGDB", nzgfjshxxfzgdb);
        busRecord.put("NZGFJSHFZGDBRY", nzgfjshfzgdbry);
        busRecord.put("NZGFJSHZGDBRY", nzgfjshzgdbry);
        // 经营期限，前面已修改
        // String nzgfjyqx = "";
        // String OPRRATE_TERM_TYPE = busRecord.get("OPRRATE_TERM_TYPE") == null
        // ? "" : busRecord.get("OPRRATE_TERM_TYPE")
        // .toString();
        // if ("1".equals(OPRRATE_TERM_TYPE)) {
        // nzgfjyqx = busRecord.get("BUSSINESS_YEARS") + "年";
        // } else if ("0".equals(OPRRATE_TERM_TYPE)) {
        // nzgfjyqx = "长期";
        // }
        busRecord.put("nzgfjyqx", busRecord.get("BUSSINESS_YEARS"));
        // MANAGER_JSON经理信息json
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            for (Map<String, Object> map : managerList) {
                String MANAGER_JOB = (String) map.get("MANAGER_JOB");
                nzfgdshjyxh++;
                String msg = "    " + nzfgdshjyxh + "、 选举" + map.get("MANAGER_NAME") + "为公司经理";
                // 设置法定代表人任命方
                if (StringUtils.isNotEmpty(MANAGER_JOB) && "经理".equals(LEGAL_JOB)) {
                    msg += "，并为公司的法定代表人";
                }
                msg += "，任期三年";
                nzfgdshjyxx.add(msg);
            }
        }
        busRecord.put("nzfgdshjyxx", nzfgdshjyxx);
        busRecord.put("nzgfqtzgzgdb", nzgfqtzgzgdb);
        if (nzgfjshzgdbsl > 0) {
            nzgfzgdbdhjyhyyt += "选举产生职工代表" + nzgfjshzgdbsl + "名出任本公司监事";
        }
        if (StringUtils.isNotBlank(nzgfzgdbdhjyhyyt)) {
            nzgfzgdbdhjyhyyt += "，";
        }
        String nzgfzczgdbdsrxx = "";
        if (nzgfdszgdbsl > 0) {
            nzgfzgdbdhjyhyyt += "选举产生职工代表" + nzgfdszgdbsl + "名出任本公司董事";
            nzgfzczgdbdsrxx = "职工代表董事" + nzgfdszgdbsl + "人";
        }
        busRecord.put("nzgfzczgdbdsrxx", nzgfzczgdbdsrxx);
        nzgfzgdbdhjyhyyt += "。";
        busRecord.put("nzgfzgdbdhjyhyyt", nzgfzgdbdhjyhyyt);

        if (StringUtils.isNotBlank(nzgfjshzgdbry)) {
            nzgfzgdbdhjyyztgxx += "选举" + nzgfjshzgdbry + "作为职工代表出任本公司首届监事会的监事，任期三年";
        }

        if (StringUtils.isNotBlank(nzgfzgdbds)) {
            nzgfzgdbdhjyyztgxx += "，选举" + nzgfzgdbds + "作为职工代表出任本公司首届董事会的董事，任期三年";
        }
        nzgfzgdbdhjyyztgxx += "。";
        busRecord.put("nzgfzgdbdhjyyztgxx", nzgfzgdbdhjyyztgxx);
        busRecord.put("nzgfzgdbdhjyqz", nzgfzgdbdhjyqz);
        busRecord.put("nzgfjshqtjs", nzgfjshqtjs);
        if (StringUtils.isNotBlank(nzgfjshfzxxm)) {
            nzgfjshjyyztgxx.add("    二、选举" + nzgfjshfzxxm + "为首届监事会副主席，任期三年。");
        }
        busRecord.put("nzgfjshjyyztgxx", nzgfjshjyyztgxx);
        busRecord.put("nzgfjshjyqz", nzgfjshjyqz);

    }

    /**
     * 
     * 描述 分公司字段处理
     * 
     * @author Danto Huang
     * @created 2018年6月11日 上午11:49:55
     * @param busRecord
     */
    private void setBranch(Map<String, Object> busRecord) {
        try {
            if (null != busRecord.get("ACCOUNTING_METHOD") && busRecord.get("ACCOUNTING_METHOD").equals("1")) {
                busRecord.put("ACCOUNTINGMETHOD1", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
                busRecord.put("ACCOUNTINGMETHOD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
            } else if (null != busRecord.get("ACCOUNTING_METHOD") && busRecord.get("ACCOUNTING_METHOD").equals("2")) {
                busRecord.put("ACCOUNTINGMETHOD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
                busRecord.put("ACCOUNTINGMETHOD2", WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
            } else {
                busRecord.put("ACCOUNTINGMETHOD1", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
                busRecord.put("ACCOUNTINGMETHOD2", WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));
            }
            
            busRecord.put("COMPANY_NAME", busRecord.get("BRANCH_NAME"));
            busRecord.put("LOCAL_REGION", busRecord.get("LOCAL_REGION")==null?"福州市平潭县":busRecord.get("LOCAL_REGION"));
            

            setNzgfWrite(busRecord);
            
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 描述:外资分公司字段处理
     *
     * @author Madison You
     * @created 2020/1/7 11:05:00
     * @param
     * @return
     */
    private void setForeignBranch(Map<String, Object> busRecord) {
        if (dictionaryService == null) {
            dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        }
        String subordinateType = busRecord.get("SUBORDINATE_TYPE") == null ? ""
                : (String) busRecord.get("SUBORDINATE_TYPE");
        if (!subordinateType.equals("")) {
            String subordinateTypeName = dictionaryService.getDicNames("FOREIGNBRANCH", subordinateType);
            busRecord.put("WZFGSLSQYLX", subordinateTypeName);
        }
        String bussinessTime = busRecord.get("SUBORDINATE_BUSSINESSTIME") == null ? ""
                : (String) busRecord.get("SUBORDINATE_BUSSINESSTIME");
        busRecord.put("WZFGSLSQYYYQX", bussinessTime);
    }

    /**
     * 描述:外资字段处理
     *
     * @author Madison You
     * @created 2020/1/8 10:53:00
     * @param
     * @return
     */
    private void setForeign(Map<String, Object> busRecord) {
        String REGISTER_TYPE = busRecord.get("REGISTER_TYPE") == null ? "" : busRecord.get("REGISTER_TYPE").toString();
        if (REGISTER_TYPE.equals("1")) {
            String INVESTMENT = busRecord.get("INVESTMENT") == null ? "" : busRecord.get("INVESTMENT").toString();
            double parInvestment = Double.parseDouble(INVESTMENT) * 0.158105;
            busRecord.put("WZTZZEMY", parInvestment);

            /* 外资副董事长 */
            StringBuffer wzfdsznames = new StringBuffer();
            /* 外资董事长 */
            StringBuffer WZDSZNAME = new StringBuffer();
            /* 外资执行董事 */
            StringBuffer WZZXDSNAME = new StringBuffer();
            /* 所有董事姓名 */
            StringBuffer WZSYDSNAME = new StringBuffer();
            /* 外资独资公司董事会决议 */
            StringBuffer WZDZGSDSHJY = new StringBuffer();
            StringBuffer WZDZGSDSHJY1 = new StringBuffer();
            StringBuffer WZDZGSDSHJY2 = new StringBuffer();
            StringBuffer WZDZGSDSHJY3 = new StringBuffer();
            StringBuffer WZDZGSDSHJZ = new StringBuffer();

            /* 董事会信息 */
            String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? ""
                    : busRecord.get("DIRECTOR_JSON").toString();
            if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
                List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
                // 法定代表人职务
                String LEGAL_JOB = (String) busRecord.get("LEGAL_JOB");
                /* 执行董事为法定代表人 */
                if (LEGAL_JOB.equals("30")) {
                    busRecord.put("WZDSHJYMARK1", "，并为公司的法定代表人");
                    busRecord.put("WZFDDBR", "执行董事");
                    busRecord.put("wzgdjdmark1", "兼法定代表人");
                    busRecord.put("wzgdjdmark2", "");
                } else {
                    busRecord.put("WZDSHJYMARK1", "");
                    busRecord.put("WZFDDBR", "经理");
                    busRecord.put("wzgdjdmark1", "");
                    busRecord.put("wzgdjdmark2", "兼法定代表人");
                }

                /* 董事长为法定代表人 */
                if (LEGAL_JOB.equals("01")) {
                    busRecord.put("WZDZFDDBRZW", "董事长");
                    WZDZGSDSHJY1.append("，并为公司的法定代表人");
                } else {
                    busRecord.put("WZDZFDDBRZW", "经理");
                    WZDZGSDSHJY3.append("兼法定代表人");
                }

                int FDSZSIZE = 0;
                for (Map<String, Object> map : directorList) {
                    String DIRECTOR_JOB = (String) map.get("DIRECTOR_JOB");
                    String DIRECTOR_NAME = (String) map.get("DIRECTOR_NAME");
                    /* 所有董事姓名 */
                    WZSYDSNAME.append(DIRECTOR_NAME).append("、");
                    // WZFDDBR
                    if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                        FDSZSIZE++;
                        busRecord.put("FGDSRMF", map.get("DIRECTOR_APPOINTOR"));
                        wzfdsznames.append(DIRECTOR_NAME).append("、");
                    }
                    /* 董事长 */
                    if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("01")) {
                        WZDSZNAME.append(DIRECTOR_NAME).append("、");
                    }
                    /* 执行董事 */
                    if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("30")) {
                        WZZXDSNAME.append(DIRECTOR_NAME).append("、");
                    }

                }
                if (FDSZSIZE > 0) {
                    busRecord.put("wzfdszmark",
                            "；选举" + wzfdsznames.substring(0, wzfdsznames.length() - 1) + "为公司副董事长，任期三年");
                    WZDZGSDSHJY2.append("；选举" + wzfdsznames.substring(0, wzfdsznames.length() - 1) + "为公司副董事长");
                    busRecord.put("WZDZFDSZMARK1", "副董事长" + FDSZSIZE + "人，");
                    busRecord.put("WZDZFDSZMARK2", "，副董事长" + FDSZSIZE + "人");
                } else {
                    busRecord.put("wzfdszmark", "");
                    busRecord.put("WZDZFDSZMARK1", "");
                    busRecord.put("WZDZFDSZMARK2", "");
                }

                /* 董事会成员人数 */
                busRecord.put("WZDZDSHRS", directorList.size());

            }

            /* 董事长 */
            busRecord.put("WZDSZNAME", wzJobNameFormate(WZDSZNAME));

            /* 执行董事 */
            busRecord.put("WZZXDSNAME", wzJobNameFormate(WZZXDSNAME));

            /* 所有董事姓名 */
            busRecord.put("WZSYDSNAME", wzJobNameFormate(WZSYDSNAME));

            /* 监事 */
            StringBuffer wzjsmc = new StringBuffer();

            /* 监事信息 */
            String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                    : busRecord.get("SUPERVISOR_JSON").toString();
            if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
                List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
                int WZJSRS = 0;
                for (Map<String, Object> map : supervisorList) {
                    WZJSRS++;
                    String SUPERVISOR_NAME = (String) map.get("SUPERVISOR_NAME");
                    wzjsmc.append(SUPERVISOR_NAME).append("、");
                }
                /* 外资监事人数 */
                if (WZJSRS == 1) {
                    busRecord.put("WZJSRS", "一人");
                } else if (WZJSRS == 2) {
                    busRecord.put("WZJSRS", "二人");
                } else {
                    busRecord.put("WZJSRS", "");
                }
            }

            /* 监事 */
            busRecord.put("wzjsmc", wzJobNameFormate(wzjsmc));

            /* 经理 */
            StringBuffer WZJLNAMES = new StringBuffer();

            /* 经理信息 */
            String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
            if (StringUtils.isNotEmpty(MANAGER_JSON)) {
                List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
                for (Map<String, Object> map : managerList) {
                    String MANAGER_NAME = (String) map.get("MANAGER_NAME");
                    WZJLNAMES.append(MANAGER_NAME).append("、");
                }
            }

            /* 经理 */
            busRecord.put("WZJLNAMES", wzJobNameFormate(WZJLNAMES));

            /* 股东姓名 */
            StringBuffer WZDZGDJDGD = new StringBuffer();
            /* 股东人数 */
            int WZGDRS = 0;

            // 外方投资方信息
            String FOREIGNINVESTOR_JSON = busRecord.get("FOREIGNINVESTOR_JSON") == null ? ""
                    : busRecord.get("FOREIGNINVESTOR_JSON").toString();
            if (StringUtils.isNotEmpty(FOREIGNINVESTOR_JSON)) {
                List<Map<String, Object>> jwtzzList = JSON.parseObject(FOREIGNINVESTOR_JSON, List.class);
                if (null != jwtzzList) {
                    for (Map<String, Object> map : jwtzzList) {
                        String INVESTOR_NAME = (String) map.get("INVESTOR_NAME");// 投资者名称
                        WZDZGDJDGD.append(INVESTOR_NAME).append("、");
                        WZGDRS++;
                    }
                }
            }

            // 中方投资方信息
            String DOMESTICINVESTOR_JSON = busRecord.get("DOMESTICINVESTOR_JSON") == null ? ""
                    : busRecord.get("DOMESTICINVESTOR_JSON").toString();
            if (StringUtils.isNotEmpty(DOMESTICINVESTOR_JSON)) {
                List<Map<String, Object>> zftzzList = JSON.parseObject(DOMESTICINVESTOR_JSON, List.class);
                if (null != zftzzList) {
                    for (Map<String, Object> map : zftzzList) {
                        String INVESTOR_NAME = (String) map.get("INVESTOR_NAME");// 投资者名称
                        WZDZGDJDGD.append(INVESTOR_NAME).append("、");
                        WZGDRS++;
                    }
                }
            }

            /* 股东姓名 */
            busRecord.put("WZDZGDJDGD", wzJobNameFormate(WZDZGDJDGD));
            /* 股东人数 */
            busRecord.put("WZGDRS", WZGDRS);

            /* 外资独资公司董事会决议 */
            WZDZGSDSHJY.append("一、选举").append(busRecord.get("WZDSZNAME")).append("为公司董事长").append(WZDZGSDSHJY1)
                    .append(WZDZGSDSHJY2).append("，任期三年");
            WZDZGSDSHJZ.append("二、聘任").append(busRecord.get("WZJLNAMES")).append("为公司经理").append(WZDZGSDSHJY3)
                    .append("，任期三年");
            busRecord.put("WZDZGSDSHJY", WZDZGSDSHJY);
            busRecord.put("WZDZGSDSHJZ", WZDZGSDSHJZ);
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/24 15:24:00
     * @param
     * @return
     */
    private String wzJobNameFormate(StringBuffer jobName) {
        if (jobName != null && jobName.length() > 0) {
            return jobName.substring(0, jobName.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 
     * 描述 设置面签信息回填到模板（商事变更登记业务）
     * 
     * @author Allin Lin
     * @created 2021年5月11日 上午9:25:25
     * @param busRecord
     */
    public void setSignInfo(Map<String, Object> busRecord) {
        if (extraDictionaryService == null) {
            extraDictionaryService = (ExtraDictionaryService) AppUtil.getBean("extraDictionaryService");
        }
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        String OPERATOR_NAME = StringUtil.getString(busRecord.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(busRecord.get("OPERATOR_IDNO"));
        // 经办人面签签名
        Map<String, Object> getSignInfo = getSignInfo(EXE_ID, OPERATOR_NAME, OPERATOR_IDNO);
        busRecord.put("OPERATOR_WRITE", "");
        String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
        setWordToImg(busRecord, SIGN_WRITE, "OPERATOR_WRITE", "100", "40");
        // 经办人面签身份证(正面)
        String SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
        busRecord.put("OPERATORIDPHOTOFRONT", "");
        setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "OPERATORIDPHOTOFRONT", "280", "180");
        // 经办人面签身份证(背面)
        String SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
        busRecord.put("OPERATORIDPHOTOBACK", "");
        setWordToImg(busRecord, SIGN_IDPHOTO_BACK, "OPERATORIDPHOTOBACK", "280", "180");

        String LEGAL_NAME = StringUtil.getString(busRecord.get("LEGAL_NAME"));
        String LEGAL_IDNO = StringUtil.getString(busRecord.get("LEGAL_IDNO"));
        String LEGAL_NAME_CHANGE = StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE"));
        String LEGAL_IDNO_CHANGE = StringUtil.getString(busRecord.get("LEGAL_IDNO_CHANGE"));
        if (StringUtils.isNotEmpty(LEGAL_NAME_CHANGE) && StringUtils.isNotEmpty(LEGAL_IDNO_CHANGE)) {
            LEGAL_NAME = LEGAL_NAME_CHANGE;
            LEGAL_IDNO = LEGAL_IDNO_CHANGE;
        }
        // 法定代表人面签签名
        getSignInfo = getSignInfo(EXE_ID, LEGAL_NAME, LEGAL_IDNO);
        busRecord.put("LEGAL_WRITE", "");
        SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
        setWordToImg(busRecord, SIGN_WRITE, "LEGAL_WRITE", "100", "40");
        // 法定代表人面签身份证（正面）
        SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
        busRecord.put("LEGALPHOTOFRONT", "");
        setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "LEGALPHOTOFRONT", "280", "180");
        // 法定代表人面签身份证（反面）
        SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
        busRecord.put("LEGALPHOTOBACK", "");
        setWordToImg(busRecord, SIGN_IDPHOTO_BACK, "LEGALPHOTOBACK", "280", "180");
        // 签署时间
        busRecord.put("SIGNTIME", getNewSignTime(EXE_ID));

        String LIAISON_NAME = StringUtil.getString(busRecord.get("LIAISON_NAME"));
        String LIAISON_IDNO = StringUtil.getString(busRecord.get("LIAISON_IDNO"));
        getSignInfo = getSignInfo(EXE_ID, LIAISON_NAME, LIAISON_IDNO);
        // 联络员面签身份证（正面）
        SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
        busRecord.put("LIAISONPHOTOFRONT", "");
        setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "LIAISONPHOTOFRONT", "280", "180");
        // 联络员面签身份证（反面）
        SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
        busRecord.put("LIAISONPHOTOBACK", "");
        setWordToImg(busRecord, SIGN_IDPHOTO_FRONT, "LIAISONPHOTOBACK", "280", "180");

        // 旧股东签名
        Set holderWrite = new HashSet();
        String HOLDER_JSON = StringUtil.getString(busRecord.get("HOLDER_JSON"));
        List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(HOLDER_JSON);
        for (Map<String, Object> map : holderList) {
            String SHAREHOLDER_TYPE = StringUtil.getString(extraDictionaryService
                    .get("investorType", StringUtil.getString(map.get("SHAREHOLDER_TYPE"))).get("DIC_NAME"));// 股东类型
            String SHAREHOLDER_NAME = StringUtil.getString(map.get("SHAREHOLDER_NAME"));
            String LICENCE_NO = StringUtil.getString(map.get("LICENCE_NO"));
            String LEGAL_PERSON = StringUtil.getString(map.get("LEGAL_PERSON"));
            String LEGAL_IDNO_PERSON = StringUtil.getString(map.get("LEGAL_IDNO_PERSON"));
            if (StringUtils.isNotEmpty(SHAREHOLDER_TYPE) && SHAREHOLDER_TYPE.indexOf("自然人") >= 0) {
                getSignInfo = getSignInfo(EXE_ID, SHAREHOLDER_NAME, LICENCE_NO);
            } else {
                getSignInfo = getSignInfo(EXE_ID, LEGAL_PERSON, LEGAL_IDNO_PERSON);
            }
            SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
            setWordToImg(holderWrite, SIGN_WRITE, "100", "40");
        }
        busRecord.put("holderWrite", holderWrite);
        // 新股东签名
        Set holderChangeWrite = new HashSet();
        List<Map<String, Object>> holderCzList = (List<Map<String, Object>>)
                busRecord.get("holderCzList");//最终股东列表
        if(holderCzList.size()>0){
            for (Map<String, Object> holderCz : holderCzList) {
                String SHAREHOLDER_TYPE = StringUtil.getString(extraDictionaryService
                        .get("investorType", StringUtil.getString(
                                holderCz.get("SHAREHOLDER_TYPE"))).get("DIC_NAME"));// 股东类型
                String SHAREHOLDER_NAME = StringUtil.getString(holderCz.get("SHAREHOLDER_BG_NAME"));
                String LICENCE_NO = StringUtil.getString(holderCz.get("LICENCE_NO"));
                String LEGAL_PERSON = StringUtil.getString(holderCz.get("LEGAL_PERSON"));
                String LEGAL_IDNO_PERSON = StringUtil.getString(holderCz.get("LEGAL_IDNO_PERSON"));
                if (StringUtils.isNotEmpty(SHAREHOLDER_TYPE) && SHAREHOLDER_TYPE.indexOf("自然人") >= 0) {
                    getSignInfo = getSignInfo(EXE_ID, SHAREHOLDER_NAME, LICENCE_NO);
                } else {
                    getSignInfo = getSignInfo(EXE_ID, LEGAL_PERSON, LEGAL_IDNO_PERSON);
                }
                SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
                setWordToImg(holderChangeWrite, SIGN_WRITE, "100", "40");
            }
        }
        busRecord.put("holderChangeWrite", holderChangeWrite);
       
        //股东身份证黏贴页正反面
        List<Map<String, Object>> bgHolderList = new LinkedList<Map<String, Object>>();
        String HOLDER_JSON_CHANGE = busRecord.get("HOLDER_JSON_CHANGE") == null ? ""
                : busRecord.get("HOLDER_JSON_CHANGE").toString();
        List<Map<String, Object>> holderChangeList = (List<Map<String, Object>>) JSON.parse(HOLDER_JSON_CHANGE);
        Map<String, Object> info = null;
        if (null != holderChangeList && holderChangeList.size() > 0) {
            for (Map<String, Object> map : holderChangeList) {
                //证件号码是身份证的，才面签
                info = new HashMap<String, Object>();
                info.put("HOLDERPHOTOFRONT", "");
                info.put("HOLDERPHOTOBACK", "");
                String HOLDER_NAME="" ;
                String HOLDER_IDNO="";
                if(StringUtils.isNotEmpty(StringUtil.getString(map.get("LICENCE_TYPE"))) &&"SF".
                        equals(StringUtil.getString(map.get("LICENCE_TYPE")))) {
                    HOLDER_NAME = StringUtil.getString(map.get("SHAREHOLDER_NAME"));
                    HOLDER_IDNO = StringUtil.getString(map.get("LICENCE_NO"));
                    //去除为原股东新增或转让的情况
                    for (Map<String, Object> holder : holderList) {
                        if(StringUtil.getString(holder.get("SHAREHOLDER_NAME")).
                                equals(HOLDER_NAME)){
                                    HOLDER_NAME = "";
                                    HOLDER_IDNO = "";
                                    break;
                        }
                    }
                }
                if(StringUtils.isNotEmpty(HOLDER_NAME) && StringUtils.isNotEmpty(HOLDER_IDNO)){
                    // 面签信息
                    getSignInfo = getSignInfo(EXE_ID, HOLDER_NAME, HOLDER_IDNO);
                    // 面签身份证
                    SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
                    setWordToImg(info, SIGN_IDPHOTO_FRONT, "HOLDERPHOTOFRONT", "280", "180");
                    // 面签身份证
                    SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
                    setWordToImg(info, SIGN_IDPHOTO_BACK, "HOLDERPHOTOBACK", "280", "180");
                    bgHolderList.add(info);
                }
            }
        }
        busRecord.put("bgHolderList", bgHolderList);
        
        // 新董事签名(新公司设董事会，就是新公司的董事会、原公司设董事会，新公司设执行董事，则由原公司董事会成员来签字。)
        Set newDirectorWrite = new HashSet();
        boolean exDirectorBoard = false;//董事会
        List<Map<String, Object>> directorOldList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
        for (Map<String, Object> map : directorOldList) {
            if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                exDirectorBoard = true;
            }
        }
        boolean newDirectorBoard = false;//董事会
        List<Map<String, Object>> directorChangeList =  (List<Map<String, Object>>) JSON
                .parse(StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
        if(directorChangeList!=null){
            for (Map<String, Object> map : directorChangeList) {
                if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//董事长
                    newDirectorBoard = true;
                }
            } 
        }
        int num = 0;
        if(exDirectorBoard==true && newDirectorBoard!=true){//原公司设董事会，新公司不设立董事会
            for (Map<String, Object> map : directorOldList) {
                map.put("DIRECTORPHOTOFRONT", "");
                map.put("DIRECTORPHOTOBACK", "");
                String DIRECTOR_IDNO = StringUtil.getString(map.get("DIRECTOR_IDNO_OLD"));
                String DIRECTOR_NAME = StringUtil.getString(map.get("DIRECTOR_NAME_OLD"));
                // 董事面签信息
                getSignInfo = getSignInfo(EXE_ID, DIRECTOR_NAME, DIRECTOR_IDNO);
                if (num == 0) {
                    // 董事面签身份证
                    SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
                    setWordToImg(map, SIGN_IDPHOTO_FRONT, "DIRECTORPHOTOFRONT", "280", "180");
                    // 董事面签身份证
                    SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                            : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
                    setWordToImg(map, SIGN_IDPHOTO_BACK, "DIRECTORPHOTOBACK", "280", "180");
                }
                // 董事签字
                SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
                setWordToImg(newDirectorWrite, SIGN_WRITE, "100", "40");
                num++;
            } 
        }else if(newDirectorBoard==true){
            if(directorChangeList!=null && directorChangeList.size()>0){
                for (Map<String, Object> map : directorChangeList) {
                    map.put("DIRECTORPHOTOFRONT", "");
                    map.put("DIRECTORPHOTOBACK", "");
                    String DIRECTOR_IDNO = StringUtil.getString(map.get("DIRECTOR_IDNO"));
                    String DIRECTOR_NAME = StringUtil.getString(map.get("DIRECTOR_NAME"));
                    // 董事面签信息
                    getSignInfo = getSignInfo(EXE_ID, DIRECTOR_NAME, DIRECTOR_IDNO);
                    if (num == 0) {
                        // 董事面签身份证
                        SIGN_IDPHOTO_FRONT = getSignInfo.get("SIGN_IDPHOTO_FRONT") == null ? ""
                                : getSignInfo.get("SIGN_IDPHOTO_FRONT").toString();
                        setWordToImg(map, SIGN_IDPHOTO_FRONT, "DIRECTORPHOTOFRONT", "280", "180");
                        // 董事面签身份证
                        SIGN_IDPHOTO_BACK = getSignInfo.get("SIGN_IDPHOTO_BACK") == null ? ""
                                : getSignInfo.get("SIGN_IDPHOTO_BACK").toString();
                        setWordToImg(map, SIGN_IDPHOTO_BACK, "DIRECTORPHOTOBACK", "280", "180");
                    }
                    // 董事签字
                    SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
                    setWordToImg(newDirectorWrite, SIGN_WRITE, "100", "40");
                    num++;
                } 
            }
        }
        busRecord.put("newDirectorWrite", newDirectorWrite);
        //加盖企业公章用印承诺书
        busRecord.put("COMPANY_WRITE", "");
        Map<String, Object> companyMap = dictionaryService.getByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[] { "EXE_ID","UPLOAD_SIGN" }, new Object[] { EXE_ID,"1" });
        if(companyMap!=null){
            if(StringUtils.isNotEmpty(StringUtil.getString(companyMap.get("LEGAL_FILEID")))){
                Map<String, Object> fileInfo = dictionaryService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[] { "FILE_ID" }, new Object[] { companyMap.get("LEGAL_FILEID")});
                String COMPANY_WRITE = StringUtil.getString(fileInfo.get("FILE_PATH"));
                setWordToImg(busRecord, COMPANY_WRITE, "COMPANY_WRITE", "680", "580"); 
            }
        }
    }

    /**
     * 
     * 描述 设置股权转让协议面签信息(新、旧股东签字)
     * 
     * @author Allin Lin
     * @created 2021年5月11日 上午11:49:54
     * @param EXE_ID
     * @param info
     */
    public void setGqzrSign(String EXE_ID, String holderName, String holderIdoNo, Map<String, Object> busRecord,
            String key) {
        // 股权转让面签签名
        Map<String, Object> getSignInfo = getSignInfo(EXE_ID, holderName, holderIdoNo);
        String SIGN_WRITE = getSignInfo.get("SIGN_WRITE") == null ? "" : getSignInfo.get("SIGN_WRITE").toString();
        setWordToImg(busRecord, SIGN_WRITE, key, "100", "40");
    }

}
