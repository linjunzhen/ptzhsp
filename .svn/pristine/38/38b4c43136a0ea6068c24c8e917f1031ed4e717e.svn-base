/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.service.ZgptInterfaceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 
 * 描述 中国平潭网上务业务接口
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年12月2日 上午10:15:19
 */
@SuppressWarnings("rawtypes")
@Service("zgptInterfaceService")
public class ZgptInterfaceServiceImpl extends BaseServiceImpl implements ZgptInterfaceService {
    /**
     * 引入获取省网办报送数据的处理业务的dao
     */
    @Resource
    private SwbInterfaceDao dao;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 引入接口日志业务服务
     */
    @Resource
    private DataAbutLogService dataAbutLogService;

    /**
     * 获取dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 发布公示信息到中国平潭网公示栏目
     * 
     * @author Derek Zhang
     * @created 2015年12月2日 上午10:15:19
     * @param dataAbutment
     */
    @SuppressWarnings("unchecked")
    @Override
    public void publishGSXX(Map<String, Object> dataAbutment) {
        // 获取数据
        String sql = "select r.res_id,r.exe_id,e.bus_tablename,e.bus_recordid "
                + " from t_bsfw_swbdata_res r,jbpm6_execution e "
                + " where r.inter_type = ? and r.oper_status = 0 " + " and r.exe_id = e.exe_id ";
        List<Map<String, Object>> dataList = this.dao.findBySql(sql,
                new Object[] { AllConstant.DATA_INTER_TYPE_ZGPT }, null);
        for (Map<String, Object> data : dataList) {
            String res_id = (String) data.get("RES_ID");
            String bus_tablename = (String) data.get("BUS_TABLENAME");
            String bus_recordid = (String) data.get("BUS_RECORDID");
            String primaryKeyName = (String) this.dao.getPrimaryKeyName(bus_tablename).get(0);
            String dataSql = "select GSSXMM,SBDW,JSDW,JSDD,JSQX,ZTZ,ZJLY,JSGMJZYNR "
                    + ",GSKSSJ,GSJSSJ,SPCS,LXDH,EMAIL,POSTCODE,ADDRESS " + " from " + bus_tablename
                    + " t where t." + primaryKeyName + " = ? ";
            Map<String, Object> dataMap = this.dao
                    .getByJdbc(dataSql, new Object[] { bus_recordid });
            if (dataMap != null)
                this.doSendPublishGSXX(dataAbutment, dataMap, res_id);
        }
    }

