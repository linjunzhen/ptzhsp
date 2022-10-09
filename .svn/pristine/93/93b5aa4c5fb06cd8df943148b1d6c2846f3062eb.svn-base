/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.demo;

import com.alibaba.fastjson.JSON;
import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.AjaxJson;
import net.evecom.platform.bdc.service.BdcQueryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述不动产测试接口
 * @version 1.0
 * @author Water Guo
 * @created 2019年01月22日 下午12:30:02
 */
public class BdcTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcTestCase.class);
    /**
     * bdcQueryService
     */
    @Resource
    private BdcQueryService bdcQueryService;

    /**
     * 不动产委托备案接口服务
     */
    @Test
    public void testEntruste(){
        Map<String, Object> map = new HashMap<String, Object>();
        //不动产单元号，三个参数任意赋值一个不为空就行
        map.put("bdcdyh", "");
        //房屋编码
        map.put("fwbm", "");
        //不动产权证号
        map.put("bdcqzh", "闽(2017)平潭不动产权第0008062号");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "entrusteUrl");
        handAjaxJson(ajaxJson);
    }
    /**
     * 不动产预告档案查询接口服务
     */
    @Test
    public void testAnnounce(){
        Map<String, Object> map = new HashMap<String, Object>();
        //不动产单元号，8个参数任意赋值一个不为空就行
        map.put("bdcdyh", "351001012009GB00571F00070349");
        //不动产登记证明号
        map.put("bdcdjzmh", "");
        //权利人姓名
        map.put("qlr", "");
        //权利人证件号码
        map.put("qlrzjh", "");
        //义务人
        map.put("ywr", "");
        //义务人证件号码
        map.put("ywrzjh", "");
        //业务号
        map.put("ywh", "");
        //坐落
        map.put("bdczl", "");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "announceUrl");
        handAjaxJson(ajaxJson);
    }
    /**
     * 不动产委托备案接口服务
     */
    @Test
    public void testContract(){
        Map<String, Object> map = new HashMap<String, Object>();
        //买卖合同号，8个参数任意赋值一个不为空就行
        map.put("fwmmhth", "");
        //买方姓名
        map.put("msfxm", "");
        //买方证件号
        map.put("msfzjhm", "");
        //卖方姓名
        map.put("zrfxm", "");
        //房屋坐落
        map.put("zl", "平潭综合实验区兴港中路（原金井二路）西侧，诚意路（原天大山北路）南侧正荣·悦玺3#、3-1#楼1903室");
        //预售许可证号
        map.put("ysxkzbh", "");
        //项目名称
        map.put("xmmc", "");
        //备案类型
        map.put("balx", "");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "contractUrl");
        handAjaxJson(ajaxJson);
    }
    /**
     * ajaxJson查询结果解析
     * @param ajaxJson
     */
    private void handAjaxJson(AjaxJson ajaxJson) {
        String listJson=ajaxJson.getJsonString();
        boolean flag=ajaxJson.isSuccess();
        if(flag) {
            if(StringUtils.isNotEmpty(ajaxJson.getJsonString())){
                List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) JSON.parse(listJson);
                for (Map<String, Object> queryData : list) {
                    //不动产单元号
                    String bdcdyh = queryData.get("BDCDYH") == null ? "" : queryData.get("BDCDYH").toString();
                    log.info(bdcdyh);
                    System.out.println(bdcdyh);
                    //不动产权证号
                    String bdcqzh = queryData.get("BDCQZH") == null ? "" : queryData.get("BDCQZH").toString();
                    log.info(bdcqzh);
                }
            }else{
                //查询无数据
                 log.info(ajaxJson.getMsg());
            }
        }else{
            //查询接口异常
            log.info(ajaxJson.getMsg());
        }
    }

}
