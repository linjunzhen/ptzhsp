/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 流程提交对象信息
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月17日 下午3:35:36
 */
@SuppressWarnings("unused")
public class FlowSubmitObj implements Serializable {
    /**
     * 描述
     */
    private static final long serialVersionUID = 1L;
    /**
     * 流程定义ID
     */
    private String EFLOW_DEFID;
    /**
     * 流程定义KEY
     */
    private String EFLOW_DEFKEY;
    /**
     * 流程的版本号
     */
    private String EFLOW_DEFVERSION;
    /**
     * 是否是暂存操作1:是 -1:否
     */
    private String EFLOW_ISTEMPSAVE;
    /**
     * 业务表名称
     */
    private String EFLOW_BUSTABLENAME;
    /**
     * 业务表记录ID
     */
    private String EFLOW_BUSRECORDID;
    /**
     * 流程发起人ID
     */
    private String EFLOW_CREATORID;
    /**
     * 流程发起人帐号
     */
    private String EFLOW_CREATORACCOUNT;
    /**
     * 流程发起人名称
     */
    private String EFLOW_CREATORNAME;
    /**
     * 流程发起人联系电话
     */
    private String EFLOW_CREATORPHONE;
    /**
     * 流程外部指定的发起时间
     */
    private String EFLOW_CREATETIME;
    /**
     * 流程实例申报号
     */
    private String EFLOW_EXEID;
    /**
     * 外部分配的流程实例申报号
     */
    private String EFLOW_ASSIGNED_EXEID;
    /**
     * 当前实例正在运行的任务名称(用逗号分割)
     */
    private String EFLOW_CUREXERUNNINGNODENAMES;
    
    /**
     * 当前审核人操作节点名称
     */
    private String EFLOW_CURUSEROPERNODENAME;
    /**
     * 是否调用存储数据方法接口,当值等于-1的时候不进行调用,其他情况都调用
     * 
     */
    private String EFLOW_INVOKEBUSSAVE;
    /**
     * 是否调用前置事件接口,当值等于-1的时候不进行调用,其他情况都调用
     */
    private String EFLOW_INVOKEFRONTCODE;
    /**
     * 是否调用后置事件接口,当值等于-1的时候不进行调用,其他情况都调用
     */
    private String EFLOW_INVOKEBACKCODE;
    /**
     * 上传的附件IDS,用逗号拼接
     */
    private String EFLOW_FILEATTACHIDS;
    /**
     * 申报提交的材料JSON,有以下字段构造的
     * ATTACH_KEY,
     * SQFS,
     * SFSQ,
     * FILE_ID
     */
    private String EFLOW_SUBMITMATERFILEJSON;
    /**
     * 流程的标题
     */
    private String EFLOW_SUBJECT;
    /**
     * 流程的办理截止事件
     */
    private String EFLOW_HANDLE_OVERTIME;
    /**
     * 下一环节相关信息,是由FlowNextStep的JSON构成
     */
    private String EFLOW_NEXTSTEPSJSON;
    /**
     * 选择器的变量值
     */
    private String EFLOW_SELECTORVARS;
    /**
     * 选择器的规则
     */
    private String EFLOW_SELECTORRULE;
    /**
     * 是否同意的值
     */
    private String EFLOW_ISAGREE;
    /**
     * 是否通过的值(1:通过 -1:不通过)
     */
    private String EFLOW_ISPASS;
    /**
     * 审核意见内容值
     */
    private String EFLOW_HANDLE_OPINION;
    /**
     * 当前审核人所操作的流程任务ID
     */
    private String EFLOW_CURRENTTASK_ID;
    /**
     * 当前审核人的编码
     */
    private String EFLOW_CURRENTTASK_HANDLERCODE;
    /**
     * 当前审核人的名称
     */
    private String EFLOW_CURRENTTASK_HANDLERNAME;
    /**
     * 产生的新流程任务IDS
     */
    private String EFLOW_NEWTASK_PARENTIDS;
    /**
     * 产生的新流程任务环节名称
     */
    private String EFLOW_NEWTASK_NODENAMES;
    /**
     * 产生的新流程任务审核人编码
     */
    private String EFLOW_NEWTASK_HANDLERCODES;
    /**
     * 产生的新流程任务审核人名称
     */
    private String EFLOW_NEWTASK_HANDLERNAMES;
    /**
     * 流程的状态
     */
    private int EFLOW_EXERUNSTATUS;
    /**
     * 
     * 是否是查看流程表单信息明细
     */
    private String EFLOW_ISQUERYDETAIL;
    /**
     * 下一环节是否是结束流程 true false
     */
    private String EFLOW_DESTTOEND;
    /**
     * 当前审核审核人操作任务的状态
     */
    private String EFLOW_CURHANDLER_TASKSTAUS;
    /**
     * 下一环节是否经过同步节点(true false)
     */
    private String EFLOW_NEXTISJOIN;
    /**
     * 下一环节如果是同步节点,同步节点的名称
     */
    private String EFLOW_NEXTJOINNODENAME;
    /**
     * 需要被合并的同步任务节点名称,用逗号拼接
     */
    private String EFLOW_JOINPRENODENAMES;
    /**
     * 下一部是否是退回操作:true false
     */
    private String EFLOW_ISBACK;
    /**
     * 是否发送待办提醒短信:true false
     */
    private String EFLOW_IS_SENDMSG;
    /**
     * 指定期限方式(1:工作日方式 2:具体截止时间)
     */
    private String EFLOW_QXZDFS;
    /**
     * 指定限制办理工作日数量
     */
    private String EFLOW_WORKDAYLIMIT;
    /**
     * 指定具体截止时间
     */
    private String EFLOW_DEADLINETIME;
    /**
     * 最终任务指定截止时间
     */
    private String EFLOW_TASKDEADLINETIME;
    /**
     * 下一步经办人类型:(1:后台用户 2:公众用户)
     */
    private String EFLOW_ASSIGNER_TYPE;
    /**
     * 审批材料IDS
     */
    private String EFLOW_APPMATERFILEIDS;
    /**
     * 审批材料JSON
     */
    private String EFLOW_APPMATERFILEJSON;
    /**
     * 是否是流程开始节点:true false
     */
    private String EFLOW_IS_START_NODE;
    /**
     * 是否是退回补件:true false
     */
    private String EFLOW_ISBACK_PATCH;
    /**
     * 传阅人员IDS
     */
    private String EFLOW_PERULA_IDS;
    /**
     * 流程的申报状态值
     */
    private String EFLOW_APPLY_STATUS;
    /**
     * 业务数据
     */
    private Map<String,Object> busRecord = new HashMap<String,Object>();
    /**
     * 当前环节的表单字段权限控制
     */
    private List<Map<String,Object>> currentNodeFieldRights = new ArrayList<Map<String,Object>>();
    
}
