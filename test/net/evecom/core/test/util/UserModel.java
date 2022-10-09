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
 * @created 2014年9月7日 上午8:20:36
 */
public class UserModel implements Serializable {
    /**
     * 姓名
     */
    private String username;
    /**
     * 性别
     */
    private int sex;
    /**
     *重量
     */
    private double weight;
    /**
     * 是否已婚
     */
    private boolean isMarray;
    /**
     * 班级
     */
    private Clazz clazz = new Clazz();
    
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:52:30
     * @return type
     */
    public Clazz getClazz() {
        return clazz;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:52:30
     * @param clazz
     */
    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @return type
     */
    public String getUsername() {
        return username;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @return type
     */
    public int getSex() {
        return sex;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @param sex
     */
    public void setSex(int sex) {
        this.sex = sex;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @return type
     */
    public double getWeight() {
        return weight;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @return type
     */
    public boolean isMarray() {
        return isMarray;
    }
    /**
     * @author Flex Hu
     * @created 2014年9月7日 上午8:21:52
     * @param isMarray
     */
    public void setMarray(boolean isMarray) {
        this.isMarray = isMarray;
    }
    
    public UserModel(){
        
    }
    
    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月7日 上午8:35:04
     * @param username
     * @param sex
     * @param weight
     * @param isMarray
     */
    public UserModel(String username, int sex, double weight, boolean isMarray) {
        this.username = username;
        this.sex = sex;
        this.weight = weight;
        this.isMarray = isMarray;
    }
}
