/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import net.evecom.core.model.FileFolder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.agile.zip.CnZipInputStream;
import com.agile.zip.ZipEntry;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:27:26
 */
public class FileUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FileUtil.class);
    /**
     * 允许的图片类型
     */
    public static final Set<String> IMAGESTYPES = new HashSet<String>();

    /**
     * 复制文件
     * 
     * @param fromPath
     *            原文件路径
     * @param toPath
     *            新文件路径
     * @return
     */
    public static boolean copyFolder(String fromPath, String toPath) {
        InputStream inStream = null; 
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File myFilePath = new File(toPath);
            File oldfile = new File(fromPath);

            if (oldfile.isFile()) {
                if (!myFilePath.exists()) {
                    myFilePath.createNewFile();
                }
                if (oldfile.exists()) { 
                    inStream = new FileInputStream(fromPath); 
                    fs = new FileOutputStream(toPath);
                    byte[] buffer = new byte[1444];
                    int length;
                    while ((byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; // 字节数 文件大小
                        fs.write(buffer, 0, byteread);
                    }
                    inStream.close();
                    fs.close();
                }
            } else {
                if (myFilePath.exists()) {
                    deleteFile(myFilePath);
                }
                if (!myFilePath.exists()) {
                    (new File(toPath)).mkdirs();
                }

            }
        } catch (Exception e) {
            log.info("复制单个文件操作出错");
            log.error(e.getMessage());
            return false;
        }finally{
            if (fs!=null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (inStream!=null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return true;

    }

    /**
     * 生成UUID的文件名称,带扩展名
     * 
     * @param originalFilename
     * @return
     */
    public static String generateFilename(String originalFilename) {
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String filePre = "DATE_" + dirSdf.format(new Date());
        String fileExt = "";
        int lastIndex = originalFilename.lastIndexOf('.');
        // 取得文件的扩展名
        if (lastIndex != -1) {
            fileExt = originalFilename.substring(lastIndex);
        }
        String filename = filePre + "/" + UUIDGenerator.getUUID() + fileExt;
        return filename;
    }

    /**
     * 
     * 描述 读取文件的总行数
     * 
     * @author Flex Hu
     * @created 2014年9月21日 下午7:38:27
     * @param file
     * @return
     */
    public static int getTotalLines(File file) {
        int lines=0;
        BufferedReader in = null;
        LineNumberReader reader = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            reader = new LineNumberReader(in);
            String s = reader.readLine();
            lines = 0;
            while (s != null) {
                lines++;
                s = reader.readLine();
            }
            reader.close();
            in.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return lines;
    }

    /**
     * 
     * 描述 读取文件关键点
     * 
     * @author Flex Hu
     * @created 2014年9月21日 下午7:40:24
     * @param sourceFile
     * @param lineNumber
     * @return
     */
    public static String readAppointedLineNumber(File sourceFile, int lineNumber) {
        BufferedReader in = null;
        LineNumberReader reader = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF-8"));
            reader = new LineNumberReader(in);
            String s = "";
            if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {
                log.info("不在文件的行数范围(1至总行数)之内。");
                System.exit(0);
            }
            int lines = 0;
            while (s != null) {
                lines++;
                s = reader.readLine();
                if ((lines - lineNumber) == 0) {
                    reader.close();
                    in.close();
                    return s;
                }
            }
            reader.close();
            in.close();
            return s;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 
     * 描述 添加方法到JAVA源代码
     * 
     * @author Flex Hu
     * @created 2014年9月21日 下午7:39:30
     * @param file
     * @param method
     * @return
     */
    public static String addMethodToJava(File file, String method) {
        // TODO Auto-generated method stub
        int startLineNum = 1; // 定义起始行位置
        int addLine = 0; // 添加的行
        for (int i = startLineNum; i <= getTotalLines(file); i++) {
            String lineContent = readAppointedLineNumber(file, i);
            if (lineContent.contains("}")) {
                addLine = i;
            }
        }
        // >>开始添加<<
        StringBuffer newContent = new StringBuffer("");
        for (int i = startLineNum; i <= getTotalLines(file); i++) {
            if (addLine != 0 && i == addLine) {
                newContent.append(method);
                String lineContent = readAppointedLineNumber(file, i);
                newContent.append("\n");
                newContent.append(lineContent).append("\n");
            } else {
                String lineContent = readAppointedLineNumber(file, i);
                newContent.append(lineContent).append("\n");
            }
        }
        return newContent.toString();
    }

    /**
     * 获取项目的根目录
     * 
     * @return
     */
    public static String getPrjRoot() {
        URL u = FileUtil.class.getResource(FileUtil.class.getSimpleName() + ".class");
        String str = u.getPath();
        if (str.indexOf(":") != -1)
            str = str.substring(1, str.lastIndexOf("WEB-INF"));// 在windows下面为/F:/MyPrj/WORK/post/
        else
            str = str.substring(0, str.lastIndexOf("WEB-INF"));// 在Linux下面为/usr/local/apache.../
        try {
            str = URLDecoder.decode(str, "GBK");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return str;
    }
    /**
     * 获取指定资源文件下的参数值
     * 
     * @param fileName
     *            全路径+属性文件名
     * @param key
     * @return
     */
    public static String getProperties(String fileName, String key) {
        InputStream input = null;
        File baseFile = new File(fileName);
        try {
            Properties baseProps = new Properties();
            input = new BufferedInputStream(new FileInputStream(baseFile));
            baseProps.load(input);
            String files = baseProps.getProperty(key);
            return files;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 获取文件名称，不包括扩展名
     * 
     * @param oldFileName
     * @return
     */
    public static String getFileName(String oldFileName) {
        return oldFileName.substring(0, oldFileName.indexOf("."));
    }

    /**
     * 获取文件的扩展名
     * 
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(i + 1).toLowerCase();
        }
        return null;
    }

    /**
     * 保存文件到磁盘
     * 
     * @param savePath
     * @param fileName
     * @param file
     * @return
     */
    public static String writeFile(String savePath, String fileName, File file) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(savePath + "/" + fileName);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return fileName;
    }

    /**
     * 格式化文件的大小值
     * 
     * @param fileLength
     * @return
     */
    public static String getFormatFileSize(long fileLength) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLength < 1024) {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576) {
            fileSizeString = df.format((double) fileLength / 1024) + "K";
        } else if (fileLength < 1073741824) {
            fileSizeString = df.format((double) fileLength / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLength / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 复制文件夹或文件
     * 
     * @param srcFile
     *            源文件
     * @param tarFilepath
     *            复制路径
     */
    public void copyFiles(File srcFile, String tarFilepath) {
        InputStream in = null; // 输入流
        OutputStream out = null;// 输出流
        File tarFile = new File(tarFilepath);
        if (srcFile.isDirectory()) {
            if (!tarFile.exists()) {
                tarFile.mkdirs();
            }
            File[] files = srcFile.listFiles();
            for (File file : files) {
                copyFiles(file, tarFilepath + File.separator + file.getName());
            }
        } else if (srcFile.isFile()) {
            try {
                in = new FileInputStream(srcFile);
                out = new FileOutputStream(tarFile);
                byte[] mybyte = new byte[1024];
                int length = 0;
                while ((length = in.read(mybyte)) != -1) {
                    out.write(mybyte, 0, length);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }finally{
                if (out!=null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        log.error(e.getMessage());
                    }
                }
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

    /**
     * 递归获取某个文件夹下面的所有文件
     * 
     * @author Flex Hu
     * @param srcFile
     * @return
     */
    public static List<File> findFiles(File srcFile, List<File> files) {
        if (files == null) {
            files = new ArrayList<File>();
        }
        if (srcFile.isFile()) {
            files.add(srcFile);
        } else {
            File[] f = srcFile.listFiles();
            for (File file : f) {
                findFiles(file, files);
            }
        }
        return files;
    }

    /**
     * 根据传入的文件格式类型获取到文件列表
     * 
     * @param fileFormats
     *            ：文件格式
     * @param srcFile
     *            :目录
     * @return
     */
    public static List<File> getFormatFiles(Set<String> fileFormats, File srcFile, List<File> files) {
        if (files == null) {
            files = new ArrayList<File>();
        }
        if (srcFile.isFile()) {
            String fileName = srcFile.getName();
            String format = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileFormats.contains(format)) {
                files.add(srcFile);
            }
        } else {
            File[] f = srcFile.listFiles();
            for (File file : f) {
                getFormatFiles(fileFormats, file, files);
            }
        }
        return files;
    }

    /**
     * 新建文件夹
     * 
     * @param dir
     */
    public static void createDirectory(String dir) {
        File filePath = new File(dir);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
    }
    /**
     * 创建文件,如果已存在先删除,再创建
     * 
     * @param filePath
     * @return
     */
    public static File createFile(String filePath) {
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File fileFolder = new File(folderPath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        File file = new File(filePath);
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            log.info("创建文件失败!");
        }
        return file;
    }

    /**
     * 根据文件路径读取磁盘上文件里的内容
     * 
     * @param filePath
     * @return
     */
    public static String getContentOfFile(String filePath) {
        try {
            // return FileUtils.readFileToString(new File(filePath));
            return FileUtils.readFileToString(new File(filePath), "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return "";
    }

    /**
     * 创建文件目录
     * 
     * @author Flex Hu
     * @param fileName
     *            :文件名称:例如 attacheFiles/abc.jpg
     */
    public static void createFolder(String serverPath, String fileName) {
        // 如果包含了目录,那么先进行目录的创建
        if (fileName.contains("/")) {
            String folderPath = fileName.substring(0, fileName.lastIndexOf("/"));
            File fileFolder = new File(serverPath + folderPath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
        }
    }

    /**
     * 将图片文件转换成字符串
     * 
     * @author Flex Hu
     * @param filePath
     * @return
     */
    public static String imgToString(String filePath) {
        String type = FileUtil.getFileExtension(filePath);
        String res = null;
        try {
            int HttpResult = 0; // 服务器返回的状态
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            BufferedImage bm = ImageIO.read(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bm, type, bos);
            bos.flush();
            byte[] data = bos.toByteArray();
            res = byte2hex(data);
            bos.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * @title 根据二进制字符串生成图片
     * @param data
     *            生成图片的二进制字符串
     * @param fileName
     *            图片名称(完整路径)
     * @return
     */
    public static void stringToImage(String data, String fileName) {
        String folderPath = fileName.substring(0, fileName.lastIndexOf("/"));
        File filefolder = new File(folderPath);
        if (!filefolder.exists()) {
            filefolder.mkdirs();
        }
        String type = FileUtil.getFileExtension(fileName);
        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_BYTE_BINARY);
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, byteOutputStream);
            byte[] bytes = hex2byte(data);
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            file.write(bytes);
            file.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 反格式化byte
     * 
     * @param s
     * @return
     */
    public static byte[] hex2byte(String s) {
        byte[] src = s.toLowerCase().getBytes();
        byte[] ret = new byte[src.length / 2];
        for (int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a') : hi - '0');
            low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a') : low - '0');
            ret[i / 2] = (byte) (hi << 4 | low);
        }
        return ret;
    }

    /**
     * 二进制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }

        }
        return sb.toString();
    }

    /**
     * 将文本文件转换成字符流
     * 
     * @author Flex Hu
     * @param fileName
     * @return
     */
    public static String txtToString(String file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file));
            } else {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            }
            // 将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
        }
        // 返回转换结果
        if (writer != null)
            return writer.toString();
        else
            return null;
    }

    /**
     * 将字符流输出磁盘变成文本文件
     * 
     * @author Flex Hu
     * @param fileString
     * @param filePath
     */
    private static boolean stringToTxt(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * 读取属性文件
     * 
     * @author Flex Hu
     * @param filename
     * @return
     */
    public static Properties readProperties(String filename) {
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(filename);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return p;
    }

    /**
     * 根据父亲路径获取到孩子目录列表
     * 
     * @author Flex Hu
     * @param parentPath
     * @return
     */
    public static List<FileFolder> getChildFolders(String parentPath, String contextPath) {
        List<FileFolder> list = new ArrayList<FileFolder>();
        File parentDir = new File(parentPath);
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String absolutePath = file.getAbsolutePath();
                    String filePath = absolutePath.substring(contextPath.length() + 1, absolutePath.length());
                    FileFolder folder = new FileFolder(file.getName(), filePath.replace("\\", "/"));
                    list.add(folder);
                }
            }
        }
        return list;
    }

    /**
     * 将文本写入到磁盘上,无需考虑是否存在目录,直接把路径传过来
     * 
     * @author Flex Hu
     * @param filePath
     *            :文件的路径
     * @param content
     *            :文件的内容
     */
    public static void writeTextToDisk(String filePath, String content) {
        FileOutputStream fos = null;
        // 生成html文件
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fos = new FileOutputStream(new File(filePath));
            fos.write(content.getBytes("utf-8")); // 设置生成文件编码格式
            fos.flush();
            fos.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 将文本写入到磁盘上,无需考虑是否存在目录,直接把路径传过来
     * 
     * @author Flex Hu
     * @param filePath
     *            :文件的路径
     * @param content
     *            :文件的内容
     */
    public static void writeTextToDisk(String filePath, String content, String encoding) {
        FileOutputStream fos = null;
        // 生成html文件
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fos = new FileOutputStream(new File(filePath));
            fos.write(content.getBytes(encoding)); // 设置生成文件编码格式
            fos.flush();
            fos.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 将文本内容写入到文件
     * 
     * @param file
     * @param content
     */
    public static void writeTextToFile(File file, String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes("utf-8")); // 设置生成文件编码格式
            fos.flush();
            fos.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 删除掉指定文件夹下的所有文件
     * 
     * @author Flex Hu
     * @param path
     *            :文件夹完整绝对路径
     * @return
     */
    public static boolean removeAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                removeAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                removeFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     * 
     * @author Flex Hu
     * @param folderPath
     *            :绝对路径
     */
    public static void removeFolder(String folderPath) {
        try {
            removeAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 把文件转换成字节数组
     * 
     * @author Flex Hu
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] convertFileToBytes(File file) {
        byte[] bytes = null;
        InputStream is = null;
        try {
            if (file != null) {
                is = new FileInputStream(file);
                int length = (int) file.length();
                if (length > Integer.MAX_VALUE) // 当文件的长度超过了int的最大值
                {
                    log.info("this file is max ");
                    return null;
                }
                bytes = new byte[length];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }
                // 如果得到的字节长度和file实际的长度不一致就可能出错了
                if (offset < bytes.length) {
                    log.info("file length is error");
                    return null;
                }
                is.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage(),e);
        }finally{
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
        }
        return bytes;
    }


    /**
     * 
     * 描述 把流转换成字节数组
     * @author Rider Chen
     * @created 2021年3月16日 下午3:16:55
     * @param is
     * @return
     */
    public static byte[] convertUrlFileToBytes(InputStream is) {
        ByteArrayOutputStream byteOut = null;
        try {
            byteOut = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int n = 0;
            while (-1 != (n = is.read(bytes))) {
                byteOut.write(bytes, 0, n);
            }
        } catch (Exception e) {
            log.info("文件转为字节数组出错" + e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            try {
                if (byteOut != null) {
                    byteOut.close();
                }
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        if (byteOut != null) {
            return byteOut.toByteArray();
        } else {
            return null;
        }

    }

    /**
     * 描述：
     *
     * @author Madison You
     * @created 2020/1/14 14:41:00
     * @param
     * @return
     */
    public static byte[] convertUrlFileToBytes(String filePath) {
        ByteArrayOutputStream byteOut = null;
        if (filePath != null && !filePath.equals("")) {
            InputStream in = null;
            try {
                URL url = new URL(filePath);
                in = url.openStream();
                byteOut = new ByteArrayOutputStream();
                byte[] bytes = new byte[4096];
                int n = 0;
                while (-1 != (n = in.read(bytes))) {
                    byteOut.write(bytes, 0, n);
                }
            } catch (Exception e) {
                log.info("文件转为字节数组出错" + e.getMessage());
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
                try {
                    if (byteOut != null) {
                        byteOut.close();
                    }
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        } else {
            return null;
        }
        if (byteOut != null) {
            return byteOut.toByteArray();
        } else {
            return null;
        }

    }

    /**
     * 根据文件路径获取到文件名称:包括扩展名
     * 
     * @author Flex Hu
     * @param filePath
     * @return
     */
    public static String getFileNameContainExt(String filePath) {
        if (filePath.contains("\\")) {
            return filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
        } else {
            return filePath;
        }
    }

    /**
     * 把字节数组转换成文件
     * 
     * @author Flex Hu
     * @param bytes
     *            :字节数组
     * @param filePath
     *            :文件路径
     */
    public static void bytesToFile(byte[] bytes, String filePath) {
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File newFile = new File(filePath);
        OutputStream os = null;
        try {
            os = new FileOutputStream(newFile);
            os.write(bytes); // 把流一次性写入一个文件里面
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }finally{
            if (os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 拷贝文件
     * 
     * @author Flex Hu
     * @param sourceFile
     *            :源文件 :目标文件地址
     * @throws IOException
     */
    public static void copyFile(File sourceFile, String filePath) {
        // log.info("filePath"+filePath);
        String folderPath = "";
        if(filePath.indexOf("/")!=-1){
            folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        }else{
            folderPath = filePath.substring(0, filePath.lastIndexOf("\\"));
        }
        File file = new File(folderPath);
        // log.info("地址:"+folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File targetFile = new File(filePath);
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }finally {
            // 关闭流
            if (inBuff != null)
                try {
                    inBuff.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            if (outBuff != null)
                try {
                    outBuff.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
        }
    }

    /**
     * 删除文件夹
     * 
     * @author Faker Li
     * @created 2015年2月3日 下午3:12:49
     * @param file
     */
    private static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
        } else {
            log.info("所删除的文件不存在");
        }
    }
    /**
     * 把一个文件转化为字节
     * 
     * @param file
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) {
        byte[] bytes = null;
        InputStream is = null;
        try {
            if (file != null) {
                is = new FileInputStream(file);
                int length = (int) file.length();
                if (length > Integer.MAX_VALUE) // 当文件的长度超过了int的最大值
                {
                    log.info("this file is max ");
                    return null;
                }
                bytes = new byte[length];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }
                // 如果得到的字节长度和file实际的长度不一致就可能出错了
                if (offset < bytes.length) {
                    log.info("file length is error");
                    return null;
                }
                is.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return bytes;
    }
    /**
     * 字节写入文件
     * @param b
     * @param filestr
     */
    public static void byteFile(byte[] b,String filestr){
        File newFile = new File(filestr); 
        OutputStream os = null;
        try {
            os = new FileOutputStream(newFile);
            os.write(b);  //把流一次性写入一个文件里面    
            os.flush();   
            os.close(); 
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }finally{
            if (null!=os) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }        
    }
    /**
     * 切割文件
     * @param file
     * @param filePath
     * @return 切割文件个数
     * @throws Exception
     */
    public static int splitFile(File file,String filePath){
        int i=0;
        int size = 1024;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            if(!file.isFile()){
                throw new Exception("file not exists"+file.getAbsolutePath());
            }
            String filename = filePath+file.getName();//file.getAbsolutePath();
            File filetmp = new File(filename+"_"+0+".vk");
            if(filetmp.isFile()){
                throw new Exception("file exists"+filetmp.getAbsolutePath());
            }
            byte[] buf = new byte[1024*512];//0.5m
            fis = new FileInputStream(file);
            int readsize = 0;
            int pos = 0;
            int k = 0;
            int m = -1;
            File fileout = null;
            while((readsize = fis.read(buf, 0, buf.length))>0){            
                if(k!=m){
                    if(fos!=null){
                        fos.close();
                        fos = null;
                    }
                    m = k;
                    i = k;
                    fileout = new File(filename+"_"+k+".vk");
                    fos = new FileOutputStream(fileout);
                }
                fos.write(buf,0,readsize);
                fos.flush();
                pos += readsize;
                if(pos>size*(k+1)){
                    k++;
                }
            }
            if(fos!=null){
                fos.close();
                fos = null;
            }
            fis.close();
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }finally{
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return i;
    }
    /**
     * 合并分割文件
     * @param fileName
     * @param filePath
     * @throws Exception
     */
    public static void combination(String fileName,String filePath){
        String filename = filePath + fileName;//file.getAbsolutePath();
        File fileout = new File(filename);
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            if(fileout.isFile()){
                throw new Exception("file exists"+fileout.getAbsolutePath());
            }
            fos = new FileOutputStream(fileout);
            int k = 0;
            File filein = null;
            byte[] buf = new byte[1024*10];
            while(true){
                if(fis!=null){
                    fis.close();
                    fis = null;
                }
                filein = new File(filename+"_"+k+".vk");
                if(!filein.isFile()){
                    break;
                }
                fis = new FileInputStream(filein);
                int readsize = 0;
                while((readsize = fis.read(buf, 0, buf.length))>0){
                    fos.write(buf,0,readsize);
                    fos.flush();
                }
                k++;
            }
            if(fis!=null){
                fis.close();
                fis = null;
            }
            fos.close();
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }finally{
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
    /**
     * 将指定的zip文件解压到指定的目录下,如果文件已存在则覆盖。
     * @param compress zip压缩文件,如 D:/doc_c06_net.zip
     * @param decompression 解压路径,如 D:/doc_redcome_com/
     * @throws Exception
     */
    public static void dezip(String compress,String decompression){
        dezipRun(compress,decompression,null);
    }
    /**
     * zip文件解压到指定的目录下
     * @author Toddle Chen
     * @created Mar 17, 2015 4:14:53 PM
     * @param compress
     * @param decompression
     * @param encoding
     */
    public static void dezipRun(String compress,String decompression,String encoding){
        if(encoding==null || encoding.equals("")) encoding = "UTF-8";
        File infile = new File(compress);
        File dir = new File(decompression);
        FileOutputStream out = null;
        CnZipInputStream in = null;
        File files = null; //在指定解压路径下建子文件夹
        try {
            // 检查是否是ZIP文件
            ZipFile zip = new ZipFile(infile);
            zip.close();
            // 建立与目标文件的输入连接
            in = new CnZipInputStream(new FileInputStream(infile), encoding);
            ZipEntry file = in.getNextEntry();
            byte[] c = new byte[1024];
            int len;
            int slen;
            while (file != null) {
                String zename = file.getName();
                if (file.isDirectory()) {
                    files = new File(dir.getAbsolutePath() + "/" + zename); //在指定解压路径下建子文件夹
                    files.mkdirs();//新建文件夹
                } else {
                    files = new File(dir.getAbsolutePath() + "/" + zename).getParentFile();//当前文件所在目录
                    if (!files.exists())  {//如果目录文件夹不存在，则创建
                        files.mkdirs();
                    }
                    out = new FileOutputStream(dir.getAbsolutePath() + "/" + zename);
                    while ((slen = in.read(c, 0, c.length)) != -1)
                        out.write(c, 0, slen);
                    out.close();
                }
                file = in.getNextEntry();
            }
            in.close();
        } catch (ZipException zipe) {
            log.info(infile.getName() + "不是一个ZIP文件！文件格式错误");
        } catch (IOException ioe) {
            log.info("读取" + compress + "时错误！文件读取错误");
        } catch (Exception i) {
            log.info("over");
        }finally{
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
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
    /**
     * 
     * 描述 将文件转化成字符串
     * @author Flex Hu
     * @created 2015年3月28日 下午12:17:48
     * @param filePath
     * @return
     */
    public static String convertFileToString(String filePath){
        File file = new File(filePath);
        byte[] bytes = null;
        try {
            bytes=FileUtil.convertFileToBytes(file);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        String fileData = StringUtil.byte2hex(bytes);
        return fileData;
    }
    
    /**
     * 
     * 描述 将字符串转换成文件
     * @author Flex Hu
     * @created 2015年4月14日 上午9:19:53
     * @param fileContent
     * @param genFilePath
     */
    public static void convertStringToFile(String fileContent,String genFilePath){
        byte[] bytes = StringUtil.hex2byte(fileContent);
        FileUtil.bytesToFile(bytes, genFilePath);
    }
    /**
     * 描述 根据文件读取磁盘上文件里的内容
     * @author Toddle Chen
     * @created 2015年5月12日 下午6:06:10
     * @param filePath
     * @return
     */
    public static String getContentsOffile(String filePath){
        try {
            File file = new File(filePath);
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "";
    }
    
    /**
     * 
     * 描述 对base64文件进行解码
     * @author Flex Hu
     * @created 2015年6月25日 上午11:27:31
     * @param base64Code
     * @param targetPath
     */
    public static void decodeBase64File(String base64Code, String targetPath){
        String folderPath = targetPath.substring(0, targetPath.lastIndexOf("/"));
        File file = new File(folderPath);
        FileOutputStream out = null;
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] buffer;
        try {
            buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out = new FileOutputStream(targetPath);
            out.write(buffer);
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
    /**
     * 
     * 描述 对文件进行base64编码
     * @author Flex Hu
     * @created 2015年6月25日 上午11:25:18
     * @param filePath
     * @return
     */
    public static String encodeBase64File(String filePath){
        File file = new File(filePath);;
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }finally{
            if (inputFile!=null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }
    /**
     * 
     * 描述： 将inputstream转为Base64
     * @author Rider Chen
     * @created 2019年9月5日 下午4:25:16
     * @param is
     * @return
     */
    public static String encodeBase64File(InputStream is) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            log.error("",e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("输入流关闭异常",e);
                }
            }
        }

        return new BASE64Encoder().encode(data);
    }
    /**
     * 
     * 描述： 把流字符串保存到磁盘
     * 
     * @author Rider Chen
     * @created 2018年11月16日 上午9:52:33
     * @param filePath
     * @param fileTxt
     */
    public static void textToFile(String filePath, String fileTxt) {
        File file = null;
        FileOutputStream fos = null;
        try {
            file = new File(filePath);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){// 如果文件夹不存在，则创建
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();// 如果文件不存在，则创建
            }
            fos = new FileOutputStream(file);
            byte data[] = StringUtil.hex2byte(fileTxt);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("",e);
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("",e);
                }
            }
        }
    }
}
