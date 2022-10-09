/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsoupUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.dao.DataSourceDao;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月18日 上午9:34:08
 */
public class JsoupUtilTestCase extends BaseTestCase{
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(JsoupUtilTestCase.class);
    /**
     * 
     */
    @Resource
    private DataSourceDao dataSourceDao;
    
    @Test
    public void testParseUrl() throws IOException{
        Document doc = Jsoup.connect("http://127.0.0.1/eveflow/leaveInfoController/view.do").get();
        log.info(doc.html());
        //Element ele = doc.getElementById("LeaveInfoForm");
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年4月24日 下午4:44:52
     */
    @Test
    public void getColComment(){
        String cl = dataSourceDao.getColComment("T_MSJW_FWDT_WANEWBARCARD","dCITY");
        log.info(cl);
    }
    /**
     * 
     * 描述 文件重命名
     * @author Flex Hu
     * @throws IOException 
     * @created 2015年4月28日 上午10:54:38
     */
    @Test
    public void testRename() throws IOException{
        Set<String> formats =new HashSet<String>();
        formats.add("html");
        String foldPath = "D:/MyCode/exmobicode/msjwapp/client/page/phone/default/html";
        List<File> htmlFiles =FileUtil.getFormatFiles(formats,new 
                File(foldPath),null);
//        Set<String> tableNames = new HashSet<String>();
        for(File htmlFile:htmlFiles){
            String fileContent = FileUtil.getContentOfFile(htmlFile.getAbsolutePath());
            Document doc = Jsoup.parse(fileContent);
            Element content = doc.getElementById("BUS_TABLENAME");
            if(content!=null){
                String busTableName = content.attr("value").toLowerCase();
                FileUtil.copyFile(htmlFile, foldPath+"/"+busTableName+".html");
                //log.info("表名称:"+content.attr("value").toLowerCase());
            }
        }
    }
    
    /**
     * 解析和遍历HTML
     * 
     * @author Flex Hu
     */
    @Test
    public void parseAndIter() {
        Set<String> formats =new HashSet<String>();
        formats.add("html");
        String foldPath = "D:/MyCode/exmobicode/msjwapp/client/page/phone/default/html";
        List<File> htmlFiles =FileUtil.getFormatFiles(formats,new 
                File(foldPath),null);
        for(File htmlFile:htmlFiles){
            String absolutePath = htmlFile.getAbsolutePath();
            String fileContent = FileUtil.getContentOfFile(absolutePath);
            Document detailDoc = Jsoup.parse(fileContent);
            Element content = detailDoc.getElementById("BUS_TABLENAME");
            if(content!=null){
                String nativeHtmlPath = foldPath+"/"+content.attr("value").toLowerCase();
                String detailHtmlPath = foldPath+"/"+content.attr("value").toLowerCase()+"_detail.html";
                File detailHtmlFile = new File(detailHtmlPath);
                if(!detailHtmlFile.exists()){
                    Elements detailCotents = detailDoc.select("*[name]");
                    for(Element ele:detailCotents){
                        String tagName = ele.tagName();
                        String type = ele.attr("type");
                        String colName = ele.attr("name");
                        String lableValue = "";
                        String colCom= dataSourceDao.getColComment(content.attr("value"),colName);
                        if(StringUtils.isNotEmpty(colCom)){
                            lableValue = "&nbsp;"+colCom+":";
                        }
                        if(tagName.equals("select")){
                            //ele.attr("disabled", "true");
                            ///ele.attr("style", "width: 60%;");
                            //ele.attr("class", "noborder");
                            StringBuffer newHtml = null;
                            if(StringUtils.isNotEmpty(lableValue)){
                                newHtml = new StringBuffer(lableValue);
                            }else{
                                newHtml = new StringBuffer();
                            }
                            //newHtml.append(ele.toString());
                            StringBuffer inputHtml =new StringBuffer("<input type=\"text\" name=\"")
                                .append(colName).append("\" style=\"width: 60%;\" ")
                                .append("class=\"noborder\" readonly=\"true\" />");
                            newHtml.append(inputHtml);
                            Element parent = ele.parent();
                            parent.html(newHtml.toString());
                        }else if(tagName.equals("input")&&type.equals("text")){
                            ele.attr("readonly", "true");
                            ele.attr("style", "width: 60%;");
                            ele.removeAttr("maxlength");
                            if(StringUtils.isNotEmpty(lableValue)){
                                StringBuffer newHtml = new StringBuffer(lableValue);
                                newHtml.append(ele.toString());
                                Element parent = ele.parent();
                                parent.html(newHtml.toString());
                            }
                        }else if(tagName.equals("textarea")){
                            ele.attr("readonly", "true");
                            ele.attr("style", "width: 60%;");
                            if(StringUtils.isNotEmpty(lableValue)){
                                StringBuffer newHtml = new StringBuffer(lableValue);
                                newHtml.append(ele.toString());
                                Element parent = ele.parent();
                                parent.html(newHtml.toString());
                            }
                        }else if(tagName.equals("input")&&type.equals("radio")){
                            ele.attr("disabled", "true");
                        }
                    }
                    FileUtil.writeTextToDisk(detailHtmlPath, detailDoc.toString());
                    //log.info(detailDoc.toString());
                }
            }
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月18日 上午9:36:44
     */
    @Test
    public void testCreateElement(){
        String div ="<div region=\"center\" id=\"myname\" ></div>";
        Document doc = Jsoup.parse(div);
        Element content = doc.getElementById("myname");
        String div2 = "<div id='hello'>测试</div>";
        content.append(div2);
        log.info(content);
        log.info(doc.html());
    }
    
    public static void main(String[] args){
        log.info(StringUtil.encryptSha256("123456"));
    }
}
