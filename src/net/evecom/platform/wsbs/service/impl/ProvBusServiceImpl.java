/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.bsfw.controller.BusController;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.ApasInfoDao;
import net.evecom.platform.wsbs.service.ApasInfoService;
import net.evecom.platform.wsbs.service.ProvBusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 描述  服务总线数据处理
 * @author Sundy Sun
 * @version v1.0
 */
@Service("provBusService")
public class ProvBusServiceImpl extends BaseServiceImpl implements ProvBusService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProvBusServiceImpl.class);

    /**
     * dao
     */
    @Resource
    private ApasInfoDao dao;
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 处理省总线数据信息
     * @param variable
     * @return
     */
    public AjaxJsonCode parseBusService(Map<String, Object> variable) {
        boolean flag = true;
        AjaxJsonCode j = new AjaxJsonCode();
        j.setCode("00");
        j.setMsg("接口调用成功");
        String sn = StringUtil.getString(variable.get("SN"));//办件编号
        log.info("接收省网总线办件号" + sn);
        if (StringUtils.isEmpty(sn)) {
            j.setCode("04");
            j.setMsg("办件编号为空");
            return j;
        }
        String fromCode = StringUtil.getString(variable.get("fromCode"));//数据推送方社会信用代码
        if (StringUtils.isEmpty(fromCode)) {
            j.setCode("04");
            j.setMsg("数据推送方社会信用代码为空");
            return j;
        }
        String wssp = StringUtil.getString(variable.get("wssp"));//办件信息xml
        log.info("接收省网总线办件信息" + wssp);
        if (StringUtils.isEmpty(wssp)) {
            j.setCode("04");
            j.setMsg("办件信息XML为空");
            return j;
        }
        try {
            //办件信息解析
            parseProvinceBj(wssp, sn);
        }catch(DataAccessException sqlException){
            log.error(sqlException.getMessage());
            j.setCode("-1");
            j.setMsg("接口调用失败，wssp办件数据重复");
            flag = false;
        } catch (Exception e){
            log.error(e.getMessage());
            j.setCode("-1");
            j.setMsg("接口调用失败，wssp办件数据重复");
            flag = false;
        }
        if (flag) {
            try {
                //过程信息解析
                String next = StringUtil.getString(variable.get("next"));//过程信息xml
                log.info("接收省网总线过程信息" + next);
                if (StringUtils.isNotBlank(next)) {
                    parseProvinceGc(next, sn);
                }
            } catch(DataAccessException sqlException){
                log.error(sqlException.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，next过程信息数据重复");
                flag = false;
            } catch (Exception e) {
                log.error(e.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，next过程信息数据不规范");
                flag = false;
            }
        }
        if (flag) {
            try {
                //计时暂停
                String suspend = StringUtil.getString(variable.get("suspend"));//计时暂停xml
                log.info("接收省网总线计时暂停信息" + suspend);
                parseProvinceJszt(suspend, sn);
            } catch(DataAccessException sqlException){
                log.error(sqlException.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，计时暂停信息数据重复");
                flag = false;
            } catch (Exception e) {
                log.error(e.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，suspend计时暂停不规范");
                flag = false;
            }
        }

        if (flag) {
            try {
                //计时恢复
                String resume = StringUtil.getString(variable.get("resume"));//计时恢复xml
                log.info("接收省网总线计时恢复信息" + resume);
                parseProvinceJshf(resume, sn);
            } catch(DataAccessException sqlException){
                log.error(sqlException.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，计时恢复信息数据重复");
                flag = false;
            }catch (Exception e) {
                log.error(e.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，resume计时恢复不规范");
                flag = false;
            }
        }
        if (flag) {
            try {
                //办件结果
                String finish = StringUtil.getString(variable.get("finish"));//办件结果xml
                parseProvinceJg(finish, sn);
                // 解析省网总线下发结果信息（电子证照、批文）
                parseProvinceJgfj(finish, sn);
            }catch(DataAccessException sqlException){
                log.error(sqlException.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，finish办件结果数据重复");
                flag = false;
            } catch (Exception e) {
                log.error(e.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，finish办件结果不规范");
                flag = false;
            }
        }
        if (flag) {
            try {
                //快递信息
                String express = StringUtil.getString(variable.get("express"));//快递信息xml
                parseProvinceExpress(express, sn);
            }catch(DataAccessException sqlException){
                log.error(sqlException.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，express快递信息数据重复");
                flag = false;
            } catch (Exception e) {
                log.error(e.getMessage());
                j.setCode("-1");
                j.setMsg("接口调用失败，express快递信息不规范");
            }
        }
        return j;
    }
    /**
     * 描述 解析处理办件数据
     *
     * @author Derek Zhang
     * @created 2016年1月22日 上午11:00:13
     * @param wssp  办件信息xml
     * @param sn  省总线办件id
     */
    public void parseProvinceBj(String wssp,String sn) {
        Document document = XmlUtil.stringToDocument(wssp);
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
        String operator = root.selectSingleNode("//Case/Body/Operator").asXML();
        String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
        Node formInfoNode=root.selectSingleNode("//Case/Body/FormInfos");
        String formInfos="";
        if(formInfoNode!=null){
            formInfos=formInfoNode.asXML();
        }
        // 定义服务事项
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + apasInfo, "gb2312");
        String proposerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + proposer, "gb2312");
        String operatorJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + operator, "gb2312");
        String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + attrs, "gb2312");
        String formInfoJson=XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + formInfos, "gb2312");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        String serviceId="";
        if(((JSONObject) obj.get("ApasInfo")).get("ServiceID")!=null){
            serviceId=StringUtil.getString(((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("ServiceID")).get(0));
        }
        String serviceCode = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("ServiceCode")).get(0);
        String serviceName= StringUtil.getString(((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("ServiceCode")).get(0));
        JSONObject attrObj = JSONObject.parseObject(attrsJson);
        JSONArray arr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));

        /*Madison You 2020/4/29新增 快递信息*/
        String expressJson = null;
        Node expressesNode = root.selectSingleNode("//Case/Body/Expresses");
        if (expressesNode != null) {
            String express = expressesNode.asXML();
            expressJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='gb2312'?>" + express, "gb2312");
        }

        StringBuffer attrIds = new StringBuffer();
        if (arr!=null) {
            for (int m = 0; m < arr.size(); m++) {
                JSONObject jObj = ((JSONObject) arr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                        if (attrIds.length() != 0) {
                            attrIds.append(",");
                        }
                        attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                    }
                }
            }
        }
        StringBuffer insertSql=new StringBuffer( "insert into T_PROV_SERVICEBUSDATA ");
        insertSql.append(" (id, swb_item_id, swb_item_code,SWB_ITEM_NAME, create_time, status, ");
        insertSql.append(" apasinfo, proposer, operator, express , attrs, attrids,");
        insertSql.append("type,FORMINFOS,ROUTER,SN,ORIGINAL_DATA ) values ");
        insertSql.append(" ( ? , ? , ?,? , ? , 0 , ? , ? , ? , ? , ? , ? , ? ,?,?,?,?) ");
        dao.executeSql(insertSql.toString(),new Object[] { unid, serviceId, serviceCode,serviceName,
                DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, proposerJson, operatorJson,
                expressJson , attrsJson, attrIds.toString(), "10",formInfoJson,routerJson,sn,wssp});
    }
    /**
     *
     * 描述    解析省网总线下发办件过程信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:47:41
     */
    private void parseProvinceGc(String nextStr,String requestOfsn) {
        if(StringUtils.isEmpty(nextStr)) return;
        String beginContent="<?xml version=\"1.0\" encoding=\"gb2312\"?>";
        String endContent="</Body></Case>";
        List<String> nextStrs=findContent(nextStr,beginContent,endContent);
        for(String next:nextStrs){
            Document document = XmlUtil.stringToDocument(next);
            Element root = document.getRootElement();

            Node caseNode = root.selectSingleNode("//Case/Body");
            String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
            Node routerNode=root.selectSingleNode("//Case/Router");
            String routerJson="";
            if(routerNode!=null){
                String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
                routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + router, "GB2312");
            }
            String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
            String opinions  = root.selectSingleNode("//Case/Body/Node").asXML();
            String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + apasInfo, "GB2312");
            String opinionsJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + opinions, "GB2312");
            JSONObject obj = JSONObject.parseObject(apasInfoJson);
            String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);
            StringBuffer insertSql=new StringBuffer("insert into T_PROV_SERVICEBUSDATA  ");
            insertSql.append(" (id, create_time, status, apasinfo, opinionsornode,exe_id,type,ROUTER,SN  ");
            insertSql.append(" ,ORIGINAL_DATA ) values");
            insertSql.append(" ( ? , ? , 0 , ? , ? , ?, ?,?,?,?) ");
            dao.executeSql(insertSql.toString(), new Object[] {unid,
                    DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, opinionsJson, sn, "80",routerJson,requestOfsn,next });
        }
    }
    /*

     */
    public List<String> findContent(String st,String startContent,String endContent) {
        List <String> listData=new ArrayList<>();
        while(st.indexOf(startContent)>=0) {
            st=st.substring(st.indexOf(startContent)+startContent.length());
            if(st.indexOf(endContent)>0){
                String sx=st.substring(0,st.indexOf(endContent));
                listData.add(startContent+sx+endContent);
            }
        }
        return listData;
    }
    /**
     *
     * 描述    解析省网总线下发计时暂停信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:48:51
     */
    private void parseProvinceJszt(String suspend,String requestOfsn){
        if(StringUtils.isEmpty(suspend)) return;
        Document document = XmlUtil.stringToDocument(suspend);
        Element root = document.getRootElement();

       Node caseNode = root.selectSingleNode("//Case/Body");
        String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
        Node routerNode=root.selectSingleNode("//Case/Router");
        String routerJson="";
        if(routerNode!=null){
            String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
            routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + router, "GB2312");
        }

        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String node = root.selectSingleNode("//Case/Body/Node").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + apasInfo, "GB2312");
        String nodeJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + node, "GB2312");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into T_PROV_SERVICEBUSDATA "
                + "  (id, create_time, status, apasinfo, opinionsornode,exe_id,type,ROUTER,SN,ORIGINAL_DATA ) values "
                + " ( ? , ? , 0 , ? , ? , ? , ?, ? , ?,?) ";
       dao.executeSql( insertSql, new Object[] {unid, DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"),
               apasInfoJson, nodeJson, sn , "81" ,routerJson,requestOfsn,suspend});
    }

    /**
     *
     * 描述    解析省网总线下发计时恢复信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:48:51
     */
    private void parseProvinceJshf(String resume,String requestOfsn){
        if(StringUtils.isEmpty(resume)) return;
        Document document = XmlUtil.stringToDocument(resume);
        Element root = document.getRootElement();

       Node caseNode = root.selectSingleNode("//Case/Body");
        String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
        Node routerNode=root.selectSingleNode("//Case/Router");
        String routerJson="";
        if(routerNode!=null){
            String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
            routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + router, "GB2312");
        }

        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String node = root.selectSingleNode("//Case/Body/Node").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + apasInfo, "GB2312");
        String nodeJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + node, "GB2312");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into T_PROV_SERVICEBUSDATA "
                + "  (id, create_time, status, apasinfo, opinionsornode,exe_id,type,ROUTER,SN,ORIGINAL_DATA ) values "
                + " ( ? , ? , 0 , ? , ? , ? , ?, ? , ?,?) ";
        dao.executeSql(insertSql, new Object[] { unid, DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"),
                apasInfoJson, nodeJson, sn , "82",routerJson,requestOfsn,resume });

    }

    /**
     *
     * 描述    解析省网总线下发结果信息
     * @author Danto Huang
     * @created 2019年5月21日 上午9:30:54
     */
    private void parseProvinceJg(String finish,String requestOfsn){
        if(StringUtils.isEmpty(finish)) return;
        Document document = XmlUtil.stringToDocument(finish);
        Element root = document.getRootElement();

       Node caseNode = root.selectSingleNode("//Case/Body");
        String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
        String  type=caseNode.getParent().attributeValue("type");//省总线数据类型
        if(!"30".equals(type))  return;
        Node routerNode=root.selectSingleNode("//Case/Router");
        String routerJson="";
        if(routerNode!=null){
            String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
            routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + router, "GB2312");
        }
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        Node formInfosNode=root.selectSingleNode("//Case/Body/FormInfos");// 表单信息，即业务数据
        String formInfosJson="";
        if(formInfosNode!=null){
            String formInfo=root.selectSingleNode("//Case/Body/FormInfos").asXML();//路由信息
            formInfosJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + formInfo, "GB2312");
        }

        String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
        String resultattrs = root.selectSingleNode("//Case/Body/ResultAttrs").asXML();
        String documents = root.selectSingleNode("//Case/Body/Documents").asXML();
        String sms = root.selectSingleNode("//Case/Body/SMS").asXML();

        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + apasInfo, "GB2312");
        String attrsJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + attrs, "GB2312");
        String resultattrsJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + resultattrs, "GB2312");
        String documentsJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + documents, "GB2312");
        String smsJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + sms, "GB2312");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        JSONObject attrObj = JSONObject.parseObject(attrsJson);
        JSONArray arr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
        JSONObject resultattrObj = JSONObject.parseObject(resultattrsJson);
        JSONArray resultattr = ((JSONArray) ((JSONObject) resultattrObj.get("ResultAttrs")).get("ResultAttr"));
        StringBuffer attrIds = new StringBuffer();
        if(arr!=null){
            for (int m = 0; m < arr.size(); m++) {
                JSONObject jObj = ((JSONObject) arr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                        if (attrIds.length() != 0) {
                            attrIds.append(",");
                        }
                        attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                    }
                }
            }
        }
        if(resultattr!=null){
            for (int m = 0; m < resultattr.size(); m++) {
                JSONObject jObj = ((JSONObject) resultattr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                        if (attrIds.length() != 0) {
                            attrIds.append(",");
                        }
                        attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                    }
                }
            }
        }
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);
        String insertSql = "insert into T_PROV_SERVICEBUSDATA  (id, create_time, status, attrids, "
                + "  apasinfo, attrs,exe_id,type,resultattrs,ROUTER,SN,FORMINFOS,DOCUMENTS,SMS,ORIGINAL_DATA ) values "
                + " ( ? , ? , 0 , ? , ? , ? , ? , ? , ?, ? , ?, ? , ?,?,?) ";
        dao.executeSql(insertSql,   new Object[] { unid, DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"),
                attrIds, apasInfoJson, attrsJson, sn, "85", resultattrsJson
                ,routerJson,requestOfsn,formInfosJson,documentsJson,smsJson,finish });
    }
    /**
     *
     * 描述    解析省网总线下发结果信息（电子证照、批文）
     * @author Danto Huang
     * @created 2019年5月21日 上午9:32:11
     */
    private void parseProvinceJgfj(String finish,String requestOfsn){
        if(StringUtils.isEmpty(finish)) return;
        Document document = XmlUtil.stringToDocument(finish);
        Element root = document.getRootElement();

       Node caseNode = root.selectSingleNode("//Case/Body");
        String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
        String  type=caseNode.getParent().attributeValue("type");//省总线数据类型
        if(!"31".equals(type))  return;
        Node routerNode=root.selectSingleNode("//Case/Router");
        String routerJson="";
        if(routerNode!=null){
            String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
            routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + router, "GB2312");
        }

        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + apasInfo, "GB2312");
        String extend = root.selectSingleNode("//Case/Body/Extend").asXML();
        String extendJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + extend, "GB2312");
        String surface = root.selectSingleNode("//Case/Body/Surface").asXML();
        String surfaceJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + surface, "GB2312");
        String resultattrs= root.selectSingleNode("//Case/Body/ResultAttrs").asXML();
        String resultattrsJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + resultattrs, "GB2312");

        JSONObject resultattrObj = JSONObject.parseObject(resultattrsJson);
        JSONArray resultattr = ((JSONArray) ((JSONObject) resultattrObj.get("ResultAttrs")).get("ResultAttr"));
        StringBuffer attrIds = new StringBuffer();
        if(resultattr!=null){
            for (int m = 0; m < resultattr.size(); m++) {
                JSONObject jObj = ((JSONObject) resultattr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                        if (attrIds.length() != 0) {
                            attrIds.append(",");
                        }
                        attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                    }
                }
            }
        }
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);
        StringBuffer insertSql=new StringBuffer("insert into T_PROV_SERVICEBUSDATA  ");
        insertSql.append(" (id, create_time, status, apasinfo, exe_id, type,ROUTER,SN,extend,SURFACE");
        insertSql.append(" ,resultattrs,attrids,ORIGINAL_DATA )  ");
        insertSql.append("  ( ? , ? , 0 , ? , ? , ? , ? , ?,?,?,?,?,?)  ");
        dao.executeSql(insertSql.toString(), new Object[] {unid, DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss")
            , apasInfoJson, sn, "86" ,routerJson,requestOfsn,extendJson,surfaceJson ,resultattrsJson,attrIds,finish});
    }
    /**
     *
     * 描述    解析省网总线下发快递信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:48:51
     */
    private void parseProvinceExpress(String express,String requestOfsn){
        if(StringUtils.isEmpty(express)) return;
        Document document = XmlUtil.stringToDocument(express);
        Element root = document.getRootElement();

       Node caseNode = root.selectSingleNode("//Case/Body");
        String  unid=caseNode.getParent().attributeValue("unid");//省总线主键id
        Node routerNode=root.selectSingleNode("//Case/Router");
        String routerJson="";
        if(routerNode!=null){
            String router=root.selectSingleNode("//Case/Router").asXML();//路由信息
            routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='GB2312'?>" + router, "GB2312");
        }
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String expressService = root.selectSingleNode("//Case/Body/Express").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + apasInfo, "GB2312");
        String expressServiceJson = XmlUtil.xml2Json("<?xml version='1.0'  encoding='GB2312'?>" + expressService, "GB2312");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into T_PROV_SERVICEBUSDATA "
                + "  (id, create_time, status, apasinfo, EXPRESSSERVICE,exe_id,type,ROUTER,SN,ORIGINAL_DATA ) values "
                + " ( ? , ? , 0 , ? , ? , ? , ?, ? , ?,?) ";
        dao.executeSql(insertSql, new Object[] { unid, DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"),
                apasInfoJson, expressServiceJson, sn , "33",routerJson,requestOfsn,express });

    }
}
