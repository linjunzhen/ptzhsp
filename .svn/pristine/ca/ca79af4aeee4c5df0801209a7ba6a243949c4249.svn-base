/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年7月3日 下午3:58:22
 */
public interface NewCallService extends BaseService {

    /**
     * 
     * 描述    获取取号单位/部门
     * @author Danto Huang
     * @created 2018年7月4日 上午9:22:28
     * @param roomNo
     * @return
     */
    public List<Map<String,Object>> findTakeNoDepart(String roomNo);
    /**
     * 
     * 描述    根据单位/部门id获取取号业务
     * @author Danto Huang
     * @created 2018年7月4日 上午11:09:35
     * @param departId
     * @return
     */
    public List<Map<String,Object>> findBusinessByDepartId(String departId);
    /**
     * 
     * 描述    是否窗口在受理业务
     * @author Danto Huang
     * @created 2018年7月5日 下午3:05:38
     * @param businessCode
     * @return
     */
    public boolean isWinAccept(String businessCode);
    /**
     * 
     * 描述    是否已在等待队列中
     * @author Danto Huang
     * @created 2018年7月5日 下午3:15:10
     * @param businessCode
     * @param cardNo
     * @return
     */
    public boolean isWaiting(String businessCode,String cardNo);
    /**
     * 
     * 描述    生成排队号
     * @author Danto Huang
     * @created 2018年7月5日 下午3:39:44
     * @param businessCode
     * @return
     */
    public Map<String,Object> getLineNo(Map<String,Object> variables);
    /**
     * 
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param businessCode
     * @return
     */
    public int getWaitingNumByBusinessCode(String businessCode);
    /**
     *
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param roomNo
     * @return
     */
    public int getWaitingNumByRoomNo(String roomNo);
    /**
     * 
     * 描述    获取排队号留意窗口
     * @author Danto Huang
     * @created 2018年7月5日 下午4:10:52
     * @param businessCode
     * @return
     */
    public String getCareWin(String businessCode);
    /**
     * 
     * 描述    获取取号数据列表
     * @author Danto Huang
     * @created 2018年7月5日 下午4:35:01
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findQueueListBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述    定时过号
     * @author Danto Huang
     * @created 2018年7月6日 上午8:57:02
     * @param date
     */
    public void overDueQueue(String date);
    /**
     * 
     * 描述    窗口选择列表
     * @author Danto Huang
     * @created 2018年7月6日 上午11:11:34
     * @return
     */
    public List<Map<String,Object>> findUserWin();
    /**
     * 
     * 描述    获取窗口办件叫号列表
     * @author Danto Huang
     * @created 2018年7月6日 下午4:38:27
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingBySqlFilter(SqlFilter sqlFilter);
    /**
     * 根据身份证号获取业务排队前面排队人数
     * @param cardId
     * @return
     */
    public List<Map<String,Object>> getBusWaitNumByCardId(String cardId);
    /**
     * 根据排队号获取业务排队排队人数
     * @param LineNo
     * @return
     */
    public Map<String,Object>  getBusWaitNumByLineNo(String LineNo);
    /**
     * 通知有绑定OPENID的微信号
     * @param recordId
     * @return
     */
    public String notifyWxHasOpenId(String recordId);
    /**
     * 根据业务编号来获取需要被通知的公众号排队信息
     * @param businessCode
     * @return
     */
    public List<Map<String,Object>> findWxNotifyByBusinessCode(String businessCode);
    /**
     * 更新openId
     * @param variable
     */
    public void updateOpenId(Map<String,Object> variable);
    /**
     * 
     * 描述    获取已办理记录列表（当天）
     * @author Danto Huang
     * @created 2018年7月9日 上午9:38:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingDayDoneBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    获取已办理记录列表
     * @author Danto Huang
     * @created 2018年7月9日 上午9:38:19
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingDoneBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    获取转发记录列表
     * @author Danto Huang
     * @created 2018年7月9日 下午5:38:11
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findQueuingFowardBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    获取网上预约记录
     * @author Danto Huang
     * @created 2018年7月20日 下午3:39:58
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAppointmentBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述    获取重新取号列表（已经取号但未叫号）
     * @author Danto Huang
     * @created 2018年7月20日 下午5:41:34
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findAgainDataBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述    获取转发窗口
     * @author Danto Huang
     * @created 2018年9月5日 上午10:00:16
     * @param businessCode
     * @return
     */
    public List<Map<String,Object>> findWinsForSelect(String businessCode);
    /**
     * 
     * 描述    获取转发窗口
     * @author Danto Huang
     * @created 2018年9月5日 上午10:00:16
     * @param businessCode
     * @return
     */
    public List<Map<String,Object>> findWinsForSelectYctb(String businessCode);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月10日 下午6:49:12
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findPrintRcordBySqlFilter(SqlFilter filter);
    public List<Map<String, Object>> findLineUpNumBySqlFilter(SqlFilter filter);
    public List<Map<String, Object>> getBeforByLineNo(String businessCode, String lineNo);
    
