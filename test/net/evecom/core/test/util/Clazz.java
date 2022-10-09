/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.io.Serializable;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月7日 上午8:50:04
 */
public class Clazz implements Serializable {
    /**
     * 班级名称
     */
    private String className;
    /**
     * 班级编号
     */
    private String classNum;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月7日 上午8:51:44
     */
    public Clazz() {
        
    }
    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月7日 上午8:51:00
     * @param className
     * @param classNum
     */
    public Clazz(String className, String classNum) {
        super();
        this.className = className;
        this.classNum = classNum;
    }
    
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:50:46
     * @return type
     */
    public String getClassName() {
        return className;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:50:46
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:50:46
     * @return type
     */
    public String getClassNum() {
        return classNum;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:50:46
     * @param classNum
     */
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
}
