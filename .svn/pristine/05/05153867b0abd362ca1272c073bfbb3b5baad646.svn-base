/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;
import java.util.Iterator;
import java.util.List;

import net.evecom.core.model.TableColumn;
import net.evecom.platform.base.service.DataSourceService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年4月13日 上午10:36:32
 */
public class JsoupUtil {
    /**
     * DataSourceService
     */
    private static DataSourceService dataSourceService;

    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年4月13日 上午10:37:50
     * @param tableName
     * @param html
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Document addAttrMaxLength(String tableName, String html) {
        Document doc = null;
        dataSourceService = (DataSourceService) AppUtil
                .getBean("dataSourceService");
        // 获取数据库所有列
        List<TableColumn> list = dataSourceService.findColumns(tableName);
        doc = Jsoup.parse(html);
        Iterator<TableColumn> iterator = list.iterator();
        while (iterator.hasNext()) {
            TableColumn column = (TableColumn) iterator.next();
            Elements content = doc.select("input[name="
                    + column.getColumnName() + "][type=text]");
            Integer dataLength = column.getDataLength();
            // 添加字段最大长度
            content.attr("maxlength", String.valueOf(dataLength/2-1));
        }
        return doc;
    }
    /**
     * 
     * 清除Html格式，截取前面N个字数
     * @author Rider Chen
     * @created 2015-11-18 下午04:23:52
     * @param Context
     * @param count
     * @return
     */
    public static String getSummary(String Context, Integer count) {
        String str = "";
        Document doc = Jsoup.parse(Context);
        String sumary = Jsoup.clean(doc.html(), Whitelist.none());
        sumary = sumary.replace("&nbsp;", "").replace("&lt;", "").replace("&gt;", "").replace("&amp;", "").replace(
                "&apos;", "").replace("&quot;", "");
        Integer length = sumary.length();
        if (count > length) {
            str = sumary.substring(0, length);
        } else {
            str = sumary.substring(0, count);
        }
        return str;
    }
}
