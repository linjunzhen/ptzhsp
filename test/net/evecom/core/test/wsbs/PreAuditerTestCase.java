/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.wsbs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.ExcelUtil;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.wsbs.dao.ServiceItemDao;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 描述 预审人员的测试用例
 * @author Flex Hu
 * @version 1.0
 * @created 2016年2月23日 下午5:17:06
 */
public class PreAuditerTestCase extends BaseTestCase {
    
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * data.get(4)
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 
     */
    @Resource
    private ServiceItemDao serviceItemDao;
    
    /**
     * 
     * 描述 导入预审人员数据
     * @author Flex Hu
     * @created 2016年2月23日 下午5:17:49
     */
    @Test
    public void testImpPreAuditer(){
        List<List<Object>> list = ExcelUtil.getExcelRowValues("d:/1.xls",1,0);
        for(List<Object> data:list){
            String itemCode = (String) data.get(4);
            String userNames = (String) data.get(5);
            if(StringUtils.isNotEmpty(itemCode)){
                Map<String,Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                        new String[]{"ITEM_CODE"},new Object[]{itemCode});
                if(serviceItem!=null&&StringUtils.isNotEmpty(userNames)){
                    String itemId = (String) serviceItem.get("ITEM_ID");
                    String[] uNames = userNames.trim().split(",");
                    for(String uName:uNames){
                        Map<String,Object> userInfo = serviceItemService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                                new String[]{"FULLNAME"},new Object[]{uName});
                        if(userInfo!=null){
                            String userId = (String) userInfo.get("USER_ID");
                            String sql = "select count(*) from T_WSBS_PREAUDITER T WHERE T.ITEM_ID=? AND T.USER_ID=?";
                            int count = Integer.parseInt(serviceItemDao.getObjectBySql(sql,
                                    new Object[]{itemId,userId}).toString());
                            if(count==0){
                                StringBuffer insertSql = new StringBuffer("insert into "
                                        + "T_WSBS_PREAUDITER(ITEM_ID,USER_ID)");
                                insertSql.append(" values(?,?) ");
                                serviceItemDao.executeSql(insertSql.toString(), new Object[]{itemId,userId});
                            }
                        }
                    }
                    
                }
            }
        }
    }
}
