/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.model;

import java.util.HashSet;
import java.util.Set;

import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.model.BaseModel;

/**
 * 描述 系统用户实体类
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午3:36:43
 */
public class SysUser extends BaseModel {

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "PTzhsp@";
    /**
     * 超级管理员资源KEYS
     */
    public static final String ADMIN_RESKEY = "__ALL";
    /**
     * 激活状态
     */
    public static final int STATUS_ACTIVE = 1;
    /**
     * 禁用状态
     */
    public static final int STATUS_FREEZEN = -1;
    /**
     * 停用状态
     */
    public static final int STATUS_DISC = 2;
    /**
     * 删除状态
     */
    public static final int STATUS_DEL = 0;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 帐号
     */
    private String username;
    /**
     * 用户姓名
     */
    private String fullname;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String mobile;
    
    /**
     * 证件号码
     */
    private String usercard;
    
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 状态1:激活0:禁用-1:删除
     */
    private int status;
    /**
     * 性别:激活0:禁用-1:删除
     */
    private Integer sex;
    /**
     * 入库时间
     */
    private String createTime;
    /**
     * 拥有的资源KEYS
     */
    private String resKeys;
    /**
     * 被授权的部门代码
     */
    private String authDepCodes;
    /**
     * 获取部门ID
     */
    private String depId;
    /**
     * 获取部门名称
     */
    private String depCode;
    /**
     * 获取部门名称
     */
    private String depName;
    /**
     * 是否修改过密码
     */
    private String isModifyPass;
    /**
     * 是否接收短信
     */
    private String isAcceptMsg;
    /**
     * 是否绑定唯一身份证和手机号
     */
    private String isUniqueBind;
    /**
     * 所授权资源KEY集合
     */
    private Set<String> resKeySet = new HashSet<String>();
    /**
     * 所授权角色编码集合
     */
    private Set<String> roleCodes = new HashSet<String>();
    /**
     * 所授权的url权限
     */
    private Set<String> urlSet = new HashSet<String>();

    /**
     * 登录用户的信息JSON
     */
    private String loginUserInfoJson;
    //单点省网数据
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 是否实名
     */
    private String isRealName;
    /**
     * 实名认证方式
     */
    private String realNameType;
    /**
     * 最后更新时间
     */
    private String lastUpdateTime;
    /**
     * 证件类型
     */
    private String certificateType;
    /**
     * 证件号码
     */
    private String certificateNumber;
    /**
     * 民族
     */
    private String nation;
    /**
     * 出生日期
     */
    private String birth;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 照片
     */
    private String photo;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 机构类型
     */
    private String orgType;
    /**
     * 组织机构代码/统一社会信用代码
     */
    private String orgCode;
    /**
     * 注册地址
     */
    private String orgAddress;
    /**
     * 注册日期
     */
    private String orgDate;
    /**
     * 法人代表/负责人
     */
    private String lawName;
    /**
     * 法人性别
     */
    private Integer orgLawSex;
    /**
     * 法人代表/负责人身份证
     */
    private String orgLawIdcard;
    /**
     * 法人手机号码
     */
    private String orgLawMobile;
    /**
     * 经办人姓名
     */
    private String jbrName;
    /**
     * 经办人身份证号
     */
    private String jbrCertificatenumber;
    /**
     * 经办人邮箱
     */
    private String jbrEmail;
    /**
     * 经办人手机号码
     */
    private String mobilePhone;

    
    
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(String isRealName) {
        this.isRealName = isRealName;
    }

    public String getRealNameType() {
        return realNameType;
    }

