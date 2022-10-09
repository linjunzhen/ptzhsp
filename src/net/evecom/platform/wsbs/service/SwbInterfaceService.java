/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import net.evecom.core.service.BaseService;
import net.evecom.platform.wsbs.model.TzProjectRespones;
import org.apache.commons.logging.Log;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
    
/**
 * 
 * 描述 省网办接口服务业务接口
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月13日 上午9:12:54
 */
@SuppressWarnings("rawtypes")
public interface SwbInterfaceService extends BaseService {
    /**
     * 描述 市县区向省网办报送办件
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendBusItemsToProvince(Map<String, Object> dataAbutment,Map<String, Object> map); 

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 10:54:00
     * @param
     * @return
     */
    public void createBusItemsToProvince(Connection conn, Map<String, Object> swbdataRes,
                                         Map<String, Object> instanceMap, Map<String, Object> dataAbutment,
                                         String type) throws Exception;

    /**
     * 描述 市县区向省网办报送结果附件信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendResultAttrToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 12:07:00
     * @param 
     * @return 
     */
    public void createResultAttrToProvince(Connection conn, Map<String, Object> swbdataRes,
                                           Map<String, Object> instanceMap,
                                           Map<String, Object> dataAbutment,String type) throws Exception;

    /**
     * 描述:市县区向省网办报送评价信息
     *
     * @author Madison You
     * @created 2019/10/23 14:40:00
     * @param
     * @return
     */
    public void sendEvaluationToProvince(Map<String, Object> dataAbutment);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 16:18:00
     * @param 
     * @return 
     */
    public void createEvaluationToProvince(Connection conn, Map<String, Object> swbdataRes,
                                           Map<String, Object> instanceMap,
                                           Map<String, Object> dataAbutment) throws Exception;


        /**
         * 描述 市县区向省网办报送办件计时暂停信息
         *
         * @author Derek Zhang
         * @created 2015年10月21日 下午1:28:08
         * @param dataAbutment
         */
    public void sendTimeStopToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:29:00
     * @param 
     * @return 
     */
    public void createTimeStopToProvince(Connection conn, Map<String, Object> swbdataRes,
                                          Map<String, Object> instanceMap,
                                          Map<String, Object> dataAbutment,String type) throws Exception;

    /**
     * 描述 市县区向省网办报送办件计时恢复信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendTimeStartToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:34:00
     * @param 
     * @return 
     */
    public void createTimeStartToProvince(Connection conn,
                                          Map<String, Object> swbdataRes, Map<String, Object> instanceMap,
                                          Map<String, Object> dataAbutment,String type) throws Exception;

    /**
     * 描述 市县区向省网办报送办件过程信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendBusProcessToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:21:00
     * @param 
     * @return 
     */
    public void createBusProcessToProvince(Connection conn, Map<String, Object> swbdataRes,
                                           Map<String, Object> instanceMap,
                                           Map<String, Object> dataAbutment,String type) throws Exception;

    /**
     * 描述 市县区向省网办报送省市联动办件
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendSSLDBusItemsToToProvince(Map<String, Object> dataAbutment);

    /**
     * 描述 市县区向省网办报送结果信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendResultInfoToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:41:00
     * @param 
     * @return 
     */
    public void createResultInfoToProvince(Connection conn, Map<String, Object> swbdataRes,
                                           Map<String, Object> instanceMap,
                                           Map<String, Object> dataAbutment,String type) throws Exception;

    /**
     * 描述 市县区向省网办报送缴费信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendJFInfoToProvince(Map<String, Object> dataAbutment);

    /**
     * 描述 市县区向省网办报送通知缴费信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendTZJFInfoToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述 市县区向省网办报送补件信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendBJInfoToProvince(Map<String, Object> dataAbutment,Map<String, Object> map);

    /**
     * 描述 市县区向省网办报送评议信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendPYXXToProvince(Map<String, Object> dataAbutment);

    /**
     * 描述 向省网办报送的数据（办件、过程、结果数据）
     * 
     * @author Derek Zhang
     * @created 2015年12月04日 下午4:28:08
     * @param dataAbutment
     */
    public void sendDataToProvince(Map<String, Object> dataAbutment);

