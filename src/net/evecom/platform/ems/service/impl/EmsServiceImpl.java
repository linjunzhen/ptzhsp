/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.ems.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.ems.dao.EmsDao;
import net.evecom.platform.ems.service.EmsService;
import net.evecom.platform.ems.util.BASE64Encoder;
import net.evecom.platform.ems.util.HttpUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年2月11日 下午4:02:24
 */
@Service("emsService")
public class EmsServiceImpl extends BaseServiceImpl implements EmsService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(EmsServiceImpl.class);
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 
     */
    @Resource
    private EmsDao dao;
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年2月11日 下午4:05:40
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
     * @author Danto Huang
     * @created 2020年2月11日 下午4:06:41
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findBySqlfilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.record_id,t.exe_id,t.mail_status,e.subject,e.run_status,e.creator_name,");
        sql.append("e.sqrmc,e.create_time ");
        sql.append("from JBPM6_FLOW_EMSINFO t ");
        sql.append("left join jbpm6_execution e on e.exe_id = t.exe_id where e.exe_id is not null");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        return list;
    }
}
