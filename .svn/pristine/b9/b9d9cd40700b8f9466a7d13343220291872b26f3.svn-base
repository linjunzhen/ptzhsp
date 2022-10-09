/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.DesForEstate;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.bdc.dao.BdcQueryDao;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 不动产接口操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@PropertySource("classpath:conf/env.properties")
@Service("bdcQueryService")
public class BdcQueryServiceImpl extends BaseServiceImpl implements BdcQueryService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcQueryServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private BdcQueryDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * env
     */
    @Autowired
    private Environment env;

    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        String exeId = sqlFilter.getRequest().getParameter("EXE_ID");
        String applyStatus = sqlFilter.getRequest().getParameter("APPLY_STATUS");
        String name = sqlFilter.getQueryParams().get("Q_T.NAME_LIKE") == null ? ""
                : sqlFilter.getQueryParams().get("Q_T.NAME_LIKE").toString();
        String idNum = sqlFilter.getQueryParams().get("Q_T.IDNUM_LIKE") == null ? ""
                : sqlFilter.getQueryParams().get("Q_T.IDNUM_LIKE").toString();

        List<Map<String, Object>> list = null;
        // 如果是已经在办理的件，直接查询数据库，否则通过接口查询
        if (StringUtils.isNotEmpty(exeId) && !"0".equals(applyStatus)) {
            Map<String, Object> execution = dao.getByJdbc("jbpm6_execution", new String[] { "EXE_ID" }, 
                    new Object[] { exeId });
            if(execution == null) {
                execution = dao.getByJdbc("jbpm6_execution_evehis", new String[] { "EXE_ID" }, new Object[] { exeId });
            }
            // 业务表
            String busTableName = String.valueOf(execution.get("BUS_TABLENAME"));
            String busCordId = String.valueOf(execution.get("BUS_RECORDID"));
            String primaryKeyName = String.valueOf(this.getPrimaryKeyName(busTableName).get(0));
            Map<String, Object> busMap = dao.getByJdbc(busTableName, new String[] { primaryKeyName },
                    new Object[] { busCordId });
            // 不动产对接的接口本地数据
            String obJson = busMap.get("POWERPEOPLEINFO_JSON") == null ? ""
                    : busMap.get("POWERPEOPLEINFO_JSON").toString();
            if (StringUtils.isNotEmpty(obJson)) {
                list = (List<Map<String, Object>>) JSON.parse(obJson);
            }
        } else if (StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(idNum)) {
            List<Map<String, Object>> famlily = new ArrayList<Map<String, Object>>();
            Map<String, Object> people = new HashMap<String, Object>();
            people.put("qlrmc", name);
            people.put("qlrzjhm", idNum);
            famlily.add(people);
            // String json=(String)JSON.toJSON(famlily);
            try {
                list = queryOgligeeOfDataByIF(famlily);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        return list;
    }

    /**
     * 获得符合in的sql语句
     * @param str   源字符串
     * @param symbol  符号
     * @return  'a','b'
     */
    public String getSplitForSqlIn(String str, String symbol) {
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer inStr = new StringBuffer();
            String[] strs = str.split(symbol);
            for (int i = 0; i < strs.length; i++) {
                inStr.append("'").append(strs[i]).append("',");
            }
            return inStr.substring(0, inStr.length() - 1).toString();
        } else {
            return " ";
        }
    }

    /**
     * 通过不动产接口获取权利人信息qlrmc
     * qlr  zjhm
     * @return
     */
    public List<Map<String, Object>> queryOgligeeOfDataByIF(List<Map<String, Object>> family) throws Exception {
        List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
        HashMap<String, Object> para = new HashMap<String, Object>();
        para.put("qlrlist", family);
        AjaxJson data=queryAjaxJsonOfBdc(para,"dataQuery");
        if(data.isSuccess()){
            if ("查询正常".equals(data.getMsg())) {
                list = getListByRespContent(data.getJsonString());
            }
        }
        return list;
    }

    /**
     * 通过不动产共用查询接口获取信息
     * @param info  查询的信息
     * @param urlDicType 为 entrusteUrl是不动产委托备案接口服务
     * announceUrl是不动产预告档案查询接口服务   contractUrl是房地产交易合同备案查询接口
     * @return    "error":接口异常；"05":查询无数据；其他数据正常；
     * @throws Exception
     */
    public AjaxJson queryAjaxJsonOfBdc(Map<String, Object> info, String urlDicType) {
        AjaxJson json = new AjaxJson();
        String queryResult = "";
        try {
            queryResult = queryDataOfBdcByIF(info, urlDicType);
        } catch (Exception e) {
            log.info("", e);
        }
        if ("error".equals(queryResult)) {
            json.setMsg("接口异常");
            json.setSuccess(false);
        } else if ("05".equals(queryResult)) {
            json.setMsg("查询无数据");
            json.setSuccess(true);
            json.setJsonString("");
        } else {
            json.setMsg("查询正常");
            json.setSuccess(true);
            json.setJsonString(queryResult);
        }
        return json;
    }

    /**
     * 通过不动产普通接口获取信息
     * @param info  查询的信息
     * @param urlDicType
     * @return    "error":接口异常；"05":查询无数据；其他数据正常；
     * @throws Exception
     */
    public String queryDataOfBdcByIF(Map<String, Object> info, String urlDicType) throws Exception {
        StringBuffer urlStr = new StringBuffer();
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/8a095e91-bc50-4dbf-b914-1b14587d8dd6,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/16616a78-847b-438f-8b55-907e93ea9c76,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/83f95428-6e42-4446-9439-46a89520a367,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/0ea7bf9a-b157-4718-9011-ee017efa1081,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/67d55c68-13fc-4454-acca-f6152dc85e75,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/c6ebe12b-5878-4950-99d9-5740844909fa,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/d181f6bc-a26a-44ab-8455-5171e205afc3,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/2d2b8d84-2c74-4f51-9874-d40ba88ff94e,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/fa9e3e0c-0e77-4357-a2e6-a1c04092fb24,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/b3e6f023-30c9-4ac9-ad6f-490916c6424e,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/a8bd7ad9-3752-4622-8f6a-e2057ed48ae6,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/f753253a-af8d-4246-b268-361e3532fa6f,");
        urlStr.append("http://10.23.202.15/GTServer/api/Script/Post/025d0683-61bb-44f2-beb1-194fcce6dea1,");
        urlStr.append("http://10.23.202.15:8055/GTServer/api/Script/Post/62ed64a1-5a7c-4ff2-982f-8ac6c1f3b313,");
        String queryResult = "";
        String url =env.getProperty("bdc.bdcCommonQueryUrl");
        String realBdcUrl = dictionaryService.getDicCode("config", urlDicType);
        String bsm = dictionaryService.getDicCode("config", "bsm");
        // 发送请求
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = "";
        JSONObject jsonParam = new JSONObject();
        HashMap<String, Object> para = new HashMap<String, Object>();
        para.put("bsm", bsm);
        para.putAll(info);
        jsonParam.put("realBdcUrl", realBdcUrl);
        jsonParam.put("paras", para);
        // 解决中文乱码问题
        StringEntity entity = null;
        entity = new StringEntity(jsonParam.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(httpPost);
            // 解析数据
            if (resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
                /*2019/12/04 Madison You 新增十一个不动产接口,数据无需加密*/
                if (urlStr.indexOf(realBdcUrl) != -1) {
                    queryResult = respContent;
                } else {
                    queryResult = getResultByRespContent(respContent);
                }
            } else {
                queryResult = "error";
            }
        } catch (Exception e) {//异常处理
            log.error("回调http post方法异常了",e);
        } finally {//关闭连接
            try {
                if(resp!=null){
                    resp.close();
                }
                if(client!=null){
                    client.close();
                }
            } catch (Exception e2) {
                log.error("关闭response失败",e2);
            }
        }
        /*int lenth=queryResult.length()>200?200:queryResult.length();
        log.info(String.format("urlDicType===%s,queryResult===%s",urlDicType,
                queryResult.substring(0,lenth)));*/
        log.info(String.format("urlDicType===%s,queryResult===%s",urlDicType,queryResult));        
        return queryResult;
    }

    /**
     * 不动产返回结果解析json数据
     * @param respContent
     * @return
     * @throws Exception
     */
    private String getResultByRespContent(String respContent) {
        // List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String sKey = dictionaryService.getDicCode("config", "sKey");
        String result = "";
        List<Map<String, Object>> resultList;
        if (StringUtils.isNotEmpty(respContent) && !"error".equals(respContent)) {
            Map<String, Object> resultMap = (Map) JSON.parse(respContent);
            String resultCode = String.valueOf(resultMap.get("retcode"));
            // 00查询结果正常
            if ("00".equals(resultCode)) {
                String dt = String.valueOf(resultMap.get("dt"));
                try {
                    dt = DesForEstate.decrypt(dt, sKey);
                    resultList = (List<Map<String, Object>>) JSON.parse(dt);
                    result = JSON.toJSONString(resultList);
                } catch (Exception e) {
                    log.info(e.getMessage());
                    log.info("解析数据失败");
                    result = "error";
                } finally {
                    return result;
                }
            } else if ("05".equals(resultCode)) {
                // 查询数据为空
                return "05";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }

    /**
     *转化为json数据
     * @return
     */
    public String getJsonResultOfBdcQuery(List<Map<String, Object>> family) throws Exception {
        List<Map<String, Object>> list = queryOgligeeOfDataByIF(family);
        String json = JSON.toJSONString(list);
        return json;
    }
    /**
     * 不动产资料查询结果合并和list
     * @param respContent
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> getListByRespContent(String respContent) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> qlrList = (List<Map<String, Object>>) JSON.parse(respContent);
        for (Map<String, Object> qlr : qlrList) {
            qlr.put("NAME", String.valueOf(qlr.get("qlrmc")));
            qlr.put("IDNUM", String.valueOf(qlr.get("qlrzjhm")));
            String cxjgJson = String.valueOf(qlr.get("cxjg"));
            List<Map<String, Object>> cxjgs = (List<Map<String, Object>>) JSON.parse(cxjgJson);
            for (Map<String, Object> cxjg : cxjgs) {
                String uuid = UUIDGenerator.getUUID();
                Map<String, Object> obMap = new HashMap<String, Object>();
                obMap.putAll(cxjg);
                obMap.put("OB_ID", uuid);
                obMap.put("NAME", String.valueOf(cxjg.get("QLR")));
                obMap.put("IDNUM", String.valueOf(cxjg.get("ZJH")));
                String propertyType = String.valueOf(cxjg.get("ZSLX"));
                obMap.put("PROPERTY_TYPE", propertyType);
                String propertyStatus = String.valueOf(cxjg.get("ZT"));
                obMap.put("PROPERTY_STATUS", propertyStatus);
                String propertyHuman = String.valueOf(cxjg.get("QLR"));
                obMap.put("PROPERTY_HUMAN", propertyHuman);
                String propertyAddr = String.valueOf(cxjg.get("ADDRESS"));
                obMap.put("PROPERTY_ADDR", propertyAddr);
                String propertyNum = String.valueOf(cxjg.get("QZH"));
                obMap.put("PROPERTY_NUM", propertyNum);
                String propertyTime = String.valueOf(cxjg.get("DJSJ"));
                obMap.put("PROPERTY_TIME", propertyTime);
                String propertyArea = String.valueOf(cxjg.get("BUILDAREA"));
                obMap.put("PROPERTY_AREA", propertyArea);
                String propertyUse = String.valueOf(cxjg.get("USEFACT"));
                obMap.put("PROPERTY_USE", propertyUse);
                String propertyNature = String.valueOf(cxjg.get("HOUSETYPE"));
                obMap.put("PROPERTY_NATURE", propertyNature);
                list.add(obMap);
            }
        }
        return list;
    }
}
