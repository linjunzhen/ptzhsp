/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.sql.Connection;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bsfw.dao.SwbProvDataDao;
import net.evecom.platform.bsfw.service.SwbItemDataService;
import net.evecom.platform.bsfw.service.SwbProvAttrService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.dao.ApplyMaterDao;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BillRightService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.DepartCatalogService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import net.sf.ehcache.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月26日 下午3:21:23
 */
@Service("swbItemDataService")
public class SwbITemDataServiceImpl extends BaseServiceImpl implements SwbItemDataService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbITemDataServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SwbProvDataDao dao;
    /**
     * 
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入billRightService
     */
    @Resource
    private BillRightService billRightService;
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入Service
     */
    @Resource
    private DepartCatalogService departCatalogService;
    /**
     * 引入Service 
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service DepartServiceItemService
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 
     */
    @Resource
    private SwbProvAttrService swbProvAttrService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterDao applyMaterDao;
    /**
     * 属性ptzhspDbUrl
     */
    private String ptzhspDbUrl;
    /**
     * 属性ptzhspDbUsername
     */
    private String ptzhspDbUsername;
    /**
     * 属性ptzhspDbPassword
     */
    private String ptzhspDbPassword;

    /**
     * 描述:省网目录ID
     *
     * @author Madison You
     * @created 2021/2/26 11:05:00
     * @param
     * @return
     */
    private String catalogInfoUnid;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 获取省网办数据中需要被启动流程的数据列表
     * @author Flex Hu
     * @created 2016年1月26日 下午3:40:21
     * @return
     */
    public List<Map<String,Object>> findNeedToCreate(){
        StringBuffer sql = new StringBuffer("select t.* from PROVINCEITEMINFO t ");
        sql.append(" where t.oper_status = 0 and rownum<50 ");
//        sql.append(" and t.unid = '23E4393827E722D84C65800F23A3CC38' ");
        //D049E0E10609A060DE02628EDCBA2192  8DB13550C656058CD44AA4C49529AD5B 
        //
        sql.append(" ORDER BY t.createtime ASC  ");
        return dao.findBySql(sql.toString(), null,null);
    }

    /**
     * 
     * 描述 根据id获取省网办数据中需要被启动流程的数据列表
     * @author Kester Chen
     * @created 2018-5-16 上午9:06:17
     * @param billId
     * @return
     */
    public List<Map<String, Object>> findNeedToCreateByIds(String unid) {
        // TODO Auto-generated method stub
        unid=unid.replace("&#39;", "'");
        StringBuffer sql = new StringBuffer("select t.* from PROVINCEITEMINFO t ");
        sql.append(" where t.oper_status = 0 ");
        sql.append(" and t.unid in ( ");
        sql.append(unid);
        sql.append(" ) ");
        sql.append(" ORDER BY t.createtime ASC  ");
        return dao.findBySql(sql.toString(), null,null);
    }
    /**
     * 
     * 描述 根据id获取省网办数据中需要被启动流程的数据列表
     * @author Kester Chen
     * @created 2018-5-16 上午9:06:17
     * @param billId
     * @return 
     * @return
     */
    public  void deleteSwbItemByIds(String unid) {
        // TODO Auto-generated method stub
        unid=unid.replace("&#39;", "'");
        unid=unid.replace("'", "");
        String[] ary = unid.split(",");
        for (String id: ary) {
            updateDataStatus(id, 331);
        }
    }

    /**
     *
     * 描述 启动省网办传输过来的流程
     * @author Flex Hu
     * @created 2016年1月27日 下午3:56:21
     * @param swbData
     * @return
     * @throws Exception
     */
    public Map<String,Object> createItem(Map<String,Object> swbData) throws Exception{
        Connection ptzhspconn = null;
//        String urlString = this.getPtzhspDbUrl();
//        String Username = this.getPtzhspDbUsername();
//        String Password = this.getPtzhspDbPassword();
        ptzhspconn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(),
                this.getPtzhspDbPassword());
        //获取省网下发信息
        Document document = XmlUtil.stringToDocument((String) swbData.get("content"));
        Element root = document.getRootElement();
        String serviceInfo = root.selectSingleNode("//Case/Body/ServiceInfo").asXML();
        String serviceAccepts = root.selectSingleNode("//Case/Body/ServiceInfo/ServiceAccepts").asXML();
        String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
        String ChargeDetails = root.selectSingleNode("//Case/Body/ServiceInfo/ChargeDetails").asXML();
//        System.out.println("UNID==================================="+swbData.get("UNID").toString());
        saveRecLog("UNID==="+swbData.get("UNID").toString());
//        String steps = root.selectSingleNode("//Case/Body/Steps").asXML();
        // 定义服务事项
        String serviceInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceInfo, "UTF-8");
//        String stepsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + steps, "UTF-8");
        String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
        String ChargeDetailsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + ChargeDetails, "UTF-8");
        String serviceAcceptsJson =
                XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceAccepts, "UTF-8");
        JSONObject serviceInfOobj = JSONObject.parseObject(serviceInfoJson);

        String unid = getObjStringInfo(serviceInfOobj, "Unid");
        String parentUnid = getObjStringInfo(serviceInfOobj, "ParentUnid");
        String parentName = getObjStringInfo(serviceInfOobj, "ParentName");
        String addType = getObjStringInfo(serviceInfOobj, "AddType");
        String addTypeCode = getAddTypeCode(addType);
        String deptName = getObjStringInfo(serviceInfOobj, "DeptName");
        String DeptCode = getObjStringInfo(serviceInfOobj, "DeptCode");
        String depatId = getDeptId(deptName,DeptCode);
//         清单名称  ParentName  清单性质  AddType  所属部门 DeptName 是否公开  1 ?? 自贸区属性  1 ??

        String projidMain = getObjStringInfo(serviceInfOobj, "ProjidMain");
        String Infoprojid = getObjStringInfo(serviceInfOobj, "Infoprojid");
        List<Map<String, Object>>swbCInfo = isExistSwbCatalogCode(projidMain);
        List<Map<String, Object>>swbIInfo = isExistSwbItemCode(Infoprojid);
        List<Map<String, Object>>swbRightBillCInfo = isExistSwbRightBillC(parentUnid);
        List<Map<String, Object>>swbRightBillIInfo = isExistSwbRightBillI(unid);
        int isExistSwbC = swbCInfo.size();
        int isExistSwbI = swbIInfo.size();
        int isExistRightBillC = swbRightBillCInfo.size();
        int isExistRightBillI = swbRightBillIInfo.size();
        String catalogCode = "";
        List<Map<String, Object>>CInfo =null;
        if (0<isExistSwbC) {
            catalogCode = swbCInfo.get(0).get("catalog_code")==null?
                    "":swbCInfo.get(0).get("catalog_code").toString();
            CInfo = getCatalogInfo(catalogCode);
        }
        int isExistSwbCReally = CInfo.size();

        String serviceName = getObjStringInfo(serviceInfOobj, "ServiceName");
        String inproperty = getObjStringInfo(serviceInfOobj, "Inproperty");

        String inpropertyCode = "";
        if (StringUtils.isEmpty(inproperty)) {
            inpropertyCode = "W";
        }else {
            inpropertyCode = getInpropertyCode(inproperty);
        }
        if (0==isExistSwbI&&0==isExistSwbC) {
            //生成或更新权责清单目录
            if (0==isExistRightBillC) {
                creatRightbillCatalog(parentUnid,parentName,addTypeCode,depatId,ptzhspconn);
            }else {
                updateRightbillCatalog(parentUnid,parentName,addTypeCode,depatId,ptzhspconn);
            }
            //生成或更新权责清单子项
            if (0==isExistRightBillI) {
                creatRightbillItem(unid,depatId,serviceName,parentUnid,inpropertyCode,ptzhspconn);
            }else {
                updateRightbillItem(unid,depatId,serviceName,parentUnid,inpropertyCode,ptzhspconn);
            }
            //生成或更新目录
            if (0==isExistSwbCReally) {
                creatCatalog(parentUnid,parentName,addTypeCode,depatId,projidMain,ptzhspconn);
            }else {
                updateCatalog(CInfo,parentUnid,parentName,addTypeCode,depatId,projidMain,ptzhspconn);
            }

            String billUnid = unid;
            creatOrUpdateItem(root, attrsJson, serviceAcceptsJson,
                    serviceInfOobj, unid,parentUnid, Infoprojid, billUnid,
                    (String) swbData.get("UNID"),ChargeDetailsJson,null);

            //更新操作状态
            updateDataStatus(swbData.get("UNID").toString(), 1);

        }else if (0==isExistSwbI&&0<isExistSwbC) {
            List<Map<String, Object>>rightbillInfo = getRightbillCatalog(projidMain);
            String rightbillCatalogId = "";
            String billUnid = "";
            if (rightbillInfo.size()>0) {
                rightbillCatalogId = rightbillInfo.get(0).get("BILL_ID")==null?
                        "":rightbillInfo.get(0).get("BILL_ID").toString();
                //更新权责清单目录
                updateRightbillCatalog(parentUnid,parentName,addTypeCode,depatId,ptzhspconn);
                //生成或更新权责清单子项
                if (0==isExistRightBillI) {
                    creatRightbillItem(unid,depatId,serviceName,parentUnid,inpropertyCode,ptzhspconn);
                }else {
                    updateRightbillItem(unid,depatId,serviceName,parentUnid,inpropertyCode,ptzhspconn);
                }
                billUnid = unid;
                //生成或更新目录
                if (0==isExistSwbCReally) {
                    creatCatalog(parentUnid,parentName,addTypeCode,depatId,projidMain,ptzhspconn);
                }else {
                    updateCatalog(CInfo,parentUnid,parentName,addTypeCode,depatId,projidMain,ptzhspconn);
                }

                creatOrUpdateItem(root, attrsJson, serviceAcceptsJson,
                        serviceInfOobj, unid,parentUnid,  Infoprojid, billUnid ,
                        (String)swbData.get("UNID"),ChargeDetailsJson,null);

                //更新操作状态
                updateDataStatus(swbData.get("UNID").toString(), 1);
            }else {
                //更新操作状态
                updateDataStatus(swbData.get("UNID").toString(), 9);
            }
        }else if (0<isExistSwbI&&0<isExistSwbC) {
            List<Map<String, Object>>rightbillInfo = getRightbillItem(Infoprojid);
            String rightbillItemId = "";
            if (rightbillInfo.size()>0) {
                rightbillItemId = rightbillInfo.get(0).get("BILL_ID")==null?
                        "":rightbillInfo.get(0).get("BILL_ID").toString();
            }else {
                rightbillItemId=unid;
            }
            //更新权责清单目录
            updateRightbillCatalog(parentUnid,parentName,addTypeCode,depatId,ptzhspconn);
            //更新权责清单子项
            updateRightbillItem(unid,depatId,serviceName,parentUnid,inpropertyCode,ptzhspconn);
            //生成或更新目录
            if (0==isExistSwbCReally) {
                creatCatalog(parentUnid,parentName,addTypeCode,depatId,projidMain,ptzhspconn);
            }else {
                updateCatalog(CInfo,parentUnid,parentName,addTypeCode,depatId,projidMain,ptzhspconn);
            }

            String billUnid = rightbillItemId;
            creatOrUpdateItem(root, attrsJson, serviceAcceptsJson,
                    serviceInfOobj, unid,parentUnid, Infoprojid, billUnid,
                    (String) swbData.get("UNID"),ChargeDetailsJson,null);

            //更新操作状态
            updateDataStatus(swbData.get("UNID").toString(), 1);

        }

        return null;
    }
    /**
     * 
     * 描述 启动省网办传输过来的流程
     * @author Flex Hu
     * @created 2016年1月27日 下午3:56:21
     * @param swbData
     * @return
     * @throws Exception 
     */
    public Map<String,Object> createItemNew(Map<String,Object> swbData) throws Exception{
        Connection ptzhspconn = null;
        ptzhspconn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(),
                this.getPtzhspDbPassword());
        //获取省网下发信息
        String content = (String) swbData.get("content");
        content = content.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
        Document document = XmlUtil.stringToDocument(content);
        Element root = document.getRootElement();
        String serviceInfo = root.selectSingleNode("//Case/Body/ServiceInfo").asXML();
        String serviceAccepts = root.selectSingleNode("//Case/Body/ServiceInfo/ServiceAccepts").asXML();
        String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
        Node ChargeDetailsNode = root.selectSingleNode("//Case/Body/ServiceInfo/ChargeDetails");
        String ChargeDetailsJson = null;
        if (ChargeDetailsNode != null) {
            String ChargeDetails = ChargeDetailsNode.asXML();
            ChargeDetailsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + ChargeDetails,
                    "UTF-8");
        }
        saveRecLog("UNID==="+swbData.get("UNID").toString());
//        String steps = root.selectSingleNode("//Case/Body/Steps").asXML();
        // 定义服务事项
        String serviceInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceInfo, "UTF-8");
//        String stepsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + steps, "UTF-8");
        String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
        String serviceAcceptsJson =
                XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceAccepts, "UTF-8");
        JSONObject serviceInfOobj = JSONObject.parseObject(serviceInfoJson);
        
        String unid = getObjStringInfo(serviceInfOobj, "Unid");
//        String parentUnid = getObjStringInfo(serviceInfOobj, "ParentUnid");
//        String parentName = getObjStringInfo(serviceInfOobj, "ParentName");


//         清单名称  ParentName  清单性质  AddType  所属部门 DeptName 是否公开  1 ?? 自贸区属性  1 ??
        String Infoprojid = getObjStringInfo(serviceInfOobj, "Infoprojid");//省网办事项编码
