/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.call.dao.NewCallDao;
import net.evecom.platform.call.service.CallLogService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.weixin.model.TemplateData;
import net.evecom.platform.weixin.model.WechatTemplate;
import net.evecom.platform.weixin.model.WxAccessToken;
import net.evecom.platform.weixin.util.WeixinUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年7月3日 下午3:58:56
 */
@Service("newCallService")
public class NewCallServiceImpl extends BaseServiceImpl implements NewCallService {
    /**
     * log
     */
    private static Log log= LogFactory.getLog(NewCallService.class);
    /**
     * 引入dao
     */
    @Resource
    private NewCallDao dao;
    /**
     * 引入dao
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/23 14:50:00
     * @param
     * @return
     */
    private final String SMS_SEND_TEMPLATEID = "66085";

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2018年7月3日 下午4:00:03
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 第三方接口调用日志
     */
    @Resource
    private CallLogService callLogService;
    
    /**
     * 
     * 描述    获取取号单位/部门
     * @author Danto Huang
     * @created 2018年7月4日 上午9:22:28
     * @param roomNo
     * @return
     */
    public List<Map<String,Object>> findTakeNoDepart(String roomNo){
        StringBuffer sql = new StringBuffer();
        sql.append("select i.depart_id,d.depart_name,b.icon_path,b.belong_room,room.dic_desc from ");
        sql.append("(select t.depart_id from T_CKBS_BUSINESSDATA t ");
        sql.append(" where t.service_status=1 group by t.depart_id) i ");
        sql.append("left join t_msjw_system_department d on d.depart_id=i.depart_id ");
        sql.append("left join T_CKBS_BUSINESSDATA_ICON b on b.depart_id=i.depart_id ");
        sql.append("left join (select d.dic_code,d.dic_name,d.dic_desc from t_msjw_system_dictionary d ");
        sql.append("where d.type_id=(select s.type_id from t_msjw_system_dictype s where s.type_code='roomNo')) ");
        sql.append("room on room.dic_code=b.belong_room ");
        sql.append("order by decode(INSTR(b.belong_room, ?),0,0,1) desc,to_number(d.tree_sn) ");
        return dao.findBySql(sql.toString(), new Object[]{roomNo}, null);
    }

