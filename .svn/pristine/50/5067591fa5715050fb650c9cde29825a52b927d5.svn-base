/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.wsbs.dao.UserTodoItemDao;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.wsbs.service.UserTodoItemService;

import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月2日 下午4:58:40
 */
@Service("userTodoItemService")
public class UserTodoItemServiceImpl extends BaseServiceImpl implements UserTodoItemService {
    /**
     * 所引入的dao
     */
    @Resource
    private UserTodoItemDao dao;
    /**
     * ServiceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 保存待发起流程任务的待办事项
     * @author Flex Hu
     * @created 2015年12月2日 下午5:03:31
     * @param flowVars
     * @return
     */
    public Map<String,Object> saveToStartTask(Map<String,Object> flowVars){
        //获取投资项目类型
        String TZXMLX = (String) flowVars.get("TZXMLX");
        //获取投资项目编号
        String TZXMBH = (String) flowVars.get("TZXMBH");
        //获取当前投资项目编号
        //获取下一个项目信息
        Map<String,Object> nextItem = serviceItemService.getNextTzItemCode(TZXMBH, TZXMLX);
        if(nextItem!=null){
            Map<String,Object> userTodoItem = new HashMap<String,Object>();
            userTodoItem.put("SXMC",nextItem.get("ITEM_NAME"));
            userTodoItem.put("DBLX","3");
        }
        return flowVars;
    }
}
