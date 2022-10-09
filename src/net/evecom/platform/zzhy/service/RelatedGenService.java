/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年9月19日 下午3:11:08
 */
public interface RelatedGenService extends BaseService {

    /**
     * 
     * 描述 汽车租赁经营许可申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午5:26:27
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genCarRental(Map<String, Object> param, String filepathString, String destpathString);
    /**
     * 
     * 描述 道路货物运输经营申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genRftApply(Map<String, Object> param, String filepathString, String destpathString);
    /**
     * 
     * 描述 道路危险货物运输经营申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genRdgtApply(Map<String, Object> param, String filepathString, String destpathString);
    /**
     * 描述
     * @author Faker Li
     * @created 2017年9月20日 下午5:32:35
     * @param relatedMater
     * @param fileRullPath
     * @param string
     */
    public void genJdcjssysRental(Map<String, Object> relatedMater,
            String fileRullPath, String string);
    /**
     * 
     * 描述 道路货物运输站经营申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     */
    public void genDLLKYSZApply(Map<String, Object> param, 
            String filepathString, String destpathString);
    /**
     * 
     * 描述 道路货物运输站经营申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     * @param param
     */
    public void genMBRAPPLYApply(Map<String, Object> relatedMater, 
            String fileRullPath, String string);
    /**
     * 
     * 描述 道路旅客运输班线经营申请表数据处理
     * @author Curtis Chen
     * @created 2017年9月21日 14:45:16
     */
    public void genRPTMApply(Map<String, Object> relatedMater,
            String fileRullPath, String string);

    /**
     * 
     * 描述 三类汽车维修业户经营许可登记申请表
     * @author Danto Huang
     * @created 2017年9月19日 下午6:21:50
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genCcrApply(Map<String, Object> param, String filepathString, String destpathString);
}
