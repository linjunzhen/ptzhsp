/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.platform.ecipws.model.EcipDataInputWsImplServiceSoapBindingStub;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.CreditDataInputService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 短信发送服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:45:06
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("creditDataInputService")
public class CreditDataInputServiceImpl extends BaseServiceImpl implements CreditDataInputService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CreditDataInputServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/9/1 11:15:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * pcipDataInputWs
     */
//    @Resource
//    private PcipDataInputWs pcipDataInputWs;
    
    

    /**
     * 
     * 描述 获取dao
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:52:27
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 办件警告短信
     * 
     * @author Water Guo
     * @created 2017年03月02日 下午1:28:08
     * @param
     */
    @Override
    public void creditDataInput(Log log) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT * FROM (SELECT A.*,ROWNUM AS ROWNUMBER "
                    + " FROM (SELECT R.RES_ID,R.DATA_TYPE,R.INTER_TYPE,R.OPER_TYPE,R.HAS_ATTR,R.EXE_ID,R.TASK_ID "
                    + " FROM T_BSFW_SWBDATA_RES R WHERE R.OPER_STATUS = 0 and r.data_type = 40 "
                    + " ORDER BY R.DATA_TYPE ASC, R.CREATE_TIME ASC ) a ) where rownumber <= 200 ";
            List<Map<String, Object>> dataList = this.dao.findBySql(sql, new Object[] {}, null);
            if (dataList != null && dataList.size() > 0) {
                log.info("开始推送平潭市场主体信用汇总数据，数量：" + dataList.size());
                for (Map<String, Object> dataRes : dataList) {
                    this.sendInfoToCreditPlatform(dataRes);
                }
                log.info("结束推送平潭市场主体信用汇总数据......");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("结束推送平潭市场主体信用汇总数据......");
        }
    }

    /**
     * 描述:信用数据推送接口改写
     *
     * @author Madison You
     * @created 2019/10/31 10:48:00
     * @param
     * @return
     */
    private void sendInfoToCreditPlatform(Map<String, Object> dataRes) {
        // TODO Auto-generated method stub
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.CREDIT_DATAINPUT_XZXK });
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        String exeid = (String) dataRes.get("EXE_ID");
        String resId = (String) dataRes.get("RES_ID");
        StringBuffer serviceSql = new StringBuffer();
        serviceSql.append(" select r.result_id,r.xkdocument_num,r.xkdocument_name,r.xk_type,r.xkdecide_time, ");
        serviceSql.append(" r.xkfile_name,r.effect_time,r.xkfile_num, ");
        serviceSql.append(" r.close_time,r.xkdept_name,r.xkcontent,r.XK_USC,e.SQJG_CODE,e.SQJG_NAME,e.BSYHLX, ");
        serviceSql.append(" e.SQJG_LEALPERSON,e.exe_id,e.SQRMC,e.SQJG_LEALPERSON_ZJLX,e.jbr_name,e.jbr_zjhm, ");
        serviceSql.append(" e.SQJG_LEALPERSON_ZJHM,e.SQRZJLX,e.SQJG_CREDIT_CODE,e.SQRSFZ,d.depart_name, ");
        serviceSql.append(" d.depart_id,s.item_name,d.USC,r.ISLONG_TERM,e.REGIST_NUM ");
        serviceSql.append(" from JBPM6_EXECUTION e left join JBPM6_FLOW_RESULT r on r.exe_id = e.exe_id ");
        serviceSql.append(" left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code ");
        serviceSql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE = s.SSBMBM ");
        serviceSql.append(" where e.exe_id = ? ");
        // 获取事项及办件相关数据
        try {
            Map<String, Object> serviceItemMap = this.dao.getMapBySql(serviceSql.toString(), new Object[] { exeid });
            serviceItemMap = encryptionService.mapDecrypt(serviceItemMap, "JBPM6_EXECUTION");
            Map<String, Object> permit = new HashMap<String, Object>();
            Map<String, Object> prmtalts = new HashMap<String, Object>();
            Map<String, Object> prmtalt = new HashMap<String, Object>();
            /*行政许可ID(原来的)*/
            permit.put("licid", serviceItemMap.get("RESULT_ID"));
            /*许可事项名称，申报事项名称*/
            permit.put("xzxkmc", serviceItemMap.get("ITEM_NAME"));
            permit.put("sbsxmc", serviceItemMap.get("ITEM_NAME"));
            /*行政相对人名称,类别*/
            String sqjgName = serviceItemMap.get("SQJG_NAME") == null ? "" : (String) serviceItemMap.get("SQJG_NAME");
            permit.put("entname", sqjgName);
            permit.put("xkxdrlb", "1");
            /*统一社会信用代码(原来的)*/
            String sqjgCode = serviceItemMap.get("SQJG_CODE")==null?
                    "":serviceItemMap.get("SQJG_CODE").toString();
            Object sqjgCreditCode = serviceItemMap.get("SQJG_CREDIT_CODE");
            if (sqjgCreditCode != null) {
                permit.put("uniscid", sqjgCreditCode);
            } else {
                if (sqjgCode.length() == 18) {
                    permit.put("uniscid", sqjgCode);
                }
            }
            /*工商注册号*/
            String REGIST_NUM = serviceItemMap.get("REGIST_NUM") == null ? "" : (String) serviceItemMap.get("REGIST_NUM");
            permit.put("regno", REGIST_NUM);
            /*行政相对人代码_3(组织机构代码)(取消不用了)*/
            permit.put("zzjgdm", "");
            /*税务登记号,事业单位证书号,社会组织登记证号，非必填直接为空*/
            permit.put("xkxdrswdj", "");
            permit.put("xkxdrsydw", "");
            permit.put("xkxdrshzz", "");
            /*法定代表人*/
            String fddbrxm = serviceItemMap.get("SQJG_LEALPERSON") == null ? "" :
                    (String)serviceItemMap.get("SQJG_LEALPERSON");
            permit.put("fddbrxm", fddbrxm);
            /*法定代表人证件类型 中文值 */
            Object sqjgLealpersonZjlx = serviceItemMap.get("SQJG_LEALPERSON_ZJLX");
            if (sqjgLealpersonZjlx != null) {
                if (sqjgLealpersonZjlx.equals("SF")) {
                    permit.put("xkfrzjlx", "身份证");
                } else if (sqjgLealpersonZjlx.equals("HZ")) {
                    permit.put("xkfrzjlx", "护照号");
                } else if (sqjgLealpersonZjlx.equals("GATX")) {
                    permit.put("xkfrzjlx", "港澳居民来往内地通行证");
                } else if (sqjgLealpersonZjlx.equals("TWTX")) {
                    permit.put("xkfrzjlx", "台湾居民来往大陆通行证");
                }
            } else {
                permit.put("xkfrzjlx", "");
            }
            /*法定代表人证件号码*/
            String sqjgLealpersonZjhm = serviceItemMap.get("SQJG_LEALPERSON_ZJHM") == null ? "" :
                    (String) serviceItemMap.get("SQJG_LEALPERSON_ZJHM");
            permit.put("jmsfzh", sqjgLealpersonZjhm);
            /*自然人证件类型*/
    //        String sqrzjlx = serviceItemMap.get("SQRZJLX") == null ? "" :
    //                (String) serviceItemMap.get("SQRZJLX");
            permit.put("xkxdrzjlx", "");
            /*自然人证件号码*/
    //        String sqrsfz = serviceItemMap.get("SQRSFZ") == null ?
    //                "" : (String) serviceItemMap.get("SQRSFZ");
            permit.put("xkxdrzjhm", "");
            /*行政许可决定文书名称,行政许可决定文书号*/
            Object xkfileNum = serviceItemMap.get("XKFILE_NUM");
            Object xkfileName = serviceItemMap.get("XKFILE_NAME");
            Object xkdocumentNum = serviceItemMap.get("XKDOCUMENT_NUM");
            Object xkdocumentName = serviceItemMap.get("XKDOCUMENT_NAME");
            if (xkdocumentName != null) {
                permit.put("xkxkws", xkdocumentName);
            } else {
                permit.put("xkxkws", xkfileName);
            }
            if (xkdocumentNum != null) {
                permit.put("xkwsh", xkdocumentNum);
            } else {
                permit.put("xkwsh", xkfileNum);
            }
            /*许可编号*/
            permit.put("licno", xkfileNum);
            /*许可证书名称*/
            permit.put("licname_cn", xkfileName);
            /*许可类别*/
            String xkType = serviceItemMap.get("XK_TYPE") == null ? "" :
                    (String) serviceItemMap.get("XK_TYPE");
            permit.put("approvetype", xkType);
            /*有效期自*/
            permit.put("valfrom", serviceItemMap.get("EFFECT_TIME"));
            /*有效期至*/
            Object islongTerm = serviceItemMap.get("ISLONG_TERM");
            Object closeTime = serviceItemMap.get("CLOSE_TIME") == null ? "" : serviceItemMap.get("CLOSE_TIME");
            if (islongTerm != null && islongTerm.equals("1")) {
                permit.put("valto", "2099-12-31");
            } else {
                permit.put("valto", closeTime);
            }

            /*许可内容*/
            permit.put("licitem", serviceItemMap.get("XKCONTENT"));
            /*许可决定日期*/
            String xkdecideTime = serviceItemMap.get("XKDECIDE_TIME") == null ? "" :
                    (String) serviceItemMap.get("XKDECIDE_TIME");
            permit.put("xkjdrq", xkdecideTime);
            /*许可机关*/
            permit.put("licanth", serviceItemMap.get("XKDEPT_NAME"));
            /*许可机关统一社会信用代码,数据来源单位统一社会信用代码*/
            Object xkUsc = serviceItemMap.get("XK_USC");
            String usc = serviceItemMap.get("USC") == null ? "" : (String) serviceItemMap.get("USC");
            if (xkUsc != null) {
                permit.put("xklydwdm", xkUsc);
                permit.put("xkxkjgdm", xkUsc);
            } else {
                permit.put("xklydwdm", usc);
                permit.put("xkxkjgdm", usc);
            }
            /*当前状态 1.有效；2.无效 字典值*/
            permit.put("xkzt", "1");
            /*数据来源单位*/
            String department = serviceItemMap.get("DEPART_NAME") == null ? "" :
                    (String) serviceItemMap.get("DEPART_NAME");
            permit.put("datadept", department);
            /*备注*/
            permit.put("remark", "");
            //变更信息
            permit.put("prmtalts", prmtalts);//??
            prmtalts.put("prmtalts", prmtalt);//??
            StringBuffer buffer = new StringBuffer();
            buffer.append(this.makeDataXml(permit, configXml));
            EcipDataInputWsImplServiceSoapBindingStub stub;
            String result = "";
            stub = new EcipDataInputWsImplServiceSoapBindingStub();
            result = stub.collectReportPermit("ptzhsp", "ptzhsp", "1", buffer.toString());
            StringBuffer sql = new StringBuffer();
            if (result.equals("0000")) {
                sql.append(" update t_bsfw_swbdata_res r set r.oper_status = 1, ");
                sql.append(" r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where r.res_id = ? ");
            }else {
                sql.append(" update t_bsfw_swbdata_res r set r.oper_status = 2, ");
                sql.append(" r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where r.res_id = ? ");
                log.info("信用数据推送发生错误，错误码为：" + result);
            }
            dao.executeSql(sql.toString(), new Object[] {resId});
        } catch (Exception e) {
            StringBuffer sql = new StringBuffer();
            log.error("信用数据推送发生错误，错误申报号为：" + exeid + e.getMessage());
            sql.append(" update t_bsfw_swbdata_res r set r.oper_status = 2, ");
            sql.append(" r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where r.res_id = ? ");
            dao.executeSql(sql.toString(), new Object[] {resId});
        }
          
        
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
}