//        List<Map<String,Object>> itemInfoList = isExistSwbCatalogCode(Infoprojid);
//        Map<String,Object> itemInfo = null;
//        Map<String,Object> catalogInfo = null;
//        if(itemInfoList!=null&&itemInfoList.size()>0){
//            itemInfo = itemInfoList.get(0);
//            catalogInfo = this.getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_CODE" },
//                    new Object[] { itemInfo.get("CATALOG_CODE") });
//        }
        StringBuffer cql = new StringBuffer("select * from PROVINCEITEMINFO t where t.content like '%");
        cql.append(unid).append("%' and t.type='101' and t.oper_status='0' order by t.createtime desc");
        List<Map<String,Object>> swbCataInfo = dao.findBySql(cql.toString(), null, null);
        Element cataroot = null;
        String parentUnid = "";
        catalogInfoUnid = "";
        if (swbCataInfo != null && swbCataInfo.size()>0) {
            catalogInfoUnid = StringUtil.getValue(swbCataInfo.get(0), "UNID");
            Document catadocument = XmlUtil.stringToDocument((String) swbCataInfo.get(0).get("content"));
            cataroot = catadocument.getRootElement();
            /*解析标准化目录*/
            Node stdCataRelationNode = cataroot.selectSingleNode("//Case/Body/ServiceDirectoryRelations/ServiceDirectoryRelation");
            if (stdCataRelationNode != null) {
                String stdCataRelation = stdCataRelationNode.asXML();
                String stdCataInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + stdCataRelation, "UTF-8");
                JSONObject stdCataInfOobj = JSONObject.parseObject(stdCataInfoJson);
                String swbStdCataUnid = (String) ((JSONArray) ((JSONObject) stdCataInfOobj.get("ServiceDirectoryRelation"))
                        .get("DirectoryUnid")).get(0);
                String swbStdCataName = (String) ((JSONArray) ((JSONObject) stdCataInfOobj.get("ServiceDirectoryRelation"))
                        .get("DirectoryName")).get(0);
                String swbStdCataState = (String) ((JSONArray) ((JSONObject) stdCataInfOobj.get("ServiceDirectoryRelation"))
                        .get("DirectoryState")).get(0);
                String sql = "select * from T_WSBS_SERVICEITEM_STDCATALOG t where t.catalog_id = ?";
                Map<String,Object> oldCataMap = dao.getMapBySql(sql, new Object[]{swbStdCataUnid});
                if (oldCataMap == null) {
                    creatCatalog(swbStdCataUnid, swbStdCataName, swbStdCataState , ptzhspconn);
                }
            }

            Node cataRelationNode = cataroot.selectSingleNode("//Case/Body/ServiceCataRelations/ServiceCataRelation");
            if (cataRelationNode != null) {
                String cataRelation = cataRelationNode.asXML();
                String cataInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + cataRelation, "UTF-8");
                JSONObject cataInfOobj = JSONObject.parseObject(cataInfoJson);
                String swbCataUnid = (String) ((JSONArray) ((JSONObject) cataInfOobj.get("ServiceCataRelation"))
                        .get("CataDirectoryUnid")).get(0);
                String swbCatalogCode = (String) ((JSONArray) ((JSONObject) cataInfOobj.get("ServiceCataRelation"))
                        .get("BaseCode")).get(0);
                String swbCatalogName = (String) ((JSONArray) ((JSONObject) cataInfOobj.get("ServiceCataRelation"))
                        .get("CataDirectoryName")).get(0);
                String sql = "select * from T_WSBS_SERVICEITEM_CATALOG t where t.swb_catalog_code=? or t.catalog_name=?";
                List<Map<String,Object>> cataList = dao.findBySql(sql, new Object[]{swbCatalogCode,swbCatalogName}, null);
                if (cataList != null && cataList.size() > 0) {
                    Map<String,Object> catalogInfo = cataList.get(0);
                    catalogInfo.put("SWB_CATALOG_CODE", swbCatalogCode);
                    catalogInfo.put("CATALOG_NAME", swbCatalogName);
                    parentUnid = dao.saveOrUpdate(catalogInfo, "T_WSBS_SERVICEITEM_CATALOG",
                            (String) catalogInfo.get("CATALOG_ID"));
                } else {
                    String addType = getObjStringInfo(serviceInfOobj, "AddType");
                    String addTypeCode = getAddTypeCode(addType);
                    String deptName = getObjStringInfo(serviceInfOobj, "DeptName");
                    String deptCode = getObjStringInfo(serviceInfOobj, "DeptCode");
                    String depatId = getDeptId(deptName,deptCode);
                    creatCatalog(swbCataUnid,swbCatalogName,addTypeCode,depatId,swbCatalogCode,ptzhspconn);
                    parentUnid = swbCataUnid;
                }
            }
         // 2021-10-19 Yanisin shi 申请材料的关联信息，主要用于接收自建系统服务总线数据使用
            Node serviceDirectoryAttrsNode = cataroot
                .selectSingleNode("//Case/Body/ServiceDirectoryRelations/ServiceDirectoryAttrs");
        if (serviceDirectoryAttrsNode != null) {
            String serviceDirectoryAttrsXml = serviceDirectoryAttrsNode.asXML();
            String serviceDirectoryAttrsJson = XmlUtil
                    .xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceDirectoryAttrsXml, "UTF-8");
            JSONObject serviceDirectoryAttrsOobj = JSONObject.parseObject(serviceDirectoryAttrsJson);
            JSONArray serviceDirectoryAttr = ((JSONArray) ((JSONObject) serviceDirectoryAttrsOobj
                    .get("ServiceDirectoryAttrs")).get("ServiceDirectoryAttr"));
            // 事项材料唯一标识
            String serviceAttrUnid = "";
            // 事项材料编码
            String serviceAttrCode = "";
            // 事项材料名称
            String serviceAttrName = "";
            // 标准化事项目录材料唯一标识
            String directoryAttrUnid = "";
            // 标准化事项目录材料名称
            String directoryAttrName = "";
            // 标准化事项目录材料状态
            String directoryAttrState = "";

            Map<String, Object> map = new HashMap<String, Object>();
            for (int m = 0; m < serviceDirectoryAttr.size(); m++) {
                JSONObject jObj = ((JSONObject) serviceDirectoryAttr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("ServiceAttrUnid") != null) {
                        serviceAttrUnid = (String) ((JSONArray) (jObj.get("ServiceAttrUnid"))).get(0);

                    }
                    if ((jObj).get("ServiceAttrCode") != null) {
                        serviceAttrCode = (String) ((JSONArray) (jObj.get("ServiceAttrCode"))).get(0);

                    }
                    if ((jObj).get("ServiceAttrName") != null) {
                        serviceAttrName = (String) ((JSONArray) (jObj.get("ServiceAttrName"))).get(0);

                    }
                    if ((jObj).get("DirectoryAttrUnid") != null) {
                        directoryAttrUnid = (String) ((JSONArray) (jObj.get("DirectoryAttrUnid"))).get(0);

                    }
                    if ((jObj).get("DirectoryAttrName") != null) {
                        directoryAttrName = (String) ((JSONArray) (jObj.get("DirectoryAttrName"))).get(0);

                    }
                    if ((jObj).get("DirectoryAttrState") != null) {
                        directoryAttrState = (String) ((JSONArray) (jObj.get("DirectoryAttrState"))).get(0);

                    }
                    map.put("ServiceAttrUnid", serviceAttrUnid);
                    map.put("ServiceAttrCode", serviceAttrCode);
                    map.put("ServiceAttrName", serviceAttrName);
                    map.put("DirectoryAttrUnid", directoryAttrUnid);
                    map.put("DirectoryAttrName", directoryAttrName);
                    map.put("DirectoryAttrState", directoryAttrState);

                    dao.saveOrUpdate(map, "T_WSBS_SWBATTRSRELATIONS", directoryAttrUnid);
                }
            }

        }
//            String sql = "select * from T_WSBS_SERVICEITEM_CATALOG t where t.swb_catalog_code=? or t.catalog_name=?";
//            List<Map<String,Object>> cataList = dao.findBySql(sql, new Object[]{swbCatalogCode,swbCatalogName}, null);
//            if(cataList!=null&&cataList.size()>0){
//                catalogInfo = cataList.get(0);
//                catalogInfo.put("SWB_CATALOG_CODE", swbCatalogCode);
//                catalogInfo.put("CATALOG_NAME", swbCatalogName);
//                parentUnid = dao.saveOrUpdate(catalogInfo, "T_WSBS_SERVICEITEM_CATALOG",
//                        (String) catalogInfo.get("CATALOG_ID"));
//            }else{
//                creatCatalog(swbCataUnid,swbCatalogName,addTypeCode,depatId,swbCatalogCode,ptzhspconn);
//                parentUnid = swbCataUnid;
//            }
        }

        creatOrUpdateItem(root, attrsJson, serviceAcceptsJson, 
                serviceInfOobj, unid,parentUnid, Infoprojid, unid ,
                (String)swbData.get("UNID"),ChargeDetailsJson,cataroot);
    
        //更新操作状态
        updateDataStatus(swbData.get("UNID").toString(), 1);

        StringBuffer udpcql = new StringBuffer();
        udpcql.append("update PROVINCEITEMINFO t set t.oper_status='1' where t.content like '%");
        udpcql.append(unid).append("%' and t.type='101' and t.oper_status='0'");
        dao.executeSql(udpcql.toString(), null);
        
        return null;
    }

    private void creatOrUpdateItem(Element root, String attrsJson, String serviceAcceptsJson,
            JSONObject serviceInfOobj, String unid, String parentUnid,
                                   String Infoprojid, String billUnid , String swunid,String ChargeDetailsJson,
                                   Element cataroot) {
        int isExist;
        List<Map<String, Object>>itemInfo = isExistServiceItem(Infoprojid);
        isExist = itemInfo.size();
        //生成事项
        Map<String, Object> itemParam = new HashMap<String, Object>();
        String entityId = "";
        String itemCode = "";
        if (StringUtils.isNotEmpty(parentUnid)) {
            Map<String, Object> catalog = dao.getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_ID" },
                    new Object[] { parentUnid });
            String catalogId = catalog.get("CATALOG_ID").toString();
            String catalogCode = catalog.get("CATALOG_CODE").toString();
            itemParam.put("CATALOG_CODE", catalogCode);
            itemParam.put("CATALOG_ID", catalogId);
        }
        if (isExist>0) {
            itemParam = itemInfo.get(0);
            entityId = itemInfo.get(0).get("ITEM_ID")==null?
                    "":itemInfo.get(0).get("ITEM_ID").toString();
            itemCode = itemInfo.get(0).get("ITEM_CODE")==null?
                    "":itemInfo.get(0).get("ITEM_CODE").toString();
        }else {
//            String projidMain = getObjStringInfo(serviceInfOobj, "ProjidMain");
          //根据省网办编码或省网办目录id获取本地目录编码
//            String catalogCode = getCatalogCode(parentUnid,Infoprojid);
//            String catalogCode = getCatalogCodeNew(serviceInfOobj);
//            String maxNumCode = serviceItemService.getMaxNumCode(catalogCode);
//            itemParam.put("NUM_CODE", maxNumCode);
//            String ProjidSub = getObjStringInfo(serviceInfOobj, "ProjidSub");
//            itemCode = catalogCode+maxNumCode;
            itemParam.put("ITEM_CODE", Infoprojid + "L");
        }
        String addType = getObjStringInfo(serviceInfOobj, "AddType");
        String addTypeCode = getAddTypeCode(addType);
        itemParam.put("SXXZ", addTypeCode);
        String deptName = getObjStringInfo(serviceInfOobj, "DeptName");
        String DeptCode = getObjStringInfo(serviceInfOobj, "DeptCode");
        String depatId = getDeptId(deptName,DeptCode);
        String depatCode = getDeptCode(depatId);
        itemParam.put("SSBMBM", depatCode);
        if (StringUtils.isNotEmpty(depatId)) {
            Map<String, Object> departMap = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[]{"DEPART_ID"}, new Object[]{depatId});
            if (departMap != null) {
                itemParam.put("IMPL_DEPART", StringUtil.getValue(departMap, "DEPART_NAME"));
            }
        }
        itemParam.put("IMPL_DEPART_ID", depatId);
        /*获取主办处室部门*/
        String LeadDept = getObjStringInfo(serviceInfOobj, "LeadDept");
        if (StringUtils.isNotEmpty(LeadDept)) {
            /*获取子部门*/
            Map<String,Object> leadDeptMap = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[]{"DEPART_NAME", "TREE_LEVEL","STATUS"}, new Object[]{LeadDept, "4","1"});
            if (leadDeptMap != null) {
                String departId = StringUtil.getValue(leadDeptMap, "DEPART_ID");
                itemParam.put("ZBCS", LeadDept);
                itemParam.put("ZBCS_ID", departId);
            }
        }

        itemParam.put("ROOM_ITEM_CODE", Infoprojid);


//        System.out.println("itemCode======================"+itemCode);
        saveRecLog("itemCode============"+itemCode);
        //事项状态【正常、挂起、取消、下放、涉密事项、变更】 !!!!!!!!
