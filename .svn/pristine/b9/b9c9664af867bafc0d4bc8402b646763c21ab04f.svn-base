/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.sync.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-8-25 上午9:55:03
 */
public interface SwbDataDao extends BaseDao {

    /**
     * 
     * 描述    是否保存过办件信息类型的指令数据
     * @author Danto Huang
     * @created 2016-8-25 下午2:31:01
     * @param exeId
     * @return
     */
    public boolean isHaveSaveBjxxInfo(String exeId);
    /**
     * 
     * 描述    是否保存过过程信息类型的指令数据
     * @author Danto Huang
     * @created 2016-8-25 下午2:31:01
     * @param taskId
     * @return
     */
    public boolean isHaveSaveGcxxInfo(String taskId);
    /**
     * 
     * 描述    是否保存过结果信息类型的指令数据
     * @author Danto Huang
     * @created 2016-8-25 下午2:31:01
     * @param exeId
     * @return
     */
    public boolean isHaveSaveJgxxInfo(String exeId);
    /**
     * 
     * 描述   获取所有已办流程任务
     * @author Danto Huang
     * @created 2016-8-25 下午4:22:16
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> getDoneTaskByExeId(String exeId);
    /**
     * 
     * 描述   获取办结节点流程任务
     * @author Danto Huang
     * @created 2016-8-25 下午4:22:16
     * @param exeId
     * @return
     */
    public Map<String,Object> getEndTaskByExeId(String exeId);
}
