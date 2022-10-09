/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.search.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.XmlConverUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.search.service.LuceneConfigService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Page;

/**
 * 描述 全文检索配置Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/luceneConfigController")
public class LuceneConfigController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(LuceneConfigController.class);
    /**
     * 引入Service
     */
    @Resource
    private LuceneConfigService luceneConfigService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        luceneConfigService.remove("T_LUCENE_CONFIG", "CONFIG_ID",
                selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 全文检索配置记录",
                SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 列表页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("search/luceneConfig/list");
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> luceneConfig = luceneConfigService.getByJdbc(
                    "T_LUCENE_CONFIG", new String[] { "CONFIG_ID" },
                    new Object[] { entityId });
            request.setAttribute("luceneConfig", luceneConfig);
        }
        return new ModelAndView("search/luceneConfig/info");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = luceneConfigService.findBySqlFilter(
                filter, "");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONFIG_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = luceneConfigService.saveOrUpdate(variables,
                "T_LUCENE_CONFIG", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 全文检索配置记录",
                    SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 全文检索配置记录",
                    SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述 首页搜索跳转
     * 
     * @author Rider Chen
     * @created 2016年1月28日 下午4:23:14
     * @param request
     * @param response
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request,
            HttpServletResponse response) {
        // 获取查询条件
        String key = request.getParameter("key");// 关键字
        request.setAttribute("key", key);
        return new ModelAndView("website/index/search");
    }

    /**
     * easyui AJAX请求数据 全文检索文章信息
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings( { "rawtypes", "deprecation", "resource" })
    @RequestMapping("/searchlist")
    public void searchlist(HttpServletRequest request,
            HttpServletResponse response) {
        StringBuffer searchStr = new StringBuffer("");
        String page = request.getParameter("page");// 当前页
        String rows = request.getParameter("rows");// 每页条数
        if (rows != null) {
            int rowsNum = Integer.parseInt(rows);
            if (rowsNum >= 15) {
                rows = "15";
            }
        }
        // 获取查询条件
        String key = request.getParameter("key");// 关键字
        String title = request.getParameter("title");// 标题
        String content = request.getParameter("content");// 内容

        String luceneName = request.getParameter("luceneName");
        getNotNull(searchStr, key, title, content);
        try {

            // 与Lucene系统交互
            DefaultHttpClient client = new DefaultHttpClient();
            Properties properties = FileUtil
                    .readProperties("project.properties");
            String lucenePath = properties.getProperty("luceneWebPath");
            HttpPost post = new HttpPost(lucenePath
                    + "/LuceneSearch?luceneName=" + luceneName);
            // 设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("pageSize", String.valueOf(rows)));
            nvps
                    .add(new BasicNameValuePair("pageNumber", String
                            .valueOf(page)));
            if (searchStr.length() > 3) {
                nvps.add(new BasicNameValuePair("searchStr", searchStr
                        .substring(0, searchStr.length() - 3)));
            } else {
                nvps.add(new BasicNameValuePair("searchStr", searchStr
                        .toString()));
            }
            post.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
            HttpResponse ser = client.execute(post);
            HttpEntity entity = ser.getEntity();
            if (entity != null) {
                String body = IOUtils.toString(entity.getContent());
                body = new String(body.getBytes(), "gbk");
                Map resultMap = XmlConverUtil.xmltoMap(body);
                List resultlist = null;
                if (resultMap != null) {
                    String list = (String) resultMap.get("result");
                    resultlist = XmlConverUtil.xmltoList(list);

                    this.setListToJsonString(Integer
                            .parseInt((String) resultMap.get("totalRow")),
                            resultlist, null, JsonUtil.EXCLUDE, response);
                } else {
                    this.setListToJsonString(0, resultlist, null,
                            JsonUtil.EXCLUDE, response);
                }
                if (resultlist == null || resultlist.size() == 0) {
                    resultlist = null;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void getNotNull(StringBuffer searchStr, String key, String title,
            String content) {

        if (notNullString(title)) {
            searchStr.append("+contentTitle:").append("\""+title.trim()+"\"").append(
                    "   ");
        } else if (notNullString(key) && !notNullString(content)) {
            searchStr.append("(contentTitle:").append("\""+key.trim()+"\"")
                    .append(" or ");
        }

        if (notNullString(content)) {
            // map.put("contentTextdelHTML", content.trim());
            searchStr.append("+contentTextdelHTML:").append("\""+content.trim()+"\"")
                    .append("   ");
        } else if (notNullString(key) && !notNullString(title)) {
            // map.put("contentTextdelHTML", key.trim());
            searchStr.append("contentTextdelHTML:").append("\""+key.trim()+"\"").append(
                    ")   ");
        }

    }

    public static boolean notNullString(String str) {
        if (!StringUtils.isNotEmpty(str))
            return false;
        if (str == null)
            return false;
        str = str.trim();
        if ("".equals(str))
            return false;

//        str = str.replace("'", "''");
        return true;
    }

    public static boolean notNullInt(String str) {
        if (!notNullString(str))
            return false;
        try {
            if (Integer.valueOf(str) == null) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 生成索引
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addIndexes")
    public void addIndexes(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String url = request.getParameter("url");
        String success = sendPost(url, "");
        if (null != success && success.indexOf("success") > -1) {
            result.put("msg", "生成索引成功");
            result.put("success", true);
        } else {
            result.put("msg", "生成索引失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 远程post请求
     * 
     * @param url
     * @param param
     * @return
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result=new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.info("发送POST请求出现异常！" + e);
            log.error(e.getMessage());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        log.info("STRING:"
                + HttpRequestUtil.sendPost("http://59.61.182.106:8081/userInfoController/login.do",
                        "luceneName=Content"));
    }
}
