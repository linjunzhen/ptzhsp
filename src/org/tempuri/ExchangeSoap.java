/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tempuri;

/**
 * 描述
 * 
 * @author Watson Zheng
 * @created 2016年12月14日 下午10:26:57
 */
public interface ExchangeSoap extends java.rmi.Remote {
    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:00
     * @param account
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean authorization(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:02
     * @param licenseName
     * @param version
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String getLicenseDataXsd(java.lang.String licenseName, java.lang.String version)
            throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:03
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitLicenseData(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:05
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitLicenseDataAndSeal(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:06
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitLicenseDataAndAutoSeal(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:07
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @param edcFile
     * @param edcFileMD5
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitLicenseDataWithEdc(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml, java.lang.String edcFile, java.lang.String edcFileMD5)
            throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:09
     * @param approvalName
     * @param serialNumberFormat
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String getApprovalDataXsd(java.lang.String approvalName, java.lang.String serialNumberFormat)
            throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:10
     * @param approvalName
     * @param approvalDataXml
     * @param rawFile
     * @param rawFileMD5
     * @param rawFileName
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitApprovalData(java.lang.String approvalName, java.lang.String approvalDataXml,
            java.lang.String rawFile, java.lang.String rawFileMD5, java.lang.String rawFileName)
            throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:12
     * @param approvalName
     * @param approvalDataXml
     * @param rawFile
     * @param rawFileMD5
     * @param rawFileName
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitApprovalDataAndSeal(java.lang.String approvalName, java.lang.String approvalDataXml,
            java.lang.String rawFile, java.lang.String rawFileMD5, java.lang.String rawFileName)
            throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:13
     * @param approvalName
     * @param approvalDataXml
     * @param edcFile
     * @param edcFileMD5
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String submitApprovalDataWithEdc(java.lang.String approvalName, java.lang.String approvalDataXml,
            java.lang.String edcFile, java.lang.String edcFileMD5) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:15
     * @param item
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String getEdcFile(java.lang.String item) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:17
     * @param item
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String isSignatured(java.lang.String item) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:19
     * @param licenseName
     * @param version
     * @param licenseDataXml
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String uploadLicenseData(java.lang.String licenseName, java.lang.String version,
            java.lang.String licenseDataXml) throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:20
     * @param licenseName
     * @param version
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String getExcelImportTemplate(java.lang.String licenseName, java.lang.String version)
            throws java.rmi.RemoteException;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:27:21
     * @param licenseName
     * @param version
     * @param base64EncodedFile
     * @param format
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String importFromExcel(java.lang.String licenseName, java.lang.String version,
            java.lang.String base64EncodedFile, org.apache.axis.types.UnsignedByte format)
            throws java.rmi.RemoteException;
}
