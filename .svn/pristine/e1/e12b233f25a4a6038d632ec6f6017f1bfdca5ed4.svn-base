/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 描述 不动产全流程申请业务相关方法
 * 
 * @author Keravon Feng
 * @created 2019年10月8日 上午10:45:59
 */
public interface BdcQlcApplyService extends BaseService {
    /**
     * 
     * 描述 不动产全流程申请多表业务通用存储事件接口方法
     * 
     * @author Keravon Feng
     * @created 2019年10月8日 上午10:50:00
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas);

    /**
     * 描述 获取子表的数据
     * 
     * @author Keravon Feng
     * @created 2019年10月10日 下午2:48:46
     * @param itemcode
     * @param type
     * @param busId
     * @return
     */
    public Map<String, Object> getSubRecordInfo(String itemcode, String type, String busId);

    /**
     * 
     * 描述 换发产权证书与不动产预告登记 是否复审
     * 
     * @author Keravon Feng
     * @created 2019年11月5日 上午10:55:36
     * @param flowVars
     * @return
     */
    public Set<String> getHfIsPreFuShen(Map<String, Object> flowVars);

    /**
     * 
     * 描述 换发产权证书与不动产预告登记 是否发证
     * 
     * @author Keravon Feng
     * @created 2019年11月5日 上午11:45:39
     * @param flowVars
     * @return
     */
    public Set<String> getHfIsPreFz(Map<String, Object> flowVars);

    /**
     * 
     * 描述 国有建设用地及房屋所有权转移登记 是否初审决策
     * 
     * @author Keravon Feng
     * @created 2019年12月13日 上午10:12:28
     * @param flowVars
     * @return
     */
    public Set<String> getIsPreChuShen(Map<String, Object> flowVars);

    /**
     * 描述 国有建设用地及房屋所有权转移登记 是否完税 决策
     * 
     * @author Keravon Feng
     * @created 2019年12月13日 上午10:13:55
     * @param flowVars
     * @return
     */
    public Set<String> getIsFinish(Map<String, Object> flowVars);

    /**
     * 不动产全流程业务涉及缴费发证等业务需要后台填报的部分
     * 
     * @author Keravon Feng
     * @created 2019年12月17日 上午11:17:27
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterBusData(Map<String, Object> flowDatas);

    /**
     * 
     * 描述：不动产登薄前置接口
     * 
     * @author Rider Chen
     * @created 2020年8月27日 上午10:24:30
     * @param flowDatas
     * @return
     */
    public Map<String, Object> beforeBdcdb(Map<String, Object> flowDatas) throws Exception;

    /**
     * 
     * 描述：登薄成功自动办结后置事件
     * 
     * @author Rider Chen
     * @created 2020年8月27日 下午1:59:10
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterBdcdbEnd(Map<String, Object> flowDatas);

    /**
     * 
     * 描述：
     * 
     * @author Rider Chen
     * @created 2020年8月28日 上午11:08:22
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterBdcdbAutoProcess(Map<String, Object> flowDatas);
    
    
    /**
     * 描述    获取待受理需要自动跳转记录
     * @author Allin Lin
     * @created 2020年9月10日 上午9:24:34
     * @return
     */
    public List<Map<String, Object>> findNeedAutoJump();
    
    /**
     * 描述    待受理自动跳转
     * @author Allin Lin
     * @created 2020年9月10日 上午10:25:20
     * @param data
     */
    public void jumpTaskToBdcSl(Map<String, Object> data)throws Exception;
    
    
   /**
    * 描述   不动产登薄前置接口(待受理)
    * @author Allin Lin
    * @created 2020年9月10日 下午9:29:17
    * @param flowDatas
    * @return
    * @throws Exception
    */
    public Map<String, Object> beforeBdcdbAuto(Map<String, Object> flowDatas) throws Exception;

    /**
     * 描述:不动产登薄前置接口(待受理)（用于类似国有转移表单结构的登簿，权利人可能为两个人，登簿在权利人信息中）
     *
     * @author Madison You
     * @created 2020/9/17 14:32:00
     * @param
     * @return
     */
    public Map<String, Object> beforeBdcdbAutoGyzy(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 描述    不动产前台申报开始环节审核人固定（平潭综合实验区行政服务中心）
     * @author Allin Lin
     * @created 2020年9月14日 上午9:07:09
     * @param flowDatas
     * @return
     */
    public Map<String, Object> bdcChangeGdShr(Map<String, Object> flowDatas);
    
    /**
     * 描述    获取平潭通网上预审需要自动跳转记录
     * @author Allin Lin
     * @created 2020年9月10日 上午9:24:34
     * @return
     */
    public List<Map<String, Object>> findPttNeedAutoJump();
    
    /**
     * 描述    网上预审自动跳转（平潭通智能审批）
     * @author Allin Lin
     * @created 2020年9月10日 上午10:25:20
     * @param data
     */
    public void jumpTaskToBdcDsl(Map<String, Object> data);
    
    /**
     * 
     * 描述    不动产全程网办待受理签章成功-自动流转至登簿环节（目前应用于预抵、首登子项全程网办个性化流程）
     * @author Allin Lin
     * @created 2020年12月17日 下午4:07:50
     * @param flowDatas
     * @return
     */
    public Map<String, Object> bdcQcwbDslAutoProcess(Map<String, Object> flowDatas);
    
    /**
     * 
     * 描述    保存不动产智能审批业务的额外需要自动生成的字段
     * @author Allin Lin
     * @created 2020年12月18日 上午11:25:07
     * @param flowVars
     * @return
     */
     public Map<String,Object> saveBdcZnspElseField(Map<String, Object> flowVars);

    /**
     * 描述:登簿成功保存登簿信息
     *
     * @author Madison You
     * @created 2021/1/11 10:26:00
     * @param
     * @return
     */
    void saveDbInfo(String eveId, String retJson);
    
    /**
     * 国有建设用地使用权及房屋所有权转移登记-全流程  办结后置短信下发事件
     * 
     * @author Scolder Lin
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveAfterBusData2(Map<String, Object> flowDatas) throws Exception;
}
