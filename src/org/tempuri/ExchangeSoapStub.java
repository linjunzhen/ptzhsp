/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tempuri;

/**
 * 描述
 * 
 * @author Watson Zheng
 * @created 2016年12月15日 下午2:07:48
 */
public class ExchangeSoapStub extends org.apache.axis.client.Stub implements org.tempuri.ExchangeSoap {

    /**
     * 描述
     */
    static org.apache.axis.description.OperationDesc[] operations;

    static {
        operations = new org.apache.axis.description.OperationDesc[15];
        initOperationDesc1();
        initOperationDesc2();
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:07:53
     */
    private static void initOperationDesc1() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        getOper0();

        getOper3();

        getOper5();

        getOper7();

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitApprovalDataWithEdc");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "edcFile"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "edcFileMD5"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitApprovalDataWithEdcResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[9] = oper;

    }

    /**
     * 描述
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:11:56
     */
    private static void getOper7() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitApprovalData");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "rawFile"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "rawFileMD5"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "rawFileName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitApprovalDataResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitApprovalDataAndSeal");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "rawFile"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "rawFileMD5"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "rawFileName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitApprovalDataAndSealResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[8] = oper;
    }

    /**
     * 描述
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:11:38
     */
    private static void getOper5() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitLicenseDataWithEdc");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "edcFile"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "edcFileMD5"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataWithEdcResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetApprovalDataXsd");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "approvalName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "serialNumberFormat"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetApprovalDataXsdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[6] = oper;
    }

    /**
     * 描述
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:11:03
     */
    private static void getOper3() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitLicenseDataAndSeal");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataAndSealResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitLicenseDataAndAutoSeal");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataAndAutoSealResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[4] = oper;
    }

    /**
     * 描述
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:10:44
     */
    private static void getOper0() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Authorization");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "account"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "password"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "AuthorizationResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLicenseDataXsd");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetLicenseDataXsdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SubmitLicenseData");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[2] = oper;
    }

    private static void initOperationDesc2() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        getOper11();

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetExcelImportTemplate");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetExcelImportTemplateResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ImportFromExcel");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "base64EncodedFile"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "format"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"),
                org.apache.axis.types.UnsignedByte.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "ImportFromExcelResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[14] = oper;

    }

    /**
     * 描述
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:13:13
     */
    private static void getOper11() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetEdcFile");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "item"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetEdcFileResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("IsSignatured");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "item"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "IsSignaturedResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UploadLicenseData");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseName"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "version"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("http://tempuri.org/", "licenseDataXml"),
                org.apache.axis.description.ParameterDesc.IN,
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
                false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://tempuri.org/", "UploadLicenseDataResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[12] = oper;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:18
     * @throws org.apache.axis.AxisFault
     */
    public ExchangeSoapStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:21
     * @param endpointURL
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public ExchangeSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:23
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public ExchangeSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:26
     * @return
     * @throws java.rmi.RemoteException
     */
    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            _call.setMaintainSession(true);
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }

            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            return _call;
        } catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:30
     * @param account
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#authorization(java.lang.String,
     *      java.lang.String)
     */
    public boolean authorization(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/Authorization");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "Authorization"));
        setRequestHeaders(_call);
        setAttachments(_call);

        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { account, password });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return ((java.lang.Boolean) _resp).booleanValue();
                } catch (java.lang.Exception _exception) {
                    return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class))
                            .booleanValue();
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:33
     * @param licenseName
     * @param version
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getLicenseDataXsd(java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String getLicenseDataXsd(java.lang.String licenseName, java.lang.String version)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setMaintainSession(true);
        _call.setOperation(operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/GetLicenseDataXsd");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "GetLicenseDataXsd"));
        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { licenseName, version });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:36
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitLicenseData(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public java.lang.String submitLicenseData(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitLicenseData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseData"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { licenseName, version, licenseDataXml });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:39
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitLicenseDataAndSeal(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public java.lang.String submitLicenseDataAndSeal(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitLicenseDataAndSeal");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataAndSeal"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { licenseName, version, licenseDataXml });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:42
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitLicenseDataAndAutoSeal(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public java.lang.String submitLicenseDataAndAutoSeal(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitLicenseDataAndAutoSeal");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataAndAutoSeal"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { licenseName, version, licenseDataXml });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:46
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @param edcFile
     * @param edcFileMD5
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitLicenseDataWithEdc(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String submitLicenseDataWithEdc(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml, java.lang.String edcFile, java.lang.String edcFileMD5)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitLicenseDataWithEdc");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitLicenseDataWithEdc"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call
                    .invoke(new java.lang.Object[] { licenseName, version, licenseDataXml, edcFile, edcFileMD5 });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:49
     * @param approvalName
     * @param serialNumberFormat
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getApprovalDataXsd(java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String getApprovalDataXsd(java.lang.String approvalName, java.lang.String serialNumberFormat)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/GetApprovalDataXsd");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "GetApprovalDataXsd"));

        setRequestHeaders(_call);
        setAttachments(_call);

        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { approvalName, serialNumberFormat });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:52
     * @param approvalName
     * @param approvalDataXml
     * @param rawFile
     * @param rawFileMD5
     * @param rawFileName
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitApprovalData(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String submitApprovalData(java.lang.String approvalName, java.lang.String approvalDataXml,
            java.lang.String rawFile, java.lang.String rawFileMD5, java.lang.String rawFileName)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitApprovalData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitApprovalData"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call
                    .invoke(new java.lang.Object[] { approvalName, approvalDataXml, rawFile, rawFileMD5, rawFileName });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:55
     * @param approvalName
     * @param approvalDataXml
     * @param rawFile
     * @param rawFileMD5
     * @param rawFileName
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitApprovalDataAndSeal(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String submitApprovalDataAndSeal(java.lang.String approvalName, java.lang.String approvalDataXml,
            java.lang.String rawFile, java.lang.String rawFileMD5, java.lang.String rawFileName)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitApprovalDataAndSeal");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitApprovalDataAndSeal"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call
                    .invoke(new java.lang.Object[] { approvalName, approvalDataXml, rawFile, rawFileMD5, rawFileName });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:08:59
     * @param approvalName
     * @param approvalDataXml
     * @param edcFile
     * @param edcFileMD5
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#submitApprovalDataWithEdc(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public java.lang.String submitApprovalDataWithEdc(java.lang.String approvalName, java.lang.String approvalDataXml,
            java.lang.String edcFile, java.lang.String edcFileMD5) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/SubmitApprovalDataWithEdc");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitApprovalDataWithEdc"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call
                    .invoke(new java.lang.Object[] { approvalName, approvalDataXml, edcFile, edcFileMD5 });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:09:02
     * @param item
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getEdcFile(java.lang.String)
     */
    public java.lang.String getEdcFile(java.lang.String item) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/GetEdcFile");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "GetEdcFile"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { item });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:09:05
     * @param item
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#isSignatured(java.lang.String)
     */
    public java.lang.String isSignatured(java.lang.String item) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/IsSignatured");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "IsSignatured"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { item });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:09:08
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#uploadLicenseData(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public java.lang.String uploadLicenseData(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/UploadLicenseData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "UploadLicenseData"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { licenseName, version, licenseDataXml });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:09:11
     * @param licenseName
     * @param version
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getExcelImportTemplate(java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String getExcelImportTemplate(java.lang.String licenseName, java.lang.String version)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/GetExcelImportTemplate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "GetExcelImportTemplate"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] { licenseName, version });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月15日 下午2:09:15
     * @param licenseName
     * @param version
     * @param base64EncodedFile
     * @param format
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#importFromExcel(java.lang.String,
     *      java.lang.String, java.lang.String,
     *      org.apache.axis.types.UnsignedByte)
     */
    public java.lang.String importFromExcel(java.lang.String licenseName, java.lang.String version,
            java.lang.String base64EncodedFile, org.apache.axis.types.UnsignedByte format)
            throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/ImportFromExcel");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://tempuri.org/", "ImportFromExcel"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call
                    .invoke(new java.lang.Object[] { licenseName, version, base64EncodedFile, format });

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

}
