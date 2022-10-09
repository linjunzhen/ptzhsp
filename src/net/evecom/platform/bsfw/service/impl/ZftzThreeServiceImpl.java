/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.ZftzThreeService;
import net.evecom.platform.cms.service.ContentService;
import net.evecom.platform.wsbs.service.ZgptInterfaceService;

import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

/**
 * 
 * 描述 政府投资项目第三阶段流程服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月17日 上午10:23:52
 * @version v1.0
 */
@SuppressWarnings("rawtypes")
@Service("zftzThreeService")
public class ZftzThreeServiceImpl extends BaseServiceImpl implements ZftzThreeService {
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
     * 覆盖获取实体dao方法
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 计算协调出来的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResult(Map<String, Object> flowVars) {
        String SFXYXT = (String) flowVars.get("SFXYXT");
        Set<String> resultNodes = new HashSet<String>();
        if (SFXYXT.equals("1")) {
            resultNodes.add("协调结果");
        } else {
            resultNodes.add("填写公示材料");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 计算协调出来的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResultNew(Map<String, Object> flowVars) {
        String SFXYXT = (String) flowVars.get("SFXYXT");
        Set<String> resultNodes = new HashSet<String>();
        if (SFXYXT.equals("1")) {
            resultNodes.add("协调结果");
        } else {
            resultNodes.add("集中公示");
        }
        return resultNodes;
    }
    /**
     * 描述:是否需要协调决策事件（适用于政府投资第一流程第二版）
     *
     * @author Madison You
     * @created 2019/8/21 10:52:00
     * @param
     * @return
     */
    public Set<String> getCoodResultA(Map<String, Object> flowVars) {
        String SFXYXT = StringUtils.getString(flowVars.get("SFXYXT"));
        String busTableName=StringUtils.getString(flowVars.get("EFLOW_BUSTABLENAME"));
        Set<String> resultNodes = new HashSet<String>();
        if (SFXYXT.equals("1")) {
            resultNodes.add("协调结果");
        } else {
            if("T_PROJECT_CODEINFOCHANGE".equals(busTableName)){ //赋码信息变更流程
                resultNodes.add("出具关于赋码信息变更的函");
            }else{
                resultNodes.add("出具《关于项目规划选址及用地的意见函》");
            }
        }
        return resultNodes;
    }
    /**
     * 描述:是否需要协调（赋码信息决策判断）
     *
     * @author Madison You
     * @created 2019/8/21 10:52:00
     * @param
     * @return
     */
    public Set<String> getCoodResultForCodeChange(Map<String, Object> flowVars) {
        String SFXYXT = StringUtils.getString(flowVars.get("SFXYXT"));
        Set<String> resultNodes = new HashSet<String>();
        if (SFXYXT.equals("1")) {
            resultNodes.add("协调结果");
        } else {
            resultNodes.add("出具关于赋码信息变更的函");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 计算是否协调一致的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String, Object> flowVars) {
        String SFXTYZ = (String) flowVars.get("SFXTYZ");
        Set<String> resultNodes = new HashSet<String>();
        if (SFXTYZ.equals("1")) {
            resultNodes.add("填写公示材料");
        } else {
            resultNodes.add("填写意见");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 计算是否协调一致的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSameNew(Map<String, Object> flowVars) {
        String SFXTYZ = (String) flowVars.get("SFXTYZ");
        Set<String> resultNodes = new HashSet<String>();
        if (SFXTYZ.equals("1")) {
            resultNodes.add("集中公示");
        } else {
            resultNodes.add("填写意见");
        }
        return resultNodes;
    }
    /**
     * 
     * 描述 签发是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getQFIsPass(Map<String, Object> flowVars) {
        String SFXYXT = (String) flowVars.get("QFSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (SFXYXT.equals("1")) {
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
     * 
     * 描述 公示是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getGSIsPass(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("-1")) {
            resultNodes.add("异议协调");
        } else {
            resultNodes.add("拟批复《施工图联合审查意见书》");
        }
        return resultNodes;
    }

    /**
     * 描述:公示是否通过（用于政府审批第三阶段流程）
     *
     * @author Madison You
     * @created 2019/8/9 16:25:00
     * @param
     * @return
     */
    public Set<String> getHhreeGSIsPass(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("-1")) {
            resultNodes.add("异议协调");
        } else {
            resultNodes.add("拟批复《施工许可综合审查意见书》");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-5-6 上午11:38:05
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcertPass(Map<String,Object> flowVars){
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (XTSFTG.equals("-1")) {
            resultNodes.add("结束");
        } else {
            resultNodes.add("拟批复《施工图联合审查意见书》");
        }
        return resultNodes;
    }

    /**
     * 描述:用于政府审批第三阶段流程
     *
     * @author Madison You
     * @created 2019/8/9 16:32:00
     * @param
     * @return
     */
    public Set<String> getTreeIsConcertPass(Map<String,Object> flowVars){
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (XTSFTG.equals("-1")) {
            resultNodes.add("结束");
        } else {
            resultNodes.add("拟批复《施工许可综合审查意见书》");
        }
        return resultNodes;
    }
    @Override
    public Set<String> getTreeIsConcertPassNew(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub

        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (XTSFTG.equals("-1")) {
            resultNodes.add("汇总 意见");
        } else {
            resultNodes.add("拟批复《施工许可综合审查意见书》");
        }
        return resultNodes;
    }

    /**
     * 描述 保存批复意见
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
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
     * 描述 保存管委会签发意见
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveGWHQFYJ(Map<String, Object> flowVars) {
        String GWHQFYJ = (String) flowVars.get("EFLOW_HANDLE_OPINION");
        if (!StringUtil.isBlank(GWHQFYJ)) {
            flowVars.put("GWHQFYJ", GWHQFYJ);
        }
        return flowVars;
    }

    /**
     * 描述:适用于政府投资第三流程（签发是否通过）
     *
     * @author Madison You
     * @created 2020/3/13 10:43:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsQfPassThree(Map<String, Object> flowVars) {
        String qfsftg = (String) flowVars.get("QFSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (qfsftg != null) {
            if (qfsftg.equals("1")) {
                resultNodes.add("批复《施工许可综合审查意见书》");
            } else {
                resultNodes.add("汇总 意见");
            }
        }
        return resultNodes;
    }


}
