/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.kit.StringKit;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.WebUtil;
import net.evecom.platform.tpl.service.ModelDsService;

/**
 * 
 * 描述 图片播放器
 * 
 * @author Rider Chen
 * @created 2015-12-10 上午11:03:38
 */
public class IMGPlayerTag extends BodyTagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(IMGPlayerTag.class);
    /**
     * 属性-3805148461261134806L
     */
    private static final long serialVersionUID = -3805148461261134806L;
    /**
     * 属性dsid
     */
    private int dsid;
    /**
     * 属性filterid
     */
    private String filterid;
    /**
     * 属性无值时自适应
     */
    private int width;// 无值时自适应
    /**
     * 属性无值时自适应
     */
    private int height;// 无值时自适应
    /**
     * 属性标题长度：-1:不显示标题。0:显示标题但不限制长度，其它正整数：限制标题的显示长度。
     */
    private int titlelenght;// 标题长度：-1:不显示标题。0:显示标题但不限制长度，其它正整数：限制标题的显示长度。
    /**
     * 属性begin
     */
    private int begin;
    /**
     * 属性vis
     */
    private int vis;
    /**
     * 属性end
     */
    private int end;
    /**
     * 属性skin
     */
    private String skin;

    /**
     * 方法doStartTag
     * 
     * @return 返回值int
     */
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        StringBuffer player = new StringBuffer("");
        ModelDsService modelDsService = (ModelDsService) AppUtil.getBean("modelDsService");
        Map<String, Object> modelDs = modelDsService.getByJdbc("T_CMS_TPL_MODELDS", new String[] { "DSID" },
                new Object[] { dsid });
        if (modelDs != null) {
            String playerId = "" + new Date().getTime();
            String sql = (String) modelDs.get("DSCODE");
            creatStyle1(player, playerId, sql);
        } else {
            player.append("没找到数据源！");
        }
        try {
            // log.info(player.toString());
            pageContext.getOut().println(player.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return SKIP_BODY; // 本标签主体为空,所以直接跳过主体
    }

    /**
     * 方法creatStyle1
     * 
     * @param player
     *            传入参数
     * @param playerId
     *            传入参数
     * @param sql
     *            传入参数
     */
    private void creatStyle1(StringBuffer player, String playerId, String sql) {
        player.append("<link rel=\"stylesheet\" href=\"plug-in/nivo-slider3.2/nivo-slider/themes/" + skin + "/" + skin
                + ".css\" type=\"text/css\" media=\"screen\" />");
        player.append("<link rel=\"stylesheet\" href=\"plug-in/nivo-slider3.2/nivo-slider/nivo-slider.css\""
                + " type=\"text/css\" media=\"screen\" />");
        player.append("<script type=\"text/javascript\" src=\""
                + "plug-in/nivo-slider3.2/nivo-slider/jquery.nivo.slider.pack.js\"></script>");
        player.append("<div class=\"slider-wrapper theme-" + skin + "\" style=\"width:" + width + "px;\">");
        player.append("<div id=\"imgplayer" + playerId + "\" class=\"nivoSlider\">");
        ModelDsService modelDsService = (ModelDsService) AppUtil.getBean("modelDsService");
        Object[] parasArray;
//        Object[] parasArray = new Object[0];
        parasArray = new Object[] { filterid, end, begin };

        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        List<Map<String, Object>> items = modelDsService.findBySql(sql, parasArray);
        if (items != null && items.size() != 0) {
            for (Map<String, Object> record : items) {
                player.append("<a href=\"contentController/view.do?contentId=" + record.get("tid")
                        + "\" target=\"_blank\">");
                String titleimg = record.get("titleimg") == null ? "webpage/common/images/nopic.jpg" : (String) record
                        .get("titleimg");
                if (titleimg.indexOf("/") == 0) {
                    titleimg = titleimg.substring(1, titleimg.length());
                }
                String title = (String) record.get("itemTitle");
                player.append("<img src=\"" + fileServer+titleimg + "\" alt=\"" + title + "\"");
                if (titlelenght == 0) {
                    player.append(" title=\"" + title + "\"");
                } else if (titlelenght >= 0) {
                    String showTitle = WebUtil.subString4JSPveiw(title, titlelenght, true);
                    if (showTitle != null) {
                        player.append(" title=\"" + showTitle + "\"");
                    }
                }
                player.append(" style=\"height:" + height + "px\"");
                player.append(" />");
                player.append("</a>");
            }
        } else {
            player.append("<img src=\"webpage/common/images/nopic.jpg" + "\" title=\"暂无\" alt=\"暂无\"  style=\"height:"
                    + height + "px\"/>");
        }
        player.append("</div></div>");
        player.append("<script type=\"text/javascript\">");
        player.append("$(window).load(function() {");
        player.append("$(\"#imgplayer" + playerId + "\").nivoSlider();");
        player.append("});");
        player.append("</script>");
    }

    /**
     * 方法doAfterBody
     * 
     * @return 返回值int
     */
    @Override
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    /**
     * 方法doEndTag
     * 
     * @return 返回值int
     */
    @Override
    public int doEndTag() throws JspException {
        super.release();// 调用父类回资源
        return EVAL_PAGE;
    }

    /**
     * 方法setDsid
     * 
     * @param dsid
     *            传入参数
     */
    public void setDsid(int dsid) {
        this.dsid = dsid;
    }

    /**
     * 方法setFilterid
     * 
     * @param filterid
     *            传入参数
     */
    public void setFilterid(String filterid) {
        this.filterid = filterid.replace("eve_moduleid_", "");
    }

    /**
     * 方法setWidth
     * 
     * @param width
     *            传入参数
     */
    public void setWidth(int width) {
        width = StringKit.isBlank("" + width) ? 0 : width;
        this.width = (width == 0 ? 400 : width);
    }

    /**
     * 方法setHeight
     * 
     * @param height
     *            传入参数
     */
    public void setHeight(int height) {
        height = StringKit.isBlank("" + height) ? 0 : height;
        this.height = (height == 0 ? 300 : height);
    }

    /**
     * 方法setBegin
     * 
     * @param begin
     *            传入参数
     */
    public void setBegin(int begin) {
        this.begin = begin;
    }

    /**
     * 方法setVis
     * 
     * @param vis
     *            传入参数
     */
    public void setVis(int vis) {
        this.vis = vis;
    }

    /**
     * 方法setEnd
     * 
     * @param end
     *            传入参数
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * 方法setTitlelenght
     * 
     * @param titlelenght
     *            传入参数
     */
    public void setTitlelenght(int titlelenght) {
        this.titlelenght = titlelenght;
    }

    /**
     * 方法setSkin
     * 
     * @param skin
     *            传入参数
     */
    public void setSkin(String skin) {
        this.skin = (skin.equals("") ? "blue" : skin);
    }
}