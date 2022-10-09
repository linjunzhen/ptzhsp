/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.project.enumpro.EnumProjectCatalogid;
import net.evecom.platform.project.enumpro.EnumProjectPushType;
import net.evecom.platform.project.constant.ProjectConstant;
import net.evecom.platform.project.dao.ProvinceInterfaceDao;
import net.evecom.platform.project.service.ProvinceInterfaceService;
import net.evecom.platform.project.util.ProjectOfProvinceUtil;
import net.evecom.platform.wsbs.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述消防设计推送到总线服务接口
 * 描述
 * @author Adrian Bian
 * @created 2020年1月6日 下午12:15:43
 */
@Service("provinceInterfaceService")
public class ProvinceInterfaceServiceImpl extends BaseServiceImpl implements ProvinceInterfaceService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectApplyServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ProvinceInterfaceDao dao;
    /**
     * exeDataService
     */
    @Autowired
    private ExeDataService exeDataService;
    /**
     * exeDataService
     */
    @Autowired
    private ProjectService projectService;

    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     *
     * 描述 消防设计推送到省服务总线
     *
     * @author Flex Hu
     * @created 2015年8月12日 下午4:52:20
     * @return
     */
    public void sendToProvince() {
        //获取需要推送的办件exeIds
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.EXE_ID from JBPM6_EXECUTION t,TB_FC_PROJECT_INFO g ");
        sql.append(" where t.bus_recordid = g.FC_PROJECT_INFO_ID and g.push_status = ? and t.run_status=? ");
        sql.append(" order by t.create_time asc");
        param.add(0);
        param.add(2);
        List<Map<String, Object>> executions = dao.findBySql(sql.toString(), param.toArray(), null);
        for(Map<String,Object> execution:executions){
            String exeId=StringUtil.getValue(execution,"EXE_ID");
            sendToProvinceByExeId(exeId);
        }
    }

    /**
     *
     * 描述 消防设计推送到省服务总线
     *
     * @author Flex Hu
     * @created 2015年8月12日 下午4:52:20
     * @param exeId
     * @return
     */
    public Map<String, Object> sendToProvinceByExeId(String exeId) {
        List<Map<String,Object>> datas=Lists.newArrayList();

        //消防项目主体数据信息组装
        List<Map<String,Object>> xmzts=getPushOfDatas(exeId,ProjectConstant.XMZRZT_TABLE_NAME,
                EnumProjectCatalogid.XMZRZT.getVal(), ProjectConstant.XMZRZT);
        datas.addAll(xmzts);
        //项目单体数据信息组装
        List<Map<String,Object>> xmdts=getPushOfDatas(exeId,ProjectConstant.XMDT_TABLE_NAME,
                EnumProjectCatalogid.XMDT.getVal(),ProjectConstant.XMDT);
        datas.addAll(xmdts);
        //项目基本数据信息组装
        Map<String,Object> xmjbxx=getDataOfXmjbxx(exeId);
        datas.add(xmjbxx);

        //项目设计审查数据组装
        Map<String,Object> xmsc=getDataOfXmscsq(exeId);
        datas.add(xmsc);

        //调用封装的推送接口
        Map<String,Object> result= pushToProvinceByHttp(datas);
        String code=StringUtil.getValue(result,"code");
        //code为1代表成功，推送标志位置为1，并且保存返回的主键
        if("1".equals(code)){
            Map<String,Object> buscordMap=exeDataService.getBuscordMap(exeId);
            String data=StringUtil.getValue(result,"data");
            Map<String,Object> pushResult=new HashMap<>();
            pushResult.put("PUSH_STATUS","1");
            pushResult.put("PK_ID",data);
            dao.saveOrUpdate(pushResult,"TB_FC_PROJECT_INFO",
                    StringUtil.getString(buscordMap.get("FC_PROJECT_INFO_ID")));
            log.info(String.format("消防设计审核向住建上报办件exeId:%s成功",exeId));
        }
        return result;
    }

    /**
     * 获取子业务项单表数据Datas
     * @param exeId  办件id
     * @param busTableName  子业务表项表名
     * @param catalogid  传输时需要的目录id
     * @param xmlTemplateCode xml模板编码
     * @return
     */
    private List<Map<String,Object>> getPushOfDatas(String exeId,String busTableName,
                                                    String catalogid,String xmlTemplateCode){
        List<Map<String,Object>> datas=Lists.newArrayList();
        Map<String,Object> buscordMap =exeDataService.getBuscordMap(exeId);
        //获取需要推送的数据
        String prjCode=StringUtil.getValue(buscordMap,"PRJ_CODE");
        String prjNum=StringUtil.getValue(buscordMap,"PRJ_NUM");
        StringBuffer sql=new StringBuffer("SELECT * FROM ");
        sql.append(busTableName);
        sql.append(" WHERE PRJ_CODE=? AND PRJ_NUM=? ");
        List<Map<String,Object>> pushDatas=dao.findBySql(sql.toString(),new Object[]{prjCode,prjNum},null);
        for(Map<String,Object>  pushData:pushDatas){
            String busPkName = (String) dao.getPrimaryKeyName(busTableName).get(0); //主键名
            String recordId=StringUtil.getValue(pushData,busPkName);//主键值
            EnumProjectPushType pushType=EnumProjectPushType.ADD;//推送类型
            //获取需要传输的xmlstr
            String xmlstr=getXfsjXml(busTableName, recordId, pushType.getVal(),xmlTemplateCode);
            Map<String,Object> data=new HashMap<>();
            data.put("catalogid",catalogid);
            data.put("xmlstr",xmlstr);
            datas.add(data);
        }
       return datas;
    }
    /**
     * 获取消防设计的xml
     * @param tableName
     * @param recordId
     * @param type
     */
    private String getXfsjXml(String tableName, String recordId, String type,String xmlTemplateCode) {
        String busPkName = (String) dao.getPrimaryKeyName(tableName).get(0);//获取业务表主键名称
        Map<String,Object> busMap=dao.getByJdbc(tableName,
                new String[]{busPkName}, new Object[]{recordId});
        //拼装模板数据
        Map<String,Object> configMap=new HashMap<String,Object>();
        Map<String,Object> table=new HashMap<String,Object>();
        Map<String,Object> row=new HashMap<String,Object>();
        configMap.put("TABLE",table);
        table.put("ROW",row);
        row.put("TYPE",type);
        row.putAll(busMap);
        //获取模板
        Map<String, Object> dataAbutment = dao.getByJdbc("T_BSFW_DATAABUTMENT",
                new String[]{"AABUT_CODE"}, new String[]{xmlTemplateCode});
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        String resultXml=FreeMarkerUtil.getResultString(configXml, configMap);
        return  resultXml;
    }

    /**
     * 获取项目基本信息数据
     * @param exeId
     */
    private Map<String,Object> getDataOfXmjbxx(String exeId){
        Map<String,Object> data=new HashMap();
        Map<String,Object> configMap=new HashMap<String,Object>();
        Map<String,Object> table=new HashMap<String,Object>();
        Map<String,Object> pushMap=new HashMap();
        configMap.put("TABLE",table);
        table.put("ROW",pushMap);
        Map<String,Object> buscordMap =exeDataService.getBuscordMap(exeId);
        //组装需要推送的数据
        pushMap.put("TYPE",EnumProjectPushType.ADD.getVal()); //推送类型
        String prjCode=StringUtil.getValue(buscordMap,"PRJ_CODE");
        pushMap.put("PRJ_CODE",prjCode);//项目编码
        pushMap.put("PRJ_NUM",StringUtil.getValue(buscordMap,"PRJ_NUM"));//项目编号
        pushMap.put("PRJ_NAME",StringUtil.getValue(buscordMap,"PRJ_NAME"));//项目名称
        pushMap.put("ADDRESS",StringUtil.getValue(buscordMap,"ADDRESS"));//项目地点
        pushMap.put("BUILD_CORPNAME",StringUtil.getValue(buscordMap,"BUILD_CORPNAME"));//建设单位
        pushMap.put("LEGAL_REPRESENT",StringUtil.getValue(buscordMap,"LEGAL_REPRESENT"));//法定代表人
        pushMap.put("LEGAL_INFORMATION",StringUtil.getValue(buscordMap,"LEGAL_INFORMATION"));//法人联系电话
        pushMap.put("CONTACTOR",StringUtil.getValue(buscordMap,"CONTACTOR"));//联系人（项目负责人）
        pushMap.put("CONTACT_INFORMATION",StringUtil.getValue(buscordMap,"CONTACT_INFORMATION"));//联系电话
        pushMap.put("FC_PRJ_TYPE",StringUtil.getValue(buscordMap,"FC_PRJ_TYPE"));//消防项目类别
        pushMap.put("FC_CHARACTER",StringUtil.getValue(buscordMap,"FC_CHARACTER"));//消防使用性质
        pushMap.put("PK_ID",StringUtil.getValue(buscordMap,"PK_ID"));//PK_ID
        //获取模板
        Map<String, Object> dataAbutment = dao.getByJdbc("T_BSFW_DATAABUTMENT",
                new String[]{"AABUT_CODE"}, new String[]{ProjectConstant.XMJB});
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        String xmlstr=FreeMarkerUtil.getResultString(configXml, configMap);//传送的xml
        data.put("catalogid", EnumProjectCatalogid.XMJB.getVal());
        data.put("xmlstr",xmlstr);
        return data;
    }
    /**
     * 获取项目审查申请数据
     * @param exeId
     */
    private Map<String,Object> getDataOfXmscsq(String exeId){
        Map<String,Object> data=new HashMap();
        Map<String,Object> configMap=new HashMap<String,Object>();
        Map<String,Object> table=new HashMap<String,Object>();
        Map<String,Object> pushMap=new HashMap();
        configMap.put("TABLE",table);
        table.put("ROW",pushMap);
        Map<String,Object> buscordMap =exeDataService.getBuscordMap(exeId);
        //获取需要推送的数据
        pushMap.put("TYPE",EnumProjectPushType.ADD.getVal()); //推送类型
        String prjCode=StringUtil.getValue(buscordMap,"PRJ_CODE");
        pushMap.put("PRJ_CODE",prjCode);//项目编码
        pushMap.put("PRJ_NUM",StringUtil.getValue(buscordMap,"PRJ_NUM"));//项目编号

        pushMap.put("XS_NUM",getXSNUM(exeId));//审查申请编号
        pushMap.put("BEGIN_DETE",StringUtil.getValue(buscordMap,"BEGIN_DETE"));//计划开工日期
        pushMap.put("END_DATE",StringUtil.getValue(buscordMap,"END_DATE"));//计划竣工日期
        pushMap.put("FC_FACILITIES",StringUtil.getValue(buscordMap,"FC_FACILITIES"));//消防设施
        pushMap.put("OTHER_INSTRUCTIONS",StringUtil.getValue(buscordMap,"OTHER_INSTRUCTIONS"));//其他说明
        pushMap.put("SOURCE",StringUtil.getValue(buscordMap,"1"));//数据来源
        pushMap.put("PK_ID",StringUtil.getValue(buscordMap,"PK_ID"));//PK_ID
        //获取审核人及审核部门
        Map<String,Object> assignInfo=getAssignInfo(exeId);
        pushMap.putAll(assignInfo);
        Map<String, Object> dataAbutment = dao.getByJdbc("T_BSFW_DATAABUTMENT",
                new String[]{"AABUT_CODE"}, new String[]{ProjectConstant.XMSCSQ});
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        String xmlstr=FreeMarkerUtil.getResultString(configXml, configMap);
        data.put("catalogid", EnumProjectCatalogid.XMSCSQ.getVal());
        data.put("xmlstr",xmlstr);
        return data;
    }

    /**
     * 获取消防设计审查申请标识
     * @return
     */
    private String getXSNUM(String exeId){
        Map<String,Object> busRecord=exeDataService.getBuscordMap(exeId);
        String xsNum=StringUtil.getValue(busRecord,"XS_NUM");
        //判断数据库是否存在了，存在就返回数据库里的值
        if(StringUtils.isEmpty(xsNum)){
            String busRecordId=StringUtil.getValue(busRecord,"FC_PROJECT_INFO_ID");
            xsNum=projectService.getGcxmCode(ProjectConstant.AREA_CODE,ProjectConstant.XS);
            Map<String,Object> map=new HashMap<>();
            map.put("XS_NUM",xsNum);
            dao.saveOrUpdate(map,"TB_FC_PROJECT_INFO",busRecordId);
        }
        return xsNum;
    }
    /**
     * 获取审查人员信息
     * @param exeId
     */
    private Map<String,Object> getAssignInfo(String exeId){
        //获取业务审核人信息
        Map<String,Object> assignInfo=new HashMap<>();
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT T.ASSIGNER_CODE,T.ASSIGNER_NAME FROM ");
        sql.append(" JBPM6_TASK T WHERE T.EXE_ID=?  AND  ");
        sql.append(" T.ASSIGNER_CODE IS NOT NULL ORDER BY T.CREATE_TIME DESC ");
        Map<String,Object> assigner=exeDataService.getFirstByJdbc(sql.toString(),new Object[]{exeId});
        String assignerCode=StringUtil.getValue(assigner,"ASSIGNER_CODE");
        String assignerName=StringUtil.getValue(assigner,"ASSIGNER_NAME");
        //获取部门名称
        StringBuffer sql1=new StringBuffer();
        sql1.append("select d.depart_name from ");
        sql1.append(" t_msjw_system_sysuser u left join t_msjw_system_department");
        sql1.append(" d on u.depart_id=d.depart_id   ");
        sql1.append(" where u.username=? ");
        Map<String,Object> depart=dao.getByJdbc(sql1.toString(),new Object[]{assignerCode});

        assignInfo.put("ASSIGN_NAME",assignerName);
        assignInfo.put("ASSIGN_DEPART_NAME",depart.get("DEPART_NAME"));
        return assignInfo;
    }

    /**
     * 推送到省服务总线
     * @param datas
     * @return
     */
    public Map<String,Object> pushToProvinceByHttp(List<Map<String,Object>> datas){
        Properties properties = FileUtil.readProperties("project.properties");
        String id = properties.getProperty("ZX_START_ID");
        //初始化
        Map<String, String> headMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        //datas->data
//        Map<String,Object> data=new HashMap<>();
//        data.put("catalogid",catalogid);
//        data.put("xmlstr",xmlstr);

        //parameters->{areaCode,datas}
        parameters.put("areaCode", ProjectConstant.AREA_CODE);
        parameters.put("datas", datas);
        //最后拼装成请求参数
        params.put("id", id);
        params.put("parameters", JSON.toJSONString(parameters));

        String header = ProjectOfProvinceUtil.getZxAccessToken();
        headMap.put("Authorization", header);
        String url = properties.getProperty("ZX_STARTWORKFLOW_URL");
        //请求接口
        String result = HttpSendUtil.sendPostParamsH(url, headMap, params);
        Map<String,Object> rMap=(Map)JSONObject.parse(result);
        return rMap;
    }

}
