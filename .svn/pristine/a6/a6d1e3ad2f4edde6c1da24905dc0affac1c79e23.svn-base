/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.bsfw.service.impl.SwbProvDataServiceImpl;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.model.Dbzhsp;
import net.evecom.platform.wsbs.service.SwbInterOfBusService;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 描述 省网办接口服务业务接口(推送总线数据）
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月13日 上午9:12:54
 */
@SuppressWarnings("rawtypes")
@Service("swbInterOfBusService")
public class SwbInterOfBusServiceImpl  extends BaseServiceImpl  implements SwbInterOfBusService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbProvDataServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SwbInterfaceDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Water Guo
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     */
    ExecutorService executor = Executors.newFixedThreadPool(10);
    /**
     * 转发总线数据到省网办
     */
    public void sendToProvince() {
        
        Connection conn = null;
        Dbzhsp dbzhsp=Dbzhsp.getDbzhsp();
        try {
            conn = DbUtil.getConnect(dbzhsp.getPtzhspDbUrl(), dbzhsp.getPtzhspDbUsername(), dbzhsp.getPtzhspDbPassword());
            //获取办件基本信息;
            List<Map<String, Object>> busDataList=dao.findBusDataList(conn);
            log.info("发送总线数据的条数："+busDataList.size()); 
            if(busDataList!=null){
                
            for(Map<String,Object> busData:busDataList){
                
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                Map<String,Object> provinceInfo=getProvinceInfoByBusData(busData);
                int  resultCode=saveOrUpdateDataToProvince(provinceInfo);
                String sql = "update t_prov_servicebusdata r set r.status = ?"
                        + " where r.uuid = ?";
                Connection conn1=null;
                conn1 = DbUtil.getConnect(dbzhsp.getPtzhspDbUrl(), dbzhsp.getPtzhspDbUsername(), dbzhsp.getPtzhspDbPassword());
                DriverManager.setLoginTimeout(5);//设置连接超时时间，5秒超时
                DbUtil.update(conn1, sql, new Object[] { resultCode, StringUtils.getString(busData.get("UUID")) }, false);
                //10数据成功后方可传80 85
                if(String.valueOf(resultCode).contentEquals("1")){
                //根据申报号  补传80 和85 的数据
                List<Map<String, Object>> gcAndjgList=dao.findGcAndJgDataList(conn1,StringUtils.getString(busData.get("SN")));
                log.info("发送总线过程和结果数据的条数："+gcAndjgList.size()+"申报号："+busData.get("SN")); 
                if(gcAndjgList!=null){
                for(Map<String,Object> gcAndjgData:gcAndjgList){
                    Map<String,Object> provinceGcInfo=getProvinceBCInfoByBusData(gcAndjgData);
                    int  result=saveOrUpdateDataToProvince(provinceGcInfo);
                    String resultsql = "update t_prov_servicebusdata r set r.status = ?"
                            + " where r.uuid = ?";
                    DbUtil.update(conn1, resultsql, new Object[] { result, StringUtils.getString(gcAndjgData.get("UUID")) }, false);
                    
                }
                }
                log.info("《---------------推送总线数据到省网办结束---------------》");
                }
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                           log.error(e.getMessage(),e);
                        }
            }
                    });
                }
            }
            
           
        }catch (SQLException e){
            log.error("综合审批平台数据库连接失败");
        } finally {
            DbUtils.closeQuietly(conn);
            
        }
    }

    /**
     * 获取省网推送数据字段
     * @param busData
     * @return
     */
    private Map<String,Object> getProvinceInfoByBusData(Map<String,Object> busData){
        Map<String,Object> provinceInfo=new HashMap<>();
        String unid= StringUtils.getString(busData.get("UUID"));
        String originalData=StringUtils.getString(busData.get("ORIGINAL_DATA"));
        //补充serviceid 以及部门信息
        String serviceId="";
        String serviceName="";
        String serviceCode="";
        String receivedeptname="";
        String receivedeptcode="";
        
        String areaCode=StringUtils.getString(busData.get("AREACODE"));
        String orinalItemCode=StringUtils.getString(busData.get("ORIGNAL_ITEM_CODE"));
       
        String swbitemcode= StringUtils.getString(busData.get("SWB_ITEM_CODE"));
       if(!swbitemcode.isEmpty()&&swbitemcode.contains("#")){
           swbitemcode=  swbitemcode.substring(0, swbitemcode.indexOf("#"));
           StringBuffer sql=new StringBuffer("SELECT T.STANDARD_CATALOG_NAME,SWB.*,d.* FROM T_SWB_DP_SERVICEITEMS SWB "); 
           sql.append("left join T_WSBS_SERVICEITEM T on SWB.STANDARD_CATALOG_ID=T.STANDARD_CATALOG_ID ");
           sql.append("left join t_msjw_system_department d on d.depart_code=t.ssbmbm ");
           sql.append("WHERE SWB.STANDARD_CATALOG_ID=? ");
           Map swbMap=  this.dao.getByJdbc(sql.toString(),new String[]{swbitemcode} );
           if(swbMap!=null){
               serviceName=StringUtils.getString(swbMap.get("STANDARD_CATALOG_NAME"));
               serviceId= StringUtils.getString(swbMap.get("SERVICEID"));
               serviceCode= StringUtils.getString(swbMap.get("SERVICECODE"));
               receivedeptcode=StringUtils.getString(swbMap.get("USC"));
               receivedeptname=StringUtils.getString(swbMap.get("DEPART_NAME")); 
           }
       }
       if(!swbitemcode.isEmpty()&&!swbitemcode.contains("#")){
         StringBuffer tsdsql= new StringBuffer("SELECT T.STANDARD_CATALOG_NAME,SWB.*,d.* FROM T_SWB_DP_SERVICEITEMS SWB "); 
             tsdsql.append("left join T_WSBS_SERVICEITEM T on SWB.STANDARD_CATALOG_ID=T.STANDARD_CATALOG_ID ");
             tsdsql.append("left join t_msjw_system_department d on d.depart_code=t.ssbmbm ");
             tsdsql.append("WHERE SWB.Servicecode=? ");
                 Map tsdMap=this.dao.getByJdbc(tsdsql.toString(),new String[]{swbitemcode} );
                 if(tsdMap!=null){
                 serviceId= StringUtils.getString(tsdMap.get("SERVICEID"));
                 serviceCode= StringUtils.getString(tsdMap.get("SERVICECODE"));
                 serviceName=StringUtils.getString(tsdMap.get("SERVICENAME"));
                 receivedeptcode=StringUtils.getString(tsdMap.get("USC"));
                 receivedeptname=StringUtils.getString(tsdMap.get("DEPART_NAME"));
                }
                
                 
       }
      //新增获取总线xml数据中的数据类型type值
        String type=getDataType(originalData);
        
        originalData= originalData.replace("<ServiceID></ServiceID>","<ServiceID>"+serviceId+"</ServiceID>");
        originalData= originalData.replace("<ReceiveDeptName></ReceiveDeptName>","<ReceiveDeptName>"+receivedeptname+"</ReceiveDeptName>");
        originalData= originalData.replace("<ReceiveDeptCode></ReceiveDeptCode>","<ReceiveDeptCode>"+receivedeptcode+"</ReceiveDeptCode>");
        originalData= originalData.replace("<ServiceName></ServiceName>","<ServiceName>"+serviceName+"</ServiceName>");
        originalData= originalData.replaceAll("<AreaCode></AreaCode>","<AreaCode>"+areaCode+"</AreaCode>");
       
        originalData= originalData.replace("<ServiceCode>"+orinalItemCode+"</ServiceCode>","<ServiceCode>"+serviceCode+"</ServiceCode>");
        
        //新增机构证件类型判断 修补数据
       /* String UnitIdcardType=xfdata(originalData);
        if("XYDM".contentEquals(UnitIdcardType)){
            originalData=originalData.replace("<UnitIdcardType>YYZZ</UnitIdcardType>","<UnitIdcardType>"+UnitIdcardType+"</UnitIdcardType>");
        }*/
        
        String routerXmlStr=getRouterXmlStr();
        //新增补充信息，回传backflow 标识
        String extendXmlStr=getExtendXmlStr();
        originalData=originalData.replace("xsd=\"1.0\">","xsd=\"1.0\">"+routerXmlStr);
        //总线下发生成的异常节点
        if(originalData.contains("<Extend/>")){
        originalData=originalData.replace("<Extend/>",extendXmlStr);
        }
        if(!originalData.contains("<Backflow>Y</Backflow>")){
        originalData=originalData.replace("<Extend></Extend>",extendXmlStr);
        }
        if(!originalData.contains("<Backflow>Y</Backflow>")){
            originalData=originalData.replace("</Body>",extendXmlStr+"</Body>");
        }
        if(originalData.contains("办件回流-市监局-工商一体化平台数据回流")&&originalData.contains("<Attr>")){
            String[] attr= originalData.split("<Attrs>");
            String[] attr1= originalData.split("</Attrs>");
            originalData= attr[0]+"<Attrs></Attrs>"+attr1[1];
        }
        if(originalData.contains("FormInfos")){
        String[] str= originalData.split("<FormInfos>");
        String[] str1= originalData.split("</FormInfos>");
        originalData= str[0]+"<FormInfos></FormInfos>"+str1[1];
        }
        
        //新增材料编码异常问题处理
       // originalData= xfclcode(originalData);
      log.info("处理后的总线数据========="+originalData);
        provinceInfo.put("UNID", unid);
        provinceInfo.put("fromAreaCode", AllConstant.FROM_AREA_CODE);
        provinceInfo.put("fromAreaName", AllConstant.FROM_AREA_NANE);
        provinceInfo.put("toAreaCode", AllConstant.TO_AREA_CODE);
        provinceInfo.put("toAreaName", AllConstant.TO_AREA_NAME);
        provinceInfo.put("ApplyFrom",AllConstant.SWB_DATA_FROM_CITYWS);
        //provinceInfo.put("TYPE", StringUtils.getString(busData.get("TYPE")));
        provinceInfo.put("TYPE",type);
        provinceInfo.put("xsdType", AllConstant.SWB_DATA_XSD);
        provinceInfo.put("copyRight", AllConstant.SWB_DATA_XSD);
        provinceInfo.put("CREATETIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        provinceInfo.put("ACTION",AllConstant.SWB_DATA_OPERATOR_INSERT);
        provinceInfo.put("CONTENT",originalData);
        return provinceInfo;
    }
    /**
     * 
     * 描述  补传80过程信息，85 结果信息
     * @author Yanisin Shi
     * @param busData
     * @return
     * create time 2021年9月22日
     */
    private Map<String,Object> getProvinceBCInfoByBusData(Map<String,Object> busData){
        Map<String,Object> provinceInfo=new HashMap<>();
        String unid= StringUtils.getString(busData.get("UUID"));
        String originalData=StringUtils.getString(busData.get("ORIGINAL_DATA"));
      //新增获取总线xml数据中的数据类型type值
        String type=getDataType(originalData);
        if(originalData.contentEquals("FormInfos")){
        String[] str= originalData.split("<FormInfos>");
        String[] str1= originalData.split("</FormInfos>");
        originalData= str[0]+"<FormInfos></FormInfos>"+str1[1];
        }
        String routerXmlStr=getRouterXmlStr();
        originalData=originalData.replace("xsd=\"1.0\">","xsd=\"1.0\">"+routerXmlStr);
        //新增材料编码异常问题处理
       // originalData= xfclcode(originalData);
        log.info("处理后的总线数据80/85========="+unid);
        provinceInfo.put("UNID", unid);
        provinceInfo.put("fromAreaCode", AllConstant.FROM_AREA_CODE);
        provinceInfo.put("fromAreaName", AllConstant.FROM_AREA_NANE);
        provinceInfo.put("toAreaCode", AllConstant.TO_AREA_CODE);
        provinceInfo.put("toAreaName", AllConstant.TO_AREA_NAME);
        provinceInfo.put("ApplyFrom",AllConstant.SWB_DATA_FROM_CITYWS);
        //新增修改 把type换成xml节点中下发的type
      //  provinceInfo.put("TYPE", StringUtils.getString(busData.get("TYPE")));
        provinceInfo.put("TYPE", type);
        provinceInfo.put("xsdType", AllConstant.SWB_DATA_XSD);
        provinceInfo.put("copyRight", AllConstant.SWB_DATA_XSD);
        provinceInfo.put("CREATETIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        provinceInfo.put("ACTION",AllConstant.SWB_DATA_OPERATOR_INSERT);
        provinceInfo.put("CONTENT",originalData);
        return provinceInfo;
    }
    /**
     * 
     * 描述         新增机构证件类型判断 修补数据
     * @author Yanisin Shi
     * @param originalData
     * create time 2021年9月6日
     * @return 
     */
    public  String  xfdata(String originalData) {
        Document document = XmlUtil.stringToDocument(originalData);
        Element root = document.getRootElement();
        Node caseNode = root.selectSingleNode("//Case/Body");
        String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
        Node routerNode=root.selectSingleNode("//Case/Router");
        String routerJson="";
        if(routerNode!=null){
            String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
            routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + router, "gb2312");
        }
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String proposer = root.selectSingleNode("//Case/Body/Proposer").asXML();
        // 定义服务事项
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + apasInfo, "gb2312");
        String proposerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + proposer, "gb2312");
       
        JSONObject obj = JSONObject.parseObject(proposerJson);

        String unitCode="";
        String UnitIdcardType="";
        JSONArray  unit=(JSONArray)((JSONObject) obj.get("Proposer")).get("Unit");
        if(unit!=null){   
            JSONObject  unitCodeJson= JSONObject.parseObject(unit.get(0).toString());
        if(unitCodeJson!=null){        
            unitCode=  unitCodeJson.get("UnitCode").toString().replace("[\"", "").replace("\"]", "");
            UnitIdcardType= unitCodeJson.get("UnitIdcardType").toString().replace("[\"", "").replace("\"]", "");
            }
        if("YYZZ".contentEquals(UnitIdcardType)&&unitCode.length()==18){
             UnitIdcardType="XYDM";
        }
        }
         return UnitIdcardType;
    }
    /**
     * 
     * 描述   获取总线xml数据中的type值
     * @author Yanisin Shi
     * @param originalData
     * @return
     */
    public  String  getDataType(String originalData) {
        Document document = XmlUtil.stringToDocument(originalData);
        Element root = document.getRootElement();
        Node caseNode = root.selectSingleNode("//Case/Body");
        String  type=caseNode.getParent().attributeValue("type");//省网总线节点中的type
         return type;
    }
    /**
     * 
     * 描述  新增总线数据申请材料编码修复
     * @author Yanisin Shi
     * @param originalData
     * @return
     */
    public  String  xfclcode(String originalData) {
        Document catadocument = XmlUtil.stringToDocument(originalData);
        Element cataroot = catadocument.getRootElement();
        Node AttrsNode = cataroot.selectSingleNode("//Case/Body/Attrs");
        if (AttrsNode != null) {
            String attrsXml = AttrsNode.asXML();
            String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrsXml, "UTF-8");
            JSONObject attrsObj = JSONObject.parseObject(attrsJson);
            JSONArray attr = ((JSONArray) ((JSONObject) attrsObj.get("Attrs")).get("Attr"));
            // 标准化事项目录材料唯一标识
            String code = "";
            //事项材料编码
            String serviceattrcode = "";
            for (int m = 0; m < attr.size(); m++) {
                JSONObject jObj = ((JSONObject) attr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("Code") != null) {
                        code = (String) ((JSONArray) (jObj.get("Code"))).get(0);
                        Map<String,Object> attrinfos=dao.getByJdbc("T_WSBS_SWBATTRSRELATIONS",new String[]{"DIRECTORYATTRUNID"}, new String[]{code});
                        if(attrinfos!=null && !attrinfos.isEmpty()){
                        if(attrinfos.get("serviceattrcode")!=null )    {
                            serviceattrcode=attrinfos.get("serviceattrcode").toString();
                            originalData=originalData.replace(code, serviceattrcode);
                        }
                        
                        }
                    }
                    

                }

            }

        }
        return originalData;

    }
    /**
     * 描述 向省网办报送信息保存到省网办的数据库
     *
     * @author Derek Zhang
     * @created 2015年10月21日 上午11:10:07
     */
    public int saveOrUpdateDataToProvince(Map<String, Object> dataMap) {
        int resultCode=1;
        StringBuffer str=new StringBuffer("select * from cityinfo where unid=?");
        StringBuffer sql = new StringBuffer("insert into cityinfo ");
        sql.append("(unid, type, createtime, xsdtype, copyright, action, fromareacode, ");
        sql.append(" fromareaname, toareacode, toareaname, applyfrom, content,TCHAR3)  values ");
        sql.append("( ?, ?, to_date(?,'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)");
        //往省网中间库推送数据
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_XMXX });
        String dbUrl = (String) dataAbutment.get("DB_URL");
        String dbUserName = (String) dataAbutment.get("DB_USERNAME");
        String dbPass = (String) dataAbutment.get("DB_PASSWORD");
        Connection conn = null;
        try{
            conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
            List result=DbUtil.findBySql(conn, str.toString(),new Object[]{dataMap.get("UNID")},false);
            if(result.size()>0){
                resultCode=1;
            }else{
                
            
            DbUtil.update(conn,  sql.toString(),new Object[] { dataMap.get("UNID"), dataMap.get("TYPE"),
                    DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"),
                    dataMap.get("xsdType"), dataMap.get("copyRight"), dataMap.get("ACTION"),
                    dataMap.get("fromAreaCode"),  dataMap.get("fromAreaName"), dataMap.get("toAreaCode"),
                    dataMap.get("toAreaName"), dataMap.get("ApplyFrom"),StringUtils.getString(dataMap.get("CONTENT")).getBytes() ,"省总线数据"});
            }
            }catch (Exception e){
            log.error(String.format("处理t_prov_servicebusdata的uuid=%s失败,失败原因%s",dataMap.get("UNID"),e.getMessage()));
            resultCode=2;
        }
        return resultCode;
    }

    public static String getRouterXmlStr(){
        Map<String,Object> caseMap=new HashMap<>();
        Map<String,Object> router=new HashMap<>();
        caseMap.put("Router",router);
        router.put("FromAreaCode",AllConstant.FROM_AREA_CODE);
        router.put("FromAreaName", AllConstant.FROM_AREA_NANE);
        router.put("ToAreaCode",AllConstant.TO_AREA_CODE);
        router.put("ToAreaName",AllConstant.TO_AREA_NAME);
        router.put("Time",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String routerStr=multilayerMapToXml(caseMap,false);
        routerStr=routerStr.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml>","");
        routerStr=routerStr.replace("</xml>","");
        return routerStr;
    }
   /**
    * 
    * 描述  新增扩展节点  
    * @author Yanisin Shi
    * @return
    * create time 2021年8月2日
    */
    public static String getExtendXmlStr(){
        Map<String,Object> caseMap=new HashMap<>();
        Map<String,Object> extend=new HashMap<>();
        caseMap.put("Extend",extend);
        extend.put("IsAgentService","");
        extend.put("AgentServiceCharge", "");
        extend.put("Backflow","Y");
        extend.put("GzcnspFlag","");
        extend.put("GzcnspFileUnid","");
        extend.put("GzcnspFileName","");
        extend.put("AgentSourceArea","");
        extend.put("CertifiedIsWriteOff","");
        String extendStr=multilayerMapToXml(caseMap,false);
        extendStr=extendStr.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml>","");
        extendStr=extendStr.replace("</xml>","");
        return extendStr;
    }


    /**
     * (多层)map转换为xml格式字符串
     *
     * @param map 需要转换为xml的map
     * @param isCDATA 是否加入CDATA标识符 true:加入 false:不加入
     * @return xml字符串
     */
    public static String multilayerMapToXml(Map<String, Object> map, boolean isCDATA){
        String parentName = "xml";
        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = recursionMapToXml(doc.getRootElement(), parentName, map, isCDATA);
        return formatXML(xml);
    }

    /**
     * multilayerMapToXml核心方法，递归调用
     *
     * @param element 节点元素
     * @param parentName 根元素属性名
     * @param map 需要转换为xml的map
     * @param isCDATA 是否加入CDATA标识符 true:加入 false:不加入
     * @return xml字符串
     */
    @SuppressWarnings("unchecked")
    private static String recursionMapToXml(Element element, String parentName, Map<String, Object> map, boolean isCDATA) {
        Element xmlElement = element.addElement(parentName);
        for(Map.Entry<String,Object> entry:map.entrySet()){
           String key=entry.getKey();
            Object obj = map.get(key);
            if (obj instanceof Map) {
                recursionMapToXml(xmlElement, key, (Map<String, Object>)obj, isCDATA);
            } else {
                String value = obj == null ? "" : obj.toString();
                if (isCDATA) {
                    xmlElement.addElement(key).addCDATA(value);
                } else {
                    xmlElement.addElement(key).addText(value);
                }
            }
        }
        return xmlElement.asXML();
    }
    /**
     * 格式化xml,显示为容易看的XML格式
     *
     * @param xml 需要格式化的xml字符串
     * @return
     */
    public static String formatXML(String xml) {
        String requestXML = null;
        try {
            // 拿取解析器
            SAXReader reader = new SAXReader();
            Document document = reader.read(new StringReader(xml));
            if (null != document) {
                StringWriter stringWriter = new StringWriter();
                // 格式化,每一级前的空格
                OutputFormat format = new OutputFormat("    ", true);
                // xml声明与内容是否添加空行
                format.setNewLineAfterDeclaration(false);
                // 是否设置xml声明头部
                format.setSuppressDeclaration(false);
                // 是否分行
                format.setNewlines(true);
                XMLWriter writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                writer.close();
                requestXML = stringWriter.getBuffer().toString();
            }
            return requestXML;
        } catch (Exception e) {
            log.error("格式化xml，失败 --> {}", e);
            return null;
        }
    }

    /**
     * (多层)xml格式字符串转换为map
     *
     * @param xml xml字符串
     * @return 第一个为Root节点，Root节点之后为Root的元素，如果为多层，可以通过key获取下一层Map
     */
    public static Map<String, Object> multilayerXmlToMap(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error("xml字符串解析，失败 --> {}", e);
        }
        Map<String, Object> map = new HashMap<>();
        if (null == doc) {
            return map;
        }
        // 获取根元素
        Element rootElement = doc.getRootElement();
        recursionXmlToMap(rootElement,map);
        return map;
    }

    /**
     * multilayerXmlToMap核心方法，递归调用
     *
     * @param element 节点元素
     * @param outmap 用于存储xml数据的map
     */
    @SuppressWarnings("unchecked")
    private static void recursionXmlToMap(Element element, Map<String, Object> outmap) {
        // 得到根元素下的子元素列表
        List<Element> list = element.elements();
        int size = list.size();
        if (size == 0) {
            // 如果没有子元素,则将其存储进map中
            outmap.put(element.getName(), element.getTextTrim());
        } else {
            // innermap用于存储子元素的属性名和属性值
            Map<String, Object> innermap = new HashMap<>();
            // 遍历子元素
            for(Element childElement:list){
                recursionXmlToMap(childElement, innermap);
            }
            outmap.put(element.getName(), innermap);
        }
    }
}