//        -- -1草稿库 4退回库 2 5 审核库 1发布库 3取消库
//        String CurState = getObjStringInfo(serviceInfOobj, "CurState");
//        if ("正常".endsWith(CurState)
//                ||"变更".endsWith(CurState)
//                ||"下放".endsWith(CurState)) {
//            itemParam.put("FWSXZT", "1");
//        }else if ("挂起".endsWith(CurState)
//                ||"涉密事项".endsWith(CurState)) {
//            itemParam.put("FWSXZT", "-1");
//        }else if ("取消".endsWith(CurState)) {
//            itemParam.put("FWSXZT", "3");
//        }
        //都放到草稿库
        itemParam.put("FWSXZT", "-1");
        makeItemParam1(serviceInfOobj, itemParam, billUnid);
        
        //自动勾选面向对象
        getMXDXids(serviceInfOobj, itemParam);
        
        makeItemParam2(serviceAcceptsJson, serviceInfOobj, itemParam);

        /*获取目录中的标准化事项目录*/
        getStandardCatalog(cataroot, itemParam);

        /*设置收费信息*/
        setChargeInfo(itemParam, serviceInfOobj);


        String inproperty = getObjStringInfo(serviceInfOobj, "Inproperty");
        itemParam.put("RZBSDTFS", inproperty);
        String FinishType = getObjStringInfo(serviceInfOobj, "FinishType");
        String FinishTypeCode = getFinishTypeCode(FinishType);
        itemParam.put("FINISH_TYPE", FinishTypeCode);
        String FinishGetTypes = getObjStringInfo(serviceInfOobj, "FinishGetType");
        StringBuffer FinishGetTypeCodes = new StringBuffer("");
        String[] FinishGetTypeArray = FinishGetTypes.split(",");
        for (int i = 0; i < FinishGetTypeArray.length; i++) {
            String FinishGetTypeCode = getFinishGetTypeCode(FinishGetTypeArray[i]);
            if (StringUtils.isNotEmpty(FinishGetTypeCode)) {
                FinishGetTypeCodes.append(FinishGetTypeCode);
                if (i < FinishGetTypeArray.length-1) {
                    FinishGetTypeCodes.append(",");
                }
            }
        }
        itemParam.put("FINISH_GETTYPE", FinishGetTypeCodes);
        String FinishInfo = getObjStringInfo(serviceInfOobj, "FinishInfo");
        itemParam.put("FINISH_INFO", FinishInfo);

        //String status = "";
        itemParam.put("SWB_ITEM_ID", unid);
        itemParam.put("SWB_ITEM_CODE", Infoprojid);
        itemParam.put("IS_FROM_SWB", "1");
        
        if (StringUtils.isEmpty(entityId)) {
            int maxSn = serviceItemService.getMaxSn();
            itemParam.put("C_SN", maxSn + 1);
            itemParam.put("FWSXZT", "-1");
        }
        itemParam.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("保存事项信息：" + JSON.toJSONString(itemParam));
        String itemId = departServiceItemService.saveOrUpdateCascade(itemParam);
        //更新全责清单子项
