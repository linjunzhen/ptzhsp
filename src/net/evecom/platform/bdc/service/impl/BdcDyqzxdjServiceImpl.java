/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WordToPdfUtil;
import net.evecom.platform.bdc.dao.BdcDyqzxdjDao;
import net.evecom.platform.bdc.service.BdcDyqzxdjService;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.util.ExcelRedrawUtil;
import net.evecom.platform.bdc.util.WordRedrawUtil;

/**
 * 描述 不动产抵押权注销操作service
 * @author Allin Lin
 * @created 2019年3月5日 上午11:41:13
 */
@Service("bdcDyqzxdjService")
public class BdcDyqzxdjServiceImpl extends BaseServiceImpl implements BdcDyqzxdjService{
    

    /**
     * log
     */
    private static Log log=LogFactory.getLog(BdcDyqzxdjServiceImpl.class);

    /**
     * 所引入Dao
     */
    @Resource
    private BdcDyqzxdjDao dao;


    /**
     * 所引入的service
     */
    @Resource
    private BdcGyjsjfwzydjService gyjsjfwzydjService;
    
    
    /**
     * 所引入的service
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    /**
     * 所引入的service
     */
    @Resource
    private BdcQlcCreateSignFlowService bdcQlcCreateSignFlowService;

    /**
     * 描述 覆盖实体dao方法
     * @author Allin Lin
     * @created 2019年3月5日 上午11:42:08
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 根据sqlFliter获取数据列表
     * @author Allin Lin
     * @created 2019年3月5日 下午2:41:52
     * @param sqlFilter
     * @return
     * @see net.evecom.platform.bdc.service.BdcDyqzxdjService#findBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_WSBS_OPINION T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public void setDyqzxdjspbData(Map<String, Map<String, Object>> args) {
        // TODO Auto-generated method stub
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> serviceItem = args.get("serviceItem");
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
        this.setItemAndDefInfo(returnMap, serviceItem);
        // 设值业务表相关信息
        this.setBusRecordInfo(returnMap, busInfo);
        // 模板类型 templateType未设值则前端默认使用DataRegion方式进行数据填充
        // DataRegion(通过添加书签做的模板)则前端以DataRegion方式填充数据
        // DataTag(通过添加【ITEM_NAME】类似标识的方式做的模板)则前端以DataRegion方式填充数据
        returnMap.put("templateType", "DataTag");
    }
    /**
     * 
     * 描述 为标签设初值以防页面出现标签
     * 
     * @author Roger Li
     * @created 2019年12月17日 下午2:35:52
     * @param returnMap
     * @return
     */
    private void setDefaultValue(Map<String, Object> returnMap, String exeId) {
        returnMap.put("ewm", " ");
        returnMap.put("dlrxm", " ");
        returnMap.put("lxdh2", " ");
        returnMap.put("dljgmc", " ");
        returnMap.put("dlrxm2", " ");
        returnMap.put("lxdh4", " ");
        returnMap.put("dljgmc2", " ");
        returnMap.put("yb", " ");
        returnMap.put("yb1", " ");
        returnMap.put("lz", " ");
        returnMap.put("bz", " ");
        returnMap.put("xydzl", " ");
        returnMap.put("xydbdcdyh", " ");
        returnMap.put("mj", " ");
        returnMap.put("yt", " ");

        // 审核意见
        returnMap.put("hd", " ");
        returnMap.put("fzr", " ");
        returnMap.put("hdsj", " ");
        returnMap.put("sh", " ");
        returnMap.put("scr1", " ");
        returnMap.put("fhrq", " ");
        returnMap.put("cs", " ");
        returnMap.put("scr", " ");
        returnMap.put("fsrq", " ");
    }

    /**
     * 
     * 描述 获取流程主表数据
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:52:45
     * @param execution
     * @return
     */
    private void setSubmitUsrsInfo(Map<String, Object> returnMap, Map<String, Object> execution) {
        // 申请人通信地址
        returnMap.put("txdz1", StringUtil.getValue(execution, "SQRLXDZ"));
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
    }

