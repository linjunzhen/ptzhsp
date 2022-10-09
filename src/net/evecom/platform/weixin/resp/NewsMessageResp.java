/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.resp;

import java.util.List;

/**
 * 描述  响应消息之图文消息
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class NewsMessageResp extends BaseMessageResp{

    /**图文消息个数，限制为10条以内 **/
    private int ArticleCount;
    /** 多条图文消息信息，默认第一个item为大图 **/
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
