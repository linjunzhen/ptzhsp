/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.call.dao.CallLogDao;
import net.evecom.platform.call.service.CallLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 描述 第三方接口调用日志
 * @author Danto Huang
 * @created 2018年7月3日 下午3:58:56
 */
@Service("callLogService")
public class CallLogServiceImpl extends BaseServiceImpl implements CallLogService {

    /**
     * log
     */
    private static Log log= LogFactory.getLog(CallLogServiceImpl.class);

    /**
     * 引入dao
     */
    @Resource
    private CallLogDao dao;

    /**
     *
     * 描述
     * @author Danto Huang
     * @created 2018年7月3日 下午4:00:03
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 保存第三方接口调用日志记录
     * @param LOG_START_TIME 请求开始时间戳
     * @param LOG_REQUEST_PARAM 请求参数
     * @param LOG_RESPONSE_STATUS 接口调用状态 0失败,1成功
     * @param LOG_RESPONSE_RESULT 返回结果
     * @param LOG_TYPE 接口类型:详见字典(interfaceCall)
     */
    public void save(long LOG_START_TIME,String LOG_REQUEST_PARAM,int LOG_RESPONSE_STATUS
            ,String LOG_RESPONSE_RESULT,int LOG_TYPE){
//        runnable(new Runnable() {
//            @Override
//            public void run() {
                if(LOG_START_TIME>0){
                    Map<String,Object> map = new HashMap<>();
                    map.put("LOG_START_TIME",DateTimeUtil.timestampToStrLocalDateTime(LOG_START_TIME));
                    long LOG_END_TIME = DateTimeUtil.getTimestamp();//请求结束时间戳
                    map.put("LOG_END_TIME",DateTimeUtil.timestampToStrLocalDateTime(LOG_END_TIME));
                    map.put("LOG_TOTAL_TIME",LOG_END_TIME - LOG_START_TIME);
                    map.put("LOG_REQUEST_PARAM",LOG_REQUEST_PARAM);
                    map.put("LOG_RESPONSE_STATUS",LOG_RESPONSE_STATUS);
                    map.put("LOG_RESPONSE_RESULT", StringUtil.isNotEmpty(LOG_RESPONSE_RESULT)
                            &&LOG_RESPONSE_RESULT.length()>1990?"":LOG_RESPONSE_RESULT);
                    map.put("LOG_TYPE",LOG_TYPE);
                    saveOrUpdate(map, "T_INTERFACE_CALL_LOG", null);
                }
//            }
//        });
    }

    /**
     * 保存第三方接口调用日志记录
     * @param LOG_START_TIME 请求开始时间戳
     * @param LOG_REQUEST_PARAM 请求参数
     * @param LOG_RESPONSE_STATUS 接口调用状态 0失败,1成功
     * @param LOG_RESPONSE_RESULT 返回结果
     * @param LOG_TYPE 接口类型:详见字典(interfaceCall)
     * @param LOG_RETURN_ID
     * @param LOG_BUSINESS_NAME
     * @param LOG_BUSINESS_CODE
     * @param LOG_USER_NAME
     * @param LOG_DEPT_NAME
     */
    public void save(long LOG_START_TIME,String LOG_REQUEST_PARAM,int LOG_RESPONSE_STATUS
            ,String LOG_RESPONSE_RESULT,int LOG_TYPE,String LOG_RETURN_ID,String LOG_BUSINESS_NAME
            ,String LOG_BUSINESS_CODE,String LOG_USER_NAME,String LOG_DEPT_NAME){
        if(LOG_START_TIME>0){
            Map<String,Object> map = new HashMap<>();
            map.put("LOG_START_TIME",DateTimeUtil.timestampToStrLocalDateTime(LOG_START_TIME));
            long LOG_END_TIME = DateTimeUtil.getTimestamp();//请求结束时间戳
            map.put("LOG_END_TIME",DateTimeUtil.timestampToStrLocalDateTime(LOG_END_TIME));
            map.put("LOG_TOTAL_TIME",LOG_END_TIME - LOG_START_TIME);
            map.put("LOG_REQUEST_PARAM",LOG_REQUEST_PARAM);
            map.put("LOG_RESPONSE_STATUS",LOG_RESPONSE_STATUS);
            map.put("LOG_RESPONSE_RESULT", StringUtil.isNotEmpty(LOG_RESPONSE_RESULT)
                    &&LOG_RESPONSE_RESULT.length()>1990?"":LOG_RESPONSE_RESULT);
            map.put("LOG_TYPE",LOG_TYPE);
            map.put("LOG_RETURN_ID",LOG_RETURN_ID);
            map.put("LOG_BUSINESS_NAME",LOG_BUSINESS_NAME);
            map.put("LOG_BUSINESS_CODE",LOG_BUSINESS_CODE);
            map.put("LOG_USER_NAME",LOG_USER_NAME);
            map.put("LOG_DEPT_NAME",LOG_DEPT_NAME);
            saveOrUpdate(map, "T_INTERFACE_CALL_LOG", null);
        }
    }

