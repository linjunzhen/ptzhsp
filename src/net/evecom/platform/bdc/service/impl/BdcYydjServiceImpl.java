/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcYydjDao;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcYydjService;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述  异议登记操作Service
 * @author Allin Lin
 * @created 2020年4月29日 下午2:15:34
 */
@Service("bdcYydjService")
public class BdcYydjServiceImpl extends BaseServiceImpl implements BdcYydjService{
    /**
     * log
     */
    private static Log log=LogFactory.getLog(BdcYydjServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private BdcYydjDao dao;
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入service
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;
    
    /**
     * 描述:审批表打印配置类
     *
     * @author Allin Lin
     * @created 2020/4/27 11:29:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSqbConfig;
    
    /**
     * 描述
     * @author Allin Lin
     * @created 2019年3月13日 上午10:24:38
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {      
        return dao;
    }
    
    /**
     * 描述     异议登记数据回填
     * @author Allin Lin
     * @created 2020年4月30日 下午4:51:22
     * @param args
     */
    @Override
    public void setYydjData(Map<String, Map<String, Object>> args){
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置异议登记业务数据*/
        this.setYydjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }
    
    
    /**
     * 描述:设置异议登记业务数据
     *
     * @author Madison You
     * @created 2020/4/27 14:29:00
     * @param
     * @return
     */
    private void setYydjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("qllx", "异议登记");//所属目录
        returnMap.put("djlx", "异议登记");//事项名称
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));//收件人
        
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "YYDXBDC_QLR"));//权利人名称              
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "YYDXBDC_QLRZJH"));//证件号码
        /*证件类型代码转换*/
        StringBuffer sfzjzl = new StringBuffer();
        String documentType = dictionaryService.getDicNames("DocumentType", 
                StringUtil.getValue(busInfo, "YYDXBDC_QLRZJZL"));
        sfzjzl.append(documentType);
        returnMap.put("sfzjzl", sfzjzl);//身份证件种类
        
        // 不动产情况
        returnMap.put("zl", StringUtil.getValue(busInfo, "YYDXBDC_ZL"));//坐落
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "YYDXBDC_BDCDYH"));//不动产单元号       
        
        returnMap.put("qlxz", StringUtil.getValue(busInfo, "YYDXBDC_QLXZ"));//权利性质
        returnMap.put("bdclx", StringUtil.getValue(busInfo, "YYDXBDC_BDCLX"));//不动产类型       
        returnMap.put("mj", StringUtil.getValue(busInfo, "YYDXBDC_MJ"));//面积
        returnMap.put("ybdcqzsh", StringUtil.getValue(busInfo, "YYDXBDC_BDCQZH"));//原不动产权证号
        returnMap.put("djyy", StringUtil.getValue(busInfo, "JBXX_DJYY"));//登记原因
        /*登记原因证明文件*/
        bdcSpbConfig.getMaterNameList(returnMap);
        /*填写初审核定意见'*/
        bdcSpbConfig.getbdcDjshOpinion(busInfo,returnMap);
        //bdcSqbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));//备注           
    }

}
