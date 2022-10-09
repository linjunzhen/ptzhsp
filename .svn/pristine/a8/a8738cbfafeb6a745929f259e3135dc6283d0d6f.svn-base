/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 下一环节信息
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月19日 上午10:47:27
 */
public class FlowNextStep implements Serializable {

    /**
     * 环节名称
     */
    private String flowNodeName;
    /***
     * 环节审核人名称
     */
    private String flowHandlerNames;
    /**
     * 选择器URL
     */
    private String selectorUrl;
    /**
     * 选择器名称
     */
    private String selectorName;
    /**
     * 选择器高度
     */
    private int selectorHeight;
    /**
     * 选择器宽度
     */
    private int selectorWeight;
    /**
     * 选择器接收变量值
     */
    private String selectorVars;
    /**
     * 选择器规则值
     */
    private String selectorRule;
    /**
     * 流程任务的性质(1:单人任务 2:团队任务)
     */
    private int taskNature;
    /**
     * 允许流程任务自由流转(1:允许 -1:不允许)
     */
    private int isFree;
    /**
     * 是否是结束节点(true false)
     */
    private String isEndNode;
    /**
     * 环节审核人列表
     */
    private List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午10:49:35
     * @return type
     */
    public String getFlowNodeName() {
        return flowNodeName;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午10:49:35
     * @param flowNodeName
     */
    public void setFlowNodeName(String flowNodeName) {
        this.flowNodeName = flowNodeName;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午10:49:35
     * @return type
     */
    public String getFlowHandlerNames() {
        return flowHandlerNames;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午10:49:35
     * @param flowHandlerNames
     */
    public void setFlowHandlerNames(String flowHandlerNames) {
        this.flowHandlerNames = flowHandlerNames;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午10:50:20
     * @return type
     */
    public List<FlowNextHandler> getHandlers() {
        return handlers;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午10:50:20
     * @param handlers
     */
    public void setHandlers(List<FlowNextHandler> handlers) {
        this.handlers = handlers;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午11:32:47
     * @return type
     */
    public String getSelectorUrl() {
        return selectorUrl;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午11:32:47
     * @param selectorUrl
     */
    public void setSelectorUrl(String selectorUrl) {
        this.selectorUrl = selectorUrl;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午11:32:47
     * @return type
     */
    public int getSelectorHeight() {
        return selectorHeight;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午11:32:47
     * @param selectorHeight
     */
    public void setSelectorHeight(int selectorHeight) {
        this.selectorHeight = selectorHeight;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午11:32:47
     * @return type
     */
    public int getSelectorWeight() {
        return selectorWeight;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 上午11:32:47
     * @param selectorWeight
     */
    public void setSelectorWeight(int selectorWeight) {
        this.selectorWeight = selectorWeight;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午1:11:18
     * @return type
     */
    public String getSelectorVars() {
        return selectorVars;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午1:11:18
     * @param selectorVars
     */
    public void setSelectorVars(String selectorVars) {
        this.selectorVars = selectorVars;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午1:11:18
     * @return type
     */
    public String getSelectorRule() {
        return selectorRule;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午1:11:18
     * @param selectorRule
     */
    public void setSelectorRule(String selectorRule) {
        this.selectorRule = selectorRule;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午1:22:30
     * @return type
     */
    public String getSelectorName() {
        return selectorName;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午1:22:31
     * @param selectorName
     */
    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月20日 下午4:23:13
     * @return type
     */
    public int getTaskNature() {
        return taskNature;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月20日 下午4:23:13
     * @param taskNature
     */
    public void setTaskNature(int taskNature) {
        this.taskNature = taskNature;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月20日 下午4:51:48
     * @return type
     */
    public int getIsFree() {
        return isFree;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月20日 下午4:51:48
     * @param isFree
     */
    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年8月22日 上午10:56:02
     * @return type
     */
    public String getIsEndNode() {
        return isEndNode;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月22日 上午10:56:02
     * @param isEndNode
     */
    public void setIsEndNode(String isEndNode) {
        this.isEndNode = isEndNode;
    }


}
