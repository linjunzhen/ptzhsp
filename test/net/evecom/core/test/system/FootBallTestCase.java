/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.system;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.system.service.FootBallService;

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
 * @created 2015年11月22日 下午1:22:35
 */
public class FootBallTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FootBallTestCase.class);
    /**
     * 
     */
    @Resource
    private FootBallService footBallService;
    
    @Test
    public void testSaveJtdw() throws IOException{
        String targetUrl = "http://www.dszuqiu.com/team/151";
        Document detailDoc = Jsoup.connect(targetUrl).get();
        Elements elements = detailDoc.select("section#panel-1 tbody tr");
        for(int i =0;i<elements.size();i++){
            Element element = elements.get(i);
            Elements tds = element.select("td");
            if(tds.size()==11){
                //获取比赛时间
                String oldbssj = tds.get(1).text().trim();
                //获取比赛队伍
                String oldbsdw = tds.get(2).text().trim();
                //获取比赛结果
                String bsjg = tds.get(8).text().trim();
                if(bsjg.equals("-")){
                    bsjg = "";
                }
                Date dateBssj = DateTimeUtil.getDateOfStr(oldbssj, "yyyy/MM/dd HH:mm");
                String bssj = DateTimeUtil.getStrOfDate(dateBssj, "yyyy-MM-dd HH:mm");
                Map<String,Object> record = footBallService.getByJdbc("T_MSJW_SYSTEM_FOOTBALL",
                        new String[]{"BSQD","BSSJ"},new Object[]{oldbsdw,bssj});
                String recordId = null;
                if(record!=null){
                    recordId = (String) record.get("BALL_ID");
                }else{
                    record = new HashMap<String,Object>();
                }
                record.put("BSQD", oldbsdw);
                record.put("BSSJ", bssj);
                record.put("QCSG", bsjg);
                footBallService.saveOrUpdate(record, "T_MSJW_SYSTEM_FOOTBALL",recordId);
            }
            
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @throws IOException 
     * @created 2015年11月22日 下午3:43:41
     */
    @Test
    public void testSaveDaily() throws IOException{
        //String targetUrl = "http://www.dszuqiu.com/diary/last";
        String targetUrl = "http://www.dszuqiu.com/diary/20151122/p.5";
        Document detailDoc = Jsoup.connect(targetUrl).get();
        Elements elements = detailDoc.select("div#diary_info tbody tr");
        for(int i =0;i<elements.size();i++){
            Element element = elements.get(i);
            Elements tds = element.select("td");
            //获取比赛时间
            String oldbssj = tds.get(1).text().trim();
            oldbssj = DateTimeUtil.getCurrentYear()+"/"+oldbssj;
            //获取比赛队伍
            String oldbsdw = tds.get(2).text().trim();
            //获取比赛结果
            String bsjg = tds.get(7).text().trim();
            Date dateBssj = DateTimeUtil.getDateOfStr(oldbssj, "yyyy/MM/dd HH:mm");
            String bssj = DateTimeUtil.getStrOfDate(dateBssj, "yyyy-MM-dd HH:mm");
            if(bsjg.equals("-")){
                bsjg = "";
            }
            Map<String,Object> record = footBallService.getByJdbc("T_MSJW_SYSTEM_FOOTBALL",
                    new String[]{"BSQD","BSSJ"},new Object[]{oldbsdw,bssj});
            String recordId = null;
            if(record!=null){
                recordId = (String) record.get("BALL_ID");
            }else{
                record = new HashMap<String,Object>();
            }
            record.put("BSQD", oldbsdw);
            record.put("BSSJ", bssj);
            record.put("QCSG", bsjg);
            footBallService.saveOrUpdate(record, "T_MSJW_SYSTEM_FOOTBALL",recordId);
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        //String targetUrl = "http://www.dszuqiu.com/diary/last";
        //Document detailDoc = Jsoup.connect(targetUrl).get();
        //FileUtil.writeTextToDisk("d:/1.html", detailDoc.html());
        String html = FileUtil.getContentOfFile("d:/1.html");
        Document detailDoc = Jsoup.parse(html);
        Elements elements = detailDoc.select("div#diary_info tbody tr");
        for(int i =0;i<elements.size();i++){
            Element element = elements.get(i);
            Elements tds = element.select("td");
            //获取比赛时间
            String oldbssj = tds.get(1).text().trim();
            oldbssj = DateTimeUtil.getCurrentYear()+"/"+oldbssj;
            //获取比赛队伍
            String oldbsdw = tds.get(2).text().trim();
            //获取比赛结果
            String bsjg = tds.get(7).text().trim();
            Date dateBssj = DateTimeUtil.getDateOfStr(oldbssj, "yyyy/MM/dd HH:mm");
            String bssj = DateTimeUtil.getStrOfDate(dateBssj, "yyyy-MM-dd HH:mm");
            log.info(bssj);
        }
    }
}
