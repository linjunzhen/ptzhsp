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
import net.evecom.platform.bsfw.service.ShtzThreeService;
import net.evecom.platform.cms.service.ContentService;
import net.evecom.platform.wsbs.service.ZgptInterfaceService;

import org.springframework.stereotype.Service;

/**
 * 描述
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2015年11月10日 上午10:42:57
 */
@Service("shtzThreeService")
public class ShtzThreeServiceImpl extends BaseServiceImpl implements ShtzThreeService {
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
     * 覆盖获取实体dao方法 描述
     * 
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
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
     * @author Faker Li
     * @created 2015年8月19日 下午5:15:45
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
     * 描述 计算是否协调一致的结果
     * 
     * @author Faker Li
     * @created 2015年11月10日 上午11:16:50
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
     * 描述:是否协调一致适用于政府投资第一流程第二版
     *
     * @author Madison You
     * @created 2019/8/21 10:59:00
     * @param
     * @return
     */
    public Set<String> getIsCoodSameA(Map<String, Object> flowVars) {
        String SFXTYZ=StringUtils.getString(flowVars.get("SFXTYZ"));
        String busTableName= StringUtils.getString(flowVars.get("EFLOW_BUSTABLENAME"));
        Set<String> resultNodes = new HashSet<String>();
        if (SFXTYZ.equals("1")) {
            if("T_PROJECT_CODEINFOCHANGE".equals(busTableName)){ //赋码信息变更流程
                resultNodes.add("出具关于赋码信息变更的函");
            }else {
                resultNodes.add("出具《关于项目规划选址及用地的意见函》");
            }
        } else {
            resultNodes.add("填写意见");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 签发是否通过
     * 
     * @author Faker Li
     * @created 2015年11月11日 上午11:24:05
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
     * @author Faker Li
     * @created 2015年11月11日 上午11:24:05
     * @param flowVars
     * @return
     */
    public Set<String> getGSIsPass(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("-1")&&XTSFTG.equals("-1")) {
            resultNodes.add("结束");
        } else {
            resultNodes.add("拟批复《施工图》联合审查意见");
        }
        return resultNodes;
    }

}
