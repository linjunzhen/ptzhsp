/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.model;

/**
 *
 * 描述 投资项目查询接口反回对象
 *
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年12月12日 下午12:31:56
 */
public class TzProjectRespones {
    /**
     * 返回查询结果状态   true:成功  成功时data 数据包有值  , false:失败  errcode和errmsg有值
     */
    private boolean result;
    /**
     * 返回的错误码
     */
    private String errcode;
    /**
     * 返回的错误信息
     */
    private String errmsg;
    /**
     * 数据包
     */
    private TzProjectData tzProject;
    /**
     *
     * 描述    属性get方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public boolean isResult() {
        return result;
    }
    /**
     *
     * 描述    属性Set方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public void setResult( boolean result) {
        this.result = result;
    }
    /**
     *
     * 描述    属性get方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public String getErrcode() {
        return errcode;
    }
    /**
     *
     * 描述    属性Set方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
    /**
     *
     * 描述    属性get方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public String getErrmsg() {
        return errmsg;
    }
    /**
     *
     * 描述    属性Set方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    /**
     *
     * 描述    属性get方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public TzProjectData getTzProject() {
        return tzProject;
    }
    /**
     *
     * 描述    属性Set方法
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:49:32
     * @return
     */
    public void setTzProject(TzProjectData tzProject) {
        this.tzProject = tzProject;
    }
    public TzProjectData tzProjectDataInstance(){
        return new TzProjectData();
    }
    /**
     *
     * 描述   数据包
     * @author Derek Zhang
     * @created 2015年12月13日 上午8:23:54
     */
    public class TzProjectData{
        /**
         * ============  datas数据包 字段  beging  =============================
         */
        /**
         * 项目所属区划
         */
        private String divisionCode;
        /**
         * 项目名称
         */
        private String projectName;
        /**
         * 项目类型
         */
        private String projectType;
        /**
         * 建设性质
         */
        private String projectNature;
        /**
         * 项目法人单位
         */
        private String enterpriseName;
        /**
         * 项目法人证照类型
         */
        private String lerepCertType;
        /**
         * 项目法人证照号码
         */
        private String lerepCertNo;
        /**
         * 项目拟开工时间
         */
        private String startYear;
        /**
         * 项目拟建成时间
         */
        private String endYear;
        /**
         * 总投资（万元）
         */
        private String totalMoney;
        /**
         * 建设规模及内容
         */
        private String scaleContent;
        /**
         * 项目备注
         */
        private String projectRemark;
        /**
         * 申报日期
         */
        private String applyDate;
        /**
         * 建设地点
         */
        private String placeCode;
        /**
         * 建设地点详情
         */
        private String placeCodeDetail;
        /**
         * 是事开发区项目
         */
        private String isDeArea;
        /**
         * 开发区名称
         */
        private String deAreaName;
        /**
         * 所属行业
         */
        private String industry;
        /**
         * 投资项目行业分类
         */
        private String permitIndustry;
        /**
         * 项目阶段
         */
        private String projectStage;
        /**
         * 联系人姓名
         */
        private String contactName;
        /**
         * 联系人身份证号码
         */
        private String contactIdCard;
        /**
         * 联系人邮箱
         */
        private String contactEmail;
        /**
         * 联系人电话
         */
        private String contactTel;
        /**
         * 联系人手机号
         */
        private String contactMobile;
        /**
         * 联系人地址
         */
        private String contactAddress;
        /**
         * 联系人邮编
         */
        private String contactPostCode;
        /**
         * 是否外商投资/境外投资
         */
        private String foreignabroadFlag;
        /**
         * 所属行业
         */
        private String theIndustry;
        /**
         * 总投资额为0说明
         */
        private String totalMoneyExplain;
        /**
         * 项目（法人）单位信息
         */
        private String lerepInfo;
        /**
         * 出资情况信息
         */
        private String contributionInfo;  
        /**
         * 项目属性
         */
        private String projectAttributes;  
        /**
         * 产业结构调整指导目录
         */
        private String industryStructure;  
  
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getDivisionCode() {
            return divisionCode;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setDivisionCode(String divisionCode) {
            this. divisionCode = divisionCode;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getProjectName() {
            return projectName;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setProjectName(String projectName) {
            this. projectName = projectName;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getProjectType() {
            return projectType;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setProjectType(String projectType) {
            this. projectType = projectType;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getProjectNature() {
            return projectNature;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setProjectNature(String projectNature) {
            this. projectNature = projectNature;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getEnterpriseName() {
            return enterpriseName;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setEnterpriseName(String enterpriseName) {
            this. enterpriseName = enterpriseName;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getLerepCertType() {
            return lerepCertType;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setLerepCertType(String lerepCertType) {
            this. lerepCertType = lerepCertType;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getLerepCertNo() {
            return lerepCertNo;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setLerepCertNo(String lerepCertNo) {
            this. lerepCertNo = lerepCertNo;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getStartYear() {
            return startYear;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setStartYear(String startYear) {
            this. startYear = startYear;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getEndYear() {
            return endYear;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setEndYear(String endYear) {
            this. endYear = endYear;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getTotalMoney() {
            return totalMoney;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setTotalMoney(String totalMoney) {
            this. totalMoney = totalMoney;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getScaleContent() {
            return scaleContent;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setScaleContent(String scaleContent) {
            this. scaleContent = scaleContent;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getProjectRemark() {
            return projectRemark;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setProjectRemark(String projectRemark) {
            this. projectRemark = projectRemark;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getApplyDate() {
            return applyDate;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setApplyDate(String applyDate) {
            this. applyDate = applyDate;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getPlaceCode() {
            return placeCode;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setPlaceCode(String placeCode) {
            this. placeCode = placeCode;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getPlaceCodeDetail() {
            return placeCodeDetail;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setPlaceCodeDetail(String placeCodeDetail) {
            this. placeCodeDetail = placeCodeDetail;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getIsDeArea() {
            return isDeArea;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setIsDeArea(String isDeArea) {
            this. isDeArea = isDeArea;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getDeAreaName() {
            return deAreaName;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setDeAreaName(String deAreaName) {
            this. deAreaName = deAreaName;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getIndustry() {
            return industry;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setIndustry(String industry) {
            this. industry = industry;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getPermitIndustry() {
            return permitIndustry;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setPermitIndustry(String permitIndustry) {
            this. permitIndustry = permitIndustry;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getProjectStage() {
            return projectStage;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setProjectStage(String projectStage) {
            this. projectStage = projectStage;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactName() {
            return contactName;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactName(String contactName) {
            this. contactName = contactName;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactIdCard() {
            return contactIdCard;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactIdCard(String contactIdCard) {
            this. contactIdCard = contactIdCard;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactEmail() {
            return contactEmail;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactEmail(String contactEmail) {
            this. contactEmail = contactEmail;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactTel() {
            return contactTel;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactTel(String contactTel) {
            this. contactTel = contactTel;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactMobile() {
            return contactMobile;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactMobile(String contactMobile) {
            this. contactMobile = contactMobile;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactAddress() {
            return contactAddress;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactAddress(String contactAddress) {
            this. contactAddress = contactAddress;
        }
        /**
         *
         * 描述    属性get方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public String getContactPostCode() {
            return contactPostCode;
        }
        /**
         *
         * 描述    属性Set方法
         * @author Derek Zhang
         * @created 2015年12月12日 下午1:49:32
         * @return
         */
        public void setContactPostCode(String contactPostCode) {
            this. contactPostCode = contactPostCode;
        }
       
       
        public String getForeignabroadFlag() {
            return foreignabroadFlag;
        }
        public void setForeignabroadFlag(String foreignabroadFlag) {
            this. foreignabroadFlag = foreignabroadFlag;
        }public String getTheIndustry() {
            return theIndustry;
        }
        public void setTheIndustry(String theIndustry) {
            this. theIndustry = theIndustry;
        }
        public String getTotalMoneyExplain() {
            return totalMoneyExplain;
        }
        public void setTotalMoneyExplain(String totalMoneyExplain) {
            this. totalMoneyExplain = totalMoneyExplain;
        }
        public String getLerepInfo() {
            return lerepInfo;
        }
        public void setLerepInfo(String lerepInfo) {
            this. lerepInfo = lerepInfo;
        }
        public String getContributionInfo() {
            return contributionInfo;
        }
        public void setContributionInfo(String contributionInfo) {
            this. contributionInfo = contributionInfo;
        }
        public String getIndustryStructure() {
            return industryStructure;
        }
        public void setIndustryStructure(String industryStructure) {
            this.industryStructure = industryStructure;
        }
        public String getProjectAttributes() {
            return projectAttributes;
        }
        public void setProjectAttributes(String projectAttributes) {
            this.projectAttributes = projectAttributes;
        }
       
    }
  
}