//        String billId = billUnid;
//        if(StringUtils.isEmpty(entityId)&&StringUtils.isNotEmpty(billId)){
//            Map<String,Object> billItem = new HashMap<String, Object>();
//            billItem.put("SERVICEITEM_CODE", itemCode);
//            departServiceItemService.saveOrUpdate(billItem, "T_WSBS_RIGHTBILL_ITEM", billId);
//            String readIds = "402881ae52339704015233a8c997000d,402881ae52339704015233b930290012,"
//                    + "402881ae52339704015233c94a09001e,402881ae52339704015233bebc1b0015,"
//                    + "402881ae52339704015233c1c3540018,402881ae52339704015233c3c8cf001b,"
//                    + "402881ae52339704015233cbcea20021,402881ae52339704015233ced6590024";
//            serviceItemService.saveItemRead(itemId, readIds);
//        }

        String serviceSpecials = root.selectSingleNode("//Case/Body/ServiceInfo/ServiceSpecials").asXML();
        String serviceSpecialsJson = 
                XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceSpecials, "UTF-8");
        JSONObject serviceSpecialsObj = JSONObject.parseObject(serviceSpecialsJson);
        JSONArray serviceSpecial = ((JSONArray) 
                ((JSONObject) serviceSpecialsObj.get("ServiceSpecials")).get("ServiceSpecial"));
        handleSS(itemId, serviceSpecial);
        /*收费明细*/
        handleCharge(ChargeDetailsJson , itemId);
        JSONObject attrObj = JSONObject.parseObject(attrsJson);
        JSONArray arr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
        handleAttr(itemId, arr);
        /*添加阅办模板*/
        handleReadTemplate(itemId);
        StringBuffer updateLog = new StringBuffer();
        updateLog.append("根据省网办下发内容更新事项,事项省网unid=").append(swunid);
        if (StringUtils.isNotEmpty(catalogInfoUnid)) {
            updateLog.append("，目录省网unid=").append(catalogInfoUnid);
        } else {
            updateLog.append("，未同时下发目录");
        }
        saveItemLog(updateLog.toString(), itemId, "1");

    }

    /**
     * 描述:设置收费信息
     *
     * @author Madison You
     * @created 2021/1/27 11:39:00
     * @param
     * @return
     */
    private void setChargeInfo(Map<String, Object> itemParam, JSONObject serviceInfOobj) {
        /*是否收费*/
        String ChargeFlag = getObjStringInfo(serviceInfOobj, "ChargeFlag");
        String ChargeFlagCode = forMateYorN(ChargeFlag);
        itemParam.put("SFSF", ChargeFlagCode);
        //仅当收费时填报，收费方式【01指定窗口收费、02已实现网上缴费、03办事大厅缴费】  改字典
        String ChargeType = getObjStringInfo(serviceInfOobj, "ChargeType");
        if (StringUtils.isNotEmpty(ChargeType)) {
            StringBuffer str = new StringBuffer();
            String[] chargeTypeSplit = ChargeType.split(",");
            for (int i = 0; i < chargeTypeSplit.length; i++) {
                String chargeTypeCode = getChargeTypeCode(chargeTypeSplit[i]);
                str.append(chargeTypeCode);
                if (i < chargeTypeSplit.length - 1) {
                    str.append(",");
                }
            }
            itemParam.put("SFFS", str);
        }
        /*收费方式说明*/
        String ChargeLimit = getObjStringInfo(serviceInfOobj, "ChargeLimit");
        itemParam.put("SFFSSM",ChargeLimit);
        /*收费项目名称*/
        String ChargeName = getObjStringInfo(serviceInfOobj, "ChargeName");
        itemParam.put("CHARGENAME",ChargeName);
        itemParam.put("SFXMMC", ChargeName);
        //收费标准
        String ChargeStandard = getObjStringInfo(serviceInfOobj, "ChargeStandard");
        itemParam.put("CHARGESTANDARD",ChargeStandard);
        /*收费依据*/
        String ChargeAccord = getObjStringInfo(serviceInfOobj, "ChargeAccord");
        itemParam.put("CHARGEACCORD",ChargeAccord);
        /*是否允许减免*/
        String ChargeDerateIf = getObjStringInfo(serviceInfOobj, "ChargeDerateIf");
        itemParam.put("CHARGEDERATEIF", ChargeDerateIf);
        /*允许减免依据*/
        String ChargeDerateAccord = getObjStringInfo(serviceInfOobj, "ChargeDerateAccord");
        itemParam.put("CHARGEDERATEACCORD", ChargeDerateAccord);
        /*支付渠道 1非税支付  字典修改*/
        String ChargeCfgType = getObjStringInfo(serviceInfOobj, "ChargeCfgType");
        itemParam.put("PAY_WAY", "0"+ChargeCfgType);
        /*支付渠道对应的站点名称*/
        String ChargeCfgName = getObjStringInfo(serviceInfOobj, "ChargeCfgName");
        itemParam.put("CHARGECFGNAME", ChargeCfgName);
        /*支付渠道对应的站点代码*/
        String ChargeCfgCode = getObjStringInfo(serviceInfOobj, "ChargeCfgCode");
        itemParam.put("CHARGECFGCODE", ChargeCfgCode);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/8 14:34:00
     * @param
     * @return
     */
    private void getStandardCatalog(Element cataroot , Map<String,Object> itemParam) {
        if (cataroot != null) {
            Node node = cataroot.selectSingleNode("//Case/Body/ServiceDirectoryRelations/ServiceDirectoryRelation");
            if (node != null) {
                String cataRelation = node.asXML();
                String cataInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + cataRelation, "UTF-8");
                JSONObject cataInfOobj = JSONObject.parseObject(cataInfoJson);
                String directoryUnid = (String) ((JSONArray) ((JSONObject) cataInfOobj.get("ServiceDirectoryRelation"))
                        .get("DirectoryUnid")).get(0);
                String directoryName = (String) ((JSONArray) ((JSONObject) cataInfOobj.get("ServiceDirectoryRelation"))
                        .get("DirectoryName")).get(0);
                itemParam.put("STANDARD_CATALOG_ID", directoryUnid);
                itemParam.put("STANDARD_CATALOG_NAME", directoryName);
            }
        }
    }

    /**
     * 描述:省网下发事项自动添加阅办模板
     *
     * @author Madison You
     * @created 2020/5/15 14:16:00
     * @param
     * @return
     */
    private void handleReadTemplate(String itemId) {
        StringBuffer insertSql = new StringBuffer();
        insertSql.append(" insert into T_WSBS_SERVICEITEM_READ (ITEM_ID,READ_ID) values (?,?) ");
        List<Map<String, Object>> swsxybmb = dictionaryService.findByTCodeAndWhere("SWSXYBMB", "1=1");
        if (swsxybmb != null && !swsxybmb.isEmpty()) {
            for (Map<String, Object> map : swsxybmb) {
                String readId = (String) map.get("DIC_CODE");
                /*判断是否存在阅办模板*/
                boolean existReadTemplate = isExistReadTemplate(itemId, readId);
                if (!existReadTemplate) {
                    dao.executeSql(insertSql.toString(), new Object[]{itemId, readId});
                }
            }
        }
    }

    /**
     * 描述:判断是否存在阅办模板
     *
     * @author Madison You
     * @created 2020/5/15 14:28:00
     * @param
     * @return
     */
    private boolean isExistReadTemplate(String itemId, String readId) {
        StringBuffer isExisSql = new StringBuffer();
        isExisSql.append(" select * from t_wsbs_serviceitem_read where item_id = ? and read_id = ? ");
        List list = dao.findBySql(isExisSql.toString(), new Object[]{itemId, readId}, null);
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * save item log
     * @param content
     * @param itemId
     * @param type
     * @param request
     */
    public void saveItemLog(String content,String itemId,String type){
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE",type);
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        data.put("FULLNAME","省网办下发");
        data.put("USERNAME","省网办下发");
        data.put("USERID", "省网办下发");
        serviceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
    }

    private void handleAttr(String itemId, JSONArray arr) {
        String applyMaterId = "";
        String AttrCode = "";
        String AttrName = "";
        String SrcAttrCode = "";
        String Type = "";
        String ProAttrCode = "";
        String AttrRemark = "";
        String AttrTempleMaterial = "";
        String AttrSampleMaterial = "";
        String AttrSort = "";
        String isNeed = "";
        String AttrType = "";
        String AttrCreateTime = "";
        String Medium = "";
        String MaterialSource = "";
        String GetTypeUnids = "";
//        if (false) {
        if (arr!=null) {
            applyMaterService.deleteMaterByItem(itemId);
            for (int m = 0; m < arr.size(); m++) {
                JSONObject jObj = ((JSONObject) arr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("AttrCode") != null && ((JSONArray) (jObj).get("AttrCode")).get(0) != null) {
                        applyMaterId = "";
                        Map<String, Object> clvariables = new HashMap<String, Object>();
                        SrcAttrCode = getjObjInfo(jObj, "SrcAttrCode");
                        AttrCode = getjObjInfo(jObj, "AttrCode");
                        GetTypeUnids = getjObjInfo(jObj, "GetTypeUnids");
                        /*与本地收取方式字典对应*/
                        String newGetTypeUnids = reviseGetTypeUnids(GetTypeUnids);
                        List<Map<String, Object>>attrInfo = getAttrInfoByCode(AttrCode);//以省网编码为标识
                        if (attrInfo.size()>0) {
                            applyMaterId = attrInfo.get(0).get("MATER_ID")==null?
                                    "":attrInfo.get(0).get("MATER_ID").toString();
                            clvariables = attrInfo.get(0);
                        };
//                      clvariables.put("MATER_CODE", "swb"+SrcAttrCode);
                        /*20191113新增，材料收取方式*/
                        clvariables.put("COLLECT_METHOD", newGetTypeUnids);
                        clvariables.put("MATER_CODE", AttrCode);//设本地编码为省网编码
                        clvariables.put("itemId", itemId);
                        clvariables.put("MATER_TYPE", "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.rar;");
                        clvariables.put("SWB_MATER_CODE", AttrCode);
                        //310777368XK17502_01
                        AttrName = getjObjInfo(jObj, "AttrName");
                        clvariables.put("MATER_NAME", AttrName);
//                        clvariables.put("")
                        MaterialSource = getjObjInfo(jObj, "MaterialSource");
                        clvariables.put("MATER_SOURCE", MaterialSource);
                        AttrType = getjObjInfo(jObj, "AttrType");
                        //保存事项子项
                        saveOrUpdateZX(itemId, AttrType, clvariables);
                        //事项详细信息
                        attachDetail(jObj, clvariables);
                        clvariables.put("SWB_UPDATE_TIME", 
                                DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        //??
                        AttrSort = getjObjInfo(jObj, "AttrSort");
                        String necessityIf = getjObjInfo(jObj, "NecessityIf");
                        if (Objects.equals(necessityIf, "1")) {
                            isNeed = "1";
                        } else {
                            isNeed = "0";
                        }
//                        AttrCreateTime = getjObjInfo(jObj, "AttrCreateTime");
                        
                        AttrTempleMaterial = getjObjInfo(jObj, "AttrTempleMaterial");
//                        Map<String,Object> fileAttach = new HashMap<String,Object>();
                        String fileId = "";
                        String fileName = "";
                        String filePath = "";
                        String fileType = "";
                        String sqfs = "1";
                        if (StringUtils.isNotEmpty(AttrTempleMaterial)) {
                            String[] AttrTempleMaterialArray = AttrTempleMaterial.split(",");
                            for (int i = 0; i < AttrTempleMaterialArray.length; i++) {
                                Map<String,Object> attachFile = dao.getByJdbc("T_BSFW_SWBPROVDATA_ATTR",
                                        new String[]{"ID"},new Object[]{AttrTempleMaterialArray[i]});
                                if (attachFile!=null&&attachFile.get("TCHAR3")!=null) {
                                    fileName = attachFile.get("TCHAR3")==null?
                                        "":attachFile.get("TCHAR3").toString();
                                    // 获取文件的后缀名
                                    fileType = FileUtil.getFileExtension(fileName);
                                    fileId = swbProvAttrService.genFileToDiskAndSaveL(AttrTempleMaterialArray[i],
                                            fileType, fileName, filePath, "T_WSBS_APPLYMATER",
                                            "swbAttrTempleMaterial", sqfs, AttrTempleMaterialArray[i]);
                                    clvariables.put("MATER_PATH", fileId);
                                }else {
                                    saveRecLog("未找到id为"+AttrTempleMaterialArray[i]+"的附件,或无文件名无法解析。");
                                }
                            }
                        }
                        
                        AttrSampleMaterial = getjObjInfo(jObj, "AttrSampleMaterial");
                        if (StringUtils.isNotEmpty(AttrSampleMaterial)) {
                            String[] AttrSampleMaterialArray = AttrSampleMaterial.split(",");
                            for (int i = 0; i < AttrSampleMaterialArray.length; i++) {
                                Map<String,Object> attachFile = dao.getByJdbc("T_BSFW_SWBPROVDATA_ATTR",
                                        new String[]{"ID"},new Object[]{AttrSampleMaterialArray[i]});
                                if (attachFile!=null&&attachFile.get("TCHAR3")!=null) {
                                    fileName = attachFile.get("TCHAR3")==null?
                                        "":attachFile.get("TCHAR3").toString();
                                    // 获取文件的后缀名
                                    fileType = FileUtil.getFileExtension(fileName);//?????
                                    fileId = swbProvAttrService.genFileToDiskAndSaveL(AttrSampleMaterialArray[i],
                                            fileType, fileName, filePath, "T_WSBS_APPLYMATER",
                                            "swbAttrSampleMaterial", sqfs, AttrSampleMaterialArray[i]);
                                    clvariables.put("MATER_PATH2", fileId);
                                }else {
                                    saveRecLog("未找到id为"+AttrSampleMaterialArray[i]+"的附件,或无文件名无法解析。");
                                }
                            }
                        }
                        
                        applyMaterId = applyMaterService.saveOrUpdate(clvariables, "T_WSBS_APPLYMATER", applyMaterId);
                        //保存材料项目中间表
                        if(StringUtils.isNotEmpty(itemId)){
//                            applyMaterService.saveMaterItem(applyMaterId, itemId);
                            /*2019/12/05 Madison You 材料改成按照省网材料排序*/
                            saveMaterItemBySn(applyMaterId, itemId, AttrSort , isNeed);
                            Type = getjObjInfo(jObj, "Type");
                            if ("是".equals(Type)) {
                                
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 事项一些详细信息
     * @param jObj
     * @param clvariables
     */
    private void attachDetail(JSONObject jObj, Map<String, Object> clvariables) {
        String ProAttrCode;
        String AttrRemark;
        String Medium;
        ProAttrCode = getjObjInfo(jObj, "ProAttrCode");
        clvariables.put("MATER_PARENTCODE", ProAttrCode);
        AttrRemark = getjObjInfo(jObj, "AttrRemark");
        clvariables.put("MATER_DESC", AttrRemark);
        //原件份数
        String PAGE_NUM=getjObjInfo(jObj,"PageNum");
        clvariables.put("PAGE_NUM",PAGE_NUM);
        //复印件份数
        String PAGECOPY_NUM=getjObjInfo(jObj,"PageCopyNum");
        clvariables.put("PAGECOPY_NUM",PAGECOPY_NUM);

        Medium = getjObjInfo(jObj, "Medium");
        if(StringUtils.isNotEmpty(Medium)){
            String[] MediumArray=Medium.split(",");
            //材料类型说明
            List<String> clsmlx=new ArrayList<String>();
            for(int i=0;i<MediumArray.length;i++){
                if ("原件(收)".equals(MediumArray[i]) || "原件".equals(MediumArray[i])) {
                    clsmlx.add("原件(收)");
                    clvariables.put("ISNEED_RAW","1");
                }
                if ("原件(验)".equals(MediumArray[i])) {
                    clsmlx.add("原件(验)");
                    clvariables.put("ISNEED_RAW","1");
                }
                if ("复印件".equals(MediumArray[i])) {
                    clsmlx.add("复印件");
                }
                if("电子文档".equals(MediumArray[i])){
                    clsmlx.add("电子文档");
                }
            }
            clvariables.put("MATER_CLSMLX",StringUtils.join(clsmlx,","));
        }
        if (StringUtils.isNotEmpty(Medium)) {
            String[] MediumArray = Medium.split(",");
            for (int i = 0; i < MediumArray.length; i++) {
                if ("原件(收)".equals(MediumArray[i]) || "原件".equals(MediumArray[i])) {
                    clvariables.put("GATHERORVER", "收取");
                }else if ("原件(验)".equals(MediumArray[i])) {
                    clvariables.put("GATHERORVER", "核验(不收取)");
                }else if ("复印件".equals(MediumArray[i])) {

                }
            }
        }
    }

    /**
     * 
     * 描述：保存事项子项
     * @author Water Guo
     * @created 2018-1-24 上午11:36:46
     * @param itemId
     * @param AttrType
     * @param clvariables
     */
    private void saveOrUpdateZX(String itemId, String AttrType, Map<String, Object> clvariables) {
        if (StringUtils.isNotEmpty(AttrType)) {
            StringBuffer ssywIds = new StringBuffer();
            String[] AttrTypes = AttrType.split(",");
            for (String attrType : AttrTypes) {
                String ssywId = "";
                List<Map<String, Object>> ssywList = getSsyw(attrType, itemId);
                if (ssywList.size() > 0) {
                    ssywId = ssywList.get(0).get("RECORD_ID") == null ? "" : ssywList.get(0)
                            .get("RECORD_ID").toString();
                } else {
                    Map<String, Object> ssywMap = new HashMap<String, Object>();
                    ssywMap.put("busclass_name", attrType);
                    ssywMap.put("item_id", itemId);
                    ssywId = dao.saveOrUpdate(ssywMap, "T_WSBS_SERVICEITEM_BUSCLASS", null);
                }
                ssywIds.append(ssywId).append(",");
            }
            clvariables.put("MATER_SSYW", ssywIds.substring(0, ssywIds.length() - 1));// 事项子项
        }
    }
    /**
     * 
     * 描述：保存事项子类类型
     * @author Water Guo
     * @created 2018-1-24 上午10:26:18
     * @param itemId
     * @param AttrType
     * @param ssywId
     * @return
     */
    private String saveAttrOfBusclass(String itemId, String AttrType, String ssywId) {
        Map<String,Object> ssywMap = new HashMap<String,Object>();
        String[] AttrTypes=AttrType.split(",");
        for(String attrType:AttrTypes){
            ssywMap.put("busclass_name", AttrType);
            ssywMap.put("item_id", itemId);
            ssywId = dao.saveOrUpdate(ssywMap, "T_WSBS_SERVICEITEM_BUSCLASS", null);
        }
        return ssywId;
    }

    private void saveRecLog(String string) {
        // TODO Auto-generated method stub
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LOG_CONTENT", string);
        dao.saveOrUpdate(map, "T_WSBS_RECITEM_LOG", null);
    }

    private List<Map<String, Object>> getSsyw(String attrtype, String itemId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_SERVICEITEM_BUSCLASS t where t.busclass_name = ? and t.item_id = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{attrtype,itemId}, null);
        return list;
    }

    private List<Map<String,Object>> isExitsFile(String filePath) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("select t.file_id  from T_MSJW_SYSTEM_FILEATTACH t where t.file_id = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{filePath}, null);
        return list;
    }

    private void handleSS(String itemId, JSONArray serviceSpecial) {
        String Sname ="";
        String Sday ="";
        String Sconent ="";
        String LinkId = "";
        if (serviceSpecial!=null) {
            for (int m = 0; m < serviceSpecial.size(); m++) {
                JSONObject jObj = ((JSONObject) serviceSpecial.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("Sname") != null && ((JSONArray) (jObj).get("Sname")).get(0) != null) {
                        LinkId = "";
                        Map<String, Object> ssvariables = new HashMap<String, Object>();
                        Sname = getjObjInfo(jObj, "Sname");
                        List<Map<String, Object>>serviceSpecialInfo = getServiceSpecialInfo(Sname,itemId);
                        if (serviceSpecialInfo.size()>0) {
                            LinkId = serviceSpecialInfo.get(0).get("RECORD_ID")==null?
                                    "":serviceSpecialInfo.get(0).get("RECORD_ID").toString();
                            ssvariables = serviceSpecialInfo.get(0);
                        }
                        ssvariables.put("ITEM_ID", itemId);
                        Sname = getjObjInfo(jObj, "Sname");
                        ssvariables.put("LINK_NAME", Sname);
                        Sday = (String) ((JSONArray) (jObj).get("Sday")).get(0);
                        ssvariables.put("LINK_LIMITTIME", Sday);
//                        Sconent = (String) ((JSONArray) (jObj).get("Sconent")).get(0);
//                        ssvariables.put("LINK_BASIS", Sconent);
                        JSONArray sconentJson = (JSONArray) (jObj).get("Sconent");
                        if (sconentJson != null) {
                            ssvariables.put("LINK_BASIS", sconentJson.get(0));
                        }
                        LinkId=departServiceItemService.saveOrUpdate(ssvariables, "T_WSBS_SERVICEITEM_LINK", LinkId);
                    }
                }
            }
        }
    }


    private void makeItemParam2(String serviceAcceptsJson, JSONObject serviceInfOobj, Map<String, Object> itemParam) {
        String ServiceType = getObjStringInfo(serviceInfOobj, "ServiceType");
        String sxlx = "";
        if (ServiceType.endsWith("承诺件")) {
            sxlx = getServiceTypeCode("普通件");
        }else{
            sxlx = getServiceTypeCode(ServiceType);
        }
        itemParam.put("SXLX", sxlx);
        String CountLimit = getObjStringInfo(serviceInfOobj, "CountLimit");
        itemParam.put("SLXZSM", CountLimit);
        String Iremark = getObjStringInfo(serviceInfOobj, "Iremark");
        itemParam.put("BZSM", Iremark);
        String According = getObjStringInfo(serviceInfOobj, "According");
        itemParam.put("XSYJ", According);
        String Applyterm = getObjStringInfo(serviceInfOobj, "Applyterm");
        itemParam.put("SQTJ", Applyterm);
        String Proceflow = getObjStringInfo(serviceInfOobj, "Proceflow");
        itemParam.put("BLLC", Proceflow);

        JSONObject serviceAcceptsOobj = JSONObject.parseObject(serviceAcceptsJson);
        JSONArray ServiceAccept = ((JSONArray) 
                ((JSONObject) serviceAcceptsOobj.get("ServiceAccepts")).get("ServiceAccept"));
        StringBuffer sqfs = new StringBuffer();
        for (int m = 0; m < ServiceAccept.size(); m++) {
            JSONObject jObj = ((JSONObject) ServiceAccept.get(m));
            if (jObj != null && !jObj.isEmpty()) {
                if ((jObj).get("AcceptType") != null && ((JSONArray) (jObj).get("AcceptType")).get(0) != null) {
                    if (sqfs.length() != 0) {
                        sqfs.append(",");
                    }
                    String AcceptType = (String) ((JSONArray) (jObj).get("AcceptType")).get(0);
                    if (AcceptType.endsWith("行政服务中心窗口受理")) {
                        sqfs.append("1");
                        String ToWinMaxNum = (String) ((JSONArray) (jObj).get("ToWinMaxNum")).get(0);
                        itemParam.put("CKCS_1", ToWinMaxNum);
                    }else if (AcceptType.endsWith("网上申请和预审，窗口纸质材料收件受理（收到纸质材料后才能受理）")||
                            AcceptType.endsWith("【三星】网上申请和预审，窗口纸质材料收件受理（收到纸质材料后才能受理）")) {
                        sqfs.append("2");
                        String ToWinMaxNum = (String) ((JSONArray) (jObj).get("ToWinMaxNum")).get(0);
                        itemParam.put("CKCS_2", ToWinMaxNum);
                    }else if (AcceptType.endsWith("网上申请、预审和受理，办结后到窗口核验纸质材料，领取结果")
                            ||AcceptType.endsWith("【四星】网上申请、预审和受理，到窗口核验纸质材料后办结，领取结果。")
                            ||AcceptType.endsWith("【四星】网上申请、预审和受理，窗口纸质核验办结 ")) {
                        sqfs.append("3");
                        String ToWinMaxNum = (String) ((JSONArray) (jObj).get("ToWinMaxNum")).get(0);
                        itemParam.put("CKCS_3", ToWinMaxNum);
                    }else if (AcceptType.endsWith("提供全流程网上办理（在线申请、网上预审、网上受理、网上办结）,申请人无需提交纸质材料，只需办结后领取结果")
                            ||AcceptType.endsWith("提供全流程网上办理（在线申请、网上预审、网上受理、网上办结）,申请人只须办结后领取结果")
                            ||AcceptType.endsWith("【五星】提供全流程网上办理，申请人在办结后到窗口领取结果 ")) {
                        sqfs.append("4");
                        
                        String ToWinMaxNum = (String) ((JSONArray) (jObj).get("ToWinMaxNum")).get(0);
                        itemParam.put("CKCS_4", ToWinMaxNum);
                    }else {
//                        System.out.println(AcceptType);
                        saveRecLog("未找到"+AcceptType+"的申请方式");
                    }
                }
            }
        }
        itemParam.put("SQFS", sqfs.toString());
        String PromiseDay = getObjStringInfo(serviceInfOobj, "PromiseDay");
        itemParam.put("CNQXGZR", PromiseDay);
        String PromisDayInfo = getObjStringInfo(serviceInfOobj, "PromiseDayInfo");
        itemParam.put("CNQXSM", PromisDayInfo);
        String LawLimit = getObjStringInfo(serviceInfOobj, "LawLimit");
        itemParam.put("FDSXGZR", LawLimit);
        String lawLimitInfo = getObjStringInfo(serviceInfOobj, "LawLimitInfo");
        itemParam.put("FDQX", lawLimitInfo);
        //String PreDay = getObjStringInfo(serviceInfOobj, "PreDay");
        //itemParam.put("PREDAY", PreDay);
        //收件时限????
        String AcceptAddress = getObjStringInfo(serviceInfOobj, "AcceptAddress");
        itemParam.put("BLDD", AcceptAddress);
        String TrafficGuide = getObjStringInfo(serviceInfOobj, "TrafficGuide");
        itemParam.put("TRAFFIC_GUIDE", TrafficGuide);
        String OfficeTime = getObjStringInfo(serviceInfOobj, "OfficeTime");
        itemParam.put("BGSJ", OfficeTime);
        String LinkMan = getObjStringInfo(serviceInfOobj, "LinkMan");
        itemParam.put("LXR", LinkMan);
        itemParam.put("SYNC_LXR", LinkMan);
        String ContactPhone = getObjStringInfo(serviceInfOobj, "ContactPhone");
        itemParam.put("LXDH", ContactPhone);
//        String MonitorTel = getObjStringInfo(serviceInfOobj, "MonitorTel");
//        itemParam.put("JDDH", MonitorTel);
        String MonitorComplain = getObjStringInfo(serviceInfOobj, "MonitorComplain");
        itemParam.put("JDDH", MonitorComplain);
//        String ReceiverSms = getObjStringInfo(serviceInfOobj, "ReceiverSms");
//        itemParam.put("MESSAGE_REC", ReceiverSms);
    }

    private void makeItemParam1(JSONObject serviceInfOobj, 
            Map<String, Object> itemParam, String billUnid) {
//        if (StringUtils.isNotEmpty(billUnid)) {
//            readBillItem(billUnid, itemParam);
//        }
        String ServiceName = getObjStringInfo(serviceInfOobj, "ServiceName");
        itemParam.put("ITEM_NAME", ServiceName);
        //互联网申请的网站或系统名称（仅链接式填写）
//        String WebapplyName = getObjStringInfo(serviceInfOobj, "WebapplyName");
        //网上申请链接（仅链接式填写）
        String WebapplyUrl = getObjStringInfo(serviceInfOobj, "WebapplyUrl");
        itemParam.put("APPLY_URL", WebapplyUrl);
        //事项星级【1～5星】
        String StarProperty = getObjStringInfo(serviceInfOobj, "StarProperty");
        itemParam.put("ITEMSTAR", StarProperty);
        //承诺时限方式
//        String PromiseDayMethod = getObjStringInfo(serviceInfOobj, "PromiseDayMethod");
//        String PromiseDayMethodCode = getFDSXCode(PromiseDayMethod);
//        itemParam.put("FDSXLX", PromiseDayMethodCode);
        //是否收费【是，否】
//        String ChargeFlag = getObjStringInfo(serviceInfOobj, "ChargeFlag");
//        String ChargeFlagCode = getYesOrNo(ChargeFlag);
//        itemParam.put("SFSF", ChargeFlagCode);
        //仅当收费时填报，收费方式【01指定窗口收费、02已实现网上缴费、03办事大厅缴费】
//        String ChargeType = getObjStringInfo(serviceInfOobj, "ChargeType");
//        String chargeTypeCode = getChargeTypeCode(ChargeType);
//        itemParam.put("SFFS", chargeTypeCode);
        //收费方式说明
//        String ChargeLimit = getObjStringInfo(serviceInfOobj, "ChargeLimit");
        //收费标准及依据
//        String ChargeStandard = getObjStringInfo(serviceInfOobj, "ChargeStandard");
//        itemParam.put("SFBZJYJ", ChargeStandard);
        //纸质申请材料收取方式【win（窗口收取）、post （邮递收取）】
        String AcceptGetType = getObjStringInfo(serviceInfOobj, "AcceptGetType");
        if ("win窗口收取".equals(AcceptGetType)) {
            itemParam.put("PAPERSUB", "1");
        }else if ("post邮递收取".equals(AcceptGetType)) {
            itemParam.put("PAPERSUB", "2");
        } else if ("win窗口收取,post邮递收取".equals(AcceptGetType)) {
            itemParam.put("PAPERSUB", "1,2");
        }
        /*2019/12/06  Madison You 新增邮递服务*/
        String recipients = getObjStringInfo(serviceInfOobj, "Recipients");
        itemParam.put("ITEM_SEND_ADDRESSEE", recipients);
        String phoneNumber = getObjStringInfo(serviceInfOobj, "PhoneNumber");
        itemParam.put("ITEM_SEND_MOBILE", phoneNumber);
        String zipCode = getObjStringInfo(serviceInfOobj, "ZipCode");
        itemParam.put("ITEM_SEND_POSTCODE", zipCode);
        String reciceAddress = getObjStringInfo(serviceInfOobj, "ReciceAddress");
        itemParam.put("ITEM_SEND_ADDR", reciceAddress);
        String province = getObjStringInfo(serviceInfOobj, "Province");
        itemParam.put("ITEM_SEND_PROV", province);
        String city = getObjStringInfo(serviceInfOobj, "City");
        itemParam.put("ITEM_SEND_CITY", city);
        String acceptGetInfo = getObjStringInfo(serviceInfOobj, "AcceptGetInfo");
        itemParam.put("ITEM_SEND_REMARKS", acceptGetInfo);
        String MediService = getObjStringInfo(serviceInfOobj, "MediService");
        if (MediService.equals("")) {
            itemParam.put("IS_NEED_AGENCY", 0);
        }
        /*委托部门*/
        String DeptEntrust = getObjStringInfo(serviceInfOobj, "DeptEntrust");
        itemParam.put("WTBM", DeptEntrust);
        String SecondOffice = getObjStringInfo(serviceInfOobj, "SecondOffice");
        itemParam.put("HBCS", SecondOffice);
        /*是否进驻政务大厅*/
        String EntryCenterIf = getObjStringInfo(serviceInfOobj, "EntryCenterIf");
        itemParam.put("SFJZZWDT", changeYorN(EntryCenterIf));
        /*是否通办就近办*/
        String IsNearby = getObjStringInfo(serviceInfOobj, "IsNearby");
        itemParam.put("SFTBJJB", IsNearby);
        /*年审批数量限制*/
        String CountLimit = getObjStringInfo(serviceInfOobj, "CountLimit");
        itemParam.put("NSPSLXZ", CountLimit);
        /*是否支持预约办理*/
        String IsOrderHandle = getObjStringInfo(serviceInfOobj, "IsOrderHandle");
        itemParam.put("IS_APPOINT", IsOrderHandle);
        /*是否支持物流快递*/
        String IsLogisticsExpress = getObjStringInfo(serviceInfOobj, "IsLogisticsExpress");
        itemParam.put("EXPRESS_SUPPORT", changeYorN(IsLogisticsExpress));
        /*是否支持自助终端办理*/
        String IsServiceTerminals = getObjStringInfo(serviceInfOobj, "IsServiceTerminals");
        itemParam.put("SFZCZZZDBL", changeYorN(IsServiceTerminals));
        /*必须现场办理原因说明*/
        String SceneReason = getObjStringInfo(serviceInfOobj, "SceneReason");
        itemParam.put("BXXCBLYY", SceneReason);
        /*入驻方式*/
        String EnterMethod = getObjStringInfo(serviceInfOobj, "EnterMethod");
        itemParam.put("ZWDTRZFS", EnterMethod);
        /*窗口描述*/
        String WinInfo = getObjStringInfo(serviceInfOobj, "WinInfo");
        itemParam.put("CKMS", WinInfo);
        /*地图坐标*/
        String Location = getObjStringInfo(serviceInfOobj, "Location");
        itemParam.put("COORDINATES", Location);
        /*是否投资类项目*/
        String IsInvestProject = getObjStringInfo(serviceInfOobj, "IsInvestProject");
        itemParam.put("IS_INVEST", changeYorN(IsInvestProject));
        /*是否工程建设项目*/
        String EngineeringFlag = getObjStringInfo(serviceInfOobj, "EngineeringFlag");
        itemParam.put("SFGCJSXM", changeYorN(EngineeringFlag));
        /*是否提供代办服务*/
        String AgentFlag = getObjStringInfo(serviceInfOobj, "AgentFlag");
        itemParam.put("SFTGDBFW", changeYorN(AgentFlag));
        /*是否支持告知承诺或容缺受理审批*/
        String InformFlag = getObjStringInfo(serviceInfOobj, "InformFlag");
        itemParam.put("SFRQSL", InformFlag);
        /*邮寄办是否收费*/
        String PostChargeType = getObjStringInfo(serviceInfOobj, "PostChargeType");
        itemParam.put("YJBSFSF", PostChargeType);
        /*是否免政府材料*/
        String IsNeedGovFile = getObjStringInfo(serviceInfOobj, "IsNeedGovFile");
        itemParam.put("SFMZFCL", IsNeedGovFile);
        /*是否网上即审*/
        String IsOnlineTrial = getObjStringInfo(serviceInfOobj, "IsOnlineTrial");
        itemParam.put("SFWSJS", IsOnlineTrial);
        /*是否智能秒办*/
        String IsSmartSeconds = getObjStringInfo(serviceInfOobj, "IsSmartSeconds");
        itemParam.put("SFZNMB", IsSmartSeconds);
        //行政层次
        String PerformLevel = getObjStringInfo(serviceInfOobj, "PerformLevel");
        itemParam.put("EXERCISE_LEVEL", PerformLevel);
        itemParam.put("YWBLXT", "福州市----平潭县");
        /*权力来源*/
        String PowerSource = getObjStringInfo(serviceInfOobj, "PowerSource");
        itemParam.put("RIGHT_SOURCE", changePowerSource(PowerSource));
        //实施主体性质
        String DeptProperty = getObjStringInfo(serviceInfOobj, "DeptProperty");
        if (StringUtils.isNotEmpty(DeptProperty)) {
            itemParam.put("IMPL_DEPART_XZ", "0" + DeptProperty);
        } else {
            itemParam.put("IMPL_DEPART_XZ", "");
        }
        //结果名称
        String ResultName = getObjStringInfo(serviceInfOobj, "ResultName");
        itemParam.put("RESULT_NAME", ResultName);
        /*结果说明*/
        String FinishInfo = getObjStringInfo(serviceInfOobj, "FinishInfo");
        itemParam.put("RESULT_DESC", FinishInfo);
        //监督部门
        String MonitorDept = getObjStringInfo(serviceInfOobj, "MonitorDept");
        itemParam.put("JDBM", MonitorDept);
        /*邮递办类型*/
        String PostChargeMethod = getObjStringInfo(serviceInfOobj, "PostChargeMethod");
        itemParam.put("MFYDBLX", PostChargeMethod);
        /*代办是否收费*/
        String AgentServiceMode = getObjStringInfo(serviceInfOobj, "AgentServiceMode");
        itemParam.put("DBSFSF", AgentServiceMode);
        
        /*2021/09/16  Luffy Cai 省网事项下发新增字段接收*/
        
        /*高频事项主题代码*/
        String Gpsxzt = getObjStringInfo(serviceInfOobj, "Gpsxzt");
        itemParam.put("GPSXZT", Gpsxzt);
        /*跨省通办高频事项清单代码*/
        String Kstbgpsx = getObjStringInfo(serviceInfOobj, "Kstbgpsx");
        itemParam.put("KSTBGPSX", Kstbgpsx);
        /*异地代收开通范围区划代码*/
        String YddsPlaceAreaCode = getObjStringInfo(serviceInfOobj, "YddsPlaceAreaCode");
        itemParam.put("YDDSPLACEAREACODE", YddsPlaceAreaCode);
        
        //审批结果样本文件unid，对应附件表中的tchar1字段值
//        String ResultFile = getObjStringInfo(serviceInfOobj, "ResultFile");

        //暂未匹配字段 ?????---------------------------------------------------
        //公共服务类别，仅公共服务填报，来源字典【公共服务类别】
//        String AddTypeGf = getObjStringInfo(serviceInfOobj, "AddTypeGf");
        //仅办件类型为承诺上报件时填报，转报上级事项编码
//        String ToReportIds = getObjStringInfo(serviceInfOobj, "ToReportIds");
        //仅办件类型为联办件时填报，协办事项编码
//        String UniteServiceIds = getObjStringInfo(serviceInfOobj, "UniteServiceIds");
        //仅收办分离式时填报，业务办理系统标识
//        String PlumbMark = getObjStringInfo(serviceInfOobj, "PlumbMark");
        //事项版本号
//        String Version = getObjStringInfo(serviceInfOobj, "Version");
        //会办处室
//        String SecondOffice = getObjStringInfo(serviceInfOobj, "SecondOffice");
        //实施单位组织机构代码
//        String DeptCode = getObjStringInfo(serviceInfOobj, "DeptCode");
        //监督投诉
//        String MonitorComplain = getObjStringInfo(serviceInfOobj, "MonitorComplain");
        //监督部门
//        String MonitorDept = getObjStringInfo(serviceInfOobj, "MonitorDept");
        //入驻方式，1：行政服务中心窗口受理，2：后方办事大厅（分中心）办理，3：后方科室办理
//        String EnterMethod = getObjStringInfo(serviceInfOobj, "EnterMethod");
        //窗口描述
//        String WinInfo = getObjStringInfo(serviceInfOobj, "WinInfo");
        //备注信息
//        String WinRemark = getObjStringInfo(serviceInfOobj, "WinRemark");
        //流程图
//        String FlowContent = getObjStringInfo(serviceInfOobj, "FlowContent");
        //是否是投资类项目
//        String IsInvestProject = getObjStringInfo(serviceInfOobj, "IsInvestProject");
        
        //预审件接收人员
//        String Receiver = getObjStringInfo(serviceInfOobj, "Receiver");
        //邮递收件人信息
//        String AcceptGetInfo = getObjStringInfo(serviceInfOobj, "AcceptGetInfo");
//        <Recipients>收件人</Recipients>
//        <PhoneNumber>手机号码</PhoneNumber>
//        <ReciceAddress>收件地址</ReciceAddress>
        //流程图图片文件unid，对应附件表中的tchar1字段值
//        String FlowFile = getObjStringInfo(serviceInfOobj, "FlowFile");
        //行政层次
//        String PerformLevel = getObjStringInfo(serviceInfOobj, "PerformLevel");
        //实施主体性质
//        String DeptProperty = getObjStringInfo(serviceInfOobj, "DeptProperty");
        //联办机构
//        String CoopOrg = getObjStringInfo(serviceInfOobj, "CoopOrg");
        //中介服务
//        String MediService = getObjStringInfo(serviceInfOobj, "MediService");
        //结果名称
//        String ResultName = getObjStringInfo(serviceInfOobj, "ResultName");
        //审批结果样本文件unid，对应附件表中的tchar1字段值
//        String ResultFile = getObjStringInfo(serviceInfOobj, "ResultFile");
        //通办范围
//        String CommonRange = getObjStringInfo(serviceInfOobj, "CommonRange");
        //办理形式
//        String HandleForm = getObjStringInfo(serviceInfOobj, "HandleForm");
        //是否支持预约办理
//        String IsOrderHandle = getObjStringInfo(serviceInfOobj, "IsOrderHandle");
        //是否支持网上支付
//        String IsOnlinePay = getObjStringInfo(serviceInfOobj, "IsOnlinePay");
        //是否支持物流快递
//        String IsLogisticsExpress = getObjStringInfo(serviceInfOobj, "IsLogisticsExpress");
        //运行办理系统
//        String RunHandleSystem = getObjStringInfo(serviceInfOobj, "RunHandleSystem");
        //权力更新类型
//        String PowerType = getObjStringInfo(serviceInfOobj, "PowerType");
        //暂未匹配字段 ?????---------------------------------------------------
    }

    /**
     * 描述:转换权力来源字典
     *
     * @author Madison You
     * @created 2021/2/20 8:48:00
     * @param
     * @return
     */
    private Object changePowerSource(String powerSource) {
        String rightSource = "";
        if (StringUtils.isNotEmpty(powerSource)) {
            if (Objects.equals(powerSource, "1")) {
                rightSource = "01";
            } else if (Objects.equals(powerSource, "2")) {
                rightSource = "06";
            } else if (Objects.equals(powerSource, "3")) {
                rightSource = "02";
            } else if (Objects.equals(powerSource, "4")) {
                rightSource = "03";
            } else if (Objects.equals(powerSource, "5")) {
                rightSource = "04";
            } else if (Objects.equals(powerSource, "6")) {
                rightSource = "05";
            }
        }
        return rightSource;
    }

    private String changeYorN(String entryCenterIf) {
        if (StringUtils.isNotEmpty(entryCenterIf)) {
            if (Objects.equals(entryCenterIf, "Y")) {
                return "1";
            } else {
                return "0";
            }
        } else {
            return "";
        }
    }

    private String getChargeTypeCode(String chargeType) {
        // TODO Auto-generated method stub
        //获取办件类型编码
        String whereString = "tsd.dic_name = '"+chargeType+"'";
        String typeCode = "ChargeType";
        String finishGetTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return finishGetTypeCode;
    }

    private void readBillItem(String billUnid, Map<String, Object> itemParam) {
        Map<String,Object> billRightInfo = findBillItemById(billUnid);
        itemParam.put("DEPART_ID", billRightInfo.get("DEPART_ID"));
        itemParam.put("SSBMBM", billRightInfo.get("DEPART_CODE"));
        itemParam.put("CATALOG_CODE", billRightInfo.get("ITEM_CATALOGCODE"));
        itemParam.put("CATALOG_NAME", billRightInfo.get("CATALOG_NAME"));
        itemParam.put("CATALOG_NAME", "1");
        itemParam.put("BILL_ID", billRightInfo.get("BILL_ID"));
        itemParam.put("IMPL_DEPART", billRightInfo.get("DEPART_NAME"));
        itemParam.put("SXXZ", billRightInfo.get("ITEM_KIND"));
        itemParam.put("FTA_FLAG", billRightInfo.get("FTA_FLAG"));
        itemParam.put("RZBSDTFS", billRightInfo.get("RZBSDTFS"));
    }


    private String getjObjInfo(JSONObject jObj, String aString) {
        return ((JSONArray) (jObj).get(aString))==null?
                "":((JSONArray) (jObj).get(aString)).get(0).toString();
    }
    private String getObjStringInfo(JSONObject serviceInfOobj, String cs) {
        String returnInfoString = (JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)==null?
                "":(String) ((JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)).get(0);
        return returnInfoString;
    }
    private String getFinishGetTypeCode(String finishGetType) {
        // TODO Auto-generated method stub
        //获取办件类型编码
        String whereString = "tsd.dic_name = '"+finishGetType+"'";
        String typeCode = "FinishGetType";
        String finishGetTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return finishGetTypeCode;
    }

    private String getFinishTypeCode(String finishType) {
        // TODO Auto-generated method stub
        //获取办件类型编码
        String whereString = "tsd.dic_name = '"+finishType+"'";
        String typeCode = "FinishType";
        String finishTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return finishTypeCode;
    }

    private String getServiceTypeCode(String serviceType) {
        // TODO Auto-generated method stub
        //获取办件类型编码
        String whereString = "tsd.dic_name = '"+serviceType+"'";
        String typeCode = "ServiceItemType";
        String serviceTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return serviceTypeCode;
    }
    private String getFDSXCode(String name) {
        // TODO Auto-generated method stub
        //获取办件类型编码
        String whereString = "tsd.dic_name = '"+name+"'";
        String typeCode = "FDSXLX";
        String serviceTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return serviceTypeCode;
    }
    private String getYesOrNo(String name) {
        // TODO Auto-generated method stub
        //获取办件类型编码
        String whereString = "tsd.dic_name = '"+name+"'";
        String typeCode = "YesOrNo";
        String serviceTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return serviceTypeCode;
    }

    /**
     * 描述:将Y和N表示是否改为0和1表示是否
     *
     * @author Madison You
     * @created 2020/2/25 15:28:00
     * @param
     * @return
     */
    private String forMateYorN(String name) {
        if (name.equals("Y")) {
            return "1";
        } else {
            return "0";
        }
    }

    private String getCodeByTypeCodeAndWhere(String whereString, String typeCode) {
        List<Map<String, Object>> dic = dictionaryService.findByTCodeAndWhere( typeCode,whereString);
        String code = "";
        if (dic.size()!=0&&dic.get(0).get("DIC_CODE")!=null) {
            code = dic.get(0).get("DIC_CODE").toString();
        }
        return code;
    }

    /**
     * 
     * 描述   获取未绑定权利清单（事项）
     * @author Danto Huang
     * @created 2016-9-12 上午11:22:06
     * @param sqlFilter
     * @return
     */
    public Map<String,Object> findBillItemById(String unid){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.BILL_ID,T.DEPART_ID,T.BILL_NAME,C.BILL_NAME AS CATALOG_NAME,C.ITEM_CATALOGCODE,");
        sql.append("T.RZBSDTFS,");
        sql.append("D.DEPART_NAME,D.DEPART_CODE,C.ITEM_KIND,T.FTA_FLAG FROM T_WSBS_RIGHTBILL_ITEM T ");
        sql.append("LEFT JOIN T_WSBS_RIGHTBILL_CATALOG C ON C.BILL_ID = T.CATALOG_BILL_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID = T.DEPART_ID ");
        sql.append("WHERE t.bill_id = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), 
                new Object[]{unid}, null);
        Map<String,Object> billRightInfo = list.get(0);
        return billRightInfo;
    }


    private void getMXDXids(JSONObject serviceInfOobj, Map<String, Object> itemParam) {
        String forUser = getObjStringInfo(serviceInfOobj, "Foruser");
        String RightclassGerendx = getObjStringInfo(serviceInfOobj, "RightclassGerendx");
        String RightclassGerensx = getObjStringInfo(serviceInfOobj, "RightclassGerensx");
        String RightclassGerentd = getObjStringInfo(serviceInfOobj, "RightclassGerentd");
        String RightclassQiyedx = getObjStringInfo(serviceInfOobj, "RightclassQiyedx");
        String RightclassQiyezt = getObjStringInfo(serviceInfOobj, "RightclassQiyezt");
        String RightclassQiyejy = getObjStringInfo(serviceInfOobj, "RightclassQiyejy");
        String forUserType = getFaceUserType (forUser);
        StringBuffer typeIds = new StringBuffer("");
        itemParam.put("MXYHDX", forUserType);
        if (StringUtils.isNotEmpty(RightclassGerendx)) {
            //个人时填报，主题分类
            typeIds.append("4028818c512273e70151227569a40001,");
//            typeIds+="4028818c512273e70151227569a40001,";
            StringBuffer dicIds = new StringBuffer("");
            String sourceStr = RightclassGerendx;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                String dicId = getDicIdByDicName(sourceStrArray[i],"4028818c512273e70151227569a40001");
                if (StringUtils.isNotEmpty(dicId)) {
//                    typeIds=typeIds+dicId+",";
                    typeIds.append(dicId+",");
                    dicIds.append(dicId);
                    if (i < sourceStrArray.length-1) {
                        dicIds.append(",");
                    }
                }
            }
            itemParam.put("GRZTFL", dicIds.toString());
        }
        if (StringUtils.isNotEmpty(RightclassGerensx)) {
            //个人时填报，人生事件分类
//            typeIds+="4028818c512273e70151229ae7e00003,";
            typeIds.append("4028818c512273e70151229ae7e00003,");
            StringBuffer dicIds = new StringBuffer("");
            String sourceStr = RightclassGerensx;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                String dicId = getDicIdByDicName(sourceStrArray[i],"4028818c512273e70151229ae7e00003");
                if (StringUtils.isNotEmpty(dicId)) {
//                    typeIds=typeIds+dicId+",";
                    typeIds.append(dicId+",");
                    dicIds.append(dicId);
                    if (i < sourceStrArray.length-1) {
                        dicIds.append(",");
                    }
                }
            }
            itemParam.put("GRRSSJ", dicIds);
        }
        if (StringUtils.isNotEmpty(RightclassGerentd)) {
            //个人时填报，特定对象分类
//            typeIds+="4028818c512273e7015122a38a130004,";
            typeIds.append("4028818c512273e7015122a38a130004,");
//            String dicIds = "";
            StringBuffer dicIds = new StringBuffer("");
            String sourceStr = RightclassGerentd;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                String dicId = getDicIdByDicName(sourceStrArray[i],"4028818c512273e7015122a38a130004");
                if (StringUtils.isNotEmpty(dicId)) {
//                    typeIds=typeIds+dicId+",";
                    typeIds.append(dicId+",");
//                    dicIds = dicIds+ dicId;
                    dicIds.append(dicId);
                    if (i < sourceStrArray.length-1) {
//                        dicIds=dicIds+",";
                        dicIds.append(",");
                    }
                }
            }
            itemParam.put("GRTDRQ", dicIds.toString());
        }
        if (StringUtils.isNotEmpty(RightclassQiyedx)) {
            //法人填报，对象分类
//            typeIds+="4028818c512273e7015122c81f850007,";
            typeIds.append("4028818c512273e7015122c81f850007,");
//            String dicIds = "";
            StringBuffer dicIds = new StringBuffer("");
            String sourceStr = RightclassQiyedx;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                String dicId = getDicIdByDicName(sourceStrArray[i],"4028818c512273e7015122c81f850007");
                if (StringUtils.isNotEmpty(dicId)) {
//                    typeIds=typeIds+dicId+",";
                    typeIds.append(dicId+",");
//                    dicIds = dicIds+ dicId;
                    dicIds.append(dicId);
                    if (i < sourceStrArray.length-1) {
//                        dicIds+=",";
                        dicIds.append(",");
                    }
                }
            }
            itemParam.put("QYDXFL", dicIds);
        }
        if (StringUtils.isNotEmpty(RightclassQiyezt)) {
            //法人填报，主题分类
//            typeIds+="4028818c512273e7015122a452220005,";
            typeIds.append("4028818c512273e7015122a452220005,");
            StringBuffer dicIds = new StringBuffer("");
            String sourceStr = RightclassQiyezt;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                String dicId = getDicIdByDicName(sourceStrArray[i],"4028818c512273e7015122a452220005");
                if (StringUtils.isNotEmpty(dicId)) {
//                    typeIds=typeIds+dicId+",";
                    typeIds.append(dicId+",");
                    dicIds.append(dicId);
                    if (i < sourceStrArray.length-1) {
                        dicIds.append(",");
                    }
                }
            }
            itemParam.put("FRZTFL", dicIds);
        }
        if (StringUtils.isNotEmpty(RightclassQiyejy)) {
            //法人填报，经营活动分类
//            typeIds+="4028818c512273e7015122c872dc0008,";
            typeIds.append("4028818c512273e7015122c872dc0008,");
            StringBuffer dicIds = new StringBuffer("");
            String sourceStr = RightclassQiyejy;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                String dicId = getDicIdByDicName(sourceStrArray[i],"4028818c512273e7015122c872dc0008");
                if (StringUtils.isNotEmpty(dicId)) {
//                    typeIds=typeIds+dicId+",";
                    typeIds.append(dicId+",");
                    dicIds.append(dicId);
                    if (i < sourceStrArray.length-1) {
                        dicIds.append(",");
                    }
                }
            }
            itemParam.put("QYJYHD", dicIds);
        }
        itemParam.put("BUS_TYPEIDS", typeIds);
    }
    private String getDicIdByDicName(String name, String parentId) {
        // TODO Auto-generated method stub
        if ("金融投资".equals(name)||"融资信贷".equals(name)) {
            name = "融资信贷";
        }
        if ("财务税务".equals(name)||"税收财务".equals(name)) {
            name = "税收财务";
        }
        if ("立项审批".equals(name)||"投资审批".equals(name)) {
            name = "投资审批";
        }
        StringBuffer sql = new StringBuffer("select t.type_id from T_WSBS_BUSTYPE t ");
        sql.append(" where t.type_name = ? and t.parent_id = ? ");
        List<Map<String,Object>> busTypeList = dao.findBySql(sql.toString(), new Object[]{name,parentId}, null);
        String dicId = "";
        if (busTypeList.size()>0) {
            dicId = busTypeList.get(0).get("TYPE_ID")==null?
                    "":busTypeList.get(0).get("TYPE_ID").toString();
        }else {
//            System.out.println("未找到名为"+name+"的"+parentId+"类型");
            saveRecLog("未找到名为"+name+"的"+parentId+"类型");
        }
        return dicId;
    }

    private String getTypeIds(Map<String, Object> mxdxParam) {
        // TODO Auto-generated method stub
        String forUser = mxdxParam.get("forUser")==null?
                "":mxdxParam.get("forUser").toString();
        if (StringUtils.isNotEmpty(forUser)) {
            forUser = forUser.replace("面向", "");
            String forUserType = getFaceUserType (forUser);
            
        }
        return null;
    }

    private String getFaceUserType(String forUser) {
        //获取入驻大厅方式对应编码
        StringBuffer forUserTypes = new StringBuffer("");
        String forUserType = "";
        String[] forUserArray = forUser.split(",");
        for (int i = 0; i < forUserArray.length; i++) {
            if (StringUtils.isNotEmpty(forUserArray[i])) {
                forUser = forUserArray[i].replace("面向", "");
                }
            String whereString = "tsd.dic_name = '"+forUser+"'";
            String typeCode = "faceUserType";
            forUserType = getCodeByTypeCodeAndWhere(whereString, typeCode);
            if (StringUtils.isNotEmpty(forUserTypes)) {
                forUserTypes.append(",");
            }
            forUserTypes.append(forUserType);
        }
        return forUserTypes.toString();
    }
    /**
     * 
     * 描述 根据省网id获取本地目录编码
     * @author Kester Chen
     * @created 2017-11-27 上午10:02:55
     * @param parentUnid
     * @param projidMain 
     * @return
     */
    private String getCatalogCode(String parentUnid, String projidMain) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_SERVICEITEM_CATALOG t ");
        sql.append("left join T_WSBS_SERVICEITEM s on s.catalog_code = t.catalog_code ");
        sql.append("where t.catalog_id = ? or s.swb_item_code = '");
        sql.append(projidMain);
        sql.append("'");
        List<Map<String, Object>> catalogInfo = dao.findBySql(sql.toString(), new Object[]{parentUnid},null);
        String catalogCode ="";
        if (catalogInfo!=null&&catalogInfo.size()>0) {
            catalogCode = catalogInfo.get(0).get("CATALOG_CODE")==null?
                    "":catalogInfo.get(0).get("CATALOG_CODE").toString();
        }
        return catalogCode;
    }

    /**
     * 
     * 描述 获取本地目录编码
     * @author Kester Chen
     * @created 2019年10月31日 下午8:14:49
     * @param serviceInfOobj
     * @return
     */
    private String getCatalogCodeNew(JSONObject serviceInfOobj) {
        // TODO Auto-generated method stub
        String projidMain = getObjStringInfo(serviceInfOobj, "ProjidMain");
        String parentUnid = getObjStringInfo(serviceInfOobj, "parentUnid");
        String CataName = getObjStringInfo(serviceInfOobj, "CataName");
        String ParentName = getObjStringInfo(serviceInfOobj, "ParentName");
        String catName = "";
        if (StringUtils.isNotEmpty(CataName)) {
            catName = CataName;
        }else if (StringUtils.isNotEmpty(ParentName)) {
            catName = ParentName;
        }
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_SERVICEITEM_CATALOG t ");
        sql.append("left join T_WSBS_SERVICEITEM s on s.catalog_code = t.catalog_code ");
        sql.append("where t.catalog_id = ? or s.swb_item_code = ? or t.CATALOG_name = ?");
        List<Map<String, Object>> catalogInfo = 
                dao.findBySql(sql.toString(), new Object[]{parentUnid,projidMain,catName},null);
        String catalogCode ="";
        if (catalogInfo!=null&&catalogInfo.size()>0) {
            catalogCode = catalogInfo.get(0).get("CATALOG_CODE")==null?
                    "":catalogInfo.get(0).get("CATALOG_CODE").toString();
        }
        return catalogCode;
    }
    /**
     * 
     * 描述 创建权责清单子项
     * @author Kester Chen
     * @created 2017-11-22 下午4:05:29
     * @param unid
     * @param depatId
     * @param serviceName
     * @param parentUnid
     * @param inpropertyCode
     * @param ptzhspconn
     */
    private void creatRightbillItem(String unid, String depatId, String serviceName, String parentUnid,
            String inpropertyCode, Connection ptzhspconn) {
        // TODO Auto-generated method stub
        StringBuffer insertSql = new StringBuffer(" INSERT INTO T_WSBS_RIGHTBILL_ITEM ");
        insertSql.append(" (BILL_ID,DEPART_ID,BILL_NAME,STATUS,CATALOG_BILL_ID,IS_PUBLIC,FTA_FLAG,RZBSDTFS) ");
        insertSql.append(" VALUES (?,?,?,1,?,1,1,?) ");
        DbUtil.update(ptzhspconn, insertSql.toString(), 
                new Object[]{unid,depatId,serviceName,parentUnid,inpropertyCode}, false);
        billRightService.updateItemNum(parentUnid);
    }
    
    /**
     * 
     * 描述 更新权责清单子项
     * @author Kester Chen
     * @created 2017-11-22 下午4:05:29
     * @param unid
     * @param depatId
     * @param serviceName
     * @param parentUnid
     * @param inpropertyCode
     * @param ptzhspconn
     */
    private void updateRightbillItem(String unid, String depatId, String serviceName, String parentUnid,
            String inpropertyCode, Connection ptzhspconn) {
        // TODO Auto-generated method stub
        StringBuffer updateSql = new StringBuffer(" update T_WSBS_RIGHTBILL_ITEM t ");
        updateSql.append(" set t.depart_id=?,t.bill_name=?,t.catalog_bill_id=?,t.rzbsdtfs=? ");
        updateSql.append(" where t.bill_id=? ");
        DbUtil.update(ptzhspconn, updateSql.toString(), 
                new Object[]{depatId,serviceName,parentUnid,inpropertyCode,unid}, false);
    }
    /**
     * 
     * 描述 创建权责清单
     * @author Kester Chen
     * @created 2017-11-21 下午3:39:58
     * @param parentUnid
     * @param parentName
     * @param addType
     * @param deptName
     * @param ptzhspconn 
     */
    private void creatRightbillCatalog(String parentUnid, String parentName, String addTypeCode,
            String depatId, Connection ptzhspconn) {
        // TODO Auto-generated method stub
        StringBuffer insertSql = new StringBuffer("insert into T_WSBS_RIGHTBILL_CATALOG ");
        insertSql.append(" (bill_id,DEPART_ID,BILL_NAME,STATUS,Item_Kind,Is_Public,Fta_Flag) ");
        insertSql.append(" values (?,?,?,1,?,1,1) ");
        DbUtil.update(ptzhspconn, insertSql.toString(), 
                new Object[]{parentUnid,depatId,parentName,addTypeCode}, false);
    }
    /**
     * 
     * 描述 更新权责清单
     * @author Kester Chen
     * @created 2017-11-21 下午3:39:58
     * @param parentUnid
     * @param parentName
     * @param addType
     * @param deptName
     * @param ptzhspconn 
     */
    private void updateRightbillCatalog(String parentUnid, String parentName, String addTypeCode,
            String depatId, Connection ptzhspconn) {
        // TODO Auto-generated method stub
        StringBuffer updateSql = new StringBuffer("update T_WSBS_RIGHTBILL_CATALOG t  ");
        updateSql.append(" set t.depart_id=?,t.bill_name=? ");
        updateSql.append(" where t.bill_id = ? ");
        DbUtil.update(ptzhspconn, updateSql.toString(), 
                new Object[]{depatId,parentName,parentUnid}, false);
    }

    /**
     * 
     * 描述 创建目录
     * @author Kester Chen
     * @created 2017-11-21 下午3:39:58
     * @param parentUnid
     * @param parentName
     * @param projidMain 
     * @param addType
     * @param deptName
     * @param ptzhspconn 
     */
    private void creatCatalog(String parentUnid, String parentName, String addTypeCode,
            String depatId, String projidMain, Connection ptzhspconn) {
        // TODO Auto-generated method stub
        int maxSn = catalogService.getMaxSn();
        int cSn = maxSn + 1;
        Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                new String[] { "DEPART_ID" }, new Object[] { depatId });
        String departsxxzcode = department.get("DEPART_CODE").toString()
                + addTypeCode;
        String numcode = catalogService.getMaxNumCode(departsxxzcode);
        String catalogCode = departsxxzcode + numcode;
        log.info("保存目录信息：" + parentName + "  " + catalogCode);
        StringBuffer insertSql = new StringBuffer("insert into T_WSBS_SERVICEITEM_CATALOG  ");
        insertSql.append(" (catalog_id,catalog_name,catalog_code,c_sn,sxxz,depart_id" +
                ",status,depart_sxxz_code,num_code,fta_flag,child_depart_id,swb_catalog_code) ");
        insertSql.append(" values (?,?,?,?,?,?,1,?,?,1,?,?) ");
        DbUtil.update(ptzhspconn, insertSql.toString(), 
                new Object[]{parentUnid,parentName,catalogCode,
            cSn,addTypeCode,depatId,departsxxzcode,numcode,depatId,projidMain}, false);

