/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.project.dao.ProjectFinishManageDao;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.project.service.ProjectFinishManageService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysUserService;

/**
 * 描述 竣工验收备案信息Service
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月17日 上午10:43:15
 */
@SuppressWarnings("rawtypes")
@Service("projectFinishManageService")
public class ProjectFinishManageServiceImpl extends BaseServiceImpl implements ProjectFinishManageService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectApplyServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectFinishManageDao dao;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    
    /**
     * sysUserService
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    public Map<String, Object> findExeInfo(String projectCode, String itemCode) {
        return dao.findExeInfo(projectCode, itemCode);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void pushFlowinfo() {
        // TODO Auto-generated method stub
        // 获取质量竣工验收监督信息
        List<Map<String, Object>> list = getPrjFinishList();
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        if (null != list && list.size() > 0) {
            // 竣工验收备案信息
            Map<String, Object> proFinishData = pushProFinish(list);
            datas.add(proFinishData);
            // 竣工验收信息
            Map<String, Object> proFinishCheckData = pushProFinishCheck(list);
            datas.add(proFinishCheckData);
            // 工程项目参与单位及相关负责人信息
            Map<String, Object> gcxmdwjryData = pushGcxmdwjry(list);
            datas.add(gcxmdwjryData);
            /*
             * // 质量监督信息 Map<String, Object> pushQualityCheckData = pushQualityCheck(list);
             * datas.add(pushQualityCheckData);
             */
        }
        if (null != datas && datas.size() > 0) {
            parameters.put("areaCode", "350128");
            parameters.put("datas", datas);
            Map<String, Object> resultMap = projectApplyService.startWorkflow(parameters);
            String code = resultMap.get("code") == null ? "" : resultMap.get("code").toString();
            if (StringUtils.isNotEmpty(code) && code.equals("1")) {// 状态码1为成功，其他为失败，如：70003
                // 无效的Token
                Map<String, Object> sgxk = null;
                for (Map<String, Object> map : list) {
                    String YW_ID = map.get("sourceid") == null ? "" : map.get("sourceid").toString();
                    sgxk = new HashMap<String, Object>();
                    sgxk.put("YW_ID", YW_ID);
                    sgxk.put("PUSHFLOW_STATUS", 1);
                    sgxk.put("PUSHFLOW_RESULT", JSON.toJSONString(resultMap));
                    dao.saveOrUpdate(sgxk, "TB_PROJECT_FINISH_MANAGE", YW_ID);
                }
            } 
        }
    }
    
   
    /**
     * 
     * 描述：获取质量竣工验收监督信息
     * 
     * @author Luffy Cai
     * @created 2020年5月8日 上午10:34:31
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getPrjFinishList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.EXE_ID,g.* from JBPM6_EXECUTION t,TB_PROJECT_FINISH_MANAGE g ");
        sql.append(" where t.bus_recordid = g.yw_id and g.PUSHFLOW_STATUS = 0 and t.run_status=2");
        sql.append(" order by t.create_time asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        int unitcodeNum = 1;
        for (Map<String, Object> map : list) {
            String PRJNUM = map.get("PRJNUM") == null ? "" : map.get("PRJNUM").toString();
            String YW_ID = map.get("YW_ID") == null ? "" : map.get("YW_ID").toString();
            String exeId = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
            map.put("CREATE_TIME",
                    DateTimeUtil.formatDateStr(CREATE_TIME, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"));
            Map<String, Object> task = getTaskList(exeId);
            map.put("ASSIGNER_DEPNAMES", task.get("ASSIGNER_DEPNAMES"));
            map.put("TEAM_NAMES", task.get("TEAM_NAMES"));
            map.put("HANDLE_OPINION", task.get("HANDLE_OPINION"));
            map.put("END_TIME", task.get("END_TIME"));
            for (Map.Entry<String, Object> a : map.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();
                if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                    map.put(a.getKey(), "<![CDATA[" + value + "]]>");
                }
            }
            String projectCode = map.get("PROJECTCODE")==null?"":map.get("PROJECTCODE").toString();
            Map<String, Object> exeMap = this.findExeInfo(projectCode, null);
            Map<String, Object> sgxkMap = new HashMap<String,Object>();
            if (exeMap != null && exeMap.size() > 0) {
                // 获取施工许可信息
                String ywid = exeMap.get("BUS_RECORDID").toString();
                sgxkMap = this.getByJdbc("T_BSFW_GCJSSGXK", new String[] { "YW_ID" },
                        new Object[] { ywid });
            }
            List<Map<String, Object>> dwryList = new ArrayList<Map<String, Object>>();
            unitcodeNum = getDwryList(sgxkMap, dwryList, "JSDW", "99", unitcodeNum, PRJNUM, YW_ID);// 建设单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "DJDW", "5", unitcodeNum, PRJNUM, YW_ID);// 代建、工程总承包（EPC）、PPP等单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "SGDW", "3", unitcodeNum, PRJNUM, YW_ID);// 施工单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "JLDW", "4", unitcodeNum, PRJNUM, YW_ID);// 监理单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "KCDW", "1", unitcodeNum, PRJNUM, YW_ID);// 勘察单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "SJDW", "2", unitcodeNum, PRJNUM, YW_ID);// 设计单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "SGTSCDW", "99", 0, PRJNUM, YW_ID);// 施工图审查单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "KZJDW", "99", unitcodeNum, PRJNUM, YW_ID);// 控制价（预算价）计价文件编制单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "JCDW", "6", unitcodeNum, PRJNUM, YW_ID);// 检测单位
            unitcodeNum = getDwryList(sgxkMap, dwryList, "ZBDW", "99", unitcodeNum, PRJNUM, YW_ID);// 招标代理单位
            map.put("dwryList", dwryList);            
            map.put("sourceid", YW_ID);
        }
        return list;
    }
    
    /**
     * 
     * 描述： 获取单位人员信息
     * 
     * @author Rider Chen
     * @created 2020年5月8日 下午2:33:50
     * @param map
     * @param dwryList
     * @param key
     * @param CORPROLENUM
     */
    @SuppressWarnings("unchecked")
    private int getDwryList(Map<String, Object> map, List<Map<String, Object>> dwryList, String key, String CORPROLENUM,
            int unitcodeNum, String PRJNUM, String YW_ID) {
        int pkidNum = 1;
        String json = map.get(key + "_JSON") == null ? "" : map.get(key + "_JSON").toString();
        if (StringUtils.isNotEmpty(json) && json.indexOf("<!") == -1) {
            if (json.indexOf("[") == 0) {
                List<Map<String, Object>> dwList = JSON.parseObject(json, List.class);
                for (Map<String, Object> map2 : dwList) {
                    map2.put("CORPROLENUM", CORPROLENUM);
                    map2.put("UNITCODE", PRJNUM + "-" + StringUtil.getFormatNumber(3, unitcodeNum + ""));
                    String CONTRACTNUMBER = map.get("CONTRACTNUMBER") == null ? ""
                            : map.get("CONTRACTNUMBER").toString();

                    String STRUCTQUALTYPES = map2.get("STRUCTQUALTYPES") == null ? ""
                            : map2.get("STRUCTQUALTYPES").toString();
                    map2.put("STRUCTQUALTYPES", ProjectApplyServiceImpl.getFormatStructqualtype(STRUCTQUALTYPES));
                    map2.put("CONTRACTNUMBER", CONTRACTNUMBER);
                    for (Map.Entry<String, Object> a : map2.entrySet()) {
                        String value = a.getValue() == null ? "" : a.getValue().toString();
                        if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                            map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                        }
                    }
                    map2.put("pkid", "<![CDATA[" + YW_ID + "-" + key + "-" + pkidNum + "]]>");
                    map2.put("IDCARDTYPENUM", "<![CDATA[111]]>");
                    dwryList.add(map2);
                    unitcodeNum++;
                    pkidNum++;
                }
            } else {
                Map<String, Object> map2 = JSON.parseObject(json, Map.class);
                map2.put("CORPROLENUM", CORPROLENUM);
                map2.put("UNITCODE", PRJNUM + "-" + StringUtil.getFormatNumber(3, unitcodeNum + ""));
                String CONTRACTNUMBER = map.get("CONTRACTNUMBER") == null ? "" : map.get("CONTRACTNUMBER").toString();
                String STRUCTQUALTYPES = map2.get("STRUCTQUALTYPES") == null ? ""
                        : map2.get("STRUCTQUALTYPES").toString();
                map2.put("STRUCTQUALTYPES", ProjectApplyServiceImpl.getFormatStructqualtype(STRUCTQUALTYPES));
                map2.put("CONTRACTNUMBER", CONTRACTNUMBER);
                for (Map.Entry<String, Object> a : map2.entrySet()) {
                    String value = a.getValue() == null ? "" : a.getValue().toString();
                    if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                        map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                    }
                }
                map2.put("pkid", "<![CDATA[" + YW_ID + "-" + key + "-" + pkidNum + "]]>");
                map2.put("IDCARDTYPENUM", "<![CDATA[111]]>");
                dwryList.add(map2);
                unitcodeNum++;
                pkidNum++;
            }
        }
        return unitcodeNum;
    }
    

    /**
     * 
     * 描述： ： 竣工验收备案信息
     * 
     * @author Luffy Cai
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushProFinish(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_JGYSBAXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("jgysbaList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_JGYSBAXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }    
    
    /**
     * 
     * 描述： 竣工验收信息
     * 
     * @author Luffy Cai
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushProFinishCheck(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_JGYSXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("jgysList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_JGYSXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }

    /**
     * 
     * 描述： 质量监督信息
     * 
     * @author Luffy Cai
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushQualityCheck(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_ZLJD });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("zljdList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_ZLJD);
        info.put("xmlstr", xmlBuffer);
        return info;
    }    
    
    /**
     * 
     * 描述： 工程项目参与单位及相关负责人信息
     * 
     * @author Rider Chen
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushGcxmdwjry(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { "WEB2186"});
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_GCXMDWXXJXGFZRXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }    
    
    
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTaskList(String exeId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        // 是否流程归档明细查看
        sql.append(" select T.* FROM JBPM6_TASK T ");
        sql.append(" where 1=1  and T.STEP_SEQ !=0 ");
        sql.append(" and T.EXE_ID =?  order by T.STEP_SEQ desc ");
        params.add(exeId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        for (Map<String, Object> task : list) {

            // 获取任务审核的类型
            String assignerType = (String) task.get("ASSIGNER_TYPE");
            if (AllConstant.ASSIGNER_TYPE_SYSTEMUSER.equals(assignerType)) {
                // 获取团队审核人编码
                String teamCodes = (String) task.get("TEAM_CODES");
                // 定义审核部门名称
                StringBuffer assignerDepNames = new StringBuffer("");
                List<String> depNames = new ArrayList<String>();
                for (String userAccount : teamCodes.split(",")) {
                    Map<String, Object> userInfo = sysUserService.getUserInfo(userAccount);
                    if (userInfo != null) {
                        // 获取部门名称
                        String departName = (String) userInfo.get("DEPART_NAME");
                        if (StringUtils.isNotEmpty(departName)) {
                            if (!depNames.contains(departName)) {
                                assignerDepNames.append(departName).append(",");
                            }
                            depNames.add(departName);
                        }
                    }
                }
                if (depNames.size() >= 1) {
                    assignerDepNames.deleteCharAt(assignerDepNames.length() - 1);
                }
                task.put("ASSIGNER_DEPNAMES", assignerDepNames.toString());
            }
        }
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }    
    
    /**
     * 描述 用数据包填充模版
     * 
     * @author Derek Zhang
     * @created 2015年10月22日 上午10:34:59
     * @param xmlMap
     * @param configXml
     * @return
     */
    private StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
    }    

    @SuppressWarnings("unchecked")
    @Override
    public void pushXfysFlowinfo(){
        // TODO Auto-generated method stub
        // 获取消防验收（备案）申请信息
        List<Map<String, Object>> list = getXfysList();
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        if(null!=list &&list.size() > 0){
            // 消防验收（备案）申请信息
            Map<String, Object> xfysData = pushXfysInfo(list);
            datas.add(xfysData);
        }
        if(null!=datas && datas.size()>0){
            parameters.put("areaCode", "350128");
            parameters.put("datas", datas);
            Map<String, Object> resultMap = projectApplyService.startWorkflow(parameters);
            String code = resultMap.get("code") == null ? "" : resultMap.get("code").toString();
            if (StringUtils.isNotEmpty(code) && code.equals("1")) {// 状态码1为成功，其他为失败，如：70003
                Map<String, Object> sgxk = null;
                for (Map<String, Object> map : list) {
                    String YW_ID = map.get("sourceid") == null ? "" : map.get("sourceid").toString();
                    sgxk = new HashMap<String, Object>();
                    sgxk.put("YW_ID", YW_ID);
                    sgxk.put("PUSH_STATUS", 1);
                    dao.saveOrUpdate(sgxk, "T_BSFW_GCJSXFYS", YW_ID);
                }
            }
        }
    }   
    
    
    /**
     * 
     * 描述：获取质量竣工验收监督信息
     * 
     * @author Luffy Cai
     * @created 2020年5月8日 上午10:34:31
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getXfysList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.EXE_ID,g.* from JBPM6_EXECUTION t,T_BSFW_GCJSXFYS g ");
        sql.append(" where t.bus_recordid = g.yw_id and g.PUSH_STATUS = 0 and t.run_status=2");
        sql.append(" order by t.create_time asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            String YW_ID = map.get("YW_ID") == null ? "" : map.get("YW_ID").toString();
            String exeId = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
            map.put("CREATE_TIME",
                    DateTimeUtil.formatDateStr(CREATE_TIME, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"));
            Map<String, Object> task = getTaskList(exeId);
            map.put("ASSIGNER_DEPNAMES", task.get("ASSIGNER_DEPNAMES"));
            map.put("TEAM_NAMES", task.get("TEAM_NAMES"));
            map.put("HANDLE_OPINION", task.get("HANDLE_OPINION"));
            map.put("END_TIME", task.get("END_TIME"));
            for (Map.Entry<String, Object> a : map.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();
                if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                    map.put(a.getKey(), "<![CDATA[" + value + "]]>");
                }
            }
            map.put("sourceid", YW_ID);
        }
        return list;
    } 
    /**
     * 
     * 描述： 质量监督信息
     * 
     * @author Luffy Cai
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushXfysInfo(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_XFYSBAXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("xfysList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_XFYSBAXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }

    /**
     * 
     * @Description 根据项目代码获取责任主体信息
     * @author Luffy Cai
     * @date 2020年9月16日
     * @param projectCode
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findZrztList(String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM TB_FC_PROJECT_CORP_INFO T ");
        sql.append(" WHERE T.PRJ_CODE = ? ");
        params.add(projectCode);
        sql.append(" AND T.ZRZT_TYPE = '2' ");
        List<Map<String, Object>> resultList = dao.findBySql(sql.toString(), params.toArray(),null);
        if (resultList != null && resultList.size() > 0) {
            return resultList;
        }else {
            return null;
        }
    }
  
    
}
