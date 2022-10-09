/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.SysSendMsgService;
import net.evecom.platform.zzhy.service.MsgSendService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 描述 短信发送服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("sysSendMsgService")
public class SysSendMsgServiceImpl extends BaseServiceImpl implements SysSendMsgService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SysSendMsgServiceImpl.class);
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
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 所引入的dao
     */
    @Resource
    private MsgSendService msgSendService;

    /**
     * 描述 保存短信下发信息到短信下发记录表
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:51:39
     * @param content
     *            发送的内容
     * @param receiverName
     *            接收人
     * @param receiverMob
     *            接收人手机号
     * @param sendStatus
     *            发送状态 0： 未发送 1：已发送成功 2：发送失败
     * @param sendResult
     *            发送返回码
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因 result为false时有值
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveSendMsg(String content, String receiverName, String receiverMob,
            Integer sendStatus, String sendResult) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (StringUtil.isBlank(content)) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的信息内容不能为空！");
            return returnMap;
        }
        if (StringUtil.isBlank(receiverMob)) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的手机号码不能为空！");
            return returnMap;
        }
        if (content.length() > 400) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的内容不能超过400个字符！");
            return returnMap;
        }
        Map<String, Object> colValues = new HashMap<String, Object>();
        colValues.put("CONTENT", content);
        colValues.put("RECEIVER_NAME", receiverName);
        colValues.put("RECEIVER_MOB", receiverMob);
        colValues.put("SEND_STATUS", sendStatus);
        colValues.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String result = this.dao.saveOrUpdate(colValues, "T_MSJW_SYSTEM_MSGSEND", "MSG_ID");
        if (StringUtil.isBlank(result)) {
            returnMap.put("result", false);
            returnMap.put("msg", "数据保存出错！");
        } else {
            returnMap.put("result", true);
        }
        return returnMap;
    }
    
    /**
     * 描述 保存短信下发信息到短信下发记录表
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:51:39
     * @param content
     *            发送的内容
     * @param receiverName
     *            接收人
     * @param receiverMob
     *            接收人手机号
     * @param sendStatus
     *            发送状态 0： 未发送 1：已发送成功 2：发送失败
     * @param sendResult
     *            发送返回码
     * @param type
     *            工程建设标识  1：工程建设  0：非工程建设
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因 result为false时有值
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveSendMsg(String content, String receiverName, String receiverMob,
            Integer sendStatus, String sendResult,String type) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (StringUtil.isBlank(content)) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的信息内容不能为空！");
            return returnMap;
        }
        if (StringUtil.isBlank(receiverMob)) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的手机号码不能为空！");
            return returnMap;
        }
        if (content.length() > 400) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的内容不能超过400个字符！");
            return returnMap;
        }
        Map<String, Object> colValues = new HashMap<String, Object>();
        colValues.put("CONTENT", content);
        colValues.put("RECEIVER_NAME", receiverName);
        colValues.put("RECEIVER_MOB", receiverMob);
        colValues.put("SEND_STATUS", sendStatus);
        colValues.put("SEND_TYPE", type);
        colValues.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String result = this.dao.saveOrUpdate(colValues, "T_MSJW_SYSTEM_MSGSEND", "MSG_ID");
        if (StringUtil.isBlank(result)) {
            returnMap.put("result", false);
            returnMap.put("msg", "数据保存出错！");
        } else {
            returnMap.put("result", true);
        }
        return returnMap;
    }

    /**
     * 描述 调用接口发送短信(发送多条 立即发送，旧接口，暂时停用)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午4:20:19
     * @param msgList
     *            要发送的内容:Map{[smsContent:发送的内容]，[phoneNumber: 接收人手机号]};
     * @return
     */
    @SuppressWarnings("unchecked")
    public String sendMsg(List<Map<String, Object>> msgList) {
        String result = "";
        if (!isOpenSendInterface())
            return result;
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String webUrl = "";
            String nameSpace = "";
            String contentType = "";
            List<Map<String, Object>> configList = this.dao.findBySql(
                    "select * from t_msjw_system_dictionary d  where type_code= ? ",
                    new Object[] { "msgInterfaceConfig" }, null);
            for (Map<String, Object> m : configList) {
                if (m.get("DIC_CODE").equals("webserviceUrl")) {
                    webUrl = (String) m.get("DIC_DESC");
                } else if (m.get("DIC_CODE").equals("namespace")) {
                    nameSpace = (String) m.get("DIC_DESC");
                } else if (m.get("DIC_CODE").equals("sysCode")) {
                    dataMap.put("sysCode", m.get("DIC_DESC"));
                } else if (m.get("DIC_CODE").equals("sysName")) {
                    dataMap.put("username", m.get("DIC_DESC"));
                } else if (m.get("DIC_CODE").equals("pwd")) {
                    dataMap.put("pwd", m.get("DIC_DESC"));
                } else if (m.get("DIC_CODE").equals("contentType")) {
                    contentType = (String) m.get("DIC_DESC");
                }
            }
            Map<String, Object> dataAbutment = dao.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.SYS_SENDMSG_CONFIG_CODE_MORE });
            String xml = (String) dataAbutment.get("CONFIG_XML");
            dataMap.put("notes", msgList);
            xml = FreeMarkerUtil.getResultString(xml, dataMap);
            result = WebServiceCallUtil.callService(webUrl, nameSpace, "CallSMSList", xml, contentType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return parseSendSMSListResult(result);
    }

    /**
     * 
     * 描述 解析返回的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月26日 下午5:14:09
     * @param result
     * @return
     */
    public String parseSendSMSListResult(String result) {
        // 调用接口失败
        String resultCode = "20";
        if (!StringUtils.isBlank(result)) {
            Object m;
            try {
                m = XmlUtil.xml2Map(result);

                Map body = (Map) ((Map) m).get("Body");
                Map callSMSResponse = (Map) body.get("CallSMSListResponse");
                Map CallSMSResult = (Map) callSMSResponse.get("CallSMSListResult");
                resultCode = (String) CallSMSResult.get("CallSMSListResult");
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
        }
        return resultCode;
    }

    /**
     * 
     * 描述 解析返回的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月26日 下午5:14:09
     * @param result
     * @return
     */
    public String parseSendSMSResult(String result) {
        // 调用接口失败
        String resultCode = "20";
        if (!StringUtils.isBlank(result)) {
            Object m;
            try {
                m = XmlUtil.xml2Map(result);
                Map body = (Map) ((Map) m).get("Body");
                Map serverHanderResponse = (Map) body.get("sendSmResponse");
                Map resultNap = (Map) serverHanderResponse.get("return");
                resultCode = (String) resultNap.get("resultCode");
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
        }
        return resultCode;
    }

    /**
     * 描述 调用接口发送短信 发送单条(立即发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午4:20:19
     * @param content
     *            :发送的内容; receiverMob : 接收人手机号
     * @return
     */
    @SuppressWarnings("unchecked")
    public String sendMsg(String content, String receiverMobs) {
        String result = "";
        if (!isOpenSendInterface())
            return result;
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("phoneList", receiverMobs);
            dataMap.put("portalCode", "综合审批平台");
            dataMap.put("portalPwd", "综合审批平台");
            dataMap.put("smsContent", content);
            dataMap.put("preTime", "");
            dataMap.put("isneedReply", "false");
            String webUrl = "";
            String nameSpace = "";
            String contentType = "";
            List<Map<String, Object>> configList = this.dao.findBySql(
                    "select * from t_msjw_system_dictionary d  where type_code= ? ",
                    new Object[] { "msgInterfaceConfig" }, null);
            for (Map<String, Object> m : configList) {
                if (m.get("DIC_CODE").equals("webserviceUrl")) {
                    webUrl = (String) m.get("DIC_DESC");
                } else if (m.get("DIC_CODE").equals("namespace")) {
                    nameSpace = (String) m.get("DIC_DESC");
                } else if (m.get("DIC_CODE").equals("sysCode")) {
                    dataMap.put("portalCode", m.get("DIC_DESC"));
                } else if (m.get("DIC_CODE").equals("pwd")) {
                    dataMap.put("portalPwd", m.get("DIC_DESC"));
                } else if (m.get("DIC_CODE").equals("contentType")) {
                    contentType = (String) m.get("DIC_DESC");
                } else if (m.get("DIC_CODE").equals("sendName")) {
                    dataMap.put("sendName", m.get("DIC_DESC"));
                }
            }
            Map<String, Object> dataAbutment = dao.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.SYS_SENDMSG_CONFIG_CODE_SINGLE });
            String xml = (String) dataAbutment.get("CONFIG_XML");
            xml = FreeMarkerUtil.getResultString(xml, dataMap);
            log.info("短信目标weburl=" + webUrl + ",nameSpace=" + nameSpace);
            result = WebServiceCallUtil.callServiceNotSSL(webUrl, nameSpace, "sendSm", xml, contentType, "utf-8");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return parseSendSMSResult(result);
    }

    /**
     * 描述 判断短信接口是否打开
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     */
    @SuppressWarnings("unchecked")
    public boolean isOpenSendInterface() {
        Map<String, Object> msgControllerMap = this.dao.getByJdbc("t_msjw_system_dictionary", 
                new String[] {"type_code", "dic_code" }, 
                new Object[] { "msgInterfaceConfig", "sendController" });
        if (msgControllerMap != null && msgControllerMap.get("DIC_DESC") != null
                && msgControllerMap.get("DIC_DESC").equals("1")) {
            return true;
        }
        return false;
    }

    /**
     * 描述 调用短信发送接口发送短信对列中的数据 由定时任务调用该方法
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    @SuppressWarnings("unchecked")
    public void sendMsgList() {
        if (!isOpenSendInterface())
            return;
        String sendname = "平潭综合审批系统";
        Map<String, Object> msgControllerMap = this.dao.getByJdbc("t_msjw_system_dictionary", 
                new String[] {"type_code", "dic_code" }, 
                new Object[] { "msgInterfaceConfig", "sendName" });
        if (msgControllerMap != null && msgControllerMap.get("DIC_DESC") != null) {
            sendname = (String) msgControllerMap.get("DIC_DESC");
        }

        Date nowDate = new Date();
        String sendTime = DateTimeUtil.dateToStr(nowDate, "yyyy-MM-dd HH:mm:ss");
        String nowDateYMD = DateTimeUtil.dateToStr(nowDate, "yyyy-MM-dd");
        // 获取短信息下发信息list 每次取500条
//        String updateSql = "update t_msjw_system_msgsend m set m.send_status =8,m.send_time= ? "
//                + " where exists (select 1 from (select msg_id from "
//                + " (select n.msg_id, rownum as rnum from (select msg_id from t_msjw_system_msgsend "
//                + " where send_status=0  order by create_time asc ) n ) p where p.rnum<=500) b "
//                + " where b.msg_id=m.msg_id)";
        String updateSql = " update t_msjw_system_msgsend m "
              + " set m.send_status = 8 , m.send_time = ? "
              + " where msg_id in (SELECT msg_id "
              + " from (select msg_id, rownum as rnum "
              + " from t_msjw_system_msgsend "
              + " where send_status = 0 and send_type = 1 and create_time like ? "
              + " order by create_time asc) n "
              + " where n.rnum <= 100) ";
        log.info("本次updatesql：" + updateSql + "sendTime：" + sendTime);
        this.dao.executeSql(updateSql, new Object[] { sendTime,"%" + nowDateYMD + "%" });
        String sql = "select msg_id, content,receiver_mob,'' as pre_Time,'false' as isneed_Reply,'" + sendname
                + "' as sendname" + " from (select m.*, rownum as rnum from (select * from t_msjw_system_msgsend"
                + " where send_status=8 and send_type = 1 and send_time = ? and create_time like ? order by create_time asc ) m ) n where n.rnum<=100";
        List<Map<String, Object>> msgList = this.dao.findBySql(sql, new Object[] { sendTime,"%" + nowDateYMD + "%" }, null);
        log.info("本次sql：" + sql + "sendTime：" + sendTime);
        log.info("本次发送短信条数：" + msgList.size());
        // 获取数据库连接信息
        if (msgList != null && !msgList.isEmpty()) {
            //短信发送改为联通发送
            Properties properties = FileUtil.readProperties("conf/messageConfig.properties");
            String templetId = properties.getProperty("messageTemplate2");
            log.info("发送短信模板ID：" + templetId);
            for (Map<String, Object> msg : msgList) {
                log.info("已进入短信待发库");
                log.info("发送短信内容：" + msg.get("CONTENT").toString());
                log.info("发送短信目标号码：" + msg.get("RECEIVER_MOB").toString());
//                String result = this.sendMsg((String) msg.get("CONTENT"), (String) msg.get("RECEIVER_MOB"));
//              String result = this.sendMsg((String) msg.get("CONTENT"), (String) msg.get("RECEIVER_MOB"));
                msgSendService.saveMsg((String) msg.get("CONTENT"), (String) msg.get("RECEIVER_MOB"));
//                boolean result = MsgSendUtils.sendSmsTypeTwo((String) msg.get("RECEIVER_MOB"), (String) msg.get("CONTENT"));
//                log.info("发送短信回执：" + msg.get("RECEIVER_MOB").toString() + "result:" + result);
//                String vSql = "update t_msjw_system_msgsend m set m.send_status = ? , m.send_result=? "
//                        + " where exists (select 1 from (select msg_id from "
//                        + " (select n.msg_id, rownum as rnum from (select msg_id from t_msjw_system_msgsend "
//              + " where send_status=8 and send_time=? order by create_time asc ) n ) p where p.rnum<=500) b "
//                        + " where b.msg_id=m.msg_id)";
                
                try {
                    SmsSendUtil.sendSms(msg.get("CONTENT").toString(),msg.get("RECEIVER_MOB").toString(), templetId);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                String vSql = "update t_msjw_system_msgsend m set m.send_status = ? "
                        + " where m.msg_id=? ";
                log.info("更新短信发送表状态sql：" + vSql +  ",msg.get('MSG_ID')" + msg.get("MSG_ID"));
//                this.dao.executeSql(vSql, new Object[] { result.equals("000000") ? "1" : "2",
//                        result,msg.get("MSG_ID")});
                this.dao.executeSql(vSql, new Object[] {"1",msg.get("MSG_ID")});
                
                //修改另一张短信表中的发送状态（如果有的话）
                StringBuffer sqlUpload=new StringBuffer();
                sqlUpload.append(" update T_MESSAGE_INFO m ");
                sqlUpload.append(" set m.SEND_STATUS='1' ");
                sqlUpload.append(" where m.SEND_STATUS='0' and m.create_time like ? ");
                sqlUpload.append(" and m.msg_info = ? and m.receiver_mob = ? ");
                this.dao.executeSql(sqlUpload.toString(), new Object[] {"%"+nowDateYMD+"%",msg.get("CONTENT").toString(),msg.get("RECEIVER_MOB").toString()});
            }
        }
    }
}
