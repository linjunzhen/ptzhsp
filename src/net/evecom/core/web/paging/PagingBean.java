/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.paging;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class PagingBean {
    /**
     * 缺省的分页数量
     */
    public static final Integer DEFAULT_PAGE_SIZE = 15;
    /**
     * 每页开始的索引号
     */
    public Integer start;

    /**
     * 页码大小
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Integer totalItems;
    
    public PagingBean(){
        
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:32
     * @param start
     * @param limit
     */
    public PagingBean(int start, int limit) {
        this.pageSize = limit;
        this.start = start;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:36
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:40
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:43
     * @return
     */
    public int getTotalItems() {
        return null==totalItems?0:totalItems;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:46
     * @param totalItems
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;

    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:54
     * @return
     */
    public int getFirstResult() {
        return start;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:20:57
     * @return
     */
    public Integer getStart() {
        return start;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午7:21:06
     * @param start
     */
    public void setStart(Integer start) {
        this.start = start;
    }
}
