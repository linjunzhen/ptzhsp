/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;


import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.ExeMsgService;
import net.evecom.platform.zzhy.model.exesuccess.ExeContent;
import net.evecom.platform.zzhy.model.exesuccess.ExeMsgFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 描述 流程实例数据操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("exeMsgService")
public class ExeMsgServiceImpl extends BaseServiceImpl implements ExeMsgService {
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;

    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 办件办结发送短信
     * @param exeId
     */
    @Override
    public void sendExeSuccessMsg(String exeId) {
        ExeContent exeContent= ExeMsgFactory.createExeMsg(exeId);
        if(Objects.nonNull(exeContent)){
            exeContent.sendSuccessMsg(exeId);
        }
    }
}