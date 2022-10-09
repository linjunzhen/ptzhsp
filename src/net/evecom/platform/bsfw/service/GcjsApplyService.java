/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 工程建设项业务相关方法
 * 
 * @author Scolder Lin
 * @created 2021年9月7日 上午10:45:59
 */
public interface GcjsApplyService extends BaseService {
    
    /**
     * 商品房预售许可证前置事件
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveBeforeBusDataSpf(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 城镇污水排入排水管网许可证前置事件
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveBeforeBusDataCzws(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 林木采伐许可证前置事件
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveBeforeBusDataLmcf(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 易地修建防空地下室审批前置事件(新：城市新建民用建筑易地修建防空地下室审批)
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年11月11日 下午15:28:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveBeforeBusDataYdxj(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 结合民用建筑修建防空地下室设计审核前置事件(新：城市新建民用建筑修建防空地下室审批)
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年11月11日 下午15:28:35
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveBeforeBusDataJhmy(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 防空地下室竣工验收备案前置事件(新：人防工程竣工验收备案（就地）)
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年11月11日 下午15:28:55
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveBeforeBusDataFkdxs(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 
     * 描述  根据电子证照数据标识获取电子证照文件
     * 
     * @author Roy Li
     * @created 2021年9月10日 上午9:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public void getCertificateFileContentByIdentifier(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 
     * 描述  根据附件URL获取附件base64编码
     * 
     * @author Roy Li
     * @created 2021年11月17日 上午16:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public String getBase64CodeByFileUrl(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 
     * 描述  根据事项编码查询材料编码
     * 
     * @author Jason Lin
     * @created 2021年11月17日 上午16:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    public String queryMaterId(Map<String, Object> flowDatas) throws Exception;
    
}
