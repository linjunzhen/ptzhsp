/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 描述 ftp文件下载类
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年04月14日 下午15:37:28
 */
public class FtpUtils {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FtpUtils.class);

    /**
     * 描述:初始化连接ftp
     *
     * @author Madison You
     * @created 2020/4/15 8:42:00
     * @param ftpHost ip地址
     * @param ftpPort 端口号 一般为21
     * @param ftpUserName ftp连接用户名
     * @param ftpPassword ftp连接密码
     * @return
     */
    public static FTPClient initFtpClient(String ftpHost , int ftpPort , String ftpUserName , String ftpPassword) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);  // 连接
            ftpClient.login(ftpUserName, ftpPassword);  //登录
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  //判断是否连接  220为连接正常 ， 530 失败
                log.error("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            }
        } catch (SocketException e) {
            log.error("FTP连接失败,FTP的IP地址可能错误，请正确配置");
        } catch (IOException e) {
            log.error("FTP连接失败,FTP的端口错误,请正确配置");
        }

        return ftpClient;
    }

    /**
     * 描述:下载指定ftp文件
     *
     * @author Madison You
     * @created 2020/4/15 8:55:00
     * @param ftpHost ip地址
     * @param ftpPort 端口号 一般为21
     * @param ftpUserName ftp连接用户名
     * @param ftpPassword ftp连接密码
     * @param ftpPath ftp文件路径（不包含文件名称） 例：shop/food
     * @param localPath ftp文件下载到本地得位置 例：D:/workNote
     * @param fileName 文件名称  例：a.txt
     * @return 返回下载是否成功
     */
    public static boolean downloadSingleFtpFile(String ftpHost , int ftpPort , String ftpUserName ,
                                                String ftpPassword , String ftpPath , String localPath ,
                                                String fileName) {
        FTPClient ftpClient = null;
        FileOutputStream os = null;
        boolean flag = false;
        try {
            ftpClient = initFtpClient(ftpHost, ftpPort, ftpUserName, ftpPassword); //连接
            ftpClient.setControlEncoding("UTF-8");  // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            File localFile = new File(localPath + File.separatorChar + fileName);
            os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(fileName, os);
            ftpClient.logout();
            flag = true;
        } catch (FileNotFoundException e) {
            log.error("没有找到" + ftpPath + "文件");
        } catch (SocketException e) {
            log.error("连接ftp失败");
        } catch (IOException e) {
            log.error("io读取错误");
        } finally {
            if (os != null) {
                try{
                    os.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (ftpClient != null) {
                try{
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.info(e.getMessage(), e);
                }
            }
        }
        return flag;
    }

    /**
     * 描述:下载目录列表下所有ftp文件
     *
     * @author Madison You
     * @created 2020/4/15 11:38:00
     * @param ftpHost ip地址
     * @param ftpPort 端口号 一般为21
     * @param ftpUserName ftp连接用户名
     * @param ftpPassword ftp连接密码
     * @param ftpPath ftp文件路径（不包含文件名称） 例：shop/food
     * @param localPath ftp文件下载到本地得位置 例：D:/workNote
     * @param downloadSize 一次下载ftp目录下文件的文件个数 0:代表无限制
     * @param matching 匹配文件名 例：.zip , .xls
     *      * @return 返回下载的文件名称集合
     */
    public static List<String> downloadListFtpFile(String ftpHost , int ftpPort ,
                                                   String ftpUserName , String ftpPassword , String ftpPath ,
                                                   String localPath , int downloadSize , String matching) {
        ArrayList<String> fileList = new ArrayList<>();
        FTPClient ftpClient = null;
        FileOutputStream os = null;
        try {
            ftpClient = initFtpClient(ftpHost, ftpPort, ftpUserName, ftpPassword); //连接
            ftpClient.setControlEncoding("UTF-8");  // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            /*获取FTP指定目录下文件列表*/
            FTPFile[] ftpFileList = ftpClient.listFiles();
            int downloadSum = 0;
            for (int i = 0; i < ftpFileList.length; i++) {
                String ftpFileName = ftpFileList[i].getName();
                if (ftpFileName.contains(matching)) {
                    File localFile = new File(localPath + File.separatorChar + ftpFileName);
                    OutputStream ous = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ftpFileName, ous);
                    fileList.add(ftpFileName);
                    if (++downloadSum >= downloadSize) {
                        break;
                    }
                    ous.close();
                }
            }
            ftpClient.logout();
        } catch (FileNotFoundException e) {
            log.error("没有找到" + ftpPath + "文件");
        } catch (SocketException e) {
            log.error("连接ftp失败");
        } catch (IOException e) {
            log.error("io读取错误");
        } finally {
            if (os != null) {
                try{
                    os.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (ftpClient != null) {
                try{
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.info(e.getMessage(), e);
                }
            }
        }
        return fileList;
    }

    /**
     * 描述:删除单个ftp文件
     *
     * @author Madison You
     * @created 2020/4/15 10:41:00
     * @param ftpHost ip地址
     * @param ftpPort 端口号 一般为21
     * @param ftpUserName ftp连接用户名
     * @param ftpPassword ftp连接密码
     * @param ftpPath ftp文件路径（不包含文件名称） 例：shop/food
     * @param fileName 文件名称
     * @return
     */
    public static boolean deleteSingleFtpFile(String ftpHost , int ftpPort , String ftpUserName ,
                                              String ftpPassword , String ftpPath , String fileName) {
        FTPClient ftpClient = null;
        boolean flag = false;
        try{
            ftpClient = initFtpClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
            ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.dele(fileName);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            log.error("删除文件失败");
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * 描述:批量删除ftp文件
     *
     * @author Madison You
     * @created 2020/4/15 10:41:00
     * @param ftpHost ip地址
     * @param ftpPort 端口号 一般为21
     * @param ftpUserName ftp连接用户名
     * @param ftpPassword ftp连接密码
     * @param ftpPath ftp文件路径（不包含文件名称） 例：shop/food
     * @param fileNameList 文件名称集合
     * @return
     */
    public static boolean deleteListFtpFile(String ftpHost , int ftpPort , String ftpUserName , String ftpPassword ,
                                        String ftpPath , List<String> fileNameList) {
        FTPClient ftpClient = null;
        boolean flag = false;
        try{
            ftpClient = initFtpClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
            ftpClient.changeWorkingDirectory(ftpPath);
            for (String fileName : fileNameList) {
                ftpClient.dele(fileName);
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            log.error("删除文件失败");
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * 描述:批量移动ftp文件
     *
     * @author Madison You
     * @created 2020/4/15 17:22:00
     * @param ftpHost ip地址
     * @param ftpPort 端口号 一般为21
     * @param ftpUserName ftp连接用户名
     * @param ftpPassword ftp连接密码
     * @param ftpPath ftp文件位置
     * @param ftpRenamePath 目标文件位置
     * @param fileNameList 文件名称集合
     * @return
     */
    public static boolean renameListFtpFile(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword,
                                            String ftpPath , String ftpRenamePath, List<String> fileNameList) {
        FTPClient ftpClient = null;
        boolean flag = false;
        try{
            ftpClient = initFtpClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
            ftpClient.changeWorkingDirectory(ftpPath);
            for (String fileName : fileNameList) {
                ftpClient.rename(fileName, ftpRenamePath + File.separatorChar + fileName);
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            log.error("删除文件失败");
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * ftp上传文件
     *
     * @param f
     * @throws Exception
     */
    public static boolean uploadFtpFile(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword,
            String ftpPath, File f) {
        boolean success = false;   
        FTPClient ftpClient = null;
        FileInputStream input = null;
        try {
            ftpClient = initFtpClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
            ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.sendCommand("OPTS UTF8", "ON");
            // 设置上传文件的类型为二进制类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            if (f.isDirectory()) {
                ftpClient.makeDirectory(f.getName());
                ftpClient.changeWorkingDirectory(f.getName());
                String[] files = f.list();
                for (String fstr : files) {
                    File file1 = new File(f.getPath() + "/" + fstr);
                    if (file1.isDirectory()) {
                        uploadFtpFile(ftpHost, ftpPort, ftpUserName, ftpPassword, ftpPath, file1);
                        ftpClient.changeToParentDirectory();
                    } else {
                        File file2 = new File(f.getPath() + "/" + fstr);
                        input = new FileInputStream(file2);
                        ftpClient.storeFile(new String(file2.getName().getBytes("UTF-8"), "iso-8859-1"), input);
                        input.close();
                    }
                }
            } else {
                File file2 = new File(f.getPath());
                input = new FileInputStream(file2);
                ftpClient.storeFile(new String(file2.getName().getBytes("UTF-8"), "iso-8859-1"), input);
                input.close();
            }   
            success = true;  
        } catch (Exception e) {
            log.error("上传文件到FTP失败", e);
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return success ;  
    }
   /* public static void main(String[] args) {
        File file = new File("D:/审核意见(样例数据)-预审及办结需要回传到FTP的文件格式.xls");获取不动产FTP配置文件
        Properties properties = FileUtil.readProperties("project.properties");
        String bdcFtpHost = properties.getProperty("BDC_FTP_HOST");
        String bdcFtpPort = properties.getProperty("BDC_FTP_PORT"); 
        int bdcFtpPortParse = Integer.parseInt(bdcFtpPort);
        String bdcFtpUserName = properties.getProperty("BDC_FTP_USERNAME");
        String bdcFtpPassword = properties.getProperty("BDC_FTP_PASSWORD");
        String bdcFtpFtppath = properties.getProperty("BDC_FTP_FTPPATH");
        FtpUtils.uploadFtpFile(bdcFtpHost, bdcFtpPortParse, bdcFtpUserName, bdcFtpPassword, bdcFtpFtppath, file);//把文件上传在ftp上
        System.out.println("上传文件完成。。。。");
    }*/
}