    /**
     * 保存第三方接口调用日志记录
     * @param LOG_START_TIME 请求开始时间戳
     * @param LOG_REQUEST_PARAM 请求参数
     * @param LOG_RESPONSE_STATUS 接口调用状态 0失败,1成功
     * @param LOG_RESPONSE_RESULT 返回结果
     * @param LOG_TYPE 接口类型:详见字典(interfaceCall)
     * @param EXE_ID
     */
    public void save(long LOG_START_TIME,String LOG_REQUEST_PARAM,int LOG_RESPONSE_STATUS
            ,String LOG_RESPONSE_RESULT,int LOG_TYPE,String EXE_ID){
        if(LOG_START_TIME>0){
            Map<String,Object> exeMap = getItemInfo(EXE_ID);
            if(StringUtil.isNotEmpty(exeMap)){
                Map<String,Object> map = new HashMap<>();
                map.put("LOG_START_TIME",DateTimeUtil.timestampToStrLocalDateTime(LOG_START_TIME));
                long LOG_END_TIME = DateTimeUtil.getTimestamp();//请求结束时间戳
                map.put("LOG_END_TIME",DateTimeUtil.timestampToStrLocalDateTime(LOG_END_TIME));
                map.put("LOG_TOTAL_TIME",LOG_END_TIME - LOG_START_TIME);
                map.put("LOG_REQUEST_PARAM",LOG_REQUEST_PARAM);
                map.put("LOG_RESPONSE_STATUS",LOG_RESPONSE_STATUS);
                map.put("LOG_RESPONSE_RESULT", StringUtil.isNotEmpty(LOG_RESPONSE_RESULT)
                        &&LOG_RESPONSE_RESULT.length()>1990?"":LOG_RESPONSE_RESULT);
                map.put("LOG_TYPE",LOG_TYPE);
                map.put("LOG_RETURN_ID",exeMap.get("LOGID"));
                map.put("LOG_BUSINESS_NAME",exeMap.get("ITEM_NAME"));
                map.put("LOG_BUSINESS_CODE",exeMap.get("ITEM_CODE"));
                map.put("LOG_USER_NAME",exeMap.get("SQRMC"));
                map.put("LOG_DEPT_NAME",exeMap.get("DEPART_NAME"));
                saveOrUpdate(map, "T_INTERFACE_CALL_LOG", null);
            }
        }
    }

    /**
     * 根据申报号获取事项信息
     * @param EXE_ID
     * @return
     */
    public Map<String,Object> getItemInfo(String EXE_ID){
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("SELECT T1.ITEM_NAME,T1.ITEM_CODE,T1.LOGID,T1.CREATOR_NAME,T1.SQRMC")
                .add(",NVL(T2.IMPL_DEPART,'') AS IMPL_DEPART,NVL(T2.ZBCS,'') AS ZBCS")
                .add(",NVL(t2.IMPL_DEPART,'')||NVL(t2.zbcs,'') AS DEPART_NAME")
                .add("FROM JBPM6_EXECUTION T1 LEFT JOIN T_WSBS_SERVICEITEM T2 ON T1.ITEM_CODE = T2.ITEM_CODE")
                .add("WHERE T1.EXE_ID = ?");
        List<Map<String,Object>> list = this.dao.findBySql(stringJoiner.toString(),new Object[]{EXE_ID},null);
        return StringUtil.isNotEmpty(list)?list.get(0):null;
    }
}
