/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述:不动产全流程审批表打印基本配置
 *
 * @author Madison You
 * @created 2020/04/26 15:27
 */
public interface BdcSpbPrintConfigService extends BaseService {

    /**
     * 描述:不动产全流程格式化字符串
     *
     * @author Madison You
     * @created 2020/4/26 17:11:00
     * @param
     * @return
     */
    public String bdcStringOutFormate(StringBuffer str);

    /**
     * 描述:不动产全流程日期格式转换
     *
     * @author Madison You
     * @created 2020/4/26 17:12:00
     * @param
     * @return
     */
    public String bdcDateFormat(String dateStr, String oldFormat, String newFormat);

    /**
     * 描述:不动产全流程获取子表业务数据
     *
     * @author Madison You
     * @created 2020/4/26 17:13:00
     * @param
     * @return
     */
    public Map<String, Object> bdcGetChildTableBusInfo(String exeId, String childTableName);

    /**
     * 描述:不动产全流程获取登簿环节受理信息
     *
     * @author Madison You
     * @created 2020/4/26 17:14:00
     * @param
     * @return
     */
    public Map<String,Object> bdcGetDbNodeInfo(String exeId);

    /**
     * 描述:不动产全流程根据环节名称获取某一环节受理信息
     *
     * @author Madison You
     * @created 2020/4/26 17:14:00
     * @param
     * @return
     */
    public Map<String, Object> bdcGetNodeInfo(String exeId, String nodeName);

    /**
     * 描述:初始化审批表字段
     *
     * @author Madison You
     * @created 2020/4/27 8:43:00
     * @param
     * @return
     */
    public void bdcInitSpbVariables(Map<String, Object> returnMap);

    /**
     * 描述:生成审批表
     *
     * @author Madison You
     * @created 2020/4/27 10:59:00
     * @param
     * @return
     */
    public void bdcCreateSpbConfig(Map<String, Object> returnMap);

    /**
     * 描述:生成二维码
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/6/16 14:17:00
     */
    public void createQrCode(Map<String, Object> returnMap, String qrCodeValue);

    /**
     * 描述:获取业务数据
     *
     * @author Madison You
     * @created 2020/4/27 11:02:00
     * @param
     * @return
     */
    public Map<String, Object> bdcGetBusInfo(Map<String, Object> flowAllObj);

    /**
     * 描述:获取某一环节的的审核信息(审核人员，审核时间，审核意见)
     *
     * @author Madison You
     * @created 2020/4/28 8:40:00
     * @param
     * @return
     */
    public void getTaskInfo(Map<String, Object> returnMap , String nodeName);

    /**
     * 描述:获取所有收取的材料名称填入登记原因证明文件字段
     *
     * @author Madison You
     * @created 2020/6/2 9:24:00
     * @param
     * @return
     */
    public String getMaterNameList(Map<String,Object> returnMap);

    /**
     * 描述:签名替换
     *
     * @author Madison You
     * @created 2020/7/3 10:49:00
     * @param busInfo
     * @param returnMap
     * @param signColumn 签名人ID
     * @param fillColumn 签名需回填字段
     * @return
     */
    public void fillInOpinionSign(Map<String, Object> busInfo, Map<String, Object> returnMap,
                                  String signColumn, String fillColumn);

    /**
     * 描述:自动回填不动产登记审核意见
     *
     * @author Madison You
     * @created 2020/6/12 20:00:00
     * @param
     * @return
     */
    void getbdcDjshOpinion(Map<String, Object> busInfo , Map<String, Object> returnMap);

    /**
     * 描述:获取url文件字节码
     *
     * @author Madison You
     * @created 2020/7/3 10:50:00
     * @param
     * @return
     */
    public byte[] getByteFileFromUrl(String filePath);

    /**
     * 描述:问询记录字段替换
     *
     * @author Madison You
     * @created 2020/8/17 14:16:00
     * @param
     * @return
     */
    void fillWxjlForm(Map<String, Object> returnMap, Map<String, Object> busInfo);

    /**
     * 描述:询问记录是否选择框
     *
     * @author Madison You
     * @created 2020/8/17 14:30:00
     * @param
     * @return
     */
    public void selectYesOrNo(String column, String columnValue, Map<String, Object> busInfo, Map<String, Object> returnMap);
}
