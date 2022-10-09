/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.ShtzSecondService;
import net.evecom.platform.cms.service.ContentService;
import net.evecom.platform.wsbs.service.ZgptInterfaceService;

/**
 * 描述 社会投资二阶段决策事件
 * @author Danto Huang
 * @created 2015-11-11 上午10:33:00
 */
@Service("shtzSecondService")
public class ShtzSecondServiceImpl extends BaseServiceImpl implements ShtzSecondService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ShtzSecondServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ShtzDao dao;
    /**
     * 引入ZgptInterfaceService
     */
    @Resource
    private ZgptInterfaceService zgptInterfaceService;
    /**
     * 引入contentService
     */
    @Resource
    private ContentService contentService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }


    /**
     * 
     * 描述 是否需要协调
     * @author Danto Huang
     * @created 2015-11-11 上午10:34:06
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResult(Map<String,Object> flowVars){
        String SFXYXT = (String) flowVars.get("SFXYXT");
        log.info("是否需要协调:"+SFXYXT);
        Set<String> resultNodes = new HashSet<String>();
        if(SFXYXT.equals("1")){
            resultNodes.add("协调结果");
        }else{
            resultNodes.add("填写公示材料");
        }
        return resultNodes;        
    }
    /**
     * 
     * 描述 是否需要协调
     * @author Danto Huang
     * @created 2015-11-11 上午10:34:06
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResultNew(Map<String,Object> flowVars){
        String SFXYXT = (String) flowVars.get("SFXYXT");
        log.info("是否需要协调:"+SFXYXT);
        Set<String> resultNodes = new HashSet<String>();
        if(SFXYXT.equals("1")){
            resultNodes.add("协调结果");
        }else{
            resultNodes.add("集中公示");
        }
        return resultNodes;        
    }
    /**
     * 描述: 是否需要修编
     *
     * @author Madison You
     * @created 2019/8/17 17:23:00
     * @param
     * @return
     */
    @Override
    public Set<String> getCoodResultX(Map<String, Object> flowVars) {
        String SFXYXB = (String) flowVars.get("SFXYXT");
        Set<String> resultNodes = new HashSet<String>();
        if(SFXYXB.equals("1")){
            resultNodes.add("修编结果");
        }else{
            resultNodes.add("并联审查");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 是否协调一致的结果
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String,Object> flowVars){
        String SFXTYZ = (String) flowVars.get("SFXTYZ");
        Set<String> resultNodes = new HashSet<String>();
        if(SFXTYZ.equals("1")){
            resultNodes.add("填写公示材料");
        }else{
            resultNodes.add("填写意见");
        }
        return resultNodes;        
    }
    /**
     * 
     * 描述 签发是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsSendPass(Map<String,Object> flowVars){
        String SFTG = (String) flowVars.get("QFJG");
        Set<String> resultNodes = new HashSet<String>();        
        if(SFTG.equals("1")){
            resultNodes.add("填写公示结果");
            zgptInterfaceService.publishGSXXSave(flowVars);
            sendGS(flowVars);
        }else{
            resultNodes.add("结束");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 推送公示信息至公示公告栏目
     * @author Danto Huang
     * @created 2015-12-16 上午11:07:33
     * @param flowVars
     */
    private void sendGS(Map<String,Object> flowVars){
        Map<String,String> gs = new HashMap<String, String>();
        gs.put("GSSXMM", flowVars.get("GSSXMM").toString());
        gs.put("SBDW", flowVars.get("SBDW").toString());
        gs.put("JSDW", flowVars.get("JSDW").toString());
        gs.put("JSDD", flowVars.get("JSDD").toString());
        gs.put("JSQX", flowVars.get("JSQX").toString());
        gs.put("ZTZ", flowVars.get("ZTZ").toString());
        gs.put("ZJLY", flowVars.get("ZJLY").toString());
        gs.put("JSGMJZYNR", flowVars.get("JSGMJZYNR").toString());
        gs.put("GSKSSJ", flowVars.get("GSKSSJ").toString());
        gs.put("GSJSSJ", flowVars.get("GSJSSJ").toString());
        gs.put("SPCS", flowVars.get("SPCS").toString());
        gs.put("LXDH", flowVars.get("LXDH").toString());
        gs.put("EMAIL", flowVars.get("EMAIL").toString());
        gs.put("POSTCODE", flowVars.get("POSTCODE").toString());
        gs.put("ADDRESS", flowVars.get("ADDRESS").toString());
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String lmId = properties.getProperty("lmId");
        gs.put("MODULEID", lmId);
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        String recordId = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId })
                .get("BUS_RECORDID").toString();
        gs.put("BUSID", recordId);
        contentService.dataSink(gs);
    }
    /**
     * 
     * 描述 公示是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsPublicityPass(Map<String,Object> flowVars){
        String GSSFTG = (String) flowVars.get("GSSFTG");
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")&&XTSFTG.equals("-1")){
            resultNodes.add("结束");
        }else{
            resultNodes.add("拟核准《项目申请报告+》");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 公示是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsPublicityPass1(Map<String,Object> flowVars){
        String GSSFTG = (String) flowVars.get("GSSFTG");
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")&&XTSFTG.equals("-1")){
            resultNodes.add("结束");
        }else{
            resultNodes.add("拟批复《项目备案请示+》意见");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 财务决算意见是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getCwjsPass(Map<String,Object> flowVars){
        String JGCWJSYJ = (String) flowVars.get("JGCWJSYJ");
        Set<String> resultNodes = new HashSet<String>();
        if(JGCWJSYJ.equals("1")){
            resultNodes.add("汇总意见");
        }else{
            resultNodes.add("竣工财务决算申请");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 处室领导审核是否通过
     * @author Danto Huang
     * @created 2015-11-25 下午2:55:56
     * @param flowVars
     * @return
     */
    public Set<String> getCsshPass(Map<String,Object> flowVars){
        String SHSFTG = (String) flowVars.get("CSSHJG");
        Set<String> resultNodes = new HashSet<String>();
        if(SHSFTG.equals("1")){
            resultNodes.add("分管领导审批");
        }else{
            resultNodes.add("申请");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 分管领导审批是否通过
     * @author Danto Huang
     * @created 2015-11-25 下午2:55:56
     * @param flowVars
     * @return
     */
    public Set<String> getFgspPass(Map<String,Object> flowVars){
        String SPSFTG = (String) flowVars.get("FGSHJG");
        Set<String> resultNodes = new HashSet<String>();
        if(StringUtils.isEmpty(SPSFTG)||SPSFTG.equals("1")){
            resultNodes.add("结束");
            zgptInterfaceService.publishDZZZSave(flowVars);
        }else{
            resultNodes.add("申请");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述
     * @author Derek Zhang
     * @created 2016年3月22日 上午10:03:06
     * @param flowVars
     * @return
     */
    public Map<String, Object> updateFlowStage(Map<String, Object> flowVars) {
        String s = (String)flowVars.get("EFLOW_ISBACK");
        if(StringUtils.isNotEmpty(s)){
            flowVars.put("STATUE", "1");
        }else{
            flowVars.put("STATUE", "0");
        }
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE");
        if(isTempSave.equals("-1")){
            String EFLOW_ISBACK= (String) flowVars.get("EFLOW_ISBACK");
            if(!(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true"))){
                flowVars.put("FLOW_STAGE","2");
            }
        }
        return flowVars;
    }
    /**
     * 
     * 描述 设置状态判断
     * @author Faker Li
     * @created 2015年12月14日 下午3:59:32
     * @param flowVars
     * @return
     * @see net.evecom.platform.bsfw.service.ShtzSecondService#setStatue(java.util.Map)
     */
    public Map<String, Object> setStatue(Map<String, Object> flowVars) {
        String s = (String)flowVars.get("EFLOW_ISBACK");
        if(StringUtils.isNotEmpty(s)){
            flowVars.put("STATUE", "1");
        }else{
            flowVars.put("STATUE", "0");
        }
        return flowVars;
    }
    /**
     * 
     * 描述 政府投资第四阶段财务决算意见是否通过
     * @author Faker Li
     * @created 2015年12月15日 上午10:01:04
     * @param flowVars
     * @return
     * @see net.evecom.platform.bsfw.service.ShtzSecondService#getCwjsPass(java.util.Map)
     */
    public Set<String> getCwjsyjPass(Map<String,Object> flowVars){
        String JGCWJSYJ = (String) flowVars.get("JGCWJSYJ");
        Set<String> resultNodes = new HashSet<String>();
        if(JGCWJSYJ.equals("1")){
            //resultNodes.add("汇总 意见");
            resultNodes.add("填写公示材料");
        }else{
            resultNodes.add("竣工财务决算申请");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 综合（专家）评审意见前置事件
     * @author Danto Huang
     * @created 2015-12-14 下午3:04:29
     * @param flowVars
     * @return
     */
    public Map<String, Object> lcjdZhpsSet(Map<String,Object> flowVars){
        String isBack = (String) flowVars.get("EFLOW_ISBACK");
        if(StringUtils.isNotEmpty(isBack)){
            flowVars.put("STATUE", "1");
        }else{
            flowVars.put("STATUE", "0");
        }
        return flowVars;
    }
    /**
     * 
     * 描述 汇总意见前置事件
     * @author Danto Huang
     * @created 2015-12-29 下午5:22:07
     * @param flowVars
     * @return
     */
    public Map<String, Object> lcHzyjSet(Map<String,Object> flowVars){
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        String currentTaskNodeName = flowVars.get("EFLOW_CURUSEROPERNODENAME").toString();
        String sql = "select t.* from jbpm6_task t where t.task_nodename in"
                + "(select t.fromtask_nodenames from jbpm6_task t where t.exe_id=? and t.task_nodename=?)"
                + " and t.exe_id=? and t.is_pass='-1'";
        List list = dao.findBySql(sql, new Object[]{exeId,currentTaskNodeName,exeId},null);
        if(list!=null&&list.size()>0){
            flowVars.put("SFXYXT", "1");
        }else{
            flowVars.put("SFXYXT", "-1");
        }
        return flowVars;
    }

}
