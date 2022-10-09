/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.ZftzSecondService;

/**
 * 描述
 * @author Danto Huang
 * @created 2015-12-7 下午2:49:13
 */
@Service("zftzSecondService")
public class ZftzSecondServiceImpl extends BaseServiceImpl implements ZftzSecondService {
    /**
     * 所引入的dao
     */
    @Resource
    private ShtzDao dao;
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
     * 描述 公示是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsPublicityPass(Map<String,Object> flowVars){
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")){
            resultNodes.add("异议协调");
        }else{
            resultNodes.add("拟批复《工程可行性研究报告+》");
        }
        return resultNodes;
    }

    /**
     * 描述:公示是否通过（适用于政府投资副流程）
     *
     * @author Madison You
     * @created 2019/8/12 10:28:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsPublicityPassF(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")){
            resultNodes.add("异议协调");
        }else{
            resultNodes.add("拟批复《立项用地规划综合审查意见书》");
        }
        return resultNodes;
    }

    @Override
    public Set<String> getIsPublicityPassS(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")){
            resultNodes.add("异议协调");
        }else{
            resultNodes.add("拟批复《工程建设项目竣工验收意见书》");
        }
        return resultNodes;
    }

    /**
     * 描述:公示是否通过（适用于政府投资第一流程）
     *
     * @author Madison You
     * @created 2019/8/15 10:02:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsPublicityPassC(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")){
            resultNodes.add("异议协调");
        }else{
            resultNodes.add("出具《关于项目规划选址及用地的意见函》");
        }
        return resultNodes;
    }

    /**
     * 描述:公示是否通过（适用于政府投资第二阶段流程）
     *
     * @author Madison You
     * @created 2019/8/12 11:19:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsPublicityPassE(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("GSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(GSSFTG.equals("-1")){
            resultNodes.add("异议协调");
        }else{
            resultNodes.add("拟批复《工程建设许可综合审查意见书》");
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
        if(XTSFTG.equals("-1")){
            resultNodes.add("结束");
        }else{
            resultNodes.add("拟批复《工程可行性研究报告+》");
        }
        return resultNodes;
    }

    /**
     * 描述:适用于政府投资副流程
     *
     * @author Madison You
     * @created 2019/8/12 10:35:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsConcertPassF(Map<String, Object> flowVars) {
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("结束");
        }else{
            resultNodes.add("拟批复《立项用地规划综合审查意见书》");
        }
        return resultNodes;
    }

    @Override
    public Set<String> getIsConcertPassS(Map<String, Object> flowVars) {
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("结束");
        }else{
            resultNodes.add("拟批复《工程建设项目竣工验收意见书》");
        }
        return resultNodes;
    }

    /**
     * 描述:适用于政府投资第二流程
     *
     * @author Madison You
     * @created 2019/8/12 11:23:00
     * @param
     * @return
     */
    @Override
    public Set<String> getIsConcertPassE(Map<String, Object> flowVars) {
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("结束");
        }else{
            resultNodes.add("拟批复《工程建设许可综合审查意见书》");
        }
        return resultNodes;
    }

    @Override
    public Set<String> getIsConcertPassNewS(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub

        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("汇总 意见");
        }else{
            resultNodes.add("拟批复《工程建设项目竣工验收意见书》");
        }
        return resultNodes;
    }

    @Override
    public Set<String> getIsConcertPassNewE(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("汇总 意见");
        }else{
            resultNodes.add("拟批复《立项用地规划综合审查意见书》");
        }
        return resultNodes;
    }

    @Override
    public Set<String> getIsConcertPassNew(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub

        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("汇总 意见");
        }else{
            resultNodes.add("拟批复《工程可行性研究报告+》");
        }
        return resultNodes;
    }

    /**
     * 描述:适用于政府投资第二流程（签发是否通过）
     *
     * @author Madison You
     * @created 2020/3/13 10:46:00
     * @param 
     * @return 
     */
    @Override
    public Set<String> getIsQfPassSecond(Map<String, Object> flowVars) {
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

    @Override
    public Set<String> getIsConcertPassNewF(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub

        String XTSFTG = (String) flowVars.get("XTSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if(XTSFTG.equals("-1")){
            resultNodes.add("汇总 意见");
        }else{
            resultNodes.add("拟批复《立项用地规划综合审查意见书》");
        }
        return resultNodes;
    }
}
