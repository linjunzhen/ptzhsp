/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.poi;

/**
 * 
 * 描述 Excel替换内容存储对象
 * @author Flex Hu
 * @created 2017-5-25 上午10:35:18
 */
public class ExcelReplaceDataVO {
    /**
     * Excel单元格行
     */
    private int row;
    /**
     * Excel单元格列
     */
    private int column;
    /**
     * 替换的关键字
     */
    private String key;
    /**
     * 替换的文本
     */
    private String value;
    
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @return type
     */
    public int getRow() {
        return row;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @return type
     */
    public int getColumn() {
        return column;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @return type
     */
    public String getKey() {
        return key;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @return type
     */
    public String getValue() {
        return value;
    }
    /**
     * @author Rider Chen
     * @created 2016-3-8 上午10:56:46
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

   

}
