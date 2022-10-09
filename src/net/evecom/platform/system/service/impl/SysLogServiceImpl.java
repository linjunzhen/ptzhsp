/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.SysLogDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:27:15
 */
@Service("sysLogServiceImpl")
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SysLogServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private SysLogDao dao;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 保存操作级别的日志 描述
     * 
     * @author Flex Hu
     * @created 2014年9月14日 上午8:37:24
     * @param logContent
     *            :日志内容
     * @param operateType
     *            :操作类型
     */
    public void saveLog(String logContent, int operateType) {
        HttpServletRequest request = AppUtil.getRequest();
        String browser = BrowserUtils.checkBrowse(request);
        String operateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        SysUser sysUser = AppUtil.getLoginUser();
        String idAddress = BrowserUtils.getIpAddr(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("BROWSER", browser);
        map.put("LOG_CONTENT", logContent);
        map.put("OPERATE_TYPE", operateType);
        map.put("OPERATE_TIME", operateTime);
        if(sysUser!=null) {
            map.put("FULLNAME", sysUser.getFullname());
            map.put("USERNAME", sysUser.getUsername());
            map.put("USERID", sysUser.getUserId());
        }else {
            map.put("FULLNAME", "website");
            map.put("USERNAME", "website");
            map.put("USERID", "website");
        }
        map.put("IP_ADDRESS", idAddress);
        dao.saveOrUpdate(map, "T_MSJW_SYSTEM_SYSLOG", null);
    }

    /**
     * 
     * 描述 保存操作级别的数据,面向网站公众
     * 
     * @author Flex Hu
     * @created 2015年12月2日 上午9:45:08
     * @param logContent
     * @param operateType
     */
    public void saveLogForMember(String logContent, int operateType) {
        HttpServletRequest request = AppUtil.getRequest();
        String browser = BrowserUtils.checkBrowse(request);
        String operateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        Map<String, Object> member = AppUtil.getLoginMember();
        String idAddress = BrowserUtils.getIpAddr(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("BROWSER", browser);
        map.put("LOG_CONTENT", logContent);
        map.put("OPERATE_TYPE", operateType);
        map.put("OPERATE_TIME", operateTime);
        map.put("FULLNAME", member.get("YHMC"));
        map.put("USERNAME", member.get("YHZH"));
        map.put("USERID", member.get("USER_ID"));
        map.put("IP_ADDRESS", idAddress);
        map.put("BUS_TABLENAME", "T_BSFW_USERINFO");
        dao.saveOrUpdate(map, "T_MSJW_SYSTEM_SYSLOG", null);
    }

    /**
     * 
     * 描述 保存用户中心用户操作级别日志
     * 
     * @author Flex Hu
     * @created 2014年11月17日 下午3:59:50
     * @param logContent
     * @param operateType
     * @param personnel
     */
    public void saveLog(String logContent, int operateType, Map<String, Object> personnel) {
        HttpServletRequest request = AppUtil.getRequest();
        String browser = BrowserUtils.checkBrowse(request);
        String operateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String idAddress = BrowserUtils.getIpAddr(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("BROWSER", browser);
        map.put("LOG_CONTENT", logContent);
        map.put("OPERATE_TYPE", operateType);
        map.put("OPERATE_TIME", operateTime);
        map.put("FULLNAME", personnel.get("PERSONNEL_NAME").toString());
        map.put("USERNAME", personnel.get("USERNAME").toString());
        map.put("USERID", personnel.get("PERSONNEL_ID").toString());
        map.put("IP_ADDRESS", idAddress);
        map.put("BUS_TABLENAME", "T_DELEGATE_PERSONNEL");
        dao.saveOrUpdate(map, "T_MSJW_SYSTEM_SYSLOG", null);
    }
   /**
    * 
    * 描述：保存信息表信息更新
    * @author Water Guo
    * @created 2017-11-10 下午2:43:01
    * @param logContent
    * @param operateType
    * @param primaryValue
    */
    private void saveLogForTable(String busTableName,String logContent, 
            int operateType,String busIndex) {
        HttpServletRequest request = AppUtil.getRequest();
        String browser = BrowserUtils.checkBrowse(request);
        String operateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        SysUser sysUser = AppUtil.getLoginUser();
        String idAddress = BrowserUtils.getIpAddr(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("BROWSER", browser);
        map.put("OPERATE_CONTENT", logContent);
        map.put("OPERATE_TYPE", operateType);
        map.put("OPERATE_TIME", operateTime);
        map.put("FULLNAME", sysUser.getFullname());
        map.put("USERNAME", sysUser.getUsername());
        map.put("USERID", sysUser.getUserId());
        map.put("IP_ADDRESS", idAddress);
        map.put("BUS_INDEX", busIndex);
        map.put("BUS_TABLENAME", busTableName);
        dao.saveOrUpdate(map, "T_MSJW_SYSTEM_BUSLOG", null);
    }
    /**
     * 
     * 描述：联合查询获取目的表变更表信息
     * @author Water Guo
     * @created 2017-11-10 下午4:29:41
     * @param indexColName 索引列
     * @param busTableName 联合查询表
     * @param colValues 查询的列值
     */
    public List<Map<String,Object>> getBusTableLogs(String indexColName,String busTableName,String[] colNames,
          Object[] colValues ){
       return dao.getBusTableLogs(indexColName, busTableName, colNames, colValues);
    }
    /**
     * 
     * 描述：日志的字典注释
     * @author Water Guo
     * @created 2017-11-17 上午11:54:45
     * @param list
     * @return
     */
    public List<Map<String,Object>> logToStand(List<Map<String,Object>> list){
        for(Map<String,Object> map:list){
            String content=(String)map.get("OPERATE_CONTENT");
            String[] contents=content.split("取字典类别:");
            if(contents.length>1){
               String typeCode=contents[1];
               typeCode=typeCode.substring(0, typeCode.indexOf(")"));
               List<Map<String,Object>> dicList=dicTypeService.getAllByJdbc("t_msjw_system_dictionary",
                       new String[]{"TYPE_CODE"}, new Object[]{typeCode.trim()});
               StringBuffer str=new StringBuffer();
               for(Map<String,Object> dicMap:dicList){
                   String dicCode=(String)dicMap.get("DIC_CODE");
                   String dicName=(String)dicMap.get("DIC_Name");
                   str.append("'").append(dicCode).append("':'").append(dicName).append("' ");
               }
               content=content.replace("取字典类别:"+typeCode, str.toString());
               map.put("OPERATE_CONTENT", content);
            }
        }
        return list;
    }
    /**
     * 
     * 描述：日志的字典注释
     * @author Water Guo
     * @created 2017-11-20 上午11:32:04
     * @param content
     * @return
     */
    private String logBydic(String content){
            if(StringUtils.isNotEmpty(content)){
            String[] contents=content.split("取字典类别:");
            if(contents.length>1){
               String typeCode=contents[1];
               typeCode=typeCode.substring(0, typeCode.indexOf(")"));
               List<Map<String,Object>> dicList=dicTypeService.getAllByJdbc("t_msjw_system_dictionary",
                       new String[]{"TYPE_CODE"}, new Object[]{typeCode.trim()});
               StringBuffer str=new StringBuffer();
               for(Map<String,Object> dicMap:dicList){
                   String dicCode=(String)dicMap.get("DIC_CODE");
                   String dicName=(String)dicMap.get("DIC_Name");
                   str.append("'").append(dicCode).append("':'").append(dicName).append("' ");
               }
               content=content.replace("取字典类别:"+typeCode, str.toString());
            }
            return content;
            }else{
              return "";
            }
    }
    /**
     * 
     * 描述：查询表更新信息
     * @author Water Guo
     * @created 2017-11-15 下午5:16:34
     * @param indexColName
     * @param busTableName
     * @return
     */
    public List<Map<String,Object>> getChBusTableLogs(String indexColVal,String busTableName){
        return dao.getChBusTableLogs(indexColVal, busTableName);
    }
  /**
   * 描述：
   * @author Water Guo
   * @created 2017-11-13 上午10:21:50
   * @param tableName  表名
   * @param colNames  列数值名  如果多个数值请以,隔开
   * @param colValues  列数组值 如果多个数值请以,隔开
   * @param insertMap  要更新插入的map
   * @param busIndex   索引值，通过索引查找
   */
    public void saveLogByMap(String tableName, String colNames, String colValues, 
            Map<String, Object> insertMap,String busIndex) {
        if (StringUtils.isNotEmpty(busIndex)&&StringUtils.isNotEmpty(colValues)) {
            // 获得数据库原始数据
            Map<String, Object> sqlMap = dao.getByJdbc
                    (tableName, new String[] { colNames }, new Object[] { colValues });
            Iterator it = insertMap.entrySet().iterator();
            Set<String> tableNames = new HashSet<String>();
            tableNames.add(tableName.toUpperCase());
            Map<String, String> comments = dao.getColumnCommentByTableNames(tableNames);
            // 遍历比较更新的数据字段
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String insertFieldName = (String) entry.getKey();
                insertFieldName=insertFieldName.toUpperCase();
                if (sqlMap.containsKey(insertFieldName)) {
                    String insertValue = String.valueOf(entry.getValue());
                    insertValue=insertValue.trim();
                    //如果是ID类的字段不纳入日志
                    if(insertValue.length()==32){
                        continue;
                    }
                    String sqlValue =sqlMap.get(insertFieldName)==null?""
                            :sqlMap.get(insertFieldName).toString().trim();
                    if (StringUtils.isNotEmpty(insertValue)&&!insertValue.equals(sqlValue)) {
                        if(StringUtils.isEmpty(sqlValue)){
                            sqlValue="<font color='red'>空值</font>";
                        }
                        // 字段注释值作为中文字段名
                        String comment = comments.get(insertFieldName);
                        comment=this.logBydic(comment);
                        StringBuffer content = new StringBuffer();
                        content.append("【").append(comment).append("】:'");
                        content.append(sqlValue).append("'").append("改为'");
                        content.append(insertValue).append("'");
                        saveLogForTable(tableName.toUpperCase(),content.toString(), 2, busIndex);
                    }
                }
            }
        }
    }
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT L.* FROM T_MSJW_SYSTEM_SYSLOG L");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 清除缓存的方法
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午9:40:54
     * @param map
     * @return
     * @TriggersRemove(cacheName="sysLogCache",removeAll=true) public String
     *                                                         saveOrUpdate
     *                                                         (Map<String
     *                                                         ,Object> map){
     *                                                         return null; }
     */

    /**
     * 
     * 描述 EXP:配置方法级别缓存的方法
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午9:40:03
     * @return
     * @Cacheable(cacheName="sysLogCache") public List<Map<String,Object>>
     *                                     findList(){ return null; }
     */

    /**
     * 
     * 描述 获取用户登录的最后时间
     * @author Flex Hu
     * @created 2014年11月17日 下午4:06:49
     * @param userId
     * @return
     */
    public String getPersonnelLastLoginTime(String userId) {
        StringBuffer sql = new StringBuffer("select OPERATE_TIME ").append(
                "FROM T_MSJW_SYSTEM_SYSLOG WHERE USERID=? AND BUS_TABLENAME=?").append(" ORDER BY OPERATE_TIME DESC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { userId, "T_DELEGATE_PERSONNEL" },
                null);
        String time = null;
        if (list != null && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            time = map.get("OPERATE_TIME").toString();
        }
        return time;
    }

    /**
     * 
     * 描述 获取上次登录信息,面向网站公众
     * 
     * @author Flex Hu
     * @created 2015年12月2日 上午9:55:33
     * @param userInfo
     * @return
     */
    public Map<String, Object> getLastLoginInfoForMember(Map<String, Object> userInfo) {
        // 获取用户ID
        String userId = (String) userInfo.get("USER_ID");
        StringBuffer sql = new StringBuffer("select OPERATE_TIME,IP_ADDRESS ")
                .append("FROM T_MSJW_SYSTEM_SYSLOG WHERE USERID=? AND BUS_TABLENAME=?");
        sql.append(" AND OPERATE_TYPE=? ORDER BY OPERATE_TIME DESC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { userId, "T_BSFW_USERINFO",
                SysLogService.OPERATE_TYPE_LOGIN }, null);
        if (list != null && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            userInfo.put("SCDLSJ", map.get("OPERATE_TIME"));
            userInfo.put("SCDLIP", map.get("IP_ADDRESS"));
          }else {
            userInfo.put("SCDLSJ", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            HttpServletRequest request = AppUtil.getRequest();
            String idAddress = BrowserUtils.getIpAddr(request);
            userInfo.put("SCDLIP", idAddress);
        }
        return userInfo;
    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2016年4月27日 下午4:53:16
     * @param string
     */
    public void saveLogForApp(String logContent, int operateType, Map<String, Object> personnel) {
        HttpServletRequest request = AppUtil.getRequest();
        String browser = BrowserUtils.checkBrowse(request);
        String operateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String idAddress = BrowserUtils.getIpAddr(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("BROWSER", browser);
        map.put("LOG_CONTENT", logContent);
        map.put("OPERATE_TYPE", operateType);
        map.put("OPERATE_TIME", operateTime);
        map.put("FULLNAME", personnel.get("FULLNAME").toString());
        map.put("USERNAME", personnel.get("USERNAME").toString());
        map.put("USERID", personnel.get("USER_ID").toString());
        map.put("IP_ADDRESS", idAddress);
        map.put("BUS_TABLENAME", "T_DELEGATE_PERSONNEL");
        dao.saveOrUpdate(map, "T_MSJW_SYSTEM_SYSLOG", null);
    }

    /**
     * 单点登录省网获取AccessToken 描述
     * 
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param
     * @param
     */
    @Override
    public String getAccessToken() {
        // TODO Auto-generated method stub
        String url = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "ca-1");
        String appId = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "APP_ID");
        String appSecret = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "APP_SECRET");
        String resultStr = HttpRequestUtil.sendGet(url, "app_id=" + appId + "&app_secret=" + appSecret);
        log.info(resultStr);
        Map<String, Object> m = JsonUtil.parseJSON2Map(resultStr);
        boolean result = (Boolean) m.get("result");
        String accessToken = "";
        if (result) {
            accessToken = m.get("access_token") == null ? "" : m.get("access_token").toString();
        }else{
            accessToken = m.get("errcode").toString();
        }
        return accessToken;
    }

    /**
     * 转跳至省网登入页 描述
     * 
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param accessToken
     * @param basepath
     */
    @Override
    public String getSwbLogin(String accessToken, String basepath) {
        // TODO Auto-generated method stub
        String url = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "ca-2");
        // String returnUrlString =
        // "http://127.0.0.1:8087/ptzhsp/loginController.do?getUserToken";
        String returnUrlString = basepath + "/userInfoController/getUserToken.do";
        String resultStr = HttpRequestUtil.sendGet(url, "access_token=" + accessToken + "&return_url="
                + returnUrlString);
        log.info(resultStr);
        Map<String, Object> m = JsonUtil.parseJSON2Map(resultStr);
        boolean result = (Boolean) m.get("result");
        String loginUrl = "";
        if (result) {
            loginUrl = m.get("login_url").toString();
        }else {
            loginUrl = m.get("errcode").toString();
        }
        return loginUrl;
    }

    /**
     * 转跳至省网注册页 描述
     * 
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param accessToken
     * @param userType
     * @param basepath
     */
    @Override
    public String getSwbRegister(String accessToken, String userType, String basepath) {
        // TODO Auto-generated method stub
        String url = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "ca-3");
        // String returnUrlString =
        // "http://127.0.0.1:8087/ptzhsp/loginController.do?getUserToken";
        String returnUrlString = basepath + "/userInfoController/getUserToken.do";
        String resultStr = HttpRequestUtil.sendGet(url, "access_token=" + accessToken + "&user_type=" + userType
                + "&return_url=" + returnUrlString);
        log.info(resultStr);
        Map<String, Object> m = JsonUtil.parseJSON2Map(resultStr);
        boolean result = (Boolean) m.get("result");
        String registeredUrl = "";
        if (result) {
            registeredUrl = m.get("registered_url").toString();
        }else {
            registeredUrl = m.get("errcode").toString();
        }
        return registeredUrl;
    }

    /**
     * 用户数据处理 描述
     * 
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param accessToken
     * @param userToken
     */
    @Override
    public Map<String, Object> swbUserInfor(String accessToken, String userToken) {
        // TODO Auto-generated method stub
        String url = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "ca-4");
        String resultStr = HttpRequestUtil.sendGet(url, "access_token=" + accessToken + "&user_token=" + userToken);
