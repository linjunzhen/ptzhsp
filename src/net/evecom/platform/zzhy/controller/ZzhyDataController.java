/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.zzhy.service.AddrService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述  外资企业登记信息Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/zzhyDataController")
public class ZzhyDataController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ZzhyDataController.class);
    /**
     * 引入Service
     */
    @Resource
    private AddrService addrService;
    /**
     * 描述 获取注册地址
     *
     * @author Allin Lin
     * @created 2020年11月18日 下午2:42:33
     * @param request
     * @param response
     */
    @RequestMapping( "/findAddrInfoByA13")
    public void findAddrInfoByA13(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = addrService.findAddrInfoByA13(filter);
        if(null==list){
            list = new  ArrayList<Map<String,Object>>();
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
}

