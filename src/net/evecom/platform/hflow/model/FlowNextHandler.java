/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.model;

import java.io.Serializable;

/**
 * 描述 流程下一环节审核人信息
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月17日 下午3:41:46
 */
public class FlowNextHandler implements Serializable {
    /**
     * 下一环节审核人编码
     */
    private String nextStepAssignerCode;
    /**
     * 下一环节审核人名称
     */
    private String nextStepAssignerName;
    /**
     * 下一环节审核人手机号
     */
    private String nextStepAssignerMobile;
    /**
     * 下一环节审核人类型
     */
    private String nextStepAssignerType;
    /**
     * @author Flex Hu
     * @created 2015年11月25日 下午3:25:54
     * @return type
     */
    public String getNextStepAssignerType() {
        return nextStepAssignerType;
    }
    /**
     * @author Flex Hu
     * @created 2015年11月25日 下午3:25:54
     * @param nextStepAssignerType
     */
    public void setNextStepAssignerType(String nextStepAssignerType) {
        this.nextStepAssignerType = nextStepAssignerType;
    }
    /**
     * 串审排序
     */
    private int taskOrder;
    
    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午12:18:39
     * @return type
     */
    public String getNextStepAssignerCode() {
        return nextStepAssignerCode;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午12:18:39
     * @param nextStepAssignerCode
     */
    public void setNextStepAssignerCode(String nextStepAssignerCode) {
        this.nextStepAssignerCode = nextStepAssignerCode;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午12:18:39
     * @return type
     */
    public String getNextStepAssignerName() {
        return nextStepAssignerName;
    }
    /**
     * @author Flex Hu
     * @created 2015年8月19日 下午12:18:39
     * @param nextStepAssignerName
     */
    public void setNextStepAssignerName(String nextStepAssignerName) {
        this.nextStepAssignerName = nextStepAssignerName;
    }
    
    /**
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 下午6:00:06
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nextStepAssignerCode == null) ? 0 : nextStepAssignerCode.hashCode());
        result = prime * result + ((nextStepAssignerName == null) ? 0 : nextStepAssignerName.hashCode());
        return result;
    }
    /**
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 下午6:00:06
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FlowNextHandler other = (FlowNextHandler) obj;
        if (nextStepAssignerCode == null) {
            if (other.nextStepAssignerCode != null)
                return false;
        } else if (!nextStepAssignerCode.equals(other.nextStepAssignerCode))
            return false;
        if (nextStepAssignerName == null) {
            if (other.nextStepAssignerName != null)
                return false;
        } else if (!nextStepAssignerName.equals(other.nextStepAssignerName))
            return false;
        return true;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年9月4日 上午11:12:23
     * @return type
     */
    public int getTaskOrder() {
        return taskOrder;
    }
    /**
     * @author Flex Hu
     * @created 2015年9月4日 上午11:12:23
     * @param taskOrder
     */
    public void setTaskOrder(int taskOrder) {
        this.taskOrder = taskOrder;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年10月30日 上午9:26:55
     * @return type
     */
    /*public String getNextStepAssignerMobile() {
        return nextStepAssignerMobile;
    }*/
    /**
     * @author Flex Hu
     * @created 2015年10月30日 上午9:26:55
     * @param nextStepAssignerMobile
     */
    /*public void setNextStepAssignerMobile(String nextStepAssignerMobile) {
        this.nextStepAssignerMobile = nextStepAssignerMobile;
    }*/
    
}
