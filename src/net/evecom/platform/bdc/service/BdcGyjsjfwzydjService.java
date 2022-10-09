/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 描述 不动产全流程 国有建设用地及房屋转移登记Service
 * 
 * @author Roger Li
 * @created 2020年1月6日 下午3:44:45
 */
public interface BdcGyjsjfwzydjService extends BaseService {
    
    /**
     * 税务事项证明告知承诺书模板申请-key
     */
    public static String SWZMCNS_KEY="SWZMSXGZCNS.docx";
    
    /**
     * 税务事项证明告知承诺书模板申请-key
     */
    public static String SWZMCNS_MATERNAME="税务事项证明告知承诺书";
    
    /**
     * 税务事项证明告知承诺书模板申请-key
     */
    public static String SWZMCNS_KEYWORD="纳税人：";
    
    /**
     * 存量房评估补充信息表-key
     */
    public static String CLFPGBCXX_KEY="CLFPGXXBCB.docx";
    
    /**
     * 存量房评估补充信息表-key
     */
    public static String CLFPGBCXX_MATERNAME="存量房评估补充信息表";
    
    /**
     * 存量房评估补充信息表-key
     */
    public static String CLFPGBCXX_KEYWORD="申请人(签字)：";
    
    /**
     * 家庭唯一生活用房承诺书-key
     */
    public static String WYSFYFCNS_KEY="JTWYSHYFCNS.docx";
    
    /**
     * 家庭唯一生活用房承诺书-key
     */
    public static String WYSFYFCNS_MATERNAME="家庭唯一生活用房承诺书";
    
    /**
     * 家庭唯一生活用房承诺书-key
     */
    public static String WYSFYFCNS_KEYWORD="承诺人（签字）：";
    
    /**
     * 个人无偿赠与不动产登记表-key
     */
    public static String BDCZY_KEY="FCZYDJB.docx";
    
    /**
     * 个人无偿赠与不动产登记表-key
     */
    public static String BDCZY_MATERNAME="个人无偿赠与不动产登记表";
    
    /**
     * 个人无偿赠与不动产登记表-key
     */
    public static String BDCZY_KEYWORD="受赠人：";
    
    /**
     * 实名办税授权委托书-key
     */
    public static String SMBSSQWTS_KEY="SMBSSQWTS.docx";
    
    /**
     * 实名办税授权委托书-key
     */
    public static String SMBSSQWTS_MATERNAME="实名办税授权委托书";
    
    /**
     * 实名办税授权委托书-key
     */
    public static String SMBSSQWTS_KEYWORD="授权人：（公司盖章及法定代表人签名）";
    
    /**
     * 不动产权属转移涉税补充信息填写确认单-key
     */
    public static String SSBCXXQRD_KEY="SSBCXXQRD.docx";
    
    /**
     * 不动产权属转移涉税补充信息填写确认单-key
     */
    public static String SSBCXXQRD_MATERNAME="不动产权属转移涉税补充信息填写确认单";
    
    /**
     * 不动产权属转移涉税补充信息填写确认单-key
     */
    public static String SSBCXXQRD_KEYWORD="申请人签章：";
    
    

    /**
     * 
     * 描述 不动产登记审批表两审数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月30日 下午5:52:05
     * @param args
     */
    public void setSecondAudit(Map<String, Map<String, Object>> args);

    /**
     * 
     * 描述 不动产登记审批表三审数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月30日 下午5:52:05
     * @param args
     */
    public void setThirdAudit(Map<String, Map<String, Object>> args);

    /**
     * 
     * 描述 打印抵押物明细_审批表数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月31日 下午2:32:19
     * @param args
     */
    public void setDYMXAudit(Map<String, Map<String, Object>> args);

    /**
     * 
     * 描述 打印抵押物明细_在建工程数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月31日 下午2:32:19
     * @param args
     */
    public void setDYMXBuilding(Map<String, Map<String, Object>> args);

    /**
     * 
     * 描述 根据实例Id获取task列表
     * 
     * @author Roger Li
     * @created 2019年12月27日 下午4:23:29
     * @param exeid
     * @return
     */
    public List<Map<String, Object>> findAuditTaskListByExeId(String exeid);

    /**
     * 描述:设置国有建设及房屋转移登记通用数据
     *
     * @author Madison You
     * @created 2020/4/30 9:08:00
     * @param
     * @return
     */
    public void setGyjsjfwzydjtyData(Map<String, Map<String, Object>> args);

    /**
     * 描述:获取宗地坐落数据
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/6/21 14:10:00
     */
    List<Map<String, Object>> finZdzlData(String value, String typeCode);

    /**
     * 描述:查询已在办理此不动产单元号的业务
     *
     * @author Madison You
     * @created 2020/6/28 16:34:00
     * @param
     * @return
     */
    List<String> findIsDealBdcdyh(String bdcdyh);

    /**
     * 描述:获取不动产银行列表
     *
     * @author Madison You
     * @created 2020/8/18 9:20:00
     * @param
     * @return
     */
    List<Map<String, Object>> getBdcBankList(HttpServletRequest request);

    /**
     * 描述:回填不动产签章材料数据
     *
     * @author Madison You
     * @created 2020/8/18 14:43:00
     * @param
     * @return
     */
    void initBdcFieldValue(Map<String, Object> returnMap, Map<String, Object> flowVars);

    /**
     * 描述:保存国有转移的额外需要自动生成的字段
     *
     * @author Madison You
     * @created 2020/10/22 14:18:00
     * @param
     * @return
     */
    Map<String,Object> saveGyjsjfwzyElseField(Map<String, Object> flowVars);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/1 14:44:00
     * @param 
     * @return 
     */
    List<Map<String, Object>> getTaxRelatedFileList(String fileIds);
    
    /**
     * 
     * 描述    国有转移6个事项业务数据存储
     * @author Allin Lin
     * @created 2021年9月9日 上午11:36:06
     * @param flowDatas
     * @return
     */
     @SuppressWarnings("unchecked")
     public Map<String, Object> saveBusData(Map<String, Object> flowDatas);
     
     /**
      * 
      * 描述      提取指定路径excel文件中的数据
      * @author Allin Lin
      * @created 2021年10月15日 下午5:21:34
      * @param path  文件相对路径
      * @param rowNum  开始行数
      * @return
      */
     public List<Map<String, Object>> getExecelDataList(String path,int rowNum);
     
     
     /**
      * 
      * 描述    存量房税费联办-涉税申报在线签章所有用户创建
      * @author Allin Lin
      * @created 2021年10月25日 上午11:40:38
      * @param flowVars
      * @param returnMap
      */
     public void clfsflbSssbSignUser(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception;
     
     /**
      * 
      * 描述    来源平潭通的办件下发短信告知工作人员需要预审
      * @author Allin Lin
      * @created 2021年12月7日 上午10:21:53
      * @param flowVars
      */
     public Map<String, Object> sendMsgToJb(Map<String, Object> flowVars);
    /**
     * 
     * 描述  创建通用模板签章数据
     * @author Yanisin Shi
     * @param returnMap
     * @param flowVars
     */
     public void initBdcGeneralFieldValue(Map<String, Object> returnMap, Map<String, Object> flowVars);
}
