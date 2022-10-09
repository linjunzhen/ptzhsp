/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
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
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.project.dao.ProjectApplyDao;
import net.evecom.platform.project.model.GcjsPushStatusFactory;
import net.evecom.platform.project.model.GcjsPushStatusTemplate;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * 描述 投资项目申报操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings("rawtypes")
@Service("projectApplyService")
public class ProjectApplyServiceImpl extends BaseServiceImpl implements ProjectApplyService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectApplyServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectApplyDao dao;

    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;

    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 系统附件存放路径
     */
    private static String attachFilePath = "";
    /**
     * 工程建设推送前置库版本号
     */
    private static String projectVersion = "";
    /**
     * 工程建设推送前置库事项版本号
     */
    private static String projectItemVersion = "";
    

    /**
     * 静态块获取
     */
    static {
        Properties properties = FileUtil.readProperties("project.properties");
        attachFilePath = properties.getProperty("AttachFilePath");
        projectVersion = properties.getProperty("projectVersion");
        projectItemVersion = properties.getProperty("projectItemVersion");
    }

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from SPGL_XMJBXXB T ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
            sql.append(" and T.flow_cate_id IS NOT NULL");
        } else {
            sql.append(" WHERE T.flow_cate_id IS NOT NULL");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> loadTzxmxxData(String projectCode) {
        // TODO Auto-generated method stub
        Map<String, Object> returnMap;
        try {
            // 二次装修项目
            if (StringUtils.isNotEmpty(projectCode) && projectCode.substring(15, 17).contains("88")) {
                returnMap = new HashMap<String, Object>();

                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { AllConstant.INTER_CODE_SWB_FGDZCTZXMCX });
                String url = (String) dataAbutment.get("CONFIG_XML");
                String resultStr = HttpRequestUtil.sendGet(url,
                        "args0=350128&args1=XStesPpHdX&args2=&args3=&args4=&args5=10&args6=" + projectCode);
                Document document = XmlUtil.stringToDocument("<?xml version='1.0' encoding='UTF-8'?>" + resultStr);
                String name = document.getRootElement().selectSingleNode("return").getText();
                Map<String, Object> resultMap = JsonUtil.parseJSON2Map(name);
                List<Map<String, Object>> datas = (List<Map<String, Object>>) resultMap.get("datas");
                if (null != datas && datas.size() > 0) {
                    Map<String, Object> data = datas.get(0);
                    Map<String, Object> projectInfo = (Map<String, Object>) data.get("projectInfo");
                    projectInfo.put("PLACE_CODE", projectInfo.get("PLACE_CODE_DETAIL"));
                    projectInfo.put("PLACE_CODE_DETAIL", projectInfo.get("PLACE_CODE_DETAIL_EXTENSION"));
                    projectInfo.put("APPLY_DATE", DateTimeUtil.getStrOfDate(
                            new Date(Long.valueOf(projectInfo.get("APPLY_DATE").toString())), "yyyy-MM-dd HH:mm:ss"));

                    Map<String, Object> lerepInfo = (Map<String, Object>) data.get("lerepInfo");
                    Map<String, Object> returnDatas = new HashMap<String, Object>();
                    returnDatas.putAll(MapUtil.transformUpperCase(projectInfo));
                    List<Map<String, Object>> lerep_info = new ArrayList<Map<String, Object>>();
                    lerep_info.add(MapUtil.transformUpperCase(lerepInfo));
                    returnDatas.put("lerep_info", lerep_info);
                    returnMap.put("datas", returnDatas);
                    returnMap.put("result", resultMap.get("result"));
                }
            } else {
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { AllConstant.INTER_CODE_SWB_TZXMCX });
                String url = (String) dataAbutment.get("CONFIG_XML");
                String resultStr = HttpRequestUtil.sendGet(url, "args0=" + projectCode);
                Document document = XmlUtil.stringToDocument("<?xml version='1.0' encoding='UTF-8'?>" + resultStr);
                String name = document.getRootElement().selectSingleNode("//ns:getProjectInfoResponse/return")
                        .getText();
                returnMap = JsonUtil.parseJSON2Map(name);
            }
            return returnMap;
        } catch (Exception e) {
            // TODO: handle exception
            returnMap = new HashMap<String, Object>();
            returnMap.put("result", false);
            returnMap.put("errcode", "0003");
            returnMap.put("errmsg", "输入参数有误");
            log.error("", e);
            return returnMap;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT S.STAGE_ID AS CATEGORY_ID,S.NAME,");
        sql.append(" T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.BACKOR_NAME,");
        sql.append(" T.C_SN,D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE,L.IS_KEY_ITEM from T_WSBS_SERVICEITEM T ");
        sql.append(" JOIN T_FLOW_CONFIG_ITEM L ON T.ITEM_ID = L.ITEM_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_STAGE S   ON S.STAGE_ID = L.STAGE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = S.TYPE_ID  ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D2 ON D2.DEPART_ID = T.ZBCS_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT in (-1,1)");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D2.DEPART_CODE in ").append(StringUtil.getValueArray(authDepCodes));
        }
        String PROJECT_CODE = sqlFilter.getRequest().getParameter("PROJECT_CODE");
        // sql.append(" order by ecount desc ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        // PagingBean pb = new PagingBean(0, 200);// 新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);

        // 2021年11月24日 15:25:09 更改获取方式
        String projectSql = "select * from (select t.exe_id,t.RUN_STATUS,r.check_status"
                + ",0 as isFiled,t.create_time,t.ITEM_CODE from JBPM6_EXECUTION t "
                + " left join T_WSBS_PROJECT_RECALL r on t.exe_id = r.exe_id " + "where  t.PROJECT_CODE = ? "
                + " union all " + "select t.exe_id,t.RUN_STATUS,r.check_status,1 as isFiled,t.create_time,t.ITEM_CODE"
                + " from JBPM6_EXECUTION_EVEHIS t left join T_WSBS_PROJECT_RECALL r on t.exe_id = r.exe_id "
                + "where t.PROJECT_CODE = ? ) a  order by a.create_time desc";

        List<Map<String, Object>> exeList = dao.findBySql(projectSql, new Object[] { PROJECT_CODE, PROJECT_CODE },
                null);

        for (Map<String, Object> map : list) {
            map.put("PROJECT_CODE", PROJECT_CODE);
            String ITEM_CODE = map.get("ITEM_CODE").toString();
            boolean flag = false;
            String EXE_ID = "";
            String RUN_STATUS = "";
            String ISFILED = "";
            String CHECK_STATUS = "";
            for (Map<String, Object> exeMap : exeList) {
                String EXE_ITEM_CODE = exeMap.get("ITEM_CODE").toString();
                if (EXE_ITEM_CODE.equals(ITEM_CODE)) {
                    flag = true;
                    EXE_ID = StringUtil.getString(exeMap.get("EXE_ID"));
                    RUN_STATUS = StringUtil.getString(exeMap.get("RUN_STATUS"));
                    ISFILED = StringUtil.getString(exeMap.get("ISFILED"));
                    CHECK_STATUS = StringUtil.getString(exeMap.get("CHECK_STATUS"));
                    break;
                }
            }
            if (flag) {
                map.put("CHECK_STATUS", CHECK_STATUS);
                map.put("EXE_ID", EXE_ID);
                map.put("STATE", RUN_STATUS);
                map.put("ISFILED", ISFILED);
            } else {
                map.put("EXE_ID", "");
                map.put("STATE", "-1");
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findProjectList(int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.*,t1.splclx,T1.TYPE_ID from SPGL_XMJBXXB t ");
        sql.append("  join T_FLOW_CONFIG_CATEGORY c  on t.flow_cate_id = c.id ");
        sql.append("  join T_flow_config_type t1  on C.flow_type_id = t1.type_id ");
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and t.id = ? ");
            param.add(id);
        } else {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        sql.append(" order by t.create_time asc");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        return getFormatXmJbxx(list);
    }

    /**
     * 获取已办结需要推送的赋码变更信息
     * 
     * @param status
     * @param id
     * @return
     */
    private List<Map<String, Object>> findChangeProjectList(int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.* from T_PROJECT_CODEINFOCHANGE t ");
        sql.append(" left join  jbpm6_execution e  on t.yw_id=e.bus_recordid ");
        sql.append(" where e.run_status='2' ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and t.YW_ID = ? ");
            param.add(id);
        } else {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        sql.append(" order by e.create_time asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        list=getFormatXmJbxx1(list);
        return getFormatChangeXmJbxx(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findXmdwxxbList(Map<String, Object> parentMap, int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select x.id as DFSJZJ,350128 AS XZQHDM,T.PROJECT_CODE AS XMDM,T.PROJECT_CODE AS GCDM ");
        sql.append("  ,X.LEREP_CERTNO AS DWTYSHXYDM,X.ENTERPRISE_NAME AS DWMC,X.DWLX ");
        sql.append("  from SPGL_XMJBXXB t join SPGL_XMDWXXB X ON T.ID = X.JBXX_ID ");
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and x.id = ? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and x.push_status = ? ");
                param.add(status);
            }
        }
        if (null != parentMap) {
            String dfsjzj = parentMap.get("DFSJZJ").toString();
            if (StringUtils.isNotEmpty(dfsjzj)) {
                sql.append(" and x.jbxx_id = ? ");
                param.add(dfsjzj);
            }
        }
        sql.append(" order by x.create_time asc");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("05", "S_SPGL_XMDWXXB"));// 流水号;
            map.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            map.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            map.put("SJSCZT", 0);// 数据上传状态
        }
        return list;
    }

    /**
     * 获取变更后的法人单位信息
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> findChangeXmdwxxbList(Map<String, Object> map) {
        List<Map<String, Object>> newLereps = Lists.newArrayList();
        String lerepInfoJson = StringUtil.getValue(map, "LEREP_INFO");
        List<Map<String, Object>> list = (List) JSONObject.parse(lerepInfoJson);
        for (Map<String, Object> lerep : list) {
            Map<String, Object> newLerep = new HashMap();
            newLerep.putAll(lerep);
            swapMapKeyAndValue(newLerep, lerep, "correspondence_address", "correspondence_address_CD");
            swapMapKeyAndValue(newLerep, lerep, "contact_name", "contact_name_CD");
            swapMapKeyAndValue(newLerep, lerep, "enterprise_place", "enterprise_place_CD");
            swapMapKeyAndValue(newLerep, lerep, "contact_phone", "contact_phone_CD");
            swapMapKeyAndValue(newLerep, lerep, "enterprise_nature", "enterprise_nature_CD");
            swapMapKeyAndValue(newLerep, lerep, "lerep_certno", "lerep_certno_CD");
            swapMapKeyAndValue(newLerep, lerep, "contact_tel", "contact_tel_CD");
            swapMapKeyAndValue(newLerep, lerep, "contact_fax", "contact_fax_CD");
            swapMapKeyAndValue(newLerep, lerep, "contact_email", "contact_email_CD");
            swapMapKeyAndValue(newLerep, lerep, "china_foreign_share_ratio", "china_foreign_share_ratio_CD");
            swapMapKeyAndValue(newLerep, lerep, "business_scope", "business_scope_CD");
            swapMapKeyAndValue(newLerep, lerep, "contact_type", "contact_type_CD");
            swapMapKeyAndValue(newLerep, lerep, "lerep_certtype", "lerep_certtype_CD");
            swapMapKeyAndValue(newLerep, lerep, "enterprise_name", "enterprise_name_CD");
                // 主键部分
                newLerep.put("XZQHDM", "350128");// 行政区域代码
                String projectCode = StringUtil.getValue(map, "PROJECTCODE");
                newLerep.put("GCDM", projectCode);// 工程代码
                newLerep.put("XMDM",projectCode);
                newLerep.put("DWMC",lerep.get("enterprise_name_CD")==null?StringUtil.getValue(map,"enterprise_name"):
                        StringUtil.getString(lerep.get("enterprise_name_CD")));
                newLerep.put("DWTYSHXYDM", StringUtil.getValue(lerep, "lerep_certno"));// 单位统一社会信用代码
                StringBuffer sql = new StringBuffer("select X.DWLX");
                sql.append("  from SPGL_XMJBXXB t join SPGL_XMDWXXB X ON T.ID = X.JBXX_ID ");
                sql.append(" where t.PROJECT_CODE=? ");
                Map<String, Object> dwxx = exeDataService.getFirstByJdbc(sql.toString(), new Object[] { projectCode });
                newLerep.put("DWLX", StringUtil.getValue(dwxx, "DWLX")==""?1:StringUtil.getValue(dwxx, "DWLX")); // 单位类型

                newLerep.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                newLerep.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
                newLerep.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
                newLerep.put("SJSCZT", 0);// 数据上传状态
                newLerep.put("BLZT",11);// 办理状态
            newLerep.remove("dwlx");
            newLereps.add(newLerep);
        }
        return newLereps;
    }

    private List<Map<String, Object>> getFormatXmJbxx(List<Map<String, Object>> jbxxList) {
        List<Map<String, Object>> formatJbxxList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : jbxxList) {
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("LSH", getLsh("04", "S_SPGL_XMJBXXB"));// 流水号
            newMap.put("DFSJZJ", Optional.ofNullable(map.get("ID")).orElse(map.get("YW_ID")));// 地方数据主键
            newMap.put("XZQHDM", "350128");// 行政区划代码
            newMap.put("XMDM", map.get("PROJECT_CODE"));// 项目代码
            newMap.put("XMMC", map.get("PROJECT_NAME"));// 项目名称
            newMap.put("GCDM", map.get("PROJECT_CODE"));// 工程代码
            newMap.put("XMTZLY", map.get("XMTZLY"));// 项目投资来源
            newMap.put("TDHQFS", getTdhqfs((String) map.get("GET_LAND_MODE")));// 土地获取方式
            newMap.put("TDSFDSJFA", map.get("TDSFDSJFA"));// 土地是否带设计方案
            newMap.put("SFWCQYPG", map.get("SFWCQYPG"));// 是否完成区域评估
            newMap.put("SPLCLX", map.get("SPLCLX"));// 审批流程类型
            newMap.put("LXLX", getLxlx((String) map.get("PROJECT_TYPE")));// 立项类型
            newMap.put("GCFL", map.get("GCFL"));// 工程分类
            newMap.put("JSXZ",
                    Integer.parseInt(map.get("PROJECT_NATURE") == null ? "0" : map.get("PROJECT_NATURE").toString())
                            + 1);// 建设性质
            newMap.put("XMZJSX", getXmzjsx((String) map.get("PROJECT_ATTRIBUTES")));// 项目资金属性
            newMap.put("GBHYDMFBND", "2017");// 国标行业代码发布年代默认为“2017”
            newMap.put("GBHY", getIndustry(map.get("INDUSTRY") == null ? "" : map.get("INDUSTRY").toString()));// 国标行业
            newMap.put("NKGSJ", map.get("START_YEAR"));// 拟开工时间
            newMap.put("NJCSJ", map.get("END_YEAR"));// 拟建成时间
            newMap.put("XMSFWQBJ", map.get("XMSFWQBJ"));// 项目是否完全办结
            newMap.put("XMWQBJSJ", map.get("XMWQBJSJ"));// 项目完全办结时间
            newMap.put("ZTZE", map.get("TOTAL_MONEY"));// 总投资额（万元）
            newMap.put("JSDDXZQH", map.get("PLACE_CODE") == null ? "350128" : map.get("PLACE_CODE"));// 建设地点行政区划
            String PLACE_CODE_DETAIL = map.get("PLACE_CODE_DETAIL") == null ? ""
                    : map.get("PLACE_CODE_DETAIL").toString().trim();
            StringBuffer jsdd = new StringBuffer();
            if (StringUtils.isNotEmpty(PLACE_CODE_DETAIL)) {
                String[] jsdds = PLACE_CODE_DETAIL.split(",");
                for (String str : jsdds) {
                    if (StringUtils.isNotEmpty(str) && str.equals("350128")) {
                        jsdd.append("平潭综合实验区").append(",");
                    } else if (str.contains("350")) {
                        Map<String, Object> dic = dictionaryService.get("XZQY", str);
                        if (null != dic && dic.size() > 0) {
                            jsdd.append(dic.get("DIC_NAME")).append(",");
                        } else {
                            jsdd.append("平潭综合实验区").append(",");
                        }
                    } else {
                        jsdd.append(str).append(",");
                    }
                }
            }
            if (StringUtils.isNotEmpty(jsdd) && jsdd.length() > 0) {
                newMap.put("JSDD", jsdd.deleteCharAt(jsdd.length() - 1));// 填写项目建设地点的文字描述
            } else {
                newMap.put("JSDD", PLACE_CODE_DETAIL);// 填写项目建设地点的文字描述
            }
            newMap.put("JSGMJNR", map.get("SCALE_CONTENT"));// 建设规模及内容
            newMap.put("YDMJ", map.get("LAND_AREA") == null ? 0 : map.get("LAND_AREA"));// 用地面积
            newMap.put("JZMJ", map.get("BUILT_AREA") == null ? 0 : map.get("BUILT_AREA"));// 建筑面积
            newMap.put("SBSJ", map.get("APPLY_DATE"));// 申报时间
            newMap.put("SPLCBM", map.get("TYPE_ID"));// 审批流程编码（主键ID）
            newMap.put("SPLCBBH", projectVersion);// 审批流程版本号
            newMap.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            newMap.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            newMap.put("SJSCZT", 0);// 数据上传状态

            // 项目单位信息表
            List<Map<String, Object>> newDwxxList = findXmdwxxbList(newMap, -1, null);
            newMap.put("LEREP_INFO", newDwxxList);

            formatJbxxList.add(newMap);
        }
        return formatJbxxList;
    }

    /**
     * getFormatXmJbxx1(与getFormatXmJbxx相比，少了一个LEREP_INFO的转换赋值）
     * @param jbxxList
     * @return
     */
    private List<Map<String, Object>> getFormatXmJbxx1(List<Map<String, Object>> jbxxList) {
        List<Map<String, Object>> formatJbxxList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : jbxxList) {
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.putAll(map);
            newMap.put("LSH", getLsh("04", "S_SPGL_XMJBXXB"));// 流水号
            newMap.put("DFSJZJ", Optional.ofNullable(map.get("ID")).orElse(map.get("YW_ID")));// 地方数据主键
            newMap.put("XZQHDM", "350128");// 行政区划代码
            newMap.put("XMDM", map.get("PROJECTCODE"));// 项目代码
            newMap.put("XMMC", map.get("PROJECT_NAME"));// 项目名称
            newMap.put("GCDM", map.get("PROJECTCODE"));// 工程代码
            newMap.put("XMTZLY", map.get("XMTZLY"));// 项目投资来源
            newMap.put("TDHQFS", getTdhqfs((String) map.get("GET_LAND_MODE")));// 土地获取方式
            newMap.put("TDSFDSJFA", map.get("TDSFDSJFA"));// 土地是否带设计方案
            newMap.put("SFWCQYPG", map.get("SFWCQYPG"));// 是否完成区域评估
            newMap.put("SPLCLX", map.get("SPLCLX"));// 审批流程类型
            newMap.put("LXLX", getLxlx((String) map.get("PROJECT_TYPE")));// 立项类型
            newMap.put("GCFL", map.get("GCFL"));// 工程分类
            newMap.put("JSXZ",
                    Integer.parseInt(map.get("PROJECT_NATURE") == null ? "0" : map.get("PROJECT_NATURE").toString())
                            + 1);// 建设性质
            newMap.put("XMZJSX", getXmzjsx((String) map.get("PROJECT_ATTRIBUTES")));// 项目资金属性
            newMap.put("GBHYDMFBND", "2017");// 国标行业代码发布年代默认为“2017”
            newMap.put("GBHY", getIndustry(map.get("INDUSTRY") == null ? "" : map.get("INDUSTRY").toString()));// 国标行业
            newMap.put("NKGSJ", map.get("START_YEAR"));// 拟开工时间
            newMap.put("NJCSJ", map.get("END_YEAR"));// 拟建成时间
            newMap.put("XMSFWQBJ", map.get("XMSFWQBJ"));// 项目是否完全办结
            newMap.put("XMWQBJSJ", map.get("XMWQBJSJ"));// 项目完全办结时间
            newMap.put("ZTZE", map.get("TOTAL_MONEY"));// 总投资额（万元）
            newMap.put("JSDDXZQH", map.get("PLACE_CODE") == null ? "350128" : map.get("PLACE_CODE"));// 建设地点行政区划
            String PLACE_CODE_DETAIL = map.get("PLACE_CODE_DETAIL") == null ? ""
                    : map.get("PLACE_CODE_DETAIL").toString().trim();
            StringBuffer jsdd = new StringBuffer();
            if (StringUtils.isNotEmpty(PLACE_CODE_DETAIL)) {
                String[] jsdds = PLACE_CODE_DETAIL.split(",");
                for (String str : jsdds) {
                    if (StringUtils.isNotEmpty(str) && str.equals("350128")) {
                        jsdd.append("平潭综合实验区").append(",");
                    } else if (str.contains("350")) {
                        Map<String, Object> dic = dictionaryService.get("XZQY", str);
                        if (null != dic && dic.size() > 0) {
                            jsdd.append(dic.get("DIC_NAME")).append(",");
                        } else {
                            jsdd.append("平潭综合实验区").append(",");
                        }
                    } else {
                        jsdd.append(str).append(",");
                    }
                }
            }
            if (StringUtils.isNotEmpty(jsdd) && jsdd.length() > 0) {
                newMap.put("JSDD", jsdd.deleteCharAt(jsdd.length() - 1));// 填写项目建设地点的文字描述
            } else {
                newMap.put("JSDD", PLACE_CODE_DETAIL);// 填写项目建设地点的文字描述
            }
            newMap.put("JSGMJNR", map.get("SCALE_CONTENT"));// 建设规模及内容
            newMap.put("YDMJ", map.get("LAND_AREA") == null ? 0 : map.get("LAND_AREA"));// 用地面积
            newMap.put("JZMJ", map.get("BUILT_AREA") == null ? 0 : map.get("BUILT_AREA"));// 建筑面积
            newMap.put("SBSJ", map.get("APPLY_DATE"));// 申报时间
            newMap.put("SPLCBM", map.get("TYPE_ID"));// 审批流程编码（主键ID）
            newMap.put("SPLCBBH", projectVersion);// 审批流程版本号
            newMap.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            newMap.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            newMap.put("SJSCZT", 0);// 数据上传状态
            newMap.put("LEREP_INFO",map.get("LEREP_INFO"));
            formatJbxxList.add(newMap);
        }
        return formatJbxxList;
    }

    /**
     * 格式化变更后的信息
     * 
     * @param jbxxList
     * @return
     */
    private List<Map<String, Object>> getFormatChangeXmJbxx(List<Map<String, Object>> jbxxList) {
        List<Map<String, Object>> formatJbxxList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : jbxxList) {
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.putAll(map);
            // 主键部分
            newMap.put("XZQHDM", "350128");// 行政区划代码

            // 变更的属性部分
            setChangeeXmJbxx(newMap, map, "XMMC", "PROJECT_NAME_CD");// 项目名称
            setChangeeXmJbxx(newMap, map, "XMTZLY", "XMTZLY_CD");// 项目投资来源
            setChangeeXmJbxx(newMap, map, "TDHQFS", "GET_LAND_MODE_CD");// 土地获取方式
            setChangeeXmJbxx(newMap, map, "TDSFDSJFA", "TDSFDSJFA_CD");// 土地是否带设计方案
            setChangeeXmJbxx(newMap, map, "SFWCQYPG", "SFWCQYPG_CD");// 是否完成区域评估
            setChangeeXmJbxx(newMap, map, "SPLCLX", "SPLCLX_CD");// 审批流程类型
            setChangeeXmJbxx(newMap, map, "LXLX", "PROJECT_TYPE_CD");// 立项类型
            setChangeeXmJbxx(newMap, map, "GCFL", "GCFL_CD");// 工程分类
            setChangeeXmJbxx(newMap, map, "JSXZ", "PROJECT_NATURE_CD");// 建设性质
            setChangeeXmJbxx(newMap, map, "XMZJSX", "PROJECT_ATTRIBUTES_CD");// 项目资金属性
            // setChangeeXmJbxx(newMap,map,"GBHYDMFBND", "2017");//
            // 国标行业代码发布年代默认为“2017”
            setChangeeXmJbxx(newMap, map, "GBHY", "INDUSTRY_CD");// 国标行业
            setChangeeXmJbxx(newMap, map, "NKGSJ", "START_YEAR_CD");// 拟开工时间
            setChangeeXmJbxx(newMap, map, "NJCSJ", "END_YEAR_CD");// 拟建成时间
            // setChangeeXmJbxx(newMap,map,"XMSFWQBJ","XMSFWQBJ");// 项目是否完全办结
            // setChangeeXmJbxx(newMap,map,"XMWQBJSJ","XMWQBJSJ"));// 项目完全办结时间
            setChangeeXmJbxx(newMap, map, "ZTZE", "TOTAL_MONEY_CD");// 总投资额（万元）
            setChangeeXmJbxx(newMap, map, "JSDDXZQH", "PLACE_CODE_CD");// 建设地点行政区划
            setChangeeXmJbxx(newMap, map, "JSDD", "PLACE_CODE_DETAIL_CD");// 填写项目建设地点的文字描述
            setChangeeXmJbxx(newMap, map, "JSGMJNR", "SCALE_CONTENT_CD");// 建设规模及内容
            setChangeeXmJbxx(newMap, map, "YDMJ", "LAND_AREA_CD");// 用地面积
            setChangeeXmJbxx(newMap, map, "JZMJ", "BUILT_AREA_CD");// 建筑面积
            setChangeeXmJbxx(newMap, map, "SBSJ", "APPLY_DATE_CD");// 申报时间
            // setChangeeXmJbxx(newMap,map,"SPLCBM","TYPE_ID"));// 审批流程编码（主键ID）
            // setChangeeXmJbxx(newMap,map,"SPLCBBH", "3.0");// 审批流程版本号
            // setChangeeXmJbxx(newMap,map,"SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            // setChangeeXmJbxx(newMap,map,"SJWXYY", "");// 数据无效原因
            // 如果数据变为无效，需要填写无效原因
            newMap.put("SJSCZT", 0);// 数据上传状态
            // 项目单位信息表
            List<Map<String, Object>> newDwxxList = findChangeXmdwxxbList(map);
            newMap.put("LEREP_INFO", newDwxxList);
            newMap.put("YW_ID", StringUtil.getValue(map, "YW_ID"));// 本地主键id
            formatJbxxList.add(newMap);
        }
        return formatJbxxList;
    }

    /**
     * 处理转换变更数据
     * 
     * @param newMap
     * @param oldMap
     * @param newColName
     * @param oldColName
     * @return
     */
    private Map<String, Object> swapMapKeyAndValue(Map<String, Object> newMap, Map<String, Object> oldMap,
            String newColName, String oldColName) {
        String colValue = StringUtil.getValue(oldMap, oldColName);
        if (StringUtils.isNotEmpty(colValue)) {
            newMap.put(newColName, colValue);
        }
        return newMap;
    }

    /***
     * 处理转换变更数据
     * 
     * @param newMap
     * @param xmjbxx
     * @return
     */
    private Map<String, Object> setChangeeXmJbxx(Map<String, Object> newMap, Map<String, Object> xmjbxx,
            String newColName, String colName) {
        String colValue = StringUtil.getValue(xmjbxx, colName);
        if (StringUtils.isNotEmpty(colValue)) {
            newMap.put(newColName, setSpecialColValue(colName, colValue));
        }
        return newMap;
    }

    /**
     * 类型或者字典值与省要求不一样的时候，做转换
     * 
     * @param colName
     *            需要转换的字段名
     * @param colValue
     *            需要转换的字段值
     * @return 返回转换后的值
     */
    private Object setSpecialColValue(String colName, String colValue) {
        if ("GET_LAND_MODE_CD".equals(colName)) {
            return getTdhqfs(colValue);
        } else if ("PROJECT_TYPE_CD".equals(colName)) {
            return getLxlx(colValue);
        } else if ("PROJECT_NATURE_CD".equals(colName)) {
            return Integer.parseInt(colValue) + 1;
        } else if ("PROJECT_ATTRIBUTES_CD".equals(colName)) {
            return getXmzjsx(colValue);
        } else if ("INDUSTRY_CD".equals(colName)) {
            return getIndustry(colValue);
        }
        return colValue;
    }

    /**
     * 
     * 描述 转换国标行业
     * 
     * @author Rider Chen
     * @created 2019年7月5日 下午1:40:37
     * @param id
     * @return
     */
    public String getIndustry(String code) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT D.* FROM T_MSJW_SYSTEM_DICTYPE D ");
        sql.append("  START WITH D.type_code = ? ");
        sql.append("  CONNECT BY PRIOR D.PARENT_ID = D.TYPE_ID");
        sql.append("   ORDER BY D.TREE_LEVEL ASC) A WHERE A.TREE_LEVEL = 5");
        sql.append(" AND A.PATH LIKE '%4028819d51cc6f280151cde6a3f00027%' ");
        List<Object> params = new ArrayList<Object>();
        params.add(code);
        Map<String, Object> map = dao.getByJdbc(sql.toString(), params.toArray());
        if (null != map && map.size() > 0) {
            String typeCode = map.get("TYPE_CODE").toString();
            if (typeCode.equals(code)) {
                return code;
            } else {
                return typeCode + code;
            }
        }
        return code;
    }

    /**
     * 
     * 描述： 转换资金属性
     * 
     * @author Rider Chen
     * @created 2019年6月19日 上午10:24:16
     * @param type
     * @return
     */
    private int getXmzjsx(String type) {
        int num = 0;
        if (StringUtils.isNotEmpty(type)) {
            if (type.equals("A00001")) {
                num = 1;
            } else if (type.equals("A00002")) {
                num = 2;
            } else if (type.equals("A00003")) {
                num = 3;
            }
        }
        return num;
    }

    /**
     * 
     * 描述： 转换立项类型
     * 
     * @author Rider Chen
     * @created 2019年6月19日 上午10:23:53
     * @param type
     * @return
     */
    private int getLxlx(String type) {
        int num = 0;
        if (StringUtils.isNotEmpty(type)) {
            if (type.equals("A00001")) {
                num = 1;
            } else if (type.equals("A00002")) {
                num = 2;
            } else if (type.equals("A00003")) {
                num = 3;
            }
        }
        return num;
    }

    /**
     * 
     * 描述： 转换土地获取方式
     * 
     * @author Rider Chen
     * @created 2019年6月19日 上午10:21:22
     * @param GET_LAND_MODE
     * @return
     */
    private int getTdhqfs(String type) {
        int num = 5;
        if (StringUtils.isNotEmpty(type)) {
            if (type.equals("A00001")) {
                num = 4;
            } else if (type.equals("A00002")) {
                num = 2;
            } else if (type.equals("A00003")) {
                num = 5;
            }
        }
        return num;
    }

    /**
     * 
     * 描述：
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午4:48:53
     * @param code
     *            数据对应表编码
     * @return
     */
    private String getLsh(String code, String seqName) {
        StringBuffer lsh = new StringBuffer("350128");
        lsh.append(DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss")).append(code)
                .append(StringUtil.getFormatNumber(10, getSeqValue(seqName)));
        return lsh.toString();
    }

    @Override
    public String getSeqValue(String seqName) {
        // TODO Auto-generated method stub
        return dao.getSeqValue(seqName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findFlowCategoryList(int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.TYPE_ID AS DFSJZJ,'350128' AS XZQHDM, T.TYPE_ID AS SPLCBM, T.TYPE_NAME AS SPLCMC ");
        sql.append(" ," + projectVersion + " AS SPLCBBH , T.CREATE_TIME AS SPLCSXSJ, T.SPLCLX,T.SPLCSM "
                + ",F.FILE_NAME AS FJMC,F.FILE_TYPE AS FJLX , ");
        sql.append(" F.FILE_ID AS FJID from  T_flow_config_type t ");
        sql.append(" left join T_MSJW_SYSTEM_FILEATTACH F ON T.FLOW_PIC_ID = F.FILE_ID");
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and T.TYPE_ID=? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and t.push_status = ? ");
                param.add(status);
            }
        }
        sql.append(" order by t.create_time asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("01", "S_SPGL_DFXMSPLCXXB"));
            map.put("SJYXBS", 1);
            map.put("SJSCZT", 0);
            map.put("DFXMSPLCJDXXB", findFlowCategoryList(map, -1, ""));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findFlowCategoryList(Map<String, Object> parentMap, int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select S.STAGE_ID AS DFSJZJ,'350128' AS XZQHDM,S.TYPE_ID AS SPLCBM ,"
                + projectVersion + " AS SPLCBBH ");
        sql.append(" ,S.STAGE_ID AS SPJDBM,S.NAME AS SPJDMC,S.SPJDSX,1 as LCBSXLX ,S.tree_sn as SPJDXH");
        sql.append(" ,S.tree_sn as DYBZSPJDXH  from T_FLOW_CONFIG_STAGE S  ");
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and S.STAGE_ID=? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and S.push_status = ? ");
                param.add(status);
            }
        }
        if (null != parentMap) {
            String dfsjzj = parentMap.get("DFSJZJ").toString();
            if (StringUtils.isNotEmpty(dfsjzj)) {
                sql.append(" and S.TYPE_ID = ? ");
                param.add(dfsjzj);
            }
        }
        sql.append(" ORDER BY S.TYPE_ID ASC,S.TREE_SN ASC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("02", "S_SPGL_DFXMSPLCJDXXB"));
            map.put("SJYXBS", 1);
            map.put("SJSCZT", 0);
            map.put("DFXMSPLCJDSXXXB", findFlowItemList(map, -1, ""));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findFlowItemList(Map<String, Object> parentMap, int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select t.id as DFSJZJ,350128 as XZQHDM,st.type_id as SPLCBM," + projectVersion + " as SPLCBBH, ");
        sql.append(
                " st.tree_sn as SPJDXH ,S.ITEM_CODE AS SPSXBM,S.SWB_ITEM_CODE AS FJSWBSPSXBM,"
                        + projectItemVersion + " as SPSXBBH,s.item_name as SPSXMC" + ",t.DYBZSPSXBM,t.SFSXGZCNZ ");
        sql.append(" ,s.sxlx as BJLX,s.MXYHDX as SQDX,s.finish_gettype as BLJGSDFS,s.cnqxgzr as SPSXBLSX");
        sql.append(",s.SSBMBM as SPBMBM,");
        sql.append(" (case D.DEPART_NAME when '平潭综合实验区' then  D.DEPART_NAME　else "
                + " '平潭综合实验区'|| D.DEPART_NAME end) as SPBMMC ");
        sql.append(",T.IS_KEY_ITEM as SFLCBSX ");
        sql.append(" from T_FLOW_CONFIG_ITEM t join T_FLOW_CONFIG_STAGE st on t.stage_id = st.stage_id");
        sql.append(" join t_wsbs_serviceitem s on t.item_id = s.item_id ");
        sql.append(" join T_MSJW_SYSTEM_DEPARTMENT d on s.ssbmbm = d.depart_code");
        sql.append(" where 1 = 1 ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and t.id=? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and t.push_status = ? ");
                param.add(status);
            }
        }
        if (null != parentMap) {
            String dfsjzj = parentMap.get("DFSJZJ").toString();
            if (StringUtils.isNotEmpty(dfsjzj)) {
                sql.append(" and t.stage_id = ? ");
                param.add(dfsjzj);
            }
        }
        sql.append(" order by t.stage_id asc ,t.create_time asc ");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("03", "S_SPGL_DFXMSPLCJDSXXXB"));
            map.put("SJYXBS", 1);
            map.put("SJSCZT", 0);
            String SPSXMC = map.get("SPSXMC").toString();
            if(SPSXMC.indexOf("XX")>-1){
                //截取XX之前的字符串
                SPSXMC = SPSXMC.substring(0,SPSXMC.indexOf("XX"));
            }
            map.put("SPSXMC", SPSXMC);
            // map.put("SPSXBBH", getSpsxbbh((String) map.get("SPSXBBH")));
            map.put("SQDX", getSqdx(map.get("SQDX") == null ? "" : map.get("SQDX").toString()));
            map.put("BLJGSDFS", getBljgsdfs(map.get("BLJGSDFS") == null ? "04" : map.get("BLJGSDFS").toString()));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveDfxmsplcxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findFlowCategoryList(0, id);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveDfxmsplcxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveDfxmsplcxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_FLOW_CONFIG_TYPE", 1, "TYPE_ID", map.get("DFSJZJ").toString());
                    // 地方项目审批流程阶段信息表
                    String splcjdxxbJson = map.get("DFXMSPLCJDXXB") == null ? "" : map.get("DFXMSPLCJDXXB").toString();
                    if (StringUtils.isNotEmpty(splcjdxxbJson)) {
                        List<Map<String, Object>> splcjdxxb = JSON.parseObject(splcjdxxbJson, List.class);
                        for (Map<String, Object> map2 : splcjdxxb) {
                            updatePushStatus("T_FLOW_CONFIG_STAGE", 1, "STAGE_ID", map2.get("DFSJZJ").toString());
                            // 地方项目审批流程阶段事项信息表
                            String splcjdsxxxbJson = map2.get("DFXMSPLCJDSXXXB") == null ? ""
                                    : map2.get("DFXMSPLCJDSXXXB").toString();
                            if (StringUtils.isNotEmpty(splcjdsxxxbJson)) {
                                List<Map<String, Object>> splcjdsxxxb = JSON.parseObject(splcjdsxxxbJson, List.class);
                                for (Map<String, Object> map3 : splcjdsxxxb) {
                                    updatePushStatus("T_FLOW_CONFIG_ITEM", 1, "ID", map3.get("DFSJZJ").toString());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveDfxmsplcjdxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findFlowCategoryList(null, 0, id);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveDfxmsplcjdxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveDfxmsplcjdxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_FLOW_CONFIG_STAGE", 1, "STAGE_ID", map.get("DFSJZJ").toString());
                    // 地方项目审批流程阶段事项信息表
                    String splcjdsxxxbJson = map.get("DFXMSPLCJDSXXXB") == null ? ""
                            : map.get("DFXMSPLCJDSXXXB").toString();
                    if (StringUtils.isNotEmpty(splcjdsxxxbJson)) {
                        List<Map<String, Object>> splcjdsxxxb = JSON.parseObject(splcjdsxxxbJson, List.class);
                        for (Map<String, Object> map2 : splcjdsxxxb) {
                            updatePushStatus("T_FLOW_CONFIG_ITEM", 1, "ID", map2.get("DFSJZJ").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveDfxmsplcjdsxxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findFlowItemList(null, 0, id);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveDfxmsplcjdsxxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveDfxmsplcjdsxxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_FLOW_CONFIG_ITEM", 1, "ID", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @Override
    public void updatePushStatus(String tableName, int status, String key, String id) {
        // TODO Auto-generated method stub
        String sql = "update " + tableName + " set push_status = ? where " + key + "= ? ";
        dao.executeSql(sql, new Object[] { status, id });
    }

    /**
     * 
     * 描述：转换版本号
     * 
     * @author Rider Chen
     * @created 2019年6月18日 下午2:59:26
     * @param bbh
     * @return
     */
    private String getSpsxbbh(String bbh) {
        String s = projectItemVersion;
        if (StringUtils.isNotEmpty(bbh)) {
            s = bbh.replaceAll("V", "");
        }
        return s;
    }

    /**
     * 
     * 描述：办理结果送达方式
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午11:55:26
     * @param sqdx
     * @return
     */
    private String getBljgsdfs(String bljgsdfs) {
        StringBuffer str = new StringBuffer("");
        if (StringUtils.isNotEmpty(bljgsdfs)) {
            if (bljgsdfs.indexOf("01") > -1) {// 申请对象窗口领取
                str.append(",2");
            }
            if (bljgsdfs.indexOf("02") > -1) {// 邮递办理结果
                str.append(",3");
            }
            if (bljgsdfs.indexOf("03") > -1 || bljgsdfs.indexOf("04") > -1 || bljgsdfs.indexOf("05") > -1) {// 其他
                str.append(",4");
            }
        }
        if (str.length() > 0) {
            return str.substring(1);
        }
        return str.toString();
    }

    /**
     * 
     * 描述：转换申请对象类型
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午11:55:26
     * @param sqdx
     * @return
     */
    private int getSqdx(String sqdx) {
        int num = 1;
        if (StringUtils.isNotEmpty(sqdx)) {
            if (sqdx.lastIndexOf("01,02") == 0) {
                num = 3;
            } else if (sqdx.lastIndexOf("01") == 0) {
                num = 1;
            } else if (sqdx.lastIndexOf("02") == 0) {
                num = 2;
            } else if (sqdx.lastIndexOf("03") == 0) {
                num = 4;
            } else if (sqdx.lastIndexOf("04") == 0) {
                num = 5;
            } else if (sqdx.lastIndexOf("05") == 0) {
                num = 6;
            } else {
                num = 3;
            }
        }
        return num;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmjbxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findProjectList(0, id);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmjbxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmjbxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("SPGL_XMJBXXB", 1, "ID", map.get("DFSJZJ").toString());
                    // 地方项目审批流程阶段事项信息表
                    String lerepInfoJson = map.get("LEREP_INFO") == null ? "" : map.get("LEREP_INFO").toString();
                    if (StringUtils.isNotEmpty(lerepInfoJson)) {
                        List<Map<String, Object>> lerepInfoList = JSON.parseObject(lerepInfoJson, List.class);
                        for (Map<String, Object> map2 : lerepInfoList) {
                            updatePushStatus("SPGL_XMDWXXB", 1, "ID", map2.get("DFSJZJ").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    /**
     *
     * 描述 推送赋码信息变更
     *
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param id
     * @return
     */
    public Map<String, Object> pushChangeXmjbxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findChangeProjectList(0, id);
            log.info("赋码信息变更同步开始");
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmjbxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            log.info("infoJson====="+JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("pushChangeXmjbxxb返回数据：" + result);
            log.info("赋码信息变更同步结束");
            if(result!=null){
                String resultCode=StringUtil.getString(result.get("invokeResultCode"));
                if("000".equals(resultCode)){
                    for (Map<String, Object> map : list) {
                        updatePushStatus("T_PROJECT_CODEINFOCHANGE", 1, "YW_ID", map.get("DFSJZJ").toString());
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmdwxxb(String id) {
        // TODO Auto-generated method stub

        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findXmdwxxbList(null, 0, null);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmdwxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmdwxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("SPGL_XMDWXXB", 1, "ID", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmspsxblxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findEexcutionList(0, id);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmspsxblxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmspsxblxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("JBPM6_EXECUTION", 1, "EXE_ID", map.get("DFSJZJ").toString());
                    // 地方项目审批流程阶段事项信息表
                    String taskListJson = map.get("TASK_LIST") == null ? "" : map.get("TASK_LIST").toString();
                    if (StringUtils.isNotEmpty(taskListJson)) {
                        List<Map<String, Object>> taskList = JSON.parseObject(taskListJson, List.class);
                        for (Map<String, Object> map2 : taskList) {
                            // updatePushStatus("JBPM6_TASK", 1, "TASK_ID",
                            // map2.get("DFSJZJ").toString());
                            updatePushStatus("SPGL_XMSPSXBLXXXXB", 1, "DFSJZJ", map2.get("DFSJZJ").toString());
                        }
                    }
                    // 地方项目附件信息表
                    String fileListJson = map.get("FILE_LIST") == null ? "" : map.get("FILE_LIST").toString();
                    if (StringUtils.isNotEmpty(fileListJson)) {
                        List<Map<String, Object>> fileList = JSON.parseObject(fileListJson, List.class);
                        for (Map<String, Object> map2 : fileList) {
                            updatePushStatus("T_MSJW_SYSTEM_FILEATTACH", 1, "FILE_ID", map2.get("DFSJZJ").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmspsxblxxxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findXspsxblxxxxb(null, 0);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmspsxblxxxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmspsxblxxxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("SPGL_XMSPSXBLXXXXB", 1, "DFSJZJ", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmqtfjxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findEexcutionFileList(null, 0, null);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmqtfjxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmqtfjxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_MSJW_SYSTEM_FILEATTACH", 1, "FILE_ID", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findEexcutionList(int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("  select e.exe_id as DFSJZJ,350128 as XZQHDM,e.PROJECT_CODE as GCDM ");
        sql.append(" ,S.ITEM_CODE AS SPSXBM,S.SWB_ITEM_CODE AS FJSWBSPSXBM,"
                + projectItemVersion + " as SPSXBBH,c.FLOW_TYPE_ID as SPLCBM," + projectVersion + " as SPLCBBH");
        sql.append(" ,e.exe_id as SPSXSLBM,s.ssbmbm as SPBMBM,");
        sql.append("  (case D.DEPART_NAME when '平潭综合实验区' then  D.DEPART_NAME　else"
                + " '平潭综合实验区'|| D.DEPART_NAME end) as SPBMMC ");
        sql.append(" ,1 as SLFS,1 as GKFS,s.cnqxgzr as SXBLSX from JBPM6_EXECUTION e  ");
        sql.append(" join t_wsbs_serviceitem s on e.item_code = s.item_code ");
        sql.append(" join T_MSJW_SYSTEM_DEPARTMENT d on s.ssbmbm = d.depart_code");
        sql.append(" join SPGL_XMJBXXB x on e.PROJECT_CODE = x.project_code ");
        sql.append(" join t_flow_config_category c on x.flow_cate_id = c.id ");
        sql.append(" where 1=1  and e.run_status<>0 ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and e.exe_id = ? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and e.push_status = ? ");
                param.add(status);
            }
        }
        sql.append(" order by e.create_time asc");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("08", "S_SPGL_XMSPSXBLXXB"));// 流水号;
            map.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            map.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            map.put("SJSCZT", 0);// 数据上传状态
            // map.put("SPSXBBH", getSpsxbbh((String) map.get("SPSXBBH")));//
            // 转换版本号
            // 并联审批实例编码
            String blspslbm = getBlspslbm(map.get("SPSXSLBM").toString(), map.get("GCDM").toString(),
                    map.get("SPLCBM").toString(), map.get("SPSXBM").toString());
            if (StringUtils.isNotEmpty(blspslbm)) {
                blspslbm = StringUtil.encryptMd5(blspslbm);
            }
            map.put("BLSPSLBM", blspslbm);// 更改为申报号的MD5编码

            map.put("TASK_LIST", findXspsxblxxxxb(map, -1));// 流程任务
            map.put("FILE_LIST", findEexcutionFileList(map, -1, null));// 附件信息
        }
        return list;
    }

    /**
     * 
     * 描述 根据工程代码、流程类型ID和事项编码获取并联审批实例编码
     * 
     * @author Rider Chen
     * @created 2021年1月20日 下午2:10:49
     * @param gcdm
     * @param typeId
     * @param ITEM_CODE
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getBlspslbm(String spsxslbm, String gcdm, String typeId, String ITEM_CODE) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.*  from T_FLOW_CONFIG_ITEM t ");
        sql.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
        sql.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
        sql.append(" WHERE  ft.type_id = ? and (item.item_code = ?  or item.SWB_ITEM_CODE = ? )");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        param.add(typeId);
        param.add(ITEM_CODE);
        param.add(ITEM_CODE);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        if (null != list && list.size() > 0) {
            Map<String, Object> configItem = list.get(0);
            String DYBZSPSXBM = configItem.get("DYBZSPSXBM") == null ? "" : configItem.get("DYBZSPSXBM").toString();
            // 联合审图
            if (StringUtils.isNotEmpty(DYBZSPSXBM)
                    && (DYBZSPSXBM.contains("0100") || DYBZSPSXBM.contains("0480") || DYBZSPSXBM.contains("0080"))) {
                StringBuffer lhstItemSql = getLhstItemSql();
                StringBuffer sql1 = getLhstExeSql();
                List<Object> param1 = new ArrayList<Object>();
                param1.add(gcdm);
                //获取所有办件的事项编码信息
                List<Map<String, Object>> lhstItemList = dao.findBySql(lhstItemSql.toString(), param1.toArray(), null);
                if(null != lhstItemList && lhstItemList.size() >= 2){//所有办件的事项编码种类大于等于2时
                    List<Map<String, Object>> list1 = dao.findBySql(sql1.toString(), param1.toArray(), null);
                    // “关联审批事项实例编码”填上第一个事项办件的申报号
                    if (null != list1 && list1.size() >= 2) {
                        Map<String, Object> exeMap = list1.get(0);
                        String exeId = exeMap.get("EXE_ID").toString();
                        int push_status = Integer.parseInt(exeMap.get("PUSH_STATUS").toString());
                        if (push_status == 1) {// 第一条数据状态为已推送的时候更改推送状态
                            StringBuffer sql2 = new StringBuffer(
                                    " update JBPM6_EXECUTION set push_status = 0 where exe_id in ");
                            sql2.append(" (select e.exe_id from JBPM6_EXECUTION e  where e.item_code in ( ");
                            sql2.append(" select item.item_code from T_FLOW_CONFIG_ITEM t ");
                            sql2.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
                            sql2.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
                            sql2.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
                            sql2.append(
                                    " where t.dybzspsxbm like '%0100%' or t.dybzspsxbm like '%0480%' or t.dybzspsxbm like '%0080%'");
                            sql2.append(
                                    " group by item.item_code) and e.run_status<>0 and e.project_code =? and e.push_status =1 )");
                            dao.executeSql(sql2.toString(), param1.toArray());
                        }
                        return exeId;
                    }
                }
            } else if (StringUtils.isNotEmpty(DYBZSPSXBM) && (DYBZSPSXBM.contains("0120") || DYBZSPSXBM.contains("0130")
                    || DYBZSPSXBM.contains("0140") || DYBZSPSXBM.contains("0150") || DYBZSPSXBM.contains("0160")
                    || DYBZSPSXBM.contains("0170"))) {// 联合验收
                StringBuffer lhysItemSql = getLhysItemSql();
                StringBuffer sql1 = getLhysExeSql();
                List<Object> param1 = new ArrayList<Object>();
                param1.add(gcdm);
                //获取所有办件的事项编码信息
                List<Map<String, Object>> lhysItemList = dao.findBySql(lhysItemSql.toString(), param1.toArray(), null);
                if (null != lhysItemList && lhysItemList.size() >= 2) {
                    List<Map<String, Object>> list1 = dao.findBySql(sql1.toString(), param1.toArray(), null);
                    // 联合验收中某个事项优先申报后，“并联审批实例编号”就填第一个事项的申报号
                    if (null != list1 && list1.size() >= 2) {//所有办件的事项编码种类大于等于2时
                        Map<String, Object> exeMap = list1.get(0);
                        String exeId = exeMap.get("EXE_ID").toString();
                        int push_status = Integer.parseInt(exeMap.get("PUSH_STATUS").toString());
                        if (push_status == 1) {// 第一条数据状态为已推送的时候更改推送状态
                            StringBuffer sql2 = new StringBuffer(
                                    " update JBPM6_EXECUTION set push_status = 0 where exe_id in ");
                            sql2.append(" (select e.exe_id from JBPM6_EXECUTION e  where e.item_code in ( ");
                            sql2.append(" select item.item_code from T_FLOW_CONFIG_ITEM t ");
                            sql2.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
                            sql2.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
                            sql2.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
                            sql2.append(" where t.dybzspsxbm in ('0120','0130','0140','0150','0160','0170')");
                            sql2.append(
                                    " group by item.item_code) and e.run_status<>0 and e.project_code =? and e.push_status =1 )");
                            dao.executeSql(sql2.toString(), param1.toArray());
                        }
                        return exeId;
                    }
                }
            }

        }
        return null;
    }
    /**
     * 
     * 描述 获取联合验收SQL
     * @author Rider Chen
     * @created 2021年3月8日 下午5:09:04
     * @return
     */
    private StringBuffer getLhysExeSql() {
        StringBuffer sql1 = new StringBuffer(" select e.* from JBPM6_EXECUTION e  where e.item_code in ( ");
        sql1.append(" select item.item_code from T_FLOW_CONFIG_ITEM t ");
        sql1.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
        sql1.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
        sql1.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
        sql1.append(" where t.dybzspsxbm in ('0120','0130','0140','0150','0160','0170') ");
        sql1.append(" group by item.item_code) and e.run_status<>0 and e.project_code =? ");
        sql1.append(" order by e.create_time asc");
        return sql1;
    }
    /**
     * 
     * 描述 获取联合省图SQL
     * @author Rider Chen
     * @created 2021年3月8日 下午5:08:29
     * @return
     */
    private StringBuffer getLhstExeSql() {
        StringBuffer sql1 = new StringBuffer(" select e.* from JBPM6_EXECUTION e  where e.item_code in ( ");
        sql1.append(" select item.item_code from T_FLOW_CONFIG_ITEM t ");
        sql1.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
        sql1.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
        sql1.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
        sql1.append(
                " where t.dybzspsxbm like '%0080%' or t.dybzspsxbm like '%0100%'  or t.dybzspsxbm like '%0480%' ");
        sql1.append(" group by item.item_code) and e.run_status<>0 and e.project_code =? ");
        sql1.append(" order by e.create_time asc");
        return sql1;
    } 
    /**
     * 
     * 描述 获取联合省图事项SQL
     * @author Rider Chen
     * @created 2021年3月8日 下午5:08:29
     * @return
     */
    private StringBuffer getLhstItemSql() {
        StringBuffer sql1 = new StringBuffer(" select e.item_code from JBPM6_EXECUTION e  where e.item_code in ( ");
        sql1.append(" select item.item_code from T_FLOW_CONFIG_ITEM t ");
        sql1.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
        sql1.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
        sql1.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
        sql1.append(
                " where t.dybzspsxbm like '%0080%' or t.dybzspsxbm like '%0100%'  or t.dybzspsxbm like '%0480%' ");
        sql1.append(" group by item.item_code) and e.run_status<>0 and e.project_code =? ");
        sql1.append(" group by e.item_code");
        return sql1;
    }    
    /**
     * 
     * 描述 获取联合验收SQL
     * @author Rider Chen
     * @created 2021年3月8日 下午5:09:04
     * @return
     */
    private StringBuffer getLhysItemSql() {
        StringBuffer sql1 = new StringBuffer(" select e.item_code from JBPM6_EXECUTION e  where e.item_code in ( ");
        sql1.append(" select item.item_code from T_FLOW_CONFIG_ITEM t ");
        sql1.append(" join T_FLOW_CONFIG_STAGE S ON T.STAGE_ID = S.STAGE_ID ");
        sql1.append(" JOIN T_FLOW_CONFIG_TYPE ft  on ft.type_id = s.type_id ");
        sql1.append(" join t_wsbs_serviceitem item on item.item_id = t.item_id ");
        sql1.append(" where t.dybzspsxbm in ('0120','0130','0140','0150','0160','0170') ");
        sql1.append(" group by item.item_code) and e.run_status<>0 and e.project_code =? ");
        sql1.append(" group by e.item_code");
        return sql1;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findEexcutionTaskList(Map<String, Object> parentMap, int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("   select T.TASK_ID AS DFSJZJ,350128 AS XZQHDM ,E.PROJECT_CODE AS GCDM, ");
        sql.append(" T.EXE_ID AS SPSXSLBM,T.ASSIGNER_NAME AS BLR,T.TASK_STATUS AS BLZT");
        sql.append(" ,T.HANDLE_OPINION AS BLYJ,T.END_TIME as BLSJ ,E.RUN_STATUS");
        sql.append(" FROM JBPM6_TASK T JOIN JBPM6_EXECUTION e ON T.EXE_ID = E.EXE_ID ");
        sql.append(" join t_wsbs_serviceitem s on e.item_code = s.item_code ");
        sql.append(" join SPGL_XMJBXXB x on e.PROJECT_CODE = x.project_code ");
        sql.append(" join t_flow_config_category c on x.flow_cate_id = c.id ");
        sql.append(" where T.IS_REAL_HANDLED = 1 and T.END_TIME is not null ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and T.TASK_ID = ? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and t.push_status = ? ");
                param.add(status);
            }
        }
        if (null != parentMap) {
            String dfsjzj = parentMap.get("DFSJZJ").toString();
            if (StringUtils.isNotEmpty(dfsjzj)) {
                sql.append(" and t.exe_id = ? ");
                param.add(dfsjzj);
            }
        }
        sql.append("  order by t.exe_id asc,t.end_time asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
            map.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            map.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            map.put("SJSCZT", 0);// 数据上传状态
            map.put("BLZT", getBlzt(map.get("BLZT").toString(), map.get("RUN_STATUS").toString()));// 办理状态
        }
        return list;
    }

    public List<Map<String, Object>> findXspsxblxxxxb(Map<String, Object> parentMap, int status) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.* FROM SPGL_XMSPSXBLXXXXB t");
        sql.append(" where 1=1 ");
        if (status >= 0) {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        if (null != parentMap) {
            String dfsjzj = parentMap.get("DFSJZJ").toString();
            if (StringUtils.isNotEmpty(dfsjzj)) {
                sql.append(" and t.spsxslbm = ? ");
                param.add(dfsjzj);
            }
        }
        sql.append("  order by t.blsj asc,t.spsxslbm asc ");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        return list;
    }

    /**
     * 
     * 描述：转换状态
     * 
     * @author Rider Chen
     * @created 2019年6月19日 下午2:56:32
     * @param taskStatus
     *            任务状态(-1:挂起 1:正在审核 2:已审核 3:退回 4:转发 5:委托,6:结束流程)
     * @param runStatus
     *            流程状态(0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),
     *            4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回))
     * @return
     */
    private int getBlzt(String taskStatus, String runStatus) {
        int num = 1;
        if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("1")) {
            if (taskStatus.equals("1")) {
                num = 1;
            } else if (taskStatus.equals("2")) {
                num = 3;
            } else if (taskStatus.equals("3")) {
                num = 6;
            } else if (taskStatus.equals("4")) {
                num = 3;
            } else if (taskStatus.equals("5")) {
                num = 3;
            } else if (taskStatus.equals("6")) {
                num = 11;
            } else if (taskStatus.equals("-1")) {
                num = 9;
            }
        } else if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("2")) {
            num = 11;
        } else if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("3")) {
            num = 12;
        } else if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("4")) {
            num = 13;
        } else if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("5")) {
            num = 4;
        } else if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("6")) {
            num = 5;
        } else if (StringUtils.isNotEmpty(taskStatus) && StringUtils.isNotEmpty(runStatus) && runStatus.equals("7")) {
            num = 4;
        }
        return num;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findEexcutionFileList(Map<String, Object> parentMap, int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("  select f.file_id as DFSJZJ,350128 as XZQHDM,e.PROJECT_CODE as GCDM ");
        sql.append(" ,e.exe_id as SPSXSLBM,f.file_name as FJMC,1 as FJFL,f.file_type as FJLX, f.file_id as FJID");
        sql.append("  from T_MSJW_SYSTEM_FILEATTACH f  join  JBPM6_EXECUTION e");
        sql.append(" on f.bus_tablerecordid = e.bus_recordid and f.bus_tablename = e.bus_tablename ");
        sql.append(" join t_wsbs_serviceitem s on e.item_code = s.item_code ");
        sql.append(" join SPGL_XMJBXXB x on e.PROJECT_CODE = x.project_code ");
        sql.append(" join t_flow_config_category c on x.flow_cate_id = c.id ");
        sql.append(" where f.file_path is not null  ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and f.file_id = ? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and f.push_status = ? ");
                param.add(status);
            }
        }
        if (null != parentMap) {
            String dfsjzj = parentMap.get("DFSJZJ").toString();
            if (StringUtils.isNotEmpty(dfsjzj)) {
                sql.append(" and e.exe_id = ? ");
                param.add(dfsjzj);
            }
        }
        sql.append("  order by f.create_time asc");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("13", "S_SPGL_XMQTFJXXB"));// 流水号;
            map.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            map.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            map.put("SJSCZT", 0);// 数据上传状态
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findNoEndXmjbxxb(String state) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = null;
        try {
            List<Object> params = new ArrayList<Object>();
            StringBuffer sql = new StringBuffer("select t.* from SPGL_XMJBXXB t ");
            sql.append(" WHERE t.xmsfwqbj = ? ");
            sql.append(" ORDER BY T.CREATE_TIME ASC ");
            params.add(state);
            list = dao.findBySql(sql.toString(), params.toArray(), null);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isNoEndXmjbxxb(String projectCode) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            List<Object> params = new ArrayList<Object>();
            StringBuffer sql = new StringBuffer(
                    "select S.NAME,T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,");
            sql.append(" T.FWSXZT,T.BUSINESS_CODE from T_WSBS_SERVICEITEM T ");
            sql.append(" JOIN T_FLOW_CONFIG_ITEM L   ON T.ITEM_ID = L.ITEM_ID ");
            sql.append(" JOIN T_FLOW_CONFIG_STAGE S   ON S.STAGE_ID = L.STAGE_ID ");
            sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = S.TYPE_ID  ");
            sql.append(" JOIN SPGL_XMJBXXB X  ON C.ID = X.FLOW_CATE_ID ");
            sql.append(" WHERE T.FWSXZT='1' AND L.IS_KEY_ITEM ='1' ");
            sql.append(" AND X.PROJECT_CODE = ? ");
            params.add(projectCode);
            sql.append(" ORDER BY S.TREE_SN ASC,S.CREATE_TIME ASC,L.SORT ASC ");
            List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
            String projectSql = "select t.exe_id,t.RUN_STATUS from JBPM6_EXECUTION t where "
                    + "t.item_code = ? and t.PROJECT_CODE = ? order by t.create_time desc";
            if (null != list) {
                for (Map<String, Object> map : list) {
                    String ITEM_CODE = map.get("ITEM_CODE").toString();
                    List<Map<String, Object>> exeList = dao.findBySql(projectSql,
                            new Object[] { ITEM_CODE, projectCode }, null);
                    if (null != exeList && exeList.size() > 0) {
                        String runStatus = exeList.get(0).get("RUN_STATUS").toString();
                        if (runStatus.equals("2") || runStatus.equals("3")) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    } else {
                        flag = false;
                        return flag;
                    }
                }
            } else {
                flag = false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateXmsfwqbj(String xmjbxxb_id) {
        // TODO Auto-generated method stub
        try {
            List<Object> params = new ArrayList<Object>();
            String goTimeSql = "select t.end_time from JBPM6_EXECUTION t join SPGL_XMJBXXB x"
                    + " on t.PROJECT_CODE = x.project_code where t.end_time is not null "
                    + " and x.id = ? order by t.end_time desc ";
            List<Map<String, Object>> list = dao.findBySql(goTimeSql, new Object[] { xmjbxxb_id }, null);
            String time = "";
            if (null != list && list.size() > 0) {
                time = list.get(0).get("END_TIME").toString();
                String sql = "update SPGL_XMJBXXB x set x.push_status=?, x.xmsfwqbj=?, x.xmwqbjsj=? where x.id = ?";
                params.add("0");
                params.add("1");
                params.add(time);
                params.add(xmjbxxb_id);
                dao.executeSql(sql, params.toArray());
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveAfterFile(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        // 是否退回流程
        String isback = (String) flowDatas.get("EFLOW_ISBACK");
        // 投资项目编号
        String projectCode = (String) flowDatas.get("PROJECT_CODE");
        // 退回流程，不执行，投资项目编号不能为空
        if (!"true".equals(isback) && StringUtils.isNotEmpty(projectCode)) {
            // 当前节点名称
            // String currNodeName = (String)
            // flowDatas.get("EFLOW_CUREXERUNNINGNODENAMES");
            // 审批件扫描件
            String submitmaterfilejson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
            if (StringUtils.isNotEmpty(submitmaterfilejson)) {
                List<Map> maters = JSON.parseArray(submitmaterfilejson, Map.class);
                String sql = " SELECT TRANSID FROM SPGL_FILETRANS WHERE FILE_ID = ? ";
                if (maters != null && maters.size() > 0) {
                    for (Map map : maters) {
                        String fileId = String.valueOf(map.get("FILE_ID"));
                        Map<String, Object> filetemp = dao.getByJdbc(sql, new Object[] { fileId });
                        if (filetemp == null) {
                            Map<String, Object> file = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                                    new String[] { "FILE_ID" }, new Object[] { fileId });
                            if (file != null) {
                                String file_path = String.valueOf(file.get("FILE_PATH"));
                                String file_txt = FileUtil.convertFileToString(attachFilePath + file_path);
                                Map<String, Object> fileInfo = new HashMap<String, Object>();
                                fileInfo.put("FILE_ID", fileId);
                                fileInfo.put("FILE_PATH", file_path);
                                fileInfo.put("FILE_TXT", file_txt);
                                fileInfo.put("PLAT_TYPE", "1");
                                fileInfo.put("PARSE_STATUS", "0");
                                dao.saveOrUpdate(fileInfo, "SPGL_FILETRANS", null);
                            }
                        }
                    }
                }
            }
            String dyqrxxFileId = flowDatas.get("DYQRXX_FILE_ID") == null ? ""
                    : flowDatas.get("DYQRXX_FILE_ID").toString();
            saveFile(dyqrxxFileId);
            String dyqdjFileId = flowDatas.get("DYQDJ_FILE_ID") == null ? ""
                    : flowDatas.get("DYQDJ_FILE_ID").toString();
            saveFile(dyqdjFileId);
            String ygygFileId = flowDatas.get("YGYG_FILE_ID") == null ? "" : flowDatas.get("YGYG_FILE_ID").toString();
            saveFile(ygygFileId);
            String ysdyqygFileId = flowDatas.get("YSDYQYG_FILE_ID") == null ? ""
                    : flowDatas.get("YSDYQYG_FILE_ID").toString();
            saveFile(ysdyqygFileId);
            String slxxFileId = flowDatas.get("SLXX_FILE_ID") == null ? "" : flowDatas.get("SLXX_FILE_ID").toString();
            saveFile(slxxFileId);
            String qlrFileId = flowDatas.get("QLR_FILE_ID") == null ? "" : flowDatas.get("QLR_FILE_ID").toString();
            saveFile(qlrFileId);
            String legalFileId = flowDatas.get("LEGAL_FILE_ID") == null ? ""
                    : flowDatas.get("LEGAL_FILE_ID").toString();
            saveFile(legalFileId);
            String powagentFileId = flowDatas.get("POWAGENT_FILE_ID") == null ? ""
                    : flowDatas.get("POWAGENT_FILE_ID").toString();
            saveFile(powagentFileId);
            String frdbFileId = flowDatas.get("FRDB_FILE_ID") == null ? "" : flowDatas.get("FRDB_FILE_ID").toString();
            saveFile(frdbFileId);
            String dlrFileId = flowDatas.get("DLR_FILE_ID") == null ? "" : flowDatas.get("DLR_FILE_ID").toString();
            saveFile(dlrFileId);
        }
        return flowDatas;
    }

    @SuppressWarnings("unchecked")
    private void saveFile(String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] id = ids.split(";");
            for (int i = 0; i < id.length; i++) {
                String fileId = id[i];
                String sql = " SELECT TRANSID FROM SPGL_FILETRANS WHERE FILE_ID = ? ";
                Map<String, Object> filetemp = dao.getByJdbc(sql, new Object[] { fileId });
                if (filetemp == null) {
                    Map<String, Object> file = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                            new Object[] { fileId });
                    if (file != null) {
                        String file_path = String.valueOf(file.get("FILE_PATH"));
                        String file_txt = FileUtil.convertFileToString(attachFilePath + file_path);
                        Map<String, Object> fileInfo = new HashMap<String, Object>();
                        fileInfo.put("FILE_ID", fileId);
                        fileInfo.put("FILE_PATH", file_path);
                        fileInfo.put("FILE_TXT", file_txt);
                        fileInfo.put("PLAT_TYPE", "1");
                        fileInfo.put("PARSE_STATUS", "0");
                        dao.saveOrUpdate(fileInfo, "SPGL_FILETRANS", null);
                    }
                }
            }
        }
    }
    /**
     * 推送工程建设状态数据
     * @param flowDatas
     */
    @Override
    public void pushGcjsStatusData(Map<String,Object> flowDatas){
        GcjsPushStatusTemplate gcjsPushStatusTemplate=GcjsPushStatusFactory.getGcjsPushStatusTemplage(flowDatas);
        //如果流程是新定义的流程模板，走新的推送逻辑，不然走原来的推送逻辑
        if(Objects.nonNull(gcjsPushStatusTemplate)){
            gcjsPushStatusTemplate.saveAfterToXmspsxblxxxxb(flowDatas);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveAfterToXmspsxblxxxxb(Map<String, Object> flowDatas) {
        GcjsPushStatusTemplate gcjsPushStatusTemplate = GcjsPushStatusFactory.getGcjsPushStatusTemplage(flowDatas);
        // 如果流程是新定义的流程模板，走新的推送逻辑，不然走原来的推送逻辑
        if (Objects.nonNull(gcjsPushStatusTemplate)) {
            gcjsPushStatusTemplate.saveAfterToXmspsxblxxxxb(flowDatas);
            return;
        }
        // TODO Auto-generated method stub
        Map<String, Object> info = new HashMap<String, Object>();
        // 先获取流程实例申报号
        String exeId = (String) flowDatas.get("EFLOW_EXEID");
        // 是否暂存
        String EFLOW_ISTEMPSAVE = flowDatas.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowDatas.get("EFLOW_ISTEMPSAVE").toString();
        // 下一步环节名称
        String EFLOW_NEWTASK_NODENAMES = flowDatas.get("EFLOW_NEWTASK_NODENAMES") == null ? ""
                : flowDatas.get("EFLOW_NEWTASK_NODENAMES").toString();
        // 当前提交环节
        String EFLOW_CURUSEROPERNODENAME = flowDatas.get("EFLOW_CURUSEROPERNODENAME") == null ? ""
                : flowDatas.get("EFLOW_CURUSEROPERNODENAME").toString();
        String flowKey = StringUtil.getString(flowDatas.get("EFLOW_DEFKEY"));
        // 定义流程实例对象
        Map<String, Object> flowExe = null;
        if (StringUtils.isNotEmpty(exeId) && !EFLOW_ISTEMPSAVE.equals("1")) {
            flowExe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            // 投资项目编号
            String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
            if (StringUtils.isNotEmpty(projectCode)) {
                // 获取办件状态
                String runStatus = flowExe.get("RUN_STATUS") == null ? "" : flowExe.get("RUN_STATUS").toString();
                // 获取办理意见内容
                String handleOpinion = flowDatas.get("EFLOW_HANDLE_OPINION") == null ? ""
                        : flowDatas.get("EFLOW_HANDLE_OPINION").toString();

                String blcs = flowDatas.get("BELONG_DEPTNAME") == null ? ""
                        : flowDatas.get("BELONG_DEPTNAME").toString();
                if (StringUtils.isEmpty(blcs)) {
                    // 获取当前用户信息
                    SysUser curUser = AppUtil.getLoginUser();
                    if (null != curUser) {
                        blcs = curUser.getDepName();
                    }
                }
                String blr = flowDatas.get("uploadUserName") == null ? "" : flowDatas.get("uploadUserName").toString();
                if (StringUtils.isEmpty(blr)) {
                    // 获取当前用户信息
                    SysUser curUser = AppUtil.getLoginUser();
                    if (null != curUser) {
                        blr = curUser.getFullname();
                    }
                }
                int blzt = 1;
                boolean isStartFlow = isStartFlow(exeId);
                if (isStartFlow) {
                    blzt = getBlzt(runStatus);
                }
                if (blzt == 3) {
                    // 特定流程的状态3与状态8需要在特定环节才生成，其他流程不变
                    if (!isTsswslkshj(flowKey, EFLOW_NEWTASK_NODENAMES)) {
                        return;
                    }
                }
                // 定义任务的办理时间
                String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                info.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                info.put("XZQHDM", "350128");// 行政区划代码;
                info.put("GCDM", projectCode);// 工程代码;
                info.put("SPSXSLBM", exeId);// 审批事项实例编码
                info.put("BLCS", StringUtils.isEmpty(blcs) ? "项目投资处" : blcs);// 办理处（科）室
                info.put("BLR", StringUtils.isEmpty(blr) ? "项目投资处" : blr);// 办理人
                info.put("BLZT", blzt);// 办理状态
                info.put("BLYJ", handleOpinion);// 办理意见
                info.put("BLSJ", endTime);// 办理时间
                info.put("SJYXBS", "1");// 数据有效标识
                info.put("SJSCZT", "0");// 数据上传状态
                Map<String, Object> blxx = getSpsxblxxxxb(exeId, blzt);
                String dfsjzj = "";
                if (null == blxx || blzt == 9 || blzt == 10) {
                    if (blzt == 10) {
                        Map<String, Object> blxx1 = getSpsxblxxxxb(exeId, 9);
                        if (null != blxx1 && blxx1.size() > 0) {
                            dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                        }
                    } else {
                        dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                    }
                }
                if (blzt == 3) {
                    saveAfterToXmspsxblxxxxb(8, exeId, blr, handleOpinion);
                }
                // 当前环节是特殊环节时，增加特别程序结束
                if (StringUtils.isNotEmpty(EFLOW_CURUSEROPERNODENAME) && isStepnames(EFLOW_CURUSEROPERNODENAME)) {
                    if (blzt != 10) {
                        saveAfterToXmspsxblxxxxb(10, exeId, flowExe, blr, handleOpinion);
                    }
                } else if (blzt != 10) {// 当前办理状态不等于10而数据库状态等于9时，新增状态10的数据
                    int newblzt = getSpsxblxxxxb(exeId);
                    if (newblzt == 9) {
                        saveAfterToXmspsxblxxxxb(10, exeId, flowExe, blr, handleOpinion);
                    }
                }
                // 下一步环节是特殊环节时，增加特别程序开始
                if (StringUtils.isNotEmpty(EFLOW_NEWTASK_NODENAMES) && isStepnames(EFLOW_NEWTASK_NODENAMES)) {
                    int newblzt = getSpsxblxxxxb(exeId);
                    if (newblzt != 9) {
                        saveAfterToXmspsxblxxxxb(9, exeId, flowExe, blr, handleOpinion);
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 是否特别程序环节
     * 
     * @author Rider Chen
     * @param CUR_STEPNAMES
     * @return
     */
    public boolean isStepnames(String CUR_STEPNAMES) {
        boolean isok = false;
        Map<String, Object> dic = dictionaryService.get("GCJSXMTBCXHJ", CUR_STEPNAMES);
        if (null != dic && dic.size() > 0) {
            isok = true;
        }
        return isok;
    }

    /**
     * 
     * 描述 是否推送省网受理开始环节
     * 
     * @author Rider Chen
     * @created 2021年11月15日 下午4:49:09
     * @param flowKey
     * @param EFLOW_NEWTASK_NODENAMES
     * @return
     */
    public boolean isTsswslkshj(String flowKey, String EFLOW_NEWTASK_NODENAMES) {
        Map<String, Object> dic = dictionaryService.get("GCJSTSSWSLKSHJ", flowKey);
        if (null != dic && dic.size() > 0) {
            String dicDesc = StringUtil.getString(dic.get("DIC_DESC"));
            String[] nodeNames = dicDesc.split(",");
            for (String nodeName : nodeNames) {
                if (StringUtils.isNotEmpty(EFLOW_NEWTASK_NODENAMES) && StringUtils.isNotEmpty(nodeName)
                        && EFLOW_NEWTASK_NODENAMES.equals(nodeName)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
    /**
     * 
     * 描述：对特殊的环节增加特别程序
     * 
     * @author Rider Chen
     * @created 2019年12月30日 上午11:05:39
     * @param blzt
     * @param exeId
     * @param flowExe
     * @param blr
     * @param blyj
     */
    @SuppressWarnings("unchecked")
    public void saveAfterToXmspsxblxxxxb(int blzt, String exeId, Map<String, Object> flowExe, String blr, String blyj) {
        Map<String, Object> info = new HashMap<String, Object>();
        // 投资项目编号
        String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
        if (StringUtils.isNotEmpty(projectCode)) {
            Map<String, Object> blxx3 = getSpsxblxxxxb(exeId, 3);
            if (null == blxx3 && blzt == 13) {
                blzt = 5;
            }
            // 定义任务的办理时间
            //String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            // 定义任务的办理时间(办理时间推迟二秒)
            String endTime = DateTimeUtil.getStrOfDate(new Date(new Date().getTime() + 2000), "yyyy-MM-dd HH:mm:ss");
            try {
                info.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                info.put("XZQHDM", "350128");// 行政区划代码;
                info.put("GCDM", projectCode);// 工程代码;
                info.put("SPSXSLBM", exeId);// 审批事项实例编码
                // 获取当前用户信息
                SysUser curUser = AppUtil.getLoginUser();
                String blcs = "";
                if (null != curUser) {
                    blcs = curUser.getDepName();
                    if (StringUtils.isEmpty(blr)) {
                        blr = curUser.getFullname();
                    }
                }
                info.put("BLCS", StringUtils.isEmpty(blcs) ? "项目投资处" : blcs);// 办理处（科）室
                info.put("BLR", StringUtils.isEmpty(blr) ? "项目投资处" : blr);// 办理人
                info.put("BLZT", blzt);// 办理状态
                info.put("BLYJ", blyj);// 办理意见
                info.put("BLSJ", endTime);// 办理时间
                info.put("SJYXBS", "1");// 数据有效标识
                info.put("SJSCZT", "0");// 数据上传状态
                Map<String, Object> blxx = getSpsxblxxxxb(exeId, blzt);
                String dfsjzj = "";
                if (null == blxx || blzt == 9 || blzt == 10) {
                    if (blzt == 10) {
                        int newblzt = getSpsxblxxxxb(exeId);
                        if (newblzt == 9) {
                            dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                        }
                    } else {
                        int newblzt = getSpsxblxxxxb(exeId);
                        if (newblzt != 9) {
                            dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                        }
                    }
                }
                // 挂起（特别程序）
                if (blzt == 9) {
                    saveAfterXmspsxbltbcxxxb(exeId, flowExe);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void saveAfterXmspsxbltbcxxxb(String exeId, Map<String, Object> flowExe) {

        // 投资项目编号
        String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
        Map<String, Object> info = new HashMap<String, Object>();
        int tbcx = 8;
        info.put("LSH", getLsh("11", "S_SPGL_XMSPSXBLTBCXXXB"));// 流水号;
        info.put("XZQHDM", 350128);// 行政区划代码
        info.put("GCDM", projectCode);// 工程代码
        info.put("SPSXSLBM", exeId);// 审批事项实例编码
        info.put("TBCX", tbcx);
        info.put("TBCXMC", "其他");
        // 特别程序开始时间
        String sTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        info.put("TBCXKSSJ", sTime);
        info.put("TBCXSXLX", 1);
        info.put("TBCXSX", 0);
        info.put("SJYXBS", "1");// 数据有效标识
        info.put("SJSCZT", "0");// 数据上传状态
        dao.saveOrUpdate(info, "SPGL_XMSPSXBLTBCXXXB", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveAfterToXmspsxblxxxxb(int blzt, String exeId, String blr, String blyj) {
        // TODO Auto-generated method stub
        Map<String, Object> info = new HashMap<String, Object>();// 先获取流程实例申报号
        // 定义流程实例对象
        Map<String, Object> flowExe = null;
        if (StringUtils.isNotEmpty(exeId)) {
            flowExe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            // 投资项目编号
            String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
            if (StringUtils.isNotEmpty(projectCode)) {
                Map<String, Object> blxx3 = getSpsxblxxxxb(exeId, 3);
                if (null == blxx3 && blzt == 13) {
                    blzt = 5;
                }
                // 定义任务的办理时间(办理时间推迟一秒)
                String endTime = DateTimeUtil.getStrOfDate(new Date(new Date().getTime() + 1000), "yyyy-MM-dd HH:mm:ss");
                try {
                    info.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                    info.put("XZQHDM", "350128");// 行政区划代码;
                    info.put("GCDM", projectCode);// 工程代码;
                    info.put("SPSXSLBM", exeId);// 审批事项实例编码
                    // 获取当前用户信息
                    SysUser curUser = AppUtil.getLoginUser();
                    String blcs = "";
                    if (null != curUser) {
                        blcs = curUser.getDepName();
                        if (StringUtils.isEmpty(blr)) {
                            blr = curUser.getFullname();
                        }
                    }
                    info.put("BLCS", StringUtils.isEmpty(blcs) ? "项目投资处" : blcs);// 办理处（科）室
                    info.put("BLR", StringUtils.isEmpty(blr) ? "项目投资处" : blr);// 办理人
                    info.put("BLZT", blzt);// 办理状态
                    info.put("BLYJ", blyj);// 办理意见
                    info.put("BLSJ", endTime);// 办理时间
                    info.put("SJYXBS", "1");// 数据有效标识
                    info.put("SJSCZT", "0");// 数据上传状态
                    Map<String, Object> blxx = getSpsxblxxxxb(exeId, blzt);
                    String dfsjzj = "";
                    if (null == blxx || blzt == 9 || blzt == 10) {
                        if (blzt == 10) {
                            int newblzt = getSpsxblxxxxb(exeId);
                            if (newblzt == 9) {
                                dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                            }
                        } else {
                            int newblzt = getSpsxblxxxxb(exeId);
                            if (newblzt != 9) {
                                dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                            }
                        }
                    }
                    // 挂起（特别程序）
                    if (blzt == 9) {
                        saveAfterXmspsxbltbcxxxb(exeId);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    log.error("", e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void saveAfterXmspsxbltbcxxxb(String exeId) {
        String sql = "select h.hang_id as DFSJZJ,350128 as XZQHDM,e.project_code as GCDM,"
                + "E.EXE_ID as SPSXSLBM,l.link_name as TBCXMC,h.begin_time as TBCXKSSJ,"
                + "1 as TBCXSXLX,l.link_limittime as TBCXSX from  JBPM6_HANGINFO h,"
                + "JBPM6_TASK t ,JBPM6_EXECUTION e,T_WSBS_SERVICEITEM_LINK l "
                + "where t.task_id = h.task_id and t.exe_id = e.exe_id and "
                + "h.link_id = l.record_id and t.exe_id = ? "
                + "and t.step_seq>0 and e.project_code is not null order by  h.begin_time desc ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId }, null);

        for (Map<String, Object> map : list) {
            Map<String, Object> dic = dictionaryService.get("SpecialLinkName", map.get("TBCXMC").toString());
            int tbcx = 8;
            if (null != dic) {
                tbcx = dic.get("DIC_DESC") == null ? 8 : Integer.parseInt(dic.get("DIC_DESC").toString());
            }
            map.put("LSH", getLsh("11", "S_SPGL_XMSPSXBLTBCXXXB"));// 流水号;
            map.put("TBCX", tbcx);
            map.put("SJYXBS", "1");// 数据有效标识
            map.put("SJSCZT", "0");// 数据上传状态
            Map<String, Object> tbcxMap = getSpsxbltbcxxxb(exeId, tbcx, map.get("TBCXMC").toString(),
                    map.get("TBCXKSSJ").toString());
            if (null == tbcxMap) {
                dao.saveOrUpdate(map, "SPGL_XMSPSXBLTBCXXXB", map.get("DFSJZJ").toString());
            }
        }
    }

    /**
     * 
     * 描述： 获取特别程序
     * 
     * @author Rider Chen
     * @created 2019年12月31日 上午10:17:06
     * @param exeId
     * @param tbcx
     * @param tbcxmc
     * @param tbcxkssj
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getSpsxbltbcxxxb(String exeId, int tbcx, String tbcxmc, String tbcxkssj) {
        String sql = " SELECT T.* FROM SPGL_XMSPSXBLTBCXXXB T WHERE  T.SPSXSLBM= ? AND T.TBCX=? "
                + " AND T.TBCXMC=? AND T.TBCXKSSJ=? ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId, tbcx, tbcxmc, tbcxkssj }, null);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 
     * 描述：转换状态
     * 
     * @author Rider Chen
     * @created 2019年6月19日 下午2:56:32
     * @param runStatus
     *            流程状态(0:草稿,1:正在办理中,2:已办结(正常结束),3:已办结(审核通过),
     *            4:已办结(审核不通过),5:已办结(退回),6:强制结束,7：预审不通过(退回))
     * @return
     */
    private int getBlzt(String runStatus) {
        int blzt = 1;
        if (StringUtils.isNotEmpty(runStatus)) {
            if (runStatus.equals("1")) {
                blzt = 3;
            } else if (runStatus.equals("2")) {
                blzt = 11;
            } else if (runStatus.equals("3")) {
                blzt = 11;
            } else if (runStatus.equals("4")) {
                blzt = 13;
            } else if (runStatus.equals("5")) {
                blzt = 13;
            } else if (runStatus.equals("6")) {
                blzt = 13;
            } else if (runStatus.equals("7")) {
                blzt = 13;
            }
        }
        return blzt;
    }

    /**
     * 
     * 描述 根据流水号和办理状态获取详情
     * 
     * @author Rider Chen
     * @created 2019年7月5日 下午5:27:44
     * @param exeId
     * @param blzt
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getSpsxblxxxxb(String exeId, int blzt) {
        String sql = " SELECT T.* FROM SPGL_XMSPSXBLXXXXB T WHERE  T.SPSXSLBM= ? AND T.BLZT=? ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId, blzt }, null);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 
     * 描述 根据流水号获取最新的办理状态
     * 
     * @author Rider Chen
     * @created 2019年7月5日 下午5:27:44
     * @param exeId
     * @param blzt
     * @return
     */
    @SuppressWarnings("unchecked")
    private int getSpsxblxxxxb(String exeId) {
        String sql = " SELECT T.* FROM SPGL_XMSPSXBLXXXXB T WHERE  T.SPSXSLBM= ? ORDER BY T.BLSJ DESC ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId, }, null);
        if (null != list && list.size() > 0) {
            return Integer.parseInt(list.get(0).get("BLZT").toString());
        }
        return 0;
    }

    /**
     * 
     * 描述 判断当前是否是发起流程
     * 
     * @author Flex Hu
     * @created 2015年8月20日 下午3:13:39
     * @param exeId
     * @param defId
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean isStartFlow(String exeId) {
        StringBuffer sql = new StringBuffer("select t.* from SPGL_XMSPSXBLXXXXB t ");
        sql.append(" WHERE t.spsxslbm = ?　");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { exeId }, null);
        if (null != list && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> setProjectInvalid() {
        // TODO Auto-generated method stub
        Map<String, Object> idsMap = new HashMap<String, Object>();
        // 地方项目审批流程信息ID
        StringBuffer sql1 = new StringBuffer(
                "select wm_concat(T.TYPE_ID) as IDS from T_flow_config_type t where 1 = 1 ");
        Map<String, Object> dfxmsplcxxbIds = dao.getByJdbc(sql1.toString(), null);
        idsMap.put("dfxmsplcxxbIds", dfxmsplcxxbIds.get("IDS"));
        // 地方项目审批流程阶段信息ID
        StringBuffer sql2 = new StringBuffer("select wm_concat(S.STAGE_ID) AS IDS from T_FLOW_CONFIG_STAGE S");
        sql2.append(" join T_FLOW_CONFIG_TYPE T ON S.TYPE_ID = T.TYPE_ID where 1 = 1 ");
        Map<String, Object> dfxmsplcjdxxbIds = dao.getByJdbc(sql2.toString(), null);
        idsMap.put("dfxmsplcjdxxbIds", dfxmsplcjdxxbIds.get("IDS"));
        // 地方项目审批流程阶段事项信息ID
        StringBuffer sql3 = new StringBuffer("select wm_concat(t.id) as IDS  from T_FLOW_CONFIG_ITEM t");
        sql3.append(" join T_FLOW_CONFIG_STAGE st on t.stage_id = st.stage_id ");
        sql3.append(" join t_wsbs_serviceitem s on t.item_id = s.item_id  ");
        sql3.append(" join T_MSJW_SYSTEM_DEPARTMENT d on s.ssbmbm = d.depart_code  where 1 = 1");
        Map<String, Object> dfxmsplcjdsxxxbIds = dao.getByJdbc(sql3.toString(), null);
        idsMap.put("dfxmsplcjdsxxxbIds", dfxmsplcjdsxxxbIds.get("IDS"));
        // 项目基本信息ID
        StringBuffer sql4 = new StringBuffer("select wm_concat(t.ID) as IDS from SPGL_XMJBXXB t ");
        sql4.append(" join T_FLOW_CONFIG_CATEGORY c  on t.flow_cate_id = c.id ");
        sql4.append("  join T_flow_config_type t1 on C.flow_type_id = t1.type_id where 1 = 1 ");
        Map<String, Object> xmjbxxbIds = dao.getByJdbc(sql4.toString(), null);
        idsMap.put("xmjbxxbIds", xmjbxxbIds.get("IDS"));
        // 项目单位信息ID
        StringBuffer sql5 = new StringBuffer("select wm_concat(x.ID) as IDS from SPGL_XMJBXXB t ");
        sql5.append(" join T_FLOW_CONFIG_CATEGORY c  on t.flow_cate_id = c.id ");
        sql5.append(" join T_flow_config_type t1 on C.flow_type_id = t1.type_id  ");
        sql5.append(" join SPGL_XMDWXXB X ON T.ID = X.JBXX_ID  where 1 = 1 ");
        Map<String, Object> xmdwxxbIds = dao.getByJdbc(sql5.toString(), null);
        idsMap.put("xmdwxxbIds", xmdwxxbIds.get("IDS"));
        // 申报ID
        StringBuffer sql6 = new StringBuffer("select wm_concat(exe_id) as IDS from (");
        sql6.append("select e.exe_id from JBPM6_EXECUTION e ");
        sql6.append(" join t_wsbs_serviceitem s  on e.item_code = s.item_code ");
        sql6.append(" join T_MSJW_SYSTEM_DEPARTMENT d on s.ssbmbm = d.depart_code ");
        sql6.append(" join SPGL_XMJBXXB x on e.PROJECT_CODE = x.project_code  ");
        sql6.append(" join t_flow_config_category c on x.flow_cate_id = c.id   ");
        sql6.append(" join T_flow_config_type t1 on C.flow_type_id = t1.type_id where 1=1 ");
        sql6.append(" and e.run_status<>0 ");
        sql6.append(" union all ");
        sql6.append(" select e.exe_id from JBPM6_EXECUTION_EVEHIS e ");
        sql6.append(" join t_wsbs_serviceitem s  on e.item_code = s.item_code ");
        sql6.append(" join T_MSJW_SYSTEM_DEPARTMENT d on s.ssbmbm = d.depart_code ");
        sql6.append(" join SPGL_XMJBXXB x on e.PROJECT_CODE = x.project_code  ");
        sql6.append(" join t_flow_config_category c on x.flow_cate_id = c.id   ");
        sql6.append(" join T_flow_config_type t1 on C.flow_type_id = t1.type_id where 1=1 ");
        sql6.append(" and e.run_status<>0 ");
        sql6.append(" )");
        Map<String, Object> exeIds = dao.getByJdbc(sql6.toString(), null);
        idsMap.put("exeIds", exeIds.get("IDS"));

        Map<String, Object> result = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "setProjectInvalid");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(idsMap));
            result = TokenUtil.doPost(devbaseUrl, params);
            //log.info("setProjectInvalid返回数据：" + result);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    /**
     * 描述:根据事项编码获取工程建设项目当前所处阶段
     *
     * @author Madison You
     * @created 2019/10/14 11:29:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> findProjectJD(String itemCode) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> params = new ArrayList<>();
        params.add(itemCode);
        sql.append(" select c.NAME from T_WSBS_SERVICEITEM a ");
        sql.append(" left join T_FLOW_CONFIG_ITEM b on a.ITEM_ID = b.ITEM_ID ");
        sql.append(" left join T_FLOW_CONFIG_STAGE c on c.STAGE_ID = b.STAGE_ID ");
        sql.append(" where ITEM_CODE = ? ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        Map<String, Object> map = list.get(0);
        return map;
    }

    /**
     * 根据配置分类表ID，查找工程项目阶段列表
     * 
     * @param categoryId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findStageList(String categoryId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT DISTINCT(S.STAGE_ID) AS ID,S.NAME, S.TREE_SN ");
        sql.append(" FROM T_FLOW_CONFIG_STAGE S ");
        sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = S.TYPE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_ITEM I ON S.STAGE_ID = I.STAGE_ID ");
        sql.append(" AND C.ID = ? ");
        sql.append(" ORDER BY S.TREE_SN ");
        params.add(categoryId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 
     * 描述：获取阶段进展情况
     * 
     * @author Scolder Lin
     * @created 2019年11月28日 上午14:28:42
     * @param projectCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public String findStageProgress(String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT R.NAME ");
        sql.append(" FROM (SELECT FS.*, T.PROJECT_CODE ");
        sql.append(" FROM JBPM6_EXECUTION T ");
        sql.append(" JOIN T_WSBS_SERVICEITEM S ON T.ITEM_CODE = S.ITEM_CODE ");
        sql.append(" JOIN T_FLOW_CONFIG_ITEM I ON S.ITEM_ID = I.ITEM_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_STAGE FS ON I.STAGE_ID = FS.STAGE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_TYPE FT ON FT.TYPE_ID = FS.TYPE_ID ");
        sql.append(" JOIN SPGL_XMJBXXB X ON X.PROJECT_CODE = T.PROJECT_CODE ");
        sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.ID = X.FLOW_CATE_ID ");
        sql.append(" AND C.FLOW_TYPE_ID = FT.TYPE_ID ");
        sql.append(" WHERE T.PROJECT_CODE = ? ");
        sql.append(" AND T.RUN_STATUS = ? ");
        sql.append(" AND S.FWSXZT = 1 ");
        sql.append(" ORDER BY FS.TREE_SN ASC) R ");
        sql.append(" WHERE ROWNUM = 1 ");
        params.add(projectCode);
        params.add("1");
        Map<String, Object> resultMap = dao.getByJdbc(sql.toString(), params.toArray());
        if (resultMap != null && resultMap.size() > 0) {
            return resultMap.get("NAME").toString();
        }
        return "";
    }

    /**
     * 
     * 描述：工程事项详情列表信息
     * 
     * @author Scolder Lin
     * @created 2019年11月28日 上午15:28:42
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findProjectDetailList(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT S.STAGE_ID AS CATEGORY_ID,S.NAME,");
        sql.append(" T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.BACKOR_NAME,");
        sql.append(" T.C_SN,D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE,L.IS_KEY_ITEM from T_WSBS_SERVICEITEM T ");
        sql.append(" JOIN T_FLOW_CONFIG_ITEM L ON T.ITEM_ID = L.ITEM_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_STAGE S   ON S.STAGE_ID = L.STAGE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = S.TYPE_ID  ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append(" LEFT JOIN (select CATALOG_CODE,D1.DEPART_ID,D1.DEPART_CODE from t_wsbs_serviceitem_catalog C ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID) DC ");
        sql.append("ON DC.CATALOG_CODE=T.CATALOG_CODE ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT in (-1,1)");
        String PROJECT_CODE = sqlFilter.getRequest().getParameter("PROJECT_CODE");
        // sql.append(" order by ecount desc ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        String projectSql = "select * from (select t.exe_id,t.RUN_STATUS,r.check_status,"
                + "0 as isFiled,t.create_time,t.HANDLE_OVERTIME,t.ITEM_CODE from JBPM6_EXECUTION t "
                + " left join T_WSBS_PROJECT_RECALL r on t.exe_id = r.exe_id " + "where t.PROJECT_CODE = ? "
                + " union all " + "select t.exe_id,t.RUN_STATUS,r.check_status,1 as isFiled,t.create_time,"
                + "t.HANDLE_OVERTIME,t.ITEM_CODE from JBPM6_EXECUTION_EVEHIS t "
                + " left join T_WSBS_PROJECT_RECALL r on t.exe_id = r.exe_id "
                + "where  t.PROJECT_CODE = ? ) a  order by a.create_time desc";
        List<Map<String, Object>> exeList = dao.findBySql(projectSql, new Object[] { PROJECT_CODE, PROJECT_CODE },
                null);
        for (Map<String, Object> map : list) {
            String ITEM_CODE = map.get("ITEM_CODE").toString();
            boolean flag = false;
            String EXE_ID = "";
            String RUN_STATUS = "";
            String ISFILED = "";
            String CREATE_TIME = "";
            String HANDLE_OVERTIME = "";
            for (Map<String, Object> exeMap : exeList) {
                String EXE_ITEM_CODE = exeMap.get("ITEM_CODE").toString();
                if (EXE_ITEM_CODE.equals(ITEM_CODE)) {
                    flag = true;
                    EXE_ID = StringUtil.getString(exeMap.get("EXE_ID"));
                    RUN_STATUS = StringUtil.getString(exeMap.get("RUN_STATUS"));
                    ISFILED = StringUtil.getString(exeMap.get("ISFILED"));
                    CREATE_TIME = StringUtil.getString(exeMap.get("CREATE_TIME"));
                    HANDLE_OVERTIME = StringUtil.getString(exeMap.get("HANDLE_OVERTIME"));
                    break;
                }
            }
            if (flag) {
                map.put("EXE_ID", EXE_ID);
                map.put("STATE", RUN_STATUS);
                map.put("CREATE_TIME", CREATE_TIME);
                map.put("END_DATE", HANDLE_OVERTIME);
                map.put("ISFILED", ISFILED);
            } else {
                map.put("EXE_ID", "");
                map.put("STATE", "-1");
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findXmspsxbltbcxxxb(Map<String, Object> parentMap, int status) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.* FROM SPGL_XMSPSXBLTBCXXXB t");
        sql.append(" where 1=1 ");
        if (status >= 0) {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        if (null != parentMap) {
            String spsxslbm = parentMap.get("SPSXSLBM").toString();
            if (StringUtils.isNotEmpty(spsxslbm)) {
                sql.append(" and t.spsxslbm = ? ");
                param.add(spsxslbm);
            }
        }
        sql.append("  order by t.TBCXKSSJ asc,t.spsxslbm asc ");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmspsxbltbcxxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findXmspsxbltbcxxxb(null, 0);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmspsxbltbcxxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmspsxbltbcxxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("SPGL_XMSPSXBLTBCXXXB", 1, "DFSJZJ", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    /**
     * 获取工程建设项目撤回列表数据(未审核)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findProjectRecallList() {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_WSBS_PROJECT_RECALL T ");
        sql.append(" WHERE T.CHECK_STATUS = ? ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        params.add("0");// 0::未审核 1:审核通过 2:审核不通过
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 去掉流程表中projectCode值
     * 
     * @param exeId
     */
    public void updateExeProCode(String exeId) {
        String sql = "UPDATE JBPM6_EXECUTION T SET T.PROJECT_CODE = '' WHERE T.EXE_ID=?";
        dao.executeSql(sql, new Object[] { exeId });
    }

    /**
     * 更新工程项目撤回表信息
     * 
     * @param recallId
     * @param type
     */
    public void updateRecallInfo(String recallId, String type) {
        String sql = "UPDATE T_WSBS_PROJECT_RECALL T SET T.CHECK_STATUS=? WHERE T.YW_ID = ? ";
        dao.executeSql(sql, new Object[] { type, recallId });
    }

    /**
     * 
     * 描述：工程建设项目撤回事项列表
     * 
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findProjectRecallData(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT X.PROJECT_CODE,X.PROJECT_NAME,E.SUBJECT,");
        sql.append(" T.YW_ID,T.EXE_ID,T.CHECK_STATUS ");
        sql.append(" FROM T_WSBS_PROJECT_RECALL T ");
        sql.append(" JOIN SPGL_XMJBXXB X ");
        sql.append(" ON X.PROJECT_CODE = T.PROJECT_CODE ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ");
        sql.append(" ON T.EXE_ID = E.EXE_ID ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findTBBuilderLicenceManage(Map<String, Object> parentMap, int status) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.*,E.EXE_ID FROM T_BSFW_GCJSSGXK t,JBPM6_EXECUTION e ");
        sql.append(" where 1=1 and t.CONSTRNUM is not null ");
        sql.append(" and t.yw_id = e.bus_recordid and e.run_status=2 ");
        if (status >= 0) {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        if (null != parentMap) {
            String ywId = parentMap.get("YW_ID").toString();
            if (StringUtils.isNotEmpty(ywId)) {
                sql.append(" and t.YW_ID = ? ");
                param.add(ywId);
            }
        }
        sql.append("  order by t.SUBDATE asc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        Map<String, Object> newMap = null;
        for (Map<String, Object> map : list) {
            newMap = new HashMap<String, Object>();
            setSgxkqzkList(newList, newMap, map);
        }
        return newList;
    }

    @SuppressWarnings("unchecked")
    private void setSgxkqzkList(List<Map<String, Object>> newList, Map<String, Object> newMap,
            Map<String, Object> map) {
        newMap.put("YW_ID", map.get("YW_ID"));// 主键ID
        newMap.put("BUILDERLICENCENUM", map.get("CONSTRNUM"));// 施工许可证编号
        newMap.put("BUIPRJNAME", map.get("PROJECT_NAME"));// 工程名称
        newMap.put("PRJCODE", map.get("PROJECTCODE"));// 项目代码
        newMap.put("PRJNUM", map.get("PRJNUM"));// 项目编号
        newMap.put("IDCARDTYPENUM", map.get("JBRIDENTITYTYPENUM"));// 建设单位名称
        newMap.put("PERSONIDCARD", map.get("JBRIDENTITY"));// 证件类型
        newMap.put("BUILDCORPNAME", map.get("BUILDCORPNAME"));// 证件号码
        newMap.put("BUILDPLANNUM", map.get("BULDPLANNUM"));// 建设用地规划许可证编号
        newMap.put("PROJECTPLANNUM", map.get("PROJECTPLANNUM"));// 建设工程规划许可证编号

        String ZBDW_JSON = map.get("ZBDW_JSON") == null ? "" : map.get("ZBDW_JSON").toString();
        if (StringUtils.isNotEmpty(ZBDW_JSON)) {
            List<Map<String, Object>> zbdwlist = JSON.parseObject(ZBDW_JSON, List.class);
            if (null != zbdwlist && zbdwlist.size() > 0) {
                String BIDDINGNOTICENUMPROVINCE = zbdwlist.get(0).get("BIDDINGNOTICENUMPROVINCE") == null ? ""
                        : zbdwlist.get(0).get("BIDDINGNOTICENUMPROVINCE").toString();
                newMap.put("TENDERNUM", BIDDINGNOTICENUMPROVINCE);// 中标通知书编号（）
            }
        }

        String CHARTREVIEWNUM_JSON = map.get("CHARTREVIEWNUM_JSON") == null ? ""
                : map.get("CHARTREVIEWNUM_JSON").toString();
        StringBuffer CENSORNUM = new StringBuffer();
        if (StringUtils.isNotEmpty(CHARTREVIEWNUM_JSON)) {
            List<Map<String, Object>> clist = JSON.parseObject(CHARTREVIEWNUM_JSON, List.class);
            for (Map<String, Object> cMap : clist) {
                String CHARTREVIEWNUM = cMap.get("CHARTREVIEWNUM") == null ? "" : cMap.get("CHARTREVIEWNUM").toString();
                if (StringUtils.isNotEmpty(CHARTREVIEWNUM)) {
                    if (StringUtils.isNotEmpty(CENSORNUM)) {
                        CENSORNUM.append(",").append(CHARTREVIEWNUM);
                    } else {
                        CENSORNUM.append(CHARTREVIEWNUM);
                    }
                }
            }
        }
        newMap.put("CENSORNUM", CENSORNUM);// 施工图审查合格书编号（）

        newMap.put("BARGAINDAYS", map.get("WORKDAYS").toString().replaceAll("天", ""));// 合同工期
        newMap.put("CONTRACTMONEY", map.get("PROCOST"));// 合同金额(万元)
        newMap.put("AREA", map.get("PROAREA"));// 面积（平方米）
        newMap.put("LENGTH", map.get("MUNILENGTHS"));// 长度（米）
        newMap.put("SPAN", "");// 跨度（米）
        newMap.put("PRJSIZE", map.get("PRJSIZE"));// 建设规模

        newMap.put("RELEASEDATE", map.get("RELEASEDATE"));// 发证日期
        newMap.put("TWODIMCODE", "");// 证书二维码(无)

        newMap.put("CREATEDATE", map.get("SUBDATE"));// 记录登记时间

        String exeId = map.get("EXE_ID").toString();
        Map<String, Object> task = getTaskList(exeId);
        newMap.put("CHECKDEPARTNAME", task.get("ASSIGNER_DEPNAMES"));// 信息审核部门
        newMap.put("CHECKPERSONNAME", task.get("TEAM_NAMES"));// 信息审核人
        List<Map<String, Object>> taskList = getTaskAllList(exeId);
        // 质量监督信息
        List<Map<String, Object>> TBProjectQualityCheck = new ArrayList<Map<String, Object>>();
        getTBProjectQualityCheck(taskList, TBProjectQualityCheck, map);
        // 安全监督信息
        List<Map<String, Object>> TBProjectSafeCheck = new ArrayList<Map<String, Object>>();
        getTBProjectSafeCheck(taskList, TBProjectSafeCheck, map);

        newMap.put("TBPROJECTQUALITYCHECK", TBProjectQualityCheck);// 质量监督信息
        newMap.put("TBPROJECTSAFECHECK", TBProjectSafeCheck);// 安全监督信息
        newMap.put("DATASOURCE", 1);// 数据来源(业务办理)
        newMap.put("DATALEVEL", "B");// 数据等级(由县级住房和城乡建设主管部门审核确认)

        newList.add(newMap);
    }

    private void getTBProjectQualityCheck(List<Map<String, Object>> taskList,
            List<Map<String, Object>> TBProjectQualityCheck, Map<String, Object> sgxk) {
        Map<String, Object> newTaskMap = null;
        //int QUALITYNUM = 1;
        //int ZLJDNUM = 1;
        if(null != taskList && taskList.size() >0){
            Map<String, Object> map = taskList.get(0); //只取第一条数据
            newTaskMap = new HashMap<String, Object>();
            newTaskMap.put("YW_ID", map.get("TASK_ID"));

            newTaskMap.put("QUALITYNUM", sgxk.get("PRJNUM") + "-ZL-" + StringUtil.getFormatNumber(3, "1"));// 质量监督记录编号
            newTaskMap.put("PRJCODE", sgxk.get("PROJECTCODE"));// 项目代码
            newTaskMap.put("PRJNUM", sgxk.get("PRJNUM"));// 项目编号（报建编号）
            newTaskMap.put("PRJNAME", sgxk.get("PROJECT_NAME"));// 工程名称

            newTaskMap.put("QUALITYACCEPTDEPART", map.get("ASSIGNER_DEPNAMES"));// 质量报监受理部门
            newTaskMap.put("QUALITYACCEPTUSER", map.get("TEAM_NAMES"));// 质量报监受理人员
            newTaskMap.put("QUALITYACCEPTDATE", map.get("END_TIME"));// 质量报监受理时间
            newTaskMap.put("QUALITYACCEPTMARK",
                    map.get("HANDLE_OPINION") == null ? "无" : map.get("HANDLE_OPINION").toString());// 质量报监受理意见

            newTaskMap.put("ZLJDNUM", sgxk.get("PRJNUM") + "-ZB-" + StringUtil.getFormatNumber(3, "1"));// 质量报监编号
            newTaskMap.put("DATASOURCE", 1);// 数据来源(业务办理)
            TBProjectQualityCheck.add(newTaskMap);
        }
    }

    private void getTBProjectSafeCheck(List<Map<String, Object>> taskList, List<Map<String, Object>> TBProjectSafeCheck,
            Map<String, Object> sgxk) {
        Map<String, Object> newTaskMap = null;
        //int SAFENUM = 1;
        //int AQJDNUM = 1;
        if(null != taskList && taskList.size() >0){
            Map<String, Object> map = taskList.get(0);//只取第一条数据
            newTaskMap = new HashMap<String, Object>();
            newTaskMap.put("YW_ID", map.get("TASK_ID"));

            newTaskMap.put("SAFENUM", sgxk.get("PRJNUM") + "-AQ-" + StringUtil.getFormatNumber(3, "1"));// 质量监督记录编号
            newTaskMap.put("PRJCODE", sgxk.get("PROJECTCODE"));// 项目代码
            newTaskMap.put("PRJNUM", sgxk.get("PRJNUM"));// 项目编号（报建编号）
            newTaskMap.put("PRJNAME", sgxk.get("PROJECT_NAME"));// 工程名称

            newTaskMap.put("SAFEACCEPTDEPART", map.get("ASSIGNER_DEPNAMES"));// 质量报监受理部门
            newTaskMap.put("SAFEACCEPTUSER", map.get("TEAM_NAMES"));// 质量报监受理人员
            newTaskMap.put("SAFEACCEPTDATE", map.get("END_TIME"));// 质量报监受理时间
            newTaskMap.put("SAFEACCEPTMARK",
                    map.get("HANDLE_OPINION") == null ? "无" : map.get("HANDLE_OPINION").toString());// 质量报监受理意见

            newTaskMap.put("AQJDNUM", sgxk.get("PRJNUM") + "-AB-" + StringUtil.getFormatNumber(3, "1"));// 质量报监编号
            newTaskMap.put("DATASOURCE", 1);// 数据来源(业务办理)
            TBProjectSafeCheck.add(newTaskMap);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveTBBuilderLicenceManage(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findTBBuilderLicenceManage(null, 0);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveTBBuilderLicenceManage");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveTBBuilderLicenceManage返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_BSFW_GCJSSGXK", 1, "YW_ID", map.get("YW_ID").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveTBBuilderLicenceChangeInfo(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findTBBuilderLicenceChangeInfo(null, 0);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveTBBuilderLicenceChangeInfo");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveTBBuilderLicenceChangeInfo返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_BSFW_GCJSSGXKBG", 1, "YW_ID", map.get("YW_ID").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findTBBuilderLicenceChangeInfo(Map<String, Object> parentMap, int status) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.*,E.EXE_ID FROM T_BSFW_GCJSSGXKBG t,JBPM6_EXECUTION e ");
        sql.append(" where 1=1 AND T.CHANGEITEM IN(1,2)");
        sql.append(" and t.yw_id = e.bus_recordid and e.run_status=2 ");
        if (status >= 0) {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        if (null != parentMap) {
            String ywId = parentMap.get("YW_ID").toString();
            if (StringUtils.isNotEmpty(ywId)) {
                sql.append(" and t.YW_ID = ? ");
                param.add(ywId);
            }
        }
        sql.append("  order by t.CREATE_TIME asc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        Map<String, Object> newMap = null;
        for (Map<String, Object> map : list) {
            newMap = new HashMap<String, Object>();
            setSgxkbgList(newList, newMap, map);
        }
        return newList;
    }

    private void setSgxkbgList(List<Map<String, Object>> newList, Map<String, Object> newMap, Map<String, Object> map) {
        newMap.put("YW_ID", map.get("YW_ID"));// 主键ID
        newMap.put("BUILDERLICENCENUM", map.get("CONSTRNUM"));// 施工许可证编号
        newMap.put("BUIPRJNAME", map.get("PROJECT_NAME"));// 工程名称
        newMap.put("PRJCODE", map.get("PROJECTCODE"));// 项目代码
        newMap.put("PRJNUM", map.get("PRJNUM"));// 项目编号
        newMap.put("CHANGEITEM", map.get("CHANGEITEM"));// 变更项 1 项目经理 2项目总监
        StringBuffer beforeValue = new StringBuffer();
        beforeValue.append(map.get("PERSONNAME")).append(",");
        // beforeValue.append(map.get("IDCARDTYPENUM")).append(",");
        beforeValue.append(map.get("PERSONIDCARD")).append(",");
        beforeValue.append(map.get("PERSONPHONE"));
        StringBuffer afterValue = new StringBuffer();
        afterValue.append(map.get("PERSONNAME_AFTER")).append(",");
        // afterValue.append(map.get("IDCARDTYPENUM_AFTER")).append(",");
        afterValue.append(map.get("PERSONIDCARD_AFTER")).append(",");
        afterValue.append(map.get("PERSONPHONE_AFTER"));
        newMap.put("BEFOREVALUE", beforeValue);// 变更前的内容
        newMap.put("AFTERVALUE", afterValue);// 变更后的内容

        String exeId = map.get("EXE_ID").toString();
        Map<String, Object> task = getTaskList(exeId);
        newMap.put("CREATEDATE", task.get("END_TIME"));// 变更审核时间
        newMap.put("CHECKDEPARTNAME", task.get("ASSIGNER_DEPNAMES"));// 信息审核部门
        newMap.put("CHECKPERSONNAME", task.get("TEAM_NAMES"));// 信息审核人

        newList.add(newMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveTBBuilderLicenceCancelInfo(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findTBBuilderLicenceCancelInfo(null, 0);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveTBBuilderLicenceCancelInfo");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveTBBuilderLicenceCancelInfo返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("T_BSFW_GCJSSGXKFQZBL", 1, "YW_ID", map.get("YW_ID").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findTBBuilderLicenceCancelInfo(Map<String, Object> parentMap, int status) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.*,E.EXE_ID FROM T_BSFW_GCJSSGXKFQZBL t,JBPM6_EXECUTION e ");
        sql.append(" where 1=1 ");
        sql.append(" and t.yw_id = e.bus_recordid and e.run_status=2 ");
        if (status >= 0) {
            sql.append(" and t.push_status = ? ");
            param.add(status);
        }
        if (null != parentMap) {
            String ywId = parentMap.get("YW_ID").toString();
            if (StringUtils.isNotEmpty(ywId)) {
                sql.append(" and t.YW_ID = ? ");
                param.add(ywId);
            }
        }
        sql.append("  order by t.CREATE_TIME asc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        Map<String, Object> newMap = null;
        for (Map<String, Object> map : list) {
            newMap = new HashMap<String, Object>();
            setSgxkfqzblList(newList, newMap, map);
        }
        return newList;
    }

    private void setSgxkfqzblList(List<Map<String, Object>> newList, Map<String, Object> newMap,
            Map<String, Object> map) {
        newMap.put("YW_ID", map.get("YW_ID"));// 主键ID
        newMap.put("BUILDERLICENCENUM", map.get("CONSTRNUM"));// 施工许可证编号
        newMap.put("PRJCODE", map.get("PROJECTCODE"));// 项目代码
        newMap.put("PRJNUM", map.get("PRJNUM"));// 项目编号
        newMap.put("CANCELREASON", map.get("CANCELREASON"));// 废止理由

        String exeId = map.get("EXE_ID").toString();
        Map<String, Object> task = getTaskList(exeId);
        newMap.put("CANCELDATE", task.get("END_TIME"));// 废止审核时间
        newMap.put("CANCELDEPARTNAME", task.get("ASSIGNER_DEPNAMES"));// 废止审核部门
        newMap.put("CANCELPERSONNAME", task.get("TEAM_NAMES"));// 废止审核人

        newList.add(newMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findFlowResultList(int status, String exeId) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT R.RESULT_ID  AS DFSJZJ, 350128 AS XZQHDM, E.PROJECT_CODE AS GCDM, ");
        sql.append(" E.EXE_ID AS SPSXSLBM, R.EFFECT_TIME  AS PFRQ, R.XKFILE_NUM  AS PFWH, ");
        sql.append(" R.XKFILE_NAME  AS PFWJBT,R.CLOSE_TIME  AS PFWJYXQX,");
        sql.append(" R.RESULT_FILE_ID AS FJID FROM JBPM6_FLOW_RESULT R, JBPM6_EXECUTION E");
        sql.append(" WHERE R.EXE_ID = E.EXE_ID AND E.PROJECT_CODE IS NOT NULL ");
        sql.append(" AND E.RESULT_FILE_ID IS NOT NULL ");
        if (StringUtils.isNotEmpty(exeId)) {
            sql.append(" and R.EXE_ID = ? ");
            param.add(exeId);
        } else {
            if (status >= 0) {
                sql.append(" and R.push_status = ? ");
                param.add(status);
            }
        }
        sql.append(" ORDER BY R.EFFECT_TIME ASC");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        for (Map<String, Object> map : list) {
            String PFWJYXQX = map.get("PFWJYXQX") == null ? "" : map.get("PFWJYXQX").toString();
            if (StringUtils.isEmpty(PFWJYXQX)) {
                map.put("PFWJYXQX", "9999-01-01");
            }
            String FJID = map.get("FJID") == null ? "" : map.get("FJID").toString();
            String[] fjid = FJID.split(";");
            Map<String, Object> file = fileAttachService.getFileAttachObject(fjid[0]);
            if (null != file) {
                map.put("FJID", fjid[0]);
                map.put("FJMC", file.get("FILE_NAME"));
                String fileType = file.get("FILE_TYPE") == null ? "" : file.get("FILE_TYPE").toString();
                if (StringUtils.isNotEmpty(fileType)) {
                    map.put("FJLX", fileType);
                } else {
                    String filePath = file.get("FILE_PATH") == null ? "" : file.get("FILE_PATH").toString();
                    if (StringUtils.isNotEmpty(filePath)) {
                        map.put("FJLX", filePath.substring(filePath.lastIndexOf(".") + 1));
                    } else {
                        map.put("FJLX", "无");
                    }
                }
            } else {
                map.put("FJID", fjid[0]);
                map.put("FJMC", "无");
                map.put("FJLX", "无");
            }
            map.put("LSH", getLsh("12", "S_SPGL_XMSPSXPFWJXXB"));// 流水号;
            map.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            map.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            map.put("SJSCZT", 0);// 数据上传状态
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveXmspsxpfwjxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findFlowResultList(0, null);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmspsxpfwjxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmspsxpfwjxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("JBPM6_FLOW_RESULT", 1, "RESULT_ID", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> findFlowTaskList(int status, String id) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select T.TASK_ID AS DFSJZJ,350128 AS XZQHDM, ");
        sql.append(" E.PROJECT_CODE AS GCDM,T.EXE_ID AS SPSXSLBM, D.DEPART_CODE  AS BLDWDM,");
        sql.append("  (case D.DEPART_NAME when '平潭综合实验区' then  D.DEPART_NAME　else "
                + "'平潭综合实验区'|| D.DEPART_NAME end) AS BLDWMC,");
        sql.append(" T.END_TIME AS FKSJ, T.ASSIGNER_NAME AS BLR, ");
        sql.append(" (case when  T.HANDLE_OPINION is not null " + "then T.HANDLE_OPINION else '无' end)   AS FKYJ, ");
        sql.append(" '' as FJMC, '' as FJID FROM JBPM6_TASK T ");
        sql.append("  JOIN JBPM6_EXECUTION E ON T.EXE_ID = E.EXE_ID  ");
        sql.append(" JOIN T_MSJW_SYSTEM_SYSUSER U ON T.ASSIGNER_CODE = U.USERNAME ");
        sql.append(" JOIN T_MSJW_SYSTEM_DEPARTMENT D ON U.DEPART_ID = D.DEPART_ID ");
        sql.append(" where T.IS_REAL_HANDLED = 1 and E.PROJECT_CODE is not null ");
        sql.append(" AND T.TASK_NODENAME IN (select DIC.DIC_CODE "
                + " from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE = 'XMSPSXZQYJXXB') ");
        if (StringUtils.isNotEmpty(id)) {
            sql.append(" and T.TASK_ID = ? ");
            param.add(id);
        } else {
            if (status >= 0) {
                sql.append(" and t.push_status = ? ");
                param.add(status);
            }
        }
        sql.append("  order by t.exe_id asc,t.end_time asc");
        PagingBean pb = new PagingBean(0, 200);//新增每次最多推送200条
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), pb);
        for (Map<String, Object> map : list) {
            map.put("LSH", getLsh("10", "S_SPGL_XMSPSXZQYJXXB"));// 流水号;
            map.put("SJYXBS", 1);// 数据有效标识 0 无效 1 有效
            map.put("SJWXYY", "");// 数据无效原因 如果数据变为无效，需要填写无效原因
            map.put("SJSCZT", 0);// 数据上传状态
        }
        return list;
    }

    @Override
    public Map<String, Object> saveXmspsxzqyjxxb(String id) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = findFlowTaskList(0, null);
            Map<String, Object> params = new HashMap<String, Object>();
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            params.put("servicecode", "saveXmspsxzqyjxxb");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            params.put("grantcode", gcjsxmGrantCode);
            params.put("infoJson", JSON.toJSONString(list));
            result = TokenUtil.doPost(devbaseUrl, params);
            log.info("saveXmspsxzqyjxxb返回数据：" + result);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            if (null != data) {
                for (Map<String, Object> map : data) {
                    updatePushStatus("JBPM6_TASK", 1, "TASK_ID", map.get("DFSJZJ").toString());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> startWorkflow(Map<String, Object> info) {
        // TODO Auto-generated method stub
        log.info("-------向住建上报数据接口开始-------");
        Map<String, Object> map = null;
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headMap = new HashMap<>();
        Properties properties = FileUtil.readProperties("project.properties");
        String id = properties.getProperty("ZX_START_ID");
        params.put("id", id);
        params.put("parameters", JSON.toJSONString(info));
        String header = getZxAccessToken();
        headMap.put("Authorization", header);
        String url = properties.getProperty("ZX_STARTWORKFLOW_URL");
        log.info("请求参数：" + JSON.toJSONString(params));
        String result = HttpSendUtil.sendPostParamsH(url, headMap, params);
        log.info("返回数据：" + result);
        log.info("-------向住建上报数据接口结束-------");
        if (StringUtils.isNotEmpty(result)) {
            map = JSON.parseObject(result, Map.class);
            String code = map.get("code") == null ? "" : map.get("code").toString();
            if (StringUtils.isNotEmpty(code) && code.equals("1")) {// 状态码1为成功，其他为失败，如：70003
                                                                   // 无效的Token
                return map;
            } else {
                log.info("向住建上报数据调用失败，状态码（CODE）:" + code);
            }
        } else {
            log.info("向住建上报数据调用失败，返回值为空");
        }
        return map;
    }

    /**
     * 描述:获取省网总线accesstoken
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/12/13 13:03:00
     */
    private String getZxAccessToken() {
        Properties properties = FileUtil.readProperties("project.properties");
        String zxAccessTokenUrl = properties.getProperty("ZX_ACCESS_TOKEN_URL");
        String grant_type = properties.getProperty("ZX_GRANT_TYPE");
        String client_id = properties.getProperty("ZX_CLIENT_ID");
        String client_secret = properties.getProperty("ZX_CLIENT_SECRET");
        HashMap<String, Object> params = new HashMap<>();
        params.put("grant_type", grant_type);
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        String result = HttpSendUtil.sendPostParams(zxAccessTokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        String tokenType = jsonObject.getString("token_type");
        return tokenType + " " + accessToken;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void pushFlowinfo() {
        // TODO Auto-generated method stub
        // 获取施工许可信息
        List<Map<String, Object>> list = getSgxkList();
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        if (null != list && list.size() > 0) {
            // 工程项目单体信息
            Map<String, Object> gcxmdtxxData = pushGcxmdtxx(list);
            datas.add(gcxmdtxxData);
            // 施工许可信息
            Map<String, Object> sgxkxxData = pushSgxkxx(list);
            datas.add(sgxkxxData);
            // 工程项目参与单位及相关负责人信息
            Map<String, Object> gcxmdwjryData = pushGcxmdwjry(list);
            datas.add(gcxmdwjryData);
            // 质量监督信息
            Map<String, Object> zljdxxData = pushZljdxx(list);
            datas.add(zljdxxData);
            // 安全监督信息
            Map<String, Object> aqjdxxData = pushAqjdxx(list);
            datas.add(aqjdxxData);
            // 参建单位信息 2021年3月2日 09:28:57
            Map<String, Object> cjdwxxData = pushCjdwxx(list);
            datas.add(cjdwxxData);
            // 单体项目明细 2021年3月2日 11:37:21
            Map<String, Object> dtxmmxData = pushDtxmmx(list);
            datas.add(dtxmmxData);
            // 施工许可证照信息 2021年3月16日 15:27:00
            Map<String, Object> sgxkzzxxData = pushSgxkzzxx(list);
            datas.add(sgxkzzxxData);
            
        }
        // 获取施工许可变更信息
        List<Map<String, Object>> sgxkbglist = getSgxkbgList();
        if (null != sgxkbglist && sgxkbglist.size() > 0) {
            // 施工许可变更信息
            Map<String, Object> sgxkxxData = pushSgxkbgxx(sgxkbglist);
            datas.add(sgxkxxData);
        }

        if (null != datas && datas.size() > 0) {
            parameters.put("areaCode", "350128");
            parameters.put("datas", datas);
            Map<String, Object> resultMap = startWorkflow(parameters);

            String code = resultMap.get("code") == null ? "" : resultMap.get("code").toString();

            if (StringUtils.isNotEmpty(code) && code.equals("1")) {// 状态码1为成功，其他为失败，如：70003
                                                                   // 无效的Token
                //String result = resultMap.get("result") == null ? "" : resultMap.get("result").toString();
                //Map<String, Object> rMap = null;
                //if (StringUtils.isNotEmpty(result)) {
                    //rMap = JSON.parseObject(result, Map.class);
                    //String rcode = rMap.get("code") == null ? "" : rMap.get("code").toString();
                    //if (StringUtils.isNotEmpty(rcode) && rcode.equals("200")) {
                        Map<String, Object> sgxk = null;
                        for (Map<String, Object> map : list) {
                            String YW_ID = map.get("sourceid") == null ? "" : map.get("sourceid").toString();
                            sgxk = new HashMap<String, Object>();
                            sgxk.put("YW_ID", YW_ID);
                            sgxk.put("PUSHFLOW_STATUS", 1);
                            sgxk.put("PUSHFLOW_RESULT", JSON.toJSONString(resultMap));
                            dao.saveOrUpdate(sgxk, "T_BSFW_GCJSSGXK", YW_ID);
                        }
                        for (Map<String, Object> map : sgxkbglist) {
                            String YW_ID = map.get("sourceid") == null ? "" : map.get("sourceid").toString();
                            sgxk = new HashMap<String, Object>();
                            sgxk.put("YW_ID", YW_ID);
                            sgxk.put("PUSHFLOW_STATUS", 1);
                            sgxk.put("PUSHFLOW_RESULT", JSON.toJSONString(resultMap));
                            dao.saveOrUpdate(sgxk, "T_BSFW_GCJSSGXKBG", YW_ID);
                        }
                    //} else {
                    //    log.info(rMap.get("msg"));
                    //}
                //}
            }
        }
    }

    /**
     * 
     * 描述： 施工许可变更信息
     * 
     * @author Rider Chen
     * @created 2020年5月27日 下午5:02:35
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getSgxkbgList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.EXE_ID,g.* from JBPM6_EXECUTION t,T_BSFW_GCJSSGXKBG g ");
        sql.append(" where 1=1 AND g.CHANGEITEM IN(1,2) ");
        sql.append(" AND t.bus_recordid = g.yw_id and g.PUSHFLOW_STATUS = ? and t.run_status=? ");
        sql.append(" order by t.create_time asc");
        param.add(0);
        param.add(2);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        for (Map<String, Object> map : list) {
            String YW_ID = map.get("YW_ID") == null ? "" : map.get("YW_ID").toString();
            String exeId = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            Map<String, Object> task = getTaskList(exeId);
            String END_TIME = task.get("END_TIME") == null ? "" : task.get("END_TIME").toString();
            map.put("END_TIME", DateTimeUtil.formatDateStr(END_TIME, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"));
            map.put("ASSIGNER_DEPNAMES", task.get("ASSIGNER_DEPNAMES"));
            map.put("TEAM_NAMES", task.get("TEAM_NAMES"));

            StringBuffer beforeValue = new StringBuffer();
            beforeValue.append(map.get("PERSONNAME")).append(",");
            // beforeValue.append(map.get("IDCARDTYPENUM")).append(",");
            beforeValue.append(map.get("PERSONIDCARD")).append(",");
            beforeValue.append(map.get("PERSONPHONE"));
            StringBuffer afterValue = new StringBuffer();
            afterValue.append(map.get("PERSONNAME_AFTER")).append(",");
            // afterValue.append(map.get("IDCARDTYPENUM_AFTER")).append(",");
            afterValue.append(map.get("PERSONIDCARD_AFTER")).append(",");
            afterValue.append(map.get("PERSONPHONE_AFTER"));
            map.put("BEFOREVALUE", beforeValue);// 变更前的内容
            map.put("AFTERVALUE", afterValue);// 变更后的内容
            for (Map.Entry<String, Object> a : map.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();
                if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                    map.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                }
            }
            map.put("sourceid", YW_ID);
        }
        return list;
    }
    /**
     * 
     * 描述：获取施工许可信息
     * 
     * @author Rider Chen
     * @created 2020年5月8日 上午10:34:31
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getSgxkList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.EXE_ID,g.* from JBPM6_EXECUTION t,T_BSFW_GCJSSGXK g ");
        sql.append(" where t.bus_recordid = g.yw_id and g.PUSHFLOW_STATUS = ? and t.run_status=? ");
        sql.append(" order by t.create_time asc");
        param.add(0);
        param.add(2);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        int unitcodeNum = 1;
        for (Map<String, Object> map : list) {
            String PRJNUM = map.get("PRJNUM") == null ? "" : map.get("PRJNUM").toString();
            String YW_ID = map.get("YW_ID") == null ? "" : map.get("YW_ID").toString();
            String exeId = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            String CERTIFICATEIDENTIFIERFILEPATH = map.get("CERTIFICATEIDENTIFIERFILEPATH") == null ? ""
                    : map.get("CERTIFICATEIDENTIFIERFILEPATH").toString();
            String CREATE_TIME = map.get("CREATE_TIME") == null ? "" : map.get("CREATE_TIME").toString();
            map.put("CREATE_TIME",
                    DateTimeUtil.formatDateStr(CREATE_TIME, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"));
            String WORKDAYS = map.get("WORKDAYS") == null ? "" : map.get("WORKDAYS").toString();// 合同工期
            map.put("WORKDAYS", WORKDAYS.replaceAll("天", ""));
            Map<String, Object> task = getTaskList(exeId);
            // 初始化质量监督信息与安全监督信息信息
            initQualityAndSafe(map, exeId);
            //初始化施工许可证照信息
            setSgxkCertificate(map, CERTIFICATEIDENTIFIERFILEPATH);
            
            map.put("ASSIGNER_DEPNAMES", task.get("ASSIGNER_DEPNAMES"));
            map.put("TEAM_NAMES", task.get("TEAM_NAMES"));
            String dwgc = map.get("DWGC_JSON") == null ? "" : map.get("DWGC_JSON").toString();//单位工程JSON
            String zjgc = map.get("ZJGC_JSON") == null ? "" : map.get("ZJGC_JSON").toString();//桩基工程JSON
            String sbgc = map.get("SBGC_JSON") == null ? "" : map.get("SBGC_JSON").toString();//上部工程JSON
            for (Map.Entry<String, Object> a : map.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();
                if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                    map.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                }
            }
            List<Map<String, Object>> dwgcList = new ArrayList<Map<String,Object>>();
            if (StringUtils.isNotEmpty(dwgc)) {
                List<Map<String, Object>> gcList = JSON.parseObject(dwgc, List.class);
                unitcodeNum = setDwgcList(unitcodeNum, PRJNUM, YW_ID, dwgcList, gcList,"DWGC");
            }
            if (StringUtils.isNotEmpty(zjgc)) {
                List<Map<String, Object>> gcList = JSON.parseObject(zjgc, List.class);
                unitcodeNum = setDwgcList(unitcodeNum, PRJNUM, YW_ID, dwgcList, gcList,"ZJGC");
            }

            if (StringUtils.isNotEmpty(sbgc)) {
                List<Map<String, Object>> gcList = JSON.parseObject(sbgc, List.class);
                unitcodeNum = setDwgcList(unitcodeNum, PRJNUM, YW_ID, dwgcList, gcList,"SBGC");
            }
            map.put("dwgcList", dwgcList);
            
            // 施工图审查合格证书编号
            String CHARTREVIEWNUM_JSON = map.get("CHARTREVIEWNUM_JSON") == null ? ""
                    : map.get("CHARTREVIEWNUM_JSON").toString();
            if (StringUtils.isNotEmpty(CHARTREVIEWNUM_JSON)) {
                List<Map<String, Object>> chartreviewnumList = JSON.parseObject(CHARTREVIEWNUM_JSON, List.class);
                StringBuffer censornum = new StringBuffer();
                for (Map<String, Object> map2 : chartreviewnumList) {
                    censornum.append(map2.get("CHARTREVIEWNUM")).append(",");
                }
                map.put("CENSORNUM", "<![CDATA[" + censornum.substring(0, censornum.length() - 1) + "]]>");
            }

            List<Map<String, Object>> dwryList = new ArrayList<Map<String, Object>>();
            unitcodeNum = getDwryList(map, dwryList, "JSDW", "99", unitcodeNum, PRJNUM, YW_ID);// 建设单位
            unitcodeNum = getDwryList(map, dwryList, "DJDW", "5", unitcodeNum, PRJNUM, YW_ID);// 代建、工程总承包（EPC）、PPP等单位
            unitcodeNum = getDwryList(map, dwryList, "SGDW", "3", unitcodeNum, PRJNUM, YW_ID);// 施工单位
            unitcodeNum = getDwryList(map, dwryList, "JLDW", "4", unitcodeNum, PRJNUM, YW_ID);// 监理单位
            unitcodeNum = getDwryList(map, dwryList, "KCDW", "1", unitcodeNum, PRJNUM, YW_ID);// 勘察单位
            unitcodeNum = getDwryList(map, dwryList, "SJDW", "2", unitcodeNum, PRJNUM, YW_ID);// 设计单位
            unitcodeNum = getDwryList(map, dwryList, "SGTSCDW", "99", 0, PRJNUM, YW_ID);// 施工图审查单位
            unitcodeNum = getDwryList(map, dwryList, "KZJDW", "99", unitcodeNum, PRJNUM, YW_ID);// 控制价（预算价）计价文件编制单位
            unitcodeNum = getDwryList(map, dwryList, "JCDW", "6", unitcodeNum, PRJNUM, YW_ID);// 检测单位
            unitcodeNum = getDwryList(map, dwryList, "ZBDW", "99", unitcodeNum, PRJNUM, YW_ID);// 招标代理单位
            map.put("dwryList", dwryList);
            List<Map<String, Object>> cjdwList = new ArrayList<Map<String, Object>>();
            unitcodeNum = getDwryList(map, cjdwList, "DJDW", "5", unitcodeNum, PRJNUM, YW_ID);// 代建、工程总承包（EPC）、PPP等单位
            unitcodeNum = getDwryList(map, cjdwList, "SGDW", "3", unitcodeNum, PRJNUM, YW_ID);// 施工单位
            unitcodeNum = getDwryList(map, cjdwList, "JLDW", "4", unitcodeNum, PRJNUM, YW_ID);// 监理单位
            unitcodeNum = getDwryList(map, cjdwList, "KCDW", "1", unitcodeNum, PRJNUM, YW_ID);// 勘察单位
            unitcodeNum = getDwryList(map, cjdwList, "SJDW", "2", unitcodeNum, PRJNUM, YW_ID);// 设计单位
            map.put("cjdwList", cjdwList);
            map.put("sourceid", YW_ID);

            
        }
        return list;
    }

    /**
     * 
     * 描述 设置施工许可证照相关信息
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午3:22:12
     * @param map
     * @param CERTIFICATEIDENTIFIERFILEPATH
     */
    @SuppressWarnings("unchecked")
    private void setSgxkCertificate(Map<String, Object> map, String CERTIFICATEIDENTIFIERFILEPATH) {
        String JSDW_JSON = map.get("JSDW_JSON") == null ? "" : map.get("JSDW_JSON").toString();
        if (StringUtils.isNotEmpty(JSDW_JSON) && JSDW_JSON.indexOf("<!") == -1) {
            List<Map<String, Object>> dwList = JSON.parseObject(JSDW_JSON, List.class);
            StringBuffer projowner = new StringBuffer();
            StringBuffer projownercode = new StringBuffer();
            StringBuffer ownermngr = new StringBuffer();
            for (Map<String, Object> map2 : dwList) {
                projowner.append(map2.get("CORPNAME")).append(",");
                projownercode.append(map2.get("CORPCREDITCODE")).append(",");
                ownermngr.append(map2.get("PERSONNAME")).append(",");
            }
            map.put("PROJOWNER", projowner.deleteCharAt(projowner.length() - 1));
            map.put("PROJOWNERCODE", projownercode.deleteCharAt(projownercode.length() - 1));
            map.put("OWNERMNGR", ownermngr.deleteCharAt(ownermngr.length() - 1));
        }
        if (StringUtils.isNotEmpty(CERTIFICATEIDENTIFIERFILEPATH)) {

            Properties projectProperties = FileUtil.readProperties("project.properties");
            String fileServerIn = projectProperties.getProperty("uploadFileUrlIn");
            String fileServer = projectProperties.getProperty("uploadFileUrl");
            map.put("FILEURL", fileServer + CERTIFICATEIDENTIFIERFILEPATH);
            try {
                InputStream is = getStreamDownloadOutFile(fileServerIn + CERTIFICATEIDENTIFIERFILEPATH);
                String fileContent = StringUtil.byte2hex(FileUtil.convertUrlFileToBytes(is));
                map.put("FILECONTENT", fileContent);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("", e);
            }
        }
        String CERTIFICATEIDENTIFIER = map.get("CERTIFICATEIDENTIFIER") == null ? ""
                : map.get("CERTIFICATEIDENTIFIER").toString();
        if (StringUtils.isEmpty(CERTIFICATEIDENTIFIER)) {
            map.remove("CERTIFICATEIDENTIFIER");
        }
    }
    /**
     * 
     * 描述： 下载文件，返回输入流。
     * 
     * @author Rider Chen
     * @created 2019年6月5日 10:15:36
     * @param apiUrl
     * @return
     * @throws Exception
     */
    public static InputStream getStreamDownloadOutFile(String apiUrl) throws Exception {
        InputStream is = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();// 创建默认http客户端
        RequestConfig requestConfig = RequestConfig.DEFAULT;// 采用默认请求配置
        HttpGet request = new HttpGet(apiUrl);// 通过get方法下载文件流
        request.setConfig(requestConfig);// 设置请头求配置
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(request);// 执行请求，接收返回信息
            int statusCode = httpResponse.getStatusLine().getStatusCode();// 获取执行状态
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
                request.abort();
            } else {
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    is = entity.getContent();// 获取返回内容
                }
            }
        } catch (Exception e) {
            log.error("远程下载文件异常",e);
            request.abort();
        }
        return is;
    }

    /**
     * 
     * 描述 设置单位工程信息
     * 
     * @author Rider Chen
     * @created 2021年3月3日 上午9:39:52
     * @param unitcodeNum
     * @param PRJNUM
     * @param YW_ID
     * @param dwgcList
     * @param gcList
     * @return
     */
    @SuppressWarnings("unchecked")
    private int setDwgcList(int unitcodeNum, String PRJNUM, String YW_ID, List<Map<String, Object>> dwgcList,
            List<Map<String, Object>> gcList, String name) {
        int pkidNum = 1;
        List<Map<String, Object>> newGcList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map2 : gcList) {
            map2.put("UNITCODE", PRJNUM + "-" + StringUtil.getFormatNumber(3, unitcodeNum + ""));
            String ARCHAREA = map2.get("ARCHAREA") == null || StringUtils.isEmpty(map2.get("ARCHAREA").toString()) ? "0"
                    : map2.get("ARCHAREA").toString();
            String STRUCTUPFLOORAREA_DWGC = map2.get("STRUCTUPFLOORAREA_DWGC") == null
                    || StringUtils.isEmpty(map2.get("STRUCTUPFLOORAREA_DWGC").toString()) ? "0"
                            : map2.get("STRUCTUPFLOORAREA_DWGC").toString();
            String STRUCTDWFLOORAREA_DWGC = map2.get("STRUCTDWFLOORAREA_DWGC") == null
                    || StringUtils.isEmpty(map2.get("STRUCTDWFLOORAREA_DWGC").toString()) ? "0"
                            : map2.get("STRUCTDWFLOORAREA_DWGC").toString();
            String STRUCTUPFLOORNUM = map2.get("STRUCTUPFLOORNUM") == null
                    || StringUtils.isEmpty(map2.get("STRUCTUPFLOORNUM").toString()) ? "0"
                            : map2.get("STRUCTUPFLOORNUM").toString();
            String STRUCTDWFLOORNUM = map2.get("STRUCTDWFLOORNUM") == null
                    || StringUtils.isEmpty(map2.get("STRUCTDWFLOORNUM").toString()) ? "0"
                            : map2.get("STRUCTDWFLOORNUM").toString();
            String CHILDREN_JSON = map2.get("CHILDREN" + name + "_JSON") == null ? ""
                    : map2.get("CHILDREN" + name + "_JSON").toString();
            String STRUCTQUALTYPE = map2.get("STRUCTQUALTYPE") == null ? "" : map2.get("STRUCTQUALTYPE").toString();
            map2.put("STRUCTQUALTYPE", getFormatStructqualtype(STRUCTQUALTYPE));
            map2.put("ARCHAREA", ARCHAREA);
            map2.put("STRUCTUPFLOORAREA_DWGC", STRUCTUPFLOORAREA_DWGC);
            map2.put("STRUCTDWFLOORAREA_DWGC", STRUCTDWFLOORAREA_DWGC);
            map2.put("STRUCTUPFLOORNUM", STRUCTUPFLOORNUM);
            map2.put("STRUCTDWFLOORNUM", STRUCTDWFLOORNUM);
            for (Map.Entry<String, Object> a : map2.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();
                if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                    map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                }
            }
            map2.put("pkid", "<![CDATA[" + YW_ID + "-" + name + "-" + pkidNum + "]]>");
            unitcodeNum++;
            pkidNum++;
            newGcList.add(map2);
            if (StringUtils.isNotEmpty(CHILDREN_JSON)) {// 子单位工程
                List<Map<String, Object>> childrenList = JSON.parseObject(CHILDREN_JSON, List.class);
                for (Map<String, Object> children : childrenList) {
                    children.put("UNITCODE", PRJNUM + "-" + StringUtil.getFormatNumber(3, unitcodeNum + ""));
                    ARCHAREA = children.get("ARCHAREA") == null
                            || StringUtils.isEmpty(children.get("ARCHAREA").toString()) ? "0"
                                    : children.get("ARCHAREA").toString();
                    STRUCTUPFLOORAREA_DWGC = children.get("STRUCTUPFLOORAREA_DWGC") == null
                            || StringUtils.isEmpty(children.get("STRUCTUPFLOORAREA_DWGC").toString()) ? "0"
                                    : children.get("STRUCTUPFLOORAREA_DWGC").toString();
                    STRUCTDWFLOORAREA_DWGC = children.get("STRUCTDWFLOORAREA_DWGC") == null
                            || StringUtils.isEmpty(children.get("STRUCTDWFLOORAREA_DWGC").toString()) ? "0"
                                    : children.get("STRUCTDWFLOORAREA_DWGC").toString(); 
                    STRUCTUPFLOORNUM = children.get("STRUCTUPFLOORNUM") == null
                            || StringUtils.isEmpty(children.get("STRUCTUPFLOORNUM").toString()) ? "0"
                                    : children.get("STRUCTUPFLOORNUM").toString();
                    STRUCTDWFLOORNUM = children.get("STRUCTDWFLOORNUM") == null
                            || StringUtils.isEmpty(children.get("STRUCTDWFLOORNUM").toString()) ? "0"
                                    : children.get("STRUCTDWFLOORNUM").toString();
                    STRUCTQUALTYPE = children.get("STRUCTQUALTYPE") == null ? "" : children.get("STRUCTQUALTYPE").toString();
                    children.put("STRUCTQUALTYPE", getFormatStructqualtype(STRUCTQUALTYPE));
                    children.put("ARCHAREA", ARCHAREA);
                    children.put("STRUCTUPFLOORAREA_DWGC", STRUCTUPFLOORAREA_DWGC);
                    children.put("STRUCTDWFLOORAREA_DWGC", STRUCTDWFLOORAREA_DWGC);
                    children.put("STRUCTUPFLOORNUM", STRUCTUPFLOORNUM);
                    children.put("STRUCTDWFLOORNUM", STRUCTDWFLOORNUM);
                    for (Map.Entry<String, Object> a : children.entrySet()) {
                        String value = a.getValue() == null ? "" : a.getValue().toString();
                        if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                            children.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                        }
                    }
                    children.put("pkid", "<![CDATA[" + YW_ID + "-CHILDREN-" + name + "-" + pkidNum + "]]>");
                    unitcodeNum++;
                    pkidNum++;
                    newGcList.add(children);
                }
            }
        }
        dwgcList.addAll(newGcList);
        return unitcodeNum;
    }

    private void initQualityAndSafe(Map<String, Object> map, String exeId) {
        List<Map<String, Object>> taskList = getTaskAllList(exeId);
        // 质量监督信息
        List<Map<String, Object>> TBProjectQualityCheck = new ArrayList<Map<String, Object>>();
        getTBProjectQualityCheck(taskList, TBProjectQualityCheck, map);
        for (Map<String, Object> map2 : TBProjectQualityCheck) {
            for (Map.Entry<String, Object> a : map2.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();

                map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
            }
        }
        map.put("zljdxxList", TBProjectQualityCheck);
        // 安全监督信息
        List<Map<String, Object>> TBProjectSafeCheck = new ArrayList<Map<String, Object>>();
        getTBProjectSafeCheck(taskList, TBProjectSafeCheck, map);
        for (Map<String, Object> map2 : TBProjectSafeCheck) {
            for (Map.Entry<String, Object> a : map2.entrySet()) {
                String value = a.getValue() == null ? "" : a.getValue().toString();
                map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
            }
        }
        map.put("aqjdxxList", TBProjectSafeCheck);
    }
    /**
     * 格式化结构体系
     */
    public static String getFormatStructqualtype(String STRUCTQUALTYPE) {
        StringBuffer jgtx = new StringBuffer();
        if (StringUtils.isNotEmpty(STRUCTQUALTYPE)) {
            String[] types = STRUCTQUALTYPE.split(",");
            //for (String type : types) {
             jgtx.append(getFormatType(types[0])).append(",");
            //}
            return jgtx.deleteCharAt(jgtx.length() - 1).toString();
        }
        return "";
    }

    private static String getFormatType(String type) {
        String jgtx = "099";
        switch (type) {
            case "框架":
                jgtx = "003";
                break;
            case "剪力墙":
                jgtx = "005";
                break;
            case "框剪":
                jgtx = "004";
                break;
            case "筒中筒":
                jgtx = "010";
                break;
            case "钢结构":
                jgtx = "014";
                break;
            case "排架":
                jgtx = "015";
                break;
            case "砖混":
                jgtx = "001";
                break;
            case "底框":
                jgtx = "002";
                break;
            case "板柱剪力墙":
                jgtx = "006";
                break;
            case "短肢剪力墙":
                jgtx = "007";
                break;
            case "部分框支剪力墙":
                jgtx = "008";
                break;
            case "异型柱框架":
                jgtx = "011";
                break;
            case "复杂高层结构":
                jgtx = "012";
                break;
            case "混合结构":
                jgtx = "013";
                break;
            case "框筒":
                jgtx = "009";
                break;
            default:
                jgtx = "099";
        }
        return jgtx;
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
                    map2.put("CONTRACTNUMBER", CONTRACTNUMBER);
                    for (Map.Entry<String, Object> a : map2.entrySet()) {
                        String value = a.getValue() == null ? "" : a.getValue().toString();
                        if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                            map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                        }
                    }
                    map2.put("pkid", "<![CDATA[" + YW_ID + "-" + key + "-" + pkidNum + "]]>");
                    map2.put("IDCARDTYPENUM", "<![CDATA[身份证]]>");                    
                    dwryList.add(map2);
                    unitcodeNum++;
                    pkidNum++;
                }
            } else {
                Map<String, Object> map2 = JSON.parseObject(json, Map.class);
                map2.put("CORPROLENUM", CORPROLENUM);
                map2.put("UNITCODE", PRJNUM + "-" + StringUtil.getFormatNumber(3, unitcodeNum + ""));
                String CONTRACTNUMBER = map.get("CONTRACTNUMBER") == null ? "" : map.get("CONTRACTNUMBER").toString();
                map2.put("CONTRACTNUMBER", CONTRACTNUMBER);
                for (Map.Entry<String, Object> a : map2.entrySet()) {
                    String value = a.getValue() == null ? "" : a.getValue().toString();
                    if (value.indexOf("[") == -1 && value.indexOf("{") == -1) {
                        map2.put(a.getKey(), "<![CDATA[" + value.trim() + "]]>");
                    }
                }
                map2.put("pkid", "<![CDATA[" + YW_ID + "-" + key + "-" + pkidNum + "]]>");
                map2.put("IDCARDTYPENUM", "<![CDATA[身份证]]>");
                dwryList.add(map2);
                unitcodeNum++;
                pkidNum++;
            }
        }
        return unitcodeNum;
    }
    /**
     * 
     * 描述 施工许可证照信息
     * @author Rider Chen
     * @created 2021年3月16日 下午3:24:53
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushSgxkzzxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { "WEB4686" });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", "WEB4686");
        info.put("xmlstr", xmlBuffer);         
        return info;
    }
    /**
     * 
     * 描述 单体项目明细
     * @author Rider Chen
     * @created 2021年3月2日 上午9:51:45
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushDtxmmx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { "WEB4688" });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", "WEB4688");
        info.put("xmlstr", xmlBuffer);
        return info;
    }
    /**
     * 
     * 描述 参建单位信息
     * @author Rider Chen
     * @created 2021年3月2日 上午9:25:34
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushCjdwxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { "WEB4687" });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", "WEB4687");
        info.put("xmlstr", xmlBuffer);
        return info;
    }
    /**
     * 
     * 描述： 工程项目单体信息
     * 
     * @author Rider Chen
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushGcxmdtxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_GCXMDWXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_GCXMDWXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }

    /**
     * 
     * 描述： 施工许可信息
     * 
     * @author Rider Chen
     * @created 2020年5月7日 下午5:27:09
     * @param list
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushSgxkxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_SGXKXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_SGXKXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }

    /**
     * 
     * 描述： 施工许可变更信息
     * 
     * @author Rider Chen
     * @created 2020年5月27日 下午5:06:59
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushSgxkbgxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_SGXKBGXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkbgList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_SGXKBGXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }

    /**
     * 
     * 描述：质量监督信息
     * 
     * @author Rider Chen
     * @created 2020年6月16日 下午2:53:33
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushZljdxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_ZLJDXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_ZLJDXX);
        info.put("xmlstr", xmlBuffer);
        return info;
    }

    /**
     * 
     * 描述：安全监督信息
     * 
     * @author Rider Chen
     * @created 2020年6月16日 下午2:55:14
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> pushAqjdxx(List<Map<String, Object>> list) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZJSXSB_AQJDXX });
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("sgxkList", list);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("catalogid", AllConstant.INTER_CODE_ZJSXSB_AQJDXX);
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
                new String[] { AllConstant.INTER_CODE_ZJSXSB_GCXMDWXXJXGFZRXX });
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

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getTaskAllList(String exeId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        // 是否流程归档明细查看
        sql.append(" select T.* FROM JBPM6_TASK T ");
        sql.append(" where 1=1  and T.STEP_SEQ !=0 and t.task_nodename='审批' ");
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
        return list;
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
     * 
     * @Description 根据事项编码获取列表
     * @author Luffy Cai
     * @date 2020年11月6日
     * @param projectCode
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findListByProjectCode(String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select T.* FROM JBPM6_EXECUTION T ");
        sql.append(" where 1=1  and T.PROJECT_CODE=? and T.RUN_STATUS NOT IN(-1,0) ");
        params.add(projectCode);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }    
    
}