    /**
     * 
     * 描述 获取投资项目信息查询结果
     * 
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:56:13
     * @param dataAbutment
     * @param projectCode
     * @return
     */
    public TzProjectRespones getTZXMXXData(String projectCode);

    /**
     * 
     * 描述 获取投资项目信息查询结果
     * 
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:56:13
     * @param dataAbutment
     * @param projectCode
     * @return
     */
    public List<Map<String, Object>> getTZXMPLBLQKData(String projectCode);

    /**
     * 描述 发布公示信息到中国平潭网公示栏目
     * 
     * @author Derek Zhang
     * @created 2015年12月2日 上午10:15:19
     * @param dataAbutment
     */
    public void saveTZXMPLBLQKData(Log log, Map<String, Object> dataAbutment);

    /**
     * 描述:判断是否是工程建设项目
     *
     * @author Madison You
     * @created 2021/3/9 9:27:00
     * @param
     * @return
     */
    public boolean judgeProject(String tableName);

    /**
     * 描述:设置获取业务数据得sql
     *
     * @author Madison You
     * @created 2021/3/9 9:33:00
     * @param
     * @return
     */
    public String setSQL(String tableName, String tableKey);

    /**
     * 描述:用户sql
     *
     * @author Madison You
     * @created 2021/3/9 16:24:00
     * @param
     * @return
     */
    public String setUserSql();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 10:13:00
     * @param
     * @return
     */
    public StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/10 9:39:00
     * @param
     * @return
     */
    public String findSexByCardNo(String cardNo);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/10 10:34:00
     * @param 
     * @return 
     */
    public void setFormInfoData(Map<String, Object> formInfos, String tablename, String keyname,
                                String keyValue);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/10 11:08:00
     * @param
     * @return
     */
    public String setTableNameSql();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/11 15:44:00
     * @param 
     * @return 
     */
    public String queryNextNodeInfoSql(String nodeFlag);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/11 15:45:00
     * @param 
     * @return 
     */
    public String getNodeStartTime(Map<String, Object> sqfsMap, Map<String, Object> serviceItemMap);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/11 15:49:00
     * @param 
     * @return 
     */
    public String getNodeOpinionType(Map<String, Object> serviceItemMap);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/11 16:49:00
     * @param 
     * @return 
     */
    public String refushNodeName(String nodeName);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/12 11:49:00
     * @param 
     * @return 
     */
    public String getTimeStopServiceSql();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 10:13:00
     * @param
     * @return
     */
    public String getResultServiceSql();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/16 14:57:00
     * @param
     * @return
     */
    public String getJgxxSql();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/16 14:58:00
     * @param
     * @return
     */
    public String getAttrSql();
    /**
     * 
     * 描述   新增归档数据处理
     * @author Yanisin Shi
     * @return
     */
    String setTableNameSqlNew();
     /**
      * 
      * 描述 计时暂停 新增归档数据处理
      * @author Yanisin Shi
      * @return
      */
    String getTimeStopServiceSqlNew();
    /**
     * 
     * 描述   新增归档数据处理
     * @author Yanisin Shi
     * @return
     */
    String getResultServiceSqlNew(String str,String exeid);
    /**
     * 
     * 描述 新增归档数据处理
     * @author Yanisin Shi
     * @param exeid
     * @return
     */
    String getJgxxSqlNew(String str);

    /**
     * 根据网上过来的数据获取用户信息
     * @param serviceItemMap
     * @return
     */
    Map<String,Object> getUserInfoMap(Map<String, Object> serviceItemMap);
    
    /**
     * 根据网上过来的数据生成用户信息
     * @param serviceItemMap
     * @return
     */
    Map<String,Object> generateUserInfo(Map<String, Object> serviceItemMap);
/**
 * 
 * 描述  发送工程建设数据到省网办定时器 
 * @author Yanisin Shi
 */
    public void sendGcjsDataToProvince();
    /**
     * 
     * 描述  根据时间段获取附件列表
     * @author Yanisin Shi
     * @return
     */
    String getAttrSqlByTime();
    /**
     * 
     * 描述  处理附件内容定时器 附件内容推送至前置库
     * @author Yanisin Shi
     */
    public void sendAttrContentToSwb();
}
