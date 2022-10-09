/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.model;

import net.evecom.core.util.DateTimeUtil;

import java.util.Date;

/**
 * 
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年1月31日 下午8:11:39
 */
public class FileInfoMsg{
    /**
     * uuid
     */
    private String fid;
    /**
     * 文件名
     */
    private String fName;
    /**
     * 绝对路径
     */
    private String absoultFilePath;
    /**
     *相对路径
     */
    private String relFilePath;
    /**
     * createDate
     */
    private String createDate;
    /**
     * 后缀名
     */
    private String suffix;
    /**
     *
     * @return
     */
    public String getSuffix() {
        return suffix;
    }
    /**
     *
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     *
     * @return
     */
    public String getCreateDate() {
        if("".equals(createDate)){
            return DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public String getFid() {
        return fid;
    }

    /**
     *
     * @param fid
     */
    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     *
     * @return
     */
    public String getfName() {
        return fName;
    }

    /**
     *
     * @param fName
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     *
     * @return
     */
    public String getAbsoultFilePath() {
        return absoultFilePath;
    }

    /**
     *
     * @param absoultFilePath
     */
    public void setAbsoultFilePath(String absoultFilePath) {
        this.absoultFilePath = absoultFilePath;
    }

    /**
     *
     * @return
     */
    public String getRelFilePath() {
        return relFilePath;
    }

    /**
     *
     * @param relFilePath
     */
    public void setRelFilePath(String relFilePath) {
        this.relFilePath = relFilePath;
    }
}
