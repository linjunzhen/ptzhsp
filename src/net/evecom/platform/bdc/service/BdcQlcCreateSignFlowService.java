/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;
import net.evecom.platform.bdc.model.SignFlowBody;
import net.evecom.platform.bdc.model.SignInfo;

import java.util.List;
import java.util.Map;

/**
 * 描述  不动产全流程全程网办创建签署流程Service
 * @author Allin Lin
 * @created 2020年8月15日 上午11:07:27
 */
public interface BdcQlcCreateSignFlowService extends BaseService {
    /**
     * 签章X轴偏移量
     */
    public static int X_OFFSET_65=59;  
    
    /**
     * 签章X轴偏移量
     */
    public static int X_OFFSET_80=80;

    /**
     * 签章y轴偏移量
     */
    public static int Y_OFFSET_25=-25;

    /**
     * 签章y轴偏移量因子
     */
    public static int Y_OFFSET_FACTOR=2;
    /**
     * 签章X轴绝对位置偏移量
     */
    public static int X_ABSOLUTE_OFFSET_300=300;

    /**
     * 签章y轴绝对位置偏移量
     */
    public static int Y_ABSOLUTE_OFFSET_350=350;
    /**
     * 申请书材料名称
     */
    public static String SQS_MATERNAME="申请表";
    /**
     * 询问笔录材料名称
     */
    public static String XWBL_MATERNAME="询问笔录";
    /**
     * 申请书材料关键字
     */
    public static String SQS_KEYWORD="申请人（签章）";

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/9/14 16:44:00
     * @param 
     * @return 
     */
    public static String SQS_KEYWORD1 = "代理人（签章）";
    /**
     * 询问笔录材料关键字
     */
    public static String XWBL_KEYWORD="被询问人签名（签章）";
    /**
     * 申请书材料名称
     */
    public static String BANKSQS_MATERNAME="银行申请表";
    /**
     * 申请书材料关键字
     */
    public static String BANKSQS_KEYWORD="代理人：（签章）";

    /**
     * 银行询问人签字：关键字
     */
    public static String BANKSQS_KEYWORD_XWRQZ="询问人签字：";
    /**
     * 抵押期限声明材料名称
     */
    public static String QYQXSQS_MATERNAME="抵押期限申明";
    /**
     * 抵押期限声明材料关键字
     */
    public static String QYQXSQS_KEYWORD="我已知晓并同意";
    /**
     * 关于无法提供“预购商品房贷款抵押登记证明书”的说明
     */
    public static String WFTGSM_MATERNAME="无法提供预购商品房贷款抵押声明";
    /**
     * 关于无法提供“预购商品房贷款抵押登记证明书”的说明 关键字
     */
    public static String WFTGSM_KEYWORD="我已知晓并同意";
    /**
     * 拆迁安置房预告登记约定书  材料名称
     */
    public static String YGAZF_MATERNAME="预告安置房登记约定书";
    /**
     * 拆迁安置房预告登记约定书  材料名称
     */
    public static String YGSPF_MATERNAME="预告商品房登记约定书";
    /**
     * 预告约定书关键字-----“出让人：”
     */
    public static String YGYDS_KEYWORD_CRR="出让人：";
    /**
     * 预告约定书关键字-----“买受人：”
     */
    public static String YGYDS_KEYWORD_MSR="买受人：";
    /**
     * 预告约定书关键字-----“代理人：”
     */
    public static String YGYDS_KEYWORD_DLR="代理人：";
    /**
     *预告不动产登记申请表 材料名称
     */
    public static String YGSQS_MATERNAME="预告不动产登记申请表";

    /**
     *预告不动产登记申请表关键字 被询问人
     */
    public static String YGSQS_KEYWORD_BXWR="被询问人";
    /**
     *预告不动产登记申请表关键字 代理人：（签章）
     */
    public static String YGSQS_KEYWORD_DLR="代理人：（签章）";

    /**
     * 拆迁安置房预抵登记约定书  材料名称
     */
    public static String YDAZF_MATERNAME="预抵安置房登记约定书";
    /**
     * 拆迁安置房预抵登记约定书  材料名称
     */
    public static String YDSPF_MATERNAME="预抵商品房登记约定书";
    /**
     * 预抵约定书关键字-----“抵押人：”
     */
    public static String YDYDS_KEYWORD_DYR="抵押人：";
    /**
     * 预抵约定书关键字-----“抵押权人：”
     */
    public static String YDYDS_KEYWORD_DYQR="抵押权人：";
    /**
     * 预抵约定书关键字-----“代理人：”
     */
    public static String YDYDS_KEYWORD_DLR="代理人：";
    /**
     *预抵不动产登记申请表 材料名称
     */
    public static String YDSQS_MATERNAME="预抵不动产登记申请表";

    /**
     *预抵不动产登记申请表关键字 被询问人
     */
    public static String YDSQS_KEYWORD_BXWR="被询问人";
    /**
     *预抵不动产登记申请表关键字 代理人：（签章）
     */
    public static String YDSQS_KEYWORD_DLR="代理人：（签章）";
    
