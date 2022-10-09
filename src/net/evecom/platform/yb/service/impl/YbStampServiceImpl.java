/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.yb.dao.YbCommonDao;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.yb.service.YbStampService;
import net.evecom.platform.yb.util.ConvertUpMoney;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.*;

/**
 * 描述 医保全流程材料签章
 * @author Madison You
 * @created 2021年01月18日 下午3:44:16
 */
@Service("ybStampService")
public class YbStampServiceImpl extends BaseServiceImpl implements YbStampService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/18 10:34:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(YbStampServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/18 10:07:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return ybCommonDao;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/18 10:07:00
     * @param
     * @return
     */
    @Resource
    private YbCommonDao ybCommonDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/18 14:13:00
     * @param 
     * @return 
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbPrintConfigService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/2 11:02:00
     * @param
     * @return
     */
    @Resource
    private YbCommonService ybCommonService;

    /**
     * 描述:设置腾讯网关请求头
     *
     * @author Madison You
     * @created 2021/1/18 10:31:00
     * @param
     * @return
     */
    @Override
    public Map<String, String> setTencentNetHeadMap() {
        Map<String,String> headMap = new HashMap<>();
        Map<String,String> tokenHeadMap = new HashMap<>();
        JSONObject json = new JSONObject();
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("TENCENT_NET_TOKEN_URL");
        String username = properties.getProperty("SSDJ_LOGIN_USERNAME");
        String password = properties.getProperty("SSDJ_LOGIN_PASSWORD");
        setUsualHeadMap(headMap);
        json.put("username", username);
        json.put("password", password);
        String result = HttpSendUtil.sendHttpPostJson(url, headMap, json.toJSONString(), "UTF-8");
        try{
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultJson = JSONObject.parseObject(result);
                String token = resultJson.getString("token");
                if (StringUtils.isNotEmpty(token)) {
                    setUsualHeadMap(tokenHeadMap);
                    tokenHeadMap.put("Authorization",token);
                }
            }
        } catch (Exception e) {
            log.error("腾讯网关获取token解析错误：" + result);
            log.error(e.getMessage(), e);
        }
        return tokenHeadMap;
    }

    /**
     * 描述:基本医疗保障参保签章材料数据
     *
     * @author Madison You
     * @created 2021/1/18 10:37:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> jbylbzcbStamp(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> params = new HashMap<>();
        Map<String, Object> requestMap = args.get("requestMap");
        try{
            params.put("token", getToken());
            params.put("aac003", StringUtil.getValue(requestMap, "name"));
            params.put("aac002", StringUtil.getValue(requestMap, "cardNo"));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "cbpz");
            if (dataJson.isSuccess()) {
                String jsonString = dataJson.getJsonString();
                boolean validObject = JSONObject.isValidObject(jsonString);
                if (validObject) {
                    Map<String,Object> dataMap = JSON.parseObject(jsonString, Map.class);
                    returnMap.put("date", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
                    returnMap.put("xm", StringUtil.getValue(requestMap, "name"));
                    returnMap.put("sfzh", StringUtil.getValue(requestMap, "cardNo"));
                    returnMap.put("ylbzbh", StringUtil.getValue(dataMap,"ylbzbh"));
                    returnMap.put("hjszd", StringUtil.getValue(dataMap,"hjszd"));
                    returnMap.put("hjlx", StringUtil.getValue(dataMap,"hjlx"));
                    returnMap.put("jbylbzlx", "");
                    String qssj = StringUtil.getValue(dataMap, "cbksrq");
                    if (StringUtils.isNotEmpty(qssj)) {
                        String qssjDate = bdcSpbPrintConfigService.bdcDateFormat(qssj, "yyyyMM", "yyyy年MM月");
                        returnMap.put("cbksrq", qssjDate);
                    } else {
                        returnMap.put("cbksrq", "");
                    }
                    String jssj = StringUtil.getValue(dataMap, "cbjzrq");
                    if (StringUtils.isNotEmpty(jssj)) {
                        String jssjDate = bdcSpbPrintConfigService.bdcDateFormat(jssj, "yyyyMM", "yyyy年MM月");
                        returnMap.put("cbjsrq", jssjDate);
                    } else {
                        returnMap.put("cbjsrq", "");
                    }
                    String monthCount = StringUtil.getValue(dataMap, "ljsjjfys");
                    if (StringUtils.isNotEmpty(monthCount)) {
                        returnMap.put("ljsjjfys", monthCount + "月");
                    } else {
                        returnMap.put("ljsjjfys", "");
                    }
                    returnMap.put("grzhyydx", StringUtil.getValue(dataMap,"grzhyedx"));
                    returnMap.put("grzhyexx", StringUtil.getValue(dataMap,"grzhye"));
                } else {
                    returnMap.put("errMsg", jsonString);
                }
            } else {
                returnMap.put("errMsg", dataJson.getMsg());
            }
        } catch (Exception e) {
            returnMap.put("errMsg", "获取基本医疗保障参保数据错误，请联系管理员");
            log.error("基本医疗保障参保签章材料数据错误",e);
        }
        return returnMap;
    }

    /**
     * 描述:生育保险参保凭证签章材料数据
     *
     * @author Madison You
     * @created 2021/1/18 14:38:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> sybxcbpzStamp(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String, Object> requestMap = args.get("requestMap");
        Map<String,Object> params = new HashMap<>();
        try{
            params.put("token", getToken());
            params.put("aac003", StringUtil.getValue(requestMap, "name"));
            params.put("aac002", StringUtil.getValue(requestMap, "cardNo"));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "sycbpz");
            if (dataJson.isSuccess()) {
                String jsonString = dataJson.getJsonString();
                boolean validObject = JSONObject.isValidObject(jsonString);
                if (validObject) {
                    Map<String,Object> dataMap = JSON.parseObject(jsonString, Map.class);
                    returnMap.put("cbrxm", StringUtil.getValue(requestMap, "name"));
                    returnMap.put("gmsfzhm", StringUtil.getValue(requestMap, "cardNo"));
                    String accountstartdate = StringUtil.getValue(dataMap, "cbkssj");
                    if (StringUtils.isNotEmpty(accountstartdate)) {
                        String qssjDate = bdcSpbPrintConfigService.bdcDateFormat(accountstartdate, "yyyyMM",
                                "yyyy年MM月");
                        returnMap.put("cbkssj", qssjDate);
                    } else {
                        returnMap.put("cbkssj", "");
                    }
                    String accountenddate = StringUtil.getValue(dataMap, "cbjssj");
                    if (StringUtils.isNotEmpty(accountenddate)) {
                        String jssjDate = bdcSpbPrintConfigService.bdcDateFormat(accountenddate, "yyyyMM",
                                "yyyy年MM月");
                        returnMap.put("cbjssj", jssjDate);
                    } else {
                        returnMap.put("cbjssj", "");
                    }
                } else {
                    returnMap.put("errMsg", jsonString);
                }
            } else {
                returnMap.put("errMsg", dataJson.getMsg());
            }
        } catch (Exception e) {
            returnMap.put("errMsg", "获取医保生育保险参保凭证数据错误，请联系管理员");
            log.error("生育保险参保凭证签章材料数据错误", e);
        }
        return returnMap;
    }

    /**
     * 描述:医保证明打印签章
     *
     * @author Madison You
     * @created 2021/3/2 10:42:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> ybzmdyStamp(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String, Object> requestMap = args.get("requestMap");
        Map<String,Object> params = new HashMap<>();
        try{
            params.put("token", getToken());
            params.put("aac003", StringUtil.getValue(requestMap, "name"));
            params.put("aac002", StringUtil.getValue(requestMap, "cardNo"));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "ybzm");
            if (dataJson.isSuccess()) {
                String jsonString = dataJson.getJsonString();
                boolean validObject = JSONObject.isValidObject(jsonString);
                if (validObject) {
                    Map<String,Object> dataMap = JSON.parseObject(jsonString, Map.class);
                    String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日");
                    returnMap.put("date", date);
                    returnMap.put("cbrxm", StringUtil.getValue(requestMap, "name"));
                    returnMap.put("cbrsfz", StringUtil.getValue(requestMap, "cardNo"));
                    returnMap.put("cbrylbxbh", StringUtil.getValue(dataMap,"cbrylbxbh"));
                    returnMap.put("cbrhjszd", StringUtil.getValue(dataMap,"hjszd"));
                    returnMap.put("cbrhjlx", StringUtil.getValue(dataMap,"hjlx"));
                    returnMap.put("hzxm", "");
                    returnMap.put("hzsfz", "");
                    String qssj = StringUtil.getValue(dataMap, "cbqssj");
                    if (StringUtils.isNotEmpty(qssj)) {
                        String qssjDate = bdcSpbPrintConfigService.bdcDateFormat(qssj, "yyyyMM", "yyyy年MM月");
                        returnMap.put("cbqssj", qssjDate);
                        returnMap.put("dyxsqssj", qssjDate);
                    } else {
                        returnMap.put("cbqssj", "");
                        returnMap.put("dyxsqssj", "");
                    }
                    String jssj = StringUtil.getValue(dataMap, "cbjssj");
                    if (StringUtils.isNotEmpty(jssj)) {
                        String jssjDate = bdcSpbPrintConfigService.bdcDateFormat(jssj, "yyyyMM", "yyyy年MM月");
                        returnMap.put("cbjssj", jssjDate);
                        returnMap.put("dyxsjssj", jssjDate);
                    } else {
                        returnMap.put("cbjssj", "");
                        returnMap.put("dyxsjssj", "");
                    }
                } else {
                    returnMap.put("errMsg", jsonString);
                }
            } else {
                returnMap.put("errMsg", dataJson.getMsg());
            }
        } catch (Exception e) {
            returnMap.put("errMsg", "医保证明打印数据错误，请联系管理员");
            log.error("医保证明打印签章材料数据错误",e);
        }
        return returnMap;
    }

    /**
     * 描述:医保参保缴费证明打印签章
     *
     * @author Madison You
     * @created 2021/3/2 15:48:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> ybcbjfzmdyStamp(Map<String, Map<String, Object>> args) {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String, Object> requestMap = args.get("requestMap");
        Map<String,Object> params = new HashMap<>();
        try{
            params.put("token", getToken());
            params.put("aac003", StringUtil.getValue(requestMap, "name"));
            params.put("aac002", StringUtil.getValue(requestMap, "cardNo"));
            String qssj = StringUtil.getValue(requestMap, "qssj");
            String jssj = StringUtil.getValue(requestMap, "jssj");
            if (StringUtils.isEmpty(qssj)) {
                returnMap.put("errMsg", "查询起始时间字段未传");
                return returnMap;
            }
            if (StringUtils.isEmpty(jssj)) {
                returnMap.put("errMsg", "查询结束时间字段未传");
                return returnMap;
            }
            params.put("aae255", qssj);
            params.put("aae256", jssj);
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "cbjfzm");
            if (dataJson.isSuccess()) {
                String jsonString = dataJson.getJsonString();
                List<Map<String, Object>> dataList = (List) JSON.parseArray(jsonString);
                returnMap.put("xm", StringUtil.getValue(requestMap, "name"));
                returnMap.put("sfzh", StringUtil.getValue(requestMap, "cardNo"));
                returnMap.put("qssj", StringUtil.getValue(requestMap,"qssj"));
                returnMap.put("jssj", StringUtil.getValue(requestMap,"jssj"));
                if (dataList != null && !dataList.isEmpty()) {
                    List<Map<String,Object>> grjfjlList = new ArrayList<>();
                    int i = 0;//条数
                    BigDecimal grjz = new BigDecimal(0);
                    BigDecimal dwjz = new BigDecimal(0);
                    BigDecimal zjez = new BigDecimal(0);
                    BigDecimal jfjsz = new BigDecimal(0);
                    BigDecimal hbjez = new BigDecimal(0);
                    for (Map<String, Object> dataMap : dataList) {
                        String xz = StringUtil.getValue(dataMap, "xz");
                        if (Objects.equals(xz, "310")) {
                            i++;
                            String grjf = StringUtil.getValue(dataMap, "grjf");
                            String dwjf = StringUtil.getValue(dataMap, "dwjf");
                            String zje = StringUtil.getValue(dataMap, "zje");
                            String jfjs = StringUtil.getValue(dataMap, "jfjs");
                            String hbje = StringUtil.getValue(dataMap, "hbje");
                            grjz = grjz.add(setBigDecimalValue(grjf));
                            dwjz = dwjz.add(setBigDecimalValue(dwjf));
                            zjez = zjez.add(setBigDecimalValue(zje));
                            jfjsz = jfjsz.add(setBigDecimalValue(jfjs));
                            hbjez = hbjez.add(setBigDecimalValue(hbje));
                            Map<String,Object> grjfjlMap = new HashMap<>();
                            grjfjlMap.put("dwmc", StringUtil.getValue(dataMap, "dwmc"));
                            grjfjlMap.put("zmny", StringUtil.getValue(dataMap, "zmny"));
                            grjfjlMap.put("grj", grjf);
                            grjfjlMap.put("dwj", dwjf);
                            grjfjlMap.put("zje", zje);
                            grjfjlMap.put("js", jfjs);
                            grjfjlMap.put("zmlx", StringUtil.getValue(dataMap,"zmlx"));
                            grjfjlMap.put("yjlx", StringUtil.getValue(dataMap, "yjlx"));
                            grjfjlMap.put("hb", hbje);
                            grjfjlMap.put("hbrq", StringUtil.getValue(dataMap,"hbrq"));
                            grjfjlList.add(grjfjlMap);
                        }
                    }
                    if (grjfjlList != null && !grjfjlList.isEmpty()) {
                        /*总计*/
                        Map<String,Object> grjfjlzjMap = new HashMap<>();
                        grjfjlzjMap.put("dwmc", "总计");
                        grjfjlzjMap.put("zmny", i);
                        grjfjlzjMap.put("grj", halfBigDecimalValue(grjz));
                        grjfjlzjMap.put("dwj", halfBigDecimalValue(dwjz));
                        grjfjlzjMap.put("zje", halfBigDecimalValue(zjez));
                        grjfjlzjMap.put("js", halfBigDecimalValue(jfjsz));
                        grjfjlzjMap.put("zmlx", "");
                        grjfjlzjMap.put("yjlx", "");
                        grjfjlzjMap.put("hb", halfBigDecimalValue(hbjez));
                        grjfjlzjMap.put("hbrq", "");
                        grjfjlList.add(grjfjlzjMap);
                        returnMap.put("grjfjlList", grjfjlList);
                    } else {
                        returnMap.put("errMsg", "未查询到医保参保缴费证明记录");
                        return returnMap;
                    }
                }
            } else {
                returnMap.put("errMsg", dataJson.getMsg());
            }
        } catch (Exception e) {
            returnMap.put("errMsg", "医保参保缴费证明打印签章材料数据错误，请联系管理员");
            log.error("医保参保缴费证明打印签章材料数据错误", e);
        }
        return returnMap;
    }
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/4 11:05:00
     * @param 
     * @return 
     */
    private BigDecimal setBigDecimalValue(String val){
        if (StringUtils.isNotEmpty(val)) {
            return new BigDecimal(val);
        } else{
            return new BigDecimal(0);
        }
    }
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/4 11:02:00
     * @param 
     * @return 
     */
    private String halfBigDecimalValue(BigDecimal val){
        return val.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/2 11:11:00
     * @param
     * @return
     */
    private String getToken(){
        String token = "";
        Map<String, Object> tokenMap = ybCommonService.getToken();
        if ((boolean) tokenMap.get("success")) {
            token = StringUtil.getValue(tokenMap, "token");
        } else {
            log.error("医保签章获取token值失败！");
        }
        return token;
    }


    /**
     * 描述:解析结果返回data数据
     *
     * @author Madison You
     * @created 2021/1/18 11:33:00
     * @param
     * @return
     */
    private Map<String,Object> parseResultData(String result) {
        log.info("腾讯网关接口返回信息" + result);
        Map<String,Object> returnMap = new HashMap<>();
        if (StringUtils.isNotEmpty(result)) {
            Map<String,Object> resultMap = JSONObject.parseObject(result, Map.class);
            String code = StringUtil.getValue(resultMap, "code");
            if (Objects.equals(code, "01")) {
                returnMap = (Map)resultMap.get("data");
            }
        }
        return returnMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/13 10:14:00
     * @param
     * @return
     */
    private void setUsualHeadMap(Map<String, String> headMap) {
        String str = new Date().getTime() / 1000 + "mKyaiApCbU4GFVacQ0QFbrKUn8EAGPfc" +
                "123456789abcdefg" + new Date().getTime() / 1000;
        String signature = "";
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            signature = Hex.encodeHexString(instance.digest(str.getBytes()));
        } catch (Exception e) {
            log.error("腾讯网关登录加密错误");
            log.error(e.getMessage(), e);
        }
        headMap.put("x-tif-paasid", "spjk");
        headMap.put("x-tif-timestamp", new Date().getTime() / 1000 + "");
        headMap.put("x-tif-signature", signature);
        headMap.put("x-tif-nonce", "123456789abcdefg");
    }

}