//        log.info(resultStr);
//        log.info(resultStr);
        Map<String, Object> m = JsonUtil.parseJSON2Map(resultStr);
        boolean result = (Boolean) m.get("result");
        JSONObject data = (JSONObject) m.get("datas");
        Map<String, Object> map = new HashMap<String, Object>();
        if (result) {
            map.put("REAL_NAME_TYPE", data.getString("real_name_type"));
            map.put("LAST_UPDATE_TIME", data.getString("last_update_time"));
            map.put("IS_REAL_NAME", data.getString("is_real_name"));
            map.put("YHZT", 1);
            if ("personal".equals(data.getString("user_type"))) {
                map.put("USER_TYPE", 1);
                String personal = data.getString("personal");
                Map<String, Object> personalMap = JsonUtil.parseJSON2Map(personal);
                map.put("DLMM", "swb" + personalMap.get("mobile_phone") == null ?
                        "" : personalMap.get("mobile_phone").toString());
                map.put("YHZH", personalMap.get("login_name") == null ?
                        "" : personalMap.get("login_name").toString());
                map.put("ZTZZ", personalMap.get("address") == null ?
                        "" : personalMap.get("address").toString());
                map.put("SJHM", personalMap.get("mobile_phone") == null ?
                        "" : personalMap.get("mobile_phone").toString());
                map.put("YHXB", Integer.valueOf(personalMap.get("sex").toString()));
                map.put("YHMC", personalMap.get("name") == null ?
                        data.get("USER_ACCOUNT").toString() : personalMap.get("name").toString());
                map.put("ZJLX", personalMap.get("certificate_type") == null ?
                        "" : personalMap.get("certificate_type").toString());
                map.put("ZJHM", personalMap.get("certificate_number") == null ?
                        "" : personalMap.get("certificate_number").toString());
                map.put("DZYX", personalMap.get("email") == null ?
                        "" : personalMap.get("email").toString());
            } else if ("enterprise".equals(data.getString("user_type"))) {
                map.put("USER_TYPE", 2);
                String enterprise = data.getString("enterprise");
                Map<String, Object> enterpriseMap = JsonUtil.parseJSON2Map(enterprise);
                map.put("DLMM", "swb" + enterpriseMap.get("mobile_phone") == null ?
                        "" : enterpriseMap.get("mobile_phone").toString());
                map.put("JBRXM", enterpriseMap.get("jbr_name") == null ?
                        "" : enterpriseMap.get("jbr_name").toString());
                map.put("JGLX", enterpriseMap.get("org_type") == null ?
                        "" : enterpriseMap.get("org_type").toString());
                map.put("DHHM", enterpriseMap.get("org_law_mobile") == null ?
                        "" : enterpriseMap.get("org_law_mobile").toString());
                map.put("FRDB", enterpriseMap.get("law_name") == null ?
                        "" : enterpriseMap.get("law_name").toString());
                map.put("YHZH", enterpriseMap.get("login_name") == null ?
                        "" : enterpriseMap.get("login_name").toString());
                map.put("DWDZ", enterpriseMap.get("org_address") == null ?
                        "" : enterpriseMap.get("org_address").toString());
                map.put("MOBILE_PHONE", enterpriseMap.get("mobile_phone") == null ?
                        "" : enterpriseMap.get("mobile_phone").toString());
                map.put("YHMC", enterpriseMap.get("name") == null ?
                        "" : enterpriseMap.get("name").toString());
                map.put("JBRYXDZ", enterpriseMap.get("jbr_email") == null ?
                        "" : enterpriseMap.get("jbr_email").toString());
                String sex = enterpriseMap.get("org_law_sex")==null?
                        "":enterpriseMap.get("org_law_sex").toString();
                if (StringUtils.isNotEmpty(sex)) {
                    map.put("ORG_LAW_SEX", Integer.valueOf(sex));
                }
                map.put("JBRSFZ",enterpriseMap.get("jbr_certificateNumber") == null ?
                        "" : enterpriseMap.get("jbr_certificateNumber").toString());
                map.put("ORG_NAME", enterpriseMap.get("org_name") == null ?
                        "" : enterpriseMap.get("org_name").toString());
                map.put("ZZJGDM", enterpriseMap.get("org_code") == null ?
                        "" : enterpriseMap.get("org_code").toString());
                map.put("ORG_LAW_IDCARD",enterpriseMap.get("org_law_idcard") == null ?
                        "" : enterpriseMap.get("org_law_idcard").toString());
            }
            // 本地是否存在该用户
            int existence = userExistence(map.get("YHZH").toString());
            if (existence == 0) {
                dao.saveOrUpdate(map, "T_BSFW_USERINFO", null);
            }
        }else {
            map.put("ERRCODE",m.get("errcode")==null?"":m.get("errcode").toString());
        }
        return map;
    }

    private int userExistence(String username) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT T.USER_ID FROM T_BSFW_USERINFO T WHERE T.YHZH = ?");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { username }, null);
        int size = list.size();
        return size;
    }

    /**
     * 查询是否通过闽政通注册过
     * @param username
     * @return
     */
    private int userExistenceByYhzhAndMztyhzh(String username) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT T.USER_ID FROM T_BSFW_USERINFO T WHERE T.YHZH = ? or MZT_YHZH= ? ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { username,username }, null);
        int size = list.size();
        return size;
    }
    @Override
    public List<Map<String, Object>> useridExistence(String uploadUserId) {
        return dao.getListByJdbc(uploadUserId);
    }
    /**
     * 获取用户信息
     * 
     * @param resultData
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> mztUserInfor(String resultData) {
        Map<String, Object> m = JsonUtil.parseJSON2Map(resultData);
        boolean result = (Boolean) m.get("success");
        JSONObject data = (JSONObject) m.get("data");
        Map<String, Object> map = new HashMap<String, Object>();
        if (result) {
            map.put("REAL_NAME_TYPE", data.getString("real_name_type"));
            map.put("LAST_UPDATE_TIME", data.getString("last_update_time"));
            map.put("IS_REAL_NAME", data.getString("is_real_name"));
            map.put("YHZT", 1);
            map.put("MZT_USER_ID", data.getString("USER_ID"));
            map.put("MZT_USER_TOKEN", data.getString("USER_TOKEN"));
            if ("1".equals(data.getString("USER_TYPE"))) {
                map.put("USER_TYPE", 1);
                map.put("DLMM", data.get("USER_MOBILE") == null ? ""
                        : StringUtil.getMd5Encode("mzt" + data.get("USER_MOBILE").toString()));
                map.put("YHZH", "mzt_1_"+data.get("USER_MOBILE").toString());
                String address = "";
                if (data.get("POPULATION_CADDRESS") != null) {
                    address = data.get("POPULATION_CADDRESS").toString();
                } else if (data.get("POPULATION_JZDZ") != null) {
                    address = data.get("POPULATION_JZDZ").toString();
                } else if (data.get("POPULATION_HJDZ") != null) {
                    address = data.get("POPULATION_HJDZ").toString();
                }
                map.put("ZTZZ", address);
                map.put("SJHM", data.get("USER_MOBILE") == null ? "" : data.get("USER_MOBILE").toString());
                if(data.get("POPULATION_SEX")==null) {
                    map.put("YHXB","1");//默认为男性
                }else {
                    map.put("YHXB", Integer.valueOf(data.get("POPULATION_SEX").toString()));
                }
                //姓名为空时，取用户名
                String populationName = "";
                if (data.get("USER_NAME") != null) {
                    populationName = data.get("USER_NAME").toString();
                } else if (data.get("POPULATION_NAME") != null) {
                    populationName = data.get("POPULATION_NAME").toString();
                } else if (data.get("USER_ACCOUNT") != null) {
                    populationName = data.get("USER_ACCOUNT").toString();
                } else {//以上都为空，取手机号码作为用户名称
                    populationName = map.get("SJHM").toString();
                }
                map.put("YHMC", populationName);
                String cardType = "";
                if (data.get("CARD_TYPE") != null) {
                    cardType = data.get("CARD_TYPE").toString();
                    if ("111".equals(cardType)) {
                        cardType = "SF";
                    } else if ("114".equals(cardType)) {
                        cardType = "JG";
                    } else if ("414".equals(cardType)) {
                        cardType = "HZ";
                    } else if ("511".equals(cardType)) {
                        cardType = "TWTX";
                    } else if ("516".equals(cardType)) {
                        cardType = "GATX";
                    } else {
                        cardType = "QT";
                    }
                }
                map.put("ZJLX", cardType);
                map.put("ZJHM", data.get("USER_IDCARD") == null ? "" : data.get("USER_IDCARD").toString());
                map.put("DZYX", data.get("USER_EMAIL") == null ? "" : data.get("USER_EMAIL").toString());
            } else {
                map.put("USER_TYPE", 2);
                map.put("DLMM", data.get("USER_MOBILE") == null ? ""
                        : StringUtil.getMd5Encode("mzt" + data.get("USER_MOBILE").toString()));
                map.put("JBRXM",
                        data.get("LEGALPERSON_JBRNAME") == null ? "" : data.get("LEGALPERSON_JBRNAME").toString());
                map.put("JGLX",
                        data.get("LEGALPERSON_ORGTYPE") == null ? "" : data.get("LEGALPERSON_ORGTYPE").toString());
                map.put("DHHM", data.get("LEGALPERSON_PHONE") == null ? "" : data.get("LEGALPERSON_PHONE").toString());
                map.put("FRDB", data.get("LEGALPERSON_NAME") == null ? "" : data.get("LEGALPERSON_NAME").toString());
                map.put("YHZH","mzt_2_"+data.get("LEGALPERSON_JBRSJH").toString()+data.get("LEGALPERSON_IDNUM"));
                map.put("DWDZ",
                        data.get("LEGALPERSON_ZADDRESS") == null ? "" : data.get("LEGALPERSON_ZADDRESS").toString());
                map.put("MOBILE_PHONE",
                        data.get("LEGALPERSON_JBRSJH") == null ? "" : data.get("LEGALPERSON_JBRSJH").toString());
                /*
                 * map.put("YHMC", data.get("USER_NAME") == null ?
                 * data.get("USER_ACCOUNT").toString() :
                 * data.get("USER_NAME").toString());
                 */
                String userName = "";
                if (data.get("USER_NAME") != null) {
                    // 用户姓名或单位
                    userName = data.get("USER_NAME").toString();
                } else if (data.get("USER_ACCOUNT") != null) {
                    // 用户名
                    userName = data.get("USER_ACCOUNT").toString();
                } else if (data.get("LEGALPERSON_NAME") != null) {
                    // 法人
                    userName = data.get("LEGALPERSON_NAME").toString();
                } else if (data.get("LEGALPERSON_JBRNAME") != null) {
                    // 经办人
                    userName = data.get("LEGALPERSON_JBRNAME").toString();
                }else if(data.get("USER_MOBILE") != null){
                    //以上情况都为空，取手机号码作为用户名称
                    userName = data.get("USER_MOBILE").toString();
                }
                map.put("YHMC", userName);
                map.put("JBRYXDZ",
                        data.get("LEGALPERSON_JBREMAIL") == null ? "" : data.get("LEGALPERSON_JBREMAIL").toString());
                String sex = data.get("LEGALPERSON_FRXB") == null ? "" : data.get("LEGALPERSON_FRXB").toString();
                if (StringUtils.isNotEmpty(sex)) {
                    map.put("ORG_LAW_SEX", Integer.valueOf(sex));
                }
                map.put("JBRSFZ",
                        data.get("LEGALPERSON_JBRIDCARD") == null ? "" : data.get("LEGALPERSON_JBRIDCARD").toString());
                map.put("ORG_NAME", data.get("LEGALPERSON_COMPANYNAME") == null ? ""
                        : data.get("LEGALPERSON_COMPANYNAME").toString());
                map.put("ZZJGDM",
                        data.get("LEGALPERSON_IDNUM") == null ? "" : data.get("LEGALPERSON_IDNUM").toString());
                map.put("ORG_LAW_IDCARD",
                        data.get("LEGALPERSON_IDCARD") == null ? "" : data.get("LEGALPERSON_IDCARD").toString());
            }
            // 本地是否存在该用户
            int existence = userExistence(map.get("YHZH").toString());
            if (existence == 0) {
                String userType = data.get("USER_TYPE").toString();
                String userAccount = map.get("YHZH").toString();
                if (userType != null && "1".equals(userType)) {
                    Map<String, Object> userInfo = dao.getByJdbc("T_BSFW_USERINFO",
                            new String[] { "MZT_YHZH", "USER_TYPE" }, new Object[] { userAccount, userType });
                    if (userInfo == null || userInfo.size() == 0) {
                        if (data.get("USER_IDCARD") != null) {
                            userInfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "ZJHM", "USER_TYPE" },
                                    new Object[] { data.get("USER_IDCARD").toString(), userType });
                        }
                    }
                    if (userInfo != null && userInfo.size() > 0) {
                        // 绑定闽政通登录账号
                        userInfo.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(userInfo, "T_BSFW_USERINFO", userInfo.get("USER_ID").toString());
                    } else {
                        // 新增用户数据
                        map.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(map, "T_BSFW_USERINFO", null);
                    }
                } else {
                    Map<String, Object> userInfo = dao.getByJdbc("T_BSFW_USERINFO",
                            new String[] { "MZT_YHZH", "USER_TYPE" }, new Object[] { userAccount, userType });
                    if (userInfo == null || userInfo.size() == 0) {
                        userInfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "ZZJGDM", "USER_TYPE" },
                                new Object[] { data.get("LEGALPERSON_IDNUM").toString(), userType });
                    }
                    if (userInfo != null && userInfo.size() > 0) {
                        // 绑定闽政通登录账号
                        userInfo.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(userInfo, "T_BSFW_USERINFO", userInfo.get("USER_ID").toString());
                    } else {
                        // 新增用户数据
                        map.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(map, "T_BSFW_USERINFO", null);
                    }
                }
            }
        } else {
            map.put("ERRCODE", m.get("errcode") == null ? "" : m.get("errcode").toString());
        }
        Map<String, Object> userinfo = null;
        if (map != null) {
            String yhzh = (String) map.get("YHZH");
            userinfo = dao.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"}, new Object[]{yhzh});
            if (userinfo == null || userinfo.size() == 0) {
                userinfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "MZT_YHZH" },
                        new Object[] { yhzh });
            }
        }
        return userinfo;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 14:49:00
     * @param 
     * @return 
     */
    @Override
    public Map<String, Object> mztWxUserInfor(String resultData) {
        Map<String, Object> m = JsonUtil.parseJSON2Map(resultData);
        String result = (String) m.get("msg");
        JSONObject data = (JSONObject) m.get("mztUser");
        Map<String, Object> map = new HashMap<String, Object>();
        if (result.equals("success")) {
            map.put("REAL_NAME_TYPE", data.getString("real_name_type"));
            map.put("LAST_UPDATE_TIME", data.getString("last_update_time"));
            map.put("IS_REAL_NAME", data.getString("is_real_name"));
            map.put("YHZT", 1);
            map.put("MZT_USER_ID", data.getString("USER_ID"));
            map.put("MZT_USER_TOKEN", data.getString("USER_TOKEN"));
            if ("1".equals(data.getString("USER_TYPE"))) {
                map.put("USER_TYPE", 1);
                map.put("DLMM", data.get("USER_MOBILE") == null ? ""
                        : StringUtil.getMd5Encode("mzt" + data.get("USER_MOBILE").toString()));
                map.put("YHZH", "mzt_1_"+data.get("USER_MOBILE").toString());
                String address = "";
                if (data.get("POPULATION_CADDRESS") != null) {
                    address = data.get("POPULATION_CADDRESS").toString();
                } else if (data.get("POPULATION_JZDZ") != null) {
                    address = data.get("POPULATION_JZDZ").toString();
                } else if (data.get("POPULATION_HJDZ") != null) {
                    address = data.get("POPULATION_HJDZ").toString();
                }
                map.put("ZTZZ", address);
                map.put("SJHM", data.get("USER_MOBILE") == null ? "" : data.get("USER_MOBILE").toString());
                if(data.get("POPULATION_SEX")==null) {
                    map.put("YHXB","1");//默认为男性
                }else {
                    map.put("YHXB", Integer.valueOf(data.get("POPULATION_SEX").toString()));
                }
                //姓名为空时，取用户名
                String populationName = "";
                if (data.get("USER_NAME") != null) {
                    populationName = data.get("USER_NAME").toString();
                } else if (data.get("POPULATION_NAME") != null) {
                    populationName = data.get("POPULATION_NAME").toString();
                } else if (data.get("USER_ACCOUNT") != null) {
                    populationName = data.get("USER_ACCOUNT").toString();
                } else {//以上都为空，取手机号码作为用户名称
                    populationName = map.get("SJHM").toString();
                }
                map.put("YHMC", populationName);
                String cardType = "";
                if (data.get("CARD_TYPE") != null) {
                    cardType = data.get("CARD_TYPE").toString();
                    if ("111".equals(cardType)) {
                        cardType = "SF";
                    } else if ("114".equals(cardType)) {
                        cardType = "JG";
                    } else if ("414".equals(cardType)) {
                        cardType = "HZ";
                    } else if ("511".equals(cardType)) {
                        cardType = "TWTX";
                    } else if ("516".equals(cardType)) {
                        cardType = "GATX";
                    } else {
                        cardType = "QT";
                    }
                }
                map.put("ZJLX", cardType);
                map.put("ZJHM", data.get("USER_IDCARD") == null ? "" : data.get("USER_IDCARD").toString());
                map.put("DZYX", data.get("USER_EMAIL") == null ? "" : data.get("USER_EMAIL").toString());
            } else {
                map.put("USER_TYPE", 2);
                map.put("DLMM", data.get("USER_MOBILE") == null ? ""
                        : StringUtil.getMd5Encode("mzt" + data.get("USER_MOBILE").toString()));
                map.put("JBRXM",
                        data.get("LEGALPERSON_JBRNAME") == null ? "" : data.get("LEGALPERSON_JBRNAME").toString());
                map.put("JGLX",
                        data.get("LEGALPERSON_ORGTYPE") == null ? "" : data.get("LEGALPERSON_ORGTYPE").toString());
                map.put("DHHM", data.get("LEGALPERSON_PHONE") == null ? "" : data.get("LEGALPERSON_PHONE").toString());
                map.put("FRDB", data.get("LEGALPERSON_NAME") == null ? "" : data.get("LEGALPERSON_NAME").toString());
                map.put("YHZH","mzt_2_"+data.get("LEGALPERSON_JBRSJH").toString()+data.get("LEGALPERSON_IDNUM"));
                map.put("DWDZ",
                        data.get("LEGALPERSON_ZADDRESS") == null ? "" : data.get("LEGALPERSON_ZADDRESS").toString());
                map.put("MOBILE_PHONE",
                        data.get("LEGALPERSON_JBRSJH") == null ? "" : data.get("LEGALPERSON_JBRSJH").toString());
                /*
                 * map.put("YHMC", data.get("USER_NAME") == null ?
                 * data.get("USER_ACCOUNT").toString() :
                 * data.get("USER_NAME").toString());
                 */
                String userName = "";
                if (data.get("USER_NAME") != null) {
                    // 用户姓名或单位
                    userName = data.get("USER_NAME").toString();
                } else if (data.get("USER_ACCOUNT") != null) {
                    // 用户名
                    userName = data.get("USER_ACCOUNT").toString();
                } else if (data.get("LEGALPERSON_NAME") != null) {
                    // 法人
                    userName = data.get("LEGALPERSON_NAME").toString();
                } else if (data.get("LEGALPERSON_JBRNAME") != null) {
                    // 经办人
                    userName = data.get("LEGALPERSON_JBRNAME").toString();
                }else if(data.get("USER_MOBILE") != null){
                    //以上情况都为空，取手机号码作为用户名称
                    userName = data.get("USER_MOBILE").toString();
                }
                map.put("YHMC", userName);
                map.put("JBRYXDZ",
                        data.get("LEGALPERSON_JBREMAIL") == null ? "" : data.get("LEGALPERSON_JBREMAIL").toString());
                String sex = data.get("LEGALPERSON_FRXB") == null ? "" : data.get("LEGALPERSON_FRXB").toString();
                if (StringUtils.isNotEmpty(sex)) {
                    map.put("ORG_LAW_SEX", Integer.valueOf(sex));
                }
                map.put("JBRSFZ",
                        data.get("LEGALPERSON_JBRIDCARD") == null ? "" : data.get("LEGALPERSON_JBRIDCARD").toString());
                map.put("ORG_NAME", data.get("LEGALPERSON_COMPANYNAME") == null ? ""
                        : data.get("LEGALPERSON_COMPANYNAME").toString());
                map.put("ZZJGDM",
                        data.get("LEGALPERSON_IDNUM") == null ? "" : data.get("LEGALPERSON_IDNUM").toString());
                map.put("ORG_LAW_IDCARD",
                        data.get("LEGALPERSON_IDCARD") == null ? "" : data.get("LEGALPERSON_IDCARD").toString());
            }
            // 本地是否存在该用户
            int existence = userExistence(map.get("YHZH").toString());
            if (existence == 0) {
                String userType = data.get("USER_TYPE").toString();
                String userAccount = map.get("YHZH").toString();
                if (userType != null && "1".equals(userType)) {
                    Map<String, Object> userInfo = dao.getByJdbc("T_BSFW_USERINFO",
                            new String[] { "MZT_YHZH", "USER_TYPE" }, new Object[] { userAccount, userType });
                    if (userInfo == null || userInfo.size() == 0) {
                        if (data.get("USER_IDCARD") != null) {
                            userInfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "ZJHM", "USER_TYPE" },
                                    new Object[] { data.get("USER_IDCARD").toString(), userType });
                        }
                    }
                    if (userInfo != null && userInfo.size() > 0) {
                        // 绑定闽政通登录账号
                        userInfo.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(userInfo, "T_BSFW_USERINFO", userInfo.get("USER_ID").toString());
                    } else {
                        // 新增用户数据
                        map.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(map, "T_BSFW_USERINFO", null);
                    }
                } else {
                    Map<String, Object> userInfo = dao.getByJdbc("T_BSFW_USERINFO",
                            new String[] { "MZT_YHZH", "USER_TYPE" }, new Object[] { userAccount, userType });
                    if (userInfo == null || userInfo.size() == 0) {
                        userInfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "ZZJGDM", "USER_TYPE" },
                                new Object[] { data.get("LEGALPERSON_IDNUM").toString(), userType });
                    }
                    if (userInfo != null && userInfo.size() > 0) {
                        // 绑定闽政通登录账号
                        userInfo.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(userInfo, "T_BSFW_USERINFO", userInfo.get("USER_ID").toString());
                    } else {
                        // 新增用户数据
                        map.put("MZT_YHZH", userAccount);
                        dao.saveOrUpdate(map, "T_BSFW_USERINFO", null);
                    }
                }
            }
        } else {
            map.put("ERRCODE", m.get("errcode") == null ? "" : m.get("errcode").toString());
        }

        Map<String, Object> userinfo = null;
        if (map != null) {
            String yhzh = (String) map.get("YHZH");
            userinfo = dao.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"}, new Object[]{yhzh});
            if (userinfo == null || userinfo.size() == 0) {
                userinfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "MZT_YHZH" },
                        new Object[] { yhzh });
            }
        }

        return userinfo;
    }
}
