/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.KtStampDao;
import net.evecom.platform.bsfw.service.KtStampService;
import net.evecom.platform.bsfw.util.KTAutoSealJavaClt;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述:凯特签章
 *
 * @author Madison You
 * @created 2020年11月2日 10:25
 */
@Service("ktStampService")
public class KtStampServiceImpl extends BaseServiceImpl implements KtStampService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/2 10:28:00
     * @param
     * @return
     */
    @Resource
    private KtStampDao ktStampDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/9 16:44:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/2 10:28:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return ktStampDao;
    }

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(KtStampServiceImpl.class);


    /**
     * 描述:获取签章管理列表数据
     *
     * @author Madison You
     * @created 2020/11/2 10:30:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getStampManageList(SqlFilter filter) {
        ArrayList<Object> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.*,P.DIC_NAME TEMPLATE_FILE_NAME FROM T_BSFW_STAMPMANAGE T LEFT JOIN (SELECT DIC_CODE,DIC_NAME ");
        sql.append(" FROM T_MSJW_SYSTEM_DICTIONARY WHERE TYPE_CODE = 'YZLB' ) P ON T.SIGN_ID = P.DIC_CODE WHERE 1 = 1 ");
        String querySql = ktStampDao.getQuerySql(filter, sql.toString(), params);
        List<Map<String,Object>> list = ktStampDao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述:自动签章方法
     *
     * @author Madison You
     * @created 2020/11/4 9:53:00
     * @param
     * @return
     */
    @Override
    public void ktAutoStamp(Map<String, Object> stampMap, String oldStampFilePath, String newStampFilePath) {
        Properties properties = FileUtil.readProperties("project.properties");
        String KT_SEAL_URL = properties.getProperty("KT_SEAL_URL");
        String KT_SEAL_USERNAME = properties.getProperty("KT_SEAL_USERNAME");
        String KT_SEAL_PASSWORD = properties.getProperty("KT_SEAL_PASSWORD");
        String signId = StringUtil.getValue(stampMap, "SIGN_ID");
        String stampIssign = StringUtil.getValue(stampMap, "STAMP_ISSIGN");
        String stampKeyword = StringUtil.getValue(stampMap, "STAMP_KEYWORD");
        Integer stampBeginpage = Integer.parseInt(StringUtil.getValue(stampMap, "STAMP_BEGINPAGE"));
        Integer stampXoffset = Integer.parseInt(StringUtil.getValue(stampMap, "STAMP_XOFFSET"));
        Integer stampYoffset = Integer.parseInt(StringUtil.getValue(stampMap, "STAMP_YOFFSET"));
        Integer stampMark = Integer.parseInt(StringUtil.getValue(stampMap, "STAMP_MARK"));
        if (!Objects.equals("0",stampIssign)) {
            KTAutoSealJavaClt clt = null;
            try{
                clt = new KTAutoSealJavaClt( KT_SEAL_URL, KT_SEAL_USERNAME, KT_SEAL_PASSWORD, true );
                if (!clt.first_add_seal_on_paper(signId, stampKeyword.getBytes("utf-8"), stampBeginpage,
                        stampMark, stampXoffset, stampYoffset, true)) {
                    log.error(clt.get_last_error());
                }
                int nRet = clt.final_convert_document_ex(oldStampFilePath, null, null, false, true, newStampFilePath);
                if (0 != nRet) {
                    log.error(nRet + "[" + clt.get_last_error() + "]");
                }
            } catch (Exception e) {
                log.error("签章错误");
            } finally {
                if (clt != null) {
                    clt.finally_free();
                }
            }
        }
    }

    /**
     * 描述:签章事项限制
     *
     * @author Madison You
     * @created 2020/11/5 10:03:00
     * @param
     * @return
     */
    @Override
    public boolean stampItemLimit(Map<String, Object> stampMap , Map<String,Object> itemMap) {
        boolean flag = true;
        String stampItemlimit = StringUtil.getValue(stampMap, "STAMP_ITEMCODELIMIT");
        String itemCode = StringUtil.getValue(itemMap, "ITEM_CODE");
        if (StringUtils.isNotEmpty(stampItemlimit) && StringUtils.isNotEmpty(itemCode)) {
            if (!stampItemlimit.contains(itemCode)) {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 描述:签章环节限制
     *
     * @author Madison You
     * @created 2020/11/5 10:18:00
     * @param
     * @return
     */
    @Override
    public boolean stampNodeLimit(Map<String, Object> stampMap, Map<String, Object> exeMap) {
        boolean flag = true;
        String runStatus = StringUtil.getValue(exeMap, "RUN_STATUS");
        String curStepnames = StringUtil.getValue(exeMap, "CUR_STEPNAMES");
        String stampNodelimit = StringUtil.getValue(stampMap, "STAMP_NODELIMIT");
        if (StringUtils.isNotEmpty(runStatus)) {
            if (runStatus.equals("0") || runStatus.equals("1") || runStatus.equals("2")) {
                if (StringUtils.isNotEmpty(stampNodelimit)) {
                    if (runStatus.equals("0") && stampNodelimit.contains("草稿")) {
                        flag = false;
                    } else if (runStatus.equals("2") && stampNodelimit.contains("已办结")) {
                        flag = false;
                    } else if (runStatus.equals("1") && stampNodelimit.contains(curStepnames)) {
                        flag = false;
                    }
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/9 16:41:00
     * @param
     * @return
     */
    @Override
    public String getExcelSelectField(String typeCode, String value) {
        StringBuffer str = new StringBuffer();
        List<Map<String, Object>> typeList = dictionaryService.findByTypeCode(typeCode);
        if (typeCode != null && !typeCode.isEmpty()) {
            for (Map<String, Object> map : typeList) {
                String dicCode = map.get("DIC_CODE").toString();
                String dicName = map.get("DIC_NAME").toString();
                if (dicCode.equals(value)) {
                    str.append(dicName).append("☑").append(" ");
                } else {
                    str.append(dicName).append("□").append(" ");
                }
            }
        }
        if (str != null) {
            return str.toString();
        } else {
            return "";
        }
    }

    /**
     * 描述:如果不存在值则用默认值
     *
     * @author Madison You
     * @created 2020/11/9 16:41:00
     * @param
     * @return
     */
    @Override
    public String getExcelSelectField(String typeCode, String value, String defaultValue) {
        StringBuffer str = new StringBuffer();
        String dicNames = dictionaryService.getDicNames(typeCode, value);
        List<Map<String, Object>> typeList = dictionaryService.findByTypeCode(typeCode);
        if (typeCode != null && !typeCode.isEmpty()) {
            if (StringUtils.isNotEmpty(dicNames)) {
                for (Map<String, Object> map : typeList) {
                    String dicCode = map.get("DIC_CODE").toString();
                    String dicName = map.get("DIC_NAME").toString();
                    if (dicCode.equals(value)) {
                        str.append(dicName).append("☑").append(" ");
                    } else {
                        str.append(dicName).append("□").append(" ");
                    }
                }
            } else {
                for (Map<String, Object> map : typeList) {
                    String dicCode = map.get("DIC_CODE").toString();
                    String dicName = map.get("DIC_NAME").toString();
                    if (dicCode.equals(defaultValue)) {
                        str.append(dicName).append("☑").append(" ");
                    } else {
                        str.append(dicName).append("□").append(" ");
                    }
                }
            }
        }
        if (str != null) {
            return str.toString();
        } else {
            return "";
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/18 10:18:00
     * @param
     * @return
     */
    @Override
    public String getSignFileId(Map<String, Object> exeMap, Map<String, Object> stampMap) {
        String signFileId = "";
        String templateGetType = StringUtil.getValue(stampMap, "TEMPLATE_FILE_GETTYPE");
        if (Objects.equals(templateGetType, "upload")) {
            signFileId = StringUtil.getValue(stampMap, "TEMPLATE_FILE_ID");
        } else if (Objects.equals(templateGetType, "mater")) {
            List<Map<String,Object>> fileList = findByList(StringUtil.getValue(exeMap, "BUS_TABLENAME"),
                    StringUtil.getValue(exeMap, "BUS_RECORDID"), StringUtil.getValue(stampMap, "TEMPLATE_FILE_ATTACHKEY"));
            if (fileList != null && !fileList.isEmpty()) {
                signFileId = StringUtil.getValue(fileList.get(0), "FILE_ID");
            }
        }
        return signFileId;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/18 19:52:00
     * @param
     * @return
     */
    @Override
    public List<Map<String,Object>> findByList(String busTableName,String busRecordId,String attachKey){
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_FILEATTACH F");
        sql.append(" WHERE F.BUS_TABLENAME=? AND F.BUS_TABLERECORDID=? AND F.ATTACH_KEY=? ");
        sql.append(" ORDER BY F.CREATE_TIME DESC ");
        return ktStampDao.findBySql(sql.toString(), new Object[]{busTableName,busRecordId,attachKey}, null);
    }


}
