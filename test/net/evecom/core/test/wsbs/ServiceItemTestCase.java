/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.wsbs;

import java.io.File;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.bsfw.service.DataAbutmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月13日 下午2:24:28
 */
public class ServiceItemTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ServiceItemTestCase.class);
    /**
     * 
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 
     */
    @Resource
    private DataAbutmentService dataAbutmentService;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    
    
    @Test
    public void updateBdlcid(){
        List<List<Object>> datas= ExcelUtil.getExcelRowValues("d:/1.xls", 1, 2);
        StringBuffer codes = new StringBuffer("");
        for(List<Object> data:datas){
            codes.append("'").append(data.get(0).toString()).append("',");
        }
        codes.deleteCharAt(codes.length()-1);
        log.info(codes.toString());
    }
    
    public static void main(String[] args){
        String content =  FileUtil.getContentOfFile("d:/服务事项信息示例.xml");
        String json = XmlUtil.xml2Json(content,"UTF-8");
        log.info(json);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年10月13日 下午2:26:56
     */
    @Test
    public void saveDatasFromProvince(){
        Map<String,Object> dataAbutment = serviceItemService.getByJdbc("T_BSFW_DATAABUTMENT",
                new String[]{"AABUT_ID"},new Object[]{"4028b9d4505b2e2001505b3256300001"});
        serviceItemService.saveDatasFromProvince(dataAbutment);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @throws SQLException 
     * @created 2015年10月16日 上午10:43:56
     */
    @Test
    public void saveOrUpdateFromProvinceXml() throws SQLException{
        String xml =  FileUtil.getContentOfFile("d:/服务事项信息示例.xml");
        Document document = XmlUtil.stringToDocument(xml);
        Element root = document.getRootElement();
        //定义服务事项
        Map<String,Object> serviceItem = new HashMap<String,Object>();
        //设置服务事项编码
        serviceItem.put("ITEM_CODE",root.selectSingleNode("//ServiceInfo/SrcCode").getText());
        //设置服务事项名称
        serviceItem.put("ITEM_NAME",root.selectSingleNode("//ServiceInfo/ServiceName").getText());
        //设置服务事项性质
        serviceItem.put("SXXZ",root.selectSingleNode("//ServiceInfo/AddType").getText());
        //获取办件类型
        String serviceType = root.selectSingleNode("//ServiceInfo/ServiceType").getText();
        if(StringUtils.isNotEmpty(serviceType)){
            serviceItem.put("SXLX",dictionaryService.getDicCode("ServiceItemType",serviceType));
        }
        //获取事项备注说明
        String iremark = root.selectSingleNode("//ServiceInfo/Iremark").getText();
        if(StringUtils.isNotEmpty(iremark)){
            serviceItem.put("BZSM",iremark);
        }
        //获取行使依据
        String xsyj = root.selectSingleNode("//ServiceInfo/According").getText();
        if(StringUtils.isNotEmpty(xsyj)){
            serviceItem.put("XSYJ",xsyj);
        }
        //获取申请条件
        String sqtj = root.selectSingleNode("//ServiceInfo/Applyterm").getText();
        if(StringUtils.isNotEmpty(sqtj)){
            serviceItem.put("SQTJ",sqtj);
        }
        //获取办理流程
        String bllc = root.selectSingleNode("//ServiceInfo/Proceflow").getText();
        if(StringUtils.isNotEmpty(bllc)){
            serviceItem.put("BLLC",bllc);
        }
        //获取承诺期限工作日
        String promiseDay = root.selectSingleNode("//ServiceInfo/PromiseDay").getText();
        if(StringUtils.isNotEmpty(promiseDay)){
            serviceItem.put("CNQXGZR",Integer.parseInt(promiseDay));
        }
        //获取承诺时限说明
        String promisDayInfo = root.selectSingleNode("//ServiceInfo/PromisDayInfo").getText();
        if(StringUtils.isNotEmpty(promisDayInfo)){
            serviceItem.put("CNQXSM",promisDayInfo);
        }
        //获取法定时限说明
        String fdqx= root.selectSingleNode("//ServiceInfo/Ifdsx").getText();
        if(StringUtils.isNotEmpty(fdqx)){
            serviceItem.put("FDQX",fdqx);
        }
        //获取办理地点
        String acceptAddress = root.selectSingleNode("//ServiceInfo/AcceptAddress").getText();
        if(StringUtils.isNotEmpty(acceptAddress)){
            serviceItem.put("BLDD",acceptAddress);
        }
        //获取办理部门
        String leadDept = root.selectSingleNode("//ServiceInfo/LeadDept").getText();
        if(StringUtils.isNotEmpty(leadDept)){
            serviceItem.put("BLBM",leadDept);
        }
        //获取办公时间
        String officeTime = root.selectSingleNode("//ServiceInfo/OfficeTime").getText();
        if(StringUtils.isNotEmpty(officeTime)){
            serviceItem.put("BGSJ",officeTime);
        }
        //获取实施主体名称
        String ssztmc = root.selectSingleNode("//ServiceInfo/DeptName").getText();
        if(StringUtils.isNotEmpty(ssztmc)){
            serviceItem.put("SSZTMC",ssztmc);
        }
        //获取联系电话
        String LXDH = root.selectSingleNode("//ServiceInfo/ContactPhone").getText();
        if(StringUtils.isNotEmpty(LXDH)){
            serviceItem.put("LXDH",LXDH);
        }
        //获取监督电话
        String JDDH = root.selectSingleNode("//ServiceInfo/MonitorComplain").getText();
        if(StringUtils.isNotEmpty(JDDH)){
            serviceItem.put("JDDH",JDDH);
        }
        //获取是否收费
        String SFSF = root.selectSingleNode("//ServiceInfo/ChargeFlag").getText();
        if(StringUtils.isNotEmpty(SFSF)){
            serviceItem.put("SFSF",dictionaryService.getDicCode("YesOrNo",SFSF));
        }
        //获取收费标准及依据
        String SFBZJYJ = root.selectSingleNode("//ServiceInfo/ChargeStandard").getText();
        if(StringUtils.isNotEmpty(SFBZJYJ)){
            serviceItem.put("SFBZJYJ",SFBZJYJ);
        }
        //获取数量限制说明
        String SLXZSM = root.selectSingleNode("//ServiceInfo/CountLimit").getText();
        if(StringUtils.isNotEmpty(SLXZSM)){
            serviceItem.put("SLXZSM",SLXZSM);
        }
        //获取收费方式
        String SFFS = root.selectSingleNode("//ServiceInfo/ChargeType").getText();
        if(StringUtils.isNotEmpty(SFFS)){
            serviceItem.put("SFFS",SFFS);
        }
        
    }
    
}
