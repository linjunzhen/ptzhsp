/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.SwbDataAttrDao;
import net.evecom.platform.bsfw.dao.SwbDataResDao;
import net.evecom.platform.bsfw.service.SwbDataAttrService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月28日 下午4:27:15
 */
@Service("swbDataAttrService")
public class SwbDataAttrServiceImpl extends BaseServiceImpl implements SwbDataAttrService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbDataAttrServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SwbDataAttrDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 存储附件指令表
     * @author Flex Hu
     * @created 2015年12月29日 上午8:37:01
     * @param resId
     * @param fileList
     */
    public void saveSwbDataAttr(String resId,List<Map<String,Object>> fileList,String itemCode){
        for(Map<String,Object> file:fileList){
            Map<String,Object> swbDataFile = this.getSwbDataFile(file, itemCode, resId);
            if(swbDataFile!=null){
                dao.saveOrUpdate(swbDataFile, "T_BSFW_SWBDATA_ATTR",null);
            }
        }
    }
    /**
     * 
     * 描述 存储附件指令表
     * @author Yanisin Shi
     * @created 2021年07月30日 上午8:37:01
     * @param resId
     * @param fileList
     */
    public void saveSwbResultDataAttr(String resId,List<Map<String,Object>> fileList,String itemCode){
        for(Map<String,Object> file:fileList){
            Map<String,Object> swbDataFile = this.getSwbDataResultFile(file, itemCode, resId);
            if(swbDataFile!=null){
                dao.saveOrUpdate(swbDataFile, "T_BSFW_SWBDATA_ATTR",null);
            }
        }
    }
    /**
     * 
     * 描述 获取需要构建的附件指令数据
     * @author Flex Hu
     * @created 2015年12月29日 上午9:17:16
     * @param file
     * @param itemCode
     * @param resId
     * @return
     */
    public Map<String,Object> getSwbDataFile(Map<String,Object> file,String itemCode,String resId){
        //获取材料编码
        String materCode = (String) file.get("ATTACH_KEY");
        String creaditFileid=StringUtil.getString(file.get("CREADIT_FILEID"));//电子证照唯一标识
        StringBuffer sql = new StringBuffer("SELECT SM.MATER_SN,A.MATER_CODE,A.MATER_NAME");
        sql.append(" FROM T_WSBS_SERVICEITEM_MATER SM LEFT JOIN T_WSBS_APPLYMATER A");
        sql.append(" ON SM.MATER_ID=A.MATER_ID LEFT JOIN T_WSBS_SERVICEITEM I");
        sql.append(" ON I.ITEM_ID=SM.ITEM_ID WHERE A.MATER_CODE=?");
        sql.append(" AND I.Item_Code=? ");
        Map<String,Object> map = dao.getByJdbc(sql.toString(), new Object[]{materCode,itemCode});
        if(map!=null){
            String materName = (String) map.get("MATER_NAME");
            int materSn = Integer.parseInt(map.get("MATER_SN").toString());
            //获取文件名称
            String fileName = (String) file.get("FILE_NAME");
            Map<String,Object> swbDataFile = new HashMap<String,Object>();
            swbDataFile.put("RES_ID", resId);
            //获取收取方式
            String SQFS = (String) file.get("SQFS");
            if(SQFS.equals("1")){
                //2021年9月16日 09:18:52 更改附件存储方式，从保存流更改为保存路径与ID
                //获取文件磁盘路径
                // 定义上传目录的根路径
//                Properties properties = FileUtil.readProperties("project.properties");
//                String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
                String filePath = (String) file.get("FILE_PATH");
//                // 写入文件
//                swbDataFile.put("ATTR_CONTENT",FileUtil.convertUrlFileToBytes(attachFileFolderPath + filePath));
//                File uploadFile = new File(attachFileFolderPath+filePath);
//                if(uploadFile.exists()){
//                    try {
////                        swbDataFile.put("ATTR_CONTENT", FileUtil.convertFileToBytes(uploadFile));
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        log.error(e.getMessage(),e);
//                    }
//                }
                swbDataFile.put("FILE_MODE","upload");
                swbDataFile.put("FILE_PATH", filePath);//新增附件路径
                swbDataFile.put("FILE_ID", file.get("FILE_ID"));//新增附件ID
            }else if(SQFS.equals("2")||SQFS.equals("3")){
                swbDataFile.put("FILE_MODE","paper");
            }
            //添加电子证照标识
            if(StringUtils.isNotEmpty(creaditFileid)){
                log.info(String.format("itemCode===%s,creditFileId===%s",itemCode,creaditFileid));
                swbDataFile.put("CREADIT_FILEID",creaditFileid);
                swbDataFile.put("FILE_MODE","license");
            }
            swbDataFile.put("CODE", materCode);
            swbDataFile.put("NAME", materName);
            swbDataFile.put("SEQ_NO", materSn);
            swbDataFile.put("FILE_NAME", fileName);
            return swbDataFile;
        }else{
            return null;
        }
    }
    /**
     * 
     * 描述 获取需要构建的办结附件指令数据
     * @author Yanisin Shi
     * @param file
     * @param itemCode
     * @param resId
     * @return
     */
    public Map<String,Object> getSwbDataResultFile(Map<String,Object> file,String itemCode,String resId){
        //获取材料编码
       //材料名称
            String materName = "办结附件";
           
            String creaditFileid=StringUtil.getString(file.get("CREADIT_FILEID"));//电子证照唯一标识
            
            int materSn = 1;
            //获取文件名称
            String fileName = (String) file.get("FILE_NAME");
            Map<String,Object> swbDataFile = new HashMap<String,Object>();
            swbDataFile.put("RES_ID", resId);
            //获取收取方式  默认上传
           //String SQFS = (String) file.get("SQFS");
         
                //获取文件磁盘路径
                // 定义上传目录的根路径
                //Properties properties = FileUtil.readProperties("project.properties");
                //String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
            
                String filePath = (String) file.get("FILE_PATH");
                // 写入文件
                //swbDataFile.put("ATTR_CONTENT",FileUtil.convertUrlFileToBytes(attachFileFolderPath + filePath));
//                File uploadFile = new File(attachFileFolderPath+filePath);
//                if(uploadFile.exists()){
//                    try {
////                        swbDataFile.put("ATTR_CONTENT", FileUtil.convertFileToBytes(uploadFile));
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        log.error(e.getMessage(),e);
//                    }
//                }
                swbDataFile.put("FILE_MODE","upload");
                swbDataFile.put("FILE_PATH", filePath);//新增附件路径
                swbDataFile.put("FILE_ID", file.get("FILE_ID"));//新增附件ID
           
            //添加电子证照标识
            if(StringUtils.isNotEmpty(creaditFileid)){
                log.info(String.format("itemCode===%s,creditFileId===%s",itemCode,creaditFileid));
                swbDataFile.put("CREADIT_FILEID",creaditFileid);
                swbDataFile.put("FILE_MODE","license");
            }
            swbDataFile.put("CODE", "bjfj202107301053");
            swbDataFile.put("NAME", materName);
            swbDataFile.put("SEQ_NO", materSn);
            swbDataFile.put("FILE_NAME", fileName);
            return swbDataFile;
     

    }
}
