/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.ShtzFourthService;
import net.evecom.platform.cms.service.ContentService;
import net.evecom.platform.wsbs.service.ZgptInterfaceService;

import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

/**
 * 描述 社会投资第四阶段服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月11日 上午10:42:40
 */
@SuppressWarnings("rawtypes")
@Service("shtzFourthService")
public class ShtzFourthServiceImpl extends BaseServiceImpl implements
        ShtzFourthService {
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
     * contentService
     */
    @Resource
    private ContentService contentService;

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
            //判断是否两次招标失败
            String sjXMyqzbly7 = (String) flowVars.get("sjXMyqzbly7");
            if (!StringUtil.isBlank(sjXMyqzbly7) && sjXMyqzbly7.equals("1")) {
                resultNodes.add("送审");
            } else {
                resultNodes.add("填写公示材料");
            }
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
            //判断是否两次招标失败
            String sjXMyqzbly7 = (String) flowVars.get("sjXMyqzbly7");
            if (!StringUtil.isBlank(sjXMyqzbly7) && sjXMyqzbly7.equals("1")) {
                resultNodes.add("送审");
            } else {
                resultNodes.add("填写公示材料");
            }
        } else {
            resultNodes.add("填写意见");
        }
        return resultNodes;
    }

    /**
     * 描述 是否招标失败两次
     * 
     * @author Water Guo
     * @created 2016年11月03日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsfailertwo(Map<String, Object> flowVars) {
        String sjXMyqzbly7 = (String) flowVars.get("sjXMyqzbly7");
        Set<String> resultNodes = new HashSet<String>();
        if (!StringUtil.isBlank(sjXMyqzbly7) && sjXMyqzbly7.equals("1")) {
            resultNodes.add("送审");
        } else {
            resultNodes.add("填写公示材料");
        }
        return resultNodes;
    }

    /**
     * 描述 领导签发结果是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getLeaderIssueResult(Map<String, Object> flowVars) {
        String LDQFSFTG = (String) flowVars.get("LDQFSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (!StringUtil.isBlank(LDQFSFTG) && LDQFSFTG.equals("1")) {
            resultNodes.add("填写公示结果");
            zgptInterfaceService.publishGSXXSave(flowVars);
            sendGS(flowVars);
        } else {
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
     * 描述 公示是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getPublicityResult(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSJG");
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("-1")&&XTSFTG.equals("-1")) {
            resultNodes.add("结束");
        } else {
            resultNodes.add("拟批复联合竣工验收申请");
        }
        return resultNodes;
    }

    /**
     * 描述 保存批复意见
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveNPFYJ(Map<String, Object> flowVars) {
        String NPFYJ = (String) flowVars.get("EFLOW_HANDLE_OPINION");
        if (!StringUtil.isBlank(NPFYJ)) {
            flowVars.put("NPFYJ", NPFYJ);
        }
        return flowVars;
    }

    /**
     * 描述 保存验收申请时间
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveJGYSSQSJ(Map<String, Object> flowVars) {
        String JGYSSQSJ = (String) flowVars.get("JGYSSQSJ");
        if (StringUtil.isBlank(JGYSSQSJ)) {
            flowVars.put("JGYSSQSJ",
                    DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm"));
        }
        return flowVars;
    }

    /**
     * 描述 保存验收通过时间
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveJGYSJSSJ(Map<String, Object> flowVars) {
        flowVars.put("JGYSJSSJ", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm"));
        return flowVars;
    }

    /**
     * 描述 保存规划局审查意见
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveGHJSCYJ(Map<String, Object> flowVars) {
        String GHJYJ = (String) flowVars.get("EFLOW_HANDLE_OPINION");
        if (!StringUtil.isBlank(GHJYJ)) {
            flowVars.put("GHJYJ", GHJYJ);
        }
        return flowVars;
    }
}
