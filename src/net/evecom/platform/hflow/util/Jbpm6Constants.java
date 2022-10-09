/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.util;

/**
 * 描述 JBPM6的常量
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月6日 上午9:32:49
 */
public class Jbpm6Constants {
    /**
     * 开始节点
     */
    public static final String START_NODE = "start";
    /**
     * 任务节点
     */
    public static final String TASK_NODE = "task";
    /**
     * 决策节点
     */
    public static final String DECISION_NODE = "decision";
    /**
     * 并行节点
     */
    public static final String PARALLEL_NODE = "parallel";
    /**
     * 合并节点
     */
    public static final String JOIN_NODE = "join";
    /**
     * 结束节点
     */
    public static final String END_NODE = "end";
    /**
     * 子流程节点
     */
    public static final String SUBPROCESS_NODE = "subProcess";
    /**
     * 缺省存储业务数据的事件代码
     */
    public static final String DEFAULT_BUS_EVENT = "flowEventService.saveBusData";
    /**
     * 流程提交按钮的KEY
     */
    public static final String FLOW_BTN_SUBMIT_KEY = "SubmitFlow";
    /**
     * 流程暂存按钮的KEY
     */
    public static final String FLOW_BTN_TEMPSAVE_KEY = "TempSaveFlow";
    /**
     * 流程回退按钮的KEY
     */
    public static final String FLOW_BTN_BACK_KEY = "BackFlow";
    /**
     * 按钮默认授权:允许
     */
    public static final int FLOW_BTN_AUTH_YES = 1;
    /**
     * 按钮默认授权:禁止
     */
    public static final int FLOW_BTN_AUTH_NO = -1;
    /**
     * 审批控件:接口类型
     */
    public static final String AUDITTYPE_INTERFACE = "1";
    /**
     * 审批控件:选择器
     */
    public static final String AUDITTYPE_SELECTOR = "2";
    /**
     * 审批控件:任务决策
     */
    public static final String AUDITTYPE_TASKDECIDE = "3";
    /**
     * 事件类型:前置
     */
    public static final String EVENTTYPE_FRONT = "1";
    /**
     * 事件类型:存储业务数据
     */
    public static final String EVENTTYPE_BUS = "2";
    /**
     * 事件类型:后置
     */
    public static final String EVENTTYPE_BACK = "3";
    /**
     * 事件类型:决策
     */
    public static final String EVENTTYPE_DECIDE = "4";
    /**
     * 流程状态:草稿
     */
    public static final int RUNSTATUS_DRAFT = 0; 
    /**
     * 流程状态:运行中
     */
    public static final int RUNSTATUS_RUNING = 1;
    /**
     * 流程状态:已办结(正常结束)
     */
    public static final int RUNSTATUS_OVERZCJS = 2;
    /**
     * 流程状态:已办结(审核通过)
     */
    public static final int RUNSTATUS_OVERSHTG = 3;
    /**
     * 流程状态:已办结(审核不通过)
     */
    public static final int RUNSTATUS_OVERSHBTG = 4;
    /**
     * 流程状态:已办结(退回)
     */
    public static final int RUNSTATUS_OVERTH = 5;
    /**
     * 流程状态:已办结(强制结束)
     */
    public static final int RUNSTATUS_OVERQZJS = 6;
    /**
     * 任务状态:挂起
     */
    public static final String TASKSTATUS_GQ = "-1";
    /**
     * 任务状态:正在审核
     */
    public static final String TASKSTATUS_ZZSH = "1";
    /**
     * 任务状态:已审核
     */
    public static final String TASKSTATUS_YSH = "2";
    /**
     * 任务状态:退回
     */
    public static final String TASKSTATUS_TH = "3";
    /**
     * 任务状态:转发
     */
    public static final String TASKSTATUS_ZF = "4";
    /**
     * 任务状态:委托
     */
    public static final String TASKSTATUS_WT = "5";
    /**
     * 任务状态:结束流程
     */
    public static final String TASKSTATUS_JSLC = "6";
    /**
     * 任务性质:单人任务
     */
    public static final int TASKNATURE_SIGNLER = 1;
    /**
     * 任务性质:团队任务
     */
    public static final int TASKNATURE_TEAM = 2;
}