    /**
     * 
     * 描述 设值服务事项和流程表单相关信息
     * 
     * @author Roger Li
     * @created 2019年12月17日 下午2:26:46
     * @param returnMap,serviceItem
     * @return
     */
    private void setItemAndDefInfo(Map<String, Object> returnMap, Map<String, Object> serviceItem) {
        // 权力类型在setBusInfo中 取的是抵押明细json中的fw_qllx
        // 登记类型
        returnMap.put("djlx", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
    }

    /**
     * 
     * 描述 设值流程相关信息
     * 
     * @author Roger Li
     * @created 2019年12月27日 下午3:34:33
     * @param returnMap
     * @param flowAllObj
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
     * 
     * 描述 设值业务表数据
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:56:30
     * @param busInfo
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setBusRecordInfo(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        // 生成二维码
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            String path = ExcelRedrawUtil.getSavePath("jpg");
            ExcelRedrawUtil.encodeVersion6(returnMap.get("xmsqbh").toString(), path);
            returnMap.put("ewm", "[image]" + path + "[/image]");
        }
        returnMap.put("qllx", "抵押权");


        List<Map> zxmx = JSON.parseArray(StringUtil.getValue(busInfo, "ZXMX_JSON"), Map.class);
        StringBuffer qlrxm = new StringBuffer("");
        StringBuffer ywrmc = new StringBuffer("");
        StringBuffer sfzjzl1 = new StringBuffer("");
        StringBuffer zjhm2 = new StringBuffer("");
        StringBuffer dlrxm2 = new StringBuffer("");
        StringBuffer zl = new StringBuffer("");
        StringBuffer bdcdyh = new StringBuffer("");
        StringBuffer ybdcqzsh = new StringBuffer("");
        StringBuffer bdbzqse = new StringBuffer("");
        if (null!=zxmx && zxmx.size() > 0) {
            for (Map<String, Object> zxmxMap : zxmx) {//(char)11 换行符
                qlrxm.append((char)11+StringUtil.getValue(zxmxMap, "DYQR"));
                ywrmc.append((char)11+StringUtil.getValue(zxmxMap, "DYR"));
                sfzjzl1.append((char)11+StringUtil.getValue(zxmxMap, "ZJLB"));
                zjhm2.append((char)11+StringUtil.getValue(zxmxMap, "ZJHM"));
                dlrxm2.append((char)11+StringUtil.getValue(zxmxMap, "DBR"));
                zl.append((char)11+StringUtil.getValue(zxmxMap, "ZJJZWZL"));
                bdcdyh.append((char)11+StringUtil.getValue(zxmxMap, "BDCDYH"));
                ybdcqzsh.append((char)11+StringUtil.getValue(zxmxMap, "BDCQZH"));
                bdbzqse.append((char)11+StringUtil.getValue(zxmxMap, "BDBZZQSE"));
            }
        }
        // 权利人信息 抵押权人
        returnMap.put("qlrxm", qlrxm.substring(1, qlrxm.length()));
        returnMap.put("sfzjzl", " ");
        returnMap.put("zjhm", " ");
        returnMap.put("txdz", " ");
        returnMap.put("yb", " ");
        returnMap.put("fddbr", " ");
        returnMap.put("lxdh", " ");
        returnMap.put("dlrxm", " ");
        returnMap.put("lxdh1", " ");
        returnMap.put("dljgmc", " ");

        // 义务人信息 抵押人
        returnMap.put("ywrmc", ywrmc.substring(1, ywrmc.length()));
        returnMap.put("sfzjzl1", sfzjzl1.substring(1, sfzjzl1.length()));
        returnMap.put("zjhm2", zjhm2.substring(1, zjhm2.length()));
        returnMap.put("txdz1", " ");
        returnMap.put("yb2", " ");
        returnMap.put("fddbr2", " ");
        returnMap.put("lxdh2", " ");
        returnMap.put("dlrxm2", dlrxm2.substring(1, dlrxm2.length()));
        returnMap.put("lxdh3", " ");
        returnMap.put("dljgmc2", " ");
        
        returnMap.put("zl", zl.substring(1, zl.length()));
        returnMap.put("bdcdyh", bdcdyh.substring(1, bdcdyh.length()));
        returnMap.put("qlxz", " ");
        // TODO 写死
        returnMap.put("bdclx", "土地和房屋");
        returnMap.put("mj", " ");
        returnMap.put("yt", " ");
        returnMap.put("ybdcqzsh", ybdcqzsh.substring(1, ybdcqzsh.length()));
        returnMap.put("gzwlx", " ");

        returnMap.put("bdbzqse", bdbzqse.substring(1, bdbzqse.length()));
        
        if (StringUtils.isNotBlank((String) busInfo.get("QLQSSJ"))
                && StringUtils.isNotBlank((String) busInfo.get("QLJSSJ"))) {
            StringBuilder zwlxqx = new StringBuilder("");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "QLQSSJ"))).append("起 ");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "QLJSSJ"))).append("止");
            returnMap.put("zwlxqx", zwlxqx.toString());
        } else {
            returnMap.put("zwlxqx", " ");
        }
        // 地役权情况
    }
    
    
    /**
     * 描述     抵押权注销登记前置事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年10月14日 下午2:44:26
     * @param flowVars
     * @return
     * @throws Exception
     */
    public Map<String, Object> dyqzxdjGenAndSign(Map<String,Object> flowVars)throws Exception{
        Map<String, Object> returnMap ;
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        String isqcwb = StringUtil.getString(flowVars.get("ISQCWB"));//前台申报方式（1：智能审批 0：全程网办）       
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作
        String curStepName=StringUtil.getString(flowVars.get("EFLOW_CUREXERUNNINGNODENAMES"));//环节名称
        String pttSqfs = StringUtil.getValue(flowVars, "PTT_SQFS");//平潭通申报方式（1智能审批 2全程网办）
        if(StringUtils.isNotEmpty(exeId)&& StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                &&StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)){
            if( ("1".equals(isqcwb) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("开始")) ||
                    ("1".equals(pttSqfs) && StringUtils.isNotEmpty(curStepName) && curStepName.equals("网上预审"))){
                /*材料业务数据回填*/
                returnMap = initGenValue(flowVars);
                /*材料替换字符串&生成PDF文件&签章流程*/                       
                zxSqbGenPdf(BdcDyqzxdjService.ZXSQS_KEY,
                        BdcDyqzxdjService.ZXSQS_MATERNAME,exeId,returnMap); //注销申请书-生成PDF 
                commonGenPdf(BdcDyqzxdjService.JQZM_KEY,
                        BdcDyqzxdjService.JQZM_MATERNAME,exeId,returnMap);//结清证明-生成PDF 
                zxSqbSign(flowVars,returnMap);//注销申请书-签章 
                if((boolean)flowVars.get("SIGN_FLAG")){
                    zxJqzmSign(flowVars,returnMap);//结清证明-签章
                    
                } 
            }
        }
        return flowVars;
    }
    