    /**
     * 根据身份证号获取业务排队前面排队人数
     * @param cardId
     * @return
     */
    public List<Map<String,Object>> getBusWaitNumByCardId(String cardId){
        //根据身份证获取
        StringBuffer sql=new StringBuffer("SELECT Q.RECORD_ID,Q.LINE_NO,Q.BUSINESS_CODE,Q.OPENID,D.BUSINESS_NAME ");
        sql.append(",Q.TAKE_SN ");
        sql.append(" FROM  T_CKBS_QUEUERECORD Q  ");
        sql.append(" LEFT JOIN T_CKBS_BUSINESSDATA  D ON Q.BUSINESS_CODE=D.BUSINESS_CODE  ");
        sql.append(" WHERE Q.LINE_CARDNO=? ");
        sql.append(" AND Q.CALL_STATUS='0'  ");
        List<Map<String,Object>> lineNums=dao.findBySql(sql.toString(),new Object[]{cardId},null);
        //根据业务号获取前面排队人数
        StringBuffer sqlOfBuscord=new StringBuffer("SELECT COUNT(*) AS NUM ");
        sqlOfBuscord.append(" FROM  T_CKBS_QUEUERECORD Q  ");
        sqlOfBuscord.append(" LEFT JOIN T_CKBS_BUSINESSDATA  D ON Q.BUSINESS_CODE=D.BUSINESS_CODE ");
        sqlOfBuscord.append("  WHERE Q.BUSINESS_CODE=?   ");
        sqlOfBuscord.append(" AND Q.CALL_STATUS='0' ");
        sqlOfBuscord.append(" AND Q.TAKE_SN<=? ");

        List<Map<String,Object>> busCordInfos = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> lineNum:lineNums){
            Map<String,Object> busCordInfo=new HashMap<String,Object>();
            String lineNo=String.valueOf(lineNum.get("LINE_NO"));
            String businessName=String.valueOf(lineNum.get("BUSINESS_NAME"));
            String businessCode=String.valueOf(lineNum.get("BUSINESS_CODE"));
            String takeSn=String.valueOf(lineNum.get("TAKE_SN"));
            Map<String,Object> numMap=dao.getByJdbc(sqlOfBuscord.toString(),
                    new Object[]{businessCode,takeSn});
            String num=String.valueOf(numMap.get("NUM"));
            busCordInfo.put("num",num);
            busCordInfo.put("businessName",businessName);
            busCordInfo.put("lineNo",lineNo);
            busCordInfo.put("recordId",lineNum.get("RECORD_ID"));
            busCordInfo.put("openId",lineNum.get("OPENID"));
            busCordInfos.add(busCordInfo);
        }
        return busCordInfos;
    }

    /**
     * 通知有绑定OPENID的微信号
     * @param recordId
     * @return
     */
    public String notifyWxHasOpenId(String recordId){
        String json="";
        //通知当前办理人员
        StringBuffer curSql=new StringBuffer("SELECT Q.*,B.BUSINESS_NAME,B.BELONG_ROOM ");
        curSql.append(" FROM T_CKBS_QUEUERECORD Q ");
        curSql.append(" LEFT JOIN T_CKBS_BUSINESSDATA B ");
        curSql.append(" ON Q.BUSINESS_CODE=B.BUSINESS_CODE ");
        curSql.append(" WHERE Q.RECORD_ID=?");
        Map<String,Object> curCall=dao.getByJdbc(curSql.toString(),new Object[]{recordId});
        String openId=curCall.get("OPENID")==null?"":
                curCall.get("OPENID").toString();
        if(StringUtils.isNotEmpty(openId)){
            Map<String,TemplateData> data=getDataOfCurMsg(curCall);
            String curTemplateId=dictionaryService.getDicCode("wxConfig","curTemplateId");
            //推送微信信息
            json=pullMessageWithOpenId(openId,data,curTemplateId);
        }
        //通知第几位办理人员
        //Map<String,Object> whenNeedToNotify=getWhenNeedToNotify(recordId);
        //if(whenNeedToNotify!=null){
        //    openId=whenNeedToNotify.get("OPENID")==null?"":
        //            whenNeedToNotify.get("OPENID").toString();
        //    if(StringUtils.isNotEmpty(openId)){
        //        //推送微信信息
        //        json=pullMessageWithOpenId(openId,"",
        //                "Rbmb2_KiX8KnfY2fxpGiGrEHGHDblWLaeIh0RQgV8UE");
        //    }
        //}
        return json;
    }

    /**
     * 获取当前发送模板值
     * @return
     */
    private Map<String,TemplateData> getDataOfCurMsg(Map<String,Object> curCall){
        Map<String,TemplateData> data = new HashMap<String,TemplateData>();
        String curOfFirst=dictionaryService.getDicCode("wxConfig","curFirst");
        TemplateData first = new TemplateData();
        first.setColor("#173177");
        first.setValue(curOfFirst);
        data.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setColor("#173177");
        keyword1.setValue(String.valueOf(curCall.get("LINE_NO")));
        data.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setColor("#173177");
        keyword2.setValue(String.valueOf(curCall.get("BUSINESS_NAME")));
        data.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
        keyword3.setColor("#173177");
        keyword3.setValue(String.valueOf(curCall.get("CUR_WIN")));
        data.put("keyword3", keyword3);

        String curTime=DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        TemplateData keyword4 = new TemplateData();
        keyword4.setColor("#173177");
        keyword4.setValue(curTime);
        data.put("keyword4", keyword4);

        TemplateData keyword5 = new TemplateData();
        keyword5.setColor("#173177");
        keyword5.setValue(String.valueOf(curCall.get("CREATE_TIME")));
        data.put("keyword5", keyword5);

        String curOfRemark=dictionaryService.getDicCode("wxConfig","curRemark");
        curOfRemark=curOfRemark.replace("CUR_WIN",String.valueOf(curCall.get("CUR_WIN")));
        TemplateData remark = new TemplateData();
        remark.setColor("#173177");
        remark.setValue(curOfRemark);
        data.put("remark", remark);
        return data;
    }
    /**
     *推送微信信息
     * @param openId
     * @return
     */
    private String pullMessageWithOpenId(String openId,Map<String,TemplateData> data,String templateId){
        WxAccessToken wxAccessToken = WeixinUtil.getWxAccessToken();
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplateId(templateId);
        wechatTemplate.setTouser(openId);
        wechatTemplate.setData(data);
        String url =dictionaryService.getDicCode("wxConfig","tokenUrl");
        url = url.replace("ACCESS_TOKEN", wxAccessToken.getTokenName());
        String wechatJson=JSONObject.toJSONString(wechatTemplate);
        wechatJson=wechatJson.replace("templateId","template_id");
        String json = HttpRequestUtil.sendPost(url, wechatJson);
        StringBuffer result=new StringBuffer();
        result.append("templateId=").append(templateId).append(",json=").append(json);
        log.info(result.toString());
        return json;
    }
    /**
     * 获取需要第几位通知的办事人
     * @param recordId
     * @return
     */
    private Map<String,Object> getWhenNeedToNotify(String recordId){
        Map<String,Object> notify=null;
        Map<String,Object> buscord=dao.getByJdbc("T_CKBS_QUEUERECORD",
                new String[]{"RECORD_ID"},new Object[]{recordId});
        String businessCode=String.valueOf(buscord.get("BUSINESS_CODE"));
        StringBuffer sql=new StringBuffer("SELECT Q.LINE_NO,Q.BUSINESS_CODE,Q.RECORD_ID ");
        sql.append(",Q.TAKE_SN,Q.OPENID ");
        sql.append(" FROM  T_CKBS_QUEUERECORD Q  ");
        sql.append(" WHERE Q.BUSINESS_CODE=? ");
        sql.append(" AND Q.CALL_STATUS='0'  ");
        sql.append(" ORDER BY Q.TAKE_SN DESC ");
        List<Map<String,Object>> list=dao.findBySql(sql.toString(),
               new Object[]{businessCode},null);
        //需要第几位通知
        String num=dictionaryService.getDicCode("wxConfig","whenNum");
        int whenNum=Integer.parseInt(num);
        if(list.size()>=whenNum){
            notify=list.get(whenNum-1);
            notify.put("whenNum",whenNum);
        }
        return notify;
    }
    /**
     * 根据排队号获取业务排队位置相关信息
     * @param LineNo
     * @return
     */
    public Map<String,Object> getBusWaitNumByLineNo(String LineNo){
        LineNo=LineNo.toUpperCase();
        //根据排队获取
        StringBuffer sql=new StringBuffer("SELECT Q.LINE_NO,Q.BUSINESS_CODE,D.BUSINESS_NAME,Q.RECORD_ID ");
        sql.append(",Q.TAKE_SN,Q.OPENID ");
        sql.append(" FROM  T_CKBS_QUEUERECORD Q  ");
        sql.append(" LEFT JOIN T_CKBS_BUSINESSDATA  D ON Q.BUSINESS_CODE=D.BUSINESS_CODE  ");
        sql.append(" WHERE Q.LINE_NO=? ");
        sql.append(" AND Q.CALL_STATUS='0'  ");
        Map<String,Object> lineNum=dao.getByJdbc(sql.toString(),new Object[]{LineNo});
        //根据业务号获取前面排队人数
        Map<String, Object> busCordInfo = new HashMap<String, Object>();
        if(lineNum!=null){
            StringBuffer sqlOfBuscord = new StringBuffer("SELECT COUNT(*) AS NUM ");
            sqlOfBuscord.append(" FROM  T_CKBS_QUEUERECORD Q  ");
            sqlOfBuscord.append("  WHERE Q.BUSINESS_CODE=?   ");
            sqlOfBuscord.append(" AND Q.CALL_STATUS='0' ");
            sqlOfBuscord.append(" AND Q.TAKE_SN<= ? ");
            String lineNo = String.valueOf(lineNum.get("LINE_NO"));
            String businessName = String.valueOf(lineNum.get("BUSINESS_NAME"));
            String businessCode = String.valueOf(lineNum.get("BUSINESS_CODE"));
            String recordId = String.valueOf(lineNum.get("RECORD_ID"));
            String takeSn = String.valueOf(lineNum.get("TAKE_SN"));
            String openId = String.valueOf(lineNum.get("OPENID"));
            Map<String, Object> numMap = dao.getByJdbc(sqlOfBuscord.toString(), new Object[]{businessCode, takeSn});
            String num = String.valueOf(numMap.get("NUM"));
            //排队的位置
            busCordInfo.put("num", num);
            //业务名称
            busCordInfo.put("businessName", businessName);
            //排队号
            busCordInfo.put("lineNo", lineNo);
            //微信号id
            busCordInfo.put("openId", openId);
            //取号表的主键id,用来更新openId
            busCordInfo.put("recordId", recordId);
        }
        return busCordInfo;
    }

    /**
     * 根据业务编号来获取需要被通知的公众号排队信息
     * @param businessCode
     * @return
     */
    public List<Map<String,Object>> findWxNotifyByBusinessCode(String businessCode){
        List<Map<String, Object>> notifys = new ArrayList<Map<String, Object>>();
        StringBuffer sql=new StringBuffer("SELECT Q.OPENID,Q.LINE_NO,D.BUSINESS_NAME ");
        sql.append(" FROM T_CKBS_QUEUERECORD Q ");
        sql.append(" LEFT JOIN T_CKBS_BUSINESSDATA  D ON Q.BUSINESS_CODE=D.BUSINESS_CODE");
        sql.append("  WHERE Q.BUSINESS_CODE=? AND ");
        sql.append(" Q.CALL_STATUS='0' AND Q.OPENID IS NOT NULL");
        List<Map<String,Object>> ques=dao.findBySql(sql.toString(),new Object[]{businessCode},null);
        for(Map<String,Object> que:ques){
            String lineNo=String.valueOf(que.get("LINE_NO"));
            Map<String,Object> notify=getBusWaitNumByLineNo(lineNo);
            notifys.add(notify);
        }
        return notifys;
    }
    /**
     * 更新openId
     * @param variable
     */
    public void updateOpenId(Map<String,Object> variable){
        String openId=String.valueOf(variable.get("opendId"));
        String recordId=String.valueOf(variable.get("recordId"));
        StringBuffer sql=new StringBuffer("");
        sql.append("update T_CKBS_QUEUERECORD set openId=？ ");
        sql.append("where record_id=? ");
        dao.executeSql(sql.toString(),new Object[]{openId,recordId});
    }
    /**
     * 
     * 描述 根据窗口号获取该组未办结的业务
     * @author Kester Chen
     * @created 2019年3月12日 下午5:15:39
     * @param departId
     * @return
     * @see net.evecom.platform.call.service.NewCallService#findBusinessByDepartId(java.lang.String)
     */
    public List<Map<String,Object>> findKByWinNo(String winNo){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.line_no,t.exe_id,t.BUSINESS_CODE from T_CKBS_QUEUERECORD t ");
        sql.append("left join JBPM6_EXECUTION e on t.exe_id = e.exe_id ");
        sql.append("where 1=1 and t.business_code in ('B','D','H','F') ");
        sql.append("and e.run_status = 1 ");
        if ("01".equals(winNo)||"02".equals(winNo)) {
            sql.append("and (t.CUR_WIN = '01' or t.CUR_WIN = '02') ");
        }else if ("03".equals(winNo)||"04".equals(winNo)) {
            sql.append("and (t.CUR_WIN = '03' or t.CUR_WIN = '04') ");
        }else if ("05".equals(winNo)||"06".equals(winNo)) {
            sql.append("and (t.CUR_WIN = '05' or t.CUR_WIN = '06') ");
        }else if ("07".equals(winNo)||"08".equals(winNo)) {
            sql.append("and (t.CUR_WIN = '07' or t.CUR_WIN = '08') ");
        }else if ("09".equals(winNo)||"10".equals(winNo)) {
            sql.append("and (t.CUR_WIN = '09' or t.CUR_WIN = '10') ");
        }else if ("11".equals(winNo)||"12".equals(winNo)) {
            sql.append("and (t.CUR_WIN = '11' or t.CUR_WIN = '12') ");
        }else {
//            其他窗口不做限制
            sql.append("and t.CUR_WIN = '111111'  ");
        }
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    /**
     * 
     * 描述 获取一窗通办排队人数
     * @author Kester Chen
     * @created 2019年3月16日 下午5:04:06
     * @return
     * @see net.evecom.platform.call.service.NewCallService#findYctbWaitingList()
     */
    public List<Map<String,Object>> findYctbWaitingList(){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.line_no ");
        sql.append("from T_CKBS_QUEUERECORD t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.BUSINESS_CODE = t.BUSINESS_CODE ");
        sql.append("where substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd') ");
        sql.append("and t.call_status = 0 ");
        sql.append("and b.depart_id = '2c90b38a67a6266d0167ab958b94619b' ");
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    /**
     * 
     * 描述 获取一窗通办排队人数
     * @author Kester Chen
     * @created 2019年3月16日 下午5:04:06
     * @return
     * @see net.evecom.platform.call.service.NewCallService#findYctbWaitingList()
     */
    public List<Map<String,Object>> findABWaitingList(){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) as num ");
        sql.append("from T_CKBS_QUEUERECORD t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.BUSINESS_CODE = t.BUSINESS_CODE ");
        sql.append("where substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd') ");
        sql.append("and t.call_status = 0 ");
        sql.append("and b.depart_id = '2c90b38a67a6266d0167ab958b94619b' ");
        sql.append("and t.BUSINESS_CODE in ('A','B') ");
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    /**
     * 
     * 描述 获取一窗通办排队人数
     * @author Kester Chen
     * @created 2019年3月16日 下午5:04:06
     * @return
     * @see net.evecom.platform.call.service.NewCallService#findYctbWaitingList()
     */
    public List<Map<String,Object>> findOtherWaitingList(){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) as num ");
        sql.append("from T_CKBS_QUEUERECORD t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.BUSINESS_CODE = t.BUSINESS_CODE ");
        sql.append("where substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd') ");
        sql.append("and t.call_status = 0 ");
        sql.append("and b.depart_id = '2c90b38a67a6266d0167ab958b94619b' ");
        sql.append("and t.BUSINESS_CODE not in ('A','B') ");
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    
    /**
     * 
     * 描述    根据单位/部门id获取取号业务
     * @author Danto Huang
     * @created 2018年7月4日 上午11:09:35
     * @param departId
     * @return
     */
    public List<Map<String,Object>> findBusinessByDepartId(String departId){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,room.dic_desc from T_CKBS_BUSINESSDATA t ");
        sql.append("left join (select d.dic_code,d.dic_name,d.dic_desc from t_msjw_system_dictionary d ");
        sql.append("where d.type_id=(select s.type_id from t_msjw_system_dictype s where s.type_code='roomNo')) ");
        sql.append("room on room.dic_code=t.belong_room ");
        sql.append("where t.depart_id=? and t.service_status=1 ");
        sql.append("order by t.business_code ");
        return dao.findBySql(sql.toString(), new Object[]{departId}, null);
    }
    
    /**
     * 
     * 描述    是否窗口在受理业务
     * @author Danto Huang
     * @created 2018年7月5日 下午3:05:38
     * @param businessCode
     * @return
     */
    public boolean isWinAccept(String businessCode){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from T_CKBS_WIN_USER t where t.WIN_BUSINESS_CODES like ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{"%"+businessCode+"%"}, null);
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述    是否已在等待队列中
     * @author Danto Huang
     * @created 2018年7月5日 下午3:15:10
     * @param businessCode
     * @param cardNo
     * @return
     */
    public boolean isWaiting(String businessCode,String cardNo){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from T_CKBS_QUEUERECORD t ");
        sql.append("where t.BUSINESS_CODE=? and t.LINE_CARDNO=? and t.CALL_STATUS='0' ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{businessCode,cardNo}, null);
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述    生成排队号
     * @author Danto Huang
     * @created 2018年7月5日 下午3:39:44
     * @param businessCode
     * @return
     */
    public Map<String,Object> getLineNo(Map<String,Object> variables){
        String lineNo = "";
        if(variables.get("IS_VIP")!=null&&variables.get("IS_VIP").equals("1")){
            lineNo = "V";
        }else if(variables.get("IS_APPOINTMENT")!=null&&variables.get("IS_APPOINTMENT").equals("1")){
            lineNo = "Y";
        }else{
//            lineNo = (String) variables.get("ROOM_NO");
        }
        String businessCode = variables.get("BUSINESS_CODE").toString();
        int maxSn = dao.getMaxTakeSn(businessCode);
        maxSn = maxSn+1;
        String lineSn = "";
        if(maxSn<10){
            lineSn = "00"+String.valueOf(maxSn);
        }else if(maxSn<100){
            lineSn = "0"+String.valueOf(maxSn);
        }else{
            lineSn = String.valueOf(maxSn);
        }
        variables.put("TAKE_SN", maxSn);
        lineNo = lineNo+businessCode+lineSn;
        variables.put("LINE_NO", lineNo);

        String datelineNo = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+lineNo;
        variables.put("DATE_LINE_NO", datelineNo);
        return variables;
    }
    /**
     * 
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param businessCode
     * @return
     */
    public int getWaitingNumByBusinessCode(String businessCode){
        return dao.getWaitingNumByBusinessCode(businessCode);
    }
    /**
     *
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param roomNo
     * @return
     */
    public int getWaitingNumByRoomNo(String roomNo){
        return dao.getWaitingNumByRoomNo(roomNo);
    }
    /**
     * 
     * 描述    获取排队号留意窗口
     * @author Danto Huang
     * @created 2018年7月5日 下午4:10:52
     * @param businessCode
     * @return
     */
    public String getCareWin(String businessCode){
        String sql = "select wmsys.wm_concat(tw.win_no) win_no from(select t.win_no from T_CKBS_WIN_USER t "
                + "where t.WIN_BUSINESS_CODES like ? group by t.win_no order by t.win_no ) tw";
        return dao.getByJdbc(sql, new Object[]{"%"+businessCode+"%"}).get("WIN_NO").toString();
    }
    
    /**
     * 
     * 描述    获取取号数据列表
     * @author Danto Huang
     * @created 2018年7月5日 下午4:35:01
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findQueueListBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.record_id,t.cur_win,t.line_no,t.line_name,t.line_cardno,t.is_vip,t.create_time, ");
        sql.append("b.business_name,t.call_status,t.TOTOPREASON ");
        sql.append("from T_CKBS_QUEUERECORD t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.BUSINESS_CODE = t.BUSINESS_CODE ");
        sql.append("where substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd') ");
        sql.append(" and t.CALL_STATUS!='-1' ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_CKBS_QUEUERECORD");
        
        return list;
    }
    
    /**
     * 
     * 描述 排队数量
     * @author Kester Chen
     * @created 2019年1月24日 下午3:56:51
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findLineUpNumBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.room_no,d.depart_name,b.business_name, ");
        sql.append("t.business_code,count(t.line_no) as num  ");
        sql.append("from T_CKBS_QUEUERECORD t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.BUSINESS_CODE = t.BUSINESS_CODE ");
        sql.append("left join t_msjw_system_department d on d.depart_id = b.depart_id ");
        sql.append(" where substr(t.create_time,0,10)=to_char(sysdate,'yyyy-mm-dd')  ");
        sql.append(" and T.CALL_STATUS = 0 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        StringBuffer newSql=new StringBuffer(exeSql);
        newSql.append(" group by t.business_code, b.business_name ,d.depart_name,t.room_no ");
        newSql.append("order by t.room_no asc,d.depart_name asc, t.business_code desc ");
        List<Map<String, Object>> list = dao.findBySql(newSql.toString(),params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 排队数量
     * @author Kester Chen
     * @created 2019年1月24日 下午3:56:51
     * @return
     */
    public List<Map<String,Object>> getBeforByLineNo(String businessCode, String lineNo){
        StringBuffer sql =  new StringBuffer();
        sql.append("select t.* from T_CKBS_QUEUERECORD t ");
        sql.append("where t.business_code = '");
        sql.append("businessCode");
        sql.append("' and t.call_status = 0  ");
        sql.append("and t.line_no < '");
        sql.append(lineNo);
        sql.append("' ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
        return list;
    }

    /**
     * 
     * 描述    定时过号
     * @author Danto Huang
     * @created 2018年7月6日 上午8:57:02
     * @param date
     */
    public void overDueQueue(String date){
        String sql = "update T_CKBS_QUEUERECORD t set t.call_Status=2 "
                + "where (t.call_status=0 or t.call_status=6) and t.create_time<?";
        dao.executeSql(sql, new Object[]{date});
    }
    
    /**
     * 
     * 描述    窗口选择列表
     * @author Danto Huang
     * @created 2018年7月6日 上午11:11:34
     * @return
     */
    public List<Map<String,Object>> findUserWin(){
        String userId = AppUtil.getLoginUser().getUserId();
        StringBuffer sql =  new StringBuffer();
        sql.append("select t.record_id,t.win_no,t.screen_no,t.win_business_codes,trim(s.departinfo) depart_name,");
        sql.append("t.belong_room,t.win_business_names,t.cur_userid from T_CKBS_WIN_USER t ");
        sql.append("left join T_CKBS_WIN_SCREEN s on s.win_no = t.win_no ");
        sql.append("where t.oper_userids like '%").append(userId).append("%' and t.is_use=1 ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
        return list;
    }
    
    /**
     * 
     * 描述    获取窗口办件叫号列表
     * @author Danto Huang
     * @created 2018年7月6日 下午4:38:27
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,zjlx.dic_name zjlx, ");
        sql.append("case when t.call_status=6 then 1 else 0 end currentCall,");
        sql.append("case when t.is_appointment=0 or a.begin_time<=to_char(sysdate,'hh24:mi') ");
        sql.append("then 1 else 0 end appointcall FROM T_CKBS_QUEUERECORD T ");
        sql.append("left join (select td.dic_code,td.dic_name  from t_msjw_system_dictionary ");
        sql.append("td where td.type_code='DocumentType') zjlx on zjlx.dic_code = t.line_zjlx ");
        sql.append("left join  T_CKBS_APPOINTMENT_APPLY a on a.num_id = T.record_id ");
        if(sqlFilter.getQueryParams().get("Q_T.CUR_WIN_=")!=null){
            String winNo = sqlFilter.getQueryParams().get("Q_T.CUR_WIN_=").toString();
            sqlFilter.removeFilter("Q_T.CUR_WIN_=");
            sql.append("WHERE ((T.call_status=0 ");
            if(sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_IN")!=null){
                String businessCode = sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_IN").toString();
                sqlFilter.removeFilter("Q_T.BUSINESS_CODE_IN");
                sql.append(" and T.BUSINESS_CODE in").append(StringUtil.getValueArray(businessCode));
            }
            if(sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_NOTIN")!=null){
                String businessCode = sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_NOTIN").toString();
                sqlFilter.removeFilter("Q_T.BUSINESS_CODE_NOTIN");
                sql.append(" and T.BUSINESS_CODE NOT in").append(StringUtil.getValueArray(businessCode));
            }
            sql.append(") or (T.call_status=6 and T.CUR_WIN='").append(winNo).append("')) ");
        }else{
            sql.append("WHERE T.call_status=0 ");
        }
        sql.append(" and T.CALL_STATUS!='-1' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_CKBS_QUEUERECORD");
        
        return list;
    }
    
    /**
     * 
     * 描述    获取已办理记录列表(当天)
     * @author Danto Huang
     * @created 2018年7月9日 上午9:38:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingDayDoneBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,zjlx.dic_name zjlx ");
        sql.append("FROM T_CKBS_QUEUERECORD T ");
        sql.append("left join (select td.dic_code,td.dic_name  from t_msjw_system_dictionary ");
        sql.append("td where td.type_code='DocumentType') zjlx on zjlx.dic_code = t.line_zjlx ");
        sql.append("WHERE T.call_status<>0 and T.call_status<>6 ");
        sql.append("and to_char(sysdate,'yyyy-mm-dd')=substr(t.oper_time,0,10) ");
        sql.append(" and T.CALL_STATUS!='-1' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_CKBS_QUEUERECORD");
        /*if(list!=null&&list.size()>0){
            Map<String,Object> firstthreePass = findFirstthreePass(sqlFilter);
            if(firstthreePass!=null&&firstthreePass.get("RECORD_IDS")!=null){
                String firstthreePassIds = firstthreePass.get("RECORD_IDS").toString();
                for(Map<String,Object> called : list){
                    if(firstthreePassIds.indexOf(called.get("RECORD_ID").toString())>=0){
                        called.put("PASS_CANDO", "1");
                    }else{
                        called.put("PASS_CANDO", "0");
                    }
                }
            }
        }*/
        
        return list;
    }
    /**
     * 
     * 描述    前三个过号记录id
     * @author Danto Huang
     * @created 2021年10月14日 下午3:08:27
     * @param sqlFilter
     * @return
     */
    private Map<String,Object> findFirstthreePass(SqlFilter sqlFilter){
        StringBuffer sql = new StringBuffer("select wmsys.wm_concat(RECORD_ID) RECORD_IDS from (SELECT T.RECORD_ID ");
        sql.append("FROM T_CKBS_QUEUERECORD T WHERE T.call_status=2 and T.CUR_WIN=? ");
        sql.append("and to_char(sysdate,'yyyy-mm-dd')=substr(t.oper_time,0,10) ");
        sql.append("order by T.OPER_TIME desc) where rownum<=3");        
        Map<String, Object> map = dao.getByJdbc(sql.toString(),
                new Object[] { sqlFilter.getQueryParams().get("Q_T.CUR_WIN_=") });
        
        return map;
    }
    
    /**
     * 
     * 描述    获取已办理记录列表
     * @author Danto Huang
     * @created 2018年7月9日 上午9:38:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingDoneBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,zjlx.dic_name zjlx ");
        sql.append("FROM T_CKBS_QUEUERECORD T ");
        sql.append("left join (select td.dic_code,td.dic_name  from t_msjw_system_dictionary ");
        sql.append("td where td.type_code='DocumentType') zjlx on zjlx.dic_code = t.line_zjlx ");
        sql.append("WHERE T.call_status<>0 and T.call_status<>6 ");
        if(sqlFilter.getQueryParams().get("Q_T.EVELUATE_=")!=null){
            String eveluate = sqlFilter.getQueryParams().get("Q_T.EVELUATE_=").toString();
            sqlFilter.removeFilter("Q_T.EVELUATE_=");
            if(StringUtils.isNotEmpty(eveluate)){
                if(eveluate.equals("1")){
                    sql.append("and t.evaluate is not null ");
                }else if(eveluate.equals("0")){
                    sql.append("and t.evaluate is null ");
                }
            }
        }
        String lineCardNo = (String) sqlFilter.getQueryParams().get("Q_T.LINE_CARDNO_=");
        if (lineCardNo != null) {
            Sm4Utils sm4Utils = new Sm4Utils();
            sqlFilter.getQueryParams().put("Q_T.LINE_CARDNO_=", sm4Utils.encryptDataCBC(lineCardNo));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_CKBS_QUEUERECORD");
        
        return list;
    }
    
    /**
     * 
     * 描述    获取转发记录列表
     * @author Danto Huang
     * @created 2018年7月9日 下午5:38:11
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingFowardBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select T.LOG_ID,T.RECORD_ID,T.LINE_NO,T.FROM_WIN,T.TO_WIN,T.FORWARD_TIME,");
        sql.append("fu.fullname FROM_USER,tu.fullname TO_USER,fw.WIN_BUSINESS_NAMES FWIN_BUSINESS_NAMES,");
        sql.append("tw.WIN_BUSINESS_NAMES TWIN_BUSINESS_NAMES from T_CKBS_FORWARD_LOG T ");
        sql.append("left join t_msjw_system_sysuser fu on fu.user_id = T.FROM_USER ");
        sql.append("left join t_msjw_system_sysuser tu on tu.user_id = T.TO_USER ");
        sql.append("left join T_CKBS_WIN_USER fw on fw.win_no = T.FROM_WIN ");
        sql.append("left join T_CKBS_WIN_USER tw on tw.win_no = T.TO_WIN ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " order by T.FORWARD_TIME DESC ";
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    获取网上预约记录
     * @author Danto Huang
     * @created 2018年7月20日 下午3:39:58
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAppointmentBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,b.business_name,b.depart_id from T_CKBS_APPOINTMENT_APPLY t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());

        list = encryptionService.listDecrypt(list, "T_CKBS_APPOINTMENT_APPLY");
        
        return list;
    }

    /**
     * 
     * 描述    获取重新取号列表（已经取号但未叫号）
     * @author Danto Huang
     * @created 2018年7月20日 下午5:41:34
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAgainDataBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,b.business_name,b.depart_id from T_CKBS_QUEUERECORD t ");
        sql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
        sql.append("where 1=1 ");
        sql.append("and t.call_status = 0 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_CKBS_QUEUERECORD");
        
        return list;
    }
    /**
     * 
     * 描述  根据业务编号获取事项
     * @author Kester Chen
     * @created 2019年3月6日 下午2:57:27
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findYctbItemDataBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1'");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述  一窗通办获取事项
     * @author Kester Chen
     * @created 2019年3月6日 下午2:57:27
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findYctbBusinessItemDataBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("cb.*,room.dic_desc,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        
        sql.append("LEFT JOIN T_CKBS_BUSINESSDATA CB ON CB.business_code=T.business_code ");
        sql.append("left join (select d.dic_code,d.dic_name,d.dic_desc from t_msjw_system_dictionary d ");
        sql.append("where d.type_id=(select s.type_id from t_msjw_system_dictype s where s.type_code='roomNo')) ");
        sql.append("room on room.dic_code=cb.belong_room ");
        
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1' ");
        sql.append("and cb.service_status=1  ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述  一窗通办获取事项
     * @author Kester Chen
     * @created 2019年3月6日 下午2:57:27
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findYctbBusinessItemDataBySqlFilter2(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append("select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,");
        sql.append("T.BACKOR_NAME,T.C_SN,cb.*,room.dic_desc,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        
        sql.append("LEFT JOIN T_CKBS_BUSINESSDATA CB ON CB.business_code=T.business_code ");
        sql.append("left join (select d.dic_code,d.dic_name,d.dic_desc from t_msjw_system_dictionary d ");
        sql.append("where d.type_id=(select s.type_id from t_msjw_system_dictype s where s.type_code='roomNo')) ");
        sql.append("room on room.dic_code=cb.belong_room ");
        
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1' ");
        sql.append("and cb.service_status=1  ");
        sql.append("and t.business_code is not null  ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        filter.getPagingBean().setPageSize(10000);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取事项子项
     * @author Kester Chen
     * @created 2019年3月7日 下午3:20:44
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByItemCode(String itemCode){
        String sql = "select t.*, t.rowid from T_WSBS_SERVICEITEM_BUSCLASS t ";
        sql+=" left join T_WSBS_SERVICEITEM s on s.item_id = t.item_id ";
        sql+=" where s.item_code = ? ";
        return dao.findBySql(sql, new Object[]{itemCode}, null);
    }
    
    /**
     * 
     * 描述 获取事项子项
     * @author Kester Chen
     * @created 2019年3月7日 下午3:20:44
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findMaterByItemCode(String itemCode){
        String sql = "select t.*, t.rowid from T_WSBS_SERVICEITEM_BUSCLASS t ";
        sql+=" left join T_WSBS_SERVICEITEM s on s.item_id = t.item_id ";
        sql+=" where s.item_code = ? ";
        return dao.findBySql(sql, new Object[]{itemCode}, null);
    }
    
    /**
     * 
     * 描述       获取转发窗口
     * @author Danto Huang
     * @created 2018年9月5日 上午10:00:16
     * @param businessCode
     * @return
     */
    public List<Map<String,Object>> findWinsForSelect(String businessCode){
        StringBuffer sql = new StringBuffer();
        sql.append("select D.WIN_NO as text,D.WIN_NO as value,D.SCREEN_NO FROM T_CKBS_WIN_SCREEN D ");
        sql.append("left join t_ckbs_win_user u on u.win_no=d.win_no ");
        sql.append("where D.USE_STATUS='1' and instr(u.win_business_codes, ?)>0 ORDER BY to_number(D.WIN_NO) ASC");
        return dao.findBySql(sql.toString(), new Object[]{businessCode}, null);
    }
    
    /**
     * 
     * 描述       获取转发窗口
     * @author Danto Huang
     * @created 2018年9月5日 上午10:00:16
     * @param businessCode
     * @return
     */
    public List<Map<String,Object>> findWinsForSelectYctb(String businessCode){
        StringBuffer sql = new StringBuffer();
        String dicNames = dictionaryService.getDicNames("yctbckjh", "yctbzfpz");
        sql.append("select D.WIN_NO as text,D.WIN_NO as value,D.SCREEN_NO FROM T_CKBS_WIN_SCREEN D ");
        sql.append("left join t_ckbs_win_user u on u.win_no=d.win_no ");
        sql.append("where d.win_no in ");
        sql.append(" (").append(dicNames).append(") ");
        sql.append(" ORDER BY to_number(D.WIN_NO) ASC");
        return dao.findBySql(sql.toString(), new Object[]{}, null);
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月10日 下午6:49:12
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findPrintRcordBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,u.fullname,b.business_name from T_CKBS_POSABLE_PRINT t ");
        sql.append("left join t_msjw_system_sysuser u on u.user_id = t.operator_id ");
        sql.append("left join t_ckbs_businessdata b on b.business_code = t.win_business ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += " order by t.print_time desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    叫号短信发送记录数据
     * @author Allin Lin
     * @created 2021年3月23日 上午9:40:40
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findlineMsgRcordBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.YW_ID,t.QUEUE_RECORDID,t.LINE_NO,t.LINE_NAME,"
                + "t.LINE_MOBILE,t.MSG_STATUS,t.CREATOR_NAME,t.CREATE_TIME ");
        sql.append("from t_Bsfw_Lineusers_Msgrecord t ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += " order by t.CREATE_TIME desc ";
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list; 
    }
    
    /**
     * 
     * 描述    排队绑定微信OPENID
     * @author Danto Huang
     * @created 2018年7月5日 下午3:39:44
     * @param variables
     * @param openId
     * @return
     */
    @SuppressWarnings("unchecked")
    public String bindOpenId(Map<String,Object> variables,String recordId){
        return dao.saveOrUpdate(variables,"T_CKBS_QUEUERECORD",recordId);
    }
    
    /**
     * 
     * 描述    获取当前办理排队号的待办理事项列表
     * @author Danto Huang
     * @created 2019年3月13日 下午4:31:08
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findCurLineSelectedItems(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select i.detail_id,i.item_code,i.business_code,i.detail_status,s.item_id,s.item_name,");
        sql.append("f.def_key,b.business_name,i.subbus_class ");
        sql.append("from t_ckbs_queuerecord_itemdetail i ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = i.item_code ");
        sql.append("left join t_ckbs_businessdata b on b.business_code = i.business_code ");
        sql.append("left join jbpm6_flowdef f on f.def_id = s.bdlcdyid ");
        sql.append("where i.DETAIL_STATUS='0' ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    根据排队记录ID更新一窗通办当前办理叫号的状态
     * @author Danto Huang
     * @created 2019年3月14日 下午3:30:27
     * @param lineId
     */
    public void updateQueueRecordByDeatil(String lineId){
        String sql = "select * from T_CKBS_QUEUERECORD_ITEMDETAIL t where t.RECORD_ID=? order by t.DETAIL_STATUS asc";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{lineId}, null);
        if(list!=null&&list.size()>0){
            Map<String,Object> detail = list.get(0);
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("CALL_STATUS", detail.get("DETAIL_STATUS"));
            if("1".equals(detail.get("DETAIL_STATUS"))){
                variables.put("EXE_ID", detail.get("EXE_ID"));
            }else if("3".equals(detail.get("DETAIL_STATUS"))||"4".equals(detail.get("DETAIL_STATUS"))
                    ||"5".equals(detail.get("DETAIL_STATUS"))){
                variables.put("OPER_REMARK_SQR", detail.get("OPER_REMARK_SQR"));
                variables.put("OPER_REMARK", detail.get("OPER_REMARK"));
            }
            variables.put("OPERATOR", AppUtil.getLoginUser().getFullname());
            variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
            variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dao.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", detail.get("RECORD_ID").toString());
        }
    }
    
    /**
     * 
     * 描述    判断当前窗口所在分组是否有在办快件业务
     * @author Danto Huang
     * @created 2019年3月19日 下午4:21:55
     * @param curWin
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> isExpressItemDealing(String curWin){
        Map<String,Object> result = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select q.line_no,e.exe_id,q.cur_win,g.group_id,b.business_code ");
        sql.append("from t_ckbs_queuerecord_itemdetail d ");
        sql.append("left join t_ckbs_queuerecord q on d.record_id = q.record_id ");
        sql.append("left join jbpm6_execution e on e.exe_id = d.exe_id ");
        sql.append("left join t_ckbs_businessdata b on b.business_code = d.business_code ");
        sql.append("left join t_ckbs_win_user u on u.win_no = q.cur_win ");
        sql.append("left join t_ckbs_win_group g on g.group_id = u.win_group ");
        sql.append("where e.run_status = 1  and b.is_express = 1 and u.is_yctb = 1 ");
        
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        if(list!=null&&list.size()>0){
            Map<String, Object> winInfo = this.getByJdbc("T_CKBS_WIN_USER", new String[] { "WIN_NO" },
                    new Object[] { curWin });
            String groupId = "";
            if(winInfo.get("WIN_GROUP")!=null){
                groupId = winInfo.get("WIN_GROUP").toString();
            }
            StringBuffer businessCode = new StringBuffer();
            for(Map<String,Object> record : list){
                if(curWin.equals(record.get("CUR_WIN"))||groupId.equals(record.get("GROUP_ID"))){
                    businessCode.append(record.get("BUSINESS_CODE")).append(",");
                }
            }
            if(StringUtils.isNotEmpty(businessCode.toString())){
                result.put("businessCode", businessCode.toString());
                result.put("isDealing", true);
            }else{
                result.put("isDealing", false);
            }
        }else{
            result.put("isDealing", false);
        }
        return result;
    }

    /**
     * 
     * 描述    一窗通办窗口等待列表
     * @author Danto Huang
     * @created 2019年3月21日 上午11:51:08
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findYctbQueuingBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,zjlx.dic_name zjlx, ");
        sql.append("case when t.call_status=6 then 1 else 0 end currentCall,");
        sql.append("case when t.is_appointment=0 or a.begin_time<=to_char(sysdate,'hh24:mi') ");
        sql.append("then 1 else 0 end appointcall FROM T_CKBS_QUEUERECORD T ");
        sql.append("left join (select td.dic_code,td.dic_name  from t_msjw_system_dictionary ");
        sql.append("td where td.type_code='DocumentType') zjlx on zjlx.dic_code = t.line_zjlx ");
        sql.append("left join  T_CKBS_APPOINTMENT_APPLY a on a.num_id = T.record_id ");
        if(sqlFilter.getQueryParams().get("Q_T.CUR_WIN_=")!=null){
            String winNo = sqlFilter.getQueryParams().get("Q_T.CUR_WIN_=").toString();
            sqlFilter.removeFilter("Q_T.CUR_WIN_=");
            sql.append("WHERE ((T.call_status=0 ");
            if(sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_IN")!=null){
                String businessCode = sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_IN").toString();
                sqlFilter.removeFilter("Q_T.BUSINESS_CODE_IN");
                sql.append(" and T.BUSINESS_CODE in").append(StringUtil.getValueArray(businessCode));
            }
            if(sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_NOTIN")!=null){
                String businessCode = sqlFilter.getQueryParams().get("Q_T.BUSINESS_CODE_NOTIN").toString();
                sqlFilter.removeFilter("Q_T.BUSINESS_CODE_NOTIN");
                sql.append(" and T.BUSINESS_CODE NOT in").append(StringUtil.getValueArray(businessCode));
            }
            sql.append(") or (T.call_status=6 and T.CUR_WIN='").append(winNo).append("')) ");
        }else{
            sql.append("WHERE T.call_status=0 ");
        }
        sql.append(" and T.CALL_STATUS!='-1' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
//        System.out.println(exeSql+"==============================");
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_CKBS_QUEUERECORD");
        
        return list;
    }

    /**
     * 
     * 描述    获取窗口当前排队总数
     * @author Danto Huang
     * @created 2019年3月21日 上午11:25:24
     * @param winNo
     * @param businessCode
     * @return
     */
    public int getTotalQueuingByWinBusiness(String winNo, String businessCode){
        StringBuffer sql = new StringBuffer("SELECT count(*) count from T_CKBS_QUEUERECORD T ");
        sql.append("WHERE (T.call_status=0 or (T.call_status=6 and T.CUR_WIN='").append(winNo).append("')) ");
        sql.append("and T.BUSINESS_CODE in ").append(StringUtil.getValueArray(businessCode));
        return Integer.parseInt(dao.getByJdbc(sql.toString(), null).get("COUNT").toString());     
    }

//    /**
//     * 描述:检测是否是失信人员
//     *
//     * @author Madison You
//     * @created 2019/12/9 18:22:00
//     * @param
//     * @return
//     */
//    @Override
//    public Map<String, Object> checkLostPromise(String lineName, String lineCardNo, String itemName, String itemCode) {
//        Map<String, Object> itemMap = dao.getByJdbc("T_WSBS_SERVICEITEM",
//                new String[]{"ITEM_CODE"}, new Object[]{itemCode});
//        JSONObject json = new JSONObject();
//        HashMap<String, Object> returnMap = new HashMap<>();
//        Map<String, String> headMap = new HashMap<>();
//        boolean isLostPromise = false;
//        String msg = "";
//        String body = null;
//        if (itemMap != null && itemMap.get("LOST_PROMISE").equals("1")) {
//            headMap.put("Authorization", "Bearer 41baf188e1f5df2517add6bc55440b09");
//            Properties properties = FileUtil.readProperties("project.properties");
//            String lostPromiseUrl = properties.getProperty("LOST_PROMISE_URL");
//            json.put("id", lineCardNo);
//            json.put("name", lineName);
//            json.put("reason", itemName);
//            json.put("reasonid", itemCode);
//            try {
//                log.info("开始获取失信人员信息，" + json.toString());
//                body = HttpSendUtil.sendHttpsPostJson(lostPromiseUrl, headMap, json.toString(), "UTF-8");
//                log.info("结束获取失信人员信息"+body);
//                if (body != null && body.indexOf("<html>") == -1) {
//                    /*解析数据*/
//                    JSONObject bodyJson = JSONObject.parseObject(body);
//                    String code = bodyJson.getString("code");
//                    if (code.equals("0")) {
//                        String result = bodyJson.getString("result");
//                        JSONObject resultStr = JSONObject.parseObject(result);
//                        String elements = resultStr.getString("elements");
//                        String totalElements = resultStr.getString("totalElements");
//                        log.info("解析失信人员信息" + elements+ "  " +totalElements);
//                        if (!totalElements.equals("0")) {
//                            JSONArray elementArr = JSONArray.parseArray(elements);
//                            Map<String,Object> lostPromiseMap = (Map)elementArr.get(0);
//                            String caseNo = (String)lostPromiseMap.get("caseNo");
//                            msg = "姓名："+lineName+"；身份证号："+lineCardNo+"；判决文书号："+caseNo+"；您已被平潭县人民法院列入失信被执行人名单，";
//                            msg += "申请事项被纳入联合奖惩范围，如有异议，请联系法院申请处理；法院联系人：林法官0591-24390070";
//                            isLostPromise = true;
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        }
//        returnMap.put("msg", msg);
//        returnMap.put("isLostPromise",isLostPromise);
//        return returnMap;
//    }
//
//    /**
//     * 描述:检测是否是失信人员
//     *
//     * @author Madison You
//     * @created 2019/12/11 9:07:00
//     * @param
//     * @return
//     */
//    @Override
//    public Map<String, Object> checkLostPromise(String lineName, String lineCardNo, String departId) {
//        JSONObject json = new JSONObject();
//        HashMap<String, Object> returnMap = new HashMap<>();
//        Map<String, String> headMap = new HashMap<>();
//        boolean isLostPromise = false;
//        String msg = "";
//        String body = null;
//        try {
//            if (departId != null) {
//                Map<String, Object> depMap = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
//                        new String[]{"DEPART_ID"}, new Object[]{departId});
//                Object departName = depMap.get("DEPART_NAME");
//                headMap.put("Authorization", "Bearer 41baf188e1f5df2517add6bc55440b09");
//                Properties properties = FileUtil.readProperties("project.properties");
//                String lostPromiseUrl = properties.getProperty("LOST_PROMISE_URL");
//                json.put("id", lineCardNo);
//                json.put("name", lineName);
//                json.put("reason", departName);
//                json.put("reasonid", departId);
//                log.info("开始获取失信人员信息，" + json.toString());
//                body = HttpSendUtil.sendHttpsPostJson(lostPromiseUrl, headMap, json.toString(), "UTF-8");
//                log.info("结束获取失信人员信息"+body);
//                if (body != null && body.indexOf("<html>") == -1) {
//                    /*解析数据*/
//                    JSONObject bodyJson = JSONObject.parseObject(body);
//                    String code = bodyJson.getString("code");
//                    if (code.equals("0")) {
//                        String result = bodyJson.getString("result");
//                        JSONObject resultStr = JSONObject.parseObject(result);
//                        String elements = resultStr.getString("elements");
//                        String totalElements = resultStr.getString("totalElements");
//                        log.info("解析失信人员信息" + elements+ "  " +totalElements);
//                        if (!totalElements.equals("0")) {
//                            JSONArray elementArr = JSONArray.parseArray(elements);
//                            Map<String,Object> lostPromiseMap = (Map)elementArr.get(0);
//                            String caseNo = (String)lostPromiseMap.get("caseNo");
//                            msg = "姓名："+lineName+"；身份证号："+lineCardNo+"；判决文书号："+caseNo+"；您已被平潭县人民法院列入失信被执行人名单，";
//                            msg += "申请事项被纳入联合奖惩范围，如有异议，请联系法院申请处理；法院联系人：林法官0591-24390070";
//                            isLostPromise = true;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        returnMap.put("msg", msg);
//        returnMap.put("isLostPromise",isLostPromise);
//        return returnMap;
//    }
//
//
//    /**
//     * 描述:失信人判定  业务
//     *
//     * @author Madison You
//     * @created 2019/12/11 16:33:00
//     * @param
//     * @return
//     */
//    @Override
//    public Map<String, Object> checkLostPromiseByBus(String lineName, String lineCardNo,
//                                                     String businessCode, String businessName) {
//        JSONObject json = new JSONObject();
//        HashMap<String, Object> returnMap = new HashMap<>();
//        Map<String, String> headMap = new HashMap<>();
//        boolean isLostPromise = false;
//        String msg = "";
//        String body = null;
//        try {
//            headMap.put("Authorization", "Bearer 41baf188e1f5df2517add6bc55440b09");
//            Properties properties = FileUtil.readProperties("project.properties");
//            String lostPromiseUrl = properties.getProperty("LOST_PROMISE_URL");
//            json.put("id", lineCardNo);
//            json.put("name", lineName);
//            json.put("reason", businessName);
//            json.put("reasonid", businessCode);
//            log.info("开始获取失信人员信息，" + json.toString());
//            body = HttpSendUtil.sendHttpsPostJson(lostPromiseUrl, headMap, json.toString(), "UTF-8");
//            log.info("结束获取失信人员信息"+body);
//            if (body != null && body.indexOf("<html>") == -1) {
//                /*解析数据*/
//                JSONObject bodyJson = JSONObject.parseObject(body);
//                String code = bodyJson.getString("code");
//                if (code.equals("0")) {
//                    String result = bodyJson.getString("result");
//                    JSONObject resultStr = JSONObject.parseObject(result);
//                    String elements = resultStr.getString("elements");
//                    String totalElements = resultStr.getString("totalElements");
//                    log.info("解析失信人员信息" + elements+ "  " +totalElements);
//                    if (!totalElements.equals("0")) {
//                        JSONArray elementArr = JSONArray.parseArray(elements);
//                        Map<String,Object> lostPromiseMap = (Map)elementArr.get(0);
//                        String caseNo = (String)lostPromiseMap.get("caseNo");
//                        msg = "姓名："+lineName+"；身份证号："+lineCardNo+"；判决文书号："+caseNo+"；您已被平潭县人民法院列入失信被执行人名单，";
//                        msg += "申请事项被纳入联合奖惩范围，如有异议，请联系法院申请处理；法院联系人：林法官0591-24390070";
//                        isLostPromise = true;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        returnMap.put("msg", msg);
//        returnMap.put("isLostPromise",isLostPromise);
//        return returnMap;
//    }

    
    /**
     * 描述:获取token
     *
     * @author Madison You
     * @created 2020/12/10 11:13:00
     * @param
     * @return
     */
    @Override
    public String getCreditToken() {
        String token = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String username = properties.getProperty("SSDJ_LOGIN_USERNAME");
        String password = properties.getProperty("SSDJ_LOGIN_PASSWORD");
        String tokenUrl = properties.getProperty("CREDIT_TOKEN_URL");
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("username", username);
        jsonMap.put("password", password);
        JSONObject json = new JSONObject(jsonMap);
        try{
            String result = HttpSendUtil.sendHttpPostJson(tokenUrl, null,
                    json.toJSONString(), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                Map<String,Object> bodyMap = JSON.parseObject(result, Map.class);
                token = StringUtil.getValue(bodyMap, "token");
            }
        } catch (Exception e) {
            log.error("Token接口调用失败:", e);
        }
        log.info("失信人信息查询Token:" + token);
        return token;
    }
    
    /**
     * 描述:根据业务配置进行失信人判断
     *
     * @author Madison You
     * @created 2020/6/23 16:56:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> checkLostPromiseByBusCode(String lineName, String lineCardNo, String businessCode) {
        JSONObject json = new JSONObject();
        HashMap<String, Object> returnMap = new HashMap<>();
        Map<String, String> headMap = new HashMap<>();
        boolean isLostPromise = false;
        String msg = "";
        String body = null;
        long LOG_START_TIME = 0l;//请求开始时间戳
        boolean isCall = false;//是否调用第三方接口
        int LOG_RESPONSE_STATUS = 0;//接口调用状态 0失败,1成功
        String LOG_RETURN_ID = "";//接口返回日志ID
        String BUSINESS_NAME = "";
        try{
            if (StringUtils.isNotEmpty(businessCode)) {
                Map<String, Object> busDataMap = dao.getByJdbc("T_CKBS_BUSINESSDATA",
                        new String[]{"BUSINESS_CODE"}, new Object[]{businessCode});
                String IS_CHECKLOST = (String) busDataMap.get("IS_CHECKLOST");
                BUSINESS_NAME = (String) busDataMap.get("BUSINESS_NAME");
                if (StringUtils.isNotEmpty(IS_CHECKLOST) && IS_CHECKLOST.equals("1")) {
                    String token = this.getCreditToken();
                    if(StringUtils.isNotEmpty(token)){
                        headMap.put("Authorization", token);
                        Properties properties = FileUtil.readProperties("project.properties");
                        String lostPromiseUrl = properties.getProperty("LOST_PROMISE_URL");
                        json.put("id", lineCardNo);
                        json.put("name", lineName);
                        json.put("reason", BUSINESS_NAME);
                        json.put("reasonId", businessCode);
                        LOG_START_TIME = DateTimeUtil.getTimestamp();//请求开始时间戳
                        isCall = true;
                        body = HttpSendUtil.sendHttpsPostJson(lostPromiseUrl, headMap, json.toString(), "UTF-8");
                        if (body != null && body.indexOf("<html>") == -1) {
                            LOG_RESPONSE_STATUS = 1;
                            /*解析数据*/
                            JSONObject bodyJson = JSONObject.parseObject(body);
                            String code = bodyJson.getString("code");
                            LOG_RETURN_ID = bodyJson.getString("logNo");
                            if (code.equals("01")) {
                                String result = bodyJson.getString("data");
                                JSONArray elementArr = JSONArray.parseArray(result);
                                if(elementArr != null && elementArr.size() >= 1){
                                    Map<String,Object> lostPromiseMap = (Map)elementArr.get(0);
                                    String caseNo = (String)lostPromiseMap.get("caseNo");
                                    msg = "姓名："+lineName+"；身份证号："+lineCardNo+"；判决文书号："+caseNo+"；您已被平潭县人民法院列入失信被执行人名单，";
                                    msg += "申请事项被纳入联合奖惩范围，如有异议，请联系法院申请处理；法院联系人：林法官0591-24390070";
                                    isLostPromise = true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("失信人接口调用出错");
            log.error(e.getMessage(), e);
        }finally {
            if(isCall){//6失信人员接口
                //callLogService.save(LOG_START_TIME,json.toJSONString(), LOG_RESPONSE_STATUS,body,6);
                callLogService.save(LOG_START_TIME,json.toJSONString(), LOG_RESPONSE_STATUS,body,6
                        ,LOG_RETURN_ID,BUSINESS_NAME,businessCode,lineName,"");
            }
        }
        returnMap.put("msg", msg);
        returnMap.put("isLostPromise",isLostPromise);
        log.info("失信人接口查询成功：returnMap = "+returnMap);
        return returnMap;
    }

    /**
     * 描述:差评数据即时发送短信提醒
     *
     * @author Madison You
     * @created 2020/11/23 10:51:00
     * @param
     * @return
     */
    @Override
    public void sendSmsNagetiveEvaluate(String recordId, String eval, String evaluateTime) {
        Map<String, Object> callMap = dao.getByJdbc("T_CKBS_QUEUERECORD", new String[]{"RECORD_ID"},
                new Object[]{recordId});
        String exeId = (String) callMap.get("EXE_ID");
        if (StringUtils.isNotEmpty(exeId)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT D.DEPART_NAME FROM JBPM6_EXECUTION A LEFT JOIN T_WSBS_SERVICEITEM B ");
            sql.append(" ON A.ITEM_CODE = B.ITEM_CODE ");
            sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = B.ZBCS_ID ");
            sql.append(" WHERE A.EXE_ID = ? ");
            Map<String,Object> deptMap = dao.getByJdbc(sql.toString(), new Object[]{exeId});
            if (deptMap != null) {
                /*获取短信管理员*/
                String departName = (String) deptMap.get("DEPART_NAME");
                StringBuffer mobiles = new StringBuffer();
                List<Map<String, Object>> adminAuditList = dao.getAllByJdbc("T_CKBS_EVALUATE_AUDITOR",
                        new String[]{"AUDITOR_PERMISSION"}, new Object[]{"admin"});
                if (adminAuditList != null && !adminAuditList.isEmpty()) {
                    for (Map<String, Object> map : adminAuditList) {
                        String pjshyPhone = (String) map.get("AUDITOR_PJSHY_PHONE");
                        String ywblyPhone = (String) map.get("AUDITOR_YWBLY_NPHONE");
                        if (StringUtils.isNotEmpty(pjshyPhone)) {
                            mobiles.append(pjshyPhone).append(",");
                        }
                        if (StringUtils.isNotEmpty(ywblyPhone)) {
                            mobiles.append(ywblyPhone).append(",");
                        }
                    }
                }
                if (StringUtils.isNotEmpty(departName)) {
                    /*获取各部门发送短信人员*/
                    StringBuffer auditSql = new StringBuffer();
                    auditSql.append(" SELECT * FROM T_CKBS_EVALUATE_AUDITOR WHERE AUDITOR_PERMISSION = 'normal' ");
                    auditSql.append(" AND AUDITOR_DEPART_NAME LIKE '%").append(departName).append("%' ");
                    List<Map<String,Object>> auditList = dao.findBySql(auditSql.toString(), null, null);
                    if (auditList != null && !auditList.isEmpty()) {
                        for (Map<String, Object> auditMap : auditList) {
                            String pjshyPhone = (String) auditMap.get("AUDITOR_PJSHY_PHONE");
                            String ywblyPhone = (String) auditMap.get("AUDITOR_YWBLY_NPHONE");
                            if (StringUtils.isNotEmpty(pjshyPhone)) {
                                mobiles.append(pjshyPhone).append(",");
                            }
                            if (StringUtils.isNotEmpty(ywblyPhone)) {
                                mobiles.append(ywblyPhone).append(",");
                            }
                        }
                    }
                }
                String mobilesStr = mobiles.substring(0, mobiles.length() - 1);
                Map<String, Object> resultMap;
                try {
                    resultMap = SmsSendUtil.sendSms(exeId + "," + evaluateTime, mobilesStr, SMS_SEND_TEMPLATEID);
                    if (resultMap != null) {
                        String resultcode = resultMap.get("resultcode").toString();
                        StringBuffer logInfo = new StringBuffer();
                        if (Objects.equals(resultcode, "0")) {
                            log.info(logInfo.append("申报号为").append(exeId).
                                    append("的办件发送短信成功,手机号码为：").append(mobilesStr));
                        } else {
                            logInfo.append("申报号为").append(exeId).append("的办件发送短信失败，失败信息为：")
                                    .append(JSON.toJSONString(resultMap)).append("，手机号码为").append(mobilesStr);
                            log.info(logInfo);
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
            
            }
        }
    }
}
