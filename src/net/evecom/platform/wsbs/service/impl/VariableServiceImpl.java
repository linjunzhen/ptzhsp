/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HtmlToWordUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.dao.VariableDao;
import net.evecom.platform.wsbs.service.DocumentTemplateService;
import net.evecom.platform.wsbs.service.VariableService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 内部流程公文虚拟表管理操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("variableService")
public class VariableServiceImpl extends BaseServiceImpl implements VariableService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(VariableServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private VariableDao dao;
    /**
     * 引入Service
     */
    @Resource
    private DocumentTemplateService documentTemplateService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年5月10日 下午6:07:40
     * @param exeId
     * @param documentId
     * @param fromUserName
     * @param toUserName
     * @param nodeName
     * @return
     */
    @Override
    public Map<String, Object> getMapByMoreId(String exeId, String documentId, String fromUserName,
            String toUserName, String nodeName) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT V.* FROM T_WSBS_VARIABLE V ");
        sql.append(" WHERE V.EXE_ID = ? ADN V.DOCUMENT_ID = ? AND V.FROMUSERNAME = ? ");
        sql.append(" AND V.TOUSERNAME = ?  ADN V.NODENAME = ? ");
        params.add(exeId);
        params.add(documentId);
        params.add(fromUserName);
        params.add(toUserName);
        params.add(nodeName);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (list != null && list.size() > 0) {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> m = list.get(i);
                String vName = (String) m.get("VB_NAME");
                Object vValue = new Object();
                if (m.get("DATE_VALUE") != null && StringUtils.isNotEmpty((String) m.get("DATE_VALUE"))) {
                    vValue = m.get("DATE_VALUE");
                } else if (m.get("STRING_VALUE") != null && StringUtils.isNotEmpty((String) m.get("STRING_VALUE"))) {
                    vValue = m.get("STRING_VALUE");
                } else if (m.get("TEXT_VALUE") != null && StringUtils.isNotEmpty((String) m.get("TEXT_VALUE"))) {
                    vValue = m.get("VB_NAME");
                } else if (m.get("NUM_VALUE") != null && StringUtils.isNotEmpty((String) m.get("NUM_VALUE"))) {
                    vValue = m.get("NUM_VALUE");
                }
                variableMap.put(vName, vValue);
            }
            return variableMap;
        } else {
            return null;
        }
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年5月12日 下午5:20:23
     * @param variables
     * @return
     * @see net.evecom.platform.wsbs.service.VariableService#saveHtmlData(java.util.Map)
     */
    @Override
    public String saveHtmlData(Map<String, Object> dataMap, String xnb_id) {
        String temPath = "";
        try {
            String exe_id = (String) dataMap.get("EXE_ID");
            String document_id = (String) dataMap.get("DOCUMENT_ID");
            String fromusername = (String) dataMap.get("FROMUSERNAME");
            String tousername = (String) dataMap.get("TOUSERNAME");
            String nodename = (String) dataMap.get("NODENAME");
            Map<String, Object> needMap = new HashMap<String, Object>();
            // 获取需要保存的数据
            List<Map<String, Object>> needData = getNeedData(dataMap);
            if (needData != null) {

                /*
                 * this.remove("T_WSBS_VARIABLE", new
                 * String[]{"EXE_ID","DOCUMENT_ID","FROMUSERNAME","TOUSERNAME", "NODENAME"}, new
                 * String[]{exe_id,document_id,fromusername,tousername,nodename});
                 */
                for (int i = 0; i < needData.size(); i++) {
                    Map<String, Object> e = needData.get(i);
                    String key = (String) e.get("key");
                    String type = (String) e.get("Type");
                    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(type)) {
                        Map<String, Object> varMap = new HashMap<String, Object>();
                        varMap.put("EXE_ID", exe_id);
                        varMap.put("DOCUMENT_ID", document_id);
                        varMap.put("FROMUSERNAME", fromusername);
                        varMap.put("TOUSERNAME", tousername);
                        varMap.put("NODENAME", nodename);
                        varMap.put("VB_NAME", key);
                        varMap.put("XNB_ID", xnb_id);
                        if (type.equals("ST")) {
                            varMap.put("STRING_VALUE", e.get("value"));
                            needMap.put(key, e.get("value"));
                        } else if (type.equals("CK")) {
                            varMap.put("STRING_VALUE", e.get("value"));
                            if (e.get("value") != null && e.get("value") != "") {
                                needMap.put(key, "√");
                            } else {
                                needMap.put(key, "□");
                            }
                        } else if (type.equals("TX")) {
                            varMap.put("TEXT_VALUE", e.get("value"));
                            needMap.put(key, e.get("value"));
                        } else if (type.equals("DA")) {
                            varMap.put("DATE_VALUE", e.get("value"));
                            needMap.put(key, e.get("value"));
                        } else if (type.equals("NUM")) {
                            varMap.put("NUM_VALUE", e.get("value"));
                            needMap.put(key, e.get("value"));
                        }
                        this.saveOrUpdate(varMap, "T_WSBS_VARIABLE", "");
                    }
                }
            }
            Map<String, Object> documentTemplate = documentTemplateService.getByJdbc("T_WSBS_DOCUMENTTEMPLATE",
                    new String[] { "DOCUMENT_ID" }, new Object[] { document_id });
            Map<String, Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[] { "FILE_ID" }, new Object[] { (String) documentTemplate.get("FTLFILE_ID") });
            temPath = HtmlToWordUtil.createWord(needMap, (String) fileAttach.get("FILE_PATH"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return temPath;
    }

    /**
     * 描述 获取需要保存的数据
     * 
     * @author Faker Li
     * @created 2016年5月12日 下午5:27:31
     * @param dataMap
     * @return
     */
    private List<Map<String, Object>> getNeedData(Map<String, Object> dataMap) {
        List<Map<String, Object>> needList = new ArrayList<Map<String, Object>>();
        Iterator<Map.Entry<String, Object>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = (String) entry.getKey();
            if (key.startsWith("VB_")) {
                Object value = entry.getValue();
                String fieldName = key.substring(key.indexOf("_") + 1, key.lastIndexOf("_"));
                String type = key.substring(key.lastIndexOf("_") + 1, key.length());
                String paramValue = (String) entry.getValue();
                // if (StringUtils.isNotEmpty(paramValue)) {
                Map<String, Object> needMap = new HashMap<String, Object>();
                needMap.put("key", fieldName);
                needMap.put("value", value);
                needMap.put("Type", type);
                needList.add(needMap);
                // }
            }
        }
        if (needList.size() > 0) {
            return needList;
        } else {
            return null;
        }
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年5月16日 下午1:55:17
     * @param xnbId
     * @return
     * @see net.evecom.platform.wsbs.service.VariableService#getMapByXnbId(java.lang.String)
     */
    @Override
    public Map<String, Object> getMapByXnbId(String xnbId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT V.* FROM T_WSBS_VARIABLE V ");
        sql.append(" WHERE V.XNB_ID = ?  ");
        params.add(xnbId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (list != null && list.size() > 0) {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> m = list.get(i);
                String vName = (String) m.get("VB_NAME");
                Object vValue = new Object();
                if (m.get("DATE_VALUE") != null && StringUtils.isNotEmpty((String) m.get("DATE_VALUE"))) {
                    vValue = m.get("DATE_VALUE");
                } else if (m.get("STRING_VALUE") != null && StringUtils.isNotEmpty((String) m.get("STRING_VALUE"))) {
                    vValue = m.get("STRING_VALUE");
                } else if (m.get("TEXT_VALUE") != null && StringUtils.isNotEmpty((String) m.get("TEXT_VALUE"))) {
                    vValue = m.get("VB_NAME");
                } else if (m.get("NUM_VALUE") != null && StringUtils.isNotEmpty((String) m.get("NUM_VALUE"))) {
                    vValue = m.get("NUM_VALUE");
                } else {
                    vValue = null;
                }
                variableMap.put(vName, vValue);
            }
            return variableMap;
        } else {
            return null;
        }
    }
}
