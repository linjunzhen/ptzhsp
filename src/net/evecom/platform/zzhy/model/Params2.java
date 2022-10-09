/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

/**
 * 描述   工商数据接口创建任务实体类
 * @author Flex Hu
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class Params2<T> {
    /**
     *
     */
    private String name;
    /**
     *number，枚举值：0，含义：数字类型。
     *string，枚举值：1，含义：字符串。
     *sensitive，枚举值：2，含义：敏感信息。
     *object，枚举值：3，含义：json字符串。
     */
    private String type="string";
    /**
     *
     */
    private T value;
    /**
     *
     */
    public String getName() {
        return name;
    }
    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *
     */
    public String getType() {
        return type;
    }
    /**
     *
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     *
     */
    public T getValue() {
        return value;
    }
    /**
     *
     */
    public void setValue(T value) {
        this.value = value;
    }
}
