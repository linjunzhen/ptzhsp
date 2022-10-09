/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.core.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

/**
 * 描述 SVG操作工具类
 * 
 * @author Flex Hu
 * @version 1.1
 * 
 */
public class SvgUtil {
    /**
     * 将SVG文件转换成PDF
     * 
     * @author Flex Hu
     * @param svg
     * @param pdf
     * @throws IOException
     * @throws TranscoderException
     */
    public static void convertSvgFile2Pdf(File svg, File pdf) throws IOException, TranscoderException {
        InputStream in = new FileInputStream(svg);
        OutputStream out = new FileOutputStream(pdf);
        out = new BufferedOutputStream(out);
        convert2Pdf(in, out);
    }

    /**
     * 将输入流转换成输出流
     * 
     * @author Flex Hu
     * @param in
     * @param out
     * @throws IOException
     * @throws TranscoderException
     */
    public static void convert2Pdf(InputStream in, OutputStream out) throws IOException, TranscoderException {
        Transcoder transcoder = new PDFTranscoder();
        try {
            TranscoderInput input = new TranscoderInput(in);
            try {
                TranscoderOutput output = new TranscoderOutput(out);
                transcoder.transcode(input, output);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * 将svg文件转换成pdf
     * 
     * @author Flex Hu
     * @param svg
     * @param pdf
     * @throws IOException
     * @throws TranscoderException
     */
    public static void convertSvgFile2Png(File svg, File pdf) throws IOException, TranscoderException {
        InputStream in = new FileInputStream(svg);
        OutputStream out = new FileOutputStream(pdf);
        out = new BufferedOutputStream(out);
        convert2PNG(in, out);
    }

    /**
     * 将输入流转换成PNG输出流
     * 
     * @author Flex Hu
     * @param in
     * @param out
     * @throws IOException
     * @throws TranscoderException
     */
    public static void convert2PNG(InputStream in, OutputStream out) throws IOException, TranscoderException {
        Transcoder transcoder = new PNGTranscoder();
        try {
            TranscoderInput input = new TranscoderInput(in);
            try {
                TranscoderOutput output = new TranscoderOutput(out);
                transcoder.transcode(input, output);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * 将字符串转换成PDF
     * 
     * @author Water Guo
     * @param pdf
     * @throws IOException
     * @throws TranscoderException
     */
    public static void convertStr2Pdf(String svg, File pdf) throws IOException, TranscoderException {
        InputStream in = new ByteArrayInputStream(svg.getBytes());
        OutputStream out = new FileOutputStream(pdf);
        out = new BufferedOutputStream(out);
        convert2Pdf(in, out);
    }

    /**
     * 将字符串转换成PNG
     * 
     * @author Flex Hu
     * @param svg
     * @param pdf
     * @throws IOException
     * @throws TranscoderException
     */
    public static void convertStr2Png(String svg, File png) throws IOException, TranscoderException {
        InputStream in = new ByteArrayInputStream(svg.getBytes());
        OutputStream out = new FileOutputStream(png);
        out = new BufferedOutputStream(out);
        convert2PNG(in, out);
    }
}