    /**
     * 描述    抵押权注销登记-注销申请书签章
     * @author Allin Lin
     * @created 2020年10月16日 上午9:25:11
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void zxSqbSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcDyqzxdjService.ZXSQS_MATERNAME});
        //2.创建用户&机构
        signUserAndExinstitution(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = bdcQlcMaterGenAndSignService.
                    getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcDyqzxdjService.ZXSQS_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfDyqzxdjsqs(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                } 
            }        
        }
    }
    
    
    /** 
     * 描述  抵押权注销登记-结清证明签章  
     * @author Allin Lin
     * @created 2020年10月16日 下午2:47:02
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void zxJqzmSign(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        //1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",new String[]{"EXE_ID","MATER_NAME"},
                new Object[] { flowVars.get("EFLOW_EXEID"),BdcDyqzxdjService.JQZM_MATERNAME});
        //2.创建用户&机构
        signUserAndExinstitution(flowVars,returnMap);
        if((boolean)flowVars.get("SIGN_FLAG")){
            //3.文件直传
            Map<String, Object> fileResult = bdcQlcMaterGenAndSignService.
                    getFileKey(materSignInfo.get("MATER_PDFPATH").toString(),
                    flowVars.get("EFLOW_EXEID").toString(),BdcDyqzxdjService.JQZM_MATERNAME);
            if(!(boolean)fileResult.get("SIGN_FLAG")){
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
            }else{
                //4.签署流程
                Map<String, Object> result = bdcQlcCreateSignFlowService.
                        createSignFlowOfDyqzxdjjqzm(flowVars,returnMap); 
                if(!(boolean)result.get("SIGN_FLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                } 
            }        
        }
    }
    
    
    
    /**
     * 描述   抵押权注销登记-签章所有用户与机构创建 
     * @author Allin Lin
     * @created 2020年8月24日 下午4:27:15
     * @param flowVars
     * @param returnMap
     */
    public void signUserAndExinstitution(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        //1.创建外部机构（抵押权人(企业或银行)&抵押权人经办人）
        Map<String, Object> developer = creDyqrInfo(returnMap);
        if(!(boolean)developer.get("INS_CREATEFLAG")){
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", developer.get("SIGN_MSG"));  
        } 
        if((boolean)flowVars.get(("SIGN_FLAG"))){
            //2.若申请方式为双方申请时-需创建抵押人
            if("2".equals(StringUtil.getString(flowVars.get("APPLICANT_TYPE")))){
                List<Map<String, Object>> dyqList = (List<Map<String, Object>> )returnMap.get("DYR_LIST");
                for (Map<String, Object> dyr : dyqList) {
                    Map<String, Object> user = new HashMap<String, Object>();
                    user.put("sqrxm", dyr.get("DYRXM"));
                    user.put("sqrzjlx", dyr.get("DYRZJLX_C"));
                    user.put("sqrzjhm", dyr.get("DYRZJHM"));
                    user.put("sqrsjhm", dyr.get("DYRDHHM"));
                    Map<String, Object> exUser = bdcQlcMaterGenAndSignService.creExUser(user); 
                    if(!(boolean)exUser.get("USER_CREATEFLAG")){
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                        break;
                    }
                } 
            }
        } 
    }
    
