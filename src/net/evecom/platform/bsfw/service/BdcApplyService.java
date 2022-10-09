/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述 不动产业务相关
 * @author Keravon Feng
 * @created 2018年11月23日 下午4:05:45
 */
public interface BdcApplyService extends BaseService {
    
    /**
     * 描述  不动产业务后置事件
     * @author Keravon Feng
     * @created 2018年11月26日 下午4:34:33
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveAfterBusData(Map<String, Object> flowDatas);
    
    /**
     * 描述 换发不动产权证书和不动产登记证明保存事件（多表）
     * @author Keravon Feng
     * @created 2019年3月18日 下午4:31:19
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas);
    
    /**
     * 描述 根据类别及业务主表ID获取业务数据
     * @author Keravon Feng
     * @created 2019年3月19日 下午1:18:11
     * @param type
     * @param ywId
     * @return
     */
    public Map<String, Object> getSubRecordInfo(String type,String ywId);

    /**
     * 描述 判断字典的名称是否存在
     * @author Keravon Feng
     * @created 2019年3月22日 下午5:24:48
     * @param parameter
     * @param parameter2
     * @return
     */
    public boolean isExist(String parameter, String parameter2);

    /**
     * 描述:延后时间
     *
     * @author Madison You
     * @created 2019/12/29 9:21:00
     * @param
     * @return
     */
    String addDateStr(String dateStr, int addTime);

    /**
     * 描述:不动产登记资料查询办结环节自动跳转
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/13 14:17:00
     */
    public List<Map<String, Object>> findNeedAutoJumpBdcdjzlcx();

    /**
     * 描述:不动产登记资料查询办结环节自动跳转
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/13 14:38:00
     */
    public void jumpTaskForBdcdjzlcx(Map<String, Object> data) throws Exception;

    /**
     * 描述:获取不动产查询附件url
     *
     * @author Madison You
     * @created 2020/7/21 20:34:00
     * @param
     * @return
     */
    public String getBdcImgFullUrl(String fileId);

    /**
     * 描述:在word文档中生成二维码
     *
     * @author Madison You
     * @created 2020/7/21 21:44:00
     * @param
     * @return
     */
    public void createBdcQueryQrcode(Map<String, Object> busInfo, Map<String, Object> returnMap , int type);

    /**
     * 描述:在word文档中插入图片
     *
     * @author Madison You
     * @created 2020/7/21 22:02:00
     * @param
     * @return
     */
    public void fillBdcQueryImg(Map<String, Object> returnMap, String fileId , String imgName);
    
   /**
    * 描述    并联审批是否通过（不动产权属来源审核表事宜）
    * @author Allin Lin
    * @created 2020年10月23日 下午4:22:13
    * @param flowVars
    * @return
    */
   public Set<String> getIsParallelAuditPass(Map<String, Object> flowVars);
   /**
    * 描述     并联审批是否通过（民宿办件旧）
    * @author Scolder Lin
    * @created 2021年6月1日 上午10:40:03
    * @param flowVars
    * @return
    */
   public Set<String> getIsBAndBPass(Map<String, Object> flowVars);

    /**
     * 描述:获取不动产在线监管总体情况
     *
     * @author Madison You
     * @created 2020/10/20 14:55:00
     * @param
     * @return
     */
    List<Map<String, Object>> getDjsjjgZtqkStatis(HttpServletRequest request);

    /**
     * 描述:获取不动产在线监管办事效能
     *
     * @author Madison You
     * @created 2020/10/22 17:08:00
     * @param
     * @return
     */
    Integer getDjsjjgBsxnStatis(String whereSql , String itemCode , String userName);

    /**
     * 描述:获取不动产在线监管近七天受理量
     *
     * @author Madison You
     * @created 2020/10/23 9:37:00
     * @param
     * @return
     */
    List<Integer> getDjsjjgJqtsllStatis(ArrayList<String> pastWeekDate,String itemCode,String userName);

