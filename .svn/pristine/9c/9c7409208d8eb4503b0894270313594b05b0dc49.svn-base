/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.platform.evaluate.dao.EvaluateDao;
import net.evecom.platform.evaluate.service.EvaluateService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.evaluate.utils.EvaluateCommonUtil;
import net.evecom.platform.evaluate.utils.Md5Util;
import net.evecom.platform.evaluate.utils.SM4;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.call.service.NewCallService;

/**
 * 描述 好差评系统评价信息Service
 * 
 * @author Luffy Cai
 * @version 1.0
 * @created 2021年12月15日 上午10:43:15
 */
@SuppressWarnings("rawtypes")
@Service("evaluateService")
public class EvaluateServiceImpl extends BaseServiceImpl implements EvaluateService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(EvaluateServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private EvaluateDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/27 11:18:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;
    
    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/27 11:18:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;
    

    /**
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 接口配置ID 好差评系统评价信息配置ID
     */
    public static final String INTER_CODE_EVALUATE = "EVALUATE";
    /**
     * 接口配置ID 好差评系统评价信息配置ID 二维码和短信
     */
    public static final String INTER_CODE_EVALUATEEXE = "EVALUATEEXE";

    /**
     * 接口配置ID 好差评系统评价信息配置ID
     */
    public static final String INTER_CODE_EVALUATE_THIRD = "EVALUATETHIRD";

    /**
     * 接口配置ID 好差评系统评价信息配置ID
     */
    public static final String INTER_CODE_EVALUATE_SUPPLE = "EVALUATESUPPLE";

    /**
     * 推送好差评系统评价信息(平板评价方式)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void pushEvaluationData() {
        List<Map<String, Object>> evaluateList = getEvaluateList();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-平板评价信息推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushEvaluateData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;
            }
        }
    }
    
    /**
     * 推送好差评系统评价信息(批量件)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void pushEvaluationDataPLJ() {
        //获取批量件字典开关
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchEvaluateTurn");
        if(!"开".equals(switchTurn)){
            return;
        }
        List<Map<String, Object>> evaluateList = getEvaluateListPLJ();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-批量件信息推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushEvaluateDataPLJ(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;
            }
        }
    }

    /**
     * 推送好差评系统评价信息(按键器评价方式)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void pushEvaluationDataByKey() {
        List<Map<String, Object>> evaluateList = getEvaluateByKeyList();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-按键器评价数据推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushKeyEvaluateData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;
            }
        }
    }

    /**
     * 推送好差评系统评价信息(平板评价方式)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void pushBadEvaluationData() {
        List<Map<String, Object>> evaluateList = getBadEvaluateList();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-平板评价信息推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushEvaluateData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;

            }
        }
    }

    /**
     * 推送好差评系统评价信息(按键器评价方式)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void pushBadEvaluationDataByKey() {
        List<Map<String, Object>> evaluateList = getBadEvaluateByKeyList();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-按键器评价数据推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushKeyEvaluateData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;
            }
        }
    }

    /**
     *
     * @Description 办件评价数据上报（二维码+短信）
     * @author Nero Wang
     * @date 2021年5月14日 void
     */
    @Override
    public void pushEvaluationDataByExe() {
        List<Map<String, Object>> evaluateList = getEvaluateByExeList();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-办件评价数据上报（二维码+短信）推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushEvaluateExeData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;
            }
        }
    }

    /**
     * @Description 办件评价数据上报（二维码+短信）
     * @author Nero Wang
     * @date 2021年5月14日 void
     */
    @Override
    public void pushBadEvaluationDataByExe() {
        List<Map<String, Object>> evaluateList = getBadEvaluateByExeList();
        if (null != evaluateList && evaluateList.size() > 0) {
            log.info("好差评-办件评价数据上报（二维码+短信）推送数据量为：:" + evaluateList.size() + "条");
            for (Map<String, Object> evaluate : evaluateList) {
                int index = 1;
                if (index < 100) {
                    try {
                        this.pushEvaluateExeData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
                index++;
            }
        }
    }

    private List<Map<String, Object>> getBadEvaluateByExeList() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.EVAL_ID,t.EVAL_LEVEL,t.EVAL_CONTENT,t.EVAL_TIME,b.EXE_ID,b.CREATE_TIME,b.BSYHLX,");
        sql.append(" b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH,b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,");
        sql.append(" b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME ,decode(t.EVAL_CHANNEL,1,3,2,7) as PF");
        sql.append(" from JBPM6_EXECUTION_EVALUATE t ");
        sql.append(" left join JBPM6_EXECUTION b  on t.EXE_ID = b.EXE_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM c  on b.ITEM_CODE = c.ITEM_CODE ");
        sql.append(" where c.swb_item_id is not null  and b.EXE_ID is not null ");
        sql.append(" and t.PUSH_STATUS = '0' and t.EVAL_LEVEL in ('1','2') ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String EVAL_TIME = map.get("EVAL_TIME") == null ? "" : map.get("EVAL_TIME").toString();
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String EVAL_CONTENT = map.get("EVAL_CONTENT") == null ? "" : map.get("EVAL_CONTENT").toString();
                String EVA_LEVEL = map.get("EVA_LEVEL") == null ? "" : map.get("EVA_LEVEL").toString();
                String PF = map.get("PF") == null ? "" : map.get("PF").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
                map.put("SQRMC", SQRMC);
                map.put("EVA_LEVEL", EVA_LEVEL);
                map.put("EVA_VALUA", EVAL_CONTENT);
                map.put("EVA_APPREISALD", "");
                map.put("EVA_NUM", "");
                map.put("JBR_NAME", JBR_NAME);
                map.put("PF", PF);
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                    map.put("SQRSJH", SQRSJH);
                    map.put("SQRSFZ", SQRSFZ);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(EVAL_TIME);
                    date2 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                boolean before = date1.before(date2);
                if (before) {// 服务时间大于评价时间
                    Date afterDate = new Date(date1.getTime() + 1200000);
                    EVAL_TIME = sdf.format(afterDate);
                }
                map.put("EVALUATE_TIME", EVAL_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append("select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null ");
                    sql3.append(" order by t.end_time desc");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }
                // 返回证件类型
                SQRZJLX = getUserPageType(SQRZJLX);
                if (JBR_ZJLX != null) {
                    JBR_ZJLX = getUserPageType(JBR_ZJLX);
                }
                map.put("SQRZJLX", SQRZJLX);
                map.put("JBR_ZJLX", JBR_ZJLX);
                // 自定义字段customFields 规则：无【评价器编号】,【业务流水号】
                map.put("CUSTOMFIELDS", "A01," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001");
            }
        }
        return list;
    }

    private List<Map<String, Object>> getEvaluateByExeList() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.EVAL_ID,t.EVAL_LEVEL,t.EVAL_CONTENT,t.EVAL_TIME,b.EXE_ID,b.CREATE_TIME,b.BSYHLX,");
        sql.append(" b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH,b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,");
        sql.append(" b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME,decode(t.EVAL_CHANNEL,1,3,2,7) as PF ");
        sql.append(" from JBPM6_EXECUTION_EVALUATE t ");
        sql.append(" left join JBPM6_EXECUTION b  on t.EXE_ID = b.EXE_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM c  on b.ITEM_CODE = c.ITEM_CODE ");
        sql.append(" where c.swb_item_id is not null  and b.EXE_ID is not null ");
        sql.append(" and t.PUSH_STATUS = '0' and t.EVAL_LEVEL in ('3','4','5') ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String EVAL_TIME = map.get("EVAL_TIME") == null ? "" : map.get("EVAL_TIME").toString();
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String EVAL_CONTENT = map.get("EVAL_CONTENT") == null ? "" : map.get("EVAL_CONTENT").toString();
                String EVA_LEVEL = map.get("EVA_LEVEL") == null ? "" : map.get("EVA_LEVEL").toString();
                String PF = map.get("PF") == null ? "" : map.get("PF").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
                map.put("SQRMC", SQRMC);
                map.put("EVA_LEVEL", EVA_LEVEL);
                map.put("EVA_VALUA", EVAL_CONTENT);
                map.put("EVA_APPREISALD", "");
                map.put("EVA_NUM", "");
                map.put("JBR_NAME", JBR_NAME);
                map.put("PF", PF);
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                    map.put("SQRSJH", SQRSJH);
                    map.put("SQRSFZ", SQRSFZ);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(EVAL_TIME);
                    date2 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                boolean before = date1.before(date2);
                if (before) {// 服务时间大于评价时间
                    Date afterDate = new Date(date1.getTime() + 1200000);
                    EVAL_TIME = sdf.format(afterDate);
                }
                map.put("EVALUATE_TIME", EVAL_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append("select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null ");
                    sql3.append(" order by t.end_time desc");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }
                // 返回证件类型
                SQRZJLX = getUserPageType(SQRZJLX);
                if (JBR_ZJLX != null) {
                    JBR_ZJLX = getUserPageType(JBR_ZJLX);
                }
                map.put("SQRZJLX", SQRZJLX);
                map.put("JBR_ZJLX", JBR_ZJLX);
                // 自定义字段customFields 规则：无【评价器编号】,【业务流水号】
                map.put("CUSTOMFIELDS", "A01," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001");
                // 判断是否是追加评价
                StringBuffer sql4 = new StringBuffer();
                ArrayList<Object> array = new ArrayList<>();
                array.add(EXE_ID);
                sql4.append("select q.* from T_CKBS_QUEUERECORD q where q.exe_id=?");
                List<Map<String, Object>> callList = dao.findBySql(sql4.toString(), array.toArray(), null);
                if (callList.size() > 0) {
                    Map<String, Object> callMap = callList.get(0);
                    String EVALUATE_UNID = callMap.get("EVALUATE_UNID") == null ? ""
                            : callMap.get("EVALUATE_UNID").toString();
                    if (StringUtils.isEmpty(EVALUATE_UNID)) {
                        map.put("EVALUATETYPE", "1");
                    } else {
                        map.put("EVALUATETYPE", "2");
                    }
                } else {
                    map.put("EVALUATETYPE", "2");
                }
            }
        }
        return list;
    }

    /**
     * @Description 好差评评价信息上报(平板评价方式)
     * @author Luffy Cai
     * @date 2020年7月30日
     * @param evaluate
     */
    private void pushEvaluateData(Map<String, Object> evaluate) {
        log.info("好差评-评价数据上报信息：" + evaluate);
        String xmlStr = this.getEvaluateXml(evaluate);
        String appCode = "a325f2b70f8640b381c14321c319ac59";// 密钥
        String md5 = Md5Util.getMD5(appCode);// 对密钥进行MD5
        String str = null;
        String infoId = (String) evaluate.get("INFO_ID");
        String recordId = (String) evaluate.get("RECORD_ID");
        try {
            str = new SM4().encode(xmlStr, md5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        xmlStr = str.replaceAll("[\\n\\r]", "");
        Map<String, Object> result = EvaluateCommonUtil.pushEvaluateData(xmlStr);
        String code = (String) result.get("code");
        String exeId = (String) evaluate.get("EXE_ID");
        Map<String, Object> info = new HashMap<String, Object>();
        Map<String, Object> evaInfo = new HashMap<String, Object>();
        if ("200".equals(code)) {
            // 保存好差评明细信息
            String evaluateUnid = (String) result.get("evaluateUnid");
            info.put("INFO_ID", evaluate.get("INFO_ID"));
            info.put("PUSH_STATUS", "1");
            info.put("EVALUATE_UNID", evaluateUnid);
            dao.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", infoId);
            evaInfo.put("EVA_PUSH_STATUS", "1");
            evaInfo.put("EVALUATE_UNID", evaluateUnid);
            dao.saveOrUpdate(evaInfo, "T_CKBS_QUEUERECORD", recordId);
            log.info("办件申报号：" + exeId + "调用好差评系统评价信息上报接口成功，返回信息：" + result);
        } else {
            String resultData = (String) result.get("resultData");
            info.put("INFO_ID", evaluate.get("INFO_ID"));
            info.put("PUSH_STATUS", "2");
            dao.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", infoId);
            evaInfo.put("EVA_PUSH_STATUS", "2");
            dao.saveOrUpdate(evaInfo, "T_CKBS_QUEUERECORD", recordId);
            // 保存数据上报日志
            Map<String, Object> evaLog = new HashMap<String, Object>();
            evaLog.put("EXE_ID", exeId);
            evaLog.put("PUST_RESULT", resultData);
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            evaLog.put("CREATE_TIME", createTime);
            dao.saveOrUpdate(evaLog, "T_CKBS_EVALUATELOG", null);
            log.error("办件申报号：" + exeId + "调用好差评系统评价信息上报接口失败，返回信息：" + resultData + "错误码：" + code);
        }
    }

    /**
     * 
     * 描述 好差评-评价数据上报
     * 
     * @author Yanisin Shi
     * @param evaluate
     * @create 2021年6月10日
     */
    private void pushEvaluateExeData(Map<String, Object> evaluate) {
        log.info("好差评-评价数据上报信息：" + evaluate);
        String xmlStr = this.getEvaluateXmlExe(evaluate);
        String appCode = "a325f2b70f8640b381c14321c319ac59";// 密钥
        String md5 = Md5Util.getMD5(appCode);// 对密钥进行MD5
        String str = null;
        String EVAL_ID = (String) evaluate.get("EVAL_ID");
        try {
            str = new SM4().encode(xmlStr, md5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        xmlStr = str.replaceAll("[\\n\\r]", "");
        Map<String, Object> result = EvaluateCommonUtil.pushEvaluateData(xmlStr);
        String code = (String) result.get("code");
        String exeId = (String) evaluate.get("EXE_ID");
        Map<String, Object> info = new HashMap<String, Object>();

        if ("200".equals(code)) {
            // 保存好差评明细信息
            info.put("EVAL_ID", evaluate.get("EVAL_ID"));
            info.put("PUSH_STATUS", "1");
            dao.saveOrUpdate(info, "JBPM6_EXECUTION_EVALUATE", EVAL_ID);
            log.info("办件申报号：" + exeId + "调用好差评系统评价信息上报接口成功，返回信息：" + result);
        } else {
            String resultData = (String) result.get("resultData");
            info.put("EVAL_ID", evaluate.get("EVAL_ID"));
            info.put("PUSH_STATUS", "2");
            dao.saveOrUpdate(info, "JBPM6_EXECUTION_EVALUATE", EVAL_ID);
            // 保存数据上报日志
            Map<String, Object> evaLog = new HashMap<String, Object>();
            evaLog.put("EXE_ID", exeId);
            evaLog.put("PUST_RESULT", resultData);
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            evaLog.put("CREATE_TIME", createTime);
            dao.saveOrUpdate(evaLog, "T_CKBS_EVALUATELOG", null);
            log.error("办件申报号：" + exeId + "调用好差评系统评价信息上报接口失败，返回信息：" + resultData + "错误码：" + code);
        }
    }

    /**
     * @Description 好差评评价信息上报(按键评价方式)
     * @author Luffy Cai
     * @date 2020年7月30日
     * @param evaluate
     */
    private void pushKeyEvaluateData(Map<String, Object> evaluate) {
        log.info("好差评-评价数据上报信息：" + evaluate);
        String xmlStr = this.getEvaluateXml(evaluate);
        String appCode = "a325f2b70f8640b381c14321c319ac59";// 密钥
        String md5 = Md5Util.getMD5(appCode);// 对密钥进行MD5
        String str = null;
        String recordId = (String) evaluate.get("RECORD_ID");
        try {
            str = new SM4().encode(xmlStr, md5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        xmlStr = str.replaceAll("[\\n\\r]", "");
        Map<String, Object> result = EvaluateCommonUtil.pushEvaluateData(xmlStr);
        String code = (String) result.get("code");
        Map<String, Object> info = new HashMap<String, Object>();
        if ("200".equals(code)) {
            String evaluateUnid = (String) result.get("evaluateUnid");
            info.put("EVA_PUSH_STATUS", "1");
            info.put("EVALUATE_UNID", evaluateUnid);
            dao.saveOrUpdate(info, "T_CKBS_QUEUERECORD", recordId);
        } else {
            String exeId = (String) evaluate.get("EXE_ID");
            String resultData = (String) result.get("resultData");
            info.put("EVA_PUSH_STATUS", "2");
            dao.saveOrUpdate(info, "T_CKBS_QUEUERECORD", recordId);
            // 保存数据上报日志
            Map<String, Object> evaLog = new HashMap<String, Object>();
            evaLog.put("EXE_ID", exeId);
            evaLog.put("PUST_RESULT", resultData);
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            evaLog.put("CREATE_TIME", createTime);
            dao.saveOrUpdate(evaLog, "T_CKBS_EVALUATELOG", null);
            log.error("办件申报号：" + exeId + "调用好差评系统评价信息上报接口失败，返回信息：" + resultData + "错误码：" + code);
        }
    }

    /**
     * 
     * @Description 获取评价信息Xml
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param evaluate
     * @return String
     */
    private String getEvaluateXml(Map<String, Object> evaluate) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { INTER_CODE_EVALUATE });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("evaluate", evaluate);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        return xmlBuffer.toString();
    }
    
    /**
     * 
     * @Description 获取评价补报信息Xml
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param evaluate
     * @return String
     */
    private String getEvaluateSupplementXml(Map<String, Object> evaluate) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { INTER_CODE_EVALUATE_SUPPLE });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("evaluate", evaluate);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        return xmlBuffer.toString();
    }    
    

    private String getEvaluateXmlExe(Map<String, Object> evaluate) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { INTER_CODE_EVALUATEEXE });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("evaluate", evaluate);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        return xmlBuffer.toString();
    }

    /**
     * 
     * @Description 获取评价信息Xml
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param evaluate
     * @return String
     */
    private String getEvaluateXmlByThird(Map<String, Object> evaluate) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { INTER_CODE_EVALUATE_THIRD });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("evaluate", evaluate);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        return xmlBuffer.toString();
    }
    
    /**
     * 
     * @Description 获取评价信息列表(批量件)
     * @author Jason Lin
     * @date 2021年1月10日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getEvaluateListPLJ() {
        StringBuffer sql = new StringBuffer();
        //--查询需要上传省网的办件
        sql.append(" select b.EXE_ID,b.BSYHLX,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH,b.SQRMC,b.SQRZJLX,b.SQRSFZ, ");
        sql.append(" b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,b.PDH,b.PDHRQ,c.SWB_ITEM_CODE,c.ITEM_NAME ");
        sql.append(" from JBPM6_EXECUTION b join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE  ");
        sql.append(" where 1=1  ");
        sql.append(" and c.SWB_ITEM_CODE is not null ");
        sql.append(" and b.PDH is not null and b.PDHRQ is not null and b.PDH_PUSH_STATUS in (0,2) ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        boolean checkPassFlag = false;
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                map.put("DATE_LINE_NO", map.get("PDHRQ").toString() + map.get("PDH").toString() + "");
                StringBuffer sqlQuery = new StringBuffer(); 
                //--查询取号记录及评价记录
                sqlQuery.append(" select t.ROOM_NO,t.CUR_WIN,t.RECORD_ID,t.DATE_LINE_NO, ");
                sqlQuery.append(" d.INFO_ID,d.EVA_LEVEL,d.EVA_INFO,t.EVALUATETIME,t.CREATE_TIME ");
                sqlQuery.append(" from T_CKBS_QUEUERECORD t left join T_CKBS_EVALUATEINFO d ");
                sqlQuery.append(" on t.record_id = d.record_id where 1=1 ");
                sqlQuery.append(" and t.DATE_LINE_NO = ? ");
                Map<String, Object> mapQuery = dao.getByJdbc(sqlQuery.toString(), new Object[] {map.get("DATE_LINE_NO").toString()});
                if(mapQuery == null){
                    continue;
                }
                map.putAll(mapQuery);
                checkPassFlag =true;
            }
        }
        if(!checkPassFlag){
            list = null;
        }
        if (null != list && list.size() > 0 ) {
            for (Map<String, Object> map : list) {
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String EVALUATE_TIME = map.get("EVALUATETIME") == null ? "" : map.get("EVALUATETIME").toString();
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String EVA_INFO = map.get("EVA_INFO") == null ? "" : map.get("EVA_INFO").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString(); 
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
                map.put("SQRMC", SQRMC);
                map.put("JBR_NAME", JBR_NAME);
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                    map.put("SQRSJH", SQRSJH);
                    map.put("SQRSFZ", SQRSFZ);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                Date date2 = null;
                if(StringUtils.isEmpty(EVALUATE_TIME) || StringUtils.isEmpty(CREATE_TIME)){
                    continue;
                }
                try {
                    date1 = sdf.parse(EVALUATE_TIME);
                    date2 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                boolean before = date1.before(date2);
                if (before) {// 服务时间大于评价时间
                    Date afterDate = new Date(date1.getTime() + 1200000);
                    EVALUATE_TIME = sdf.format(afterDate);
                }
                map.put("EVALUATE_TIME", EVALUATE_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append(
                            "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }

                // 返回证件类型
                SQRZJLX = getUserPageType(SQRZJLX);
                if (JBR_ZJLX != null) {
                    JBR_ZJLX = getUserPageType(JBR_ZJLX);
                }
                map.put("SQRZJLX", SQRZJLX);
                map.put("JBR_ZJLX", JBR_ZJLX);
                if (StringUtils.isNotEmpty(EVA_INFO)) {
                    Map<String, Object> evaInfo = getEvaluateInfo(EVA_INFO);
                    String EVA_APPREISALD = evaInfo.get("appraisald") == null ? ""
                            : evaInfo.get("appraisald").toString();
                    String EVA_NUM = evaInfo.get("appraisaldnum") == null ? ""
                            : evaInfo.get("appraisaldnum").toString();
                    String EVA_VALUA = evaInfo.get("writingevalua") == null ? ""
                            : evaInfo.get("writingevalua").toString();
                    map.put("EVA_APPREISALD", EVA_APPREISALD);
                    map.put("EVA_NUM", EVA_NUM);
                    map.put("EVA_VALUA", EVA_VALUA);
                } else {
                    map.put("EVA_APPREISALD", "");
                    map.put("EVA_NUM", "");
                    map.put("EVA_VALUA", "");
                }
                // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
                String customFields = "";
                String ROOM_NO = map.get("ROOM_NO") == null ? "" : map.get("ROOM_NO").toString();
                String CUR_WIN = map.get("CUR_WIN") == null ? "" : map.get("CUR_WIN").toString();
                customFields = ROOM_NO + CUR_WIN + "," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
                map.put("CUSTOMFIELDS", customFields);
            }
        }
        return list;
    }
    
    /**
     * @Description 好差评评价信息上报(批量件上传)
     * @author Luffy Cai
     * @date 2020年7月30日
     * @param evaluate
     */
    private void pushEvaluateDataPLJ(Map<String, Object> evaluate) {
        log.info("好差评-批量件评价数据上报信息：" + evaluate);
        String xmlStr = this.getEvaluateXml(evaluate);
        String appCode = "a325f2b70f8640b381c14321c319ac59";// 密钥
        String md5 = Md5Util.getMD5(appCode);// 对密钥进行MD5
        String str = null;
        try {
            str = new SM4().encode(xmlStr, md5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        xmlStr = str.replaceAll("[\\n\\r]", "");
        Map<String, Object> result = EvaluateCommonUtil.pushEvaluateData(xmlStr);
        String code = (String) result.get("code");
        String exeId = (String) evaluate.get("EXE_ID");
        String uploadStatus = "0";
        String updateSql = " update JBPM6_EXECUTION m set m.PDH_PUSH_STATUS = ? where m.exe_id = ? ";
        if ("200".equals(code)) {
            uploadStatus = "1";
        } else {
            uploadStatus = "2";
        }
        this.dao.executeSql(updateSql, new Object[] {uploadStatus,exeId});
        Map<String, Object> saveMap = new HashMap<String, Object>();
        saveMap.put("send_map", evaluate.toString());
        saveMap.put("exe_id", exeId);
        saveMap.put("upload_status", uploadStatus);
        saveMap.put("update_time", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        saveMap.put("upload_msg_json", result.toString());
        //--查询该申报号是否有记录 
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append(" select * from T_CKBS_EVALUATE_RECORDPLJ ");
        sqlQuery.append(" where 1=1 ");
        sqlQuery.append(" and exe_id = ? ");
        Map<String, Object> mapQuery = dao.getByJdbc(sqlQuery.toString(), new Object[] {exeId});
        if(mapQuery == null){
            dao.saveOrUpdate(saveMap, "T_CKBS_EVALUATE_RECORDPLJ", null);
        }else{
            String updateSql1 = " update T_CKBS_EVALUATE_RECORDPLJ set send_map = ?,upload_status = ?,update_time = ?,upload_msg_json = ?  where exe_id = ? ";
            this.dao.executeSql(updateSql1, new Object[] {saveMap.get("send_map"), saveMap.get("upload_status"),saveMap.get("update_time"),saveMap.get("upload_msg_json"),exeId});
        }
    }
    
    

    /**
     * 
     * @Description 获取评价信息列表
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getEvaluateList() {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select t.ROOM_NO,t.CUR_WIN,t.RECORD_ID,d.INFO_ID,d.EVA_LEVEL,d.EVA_INFO,t.EVALUATETIME,b.EXE_ID,t.CREATE_TIME,b.BSYHLX,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH, ");
        sql.append(
                " b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME from T_CKBS_QUEUERECORD t ");
        sql.append(" left join JBPM6_EXECUTION b on t.EXE_ID = b.EXE_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE ");
        sql.append(" left join T_CKBS_EVALUATEINFO d on t.record_id = d.record_id ");
        sql.append(" where 1=1 and c.swb_item_id is not null and d.info_id is not null ");
        sql.append(" and d.EVA_LEVEL is not null and b.EXE_ID is not null ");
        sql.append(" and d.PUSH_STATUS = '0' and t.EVA_PUSH_STATUS='0' and t.EVALUATE_UNID is null ");
        sql.append(" and t.EVALUATETIME>'2021-03-01 00:00:00' and t.evaluate in(1,2,3) and d.EVA_LEVEL in(5,4,3)");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String EVALUATE_TIME = map.get("EVALUATETIME") == null ? "" : map.get("EVALUATETIME").toString();
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String EVA_INFO = map.get("EVA_INFO") == null ? "" : map.get("EVA_INFO").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
                map.put("SQRMC", SQRMC);
                map.put("JBR_NAME", JBR_NAME);
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                    map.put("SQRSJH", SQRSJH);
                    map.put("SQRSFZ", SQRSFZ);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(EVALUATE_TIME);
                    date2 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                boolean before = date1.before(date2);
                if (before) {// 服务时间大于评价时间
                    Date afterDate = new Date(date1.getTime() + 1200000);
                    EVALUATE_TIME = sdf.format(afterDate);
                }
                map.put("EVALUATE_TIME", EVALUATE_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append(
                            "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }

                // 返回证件类型
                SQRZJLX = getUserPageType(SQRZJLX);
                if (JBR_ZJLX != null) {
                    JBR_ZJLX = getUserPageType(JBR_ZJLX);
                }
                map.put("SQRZJLX", SQRZJLX);
                map.put("JBR_ZJLX", JBR_ZJLX);
                if (StringUtils.isNotEmpty(EVA_INFO)) {
                    Map<String, Object> evaInfo = getEvaluateInfo(EVA_INFO);
                    String EVA_APPREISALD = evaInfo.get("appraisald") == null ? ""
                            : evaInfo.get("appraisald").toString();
                    String EVA_NUM = evaInfo.get("appraisaldnum") == null ? ""
                            : evaInfo.get("appraisaldnum").toString();
                    String EVA_VALUA = evaInfo.get("writingevalua") == null ? ""
                            : evaInfo.get("writingevalua").toString();
                    map.put("EVA_APPREISALD", EVA_APPREISALD);
                    map.put("EVA_NUM", EVA_NUM);
                    map.put("EVA_VALUA", EVA_VALUA);
                } else {
                    map.put("EVA_APPREISALD", "");
                    map.put("EVA_NUM", "");
                    map.put("EVA_VALUA", "");
                }
                // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
                String customFields = "";
                String ROOM_NO = map.get("ROOM_NO") == null ? "" : map.get("ROOM_NO").toString();
                String CUR_WIN = map.get("CUR_WIN") == null ? "" : map.get("CUR_WIN").toString();
                customFields = ROOM_NO + CUR_WIN + "," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
                map.put("CUSTOMFIELDS", customFields);
                Map<String, Object> evaInfo = new HashMap<String, Object>();
                evaInfo.put("CUSTOMFIELDS", customFields);
                String RECORD_ID = map.get("RECORD_ID") == null ? "" : map.get("RECORD_ID").toString();
                dao.saveOrUpdate(evaInfo, "T_CKBS_QUEUERECORD", RECORD_ID);
            }
        }
        return list;
    }

    /**
     * 
     * @Description 获取评价信息列表
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getBadEvaluateList() {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select t.ROOM_NO,t.CUR_WIN,t.RECORD_ID,d.INFO_ID,d.EVA_LEVEL,d.EVA_INFO,t.EVALUATETIME,b.EXE_ID,b.CREATE_TIME,b.BSYHLX,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH, ");
        sql.append(
                " b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME from T_CKBS_QUEUERECORD t ");
        sql.append(" left join JBPM6_EXECUTION b on t.EXE_ID = b.EXE_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE ");
        sql.append(" left join T_CKBS_EVALUATEINFO d on t.record_id = d.record_id ");
        sql.append(" where 1=1 and c.swb_item_id is not null and d.info_id is not null ");
        sql.append(" and d.EVA_LEVEL is not null and b.EXE_ID is not null");
        sql.append(
                " and d.PUSH_STATUS = '0' and t.EVA_PUSH_STATUS='0' and t.EVALUATE_UNID is null and t.EVALUATETIME>'2021-03-01 00:00:00' and t.EVALUATE in(0,5)");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String EVALUATE_TIME = map.get("EVALUATETIME") == null ? "" : map.get("EVALUATETIME").toString();
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String EVA_INFO = map.get("EVA_INFO") == null ? "" : map.get("EVA_INFO").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
                map.put("SQRMC", SQRMC);
                map.put("JBR_NAME", JBR_NAME);
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    map.put("SQRSJH", SQRSJH);

                    String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                    map.put("SQRSFZ", SQRSFZ);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(EVALUATE_TIME);
                    date2 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                boolean before = date1.before(date2);
                if (before) {// 服务时间大于评价时间
                    Date afterDate = new Date(date1.getTime() + 1200000);
                    EVALUATE_TIME = sdf.format(afterDate);
                }
                map.put("EVALUATE_TIME", EVALUATE_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append(
                            "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }

                // 返回证件类型
                SQRZJLX = getUserPageType(SQRZJLX);
                if (JBR_ZJLX != null) {
                    JBR_ZJLX = getUserPageType(JBR_ZJLX);
                }
                map.put("SQRZJLX", SQRZJLX);
                map.put("JBR_ZJLX", JBR_ZJLX);
                if (StringUtils.isNotEmpty(EVA_INFO)) {
                    Map<String, Object> evaInfo = getEvaluateInfo(EVA_INFO);
                    String EVA_APPREISALD = evaInfo.get("appraisald") == null ? ""
                            : evaInfo.get("appraisald").toString();
                    String EVA_NUM = evaInfo.get("appraisaldnum") == null ? ""
                            : evaInfo.get("appraisaldnum").toString();
                    String EVA_VALUA = evaInfo.get("writingevalua") == null ? ""
                            : evaInfo.get("writingevalua").toString();
                    map.put("EVA_APPREISALD", EVA_APPREISALD);
                    map.put("EVA_NUM", EVA_NUM);
                    map.put("EVA_VALUA", EVA_VALUA);
                } else {
                    map.put("EVA_APPREISALD", "");
                    map.put("EVA_NUM", "");
                    map.put("EVA_VALUA", "");
                }
                // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
                String customFields = "";
                String ROOM_NO = map.get("ROOM_NO") == null ? "" : map.get("ROOM_NO").toString();
                String CUR_WIN = map.get("CUR_WIN") == null ? "" : map.get("CUR_WIN").toString();
                customFields = ROOM_NO + CUR_WIN + "," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
                map.put("CUSTOMFIELDS", customFields);
                Map<String, Object> evaInfo = new HashMap<String, Object>();
                evaInfo.put("CUSTOMFIELDS", customFields);
                String RECORD_ID = map.get("RECORD_ID") == null ? "" : map.get("RECORD_ID").toString();
                dao.saveOrUpdate(evaInfo, "T_CKBS_QUEUERECORD", RECORD_ID);
            }
        }
        return list;
    }

    /**
     * 
     * @Description 获取按键评价器评价信息
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getEvaluateByKeyList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(
                " select t.ROOM_NO,t.CUR_WIN,t.RECORD_ID,t.EVALUATE,t.EVALUATETIME,b.EXE_ID,b.CREATE_TIME,b.BSYHLX,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH, ");
        sql.append(
                " b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME from T_CKBS_QUEUERECORD t ");
        sql.append(" left join JBPM6_EXECUTION b on t.EXE_ID = b.EXE_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE where 1=1");
        sql.append(" and t.EVALUATE is not null and b.EXE_ID is not null");
        sql.append(" and c.swb_item_id is not null");
        sql.append(
                " and t.EVALUATETYPE ='2' and t.EVA_PUSH_STATUS ='0' and t.EVALUATE_UNID is null and t.EVALUATETIME>'2021-03-01 00:00:00' and t.EVALUATE in(1,2,3) ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            String EVALUATE_TIME = map.get("EVALUATETIME") == null ? "" : map.get("EVALUATETIME").toString();
            String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
            String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
            String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
            String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
            String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
            // String EVA_INFO = map.get("EVA_INFO") == null ? "" :
            // map.get("EVA_INFO").toString();
            String EVALUATE = map.get("EVALUATE") == null ? "" : map.get("EVALUATE").toString();
            String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
            String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
            SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
            map.put("SQRMC", SQRMC);
            String EVA_LEVEL = "";// 评价等级
            String EVA_VALUA = "";// 文字描述
            ArrayList<Object> params = new ArrayList<>();
            params.add(EXE_ID);
            StringBuffer sql2 = new StringBuffer();
            sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
            List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
            List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
            if (decryptList != null && decryptList.size() > 0) {
                Map<String, Object> userInfo = decryptList.get(0);
                String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                if (StringUtils.isEmpty(SQRSJH)) {
                    SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                }
                String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                map.put("SQRSJH", SQRSJH);
                map.put("SQRSFZ", SQRSFZ);
                map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
            }
            map.put("JBR_NAME", JBR_NAME);
            if ("3".equals(EVALUATE)) {
                EVA_LEVEL = "5";
            } else if ("2".equals(EVALUATE)) {
                EVA_LEVEL = "4";
            } else if ("1".equals(EVALUATE)) {
                EVA_LEVEL = "3";
            } else if ("0".equals(EVALUATE)) {
                EVA_LEVEL = "2";
                EVA_VALUA = "办事效率不高";
            } else if ("5".equals(EVALUATE)) {
                EVA_LEVEL = "1";
                EVA_VALUA = "办事效率不高";
            }
            map.put("EVA_LEVEL", EVA_LEVEL);
            map.put("EVA_APPREISALD", "");
            map.put("EVA_NUM", "");
            map.put("EVA_VALUA", EVA_VALUA);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(EVALUATE_TIME);
                date2 = sdf.parse(CREATE_TIME);
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
            boolean before = date1.before(date2);
            if (before) {// 服务时间大于评价时间
                Date afterDate = new Date(date1.getTime() + 600000);
                EVALUATE_TIME = sdf.format(afterDate);
            }
            map.put("EVALUATE_TIME", EVALUATE_TIME);
            // 判断办件环节名称和办理状态
            String TASK_NODENAME = "";
            if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                ArrayList<Object> task = new ArrayList<>();
                task.add(EXE_ID);
                StringBuffer sql3 = new StringBuffer();
                sql3.append(
                        "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                if (taskList.size() > 0) {// 在办状态
                    TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                            : taskList.get(0).get("TASK_NODENAME").toString();
                } else {
                    TASK_NODENAME = "开始";
                }
                if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                    CUR_STEPNAMES = TASK_NODENAME;
                }
                map.put("PROSTATUS", "2");
                map.put("END_TIME", "");
                map.put("CUR_STEPNAMES", CUR_STEPNAMES);
            } else {
                map.put("PROSTATUS", "3");
                map.put("END_TIME", END_TIME);
                map.put("CUR_STEPNAMES", "办结");
            }

            // 返回证件类型
            SQRZJLX = getUserPageType(SQRZJLX);
            if (JBR_ZJLX != null) {
                JBR_ZJLX = getUserPageType(JBR_ZJLX);
            }
            map.put("SQRZJLX", SQRZJLX);
            map.put("JBR_ZJLX", JBR_ZJLX);

            // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
            String customFields = "";
            String ROOM_NO = map.get("ROOM_NO") == null ? "" : map.get("ROOM_NO").toString();
            String CUR_WIN = map.get("CUR_WIN") == null ? "" : map.get("CUR_WIN").toString();
            customFields = ROOM_NO + CUR_WIN + "," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
            map.put("CUSTOMFIELDS", customFields);
            Map<String, Object> evaInfo = new HashMap<String, Object>();
            evaInfo.put("CUSTOMFIELDS", customFields);
            String RECORD_ID = map.get("RECORD_ID") == null ? "" : map.get("RECORD_ID").toString();
            dao.saveOrUpdate(evaInfo, "T_CKBS_QUEUERECORD", RECORD_ID);
        }
        return list;
    }

    /**
     * 
     * @Description 获取按键评价器评价信息
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getBadEvaluateByKeyList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(
                " select t.ROOM_NO,t.CUR_WIN,t.RECORD_ID,t.EVALUATE,t.EVALUATETIME,b.EXE_ID,b.CREATE_TIME,b.BSYHLX,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH, ");
        sql.append(
                " b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME from T_CKBS_QUEUERECORD t ");
        sql.append(" left join JBPM6_EXECUTION b on t.EXE_ID = b.EXE_ID ");
        sql.append(" left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE where 1=1");
        sql.append(" and t.EVALUATE is not null and b.EXE_ID is not null");
        sql.append(" and c.SWB_ITEM_ID is not null");
        sql.append(
                " and t.EVALUATETYPE ='2' and t.EVA_PUSH_STATUS ='0' and t.EVALUATE_UNID is null and t.EVALUATETIME>'2021-03-01 00:00:00' and t.EVALUATE in(0,5) ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            String EVALUATE_TIME = map.get("EVALUATETIME") == null ? "" : map.get("EVALUATETIME").toString();
            String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
            String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
            String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
            String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
            String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
            // String EVA_INFO = map.get("EVA_INFO") == null ? "" :
            // map.get("EVA_INFO").toString();
            String EVALUATE = map.get("EVALUATE") == null ? "" : map.get("EVALUATE").toString();
            String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
            String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
            SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(");
            map.put("SQRMC", SQRMC);
            String EVA_LEVEL = "";// 评价等级
            String EVA_VALUA = "";// 文字描述
            ArrayList<Object> params = new ArrayList<>();
            params.add(EXE_ID);
            StringBuffer sql2 = new StringBuffer();
            sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
            List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
            List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
            if (decryptList != null && decryptList.size() > 0) {
                Map<String, Object> userInfo = decryptList.get(0);
                String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                String JBR_MOBILE = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                if (StringUtils.isEmpty(SQRSJH)) {
                    map.put("SQRSJH", JBR_MOBILE);
                } else {
                    map.put("SQRSJH", SQRSJH);
                }
                String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                map.put("SQRSFZ", SQRSFZ);
                map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : SQRSFZ);
            }
            map.put("JBR_NAME", JBR_NAME);
            if ("3".equals(EVALUATE)) {
                EVA_LEVEL = "5";
            } else if ("2".equals(EVALUATE)) {
                EVA_LEVEL = "4";
            } else if ("1".equals(EVALUATE)) {
                EVA_LEVEL = "3";
            } else if ("0".equals(EVALUATE)) {
                EVA_LEVEL = "2";
                EVA_VALUA = "办事效率不高";
            } else if ("5".equals(EVALUATE)) {
                EVA_LEVEL = "1";
                EVA_VALUA = "办事效率不高";
            }
            map.put("EVA_LEVEL", EVA_LEVEL);
            map.put("EVA_APPREISALD", "");
            map.put("EVA_NUM", "");
            map.put("EVA_VALUA", EVA_VALUA);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(EVALUATE_TIME);
                date2 = sdf.parse(CREATE_TIME);
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
            boolean before = date1.before(date2);
            if (before) {// 服务时间大于评价时间
                Date afterDate = new Date(date1.getTime() + 600000);
                EVALUATE_TIME = sdf.format(afterDate);
            }
            map.put("EVALUATE_TIME", EVALUATE_TIME);
            // 判断办件环节名称和办理状态
            String TASK_NODENAME = "";
            if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                ArrayList<Object> task = new ArrayList<>();
                task.add(EXE_ID);
                StringBuffer sql3 = new StringBuffer();
                sql3.append(
                        "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                if (taskList.size() > 0) {// 在办状态
                    TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                            : taskList.get(0).get("TASK_NODENAME").toString();
                } else {
                    TASK_NODENAME = "开始";
                }
                if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                    CUR_STEPNAMES = TASK_NODENAME;
                }
                map.put("PROSTATUS", "2");
                map.put("END_TIME", "");
                map.put("CUR_STEPNAMES", CUR_STEPNAMES);
            } else {
                map.put("PROSTATUS", "3");
                map.put("END_TIME", END_TIME);
                map.put("CUR_STEPNAMES", "办结");
            }

            // 返回证件类型
            SQRZJLX = getUserPageType(SQRZJLX);
            if (JBR_ZJLX != null) {
                JBR_ZJLX = getUserPageType(JBR_ZJLX);
            }
            map.put("SQRZJLX", SQRZJLX);
            map.put("JBR_ZJLX", JBR_ZJLX);

            // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
            String customFields = "";
            String ROOM_NO = map.get("ROOM_NO") == null ? "" : map.get("ROOM_NO").toString();
            String CUR_WIN = map.get("CUR_WIN") == null ? "" : map.get("CUR_WIN").toString();
            customFields = ROOM_NO + CUR_WIN + "," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
            map.put("CUSTOMFIELDS", customFields);
            Map<String, Object> evaInfo = new HashMap<String, Object>();
            evaInfo.put("CUSTOMFIELDS", customFields);
            String RECORD_ID = map.get("RECORD_ID") == null ? "" : map.get("RECORD_ID").toString();
            dao.saveOrUpdate(evaInfo, "T_CKBS_QUEUERECORD", RECORD_ID);
        }
        return list;
    }

    /**
     * @Description 获取好差评明细信息
     * @author Luffy Cai
     * @date 2020年7月30日
     * @param evaluateInfo
     * @return Map<String,Object>
     */
    private Map<String, Object> getEvaluateInfo(String evaluateInfo) {
        Map<String, Object> evaInfo = new HashMap<String, Object>();
        String str = evaluateInfo.replace("|", "。");
        String temp[];
        temp = str.split("。");
        StringBuffer appraisald = new StringBuffer();
        for (int i = 0; i < temp.length; i++) {
            String[] a = temp[i].split("[,]");
            if (a.length == 2) {
                if (StringUtils.isNotEmpty(appraisald.toString())) {
                    String b = "," + a[1];
                    appraisald.append(b);
                } else {
                    appraisald.append(a[1]);
                }
            }
        }
        int index = str.lastIndexOf("。");
        String writingevalua = str.substring(index + 1, str.length());
        boolean status = writingevalua.contains(",");
        if (!status) {

            evaInfo.put("writingevalua", writingevalua);
        }
        evaInfo.put("appraisald", appraisald.toString());
        String num[];
        num = appraisald.toString().split(",");
        evaInfo.put("appraisaldnum", num.length);
        return evaInfo;
    }

    /**
     * 
     * @Description 获取好差评系统证件类型
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param value
     * @return String
     */
    @SuppressWarnings("unchecked")
    private String getUserPageType(String value) {
        String userPageType = "";
        if ("SF".equals(value)) {
            userPageType = "111";
        } else if ("JGDM".equals(value)) {
            userPageType = "001";
        } else if ("YYZZ".equals(value)) {
            userPageType = "821";
        } else if ("JG".equals(value)) {
            userPageType = "114";
        } else if ("SB".equals(value)) {
            userPageType = "118";
        } else if ("HZ".equals(value)) {
            userPageType = "414";
        } else if ("TWTX".equals(value)) {
            userPageType = "511";
        } else if ("HKSF".equals(value)) {
            userPageType = "516";
        } else if ("AMSF".equals(value)) {
            userPageType = "516";
        } else if ("TWSF".equals(value)) {
            userPageType = "511";
        } else if ("GATX".equals(value)) {
            userPageType = "516";
        } else if ("QT".equals(value)) {
            userPageType = "999";
        }
        return userPageType;
    }

    /**
     * 描述 用数据包填充模版
     * 
     * @author Derek Zhang
     * @created 2015年10月22日 上午10:34:59
     * @param xmlMap
     * @param configXml
     * @return
     */
    private StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
    }

    /**
     * 
     * @Description 获取评价统计数据
     * @author Luffy Cai
     * @date 2020年9月25日
     * @param beginTime
     * @param endTime
     * @return Map<String,Object>
     */
    public Map<String, Object> getEvaluationStatistics(String beginTime, String endTime) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(beginTime);
        params.add(endTime);
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*)as projectNum,");
        sql.append("sum(case when t.EVALUATE in (2, 3, 1) then 1 else 0 end)as goodComments ");
        sql.append("from T_CKBS_QUEUERECORD t ");
        sql.append("where 1=1 and t.CREATE_TIME >= ? and t.CREATE_TIME <= ?");
        List<Map<String, Object>> statisticList = dao.findBySql(sql.toString(), params.toArray(), null);
        Map<String, Object> map = statisticList.get(0);
        // 获取事项总数itemCodNum
        StringBuffer sql2 = new StringBuffer();
        sql2.append("select count(*) as ITEMCODNUM from T_WSBS_SERVICEITEM t where t.fwsxzt='1'");
        List<Map<String, Object>> serviceItemList = dao.findBySql(sql2.toString(), null, null);
        Map<String, Object> itemMap = serviceItemList.get(0);
        map.put("ITEMCODNUM", itemMap.get("ITEMCODNUM"));
        // 获取差评数量
        StringBuffer sql3 = new StringBuffer();
        sql3.append("select t.EVALUATE, b.ITEM_NAME,b.exe_id,b.b.SUBJECT,t.OPERATOR_NAME from T_CKBS_QUEUERECORD t ");
        sql3.append("left join JBPM6_EXECUTION b on t.EXE_ID = b.EXE_ID ");
        sql3.append("left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE ");
        sql3.append("where 1=1 and t.EVALUATE in (0,5) and ");
        sql3.append("t.CREATE_TIME >= ? and t.CREATE_TIME <= ? and b.exe_id is not null");
        List<Map<String, Object>> badCommentsList = dao.findBySql(sql3.toString(), params.toArray(), null);
        if (null != badCommentsList && badCommentsList.size() > 0) {
            map.put("BADCOMMENTS", badCommentsList.size());
        } else {
            map.put("BADCOMMENTS", 0);
        }
        return map;
    }

    /**
     * 
     * @Description 好差评评价信息上报(第三方)
     * @author Luffy Cai
     * @date 2020年9月25日
     * @param evaData
     * @return Map<String,Object>
     */
    public Map<String, Object> pushEvaluateDataByThird(Map<String, Object> evaData) {
        String xmlStr = this.getEvaluateXmlByThird(evaData);
        String appCode = "a325f2b70f8640b381c14321c319ac59";// 密钥
        String md5 = Md5Util.getMD5(appCode);// 对密钥进行MD5
        String str = null;
        try {
            str = new SM4().encode(xmlStr, md5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        xmlStr = str.replaceAll("[\\n\\r]", "");
        Map<String, Object> result = EvaluateCommonUtil.pushEvaluateData(xmlStr);
        String code = (String) result.get("code");
        String exeId = (String) evaData.get("EXE_ID");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if ("200".equals(code)) {
            // 保存好差评明细信息
            String evaluateUnid = (String) result.get("evaluateUnid");
            returnMap.put("evaluateUnid", evaluateUnid);
            returnMap.put("success", true);
            returnMap.put("invokeResultCode", "000");
            returnMap.put("msg", "评价成功!");
            log.info("第三方业务系统办件申报号：" + exeId + "调用好差评系统评价信息上报接口成功，返回信息：" + result);
        } else {
            String resultData = (String) result.get("resultData");
            returnMap.put("success", true);
            returnMap.put("invokeResultCode", code);
            returnMap.put("msg", resultData);
            log.error("第三方业务系统办件申报号：" + exeId + "调用好差评系统评价信息上报接口失败，返回信息：" + resultData + "错误码：" + code);
        }
        return returnMap;
    }

    /**
     * 
     * @Description 审批系统办结办件短信评价定时器
     * @author Luffy Cai
     * @date 2021年5月31日 void
     */
    public void sendEvaluateMsg() {
        List<Map<String, Object>> list = getApprovedList();
        if (null != list && list.size() > 0) {
            log.info("审批系统办结办件短信评价数据量为：:" + list.size() + "条");
            for (Map<String, Object> approval : list) {
                try {
                    this.sendSuccessMsgToUser(approval);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @Description 获取评价信息列表
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getApprovedList() {
        StringBuffer sql = new StringBuffer();
        String createTime = DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd");
        List<Object> params = new ArrayList<>();
        params.add("%" + createTime + "%");
        sql.append(
                "select t.* from JBPM6_EXECUTION t where t.run_status in (2,3) and t.create_time like ? and t.msg_send_status ='0' and t.sqfs in (1,2)");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        if (null != list && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 
     * @Description 发送流程成功办结通知给发起人
     * @author Luffy Cai
     * @date 2021年5月31日
     * @param approval void
     */
    public void sendSuccessMsgToUser(Map<String, Object> approval) {
        String exeId = approval.get("EXE_ID").toString();
        String updateSql = " update JBPM6_EXECUTION set msg_send_status = '1' where exe_id = ? ";
        this.dao.executeSql(updateSql, new Object[]{exeId});
        // 获取流程实例
        Map<String, Object> exeMap = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        // 获取申请人类型
        String BSYHLX = (String) exeMap.get("BSYHLX");
        String sjhm = "";
        if (BSYHLX.equals("1")) {
            sjhm = (String) exeMap.get("SQRSJH");
        } else {
            sjhm = (String) exeMap.get("JBR_MOBILE");
        }
        String content = "【" + exeId + "】," + exeId;
        if (StringUtils.isNotEmpty(sjhm)) {
            Map<String, Object> returnMap;
            try {
                returnMap = SmsSendUtil.sendSms(content, sjhm, "217737");
                if (returnMap != null) {
                    String resultcode = returnMap.get("resultcode").toString();
                    StringBuffer logInfo = new StringBuffer();
                    if (Objects.equals(resultcode, "0")) {
                        exeMap.put("MSG_SEND_STATUS", 1);// 发送成功
                        log.info(logInfo.append("申报号：").append(exeId).append("发送短信提醒成功,手机号码为：").append(sjhm));
                    } else {
                        exeMap.put("MSG_SEND_STATUS", 2);// 发送失败
                        log.info(logInfo.append("申报号：").append(exeId).append("发送短信提醒失败,错误信息为：")
                                .append(JSON.toJSONString(returnMap)));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }

        }
    }
    
    /**
     * 
     * @Description 好差评系统数据补报
     * @author Luffy Cai
     * @date 2021年12月15日 void
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getEvaluationSupplement() {
        List<Map<String, Object>> evaluateList = getEvaluationSupplementList();
        if (null != evaluateList && evaluateList.size() > 0) {
            for (Map<String, Object> evaluate : evaluateList) {
                    try {
                        this.pushEvaluateSupplementData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
        }
    }    
    
    
    /**
     * 
     * @Description 好差评系统数据补报
     * @author Luffy Cai
     * @date 2021年12月15日 void
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getEvaluationSupplementAugust() {
        List<Map<String, Object>> evaluateList = getEvaluationSupplementAugustList();
        if (null != evaluateList && evaluateList.size() > 0) {
            for (Map<String, Object> evaluate : evaluateList) {
                    try {
                        this.pushEvaluateSupplementData(evaluate);
                    } catch (Exception e) {
                        log.error("省网好差评系统评价信息上报错误，申报号：" + evaluate.get("EXE_ID"));
                        log.error(e.getMessage(), e);
                    }
                }
        }
    } 
    
    
    /**
     * 
     * @Description 获取评价信息列表
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getEvaluationSupplementList() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select b.EXE_ID,b.CREATE_TIME,b.BSYHLX,b.SQJG_NAME,b.SQJG_CODE,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH, b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,");
        sql.append(" b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME from JBPM6_EXECUTION b");
        sql.append(" left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE");
        sql.append(" where b.create_time>'2021-01-01 00:00:00' and b.create_time<'2021-08-01 00:00:00' and c.SWB_ITEM_CODE is not null");
        sql.append(" and c.swb_item_code !='123a'  and b.exe_id  not in");
        sql.append(" (select t.exe_id from  T_CKBS_QUEUERECORD t where t.create_time>'2021-01-01 00:00:00' and t.create_time<'2021-08-01 00:00:00' ");
        sql.append(" and t.exe_id is not null) and b.EVA_SUPPLE_STATUS ='0' and ROWNUM<=200  ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
                String SQJG_NAME = map.get("SQJG_NAME") == null ? "" : map.get("SQJG_NAME").toString();
                String BSYHLX = map.get("BSYHLX") == null ? "" : map.get("BSYHLX").toString();
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(").replaceAll("\\、", " ");
                JBR_NAME =JBR_NAME.replaceAll("\\）", ")").replaceAll("\\（", "(").replaceAll("\\、", " ");
                SQJG_NAME=SQJG_NAME.replaceAll("\\）", ")").replaceAll("\\（", "(").replaceAll("\\、", " ");
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if(BSYHLX.equals("1")) {
                        String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                        map.put("SQRMC", SQRMC);
                        map.put("SQRSFZ", SQRSFZ);
                        // 返回证件类型
                        SQRZJLX = getUserPageType(SQRZJLX);
                        map.put("SQRZJLX", SQRZJLX);
                    }else {
                        String SQJG_CODE = userInfo.get("SQJG_CODE") == null ? "" : userInfo.get("SQJG_CODE").toString();
                        map.put("SQRMC", SQJG_NAME);
                        map.put("SQRSFZ", SQJG_CODE);
                        map.put("SQRZJLX", "001");
                        
                    }
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    map.put("SQRSJH", SQRSJH);
                    // 返回证件类型
                    if (JBR_ZJLX != null) {
                        JBR_ZJLX = getUserPageType(JBR_ZJLX);
                    }
                    map.put("JBR_NAME", JBR_NAME);
                    map.put("JBR_ZJLX", JBR_ZJLX);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : userInfo.get("JBR_ZJHM").toString());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                Date evaDate = new Date(date1.getTime() + 300000);
                String  EVALUATE_TIME = sdf.format(evaDate);
                map.put("EVALUATE_TIME", EVALUATE_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append(
                            "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }
                map.put("EVA_APPREISALD", "");
                map.put("EVA_NUM", "");
                map.put("EVA_VALUA", "");
                // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
                String customFields = "B99," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
                map.put("CUSTOMFIELDS", customFields);
            }
        }
        return list;
    }    
    
    /**
     * 
     * @Description 获取评价信息列表
     * @author Luffy Cai
     * @date 2020年7月31日
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getEvaluationSupplementAugustList() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select b.EXE_ID,b.CREATE_TIME,b.BSYHLX,b.SQJG_NAME,b.SQJG_CODE,b.CUR_STEPNAMES,b.SUBJECT,b.SQRSJH, b.SQRMC,b.SQRZJLX,b.SQRSFZ,b.JBR_NAME,");
        sql.append(" b.JBR_ZJLX,b.JBR_ZJHM,b.END_TIME,c.SWB_ITEM_CODE,c.ITEM_NAME from JBPM6_EXECUTION b");
        sql.append(" left join T_WSBS_SERVICEITEM c on b.ITEM_CODE = c.ITEM_CODE");
        sql.append(" where b.create_time>'2021-08-01 00:00:00' and b.create_time<'2021-12-17 00:00:00' and c.SWB_ITEM_CODE is not null");
        sql.append(" and c.swb_item_code !='123a'  and b.exe_id  not in");
        sql.append(" (select t.exe_id from  T_CKBS_QUEUERECORD t where t.create_time>'2021-08-01 00:00:00' and t.create_time<'2021-12-17 00:00:00' ");
        sql.append(" and t.exe_id is not null) and b.EVA_SUPPLE_STATUS ='0' and  c.IMPL_DEPART='区行政审批局'  and ROWNUM <=200  ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
                String EXE_ID = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
                String CUR_STEPNAMES = map.get("CUR_STEPNAMES") == null ? "" : map.get("CUR_STEPNAMES").toString();
                String END_TIME = map.get("END_TIME") == null ? "" : map.get("END_TIME").toString();
                String SQRZJLX = map.get("SQRZJLX") == null ? "" : map.get("SQRZJLX").toString();
                String JBR_NAME = map.get("JBR_NAME") == null ? "" : map.get("JBR_NAME").toString();
                String JBR_ZJLX = map.get("JBR_ZJLX") == null ? "" : map.get("JBR_ZJLX").toString();
                String SQRMC = map.get("SQRMC") == null ? "" : map.get("SQRMC").toString();
                String SQJG_NAME = map.get("SQJG_NAME") == null ? "" : map.get("SQJG_NAME").toString();
                String BSYHLX = map.get("BSYHLX") == null ? "" : map.get("BSYHLX").toString();
                SQRMC = SQRMC.replaceAll("\\）", ")").replaceAll("\\（", "(").replaceAll("\\、", " ");
                JBR_NAME =JBR_NAME.replaceAll("\\）", ")").replaceAll("\\（", "(").replaceAll("\\、", " ");
                SQJG_NAME=SQJG_NAME.replaceAll("\\）", ")").replaceAll("\\（", "(").replaceAll("\\、", " ");
                ArrayList<Object> params = new ArrayList<>();
                params.add(EXE_ID);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
                List<Map<String, Object>> userList = dao.findBySql(sql2.toString(), params.toArray(), null);
                List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
                if (decryptList != null && decryptList.size() > 0) {
                    Map<String, Object> userInfo = decryptList.get(0);
                    String SQRSJH = userInfo.get("SQRSJH") == null ? "" : userInfo.get("SQRSJH").toString();
                    if(BSYHLX.equals("1")) {
                        String SQRSFZ = userInfo.get("SQRSFZ") == null ? "" : userInfo.get("SQRSFZ").toString();
                        map.put("SQRMC", SQRMC);
                        map.put("SQRSFZ", SQRSFZ);
                        // 返回证件类型
                        SQRZJLX = getUserPageType(SQRZJLX);
                        map.put("SQRZJLX", SQRZJLX);
                    }else {
                        String SQJG_CODE = userInfo.get("SQJG_CODE") == null ? "" : userInfo.get("SQJG_CODE").toString();
                        map.put("SQRMC", SQJG_NAME);
                        map.put("SQRSFZ", SQJG_CODE);
                        map.put("SQRZJLX", "001");
                        
                    }
                    if (StringUtils.isEmpty(SQRSJH)) {
                        SQRSJH = userInfo.get("JBR_MOBILE") == null ? "" : userInfo.get("JBR_MOBILE").toString();
                    }
                    map.put("SQRSJH", SQRSJH);
                    // 返回证件类型
                    if (JBR_ZJLX != null) {
                        JBR_ZJLX = getUserPageType(JBR_ZJLX);
                    }
                    map.put("JBR_NAME", JBR_NAME);
                    map.put("JBR_ZJLX", JBR_ZJLX);
                    map.put("JBR_ZJHM", userInfo.get("JBR_ZJHM") == null ? "" : userInfo.get("JBR_ZJHM").toString());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = sdf.parse(CREATE_TIME);
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                Date evaDate = new Date(date1.getTime() + 300000);
                String  EVALUATE_TIME = sdf.format(evaDate);
                map.put("EVALUATE_TIME", EVALUATE_TIME);
                String TASK_NODENAME = "";
                if (StringUtils.isEmpty(END_TIME)) {// 办理状态 2（已受理）
                    ArrayList<Object> task = new ArrayList<>();
                    task.add(EXE_ID);
                    StringBuffer sql3 = new StringBuffer();
                    sql3.append(
                            "select t.* from JBPM6_TASK t where t.exe_id=? and t.end_time is not null order by t.end_time desc ");
                    List<Map<String, Object>> taskList = dao.findBySql(sql3.toString(), task.toArray(), null);
                    if (taskList.size() > 0) {// 在办状态
                        TASK_NODENAME = taskList.get(0).get("TASK_NODENAME") == null ? ""
                                : taskList.get(0).get("TASK_NODENAME").toString();
                    } else {
                        TASK_NODENAME = "开始";
                    }
                    if (StringUtils.isEmpty(CUR_STEPNAMES)) {// 环节名称
                        CUR_STEPNAMES = TASK_NODENAME;
                    }
                    map.put("PROSTATUS", "2");
                    map.put("END_TIME", "");
                    map.put("CUR_STEPNAMES", CUR_STEPNAMES);
                } else {
                    map.put("PROSTATUS", "3");
                    map.put("END_TIME", END_TIME);
                    map.put("CUR_STEPNAMES", "办结");
                }
                map.put("EVA_APPREISALD", "");
                map.put("EVA_NUM", "");
                map.put("EVA_VALUA", "");
                // 自定义字段customFields 规则：【评价器编号】,【业务流水号】
                String customFields = "B99," + EXE_ID.substring(EXE_ID.length() - 12) + ",351001";
                map.put("CUSTOMFIELDS", customFields);
            }
        }
        return list;
    }    
    
    
    
    
    
    /**
     * @Description 好差评评价信息上报(平板评价方式)
     * @author Luffy Cai
     * @date 2020年7月30日
     * @param evaluate
     */
    private void pushEvaluateSupplementData(Map<String, Object> evaluate) {
        String xmlStr = this.getEvaluateSupplementXml(evaluate);
        String appCode = "a325f2b70f8640b381c14321c319ac59";// 密钥
        String md5 = Md5Util.getMD5(appCode);// 对密钥进行MD5
        String str = null;
        try {
            str = new SM4().encode(xmlStr, md5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        xmlStr = str.replaceAll("[\\n\\r]", "");
        Map<String, Object> result = EvaluateCommonUtil.pushEvaluationSupplementData(xmlStr);
        String code = (String) result.get("code");
        String exeId = (String) evaluate.get("EXE_ID");
        Map<String, Object> evaInfo = new HashMap<String, Object>();
        if ("200".equals(code)) {
            // 保存好差评明细信息
            String evaluateUnid = (String) result.get("evaluateUnid");
            evaInfo.put("EVA_SUPPLE_STATUS", "1");
            evaInfo.put("EVA_SUPPLE_UNID", evaluateUnid);
            dao.saveOrUpdate(evaInfo, "JBPM6_EXECUTION", exeId);
        } else {
            String resultData = (String) result.get("resultData");
            evaInfo.put("EVA_SUPPLE_STATUS", "2");
            dao.saveOrUpdate(evaInfo, "JBPM6_EXECUTION", exeId);
            // 保存数据上报日志
            Map<String, Object> evaLog = new HashMap<String, Object>();
            evaLog.put("EXE_ID", exeId);
            evaLog.put("PUST_RESULT", resultData);
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            evaLog.put("CREATE_TIME", createTime);
            dao.saveOrUpdate(evaLog, "T_CKBS_EVALUATELOG", null);
        }
    }    
    
    /**
     * @Description 未评价数据默认好评数据插入
     * @author Jason Lin
     * @date 2020年7月30日
     * @param evaluate
     */
    @Override
    public void evaluateTheDefaultHighPraise() {
        //获取默认好差评字典开关
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchEvaluateTurn2");
        if(!"开".equals(switchTurn)){
            return;
        }
        //查询办件表中截止到目前为止，已经办结时间超过2天的办件
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.exe_id from jbpm6_execution a ");
        sql.append(" left join T_CKBS_QUEUERECORD b on a.exe_id = b.exe_id ");
        sql.append(" left join T_CKBS_EVALUATEINFO c on b.record_id = c.record_id ");
        sql.append(" where 1=1 ");
        sql.append(" and a.run_status not in (0,1) ");
        sql.append(" and a.end_time is not null ");
        sql.append(" and c.record_id is null ");
        sql.append(" and a.end_time <= ? ");
        sql.append(" and a.create_time >= ? ");
        String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();         //得到日历
        calendar.setTime(new Date());                       //把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -2);            //设置为前一天
        Date  beforeTimeDate = calendar.getTime();          //得到前一天的时间
        String BeforeTime = DateTimeUtil.getStrOfDate(beforeTimeDate, "yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] {BeforeTime,"2022-01-20 00:00:00"}, null);
        for (Map<String, Object> map : list) {
            //生成取号记录信息并插入
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("EXE_ID", map.get("EXE_ID"));
            variables.put("EVALUATE", "3");  //非常满意
            variables.put("EVALUATETIME", evaluateTime);
            variables.put("EVALUATETYPE", "3");//评价方式（1：平板，2：按键器,3审批后台,4WEB）
            variables.put("CALL_STATUS","-1");//审批后台
            variables.put("ROOM_NO","B");//B厅
            variables.put("CUR_WIN","99");//99窗口
            String recordId = newCallService.saveOrUpdate(variables, "T_CKBS_QUEUERECORD", "");
            
            //保存好差评明细信息
            Map<String,Object> info = new HashMap<String, Object>();
            info.put("RECORD_ID", recordId);
            info.put("EVA_LEVEL", "5");  // 5：非常满意4：满意3：基本满意2：不满意1：非常不满意
            info.put("EVA_INFO", "");    //   复选框内容为空
            info.put("EVALUATETIME", evaluateTime);
            newCallService.saveOrUpdate(info, "T_CKBS_EVALUATEINFO", null);
        }
    }    

}
