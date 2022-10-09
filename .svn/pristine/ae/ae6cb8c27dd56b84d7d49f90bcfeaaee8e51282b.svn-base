/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.ShtzService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月10日 上午10:42:57
 */
@Service("shtzService")
public class ShtzServiceImpl extends BaseServiceImpl implements ShtzService {
    /**
     * 所引入的dao
     */
    @Resource
    private ShtzDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
     * 描述 计算协调出来的结果
     * 
     * @author Flex Hu
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
//            resultNodes.add("区审批局项目投资处领导审签");
            resultNodes.add("起草意见书");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 计算是否协调一致的结果
     * 
     * @author Flex Hu
     * @created 2015年11月10日 上午11:16:50
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String, Object> flowVars) {
        String SFXTYZ = (String) flowVars.get("SFXTYZ");
        Set<String> resultNodes = new HashSet<String>();
        if (SFXTYZ.equals("1")) {
//            resultNodes.add("区审批局项目投资处领导审签");
            resultNodes.add("起草意见书");
        } else {
            resultNodes.add("填写意见");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 获取投资项目流程数据集合
     * 
     * @author Flex Hu
     * @created 2015年11月27日 下午1:57:15
     * @param type
     *            (SHTZ:社会投资 ZFTZ:政府投资)
     * @return
     */
    public List<Map<String, Object>> findShtzJdDatas(String type, int jdValue) {
        String typeCode = "";
        if (type.equals("SHTZ")) {
            // typeCodes = "('SHTZJD1','SHTZJD2','SHTZJD3','SHTZJD4')";
            typeCode = "SHTZJD" + jdValue;
        } else {
            // typeCodes = "('ZFTZJD1','ZFTZJD2','ZFTZJD3','ZFTZJD4')";
            typeCode = "ZFTZJD" + jdValue;
        }
        StringBuffer sql = new StringBuffer("select T.DIC_ID,T.DIC_CODE,T.DIC_NAME,T.TYPE_CODE");
        sql.append(" from T_MSJW_SYSTEM_DICTIONARY T where T.TYPE_CODE=? ");
        sql.append(" ORDER BY T.DIC_SN ASC");
        return dao.findBySql(sql.toString(), new Object[] { typeCode }, null);
    }

    /**
     * 
     * 描述 获取投资项目流程数据集合
     * 
     * @author Flex Hu
     * @created 2015年11月27日 下午2:48:05
     * @param type
     * @return
     */
    public List<List<Map<String, Object>>> findShtzDatas(String type) {
        List<Map<String, Object>> list1 = this.findShtzJdDatas(type, 1);
        List<Map<String, Object>> list2 = this.findShtzJdDatas(type, 2);
        List<Map<String, Object>> list3 = this.findShtzJdDatas(type, 3);
        List<Map<String, Object>> list4 = this.findShtzJdDatas(type, 4);
        List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();
        list.add(list1);
        list.add(list2);
        list.add(list3);
        list.add(list4);
        return list;
    }

    /**
     * 
     * 描述 设置项目编号值
     * 
     * @author Flex Hu
     * @created 2015年12月2日 上午10:55:47
     * @param flowVars
     * @return
     */
    public Map<String, Object> setProjectNumber(Map<String, Object> flowVars) {
        // 获取是否暂存操作
        String EFLOW_ISTEMPSAVE = (String) flowVars.get("EFLOW_ISTEMPSAVE");
        // 获取是否退回操作
        String EFLOW_ISBACK = (String) flowVars.get("EFLOW_ISBACK");
        if (StringUtils.isNotEmpty(EFLOW_ISTEMPSAVE) && EFLOW_ISTEMPSAVE.equals("1")) {
            return flowVars;
        }
        if (StringUtils.isNotEmpty(EFLOW_ISBACK) && EFLOW_ISBACK.equals("true")) {
            return flowVars;
        }
        StringBuffer tzxmbh = new StringBuffer("");
        // 获取当前年份
        int currentYear = DateTimeUtil.getCurrentYear();
        // 获取地区部门
        String depCode = "110000";
        // 获取行业代码
        String hyCode = "01";
        // 获取项目类型代码
        String xmlxCode = "03";
        // 获取序列号
        String seqValue = dao.getXmbhSeq(flowVars);
        tzxmbh.append(currentYear).append("-").append(depCode).append("-");
        tzxmbh.append(hyCode).append("-").append(xmlxCode).append("-").append(seqValue);
        flowVars.put("TZXMBH", tzxmbh.toString());
        return flowVars;
    }

    @Override
    public List<Map<String, Object>> findSwbtzxmData(String status, String projectCode) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(
                "select t.CONTENT,t.data_id,t.exe_id,t.project_code,t.oper_username,t.status from"
                       + " T_BSFW_SWBTZXMDATA t where t.status = ? and t.project_code = ? order by t.create_date desc");
        return dao.findBySql(sql.toString(), new Object[] { status, projectCode }, null);
    }

}
