/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.*;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件压缩
 * @author Toddle Chen
 * @created Mar 17, 2015 4:13:15 PM
 */
public class ZipUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ZipUtil.class);
    /**
     * 压缩文件file成zip文件zipFile
     * 
     * @param file 要压缩的文件
     * @param zipFile 压缩文件存放地方
     * @throws Exception
     */
    public static void zip(File file, File zipFile) throws Exception {
        ZipOutputStream output = null;
        try {
            output = new ZipOutputStream(new FileOutputStream(zipFile));
            // 顶层目录开始
            zipFile(output, file, "");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            // 关闭流
            if (output != null) {
                output.flush();
                output.close();
            }
        }
    }

    /**
     * 压缩文件为zip格式
     * 
     * @param output ZipOutputStream对象
     * @param file 要压缩的文件或文件夹
     * @param basePath 条目根目录
     * @throws IOException
     */
    private static void zipFile(ZipOutputStream output, File file,String basePath) throws IOException {
        FileInputStream input = null;
        try {
            // 文件为目录
            if (file.isDirectory()) {
                // 得到当前目录里面的文件列表
                File list[] = file.listFiles();
                basePath = basePath + (basePath.length() == 0 ? "" : "/")+ file.getName();
                // 循环递归压缩每个文件
                for (File f : list)
                    zipFile(output, f, basePath);
            } else {
                // 压缩文件
                basePath = (basePath.length() == 0 ? "" : basePath + "/")+ file.getName();
                // log.info(basePath);
                output.putNextEntry(new ZipEntry(basePath));
                input = new FileInputStream(file);
                int readLen = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
                    output.write(buffer, 0, readLen);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            // 关闭流
            if (input != null)
                input.close();
        }
    }

    /**
     * 解压zip文件
     * 
     * @param zipFilePath zip文件绝对路径
     * @param unzipDirectory 解压到的目录
     * @throws Exception
     */
    public static void unzip(String zipFilePath, String unzipDirectory) {
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        try {
            // 创建文件对象
            File file = new File(zipFilePath);
            // 创建zip文件对象
            ZipFile zipFile = new ZipFile(file);
            // 创建本zip文件解压目录
            String name = file.getName().substring(0,file.getName().lastIndexOf("."));
            File unzipFile = new File(unzipDirectory + "/" + name);
            if (unzipFile.exists())
                unzipFile.delete();
            unzipFile.mkdir();
            // 得到zip文件条目枚举对象
            Enumeration zipEnum = zipFile.getEntries();
            // 定义对象
            ZipEntry entry = null;
            String entryName = null, path = null;
            String names[] = null;
            int length;
            // 循环读取条目
            while (zipEnum.hasMoreElements()) {
                // 得到当前条目
                entry = (ZipEntry) zipEnum.nextElement();
                entryName = new String(entry.getName());
                // 用/分隔条目名称
                names = entryName.split("\\/");
                length = names.length;
                path = unzipFile.getAbsolutePath();
                for (int v = 0; v < length; v++) {
                    if (v < length - 1) // 最后一个目录之前的目录
                        FileUtil.createDirectory(path += "/" + names[v] + "/"); 
                    else { // 最后一个 
                        if (entryName.endsWith("/")) // 为目录,则创建文件夹
                            FileUtil.createDirectory(unzipFile.getAbsolutePath() + "/" + entryName);
                        else { // 为文件,则输出到文件
                            input = zipFile.getInputStream(entry);
                            output = new FileOutputStream(new File(unzipFile.getAbsolutePath() + "/" + entryName));
                            byte[] buffer = new byte[1024 * 8];
                            int readLen = 0;
                            while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
                                output.write(buffer, 0, readLen);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            // 关闭流
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            if (output != null) {
                try {
                    output.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
                try {
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }

    }

    /**
     * 描述:解压文件
     *
     * @author Madison You
     * @created 2020/4/16 16:06:00
     * @param
     * @return
     */
    public static void unzipNew(String zipFile, String dest){
        File xfile = new File(zipFile);
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile);
            // 创建本zip文件解压目录
            String name = xfile.getName().substring(0, xfile.getName().lastIndexOf("."));
            File unzipFile = new File(dest + "/" + name);
            if (unzipFile.exists())
                unzipFile.delete();
            unzipFile.mkdir();
            Enumeration<ZipEntry> en = zip.getEntries();
            ZipEntry entry = null;
            byte[] buffer = new byte[1024];
            int length = -1;
            InputStream input = null;
            BufferedOutputStream bos = null;
            File file = null;

            while (en.hasMoreElements()) {
                entry = (ZipEntry) en.nextElement();
                if (entry.isDirectory()) {
                    file = new File(unzipFile, entry.getName());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    continue;
                }
                try {
                    input = zip.getInputStream(entry);
                    file = new File(unzipFile, entry.getName());
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(file));

                    while (true) {
                        length = input.read(buffer);
                        if (length == -1)
                            break;
                        bos.write(buffer, 0, length);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                zip.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

    }

    /**
     * 测试
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {

    }
}