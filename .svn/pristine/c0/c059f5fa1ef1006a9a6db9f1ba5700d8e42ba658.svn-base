/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.ZftzService;
import net.evecom.platform.hflow.service.JbpmService;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

/**
 * 描述 政府投资项目第一阶段服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月16日 上午9:39:36
 */
@SuppressWarnings("rawtypes")
@Service("zftzService")
public class ZftzServiceImpl extends BaseServiceImpl implements ZftzService {
    /**
     * 所引入的dao
     */
    @Resource
    private ShtzDao dao;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * 描述 覆盖获取实体dao方法
     * 
     * @author Derek Zhang
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 是否需要协调
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsNeedCoordination(Map<String, Object> flowVars) {
        String SFXYXT = (String) flowVars.get("SFXYXT");
        Set<String> resultNodes = new HashSet<String>();
        if (!StringUtil.isBlank(SFXYXT) && SFXYXT.equals("1")) {
            resultNodes.add("协调结果");
        } else {
            resultNodes.add("起草意见书");
        }
        return resultNodes;
    }

    /**
     * 描述 协调结果--协调是否一致
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getCoordinationResult(Map<String, Object> flowVars) {
        String SFXTYZ = (String) flowVars.get("SFXTYZ");
        Set<String> resultNodes = new HashSet<String>();
        if (!StringUtil.isBlank(SFXTYZ) && SFXTYZ.equals("1")) {
            resultNodes.add("起草意见书");
        } else {
            resultNodes.add("填写意见");
        }
        return resultNodes;
    }

    /**
     * 描述 保存规划局审查意见
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveGWHLDQFYJ(Map<String, Object> flowVars) {
        String GWHLDQFYJ = (String) flowVars.get("EFLOW_HANDLE_OPINION");
        if (!StringUtil.isBlank(GWHLDQFYJ)) {
            flowVars.put("GHJYJ", GWHLDQFYJ);
        }
        return flowVars;
    }

    /**
     * 
     * 描述 获取需要从网站公式自动跳转的数据
     * 
     * @author Flex Hu
     * @created 2016年2月17日 下午4:19:11
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findNeedAutoJump() {
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES");
        sql.append(",TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=?  ");
        sql.append(" AND D.DEF_KEY IN ('zhtz0301','021','011') ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> datas = dao.findBySql(sql.toString(), new Object[] { "网站公示", "网站公示" }, null);
        // 获取当前的时间
        String currentTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        // currentTime = "2016-02-23 00:00:00";
        for (Map<String, Object> d : datas) {
            String time = (String) d.get("CREATE_TIME");
            long days = DateTimeUtil.getIntervalDay(time, currentTime);
            if (days >= 5) {
                list.add(d);
            }
        }
        return list;
    }

    /**
     * 
     * 描述 面向网站公示进行跳转
     * 
     * @author Flex Hu
     * @created 2016年2月17日 下午4:34:57
     * @param data
     * @throws Exception
     */
    public void jumpTaskForWzgs(Map<String, Object> data) throws Exception {
        Map<String, Object> flowVars = new HashMap<String, Object>();
        String defId = (String) data.get("DEF_ID");
        int flowVersion = Integer.parseInt(data.get("DEF_VERSION").toString());
        flowVars.put("EFLOW_EXEID", data.get("EXE_ID"));
        flowVars.put("EFLOW_DEFKEY", data.get("DEF_KEY"));
        flowVars.put("EFLOW_DEFID", data.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION", data.get("DEF_VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", data.get("CUR_STEPNAMES"));
        flowVars.put("EFLOW_CURUSEROPERNODENAME", data.get("CUR_STEPNAMES"));
        flowVars.put("ITEM_CODE", data.get("ITEM_CODE"));
        flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
        flowVars.put("EFLOW_CURRENTTASK_ID", data.get("TASK_ID"));
        String nextStepJson = this.jbpmService.getNextStepsJson(defId, flowVersion, data.get("CUR_STEPNAMES")
                .toString(), flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
            jbpmService.doFlowJob(flowVars);
        }
    }

    /**
     * 
     * 描述 更新流程阶段字段
     * 
     * @author Flex Hu
     * @created 2016年3月7日 下午3:52:44
     * @param flowVars
     * @return
     */
    public Map<String, Object> updateFlowStage(Map<String, Object> flowVars) {
        // 获取是否是暂存操作
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE");
        if (isTempSave.equals("-1")) {
            String EFLOW_ISBACK = (String) flowVars.get("EFLOW_ISBACK");
            if (!(StringUtils.isNotEmpty(EFLOW_ISBACK) && EFLOW_ISBACK.equals("true"))) {
                flowVars.put("FLOW_STAGE", "2");
            }
        }
        return flowVars;
    }

    /**
     * 描述 是否有异议
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getHasDissent(Map<String, Object> flowVars) {
        String SFYYY = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (!StringUtil.isBlank(SFYYY) && SFYYY.equals("1")) {
            resultNodes.add("送审");
        } else {
            resultNodes.add("异议复核");
        }
        return resultNodes;
    }

    /**
     * 描述 异议复核结果是否驳回异议
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsRejectDissent(Map<String, Object> flowVars) {
        String SFBHYY = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (!StringUtil.isBlank(SFBHYY) && SFBHYY.equals("1")) {
            resultNodes.add("送审");
        } else {
            resultNodes.add("预审");
        }
        return resultNodes;
    }

    /**
     * 描述 招标、投标流程是否送分管领导
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsSendFGLD(Map<String, Object> flowVars) {
        String zbxmType = (String) flowVars.get("ZBXM_TYPE");
        boolean isSendFGLD=false;
        if (!StringUtils.isBlank(zbxmType) && !zbxmType.equals("null")) {
            String[] zbxmTypeArr = zbxmType.split(",");
            for (int i = 0; i < zbxmTypeArr.length; i++) {
                String record = (String) flowVars.get(ZBTB_TYPEARRS[Integer.valueOf(zbxmTypeArr[i]) - 1][0]
                        + "_Record");
                if (!StringUtils.isBlank(record) && !record.equals("null")) {
                    String[] recordArray = record.split("_");
                    float zbje=Float.valueOf(recordArray[1]);
                    String xmType =ZBTB_TYPEARRS[Integer.valueOf(zbxmTypeArr[i]) - 1][0];
                    if((xmType.equals("kcXM")||xmType.equals("sjXM")||xmType.equals("jlXM"))&&zbje>200.00){
                        isSendFGLD=true;
                        break;
                    }else if(xmType.equals("gcXM")&&zbje>1000){
                        isSendFGLD=true;
                        break;
                    }else if((xmType.equals("zysbXM")||xmType.equals("zyclXM"))&&zbje>1000){
                        isSendFGLD=true;
                        break;
                    }
                }
            }
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isSendFGLD) {
            resultNodes.add("分管领导审核");
        } else {
            resultNodes.add("出具《招标申请》审核意见书");
        }
        return resultNodes;
    }

    /**
     * 描述 招标、投标流程是否送主任办公会审议
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsSendZRBGHSY(Map<String, Object> flowVars) {
        String zbxmType = (String) flowVars.get("ZBXM_TYPE");
        boolean isSendZRBGH=false;
        if (!StringUtils.isBlank(zbxmType) && !zbxmType.equals("null")) {
            String[] zbxmTypeArr = zbxmType.split(",");
            for (int i = 0; i < zbxmTypeArr.length; i++) {
                String record = (String) flowVars.get(ZBTB_TYPEARRS[Integer.valueOf(zbxmTypeArr[i]) - 1][0]
                        + "_Record");
                if (!StringUtils.isBlank(record) && !record.equals("null")) {
                    String[] recordArray = record.split("_");
                    float zbje=Float.valueOf(recordArray[1]);
                    String xmType = ZBTB_TYPEARRS[Integer.valueOf(zbxmTypeArr[i]) - 1][0];
                    if((xmType.equals("kcXM")||xmType.equals("sjXM")||xmType.equals("jlXM"))&&zbje>1000.00){
                        isSendZRBGH=true;
                        break;
                    }else if(xmType.equals("gcXM")&&zbje>3000){
                        isSendZRBGH=true;
                        break;
                    }else if((xmType.equals("zysbXM")||xmType.equals("zyclXM"))&&zbje>1000){
                        isSendZRBGH=true;
                        break;
                    }
                }
            }
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isSendZRBGH) {
            resultNodes.add("主任办公会审议");
        } else {
            resultNodes.add("出具《招标申请》审核意见书");
        }
        return resultNodes;
    }

    /**
     * 描述 保存招标、投标流程业务子表数据
     * 
     * @author Derek Zhang
     * @created 2016年8月9日 上午8:44:49
     * @param flowDatas
     * @param busRecordId
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public void saveOrUpdateZTBDateItems(Map<String, Object> flowDatas, String busRecordId) {
        String zbxmType = (String) flowDatas.get("ZBXM_TYPE");
        if (!StringUtils.isBlank(zbxmType) && !zbxmType.equals("null")) {
            String[] zbxmTypeArr = zbxmType.split(",");
            dao.remove("T_BSFW_TZXMZBTB_ITEM", "ZB_ID", new Object[] { busRecordId });
            for (int i = 0; i < zbxmTypeArr.length; i++) {
                String record = (String) flowDatas.get(ZBTB_TYPEARRS[Integer.valueOf(zbxmTypeArr[i]) - 1][0]
                        + "_Record");
                if (!StringUtils.isBlank(record) && !record.equals("null")) {
                    String[] types = ZBTB_TYPEARRS[Integer.valueOf(zbxmTypeArr[i]) - 1];
                    Map<String, Object> varMap = new HashMap<String, Object>();
                    varMap.put("ZB_TYPE", types[0]);
                    varMap.put("ZB_TYPE_NAME", types[1]);
                    varMap.put("ZB_ID", busRecordId);
                    varMap.put("CREATE_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    String[] recordArray = record.split("_");
                    varMap.put("ZB_XS", recordArray[0]);
                    varMap.put("ZB_ITEM_AMOUNT", recordArray[1]);
                    varMap.put("REASONS", recordArray[2]);
                    if (recordArray.length == 4) {
                        varMap.put("REASONS_ITEMS", recordArray[3]);
                    }
                    String typeId = this.dao.saveOrUpdate(varMap, "T_BSFW_TZXMZBTB_ITEM", null);
                }
            }
        }
    }

    /**
     * 描述:适用于政府投资副流程（签发是否通过）
     *
     * @author Madison You
     * @created 2020/3/13 10:49:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsQfPassElse(Map<String, Object> flowVars) {
        String qfsftg = (String) flowVars.get("QFSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (qfsftg != null) {
            if (qfsftg.equals("1")) {
                resultNodes.add("批复《立项用地规划综合审查意见书》");
            } else {
                resultNodes.add("汇总 意见");
            }
        }
        return resultNodes;
    }
}
