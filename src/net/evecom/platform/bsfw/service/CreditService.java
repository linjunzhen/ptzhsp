/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bsfw.model.PtspFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 描述 主体信息用信息服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年06月17日 上午9:39:36
 */
@SuppressWarnings("rawtypes")
public interface CreditService extends BaseService {
    /**
     * 描述 是否需要协调
     * 
     * @author Derek Zhang
     * @created 2016年06月17日 上午9:39:36
     * @param codes
     *            names
     * @return
     */
    public Map<String, Object> findCreditList(String codes, String names);

    /**
     * 描述
     * @author Derek Zhang
     * @created 2016年6月22日 下午4:54:26
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter,String codes,String names);
    /**
     * 获取文件名
     * @param uploadPath  上传的特征路径
     * @param suffix  文件后缀名
     * @return
     */
    public String getFilePath(String uploadPath,String suffix,String fileId);
    /**
     * 下载文件
     * @param variables
     */
    public PtspFile downFile(Map<String,Object> variables);
    
    /**
     * 下载文件
     * @param variables
     */
    public PtspFile downCertFile(Map<String,Object> variables);    
    
    /**
     * 上传文件
     * @param variables
     */
    public List<Map<String,Object>> uploadFile(Map<String,Object> variables);

    /**
     * 获取机构列表
     * @return
     */
    public List<Map<String,Object>> getOrgList();
    /**
     * 获取证照目录
     * @param orgName
     * @return
     */
    public List<Map<String,Object>> getEvidenceMeta(String orgName);
    /**
     * 保存接口获取证照目录信息
     * @param orgName
     * @return
     */
    public void saveEvidenceMetaByInterface(String orgName);
    /**
     * 保存接口获取机构名称
     * @return
     */
    public void saveOrgListByInterface();
    /**
     * 获取guid
     * @return
     */
    public String getGuid();

    /**
     * 获取电子证照树json
     * @return
     */
    public String getEviditTreeJson(Map<String,Object> variables);
    /**
     * 获取电子证照信息列表
     * @param variables
     * @return
     */
    public List<Map<String, Object>> findDatagrid(Map<String, Object> variables);
    /**
     * 上传文件服务器并获取Ptspfile对象
     * @param fileName
     * @param file
     */
    public PtspFile uploadAndGetPtspfile(Map<String,Object> variables,String fileName, File file,String typeId);
    /**
     * 上传baseCode64服务器并获取Ptspfile对象
     * @param fileName
     */
    public PtspFile uploadBase64AndGetPtspfile(String fileName,String baseCode64,String typeId);

    /**
     * 描述:根据用户id获取信息
     *
     * @author Madison You
     * @created 2020/11/26 10:29:00
     * @param
     * @return
     */
    void getUserInfoByUserId(Map<String, Object> clientInfoMap, Map<String, Object> requestMap);
    
    /**
     * 获取电子证照信息列表
     * @param variables
     * @return
     */
    public List<Map<String, Object>> getCertificateType(Map<String, Object> variables);
    /**
     * 获取可信文件列表信息
     * 
     * @author Scolder Lin
     * @param variables 前台传递的参数
     * @return
     */
    public List<Map<String, Object>> findElectDocumentDatagrid(Map<String, Object> variables);
    
    /**
     * 上传可信文件
     * @author Scolder Lin
     * 
     * @param variables 前台传递的参数
     * @return
     */
    public List<Map<String,Object>> uploadElectDocumentFile(Map<String,Object> variables);
}
