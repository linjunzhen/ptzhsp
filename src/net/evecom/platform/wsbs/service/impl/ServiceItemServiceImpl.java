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
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysResService;
import net.evecom.platform.wsbs.dao.ServiceItemDao;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 描述 服务事项操作service
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("serviceItemService")
public class ServiceItemServiceImpl extends BaseServiceImpl implements
        ServiceItemService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ServiceItemServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ServiceItemDao dao;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 
     */
    @Resource
    private DataAbutLogService dataAbutLogService;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private SysResService sysResService;
    /**
     *
     */
    @Resource
    private ApplyMaterService applyMaterService;


    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND T.SSBMBM in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 根据项目编码判断是否存在记录
     * 
     * @author Flex Hu
     * @created 2015年9月22日 上午11:28:45
     * @param itemCode
     * @return
     */
    public boolean isExists(String itemCode) {
        return dao.isExists(itemCode);
    }

    /**
     * 
     * 描述 更新或者保存服务项目
     * 
     * @author Flex Hu
     * @created 2015年9月22日 下午3:02:14
     * @param serviceItem
     * @return
     */
    public String saveOrUpdateCascade(Map<String, Object> serviceItem) {
        String entityId = (String) serviceItem.get("ITEM_ID");
        String itemId = dao.saveOrUpdate(serviceItem, "T_WSBS_SERVICEITEM",
                entityId);
        // 获取勾选主题类别IDS
        String typeIds = (String) serviceItem.get("BUS_TYPEIDS");
        if (StringUtils.isNotEmpty(typeIds)) {
            busTypeService.saveBusTypeItem(typeIds.split(","), itemId);
        }
        return itemId;
    }

    /**
     * 
     * 描述 保存办事项目用户中间表
     * 
     * @author Flex Hu
     * @created 2015年9月24日 下午5:48:57
     * @param itemId
     * @param userIds
     */
    public void saveItemUsers(String itemId, String userIds) {
        // 先删除掉中间表数据
        dao.remove("T_WSBS_PREAUDITER", "ITEM_ID", new String[] { itemId });
        dao.saveItemUsers(itemId, userIds);
    }

    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * 
     * @author Flex Hu
     * @created 2015年9月24日 下午5:57:47
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindUserIdANdNames(String itemId) {
        return dao.getBindUserIdANdNames(itemId);
    }

    /**
     * 
     * 描述 从省网办同步办事项目数据
     * 
     * @author Flex Hu
     * @created 2015年10月12日 下午5:02:07
     * @param params
     */
    public void saveDatasFromProvince(Map<String, Object> dataAbutment) {
        String dbUrl = (String) dataAbutment.get("DB_URL");
        String dbUserName = (String) dataAbutment.get("DB_USERNAME");
        String dbPass = (String) dataAbutment.get("DB_PASSWORD");
        Connection conn = null;
        try {
            conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
            StringBuffer sql = new StringBuffer("select * from PROVINCEINFO P");
            sql.append(" WHERE P.TYPE=? ORDER BY P.CREATETIME ASC");
            List<Map<String, Object>> list = DbUtil.findBySql(conn,
                    sql.toString(),
                    new Object[] { AllConstant.SWB_DATA_TYPE_SX });
            for (Map<String, Object> map : list) {
                // 获取业务标识值
                String busIdValue = (String) map.get("UNID");
                // 获取操作动作
                String action = (String) map.get("ACTION");
                // 获取服务事项XML
                String itemXml = (String) map.get("CONTENT");
                Map<String, Object> dataAbutLog = new HashMap<String, Object>();
                dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
                dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
                dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(
                        new Date(), "yyyy-MM-dd HH:mm:ss"));
                String abutDesc = "";
                if (action.equals("I")) {
                    abutDesc = "新增了服务事项信息";
                } else if (action.equals("U")) {
                    abutDesc = "更新了服务事项信息";
                } else if (action.equals("D")) {
                    abutDesc = "删除了服务事项信息";
                }
                dataAbutLog.put("ABUT_DESC", abutDesc);
                dataAbutLog.put("BUS_IDVALUE", busIdValue);
                dataAbutLog.put("ABUT_RECEDATA", itemXml);
                try {
                    this.saveOrUpdateFromProvinceXml(itemXml);
                    dataAbutLog.put("ABUT_RESULT", "1");
                    dataAbutLogService.saveOrUpdateLog(dataAbutLog);
                } catch (Exception e) {
                    dataAbutLog.put("ERROR_LOG", e.getMessage());
                    dataAbutLog.put("ABUT_RESULT", "-1");
                    dataAbutLogService.saveOrUpdateLog(dataAbutLog);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            Map<String, Object> dataAbutLog = new HashMap<String, Object>();
            dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
            dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
            dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            dataAbutLog.put("ABUT_DESC", "对接数据失败");
            dataAbutLog.put("ABUT_RESULT", "-1");
            dataAbutLog.put("ERROR_LOG", e.getMessage());
            dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG",
                    null);
        } finally {
            if (conn != null) {
                try {
                    DbUtils.close(conn);
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 
     * 描述 根据事项编码获取事项ID
     * 
     * @author Flex Hu
     * @created 2015年10月19日 下午4:06:32
     * @param itemCode
     * @return
     */
    public String getItemId(String itemCode) {
        StringBuffer sql = new StringBuffer(
                "SELECT W.ITEM_ID FROM T_WSBS_SERVICEITEM W");
        sql.append(" WHERE W.ITEM_CODE=? ");
        return (String) this.getObjectBySql(sql.toString(),
                new Object[] { itemCode });
    }

    /**
     * 
     * 描述 根据省网办XML新增或者更新数据
     * 
     * @author Flex Hu
     * @created 2015年10月16日 上午10:42:03
     * @param xmlInfo
     */
    public String saveOrUpdateFromProvinceXml(String xmlInfo) {
        Document document = XmlUtil.stringToDocument(xmlInfo);
        Element root = document.getRootElement();
        // 定义服务事项
        Map<String, Object> serviceItem = new HashMap<String, Object>();
        String itemCode = root.selectSingleNode("//ServiceInfo/SrcCode").getText();
        // 设置服务事项编码
        serviceItem.put("ITEM_CODE", itemCode);
        // 设置服务事项名称
        serviceItem.put("ITEM_NAME",
                root.selectSingleNode("//ServiceInfo/ServiceName").getText());
        // 设置服务事项性质
        serviceItem.put("SXXZ", root.selectSingleNode("//ServiceInfo/AddType").getText());
        // 获取办件类型
        String serviceType = root.selectSingleNode("//ServiceInfo/ServiceType").getText();
        if (StringUtils.isNotEmpty(serviceType)) {
            serviceItem.put("SXLX", dictionaryService.getDicCode(
                    "ServiceItemType", serviceType));
        }
        // 获取事项备注说明
        String iremark = root.selectSingleNode("//ServiceInfo/Iremark").getText();
        if (StringUtils.isNotEmpty(iremark)) {
            serviceItem.put("BZSM", iremark);
        }
        // 获取行使依据
        String xsyj = root.selectSingleNode("//ServiceInfo/According").getText();
        if (StringUtils.isNotEmpty(xsyj)) {
            serviceItem.put("XSYJ", xsyj);
        }
        // 获取申请条件
        String sqtj = root.selectSingleNode("//ServiceInfo/Applyterm").getText();
        if (StringUtils.isNotEmpty(sqtj)) {
            serviceItem.put("SQTJ", sqtj);
        }
        // 获取办理流程
        String bllc = root.selectSingleNode("//ServiceInfo/Proceflow").getText();
        if (StringUtils.isNotEmpty(bllc)) {
            serviceItem.put("BLLC", bllc);
        }
        // 获取承诺时限工作日
        String promiseDay = root.selectSingleNode("//ServiceInfo/PromiseDay").getText();
        if (StringUtils.isNotEmpty(promiseDay)) {
            serviceItem.put("CNQXGZR", Integer.parseInt(promiseDay));
        }
        // 获取承诺时限说明
        String promisDayInfo = root.selectSingleNode(
                "//ServiceInfo/PromisDayInfo").getText();
        if (StringUtils.isNotEmpty(promisDayInfo)) {
            serviceItem.put("CNQXSM", promisDayInfo);
        }
        // 获取法定时限说明
        String fdqx = root.selectSingleNode("//ServiceInfo/Ifdsx").getText();
        if (StringUtils.isNotEmpty(fdqx)) {
            serviceItem.put("FDQX", fdqx);
        }
        // 获取办理地点
        String acceptAddress = root.selectSingleNode(
                "//ServiceInfo/AcceptAddress").getText();
        if (StringUtils.isNotEmpty(acceptAddress)) {
            serviceItem.put("BLDD", acceptAddress);
        }
        // 获取办理部门
        String leadDept = root.selectSingleNode("//ServiceInfo/LeadDept").getText();
        if (StringUtils.isNotEmpty(leadDept)) {
            serviceItem.put("BLBM", leadDept);
        }
        // 获取办公时间
        String officeTime = root.selectSingleNode("//ServiceInfo/OfficeTime")
                .getText();
        if (StringUtils.isNotEmpty(officeTime)) {
            serviceItem.put("BGSJ", officeTime);
        }
        // 获取实施主体名称
        String ssztmc = root.selectSingleNode("//ServiceInfo/DeptName").getText();
        if (StringUtils.isNotEmpty(ssztmc)) {
            serviceItem.put("SSZTMC", ssztmc);
        }
        // 获取联系电话
        String LXDH = root.selectSingleNode("//ServiceInfo/ContactPhone").getText();
        if (StringUtils.isNotEmpty(LXDH)) {
            serviceItem.put("LXDH", LXDH);
        }
        // 获取监督电话
        String JDDH = root.selectSingleNode("//ServiceInfo/MonitorComplain")
                .getText();
        if (StringUtils.isNotEmpty(JDDH)) {
            serviceItem.put("JDDH", JDDH);
        }
        // 获取是否收费
        String SFSF = root.selectSingleNode("//ServiceInfo/ChargeFlag").getText();
        if (StringUtils.isNotEmpty(SFSF)) {
            serviceItem.put("SFSF",
                    dictionaryService.getDicCode("YesOrNo", SFSF));
        }
        // 获取收费标准及依据
        String SFBZJYJ = root.selectSingleNode("//ServiceInfo/ChargeStandard")
                .getText();
        if (StringUtils.isNotEmpty(SFBZJYJ)) {
            serviceItem.put("SFBZJYJ", SFBZJYJ);
        }
        // 获取数量限制说明
        String SLXZSM = root.selectSingleNode("//ServiceInfo/CountLimit").getText();
        if (StringUtils.isNotEmpty(SLXZSM)) {
            serviceItem.put("SLXZSM", SLXZSM);
        }
        // 获取收费方式
        String SFFS = root.selectSingleNode("//ServiceInfo/ChargeType")
                .getText();
        if (StringUtils.isNotEmpty(SFFS)) {
            serviceItem.put("SFFS", SFFS);
        }
        // 获取主键值
        String itemId = this.getItemId(itemCode);
        if (StringUtils.isNotEmpty(itemId)) {
            dao.saveOrUpdate(serviceItem, "T_WSBS_SERVICEITEM", itemId);
        } else {
            itemId = dao.saveOrUpdate(serviceItem, "T_WSBS_SERVICEITEM", null);
        }
        return itemId;
    }

    /**
     * 
     * 描述 保存事项票单模板配置
     * 
     * @author Faker Li
     * @created 2015年10月16日 下午5:27:23
     * @param itemId
     * @param ticketsIds
     * @see net.evecom.platform.wsbs.service.ServiceItemService#saveItemTickets(java.lang.String,
     *      java.lang.String)
     */
    public void saveItemTickets(String itemId, String ticketsIds) {
        // 先删除掉中间表数据
        dao.remove("T_WSBS_SERVICEITEM_TICKETS", "ITEM_ID",
                new String[] { itemId });
        dao.saveItemTickets(itemId, ticketsIds);

    }

    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * 
     * @author Faker Li
     * @created 2015年10月19日 上午9:09:46
     * @param entityId
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#getBindTicketsIdANdNames(java.lang.String)
     */
    public Map<String, Object> getBindTicketsIdANdNames(String itemId) {
        return dao.getBindTicketsIdANdNames(itemId);
    }

    /**
     * 
     * 描述 根据项目ID获取绑定的公文IDSNAMES
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午3:02:25
     * @param itemId
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#getBindDocumentIdANdNames(java.lang.String)
     */
    public Map<String, Object> getBindDocumentIdANdNames(String itemId) {
        return dao.getBindDocumentIdANdNames(itemId);
    }

    /**
     * 
     * 描述 保存事项公文模板配置
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午3:09:31
     * @param itemId
     * @param documentIds
     * @see net.evecom.platform.wsbs.service.ServiceItemService#saveItemDocument(java.lang.String,
     *      java.lang.String)
     */
    public void saveItemDocument(String itemId, String documentIds) {
        // 先删除掉中间表数据
        dao.remove("T_WSBS_SERVICEITEM_DOCUMENT", "ITEM_ID",
                new String[] { itemId });
        dao.saveItemDocument(itemId, documentIds);

    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午4:09:01
     * @param itemId
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#getBindReadIdANdNames(java.lang.String)
     */
    public Map<String, Object> getBindReadIdANdNames(String itemId) {
        return dao.getBindReadIdANdNames(itemId);
    }

    /**
     * 
     * 描述 保存阅办中间表
     * 
     * @author Faker Li
     * @created 2015年10月19日 下午4:11:21
     * @param itemId
     * @param readIds
     * @see net.evecom.platform.wsbs.service.ServiceItemService#saveItemRead(java.lang.String,
     *      java.lang.String)
     */
    public void saveItemRead(String itemId, String readIds) {
        // 先删除掉中间表数据
        dao.remove("T_WSBS_SERVICEITEM_READ", "ITEM_ID",
                new String[] { itemId });
        dao.saveItemRead(itemId, readIds);

    }

    /**
     * 
     * 描述 获取草稿列表
     * 
     * @author Faker Li
     * @created 2015年10月20日 下午5:02:21
     * @param filter
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#findByDraftSqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findByDraftSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND (T.FWSXZT='-1' or T.FWSXZT='5' or T.FWSXZT='2') ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND T.SSBMBM in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 获取审核列表页
     * 
     * @author Faker Li
     * @created 2015年10月21日 上午10:35:33
     * @param filter
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#findByAuditingSqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findByAuditingSqlFilter(SqlFilter sqlFilter) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND (T.FWSXZT='2' or T.FWSXZT='5') ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // boolean isGranted = isAuditor("bssxshr",curUser);//是否有办事事项审核权限
        boolean isGranted = sysResService.isGranted("shqx", curUser);
        if (isGranted) {
            // 非超管进行数据级别权限控制
            if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
                // 获取当前用户被授权的部门代码
                String authDepCodes = curUser.getAuthDepCodes();
                sql.append(" AND T.SSBMBM in ").append(
                        StringUtil.getValueArray(authDepCodes));
            }
            String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
            list = dao.findBySql(exeSql, params.toArray(),
                    sqlFilter.getPagingBean());
        }
        return list;
    }

    /**
     * 
     * 描述 获取发布库列表
     * 
     * @author Faker Li
     * @created 2015年10月21日 下午4:39:46
     * @param filter
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#findByPublishSqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,C.CATALOG_NAME,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append(" LEFT JOIN t_wsbs_serviceitem_catalog C ON C.CATALOG_CODE=T.CATALOG_CODE ");
        //sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID ");
//        sql.append(" LEFT JOIN (select CATALOG_CODE,D1.DEPART_ID,D1.DEPART_CODE from t_wsbs_serviceitem_catalog C ");
//        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID) DC ");
//        sql.append("ON DC.CATALOG_CODE=T.CATALOG_CODE ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DC ON DC.DEPART_ID=T.ZBCS_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1'");
        if(sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ")!=null){
            sql.append(" AND (D.DEPART_ID='").append(sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ")).append("'");
            sql.append(" OR DC.DEPART_ID='").append(sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ")).append("')");
            
            sqlFilter.removeFilter("Q_T.DEPART_ID_EQ");
        }
        
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND DC.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        //sql.append(" order by ecount desc ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     *
     * 描述 获取所有事项列表
     *
     * @author Faker Li
     * @created 2015年10月21日 下午4:39:46
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#findByPublishSqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findByAllServiceItemSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append(" LEFT JOIN (select CATALOG_CODE,D1.DEPART_ID,D1.DEPART_CODE from t_wsbs_serviceitem_catalog C ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID) DC ");
        sql.append("ON DC.CATALOG_CODE=T.CATALOG_CODE ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND DC.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        if(sqlFilter.getQueryParams().get("Q_D.DEPART_ID_EQ")!=null){
            sql.append(" AND (D.DEPART_ID='").append(sqlFilter.getQueryParams().get("Q_D.DEPART_ID_EQ")).append("'");
            sql.append(" OR DC.DEPART_ID='").append(sqlFilter.getQueryParams().get("Q_D.DEPART_ID_EQ")).append("')");
            sqlFilter.removeFilter("Q_D.DEPART_ID_EQ");
        }
        //sql.append(" order by ecount desc ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取一窗通办列表
     * 
     * @author Faker Li
     * @created 2015年10月21日 下午4:39:46
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findByPublishYctbSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,T.C_SN,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" case when ecount>0 then ecount else 0 end as ecount, ");
        sql.append("T.FWSXZT,J.DEF_KEY from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN (select count(item_code) ecount,item_code from jbpm6_execution group by item_code)");
        sql.append("  jc on T.ITEM_CODE=jc.item_code ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        //sql.append(" LEFT JOIN t_wsbs_serviceitem_catalog C ON C.CATALOG_ID=T.CATALOG_ID ");
        //sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID ");
//        sql.append(" LEFT JOIN (select CATALOG_CODE,D1.DEPART_ID,D1.DEPART_CODE from t_wsbs_serviceitem_catalog C ");
//        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID) DC ");
//        sql.append("ON DC.CATALOG_CODE=T.CATALOG_CODE ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT DC ON DC.DEPART_ID=T.ZBCS_ID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1'");
        if(sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ")!=null){
            sql.append(" AND (D.DEPART_ID='").append(sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ")).append("'");
            sql.append(" OR DC.DEPART_ID='").append(sqlFilter.getQueryParams().get("Q_T.DEPART_ID_EQ")).append("')");

            sqlFilter.removeFilter("Q_T.DEPART_ID_EQ");
        }
        // 获取当前用户信息
        //SysUser curUser = AppUtil.getLoginUser();
        //sql.append(" order by ecount desc ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 根据事项编码获取事项具体信息
     * @param itemCode
     * @return
     */
    public Map<String,Object> getItemInfoByItemCode(String itemCode){
        Map<String, Object> serviceItem;
        serviceItem = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new Object[] { itemCode });
        Map<String, Object> catalogInfo = dao.getByJdbc(
                "T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_CODE" },
                new Object[] { serviceItem.get("CATALOG_CODE") });
        serviceItem.put("CATALOG_NAME", catalogInfo.get("CATALOG_NAME"));
        if (null != serviceItem) {
            Iterator i = serviceItem.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry e = (Map.Entry) i.next();
                if (null == e.getValue()) {// 等于null转换为""
                    serviceItem.put(e.getKey().toString(), "");
                }
            }
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // 获取材料信息列表
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId,null);

            // 获取材料业务办理子项分类列表
            List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeList(itemId);
            serviceItem.put("applyMaters", applyMaters);
            serviceItem.put("applyNames",getApplyNames(applyMaters));
            serviceItem.put("handleTypes", handleTypes);
            String papersub =serviceItem.get("PAPERSUB")==null?
                    "":serviceItem.get("PAPERSUB").toString();
            papersub=papersub.replace("1", "窗口收取");
            papersub=papersub.replace("2", "邮递收取");
            serviceItem.put("PAPERSUB",papersub);
            setItemTime(serviceItem);
            // 办件类型（取字典类别:ServiceItemType)
            serviceItem.put("SXLX",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SXLX"), "ServiceItemType"));
            // 事项性质(取字典类别: ServiceItemXz)
            serviceItem.put("SXXZ",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SXXZ"), "ServiceItemXz"));
            // 是否收费(取字典类别 YesOrNo)
            serviceItem.put("SFSF",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SFSF"), "YesOrNo"));
            // 收费方式(取字典类别: ChargeType)
            serviceItem.put("SFFS",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("SFFS"), "ChargeType"));
            // 办理结果(取字典类别: FinishType)
            serviceItem.put("FINISH_TYPE",
                    dictionaryService.findByDicCodeAndTypeCode((String) serviceItem.get("FINISH_TYPE"), "FinishType"));
            // 结果获取方式(取字典类别: FinishGetType)
            serviceItem.put("FINISH_GETTYPE", dictionaryService.findByDicCodeAndTypeCode(
                    (String) serviceItem.get("FINISH_GETTYPE"), "FinishGetType"));
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "DIC_CODE" }, new Object[] { serviceItem.get("ITEM_CODE") });
            if (null != dictionary) {
                String dicName = dictionary.get("DIC_NAME").toString();
                if (StringUtils.isNotEmpty(dicName)) {
                    String[] dicNames = dicName.split(",");
                    serviceItem.put("TZLX", dictionary.get("TYPE_CODE"));
                    if (dicNames.length == 2) {
                        serviceItem.put("DQJD", Integer.parseInt(dicNames[0]));
                        serviceItem.put("DQLC", Integer.parseInt(dicNames[1]));
                    }
                }
            }
            Map<String, Object> department = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_CODE" }, new Object[] { (String) serviceItem.get("SSBMBM") });
            serviceItem.put("SSBMBM", department.get("DEPART_NAME"));
            serviceItem.put("deptId", department.get("DEPART_ID"));

            Map<String, Object> e = busTypeService.getIdAndNamesByItemId(itemId);
            String busTypenames = "";
            String typeids = e.get("TYPE_IDS").toString();
            if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                    || typeids.contains("4028818c512273e70151227569a40001")
                    || typeids.contains("4028818c512273e70151229ae7e00003")
                    || typeids.contains("4028818c512273e7015122a38a130004")) {
                busTypenames += "个人";
            }
            if (typeids.contains("4028818c512273e7015122a452220005")
                    || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                    || typeids.contains("4028818c512273e7015122c81f850007")
                    || typeids.contains("4028818c512273e7015122c872dc0008")) {
                if (busTypenames.length() > 1) {
                    busTypenames += ",";
                }
                busTypenames += "企业";
            }
            serviceItem.put("busTypenames", busTypenames);
        }
        return serviceItem;
    }

    /**
     *
     * @param applyMaters
     * @return
     */
    private String getApplyNames(List<Map<String,Object>> applyMaters){
        StringBuffer applyName=new StringBuffer();
        for(int i=0;i<applyMaters.size();i++){
            applyName.append(i+1).append(":").append(applyMaters.get(i).get("MATER_NAME"));
            if(i!=applyMaters.size()-1){
                applyName.append("</br>");
            }
        }
        return applyName.toString();
    }

    /**
     * 设置工作日
     * @param serviceItem
     */
    private void setItemTime(Map<String,Object> serviceItem){
        // 承诺时限（取字典类别:ServiceItemType)
        String cnsxType= dictionaryService.
                findByDicCodeAndTypeCode(StringUtil.getString(serviceItem.get("CNSXLX")), "FDSXLX");
        if(StringUtils.isEmpty(cnsxType)) cnsxType="工作日";
        String cnqxgzr=StringUtil.getString(serviceItem.get("CNQXGZR"));
        serviceItem.put("CNSX",cnqxgzr+"个"+cnsxType);
        //法定时限
        String fdsxType= dictionaryService.
                findByDicCodeAndTypeCode((String) serviceItem.get("FDSXLX"), "FDSXLX");
        if(StringUtils.isEmpty(fdsxType)) fdsxType="工作日";
        String fasxgzr=StringUtil.getString(serviceItem.get("FDSXGZR"));
        if("".equals(fasxgzr)||"-1".equals(fasxgzr)) {
            serviceItem.put("FDSX","无法定时限");
        }else{
            serviceItem.put("FDSX",fasxgzr+"个"+fdsxType);
        }
    }
    /**
     * 
     * 描述 批量修改状态
     * 
     * @author Faker Li
     * @created 2015年10月22日 上午10:18:19
     * @param selectColNames
     * @param state
     * @see net.evecom.platform.wsbs.service.ServiceItemService#updateFwsxzt(java.lang.String,
     *      java.lang.String)
     */
    public void updateFwsxzt(String selectColNames, String state) {
        dao.updateFwsxzt(selectColNames, state);
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年10月27日 上午11:12:11
     * @param itemCode
     * @return
     */
    public Map<String, Object> getItemAndDefInfo(String itemCode) {
        StringBuffer sql = new StringBuffer(
                "select t.FINISH_GETTYPE,T.PAPERSUB,T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.FWSXZT,T.BDLCDYID,");
        sql.append("D.DEF_KEY,F.FORM_KEY,TSD.DEPART_NAME SSBMMC,T.SSBMBM,T.CNQXGZR,SXLX.DIC_NAME SXLX,");
        sql.append("SXXZ.DIC_NAME SXXZ from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF D ON T.BDLCDYID=D.DEF_ID ");
        sql.append("LEFT JOIN JBPM6_FLOWFORM F ON F.FORM_ID=D.BIND_FORMID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT TSD ON TSD.DEPART_CODE = T.SSBMBM ");
        sql.append("LEFT JOIN (SELECT T.DIC_CODE,T.DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY T ");
        sql.append("WHERE T.TYPE_ID=(SELECT S.TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE S ");
        sql.append("WHERE S.TYPE_CODE='ServiceItemType')) SXLX ON SXLX.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN (SELECT T.DIC_CODE,T.DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY T ");
        sql.append("WHERE T.TYPE_ID=(SELECT S.TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE S ");
        sql.append("WHERE S.TYPE_CODE='ServiceItemXz')) SXXZ ON SXXZ.DIC_CODE=T.SXXZ ");
        sql.append("WHERE T.ITEM_CODE=? ");
        Map<String, Object> serviceItem = dao.getByJdbc(sql.toString(),
                new Object[] { itemCode });
        if(serviceItem==null){
            return null;
        }else{
            // 获取项目状态
            String serviceStatus = (String) serviceItem.get("FWSXZT");
            if (serviceStatus.equals("1")) {
                return serviceItem;
            } else {
                return null;
            }
            
        }
    }

    /**
     * 
     * 描述 获取预审人员数据
     * 
     * @author Flex Hu
     * @created 2015年8月20日 下午1:13:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getPreAuditers(Map<String, Object> flowVars,
            String varName, String handlerRule) {
        // 获取事项编码
        String itemCode = (String) flowVars.get("ITEM_CODE");
        StringBuffer sql = new StringBuffer(
                "SELECT U.USER_ID,U.USERNAME,U.FULLNAME,U.MOBILE");
        sql.append(" FROM T_MSJW_SYSTEM_SYSUSER U LEFT JOIN ");
        sql.append(" T_WSBS_PREAUDITER A ON A.USER_ID=U.USER_ID WHERE A.ITEM_ID ");
        sql.append("=(select T.ITEM_ID from T_WSBS_SERVICEITEM T where T.item_code=?)");
        List<Map<String, Object>> users = dao.findBySql(sql.toString(),
                new Object[] { itemCode }, null);
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        for (Map<String, Object> user : users) {
            FlowNextHandler handler = new FlowNextHandler();
            handler.setNextStepAssignerCode((String) user.get("USERNAME"));
            handler.setNextStepAssignerName((String) user.get("FULLNAME"));
            handlers.add(handler);
        }
        return handlers;
    }

    /**
     * 
     * 描述 根据目录编码获取事项编码
     * 
     * @author Faker Li
     * @created 2015年10月28日 下午5:08:19
     * @param catalogCode
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#getMaxNumCode(java.lang.String)
     */
    public String getMaxNumCode(String catalogCode) {
        int num = dao.getMaxNumCode(catalogCode) + 1;
        String numcode = StringUtils.leftPad(String.valueOf(num), 2, '0');
        return numcode;
    }

    /**
     * 
     * 描述 获取服务事项绑定的表单列表
     * 
     * @author Flex Hu
     * @created 2015年11月16日 下午4:30:11
     * @param exeId
     * @param itemCode
     * @return
     */
    public List<Map<String, Object>> findBindForms(String exeId, String itemCode) {
        StringBuffer sql = new StringBuffer(
                "select F.DIC_ID,D.DIC_NAME,D.DIC_DESC from ");
        sql.append("T_WSBS_SERVICEITEM_FORM F LEFT JOIN T_MSJW_SYSTEM_DICTIONARY");
        sql.append(" D ON F.DIC_ID=D.DIC_ID WHERE F.ITEM_ID=(select T.ITEM_ID");
        sql.append(" from T_WSBS_SERVICEITEM T WHERE T.Item_Code=?)");
        sql.append(" ORDER BY F.CREATE_TIME ASC");
        if (StringUtils.isNotEmpty(itemCode)) {
            List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                    new Object[] { itemCode }, null);
            if (list != null && list.size() > 0) {
                List<Map<String, Object>> forms = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> dic : list) {
                    Map<String, Object> form = new HashMap<String, Object>();
                    form.put("FORM_NAME", dic.get("DIC_NAME"));
                    String dicDesc = (String) dic.get("DIC_DESC");
                    StringBuffer formUrl = new StringBuffer(dicDesc);
                    if (dicDesc.indexOf("?") != -1) {
                        formUrl.append("&");
                    } else {
                        formUrl.append("?");
                    }
                    formUrl.append("itemCode=").append(itemCode);
                    formUrl.append("&exeId=").append(exeId);
                    form.put("FORM_URL", formUrl.toString());
                    forms.add(form);
                }
                return forms;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 
     * 描述 保存绑定表单
     * 
     * @author Faker Li
     * @created 2015年11月16日 下午5:31:34
     * @param itemId
     * @param bindFormIds
     */
    public void saveItemFormIds(String itemId, String bindFormIds) {
        // 先删除掉中间表数据
        dao.remove("T_WSBS_SERVICEITEM_FORM", "ITEM_ID",
                new String[] { itemId });
        dao.saveItemFormIds(itemId, bindFormIds);

    }

    /**
     * 
     * 描述 根据itemid获取绑定表单
     * 
     * @author Faker Li
     * @created 2015年11月17日 上午9:12:21
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindFormIdAndName(String itemId) {
        return dao.getBindFormIdAndName(itemId);
    }

    /**
     * 
     * 描述 获取前台列表
     * 
     * @author Faker Li
     * @created 2015年11月23日 下午3:17:35
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> findfrontList(String page, String rows,
            String busType, String sfzxbl) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1)
                * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND T.FWSXZT='1' AND T.FWSXMXLB !='3' ");
        if (StringUtils.isNotEmpty(busType) && busType.equals("grbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fb65110001','4028818c512273e70151227569a40001'"
                    + ",'4028818c512273e70151229ae7e00003','4028818c512273e7015122a38a130004') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("frbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('4028818c512273e7015122a452220005','402883494fd4f3aa014fd4fbe7bf0002'"
                    + ",'4028818c512273e7015122c81f850007','4028818c512273e7015122c872dc0008') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fc68260003') ) ");
        }
        if (sfzxbl.equals("1")) {
            sql.append("AND T.RZBSDTFS !='in01' AND T.RZBSDTFS !='in02' ");
        }
        sql.append(" ORDER BY ");
        if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append(" D.TREE_SN ,");
        }
        sql.append(" T.ITEM_NAME DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> e = busTypeService
                    .getIdAndNamesByItemId((String) list.get(i).get("ITEM_ID"));
            String busTypenames = "";
            String typeids = e.get("TYPE_IDS").toString();
            if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                    || typeids.contains("4028818c512273e70151227569a40001")
                    || typeids.contains("4028818c512273e70151229ae7e00003")
                    || typeids.contains("4028818c512273e7015122a38a130004")) {
                busTypenames += "个人";
            }
            if (typeids.contains("4028818c512273e7015122a452220005")
                    || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                    || typeids.contains("4028818c512273e7015122c81f850007")
                    || typeids.contains("4028818c512273e7015122c872dc0008")) {
                if (busTypenames.length() > 1) {
                    busTypenames += ",";
                }
                busTypenames += "企业";
            }
            list.get(i).put("BUS_TYPENAMES", busTypenames);
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findfrontListNew(String page, String rows,
            String busType, String busTypeIds, String itemName, String sfzxbl) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        boolean isXK = false;
        boolean isGF = false;
        boolean isALL = false;
        if (busTypeIds.contains("XK")) {
            isXK=true;
            busTypeIds = busTypeIds.replace("XK,", "");
        }
        if (busTypeIds.contains("GF")) {
            isGF=true;
            busTypeIds = busTypeIds.replace("GF,", "");
        }
        if (busTypeIds.contains("ALL")) {
            isALL=true;
            busTypeIds = busTypeIds.replace("ALL,", "");
        }
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1)
                * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer(
                "SELECT T.CATALOG_ID,T.DEPART_ID,D.DEPART_NAME,T.CATALOG_NAME,T.CATALOG_CODE,T.CREATE_TIME FROM ");
        sql.append(" T_WSBS_SERVICEITEM_CATALOG T LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ");
        sql.append(" ON D.DEPART_ID = T.DEPART_ID ");
        sql.append("  LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON T.CHILD_DEPART_ID = D1.DEPART_ID ");
        sql.append(" WHERE T.STATUS='1' ");
        if (isXK) {
            sql.append(" and T.sxxz = 'XK' ");
        }
        if (isGF) {
            sql.append(" and T.sxxz = 'GF' ");
        }
        if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append(" AND D.DEPART_CODE IN ( select b.type_code from T_WSBS_BUSTYPE b ");
            sql.append("where b.is_show=1 and b.parent_id='402883494fd4f3aa014fd4fc68260003' ) ");
        }
        sql.append(" AND (select count(*) from T_WSBS_SERVICEITEM i ");
        /*sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=i.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=i.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=i.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=i.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");*/
        sql.append(" WHERE i.FWSXZT='1' AND i.FWSXMXLB !='3' ");
        
        if (StringUtils.isNotEmpty(busType) && busType.equals("grbs")) {
            //sql.append("AND  i.ssbmbm not like '%0000000%' ");//去除乡镇
            sql.append("AND  i.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fb65110001','4028818c512273e70151227569a40001'"
                    + ",'4028818c512273e70151229ae7e00003','4028818c512273e7015122a38a130004') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("frbs")) {
            //sql.append("AND  i.ssbmbm not like '%0000000%' ");//去除乡镇
            sql.append("AND  i.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('4028818c512273e7015122a452220005','402883494fd4f3aa014fd4fbe7bf0002'"
                    + ",'4028818c512273e7015122c81f850007','4028818c512273e7015122c872dc0008') ) ");
        }else if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append("AND  i.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN (select b.type_id from T_WSBS_BUSTYPE b ");
            sql.append("where b.parent_id='402883494fd4f3aa014fd4fc68260003') ) ");
        }
        if (sfzxbl.equals("1")) {
            sql.append("AND i.RZBSDTFS !='in01' AND i.RZBSDTFS !='in02' ");
        }
        if (StringUtils.isNotEmpty(itemName)) {
            //sql.append("AND  i.ITEM_NAME LIKE '%" + itemName.trim() + "%'");
            sql.append(" AND ( i.ITEM_NAME LIKE '%" + itemName.trim() 
                    + "%' OR T.CATALOG_NAME LIKE '%" + itemName.trim()
                    +"%' OR i.SWB_ITEM_CODE LIKE '%" +itemName.trim()
                    + "%' OR i.ITEM_CODE LIKE '%" + itemName.trim() + "%') ");
        }
        if (StringUtils.isNotEmpty(busTypeIds)&&!busType.equals("lsgrbs")) {
            sql.append("AND  i.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                    + " )");
        }else if (busType.equals("lsgrbs")){//绿色通道
            if (StringUtils.isNotEmpty(busTypeIds)) {
                //sql.append("AND  i.ssbmbm not like '%0000000%' ");//去除乡镇
                sql.append("AND  i.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_LSTYPE ST WHERE ");
                sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                        + " )");
            }else {
                sql.append("AND  i.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_LSTYPE ST )");
            }
        }
        sql.append(" AND i.catalog_code = t.catalog_code)>0 ");
        sql.append(" ORDER BY ");
//        if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append(" D.TREE_SN ASC,D.DEPART_ID ASC,t.catalog_name  asc,D1.TREE_SN ASC,D1.DEPART_ID ASC, ");
//        }
        sql.append(" T.CREATE_TIME DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        for (Map<String, Object> map : list) {
            String catalogCode = map.get("CATALOG_CODE") == null ? "" : map.get("CATALOG_CODE").toString();
            if (StringUtils.isNotEmpty(catalogCode)) {
                List<Map<String, Object>> itemList = findItemByCatalog(catalogCode,busType,busTypeIds,itemName,sfzxbl);
                if (null != itemList && itemList.size() > 0) {
                    map.put("itemList", itemList);
                }
            }
            map.put("pageNum", page);
            map.put("limitNum", rows);
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    /**
     * according catalogcode query items
     * @param catalogCode
     * @param busType
     * @param busTypeIds
     * @param itemName
     * @param sfzxbl
     * @return
     */
    public List<Map<String, Object>> findItemByCatalog(String catalogCode,String busType,
            String busTypeIds,String itemName,String sfzxbl){
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON T.CATALOG_CODE=C.CATALOG_CODE ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND T.FWSXZT='1' AND T.FWSXMXLB !='3' ");
        if (StringUtils.isNotEmpty(catalogCode)) {
            sql.append(" AND T.CATALOG_CODE='"+catalogCode+"'");
        }
        if (StringUtils.isNotEmpty(itemName)) {
            //sql.append("AND  T.ITEM_NAME LIKE '%" + itemName.trim() + "%'");
            sql.append(" AND ( T.ITEM_NAME LIKE '%" + itemName.trim() 
                    + "%' OR C.CATALOG_NAME LIKE '%" + itemName.trim()
                     +"%' OR T.SWB_ITEM_CODE LIKE '%" +itemName.trim()
                    + "%' OR T.ITEM_CODE LIKE '%" + itemName.trim() + "%') ");
        }
        if (StringUtils.isNotEmpty(busTypeIds)&&!busType.equals("lsgrbs")) {
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                    + " )");
        }else if (StringUtils.isNotEmpty(busTypeIds)&&busType.equals("lsgrbs")) {//绿色通道
            //sql.append("AND  t.ssbmbm not like '%0000000%' ");//去除乡镇
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_LSTYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                    + " )");
        }
        if (StringUtils.isNotEmpty(busType) && busType.equals("grbs")) {
           // sql.append("AND  t.ssbmbm not like '%0000000%' ");//去除乡镇
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fb65110001','4028818c512273e70151227569a40001'"
                    + ",'4028818c512273e70151229ae7e00003','4028818c512273e7015122a38a130004') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("frbs")) {
            //sql.append("AND  t.ssbmbm not like '%0000000%' ");//去除乡镇
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('4028818c512273e7015122a452220005','402883494fd4f3aa014fd4fbe7bf0002'"
                    + ",'4028818c512273e7015122c81f850007','4028818c512273e7015122c872dc0008') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fc68260003') ) ");
        }else if (StringUtils.isNotEmpty(busType) && busType.equals("lsgrbs")) { //绿色通道
            sql.append("  and t.sflstd is not null ");
            if (StringUtils.isNotEmpty(busTypeIds)) {
                sql.append("and t.sflstd IN " + StringUtil.getValueArray(busTypeIds));
            }
        }
        if (sfzxbl.equals("1")) {
            sql.append("AND T.RZBSDTFS !='in01' AND T.RZBSDTFS !='in02' ");
        }
        sql.append(" ORDER BY ");
//        if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append(" D.TREE_SN ASC,D.DEPART_ID ASC,");
//        }
        sql.append(" T.ITEM_NAME DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> e = busTypeService
                    .getIdAndNamesByItemId((String) list.get(i).get("ITEM_ID"));
            if (busType.equals("lsgrbs")) {
                e = busTypeService
                        .getLsIdAndNamesByItemId((String) list.get(i).get("ITEM_ID"));
            }
            String busTypenames = "";
            String typeids = e.get("TYPE_IDS")==null?
                    "":e.get("TYPE_IDS").toString();
//            if (busType.equals("lsgrbs")) {
//                typeids = list.get(i).get("SFLSTD")==null?
//                        "":list.get(i).get("SFLSTD").toString();
//            }
            if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                    || typeids.contains("4028818c512273e70151227569a40001")
                    || typeids.contains("4028818c512273e70151229ae7e00003")
                    || typeids.contains("4028818c512273e7015122a38a130004")) {
                busTypenames += "个人";
            }
            if (typeids.contains("4028818c512273e7015122a452220005")
                    || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                    || typeids.contains("4028818c512273e7015122c81f850007")
                    || typeids.contains("4028818c512273e7015122c872dc0008")) {
                if (busTypenames.length() > 1) {
                    busTypenames += ",";
                }
                busTypenames += "企业";
            }
            list.get(i).put("BUS_TYPENAMES", busTypenames);
        }
       return list;
    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2015年11月24日 下午3:41:13
     * @param itemIds
     */
    public void updateSn(String[] itemIds) {
        dao.updatSn(itemIds);

    }

    /**
     * 
     * 描述 获取排序最大值
     * 
     * @author Faker Li
     * @created 2015年11月24日 下午3:47:47
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#getMaxSn()
     */
    public int getMaxSn() {
        return dao.getMaxSn();
    }

    /**
     * 
     * 描述 前台获取全部发布事项
     * 
     * @author Faker Li
     * @created 2015年11月26日 下午3:06:48
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findALLPublishItemsFront(String busType) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME ");
        sql.append("from T_WSBS_SERVICEITEM T ");
        sql.append("WHERE T.FWSXZT='1'  AND T.FWSXMXLB !='3' ");
        if (StringUtils.isNotEmpty(busType) && busType.equals("grbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fb65110001','4028818c512273e70151227569a40001'"
                    + ",'4028818c512273e70151229ae7e00003','4028818c512273e7015122a38a130004') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("frbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('4028818c512273e7015122a452220005','402883494fd4f3aa014fd4fbe7bf0002'"
                    + ",'4028818c512273e7015122c81f850007','4028818c512273e7015122c872dc0008') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fc68260003') ) ");
        }
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 
     * 描述 前台办事事项查询
     * 
     * @author Faker Li
     * @created 2015年11月26日 下午6:41:29
     * @param page
     * @param rows
     * @param busType
     * @param itemName
     * @param busTypeIds
     */
    public Map<String, Object> findfrontListByNameAndbusTypeIds(String page,
            String rows, String busType, String itemName, String busTypeIds,
            String sfzxbl) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        boolean isXK = false;
        boolean isGF = false;
        boolean isALL = false;
        if (busTypeIds.contains("XK")) {
            isXK=true;
            busTypeIds = busTypeIds.replace("XK,", "");
        }
        if (busTypeIds.contains("GF")) {
            isGF=true;
            busTypeIds = busTypeIds.replace("GF,", "");
        }
        if (busTypeIds.contains("ALL")) {
            isALL=true;
            busTypeIds = busTypeIds.replace("ALL,", "");
        }
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1)
                * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON T.CATALOG_CODE=C.CATALOG_CODE ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' ");
        sql.append("AND T.FWSXZT='1'  AND T.FWSXMXLB !='3' ");
        if (isXK) {
            sql.append(" and T.sxxz = 'XK' ");
        }
        if (isGF) {
            sql.append(" and T.sxxz = 'GF' ");
        }
        if (StringUtils.isNotEmpty(busType) && busType.equals("grbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fb65110001','4028818c512273e70151227569a40001'"
                    + ",'4028818c512273e70151229ae7e00003','4028818c512273e7015122a38a130004') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("frbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('4028818c512273e7015122a452220005','402883494fd4f3aa014fd4fbe7bf0002'"
                    + ",'4028818c512273e7015122c81f850007','4028818c512273e7015122c872dc0008') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fc68260003') ) ");
        }
        //以省网编码为主
        if (StringUtils.isNotEmpty(itemName)) {
            /*防止sql注入*/
            sql.append(" AND ( T.ITEM_NAME LIKE ? OR C.CATALOG_NAME LIKE ? OR T.SWB_ITEM_CODE LIKE ? " +
                    "OR T.ITEM_CODE LIKE ?) ");
            params.add("%" + itemName.trim() + "%");
            params.add("%" + itemName.trim() + "%");
            params.add("%" + itemName.trim() + "%");
            params.add("%" + itemName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(busTypeIds)) {
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                    + " )");
        }
        if (sfzxbl.equals("1")) {
            sql.append("AND T.RZBSDTFS !='in01' AND T.RZBSDTFS !='in02' ");
        }
        sql.append(" ORDER BY ");
        if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {
            sql.append(" D.TREE_SN ,");
        }
        sql.append(" T.ITEM_NAME DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        if(list!=null){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> e = busTypeService
                        .getIdAndNamesByItemId((String) list.get(i).get("ITEM_ID"));
                String busTypenames = "";
                String typeids = e.get("TYPE_IDS").toString();
                if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                        || typeids.contains("4028818c512273e70151227569a40001")
                        || typeids.contains("4028818c512273e70151229ae7e00003")
                        || typeids.contains("4028818c512273e7015122a38a130004")) {
                    busTypenames += "个人";
                }
                if (typeids.contains("4028818c512273e7015122a452220005")
                        || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                        || typeids.contains("4028818c512273e7015122c81f850007")
                        || typeids.contains("4028818c512273e7015122c872dc0008")) {
                    if (busTypenames.length() > 1) {
                        busTypenames += ",";
                    }
                    busTypenames += "企业";
                }
                list.get(i).put("BUS_TYPENAMES", busTypenames);
            }
        }else{
            list = new ArrayList<Map<String,Object>>();
        }
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;

    }

    /**
     * 根据事项名查找事项
     * @param itemName
     * @param page
     * @param rows
     * @return
     */
    public List<Map<String,Object>> findItemForRobot(String itemName,String page,String rows){
        page=StringUtils.isEmpty(page)?"1":page;
        rows=StringUtils.isEmpty(rows)?"10":rows;
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1)
                * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql=new StringBuffer("select * from ");
        sql.append("(select  NVL(a.TOTAL,0) as num,s.item_name,s.item_code,s.item_id ");
        sql.append(" from t_wsbs_serviceitem s left join ");
        sql.append(" (select COUNT(*) as TOTAL,item_code from jbpm6_execution e group by  ");
        sql.append(" e.item_code) a on s.item_code=a.item_code where s.fwsxzt='1' ");
        sql.append("and s.item_name like '%").append(itemName).append("%') b ");
        sql.append(" order by b.num desc ");
        return  dao.findBySql(sql.toString(),null, pb);
    }
    /**
     * 
     * 描述 根据栏目编码获取发布的事项
     * 
     * @author Faker Li
     * @created 2015年11月27日 上午10:02:44
     * @param catalogCode
     * @return
     * @see net.evecom.platform.wsbs.service.ServiceItemService#findByCatalogCodeForWebSite(java.lang.String)
     */
    public List<Map<String, Object>> findByCatalogCodeForWebSite(
            String catalogCode) {
        List<Object> params = new ArrayList<Object>();
        params.add(catalogCode);
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME ");
        sql.append(" from T_WSBS_SERVICEITEM T WHERE T.CATALOG_CODE = ? AND T.FWSXMXLB !='3' AND T.FWSXZT='1'");
        sql.append("  ORDER BY T.C_SN DESC ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 
     * 描述 获取下一个流程对应的项目编码
     * 
     * @author Flex Hu
     * @created 2015年12月2日 下午4:17:35
     * @param currentItemCode
     *            :当前项目编码
     * @param tzlx
     *            :投资项目类型(1:社会投资 2:政府投资)
     * @return
     */
    public Map<String, Object> getNextTzItemCode(String currentItemCode,
            String tzlx) {
        // 定义不需要编码
        String noNeedItemCode = "350128XK00102";
        if (tzlx.equals("2")) {
            noNeedItemCode = "350128XK00201";
        }
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_NAME,T.ITEM_CODE from T_WSBS_SERVICEITEM T");
        sql.append(" WHERE T.CATALOG_CODE = (select C.catalog_code from T_WSBS_SERVICEITEM C");
        sql.append(" where C.item_code=? ) AND T.ITEM_CODE!=? ");
        sql.append(" AND T.C_SN < (select C.C_SN from T_WSBS_SERVICEITEM C ");
        sql.append(" where C.item_code=? ) ORDER BY T.C_SN DESC ");
        List<Map<String, Object>> list = dao
                .findBySql(sql.toString(), new Object[] { currentItemCode,
                        noNeedItemCode, currentItemCode }, null);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 
     * 描述 根据catalogCode更新事项所属部门编码
     * 
     * @author Faker Li
     * @created 2015年12月16日 下午5:04:51
     * @param catalogCode
     * @param departCode
     */
    public void updateSSBMBM(String catalogCode, String departCode) {
        dao.updateSSBMBM(catalogCode, departCode);
    }

    /**
     * 
     * 描述获取前台权利清单列表
     * 
     * @author Faker Li
     * @created 2015年12月28日 下午3:43:20
     * @param page
     * @param rows
     * @param sxxz
     * @param busTypeIds
     * @return
     */
    public Map<String, Object> findfrontQlqdList(String page, String rows,
            String sxxz, String busTypeIds) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1)
                * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND T.FWSXZT='1' AND T.FWSXMXLB !='3' ");
        if (StringUtils.isNotEmpty(sxxz)) {
            sql.append(" AND T.SXXZ = ? ");
            params.add(sxxz);
        }
        if (StringUtils.isNotEmpty(busTypeIds)) {
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                    + " )");
        }
        sql.append(" ORDER BY T.C_SN DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findfrontList(String busTypeId) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME ");
        sql.append(" FROM T_WSBS_SERVICEITEM T ");
        sql.append(" WHERE  T.FWSXZT='1' AND T.FWSXMXLB !='3'");
        sql.append(" AND T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
        sql.append("ST.TYPE_ID =?)  ORDER BY T.C_SN DESC");
        params.add(busTypeId);
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findWXListByName(String busType,
            String itemName, String busTypeIds, String sfzxbl) {
        //Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND T.FWSXZT='1'  AND T.FWSXMXLB !='3' ");
        if (StringUtils.isNotEmpty(busType) && busType.equals("grbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fb65110001','4028818c512273e70151227569a40001'"
                    + ",'4028818c512273e70151229ae7e00003','4028818c512273e7015122a38a130004') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("frbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('4028818c512273e7015122a452220005','402883494fd4f3aa014fd4fbe7bf0002'"
                    + ",'4028818c512273e7015122c81f850007','4028818c512273e7015122c872dc0008') ) ");
        } else if (StringUtils.isNotEmpty(busType) && busType.equals("bmbs")) {

            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN ('402883494fd4f3aa014fd4fc68260003') ) ");
        }
        if (StringUtils.isNotEmpty(itemName)) {
            sql.append("AND  T.ITEM_NAME LIKE '%" + itemName.trim() + "%'");
        }
        if (StringUtils.isNotEmpty(busTypeIds)) {
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN " + StringUtil.getValueArray(busTypeIds)
                    + " )");
        }
        if (sfzxbl.equals("1")) {
            sql.append("AND T.RZBSDTFS !='in01' AND T.RZBSDTFS !='in02' ");
        }
        sql.append(" ORDER BY T.C_SN DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        // for (int i = 0; i < list.size(); i++) {
        // Map<String, Object> e = busTypeService.getIdAndNamesByItemId((String)
        // list.get(i).get("ITEM_ID"));
        // String busTypenames = "";
        // String typeids = e.get("TYPE_IDS").toString();
        // if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
        // || typeids.contains("4028818c512273e70151227569a40001")
        // || typeids.contains("4028818c512273e70151229ae7e00003")
        // || typeids.contains("4028818c512273e7015122a38a130004")) {
        // busTypenames += "个人";
        // }
        // if (typeids.contains("4028818c512273e7015122a452220005")
        // || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
        // || typeids.contains("4028818c512273e7015122c81f850007")
        // || typeids.contains("4028818c512273e7015122c872dc0008")) {
        // if (busTypenames.length() > 1) {
        // busTypenames += ",";
        // }
        // busTypenames += "企业";
        // }
        // list.get(i).put("BUS_TYPENAMES", busTypenames);
        // }

        return list;
    }

    /**
     * 
     * 描述 根据部门编码获取该部门下的事项总数、行政许可事项数、公共服务事项数、事项服务星级、网上办事开通比例（%）
     * 
     * @author Faker Li
     * @created 2016年1月26日 上午9:54:44
     * @param ssbmbm
     * @return
     */
    public Map<String, Object> getXnjcTaotal(String ssbmbm) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 总数
        int alltotal = dao.getXnjcNum(ssbmbm, null, null,null);
        // 行政许可总数
        int xzxktotal = dao.getXnjcNum(ssbmbm, "XK", null,null);
        // 公共服务总数
        int ggfwtotal = dao.getXnjcNum(ssbmbm, "GF", null,null);
        // 非行政许可公共服务
        //int ftotal = alltotal - xzxktotal - ggfwtotal ;
        // 开通行政许可总数
        int ktxzxktotal = dao.getXnjcNum(ssbmbm, "XK", null,"1");
        // 开通公共服务总数
        int ktggfwtotal = dao.getXnjcNum(ssbmbm, "GF", null,"1");
        // 一星
        int oneStar = dao.getXnjcNum(ssbmbm, null, "1",null);
        // 二星
        int twoStar = dao.getXnjcNum(ssbmbm, null, "2",null);
        // 三星
        int threeStar = dao.getXnjcNum(ssbmbm, null, "3",null);
        // 四星
        int fourStar = dao.getXnjcNum(ssbmbm, null, "4",null);
        // 五星
        int fiveStar = dao.getXnjcNum(ssbmbm, null, "5",null);
        // 行政许可百分比
        double xzxkpre = (ktxzxktotal * 100.0) / alltotal;
        // 公共服务百分比
        double ggfwpre = (ktggfwtotal * 100.0) / alltotal;
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
        if (alltotal != 0) {
            map.put("alltotal", alltotal);
        } else {
            map.put("alltotal", "0");
        }
        if (xzxktotal != 0) {
            map.put("xzxktotal", xzxktotal);
            map.put("tbxzxktotal", xzxktotal);
        } else {
            map.put("xzxktotal", "0");
            map.put("tbxzxktotal", 0);
        }
        if (ktxzxktotal != 0) {
            map.put("xzxkpre", df.format(xzxkpre));
            map.put("ktxzxktotal", ktxzxktotal);
        } else {
            map.put("xzxkpre", "0.0");
            map.put("ktxzxktotal", ktxzxktotal);
        }
        if (ggfwtotal != 0) {
            map.put("ggfwtotal", ggfwtotal);
            map.put("tbggfwtotal", ggfwtotal);
        } else {
            map.put("ggfwtotal", "0");
            map.put("tbggfwtotal", 0);
        }
        if (ktggfwtotal != 0) {
            map.put("ggfwpre", df.format(ggfwpre));
            map.put("ktggfwtotal", ktggfwtotal);
        } else {
            map.put("ggfwpre", "0.0");
            map.put("ktggfwtotal", ktggfwtotal);
        }
        if (oneStar != 0) {
            map.put("oneStar", oneStar);
        } else {
            map.put("oneStar", "0");
        }
        if (twoStar != 0) {
            map.put("twoStar", twoStar);
        } else {
            map.put("twoStar", "0");
        }
        if (threeStar != 0) {
            map.put("threeStar", threeStar);
        } else {
            map.put("threeStar", "0");
        }
        if (fourStar != 0) {
            map.put("fourStar", fourStar);
        } else {
            map.put("fourStar", "0");
        }
        if (fiveStar != 0) {
            map.put("fiveStar", fiveStar);
        } else {
            map.put("fiveStar", "0");
        }
        return map;
    }

    /**
     * 
     * 描述 是否支持挂起
     * 
     * @author Flex Hu
     * @created 2016年3月19日 上午8:55:45
     * @param itemCode
     * @return
     */
    public boolean isForHandUp(String itemCode) {
        return dao.isForHandUp(itemCode);
    }

    /**
     * 描述 获取需要特殊处理的环节
     * 
     * @author Joseph Huang
     * @created 2016年5月5日 上午11:15:00
     * @param itemCode
     * @return
     */
    @Override
    public Map<String, Object> getParticularDealNode(String itemCode) {
        String sql = "select * from JBPM6_CHECKDEALITEM a where a.dealitem_code = ?";
        List<Map<String,Object>> list = dao.findBySql(sql, new Object[]{itemCode}, null);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findZNListByName(String itemName,
            String sfzxbl) {
        //Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,T.BACKOR_NAME,");
        sql.append("D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append("T.FWSXZT,J.DEF_KEY,T.RZBSDTFS from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append("WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType'");
        sql.append("AND T.FWSXZT='1'  AND T.FWSXMXLB !='3' ");

        if (StringUtils.isNotEmpty(itemName)) {
            sql.append("AND  T.ITEM_NAME LIKE '%" + itemName.trim() + "%'");
        }

        if (sfzxbl.equals("1")) {
            sql.append("AND T.RZBSDTFS !='in01' AND T.RZBSDTFS !='in02' ");
        }
        sql.append(" ORDER BY T.C_SN DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    @Override
    public String getSwbItemCode(String itemCode) {
        // TODO Auto-generated method stub
        return dao.getSwbItemCode(itemCode);
    }

    @Override
    public List<Map<String, Object>> findByItemIds(String itemIds) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select * from jbpm6_execution where item_code in ");
        sql.append(" (select item_code from T_WSBS_SERVICEITEM where item_id in ")
        .append(StringUtil.getValueArray(itemIds));
        sql.append("  )");
        
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                params.toArray(),null);
        return list;
    }

    @Override
    public Map<String, Object> getItemAndDefInfoNew(String itemCode) {
        StringBuffer sql = new StringBuffer(
                "select T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.FWSXZT,T.CATALOG_CODE,T.BDLCDYID,");
        sql.append("L.CATALOG_NAME,");
        sql.append("D.DEF_KEY,F.FORM_KEY,TSD.DEPART_NAME SSBMMC,T.SSBMBM,T.CNQXGZR,SXLX.DIC_NAME SXLX,");
        sql.append("SXXZ.DIC_NAME SXXZ from T_WSBS_SERVICEITEM T ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF D ON T.BDLCDYID=D.DEF_ID ");
        sql.append("LEFT JOIN JBPM6_FLOWFORM F ON F.FORM_ID=D.BIND_FORMID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT TSD ON TSD.DEPART_CODE = T.SSBMBM ");
        sql.append("LEFT JOIN (SELECT T.DIC_CODE,T.DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY T ");
        sql.append("WHERE T.TYPE_ID=(SELECT S.TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE S ");
        sql.append("WHERE S.TYPE_CODE='ServiceItemType')) SXLX ON SXLX.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN (SELECT T.DIC_CODE,T.DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY T ");
        sql.append("WHERE T.TYPE_ID=(SELECT S.TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE S ");
        sql.append("WHERE S.TYPE_CODE='ServiceItemXz')) SXXZ ON SXXZ.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_CATALOG L ON L.CATALOG_CODE=T.CATALOG_CODE ");
        sql.append("WHERE T.ITEM_CODE=? ");
        Map<String, Object> serviceItem = dao.getByJdbc(sql.toString(),
                new Object[] { itemCode });
        // 获取项目状态
 //       String serviceStatus = (String) serviceItem.get("FWSXZT");
//        if (serviceStatus.equals("1")||"3".equals(serviceStatus)) {
//            return serviceItem;
//        } else {
//            return null;
//        }
        return serviceItem;
    }

    @Override
    public List<Map<String, Object>> findLogByItemId(String itemId,String type) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT L.* FROM T_WSBS_SERVICEITEM_LOG L where L.ITEM_ID='"
                +itemId+"'");
        if(StringUtils.isNotEmpty(type)){
            sql.append(" and OPERATE_TYPE='"+type+"' ");
        }
        sql.append(" order by OPERATE_TYPE desc,L.OPERATE_TIME desc ");
        //String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        //List<Map<String, Object>> list = dao.findBySql(exeSql,
           //     params.toArray(), sqlFilter.getPagingBean());
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
    
    /**
     * 描述 根据事项名称和部门编码，获取事项
     * @author Bryce Zhang
     * @created 2017-5-22 上午10:43:21
     * @param name
     * @param deptCode
     * @return
     */
    public Map<String, Object> getByNameAndDeptcode(String name, String deptCode){
        StringBuffer sql = new StringBuffer("select * from T_WSBS_SERVICEITEM t ");
        sql.append("where t.item_name like ? and t.ssbmbm = ? ");
        List<Map<String, Object>> items = dao.findBySql(sql.toString(), new Object[]{"%"+name+"%",deptCode}, null);
        if(items != null && items.size() > 0){
            return items.get(0);
        }else{
            return null;
        }
    }

    /**
     * 
     * 描述 获取事项的流程审核节点设置信息
     * @author Danto Huang
     * @created 2017年10月25日 上午9:47:56
     * @param itemCode
     * @param nodeName
     * @return
     */
    public boolean checkItemFlowNodes(String itemCode,String nodeName,String userAccount){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_SERVICEITEM_AUDITER t ");
        sql.append("where t.item_id = (select s.item_id from t_wsbs_serviceitem s where s.item_code = ?) ");
        sql.append("and t.def_id=(select s.bdlcdyid from t_wsbs_serviceitem s where s.item_code = ?) ");
        sql.append("and t.node_type='task' and t.node_name<>'网上预审' ");
        sql.append("order by t.node_key ");
        List<Map<String, Object>> nodes = dao.findBySql(sql.toString(), new Object[] { itemCode, itemCode }, null);
        boolean flag = false;
        for(Map<String,Object> node : nodes){
            if(nodeName.equals(node.get("NODE_NAME"))){
                String userAccounts = (String) node.get("USER_ACCOUNT");
                String[] user = userAccounts.split(",");
                for(int i=0;i<user.length;i++){
                    if(user[i].equals(userAccount)){
                        flag = true;
                        break;
                    }
                }
                if(flag)break;
            }
        }
        return flag;
    }
    /**
     * 
     * 描述 获取未同步事项
     * @author Kester Chen
     * @created 2018-4-20 上午11:43:31
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> swbRecDatas(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        Map<String, Object> querMap = sqlFilter.getQueryParams();
        String swbItemName = querMap.get("Q_T.ITEM_NAME_LIKE")==null?
                "":querMap.get("Q_T.ITEM_NAME_LIKE").toString();
        String swbDepName = querMap.get("Q_T.DEP_NAME_LIKE")==null?
                "":querMap.get("Q_T.DEP_NAME_LIKE").toString();
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.UNID,t.DEPT_NAME as DEPTNAME,t.SERVICE_NAME as SERVICENAME ")
            .append(" ,to_char(t.CREATETIME,'yyyy-mm-dd hh24:mi:ss') as CREATETIME ")
            .append(" from PROVINCEITEMINFO t ")
            .append(" where t.oper_status = 0 and t.type='100' and t.is_file = '0' ");
        if (StringUtils.isNotEmpty(swbItemName)) {
           /* swbItemName = swbItemName.replace("&#40;", "(");
            swbItemName = swbItemName.replace("&#41;", ")");
            sql.append("and t.content like '%"+swbItemName+"%' ");*/
            sql.append(" and t.SERVICE_NAME like ? ");
            params.add("%" + swbItemName + "%");
        }
        if (StringUtils.isNotEmpty(swbDepName)) {
            /*swbDepName = swbDepName.replace("&#40;", "(");
            swbDepName = swbDepName.replace("&#41;", ")");
            sql.append("and t.content like '%"+swbDepName+"%' ");*/
            sql.append(" and t.DEPT_NAME like ? ");
            params.add("%" + swbDepName + "%");
        }
        sql.append("order by t.createtime desc ");

//        // 获取当前用户信息
//        SysUser curUser = AppUtil.getLoginUser();
//        // 非超管进行数据级别权限控制
//        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
//            // 获取当前用户被授权的部门代码
//            String authDepCodes = curUser.getAuthDepCodes();
//            sql.append(" AND D.DEPART_CODE in ").append(
//                    StringUtil.getValueArray(authDepCodes));
//        }
        
//        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                params.toArray(), sqlFilter.getPagingBean());
        return list;
        /*List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            Document document = null;
            document = XmlUtil.stringToDocument((String) map.get("content").toString().replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", ""));
            Element root = document.getRootElement();
            String serviceInfo = root.selectSingleNode("//Case/Body/ServiceInfo").asXML();
            String serviceAccepts = root.selectSingleNode("//Case/Body/ServiceInfo/ServiceAccepts").asXML();
            String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
            // 定义服务事项
            String serviceInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceInfo, "UTF-8");
            String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
            String serviceAcceptsJson = 
                    XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + serviceAccepts, "UTF-8");
            JSONObject serviceInfOobj = JSONObject.parseObject(serviceInfoJson);
            
            String UNID = map.get("UNID").toString();
            String createTime = map.get("CREATETIME").toString();
            String deptName = getObjStringInfo(serviceInfOobj, "DeptName");
            String parentName = getObjStringInfo(serviceInfOobj, "ParentName");
            String serviceName = getObjStringInfo(serviceInfOobj, "ServiceName");
            returnMap.put("UNID", UNID);
            returnMap.put("DEPTNAME", deptName);
            returnMap.put("PARENTNAME", parentName);
            returnMap.put("SERVICENAME", serviceName);
            returnMap.put("CREATETIME", createTime);
            returnlist.add(returnMap);
        }
        return returnlist;*/
    }

    /**
     * 描述:根据申报号exeid获取事项
     *
     * @author Madison You
     * @created 2019/11/8 14:19:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getItemInfoByExeId(String exeId) {
        ArrayList<String> params = new ArrayList<>();
        params.add(exeId);
        StringBuffer sql = new StringBuffer();
        sql.append(" select b.* from JBPM6_EXECUTION a left join T_WSBS_SERVICEITEM b on a.ITEM_CODE = b.ITEM_CODE ");
        sql.append(" where a.exe_id = ? ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        Map<String, Object> map = null;
        if (list != null) {
            map = list.get(0);
        }
        return map;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/31 17:42:00
     * @param
     * @return
     */
    @Override
    public boolean findExitCatalog(String unid) {
        StringBuffer cql = new StringBuffer("select * from PROVINCEITEMINFO t where t.content like '%");
        cql.append(unid).append("%' and t.type='101' and t.oper_status='0' and is_file = '0'");
        List<Map<String,Object>> swbCataInfo = dao.findBySql(cql.toString(), null, null);
        if (swbCataInfo != null && swbCataInfo.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private String getObjStringInfo(JSONObject serviceInfOobj, String cs) {
        String returnInfoString = (JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)==null?
                "":(String) ((JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)).get(0);
        return returnInfoString;
    }

    /**
     * 初始化事项数据
     * @param type
     */
    public void initServiceItem(String type){
        //System.out.println("开始初始化事项基础数据");
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("SELECT T.UNID,T.CONTENT FROM PROVINCEITEMINFO T")
            .add("WHERE T.OPER_STATUS = ? AND T.TYPE= ? AND T.IS_FILE = ?")
            .add("AND T.SERVICE_NAME IS NULL AND ROWNUM<=300")
            .add("ORDER BY T.CREATETIME DESC");
        List<Map<String,Object>> list = dao.findBySql(stringJoiner.toString(),new Object[]{"0",type,"0"},null);
        for(Map<String,Object> map : list){
            String content = (String)map.get("CONTENT");
            if(content!=null){
                Document document = XmlUtil.stringToDocument(content);
                if(document!=null){
                    Element root = document.getRootElement();
                    if(root!=null){
                        Node node = root.selectSingleNode("//Case/Body/ServiceInfo");
                        if(node!=null){
                            String serviceInfo = node.asXML();
                            String serviceInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>"
                                            +serviceInfo, "UTF-8");
                            JSONObject serviceInfOobj = JSONObject.parseObject(serviceInfoJson);
                            Map<String,Object> updateMap = new HashMap<>();
                            updateMap.put("DEPT_UNID", getObjStringInfo(serviceInfOobj,"DeptUnid"));
                            updateMap.put("DEPT_NAME", getObjStringInfo(serviceInfOobj,"DeptName"));
                            updateMap.put("SERVICE_UNID", getObjStringInfo(serviceInfOobj,"Unid"));
                            updateMap.put("SERVICE_NAME", getObjStringInfo(serviceInfOobj,"ServiceName"));
                            updateMap.put("PARENT_UNID", getObjStringInfo(serviceInfOobj,"ParentUnid"));
                            updateMap.put("PARENT_NAME", getObjStringInfo(serviceInfOobj,"ParentName"));
                            dao.saveOrUpdate(updateMap, "PROVINCEITEMINFO", (String)map.get("UNID"));
                        }
                    }
                }
            }
        }

        //System.out.println("结束初始化事项基础数据");
    }
    
    /**
     * 
     * 描述 根据申报号获取流程节点
     * 
     * @author Flex Hu
     * @created 2015年10月27日 上午11:12:11
     * @param itemCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getNodeConfigInfo(String exeid) {
        StringBuffer sql = new StringBuffer(" SELECT t1.NODE_NAME,t1.DEF_VERSION  ");
        sql.append(" FROM JBPM6_NODECONFIG t1,JBPM6_FLOWDEF t2,T_WSBS_SERVICEITEM t3 ");
        sql.append(" WHERE t1.DEF_ID  = t2.DEF_ID AND t1.DEF_VERSION = t2.VERSION AND t1.DEF_ID  = t3.BDLCDYID ");
        sql.append(" AND t3.ITEM_CODE = ? ");
        List<Map<String, Object>> flowItems = dao.findBySql(sql.toString(),
                new Object[] { exeid }, null);
        return flowItems;
    }
    
    /**
     * 
     * 描述 根据申报号获取流程节点定义关系
     * 
     * @author Flex Hu
     * @created 2015年10月27日 上午11:12:11
     * @param itemCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getFlowConfigInfo(String exeid) {
        StringBuffer sql = new StringBuffer(" SELECT t1.FLOW_VERSION ,t1.NODE_NAME ,t1.NODE_KEY ,t1.FROMNODE_KEY ,t1.FROMNODE_LABLE ,t1.NODE_TYPE  ");
        sql.append(" FROM JBPM6_FLOWNODE t1,JBPM6_FLOWDEF t2,T_WSBS_SERVICEITEM t3 ");
        sql.append(" WHERE t1.DEF_ID  = t2.DEF_ID AND t1.FLOW_VERSION = t2.VERSION AND t1.DEF_ID  = t3.BDLCDYID AND t3.ITEM_CODE = ? ");
        sql.append(" ORDER BY t1.NODE_KEY DESC ");
        List<Map<String, Object>> flowItems = dao.findBySql(sql.toString(),
                new Object[] { exeid }, null);
        return flowItems;
    }
}
