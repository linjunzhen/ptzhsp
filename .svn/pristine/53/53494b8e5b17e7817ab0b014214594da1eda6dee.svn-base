/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 描述 Zip压缩工具类
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年12月29日 上午11:28:59
 */
public class ZipCompressUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ZipCompressUtil.class);
    /**
     * 
     * 描述 压缩目标文件夹
     * 
     * @author Flex Hu
     * @created 2015年1月19日 上午10:50:17
     * @param sourceDir
     *            :源文件夹
     * @param zipFile
     *            :目标zip文件
     */
    public static void zip(String sourceDir, String zipFile) {
        OutputStream os;
        try {
            os = new FileOutputStream(zipFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ZipOutputStream zos = new ZipOutputStream(bos);
            File file = new File(sourceDir);
            String basePath = null;
            if (file.isDirectory()) {
                basePath = file.getPath();
            } else {// 直接压缩单个文件时，取父目录
                basePath = file.getParent();

            }
            zipFile(file, basePath, zos);
            zos.closeEntry();
            zos.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 功能：执行文件压缩成zip文件
     * 
     * @param source
     * @param basePath
     *            待压缩文件根目录
     * @param zos
     */

    private static void zipFile(File source, String basePath, ZipOutputStream zos) {
        File[] files;
        if (source.isDirectory()) {
            files = source.listFiles();
        } else {
            files = new File[1];
            files[0] = source;
        }
        String pathName;// 存相对路径(相对于待压缩的根目录)
        byte[] buf = new byte[1024];
        int length = 0;
        BufferedInputStream bis = null;
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    pathName = file.getPath().substring(basePath.length() + 1) + "/";
                    zos.putNextEntry(new ZipEntry(pathName));
                    zipFile(file, basePath, zos);
                } else {
                    pathName = file.getPath().substring(basePath.length() + 1);
                    InputStream is = new FileInputStream(file);
                    bis = new BufferedInputStream(is);
                    zos.putNextEntry(new ZipEntry(pathName));
                    while ((length = bis.read(buf)) > 0) {
                        zos.write(buf, 0, length);
                    }
                    is.close();
                    bis.close();
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (null!=bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 
     * 描述 解压文件
     * 
     * @author Flex Hu
     * @created 2015年1月19日 上午10:54:28
     * @param zipfile
     * @param destDir
     */
    public static void unZip(String zipfile, String destDir) {
        destDir = destDir.endsWith("\\") ? destDir : destDir + "\\";
        byte b[] = new byte[1024];
        int length;
        ZipFile zipFile;
        OutputStream outputStream =null;
        try {
            zipFile = new ZipFile(new File(zipfile));
            Enumeration enumeration = zipFile.getEntries();
            ZipEntry zipEntry = null;
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                File loadFile = new File(destDir + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    loadFile.mkdirs();
                } else {
                    if (!loadFile.getParentFile().exists()) {
                        loadFile.getParentFile().mkdirs();
                    }
                    outputStream = new FileOutputStream(loadFile);
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    while ((length = inputStream.read(b)) > 0)
                        outputStream.write(b, 0, length);
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }

    }

    /**
     * 
     * 描述 压缩文件
     * 
     * @author Faker Li
     * @created 2015-1-21 上午10:27:39
     * @param inputFile
     *            源文件
     * @param zipFileName
     *            压缩文件
     */
    public static void zip(File inputFile, String zipFileName) {
        try {
            FileOutputStream out = new FileOutputStream(new String(zipFileName.getBytes("UTF-8")));
            ZipOutputStream zOut = new ZipOutputStream(out);
            zip(zOut, inputFile, "");
            zOut.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public static void zip(ZipOutputStream zOut, File file, String base) {
        try {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                zOut.putNextEntry(new ZipEntry(base + "/"));
                base = (base.length() == 0 ? "" : base + "/");
                for (int i = 0; i < listFiles.length; i++) {
                    zip(zOut, listFiles[i], base + listFiles[i].getName());
                }
            }
            else {
                if (base.equals("")) {
                    base = file.getName();
                }
                zOut.putNextEntry(new ZipEntry(base));
                writeFile(zOut, file);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    /**
     * 
     * 描述 开始压缩文件
     * 
     * @author Faker Li
     * @created 2015-1-21 上午10:28:36
     * @param zOut
     * @param file
     * @throws IOException
     */
    public static void writeFile(ZipOutputStream zOut, File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            int len;
            while ((len = in.read()) != -1)
                zOut.write(len);
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }
        finally{
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
}
