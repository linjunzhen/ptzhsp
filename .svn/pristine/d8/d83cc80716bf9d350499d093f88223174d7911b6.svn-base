/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tempuri;

/**
 * 描述
 * 
 * @author Watson Zheng
 * @created 2016年12月14日 下午10:27:37
 */
public class ExchangeSoapProxy implements org.tempuri.ExchangeSoap {
    /**
     * 描述
     */
    private String _endpoint = null;
    /**
     * 描述
     */
    private org.tempuri.ExchangeSoap exchangeSoap = null;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:44
     */
    public ExchangeSoapProxy() {
        initExchangeSoapProxy();
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:46
     * @param endpoint
     */
    public ExchangeSoapProxy(String endpoint) {
        _endpoint = endpoint;
        initExchangeSoapProxy();
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:49
     */
    private void initExchangeSoapProxy() {
        try {
            exchangeSoap = (new org.tempuri.ExchangeLocator()).getExchangeSoap();
            if (exchangeSoap != null) {
                if (_endpoint != null)
                    ((javax.xml.rpc.Stub) exchangeSoap)._setProperty("javax.xml.rpc.service.endpoint.address",
                            _endpoint);
                else
                    _endpoint = (String) ((javax.xml.rpc.Stub) exchangeSoap)
                            ._getProperty("javax.xml.rpc.service.endpoint.address");
            }
        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:52
     * @return
     */
    public String getEndpoint() {
        return _endpoint;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:54
     * @param endpoint
     */
    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (exchangeSoap != null)
            ((javax.xml.rpc.Stub) exchangeSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:56
     * @return
     */
    public org.tempuri.ExchangeSoap getExchangeSoap() {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:59
     * @param account
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#authorization(java.lang.String,
     *      java.lang.String)
     */
    public boolean authorization(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.authorization(account, password);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:02
     * @param licenseName
     * @param version
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getLicenseDataXsd(java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String getLicenseDataXsd(java.lang.String licenseName, java.lang.String version)
            throws java.rmi.RemoteException {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.getLicenseDataXsd(licenseName, version);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:05
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitLicenseData(licenseName, version, licenseDataXml);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:08
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitLicenseDataAndSeal(licenseName, version, licenseDataXml);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:10
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitLicenseDataAndAutoSeal(licenseName, version, licenseDataXml);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:13
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitLicenseDataWithEdc(licenseName, version, licenseDataXml, edcFile, edcFileMD5);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:16
     * @param approvalName
     * @param serialNumberFormat
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getApprovalDataXsd(java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String getApprovalDataXsd(java.lang.String approvalName, java.lang.String serialNumberFormat)
            throws java.rmi.RemoteException {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.getApprovalDataXsd(approvalName, serialNumberFormat);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:19
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitApprovalData(approvalName, approvalDataXml, rawFile, rawFileMD5, rawFileName);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:21
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitApprovalDataAndSeal(approvalName, approvalDataXml, rawFile, rawFileMD5, rawFileName);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:24
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.submitApprovalDataWithEdc(approvalName, approvalDataXml, edcFile, edcFileMD5);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:27
     * @param item
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getEdcFile(java.lang.String)
     */
    public java.lang.String getEdcFile(java.lang.String item) throws java.rmi.RemoteException {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.getEdcFile(item);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:29
     * @param item
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#isSignatured(java.lang.String)
     */
    public java.lang.String isSignatured(java.lang.String item) throws java.rmi.RemoteException {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.isSignatured(item);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:32
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.uploadLicenseData(licenseName, version, licenseDataXml);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:34
     * @param licenseName
     * @param version
     * @return
     * @throws java.rmi.RemoteException
     * @see org.tempuri.ExchangeSoap#getExcelImportTemplate(java.lang.String,
     *      java.lang.String)
     */
    public java.lang.String getExcelImportTemplate(java.lang.String licenseName, java.lang.String version)
            throws java.rmi.RemoteException {
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.getExcelImportTemplate(licenseName, version);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:28:37
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
        if (exchangeSoap == null)
            initExchangeSoapProxy();
        return exchangeSoap.importFromExcel(licenseName, version, base64EncodedFile, format);
    }

}