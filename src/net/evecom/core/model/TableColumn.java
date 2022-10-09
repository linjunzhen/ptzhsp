/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.model;

import java.io.Serializable;

/**
 * 
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月9日 上午9:39:56
 */
public class TableColumn implements Serializable {
    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 字段别名
     */
    private String aliasName;
    /**
     * 字段注释
     */
    private String comments;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 字段长度
     */
    private int dataLength;
    /**
     * 精度值
     */
    private int dataScale;
    /**
     * 是否允许为空
     */
    private boolean nullAble;

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName
     *            the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     *            the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the dataLength
     */
    public int getDataLength() {
        return dataLength;
    }

    /**
     * @param dataLength
     *            the dataLength to set
     */
    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    /**
     * @return the dataScale
     */
    public int getDataScale() {
        return dataScale;
    }

    /**
     * @param dataScale
     *            the dataScale to set
     */
    public void setDataScale(int dataScale) {
        this.dataScale = dataScale;
    }
    
    /**
     * @author Flex Hu
     * @created 2014年9月21日 下午1:40:53
     * @return type
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月21日 下午1:40:53
     * @param aliasName
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * @return the nullAble
     */
    public boolean isNullAble() {
        return nullAble;
    }

    /**
     * @param nullAble
     *            the nullAble to set
     */
    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }
    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月21日 下午1:48:58
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TableColumn [columnName=" + columnName + ", aliasName=" + aliasName + ", comments=" + comments
                + ", dataType=" + dataType + ", dataLength=" + dataLength + ", dataScale=" + dataScale + ", nullAble="
                + nullAble + "]";
    }

}
