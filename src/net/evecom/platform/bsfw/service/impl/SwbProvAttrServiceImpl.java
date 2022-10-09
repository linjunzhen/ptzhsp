/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.SwbProvAttrDao;
import net.evecom.platform.bsfw.service.SwbProvAttrService;

import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月27日 上午11:04:14
 */
@Service("swbProvAttrService")
public class SwbProvAttrServiceImpl extends BaseServiceImpl implements SwbProvAttrService {
    
    /**
     * 所引入的dao
     */
    @Resource
    private SwbProvAttrDao dao;

    /**
     * 描述
     * @author Flex Hu
     * @created 2016年1月27日 上午11:04:14
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     *
     * 描述 生成省网办的附件数据到磁盘
     * @author Flex Hu
     * @created 2016年1月27日 上午11:10:18
     * @param fileId
     */
    public String genFileToDiskAndSave(String fileId,String fileType){
        Map<String,Object> attachFile = dao.getByJdbc("T_BSFW_SWBPROVDATA_ATTR",
                new String[]{"ID"},new Object[]{fileId});
        if(attachFile!=null){
            Properties properties = FileUtil.readProperties("project.properties");
            StringBuffer fileFullPath = new StringBuffer("");
            String attachFileFolderPath = properties.getProperty("AttachFilePath");
            String fileRelativePath = "attachFiles/swb/"+(DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+"/");
            String fileName = fileId+"."+fileType;
            fileFullPath.append(attachFileFolderPath).append(fileRelativePath).append(fileName);
            //获取内容
            byte[] bys = (byte[])attachFile.get("CONTENT");
            if (bys != null) {
                String hexString = StringUtil.byte2hex(bys);
                FileUtil.convertStringToFile(hexString,fileFullPath.toString());
            }
            return fileRelativePath+fileName;
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 生成省网办的附件数据到磁盘
     * @author Flex Hu
     * @created 2016年1月27日 上午11:10:18
     * @param fileId
     */
    public String genFileToDiskAndSaveL(String fileId,String fileType,String fileName,String filePath,
                                       String busTableName,String code,String sqfs,String busRecordId){
        Map<String,Object> attachFile = dao.getByJdbc("T_BSFW_SWBPROVDATA_ATTR",
                new String[]{"ID"},new Object[]{fileId});
        if(attachFile!=null){
            Object content = attachFile.get("CONTENT");
            if (content != null) {
                //获取内容
                byte[] bys = (byte[]) content;
                Properties properties = FileUtil.readProperties("project.properties");
                String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
                String url = uploadFileUrl + "upload/file";// 上传路径
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                HashMap<String, Object> params = new HashMap<>();
                params.put("uploaderId", "swb");// 上传人ID
                params.put("uploaderName", "swb"); // 上传人姓名
                params.put("typeId", "0");// 上传类型ID，默认0
                params.put("name", fileName);
                params.put("busTableName", busTableName);
                params.put("attachKey", code);
                params.put("busRecordId", busRecordId);
                String result = HttpRequestUtil.sendByteFilePost(url, bys, responesCode, app_id, password, params);
                Map<String, Object> jsonMap = JSON.parseObject(result, Map.class);
                String id = "";
                if ((boolean) jsonMap.get("success")) {
                    Map<String, Object> data = (Map<String, Object>) jsonMap.get("data");
                    id = (String) data.get("fileId");
                }
                return id;
            } else {
                return null;
            }
        }else{
            return null;
        }
    }

}
