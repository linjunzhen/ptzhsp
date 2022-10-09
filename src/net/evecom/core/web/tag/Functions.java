/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.tag;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.tpl.service.ModelDsService;

import com.jfinal.core.JFinal;

/**
 * 
 * 描述
 * 
 * @author Rider Chen
 * @created 2017年3月10日 上午9:44:02
 */
public class Functions {
    /**
     * 访问统计
     * 
     * @param siteid
     * @param cnid
     * @param ctid
     * @param oldhits
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String visitstat(String siteid, String oldvisit, String numberStyle) {
        Long visit = (long) 1;
        ModelDsService modelDsService = (ModelDsService) AppUtil.getBean("modelDsService");
        Map<String, Object> site = modelDsService.getByJdbc("T_SYSTEM_SITE", new String[] { "SITE_ID" },
                new Object[] { siteid });
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        Map<String, Object> click = modelDsService.getByJdbc("T_SYSTEM_CLICK", new String[] { "CLICK_TYPE",
                "CLICK_PKID", "CLICK_DATE" }, new Object[] { 3, siteid, date });
        String entityId = "";
        if (click == null || click.size() < 1) {
            click = new HashMap<String, Object>();
            click.put("CLICK_TYPE", 3);
            click.put("CLICK_PKID1", siteid);
            click.put("CLICK_PKID", siteid);
            click.put("CLICK_DATE", date);
            click.put("CLICK_NUM", 1);
            modelDsService.saveOrUpdate(click, "T_SYSTEM_CLICK", entityId, "S_SYSTEM_CLICK");
        } else {
            Long clickNum = visit + Long.valueOf((String) click.get("CLICK_NUM"));
            click.put("CLICK_NUM", clickNum);
            entityId = (String) click.get("CLICK_ID");
            modelDsService.saveOrUpdate(click, "T_SYSTEM_CLICK", entityId);
        }
        Long newvisit = visit + Long.valueOf(oldvisit);
        site.put("SITE_CLICKALL", newvisit);
        modelDsService.saveOrUpdate(site, "T_SYSTEM_SITE", siteid);
//        oldvisit = (oldvisit == null || oldvisit.equals("")) ? "0" : oldvisit;
        String newvisitStr = newvisit.toString();
        StringBuffer strImg=new StringBuffer();
        String webRoot = JFinal.me().getContextPath();
        if (numberStyle.equals("")) {
            numberStyle = "11";
        }
        if (!numberStyle.equals("0")) {
            for (int i = 0; i < newvisitStr.length(); i++) {
                strImg.append("<img src='").append(webRoot);
                strImg.append( "/themes/default/images/counterImg/").append(numberStyle);
                strImg.append("/").append(newvisitStr.charAt(i)).append(".gif' alt='");
                strImg.append(newvisitStr.charAt(i)).append("'/>");
            }
        } else {
            return newvisitStr;
        }
        return strImg.toString();
    }
    
    /**
     * 当日访问数
     * 
     * @param siteid
     * @return
     */
    public static String todatecount(String siteid, String numberStyle) {
        String sql = "select click_num from t_system_click "
                + "where click_type=4 and click_pkid=? and click_date=to_char(sysdate,'yyyy-mm-dd')";
        ModelDsService modelDsService = (ModelDsService) AppUtil.getBean("modelDsService");
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        Map<String, Object> click = modelDsService.getByJdbc("T_SYSTEM_CLICK", new String[] { "CLICK_TYPE",
                "CLICK_PKID", "CLICK_DATE" }, new Object[] { 3, siteid, date });

        Long oldvisit =  1L;
        if(click!=null ){
            oldvisit = Long.valueOf((String) click.get("CLICK_NUM"));
        }

        Long newvisit = oldvisit;
        String newvisitStr = newvisit.toString();
        StringBuffer strImg=new StringBuffer();
        String webRoot = JFinal.me().getContextPath();
        if (numberStyle.equals("")) {
            numberStyle = "11";
        }
        if (!numberStyle.equals("0")) {
            for (int i = 0; i < newvisitStr.length(); i++) {
                strImg.append("<img src='").append(webRoot).append("/themes/default/images/counterImg/");
                strImg.append(numberStyle).append("/").append(newvisitStr.charAt(i));
                strImg.append(".gif' alt='").append(newvisitStr.charAt(i)).append("'/>");
            }
        } else {
            return newvisitStr;
        }
        return strImg.toString();
    }

}
