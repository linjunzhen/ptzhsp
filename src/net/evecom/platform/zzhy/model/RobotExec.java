/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import com.google.common.collect.Lists;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 描述   工商数据接口提交数据机器人(桥接模式）
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class RobotExec  extends  RobotNum implements  Robot{
    /**
     *
     */
    public RobotExec(RobotNum robot){
        rootName= robot.rootName;
        jobName= robot.jobName;
        jobUid=robot.jobUid;
        queryStatusUrl= robot.queryStatusUrl;
        updateUrl=robot.updateUrl;
        triggerUrl= robot.triggerUrl;
        paramType=robot.paramType;
    }
    /**
     * 查询执行机状态
     *  推送提交数据--步骤一
     */
    @Override
    public boolean queryRobotStatus(){
        TaskData<Map> queryData=new TaskData();
        Channel channel=new Channel();
        channel.setName(RobotUtils.QUERY_CHANNEL_NAME);
        //params对象
        Map<String,Object> params=new HashMap<>();
        params.put("pageSize",10);
        params.put("pageNo",1);
        params.put("orderBy","createTime");
        params.put("order","desc");
        Map<String,Object> conditions=new HashMap<>();
        conditions.put("name",rootName);
        conditions.put("creator",RobotUtils.QUERY_CREATOR);
        conditions.put("available",true);
        params.put("conditions",conditions);
        //queryData对象
        queryData.setVersion(RobotUtils.QUERY_VERSION);
        queryData.setChannel(channel);
        queryData.setParams(params);
        //推送接口
        String responseContent=RobotUtils.pushTaskData(queryData,queryStatusUrl);
        return RobotUtils.queryRobotStatusByResult(responseContent);
    }
    /**
     * 触发机器执行任务
     *  推送提交数据--步骤三
     */
    @Override
    public boolean execTask(){
        //params对象
        Map<String,Object> params=new HashMap<>();
        params.put("jobName",jobName);
        params.put("uid",jobUid);
        TaskData taskData=RobotUtils.getTaskData(params);
        String result=RobotUtils.pushTaskData(taskData,triggerUrl);
        return RobotUtils.getSubmitDataByResult(result);
    }
    /**
     * 执行更新数据
     *  推送提交数据--步骤二
     */
    @Override
    public boolean submitData(Map<String,Object> param){
        List<Params2> jobParams= Lists.newArrayList();
        for(Map.Entry<String,Object> entry:param.entrySet()){
            String key=StringUtil.getString(entry.getKey());
            Params2 param2=new Params2();
            param2.setName(key);
            param2.setValue(entry.getValue());
            if(Objects.equals("data",key)){
                param2.setType(RobotUtils.paramTypeObject);
            }else{
                param2.setType(RobotUtils.paramTypeString);
            }
            jobParams.add(param2);
        }
        //params对象
        Map<String,Object> params=new HashMap<>();
        params.put("jobName",jobName);
        params.put("jobParams",jobParams);
        TaskData taskData=RobotUtils.getTaskData(params);
        String result=RobotUtils.pushTaskData(taskData,updateUrl);
        return RobotUtils.getSubmitDataByResult(result);
    }
}
