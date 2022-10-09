/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Flex Hu 
 * 数据库表信息
 * @version 1.1
 */
public class TableInfo implements Serializable {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "fj";
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表注释
     */
    private String tableComments;
    /**
     * 主键集合
     */
    private List<String> primaryKeys = new ArrayList<String>();
    /**
     * 数据库列集合
     */
    private List<TableColumn> columns = new ArrayList<TableColumn>();
    
    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }
    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    /**
     * @return the tableComments
     */
    public String getTableComments() {
        return tableComments;
    }
    /**
     * @param tableComments the tableComments to set
     */
    public void setTableComments(String tableComments) {
        this.tableComments = tableComments;
    }
    /**
     * @return the columns
     */
    public List<TableColumn> getColumns() {
        return columns;
    }
    /**
     * @param columns the columns to set
     */
    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }
    /**
     * @author Flex Hu
     * @created 2014年10月13日 下午4:50:11
     * @return type
     */
    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }
    /**
     * @author Flex Hu
     * @created 2014年10月13日 下午4:50:11
     * @param primaryKeys
     */
    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
}
