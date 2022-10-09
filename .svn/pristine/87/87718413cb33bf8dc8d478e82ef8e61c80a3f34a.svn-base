/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.test.nanwei.ProvinceInfoTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.platform.system.dao.DictionaryDao;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月7日 下午3:45:39
 */
public class DictionaryTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(DictionaryTestCase.class);
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     */
    @Resource
    private DictionaryDao dictionaryDao;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月7日 下午3:46:13
     */
    @Test
    public void findDatasForSelect(){
        List<Map<String,Object>> list = dictionaryService.findDatasForSelect("FlowFormType");
        for(Map<String,Object> data:list){
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
                log.info(e.getKey());
            }
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年10月19日 下午2:54:55
     */
    @Test
    public void getDicCode(){
        String code = dictionaryDao.getDicCode("ServiceItemType","承诺件1");
        log.info("code:"+code);
    }
    
    /**
     * 
     * 描述 导入Excel
     * @author Flex Hu
     * @created 2016年5月4日 下午2:12:20
     */
    @Test
    public void testImp(){
        List<List<Object>> listDatas = ExcelUtil.getExcelRowValues("d:/processcode_20160504.xls", 1, 1);
        int dicSn = 1;
        for(List<Object> data:listDatas){
            String dicCode = (String) data.get(0);
            String dicName = (String) data.get(1);
            Map<String,Object> dic = new HashMap<String,Object>();
            dic.put("TYPE_ID", "4028b8a7547a0e3001547a0ee7d70001");
            dic.put("DIC_CODE",dicCode);
            dic.put("DIC_NAME",dicName);
            dic.put("DIC_SN",dicSn);
            dic.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dic.put("TYPE_CODE","GCBM");
            dictionaryDao.saveOrUpdate(dic,"T_MSJW_SYSTEM_DICTIONARY",null);
            dicSn++;
        }
    }
}
