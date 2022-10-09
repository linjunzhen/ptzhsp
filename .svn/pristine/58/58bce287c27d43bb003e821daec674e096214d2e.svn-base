/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * 描述 图片压缩
 * 
 * @author Rider Chen
 * @created 2016-1-6 下午04:20:24
 */
public class CompressPic {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CompressPic.class);
    /**
     * 属性文件对象
     */
    private File file = null; // 文件对象
    /**
     * 属性输入图路径
     */
    private String inputDir; // 输入图路径
    /**
     * 属性输出图路径
     */
    private String outputDir; // 输出图路径
    /**
     * 属性输入图文件名
     */
    private String inputFileName; // 输入图文件名
    /**
     * 属性输出图文件名
     */
    private String outputFileName; // 输出图文件名
    /**
     * 属性默认输出图片宽
     */
    private int outputWidth = 600; // 默认输出图片宽
    /**
     * 属性默认输出图片高
     */
    private int outputHeight = 600; // 默认输出图片高

    /**
     * 等比率压缩率
     */
    private double proportionNum = 1;
    /**
     * 方法是否等比缩放标记
     * 
     * @param 默认为等比缩放
     *            传入参数
     * @return 返回值//
     */
    private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)

    /**
     * 方法_构造方法
     * 
     */
    public CompressPic() { // 初始化变量
        inputDir = "";
        outputDir = "";
        inputFileName = "";
        outputFileName = "";
        outputWidth = 600;
        outputHeight = 600;
        proportionNum = 1;
        proportion = true;
    }

    /**
     * 方法setInputDir
     * 
     * @param inputDir
     *            传入参数
     */
    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    /**
     * 方法setOutputDir
     * 
     * @param outputDir
     *            传入参数
     */
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * 方法setInputFileName
     * 
     * @param inputFileName
     *            传入参数
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * 方法setOutputFileName
     * 
     * @param outputFileName
     *            传入参数
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     * 方法setOutputWidth
     * 
     * @param outputWidth
     *            传入参数
     */
    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
    }

    /**
     * 方法setOutputHeight
     * 
     * @param outputHeight
     *            传入参数
     */
    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
    }

    /**
     * 方法setWidthAndHeight
     * 
     * @param width
     *            传入参数
     * @param height
     *            传入参数
     */
    public void setWidthAndHeight(int width, int height) {
        this.outputWidth = width;
        this.outputHeight = height;
    }

    /*
     * 获得图片大小 传入参数 String path ：图片路径
     */
    public long getPicSize(String path) {
        file = new File(path);
        return file.length();
    }

    // 图片处理
    /**
     * 方法compressPic
     * 
     * @return 返回值String
     */
    public String compressPic() {
        try {
            // 获得源文件
            file = new File(inputDir + inputFileName);
            if (!file.exists()) {
                return "";
            }
            Image img = ImageIO.read(file); // 这里是从本地读图片文件，如果是执行上传图片的话， Formfile
            // formfile=获得表单提交的Formfile ,然后
            // ImageIO.read 方法里参数放
            // formfile.getInputStream()
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                log.info(" can't read,retry!" + "<BR>");
                return "no";
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (this.proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (((double) img.getWidth(null)) * this.proportionNum);
                    // 根据缩放比率大的进行缩放控制
                    double rate2 = ((double) img.getHeight(null))
                            / (((double) img.getHeight(null)) * this.proportionNum);
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);

                } else {
                    newWidth = outputWidth; // 输出的图片宽度
                    newHeight = outputHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "ok";
    }

    /**
     * 方法compressPic
     * 
     * @param inputDir
     *            输入图路径
     * @param outputDir
     *            输出图路径
     * @param inputFileName
     *            输入图文件名
     * @param outputFileName
     *            输出图文件名
     * @param gp
     *            是否是等比缩放 标记 true是，false 否
     * @param num
     *            压缩比率 1为100%
     * @return String
     */
    private String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName,
            boolean gp, double num) {
        // 输入图路径
        this.inputDir = inputDir;
        // 输出图路径
        this.outputDir = outputDir;
        // 输入图文件名
        this.inputFileName = inputFileName;
        // 输出图文件名
        this.outputFileName = outputFileName;
        // 是否是等比缩放 标记
        this.proportion = gp;
        // 压缩比率
        this.proportionNum = num;
        return compressPic();
    }

    /**
     * 方法compressPic
     * 
     * @param inputDir
     *            输入图路径
     * @param outputDir
     *            输出图路径
     * @param inputFileName
     *            输入图文件名
     * @param outputFileName
     *            输出图文件名
     * @param width
     *            设置图片宽
     * @param height
     *            设置图片高
     * @param gp
     *            是否是等比缩放 标记 true是，false 否
     * @param num
     *            压缩比率 1为100%
     * @return String
     */
    private String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName,
            int width, int height, boolean gp, double num) {
        // 输入图路径
        this.inputDir = inputDir;
        // 输出图路径
        this.outputDir = outputDir;
        // 输入图文件名
        this.inputFileName = inputFileName;
        // 输出图文件名
        this.outputFileName = outputFileName;
        // 设置图片长宽
        setWidthAndHeight(width, height);
        // 是否是等比缩放 标记
        this.proportion = gp;
        // 压缩比率
        this.proportionNum = num;
        return compressPic();
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2016年1月14日 上午10:40:42
     * @param sourceFilePath
     *            :被压缩原图片路径
     * @param targetFilePath
     *            :压缩后图片路径
     * @param width
     *            设置图片宽
     * @param height
     *            设置图片高
     *            是否是等比缩放 标记 true是，false 否
     * @return
     */
    public String compressPic(String sourceFilePath, String targetFilePath, int width, int height) {
        String sourceFileFolder = null;
        String sourceFileName = null;
        String targetFileFolder = null;
        String targetFileName = null;
        if (sourceFilePath.contains("/")) {
            sourceFileFolder = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
            sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        } else if (sourceFilePath.contains("\\")) {
            sourceFileFolder = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("\\") + 1);
            sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("\\") + 1, sourceFilePath.length());
        }
        if (targetFilePath.contains("/")) {
            targetFileFolder = targetFilePath.substring(0, targetFilePath.lastIndexOf("/") + 1);
            targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf("/") + 1, targetFilePath.length());
        } else if (sourceFilePath.contains("\\")) {
            targetFileFolder = targetFilePath.substring(0, targetFilePath.lastIndexOf("\\") + 1);
            targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf("\\") + 1, targetFilePath.length());
        }
        // 建立全路径目录和临时目录
        File targetFolder = new File(targetFileFolder);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        return this.compressPic(sourceFileFolder, targetFileFolder, sourceFileName, targetFileName, width, height,
                false, 0);
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2016年1月14日 上午10:40:42
     * @param sourceFilePath
     *            :被压缩原图片路径
     * @param targetFilePath
     *            :压缩后图片路径
     * @param gp
     *            :是否是等比缩放 标记 true是，false 否
     * @param num
     *            :压缩比率 1为100%
     * @return
     */
    public String compressPic(String sourceFilePath, String targetFilePath, boolean gp, double num) {
        String sourceFileFolder = null;
        String sourceFileName = null;
        String targetFileFolder = null;
        String targetFileName = null;
        if (sourceFilePath.contains("/")) {
            sourceFileFolder = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
            sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        } else if (sourceFilePath.contains("\\")) {
            sourceFileFolder = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("\\") + 1);
            sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("\\") + 1, sourceFilePath.length());
        }
        if (targetFilePath.contains("/")) {
            targetFileFolder = targetFilePath.substring(0, targetFilePath.lastIndexOf("/") + 1);
            targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf("/") + 1, targetFilePath.length());
        } else if (sourceFilePath.contains("\\")) {
            targetFileFolder = targetFilePath.substring(0, targetFilePath.lastIndexOf("\\") + 1);
            targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf("\\") + 1, targetFilePath.length());
        }
        // 建立全路径目录和临时目录
        File targetFolder = new File(targetFileFolder);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        return this.compressPic(sourceFileFolder, targetFileFolder, sourceFileName, targetFileName, gp, num);
    }

    /*public static void main(String[] args) {
        CompressPic mypic = new CompressPic();
        String path = "e:\\Chrysanthemum.jpg";
        log.info("输入的图片大小：" + mypic.getPicSize(path) / 1024 + "KB");
        int count = 0; // 记录全部图片压缩所用时间
        for (int i = 0; i < 3; i++) {
            int start = (int) System.currentTimeMillis(); // 开始时间
            mypic.compressPic("e:\\", "e:\\test\\", "Chrysanthemum.jpg", "chris" + i + ".jpg", true, 0.1);
            int end = (int) System.currentTimeMillis(); // 结束时间
            int re = end - start; // 但图片生成处理时间
            count += re;
            log.info("第" + (i + 1) + "张图片压缩处理使用了: " + re + "毫秒");
            log.info("输出的图片大小：" + mypic.getPicSize("e:\\test\\chris" + i + ".jpg") / 1024 + "KB");
        }
        log.info("总共用了：" + count + "毫秒");

    }*/
}
