/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.util.StringUtils;

/**
 * 
 * 描述：DateConvertEditor
 * @author Water Guo
 * @created 2017-6-12 下午5:36:06
 */
public class DateConvertEditor extends PropertyEditorSupport {
    /**
     * 方法SimpleDateFormat
     * 
     * @param HH
     *            :mm:ss" 传入参数
     * @return 返回值new
     */
    private SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    /**
     * 方法SimpleDateFormat
     * 
     * @param "yyyy-MM-dd" 传入参数
     * @return 返回值new
     */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 方法setAsText
     * 
     * @param text
     *            传入参数
     */
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(this.dateFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(this.datetimeFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 21) {
                    text = text.replace(".0", "");
                    setValue(this.datetimeFormat.parse(text));
                } else {
                    throw new IllegalArgumentException(
                            "Could not parse date, date format is error ");
                }
            } catch (ParseException ex) {
                IllegalArgumentException iae = new IllegalArgumentException(
                        "Could not parse date: " + ex.getMessage());
                iae.initCause(ex);
                throw iae;
            }
        } else {
            setValue(null);
        }
    }
}
