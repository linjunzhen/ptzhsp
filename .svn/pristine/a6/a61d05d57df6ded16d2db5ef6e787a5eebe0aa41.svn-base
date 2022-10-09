/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.util.Sm2UtilForSJJXXZX;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.ems.util.BASE64Encoder;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.PushDataToSJJXXZXService;
import net.evecom.platform.thread.PushDateToSJJXXZXRunnable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述  推送省经济中心接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@Service("pushDataToSJJXXZXService")
public class PushSJJXXZXServiceImpl extends BaseServiceImpl implements PushDataToSJJXXZXService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(PushSJJXXZXServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;

    /**
     * 
     * 描述 获取dao
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:52:27
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;
    
    /**
     * 描述:推送进驻事项资源报送
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    @Override
    public void pollingWithAddProcessItemSourceInfo() {
        //获取字典相关信息
        String orgCode = dictionaryService.getDicNames("sjjxxzxConfig","orgCode");
        String orgName = dictionaryService.getDicNames("sjjxxzxConfig","orgName");
        String urlName = dictionaryService.getDicNames("sjjxxzxConfig","addProcessItemSourceInfoUrl");
        //此处查询现有表中记录
        String sql =" select a.IMPL_DEPART_ID,a.IMPL_DEPART,a.ITEM_CODE,a.ITEM_NAME,a.SWB_ITEM_CODE,a.SXXZ,a.APPOINTMENT_SUPPORT,b.yw_id,b.send_json,b.foreign_key, ";
        sql +=" rownum as rnum from (select * from T_WSBS_SERVICEITEM where swb_item_code is not null) a ";
        sql +=" left join T_BSFW_SJJXXZX_RECORD b on a.item_code = b.foreign_key ";
        sql +=" where 1=1 ";
        sql +=" and a.swb_item_code is not null ";
        sql +=" or b.yw_id is null ";
        List<Map<String, Object>> queryList = this.dao.findBySql(sql,null, null);
        for (int j = 0; j < queryList.size(); j++) {
            JSONObject sendJson = new JSONObject();
            sendJson.put("orgCode", orgCode);
            sendJson.put("orgName", orgName);
            sendJson.put("proDepartId", StringUtil.getValue(queryList.get(j), "IMPL_DEPART_ID"));
            sendJson.put("proDepart", StringUtil.getValue(queryList.get(j), "IMPL_DEPART"));
            sendJson.put("processItem", StringUtil.getValue(queryList.get(j), "ITEM_NAME"));
            sendJson.put("processItemCode", StringUtil.getValue(queryList.get(j), "SWB_ITEM_CODE"));
            sendJson.put("processItemType", StringUtil.getValue(queryList.get(j), "SXXZ"));
            int isOnlineAppointment = 0;
            if(queryList.get(j).get("appointment_support")!=null){
                isOnlineAppointment =  Integer.valueOf(queryList.get(j).get("appointment_support").toString());
            };
            sendJson.put("isOnlineAppointment", isOnlineAppointment);
            sendJson.put("isEnable", "1");
            sendJson.put("isHaveQueue", "1");
            Map<String, Object> saveMap = new HashMap<String, Object>();
            saveMap.put("SEND_JSON", sendJson.toString());
            saveMap.put("UPLOAD_STATUS", "0");
            saveMap.put("UPDATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            saveMap.put("INTERFACE_URL", urlName);
            saveMap.put("INTERFACE_NAME", "进驻事项资源报送");
            saveMap.put("FOREIGN_KEY", StringUtil.getValue(queryList.get(j), "ITEM_CODE"));
            //发送记录表中无记录
            if(queryList.get(j).get("foreign_key") == null){
                String result = this.dao.saveOrUpdate(saveMap, "T_BSFW_SJJXXZX_RECORD", null);
            }else{
                //判断发送记录表中数据与之前是否有做过变动
                if(StringUtil.getValue(queryList.get(j), "SEND_JSON").equals(sendJson.toString())) {
                    continue;
                }else{
                    String updateSql = " update T_BSFW_SJJXXZX_RECORD set send_json = ?,update_time = ?,upload_status = ? where foreign_key = ? ";
                    this.dao.executeSql(updateSql, new Object[] {saveMap.get("SEND_JSON"), saveMap.get("UPDATE_TIME"),saveMap.get("UPLOAD_STATUS"),saveMap.get("FOREIGN_KEY")});
                }
            }
        }
    }
    
    /**
     * 描述:行政服务中心窗口资源报送
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    @Override
    public void pollingWithAddCounterSourceInfo() {
        //获取字典相关信息
        String orgCode = dictionaryService.getDicNames("sjjxxzxConfig","orgCode");
        String orgName = dictionaryService.getDicNames("sjjxxzxConfig","orgName");
        String urlName = dictionaryService.getDicNames("sjjxxzxConfig","addCounterSourceInfoUrl");
        //此处查询现有表中记录
        String sql = " select rownum r,e.* from ( ";
              sql += " select * from T_CKBS_WIN_SCREEN a left join T_BSFW_SJJXXZX_RECORD b on a.RECORD_ID = b.FOREIGN_KEY ";
              sql += " where 1=1  ";
              sql += " and b.upload_status not in ('1') ";
              sql += " or b.yw_id is null ";
        sql +=" ) e where rownum <= 200 ";
        List<Map<String, Object>> queryList = this.dao.findBySql(sql,null, null);
        for (int j = 0; j < queryList.size(); j++) {
            JSONObject sendJson = new JSONObject();
            sendJson.put("orgCode", orgCode);
            sendJson.put("orgName", orgName);
            sendJson.put("counterId", StringUtil.getValue(queryList.get(j), "WIN_NO"));
            sendJson.put("counterName", StringUtil.getValue(queryList.get(j), "DEPARTINFO"));
            sendJson.put("isEnable", StringUtil.getValue(queryList.get(j), "USE_STATUS"));
            Map<String, Object> saveMap = new HashMap<String, Object>();
            saveMap.put("SEND_JSON", sendJson.toString());
            saveMap.put("UPLOAD_STATUS", "0");
            saveMap.put("UPDATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            saveMap.put("INTERFACE_URL", urlName);
            saveMap.put("INTERFACE_NAME", "行政服务中心窗口资源报送");
            saveMap.put("FOREIGN_KEY", StringUtil.getValue(queryList.get(j), "WIN_NO"));
            //发送记录表中无记录
            if(queryList.get(j).get("foreign_key") == null){
                String result = this.dao.saveOrUpdate(saveMap, "T_BSFW_SJJXXZX_RECORD", null);
            }else{
                //判断发送记录表中数据与之前是否有做过变动
                if(StringUtil.getValue(queryList.get(j), "SEND_JSON").equals(sendJson.toString())) {
                    continue;
                }else{
                    String updateSql = " update T_BSFW_SJJXXZX_RECORD set send_json = ?,update_time = ?,upload_status = ? where foreign_key = ? ";
                    this.dao.executeSql(updateSql, new Object[] {saveMap.get("SEND_JSON"), saveMap.get("UPDATE_TIME"),saveMap.get("UPLOAD_STATUS"),saveMap.get("FOREIGN_KEY")});
                }
            }
        }
    }
    
    /**
     * 描述 轮询数据进行省经济信息中心推送
     * 
     * @author Jason Lin
     * @created 2015年11月24日 下午3:51:39
     */
    @Override
    public void sendDataToSJJXXZX() {
        String appId = dictionaryService.getDicNames("sjjxxzxConfig","appId");
        String appSecret = dictionaryService.getDicNames("sjjxxzxConfig","appSecret");
        String sql = " select rownum r,e.* from ( ";
        sql +=" select * from T_BSFW_SJJXXZX_RECORD where upload_status not in ('1') and interface_name in ('进驻事项资源报送','排队叫号机资源报送') ";
        sql +=" ) e where rownum <= 200 ";
        List<Map<String, Object>> queryList = this.dao.findBySql(sql,null, null);
        for (int i = 0; i < queryList.size(); i++) {
            String url = queryList.get(i).get("INTERFACE_URL").toString();
            Map<String, Object> sendParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            sendParam.put("appId", appId);
            String encBase64Data = "";
            try {
                encBase64Data = new BASE64Encoder().encode(queryList.get(i).get("SEND_JSON").toString().getBytes("utf-8"));
                String encHexData = Sm2UtilForSJJXXZX.encrypt(encBase64Data,appSecret);
                sendParam.put("data", encHexData);
                String result = HttpSendUtil.sendPostParamsH(url, null, sendParam);
                if (StringUtils.isNotEmpty(result)) {
                    resultMap = JSON.parseObject(result, Map.class);
                    String updateSql = " update T_BSFW_SJJXXZX_RECORD set upload_status = ?,upload_msg_json=? where yw_id = ? ";
                    if("上报成功".equals(resultMap.get("msg"))){
                        this.dao.executeSql(updateSql, new Object[]{"1",result,queryList.get(i).get("YW_ID")});
                    }else{
                        this.dao.executeSql(updateSql, new Object[]{"2",result,queryList.get(i).get("YW_ID")});
                    }
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    
    
    /**
     * 描述 实时进行省经济信息中心推送
     * 
     * @author Jason Lin
     * @created 2015年11月24日 下午3:51:39
     */
    @Override
    public void sendDataToSJJXXZXTrueTime() {
        String appId = dictionaryService.getDicNames("sjjxxzxConfig","appId");
        String appSecret = dictionaryService.getDicNames("sjjxxzxConfig","appSecret");
        String sql = " select rownum r,e.* from ( ";
        sql +=" select * from T_BSFW_SJJXXZX_RECORD where upload_status in ('0') and interface_name not in ('进驻事项资源报送','排队叫号机资源报送') ";
        sql +=" ) e where rownum <= 500 ";
        List<Map<String, Object>> queryList = this.dao.findBySql(sql,null, null);
        for (int i = 0; i < queryList.size(); i++) {
            String url = queryList.get(i).get("INTERFACE_URL").toString();
            Map<String, Object> sendParam = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            sendParam.put("appId", appId);
            String encBase64Data = "";
            try {
                encBase64Data = new BASE64Encoder().encode(queryList.get(i).get("SEND_JSON").toString().getBytes("utf-8"));
                String encHexData = Sm2UtilForSJJXXZX.encrypt(encBase64Data,appSecret);
                sendParam.put("data", encHexData);
                String result = HttpSendUtil.sendPostParamsH(url, null, sendParam);
                if (StringUtils.isNotEmpty(result)) {
                    resultMap = JSON.parseObject(result, Map.class);
                    String updateSql = " update T_BSFW_SJJXXZX_RECORD set upload_status = ?,upload_msg_json=? where yw_id = ? ";
                    if("上报成功".equals(resultMap.get("msg"))){
                        this.dao.executeSql(updateSql, new Object[]{"1",result,queryList.get(i).get("YW_ID")});
                    }else{
                        this.dao.executeSql(updateSql, new Object[]{"2",result,queryList.get(i).get("YW_ID")});
                    }
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 描述:推送数据至省经济中心
     *
     * @author Madison You
     * @created 2019/12/2 10:30:00
     * @param
     * @return
     */
    public void pushDateToSJJXXZX(Map<String,Object> variables,String operationType) {
        JSONObject sendParamsJson = new JSONObject();
        String orgCode = dictionaryService.getDicNames("sjjxxzxConfig","orgCode");
        String orgName = dictionaryService.getDicNames("sjjxxzxConfig","orgName");
        Map<String,Object> httpParams = new HashMap<String, Object>();
        sendParamsJson.put("orgCode", orgCode);
        sendParamsJson.put("orgName", orgName);
        if("addQueueTaskInfo".equals(operationType)){
            //取号信息报送
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addQueueTaskInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addQueueTaskInfoName");
            sendParamsJson.put("personName", variables.get("LINE_NAME"));
            sendParamsJson.put("cardType", variables.get("LINE_ZJLX"));
            sendParamsJson.put("personIdCard", variables.get("LINE_CARDNO_TWO"));
            sendParamsJson.put("taskNo", variables.get("LINE_NO"));
            sendParamsJson.put("taskTime", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            sendParamsJson.put("isOrder", 0);
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("RECORD_ID"));
        } else if("addQueueCallInfo".equals(operationType)){
            //叫号信息报送
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addQueueCallInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addQueueCallInfoName");
            sendParamsJson.put("callTime", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            sendParamsJson.put("callNo", variables.get("LINE_NO"));
            sendParamsJson.put("isPass", 0);
            sendParamsJson.put("counterId", variables.get("WIN_NO"));
            sendParamsJson.put("counterName", variables.get("WIN_NO"));
            sendParamsJson.put("processItem", "无事项名称");
            sendParamsJson.put("processItemCode", "无事项编码");
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("RECORD_ID"));
        } else if("addQueueOrderInfo".equals(operationType)){
            //预约信息报送
            String sql = " select * from T_CKBS_APPOINTMENT_APPLY a join T_CKBS_QUEUERECORD b on a.NUM_ID = b.RECORD_ID "
                    + " where a.record_id = ? ";
            Map<String, Object> queryMap = this.dao.getByJdbc(sql, new Object[]{variables.get("RECORD_ID").toString()});
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addQueueOrderInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addQueueOrderInfoName");
            sendParamsJson.put("personName", variables.get("NAME"));
            sendParamsJson.put("cardType", "SF");
            sendParamsJson.put("personIdCard", variables.get("LINE_CARDNO_TWO"));
            sendParamsJson.put("orderNo", queryMap.get("LINE_NO"));
            sendParamsJson.put("orderTime", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("RECORD_ID"));
        } else if("addQueueOrderInfoTwo".equals(operationType)){
            //预约信息报送
            String sql = " select * from T_CKBS_APPOINTMENT_APPLY a join T_CKBS_QUEUERECORD b on a.NUM_ID = b.RECORD_ID "
                    + " where b.LINE_NO = ? ";
            Map<String, Object> queryMap = this.dao.getByJdbc(sql, new Object[]{variables.get("LINE_NO").toString()});
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addQueueOrderInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addQueueOrderInfoName");
            sendParamsJson.put("personName", variables.get("LINE_NAME"));
            sendParamsJson.put("cardType", "SF");
            sendParamsJson.put("personIdCard", variables.get("LINE_CARDNO_TWO"));
            sendParamsJson.put("orderNo", queryMap.get("DATE_LINE_NO"));
            sendParamsJson.put("orderTime", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", queryMap.get("RECORD_ID"));
        } else if("addCounterNowInfo".equals(operationType)){
            //窗口实时状态报送
            String sql = " select * from T_CKBS_WIN_SCREEN a join T_CKBS_WIN_USER b on a.win_no = b.win_no "
                    + " where b.record_id = ? ";
            Map<String, Object> queryMap = this.dao.getByJdbc(sql, new Object[]{variables.get("RECORD_ID")});
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addCounterNowInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addCounterNowInfoName");
            sendParamsJson.put("counterId", queryMap.get("WIN_NO"));
            sendParamsJson.put("counterName", queryMap.get("DEPARTINFO"));
            sendParamsJson.put("counterStatus", variables.get("COUNTERSTATUS"));
            sendParamsJson.put("reportTime", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("RECORD_ID"));
        } else if("addCounterNowInfoStop".equals(operationType)){
            //窗口实时状态报送
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addCounterNowInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addCounterNowInfoName");
            sendParamsJson.put("counterId", variables.get("WIN_NO"));
            sendParamsJson.put("counterName", variables.get("DEPARTINFO"));
            sendParamsJson.put("counterStatus", variables.get("COUNTERSTATUS"));
            sendParamsJson.put("reportTime", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("WIN_NO"));
        } else if("addQueueAssignInfo".equals(operationType)){
            //放号信息报送
            String sql = " select * from T_MSJW_SYSTEM_DEPARTMENT a "
                    + " where a.depart_id = ? ";
            Map<String, Object> queryMap = this.dao.getByJdbc(sql, new Object[]{variables.get("depart_id")});
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addQueueAssignInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addQueueAssignInfoName");
            sendParamsJson.put("proDepartId", queryMap.get("DEPART_ID"));
            sendParamsJson.put("proDepart", queryMap.get("DEPART_NAME"));
            sendParamsJson.put("processItem", "无");
            sendParamsJson.put("processItemCode", "无");
            sendParamsJson.put("assignNum", "1");
            sendParamsJson.put("assignTime", variables.get("CREATE_TIME"));
            sendParamsJson.put("isFull", "0");
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("CARD"));
        }  else if("addCounterSourceInfo".equals(operationType)){
            //行政服务中心窗口资源报送
            String sql = "";
            String querySql = "";
            if("2".equals(variables.get("acType")) || "3".equals(variables.get("acType"))){
                sql = " select * from T_CKBS_WIN_SCREEN a "
                        + " where a.RECORD_ID = ? ";
                querySql = variables.get("RECORD_ID").toString();
            }else{
                sql = " select * from T_CKBS_WIN_SCREEN a "
                        + " where a.SCREEN_NO = ? ";
                querySql = variables.get("SCREEN_NO").toString();
            }
            Map<String, Object> queryMap = this.dao.getByJdbc(sql, new Object[]{querySql});
            String sendUrl = dictionaryService.getDicNames("sjjxxzxConfig","addCounterSourceInfoUrl");
            String sendUrlName = dictionaryService.getDicNames("sjjxxzxConfig","addCounterSourceInfoName");
            sendParamsJson.put("counterId", queryMap.get("WIN_NO"));
            sendParamsJson.put("counterName", queryMap.get("DEPARTINFO"));
            sendParamsJson.put("processItem", "窗口绑定业务，无唯一归属部门");
            sendParamsJson.put("processItemCode", "窗口绑定业务，无唯一归属部门");
            sendParamsJson.put("isEnable", queryMap.get("use_status"));
            sendParamsJson.put("acType", variables.get("acType"));
            if("3".equals(sendParamsJson.get("acType"))){
                //如果是删除操作
                sendParamsJson.put("isEnable", "0");
            }else if("1".equals(sendParamsJson.get("acType")) || variables.get("acType")==null){
                //如果是新增操作
                sendParamsJson.put("isEnable", "1");
            }
            httpParams.put("url", sendUrl);
            httpParams.put("urlName", sendUrlName);
            httpParams.put("FOREIGN_KEY", variables.get("RECORD_ID"));
        }
        //保存待发送记录入表
        saveDataDB(httpParams,sendParamsJson.toString());
        //sendDataToSJJXXZXTrueTime(httpParams,sendParamsJson.toString());
    }
    
    @Override
    public void startNewThread(Map<String, Object> variables, String operationType) {
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
        String FORWARDURL = dictionaryService.getDicNames("sjjxxzxConfig", "SJJXXZX_FORWARD_URL");
        variables.put("FORWARDURL", FORWARDURL);
        if ("开".equals(switchTurn)) {
            newCallService.runnable(new PushDateToSJJXXZXRunnable(variables, operationType));
        } else if ("测试".equals(switchTurn)) {
            new Thread(new PushDateToSJJXXZXRunnable(variables, operationType)).start();
        }
    }
    
    /**
     * 描述 保存省经济信息中心待发记录入表
     * 
     * @author Jason Lin
     * @created 2015年11月24日 下午3:51:39
     */
    public void saveDataDB(Map<String,Object> requestMap,String sendJsonStr) {
        //获取字典相关信息
        Map<String, Object> saveMap = new HashMap<String, Object>();
        saveMap.put("INTERFACE_URL", requestMap.get("url").toString());
        saveMap.put("INTERFACE_NAME", requestMap.get("urlName").toString());
        saveMap.put("SEND_JSON", sendJsonStr.trim());
        saveMap.put("UPDATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        saveMap.put("FOREIGN_KEY", StringUtil.getValue(requestMap, "RECORD_ID"));
        saveMap.put("UPLOAD_STATUS", "0");
        this.dao.saveOrUpdate(saveMap, "T_BSFW_SJJXXZX_RECORD", null);
    }
    
}
