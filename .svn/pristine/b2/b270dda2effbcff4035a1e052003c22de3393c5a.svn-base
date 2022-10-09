/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.hflow.service.ExecutionService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcFwzldjbaDao;
import net.evecom.platform.bdc.service.BdcFwzldjbaService;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcYgspfygdjService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述 不动产抵押权登记操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("bdcFwzldjbaService")
public class BdcFwzldjbaServiceImpl extends BaseServiceImpl implements BdcFwzldjbaService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcFwzldjbaServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private BdcFwzldjbaDao dao;
    /**
     * 所引入的serivice
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 所引入的serivice
     */
    @Resource
    private BdcFwzldjbaService bdcFwzldjbaService;

    /**
     * 所引入的service
     */
    @Resource
    private BdcGyjsjfwzydjService gyjsjfwzydjService;

    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 描述:审批表打印配置类
     *
     * @author Madison You
     * @created 2020/4/27 11:29:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSqbConfig;


    /**
     * 引入service
     */
    @Resource
    private BdcYgspfygdjService bdcYgspfygdjService;

    /**
     * 引入service
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;

    /**
     * 引入service
     */
    @Resource
    private BdcQlcCreateSignFlowService bdcQlcCreateSignFlowService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 覆盖获取实体dao方法 描述
     *
     * @return
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 一审自定义数据回填方法
     *
     * @param args
     * @author Roger Li
     * @created 2019年12月25日 下午4:24:34
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setRedrawData(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        // 为标签设初值以防页面出现标签
        this.setDefaultValue(returnMap, StringUtil.getValue(execution, "EXE_ID"));
        // 设值流程主表相关信息 如收件人
        this.setSubmitUsrsInfo(returnMap, execution);
        // 设值流程相关信息 如环节审核人、审核意见等
        this.setFlowAllObj(returnMap, flowAllObj);
        // 设值服务事项和流程表单相关信息 如服务事项名称
        // this.setItemAndDefInfo(returnMap, serviceItem);
        // 设值业务表相关信息
        this.setBusRecordInfo(returnMap, busInfo);
        // 模板类型 templateType未设值则前端默认使用DataRegion方式进行数据填充
        // DataRegion(通过添加书签做的模板)则前端以DataRegion方式填充数据
        // DataTag(通过添加【ITEM_NAME】类似标识的方式做的模板)则前端以DataRegion方式填充数据
        returnMap.put("templateType", "DataTag");
        /* 模板字符串替换 */
       bdcSqbConfig.bdcCreateSpbConfig(returnMap);
        
    }

    /**
     * 描述 为标签设初值以防页面出现标签
     *
     * @param returnMap
     * @return
     * @author Roger Li
     * @created 2019年12月17日 下午2:35:52
     */
    private void setDefaultValue(Map<String, Object> returnMap, String exeId) {
        returnMap.put("fwzl", "");
        returnMap.put("jzmj", "");
        returnMap.put("fwjg", "");
        returnMap.put("ytsm", "");
        returnMap.put("zj", "");
        returnMap.put("qzh", "");
        returnMap.put("czrname0", "");
        returnMap.put("czrcardtype0", "");
        returnMap.put("czrcardno0", "");
        returnMap.put("czrname1", "");
        returnMap.put("czrcardtype1", "");
        returnMap.put("czrcardno1", "");
        returnMap.put("czrname2", "");
        returnMap.put("czrcardtype2", "");
        returnMap.put("czrcardno2", "");
        returnMap.put("personname0", "");
        returnMap.put("cardtype0", "");
        returnMap.put("cardno0", "");
        returnMap.put("personname1", "");
        returnMap.put("cardtype1", "");
        returnMap.put("cardno1", "");
        returnMap.put("personname2", "");
        returnMap.put("cardtype2", "");
        returnMap.put("cardno2", "");

    }

    /**
     * 描述 获取流程主表数据
     *
     * @param execution
     * @return
     * @author Roger Li
     * @created 2019年12月17日 上午10:52:45
     */
    private void setSubmitUsrsInfo(Map<String, Object> returnMap, Map<String, Object> execution) {
        // 申请人通信地址
        returnMap.put("txdz1", StringUtil.getValue(execution, "SQRLXDZ"));
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
    }

    /**
     * 描述 设值服务事项和流程表单相关信息
     *
     * @param returnMap,serviceItem
     * @return
     * @author Roger Li
     * @created 2019年12月17日 下午2:26:46
     */
    private void setItemAndDefInfo(Map<String, Object> returnMap, Map<String, Object> serviceItem) {
        // 权力类型在setBusInfo中 取的是抵押明细json中的fw_qllx
        // 登记类型
        returnMap.put("djlx", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
    }

    /**
     * 描述 设值流程相关信息
     *
     * @param returnMap
     * @param flowAllObj
     * @author Roger Li
     * @created 2019年12月27日 下午3:34:33
     */
    @SuppressWarnings("unchecked")
    private void setFlowAllObj(Map<String, Object> returnMap, Map<String, Object> flowAllObj) {
        // TODO 审核意见
        String exeId = StringUtil.getValue((Map<String, Object>) flowAllObj.get("EFLOWOBJ"), "EFLOW_EXEID");
        List<Map<String, Object>> tasks = gyjsjfwzydjService.findAuditTaskListByExeId(exeId);
        for (Map<String, Object> task : tasks) {
            String nodeName = StringUtil.getValue(task, "TASK_NODENAME");
            String assigner = StringUtil.getValue(task, "ASSIGNER_NAME");
            String opinion = StringUtil.getValue(task, "HANDLE_OPINION");
            String date = StringUtil.getValue(task, "END_TIME");
            if (StringUtils.isNotBlank(date)) {
                date = DateTimeUtil.getChinaDate(date);
            } else {
                date = "";
            }
            if (StringUtils.isBlank(assigner)) {
                assigner = StringUtil.getValue(task, "TEAM_NAMES");
            }
            if (nodeName.equals("登簿")) {
                returnMap.put("hd", opinion);
                returnMap.put("fzr", assigner);
                returnMap.put("hdsj", date);
            }
        }
    }

    /**
     * 描述 设值业务表数据
     *
     * @param busInfo
     * @return
     * @author Roger Li
     * @created 2019年12月17日 上午10:56:30
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setBusRecordInfo(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        
        returnMap.put("fwzl", StringUtil.getValue(busInfo, "fwzl"));
        returnMap.put("jzmj", StringUtil.getValue(busInfo, "jzmj"));
        returnMap.put("fwjg", StringUtil.getValue(busInfo, "fwjg"));
        returnMap.put("ytsm", StringUtil.getValue(busInfo, "ytsm"));
        returnMap.put("zj", StringUtil.getValue(busInfo, "zj"));
        returnMap.put("qzh", StringUtil.getValue(busInfo, "qzh"));
        returnMap.put("czrname", StringUtil.getValue(busInfo, "czrname"));
        returnMap.put("czrcardtype", StringUtil.getValue(busInfo, "czrcardtype"));
        returnMap.put("czrcardno", StringUtil.getValue(busInfo, "czrcardno"));
        returnMap.put("personname0", StringUtil.getValue(busInfo, "personname0"));
        returnMap.put("cardtype0", StringUtil.getValue(busInfo, "cardtype0"));
        returnMap.put("cardno0", StringUtil.getValue(busInfo, "cardno0"));
        returnMap.put("personname1", StringUtil.getValue(busInfo, "personname1"));
        returnMap.put("cardtype1", StringUtil.getValue(busInfo, "cardtype1"));
        returnMap.put("cardno1", StringUtil.getValue(busInfo, "cardno1"));
        returnMap.put("personname2", StringUtil.getValue(busInfo, "personname2"));
        returnMap.put("cardtype2", StringUtil.getValue(busInfo, "cardtype2"));
        returnMap.put("cardno2", StringUtil.getValue(busInfo, "cardno2"));

        
    }
    /**
     * 
     * 描述
     * @author Yanisin Shi
     * @param flowDatas
     * @return
     * create time 2021年8月20日
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
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
        
        // 保存人员信息
        savePersonList(flowDatas, busRecordId);
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
      
     
        return flowDatas;
    }
    /**
     * 
     * 描述   获取人员列表
     * @author Yanisin Shi
     * @param ids
     * @return
     * @see net.evecom.platform.bdc.service.BdcFwzldjbaService#findListForResult(java.lang.String)
     * create time 2021年8月20日
     */
    @Override
    public List<Map<String, Object>> findListForResult(String ids) {
        StringBuffer sb = new StringBuffer();
        String[] valuesArray = ids.split(";");
        for (String value : valuesArray) {
            sb.append(",'" + value + "'");
        }
        sb.delete(0, 1);
        sb.insert(0, "(");
        sb.append(")");
        ids=sb.toString();
//        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT * from t_bdc_fwzldjba_persons where  ");
      
        sql.append(" YW_ID = "+ids);
        
        list = dao.findBySql(sql.toString(), params.toArray(),null);
        
        //mlist.put("total",list.size());
        //mlist.put("list", list);
        return list;
    }

    /**
     * 
     * 描述     初始化备案证明编号
     * @author Yanisin Shi
     * @param flowDatas
     * @param busRecordId
     * create time 2021年8月20日
     */
    @SuppressWarnings({ "unchecked" })
    private void savePersonList(Map<String, Object> flowDatas, String busRecordId) {
        //获取当前环节 是否为开始或者暂存
        String cur_stepnames =String.valueOf(flowDatas.get("EFLOW_CURUSEROPERNODENAME"));
       String istempsave=String.valueOf(flowDatas.get("EFLOW_ISTEMPSAVE"));
       //暂存环节和开始环节可以保存人员信息
       if(("1").equals(istempsave)||"开始".equals(cur_stepnames)){
          
               //先删除原有的人员信息避免后续因流程添加产生重复数据
              
        dao.remove("T_BDC_FWZLDJBA_PERSONS", "YW_ID",  new Object[] { busRecordId });
           
        String CzrxxTab = (String) flowDatas.get("CzrxxTab");
        String ryTab = (String) flowDatas.get("ryTab"); 
        String CzfxxTab = (String) flowDatas.get("CzfxxTab");
        //保存人员信息
        if (StringUtils.isNotEmpty(CzrxxTab)) {
            saveTab(CzrxxTab,busRecordId);
        }
        if (StringUtils.isNotEmpty(ryTab)) {
            saveTab(ryTab,busRecordId);
        } 
        if (StringUtils.isNotEmpty(CzfxxTab)) {
            saveTab(CzfxxTab,busRecordId);
        } 
        }
            Map<String,Object>  bdcfwzldjba=null;
            Map<String,Object> fwzldjbaup=new HashMap<String,Object>();
            bdcfwzldjba=  dao.getByJdbc("T_BDC_FWZLDJBA",  new String[] { "YW_ID" }, new Object[] { busRecordId }) ; 
            if(bdcfwzldjba!=null){
            String bazmbh = StringUtil.getValue(bdcfwzldjba, "BAZMBH");
            // 下一环节的名称
            String eflow_nextnodename = String.valueOf(flowDatas.get("EFLOW_NEXTNODENAME"));
            if ("办结".equals(eflow_nextnodename)) {
            //需要添加环节判断 只有办结环节蔡可以生成备案证明编号
            //如果备案证明编号为空则插入一条备案证明编号
            if(!StringUtils.isNotEmpty(bazmbh)){
              //获取业务表记录数
                StringBuffer sql = new StringBuffer(" ");
                sql.append("select count(1)+34 as bazmbh from t_bdc_fwzldjba");
                String b= dao.getByJdbc(sql.toString(), null).get("bazmbh").toString();
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR); 
                String newBazmbh="闽岚房租证字第"+(year*1000+Integer.valueOf(b))+"号";
            
                fwzldjbaup.put("bazmbh",newBazmbh);
            
            }else{
                fwzldjbaup.put("bazmbh",bazmbh);
                
            }
            fwzldjbaup.put("bazmbh_status","1");
            //更新bazmbh
            String record =  dao.saveOrUpdate(fwzldjbaup, "T_BDC_FWZLDJBA",busRecordId);
            }
            }
        
    }
