/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.hibernate;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:29:30
 */
public class HibernateModel {
    /**
     * 实体类名称
     */
    private String entityName;
    /**
     * 私有主键值
     */
    private String primaryKeyValue;
    /**
     * 私有主键名称
     */
    private String primaryKeyName;
    /**
     * 属性的值
     */
    private Map<String, Object> properties = new HashMap<String, Object>();

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPrimaryKeyValue() {
        return primaryKeyValue;
    }

    public void setPrimaryKeyValue(String primaryKeyValue) {
        this.primaryKeyValue = primaryKeyValue;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "HibernateModel [entityName=" + entityName
                + ", primaryKeyValue=" + primaryKeyValue + ", primaryKeyName="
                + primaryKeyName + "]";
    }
}
