/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.fjfda.dao.FoodManagementDao;
import net.evecom.platform.fjfda.service.FdaDicTypeService;
import net.evecom.platform.fjfda.service.FdaDictionaryService;
import net.evecom.platform.fjfda.service.FoodManagementService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 食品经营操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("foodManagementService")
public class FoodManagementServiceImpl extends BaseServiceImpl implements FoodManagementService {
    /**
     * 所引入的dao
     */
    @Resource
    private FoodManagementDao dao;

    /**
     * fdaDicTypeService
     */
    @Resource
    private FdaDicTypeService fdaDicTypeService;

    /**
     * 引入Service
     */
    @Resource
    private FdaDictionaryService fdaDictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年5月31日 下午4:18:22
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        if (StringUtils.isNotEmpty(busRecordId)) {
            sysLogService.saveLog("修改了ID为[" + busRecordId + "]的食品经营信息记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + busRecordId + "]的食品经营信息记录", SysLogService.OPERATE_TYPE_ADD);
        }
        flowDatas.put("JBXX_ID", busRecordId);
        flowDatas.put("XKZBH", flowDatas.get("XKZBH_OLD"));

        // 保存地址信息
        setAddressInfo(flowDatas, busRecordId);

        // 保存代理人信息，先删除代理人信息
        dao.remove("T_FJFDA_SPJYXK_WTDLRXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        dao.saveOrUpdate(flowDatas, "T_FJFDA_SPJYXK_WTDLRXX", "");

        // 先移除设备子表，保存设备表
        dao.remove("T_FJFDA_SPJYXK_SBXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String facilityequipmentjson = (String) flowDatas.get("FacilityEquipmentJson");
        if (StringUtils.isNotEmpty(facilityequipmentjson)) {
            List<Map> facilityequipmentList = JSON.parseArray(facilityequipmentjson, Map.class);
            for (int i = 0; i < facilityequipmentList.size(); i++) {
                Map<String, Object> facilityequipmentMap = facilityequipmentList.get(i);
                facilityequipmentMap.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(facilityequipmentMap, "T_FJFDA_SPJYXK_SBXX", "");
            }
        }
        // 保存人员信息
        savePerson(flowDatas, busRecordId);
        // 保存经营信息
        saveJYXX(flowDatas, busRecordId);
        
        // 获取上传的附件ID
        String fileIds = (String) flowDatas.get("EFLOW_FILEATTACHIDS");
        if (StringUtils.isNotEmpty(fileIds)) {
            // 进行附件业务表记录ID的更新
            fileAttachService.updateTableName(fileIds, busRecordId);
        }
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            fileAttachService.saveItemMaterFiles(submitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }

    /**
     * 描述 保存地址信息
     * 
     * @author Faker Li
     * @created 2016年6月1日 下午5:43:12
     * @param flowDatas
     * @param busRecordId
     */
    private void setAddressInfo(Map<String, Object> flowDatas, String busRecordId) {
        // 保存三个地址信息，先删除
        dao.remove("T_FJFDA_SPJYXK_DZXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        // 保存住所信息
        Map<String, Object> residenceInfo = new HashMap<String, Object>();
        residenceInfo.put("DZLB", "1");
        residenceInfo.put("SQZZZM", flowDatas.get("ZSQS"));
        residenceInfo.put("XZZXXJS", flowDatas.get("ZSXS"));
        residenceInfo.put("XZJD", flowDatas.get("ZSXZ"));
        residenceInfo.put("CLNMPHM", flowDatas.get("ZSXXDZ"));
        residenceInfo.put("JBXX_ID", busRecordId);
        dao.saveOrUpdate(residenceInfo, "T_FJFDA_SPJYXK_DZXX", "");
        // 保存经营场所
        Map<String, Object> placeOfBusiness = new HashMap<String, Object>();
        placeOfBusiness.put("DZLB", "2");
        placeOfBusiness.put("SQZZZM", flowDatas.get("JYCSQS"));
        placeOfBusiness.put("XZZXXJS", flowDatas.get("JYCSXS"));
        placeOfBusiness.put("XZJD", flowDatas.get("JYCSXZ"));
        placeOfBusiness.put("CLNMPHM", flowDatas.get("JYXXDZ"));
        placeOfBusiness.put("JBXX_ID", busRecordId);
        dao.saveOrUpdate(placeOfBusiness, "T_FJFDA_SPJYXK_DZXX", "");
        flowDatas.put("APPLY_ADDR", this.getAddressStr(busRecordId, 2));
        // 保存仓储场所
        Map<String, Object> storagePlace = new HashMap<String, Object>();
        storagePlace.put("DZLB", "3");
        storagePlace.put("SQZZZM", flowDatas.get("CCCSQS"));
        storagePlace.put("XZZXXJS", flowDatas.get("CCCSXS"));
        storagePlace.put("XZJD", flowDatas.get("CCCSXZ"));
        storagePlace.put("CLNMPHM", flowDatas.get("CCXXDZ"));
        storagePlace.put("JBXX_ID", busRecordId);
        dao.saveOrUpdate(storagePlace, "T_FJFDA_SPJYXK_DZXX", "");
    }

    /**
     * 描述 获取人员信息列表
     * 
     * @author Faker Li
     * @created 2016年5月31日 下午4:38:17
     * @param string
     * @param string2
     * @return
     * @see net.evecom.platform.business.service.FoodManagementService#findPersonel(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findPersonel(String jbxxId, String rysx) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPJYXK_RYXX R ");
        sql.append("WHERE R.JBXX_ID=?  AND R.RYSX =? ORDER BY R.CREATE_TIME ASC,R.RYXX_ID ASC");
        params.add(jbxxId);
        params.add(rysx);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 描述获取法人代表信息
     * 
     * @author Faker Li
     * @created 2016年5月31日 下午4:48:45
     * @param jbxxId
     * @return
     */
    @Override
    public Map<String, Object> getLegalRepresentative(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPJYXK_FRDB R ");
        sql.append("WHERE R.JBXX_ID=?   ");
        params.add(jbxxId);
        return dao.getByJdbc(sql.toString(), params.toArray());
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年5月31日 下午4:54:13
     * @param string
     * @return
     * @see net.evecom.platform.business.service.FoodManagementService#findFacilityEquipment(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> findFacilityEquipment(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPJYXK_SBXX S ");
        sql.append("WHERE S.JBXX_ID=?   ");
        sql.append("ORDER BY  S.CREATE_TIME ASC,S.SBXX_ID ASC   ");
        params.add(jbxxId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 描述获取经营项目信息
     * 
     * @author Faker Li
     * @created 2016年5月31日 下午6:31:51
     * @param jbxxId
     * @return
     * @see net.evecom.platform.business.service.FoodManagementService#getYxm(java.lang.String)
     */
    @Override
    public Map<String, Object> getYxm(String jbxxId, String key) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select WM_CONCAT(JY.JYXM_VALUE) JYXM_VALUE ");
        sql.append("from T_FJFDA_SPJYXK_JYXM JY ");
        sql.append("WHERE JY.JBXX_ID=?  AND JY.JYXM_KEY = ? ");
        params.add(jbxxId);
        params.add(key);
        return dao.getByJdbc(sql.toString(), params.toArray());
    }

    /**
     * 描述获取地址信息
     * 
     * @author Faker Li
     * @created 2016年6月1日 上午9:10:50
     * @param bus_recordid
     * @param string
     * @return
     */
    @Override
    public Map<String, Object> getAddressMap(String jbxxId, String dzlb) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        if (StringUtils.isNotEmpty(dzlb)) {
            if (dzlb.equals("1")) {
                sql.append("select SQZZZM ZSQS,XZZXXJS ZSXS,XZJD ZSXZ,CLNMPHM ZSXXDZ  ");
            } else if (dzlb.equals("2")) {
                sql.append("select SQZZZM JYCSQS,XZZXXJS JYCSXS,XZJD JYCSXZ,CLNMPHM JYXXDZ  ");
            } else if (dzlb.equals("3")) {
                sql.append("select SQZZZM CCCSQS,XZZXXJS CCCSXS,XZJD CCCSXZ,CLNMPHM CCXXDZ  ");
            } else {
                sql.append("select *  ");
            }
            sql.append(" from T_FJFDA_SPJYXK_DZXX D WHERE D.JBXX_ID=? AND D.DZLB=?   ");
            params.add(jbxxId);
            params.add(dzlb);
            return dao.getByJdbc(sql.toString(), params.toArray());
        } else {
            return null;
        }
    }

    /**
     * 描述获取代理人信息
     * 
     * @author Faker Li
     * @created 2016年6月1日 下午4:48:40
     * @param jbxxId
     * @return
     */
    @Override
    public Map<String, Object> getClinetMap(String jbxxId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from T_FJFDA_SPJYXK_WTDLRXX R ");
        sql.append("WHERE R.JBXX_ID=?   ");
        params.add(jbxxId);
        return dao.getByJdbc(sql.toString(), params.toArray());
    }

    /**
     * 
     * 描述 根据申请方式不同跳转不同的环节
     * 
     * @author Faker Li
     * @created 2016年6月2日 上午9:53:39
     * @param flowVars
     * @return
     */
    public Set<String> getApplicationFormResult(Map<String, Object> flowVars) {
        String sqfs = (String) flowVars.get("SQFS");
        Set<String> resultNodes = new HashSet<String>();
        if (StringUtils.isNotEmpty(sqfs)) {
            if (sqfs.equals("1")) {
                resultNodes.add("预审");
            } else {
                resultNodes.add("受理");
            }
        }
        return resultNodes;
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年6月12日 下午7:04:44
     * @param flowDatas
     * @return
     * @see net.evecom.platform.business.service.FoodManagementService#saveChangeBusData(java.util.Map)
     */
    @Override
    public Map<String, Object> saveChangeBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);

        flowDatas.put("JBXX_ID", busRecordId);
        flowDatas.put("XKZBH", flowDatas.get("XKZBH_OLD"));
        // 保存委托人信息，先删除委托人信息
        dao.remove("T_FJFDA_SPJYXK_WTDLRXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        dao.saveOrUpdate(flowDatas, "T_FJFDA_SPJYXK_WTDLRXX", "");

        // 先移除设备子表，保存设备表
        dao.remove("T_FJFDA_SPJYXK_SBXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String facilityequipmentjson = (String) flowDatas.get("FacilityEquipmentJson");
        if (StringUtils.isNotEmpty(facilityequipmentjson)) {
            List<Map> facilityequipmentList = JSON.parseArray(facilityequipmentjson, Map.class);
            for (int i = 0; i < facilityequipmentList.size(); i++) {
                Map<String, Object> facilityequipmentMap = facilityequipmentList.get(i);
                facilityequipmentMap.put("JBXX_ID", busRecordId);
                dao.saveOrUpdate(facilityequipmentMap, "T_FJFDA_SPJYXK_SBXX", "");
            }
        }
        // 保存地址信息
        setAddressInfo(flowDatas, busRecordId);
        // 保存人员信息
        savePerson(flowDatas, busRecordId);
        // 保存经营信息
        saveJYXX(flowDatas, busRecordId);
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }

    /**
     * 描述 保存人员信息
     * 
     * @author Faker Li
     * @created 2016年6月12日 下午7:10:38
     * @param flowDatas
     * @param busRecordId
     */
    public void savePerson(Map<String, Object> flowDatas, String busRecordId) {
        // 保存法人信息 ,先删除法人信息
        dao.remove("T_FJFDA_SPJYXK_FRDB", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        dao.saveOrUpdate(flowDatas, "T_FJFDA_SPJYXK_FRDB", "");

        // 先移除设备子表，技术人员子表
        dao.remove("T_FJFDA_SPJYXK_RYXX", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String technicalpersonneljson = (String) flowDatas.get("TechnicalPersonnelJson");
        String practitionersjson = (String) flowDatas.get("PractitionersJson");
        if (StringUtils.isNotEmpty(technicalpersonneljson)) {
            List<Map> technicalpersonnelList = JSON.parseArray(technicalpersonneljson, Map.class);
            for (int i = 0; i < technicalpersonnelList.size(); i++) {
                Map<String, Object> technicalpersonneltMap = technicalpersonnelList.get(i);
                technicalpersonneltMap.put("JBXX_ID", busRecordId);
                technicalpersonneltMap.put("RYSX", "1");
                dao.saveOrUpdate(technicalpersonneltMap, "T_FJFDA_SPJYXK_RYXX", "");
            }
        }
        if (StringUtils.isNotEmpty(practitionersjson)) {
            List<Map> practitionersList = JSON.parseArray(practitionersjson, Map.class);
            for (int i = 0; i < practitionersList.size(); i++) {
                Map<String, Object> practitionersMap = practitionersList.get(i);
                practitionersMap.put("JBXX_ID", busRecordId);
                practitionersMap.put("RYSX", "2");
                dao.saveOrUpdate(practitionersMap, "T_FJFDA_SPJYXK_RYXX", "");
            }
        }
    }

    /**
     * 描述 保存经营项目信息
     * 
     * @author Faker Li
     * @created 2016年6月12日 下午7:08:28
     * @param flowDatas
     * @param busRecordId
     */
    public void saveJYXX(Map<String, Object> flowDatas, String busRecordId) {
        // 移除经营项目数据
        dao.remove("T_FJFDA_SPJYXK_JYXM", new String[] { "JBXX_ID" }, new Object[] { busRecordId });
        String ybzspxs = (String) flowDatas.get("YBZSPXS");
        if (StringUtils.isNotEmpty(ybzspxs)) {
            Map<String, Object> ybzspxsMap = new HashMap<String, Object>();
            ybzspxsMap.put("JBXX_ID", busRecordId);
            ybzspxsMap.put("JYXM_VALUE", ybzspxs);
            ybzspxsMap.put("JYXM_KEY", "YBZSPXS");
            dao.saveOrUpdate(ybzspxsMap, "T_FJFDA_SPJYXK_JYXM", "");
        }
        String szspxs = (String) flowDatas.get("SZSPXS");
        if (StringUtils.isNotEmpty(szspxs)) {
            Map<String, Object> szspxsMap = new HashMap<String, Object>();
            szspxsMap.put("JBXX_ID", busRecordId);
            szspxsMap.put("JYXM_VALUE", szspxs);
            szspxsMap.put("JYXM_KEY", "SZSPXS");
            dao.saveOrUpdate(szspxsMap, "T_FJFDA_SPJYXK_JYXM", "");
        }
        String tsspxs = (String) flowDatas.get("TSSPXS");
        if (StringUtils.isNotEmpty(tsspxs)) {
            String[] tsspxsArray = tsspxs.split(",");
            for (int i = 0; i < tsspxsArray.length; i++) {
                Map<String, Object> tsspxsMap = new HashMap<String, Object>();
                tsspxsMap.put("JBXX_ID", busRecordId);
                tsspxsMap.put("JYXM_VALUE", tsspxsArray[i]);
                tsspxsMap.put("JYXM_KEY", "TSSPXS");
                dao.saveOrUpdate(tsspxsMap, "T_FJFDA_SPJYXK_JYXM", "");
            }
        }
        String qtlspxslb = (String) flowDatas.get("QTLSPXSLB");
        if (StringUtils.isNotEmpty(qtlspxslb)) {
            Map<String, Object> qtlspxslbMap = new HashMap<String, Object>();
            qtlspxslbMap.put("JBXX_ID", busRecordId);
            qtlspxslbMap.put("JYXM_VALUE", qtlspxslb);
            qtlspxslbMap.put("JYXM_KEY", "QTLSPXSLB");
            dao.saveOrUpdate(qtlspxslbMap, "T_FJFDA_SPJYXK_JYXM", "");
        }
        String dllspzs = (String) flowDatas.get("DLLSPZS");
        if (StringUtils.isNotEmpty(dllspzs)) {
            String[] dllspzsArray = dllspzs.split(",");
            for (int i = 0; i < dllspzsArray.length; i++) {
                Map<String, Object> dllspzsMap = new HashMap<String, Object>();
                dllspzsMap.put("JBXX_ID", busRecordId);
                dllspzsMap.put("JYXM_VALUE", dllspzsArray[i]);
                dllspzsMap.put("JYXM_KEY", "DLLSPZS");
                dao.saveOrUpdate(dllspzsMap, "T_FJFDA_SPJYXK_JYXM", "");
            }
        }
        String qtlspzslb = (String) flowDatas.get("QTLSPZSLB");
        if (StringUtils.isNotEmpty(qtlspzslb)) {
            Map<String, Object> qtlspzslbMap = new HashMap<String, Object>();
            qtlspzslbMap.put("JBXX_ID", busRecordId);
            qtlspzslbMap.put("JYXM_VALUE", qtlspzslb);
            qtlspzslbMap.put("JYXM_KEY", "QTLSPZSLB");
            dao.saveOrUpdate(qtlspzslbMap, "T_FJFDA_SPJYXK_JYXM", "");
        }
    }

    /**
     * 
     * 描述 许可延续草稿复制接口
     * 
     * @author Flex Hu
     * @created 2016年6月21日 下午4:30:58
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveDraftForContinue(Map<String, Object> flowVars) {
        // 获取主表ID
        String mainTableId = (String) flowVars.get("EFLOW_BUSRECORDID");
        // 获取主表记录
        Map<String, Object> eflow_busrecord = dao.getByJdbc("T_FJFDA_SPJYXK_JBXX", new String[] { "JBXX_ID" },
                new Object[] { mainTableId });
        Map<String, Object> ybzspxs = this.getYxm(mainTableId, "YBZSPXS");
        Map<String, Object> szspxs = this.getYxm(mainTableId, "SZSPXS");
        Map<String, Object> tsspxs = this.getYxm(mainTableId, "TSSPXS");
        Map<String, Object> qtlspxslb = this.getYxm(mainTableId, "QTLSPXSLB");
        Map<String, Object> dllspzs = this.getYxm(mainTableId, "DLLSPZS");
        Map<String, Object> qtlspzslb = this.getYxm(mainTableId, "QTLSPZSLB");
        if (ybzspxs != null) {
            eflow_busrecord.put("YBZSPXS", ybzspxs.get("JYXM_VALUE"));
        }
        if (szspxs != null) {
            eflow_busrecord.put("SZSPXS", szspxs.get("JYXM_VALUE"));
        }
        if (tsspxs != null) {
            eflow_busrecord.put("TSSPXS", tsspxs.get("JYXM_VALUE"));
        }
        if (qtlspxslb != null) {
            eflow_busrecord.put("QTLSPXSLB", qtlspxslb.get("JYXM_VALUE"));
        }
        if (dllspzs != null) {
            eflow_busrecord.put("DLLSPZS", dllspzs.get("JYXM_VALUE"));
        }
        if (qtlspzslb != null) {
            eflow_busrecord.put("QTLSPZSLB", qtlspzslb.get("JYXM_VALUE"));
        }
        Map<String, Object> residenceInfo = this.getAddressMap(mainTableId, "1");
        Map<String, Object> placeOfBusiness = this.getAddressMap(mainTableId, "2");
        Map<String, Object> storagePlace = this.getAddressMap(mainTableId, "3");
        if (residenceInfo != null) {
            eflow_busrecord.putAll(residenceInfo);
        }
        if (placeOfBusiness != null) {
            eflow_busrecord.putAll(placeOfBusiness);
        }
        if (storagePlace != null) {
            eflow_busrecord.putAll(storagePlace);
        }

        return flowVars;
    }

    /**
     * 描述 根据经营场所获取结果表条数
     * 
     * @author Faker Li
     * @created 2016年7月15日 下午1:17:43
     * @param jYCS
     * @return
     */
    @Override
    public Map<String, Object> getCountNumByJycs(String itemCode, String jycsqs, String jycsxs, String jycsxz,
            String jyxxdz, String exe_id) {
        return null;
    }

    /**
     * 
     * 描述 获取经营许可信息
     * 
     * @author Usher Song
     * @created 2016年7月15日 上午10:58:15
     * @param jbxxId
     * @return
     */
    @Override
    public Map<String, Object> getFoodManagementInfo(String jbxxId) {
        Map<String, Object> jbxxMap = dao.getByJdbc("T_FJFDA_SPJYXK_JBXX", new String[] { "JBXX_ID" },
                new Object[] { jbxxId });
        // 住所地址
        jbxxMap.put("ZS", getAddressStr(jbxxId, 1));
        // 经营场所
        jbxxMap.put("JYCS", getAddressStr(jbxxId, 2));
        // 仓库地址
        jbxxMap.put("CKDZ", getAddressStr(jbxxId, 3));

        // 获取法定责任人
        Map<String, Object> frdbMap = dao.getByJdbc("T_FJFDA_SPJYXK_FRDB", new String[] { "JBXX_ID" },
                new Object[] { jbxxId });
        if (frdbMap != null) {
            // 新开办
            jbxxMap.put("FRDB", frdbMap.get("FRXM"));
            jbxxMap.put("FDDBRFZR", frdbMap.get("FRXM"));
        }
        // 经营项目
        getJyxmValue(jbxxId, jbxxMap);

        if (jbxxMap.get("WLJY").equals("1")) {
            jbxxMap.put("WLJY", "是");
        } else {
            jbxxMap.put("WLJY", "否");
        }

        if (jbxxMap.get("ZYCF").equals("1")) {
            jbxxMap.put("ZYCF", "是");
        } else {
            jbxxMap.put("ZYCF", "否");
        }
        if (jbxxMap.get("JTYCPSDW").equals("1")) {
            jbxxMap.put("JTYCPSDW", "是");
        } else {
            jbxxMap.put("JTYCPSDW", "否");
        }
        // 主体业态
        String ztytStr = getZtytStr(jbxxMap);
        jbxxMap.put("ZTYT", ztytStr);
        return jbxxMap;
    }

    /**
     * 
     * 描述 获取经营许可信息
     * 
     * @author Usher Song
     * @created 2016年7月15日 上午10:58:15
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getFoodManagementInfo(Map<String, Object> flowVars) {
        Map<String, Object> licenseData = (Map<String, Object>) flowVars.get("EFLOW_BUSRECORD");
        String itemCode = (String) flowVars.get("ITEM_CODE");
        // 补证流程
        if (itemCode.equals("001XK00107")) {
            licenseData = dao.getByJdbc("T_FJFDA_SPJYXKXX", new String[] { "XKZBH" },
                    new Object[] { licenseData.get("XKZBH_OLD").toString().trim() });
            if (licenseData != null) {
                licenseData.put("FRDB", licenseData.get("FDDBRFZR"));
                licenseData.put("YXQ", licenseData.get("YXQZ"));
            }
        } else if (licenseData.containsKey("JBXX_ID") && licenseData.get("JBXX_ID") != null) {
            String jbxxId = String.valueOf(licenseData.get("JBXX_ID"));
            // 有效期和发证日期
            this.setLicenseDate(flowVars, licenseData, itemCode);
            // 经营项目
            this.getJyxmValue(jbxxId, licenseData);
            // 社会信用代码
            if (licenseData.get("SHXYDMSFZHM") != null) {
                licenseData.put("SHXYDM", licenseData.get("SHXYDMSFZHM").toString());
            }
            // 住所地址
            licenseData.put("ZS", getAddressStr(jbxxId, 1));
            // 经营场所
            licenseData.put("JYCS", getAddressStr(jbxxId, 2));
            // 仓库地址
            licenseData.put("CKDZ", getAddressStr(jbxxId, 3));
            // 获取法定责任人
            Map<String, Object> frdbMap = dao.getByJdbc("T_FJFDA_SPJYXK_FRDB", new String[] { "JBXX_ID" },
                    new Object[] { jbxxId });
            // 获取委托人
            Map<String, Object> wtrMap = dao.getByJdbc("T_FJFDA_SPJYXK_WTDLRXX", new String[] { "JBXX_ID" },
                    new Object[] { jbxxId });
            if (frdbMap != null) {
                // 新开办
                licenseData.put("FRDB", frdbMap.get("FRXM"));
                licenseData.put("FDDBRFZR", frdbMap.get("FRXM"));
                if (frdbMap.get("FRYDDH") != null) {
                    licenseData.put("CONTACT", frdbMap.get("FRYDDH"));
                } else {
                    licenseData.put("CONTACT", frdbMap.get("FRGDDH"));
                }
            }
            if (wtrMap != null) {
                if (frdbMap.get("WTRYDDH") != null) {
                    licenseData.put("CONTACT", frdbMap.get("WTRYDDH"));
                } else {
                    licenseData.put("CONTACT", frdbMap.get("WTRGDDH"));
                }
            }
            Map<String, Object> jycsMap = dao.getByJdbc("T_FJFDA_SPJYXK_DZXX", new String[] { "JBXX_ID", "DZLB" },
                    new Object[] { jbxxId, 2 });
            String xzqhbm = jycsMap.get("XZZXXJS").toString();
            // licenseData.put("DEPART_CODE", AppUtil.getXzqhCode());
            // 平潭行政区划编码特殊处理
            if (jycsMap.get("SQZZZM").equals("350128")) {
                xzqhbm = "350128";
            } else if (jycsMap.get("SQZZZM").equals("350199")) {
                // 铁路
                xzqhbm = "350199";
            } else if (xzqhbm.equals("3509001")) {
                // 宁德东桥经济开发区
                xzqhbm = "350900";
            }
            licenseData.put("JYCSDM", xzqhbm);
            // 营业方式
            if (!licenseData.get("ZTYTDM").equals("1")) {
                licenseData.put("YYFS", "");
            }
            // 餐饮类型
            if (!licenseData.get("ZTYTDM").equals("2")) {
                licenseData.put("CYLX", "");
            }
            if (licenseData.get("WLJY").equals("1")) {
                licenseData.put("WLJY", "是");
            } else {
                licenseData.put("WLJY", "否");
                licenseData.put("WZDZ", "");
                licenseData.put("STMD", "0");
            }

            if (licenseData.get("ZYCF").equals("1")) {
                licenseData.put("ZYCF", "是");
            } else {
                licenseData.put("ZYCF", "否");
            }
            if (licenseData.get("JTYCPSDW").equals("1")) {
                licenseData.put("JTYCPSDW", "是");
            } else {
                licenseData.put("JTYCPSDW", "否");
            }
        }
        setZtytParam(licenseData);
        return licenseData;
    }

    /**
     * @param licenseData
     */
    private void setZtytParam(Map<String, Object> licenseData) {
        // 主体业态
        String ztytStr = getZtytStr(licenseData);
        licenseData.put("ZTYT", ztytStr);
        /*
         * if(licenseData.get("SHXYDMSFZHM")!=null){ try { String shxydm =
         * IDCardValidateUtil.getIdecard(licenseData.get("SHXYDMSFZHM").toString
         * ()); licenseData.put("SHXYDM", shxydm); } catch (ParseException e) {
         * ExceptionLogUtil.saveLog(e); } }
         */
        if (licenseData.get("FZRQ") != null && licenseData.get("FZRQ").toString().length() >= 10) {
            String fzrq = licenseData.get("FZRQ").toString();
            licenseData.put("FZRQ_YEAR", fzrq.substring(0, 4));
            licenseData.put("FZRQ_MONTH", fzrq.substring(5, 7));
            licenseData.put("FZRQ_DAY", fzrq.substring(8, 10));
        }
        if (licenseData.get("YXQ") != null && licenseData.get("YXQ").toString().length() >= 10) {
            String yxq = licenseData.get("YXQ").toString();
            licenseData.put("YXQZ", yxq);
            licenseData.put("YXQ_YEAR", yxq.substring(0, 4));
            licenseData.put("YXQ_MONTH", yxq.substring(5, 7));
            licenseData.put("YXQ_DAY", yxq.substring(8, 10));
        }
    }

    /**
     * 
     * 描述 设置发证日期和有效期
     * 
     * @author Usher Song
     * @created 2016年10月25日 下午3:51:46
     * @param flowVars
     * @param licenseData
     * @param itemCode
     */
    private void setLicenseDate(Map<String, Object> flowVars, Map<String, Object> licenseData, String itemCode) {

    }

    /**
     * 
     * 描述 获取经营项目
     * 
     * @author Usher Song
     * @created 2016年8月23日 上午9:34:56
     * @param jbxxId
     * @param jbxxMap
     */
    @Override
    public String getJyxmValue(String jbxxId, Map<String, Object> jbxxMap) {
        List<Map<String, Object>> jyxmList = this.finJyxmListById(jbxxId);
        StringBuilder jyxmB = new StringBuilder();
        StringBuilder jyxmDMB = new StringBuilder();
        StringBuilder specialFoodSalB = new StringBuilder();
        boolean b = true;
        for (Map<String, Object> map : jyxmList) {
            if (map.get("jyxm_value") != null) {
                String jyxmValueCode = map.get("jyxm_value").toString();
                // 排除预包装食品销售、散装食品销售、特殊食品销售一级选项
                if (!jyxmValueCode.equals("01") && !jyxmValueCode.equals("02") && !jyxmValueCode.equals("03")) {
                    if (jyxmValueCode.equals("0201") || jyxmValueCode.equals("0202")) {
                        if (jbxxMap.get("SZSSXS") != null && jbxxMap.get("SZSSXS").equals("1")) {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode));
                            jyxmB.append("（含散装熟食销售）").append("、");
                        } else {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                        }
                    } else if (jyxmValueCode.equals("0301") || jyxmValueCode.equals("0302")
                            || jyxmValueCode.equals("0303") || jyxmValueCode.equals("0304")) {
                        if (b) {
                            jyxmB.append("_TSSPXS_");
                            b = false;
                        }
                        specialFoodSalB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                    } else if (jyxmValueCode.equals("11")) {
                        if (jbxxMap.get("JZCTW") != null && jbxxMap.get("JZCTW").equals("1")) {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode));
                            jyxmB.append("（仅拆封、调味）").append("、");
                        } else {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                        }
                    } else if (jyxmValueCode.equals("13")) {
                        if (jbxxMap.get("BHDG") != null && jbxxMap.get("BHDG").equals("1")) {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode));
                            jyxmB.append("（含裱花蛋糕）").append("、");
                        } else {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                        }
                    } else if (jyxmValueCode.equals("14")) {
                        if (jbxxMap.get("ZNJZS") != null && jbxxMap.get("ZNJZS").equals("1")) {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode));
                            jyxmB.append("（含自酿酒制售）").append("、");
                        } else {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                        }
                    } else if (jyxmValueCode.equals("19")) {
                        if (jbxxMap.get("QTLSPZS") != null) {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode));
                            jyxmB.append("（" + jbxxMap.get("QTLSPZS") + "）").append("、");
                        } else {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                        }
                    } else if (jyxmValueCode.equals("09")) {
                        if (jbxxMap.get("QTLSPXS") != null) {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode));
                            jyxmB.append("（" + jbxxMap.get("QTLSPXS") + "）").append("、");
                        } else {
                            jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode)).append("、");
                        }
                    } else {
                        jyxmB.append(fdaDictionaryService.getDicNames("jyxm", jyxmValueCode) + "、");
                    }
                }
                jyxmDMB.append(jyxmValueCode + "、");
            }
        }

        if (specialFoodSalB.length() > 0) {
            String specialFoodSalStr = fdaDictionaryService.getDicNames("jyxm", "03") + "（"
                    + specialFoodSalB.toString().substring(0, specialFoodSalB.toString().lastIndexOf("、")) + "）、";
            // jyxmB.append(specialFoodSalStr);
            String mm = jyxmB.toString().replaceAll("_TSSPXS_", specialFoodSalStr);
            jyxmB = new StringBuilder(mm);
        }
        if (jyxmB.length() > 0) {
            jbxxMap.put("JYXM", jyxmB.toString().substring(0, jyxmB.toString().lastIndexOf("、")));
            jbxxMap.put("JYXMDM", jyxmDMB.toString().substring(0, jyxmDMB.toString().lastIndexOf("、")));
        }
        if (jbxxMap != null && jbxxMap.get("JYXM") != null) {
            return jbxxMap.get("JYXM").toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 描述 获取主体业态
     * 
     * @author Usher Song
     * @created 2016年6月29日 上午9:13:35
     * @param licenseData
     * @return
     */
    @Override
    public String getZtytStr(Map<String, Object> licenseData) {
        StringBuilder ztytStr = new StringBuilder();
        if (licenseData.get("ZTYTDM") != null) {
            String ztyt = fdaDictionaryService.getDicNames("ztyt", licenseData.get("ZTYTDM").toString());
            ztytStr.append(ztyt);
            String isNetworkOperators = "", isCentralKitchen = "", isMealDelivery = "";
            // 是否网络经营
            if (licenseData.get("WLJY") != null) {
                isNetworkOperators = licenseData.get("WLJY").toString();
            }
            // 是否中央厨房
            if (licenseData.get("ZYCF") != null) {
                isCentralKitchen = licenseData.get("ZYCF").toString();
            }
            // 是否集体用餐配送单位
            if (licenseData.get("JTYCPSDW") != null) {
                isMealDelivery = licenseData.get("JTYCPSDW").toString();
            }
            if (isNetworkOperators.equals("1") || isCentralKitchen.equals("1") || isMealDelivery.equals("1")) {
                ztytStr.append("(");
                boolean flag = false;
                if (isNetworkOperators.equals("1")) {
                    ztytStr.append("网络经营");
                    flag = true;
                }
                if (isCentralKitchen.equals("1")) {
                    if (flag) {
                        ztytStr.append("、");
                    }
                    ztytStr.append("中央厨房");
                    flag = true;
                }
                if (isMealDelivery.equals("1")) {
                    if (flag) {
                        ztytStr.append("、");
                    }
                    ztytStr.append("集体用餐配送");
                }
                ztytStr.append(")");
            }
            if (isNetworkOperators.equals("是") || isCentralKitchen.equals("是") || isMealDelivery.equals("是")) {
                ztytStr.append("(");
                boolean flag = false;
                if (isNetworkOperators.equals("是")) {
                    ztytStr.append("网络经营");
                    flag = true;
                }
                if (isCentralKitchen.equals("是")) {
                    if (flag) {
                        ztytStr.append("、");
                    }
                    ztytStr.append("中央厨房");
                    flag = true;
                }
                if (isMealDelivery.equals("是")) {
                    if (flag) {
                        ztytStr.append("、");
                    }
                    ztytStr.append("集体用餐配送");
                }
                ztytStr.append(")");
            }
        }
        return ztytStr.toString();
    }

    /**
     * 
     * 描述 获取经营项目列表
     * 
     * @author Usher Song
     * @created 2016年6月21日 上午10:38:17
     * @param jbxxId
     *            基本信息id
     * @return
     */
    private List<Map<String, Object>> finJyxmListById(String jbxxId) {
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_FJFDA_SPJYXK_JYXM T ");
        sql.append(" WHERE T.JBXX_ID = '");
        sql.append(jbxxId).append("' ORDER BY  t.jyxm_value asc,T.JYXM_ID DESC");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * 描述 获取地址信息
     * 
     * @author Usher Song
     * @created 2016年6月24日 上午11:56:49
     * @param jbxxId
     *            基本信息ID
     * @param 地址类型（1:住所;2:经营场所;3:仓库地址;)
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getAddressStr(String jbxxId, int dzlb) {
        Map<String, Object> addressMap = dao.getByJdbc("T_FJFDA_SPJYXK_DZXX", new String[] { "JBXX_ID", "DZLB" },
                new Object[] { jbxxId, dzlb });
        StringBuilder addressB = new StringBuilder();
        if (addressMap != null) {
            if (addressMap.get("SQZZZM") != null) {
                addressB.append(fdaDicTypeService.getByTypeCode(addressMap.get("SQZZZM").toString()).get("TYPE_NAME"));
            }
            if (addressMap.get("XZZXXJS") != null) {
                addressB.append(fdaDicTypeService.getByTypeCode(addressMap.get("XZZXXJS").toString()).get("TYPE_NAME"));
                ;
            }
            if (addressMap.get("XZJD") != null && addressMap.get("XZZXXJS") != null) {
                addressB.append(fdaDictionaryService.getDicNames(addressMap.get("XZZXXJS").toString(),
                        addressMap.get("XZJD").toString()));
            }
            /*
             * if(addressMap.get("CLNMPHM")!=null){ String clnmphm = (String)
             * addressMap.get("CLNMPHM");
             * addressB.append(StringUtil.processQuotationMarks(clnmphm)); }
             */
            int indexQZ = addressB.indexOf("泉州市泉州台商投资区");
            if (indexQZ != -1) {
                addressB.replace(indexQZ, indexQZ + "泉州市泉州台商投资区".length(), "泉州台商投资区");
            }
            int indexQZJJ = addressB.indexOf("泉州市经济技术开发区");
            if (indexQZJJ != -1) {
                addressB.replace(indexQZJJ, indexQZJJ + "泉州市经济技术开发区".length(), "泉州经济技术开发区");
            }
            int indexZZ = addressB.indexOf("漳州市漳州台商投资区");
            if (indexZZ != -1) {
                addressB.replace(indexZZ, indexZZ + "漳州市漳州台商投资区".length(), "漳州台商投资区");
            }
            int indexFZ = addressB.indexOf("福州市福州高新区");
            if (indexFZ != -1) {
                addressB.replace(indexFZ, indexFZ + "福州市福州高新区".length(), "福州高新区");
            }
        }
        if (addressB.length() > 0) {
            addressB.insert(0, "福建省");
            // 铁路特殊处理
            /*
             * if(addressMap!=null){ if(addressMap.get("SQZZZM")!=null){ String
             * SQZZZM = addressMap.get("SQZZZM").toString();
             * if(SQZZZM.equals("350199")&&addressMap.get("CLNMPHM")!=null){
             * String clnmphm = (String) addressMap.get("CLNMPHM"); addressB =
             * new StringBuilder(StringUtil.processQuotationMarks(clnmphm)); } }
             * }
             */
            return addressB.toString();
        } else {
            return null;
        }
    }

    /**
     * 描述 根据原许可编号获取Map
     * 
     * @author Faker Li
     * @created 2016年7月16日 上午9:56:25
     * @param xKZBH_OLD
     * @return
     */
    @Override
    public Map<String, Object> getSpjyxkxxMap(String xkzbhOld, String xkzzt) {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String ownProject = properties.getProperty("ownProject");
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        if (StringUtils.isNotEmpty(ownProject) && ownProject.equals("0")) {
            sql.append("from T_FJFDA_SPJYXKXX_SSWEB J ");
        } else {
            sql.append("from T_FJFDA_SPJYXKXX J ");
        }
        sql.append("WHERE J.XKZBH=? AND J.COME_FROM in ('0','1','4') ");
        params.add(xkzbhOld);
        if (StringUtils.isNotEmpty(xkzzt)) {
            // sql.append(" AND J.XKZZT = ? ");
            // params.add(xkzzt);
            sql.append(" AND J.XKZZT in  ").append(net.evecom.core.util.StringUtil.getValueArray(xkzzt));
        }
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 
     * 描述 预审后置事件
     * 
     * @author Faker Li
     * @created 2016年7月18日 下午8:44:14
     * @param flowVars
     * @return
     */
    @Override
    public Map<String, Object> afterYs(Map<String, Object> flowVars) {
        return null;
    }

    /**
     * 描述 根据退回业务受理号获取Map
     * 
     * @author Lina Lin
     * @created 2016年7月23日 上午10:45:44
     * @param oldexe_id
     * @return
     */
    public Map<String, Object> getExecutionMap(String oldexe_id) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from JBPM6_EXECUTION J ");
        sql.append("WHERE J.EXE_ID=?  ");
        sql.append("AND J.ITEM_CODE IN ( ");
        sql.append(" select WS.ITEM_CODE from T_WSBS_SERVICEITEM WS WHERE WS.CATALOG_CODE IN ( ");
        sql.append(" select T.DIC_CODE from T_MSJW_SYSTEM_DICTIONARY t where t.type_code = 'spjy') ");
        sql.append(" ) AND J.RUN_STATUS = '1' ORDER BY J.CREATE_TIME DESC");
        params.add(oldexe_id);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 
     * 描述 业务退回后置事件
     * 
     * @author Faker Li
     * @created 2016年7月23日 上午11:22:08
     * @param flowVars
     * @return
     */
    @Override
    public Map<String, Object> afterYwthHandle(Map<String, Object> flowVars) {

        if (flowVars != null && flowVars.get("EXE_ID") != null) {
            String exeId = flowVars.get("EXE_ID").toString().trim();
            Map<String, Object> exeMap = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String runStatus = exeMap.get("RUN_STATUS").toString();
            String OLDEXE_ID = (String) flowVars.get("OLDEXE_ID");
            // 正常结束
            if (runStatus.equals("2")) {
                if (StringUtils.isNotEmpty(OLDEXE_ID)) {
                    Map<String, Object> oldExeMap = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                            new Object[] { OLDEXE_ID });
                    if (oldExeMap != null) {
                        String RUN_STATUS = oldExeMap.get("RUN_STATUS").toString();
                        if (RUN_STATUS.equals("1") || RUN_STATUS.equals("0")) {
                            executionService.endByExeId(new String[] { OLDEXE_ID.trim() },
                                    "申请人申请业务退回," + AppUtil.getLoginUser().getFullname() + "强制结束该流程！");
                        }
                    }

                }
            }
        }
        return flowVars;
    }

    /**
     * 
     * 描述 注销审核通过后置事件
     * 
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> afterZXHandle(Map<String, Object> flowVars) {
        return null;
    }

    /**
     * 
     * 描述 撤销审核通过后置事件
     * 
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> afterCXHandle(Map<String, Object> flowVars) {
        return null;
    }

    /**
     * 
     * 描述 换证决定后置事件
     * 
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> afterHZHandle(Map<String, Object> flowVars) {
        return null;
    }

    /**
     * 
     * 描述 决定之后生成许可证编号
     * 
     * @author Usher Song
     * @created 2016年7月5日 下午7:23:06
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> generateJYLicenseCode(Map<String, Object> flowVars) {
        String eflowIsBack = (String) flowVars.get("EFLOW_ISBACK");
        String nodenames = (String) flowVars.get("EFLOW_NEWTASK_NODENAMES");
        // 不等于退回且产生的新流程任务环节名称为发证时
        if ((flowVars.get("EFLOW_NEWTASK_NODENAMES") != null && nodenames.equals("发证"))
                && (flowVars.get("EFLOW_ISBACK") == null || eflowIsBack.equals("false"))) {
            if (flowVars.get("JBXX_ID") != null && flowVars.get("ZTYTDM") != null) {
                String jbxxId = flowVars.get("JBXX_ID").toString();
                Map<String, Object> jbxxMap = dao.getByJdbc("T_FJFDA_SPJYXK_JBXX", new String[] { "JBXX_ID" },
                        new Object[] { jbxxId });
                if (jbxxMap.get("XKZBH") == null) {
                    String ztytdm = flowVars.get("ZTYTDM").toString();
                    flowVars.put("XKZBH", this.generateAndSaveLicenseCode(jbxxId, ztytdm));// 保存许可证编号到jbpm6_execution表
                }
            }
        }
        return flowVars;
    }

    /**
     * 
     * 描述 生成和保存食品经营许可证编号
     * 
     * @author Usher Song
     * @created 2016年8月1日 上午10:28:20
     * @param jbxxId
     * @param ztytdm
     */
    @Override
    public String generateAndSaveLicenseCode(String jbxxId, String ztytdm) {
        return null;
    }

    /**
     * 
     * 描述 生成许可证编号
     * 
     * @author Usher Song
     * @created 2016年6月21日 下午3:10:01
     * @param ztytCode
     *            主体业态代码
     * @param xzqhbm
     *            行政区划编码
     * @return
     */
    @Override
    public String generateLicenseCode(String ztytCode, String xzqhbm, String orderCode) {
        return null;
    }

    /**
     * 
     * 描述 更新许可证状态（10有效 20过期 30被撤销 40被吊销 50被撤回 60注销）
     * 
     * @author Usher Song
     * @created 2016年7月5日 下午7:27:49
     * @param punishId
     * @param punishStatue
     */
    @Override
    public void updateLicenseState(String xkzbh, String state) {

    }

    /**
     * 描述 获取社会信用代码回填数据
     * 
     * @author Faker Li
     * @created 2016年7月27日 上午9:35:39
     * @param sHXYDMSFZHM
     * @return
     */
    @Override
    public Map<String, Object> getCreditCodeData(String shxydmsfzhm) {
        //getCreditCodeData
        /*List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * ");
        sql.append("from FDAEFS_ENT_INFO J ");
        sql.append("WHERE J.SOCIAL_CREDIT_UNICODE=? AND J.IS_VALID = '1' ");
        params.add(shxydmsfzhm);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return null != list && list.size() > 0 ? list.get(0) : null;*/
        return null;
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年7月28日 上午10:22:36
     * @param shxydmsfzhm
     * @param itemCode
     * @return
     */
    @Override
    public List<Map<String, Object>> isExistShxydm(String shxydmsfzhm, String itemCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.EXE_ID FROM JBPM6_EXECUTION T ");
        sql.append(" LEFT JOIN T_FJFDA_SPJYXK_JBXX J ON T.BUS_RECORDID = J.JBXX_ID ");
        sql.append(" WHERE T.BUS_TABLENAME = 'T_FJFDA_SPJYXK_JBXX' ");
        sql.append(" AND T.ITEM_CODE IS NOT NULL AND J.SHXYDMSFZHM = ? ");
        sql.append(" AND T.ITEM_CODE in  ").append(StringUtil.getValueArray(itemCode));
        sql.append(" AND T.RUN_STATUS != 0 ");
        params.add(shxydmsfzhm);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述 获取旧换证补数据
     * 
     * @author Faker Li
     * @created 2016年8月12日 下午5:00:46
     * @param string
     * @param string2
     * @return
     */
    @Override
    public Map<String, Object> getPatchSpjyxx(String xkzbm) {
        StringBuffer ltsql = new StringBuffer("select t.jyzmc,t.jycs as JYXXDZ,T.FZR as FRXM from T_FJFDA_SPLTXKXX t"
                + " where t.XKZZT in (10,80) and t.xkzbh = ? and rownum = 1 ");
        Map<String, Object> ltmap = dao.getByJdbc(ltsql.toString(), new Object[] { xkzbm });
        if (ltmap != null) {
            return ltmap;
        }
        StringBuffer cysql = new StringBuffer("select t.jyzmc,t.jycs as JYXXDZ,T.FDDBRFZR as FRXM "
                + "from T_FJFDA_SPCYXKXX t" + " where t.XKZZT in (10,80) and t.xkzbh = ? and rownum = 1 ");
        Map<String, Object> cymap = dao.getByJdbc(cysql.toString(), new Object[] { xkzbm });
        if (cymap != null) {
            return cymap;
        }
        return null;
    }

    /**
     * 描述 验证许可信息
     * 
     * @author Faker Li
     * @created 2016年8月12日 下午5:35:27
     * @param xKZBH_OLD
     * @param string
     * @return
     */
    @Override
    public Map<String, Object> getSpjyxkxxMapForValidateXkzbh(String xkzbhOld, String string) {
        Map<String, Object> xzMap = this.getSpjyxkxxMap(xkzbhOld, string);
        if (xzMap != null) {
            return xzMap;
        }
        StringBuffer ltsql = new StringBuffer("select t.JYZMC,t.jycs,T.FZR as FDDBRFZR" + " from T_FJFDA_SPLTXKXX t"
                + " where t.XKZZT in (10,20,80) and t.xkzbh = ? and rownum = 1 ");
        Map<String, Object> ltmap = dao.getByJdbc(ltsql.toString(), new Object[] { xkzbhOld });
        if (ltmap != null) {
            return ltmap;
        }
        StringBuffer cysql = new StringBuffer("select t.jyzmc,t.jycs,T.FDDBRFZR,T.PHONE  from T_FJFDA_SPCYXKXX t"
                + " where t.XKZZT in (10,20,80) and t.xkzbh = ? and rownum = 1 ");
        Map<String, Object> cymap = dao.getByJdbc(cysql.toString(), new Object[] { xkzbhOld });
        if (cymap != null) {
            return cymap;
        }
        return null;
    }

    /**
     * 
     * 描述 保存证照图片路径
     * 
     * @author Usher Song
     * @created 2016年7月11日 上午9:17:51
     * @param jbxxId
     * @param filePath
     */
    @Override
    public void saveImagePath(String jbxxId, String filePath) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" UPDATE T_FJFDA_SPJYXKXX X SET ");
        sql.append(" X.IMAGE_PATH =? WHERE X.JBXX_ID = ? ");
        params.add(filePath);
        params.add(jbxxId);
        dao.executeSql(sql.toString(), params.toArray());
    }

    /**
     * 
     * 描述
     * 
     * @author Usher Song
     * @created 2016年9月14日 上午9:06:59
     * @param string
     * @param string2
     * @return
     */
    @Override
    public Map<String, Object> getSpjyOldMap(String xkzbhOld, String string2) {
        Map<String, Object> xzMap = this.getSpjyxkxxMap(xkzbhOld, "");
        if (xzMap != null) {
            return xzMap;
        }
        StringBuffer ltsql = new StringBuffer("select t.JYZMC,t.jycs,T.FZR as FDDBRFZR" + " from T_FJFDA_SPLTXKXX t"
                + " where  t.xkzbh = ? and rownum = 1 ");
        Map<String, Object> ltmap = dao.getByJdbc(ltsql.toString(), new Object[] { xkzbhOld });
        if (ltmap != null) {
            return ltmap;
        }
        StringBuffer cysql = new StringBuffer("select t.jyzmc,t.jycs,T.FDDBRFZR,T.PHONE  from T_FJFDA_SPCYXKXX t"
                + " where t.xkzbh = ? and rownum = 1 ");
        Map<String, Object> cymap = dao.getByJdbc(cysql.toString(), new Object[] { xkzbhOld });
        if (cymap != null) {
            return cymap;
        }
        return null;
    }

    /**
     * 
     * 描述 保存数据到证照表
     * 
     * @author Usher Song
     * @created 2016年10月25日 下午5:59:15
     * @param licenseData
     * @param printConfigMap
     */
    @SuppressWarnings("unchecked")
    @Override
    public String saveOrUpdateToResult(Map<String, Object> licenseData, Map<String, Object> printConfigMap) {
        licenseData.put("XKZZT", "10");
        String jbxxId = (String) licenseData.get("JBXX_ID");
        // 把监督管理机构、监督管理人员、发证机关、签发人、许可证编号数据保存到业务表
        printConfigMap.put("XKZBH", licenseData.get("XKZBH"));
        dao.saveOrUpdate(printConfigMap, "T_FJFDA_SPJYXK_JBXX", jbxxId);
        licenseData.put("DEPART_ID", AppUtil.getLoginUser().getDepId());
        // licenseData.put("DEPART_CODE", AppUtil.getXzqhCode());
        String zzxxId = "";
        Map<String, Object> resultMap = dao.getByJdbc("T_FJFDA_SPJYXKXX", new String[] { "EFLOW_EXEID" },
                new Object[] { licenseData.get("EFLOW_EXEID") });

        if (resultMap != null) {
            zzxxId = (String) resultMap.get("SPJYXKXX_ID");
        }

        // 如果是同一个流程，直接更新证照表
        if (StringUtils.isNotEmpty(zzxxId)) {
            licenseData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dao.saveOrUpdate(licenseData, "T_FJFDA_SPJYXKXX", zzxxId);
        } else {
            resultMap = dao.getByJdbc("T_FJFDA_SPJYXKXX", new String[] { "XKZBH" },
                    new Object[] { (String) licenseData.get("XKZBH") });
            if (resultMap != null) {
                if (licenseData.get("XKZBH_OLD") != null) {
                    // licensePrintService.saveAndUpdateResultRecord(licenseData,
                    // "XKZBH",
                    // licenseData.get("XKZBH_OLD").toString(),"T_FJFDA_SPJYXKXX");
                }
                zzxxId = (String) resultMap.get("SPJYXKXX_ID");
            } else {
                zzxxId = dao.saveOrUpdate(licenseData, "T_FJFDA_SPJYXKXX", null);
            }
        }

        return zzxxId;

    }

    /**
     * 
     * 描述 修改许可证编号
     * 
     * @author Usher Song
     * @created 2016年10月26日 上午12:10:30
     * @param licenseData
     */
    @Override
    public String generateNewXkzbh(Map<String, Object> licenseData) {
        String jbxxId = licenseData.get("JBXX_ID").toString();
        String ztytdm = licenseData.get("ZTYTDM").toString();
        String xkzbh = licenseData.get("XKZBH").toString();
        // JY23501020003669
        String xzqhbm = xkzbh.substring(3, 9);
        String orderCode = xkzbh.substring(9, 15);
        String newXkzbh = this.generateLicenseCode(ztytdm, xzqhbm, orderCode);

        StringBuffer sql1 = new StringBuffer(" UPDATE T_FJFDA_SPJYXK_JBXX T SET ");
        sql1.append(" T.XKZBH ='" + newXkzbh + "' WHERE T.JBXX_ID = '" + jbxxId + "'");
        dao.executeSql(sql1.toString(), null);

        StringBuffer sql2 = new StringBuffer(" UPDATE T_FJFDA_ORDERCODE T SET ");
        sql2.append(" T.XKZBH ='" + newXkzbh + "' WHERE T.XKZBH = '" + xkzbh + "'");
        dao.executeSql(sql2.toString(), null);

        return newXkzbh;

    }

    /**
     * 描述 回填食品经营新开办数据
     * 
     * @author Faker Li
     * @created 2016年11月14日 上午11:30:47
     * @param vars
     * @return
     */
    @Override
    public Map<String, Object> setWordValuesForXKB(Map<String, Object> vars) {
        Map<String, Object> result = new HashMap<String, Object>();
        String exeId = (String) vars.get("RECORD_ID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null) {
            String ITEM_CODE = (String) flowExe.get("ITEM_CODE");
            String busTableName = (String) flowExe.get("BUS_TABLENAME");
            String busRecordId = (String) flowExe.get("BUS_RECORDID");
            String pkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> EFLOW_BUSRECORD = executionService.getByJdbc(busTableName, new String[] { pkName },
                    new Object[] { busRecordId });
            setWordJbxx(busRecordId, EFLOW_BUSRECORD, result, "");
            setWordFrdb(busRecordId, EFLOW_BUSRECORD, result, "");
            setWordSpaqry(busRecordId, EFLOW_BUSRECORD, result, "");
            setWordCyry(busRecordId, EFLOW_BUSRECORD, result, "");
            setWordSmxx(busRecordId, EFLOW_BUSRECORD, result, "");
            setWordWtrxx(busRecordId, EFLOW_BUSRECORD, result, "");
            if (ITEM_CODE.equals("001XK00103")) {
                setWordValuesForBg(result, EFLOW_BUSRECORD);
            } else if (ITEM_CODE.equals("001XK00102")) {
                if (EFLOW_BUSRECORD.get("YXQZ") != null
                        && StringUtils.isNotEmpty((String) EFLOW_BUSRECORD.get("YXQZ"))) {
                    result.put("YXQZ", DateTimeUtil.formatDateStr((String) EFLOW_BUSRECORD.get("YXQZ"), "yyyy-MM-dd",
                            "yyyy年MM月dd日"));
                }
                setEasyValue(EFLOW_BUSRECORD, result, "", "BJSBYWBH");
                result.put("XKYX_YEARNUM", EFLOW_BUSRECORD.get("XKYX_YEARNUM"));
                result.put("XKYX_MONTHNUM", EFLOW_BUSRECORD.get("XKYX_MONTHNUM"));
                result.put("XKZBH_OLD", EFLOW_BUSRECORD.get("XKZBH_OLD"));
                result.put("FZJG", EFLOW_BUSRECORD.get("FZJG"));
            } else if (ITEM_CODE.equals("001XK00108")) {
                result.put("XKZBH_OLD", EFLOW_BUSRECORD.get("XKZBH_OLD"));
            }

        }
        result.put("CURRENT_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return result;
    }

    /**
     * 描述 回填食品经营变更数据
     * 
     * @author Faker Li
     * @created 2016年11月15日 下午6:00:43
     * @param result
     * @param EFLOW_BUSRECORD
     */
    public void setWordValuesForBg(Map<String, Object> result, Map<String, Object> EFLOW_BUSRECORD) {
        String jbxxId = "-1";
        Map<String, Object> jbxxMap = new HashMap<String, Object>();
        String XKZBH_OLD = (String) EFLOW_BUSRECORD.get("XKZBH_OLD");
        if (StringUtils.isNotEmpty(XKZBH_OLD)) {
            result.put("XKZBH_OLD", XKZBH_OLD);
            Map<String, Object> xpjyxkxxMap = this.getSpjyxkxxMap(XKZBH_OLD, "10");
            if (null != xpjyxkxxMap) {
                jbxxId = xpjyxkxxMap.get("JBXX_ID") == null ? "" : xpjyxkxxMap.get("JBXX_ID").toString();
                if (StringUtils.isNotEmpty(jbxxId)) {
                    jbxxMap = this.getByJdbc("T_FJFDA_SPJYXK_JBXX", new String[] { "JBXX_ID" },
                            new Object[] { jbxxId });
                    if (null == jbxxMap) {
                        jbxxMap = new HashMap<String, Object>();
                    }
                } else {
                    jbxxMap.putAll(xpjyxkxxMap);
                    result.put("HTY_ZSDZ", xpjyxkxxMap.get("ZS"));
                    result.put("HTY_JYCSDZ", xpjyxkxxMap.get("JYCS"));
                    result.put("HTY_CKDZ", xpjyxkxxMap.get("CKDZ"));
                }
            }
        }
        setWordJbxx(jbxxId, jbxxMap, result, "HTY_");
        setWordFrdb(jbxxId, jbxxMap, result, "HTY_");
        setWordSpaqry(jbxxId, jbxxMap, result, "HTY_");
        setWordCyry(jbxxId, jbxxMap, result, "HTY_");
        setWordSmxx(jbxxId, jbxxMap, result, "HTY_");
        setWordWtrxx(jbxxId, jbxxMap, result, "HTY_");
        if (EFLOW_BUSRECORD.get("YXQZ") != null && StringUtils.isNotEmpty((String) EFLOW_BUSRECORD.get("YXQZ"))) {
            result.put("YXQZ",
                    DateTimeUtil.formatDateStr((String) EFLOW_BUSRECORD.get("YXQZ"), "yyyy-MM-dd", "yyyy年MM月dd日"));
        }
        setNoValueCheckbox(result, "", "SFSQYX", new String[] { "A", "B" });
        if (EFLOW_BUSRECORD.get("SFSQYX") != null) {
            String SFSQYX = (String) EFLOW_BUSRECORD.get("SFSQYX");
            if (SFSQYX.equals("1")) {
                result.put("SFSQYX_A", "☑");
                result.put("XKYX_YEARNUM", EFLOW_BUSRECORD.get("XKYX_YEARNUM"));
                result.put("XKYX_MONTHNUM", EFLOW_BUSRECORD.get("XKYX_MONTHNUM"));
            } else if (SFSQYX.equals("0")) {
                result.put("SFSQYX_B", "☑");
            }
        }
        String HTY_CKDZ = (String) result.get("HTY_CKDZ");
        String CKDZ = (String) result.get("CKDZ");
        setNoValueCheckbox(result, "", "BGCKDZ", new String[] { "A", "B" });
        if (StringUtils.isNotEmpty(HTY_CKDZ) && StringUtils.isEmpty(CKDZ)) {
            result.put("BGCKDZ_A", "☑");
        } else if (StringUtils.isEmpty(HTY_CKDZ) && StringUtils.isNotEmpty(CKDZ)) {
            result.put("BGCKDZ_A", "☑");
        } else if (StringUtils.isEmpty(HTY_CKDZ) && StringUtils.isEmpty(CKDZ)) {
            result.put("BGCKDZ_B", "☑");
        } else if (!HTY_CKDZ.equals(CKDZ)) {
            result.put("BGCKDZ_A", "☑");
        } else {
            result.put("BGCKDZ_B", "☑");
        }
    }

    /**
     * 描述 设置委托人信息
     * 
     * @author Faker Li
     * @created 2016年11月15日 下午4:40:46
     * @param busRecordId
     * @param eFLOW_BUSRECORD
     * @param result
     * @param string
     */
    private void setWordWtrxx(String busRecordId, Map<String, Object> eFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        Map<String, Object> clinetMap = this.getClinetMap(busRecordId);
        if (clinetMap != null) {
            result.put(prefix + "WTDLR", clinetMap.get("WTDLR"));
            result.put(prefix + "WTR", clinetMap.get("WTR"));
            if (clinetMap.get("WTRZJLX") != null && StringUtils.isNotEmpty((String) clinetMap.get("WTRZJLX"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zjlx", (String) clinetMap.get("WTRZJLX"));
                result.put("WTRZJLX", dicName);
            }
            result.put(prefix + "WTRZJH", clinetMap.get("WTRZJH"));
            result.put(prefix + "WTKSSJ", clinetMap.get("WTKSSJ"));
            result.put(prefix + "WTJSSJ", clinetMap.get("WTJSSJ"));
            result.put(prefix + "WTRGDDH", clinetMap.get("WTRGDDH"));
            result.put(prefix + "WTRYDDH", clinetMap.get("WTRYDDH"));
            result.put(prefix + "WTQTCZQX", clinetMap.get("WTQTCZQX"));

            // 是否 修改自备材料中的修改材料
            setEasyValue(clinetMap, result, prefix, "WTXGCLQX");
            setEasyValue(clinetMap, result, prefix, "WTXGBGQX");
            setEasyValue(clinetMap, result, prefix, "WTLQZSQX");
            setEasyValue(clinetMap, result, prefix, "WTHDQX");
        }

    }

    /**
     * 描述 设置设备信息
     * 
     * @author Faker Li
     * @created 2016年11月15日 下午4:32:26
     * @param busRecordId
     * @param eFLOW_BUSRECORD
     * @param result
     * @param string
     */
    private void setWordSmxx(String busRecordId, Map<String, Object> eFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        List<Map<String, Object>> facilityEquipmentList = this.findFacilityEquipment(busRecordId);
        if (facilityEquipmentList == null || facilityEquipmentList.size() == 0) {
            facilityEquipmentList = new ArrayList<Map<String, Object>>();
            Map<String, Object> m = new HashMap<String, Object>();
            facilityEquipmentList.add(m);
        }
        result.put(prefix + "fList", facilityEquipmentList);
    }

    /**
     * 描述 设置从业人员
     * 
     * @author Faker Li
     * @created 2016年11月15日 下午4:14:22
     * @param busRecordId
     * @param eFLOW_BUSRECORD
     * @param result
     * @param string
     */
    private void setWordCyry(String busRecordId, Map<String, Object> eFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        List<Map<String, Object>> practitionersList = this.findPersonel(busRecordId, "2");
        if (practitionersList == null || practitionersList.size() == 0) {
            practitionersList = new ArrayList<Map<String, Object>>();
            Map<String, Object> m = new HashMap<String, Object>();
            practitionersList.add(m);
        }
        for (int i = 0; i < practitionersList.size(); i++) {
            Map<String, Object> p = practitionersList.get(i);
            if (p.get("RYXB") != null && StringUtils.isNotEmpty((String) p.get("RYXB"))) {
                String dicName = this.fdaDictionaryService.getDicNames("sex", (String) p.get("RYXB"));
                p.put("RYXB", dicName);
            }
            if (p.get("RYMZ") != null && StringUtils.isNotEmpty((String) p.get("RYMZ"))) {
                String dicName = this.fdaDictionaryService.getDicNames("mzdm", (String) p.get("RYMZ"));
                p.put("RYMZ", dicName);
            }
            if (p.get("RYZW") != null && StringUtils.isNotEmpty((String) p.get("RYZW"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zwdm", (String) p.get("RYZW"));
                p.put("RYZW", dicName);
            }
            if (p.get("RYZJLX") != null && StringUtils.isNotEmpty((String) p.get("RYZJLX"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zjlx", (String) p.get("RYZJLX"));
                p.put("RYZJLX", dicName);
            }
        }
        result.put(prefix + "tPractitionersList", practitionersList);
    }

    /**
     * 描述 设置食品安全人员
     * 
     * @author Faker Li
     * @created 2016年11月15日 下午3:05:48
     * @param busRecordId
     * @param eFLOW_BUSRECORD
     * @param result
     * @param string
     */
    private void setWordSpaqry(String busRecordId, Map<String, Object> eFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        // 获取技术人员列表
        List<Map<String, Object>> technicalPersonnelList = this.findPersonel(busRecordId, "1");
        if (technicalPersonnelList == null || technicalPersonnelList.size() == 0) {
            technicalPersonnelList = new ArrayList<Map<String, Object>>();
            Map<String, Object> m = new HashMap<String, Object>();
            technicalPersonnelList.add(m);
        }
        for (int i = 0; i < technicalPersonnelList.size(); i++) {
            Map<String, Object> p = technicalPersonnelList.get(i);
            if (p.get("RYXB") != null && StringUtils.isNotEmpty((String) p.get("RYXB"))) {
                String dicName = this.fdaDictionaryService.getDicNames("sex", (String) p.get("RYXB"));
                p.put("RYXB", dicName);
            }
            if (p.get("RYMZ") != null && StringUtils.isNotEmpty((String) p.get("RYMZ"))) {
                String dicName = this.fdaDictionaryService.getDicNames("mzdm", (String) p.get("RYMZ"));
                p.put("RYMZ", dicName);
            }
            if (p.get("RYZW") != null && StringUtils.isNotEmpty((String) p.get("RYZW"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zwdm", (String) p.get("RYZW"));
                p.put("RYZW", dicName);
            }
            if (p.get("RYZJLX") != null && StringUtils.isNotEmpty((String) p.get("RYZJLX"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zjlx", (String) p.get("RYZJLX"));
                p.put("RYZJLX", dicName);
            }
        }
        result.put(prefix + "tPersonList", technicalPersonnelList);
    }

    /**
     * 描述 设置法人信息回填值
     * 
     * @author Faker Li
     * @created 2016年11月15日 下午2:49:31
     * @param busRecordId
     * @param eFLOW_BUSRECORD
     * @param result
     * @param string
     */
    private void setWordFrdb(String busRecordId, Map<String, Object> EFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        // 获取法人代表信息
        Map<String, Object> legal = this.getLegalRepresentative(busRecordId);
        if (legal != null) {
            result.put(prefix + "FRXM", legal.get("FRXM"));
            result.put(prefix + "FRHJDJZZ", legal.get("FRHJDJZZ"));
            result.put(prefix + "FRZJH", legal.get("FRZJH"));
            result.put(prefix + "FRGDDH", legal.get("FRGDDH"));
            result.put(prefix + "FRYDDH", legal.get("FRYDDH"));
            if (legal.get("FRXB") != null && StringUtils.isNotEmpty((String) legal.get("FRXB"))) {
                String dicName = this.fdaDictionaryService.getDicNames("sex", (String) legal.get("FRXB"));
                result.put(prefix + "FRXB", dicName);
            }
            if (legal.get("FRMZ") != null && StringUtils.isNotEmpty((String) legal.get("FRMZ"))) {
                String dicName = this.fdaDictionaryService.getDicNames("mzdm", (String) legal.get("FRMZ"));
                result.put(prefix + "FRMZ", dicName);
            }
            if (legal.get("FRZW") != null && StringUtils.isNotEmpty((String) legal.get("FRZW"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zwdm", (String) legal.get("FRZW"));
                result.put(prefix + "FRZW", dicName);
            }
            if (legal.get("FRZJLX") != null && StringUtils.isNotEmpty((String) legal.get("FRZJLX"))) {
                String dicName = this.fdaDictionaryService.getDicNames("zjlx", (String) legal.get("FRZJLX"));
                result.put(prefix + "FRZJLX", dicName);
            }
        }
    }

    /**
     * 描述 设置基本信息回填值
     * 
     * @author Faker Li
     * @created 2016年11月14日 下午3:15:37
     * @param eFLOW_BUSRECORD
     * @param result
     */
    private void setWordJbxx(String busRecordId, Map<String, Object> EFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        result.put(prefix + "JYZMC", EFLOW_BUSRECORD.get("JYZMC"));
        result.put(prefix + "SHXYDMSFZHM", EFLOW_BUSRECORD.get("SHXYDMSFZHM"));
        if (StringUtils.isNotEmpty(busRecordId) && !busRecordId.equals("-1")) {
            StringBuffer zsdz = new StringBuffer("福建省");
            StringBuffer jycsdz = new StringBuffer("福建省");
            StringBuffer ckdz = new StringBuffer("福建省");
            setWordDzxx(zsdz, busRecordId, "1");
            setWordDzxx(jycsdz, busRecordId, "2");
            setWordDzxx(ckdz, busRecordId, "3");
            // 设置三个地址
            result.put(prefix + "ZSDZ", zsdz.toString());
            result.put(prefix + "JYCSDZ", jycsdz.toString());
            if (!ckdz.toString().trim().equals("福建省")) {
                result.put(prefix + "CKDZ", ckdz.toString());
            } else {
                result.put(prefix + "CKDZ", "");
            }

        }
        // 设置主体业态信息
        setZtytxx(EFLOW_BUSRECORD, result, prefix);
        // 设置经营项目信息
        setJyxmxx(busRecordId, EFLOW_BUSRECORD, result, prefix);
        result.put(prefix + "SQFBS", EFLOW_BUSRECORD.get("SQFBS"));
        result.put(prefix + "YXQ", EFLOW_BUSRECORD.get("YXQ"));
        result.put(prefix + "ZGRS", EFLOW_BUSRECORD.get("ZGRS"));
        result.put(prefix + "YTJRS", EFLOW_BUSRECORD.get("YTJRS"));
        result.put(prefix + "YZBM", EFLOW_BUSRECORD.get("YZBM"));
        result.put(prefix + "EMAIL", EFLOW_BUSRECORD.get("EMAIL"));
        setNoValueCheckbox(result, prefix, "JJXZ", new String[] { "A", "B", "C", "D" });
        // 设置经营行政
        if (EFLOW_BUSRECORD.get("JJXZ") != null) {
            String JJXZ = (String) EFLOW_BUSRECORD.get("JJXZ");
            if (JJXZ.equals("1")) {
                result.put(prefix + "JJXZ_A", "☑");
            } else if (JJXZ.equals("2")) {
                result.put(prefix + "JJXZ_B", "☑");
            } else if (JJXZ.equals("3")) {
                result.put(prefix + "JJXZ_C", "☑");
            } else if (JJXZ.equals("4")) {
                result.put(prefix + "JJXZ_D", "☑");
            }
        }
    }

    /**
     * 描述 设置经营项目信息
     * 
     * @author Faker Li
     * @created 2016年11月15日 上午10:22:09
     * @param eFLOW_BUSRECORD
     * @param result
     * @param prefix
     */
    private void setJyxmxx(String busRecordId, Map<String, Object> EFLOW_BUSRECORD, Map<String, Object> result,
            String prefix) {
        Map<String, Object> ybzspxs = this.getYxm(busRecordId, "YBZSPXS");
        Map<String, Object> szspxs = this.getYxm(busRecordId, "SZSPXS");
        Map<String, Object> tsspxs = this.getYxm(busRecordId, "TSSPXS");
        Map<String, Object> dllspzs = this.getYxm(busRecordId, "DLLSPZS");
        setNoValueCheckbox(result, prefix, "YBZSPXS", new String[] { "A", "B" });
        if (ybzspxs != null) {
            String JYXM_VALUE = (String) ybzspxs.get("JYXM_VALUE");
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.equals("0101")) {
                result.put(prefix + "YBZSPXS_A", "☑");
                result.put(prefix + "YBZSPXS_B", "□");
            } else if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.equals("0102")) {
                result.put(prefix + "YBZSPXS_A", "□");
                result.put(prefix + "YBZSPXS_B", "☑");
            }
        }
        setNoValueCheckbox(result, prefix, "SZSPXS", new String[] { "A", "B" });
        if (szspxs != null) {
            String JYXM_VALUE = (String) szspxs.get("JYXM_VALUE");
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.equals("0201")) {
                result.put(prefix + "SZSPXS_A", "☑");
                result.put(prefix + "SZSPXS_B", "□");
            } else if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.equals("0202")) {
                result.put(prefix + "SZSPXS_A", "□");
                result.put(prefix + "SZSPXS_B", "☑");
            }
        }
        setNoValueCheckbox(result, prefix, "TSSPXS", new String[] { "A", "B", "C", "D" });
        if (tsspxs != null) {
            String JYXM_VALUE = (String) tsspxs.get("JYXM_VALUE");
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("0301") > -1) {
                result.put(prefix + "TSSPXS_A", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("0302") > -1) {
                result.put(prefix + "TSSPXS_B", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("0303") > -1) {
                result.put(prefix + "TSSPXS_C", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("0304") > -1) {
                result.put(prefix + "TSSPXS_D", "☑");
            }
        }
        setNoValueCheckbox(result, prefix, "DLLSPZS",
                new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" });
        if (dllspzs != null) {
            String JYXM_VALUE = (String) dllspzs.get("JYXM_VALUE");
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("01") > -1) {
                result.put(prefix + "DLLSPZS_A", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("02") > -1) {
                result.put(prefix + "DLLSPZS_B", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("03") > -1) {
                result.put(prefix + "DLLSPZS_C", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("09") > -1) {
                result.put(prefix + "DLLSPZS_D", "☑");
                result.put(prefix + "QTLSPXS", EFLOW_BUSRECORD.get("QTLSPXS"));
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("10") > -1) {
                result.put(prefix + "DLLSPZS_E", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("11") > -1) {
                result.put(prefix + "DLLSPZS_F", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("12") > -1) {
                result.put(prefix + "DLLSPZS_G", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("13") > -1) {
                result.put(prefix + "DLLSPZS_H", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("14") > -1) {
                result.put(prefix + "DLLSPZS_I", "☑");
            }
            if (StringUtils.isNotEmpty(JYXM_VALUE) && JYXM_VALUE.indexOf("19") > -1) {
                result.put(prefix + "DLLSPZS_J", "☑");
                result.put(prefix + "QTLSPZS", EFLOW_BUSRECORD.get("QTLSPZS"));
            }
        }
        // 是否含散装熟食销售
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "SZSSXS");
        // 是否含自酿酒制售
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "ZNJZS");
        // 仅拆封、调味
        setNoValueCheckbox(result, prefix, "JZCTW", new String[] { "A" });
        if (EFLOW_BUSRECORD.get("JZCTW") != null) {
            result.put(prefix + "JZCTW_A", "☑");
        }
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2016年11月14日 下午5:28:09
     * @param EFLOW_BUSRECORD
     * @param result
     * @param prefix
     */
    public void setZtytxx(Map<String, Object> EFLOW_BUSRECORD, Map<String, Object> result, String prefix) {
        // 设置销售类型
        if (EFLOW_BUSRECORD.get("YYFS") != null) {
            String YYFS = (String) EFLOW_BUSRECORD.get("YYFS");
            if (YYFS.equals("1")) {
                result.put(prefix + "YYFS_A", "☑");
                result.put(prefix + "YYFS_B", "□");
                result.put(prefix + "YYFS_C", "□");
            } else if (YYFS.equals("2")) {
                result.put(prefix + "YYFS_A", "□");
                result.put(prefix + "YYFS_B", "☑");
                result.put(prefix + "YYFS_C", "□");
            } else if (YYFS.equals("3")) {
                result.put(prefix + "YYFS_A", "□");
                result.put(prefix + "YYFS_B", "□");
                result.put(prefix + "YYFS_C", "☑");
            }
        } else {
            setNoValueCheckbox(result, prefix, "YYFS", new String[] { "A", "B", "C" });
        }
        // 设置餐饮类型
        if (EFLOW_BUSRECORD.get("CYLX") != null) {
            String CYLX = (String) EFLOW_BUSRECORD.get("CYLX");
            if (CYLX.equals("1")) {
                result.put(prefix + "CYLX_A", "☑");
                result.put(prefix + "CYLX_B", "□");
                result.put(prefix + "CYLX_C", "□");
                result.put(prefix + "CYLX_D", "□");
            } else if (CYLX.equals("2")) {
                result.put(prefix + "CYLX_A", "□");
                result.put(prefix + "CYLX_B", "☑");
                result.put(prefix + "CYLX_C", "□");
                result.put(prefix + "CYLX_D", "□");
            } else if (CYLX.equals("3")) {
                result.put(prefix + "CYLX_A", "□");
                result.put(prefix + "CYLX_B", "□");
                result.put(prefix + "CYLX_C", "☑");
                result.put(prefix + "CYLX_D", "□");
            } else if (CYLX.equals("4")) {
                result.put(prefix + "CYLX_A", "□");
                result.put(prefix + "CYLX_B", "□");
                result.put(prefix + "CYLX_C", "□");
                result.put(prefix + "CYLX_D", "☑");
            }
        } else {
            setNoValueCheckbox(result, prefix, "CYLX", new String[] { "A", "B", "C", "D" });
        }
        // 设置单位食堂
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "SFWXXST");
        // 设置主体业态
        if (EFLOW_BUSRECORD.get("ZTYTDM") != null) {
            String ZTYTDM = (String) EFLOW_BUSRECORD.get("ZTYTDM");
            if (ZTYTDM.equals("1")) {
                result.put(prefix + "ZTYTDM_A", "☑");
                result.put(prefix + "ZTYTDM_B", "□");
                result.put(prefix + "ZTYTDM_C", "□");
                setNoValueCheckbox(result, prefix, "CYLX", new String[] { "A", "B", "C", "D" });
                setNoValueCheckbox(result, prefix, "SFWXXST", new String[] { "A", "B" });
            } else if (ZTYTDM.equals("2")) {
                result.put(prefix + "ZTYTDM_A", "□");
                result.put(prefix + "ZTYTDM_B", "☑");
                result.put(prefix + "ZTYTDM_C", "□");
                setNoValueCheckbox(result, prefix, "YYFS", new String[] { "A", "B", "C" });
                setNoValueCheckbox(result, prefix, "SFWXXST", new String[] { "A", "B" });
            } else if (ZTYTDM.equals("3")) {
                result.put(prefix + "ZTYTDM_A", "□");
                result.put(prefix + "ZTYTDM_B", "□");
                result.put(prefix + "ZTYTDM_C", "☑");
                setNoValueCheckbox(result, prefix, "YYFS", new String[] { "A", "B", "C" });
                setNoValueCheckbox(result, prefix, "CYLX", new String[] { "A", "B", "C", "D" });
            }
        } else {
            setNoValueCheckbox(result, prefix, "ZTYTDM", new String[] { "A", "B", "C" });
        }
        // 设置实体门店
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "STMD");
        // 设置网络经营
        if (EFLOW_BUSRECORD.get("WLJY") != null) {
            String WLJY = (String) EFLOW_BUSRECORD.get("WLJY");
            if (WLJY.equals("1")) {
                result.put(prefix + "WLJY_A", "☑");
                result.put(prefix + "WLJY_B", "□");
                result.put(prefix + "WZDZ", EFLOW_BUSRECORD.get("WZDZ"));
            } else if (WLJY.equals("0")) {
                result.put(prefix + "WLJY_A", "□");
                result.put(prefix + "WLJY_B", "☑");
                result.put(prefix + "WZDZ", "   ");
                setNoValueCheckbox(result, prefix, "STMD", new String[] { "A", "B" });
            }
        } else {
            setNoValueCheckbox(result, prefix, "WLJY", new String[] { "A", "B" });
        }
        // 设置中央厨房
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "ZYCF");
        // 设置用餐配送单位
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "JTYCPSDW");
        // 利用自动售货设备从事食品销售
        setEasyValue(EFLOW_BUSRECORD, result, prefix, "LYZDSHSBCSSPXS");
    }

    /**
     * 描述 设置空复选框
     * 
     * @author Faker Li
     * @created 2016年11月15日 上午9:50:54
     * @param result
     * @param prefix
     */
    public void setNoValueCheckbox(Map<String, Object> result, String prefix, String fileName, String[] snum) {
        for (int i = 0; i < snum.length; i++) {
            result.put(prefix + fileName + "_" + snum[i], "□");
        }
    }

    /**
     * 描述 设置简单单选框
     * 
     * @author Faker Li
     * @created 2016年11月14日 下午5:30:25
     * @param EFLOW_BUSRECORD
     * @param result
     * @param prefix
     * @param dwst
     */
    public void setEasyValue(Map<String, Object> EFLOW_BUSRECORD, Map<String, Object> result, String prefix,
            String dwst) {
        if (EFLOW_BUSRECORD.get(dwst) != null) {
            String SFWXXST = (String) EFLOW_BUSRECORD.get(dwst);
            if (SFWXXST.equals("1")) {
                result.put(prefix + dwst + "_A", "☑");
                result.put(prefix + dwst + "_B", "□");
            } else if (SFWXXST.equals("0")) {
                result.put(prefix + dwst + "_A", "□");
                result.put(prefix + dwst + "_B", "☑");
            }
        } else {
            /*
             * result.put(prefix+dwst+"_A", "□"); result.put(prefix+dwst+"_B",
             * "□");
             */
            setNoValueCheckbox(result, prefix, dwst, new String[] { "A", "B" });
        }
    }

    /**
     * 描述 设置地址回填数据
     * 
     * @author Faker Li
     * @created 2016年11月14日 下午3:37:24
     * @param zsdz
     * @param busRecordId
     * @param string
     */
    private void setWordDzxx(StringBuffer zsdz, String busRecordId, String type) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select SQZZZM,XZZXXJS,XZJD,CLNMPHM ");
        sql.append(" from T_FJFDA_SPJYXK_DZXX D WHERE D.JBXX_ID=? AND D.DZLB=?   ");
        params.add(busRecordId);
        params.add(type);
        Map<String, Object> dzxxMap = dao.getByJdbc(sql.toString(), params.toArray());
        if (dzxxMap.get("SQZZZM") != null && StringUtils.isNotEmpty((String) dzxxMap.get("SQZZZM"))) {
            String dicName = (String) fdaDicTypeService.getByTypeCode((String) dzxxMap.get("SQZZZM")).get("TYPE_NAME");
            zsdz.append(dicName);
        }
        if (dzxxMap.get("XZZXXJS") != null && StringUtils.isNotEmpty((String) dzxxMap.get("XZZXXJS"))) {
            String dicName = (String) fdaDicTypeService.getByTypeCode((String) dzxxMap.get("XZZXXJS")).get("TYPE_NAME");
            zsdz.append(dicName);
        }
        if (dzxxMap.get("XZJD") != null && StringUtils.isNotEmpty((String) dzxxMap.get("XZJD"))) {
            String dicName = this.fdaDictionaryService.getDicNames((String) dzxxMap.get("XZZXXJS"),
                    (String) dzxxMap.get("XZJD"));
            zsdz.append(dicName);
        }
        if (dzxxMap.get("CLNMPHM") != null && StringUtils.isNotEmpty((String) dzxxMap.get("CLNMPHM"))) {
            zsdz.append((String) dzxxMap.get("CLNMPHM"));
        }
    }

    /**
     * 
     * 描述 业务退回回填数据
     * 
     * @author Faker Li
     * @created 2016年11月16日 下午5:05:04
     * @param vars
     * @return
     */
    @Override
    public Map<String, Object> setWordValuesForYWTH(Map<String, Object> vars) {
        Map<String, Object> result = new HashMap<String, Object>();
        String exeId = (String) vars.get("RECORD_ID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null) {
            // String ITEM_CODE = (String)flowExe.get("ITEM_CODE");
            String busTableName = (String) flowExe.get("BUS_TABLENAME");
            String busRecordId = (String) flowExe.get("BUS_RECORDID");
            String pkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> EFLOW_BUSRECORD = executionService.getByJdbc(busTableName, new String[] { pkName },
                    new Object[] { busRecordId });
            setWordWtrxx(busRecordId, EFLOW_BUSRECORD, result, "");
            result.put("JYZMC", EFLOW_BUSRECORD.get("JYZMC"));
            result.put("SQSJ_TIME", EFLOW_BUSRECORD.get("SQSJ_TIME"));
            result.put("OLDEXE_ID", EFLOW_BUSRECORD.get("OLDEXE_ID"));
            result.put("PHONE", EFLOW_BUSRECORD.get("PHONE"));
            result.put("THYY", EFLOW_BUSRECORD.get("THYY"));

        }
        result.put("CURRENT_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return result;
    }

    /**
     * 描述回填注销申请表数据
     * 
     * @author Faker Li
     * @created 2016年11月16日 下午5:14:33
     * @param vars
     * @return
     */
    @Override
    public Map<String, Object> setWordValuesForZX(Map<String, Object> vars) {
        Map<String, Object> result = new HashMap<String, Object>();
        String exeId = (String) vars.get("RECORD_ID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null) {
            // String ITEM_CODE = (String)flowExe.get("ITEM_CODE");
            String busTableName = (String) flowExe.get("BUS_TABLENAME");
            String busRecordId = (String) flowExe.get("BUS_RECORDID");
            String pkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> EFLOW_BUSRECORD = executionService.getByJdbc(busTableName, new String[] { pkName },
                    new Object[] { busRecordId });
            setWordWtrxx(busRecordId, EFLOW_BUSRECORD, result, "");
            result.put("JYZMC", EFLOW_BUSRECORD.get("JYZMC"));
            result.put("XKZBH_OLD", EFLOW_BUSRECORD.get("XKZBH_OLD"));
            result.put("SQR", EFLOW_BUSRECORD.get("SQR"));
            result.put("PHONE", EFLOW_BUSRECORD.get("PHONE"));
            result.put("XKZZXYY", EFLOW_BUSRECORD.get("XKZZXYY"));

        }
        result.put("CURRENT_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return result;
    }

    /**
     * 描述 回填撤销申请表数据
     * 
     * @author Faker Li
     * @created 2016年11月16日 下午5:26:25
     * @param vars
     * @return
     */
    @Override
    public Map<String, Object> setWordValuesForCX(Map<String, Object> vars) {
        Map<String, Object> result = new HashMap<String, Object>();
        String exeId = (String) vars.get("RECORD_ID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null) {
            // String ITEM_CODE = (String)flowExe.get("ITEM_CODE");
            String busTableName = (String) flowExe.get("BUS_TABLENAME");
            String busRecordId = (String) flowExe.get("BUS_RECORDID");
            String pkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> EFLOW_BUSRECORD = executionService.getByJdbc(busTableName, new String[] { pkName },
                    new Object[] { busRecordId });
            setWordWtrxx(busRecordId, EFLOW_BUSRECORD, result, "");
            result.put("JYZMC", EFLOW_BUSRECORD.get("JYZMC"));
            result.put("XKZBH_OLD", EFLOW_BUSRECORD.get("XKZBH_OLD"));
            result.put("PHONE", EFLOW_BUSRECORD.get("PHONE"));
            result.put("XKZCXYY", EFLOW_BUSRECORD.get("XKZCXYY"));

        }
        result.put("CURRENT_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return result;
    }

    /**
     * 描述 回填补证申请表数据
     * 
     * @author Faker Li
     * @created 2016年11月16日 下午5:35:01
     * @param vars
     * @return
     */
    @Override
    public Map<String, Object> setWordValuesForBZ(Map<String, Object> vars) {
        Map<String, Object> result = new HashMap<String, Object>();
        String exeId = (String) vars.get("RECORD_ID");
        Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe != null) {
            // String ITEM_CODE = (String)flowExe.get("ITEM_CODE");
            String busTableName = (String) flowExe.get("BUS_TABLENAME");
            String busRecordId = (String) flowExe.get("BUS_RECORDID");
            String pkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> EFLOW_BUSRECORD = executionService.getByJdbc(busTableName, new String[] { pkName },
                    new Object[] { busRecordId });
            setWordWtrxx(busRecordId, EFLOW_BUSRECORD, result, "");
            result.put("JYZMC", EFLOW_BUSRECORD.get("JYZMC"));
            result.put("XKZBH_OLD", EFLOW_BUSRECORD.get("XKZBH_OLD"));
            result.put("PHONE", EFLOW_BUSRECORD.get("PHONE"));
            setNoValueCheckbox(result, "", "BZ_TYPE", new String[] { "A", "B" });
            if (EFLOW_BUSRECORD.get("BZ_TYPE") != null) {
                String BZ_TYPE = (String) EFLOW_BUSRECORD.get("BZ_TYPE");
                if (BZ_TYPE.indexOf("1") > -1) {
                    result.put("BZ_TYPE_A", "☑");
                }
                if (BZ_TYPE.indexOf("2") > -1) {
                    result.put("BZ_TYPE_B", "☑");
                    result.put("BZ_NUM", EFLOW_BUSRECORD.get("BZ_NUM"));
                }
            }
            setNoValueCheckbox(result, "", "BZYY", new String[] { "A", "B" });
            if (EFLOW_BUSRECORD.get("BZYY") != null) {
                String BZYY = EFLOW_BUSRECORD.get("BZYY").toString();
                if (BZYY.equals("1")) {
                    result.put("BZYY_A", "☑");
                    result.put("GGBKMC", EFLOW_BUSRECORD.get("GGBKMC"));
                    result.put("GGRQ", EFLOW_BUSRECORD.get("GGRQ"));
                } else if (BZYY.equals("2")) {
                    result.put("BZYY_B", "☑");
                }
            }
        }
        result.put("CURRENT_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        return result;
    }

    /**
     * 
     * 描述
     * 
     * @author Usher Song
     * @created 2016年12月9日 上午10:35:56
     * @param xkzbh
     * @param jycs
     * @param jyzcm
     */
    @Override
    public List<Map<String, Object>> getLicenseByJycsAndXkzbh(String jycs, String xkzbh, String jyzmc) {
        List<Object> params = new ArrayList<Object>();
        params.add(jycs);
        params.add(xkzbh);
        params.add(jyzmc);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.* FROM T_FJFDA_SPJYXKXX T WHERE T.COME_FROM IN('0','1','4') ");
        sql.append("AND T.XKZZT='10' AND T.JYCS=? AND T.XKZBH!=? AND T.JYZMC!=? ");
        List<Map<String, Object>> jycsList = dao.findBySql(sql.toString(), params.toArray(), null);
        if (jycsList == null || jycsList.size() <= 0) {
            return null;
        } else {
            return jycsList;
        }
    }

    /**
     * 
     * 描述 预审通过后置事件
     * 
     * @author Faker Li
     * @created 2016年12月14日 下午3:19:40
     * @param flowVars
     * @return
     */
    @Override
    public Map<String, Object> afterYSTGHandle(Map<String, Object> flowVars) {
        return null;
    }

    /**
     * 描述
     * 
     * @author Faker Li
     * @created 2017年11月17日 上午11:12:41
     */
    @Override
    public void updateExeAddr() {
        StringBuffer sql = new StringBuffer("");
        sql.append(" select t.* from JBPM6_EXECUTION t where t.apply_addr is null ");
        sql.append(" and t.item_code  like '001XK001%' ");
        sql.append("  and t.create_time > '2017-11-16 23:59:59' ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String BUS_RECORDID = (String) list.get(i).get("BUS_RECORDID");
                String EXE_ID = (String) list.get(i).get("EXE_ID");
                String addr = this.getAddressStr(BUS_RECORDID, 2);
                StringBuffer updateSql = new StringBuffer("");
                updateSql.append(" update JBPM6_EXECUTION e set e.APPLY_ADDR=? where  ");
                updateSql.append(" e.EXE_ID=?  ");
                dao.executeSql(updateSql.toString(), new Object[] { addr, EXE_ID });

            }
        }

    }

    /**
     * 描述 发送预警信息
     * 
     * @author Faker Li
     * @created 2018年3月27日 下午2:25:30
     */
    @Override
    public void sendFoodWarnMessage() {
    }

    /**
     * 
     * 描述 后置事件构造传输报文
     * @author Keravon Feng
     * @created 2019年2月21日 上午11:13:32
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> afterCreateTransData(Map<String, Object> flowDatas) {
        String bus_recordid = (String)flowDatas.get("EFLOW_BUSRECORDID");
        String eflow_eveId = (String)flowDatas.get("EFLOW_EXEID");
        //退回流程，不执行
        String isback = (String)flowDatas.get("EFLOW_ISBACK");
        String eflow_istempsave  = (String)flowDatas.get("EFLOW_ISTEMPSAVE");
        if(!("true".equals(isback)) && !("1".equals(eflow_istempsave))){
            Map<String,Object> data = new HashMap<String,Object>();
            buildTransData(data,flowDatas);
            //构造材料信息
            Map<String,Map<String,Object>> attachs = new HashMap<String, Map<String,Object>>();
            String submitmaterfilejson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
            if(StringUtils.isNotEmpty(submitmaterfilejson)){
                List<Map> maters = JSON.parseArray(submitmaterfilejson, Map.class);
                if(maters != null && maters.size() > 0){
                    //删除待传材料列表业务相关的数据
                    dao.remove("T_FJFDA_TRANS_MATERS", new String[] { "BUS_RECORDID" }, new Object[] { bus_recordid });
                    for(Map map : maters){
                        String attach_key = String.valueOf(map.get("ATTACH_KEY"));
                        String file_id = String.valueOf(map.get("FILE_ID"));
                        Map<String,Object> fileInfo = fileAttachService.getFileAttachObject(file_id);
                        Map<String,Object> fileMap = new HashMap<String, Object>();
                        fileMap.put("fileRealName", fileInfo.get("FILE_NAME"));
                        String filePath  = (String)fileInfo.get("FILE_PATH");
                        if(filePath != null){
                            fileMap.put("fileName", filePath.substring(filePath.lastIndexOf("/")+1));
                        }else{
                            fileMap.put("fileName", "error");
                        }
                        fileMap.put("fileSize", String.valueOf(fileInfo.get("FILE_LENGTH"))); 
                        attachs.put(attach_key,fileMap);
                        Map<String,Object> transMaters = new HashMap<String, Object>();
                        transMaters.putAll(fileMap);
                        transMaters.put("FILEID", file_id);
                        transMaters.put("BUS_RECORDID", bus_recordid);
                        transMaters.put("FILEPATH", filePath);
                        transMaters.put("MATER_CODE", attach_key);
                        dao.saveOrUpdate(transMaters, "T_FJFDA_TRANS_MATERS", null);
                    }
                }
            }
            data.put("attachs", attachs);
            //把构造的报文结果存储在数据库中
            dao.remove("T_FJFDA_TRANS_DATA", new String[] { "BUS_RECORDID" }, new Object[] { bus_recordid });
            Map<String,Object> transData = new HashMap<String, Object>();
            transData.put("ITEM_CODE", flowDatas.get("FJFDA_ITEM_CODE"));
            transData.put("BUS_RECORDID", bus_recordid);
            transData.put("DATA_JSON", JSON.toJSONString(data));
            dao.saveOrUpdate(transData, "T_FJFDA_TRANS_DATA", null);
            //把当前的办件数据入库
            dao.remove("T_FJFDA_TRANS", new String[] { "BUS_RECORDID" }, new Object[] { bus_recordid });
            Map<String,Object> trans = new HashMap<String, Object>();
            trans.put("BUS_RECORDID", bus_recordid);
            trans.put("BUS_EVEID", eflow_eveId);
            trans.put("ITEM_CODE", flowDatas.get("FJFDA_ITEM_CODE"));
            trans.put("BUS_ITEMCODE", flowDatas.get("ITEM_CODE"));
            trans.put("BUS_ITEMNAME", flowDatas.get("ITEM_NAME"));
            dao.saveOrUpdate(trans, "T_FJFDA_TRANS", null);
        }
        return flowDatas;
    }

    /**
     * 描述 构建相应data格式数据
     * @author Keravon Feng
     * @created 2019年2月21日 下午2:28:49
     * @param data
     * @param flowDatas
     */
    private void buildTransData(Map<String, Object> data, Map<String, Object> flowDatas) {
        data.put("JYZMC", flowDatas.get("JYZMC"));
        data.put("SHXYDMSFZHM", flowDatas.get("SHXYDMSFZHM"));
        data.put("ZSQS", flowDatas.get("ZSQS"));
        data.put("ZSXS", flowDatas.get("ZSXS"));
        data.put("ZSXZ", flowDatas.get("ZSXZ"));
        data.put("ZSXXDZ", flowDatas.get("ZSXXDZ"));
        data.put("JYCSQS", flowDatas.get("JYCSQS"));
        data.put("JYCSXS", flowDatas.get("JYCSXS"));
        data.put("JYCSXZ", flowDatas.get("JYCSXZ"));
        data.put("JYXXDZ", flowDatas.get("JYXXDZ"));
        data.put("CCCSQS", flowDatas.get("CCCSQS"));
        data.put("CCCSXS", flowDatas.get("CCCSXS"));
        data.put("CCCSXZ", flowDatas.get("CCCSXZ"));
        data.put("CCXXDZ", flowDatas.get("CCXXDZ"));
        data.put("ZTYTDM", flowDatas.get("ZTYTDM"));
        data.put("WLJY", flowDatas.get("WLJY"));
        data.put("WZDZ", flowDatas.get("WZDZ"));
        data.put("STMD", flowDatas.get("STMD"));
        data.put("ZYCF", flowDatas.get("ZYCF"));
        data.put("JTYCPSDW", flowDatas.get("JTYCPSDW"));
        data.put("LYZDSHSBCSSPXS", flowDatas.get("LYZDSHSBCSSPXS"));
        data.put("DLLSPZS", flowDatas.get("DLLSPZS"));
        data.put("YBZSPXS", flowDatas.get("YBZSPXS"));
        data.put("SZSPXS", flowDatas.get("SZSPXS"));
        data.put("SZSSXS", flowDatas.get("SZSSXS"));
        data.put("TSSPXS", flowDatas.get("TSSPXS"));
        data.put("QTLSPXS", flowDatas.get("QTLSPXS"));
        data.put("JZCTW", flowDatas.get("JZCTW"));
        data.put("BHDG", flowDatas.get("BHDG"));
        data.put("ZNJZS", flowDatas.get("ZNJZS"));
        data.put("QTLSPZS", flowDatas.get("QTLSPZS"));
        data.put("SQFBS", flowDatas.get("SQFBS"));
        data.put("YXQ", flowDatas.get("YXQ"));
        data.put("JJXZ", flowDatas.get("JJXZ"));
        data.put("ZGRS", flowDatas.get("ZGRS"));
        data.put("YTJRS", flowDatas.get("YTJRS"));
        data.put("YZBM", flowDatas.get("YZBM"));
        data.put("EMAIL", flowDatas.get("EMAIL"));
        data.put("FRXM", flowDatas.get("FRXM"));
        data.put("FRXB", flowDatas.get("FRXB"));
        data.put("FRMZ", flowDatas.get("FRMZ"));
        data.put("FRZW", flowDatas.get("FRZW"));
        data.put("FRHJDJZZ", flowDatas.get("FRHJDJZZ"));
        data.put("FRZJLX", flowDatas.get("FRZJLX"));
        data.put("FRZJH", flowDatas.get("FRZJH"));
        data.put("FRGDDHID", flowDatas.get("FRGDDHID"));
        data.put("FRYDDHID", flowDatas.get("FRYDDHID"));
        String technicalPersonnelJson = (String)flowDatas.get("TechnicalPersonnelJson");
        if(technicalPersonnelJson != null && StringUtils.isNotEmpty(technicalPersonnelJson)){
            List<Map> technicalPersonnes = JSON.parseArray(technicalPersonnelJson, Map.class);
            data.put("TechnicalPersonnelJson", technicalPersonnes);
        }
        String practitionersJson = (String)flowDatas.get("PractitionersJson");
        if(practitionersJson != null && StringUtils.isNotEmpty(practitionersJson)){
            List<Map> practitioners = JSON.parseArray(practitionersJson, Map.class);
            data.put("PractitionersJson", practitioners);
        }
        String facilityEquipmentJson = (String)flowDatas.get("FacilityEquipmentJson");
        if(facilityEquipmentJson != null && StringUtils.isNotEmpty(facilityEquipmentJson)){
            List<Map> facilityEquipments = JSON.parseArray(facilityEquipmentJson, Map.class);
            data.put("FacilityEquipmentJson", facilityEquipments);
        }
        data.put("WTDLR", flowDatas.get("WTDLR"));
        data.put("WTR", flowDatas.get("WTR"));
        data.put("WTRZJLX", flowDatas.get("WTRZJLX"));
        data.put("WTRZJH", flowDatas.get("WTRZJH"));
        data.put("WTKSSJ", flowDatas.get("WTKSSJ"));
        data.put("WTJSSJ", flowDatas.get("WTJSSJ"));
        data.put("WTXGCLQX", flowDatas.get("WTXGCLQX"));
        data.put("WTXGBGQX", flowDatas.get("WTXGBGQX"));
        data.put("WTLQZSQX", flowDatas.get("WTLQZSQX"));
        data.put("WTHDQX", flowDatas.get("WTHDQX"));
        data.put("WTQTCZQX", flowDatas.get("WTQTCZQX"));
        
        //补充数据
        data.put("YYFSDIV", flowDatas.get("YYFSDIV"));
        data.put("CYLXDIV", flowDatas.get("CYLXDIV"));
        data.put("SFWXXSTDIV", flowDatas.get("SFWXXSTDIV"));
        
        data.put("FRGDDHID", flowDatas.get("FRGDDH"));
        data.put("FRYDDHID", flowDatas.get("FRYDDH"));
        
        data.put("FRGDDH", flowDatas.get("FRGDDH"));
        data.put("FRYDDH", flowDatas.get("FRYDDH"));
        
    }

    /**
     * 描述 listTransMaters
     * @author Keravon Feng
     * @created 2019年2月22日 下午5:29:42
     * @param pageSize
     * @return
     */
    @Override
    public List<Map<String, Object>> listTransMaters(int pageSize) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.YW_ID,T.BUS_RECORDID,T.FILEPATH FROM T_FJFDA_TRANS_MATERS T ");
        sql.append(" WHERE T.TRANS_STATE = ? ORDER BY T.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{0}, new PagingBean(0, pageSize));
    }

    /**
     * 描述 listTrans
     * @author Keravon Feng
     * @created 2019年2月25日 下午2:20:56
     * @param pageSize
     * @return
     */
    @Override
    public List<Map<String, Object>> listTrans(int pageSize) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.YW_ID,T.BUS_RECORDID,T.ITEM_CODE FROM T_FJFDA_TRANS T ");
        sql.append(" WHERE T.TRANS_STATE = ? AND IS_MATERS_UPLOAD = 1 ORDER BY T.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{0}, new PagingBean(0, pageSize));
    }
    
}