/**
 * 
 * 描述 根据业务id 插入人员表数据
 * @author Yanisin Shi
 * @param jsonarraytab
 * @param busRecordId
 * create time 2021年8月20日
 */
@SuppressWarnings({ "unchecked" })
private void saveTab(String jsonarraytab, String busRecordId) {
Map<String, Object> variables = new HashMap<String,Object>(); 
JSONArray jsonArray=JSONArray.parseArray(jsonarraytab);//转成json数组
for(int i=0;i<jsonArray.size();i++)//遍历json数组
{
    JSONObject jsonObject = jsonArray.getJSONObject(i);
   variables.put( "person_type",(String)jsonObject.get("person_type"));//根据key取值
   variables.put( "lassee_type",(String)jsonObject.get("lassee_type"));
   variables.put( "person_name",(String)jsonObject.get("person_name"));
   variables.put( "card_type",(String)jsonObject.get("card_type"));
   variables.put( "card_no",(String)jsonObject.get("card_no"));
   variables.put( "sex",(String)jsonObject.get("sex"));
   variables.put( "phone",(String)jsonObject.get("phone"));
   variables.put( "ADDRESS",(String)jsonObject.get("ADDRESS"));
   variables.put( "EMAIL",(String)jsonObject.get("EMAIL"));
   variables.put( "YW_ID",busRecordId);
String recordId =  dao.saveOrUpdate(variables, "T_BDC_FWZLDJBA_PERSONS", "");

}
}

    /**
     * 
     * 描述 抵押权首次登记（以及转本登记）业务数据回填
     * 
     * @author Allin Lin
     * @created 2020年12月8日 上午10:47:55
     * @param args
     */
    public void setDyqscdjData(Map<String, Map<String, Object>> args) {
        /* 获取args数据 */
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /* 获取业务数据 */
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /* 初始化审批表字段 */
        this.bdcFwzldjInitSpbVariables(returnMap);

        /* 设置抵押权首次登记业务数据 */
        this.setDyqscdjBusRecordInfo(returnMap, busInfo, execution);

        /* 模板字符串替换 */
        this.bdcCreateSpbConfig(returnMap);
    }
   /**
    * 
    * 描述   打印审请表
    * @author Yanisin Shi
    * @param returnMap
    * create time 2021年8月17日
    */
    public void bdcCreateSpbConfig(Map<String, Object> returnMap) {
        
      
            /*打印申请表*/
            if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
                WordRedrawUtil.processNormalTable07("attachFiles//readtemplate//files//BDCFWZLDJSQB.docx", returnMap);
            
            

    }
            }
    /**
     * 描述 设置抵押权首次登记业务数据
     * 
     * @author Allin Lin
     * @created 2020年12月8日 上午11:13:14
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setDyqscdjBusRecordInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        String sqfs = execution.get("SQFS") == null ? "" : execution.get("SQFS").toString();
        String sjr = "平潭综合实验区行政服务中心";
        if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
            sjr = StringUtil.getValue(execution, "CREATOR_NAME");
        }
        returnMap.put("sjr", sjr);

        returnMap.put("qllx", "抵押权");
        // 登记类型
        String TAKECARD_TYPE = StringUtil.getValue(busInfo, "TAKECARD_TYPE");
        if (StringUtils.isNotEmpty(TAKECARD_TYPE) && TAKECARD_TYPE.equals("2")) {
            returnMap.put("djlx", "抵押权首次登记（转本）");
        } else {
            returnMap.put("djlx", "抵押权首次登记");
        }

        // 权利人信息 抵押权人
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "DYQRXX_IDTYPE"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
        returnMap.put("txdz", StringUtil.getValue(busInfo, "DYQRXX_ADDR"));
        returnMap.put("yb", "");
        returnMap.put("fddbr", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_NAME"));
        returnMap.put("lxdh1", "");
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "DYQRXX_AGENT_NAME"));
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");

        // 义务人信息 抵押人
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "DYQDJ_NAME"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "DYQDJ_IDTYPE"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "DYQDJ_IDNO"));
        returnMap.put("txdz1", "");
        returnMap.put("yb1", "");
        returnMap.put("fddbr1", "");
        returnMap.put("lxdh3", "");
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DYQDJ_AGENT_NAME"));
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc1", "");

        List<Map> dymx = JSON.parseArray(StringUtil.getValue(busInfo, "DYMX_JSON"), Map.class);
        Map<String, Object> dymxMap = null;
        if (dymx.size() > 0) {
            dymxMap = dymx.get(0);
        }
        returnMap.put("zl", StringUtil.getValue(dymxMap, "FDZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(dymxMap, "BDCDYH"));
        StringBuilder qlxz = new StringBuilder();
        String zdxz = StringUtil.getValue(dymxMap, "QLXZ");
        String fwxz = StringUtil.getValue(dymxMap, "FWXZ");
        if (StringUtils.isNotBlank(zdxz)) {
            qlxz.append(zdxz).append("/");
            if (StringUtils.isNotBlank(fwxz)) {
                qlxz.append(fwxz);
            }
        }
        returnMap.put("qlxz", qlxz.toString());
        returnMap.put("bdclx", "土地和房屋");
        StringBuilder sb = new StringBuilder();
        String zdmj = StringUtil.getValue(dymxMap, "ZDMJ");
        String fwjzmj = StringUtil.getValue(dymxMap, "JZMJ");
        if (StringUtils.isNotBlank(zdmj)) {
            sb.append("宗地面积:").append(zdmj).append("平方米/");
            if (StringUtils.isNotBlank(fwjzmj)) {
                sb.append("房屋建筑面积:").append(fwjzmj).append("平方米");
            }
        }
        returnMap.put("mj", sb.toString());
        returnMap.put("yt", StringUtil.getValue(dymxMap, "FW_GHYT"));
        returnMap.put("ybdcqzsh", StringUtil.getValue(dymxMap, "BDCQZH"));
        returnMap.put("gzwlx", "");

        // 抵押情况
        if (StringUtil.getValue(busInfo, "DYQDJ_SFZGEDY").equals("0")) {
            returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DYQDJ_BDBZZQSE"));
        } else {
            returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DYQDJ_ZGZQE"));
        }
        if (StringUtils.isNotBlank((String) busInfo.get("DYQDJ_ZWQSSJ"))
                && StringUtils.isNotBlank((String) busInfo.get("DYQDJ_ZWJSSJ"))) {
            StringBuilder zwlxqx = new StringBuilder();
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DYQDJ_ZWQSSJ"))).append("起 ");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DYQDJ_ZWJSSJ"))).append("止");
            returnMap.put("zwlxqx", zwlxqx.toString());
        } else {
            returnMap.put("zwlxqx", "");
        }

        returnMap.put("djyy", StringUtil.getValue(busInfo, "DYQDJ_DJYY"));
        returnMap.put("djyyzmwj", "");

        returnMap.put("hd", StringUtil.getValue(busInfo, "DJSHXX_HZYJ"));
        returnMap.put("fzr", StringUtil.getValue(busInfo, "DJSHXX_SHR"));
        returnMap.put("hdsj", StringUtil.getValue(busInfo, "DJSHXX_SHJSRQ"));

    }

    /**
     * 
     * @Description 初始化业务数据
     * @author Luffy Cai
     * @date 2020年8月25日
     * @param returnMap
     * @param flowVars
     *            void
     */
    public void initGenValue(Map<String, Object> returnMap, Map<String, Object> flowVars) {
        // setDyqscdjtyBusInfo(returnMap, flowVars, flowVars);
        setDyqscdjBusInfo(returnMap, flowVars, flowVars);
        returnMap.put("xmsqbh", StringUtil.getValue(flowVars, "EFLOW_EXEID"));
        Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { flowVars.get("EFLOW_EXEID") });
        if (flowExe == null) {
            flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                    new Object[] { flowVars.get("EFLOW_EXEID") });
        }
        returnMap.put("xmsqsj", DateTimeUtil.getChinaDate(StringUtil.getValue(flowExe, "CREATE_TIME")));
    }

    /**
     * 描述:查找分户办件
     *
     * @author Madison You
     * @created 2020/8/26 14:40:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBdcfhData(String bdcdyh) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT x.* FROM JBPM6_EXECUTION T LEFT JOIN T_BDCQLC_GYJSJFWZYDJ X ON T.BUS_RECORDID = X.YW_ID ");
        sql.append(" WHERE X.ESTATE_NUM = ? AND T.RUN_STATUS = '2' AND X.ZYDJ_TYPE = '1' ORDER BY T.CREATE_TIME DESC");
        List list = dao.findBySql(sql.toString(), new Object[] { bdcdyh }, null);
        return list;
    }

    /**
     * @Description:设置国有建设及房屋转移登记通用业务数据
     * @author Madison You
     * @created 2020/4/30 9:18:00
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setDyqscdjtyBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        if (StringUtil.getValue(execution, "SQFS").equals("1")) {
            returnMap.put("sjr", "");
        } else {
            returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        }
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isEmpty(sbh)) {
            returnMap.put("xmsqbh", StringUtil.getValue(busInfo, "EFLOW_EXEID"));
        }
        /* 根据单元号获取分户信息 */
        String BDCDYH = StringUtil.getValue(busInfo, "BDCDYH");
        List<Map<String, Object>> list = findBdcfhData(BDCDYH);
        Map<String, Object> map = list.get(0);
        /* 权利人信息JSON */
        String piJson = StringUtil.getValue(map, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();
            List<Map<String, Object>> sqrInfoList = new ArrayList<>();

            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /* 证件类型代码转换 */
                String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                sfzjzl.append(documentType).append(",");
                zjhm.append(StringUtil.getValue(piMap, "POWERPEOPLEIDNUM")).append(",");
                txdz.append(StringUtil.getValue(piMap, "POWERPEOPLEADDR")).append(",");
                yb.append(StringUtil.getValue(piMap, "POWERPEOPLEPOSTCODE")).append(",");
                fddbr.append(StringUtil.getValue(piMap, "POWLEGALNAME")).append(",");
                dlrxm.append(StringUtil.getValue(piMap, "POWAGENTNAME")).append(",");
                lxdh1.append(StringUtil.getValue(piMap, "POWERPEOPLEMOBILE")).append(",");
                lxdh2.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");

                Map<String, Object> sqlInfoMap = new HashMap<>();
                sqlInfoMap.put("sqrxm", StringUtil.getValue(piMap, "POWERPEOPLENAME"));
                if (powerpeopleidtype.equals("SF")) {
                    sqlInfoMap.put("sqrzjlx", "IDCard");
                } else if (powerpeopleidtype.equals("HZ")) {
                    sqlInfoMap.put("sqrzjlx", "Passport");
                } else if (powerpeopleidtype.equals("GATX")) {
                    sqlInfoMap.put("sqrzjlx", "HMPass");
                } else {
                    sqlInfoMap.put("sqrzjlx", "Other");
                }
                sqlInfoMap.put("sqrzjhm", StringUtil.getValue(piMap, "POWERPEOPLEIDNUM"));
                sqlInfoMap.put("sqrsjhm", StringUtil.getValue(piMap, "POWERPEOPLEMOBILE"));
                sqrInfoList.add(sqlInfoMap);
            }
            returnMap.put("sqrInfoList", sqrInfoList);
           
        }

        initYhdjsqrFieldForm(returnMap, busInfo, execution);

    }

    /**
     * 描述 设置抵押权首次登记转本业务数据
     * 
     * @author Allin Lin
     * @created 2020年9月24日 下午5:28:41
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    public void setDyqscdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));

        // 回填银行申请表-抵押人信息
        initYhDjdyrFieldForm(returnMap, busInfo, execution);
        /* 抵押权人信息 */
        String DYQRXX_NAME = StringUtil.getValue(busInfo, "DYQRXX_NAME");
        returnMap.put("dyqrxm", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        if (StringUtils.isNotEmpty(DYQRXX_NAME)) {
            
            returnMap.put("yhmc", DYQRXX_NAME);
            returnMap.put("yhdm", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
            returnMap.put("yhzzlx", zjlxFormmat(StringUtil.getValue(busInfo, "DYQRXX_IDTYPE")));
            returnMap.put("yhlxr", StringUtil.getValue(busInfo, "DYQRXX_AGENT_NAME"));
            returnMap.put("yhlxdh", StringUtil.getValue(busInfo, "DYQRXX_AGENT_PHONE"));
            returnMap.put("yhlxzjhm", StringUtil.getValue(busInfo, "DYQRXX_AGENT_IDNO"));
            returnMap.put("yhlxzjlx", zjlxFormmat(StringUtil.getValue(busInfo, "DYQRXX_AGENT_IDTYPE")));
            returnMap.put("yhfr", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_NAME"));
            // returnMap.put("yhfrsjh", StringUtil.getValue(busInfo,
            // "BANK_LEGAL_PHONE"));
            returnMap.put("yhfrzjhm", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_IDNO"));
            returnMap.put("yhfrzjlx", zjlxFormmat(StringUtil.getValue(busInfo, "DYQRXX_LEGAL_IDTYPE")));
            returnMap.put("yhlxdz", StringUtil.getValue(busInfo, "DYQRXX_ADDR"));

        }
        returnMap.put("sfzjzl2", StringUtil.getValue(busInfo, "DYQRXX_IDTYPE"));
        returnMap.put("zjhm2", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
    }

    /**
     * 描述 证件种类格式化
     * 
     * @author Allin Lin
     * @created 2020年12月1日 下午2:14:33
     * @param zjlx
     * @return
     */
    private String zjlxFormmat(String zjlx) {
        String fmtzjlx = "";
        if (zjlx.equals("身份证")) {
            fmtzjlx = "IDCard";
        } else if (zjlx.equals("港澳居民来往内地通行证")) {
            fmtzjlx = "HMPass";
        } else if (zjlx.equals("护照")) {
            fmtzjlx = "Passport";
        } else if (zjlx.equals("台湾居民来往内地通行证")) {
            fmtzjlx = "MTP";
        } else if (zjlx.equals("营业执照") || zjlx.equals("统一社会信用代码")) {
            fmtzjlx = "SOCNO";
        } else if (zjlx.equals("组织机构代码")) {
            fmtzjlx = "ORANO";
        } else {
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }

    /**
     * 描述 回填银行申请表-抵押人字段信息
     * 
     * @author Allin Lin
     * @created 2020年9月24日 下午5:49:38
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    public void initYhDjdyrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        /* 根据单元号调取不动产预告档案接口获取抵押人信息 */
        String bdcdyh = StringUtil.getValue(busInfo, "BDCDYH");
        String takecardType = StringUtil.getValue(busInfo, "TAKECARD_TYPE");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("bdcdyh", bdcdyh);
        List<Map<String, Object>> ygList = new ArrayList<Map<String, Object>>();
        AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(variables, "announceUrl");
        String jsonString = ajaxJson.getJsonString();
        if (StringUtils.isNotEmpty(jsonString)) {
            ygList = JSON.parseObject(jsonString, List.class);
        }
        /* 根据单元号调取不动产档案接口获取坐落、共有方式、权利比例字段 */
        List<Map<String, Object>> daList = new ArrayList<Map<String, Object>>();
        AjaxJson ajaxJson1 = bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdaxxcxUrl");
        String jsonString1 = ajaxJson1.getJsonString();
        if (StringUtils.isNotEmpty(jsonString1)) {
            daList = JSON.parseObject(jsonString1, List.class);
        }
        // 取预告登记种类 -预售商品房抵押权预告登记的预告数据
        Map<String, Object> dyrMap = new HashMap<String, Object>();// 抵押人信息
        if (ygList != null) {
            for (Map<String, Object> map : ygList) {
                String ygdjzl = StringUtil.getString(map.get("YGDJZL")).trim();// 预告登记种类
                if (ygdjzl.indexOf("抵押权预告登记") > -1 || ygdjzl.indexOf("预售商品房抵押权预告登记") > -1) {
                    dyrMap = map;
                    break;
                }
            }
        }
        // 取不动产单元号-权属状态为现势的档案信息
        Map<String, Object> daMap = new HashMap<String, Object>();
        for (Map<String, Object> map : daList) {
            String qszt = StringUtil.getString(map.get("QSZT")).trim();// 权属状态
            if (qszt.indexOf("现势") > -1) {
                daMap = map;
                break;
            }
        }
        if (StringUtils.isNotEmpty(takecardType) && takecardType.equals("1")) {// 抵押权首次登记
            if (daMap != null) {
                String qlrmc = StringUtil.getValue(daMap, "QLRMC");// 名称
                String[] qlrmcList = qlrmc.split(",");
                String zjlx = StringUtil.getValue(daMap, "ZJLX");// 名称
                String[] zjlxList = zjlx.split(",");
                String zjhm = StringUtil.getValue(daMap, "ZJHM");// 名称
                String[] zjhmList = zjhm.split(",");
                if (qlrmcList != null && qlrmcList.length > 0) {
                    returnMap.put("sqrgyqklxr", qlrmcList[0]);
                    returnMap.put("sqrgyqklxdz", "");// 联系地址
                    returnMap.put("sqrgyqklxdh", "");// 联系电话
                    String GYFS = StringUtil.getString(daMap.get("GYFS"));
                    String qlbl = StringUtil.getValue(daMap, "QLBL");// 权利比例
                    String[] qlblList = qlbl.split(",");
                    if (GYFS.equals("单独所有") || (qlrmcList.length == 1)) {
                        returnMap.put("sqrgyqkxm1", qlrmcList[0]);
                        returnMap.put("sqrgyqkzjzl1", zjlxList[0]);
                        returnMap.put("sqrgyqkzjhm1", zjhmList[0]);
                        returnMap.put("sqrgyqkgj1", "中国");
                        returnMap.put("sqrgyqk1_1", "■");
                        returnMap.put("sqrgyqk1_2", "□");
                        returnMap.put("sqrgyqk1_3", "□");
                        returnMap.put("sqrgyqkfe1", "______");

                        returnMap.put("sqrgyqk2_1", "□");
                        returnMap.put("sqrgyqk2_2", "□");
                        returnMap.put("sqrgyqkfe2", "______");
                    } else if (qlrmcList.length > 1) {
                        returnMap.put("sqrgyqkxm1", qlrmcList[0]);
                        returnMap.put("sqrgyqkzjzl1", zjlxList[0]);
                        returnMap.put("sqrgyqkzjhm1", zjhmList[0]);
                        returnMap.put("sqrgyqkgj1", "中国");
                        returnMap.put("sqrgyqkxm2", qlrmcList[1]);
                        returnMap.put("sqrgyqkzjzl2", zjlxList[0]);
                        returnMap.put("sqrgyqkzjhm2", zjhmList[1]);
                        returnMap.put("sqrgyqkgj2", "中国");
                        if (GYFS.equals("共同共有")) {
                            returnMap.put("sqrgyqk1_1", "□");
                            returnMap.put("sqrgyqk1_2", "■");
                            returnMap.put("sqrgyqk1_3", "□");
                            returnMap.put("sqrgyqkfe1", "______");
                            returnMap.put("sqrgyqk2_1", "■");
                            returnMap.put("sqrgyqk2_2", "□");
                            returnMap.put("sqrgyqkfe2", "______");
                        } else if (GYFS.equals("按份共有")) {
                            returnMap.put("sqrgyqk1_1", "□");
                            returnMap.put("sqrgyqk1_2", "□");
                            returnMap.put("sqrgyqk1_3", "■");
                            returnMap.put("sqrgyqkfe1", qlblList[0]);
                            returnMap.put("sqrgyqk2_1", "□");
                            returnMap.put("sqrgyqk2_2", "■");
                            returnMap.put("sqrgyqkfe2", qlblList[1]);
                        }
                    }
                    /* 不动产情况 */
                    returnMap.put("zl", daMap.get("FDZL"));
                    returnMap.put("dybdcdjzmh", daMap.get("BDCQZH"));
                }
            }
        } else {// 抵押权首次登记转本
            // 取义务人信息
            if (dyrMap != null) {
                String ywr = StringUtil.getValue(dyrMap, "YWR");// 名称
                String[] ywrList = ywr.split(",");
                String zjlx = StringUtil.getValue(dyrMap, "YWRZJZL");// 证件种类
                String[] ywrzjzlList = zjlx.split(",");
                String zjh = StringUtil.getValue(dyrMap, "YWRZJH");// 证件号
                String[] ywrzjhList = zjh.split(",");
                if (ywrList != null && ywrList.length > 0) {
                    returnMap.put("sqrgyqklxr", ywrList[0]);
                    returnMap.put("sqrgyqklxdz", "");// 联系地址
                    returnMap.put("sqrgyqklxdh", "");// 联系电话
                    // 共有情况
                    if (daMap != null) {
                        String GYFS = StringUtil.getString(daMap.get("GYFS"));
                        String qlbl = StringUtil.getValue(daMap, "QLBL");// 权利比例
                        String[] qlblList = qlbl.split(",");
                        if (GYFS.equals("单独所有") || (ywrList.length == 1)) {
                            returnMap.put("sqrgyqkxm1", ywrList[0]);
                            returnMap.put("sqrgyqkzjzl1", ywrzjzlList[0]);
                            returnMap.put("sqrgyqkzjhm1", ywrzjhList[0]);
                            returnMap.put("sqrgyqkgj1", "中国");
                            returnMap.put("sqrgyqk1_1", "■");
                            returnMap.put("sqrgyqk1_2", "□");
                            returnMap.put("sqrgyqk1_3", "□");
                            returnMap.put("sqrgyqkfe1", "______");

                            returnMap.put("sqrgyqk2_1", "□");
                            returnMap.put("sqrgyqk2_2", "□");
                            returnMap.put("sqrgyqkfe2", "______");
                        } else if (ywrList.length > 1) {
                            returnMap.put("sqrgyqkxm1", ywrList[0]);
                            returnMap.put("sqrgyqkzjzl1", ywrzjzlList[0]);
                            returnMap.put("sqrgyqkzjhm1", ywrzjhList[0]);
                            returnMap.put("sqrgyqkgj1", "中国");
                            returnMap.put("sqrgyqkxm2", ywrList[1]);
                            returnMap.put("sqrgyqkzjzl2", ywrzjzlList[0]);
                            returnMap.put("sqrgyqkzjhm2", ywrzjhList[1]);
                            returnMap.put("sqrgyqkgj2", "中国");
                            if (GYFS.equals("共同共有")) {
                                returnMap.put("sqrgyqk1_1", "□");
                                returnMap.put("sqrgyqk1_2", "■");
                                returnMap.put("sqrgyqk1_3", "□");
                                returnMap.put("sqrgyqkfe1", "______");
                                returnMap.put("sqrgyqk2_1", "■");
                                returnMap.put("sqrgyqk2_2", "□");
                                returnMap.put("sqrgyqkfe2", "______");
                            } else if (GYFS.equals("按份共有")) {
                                returnMap.put("sqrgyqk1_1", "□");
                                returnMap.put("sqrgyqk1_2", "□");
                                returnMap.put("sqrgyqk1_3", "■");
                                returnMap.put("sqrgyqkfe1", qlblList[0]);
                                returnMap.put("sqrgyqk2_1", "□");
                                returnMap.put("sqrgyqk2_2", "■");
                                returnMap.put("sqrgyqkfe2", qlblList[1]);
                            }
                        }
                        /* 不动产情况 */
                        returnMap.put("zl", daMap.get("FDZL"));
                        returnMap.put("dybdcdjzmh", dyrMap.get("BDCDJZMH"));
                    }
                }
            }
        }
    }

    /**
     * 描述:回填银行登记申请人字段（抵押人）
     *
     * @author Madison You
     * @created 2020/8/18 17:20:00
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void initYhdjsqrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        /* 根据单元号获取分户信息 */
        String BDCDYH = StringUtil.getValue(busInfo, "BDCDYH");
        List<Map<String, Object>> list = findBdcfhData(BDCDYH);
        Map<String, Object> map = list.get(0);
        /* 权利人信息JSON */
        String piJson = StringUtil.getValue(map, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            String GYTD_GYFS = StringUtil.getValue(map, "GYTD_GYFS");
            if (piList != null && piList.size() > 0) {
                Map<String, Object> piMapLxr = piList.get(0);
                returnMap.put("sqrgyqklxr", StringUtil.getValue(piMapLxr, "POWERPEOPLENAME"));
                returnMap.put("sqrgyqklxdz", StringUtil.getValue(piMapLxr, "POWERPEOPLEADDR"));
                returnMap.put("sqrgyqklxdh", StringUtil.getValue(piMapLxr, "POWERPEOPLEMOBILE"));

                if (GYTD_GYFS.equals("0")) {
                    Map<String, Object> piMap = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap, "POWERPEOPLENAME"));
                    /* 证件类型代码转换 */
                    String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                    String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                    returnMap.put("sqrgyqkzjzl1", documentType);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "■");
                    returnMap.put("sqrgyqk1_2", "□");
                    returnMap.put("sqrgyqk1_3", "□");
                    returnMap.put("sqrgyqkfe1", "______");

                    returnMap.put("sqrgyqk2_1", "□");
                    returnMap.put("sqrgyqk2_2", "□");
                    returnMap.put("sqrgyqkfe2", "______");
                } else if (GYTD_GYFS.equals("1")) {
                    Map<String, Object> piMap1 = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap1, "POWERPEOPLENAME"));
                    /* 证件类型代码转换 */
                    String powerpeopleidtype1 = StringUtil.getValue(piMap1, "POWERPEOPLEIDTYPE");
                    String documentType1 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype1);
                    returnMap.put("sqrgyqkzjzl1", documentType1);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap1, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "□");
                    returnMap.put("sqrgyqk1_2", "■");
                    returnMap.put("sqrgyqk1_3", "□");
                    returnMap.put("sqrgyqkfe1", "______");
                    if (piList.size() > 1) {
                        Map<String, Object> piMap2 = piList.get(1);
                        returnMap.put("sqrgyqkxm2", StringUtil.getValue(piMap2, "POWERPEOPLENAME"));
                        /* 证件类型代码转换 */
                        String powerpeopleidtype2 = StringUtil.getValue(piMap2, "POWERPEOPLEIDTYPE");
                        String documentType2 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype2);
                        returnMap.put("sqrgyqkzjzl2", documentType2);
                        returnMap.put("sqrgyqkzjhm2", StringUtil.getValue(piMap2, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj2", "中国");
                        returnMap.put("sqrgyqk2_1", "■");
                        returnMap.put("sqrgyqk2_2", "□");
                        returnMap.put("sqrgyqkfe2", "______");
                    }
                } else if (GYTD_GYFS.equals("2")) {
                    Map<String, Object> piMap1 = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap1, "POWERPEOPLENAME"));
                    /* 证件类型代码转换 */
                    String powerpeopleidtype1 = StringUtil.getValue(piMap1, "POWERPEOPLEIDTYPE");
                    String documentType1 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype1);
                    returnMap.put("sqrgyqkzjzl1", documentType1);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap1, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "□");
                    returnMap.put("sqrgyqk1_2", "□");
                    returnMap.put("sqrgyqk1_3", "■");
                    returnMap.put("sqrgyqkfe1", StringUtil.getValue(piMap1, "POWERPEOPLEPRO"));
                    if (piList.size() > 1) {
                        Map<String, Object> piMap2 = piList.get(1);
                        returnMap.put("sqrgyqkxm2", StringUtil.getValue(piMap2, "POWERPEOPLENAME"));
                        /* 证件类型代码转换 */
                        String powerpeopleidtype2 = StringUtil.getValue(piMap2, "POWERPEOPLEIDTYPE");
                        String documentType2 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype2);
                        returnMap.put("sqrgyqkzjzl2", documentType2);
                        returnMap.put("sqrgyqkzjhm2", StringUtil.getValue(piMap2, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj2", "中国");
                        returnMap.put("sqrgyqk2_1", "□");
                        returnMap.put("sqrgyqk2_2", "■");
                        returnMap.put("sqrgyqkfe2", StringUtil.getValue(piMap2, "POWERPEOPLEPRO"));
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 （抵押权首次登记）银行申请表签章
     * 
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:43
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bankSign(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception {
        // 1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",
                new String[] { "EXE_ID", "MATER_NAME" },
                new Object[] { flowVars.get("EFLOW_EXEID"), BdcQlcCreateSignFlowService.BANKSQS_MATERNAME });
        flowVars.put("SIGN_FLAG", true);// 设置签章标志位
        // 2.创建外部用户（经办人）
        String jbrName = StringUtil.getString(flowVars.get("JBR_NAME"));
        String jbrZjhm = StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String jbrZjlx = "IDCard";
        String jbrMobile = StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String jbrEmail = StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String, Object> user = new HashMap<>();
        user.put("sqrxm", jbrName);
        user.put("sqrzjhm", jbrZjhm);
        user.put("sqrzjlx", jbrZjlx);
        user.put("contactsEmail", jbrEmail);
        user.put("sqrsjhm", jbrMobile);
        Map<String, Object> exUser = bdcQlcMaterGenAndSignService.creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        } else {
            // 3.创建机构（银行&经办人信息）
            Map<String, Object> bank = bdcQlcMaterGenAndSignService.creBank(returnMap);
            if (!(boolean) bank.get(("INS_CREATEFLAG"))) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
            } else {
                // 5.文件直传
                Map<String, Object> fileResult = bdcQlcMaterGenAndSignService.getFileKey(
                        materSignInfo.get("MATER_PDFPATH").toString(), flowVars.get("EFLOW_EXEID").toString(),
                        BdcQlcCreateSignFlowService.BANKSQS_MATERNAME);
                if (!(boolean) fileResult.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                } else {
                    // 6.签署流程
                    Map<String, Object> result = bdcQlcCreateSignFlowService
                            .createSignFlowOfDyqscdjBankSqb(flowVars, returnMap);
                    if (!(boolean) result.get("SIGN_FLAG")) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                    }
                }
            }
        }
    }

    
     /**
     * 描述
     * @author Yanisin Shi
     * @param templateData
     * @param exeId
     * @see net.evecom.platform.bdc.service.BdcFwzldjbaService#getTemplateDataMapByExeId(java.util.Map, java.lang.String)
     * create time 2021年8月6日
     */
     
    @Override
    public void getTemplateDataMapByExeId(Map<String, Object> returnMap, String exeId) {
 
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("undefined")) {
            Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            //获取业务id
            String ywId=flowExe.get("BUS_RECORDID")==null?" ":flowExe.get("BUS_RECORDID").toString();
            if(ywId!=" "){
                Map<String, Object> fwzldjbaxx = this.executionService.getByJdbc("T_BDC_FWZLDJBA", new String[] { "YW_ID" },
                        new Object[] { ywId }); 
                if(!fwzldjbaxx.isEmpty()){
                    returnMap.put("fwzl", fwzldjbaxx.get("FW_ZL")==null?" ":fwzldjbaxx.get("FW_ZL").toString());
                    returnMap.put("jzmj", fwzldjbaxx.get("JZMJ")==null?" ":fwzldjbaxx.get("JZMJ").toString());
                    returnMap.put("fwjg", fwzldjbaxx.get("FW_JG")==null?" ":fwzldjbaxx.get("FW_JG").toString());
                    returnMap.put("ytsm", fwzldjbaxx.get("YTSM")==null?" ":fwzldjbaxx.get("YTSM").toString());
                    returnMap.put("zj", fwzldjbaxx.get("ZJ")==null?" ":fwzldjbaxx.get("ZJ").toString());
                    returnMap.put("qzh", fwzldjbaxx.get("QZH")==null?" ":fwzldjbaxx.get("QZH").toString());
                    returnMap.put("bazmbh", fwzldjbaxx.get("BAZMBH")==null?" ":fwzldjbaxx.get("BAZMBH").toString());
                    returnMap.put("czqxbegin", fwzldjbaxx.get("CZ_QX_BEGIN")==null?" ":fwzldjbaxx.get("CZ_QX_BEGIN").toString());
                    returnMap.put("czqxend", fwzldjbaxx.get("CZ_QX_END")==null?" ":fwzldjbaxx.get("CZ_QX_END").toString());
                    returnMap.put("slbz", fwzldjbaxx.get("SL_BZ")==null?" ":fwzldjbaxx.get("SL_BZ").toString());
                    returnMap.put("endtime", flowExe.get("END_TIME")==null?"   年  月   日":DateTimeUtil.getChinaDate(flowExe.get("END_TIME").toString()));
                    
                }
                 //获取承租人员信息 只取三条
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT * FROM T_BDC_FWZLDJBA_PERSONS s WHERE YW_ID = ? and person_type ='2' and rownum<=3 " );
                    List<Map<String, Object>> persons = dao.findBySql(sql.toString(), new Object[] { ywId }, null);
                  if(null != persons && persons.size() > 0){
                      for (int i=0;i<persons.size();i++){
                         
                          returnMap.put("personname"+i, persons.get(i).get("PERSON_NAME")==null?
                                      " ":persons.get(i).get("PERSON_NAME").toString()); 
                          returnMap.put("cardtype"+i, persons.get(i).get("CARD_TYPE")==null?
                                      " ":persons.get(i).get("CARD_TYPE").toString()); 
                          returnMap.put("cardno"+i, persons.get(i).get("CARD_NO")==null?
                                      " ":persons.get(i).get("CARD_NO").toString()); 
                             
                             
                          
                          }
                      }
                  //获取出租人信息 只取三条
                  StringBuffer czrsql= new StringBuffer();
                  czrsql.append(" SELECT * FROM T_BDC_FWZLDJBA_PERSONS s WHERE YW_ID = ? and person_type ='6' and rownum<=3 " );
                  List<Map<String, Object>> czrpersons = dao.findBySql(czrsql.toString(), new Object[] { ywId }, null);
                if(null != czrpersons && czrpersons.size() > 0){

                    for (int i=0;i<czrpersons.size();i++){
                       
                        returnMap.put("czrname"+i, czrpersons.get(i).get("PERSON_NAME")==null?
                                    " ":czrpersons.get(i).get("PERSON_NAME").toString()); 
                        returnMap.put("czrcardtype"+i, czrpersons.get(i).get("CARD_TYPE")==null?
                                    " ":czrpersons.get(i).get("CARD_TYPE").toString()); 
                        returnMap.put("czrcardno"+i, czrpersons.get(i).get("CARD_NO")==null?
                                    " ":czrpersons.get(i).get("CARD_NO").toString()); 
                           
                           
                        
                        }
                    }
                }
            }
        }

    /**
     * 描述
     * @author Yanisin Shi
     * @param templateData
     * @param exeId
     * @see net.evecom.platform.bdc.service.BdcFwzldjbaService#getTemplateDataMapByExeId(java.util.Map, java.lang.String)
     * create time 2021年8月6日
     */
     
    @Override
    public void getSqbTemplateDataMapByExeId(Map<String, Object> returnMap, String exeId) {
 
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("undefined")) {
            Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            //获取业务id
            String ywId=flowExe.get("BUS_RECORDID")==null?" ":flowExe.get("BUS_RECORDID").toString();
            if(ywId!=" "){
                Map<String, Object> fwzldjbaxx = this.executionService.getByJdbc("T_BDC_FWZLDJBA", new String[] { "YW_ID" },
                        new Object[] { ywId }); 
                if(!fwzldjbaxx.isEmpty()){
                    returnMap.put("fwzl", fwzldjbaxx.get("FW_ZL")==null?" ":fwzldjbaxx.get("FW_ZL").toString());
                    returnMap.put("jzmj", fwzldjbaxx.get("JZMJ")==null?" ":fwzldjbaxx.get("JZMJ").toString());
                    returnMap.put("fwjg", fwzldjbaxx.get("FW_JG")==null?" ":fwzldjbaxx.get("FW_JG").toString());
                    returnMap.put("ytsm", fwzldjbaxx.get("YTSM")==null?" ":fwzldjbaxx.get("YTSM").toString());
                    returnMap.put("zj", fwzldjbaxx.get("ZJ")==null?" ":fwzldjbaxx.get("ZJ").toString());
                    returnMap.put("qzh", fwzldjbaxx.get("QZH")==null?" ":fwzldjbaxx.get("QZH").toString());
                    returnMap.put("czrname", fwzldjbaxx.get("CZR_NAME")==null?" ":fwzldjbaxx.get("CZR_NAME").toString());
                    returnMap.put("czrcardtype", fwzldjbaxx.get("CZR_CARD_TYPE")==null?" ":fwzldjbaxx.get("CZR_CARD_TYPE").toString());
                    returnMap.put("czrcardno", fwzldjbaxx.get("CZR_CARD_NO")==null?" ":fwzldjbaxx.get("CZR_CARD_NO").toString());
                    returnMap.put("bazmbh", fwzldjbaxx.get("BAZMBH")==null?" ":fwzldjbaxx.get("BAZMBH").toString());
                    returnMap.put("czqxbegin", fwzldjbaxx.get("CZ_QX_BEGIN")==null?" ":fwzldjbaxx.get("CZ_QX_BEGIN").toString());
                    returnMap.put("czqxend", fwzldjbaxx.get("CZ_QX_END")==null?" ":fwzldjbaxx.get("CZ_QX_END").toString());
                     
                    returnMap.put("czbw", fwzldjbaxx.get("CZBW")==null?" ":fwzldjbaxx.get("CZBW").toString());
                    returnMap.put("czrphone", fwzldjbaxx.get("CZR_PHONE")==null?" ":fwzldjbaxx.get("CZR_PHONE").toString());
                     
                    
                }
                 //获取承租人员信息 只取三条
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT * FROM T_BDC_FWZLDJBA_PERSONS s WHERE YW_ID = ? and person_type ='2' and rownum<=3 " );
                    List<Map<String, Object>> persons = dao.findBySql(sql.toString(), new Object[] { ywId }, null);
                  if(null != persons && persons.size() > 0){
                    
                      for (int i=0;i<persons.size();i++){
                         
                          returnMap.put("personname"+i, persons.get(i).get("PERSON_NAME")==null?
                                      " ":persons.get(i).get("PERSON_NAME").toString()); 
                          returnMap.put("cardtype"+i, persons.get(i).get("CARD_TYPE")==null?
                                      " ":persons.get(i).get("CARD_TYPE").toString()); 
                          returnMap.put("cardno"+i, persons.get(i).get("CARD_NO")==null?
                                      " ":persons.get(i).get("CARD_NO").toString()); 
                             
                             
                          
                          }
                      }
                      
                   //代理人
                //获取承租人员信息 只取三条
                  StringBuffer dlrsql = new StringBuffer();
                  dlrsql.append(" SELECT * FROM T_BDC_FWZLDJBA_PERSONS s WHERE YW_ID = ? and person_type ='4' and rownum<=3 " );
                  List<Map<String, Object>> dlrpersons = dao.findBySql(dlrsql.toString(), new Object[] { ywId }, null);
                if(null != dlrpersons && dlrpersons.size() > 0){
                  
                    for (int i=0;i<dlrpersons.size();i++){
                       
                        returnMap.put("dlrname"+i, dlrpersons.get(i).get("PERSON_NAME")==null?
                                    " ":dlrpersons.get(i).get("PERSON_NAME").toString()); 
                        returnMap.put("dlrcardno"+i, dlrpersons.get(i).get("CARD_TYPE")==null?
                                    " ":dlrpersons.get(i).get("CARD_TYPE").toString()); 
                       
                           
                           
                        
                        }
                    }
                }
            }
        }
     /**
     * 描述
     * @author Yanisin Shi
     * @param args
     * @see net.evecom.platform.bdc.service.BdcFwzldjbaService#setBdcFwzldjData(java.util.Map)
     * create time 2021年8月17日
     */
     
    @Override
    public void setBdcFwzldjData(Map<String, Map<String, Object>> args) {
        //  /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcFwzldjbaService.bdcFwzldjInitSpbVariables(returnMap);

        /*设置房屋租赁首次登记业务数据*/
        bdcFwzldjbaService.setFwzldjData(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
        
    }

    
     /**
     * 描述   设置回填的业务数据
     * @author Yanisin Shi
     * @param returnMap
     * @param busInfo
     * @param execution
     * create time 2021年8月17日
     */
  
   
    public void setFwzldjData(Map<String, Object> returnMap, Map<String, Object> busInfo, Map<String, Object> execution) {
        // TODO Auto-generated method stub
      
        returnMap.put("qllx", "房屋租赁");
        returnMap.put("djlx", "房屋租赁首次登记");
        
        String sqfs = execution.get("SQFS") == null ? "" : execution.get("SQFS").toString();
        String exeid=execution.get("EXE_ID")==null? "":execution.get("EXE_ID").toString();
        
        String jbrName = StringUtil.getString(execution.get("JBR_NAME"));
        String jbrZjhm = StringUtil.getString(execution.get("JBR_ZJHM"));
        if(StringUtils.isNotEmpty(jbrName)){
            returnMap.put("jbrName", jbrName);
        }
        if(StringUtils.isNotEmpty(jbrZjhm)){
            returnMap.put("jbrZjhm", jbrZjhm);
         }
        String teplateName=returnMap.get("TeplateName")==null?" ":returnMap.get("TeplateName").toString();
        
        if(StringUtils.isNotEmpty(exeid)&&"BDCFWZLDJSQB".equals(teplateName)){
            getTemplateDataMapByExeId(returnMap, exeid);
        }
        if(StringUtils.isNotEmpty(exeid)&&"FWCZSQB".equals(teplateName)){
            getSqbTemplateDataMapByExeId(returnMap, exeid);
        }
        String sjr = "平潭综合实验区行政服务中心";
        if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
            sjr = StringUtil.getValue(execution, "CREATOR_NAME");
        }
        returnMap.put("sjr", sjr);
        returnMap.put("sjr", sjr);
    }

    
     /**
     * 描述  初始化模板数据
     * @author Yanisin Shi
     * @param returnMap
     * @see net.evecom.platform.bdc.service.BdcFwzldjbaService#bdcFwzldjInitSpbVariables(java.util.Map)
     * create time 2021年8月17日
     */
     
    @Override
    public void bdcFwzldjInitSpbVariables(Map<String, Object> returnMap) {
        returnMap.put("fwzl", "");
        returnMap.put("jzmj", "");
        returnMap.put("fwjg", "");
        returnMap.put("ytsm", "");
        returnMap.put("zj", "");
        returnMap.put("qzh", "");
        returnMap.put("czrname0", "");
        returnMap.put("czrcardtype0", "");
        returnMap.put("czrcardno0", "");
        returnMap.put("czrname1", "");
        returnMap.put("czrcardtype1", "");
        returnMap.put("czrcardno1", "");
        returnMap.put("czrname2", "");
        returnMap.put("czrcardtype2", "");
        returnMap.put("czrcardno2", "");
        returnMap.put("personname0", "");
        returnMap.put("cardtype0", "");
        returnMap.put("cardno0", "");
        returnMap.put("personname1", "");
        returnMap.put("cardtype1", "");
        returnMap.put("cardno1", "");
        returnMap.put("personname2", "");
        returnMap.put("cardtype2", "");
        returnMap.put("cardno2", "");
        returnMap.put("czbw", "");
        returnMap.put("czrphone", "");
        returnMap.put("jbrname", "");
        returnMap.put("jbrzjhm", "");
        returnMap.put("phone0", "");
        returnMap.put("dlrname", "");
        returnMap.put("dlrcardno", "");
        returnMap.put("bazmbh", "");
        returnMap.put("czqxbegin", "");
        returnMap.put("czqxend", "");
        
        returnMap.put("slbz", "");
        returnMap.put("endtime", "");
        
    }
    /**
     * 
     * 描述    需要去配置字典表数据关联业务表
     * @author Yanisin Shi
     * @param itemcode
     * @param type
     * @param busId
     * @return
     * create time 2021年8月18日
     */
    public Map<String, Object> getSubRecordInfo(String itemcode, String type, String busId) {
        // 获取子业务对应的表名（配置在数据字典中）
        List<Map<String, Object>> tables = dictionaryService.findByTypeCode(itemcode);
        String tableName = "", tabColName = "";
        if (tables != null && tables.size() > 0) {
            // 从数据字典中获取业务表对应关系
            for (Map<String, Object> map : tables) {
                String diccode = String.valueOf(map.get("DIC_CODE"));
                if (type.equals(diccode)) {
                    tableName = String.valueOf(map.get("DIC_NAME"));
                    // 主表主键，在子表中的字段名称配置在DIC_DESC
                    tabColName = String.valueOf(map.get("DIC_DESC"));
                    break;
                }
            }
        }
        if (!"".equals(tableName) && !"".equals(tabColName)) {
            Map<String, Object> record = dao.getByJdbc(tableName, new String[] { tabColName }, new Object[] { busId });
            if (record != null) {
                for (Map.Entry<String, Object> ent : record.entrySet()) {
                    if (ent.getKey().endsWith("_JSON")) {
                        if (ent.getValue() != null) {
                            if (ent.getValue() != null) {
                                Map<String, Object> items = new HashMap<String, Object>();
                                items.put("items", ent.getValue());
                                record.put(ent.getKey(), JSON.toJSON(items));
                            }
                        }
                    }
                }
            }
            return record;
        }
        return null;
    }

    
     /**
     * 描述
     * @author Yanisin Shi
     * @param filter
     * @return
     * @see net.evecom.platform.bdc.service.BdcFwzldjbaService#findBySqlFilter(net.evecom.core.util.SqlFilter)
     * create time 2021年8月23日
     */
     
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter) {
        // TODO Auto-generated method stub
        return dao.findBySqlFilter(filter);
    }

    
}