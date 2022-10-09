/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import net.evecom.platform.base.model.BaseModel;

/**
 * 描述 流程图节点信息
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
public class FlowNodeBean extends BaseModel {
    /** 目录 **/
    private String category;
    /** 文字 **/
    private String text;
    /** 键值 **/
    private String key;
    /** 位置 **/
    private String loc;
    /** 图标 **/
    private String figure;
    /** 宽度 **/
    private String width;
    /** 高度 **/
    private String height;
    /** 颜色 **/
    private String color;
    /** 标志 **/
    private String isdeal;
    /** 编号 **/
    private String id;
    /**是否可编辑**/
    private boolean editable;
    /**是否可删除**/
    private boolean deletable;
    
    /**
     * @return the editable
     */
    public boolean getEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the loc
     */
    public String getLoc() {
        return loc;
    }

    /**
     * @param loc
     *            the loc to set
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * @return the figure
     */
    public String getFigure() {
        return figure;
    }

    /**
     * @param figure
     *            the figure to set
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the isdeal
     */
    public String getIsdeal() {
        return isdeal;
    }

    /**
     * @param isdeal
     *            the isdeal to set
     */
    public void setIsdeal(String isdeal) {
        this.isdeal = isdeal;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the deletable
     */
    public boolean isDeletable() {
        return deletable;
    }

    /**
     * @param deletable the deletable to set
     */
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
    

}
