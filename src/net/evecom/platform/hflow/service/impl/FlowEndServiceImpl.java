/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;


import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.ExeMsgService;
import net.evecom.platform.hflow.service.FlowEndService;
import net.evecom.platform.zzhy.model.flow.FlowEndFactory;
import net.evecom.platform.zzhy.model.flow.FlowEndProcess;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 流程实例数据操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowEndService")
public class FlowEndServiceImpl extends BaseServiceImpl implements FlowEndService {
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 所引入的dao
     */
    @Resource
    private ExeMsgService exeMsgService;
    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 通过接口办件执行成功
     * @param flowInfo
     */
    @Override
    public void exeSuccessByInter(Map<String,Object> flowInfo) {
        String exeId = StringUtil.getString(flowInfo.get("EXE_ID"));
        //创建办结流程任务
        FlowEndProcess flowEndProcess = FlowEndFactory.createFlowEndProcess(exeId);
        if(Objects.nonNull(flowEndProcess)){
            flowEndProcess.flowEnd(flowInfo);
        }
        //发送办结短信
        exeMsgService.sendExeSuccessMsg(exeId);
    }
}