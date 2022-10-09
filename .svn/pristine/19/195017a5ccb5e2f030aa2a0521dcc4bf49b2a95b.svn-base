/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 描述 公共参数xml解析文件
 * @author Flex Hu
 * @created 2017-5-25 上午10:43:14
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Approval")
public class CommonExchange implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * 描述
     */
    @XmlAttribute(name = "xmlns")
    private String xmlns = "http://www.fgi.net.cn/";

    /***/
    @XmlElement(name = "Face")
    private Face face;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:01:42
     * @return
     */
    public Face getFace() {
        return face;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:01:47
     * @param face
     */
    public void setFace(Face face) {
        this.face = face;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:01:50
     * @return
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:01:53
     * @param metadata
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:01:56
     * @return
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:01:59
     * @param xmlns
     */
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:02:04
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Face implements Serializable {

        /** serialVersionUID */
        private static final long serialVersionUID = 1L;

        /**
         * 描述
         */
        @XmlAttribute(name = "xmlns")
        private String xmlns = "";

        /****/
        @XmlElement(name = "SerialNumber")
        private String serialNumber;

        /**
         * 描述
         */
        @XmlElement(name = "Name")
        private String name;

        /**
         * 描述
         */
        @XmlElement(name = "Topic")
        private String topic;

        /**
         * 描述
         */
        @XmlElement(name = "Department")
        private String department;

        /**
         * 描述
         */
        @XmlElement(name = "Office")
        private String office;

        /**
         * 描述
         */
        @XmlElement(name = "Drafter")
        private String drafter;

        /**
         * 描述
         */
        @XmlElement(name = "Signer")
        private String signer;

        /**
         * 描述
         */
        @XmlElement(name = "PublishDate")
        private String publishDate;

        /**
         * 描述
         */
        @XmlElement(name = "MajorPost")
        private String majorPost;

        /**
         * 描述
         */
        @XmlElement(name = "CopyPost")
        private String copyPost;

        /**
         * 描述
         */
        @XmlElement(name = "PostCount")
        private String postCount;

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:02:49
         * @return
         */
        public String getXmlns() {
            return xmlns;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:02:51
         * @param xmlns
         */
        public void setXmlns(String xmlns) {
            this.xmlns = xmlns;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:02:54
         * @return
         */
        public String getSerialNumber() {
            return serialNumber;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:02:56
         * @param serialNumber
         */
        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:02:59
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:02
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:04
         * @return
         */
        public String getTopic() {
            return topic;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:08
         * @param topic
         */
        public void setTopic(String topic) {
            this.topic = topic;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:10
         * @return
         */
        public String getDepartment() {
            return department;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:13
         * @param department
         */
        public void setDepartment(String department) {
            this.department = department;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:15
         * @return
         */
        public String getOffice() {
            return office;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:19
         * @param office
         */
        public void setOffice(String office) {
            this.office = office;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:22
         * @return
         */
        public String getDrafter() {
            return drafter;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:25
         * @param drafter
         */
        public void setDrafter(String drafter) {
            this.drafter = drafter;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:28
         * @return
         */
        public String getSigner() {
            return signer;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:43
         * @param signer
         */
        public void setSigner(String signer) {
            this.signer = signer;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:46
         * @return
         */
        public String getPublishDate() {
            return publishDate;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:49
         * @param publishDate
         */
        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:51
         * @return
         */
        public String getMajorPost() {
            return majorPost;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:54
         * @param majorPost
         */
        public void setMajorPost(String majorPost) {
            this.majorPost = majorPost;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:56
         * @return
         */
        public String getCopyPost() {
            return copyPost;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:03:59
         * @param copyPost
         */
        public void setCopyPost(String copyPost) {
            this.copyPost = copyPost;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:01
         * @return
         */
        public String getPostCount() {
            return postCount;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:04
         * @param postCount
         */
        public void setPostCount(String postCount) {
            this.postCount = postCount;
        }

    }

    /***/
    @XmlElement(name = "Metadata")
    private Metadata metadata;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:04:09
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Metadata implements Serializable {

        /**
         * 描述
         */
        private static final long serialVersionUID = -1;

        /**
         * 描述
         */
        @XmlAttribute(name = "xmlns")
        private String xmlns = "";

        /***/
        @XmlElement(name = "Basic")
        private Basic basic;

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:18
         * @return
         */
        public Basic getBasic() {
            return basic;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:21
         * @param basic
         */
        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:24
         * @return
         */
        public Extended getExtended() {
            return extended;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:27
         * @param extended
         */
        public void setExtended(Extended extended) {
            this.extended = extended;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:29
         * @return
         */
        public String getXmlns() {
            return xmlns;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:31
         * @param xmlns
         */
        public void setXmlns(String xmlns) {
            this.xmlns = xmlns;
        }

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:04:36
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Basic implements Serializable {

            /**
             * 描述
             */
            private static final long serialVersionUID = 1L;

            /****/
            @XmlElement(name = "Name")
            private String name;

            /**
             * 描述
             */
            @XmlElement(name = "FileNumber")
            private String fileNumber;

            /**
             * 描述
             */
            @XmlElement(name = "ApprovalName")
            private String approvalName;

            /**
             * 描述
             */
            @XmlElement(name = "SerialNumberFormat")
            private String serialNumberFormat;

            /**
             * 描述
             */
            @XmlElement(name = "SerialNumber")
            private String serialNumber;

            /**
             * 描述
             */
            @XmlElement(name = "PublishDate")
            private String publishDate;

            /**
             * 描述
             */
            @XmlElement(name = "ValidFromDate")
            private String validFromDate;

            /**
             * 描述
             */
            @XmlElement(name = "ValidUntilDate")
            private String validUntilDate;

            /**
             * 描述
             */
            @XmlElement(name = "Publisher")
            private String publisher;

            /**
             * 描述
             */
            @XmlElement(name = "Owner")
            private String owner;

            /**
             * 描述
             */
            @XmlElement(name = "UpdateHistory")
            private String updateHistory;

            /**
             * 描述
             */
            @XmlElement(name = "SoftwareEnvironment")
            private String softwareEnvironment;

            /**
             * 描述
             */
            @XmlElement(name = "BusinessBehavior")
            private String businessBehavior;

            /**
             * 描述
             */
            @XmlElement(name = "SignatureInfo")
            private String signatureInfo;

            /**
             * 描述
             */
            @XmlElement(name = "DCInfo")
            private String dCInfo;

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:22
             * @return
             */
            public String getName() {
                return name;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:25
             * @param name
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:28
             * @return
             */
            public String getFileNumber() {
                return fileNumber;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:30
             * @param fileNumber
             */
            public void setFileNumber(String fileNumber) {
                this.fileNumber = fileNumber;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:34
             * @return
             */
            public String getApprovalName() {
                return approvalName;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:36
             * @param approvalName
             */
            public void setApprovalName(String approvalName) {
                this.approvalName = approvalName;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:38
             * @return
             */
            public String getSerialNumberFormat() {
                return serialNumberFormat;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:41
             * @param serialNumberFormat
             */
            public void setSerialNumberFormat(String serialNumberFormat) {
                this.serialNumberFormat = serialNumberFormat;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:44
             * @return
             */
            public String getSerialNumber() {
                return serialNumber;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:47
             * @param serialNumber
             */
            public void setSerialNumber(String serialNumber) {
                this.serialNumber = serialNumber;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:50
             * @return
             */
            public String getPublishDate() {
                return publishDate;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:52
             * @param publishDate
             */
            public void setPublishDate(String publishDate) {
                this.publishDate = publishDate;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:55
             * @return
             */
            public String getValidFromDate() {
                return validFromDate;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:05:57
             * @param validFromDate
             */
            public void setValidFromDate(String validFromDate) {
                this.validFromDate = validFromDate;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:06:00
             * @return
             */
            public String getValidUntilDate() {
                return validUntilDate;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:06:02
             * @param validUntilDate
             */
            public void setValidUntilDate(String validUntilDate) {
                this.validUntilDate = validUntilDate;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:06:05
             * @return
             */
            public String getPublisher() {
                return publisher;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:00
             * @param publisher
             */
            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:03
             * @return
             */
            public String getOwner() {
                return owner;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:05
             * @param owner
             */
            public void setOwner(String owner) {
                this.owner = owner;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:08
             * @return
             */
            public String getUpdateHistory() {
                return updateHistory;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:10
             * @param updateHistory
             */
            public void setUpdateHistory(String updateHistory) {
                this.updateHistory = updateHistory;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:12
             * @return
             */
            public String getSoftwareEnvironment() {
                return softwareEnvironment;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:15
             * @param softwareEnvironment
             */
            public void setSoftwareEnvironment(String softwareEnvironment) {
                this.softwareEnvironment = softwareEnvironment;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:17
             * @return
             */
            public String getBusinessBehavior() {
                return businessBehavior;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:20
             * @param businessBehavior
             */
            public void setBusinessBehavior(String businessBehavior) {
                this.businessBehavior = businessBehavior;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:22
             * @return
             */
            public String getSignatureInfo() {
                return signatureInfo;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:24
             * @param signatureInfo
             */
            public void setSignatureInfo(String signatureInfo) {
                this.signatureInfo = signatureInfo;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:27
             * @return
             */
            public String getdCInfo() {
                return dCInfo;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:07:30
             * @param dCInfo
             */
            public void setdCInfo(String dCInfo) {
                this.dCInfo = dCInfo;
            }

        }

        /***/
        @XmlElement(name = "Extended")
        private Extended extended;

        /**
         * 描述
         * 
         * @author Watson Zheng
         * @created 2016年12月14日 下午10:07:32
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Extended implements Serializable {

            /**
             * 描述
             */
            private static final long serialVersionUID = 1L;

            /****/
            @XmlElement(name = "AbolishedUrl")
            private String abolishedUrl;

            /**
             * 描述
             */
            @XmlElement(name = "LimitingUrl")
            private String limitingUrl;

            /**
             * 描述
             */
            @XmlElement(name = "Topic")
            private String topic;

            /**
             * 描述
             */
            @XmlElement(name = "Source")
            private String source;

            /**
             * 描述
             */
            @XmlElement(name = "Langaue")
            private String langaue;

            /**
             * 描述
             */
            @XmlElement(name = "HardwareEnvironment")
            private String hardwareEnvironment;

            /**
             * 描述
             */
            @XmlElement(name = "VerifyUrl")
            private String verifyUrl;

            /**
             * 描述
             */
            @XmlElement(name = "QueryUrl")
            private String queryUrl;

            /**
             * 描述
             */
            @XmlElement(name = "SelCode")
            private String selCode;

            /**
             * 描述
             */
            @XmlElement(name = "OwnerType")
            private String ownerType;

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:02
             * @return
             */
            public String getAbolishedUrl() {
                return abolishedUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:04
             * @param abolishedUrl
             */
            public void setAbolishedUrl(String abolishedUrl) {
                this.abolishedUrl = abolishedUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:07
             * @return
             */
            public String getLimitingUrl() {
                return limitingUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:09
             * @param limitingUrl
             */
            public void setLimitingUrl(String limitingUrl) {
                this.limitingUrl = limitingUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:11
             * @return
             */
            public String getTopic() {
                return topic;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:14
             * @param topic
             */
            public void setTopic(String topic) {
                this.topic = topic;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:17
             * @return
             */
            public String getSource() {
                return source;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:20
             * @param source
             */
            public void setSource(String source) {
                this.source = source;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:22
             * @return
             */
            public String getLangaue() {
                return langaue;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:25
             * @param langaue
             */
            public void setLangaue(String langaue) {
                this.langaue = langaue;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:27
             * @return
             */
            public String getHardwareEnvironment() {
                return hardwareEnvironment;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:30
             * @param hardwareEnvironment
             */
            public void setHardwareEnvironment(String hardwareEnvironment) {
                this.hardwareEnvironment = hardwareEnvironment;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:32
             * @return
             */
            public String getVerifyUrl() {
                return verifyUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:35
             * @param verifyUrl
             */
            public void setVerifyUrl(String verifyUrl) {
                this.verifyUrl = verifyUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:38
             * @return
             */
            public String getQueryUrl() {
                return queryUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:40
             * @param queryUrl
             */
            public void setQueryUrl(String queryUrl) {
                this.queryUrl = queryUrl;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:42
             * @return
             */
            public String getSelCode() {
                return selCode;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:45
             * @param selCode
             */
            public void setSelCode(String selCode) {
                this.selCode = selCode;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:48
             * @return
             */
            public String getOwnerType() {
                return ownerType;
            }

            /**
             * 描述
             * 
             * @author Watson Zheng
             * @created 2016年12月14日 下午10:08:50
             * @param ownerType
             */
            public void setOwnerType(String ownerType) {
                this.ownerType = ownerType;
            }

        }

    }

}