    /**
     * 
     * 描述 调用公示发布接口进行发布数据
     * 
     * @author Derek Zhang
     * @created 2015年12月2日 下午12:29:58
     * @param data
     */
    private void doSendPublishGSXX(Map<String, Object> dataAbutment, Map<String, Object> data,
            String res_id) {
        String url = (String) dataAbutment.get("WEBSERVICE_URL");
        String moduleId = (String) dataAbutment.get("CONFIG_XML");
        if (StringUtils.isBlank(moduleId))
            moduleId = "2930";
        StringBuffer buffer = new StringBuffer();
        buffer.append("MODULEID=" + moduleId + "&BUSID=" + res_id);
        buffer.append("&GSSXMM=" + (data.get("GSSXMM") == null ? "" : (String) data.get("GSSXMM")));
        buffer.append("&SBDW=" + (data.get("SBDW") == null ? "" : (String) data.get("SBDW")));
        buffer.append("&JSDW=" + (data.get("JSDW") == null ? "" : (String) data.get("JSDW")));
        buffer.append("&JSDD=" + (data.get("JSDD") == null ? "" : (String) data.get("JSDD")));
        buffer.append("&JSQX=" + (data.get("JSQX") == null ? "" : (String) data.get("JSQX")));
        buffer.append("&ZTZ=" + (data.get("ZTZ") == null ? "" : (String) data.get("ZTZ")));
        buffer.append("&ZJLY=" + (data.get("ZJLY") == null ? "" : (String) data.get("ZJLY")));
        buffer.append("&JSGMJZYNR="
                + (data.get("JSGMJZYNR") == null ? "" : (String) data.get("JSGMJZYNR")));
        buffer.append("&GSKSSJ=" + (data.get("GSKSSJ") == null ? "" : (String) data.get("GSKSSJ")));
        buffer.append("&GSJSSJ=" + (data.get("GSJSSJ") == null ? "" : (String) data.get("GSJSSJ")));
        buffer.append("&SPCS=" + (data.get("SPCS") == null ? "" : (String) data.get("SPCS")));
        buffer.append("&LXDH=" + (data.get("LXDH") == null ? "" : (String) data.get("LXDH")));
        buffer.append("&EMAIL=" + (data.get("EMAIL") == null ? "" : (String) data.get("EMAIL")));
        buffer.append("&POSTCODE="
                + (data.get("POSTCODE") == null ? "" : (String) data.get("POSTCODE")));
        buffer.append("&ADDRESS="
                + (data.get("ADDRESS") == null ? "" : (String) data.get("ADDRESS")));
        Map<String, Object> dataAbutLog = new HashMap<String, Object>();
        dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
        dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
        dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        dataAbutLog.put("BUS_IDVALUE", res_id);
        dataAbutLog.put("ABUT_SENDDATA", url + "?" + buffer.toString());
        String result = HttpRequestUtil.sendPost(url, buffer.toString());
        if (!result.isEmpty()) {
            Map<String, Object> map = JsonUtil.parseJSON2Map(result);
            if ((Boolean) map.get("success")) {
                dataAbutLog.put("ABUT_RESULT", "1");
                dataAbutLog.put("ABUT_RECEDATA", result);
                dataAbutLog.put("ABUT_DESC", "【向中国平潭网站发布公示信息】操作成功！");
                String updateSql = "update t_bsfw_swbdata_res r set r.oper_status = 1"
                        + ", r.oper_time =? where r.res_id = ? ";
                this.dao.executeSql(updateSql, new Object[] {
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), res_id });
            } else {
                dataAbutLog.put("ABUT_RESULT", "-1");
                dataAbutLog.put("ABUT_RECEDATA", result);
                dataAbutLog.put("ABUT_DESC", "【向中国平潭网站发布公示信息】操作失败！");
                String updateSql = "update t_bsfw_swbdata_res r set r.oper_status = 2"
                        + ", r.oper_time =? where r.res_id = ?";
                this.dao.executeSql(updateSql, new Object[] {
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), res_id });
            }
        } else {
            dataAbutLog.put("ERROR_LOG", "");
            dataAbutLog.put("ABUT_DESC", "【向中国平潭网站发布公示信息】操作失败！");
            dataAbutLog.put("ABUT_RESULT", "-1");
            String updateSql = "update t_bsfw_swbdata_res r set r.oper_status = 2"
                    + ", r.oper_time =? where r.res_id = ?";
            this.dao.executeSql(updateSql, new Object[] { DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                res_id });
        }
        dataAbutLogService.saveOrUpdateLog(dataAbutLog);
    }

    /**
     * 描述 后置事件保存公示信息发布指令到接口中间表
     * 
     * @author Derek Zhang
     * @created 2015年12月2日 上午10:15:19
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> publishGSXXSave(Map<String, Object> flowVars) {
        if (flowVars != null && flowVars.get("EFLOW_EXEID") != null) {
            try {
                Map<String, String> colValues = new HashMap<String, String>();
                colValues.put("EXE_ID", (String) flowVars.get("EFLOW_EXEID"));
                colValues.put("DATA_TYPE", "00");
                colValues.put("OPER_TYPE", "I");
                colValues.put("HAS_ATTR", "0");
                colValues.put("CREATE_TIME",
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                colValues.put("OPER_STATUS", "0");
                colValues.put("INTER_TYPE", "00");
                this.dao.saveOrUpdate(colValues, "t_bsfw_swbdata_res", "res_id");
            } catch (Exception e) {
            }
        }
        return flowVars;
    }
    
    /**
     * 
     * 描述 后置时间保存推送电子证照指令到接口中间表
     * @author Danto Huang
     * @created 2015-12-22 下午3:08:48
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> publishDZZZSave(Map<String, Object> flowVars){
        if (flowVars != null && flowVars.get("EFLOW_EXEID") != null) {
            try {
                Map<String, String> colValues = new HashMap<String, String>();
                colValues.put("EXE_ID", (String) flowVars.get("EFLOW_EXEID"));
                colValues.put("DATA_TYPE", "00");
                colValues.put("OPER_TYPE", "I");
                colValues.put("HAS_ATTR", "0");
                colValues.put("TASK_ID", (String) flowVars.get("EFLOW_CURRENTTASK_ID"));
                colValues.put("CREATE_TIME",
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                colValues.put("OPER_STATUS", "0");
                colValues.put("INTER_TYPE", "20");
                this.dao.saveOrUpdate(colValues, "t_bsfw_swbdata_res", "res_id");
            } catch (Exception e) {
            }
        }
        return flowVars;
    }
}
