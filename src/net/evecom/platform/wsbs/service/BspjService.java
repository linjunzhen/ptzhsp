/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import javax.xml.rpc.ServiceException;

/**
 * 描述 办事评价操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface BspjService extends BaseService {
    /**
     * 
     * 描述 获取前台用户中心我的评价列表
     * @author Faker Li
     * @created 2015年12月4日 上午10:49:54
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> findfrontList(String page, String rows);

    /**
     * 
     * 描述 办事指南服务评价
     * @author Faker Li
     * @created 2015年12月4日 上午10:49:54
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> findfrontList(String page, String rows, String itemCode);
    /**
     * 
     * 描述：sql查找数据
     * @author Water Guo
     * @created 2017-3-27 下午5:14:12
     * @param filter
     * @return
     */
    List<Map<String, Object>> findBySqlFilter(SqlFilter filter);


    /**
     * 描述:获取省网办件评价信息
     *
     * @author Madison You
     * @created 2019/10/21 12:35:00
     * @param
     * @return
     */
    Map<String, Object> findSwpjData
    (String businessCode,String projectNo,String nodeName,String visitFrom) throws ServiceException, RemoteException;


    /**
     * 描述:获取省网办件评价链接
     *
     * @author Madison You
     * @created 2019/10/21 12:36:00
     * @param
     * @return
     */
    Map<String, Object> findSwpjLink(String businessCode,String projectNo,
                                     String nodeName,String pf,String type) throws RemoteException, ServiceException;


}