    /** 
     * 描述      抵押权注销登记-创建抵押权人信息（企业或银行，含经办人）
     * @author Allin Lin
     * @created 2020年8月20日 上午8:51:16
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> creDyqrInfo(Map<String, Object> returnMap)throws Exception{
        Map<String, Object> dyqr = new HashMap<String, Object>();
        //抵押权人信息
        dyqr.put("licenseNumber", returnMap.get("DYQRXX_IDNO"));//证照号码
        dyqr.put("organizeName", returnMap.get("DYQRXM"));//名称
        dyqr.put("licenseType", returnMap.get("DYQRXX_ZJZL_C"));//证照类型
        //抵押权人-经办信息
        dyqr.put("sqrsjhm", returnMap.get("JBDH"));//手机号码
        dyqr.put("sqrzjhm", returnMap.get("JBZJHM"));//证件号码
        dyqr.put("sqrzjlx", returnMap.get("JBZJLX_C"));//证件类型
        dyqr.put("sqrxm", returnMap.get("JBR"));//姓名               
        dyqr = bdcQlcMaterGenAndSignService.creExInstitutions(dyqr);
        return dyqr;  
    }
    /**
     * 描述    生成注销申请表PDF文件
     * @author Allin Lin
     * @created 2020年10月14日 下午9:44:30
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void zxSqbGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){ 
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//dyqzxdj//"+templatePath;       
        generateSQB(returnMap,AttachFilePath+path);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空  
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空    
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = BdcQlcMaterGenAndSignServiceImpl.getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
    /** 
     * 描述  非动态表格通用生成PDF文件
     * @author Allin Lin
     * @created 2020年8月24日 下午5:52:25
     * @param templatePath
     * @param materName
     * @param exeId
     * @param returnMap
     */
    public void commonGenPdf(String templatePath,String materName,String exeId,Map<String, Object> returnMap){ 
        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//dyqzxdj//"+templatePath;       
        WordRedrawUtil.generateWord(returnMap,AttachFilePath+path);
        /*word转PDF文件,进行存储*/          
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("EXE_ID",exeId);//申报号
        variables.put("MATER_NAME",materName);//材料模板名称         
        variables.put("IS_SIGN", "0");//签署状态更新-未通知
        variables.put("DOWNLOAD_DOCURL", "");//文档下载地址清空    
        variables.put("CANCEL_STATUS", "0");//作废更新-未作废
        variables.put("CANCEL_FAILREASON", "");//作废失败原因清空
        //判断材料生成PDF签署文件是否已经存在
        Map<String, Object> signInfo  = this.getByJdbc("T_BDCQLC_MATERSIGNINFO", 
                new String[]{"EXE_ID","MATER_NAME"}, new Object[]{exeId,materName});
        String newWordFilePath = returnMap.get("bdcPath").toString();//模板替换生成word路径           
        String pdfFilePath = BdcQlcMaterGenAndSignServiceImpl.getSignPdfPath();//生成PDF文件路径      
        variables.put("MATER_PATH",newWordFilePath);
        variables.put("MATER_PDFPATH",pdfFilePath);
        WordToPdfUtil.word2pdf(newWordFilePath, pdfFilePath);
        if(signInfo!=null){
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", signInfo.get("YW_ID").toString());
        }else{
            this.saveOrUpdate(variables, "T_BDCQLC_MATERSIGNINFO", UUIDGenerator.getUUID());
        }   
        log.info("不动产网办业务签章材料："+materName+"-模板生成PDF文件成功");
    }
    
    
    
