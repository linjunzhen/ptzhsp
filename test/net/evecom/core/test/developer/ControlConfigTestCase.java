/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.developer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.model.TableColumn;
import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.developer.dao.ControlConfigDao;
import net.evecom.platform.developer.service.ControlConfigService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月18日 下午3:17:36
 */
public class ControlConfigTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ControlConfigTestCase.class);
    /**
     * controlConfigService
     */
    @Resource
    private ControlConfigService controlConfigService;
    /**
     * controlConfigDao
     */
    @Resource
    private ControlConfigDao controlConfigDao;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月18日 下午3:18:31
     */
    @Test
    public void getGenCodeByMissionId(){
        String code = controlConfigService.getGenCodeByMissionId("402881e54890fff50148910d63300003");
        log.info(code);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月18日 下午5:17:21
     */
    @Test
    public void testGetColumns(){
        String sql = "select T.User_Id,T.USERNAME,T.FULLNAME,T.MOBILE,"
                + "T.STATUS,D.DEPART_NAME from T_MSJW_SYSTEM_SYSUSER T";
        sql+=" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON T.DEPART_ID=D.DEPART_ID ";
        List<TableColumn> columns = controlConfigDao.findQueryResultColsBySql(sql);
        for(TableColumn column:columns){
            log.info("列名称:"+column.getColumnName()+","+column.getComments());
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月23日 下午5:37:23
     */
    @Test
    public void genQueryConfigHtml(){
        //获取配置的查询条件
        List<Map<String,Object>> queryConfigs = controlConfigDao.
                findByParentId("402881e54890fff50148911fdc240020");
        String code=  controlConfigService.genQueryConfigHtml(queryConfigs, "SysLog");
        log.info(code);
    }
    /**
     * 
     * 描述测试显示行
     * @author Flex Hu
     * @created 2014年9月28日 上午11:06:43
     */
    public static void main(String[] args){
        List<String> datas = new ArrayList<String>();
        for(int i =0;i<9;i++){
            datas.add(String.valueOf(i));
        }
        int formColumnNum =3;
        StringBuffer content = new StringBuffer("");
        if(formColumnNum==1){
            for(String data:datas){
                content.append("<tr><td>").append(data).append("</td></tr>\n");
            }
        }else if(formColumnNum==2){
            if(datas.size()%2!=0){
                datas.add("bu");
            }
            for(int i=0;i<datas.size();i++){
                if(i%2==0){
                    content.append("<tr>");
                }
                content.append("<td>").append(datas.get(i)).append("</td>");
                if(i%2!=0){
                    content.append("</tr>\n");
                }
            }
        }else if(formColumnNum==3){
            if(datas.size()%3==1){
                datas.add("bu");
                datas.add("bu");
            }else if(datas.size()%3==2){
                datas.add("bu");
            }
            for(int i=0;i<datas.size();i++){
                if(i%3==0){
                    content.append("<tr>");
                }
                content.append("<td>").append(datas.get(i)).append("</td>");
                if(i%3==2){
                    content.append("</tr>\n");
                }
            }
        }
        
        log.info(content);
    }
}
