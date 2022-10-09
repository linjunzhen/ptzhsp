/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.pdfview.Flag;

import bsh.EvalError;
import bsh.Interpreter;
import icepdf.a;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceParamUtil;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.GenPlatReqUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.commercial.dao.CommercialSetDao;
import net.evecom.platform.commercial.service.CommercialSetService;
import net.evecom.platform.commercial.service.ExtraDictionaryService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.yb.service.YbCommonService;
import net.evecom.platform.zzhy.service.CancelService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年7月11日 上午9:12:32
 */
@Service("commercialSetService")
public class CommercialSetServiceImpl extends BaseServiceImpl implements CommercialSetService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CommercialSetServiceImpl.class);

    /**
     * 引入dao
     */
    @Resource
    private CommercialSetDao dao;
    
    /**
     * 引入service
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 引入service
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入service
     */
    @Resource
    private ExtraDictionaryService extraDictionaryService;
    
    /**
     * 引入Service
     */
    @Resource
    private CancelService cancelService;
    
    /**
     * 覆盖获取实体dao方法
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 获取1+N关联事项
     * @author Danto Huang
     * @created 2017年7月11日 下午2:53:40
     * @param filter
     * @return
     */
    public List<Map<String ,Object>> getRelatedItems(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name from T_COMMERCIAL_RELATED_ITEM t ");
        sql.append("left join t_msjw_system_department d on d.depart_code=t.ssbmbm ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 关联事项材料
     * @author Danto Huang
     * @created 2017年7月12日 下午5:40:04
     * @param itemCodes
     * @return
     */
    public List<Map<String ,Object>> getRelatedItemMater(String itemCodes){
        StringBuffer sql = new StringBuffer();
        sql.append("select s.item_code,s.item_name,a.mater_name,a.mater_code,t.mater_isneed,a.mater_clsmlx,");
        sql.append("a.mater_clsm from T_WSBS_SERVICEITEM_MATER t ");
        sql.append("left join t_wsbs_serviceitem s on s.item_id = t.item_id ");
        sql.append("left join t_wsbs_applymater a on a.mater_id = t.mater_id ");
        sql.append("where s.item_code in ").append(StringUtil.getValueArray(itemCodes));
        sql.append(" order by s.item_code,t.mater_sn");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
        return list;
    }
    
    /**
     * 
     * 获取关联事项材料列表
     * @author Danto Huang
     * @created 2017年8月23日 上午9:32:32
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findRelatedItemMater(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select t.*,a.mater_name from T_COMMERCIAL_RELATMATER t ");
        sql.append("left join t_wsbs_applymater a on a.mater_id = t.mater_id ");
        sql.append("where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    

    /**
     * 
     *  根据事项Codes获取材料信息列表
     * @author Danto Huang
     * @created 2017年8月24日 上午8:40:26
     * @param itemCodes
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findMaterByItemCodes(String itemCodes,String exeId){
        StringBuffer sql = new StringBuffer("SELECT SB.BUSCLASS_NAME,M.MATER_ID,M.MATER_CODE,");
        sql.append("M.MATER_NAME,M.MATER_TYPE,M.MATER_SIZE,M.MATER_DESC,");
        sql.append("M.MATER_CLSMLX,M.MATER_CLSM,M.MATER_PARENTCODE,S.ITEM_CODE,S.ITEM_NAME,");
        sql.append("M.MATER_PATH,SM.MATER_ISNEED,M.CLLX,SM.MATER_FPARA,");
        sql.append("M.SUPPORT_WORD,M.MATER_FILTER,M.TAG_ID,SM.IS_FORM,SM.FORM_NAME ");
        sql.append("FROM T_WSBS_APPLYMATER M ");
        sql.append("LEFT JOIN T_COMMERCIAL_RELATMATER SM ON M.MATER_ID=SM.MATER_ID ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_BUSCLASS SB ON M.MATER_SSYW = SB.RECORD_ID ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM S ON S.ITEM_ID = SM.ITEM_ID ");
        sql.append("WHERE SM.ITEM_ID in(");
        sql.append("select t.item_id from T_WSBS_SERVICEITEM t where t.item_code in ");
        sql.append(StringUtil.getValueArray(itemCodes));
        sql.append(") ");
        sql.append(" ORDER BY S.ITEM_CODE,SM.MATER_SN ASC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
        List<Map<String, Object>> maters = new ArrayList<Map<String, Object>>();
        Map<String, Object> flowInfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(exeId) && !"null".equals(exeId)) {
            Map<String, Object> flowExe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            flowInfo = flowExe;
        }
        for (Map<String, Object> mater : list) {
            String materParam = (String) mater.get("MATER_FPARA");
            if (StringUtils.isNotEmpty(materParam)) {
                String isAdd = "";
                StringBuffer exeCode = new StringBuffer("if(").append(materParam).append("){ isAdd=\"true\";}");
                try {
                    Interpreter it = new Interpreter();
                    it.set("flowInfo", flowInfo);
                    it.eval(exeCode.toString());
                    isAdd = (String) it.get("isAdd");
                    if (StringUtils.isNotEmpty(isAdd) && isAdd.equals("true")) {
                        maters.add(mater);
                    }
                } catch (EvalError e) {
                    log.error(e.getMessage(),e);
                }
            } else {
                maters.add(mater);
            }
        }
        return maters;
    }
    /**
     * 
     * 描述 验证1+N关联事项是否归属当前登录审核人
     * @author Danto Huang
     * @created 2017年9月28日 下午5:16:14
     * @param username
     * @param itemCode
     * @param currNodeName
     * @return
     */
    public Map<String,Object> checkAuditerRelatedItem(String username,String itemCode,String currNodeName){
        Map<String,Object> result = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_SERVICEITEM_AUDITER t ");
        sql.append("where t.item_id = (select s.item_id from t_wsbs_serviceitem s where s.item_code = ?) ");
        sql.append("and t.def_id=(select s.bdlcdyid from t_wsbs_serviceitem s where s.item_code = ?) ");
        sql.append("and t.node_type='task' and t.node_name<>'网上预审' ");
        sql.append("order by t.node_key ");
        List<Map<String, Object>> nodes = dao.findBySql(sql.toString(),new Object[] { itemCode, itemCode }, null);
        String userAccounts = "";
        if(currNodeName.contains("预审")){
            userAccounts = nodes.get(0).get("USER_ACCOUNT").toString();
        }else if(currNodeName.contains("审批")){
            userAccounts = nodes.get(nodes.size()-2).get("USER_ACCOUNT").toString();
        }
        String[] userAccount = userAccounts.split(",");
        boolean flag = false;
        for(int j=0;j<userAccount.length;j++){
            if(userAccount[j].equals(username)){
                flag = true;
            }
        }
        if(flag){
            Map<String, Object> item = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });
            result.put("resultFlag", true);
            result.put("itemCode", itemCode);
            result.put("itemName", item.get("ITEM_NAME"));
        }else{
            result.put("resultFlag", false);
        }
        return result;
    }
    /**
     * 
     * 描述 将环节审核转换成多人审核(基于关联事项)
     * @author Danto Huang
     * @created 2017年9月29日 下午2:41:32
     * @param nodeName
     * @param handlers
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FlowNextStep> conventTaskToMulitItem(String nodeName, List<FlowNextHandler> handlers,
            Map<String, Object> flowVars){
        List<FlowNextStep> steps = new ArrayList<FlowNextStep>();
        //定义事项MAP
        Map<String,List<FlowNextHandler>> itemMap = new HashMap<String,List<FlowNextHandler>>();
        String exeId = flowVars.get("EFLOW_EXEID").toString();
        Map<String,Object> exe = dao.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{exeId});
        String tableName = (String) exe.get("BUS_TABLENAME");
        if(tableName.equals("T_COMMERCIAL_DOMESTIC")||tableName.equals("T_COMMERCIAL_FOREIGN")){
            tableName = "T_COMMERCIAL_COMPANY";
        }
        String primaryKey = dao.getPrimaryKeyName(tableName).get(0).toString();
        String[] itemCodes = dao
                .getByJdbc(tableName, new String[] { primaryKey }, new Object[] { exe.get("BUS_RECORDID") })
                .get("ITEMCODES").toString().split(",");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_SERVICEITEM_AUDITER t ");
        sql.append("where t.item_id = (select s.item_id from t_wsbs_serviceitem s where s.item_code = ?) ");
        sql.append("and t.def_id=(select s.bdlcdyid from t_wsbs_serviceitem s where s.item_code = ?) ");
        sql.append("and t.node_type='task' and t.node_name<>'网上预审' ");
        sql.append("order by t.node_key ");
        for(int i=0;i<itemCodes.length;i++){
            List<Map<String, Object>> nodes = dao.findBySql(sql.toString(),
                    new Object[] { itemCodes[i], itemCodes[i] }, null);
            String userAccounts = "";
            if(nodeName.contains("预审")){
                userAccounts = nodes.get(0).get("USER_ACCOUNT").toString();
            }else if(nodeName.contains("审批")){
                userAccounts = nodes.get(nodes.size()-2).get("USER_ACCOUNT").toString();
            }            
            List<FlowNextHandler> handlerList = itemMap.get(itemCodes[i]);
            for(FlowNextHandler handle:handlers){
                if(!(handlerList!=null&&handlerList.size()>0)){
                    handlerList = new ArrayList<FlowNextHandler>();
                }
                String[] userAccount = userAccounts.split(",");
                for(int j=0;j<userAccount.length;j++){
                    if(userAccount[j].equals(handle.getNextStepAssignerCode())){
                        if(!handlerList.contains(handle)){
                            handlerList.add(handle);
                        }
                    }
                }
                /*if(userAccounts.indexOf(handle.getNextStepAssignerCode())!=-1){
                    if(!handlerList.contains(handle)){
                        handlerList.add(handle);
                    }
                }*/
            }
            if(handlerList!=null&&handlerList.size()>0){
                itemMap.put(itemCodes[i], handlerList);
            }
        }
        Iterator it = itemMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,List<FlowNextHandler>> entry = (Map.Entry<String,List<FlowNextHandler>>) it.next();
            List<FlowNextHandler> handlerList = entry.getValue();
            FlowNextStep step = new FlowNextStep();
            step.setFlowNodeName(nodeName);
            step.setHandlers(handlerList);
            step.setTaskNature(Jbpm6Constants.TASKNATURE_TEAM);
            step.setIsFree(-1);
            steps.add(step);
        }
        return steps;
    }
    
    /**
     *     
     * 描述  商事银行预审审核人获取
     * @author Danto Huang
     * @created 2017年11月21日 上午9:47:22
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getBankUsers(Map<String,Object> flowVars,
            String varName,String handlerRule){
        String isAccountOpen = flowVars.get("IS_ACCOUNT_OPEN").toString();
        List<FlowNextHandler> handlers = new ArrayList<FlowNextHandler>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from t_msjw_system_sysuser t ");
        sql.append("left join t_msjw_system_sysuser_sysrole ur on ur.user_id = t.user_id ");
        sql.append("left join t_msjw_system_sysrole r on r.role_id = ur.role_id ");
        sql.append("where r.role_code=? ");
        if(isAccountOpen.equals("1")&&flowVars.get("BANK_TYPE")!=null){
            String bankType = flowVars.get("BANK_TYPE").toString();
            List<Map<String,Object>> users = dao.findBySql(sql.toString(), new Object[]{bankType}, null);
            if(users!=null&&users.size()>0){
                for(Map<String,Object> user : users){
                    FlowNextHandler handler = new FlowNextHandler();
                    handler.setNextStepAssignerCode(user.get("USERNAME").toString());
                    handler.setNextStepAssignerName(user.get("FULLNAME").toString());
                    handlers.add(handler);
                }
            }
        }
        return handlers;
    }
    
    /**
     * 
     * 描述  获取支撑平台字典数据
     * @author Danto Huang
     * @created 2018年6月7日 上午11:26:36
     * @param param
     * @return
     */
    public List<Map<String,Object>> findDatasForSelectFromDevbase(String param){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("params", param);
        List<Map> list = GenPlatReqUtil.findDatas("pt_division_list", params);
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> data:list){
            Map<String,Object> dic = new HashMap<String, Object>();
            dic.put("TEXT", data.get("LABEL"));
            dic.put("VALUE", data.get("VALUE"));
            returnList.add(dic);
        }
        return returnList;
    }

    /**
     * 描述:公章刻制材料下载数据
     *
     * @author Madison You
     * @created 2019/9/4 11:00:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findSealMaterData(SqlFilter filter) {
        ArrayList<String> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("");
        sql.append("select * from (  ");
        sql.append(" select b.EXE_ID , a.COMPANY_NAME , c.dic_name is_sealed , b.CREATE_TIME ,a.ISEMAIL, ");
        sql.append(" b.BUS_TABLENAME,a.COMPANY_ID , a.EMS_NUMBER from T_COMMERCIAL_COMPANY a ");
        sql.append(" left join JBPM6_EXECUTION b on b.BUS_RECORDID = a.COMPANY_ID ");
        sql.append(" left join (select dic_code , dic_name from t_msjw_system_dictionary ");
        sql.append(" where type_code = 'YesOrNo') c on c.dic_code = a.is_sealed ");
        sql.append(" where a.IS_ENGRAVE_SEAL = 1 and b.RUN_STATUS = '2' ");
        sql.append(" union all ");
        sql.append(" select b.EXE_ID , a.BRANCH_NAME COMPANY_NAME , c.dic_name is_sealed , b.CREATE_TIME ,a.ISEMAIL, ");
        sql.append(" b.BUS_TABLENAME,a.BRANCH_ID COMPANY_ID , a.EMS_NUMBER from T_COMMERCIAL_BRANCH a ");
        sql.append(" left join JBPM6_EXECUTION b on b.BUS_RECORDID = a.BRANCH_ID ");
        sql.append(" left join (select dic_code , dic_name from t_msjw_system_dictionary ");
        sql.append(" where type_code = 'YesOrNo') c on c.dic_code = a.is_sealed ");
        sql.append(" where a.IS_ENGRAVE_SEAL = 1 and b.RUN_STATUS = '2' ");
        sql.append(" union all ");
        sql.append(" select b.EXE_ID , a.COMPANY_NAME , c.dic_name is_sealed , b.CREATE_TIME ,a.ISEMAIL, ");
        sql.append(" b.BUS_TABLENAME,a.INTERNAL_STOCK_ID COMPANY_ID,a.EMS_NUMBER from T_COMMERCIAL_INTERNAL_STOCK a ");
        sql.append(" left join JBPM6_EXECUTION b on b.BUS_RECORDID = a.INTERNAL_STOCK_ID ");
        sql.append(" left join (select dic_code , dic_name from t_msjw_system_dictionary ");
        sql.append(" where type_code = 'YesOrNo') c on c.dic_code = a.is_sealed ");
        sql.append(" where a.IS_ENGRAVE_SEAL = 1 and b.RUN_STATUS = '2' ");
        sql.append(" union all ");
        sql.append(" select b.EXE_ID , a.COMPANY_NAME , c.dic_name is_sealed , b.CREATE_TIME ,a.ISEMAIL, ");
        sql.append(" b.BUS_TABLENAME,a.SOLELY_ID COMPANY_ID , a.EMS_NUMBER from T_COMMERCIAL_SOLELYINVEST a ");
        sql.append(" left join JBPM6_EXECUTION b on b.BUS_RECORDID = a.SOLELY_ID ");
        sql.append(" left join (select dic_code , dic_name from t_msjw_system_dictionary ");
        sql.append(" where type_code = 'YesOrNo') c on c.dic_code = a.is_sealed ");
        sql.append(" where a.IS_ENGRAVE_SEAL = 1 and b.RUN_STATUS = '2' ");
        sql.append(" union all ");
        sql.append(" select b.EXE_ID , a.COMPANY_NAME , c.dic_name is_sealed , b.CREATE_TIME ,a.ISEMAIL, ");
        sql.append(" b.BUS_TABLENAME,a.COMPANY_ID , a.EMS_NUMBER from t_Commercial_Nz_Jointventure a  ");
        sql.append(" left join JBPM6_EXECUTION b on b.BUS_RECORDID = a.COMPANY_ID  ");
        sql.append("    left join (select dic_code , dic_name from t_msjw_system_dictionary  ");
        sql.append("   where type_code = 'YesOrNo') c on c.dic_code = a.is_sealed  ");
        sql.append("   where a.IS_ENGRAVE_SEAL = 1 and b.RUN_STATUS = '2' ) b WHERE 1=1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String,Object>> list = dao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述:公章刻制材料详细信息
     *
     * @author Madison You
     * @created 2019/9/4 17:26:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> findSealMaterDetailData(SqlFilter filter) {
        String companyId = filter.getRequest().getParameter("COMPANY_ID");
        String bustableName = filter.getRequest().getParameter("BUS_TABLENAME");
        Map<String,Object> conpanyInfo = new HashMap<String, Object>();
        if(bustableName.equals("T_COMMERCIAL_DOMESTIC")){
            conpanyInfo = dao.getByJdbc("T_COMMERCIAL_COMPANY",
                    new String[]{"COMPANY_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_NZ_JOINTVENTURE")){
            conpanyInfo = dao.getByJdbc("T_COMMERCIAL_NZ_JOINTVENTURE",
                    new String[]{"COMPANY_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_INTERNAL_STOCK")){
            conpanyInfo = dao.getByJdbc("T_COMMERCIAL_INTERNAL_STOCK",
                    new String[]{"INTERNAL_STOCK_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_BRANCH")){
            conpanyInfo = dao.getByJdbc("T_COMMERCIAL_BRANCH",
                    new String[]{"BRANCH_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_SOLELYINVEST")){
            conpanyInfo = dao.getByJdbc("T_COMMERCIAL_SOLELYINVEST",
                    new String[]{"SOLELY_ID"}, new Object[]{companyId});
        }
        Map<String,Object> busRecord = dao.getByJdbc("JBPM6_EXECUTION",
            new String[]{"bus_recordid"}, new Object[]{companyId});
        if (conpanyInfo != null) {
            busRecord.putAll(conpanyInfo);
        }
        return busRecord;
    }
    
    /**
     * 
     * 描述    商事登记-企业医保开户
     * @author Allin Lin
     * @created 2021年4月21日 上午9:31:31
     * @param flowDatas
     * @return
     */
    public Map<String, Object> openYbAccount(Map<String, Object> flowDatas){
        Map<String,Object> pushInfo = new  HashMap<String, Object>();//推送信息
        String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        //获取需开办医保账户数据
        if(StringUtils.isNotEmpty(exeId)){
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String isOpenYbAccount = StringUtil.getString(busRecord.get("IS_OPEN_YBACCOUNT"));
            //判断是否需要开户医保账户
            if(StringUtils.isNotEmpty(isOpenYbAccount) && "1".equals(isOpenYbAccount)){
                //基本信息体
                Map<String, Object> basic = new HashMap<String, Object>();
                String dwmc = StringUtil.getString(busRecord.get("COMPANY_NAME"));//单位名称
                String dwlx = "15";//单位类型(默认传私营企业)
                String xzqh =  StringUtil.getString("350128");//所属行政区划(默认平潭县)
                String dwlb =  "150";//单位类别(默认150);
                String lsgx =  "05";//隶属关系(默认05);
                String tsdwlx =  "0";//特殊单位类型(默认0);
                String clbm =  StringUtil.getString(busRecord.get("APPROVAL_AUTHORITY")); //批准成立部门
                String clrq =  StringUtil.getString(busRecord.get("APPROVAL_DATE"));//批准成立日期
                String clwh =  StringUtil.getString(busRecord.get("APPROVAL_NO"));//批准成立文号
                String dqbh =  "23";//地区编号(平潭为23);
                String xydm =  StringUtil.getString(busRecord.get("SOCIAL_CREDITUNICODE"));//社会统一信用代码
                String yzbm =  StringUtil.getString(busRecord.get("POST_CODE"));//邮政编码
                String dz =  StringUtil.getString(busRecord.get("DEPOSITOR_ADDR"));//地址
                String frxm =  StringUtil.getString(busRecord.get("LEGAL_NAME")); //法人姓名
                String frzjhm =  StringUtil.getString(busRecord.get("LEGAL_IDNO"));//法人证件号码
                String frzwCode =  StringUtil.getString(busRecord.get("LEGAL_JOB"));
                String frzw = dictionaryService.findByDicCodeAndTypeCode(frzwCode,"ssdjzw");//法人职务
                String lxrxm =  StringUtil.getString(busRecord.get("OPERATOR_NAME"));//联系人姓名
                String lxdh =  StringUtil.getString(busRecord.get("OPERATOR_MOBILE")); //联系电话
                String dwdh =  StringUtil.getString(busRecord.get("CONTACT_PHONE"));//单位电话
                String lxrzjhm =  StringUtil.getString(busRecord.get("OPERATOR_IDNO")); //联系人证件号码
                basic.put("aab004", dwmc);
                basic.put("aab019", dwlx);
                basic.put("aab301", xzqh);
                basic.put("aab020", dwlb);
                basic.put("aab021", lsgx);
                basic.put("aab065", tsdwlx);
                basic.put("aae149", dqbh);
                basic.put("aae048", clbm);
                basic.put("aae049", clrq);
                basic.put("aae051", clwh);
                basic.put("aab090", xydm);
                basic.put("aae007", yzbm);
                basic.put("aae006", dz);
                basic.put("aab013", frxm);
                basic.put("aab014", frzjhm);
                basic.put("bab500", frzw);
                basic.put("aae004", lxrxm);
                basic.put("aae005", lxdh);
                basic.put("aab069", dwdh);
                basic.put("bae976", lxrzjhm);
                //险种公共信息体
                Map<String, Object> insuCommon = new HashMap<String, Object>();
                //险种信息体(传默认)
                List<Map<String, Object>> insuList = new ArrayList<Map<String,Object>>();
                Map<String, Object> insu1 = new HashMap<String, Object>();
                insu1.put("aae140", "310");
                insu1.put("aab033", "1");
                insu1.put("aae041", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//参保开始日期
                insu1.put("aae042", "99991231");//参保截止日期
                Map<String, Object> insu2 = new HashMap<String, Object>();
                insu2.put("aae140", "510");
                insu2.put("aab033", "1");
                insu2.put("aae041", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//参保开始日期
                insu2.put("aae042", "99991231");//参保截止日期
                Map<String, Object> insu3 = new HashMap<String, Object>();
                insu3.put("aae140", "610");//商业补充保险
                insu3.put("aab033", "1");
                insu3.put("aae041", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyyMMdd"));//参保开始日期
                insu3.put("aae042", "99991231");//参保截止日期
                insuList.add(insu1);
                insuList.add(insu2);
                insuList.add(insu3);
                //调用医保开户接口
                pushInfo.put("basic", basic);
                pushInfo.put("insuCommon", insuCommon);
                pushInfo.put("insuList", insuList);
                Map<String,Object> tokenMap = ybCommonService.getToken();
                AjaxJson ajaxJson ;
                Map<String, Object> record = new HashMap<String, Object>();
                if((boolean) tokenMap.get("success")){
                    pushInfo.put("token", tokenMap.get("token"));
                    ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "openAccount"); 
                    if(ajaxJson.isSuccess()){              
                        //新增记录表信息
                        record.put("EXE_ID", exeId);
                        record.put("PUSHINFO", JSON.toJSONString(pushInfo));
                        record.put("STATUS", "1");
                        this.saveOrUpdate(record, "T_COMMERCIAL_YBRECORD", null);
                    }else{
                        record.put("EXE_ID", exeId);
                        record.put("PUSHINFO", JSON.toJSONString(pushInfo));
                        record.put("STATUS", "0");
                        record.put("RETURN_MSG", ajaxJson.getMsg());
                        this.saveOrUpdate(record, "T_COMMERCIAL_YBRECORD", null);
                    }
                }else{
                    log.info("获取token值失败！");
                    record.put("EXE_ID", exeId);
                    record.put("PUSHINFO", JSON.toJSONString(pushInfo));
                    record.put("STATUS", "0");
                    record.put("RETURN_MSG", "获取token值失败！");
                    this.saveOrUpdate(record, "T_COMMERCIAL_YBRECORD", null);
                }
            }
        }
        return flowDatas;
    }
    
    
    /**
     * 
     * 描述    商事登记-推送企业信息开办医保账户（推送失败重复推送）
     * @author Allin Lin
     * @created 2021年4月21日 下午4:43:08
     */
    @SuppressWarnings("unchecked")
    public void pushYbAccount(){
        List<Map<String, Object>> failList = this.getAllByJdbc("T_COMMERCIAL_YBRECORD",
                new String[]{"STATUS"}, new Object[]{"0"});
        for (Map<String, Object> fail : failList) {
            Map<String,Object> tokenMap = ybCommonService.getToken();
            AjaxJson ajaxJson ;
            if((boolean) tokenMap.get("success")){
                Map<String, Object> pushInfo = JSON.parseObject(StringUtil.getString(fail.get("PUSHINFO")));
                pushInfo.put("token", tokenMap.get("token"));
                ajaxJson = ybCommonService.pushInfoOfYb(pushInfo, "openAccount"); 
                if(ajaxJson.isSuccess()){              
                    //更新记录表信息
                    fail.put("STATUS", "1");
                    this.saveOrUpdate(fail, "T_COMMERCIAL_YBRECORD", StringUtil.getString(fail.get("RECORD_ID")));
                }else{
                    fail.put("STATUS", "0");
                    fail.put("RETURN_MSG", ajaxJson.getMsg());
                    this.saveOrUpdate(fail, "T_COMMERCIAL_YBRECORD", StringUtil.getString(fail.get("RECORD_ID")));
                }
            }else{
                fail.put("STATUS", "0");
                fail.put("RETURN_MSG", "获取token值失败！");
                this.saveOrUpdate(fail, "T_COMMERCIAL_YBRECORD", StringUtil.getString(fail.get("RECORD_ID")));
            }
        }
    }
    
    /**
     * 
     * 描述    内资变更登记业务数据回填
     * @author Allin Lin
     * @created 2021年4月25日 上午11:57:07
     * @param busRecord
     */
    public void setCommercialChangeDomestic(Map<String, Object> busRecord,String fileName,String stockFrom){
        
        /*企业登记（备案申请书）*/
        setJbxx(busRecord);//基本信息
        setHolderCzList(busRecord);//股东（发起人）、外国投资者出资情况
        setBgXzList(busRecord);//变更内容
        setDsJsJlList(busRecord);//董事、监事、经理信息
        
        
        /*决定、决议相关材料*/
        List<Object> yrgdjdYgdList = new LinkedList<Object>();//一人股东决定（原股东）
        List<Object> yrgdjdXgdList = new LinkedList<Object>();//一人股东决定（新股东）
        List<Object> gdhyYgdList = new LinkedList<Object>();// 股东会决议（原股东）
        List<Object> gdhyXgdList = new LinkedList<Object>();// 股东会决议（新股东）
        List<Object> dshyList = new LinkedList<Object>();// 董事会决议
        
        setJyList(busRecord,yrgdjdYgdList,yrgdjdXgdList,gdhyYgdList,gdhyXgdList,dshyList);
        busRecord.put("yrgdjdYgdList", yrgdjdYgdList);
        busRecord.put("yrgdjdXgdList", yrgdjdXgdList);
        busRecord.put("gdhyYgdList",gdhyYgdList);
        busRecord.put("gdhyXgdList",gdhyXgdList);
        busRecord.put("dshyList",dshyList);
        
        /*经营场所申报承诺表*/
        setIndividual(busRecord);

        /*章程相关*/
        if(fileName.indexOf("章程")>=0){
            setAssociation(busRecord);
        }
        
        /*股权转让协议相关*/
        if(fileName.indexOf("股权转让协议")>=0){
            setStockFrom(busRecord,stockFrom);
        }
        
        /*设置面签信息回填到模板*/
        WordReplaceParamUtil wordReplaceParamUtil = new WordReplaceParamUtil();
        wordReplaceParamUtil.setSignInfo(busRecord);
    }
    
    
    
    /**
     * 
     * 描述    基本信息
     * @author Allin Lin
     * @created 2021年4月25日 上午11:59:52
     */
    private void setJbxx(Map<String, Object> busRecord){
        busRecord.put("COMPANY_NAME", StringUtil.getString(busRecord.get("COMPANY_NAME")));
        busRecord.put("GROUP_NAME", StringUtil.getString(busRecord.get("GROUP_NAME")));
        busRecord.put("GROUP_ABBRE", StringUtil.getString(busRecord.get("GROUP_ABBRE")));
        busRecord.put("SOCIAL_CREDITUNICODE", StringUtil.getString(busRecord.get("SOCIAL_CREDITUNICODE")));
        busRecord.put("REGISTER_ADDR", StringUtil.getString(busRecord.get("REGISTER_ADDR")));
        busRecord.put("CONTACT_PHONE", StringUtil.getString(busRecord.get("CONTACT_PHONE")));
        //指定代表、委托代理人
        busRecord.put("OPERATOR_FIXEDLINE", StringUtil.getString(busRecord.get("OPERATOR_FIXEDLINE")));
        busRecord.put("OPERATOR_MOBILE", StringUtil.getString(busRecord.get("OPERATOR_MOBILE")));
        busRecord.put("OPERATOR_WRITE", StringUtil.getString(busRecord.get("OPERATOR_WRITE")));
        busRecord.put("OPERATORIDPHOTOFRONT", " ");
        busRecord.put("OPERATORIDPHOTOBACK", " ");
        //申请承诺人
        busRecord.put("LEGAL_WRITE", "");
        busRecord.put("SIGNTIME", "");
        //法定代表人信息
        busRecord.put("LEGAL_CHANGE_NAME", StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")));
        String LEGAL_CHANGE_COUNTRY = "";
        if(busRecord.get("LEGAL_COUNTRY_CHANGE")!=null){
            LEGAL_CHANGE_COUNTRY = StringUtil.getString(extraDictionaryService.
                    get("state", StringUtil.getString(busRecord.get("LEGAL_COUNTRY_CHANGE"))).get("DIC_NAME"));
        }
        busRecord.put("LEGAL_CHANGE_COUNTRY",LEGAL_CHANGE_COUNTRY);
        busRecord.put("LEGAL_CHANGE_JOB", StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))));
        busRecord.put("LEGAL_CHANGE_PRODUCEMODE", StringUtil.getString(dictionaryService.getDicNames("ssdjcsfs",
                StringUtil.getString(busRecord.get("LEGAL_PRODUCEMODE_CHANGE")))));
        busRecord.put("LEGAL_BG_IDTYPE", StringUtil.getString(dictionaryService.getDicNames("DocumentType",
                StringUtil.getString(busRecord.get("LEGAL_IDTYPE_CHANGE")))));
        busRecord.put("LEGAL_BG_IDNO", StringUtil.getString(busRecord.get("LEGAL_IDNO_CHANGE")));
        busRecord.put("LEGAL_CHANGE_FIXEDLINE", StringUtil.getString(busRecord.get("LEGAL_FIXEDLINE_CHANGE")));
        busRecord.put("LEGAL_CHANGE_MOBILE", StringUtil.getString(busRecord.get("LEGAL_MOBILE_CHANGE")));
        busRecord.put("LEGAL_CHANGE_ADDRESS", StringUtil.getString(busRecord.get("LEGAL_ADDRESS_CHANGE")));
        busRecord.put("LEGAL_CHANGE_EMAIL", StringUtil.getString(busRecord.get("LEGAL_EMAIL_CHANGE")));
        busRecord.put("LEGALPHOTOFRONT", "");
        busRecord.put("LEGALPHOTOBACK", "");
        //联络员信息
        busRecord.put("LIAISON_NAME", StringUtil.getString(busRecord.get("LIAISON_NAME")));
        busRecord.put("LIAISON_FIXEDLINE", StringUtil.getString(busRecord.get("LIAISON_FIXEDLINE")));
        busRecord.put("LIAISON_MOBILE", StringUtil.getString(busRecord.get("LIAISON_MOBILE")));
        busRecord.put("LIAISON_EMAIL", StringUtil.getString(busRecord.get("LIAISON_EMAIL")));
        busRecord.put("LIAISON_IDTYPE",  StringUtil.getString(dictionaryService.getDicNames("DocumentType",
                StringUtil.getString(busRecord.get("LIAISON_IDTYPE")))));
        busRecord.put("LIAISON_IDNO", StringUtil.getString(busRecord.get("LIAISON_IDNO")));
        busRecord.put("LIAISONPHOTOFRONT","");
        busRecord.put("LIAISONPHOTOBACK","");
        //企业名称（变更后）
        if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")))){
            busRecord.put("COMPANY_CHANGE_NAME", StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")));
        }else{
            busRecord.put("COMPANY_CHANGE_NAME", StringUtil.getString(busRecord.get("COMPANY_NAME")));
        }
    }
    /**
     * 
     * 描述    变更选项内容集合
     * @author Allin Lin
     * @created 2021年4月25日 上午11:59:52
     */
    @SuppressWarnings("unchecked")
    private void setBgXzList(Map<String, Object> busRecord){
        String baxz = "□";//备案（董事、监事、经理）
        String baxz01 = "□";//备案（董事）
        String baxz02 = "□";//备案（监事）
        String baxz03 = "□";//备案（经理）
        String baxz04 = "□";//联络员
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        List<Map<String, Object>> bgXzList = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(CHANGE_OPTIONS)) {
            if(CHANGE_OPTIONS.indexOf("01")>=0){
                //名称变更
                Map<String, Object> mcBg = new HashMap<String, Object>();
                mcBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "01").get("DIC_NAME")));
                mcBg.put("DJNR", StringUtil.getString(busRecord.get("COMPANY_NAME")));
                mcBg.put("BGNR", StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")));
                bgXzList.add(mcBg);
            }
            if(CHANGE_OPTIONS.indexOf("02")>=0){
                //住所变更
                Map<String, Object> zsBg = new HashMap<String, Object>();
                zsBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "02").get("DIC_NAME")));
                zsBg.put("DJNR", StringUtil.getString(busRecord.get("REGISTER_ADDR")));
                zsBg.put("BGNR", StringUtil.getString(busRecord.get("REGISTER_ADDR_CHANGE")));
                bgXzList.add(zsBg);
            }
            if(CHANGE_OPTIONS.indexOf("03")>=0){
                //法定代表人变更
                Map<String, Object> frBg = new HashMap<String, Object>();
                frBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "03").get("DIC_NAME")));
                frBg.put("DJNR", StringUtil.getString(busRecord.get("LEGAL_NAME")));
                frBg.put("BGNR", StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")));
                bgXzList.add(frBg);
            }
            if(CHANGE_OPTIONS.indexOf("04")>=0){
                //企业类型变更
                Map<String, Object> qylxBg = new HashMap<String, Object>();
                qylxBg.put("BGSX", StringUtil.getString(extraDictionaryService.
                        get("changeItem", "04").get("DIC_NAME")));
                qylxBg.put("DJNR", StringUtil.getString(dictionaryService.getByJdbc("t_msjw_system_dictype",
                        new String[]{"TYPE_CODE"}, new Object[]{busRecord.get("COMPANY_TYPE")}).get("TYPE_NAME")));
                qylxBg.put("BGNR",StringUtil.getString(dictionaryService.getByJdbc("t_msjw_system_dictype",
                        new String[] { "TYPE_CODE" },new Object[] { busRecord.get("COMPANY_TYPE_CHANGE")})
                                        .get("TYPE_NAME")));
                bgXzList.add(qylxBg);
            }
            if(CHANGE_OPTIONS.indexOf("05")>=0){
                //注册资本（金）变更
                Map<String, Object> zczjBg = new HashMap<String, Object>();
                zczjBg.put("BGSX", StringUtil.getString(extraDictionaryService.
                        get("changeItem", "05").get("DIC_NAME")));
                zczjBg.put("DJNR", StringUtil.getString(busRecord.get("INVESTMENT"))+"万元");
                zczjBg.put("BGNR", StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))+"万元");
                bgXzList.add(zczjBg);
            }
            if(CHANGE_OPTIONS.indexOf("06")>=0){
                //经营(营业)期限变更
                Map<String, Object> qxBg = new HashMap<String, Object>();
                qxBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "06").get("DIC_NAME")));
                qxBg.put("DJNR", StringUtil.getString(busRecord.get("YEAR_FROM"))+
                        " 至  "+StringUtil.getString(busRecord.get("YEAR_TO")));
                String OPRRATE_TERM_TYPE_CHANGE= StringUtil.getString(busRecord.get("OPRRATE_TERM_TYPE_CHANGE")); 
                if (OPRRATE_TERM_TYPE_CHANGE.equals("1")) {//年
                    qxBg.put("BGNR", StringUtil.getString(busRecord.get("BUSSINESS_YEARS_CHANGE"))+"年");
                }else if(OPRRATE_TERM_TYPE_CHANGE.equals("0")){//长期
                    qxBg.put("BGNR","长期");
                }
                bgXzList.add(qxBg);
            }
            if(CHANGE_OPTIONS.indexOf("07")>=0){
                //投资人（股权）变更
                List<Object> holders = new LinkedList<Object>();
                List<Object> holderChanges = new LinkedList<Object>();
                Map<String, Object> gqBg = new HashMap<String, Object>();
                List<Map<String, Object>> holderList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("HOLDER_JSON")));
                List<Map<String, Object>> holderCzList = (List<Map<String, Object>>)
                        busRecord.get("holderCzList");//最终股东列表
                for (Map<String, Object> holder : holderList) {
                    String holderStr = holder.get("SHAREHOLDER_NAME")+("  证照号码：")+(holder.get("LICENCE_NO"));
                    holders.add(holderStr);
                }
                for (Map<String, Object> holderCz : holderCzList) {
                    String holderChangeStr = holderCz.get("SHAREHOLDER_BG_NAME")+
                            ("  证照号码：")+(holderCz.get("LICENCE_NO"));
                    holderChanges.add(holderChangeStr);
                }
                
                gqBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "07").get("DIC_NAME")));
                gqBg.put("DJNR", holders);
                gqBg.put("BGNR", holderChanges);
                bgXzList.add(gqBg);
            }
            if(CHANGE_OPTIONS.indexOf("08")>=0){
                //经营范围变更
                Map<String, Object> jyfwBg = new HashMap<String, Object>();
                jyfwBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem","08").get("DIC_NAME")));
                jyfwBg.put("DJNR", StringUtil.getString(busRecord.get("BUSSINESS_SCOPE")));
                jyfwBg.put("BGNR", StringUtil.getString(busRecord.get("BUSSINESS_SCOPE_CHANGE")));
                bgXzList.add(jyfwBg);
            }
            if(CHANGE_OPTIONS.indexOf("09")>=0){
                //股东或股权发起人改变姓名或名称
                Map<String, Object> gdMcBg = new HashMap<String, Object>();
                List<Object> holderNames = new LinkedList<Object>();
                List<Object> holderChangeNames = new LinkedList<Object>();
                List<Map<String, Object>> holderNameList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("HOLDER_JSON")));
                List<Map<String, Object>> holderCzList = (List<Map<String, Object>>)
                        busRecord.get("holderCzList");//最终股东列表
                for (Map<String, Object> holderName : holderNameList) {
                    String holderNameStr= StringUtil.getString(holderName.get("SHAREHOLDER_NAME"));
                    holderNames.add(holderNameStr);
                }
                for (Map<String, Object> holderCz : holderCzList) {
                    String holderChangeNameStr= StringUtil.getString(holderCz.get("SHAREHOLDER_BG_NAME"));
                    holderChangeNames.add(holderChangeNameStr);
                }
                gdMcBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem","09").get("DIC_NAME")));
                gdMcBg.put("DJNR", holderNames);
                gdMcBg.put("BGNR", holderChangeNames);
                bgXzList.add(gdMcBg);
            }
            if(CHANGE_OPTIONS.indexOf("10")>=0){
                baxz = "☑";
                baxz01 = "☑";
                //董事备案
                Map<String, Object> dsBg = new HashMap<String, Object>();
                List<Object> directors = new LinkedList<Object>();
                List<Object> directorChanges = new LinkedList<Object>();
                List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
                List<Map<String, Object>> directorChangeList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
                for (Map<String, Object> director : directorList) {
                    String directorStr = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                            StringUtil.getString(director.get("DIRECTOR_JOB_OLD"))))+("：")+StringUtil.getString(
                            director.get("DIRECTOR_NAME_OLD"));
                    directors.add(directorStr);
                }
                for (Map<String, Object> directorChange : directorChangeList) {
                    String directorChangeStr = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                            StringUtil.getString(directorChange.get("DIRECTOR_JOB"))))+("：")+StringUtil.getString(
                                    directorChange.get("DIRECTOR_NAME"));
                    directorChanges.add(directorChangeStr);
                }
                dsBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "10").get("DIC_NAME")));
                dsBg.put("DJNR", directors);
                dsBg.put("BGNR", directorChanges);
                //bgXzList.add(dsBg);
            }
            if(CHANGE_OPTIONS.indexOf("11")>=0){
                baxz = "☑";
                baxz02 = "☑";
                //监事备案
                Map<String, Object> jsBg = new HashMap<String, Object>();
                List<Object> supervisors = new LinkedList<Object>();
                List<Object> supervisorChanges = new LinkedList<Object>();
                List<Map<String, Object>> supervisorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("SUPERVISOR_JSON")));
                List<Map<String, Object>> supervisorChangeList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("SUPERVISOR_JSON_CHANGE")));
                for (Map<String, Object> supervisor : supervisorList) {
                    String supervisorStr = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                            StringUtil.getString(supervisor.get("SUPERVISOR_JOB_OLD"))))+("：")+StringUtil.getString(
                                    supervisor.get("SUPERVISOR_NAME_OLD"));
                    supervisors.add(supervisorStr);
                }
                for (Map<String, Object> supervisorChange : supervisorChangeList) {
                    String supervisorChangeStr = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                            StringUtil.getString(supervisorChange.get("SUPERVISOR_JOB"))))+("：")+StringUtil.getString(
                                    supervisorChange.get("SUPERVISOR_NAME"));
                    supervisorChanges.add(supervisorChangeStr);
                }
                jsBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "11").get("DIC_NAME")));
                jsBg.put("DJNR", supervisors);
                jsBg.put("BGNR", supervisorChanges);
                //bgXzList.add(jsBg);
            }
            if(CHANGE_OPTIONS.indexOf("12")>=0){
                baxz = "☑";
                baxz03 = "☑";
                //经理备案
                Map<String, Object> jlBg = new HashMap<String, Object>();
                List<Object> managers = new LinkedList<Object>();
                List<Object> managerChanges = new LinkedList<Object>();
                List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("MANAGER_JSON")));
                List<Map<String, Object>> managerChangeList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("MANAGER_JSON_CHANGE")));
                for (Map<String, Object> manager : managerList) {
                    String managerStr = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                            StringUtil.getString(manager.get("MANAGER_JOB_OLD"))))+("：")+StringUtil.getString(
                                    manager.get("MANAGER_NAME_OLD"));
                    managers.add(managerStr);
                }
                for (Map<String, Object> managerChange : managerChangeList) {
                    String managerChangeStr = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                            StringUtil.getString(managerChange.get("MANAGER_JOB"))))+("：")+StringUtil.getString(
                                    managerChange.get("MANAGER_NAME"));
                    managerChanges.add(managerChangeStr);
                }
                jlBg.put("BGSX", StringUtil.getString(extraDictionaryService.get("changeItem", "12").get("DIC_NAME")));
                jlBg.put("DJNR", managers);
                jlBg.put("BGNR", managerChanges);
                //bgXzList.add(jlBg);
            }
            
            
        }
        busRecord.put("bgXzList", bgXzList);
        //备案
        busRecord.put("BAXZ01", baxz01);
        busRecord.put("BAXZ02", baxz02);
        busRecord.put("BAXZ03", baxz03);
        String lly = StringUtil.getString(busRecord.get("IS_LIAISON_CHANGE"));
        if("1".equals(lly)){
            baxz04 = "☑";
            baxz = "☑";
        }
        busRecord.put("BAXZ04", baxz04);
        busRecord.put("SFBA", baxz);
        
        if (CHANGE_OPTIONS.indexOf("03")<0) {// 法定代表人不做变更时公司登记（备案）申请书中不出现附表1
            busRecord.put("bgfrxx", "delete");
        }
        // 董事/监事/经理不做备案时公司登记（备案）申请书中不出现附表2
        if (CHANGE_OPTIONS.indexOf("10")<0 && CHANGE_OPTIONS.indexOf("11")<0 && CHANGE_OPTIONS.indexOf("12")<0) {
            busRecord.put("dsjsjlxx", "delete");
        }
    }
    
    
    /**
     * 
     * 描述    设置董事监事经理信息
     * @author Allin Lin
     * @created 2021年4月27日 上午10:59:02
     * @param busRecord
     */
    @SuppressWarnings("unchecked")
    private void setDsJsJlList(Map<String, Object> busRecord) {
        WordReplaceParamUtil wordReplaceParamUtil = new WordReplaceParamUtil();
        // 董事
        List<Map<String, Object>> directorList = (List<Map<String, Object>>) JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
        // 监理
        List<Map<String, Object>> supervisorList = (List<Map<String, Object>>) JSON.parse(
                StringUtil.getString(busRecord.get("SUPERVISOR_JSON_CHANGE")));
        // 经理
        List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString( busRecord.get("MANAGER_JSON_CHANGE")));
        // 董事监理经理
        List<Map<String, Object>> dsjsjlList = new LinkedList<Map<String, Object>>();
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        Map<String, Object> info = null;
        int num = 0;
        if (null != directorList && directorList.size() > 0) {
            for (Map<String, Object> map : directorList) {
                info = new HashMap<String, Object>();
                info.put("DSJSJL_NAME", map.get("DIRECTOR_NAME"));
                info.put("DSJSJL_COUNTRY",StringUtil.getString(extraDictionaryService.
                        get("state", StringUtil.getString(map.get("DIRECTOR_COUNTRY"))).get("DIC_NAME")));
                info.put("DSJSJL_IDTYPE", StringUtil.getString(dictionaryService.getDicNames("DocumentType",
                        StringUtil.getString(map.get("DIRECTOR_IDTYPE")))));
                info.put("DSJSJL_IDNO", map.get("DIRECTOR_IDNO"));
                info.put("DSJSJL_JOB", StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                        StringUtil.getString(map.get("DIRECTOR_JOB")))));
                info.put("DSJSJL_GENERATION_MODE",StringUtil.getString(dictionaryService.getDicNames("ssdjcsfs",
                        StringUtil.getString(map.get("DIRECTOR_GENERATION_MODE")))));
                info.put("DSJSJLPHOTOFRONT", "");
                info.put("DSJSJLPHOTOBACK", "");
                if (num == 0) {
                    wordReplaceParamUtil.setDsJsJlSign(EXE_ID, info);//设置面签信息
                }
                dsjsjlList.add(info);
                num++;
            }
        }
        if (null != supervisorList && supervisorList.size() > 0) {
            for (Map<String, Object> map : supervisorList) {
                info = new HashMap<String, Object>();
                info.put("DSJSJL_NAME", map.get("SUPERVISOR_NAME"));
                info.put("DSJSJL_COUNTRY",StringUtil.getString(extraDictionaryService.
                        get("state", StringUtil.getString(map.get("SUPERVISOR_COUNTRY"))).get("DIC_NAME")));
                info.put("DSJSJL_IDTYPE",StringUtil.getString(dictionaryService.getDicNames("DocumentType",
                        StringUtil.getString(map.get("SUPERVISOR_IDTYPE")))));
                info.put("DSJSJL_IDNO", map.get("SUPERVISOR_IDNO"));
                info.put("DSJSJL_JOB", StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                        StringUtil.getString(map.get("SUPERVISOR_JOB")))));
                info.put("DSJSJL_GENERATION_MODE",StringUtil.getString(dictionaryService.getDicNames("ssdjcsfs",
                        StringUtil.getString(map.get("SUPERVISOR_GENERATION_MODE")))));
                info.put("DSJSJLPHOTOFRONT", "");
                info.put("DSJSJLPHOTOBACK", "");
                if (num == 0) {
                    wordReplaceParamUtil.setDsJsJlSign(EXE_ID, info);//设置面签信息
                }
                dsjsjlList.add(info);
                num++;
            }
        }
        if (null != managerList && managerList.size() > 0) {
            for (Map<String, Object> map : managerList) {
                info = new HashMap<String, Object>();
                info.put("DSJSJL_NAME", map.get("MANAGER_NAME"));
                info.put("DSJSJL_COUNTRY",StringUtil.getString(extraDictionaryService.
                        get("state", StringUtil.getString(map.get("MANAGER_COUNTRY"))).get("DIC_NAME")));
                info.put("DSJSJL_IDTYPE",StringUtil.getString(dictionaryService.getDicNames("DocumentType",
                        StringUtil.getString(map.get("MANAGER_IDTYPE")))));
                info.put("DSJSJL_IDNO", map.get("MANAGER_IDNO"));
                info.put("DSJSJL_JOB",StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                        StringUtil.getString(map.get("MANAGER_JOB")))));
                info.put("DSJSJL_GENERATION_MODE",StringUtil.getString(dictionaryService.getDicNames("ssdjcsfs",
                        StringUtil.getString(map.get("MANAGER_GENERATION_MODE")))) );
                info.put("DSJSJLPHOTOFRONT", "");
                info.put("DSJSJLPHOTOBACK", "");
                if (num == 0) {
                    wordReplaceParamUtil.setDsJsJlSign(EXE_ID, info);//设置面签信息
                }
                dsjsjlList.add(info);
                num++;
            }
        }
        if(dsjsjlList.size()>0){
            busRecord.put("dsjsjlList", dsjsjlList); 
        }else{
            busRecord.put("DSJSJL_NAME", ""); 
            busRecord.put("DSJSJL_JOB", "");
            busRecord.put("DSJSJL_GENERATION_MODE", "");
            busRecord.put("DSJSJL_COUNTRY", "");
            busRecord.put("DSJSJL_IDTYPE", "");
            busRecord.put("DSJSJL_IDNO", "");
            busRecord.put("DSJSJLPHOTOFRONT", "");
            busRecord.put("DSJSJLPHOTOBACK", "");
        }
        
    }

    /**
     * 
     * 描述    设置股东（发起人）、外国投资者出资情况
     * @author Allin Lin
     * @created 2021年4月27日 上午10:59:02
     * @param busRecord
     */
    @SuppressWarnings("unchecked")
    public void setHolderCzList(Map<String, Object> busRecord) {
        List<Map<String, Object>> holderCzList = new LinkedList<Map<String, Object>>();
        List<Map<String, Object>> holderYczList = new LinkedList<Map<String, Object>>();
        List<Map<String, Object>> holderXczList = new LinkedList<Map<String, Object>>();
        List<Map<String,Object>> sameHolderList = new ArrayList<Map<String,Object>>();

        // 股东出资信息
        List<Map<String, Object>> holderPayList = (List<Map<String, Object>>) JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON_CHANGE")));
        List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON")));
        List<Map<String, Object>> oldGqlyList= new ArrayList<Map<String,Object>>();//股权来源
        Map<String, Object> info = null;
        if (null != holderPayList && holderPayList.size() > 0) {
            for (Map<String, Object> holderPay : holderPayList) {
                info = new HashMap<String, Object>();
                info.put("SHAREHOLDER_BG_NAME", holderPay.get("SHAREHOLDER_NAME"));
                info.put("ID_ADDRESS", StringUtil.getString(holderPay.get("ID_ADDRESS")));
                info.put("SHAREHOLDER_COMPANYCOUNTRY", StringUtil.getString(extraDictionaryService.
                        get("state", StringUtil.getString(holderPay.get("SHAREHOLDER_COMPANYCOUNTRY")))
                        .get("DIC_NAME")));
                info.put("LICENCE_TYPE", StringUtil.getString(dictionaryService.
                        get("DocumentType", StringUtil.getString(holderPay.get("LICENCE_TYPE"))).get("DIC_NAME")));
                info.put("LICENCE_IDNO",StringUtil.getString(holderPay.get("LICENCE_NO")));
                info.put("LICENCE_NO",StringUtil.getString(holderPay.get("LICENCE_NO")));
                info.put("CONTRIBUTIONS",StringUtil.getString(holderPay.get("CONTRIBUTIONS")));
                info.put("RJCZE",StringUtil.getString(holderPay.get("CONTRIBUTIONS")));
                info.put("PAIDCAPITAL", StringUtil.getString(holderPay.get("PAIDCAPITAL")));
                info.put("PAYMENT_PERIOD", StringUtil.getString(busRecord.get("PAYMENT_PERIOD")));
                info.put("PROPORTION", StringUtil.getString(holderPay.get("PROPORTION")));
                info.put("SHAREHOLDER_TYPE",StringUtil.getString(holderPay.get("SHAREHOLDER_TYPE")));// 股东类型
                info.put("LEGAL_PERSON", StringUtil.getString(holderPay.get("LEGAL_PERSON")));
                info.put("LEGAL_IDNO_PERSON", StringUtil.getString(holderPay.get("LEGAL_IDNO_PERSON")));
                //出资方式
                List<Object> payWay = new LinkedList<Object>();
                //StringBuffer payWay = new StringBuffer("");
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("INVESTMENT_CASH_TOTAL")))){//现金
                    if(Double.valueOf(StringUtil.getString(holderPay.get("INVESTMENT_CASH_TOTAL")))>0){
                        //payWay.append("货币：").append(holderPay.get("INVESTMENT_CASH_TOTAL")).append("万元").append("\r\n");
                        String cashTotal = "货币："+holderPay.get("INVESTMENT_CASH_TOTAL")+"万元";
                        payWay.add(cashTotal);
                    }
                   
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("INVESTMENT_MATERIAL_TOTAL")))){//实物
                    if(Double.valueOf(StringUtil.getString(holderPay.get("INVESTMENT_MATERIAL_TOTAL")))>0){
                       /* payWay.append("实物：").append(holderPay.get("INVESTMENT_MATERIAL_TOTAL"))
                        .append("万元").append("\r\n");*/
                        String materTotal = "实物："+holderPay.get("INVESTMENT_MATERIAL_TOTAL")+"万元";
                        payWay.add(materTotal);
                    }
                    
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("INVESTMENT_TECHNOLOGY_TOTAL")))){//技术出资
                    if(Double.valueOf(StringUtil.getString(holderPay.get("INVESTMENT_TECHNOLOGY_TOTAL")))>0){
                        /*payWay.append("技术出资：").append(holderPay.get("INVESTMENT_TECHNOLOGY_TOTAL"))
                        .append("万元").append("\r\n");*/ 
                        String technologyTotal = "技术出资："+holderPay.get("INVESTMENT_TECHNOLOGY_TOTAL")+"万元";
                        payWay.add(technologyTotal);
                    }
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("INVESTMENT_LAND_TOTAL")))){//土地使用权
                    if(Double.valueOf(StringUtil.getString(holderPay.get("INVESTMENT_LAND_TOTAL")))>0){
                        /*payWay.append("土地使用权：").append(holderPay.get("INVESTMENT_LAND_TOTAL"))
                        .append("万元").append("\r\n");*/
                        String landTotal = "土地使用权："+holderPay.get("INVESTMENT_LAND_TOTAL")+"万元";
                        payWay.add(landTotal);
                    }
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("INVESTMENT_STOCK_TOTAL")))){//股权
                    if(Double.valueOf(StringUtil.getString(holderPay.get("INVESTMENT_STOCK_TOTAL")))>0){
                        //payWay.append("股权：").append(holderPay.get("INVESTMENT_STOCK_TOTAL")).append("万元");
                        String stockTotal = "股权："+holderPay.get("INVESTMENT_STOCK_TOTAL")+"万元";
                        payWay.add(stockTotal);
                    }
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("INVESTMENT_OTHER_TOTAL")))){//其他
                    if(Double.valueOf(StringUtil.getString(holderPay.get("INVESTMENT_OTHER_TOTAL")))>0){
                        /*payWay.append("其他：").append(holderPay.get("INVESTMENT_OTHER_TOTAL"))
                        .append("万元").append("\r\n");*/
                        String otherTotal = "其他："+holderPay.get("INVESTMENT_OTHER_TOTAL")+"万元";
                        payWay.add(otherTotal);
                    }
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(holderPay.get("GQLY_JSON")))){//股东出资方式
                    //获取股权来源为原股东转让的数据
                    List<Map<String, Object>> gqlyList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(holderPay.get("GQLY_JSON")));
                    for (Map<String, Object> gqly : gqlyList) {
                        if(!"新增".equals(StringUtil.getString(gqly.get("stockFrom")))){
                           Map<String, Object> oldGyly = new HashMap<String, Object>();
                           String stockFromName = StringUtil.getString(gqly.get("stockFrom"));
                           oldGyly.put("SHAREHOLDER_NAME", stockFromName.substring(stockFromName.indexOf("-")+1));
                           oldGyly.put("TRANSFER_CONTRIBUTIONS", 
                                   StringUtil.getString(gqly.get("TRANSFER_CONTRIBUTIONS")));//总转让认缴出资额
                           oldGqlyList.add(oldGyly);
                        }
                    }
                }
                info.put("PAYWAY", payWay);
                info.put("CZFS", payWay);
                holderXczList.add(info);
            }
        }
        //判断转让后原股东还有转让额的需要体现。
        if(holderList!=null && holderList.size()>0){
            for (Map<String, Object> holder : holderList) {
                info = new HashMap<String, Object>();
                if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))){
                    if(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN"))) > 0){
                        //统计出资方式（按顺序抵扣）
                        boolean isExist = false;
                        double INVESTMENT_CASH = 0;
                        double INVESTMENT_MATERIAL=0;
                        double INVESTMENT_TECHNOLOGY=0;
                        double INVESTMENT_LAND=0;
                        double INVESTMENT_STOCK=0;
                        double INVESTMENT_OTHER=0;
                        double TRANSFER_CONTRIBUTIONS =0;
                        if(oldGqlyList!=null && oldGqlyList.size()>0){
                            for (Map<String, Object> oldGqly : oldGqlyList) {
                                if(StringUtil.getString(holder.get("SHAREHOLDER_NAME"))
                                        .equals(StringUtil.getString(oldGqly.get("SHAREHOLDER_NAME")))){
                                    isExist = true;
                                    TRANSFER_CONTRIBUTIONS += Double.valueOf(
                                            StringUtil.getString(oldGqly.get("TRANSFER_CONTRIBUTIONS")));
                                }
                            }
                        }
                        info.put("SHAREHOLDER_BG_NAME", holder.get("SHAREHOLDER_NAME"));
                        info.put("ID_ADDRESS", StringUtil.getString(holder.get("ID_ADDRESS")));
                        info.put("SHAREHOLDER_COMPANYCOUNTRY", StringUtil.getString(extraDictionaryService.
                                get("state", StringUtil.getString(holder.get("SHAREHOLDER_COMPANYCOUNTRY")))
                                .get("DIC_NAME")));
                        info.put("LICENCE_TYPE", StringUtil.getString(dictionaryService.
                                get("DocumentType", StringUtil.getString(holder.get("LICENCE_TYPE"))).get("DIC_NAME")));
                        info.put("LICENCE_IDNO",StringUtil.getString(holder.get("LICENCE_NO")));
                        info.put("LICENCE_NO",StringUtil.getString(holder.get("LICENCE_NO")));
                        //若未发生股权变更，则使用旧的出资额缴付时间
                        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
                        if( CHANGE_OPTIONS.indexOf("05") >= 0 || CHANGE_OPTIONS.indexOf("07") >= 0 
                                || CHANGE_OPTIONS.indexOf("09") >= 0 ){//股权变更
                            info.put("PAYMENT_PERIOD", StringUtil.getString(busRecord.get("PAYMENT_PERIOD")));
                        }else{
                            info.put("PAYMENT_PERIOD", StringUtil.getString(busRecord.get("PAYMENT_PERIOD_OLD")));
                        }
                        info.put("CONTRIBUTIONS",StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")));//认缴出资额
                        info.put("RJCZE",StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")));
                        info.put("PAIDCAPITAL", StringUtil.getString(holder.get("PAIDCAPITAL")));//实缴出资额
                        info.put("SHAREHOLDER_TYPE", StringUtil.getString(holder.get("SHAREHOLDER_TYPE")));// 股东类型
                        info.put("LEGAL_PERSON", StringUtil.getString(holder.get("LEGAL_PERSON")));
                        info.put("LEGAL_IDNO_PERSON", StringUtil.getString(holder.get("LEGAL_IDNO_PERSON")));
                        if(isExist){
                            //出资方式
                            //StringBuffer payWay = new StringBuffer("");
                            List<Object> payWay = new LinkedList<Object>();
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_CASH")))){//转让后的现金统计
                                if(Double.valueOf(StringUtil.getString(holder.get("INVESTMENT_CASH")))>0){
                                    INVESTMENT_CASH = Double.valueOf(StringUtil.getString(
                                            holder.get("INVESTMENT_CASH")))-TRANSFER_CONTRIBUTIONS; 
                                    if(INVESTMENT_CASH>0){
                                        /*payWay.append("货币：").append(String.
                                                valueOf(INVESTMENT_CASH)).append("万元").append("\r\n");*/
                                        String cashTotal = "货币："+String.
                                                valueOf(INVESTMENT_CASH)+"万元";
                                        payWay.add(cashTotal);
                                    } 
                                }
                            }else{
                                INVESTMENT_CASH = 0-TRANSFER_CONTRIBUTIONS;
                            }
                            //转让后的实物统计
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_MATERIAL")))){
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_MATERIAL")))>0){
                                    if(INVESTMENT_CASH>0){//现金足够抵扣
                                        /*payWay.append("实物：").append(holder.
                                                get("INVESTMENT_MATERIAL")).append("万元").append("\r\n");*/
                                        String materTotal = "实物："+holder.get("INVESTMENT_MATERIAL")+"万元";
                                        payWay.add(materTotal);
                                        
                                    }else{
                                        INVESTMENT_MATERIAL = Double.valueOf(StringUtil.getString(
                                                holder.get("INVESTMENT_MATERIAL")))+INVESTMENT_CASH; 
                                        if(INVESTMENT_MATERIAL>0){
                                           /* payWay.append("实物：").append(String.
                                                    valueOf(INVESTMENT_MATERIAL)).append("万元").append("\r\n");*/
                                            String materTotal = "实物："+String.
                                                    valueOf(INVESTMENT_MATERIAL)+"万元";
                                            payWay.add(materTotal);
                                        }
                                    }  
                                }
                            }else{
                                INVESTMENT_MATERIAL = INVESTMENT_CASH;
                            }
                            //转让后的技术出资统计
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_TECHNOLOGY")))){
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_TECHNOLOGY")))>0){
                                    if(INVESTMENT_MATERIAL>0){//实物足够抵扣
                                        /*payWay.append("技术出资：").append(holder.
                                                get("INVESTMENT_TECHNOLOGY")).append("万元").append("\r\n");*/
                                        String technologyTotal = "技术出资："+holder.get("INVESTMENT_TECHNOLOGY")+"万元";
                                        payWay.add(technologyTotal);
                                    }else{
                                        INVESTMENT_TECHNOLOGY = Double.valueOf(StringUtil.getString(
                                                holder.get("INVESTMENT_TECHNOLOGY")))+INVESTMENT_MATERIAL; 
                                        if(INVESTMENT_TECHNOLOGY>0){
                                           /* payWay.append("技术出资：").append(String.
                                                    valueOf(INVESTMENT_TECHNOLOGY)).append("万元").append("\r\n");*/
                                            String technologyTotal = "技术出资："+String.
                                                    valueOf(INVESTMENT_TECHNOLOGY)+"万元";
                                            payWay.add(technologyTotal);
                                        }
                                    }  
                                }
                            }else{
                                INVESTMENT_TECHNOLOGY = INVESTMENT_MATERIAL;
                            }
                            //转让后的土地使用权统计
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_LAND")))){
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_LAND")))>0){
                                    if(INVESTMENT_TECHNOLOGY>0){//技术出资足够抵扣
                                        /*payWay.append("土地使用权：").append(holder.
                                                get("INVESTMENT_LAND")).append("万元").append("\r\n");*/
                                        String landTotal = "土地使用权："+holder.get("INVESTMENT_LAND")+"万元";
                                        payWay.add(landTotal);
                                    }else{
                                        INVESTMENT_LAND = Double.valueOf(StringUtil.getString(
                                                holder.get("INVESTMENT_LAND")))+INVESTMENT_TECHNOLOGY; 
                                        if(INVESTMENT_LAND>0){
                                            /*payWay.append("土地使用权：").append(String.
                                                    valueOf(INVESTMENT_LAND)).append("万元").append("\r\n");*/
                                            String landTotal = "土地使用权："+String.
                                                    valueOf(INVESTMENT_LAND)+"万元";
                                            payWay.add(landTotal);
                                        }
                                    }  
                                }
                            }else{
                                INVESTMENT_LAND = INVESTMENT_TECHNOLOGY;
                            }
                            //转让后的股权统计
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_STOCK")))){
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_STOCK")))>0){
                                    if(INVESTMENT_LAND>0){//土地使用权足够抵扣
                                        /*payWay.append("股权：").append(holder.
                                                get("INVESTMENT_STOCK")).append("万元").append("\r\n");*/
                                        String stockTotal = "股权："+holder.get("INVESTMENT_STOCK")+"万元";
                                        payWay.add(stockTotal);
                                    }else{
                                        INVESTMENT_STOCK = Double.valueOf(StringUtil.getString(
                                                holder.get("INVESTMENT_STOCK")))+INVESTMENT_LAND; 
                                        if(INVESTMENT_STOCK>0){
                                            /*payWay.append("股权：").append(String.
                                                    valueOf(INVESTMENT_STOCK)).append("万元").append("\r\n");*/
                                            String stockTotal = "股权："+String.
                                                    valueOf(INVESTMENT_STOCK)+"万元";
                                            payWay.add(stockTotal);
                                        }
                                    }  
                                }
                            }else{
                                INVESTMENT_STOCK = INVESTMENT_LAND;
                            }
                            //转让后的其他统计
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_OTHER")))){
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_OTHER")))>0){
                                    if(INVESTMENT_STOCK>0){//股权足够抵扣
                                        /*payWay.append("其他：").append(holder.
                                                get("INVESTMENT_OTHER")).append("万元").append("\r\n");*/
                                        String otherTotal = "其他："+holder.
                                                get("INVESTMENT_OTHER")+"万元";
                                        payWay.add(otherTotal);
                                    }else{
                                        INVESTMENT_OTHER = Double.valueOf(StringUtil.getString(
                                                holder.get("INVESTMENT_OTHER")))+INVESTMENT_STOCK; 
                                        if(INVESTMENT_OTHER>0){
                                           /* payWay.append("股权：").append(String.
                                                    valueOf(INVESTMENT_OTHER)).append("万元").append("\r\n");*/
                                            String otherTotal = "其他："+String.
                                                    valueOf(INVESTMENT_OTHER)+"万元";
                                            payWay.add(otherTotal);
                                        }
                                    }  
                                }
                            }
                            info.put("PAYWAY", payWay);
                            info.put("CZFS", payWay);
                            double a = Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))
                                    /Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")));
                            DecimalFormat df=new DecimalFormat("#.##");
                            String b=df.format(a*100);
                            info.put("PROPORTION", b+"%");
                            holderYczList.add(info);
                        }else{
                            //出资方式
                            //StringBuffer payWay = new StringBuffer("");
                            List<Object> payWay = new LinkedList<Object>();
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("INVESTMENT_CASH")))){//现金
                                if(Double.valueOf(StringUtil.getString(holder.get("INVESTMENT_CASH")))>0){
                                        /*payWay.append("货币：").append(holder.
                                                get("INVESTMENT_CASH")).append("万元").append("\r\n");*/
                                    String cashTotal = "货币："+holder.
                                            get("INVESTMENT_CASH")+"万元";
                                    payWay.add(cashTotal);
                                    
                                }
                            }
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.
                                    get("INVESTMENT_MATERIAL")))){//实物
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_MATERIAL")))>0){
                                   /* payWay.append("实物：").append(holder.get("INVESTMENT_MATERIAL"))
                                    .append("万元").append("\r\n");*/
                                    String materTotal = "实物："+holder.get("INVESTMENT_MATERIAL")+"万元";
                                    payWay.add(materTotal);
                                }
                                
                            }
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.
                                    get("INVESTMENT_TECHNOLOGY")))){//技术出资
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_TECHNOLOGY")))>0){
                                    /*payWay.append("技术出资：").append(holder.get("holder"))
                                    .append("万元").append("\r\n"); */
                                    String technologyTotal = "技术出资："+holder.get("INVESTMENT_TECHNOLOGY")+"万元";
                                    payWay.add(technologyTotal);
                                }
                            }
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.
                                    get("INVESTMENT_LAND")))){//土地使用权
                                if(Double.valueOf(StringUtil.getString(holder.get("INVESTMENT_LAND")))>0){
                                    /*payWay.append("土地使用权：").append(holder.get("INVESTMENT_LAND"))
                                    .append("万元").append("\r\n");*/
                                    String landTotal = "土地使用权："+holder.get("INVESTMENT_LAND")+"万元";
                                    payWay.add(landTotal);
                                }
                            }
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.
                                    get("INVESTMENT_STOCK")))){//股权
                                if(Double.valueOf(StringUtil.getString(holder.get("INVESTMENT_STOCK")))>0){
                                    /*payWay.append("股权：").append(holder.get("INVESTMENT_STOCK")).append("万元");*/
                                    String stockTotal = "股权："+holder.get("INVESTMENT_STOCK")+"万元";
                                    payWay.add(stockTotal);
                                }
                            }
                            if(StringUtils.isNotEmpty(StringUtil.getString(holder.
                                    get("INVESTMENT_OTHER")))){//其他
                                if(Double.valueOf(StringUtil.getString(holder.
                                        get("INVESTMENT_OTHER")))>0){
                                    /*payWay.append("其他：").append(holder.get("INVESTMENT_OTHER"))
                                    .append("万元").append("\r\n");*/
                                    String otherTotal = "其他："+holder.get("INVESTMENT_OTHER")+"万元";
                                    payWay.add(otherTotal);
                                }
                            }
                            info.put("PAYWAY", payWay);
                            info.put("CZFS", payWay);
                            if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")))){
                                double a = Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))
                                        /Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")));
                                DecimalFormat df=new DecimalFormat("#.##");
                                String b=df.format(a*100);
                                info.put("PROPORTION", b+"%");
                            }else{
                                info.put("PROPORTION", StringUtil.getString(holder.get("PROPORTION")));//出资比例 
                            }
                            holderYczList.add(info);
                        }
                    }
                }
            }
            
        }
        if(holderYczList.size()>0 || holderXczList.size()>0){//合并新旧股东出资情况
            if(holderYczList.size()>0 && holderXczList.size()>0 ){
                for (Map<String, Object> holderXcz : holderXczList) {
                    if(holderYczList.size()>0){
                        for (Map<String, Object> holderYcz : holderYczList) {
                            if(StringUtil.getString(holderYcz.get("SHAREHOLDER_BG_NAME"))
                                    .equals(StringUtil.getString(holderXcz.get("SHAREHOLDER_BG_NAME")))){
                                double CONTRIBUTIONS = Double.valueOf(
                                        StringUtil.getString(holderYcz.get("CONTRIBUTIONS")))
                                        +Double.valueOf(
                                                StringUtil.getString(holderXcz.get("CONTRIBUTIONS")));
                                holderXcz.put("CONTRIBUTIONS", CONTRIBUTIONS);//认缴出资额
                                holderXcz.put("RJCZE", CONTRIBUTIONS);//认缴出资额
                                double PAIDCAPITAL = Double.valueOf(
                                        StringUtil.getString(holderYcz.get("PAIDCAPITAL")))
                                        +Double.valueOf(
                                                StringUtil.getString(holderXcz.get("PAIDCAPITAL")));
                                holderXcz.put("PAIDCAPITAL", PAIDCAPITAL);//实缴出资额
                                //合并计算出资方式
                                List<Object> payWay = new LinkedList<Object>();
                                List<Object> payOldWay = new LinkedList<Object>();
                                List<Object> payNewWay ;
                                double INVESTMENT_CASH_TOTAL = 0;
                                double INVESTMENT_MATERIAL_TOTAL=0;
                                double INVESTMENT_TECHNOLOGY_TOTAL=0;
                                double INVESTMENT_LAND_TOTAL=0;
                                double INVESTMENT_STOCK_TOTAL=0;
                                double INVESTMENT_OTHER_TOTAL=0;
                                payOldWay = (List<Object>)holderYcz.get("PAYWAY");
                                for(int i =0;i<payOldWay.size();i++){
                                    String[] payString= StringUtil.getString(payOldWay.get(i)).split("：");
                                    if("货币".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_CASH_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("实物".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_MATERIAL_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("技术出资".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_TECHNOLOGY_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("土地使用权".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_LAND_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("股权".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_STOCK_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("其他".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_OTHER_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                }
                                payNewWay = (List<Object>)holderXcz.get("PAYWAY");
                                for(int i =0;i<payNewWay.size();i++){
                                    String[] payString= StringUtil.getString(payNewWay.get(i)).split("：");
                                    if("货币".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_CASH_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("实物".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_MATERIAL_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("技术出资".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_TECHNOLOGY_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("土地使用权".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_LAND_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("股权".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_STOCK_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                    if("其他".equals(StringUtil.getString(payString[0]))){
                                        INVESTMENT_OTHER_TOTAL +=Double.valueOf(
                                                payString[1].substring(0, payString[1].indexOf("万")));
                                    }
                                }
                                if(INVESTMENT_CASH_TOTAL>0){
                                    String cashTotal = "货币："+INVESTMENT_CASH_TOTAL+"万元";
                                    payWay.add(cashTotal);
                                }
                                if(INVESTMENT_MATERIAL_TOTAL>0){
                                    String materTotal = "实物："+INVESTMENT_MATERIAL_TOTAL+"万元";
                                    payWay.add(materTotal);
                                }
                                if(INVESTMENT_TECHNOLOGY_TOTAL>0){
                                    String technologyTotal = "技术出资："+INVESTMENT_TECHNOLOGY_TOTAL+"万元";
                                    payWay.add(technologyTotal);
                                }
                                if(INVESTMENT_LAND_TOTAL>0){
                                    String landTotal = "土地使用权："+INVESTMENT_LAND_TOTAL+"万元";
                                    payWay.add(landTotal);
                                }
                                if(INVESTMENT_STOCK_TOTAL>0){
                                    String stockTotal = "股权："+INVESTMENT_STOCK_TOTAL+"万元";
                                    payWay.add(stockTotal);
                                }
                                if(INVESTMENT_OTHER_TOTAL>0){
                                    String otherTotal = "其他："+INVESTMENT_OTHER_TOTAL+"万元";
                                    payWay.add(otherTotal);
                                }
                                holderXcz.put("PAYWAY", payWay);
                                holderXcz.put("CZFS", payWay);
                                double a = CONTRIBUTIONS
                                        /Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")));
                                DecimalFormat df=new DecimalFormat("#.##");
                                String b=df.format(a*100);
                                holderXcz.put("PROPORTION", b+"%");//出资比例
                                sameHolderList.add(holderYcz);
                            }
                        } 
                    }
                }
                if(sameHolderList.size()>0){
                    for (Map<String, Object> same : sameHolderList) {
                        holderYczList.remove(same);
                    }
                }
                holderCzList.addAll(holderYczList);
                holderCzList.addAll(holderXczList);
                busRecord.put("holderCzList", holderCzList); 
            }else if(holderXczList.size()<=0){
                holderCzList.addAll(holderYczList);
                busRecord.put("holderCzList",holderCzList ); 
            }else if(holderYczList.size()<=0){
                holderCzList.addAll(holderXczList);
                busRecord.put("holderCzList",holderCzList );
            }
        }else{
            busRecord.put("SHAREHOLDER_BG_NAME", ""); 
            busRecord.put("SHAREHOLDER_COMPANYCOUNTRY", "");
            busRecord.put("LICENCE_TYPE", "");
            busRecord.put("LICENCE_IDNO", "");
            busRecord.put("CONTRIBUTIONS", "");
            busRecord.put("PAIDCAPITAL", "");
            busRecord.put("PAYMENT_PERIOD", "");
            busRecord.put("PAYWAY", "");
            busRecord.put("PROPORTION", "");
        }
    }

    /**
     * 
     * 描述    一人股东决定（原股东&新股东）、股东会决议（原股东&新股东）
     * @author Allin Lin
     * @created 2021年4月28日 下午2:33:25
     * @param busrecord
     * @param yrgdjdYgdList
     * @param yrgdjdXgdList
     */
    @SuppressWarnings("unchecked")
    private void setJyList(Map<String, Object> busRecord,List<Object> yrgdjdYgdList,List<Object> yrgdjdXgdList
            ,List<Object> gdhyYgdList,List<Object> gdhyXgdList,List<Object> dshyList){
        StringBuffer holderNames = new StringBuffer();
        int yrgdjdYgdListSize = 0;
        int yrgdjdXgdListSize = 0;
        int gdhyYgdListSize = 0;
        int gdhyXgdListSize = 0;
        int dshyListSize = 0;
        //变更页所选事项
        String BASX = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        String[] basxs = BASX.split(",");
        StringBuffer BAITEMNAME = new StringBuffer("");
        for (String baxz : basxs) {
            BAITEMNAME.append(StringUtil.getString(extraDictionaryService.
                    get("changeItem", baxz).get("DIC_NAME"))).append("、");
        }
        busRecord.put("BAITEMNAME", BAITEMNAME.deleteCharAt(BAITEMNAME.length() - 1));
        //旧执行董事/董事会
        boolean exDirector = false; 
        StringBuffer DSZNAMES = new StringBuffer();
        String gdhyname ="";
        boolean exDirectorBoard = false;//董事会
        List<Map<String, Object>> directorOldList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
        for (Map<String, Object> map : directorOldList) {
            if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//执行董事
                exDirector = true;
                DSZNAMES.append("执行董事:").append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
            }
            if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                exDirectorBoard = true;
                DSZNAMES.append("董事长:").append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
            }
        }
        if(exDirector==true){
            gdhyname = "执行董事"; 
        }else if(exDirectorBoard==true){
            gdhyname = "董事长"; 
        }
        busRecord.put("gdhyname", gdhyname);
        busRecord.put("DSZNAMES", DSZNAMES.deleteCharAt(DSZNAMES.length()-1));
        
        //新执行董事/董事会
        boolean newDirector = false; 
        boolean newDirectorBoard = false;
        List<Map<String, Object>> directorNewList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
        if(directorNewList!=null && directorNewList.size()>0){
            for (Map<String, Object> map : directorNewList) {
                if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//执行董事
                    newDirector = true;
                }
                if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//董事长
                    newDirectorBoard = true;
                }
            }  
        }
        List<Map<String, Object>> holderList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON")));
        for (Map<String, Object> map : holderList) {
            holderNames.append(StringUtil.getString(map.get("SHAREHOLDER_NAME"))).append("、");
        }
        busRecord.put("holderNames", holderNames.deleteCharAt(holderNames.length() - 1).toString());//旧股东名字
        
        StringBuffer holderChangeNames = new StringBuffer();
        List<Map<String, Object>> holderChangeList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON_CHANGE")));
        if(holderChangeList!=null){
            for (Map<String, Object> map : holderChangeList) {
                holderChangeNames.append(StringUtil.getString(map.get("SHAREHOLDER_NAME"))).append("、");
            } 
        }
        if(holderChangeNames.length()>0){
            holderChangeNames.deleteCharAt(holderChangeNames.length() - 1).toString();//新股东名字
        }
        
        
        busRecord.put("holderChangeNames", "");
        StringBuffer holderAllNames = new StringBuffer();
        List<Map<String, Object>> holderCzList = (List<Map<String, Object>>)
                busRecord.get("holderCzList");//最终股东列表
        if(holderCzList.size()>0){
            for (Map<String, Object> holderCz : holderCzList) {
                holderAllNames.append(StringUtil.getString(holderCz.get("SHAREHOLDER_BG_NAME"))).append("、");
            }
        }
        if(holderAllNames.length()>0){
            busRecord.put("holderChangeNames", holderAllNames.deleteCharAt(holderAllNames.length() - 1).toString());
        }
        
        
        StringBuffer DIRECTORNAMES = new StringBuffer();
        List<Map<String, Object>> directorChangeList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
        if(directorChangeList!=null){
            for (Map<String, Object> map : directorChangeList) {
                DIRECTORNAMES.append(StringUtil.getString(map.get("DIRECTOR_NAME"))).append("、");
            }  
        }
        if(DIRECTORNAMES.length()>0 && newDirectorBoard){//公司设立新董事会
            busRecord.put("DIRECTORNAMES", DIRECTORNAMES.deleteCharAt(
                    DIRECTORNAMES.length() - 1).toString());//新董事名字
        }else{
            StringBuffer oldDirectorNames = new StringBuffer();
            for (Map<String, Object> map : directorOldList) {
                oldDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
            }
            busRecord.put("DIRECTORNAMES", oldDirectorNames.deleteCharAt(
                    oldDirectorNames.length() - 1).toString());//旧董事名字
        }
        
        StringBuffer OLDDSZ = new StringBuffer();
        busRecord.put("OLDDSZ", "");//旧董事名字
        for (Map<String, Object> map : directorOldList) {
            if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                OLDDSZ.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、"); 
            }
        }
        if(OLDDSZ.length()<=0){
           //判断是否有新董事长
            if(directorChangeList!=null){
                for (Map<String, Object> map : directorChangeList) {
                    if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//董事长
                        OLDDSZ.append(StringUtil.getString(map.get("DIRECTOR_NAME"))).append("、"); 
                    }
                }  
            } 
        }
        if(OLDDSZ.length()>0){
            busRecord.put("OLDDSZ", OLDDSZ.deleteCharAt(
                    OLDDSZ.length() - 1).toString());//旧董事名字  
        }
     
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        if (StringUtils.isNotEmpty(CHANGE_OPTIONS)) {
            if (CHANGE_OPTIONS.indexOf("01") >= 0) {// 名称变更
                StringBuffer mcBg = new StringBuffer("、决定公司名称变更为"
                        + StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")));
                if(!isNewGdMater(busRecord)){//不生成新股东会议/决定
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1) + mcBg.toString());
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1) + mcBg.toString());
                }
                yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1) +mcBg.toString());
                gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+mcBg.toString());
                
            }
            if (CHANGE_OPTIONS.indexOf("02") >= 0) {// 住所变更
                StringBuffer addBg = new StringBuffer("、决定公司住所变更为"
                        + StringUtil.getString(busRecord.get("REGISTER_ADDR_CHANGE")));
                if(!isNewGdMater(busRecord)){//不生成新股东会议/决定
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+addBg.toString());
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+addBg.toString());
                }
                yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+addBg.toString());
                gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+addBg.toString());
                if("1".equals(StringUtil.getString(busRecord.get("IS_BUSSINESS_ADDR")))){//是否有生产经营地址
                    StringBuffer bussiness = new StringBuffer("、决定公司经营场所变更为"
                            + StringUtil.getString(busRecord.get("BUSSINESS_ADDR")));
                    if(!isNewGdMater(busRecord)){//不生成新股东会议/决定
                        yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1) 
                                + bussiness.toString());
                        gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1) 
                                + bussiness.toString());
                    }
                    yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1) 
                            +bussiness.toString());
                    gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1) 
                            +bussiness.toString());
                }
            }
            if (CHANGE_OPTIONS.indexOf("08") >= 0) {//经营范围
                StringBuffer scopeBg = new StringBuffer( "、决定公司经营范围变更为："
                        + StringUtil.getString(busRecord.get("BUSSINESS_SCOPE_CHANGE")));
                if(!isNewGdMater(busRecord)){//不生成新股东会议/决定
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)
                            +scopeBg.toString());
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)
                            +scopeBg.toString());
                }
                yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)
                        +scopeBg.toString());
                gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)
                        +scopeBg.toString());
            }
            
            //股东会议决定（原股东）-内部转内部、新旧股东个数与人名一致(不涉及股权变更时出现)
           /* if(CHANGE_OPTIONS.indexOf("07")<0 && CHANGE_OPTIONS.indexOf("09") < 0 ){
                if((holderList.size()==holderChangeList.size()) && 
                        (StringUtil.getString(busRecord.get("holderNames")).equals(
                                StringUtil.getString(busRecord.get("holderChangeNames"))))){
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+
                            "、决定将公司注册资本从"+StringUtil.getString(busRecord.get("INVESTMENT"))
                            +"万元人民币增至"+StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))
                            +"万元人民币，增加注册资本后，股东出资情况如下：");
                    for (Map<String, Object> map : holderChangeList) {
                        gdhyYgdList.add("  股东"+StringUtil.getString(map.get("SHAREHOLDER_NAME"))
                        +"，认缴出资额"+StringUtil.getString(map.get("CONTRIBUTIONS"))+"万元人民币，占注册资本"
                        + StringUtil.getString(map.get("PROPORTION"))); 
                    }
                }
            }*/
            
            if( CHANGE_OPTIONS.indexOf("05") >= 0 || CHANGE_OPTIONS.indexOf("07") >= 0 
                    || CHANGE_OPTIONS.indexOf("09") >= 0 ){//股权变更
                List<Map<String, Object>> stockFromList = new  LinkedList<Map<String,Object>>();
                for (Map<String, Object> map : holderChangeList) {
                    if(StringUtils.isNotEmpty(StringUtil.getString(map.get("GQLY_JSON")))){//股东出资方式
                        //获取股权来源为原股东转让的数据
                        List<Map<String, Object>> gqlyList = (List<Map<String, Object>>)JSON.parse(
                                StringUtil.getString(map.get("GQLY_JSON")));
                        for (Map<String, Object> gqly : gqlyList) {
                            if(!"新增".equals(StringUtil.getString(gqly.get("stockFrom")))){
                                gqly.put("newHolderName", StringUtil.getString(map.get("SHAREHOLDER_NAME")));//新股东名
                                stockFromList.add(gqly);
                            }
                        }
                    }
                }
                if(stockFromList.size()>0){
                    for (Map<String, Object> stockFrom : stockFromList) {
                        String stockFromName = StringUtil.getString(stockFrom.get("stockFrom"));
                        stockFromName = stockFromName.substring(stockFromName.indexOf("-")+1);
                        yrgdjdYgdList.add((StringUtil.convertInteger(yrgdjdYgdListSize+=1)+
                                "、决定将"+stockFromName+"在公司中的"+StringUtil.getString(stockFrom.get("OLD_PROPORTION"))
                                +"股权（认缴出资额"+StringUtil.getString(stockFrom.get("TRANSFER_CONTRIBUTIONS"))
                                +"万元人民币）以"+StringUtil.getString(stockFrom.get("TRANSFER_PRICE"))
                                +"万元人民币转让给新股东"+StringUtil.getString(stockFrom.get("newHolderName"))));
                        
                        gdhyYgdList.add((StringUtil.convertInteger(gdhyYgdListSize+=1)+
                                "、决定将"+stockFromName+"在公司中的"+StringUtil.getString(stockFrom.get("OLD_PROPORTION"))
                                +"股权（认缴出资额"+StringUtil.getString(stockFrom.get("TRANSFER_CONTRIBUTIONS"))
                                +"万元人民币）以"+StringUtil.getString(stockFrom.get("TRANSFER_PRICE"))
                                +"万元人民币转让给新股东"+StringUtil.getString(stockFrom.get("newHolderName"))));
                    }
                    //一人股东决定（原股东）、有发生股权转让时体现
                    /*yrgdjdYgdList.add("   股东"+holderChangeNames+"，认缴出资额"+
                                    StringUtil.getString(busRecord.get("INVESTMENT"))
                    +"万元人民币，占注册资本100%。"); */
                    //股东会决议（原股东）、有发生股权转让时体现
                    
                    List<Map<String,Object>> newHolderList = new ArrayList<Map<String,Object>>();
                    List<Map<String,Object>> yHolderList = new ArrayList<Map<String,Object>>();
                    List<Map<String,Object>> xHolderList = new ArrayList<Map<String,Object>>();
                    List<Map<String,Object>> sameHolderList = new ArrayList<Map<String,Object>>();
                    for (Map<String, Object> holder : holderList) {//原股东认缴
                        if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))){
                            if(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN"))) > 0){
                                Map<String, Object> info = new HashMap<String, Object>();
                                info.put("SHAREHOLDER_NAME", StringUtil.
                                        getString(holder.get("SHAREHOLDER_NAME")));//股东姓名
                                info.put("CONTRIBUTIONS_REMAIN", StringUtil.
                                        getString(holder.get("CONTRIBUTIONS_REMAIN")));//认缴出资额
                                yHolderList.add(info);
                            }
                        }
                    }
                    
                    for (Map<String, Object> map : holderChangeList) {
                        double CONTRIBUTIONS = 0;
                        if(StringUtils.isNotEmpty(StringUtil.getString(map.get("GQLY_JSON")))){//股东出资方式
                            //获取股权来源为原股东转让的数据
                            List<Map<String, Object>> gqlyList = (List<Map<String, Object>>)JSON.parse(
                                    StringUtil.getString(map.get("GQLY_JSON")));
                            for (Map<String, Object> gqly : gqlyList) {
                                if(!"新增".equals(StringUtil.getString(gqly.get("stockFrom")))){
                                    CONTRIBUTIONS += Double.valueOf(StringUtil.
                                            getString(gqly.get("TRANSFER_CONTRIBUTIONS")));
                                }
                            }
                        }
                        if(CONTRIBUTIONS>0){
                            Map<String, Object> info = new HashMap<String, Object>();
                            info.put("SHAREHOLDER_NAME", StringUtil.getString(map.get("SHAREHOLDER_NAME")));//股东姓名
                            info.put("CONTRIBUTIONS_REMAIN", String.valueOf(CONTRIBUTIONS));//认缴出资额
                            xHolderList.add(info);
                        }
                    }
                    
                    //剔除新旧股东同名情况
                    if(yHolderList.size()>0 && xHolderList.size()>0){
                        for (Map<String, Object> xHolder : xHolderList) {
                            for (Map<String, Object> yHolder : yHolderList) {
                                if(StringUtil.getString(xHolder.get("SHAREHOLDER_NAME"))
                                        .equals(StringUtil.getString(yHolder.get("SHAREHOLDER_NAME")))){
                                    //旧股东资金添加至新股东
                                    xHolder.put("CONTRIBUTIONS_REMAIN", Double.valueOf(StringUtil.
                                            getString(yHolder.get("CONTRIBUTIONS_REMAIN")))
                                            +Double.valueOf(StringUtil.getString(xHolder.get("CONTRIBUTIONS_REMAIN"))));
                                    sameHolderList.add(yHolder);
                                } 
                            } 
                        }
                        if(sameHolderList.size()>0){
                            for (Map<String, Object> same : sameHolderList) {
                                yHolderList.remove(same);
                            }
                        }
                        newHolderList.addAll(yHolderList);
                        newHolderList.addAll(xHolderList);
                    }else if(xHolderList.size()<=0){
                        newHolderList.addAll(yHolderList);
                    }else if(yHolderList.size()<=0){
                        newHolderList.addAll(xHolderList);
                    }
                    
                    if(newHolderList.size()>0){
                        yrgdjdYgdList.add("   股权转让后，股东出资情况如下：");
                        gdhyYgdList.add("   股权转让后，股东出资情况如下：");
                        for (Map<String, Object> newHolder : newHolderList) {
                            StringBuffer ygdPayStr = new StringBuffer();
                            ygdPayStr.append("    股东").append(StringUtil.getString(newHolder.get("SHAREHOLDER_NAME")))
                                    .append("，认缴出资额").append(StringUtil
                                    .getString(newHolder.get("CONTRIBUTIONS_REMAIN")))
                                    .append("万元人民币，占注册资本");
                            double a = Double.valueOf(StringUtil.getString(newHolder.get("CONTRIBUTIONS_REMAIN")))
                                    /Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT")));
                            DecimalFormat df=new DecimalFormat("#.00");
                            String b=df.format(a*100);
                            ygdPayStr.append(b+"%").append("；");
                            gdhyYgdList.add(ygdPayStr.toString());
                            yrgdjdYgdList.add(ygdPayStr.toString());
                        }
                    }
                }
               
                //注册资本变化时出现
                DecimalFormat s=new DecimalFormat("#.00");
                double INVESTMENT = Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT")));
                double INVESTMENT_CHANGE = Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")));
                String oldMoney = s.format(INVESTMENT);
                String newMoney = s.format(INVESTMENT_CHANGE);
                if(!oldMoney.equals(newMoney)){
                    //一人股东决定（新）
                    yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+
                            "、决定将公司注册资本从"+StringUtil.getString(busRecord.get("INVESTMENT"))+"万元人民币增至"
                            +StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))+"万元人民币，增加注册资本后，股东出资情况如下：");
                    yrgdjdXgdList.add("   股东"+holderChangeNames+" ，认缴出资额"
                            +StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))
                    +"万元人民币，占注册资本100%；");
                    
                    //股东会决议（新）
                    gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+
                            "、决定将公司注册资本从"+StringUtil.getString(busRecord.get("INVESTMENT"))+"万元人民币增至"
                            +StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))+"万元人民币，增加注册资本后，股东出资情况如下：");
                    
                    if(holderCzList.size()>0){
                        for (Map<String, Object> holderCz : holderCzList) {
                            StringBuffer xgdPayStr = new StringBuffer();
                            xgdPayStr.append("    股东").append(StringUtil.getString(holderCz.get("SHAREHOLDER_BG_NAME")))
                                    .append("，认缴出资额").append(StringUtil.getString(holderCz.get("CONTRIBUTIONS")))
                                    .append("万元人民币，占注册资本").append(StringUtil.
                                            getString(holderCz.get("PROPORTION"))).append("；");
                            gdhyXgdList.add(xgdPayStr.toString());
                        }
                    }
                }
                
            }
            
            if (CHANGE_OPTIONS.indexOf("05") >= 0) {//注册资本（金）变更
                DecimalFormat s=new DecimalFormat("#.00");
                double INVESTMENT = Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT")));
                double INVESTMENT_CHANGE = Double.valueOf(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")));
                String oldMoney = s.format(INVESTMENT);
                String newMoney = s.format(INVESTMENT_CHANGE);
                if(!oldMoney.equals(newMoney)){//资本金数额发生变化
                    StringBuffer investmentBg = new StringBuffer( "、决定将公司注册资本从"
                            + StringUtil.getString(busRecord.get("INVESTMENT"))
                            + "万元人民币增至"+StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))
                            + "万元人民币，增加注册资本后，股东出资情况如下：");
                    if(CHANGE_OPTIONS.indexOf("07") <0 && CHANGE_OPTIONS.indexOf("09") <0){//不涉及股权变更
                        yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)
                                +investmentBg.toString()); 
                    }
                    StringBuffer ygdInveStr = new StringBuffer("   股东"+holderNames+"，认缴出资额"
                            +StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))
                            +"万元人民币，占注册资本100%；"); 
                    if(CHANGE_OPTIONS.indexOf("07") <0 && CHANGE_OPTIONS.indexOf("09") <0){//不涉及股权变更
                        yrgdjdYgdList.add(ygdInveStr.toString());
                    }
                }
            }
            if (CHANGE_OPTIONS.indexOf("10") >= 0) {//董事备案
                StringBuffer exeDirectorNames = new StringBuffer();
                boolean vreDirector = false;//副董事长
                StringBuffer chrimanDirectorName = new StringBuffer();
                StringBuffer vreDirectorName = new StringBuffer();
                List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
                for (Map<String, Object> map : directorList) {
                    if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//执行董事
                        exeDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
                    }
                    if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                        chrimanDirectorName.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))+"董事长");
                        if((StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).
                                equals(StringUtil.getString(busRecord.get("LEGAL_NAME")))
                                && ("01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))))){
                            chrimanDirectorName.append("兼法定代表人");
                        }
                    }
                    if("02".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//副董事长
                        vreDirector = true;
                        vreDirectorName.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD")));
                    }
                }
                if(exDirector==true){//设执行董事的情况下
                    StringBuffer exeDirectorStr = new StringBuffer("、免去"+ 
                                exeDirectorNames.deleteCharAt(exeDirectorNames.length() - 1).toString()
                            + "执行董事");
                    if((StringUtil.getString(busRecord.get("LEGAL_NAME")).//执行董事为法定代表人
                            equals(exeDirectorNames.toString()))
                           && "30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) ){
                        exeDirectorStr.append("兼法定代表人");
                    }
                    exeDirectorStr.append("职务。");
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+exeDirectorStr.toString());
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+exeDirectorStr.toString());
                }
                if(exDirectorBoard==true){//设董事会的情况下(含董事长)
                    for (Map<String, Object> map : directorList) {
                        String oldDirectorStr = "、免去"
                                +StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))+"董事职务。";
                        yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+oldDirectorStr.toString());
                        gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+oldDirectorStr.toString());
                    }
                    dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、免去"
                      +chrimanDirectorName.toString()+"职务");
                }
                if(vreDirector==true){//副董事长
                    dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、免去"
                            +vreDirectorName.toString()+"副董事长职务");
                }
            }else{
                /*不涉及董事备案
                  1、（一人股东决定（原股东）/股东会决议（新股东））原公司设执行董事且原法定代表人由执行董事兼任且该法定代表人职务发生变化；
                  2、（董事会决议）原公司设董事会情况下且原法定代表人由董事长兼任且法定代表人职务发生变化时；
                */
                StringBuffer exeDirectorNames = new StringBuffer();
                StringBuffer chrimanDirectorName = new StringBuffer();
                List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
                for (Map<String, Object> map : directorList) {
                    if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//执行董事
                        exeDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
                    }
                    if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                        chrimanDirectorName.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))+"、");
                    }
                }
                if(exDirector==true){//设执行董事的情况下
                    StringBuffer exeDirectorStr = new StringBuffer("、免去"+ 
                                exeDirectorNames.deleteCharAt(exeDirectorNames.length() - 1).toString()
                            + "执行董事兼法定代表人职务");
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)
                                    +exeDirectorStr.toString());
                            gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+exeDirectorStr.toString());
                        }
                    }
                }else if(exDirectorBoard==true){//设董事会的情况下
                    StringBuffer exeDirectorStr = new StringBuffer("、免去"+ 
                            chrimanDirectorName.deleteCharAt(chrimanDirectorName.length() - 1).toString()
                        + "董事长兼法定代表人职务");
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+exeDirectorStr.toString());
                        }
                    }
                }
            }
            
            //监事备案
            if (CHANGE_OPTIONS.indexOf("11") >= 0) {
                List<Map<String, Object>> supervisorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("SUPERVISOR_JSON")));
                for (Map<String, Object> map : supervisorList) {
                    StringBuffer supervisorStr = new StringBuffer("、免去"+StringUtil.
                            getString(map.get("SUPERVISOR_NAME_OLD"))+"监事职务。");
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+supervisorStr.toString());
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+supervisorStr.toString());
                }
            }
            /*1、旧公司设董事会、在董事会决议中体现、涉及到经理备案时；或未涉及经理备案但是原法定代表人由经理兼任且法定代表人职务发生变化或出现
              2、 旧公司不设董事会、在一人股东决定（原股东）/股东会决议（新股东）中体现、涉及到经理备案时出现或未涉及经理备案但是原法定代表人由经理兼任且法定代表人职务发生变化时出现*/
            if(exDirectorBoard==true){//董事会
                if (CHANGE_OPTIONS.indexOf("12") >= 0) {//涉及经理备案
                    StringBuffer managerNames = new StringBuffer();
                    StringBuffer managerStr = new StringBuffer();
                    List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("MANAGER_JSON")));
                    for (Map<String, Object> map : managerList) {
                        managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                    }
                    managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)).append("经理");
                    if((StringUtil.getString(busRecord.get("LEGAL_NAME")).//经理为法定代表人
                            equals(managerNames.toString()))
                            && "20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB")))){
                        managerStr.append("兼法定代表人");
                    }
                    dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、解聘"+managerStr+"职务。");
                }else{
                    StringBuffer managerNames = new StringBuffer();
                    StringBuffer managerStr = new StringBuffer();
                    List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("MANAGER_JSON")));
                    for (Map<String, Object> map : managerList) {
                        managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                    }
                    managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)+"经理兼法定代表人");
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、解聘"+managerStr+"职务。");
                        }
                    }
                }  
            }else{
                if (CHANGE_OPTIONS.indexOf("12") >= 0) {//涉及经理备案
                    StringBuffer managerNames = new StringBuffer();
                    StringBuffer managerStr = new StringBuffer();
                    List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("MANAGER_JSON")));
                    for (Map<String, Object> map : managerList) {
                        managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                    }
                    managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)).append("经理");
                    if((StringUtil.getString(busRecord.get("LEGAL_NAME")).//经理为法定代表人
                            equals(managerNames.toString()))
                            && "20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB")))){
                        managerStr.append("兼法定代表人");
                    }
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+"、解聘"+managerStr+"职务。");
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+"、解聘"+managerStr+"职务。");
                }else{
                    StringBuffer managerNames = new StringBuffer();
                    StringBuffer managerStr = new StringBuffer();
                    List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("MANAGER_JSON")));
                    for (Map<String, Object> map : managerList) {
                        managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                    }
                    managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)+"经理兼法定代表人");
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+"、解聘"+managerStr+"职务。");
                            gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+"、解聘"+managerStr+"职务。");
                        }
                    }
                }
            }
            
            //股权未变更下（不生成新股东决定/会议）、变更法定代表人以及董事、监事、经理信息
            if(!isNewGdMater(busRecord)){//不生成新股东会议/决定
                if (CHANGE_OPTIONS.indexOf("10") >= 0) {//董事备案
                    boolean exeDirector = false; 
                    StringBuffer exeDirectorNames = new StringBuffer();
                    boolean directorBoard = false;//董事会
                    List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
                    for (Map<String, Object> map : directorList) {
                        if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//执行董事
                            exeDirector = true;
                            exeDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME"))).append("、");
                        }
                        if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//董事长
                            directorBoard = true;
                        }
                    }
                    if(exeDirector==true){//设执行董事的情况下
                        StringBuffer exeDirectorStr = new StringBuffer(StringUtil.
                                convertInteger(yrgdjdYgdListSize+=1)
                                + "、重新委派"+  exeDirectorNames.deleteCharAt(exeDirectorNames.length() - 1).toString()
                                + "为公司执行董事");
                        StringBuffer exDirectorStr = new StringBuffer(StringUtil.
                                convertInteger(gdhyYgdListSize+=1)
                                + "、重新选举"+  exeDirectorNames.toString()
                                + "为公司执行董事");
                        if(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")).//新执行董事为新法定代表人
                                equals(exeDirectorStr.toString()) && 
                                "30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            exeDirectorStr.append("兼法定代表人");
                        }
                        if(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")).//新执行董事为新法定代表人
                                equals(exeDirectorNames.toString()) && 
                                "30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            exDirectorStr.append("兼法定代表人");
                        }
                        exeDirectorStr.append("，任期三年。");
                        exDirectorStr.append("，任期三年。");
                        yrgdjdYgdList.add(exeDirectorStr.toString());
                        gdhyYgdList.add(exDirectorStr.toString());
                    }
                    if(directorBoard==true){//设董事会的情况下
                        for (Map<String, Object> map : directorList) {
                            String oldDirectorStr = (StringUtil.convertInteger(yrgdjdYgdListSize+=1))+"、重新委派"
                                    +StringUtil.getString(map.get("DIRECTOR_NAME"))+"为公司董事，任期三年。";
                            String gdhyDirectorStr = (StringUtil.convertInteger(gdhyYgdListSize+=1))+"、重新选举"
                                    +StringUtil.getString(map.get("DIRECTOR_NAME"))+"为公司董事，任期三年。";
                            yrgdjdYgdList.add(oldDirectorStr.toString());
                            gdhyYgdList.add(gdhyDirectorStr.toString());

                        }
                    }
                }else{
                    /*不涉及董事备案、原公司设执行董事情况下但是原法定代表人由执行董事兼任且法定代表人职务发生变化；
                                                                    或原公司设执行董事情况下未涉及董事备案但是新法定代表人由执行董事兼任且法定代表人职务发生变化出现*/
                    if(exDirector==true){
                        StringBuffer exeDirectorNames = new StringBuffer();
                        List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                                StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
                        for (Map<String, Object> map : directorList) {
                            if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//执行董事
                                exeDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
                            }
                        }
                        StringBuffer exeDirectorStr = new StringBuffer(
                                    exeDirectorNames.deleteCharAt(exeDirectorNames.length() - 1).toString()
                                + "为公司执行董事，任期三年。");
                        StringBuffer newDirectorNames = new StringBuffer();
                        List<Map<String, Object>> newDirectorList = (List<Map<String, Object>>)JSON.parse(
                                StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
                        if(newDirectorList!=null){
                            for (Map<String, Object> map : newDirectorList) {
                                if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//执行董事
                                    newDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME"))).append("、");
                                }
                            } 
                        }
                        StringBuffer newDirectorStr = new StringBuffer();
                        if(newDirectorNames.length()>0){
                            newDirectorStr = new StringBuffer( 
                                    newDirectorNames.deleteCharAt(newDirectorNames.length() - 1).toString()
                                    + "为公司执行董事兼法定代表人，任期三年。");
                        }else{
                            newDirectorStr = new StringBuffer("");
                        }
                        if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                                StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            if("30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                    !"30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                                yrgdjdYgdList.add(StringUtil.convertInteger
                                        (yrgdjdYgdListSize+=1)+"、重新委派"+exeDirectorStr.toString());
                                gdhyYgdList.add(StringUtil.convertInteger
                                        (gdhyYgdListSize+=1)+"、重新选举"+exeDirectorStr.toString());
                            }else if(!"30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                    "30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                                yrgdjdYgdList.add(StringUtil.convertInteger
                                        (yrgdjdYgdListSize+=1)+"、重新委派"+newDirectorStr.toString());
                                gdhyYgdList.add(StringUtil.convertInteger
                                        (gdhyYgdListSize+=1)+"、重新选举"+exeDirectorStr.toString());
                            }
                        }
                    }
                }
                //监事备案
                if (CHANGE_OPTIONS.indexOf("11") >= 0) {
                    List<Map<String, Object>> supervisorList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("SUPERVISOR_JSON_CHANGE")));
                    for (Map<String, Object> map : supervisorList) {
                        StringBuffer supervisorStr = new StringBuffer(StringUtil.convertInteger(yrgdjdYgdListSize+=1)
                                +"、重新委派"+StringUtil.getString(map.get("SUPERVISOR_NAME"))+"为公司监事，任期三年。");
                        StringBuffer gdhySupervisorStr = new StringBuffer(StringUtil.convertInteger(gdhyYgdListSize+=1)
                                +"、重新选举"+StringUtil.getString(map.get("SUPERVISOR_NAME"))+"为公司监事，任期三年。");
                        yrgdjdYgdList.add(supervisorStr.toString());
                        gdhyYgdList.add(gdhySupervisorStr.toString());

                    }
                }
                /*1、当新公司设董事会、董事会决议中体现新经理聘任、
                  2、当新公司设执行董事、一人股东决定（原股东）、股东会决议（原股东）体现新经理聘任（涉及到经理备案时
                  /未涉及经理备案但是原法定代表人由经理兼任且法定代表人职务发生变化
                   /未涉及经理备案但是新法定代表人由经理兼任且法定代表人职务发生变化）*/
                if(newDirector==true || (exDirector == true && newDirectorBoard!=true)){//新公司设执行董事或最终公司设立执行董事
                    if (CHANGE_OPTIONS.indexOf("12") >= 0) {
                        StringBuffer managerNames = new StringBuffer();
                        StringBuffer managerStr = new StringBuffer();
                        List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                                StringUtil.getString(busRecord.get("MANAGER_JSON_CHANGE")));
                        for (Map<String, Object> map : managerList) {
                            managerNames.append(StringUtil.getString(map.get("MANAGER_NAME"))).append("、");
                        }
                        managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)).append("为公司经理");
                        if(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")).//新经理为新法定代表人
                                equals(managerNames.toString()) && 
                                "20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            managerStr.append("兼法定代表人");
                        }
                        yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+"、聘任"+managerStr+"，任期三年。");
                        gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+"、聘任"+managerStr+"，任期三年。");
                    }else{
                        StringBuffer managerNames = new StringBuffer();
                        StringBuffer managerStr = new StringBuffer();
                        List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                                StringUtil.getString(busRecord.get("MANAGER_JSON")));
                        for (Map<String, Object> map : managerList) {
                            managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                        }
                        managerStr.append("、聘任"+managerNames.deleteCharAt(managerNames.length() - 1)+"为公司经理，任期三年。");
                        StringBuffer newManagerStr = new StringBuffer();
                        newManagerStr.append("、聘任"+StringUtil.getString(busRecord.
                                get("LEGAL_NAME_CHANGE"))+"为公司经理兼法定代表人，任期三年。");
                        if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                                StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            if("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                    !"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                                yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)
                                        +managerStr.toString());
                                gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+managerStr.toString());
                            }else if(!"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                    "20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                                yrgdjdYgdList.add(StringUtil.convertInteger(
                                        yrgdjdYgdListSize+=1)+newManagerStr.toString());
                                gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+newManagerStr.toString());
                            }
                        }
                        
                        
                    }
                }
            }
            
            //一人股东决定（新股东）/股东会决议（新股东）/董事会决议:董事、监事、经理备案
            if (CHANGE_OPTIONS.indexOf("10") >= 0) {//董事备案
                boolean exeDirector = false; 
                StringBuffer exeDirectorNames = new StringBuffer();
                boolean directorBoard = false;//董事会
                boolean vreDirector = false;//副董事长
                StringBuffer chrimanDirectorName = new StringBuffer();
                StringBuffer vreDirectorName = new StringBuffer();
                List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
                for (Map<String, Object> map : directorList) {
                    if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//新执行董事
                        exeDirector = true;
                        exeDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME"))).append("、");
                    }
                    if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//新董事长
                        directorBoard = true;
                        chrimanDirectorName.append(StringUtil.getString(map.get("DIRECTOR_NAME"))+"为公司董事长");
                        if(StringUtil.getString(map.get("DIRECTOR_NAME")).
                                equals(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")))){
                            chrimanDirectorName.append("兼法定代表人");
                        }else if(CHANGE_OPTIONS.indexOf("03")<0 && (StringUtil.getString(map.get("DIRECTOR_NAME")).
                                equals(StringUtil.getString(busRecord.get("LEGAL_NAME"))))
                                && ("01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))))){
                            chrimanDirectorName.append("兼法定代表人");
                        }
                    }
                    if("02".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//新副董事长
                        vreDirector = true;
                        vreDirectorName.append(StringUtil.getString(map.get("DIRECTOR_NAME")));
                    }
                }
                if(exeDirector==true){//设执行董事的情况下
                    StringBuffer exeDirectorStr = new StringBuffer(StringUtil.
                            convertInteger(yrgdjdXgdListSize+=1)
                            + "、重新委派"+  exeDirectorNames.deleteCharAt(exeDirectorNames.length() - 1).toString()
                            + "为公司执行董事");
                    StringBuffer exDirectorStr = new StringBuffer(StringUtil.
                            convertInteger(gdhyXgdListSize+=1)
                            + "、重新选举"+  exeDirectorNames.toString()
                            + "为公司执行董事");
                    if(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")).//新执行董事为新法定代表人
                            equals(exeDirectorNames.toString())){
                        exeDirectorStr.append("兼法定代表人");
                        exDirectorStr.append("兼法定代表人");
                    }
                    exeDirectorStr.append("，任期三年。");
                    exDirectorStr.append("，任期三年。");
                    yrgdjdXgdList.add(exeDirectorStr.toString());
                    gdhyXgdList.add(exDirectorStr.toString());
                }
                if(directorBoard==true){//设董事会的情况下
                    for (Map<String, Object> map : directorList) {
                        String newDirectorStr = (StringUtil.convertInteger(yrgdjdXgdListSize+=1))+"、重新委派"
                                +StringUtil.getString(map.get("DIRECTOR_NAME"))+"为公司董事，任期三年。";
                        String gdhyDirectorStr = (StringUtil.convertInteger(gdhyXgdListSize+=1))+"、重新选举"
                                +StringUtil.getString(map.get("DIRECTOR_NAME"))+"为公司董事，任期三年。";
                        yrgdjdXgdList.add(newDirectorStr.toString());
                        gdhyXgdList.add(gdhyDirectorStr.toString());
                    }
                    dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、选举"
                    +chrimanDirectorName+"，任期三年；");
                }
               if(vreDirector==true){
                   dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、选举"
                           +vreDirectorName+"为公司副董事长，任期三年；");
               }
            }else{
                /*不涉及董事备案
                 1、原公司设执行董事情况下但是原法定代表人由执行董事兼任且法定代表人职务发生变化
                                                         或原公司设执行董事情况下但是新法定代表人由执行董事兼任且法定代表人职务发生变化
                 2、原公司设董事会情况下未涉及董事备案但是原法定代表人由董事长兼任且法定代表人职务发生变化
                                                         或原公司设董事会情况下未涉及董事备案但是新法定代表人由董事长兼任且法定代表人职务发生变化出现*/
                if(exDirector==true){
                    StringBuffer exeDirectorNames = new StringBuffer();
                    List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
                    for (Map<String, Object> map : directorList) {
                        if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//执行董事
                            exeDirectorNames.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
                        }
                    }
                    StringBuffer exeDirectorStr = new StringBuffer( 
                                exeDirectorNames.deleteCharAt(exeDirectorNames.length() - 1).toString()
                            + "为公司执行董事，任期三年。");
                    StringBuffer newDirectorStr = new StringBuffer();
                    newDirectorStr = new StringBuffer( 
                            StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE"))
                            + "为公司执行董事兼法定代表人，任期三年。"); 
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            yrgdjdXgdList.add(StringUtil.convertInteger
                                    (yrgdjdXgdListSize+=1)+"、重新委派"+exeDirectorStr.toString());
                            gdhyXgdList.add(StringUtil.convertInteger
                                    (gdhyXgdListSize+=1)+"、重新选举"+exeDirectorStr.toString());
                        }else if(!"30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                "30".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            yrgdjdXgdList.add(StringUtil.convertInteger
                                    (yrgdjdXgdListSize+=1)+"、重新委派"+newDirectorStr.toString());
                            gdhyXgdList.add(StringUtil.convertInteger
                                    (gdhyXgdListSize+=1)+"、重新选举"+newDirectorStr.toString());
                        }
                    }
                }else if(exDirectorBoard==true){
                    StringBuffer chrimanDirectorName = new StringBuffer();
                    List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
                    for (Map<String, Object> map : directorList) {
                        if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                            chrimanDirectorName.append(StringUtil.getString(map.get("DIRECTOR_NAME_OLD"))).append("、");
                        }
                    }
                    StringBuffer exeDirectorStr = new StringBuffer( 
                            chrimanDirectorName.deleteCharAt(chrimanDirectorName.length() - 1).toString()
                            + "为公司董事长，任期三年。");
                    StringBuffer newChrimanDirectorNames = new StringBuffer();
                    List<Map<String, Object>> newDirectorList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
                    if(newDirectorList!=null){
                        for (Map<String, Object> map : newDirectorList) {
                            if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//董事长
                                newChrimanDirectorNames.append(
                                        StringUtil.getString(map.get("DIRECTOR_NAME"))).append("、");
                            }
                        } 
                    }
                    StringBuffer newDirectorStr = new StringBuffer();
                    if(newChrimanDirectorNames.length()>0){
                        newDirectorStr =  new StringBuffer( 
                                newChrimanDirectorNames.deleteCharAt(newChrimanDirectorNames.length() - 1).toString()
                                + "为公司董事长兼法定代表人，任期三年。");
                    }
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            dshyList.add(StringUtil.convertInteger
                                    (dshyListSize+=1)+"、选举"+exeDirectorStr.toString());
                        }else if(!"01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                "01".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            dshyList.add(StringUtil.convertInteger
                                    (dshyListSize+=1)+"、选举"+newDirectorStr.toString());
                        }
                    }
                }
            }
            
            //监事备案
            if (CHANGE_OPTIONS.indexOf("11") >= 0) {
                List<Map<String, Object>> supervisorList = (List<Map<String, Object>>)JSON.parse(
                        StringUtil.getString(busRecord.get("SUPERVISOR_JSON_CHANGE")));
                for (Map<String, Object> map : supervisorList) {
                    StringBuffer supervisorStr = new StringBuffer(StringUtil.getString(map
                            .get("SUPERVISOR_NAME"))+"为公司监事，任期三年。");
                    yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+"、重新委派"+supervisorStr.toString());
                    gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+"、重新选举"+supervisorStr.toString());
                }
            }
         
            /*1、当新公司设董事会、董事会决议中体现新经理聘任、
              2、当新公司设立执行董事、一人股东决定（新股东）、股东会决议（新股东）体现新经理聘任、
                                              （涉及到经理备案时/未涉及经理备案但是原法定代表人由经理兼任且法定代表人职务发生变化
                /未涉及经理备案但是新法定代表人由经理兼任且法定代表人职务发生变化）*/
            if((newDirector==true) || (newDirectorBoard!=true && exDirector==true)){//新公司设立执行董事
                  if (CHANGE_OPTIONS.indexOf("12") >= 0) {
                      StringBuffer managerNames = new StringBuffer();
                      StringBuffer managerStr = new StringBuffer();
                      List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                              StringUtil.getString(busRecord.get("MANAGER_JSON_CHANGE")));
                      for (Map<String, Object> map : managerList) {
                          managerNames.append(StringUtil.getString(map.get("MANAGER_NAME"))).append("、");
                      }
                      managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)).append("为公司经理");
                      if(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")).//新经理为新法定代表人
                              equals(managerNames.toString()) &&
                              ("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE"))))){
                          managerStr.append("兼法定代表人");
                      }
                      yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+"、聘任"+managerStr+"，任期三年。");
                      gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+"、聘任"+managerStr+"，任期三年。");
                  }else{
                      StringBuffer managerNames = new StringBuffer();
                      StringBuffer managerStr = new StringBuffer();
                      List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                              StringUtil.getString(busRecord.get("MANAGER_JSON")));
                      for (Map<String, Object> map : managerList) {
                          managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                      }
                      managerStr.append("、聘任"+managerNames.deleteCharAt(managerNames.length() - 1)+"为公司经理，任期三年。");
                      StringBuffer newManagerStr = new StringBuffer();
                      newManagerStr.append("、聘任"+StringUtil.getString(busRecord.
                              get("LEGAL_NAME_CHANGE"))+"为公司经理兼法定代表人，任期三年。");
                      if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                              StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                          if("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                  !"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                              yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+managerStr.toString());
                              gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+managerStr.toString());
                          }else if(!"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                  "20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                              yrgdjdXgdList.add(StringUtil.convertInteger(
                                      yrgdjdXgdListSize+=1)+newManagerStr.toString());
                              gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+newManagerStr.toString());
                          }
                      }
                  }
            }else if((newDirectorBoard==true) || (newDirector!=true && exDirectorBoard==true)){//新公司设董事会/未执行董事变更旧公司设立董事会
                //经理备案
                if (CHANGE_OPTIONS.indexOf("12") >= 0) {
                    StringBuffer managerNames = new StringBuffer();
                    StringBuffer managerStr = new StringBuffer();
                    List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("MANAGER_JSON_CHANGE")));
                    for (Map<String, Object> map : managerList) {
                        managerNames.append(StringUtil.getString(map.get("MANAGER_NAME"))).append("、");
                    }
                    managerStr.append(managerNames.deleteCharAt(managerNames.length() - 1)).append("为公司经理");
                    if(StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")).//新经理为新法定代表人
                            equals(managerNames.toString()) && 
                            ("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE"))))){
                        managerStr.append("兼法定代表人");
                    }
                    dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+"、聘任"+managerStr+"，任期三年。");
                }else{
                    StringBuffer managerNames = new StringBuffer();
                    StringBuffer managerStr = new StringBuffer();
                    List<Map<String, Object>> managerList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(busRecord.get("MANAGER_JSON")));
                    for (Map<String, Object> map : managerList) {
                        managerNames.append(StringUtil.getString(map.get("MANAGER_NAME_OLD"))).append("、");
                    }
                    managerStr.append("、聘任"+managerNames.deleteCharAt(managerNames.length() - 1)+"为公司经理，任期三年。");
                    StringBuffer newManagerStr = new StringBuffer();
                    newManagerStr.append("、聘任"+StringUtil.getString(busRecord.
                            get("LEGAL_NAME_CHANGE"))+"为公司经理兼法定代表人，任期三年。"); 
                    if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB"))) && 
                            StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                        if("20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                !"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+managerStr.toString());
                        }else if(!"20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB"))) &&
                                "20".equals(StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE")))){
                            dshyList.add(StringUtil.convertInteger(dshyListSize+=1)+newManagerStr.toString());
                        }
                    } 
                }
            }
            
            //经营期限变更
            if(CHANGE_OPTIONS.indexOf("06")>=0){
                //经营(营业)期限变更
                StringBuffer jyqx = new StringBuffer();
                jyqx.append("、决定公司营业期限变更为").append(StringUtil.getString(busRecord.get("BUSSINESS_YEARS_CHANGE")));
                if("1".equals(busRecord.get("OPRRATE_TERM_TYPE_CHANGE"))){
                    jyqx.append("年；");
                }else if("0".equals(busRecord.get("OPRRATE_TERM_TYPE_CHANGE"))){
                    jyqx.append("长期；");
                }
                if(!isNewGdMater(busRecord)){//不生成新股东会议/决定
                    yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+jyqx);
                    gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+jyqx);
                }
                yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+jyqx);
                gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+jyqx);

            }
            //企业类型变更
            if(CHANGE_OPTIONS.indexOf("04")>=0){
                StringBuffer qylx = new StringBuffer();
                qylx.append("、决定企业类型变更为 "
                        +StringUtil.getString(dictionaryService.getByJdbc("t_msjw_system_dictype",
                                new String[] { "TYPE_CODE" },new Object[] { busRecord.get("COMPANY_TYPE_CHANGE")})
                                .get("TYPE_NAME")));
                yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+qylx.toString());
                gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+qylx.toString());
            }
            //不生成新股东会议/决定出现表决
            StringBuffer bjsj = new StringBuffer();
            if(!isNewGdMater(busRecord)){
                bjsj.append("、表决通过").append(getNewSignTime(StringUtil.
                        getString(busRecord.get("EXE_ID")))).append("制定的公司章程。");
                yrgdjdYgdList.add(StringUtil.convertInteger(yrgdjdYgdListSize+=1)+bjsj.toString());
                gdhyYgdList.add(StringUtil.convertInteger(gdhyYgdListSize+=1)+bjsj.toString());
            }
            StringBuffer byjg = new StringBuffer();
            byjg.append("、表决通过").append(getNewSignTime(StringUtil.
                    getString(busRecord.get("EXE_ID")))).append("制定的公司章程。");
            yrgdjdXgdList.add(StringUtil.convertInteger(yrgdjdXgdListSize+=1)+byjg.toString());
            gdhyXgdList.add(StringUtil.convertInteger(gdhyXgdListSize+=1)+byjg.toString());
            //旧股东签名
            busRecord.put("holderWrite", "");
            //新股东签名
            busRecord.put("holderChangeWrite", "");
            //新董事签名
            busRecord.put("newDirectorWrite", "");
        }
    }
    
    
    
    /**
     * 
     * 描述    经营场所-申报承诺表
     * @author Allin Lin
     * @created 2021年5月7日 上午11:09:18
     * @param busRecord
     */
    private void setIndividual(Map<String, Object> busRecord){
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        busRecord.put("INDIVIDUAL_NAME", StringUtil.getString(busRecord.get("COMPANY_NAME")));
        if(CHANGE_OPTIONS.indexOf("01")>=0){//名称变更
            busRecord.put("INDIVIDUAL_NAME", StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")));
        }
        busRecord.put("BUSINESS_PLACE", StringUtil.getString(busRecord.get("REGISTER_ADDR_CHANGE")));
        busRecord.put("REGISTER_SQUARE_ADDR", StringUtil.getString(busRecord.get("REGISTER_SQUARE_ADDR")));
        busRecord.put("BUSSINESS_ADDR", StringUtil.getString(busRecord.get("BUSSINESS_ADDR")));
        busRecord.put("BUSSINESS_SQUARE_ADDR", StringUtil.getString(busRecord.get("BUSSINESS_SQUARE_ADDR")));
        busRecord.put("LAW_SEND_ADDR", StringUtil.getString(busRecord.get("LAW_SEND_ADDR")));
        busRecord.put("LAW_EMAIL_ADDR", StringUtil.getString(busRecord.get("LAW_EMAIL_ADDR")));
        busRecord.put("LAW_FAX_ADDR", StringUtil.getString(busRecord.get("LAW_FAX_ADDR")));
        busRecord.put("LAW_IM_ADDR", StringUtil.getString(busRecord.get("LAW_IM_ADDR")));
        busRecord.put("PLACE_OWNER", StringUtil.getString(busRecord.get("PLACE_REGISTER_OWNER")));
        busRecord.put("PLACE_TEL", StringUtil.getString(busRecord.get("PLACE_REGISTER_TEL")));
        String IS_BUSSINESS_ADDR = StringUtil.getString(busRecord.get("IS_BUSSINESS_ADDR"));
        if("1".equals(IS_BUSSINESS_ADDR)){
            busRecord.put("PLACE_OWNER", StringUtil.getString(busRecord.get("PLACE_OWNER")));
            busRecord.put("PLACE_TEL", StringUtil.getString(busRecord.get("PLACE_TEL")));
        }
        //取得方式
        String RESIDENCEREGISTERWAYOFGET01 = "□";
        String RESIDENCEREGISTERWAYOFGET23 = "□";
        String RESIDENCEREGISTERWAYOFGET02 = "□";
        String RESIDENCEREGISTERWAYOFGET03 = "□";
        String RESIDENCEREGISTERWAYOFGET04 = "□";
        String RESIDENCEREGISTERWAYOFGET05 = "□";
        String wayOfGet = StringUtil.getString(busRecord.get("RESIDENCE_REGISTER_WAYOFGET"));
        if("1".equals(IS_BUSSINESS_ADDR)){
             wayOfGet = StringUtil.getString(busRecord.get("RESIDENCE_WAYOFGET")); 
        }
        if("01".equals(wayOfGet)){
            RESIDENCEREGISTERWAYOFGET01 = "☑";
        }
        if("02".equals(wayOfGet) || "03".equals(wayOfGet)){
            RESIDENCEREGISTERWAYOFGET23 = "☑";
            if("02".equals(wayOfGet)){
                 RESIDENCEREGISTERWAYOFGET02 = "☑";
            }
            if("03".equals(wayOfGet)){
                RESIDENCEREGISTERWAYOFGET03 = "☑";
            }
        }
        if("04".equals(wayOfGet)){
            RESIDENCEREGISTERWAYOFGET04 = "☑";
        }
        if("05".equals(wayOfGet)){
            RESIDENCEREGISTERWAYOFGET05 = "☑";
        }
        busRecord.put("RESIDENCEREGISTERWAYOFGET01", RESIDENCEREGISTERWAYOFGET01);
        busRecord.put("RESIDENCEREGISTERWAYOFGET23", RESIDENCEREGISTERWAYOFGET23);
        busRecord.put("RESIDENCEREGISTERWAYOFGET02", RESIDENCEREGISTERWAYOFGET02);
        busRecord.put("RESIDENCEREGISTERWAYOFGET03", RESIDENCEREGISTERWAYOFGET03);
        busRecord.put("RESIDENCEREGISTERWAYOFGET04", RESIDENCEREGISTERWAYOFGET04);
        busRecord.put("RESIDENCEREGISTERWAYOFGET05", RESIDENCEREGISTERWAYOFGET05);
        //是否属于军队房产
        String ARMYREGISTERHOURSE01 = "□";
        String ARMYREGISTERHOURSE23 = "□";
        String ARMYREGISTERHOURSE02 = "□";
        String ARMYREGISTERHOURSE03 = "□";
        String hourse = StringUtil.getString(busRecord.get("ARMY_REGISTER_HOURSE"));
        if("1".equals(IS_BUSSINESS_ADDR)){
            hourse = StringUtil.getString(busRecord.get("ARMY_HOURSE")); 
       }
        if("01".equals(hourse)){
            ARMYREGISTERHOURSE01 = "☑";
        }
        if("02".equals(hourse) || "03".equals(hourse)){
            ARMYREGISTERHOURSE23 = "☑";
            if("02".equals(hourse)){
                ARMYREGISTERHOURSE02 = "☑";
            }
            if("03".equals(hourse)){
                ARMYREGISTERHOURSE03 = "☑";
            }
        }
        busRecord.put("ARMYREGISTERHOURSE01", ARMYREGISTERHOURSE01);
        busRecord.put("ARMYREGISTERHOURSE23", ARMYREGISTERHOURSE23);
        busRecord.put("ARMYREGISTERHOURSE02", ARMYREGISTERHOURSE02);
        busRecord.put("ARMYREGISTERHOURSE03", ARMYREGISTERHOURSE03);
        busRecord.put("ARMYHOURSE_REGISTER_REMARKS", StringUtil.getString(
                busRecord.get("ARMYHOURSE_REGISTER_REMARKS")));
        //法定代表人签字
        busRecord.put("SIGN_WRITE","");
       /* Calendar lastDate = Calendar.getInstance();
        busRecord.put("CREATE_TIME", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyy年MM月dd日"));*/
    }

    /**
     * 
     * 描述    章程材料相关
     * @author Allin Lin
     * @created 2021年5月7日 下午6:18:06
     * @param busRecord
     */
    private void setAssociation(Map<String, Object> busRecord){
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        //公司名称
        if(CHANGE_OPTIONS.indexOf("01")>=0){
            busRecord.put("COMPANY_NAME", StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")));
        }
        //全体股东名称
        StringBuffer holderNames = new StringBuffer();
        busRecord.put("HOLDERNAMES","");
        if(StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("holderCzList")))){
            List<Map<String, Object>> holderCzList = (List<Map<String,Object>>)busRecord.get("holderCzList");
            if(holderCzList.size()>0){
                for (Map<String, Object> map : holderCzList) {
                    holderNames.append(StringUtil.getString(map.get("SHAREHOLDER_BG_NAME"))).append("、");
                }
                busRecord.put("HOLDERNAMES", holderNames.deleteCharAt(holderNames.length() - 1).toString());//全体股东名字   
            }
        }
        //注册地址
        busRecord.put("REGISTER_ADDR", StringUtil.getString(busRecord.get("REGISTER_ADDR")));
        if(CHANGE_OPTIONS.indexOf("02")>=0){
            busRecord.put("REGISTER_ADDR", StringUtil.getString(busRecord.get("REGISTER_ADDR_CHANGE")));
        }
        //生产经营地址
        busRecord.put("BUSSINESSADDR", StringUtil.getString(busRecord.get("BUSSINESS_ADDR")));
        //经营范围
        busRecord.put("BUSSINESS_SCOPE", StringUtil.getString(busRecord.get("BUSSINESS_SCOPE")));
        if(CHANGE_OPTIONS.indexOf("08")>=0){
            busRecord.put("BUSSINESS_SCOPE", StringUtil.getString(busRecord.get("BUSSINESS_SCOPE_CHANGE")));
        }
        //公司注册资本
        busRecord.put("INVESTMENT", StringUtil.getString(busRecord.get("INVESTMENT")));
        if((CHANGE_OPTIONS.indexOf("05")>=0) ||
                (StringUtils.isNotEmpty(StringUtil.getString(busRecord.get("INVESTMENT_CHANGE"))))){
            busRecord.put("INVESTMENT", StringUtil.getString(busRecord.get("INVESTMENT_CHANGE")));
        }
        //法定代表人职务
        String LEGAL_JOB = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                StringUtil.getString(busRecord.get("LEGAL_JOB"))));
        String LEGAL_JOB_CHANGE = StringUtil.getString(dictionaryService.getDicNames("ssdjzw",
                StringUtil.getString(busRecord.get("LEGAL_JOB_CHANGE"))));
        busRecord.put("LEGAL_JOB", LEGAL_JOB);
        if(StringUtils.isNotEmpty(LEGAL_JOB_CHANGE)){
            busRecord.put("LEGAL_JOB", LEGAL_JOB_CHANGE);
        }
        //公司经营期限
        List<Object> list = new LinkedList<Object>();
        busRecord.put("BUSSINESS_BZ","");
        busRecord.put("BUSSINESS_BK","");
        busRecord.put("BUSSINESS_THXY","");
        busRecord.put("BUSSINESS_LIST", list);
        String OPRRATE_TERM_TYPE= StringUtil.getString(busRecord.get("OPRRATE_TERM_TYPE")); 
        if (OPRRATE_TERM_TYPE.equals("1")) {//年
            busRecord.put("BUSSINESS_YEARS", StringUtil.getString(busRecord.get("BUSSINESS_YEARS")) + "年");
            busRecord.put("BUSSINESS_BZ", "公司营业期限届满，可以通过修改公司章程而存续。公司延长营业期限须办理变更登记。");
            busRecord.put("BUSSINESS_BK", "（一）、（二）、（四）、（五）");
            busRecord.put("BUSSINESS_THXY", "有《合伙企业法》第四十五条规定的情形之一的，合伙人可以退伙。");
            list.add("（一）公司章程规定的营业期限届满；");
            list.add("    （二）股东决定解散；");
            list.add("    （三）因公司合并或者分立需要解散；");
            list.add("    （四）依法被吊销营业执照、责令关闭或者被撤销；");
            list.add("    （五）人民法院依照《公司法》第一百八十三条的规定予以解散；");
        }else if(OPRRATE_TERM_TYPE.equals("0")){//长期
            busRecord.put("BUSSINESS_YEARS", "长期");
            busRecord.put("BUSSINESS_BZ", "");
            busRecord.put("BUSSINESS_BK", "（一）、（三）、（四）");
            busRecord.put("BUSSINESS_YEARS", "长期");
            busRecord.put("BUSSINESS_THXY", "合伙人在不给合伙企业事务执行造成不利影响的情况下，可以退伙，但应当提前三十日通知其他合伙人。");
            list.add("（一）股东决定解散；");
            list.add("    （二）因公司合并或者分立需要解散；");
            list.add("    （三）依法被吊销营业执照、责令关闭或者被撤销；");
            list.add("    （四）人民法院依照《公司法》第一百八十三条的规定予以解散；");
        }
        if(CHANGE_OPTIONS.indexOf("06")>=0){
            list = new LinkedList<Object>();
            String OPRRATE_TERM_TYPE_CHANGE = StringUtil.getString(busRecord.get("OPRRATE_TERM_TYPE_CHANGE")); 
            if (OPRRATE_TERM_TYPE_CHANGE.equals("1")) {//年
                busRecord.put("BUSSINESS_BZ", "公司营业期限届满，可以通过修改公司章程而存续。公司延长营业期限须办理变更登记。");
                busRecord.put("BUSSINESS_BK", "（一）、（二）、（四）、（五）");
                busRecord.put("BUSSINESS_YEARS", StringUtil.getString(busRecord.get("BUSSINESS_YEARS_CHANGE"))+ "年");
                busRecord.put("BUSSINESS_THXY", "有《合伙企业法》第四十五条规定的情形之一的，合伙人可以退伙。");
                list.add("（一）公司章程规定的营业期限届满；");
                list.add("    （二）股东决定解散；");
                list.add("    （三）因公司合并或者分立需要解散；");
                list.add("    （四）依法被吊销营业执照、责令关闭或者被撤销；");
                list.add("    （五）人民法院依照《公司法》第一百八十三条的规定予以解散；");
            } else if (OPRRATE_TERM_TYPE_CHANGE.equals("0")) {//长期
                busRecord.put("BUSSINESS_BZ", "");
                busRecord.put("BUSSINESS_BK", "（一）、（三）、（四）");
                busRecord.put("BUSSINESS_YEARS", "长期");
                busRecord.put("BUSSINESS_THXY", "合伙人在不给合伙企业事务执行造成不利影响的情况下，可以退伙，但应当提前三十日通知其他合伙人。");
                list.add("（一）股东决定解散；");
                list.add("    （二）因公司合并或者分立需要解散；");
                list.add("    （三）依法被吊销营业执照、责令关闭或者被撤销；");
                list.add("    （四）人民法院依照《公司法》第一百八十三条的规定予以解散；");
            }
        }
        busRecord.put("BUSSINESS_LIST", list);
        //时间
        Calendar lastDate = Calendar.getInstance();
        busRecord.put("FILL_DATE", DateTimeUtil.dateToStr(lastDate.getTime(), "yyyy年MM月dd日"));
        //董事长、副董事长人员个数
        int FDSZSIZE = 0;//副董事长
        List<Map<String, Object>> directorList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON")));
        for (Map<String, Object> director : directorList) {
            String DIRECTOR_JOB_OLD = StringUtil.getString(director.get("DIRECTOR_JOB_OLD"));
            // 副董事长
            if (StringUtils.isNotEmpty(DIRECTOR_JOB_OLD) && DIRECTOR_JOB_OLD.equals("02")) {
                FDSZSIZE++;
            }
        }
        busRecord.put("dsrysl", directorList.size());
        if (FDSZSIZE > 0) {
            busRecord.put("fdszrysl", "副董事长" + FDSZSIZE + "人，");
        } else {
            busRecord.put("fdszrysl", "");
        }
        List<Map<String, Object>> directorChangeList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("DIRECTOR_JSON_CHANGE")));
        if(directorChangeList!=null){
            FDSZSIZE = 0;
            busRecord.put("dsrysl", directorChangeList.size());
            for (Map<String, Object> directorChange : directorChangeList) {
                String DIRECTOR_JOB = StringUtil.getString(directorChange.get("DIRECTOR_JOB"));
                // 副董事长
                if (StringUtils.isNotEmpty(DIRECTOR_JOB) && DIRECTOR_JOB.equals("02")) {
                    FDSZSIZE++;
                }
            }
            if (FDSZSIZE > 0) {
                busRecord.put("fdszrysl", "副董事长" + FDSZSIZE + "人，");
            } else {
                busRecord.put("fdszrysl", "");
            }
        }
        //监事会
        int zgjsNum = 0;
        List<Map<String, Object>> supervisorList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("SUPERVISOR_JSON")));
        busRecord.put("jsrysl", supervisorList.size());
        busRecord.put("ZGJSSL", zgjsNum);
        List<Map<String, Object>> supervisorChangeList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("SUPERVISOR_JSON_CHANGE")));
        if(supervisorChangeList!=null){
            busRecord.put("jsrysl", supervisorChangeList.size());
            for (Map<String, Object> supervisorChange : supervisorChangeList) {
                // 任命方
                String SUPERVISOR_APPOINTOR = StringUtil.getString(supervisorChange.get("SUPERVISOR_APPOINTOR"));
                // 设置职工监事人员
                if (StringUtils.isNotEmpty(SUPERVISOR_APPOINTOR) && SUPERVISOR_APPOINTOR.equals("职工代表大会")) {
                    zgjsNum++;
                }
            }
            busRecord.put("ZGJSSL", zgjsNum);
        }
        
    }
    
  
    /**
     * 
     * 描述    股权转让协议信息
     * @author Allin Lin
     * @created 2021年5月10日 下午3:19:43
     * @param busRecord
     * @param stockFrom
     */
    private void setStockFrom(Map<String, Object> busRecord,String stockFrom){
        WordReplaceParamUtil wordReplaceParamUtil = new WordReplaceParamUtil();
        String EXE_ID = StringUtil.getString(busRecord.get("EXE_ID"));
        if(StringUtils.isNotEmpty(stockFrom)){
            Map<String, Object> stockFromMap = (Map<String, Object>)JSON.parse(stockFrom);
            busRecord.putAll(stockFromMap);
            if("0".equals(StringUtil.getString(busRecord.get("TRANSFER_PRICE")))){
                busRecord.put("PAYWAY", "");
                busRecord.put("NUM", "");
                busRecord.put("PAYDETAIL","");
            }else{
                busRecord.put("PAYWAY", "与付款方式");
                busRecord.put("NUM", "1、");
                busRecord.put("PAYDETAIL", "２、乙方同意在本协议签定之日起15日内，将转让费"+
                busRecord.get("TRANSFER_PRICE")+"万元人民币以"+busRecord.get("PAYMETHOD")+
                "方式一次性支付给甲方。");
            }
            //旧股东签名
            busRecord.put("oldHolderWrite"," ");
            String oldHolderName  = StringUtil.getString(busRecord.get("oldHolderNameWrite"));
            String oldHolderIdNo  = StringUtil.getString(busRecord.get("oldHolderIdNo"));
            wordReplaceParamUtil.setGqzrSign(EXE_ID, oldHolderName, oldHolderIdNo, busRecord, "oldHolderWrite");
            //受让新股东签名
            busRecord.put("newHolderWrite"," ");
            String newHolderName  = StringUtil.getString(busRecord.get("newHolderNameWrite"));
            String newHolderIdNo  = StringUtil.getString(busRecord.get("newHolderIdNo"));
            wordReplaceParamUtil.setGqzrSign(EXE_ID, newHolderName, newHolderIdNo, busRecord, "newHolderWrite");
        }
    }
    /**
     * 
     * 描述 根据办件号获取最近的一个面签信息
     * 
     * @author Rider Chen
     * @created 2021年3月30日 下午6:54:37
     * @param exeid
     * @return
     */
    public String getNewSignTime(String exeid) {
        if (cancelService == null) {
            cancelService = (CancelService) AppUtil.getBean("cancelService");
        }
        String signTime = "";
        Map<String, Object> signinfo = cancelService.findNewSignInfo(exeid);
        if (null != signinfo && signinfo.size() > 0) {
            signTime = StringUtil.getString(signinfo.get("SIGN_TIME"));
            if (StringUtils.isNotEmpty(signTime)) {
                signTime = DateTimeUtil.formatDateStr(signTime, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日");
            }
        } else {
            Map<String, Object> taskInfo = cancelService.findNewTaskInfo(exeid, "窗口办理");
            if (null != taskInfo && taskInfo.size() > 0) {
                signTime = StringUtil.getString(taskInfo.get("CREATE_TIME"));
                if (StringUtils.isNotEmpty(signTime)) {
                    signTime = DateTimeUtil.formatDateStr(signTime, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日");
                }
            }
        }
        return signTime;
    }
    
    /**
     * 
     * 描述    内资变更股权转让协议集合
     * @author Allin Lin
     * @created 2021年6月20日 下午8:43:42
     * @param exeId
     * @param fileId
     * @return
     */
    public List<Map<String, Object>> getStockFromList(String exeId,String fileId){
        //组装股权转让协议信息列表
        List<Map<String, Object>> stockFromList = new  LinkedList<Map<String,Object>>();
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        List<Map<String, Object>> holderList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON")));
        List<Map<String, Object>> holderChangeList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON_CHANGE")));
        if(CHANGE_OPTIONS.indexOf("05") >= 0 || CHANGE_OPTIONS.indexOf("07") >= 0
                || CHANGE_OPTIONS.indexOf("09") >= 0 ){//股权变更
            for (Map<String, Object> map : holderChangeList) {
                if(StringUtils.isNotEmpty(StringUtil.getString(map.get("GQLY_JSON")))){//股东出资方式
                    //获取股权来源为原股东转让的数据
                    List<Map<String, Object>> gqlyList = (List<Map<String, Object>>)JSON.parse(
                            StringUtil.getString(map.get("GQLY_JSON")));
                    for (Map<String, Object> gqly : gqlyList) {
                        if(!"新增".equals(StringUtil.getString(gqly.get("stockFrom")))){
                            Map<String, Object> newGqly = new HashMap<String, Object>();
                            String stockFromName = StringUtil.getString(gqly.get("stockFrom"));
                            stockFromName = stockFromName.substring(stockFromName.indexOf("-")+1);
                            //判断是否为企业法人或个人
                            String licenceType = StringUtil.getString(map.get("LICENCE_TYPE"));
                            if(StringUtils.isNotEmpty(licenceType)){
                                if("JGDM".equals(licenceType) || "YYZZ".equals(licenceType)
                                        || "XYDM".equals(licenceType)){//企业
                                    //受让新股东姓名（用于签字）
                                    newGqly.put("newHolderNameWrite", StringUtil.getString(map.get("LEGAL_PERSON")));
                                   //受让新股东身份证号码
                                    newGqly.put("newHolderIdNo", StringUtil.getString(map.get("LEGAL_IDNO_PERSON")));
                                }else{
                                    //个人
                                    //受让新股东姓名（用于签字）
                                  newGqly.put("newHolderNameWrite", StringUtil.getString(map.get("SHAREHOLDER_NAME")));
                                    //受让新股东身份证号码
                                    newGqly.put("newHolderIdNo", StringUtil.getString(map.get("LICENCE_NO")));
                                }
                                
                            }
                            newGqly.put("stockFrom", stockFromName);//旧股东姓名
                            newGqly.put("newHolderName", StringUtil.getString(map.get("SHAREHOLDER_NAME")));//受让新股东姓名
                            newGqly.put("newHolderAddr", StringUtil.getString(map.get("ID_ADDRESS")));//受让新股东住所
                            newGqly.put("TRANSFER_CONTRIBUTIONS", StringUtil.getString(
                                    gqly.get("TRANSFER_CONTRIBUTIONS")));//总转让额
                            newGqly.put("OLD_PROPORTION", StringUtil.getString(gqly.get("OLD_PROPORTION")));//占原注册资本比例
                            newGqly.put("TRANSFER_PRICE", StringUtil.getString(gqly.get("TRANSFER_PRICE")));//转让价格
                            newGqly.put("PAYMETHOD", StringUtil.getString(gqly.get("PAYMETHOD")));//转让方式
                            stockFromList.add(newGqly);
                        }
                    }
                }
            }
            if(stockFromList.size()>0){
                for (Map<String, Object> stockFrom : stockFromList) {
                    stockFrom.put("oldCompanyName", StringUtil.getString(busRecord.get("COMPANY_NAME")));//旧公司名称
                    stockFrom.put("oldCompanyAddr", StringUtil.getString(busRecord.get("REGISTER_ADDR")));//旧公司地址
                    //获取旧股东信息
                    for (Map<String, Object> holder : holderList) {
                        if((StringUtil.getString(stockFrom.get("stockFrom"))).
                                equals(StringUtil.getString(holder.get("SHAREHOLDER_NAME")))){
                            //判断是否为企业法人或个人
                            String licenceType = StringUtil.getString(holder.get("LICENCE_TYPE"));
                            if(StringUtils.isNotEmpty(licenceType)){
                                if("JGDM".equals(licenceType) || "YYZZ".equals(licenceType)
                                        || "XYDM".equals(licenceType)){//企业
                                    //旧股东姓名（用于签字）
                                    stockFrom.put("oldHolderNameWrite", 
                                            StringUtil.getString(holder.get("LEGAL_PERSON")));
                                    //旧股东身份证号码
                                    stockFrom.put("oldHolderIdNo", 
                                            StringUtil.getString(holder.get("LEGAL_IDNO_PERSON")));
                                }else{
                                    //个人
                                    //旧新股东姓名（用于签字）
                                    stockFrom.put("oldHolderNameWrite", 
                                            StringUtil.getString(holder.get("SHAREHOLDER_NAME")));
                                    //旧股东身份证号码
                                    stockFrom.put("oldHolderIdNo", StringUtil.getString(holder.get("LICENCE_NO")));
                                }
                                
                            }
                            stockFrom.put("oldHolderAddr", StringUtil.getString(holder.get("ID_ADDRESS")));//旧股东住所
                        }
                    }
                    stockFrom.put("MATER_PATH", fileId);//模板文件ID
                }
            }
        }
        return stockFromList;
    }
    
    
    /**
     * 
     * 描述  判断是否生成新股东决定/会议  (依据材料列表生成规则)--和前台保持一致
     * @author Allin Lin
     * @created 2021年7月6日 上午9:22:12
     * @param busRecord
     * @return
     */
    public boolean isNewGdMater(Map<String, Object> busRecord){
        boolean flag = false;
        List<Map<String, Object>> holderCzList = (List<Map<String, Object>>)
                busRecord.get("holderCzList");//最终股东列表
        boolean haveNewChairman = false;//是否有董事长,有董事长代表设立董事会
        String DIRECTOR_JSON_CHANGE = busRecord.get("DIRECTOR_JSON_CHANGE") == null ? ""
                : busRecord.get("DIRECTOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON_CHANGE)) {
            List<Map<String, Object>> directorChangeList = JSON.parseObject(DIRECTOR_JSON_CHANGE, List.class);
            for(Map<String,Object> directorChange : directorChangeList){                
                if(directorChange.get("DIRECTOR_JOB")!=null){
                    if("01".equals(directorChange.get("DIRECTOR_JOB"))){
                        haveNewChairman = true;
                    }
                }
            }
        }
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        if (StringUtils.isNotEmpty(CHANGE_OPTIONS)) {
            if(CHANGE_OPTIONS.indexOf("01")>=0 || CHANGE_OPTIONS.indexOf("02")>=0
                    || CHANGE_OPTIONS.indexOf("05")>=0 || CHANGE_OPTIONS.indexOf("06")>=0
                    || CHANGE_OPTIONS.indexOf("08")>=0 || CHANGE_OPTIONS.indexOf("09")>=0){
                if(holderCzList!=null && holderCzList.size()>0){
                    flag = true;
                }
            }
            if(CHANGE_OPTIONS.indexOf("04")>=0 || CHANGE_OPTIONS.indexOf("07")>=0){
                if(CHANGE_OPTIONS.indexOf("10")<0 && CHANGE_OPTIONS.indexOf("11")<0
                        && CHANGE_OPTIONS.indexOf("12")<0){
                    if(holderCzList!=null && holderCzList.size()>0){
                        flag = true;
                    }
                }
                if(CHANGE_OPTIONS.indexOf("10")>=0 || CHANGE_OPTIONS.indexOf("11")>=0){
                    if(holderCzList!=null && holderCzList.size()>0){
                        flag = true;
                    } 
                }
                if(CHANGE_OPTIONS.indexOf("12")>=0  && !haveNewChairman){
                    if(holderCzList!=null && holderCzList.size()>0){
                        flag = true;
                    } 
                }
            }
        }
        return flag ;
    }
    
    /**
     * 
     * 描述  判断是否生成原股东决定/决议  (依据材料列表生成规则)--和前台保持一致
     * @author Allin Lin
     * @created 2021年7月6日 上午9:22:12
     * @param busRecord
     * @return
     */
    public boolean isOldGdMater(Map<String, Object> busRecord){
        boolean flag = false;
        List<Map<String, Object>> holderList = (List<Map<String, Object>>) JSON.parse(
                StringUtil.getString(busRecord.get("HOLDER_JSON")));//原股东列表
        boolean havechairman = false;//是否有董事长,有董事长代表设立董事会
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            for(Map<String,Object> director : directorList){                
                if(director.get("DIRECTOR_JOB_OLD")!=null){
                    if("01".equals(director.get("DIRECTOR_JOB_OLD"))){
                        havechairman = true;
                    }
                }
            }
        }
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        if (StringUtils.isNotEmpty(CHANGE_OPTIONS)) {
            if(CHANGE_OPTIONS.indexOf("03")>=0 && CHANGE_OPTIONS.indexOf("07")<0 && 
                    "03".equals(StringUtil.getString(busRecord.get("COMPANY_NATURE")))){
                if(holderList!=null && holderList.size()>0){
                    flag = true;
                }
            }
            if(CHANGE_OPTIONS.indexOf("04")>=0 || CHANGE_OPTIONS.indexOf("07")>=0){
                if(CHANGE_OPTIONS.indexOf("10")<0 && CHANGE_OPTIONS.indexOf("11")<0
                        && CHANGE_OPTIONS.indexOf("12")<0){
                    if(holderList!=null && holderList.size()>0){
                        flag = true;
                    }
                }
                if(CHANGE_OPTIONS.indexOf("10")>=0 || CHANGE_OPTIONS.indexOf("11")>=0){
                    if(holderList!=null && holderList.size()>0){
                        flag = true;
                    } 
                }
                if(CHANGE_OPTIONS.indexOf("12")>=0  && !havechairman){
                    if(holderList!=null && holderList.size()>0){
                        flag = true;
                    } 
                }
            }
        }
        return flag ;
    }
    
    /**
     * 
     * 描述  判断是否生成董事会决议  (依据材料列表生成规则)--和前台保持一致
     * @author Allin Lin
     * @created 2021年7月6日 上午9:22:12
     * @param busRecord
     * @return
     */
    public boolean isDshMater(Map<String, Object> busRecord){
        boolean flag = false;
        boolean havechairman = false;//是否有董事长,有董事长代表设立董事会
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            for(Map<String,Object> director : directorList){                
                if(director.get("DIRECTOR_JOB_OLD")!=null){
                    if("01".equals(director.get("DIRECTOR_JOB_OLD"))){
                        havechairman = true;
                    }
                }
            }
        }
        String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
        String COMPANY_NATURE = StringUtil.getString(busRecord.get("COMPANY_NATURE"));

        if (StringUtils.isNotEmpty(CHANGE_OPTIONS)) {
            if(CHANGE_OPTIONS.indexOf("03")>=0 && CHANGE_OPTIONS.indexOf("07")<0){
                if("02".equals(COMPANY_NATURE) || "04".equals(COMPANY_NATURE) || "07".equals(COMPANY_NATURE)){
                    flag = true;
                }
            }
            if(CHANGE_OPTIONS.indexOf("04")>=0 || CHANGE_OPTIONS.indexOf("07")>=0){
                if(CHANGE_OPTIONS.indexOf("10")<0 && CHANGE_OPTIONS.indexOf("11")<0
                        && CHANGE_OPTIONS.indexOf("12")<0){
                    if("02".equals(COMPANY_NATURE) || "04".equals(COMPANY_NATURE) || "07".equals(COMPANY_NATURE)
                            || havechairman){
                        flag = true;
                    }
                }
                if(CHANGE_OPTIONS.indexOf("10")>=0 || CHANGE_OPTIONS.indexOf("11")>=0){
                    if("02".equals(COMPANY_NATURE) || "04".equals(COMPANY_NATURE) || "07".equals(COMPANY_NATURE)
                            || havechairman){
                        flag = true;
                    }
                }
            }
        }
        return flag ;
    }
}
