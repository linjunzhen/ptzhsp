/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.zzhy.dao.ForeignControllerDao;
import net.evecom.platform.zzhy.model.AddrPara;
import net.evecom.platform.zzhy.model.addr.Pages;
import net.evecom.platform.zzhy.service.AddrService;
import net.evecom.platform.zzhy.util.AddrUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 描述 外资企业登记信息操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("addrService")
public class AddrServiceImpl extends BaseServiceImpl implements AddrService {
    /**
     * 所引入的dao
     */
    @Resource
    private ForeignControllerDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     *
     * 描述    获取注册地址通过接口13
     * @author Allin Lin
     * @created 2020年11月18日 下午2:45:40
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findAddrInfoByA13(SqlFilter sqlFilter){
        List<Map<String,Object>> datas=Lists.newArrayList();
        int start=sqlFilter.getPagingBean().getStart();
        int size=sqlFilter.getPagingBean().getPageSize();
        int pNo=start/size+1;
        String jsnr=sqlFilter.getRequest().getParameter("jsnr");
        if(StringUtils.isEmpty(jsnr)){
            return datas;
        }
        //地址查询参数
        List<Map<String,Object>> list1= Lists.newArrayList();
        Map<String,Object> map1=new HashMap<>();
        map1.put("dsbm", AddrPara.ADDR_DSBM);
        map1.put("ALL_FULL_ADDR",jsnr);
        list1.add(map1);
        //分页参数
        List<Pages> list2= Lists.newArrayList();
        Pages pages=new Pages();
        pages.setPno(String.valueOf(pNo));
        pages.setPsize(String.valueOf(size));
        list2.add(pages);
        //组合参数
        Map<String,Object> postData=new HashMap<>();
        postData.put("datas",list1);
        postData.put("pages",list2);
        String postParam=JSON.toJSONString(postData, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse);
        String result=AddrUtils.findAddrInfo(postParam);
        Map<String,Object> resultMap= (Map<String, Object>) JSON.parse(result);
        String dataStr= StringUtil.getString(resultMap.get("datas"));
        String pageStr= StringUtil.getString(resultMap.get("pages"));
        datas=(List)JSON.parse(dataStr);
        List<Map<String,Object>> pageList=(List)JSON.parse(pageStr);
        int pageAll=0;
        if(CollectionUtils.isNotEmpty(pageList)){
            pageAll=Integer.parseInt(StringUtil.getString(pageList.get(0).get("tcount")));
        }
        sqlFilter.getPagingBean().setTotalItems(pageAll);
        return datas;
    }
}