    /**
     * 
     * 描述    排队绑定微信OPENID
     * @author Danto Huang
     * @created 2018年7月5日 下午3:39:44
     * @param variables
     * @param openId
     * @return
     */
    public String bindOpenId(Map<String,Object> variables,String recordId);
    public List<Map<String, Object>> findYctbItemDataBySqlFilter(SqlFilter filter);
    public List<Map<String, Object>> findByItemCode(String itemCode);
    public List<Map<String, Object>> findYctbBusinessItemDataBySqlFilter(SqlFilter filter);
    public List<Map<String, Object>> findMaterByItemCode(String itemCode);
    public List<Map<String, Object>> findYctbBusinessItemDataBySqlFilter2(SqlFilter filter);
    public List<Map<String, Object>> findKByWinNo(String winNo);
    public List<Map<String, Object>> findYctbWaitingList();
    public List<Map<String, Object>> findABWaitingList();
    public List<Map<String, Object>> findOtherWaitingList();
    
    /**
     * 
     * 描述    获取一窗通办当前办理排队号的待办理事项列表
     * @author Danto Huang
     * @created 2019年3月13日 下午4:31:08
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findCurLineSelectedItems(SqlFilter filter);
    /**
     * 
     * 描述    根据排队记录ID更新一窗通办当前办理叫号的状态
     * @author Danto Huang
     * @created 2019年3月14日 下午3:30:27
     * @param lineId
     */
    public void updateQueueRecordByDeatil(String lineId);
    /**
     * 
     * 描述    判断当前窗口所在分组是否有在办快件业务
     * @author Danto Huang
     * @created 2019年3月19日 下午4:21:55
     * @param curWin
     * @return
     */
    public Map<String,Object> isExpressItemDealing(String curWin);
    /**
     * 
     * 描述    一窗通办窗口等待列表
     * @author Danto Huang
     * @created 2019年3月21日 上午11:51:08
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findYctbQueuingBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    获取窗口当前排队总数
     * @author Danto Huang
     * @created 2019年3月21日 上午11:25:24
     * @param winNo
     * @param businessCode
     * @return
     */
    public int getTotalQueuingByWinBusiness(String winNo, String businessCode);

//    /**
//     * 描述:检测是否是失信人员
//     *
//     * @param
//     * @return
//     * @author Madison You
//     * @created 2019/12/9 18:01:00
//     */
//    public Map<String, Object> checkLostPromise(String lineName, String lineCardNo, String itemName, String itemCode);
//
//    /**
//     * 描述:失信人员判定
//     *
//     * @author Madison You
//     * @created 2019/12/11 8:42:00
//     * @param
//     * @return
//     */
//    Map<String, Object> checkLostPromise(String lineName, String lineCardNo, String departId);
//
//    /**
//     * 描述:失信人员判定 业务
//     *
//     * @author Madison You
//     * @created 2019/12/11 16:33:00
//     * @param
//     * @return
//     */
//    Map<String, Object> checkLostPromiseByBus(String lineName, String lineCardNo,
//                                              String businessCode, String businessName);

    /**
     * 描述:根据业务配置进行失信人判断
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/6/23 16:55:00
     */
    Map<String, Object> checkLostPromiseByBusCode(String lineName, String lineCardNo, String businessCode);


    /**
     * 描述:差评数据即时发送短信提醒
     *
     * @author Madison You
     * @created 2020/11/23 10:51:00
     * @param
     * @return
     */
    void sendSmsNagetiveEvaluate(String recordId, String eval, String evaluateTime);
    
    /**
     * 
     * 描述    叫号短信发送记录数据
     * @author Allin Lin
     * @created 2021年3月23日 上午9:40:40
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findlineMsgRcordBySqlFilter(SqlFilter filter);
    
    /**
     * 
     * 描述    失信人信息查询Token
     * @author Allin Lin
     * @created 2021年3月23日 上午9:40:40
     * @param filter
     * @return
     */
    public String getCreditToken();
}
