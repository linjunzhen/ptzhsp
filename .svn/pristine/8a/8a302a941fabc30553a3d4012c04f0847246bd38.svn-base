/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.tag;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 描述 定制化引入资源标签
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午9:27:18
 */
public class ResourcesTag extends TagSupport {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ResourcesTag.class);
    /**
     * easyUI资源
     */
    private static final String RES_EASYUI = "easyui";
    /**
     * JQUERY资源
     */
    private static final String RES_JQUERY = "jquery";
    /**
     * EVE工具JS
     */
    private static final String RES_APPUTIL = "apputil";
    /**
     * 弹出层插件
     */
    private static final String RES_ARTDIALOG = "artdialog";
    /**
     * 日期插件
     */
    private static final String RES_LAYDATE = "laydate";
    /**
     * 验证引擎
     */
    private static final String RES_VALIDENGINEE = "validationegine";
    /**
     * ZTREE组件
     */
    private static final String RES_ZTREE = "ztree";
    /**
     * JSON2组件
     */
    private static final String RES_JSON2 = "json2";
    /**
     * SWF上传组件
     */
    private static final String RES_SWFUPLOAD = "swfupload";
    /**
     * EWEB插件
     */
    private static final String RES_EWEB = "eweb";
    /**
     * 自动补全插件
     */
    private static final String RES_AUTOCOMPLETE = "autocomplete";
    /**
     * 弹出层插件
     */
    private static final String RES_LAYER = "layer";
    /**
     * 图片上传组件
     */
    private static final String RES_PICUPLOAD = "picupload";
    /**
     * 属性加载类型
     */
    protected String loadres = "default";// 加载类型
    /**
     * @author Flex Hu
     * @created 2014年9月6日 上午9:33:18
     * @param loadres
     */
    public void setLoadres(String loadres) {
        this.loadres = loadres;
    }

    /**
     * 方法doStartTag
     * 
     * @return 返回值int
     */
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * 方法doEndTag
     * 
     * @return 返回值int
     */
    public int doEndTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            StringBuffer sb = new StringBuffer();
            Set<String> res = new HashSet<String>();
            for(String resName:loadres.split(",")){
                res.add(resName);
            }
            if(res.contains(RES_JQUERY)){
                sb.append("<script type=\"text/javascript\" "
                        + "src=\"plug-in/jquery2/jquery.min.js\"></script>");
            }
            
            if(res.contains(RES_EASYUI)){
                sb
                .append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/easyui-1.4/themes/bootstrap"
                        + "/easyui.css\" type=\"text/css\"></link>");
                sb
                .append("<link rel=\"stylesheet\" href=\"plug-in/easyui-1.4/themes"
                        + "/icon.css\" type=\"text/css\"></link>");
                sb
                .append("<script type=\"text/javascript\" src=\"plug-in/easyui-1.4/jquery.easyui.min.js\">"
                        + "</script>");
                sb
                .append("<script type=\"text/javascript\" src=\"plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js\">"
                        + "</script>");
            }
            
            if(res.contains(RES_APPUTIL)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/eveutil-1.0/AppUtil.js\"></script>");
            }
            
            if(res.contains(RES_ARTDIALOG)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/artdialog-4.1.7/"
                        + "jquery.artDialog.js?skin=default\"></script>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/artdialog-4.1.7/"
                        + "plugins/iframeTools.source.js\"></script>");
                sb.append("<link rel=\"stylesheet\" href=\"plug-in/artdialog-4.1.7/skins/default.css"
                        + "\" type=\"text/css\"></link>");
            }
            
            if(res.contains(RES_LAYDATE)){
                sb
                .append("<link rel=\"stylesheet\" href=\"plug-in/laydate-1.1/"
                        + "laydatedemo.css\" type=\"text/css\"></link>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/laydate-1.1/laydate.js\"></script>");
            }
            
            if(res.contains(RES_VALIDENGINEE)){
                sb
                .append("<link rel=\"stylesheet\" href=\"plug-in/validationegine-2.6.2/"
                        + "css/validationEngine.jquery.css\" type=\"text/css\"></link>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "validationegine-2.6.2/jquery.validationEngine.js\"></script>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/validationegine-2.6.2/"
                        + "jquery.validationEngine-zh_CN.js\"></script>");
            }
            
            if(res.contains(RES_ZTREE)){
                sb
                .append("<link rel=\"stylesheet\" href=\"plug-in/"
                        + "ztree-3.5/css/zTreeStyle/zTreeStyle.css\" type=\"text/css\"></link>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "ztree-3.5/js/jquery.ztree.core-3.5.js\"></script>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "ztree-3.5/js/jquery.ztree.excheck-3.5.js\"></script>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "ztree-3.5/js/jquery.ztree.exedit-3.5.js\"></script>");
            }
            
            if(res.contains(RES_JSON2)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "json-2.0/json2.js\"></script>");
            }
            
            if(res.contains(RES_SWFUPLOAD)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "swfupload-2.2/swfupload.js\"></script>");
            }
            
            if(res.contains(RES_EWEB)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "ewebeditor/ewebeditor.js\"></script>");
            }
            
            if(res.contains(RES_AUTOCOMPLETE)){
                sb
                .append("<link rel=\"stylesheet\" href=\"plug-in/"
                        + "jqueryautocomplete-1.2.3/jquery.autocomplete.css\" type=\"text/css\"></link>");
                sb.append("<script type=\"text/javascript\" src=\"plug-in/"
                        + "jqueryautocomplete-1.2.3/jquery.autocomplete.js\"></script>");
            }
            
            if(res.contains(RES_LAYER)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/layer-1.8.5/layer.min.js\"></script>");
            }
            if(res.contains(RES_PICUPLOAD)){
                sb.append("<script type=\"text/javascript\" src=\"plug-in/upload/jquery.picupload.js\"></script>");
            }
            
            out.print(sb.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