    public void setRealNameType(String realNameType) {
        this.realNameType = realNameType;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgDate() {
        return orgDate;
    }

    public void setOrgDate(String orgDate) {
        this.orgDate = orgDate;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public Integer getOrgLawSex() {
        return orgLawSex;
    }

    public void setOrgLawSex(Integer orgLawSex) {
        this.orgLawSex = orgLawSex;
    }

    public String getOrgLawIdcard() {
        return orgLawIdcard;
    }

    public void setOrgLawIdcard(String orgLawIdcard) {
        this.orgLawIdcard = orgLawIdcard;
    }

    public String getOrgLawMobile() {
        return orgLawMobile;
    }

    public void setOrgLawMobile(String orgLawMobile) {
        this.orgLawMobile = orgLawMobile;
    }

    public String getJbrName() {
        return jbrName;
    }

    public void setJbrName(String jbrName) {
        this.jbrName = jbrName;
    }

    public String getJbrCertificatenumber() {
        return jbrCertificatenumber;
    }

    public void setJbrCertificatenumber(String jbrCertificatenumber) {
        this.jbrCertificatenumber = jbrCertificatenumber;
    }

    public String getJbrEmail() {
        return jbrEmail;
    }

    public void setJbrEmail(String jbrEmail) {
        this.jbrEmail = jbrEmail;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getUsername() {
        return username;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getPassword() {
        return password;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getEmail() {
        return email;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public int getStatus() {
        return status;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @return type
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:27
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月20日 下午6:21:40
     * @return type
     */
    public String getResKeys() {
        return resKeys;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月20日 下午6:21:40
     * @param resKeys
     */
    public void setResKeys(String resKeys) {
        this.resKeys = resKeys;
    }

    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:55
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2014年9月11日 下午3:39:55
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SysUser other = (SysUser) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    /**
     * @author Flex Hu
     * @created 2014年12月2日 下午5:14:18
     * @return type
     */
    public Set<String> getResKeySet() {
        return resKeySet;
    }

    /**
     * @author Flex Hu
     * @created 2014年12月2日 下午5:14:18
     * @param resKeySet
     */
    public void setResKeySet(Set<String> resKeySet) {
        this.resKeySet = resKeySet;
    }

    /**
     * @author Danto Huang
     * @created 2020年6月22日 上午9:13:41
     * @return type
     */
    public Set<String> getUrlSet() {
        return urlSet;
    }

    /**
     * @author Danto Huang
     * @created 2020年6月22日 上午9:13:41
     * @param urlSet
     */
    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月26日 上午11:14:46
     * @return type
     */
    public String getLoginUserInfoJson() {
        return loginUserInfoJson;
    }

    /**
     * @author Flex Hu
     * @created 2015年8月26日 上午11:14:46
     * @param loginUserInfoJson
     */
    public void setLoginUserInfoJson(String loginUserInfoJson) {
        this.loginUserInfoJson = loginUserInfoJson;
    }

    /**
     * @author Flex Hu
     * @created 2015年9月15日 上午11:02:22
     * @return type
     */
    public String getAuthDepCodes() {
        return authDepCodes;
    }

    /**
     * @author Flex Hu
     * @created 2015年9月15日 上午11:02:22
     * @param authDepCodes
     */
    public void setAuthDepCodes(String authDepCodes) {
        this.authDepCodes = authDepCodes;
    }

    /**
     * @author Flex Hu
     * @created 2015年9月22日 下午3:09:02
     * @return type
     */
    public String getDepId() {
        return depId;
    }

    /**
     * @author Flex Hu
     * @created 2015年9月22日 下午3:09:02
     * @param depId
     */
    public void setDepId(String depId) {
        this.depId = depId;
    }

    /**
     * @author Flex Hu
     * @created 2015年9月22日 下午3:09:02
     * @return type
     */
    public String getDepCode() {
        return depCode;
    }

    /**
     * @author Flex Hu
     * @created 2015年9月22日 下午3:09:02
     * @param depCode
     */
    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    /**
     * 
     * 描述
     * 
     * @author Derek Zhang
     * @created 2015年10月13日 下午2:31:06
     * @return
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 
     * 描述
     * 
     * @author Derek Zhang
     * @created 2015年10月13日 下午2:31:06
     * @return
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @author Flex Hu
     * @created 2015年10月24日 上午6:33:58
     * @return type
     */
    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    /**
     * @author Flex Hu
     * @created 2015年10月24日 上午6:33:58
     * @param roleCodes
     */
    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    /**
     * @return the depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName the depName to set
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }
    /**
     * @author Flex Hu
     * @created 2015年12月24日 下午4:11:51
     * @return type
     */
    public String getIsModifyPass() {
        return isModifyPass;
    }

    /**
     * @author Flex Hu
     * @created 2015年12月24日 下午4:11:51
     * @param isModifyPass
     */
    public void setIsModifyPass(String isModifyPass) {
        this.isModifyPass = isModifyPass;
    }

    /**
     * 
     * @return
     */
    public String getIsAcceptMsg() {
        return isAcceptMsg;
    }

    /**
     * 
     * @param isAcceptMsg
     */
    public void setIsAcceptMsg(String isAcceptMsg) {
        this.isAcceptMsg = isAcceptMsg;
    }
    
    /**
     * @author Allin Lin
     * @created 2021年1月9日 下午12:54:59
     * @return type
     */
    public String getUsercard() {
        return usercard;
    }

    /**
     * @author Allin Lin
     * @created 2021年1月9日 下午12:54:59
     * @param usercard
     */
    public void setUsercard(String usercard) {
        this.usercard = usercard;
    }

    /**
     * @author Danto Huang
     * @created 2021年9月2日 上午10:45:11
     * @return type
     */
    public String getIsUniqueBind() {
        return isUniqueBind;
    }

    /**
     * @author Danto Huang
     * @created 2021年9月2日 上午10:45:11
     * @param isUniqueBind
     */
    public void setIsUniqueBind(String isUniqueBind) {
        this.isUniqueBind = isUniqueBind;
    }
    
}
