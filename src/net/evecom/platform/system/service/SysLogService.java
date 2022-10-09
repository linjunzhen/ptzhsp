/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.wsbs.model.TzProjectRespones;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:26:49
 */
public interface SysLogService extends BaseService {
    /**
     * 增加操作
     */
    public static final int OPERATE_TYPE_ADD = 1;
    /**
     * 编辑操作
     */
    public static final int OPERATE_TYPE_EDIT = 2;
    /**
     * 删除操作
     */
    public static final int OPERATE_TYPE_DEL = 3;
    /**
     * 登录操作
     */
    public static final int OPERATE_TYPE_LOGIN = 4;
    /**
     * 上传操作
     */
    public static final int OPERATE_TYPE_UPLOAD = 5;
    /**
     * 保存操作级别的日志
     * 描述
     * @author Flex Hu
     * @created 2014年9月14日 上午8:37:24
     * @param logContent:日志内容
     * @param operateType:操作类型
     */
    public void saveLog(String logContent,int operateType);
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
    public void saveLogByMap(String tableName,String colNames ,
            String colValues,Map<String,Object> insertMap,String busIndex);
    /**
     * 描述：联合查询获取目的表变更表信息
     * @author Water Guo
     * @created 2017-11-10 下午4:29:41
     * @param indexColName 索引列
     * @param busTableName 联合查询表
     * @param colValues 查询的列值
     */
    public List<Map<String,Object>> getBusTableLogs(String indexColName,String busTableName,String[] colNames,
          Object[] colValues );
    /**
     * 
     * 描述：查询表更新信息
     * @author Water Guo
     * @created 2017-11-15 下午5:16:34
     * @param indexColName
     * @param busTableName
     * @return
     */
    public List<Map<String,Object>> getChBusTableLogs(String indexColVal,String busTableName);
    /**
     * 
     * 描述：日志的字典注释
     * @author Water Guo
     * @created 2017-11-17 上午11:54:45
     * @param list
     * @return
     */
    public List<Map<String,Object>> logToStand(List<Map<String,Object>> list);
    /**
     * 描述 保存操作级别的数据,面向网站公众
     * @author Flex Hu
     * @created 2015年12月2日 上午9:45:08
     * @param logContent
     * @param operateType
     */
    public void saveLogForMember(String logContent,int operateType);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 描述 保存用户中心用户操作级别日志
     * @author Flex Hu
     * @created 2014年11月17日 下午3:59:50
     * @param logContent
     * @param operateType
     * @param personnel
     */
    public void saveLog(String logContent,int operateType,Map<String,Object> personnel);
    /**
     * 描述 获取用户登录的最后时间
     * @author Flex Hu
     * @created 2014年11月17日 下午4:06:49
     * @param userId
     * @return
     */
    public String getPersonnelLastLoginTime(String userId);
    /**
     * 
     * 描述 获取上次登录信息,面向网站公众
     * @author Flex Hu
     * @created 2015年12月2日 上午9:55:33
     * @param userInfo
     * @return
     */
    public Map<String,Object> getLastLoginInfoForMember(Map<String,Object> userInfo);
    /**
     * 报错APP用户登录数据
     * 描述
     * @author Faker Li
     * @created 2016年4月27日 下午4:51:09
     * @param string
     * @param operateTypeLogin
     */
     public void saveLogForApp(String logContent, int operateType,Map<String,Object> personnel);
     /**
      * 单点登录省网获取AccessToken
      * 描述
      * @author Water Guo
      * @created 2016年12月07日 下午4:51:09
      */
    public String getAccessToken();
    /**
     * 转跳至省网登入页
     * 描述
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param accessToken
     * @param basepath
     */
    public String getSwbLogin(String accessToken, String basepath);
    /**
     * 转跳至省网注册页
     * 描述
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param accessToken
     * @param userType
     * @param basepath
     */
    public String getSwbRegister(String accessToken, String userType, String basepath);
    /**
     * 用户数据处理
     * 描述
     * @author Water Guo
     * @created 2016年12月07日 下午4:51:09
     * @param accessToken
     * @param userToken
     */
    public Map<String, Object> swbUserInfor(String accessToken, String userToken);
    /**
     * 
     * 描述：
     * @author Water Guo
     * @created 2017-11-13 下午8:57:40
     * @param uploadUserId
     * @return
     */
    public List<Map<String, Object>> useridExistence(String uploadUserId);
    /**
     * 获取用户信息
     * @param resultData
     * @return
     */
    public Map<String, Object> mztUserInfor(String resultData);
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 14:49:00
     * @param
     * @return
     */
    public Map<String, Object> mztWxUserInfor(String resultData);
}