    /**
     *抵押权注销登记注销申请表关键字 被询问人签名
     */
    public static String ZXSQS_KEYWORD_BXWR="被询问人签名";
    
    /**
     *抵押权注销登记注销申请表关键字 代理人：（签章）
     */
    public static String ZXSQS_KEYWORD_DLR="代理人：（签章）";
    
    /**
     *抵押权注销登记结清证明关键字 同意办理
     */
    public static String ZXJQZM_KEYWORD="同意办理";
    
    
    /**
     *国有建设用地使用权首次登记-不动产登记申请书关键字 代理人（被询问人）
     */
    public static String GYSCDJSQS_KEYWORD_BXWR="（被询问人）（签章）：";
    
    
    /**
     *国有建设用地使用权首次登记-授权委托书关键字  签名（或签章）
     */
    public static String GYSCDJWTS_KEYWORD="签名（或盖章）";
    
    
    /**
     *国有建设用地使用权及房屋所有权首次登记-不动产登记申请书关键字 代理人（被询问人）
     */
    public static String GYJSYDJFWSCDJSQS_KEYWORD_BXWR="（被询问人）（签章）：";
    
    /**
     * 签章流程启动
     *
     * @param signFlowBody
     * @throws Exception
     */
    public Map<String, Object> createSignFlow(SignFlowBody signFlowBody) throws Exception;


    /**
     * 创建询问笔录签章流程接口
     */
    public Map<String, Object> createSignFlowOfXwbl(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;

    /**
     * 创建申请表签章流程接口
     */
    public Map<String, Object> createSignFlowOfSqb(Map<String, Object> flowVars,
                                                   Map<String, Object> returnMap) throws Exception;
    /**
     * 描述:存量房申请表
     *
     * @author Madison You
     * @created 2020/9/14 9:46:00
     * @param
     * @return
     */
    public Map<String, Object> createSignFlowOfSqb1(Map<String, Object> flowVars,
                                                    Map<String, Object> returnMap) throws Exception;

    /**
     * 创建申请书签章流程接口
     *
     * @param exeId       办件id
     * @param materName   材料名称
     * @param sqrInfoList 签章信息人列表(sqrzjhm:申请人证件号码；signPos：签章位置信息集合）
     * @return
     * @throws Exception
     */
    public Map<String, Object> createSignFlowByPeoplesAndKeyword(String exeId, String materName
            , List<SignInfo> sqrInfoList) throws Exception;
    /**
     * 创建银行申请表签章流程接口(国有转移)
     */
    public Map<String,Object>  createSignFlowOfBankSqb(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 创建抵押期限申请表签章流程接口
     */
    public Map<String,Object>  createSignFlowOfDyqxSqb(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 无法提供预购商品房贷款抵押声明签章接口
     */
    public Map<String,Object>  createSignFlowOfWftgsmSqb(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 创建预告约定书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYgyds(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 创建预告不动产申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYgbdcsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 创建预抵约定书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYdyds(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 创建预抵不动产申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfYdbdcsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    /**
     * 创建指定位置申请表签章流程接口
     */
    public Map<String,Object>  createSignFlowOfAbsolutePos(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    
    /**
     * 创建抵押权注销登记-注销申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfDyqzxdjsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    
    /**
     * 创建抵押权注销登记-结清证明签章流程接口
     */
    public Map<String,Object>  createSignFlowOfDyqzxdjjqzm(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    
    /**
     * 创建国有建设用地使用权首次登记-不动产登记申请书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfGyjsscdjsqs(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    
    /**
     * 创建国有建设用地使用权首次登记-授权委托书签章流程接口
     */
    public Map<String,Object>  createSignFlowOfGyjsscdjwts(Map<String, Object> flowVars, Map<String, Object> returnMap)
            throws Exception ;
    
    /**
     * 创建国有建设用地使用权及房屋所有权首次登记-不动产登记申请书签章流程接口
     *//*
    public Map<String,Object>  createSignFlowOfGyjsydjfwscdjsqs(Map<String, Object> flowVars,
            Map<String, Object> returnMap)throws Exception;*/
    
    /**
     * 创建抵押权首次登记-银行申请表签章流程接口
     */
    public Map<String, Object> createSignFlowOfDyqscdjBankSqb(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 存量房税费联办业务-税务证明事项告知承诺书签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfSwzmCns(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 存量房税费联办业务-存量房评估补充信息表签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfPgBcXx(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 存量房税费联办业务-家庭唯一生活用房承诺书签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfJtWyYfCns(Map<String, Object> flowVars,
            Map<String, Object> returnMap,Map<String, Object> qlrMap,int index) throws Exception;
    
    
    /**
     * 存量房税费联办业务-个人无偿赠与不动产登记表签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfBdcZyDjb(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 存量房税费联办业务-实名办税授权委托书签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfSmBsSqWts(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;
    
    
    /**
     * 存量房税费联办业务-不动产权属转移涉税补充信息确认单签章流程接口
     */
    public Map<String, Object> createSignFlowOfClfSsbcxxQrd(Map<String, Object> flowVars,
            Map<String, Object> returnMap) throws Exception;
    
    
}