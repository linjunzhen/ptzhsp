/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.demo.dao.ProductDao;
import net.evecom.platform.demo.service.ProductService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年10月5日 下午12:30:02
 */
public class ProductTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ProductTestCase.class);
    /**
     * productService
     */
    @Resource
    private ProductService productService;
    /**
     * ProductDao
     */
    @Resource
    private ProductDao productDao;
    
    @Test
    public void testGet(){
        Map<String,Object> product = productDao.getByJdbc("T_MSJW_DEMO_PRODUCT",
                new String[]{"PRODUCT_ID"},new Object[]{1});
        log.info(product.get("PRODUCT_NAME"));
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年10月5日 下午12:30:35
     */
    @Test
    public void testSave(){
       /* Map<String,Object> product = new HashMap<String,Object>();
        product.put("PRODUCT_CODE","001XX");
        product.put("PRODUCT_NAME","Dior韩式西装");
        product.put("MANU_DATE","2014-10-05");
        product.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        product.put("PRODUCT_PRICE", 288.88);
        product.put("PRODUCT_STATUS", "1");
        String id = productDao.saveOrUpdate(product, "T_MSJW_DEMO_PRODUCT", null);
        log.info("Idea:"+id);*/
        for(int i=0;i<=10;i++){
            Map<String,Object> product = new HashMap<String,Object>();
            product.put("PRODUCT_CODE","001XX"+i);
            product.put("PRODUCT_NAME","Dior韩式西装");
            product.put("MANU_DATE","2014-10-05");
            product.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            product.put("PRODUCT_PRICE", 288.88);
            product.put("PRODUCT_STATUS", "1");
            String id = productDao.saveOrUpdate(product, "T_MSJW_DEMO_PRODUCT", null);
            log.info("Idea:"+id);
        }
        
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年10月5日 下午12:34:37
     */
    @Test
    public void testSaveType(){
        Map<String,Object> type = new HashMap<String,Object>();
        type.put("TYPE_NAME","内衣");
        type.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        productService.saveOrUpdateTreeData("402881e548de9c4a0148de9c4a080000", type, "T_MSJW_DEMO_PRODUCTTYPE",null);
    }
}