    /**
     * 描述:获取不动产在线监管近七天评价
     *
     * @author Madison You
     * @created 2020/10/23 14:22:00
     * @param
     * @return
     */
    Map<String, Object> getDjsjjgFwpjStatis(HttpServletRequest request ,String itemCode ,String userName);

    /**
     * 描述:近七天平均受理时长
     *
     * @author Madison You
     * @created 2020/10/23 15:25:00
     * @param
     * @return
     */
    List<Integer> getDjsjjgJqtslscStatis(ArrayList<String> pastWeekDate);

    /**
     * 描述:近七天平均办结时长
     *
     * @author Madison You
     * @created 2020/11/24 15:05:00
     * @param
     * @return
     */
    List<Integer> getDjsjjgJqtbjscStatis(ArrayList<String> pastWeekDate);

    /**
     * 描述:获取不动产事项统计表事项
     *
     * @author Madison You
     * @created 2020/10/26 15:41:00
     * @param
     * @return
     */
    List<Map<String, Object>> getBdcsxtjbItems();

    /**
     * 描述:不动产事项统计表叫号状态
     *
     * @author Madison You
     * @created 2020/10/27 9:05:00
     * @param
     * @return
     */
    Map<String,Integer> getBdcsxtjbJhzt(String itemCode , String userName);

    /**
     * 描述:不动产事项统计表个人受理来源
     *
     * @author Madison You
     * @created 2020/10/27 10:01:00
     * @param
     * @return
     */
    ArrayList<ArrayList<Object>> getBdcsxtjbGrslly(ArrayList<String> pastWeekDate , String itemCode);

    /**
     * 描述:不动产事项统计表个人办件
     *
     * @author Madison You
     * @created 2020/10/27 10:49:00
     * @param
     * @return
     */
    List<Map<String, Object>> getBdcsxtjbGrbjl(String itemCode);

    /**
     * 描述:不动产事项统计表每日办件量
     *
     * @author Madison You
     * @created 2020/10/27 10:54:00
     * @param
     * @return
     */
    List<Map<String, Object>> getBdcsxtjbMrbjl(String itemCode);

    /**
     * 描述:获取不动产的所有用户
     *
     * @author Madison You
     * @created 2020/10/28 9:51:00
     * @param
     * @return
     */
    Set<Map<String,Object>> getUsersFromBdc();

    /**
     * 描述:获取经办人不动产事项统计
     *
     * @author Madison You
     * @created 2020/10/28 11:12:00
     * @param
     * @return
     */
    List<Map<String, Object>> getJbrsxtjList(String userName);

    /**
     * 描述:获取办件列表
     *
     * @author Madison You
     * @created 2020/11/18 15:08:00
     * @param
     * @return
     */
    List<Map<String, Object>> getExeList(SqlFilter filter);

    /**
     * 描述:获取基准地价
     *
     * @author Madison You
     * @created 2020/12/28 15:35:00
     * @param
     * @return
     */
    double getBenchmarkLandPrc(Map<String, Object> requestMap);

    /**
     * 描述:获取修正系数
     *
     * @author Madison You
     * @created 2020/12/28 16:32:00
     * @param
     * @return
     */
    double getCorrectionFactor(Map<String, Object> requestMap);

    /**
     * 描述:四舍五入到小数点后一位
     *
     * @author Madison You
     * @created 2020/12/28 16:45:00
     * @param
     * @return
     */
    double roundToOneDecimalPlce(double value);

    /**
     * 描述:计算单位集资房
     *
     * @author Madison You
     * @created 2020/12/29 8:52:00
     * @param
     * @return
     */
    String calUnitFundraisingHouse(Map<String, Object> requestMap);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/29 9:35:00
     * @param
     * @return
     */
    String calLandPrc(Map<String, Object> requestMap);

    /**
     * 描述:计算违建面积
     *
     * @author Madison You
     * @created 2021/3/29 14:34:00
     * @param
     * @return
     */
    String calLandPrcWjbj(Map<String, Object> requestMap);

}
