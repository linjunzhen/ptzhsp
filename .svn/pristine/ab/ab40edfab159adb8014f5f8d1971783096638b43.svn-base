/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.sync.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bsfw.service.SwbDataAttrService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.sync.dao.SwbDataDao;
import net.evecom.platform.sync.service.SwbDataService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-8-25 上午10:45:06
 */
@Service("swbDataService")
public class SwbDataServiceImpl extends BaseServiceImpl implements
        SwbDataService {

    /**
     * 所引入的dao
     */
    @Resource
    private SwbDataDao dao;
    /**
     * 
     */
    @Resource
    private FileAttachService  fileAttachService;
    /**
     * 
     */
    @Resource
    private SwbDataAttrService swbDataAttrService;
    /**
     * 
     */
    @Resource
    private FlowMappedService  flowMappedService;
    /**
     * 
     */
    @Resource
    private DictionaryService  dictionaryService;

    /**
     * 引入dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述   获取未同步办件信息
     * @author Danto Huang
     * @created 2016-8-25 上午10:55:48
     * @return
     */
    public List<Map<String,Object>> getUnSyncBjxx(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select e.* from jbpm6_execution e ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code = e.item_code ");
        sql.append("where s.swb_item_code is not null and e.apply_status<>1 and e.exe_id not in ");
        sql.append("(select r.exe_id from t_bsfw_swbdata_res r where r.data_type=10) ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 获取省网办返回信息
     * @author Kester Chen
     * @created 2017-11-6 下午3:36:37
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSwbReturnInfoData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,r.data_type,r.oper_time from T_BSFW_SWBDATA_RETURNINFO t ");
        sql.append(" left join T_BSFW_SWBDATA_RES r on t.caseunid = r.res_id ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(params.size() != 0) {
            list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        }
        return list;
    }
    /**
     * 
     * 描述 获取省网办返回信息
     * @author Kester Chen
     * @created 2017-11-6 下午3:36:37
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSwbReturnInfoStatisData(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select s.IMPL_DEPART depart_name,s.ZBCS as depart_namec, ");
        sql.append(" s.item_code,s.item_name,r.data_type,t.result,count(t.unid) as NUM ");
        sql.append(" from T_BSFW_SWBDATA_RETURNINFO t ");
        sql.append(" left join T_BSFW_SWBDATA_RES r on t.caseunid = r.res_id ");
        sql.append(" left join JBPM6_EXECUTION e on e.exe_id = t.sn ");
        sql.append(" left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code ");
        sql.append(" where e.item_code is not null and r.res_id is not null ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " group by s.IMPL_DEPART,s.ZBCS,s.item_code,s.item_name,r.data_type,t.result ";
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 根据sqlfilter获取到数据列表 (省网办返回信息)
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySwbReturnInfoSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,r.data_type,r.oper_time from T_BSFW_SWBDATA_RETURNINFO t ");
        sql.append(" left join T_BSFW_SWBDATA_RES r on t.caseunid = r.res_id ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }
    /**
     * 根据sqlfilter获取到数据列表 (省网办返回信息)
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySwbReturnInfoStatisSqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select d.depart_name,dc.depart_name as depart_namec, ");
        sql.append(" s.item_code,s.item_name,r.data_type,t.result,count(t.unid) as NUM ");
        sql.append(" from T_BSFW_SWBDATA_RETURNINFO t ");
        sql.append(" left join T_BSFW_SWBDATA_RES r on t.caseunid = r.res_id ");
        sql.append(" left join JBPM6_EXECUTION e on e.exe_id = t.sn ");
        sql.append(" left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE = s.SSBMBM ");
        sql.append(" LEFT JOIN (select CATALOG_CODE, D1.DEPART_ID,d1.depart_name ");
        sql.append(" from t_wsbs_serviceitem_catalog C ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID = C.CHILD_DEPART_ID) DC ");
        sql.append(" ON DC.CATALOG_CODE = s.CATALOG_CODE ");
        sql.append(" where e.item_code is not null and r.res_id is not null ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql += " group by d.depart_name,dc.depart_name,s.item_code,s.item_name,r.data_type,t.result ";
//        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述   保存办件信息指令
     * @author Danto Huang
     * @created 2016-8-25 下午2:25:05
     * @param exeId
     */
    public void saveBjxxData(String exeId){
        Map<String, Object> flowExe = dao.getByJdbc("jbpm6_execution",
                new String[] { "exe_id" }, new Object[] { exeId });
        boolean isExists = dao.isHaveSaveBjxxInfo(exeId);
        if (!isExists) {
            // 开始保存办件信息
            Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
            bjxxDataRes.put("EXE_ID", exeId);
            bjxxDataRes.put("DATA_TYPE", "10");
            bjxxDataRes.put("OPER_TYPE", "I");
            // 定义是否有附件
            int HAS_ATTR = 0;
            // 获取业务表名称
            String BUS_TABLENAME = (String) flowExe.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String BUS_RECORDID = (String) flowExe.get("BUS_RECORDID");
            // 获取附件列表
            List<Map<String, Object>> fileList = fileAttachService.findList(BUS_TABLENAME, BUS_RECORDID);
            if (fileList != null && fileList.size() > 0) {
                HAS_ATTR = 1;
            }
            bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
            bjxxDataRes.put("INTER_TYPE", "10");
            String resId = dao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
            // 获取事项编码
            String itemCode = (String) flowExe.get("ITEM_CODE");
            if (fileList != null && fileList.size() > 0) {
                swbDataAttrService.saveSwbDataAttr(resId, fileList, itemCode);
            }
        }
    }

    /**
     * 
     * 描述   获取未同步过程信息
     * @author Danto Huang
     * @created 2016-8-25 上午10:55:48
     * @return
     */
    public List<Map<String,Object>> getUnSyncGcxx(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select e.* from jbpm6_execution e ");
        sql.append("where e.exe_id in ");
        sql.append("(select r.exe_id from t_bsfw_swbdata_res r where r.data_type=10) ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述   保存过程信息指令
     * @author Danto Huang
     * @created 2016-8-25 下午2:25:05
     * @param exeId
     */
    public void saveGcxxData(String exeId){
        Map<String, Object> flowExe = dao.getByJdbc("jbpm6_execution",
                new String[] { "exe_id" }, new Object[] { exeId });
        List<Map<String,Object>> taskList = dao.getDoneTaskByExeId(exeId);
        if(taskList!=null&&taskList.size()>0){
            String defId = (String) flowExe.get("DEF_ID");
            for(Map<String,Object> task : taskList){
                String taskId = task.get("TASK_ID").toString();
                String nodeName = task.get("TASK_NODENAME").toString();
                boolean isExist = dao.isHaveSaveGcxxInfo(taskId);
                if(!isExist){
                    // 获取映射数据
                    Map<String, Object> flowMapInfo = flowMappedService.getFlowMapInfo(defId,
                            nodeName, "1");
                    if (flowMapInfo != null) {
                        // 获取映射名称
                        String ysName = (String) flowMapInfo.get("YS_NAME");
                        Map<String, Object> gcxxDataRes = new HashMap<String, Object>();
                        gcxxDataRes.put("EXE_ID", exeId);
                        gcxxDataRes.put("DATA_TYPE", "20");
                        gcxxDataRes.put("OPER_TYPE", "I");
                        gcxxDataRes.put("HAS_ATTR", 0);
                        gcxxDataRes.put("INTER_TYPE", "10");
                        gcxxDataRes.put("TASK_ID", taskId);
                        String OTHER_STATUS = "3";
                        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                                new String[] {
                                    "TYPE_CODE", "DIC_NAME" 
                                }, new Object[] { "SWBDYZ", ysName }
                        );
                        if (dictionary != null) {
                            OTHER_STATUS = (String) dictionary.get("DIC_CODE");
                        }
                        gcxxDataRes.put("OTHER_STATUS", OTHER_STATUS);
                        dao.saveOrUpdate(gcxxDataRes, "T_BSFW_SWBDATA_RES", null);
                    }
                }
            }
        }
    }    

    /**
     * 
     * 描述   获取未同步结果信息
     * @author Danto Huang
     * @created 2016-8-25 上午10:55:48
     * @return
     */
    public List<Map<String,Object>> getUnSyncJgxx(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select e.* from jbpm6_execution e ");
        sql.append("where e.run_status not in (0,1) and e.exe_id in ");
        sql.append("(select r.exe_id from t_bsfw_swbdata_res r where r.data_type=10) ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述   保存结果信息指令
     * @author Danto Huang
     * @created 2016-8-25 下午2:25:05
     * @param exeId
     */
    public void saveJgxxData(String exeId){
        boolean isExist = dao.isHaveSaveJgxxInfo(exeId);
        if(!isExist){
            Map<String ,Object> task = dao.getEndTaskByExeId(exeId);
            String taskId = task.get("TASK_ID").toString();
            // 开始保存办件信息
            Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
            bjxxDataRes.put("EXE_ID", exeId);
            bjxxDataRes.put("DATA_TYPE", "30");
            bjxxDataRes.put("OPER_TYPE", "I");
            // 定义是否有附件
            int HAS_ATTR = 0;
            bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
            bjxxDataRes.put("INTER_TYPE", "10");
            bjxxDataRes.put("TASK_ID", taskId);
            String resId = dao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
        }
    }
}
