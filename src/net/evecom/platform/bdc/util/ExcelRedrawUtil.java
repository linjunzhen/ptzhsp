/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.star.uno.Exception;
import com.swetake.util.Qrcode;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.UUIDGenerator;

/**
 * 
 * 描述 Excel回填数据工具类
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月20日 上午11:46:08
 */
public class ExcelRedrawUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExcelRedrawUtil.class);

    /**
     * 二维码文件后缀
     */
    private static String pICEXT = "jpg";
    /**
     * 源数据最大字节数
     */
    private static int mAXDATARANGESMALL = 120;
    /**
     * 源数据最大字节数
     */
    private static int mAXDATARANGELARGE = 500;

    /**
     * 
     * 描述 插入二维码图片
     * 
     * @author Roger Li
     * @created 2019年12月22日 上午9:37:55
     * @param wb
     * @param sheetName
     *            二维码所在的sheet名
     * @param srcData
     */
    public static void setQRCode(Workbook wb, String sheetName, String srcData) {
        try {
            if (wb instanceof HSSFWorkbook) {
                ExcelRedrawUtil.setQRcode03(wb, sheetName, srcData);
            } else if (wb instanceof XSSFWorkbook) {
                ExcelRedrawUtil.setQRcode07(wb, sheetName, srcData);
            } else {
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 
     * 描述 根据redrawData给excel设值
     * 
     * @author Roger Li
     * @created 2019年12月20日 上午9:12:59
     * @param redrawData
     * @param workbook
     * @return
     */
    public static void setExcelData(Workbook wb, Map<String, Object> redrawData) {
        for (Map.Entry<String, Object> entry : redrawData.entrySet()) {
            String valString = entry.getValue() == null ? "" : entry.getValue().toString();
            String tagString = entry.getKey() == null ? "" : entry.getKey().toString();
            int namedCellIdx = wb.getNameIndex(tagString);
            // 如果Excel中存在该标签则设值
            if (namedCellIdx > -1) {
                Name aNamedCell = wb.getNameAt(namedCellIdx);
                // 有定义名称 但是名称没有绑定单元格
                if (aNamedCell.getRefersToFormula().endsWith("#REF!")) {
                    break;
                }
                AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula());
                CellReference cref = aref.getAllReferencedCells()[0];
                Sheet s = wb.getSheet(cref.getSheetName());
                Row r = s.getRow(cref.getRow());
                // 行没内容即返回null
                if (r == null) {
                    r = s.createRow(cref.getRow());
                }
                Cell c1 = r.getCell(cref.getCol());
                // 单元格没内容即返回null
                if (c1 == null) {
                    c1 = r.createCell(cref.getCol());
                }
                c1.setCellValue(valString);
            }
        }
    }

    /**
     * 
     * 描述 根据输入流判断Excel所属版本返回对应的Workbook对象
     * 
     * @author Roger Li
     * @created 2019年12月22日 上午9:33:39
     * @param fis
     * @return
     */
    public static Workbook getVersionWorkbook(InputStream fis) {
        // 不加报错：java.io.IOException: mark/reset not supported
        // PushbackInputStream参考：https://my.oschina.net/fhd/blog/345011
        if (!fis.markSupported()) {
            fis = new PushbackInputStream(fis, 8);
        }
        /**
         * 只能通过这种方式判断版本，使用如果通过 try catch捕获异常方式先读取了一次，流会被关闭，后面就读取不到了
         */

        try {
            if (POIFSFileSystem.hasPOIFSHeader(fis)) {
                // Excel2003
                return new HSSFWorkbook(fis);
            } else if (POIXMLDocument.hasOOXMLHeader(fis)) {
                // Excel2007
                return new XSSFWorkbook(fis);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            // Excel格式错误
            return null;
        }
        // Excel格式错误
        return null;
    }

    /**
     * 
     * 描述 03给指定名称的sheet插入二维码(覆盖sheet中第一张图片位置)
     * 
     * @author Roger Li
     * @created 2019年12月20日 上午12:58:28
     * @param
     * @return
     */
    private static void setQRcode03(Workbook wb, String sheetName, String srcData) throws Exception {

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        if (ExcelRedrawUtil.generateQRcode(byteArrayOut, srcData)) {
            // 读取excel工作簿
            HSSFSheet sheet = (HSSFSheet) wb.getSheet(sheetName);
            if (sheet == null) {
                throw new Exception("sheet未找到");
            }
            // 填入二维码
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFClientAnchor hAnchor = null;
            HSSFShape hShape = sheet.getDrawingPatriarch().getChildren().get(0);
            if (hShape != null) {
                // 如果是Excel2003及以下
                hAnchor = (HSSFClientAnchor) hShape.getAnchor();
                hAnchor.setAnchorType(ClientAnchor.DONT_MOVE_AND_RESIZE);
                patriarch.createPicture(hAnchor,
                        wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            }
        } else {
            throw new Exception("二维码生成失败");
        }
        try {
            byteArrayOut.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 
     * 描述 07给指定名称的sheet插入二维码(覆盖sheet中第一张图片位置)
     * 
     * @author Roger Li
     * @created 2019年12月20日 上午1:03:55
     * @param
     * @return
     */
    private static void setQRcode07(Workbook wb, String sheetName, String srcData) throws Exception {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        if (ExcelRedrawUtil.generateQRcode(byteArrayOut, srcData)) {
            XSSFSheet xSheet = (XSSFSheet) wb.getSheet(sheetName);
            if (xSheet == null) {
                throw new Exception("sheet未找到");
            }
            Drawing drawing = xSheet.createDrawingPatriarch();
            XSSFClientAnchor xAnchor = null;
            POIXMLDocumentPart dr = xSheet.getRelations().get(0);
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing1 = (XSSFDrawing) dr;
                XSSFShape sShape = drawing1.getShapes().get(0);
                xAnchor = (XSSFClientAnchor) sShape.getAnchor();
                xAnchor.setAnchorType(ClientAnchor.DONT_MOVE_AND_RESIZE);
                drawing.createPicture(xAnchor,
                        wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
            }
        } else {
            throw new Exception("二维码生成失败");
        }
        try {
            byteArrayOut.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 
     * 描述 生成打印文件的保存路径
     * 
     * @author Roger Li
     * @created 2019年12月23日 下午4:47:18
     * @param fileExt
     * @return
     */
    public static String getSavePath(String fileExt) {
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        String uploadPath = "PrintAttach/";
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        String str = "";
        String uuId = UUIDGenerator.getUUID();
        str = uuId + "." + fileExt;
        String filename = uploadFullPath + str;
        // filename 包含了配置文件中配置的attachFileFolderPath
        // filePath 只有attachFiles/开始的路径 因为他上传到服务器的那部分代码 又加了一遍配置文件中的地址。
        return filename;
    }
    /**
     * 
     * 描述 生成二维码
     * 
     * @author Roger Li
     * @created 2019年12月25日 上午11:24:19
     */
    private static boolean generateQRcode(ByteArrayOutputStream byteArrayOut, String srcData) {
        byte[] d = srcData.getBytes();
        if (d.length == 0) {
            return false;
        }
        BufferedImage bufferedImage = null;
        if (d.length <= 120) {
            bufferedImage = encode120(d);
        } else if (d.length <= 500) {
            bufferedImage = encode500(d);
        } else {
            return false;
        }
        // 写入byteArrayOut中
        try {
            ImageIO.write(bufferedImage, pICEXT, byteArrayOut);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 
     * 描述 源数据小于120字节申城二维码
     * 
     * @author Roger Li
     * @created 2019年12月25日 下午2:26:39
     * @param d
     * @return
     */
    private static BufferedImage encode120(byte[] d) {
        // 计算二维码图片的高宽比
        // API文档规定计算图片宽高的方式
        int v = 6;
        int width = 67 + 12 * (v - 1);
        int height = 67 + 12 * (v - 1);

        Qrcode x = new Qrcode();
        /**
         * 纠错等级分为 level L : 最大 7% 的错误能够被纠正； level M : 最大 15% 的错误能够被纠正； level Q :
         * 最大 25% 的错误能够被纠正； level H : 最大 30% 的错误能够被纠正；
         */
        x.setQrcodeErrorCorrect('L');
        x.setQrcodeEncodeMode('B');// 注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
        // 版本号 1-40 纠错等级L,QrcodeVersion=6时，容量为134字节。
        // https://www.cnblogs.com/asderx/p/4911133.html
        x.setQrcodeVersion(v);

        // 缓冲区
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 绘图
        Graphics2D gs = bufferedImage.createGraphics();

        gs.setBackground(Color.WHITE);
        gs.clearRect(0, 0, width, height);
        gs.setColor(Color.BLACK);
        // 偏移量
        int pixoff = 2;

        /**
         * 容易踩坑的地方 1.注意for循环里面的i，j的顺序， s[j][i]二维数组的j，i的顺序要与这个方法中的
         * gs.fillRect(j*3+pixoff,i*3+pixoff, 3, 3); 顺序匹配，否则会出现解析图片是一串数字
         * 2.注意此判断if (d.length > 0 && d.length < 120)
         * 是否会引起字符串长度大于120导致生成代码不执行，二维码空白 根据自己的字符串大小来设置此配置
         */
        if (d.length > 0 && d.length <= mAXDATARANGESMALL) {
            boolean[][] s = x.calQrcode(d);
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        }
        gs.dispose();
        bufferedImage.flush();
        return bufferedImage;
    }

    /**
     * Encoding the information to a QRCode, size of the information must be
     * less than 500 byte.
     * 
     * @param d
     * @return
     */
    private static BufferedImage encode500(byte[] d) {
        int dataLength = d.length;
        int imageWidth = 269; /*
                               * 269是预先计算出来的.
                               * 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
                               */
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= mAXDATARANGELARGE) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(18); // 0<= version <=40; 89字节,
                                         // (89-1)*3+2+3-1+1 = 269
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 =
                         * 136; 生成的image的宽度大小必须 >=(136+1), 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
            // System.out.println("二维码数据长度(字节):" + qrcodeDataLen);
        } else {
            return null;
        }
        g.dispose();
        bi.flush();
        return bi;
    }

    /**
     * Encoding the information to a QRCode, size of the information must be
     * less than 106 byte. QrcodeVersion=6
     * 
     * @param srcValue
     * @param qrcodePicfilePath
     * @return
     */
    public static boolean encodeVersion6(String srcValue, String qrcodePicfilePath) {
        int MAX_DATA_LENGTH = 106; // 限制生成二维码的数据最大大小
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageWidth = 128; /*
                               * 113是预先计算出来的.
                               * 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
                               */
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(6); // 37字节, (37-1)*3+2+3-1+1 = 113
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 ;
                         * 生成的image的宽度大小必须 >=该值, 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
            // System.out.println("二维码数据长度(字节):" + qrcodeDataLen);
        } else {
            // System.out.println("Generate QRCode image error! Data size is "
            // + dataLength + ", it is lager than 84 bytes.");
            return false;
        }
        g.dispose();
        bi.flush();
        /* generate image */
        File f = new File(qrcodePicfilePath);
        String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
        try {
            ImageIO.write(bi, suffix, f); // "png"
        } catch (IOException ioe) {
            // System.out.println("Generate QRCode image error!"
            // + ioe.getMessage());
            return false;
        }
        return true;
    }
    /**
     * Encoding the information to a QRCode, size of the information must be
     * less than 106 byte. QrcodeVersion=6
     * 
     * @param srcValue
     * @param qrcodePicfilePath
     * @return
     */
    public static boolean encodeVersion6(String srcValue, String qrcodePicfilePath,int imageWidth) {
        int MAX_DATA_LENGTH = 106; // 限制生成二维码的数据最大大小
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(6); // 37字节, (37-1)*3+2+3-1+1 = 113
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 4 + 2, i * 4 + 2, 4, 4);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 ;
                         * 生成的image的宽度大小必须 >=该值, 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
            // System.out.println("二维码数据长度(字节):" + qrcodeDataLen);
        } else {
            // System.out.println("Generate QRCode image error! Data size is "
            // + dataLength + ", it is lager than 84 bytes.");
            return false;
        }
        g.dispose();
        bi.flush();
        /* generate image */
        File f = new File(qrcodePicfilePath);
        String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
        try {
            ImageIO.write(bi, suffix, f); // "png"
        } catch (IOException ioe) {
            // System.out.println("Generate QRCode image error!"
            // + ioe.getMessage());
            return false;
        }
        return true;
    }
    public static byte[] encodeVersion6test(String srcValue, String qrcodePicfilePath) {
        int MAX_DATA_LENGTH = 106; // 限制生成二维码的数据最大大小
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageWidth = 128; /*
                               * 113是预先计算出来的.
                               * 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
                               */
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(6); // 37字节, (37-1)*3+2+3-1+1 = 113
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 ;
                         * 生成的image的宽度大小必须 >=该值, 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
            // System.out.println("二维码数据长度(字节):" + qrcodeDataLen);
        } else {
            // System.out.println("Generate QRCode image error! Data size is "
            // + dataLength + ", it is lager than 84 bytes.");
            return null;
        }
        g.dispose();
        bi.flush();
        /* generate image */
        // File f = new File(qrcodePicfilePath);
        // String suffix = f.getName().substring(f.getName().indexOf(".") + 1,
        // f.getName().length());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos); // "png"
            byte[] bt = baos.toByteArray();
            return bt;
        } catch (IOException ioe) {
            // System.out.println("Generate QRCode image error!"
            // + ioe.getMessage());
            return null;
        }
    }

}