    /** 
     * 描述 初始化签章材料业务数据
     * @author Allin Lin
     * @created 2020年10月14日 下午2:53:43
     * @param flowVars
     * @return
     * @see net.evecom.platform.bdc.service.BdcDyqzxdjService#initGenValue(java.util.Map)
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars){
        Map<String,Object> result = new HashMap<String, Object>();
        String ZXMX_JSON = (String) flowVars.get("ZXMX_JSON");
        /*前台业务申报时办理一次抵押权注销登记只允许注销一条明细*/
        /*1、注销申请书*/
        List zxmxList = JSON.parseArray(ZXMX_JSON, Map.class);//注销明细
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<zxmxList.size();i++){
            Map<String,Object> zxmx = (Map) zxmxList.get(i);
            //抵押人信息(存在多个人的情况)
            String[] dyrList = StringUtil.getString(zxmx.get("DYR")).split(",");
            String[] dyrZjhmList = StringUtil.getString(zxmx.get("ZJHM")).split(",");
            String[] dyrDhhmList = StringUtil.getString(zxmx.get("DYRXX_DHHM")).split(",");
            for (int j = 0; j < dyrList.length; j++) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("DYRXM", dyrList[j]);//姓名
                map.put("DYRZJLX", StringUtil.getString(zxmx.get("ZJLB")));//证件类型
                map.put("DYRZJLX_C", zjlxFormmat(zxmx.get("ZJLB").toString()));//证件类型 (用于签章)
                map.put("GJ","中国");//证件类型 
                map.put("DYRZJHM", dyrZjhmList[j]);//证件号码
                map.put("DYRDHHM", dyrDhhmList[j]);//手机号码
                if(j==0){
                    if(dyrList.length==1){
                        map.put("GYQK", "■单独所有□共同共有 □按份共有______%"); 
                    }else{
                        map.put("GYQK", "□单独所有 ■共同共有  □按份共有______%");  
                    }               
                }else{
                    map.put("GYQK", "■共同共有 □按份共有______%");
                }
                list.add(map); 
            }              
        }
        result.put("DYR_LIST", list);        
        Map map = (Map)zxmxList.get(0);
        //抵押权人       
        result.put("DYQRXM", StringUtil.getString(map.get("DYQR")));
        result.put("DYQRXX_ZJZL", StringUtil.getString(map.get("DYQRXX_ZJZL")));
        result.put("DYQRXX_ZJZL_C", zjlxFormmat(StringUtil.getString(map.get("DYQRXX_ZJZL"))));
        result.put("DYQRXX_IDNO", StringUtil.getString(map.get("DYQRXX_IDNO")));        
        //抵押权人经办
        result.put("JBR", StringUtil.getString(map.get("DYQRXX_JB")));
        result.put("JBZJLX", StringUtil.getString(map.get("DYQRXX_JBZJLX")));
        result.put("JBZJLX_C", zjlxFormmat(StringUtil.getString(map.get("DYQRXX_JBZJLX"))));
        result.put("JBZJHM", StringUtil.getString(map.get("DYQRXX_JBZJHM")));
        result.put("JBDH", StringUtil.getString(map.get("DYQRXX_JBDH")));
        //抵押人-联系人(取第一个)       
        result.put("DY_LXR", StringUtil.getString(map.get("DYR")).split(",")[0]);
        result.put("DY_DHHM", StringUtil.getString(map.get("DYRXX_DHHM")).split(",")[0]);
        //不动产坐落       
        result.put("ZJJZWZL", StringUtil.getString(map.get("ZJJZWZL")));
        //不动产登记证明号
        result.put("BDCDJZMH", StringUtil.getString(map.get("BDCDJZMH")));
        //申请时间
        result.put("CLSCSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));  
        /*2、结清证明书*/
        result.put("DYR", StringUtil.getString(map.get("DYR")));
        result.put("DYZJHM", StringUtil.getString(map.get("ZJHM")));
        return result;
    }
    
    
    /**
     * 描述    生成注销申请表
     * @author Allin Lin
     * @created 2020年10月14日 下午5:50:33
     * @param returnMap
     * @param path
     */
    public void generateSQB(Map<String, Object> returnMap,String path){
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            in = new FileInputStream(new File(path));
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            List<XWPFTableCell> cells = new ArrayList<XWPFTableCell>();
            for (XWPFTableRow row : xwpfDocument.getTables().get(0).getRows()) {
                cells.addAll(row.getTableCells());
            }
            for (XWPFTableCell cell1 : cells) {
                String tag = cell1.getText().trim();
                List<XWPFParagraph> paragraphs = cell1.getParagraphs();
                WordRedrawUtil.processParagraphs(paragraphs, returnMap, xwpfDocument);
            }
            // 处理动态表格
            List<Map<String, Object>> equipList = (List<Map<String, Object>>) returnMap.get("DYR_LIST");
            Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                // table.addRow(rows.get(0));
                XWPFTableRow oldRow = null;
     
                WordReplaceUtil.addTableRow3(returnMap, table, rows, oldRow, equipList, "DYRXM");
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> celllists = row.getTableCells();
                    for (XWPFTableCell cell : celllists) {
                        List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                        WordReplaceUtil.setTableRow(returnMap, xwpfDocument, celllists, paragraphListTable, equipList,
                                "DYRXM");
                        WordReplaceUtil.processParagraphs(paragraphListTable, returnMap, xwpfDocument);
                    }
                }
            }
            
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            xwpfDocument.write(fos);
            returnMap.put("bdcPath", savePath);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }
    
    
    
    private String zjlxFormmat(String zjlx){
        String fmtzjlx = "";
        if(zjlx.equals("身份证") || zjlx.equals("SF")){
            fmtzjlx = "IDCard";
        }else if(zjlx.equals("港澳居民来往内地通行证") || zjlx.equals("GATX")){
            fmtzjlx = "HMPass";
        }else if(zjlx.equals("护照") || zjlx.equals("HZ")){
            fmtzjlx = "Passport";
        }else if(zjlx.equals("营业执照")||zjlx.equals("统一社会信用代码")){
            fmtzjlx = "SOCNO";
        }else if(zjlx.equals("组织机构代码")){
            fmtzjlx = "ORANO";
        }else{
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }
}
