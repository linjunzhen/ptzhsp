/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.ImageRemarkUtil;
import net.evecom.core.util.PdfRemarkUtil;
import net.evecom.core.util.SendMsgUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.ZipUtil;
import net.evecom.platform.commercial.dao.BankDao;
import net.evecom.platform.commercial.service.BankService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.zzhy.service.BankGenService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年12月4日 上午10:20:04
 */
@Service("bankService")
public class BankServiceImpl extends BaseServiceImpl implements BankService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BankService.class);

    /**
     * 引入bankGenService
     */
    @Resource
    private BankGenService bankGenService;
    /**
     * 引入sysUserService
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 引入dao
     */
    @Resource
    private BankDao dao;
    /**
     * 覆盖获取实体dao方法
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 工商列表数据
     * @author Danto Huang
     * @created 2017年12月4日 上午10:22:54
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findForUpload(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append("(select e.exe_id,c.company_name,c.bank_type,e.create_time,c.bank_license,");
        sql.append("case when b.license_path is not null then '已上传' else '未上传' end status from jbpm6_execution e ");
        sql.append("left join t_commercial_company c on c.company_id = e.bus_recordid ");
        sql.append("left join t_commercial_bankfile b on b.exe_id = e.exe_id ");
        sql.append("where c.is_account_open='1' and e.run_status in('1','2','3') ");
        sql.append("and (e.cur_stepnames<>'开始' or e.cur_stepnames is null)");
        sql.append("union ");
        sql.append("select e.exe_id,s.company_name,s.bank_type,e.create_time,s.bank_license,");
        sql.append("case when b.license_path is not null then '已上传' else '未上传' end status from jbpm6_execution e ");
        sql.append("left join T_COMMERCIAL_NZ_JOINTVENTURE s on s.company_id = e.bus_recordid ");
        sql.append("left join t_commercial_bankfile b on b.exe_id = e.exe_id ");
        sql.append("where s.is_account_open='1' and e.run_status in('1','2','3') ");
        sql.append("and (e.cur_stepnames<>'开始' or e.cur_stepnames is null)");
        sql.append("union ");
        sql.append("select e.exe_id,s.branch_name,s.bank_type,e.create_time,s.bank_license,");
        sql.append("case when b.license_path is not null then '已上传' else '未上传' end status from jbpm6_execution e ");
        sql.append("left join T_COMMERCIAL_BRANCH s on s.branch_id = e.bus_recordid ");
        sql.append("left join t_commercial_bankfile b on b.exe_id = e.exe_id ");
        sql.append("where s.is_account_open='1' and e.run_status in('1','2','3') ");
        sql.append("and (e.cur_stepnames<>'开始' or e.cur_stepnames is null)");
        sql.append("union ");
        sql.append("select e.exe_id,s.company_name,s.bank_type,e.create_time,s.bank_license,");
        sql.append("case when b.license_path is not null then '已上传' else '未上传' end status from jbpm6_execution e ");
        sql.append("left join T_COMMERCIAL_INTERNAL_STOCK s on s.internal_stock_id = e.bus_recordid ");
        sql.append("left join t_commercial_bankfile b on b.exe_id = e.exe_id ");
        sql.append("where s.is_account_open='1' and e.run_status in('1','2','3') ");
        sql.append("and (e.cur_stepnames<>'开始' or e.cur_stepnames is null)");
        sql.append("union ");
        sql.append("select e.exe_id,s.company_name,s.bank_type,e.create_time,s.bank_license,");
        sql.append("case when b.license_path is not null then '已上传' else '未上传' end status from jbpm6_execution e ");
        sql.append("left join t_commercial_solelyinvest s on s.solely_id = e.bus_recordid ");
        sql.append("left join t_commercial_bankfile b on b.exe_id = e.exe_id ");
        sql.append("where s.is_account_open='1' and e.run_status in('1','2','3') ");
        sql.append("and (e.cur_stepnames<>'开始' or e.cur_stepnames is null)");
        sql.append(") t where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述  上传材料保存
     * @author Danto Huang
     * @created 2017年12月6日 下午5:07:08
     * @param variables
     */
    @SuppressWarnings("unchecked")
    public void saveUploadInfo(Map<String, Object> variables){
        String entityId = variables.get("EXE_ID").toString();
        Map<String ,Object> exe = dao.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{entityId});
        String tableName = exe.get("BUS_TABLENAME").toString();
        if (tableName.equals("T_COMMERCIAL_DOMESTIC") || tableName.equals("T_COMMERCIAL_FOREIGN")) {
            tableName = "T_COMMERCIAL_COMPANY";
        }
        String pkey = dao.getPrimaryKeyName(tableName).get(0).toString();
        Map<String, Object> busRecord = dao.getByJdbc(tableName, new String[] { pkey },
                new Object[] { exe.get("BUS_RECORDID") });
        String bankType = busRecord.get("BANK_TYPE").toString();
        dao.saveOrUpdateForAssignId(variables, "T_COMMERCIAL_BANKFILE", entityId);
        String senMsg = "您好，申报号为"+entityId+"的单位开户开户申请材料已上传，请及时办理！";
        List<Map<String,Object>> userList = sysUserService.findUsersByRoleCode(bankType);
        for (Map<String, Object> user : userList) {
            String mobile = (String) user.get("MOBILE");
            if (StringUtils.isNotEmpty(mobile)) {
                log.info("发送号码:" + mobile + ",的内容是:" +senMsg);
                SendMsgUtil.saveSendMsg(senMsg, mobile);
            }
        }
    }

    /**
     * 
     * 描述 工商列表数据
     * @author Danto Huang
     * @created 2017年12月4日 上午10:22:54
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findForDownload(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();

        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述  打包下载
     * @author Danto Huang
     * @created 2017年12月6日 上午9:22:57
     * @param exeId
     */
    @SuppressWarnings("unchecked")
    public void genApplyRar(String exeId, HttpServletResponse response) {
        Map<String, Object> exe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
        String tableName = exe.get("BUS_TABLENAME").toString();
        if (tableName.equals("T_COMMERCIAL_DOMESTIC") || tableName.equals("T_COMMERCIAL_FOREIGN")) {
            tableName = "T_COMMERCIAL_COMPANY";
        }
        String pkey = dao.getPrimaryKeyName(tableName).get(0).toString();
        Map<String, Object> busRecord = dao.getByJdbc(tableName, new String[] { pkey },
                new Object[] { exe.get("BUS_RECORDID") });
        busRecord.put("tableName", tableName);
        //genApply(busRecord);
        genImageMater(exeId, busRecord);

        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath");
        String targetPath = attachFileFolderPath + "attachFiles/bankDownload/" + busRecord.get("COMPANY_NAME");
        String zipPath = attachFileFolderPath + "attachFiles/bankDownload/" + busRecord.get("COMPANY_NAME") + ".zip";
        String fileName = (String) busRecord.get("COMPANY_NAME") + ".zip";

        OutputStream toClient = null;
        InputStream fis = null;
        try {
            ZipUtil.zip(new File(targetPath), new File(zipPath));

            // path是指欲下载的文件的路径。
            File file = new File(zipPath);

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(zipPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header

            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-msdownload");

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.info("取消下载或下载异常");
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
    
    /**
     * 
     * 描述 生成申请书
     * 
     * @author Danto Huang
     * @created 2017年12月6日 上午9:38:46
     * @param exe
     */
    private void genApply(Map<String, Object> busRecord) {
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        String fileRullPath = attachFileFolderPath + "webpage/bsdt/applyform/banktemplate/";
        // 定义相对目录路径
        String uploadFullPath = "attachFiles/bankDownload/" + busRecord.get("COMPANY_NAME");

        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(attachFileFolderPath + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String fileName = "";

        String bankType = busRecord.get("BANK_TYPE").toString();
        if (bankType.equals("ccb")) {
            fileRullPath = fileRullPath + "ccb.docx";
            fileName = "单位银行结算账户申请书.docx";
        } else if (bankType.equals("pdb")) {
            fileRullPath = fileRullPath + "pdb.docx";
            fileName = "开立单位银行结算账户申请书.docx";
        }
        String newWordFilePath = uploadFullPath + "/" + fileName;
        bankGenService.genBankApply(busRecord, fileRullPath, attachFileFolderPath + newWordFilePath);
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月6日 上午9:51:44
     */
    private void genImageMater(String exeId,Map<String, Object> busRecord){
        Map<String,Object> imgInfo = dao.getByJdbc("T_COMMERCIAL_BANKFILE", new String[] { "EXE_ID" },
                new Object[] { exeId });
        
        FileAttachService fileAttachService = (FileAttachService) AppUtil.getBean("fileAttachService");
        
        Map<String,Object> license = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{imgInfo.get("LICENSE_PATH")});
        Map<String,Object> card = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{imgInfo.get("CARD_PATH")});
        Map<String,Object> apply = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{imgInfo.get("APPLY_PATH")});
        
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath");
        //执照
        String fileName = (String) license.get("FILE_NAME");
        String fileRullPath = attachFileFolderPath+license.get("FILE_PATH");
        String iconPath = attachFileFolderPath + "webpage/bsdt/applyform/banktemplate/pdfmark.png";
        String targetPath = attachFileFolderPath + "attachFiles/bankDownload/" + busRecord.get("COMPANY_NAME")+"/";
        File targetPathFolder = new File(targetPath);
        if (!targetPathFolder.exists()) {
            targetPathFolder.mkdirs();
        }else{
            FileUtil.removeAllFile(targetPath);
        }
        String filetargetPath = targetPath + fileName;
        
        ImageRemarkUtil.setImageMarkOptions(1f, 1, 1, null, null);
        ImageRemarkUtil.markImageByIcon(iconPath, fileRullPath, filetargetPath);
        //法人身份证
        fileName = (String) card.get("FILE_NAME");
        fileRullPath = attachFileFolderPath+card.get("FILE_PATH");
        filetargetPath = targetPath + fileName;
         
        ImageRemarkUtil.setImageMarkOptions(1f, 1, 1, null, null);
        ImageRemarkUtil.markImageByIcon(iconPath, fileRullPath, filetargetPath);
        //开户申请书
        String isImg = apply.get("IS_IMG").toString();
        fileName = (String) apply.get("FILE_NAME");
        fileRullPath = attachFileFolderPath+apply.get("FILE_PATH");
        filetargetPath = targetPath + fileName;
        
        if(isImg.equals("1")){
            ImageRemarkUtil.setImageMarkOptions(1f, 1, 1, null, null);
            ImageRemarkUtil.markImageByIcon(iconPath, fileRullPath, filetargetPath);
        }else{
            //FileUtil.copyFile(new File(fileRullPath), filetargetPath);
            PdfRemarkUtil.markPdfByIcon(iconPath, fileRullPath, filetargetPath);
        }
    }
}