//        Map<String,Object> billCatalog = new HashMap<String, Object>();
//        billCatalog.put("ITEM_CATALOGCODE", catalogCode);
//        departCatalogService.saveOrUpdate(billCatalog, "T_WSBS_RIGHTBILL_CATALOG", parentUnid);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/27 10:56:00
     * @param
     * @return
     */
    private void creatCatalog(String swbCataUnid , String swbCataName , String swbCataState ,
                              Connection ptzhspconn){
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO T_WSBS_SERVICEITEM_STDCATALOG (CATALOG_ID,CATALOG_NAME,CATALOG_STATE) VALUES ");
        sql.append(" ( ? , ? , ?) ");
        DbUtil.update(ptzhspconn, sql.toString(), new Object[]{swbCataUnid, swbCataName, swbCataState}, false);
    }


    /**
     * 
     * 描述 更新目录
     * @author Kester Chen
     * @param cInfo 
     * @created 2017-11-21 下午3:39:58
     * @param parentUnid
     * @param parentName
     * @param projidMain 
     * @param addType
     * @param deptName
     * @param ptzhspconn 
     */
    private void updateCatalog(List<Map<String, Object>> cInfo, String parentUnid, 
            String parentName, String addTypeCode,
            String depatId, String projidMain, Connection ptzhspconn) {
        // TODO Auto-generated method stub
        String catalog_id = cInfo.get(0).get("catalog_id").toString();
        String catalog_name = cInfo.get(0).get("CATALOG_NAME").toString();
        if (StringUtils.isNotEmpty(parentName)) {
            catalog_name = parentName;
        }
        String sxxz = addTypeCode;
        String child_depart_id = cInfo.get(0).get("child_depart_id").toString();
//        if (bddepatId.equals(depatId)) {
//            child_depart_id = cInfo.get(0).get("child_depart_id").toString();
//        }else {
//            child_depart_id = depatId;
//        }
        StringBuffer updateSql = new StringBuffer("update T_WSBS_SERVICEITEM_CATALOG t   ");
        updateSql.append(" set t.catalog_name = ?,t.sxxz=?,t.depart_id=?, ");
        updateSql.append(" t.status=1,t.child_depart_id=?,t.swb_catalog_code=? ");
        updateSql.append(" where t.catalog_id = ? ");
        DbUtil.update(ptzhspconn, updateSql.toString(), 
                new Object[]{catalog_name,sxxz,depatId,child_depart_id,projidMain,catalog_id}, false);

    }
    
    private List<Map<String, Object>> getCatalogInfo(String catalogCode) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_SERVICEITEM_CATALOG t ");
        sql.append(" where t.catalog_code = '");
        sql.append(catalogCode);
        sql.append("'");
        List<Map<String,Object>> status = dao.findBySql(sql.toString(), new Object[]{},null);
        return status;
        
    }
    private List<Map<String, Object>> getCatalogInfoByName(String catalogName) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_SERVICEITEM_CATALOG t ");
        sql.append(" where t.catalog_name = '");
        sql.append(catalogName);
        sql.append("'");
        List<Map<String,Object>> status = dao.findBySql(sql.toString(), new Object[]{},null);
        return status;
        
    }
    private List<Map<String, Object>> isExistSwbCatalogCode(String Infoprojid) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.* from T_WSBS_SERVICEITEM t ");
        sql.append(" where t.swb_item_code = '");
        sql.append(Infoprojid);
        sql.append("'");
        List<Map<String,Object>> status = dao.findBySql(sql.toString(), new Object[]{},null);
        return status;
    }
    
    private List<Map<String, Object>> isExistSwbItemCode(String Infoprojid) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_SERVICEITEM t where t.swb_item_code = ? ");
        List<Map<String,Object>> status = dao.findBySql(sql.toString(), new Object[]{Infoprojid},null);
        return status;
        
    }
    private List<Map<String, Object>> isExistSwbRightBillI(String id) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_RIGHTBILL_ITEM t where t.bill_id = ? ");
        List<Map<String,Object>> status = dao.findBySql(sql.toString(), new Object[]{id},null);
        return status;
    }
    private List<Map<String, Object>> isExistSwbRightBillC(String id) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_RIGHTBILL_CATALOG t where t.bill_id = ? ");
        List<Map<String,Object>> status = dao.findBySql(sql.toString(), new Object[]{id},null);
        return status;
    }
    public int isExistRightbillCatalog(String id) {
        StringBuffer sql = new StringBuffer("select * from T_WSBS_RIGHTBILL_CATALOG t where t.bill_id = ? ");
        int status = dao.findBySql(sql.toString(), new Object[]{id},null).size();
        return status;
    }

    public int isExistRightbillItem(String id) {
        StringBuffer sql = new StringBuffer("select * from T_WSBS_RIGHTBILL_ITEM t where t.bill_id = ? ");
        int status = dao.findBySql(sql.toString(), new Object[]{id},null).size();
        return status;
    }
    
    public int isExistCatalog(String id) {
        StringBuffer sql = new StringBuffer("select * from T_WSBS_SERVICEITEM_CATALOG t where t.catalog_id = ? ");
        int status = dao.findBySql(sql.toString(), new Object[]{id},null).size();
        return status;
    }
    
    public List<Map<String, Object>> isExistServiceItem(String code) {
        StringBuffer sql = new StringBuffer("select * from T_WSBS_SERVICEITEM t where t.swb_item_code = ? ");
        sql.append(" order by t.create_time desc");
        List<Map<String,Object>> itemInfo = dao.findBySql(sql.toString(), new Object[]{code},null);
        return itemInfo;
    }
    public List<Map<String, Object>> getRightbillCatalog(String code) {
        StringBuffer sql = new StringBuffer("select r.* from T_WSBS_RIGHTBILL_CATALOG r where r.item_catalogcode = ");
        sql.append("( select t.catalog_code from T_WSBS_SERVICEITEM t where t.swb_item_code like '");
        sql.append(code);
        sql.append("%' and  rownum =1 )");
        List<Map<String,Object>> itemInfo = dao.findBySql(sql.toString(), new Object[]{},null);
        return itemInfo;
    }
    public List<Map<String, Object>> getRightbillItem(String code) {
        StringBuffer sql = new StringBuffer("select r.* from T_WSBS_RIGHTBILL_ITEM r where r.serviceitem_code = ( ");
        sql.append("select t.item_code from T_WSBS_SERVICEITEM t where t.swb_item_code like '");
        sql.append(code);
        sql.append("%' and  rownum =1 )");
        List<Map<String,Object>> itemInfo = dao.findBySql(sql.toString(), new Object[]{},null);
        return itemInfo;
    }
    
    public List<Map<String, Object>> getAttrInfoByCode(String code) {
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_APPLYMATER t ");
        sql.append(" where t.mater_code = ? ");
        List<Map<String,Object>> attrInfo = dao.findBySql(sql.toString(), new Object[]{code},null);
        return attrInfo;
    }
    public List<Map<String, Object>> getServiceSpecialInfo(String name,String itemId) {
        StringBuffer sql = new StringBuffer("select t.*, t.rowid from T_WSBS_SERVICEITEM_LINK t ");
        sql.append(" where t.link_name = ? and t.item_id = ? ");
        List<Map<String,Object>> attrInfo = dao.findBySql(sql.toString(), new Object[]{name,itemId},null);
        return attrInfo;
    }
    private String getDeptId(String deptName,String deptCode) {
        String depatId = "";
        StringBuffer deptSql = new StringBuffer("select * from T_MSJW_SYSTEM_DEPARTMENT t ");
        deptSql.append("where t.usc = ? ");
        List<Map<String,Object>> depList = dao.findBySql(deptSql.toString(), new Object[]{deptCode},null);
        if (depList.size()>0) {
            depatId = depList.get(0).get("DEPART_ID")==null?
                    "":depList.get(0).get("DEPART_ID").toString();
        }else{
            //获取部门id
            String depname = deptName.replace("平潭综合实验", "");
            depname = depname.replace("便民服务中心", "");
            depname = depname.replace("农业服务中心", "");
            if("区财金局".equals(depname)||"区财政金融局".equals(depname)){
                depname = "区财政金融局";
            }
            if("区卫生和计划生育局".equals(depname)||"区卫生与计划生育局".equals(depname)){
                depname = "区卫生和计划生育局";
            }
            if("区旅游发展局".equals(depname)||"区旅游发展委员会".equals(depname)){
                depname = "区旅游发展委员会";
            }
            if("区党工委党群工作部".equals(depname)||"区党群工作部".equals(depname)){
                depname = "区党群工作部";
            }
            if ("区农村发展局".equals(depname)) {
                depname = "区农业农村局";
            }
            deptSql = new StringBuffer("select * from T_MSJW_SYSTEM_DEPARTMENT t ");
            deptSql.append("where t.depart_name = ? and t.tree_level < 4 ");
            depList = dao.findBySql(deptSql.toString(), new Object[]{depname},null);
            if (depList.size()>0) {
                depatId = depList.get(0).get("DEPART_ID")==null?
                        "":depList.get(0).get("DEPART_ID").toString();
            }else {
//                System.out.println("找不到名为【"+deptName+"】的部门");
                saveRecLog("找不到名为【"+deptName+"】的部门");
            }
        }
        return depatId;
    }
    private String getDeptCode(String deptid) {
        //获取部门code
        StringBuffer deptSql = new StringBuffer("select * from T_MSJW_SYSTEM_DEPARTMENT t ");
        deptSql.append("where t.DEPART_ID = ?  ");
        List<Map<String,Object>> depList = dao.findBySql(deptSql.toString(), new Object[]{deptid},null);
        String depatCode = "";
        if (depList.size()>0) {
            depatCode = depList.get(0).get("DEPART_CODE")==null?
                    "":depList.get(0).get("DEPART_CODE").toString();
        }else {
//            System.out.println("找不到名为【"+deptName+"】的部门");
            saveRecLog("找不到id为【"+deptid+"】的部门");
        }
        return depatCode;
    }

    private String getAddTypeCode(String addType) {
        //获取入驻大厅方式对应编码
        String whereString = "tsd.dic_name = '"+addType+"'";
        String typeCode = "ServiceItemXz";
        String addTypeCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return addTypeCode;
    }
    
    private String getInpropertyCode(String inproperty) {
        //获取清单性质对应编码
        String inpropertyString = inproperty.substring(0,3);
        String whereString = "tsd.dic_name like '%"+inpropertyString+"%'";
        String typeCode = "RZBSDTFS";
        String inpropertyCode = getCodeByTypeCodeAndWhere(whereString, typeCode);
        return inpropertyCode;
    }
    /**
     * 
     * 描述 更新数据的状态
     * @author Flex Hu
     * @created 2016年1月27日 下午4:08:51
     * @param dataId
     * @param status
     */
    public void updateDataStatus(String dataId,int status){
        StringBuffer sql = new StringBuffer("update PROVINCEITEMINFO S ");
        sql.append(" SET S.OPER_STATUS=? WHERE S.UNID=? ");
        dao.executeSql(sql.toString(), new Object[]{status,dataId});
    }
    /**
     * 
     * 描述 属性ptzhspDbUrl get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbUrl() {
        if (ptzhspDbUrl == null || StringUtils.isEmpty(this.ptzhspDbUrl))
            this.ptzhspDbUrl = this.dictionaryService.getDicNames("NWReadWWDB", "recDbUrl");
        return ptzhspDbUrl;
    }

    /**
     * 
     * 描述 属性ptzhspDbUrl set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setPtzhspDbUrl(String ptzhspDbUrl) {
        this.ptzhspDbUrl = ptzhspDbUrl;
    }

    /**
     * 
     * 描述 属性ptzhspDbUsername get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbUsername() {
        if (ptzhspDbUsername == null || StringUtils.isEmpty(this.ptzhspDbUsername))
            this.ptzhspDbUsername = this.dictionaryService.getDicNames("NWReadWWDB", "dbUser");
        return this.ptzhspDbUsername;
    }

    /**
     * 
     * 描述 属性ptzhspDbUsername set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setPtzhspDbUsername(String ptzhspDbUsername) {
        this.ptzhspDbUsername = ptzhspDbUsername;
    }

    /**
     * 
     * 描述 属性ptzhspDbPassword get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbPassword() {
        if (ptzhspDbPassword == null || StringUtils.isEmpty(this.ptzhspDbPassword))
            this.ptzhspDbPassword = this.dictionaryService.getDicNames("NWReadWWDB", "dbPassword");
        return ptzhspDbPassword;
    }

    /**
     * 
     * 描述 属性ptzhspDbPassword set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setPtzhspDbPassword(String ptzhspDbPassword) {
        this.ptzhspDbPassword = ptzhspDbPassword;
    }

    /**
     * 描述:与本地数据字典对应
     *
     * @author Madison You
     * @created 2019/11/25 12:46:00
     * @param
     * @return
     */
    private String reviseGetTypeUnids(String getTypeUnids) {
        String newGetTypeUnids = getTypeUnids.replace("paper", "01").replace("upload", "02")
                .replace("license", "03").replace("form", "04");
        return newGetTypeUnids;
    }

    /**
     * 描述:保存材料信息到中间表，排序为省网材料排序
     *
     * @author Madison You
     * @created 2019/12/5 12:05:00
     * @param
     * @return
     */
    private void saveMaterItemBySn(String applyMaterId, String itemId, String attrSn , String isNeed) {
        StringBuffer insertSql = new StringBuffer();
        insertSql.append(" insert into T_WSBS_SERVICEITEM_MATER (ITEM_ID,MATER_ID,MATER_SN,MATER_ISNEED) values (?,?,?,?) ");
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(" update T_WSBS_SERVICEITEM_MATER set mater_sn = ?,mater_isneed = ? where item_id = ? and mater_id = ? ");
        boolean flag = applyMaterDao.isExistMaterItem(applyMaterId, itemId);
        if (!flag) {
            dao.executeSql(insertSql.toString(), new Object[]{itemId, applyMaterId, attrSn, isNeed});
        } else {
            dao.executeSql(updateSql.toString(), new Object[]{attrSn, isNeed, applyMaterId, itemId});
        }
    }

    /**
     * 描述:收费配置
     *
     * @author Madison You
     * @created 2020/2/25 11:14:00
     * @param
     * @return
     */
    private void handleCharge( String ChargeDetailsJson ,
                              String itemId) {
        if (ChargeDetailsJson != null) {
            JSONObject ChargeDetailsObj = JSONObject.parseObject(ChargeDetailsJson);
            JSONArray ChargeDetail = ((JSONArray) ((JSONObject) ChargeDetailsObj.get("ChargeDetails")).
                    get("ChargeDetail"));
            if (ChargeDetail != null) {
                for (int m = 0; m < ChargeDetail.size(); m++) {
                    JSONObject jObj = ((JSONObject) ChargeDetail.get(m));
                    if (jObj != null && !jObj.isEmpty()) {
                        if ((jObj).get("ChargeDetailUnid") != null &&
                                ((JSONArray) (jObj).get("ChargeDetailUnid")).get(0) != null) {
                            HashMap<String, Object> variables = new HashMap<>();
                            /*明细id*/
                            String ChargeDetailUnid = getjObjInfo(jObj, "ChargeDetailUnid");
                            /*收费项目名称*/
                            String ChargeDetailName = getjObjInfo(jObj, "ChargeDetailName");
                            /*排序号*/
                            String ChargeDetailOrderid = getjObjInfo(jObj, "ChargeDetailOrderid");
                            /*状态（Y启用，N禁用）*/
                            String ChargeDetailStatus = getjObjInfo(jObj, "ChargeDetailStatus");
                            /*执行单位编码*/
                            String ChargeDetailOrgCode = getjObjInfo(jObj, "ChargeDetailOrgCode");
                            /*执收标准*/
                            String ChargeDetailStandard = getjObjInfo(jObj, "ChargeDetailStandard");
                            /*计量单位*/
                            String ChargeDetailUnit = getjObjInfo(jObj, "ChargeDetailUnit");
                            /*执行单位名称*/
                            String ChargeDetailOrgName = getjObjInfo(jObj, "ChargeDetailOrgName");
                            /*收费项目代码*/
                            String ChargeDetailCode = getjObjInfo(jObj, "ChargeDetailCode");
                            /*执收数量*/
                            String ChargeDetailAmount = getjObjInfo(jObj, "ChargeDetailAmount");
                            variables.put("ITEM_ID", itemId);
                            variables.put("CHARGEDETAILNAME", ChargeDetailName);
                            variables.put("CHARGEDETAILORDERID", ChargeDetailOrderid);
                            variables.put("CHARGEDETAILSTATUS", ChargeDetailStatus);
                            variables.put("CHARGEDETAILORGCODE", ChargeDetailOrgCode);
                            variables.put("CHARGEDETAILSTANDARD", ChargeDetailStandard);
                            variables.put("UNITS", ChargeDetailUnit);
                            variables.put("CHARGEDETAILORGNAME", ChargeDetailOrgName);
                            variables.put("CHARGEDETAILCODE", ChargeDetailCode);
                            variables.put("CHARGEDETAILAMOUNT", ChargeDetailAmount);
                            dao.saveOrUpdateForAssignId(variables, "T_WSBS_SERVICEITEM_CHARGE", ChargeDetailUnid);
                        }
                    }
                }
            }
        }

    }

}

